<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>集团办公室管理</title>
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	font-size: 12px;
}

a:link,a:visited,a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

a {
	color: #333;
	text-decoration: none;
}

a:active {
	color: #002f76;
	font-weight: bold;
}

.roundedCorners {
	width: 750px;
	padding: 10px;
	margin: auto;
	background-color: #FFF;
	border: 1px solid #CCE6F1;
	/* Do rounding (native in Safari, Firefox and Chrome) */
	-webkit-border-radius: 6px;
	-moz-border-radius: 6px;
}
</style>
		<style type="text/css">
<!--
.table03 {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
	margin: 10px 0 0 10px；
}

.table03 th {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
}

.table03 td {
	font-size: 14px;
	line-height: 17px;
	font-weight: bold;
}

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.colors{
	border-bottom:1px dashed #FF0000;
}
-->
</style>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
		<script type="text/javascript">
			addEvent(window, 'load', initCorners);
			function initCorners() {
				var setting = {
					tl: { radius: 6 },
					tr: { radius: 6 },
					bl: { radius: 6 },
					br: { radius: 6 },
					antiAlias: true
				}
				curvyCorners(setting, ".roundedCorners");
			}

			//function gd(op){
			//	if(op==1){
			//		document.getElementById('sz').scrollTop =0;
			//	}
			//	if(op==2){
			//		document.getElementById('sz').scrollTop = document.getElementById('sz').scrollHeight;
			//	}
			//	if(op==3){
			//		document.getElementById('sz').scrollTop = document.getElementById('sz').scrollHeight;
			//	}
              
			//}
	</script>
</head>
<body >

<%--	<div style="position: absolute;top: 1%;left: 1%;z-index: 4 ; background-color:#e1ecfc; filter : Alpha(opacity=50);">
    <input type="button" id="t1" onclick="gd(1);" value="售前" />&nbsp;&nbsp;<input type="button" onclick="gd(2);"  id="t2" value="售中" />&nbsp;&nbsp;<input type="button" onclick="gd(3);"  id="t3" value="售后" /></div>
		--%>
	<%--制作下拉框  style="overflow: scroll;height:580px;"--%>
    ·
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table height="90" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03" style="margin-top: 5px">
                    <tr>
                      <td width="98" rowspan="2" align="center">
                     	<div class="na_back_img_ks"></div>
                      	<div class="center_a"><strong>集团办公室管理</strong></div>
                        </td>
                      <td width="80" height="62" align="center">
                      	<div class="na_back_img_jt_hx"></div>
					  </td>
                      <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0"
										height="132px">
                          <tr>
                             
                             <td width="120" align="center">
								<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/strategic_planning.jsp'"></div>
                      			<div class="center_a"><span>集团规划管理</span></div>
                             </td>
                             <td width="100" align="center">
                             	<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/work_bills.jsp'"></div>
                      			<div class="center_a"><span>集团行政管理</span></div>
                             </td>
                             <td width="100" align="center">
                             	<div class="na_back_img" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/information_management.jsp'"></div>
                      			<div class="center_a"><span>集团信息管理</span></div>
                             </td>
                             <td width="100" align="center">
                             	<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_management.jsp'"></div>
                      			<div class="center_a"><span>集团后勤管理</span></div>
                             </td>
                             <td width="100" align="center">
                             	<div class="na_back_img" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/office_o.jsp'"></div>
                      			<div class="center_a"><span>集团督查管理</span></div>
                             </td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td width="80" align="center"><img src="<%=basePath%>images/jiatou_02.gif" width="56"
										height="51" border="0" /> </td>
                    </tr>
                </table></td>
        </tr>
      </table>
</body>
</html>