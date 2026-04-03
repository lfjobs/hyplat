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
		<title>车辆接送信息管理</title>
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
			var quasiID = '';
			var search='${search}';
			var personurl = "";
			var notoken = 0;
			var pNumber = ${pageNumber};
			var carID=''
			var token=0;
			var zhi=$("#seatState").text();
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carshuttle_edit.js"></script>
	</head>
	<body>
		<form name="carquasiForm" id="carquasiForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div id="main_main" class="main_main">
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
								车型
							</th>
							<th width="80" align="center">
								车架号
							</th>
							<th width="50" align="center">
								排号
							</th>
							<th width="50" align="center">
								座号
							</th>
							<th width="60" align="center">
								座位状态
							</th>
							<th width="50" align="center">
								准载人数
							</th>
							<th width="50" align="center">
								实载人数
							</th>
							<th width="50" align="center">
								超载人数
							</th>
							<th width="100" align="center">
								车辆所属单位
							</th>
							<th width="100" align="center">
								单位责任人
							</th>
							<th width="100" align="center">
								单位联系方式
							</th>
							<th width="50" align="center">
								驾驶员
							</th>
							<th width="80" align="center">
								手机号
							</th>
							<th width="80" align="center">
								接送起地点
							</th>
							<th width="80" align="center">
								接送止地点
							</th>
							<th width="80" align="center">
								接送起时间
							</th>
							<th width="80" align="center">
								接送止时间
							</th>
							<th width="80" align="center">
								乘坐方式
							</th>
							<th width="50" align="center">
								启程时间
							</th>
							<th width="50" align="center">
								抵达时间
							</th>
							<th width="80" align="center">
								单位
							</th>
							<th width="80" align="center">
								乘坐人姓名
							</th>
							<th width="80" align="center">
								手机号
							</th>
							<th width="80" align="center">
								承办单位
							</th>
							<th width="80" align="center">
								承办单位责任人
							</th>
							<th width="80" align="center">
								承办人联系方式
							</th>
							<th width="100" align="center">
								备注
							</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr id="${quasiID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${quasiID}" />
									<input type="hidden" name="quasiKey" id="quasiKey"
										value="${quasiKey}" />
									<input type="hidden" name="quasiID" id="quasiID"
										value="${quasiID}" />
									<input type="hidden" name="companyID" id="companyID"
										value="${companyID}" />
									<input type="hidden" name="organizationID" id="organizationID"
										value="${organizationID}" />
									<input type="hidden" name="carID" value="${carquasi.carID}"
										id="carID" />
								</td>
								<td class="td_bg01">
									<span id="carNum">${carNum}</span>
								</td>
								<td class="td_bg01">
									<span id="carType">${carType}</span>
							
								</td>
								<td class="td_bg01">
									<span id="carFrameNum">${carFrameNum}</span>
								</td>
								<td class="td_bg01">
									<span id="numeral">${numeral}</span>
								</td>
								<td class="td_bg01">
									<span id="zuonum">${zuonum}</span>
								</td>
								<td class="td_bg01">
									<span id="seatState1">${seatState=='00'?'未坐':'已坐'}</span>
									<span id="seatState" style="display:none">${seatState=='00'?'00':'01'}</span>
								</td>
								<td>
	                                <span id="QuasiLoad">${QuasiLoad}</span>
	                            </td>
	                            <td>
	                                <span id="realLoad">${realLoad}</span>
	                            </td>
	                            <td>
	                                <span id="overLoad">${overLoad}</span>
	                            </td>
								 <td>
                                <span id="carUtil">${carUtil}</span>
	                            </td>
	                            <td>
	                                <span id="utilPeople">${utilPeople}</span>
	                            </td>
								<td class="td_bg01">
									<span id="utilTel">${utilTel}</span>
								</td>
								<td class="td_bg01">
									<span id="driver">${driver}</span>
								</td>
								<td class="td_bg01">
									<span id="driverTel">${driverTel}</span>
								</td>
								<td class="td_bg01">
								<span id="beganPlace" style="display:none">${beganPlace}</span>
								<s:select list="addlisyt" listKey="codeID" listValue="codeValue" name="beganPlace"  theme="simple"></s:select>
								</td>
								<td class="td_bg01">
									<span id="endPlace">${endPlace}</span>
								</td>
								<td class="td_bg01">
									<span id="beganTime">${fn:substring(beganTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="endTime">${fn:substring(endTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
								<span id="byWay" style="display:none">${byWay}</span>
									<s:select   list="bywaylist" class="dis model1"  name="byWay"  listKey="codeID" listValue="codeValue" style="display:none"  theme="simple" ></s:select>
								</td>
								<td class="td_bg01">
									<span id="departureTime">${fn:substring(departureTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="arriveTime">${fn:substring(arriveTime, 0, 10)}</span>
								</td>
								<td class="td_bg01">
									<span id="util">${util}</span>
								</td>
								<td class="td_bg01">
									<span id="passenger">${passenger}</span>
								</td>
								<td class="td_bg01">
									<span id="passengerTel">${passengerTel}</span>
								</td>
								<td class="td_bg01">
									<span id="undertakeUtil">${undertakeUtil}</span>
								</td>
								<td class="td_bg01">
									<span id="undertakePeo">${undertakePeo}</span>
								</td>
								<td class="td_bg01">
									<span id="undertakeTel">${undertakeTel}</span>
								</td>
								<td class="td_bg01">
									<span id="remark">${remark}</span>
									<span id="quasiID" style="display:none">${quasiID}</span>
                                 	<span id="quasiKey"  style="display:none">${quasiKey}</span>
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				  <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/shuttle/ea_getCarQuasiList.jspa?carquasi.carID=${carquasi.carID}&pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
        </div>
        </form>
         <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 5%" id="jqModel">
            <form name="addseatForm" id="addseatForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">车辆管理
				    <div class="close"></div>
				  </div>
				  </div>
				 <table width="100%" height="278"  border="0" id="stafftable">
                      <tr>
                        <td width="10%" height="32" align="right">选择车辆：</td>
                        <td width="10%"><input type="button" id="newG" name="button7"value="选择车辆" size="10"/></td>
                        <td width="10%" align="right">车牌号：</td>
                        <td width="10%"><span id="carNum"></span></td>
                        <td width="10%" align="right">车型：</td>
                        <td width="10%"><span id="carType"></span></td>
                        <td width="10%" align="right">车架号：</td>
                        <td width="10%"><span id="carFrameNum"></span></td>
                      </tr>
                      <tr>
                        <td align="right" height="29">准载人数：</td>
                        <td><span id="ratifyPeople"></span></td>
                        <td align="right">实载人数：</td>
                        <td><span id="rPeoples">0</span></td>
                        <td align="right">超载人数：</td>
                        <td><span id="oPeople">0</span></td>
                        <td align="right">座位状态：</td>
                        <td><select name="carquasi.seatState" id="seatState">
                            <option value="00">未坐</option>
                            <option value="01">已坐</option>
                          </select>
                        </td>
                      </tr>
                      <tr>
                        <td align="right" height="29">排号：</td>
                        <td><input name="carquasi.numeral"  id="numeral" size="10"/></td>
                        <td align="right" >座号：</td>
                        <td><input name="carquasi.zuonum"   id="zuonum" size="10"/></td>
                        <td align="right" >选择单位：</td>
                        <td><input type="button" name="button5" value="选择单位" id="xzwlaw" size="10"/></td>
                        <td align="right" >车辆所属单位：</td>
                        <td><span id="ccompanyname"></span></td>
                      </tr>
                      <tr>
                        <td align="right" height="29">单位责任：</td>
                        <td><span id="cresponsible"></span></td>
                        <td align="right">单位联系方式：</td>
                        <td ><span id="responsibleTel"></span></td>
                        <td align="right"><span class="xx">*</span>驾驶员：</td>
                        <td><input name="carquasi.driver"  id="driver"  size="10" class="notnull model3"/></td>
                        <td align="right"><span class="xx">*</span>手机号：</td>
                        <td><input name="carquasi.driverTel"  id="driverTel"  size="10" class="notnull model3"/></td>
                   </tr>
                      <tr>
                        <td align="right">接送起地点：</td>
                        <td><s:select   list="addlisyt" class="dis model1"  name="carquasi.beganPlace"   listKey="codeID" listValue="codeValue"   theme="simple" ></s:select>                        </td>
                        <td align="right">接送止地点：</td>
                        <td><input name="carquasi.endPlace"  id="endPlace"  size="10" /></td>
                        <td align="right"><span class="xx">*</span>接送起时间：</td>
                        <td><input name="carquasi.beganTime"  id="beganTime"  onfocus="date(this);" size="10" class="notnull model3"/></td>
                        <td align="right">接送止时间：</td>
                        <td><input name="carquasi.endTime"  id="endTime" onfocus="date(this);" size="10" /></td>
                      </tr>
                      <tr>
                        <td height="29" align="right">乘坐方式：</td>
                        <td><s:select   list="bywaylist" class="dis model1"  name="carquasi.byWay"   listKey="codeID" listValue="codeValue"  theme="simple" ></s:select></td>
                        <td align="right"><span class="xx">*</span>启程时间：</td>
                        <td><input name="carquasi.departureTime"  id="departureTime" onfocus="date(this);" size="10" class="notnull model3"/></td>
                        <td align="right">抵达时间：</td>
                        <td><input name="carquasi.arriveTime"  id="arriveTime"  onfocus="date(this);" size="10" /></td>
                        <td align="right"><span class="xx">*</span>单位：</td>
                        <td><input name="carquasi.util"  id="util"  size="10" class="notnull model3"/></td>
                      </tr>
                      <tr>
                        <td align="right"><span class="xx">*</span>乘坐人姓名：</td>
                        <td><input name="carquasi.passenger"  id="passenger"  size="10" class="notnull model3"/></td>
                        <td height="29" align="right"><span class="xx">*</span>手机号：</td>
                        <td><input name="carquasi.passengerTel"  id="passengerTel"  size="10" class="notnull model3"/></td>
                        <td align="right"><span class="xx">*</span>承办单位：</td>
                        <td><input name="carquasi.undertakeUtil"  id="undertakeUtil"  size="10" class="notnull model3"/></td>
                        <td align="right"><span class="xx">*</span>承办单位责任人：</td>
                        <td><input name="carquasi.undertakePeo"  id="undertakePeo"  size="10" class="notnull model3"/></td>
                      </tr>
                      <tr>
                        <td align="right"><span class="xx">*</span>承办人联系方式：</td>
                        <td><input name="carquasi.undertakeTel"  id="undertakeTel"  size="10" class="notnull model3"/></td>
                        <td align="right">备注：</td>
                        <td colspan="5"><input name="carquasi.remark"  id="remark"  size="80" /></td>
                      </tr>
                        <tr>
                      <td height="30" colspan="10" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
                          <input name="carInformation.carID" id="contactUserID" type="hidden" class="input"  size="20"/>
                          <input name="contactCompany.ccompanyID" id="ccompanyID" type="hidden" class="input"  size="20"/>
                          <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                         <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                          <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                    </tr>
                    </table>
				</div>
				<s:token></s:token>
            </form>
        </div>
        <!-- 车辆接送信息修改页面 -->
         <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 5%" id="jqModelup">
            <form name="updateseatForm" id="updateseatForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">车辆管理
				    <div class="close"></div>
				  </div>
				  </div>
				 <table width="100%" height="266"  border="0" id="stafftables">
                      <tr>
                        <td width="10%" height="32">座位状态:</td>
                        <td width="10%">
                        	<select name="carquasi.seatState" id="seatState" >
											<option id="gc" value="00">
												未坐
											</option>
											<option id="jk" value="01">
												已坐
											</option>
						   </select>  
                        </td>
                        <td width="10%">排号:</td>
                        <td width="10%"><input name="carquasi.numeral"  value="${carquasi.numeral }"  id="numeral" /></td>
                        <td width="10%">座号</td>
                        <td width="10%"><input name="carquasi.zuonum"  value="${carquasi.zuonum }"  id="zuonum"/></td>
                        <td width="10%">驾驶员:</td>
                        <td width="10%"><input name="carquasi.driver"  value="${carquasi.driver }"  id="driver"/></td>
                      </tr>
                      <tr>
                        <td height="29">手机号:</td>
                        <td><input name="carquasi.driverTel"  value="${carquasi.driverTel }"  id="driverTel"/></td>
                        <td>接送起地点:</td>
                        <td>
                        		<s:select   list="addlisyt" class="dis model1"  name="carquasi.beganPlace" id="ss1"  listKey="codeID" listValue="codeValue"   theme="simple" ></s:select>
                        </td>
                        <td>接送止地点</td>
                        <td><input name="carquasi.endPlace"  value="${carquasi.endPlace }"  id="endPlace"/></td>
                        <td>接送起时间</td>
                        <td><input name="carquasi.beganTime"  value="${carquasi.beganTime }"  id="beganTime" onfocus="date(this);"/></td>
                      </tr>
                      <tr>
                      	 <td height="29">接送止间时间</td>
                        <td><input name="carquasi.endTime"  value="${carquasi.endTime }"  id="endTime" onfocus="date(this);"/></td>
                        <td>乘坐方式</td>
                        <td><s:select   list="bywaylist" class="dis model1"  name="carquasi.byWay"  id="ss2" listKey="codeID" listValue="codeValue"   theme="simple" ></s:select></td>
                        <td >启程时间</td>
                        <td><input name="carquasi.departureTime"  value="${carquasi.departureTime }"  id="departureTime" onfocus="date(this);"/></td>
                        <td>抵达时间</td>
                        <td><input name="carquasi.arriveTime"  value="${carquasi.arriveTime }"  id="arriveTime" onfocus="date(this);"/></td>
                      </tr>
                      <tr>
                      	<td height="29">单位</td>
                        <td><input name="carquasi.util"  value="${carquasi.util }"  id="util"/></td>
                        <td>乘坐人姓名</td>
                        <td><input name="carquasi.passenger"  value="${carquasi.passenger }"  id="passenger"/></td>
                        <td>手机号</td>
                        <td><input name="carquasi.passengerTel"  value="${carquasi.passengerTel }"  id="passengerTel"/></td>
                        <td >承办单位</td>
                        <td><input name="carquasi.undertakeUtil"  value="${carquasi.undertakeUtil }"  id="undertakeUtil"/></td>
                      </tr>
                      <tr>
                      	<td height="29">承办单位责人任</td>
                        <td><input name="carquasi.undertakePeo"  value="${carquasi.undertakePeo }"  id="undertakePeo"/></td>
                        <td>承办人联系方式</td>
                        <td><input name="carquasi.undertakeTel"  value="${carquasi.undertakeTel }"  id="undertakeTel"/></td>
                        <td>备注</td>
                        <td colspan="3"><input name="carquasi.remark"  value="${carquasi.remark }"  id="remark"/></td>
                      </tr>
                        <tr>
                      <td height="30" colspan="10" align="center"><input name="carquasi.quasiID" id="quasiID" type="hidden" class="input"  size="20"/>
                          <input name="carquasi.carID" id="carID" type="hidden" class="input"  size="20"/>
                          <input name="carquasi.quasiKey" id="quasiKey" type="hidden" class="input"  size="20"/>
                          <input type="button"   class="input-button JQuerySubmits" style="cursor:pointer;width:80px;" value="提交" />
                         <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                          <input type="button"  class="input-button JQueryreturns" style="cursor:pointer;width:80px;"  value="取消" />                          </td>
                    </tr>
                    </table>
				</div>
				<s:token></s:token>
            </form>
        </div>
        <!-- 车辆选择 -->
        <%------------------------------------车辆选择------------------------------------%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择车辆
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td  align="right">
								车牌号：
							</td>
							<td >
								<input name="carNum" class="input" id="carNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td  align="right">
								车架号：
							</td>
							<td >
								<input name="carFrameNum" class="input" id="carFrameNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td align="right">
								车型：
							</td>
							<td >
								<input name="carType" class="input" id="carType" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdcar"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturnutil" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<!-- 查询信息 -->
		<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					<tr>
						<td height="40" >
							查询条件 
						</td>
					</tr>
					<tr>
						<td height="40">
							车牌号：
						</td>
						<td>
							<input name="carquasi.carNum" />
						</td>
					</tr>
					<tr>
						<td height="40">
							车辆类型：
						</td>
						<td>
							<input name="carquasi.carType" />
						</td>
					</tr>
					<tr>
						<td height="40">
							车架号：
						</td>
						<td>
							<input name="carquasi.carFrameNum" />
						</td>
					</tr>
					<tr>
						<td height="40">
							车辆所属单位：
						</td>
						<td>
							<input name="carquasi.carUtil" />
						</td>
					</tr>
					<tr>
						<td height="40">
							单位责任人：
						</td>
						<td >
							<input name="carquasi.utilPeople" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
        <!--JS遮罩层-->
		<div id="fullbg"></div>
	</body>
</html>
