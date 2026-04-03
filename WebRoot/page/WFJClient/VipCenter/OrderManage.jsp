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
    <script type="application/javascript">
        var from = "";
        var searchParams = new URLSearchParams(window.location.search);
        var params = new Map();
        searchParams.forEach(function(value, key) {
            params.set(key,value);
        });
        if(params.size > 0){
            if(null != params.get("from")){
                from = params.get("from");
            }
        }
    </script>
</head>
<body>
<!-- header start  -->
<header class="mem_header">
    <a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa" target="_self" class="back" id="ret"></a>
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
            <div class="order_tab_box" style="font-size: 15px;line-height: 1.9rem;height: 1.9rem;">采购订单</div>
        </div>
    </section>
    <%--<section class="tab_con">
        <div class="order_m_box order_m_box4">
            <i class="order_man_ico order_man_ico1"></i>
            <span>订单管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box5">
            <i class="order_man_ico order_man_ico2"></i>
            <span>收货管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box6">
            <i class="order_man_ico order_man_ico3"></i>
            <span>退货管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box7">
            <i class="order_man_ico order_man_ico8"></i>
            <span>我的收藏</span>
            <i class="order_arr_R"></i>
        </div>
    </section>
    <section class="tab_con">
        <div class="order_m_box order_m_box1">
            <i class="order_man_ico order_man_ico5"></i>
            <span>订单管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box8">
            <i class="order_man_ico order_man_ico5"></i>
            <span>新版订单管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box2">
            <i class="order_man_ico order_man_ico6"></i>
            <span>收货管理</span>
            <i class="order_arr_R"></i>
        </div>
        <div class="order_m_box order_m_box3">
            <i class="order_man_ico order_man_ico7"></i>
            <span>退货管理</span>
            <i class="order_arr_R"></i>
        </div>
    </section>--%>

    <section class="tab_con">
        <div class="tab_con_box">
            <iframe width="100%" height="850px" border="0"
                    frameborder="0" src="/ea/scBudget/ea_toCostBudgetBillList.jspa?menuId=ng&tenantFlag=personal&head=hide"></iframe>
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
    <section class="tab_con">
        <div class="tab_con_box">
            <div class="tab_item_box">
                <div class="tab_item order_m_box4 bdd">
                    <span>订单管理</span>
                    <i class="order_arr_R"></i>
                </div>
<%--                <div class="tab_item sellOrder">--%>
<%--                    <span>采购管理</span>--%>
<%--                    <i class="order_arr_R"></i>--%>
<%--                </div>--%>
<%--                <div class="tab_item checkOrder">--%>
<%--                    <span>验货管理</span>--%>
<%--                    <i class="order_arr_R"></i>--%>
<%--                </div>--%>
<%--                <div class="tab_item inboundOrder">--%>
<%--                    <span>入库管理</span>--%>
<%--                    <i class="order_arr_R"></i>--%>
<%--                </div>--%>
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

