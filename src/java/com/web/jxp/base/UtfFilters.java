package com.web.jxp.base;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class UtfFilters implements Filter
{
	FilterConfig filterConfig = null;
	public void init(FilterConfig filterConfig) throws ServletException
    {
		this.filterConfig = filterConfig;
	}

	public void destroy()
    {
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException
    {
        servletRequest.setCharacterEncoding("UTF-8");
        filterChain.doFilter(servletRequest, servletResponse);
	}
}