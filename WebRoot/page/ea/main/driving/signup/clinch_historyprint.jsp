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
		<title>打印历史</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
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
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/driving/signup/clinch_historyprint.js"></script>
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
   var companyID='${companyID}';
   var typeprint='${typeprint}';
   var companyGroupLogo='${param.companyGroupLogo}';
   
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
						<th width="130" align="center">
							打印操作
						</th>
						<th width="130" align="center">
							打印次数
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
							联系方式
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
						<th width="200" align="center">
							学员状态
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${staffid}">
							<td>
								<input type="radio" class="chx JQuerypersonvalue"
									value="${staffid}" title="${staffid}" name="chbox" id="relationID"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<input type="button" value="打印学员证" onclick="printCard('${staffid}','${staffid}','${companyid}');"/>
							</td>
							<td>
								<span id="printCount">${printcount}</span>
							</td>
							<td>
								<span id="staffCode">${staffcode}</span>
							</td>
							<td>
								<span id="recordCode">${recordcode}</span>
							</td>
							<td>
								<span id="staffName">${staffname}</span>
							</td>
							<td>
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="usedNmae">${usednmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativeplace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffidentitycard}</span>
								<span id="staffID" style="display: none">${staffid}</span>
							</td>
							<td>
							<span>
								${fn:substring(param.module_title,0,2)}${studentstatusnote}
							</span>
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
					value="ea/clinch/ea_getListContactUser.jspa?module_Identifier=${param.module_Identifier}&educationalCategories=${param.educationalCategories}&view_Identifier=${param.view_Identifier}&pageNumber=${pageNumber}&search=${search}&module_title=${ param.module_title}&theModule=${param.theModule}&typeprint=${typeprint}&companyGroupLogo=${param.companyGroupLogo}">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>
		
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
							<input name="dtDrivingPrincipal.studentname" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							编号：
						</td>
						<td width="261">
							<input name="dtDrivingPrincipal.studentCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">个人身份证号：</td>
                     <td><input name="dtDrivingPrincipal.studentcard" />
                     <input name="module_Identifier" type="hidden" value="${param.module_Identifier}"/>
                     <input name="module_title" type="hidden" value="${param.module_title}"/>
                     <input name="view_Identifier" type="hidden" value="${param.view_Identifier}"/>
                     <input name="educationalCategories" type="hidden" value="${param.educationalCategories}"/>
                     <input name="theModule" type="hidden" value="${param.theModule}"/>
                     <input name="companyGroupLogo" type="hidden" value="${param.companyGroupLogo}"/>
                     </td>
                    </tr>
                </table>
            <div align="center">
            <input type="submit" name="submit" style="display: none" />
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
       
        
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