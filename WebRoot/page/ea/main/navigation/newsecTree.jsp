<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>树形菜单展示导航</title>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<script type="text/javascript">
             var basePath='<%=basePath%>';  
             /* $(document).ready(function(){
            	 
            	 $("#navigation").treeview({
					 persist: "location",
				      collapsed: true,
				      unique: true
				}); 

            	}); */
  
        </script>
		<style type="text/css">
#qh_sw {
	width: 15%;
	border: 1px solid #DAE7F6;
}

.treeview li {
	margin: 0;
	padding: 1px 0 1px 16px;
}


</style>
	</head>
	<body>
		<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
				<td id="qh_sw" style="width: 15%" valign="top">
					<div class="qh_gg_nav">
						&nbsp; <span id="frametitle"></span>
					</div>
					<ul id="navigation" style="width: 180px;height:300px;" class="filetree">
						<s:iterator value="menus">
						<s:if test="murl.contains('getCSec')">
						<li><a target="mainframe" 
						href="<%=basePath%>${murl }secID=${modalid}&menuType=${menuState }">
						<span class="folder">${modalname}</span></a></li>
						</s:if>
						<s:else>
						<li><a target="mainframe" 
						href="<%=basePath%>${murl }">
						<span class="folder">${modalname}</span></a></li>
						</s:else>
						</s:iterator>
					</ul>
				</td>
				<td style="width: 85%;" valign="top"><iframe id="mainframe" name="mainframe"
						    src=""
							frameborder="0" style="width: 100%;height:100%"></iframe></td>

			</tr>
			</table>
		</div>
		<script type="text/javascript">
			$(function() {
				$(window).resize(function() {
					setTimeout(function() {
						$("#navigation").height($(window).height() - 30);
					}, 100);
				});
				$("#navigation").height($(window).height() - 30);
				$("#mainframe").css({
					"height" : $(window).height() + "px"
				});
			});
			function changemenu(id,url,menuState){
			if(url!=""){
				document.location.href="<%=basePath%>" + url + "secID=" + id+"&menuType="+menuState;
				}
			}
		</script>
	</body>


</html>
