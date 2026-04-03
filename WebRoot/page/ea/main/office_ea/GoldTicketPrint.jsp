<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>金币折扣单</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <link  rel="stylesheet" type="text/css" href="<%=basePath %>/css/ea/human/style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.1.1.min.js"></script>
</head>
<body>
<div id="apDiv1" style="position:absolute;width:63px;height:32px;z-index:1;"></div>
<div class="contion" align="center">
    <div class="gold_ticket">
        <h2>金币折扣单</h2>
        <div class="txt">
            <div class="one">
                <div class="com">
                    <h4>公司：</h4>
                    <p>${company.companyName}</p>
                </div>
                <div class="sim">
                    <h4>制单人：</h4>
                    <p>${publicreceipts.operator}</p>
                </div>
                <div class="sim">
                    <h4>帐号名称：</h4>
                    <p>${st[1]}</p>
                </div>
            </div>
            <div class="one">
                <div class="com">
                    <h4>部门：</h4>
                    <p>${organizationName}</p>
                </div>
                <div class="sim">
                    <h4>制单日期：</h4>
                    <p>${publicreceipts.applyDate}</p>
                </div>
                <div class="sim">
                    <h4>帐号：</h4>
                    <p>${st[0]}</p>
                </div>
            </div>
            <div class="three">
                <div class="person">
                    <h4>责任人：</h4>
                    <p>${publicreceipts.principal}</p>
                </div>
                <div class="proof">
                    <h4>凭证号：</h4>
                    <p>${publicreceipts.voucherNum}</p>
                </div>
            </div>
        </div>

        <table border="0" cellspacing="0" cellpadding="2" class="tab">
            <tr>
                <td>单据类型</td>
                <td>${publicreceipts.type}</td>
            </tr>
            <tr>
                <td>折扣原因</td>
                <td>${publicreceiptsChild.fineReason}</td>
            </tr>
            <tr>
                <td>折扣数量（金币）</td>
                <td>${publicreceiptsChild.fineCount}</td>
            </tr>
        </table>
        <div class="btm">
            <h5 style="text-indent: 1em;">公司经理：${publicreceipts.finalAuditor}</h5>
            <h5 style="text-indent: 2em;">部门主管：${publicreceipts.firstAuditor}</h5>
            <h5 style="text-indent: 2em;">人事处：${publicreceipts.secondAuditor}</h5>
            <h5 style="text-indent: 1em;">财务审核：</h5>
            <h5 style="width: 130px;">责任人签字：</h5>

            <h5>总部总经理：</h5>
            <h5>总部部门主管：</h5>
            <h5>总部人事处：</h5>
            <h5>总财务审核：</h5>
        </div>
    </div>

</div>
</body>
<script type="text/javascript">
    $(function(){
        var height = $(".contion").height();
        var basePath = "<%=basePath%>";
        var str = '';
        var st = '${publicreceipts.receiptsStatus}';
        if (st == "R") {
            str = "<img src='" + basePath + "images/ea/finance/zuofei.png'/>";
        } else if (st == "S") {
            str = "<img src='" + basePath + "images/ea/finance/yishen.png'/>";
        } else if (st == "P"||st == "F"){
            str = "<img src='" + basePath + "images/ea/finance/daishen.png'/>";
        }
        $("#apDiv1").html(str);
        $("#apDiv1").css({
            'top' : height - 50
        });
        $("#apDiv1").css({
            'left' : 500
        });
    });
</script>
</html>