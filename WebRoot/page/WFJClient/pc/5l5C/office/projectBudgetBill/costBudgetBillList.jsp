<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
    String personalId = (String)session.getAttribute("personalId");
    String tenant = (String)session.getAttribute("tenantFlag");
    String menuName = (String)session.getAttribute("menuName");
%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>采购销售订单</title>
    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetBasicStyle.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pm_base.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/WFJClient/pc/5l5C/office/costBudgetItemAdd.css">
    <script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
    <script src="<%=basePath %>js/ea/budgetBill/javascript.js" type="application/javascript"></script>
    <%--异步获取当前登录人所在菜单--%>
    <script type="text/javascript" src="<%=basePath%>js/ea/projectBudgetBill/menuForMobileUtil.js"></script>
    <%--异步上拉加载js文件--%>
    <script type="text/javascript" src="<%=basePath%>js/ea/projectBudgetBill/costBudgetList.js">
    </script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/diyUpload.js"></script>
</head>
<style>
    .bug-nav li {
        padding: 0 0.5rem;
    }
    .bug-nav {
        font-size: 0.5rem;
    }
    .bug-nav2 {
        overflow: hidden;
        background-color: #f2f2f2;
        color: #222;
        font-size: 0.5rem;
        padding: 0 0.5rem;
        width: 30rem;
    }
    .bug-nav2 li {
        margin-right: 1rem;
        height: 1.5rem;
        line-height: 1.5rem;
        float: left;
        text-align: center;
    }
    /* 模态背景 */
    .selectModal {
        display: none; /* 默认隐藏 */
        position: fixed; /* 固定定位 */
        z-index: 1; /* 置于顶层 */
        left: 0;
        top: 0;
        width: 100%; /* 全宽 */
        height: 100%; /* 全高 */
        overflow: auto; /* 启用滚动 */
        background-color: rgb(0,0,0); /* 背景颜色 */
        background-color: rgba(0,0,0,0.4); /* 背景颜色半透明 */
    }

    /* 模态内容框 */
    .selectModal-content {
        background-color: #fefefe;
        margin: 50% auto; /* 15% 从顶部和自动水平居中 */
        padding: 20px;
        border: 0px solid #888;
        width: 80%; /* 可根据需要调整宽度 */
        position: relative;
        height: 5rem;
    }

    /* 关闭按钮 */
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }
    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    .bug-nav-box {
        height: 2rem;
        line-height: 2rem;
    }

    .orderModal {
        display: none; /* 默认隐藏 */
        position: fixed; /* 固定定位 */
        z-index: 1; /* 置于顶层 */
        left: 0;
        top: 0;
        width: 100%; /* 全宽 */
        height: 100%; /* 全高 */
        overflow: auto; /* 启用滚动 */
        background-color: rgb(0,0,0); /* 背景颜色 */
        background-color: rgba(0,0,0,0.4); /* 背景颜色半透明 */
    }

    /* 模态内容框 */
    .orderModal-content {
        background-color: #fefefe;
        margin-top: 5.6rem;
        border: 0px solid #888;
        width: 100%;
        position: relative;
        height: 100%;
    }

    .tab_item_box .tab_item{
        display: flex;
        padding-left: 1.2rem;
        justify-content: space-between;
        align-items: center;
        padding-right: 0.1rem;
    }
    .tab_item_box .tab_item span{
        height: 2.2rem;
        line-height: 2.2rem;
        color: #232323;
        font-size: .7rem;
    }
    .tab_item_box .tab_item i{

    }
