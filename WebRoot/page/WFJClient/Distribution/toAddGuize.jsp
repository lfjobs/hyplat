<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>预设数据</title>
</head>
<body>
<form action="ea/jifen/ea_saveGuize.jspa" method="post">
公司ID：<s:textfield name="gzCompany" ></s:textfield>不填写默认世统公司ID<br/>
<s:textfield name="gzName"></s:textfield>规则名称<br/>
<s:textfield name="gzScore"></s:textfield>规则分数<br/>
<s:textfield name="gzOrder"></s:textfield>规则排序<br/>
<s:textfield name="gzType" value="A"></s:textfield>A加或者M 减<br/>
<s:textfield name="gzUnit" value="0"></s:textfield>规则类型0固定值，1百分比<br/>
<s:submit name="submit" label="submit"></s:submit>
</form>


</body>
</html>