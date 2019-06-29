package com.ronglian.kangrui.saas.research.gate.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class CrossDomainFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        final String method = request.getMethod();

        HttpServletResponse httpServletResponse = ctx.getResponse();

        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Kr-D-Token, Accept,X-Requested-With");

        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("X-Powered-By","Jetty");

        httpServletResponse.setHeader( "Pragma", "no-cache" );

        httpServletResponse.addHeader( "Cache-Control", "must-revalidate" );

        httpServletResponse.addHeader( "Cache-Control", "no-cache" );

        httpServletResponse.addHeader( "Cache-Control", "no-store" );

        httpServletResponse.setDateHeader("Expires", 0);

        //httpServletResponse.setHeader("P3P","CP=\"NON DSP COR CURa ADMa DEVa TAIa PSAa PSDa IVAa IVDa CONa HISa TELa OTPa OUR UNRa IND UNI COM NAV INT DEM CNT PRE LOC\"");
        httpServletResponse.addHeader("P3P", "CP=CAO PSA OUR");

        if (method.equals("OPTIONS")){
            ctx.setResponseStatusCode(200);
            if (ctx.getResponseBody() == null) {
                ctx.setResponseBody("{\"result\":\"response body is null!\"}");
                ctx.setSendZuulResponse(false);
            }
            return null;
        }
        return null;
    }
}