</style>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var treeid = "<%= paramMap==null ? "" : paramMap.get("companyId")%>";//公司id
    var pageCount = ${pageForm.pageCount==null?0:pageForm.pageCount};
    var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
    var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
    var pageNumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
    var departmentId = "${departmentID}";//部门id，标识用于判断是那个菜单
    var showFlag = ${showFlag};//false查看总列表true查看分列表
    var departmentName = "${departmentName}";//所选部门名称
    var search = "${search}";//模糊查询条件
    var searchType = ${searchType};//模糊查询条件类型
    var staffId = "<%= null != personalId ? personalId : paramMap != null ? paramMap.get("staffId") : ""%>";//登录人id
    var companyid = "${companyid}";
    var menuId = "${menuId}";
    var tenantFlag = "${tenantFlag}";
    if(!tenantFlag){
        tenantFlag = "${tenant}";
    }
    var cashierBillsId = "${cashierBillsId}";
    <%--var sccid = "${sccid}";--%>
    var sccid = "<%= paramMap != null ? paramMap.get("sccId") : ""%>";
    var menuName = "${menuName}";
    if(menuName){
        menuName = "<%= menuName %>"
    }
    var billsType = "${billsType}";
    var menuPage = "";
    var searchParams = new URLSearchParams(window.location.search);
    var params = new Map();
    searchParams.forEach(function(value, key) {
        params.set(key,value);
    });
    if(params.size > 0){
        if(null != params.get("menuPage")){
            menuPage = params.get("menuPage");
        }
    }
</script>
<body>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">
    <div class="div-tupian2" style="opacity: 0; transform: translate(1000000px);">
        <div class="div-box">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
            </ul>
        </div>
        <div class="div-con">
            <div class="div-tab">
                <div class="div-01">
                    <div class="demo">
                        <div id="as"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<ul class="header" style="background-color: #f74c32;color: #fff;">
    <li onclick="toBack()">
        <img src="<%=basePath %>images/WFJClient/pc/5l5c/img_03.png" alt="">
    </li>
    <li id="title">采购销售订单</li>
</ul>
<div class="bug-nav-box" id="ttsw_one_menu_id"></div>
<div>
    <ul class="bug-nav2">
<%--        <li onclick="order();">采购销售订单</li>--%>
        <li onclick="toAdd();" id="add">添加</li>
        <li onclick="toUpdate();">修改</li>
        <li onclick="circularize();">传阅</li>
        <li onclick="delCostSheet();">删除</li>
        <li onclick="printDetail();">打印</li>
    </ul>
</div>
<div>
    <ul class="bug-nav2">
        <li onclick="importOrder();" id="import">导入</li>
        <li id="importImage">导入图片</li>
        <li onclick="check();" id="check"></li>
        <li onclick="inbound();" id="inboundOper">入库</li>
        <li onclick="select();">筛选</li>
    </ul>
</div>
<div class="bug-con  main_hide" id="phoneDiv">
    <input type="file" id="excelFile" accept=".xlsx,.xls" style="display: none">
    <ul class="tj_con">
    </ul>
</div>
<!-- 模态对话框的容器 -->
<div id="selectModal" class="selectModal">
    <!-- 模态内容 -->
    <div class="selectModal-content">
        <p style="font-size: 16px;">选择查询状态</p>
        <div style="position: relative;width:280px;left:20px;top:35px;">
            <button onclick="buttonClicked('unapproved')" style="width:80px;height: 30px;border: 0px;background-color: #f74c32;color: #fff;font-size: 15px;">未审核</button>
            <button onclick="buttonClicked('reviewed')" style="width:80px;height: 30px;border: 0px;background-color: #f74c32;color: #fff;margin-left: 4px;font-size: 15px;">已审核</button>
            <button onclick="buttonClicked('all')" style="width:80px;height: 30px;border: 0px;background-color: #f74c32;color: #fff;margin-left: 4px;font-size: 15px;">全部</button>
        </div>
    </div>
