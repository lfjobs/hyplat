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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>账户登记资料</title>
	<!-- 总公司 子公司 各个部门 机构银行帐号列表 --> 
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
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript"src="<%=basePath%>js/accifr/js/accift.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/human/institutionsRegistration.js"></script>
	
</head>
<SCRIPT type="text/javascript">
		   var select =1;
		   var registrationID = '';
		   var basePath = '<%=basePath%>';
		   var organizationPID='${institutionsRegistration.organizationPID}';
           var ppageNumber=${pageNumber};
          	var search='${search}';
           var token=0;
           var notoken=0;
           var ogID = '${institutionsRegistration.organizationPID}';
			var ogName = '<%=request.getParameter("ogName") %>';
			//var ogName = '${ogName}';
    </SCRIPT>
	<body>
	
		<!-- 银行地址 -->
		 <div class="jqmWindow" style="width: 600px;right: 25%;top:10%" id="jqModelSearch">
                                            <div class="drag">
                                               选择地址
                                                <div class="close">
                                                </div>
                                            </div>
                                            <table id="cataffSearchTable"> 
                                                <tr> 
				                            	<td class="JQueryaddresssearch" >
				                            		 <input name="searchCStaff.address" id="address"  type="hidden"/>
				                            		 <input name="searchCStaff.staffAddress" id="staffAddress"   type="hidden"/>
												     <select name="addressProvince" id="province" number='0' style="width:110px;" ></select><!-- <option>选择省</option>-->
												     <select name="addressCity" id="city" number='1' style="width:110px;"></select>
												     <select name="addressCounty" id="county" number='2'  style="width:110px;"></select>
												     <select name="addressTown" id="addressTown" number='3'  style="width:110px;"></select>
												     <select name="addressVillage" id="addressVillage" number='4'  style="width:110px;"></select>
												     <select name="addressCommunity" id="addressCommunity" number='5' style="width:110px;" ></select><!-- <option>选择省</option>-->
												     <select name="addressFloor" id="addressFloor" number='6' style="width:110px;"></select>
												     <select name="addressLayer" id="addressLayer" number='7'  style="width:110px;"></select>
												     <select name="addressSize" id="addressSize" number='8'  style="width:110px;"></select>
				                                </td>
				                           	</tr>
                                            </table>
                                            <div align="center">
                                               <input class="input-button" type = "button" value="确定"  id = "addrOK"/>
	         								   <input class="input-button" type = "button" value="取消" id = "addrREST"/>
                                            </div> 
                                    </div>
		
	
		<form  name="staffappraisalForm" enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="hidden" name="institutionsRegistration.organizationPID" value="${institutionsRegistration.organizationPID}"></input>
			<input type="submit" name="submit" style="display: none" />
		<div id="main_main" >
			<table class="registration">
				<thead>
					<tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="160" align="center">
							账号编号
						</th>
						<th width="180" align="center">
							银行开户登记证明号(审核号)
						</th>
						<th width="70" align="center">
							发证日期
						</th>
						<th width="100" align="center">
							账户性质(使用类别)
						</th>
						<th width="100" align="center">
							银行名称
						</th>
						<th width="100" align="center">
							开户账号
						</th>
						<th width="70" align="center">
							是否开通网银
						</th>	
						<th width="100" align="center">
							网银注册行
						</th>
						<th width="70" align="center">
							开户时间
						</th>
						<th width="70" align="center">
							注销时间
						</th> 
						<th width="85" align="center">
							联系方式
						</th> 
						<th width="80" align="center">
							账户责任人
						</th> 
						<th width="80" align="center">
							证件类型
						</th> 
						<th width="150" align="center">
							证件号码
						</th> 
						<th width="80" align="center">
							出生日期
						</th> 
						<th width="180" align="center">
							开户银行地址
						</th>  
					</tr>
				</thead>
                <tbody  id="tbwid">
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2 ">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${registrationID}" />
						</td>
						<td class="td_bg01">
							<input name="accountNum" id="accountNum" class="nummin"/></td>
						<td class="td_bg01">
							<input name="bankRegistrationID" id="bankRegistrationID" class="nummin"/></td>
						<td class="td_bg01">
							<input name="registrationDate" id="registrationDate" onfocus="date(this); " size="10"/>
						</td>
						<td class="td_bg01">
							<input name="accountNature" id="accountNature" size="10" class="nummax"/>
						</td>
						<td class="td_bg01">
							<input name="bankName" id="bankName" size="10" class="nummax"/>
						</td>
						<td class="td_bg01">
							<input name="bankAccount" id="bankAccount" size="10" class="nummax"/>
						</td>
						<td class="td_bg01">
							<select name="bankState" id="bankState" >
								<option value="0" >否</option>
								<option value="1">是</option>
							</select>
						</td>
						<td class="td_bg01">
							<input name="bankCyber" id="bankCyber" size="10" class="nummax"/>
						</td>
						<td class="td_bg01">
							<input name="openAccountDate" id="openAccountDate"  onfocus="date(this);" size="10"/>
							<input type="hidden" name="registrationKey" id="registrationKey" />
							<input type="hidden" name="registrationID" id="registrationID" />
							<input type="hidden" name="organizationPID" value="${institutionsRegistration.organizationPID}" id="organizationPID"/> 
						</td>
						<td class="td_bg01">
							<input name="cancellationDate" id="cancellationDate" onfocus="date(this);" size="10"></td>
						<td class="td_bg01"> 
							<input id="contact" name="contact" size="10" class="nummax"/>
						</td>
						<td class="td_bg01"> 
							<input id="conPerson" name="conPerson" size="10" readonly="readonly"/>
							<a href="#" onclick="javascript:aonclick()">选择</a>
						</td>
						<td class="td_bg01"> 
								<input id="papersType" name="papersType" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01"> 
								<input id="papersNumber" name="papersNumber" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01"> 
								<input id="birthday" name="birthday" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01"> 
								<input id="bankAddr" class="bankAddr" name="bankAddr" size="30" class="nummax"/>
						</td>

					</tr>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${registrationID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue trclass" value="${registrationID}" />
							</td>
							<td class="td_bg01">
								<span id="accountNum" >${accountNum}</span>
								<input class="model1" value="${accountNum}" name="accountNum"  />
							</td>
							<td class="td_bg01">
								<span id="bankRegistrationID" >${bankRegistrationID}</span>
								<input class="model1" value="${bankRegistrationID}" name="bankRegistrationID"  />
							</td>
							<td class="td_bg01" >							                                        
								<span id="registrationDate" class="datas">${fn:substring(registrationDate, 0, 10)}</span>
								<input class="model1" value="${fn:substring(registrationDate, 0, 10)}"
									name="registrationDate"  onfocus="date(this);" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="accountNature">${accountNature}</span>
								<input class="model1" value="${accountNature}"
									name="accountNature" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="bankName">${bankName}</span>
								<input class="model1" value="${bankName}"
									name="bankName" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="bankAccount">${bankAccount}</span>
								<input class="model1" value="${bankAccount}"
									name="bankAccount" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="bankState">
								<c:if test="${bankState == 0}">否</c:if>
								<c:if test="${bankState == 1}">是</c:if>
								</span>
								<select class="model1" name="bankState" id="bankState">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</td>
							<td class="td_bg01">
								<span id="bankCyber">${bankCyber}</span>
								<input class="model1" value="${bankCyber}"
									name="bankCyber" size="10"/>
							</td>
							<td class="td_bg01">                        
								<span id="openAccountDate" class="datas">${fn:substring(openAccountDate, 0, 10)}</span>
								<input class="model1" name="openAccountDate" value="${fn:substring(openAccountDate, 0, 10)}"  onfocus="date(this);" size="10"/>
								<input type="hidden" name="registrationKey" value="${registrationKey}" />
								<input type="hidden" name="registrationID" value="${registrationID}" />
								<input type="hidden" name="organizationID" value="${organizationID}" id="organizationID"/> 
								<input type="hidden" name="organizationPID" value="${organizationPID}" id="organizationPID"/> 
							</td>
							<td class="td_bg01">
								<span id="cancellationDate" class="datas">${fn:substring(cancellationDate, 0, 10)}</span>
								<input class="model1" value="${fn:substring(cancellationDate, 0, 10)}"
									name="cancellationDate" onfocus="date(this);" size="10"/>
							</td>
							
							<td class="td_bg01">
								<span id="contact" >${contact}</span>
								<input class="model1" value="${contact}" name="contact" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="conPerson" >${conPerson}</span>
								<input class="model1" value="${conPerson}" name="conPerson" size="10" readonly="readonly"/>
								<a href="#" class="model1" onclick="javascript:aonclick()">选择</a>
							</td>
							<td class="td_bg01">
								<span id="papersType" >${papersType}</span>
								<input class="model1" value="${papersType}" name="papersType" size="10" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<span id="papersNumber" >${papersNumber}</span>
								<input class="model1" value="${papersNumber}" name="papersNumber" size="10" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<span id="birthday" >${birthday}</span>
								<input class="model1" value="${birthday}" name="birthday" size="10" readonly="readonly"/>
							</td>
							<td class="td_bg01">
								<span id="bankAddr" >${bankAddr}</span>
								<input class="model1 bankAddr" value="${bankAddr}" name="bankAddr" size="30"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/institutionsregistration/ea_getListInstitutionsRegistration.jspa?institutionsRegistration.organizationPID=${institutionsRegistration.organizationPID}&search=${search}&pageNumber=${pageNumber}&ogName=${param.ogName}"></c:param>
			</c:import>
		</div>
	</form>
		<!--搜索窗口 -->
		<form name="appraisalForm" id="appraisalForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch2">

				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							发证日期： 
						</td>
					</tr>
					<tr>
						<td>
							起始日期：
						</td>
						<td>
							<input name="institutionsRegistration.registrationDate" class="put3" onfocus="date(this);" />至<input name="institutionsRegistration.openAccountDate" class="put3" onfocus="date(this);" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input type="hidden" name="institutionsRegistration.organizationPID"
						value="${institutionsRegistration.organizationPID}" />
				</div/>
			</div>
		</form> 
		 <iframe name="hidden" style="height:0;"  marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize" ></iframe>
		 
	</body>
</html>
