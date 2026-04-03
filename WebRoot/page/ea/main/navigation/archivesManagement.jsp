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
<title>考试归档管理</title>

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
					           <span>考试归档管理</span>  
					           <ul> 
					               <%-- <li>  
					                   <span>学员档案</span>
					               	   <ul>
					               	   	  <li><span><a target="mainframe"  href="<%=basePath%>/ea/studentarchive/ea_getListSTU.jspa?">学员档案</a></span></li>	
					               	   </ul>	 
					               </li> --%>
					               <li>
					               	   <span>测试考试</span>
					               	   <ul>
					               	   		<li><span>学校测试</span>
					               	   			<ul >
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total&identifier=drivingListYueKaoTestOfList">预约测试</a></span></li>
					               	   	  		    <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsOfTestList.jspa?extensionStaffCoach=extensionStaffStudent">测试</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?total=total&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=不合格">测试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?total=total&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=合格">测试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysisOfTest.jspa?search=search&other=other&companyGroupLogo=">测试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   		<li><span>公安车管考试</span>
					               	   			<ul>
					               	   				 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?total=total">预约考试</a></span></li>
							               	   	     <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?extensionStaffCoach=extensionStaffStudent">考试</a></span></li>
					               	   				 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?total=total&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testresult=01">考试不合格</a></span></li>
					               	   	   			 <li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?studentstatus=07&title=05">考试合格归档</a></span></li>
					               	   	   			 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?search=search&other=other&companyGroupLogo=">考试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科一测试考试</span>
					               	   <ul>
					               	   		<li state="closed"><span>学校测试</span>
					               	   			<ul >
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=14&identifier=drivingListYueKaoTestOfList">预约测试</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsOfTestList.jspa?docstatus=01&extensionStaffCoach=extensionStaffStudent">测试</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=01&studentstatus=02&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=不合格">测试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=01&studentstatus=02&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=合格">测试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysisOfTest.jspa?docstatus=01&dtDrivingTest.examtype=01&search=search&other=other&companyGroupLogo=">测试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   		<li state="closed"><span>公安车管考试</span>
					               	   			<ul>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=14">预约考试</a></span></li>
					               	   	  			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=01&extensionStaffCoach=extensionStaffStudent">考试</a></span></li>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=01&studentstatus=02&title=14&search=search&dtDrivingPrincipalType.testresult=01">考试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=07&title=05">考试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=01&dtDrivingTest.examtype=01&search=search&other=other&companyGroupLogo=">考试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   </ul>
					               </li>
					               <li>
					               	   <span>科二测试考试</span>
					               	   <ul>
					               	   		<li state="closed"><span>学校测试</span>
					               	   			<ul >
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList">预约测试</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsOfTestList.jspa?docstatus=02&extensionStaffCoach=extensionStaffStudent">测试</a></span></li>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=02&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=不合格">测试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=02&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=合格">测试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysisOfTest.jspa?docstatus=02&dtDrivingTest.examtype=02&search=search&other=other&companyGroupLogo=">测试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   		<li state="closed"><span>公安车管考试</span>
					               	   			<ul>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=04&title=14">预约考试</a></span></li>
							               	   	    <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=02&extensionStaffCoach=extensionStaffStudent">考试</a></span></li>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=02&studentstatus=04&title=14&search=search&dtDrivingPrincipalType.testresult=01">考试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=07&title=05">考试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=02&dtDrivingTest.examtype=02&search=search&other=other&companyGroupLogo=">考试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   </ul>
					               </li>
					               <li>
					               	   <span>科三测试考试</span>
					               	   <ul>
					               	   		<li state="closed"><span>学校测试</span>
					               	   			<ul >
					               	   				 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList">预约测试</a></span></li>
					               	   	   			 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsOfTestList.jspa?docstatus=03&extensionStaffCoach=extensionStaffStudent">测试</a></span></li>
					               	   				 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=03&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=不合格">测试不合格</a></span></li>
					               	   	   			 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=03&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=合格">测试合格归档</a></span></li>
					               	   	   			 <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysisOfTest.jspa?docstatus=03&dtDrivingTest.examtype=03&search=search&other=other&companyGroupLogo=">测试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   		<li state="closed"><span>公安车管考试</span>
					               	   			<ul>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=04&title=14">预约考试</a></span></li>
							               	   	    <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=03&extensionStaffCoach=extensionStaffStudent">考试</a></span></li>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=03&studentstatus=04&title=14&search=search&dtDrivingPrincipalType.testresult=01">考试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=07&title=05">考试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=03&dtDrivingTest.examtype=03&search=search&other=other&companyGroupLogo=">考试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科四测试考试</span>
					               	    <ul>
					               	   		<li state="closed"><span>学校测试</span>
					               	   			<ul >
					               	   				  <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList">预约测试</a></span></li>
					               	   	  			  <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsOfTestList.jspa?docstatus=04&extensionStaffCoach=extensionStaffStudent">测试</a></span></li>
					               	   				  <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=04&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=不合格">测试不合格</a></span></li>
					               	   	   			  <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=04&studentstatus=04&title=14&identifier=drivingListYueKaoTestOfList&search=search&dtDrivingPrincipalType.testOtherResult=合格">测试合格归档</a></span></li>
					               	   	   			  <li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysisOfTest.jspa?docstatus=04&dtDrivingTest.examtype=04&search=search&other=other&companyGroupLogo=">测试合格率排行榜</a></span></li>	
					               	   			</ul>
					               	   		</li>
					               	   		<li state="closed"><span>公安车管考试</span>
					               	   			<ul>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=04&title=14">预约考试</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_getStatisticsList.jspa?docstatus=04&extensionStaffCoach=extensionStaffStudent">考试</a></span></li>
					               	   				<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearch.jspa?docstatus=04&studentstatus=04&title=14&search=search&dtDrivingPrincipalType.testresult=01">考试不合格</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=07&title=05">考试合格归档</a></span></li>
					               	   	   			<li><span><a target="mainframe"  href="<%=basePath%>/ea/driving/ea_toSearchAnalysis.jspa?docstatus=04&dtDrivingTest.examtype=04&search=search&other=other&companyGroupLogo=">考试合格率排行榜</a></span></li>
					               	   			</ul>
					               	   		</li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>合格归档</span>
					               	   <ul>
					               	   	   <li><span><a target="mainframe"  href="<%=basePath%>ea/driving/ea_getDrivingList.jspa?total=total&studentstatus=07&title=05">合格归档</a></span></li>
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
