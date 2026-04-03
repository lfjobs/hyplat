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
<title>综合办证管理</title>

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
		$("a").click(function(){
			$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"538px"}).appendTo("#ieftDiv");     
            $("<div class=\"datagrid-mask-msg\"></div>").html("正在努力加载中,请稍等....").appendTo("#ieftDiv").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:(538 - 45) / 2});     
		})
		$("#mainframe").load(function(){
			 $("#ieftDiv").find("div.datagrid-mask-msg").remove();
             $("#ieftDiv").find("div.datagrid-mask").remove();
		});
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
					           <span>综合办证管理</span>  
					           <ul> 
					           		<li>
					           			<span>学校管理</span>
					           			<ul>
					           				<li><span>证件管理</span>
					           				<ul>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?search=search&docstatus=01&identifier=dataIsComplete&dtDrivingPrincipal.dataIsComplete=00">证件齐全学员</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?search=search&docstatus=01&identifier=dataIsComplete&dtDrivingPrincipal.dataIsComplete=01">证件不齐学员</a></span>  
									               </li>
					          				   </ul>
					           				 </li>
					           				<%-- <li state="closed"><span>科一管理</span>
					           				<ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=01&dtDrivingTest.examtype=01&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>
					           				 </li>
					           				<li state="closed"><span>科二管理</span>
					           				<ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=02&dtDrivingTest.examtype=02&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=02&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>
					          				    </li>
					           				<li state="closed"><span>科三管理</span> 
					           				<ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=03&dtDrivingTest.examtype=03&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=03&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>
					           				</li>
					           				<li state="closed"><span>科四管理</span>
					           				<ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=04&dtDrivingTest.examtype=04&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>
					           				 </li>
					           				<li><span>归档管理</span> </li> --%>
					           			</ul>
					           		</li> 
					               <li>  
					                   <span>车管管理</span>
					                   <ul>
					                   	   <li>  
					                   	   		<span>报开学管理</span>
					                   	   		<ul>
					                   	   			<li>
					                   	   				<span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=04&identifier=baokaixue">报开学管理</a></span>
					                   	   			</li>
					                   	   		</ul>
							               </li>
							               <li>  
							                   <span>入学培训考试进度</span>
							                   <ul>  
									               <li>  
									                   <span>入学培训考试进度</span>  
									               </li>  
									               <li>  
									                   <span>有效期提示报表</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科一培训考试</span>
							                   <ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=01&dtDrivingTest.examtype=01&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科二培训考试</span>
							                   <ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=02&dtDrivingTest.examtype=02&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=02&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科三培训考试</span>
							                   <ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=03&dtDrivingTest.examtype=03&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=03&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科四培训考试</span>
							                   <ul>  
									               <li>  
									                   <span>入考通知</span>  
									               </li>  
									               <li>  
									                   <span>结果通知</span>  
									               </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=04&dtDrivingTest.examtype=04&search=search&other=other&companyGroupLogo=">合格率</a></span>  
									               </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=04&other=other&companyGroupLogo=">成绩报表</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>出证管理</span>
							                   <ul> 
									               <li>  
									                   <span>出证通知</span>  
									               </li>
					          				   </ul>  
							               </li>  
					          			</ul>    
					               </li>
					                 
					               <li state="closed">  
					                   <span>运管管理</span>
					                   <ul>  
							               <li>  
							                   <span>入学培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>入学考试档案进度</span>  
									               </li>  
									               <li>  
									                   <span>有效期提示报表</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科一报运管</span>
							               </li>  
							               <li>  
							                   <span>科一培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>档案管理通知</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科二报运管</span>
							               </li>
							               <li>  
							                   <span>科二培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>科三培训考试档案</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科三报运管</span>
							               </li>
							               <li>  
							                   <span>科三培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>档案管理通知</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科四报运管</span>
							               </li> 
							               <li>  
							                   <span>科四培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>档案管理通知</span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>毕业证档案</span>
							                   <ul> 
									               <li>  
									                   <span>毕业证档案通知</span>  
									               </li>
					          				   </ul>  
							               </li>  
					          			</ul>    
					               </li>
					                
					           </ul>  
					       </li>
					</ul>  
				</div>
			</td>
			
			<td>
				<div style="width: 100%;height: 538px;float: left;position:relative;" id="ieftDiv">
		 			 <iframe id="mainframe" name="mainframe"
								src="<%=basePath%>/page/ea/main/driving/index.jsp"
								frameborder="0" style="width: 100%;height: 538px;overflow: auto;"></iframe>
				</div>	
			</td>
		</tr>
	</table>

</body>
</html>
