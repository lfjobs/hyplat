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
<title>调查情况</title>
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
<script type="text/javascript" src="<%=basePath %>js/ea/human/cstaff/cstaff_investigation.js"></script>

<SCRIPT type="text/javascript">
   var select = 1;
   var investigationID = '';
   var pbasePath = '<%=basePath%>';
   var pstaffID = '${investigation.staffID}';
   var notoken = 0;
   var mainheught = 0; //框架高度
   var ids = ''; //存放行ID
   var isvals = 0; //赋值判断
   
	function getValueForParm(id,isval){ //打开页面
		ids = id;
		isvals = isval;
	  	$("#ifr").attr("src",pbasePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
	  	mainheught = parent.document.getElementById("mainframe12").offsetHeight;
	  	parent.document.getElementById("mainframe12").style.height = 330 + 'px';
	  	$("#jqmWindow2").jqmShow();
	}
	
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	       parent.document.getElementById("mainframe12").style.height = mainheught + 'px';
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			if(isvals == 3){
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
				$("#"+ids).find("#assessor").val(value2);
				$("#"+ids).find("#assessorCode").val(value3);
			}else{
				if(isvals == 1){
					$("#"+ids).find("#investigationPrincipal").val(value2);
				}else if(isvals == 2){
					$("#"+ids).find("#conductPrincipal").val(value2);
				}
			}
			$("#ifr").attr("src","");
			parent.document.getElementById("mainframe12").style.height = mainheught + 'px';
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
 <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form name="investigationForm" enctype="multipart/form-data" id="investigationForm" method="post"><s:token></s:token>
<input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table class="investigation">
  <thead>
    <tr>
       <th width="30" align="center">选择</th>
      <th width="70" align="center" >调查时间</th>
      <th width="70" align="center" >调查项目</th>
      <th width="150" align="center" >调查结论</th>
      <th width="80" align="center" >调查责任人</th>
      <th width="150" align="center" >处理意见</th>
      <th width="80" align="center" >处理责任人</th>
      <th width="70" align="center" >处理时间</th>
      <th width="80" align="center" >审核人</th>
      <th width="85" align="center" >审核人人员编号</th>
      <th width="70" align="center" >审核时间</th>
      <th width="150" align="center" >备注</th>
      <th width="150" align="center" >附件</th>
    </tr>
    </thead>
    <%
		int number = 1;
	%>
<tbody id="tbwid">
    <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
    <td > <input type="radio" name="a" class="JQuerypersonvalue" value="${investigationID }"/>
            			</td>
      <td class="td_bg01">
      <input name="investigationDate" id="investigationDate" onfocus="date(this);" size="10"/></td>
      <td class="td_bg01"><input name="investigationItem" id="investigationItem" size="10"></td>
      <td class="td_bg01"><input name="investigationPeroration" id="investigationPeroration" size="18"></td>
      <td class="td_bg01">
          <input name="investigationPrincipal" id="investigationPrincipal" size="10" readonly="readonly"/>
      	  <a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
      </td>
      <td class="td_bg01"><input name="conductIdea" id="conductIdea" size="18"></td>
      <td class="td_bg01">
      	  <input name="conductPrincipal" id="conductPrincipal" size="10" readonly="readonly"/>
      	  <a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
      </td>
      <td class="td_bg01"><input name="conductDate" id="conductDate" onfocus="date(this);" size="10"/>
			  <input type="hidden" name="investigationKey" 	id="investigationKey" size="10"/>
			  <input type="hidden" name="investigationID" id="investigationID" size="10"/>
			  <input type="hidden" name="staffID"  value="${investigation.staffID}"  id="staffID" size="10"></td>
      <td class="td_bg01">
      	  <input name="assessor" id="assessor" size="10" readonly="readonly"/>
      	  <a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),3)">选择</a>
      </td>
      <td class="td_bg01"><input name="assessorCode" id="assessorCode" size="10" readonly="readonly"></td>
      <td class="td_bg01"><input name="assessorDate" id="assessorDate" onfocus="date(this);" size="10"></td>
      <td class="td_bg01"><input name="investigationDesc" id="investigationDesc" size="30"/></td>
     <td class="td_bg01">
							<input name="photos" id="photo" type="file" contentEditable="false" size="10" size="10"/>
						</td>
     </tr>
    <s:iterator value="investigationList">
    <tr class="td_bg01 saveAjax trclass" id="${investigationID}">
    <td > <input type="radio" name="a" class="JQuerypersonvalue" value="${investigationID}"/>
            			</td>
      <td class="td_bg01">
     		<SPAN id="investigationDate" class="datas">${investigationDate}</SPAN>
	  		<input class="model1" name="investigationDate" value="${investigationDate}"   onfocus="date(this);"" size="10"/></td>
      <td class="td_bg01">
      <SPAN id="investigationItem">${investigationItem}</SPAN>
      <input class="model1" name="investigationItem" value="${investigationItem}" size="10"></td>
      <td class="td_bg01">
       <SPAN id="investigationPeroration">${investigationPeroration}</SPAN>
      <input class="model1" name="investigationPeroration" value="${investigationPeroration}" size="18"></td>
      <td class="td_bg01">
      	<SPAN id="investigationPrincipal">${investigationPrincipal}</SPAN>
      	<input class="model1" id="investigationPrincipal" name="investigationPrincipal" value="${investigationPrincipal}" size="10" readonly="readonly"/>
      	<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)">选择</a>
      </td>
      <td class="td_bg01">
       <SPAN id="conductIdea">${conductIdea}</SPAN>
      <input class="model1" name="conductIdea" value="${conductIdea}" size="18"></td>
      <td class="td_bg01">
      	<SPAN id="conductPrincipal">${conductPrincipal}</SPAN>
      	<input class="model1" id="conductPrincipal" name="conductPrincipal" value="${conductPrincipal}" size="10" readonly="readonly"/>
      	<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),2)">选择</a>
      </td>
      <td class="td_bg01">
       <SPAN id="conductDate" class="datas">${conductDate}</SPAN>
      <input class="model1" name="conductDate" value="${conductDate}" onfocus="date(this);" size="10"/>
					      <input type="hidden" name="investigationKey" value="${investigationKey}"/>
					      <input type="hidden" name="investigationID" id="investigationID"  value="${investigationID}"/>
	                      <input type="hidden" name="staffID" id="staffID" value="${staffID}"></td>
      <td class="td_bg01">
      	<SPAN id="assessor">${assessor}</SPAN>
      	<input class="model1" id="assessor" name="assessor" value="${assessor}" size="10" readonly="readonly"/>
      	<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),3)">选择</a>
      </td>
      <td class="td_bg01">
      	<SPAN id="assessorCode">${assessorCode}</SPAN>
      	<input class="model1" id="assessorCode" name="assessorCode" value="${assessorCode}" size="10" readonly="readonly"/>
      </td>
      <td class="td_bg01">
      <SPAN id="assessorDate" class="datas">${assessorDate}</SPAN>
      <input class="model1" name="assessorDate" value="${assessorDate}" onfocus="date(this);" size="10"></td>
      <td class="td_bg01">
      <SPAN id="investigationDesc">${investigationDesc}</SPAN>
      <input class="model1" name="investigationDesc" value="${investigationDesc}" size="30"/></td>
      <td class="td_bg01">
						  <span><s:if test="photo==null||photo==''">无</s:if></span>
                             <s:else>
                                <span id="photos"  onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                            </s:else>
						    <input name="photos"   type="file" class="model1" size="10" contentEditable="false"/>
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
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe12").offsetHeight-57+"px"});
},100);
	 $(window).resize(function(){ 	
		 setTimeout(function(){
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe12").offsetHeight-57+"px"});
	 },100);
	 }); 	
});
</script>
</body>
</html>