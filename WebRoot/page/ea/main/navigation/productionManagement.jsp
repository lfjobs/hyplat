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
<title>教务(生产)管理</title>

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
					           <span>教务（生产）项目</span>
					           <ul>
					           		<li state="closed">
					           			<span>教研管理</span>
					           			<ul>  
					               <li>  
					                   <span>普通教学服务方案研究</span>  
					               </li>  
					               <li>  
					                   <span>会员教学服务方案研究</span>
					                   <ul>
					                   		<li><span>普通会员服务方案研究</span></li>
					                   		<li><span>VlP会员服务方案研究</span></li>
					                   </ul>  
					               </li> 
					           </ul>
					           		</li>
					           		<li state="closed">
					           			<span>模拟管理</span>
					           			<ul>  
					               <li>  
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
					               <li>  
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
					               <li>  
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
					               <li>  
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
					               <li>  
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
					               <li>  
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
					           </ul>
					           		</li>
					           		<li state="closed">
					           			<span>综合办证管理</span>
					           			<ul>  
					               <li>  
					                   <span>车管管理</span>
					                   <ul>  
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
					                 
					               <li>  
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
							                   <span>科一培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>档案管理通知</span>  
									               </li>
					          				   </ul>  
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
							                   <span>科三培训考试档案</span>
							                   <ul>  
									               <li>  
									                   <span>档案管理通知</span>  
									               </li>
					          				   </ul>  
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
					           		<li state="closed">
					           			<span>培训管理</span>
					           			<ul>  
					                <li>  
							                   <span>培训管理</span>
							                   <ul>
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
					                <li>  
										<span><a target="mainframe"  href="<%=basePath%>/ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=showSubjectList?">教学内容</a></span>  
									</li>
					               <li>  
					                   <span><a target="mainframe"  href="<%=basePath%>/ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&search=search">教练信息</a></span>  
					               </li> 
					                <li>  
					                   <span><a target="mainframe"  href="<%=basePath%>/ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList">学员信息</a></span>  
					               </li>
					               <li><span>文明学习</span></li>
					               <li><span>接送管理</span></li>
					               <li><span>车辆设备管理</span></li>
					               
					               
					           </ul>
					           		</li>
					           		<li state="closed">
					           			<span>考试归档管理</span>
					           			<ul> 
					               <li>  
					                   <span>学员档案</span>
					               	   <ul>
					               	   	  <li><span><a target="mainframe"  href="<%=basePath%>/ea/studentarchive/ea_getListSTU.jspa?">学员档案</a></span></li>	
					               	   </ul>	 
					               </li>
					               <li>
					               	   <span>测试考试</span>
					               	   <ul>
					               	   	   <li><span>预约测试</span></li>
					               	   	   <li><span>测试</span></li>
					               	   	   <li><span>预约考试</span></li>
					               	   	   <li><span>考试</span></li>
					               	   	   <li><span>合格归档</span></li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科一测试考试</span>
					               	   <ul>
					               	   	   <li><span>预约测试</span></li>
					               	   	   <li><span>测试</span></li>
					               	   	   <li><span>预约考试</span></li>
					               	   	   <li><span>考试</span></li>
					               	   	   <li><span>合格归档</span></li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科二测试考试</span>
					               	   <ul>
					               	   	   <li><span>预约测试</span></li>
					               	   	   <li><span>测试</span></li>
					               	   	   <li><span>预约考试</span></li>
					               	   	   <li><span>考试</span></li>
					               	   	   <li><span>合格归档</span></li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科三测试考试</span>
					               	   <ul>
					               	   	   <li><span>预约测试</span></li>
					               	   	   <li><span>测试</span></li>
					               	   	   <li><span>预约考试</span></li>
					               	   	   <li><span>考试</span></li>
					               	   	   <li><span>合格归档</span></li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>科四测试考试</span>
					               	   <ul>
					               	   	   <li><span>预约测试</span></li>
					               	   	   <li><span>测试</span></li>
					               	   	   <li><span>预约考试</span></li>
					               	   	   <li><span>考试</span></li>
					               	   	   <li><span>合格归档</span></li>
					               	   </ul>	
					               </li>
					               <li>
					               	   <span>合格归档</span>
					               	   <ul>
					               	   	   <li><span>合格归档</span></li>
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
