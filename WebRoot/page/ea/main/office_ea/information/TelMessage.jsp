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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>短消息列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<script src="<%=basePath%>js/ea/office_ea/information/TelMessage.js"></script>
<script type="text/javascript">
	var  basePath="<%=basePath%>";
	var pNumber = '${pageNumber}';
	var search = '${search}';
	var telMessageID = '';
	var token = 0;	
	var type = "${type}";
	var orgDetail = "${orgDetail}";
</script>
</head>
<body>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="20" align="center">选择</th>
					<th width="20" align="center">序号</th>
					<th width="70" align="center">发送方式</th>
					<th width="270" align="center">发送单位</th>
					<th width="140" align="center">发送人员</th>
					<th width="140" align="center">发送内容</th>
					<th width="120" align="center">接收短信手机</th>
					<th width="200" align="center">接收单位</th>
					<th width="120" align="center">接收人</th>
					<th width="120" align="center">往来关系</th>
					<th width="165" align="center">发送时间</th>
					<th width="200" align="center">发送状态</th>
				</tr>
			</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${telMessageID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${telMessageID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<s:if test="status=='00'">
									<span id="status">内部短信</span>
								</s:if>
								<s:if test="status=='01'">
									<span id="status">外部短信</span>
								</s:if>
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="content">${content}</span>
							</td>
							<td>
								<span id="telNum">${telNum}</span>
							</td>
							<td>
								<span id="receiverCompanyName">${receiverCompanyName}</span>
							</td>
							<td>
								<span id="receiverName">${receiverName}</span>
							</td>
							<td>
								<span id="ralation">${ralation}${connection}</span>
							</td>
							<td>
								<span id="sendDate">${fn:substring(sendDate,0,19)}</span>
							</td>
							<td>
								<span id="msgStatusName" style="display:none;">${msgStatusName}</span>
								  
							
								<s:if test="msgStatusName=='ok'||msgStatusName==0">
								
                                       提交成功
                                </s:if> 
								<s:else>
								
								 提交失败原因： <span id="msgStatusName">${msgStatusName}</span>
								</s:else>
								<span id="key" style="display: none">${key}</span>
								<span id="telMessageID" style="display: none">${telMessageID}</span>
								<span id="companyID" style="display: none">${companyID}</span>
							
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/telmessage/ea_getTelMessageList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}&orgDetail=${orgDetail}">
			</c:param>
		</c:import>
	</div>
	<!-- 查看短信 -->
	<div class="contentbannb jqmWindow jqmWindowcss1"
		style="top: 10%;width: 560px" id="jqModel">
		<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				短消息
				<div class="close"></div>
			</div>
			<table cellpadding="5px" cellspacing="10px" name="stafftable"
				id="stafftable">
				<tr>
					<td>时间:</td>
 					<td><input size="75" name="sendDate" id="sendDate" />
					<input type="hidden" name="orgDetail" value="${orgDetail}"/>
					</td>

				</tr>
				<tr>
					<td>内容:</td>
					<td><textarea rows="8" cols="55" name="content" id="content"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="button"
						 value="取消" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"/>
					</td>
				</tr>
			</table>
			<s:token></s:token>
		</form>
	</div>
	
       <div class="jqmWindow" style="width: 460px;right: 25%;;top: 10%"
		id="jqModelRepeat">
		<form name="postRepeatForm" id="postRepeatForm" method="post">
		 <s:token></s:token>
			<input type="submit" name="submit" style="display:none" />
			<input type="hidden" name="type" value="repeat"/>
			<input type="hidden" name="telMessageID" id= "telMessageIDs" value=""/>
           
		</form>
		</div>

	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 460px;right: 25%;;top: 10%"
		id="jqModelSearch">
		<form name="postSearchForm" id="postSearchForm" method="post">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table width="450px" id="cataffSearchTable">
				<tr>
					<td align="right">短信来源：</td>
					<td><select name="telMessage.status">
							<option value="">全部</option>
							<option value="00">内部</option>
							<option value="01">外部</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">发送状态：</td>
					<td><select name="telMessage.msgStatus">
							<option value="all">全部</option>
							<option value="suc">成功</option>
							<option value="fail">失败</option>
							<option value="other">其他</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">发送单位：</td>
					<td><input name="telMessage.companyName" id="" /></td>
				</tr>
				<tr>
					<td align="right">发送人员姓名：</td>
					<td><input name="telMessage.staffName" id="" /></td>
				</tr>
				<tr>
					<td align="right">接收方往来单位关系：</td>
					<td><select name="telMessage.connection" id="connection">
							<option value="">全部</option>
					</select></td>
				</tr>
				<tr>
				<td align="right">接收方往来个人关系：</td>
					<td><select name="telMessage.ralation" id="relations">

					</select></td>
				</tr>
				<tr>
					<td align="right">电话号码：</td>
					<td><input name="telMessage.telNum" id="telNum" /></td>
				</tr>
				<tr>
					<td align="right">起始时间：</td>
					<td><input name="sdate" onfocus="date(this)" />到<input
						name="edate" onfocus="date(this)" /></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="searchStaff"
					value=" 查询 " /> <input name="search" type="hidden" value="search" />
					<input name="type" type="hidden"  value="${type}" />
					<input name="orgDetail" type="hidden"  value="${orgDetail}" />
			</div>
		</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
</body>
</html>