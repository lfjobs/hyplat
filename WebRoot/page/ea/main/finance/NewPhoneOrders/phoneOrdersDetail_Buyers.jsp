<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>订单详情-买家</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>

    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/finance/NewPhoneOrders/phoneOrderDetail.js?version=20250430"></script>


</head>
<script type="text/javascript">
    var cashid = "${pb.cashierBillsID }";
    var staid = "${pb.contactUserID }";
    var sccId = "${sccId}";
    var basePath = "<%=basePath%>";
    var pl = "01"; // 未付款状态
    var zffs = 1;//选择的支付方式   默认为支付宝
    var jum = "${pb.journalNum}";
    var morre = "${pb.priceSub}";
    var lastPay = "${param.lastPay}";


var goodsName = "";
    var status = "${pb.fkStatus}";
    var token = 0;
    var flag = "${flag}";
    var state = "${state}";
    var user = "${user}";
    var wfStatus4 = "${pb.wfStatus4}";
    var companyID = "${pb.companyid}";
    var  companyName = "${pb.companyName}";
    var gd = "${pb.goodsName}";
    var ge = "${pb.goodsCoding}";
    var remainNum = "${unPayRecord.remainNum}";

    if(status=="01"&&remainNum!=null&&remainNum!=""){
        morre = remainNum;
    }

</script>

<script type="text/javascript">
    $(function () {
      if(status=="01"){
        var remainMoney = "${unPayRecord.remainNum}";
        if(remainMoney!=null&&remainMoney!=""){
               $(".cfd").hide();
            $(".det").hide();
            $(".mil_det").hide();

        }
    }





        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (!isWeixin) {
            if (ua.indexOf("browser") != -1) {
                $(".wechat").hide();

            } else {
                $(".wechat").show();

            }
        }


    });

</script>
<body>
<div class="loading" style="display:none;">
    <img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
    <p><span>加载中...</span></p>
</div>
<div class="header">
    <ul>
        <s:if test="flag=='sh'">
            <li style="width: 10%;"><a
                    href="<%=basePath%>ea/pobuy/ea_getReceiptList.jspa?&user=${user}&state=${state}&flag=sh"><img
                    src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        </s:if >
<s:else>
        <s:if test="#request.entrance=='integral'">
            <li style="width: 10%;"><a  href="javascript:history.go(-1)"><img
                    src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        </s:if >
        <s:else>
            <li  class="fh-li" style="width: 10%;"><a
                    href="<%=basePath%>ea/pobuy/ea_getPhoneOrdersList.jspa?staid=${pb.contactUserID}&sccId=${sccId}"><img
                    src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        </s:else>
