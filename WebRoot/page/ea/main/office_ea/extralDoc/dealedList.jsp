<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>已处理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<script src="<%=basePath%>js/ea/office_ea/extralDoc/dealedList.js"></script>

		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>



		<script type="text/javascript">
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};
   var treeID = '<%=session.getAttribute("organizationID")%>';  
   var  search='${search}';
   var  token=0;
   var module ='<%=request.getAttribute("module")%>'; 
   var Ids = "";

   </script>

	</head>
	<body>

		<!--意见箱 -->

		<div id="draft">
			<table class="draft0">
				<thead>
					<tr>
						<th width="70" align="center">
							选择
						</th>
						<th width="70" align="center">
							序号
						</th>
						<th width="70" align="center">
							投诉编号
						</th>
						<th width="100" align="center">
							投诉人电话
						</th>
						<th width="150" align="center">
							投诉人账号
						</th>
						<th width="100" align="center">
							投诉人真实姓名
						</th>
						<th width="70" align="center">
							投诉内容
						</th>
						<th width="150" align="center">
							投诉时间
						</th>
						<th width="70" align="center">
							处理状态
						</th>
						<th width="80" align="center">
							处理意见
						</th>
						<th width="70" align="center">
							回复内容
						</th>


					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr class="docs" id="${Id}">
							<td class="td_bg01">
								<input type="checkbox" name="checkinput"
									class="JQuerypersonvalue" value="${Id}" />
							</td>
							<td class="td_bg01">
								<span><%=number%></span>
							</td>
							<td class="td_bg01">
								<span id="code">${code}</span>
							</td>
							<td class="td_bg01">
								<s:if test="telphone==null||telphone==''">
									<span id="telphone">无</span>
								</s:if>
								<s:else>
									<span id="telphone">${telphone}</span>
								</s:else>
							</td>
							<td class="td_bg01">
								<s:if test="userName==null||userName==''">
									<span id="userName">匿名</span>
								</s:if>
								<s:else>
									<span id="userName">${userName}</span>
								</s:else>

							</td>

							<td class="td_bg01">
								<s:if test="realName==null||realName==''">
									<span id="realName">匿名</span>
								</s:if>
								<s:else>
									<span id="realName">${realName}</span>
								</s:else>
							</td>
							<td class="td_bg01">
								<s:if test="docPath==null||docPath==''">无</s:if>
								<s:else>
									<span><a href="javascript:OpenWord('${docPath}');">查看</a>
									</span>
								</s:else>
								<input type="hidden" value="${docPath}" id="docPath" />
							</td>
							<td class="td_bg01">
								<span id="complaintTime">${fn:substring(complaintTime,0,19)}</span>
							</td>
							<td class="td_bg01">
								<img src="<%=basePath%>${statusPic}" width="30" height="20"
									id="statusPic" />
								<input type="hidden" value="${status}" id="hidstatus" />
							</td>


							<td class="td_bg01">
								<div style="text-align: left;">
									<s:if test='status=="pass"||status=="reject"||status=="dealed"'>
										<span id="suggestion">${suggestion}</span>
									</s:if>
								</div>
							</td>
							<td class="td_bg01">
								<div style="text-align: left;">
									<span id="reply">${reply}</span>
								</div>
							</td>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/extralflow/ea_getComplaintList.jspa?type=dealed&pageNumber=${pageNumber}&search=${search}&type=draft">
				</c:param>
			</c:import>
		</div>



		<!-- 回复 -->
		<form id="replyForm" name="replyForm">
			<div class="jqmWindow"
				style="text-align: left; width: 400px; right: 25%; top: 10%"
				id="jqModelReply">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					填写回复内容
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="25" id="templateSearchtab">
						<tr>
							<td align="right">
								<textarea cols="40" rows="5" name="docComplaint.reply">
								 
								
								
								</textarea>

							</td>

						</tr>
						<tr>

							<td colspan="2" align="center">
								<input type="button" value="回复" class="input-button"
									id="toReply" />
								<input type="hidden" value="" id="hidreply"
									name="docComplaint.Id" />
							</td>
						</tr>

					</table>
				</center>
			</div>
		</form>


		<!-- 查看 -->
		<form id="viewForm" name="viewForm">
			<div class="jqmWindow"
				style="text-align: left; width: auto; width: 400px; right: 25%; top: 5%"
				id="jqModelView">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查看
					<div class="close">
					</div>
				</div>
				<table width="100%" cellspacing="10" id="viewtable">
					<tr>
						<td width="10%" align="right">
							投诉编号：

						</td>
						<td width="5%" align="left">
							<span id="code"></span>

						</td>
						<td width="10%" align="left">
							投诉人电话：
							<span id="telphone"></span>

						</td>
					</tr>

					<tr>
						<td width="10%" align="right">
							投诉人账号：

						</td>
						<td width="5%" align="left">
							<span id="userName"></span>

						</td>
						<td width="10%" align="left">
							真实姓名：
							<span id="realName"></span>

						</td>
					</tr>
					<tr>
						<td align="right">
							投诉时间：

						</td>
						<td align="left" colspan="2">
							<span id="complaintTime"></span>

						</td>


					</tr>
					<tr>
						<td align="right">
							投诉内容：

						</td>
						<td align="left" colspan="2">
							<img src="<%=basePath%>images/complaint/word.gif" title="点击查看内容"
								style="cursor: hand;" id="content" />
							<input type="hidden" value="" id="hidcontent" />

						</td>


					</tr>
					<tr>
						<td align="right">
							处理状态：

						</td>
						<td align="left" colspan="2">
							<img src="" width="30" height="20" id="dealstatus" />
						</td>


					</tr>
					<tr>
						<td align="right">
							处理意见：

						</td>
						<td align="left" colspan="2">
							<div id="suggestion" style="overflow:auto;width:200px;height:50px;background:#ffffff;border:#CCC 1px solid;"></div>

						</td>


					</tr>
					<tr>
						<td align="right">
							回复内容：

						</td>
						<td align="left" colspan="2">
							<div id="reply" style="overflow:auto;width:200px;height:50px;background:#ffffff;border:#CCC 1px solid;"></div>
                            <input type="text" id="help" />
						</td>
					</tr>


				</table>
			</div>
		</form>

		<form id="searchForm" name="searchForm">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="10" id="templateSearchtab">
						<tr>
							<td align="right">
								投诉编号：
							</td>
							<td>
								<input type="text" id="code" name="docComplaint.code" size="15" />

							</td>
						</tr>

						<tr>
							<td align="right">
								起时间：
							</td>
							<td>
								<input type="text" name="docComplaint.startDate" id="startDate"
									onFocus="var 

