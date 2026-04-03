<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@page import="hy.ea.bo.Company"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>添加/编辑项目</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company) session.getAttribute("currentcompany");
		%>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<%--树形机构--%>
		<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
		<%----------%>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/mysl/ProjectManage_add.js"></script>
			
<script type="text/javascript">
  var basePath = "<%=basePath%>";
  var treeid = "<%=c.getCompanyID()%>";
  var companyName = "<%=c.getCompanyName()%>";
  var result='';
  var proid="${project.proid}";
  var notoken = 0;
  var token = 0;
  var pNumber="${pageNumber}";
  var code1="${procode1}";
  var code2="${procode2}";
  var type="${type}";
</script>
	</head>
	<body>
		<form name="projectAddForm" id="projectAddForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="content1" style="width:100%;height:1000px;">
				<div class="contentbannb">
					<div class="divtx">
					<s:if test='type=="add"'>添加项目</s:if><s:elseif test='type=="edit"'>修改项目</s:elseif>
					</div>
				</div>
				<table width="99%" border="0" id="table" align="center"
					cellpadding="0" cellspacing="1" style="background: #FFFFFF;">
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>项目编号：
						</td>
						<td>
						     <input type="hidden" name="project.prokey"  id="prokey" value="${project.prokey}" />
						     <input type="hidden" name="project.proid"  id="proid" value="${project.proid}" />
						     <input type="hidden" name="project.factfinishdate"  id="factfinishdate" value="${fn:substring(project.factfinishdate,0,10)}" />
						     <input type="hidden" name="addtime"  id="addtime" value="${fn:substring(project.addDate,0,19)}" />
						     
						     <s:if test='type=="add"'>
						     <input type="text"   class="input put3" style="width:40px;height:80%;" id="code1"  onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})"/>
						     <span>-</span>
						     <input type="text"  class="input put3 ckTextLength isNaN" maxlength="10" style="width:80px;height:80%;" id="code2"/>
						     <a href="#" class="editor" id="editor">重置</a>
						     </s:if>
						     <s:elseif test='type=="edit"'>
						     <span>${project.procode}</span>
						     </s:elseif>
						     <input type="hidden" name="project.procode"  id="procode" value="${project.procode}" />
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>项目名称：
						</td>
						<td>
						     <input  name="project.proname" class="input put3 ckTextLength" maxlength="40" style="width:200px;height:80%;" id="proname" value="${project.proname}" />
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>往来单位：
						</td>
						<td>
						     <input name="project.contactcompany" class="input put3 ckTextLength" maxlength="40" style="width:200px;height:80%;" id="contactcompany" value="${project.contactcompany}" />
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>启动时间：
						</td>
						<td>
						     <input name="project.startdate" id="startdate" size="20" style="width:160px;height:80%;" class="input put3" onfocus="date(this)" value="${fn:substring(project.startdate,0,10)}"/>
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>计划完成时间：
						</td>
						<td>
						    <input name="project.planfinishdate" id="planfinishdate" size="20" style="width:160px;height:80%;" class="input put3" onfocus="date(this)" value="${fn:substring(project.planfinishdate,0,10)}"/>
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>负责人：
						</td>
						<td>
						    <input type="button" class="btn001 JQueryBoss" onclick="selectPeople('manager');" name="buttonboss"
								value="负责人" />
							  <s:if test='type=="add"'>
							  <span id="smp1" name="manager"></span>
							  </s:if>
							  <s:elseif test='type=="edit"'>
							  <span id="smp1" name="manager">
								   <s:iterator value="stafflist">
								   <s:if test="identity ==00">
			                       ${staffname};
			                       </s:if>
			                       </s:iterator>
                              </span>
                              <s:iterator value="stafflist">
                              <s:if test="identity ==00">	
                              <span name="smpid1" style="display:none;">${staffid}-${organizationID}-,</span>						
							  </s:if> 
							  </s:iterator>
							  </s:elseif>
							<span id="managererror"></span>
							<input type="hidden" name="manager" id="smpid1" value=""/>
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right">
							<span style="color: #FF0000;">*</span>项目成员：
						</td>
						<td>
						    <input type="button" class="btn001 JQueryStaff" onclick="selectPeople('member');" name="buttonstaff"
								value="成员 " />
							  <s:if test='type=="add"'>
							  <span id="smp2" name="member"></span>
							  </s:if>
							  <s:elseif test='type=="edit"'>
								<span id="smp2" name="member">
									<s:iterator value="stafflist">
								    <s:if test="identity ==01">
			                        ${staffname};
			                        </s:if>
			                        </s:iterator>
								</span>
								<s:iterator value="stafflist">
								<s:if test="identity ==01">
							    <span name="smpid2" style="display:none;">${staffid}-${organizationID}-,</span>					
							    </s:if>
							    </s:iterator>
							  </s:elseif>
							<span id="membererror"></span>
							<input type="hidden" name="member" id="smpid2" value=""/>
						</td>
					</tr>
					<tr>
						<td height="30" width="260" align="right" valign="top">
							<span style="color: #FF0000;">*</span>项目简介：
						</td>
						<td>
							<textarea rows="0" cols="0" class="input put3 ckTextLength" maxlength="3000" name="project.probrief"  id="probrief" style="width:500px;height:150px;overflow-y:auto;font-size:14px; color:black;">${project.probrief}</textarea>
						</td>
					</tr>
					<tr>
					    <td height="30" width="260"></td>
						<td height="30">
							<input type="button" class="btn001 JQuerySubmit" name="button1"
								value="  保存 " />&nbsp;&nbsp;&nbsp;
							<input type="button" class="btn001 JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
		</form>
         <!--选择人员窗口 -->
         <div class="jqmWindow" id="zj"
			style="width: 600px; height: 450px; left: 40%; top: 5%">
			<div>
				<div class="contentbannb">
					<div class="drag">
						组织机构树
					</div>
				</div>
			</div>
			<table style="width: 100%; height: 450px;" cellpadding="0"
				cellspacing="0" style="margin-top: 2px;">
				<tr>
					<td width="30%" align="left" valign="top">
						<div id="tree1"></div>
					</td>
					<td width="70%" align="left" valign="top">
						<table style="width: 450px; height: 350px;" align="center"
							cellpadding="0" cellspacing="2"
							style="margin-top: 5px; margin-bottom: 5px;">
							<tr>
								<td width="200" height="20" class="txt01">
									备选人员
								</td>
								<td width="50">
									&nbsp;
								</td>
								<td width="200" align="left" class="txt01">
									已选人员
								</td>
							</tr>
							<tr>
								<td height="137">
									<select name="leftfields" multiple="multiple" id="leftfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>

								</td>
								<td width="250" align="center">
									<div>
										<input type="button" class="input-button" id="query_add"
											value=" 添加 " />
									</div>
									<div>
										<input type="button" class="input-button" id="query_delete"
											value=" 删除 " />
									</div>
								</td>
								<td>
									<select name="rightfields" multiple="multiple" id="rightfields"
										style="height: 300px; width: 150px; font-size: 9pt">
									</select>
								</td>
								<td width="100">
									&nbsp;
								</td>
							</tr>


							<tr>
								<td height="30" colspan="3" align="center">
									<br />
									<input type="button" class="input-button" id="confirm"
										value=" 确定 " onclick="submit();"/>
									<input type="button" class="input-button" id="closed"
										value=" 关闭 " onclick="closed();" />
									<a href="#" id="ttttt" target="_self"></a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<iframe name="main" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>

