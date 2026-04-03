<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>保险情况</title>
		<!-- 此页面在社会人力添加页面丢弃了，时间：2014-12-18。一年后没用到可以删除。 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
   var select = 1;
   var insuranceID = ''; 
   var pbasePath = '<%=basePath%>';
   var pstaffID = '${insurance.staffID}';
   var token = 0 ;
   var notoken = 0;
   var mainheught = 0; //框架高度
   var ids = ''; //存放行ID
 
   function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",pbasePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe11").offsetHeight;
	  	parent.document.getElementById("mainframe11").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
   }
	
  $(document).ready(function() {
 		$("#isBack").click(function(){// 返回
 	       $("#jqmWindow2").jqmHide();
 	       parent.document.getElementById("mainframe11").style.height = mainheught + 'px';
 	    }); 
 	   
 		$("#isSubmit").click(function(){// 选择确定
 			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
 			if(value1 == ""){
 				alert("请选择")
 				return;
 			}
 			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
 			var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
 			$("#"+ids).find("#auditor").val(value2);
 			$("#"+ids).find("#referenceCode").val(value3);
 			$("#ifr").attr("src","");
 			parent.document.getElementById("mainframe11").style.height = mainheught + 'px';
 	        $("#jqmWindow2").jqmHide();
 	    });
  });
</script>

 <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
 <script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_insurance.js"></script>
 <link href="<%=basePath%>/css/ea/personal.css" rel="stylesheet" type="text/css"/>
</head>
	<body>
	<form  name="insuranceForm" method="post" id="insuranceForm"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
		<div id="main_main" class="main_main">
			<table class="insurance">
			<thead>
				<tr>
				   <th width="30" align="center">选择</th>
					<th width="70" align="center" >购买时间</th>
					<th width="85" align="center" >购买保险名称</th>
					<th width="100" align="center" >购买保险单位</th>
					<th width="70" align="center" >有效时间</th>
					<th width="60" align="center" >保险金额</th>
					<th width="80" align="center" >保险电话</th>
					<th width="80" align="center" >审核人</th>
					<th width="85" align="center" >审核人人员编号</th>
					<th width="70" align="center" >审核时间</th>
					<th width="150" align="center" >备注</th>
					<th width="150" align="center" >附件</th>
				</tr>
				</thead>
				<tbody id="tbwid">
				<tr align="center" height="22" style="display: none;" class="td_bg01 saveAjax model2" id="sa">
				<td > <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
            			</td>
						<td class="td_bg01">
							<input  name="startTime"  id="startTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<s:select list="codeInsuranceList" listKey="codeValue" listValue="codeValue" name="insuranceName" id="xxx"  theme="simple"></s:select>
						</td>
						<td class="td_bg01">
							<input name="insuranceUnit" id="insuranceUnit" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="effectiveTime" id="effectiveTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="amount" id="amount" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="phone" id="phone" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="auditor"  id="auditor" size="10" readonly="readonly"/>
							<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<input name="referenceCode" id="referenceCode" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
							<input name="verifyTime"  id="verifyTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="insuranceDesc" id="insuranceDesc" size="30"/>
							<input type="hidden" name="insuranceKey" id="insuranceKey"/>
							<input type="hidden" name="insuranceID" id="insuranceID" />
							<input type="hidden" name="staffID" id="staffID" value="${insurance.staffID}"/>
						</td>
						<td class="td_bg01">
							<input name="photos" id="photos" type="file" contentEditable="false" size="10"/>
						</td>
					</tr>
				<!-- 隐 -->
				<s:iterator value="insuranceList">
					<tr  class="td_bg01 saveAjax trclass" id="${insuranceID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${insuranceID}"></td>
						<td class="td_bg01">
						    <SPAN id="startTime" class="datas">${startTime}</SPAN>
							<input class="model1" value="${startTime}" name="startTime"  onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
						   <SPAN id="insuranceName"></SPAN>
							<s:select list="codeInsuranceList" listKey="codeValue" listValue="codeValue" name="insuranceName" id="insuranceName"  theme="simple" disabled="true"></s:select>
						</td>
						<td class="td_bg01">
						 <SPAN id="insuranceUnit">${insuranceUnit}</SPAN>
							<input class="model1" name="insuranceUnit" value="${insuranceUnit}" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="effectiveTime" class="datas">${effectiveTime}</SPAN>
							<input  class="model1" name="effectiveTime" value="${effectiveTime}"  onfocus="date(this);" size="10" />
						</td>
						<td class="td_bg01">
						 <SPAN id="amount">${amount}</SPAN>
							<input class="model1" name="amount" value="${amount}" size="10"/>
						</td>
						<td class="td_bg01">
						 <SPAN id="phone">${phone}</SPAN>
							<input class="model1" name="phone" value="${phone}" size="10"/>
						</td>
						<td class="td_bg01">
							<SPAN id="auditor">${auditor}</SPAN>
							<input class="model1" id="auditor" name="auditor" value="${auditor}" size="10" readonly="readonly"/>
							<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<SPAN id="referenceCode">${referenceCode}</SPAN>
							<input class="model1" id="referenceCode" name="referenceCode" value="${referenceCode}" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
						    <SPAN id="verifyTime" class="datas">${verifyTime}</SPAN>
							<input class="model1" name="verifyTime" id="verifyTime" value="${verifyTime}" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="insuranceDesc">${insuranceDesc}</SPAN>
							<input class="model1" name="insuranceDesc" value="${insuranceDesc}" size="30"/>
							<input type="hidden" name="insuranceKey" value="${insuranceKey}"/>
							<input type="hidden" name="insuranceID"  value="${insuranceID}" />
							<input type="hidden" name="staffID"  value="${staffID}" />
						</td>
						<td class="td_bg01">
						  <span> <s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="photos"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="photos"   type="file" class="model1" size="10" contentEditable="false"/>
						    <input name="photo" type="hidden" value="${photo}" class="model1"/>
						</td>
					</tr>
				</s:iterator>
			</table>
		</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
		<!-- 从当前部门的员工中选择责任人 -->
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div align="center">
				<iframe name="ifr" id="ifr" width="100%" height="280px"
				frameborder="0"></iframe>
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		</div>
		
		<script type="text/javascript">
			$(function(){   
				setTimeout(function(){ 	
			        $("div.bDiv").css({"height":parent.document.getElementById("mainframe11").offsetHeight-57+"px"});
			 },100);
				 $(window).resize(function(){ 	
					 setTimeout(function(){ 	
					        $("div.bDiv").css({"height":parent.document.getElementById("mainframe11").offsetHeight-57+"px"});
					 },100);
					 }); 	
			});
		</script>
	</body>
</html>