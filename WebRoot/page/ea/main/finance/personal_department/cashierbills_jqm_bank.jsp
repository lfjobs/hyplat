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
		<title>银行账户列表</title>
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
	
	<script type="text/javascript">
			var select =1;
			var opertionID = '';
			var basePath = '<%=basePath%>';
			var organizationPID='${institutionsRegistration.organizationPID}';
			var ppageNumber=${pageNumber};
          	var search='${search}';
			var token=0;
			var notoken=0;
			var dept = '${departmentID}';
           
           $(document).ready(function() {
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close')// 添加触发关闭的selector  
//                .jqDrag('.drag');// 添加拖拽的selector
	 $(".JQueryaddress").jqm({
                    modal: true,// 
                    overlay: 20 // 
                }).jqmAddClose('.close')//
//                .jqDrag('.drag');// 
	$('.registration').flexigrid({
        height: 230,
        width: 'auto',
        minwidth: 30,
        title: '银行账户列表',
        minheight: 80,
        buttons: [{
                  name: '查询',
                  bclass: 'mysearch',
                  onpress: action//当点击调用方法
              }, {
                  separator: true
              }, {
				  name: '设置每页显示条数',
				  bclass: 'mysearch',
				  onpress : action//当点击调用方法
			  },{
				  separator: true
		}]
    	
	});
		
	function action(com, grid){
		switch (com) {
			case '查询':
					$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数':
			var url=basePath + "ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa?search="+search+"&departmentID="+dept;
				numback(url);
				break; 
		}
	};
	//查询相关操作
     $("#searchBank","#bankSearchForm").click(function(){
         $("#bankSearchForm").attr("action", basePath + "ea/institutionsregistration/ea_toSearchBankNum.jspa?pageNumber="+ppageNumber+"&departmentID="+dept);
         document.bankSearchForm.submit.click();
     });
     //单击一行事件
     $(".registration tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    opertionID =this.id;
                })
})
           
    </script>
    </head>
	<body>
	
		<form  name="staffappraisalForm" enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="hidden" name="institutionsRegistration.organizationPID" value="${institutionsRegistration.organizationPID}"></input>
			<input type="submit" name="submit" style="display: none" />
		<div id="main_main" class="main_main">
			<table class="registration" id="bankSearchTable">
				<thead>
					<tr>
						<th width="40" align="center">
							选择
						</th>
						<th width="160" align="center">
							账号编号
						</th>
						<th width="180" align="center">
							银行开户登记证明号(审核号)
						</th>
						<th width="100" align="center">
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
						<th width="100" align="center">
							开户时间
						</th>
						<th width="100" align="center">
							注销时间
						</th> 
						<th width="100" align="center">
							联系方式
						</th> 
						<th width="100" align="center">
							账户责任人
						</th> 
						<th width="180" align="center">
							开户银行地址
						</th>  
					</tr>
				</thead>
                <tbody id="tbwid">
					<tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" value="${registrationID}" />
						</td>
						<td class="td_bg01">
							<input name="accountNum" id="accountNum" /></td>
						<td class="td_bg01">
							<input name="bankRegistrationID" id="bankRegistrationID"/></td>
						<td class="td_bg01">
							<input name="registrationDate" id="registrationDate" onfocus="date(this); " size="10"/>
						</td>
						<td class="td_bg01">
							<input name="accountNature" id="accountNature" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="bankName" id="bankName" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="bankAccount" id="bankAccount" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="openAccountDate" id="openAccountDate"  onfocus="date(this);" size="10"/>
							<input type="hidden" name="registrationKey" id="registrationKey" />
							<input type="hidden" name="opertionID" id="opertionID" />
							<input type="hidden" name="organizationPID" value="${institutionsRegistration.organizationPID}" id="organizationPID"/> 
						</td>
						<td class="td_bg01">
							<input name="cancellationDate" id="cancellationDate" onfocus="date(this);" size="10"></td>
						<td class="td_bg01"> 
								<input id="contact" name="contact" size="10"/>
						</td>
						<td class="td_bg01"> 
								<input id="conPerson" name="conPerson" size="10"/>
						</td>
						<td class="td_bg01"> 
								<input id="bankAddr" class="bankAddr" name="bankAddr" size="30"/>
						</td>

					</tr>
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${registrationID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"  value="${registrationID}" />
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
								
								<span id="childbankName" style="display:none">${bankName}---${bankAccount}</span>
								<span id="bankAccount">${bankAccount}</span>
							</td>
							<td class="td_bg01">                        
								<span id="openAccountDate" class="datas">${fn:substring(openAccountDate, 0, 10)}</span>
								<input class="model1" name="openAccountDate" value="${fn:substring(openAccountDate, 0, 10)}"  onfocus="date(this);" size="10"/>
								<input type="hidden" name="registrationKey" value="${registrationKey}" />
								<input type="hidden" name="opertionID" value="${registrationID}" />
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
								<input class="model1" value="${conPerson}" name="conPerson" size="10"/>
							</td>
							<td class="td_bg01">
								<span id="bankAddr" >${bankAddr}</span>
								<input class="model1 bankAddr" value="${bankAddr}" name="bankAddr" size="30"/>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa?search=${search }&pageNumber=${pageNumber}&departmentID=${departmentID}"></c:param>
			</c:import>
		</div>
	</form>
		
	<!--搜索窗口 -->
		<form name="bankSearchForm" id="bankSearchForm" method="post">
		<input type="submit" name="submit" style="display: none"/>
			<div class="jqmWindow" style="width: 400px;right: 25%;top: 10%" id="jqModelSearch">
	                <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	                </div>
	                <table id="bankSearchTable">
	                    <tr>
	                        <td  width="123" align="center">
	                            银行名称：
	                        </td>
	                        <td >
	                            <input name="institutionsRegistration.bankName" />
	                        </td>
	                    </tr>
	                    <tr>
	                        <td width="123" align="center">
	                            账户责任人：
	                        </td>
	                        <td >
	                            <input name="institutionsRegistration.conPerson" />
	                        </td>
	                    </tr>
	                </table>
	                <div align="center">
	                    <input type="button" class="input-button" id="searchBank" value=" 查询 " /><input name="search" type="hidden" value="search" />
	                </div>
	        </div>
        </form>
	</body>
</html>