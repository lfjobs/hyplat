<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title> 现金流水账打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>   
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqChart/excanvas.js"></script>
<script src="<%=basePath%>js/ea/finance/invoicing/voucher/moneyaccount_print.js" type="text/javascript"></script>


<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}
.table td {
	border: 1px solid #C0D9D9;
	height:20px;
}
td span{
text-align: left;display: block;}
</style>
<style media="print">
    #but{
       display:none;
    }
</style>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
 var zz="${param.zz}";
</script>
</head>
<body>
<center>
<br/>
<font size="5"><s:if test="zz==00">现金流水账</s:if>
<s:elseif test="zz==01">银行流水账</s:elseif>
</font>
<div style="width:800px;text-align:right;">
<input type="button" name="but" style="height:25px;" id="but" value="导出Excel文件"/>
</div>
<s:if test="zz==00">
<table width="800" cellspacing="0" cellpadding="0" class="table">
		<tr>
		<td>现金结余汇总表</td>
		<td colspan="2">（按币别，日期排序产生报表）</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td>日期</td>
		<td>科目代号</td>
		<td>科目名称</td>
		<td>币别</td>
		<td>前日余额</td>
		<td>收入金额</td>
		<td>支出金额</td>
		<td>本日结余</td>
		<td align="center"></td>
		<td align="center"></td>
		</tr>
		<c:if test="${fn:length(bcdaylist)>0}">
		<c:forEach items="${bcdaylist}" var="arr">
		   <c:if test="${arr[5]=='B'}">
				<tr class="together">
				<td align="center">&nbsp;
					<span>${arr[0]}</span>
					<input type="hidden" value="${arr[0]}"></td>
				<td align="center">&nbsp;
					<span>${arr[1]}</span>
					<input type="hidden" value="${arr[1]}"></td>
				<td align="center">&nbsp;
					<span>${arr[2]}</span>
					<input type="hidden" value="${arr[2]}"></td>
				<td align="center">&nbsp;
					<span class="codeDe">${arr[3]}</span>
					<input type="hidden" class="currencyid" value="${arr[3]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[4]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[4]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[6]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[6]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[7]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[7]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[8]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[8]}"></td>
				<td align="center"></td>
				<td align="center"></td>
			    </tr>
		  </c:if>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(bcdaylist)==0}">
		<tr><td colspan="10">暂无数据...</td></tr>
		</c:if>
		<tr>
		<td>现金结余明细表</td>
		<td colspan="3">（按币别，日期，凭证流水号排序产生报表）</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td>日期</td>
		<td>科目代号</td>
		<td>科目名称</td>
		<td>币别</td>
		<td>前日余额</td>
		<td>收入金额</td>
		<td>支出金额</td>
		<td>本日结余</td>
		<td>凭证流水号</td>
		<td>摘要</td>
		</tr>
		<c:if test="${fn:length(detailedlist)>0}">
		<c:forEach items="${detailedlist}" var="ar">
		   <c:if test="${ar[5]=='A'}">
				<tr class="detail">
				<td align="center">&nbsp;
					<span>${ar[0]}</span>
					<input type="hidden" value="${ar[0]}"></td>
				<td align="center">&nbsp;
					<span>${ar[1]}</span>
					<input type="hidden" value="${ar[1]}"></td>
				<td align="center">&nbsp;
					<span>${ar[2]}</span>
					<input type="hidden" value="${ar[2]}"></td>
				<td align="center">&nbsp;
					<span class="codeDe">${ar[3]}</span>
					<input type="hidden" class="currencyid" value="${ar[3]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[4]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[4]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[6]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[6]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[7]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[7]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[8]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[8]}"></td>
				<td align="center">&nbsp;
					<span>${ar[9]}</span>
					<input type="hidden" value="${ar[9]}"></td>
				<td align="center">&nbsp;
					<span>${ar[11]}</span>
					<input type="hidden" value="${ar[11]}"></td>
			    </tr>
		  </c:if>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(detailedlist)==0}">
		<tr><td colspan="10">暂无数据...</td></tr>
		</c:if>
	</table>
	</s:if>
	<s:elseif test="zz==01">
	<table width="800" cellspacing="0" cellpadding="0" class="table">
		<tr>
		<td>银行结余汇总表</td>
		<td colspan="2">（按币别，银行账户，日期排序）</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td>日期</td>
		<td>银行账号</td>
		<td>银行开户行名称</td>
		<td>开户人</td>
		<td>币别</td>
		<td>会计科目</td>
		<td>科目名称</td>
		<td>前日余额</td>
		<td>收入金额</td>
		<td>支出金额</td>
		<td>本日结余</td>
		<td></td>
		<td></td>
		</tr>
		<c:if test="${fn:length(bcdaylist)>0}">
		<c:forEach items="${bcdaylist}" var="arr">
		   <c:if test="${arr[14]=='B'}">
				<tr class="together">
				<td align="center">&nbsp;
					<span>${arr[0]}</span>
					<input type="hidden" value="${arr[0]}"></td>
				<td align="center">&nbsp;
					<span>${arr[1]}</span>
					<input type="hidden" value="${arr[1]}"></td>
				<td align="center">&nbsp;
					<span>${arr[2]}</span>
					<input type="hidden" value="${arr[2]}"></td>
				<td align="center">&nbsp;
					<span>${arr[3]}</span>
					<input type="hidden" value="${arr[3]}"></td>
				<td align="center" >&nbsp;
					<span class="codeDe">${arr[4]}</span>
					<input type="hidden" class="currencyid" value="${arr[4]}"></td>
				<td align="center">&nbsp;
					<span>${arr[5]}</span>
					<input type="hidden" value="${arr[5]}"></td>
				<td align="center">&nbsp;
					<span>${arr[6]}</span>
					<input type="hidden" value="${arr[6]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[7]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[7]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[8]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[8]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[9]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[9]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${arr[10]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${arr[10]}"></td>
				<td align="center"></td>
				<td align="center"></td>
			    </tr>
		  </c:if>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(bcdaylist)==0}">
		<tr><td colspan="13">暂无数据...</td></tr>
		</c:if>
		<tr>
		<td>银行结余明细表</td>
		<td colspan="3">（按币别，银行账户，日期，凭证流水号排序）</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		<tr>
		<td>日期</td>
		<td>银行账号</td>
		<td>银行开户行名称</td>
		<td>开户人</td>
		<td>币别</td>
		<td>会计科目</td>
		<td>科目名称</td>
		<td>前日余额</td>
		<td>收入金额</td>
		<td>支出金额</td>
		<td>本日结余</td>
		<td>凭证流水号</td>
		<td>摘要</td>
		</tr>
		<c:if test="${fn:length(bcdaylist)>0}">
		<c:forEach items="${detailedlist}" var="ar">
		   <c:if test="${ar[14]=='A'}">
				<tr class="detail">
				<td align="center">&nbsp;
					<span>${ar[0]}</span>
					<input type="hidden" value="${ar[0]}"></td>
				<td align="center">&nbsp;
					<span>${ar[1]}</span>
					<input type="hidden" value="${ar[1]}"></td>
				<td align="center">&nbsp;
					<span>${ar[2]}</span>
					<input type="hidden" value="${ar[2]}"></td>
				<td align="center">&nbsp;
					<span>${ar[3]}</span>
					<input type="hidden" value="${ar[3]}"></td>
				<td align="center">&nbsp;
					<span class="codeDe">${ar[4]}</span>
					<input type="hidden" class="currencyid" value="${ar[4]}"></td>
				<td align="center">&nbsp;
					<span>${ar[5]}</span>
					<input type="hidden" value="${ar[5]}"></td>
				<td align="center">&nbsp;
					<span>${ar[6]}</span>
					<input type="hidden" value="${ar[6]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[7]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[7]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[8]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[8]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[9]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[9]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[10]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[10]}"></td>
				<td align="center">&nbsp;
					<span>${ar[11]}</span>
					<input type="hidden" value="${ar[11]}"></td>
				<td align="center">&nbsp;
					<span><fmt:formatNumber value="${ar[12]}" pattern="#,##0.00"/></span>
					<input type="hidden" value="${ar[12]}"></td>
			    </tr>
		  </c:if>
		</c:forEach>
		</c:if>
		<c:if test="${fn:length(bcdaylist)==0}">
		<tr><td colspan="13">暂无数据...</td></tr>
		</c:if>
	</table>
	</s:elseif>
	</center>
</body>
</html>
