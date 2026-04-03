<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>凭证管理树</title>
    <%@ page language="java" pageEncoding="UTF-8"%>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
          type="text/css" />
    <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet"
          href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
    <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
    <script src="<%=basePath%>/js/ea/finance/Navigation/finace_na.js"></script>

    <script type="text/javascript">

        var basePath='<%=basePath%>';
    </script>
    <style type="text/css">

        #qh_sw {
            width: 15%;
            border: 1px solid #DAE7F6;
        }

        .treeview li {
            margin: 0;
            padding: 1px 0 1px 16px;
        }
    </style>
</head>
<body>
<div class="main_main">
    <table width="100%" cellspacing="0" cellpadding="0" "border="2">
    <tr>
        <td id="qh_sw" style="width: 15%" valign="top">
            <div class="qh_gg_nav">
                &nbsp;
                <span id="frametitle">报表</span>

            </div>
            <!--左边的树 -->
            <ul id="navigation" style="width: 200px;"
                class="filetree">
                <li>
                    <span class="folder">报表</span>
                    <ul>
                        <li>
                            <span class="folder" id="pzgl">销售报表</span>
                            <ul>
                                <li>
                                    <a target="_blank" href="<%=basePath%>/ea/reports/ea_getGorssProState.jspa?reportType=gross"><span class="file">毛利润报表</span></a>
                                </li>
                                <li>
                                    <a target="_blank" href="<%=basePath%>/ea/reports/ea_getReFingoodsSold.jspa?reportType=refin"><span class="file">商品销售成本报表</span></a>
                                </li>
                                <li>
                                    <a target="_blank" href="<%=basePath%>/ea/reports/ea_getSalesOrder.jspa?reportType=order"><span class="file">销售订单报表</span></a>
                                </li>
                                <li>
                                    <a target="_blank" href="<%=basePath%>/ea/reports/ea_getSalesRevenue.jspa?reportType=sales"><span class="file">销售收入报表</span></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </td>
        <td style="width: 85%;" valign="top">
            <iframe id="mainframe"
                    frameborder="0" style="width: 100%;"></iframe>
        </td>
    </tr>
    </table>
</div>
<script type="text/javascript">
    $(function(){
        $(window).resize(function(){
            setTimeout(function () {
                $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
            },100);
        });
        $("#navigation").height($(window).height()- 30);
        $("#mainframe").css({"height" : $(window).height()+ "px"});
    });
</script>
</body>


</html>
