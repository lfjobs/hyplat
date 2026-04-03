<!DOCTYPE html>
<%@ page import="java.util.Map" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>盘库单详情</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/addCheckInv/addCheckInv.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/checkInvList/swiper/public.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript"
            charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/mdate/needcss/Mdate.css">
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/jquery-2.1.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/font-size.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/iScroll.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/Mdate.js" type="text/javascript"
            charset="utf-8"></script>
    <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
        <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
    </object>
    <style>
        .content {
            background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
            background-size: 100%
        }
    </style>
    <script>
        //初始化数据
        var basePath = "<%=basePath%>";
        var treeid = "<%=paramMap.get("companyId")%>";//公司id
        var staffId = "<%=paramMap.get("staffId")%>";//登录人id
        var LODOP;
    </script>
<body class="hy">
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            盘库单详情
        </li>
        <li class="li_dy">
            打印
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="f2" action="ea_saveCostSheet.jspa" namespace="/ea/cashinv" method="POST">
        <section class="sec_con clearfix">
            <ul class="ul_con">
                <li class="clearfix">
                    <div class="div_ul_c">
                        <p>
                            单据编号：
                            <input type="text" name="invtFbCheck.journalnum" id="ttsw_billID"
                                   value="${invtFbCheck.journalnum}"
                                   placeholder="自动获取" required readonly/>
                        </p>
                        <ul>
                            <li class="txt">
                                公司名称：
                                <input type="text" id="ttsw_companyNmae" name="invtFbCheck.companyName" class="gs_name"
                                       value="${invtFbCheck.companyName}" required readonly/>

                            </li>
                            <li>
                                单据类型：
                                <input type="text" name="invtFbCheck.billstype" id="pankudan" value="盘库单" readonly/>
                            </li>
                        </ul>
                        <ul>
                            <li class="txt">
                                部门：
                                <input type="text" class="csbm_xiala-1" id="ttsw_dep_n_name"
                                       name="invtFbCheck.orgName" value="${invtFbCheck.orgName}"
                                       placeholder="请选择部门" required readonly/>
                                <div class="select_box csbm_select_box1"></div>
                            </li>
                            <li>
                                单据状态：
                                <input type="text" name="invtFbCheck.billstatus" value="${invtFbCheck.billstatus}"
                                       placeholder="自动获取" id="ttsw_billstatus"
                                       readonly/>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </section>
        <section class="sec_con2">
            <ul class="ul_con2">
                <li class="clearfix">
                    <label for=""><span>*</span>盘库日期：</label>
                    <input type="text" name="invtFbCheck.billsdate" id="ttsw_billsdate"
                           value="<fmt:formatDate value="${invtFbCheck.billsdate}" pattern="yyyy-MM-dd" />"
                           placeholder="" required readonly/>
                </li>
                <li class="clearfix" onclick="panku()">
                    <label for=""><span>*</span>盘库仓库:
                        <input type="text" name="invtFbCheck.warehousename" id="ttsw_warehousename"
                               value="${invtFbCheck.warehousename}" required readonly/>
                    </label>
                </li>
                <li class="clearfix">
                    <label for=""><span></span>盘库人：</label>
                    <input type="text" name="invtFbCheck.staffname" class="ttsw_emp_name"
                           value="${invtFbCheck.staffname}"/>
                </li>
            </ul>
        </section>
    </s:form>
    <section class="sec_con3" id="goodsTable">
        <c:if test="${!empty goodBeanslist}">
            <table width="100%" style="table-layout:fixed" id="goods">
                <tr>
                    <td type="text">
                        物品名称
                    </td>
                    <td>
                        单价
                    </td>
                    <c:if test="${showStyle==1 ||showStyle==2}">
                        <td>
                            库存数
                        </td>
                    </c:if>
                    <c:if test="${showStyle==1 ||showStyle==3}">
                        <td>
                            盘库数量
                        </td>
                    </c:if>
                    <c:if test="${showStyle==1 ||showStyle==4}">
                        <td>
                            误差
                        </td>
                    </c:if>
                </tr>
                <c:forEach items="${goodBeanslist}" var="entity" varStatus="v">
                    <tr class="abccc">
                        <td type="text" class="goodsName">${entity.goodsName}</td>
                        <td class="ttsw_jj">${entity.price}</td>
                        <c:if test="${showStyle==1 ||showStyle==2}">
                            <td class="dnum">${entity.invenQuantity}</td>
                        </c:if>
                        <c:if test="${showStyle==1 ||showStyle==3}">
                            <td class="rnum">${entity.realQuantity}</td>
                        </c:if>
                        <c:if test="${showStyle==1 ||showStyle==4}">
                            <td class="wucha">${entity.error}</td>
                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <div class="clearfix">
            <div class="left"  >
                <p>合计：¥<span id="ttsw_allPrice" >${countPrice}</span></p>
                <p>共<span id="ttsw_num_pro">${goodBeanslist.size()}</span>种商品</p>
            </div>
        </div>
    </section>
