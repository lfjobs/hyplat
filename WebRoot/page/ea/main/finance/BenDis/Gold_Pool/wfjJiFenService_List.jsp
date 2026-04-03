<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Company c = (Company)session.getAttribute("currentcompany");
    CAccount ca = (CAccount)session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>兑现审核</title>
    <script type="text/javascript" src="<%=basePath%>js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/wfjJiFenService_List.js"></script>
    <script>
        var companyID = '<%=c.getCompanyID()%>';
        var aemail = '<%=ca.getAccountEmail()%>';
        var basePath = "<%=basePath%>";
        var ppageNumber = '${pageNumber}';
        var pageSize = '${pageSize}';
        var sdate='${sdate}';
        var edate='${edate}';
        var search='${search}';
        var token=0;
        var zhxm='${zhxm}'; //帐号或者姓名
        var num='${num}'; //顺序号
        var shr='${shr}'; //审核人
        var payState='${payState}'; //状态
        var erId="";
        var state="";
    </script>
    <style >
    </style>
</head>
<body >
<div class="main_main">
    <table class="flexme11">
        <thead>

        <%--收款账号开户行
	private String recevCardProvince;//收款账号省份
	private String recevCardCity;//收款账号地市
	private String receCardDCode;//收款账号地区码  工行：0200， 其他：0000
	private String recevCardAccount;//收款账号
	private String recevCardName;//收款账号名称
	private String money;//金额
	payMode;//付款方式  0，普通；1，加急；3，跨行快汇  默认为0
	payOperatorName;//审核操作人员姓名
	tradeCode;//第三方交易号，回执单上面的回执单号--%>

        <tr class="tablewith">
            <th width="40" align="center">请选择</th>
            <th width="120" align="center">顺序号</th>
            <th width="50" align="center">兑现人名称</th>
            <th width="100" align="center">微分金账号</th>
            <th width="50" align="center">收款账号省份</th>
            <th width="100" align="center">收款账号地市</th>
            <th width="100" align="center">收款账号地区码</th>
            <th width="100" align="center">收款账号名称</th>
            <th width="180" align="center">收款账号</th>
            <th width="50" align="center">金额</th>
            <th width="50" align="center">付款方式</th>
            <th width="50" align="center">审核操作人员姓名</th>
            <th width="100" align="center">第三方交易号</th>
            <th width="50" align="center">导出次数</th>
            <th width="50" align="center">状态</th>
        </thead>
        <%int number = 1;%>
        <c:forEach items="${pageForm.list}" var="a">
            <tr id="${a[0]}">
            <%--w.wdaID,w.applyDate,s.staffname,t.acount,w.orderNum,w.recevOpenAccountBank,");
            sql.append(" w.recevCardProvince,w.recevCardCity,w.receCardDCode,w.recevCardName,w.recevCardAccount,w.money,w.payMode,");
            sql.append(" w.payOperatorName,w.tradeCode,w.payState--%>
            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${a[0]}" /></td>
            <td><span>${a[4]}</span></td>
            <td><span>${a[2]}</span></td>
            <td><span>${a[3]}</span></td>
            <td><span>${a[6]}</span></td>
            <td><span>${a[7]}</span></td>
            <td><span>${a[8]}</span></td>
            <td><span>${a[9]}</span></td>
            <td><span>${a[10]}</span></td>
            <td><span>${a[11]}</span></td>
            <td><span>
                <c:if test="${a[12]=='0'}">普通</c:if>
                <c:if test="${a[12]=='1'}">加急</c:if>
                <c:if test="${a[12]=='3'}">跨行快汇</c:if>
            </span></td>
            <td><span>${a[13]}</span></td>
            <td><span>${a[14]}</span></td>
            <td><span>${a[16]}</span></td>
            <td><span id="state">
                <c:if test="${a[15]=='00'}">初始状态</c:if>
                <c:if test="${a[15]=='01'}">通过</c:if>
                <c:if test="${a[15]=='02'}">不通过</c:if>
                <c:if test="${a[15]=='03'}">已打款</c:if>
            </span></td>

            <script></script>
        </tr>
        <%  number++; %>
    </c:forEach>

</table>
<c:import url="../../../../../ea/page_navigator.jsp">
    <c:param name="actionPath"
             value="/ea/jinbi/ea_getWdsList.jspa?pageNumber=${pageNumber}
             &sdate=${sdate}&edate=${edate}&zhxm=${zhxm}&num=${num}&shr=${shr}&payState=${payState}">
    </c:param>
</c:import>
<s:token></s:token>

    <form name="SendForm2" id="SendForm2" method="post">
        <div class="jqmWindow"
             style="display: none; width: 300px; height: 110px; right: 40%; top: 10%;"
             id="shyj">
            <input type="submit" name="submit2" style="display: none" />
            <div class="contentbannb">
                <div class="drag">
                    导出
                    <div class="close"></div>
                </div>
            </div>
            <center>
                <table width="100%" id="SearchTable2" cellspacing="15"
                       cellpadding="15">
                    <tr>
                        <td align="right" width='50%'>excel文件每页条数：</td>
                        <td align="left" colspan="2">
                            <input name="pageNum" type="text" id="pageNum" style="width: 100px" value="60"/>
                            <input name="zhxm" type="hidden" value="${zhxm}"/>
                            <input name="num" type="hidden" value="${num}"/>
                            <input name="shr" type="hidden" value="${shr}"/>
                            <input name="payState" type="hidden" value="${payState}"/>
                            <input name="sdate" type="hidden" value="${sdate}"/>
                            <input name="edate" type="hidden" value="${edate}"/>

                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="submitResult2"
                           value=" 提交 " /> <input type="submit" name="submit" style="display:none"/>
                </div>
            </center>
        </div>
    </form>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>