<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+request.getServerName()+":"+request.getServerPort()+ path + "/";
%>
<script src="<%=basePath %>/js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/flexigrid_blue.css"/>
<script type="text/javascript" src="<%=basePath %>js/flexigrid.js"></script>

<script type="text/javascript" src="<%=basePath %>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath %>js/163css/css/163css.css"/>
<script type="text/javascript" src="<%=basePath %>js/163css/js/163css.js"></script>

<script type="text/javascript" src="<%=basePath %>js/dropdown/extendPageMenu.js"></script>
<c:set value="<%=basePath %>" var="basePath"></c:set>