</div>
</body>
<script>
    //后退
    function toBack() {
        window.location.href = basePath + "ea/cashinv/ea_getCheckInvList.jspa?companyid=" + treeid + "&&staffId=" + staffId + "";
    }

    //监听点击浏览器后退
    $(function () {
        pushHistory();
        window.addEventListener("popstate", function (e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
    //输入的盘库数量不能为负数
    function clearNoNum(obj){
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
//    alert(obj.value);//输入的盘库数量
        var  depotNum   = obj.value;
//    alert(depotNum);
        $("#ttsw_hid_depotNum").val(depotNum);
        var tds = $(obj).parent().prevAll();
        var price = 0;
        for(i = 0;i<tds.length;i++){
            if(tds[i].className == "ttsw_jj"){
                price = $(tds[i]).text()
            }
        }
        var count =(obj.value)*price;
        $($(obj).next()).val(count);
//        alert("单个商品总价"+ $($(obj).next()).val())
        var prices = $(".goodPrice");
        var goodsPrices = 0;
        for(i=0;i<prices.length;i++){
            if(prices[i].className == "goodPrice"){
                goodsPrices += parseInt($(prices[i]).val());
            }
        }
        var numn=0;
        //库存数
        for(i = 0;i<tds.length;i++){
            if(tds[i].className == "dnum"){
                numn = $(tds[i]).text();
            }
        }
        /* numn 库存数
         obj.value  盘库数量
         nums  每个商品的误差=库存数-盘库数量
         */
        var nums=numn-(obj.value);
        if(nums<0){
            alert("误差不能为负数")
        }
        $("#ttsw_allPrice").text(goodsPrices);
        $(obj).parent().siblings(".wucha").text(nums);
    }

    $(function () {

        function getUrlParam(name){
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r!=null) return unescape(r[2]);
            return null;
        }

        $(".li_dy").click(function () {
            var showStyle = getUrlParam('showStyle');
//            var goodsList = $("#goods").val()
//            console.log(goodsList)
            //单据编号
            var journalnum = $("#ttsw_billID").val();
            //公司名称
            var companyName = $("#ttsw_companyNmae").val();
            //部门
            var orgName = $("#ttsw_dep_n_name").val();
            //盘库日期
            var billsdate = $("#ttsw_billsdate").val();
            //盘库仓库
            var warehousename = $("#ttsw_warehousename").val();
            //盘库人
            var staffname = $(".ttsw_emp_name").val();
            //物品名称
            var goodsName = $(".goodsName").text();
            //单价
            var price = $(".ttsw_jj").text();
            //库存数
            var invenQuantity = $(".dnum").text();
            //盘库数量
            var realQuantity = $(".rnum").text();
            //误差
            var error = $(".wucha").text();
            var u = window.navigator.userAgent;
          var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
           var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

            try {
                if (isAndroid == true) {
                    Android.androidPrintIntegralInfo(journalnum + "", companyName + "", orgName + "", billsdate + "", warehousename + "", staffname + "", goodsName + "", price + "", invenQuantity + "", realQuantity + "", error + "");//调用安卓接口
                } else if (isiOS == true) {
                    var url = "func=" + 'iosSetAPAuth';
                    params = {
                        'journalnum': journalnum,
                        'companyName': companyName,
                        'orgName': orgName,
                        'billsdate': billsdate,
                        'warehousename': warehousename,
                        'staffname': staffname,
                        'goodsName': goodsName,
                        'price': price,
                        'invenQuantity': invenQuantity,
                        'realQuantity': realQuantity,
                        'error': error
                    };
                    for (var i in params) {
                        url = url + "&" + i + "=" + params[i];
                        console.log(url);
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                } else {
                    LODOP = getLodop();
                    LODOP.PRINT_INIT("盘库明细");
                    LODOP.SET_PRINT_PAGESIZE(3, 500, 0.2, "");
                    LODOP.ADD_PRINT_TEXT(42,182,100,20, "盘库");
                    LODOP.ADD_PRINT_TEXT(93,53,100,20, "编号: " + journalnum);
                    LODOP.ADD_PRINT_TEXT(127,55,100,20, "单位: " + companyName);
                    LODOP.ADD_PRINT_TEXT(158,53,100,20, "部门: " + orgName);
                    LODOP.ADD_PRINT_TEXT(187,52,100,20, "盘库人: " + staffname);
                    LODOP.ADD_PRINT_TEXT(217,53,100,20, "盘库日期: " + billsdate);
                    LODOP.ADD_PRINT_TEXT(250,52,100,20, "盘库仓库: " + warehousename);
                    LODOP.ADD_PRINT_TEXT(280,0,100,20, "-------------------------------");
                    LODOP.ADD_PRINT_TEXT(314,53,69,20, "物品名称 ");
                    LODOP.ADD_PRINT_TEXT(314,165,68,20, "单价 ");
                    if (showStyle == 1 || showStyle == 2) {
                        LODOP.ADD_PRINT_TEXT(314,247,68,20, "库存数");
                    }
                    if (showStyle == 1 || showStyle == 3) {
                        LODOP.ADD_PRINT_TEXT(314,328,68,20, "盘库数 ");
                    }
                    if (showStyle == 1 || showStyle == 4) {
                        LODOP.ADD_PRINT_TEXT(314,408,68,20, "误差");
                    }
                    var tab=document.getElementById("goods");
                    var rows=tab.rows;
                    console.log(rows.length);//获取表格的行数
                    for(var i=1;i<rows.length;i++) { //遍历表格的行
                            LODOP.ADD_PRINT_TEXT(355,53,72,20, rows[i].cells[0].innerHTML);
                                LODOP.ADD_PRINT_TEXT(355,168,72,20, rows[i].cells[1].innerHTML);
                                if (showStyle == 1 || showStyle == 2) {
                                    LODOP.ADD_PRINT_TEXT(355,250,72,20, rows[i].cells[2].innerHTML);
                                }
                                if (showStyle == 1 || showStyle == 3) {
                                    LODOP.ADD_PRINT_TEXT(355,323,72,20,rows[i].cells[3].innerHTML);
                                }
                                if (showStyle == 1 || showStyle == 4) {
                                    LODOP.ADD_PRINT_TEXT(355,405,72,20, rows[i].cells[4].innerHTML);
                            }
                            LODOP.ADD_PRINT_TEXT(360, 70, 250, 50, "-------------------------------");

                    }
                    LODOP.PRINT();
                }
            } catch (e) {
                // window.history.go(-1);return false;
            }

        })
    });

</script>
</html>