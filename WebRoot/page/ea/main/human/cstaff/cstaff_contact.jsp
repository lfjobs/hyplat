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
<title>联系方式</title>
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
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_contact.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var contactID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${contact.staffID}'; 
   var notoken = 0;
   var mainheught = 0; //框架高度
   var ids = ''; //存放行ID
   var isvals = 0; //赋值判断
   
	function getValueForParm(id,isval){ //打开页面
		ids = id;
		isvals = isval;
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe3").offsetHeight;
	  	parent.document.getElementById("mainframe3").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
	}
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe3").style.height = mainheught + 'px';
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			if(isvals == 1){
				$("#"+ids).find("#contactName").val(value2);
			}else if(isvals == 2){
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
				$("#"+ids).find("#assessor").val(value2);
				$("#"+ids).find("#assessorCode").val(value3);
			}
			$("#ifr").attr("src","");
			parent.document.getElementById("mainframe3").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>

  <body>
 		<form  name="contactForm" id="contactForm" method="post"><input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="contact">
  	<thead>
	    <tr>
	    <th width="30" align="center">选择</th>
	      <th width="70" align="center" >联系类型</th>
	      <th width="85" align="center" >联系号码</th>
	      <th width="80" align="center" >联系姓名</th>
	      <th width="70" align="center" >登记日期</th>
	      <th width="80" align="center" >审核人</th>
	      <th width="70" align="center" >审核人编号</th>
	      <th width="70" align="center" >审核时间</th>
	      <th width="178" align="center">备注</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td><input type="radio" name="a"  class="JQuerypersonvalue" value="${staffID}" /></td>
		       <td class="td_bg01"><s:select list="contactTypelist"  listKey="codeID" listValue="codeValue"  name="contactType" id="xxx" theme="simple"></s:select></td>
		      <td><input name="contactWay" id="contactWay" size="10"/></td>
		      <td class="td_bg01">
		      	<input name="contactName" id="contactName" size="10" readonly="readonly"/>
		      	<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
		      </td>
		      <td class="td_bg01"><input name="contactDate" id="contactDate" onfocus="date(this);"  size="10"/></td>
		      <td class="td_bg01"><input name="assessor" id="assessor" size="10" readonly="readonly"/>
		      					  <a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
							      <input type="hidden" name="contactKey" id="contactKey"/>
							      <input type="hidden" name="contactID" id="contactID"/>
							      <input type="hidden" name="staffID" value="${contact.staffID}" id="staffID"/>
			  </td>
			  <td class="td_bg01"><input name="assessorCode" id="assessorCode" size="10" readonly="readonly"/></td>
		      <td class="td_bg01"><input name="assessorDate" id="assessorDate" onfocus="date(this);" size="10"/></td>
		      <td class="td_bg01"><input name="contactDesc" id="contactDesc" size="40"/></td>
	    </tr>
    <s:iterator value="contactList">
          <tr class="td_bg01 saveAjax trclass" id="${contactID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${contactID}"/>
	            	</td>
	            	<td class="td_bg01">  <span id="contactType"></span>
      					  <s:select list="contactTypelist" id="contactType" listKey="codeID" listValue="codeValue" class="model1" disabled="true" name="contactType"  theme="simple"></s:select></td>
		            <td class="td_bg01"><span id="contactWay">${contactWay}</span>
      					<input class="model1" name="contactWay"  value="${contactWay}" size="10" />
      				</td>
      				 <td class="td_bg01"><span id="contactName">${contactName}</span>
      					  <input class="model1" id="contactName" name="contactName" value="${contactName}" size="10" readonly="readonly"/>
      				 	  <a  href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
      				 </td>
      				 <td class="td_bg01"><span id="contactDate" class="datas">${contactDate}</span>
      				      <input class="model1"  value="${contactDate}" name="contactDate" onfocus="date(this);" size="10"/></td>
      				 <td class="td_bg01"><span id="assessor">${assessor}</span>
     					  <input class="model1" id="assessor" name="assessor" value="${assessor}" size="10" readonly="readonly"/>
     					  <a class="model1" href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
					      <input type="hidden" name="contactKey" value="${contactKey}"/>
					      <input type="hidden" name="contactID" id="contactID" value="${contactID}"/>
					      <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
	 </td>
	 
	 <td class="td_bg01">
	 <span id="assessorCode">${assessorCode}</span>
	 <input class="model1" name="assessorCode" value="${assessorCode}" id="assessorCode" size="10" readonly="readonly"/></td>
      <td class="td_bg01">
      <span id="assessorDate" class="datas">${assessorDate}</span>
      <input class="model1" name="assessorDate" value="${assessorDate}" onfocus="date(this)" size="10"/></td>
      <td class="td_bg01">
      <span id="contactDesc">${contactDesc}</span>
      <input class="model1" name="contactDesc" value="${contactDesc}" size="40"/></td>
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe3").offsetHeight-57+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 			
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe3").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>