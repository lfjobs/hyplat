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
		<title>购置税发票</title>
		<style type="text/css">
		.xx{
			color:#FF0000;
			margin-right:2px;} 
		</style>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
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
		<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var purchaseTaxID = '';
			var  search='${search}';
			var personurl = "";
			var notoken = 0;
			var pNumber = ${pageNumber};
			var carID=parent.carID;
			var token=0;
			var select = 1;
			var type='${type}';
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carPurchaseTax_edit.js"></script>
	</head>
	<body>
		<form name="carPurchaseTaxForm" id="carPurchaseTaxForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="main_main" class="main_main">
			<!-- <input type="hidden" id="thisdate" /> -->
				<table class="JQueryflexme">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="80" align="center">
								车牌号
							</th>
							<th width="80" align="center">
								车辆类型
							</th>
							<th width="100" align="center">
								发动机号码
							</th>
							<th width="200" align="center">
								负责人
							</th>
							<th width="80" align="center">
								发票代号
							</th>
							<th width="80" align="center">
								开票日期
							</th>
							<th width="60" align="center">
								车主
							</th>
							<th width="80" align="center">
								厂牌型号
							</th>
							<th width="60" align="center">
								国产/进口
							</th>
							<th width="60" align="center">
								车辆计税价格
							</th>
							<th width="60" align="center">
								缴税金额
							</th>
							<th width="60" align="center">
								滞纳金
							</th>
							<th width="80" align="center">
								完税证明号码
							</th>
							<th width="80" align="center">
								代收单位
							</th>
							<th width="60" align="center">
								收款人
							</th>
							<th width="100" align="center">
								备注
							</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${purchaseTaxID}" class="td_bg01 saveAjax"
								class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${purchaseTaxID}" />
									<input type="hidden" name="purchaseTaxKey" id="purchaseTaxKey"
										value="${purchaseTaxKey}" />
									<input type="hidden" name="purchaseTaxID" id="purchaseTaxID"
										value="${purchaseTaxID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID"
										value="${carPurchaseTax.carID}" id="carID" />
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
									<span id="invoiceCode">${invoiceCode}</span>
								</td>
								<td class="td_bg01">
									<span id="invoiceDate">${fn:substring(invoiceDate, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="carName">${carName}</span>
								</td>
								<td class="td_bg01">
									<span id="brandModel">${brandModel}</span>
								</td>
								<td class="td_bg01">
									<span id="originType">${originType=='国产'?'国产':'进口'}</span>
								</td>
								<td class="td_bg01">
									<span id="taxprice">${taxprice}</span>
								</td>
								<td class="td_bg01">
									<span id="tax">${tax}</span>
								</td>
								<td class="td_bg01">
									<span id="penalty">${penalty}</span>
								</td>
								<td class="td_bg01">
									<span id="dutyPaidNum">${dutyPaidNum}</span>
								</td>
								<td class="td_bg01">
									<span id="collectors">${collectors}</span>
								</td>
								<td class="td_bg01">
									<span id="payee">${payee}</span>
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<span id="purchaseTaxID" style="display:none">${purchaseTaxID}</span>
                                 	<span id="purchaseTaxKey"  style="display:none">${purchaseTaxKey}</span>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
              <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
              <iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
			</div>
		</form>
		<!-- 车辆购置税发票添加页面 -->
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="width: 680px; left: 60%; top: 15%" id="jqModel">
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
					<table width="100%" border="0" id="stafftable" cellpadding="5" cellspacing="5">

						<tr>
							<td style="width:15%;"align="right">
								<span class="xx">*</span>发票代号：
							</td>
							<td style="width:33%;">
								<input name="carPurchaseTax.invoiceCode" id="invoiceCode"
									class="invoiceCode isremove put3" size="20" />
							</td>
							<td style="width:5%;" align="right">
								<span class="xx">*</span>开票日期：
							</td>
							<td>
								<input name="carPurchaseTax.invoiceDate" onfocus="date(this);"
									id="invoiceDate" class="invoiceDate isremove put3" size="20" />
							</td>
							

						</tr>
						<tr>
							<td align="right">
								<span class="xx">*</span>厂牌型号：
							</td>
							<td>
								<input name="carPurchaseTax.brandModel" id="brandModel"
									class="brandModel isremove put3" size="20" />
							</td>
							
							<td style="width:18%;" align="right" >
								<span class="xx">*</span>车辆计税价格：
							</td>
							<td>
								<input name="carPurchaseTax.taxprice" id="taxprice"
									class="taxprice isremove put3" size="20" />
							</td>

						</tr>
						
						<tr>
					      <td style="width:8%;"align="right">
								<span class="xx">*</span>车主：
							</td>
							<td>
								<input name="carPurchaseTax.carName" id="carName"
									class="carName isremove put3" size="20" />
							</td>
							
							<td align="right">
								国产/进口：
							</td>
							<td>
								<select name="carPurchaseTax.originType" id="originType">
									<option value="国产">
										国产
									</option>
									<option value="进口">
										进口
									</option>
								</select>
							</td>
							

						</tr>
						<tr>

							<td align="right">
								<span class="xx">*</span>缴税金额：
							</td>
							<td>
								<input name="carPurchaseTax.tax" id="tax"
									class="tax isremove put3" size="20" />
							</td>
							<td align="right">
								<span class="xx">*</span>滞纳金：
							</td>
							<td>
								<input name="carPurchaseTax.penalty" id="penalty"
									class="penalty isremove put3" size="20" />
							</td>
							
						</tr>
						<tr>
						<td align="right">
								<span class="xx">*</span>完税证明号码：
							</td>
							<td>
								<input name="carPurchaseTax.dutyPaidNum" id="dutyPaidNum"
									class="dutyPaidNum isremove put3" size="20" />
							</td>
							<td align="right">
								代收单位：
							</td>
							<td>
								<input name="carPurchaseTax.collectors" id="collectors"
									size="20" />
							</td>
						</tr>
						<tr>


							
							<td align="right">
								<span class="xx">*</span>收款人：
							</td>
							<td>
								<input name="carPurchaseTax.payee" id="payee"
									class="payee isremove put3" size="20" />
							</td>
							<td align="right">
								备注：
							</td>
							<td >
								<input name="carPurchaseTax.remarks" id="remarks" size="20" />
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
		<!-- 车辆购置税发票 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 60%; top: 15%" id="jqModelup">
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
					<table width="100%" border="0" cellpadding="5" cellspacing="5"
						id="stafftableupdate">
						<tr>
							<td align="right">
								发票代号:
							</td>
							<td>
								<input name="carPurchaseTax.invoiceCode"
									value="${carPurchaseTax.invoiceCode }" id="invoiceCode"
									size="20" />
							</td>
							<td align="right">
								开票日期:
							</td>
							<td>
								<input name="carPurchaseTax.invoiceDate"
									value="${carPurchaseTax.invoiceDate}" onfocus="date(this);"
									id="invoiceDate" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								车主:
							</td>
							<td>
								<input name="carPurchaseTax.carName"
									value="${carPurchaseTax.carName }" id="carName" size="20" />
							</td>
							<td align="right">
								厂牌型号:
							</td>
							<td>
								<input name="carPurchaseTax.brandModel"
									value="${carPurchaseTax.brandModel}" id="brandModel" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								国产/进口:
							</td>
							<td>
								<select name="carPurchaseTax.originType" id="originType"
									value="${carPurchaseTax.originType }">
									<option value="国产">
										国产
									</option>
									<option value="进口">
										进口
									</option>
								</select>
							</td>
							<td align="right">
								车辆计税价格:
							</td>
							<td>
								<input name="carPurchaseTax.taxprice"
									value="${carPurchaseTax.taxprice}" id="taxprice" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								缴税金额:
							</td>
							<td>
								<input name="carPurchaseTax.tax" value="${carPurchaseTax.tax }"
									id="tax" size="20" />
							</td>
							<td align="right">
								滞纳金:
							</td>
							<td>
								<input name="carPurchaseTax.penalty"
									value="${carPurchaseTax.penalty}" id="penalty" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								完税证明号码:
							</td>
							<td>
								<input name="carPurchaseTax.dutyPaidNum"
									value="${carPurchaseTax.dutyPaidNum }" id="dutyPaidNum"
									size="20" />
							</td>
							<td align="right">
								代收单位:
							</td>
							<td>
								<input name="carPurchaseTax.collectors"
									value="${carPurchaseTax.collectors}" id="collectors" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								收款人:
							</td>
							<td>
								<input name="carPurchaseTax.payee"
									value="${carPurchaseTax.payee }" id="payee" size="20" />
							</td>
							<td align="right">
								备注:
							</td>
							<td>
								<input name="carPurchaseTax.remarks"
									value="${carPurchaseTax.remarks }" id="remarks" size="20" />
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div align="center">
									<input name="carPurchaseTax.purchaseTaxID" id="purchaseTaxID"
										type="hidden" class="input" size="20" />
									<input name="carPurchaseTax.purchaseTaxKey" id="purchaseTaxKey"
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
		
		<!-- 购置税发票查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
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
							<input name="carPurchaseTax.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carPurchaseTax.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							发动机号：
						</td>
						<td>
							<input name="carPurchaseTax.engineNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="carPurchaseTax.carpeople" />
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
