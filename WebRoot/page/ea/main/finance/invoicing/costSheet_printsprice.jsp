<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>各子项目总价</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var journalNum="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var csbID="${costSheetBill.csbID}";
        	var pNumber=${pageNumber};
        	var token=0;
      
        </script>
		<style type="text/css">
.tables {
	color: #000000;
	font-size: 9pt;
	border: 1px solid #000000;
	border-collapse: collapse;
}

.tables tr,td,th {
	border: 1px solid #000000;
}

p {
	font-size: 10pt;
}
</style>
	</head>
	<body>
		<div style="padding-top: 40px; overflow: auto;text-align: center;">
			<h2>
				工程单价计算表
			</h2>
			<c:forEach items="${list}" var="m">
			<br/>
			<br/>
				<p>
					${m.billsType}
				</p>
				<div style="margin-left: 19%; text-align: left;">
					<p>
						预算凭证号：${m.journalNum }
						<span style="margin-left: 500px;">定额单位:100m3</span>
					</p>
				</div>
				<table class="tables" style="margin-bottom: 20px;margin:auto;" border="0"
					cellpadding="10">
					<thead>
						<tr>
							<th align="center">
								项目描述：
							</th>

							<th colspan="7" align="left">
								${m.content}
							</th>

						</tr>
						<tr>
							<th align="center">
								序号
							</th>

							<th align="center" width="200">
								名称
							</th>
							<th align="center" width="200">
								摘要
							</th>
							<th align="center">
								型号规格
							</th>
							<th align="center">
								计量单位
							</th>
							<th align="center">
								数量
							</th>
							<th align="center">
								单价(元)
							</th>
							<th align="center">
								总价(元)
							</th>
						</tr>
					</thead>
					<tbody>
						<%
							int number = 1;
						%>
						<c:forEach items="${goodsBillsmaps}" var="m1">
							<c:if test="${m1.key eq m.cashierBillsID}">
								<c:forEach items="${m1.value}" var="m2">
									<tr>
										<td><%=number%></td>
										<td>
											<c:out value="${m2.goodsName}" />
										</td>
										<td>
											<c:out value="${m2.reasonThing}" />
										</td>
										<td>
											<c:out value="${m2.standard}" />
										</td>
										<td>
											<c:out value="${m2.goodsVariableID}" />
										</td>
										<td>
											<c:out value="${m2.quantity}" />
										</td>
										<td>
											<c:out value="${m2.price}" />
										</td>
										<td>
											<c:out value="${m2.money}" />
										</td>
									</tr>
									<%
										number++;
									%>
								</c:forEach>
								</c:if>

								<%--<%
									if (number < 15) {

													for (int i = 0; i < 20 - number; i++) {
								%>
								<tr>
									<td><%=number++%></td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
									<td>
										&nbsp;
									</td>
								</tr>

								<%
									}
												}
								%>

							--%>


						</c:forEach>
						<s:iterator value="mapaccount" id="column">
								<c:if test="${column.key eq m.cashierBillsID}">
								<tr>
									<td height="15" align="center" bgcolor="#FFFFFF">
										合计
									</td>
									<td align="center" bgcolor="#FFFFFF"></td>
									<td align="center" bgcolor="#FFFFFF"></td>
									<td align="center" bgcolor="#FFFFFF"></td>
									<td align="center" bgcolor="#FFFFFF"></td>
									<td align="center" bgcolor="#FFFFFF">
									
									</td>
									<td align="center" bgcolor="#FFFFFF"></td>
									<td align="center" bgcolor="#FFFFFF">
									 <s:property value="value"/>
									</td>
								</tr>
								</c:if>
					  </s:iterator>
					  </tbody>
				</table>
		
				<div style="float: right; margin-right: 18%;">
					委托代理人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（签字）
				</div>
			</c:forEach>
		</div>

	</body>
</html>
