<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆基本信息管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<style type="text/css">
.wenben {
	border-color: #FFFFFF;
	border-width: 0px;
	background: url('../../../images/ea/office/brue.jpg')
}
</style>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
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
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js">
		</script>
		<script src="<%=basePath%>js/ea/office_ea/carBaseInfo/CarBaseInfoList.js"></script>
		<script type="text/javascript">
         var treeID = '<%=session.getAttribute("organizationID")%>';
         var treePName='<%=session.getAttribute("organizationName")%>';
         var treeNames='<%=c.getCompanyName()%>';
		 var  basePath='<%=basePath%>';           
         var pNumber =${pageNumber};  
         var  search='${search}';  
         var carNums="";
         var personurl = "";
         var carID = "";
         var token=0;
         var treeid;
  		 var treename;
		 var goodsBillsID="";
		 var notoken = 0;
		 var tokens = 0;
		 var token = 0;
		 var companyID='${account.companyID}';
   		</script>


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
						<th width="150" align="center">
							车牌号
						</th>
						<th width="100" align="center">
							发动号
						</th>
						<th width="100" align="center">
							车辆类型
						</th>
						<th width="150" align="center">
							车架号
						</th>
						<th width="150" align="center">
							车辆品牌
						</th>
						<th width="100" align="center">
							注册日期
						</th>
						<th width="200" align="center">
							责任人
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${carID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${carID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="carNum">${carNum}</span>
							</td>
							<td>
								<span id="engineType">${engineType}</span>
							</td>
							<td>
								<span id="carType">${carType}</span>
							</td>
							<td>
								<span id="carFrameNum">${carFrameNum}</span>
							</td>

							<td>
								<span id="vehicleBrand">${vehicleBrand}</span>
							</td>
							<td>
								<span id="registrationDate">${registrationDate}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
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
					value="ea/carbaseinfo/ea_getCarBaseInfoList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
				framespacing="0" height="0"></iframe>
		</div>
		
		
		<!-- 车辆添加 -->
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">车辆管理
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="99%" id="table3">	
				     <tr>
				     <td>
				     <table width="699" height="263" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     <tr>
						<td  height="40" align="right"><span class="STYLE1">公司名称：</span></td>
						<td ><span id="companyNames"></span></td>
						<td height="40" align="right">责 任 人：</td>
						<td><input type="hidden" id="partnerID" name="carInformation.staffID" readonly="readonly" value="${carInformation.staffID}" />
							<input type="text" id="staffName" name="carInformation.staffName" class="put3" readonly="readonly" value="${carInformation.staffName}" size="15"/>&nbsp;<a href="#" onclick="importGY('table3','partnerID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a></td>
					</tr>
					<tr>
						<td height="40" align="right">部门名称：</td>
						<td ><span id="dept"></span></td>
						<td height= "46" align="right">选择车辆：</td>
                       	<td><input type="button" id="newG" name="button7"value="选择车辆" /></td>
					</tr>
                    <tr>
                      	<td height="46" align="right">车 牌 号：</td>
                      	<td ><span class="carNum" id="carNum">${carInformation.carNum}</span></td>
                      	<td height="46" align="right">发动机号：</td>
                      	<td ><span id="engineNum"></span></td>
                    </tr>
                    <tr>
                        <td height="46"  align="right">车辆类型：</td>
                        <td ><span id="carType"></span></td>
                        <td  align="right">车 架 号：</td>
                        <td ><span id="carFrameNum"></span></td>
                    </tr>
                    <tr>
	                    <td height="46" align="right">注册日期：</td>
	                     <td><span id="registrationDate"></span></td>
	                     <td height = "46" align="right">使用状态：</td>
                     	 <td><select name="carInformation.state3" id="state3">
						     <option value="00">未使用</option>
						    <option value="01">已使用</option>
						   </select></td>
                    </tr>
                     <tr>
		                  <td align="right">加盟状态：</td>
		                  <td>
		                  <select name="carInformation.state1" id="j_state">
					     <option value="00">加盟车</option>
					    <option value="01">本校车</option>
                              <option value="02">教练车</option>
					   </select></td>
                    </tr>
                         <tbody id="j_body" style="display: none;">
                         <tr>
                             <td height = "46" align="right">
                                 培训车型：</td>
                             <td>
                                 <select  name="tcar.perdritype" style="width:140px;">
                                     <option value="A1">A1</option>
                                     <option value="A2">A2</option>
                                     <option value="A3">A3</option>
                                     <option value="B1">B1</option>
                                     <option value="B2">C2</option>
                                     <option value="C1">C1</option>
                                     <option value="C2">C2</option>
                                     <option value="C3">C3</option>
                                     <option value="C4">C4</option>
                                     <option value="C5">C5</option>
                                     <option value="D">D</option>
                                     <option value="E">E</option>
                                     <option value="F">F</option>
                                     <option value="M">M</option>
                                     <option value="N">N</option>
                                     <option value="P">P</option>
                                 </select>
                             </td>
                             <td height = "46" align="right">
                                 技术等级：</td>

                             <td><select  name="tcar.skillLevel" style="width:140px;">
                                 <option value="1">无</option>
                                 <option value="2">一级</option>
                                 <option value="3">二级</option>
                                 <option value="4">三级</option>
                             </select>
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 燃油：</td>
                             <td> <select  name="tcar.fuel" style="width:140px;">
                                 <option value="1">无</option>
                                 <option value="2">汽油</option>
                                 <option value="3">3柴油</option>
                                 <option value="4">4其他</option>
                                 <option value="5">双燃油</option>
                             </select>
                             </td>
                             <td height = "46" align="right">
                                 车辆颜色：</td>
                             <td><select  name="tcar.plateColor" style="width:140px;">
                                 <option value="1">蓝色</option>
                                 <option value="2">黄色</option>
                                 <option value="3">黑色</option>
                                 <option value="4">白色</option>
                                 <option value="5">绿色</option>
                                 <option value="9">9其他</option>
                             </select>
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 生产厂家：</td>
                             <td><input type="text" style="width:140px;" name="tcar.manufacture">
                             </td>
                             <td height = "46" align="right">
                                 厂牌型号：</td>
                             <td><input type="text" style="width:140px;" name="tcar.model">
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 购买时间：</td>
                             <td><input type="text" style="width:140px;" name="tcar.buydate" onfocus="daytime(this);">
                             </td>
                             <td height = "46" align="right">
                                 载客量：</td>
                             <td><input type="text" style="width:140px;" name="tcar.zkl">
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 检测情况：</td>
                             <td><input type="text" style="width:140px;" name="tcar.equipStatus">
                             </td>
                             <td height = "46" align="right">
                                 备注：</td>
                             <td><input type="text" style="width:140px;" name="tcar.remark">
                             </td>
                         </tr>
                         </tbody>
                    <tr>
                      <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
                          <input name="carInformation.carID" id="contactUserID" type="hidden" class="input"  size="20"/>
                          <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                         <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                          <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
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
		<form name="carSearchForm" id="carSearchForm" method="post">
		   <input type="submit" id="submit"  />
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable" cellpadding="5" cellspacing="5">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carInformation.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carInformation.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<input name="carInformation.staffName" />
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
		
		
			<%------------------------------------物品选择------------------------------------%>
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
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
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
		
		
		 <!----------------------------------------选择责任人---------------------------------------- -->
        <div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

        <script type="text/javascript">
            $("#j_state").change(function () {
                if($(this).val()=="02"){
                    $("#j_body").show();
                    $("#mainframe").attr("height",632+"px");
                }else {
                    $("#j_body").hide();
                }
            })
        </script>
	</body>

</html>