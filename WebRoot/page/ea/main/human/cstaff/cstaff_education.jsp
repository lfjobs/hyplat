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
		<title>学历学位</title>
		<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff/cstaff_education.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<SCRIPT type="text/javascript">
		   var token = 0;
		   var select = 1;
		   var educationID='';
		   var basePath = '<%=basePath%>';
		   var staffID = '${education.staffID}'; 
		   var notoken = 0;
		   var mainheught = 0; //框架高度
		   var ids = ''; //存放行ID
		   var isvals = 0; //赋值判断
		   
		   function getValueForParm(id,isval){ //打开页面
				ids = id;
				isvals = isval;
			  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
			  	mainheught = parent.document.getElementById("mainframe4").offsetHeight;
			  	parent.document.getElementById("mainframe4").style.height = 330 + 'px';
			  	$("#jqmWindow2").jqmShow();
		   }
			
			$(document).ready(function() {
				$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			       parent.document.getElementById("mainframe4").style.height = mainheught + 'px';
			    }); 
			   
				$("#isSubmit").click(function(){// 选择确定
					var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
					if(value1 == ""){
						alert("请选择")
						return;
					}
					var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
					if(isvals == 1){
						$("#"+ids).find("#provePerson").val(value2);
					}else if(isvals == 2){
						var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
						$("#"+ids).find("#assessor").val(value2);
						$("#"+ids).find("#assessorCode").val(value3);
					}
					$("#ifr").attr("src","");
					parent.document.getElementById("mainframe4").style.height = mainheught + 'px';
			        $("#jqmWindow2").jqmHide();
			    });
			});
		</script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>
