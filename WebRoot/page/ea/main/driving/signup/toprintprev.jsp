<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>打印列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 16px;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}
.table th {
	border: 1px solid #000000;
	color: #000000;
	background: #E4F1FA;
	white-space: nowrap;
	align: center;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

body,td,th {
	font-size: 16px;
}

body {
	margin-left: 15px;
}
</style>
	</head>
	<body>

		<div id="tableprint" align="center">
			<form id="printform">
				<table width="620" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;">
					<tr>
						<td height="25" align="center" style="font-weight: bold">
							${param.module_title }
						</td>
					</tr>
				</table>

				<table style="widht:630px;" cellpadding="0" cellspacing="0" class="table">
					<tr class="tablewith">
						<th width="60" align="center">
							序号
						</th>

						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="80" align="center">
							联系方式
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="150" align="center">
							学员状态
						</th>
					</tr>
					<%
						int number = 1;
					%>
					<s:iterator value="stafflist">
						<tr>
							<td>
								<span><%=number%></span>
							</td>

						<td>
								<span id="staffCode">${staffcode}</span>
							</td>
							<td>
								<span id="recordCode">${recordcode}</span>
							</td>
							<td>
								<span id="staffName">${staffname}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="usedNmae">${usednmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativeplace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>

                            <td>
                                 <span id="staffIdentityCard">${staffidentitycard}</span>
                                
                            </td>
							<td>
								<span>${fn:substring(param.module_title,0,2)}${studentstatusnote}</span>
							</td>			
						</tr>
						<%
							number++;
							if(number%5==0){
							
						%>
						<p class="webjxcom"></p><% } %>
						
						
					</s:iterator>
					<%
						if (number < 5) {
							for (int i = 0; i < 4; i++) {
					%>

					<tr class="blank">
						<%
							for (int j = 0; j < 13; j++) {
						%>

						<td align="center">
							&nbsp;
						</td>
						<%
							}
						%>

					</tr>

					<%
						}
						 }
					%>
				</table>
			</form>
		</div>
	</body>
</html>