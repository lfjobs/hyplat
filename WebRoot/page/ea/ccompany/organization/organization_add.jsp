<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache">
		<title>组织机构——总公司、子公司</title>
		<%@ page language="java" pageEncoding="UTF-8" %> 
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css"/>
		<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css"/>
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css">
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script> 
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script> 
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>	
		<script type="text/javascript" >
			$(document).ready(function() {
				$(".jqmWindow").jqm({
					modal : true,// 限制输入（鼠标点击，按键）的对话
					overlay : 20// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			});
		</script>	
	</head>
	
<body style="text-align: center;">
<div class="content" style="width:850px;" >
<form name="organizationform" id="organizationform" method="post" enctype="multipart/form-data">
	<input type="submit" name="submit" style="display:none"/>
		<div class="contentbannb">
			<div class="divtx" style="text-align: left;">组织机构管理</div>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" style="color:white;font-weight: bold;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;部门机构信息</td>
				<td align="right">
					<a href="#" onclick="print()" id="print" class="mord isShow" style="color:#0066FF;" >打印</a>&nbsp;
					<a href="#" onclick="changemenu('save')" id="mords" class="mord isHide" style="color:#0066FF;">保存</a>
					<a href="#" onclick="changemenu('edit')" id="mord1" class="mord isShow" style="color:#0066FF;">修改</a>
					<a href="#" onclick="changemenu('close')" id="mord1_close" class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		
		<table width="99%" id="practise"　border="0" align="center" cellpadding="0"
			cellspacing="0" class="table">
		<tr>
              <td width="15%" align="right" class="td_bg"> 机构序号： </td>
       		  <td width="30%" class="td_bg" align="left">
       		  	<input name="organization.organizationNumber" class="input01 isHide" size="20" value="${organization.organizationNumber}" maxlength="3"/>
       		  	<span class="isShow">${organization.organizationNumber}</span>
       		  </td>
       		  
       		  <td colspan="2" class="td_bg" align="center">
       		 		<c:if test="${organization.photoUrl == null }">
       		 			<span class="isShow">无工作范围图</span>
       		 		</c:if>
       		 		<c:if test="${organization.photoUrl != null }">
						<input class="photoUrl isShow" type="button" value="工作范围图附件" />
					</c:if>
					<input type="hidden" id="photoUrl" name="organization.photoUrl" value="${organization.photoUrl}" />
					<input class="photoUrl isHide" type="button" value="工作范围图编辑" />
					&nbsp;&nbsp;&nbsp;
					<c:if test="${organization.orgUrl == null }">
       		 			<span class="isShow">无岗位图</span>
       		 		</c:if>
       		 		<c:if test="${organization.orgUrl != null }">
						<input class="orgUrl isShow" type="button" value="岗位图附件" />
					</c:if>
					<input type="hidden" id="orgUrl" name="organization.orgUrl" value="${organization.orgUrl}" />
					<input class="orgUrl isHide" type="button" value="岗位图编辑" />
       		  </td> 
           </tr>
           <tr>
              <td  align="right" class="td_bg"> 机构编号： </td>
           	  <td class="td_bg" align="left" id="td001"><input name="organization.ocode" maxlength="50"  
				 class="input01 put3 isHide" id="ocode" size="20" value="${organization.ocode }" />      
			  	 <span class="isShow">${organization.ocode }</span>
			  </td>
     	   
           	  <td width="15%" align="right" class="td_bg"> 部门名称： </td>
         	  <td width="40%" id="td002" align="left"><input name="organization.organizationName" 
		 		 class="input01 put3 ckTextLength isHide" maxLength="50" id="organizationName" size="20" value="${organization.organizationName }"/>
		 	     <span class="isShow">${organization.organizationName }</span>
		 	  </td>
           </tr>
           
            <tr>
              <td width="15%"  align="right" class="td_bg"> 岗位编号： </td>
       		  <td width="30%" class="td_bg" align="left" >
       		  	<input name="organization.opostCode" class="input01 put3 isHide" size="20" value="${organization.opostCode}" id="opostCode"/>
       		  	<span class="isShow">${organization.opostCode}</span>
       		  </td>
     	   
           	  <td width="15%" align="right" class="td_bg"> 岗位名称： </td>
         	  <td width="40%" align="left"><input name="organization.opostName" 
		 		 class="input01 put3 ckTextLength isHide" maxLength="50" id="opostName" size="20" value="${organization.opostName }"/>
		 	     <span class="isShow">${organization.opostName }</span>
		 	  </td>
           </tr>
            <tr>
              <td width="15%"  align="right" class="td_bg"> 职务编号： </td>
       		  <td width="30%" class="td_bg" align="left">
       		  	<input name="organization.odutiesID" style="display: none;" class="input01 put3" size="20" value="${organization.odutiesID}" contentEditable="false" />
       		  	 <span class="isHide"><font color="#0000FF">自动生成</font></span>  
       		  	<span class="isShow">${organization.odutiesID}</span>
       		  </td>
     	   
           	  <td width="15%" align="right" class="td_bg"> 职务名称： </td>
         	  <td width="40%" align="left"><input name="organization.odutiesName" 
		 		  class="input01 put3 ckTextLength isHide" maxLength="50" id="odutiesName" size="20" value="${organization.odutiesName }"/>
		 	     <span class="isShow">${organization.odutiesName }</span>
		 	  </td>
           </tr>
           <tr>
           	  <td  align="right" class="td_bg"> 所属上级机构： </td>
              <td align="left" class="td_bg" id="upper">
              	  <input type="text" id="oraganizationName" class="isHide" name="organization.oraganizationName" readonly="readonly" value="${porganization.organizationName }"/><a class="isHide" href="#" onclick="javascript:getPID()"><img src="<%=basePath %>images/up.jpg" style="border: 0;"/></a>
              	  <span class="isShow">${porganization.organizationName }</span>
              </td>
           	 
         	  <td align="right" class="td_bg">负责工作范围：</td>
         	  <td align="left" class="td_bg"><s:select list="%{#request.SInterfaceList}" cssClass="isHide" listKey="interfaceUrl" id="interfaceUrl" listValue="interfaceName"  name="organization.organizationUrl" theme="simple" > </s:select>
         	  	  <span id="interface" class="isShow"></span>
         	  	  <input type="button" class="ACT_btn input-button JQuerySubmit" id="tosave" 
               	  style="display: none; cursor: pointer; width: 80px;" value=" 保存 " />
               	  
				<input type="hidden" name="organization.organizationKey" value="${organization.organizationKey}"/>
				<input type="hidden" name="organization.organizationCreateDate" value="${organization.organizationCreateDate }" />
				<input type="hidden" name="organization.organizationID" id="organizationID" value="${organization.organizationID}"/>
				<input type="hidden" name="organization.organizationPID" id="PID" value="${organization.organizationPID}"/>	
				<input type="hidden" name="organization.companyID" value="${organization.companyID}"/>	
				<input type="hidden" name="organization.Status" value="${organization.status}"/>		
               	  
         	  </td>         	  
           </tr>
           <tr>
           		<td  align="right" class="td_bg"> 是否微分金店： </td>
           		<td align="left" class="td_bg" >
           			<input id="iswfj" name="organization.isWfj" type="radio" value="是" class="isHide"/><span class="isHide">是</span>
                    <input id="isnotwfj" name="organization.isWfj" type="radio" value="否" class="isHide" checked="true"/><span class="isHide">否</span>
					<span class="isShow">${organization.isWfj}</span>
           		</td>
           		<td  align="right" class="td_bg"> 存放微分金店： </td>
           		<td align="left" class="td_bg" colspan="3">
           			<input id="storageWFJ" name="organization.storageWFJ" type="checkbox" value="00" class="isHide" style="margin-left: 10px;margin-top: 5px;"/>
					<span class="isShow"> <c:if test="${organization.storageWFJ=='00'}">是</c:if></span>
           		</td>
           </tr>
           <tr>
           	<td align="right" class="td_bg">
           		菜单导航：
           	</td>
           	<td colspan="3" align="left">
           		<input type="button" onclick="mao('0','0')" value="全部"/>
           		<input type="button" onclick="mao('table3','table33')" value="银行帐号"/>
           		<input type="button" onclick="mao('table4','table44')" value="负责人员"/>
           		<input type="button" onclick="mao('table5','table55')" value="机构职责"/>
           		<input type="button" onclick="mao('table6','table66')" value="微分金"/>
           	</td>
           </tr>
        </table>
	    <s:token></s:token>
		
		<div style="overflow-y:scroll;" class="gdkd" id="div2">
            
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti hel" id="table3">
		      <tr>
		      	<td height="27" style="color:white;font-weight: bold;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构银行帐号</td>
		      </tr>
			</table>
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact hel" id="table33">
             <tr><td>
			 <div style="width: 100%;">
				<iframe src="" name="main" height="265px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" transparent="transparent" 
				     id="mainframe3" border="0" framespacing="0" noresize="noResize" 
				     vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
           
           	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti hel" id="table4">
		      <tr>
		      	<td height="27" style="color:white;font-weight: bold;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构负责人</td>
		      </tr>
			</table>
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact hel" id="table44">
             <tr><td>
			 <div style="width: 100%;">
				<iframe src="" name="main" height="265px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" transparent="transparent" 
				     id="mainframe4" border="0" framespacing="0" noresize="noResize" 
				     vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
            
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti hel" id="table5">
		      <tr>
		      	<td height="27" style="color:white;font-weight: bold;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;机构职责</td>
		      </tr>
			</table>
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact hel" id="table55">
             <tr><td>
			 <div style="width: 100%;">
				<iframe src="" name="main" height="265px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" transparent="transparent" 
				     id="mainframe5" border="0" framespacing="0" noresize="noResize" 
				     vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
            
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti hel" id="table6">
		      <tr>
		      	<td height="27" style="color:white;font-weight: bold;" align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微分金</td>
		      </tr>
			</table>
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact hel" id="table66">
             <tr><td>
			 <div style="width: 100%;">
				<iframe src="" name="main" height="265px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" transparent="transparent" 
				     id="mainframe6" border="0" framespacing="0" noresize="noResize" 
				     vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
            
		</div>
	</form>
</div>
<div style="display:none;width: 210px; height: 240px;position: absolute;top: 31%;left: 25%;z-index: 4 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
	id="jqModel"></div>
	<input type="text" style="display: none;" id="treeid" />
	<input type="text" style="display: none;" id="parentid" />
	<input type="text" style="display: none;" id="treename" />
	<input type="text" style="display: none;" id="parentname" />
	<input type="text" style="display: none;" id="unitsID" />

<div id="jqmWindow2" class="jqmWindow"
	style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
	<div style="background: #efg; margin-right: 500px;">
		<input style="display: none;" id="myform" />
		<input style="display: none;" id="parm" />
	</div>
	<iframe name="ifr" id="ifr" width="100%" height="380px"
		frameborder="0"></iframe>
	<div align="center">
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
</div>

<!-- 项目预算添加表单结束 -->
		<%--******************************************物品选择****************************************--%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择物品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="142">
								<input name="typeID" class="input" id="typeID" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
								<input type="hidden" id="clicktr" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="aadTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

<script type="text/javascript">
	var iswfj = '${ organization.isWfj }';	
	var storageWFJ='${ organization.storageWFJ }'
	var num='${num}';
    var basePath = "<%=basePath%>";
    var notoken = 0;
    $("#iswfj").click(function() {
    	var ischeck=$("input[type='checkbox']").is(':checked');
        var isw=$("#iswfj").is(':checked');
        if(isw==true && ischeck==true){
        	$("#iswfj").attr("checked", false);
        	$("#isnotwfj").attr("checked", true);
			alert("此部门用来存放微分金店不能设置为微分金店！");
			return;
        }
    });
    $("#storageWFJ").click(function() {
    var ischeck=$("input[type='checkbox']").is(':checked');
    var isw=$("#iswfj").is(':checked');
        if(ischeck == true){
        	if(num>0){
        		$("input[type='checkbox']").attr("checked", false);
				alert("已设置存放部门不能重复设置！");
				return;
			}if(isw==true){
				$("input[type='checkbox']").attr("checked", false);
				alert("此部门已设置成微分金店铺！");
				return;
			}else{
				$("input[type='checkbox']").attr("checked", true);
        		return;
			}
        }else if(storageWFJ=="00"){
        	$("input[type='checkbox']").attr("checked", true);
        	alert("不可修改！");
			return;
        }
	});
	document.onkeydown = function() {//捕捉回车   
		if (event.keyCode == 13) {
			$("#tosave").trigger("click");
			return false;
		}
	};
	$("span#interface").text($("#interfaceUrl").find("option:selected").text());
	var pogID = '<%=request.getAttribute("porganization.organizationID") %>';
	var pogName = '<%=request.getAttribute("porganization.organizationName") %>';
	var ogName = $("#organizationName").val();
	var ogID = $("#organizationID").val();
	$("#PID").attr("value",pogID);
	
	var url1 = basePath + "ea/departmentpost/ea_getOrgPostListByOrg.jspa?departmentPost.organizationID=" + ogID + "&ogName=" + ogName;
	var url2 = basePath + "ea/cosincumbent/ea_getStaffList.jspa?ogID="+ogID+"&ogName="+ogName;
	var url3 = basePath + "ea/institutionsregistration/ea_getListInstitutionsRegistration.jspa?institutionsRegistration.organizationPID="+ogID+"&ogName="+ogName;
	var url4 = basePath + "ea/agencies/ea_getListAgencies.jspa?agencies.organizationPID="+ogID+"&ogName="+ogName;
	var url5 = basePath + "ea/organizationdesc/ea_getOrganizationdescList.jspa?organizationdesc.organizationid="+ogID+"&ogName="+ogName;
	var url6 = basePath + "ea/marketingWfj/ea_getWfjEshopList.jspa?eshop.organizationID="+ogID+"&ogName="+ogName;;
	var i = "";
		
	if(ogID != ''){
		$("#mainframe1").attr("src",encodeURI(url1));
		$("#mainframe2").attr("src",encodeURI(url2));
		$("#mainframe3").attr("src",encodeURI(url3));
		$("#mainframe4").attr("src",encodeURI(url4));
		$("#mainframe5").attr("src",encodeURI(url5));
		$("#mainframe6").attr("src",encodeURI(url6));
		$("#ifrs").show();
	}else{
		$("#PID").val(pogID);
		changemenu("edit");
	}
	//工作范围图
	$(".photoUrl").click(function() {
		var photoUrl = $.trim($("#photoUrl").attr("value"));
		var urlReturn = OpenWord(photoUrl, 2);
		$("#photoUrl").attr("value", urlReturn);
	});
	//岗位图
	$(".orgUrl").click(function() {
		var orgUrl = $.trim($("#orgUrl").attr("value"));
		var urlReturn = OpenWord(orgUrl, 2);
		$("#orgUrl").attr("value", urlReturn);
	});
	
	 //选择机构责任人        
	function searchCoach(){
		 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ pogID + "&title1=title1";
		 getValueForParm('practise','partnerName',url);
	}
	
	function getValueForParm(attachTable,parm,url){ //打开页面
		 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
		 $("#parm",$("#jqmWindow2")).attr("value",parm);
	  	 $("#ifr").attr("src",basePath+url);
	  	 $("#jqmWindow2").jqmShow();
	}
	
	$(document).ready(function() {
		if(storageWFJ=='00'){
			$("input[type='checkbox']").attr("checked", true);
		}
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	    }); 
	   
		$("#isSubmit").click(function(){// 确定
			var myfrom=$("#myform",$("#jqmWindow2")).attr("value");
			var parm = $("#parm",$("#jqmWindow2")).attr("value");
			var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择");
				return;
			}
			
			var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
			
			if(parm != "")
			 $("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
			$("#ifr").attr("src","");
	        $("#jqmWindow2").jqmHide();
	    });
	});
//修改保存基本信息
	$(function() {
		var ttt = "";
		$("#tosave").click(function() {
			if(notoken){
				alert("数据正在提交！");
				return;
			}
			if ($("form .error").length) {
				return;
			}
			var organizationName = $("#organizationName").val();
			var organizationID = $("#organizationID").val();
			var ocode = $("#ocode").val();
			var formData = $("#organizationform").serialize();
		 	var url =basePath+"ea/corganization/sajax_ea_validateName.jspa?" + formData +"&data="+new Date();
		 	$.ajax({
					url: url,
					type: "get",
					async: true,
					dataType: "json",
					success: function(data){
						var member = eval("(" + data + ")");
						var msg = member.ajaxString;
		     			if(msg=="succ"){
		     				$("#td002").attr("value","");
		     				$("#td002").find(".corect").remove();
		     				$("#td002").append("<span class=\"error\"><a class=\"tex\">机构名称已禁用</a></span>&nbsp;");
		     				$("#td002").append("<span class=\"error\"><input type='button'onclick='javascript:addInput()' value='&nbsp;启用&nbsp;'></span>");
		     				return;
		     			}else if(msg=="err"){
			     			$("#organizationName").attr("value","");
			     			$("#td002").find(".corect").remove();
			     			$("#td002").append("<span class=\"error\"><a class=\"tex\">机构名称已存在，请重新输入</a></span>&nbsp;");
		     				return;
		     			}else if(msg=="erro"){
		     				$("#td001").append("<span class=\"error\"><a class=\"tex\">已存在</a></span>");
		     				return;
		     			}else if(msg=="suc"){
		     				$("#td002").append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
			     			notoken = 1;
							var url = "ea/corganization/sajax_ea_saveOrganization.jspa?ogID=ogID";
							var formData = $("#organizationform").serialize();
							formData = decodeURIComponent(formData,true);
							$.ajax({
									url : encodeURI(basePath + url + "&" + formData),
									type: "get",
									async: true,
									dataType: "json",
									success: function cbf(data){
										var member = eval("(" + data + ")");
										var ogIDs = member.ogIDs;
										window.location.href = basePath 
												+ "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
						 						+ pogID + "&porganization.organizationName=" + pogName + "&organization.organizationID="+ogIDs;
										window.opener.refresh();//刷新父页面
										
										//添加父页面树的子id
										var oid = '${organization.organizationID}';
										var oname = $("#organizationName").val();
										if (oid == "") {
											window.opener.parent.tree.insertNewChild(
											window.opener.parent.treeid,ogIDs,oname,0, 0, 0, 0);
										}else{
											window.opener.parent.tree.setItemText(ogIDs,oname);
										}
									},
									error: function cbf(data){
										alert("数据保存失败！");
									}
							});
						}
		   			}
			});
	});
});

$(".jqmWindow").jqm({
       modal: true,// 限制输入（鼠标点击，按键）的对话  
       overlay: 20 // 遮罩程度%  
   }).jqmAddClose('.close');// 添加触发关闭的selector  
	var companyID='${company.companyID}'; 
	var companyName='${company.companyName}';
	var parentID;
	var treeid = null;
	var treename;
	var parentid;
	var parentname;
	var organizationid;
	var tree;
	var me = document.getElementById("oraganizationName").value;
    var outme = document.getElementById("organizationName").value;
	tree = new dhtmlXTreeObject("jqModel", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	tree.insertNewChild("0", pogID, pogName, 0, 0, 0, 0);
	$.ajaxSetup({async:false});
	tree.setOnClickHandler(function() {
		getDatas();
	});
	tree.setOnDblClickHandler(function(){
		$("#PID").attr("value",treeid);
		$("#oraganizationName").attr("value",treename);
		$("#jqModel").hide();
	});
	function getPID() {
    	if(document.getElementById("jqModel").style.display=="none"){
    		$("img","#upper").attr("src",basePath + "images/down.jpg");
			$("#jqModel").show();
		}else{
			$("img","#upper").attr("src",basePath + "images/up.jpg");
			$("#jqModel").hide();
		}
	}
	function getDatas(){
		treeid = tree.getSelectedItemId();//pogID
		treename = tree.getItemText(treeid);
		parentid = tree.getParentId(treeid);
		parentname = tree.getItemText(parentid);
		tree.deleteChildItems(treeid);
		
		var url1 = basePath + "ea/organization/sajax_ea_getOrganizationList.jspa?organizationID="+treeid+"&date="+new Date().toLocaleString();
			$.ajax({
				url: encodeURI(url1),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
	                  	if(nologin){
	                  		document.location.href =basePath+"page/ea/not_login.jsp";
	                    }
						var organizationList = member.organizationList;
						if (null == organizationList) {
							return;
						}
						for (var i = 0; i < organizationList.length; i++) {
							if(outme==organizationList[i].organizationName && outme != ""){
								continue;	
							}
							tree.insertNewChild(treeid,
									organizationList[i].organizationID,
									organizationList[i].organizationName,
									0, 0, 0, 0);
						}
				},
				error: function cbf(data){
				alert("数据获取失败！");
				}
		});
	}
	
	$(function(){   
		setTimeout(function(){ 
	        $("div.gdkd").css({"height":GetPageSize()[3]-200+"px"});
	 	},100);
		$(window).resize(function(){ 		
			 setTimeout(function(){ 
			        $("div.gdkd").css({"height":GetPageSize()[3]-200+"px"});
			 },100);
		}); 	
	});	
function print(){
	open(basePath + "ea/corganization/ea_toPrint.jspa?organization.organizationID="+ogID+"&porganization.organizationName="+pogName+"&date="+new Date());
}
function mao(u,u1){
	if(u == "0"){
		$("#div2").find(".hel").show();
	}else{
		$("#div2").find("table.hel").each(function() {
			if(this.id == u ){
				$("#div2").find("#"+this.id).show();
			}else if(this.id == u1){
				$("#div2").find("#"+this.id).show();
			}else{
				$("#div2").find("#"+this.id).hide();
			}
		});
	}
	}
function changemenu(menu){
	if(menu == 'save'){
		$("#organizationName").blur();	
		$("#ocode").blur();	
		$("#opostCode").blur();	
		$("#opostName").blur();	
		$("#odutiesName").blur();	
		$("#tosave").click();
		
	}else if(menu == 'edit'){
		$(".isHide").show();
		$(".isShow").hide();
		if (iswfj=="是") {
			$("#iswfj").attr("checked","true");
		}else{
			$("#isnotwfj").attr("checked","true");
		}		
	}else{
		if(ogID != ''){
			$(".isHide").hide();
			$(".isShow").show();
		}else{
			window.close();
		}
		$("#jqModel").hide();
	}
}

function addInput(){
	var url = basePath + "ea/corganization/sajax_ea_resOrganization.jspa?ogID=ogID";
	var formData = $("#organizationform").serialize();
	formData = decodeURIComponent(formData,true);
	$.ajax({
			url : encodeURI(url + "&" + formData),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var ogIDs = member.ogIDs;
				window.location.href = basePath 
						+ "ea/corganization/ea_toAdd.jspa?pageNumber=${pageNumber}&porganization.organizationID="
 						+ pogID + "&porganization.organizationName=" + pogName + "&organization.organizationID="+ogIDs;
 				window.opener.refresh();//刷新父页面
			},
			error: function cbf(data){
				alert("数据保存失败！");
			}
	});
}

</script>
</body>
</html>