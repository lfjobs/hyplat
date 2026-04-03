<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>组织机构打印</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>js/jquery.js" type="text/javascript">
	
</script>
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size:10px;
}

body {
	margin-left: 15px;
}

#apDiv1 {
	position: absolute;
	left: 207px;
	top: 20px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>
</head>
<body>
	<div id="apDiv1">
		<table  width="620"  align="center" >
			<tr>
				<td  height="25" align="center"  style="font-weight: bold">部门岗位信息</td>
			</tr>
		</table>
		<table width="620"  id="" align="center" >
			<tr>
				<td align="center" class="td_bg">机构编号：</td>
				<td class="td_bg"><span>${organization.ocode }</span>
				</td>
				<td width="20%" align="center" class="td_bg">部门名称：</td>
				<td width="30%"><span>${organization.organizationName}</span></td>
			</tr>
			<tr>
				<td width="20%" align="center" class="td_bg">岗位编号：</td>
				<td width="30%" class="td_bg"><span>${organization.opostCode}</span>
				</td>

				<td width="20%" align="center" class="td_bg">岗位名称：</td>
				<td width="30%" id="td002"><span>${organization.opostName}</span>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center" class="td_bg">职务编号：</td>
				<td width="30%" class="td_bg"><span>${organization.odutiesID}</span>
				</td>

				<td width="20%" align="center" class="td_bg">职务名称：</td>
				<td width="30%" id="td002"><span>${organization.odutiesName}</span>
				</td>
				<tr>
				<td align="center" class="td_bg">所属上级机构：</td>
				<td class="td_bg"><span>${porganization.organizationName}</span>
				</td>

				<td align="center" class="td_bg">负责工作范围：</td>
				<td class="td_bg"><s:select list="%{#request.SInterfaceList}"
						 style="display: none;" listKey="interfaceUrl" id="interfaceUrl"
						listValue="interfaceName" name="organization.organizationUrl"
						theme="simple">
					</s:select> <span id="interface">${organization.organizationUrl }</span></td>
			</tr>
			</tr>
		</table>
		<!-- 机构责任人 -->
		<table width="620" cellpadding="0" cellspacing="0" class="table" id="agenciesList">
				<tr>
				<td  height="25" align="center"  style="font-weight: bold" colspan="10">机构责任人</td>
				</tr>
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						 负责人编号
					</th>
					<th align="center">
						岗位开始时间
					</th>
					<th align="center">
						岗位结束时间
					</th>
					<th align="center">
						负责人
					</th>
					<th align="center">
						负责人电话
					</th>
					<th align="center">
						公司电话
					</th>
					<th align="center">
						负责内容
					</th>
					<th align="center">
						备注
					</th>
				</tr>
				<s:iterator value="agenciesList" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 负责人编号 -->
						<td align="center" bgcolor="#FFFFFF">
							${agenciesCode}
						</td>
						<!-- 岗位开始时间 -->
						<td align="center" bgcolor="#FFFFFF">
							${statDate}
						</td>
						<!-- 岗位结束时间 -->
						<td align="center" bgcolor="#FFFFFF">
							${endDate}
						</td>
						<!-- 负责人 -->
						<td align="center" bgcolor="#FFFFFF">
							${agenciesName}
						</td>
						<!-- 负责人电话 -->
						<td align="center" bgcolor="#FFFFFF">
							${tep}
						</td>
						<!-- 公司电话 -->
						<td align="center" bgcolor="#FFFFFF">
							${octep}
						</td>
						<!-- 负责内容 -->
						<td align="center" bgcolor="#FFFFFF">
							${agenciesContent}
						</td>
						<!-- 备注-->
						<td align="center" bgcolor="#FFFFFF">
							${remarks}
						</td>
					</tr>
				</s:iterator>
		</table>
		<!-- 部门职责 -->
		<table width="620" cellpadding="0" cellspacing="0" class="table" id="orgdescList">
				<tr>
				<td  height="25" align="center"  style="font-weight: bold" colspan="8">部门机构职责</td>
				</tr>
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						日工作
					</th>
					<th align="center">
						周工作
					</th>
					<th align="center">
						月工作
					</th>
					<th align="center">
						季工作
					</th>
					<th align="center">
						年工作
					</th>
					<th align="center">
						本职工作
					</th>
					<th align="center">
						兼职工作
					</th>
				</tr>
				<s:iterator value="orgdescList" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 日工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${datework}
						</td>
						<!-- 周工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${weekwork}
						</td>
						<!-- 月工作-->
						<td align="center" bgcolor="#FFFFFF">
							${monthwork}
						</td>
						<!-- 季工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${seasonwork}
						</td>
						<!-- 年工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${yearwork}
						</td>
						<!-- 本职工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${jobwork}
						</td>
						<!-- 兼职工作 -->
						<td align="center" bgcolor="#FFFFFF">
							${twicework}
						</td>
					</tr>
				</s:iterator>		
		<!-- 职位设置 -->
		<table width="620" cellpadding="0" cellspacing="0" class="table" id="deppostlist">
				<tr>
				<td  height="25" align="center"  style="font-weight: bold" colspan="8">职位设置</td>
				</tr>
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						职务名称
					</th>
					<th align="center">
						职务编号
					</th>
					<th align="center">
						编员人数
					</th>
					<th align="center">
						岗位定员
					</th>
					<th align="center">
						岗位职责
					</th>
					<th align="center">
						岗位要求
					</th>
					<th align="center">
						备注
					</th>
				</tr>
				<s:iterator value="deppostlist" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 职务名称 -->
						<td align="center" bgcolor="#FFFFFF">
							${postName}
						</td>
						<!-- 岗位编号 -->
						<td align="center" bgcolor="#FFFFFF">
							${postNum}
						</td>
						<!-- 编员人数 -->
						<td align="center" bgcolor="#FFFFFF">
							${adminNum}
						</td>
						<!-- 岗位定员 -->
						<td align="center" bgcolor="#FFFFFF">
							${postSureNum}
						</td>
						<!-- 岗位职责 -->
						<td align="center" bgcolor="#FFFFFF">
							${postResponsibility}
						</td>
						<!-- 岗位要求 -->
						<td align="center" bgcolor="#FFFFFF">
							${responsibilityRequire}
						</td>
						<!-- 备注 -->
						<td align="center" bgcolor="#FFFFFF">
							${remark}
						</td>
					</tr>
				</s:iterator>
			<!-- 岗位人员	-->
		<table  width="620" cellpadding="0" cellspacing="0" class="table" id="staffDepList">
					<tr>
						<td  height="25" align="center"  style="font-weight: bold" colspan="13">岗位人员</td>	
					</tr>
					<tr>
						<th  align="center">
							序号
						</th>
						<th  align="center">
							人员编号
						</th>
						<th align="center">
							档案编号
						</th>
						<th align="center">
							员工工种
						</th>
						<th align="center">
							人员姓名
						</th>
						<th align="center">
							岗位名称
						</th>
						<th align="center">
							曾用名
						</th>
						<th align="center">
							性别
						</th>
						<th align="center">
							出生日期
						</th>
						<th align="center">
							国籍
						</th>
						<th align="center">
							籍贯
						</th>
						<th align="center">
							民族
						</th>
						<th  align="center">
							身份证
						</th>
					</tr>
					<%
						int number = 1;
					%>
					<s:iterator value="staffDepList" var="lists" >
						<tr id="${lists[0]}" class="${lists[4]}">
							<td align="center" bgcolor="#FFFFFF">
								<%=number %>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[1]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[2]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[4]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[5]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[6]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[7]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[8]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[9]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[10]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[11]}</span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span>${lists[12]}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
			</table>		
				
		<!-- 机构银行帐号 -->
		<table width="620" cellpadding="0" cellspacing="0" class="table"  id="institList">
				<tr>
				<td  height="25" align="center"  style="font-weight: bold" colspan="12">机构银行帐号</td>
				</tr>
				<tr>
					<th align="center">
						序号
					</th>
					<th align="center">
						帐号编号
					</th>
					<th align="center">
						银行开户登记证明号(审核号)
					</th>
					<th align="center">
						发证日期
					</th>
					<th align="center">
						账户性质(适用类型)
					</th>
					<th align="center">
						银行名称
					</th>
					<th align="center">
						开户帐号
					</th>
					<th align="center">
						开户时间
					</th>
					<th align="center">
						注销时间
					</th>
					<th align="center">
						联系方式
					</th>
					<th align="center">
						账户责任人
					</th>
					<th align="center">
						开户银行地址
					</th>
				</tr>
				<s:iterator value="institList" status="number">
					<tr>
						<td align="center" bgcolor="#FFFFFF">
							<s:property value="%{#number.index+1}"/>
						</td>
						<!-- 帐号编号 -->
						<td align="center" bgcolor="#FFFFFF">
							${accountNum}
						</td>
						<!-- 银行开户登记证明号(审核号) -->
						<td align="center" bgcolor="#FFFFFF">
							${bankRegistrationID}
						</td>
						<!-- 发证日期 -->
						<td align="center" bgcolor="#FFFFFF">
							${registrationDate}
						</td>
						<!-- 账户性质(适用类型) -->
						<td align="center" bgcolor="#FFFFFF">
							${accountNature}
						</td>
						<!-- 银行名称 -->
						<td align="center" bgcolor="#FFFFFF">
							${bankName}
						</td>
						<!-- 开户帐号 -->
						<td align="center" bgcolor="#FFFFFF">
							${bankAccount}
						</td>
						<!-- 开户时间 -->
						<td align="center" bgcolor="#FFFFFF">
							${openAccountDate}
						</td>
						<!-- 注销时间 -->
						<td align="center" bgcolor="#FFFFFF">
							${cancellationDate}
						</td>
						<!-- 联系方式 -->
						<td align="center" bgcolor="#FFFFFF">
							${contact}
						</td>
						<!-- 账户责任人 -->
						<td align="center" bgcolor="#FFFFFF">
							${conPerson}
						</td>
						<!-- 开户银行地址 -->
						<td align="center" bgcolor="#FFFFFF">
							${bankAddr}
						</td>
					</tr>
				</s:iterator>
			
		<table width="620" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td width="80" height="25" align="right">公司经理：</td>
				<td>&nbsp;</td>
				<td width="90" align="right">部门主管：</td>
				<td>&nbsp;</td>
				<td width="80" align="right">人事处：</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="80" height="25" align="right">总部总经理：</td>
				<td>&nbsp;</td>
				<td width="90" align="right">总部部门主管：</td>
				<td>&nbsp;</td>
				<td width="80" align="right">总部人事处：</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript">
		$("span#interface").text($("#interfaceUrl").find("option:selected").text());
		var deppostlist = '${deppostlist.size()}';
		var institList = '${institList.size()}';
		var agenciesList = '${agenciesList.size()}';
		var staffDepList = '${staffDepList}';
		var orgdescList = '${orgdescList.size()}';
		if(staffDepList=="[]"){
			$("table#staffDepList").hide();
		}
		if(orgdescList==0){
			$("table#orgdescList").hide();
		}
		if(deppostlist==0){
			$("table#deppostlist").hide();
		}
		if(institList==0){
			$("table#institList").hide();
		}
		if(agenciesList==0){
			$("table#agenciesList").hide();
		}
		
	</script>
</body>
</html>