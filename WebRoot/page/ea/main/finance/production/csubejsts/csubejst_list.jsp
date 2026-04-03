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
<title>会计科目管理</title>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/csubejst_list.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
</style>
<script type="text/javascript">
document.onkeydown = function(){
         if(event.keyCode==116) {
         event.keyCode=0;
         event.returnValue = false;
         }
};
document.oncontextmenu = function() {event.returnValue = false;};
</script>
<script type="text/javascript">
   var treeid;
   var treename;
   var notoken = 0;
   var basePath="<%=basePath%>";
   var treeID = "${csbjects.subjectsID}";
   var treePID = "${csbjects.subjectsPID}";
   var subjectsValue = "${csbjects.subjectsName}";
   var subjectsNumbers="${csbjects.subjectsNumbers}";
	if(treeID != ""){
	  if(window.parent.tree.getItemText(treeID) != '0'){
		   window.parent.tree.setItemText(treeID,subjectsValue);
		}
	  else if(treeID ){
			window.parent.tree.insertNewChild(treePID,treeID,subjectsValue,0,0,0,0);
			window.parent.tree.setUserData(treeID,"subjectsNumbers",subjectsNumbers);
		}
    }

</script>
</head>
<body>
	<form method="post" name="sortchildren">
		<%-- 不告诉你--%>
		<input id="oID" name="subjectsID" type="hidden" />
	</form>
	<form name="subjectsForm" id="subjectsForm" method="post">
		<input type="submit" style="display:none" name="submit" />
		<input name="sub" value="${session_value}" type="hidden" />
		<!-- 代替token-->
		<div>
			<table class="flexme11" width="600">
				<thead>
					<tr>
						<th width="129" align="center">科目序号</th>
						<th width="230" align="center">科目名称</th>
						<th width="80" align="center">借贷方向</th>
						<th width="80" align="center">科目类别</th>
						<th width="80" align="center">账号类型</th>
						<th width="80" align="center">状态</th>
						<th width="114" align="center">操作</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr ondblclick="alee('<s:property value="subjectsID"/>')">
							<td><s:property value="subjectsNumbers" />
							</td>
							<td><s:property value="subjectsName" /></td>
							<td>
								<s:if test='subjectsDirection =="D"'>借</s:if>
								<s:elseif test='subjectsDirection == "C"'>贷</s:elseif>
								<s:else><s:property value="subjectsDirection" /></s:else>
							</td>
							<td>
								<s:if test='subjectsCategory =="A"'>银行</s:if>
								<s:elseif test='subjectsCategory =="B"'>现金</s:elseif>
								<s:elseif test='subjectsCategory =="C"'>固定资产</s:elseif>
								<s:else>其他</s:else>
							</td>
							<td>
							<s:if test='subjectsAccounts=="Y"'>主账号</s:if>
							<s:elseif test='subjectsAccounts=="N"'>虚账号</s:elseif>
							<s:else></s:else>
							</td>
							<td>
								<s:if test="subjectsStatus == 00">系统预设</s:if>
								<s:if test="subjectsStatus == 01">用户预设</s:if>
							</td>
							<td><a href="#"
								onclick="toedit('<s:property value="subjectsID"/>')"><img
									src="<%=basePath%>images/ea/main/edit.gif" width="16"
									height="16" title="修改" border="0" />
							</a> <a href="#"
								onclick="deleteid('<s:property value="subjectsID"/>','<s:property value="subjectsStatus"/>')"><img
									src="<%=basePath%>images/ea/main/gtk-del.png" width="16"
									height="16" title="删除" border="0" />
							</a></td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/csbjects/ea_getCsubejstsListAll.jspa?pageNumber=${pageNumber}&subjectsID=${subjectsID}"></c:param>
			</c:import>
			<s:token />
		</div>
	</form>

	<%------------------------------------物品选择------------------------------------%>
	<form name="SubjectsForm" id="SubjectsForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
			id="goodsjqModel">
			<div class="content1" style="width: 100%; height: 400px;">
				<div class="contentbannb">
					<div class="drag">
						选择物品
						<div class="close"></div>
					</div>
				</div>
				<table width="99%" height="33" id="searchgood" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">物品编码或名称：</td>
						<td width="100"><input name="parameter" class="input"
							id="parameter" size="10" /></td>
						<td height="33"><input type="button" class="btn02"
							ID="searchGood" name="button7" value="查询" /> <input
							type="button" class="btn02" id="selectGood" name="button5"
							value="确定" /> <input type="button" class="btn02 xzwp"
							name="button" value="新增" /> <input type="button"
							class="btn02 JQueryreturns" name="button4" value="关闭" /> <input
							type="hidden" name="parms" id="parms" /></td>
						<td width="40"><a id="wpsy" title="0">上一页</a></td>
						<td width="40"><a id="wpxy" title="0">下一页</a></td>
						<td width="80"><a id="wpzy">共&nbsp;&nbsp; <span
								style="color: red" id="wpzycount"></span>&nbsp;&nbsp;页</a></td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="16%">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
									<td>
										<div id="SubjectsAadTree" class="text_tree"
											style="overflow: scroll; z-index: 99; height: 320px;"></div>
									</td>
								</tr>
							</table></td>
						<td width="83%" valign="top" align="left">
							<div id="body_02"
								style="margin-top: 2px; display: none; height: 320px; width: 100%; overflow: scroll;">
							</div></td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
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