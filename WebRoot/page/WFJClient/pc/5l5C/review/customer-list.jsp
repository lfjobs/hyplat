<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, userDTO-scalable=no">
    <script src="https://cdn.tailwindcss.com"></script>
    <title>注册个人</title>
</head>
<body class="h-screen">
<script src="<%=basePath%>/js/dynamic-tab/dynamic-tab.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/dayjs/1.11.13/dayjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/dayjs/1.11.13/plugin/quarterOfYear.min.js"></script>
<script src="https://cdn.sheetjs.com/xlsx-0.20.3/package/dist/xlsx.full.min.js"></script>
<script>dayjs.extend(window.dayjs_plugin_quarterOfYear)</script>
<div class="flex flex-col h-screen">

    <div id="toolbar" class="flex flex-row items-center justify-between w-full">

    </div>

    <div class="p-2">
        <div id="actions-tab" class="space-x-4 text-center h-auto p-2">

        </div>
    </div>

    <div class="flex-grow px-2 overflow-auto bg-white">
        <table class="table-auto scroll-pt-8">
            <thead class="sticky top-0 text-center bg-white">
            <tr class="text-gray-700">
                <th class="text-nowrap p-2">序号</th>
                <th class="text-nowrap p-2">人员编号</th>
                <th class="text-nowrap p-2">姓名</th>
                <th class="text-nowrap p-2">地址</th>
                <th class="text-nowrap p-2">电话</th>
                <th class="text-nowrap p-2">认证</th>
                <th class="text-nowrap p-2">名片</th>
                <th class="text-nowrap p-2">履历</th>
                <th class="text-nowrap p-2">短信</th>
                <th class="text-nowrap p-2">审核小视频</th>
                <th class="text-nowrap p-2">审核资讯</th>
                <th class="text-nowrap p-2">服务咨询</th>
                <th class="text-nowrap p-2">往来关系</th>
                <th class="text-nowrap p-2">个人买卖</th>
                <th class="text-nowrap p-2">会员卡</th>
                <th class="text-nowrap p-2">往来关系</th>
                <th class="text-nowrap p-2">合作项目</th>
                <th class="text-nowrap p-2">收付</th>
                <th class="text-nowrap p-2">合作意向</th>
                <th class="text-nowrap p-2">来源方式</th>
                <th class="text-nowrap p-2">分配服务人</th>
                <th class="text-nowrap p-2">佣金审核</th>
            </tr>
            </thead>
            <tbody id="data-list" class=" divide-y">

            </tbody>
        </table>
        <div class="h-2" id="load-more"></div>
    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var account = "<%=session.getAttribute("key_shop_cus_com")!=null ?
                        ((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>"
</script>
<script src="<%=basePath%>/js/WFJClient/pc/5l5C/market/review/customer-list.js"></script>

</body>
</html>