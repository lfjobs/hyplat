<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆出入记录</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/CardManage/CarGooutRecord.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
         var cardCode = '${cardCode}';
         var cardReaderID = "";
		</script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme" id="tables">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="100" align="center">
							卡号
						</th>
						<th width="100" align="center">
							车牌号
						</th>
						<th width="150" align="center">
							出入时间
						</th>
						<th width="150" align="center">
							读卡器
						</th>
						<th width="80" align="center">
							司机
						</th>
						<th width="150" align="center">
							创建时间
						</th>
						<th width="150" align="center">
							照片
						</th>



					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${ID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ID}" />
							</td>
							<td>
								<span id="cardCode">${cardCode}</span>
							</td>
							<td>
								<span id="carNum">${carNum}</span>
							</td>
							<td>
								<span id="enterTime">${fn:substring(enterTime,0,19)}</span>
							</td>
							<td>
								<span id="readerEnterName">${readerEnterName}</span>
							</td>
							<td>
								<span id="createUser">${createUser}</span>
							</td>
							<td>
								<span id="createDate">${fn:substring(createDate,0,19)}</span>
							</td>
							<td>
								<s:if test="videoPic==null||videoPic==''">无</s:if>
								<s:else>
									<span class="look" id='${videoPic}'> 查看 </span>
								</s:else>
							</td>



						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cardmanage/ea_getCarGooutRecord.jspa?pageNumber=${pageNumber}&search=${search}&cardCode=${cardCode}">
				</c:param>
			</c:import>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" cellpadding="10" cellspacing="10"
					id="cataffSearchTable">
					<tr>
						<td align="right">
							卡号：
						</td>
						<td>
							<input name="cardRecord.cardCode" class="input" id="cardcode"
								size="18" />
						</td>
					</tr>
					<tr>
						<td align="right">
							读卡器：
						</td>
						<td>
							<select id="readerEnter" name="cardRecord.readerEnter"
								style="width: 127px;">
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="cardRecord.carNum" class="input" id="createUser"
								size="18" />
						</td>
					</tr>
					<tr>
						<td align="right">
							起时间：
						</td>
						<td>
							<input type="text" name="cardRecord.startTime" id="startDate"
								onFocus="var endTime=$dp.$('endDate');WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm', onpicked:function(){endDate.focus();}})"
								readonly size="18"
								value="${fn:substring(document.startValidity, 0, 10)}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							止时间：
						</td>
						<td>
							<input type="text" name="cardRecord.endTime" id="endDate"
								onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly size="18"
								value="${fn:substring(document.endValidity, 0, 10)}" />
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


		<!--查看 -->
		<div class="jqmWindow" style="width: 500px; right: 25%; top: 10%"
			id="jqModelView">
			<form name="postViewForm" id="postViewForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查看
					<div class="close">
					</div>
				</div>
				<table width="99%" cellpadding="2" cellspacing="2" id="viewTable">
					<tr>
						<td align="right">
							<span>卡号：</span>
						</td>
						<td align="left">
							<span id="cardCode"></span>
						</td>
						<td rowspan="9" align="center">
							<img id="pic" width="240" height="180" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>有效期：</span>
						</td>
						<td align="left">
							<span id="validityTime"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>使用状态：</span>
						</td>
						<td align="left">
							<span id="status"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>卡使用类型：</span>
						</td>
						<td align="left">
							<span id="statesType"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>卡类型：</span>
						</td>
						<td align="left">
							<span id="cardType"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>车牌号：</span>
						</td>
						<td>
							<span id="carNum">${carInformation.carNum}</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>司机：</span>
						</td>
						<td>
							<span id="driver">${carInformation.driver}</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>出入时间：</span>
						</td>
						<td>
							<span id="enterTime">${fn:substring(cardRecord.enterTime,0,19)}</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<span>读卡器：</span>
						</td>
						<td>
							<span id="readerEnterName">${cardRecord.readerEnterName}</span>
						</td>
					</tr>


				</table>
				<div align="center">
					<input type="button" class="input-button" id="tocancle"
						value=" 关闭 " />
				</div>
			</form>
		</div>
		<!-- 用于显示图片的层 -->
		<div style="display: none;width:240;height:180;" id="showPlayer">
			<img id="pics" width="240" height="180" />
		</div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>