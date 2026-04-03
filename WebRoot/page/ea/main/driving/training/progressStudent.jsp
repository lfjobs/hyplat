<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>进度跟踪</title>
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
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>/js/ea/driving/training/progressStudent.js"></script>
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
</script>
	</head>
	<body>
			<%-- <form name="SearchForm" id="SearchForm" method="post" action="" style="position: relative;background-color: rgb(200,218,240);" >
				<table >
					<tr>
						<td >
							报名时间：
						</td>
						<td >
							<input type="text" style="width: 80px" name="viewProgressStudent.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>&nbsp;
							至&nbsp;<input type="text" style="width: 80px" name="viewProgressStudent.searchEndDate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
						</td>
						<td >
							教练姓名：
						</td>
						<td >
							<input name="viewProgressStudent.coach"  class="ckTextLength" maxlength="20"/>
						</td>
                        <td >身份证：</td>
                     <td><input name="viewProgressStudent.studentcard"  id="studentcard" class="ckTextLength" maxlength="36"/>
                     <input name="module_Identifier" type="hidden" value="${param.module_Identifier}"/>
                     <input name="module_title" type="hidden" value="${param.module_title}"/>
                     <input name="view_Identifier" type="hidden" value="${param.view_Identifier}"/>
                     <input name="educationalCategories" type="hidden" value="${param.educationalCategories}"/>
                     <input name="theModule" type="hidden" value="${param.theModule}"/>
                     <input name="companyGroupLogo" type="hidden" value="${param.companyGroupLogo}"/>
                       <input name="staffIdArr" type="hidden" id="staffIdArr"/>
                     </td>
						<td> <input type="submit" name="submit" style="display: none" />
				              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
				              <input name="search" type="hidden" value="search" />
						</td>
					</tr>
                </table>
            </form> --%>
		<div  >
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
						<th width="90" align="center">
							电话
						</th>
						<th width="150" align="center">
							身份证
						</th>
						<th width="60" align="center">
							教练名称
						</th>
						<th width="50" align="center">
							入学缴费
						</th>
						<th width="40" align="center">
							科目一
						</th>
						<th width="40" align="center">
							科目二
						</th>
						<th width="40" align="center">
							科目三
						</th>
						<th width="40" align="center">
							科目四
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="bean">
						<tr id="${bean[0]}">
							<td>
								<input type="radio" class="chx JQuerypersonvalue"
									value="${bean[0]}"  name="chbox" id="studentid"/>
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="registrationdate">${bean[2]}</span>
							</td>
							<td>
								<span id="registrationcarname">${bean[3]}</span>
							</td>
							<td>
								<span id="studentname">${bean[5] }</span>
							</td>
							<td>
								<span id="studentsex">${bean[6]}</span>
							</td>
							<td>
								<span id="studentphone">${bean[7]}</span>
							</td>
							<td>
								<span id="studentcard">${bean[8]}</span>
								<span id="studentcode" style="display:none;">${bean[1]}</span>
							</td>
							<td>
								<span id="coach">${bean[10]}</span>
							</td>
							<td>
								<span id="entrance">
								<s:if test='%{#bean[11] >= 1 }'><img src="<%=basePath%>/images/ea/driving/valid.png"  /></s:if>
								<s:else><img src="<%=basePath%>/images/ea/driving/red_asterisk.png"   /></s:else>
								</span>
							</td>
							<td>
								<span id="subjectone" >
								<s:if test="%{#bean[12] >= 1}"><img src="<%=basePath%>/images/ea/driving/valid.png"   /></s:if>
								<s:else><img src="<%=basePath%>/images/ea/driving/red_asterisk.png"   /></s:else></span>
							</td>
							<td>
								<span id="subjecttwo">
								<s:if test="%{#bean[13] >= 1}"><img src="<%=basePath%>/images/ea/driving/valid.png"   /></s:if>
								<s:else><img src="<%=basePath%>/images/ea/driving/red_asterisk.png"   /></s:else></span>	
							</td>
							<td>
								<span id="subjectthree">
								<s:if test="%{#bean[14] >= 1}"><img src="<%=basePath%>/images/ea/driving/valid.png"   /></s:if>
								<s:else><img src="<%=basePath%>/images/ea/driving/red_asterisk.png"   /></s:else></span>	
							</td>
							<td>
								<span id="subjectfour">
								<s:if test="%{#bean[15] >= 1}"><img src="<%=basePath%>/images/ea/driving/valid.png"   /></s:if>
								<s:else><img src="<%=basePath%>/images/ea/driving/red_asterisk.png"   /></s:else></span>
								<span id="companyid" style="display: none">${bean[17]}</span>
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
					value="/ea/progressstudent/ea_getListOfViewProgressStudent.jspa?search=${search}">
				</c:param>
			</c:import>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>
        <div  id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				
							报名时间：
							<input type="text" style="width: 80px" name="viewProgressStudent.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>&nbsp;
							至&nbsp;<input type="text" style="width: 80px" name="viewProgressStudent.searchEndDate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
						
							教练姓名：
						
							<input name="viewProgressStudent.coach"  class="ckTextLength" maxlength="20" size="7"/>
						
							学员姓名：
						
							<input name="viewProgressStudent.studentname"  class="ckTextLength" maxlength="20" size="7"//>
						身份证：
                     <input name="viewProgressStudent.studentcard"  id="studentcard" class="ckTextLength" maxlength="36" size="13"//>
                     <input name="module_Identifier" type="hidden" value="${param.module_Identifier}"/>
                     <input name="module_title" type="hidden" value="${param.module_title}"/>
                     <input name="view_Identifier" type="hidden" value="${param.view_Identifier}"/>
                     <input name="educationalCategories" type="hidden" value="${param.educationalCategories}"/>
                     <input name="theModule" type="hidden" value="${param.theModule}"/>
                     <input name="companyGroupLogo" type="hidden" value="${param.companyGroupLogo}"/>
                       <input name="staffIdArr" type="hidden" id="staffIdArr"/>
                    
							学员状态：
						
							<select name="viewProgressStudent.theProgress">
								<option value="">全部</option>
								<optgroup label="科一">
										<option value="0104">未报开学</option>
										<option value="0105">已报开学</option>
										<option value="0103">未培训</option>
										<option value="0108">已培训</option>
										<option value="0107">已合格</option>
								</optgroup>
								<optgroup label="科二">
										<option value="0203">未培训</option>
										<option value="0205">已培训</option>
										<option value="0207">已合格</option>
								</optgroup>
								<optgroup label="科三">
										<option value="0303">未培训</option>
										<option value="0305">已培训</option>
										<option value="0307">已合格</option>
								</optgroup>
								<optgroup label="科四">
										<option value="0403">未培训</option>
										<option value="0405">已培训</option>
										<option value="0407">已合格</option>
								</optgroup>
							</select>
            <input type="submit" name="submit" style="display: none" />
              <input type="button" id="tosearch" value=" 查询 " />
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
<%--         
<script type="text/javascript">
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