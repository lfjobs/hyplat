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
		<title>政治面貌</title>
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
    var politicalID = '';
	var pbasePath='<%=basePath%>';
	var politicalstaffID='${political.staffID}';
	var token=0;
	var notoken = 0;
	var mainheught = 0; //框架高度
    var ids = ''; //存放行ID
  
    function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",pbasePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe8").offsetHeight;
	  	parent.document.getElementById("mainframe8").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
    }
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe8").style.height = mainheught + 'px';
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
			parent.document.getElementById("mainframe8").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_political.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
	<body>
	<form name="politicalForm" id="politicalForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
		<div id="main_mian" class="main_main">
			<table class="political">
			  <thead>
				<tr>
					<th width="30" align="center">选择</th>
					<th width="75" align="center" >政治面貌</th>
					<th width="80" align="center" >参加组织日期</th>
					<th width="150"align="center" >参加党派时所在单位</th>
					<th width="60" align="center" >介绍人</th>
					<th width="70" align="center" >转正时间</th>
					<th width="60" align="center" >党（团）龄</th>
					<th width="80" align="center" >审核人</th>
					<th width="85" align="center" >审核人人员编号</th>
					<th width="70" align="center" >审核时间</th>
					<th width="150" align="center" >备注</th>
					<th width="150" align="center" >附件</th>
				</tr>
			 </thead>
				<tbody id="tbwid">
				<tr id="sa"  style="display: none;" class="td_bg01 saveAjax model2">
				 <td class="td_bg01"> <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID }"/></td>
					<td class="td_bg01"><s:select list="politicalTpyelist" listKey="codeID" listValue="codeValue" name="politicalStatus" id="xxx" theme="simple"></s:select></td>
					<td class="td_bg01"><input name="joinDate"  id="joinDate"  onfocus="date(this);" size="10"/></td>
					<td class="td_bg01"><input name="unit" id="unit" size="20"/></td>
					<td class="td_bg01"><input name="introducer" id="introducer" size="10"/></td>
					<td class="td_bg01"><input name="probatePassDate"  id="probatePassDate"  onfocus="date(this);" size="10" /></td>
					<td class="td_bg01"><input name="partyStand"  id="partyStand" size="10"/></td>
					<td class="td_bg01">
						<input name="auditor"  id="auditor" size="10" readonly="readonly"/>
						<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
					</td>
					<td class="td_bg01"><input name="auditorNumber"  id="auditorNumber" size="10" readonly="readonly"/></td>
					<td class="td_bg01"><input name="auditorTime"  id="auditorTime"  onfocus="date(this);"  size="10"/></td>
					<td class="td_bg01">
						<input name="politicalDesc" id="politicalDesc" size="30"/>
						<input type="hidden" name="politicalkey" id="politicalkey"/>
						<input type="hidden" name="politicalID"  id="politicalID"/>
						<input type="hidden" name="staffID" value="${political.staffID}" id="staffID"/>
					</td>
					<td class="td_bg01"><input name="filephoto" id="filephoto" type="file" contentEditable="false" size="10"/></td>	
				</tr>
				<s:iterator value="politicalList">
					<tr align="center" height="22" class="td_bg01 saveAjax trclass" id="${politicalID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${politicalID}"></td>
						<td class="td_bg01">
						<span id="politicalStatus"></span>
						<s:select list="politicalTpyelist"   class="model1" listKey="codeID" listValue="codeValue" disabled="true" name="politicalStatus" id="politicalStatus" theme="simple"></s:select>
						</td>
						<td class="td_bg01">
						<span id="joinDate" class="datas">${joinDate}</span>
							<input class="model1" name="joinDate" value="${joinDate}"  onfocus="date(this);"   size="10"/>
						</td>
						<td class="td_bg01">
						<span id="unit">${unit}</span>
							<input class="model1" name="unit" value="${unit}" size="20"/>
						</td>
						<td class="td_bg01">
						<span id="introducer">${introducer}</span>
							<input class="model1" name="introducer" value="${introducer}" size="10"/>
						</td>
						<td class="td_bg01">
						<span id="probatePassDate" class="datas">${probatePassDate}</span>
							<input class="model1" name="probatePassDate" value="${probatePassDate}"  onfocus="date(this);"  size="10" />
						</td>
						<td class="td_bg01">
						<span id="partyStand">${partyStand}</span>
							<input class="model1" name="partyStand" value="${partyStand}" size="10"/>
						</td>
						<td class="td_bg01">
							<span id="auditor">${auditor}</span>
							<input class="model1" id="auditor" name="auditor" value="${auditor}" size="10" readonly="readonly"/>
							<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<span id="auditorNumber">${auditorNumber}</span>
							<input class="model1" id="auditorNumber" name="auditorNumber" value="${auditorNumber}" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
						<span id="auditorTime" class="datas">${auditorTime}</span>
							<input class="model1" name="auditorTime" value="${auditorTime}"  onfocus="date(this);" size="10" />
						</td>
						<td class="td_bg01">
						<span id="politicalDesc">${politicalDesc}</span>
							<input class="model1" name="politicalDesc" value="${politicalDesc}" size="30"/>
							<input type="hidden" name="politicalkey" value="${politicalkey}"/>
							<input type="hidden" name="politicalID" value="${politicalID}"/>
							<input type="hidden" name="staffID" value="${staffID}"/>
						</td>
						<td class="td_bg01">
						  <span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="photo"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="filephoto"   type="file" class="model1" size="10" contentEditable="false" />
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
			        $("div.bDiv").css({"height":parent.document.getElementById("mainframe8").offsetHeight-57+"px"});
			 },100);
				 $(window).resize(function(){ 	
					 setTimeout(function(){
					        $("div.bDiv").css({"height":parent.document.getElementById("mainframe8").offsetHeight-57+"px"});
					 },100);
					 }); 	
			});
		</script>
	</body>
</html>
