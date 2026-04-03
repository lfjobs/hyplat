<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>微分金收支单据管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";

			Company c = (Company)session.getAttribute("currentcompany");
			CAccount ca = (CAccount)session.getAttribute("account");
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
		<script type="text/javascript">
             var basePath='<%=basePath%>';
             var companyID = '<%=c.getCompanyID()%>';
             var aemail = '<%=ca.getAccountEmail()%>';
        </script>
		<style type="text/css">
#doctree {
	position: absoute;
	padding-top: 0cm;
	margin-top: 0px;
	width: 100%;
	background-color: #FFFFFF;
	float: left;
}

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
							<span id="frametitle">微分金收支单据管理</span>

						</div>
						<!--左边的树 -->

						<ul id="navigation" style="width: 180px;"
							class="filetree">
							<li>
								<span class="folder" id="tit">微分金收支单据管理</span>
								<ul>
									<li>
										<span class="folder">微分金收支单据管理</span>
										<ul>
										<li>
													<span class="folder">订单管理</span>
													<ul>
													<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?" target="admin1">订单</a></span></li>
														<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?zzorder=zz" target="admin1">待审核转账订单</a></span></li>
													</ul>
												</li>
												<li>
													<span class="folder">收款单管理</span>
													<ul>
													<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_skd.jspa?pl=sk" target="admin1">收款单</a></span></li>
													<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_getskd.jspa?pl=zk&iisnull=2" target="admin1">支款单</a></span></li>
													</ul>
												</li>
											<li>
												<span class="folder">兑现收支管理</span>
												<ul>
													<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_dxskd.jspa?pl=sk" target="admin1">个人兑现收款单</a></span></li>
													<li><span class="file"><a href="<%=basePath%>ea/bdbill/ea_getdxskd.jspa?pl=zk&iisnull=2" target="admin1">公司支款单</a></span></li>
												</ul>
											</li>
												<li><a href="<%=basePath%>ea/salesman/ea_getCollectInformationList.jspa?" target="admin1"><span class='file'>出库单管理</span></a></li>
												<li>
													<span class="folder">物流管理</span>
													<ul>
														<li>
														<a target="admin1" href="<%=basePath%>/ea/logstor/ea_getCollectInformationList.jspa">
														<span class="file">物流入库</span> </a>
														</li>
														<li>
														<a target="admin1" href="<%=basePath%>/ea/logware/ea_getCollectInformationList.jspa"><span
															class="file">物流出库</span> </a>
														</li>
													</ul>
												</li>
												<li>
													<span class="folder">收货管理</span>
													<ul>
													    <li>
															<span class="file"><a href="<%=basePath%>ea/consignee/ea_getConsigneeSheetList.jspa?stype=04" target="admin1">收货单</a></span>
														</li>
													</ul>
												</li>
												<li>
												<span class="file"><a href="<%=basePath%>ea/refund/ea_getRefundSheetList.jspa?stype=02" target="admin1">退货管理</a></span>
											</li>
											<li>
												<span class="file"><a href="<%=basePath%>ea/refund/ea_findRefundCashList.jspa?stype=02" target="admin1">退款管理</a></span>
											</li>
											<li>
												<a href="<%=basePath%>/ea/bdbill/ea_getjbfpmx.jspa?pl=fl" target="admin1"><span class="file" >金币分配明细管理</span></a>		
											</li>
                                            <li id="dxsh" style="display: none;">
                                                <a href="<%=basePath%>/ea/jinbi/ea_getWdsList.jspa" target="admin1"><span class="file" >兑现申请审核</span></a>
                                            </li>
											<li>
												<span class="file"><a href="<%=basePath%>/ea/jinbi/ea_getWithWalPage.jspa?stype=02" target="admin1">提现记录</a></span>
											</li>
										</ul>
									</li>
						</ul>
					</td>
					<td style="width: 85%;" valign="top">
						<iframe src="" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe></iframe>
					</td>
				</tr>
			</table>
		</div>
		<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe1").css({"height" : $(window).height()+ "px"}); 
   		$("#navigation").treeview();

   		if(companyID=="company201009046vxdyzy4wg0000000025"){
   		    $("#dxsh").show();
        }
     });
</script>
	</body>

</html>
