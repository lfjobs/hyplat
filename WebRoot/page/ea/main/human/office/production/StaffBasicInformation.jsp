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
<title>员工基本信息</title>
<link href="<%=basePath%>css/ea/human/admin_main.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
   var basePath= '<%=basePath%>';
   var pNumber='${pageNumber}';
   var treeid;
   var treename;
   var tree;
   var date;
</script>
</head>
<body>
<table width="800" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="26" align="right">编号：${basic[0][0]}</td>
    <td align="right">&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="6">1、员工基本信息</td>
    <td rowspan="8" height="220px" width="160px"><img src="<%=basePath%>${basic[0][8]}" width="160px" height="220" /></td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">姓 名 </td>
    <td height="26">${basic[0][1]}</td>
    <td width="100">曾用名</td>
    <td>${basic[0][2]}</td>
    <td width="100">出生日期</td>
    <td>${basic[0][3]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" >
    <td height="26">籍 贯</td>
    <td>${basic[0][4]}</td>
    <td>民 族</td>
    <td align="center">${basic[0][5]}</td>
    <td>身份证号码</td>
    <td>${basic[0][6]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">家庭住址</td>
    <td colspan="5">${basic[0][7]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">常住地址</td>
    <td colspan="5">${address[0]}</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="6">2、联系方式</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">家庭电话</td>
    <td colspan="2">${basic[0][9]}</td>
    <td width="100" align="center">移动电话</td>
    <td colspan="2">&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26">E-mail</td>
    <td colspan="2">${basic[0][10]}</td>
    <td align="center">QQ</td>
    <td colspan="2">${basic[0][11]}</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">3、学历子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">入学时间</td>
    <td>毕业时间</td>
    <td>毕业学校及单位</td>
    <td>所学专业</td>
    <td>学历</td>
    <td>学习形式</td>
    <td>教育类别</td>
    <td>证明人</td>
  </tr>
	<c:forEach var="objs" items="${listDegree}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">4、个人履历子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td width="100" height="26">起时间</td>
    <td width="100">止时间</td>
    <td>工作单位</td>
    <td width="60">职 务</td>
    <td width="200">工作内容</td>
    <td width="60">岗位名称</td>
    <td>证明人</td>
  </tr>
	<c:forEach var="objs" items="${listResume}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">5、家庭成员子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">姓名</td>
    <td>与本人关系</td>
    <td>出生日期</td>
    <td>工作单位</td>
    <td>职务</td>
    <td>住址</td>
    <td>电话号码</td>
    <td>备注</td>
  </tr>
	<c:forEach var="objs" items="${listFamily}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">6、身体状况子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">体检时间</td>
    <td>体检医院</td>
    <td>体检内容</td>
    <td>备注（体检表附后）</td>
    <td>审核人</td>
    <td>审核时间</td>
  </tr>
	<c:forEach var="objs" items="${listHealth}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">7、政治面貌子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">政治面貌</td>
    <td>入党时间</td>
    <td>所在单位</td>
    <td>介绍人</td>
    <td>转正时间</td>
    <td>党（团）龄</td>
    <td>审核人</td>
  </tr>
	<c:forEach var="objs" items="${listPolitical}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">8、奖励子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">奖励类别</td>
    <td>奖励名称</td>
    <td>奖励原因</td>
    <td>奖励时间</td>
    <td>奖励批准单位</td>
    <td>荣誉称号</td>
    <td>审核人</td>
  </tr>
	<c:forEach var="objs" items="${listEncourage}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">9、惩罚子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">惩罚类别</td>
    <td>惩罚名称</td>
    <td>惩罚原因</td>
    <td>惩罚时间</td>
    <td>受处分单位</td>
    <td>惩罚取消时间</td>
    <td>审核人</td>
  </tr>
  	<c:forEach var="objs" items="${listPunishment}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">10、调查子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">调查时间</td>
    <td>调查项目</td>
    <td>调查结论</td>
    <td>处理责任人</td>
    <td>处理意见</td>
    <td>处理时间</td>
    <td>审核人</td>
  </tr>
    	<c:forEach var="objs" items="${listInvestigation}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="9">11、人事档案管理子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">档案编号</td>
    <td>档案名称</td>
    <td>审核时间</td>
    <td>管理止时间</td>
    <td>档案盒编号</td>
    <td>档案责任人</td>
    <td>档案盒名称</td>
  </tr>
 	<c:forEach var="objs" items="${listPersonalFile}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="800" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#A8C7CE">
  <tr align="center" bgcolor="#FFFFFF" height="22" >
    <td height="26" colspan="10">12、证件及相关资料子集</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">证件及资料名称</td>
    <td>有效时间</td>
    <td>终止时间</td>
    <td>发证机关</td>
    <td>证件编号</td>
    <td>证件资料文号</td>
    <td>审核时间</td>
    <td>审核人编号</td>
    <td>审核人</td>
  </tr>
 <c:forEach var="objs" items="${listCertificate}">
						<tr id="${objs[0]}" align="center" bgcolor="#FAFAF1">
							<c:forEach items="${objs}" var="obj" >
							<td  height="26">
								<span>${obj}</span>
							</td>
							</c:forEach>
					</tr>
</c:forEach>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr align="center" bgcolor="#FAFAF1">
    <td height="26">&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
