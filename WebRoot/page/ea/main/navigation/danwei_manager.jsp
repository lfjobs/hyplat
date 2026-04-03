﻿<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单位管理</title>
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>/js/dropdown/extendPageMenu.js"></script>
	<style type="text/css">
		body{
			font-family: '微软雅黑';
		}
	</style>
</head>
<body>
	<!--   OBJECT标签，客户端控件引用    -->
											<OBJECT id="SOAOfficeCtrl"
												codeBase="<%=basePath%>js/cabs/ZSOffice.ocx#version=2,0,0,1"
												height="0" width="0"
												classid="clsid:AD06827C-D92F-4648-B880-138AF11E8A13" data=""
												VIEWASTEXT>
												<div align=center STYLE="color: red;">
													本机尚未安装卓正OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的卓正OFFICE组件。
												</div>
											</OBJECT>
											<!--**************   SOAOFFICE 客户端代码结束    ************************-->
	<table border="0" cellpadding="1" cellspacing="0" >
		<tr>
			<td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_1.png");'></div>
				<div align="center">
				<strong>单位管理</strong>				</div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img"  style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_1_01.png");'></div>
				<div class="center_a">企业所属单位管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_1_02.png");'></div>
				<div class="center_a">经营范围</div></td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_1_03.png");'></div>
				<div class="center_a">企业证件管理</div>			</td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_1_04_1.png");'></div>
				<div class="center_a">企业资质评定管理</div></td>
		</tr>
		<tr>
			<td rowspan="2">
				<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2.png");'></div>
				<div align="center">
					<strong>企业文化建设</strong>				</div>			</td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_01.png");' onclick="document.location.href='<%=basePath%>ea/logo/ea_getEnterpriseLogoList.jspa?'"></div>
				<div class="center_a">企业形象</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_02.png");'></div>
				<div class="center_a">品牌建设</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_03.png");' onclick="document.location.href='<%=basePath%>ea/image/ea_getOfficeImageManagerList.jspa?'"></div>
				<div class="center_a">办公室形象管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_04.png");'></div>
				<div class="center_a">员工形象管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_02.png");' onclick="document.location.href='<%=basePath%>ea/culture/ea_getEnterpriseCultureList.jspa?'"></div>
				<div class="center_a">企业文化管理</div></td>
				
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_05.png");' onclick="document.location.href='<%=basePath%>ea/spirit/ea_getEnterpriseSpiritList.jspa?'"></div>
				<div class="center_a">企业精神</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_06.png");' onclick="document.location.href='<%=basePath%>ea/report/ea_getEnterpriseReportList.jspa?'" ></div>
				<div class="center_a">企业历程</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_07.png");' onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/work_bills_a.jsp'"></div>
				<div class="center_a">员工活动</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_08.png");' onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/work_bills_a.jsp'"></div>
				<div class="center_a">优秀员工奖励</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_2_09.png");' onclick = "window.location.href='<%=basePath%>ea/meritorious/ea_getEnterpriseMeritoriousList.jspa?'"></div>
				<div class="center_a">企业员工排行榜</div></td>
		</tr>
		<tr>
			<td rowspan="5"><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3.png");'></div>
				<div align="center">
				<strong>行政管理</strong>				</div></td>
			<td><div class="na_back_img_jt_xs"></div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_01.png");' onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/logistics_conference.jsp'"></div>
				<div class="center_a">现场会议</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_02.png");' onclick="document.location.href='<%=basePath%>ea/fileManage/ea_getaFileManageList.jspa?'"></div>
				<div class="center_a">视频会议</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_03.png");'></div>
				<div class="center_a">会议记录</div></td>
							<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_07.png");' onclick="document.location.href='<%=basePath%>ea/meetingroom/ea_getMyRoomOrder.jspa'"></div>
				<div class="center_a">会议室预约管理</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_17.png");' onclick="document.location.href='<%=basePath%>ea/smeeting/ea_getStaffMeeingList.jspa'"></div>
				<div class="center_a">员工会议管理</div>			</td>
		</tr>
		<tr>
			<td><div class="na_back_img_jt_xs"></div></td>
			
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_04.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=contract&d=<%=Math.random()%>'"></div>
				<div class="center_a">办公室合同流转</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_04.png");' onclick=""></div>
				<div class="center_a">办公室合同查询</div></td>
			
		</tr>
		<tr>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_05.png");' onclick = "window.location.href='<%=basePath%>ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?'"></div>
				<div class="center_a">电子印章管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_06.png");'></div>
				<div class="center_a">电子记录管理</div></td>
			<td> <%-- <div class="na_back_img" onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/work_bills.jsp'"></div>
				<div class="center_a">行政管理</div> --%></td>
		</tr>
		<tr>
			<td rowspan="2"><div class="na_back_img_jt_xx"></div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_08.png");'></div>
				<div class="center_a">企业章程管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_08.png");'></div>
				<div class="center_a">企业制度</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_08.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=InduReg&d=<%=Math.random()%>'"></div>
				<div class="center_a">行政法规</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_09.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=CountReg&d=<%=Math.random()%>'"></div>
				<div class="center_a">国家法规</div></td>
		</tr>
		<%-- <tr>
		  <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/payGradeModulation/ea_getListForPage.jspa?'"></div>
			  <div class="center_a" >工资级别单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getRankPublicreceipt.jspa?'"></div>
				<div class="center_a">员工级别单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getOverPublicreceipts.jspa?'"></div>
				<div class="center_a">加班申请单</div></td>
			<td><div class="na_back_img"></div>
				<div class="center_a" onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getLeaveBillsList.jspa?'">请假单</div></td>
			<td><div class="na_back_img " onclick="document.location.href='<%=basePath%>ea/publicreceipts/ea_getListRewardPunishment.jspa?type=1'"></div>
				<div class="center_a">奖罚单</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?'"></div>
				<div class="center_a">通知单</div></td>
		</tr> --%>
		<tr>
			<%-- <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_file_manager.jsp?type=a'"></div>
				<div class="center_a" >行政管理文书</div> </td>	
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_file_manager.jsp?type=b'"></div>
				<div class="center_a" >商务决策文书</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_file_manager.jsp?type=c'"></div>
				<div class="center_a">商务活动文书</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_file_manager.jsp?type=d'"></div>
				<div class="center_a">企业礼仪文书</div></td>
			<td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_file_manager.jsp?type=e'"></div>
				<div class="center_a">演讲会议文书</div></td> --%>
		  <td>  <div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_15.png");' onclick = "document.location.href='<%=basePath%>ea/informbills/ea_getOfficeFileManager.jspa?'"></div>
			  <div class="center_a">文书文件管理</div>  </td>
			  <td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_3_04.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=doc&d=<%=Math.random()%>'"></div>
				<div class="center_a">公文流转管理</div></td>
		</tr>	
		<tr>
			<td>
				<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4.png");'></div>
				<div align="center">
					<strong>办公资料管理</strong>				</div>			</td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_01.png");' onclick="document.location.href='<%=basePath%>ea/fileManage/ea_getaFileManageList.jspa?type=1'"></div>
				<div class="center_a">个人资料管理</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_02.png");' onclick="document.location.href='<%=basePath%>ea/fileManage/ea_getaFileManageList.jspa?type=2'"></div>
				<div class="center_a">公共资料管理</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_03.png");' onclick="document.location.href='<%=basePath%>ea/corporationPhoto/ea_getCorporationPhotoList.jspa?'"></div>
				<div class="center_a">图片管理</div>			</td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_04.png");'></div>
				<div class="center_a">视频管理</div></td>
			<td><div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_05.png");'></div>
				<div class="center_a">ppt管理</div></td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_4_06.png");'></div>
				<div class="center_a">设计图管理</div>			</td>

			
		</tr>
		<tr>
			<td><div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5.png");'></div>
				<div align="center">
				<strong>资料库公共查询管理</strong>				</div></td>
			<td><div class="na_back_img_jt_hx" align="center"></div></td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5_01.png");' onclick="document.location.href='<%=basePath%>ea/docunfinish/ea_showDocUnfinishModule.jspa?type=W'"></div>
				<div class="center_a">Word文档查询</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5_02.png");' onclick="document.location.href='<%=basePath%>ea/docunfinish/ea_showDocUnfinishModule.jspa?type=E'"></div>
				<div  class="center_a">Excel文档查询</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5_03.png");'></div>
				<div class="center_a">图片管理查询</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5_04.png");'></div>
				<div class="center_a">视频管理查询</div>			</td>
			<td>
				<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_5_05.png");'></div>
				<div class="center_a">设计图管理查询</div>			</td>
		</tr>
	</table>
	
	
										
</body>
</html>