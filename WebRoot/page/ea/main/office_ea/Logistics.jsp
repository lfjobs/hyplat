<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>物流管理列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script>
			var token = 0;
            var logisticsID = "";
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/Logistics.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="200" align="center">
							物流公司名称
						</th>
						<th width="100" align="center">
							负责人
						</th>
						<th width="150" align="center">
							货物名称
						</th>
						<th width="100" align="center">
							运单编号
						</th>
						<th width="100" align="center">
							数量
						</th>
						<th width="100" align="center">
							承运人
						</th>

						<th width="100" align="center">
							起运日期
						</th>
						<th width="100" align="center">
							到达日期
						</th>
						<th width="100" align="center">
							货物签收人
						</th>
						<th width="100" align="center">
							备注
						</th>
						<th width="70" align="center" >
							签收状态
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${logisticsID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${logisticsID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="penskelogistics">${penskelogistics}</span>
							</td>

							<td>
								<span id="principal">${principal}</span>
							</td>
							<td>
								<span id="goodsName">${goodsName}</span>
							</td>
							<td>
								<span id="waybillNum">${waybillNum}</span>
							</td>
							<td>
								<span id="quantity">${quantity}</span>
							</td>
							<td>
								<span id="carrier">${carrier}</span>
							</td>
							<td>
								<span id="startDate" class="datas">${fn:substring(startDate,
									0, 10)}</span>
							</td>
							<td>
								<span id="endDate" class="datas">${fn:substring(endDate,
									0, 10)}</span>
							</td>
							<td>
								<span id="staffName" >${staffName}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
								<span id="logisticsID" style="display: none">${logisticsID}</span>
								<span id="logisticsKey" style="display: none">${logisticsKey}</span>
							</td>
							<td>
								<span id="state">${state=="00"?"是":"否"}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/logistics/ea_getLogisticsList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>

		<!--查看 -->
		<div class="jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				action="<%=basePath%>ea/afforest/t_ea_save.jspa">
				<div class="drag">
					物流管理
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
					</div>
					<table width="700" height="340" border="0" id="stafftable"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 0px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="700" height="293" border="0" id="stafftable2"
									align="center" cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="157" height="30" align="right">
											物流公司名称：
										</td>
										<td width="184">
											<input type="text" id="penskelogistics"
												name="logistics.penskelogistics" />
										</td>
										<td width="197" align="right">
											负责人：
										</td>
										<td width="248">
											<input type="text" id="principal" name="logistics.principal" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											货物名称：
										</td>
										<td>
											<input name="logistics.goodsName" id="goodsName" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											运单编号：
										</td>
										<td>
											<input name="logistics.waybillNum" id="waybillNum"
												type="text" class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											数量：
										</td>
										<td>
											<input name="logistics.quantity" id="quantity" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											承运人：
										</td>
										<td>
											<input name="logistics.carrier" id="carrier" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											起运时间：
										</td>
										<td>
											<input name="logistics.startDate" id="startDate"
												onfocus="date(this);" type="text" class="input" size="20" />
										</td>
										<td align="right">
											到达时间：
										</td>
										<td>
											<input name="logistics.endDate" type="text"
												onfocus="date(this);" class="input" id="endDate" size="20" />
										</td>
									</tr>
									<tr>
										<td height="30" align="right">
											是否签收：
										</td>
										<td>
											<select name="logistics.state" id="state" class="select">
												<option value="00">
													是
												</option>
												<option value="01">
													否
												</option>
											</select>
										</td>
										<td align="right">
											货物签收人：
										</td>
										<td><input name="logistics.staffName" type="text" class="input" id="staffName" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											备注：
										</td>
										<td colspan="4">
											<textarea rows="3" cols="61" id="remark"
												name="logistics.remark" class="input"></textarea>
										</td>
									</tr>
									<tr>
										<td colspan="5" align="center">
											<input name="logistics.logisticsKey" id="logisticsKey"
												type="hidden" class="input" size="20" />
											<input name="logistics.logisticsID" id="logisticsID"
												type="hidden" class="input" size="20" />
											<input type="button" class="input-button JQuerySubmit"
												style="cursor: pointer; width: 80px;" value="提交" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>


		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							员工编号：
						</td>
						<td width="261">
							<input name="logistics.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							员工姓名：
						</td>
						<td>
							<input name="logistics.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							岗位名称：
						</td>
						<td>
							<input name="logistics.postName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="deptID" name="logistics.departmentID"></select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
	</body>
</html>