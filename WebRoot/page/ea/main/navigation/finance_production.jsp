<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.util.SpringContextUtil"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="hy.plat.service.BaseBeanService"%>
<%@page import="hy.ea.bo.Company"%>
<%@page import="java.util.Map"%>
<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@page import="hy.ea.bo.CAccount"%>
<%@page import="hy.plat.service.impl.BaseBeanServiceImpl"%>
<%@page import="org.hibernate.impl.SessionFactoryImpl"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@page import="org.hibernate.Query"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>财务生产管理</title>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div>
      <table style="border:0;text-align:left;padding: 0;margin:0" class="table03">
        <tr>
          <td><table>
          
               <tr>
                <td><table >
                    <tr>
                      <td width="120" align="center" class="colors">
                      <div class="na_back_img_ks"></div>
                      <div class="center_a"><strong>生产合同管理</strong></div>
                      </td>
                      <td width="55" align="center" class="colors"><div class="na_back_img_jt_hx"></div></td>
                       <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td width="110" align="center" class="colors" >
                             		<div class="na_back_img" onclick=""></div>
                             		<div class="center_a"><span>生产合同流转</span></div>
                                    </td>
                                   
                                  <td width="110" align="center" class="colors">
										<div class="na_back_img" onclick=""></div>
                             			<div class="center_a"><span>生产合同查询</span></div>
                            </td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
            </tr>
            <tr>
                <td><table >
                    <tr>
                      <td width="120" align="center" class="colors">
                      <div class="na_back_img_ks"></div>
                      <div class="center_a"><strong>公司财务管理</strong></div>
                      </td>
                      <td width="55" align="center" class="colors"><div class="na_back_img_jt_hx"></div></td>
                       <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td width="110" align="center" class="colors" >
                             		<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_h1.jsp'"></div>
                             		<div class="center_a"><span>公司财务管理</span></div>
                                    </td>
                                   
                                  <td width="110" align="center" class="colors">
										<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_d.jsp'"></div>
                             			<div class="center_a"><span>公司税务管理</span></div>
                            </td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
            </tr>
            <tr>
                <td><table >
                    <tr>
                      <td width="120" align="center">
                      	<div class="na_back_img_ks"></div>
                      	<div class="center_a"><strong> 集团财务管理</strong></div>
                       </td>
                      <td width="55" align="center"><div class="na_back_img_jt_hx"></div></td>
                      <td ><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                              <td width="110" align="center">
										<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_l1.jsp'"></div>
                             			<div class="center_a"><span>集团财务管理</span></div>
									
								</td>
                                <td width="110" align="center">
										<div class="na_back_img"></div>
                             			<div class="center_a"><span>集团税务管理</span></div>
								</td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              
          </table></td>
        </tr>
      </table>
    </div>
</body>
</html>