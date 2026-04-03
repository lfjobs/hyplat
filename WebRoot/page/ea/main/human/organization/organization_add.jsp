<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>组织机构</title>
		<!-- 后台管理中  组织机构添加-->
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"
			type="text/css" />
		<!--tree-->
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		<!-- jqmodal -->
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>


		<script type="text/javascript">
$(function(){
	$("#tosave").click(function(){
		$(".put3").trigger("blur");
        if ($("form .error").length) {
            return;
        }
        var opid = '${organization.organizationPID}';//被修改对象的上级id
        var newopid = $('#PID').val();//新的上级id
        var oid = '${organization.organizationID}';//本身id
        if (opid != "" && opid != newopid) {
            window.parent.tree.moveItem(oid, 'item_child', newopid);
        }
         $("#organizationform").attr("action","<%=basePath%>ea/organization/t_ea_saveOrganization.jspa?organizationID=" + parent.treeid);
		document.organizationform.submit.click();
	})
	$("#goback").click(function(){
		window.location.href = "<%=basePath%>ea/organization/ea_getOrganizationListAll.jspa?pageNumber=${pageNumber}&organizationID=" + parent.treeid;
	})
   
})



</script>
</head>
	<body>
		<form name="organizationform" id="organizationform" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input name="organization.organizationKey" type="hidden"
				value="${organization.organizationKey}" />
			<input name="organization.organizationCreateDate" type="hidden"
				value="${organization.organizationCreateDate }" />
			<input name="organization.organizationID" type="hidden"
				id="organizationID" value="${ organization.organizationID}" />
			<input type="hidden" name="organization.organizationPID" id="PID"
				value="${ organization.organizationPID}" />
			<table width="100%" height="68" border="0" align="center"
				cellpadding="0" cellspacing="2">
				<tr>
					<td align="left" valign="top">
						<div class="right">
							<div class="qh_gg_nav">
								&nbsp;添加/修改
							</div>
							<table width="100%" 　border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td width="20%" height="30" align="right" class="td_bg">
										机构序号：
									</td>
									<td class="td_bg">
										<input name="organization.organizationNumber" class="input01"
											type="text" size="24"
											value="${organization.organizationNumber}" maxlength="3" />
									</td>
								</tr>
								<tr>
									<td width="12%" height="30" align="right" class="td_bg">
										下属机构编号：
									</td>
									<td  class="td_bg">
										<input name="organization.ocode" type="text" maxlength="50"
											class="input01" id="ocode" size="24"
											value="${organization.ocode }" contentEditable="false" readyonly="readyonly"/>
										<span><font color="#0000FF">自动生成</font>
										</span>
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										部门名称：
									</td>
									<td  class="td_bg">
										<input name="organization.organizationName" type="text"
											maxlength="50" class="input01 put3 ckTextLength"
											id="organizationName" size="24"
											value="${organization.organizationName }" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										机构负责人：
									</td>
									<td class="td_bg">
										<input name="organization.organizationManager" type="text"
											maxlength="50" class="input01 ckTextLength" id="Manager"
											size="24" value="${organization.organizationManager }" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										负责内容：
									</td>
									<td colspan="2 class="td_bg">
										<s:select list="%{#request.SInterfaceList}"
											listKey="interfaceUrl" id="interfaceUrl"
											listValue="interfaceName" name="organization.organizationUrl"
											theme="simple">
										</s:select>
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										所属上级机构：
									</td>
									<td  class="td_bg" id="upper">
										<input type="text" id="oraganizationName"
											name="organization.oraganizationName" readonly="readonly"
											value="${porganization.organizationName }" /><a href="#" onclick="javascript:getPID()"><img
												src="<%=basePath%>images/up.jpg" style="border: 0;" /></a>
										<%--<select id="organizationPID"  name="organization.organizationPID" listKey="organizationID"  listValue="organizationName" theme="simple"></select>
                      --%>
									</td>
								</tr>
								<!-- 
								<tr>
									<td  height="30" align="right" class="td_bg">
										岗位编号：
									</td>
									<td  class="td_bg">
										<input name="organization.opostCode" type="text"
											maxlength="50" class="input01 put3" id="opostCode" size="24"
											value="${organization.opostCode }" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										岗位名称：
									</td>
									<td  class="td_bg">
										<input name="organization.opostName" type="text"
											maxlength="50" class="input01 put3 ckTextLength"
											id="opostName" size="24" value="${organization.opostName }" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										职位名称：
									</td>
									<td  class="td_bg">
										<input name="organization.odutiesName" type="text"
											maxlength="50" class="input01 put3 ckTextLength"
											id="odutiesName" size="24"
											value="${organization.odutiesName }" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right" class="td_bg">
										岗位要求：
									</td>
									<td  class="td_bg">
										<input name="organization.opostRequirements" type="text"
											maxlength="50" class="input01 put3 ckTextLength"
											id="opostRequirements" size="24"
											value="${organization.opostRequirements}" />
									</td>
								</tr>
								<tr>
									<td  height="30" align="right"
										class="td_bg">
										工作地点：
									</td>
									<td class="td_bg">
										<input name="organization.ojobLocation" type="text"
											maxlength="50" class="input01 put3" id="ojobLocation"
											size="24" value="${organization.ojobLocation}" />
									</td>
								</tr>
								
								 -->
								<tr>
									<td height="30" align="right" class="td_bg">
										机构职责：
									</td>
									<td class="td_bg">
										<textarea style="height: 100px; width: 500px"
											class="input01 ckTextLength" id="desc" maxlength="250"
											name="organization.organizationDesc">${organization.organizationDesc }</textarea>
									</td>
								</tr>
								<tr>
									<td  height="30" align="center" class="td_bg">&nbsp;
										
									</td>
									<td height="39" align="left" class="td_bg">
                                    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" class="ACT_btn" id="tosave" value=" 保存 " />
                                        &nbsp;&nbsp;&nbsp;&nbsp;
										<input type="button" class="ACT_btn" id="goback" value=" 取消 " />
								</tr>
							</table>
							<s:token></s:token>
						</div>
					</td>
				</tr>
			</table>
		</form>
		<div
			style="display: none; width: 210px; height: 300px; position: absolute; top: 41%; left: 20%; z-index: 4; background-color: #e1ecfc; filter: Alpha(opacity = 100);"
			id="jqModel"></div>
		<input type="text" style="display: none;" id="treeid" />
		<input type="text" style="display: none;" id="parentid" />
		<input type="text" style="display: none;" id="treename" />
		<input type="text" style="display: none;" id="parentname" />
		<input type="text" style="display: none;" id="unitsID" />

	</body>
	<script>
$(".jqmWindow").jqm({
       modal: true,// 限制输入（鼠标点击，按键）的对话  
       overlay: 20 // 遮罩程度%  
   }).jqmAddClose('.close')// 添加触发关闭的selector  
//		.jqDrag('.drag');// 添加拖拽的selector
	var basePath="<%=basePath%>";
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
	var outme =$("#organizationName","form#organizationform").attr("value");
	tree = new dhtmlXTreeObject("jqModel", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	tree.insertNewChild("0", companyID, companyName, 0, 0, 0, 0);
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
    		$("img","#upper").attr("src","<%=basePath%>images/down.jpg");
			$("#jqModel").show();
		}else{
			$("img","#upper").attr("src","<%=basePath%>images/up.jpg");
			$("#jqModel").hide();
		}
}
function getDatas(){
	treeid = tree.getSelectedItemId();
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
						if( outme == organizationList[i].organizationName && outme !=""){
							continue;	
						}
						
						tree.insertNewChild(treeid,
								organizationList[i].organizationID,
								organizationList[i].organizationName,
								0, 0, 0, 0);

					}
			},
			error: function cbf(data){
			alert("数据获取失败！")
			}
		});
}
</script>
</html>
