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
		<title>企业文化艺术作品管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/ea/office_ea/corporationcode/EnterpriseArt.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  enterpriseArtID = '';
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
						<th width="20" align="center">
							序号
						</th>
						<th width="100" align="center">
							作者
						</th>
						<th width="100" align="center">
							作品名称
						</th>
						<th width="200" align="center">
							作品描述
						</th>
						<th width="100" align="center">
							作品类别
						</th>
						<th width="200" align="center">
							鉴赏评论
						</th>
						<th width="100" align="center">
							备注
						</th>
						<th width="100" align="center">
							作品文件
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${enterpriseArtID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${enterpriseArtID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="enPerson">${enPerson}</span>
							</td>
							<td>
								<span id="enName">${enName}</span>
							</td>
							<td>
								<span id="enSubject">${enSubject}</span>
							</td> 
							<td>
								<span style="display:none" id="enType">${enType}</span><s:if test="enType=='00'">国内</s:if><s:if test="enType=='01'">国际</s:if>
							</td>
							<td>
								<span id="enDiscuss">${enDiscuss}</span>
							</td>
							<td>
								<span id="mark">${mark}</span>
							</td> 
							<td> 
								<span style="display:none" id="artFilePath">${artFilePath}</span>
								<s:if test="artFilePath==null||artFilePath==''">无</s:if>
                            	<s:else>
                            		<a href="#" onclick="lookImage('${artFilePath}')">查看</a>
                            	</s:else>
							
								<span id="enterpriseArtKey" style="display: none">${enterpriseArtKey}</span>
								<span id="enterpriseArtID" style="display: none">${enterpriseArtID}</span>
								<span id="companyID" style="display: none">${companyID}</span>
								<span id="organizationID" style="display: none">${organizationID}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/enterpriseart/ea_getEnterpriseArtList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss3"
			style="top: 10%; width: 600px;" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					详细信息
					<div class="close">
					</div>
				</div>
				<table  cellspacing="10px" name="stafftable"
					id="stafftable">
					<tr>
						<td align="right" height="40">
							名称:
						</td>
						<td height="40">
							<input name="enterpriseArt.enName" id="enName" />
						</td>
						<td align="right" height="40">
							主题:
						</td>
						<td height="40">
							<input name="enterpriseArt.enSubject" id="enSubject" />
						</td>
						<td align="right" height="40">
							文件:
						</td>
						<td rowspan="3" height="99px">
							<img id="pic" width="99" height="135"  /><br />
							<input type="file" name="enterpriseArt.artFile" contentEditable="false" style="width: 150px"/>
							<input type="text" style="display:none" name="enterpriseArt.artFilePath" id="artFilePath"/>
						</td>
					</tr>
					<tr>
						<td align="right" height="40"> 
							类型:
						</td>
						<td height="40">
							<select name="enterpriseArt.enType" id="enType">
								<option value="">请选择</option>
								<option value="00">国内</option>
								<option value="01">国际</option>
							</select>
						</td>
						<td align="right" height="40">
							作者:
						</td>
						<td height="40">
							<input name="enterpriseArt.enPerson" id="enPerson"/>
						</td>
					</tr>
					<tr>
					<td align="right" height="40">
							描述:
						</td>
						<td height="40" colspan="3">
							<input name="enterpriseArt.enDiscuss" id="enDiscuss" style="width: 310px"/> 
						</td>
					</tr>
					<tr>
						<td align="right" height="40">
							备注:
						</td>
						<td height="40" colspan="3">
							<input name="enterpriseArt.mark" id="mark" style="width: 310px"/>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="right">
							<input type="hidden" name="enterpriseArt.enterpriseArtID" id="enterpriseArtID" />
							<input type="hidden" name="enterpriseArt.enterpriseArtKey" id="enterpriseArtKey" />
							<input type="hidden" name="enterpriseArt.companyID" id="companyID" />
							<input type="hidden" name="enterpriseArt.organizationID" id="organizationID" />
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
		<div class="jqmWindow" style="width: 300px; right: 35%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="cataffSearchTable">
					<tr>
						<td align="right">
							名称：
						</td>
						<td>
							<input name="enterpriseArt.enName" id="enName" />
						</td>
					</tr> 
					<tr>
						<td align="right">
							类型：
						</td>
						<td>
							<select name="enterpriseArt.enType" id="enType">
								<option value="">全部</option>
								<option value="00">国内</option>
								<option value="01">国际</option>
							</select>
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