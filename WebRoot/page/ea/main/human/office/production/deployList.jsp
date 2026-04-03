<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<title>人员配备--微型企业版</title>
		<style type="text/css">
.xx {
	color: #FF0000;
	margin-right: 2px;
}
</style>
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
		<script
			src="<%=basePath%>js/ea/human/office/production/deployList.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var depPostID = '';
  		var search='${search}';
		var personurl = "";
		var notoken = 0;
		var pNumber = '${pageNumber}';
		var orgPostID='';
		var token=0;
		var guanxi='${guanxi}';
		var select =1;
		var org2 = parent.tree.getSelectedItemText();
		var orgname = org2 != "" ? org2 : parent.companyName; 
		var staffCategoryID;
		var orgId = parent.tree.getSelectedItemId();
		if(orgId == ""){
			orgId = parent.companyID; ;
		}
		</script>

	</head>
	<body>
		<form name="orgPostForm" id="orgPostForm" method="post">
			<div id="main_main">
			<input type="submit" name="submit" style="display: none" />
				<input type="hidden" id="thisdate" />
				<table class="JQueryflexme">
					<thead>
						<tr class="tablewith">
							<th width="30" align="center">
								选择
							</th>
							<th width="30" align="center">
								序号
							</th>
							<th width="200" align="center">
								公司名称
							</th>
							<th width="150" align="center">
								部门名称
							</th>
							<th width="100" align="center">
								职务名称
							</th>
							<th width="60" align="center">
								职务编号
							</th>
							<th width="60" align="center">
								编员人数
							</th>
							<th width="80" align="center">
								专岗人数
							</th>
							<th width="80" align="center">
								兼岗人数
							</th>
							<th width="120" align="center">
								任职要求
							</th>
							<th width="100" align="center">
								备注
							</th>
						</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody id="tbwid">
						 <s:iterator value="pageForm.list" status="number">
							<tr id="${depPostID}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${depPostID}" />
									<input name="depPostID" type="hidden" value="${depPostID}" />
								</td>
								<td class="td_bg01">
									<%=number%>
								</td>
								<td class="td_bg01">
									<span id="companyName">${companyName}</span>
								</td>
								<td class="td_bg01">
									<span id="orgName">${orgName}</span>
								</td>
								<td class="td_bg01">
									<span id="postName">${postName}</span>
								</td>
								<td class="td_bg01">
									<span id="postNum">${postNum }</span>
								</td>
								<td class="td_bg01">
									<span id="adminNum">${adminNum==null ? '0' : adminNum}</span>
									人
								</td>
								<td class="td_bg01">
									<span id="SpecialpostNum">${SpecialpostNum ==null ? '0' : SpecialpostNum}</span>
									人
								</td>
									<td class="td_bg01">
									<span id="omppostNum">${omppostNum ==null ? '0' : omppostNum}</span>
									人
								</td>
								<td class="td_bg01">
									<span id="responsibilityRequire">${responsibilityRequire }</span>
									<input id="organizationID" type="hidden" value="${organizationID}" />
									<input id="companyID" type="hidden" value="${companyID}" />
									<input id="depPostKey" type="hidden" value="${depPostKey}" />
									<input id="leveloneOrgID" type="hidden" value="${leveloneOrgID}" />
									
									
								</td>
								<td class="td_bg01">
									<span id="remark">${remark}</span>
								</td>
							</tr>
							<%
								number++;
							%>
						</s:iterator>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/departmentpost/ea_getDeployList.jspa?departmentPost.organizationID=${departmentPost.organizationID }&pageNumber=${pageNumber}&star=00">
					</c:param>
				</c:import>				
			</div>
		</form>
		<iframe src="" name="main" scrolling="no" style="width:100%;height:0;"
						marginwidth="0" marginheight="0" frameborder="0"
						id="mainframe" border="0" framespacing="0" noresize="noResize"
						vspale="0"> </iframe> 
		<!-- 查询信息 -->
		<form name="orgPostSearchForm" id="orgPostSearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="orgPostSearchTable">

					<tr>
						<td height="40">
							职务名称：
						</td>
						<td>
							<input name="departmentPost.postName" />
						</td>
					</tr>
					<tr>
						<td height="40">
							职务编号：
						</td>
						<td>
							<input name="departmentPost.postNum" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>

		<!-- 人员分配 -->
		<form name="orgEntryForm" id="orgEntryForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" id="start" />
			<input type="hidden" id="end" />
			<div class="jqmWindow" style="width: 400px; right: 35%; top: 10%"
				id="jqModelEntry">
				<div class="drag">
					人员分配
					<div class="close">
					</div>
				</div>
				<table id="orgEntryTable"
					style="align: center; width: 100%; border: 0; text-align: left; padding: 0; margin: 0">

					<tr>
						<td align="right">
							<span style="color:red">*</span>选择人员：
						</td>
						<td align="left">
							<input style="width: 100px" id="principal" readonly="readonly" />
							&nbsp;
							<a href="#" class="yincang" onclick="searchCoach();">选择</a>
							<input type="hidden" id="principalID" name="staffid" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<span style="color:red">*</span>岗位类型：
						</td>
						<td align="left">
							<input type="radio" name="cos.status" value="01" id="radio1" class="radio"/>专岗&nbsp;
							<input type="radio" name="cos.status" value="00" id="radio2" class="radio"  checked="checked"/>兼岗
							<input style="width: 100px" id="postName" readonly="readonly"></input>
							<input type="hidden" id="postID" name="departmentPost.depPostID" />
							<input type="hidden" id="orgID" name="departmentPost.organizationID" />
						</td>
						
					</tr>
					<tr id="lb" style="display: none">
						<td align="right">
							<span style="color:red">*</span>员工类别：
						</td>
						<td align="left">
							<select id="staffType" name="entity.staffCategoryID" 
								style="width: 135px;"></select>
						</td>
					</tr>
					<tr id="jb" style="display: none">
						<td align="right">
							<span style="color:red">*</span>职务级别：
						</td>
						<td align="left">
							<select class="PayScale" style="width: 135px;"
								name="csp.payScaleID" id="payScaleID">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toCommit"
						value=" 确定 " />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="input-button" id="toReset" value=" 重置 " />
					<input name="search" type="hidden" value="search" />
					<iframe width="1" height="1" name="loadcab" id="loadcab"></iframe>
				</div>
			</div>
		</form>

		<!-- 从当前部门的员工中选择责任人 -->
		<iframe name="hidden" width="100%" height="0"></iframe>
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 380px; absolute; display: none; left: 1%; top: 5%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="340px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 30 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			$("#mainframe").css({"height": _height / 2 - 30 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>  
	</body>
</html>