</s:else>
        <li style="width: 80%;text-align: center;" class="title-lis">订单详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content rec_content det_content">
        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/banner.png" alt="" width="100%">
        <div class="mil_det">
            <c:choose>
                <c:when test="${not empty wllist}">
                    <ul onclick="window.location.href='<%=basePath%>ea/pobuy/ea_toGetWuliu.jspa?cbid=${pb.cashierBillsID}'">
                        <li style="width: 10%;text-align: left;"><img
                                src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-car.png" alt=""></li>
                        <li style="width: 85%;">
                         <%--物流状态--%>

                            <p>${wllist[fn:length(wllist)-1][0]}</p>
                         <%--最新一条物流信息--%>
                            <h5>${wllist[fn:length(wllist)-1][1]}</h5>

                        </li>
                        <li style="width: 5%;text-align: right;"><img
                                src="<%=basePath%>images/ea/finance/NewPhoneOrders/right.png" alt=""></li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <ul onclick="window.location.href='<%=basePath%>ea/pobuy/ea_toGetWuliu.jspa?cbid=${pb.cashierBillsID}'">
                        <li style="width: 10%;text-align: left;"><img
                                src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-car.png" alt=""></li>
                        <li style="width: 85%;">
                                <%--物流状态--%>
                            <p>${fn:length(wllist)}</p>
                            <p>此订单无物流信息</p>
                            <%--<p> &lt;%&ndash;>${wllist[fn:length(wllist)-1][0].time }&ndash;%&gt;</p>
                                 &lt;%&ndash;最新一条物流信息&ndash;%&gt;
                            <p>&lt;%&ndash;${wllist[fn:length(wllist)-1][0].status }&ndash;%&gt;</p>--%>
                            <h5></h5>
                        </li>
                        <li style="width: 5%;text-align: right;"><img
                                src="<%=basePath %>images/ea/finance/NewPhoneOrders/right.png" alt=""></li>
                    </ul>
                </c:otherwise>
            </c:choose>
            <s:iterator value="#request.rlist" var="r">
                <ul class="det">
                    <li style="width: 10%;text-align: left;"><img
                            src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-left.png" alt=""></li>
                    <li style="width: 85%;">
                        <h3>${r[0] }</h3>
                        <span class="tel">${r[1] }</span>
                        <div style="clear: both;"></div>
                        <p>收货地址：<span>${r[2] }</span></p>
                    </li>
                </ul>
            </s:iterator>
        </div>
        <div class="company">
            <div class="left img">
                <img src="<%=basePath %>${pb.companyLogo}" alt="">
            </div>
            <div class="txt">
                <span>${pb.companyName }</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right2.png"
                                                       alt=""></a>
            </div>
        </div>
        <s:iterator value="#request.pb.goodsList" var="pf">
        <div class="shop_mil" onclick="goodsDetail('${pf[7] }','${pf[10] }','${pf[12] }')">
            <div class="left">
                <c:choose>
                    <c:when test="${pf[16] != null && pf[16]!= '' && pf[16] == '1'}">
                        <%--批发--%>
                    </c:when>
                    <c:when test="${pf[16] != null && pf[16] !='' && pf[16] == '2'}">
                        <%--VIP--%>
                        <span class="vip"><i></i></span>
                    </c:when>
                    <c:when test="${pf[16] != null && pf[16] != '' && pf[16] == '3'}">
                        <%--普通活动--%>
                        <span class="cx"><i></i></span>
                    </c:when>
                    <c:when test="${pf[16] != null && pf[16] != '' && pf[16] == '4'}">
                        <%--特价活动--%>
                        <span class="tj"><i></i></span>
                    </c:when>

                    <c:otherwise>
                        <%--零售--%>
                    </c:otherwise>

                </c:choose>
                <img src="<%=basePath %>${pf[6]}" alt="">
            </div>

            <div class="txt">
                <h3 class="gods">${pf[5] }</h3>
                <h4>产品规格：<span>${pf[11] }</span></h4>
            </div>
            <div class="txt2">
                <h3>&yen;<span>${pf[3] }</span></h3>
                <h4>x<span>${pf[2] }</span></h4>
            </div>
            <script type="java/script">

            goodsName='${pf[14]}';

		

            </script>
        </div>
        </s:iterator>

        <!-- 促销品 -->
        <s:iterator value="#request.pb.ptgoodsList" var="pg">
            <div class="shop_mil" onclick="goodsDetail('${pg[1] }','${pg[7] }','${pg[3] }')">
                <div class="left">
                    <img src="<%=basePath %>${pg[6]}" alt="">
                </div>
                <div class="txt">
                    <h3>${pg[2] }</h3>
                    <h4>产品规格：<span>${pg[5] }</span></h4>
                </div>
                <div class="txt2">
                    <h3><span></span></h3>
                    <h4>x<span>${pg[8]}</span></h4>
                </div>
                <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-cu.png" class='cu'>
            </div>
        </s:iterator>
        <div class="mon">
            <s:if test="#request.entrance!='integral'">
                <div class="btn">
                    <s:if test="#request.pb.fkStatus=='00'">
                        <a href="<%=basePath %>ea/refundMoney/ea_ref.jspa?cashId=${pb.cashierBillsID }&tp=00"><input
                                type="button" value="申请退款"></a>
                    </s:if>
                    <s:if test="#request.pb.fkStatus=='02'">
                        <a href="<%=basePath%>ea/refundMoney/ea_ref.jspa?cashId=${pb.cashierBillsID }&tp=01"><input
                                type="button" value="申请退货"></a>
                    </s:if>
                    <s:if test="#request.pb.fkStatus=='03'">
                        <a href="#;"><input type="button" value="申请售后"></a>
                    </s:if>
                    <s:if
                            test="#request.pb.fkStatus=='05'||#request.pb.fkStatus=='06'||#request.pb.fkStatus=='07'||#request.pb.fkStatus=='08'">
                        <a href="<%=basePath%>ea/refundMoney/ea_details.jspa?cashId=${pb.cashierBillsID}&tp=01"><input
                                type="button" value="退货中"></a>
                    </s:if>
                    <s:if
                            test="#request.pb.fkStatus=='10'||#request.pb.fkStatus=='11'||#request.pb.fkStatus=='12'||#request.pb.fkStatus=='13'">
                        <a href="#;"><input type="button" value="售后中"></a>
                    </s:if>

                    <s:if
                            test="#request.pb.fkStatus=='16'||#request.pb.fkStatus=='17'||#request.pb.fkStatus=='18'">
                        <a href="<%=basePath%>ea/refundMoney/ea_details.jspa?cashId=${pb.cashierBillsID }&tp=00"><input
                                type="button" value="退款中"></a>
                    </s:if>

                </div>
            </s:if>

            <div class="txt">
                <h5>商品总价<p>&yen;<span>${pb.priceSub}</span></p></h5>
                <h5>运费（快递）<p>&yen;<span>0.00</span></p></h5>
                <h5>订单总价<p>&yen;<span>${pb.priceSub}</span></p></h5>
                <s:if test="#request.pb.wfStatus4=='00'">
                    <h5>支付方式<p><span>微信支付</span></p></h5>
                </s:if>
                <s:if test="#request.pb.wfStatus4=='01'">
                    <h5>支付方式<p><span>支付宝支付</span></p></h5>
                </s:if>
                <s:if test="#request.pb.wfStatus4=='02'">
                    <h5>支付方式<p><span>银联支付</span></p></h5>
                </s:if>
                <s:if test="#request.pb.wfStatus4=='03'">
                    <h5>支付方式<p><span>线下转账</span></p></h5>
                </s:if>
                <s:if test="#request.pb.wfStatus4=='04'">
                    <h5>支付方式<p><span>钱盒子支付</span></p></h5>
                </s:if>
                <h4>实付<p>&yen;<span>${pb.priceSub}</span></p></h4>
                <h4>备注:<span>&nbsp;${pb.remark}</span></h4>
                <c:if test="${pb.privateRoom!=null}">
                    <h4>桌号:<span>&nbsp;${pb.privateRoom}</span></h4>

                </c:if>
                <c:if test="${pb.mealNum!=null}">
                    <h4>取餐号:<span>&nbsp;${pb.mealNum}</span></h4>

                </c:if>


            </div>
        </div>
        <div class="code">
            <div class="code_">
                <h4>订单编号：<span>${pb.journalNum }</span></h4><input class="copy_btn" type="button" value="复制">
            </div>
            <s:if test="pb.fkStatus!=01">
                <h4>支付宝交易号：<span>${pb.trade_no}</span></h4>
            </s:if>
            <h4>下单时间：<span>${fn:substring(pb.cashierdate, 0, 19)}</span></h4>
            <c:forEach items="${dateMap}" var="c">
                <h4>${c.key }：<span>${fn:substring(c.value, 0, 19)}</span></h4>
            </c:forEach>
        </div>
        <div class="so-much">
            <a href="<%=basePath%>ea/industry/ea_CompanyProducts.jspa?ccompanyId=${ccompanyID}">
            <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/so-much.png" alt="">
            <p style="color:#ff6600;font-weight:bold;">更多好货点击进商城选购</p>
            </a>
        </div>
        <div class="so_shop">
            <ul>
                <s:iterator value="#request.cplist" var="cp">
                    <li onclick="goodsDetail('${cp[0]}','${cp[16]}','${pb.companyid }')">
                        <img src="<%=basePath %>${cp[5]}" alt="">
                        <div class="txt">
                            <h4>${cp[2]}</h4>
                            <c:choose>
                                <c:when test="${cp[19]!=null&& cp[17]!=null}">
                                    <c:if test="${cp[19]=='00'}"><%--促销活动--%>
                                        <span class="sp cx"><i></i></span>
                                        <p>&yen;<span><fmt:formatNumber pattern="0.00" value="${cp[17]*pct}"/></span></p>
                                    </c:if>
                                    <c:if test="${cp[19]=='01'}"><%--特价活动--%>
                                        <span class="sp tj"><i></i></span>
                                        <p>&yen;<span><fmt:formatNumber pattern="0.00" value="${cp[17]*pct}"/></span></p>
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${cp[18]!=null}"><%--VIP价--%>
                                            <span class="sp vip"><i></i></span>
                                            <p>&yen;<span><fmt:formatNumber pattern="0.00" value="${cp[18]*pct}"/></span></p>
                                        </c:when>
                                         <c:otherwise>
                                             <p>&yen;<span><fmt:formatNumber pattern="0.00" value="${cp[4]*pct}"/></span></p><%--零售价--%>
                                         </c:otherwise>
                                    </c:choose>

                                </c:otherwise>
                            </c:choose>

                        </div>
                    </li>
                </s:iterator>
            </ul>
        </div>
    </div>
