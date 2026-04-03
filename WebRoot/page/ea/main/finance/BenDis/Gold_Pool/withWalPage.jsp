<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>提现记录</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <%--<script src="<%=basePath%>js/ea/marketing/supermarket/giftCard.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var  pageNumber = "${pageNumber}";
        var search='${search}';
    </script>
</head>
<body>
<div style="margin-top:10px;margin-left:10px;"
     class="query"><form id="SearchForm" name="SearchForm" method="post">
    姓名查询：<input type="text" style="width:90px;height:18px;" name="staffName"  />
    账号查询：<input type="text" style="width:90px;height:18px;" name="user"  />
    订单号查询：<input type="text" style="width:90px;height:18px;" name="withDraw.jNumOrder"  />
    提现时间查询：<input type='text' size='10' name='sdate' onfocus='date(this)'/>
    &nbsp;-&nbsp;<input type='test' size='10' name='edate' onfocus='date(this)'/>
    提现方式：<select name="withDraw.withDrawWay" >
                <option value="000">全部</option>
                <option value="01">支付宝</option>
                <option value="02">微信</option>
                <option value="03">银行卡</option>
            </select>
    <input id="toSearch" type="button" class="input-button" value="  查询   "   style="margin:0px;margin-left:5px;" />
</form>

</div>
<div class="main_main">
    <table class="JQueryflexme">
        <thead>
        <tr class="tablewith">
            <th width="40" align="center">序号</th>
            <th width="150" align="center">姓名</th>
            <th width="150" align="center">账号</th>
            <th width="200" align="center">订单号</th>
            <th width="150" align="center">金额</th>
            <th width="100" align="center">提现方式</th>
            <th width="200" align="center">信息</th>
            <th width="150" align="center">时间</th>
            <th width="200" align="center">状态</th>
        </tr>
        </thead>
        <tbody>
        <%
            int number = 1;
        %>
        <s:iterator value="pageForm.list" var="list">
            <tr id="${list[0]}" class="">
                <td><%=number%></td>
                <td><span class="name">${list[9]}</span></td>
                <td><span class="account">${list[8]}</span></td>
                <td><span class="jnumorder">${list[5]}</span></td>
                <td><span class="amount">${list[6]}</span></td>
                <td><span class="amount">${list[2]=="01"?"支付宝":list[2]=="02"?"微信":list[2]=="03"?"银行卡":"无"}</span></td>
                <td><span class="message">${list[7]}</span></td>
                <td><span class="withdrawdate">${list[3]}</span></td>
                <td><span class="state">${list[4]=="00"?"提现成功":list[4]=="01"?"提现成功(生成订单失败)":list[4]=="10"?"提现失败(正常)":list[4]=="11"?"提现失败(异常)":"无"}</span></td>
            </tr>
            <%
                number++;
            %>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/jinbi/ea_WithWalListLimit.jspa?pageNumber=${pageNumber}">
        </c:param>
    </c:import>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
        framespacing="0" height="0"></iframe>
</body>
<script type="text/javascript">
    $(function () {
        $(".jqmWindow").jqm({
            modal : true,
            overlay : 20
            //
        }).jqmAddClose('.close');

        var html =  $(".query").html();
        $(".query").remove();
        $('.JQueryflexme').flexigrid({
            height : 350,
            width : 'auto',
            minwidth : 30,
            title : html,
            minheight : 80,
            buttons : [
                {name : 'EXCEL导出',bclass : 'edit',onpress : action},{separator : true},
                {name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}]
        });

        $("#toSearch").click(function () {
            $("#SearchForm").attr(
                "action",
                basePath + "ea/jinbi/ea_getWithWalPage.jspa?pageNumber=0");
            document.getElementById("SearchForm").submit();
        })
    })

    function action(com, grid) {
        switch (com) {
            case 'EXCEL导出' :
                url = basePath+"/ea/jinbi/ea_showExcelByWal.jspa?"+$("#SearchForm").serialize();
                open(url);
                break;
            case '设置每页显示条数':
                var url = basePath
                    + "ea/jinbi/ea_WithWalListLimit.jspa?"+$("#SearchForm").serialize();
                numback(url);
                break;
        }
    }
</script>
</html>