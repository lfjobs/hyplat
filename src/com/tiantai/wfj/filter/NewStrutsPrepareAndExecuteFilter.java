package com.tiantai.wfj.filter;

import org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public class NewStrutsPrepareAndExecuteFilter extends
		StrutsPrepareAndExecuteFilter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {		
		
		HttpServletRequest request = (HttpServletRequest)req;
		String url = request.getRequestURI();
		if (url.contains("ueditor/jsp/")){ //UEditor上传文件
			chain.doFilter(req, res);
		}else
		{
			super.doFilter(req, res, chain);
		}
		
	}
}