<!--  页面内容 end -->
<script>
    var basePath = '<%=basePath%>';
    var state = '${cuscom.state}';
    var staffid = '${cuscom.staffid}';
    var user = '${cuscom.account}';
    var companyid = '${cuscom.companyId}';
    var sccId = '${cuscom.sccId}';
    var ret = "${ret }";
    // if (state == 1) {
    //     $(".order_tab_box").css("width","50%");
    //     $(".sell").css("display", "none");
    // }

    $(".sellOrder").click(function () {
        document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=personal&billsType=采购单"+ "&sccid=" + sccId;
    });

    $(".checkOrder").click(function () {
        document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=personal&billsType=验货单"+ "&sccid=" + sccId;
    });

    $(".inboundOrder").click(function () {
        document.location.href = basePath + "ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=personal&billsType=入库单"+ "&sccid=" + sccId;
        // document.location.href = basePath + "page/ea/main/finance/invoicing/rukuBill.jsp?compayid="+companyid+"&sccId="+sccId+"&staffid="+staffid;
    });

    //初始项目
    $(".djfb").click(function () {
        // document.location.href = basePath + "ea/productslaunch/ea_productsManage.jspa?user=" + user;
        document.location.href = basePath + "ea/scBudget/ea_toCostBudgetBillList.jspa?menuId=ng&tenantFlag=personal";
    });

    //项目计划
    $(".projectPlan").click(function () {
        document.location.href = "http://localhost:8080/ea/scBudget/ea_toPlanCostBudgetBillList.jspa?menuId=ng&tenantFlag=personal";
    });

    //卖家订单管理
    $(".sdd").click(function () {
        document.location.href = basePath + "ea/seller/ea_getcomporder.jspa?companyid=" + companyid + "&staffid=" + staffid + "&sccId=" + sccId;
    });

    //新版订单管理
    $(".xbdd").click(function () {
        document.location.href = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?&companyid="+companyid+ "&staffid=" + staffid+"&sort=1";
    });

    //卖家退货单
    $(".sth").click(function () {
        document.location.href = basePath + "ea/seller/ea_getReturnOrder.jspa?companyid=" + companyid + "&sccId=" + sccId;
    });

    //买家订单管理
    $(".bdd").click(function () {
        //document.location.href=basePath+"ea/hypb/ea_getcomporder.jspa?staid="+staffid;
        document.location.href = basePath + "ea/pobuy/ea_getPhoneOrdersList.jspa?staid=" + staffid + "&sccId=" + sccId;
    });

    //买家退货单
    $(".bth").click(function () {
        //document.location.href = basePath+"ea/refund/ea_getRefundSheetList.jspa?user="+user+"&type=mobile&role=buyer";
        document.location.href = basePath + "/ea/refundMoney/ea_getRefundList.jspa?staffid=" + staffid + "&sccId=" + sccId;//退货
    });

    /*//卖家订单管理
    $(".order_m_box1").click(function () {
        document.location.href = basePath + "ea/seller/ea_getcomporder.jspa?companyid=" + companyid + "&staffid=" + staffid + "&sccId=" + sccId;
    });
    //卖家收货单
    $(".order_m_box2").click(function () {
        document.location.href = basePath + "ea/seller/ea_getReceipt.jspa?sta=00&companyid=" + companyid + "&sccId=" + sccId;
    });
    //卖家退货单
    $(".order_m_box3").click(function () {
        document.location.href = basePath + "ea/seller/ea_getReturnOrder.jspa?companyid=" + companyid + "&sccId=" + sccId;
    });
    //买家订单管理
    $(".order_m_box4").click(function () {
        //document.location.href=basePath+"ea/hypb/ea_getcomporder.jspa?staid="+staffid;
        document.location.href = basePath + "ea/pobuy/ea_getPhoneOrdersList.jspa?staid=" + staffid + "&sccId=" + sccId;
    });
    //买家收货单
    $(".order_m_box5").click(function () {
        //document.location.href = basePath+"ea/consignee/ea_receipt.jspa?stype=&sta=00&tupn=goods&stype3=&user";
        document.location.href = basePath + "/ea/pobuy/ea_getReceiptList.jspa?user=" + user + "&state=00" + "&sccId=" + sccId;
    });
    //买家退货单
    $(".order_m_box6").click(function () {
        //document.location.href = basePath+"ea/refund/ea_getRefundSheetList.jspa?user="+user+"&type=mobile&role=buyer";
        document.location.href = basePath + "/ea/refundMoney/ea_getRefundList.jspa?staffid=" + staffid + "&sccId=" + sccId;//退货
    });
    //我的收藏
    $(".order_m_box7").click(function () {
        document.location.href = basePath + "page/ea/collage/phl/phl_collectGoods.jsp";
    });
    //新版订单管理
    $(".order_m_box8").click(function () {
        document.location.href = basePath + "/page/ea/main/finance/NewPhoneOrders/sellerOrder/Office/myOrder.jsp?&companyid="+companyid+ "&staffid=" + staffid+"&sort=1";
    });*/


</script>
<script>
    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    }
</script>
<script>
    $(function () {
        $(".tab_con").eq(0).show();//初始化tab显示
        //绑定切换事件
        $(".order_tab_box").click(function () {
            var _index = $(".order_tab>div").index(this);
            $(this).addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
            $(".tab_con").eq(_index).show().siblings(".tab_con").hide();
        })
    })
</script>

<script>
    $(document).ready(function () {
        if(from == "buyOrder"){
            $(".order_tab_box").eq(0).addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
            $(".tab_con").eq(0).show().siblings(".tab_con").hide();
            buyLoad();
        }

        var ret = '${ret}';
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retAndroid()");
        } else if (isiOS == true) {
            var obj = document.getElementById("ret");
            obj.setAttribute("href", "#");
            obj.setAttribute("onclick", "retIOS()");
        }
    });

    function buyLoad() {
        setTimeout(buyResolve, 1000);
    }

    function buyResolve(){
        $(".order_tab_box").eq(2).addClass("order_tab_cur").siblings().removeClass("order_tab_cur");
        $(".tab_con").eq(2).show().siblings(".tab_con").hide();
    }

    //安卓，苹果返回
    function retAndroid() {
        try {
            Android.callAndroidjianli();
        } catch (err) {
            $(".back").removeAttr("onclick");
            $(".back").attr("href", basePath + "ea/mycenter/ea_myIndex.jspa");
            // $(".back").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
        }
    }

    function retIOS() {
        try {
            if (ret == 'ret') {
                $(".back").removeAttr("onclick");
                $(".back").attr("href", basePath + "ea/mycenter/ea_myIndex.jspa");
                // $(".back").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
            }
            var url = "func=" + 'calliosOrder';
            window.webkit.messageHandlers.Native.postMessage(url);
        } catch (err) {
            $(".back").removeAttr("onclick");
            $(".back").attr("href", basePath + "ea/mycenter/ea_myIndex.jspa");
            // $(".back").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
        }
    }
</script>
</body>
</html>