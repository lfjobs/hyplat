<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
		
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>处分情况</title>
		<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<SCRIPT type="text/javascript">
     var select = 1;
	 var punishmentID='';
	 var pbasePath='<%=basePath%>';
	 var punishmentstaffID='${punishment.staffID}';
	 var token=0;
	 var notoken = 0;
	 var mainheught = 0; //框架高度
     var ids = ''; //存放行ID
   
     function getValueForParm(id){ //打开页面
 		ids = id;
 	  	$("#ifr").attr("src",pbasePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
 	  	mainheught = parent.document.getElementById("mainframe10").offsetHeight;
 	  	parent.document.getElementById("mainframe10").style.height = 330 + 'px';
 	  	$("#jqmWindow2").jqmShow();
     }
 	
    $(document).ready(function() {
   		$("#isBack").click(function(){// 返回
   	       $("#jqmWindow2").jqmHide();
   	       parent.document.getElementById("mainframe10").style.height = mainheught + 'px';
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
   			$("#"+ids).find("#auditorNumber").val(value3);
   			$("#ifr").attr("src","");
   			parent.document.getElementById("mainframe10").style.height = mainheught + 'px';
   	        $("#jqmWindow2").jqmHide();
   	    });
    });
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_punishment.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
	<body>
	<form  name="punishmentForm" id="punishmentForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
		<div id="main_main" class="main_main">
		  <table class="punishment">
		  <thead>
			<tr>
			   <th width="30" align="center">选择</th>
				<th width="100" height="26" align="center" >处分类别</th>
				<th width="60" align="center" >处分名称</th>
				<th width="150" align="center" >处分原因</th>
				<th width="80" align="center" >处分批准日期</th>
				<th width="100" align="center" >处分批准机关名称</th>
				<th width="80" align="center" >处分撤销日期</th>
				<th width="80" align="center" >审核人</th>
				<th width="85" align="center" >审核人人员编号</th>
				<th width="70" align="center" >审核时间</th>
				<th width="150" align="center" >备注</th>
				<th width="150" align="center" >附件</th>
			</tr>
			</thead>
				<tbody id="tbwid">
				<tr id="sa" style="display:none; " class="td_bg01 saveAjax model2">
				<td ><input type="radio" name="a" class="JQuerypersonvalue" value="${staffID }"/></td>
				<td class="td_bg01"><s:select list="punishmentTypelist" listKey="codeID" listValue="codeValue" name="punishmentType" id="xxx" theme="simple"></s:select></td>
				<td class="td_bg01"><input  name="punishmentName" id="punishmentName" size="10"/></td>
				<td class="td_bg01"><input  name="punishmentReason" id="punishmentReason" maxlength="25" size="10"/></td>
				<td class="td_bg01"><input  name="punishmentDate"  id="punishmentDate" onfocus="date(this);" size="10"/></td>
				<td class="td_bg01"><input  name="punishmentOrgan"  id="punishmentOrgan" size="10"/></td>
				<td class="td_bg01"><input  name="dischargeDate"  id="dischargeDate" onfocus="date(this);" size="10"/></td>
				<td class="td_bg01">
					<input name="auditor" id="auditor" size="10" readonly="readonly"/>
					<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
				</td>
				<td class="td_bg01"><input   name="auditorNumber"  id="auditorNumber" size="10" readonly="readonly"/></td>
				<td class="td_bg01"><input  name="auditorTime"  id="auditorTime" onfocus="date(this);" size="10"/></td>
				<td class="td_bg01">
				<input  name="punishmentDesc" id="punishmentDesc" maxlength="25" size="30"/>
				<input type="hidden"  name="punishmentkey"  id="punishmentkey"/>
				<input type="hidden"  name="punishmentID" id="punishmentID"/>
				<input type="hidden"  name="staffID"	value="${punishment.staffID}" id="staffID"/>
				</td>
				<td class="td_bg01"><input name="filePhoto" id="filePhoto" type="file" contentEditable="false" size="10"/></td>
				</tr>
				<s:iterator value="punishmentList">
					<tr  class="td_bg01 saveAjax trclass" id="${punishmentID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${punishmentID}"></td>
						<td class="td_bg01">
						<s:select list="punishmentTypelist" listKey="codeID" listValue="codeValue" disabled="true" name="punishmentType" id="punishmentType" theme="simple"></s:select>
						</td>
						<td class="td_bg01">
						<SPAN id="punishmentName">${punishmentName} </SPAN>
							<input class="model1"  name="punishmentName" value="${punishmentName}" size="10" />
						</td>
						<td class="td_bg01">
						<SPAN id="punishmentReason">${punishmentReason} </SPAN>
							<input class="model1"  name="punishmentReason" value="${punishmentReason}" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="punishmentDate" class="datas">${punishmentDate} </SPAN>
							<input class="model1"  name="punishmentDate" value="${punishmentDate}"  onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="punishmentOrgan">${punishmentOrgan} </SPAN>
							<input class="model1" name="punishmentOrgan" 	value="${punishmentOrgan}" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="dischargeDate" class="datas">${dischargeDate} </SPAN>
							<input class="model1" name="dischargeDate" value="${dischargeDate}"  onfocus="date(this);"  size="10" />
						</td>
						<td class="td_bg01">
							<SPAN id="auditor">${auditor} </SPAN>
							<input class="model1" id="auditor" name="auditor"  value="${auditor}" size="10" readonly="readonly"/>
							<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
						<SPAN id="auditorNumber">${auditorNumber} </SPAN>
							<input class="model1" id="auditorNumber" name="auditorNumber" value="${auditorNumber}" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
						<SPAN id="auditorTime" class="datas">${auditorTime} </SPAN>
							<input class="model1" name="auditorTime" value="${auditorTime}"  onfocus="date(this);"  size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="punishmentDesc">${punishmentDesc} </SPAN>
							<input class="model1"  name="punishmentDesc"  value="${punishmentDesc}"  size="30"/>
							<input type="hidden"  name="punishmentkey" value="${punishmentkey}"/>
							<input type="hidden"  name="punishmentID" value="${punishmentID}"/>
							<input type="hidden"  name="staffID" value="${staffID}"/>
						</td>
						 <td class="td_bg01">
						    <span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="filePhoto"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="filePhoto"   type="file" class="model1" size="10"  contentEditable="false" />
						    <input name="photo" type="hidden" value="${photo}" class="model1"/>
						</td>
					</tr>
				</s:iterator>
				</tbody>
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
			        $("div.bDiv").css({"height":parent.document.getElementById("mainframe10").offsetHeight-57+"px"});
			 },100);
				 $(window).resize(function(){
					 setTimeout(function(){
					        $("div.bDiv").css({"height":parent.document.getElementById("mainframe10").offsetHeight-57+"px"});
					 },100);
					 }); 	
			});
		</script>
	</body>
</html>