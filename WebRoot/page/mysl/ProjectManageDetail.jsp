<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<%@page import="hy.ea.bo.Company"%>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>项目详情/进度</title>
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
        <script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<style type="text/css">
#apDiv1 {
	position: absolute;
	left: 750px;
	top: 387px;
	width: 63px;
	height: 32px;
	z-index: 1;
}
</style>


<script type="text/javascript">
  var basePath = "<%=basePath%>";
</script>
	</head>
	<body>
			<div class="content1" style="width:100%;height:1000px;background-color:white;">
				<div class="contentbannb">
					<div class="divtx">
					</div>
				</div>
				<table width="99%" border="0" id="table" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td height="30" width="100" align="right">
							项目编号：
						</td>
						<td>&nbsp;&nbsp; 
						<span>${project.procode}</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							项目名称：
						</td>
						<td>&nbsp;&nbsp; 
						   <span style="font-size:14px; color:red;">${project.proname}</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							发起单位：
						</td>
						<td>&nbsp;&nbsp; 
						   <span>${project.contactcompany}</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							启动时间：
						</td>
						<td>&nbsp;&nbsp; 
						   <span>${fn:substring(project.startdate,0,10)}</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							完成时间：
						</td>
						<td>&nbsp;&nbsp; 
						   <span>${fn:substring(project.planfinishdate,0,10)}</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							负责人：
						</td>
						<td>&nbsp;&nbsp; 
						   <span>
						    <s:iterator value="stafflist">
								<s:if test="identity ==00">
			                    ${staffname};
			                    </s:if>
			                </s:iterator>
						   </span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right">
							项目成员：
						</td>
						<td>&nbsp;&nbsp; 
							<span>
							<s:iterator value="stafflist">
								 <s:if test="identity ==01">
			                     ${staffname};
			                     </s:if>
			                </s:iterator>
							</span>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right" valign="top">
							项目进度：
						</td>
						<td>
						    <% 
						    int htzdnum = 0;//合同制定
						    int scjhnum = 0;//生产计划通知书
						    int scrwnum = 0;//生产任务通知书
						    int sjdgnum = 0;//设计大纲
						    int htrwnum = 0;//绘图任务
						    
						    int scsjnum = 0;//生产设计
						    %>
						    <s:iterator value="tasklist">
			                     <s:if test="tasktype == 'htzd'">
                                 <%htzdnum++;%>
			                     </s:if>
			                     <s:if test="tasktype == 'scsj'">
                                 <%scjhnum++;%>
			                     </s:if>
			                     <s:if test="tasktype == 'scrw'">
                                 <%scrwnum++;%>
			                     </s:if>
			                     <s:if test="tasktype == 'sjdg'">
                                 <%sjdgnum++;%>
			                     </s:if>
			                     <s:if test="tasktype == 'htrw'">
                                 <%htrwnum++;%>
			                     </s:if>
			                </s:iterator>
			                <% 
						    int total=htzdnum+scjhnum+scrwnum+sjdgnum+htrwnum;
						    %>
						<table>
						      <tr>
						        <td>任务下达阶段</td>
						        <td>&nbsp;&nbsp;合同制定（<%=htzdnum%>）  生产计划通知书（<%=scsjnum%>）生产任务通知书（<%=scrwnum%>）  设计大纲（<%=sjdgnum%>）  绘图任务（<%=htrwnum%>） 共下发任务（<%=total%>）</td>
						      </tr>
						      <tr>
						        <td>生产设计阶段</td>
						        <td>
						        <s:iterator value="gresslist">
			                     &nbsp;&nbsp;${staffname}（${scsjnum}/${tasknumbyone}）
			                    </s:iterator>
						        </td>
						      </tr>
						      <tr>
						        <td>设计完成阶段</td>
						        <td>
						        <s:iterator value="gresslist">
			                     &nbsp;&nbsp;${staffname}（${sjwcnum}/${tasknumbyone}）
			                    </s:iterator>
						        </td>
						      </tr>
						      <tr>
						        <td>提交成果阶段</td>
						        <td>
						        <s:iterator value="gresslist">
			                     &nbsp;&nbsp;${staffname}（${tjcgnum}/${tasknumbyone}）
			                    </s:iterator>
						        </td>
						      </tr>
						      <tr>
						        <td>收支费用管理</td>
						        <td>&nbsp;&nbsp;${cost3}</td>
						      </tr>
						      <tr>
						        <td>项目档案管理</td>
						        <td>
						        <s:iterator value="gresslist">
			                     &nbsp;&nbsp;${staffname}（${xmdanum}/${tasknumbyone}）
			                    </s:iterator>
						        </td>
						      </tr>
						    </table>
						</td>
					</tr>
					<tr>
						<td height="30" width="100" align="right" valign="top">
							项目简介：
						</td>
						<td>
							<span>
							<p>&nbsp;&nbsp;&nbsp;${project.probrief}</p>
							</span>
						</td>
					</tr>
				</table>
			</div>
	</body>
</html>

