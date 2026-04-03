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
<title>模拟管理</title>

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
					           <span>模拟管理</span>  
					           <ul>  
					               <li state="closed">  
					                   <span>入学模拟培训</span>
					                   <ul>  
							               <li>  
							                   <span>模拟培训进度</span>  
							               </li>  
							               <li>  
							                   <span>报表管理</span>  
							               </li>
							               <li>  
							                   <span>数据导入</span>  
							               </li> 
					          			</ul>    
					               </li>  
					               <li state="closed">  
					                   <span>科一模拟培训</span>
					                   <ul>  
							               <li>  
							                   <span>入学通知书</span>  
							               </li>  
							               <li>  
							                   <span>登记表报表</span>  
							               </li>
							               <li>  
							                   <span>短息电话通知</span>  
							               </li>
							               <li>  
							                   <span>培训</span>  
							               </li>
							               <li>  
							                   <span>毕业</span>  
							               </li>   
					          			</ul>     
					               </li>
					               <li state="closed">  
					                   <span>科二模拟培训</span>
					                   <ul>  
							               <li>  
							                   <span>入学通知书</span>  
							               </li>  
							               <li>  
							                   <span>登记表报表</span>  
							               </li>
							               <li>  
							                   <span>短息电话通知</span>  
							               </li>
							               <li>  
							                   <span>培训</span>  
							               </li>
							               <li>  
							                   <span>毕业</span>  
							               </li>   
					          			</ul>  
					               </li> 
					               <li state="closed">  
					                   <span>科三模拟培训</span>
					                   <ul>  
							               <li>  
							                   <span>入学通知书</span>  
							               </li>  
							               <li>  
							                   <span>登记表报表</span>  
							               </li>
							               <li>  
							                   <span>短息电话通知</span>  
							               </li>
							               <li>  
							                   <span>培训</span>  
							               </li>
							               <li>  
							                   <span>毕业</span>  
							               </li>   
					          			</ul>  
					               </li> 
					               <li state="closed">  
					                   <span>科四模拟培训</span> 
					                   <ul>  
							               <li>  
							                   <span>入学通知书</span>  
							               </li>  
							               <li>  
							                   <span>登记表报表</span>  
							               </li>
							               <li>  
							                   <span>短息电话通知</span>  
							               </li>
							               <li>  
							                   <span>培训</span>  
							               </li>
							               <li>  
							                   <span>毕业</span>  
							               </li>   
					          			</ul> 
					               </li> 
					               <li state="closed">  
					                   <span>模拟培训毕业</span>
					                   <ul>  
							               <li>  
							                   <span>毕业通知书</span>  
							               </li>  
							               <li>  
							                   <span>登记表报表</span>  
							               </li>
							               <li>  
							                   <span>短息电话通知</span>  
							               </li>
							               <li>  
							                   <span>毕业</span>  
							               </li>   
					          			</ul>  
					               </li>
					               <li state="closed">  
					                   <span>生产产品测试</span>
					                   <ul>  
							               <li>  
							                   <span>a</span>  
							               </li>  
					          			</ul>  
					               </li> 
					               <li state="closed">  
					                   <a href="<%=basePath%>/ea/companytestproduct/ea_getListProductdesign.jspa?" target="mainframe"><span>测试产品</span></a>
					               </li>
					               <li state="closed">  
					                   <span>测试产品记录</span>
					                   <ul>  
							               <li>
							               	   <a href="<%=basePath%>/ea/companytestproduct/ea_getCompanyUpProduct.jspa?" target="mainframe">  
							                   		<span>合格产品</span> 
							                   </a> 
							               </li>
							               <li>
							               	   <a href="<%=basePath%>/ea/companytestproduct/ea_getCompanyDownProduct.jspa?" target="mainframe">  
							                   		<span>不合格产品</span>
							                   </a>  
							               </li>  
					          			</ul>  
					               </li>
					                
					           </ul>  
					       </li>
					</ul>  
				</div>
			</td>
			
			<td>
				<div style="width: 100%;height: 538px;float: left;">
		 			 <iframe id="mainframe" name="mainframe"
								src="<%=basePath%>/page/ea/main/driving/index.jsp"
								frameborder="0" style="width: 100%;height: 538px;overflow: auto;"></iframe>
				</div>	
			</td>
		</tr>
	</table>

</body>
</html>
