<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>培训日志记录</title>
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
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>/js/ea/driving/training/progressStudentSubject.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

	<script type="text/javascript">
   var relationID = "";
   var studentid="";
   var  treeID='${organizationID}'
   var basePath='<%=basePath%>';
   var companyIDLogin='<%=c.getCompanyID()%>';
   var companyNameLogin='<%=c.getCompanyName()%>';
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
   var companyGroupLogo='${param.companyGroupLogo}';
   var docstatus="${viewProgressStudentSubject.docstatus}";
</script>
<style type="text/css">
</style>
	</head>
	<body>
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="25" align="center">
							序号
						</th>
						<th width="80" align="center">
							报名时间
						</th>
						<th width="60" align="center">
							报考车型
						</th>
						<th width="60" align="center">
							学员姓名
						</th>
						<th width="30" align="center">
							性别
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="150" align="center">
							身份证
						</th>
						<th width="100" align="center">
							教练名称
						</th>
						<th width="200" align="center">
							完成进度(完成学时/总学时)
						</th>
						<%-- <s:if test="pageForm.list.size>0">
							<s:iterator value="pageForm.list" begin="0" end="0" >
								<s:generator separator="," val="complete" id="complete_i">
										 <s:iterator value="complete_i" id="complete_ii">
										     	<th width="80" align="center">
									            	<s:generator separator="-" val="complete_ii" id="complete_iii">
									            			 <s:iterator value="complete_iii" id="complete_iiii" status="row">
									            			 	<s:if test="#row.index == 0">
									            					<s:property value="#complete_iiii"/>
									            				</s:if>	
									            			 </s:iterator> 
									            	</s:generator> 
									        	</th>
										 </s:iterator> 
										 
								</s:generator> 
							</s:iterator>
						</s:if> --%>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${studentid}">
							<td>
								<input type="radio" class="chx JQuerypersonvalue"
									value="${studentid}"  name="chbox" id="studentid"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="registrationdate">${registrationdate}</span>
							</td>
							<td>
								<span id="registrationcarname">${registrationcarname}</span>
							</td>
							<td>
								<span id="studentname">${studentname }</span>
							</td>
							<td>
								<span id="studentsex">${studentsex}</span>
							</td>
							<td>
								<span id="studentphone">${studentphone}</span>
							</td>
							<td>
								<span id="studentcard">${studentcard}</span>
								<span id="studentcode" style="display:none;">${studentcode}</span>
							</td>
							<td>
								<span id="coachname">${coachname}</span>
							</td>
							<td  style="position: relative;">
									<div style="position:absolute;width: 95%;height:21px;border:0px; margin : 0px;padding:0px; background-color: #cccccc;z-index: 99;"></div>
									<div style="position:absolute;width: ${((finished - total)>0?1:finished/total)*95}%;height:21px;border:0px; margin  : 0px;padding:0px; background-color: rgb(192, 224, 245);z-index: 100;"></div>
									<div style="position:absolute;width: 95%;height:21px;border:0px; margin : 0px;padding:0px;z-index: 101;font: white ; "> <fmt:formatNumber type="percent" value="${(total==0)?0:(finished - total)>=0?1:finished/total}" />(${finished}/${total})</div>
							</td>
								<%-- <s:generator separator="," val="complete" id="complete_i">
									 <s:iterator value="complete_i" id="complete_ii">  
									     	<s:generator separator="-" val="complete_ii" id="complete_iii">
												 <s:iterator value="complete_iii" id="complete_iiii" status="row">  
											        	<s:if test="#row.index == 1&&#complete_iiii==1">
											        		<td>
											        	 	<spn><img src="<%=basePath%>/images/ea/office/accept.png"  height="20" width="20"/></spn>
											        		</td>
											        	</s:if>
											        	<s:if test="#row.index == 1&&#complete_iiii==0">
											        	 	<td>
											        	 		<spn><img src="<%=basePath%>/images/ea/office/cancle.png"  height="20" width="20"/></spn>
											        		</td>
											        	</s:if>
											        	
												 </s:iterator> 
											</s:generator>
									 </s:iterator> 
									 
								</s:generator> --%>
							
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/progressstudentsubject/ea_getListOfViewProgressStudentSubject.jspa?search=${search }&module_Identifier=${param.module_Identifier}&educationalCategories=${param.educationalCategories}&view_Identifier=${param.view_Identifier}&module_title=${param.module_title}&companyGroupLogo=${param.companyGroupLogo }&viewProgressStudentSubject.docstatus=${viewProgressStudentSubject.docstatus}&pageNumber=${pageNumber}">
				</c:param>
			</c:import>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>
        <div id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				
							报名时间：
							<input type="text" style="width: 80px" name="viewProgressStudentSubject.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>&nbsp;
							至&nbsp;<input type="text" style="width: 80px" name="viewProgressStudentSubject.searchEndDate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
						
							教练姓名：
						
							<input name="viewProgressStudentSubject.coachname"  class="ckTextLength" maxlength="20" size="7"/>
						
							学员姓名：
						
							<input name="viewProgressStudentSubject.studentname"  class="ckTextLength" maxlength="20" size="7"/>
							身份证：
						<input name="viewProgressStudentSubject.studentcard"  id="studentcard" class="ckTextLength" maxlength="36" size="13"/>
                     <input name="module_Identifier" type="hidden" value="${param.module_Identifier}"/>
                     <input name="module_title" type="hidden" value="${param.module_title}"/>
                     <input name="view_Identifier" type="hidden" value="${param.view_Identifier}"/>
                     <input name="educationalCategories" type="hidden" value="${param.educationalCategories}"/>
                     <input name="theModule" type="hidden" value="${param.theModule}"/>
                     <input name="companyGroupLogo" type="hidden" value="${param.companyGroupLogo}"/>
                       <input name="staffIdArr" type="hidden" id="staffIdArr"/>
                       <input name="viewProgressStudentSubject.docstatus" type="hidden" value="${viewProgressStudentSubject.docstatus}"/>
                     
							完成进度：
						
						<input name="viewProgressStudentSubject.theProgress"  class="isNaN" maxlength="4" size="2"/><span style="color:red">&nbsp;&nbsp;(学时)*</span>
						
						<input type="submit" name="submit" style="display: none" />
			              <input type="button"  id="tosearch" value=" 查询 " />
			              <input name="search" type="hidden" value="search" />
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
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="button" class="btn02 print" 
									value="打印预览" />
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
												&nbsp;全选<input type="checkbox"  id="fullcheck" />
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
        
<%-- <script type="text/javascript">
setTimeout(function(){ 
    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
},100);

$(window).resize(function(){ 
	setTimeout(function(){ 					    
	   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
	},100);
}); 
</script>  --%>
</body>
</html>