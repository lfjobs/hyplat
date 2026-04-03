<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>销售订单报表</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/reports/laydate.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/reports/salesOrder.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/finance/BenDis/reports/laydate.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/finance/BenDis/reports/salesOrder.js" type="text/javascript" charset="utf-8"></script>
    <script>
        var basePath = "<%=basePath%>";
        var ppageNumber = '${pageNumber}';
        var reportType = '${reportType}';
    </script>
</head>
<body>
<header>
    <div class="box clearfix">
        <h2>销售订单报表</h2>
        <section>
            <input type="button" name="" id="export" value="导出" />
            <div class="clearfix">
                <p>消费总人数：<span id="totalCons"></span>人</p>
                <p>总订单：<span id="totalOrder"></span>个</p>
                <%--<p>总数量：<span id="totalNumber"></span>个</p>--%>
                <p>销售总金额：<span id="totalAmount"></span>元</p>
            </div>
        </section>
    </div>
</header>
<div class="load">
    <section>
        <div>
            <img src="<%=basePath%>/images/ea/lottery/loading.gif" style="height: 45px;width: 45px; margin-top: 7px;" />
            <p>加载中，请等待...</p>
        </div>
    </section>
</div>
<div class="content">
    <section class="clearfix">
        <form id="schForm" action="" method="post">
            <p>
                <label for="">日期</label>
							<input type="text" name="timeInterval" id="test1" value="" />
            </p>
            <p>
                <label for="">用户账号/用户姓名</label>
                <input type="text" name="accountOrName"  value="" />
            </p>
            <p>
                <label for="">订单号</label>
                <input type="text" name="cashierBills.journalNum" value="" />
            </p>
            <p>
                <label for="">操作人</label>
                <input type="text" name="cashierBills.inputName"  value="" />
            </p>
            <input type="button" id="toSch" value="搜索"/>
        </form>
    </section>
    <div>
        <table border="0" cellspacing="0" cellpadding="0" id="reportList">
            <tr>
                <td>序号</td>
                <td>订单号</td>
                <td>用户账号</td>
                <td>用户姓名</td>
                <td>操作人</td>
                <td>销售金额</td>
                <td>支付方式</td>
                <td>支付时间</td>
            </tr>
        </table>
    </div>
    <footer class="limit">
    </footer>
</div>
</body>
<script type="text/javascript">
    $("footer div a").not(":first-of-type,:last-of-type").click(function(){
        $(this).parent().children("a").removeClass("active");
        $(this).addClass("active");
    })
    laydate.render({
        elem: '#test1'
        ,type: 'datetime'
        ,range: '至'
        ,format: 'yyyy-MM-dd HH:mm:ss'
        ,theme: '#393D49'
    });
</script>
</html>

