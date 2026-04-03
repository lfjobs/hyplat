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
		<title>部门岗位入职</title>
		<style type="text/css">
.xx {
	color: #FF0000;
	margin-right: 2px;
}
</style>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js"type="text/javascript"></script>
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
			src="<%=basePath%>js/ea/human/office/production/orgPost_Entry.js"></script>
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
								岗位定员
							</th>
							<th width="80" align="center">
								专岗人数
							</th>
							<th width="200" align="center">
								岗位职责
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
						<c:forEach var='arr' items="${pageForm.list}">
							<tr id="${arr[0]}" class="td_bg01 saveAjax" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${arr[0]}" />
									<input name="depPostID" type="hidden" value="${arr[0]}" />
								</td>
								<td class="td_bg01">
									<%=number%>
								</td>
								<td class="td_bg01">
									<span id="postName">${arr[1]}</span>
									<input class="model1" value="${arr[1]}" name="postName" />
								</td>
								<td class="td_bg01">
									<span id="postNum">${arr[2] }</span>
									<input class="model1" value="${arr[2] }" name="postNum"
										id="postNum" readonly="readonly" />
								</td>
								<td class="td_bg01">
									<span id="adminNum">${arr[3]==null ? "0" : arr[3]}</span>
									<input class="model1 " value="${arr[3]==null ? " 0" :
										arr[3]}" name="adminNum" readonly="readonly" />
									人
								</td>
								<td class="td_bg01">
									<span id="postSureNum">${arr[4]==null ? "0" : arr[4]}</span>
									<input class="model1 isNaN" value="${arr[4]==null ? " 0" :
										arr[4]}" name="postSureNum" />
									人
								</td>
								<td class="td_bg01">
									<span id="SpecialpostNum">${arr[12]==null ? "0" : arr[12]}</span>
									<input class="model1 isNaN" value="${arr[12]==null ? "0" : 
									arr[12]}" name="SpecialpostNum" />
									人
								</td>
								<td class="td_bg01">
									<span id="postResponsibility">${arr[5]}</span>
									<input class="model1" value="${arr[5]}"
										name="postResponsibility" size="5" />
								</td>
								<td class="td_bg01">
									<span id="responsibilityRequire">${arr[6] }</span>
									<input class="model1" value="${arr[6] }"
										name="responsibilityRequire" size="5" />
									<input name="depPostKey" type="hidden" value="${arr[7]}" />
									<input name="leveloneOrgID" type="hidden" value="${arr[8]}" />
									<input name="companyID" type="hidden" value="${arr[10]}" />
									<input name="organizationID" type="hidden" value="${arr[11]}" />
								</td>
								<td class="td_bg01">
									<span id="remark">${arr[9]}</span>
									<input class="model1" value="${arr[9]}" name="remark" />
								</td>
							</tr>
							<%
								number++;
							%>
						</c:forEach>
					</tbody>
				</table>
				<c:import url="../../../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/departmentpost/ea_getOrgPostListByOrg.jspa?departmentPost.organizationID=${departmentPost.organizationID }&pageNumber=${pageNumber}&star=00">
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
						<td height="40" align="right">
							选择人员：
						</td>
						<td align="left">
							<input style="width: 100px" id="principal" readonly="readonly" />
							&nbsp;
							<a href="#" class="yincang" onclick="searchCoach();">选择</a>
							<input type="hidden" id="principalID" name="staffid" />
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							<input type="radio" name="cos.status" value="01" id="radio1" class="radio"/>专岗&nbsp;
							<input type="radio" name="cos.status" value="00" id="radio2" class="radio"  checked="checked"/>兼岗
						</td>
						<td align="left">
							<input style="width: 100px" id="postName" readonly="readonly"></input>
							<input type="hidden" id="postID" name="departmentPost.depPostID" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<div id="div1" style="display: none">
								<table>
									<tr>
										<td align="right">
											员工类别：
										</td>
										<td align="left">
											<select id="staffType" name="entity.staffCategoryID" 
												style="width: 135px;"></select>
										</td>
									</tr>
									<tr>
										<td align="right">
											职务级别：
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
									<tr>
										<td align="right">
											合同类型：
										</td>
										<td align="left">
											<select style="width: 135px" name="contracttype">
												<option value="01">
													劳动合同
												</option>
												<option value="02">
													实习协议
												</option>
												<option value="03">
													劳务协议
												</option>
											</select>
										</td>
									</tr>
									<tr>
										<td align="right">
											合同编号：
										</td>
										<td align="left">
											<input type="text" name="archiveTemp.contractCode" />
										</td>
										
									</tr>
									<tr>
										<td align="right">
											合同签订日期：
										</td>
										<td align="left">
											<input type="text" name="archiveTemp.contractSignDate"
												id="startDate"
												onfocus="date(this)"/>
										</td>
									</tr>
									
									<tr>
										<td align="right">
											合同起日期：
										</td>
										<td align="left">
											<input type="text" name="archiveTemp.startDate"
												id="startDate"
												onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
												readonly
												value="${fn:substring(archiveTemp.startDate, 0, 10)}"/>
										</td>
									</tr>
									<tr>
										<td align="right">
											合同止日期：
										</td>
										<td align="left">
											<input type="text" name="archiveTemp.endDate" id="endDate"
												onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
												readonly value="${fn:substring(archiveTemp.endDate, 0, 10)}" />
										</td>
									</tr>
									<tr>
										<td align="right">
											条码：
										</td>
										<td align="left">
											<input type="text"
												name="archiveTemp.barcode" id="barcodes" />
										</td>
									</tr>
									<tr>
										<td align="right">
											芯片号：
										</td>
										<td align="left">
											<input type="text"
												name="archiveTemp.chipid" id="chipidss"  readonly/>
											<input type="button" class="input-button" id="readchipid"
												value=" 读取 " />
										</td>
									</tr>
								</table>
							</div>
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
