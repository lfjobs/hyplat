<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>工作计划</title>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
         <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <style type="text/css">
<!--
body,td,th {
	font-size: 12px;
}
.table {
	border-collapse:collapse;
	border:1px solid #a8c7ce;
	font-size:12px;
}

.table th {
	border:1px solid #a8c7ce;
	color:#1E5494;
	background:#E4F1FA;
}
.table td {
	border:1px solid #a8c7ce;
	color:#333;
}
-->
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

</style>
	</head>
	<body style="height: 100%;position: absolute;overflow: scroll;width: 700px;left: 50%;margin-left: -350px;" >
	<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0">
    </OBJECT>
	<div style="height: 100%;">
	<table width="643" border="0" align="center" cellpadding="0" cellspacing="0" height="35" style="margin-top: 15px;">
	  <tr>
	    <td width="55%" align="right"><strong>项目工作计划统计表</strong> </td>
	    <td width="45%" align="right"><a style="color: red;font-size: 20px;">${LokBookPrintVOID}</a></td>
	  </tr>
	</table>
	<table width="643" height="30"  border="0" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
	  <tr>
	    <td width="50" height="30" align="right">公司：</td>
	    <td width="250">${LokPlanPrintVO.companyName}</td>
	    <td width="40" align="right">部门：</td>
	    <td width="40">&nbsp;</td>
	    <td width="80" align="right">责任人：</td>
	    <td width="80">${LokPlanPrintVO.staffName }</td>
	    <td width="60">&nbsp;</td>
	    <td width="75">${fn:substring(todaydate, 0, 10)}</td>
	  </tr>
	</table>
<div >
<table width="643"  align="center" cellpadding="0" cellspacing="0" class="table">
	  <tr>
	    <th width="35" align="center" bgcolor="#E4F1FA">录入时间</th>
	    <th width="35" align="center" bgcolor="#E4F1FA">起时间</th>
	    <th width="35" align="center" bgcolor="#E4F1FA">止时间</th>
	    <th width="35" align="center" bgcolor="#E4F1FA">项目编码</th>
	    <th width="90" align="center" bgcolor="#E4F1FA">项目内容</th>
	    <th width="35" align="center" bgcolor="#E4F1FA">明细项目编号</th>
	    <th width="140" align="center" bgcolor="#E4F1FA">明细项目内容</th>
	    <th width="60" align="center" bgcolor="#E4F1FA">项目要求</th>
	    <th width="35" align="center" bgcolor="#E4F1FA">完成情况</th>
	    <th width="20" align="center" bgcolor="#E4F1FA">应得分数</th>
	    <th width="20" align="center" bgcolor="#E4F1FA">扣分</th>
	    <th width="20" align="center" bgcolor="#E4F1FA">实际得分</th>
	    <th width="20" align="center" bgcolor="#E4F1FA">附件</th>
	  </tr>	
	 <s:iterator value="LokPlanPrintVO.logplanList">
	  <tr>
	    <td align="center" bgcolor="#FFFFFF">${entryDate }</td>
	    <td align="center" bgcolor="#FFFFFF">${startDate }</td>
	    <td align="center" bgcolor="#FFFFFF">${endDate }</td>
	    <td align="center" bgcolor="#FFFFFF">${projectcode }</td> 
	    <td align="center" bgcolor="#FFFFFF">${jobName }</td>
	    <td align="center" bgcolor="#FFFFFF">${projectDetailsCode }</td>
	    <td align="center" bgcolor="#FFFFFF">${jobContent }</td>
	    <td align="center" bgcolor="#FFFFFF">${projectRequirements }</td>
	    <td align="center" bgcolor="#FFFFFF">${entry }</td>
	    <td align="center" bgcolor="#FFFFFF">${fraction }</td>
	    <td align="center" bgcolor="#FFFFFF">${points }</td>
	    <td align="center" bgcolor="#FFFFFF">${actualScore }</td>
	    <td align="center" bgcolor="#FFFFFF">&nbsp;</td>
	  </tr>
    </s:iterator>
</table>
</div>
<table width="643" border="0" align="center" cellpadding="0" cellspacing="0">
</table>
<table width="643" border="0" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
    <td width="80" height="30" align="center">公司经理：</td>
    <td align="center">&nbsp;</td>
    <td width="90" align="center">部门主管：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">人事处：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">财务审核：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">责任人签字：</td>
    <td width="45" align="center">&nbsp;</td>
  </tr>
</table>
<table width="643" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
  </tr>
</table>
<table width="643" border="0" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
  <tr>
    <td width="80" height="30" align="center">总部总经理：</td>
    <td align="center">&nbsp;</td>
    <td width="90" align="center">总部部门主管：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">总部人事处：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">总财务审核：</td>
    <td width="45" align="center">&nbsp;</td>
    <td width="80" align="center">&nbsp;</td>
    <td width="45" align="center">&nbsp;</td>
  </tr>
</table>

<table width="643" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td style="line-height:15px">备注：以上考评等分，每分20元。本工作日记一式二联，人事（白），财务（红）。<br />
    填写方法：1、按每日上班时间，考勤开始计算工作日作基础工资计算；<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、工作内容填主要工作，语言简练；<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、得分类别，选择天相应工资项目；<br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、附件即工作业绩依据；    </td>
  </tr>
</table>
</div>
<script type="text/javascript">
wb.execwb(7,1);
window.close(); 
 </script>
</body>
</html>