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
<title>家庭成员</title>
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
<script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_fmember.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
</head><SCRIPT type="text/javascript">
   var select = 1;
   var memberID='';
   var basePath = '<%=basePath%>';
   var staffID = '${member.staffID}';
   var notoken = 0;
   var token = 0;
   var mainheught = 0; //框架高度
   var ids = ''; //存放行ID
  
   function getValueForParm(id){ //打开页面
		ids = id;
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe6").offsetHeight;
	  	parent.document.getElementById("mainframe6").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
   }
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe6").style.height = mainheught + 'px';
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
			$("#"+ids).find("#reference").val(value2);
			$("#"+ids).find("#referenceCode").val(value3);
			$("#ifr").attr("src","");
			parent.document.getElementById("mainframe6").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</SCRIPT>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<body>
<div id="main_main" class="main_main">
<form  name="familyForm" id="familyForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>
  <table class="fmember">
  <thead>
		  <tr>
		    <th width="30" align="center">选择</th>
            <th width="60" align="center" >姓名</th>
            <th width="80" align="center" >与本人的关系</th>
            <th width="70" align="center" >出生日期</th>
            <th width="100" align="center" >工作单位</th>
            <th width="70" align="center" >职务</th>
			<th width="200" align="center" >岗位类型</th>
            <th width="150" align="center" >工作内容</th>
            <th width="150" align="center" >住址</th>
            <th width="85" align="center" >电话号码</th>
			<th width="85" align="center" >工作电话</th>
			<th width="80" align="center" >证明人</th>
			<th width="100" align="center" >审核人人员编号</th>
			<th width="70" align="center" >审核时间</th>
            <th width="300" align="center" >备注</th>
	      </tr>
	      </thead>
          <!-- 隐 -->
          <tbody id="tbwid">
				<tr align="center" height="22" style="display: none" class="td_bg01 saveAjax model2" id="sa">
				       <td > 
				            <input type="radio" name="a" class="JQuerypersonvalue" value="${staffID}"/>
            			</td>
						<td class="td_bg01">
							<input name="memberName" id="memberName" size="10"/>
						</td>
						<td class="td_bg01">
							<s:select list="codeRelationshipList" listKey="codeID" listValue="codeValue" id="xxx" name="memberRelationship"   theme="simple"></s:select>
						
						</td>
						
						<td class="td_bg01">
							<input name="memberBirthDay"  id="memberBirthDay" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="memberCompanyName" id="memberCompanyName" size="20"/>
						</td>
						<td class="td_bg01">
							<input name="memberPosition" id="memberPosition" size="18"/>
						</td>
						<td class="td_bg01">
							<s:select list="codePostTypeList" listKey="codeID" listValue="codeValue" name="postType"  id="xxx" theme="simple" ></s:select>
						</td>
						
						<td class="td_bg01">
							<input name="memberDuties" 	id="memberDuties" maxlength="25" size="18"/>
						</td>
						
						<td class="td_bg01">
							<input name="memberAddress" id="memberAddress" maxlength="25" size="18"/>
						</td>
						<td class="td_bg01">
							<input name="memberPhone" id="memberPhone" size="15"/>
						</td>
						<td class="td_bg01">
							<input name="workPhone" id="workPhone" size="15"/>
						</td>
						<td class="td_bg01">
							<input name="reference" id="reference" size="10" readonly="readonly"/>
							<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
							<input name="referenceCode" id="referenceCode" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
							<input name="referenceTime" id="referenceTime" onfocus="date(this);" size="10"/>
						</td>
						<td class="td_bg01">
							<input name="memberDesc" id="memberDesc"  size="30"/>
							<input type="hidden" name="staffID" value="${member.staffID}" id="staffID"/>
						</td>						
					</tr>
					


				<!-- 隐 -->
				<s:iterator value="familyList">
					<tr align="center" height="22" class="td_bg01 saveAjax trclass" id="${memberID}">
					<td><input type="radio" name="a" class="JQuerypersonvalue" value="${memberID}"></td>
						<td class="td_bg01">
						<SPAN id="memberName">${memberName}</SPAN>
							<input class="model1" name="memberName" value="${memberName}" size="10"/>
						</td>
						<td class="td_bg01">
						<SPAN id="memberRelationship"></SPAN>
							<s:select list="codeRelationshipList" listKey="codeID" class="model1" listValue="codeValue" name="memberRelationship"   theme="simple" disabled="true"></s:select>
						
						</td>
						
						<td class="td_bg01">
						<SPAN id="memberBirthDay" class="datas">${memberBirthDay}</SPAN>
							<input class="model1"  class="Wdate" value="${memberBirthDay}" name="memberBirthDay" onfocus="date(this);"  size="10" />
						</td>
						<td class="td_bg01">
						<SPAN id="memberCompanyName">${memberCompanyName}</SPAN>
							<input class="model1" name="memberCompanyName" value="${memberCompanyName}" size="20"/>
						</td>
						<td class="td_bg01">
						<SPAN id="memberCompanyName">${memberPosition}</SPAN>
							<input class="model1" name="memberPosition" value="${memberPosition}" size="18"/>
						</td>
						<td class="td_bg01">
						<SPAN id="postType"></SPAN>
							<s:select list="codePostTypeList" listKey="codeID"  class="model1" listValue="codeValue" name="postType"   theme="simple" disabled="true"></s:select>
						
						</td>
						
						<td class="td_bg01">
						<SPAN id="memberDuties">${memberDuties}</SPAN>
							<input class="model1" name="memberDuties" value="${memberDuties}" size="18"/>
						</td>
						<td class="td_bg01">
						<SPAN id="memberAddress">${memberAddress}</SPAN>
							<input class="model1" name="memberAddress" value="${memberAddress}" size="18"/>
						</td>
						<td class="td_bg01">
						<SPAN id="memberPhone">${memberPhone}</SPAN>
							<input class="model1" name="memberPhone" value="${memberPhone}" size="15"/>
						</td>
						<td class="td_bg01">
						<SPAN id="workPhone">${workPhone}</SPAN>
							<input class="model1" name="workPhone" value="${workPhone}" size="15"/>
						</td>
						<td class="td_bg01">
						<SPAN id="reference">${reference}</SPAN>
							<input class="model1" id="reference" name="reference" value="${reference}" size="10" readonly="readonly"/>
							<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
						</td>
						<td class="td_bg01">
						<SPAN id="referenceCode">${referenceCode}</SPAN>
							<input class="model1" id="referenceCode" name="referenceCode" value="${referenceCode}" size="10" readonly="readonly"/>
						</td>
						<td class="td_bg01">
						<SPAN id="referenceTime" class="datas">${referenceTime}</SPAN>
							<input class="model1" name="referenceTime" value="${referenceTime}" onfocus="date(this);"  size="10" />
						</td>
						<td class="td_bg01">
						<SPAN id="memberDesc">${memberDesc}</SPAN>
							<input class="model1" name="memberDesc" value="${memberDesc}" size="30"/>
							<input type="hidden" name="memberKey" type="hidden" value="${memberKey}" />
							<input type="hidden" name="memberID" value="${memberID}" />
							<input type="hidden" name="staffID" value="${staffID}"/>
						</td>						
					</tr>
				</s:iterator>
				</tbody>
  </table>
</form>
</div>
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
					 $("div.bDiv").css({"height":parent.document.getElementById("mainframe6").offsetHeight-57+"px"});
				 },100);
				 $(window).resize(function(){ 			    
					 setTimeout(function(){ 	
					 $("div.bDiv").css({"height":parent.document.getElementById("mainframe6").offsetHeight-57+"px"});
				 },100);
				 }); 	
			});
		</script>
</body>
</html>