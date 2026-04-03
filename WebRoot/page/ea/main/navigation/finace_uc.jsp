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
<title>集团财务票据使用管理</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
    type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
        <script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
    <br />
    <table style="width:100%;border:0; padding: 0;margin:0">
       <tr>
			<td>
				<!--票据管理-->
				<table>
					<tr>
						<td >
							<div align="center"></div><img src="<%=basePath%>images/sytemicon/05.png"></img>
							<div class="center_a">
								<strong>票据管理</strong>
							</div></td>
						<td>
							<div class="na_back_img_jt_hx"></div></td>
						<td><table border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td >
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/ea/cashiersummary/ea_getCashierList.jspa?cc=jt&period=01'"><img src="<%=basePath%>images/sytemicon/r5_c3.gif"></img></div>
										<div class="center_a">票据汇总</div></td>
									<td >
										<div class="na_back_dh"  onclick="document.location.href='<%=basePath%>/ea/goodscashier/ea_getCashierList.jspa?cc=jt&period=01'"><img src="<%=basePath%>images/sytemicon/r5_c5.gif"></img></div>
										<div class="center_a">明细汇总</div></td>
									<td >
										<div class="na_back_dh" onclick="document.location.href='<%=basePath%>/ea/rejectbills/ea_getCashierBillsList.jspa?level=allCompany'"><img src="<%=basePath%>images/sytemicon/r5_c7.gif"></img></div>
										<div class="center_a">驳回票据汇总</div></td>
									 <td> <div class="na_back_dh" onclick="document.location.href='<%=basePath%>/ea/archivessummary/ea_getArchivesList.jspa?'" align="center">
                                        <img src="<%=basePath%>images/sytemicon/bumenguidang.png"></img></div>
                                            <div class="center_a">集团归档管理</div>
                                        </td>
									<td >
										<div class="na_back_dh" align="center">
										<img src="<%=basePath%>images/sytemicon/zichanliushui.png"></img></div>
										<div class="center_a">资产流水管理</div></td>
								</tr>
							</table></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<!--报表管理-->
				<table>
					<tr>
						<td >
							<div align="center"></div><img src="<%=basePath%>images/sytemicon/06.png"></img>
							<div class="center_a">
								<strong>报表管理</strong>
							</div></td>
						<td >
							<div class="na_back_img_jt_hx"></div></td>
						<td><table >
                                    <tr>
                                    <td>
                                        <div  class="na_back_dh"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_r.jsp'"><img src="<%=basePath%>images/sytemicon/zongzhangguanli.png"></img></div>
                                        <div class="center_a">总账管理</div>
                                    </td>
                                        <td>
                                        <div class="na_back_dh" align="center"><img src="<%=basePath%>images/sytemicon/xianjinyianhangliushuizhang.png"></img></div>
                                        <div class="center_a">现金银行流水账</div></td>
                                    <td >
                                        <div class="na_back_dh" align="center"><img src="<%=basePath%>images/sytemicon/yinhangzhichuguanli.png"></img></div>
                                        <div class="center_a">银行支出管理</div></td>
                                    <td >
                                        <div class="na_back_dh" align="center"><img src="<%=basePath%>images/sytemicon/xianjinyue.png"></img></div>
                                        <div class="center_a">现金余额</div></td>
                                    <td >
                                        <div class="na_back_dh" align="center"><img src="<%=basePath%>images/sytemicon/xianjinyinhangzongzhang.png"></img></div>
                                        <div class="center_a">现金银行总账</div></td>
                                    <td >
                                        <div class="na_back_dh" align="center">
                                        <img src="<%=basePath%>images/sytemicon/kuaijikemuguanli.png"></img></div>
                                        <div class="center_a">会计科目管理</div></td>
                                    <td >
                                        <div class="na_back_dh"  align="center" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/finace_bb.jsp'">
                                        <img src="<%=basePath%>images/sytemicon/caiwubaobiao.png"></img></div>
                                        <div class="center_a">财务报表</div></td>
                                    </tr>
                                </table></td>
					</tr>
				</table>
			</td>
		</tr>
    </table>
</body>
</html>
