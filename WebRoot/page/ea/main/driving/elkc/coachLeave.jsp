<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>教练休班</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/driving/elkc/coachLeave.js"
	type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />




<script type="text/javascript">

var basePath="<%=basePath%>";
var  ppageNumber = "${pageNumber}";
var etvId = "";
var status = "";

var cpageNumber = 0;
var cpageCount = 0;
var cname = "";

</script>
</head>
<body> 
     <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query">
         教练休班<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>姓名:
		 <input type="text" class="clname" id="clname" name="tbJpTeacher.name" value="${tbJpTeacher.name}" placeholder="请输入姓名">
         身份证号:
		 <input type="text" class="clidcard" id="clidcard" name="tbJpTeacher.idcard" value="${tbJpTeacher.idcard}" placeholder="请输入身份证号">
		 休班开始时间：
         <input type="text" class="required text" id="time" name="beginDate" value="${beginDate}" onfocus="date(this);" placeholder="请选择休班时间">
         <input
                 type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
        </form>
	</div>
    
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	<form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>教练休班 &nbsp;&nbsp;&nbsp;
	</form>
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="150" align="center">教练员姓名</th>
					<th width="150" align="center">性别</th>
					<th width="150" align="center">证件号</th>
					<th width="150" align="center">联系电话</th>
                    <th width="300" align="center">休班开始时间</th>
                    <th width="300" align="center">休班结束时间</th>
                    <th width="150" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<% int number = 1; %>
				<s:iterator value="pageForm.list" var="f">
					<tr id="${f[0]}"  data-status="${f[7]}">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="${f[0]}" /></td>
						<td><%=number%></td>
						<td><span id="name">${f[1]}</span></td>
                        <c:choose>
                            <c:when test="${f[2] eq '1'}">
                                <td><span>男</span></td>
                            </c:when>
                            <c:when test="${f[2] eq '2'}">
                                <td><span>女</span></td>
                            </c:when>
                        </c:choose>
                        <td><span id="idcard">${f[3]}</span></td>
                        <td><span id="mobile">${f[4]}</span></td>
                        <td><span id="start_time">${f[5]}</span></td>
                        <td><span id="end_time">${f[6]}</span></td>
						<c:choose>
							<c:when test="${f[7] eq '01'}">
								<td><span>已生效</span></td>
							</c:when>
							<c:when test="${f[7] eq '00'}">
								<td><span>未生效</span></td>
							</c:when>
						</c:choose>
					</tr>
					<% number++; %>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/aflovacation/ea_coachVeave.jspa?pageNumber=${pageNumber}&beginDate=${beginDate}&tbJpTeacher.name=${tbJpTeacher.name}&tbJpTeacher.idcard=${tbJpTeacher.idcard}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
		
		
		<!--弹框-->
	<div class="fees_" id="fees_">
	    <div class="fees" style="height: 400px">
	        <h4 class="tit">教练休班<img src="<%=basePath%>/images/ico-delete.png" alt=""></h4>
	        <form  action="" method="post" class="con" id="con">
                <div class="mil" style="position: relative;">
                    <p>教练姓名：</p>
                    <div style="border: none;width: 208px;">
                        <input type="text" class="put3 name" name="name" value="" disabled>
                    </div>
                    <input type="button" value="选择" class="btn_formTip" style="position: absolute;width: 80px;text-align: center;right: 0;height: 20px;line-height: 20px;margin: 4px 0;font-size: 14px;border: none;top: 0;">
                </div>
                <div class="mil">
                    <p>性别：</p>
                    <div style="border: none;">
                        <input type="text" class="sex" name="sex" value="" disabled>
                    </div>
                </div>
                <div class="mil">
                    <p>身份证号：</p>
                    <div style="border: none;">
                        <input type="text" class="idcard" name="idcard" value="" disabled>
                    </div>
                </div>
                <div class="mil">
                    <p>联系电话：</p>
                    <div style="border: none;">
                        <input type="text" class="mobile" name="mobile" value="" disabled>
                    </div>
                </div>
	            <div class="mil">
	                <p>开始时间：</p>
					<div>
						<input type="text" class="put3 beginDate" name="beginDate" onfocus="date(this);" placeholder="请选择开始时间">
					</div>
				</div>
	            <div class="mil">
	                <p>结束时间：</p>
	                <div>
						<input type="text" class="put3 endDate" name="endDate" onfocus="date(this);" placeholder="请选择开始时间">
	                </div>
	            </div>
                <input type="hidden" class="etvKey" name="elycTrainerVacation.etvKey" value="">
                <input type="hidden" class="etvId" name="elycTrainerVacation.etvId" value="">
                <input type="hidden" class="trainer_id" name="elycTrainerVacation.trainer_id" value="">
	            <input type="button" value="保存" class="sub">
	        </form>
	    </div>
	</div>



     <div id="coach" class="coach" style="background: rgba(0, 0, 0, 0.3);position: fixed;width: 100%;height: 100%;left: 0;top: 0;z-index: 5;display: none;">
         <div class="jqmWindow jqmWindowcss1 jqmID4" style="top:10%; left: 50%; z-index: 3000; display: block;" id="xmjqModel">
             <div class="content1" style="width: 100%; height: 400px;">
                 <div class="contentbannb">
                     <div class="drag">
                         项目信息
                     </div>
                 </div>
                 <table width="99%" height="33" id="searchxm" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; background: #FFFFFF;">
                     <tbody><tr>
                         <td width="100" align="right">
                             教练名称：
                         </td>
                         <td width="142">
                             <input name="parameter" class="input" id="parameterxm" size="10" style="margin-left: 2px;">
                             <input type="hidden" id="selectxm">
                             <input type="hidden" id="selectxms">
                         </td>
                         <td height="33">
                             <input type="button" class="btn02" id="searchxmbtn" name="button7" value="查询">
                             <input type="button" class="btn02" id="qdxm" name="button5" value="确定">
                             <input type="button" class="btn02 JQueryreturns" name="button4" value="关闭">


                         </td>
                         <td width="80">
                             <a id="xmsy" title="0">上一页</a>
                         </td>
                         <td width="80">
                             <a id="xmxy" title="2">下一页</a>
                         </td>
                         <td width="100">
                             <a id="xmzy" title="0">共&nbsp;&nbsp; <span style="color: red" id="xmzycount"></span>&nbsp;&nbsp;页 </a>
                         </td>
                     </tr>
                     </tbody></table>
                 <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                     <tbody><tr>
                         <td width="100%" valign="top" align="left">
                             <div style="margin-top: 2px;  height: 310px; width: 100%; overflow: auto;">
                                 <table width="98%" height="26" align="center" cellspacing="0" cellpadding="1" style="font-size:12px;" class="bannb_01">
                                     <tbody><tr>
                                         <td height="24" align="left" valign="top" class="txt01">&nbsp;点击选择教练</td>
                                     </tr>
                                     </tbody></table>
                                 <table width="99%" align="center" id="xmtable" cellpadding="0" cellspacing="0" class="table">
                                     <thead>
                                         <tr>
                                             <th align="center" bgcolor="#E4F1FA" width="3%">选择</th>
                                             <th align="center" bgcolor="#E4F1FA" width="3%">序号</th>
                                             <th align="center" bgcolor="#E4F1FA" width="15%">教练名称</th>
                                             <th align="center" bgcolor="#E4F1FA" width="20%">身份证号</th>
                                             <th align="center" bgcolor="#E4F1FA" width="8%">联系电话</th>
                                             <th align="center" bgcolor="#E4F1FA" width="8%">性别</th>
                                         </tr>
                                     </thead>
                                     <tbody id="body_02xm">

                                     </tbody>
                                 </table>
                             </div>
                         </td>
                     </tr>
                     </tbody></table>
             </div>
         </div>
     </div>



</body>
</html>