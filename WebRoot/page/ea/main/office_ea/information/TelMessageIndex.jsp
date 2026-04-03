<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache" />
<title>短信管理</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="hy.ea.bo.Company"%>
<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
%>
<link href="<%=basePath%>css/ea/human/staff_post.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css"
	type="text/css" media="screen" />
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet"
			href="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.css" />
		<script type="text/javascript" src="<%=basePath%>js/jqueryplus/treeview/jquery.treeview.js"></script>
<script
	src="<%=basePath%>js/ea/office_ea/information/TelMessageIndex.js"></script>
<script type="text/javascript">
			var  treeID ="<%=session.getAttribute("organizationID")%>";
   var  basePath='<%=basePath%>';           
         var  pNumber ="${pageNumber}";  
         var  search='${search}';
         var  qqID = '';
         var  token=0;
         var j = 0;
         var ftj = 0;  //便于短消息计数
         var type='${type}';
         var orgDetail="${orgDetail}";
         var treePName="<%=c.getCompanyName()%>";
        
  </script>
  
  <style type="text/css">
 div.close3
{
  position: absolute;
  right: 7px;
  top: 6px;
  padding: 0 0 0 13px;
  height: 19px;
  width: 0px;
  background: url("<%=basePath%>js/jqModal/css/images/close_icon.png") no-repeat top left;
  cursor: hand;
}
 
  </style>
