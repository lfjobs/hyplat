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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- 月年奖励模块 -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>月年奖励模块</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />    
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>

		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var personvalue = "";
            var ppageNumber=${pageNumber};
			var comID = '';      
			var token = 0;	
			var rewardid = '';	
			var rewardids = '';	
			var search = '${search}';
			var  ott  = '${ott}';
		
			
        </script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/personal_department/reward.js"></script>
	</head>
	<body>
		<form name="rewardForm" id="rewardForm" method="post">
		<input type="submit" name="submit" style="display:none" />
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							<input type="checkbox" name="chkMsgId" id="chkMsgId" onclick="doCheck(this)" /> 选择 
						</th>
						<th width="200" align="center">
							单位
						</th>
						<th width="100" align="center">
							部门
						</th>
						<th width="100" align="center">
							职务
						</th>
						<th width="80" align="center">
							姓名
						</th>
						<th width="100" align="center">
							奖励时间
						</th>
						<th width="100" align="center">
							荣誉名称
						</th>
						<th width="100" align="center">
							奖励金额
						</th>
						<th width="100" align="center">
							单据状态
						</th>
						<th width="80" align="center">
							审核人
						</th>
						<th width="130" align="center">
							审核时间
						</th>
						<th width="80" align="center">
							审批人
						</th>
						<th width="130" align="center">
							审批时间
						</th>
						<th width="80" align="center">
							录入人
						</th>
						<th width="130" align="center">
							录入时间
						</th>
						<th width="200" align="center">
							备注
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" >
						<tr id="${rewardid}" >
							<td>
								<input type="checkbox" onclick="toChkSon(this); " id="chkMsgId23" name="chkMsgId23"  value="${rewardid}"/>
							</td>
							<td>
								<span id="companyname">${companyname}</span>
							</td>
							<td>
								<span id="orgname">${orgname}</span>
							</td>
							<td>
								<span id="deptname">${deptname}</span>
							</td>
							<td>
								<span id="staffname">${staffname}</span>
							</td>
							<td>
								<span id="rewtimes">${rewtimes}</span>
							</td>
							<td>
								<span id="codevalue">${codevalue}</span>
							</td>
							<td>
								<span id="money">${money}</span>
							</td>
							<td>
								<span id="">
									<c:if test="${status == '10'}">待送审</c:if>
									<c:if test="${status == '20'}">待督查审核</c:if>
									<c:if test="${status == '21'}">督查退回</c:if>
									<c:if test="${status == '30'}">待董事长审批</c:if>
									<c:if test="${status == '31'}">董事长退回</c:if>
									<c:if test="${status == '99'}">审批通过</c:if>
								</span>
							</td>
							<td>
								<span id="twoname">${twoname}</span>
							</td>
							<td>
								<span id="twotimes" >${twotimes}</span>
							</td>
							<td>
								<span id="threename">${threename}</span>
							</td>
							<td>
								<span id="threetimes">${threetimes}</span>
							</td>
							<td>
								<span id="onename">${onename}</span>
							</td>
							<td>
								<span id="onetimes">${onetimes}</span>
								<span style="display: none" id="rewardkey">${rewardkey}</span>
								<span style="display: none" id="rewardid">${rewardid}</span>
								<span style="display: none" id="rewstatus">${rewstatus}</span>
								<span style="display: none" id="rewtimes">${rewtimes}</span>
								<span style="display: none" id="oneormore">${oneormore}</span>
								<span style="display: none" id="status">${status}</span>
								<span style="display: none" id="manager">${manager}</span>
								<span style="display: none" id="president">${president}</span>
								<span style="display: none" id="deptcharge">${deptcharge}</span>
								<span style="display: none" id="deptcharge">${headcharge}</span>
								<span style="display: none" id="companyid">${companyid}</span>
								<span style="display: none" id="organizationid">${organizationid}</span>
								<span style="display: none" id="deptid">${deptid}</span>
								<span style="display: none" id="codeid">${codeid}</span>
								<span style="display: none" id="staffid">${staffid}</span>
								<span style="display: none" id="onestaffid">${onestaffid}</span>
								
							</td>
								<td>
								<span id="remarks">${remarks}</span>
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
					value="ea/reward/ea_getStaffReward.jspa?pageNumber=${pageNumber}&search=${search}&ott=${ott }">
				</c:param>
			</c:import>
		</div>
		</form>
		<form name="AddForm" id="AddForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow" style="width: 400px; right: 35%; top: 10%"
				id="jqModel">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					奖励管理
					<div class="close">
					</div>
				</div>
				<table width="399px"  border="0" align="center"
					cellpadding="0" cellspacing="0" id="addtable">
					<tr>
						<td width="103" height="27" align="right">
							奖励类别：
						</td>
						<td width="140">
							<input type="radio" name="reward.rewstatus" value="00" class="jllx" checked="checked"/>月度奖
							<input type="radio" name="reward.rewstatus" value="02" class="jllx"/>年度奖
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							奖励时间：
						</td>
						<td>
							<input name="reward.rewtimes" onfocus="date(this)" class="put3" id="rewtimes" size="20" />
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							奖励项目：
						</td>
						<td>
							<input type="radio" name="reward.oneormore" class="jl" id="gr" checked="checked" value="00"/>个人奖
							<input type="radio" name="reward.oneormore" class="jl" id="tt" value="01"/>团体奖
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							单位：
						</td>
						<td>
							<s:select list="comList" listKey="companyID" listValue="companyName" headerKey="" headerValue="请选择" name="reward.companyid" id="companyid"></s:select>
							<input type="hidden" name="reward.companyname" id="companyname"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							姓名：
						</td>
						<td>
							<input type="hidden" name="reward.staffid" id="staffid" calss="staffID"/>
							<input name="reward.staffname" id="staffname" size="20" readonly="readonly" class="staffName"/>&nbsp;&nbsp;
							<a href="#" onclick="searchCoach();" >选择</a>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							部门：
						</td>
						<td>
							<input type="hidden" name="reward.organizationid" id="organizationid"/>
							<input name="reward.orgname"  id="orgname" size="20" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							职务：
						</td>
						<td>
							<input type="hidden" name="reward.deptid" id="deptid"/>
							<input name="reward.deptname"  id="deptname" size="20" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							荣誉名称：
						</td>
						<td>
							<select name="reward.codeid" id="codeid" style="width:140px">
							</select>
							<input type="hidden" name="reward.codevalue" id="codevalue"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							奖励金额：
						</td>
						<td>
							<input name="reward.money" id="money" size="20" class="put3"/>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							备注：
						</td>
						<td>
							<input name="reward.remarks" id="remarks" size="20" />
							<input type="hidden" name="reward.rewardkey" id="rewardkey"/>
							<input type="hidden" name="reward.rewardid" id="rewardid"/>
							<input type="hidden" name="reward.status" id="status"/>
							<input type="hidden" name="reward.manager" id="manager"/>
							<input type="hidden" name="reward.president" id="president"/>
							<input type="hidden" name="reward.deptcharge" id="deptcharge"/>
							<input type="hidden" name="reward.headcharge" id="headcharge"/>
							<input type="hidden" name="reward.onename" id="onename"/>
							<input type="hidden" name="reward.onestaffid" id="onestaffid"/>
							<input type="hidden" name="reward.onetimes" id="onetimes"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="savedraft" value=" 保存为草稿 "
						style="cursor: hand" />
					<input type="button" class="input-button" id="submitaudit" value=" 提交并送审 "
						style="cursor: hand" />
					<input type="button" class="input-button" id="res" value=" 重置 "
						style="cursor: hand" />
				</div>
				<div align="center">
				</div>
			</div>
		</form>
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							单位：
						</td>
						<td>
							<s:select list="comList" listKey="companyID" listValue="companyName" headerKey="" headerValue="请选择" name="reward.companyid" id="companyid"></s:select>
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							人员姓名：
						</td>
						<td>
							<input name="reward.staffname" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>

	<!--搜索打印窗口 -->
		<form name="SearchOneForm" id="SearchOneForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelOneSearch">
				<div class="drag">
					查询打印信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							奖励时间：
						</td>
						<td>
							<input onclick="WdatePicker({dateFmt:'yyyy-MM'});"  id="rewtimes" style="width:200px"/>
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							奖励类别：
						</td>
						<td>
							<select  id="rewstatus" style="width:200px">
								<option value="">请选择</option>
								<option value="00">月度</option>
								<option value="02">年度</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							奖励项目：
						</td>
						<td>
							<select  id="oneormore" style="width:200px">
								<option value="">请选择</option>
								<option value="00">个人奖</option>
								<option value="01">团体奖</option>
							</select>
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							单据状态：
						</td>
						<td>
							<select  id="status" style="width:200px">
								<option value="">请选择</option>
								<option value="10">待送审</option>
								<option value="20">待督查审核</option>
								<option value="21">督查退回</option>
								<option value="30">待董事长审批</option>
								<option value="31">董事长退回</option>
								<option value="99">审批通过</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchOne"
						value=" 打印 " />
				</div>
			</div>
		</form>
<!--搜索汇总打印窗口 -->
		<form name="SearchMoreForm" id="SearchMoreForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 300px; right: 25%; top: 10%"
				id="jqModelMoreSearch">
				<div class="drag">
					汇总打印信息
					<div class="close">
					</div>
				</div>
				<table id="SearchMoreTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							奖励时间：
						</td>
						<td>
							<input onclick="WdatePicker({dateFmt:'yyyy'});"  id="rewtimes" style="width:150px"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchMore"
						value=" 打印 " />
				</div>
			</div>
		</form>

		<!-- 从当前部门的员工中选择责任人 -->
		<iframe name="hidden" width="100%" height="0"></iframe>
		<div id="jqmWindow2" class="jqmWindow" style="width: 65%; height: 380px; absolute; display: none; left: 15%; top: 5%; background: #eff">
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
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>