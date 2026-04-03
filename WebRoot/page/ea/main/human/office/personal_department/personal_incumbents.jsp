<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- 部门正式人员 -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人员列表</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/human/staff_info.js">
		</script>
		<script src="<%=basePath%>js/ea/human/cstaff.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
			
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>

		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var personvalue = "";
            var staffName = "";
            var ppageNumber = '${pageNumber}';
            var search = false;
            var psearch='${search}';
            var notoken = 0;
            var long = 1;
      		var ogID = '<%=request.getParameter("ogID")%>';
      		var ogName = '<%=request.getParameter("ogName")%>';
      		var comID = '${account.companyID}';
      		var heigh = '';
      		
	        document.onkeydown = function(evt){//捕捉回车   
	   			 evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
	   			 var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
	   			 if (key == 13&&long == 1&&search==true) { //判断是否是回车事件。
	   			  $("input#searchStaff").trigger("click");
	   				  search = false;
	   			 }
			};
        </script>

		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/personal_department/personal_incumbents.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							员工工种
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="100" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
					</tr>
				</thead>
				<tbody id="trwid">
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="lists">
						<tr id="${lists[0]}" class="${lists[4]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue trclass"
									value="${lists[0]}" />
							</td>
							<td>
								<span id="staffCode">${lists[1]}</span>
							</td>
							<td>
								<span id="recordCode">${lists[2]}</span>
							</td>
							<td>
								<span id="categoryname">${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td>
								<span id="staffName">${lists[4]}</span>
							</td>
							<td>
								<span id="postName">${lists[5]}</span>
							</td>
							<td>
								<span id="usedNmae">${lists[6]}</span>
							</td>
							<td>
								<span id="sex">${lists[7]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${lists[8]}</span>
							</td>
							<td>
								<span id="nationality">${lists[9]}</span>
							</td>
							<td>
								<span id="nativePlace">${lists[10]}</span>
							</td>
							<td>
								<span id="nation">${lists[11]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${lists[12]}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${lists[24]}</span>
								<span style="display: none" id="staffID">${lists[0]}</span>
								<span style="display: none" id="photo">${lists[26]}</span>
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
					value="ea/cosincumbent/ea_getStaffList.jspa?ogID=${param.ogID}&ogName=${param.ogName}&pageNumber=${pageNumber}&search=${search}&searchValue=${searchValue}">
				</c:param>
			</c:import>
		</div>
		
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 35%; top: 0%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable" border="0">
					<tr>
						<td style="width: 100px">
							查询条件
						</td>
						<td></td>
					</tr>
					<tr>
						<td align="right">
							人员编号：
						</td>
						<td>
							<input name="searchCStaff.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="searchCStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证：
						</td>
						<td>
							<input name="searchCStaff.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " style="margin-top: 0px;margin-left: 60px;"/>
					<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		<!-- 人员分配 -->
		<form name="orgEntryForm" id="orgEntryForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 35%; top: 10%"
				id="jqModelEntry">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" id="start" />
				<input type="hidden" id="end" />
				<div class="drag">
					人员分配
					<div class="close">
					</div>
				</div>
				<table id="orgEntryTable"
					style="align: center; width: 100%; border: 0; text-align: left; padding: 0; margin: 0">
					<tr>
						<td align="right" style="heught:40px">
							<input type="radio" name="cos.status" value="01" id="radio1" class="radio"/>专岗
							<input type="radio" name="cos.status" value="00" id="radio2" class="radio" checked="checked"/>兼岗
						</td>
						<td align="left">
							<select id="postID" name="departmentPost.depPostID" style="width: 135px;"></select>
						</td>
					</tr>
					<tr>
						<td align="right">
							选择人员：
						</td>
						<td align="left">
							<input style="width: 100px" id="principal" readonly="readonly" />
							<a href="#" class="yincang" id="searchCoach">选择</a>
							<input type="hidden" id="principalID" name="staffid" />
						</td>
					</tr>
					
					<tr class="div1" style="display: none;">
						<td align="right">
							员工类别：
						</td>
						<td align="left">
							<select id="staffType" name="entity.categoryName"
								style="width: 135px;"></select>
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							职务级别：
						</td>
						<td align="left" id="upper">
							<select class="PayScale" style="width: 200px;"
								name="csp.payScaleID" id="payScaleID">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							合同类型：
						</td>
						<td align="left">
							<select style="width: 135px" name="contracttype" id="contracttype">
								<option value="01">
									劳动合同
								</option>
								<option value="02">
									实习合同
								</option>
								<option value="03">
									劳务合同
								</option>
							</select>
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							合同编号：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.contractCode" id="contractCode"/>
						</td>
						
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							合同签订日期：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.contractSignDate"
								id="startDate" class="contractSignDate"
								onfocus="date(this)"/>
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							合同起日期：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.startDate"
								id="startDate" class="startDate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly = "readonly"/>
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							合同止日期：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.endDate" id="endDate"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly = "readonly" />
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							条码：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.barcode" id="barcodes" />
						</td>
					</tr>
					<tr class="div1" style="display: none;">
						<td align="right">
							芯片号：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.chipid" id="chipidss"/>
							<input type="button" class="input-button" id="readchipid"
								value="读取" />
						</td>
					</tr>
				</table>
				<div style="text-align: center;">
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
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 310px; absolute; display: none; left: 0; top: 0; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="270px"
				frameborder="0"></iframe>
			<div style="text-align: center;">
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		</div>
	</body>
</html>