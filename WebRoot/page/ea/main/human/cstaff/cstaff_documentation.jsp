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
		<title>资料列表</title>
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
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_documentation.js"></script>
<SCRIPT type="text/javascript">
   var select = 1;
   var documentationID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${documentation.staffID}'; 
   var notoken = 0;
   var mainheught = 0; //框架高度
   var ids = ''; //存放行ID
 
   function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe14").offsetHeight;
	  	parent.document.getElementById("mainframe14").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
   }
	
  $(document).ready(function() {
 		$("#isBack").click(function(){// 返回
 	       $("#jqmWindow2").jqmHide();
 	       parent.document.getElementById("mainframe14").style.height = mainheught + 'px';
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
 			parent.document.getElementById("mainframe14").style.height = mainheught + 'px';
 	        $("#jqmWindow2").jqmHide();
 	    });
  });
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>

		<div id="main_main" class="main_main">
		<form   enctype="multipart/form-data" name="documentationForm" id="documentationForm" method="post">
               <s:token></s:token><input type="submit" name="submit" style="display:none"/>
		
			<table class="documentation">
				<thead>
				<tr>
				   <th width="30" align="center">选择</th>
					<th width="50" align="center">
						资料编号
					</th>
					<th  width="70" align="center">
						资料名称
					</th>
					<th  width="80" align="center">
						资料管理起时间
					</th>
					<th  width="80" align="center">
						资料管理止时间
					</th>
					<th  width="60" align="center">
						资料盒编号
					</th>
					<th  width="70" align="center">
						资料盒名称
					</th>
					<th  width="80" align="center">
						审核人
					</th>
					<th width="70" align="center">
						审核人编号
					</th>
					<th width="70" align="center">
						审核时间
					</th>
					<th  width="200" align="center">
						备注说明
					</th>
					<th  width="150" align="center">
						附件
					</th>
				</tr>
				</thead>
				<tbody id="tbwid">
				<input type="hidden" id="start"/>
				<input type="hidden" id="end"/>
				<tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
				<td > <input type="radio" name="a" class="JQuerypersonvalue" />
            			</td>
						<td class="td_bg01" >
							<input name="documentationNumber" id="documentationNumber" class="err" size="11"/>
						</td>
						<td class="td_bg01">
							<input  name="documentationName" id="documentationName" class="err" size="12"/>
						</td>
						<td class="td_bg01">
							<input  name="documentationManagerStart" id="documentationManagerStart" 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="11" />
						</td>
							<td class="td_bg01">
							<input name="documentationManagerEnd" id="documentationManagerEnd" 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})" size="11"/>
						</td>
						<td class="td_bg01">
							<input name="documentationBoxNumber" id="documentationBoxNumber" size="11"/>
						</td>
						<td class="td_bg01">
							<input name="documentationBoxName" id="documentationBoxName" size="11"/>
						</td>
						<td class="td_bg01">
							<input name="auditor" id="auditor" size="11" readonly="readonly"/>
							<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<input name="auditorNumber" id="auditorNumber" size="11" readonly="readonly"/>
						</td>
						<td class="td_bg01">
							<input  name="auditorTime" id="auditorTime" onfocus="date(this);" size="11"/>
						</td>
						<td class="td_bg01">
							<input name="ddesc" id="ddesc" size="30"/>
							<input type="hidden"  name="documentationkey" id="documentationkey"/>
							<input type="hidden"  name="staffID" value="${documentation.staffID}"}" id="staffID"/>
							<input type="hidden"  name="documentationID" id="documentationID"/>
						</td>
						<td class="td_bg01">
							<input name="photos" id="photos" size="10" type="file" contentEditable="false"/>
						</td>
					</tr>
				<s:iterator value="documentationList"> 
					<tr align="center" height="22" class="td_bg01 saveAjax trclass" id="${documentationID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${documentationID}" ></td>
						<td class="td_bg01" >
						<SPAN id="documentationNumber">${documentationNumber}</SPAN>
							<input class="model1 err" name="documentationNumber" value="${documentationNumber}"  size="11"/>
						</td>
						<td class="td_bg01">
						<SPAN id="documentationName">${documentationName}</SPAN>
							<input class="model1 err" name="documentationName" value="${documentationName}" size="12"/>
						</td>
						<td class="td_bg01">
						<SPAN id="documentationManagerStart" class="datas">${documentationManagerStart}</SPAN>
							<input class=" model1" name="documentationManagerStart" id="documentationManagerStart" value="${documentationManagerStart}" 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="11" />
						</td>
						<td class="td_bg01">
						<SPAN id="documentationManagerEnd" class="datas">${documentationManagerEnd}</SPAN>
							<input class="model1" name="documentationManagerEnd" id="documentationManagerEnd" value="${documentationManagerEnd}" 
								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})" size="11"/>
						</td>
						<td class="td_bg01">
						<SPAN id="documentationBoxNumber">${documentationBoxNumber}</SPAN>
							<input class=" model1" name="documentationBoxNumber" value="${documentationBoxNumber}"  size="11"/>
						</td>
						<td class="td_bg01">
						<SPAN id="documentationBoxName">${documentationBoxName}</SPAN>
							<input class="model1" name="documentationBoxName" value="${documentationBoxName}" size="11"/>
						</td>
						<td class="td_bg01">
						 	<SPAN id="auditor">${auditor}</SPAN>
							<input class="model1" id="auditor" name="auditor" value="${auditor}" size="11" readonly="readonly"/>
							<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
						 	<SPAN id="auditorNumber">${auditorNumber}</SPAN>
							<input class="model1" id="auditorNumber" name="auditorNumber" value="${auditorNumber}" size="11" readonly="readonly"/>
						</td>
						<td class="td_bg01">
						 <SPAN id="auditorTime" class="datas">${auditorTime}</SPAN>
							<input class="model1" name="auditorTime" value="${auditorTime}"  onfocus="date(this);"  size="11"/>
						</td>
						<td class="td_bg01">
						 <SPAN id="ddesc">${ddesc}</SPAN>
							<input class="model1" name="ddesc" value="${ddesc}" size="30" />
							<input type="hidden"  name="documentationkey"value="${documentationkey}"/>
						    <input type="hidden"  name="documentationID" value="${documentationID}"/>
						     <input type="hidden" name="staffID" value="${staffID}"/>
						</td>
						<td class="td_bg01">
						<span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="photos"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="photos" type="file" class="model1" size="10" contentEditable="false"/>
						    <input name="photo" type="hidden" value="${photo}" class="model1"/>
						</td>
					</tr>
				</s:iterator>
				</tbody>
			</table>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</div>
	
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
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe14").offsetHeight-57+"px"});
		},100);
	    	 $(window).resize(function(){ 
				setTimeout(function(){ 				    
				        $("div.bDiv").css({"height":parent.document.getElementById("mainframe14").offsetHeight-57+"px"});
				},100);
	    	 }); 	
	    });
	</script>
	</body>
</html>