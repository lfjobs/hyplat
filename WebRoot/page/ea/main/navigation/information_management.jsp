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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>信息管理科-</title>
			<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
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

		<table
			style="width: 100%; border: 0; text-align: left; padding: 0; margin: 0"
			class="table03">
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15.png");'></div>
                      			<div align="center"><strong>网络管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_01.png");'></div>
                      						<div class="center_a"><span>软件系统管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_02.png");' onclick="document.location.href='<%=basePath%>/ea/addressip/ea_getListIPaddress.jspa?'"></div>
                      						<div class="center_a"><span>IP地址管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_03.png");' onclick="document.location.href='<%=basePath%>/ea/telmessage/ea_goMessageIndex.jspa?type=1&orgDetail=office'"></div>
                      						<div class="center_a"><span>短信管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_04.png");' onclick="document.location.href='<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp'"></div>
                      						<div class="center_a"><span>微信管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_05.png");' onclick="document.location.href='<%=basePath%>/ea/qq/ea_getQqList.jspa?'"></div>
                      						<div class="center_a"><span>QQ管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_06.png");' onclick="document.location.href='<%=basePath%>/ea/netdisk/ea_getNetDiskList.jspa?'"></div>
                      						<div class="center_a"><span>网络硬盘管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_07.png");' onclick="document.location.href='<%=basePath%>/ea/domainname/ea_getListDomainname.jspa?'"></div>
                      						<div class="center_a"><span>域名管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_08.png");' onclick="document.location.href='<%=basePath%>/ea/networkfax/ea_getListNetworkFax.jspa?'"></div>
                      						<div class="center_a"><span>网络传真管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_15_09.png");' onclick="document.location.href='<%=basePath%>/page/ea/main/telrec/telMain.jsp'"></div>
                      						<div class="center_a"><span>呼叫中心管理</span></div>
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16.png");'></div>
                      			<div align="center"><strong>公共信息管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_01.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=AnnNoti&d=<%=Math.random()%>'"></div>
                      						<div class="center_a"><span>公告通知管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_02.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=news&d=<%=Math.random()%>'"></div>
                      						<div class="center_a"><span>新闻管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_03.png");'></div>
                      						<div class="center_a"><span>投票管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_04.png");' onclick="document.location.href='<%=basePath%>/ea/telephone/ea_getaTelephoneList.jspa?'"></div>
                      						<div class="center_a"><span>公共电话薄</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_05.png");' onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=bulletin&d=<%=Math.random()%>'"></div>
                      						<div class="center_a"><span>简报管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_06.png");'></div>
                      						<div class="center_a"><span>数据提醒管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_07.png");' onclick="document.location.href='<%=basePath%>/ea/schedule/ea_getScheduleList.jspa?'"></div>
                      						<div class="center_a"><span>公共日程管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_16_08.png");'></div>
                      						<div class="center_a"><span>公共日志管理</span></div>
										</td>

									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_17.png");'></div>
                      			<div align="center"><strong>企业信息管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_17_01.png");'></div>
                      						<div class="center_a"><span>企业信息管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_17_02.png");'></div>
                      						<div class="center_a"><span>企业信息推广</span></div>
										</td>
										<td width="110" align="center"></td>
										<td width="110" align="center"></td>
										<td width="110" align="center"></td>
										<td width="110" align="center"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td width="120" align="center">
								<div class="na_back_img_ks" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18.png");'></div>
                      			<div align="center"><strong>网络安全管理</strong></div>
							</td>
							<td width="55" align="center">
								<div class="na_back_img_jt_hx"></div>
							</td>
							<td>
								<table border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_01.png");' onclick="document.location.href='<%=basePath%>/ea/network/ea_getNetWork.jspa?'"></div>
                      						<div class="center_a"><span>网络加密管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_02.png");' onclick="document.location.href='<%=basePath%>/ea/netWorkAntiVirus/ea_getNetWorkAntiVirusList.jspa?'"></div>
                      						<div class="center_a"><span>网络杀毒管理</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_03.png");'></div>
                      						<div class="center_a"><span>网络建设1</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_04.png");'></div>
                      						<div class="center_a"><span>带宽建设2</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_05.png");'></div>
                      						<div class="center_a"><span>网络安全建设3</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_06.png");'></div>
                      						<div class="center_a"><span>服务器建设4</span></div>
										</td>
										<td width="110" align="center">
											<div class="na_back_img" style='background-image: url("<%=basePath%>images/sytemicon_new/adminicon/icon_18_07.png");'></div>
                      						<div class="center_a"><span>终端建设</span></div>
										</td>
										<td width="110" align="center"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