</head>
<body>
	<iframe src="<%=basePath%>ea/telmessage/ea_getTelMessageList.jspa?pageNumber=${pageNumber}&type=${type}&orgDetail=${orgDetail}"
				name="MRList" id="mainframe" frameborder="0" scrolling="no" width="100%"></iframe>
	<div>
		<input type="button" value="写短信" class="SHOWSEND" style="display: none" />
		<font color="#ff0000">${sessionScope.account.accountName }</font>,你好,你发送了
		<font color="#ff0000">${accountAll }</font>条短信，成功发送 <font
			color="#ff0000">${accountSuccess }</font>条短信。
	</div>
		<!--发送窗口 -->
		<div class="jqmWindow" style="width: 730px; left: 10%; top: 3%"
			id="jqModelSend">
			<form action="" name="mForm" id="mForm" method="post"
				enctype="multipart/form-data">
				<input name="submit" type="submit" style="display: none" />
				<div class="drag">
					发短信息
					<div class="close"></div>
					<input type="hidden" name="orgDetail" value="${orgDetail}" />
				</div>
				<table>
					<tr>
						<td>
							<table>
								<tr>
									<td rowspan="2"></td>
									<td colspan="2"></td>
								</tr>
								<tr>
									<td></td>
									<td colspan="2">
										<div style="border: solid #eff 1px;">
											<%--<p>导入号码<input type="file" contentEditable="false" name="txtFile" />
									</p>
								--%>
											<p>
												手机号码
												<input type="text" size="28" id="telPhotoNumber"
													class="telPhotoNumber" />
												<input type="button" value="输入" class="ADDTEL" />
												<input type="button" value="查找号码" class="openfm" />
												<input type="button" value="清空" class="RESET" />
											</p>
										</div>
										<textarea id="cumID" contentEditable="false" cols="70"
											rows="4" name="telNumber"></textarea>
										<div id="cData" style="display: none"></div>
										<input type="hidden" id="fulldata" name="fulldata" />

										<p style="text-align: left">
											请在以下输入要发送的内容:
										</p>
										<textarea cols="70" rows="8" id="content"
											name="telMessage.content"></textarea>
										<p style="text-align: right;">
											<input id="companyName" name="telMessage.companyName"
												type="hidden" />
											<input type="button" value="发送" class="SEND" disabled="ture" />
											<input type="button" value="清空" class="RMCONTENT" />
										</p>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>
										常用短信&nbsp;&nbsp;<input type="button" value="新增" class="addQms"/>&nbsp;<input type="button" value="删除" class="deleteQms"/>
									</td>
								</tr>
								<tr>
									<td>
										<select size="14" style="width:150px;"id="selectqms">
											
										</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>

		</div>
		
		
		
		
   		<!--新增常用短信窗口 -->
	<div class="jqmWindow" style="width: 300px; left: 40%; top: 15%"
		id="jqModelQMS">
		<form action="" name="QMSForm" id="QMSForm" method="post" enctype="multipart/form-data">
			<input name="submit" type="submit" style="display: none" />
			
		    <div class="drag">
		 		新增常用短信
				<div class="close3" id="close"></div>
			</div>
			<table>
				<tr>
					<td>常用短信内容：</td>
					<td>&nbsp;</td>
					
				</tr>
				<tr>
					<td colspan="2" align="center"><div style="padding:4px;"><textarea id="tqms" cols="36" rows="9"></textarea></div></td>
					
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="button" value="提交" id="submitqms"/>&nbsp;<input type="button" value="关闭" class="close2"/></td>
					
				</tr>
				
				
			</table>
	
		</form>
	</div>


		<!--失败重发窗口 -->
		<div class="jqmWindow" style="width: 700px; left: 10%; top: 3%"
			id="jqModelReSends">
			<form action="" name="remForm" id="remForm" method="post"
				enctype="multipart/form-data">
				<input name="submit" type="submit" style="display: none" />

				<div class="drag">
					重发失败信息
					<div class="close close1" id="closep"></div>
					<input type="hidden" name="orgDetail" value="${orgDetail}" />
				</div>
				<table>
					<tr>
						<td>
							<table>
								<tr>
									<td rowspan="2"></td>
									<td colspan="2"></td>
								</tr>
								<tr>
									<td></td>
									<td colspan="2">
										<div style="border: solid #eff 1px;">
				
											<p>
												手机号码：
												
											</p>
										</div>
										<textarea id="cumID2" contentEditable="false" cols="70"
											rows="4" name="telNumber"></textarea>
										<input type="hidden" value="" id="telID2" name="telMessage.telMessageID"/>
										<input type="hidden" id="filename" name="filename" />
                                        <p style="text-align: left">
											发送失败原因:<font color="red"><span id="errorcause"></span></font>
										</p>
										<p style="text-align: left">
											请在以下输入要发送的内容:
										</p>
										<textarea cols="70" rows="8" id="content2"
											name="telMessage.content"></textarea>
										<p style="text-align: right;">
									
											<input type="button" value="发送" class="SEND2" />
											<input type="button" value="清空" class="RMCONTENT2" />
										</p>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table>
								<tr>
									<td>
										请选择失败号码日志
										
									</td>
								</tr>
								<tr>
									<td>
										<select size="14" style="width: 190px;" id="selectfaillog">

										</select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>
		</div>


		<div id="jqmWindow2" class="jqmWindow"
		style="width: 95%; height: 500px; color: #333; absolute; display: none; left: 1%; top: 1%">
		<div class="drag">
			号码查询
			<div class="close close2"></div>
		</div>
		<table width="100%" style="background: #eff" cellpadding="2">
			<tr>
				<td align="left">查询范围 <s:select
						list="#{'COMP':'公司','PERS':'个人'}" name="searchMap['type']"
						id="selType"></s:select> <input type="button" class="input-button"
					id="search" value="查询" /></td>
				<td align="right"><input type="button" class="input-button"
					id="checked" value="全选/全不选" /> <input type="button"
					class="input-button" id="searchAdd" value="添加" />
				<input type="hidden" id="searchtype" value="" />	
				</td>
			</tr>
		</table>
			<table width="100%" border="1">
				<tr>
					<td valign="top" style="width:20%;background:#FFFFFF;">
					<div id="tree1" style="height:420px;width:300px;overflow:auto;border:2px solid #ccc;"></div>
					</td>
					<td>
					<iframe name="searchForm" id="searchForm" src="" width="100%"
			height="428px" allowTransparency="true"></iframe>
					</td>
				<tr>
			</table>

			
	</div>
	<script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                  
                 $("#mainframe").css({"height" : $(window).height() + "px"});
             },100);
         }); 
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
     });
</script>
</body>
</html>