</div>
<div id="orderModal" class="orderModal">
    <!-- 模态内容 -->
    <div class="orderModal-content">
        <div class="wrap_page" style="width: 100%;margin-top: 0;">
            <section class="order_tab_wrap">
                <div class="order_tab clearfix">
                    <div class="order_tab_box order_tab_cur" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">采购订单</div>
                    <div id="sell" class="order_tab_box sell" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">销售订单</div>
                    <div class="order_tab_box other" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">派单抢单</div>
                </div>
            </section>
            <section class="tab_con">
                <div class="tab_con_box">
                    <div class="tab_item_box" style="font-size: 15px;">
                        <div id="myOrder" class="tab_item order_m_box4 bdd">
                            <span style="font-size: 15px;">订单管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="init" class="tab_item">
                            <span style="font-size: 15px;">初始项目管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="buy" class="tab_item">
                            <span style="font-size: 15px;">采购管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="inspection" class="tab_item">
                            <span style="font-size: 15px;">验货管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="inbound" class="tab_item">
                            <span style="font-size: 15px;">入库管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="checkInventory" class="tab_item">
                            <span style="font-size: 15px;">盘库管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="lossReported" class="tab_item">
                            <span style="font-size: 15px;">误差管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item">
                            <span style="font-size: 15px;">有效期管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="companyReturn" class="tab_item order_m_box4">
                            <span style="font-size: 15px;">退货管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div id="myReturn" class="tab_item order_m_box4 bth">
                            <span style="font-size: 15px;">退货管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item">
                            <span style="font-size: 15px;">库存量管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item order_m_box4">
                            <span style="font-size: 15px;">退款管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                    </div>
                </div>
            </section>
            <section id="sellSection" class="tab_con">
                <div class="tab_con_box">
                    <div class="tab_item_box">
                        <div class="tab_item sdd">
                            <span style="font-size: 15px;">订单管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item xbdd">
                            <span style="font-size: 15px;">发货管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item">
                            <span style="font-size: 15px;">库存管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item sth">
                            <span style="font-size: 15px;">退货管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item xbdd">
                            <span style="font-size: 15px;">物流管理</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item zdjysz">
                            <span style="font-size: 15px;">针对性交易设置</span>
                            <i class="order_arr_R"></i>
                        </div>
                    </div>
                </div>
            </section>
            <section class="tab_con">
                <div class="tab_con_box">
                    <div class="tab_item_box">
                        <div class="tab_item">
                            <span style="font-size: 15px;">发单</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item">
                            <span style="font-size: 15px;">抢单</span>
                            <i class="order_arr_R"></i>
                        </div>
                        <div class="tab_item">
                            <span style="font-size: 15px;">派单</span>
                            <i class="order_arr_R"></i>
                        </div>
                    </div>
                </div>
            </section>
<%--            <section class="tab_con">--%>
<%--                <div class="tab_con_box">--%>
<%--                    <div class="tab_item_box">--%>
<%--                        <div id="reimbursement" class="tab_item">--%>
<%--                            <span style="font-size: 15px;">报销单</span>--%>
<%--                            <i class="order_arr_R"></i>--%>
<%--                        </div>--%>
<%--                        <div id="inMoney" class="tab_item">--%>
<%--                            <span style="font-size: 15px;">收入单</span>--%>
<%--                            <i class="order_arr_R"></i>--%>
<%--                        </div>--%>
<%--                        <div id="outMoney" class="tab_item">--%>
<%--                            <span style="font-size: 15px;">支出单</span>--%>
<%--                            <i class="order_arr_R"></i>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </section>--%>
        </div>
    </div>
</div>


</body>

