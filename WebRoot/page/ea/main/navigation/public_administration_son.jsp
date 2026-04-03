<%@ page import="java.net.URLEncoder"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>办公室行政管理</title>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>

<script type="text/javascript">
             var basePath='<%=basePath%>';  
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

.numcss {
	color: red;
}
</style>

	</head>
	<body>
	<div class="main_main">
			<table width="100%" cellspacing="0" cellpadding="0" "border="2">
				<tr>
					<td id="qh_sw" style="width: 15%" valign="top">
						<div class="qh_gg_nav">
							&nbsp;
							<span id="frametitle">办公室行政管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<%--<li>
								<span class="folder" id="tit">办公室行政管理</span>
										--%><ul>
										     <li>
												<a href="<%=basePath%>ea/informbills/ea_getOfficeFileManager.jspa?" target="admin1"><span
													class="folder">行政文书管理</span> </a>
											</li>
											<li>
												<span
													class="folder" id="unex">现场会议(会议管理)</span>
													<ul>
														<li>
														<a href="<%=basePath%>ea/dtconferenceorg/ea_getAllDtconOrg.jspa?" target="admin1"><span
														class="file">会务机构人员配备</span> </a>
														</li>
														<li><a href="<%=basePath%>ea/dtconference/ea_getDtconferenceList.jspa?newState=00" target="admin1"><span
														class="file">会议准备阶段</span> </a></li>
														<li><a href="<%=basePath%>ea/dtconference/ea_getDtconferenceList.jspa?newState=01" target="admin1"><span
														class="file">正式会议阶段</span> </a></li>
														<li><a href="<%=basePath%>ea/dtconference/ea_getDtconferenceList.jspa?newState=02" target="admin1"><span
														class="file">会议闭幕阶段</span> </a></li>
													</ul>
											</li>
											<li>
												<a href="<%=basePath%>ea/fileManage/ea_getaFileManageList.jspa?type=2" target="admin1"><span
													class="file">视频会议</span> </a>
											</li>
											<li>
												<a  target="admin1" href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=MeetRecord&d=<%=Math.random()%>"><span
													class="file">会议记录</span> </a>
											</li>
											<li>
												<a href="<%=basePath%>ea/meetingroom/ea_getMyRoomOrder.jspa" target="admin1"><span
													class="file">会议室预约管理</span> </a>
											</li>
											
											<li>
												<a href="<%=basePath%>ea/smeeting/ea_getStaffMeeingList.jspa" target="admin1"><span
													class="file" id="unex">员工会议管理</span></a>
											</li>
											
											<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=contract&d=0.17060001260558144" target="admin1"><span
													class="folder">合同管理</span> </a>
													
													<ul>
													<li><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=renshi&d=0.17060001260558145" target="admin1"><span
														class="file">人事合同流转</span> </a></li>
														<li>
														<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=bangongshi&d=0.17060001260558146" target="admin1"><span
														class="file">办公室合同流转</span> </a>
														</li>
														<li><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=finance&d=0.17060001260558147" target="admin1"><span
														class="file">财务合同流转</span> </a></li>
														<li><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=shengchan&d=0.17060001260558148" target="admin1"><span
														class="file">生产合同流转</span> </a></li>
														<li><a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=yingxiao&d=0.17060001260558149" target="admin1"><span
														class="file">营销合同流转</span> </a></li>
													</ul>
											</li>
											<%-- <li>
												<a  target="admin1"><span
													class="file">办公室合同查询</span> </a>
											</li> --%>
											<li>
												<a href="<%=basePath%>ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?" target="admin1"><span
													class="file">电子印章管理</span> </a>
											</li>
											
											<%-- <li>
												<a  href="<%=basePath%>/ea/enterprisestamp/ea_getListGeneralStamp.jspa?'" target="admin1"><span
													class="file" id="unex">普通印章管理</span></a>
											</li> --%>
											<li>
												<a  target="admin1"><span
													class="file">企业章程管理</span> </a>
											</li>
											<li>
												<a  target="admin1"><span
													class="file">企业制度</span> </a>
											</li>
											<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=InduReg&d=0.5275021608817068" target="admin1"><span
													class="file">行政法规</span> </a>
											</li>
											<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=CountReg&d=0.059977840643370084" target="admin1"><span
													class="file">国家法规</span> </a>
											</li>
											
											<%--<li>
												<a href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=doc&d=0.02697930729000919" target="admin1"><span
													class="file">公文流转管理</span> </a>
											</li>
										--%></ul>
<%--							</li>--%>
						</ul>
									
					</td>
					<td style="width: 85%;" valign="top">
						<iframe src="<%=basePath%>ea/dtconferenceorg/ea_getAllDtconOrg.jspa?" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
   /*   $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
     }); */

     $(document).ready(function() {  
   		$("#navigation").treeview({
   		 persist: "location",
				  collapsed: true,
				  unique: true
   		
   		});   
   		/* setInterval(function() {
   					var FormObj = document.getElementById("mainframe").contentWindow;
   					var total = FormObj.document.getElementById("totals");
   					if (total != null) {
   						total.innerText = Number(result1) + Number(result2)
   								+ Number(result3) + Number(result4)
   								+ Number(result5) + Number(result6);

   					}

   				}, 100); */
   	   $("#aadTree").height($(window).height() - 30);
			$("#mainframe1").css({
				"height" : $(window).height() - 5 + "px"
			});
   	   
    	});
    	
</script>
	

	</body>
</html>