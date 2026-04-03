<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<title>会计科目管理--期初余额添加</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"
	type="text/css" />
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<style type="text/css">
<!--
.STYLE1 {
	color: #27B3FE;
}
-->
</style>
<script type="text/javascript">
   $(function(){
	   var x = parent.tree.getLevel(parent.treeid);
	   var y = parent.subRule.rulesArray[x-1];
	   $("#subjectsNumbers").attr("maxlength", y);
	   var basePath="<%=basePath%>";
	   var treeid = window.parent.tree.getSelectedItemId();
	   var treename = window.parent.tree.getItemText(treeid);
	   $("#subjectsName").val(treename).attr("readonly","readonly");
	   $("#subjectsID").val(treeid);
	   $("#subjectsNumbers").val(window.parent.tree.getUserData(treeid,"subjectsNumbers"));
	   $("#currentLevel").val(parseInt(window.parent.tree.getUserData(treeid,"currentLevel"))-1);
	   $("#subjectsPID").val(window.parent.tree.getUserData(treeid,"subjectsPID"));
	   $("#startCash").focus(function(){
	   		if($("#sdirection").val()!="平"||$(this).val()=="0.00"){
	   			$(this).attr("value","");
	   		}
	   });
	   $("#startCash").blur(function(){
	   		if($("#sdirection").val()=="平"||$(this).val()==""){
	   			$(this).attr("value","0.00");
	   		}
	   });
	   function panduan(){
	        var b=true;
	        var startCash=$("#startCash").val();
	        if(startCash.match(/^\+{0,1}\d+(\.\d{1,2})?$/)==null){
	          alert("请输入正数！");
	          return false;
	        }
	              var url1=basePath+"/ea/csbjects/sajax_ea_ajaxDatess.jspa?subs.subjectsID="+treeid+"&date="+new Date().toLocaleString();
	               $.ajax({
	                        url: encodeURI(url1),
	                        type: "get",
	                        async: false,
	                        dataType: "json",
	                        success: function cbf(data){
				              var member = eval("(" + data + ")");
				              var nologin = member.nologin;
				              if(nologin){
				                  document.location.href =basePath+"page/ea/not_login.jsp";
									}
									var c = member.count;
									if (c != 0) {
										alert("该科目已设置期初余额！");
										b = false;
									}
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
					return b;
				}

				$("#save").click(function() {
					if(panduan()){
							$("#editcsbjectsForm").attr(
						"action",basePath+"/ea/csbjects/ea_saveSubs.jspa?pageNumber=${pageNumber}");
	                 	document.editcsbjectsForm.submit.click();
					}
	          });
          
   });
</script>
</head>
<body>
	<form name="editcsbjectsForm" id="editcsbjectsForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<div style="width:100%;top:20%;right:20%; border:#7F9DB9 solid 1px;">
			<div class="drag">科目设置:</div>
			<div class="unitlib_list_right02">
				<table width="100%" 　border="0" align="center" cellpadding="0"
					cellspacing="0">
					<input style="display: none;" id="parmiter" />
					<input style="display: none;" name="subs.subjectsID"
						id="subjectsID" />
					<input style="display: none;" name="subs.subjectsPID"
						id="subjectsPID" />
					<input style="display: none;" name="subs.subjectsNumbers"
						id="subjectsNumbers" />
					<input style="display: none;" name="subs.currentLevel"
						id="currentLevel" />
					<tr>
						<td width="39%" height="44" align="right" class="td_bg">科目名称：</td>
						<td width="61%" class="td_bg"><input id="subjectsName"
							type="text" size="30" name="subs.subjectsName"
							style="border: 0px" /></td>
					</tr>
					<tr>
						<td height="45" align="right" class="td_bg">期初余额方向：</td>
						<td height="45" class="td_bg"><select name="subs.sdirection"
							id="sdirection">
								<option value="借">借</option>
								<option value="贷">贷</option>
								<option value="平">平</option>
						</select>
						</td>
					</tr>
					<tr>
						<td height="45" align="right" class="td_bg">期初余额：</td>
						<td height="45" class="td_bg"><input name="subs.startCash"
							maxlength="50" type="text" class="input01" id="startCash"
							size="26" value="0.00" />
						</td>
					</tr>
				</table>
				<s:token />
			</div>
			<div align="center">
				<input type="button" id="save" class="input-button JQuerySubmit"
					value=" 保存 " /> <input type="button" class="input-button"
					onclick="javascript:window.history.go(-1);" name="button2"
					value="返回" />
			</div>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>