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
<title>身体状况</title>
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
<script type="text/javascript">
	var select = 1;
	var conditionrID = '';
	var pbasePath='<%=basePath%>';
	var pconditionstaffID='${condition.staffID}';
	var token=0;
	var notoken = 0;
	var mainheught = 0; //框架高度
    var ids = ''; //存放行ID
  
    function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",pbasePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe7").offsetHeight;
	  	parent.document.getElementById("mainframe7").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
    }
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
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
			parent.document.getElementById("mainframe7").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
<script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_pcondition.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form   enctype="multipart/form-data" name="conditionForm" method="post" id="conditionForm"><s:token></s:token>
<input type="submit" name="submit" style="display:none" />
<div id="main_main" class="main_main">
	<table class="pcondition">
	<thead>
		<tr>
		    <th width="30" align="center">选择</th>
            <th width="70" align="center" >体检时间</th>
            <th width="100" align="center" >体检医院</th>
			<th width="80" align="center" >体检内容</th>
			<th width="80" align="center" >体检指标</th>
			<th width="60" align="center" >是否正常</th>
			<th width="150" align="center" >医生意见</th>
			<th width="60" align="center" >医生姓名</th>
			<th width="80" align="center" >审核人</th>
			<th width="85" align="center" >审核人人员编号</th>
			<th width="70" align="center" >审核时间</th>
			<th width="80" align="center" >要求体检指标</th>
            <th width="150" align="center" >备注</th>
            <th width="150" align="center" >附件</th>
	      </tr>
	      </thead>
         <tbody id="tbwid">
				<tr align="center" height="22" style="display: none;" class="td_bg01 saveAjax model2" id="sa">
						<td > <input type="radio" name="a" class="JQuerypersonvalue" />
            			</td>
						<td class="td_bg01">
							<input  name="examinationTime" id="examinationTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="examinationHospital" id="examinationHospital" size="20"/>
						</td>
						
						<td class="td_bg01">
							<s:select list="codeExaminationContentList" id="xxx" listKey="codeID" listValue="codeValue" name="details"   theme="simple"></s:select>
						</td>
						
						<td class="td_bg01">
							<input name="indicator" id="indicator" size="15"/>
						</td>
						<td class="td_bg01">
							<input name="normal" id="normal" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="doctorAdvice" id="doctorAdvice" maxlength="25" size="18"/>
						</td>
						<td class="td_bg01">
							<input name="doctorName" id="doctorName" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="auditor" id="auditor" size="10" readonly="readonly"/>
							<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<input name="referenceCode" id="referenceCode" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
							<input   name="verifyTime" id="verifyTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input   name="orderedIndicator" id="orderedIndicator" size="20"/>
						</td>
						<td class="td_bg01">
							<input name="conditionDesc" id="conditionDesc" size="30"/>
							<input type="hidden" name="staffID" id="staffID" value="${condition.staffID}" />
						</td>	
						<td class="td_bg01">
							<input name="filephoto" id="filephoto" type="file" contentEditable="false" size="10"/>
						</td>					
					</tr>
				<!-- 隐 -->
				<s:iterator value="conditionList">
					<tr align="center" height="22" class="td_bg01 saveAjax trclass" id="${conditionrID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${conditionrID}"></td>
						<td class="td_bg01">
						<SPAN id="examinationTime" class="datas">${ examinationTime}</SPAN>
							<input class="model1" value="${examinationTime}"  name="examinationTime"   onfocus="date(this);" size="10" />
						</td>
						<td class="td_bg01">
						<SPAN id="examinationHospital">${examinationHospital}</SPAN>
							<input class="model1" name="examinationHospital" value="${examinationHospital}" size="20"/>
						</td>
						<td class="td_bg01">
							<SPAN id="details"></SPAN>
							<s:select list="codeExaminationContentList" class="model1"  listKey="codeID" listValue="codeValue" name="details" id="details"  theme="simple" disabled="true"></s:select><!--岗位情况 -->
						</td>
						<td class="td_bg01">
						<SPAN id="indicator">${indicator}</SPAN>
							<input class="model1" name="indicator" value="${indicator}" size="15"/>
						</td>
						<td class="td_bg01">
						<SPAN id="normal">${normal}</SPAN>
							<input class="model1" name="normal" value="${normal}" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="doctorAdvice">${doctorAdvice}</SPAN>
							<input class="model1" name="doctorAdvice" value="${doctorAdvice}" size="18"/>
						</td>
						<td class="td_bg01">
						<SPAN id="doctorName">${doctorName}</SPAN>
							<input class="model1" name="doctorName" value="${doctorName}" size="10"/>
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
							<input class="model1"  name="verifyTime" value="${verifyTime}"   onfocus="date(this);" size="10"/>
						</td>	
						<td class="td_bg01">
						<SPAN id="orderedIndicator">${orderedIndicator}</SPAN>
							<input class="model1" name="orderedIndicator" value="${orderedIndicator}" size="20"/>
						</td>
						<td class="td_bg01">
						<SPAN id="conditionDesc">${conditionDesc}</SPAN>
							<input class="model1" name="conditionDesc" value="${conditionDesc}"  size="30"/>
							<input name="conditionrKey" type="hidden" value="${conditionrKey}" />
							<input type="hidden" name="conditionrID" value="${conditionrID}" />
							<input type="hidden" name="staffID" value="${staffID }" />
						</td>	
						  <td class="td_bg01">
						   <span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="filephoto"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="filephoto"   type="file" class="model1" size="10" contentEditable="false"/>
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe7").offsetHeight-57+"px"});
 },100);
	 $(window).resize(function(){
		 setTimeout(function(){
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe7").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>
