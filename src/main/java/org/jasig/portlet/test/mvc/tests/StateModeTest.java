/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.portlet.test.mvc.tests;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
@Controller("stateModeTest")
@RequestMapping(value = {"VIEW", "EDIT", "HELP", "ABOUT"}, params="currentTest=stateModeTest")
public class StateModeTest extends BasePortletTest {

    @Override
    public String getTestName() {
        return "State/Mode Test";
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleActionRequestInternal(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @ActionMapping
    public void handleActionRequestInternal(ActionRequest request, ActionResponse response) throws Exception {
        final PortletMode actionPortletMode = request.getPortletMode();
        final WindowState actionWindowState = request.getWindowState();

        this.logger.info("Handling ActionRequest with PortletMode='" + actionPortletMode + "' and WindowState='" + actionWindowState + "'");
        
        response.setRenderParameter("ACTION_PORTLET_MODE", actionPortletMode.toString());
        response.setRenderParameter("ACTION_WINDOW_STATE", actionWindowState.toString());
        
        
        final String requestedPortletMode = request.getParameter("REQUESTED_PORTLET_MODE");
        if (requestedPortletMode != null) {
            this.logger.info("PortletMode '" + requestedPortletMode + "' requested to be set during action.");
            final PortletMode portletMode = new PortletMode(requestedPortletMode);
            
            final boolean portletModeAllowed = request.isPortletModeAllowed(portletMode);
            if (portletModeAllowed) {
                try {
                    response.setPortletMode(portletMode);
                }
                catch (PortletException pe) {
                    this.logger.warn("Failed to set requested PortletMode to '" + portletMode + "' during action.", pe);
                    throw pe;
                }
            }
            else {
                response.setRenderParameter("REQUESTED_PORTLET_MODE_NOT_ALLOWED", portletMode.toString());
                this.logger.info("PortletMode '" + portletMode + "' is not allowed.");
            }
        }
        
        final String requestedWindowState = request.getParameter("REQUESTED_WINDOW_STATE");
        if (requestedWindowState != null) {
            this.logger.info("WindowState '" + requestedWindowState + "' requested to be set during action.");
            final WindowState windowState = new WindowState(requestedWindowState);
            
            final boolean windowStateAllowed = request.isWindowStateAllowed(windowState);
            if (windowStateAllowed) {
                try {
                    response.setWindowState(windowState);
                }
                catch (PortletException pe) {
                    this.logger.warn("Failed to set requested WindowState to '" + windowState + "' during action.", pe);
                    throw pe;
                }
            }
            else {
                response.setRenderParameter("REQUESTED_WINDOW_STATE_NOT_ALLOWED", windowState.toString());
                this.logger.info("WindowState '" + windowState + "' is not allowed.");
            }
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.portlet.mvc.AbstractController#handleRenderRequestInternal(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @RenderMapping
    public ModelAndView handleRenderRequestInternal(RenderRequest request, RenderResponse response) throws Exception {
        final PortalContext portalContext = request.getPortalContext();
        
        final List<String> supportedPortletModes = this.toStringList(portalContext.getSupportedPortletModes());
        final List<String> supportedWindowStates = this.toStringList(portalContext.getSupportedWindowStates());
        
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("supportedPortletModes", supportedPortletModes);
        model.put("supportedWindowStates", supportedWindowStates);
        
        return new ModelAndView("stateModeTest", model);
    }
    
    protected List<String> toStringList(Enumeration<?> enumeration) {
        final List<String> list = new LinkedList<String>();
        
        while (enumeration.hasMoreElements()) {
            final Object nextElement = enumeration.nextElement();
            list.add(nextElement.toString());
        }
        
        return list;
    }
}
