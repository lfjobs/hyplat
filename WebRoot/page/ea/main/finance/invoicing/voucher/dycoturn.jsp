<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>年度结转</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/finance/voucher.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/finance/invoicing/voucher/dycoturn.js"></script>
		<script type="text/javascript">
		 var  basePath="<%=basePath%>";           
         var  pNumber ="${pageNumber}";  
         var  search ="${search}";
         var stas="${stas}";
         var  token=0;
         var notoken=0;
	</script>
	</head>
	<body>
			<div class="top_div">
			<form name="searchForm" id="searchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<s:token></s:token>
				<table border="0" id="searchtbl" >
				<tr><td><strong>年度结转</strong></td><td>&nbsp;&nbsp;&nbsp;</td><td>年度：</td>
				<td><input name="invdyco.dycoYear" onfocus="cher_y(this);" style="height:18px;width:100px;" type="text" value=""/>
				<input name="search" type="hidden" value="search" />
				<input type="button" value="查询 " id="tosearch" style="margin:2px;" class="input-button"/>
				</td>
				</tr>
				</table>
			</form>
			</div>
			<div class="acip">
            <form name="saveTurnForm" id="saveTurnForm" method="post"
			enctype="multipart/form-data">
		    <input type="submit" name="submit" style="display: none" />
		    <s:token></s:token>
            <table class="acip" border="0" id="qdzy">
                <tr height="30">
                    <td>&nbsp;</td>
                    <td><span style="color:red;">*</span><span style="font-weight :bold;">结转年月：</span><input type="text" id="yearm" name="yearm" onfocus="cher_dm(this);" 
                    class="inputbottom " style="width:100px;height:19px;"/></td>
                    <td><input type="checkbox" id="jiez" name="jiez"/><span style="font-weight :bold;">结转</span></td>
                    <td></td>
                    <td><input type="checkbox" id="tjiez" name="tjiez"/><span style="font-weight :bold;">退结转</span></td>
                    <td><input type="button" class="input-button savedycturn" style="margin:0px;margin-left:5px;" value="确定作业" /></td>
                </tr>
            </table>
            </form>
            </div>
            <hr />
            <div class="fo_div" id="flex" style="border:0px solid green;height:600px;">
           <div style="overflow: auto;height:600px;">
				<table width="100%" border="1" cellspacing="1" cellpadding="1" class="border_t fexlist">
					<thead>
						<tr>
							<th width="35" align="center">
								序号
							</th>
							<th width="50" align="center">
								年度
							</th>
							<th width="100" align="center">
								会计科目
							</th>
							<th width="80" align="center">
								币别
							</th>
							<th width="90" align="center">
								部门
							</th>
							<th width="100" align="center">
								供应商
							</th>
							<th width="110" align="center">
								本位币期初借方金额
							</th>
                            <th width="110" align="center">
								本位币期初贷方金额
							</th>
							<th width="110" align="center">
								本位币本期借方金额
							</th>
							<th width="110" align="center">
								本位币本期贷方金额
							</th>
							<th width="110" align="center">
								本位币期末借方金额
							</th>
                            <th width="110" align="center">
								本位币期末贷方金额
							</th>
							<th width="110" align="center">
								记账期初借方金额
							</th>
							<th width="110" align="center">
								记账期初贷方金额
							</th>
							<th width="110" align="center">
								记账本期借方金额
							</th>
							<th width="110" align="center">
								记账本期贷方金额
							</th>
							<th width="110" align="center">
								记账期末借方金额
							</th>
							<th width="110" align="center">
								记账期末贷方金额
							</th>
						</tr>
					</thead>
					<tbody>
						<% int number = 1; %>
						<s:if test="pageForm.list!=null && search!=null">
                        <s:iterator value="pageForm.list">
							<tr id="showsearchlist">
								<td class="td_bg01">
								    <input type="hidden" name="dycoid" value="${dycoid}" />
									<span><%=number%></span>
								</td>
								<td class="td_bg01">
									<span id="dycoYear">${dycoYear}</span>
								</td>
								<td class="td_bg01">
									<span id="subjectsname">${subjectsname}</span>
								</td>
								<td class="td_bg01">
									<span class="currencyname">${currencyname}</span>
								</td>
								<td class="td_bg01">
									<input type="hidden" class="orgId" value="${orgid}">
									<span class="orgName"></span>
								</td>
								<td class="td_bg01">
									<input type="hidden" class="cliId" value="${clientid}">
									<span id="clientname">${clientname}</span>
								</td>
								<td class="td_bg01">
									<span id="sbdmoney"><fmt:formatNumber value="${sbdmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id=""><fmt:formatNumber value="${sbcmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
								    <span id="scdmoney"><fmt:formatNumber value="${scdmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="sccmoney"><fmt:formatNumber value="${sccmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="scldmoney"><fmt:formatNumber value="${scldmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="sclcmoney"><fmt:formatNumber value="${sclcmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
								    <span id="abdmoney"><fmt:formatNumber value="${abdmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="abcmoney"><fmt:formatNumber value="${abcmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="acdmoney"><fmt:formatNumber value="${acdmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="accmoney"><fmt:formatNumber value="${accmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
								    <span id="acldmoney"><fmt:formatNumber value="${acldmoney}" pattern="#,##0.00"/></span>
								</td>
								<td class="td_bg01">
									<span id="aclcmoney"><fmt:formatNumber value="${aclcmoney}" pattern="#,##0.00"/></span>
								</td>
							</tr>
							<% number++; %>
						</s:iterator> 
						</s:if>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
						<tr class="td_bg01">
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
							<td>&nbsp;</td><td>&nbsp;</td>
						</tr>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/dycoturn/ea_toDyCoTurn.jspa?pageNumber=${pageNumber}&search=${search}">
					</c:param>
				</c:import>
			</div>
			</div>
		<iframe name="hidden" border="0"  height="0" frameborder="0"></iframe>
	</body>
</html>
