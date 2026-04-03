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
<title>教研管理</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<style type="text/css">
a:link { color:black;text-decoration:none; }
</style>
<script type="text/javascript">
	var basePath='<%=basePath%>'; 
	$(document).ready(function(){
		$("div.panel").offset({top:0,left:0});//兼容IE
	}) 
</script>
</head>
<body style="padding:0px;margin: 0px;">
	<table width="100%" cellspacing="0" cellpadding="0" "border="2">
		<tr>
			<td width="200">
				<div class="easyui-panel" style="width: 200px;height: 538px;overflow: auto;float: left;">
					<ul  id="tree" class="easyui-tree" data-options="lines:true">  
					       <li>  
					           <span>教研管理</span>  
					           <ul>  
					               <li> 
					                   <a href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?fiveClear=" target="mainframe"> 
					                  	 <span>生产产品设计</span></a>  
					               </li>  			          									
									<li class="collapsable">
											<div class="hitarea collapsable-hitarea"></div>
										<span class="folder"><a href="<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?flexbutton=jiage" target="mainframe"> 产品定价设计</a></span>
										
									</li>
									
									
									
									
					           </ul> 
					            
					       </li>
					</ul>  
				</div>
			</td>
			
			<td>
				<div style="width: 100%;height: 538px;float: left;">&nbsp;
		 			 <iframe id="mainframe" name="mainframe"
								src="<%=basePath%>/page/ea/main/driving/index.jsp"
								frameborder="0" style="width: 100%;height: 538px;overflow: auto;"></iframe>
				</div>	
			</td>
		</tr>
	</table>

</body>
</html>
