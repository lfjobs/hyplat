<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	
	String domain=request.getScheme() + "://"
	        + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
	    if(domain.contains(".com")){
			domain=domain.substring(0,domain.lastIndexOf(".com")+4);
			request.setAttribute("domain", domain);
			String url=domain+"/ea/hdocument/ea_toPage.jspa";
			response.sendRedirect(url+"?domain="+domain);
		}else{
			//response.sendRedirect(domain+"page/ea/index.jsp");
			response.sendRedirect(domain+"/page/WFJClient/pc/pc_login.jsp");
		}    
	    
	
%>
