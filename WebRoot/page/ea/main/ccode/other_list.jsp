<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>other管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
</style>
<script type="text/javascript">
   var basePath="<%=basePath%>";
   var treeID = "${code.codeID}";
   var treePID = "${code.codePID}";
   var codeValue = "${code.codeValue}";
   var codeSn="${code.codeSn}";
   var maxNum = 0;

	$(function(){
       $("#jqModel").jqm({
	        modal: true,// 限制输入（鼠标点击，按键）的对话  
	        overlay: 20 // 遮罩程度%  
	    }).jqmAddClose('.close')// 添加触发关闭的selector  
				.jqDrag('.drag');
	 $("#save").click(function(){
	 if($("#newParent").val()=="0"){
	 	alert("请选择分类...");
	 	return;
	 }
	       $('#editcodeForm').attr("action","<%=basePath%>ea/ccode/t_ea_updateParent.jspa");
		   document.editcodeForm.submit.click();
	    })
    $('.flexme11').flexigrid({
		height: 300,
		width: 'auto',
		minwidth: 30,
		title: '请将以下代码修改到指定模块中',
		minheight: 80
	});
 });
 function toedit(codeID,codeStatus){
                var url = basePath + "ea/ccode/sajax_ea_editCCode.jspa?codeID="+encodeURI(codeID)+"&date="+new Date().toLocaleString();
				 $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
					var member = eval("(" + data + ")");
					 var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
					var code = member.code;
					$("#codePID").attr("value", code.codePID);
                    $("#codeID").attr("value", code.codeID);
                    $("#codeKey").attr("value", code.codeKey);
                    $("#codeStatus").attr("value", code.codeStatus);
                    $("#codeNumber").attr("value", code.codeNumber);
                    $("#codeValue").attr("value", code.codeValue);
                    $("#codeSn").attr("value", code.codeSn);
                    $("#desc").attr("value", code.codeDesc);
                     $("#jqModel").jqmShow();
				}, error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
 }

function re(){
					$("select#newParent").find("option[value='0']").attr("selected", "selected");
                    $("#codePID").attr("value", "");
                    $("#codeID").attr("value", "");
                    $("#codeKey").attr("value", "");
                    $("#codeStatus").attr("value", "");
                    $("#codeNumber").attr("value", "");
                    $("#codeValue").attr("value", "");
                    $("#codeSn").attr("value", "");
                    $("#desc").attr("value", "");
                    $("#jqModel").jqmHide();
}
function alee(codeID){
       toedit(codeID);
}
</script>
</head>
<body > 
<form method="post" name="sortchildren" id="sortchildren"><%-- 不告诉你--%><input type="submit" name="submit" style="display:none"/>
			<input id="oID" name="codeID" type="hidden"/>
	</form>
<form name="codeForm" method="post" id="codeForm"><input type="submit" name="submit" style="display:none"/>
        <input name="sub" value="${session_value}" type="hidden"/><!-- 代替token-->
<div>
  <table class="flexme11" width="600">
        				<thead>
			    	   	<tr>
			             	<th width="90" align="center">
								代码序号
							</th>
							<th width="90" align="center">
								代码编号
							</th>
							<th width="330" align="center">
								代码名称
							</th>
							 <s:if test="codeID=='scode201007306kdf8m76me0000000001'">
     						 <th width="130" align="center">
								得分类别
							</th>
   							 </s:if>
							<th width="302" align="center">
								代码描述
							</th>
							<th width="80" align="center">
								状态
							</th>
							<th width="114" align="center">
								修改
							</th>
			            </tr>
						</thead>
								 <tbody>
							         	<s:iterator value="pageForm.list">
							    		<tr ondblclick="alee('<s:property value="codeID"/>')" >
							            	<td><s:property value="codeNumber" /></td>
										<td>
											<s:property value="codeSn" />
										</td>
										<td>
											<s:property value="codeValue" />
										</td>
										<s:if test="codePID=='scode201007306kdf8m76me0000000001'">
				     					<td width="130" align="center">
												${codeStatus=='00'?'系统预设':wageStatus==null?'未定义':wageStatus=='00'?'加分项':'减分项'}
										</td>
				   						</s:if>
										<td >
											<s:property value="codeDesc" />
										</td>
										<td>
										    <s:if test="codeStatus == 00">不可停用</s:if>
										    <s:if test="codeStatus == 01">可停用</s:if>
										    <s:if test="codeStatus == 98">已停用</s:if>
										</td>
										<td>
										   <a href="#" onclick="toedit('<s:property value="codeID"/>','<s:property value="codeStatus"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
										</td>
							            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/ccode/ea_getCodeListAll.jspa?pageNumber=${pageNumber}&codeID=${codeID}"></c:param>
						</c:import>
						<s:token />
			</div>
		</form>
<form name="editcodeForm" id="editcodeForm" method="post">		<input type="submit" name="submit" style="display:none"/>
	<div class="jqmWindow jqmWindowcss3"  style="width: 600px;top:10%;" id="jqModel" >
	<div class="drag">代码设置:
   	       <div class="close" onclick="re()"></div>
	    </div>
	     <div class="unitlib_list_right02" >
				<table width="100%" 　border="0" align="center" cellpadding="0" cellspacing="0">
					<input type="hidden" name="codeID" value="scode20141030rnzhjs7ap60000000331"/>
					<input style="display: none; " name="code.codeID" value="${code.codeID}" type="text" id="codeID" />
					<input style="display: none;" name="code.codeKey" value="${code.codeKey}" type="text" id="codeKey" />
					<input style="display: none;" name="code.codeStatus" value="${code.codeStatus}" type="text" id="codeStatus" />
					<tr>
						<td height="44" align="right" class="td_bg">序号：</td>
						<td class="td_bg"><input readonly="readonly" type="text"
							name="code.codeNumber" id="codeNumber" /></td>
						<td height="45" align="right" class="td_bg">代码编号：</td>
						<td height="45" class="td_bg"><input name="code.codeSn" readonly="readonly"
							maxlength="10" type="text" id="codeSn" /></td>
					</tr>
					<tr>
						<td height="45" align="right" class="td_bg">代码名称：</td>
						<td height="45" class="td_bg iscode"><input
							name="code.codeValue" type="text" id="codeValue" readonly="readonly"/></td>
						<td height="45" align="right" class="td_bg"></td>
						<td height="45" class="td_bg"></td>
					</tr>
					<tr>
						<td height="39" colspan="4" align="left" class="td_bg">将上面的分类修改到下面的分类中:</td>
					</tr>
					<tr>
						<td height="39" align="center" class="td_bg">分类:</td>
						<td height="39" colspan="3" align="left" class="td_bg">
							<s:select id="newParent" name="parmiter" list="#basicMap" headerKey="0" headerValue="请选择" key="#key" value="#value"></s:select>
						</td>
					</tr>
				</table>
				<s:token/>
				</div>
	     <div align="center">  
	      <input type="button"  id="save"  class="input-button JQuerySubmit" value=" 保存 " /><input  type="button" onclick="re()"  class="input-button JQuerySubmit" value="取消" />
	    </div> 
	</div>
</form>
<script type="text/javascript">
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
</script>       
	</body>
</html>	    
							    
	