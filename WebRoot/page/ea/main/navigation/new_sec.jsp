<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>下级菜单管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<%@ taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
	<div style="text-align:center;padding-top: 16%">
		<table style="text-align: center;border: 0;padding: 0;width:90%;">
			<tr>
			<s:iterator value="menus">
			<td>
			<script>
			var id='${modalid}',url='${murl }',menuState='${menuState }';
			var ylurl="javascript:void(0)";
		if(url!=""){
			if(url.indexOf("getCSec")>0){
				ylurl="<%=basePath%>" + url + "secID=" + id+"&menuType="+menuState;
			}else if(url.indexOf("jsp")>0){
				ylurl="<%=basePath%>"+url;
			}else{
				ylurl="<%=basePath%>" + url +".jspa";
			}
		}
		var htmltag=new Array();
		htmltag.push("<div class=\"na_back_img\" onclick=\"document.location.href=");
		htmltag.push("'"+ylurl+"'\"></div>");
		document.write(htmltag.join(""));
			</script>
					<div class="center_a">${modalname}</div></td>
			</s:iterator>
			</tr>
		</table>
	</div>

</body>
</html>