<s:if test="#request.entrance!='integral'">
    <div class="btn">
        <s:if test="#request.pb.tradeCode=='z30001汽车驾校'">
            <a href="#;"><input type="button" onclick="update()" value="修改学员"></a>
        </s:if>
        <s:if test="#request.pb.fkStatus=='01'">
            <s:if test="#request.pb.wfStatus4 == '03' ">
                <a href="#;"><input type="button" onclick="zzqr()" id="zzqr" value="确认转账"></a>
            </s:if>
            <s:else>
                <%--<a href="#;"><input type="button" value="删除订单" onclick="del()"></a>20180224隐藏--%>
                <a href="#;"><input type="button" value="立即支付" id="zf"></a>
            </s:else>
        </s:if>
        <s:if test="#request.pb.fkStatus=='02'">
            <a href="#;"><input type="button" id="wfj_sh" value="确认收货"></a>
        </s:if>
        <s:if test="state=='00'">
            <a href="#;"><input type="button" id="wfj_sh" value="评价"
                                onclick="window.location.href='<%=basePath%>ea/pobuy/ea_Assess.jspa?cbid=${pb.cashierBillsID}&user=${user}'"></a>
        </s:if>
    </div>
</s:if>
    <div class="wfj12_014_hidden_buy pays" style="display:none;">
        <table id="pays" width="100%">
            <tr>
                <td width="50%" style="padding-left:4%;"><span>需支付：</span>
                </td>
                <td align="right" style="padding-right:4%;" width="50%">
                    <span id="money" style="color:#F74C31;">
       <s:if test="#request.pb.fkStatus=='01'&&#request.unPayRecord.remainNum!=null&&#request.unPayRecord.remainNum!=''">￥${unPayRecord.remainNum}</s:if>
        <s:else>￥${pb.priceSub}</s:else></span>
                </td>
            </tr>
        </table>
        <div class="wfj12_014_buy_width">
            <table>
                <tr>
                    <td colspan="2">选择支付方式</td>
                </tr>
                <tr class="wfj12_014_choice">
                    <td class="wfj12_014_pay"><img
                            src="<%=basePath %>images/ea/finance/BenDis/all_pay_01.png"/>
                    </td>
                    <td class="second" align="right"><img width="24" name="1"
                                                          height="24"
                                                          src="<%=basePath %>images/ea/finance/BenDis/choice_01.png"/>
                    </td>
                </tr>
                <%--<tr class="wfj12_014_choice">--%>
                <%--<td class="wfj12_014_pay"><img--%>
                <%--src="<%=basePath %>images/ea/finance/BenDis/all_pay_02.png" />--%>
                <%--</td>--%>
                <%--<td class="second" align="right"><img width="24" name="2"--%>
                <%--height="24"--%>
                <%--src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />--%>
                <%--</td>--%>
                <%--</tr>--%>
