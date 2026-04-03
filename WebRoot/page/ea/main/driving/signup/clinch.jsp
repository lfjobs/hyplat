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
		<title>成交客户</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/driving/signup/clinch.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
   var relationID = '';
   var  treeID='${organizationID}'
   var basePath='<%=basePath%>';           
   var pNumber =${pageNumber};  
   var token = 0;
   var notoken = 0;
   var search='${search}';
   var roomtpID = "";
   var staffID = "";
   var roomtype = "";
   var deitnumid = "";
   var module_Identifier='${param.module_Identifier}';
   var module_title='${param.module_title}';
   var view_Identifier='${param.view_Identifier}';
   var educationalCategories='${param.educationalCategories}';
   var theModule='${param.theModule}';
   var retoken = 0;
   function closePort(){
       loadcabs.window.closePort();// 关闭读数据端口
}
</script>
	</head>
	<body>
		<div class="main_main" >
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>

						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="80" align="center">
							往来关系
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${relationID}">
							<td>
								<input type="radio" class="chx JQuerypersonvalue"
									value="${relationID}" title="${staffID}" name="chbox" id="relationID"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="recordCode">${recordCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="relation">${relation}</span>
							</td>
							<td>
								<span id="usedNmae">${usedNmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
								<span id="staffID" style="display: none">${staffID}</span>
								<span id="relationID" style="display: none">${relationID}</span>
								<span id="relationKey" style="display: none">${relationKey}</span>
								<span style="display: none" id="photo">${photo}</span>
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
					value="ea/clinch/ea_getListContactUserConsult.jspa?pageNumber=${pageNumber}&search=${search}&module_Identifier=${param.module_Identifier}">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>
		<%--<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							往来个人
							<div class="close"></div>
						</div>
					</div>
					<table width="685" height="220" border="0" id="stafftable"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="685" height="200" border="0" id="stafftable2"
									align="center" cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td align="right">
											人员编号：
										</td>
										<td>
											<input type="text" id="staffCode" readonly="readonly" />
										</td>
										<td align="right">
											档案编号：
										</td>
										<td>
											<input type="text" id="recordCode" readonly="readonly" />
										</td>
										<td id="phototd" rowspan="6" align="center">
											<img width="99" height="135" id="photo" />
										</td>
									</tr>
									<tr>
										<td align="right">
											姓名：
										</td>
										<td>
											<input type="text" id="staffName" readonly="readonly" />
										</td>
										<td align="right">
											曾用名：
										</td>
										<td>
											<input readonly="readonly" id="usedNmae" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											往来关系：
										</td>
										<td>
											<input readonly="readonly" id="relation" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											性别：
										</td>
										<td>
											<input type="text" id='sex' readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td align="right">
											籍贯：
										</td>
										<td>
											<input type="text" id='nativePlace' readonly="readonly" />
										</td>
										<td align="right">
											国籍：
										</td>
										<td>
											<input type="text" id='nationality' readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td align="right">
											民族：
										</td>
										<td>
											<input type="text" id='nation' readonly="readonly" />
										</td>
										<td align="right">
											身份证：
										</td>
										<td>
											<input id="staffIdentityCard" readonly="readonly" type="text"
												class="input" size="20" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>--%>
		
		
		<div class="jqmWindow" style="width: 350px; right: 45%;; top: 10%" 
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				<div class="drag">
					查询往来个人
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							姓名：
						</td>
						<td width="261">
							<input name="contactUser.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							编号：
						</td>
						<td width="261">
							<input name="contactUser.staffCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">个人身份证号：</td>
                     <td><input name="contactUser.staffIdentityCard" /></td>
                    </tr>
                </table>
            <div align="center">
            <input type="submit" name="submit" style="display: none" />
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
        
        <form name="goodsForm" id="goodsForm" method="post" enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							学员信息
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="120" align="right">
								条码号/身份证号：
							</td>
							<td width="80">
								<input name="recordCode" class="input" id="recordCode" size="25"
									style="margin-left: 2px;" />
							</td>
							<td >
							  <nobr>
							 &nbsp;&nbsp;
							    <input type="button" value="手动输入" class="btn02 manual"/>
								<input style="display:none;"type="button" value="扫描输入" class="btn02 scan"/>
								<input type="button" class="btn02" id="searchGood"
									name="button7" value="查询" style="display:none;"/>
								<input type="button" class="btn02 JQueryrefresh" name="button4"
									value="刷新" />
								<input type="button" class="btn02 xzgr" name="button4"
									value="新增" />
								<input type="button" class="btn02 inputting" name="button4"
									value="建档" />			
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							  <iframe width="0" height = "0" id="loadcabs" name="loadcabs"></iframe>
							</nobr>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 300px; width: 100%; overflow: auto;">
									<table width='98%' height='26' align='center' cellspacing='0'
										cellpadding='1' style='font-size: 12px;' class='bannb_01'>
										<tr>
											<td height='24' align='left' valign='top' class='txt01'>
												&nbsp;点击选择人员
											</td>
										</tr>
									</table>
									<table width='99%' align='center' id='gotable' cellpadding='0'
										cellspacing='0' class='table'>
										<thead>
										<tr>
											<th height='21' align='center' bgcolor='#E4F1FA'>
												选择
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												人员编码
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												档案编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												姓名
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												曾用名
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												性别
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												出生日期
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												国籍
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												籍贯
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												民族
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												身份证
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												查看详细
											</th>
										</tr>

                                   </thead>
                                   <tbody id="tbody">
                                   
                                   </tbody>
									</table>


								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form> 
		
<script type="text/javascript">
setTimeout(function(){ 
    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
},100);

$(window).resize(function(){ 
	setTimeout(function(){ 					    
	   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
	},100);
}); 
</script>    
	</body>
</html>