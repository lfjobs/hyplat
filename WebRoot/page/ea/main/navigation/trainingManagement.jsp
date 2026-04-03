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
<title>培训管理</title>

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
					           <span>培训管理</span>  
					           <ul>  
					                <li>  
							             	<span>培训管理</span>
							                   <ul>
							                   	   <li>  
									                   <a target="mainframe"   href="<%=basePath%>/ea/progressstudent/ea_getListOfViewProgressStudent.jspa?total=total" >培训项目跟踪</a>  
									               </li>  
							                   	   <li>  
									                   <a target="mainframe"   href="<%=basePath%>/ea/progressstudent/ea_getListOfViewProgressStudent.jspa?total=total" >培训跟踪</a>
									               </li>  
									               <li>  
									                   <span>入学通知</span>  
									               </li>  
									               <li>  
									                   <a target="mainframe"   href="<%=basePath%>/ea/trainingregistration/ea_getListOfViewTrainingRegistration.jspa?" >登记表</a>  
									               </li>
									               <li>  
									                   <span>学员手续进度</span>  
									               </li>  
									               <li>  
									                   <span>有效期提示报表</span>  
									               </li>
					          				   </ul>  
							               </li>  
							               <li>  
							                   <span>科一培训管理</span>
							                   <ul>
								                   <li state="closed">
							                   	   		<span>预约跟踪</span>
							                   	   		<ul>  
													       <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?dssrsubject.subjType=10">教练预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   	    <span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian=10">学员预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=10">预约汇总</a></span>
									                   	   </li>
							          				    </ul>
						                   	   	   </li>  
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/progressstudentsubject/ea_getListOfViewProgressStudentSubject.jspa?total=total&module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一培训进度&companyGroupLogo=&viewProgressStudentSubject.docstatus=01">培训进度</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科二培训管理</span>
							                   <ul> 
							                   	   <li state="closed" >
					                   	   		<span>预约跟踪</span>
					                   	   		<ul>  
											       <li>
							                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?dssrsubject.subjType=20">教练预约</a></span>
							                   	   </li>
							                   	   <li>
							                   	   	    <span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian=20">学员预约</a></span>
							                   	   </li>
							                   	   <li>
							                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=20">预约汇总</a></span>
							                   	   </li>
					          				    </ul>
					                   	    </li> 
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/progressstudentsubject/ea_getListOfViewProgressStudentSubject.jspa?total=total&module_Identifier=customer_Deal&educationalCategories=02&view_Identifier=educational_train&module_title=科二培训进度&companyGroupLogo=&viewProgressStudentSubject.docstatus=02">培训进度</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科三培训管理</span>
							                   <ul>  
								                   <li state="closed" >
						                   	   			<span>预约跟踪</span>
							                   	   		<ul>  
													       <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?dssrsubject.subjType=30">教练预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   	    <span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian=30">学员预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=30">预约汇总</a></span>
									                   	   </li>
							          				    </ul>
						                   	       </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/progressstudentsubject/ea_getListOfViewProgressStudentSubject.jspa?total=total&module_Identifier=customer_Deal&educationalCategories=03&view_Identifier=educational_train&module_title=科三培训进度&companyGroupLogo=&viewProgressStudentSubject.docstatus=03">培训进度</a></span>  
									               </li>
					          				   </ul>  
							               </li>
							               <li>  
							                   <span>科四培训管理</span>
							                   <ul>  
								                   <li state="closed" >
						                   	   		<span>预约跟踪</span>
							                   	   		<ul>  
													       <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?dssrsubject.subjType=40">教练预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   	    <span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian=40">学员预约</a></span>
									                   	   </li>
									                   	   <li>
									                   	   		<span><a target="mainframe"  href="<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=40">预约汇总</a></span>
									                   	   </li>
							          				    </ul>
						                   	       </li>
									               <li>  
									                   <span><a target="mainframe"  href="<%=basePath%>/ea/progressstudentsubject/ea_getListOfViewProgressStudentSubject.jspa?total=total&module_Identifier=customer_Deal&educationalCategories=04&view_Identifier=educational_train&module_title=科四培训进度&companyGroupLogo=&viewProgressStudentSubject.docstatus=04">培训进度</a></span>  
									               </li>
					          				   </ul>    
					               		   </li>
					               <li><span>文明学习</span></li>
					               <li state="closed"><span>接送管理</span>
					               		<ul>
					               			<li>
					               				<span>预约接送</span>
					               			</li>
					               			<li>
					               				<span>接送路线</span>
					               			</li>
					               			<li>
					               				<span>接送管理</span>
					               			</li>
					               		</ul>
					               </li>
					               <li state="closed"><span>车辆设备管理</span>
					               		<ul>
					               			<li>
					               				<span>车辆证件</span>
					               			</li>
					               			<li>
					               				<span>维修保养</span>
					               			</li>
					               			<li>
					               				<span>车辆保险</span>
					               			</li>
					               			<li>
					               				<span>车辆油汽</span>
					               			</li>
					               			<li>
					               				<span>车辆使用率</span>
					               			</li>
					               			<li>
					               				<span>车辆效率</span>
					               			</li>
					               		</ul>	
					               
					               </li>
					               <li state="closed"><span>教案管理</span>
					               		<ul>
					               			<li>
					               				<span><a target="mainframe"  href="<%=basePath%>/ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=showSubjectList?">教案设置</a></span>
					               			</li>
					               			<li>
					               				<span>教案汇总</span>
					               			</li>
					               		</ul>
					               </li>
					               <li state="closed">  
					                   <span>教练管理</span>  
					               	   <ul>
					               	   		<li><span><a target="mainframe"  href="<%=basePath%>/ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&search=search">教练信息</a></span> </li>
					               	   		<li><span>教练证件</span> </li>
					               	   		<li><span>教练排行</span> </li>
					               	   </ul>	
					               </li> 
					                <li>  
					                   <span><a target="mainframe"  href="<%=basePath%>/ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList">学员信息</a></span>  
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
