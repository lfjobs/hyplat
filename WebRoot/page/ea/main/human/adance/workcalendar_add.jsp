<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作记录时间表添加</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"type="text/css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/adance/workcalendar_add.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
	var ppageNumber = ${pageNumber};
	var notoken = 0;
	var workcalendarid = "";
	var seaDate = '${seaDate}';
	var stus = '${stus}';
</script>
<style>
.rili_cent{
	width:120px;
	height: 80px;
	background:#eee;
}
.l_gongzuo{
	float:left;
	height:80px;
	width:33px;
	text-align:center;
	background:#eee;
}
.l_gongzuo p{
	margin-left:10px;
	width:20px; 
	height:80px;
	line-height:25px;
	font-weight:bold;
	text-align:center;
}
.r_riqi{
	width:80px;
	height:80px;
	float:left;
}
.r_riqi p{
	width:100%;
	height:40px;
	line-height:50px;
	font-size:16px;
	font-weight:bold;
	text-align:center;
}
.r_riqi .rq{
	font-size:12px;
}
</style>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
	<form name="workcalendarForm" id="workcalendarForm" method="post">
		<s:token></s:token>
	<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="2">
  	<tr>
    <td align="left" valign="top">
		<div class="right">
    	<div class="qh_gg_nav">&nbsp;考勤工作日历预设</div> 		
    	<input type="submit" name="submit" style="display:none" />
			<table class="workcalendar" width="100%" >
				<tr>
					<th align="center" colspan="3"><input type="button" class="toNewDB" value="初始化数据"/></th>
					<th width="30" align="center" colspan="4">
						<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM'});" id="seaDate" name="seaDate" value="${seaDate }" onchange="foc(seaDate)"/>
						&nbsp;
						<input type="button" class="save" value="保存" />
					</th>
				</tr>
				<c:forEach var="mapl" items="${mapwc}" varStatus="step">
					<tr>
						 <c:forEach	items="${mapl}" var="l"  varStatus="step2">
						 <td>
						 	<div class="rili_cent dbcil" onmouseover="mouo(this)" onmouseout="moum(this)" style="border:#e6e7e8 2px solid">
							   <div class="l_gongzuo">
							   <p> <span class="status" ><c:if test="${l.status==00 }">工作日</c:if><c:if test="${l.status==01 }">休息日</c:if></span></p>
							   </div>
							   <div class="r_riqi">
							   <p class="rq"><span id="">${fn:substring(l.days,0,10) }</span></p>
							   <p><span id="">${l.week }</span></p>
							   <input type="hidden" class="status" name="mapSaveWC[${step.index+1 }${step2.index+1 }].status" value="${l.status }"/>
							   <input type="hidden" class="days" name="mapSaveWC[${step.index+1 }${step2.index+1 }].days" value="${fn:substring(l.days,0,10) }"/>
							   <input type="hidden" class="week" name="mapSaveWC[${step.index+1 }${step2.index+1 }].week" value="${l.week }"/>
							   </div>
							</div>
						 </td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</div>
	 </td>
	 </tr>
	</table>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	<!--修改窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 20%"
			id="jqModelDeit">
			<form name="jqModelDeitForm" id="jqModelDeitForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					修改工作日期
					<div class="close">
					</div>
				</div>
				<table width="260px" id="DeitTable">
					<tr>
						<td align="right" width="100px">
							日期：
						</td>
						<td>
							<input  id="days" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							星期：
						</td>
						<td>
							<input id="week"  readonly="readonly"/>
							
						</td>
					</tr>
					<tr>
						<td align="right">
							状态：
						</td>
						<td>
							<select id="status" style="width:140px;">
								<option value="00">工作日</option>
								<option value="01">休息日</option>
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button edit" id="edit"
						value=" 确定  " />
				</div>
			</form>
		</div>
</body>
</html>
