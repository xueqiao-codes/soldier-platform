package org.soldier.platform.oa.web.controller;

import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.web_framework.CMethodController;

public class HttpReqDumper extends CMethodController {
    public void dump() throws Exception{
        AppLog.i("Headers:");
        echo("Headers:");
        
        Enumeration<?> headerNames = getServletRequest().getHeaderNames();
        while(headerNames.hasMoreElements()) {
            StringBuilder headerBuilder = new StringBuilder(128);
            String headerName = headerNames.nextElement().toString();
            headerBuilder.append("  ").append(headerName).append(" =");
            Enumeration<?> headerValues = getServletRequest().getHeaders(headerName);
            while(headerValues.hasMoreElements()) {
                headerBuilder.append(" ").append(headerValues.nextElement().toString());
            }
            AppLog.i(headerBuilder.toString());
            print(headerBuilder.toString());
        }
        
        String content = IOUtils.toString(super.getServletRequest().getInputStream());
        AppLog.i("Content:");
        echo("Content:");
        
        AppLog.i(content);
        echo(content);
    }
}
