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
	String star = request.getParameter("star");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>打印招聘有数据登记资料管理</title>
<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	.table{
		border:#cccccc 1px solid;
		cellpadding:0;
		cellspacing:0;
	}
	.td{
		border:#cccccc 1px solid;
	}
</style>
</head>
<body>
	<table width="800" border="0" align="center" >
		<tr>
			<td align="center" colspan="6">
				<h1><%=star%></h1></td>
		</tr>
		<tr>
			<td height="26" width="100" align="center">应聘部门</td>
			<td height="26" align="left">${listaud[0][0] }</td>
			<td height="26" width="100" align="center">应聘职位</td>
			<td height="26" align="left">${listaud[0][1] }</td>
			<td height="26" width="100" align="center">应聘日期</td>
			<td height="26" align="left">&nbsp;</td>
		</tr>
	</table>
	<table width="800" align="center" class="table" >
		<tr align="center" height="22">
			<td height="26" colspan="6">1、员工基本信息</td>
			<td rowspan="8" height="220px" width="160px">&nbsp;</td>
		</tr>
		<tr align="center" >
			<td width="100" height="26">姓 名</td>
			<td height="26">${listBasic[0][1]}</td>
			<td width="100">编号</td>
			<td>${listBasic[0][0]}</td>
			<td width="100">出生日期</td>
			<td>${listBasic[0][3]}</td>
		</tr>
		<tr align="center">
			<td height="26">籍 贯</td>
			<td>${listBasic[0][4]}</td>
			<td>民 族</td>
			<td align="center">${listBasic[0][5]}</td>
			<td>身份证号码</td>
			<td width="150">${listBasic[0][6]}</td>
		</tr>
		<tr align="center" height="22">
			<td height="26">家庭住址</td>
			<td colspan="5">${listBasic[0][7]}</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">常住地址</td>
			<td colspan="5">${address[0]}</td>
		</tr>
		<tr align="center" height="22">
			<td height="26" colspan="6">2、联系方式</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">家庭电话</td>
			<td colspan="2">${listBasic[0][9]}</td>
			<td width="100" align="center">移动电话</td>
			<td colspan="2">&nbsp;</td>
		</tr>
		<tr align="center"  height="22">
			<td height="26">E-mail</td>
			<td colspan="2">${listBasic[0][10]}</td>
			<td align="center">QQ</td>
			<td colspan="2">${listBasic[0][11]}</td>
		</tr>
	</table>
	<table width="800" align="center" class="table">
		<tr align="center" height="22">
			<td height="26" colspan="9">3、学历子集</td>
		</tr>
		<tr align="center">
			<td height="26">入学时间</td>
			<td>毕业时间</td>
			<td>毕业学校及单位</td>
			<td>所学专业</td>
			<td>学历</td>
			<td>学习形式</td>
			<td>教育类别</td>
			<td>证明人</td>
		</tr>
		<c:if test="${listDegree != '[]'}">
			<c:forEach var="objs" items="${listDegree}" begin="0" end ="3" >
				<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
					<c:forEach items="${objs}" var="obj" >
					<td  height="26">
						<span>${obj}</span>
					</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${listDegree == '[]'}">
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
	</table>
	<table width="800" class="table" align="center" >
		<tr align="center"  height="22">
			<td height="26" colspan="9">4、个人履历子集</td>
		</tr>
		<tr align="center">
			<td width="100" height="26">起时间</td>
			<td width="100">止时间</td>
			<td>工作单位</td>
			<td width="60">职 务</td>
			<td width="200">工作内容</td>
			<td width="60">岗位名称</td>
			<td>证明人</td>
		</tr>
		<c:if test="${listResume != '[]'}">
			<c:forEach var="objs" items="${listResume}" begin="0" end ="3" >
				<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
					<c:forEach items="${objs}" var="obj" >
					<td  height="26">
						<span>${obj}</span>
					</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${listResume == '[]'}">
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
	</table>
	<table width="800" align="center"class="table">
		<tr align="center"  height="22">
			<td height="26" colspan="9">5、家庭成员子集</td>
		</tr>
		<tr align="center">
			<td height="26">姓名</td>
			<td>与本人关系</td>
			<td>出生日期</td>
			<td>工作单位</td>
			<td>职务</td>
			<td>住址</td>
			<td>电话号码</td>
			<td>备注</td>
		</tr>
		<c:if test="${listFamily != '[]'}">
			<c:forEach var="objs" items="${listFamily}" begin="0" end ="3" >
				<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
					<c:forEach items="${objs}" var="obj" >
					<td  height="26">
						<span>${obj}</span>
					</td>
					</c:forEach>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${listResume == '[]'}">
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr align="center">
				<td height="26">&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</c:if>
		<tr align="center">
			<td height="26" colspan="2">个人需求</td>
			<td>待遇需求</td>
			<td>岗位需求</td>
			<td>工作地点需求</td>
			<td>工作时间需求</td>
			<td>能否加班</td>
			<td>能否出差</td>
		</tr>

		<tr align="center">
			<td height="26" colspan="2">&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table width="800"  align="center" class="table">
		<tr align="center"  height="22">
			<td height="35" align="left">董事长意见：</td>
			<td height="35" align="left">总经理意见：</td>
			<td height="35" align="left">人事主管意见：</td>
			<td height="35" align="left">部门主管意见：</td>
		</tr>
		<tr align="center"  height="22">
			<td height="35" align="left">总部董事长意见：</td>
			<td height="35" align="left">总部总裁意见：</td>
			<td height="35" align="left">总部人事主管意见：</td>
			<td height="35" align="left"">总部部门主管意见：</td>
		</tr>
	</table>
	<table width="800" border="0" align="center">
		<tr>
			<td>
				备注：			
			</td>
		</tr><tr>
			<td>
				1.填写字迹要工整，无涂改，用正楷填写。 2.填写时若遇内容较多时，请把字写小一点，不要跳格填写。 3.填写时要依据本人的身份证、学历等原件的内容认真填写。 4.员工档案中无复印件的请补充完善附后。
				5.审核人在收集资料时要按填报要求的复印件仔细审核，凡不符的要及时通知本人补充完善。
				6.审核人必须在复印件上签字:"复印件与原件相符"。 7.入职时必须填写岗位说明书并录入岗位变动子集。
				8.入职时有合同，安全责任书。 9.人事档案必须填写异动单（出入库单），按分价值100元-500元。
				10.家庭成员子集要求本人填写婚姻状况历程。 11.入职必须填写持有岗位入职变动审批流程文件。
			</td>
		</tr>
	</table>
</body>
</html>
