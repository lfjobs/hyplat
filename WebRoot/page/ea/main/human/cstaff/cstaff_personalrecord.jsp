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
<title>人事档案管理</title>
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

<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<SCRIPT type="text/javascript">
    var select = 1;
	var recordID='';
	var basePath='<%=basePath%>';
	var pbasePath='<%=basePath%>';
	var ppersonalRecordstaffID='${personalRecord.staffID}';
	var notoken = 0;
	var mainheught = 0; //框架高度
    var ids = ''; //存放行ID
    var isvals = 0; //赋值判断
   
	function getValueForParm(id,isval){ //打开页面
		ids = id;
		isvals = isval;
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe15").offsetHeight;
	  	parent.document.getElementById("mainframe15").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
	}
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe15").style.height = mainheught + 'px';
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			if(isvals == 1){
				$("#"+ids).find("#dutyOfficer").val(value2);
			}else if(isvals == 2){
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
				$("#"+ids).find("#assessor").val(value2);
				$("#"+ids).find("#assessorCode").val(value3);
			}
			$("#ifr").attr("src","");
			parent.document.getElementById("mainframe15").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_personalrecord.js"></script>
</head>

<body>
<form enctype="multipart/form-data" name="personrecordForm" id="personrecordForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table class="personalrecord">
  <thead>
	 	  <tr>
	 	    <th width="30" align="center">选择</th>
            <th width="50" align="center" >档案编号</th>
            <th width="70" align="center" >档案名称</th>
            <th width="70" align="center" >管理起时间</th>
            <th width="70" align="center" >管理止时间</th>
            <th width="80" align="center" >档案责任人</th>
            <th width="60" align="center" >档案盒编号</th>
            <th width="70" align="center" >档案盒名称</th>
            <th width="80" align="center" >审核人</th>
            <th width="85" align="center" >审核人人员编号</th>
            <th width="70" align="center" >审核时间</th>
             <th width="150" align="center" >附件</th>
          </tr>
          </thead>
		  <tbody id="tbwid">
		   <input type="hidden" id="start"/>
		   <input type="hidden" id="end"/>
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
           <td > <input type="radio" name="a" class="JQuerypersonvalue" />
            			</td>
            <td class="td_bg01"><input  name="recordCode" id="recordCode" class="err" size="10"/></td>
            <td class="td_bg01"><input  name="recordName" id="recordName" class="err" size="10"/></td>
            <td class="td_bg01"><input  name="controlStartDate" id="controlStartDate" 
            	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="10"/></td>
            <td class="td_bg01"><input  name="controlEndDate" id="controlEndDate" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})" size="10"></td>
            <td class="td_bg01"><input  name="dutyOfficer" id="dutyOfficer" size="10" readonly="readonly"/>
            					<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
					            <input type="hidden" name="recordKey" id="recordKey"/>
					            <input type="hidden" name="recordID" id="recordID" />
					            <input type="hidden" name="staffID" value="${personalRecord.staffID}" id="staffID" />
			</td>
			 <td class="td_bg01"><input  name="recordBoxCode" id="recordBoxCode" size="10"></td>
			  <td class="td_bg01"><input  name="recordBoxName" id="recordBoxName" size="10"></td>
			    <td class="td_bg01">
			    	<input name="assessor" id="assessor" size="10" readonly="readonly"/>
			    	<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
			    </td>
			     <td class="td_bg01"><input  name="assessorCode" id="assessorCode" size="10" readonly="readonly"/></td>
			  <td class="td_bg01"><input  name="assessorDate" id="assessorDate" onfocus="date(this);" size="10"></td>
            <td class="td_bg01">
							<input name="filePhoto" id="filePhoto" type="file" contentEditable="false" size="10"/>
						</td>	
          </tr>
         
          <s:iterator value="personrecordList">
          <tr class="td_bg01 saveAjax trclass" id="${recordID}">
          <td><input type="radio" name="a" class="JQuerypersonvalue" value="${recordID}"></td>
            <td class="td_bg01">
            <SPAN id="recordCode">${recordCode}</SPAN>
				<input class="model1 err" value="${recordCode}" name="recordCode" size="10"/></td>
            <td class="td_bg01">
             <SPAN id="recordName">${recordName}</SPAN>
				<input class="model1 err" value="${recordName}" name="recordName" size="10"/></td>
            <td class="td_bg01"> <SPAN id="controlStartDate" class="datas">${controlStartDate}</SPAN>
            <input class="model1"  name="controlStartDate" id="controlStartDate" value="${controlStartDate}" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="10"></td>
            <td class="td_bg01"> <SPAN id="controlEndDate" class="datas">${controlEndDate}</SPAN>
            <input class="model1"  name="controlEndDate" id="controlEndDate" value="${controlEndDate}" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})" size="10"></td>
            <td class="td_bg01"> 
            	<SPAN id="dutyOfficer">${dutyOfficer}</SPAN>
            	<input class="model1" id="dutyOfficer" name="dutyOfficer" value="${dutyOfficer}" size="10" readonly="readonly"/>
            	<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
                                 <input type="hidden" name="recordKey" value="${recordKey}" id="recordKey"/>
					            <input type="hidden" name="recordID" id="recordID" value="${recordID}" id="recordID" />
					            <input type="hidden" name="staffID" id="staffID" value="${staffID}" id="staffID" />
			</td> 
			<td class="td_bg01"> <SPAN id="recordBoxCode">${recordBoxCode}</SPAN>
			<input class="model1" name="recordBoxCode" value="${recordBoxCode}" id="recordBoxCode" size="10"></td>
			  <td class="td_bg01"> <SPAN id="recordBoxName">${recordBoxName}</SPAN>
			  <input class="model1" name="recordBoxName" value="${recordBoxName}" id="recordBoxName" size="10"></td>
			    <td class="td_bg01">
				     <SPAN id="assessor">${assessor}</SPAN>
				     <input class="model1" name="assessor" value="${assessor}" id="assessor" size="10" readonly="readonly"/>
				     <a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
			     </td>
			     <td class="td_bg01">
			      <SPAN id="assessorCode">${assessorCode}</SPAN>
			      <input class="model1" name="assessorCode" value="${assessorCode}" id="assessorCode" size="10" readonly="readonly"/></td>
			  <td class="td_bg01"> <SPAN id="assessorDate" class="datas">${assessorDate}</SPAN>
			  <input class="model1" name="assessorDate" value="${assessorDate}" id="assessorDate" onfocus="date(this);" size="10"></td>
             <td class="td_bg01">
						 <span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="filePhoto"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="filePhoto"   type="file" class="model1" size="10" contentEditable="false"/>
						    <input name="photo" type="hidden" value="${photo}" class="model1"/>
						</td> 
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
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe15").offsetHeight-57+"px"});
	 },100);
	 $(window).resize(function(){
		 setTimeout(function(){	 
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe15").offsetHeight-57+"px"});
	 },100);
	 }); 	
});
</script>
</body>
</html>