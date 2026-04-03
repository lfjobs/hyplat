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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>企业奖状奖牌</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/ea/office_ea/corporationcode/EnterpriseCitation.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  enterpriseCitationID = '';
         var  token=0;
		</script>


	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="20" align="center">
							选择
						</th>
						<th width="50" align="center">
							序号
						</th>
						<th width="100"  align="center">
							名称
						</th>
						<th width="240" align="center">
							主题
						</th>
						<th width="70" align="center">
							授予年度
						</th>
						<th width="240" align="center">
							备注
						</th>
						<th width="70" align="center">
							扫描附件
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${enterpriseCitationID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${enterpriseCitationID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="enName">${enName}</span>
							</td>
							<td>
								<span id="enSubject">${enSubject}</span>
							</td> 
							<td>
								<span id="enDate">${fn:substring(enDate,0,10)}</span>
							</td>
							<td>
								<span id="mark">${mark}</span>
							</td> 
							<td> 
								<span style="display:none" id="citationFilePath">${citationFilePath}</span>
								<s:if test="citationFilePath==null||citationFilePath==''">无</s:if>
                            	<s:else>
                            		<a href="#" onclick="lookImage('${citationFilePath}')">查看</a>
                            	</s:else>
							
								<span id="enterpriseCitationKey" style="display: none">${enterpriseCitationKey}</span>
								<span id="enterpriseCitationID" style="display: none">${enterpriseCitationID}</span>
								<span id="companyID" style="display: none">${companyID}</span>
								<span id="organizationID" style="display: none">${organizationID}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/enterprisecitation/ea_getEnterpriseCitationList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="top: 10%; width: 450px;" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					详细信息
					<div class="close">
					</div>
				</div>
				<table cellpadding="5px" cellspacing="10px" name="stafftable"
					id="stafftable">
					<tr>
						<td align="right">
							名称：
						</td>
						<td>
							<input name="enterpriseCitation.enName" id="enName" />
						</td>
						<td align="right">
							文件：
						</td>
						<td rowspan="2" align="left">
							<img id="pic" width="99" height="135"  />
						</td>
					</tr>
					<tr>
					<td align="right">
							主题：
						</td>
						<td>
							<input name="enterpriseCitation.enSubject" id="enSubject" />
						</td>
					</tr>
					<tr>
						<td align="right" >
							授予年度：
						</td>
						<td align="left">
							<input name="enterpriseCitation.enDate" id="enDate" onfocus="date(this)"/>
						</td>
						<td align="right" colspan="2">
						<input type="file" name="enterpriseCitation.citationFile" contentEditable="false" style="width: 165px"/>
							<input type="text" style="display:none" name="enterpriseCitation.citationFilePath" id="citationFilePath"/>
						</td>
					</tr> 
					<tr>
						<td align="right">
							备注：
						</td>
						<td  colspan="5">
							<input name="enterpriseCitation.mark" maxlength="50" class="ckTextLength" id="mark" size="40"/>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="hidden" name="enterpriseCitation.enterpriseCitationID" id="enterpriseCitationID" />
							<input type="hidden" name="enterpriseCitation.enterpriseCitationKey" id="enterpriseCitationKey" />
							<input type="hidden" name="enterpriseCitation.companyID" id="companyID" />
							<input type="hidden" name="enterpriseCitation.organizationID" id="organizationID" />
							<input type="button" class="input-button JQuerySubmit"
								style="cursor: pointer; width: 80px;" value="提交" />
							<input type="button" class="input-button JQueryreturn"
								style="cursor: pointer; width: 80px;" value="取消" />
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="cataffSearchTable" style="margin-left: 30px ">
					<tr>
						<td align="right">
							名称：
						</td>
						<td>
							<input name="enterpriseCitation.enName" id="enName" />
						</td>
					</tr> 
					<tr>
						<td align="right">
							时间：
						</td>
						<td>
							<input name="sDate" id="sDate" onfocus="date(this)"/>到<input name="eDate" id="eDate" onfocus="date(this)"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>