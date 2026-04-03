<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String curOrganizationID = request.getParameter("curOrganizationID"); 
if (null==curOrganizationID) curOrganizationID = "";
%>
<html>
  <head>
    <base href="<%=basePath%>">    
    <title></title>
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
	#content {margin-top:250px;}
	#content ul{ list-style: none;}
	#content ul a{ display:block; text-decoration: none; color:black;}
	#content ul li{ float:left; margin-left:40px; text-align: center; font-size:12px;}
	#content ul li img{}
	</style>

  </head>
  
  <body>
  <div ></div>
    <div id="content">
		<ul id="ul_bottom" name="ul_bottom">			
		</ul>
	</div>
  </body>
</html>

<script type="text/javascript">	
		var basePath = "<%=basePath%>";			
		var url1 = basePath + "ea/office/sajax_ea_getOrganizationChild.jspa?curOrganizationID=" + "<%=curOrganizationID%>";		
		$.ajax({
			url: encodeURI(url1),
			type:"get",
			dataType:"json",
			success:function cbf(data){
				var res = eval("(" + data + ")");				
				var orgList = res.organizationList;
				var html = "";					
				for(var i=0;i<orgList.length;i++){					
					html += "<li><a href='" + basePath + orgList[i]["organizationUrl"] + ".jspa?organizationID=" + orgList[i]["organizationPID"] + "&curOrganizationID=" + orgList[i]["organizationID"] + "'><img src='<%=basePath%>" + "images/sytemicon/default.png'/></a>" + orgList[i]["organizationName"] + "</li>";		
			}									
				$("#ul_bottom").html(html);
			},
			error:function cbf(data){
				alert("获取子部门失败！");
			}
		});
	</script>