<tr class="wfj12_014_choice wechat">
    <td class="wfj12_014_pay"><img
            src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png"/>
    </td>
    <td class="second" align="right"><img width="24" name="3"
                                          height="24"
                                          src="<%=basePath %>images/ea/finance/BenDis/choice_02.png"/>
    </td>
</tr>
                <tr class="wfj12_014_choice integral_">
                    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/jifen.png" />
                        <span style="font-size: 12px"></span>
                    </td>
                    <td class="second" align="right" ><img width="24" name="7"
                                                           height="24"
                                                           src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
                    </td>
                </tr>
                <tr class="wfj12_014_choice gold">
                    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/gold.png" />
                        <span style="font-size: 12px"></span>
                    </td>
                    <td class="second" align="right" ><img width="24" name="6"
                                                           height="24"
                                                           src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
                    </td>
                </tr>
<c:if test="${pb.goodsName ne '智能货柜'}">
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png"/>
    </td>
    <td class="second" align="right"><img width="24" name="4"
                                          height="24"
                                          src="<%=basePath %>images/ea/finance/BenDis/choice_02.png"/>
    </td>
</tr>
<tr class="wfj12_014_choice">
    <td class="wfj12_014_pay"><img src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png"/>
    </td>
    <td class="second" align="right"><img width="24" name="5"
                                          height="24"
                                          src="<%=basePath %>images/ea/finance/BenDis/choice_02.png"/>
    </td>
</tr>
</c:if>
</table>
</div>
<div class="wfj12_014_bottom_commit">
<div id="paycommit" onclick="zf()">确认支付</div>
</div>
</div>
    <%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>

    <!-- 修改学员信息 -->
<form name="addForm" id="addForm" method="post">
<div class="wfj12_014_hidden_buy" style="display:none;" id="jqModeladd">
<table id="SearchTable" width="100%">
<tr>
    <td width="50%" style="padding-left:6%;"><span><font color="#660000" size="5%">学员信息</font></span>
    </td>
    <td width="50%" style="padding-right:6%;" align="right" id="back">
        <span><a href="javascript:"><img style="height: 40%"
                                         src="<%=basePath %>images/ea/finance/BenDis/x.png"></a></span>
    </td>
