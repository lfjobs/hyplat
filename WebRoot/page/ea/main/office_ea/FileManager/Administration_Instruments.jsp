<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>办公室督查</title>
	<%@ page language="java" pageEncoding="UTF-8"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/";
	%>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
		  type="text/css" />
	<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet"
		  href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
	<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	<script type="text/javascript">

        var basePath='<%=basePath%>';

        $(document).ready(function(){
            $("#navigation").treeview({
                persist: "location",
                collapsed: true,
                unique: true

            });

        });

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
		body{
			margin:0px;
		}


	</style>
</head>
<body>

<div class="main_main">
	<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td id="qh_sw" style="width: 15%" valign="top">
				<div class="qh_gg_nav">
					&nbsp; <span id="frametitle">行政管理文书</span>

				</div>

				<ul id="navigation" style="width: 180px;" class="filetree">
					<ul>

						<li><span class="folder">行政办公文书</span>
							<ul>
								<li><a
										href="<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=doc&d=<%=Math.random()%>'"
										target="admin1"><span class="file">公文流转</span> </a></li>
								<li><a
										href="<%=basePath%>ea/informbills/ea_getInformBillsList.jspa"
										target="admin1"><span class="file">通知</span> </a>
								</li>
								<li><a target="admin1"><span class="file">通报</span> </a>
								</li>
								<li><a target="admin1"><span class="file">请示</span> </a>
								</li>
								<li><a target="admin1"><span class="file">批复</span> </a>
								</li>
								<li><a target="admin1"><span class="file">书函</span> </a>
								</li>
								<li><a  href="<%=basePath%>ea/enterprisestamp/ea_getListEnterpriseStamp.jspa?" target="admin1"><span class="file">电子印章管理</span>
								</a>
								</li>
								<li><a   target="admin1"><span class="file">企业章程管理</span>
								</a>
								</li>


							</ul></li>


						<li><span class="folder">行政文书</span>

							<ul>
								<li><a
										href="<%=basePath%>ea/payGradeModulation/ea_getListForPage.jspa?"
										target="admin1"><span class="file">工资级别升降级单</span></a>

								</li>
								<li><a
										href="<%=basePath%>ea/publicreceipts/ea_getRankPublicreceipt.jspa?"
										target="admin1"><span class="file">员工级别变更单</span></a>
								</li>
								<li><span class="folder">员工加班申请管理</span><ul>
									<li>
										<a
												href="<%=basePath%>ea/overtime/ea_getListByOvertime.jspa?type=00"
												target="admin1"><span class="file">员工加班申请</span></a>
									</li>
									<li>
										<a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=01&receiptType=加班申请单"
												target="admin1"><span class="file">待审核加班申请</span></a>
									</li>
									<li>
										<a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=02&receiptType=加班申请单"
												target="admin1"><span class="file">已审核加班申请</span></a>
									</li>
									<li>
										<a
												href="<%=basePath%>ea/overtime/ea_getListByOvertime.jspa?type=org"
												target="admin1"><span class="file">部门加班单汇总</span></a>
									</li>
								</ul>
								</li>
								<li><a
										href="<%=basePath%>ea/stafftransfer/ea_getstaffTransferList.jspa?"
										target="admin1"><span class="file">人事调令单管理</span></a>

								</li>
								<li><a
										href="<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?"
										target="admin1"><span class="file">通知单管理</span></a>

								</li>
								<li><span class="folder">请假单管理</span>
									<ul>

										<li><a
												href="<%=basePath%>ea/leaveapply/ea_getListByLeave.jspa?type=00"
												target="admin1"><span class="file">请假单申请</span></a>
										</li>
										<li><a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=01&receiptType=请假申请单"
												target="admin1"><span class="file">待审核请假单</span></a>
										</li>
										<li><a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=02&receiptType=请假申请单"
												target="admin1"><span class="file">已审核请假单</span></a></li>
										<li><a
												href="<%=basePath%>/ea/leaveapply/ea_getListByLeave.jspa?type=org"
												target="admin1"><span class="file">部门请假单汇总</span></a></li>

									</ul>

								</li>

								<li><span class="folder">出差申请管理</span>
									<ul>
										<li><a
												href="<%=basePath%>ea/travelapply/ea_getListByTravel.jspa?type=00"
												target="admin1"><span class="file">出差申请</span>
										</a></li>
										<li><a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=01&receiptType=出差申请单"
												target="admin1"><span class="file">待审核出差单</span>
										</a></li>
										<li><a
												href="<%=basePath%>/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=02&receiptType=出差申请单"
												target="admin1"><span class="file">已审核出差单</span>
										</a></li>
										<li><a
												href="<%=basePath%>/ea/travelapply/ea_getListByTravel.jspa?type=org"
												target="admin1"><span class="file">部门出差单汇总</span></a></li>

									</ul></li>

								<li><a target="admin1"><span class="file">消假单管理</span></a>

								</li>
								<li><a target="admin1"><span class="file">派车单管理</span></a>

								</li>
								<li><a href="<%=basePath%>ea/publicreceipts/ea_getListRewardPunishment.jspa?type=1"
									   target="admin1"><span class="file">奖罚单</span></a>

								</li>
								<li><a href="<%=basePath%>ea/goldticket/ea_getListGoldTicket.jspa?" target="admin1"><span class="file">金币折扣单</span></a>

								</li>
							</ul></li>

					</ul>
				</ul></td>
			<td style="width: 85%;" valign="top"><iframe
					src="<%=basePath%>ea/informbills/ea_getInformBillsList.jspa?"
					id="mainframe1" style="margin:0px;width:100%;height:580px;"
					name="admin1" scrolling="no" frameBorder="0"></iframe></td>
		</tr>
	</table>
</div>




</body>
</html>
