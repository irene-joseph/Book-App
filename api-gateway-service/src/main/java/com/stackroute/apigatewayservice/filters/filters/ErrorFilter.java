package com.stackroute.apigatewayservice.filters.filters;

import com.netflix.zuul.ZuulFilter;

/**
 * This filter is invoked when an error occurs while handling the request.
 */
public class ErrorFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "error";
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
        System.out.println("Inside Route Filter");

        return null;
    }

}