endTime=$dp.$('endDate');WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
									readonly size="15" />

							</td>
						</tr>
						<tr>

							<td align="right">
								止时间：
							</td>
							<td>
								<input type="text" name="docComplaint.endDate" id="endDate"
									onFocus="WdatePicker({lang:'zh-cn', 

dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
									readonly size="15" />
							</td>
						</tr>
					</table>
					<div align="center" style="margin-top: 10px;">
						<input type="button" class="input-button" id="tosearch"
							value=" 查询 " />
						<input name="search" type="hidden" value="search" />
						<input name="global" type="hidden" value="dealed" />
					</div>
				</center>
			</div>
		</form>
		<form name="SendForm2" id="SendForm2" method="post">
			<div class="jqmWindow"
				style="width: 360px; height: 210px; right: 15%; top: 10%;"
				id="jqModelSend2">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					选择人员
					<div class="close">
					</div>
				</div>
				<center>
					<table width="300" id="SearchTable" cellspacing="8" cellpadding="2">
						<tr>
							<td align="right">
								收件人公司：
							</td>
							<td align="left">
								<select id="companyIDofSubscriber2"
									name="document.companyIDofSubscriber" style="width: 150px;">
									<option value="">

										请选择收件人公司
									</option>
								</select>

							</td>
						</tr>
						<tr>
							<td align="right">
								收件人部门：
							</td>
							<td align="left">
								<select id="deptIDofSubscriber2"
									name="document.deptIDofSubscriber" style="width: 150px;">
									<option value="">

										请选择收件人部门
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								收件人：
							</td>
							<td align="left">
								<select name="document.subscriberID" id='subscriberID2'
									style="width: 150px;">
									<option value="">

										请选择收件人
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								<input type="checkbox" name="document.drafterMessageStatus"
									id="tip3" checked />
								短信提示
							</td>
						</tr>

					</table>

					<div align="center">
						<input type="button" class="input-button" id="send" value=" 发送 " />
						<input type="hidden" id="hidids" name="docComplaint.Id" value="" />
					</div>
				</center>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