</tr>
</table>

<div class="wfj12_014_buy_width wfj12_014_buy_width2">
<table>
    <tr class="wfj12_014_choice">
        <td class="wfj12_014_pay" align="right">订单编号：</td>
        <td>
            <input type="hidden" id="joinput" style="width: 195px" name="jo"/>
            <span>${pb.journalNum}</span>
        </td>
    </tr>

    <tr class="wfj12_014_choice">
        <td class="wfj12_014_pay" align="right">
            学员姓名：
        </td>
        <td>
            <input id="noviceName" class="novice" style="width: 195px" name="noviceName"/>
        </td>
    </tr>

    <tr class="wfj12_014_choice">
        <td class="wfj12_014_pay" align="right">
            学员电话：
        </td>
        <td>
            <input id="novicePhone" class="novice" style="width: 195px" name="novicePhone"/>
        </td>
    </tr>
    <tr class="wfj12_014_choice">
        <td class="wfj12_014_pay" align="right">
            学员身份证号：
        </td>
        <td>
            <input id="noviceCode" class="novice" style="width: 195px" name="noviceCode"/>
        </td>
    </tr>
    <tr class="wfj12_014_choice">
        <td class="wfj12_014_pay" align="right">
            学员地址：
        </td>
        <td>
            <input id="noviceAddress" class="novice" style="width: 195px" name="noviceAddress"/>
        </td>
    </tr>
</table>
</div>
<div class="wfj12_014_bottom_commit wfj12_014_bottom_commit2">
<div id="tosave">保存</div>
</div>
</div>
</form>

</div>
<div class="alert123">
</div>
<form name="formsutm" id="formsutm" method="post">
<s:hidden name="ddid" id="ddid"></s:hidden>
<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
<s:hidden name="morre" id="morre"></s:hidden>
<input type="submit" style="display: none" name="submit" id="submit"/>


</form>
<div id="prompt" style="width: 100%;position: absolute;top: 0px;height: 100%;display: none;
            background: rgba(0,0,0, 0.5);">
    <center>
        <div>
            <span style="position: relative;top: 10%;"></span>
        </div>
    </center>
</div>
<style type="text/css">
#prompt div {
    border-radius: 0.5rem;
    height: 3rem;
    /*width: 8rem;*/
    vertical-align: center;
    margin: 20% auto;
    font-size: 0.6rem;
    color: #fff;
    padding: 0.5rem;
}

</style>
<script>
$(document).ready(function () {
$(".header ul li").css("line-height", $(window).height() * 0.08 + "px");
$(".header").css("height", $(window).height() * 0.08 + "px");
$(".content_hidden").css("height", $(window).height() * 0.92 + "px");
$(".content").css("height", $(window).height() * 0.92 - 40 + "px");
$(".so_shop ul li img").css("height", $(".so_shop ul li img").width() + "px");

    if(lastPay=="yes"){
        $(".fh-li").css("visibility","hidden");
        $(".cfd").hide();
        $(".det").hide();
        $(".mil_det").hide();
        prompt("您上次购物未完成支付，请先支付上次购物待支付金额！");
    }else if(lastPay=="close"||gd=="智能货柜"){
        $(".fh-li").css("visibility","hidden");
        $(".cfd").hide();
        $(".det").hide();
        $(".mil_det").hide();
        if(status=="01"&&lastPay=="close"){
               //未付款

            $(".title-lis").text("请付款");
        }else{
            $(".title-lis").text("购买成功");
        }
    }

})
function prompt(obj) {
    $("#prompt").find("span").text("");
    if ($("#prompt").css("display") != "none")
        return;
    $("#prompt").find("span").text(obj);
    $("#prompt").fadeIn(500);
    setTimeout(function () {
       $("#prompt").fadeOut(500);
    }, 3000);
}
</script>

<script>
// var num1=num2=num3=0
window.onload = window.onresize = function () {
//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
//获取窗口的尺寸
var clientWidth = document.documentElement.clientWidth;
//console.log(clientWidth);
//通过屏幕宽度去设置不同的后台根字体的大小
//document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px'
}
function goodsDetail(ppid, goodsid, companyId) {
window.location.href = basePath + "/ea/wfjshop/ea_doodsDetail.jspa?ppid=" + ppid + "&goodsid=" + goodsid + "&companyId=" + companyId;
}
</script>


</body>

</html>

