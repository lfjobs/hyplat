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
		<title>子项目单价分类汇总</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/costSheet_printlist.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var basePath="<%=basePath%>";
        	var proID="${proID.csbID}";

      
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
		<div style="padding-top: 40px; margin:auto;width:950px;overflow: auto; text-align: center;">
			<h2>
				工程单价计算表
			</h2>


			<div style="text-align: left;">
				<p>
					项目编号：${productPack.goodsNum}

				</p>
				<p>
					工程名称：${productPack.goodsName}
				</p>
			</div>
			<table class="tables" style="margin-bottom: 20px;margin:auto;" border="0"
				cellpadding="10">
				<thead>
					<tr>
						<th align="center">
							序号
						</th>

						<th align="center" width="200">
							项目编号
						</th>
						<th align="center">
							项目名称
						</th>
						<th align="center">
							计量单位
						</th>
						<th align="center">
							人工费
						</th>
						<th align="center">
							材料费
						</th>
						<th align="center">
							机械使用费
						</th>
						<th align="center">
							其他费
						</th>
						<th align="center">
							施工管理费
						</th>
						<th align="center">
							企业利润
						</th>
						<th align="center">
							税金
						</th>
						<th align="center">
							合计
						</th>
					</tr>
				</thead>
				<tbody>
    <%
								 int number2= 1;
								
								%>
					<c:forEach items="${list}" var="m">
                     
						<c:forEach items="${goodsBillsmaps}" var="m1">
							<c:if test="${m1.key eq m.cashierBillsID}">


								<tr>
									<td><%=number2 %>部分</td>
									<td>
										${m.journalNum}
									</td>
									<td>
										${m.projectName}
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
								 int number1 = 1;
								
								%>
								<c:forEach items="${m1.value}" var="m2">
									<tr>
										<td><%=number1 %></td>
										<td>${m2.goodsNum}</td>
										<td>
											<c:out value="${m2.goodsName}" />
										</td>
										<td>
											<c:out value="${m2.goodsVariableID}" />
										</td>
										<td>
										<%--<c:if test='${m2.subjectsName=="人工费"}'>
										
										  <c:out value="${m2.amount}" />
										</c:if>
											
										--%></td>
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
										<td>
											<c:out value="${m2.money}" />
										</td>
									</tr>


                                 <% number1++; %>
								</c:forEach>

							</c:if>

						</c:forEach>
					 <% number2++; %>
					</c:forEach>

				</tbody>
			</table>
			<div style="float: right;margin-right:10px;">
					委托代理人&nbsp;&nbsp;&nbsp;&nbsp;（签字）
				</div>
		</div>
			
	</body>
</html>
