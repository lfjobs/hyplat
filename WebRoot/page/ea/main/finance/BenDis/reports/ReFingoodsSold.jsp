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
    <title>商品销售成本报表</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/reports/laydate.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/reports/ReFingoodsSold.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/finance/BenDis/reports/laydate.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/finance/BenDis/reports/grossProState.js" type="text/javascript" charset="utf-8"></script>
    <script>
        var basePath = "<%=basePath%>";
        var ppageNumber = '${pageNumber}';
        var reportType = '${reportType}';
    </script>
</head>
<body>
<header>
    <div class="box clearfix">
        <h2>商品销售成本报表</h2>
        <section>
            <input type="button" name="" id="export" value="导出" />
            <div class="clearfix">
                <p>消费总人数：<span id="totalCons"></span>人</p>
                <p>总订单：<span id="totalOrder"></span>个</p>
                <p>总数量：<span id="totalNumber"></span>个</p>
                <p>成本总金额：<span id="refinAmount"></span>元</p>
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
							<input type="text" name="timeInterval" id="test1" value=""/>
            </p>
            <p>
                <label for="">条码/名称</label>
                <input type="text" name="product.barCode"  value="" />
            </p>
            <p>
                <label for="">订单号</label>
                <input type="text" name="cashierBills.journalNum"  value="" />
            </p>
            <p>
                <label for="">供应商</label>
                <input type="text" name="product.ccompanyName" value="" />
            </p>
            <input type="button" id="toSch" value="搜索"/>
        </form>
    </section>
    <div>
        <table border="0" cellspacing="0" cellpadding="0" id="reportList">
            <tr>
                <td>序号</td>
                <td>名称</td>
                <td>条码</td>
                <td>单位</td>
                <td>成本单价</td>
                <td>数量</td>
                <td>金额</td>
                <td>订单数</td>
                <td>消费人数</td>
                <td>供应商</td>
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

