<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>职位信息</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link href="<%=basePath%>css/WFJClient/zhaopin.css" rel="stylesheet" />
  </head>
	<body>
    <div class="Jobs_Backstage">
        <div>
            <ul>
                <li class="title">人才招聘</li>
            </ul>
        </div>
        <div class="content">
            <div class="padd">
                <div style="margin-top: 10px;">
                    <ul>
                        <li class="job_info">职位信息</li>
                    </ul>
                </div>
                <div>
                    <ul>
                        <li class="job_name">${wxRecruit.recName}</li>
                        <li class="staff">人数：${wxRecruit.recNum}人</li>
                        <li class="staff">地点：${wxRecruit.recAdd}</li>
                         <li class="staff" style="width: 100%;line-height: 25px;">性质：${wxRecruit.recNature} ${wxRecruit.recNaturei} ${wxRecruit.recNatureii}</li>
                    </ul>
                </div>
                <div>
                    <ul>
                        <li class="job_info">任职要求</li>
                    </ul>
                </div>
                <div>
                    <ul class="job_claim clboth">
                        <li class="fleft">学历</li>
                        <li class="fclaim">${wxRecruit.recEdu }</li>
                        <li class="fleft">工龄</li>
                        <li class="fclaim">${wxRecruit.recExp }</li>
                    </ul>
                    <ul class="job_claim">
                        <li class="fleft">薪酬</li>
                        <li class="fclaim">${wxRecruit.recPay }</li>
                        <li class="fleft">类别</li>
                        <li class="fclaim">${wxRecruit.recInd }</li>
                    </ul>
                </div>
                <s:iterator value="beans" id="arr">  
	                <div>
	                    <ul>
	                        <li class="job_info clboth">${arr.describeName }</li>
	                    </ul>
	                </div>
	                <div style="clear: both;">
	                    <p>${arr.describeContent }</p>
	                </div>
                </s:iterator>
                
            </div>
        </div>
    </div>
	</body>
</html>
