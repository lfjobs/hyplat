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
		<title>道路运输证管理</title>
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
		<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var roadID = '';
		   	var  search='${search}';
		    var personurl = "";
		    var notoken = 0;
		    var pNumber = ${pageNumber};
		    var carID=parent.carID;
		    var token=0;
		    var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carroad_edit.js"></script>
	</head>
	<body>
		<form name="carRoadForm" id="carRoadForm" method="post">
		<div>
			<input type="submit" name="submit" style="display: none" />
			
			<div id="main_main" class="main_main">
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
							<th width="30" align="center">
								选择
							</th>
							<th width="80" align="center">
								车牌号
							</th>
							<th width="100" align="center">
								车辆类型
							</th>
							<th width="100" align="center">
								发动机号码
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="80" align="center">
								业户名称
							</th>
							<th width="80" align="center">
								地址
							</th>
							<th width="80" align="center">
								经营许可证号
							</th>
							<th width="80" align="center">
								吨（座）位
							</th>
							<th width="80" align="center">
								车辆长宽高
							</th>
							<th width="80" align="center">
								经营范围
							</th>
							<th width="80" align="center">
								发证日期
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${roadID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${roadID}" />
									<input type="hidden" name="roadKey" id="roadKey"
										value="${roadKey}" />
									<input type="hidden" name="roadID" id="roadID"
										value="${roadID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carroad.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
								</td>
								<td class="td_bg01">
									<span id="engineNum">${engineNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carpeople">${carpeople}</span>
								</td>
								<td class="td_bg01">
									<span id="baseName">${baseName}</span>
								</td>
								<td class="td_bg01">
									<span id="roadAddress">${roadAddress}</span>
								</td>
								<td class="td_bg01">
									<span id="permitNum">${permitNum}</span>
								</td>
								<td class="td_bg01">
									<span id="tonnage">${tonnage}</span>
								</td>
								<td class="td_bg01">
									<span id="carLwh">${carLwh}</span>
								</td>
								<td class="td_bg01">
									<span id="businessRange">${businessRange}</span>
								</td>
								<td class="td_bg01">
									<span id="cardDate">${fn:substring(cardDate, 0, 10)}</span>
									<span id="roadID" style="display:none">${roadID}</span>
                                 	<span id="roadKey"  style="display:none">${roadKey}</span>
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carroad/ea_getCarRoadList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
              <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
              <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
			</div>
		</form>
		<!--道路运输证添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 680px; left: 65%; top: 5%" id="jqModel">
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
								<span class="xx">*</span>业户名称：
							</td>
							<td style="width:33%;">
								<input name="carroad.baseName" id="baseName" size="20"
									class="baseName isremove put3" />
							</td>
							<td style="width:19%;" align="right">
								<span class="xx">*</span>地址：
							</td>
							<td>
								<input name="carroad.roadAddress" id="roadAddress" size="20"
									class="roadAddress isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>经营许可证号：
							</td>
							<td>
								<input name="carroad.permitNum" id="permitNum" size="20"
									class="permitNum isremove put3" />
							</td>
							<td align="right">
								吨（座）位：
							</td>
							<td>
								<input name="carroad.tonnage" id="tonnage" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								车辆长宽高：
							</td>
							<td>
								<input name="carroad.carLwh" id="carLwh" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>经营范围：
							</td>
							<td>
								<input name="carroad.businessRange" id="businessRange" size="20"
									class="businessRange isremove put3" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>发证日期：
							</td>
							<td>
								<input name="carroad.cardDate" onfocus="date(this);"
									id="cardDate" size="20" class="cardDate isremove put3" />
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
		<!-- 道路运输证修改页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 65%; top: 5%" id="jqModelup">
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
					<table width="100%" border="0" id="stafftableupdate"
						cellpadding="5" cellspacing="5">
						<tr>
							<td style="width: 15%;" align="right">
								业户名称：
							</td>
							<td style="width: 33%;">
								<input name="carroad.baseName" value="${carroad.baseName }"
									id="baseName" size="20" />
							</td>
							<td style="width: 19%;" align="right">
								地址：
							</td>
							<td>
								<input name="carroad.roadAddress"
									value="${carroad.roadAddress }" id="roadAddress" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								经营许可证号：
							</td>
							<td>
								<input name="carroad.permitNum" value="${carroad.permitNum }"
									id="permitNum" size="20" />
							</td>
							<td align="right">
								吨（座）位：
							</td>
							<td>
								<input name="carroad.tonnage" value="${carroad.tonnage }"
									id="tonnage" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								车辆长宽高：
							</td>
							<td>
								<input name="carroad.carLwh" value="${carroad.carLwh }"
									id="carLwh" size="20" />
							</td>
							<td align="right">
								经营范围：
							</td>
							<td>
								<input name="carroad.businessRange"
									value="${carroad.businessRange }" id="businessRange" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								<div align="right">
									发证日期:
								</div>
							</td>
							<td>
								<input name="carroad.cardDate" value="${carroad.cardDate}"
									onfocus="date(this);" id="cardDate" 
									size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<input name="carInformation.carID" id="contactUserID"
										type="hidden" class="input" size="20" />
									<input type="button" class="input-button JQuerySubmits"
										style="cursor: pointer; width: 80px;" value="提交" />
									<input name="sub" value="${session_value}" type="hidden" />
									<!-- 代替token-->
									<input type="button" class="input-button JQueryreturns"
										style="cursor: pointer; width: 80px;" value="取消" />
								</div>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		<!-- 查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 15%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" width="100%" cellpadding="5"
					cellspacing="5">

					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carroad.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carroad.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carroad.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="carroad.carpeople" />
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
