<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

    // 设置不要缓存页面
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // 设置过期时间为0.
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>util/layui/css/layui.css">
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/reviewCirculate.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>util/layui/layui.all.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/staff/reviewCirculate.js"></script>
    <link rel="stylesheet"
          href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css"/>
    <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyId = "${param.companyId}";
        var staffID = "${param.staffID}";
    </script>
    <title>&lrm;</title>
    <style type="text/css">
        .filetree span.file {
            line-height: 0.8rem;
            display: inline-block;

        }

        .filetree span.folder {
            color: #343333;
        }

        .treeview li {
            margin: 0;
            padding: 1px 0 1px 16px;
        }

        .numcss {
            color: red;
        }
        .layui-layer-msg {
            color: #000 !important; /* 强制黑色字体 */
        }

        .layer-msg {
            color: #333 !important;
        }
    </style>
</head>
<body>

<div class="content">
    <div class="main_main">
        <c:if test="${param.ifr ne 'nohead'}">
            <header>
                <ul class="clearfix">
                    <li>
                        <a href="<%=basePath%>ea/mycenter/ea_myIndex.jspa" target="_self">
                            <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                        </a>
                    </li>
                    <li class="title-i">
                        传阅审核
                    </li>
                </ul>
            </header>
        </c:if>
        <!--左边的树 -->

        <ul id="navigation" style="width: 180px;"
            class="filetree">
            <li>
                <span class="folder" id="tit">传阅审核</span>
                <ul>
                    <li>
                        <span class="folder">入库单</span>
                        <ul>
                            <li class="noWarehousing">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox" id="noReceivebox">(0)</span>
                                </a>
                            </li>
                            <li class="isWarehousing">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox" id="isReceivebox">(0)</span>
                                </a>
                            </li>
                            <li class="rejectWarehousing">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject " id="reject">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">出库单</span>
                        <ul>
                            <li class="noOutbound">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox1" id="noReceivebox1">(0)</span>
                                </a>
                            </li>
                            <li class="isOutbound">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox1" id="isReceivebox1">(0)</span>
                                </a>
                            </li>
                            <li class="rejectOutbound">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject1" id="reject1">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">盘库单</span>
                        <ul>
                            <li class="noCheck">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox2" id="noReceivebox2">(0)</span>
                                </a>
                            </li>
                            <li class="isCheck">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox2" id="isReceivebox2">(0)</span>
                                </a>
                            </li>
                            <li class="rejectCheck">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject2" id="reject2">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">报损单</span>
                        <ul>
                            <li class="noLoss">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox3" id="noReceivebox3">(0)</span>
                                </a>
                            </li>
                            <li class="isLoss">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox3" id="isReceivebox3">(0)</span>
                                </a>
                            </li>
                            <li class="rejectLoss">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject3" id="reject3">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">采购单</span>
                        <ul>
                            <li class="noProcurement">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox4" id="noReceivebox4">(0)</span>
                                </a>
                            </li>
                            <li class="isProcurement">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox4" id="isReceivebox4">(0)</span>
                                </a>
                            </li>
                            <li class="rejectProcurement">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject4" id="reject4">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">上架单</span>
                        <ul>
                            <li class="noLaunch">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox5" id="noReceivebox5">(0)</span>
                                </a>
                            </li>
                            <li class="isLaunch">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox5" id="isReceivebox5">(0)</span>
                                </a>
                            </li>
                            <li class="rejectLaunch">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject5" id="reject5">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">下架单</span>
                        <ul>
                            <li class="noWithdraw">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox6" id="noReceivebox6">(0)</span>
                                </a>
                            </li>
                            <li class="isWithdraw">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox6" id="isReceivebox6">(0)</span>
                                </a>
                            </li>
                            <li class="rejectWithdraw">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject6" id="reject6">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">订货单</span>
                        <ul>
                            <li class="noOrderGoods">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox7" id="noReceivebox7">(0)</span>
                                </a>
                            </li>
                            <li class="isOrderGoods">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox7" id="isReceivebox7">(0)</span>
                                </a>
                            </li>
                            <li class="rejectOrderGoods">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject7" id="reject7">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">发货单</span>
                        <ul>
                            <li class="noShipment">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox8" id="noReceivebox8">(0)</span>
                                </a>
                            </li>
                            <li class="isShipment">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox8" id="isReceivebox8">(0)</span>
                                </a>
                            </li>
                            <li class="rejectShipment">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject8" id="reject8">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">收款单</span>
                        <ul>
                            <li class="noPayment">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox9" id="noReceivebox9">(0)</span>
                                </a>
                            </li>
                            <li class="isPayment">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox9" id="isReceivebox9">(0)</span>
                                </a>
                            </li>
                            <li class="rejectPayment">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject9" id="reject9">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">费用报销单</span>
                        <ul>
                            <li class="noExpenseReimbursement">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox10" id="noReceivebox10">(0)</span>
                                </a>
                            </li>
                            <li class="isExpenseReimbursement">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox10" id="isReceivebox10">(0)</span>
                                </a>
                            </li>
                            <li class="rejectExpenseReimbursement">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject10" id="reject10">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">初始项目单</span>
                        <ul>
                            <li class="noInitialProject">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox11" id="noReceivebox11">(0)</span>
                                </a>
                            </li>
                            <li class="isInitialProject">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox11" id="isReceivebox11">(0)</span>
                                </a>
                            </li>
                            <li class="rejectInitialProject">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject11" id="reject11">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <span class="folder">验货单</span>
                        <ul>
                            <li class="noInspectionReport">
                                <a href="#"><span
                                        class="file">未审核</span><span class="numcss noReceivebox12" id="noReceivebox12">(0)</span>
                                </a>
                            </li>
                            <li class="isInspectionReport">
                                <a href="#"><span
                                        class="file">已审核</span><span class="numcss isReceivebox12" id="isReceivebox12">(0)</span>
                                </a>
                            </li>
                            <li class="rejectInspectionReport">
                                <a href="#"><span
                                        class="file">已驳回</span><span class="numcss reject12" id="reject12">(0)</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading5.gif" alt=""/>
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>";
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var ifr = "${param.ifr}";
    $(document).ready(function () {
        $("#navigation").treeview();

        if (module == "") {
            $(".gr").hide();
            $("#yz").text("个人印章");
            $(".mbname").text("个人模板");
        } else {
            $(".gr").show();
            $("#yz").text("印章管理");
            $(".mbname").text("公司模板");
        }

        if (module == "doc") {
            $(".title-i").text("传阅审核");
            $("#tit").text("传阅审核");
        } else if (module == "contract") {
            $(".title-i").text("合同签约");
            $("#tit").text("合同签约");
        }
    });
</script>
</html>