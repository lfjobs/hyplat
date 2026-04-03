<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>教务（培训管理）</title>
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	<style type="text/css">
		table.intervalTd{
			border-spacing: 25px 5px;
		}
	</style>
	 <%--
  param:  theModule  describe：区别模块   parameters：培训计时 00,分车管理 01,预约培训管理02,培训管理03,接送管理 04,约考管理05,考试成绩管理06,合格管理07
  --%>
  </head>
<body>
<c:set  value="${param.docstatusRequest=='01'?'科一':param.docstatusRequest=='02'?'科二':param.docstatusRequest=='03'?'科三':param.docstatusRequest=='04'?'科四':''}" var="title2" />
<c:set  value="${param.docstatusRequest=='01'?10:param.docstatusRequest=='02'?20:param.docstatusRequest=='03'?30:param.docstatusRequest=='04'?40:''}" var="subjType" />

<table  border="0">
  <tr align="center">
    <td><div class="na_back_img_ks"></div><div class="center_a"><strong>培训管理</strong></div></td>
    <td>
        <table >
                <tr>
                  <td><div class="na_back_img_jt_xs"></div></td>
                  <td><table class="intervalTd">
                    <tr>
					    <%--<td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/academicadmin/ea_getCompanyListForIncumbent.jspa?companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }教师管理</div></td>
					    --%>
					    <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=showSubjectList'"></div><div class="center_a">${title2 }科目信息管理</div></td>
					    <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&search=search'"></div><div class="center_a">${title2 }教练管理</div></td>
					    <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList'"></div><div class="center_a">${title2 }学员管理</div></td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><div class="na_back_img_jt_xs"></div></td>
                  <td>
                  <table class="intervalTd">
                    <tr>
 					  <%-- 旧版本预约培训管理 （无任何作用）
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}预约培训管理&theModule=02&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }预约培训管理</div></td>
                      --%>
                      <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?dssrsubject.subjType=${subjType}'"></div><div class="center_a"> ${title2 }教练预约管理</div></td>
                      <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian=${subjType}'"></div><div class="center_a"> ${title2 }学员预约管理</div></td>		
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?dssrsubject.subjType=${subjType}'"></div><div class="center_a">${title2 }预约汇总管理</div></td>
					  	
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><div class="na_back_img_jt_xs"></div></td>
                  <td>
                  <table class="intervalTd">
                    <tr>
                      <td><div class="na_back_img"></div><div class="center_a">${title2 }教学内容管理</div></td>	
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}教学进度管理&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }教学进度管理</div></td>
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}学时管理&theModule=00&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a">${title2 }学时管理</div></td>
                    </tr>
                  </table></td>
                </tr>
                
                <tr>
                  <td><div class="na_back_img_jt_xx"></div></td>
                  <td>
                  <table class="intervalTd">
                    <tr>
                      <td><div class="na_back_img" ></div><div class="center_a">${title2 }教学设备管理</div></td>
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}教学场地管理&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }教学场地管理</div></td>
                      <td><div class="na_back_img" ></div><div class="center_a">${title2 }教室管理</div></td>
                     </tr>
                  </table></td>
                </tr>
                
                <tr>
                  <td><div class="na_back_img_jt_xx"></div></td>
                  <td>
                  <table class="intervalTd">
                    <tr>
                      
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}安全管理&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a">${title2 }安全管理</div></td>
					  <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}接送管理&theModule=04&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }接送管理</div></td>
					  <td><div class="na_back_img" ></div><div class="center_a">${title2 }文明教育管理</div></td>
					  
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td><div class="na_back_img_jt_xx"></div></td>
                  <td>
                  <table class="intervalTd">
                    <tr>
                      <td><div class="na_back_img" ></div><div class="center_a">${title2 }VIP服务管理</div></td>
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/appointment/ea_getListDtDrivingAppointmentRecordAll.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}预约记录管理&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a">${title2 }预约记录管理</div></td>
                      <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/training/ea_getListDtDrivingTrainingInforAll.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}培训记录管理&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a">${title2 }培训记录管理</div></td>
                     </tr>
                  </table></td>
                </tr>
				<tr>
				  <td><div class="na_back_img_jt_xx"></div></td>
				  <td>
				  <table class="intervalTd">
					<tr>      
					  <td><div class="na_back_img"></div><div class="center_a">${title2 }制卡管理</div></td>
					  <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}分车管理&theModule=01&companyGroupLogo=${param.companyGroupLogo}'"></div><div class="center_a"> ${title2 }分车管理</div></td>	                   
                      <td><div class="na_back_img" ></div><div class="center_a"> ${title2 }班级管理</div></td>
                      
					 </tr>
				  </table></td>
				</tr>
				<tr>
				  <td><div class="na_back_img_jt_xx"></div></td>
				  <td>
				  <table class="intervalTd">
					<tr>      
					  <td><div class="na_back_img" onClick="document.location.href='<%=basePath%>/ea/ddsrcoachManager/isrecommend_ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&view_Identifier=educational_train&educationalCategories=${param.educationalCategories }&module_title=${param.module_title}推荐管理&theModule=01&companyGroupLogo=${param.companyGroupLogo}&searchIsrecommend=1&search=search'"></div><div class="center_a"> ${title2 }推荐管理</div></td>	                   
					 </tr>
				  </table></td>
				</tr>
           </table>
    </td>
   </tr>
</table>
</body>
</html>