<script>
    var orderModal = document.getElementById("orderModal");
    var selectModal = document.getElementById("selectModal");

    function toAdd() {
        var url = "ea/scBudget/ea_toAddProjectBill.jspa";
        var parameter = "?showFlag="+showFlag+"&companyid="+companyid+"&menuId="+menuId+"&departmentName="+menuName+"&billsType="+billsType;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+menuName;
        }
        window.location.href = basePath + url + parameter;
    }

    function circularize(op) {
        //循环获取选中的值
        var id = "";
        var status = "";
        var invName = "";
        var billsType = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
            status = $(this).attr("cashierBillsStatus");
            invName = $(this).attr("invName");
            billsType = $(this).attr("billsType");
        });
        if (id != "" && id != null) {
            if ("00" != status && op != "approval") {
                alert("此单已传阅不能再次传阅！");
                return;
            }
            if ("07" == status && op == "approval") {
                alert("此单已审核不能审核！");
                return;
            }
            if ("15" == status && op == "approval") {
                alert("入库单已入库不能审核！");
                return;
            }
            if("入库单" == billsType || "出库单" == billsType || "盘库单" == billsType || "报损单" == billsType){
                if ((invName == null || invName == "") && op != "approval") {
                    alert("入库单没有设置仓库不能传阅！");
                    return;
                }
                if ((invName == null || invName == "") && op == "approval") {
                    alert("入库单没有设置仓库不能审核！");
                    return;
                }
            }
            window.location.href = basePath + "/page/WFJClient/pc/5l5C/office/projectBudgetBill/selectCompany.jsp?type=cy&keyId=" + id + "&billsType=" + billsType;
        } else {
            if(op == "approval"){
                alert("请选择要审核的数据");
            }else{
                alert("请选择要传阅的数据");
            }
        }
    }

    //转订单类型
    function check() {
        var operation = "";
        var id = "";
        var status = "";
        if (billsType == "采购单") {
            operation = "验货";
        } else if (billsType == "验货单") {
            operation = "入库";
        } else if (billsType == "盘库单") {
            operation = "报损";
        }
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
            status = $(this).attr("cashierBillsStatus");
        });
        if (id != "" && id != null) {
            if ("07" != status) {
                alert("此单未审核不能" + (operation == "入库" ? "转入库单" : operation) + "！");
                return;
            }
            var url = basePath + "ea/scBudget/sajax_ea_changeBillsType.jspa";
            $.ajax({
                url: url,
                type: "post",
                async: false,
                dataType: "json",
                data: {
                    "billsTypeName": operation + "单",
                    "cashierBillsId": id
                },
                success: function (data) {
                    var map = eval("(" + data + ")");
                    var result = map.result;
                    if ("error" == result) {
                        alert("转" + operation + "单失败！");
                        return;
                    }
                    alert("转" + operation + "单成功！");
                    window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?billsType=" + billsType;
                },
                error: function () {
                    alert("转" + operation + "单失败！");
                }
            });
        } else {
            alert("请选择要" + (operation == "入库" ? "转入库单" : operation) + "的数据");
            return false;
        }
    }

    function inbound(){
        var result = true;
        var selectedItems = $(".aside_yes");
        if (selectedItems.length === 0) {
            alert("请先选择要入库的数据");
            return;
        }
        $(".aside_yes").each(function () {
            var status = $(this).attr("cashierBillsStatus");
            if ("15" == status) {
                alert("入库单已入库不能入库！");
                result = false;
                return;
            }
            if ("07" != status) {
                alert("入库单未审核不能入库！");
                result = false;
                return;
            }
            var invName = $(this).attr("invName");
            if (invName == null || invName == "") {
                alert("入库单没有设置仓库不能入库！");
                result = false;
                return;
            }
        });
        if(!result){
            return;
        }
        // 获取选中的数据ID
        var selectedIds = [];
        selectedItems.each(function () {
            var id = $(this).attr("checkCasId");
            selectedIds.push(id);
        });
        var url = basePath + "ea/scBudget/sajax_ea_ajaxUpdateWareManagement.jspa";
        $.ajax({
            url: url,
            type: "post",
            async: false,
            dataType: "json",
            data: {
                "selectedItems": JSON.stringify(selectedIds)
            },
            success: function (data) {
                var result = "";
                var map = eval("(" + data + ")");
                if(null != map){
                    result = map.result;
                }
                if ("OK" != result) {
                    alert("入库失败！");
                    return;
                }
                alert("入库成功！");
                window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?billsType=" + billsType;
            },
            error: function () {
                alert("入库失败！");
            }
        });
    }

    //跳转详情
    function toDetail(id) {
        var url = "ea/scBudget/ea_toProjectBudgetDetail.jspa";
        var parameter = "?showFlag=" + showFlag + "&menuId=" + menuId + "&cashierBillsId=" + id + "&billsType=" + billsType;
        if (showFlag) {//单独部门传部门名称过去
            parameter += "&departmentName=" + departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //跳转详情
    function printDetail() {
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });
        if (id != "" && id != null) {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            if (isAndroid || isiOS) {
                alert("请在电脑端进行打印操作");
                return;
            }
            var url = "ea/scBudget/ea_toProjectBudgetDetail.jspa";
            var parameter = "?pageType=printPage" + "&cashierBillsId=" + id;
            window.open(basePath + url + parameter);
        } else {
            alert("请选择要打印的数据");
            return false;
        }
    }

    function toUpdate(){
        //循环获取选中的值
        var id = "";
        var status = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
            status = $(this).attr("cashierBillsStatus");
        });
        if(id != "" && id != null){
            if ("07" == status || "15" == status) {
                alert("此单已审核或已入库不能修改！");
                return;
            }
            var url = "ea/scBudget/ea_toUpdateProjectBill.jspa";
            var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+id+"&menuId="+menuId+"&billsType="+billsType;
            if(showFlag){//单独部门传部门名称过去
                parameter += "&departmentName="+departmentName;
            }
            window.location.href = basePath + url + parameter;
        }else{
            alert("请选择要修改的数据");
            return false;
        }
    }

    //删除订单
    function delCostSheet() {
        //循环获取选中的值
        var id = "";
        var status = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
            status = $(this).attr("cashierBillsStatus");
        });
        if (id != "" && id != null) {
            if ("07" == status || "15" == status) {
                alert("此单已审核或已入库不能删除！");
                return;
            }
            var result = confirm("确定删除该数据吗？");
            if (result == true) {
                var url = basePath + "ea/scBudget/sajax_ea_delProjectCostBudgetSheet.jspa";
                $.ajax({
                    url: url,
                    type: "post",
                    async: true,
                    dataType: "json",
                    data: {
                        "cashierBillsId": id
                    },
                    success: function (data) {
                        var map = eval("(" + data + ")");
                        window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?menuId="+menuId+"&billsType=" + billsType;
                    },
                    error: function () {
                        alert("删除该数据失败！");
                    }
                });
            }
        } else {
            alert("请选择要删除的数据");
            return false;
        }
    }

    function importOrder() {
        document.getElementById('excelFile').click();
    }

    //后退
    function toBack() {
        if (tenantFlag == "personal") {
            window.location.href = basePath + "ea/mycenter/ea_myIndex.jspa";
            // window.location.href = basePath + "ea/vipcenter/ea_orderManage.jspa?sccid=" + sccid + "&ret=ret";
        } else {
            // window.location.href = basePath + "ea/scBudget/ea_projectOrderManage.jspa?tenantFlag=other&companyid=" + treeid + "&staffId=" + staffId;
            window.location.href = basePath + "ea/5l5c/ea_work5L5C.jspa?companyID="+(companyid == "" ? treeid : companyid)+"&staffID="+staffId;
        }
    }

    function back() {
        window.history.go(-1);
    }

    function select() {
        openModal();
    }

    function hideDialog() {
        selectModal.style.display = "none";
    }

    // 点击按钮显示模态框
    function openModal() {
        selectModal.style.display = "block";
    }

    // 点击关闭按钮或模态外部隐藏模态框
    // span.onclick = function() {
    //     selectModal.style.display = "none";
    // }

    function order() {
        openOrderModal();
    }

    function hideOrderDialog() {
        orderModal.style.display = "none";
    }

    // 点击按钮显示模态框
    function openOrderModal() {
        orderModal.style.display = "block";
    }

    window.onclick = function (event) {
        if (event.target == selectModal) {
            selectModal.style.display = "none";
        }
        if (event.target == orderModal) {
            orderModal.style.display = "none";
        }
    }

    // 点击关闭按钮或模态外部隐藏模态框
    // span.onclick = function() {
    //     orderModal.style.display = "none";
    // }

    document.getElementById('excelFile').addEventListener('change', function (e) {
        const file = e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function (e) {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, {type: 'array'});
            let result = "success";
            for (let i = 0; i < workbook.SheetNames.length; i++) {
                let jsonDataVos = [];
                const sheetName = workbook.SheetNames[i];
                const worksheet = workbook.Sheets[sheetName];
                const jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
                const parse = JSON.parse(JSON.stringify(jsonData));
                for (let i = 1; i < parse.length; i++) {
                    const jsonDataVo = {
                        "lineNo": parse[i][0],
                        "goodsName": parse[i][1],
                        "barCode": parse[i][2],
                        "specs": parse[i][3],
                        "count": parse[i][4],
                        "price": parse[i][5],
                        "amount": parse[i][6],
                        "account": parse[i][7]
                    };
                    jsonDataVos.push(jsonDataVo);
                }
                localStorage.setItem("dataImport", JSON.stringify(jsonData));
                if(importBillsData() != "success"){
                    result = "error";
                    break;
                }
            }
            if(result == "success"){
                alert("成功导入"+workbook.SheetNames.length+"张订单");
            }else{
                alert("部分订单导入失败");
            }
            window.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?billsType=" + billsType;
        };
        reader.readAsArrayBuffer(file);
    });


</script>
</html>