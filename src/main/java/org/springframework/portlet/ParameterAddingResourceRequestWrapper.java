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

package org.springframework.portlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.portlet.ResourceRequest;

/**
 * @author Eric Dalquist
 * @version $Revision$
 */
public class ParameterAddingResourceRequestWrapper extends ParameterAddingPortletRequestWrapper implements ResourceRequest {
    private final ResourceRequest request;
    
    public ParameterAddingResourceRequestWrapper(ResourceRequest request) {
        super(request);
        this.request = request;
    }

    @Override
    public InputStream getPortletInputStream() throws IOException {
        return this.request.getPortletInputStream();
    }

    @Override
    public void setCharacterEncoding(String enc) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(enc);
    }

    @Override
    public BufferedReader getReader() throws UnsupportedEncodingException, IOException {
        return this.request.getReader();
    }

    @Override
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.request.getContentType();
    }

    @Override
    public int getContentLength() {
        return this.request.getContentLength();
    }

    @Override
    public String getMethod() {
        return this.request.getMethod();
    }

    @Override
    public String getETag() {
        return this.request.getETag();
    }

    @Override
    public String getResourceID() {
        return this.request.getResourceID();
    }

    @Override
    public Map<String, String[]> getPrivateRenderParameterMap() {
        return this.request.getPrivateParameterMap();
    }

    @Override
    public String getCacheability() {
        return this.request.getCacheability();
    }
}
