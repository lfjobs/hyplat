<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>项目订单</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>
    <meta name="format-detection" content="telephone=no, email=no"/>
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
    <style type="text/css">
        .o_tab {
            overflow: hidden;
            background-color: #f2f2f2;
            color: #222;
            font-size: 0.5rem;
            padding: 0 0;
            width: 1200px;
        }
        .o_tab li {
            width: 3rem;
            margin-left: 0.2rem;
            line-height: 1.9rem;
            height: 1.9rem;
            float: left;
            text-align: center;
            font-size: 15px;
        }
        .project{
            color: #ff6600;
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
        .back {
            position: absolute;
            display: block;
            top: 50%;
            height: 0.84rem;
            width: 0.5rem;
            background: url(../../images/WFJClient/pc/5l5c/img_03.png) no-repeat center;
            background-size: contain;
            left: 5%;
            margin-top: -0.42rem;
        }
    </style>
    <script>
        var basePath = '<%=basePath%>';
        var staffId = '${staffId}';
        var companyId = '${companyid}';
        var sccId;

        $(document).ready(function () {
            $("#iframe").attr("src","/ea/scBudget/ea_toCostBudgetBillList.jspa?companyid="+companyId+"&staffId="+staffId+"&menuId=ng&tenantFlag=other&head=hide");

            $(".tab_con").eq(0).show();//初始化tab显示
            //绑定切换事件
            $(".order_tab_box").click(function () {
                var _index = $(".order_tab>div").index(this);
                $(this).addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
                $(".tab_con").eq(_index).show().siblings(".tab_con").hide();
            })
            $(".sellOrder").click(function () {
                document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=other&billsType=采购单";
            });

            $(".checkOrder").click(function () {
                document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=other&billsType=验货单";
            });

            $(".inboundOrder").click(function () {
                document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=other&billsType=入库单";
                // document.location.href = basePath + "page/ea/main/finance/invoicing/rukuBill.jsp?compayid="+companyid+"&sccId="+sccId+"&staffid="+staffid;
            });

            //初始项目
            $(".djfb").click(function () {
                document.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillList.jspa?menuId=ng&tenantFlag=other";
            });

            //项目计划
            $(".projectPlan").click(function () {
                document.location.href = "http://localhost:8080/ea/scBudget/ea_toPlanCostBudgetBillList.jspa?menuId=ng&tenantFlag=other";
            });

            //卖家订单管理
            $(".sdd").click(function () {
                document.location.href = basePath + "ea/seller/ea_getcomporder.jspa?companyid=" + companyId + "&staffid=" + staffId + "&sccId=" + sccId;
            });

            //新版订单管理
            $(".xbdd").click(function () {
                document.location.href = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?&companyid="+companyId+ "&staffid=" + staffId+"&sort=1";
            });

            //卖家退货单
            $(".sth").click(function () {
                document.location.href = basePath + "ea/seller/ea_getReturnOrder.jspa?companyid=" + companyId + "&sccId=" + sccId;
            });

            //买家订单管理
            $(".bdd").click(function () {
                document.location.href = basePath + "ea/pobuy/ea_getPhoneOrdersList.jspa?staffid=" + staffId + "&sccId=" + sccId;
            });

            //买家退货单
            $(".bth").click(function () {
                document.location.href = basePath + "/ea/refundMoney/ea_getRefundList.jspa?staffid=" + staffId + "&sccId=" + sccId;
            });

            window.onload = window.onresize = function () {
                //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
                //获取窗口的尺寸
                var clientWidth = document.documentElement.clientWidth;
                //通过屏幕宽度去设置不同的后台根字体的大小
                document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
            }
        });

        function toBack() {
            window.location.href = basePath + "ea/5l5c/ea_work5L5C.jspa?companyID="+companyId+"&staffID="+staffId;
        }
    </script>
</head>
<body>
<!-- header start  -->
<header class="mem_header">
    <a href="#" target="_self" class="back" onclick="toBack()"></a>
    <h1 style="background-color: #f74c32;color: #fff;font-size: 0.7rem">项目订单</h1>
</header>
<!--  header end  -->
<!-- 页面内容 start  -->
<div class="wrap_page">
    <section class="order_tab_wrap">
        <ul class="o_tab">
            <li class="project">项目管理</li>
            <li class="">招标管理</li>
            <li class="">比价管理</li>
            <li class="">收付管理</li>
        </ul>
    </section>
    <section class="order_tab_wrap">
        <div class="order_tab clearfix">
            <div class="order_tab_box order_tab_cur" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">初始项目</div>
            <div class="order_tab_box sell" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">销售订单</div>
            <div class="order_tab_box" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">资产采购订单</div>
        </div>
    </section>
    <section class="tab_con">
        <div class="tab_con_box">
            <iframe id="iframe" width="100%" height="850px" border="0" frameborder="0" src=""></iframe>
        </div>
    </section>
    <section class="tab_con">
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item sdd">
                    <span>订单管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item xbdd">
                    <span>拣货管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item xbdd">
                    <span>出库管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item xbdd">
                    <span>发货管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item">
                    <span>库存管理</span>
                    <i class="order_arr_R"></i>
                </div>
            </div>
        </div>
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item sth">
                    <span>退货管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item xbdd">
                    <span>物流管理</span>
                    <i class="order_arr_R"></i>
                </div>
            </div>
        </div>
    </section>
    </section>
    <section class="tab_con">
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item sellOrder">
                    <span>采购管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item checkOrder">
                    <span>验货管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item inboundOrder">
                    <span>入库管理</span>
                    <i class="order_arr_R"></i>
                </div>
            </div>
        </div>
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item">
                    <span>库存管理</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item">
                    <span>库存限量</span>
                    <i class="order_arr_R"></i>
                </div>
                <div class="tab_item">
                    <span>有效期</span>
                    <i class="order_arr_R"></i>
                </div>
            </div>
        </div>
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item order_m_box4 bth">
                    <span>退货管理</span>
                    <i class="order_arr_R"></i>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>