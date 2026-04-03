<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>工作日志汇总列表1</title>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        
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
        
         <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
	</head>
	<body style="height: 100%;position: absolute;overflow: scroll;width: 700px;left: 50%;margin-left: -350px;" >
	<s:iterator value="logBookPrintVoList">
	<div style="height: 500px;overflow: hidden;">
	<table width="643" border="0" align="center" cellpadding="0" cellspacing="0" height="35" style="margin-top: 15px;">
	  <tr>
	    <td width="55%" align="right"><strong>工作日记及当日工资</strong> </td>
	    <td width="45%" align="right"><a style="color: red;font-size: 20px;">${LokBookPrintVOID}</a></td>
	  </tr>
	</table>
	<table width="643" height="30"  border="0" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
	  <tr>
	    <td width="50" height="30" align="right">公司：</td>
	    <td width="250">${companyName}</td>
	    <td width="40" align="right">部门：</td>
	    <td width="40">&nbsp;</td>
	    <td width="80" align="right">责任人：</td>
	    <td width="80">${staffName }</td>
	    <td width="80" align="right">人员编号：</td>
	    <td width="60">&nbsp;</td>
	    <td width="40" align="right">日期：</td>
	    <td width="75">${fn:substring(todaydate, 0, 10)}</td>
	  </tr>
	</table>
<div style="height: 158px;">
<table width="643"  align="center" cellpadding="0" cellspacing="0" class="table">
  <tr>
    <th width="20" height="20" align="center" bgcolor="#E4F1FA">序号</th>
    <th width="40" align="center" bgcolor="#E4F1FA">起时间</th>
    <th width="40" align="center" bgcolor="#E4F1FA">止时间</th>
    <th width="200" align="center" bgcolor="#E4F1FA">完成工作内容</th>
    <th width="90" align="center" bgcolor="#E4F1FA">得分类别</th>
    <th width="35" align="center" bgcolor="#E4F1FA">应得<br />
    分数</th>
    <th width="35" align="center" bgcolor="#E4F1FA">扣分</th>
    <th width="35" align="center" bgcolor="#E4F1FA">实际<br />
    得分</th>
    <th width="95" align="center" bgcolor="#E4F1FA">附件类别及名称</th>
    <th width="60" align="center" bgcolor="#E4F1FA">领导意见</th>
  </tr>	
  <%
                    int number = 1; %>
      <c:set var="title" value="0"></c:set>
	 <s:iterator value="logbookList">
	  <tr>
	    <td height="20" align="center" bgcolor="#FFFFFF"><%=number%></td>
	    <td align="center" bgcolor="#FFFFFF">${startdate }</td>
	    <td align="center" bgcolor="#FFFFFF">${enddate }</td>
	    <td align="center" bgcolor="#FFFFFF">${jobContent }</td>
	    <td align="center" bgcolor="#FFFFFF">${scoreSortName }</td>
	    <c:if test="${scoreSortName=='月考评'}">
	    	<c:set var="title" value="1"></c:set>
	    </c:if>
	    <td align="center" bgcolor="#FFFFFF"><%--${bisect }--%></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF"><%--${bisect }--%></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    </tr>
	     <%
                        number++; %>
    </s:iterator>
     <tr>
	    <td height="20" align="center" bgcolor="#FFFFFF"><%=number%></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF">得分小计</td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    <td align="center" bgcolor="#FFFFFF">${bisects}</td>
	    <td align="center" bgcolor="#FFFFFF">工资小计<a style="color: red;">${momey }</a>元</td>
	    <td align="center" bgcolor="#FFFFFF"></td>
	    </tr>
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
    <td width="80" align="center"><c:if test="${title=='0'}">责任人签字：</c:if> </td>
    <td width="45" align="center">&nbsp;</td>
  </tr>
</table>
<table width="643" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr></tr>
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
     <td style="line-height:15px">备注：以上考评等分，每分20元。本工作日记一式二联，人事（白），财务（红）。<br>
    填写方法：1：每日在下午6点至次日8：30前填写本日工作日志<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2：每日志必须真实，工作内容必须要工作计划，填写当日个工作进度<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3：造假扣每项目5分，主管10分 经理15分当日无业绩计算<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4：超时24小时填写，超时一日扣1分<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
5：不填写，工资无法计算，按无业绩和未上班计算<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
6：填写日志，无工作业绩，按无业绩计算<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
7：部门主管按日必须审核，每日审核必在本日上午11点至下午3点，下午6点<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至次日8：30间审核，延时审核主管扣2分，管理5分<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
8：人事办公室每日工作日清，主要人事工作，办公室工作汇总 各部门日清未<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;清按日清，财务部要做账目资金日清，不清每日扣1分，主管扣2分 经理5分</td>
  </tr>
</table>
</div>
</s:iterator>
<script type="text/javascript">
wb.execwb(7,1);
window.close(); 
 </script>
</body>
</html>