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
		<title>车用瓶使用登记管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
			<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var bottleID = '';
  		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = ${pageNumber};
		var carID=parent.carID;
		var token=0;
		var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carBottle_edit.js"></script>
	</head>
	<body>
		<form name="bottleForm" id="bottleForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="60" align="center">
								车牌号
							</th>
							<th width="60" align="center">
								车型
							</th>
							<th width="60" align="center">
								车架号
							</th>
							<th width="100" align="center">
								发动机号
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="80" align="center">
								登证记号
							</th>
							<th width="50" align="center">
								区域
							</th>
							<th width="30" align="center">
								单位
							</th>
							<th width="50" align="center">
								厂牌型号
							</th>
							<th width="50" align="center">
								车主
							</th>
							<th width="50" align="center">
								地址
							</th>
							<th width="50" align="center">
								电话
							</th>
							<th width="50" align="center">
								安装单位
							</th>
							<th width="50" align="center">
								安装数量
							</th>
							<th width="50" align="center">
								安装日期
							</th>
							<th width="50" align="center">
								气瓶编号
							</th>
							<th width="50" align="center">
								型号
							</th>
							<th width="50" align="center">
								制造单位
							</th>
							<th width="50" align="center">
								出场年月
							</th>
							<th width="50" align="center">
								容积
							</th>
							<th width="50" align="center">
								下次体检时间
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${bottleID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${bottleID}" />
									<input type="hidden" name="bottleKey" id="bottleKey"
										value="${bottleKey}" />
									<input type="hidden" name="motorcarID" id="bottleID"
										value="${bottleID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${bottle.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="frameNum">${frameNum}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carPeople">${carPeople}</span>
								</td>
								<td class="td_bg01">
									<span id="boardingNum">${boardingNum}</span>
								</td>
								<td class="td_bg01">
									<span id="area">${area}</span>
								</td>
								<td class="td_bg01">
									<span id="unit">${unit}</span>
								</td>
								<td class="td_bg01">
									<span id="brandType">${brandType}</span>
								</td>
								<td class="td_bg01">
									<span id="owners">${owners}</span>
								</td>
								<td class="td_bg01">
									<span id="bottleAddress">${bottleAddress}</span>
								</td>
								<td class="td_bg01">
									<span id="telephone">${telephone}</span>
								</td>
								<td class="td_bg01">
									<span id="installationUnit">${installationUnit}</span>
								</td>
								<td class="td_bg01">
									<span id="installationNum">${installationNum}</span>
								</td>
								<td class="td_bg01">
									<span id="installationDate">${fn:substring(installationDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="cylindersNum">${cylindersNum}</span>
								</td>
								<td class="td_bg01">
									<span id="typeNum">${typeNum}</span>
								</td>
								<td class="td_bg01">
									<span id="makeUnit">${makeUnit}</span>
								</td>
								<td class="td_bg01">
									<span id="factoryDate">${fn:substring(factoryDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="volume">${volume}</span>
								</td>
								<td class="td_bg01">
									<span id="inspectionDate">${fn:substring(inspectionDate, 0, 10)}</span>
									<span id="bottleKey" style="display:none">${bottleKey}</span>
                                 	<span id="bottleID"  style="display:none">${bottleID}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/bottle/ea_getBottleList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车用瓶使用证的添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1" style="width: 700px; left: 65%; top: 5%" 
			id="jqModel">
			<form name="addForm" id="addForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5"
						cellspacing="5">

						<tr>
							<td style="width:15%;" align="right">
								<span class="xx">*</span>登证记号：
							</td>
							<td style="width:33%;">
								<input name="bottle.boardingNum" id="boardingNum"
									class="boardingNum isremove put3" size="20" />
							</td>
							<td style="width:19%;" align="right">
								区域：
							</td>
							<td>
								<input name="bottle.area" id="area" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>单位：
							</td>
							<td>
								<input name="bottle.unit" id="unit" class="unit isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>车主：
							</td>
							<td>
								<input name="bottle.owners" id="owners" size="20"
									class="owners isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								地址：
							</td>
							<td>
								<input name="bottle.bottleAddress" id="bottleAddress" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>电话：
							</td>
							<td>
								<input name="bottle.telephone" id="telephone" size="20"
									class="telephone isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>安装单位：
							</td>
							<td>
								<input name="bottle.installationUnit" id="installationUnit"
									class="installationUnit isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>安装数量：
							</td>
							<td>
								<input name="bottle.installationNum" id="installationNum"
									class="installationNum isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>安装日期：
							</td>
							<td>
								<input name="bottle.installationDate" id="installationDate"
									onfocus="date(this);" class="installationDate isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>气瓶编号：
							</td>
							<td>
								<input name="bottle.cylindersNum" id="cylindersNum"
									class="cylindersNum isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>型号：
							</td>
							<td>
								<input name="bottle.typeNum" id="typeNum"
									class="typeNum isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>制造单位：
							</td>
							<td>
								<input name="bottle.makeUnit" id="makeUnit"
									class="makeUnit isremove put3" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>出场年月：
							</td>
							<td>
								<input name="bottle.factoryDate" id="factoryDate"
									onfocus="date(this);" class="factoryDate isremove put3"
									size="20" />
							</td>
							<td align="right">
								容积
							</td>
							<td>
								<input name="bottle.volume" id="volume" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>下次体检时间：
							</td>
							<td>
								<input name="bottle.inspectionDate" id="inspectionDate"
									onfocus="date(this);" class="inspectionDate isremove put3"
									size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>厂牌型号：
							</td>
							<td colspan="4">
								<input name="bottle.brandType" id="brandType"
									class="brandType isremove put3" size="20" />
							   <input name="carInformation.carID" type="hidden"  id="numCarID"/>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
							
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		<!-- 车用瓶的修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 700px; left: 65%; top: 5%" id="jqModelup">
			<form name="updateForm" id="updateForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftableupdate" cellpadding="5"
						cellspacing="5">
						<tr>
							<td style="width: 15%" align="right">
								登证记号：
							</td>
							<td style="width: 33%">
								<input name="bottle.boardingNum" id="boardingNum"
									value="${bottle.boardingNum }" size="20" />
							</td>
							<td style="width: 19%" align="right">
								区域：
							</td>
							<td>
								<input name="bottle.area" id="area" value="${bottle.area }"
									size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								单位：
							</td>
							<td>
								<input name="bottle.unit" id="unit" value="${bottle.unit}"
									size="20" />
							</td>
							<td align="right">
								车主：
							</td>
							<td>
								<input name="bottle.owners" id="owners" value="${bottle.owners}"
									size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								地址：
							</td>
							<td>
								<input name="bottle.bottleAddress" id="bottleAddress"
									value="${bottle.bottleAddress}" size="20" />
							</td>
							<td align="right">
								电话：
							</td>
							<td>
								<input name="bottle.telephone" id="telephone"
									value="${bottle.telephone }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								安装单位：
							</td>
							<td>
								<input name="bottle.installationUnit" id="installationUnit"
									value="${bottle.installationUnit}" size="20" />
							</td>
							<td align="right">
								安装数量：
							</td>
							<td>
								<input name="bottle.installationNum" id="installationNum"
									value="${ bottle.installationNum}" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								安装日期：
							</td>
							<td>
								<input name="bottle.installationDate" id="installationDate"
									onfocus="date(this);" value="${bottle.installationDate }"
									size="20" />
							</td>
							<td align="right">
								气瓶编号：
							</td>
							<td>
								<input name="bottle.cylindersNum" id="cylindersNum"
									value="${bottle.cylindersNum }" size="20" />
							</td>
						</tr>
						<tr>

							<td align="right">
								型号：
							</td>
							<td>
								<input name="bottle.typeNum" id="typeNum"
									value="${bottle.typeNum }" size="20" />
							</td>
							<td align="right">
								制造单位：
							</td>
							<td>
								<input name="bottle.makeUnit" id="makeUnit"
									value="${bottle.makeUnit }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								出场年月：
							</td>
							<td>
								<input name="bottle.factoryDate" id="factoryDate"
									value="${bottle.factoryDate }" onfocus="date(this);" size="20" />
							</td>
							<td align="right">
								容积
							</td>
							<td>
								<input name="bottle.volume" id="volume"
									value="${bottle.volume }" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								下次体检时间：
							</td>
							<td>
								<input name="bottle.inspectionDate" id="inspectionDate"
									value="${ bottle.inspectionDate}" size="20" />
							</td>
							<td align="right">
								厂牌型号：
							</td>
							<td>
								<input name="bottle.brandType" id="brandType"
									value="${bottle.brandType }" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input name="bottle.bottleKey" id="bottleKey" type="hidden"
									class="input" size="20" />
								<input name="bottle.bottleID" id="bottleID" type="hidden"
									class="input" size="20" />
								<input type="button" class="input-button JQuerySubmits"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturns"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		<!-- 车用瓶查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" cellpadding="5" cellspacing="5">

					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="bottle.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="bottle.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="bottle.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="bottle.carPeople" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="carInformation.carID" type="hidden" id="carIDs" />
					<input name="type" type="hidden" value="${type}" />
				</div>
			</div>
		</form>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