<form  enctype="multipart/form-data"  name="educationForm" id="educationForm" method="post">
<s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table class="education">
   <thead>
	     <tr>
	        <th width="30" align="center">选择</th>
            <th width="75" align="center">入学日期</th>
            <th width="75" align="center" >毕业日期</th>        
            <th width="120" align="center" >学校（培训机构）名称</th>
            <th width="90" align="center" >所学专业类型</th>
            <th width="90" align="center" >学历</th>
            <th width="60" align="center" >学习形式</th>
            <th width="60" align="center" >教育类别</th>
            <th width="80" align="center" >证明人</th>
            <th width="85" align="center" >证明人电话</th>
            <th width="80" align="center" >审核人</th>
            <th width="75" align="center" >审核人员编号</th>
            <th width="70" align="center" >审核时间</th>
            <th width="200" align="center" >备注</th>
             <th width="150" align="center" >附件</th>
          </tr>
          </thead>
			<tbody id="tbwid">
			 <tr class="td_bg01 saveAjax model2" id="sa" style="display: none">
          	<td><input type="radio" name="a" class="JQuerypersonvalue" /></td>
            <td class="td_bg01"><input   name="educationStart" id="educationStart"  onfocus="date(this);" size="10"/> </td>
            <td class="td_bg01"><input   name="educationEnd" id="educationEnd"  onfocus="date(this);"  size="10"/></td>
            <td class="td_bg01"><input name="educationName" id="educationName" size="30"/></td>
            <td class="td_bg01"><s:select list="majorTypelist" listKey="codeID" id="xxx" listValue="codeValue" name="majorType" theme="simple"></s:select></td>
            <td class="td_bg01"><s:select list="educationHistorylist" listKey="codeID" id="xxx"  listValue="codeValue" name="educationHistory"   theme="simple"></s:select> </td>
            <td class="td_bg01"><s:select list="educationStylelist" listKey="codeID" id="xxx"  listValue="codeValue" name="educationStyle"   theme="simple"></s:select></td>
            <td class="td_bg01"><s:select list="educationTypelist" listKey="codeID" id="xxx"  listValue="codeValue" name="educationType"  theme="simple"></s:select></td>
            <td class="td_bg01">
            	<input name="provePerson" id="provePerson" size="10" readonly="readonly"/>
            	<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
            </td>
            <td class="td_bg01"><input name="provePhone" id="provePhone" size="15"/>
					            <input type="hidden"  name="educationKey" id="educationKey"/>
					            <input type="hidden" name="educationID" id="educationID"/>
					            <input type="hidden" name="staffID"  value="${education.staffID}"/>
			</td>
			<td class="td_bg01">
				<input name="assessor" id="assessor" size="10" readonly="readonly"/>
				<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
			</td>
			<td class="td_bg01"><input name="assessorCode" id="assessorCode" size="10" readonly="readonly"/></td>
			<td class="td_bg01"><input name="assessorDate" id="assessorDate" onfocus="date(this);" size="10"/></td>
			<td class="td_bg01"><input name="educationDesc" id="educationDesc" size="40"/></td>
			<td class="td_bg01">
							<input name="filePhoto" id="filePhoto" type="file" contentEditable="false"/>
			</td>
          </tr>
          <s:iterator value="educationList">
          <tr class="td_bg01 saveAjax trclass" id="${educationID}">
          <td > <input type="radio" name="a" class="JQuerypersonvalue" value="${educationID}"/>
            			</td>
            <td class="td_bg01">
                    <span id=" educationStart" class="datas">${educationStart}</span>
					<input class="model1"   value="${educationStart}" name="educationStart"  onfocus="date(this);" size="10"/></td>
            <td class="td_bg01">
           			 <span id=" educationEnd" class="datas">${educationEnd}</span>
					<input class="model1" value="${educationEnd}" name="educationEnd"   onfocus="date(this);" size="10"/></td>
            <td class="td_bg01">
		           <span id=" educationName"> ${educationName}</span>
		            <input class="model1" name="educationName" value="${educationName}" size="30"/></td>
            <td class="td_bg01">
		             <span id=" majorType"></span>
		            <s:select  list="majorTypelist"  class="model1" listKey="codeID" listValue="codeValue"  name="majorType"  theme="simple" disabled="true"></s:select></td>
		    <td class="td_bg01">
             		<span id=" educationHistory"></span>
           			 <s:select  list="educationHistorylist" class="model1"  listKey="codeID"  listValue="codeValue" name="educationHistory"  disabled="true"  theme="simple"></s:select> </td>
            <td class="td_bg01">
             		<span id=" educationStyle"></span>
           		 	<s:select   list="educationStylelist" class="model1"  name="educationStyle"   listKey="codeID" listValue="codeValue" disabled="true"  theme="simple"></s:select></td>
            <td class="td_bg01">
            		 <span id=" educationType"></span>
            <s:select   list="educationTypelist" class="model1"  name="educationType"   listKey="codeID" listValue="codeValue" disabled="true" theme="simple"></s:select></td>
            <td class="td_bg01">
            	<span id=" provePerson"> ${provePerson}</span>
            	<input class="model1" id="provePerson" name="provePerson" value="${provePerson}" size="10" readonly="readonly"/>
            	<a class="model1" href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
            </td>
            <td class="td_bg01">
           			 <span id=" provePhone"> ${provePhone}</span>
            <input class="model1" name="provePhone" value="${provePhone}"  size="13"/>
					            <input type="hidden" value="${educationKey}" name="educationKey"/>
					            <input type="hidden" name="educationID" value="${educationID}" id="educationID"/>
					             <input type="hidden" name="staffID" id="staffID" value="${staffID}"/>
            </td>
            <td class="td_bg01">  
            	<span id=" assessor"> ${assessor}</span>
            	<input class="model1" name="assessor" value="${assessor}" id="assessor" size="10" readonly="readonly"/>
            	<a class="model1" href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
            </td>
			<td class="td_bg01">  <span id=" assessorCode"> ${assessorCode}</span>
					<input class="model1" name="assessorCode" value="${assessorCode}" id="assessorCode" size="10" readonly="readonly"/></td>
			<td class="td_bg01">  <span id=" assessorDate" class="datas"> ${assessorDate}</span>
					<input class="model1" name="assessorDate" value="${assessorDate}" id="assessorDate" onfocus="date(this);"  size="10"/></td>
			<td class="td_bg01">  <span id=" educationDesc"> ${educationDesc}</span>
					<input class="model1" name="educationDesc" value="${educationDesc}" id="educationDesc" size="10"/></td>
			<td class="td_bg01">
			<span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="filePhoto"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="filePhoto"   type="file" class="model1" size="10" contentEditable="false" />
						    <input name="photo" type="hidden" value="${photo}" class="model1" />
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe4").offsetHeight-57+"px"});
 },100);
	 $(window).resize(function(){ 			    
		 setTimeout(function(){ 	
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe4").offsetHeight-57+"px"});
		 },100);
	 }); 	
});
</script>
</body>
</html>
