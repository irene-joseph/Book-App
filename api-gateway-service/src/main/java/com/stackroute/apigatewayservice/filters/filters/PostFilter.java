package com.stackroute.apigatewayservice.filters.filters;

import com.netflix.zuul.ZuulFilter;

/**
 * This filter is invoked after the request has been routed.
 */
public class PostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
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
        System.out.println("Inside Response Filter");

        return null;
    }

}