<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>确认订单</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/fontscroll.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/NEWjsp/jqModal.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/ea/unmannedsupermarket/classifyComponent.css">

    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/jqModal_blue.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/wfjeshop/OrderDetails.js"></script>
    <script src="<%=basePath%>/js/ea/unmannedsupermarket/classifyComponent.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/style11.css" type="text/css"></link>

    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/my.css" type="text/css"></link>
</head>
<script type="text/javascript">
    var adrss = "${staffaddress.addressDetailed}";
    var basePath = "<%=basePath%>";
    var ddid = "";
    var zffs = 1;//选择的支付方式   默认为支付宝
    var producttype = "${productDesign.type}";
    var tradename = "${productDesign.tradeName}";
    var model = "${productDesign.model}";
    var token = 0;
    var staffID = "${staffID}";
    var ppids = "${ppids}";
    var sccid = "${sccid}"
    var mk = "${param.mk}";
    var user1 = "${user1}";
    <%--var jifenshu = "${jifenshu}";--%>
    var jifenshu = parseFloat(jifenshu).toFixed(2);
    var bonusPoints = "${bonusPoints}";
    if(bonusPoints==""){
        bonusPoints = 0;
    }
    var jineshu = "${jineshu}";
    //    var jineshu = parseFloat(jineshu).toFixed(2);
    var gold = "${gold}";
    var goumaitype = "";
    var panduan = "00";
    var buyIsOkPage = "${buyIsOkPage}";
    var staffName = "${key_staff.staffName}";
    var companyIdentifier = "${companyIdentifier}";
    var companyID = "${company.companyID}";
    var  companyName = "${company.companyName}";
    var bbb = true;
    var status = "${status}";

    var journalNum = "${param.journalNum}";
    var mode = "${param.mode}";
    var totalMoney = "${param.totalMoney}";
    var totalNum = "${param.totalNum}";
    var cardNum = "${param.cardNum}";
    var sccId = "${param.sccId}";
    var posNum = "${param.posNum}";

    var addressID = "${staffAddress.addressID}";
    var consignee = "${staffAddress.consignee}";
    var phone = "${staffAddress.phone}";
    var noviceAddress = "${staffAddress.area}${staffAddress.addressDetailed }";
    var ptppid = "${param.ptppid}";
    var ptstandard = "${param.ptstandard}";
    var paymentCode = "${param.paymentCode}";
    var activityid="${actPriceId[1]}";
    var bind = 0;
    var user = "${user}";
    var openid = "${param.openid}";
    $(function () {
        //商品价格类型判断
        // 价格类别[cx:促销价,tj:特价,vip:VIP价,yj:原价(零售价)]
        var priceType = "${priceType}";
        if (priceType == 'yj') {
            $("#flagPrice").remove();
            $("#priceType").val(0);
        }
        if (priceType == 'pf') {
            $("#flagPrice").remove();
            $("#priceType").val(1);
        }
        if (priceType == 'tj') {
            $("#flagPrice").attr("class", "tj");
            $("#priceType").val(4);
        }
        if (priceType == 'cx') {
            $("#flagPrice").attr("class", "cx");
            $("#priceType").val(3);
        }
        if (priceType == 'vip') {
            $("#flagPrice").attr("class", "vip");
            $("#priceType").val(2);
        }

        if (mk == "nomk") {
            $(".mk").css("display", "block");
        }



        if (parseFloat(bonusPoints) - accMul(parseFloat(jineshu) , parseFloat("100")) < 0) {
            $(".integral_ span").text("(您的积分不足，无法选择)").show();
            $(".integral_ .second").hide()
            $("integral_").attr("style","pointer-events: none")
        } else if (bonusPoints == "" || isNaN(bonusPoints) || bonusPoints == null) {
            $(".integral_ span").text("(您积分为0,无法选择)");
            $(".integral_ .second").hide()
            $("integral_").attr("style","pointer-events: none")
        } else if (parseFloat(bonusPoints) - accMul(parseFloat(jineshu) , parseFloat("100")) >= 0) {
            //goumaitype = "jifen";
        } else {
            $(".integral_ span").text("(您的积分不足，无法选择)");
            $("integral_").attr("style","pointer-events: none")
            $(".integral_ .second").hide()
        }
        if(status!=null && status!=""){
            $(".gold span").text("(您的金币已冻结，无法选择)").show();
            $(".gold .second").hide()
            $("integral_").attr("style","pointer-events: none")
        }else if (parseFloat(gold) - accMul(parseFloat(jineshu) , parseFloat("100")) < 0) {
            $(".gold span").text("(您的金币不足，无法选择)").show();
            $(".gold .second").hide()
            $("integral_").attr("style","pointer-events: none")
        } else if (gold == "" || isNaN(gold) || gold == null) {
            $(".gold span").text("(您金币为0,无法选择)");
            $(".gold .second").hide()
            $("integral_").attr("style","pointer-events: none")
        } else if (parseFloat(gold) - accMul(parseFloat(jineshu) , parseFloat("100")) >= 0) {
            //goumaitype = "jifen";
        } else {
            $(".gold span").text("(您的金币不足，无法选择)");
            $(".gold .second").hide()
            $("integral_").attr("style","pointer-events: none")
        }

    });


</script>

<script type="text/javascript">
    $(function () {
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
<div class="wfj11_015">
    <form id="formsutm" method="post">
        <input type="hidden" value="${ppids}" id="ppids" name="ppids"/>
        <div class="wfj11_015_top">

            <input type="submit" id="submit" style="display: none;"/>
            <ul id="tops">
                <li><a onclick="javascript: window.history.go(-1);return false;" target="_self"><img
                        src="<%=basePath%>/images/WFJClient/Newjspim/wfj_return_02.png"/>
                </a>
                </li>
                <li>确认订单</li>
                <li>
                </li>
            </ul>
        </div>
        <div class="wfj11_015_body">
            <div class="wfj11_015_hidden">
                <c:if test="${param.mode ne null}" >   <a onclick="addAddress()"  target="_self"></c:if>
<c:if test="${param.mode eq null}" >
<a href="<%=basePath %>ea/wfjshop/ea_getAddressList.jspa?inventory.inventoryID=${inventory.inventoryID}&ppid=${ppid}&count=${count}&companyId=${company.companyID}&ccompanyId=${ccompanyId}&mk=${param.mk}&ptppid=${ptppid}&carType=${param.carType}"
                   target="_self">
    </c:if>
                    <div class="wfj11_015_consignee">
                        <div class="wfj11_015_width">
                            <table>
                                <tr>
                                    <td width="10%"><input type="hidden"
                                                           value="${staffAddress.addressID }"
                                                           name="staffAddress.addressID"
                                                           id="addressDetailed"/></td>
                                    <td width="40%">收货人：<span>${staffAddress.consignee}</span>
                                    </td>
                                    <td width="40%">${staffAddress.phone}</td>
                                    <td width="10%"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <!-- <img style="width:60%;"
								src="<%=basePath%>/images/WFJClient/Newjspim/wfj11_address_01.png" /> -->
                                    </td>
                                    <td colspan="2">收货地址：${staffAddress.area}${staffAddress.addressDetailed }</td>
                                    <td align="right">
                                    </td>
                                </tr>
                            </table>

                        </div>
                        <div style="left:5px;" class="img_ico img_ico1">
                            <p style="display:table-cell;vertical-align:middle;">
                                <img style="width:60%;" class="left"
                                     src="<%=basePath%>/images/WFJClient/Newjspim/wfj11_address_01.png"/>
                            </p>
                        </div>
                        <div style="right:10px;" class="img_ico">
                            <p style="display:table-cell;vertical-align:middle;">
                                <img class="right" style="width:50%;"
                                     src="<%=basePath%>/images/WFJClient/Newjspim/wfj_return_03.png"/>
                            </p>
                        </div>
                    </div>
                </a>

                <div id="FontScroll">
                    <div class="gonggao_tu">
                        <p>
                            <img src="<%=basePath %>images/ea/finance/BenDis/jinbi_03.jpg" alt="" />
                        </p>
                    </div>
                    <ul>
                        <c:forEach items="${jf}" var="d">
                            <li><img src="<%=basePath %>images/ea/finance/BenDis/hongdian_06.png" alt=""
                                     style="width: 0.3rem;"/>

                                <span>恭喜${d[1]}获得${d[0]}枚金币</span></li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="wfj11_015_com">
                    <div class="wfj11_015_width div-mc-img">
                    	<img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">
                        <a
                                href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?companyId=${company.companyID}">
                            ${company.companyName}</a>
                    </div>
                </div>
                <!--产品列表 -->
                <!-- 计算金额  -->
                <%int privce = 0;%>
                <div class="wfj11_015_proinfo">
                    <div class="wfj11_015_width">
                        <table width="100%">
                            <tr>
                                <td width="30%" rowspan="3"><span id="flagPrice"><i></i></span>
                                    <c:choose>
                                    <c:when test="${productDesign.image==null || productDesign.image==''}">
                                        <img class="img-gs" src="<%=basePath %>/images/WFJClient/zwtp160.png" style="cursor: auto;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img style="width:4.6rem;height:3.6rem;cursor: auto;" class="img-gs"
                                             src="<%=basePath %>${productDesign.image}"/>
                                    </c:otherwise>
                                </c:choose></td>
                                <td width="35%" colspan="2">${productDesign.goodsName}</td>
                                <input type="hidden" value="${productDesign.goodsName}"
                                       id="goodsName" name="sort" class="goodsName"/>
                                <input type="hidden" value="${ccompanyId}"
                                       name="ccompanyId"/>
                                <%-- 						<input type="hidden" value="${company.companyID }" class="companyId"/> --%>
                            </tr>
                            <tr>
                                <td colspan="2">${standard}
                                    <input type="hidden" value="${standard}"
                                           name="standard"/>

                                </td>
                            </tr>
                            <tr>
                                <td>￥<fmt:formatNumber pattern="0.00" value="${morre}"/></td>
                                <input type="hidden" value="<fmt:formatNumber pattern="0.00" value="${morre}" />"
                                       name="indus"
                                       id="indus"/>
                                <input type="hidden" value="${actPriceId[1]}" name="activityid" id="activityid"/>
                                <input type="hidden"  name="priceType" id="priceType"/>
                                <td width="35%" align="right">×${count}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                <c:if test="${ptlist ne null&&fn:length(ptlist)>0 }">
                    <hr style="margin: 0 0.6rem; border-top: 1px solid #ddd;">
                    <div class="zs-goods">
                        <img src="<%=basePath%>/images/WFJClient/Newjspim/zs-goods.png">
                        <h2>促销赠送商品</h2>
                        <div class="clearfix"></div>
                    </div>
                    <c:forEach items="${ptlist }" var="entity" varStatus="status">
                        <hr style="margin: 0 0.6rem; border-top: 1px solid #ddd;">
                        <div class="con-ord_grd">
                            <input type="hidden" value="${entity[1] }" id="ptppid"/>
                            <input type="hidden" value="${entity[5] }" class="companyId"/>
                            <img src="<%=basePath %>${entity[3] }">
                            <div class="con-ord_grd_txt">
                                <span>${entity[0] }</span>
                                <h3>${ptstandard[status.index]=='规格暂无'?"":ptstandard[status.index] }</h3>
                                <h4>促销赠品产品</h4>
                                <p>x1</p>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </c:forEach>
                    <input type="hidden" value="" name="ptppid"/>
                    <input type="hidden" value="" name="companyId"/>
                    <input type="hidden" value="" name="ptmorre"/>
                    <input type="hidden" value="" name="ptstandard"/>
                </c:if>


                <div class="wfj11_015_delivery">
                    <div class="wfj11_015_width">
                        <table>
                            <tr>
                                <input class="ppid" type="hidden" value="${productDesign.ppID}" name="ppid"/>
                                <td width="25%">购买数量</td>
                                <td align="right"><input id="shuliag" type="text"
                                                         value="${count}" oninput="yz()" name="count"/>
                                </td>
                            </tr>
                            <c:if test="${cfList!=null}">
                                <tr>
                                    <input class="carID" type="hidden" name="carID"/>
                                    <td width="25%">选择车辆</td>
                                    <td align="right">
                                        <select class="che">
                                            <option date-carID="">请选择</option>
                                            <c:forEach items="${cfList}" var="c">
                                                <option date-carID="${c.carID}">${c.carNum}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>配送方式</td>
                                <td align="right">快递&nbsp;免邮<span></span>
                                </td>
                            </tr>
                            <tr>
                                <td>买家备注</td>
                                <td><input type="text" placeholder="选填" class="remark"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <%--<div class="integral">--%>
                    <%--<h3></h3>--%>
                    <%--<div class="slide_btn on">--%>
                        <%--<p id="slid"></p>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="mk" style="display:none;margin-top:5px;">填写注册推荐人：<input type="text" name="mkuserID"
                                                                                    placeholder="推荐人手机号"/></div>
                <c:if test='${productDesign.type=="公司会员" && haveCompany=="0"}'>
                    <input type="hidden" value="" name="" id="superaccount"/>
                    <hr style="margin: 0;border-top:10px solid #ddd;">
                    <div class="con-ord2" style="background: #fff; padding: 15px 0.5rem;">
                        <div class="com_head2"><img src="<%=basePath%>/images/WFJClient/Newjspim/huiyaun.png"></div>
                        <h3>填写公司名称</h3>
                        <div class="clearfix"></div>
                    </div>
                    <div class="member-information">
                        <table>
                            <tr>
                                <td>行业平台</td>
                                <td>
                                    <table style="border: none;">
                                        <tr>
<%--                                            <input type="hidden" name="industy" id="hanye"/>--%>
<%--                                            <input type="hidden" name="company.industryType" class="notnull"/>--%>
<%--                                            <input type="hidden" name="company.industryId" id="industryId"/>--%>
                                            <input type="hidden" id="hangyelb1" class="notnull">
                                            <td id="hangyelb" style="width:98%"  ></td>

                                            <td><img src="<%=basePath%>/images/WFJClient/Newjspim/013_choose.png"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>公司名称</td>
                                <td><input name="company.companyName" id="companyName" class="notnull" type="text">

                                </td>
                            </tr>
                            <tr style="display:none;">
                                <td>机构名称</td>
                                <td><input id="companyIdentifier" name="company.companyIdentifier" maxlength="20"
                                           class="notnull" type="text"></td>
                            </tr>
                            <tr style="display:none;">
                                <td>详细地址</td>
                                <td><input name="cdl.companyAddress" class="notnull" type="text" value="1"></td>
                            </tr>
                            <tr style="display:none;">
                                <td>注册号</td>
                                <td>
                                    <div class="showPlaceholder loginFormIpt">
                                        <input type="text" style="width:100%;height:100%;" id="pwdzch" value="1"
                                               name="cdl.registrationNumber" class="notnull"/>
                                        <label for="pwdzch" class="placeholder" id="pwdPlaceholder">公司营业执照注册号</label>
                                    </div>
                                </td>
                            </tr>
                            <tr style="display:none;">
                                <td>公司邮箱</td>
                                <td><input type="text" id="email" name="cdl.companyEmail" class="notnull" value="1">
                                </td>
                            </tr>
                            <tr style="display:none;">
                                <td>法人代表</td>
                                <td><input type="text" name="cdl.companyManager" maxlength="10" class="notnull"
                                           value="1"></td>
                            </tr>
                            <tr style="display:none;">
                                <td>联系电话</td>
                                <td><input type="text" name="cdl.companyPhone" maxlength="11" class="notnull" value="1">
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:if>
                <c:if test='${productDesign.type=="个人会员"}'>
                    <c:if test='${productDesign.price=="0"}'>
                        <input type="hidden" value="" name="" id="superaccount"/>
                        <hr style="margin: 0;border-top:10px solid #ddd;">
                        <div class="con-ord2" style="background: #fff; padding: 15px 0.5rem;">
                            <div class="com_head2"><img src="<%=basePath%>/images/WFJClient/Newjspim/huiyaun.png"></div>
                            <h3>填写店铺名称</h3>
                            <div class="clearfix"></div>
                        </div>
                        <div class="member-information">
                            <table>
                                <tr>
                                    <td>行业类别</td>
                                    <td>
                                        <table style="border: none;">
                                            <tr>
                                                <input type="hidden" name="company.industryType" class="notnull"/>
                                                <td id="hangyelb"></td>
                                                <td><img src="<%=basePath%>/images/WFJClient/Newjspim/013_choose.png">
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>店铺名称</td>
                                    <td><input name="company.companyName" id="companyName" class="notnull" type="text">
                                    </td>
                                </tr>
                                <tr style="display:none;">
                                    <td>机构名称</td>
                                    <td><input id="companyIdentifier" name="company.companyIdentifier" maxlength="20"
                                               class="notnull" type="text"></td>
                                </tr>
                                <tr style="display:none;">
                                    <td>详细地址</td>
                                    <td><input name="cdl.companyAddress" class="notnull" type="text" value="1"></td>
                                </tr>
                                <tr style="display:none;">
                                    <td>注册号</td>
                                    <td>
                                        <div class="showPlaceholder loginFormIpt">
                                            <input type="text" style="width:100%;height:100%;" id="pwdzch" value="1"
                                                   name="cdl.registrationNumber" class="notnull"/>
                                            <label for="pwdzch" class="placeholder"
                                                   id="pwdPlaceholder">公司营业执照注册号</label>
                                        </div>
                                    </td>
                                </tr>
                                <tr style="display:none;">
                                    <td>公司邮箱</td>
                                    <td><input type="text" id="email" name="cdl.companyEmail" class="notnull" value="1">
                                    </td>
                                </tr>
                                <tr style="display:none;">
                                    <td>法人代表</td>
                                    <td><input type="text" name="cdl.companyManager" maxlength="10" class="notnull"
                                               value="1"></td>
                                </tr>
                                <tr style="display:none;">
                                    <td>联系电话</td>
                                    <td><input type="text" name="cdl.companyPhone" maxlength="11" class="notnull"
                                               value="1"></td>
                                </tr>
                            </table>
                        </div>
                    </c:if>
                </c:if>
                <c:if test='${productDesign.tradeName=="B102汽车交通工具>z30001汽车驾校"}'>
                    <div class="member-information" id="novice">
                        <div style="background: #f5f5f5; padding: 15px 0"><span class="vipinfo">学员信息</span></div>
                        <input type="hidden" value="" name="" id="superaccount"/>
                        <table>
                            <tr>
                                <td width="25%" align="center">学员姓名</td>
                                <td width="75%"><input type="text" value="${staffAddress.consignee}"
                                                       name="noviceName" id="noviceName" class="isNotNull"/>
                                </td>
                            </tr>
                            <tr>
                                <td width="25%" align="center">学员手机号</td>
                                <td width="75%"><input type="text" value="${staffAddress.phone}"
                                                       id="novicePhone" name="novicePhone" maxlength="20"
                                                       class="isNotNull"/>
                                </td>
                            </tr>
                            <tr>
                                <td width="25%" align="center">学员身份证号</td>
                                <td width="75%"><input type="text" value=""
                                                       name="noviceCode" class="isNotNull" id="noviceCode"/>
                                </td>
                            </tr>
                            <tr>
                                <td width="25%" align="center">学员住址</td>
                                <td width="75%"><input type="text"
                                                       value="${staffAddress.area}${staffAddress.addressDetailed }"
                                                       name="noviceAddress" class="isNotNull" id="noviceAddress"/>
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:if>

                <div class="wfj11_015_buy_commit" style="display:none;">
                    <div class="wfj11_015_need">
                        <div class="wfj11_015_width">
                            <ul>
                                <li class="left">需支付：</li>
                                <li class="right"><span>￥</span><span class="xzf"></span></span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="wfj11_015_allbay">
                        <div class="wfj11_015_width">
                            <table>
                                <tr>
                                    <td colspan="2">选择支付方式</td>
                                </tr>
                                <tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
                                            width="24" height="24" name="1"/>
                                    </td>
                                </tr>
                                <%--<tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                        src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_02.png" />
                                    </td>
                                    <td class="second" align="right"><img
                                        src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                        width="24" height="24" name="2" />
                                    </td>
                                </tr>
                                --%>
                                <tr class="wfj11_015_choice wechat">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                            width="24" height="24" name="3"/>
                                    </td>
                                </tr>
                                <c:if test="${param.mode ne 'scan'}">
                                <tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                            width="24" height="24" name="4"/>
                                    </td>
                                </tr>
                                <tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                            width="24" height="24" name="5"/>
                                    </td>
                                </tr>
                                </c:if>
                                <%--金币--%>
                                <tr class="wfj11_015_choice gold">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/gold.png"/>
                                        <span style="font-size: 12px"></span>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                            width="24" height="24" name="6"/>
                                    </td>
                                </tr>
                                <%--积分--%>
                                <tr class="wfj11_015_choice integral_">
                                    <td align="left"><img class="all_pay"
                                                      src="<%=basePath%>/images/WFJClient/Newjspim/jifen.png"/>
                                        <span style="font-size: 12px"></span>
                                    </td>
                                    <td class="second" align="right"><img
                                        src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                        width="24" height="24" name="7"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td colspan="2" align="center">
                                        <div id="paycommit"
                                             onclick="zf()">确认支付
                                        </div>
                                    </td>
                                </tr>

                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="wfj11_015_bottom">
            <ul>
                <li id="commit"><a href="javascript:;">确认订单</a></li>
                <li id="footing">${(param.vipmoney ne null&&param.vipmoney ne "")?'会员专享价：':'合计：'}<span id="sapnqian"></span></li>
                <input type="hidden" id="morre" name="morre"/>
                <input type="hidden" value="${recordId}" name="recordId"/>
            </ul>
        </div>
    </form>
</div>
<div class="menu-modal" id="menu-modal">
    <div class="menu-content" id="menu-content">

        <!-- 固定头部 -->
        <div class="menu-header">
            <span class="menu-back" onclick="hidewin()">返回</span>
        </div>

        <!-- 固定搜索框 -->
        <div class="div-search">
            <input type="text" id="type_search" placeholder="请输入搜索内容">
        </div>

        <!-- 可滚动内容 -->
        <div class="menu-body" id="menu-body">
            <!-- JS 动态内容插入这里 -->
        </div>

    </div>
</div>
<div class="hangyelb_"></div>
<div class="hangyelb">
    <h3>选择行业 <img src="<%=basePath%>/images/WFJClient/Newjspim/x2.png"></h3>
    <div class="clearfix"></div>
    <div class="hylb_p" id="hylb_p">
    </div>
</div>
<div class="hangyelb_ hangyelb2_"></div>
<div class="hangyelb hangyelb2">
    <h3><img src="<%=basePath%>/images/WFJClient/Newjspim/x2.png"></h3>
    <div class="hylb_p" id="hylb_p2">
    </div>
</div>
<div class="hangyelb_ hangyelb3_"></div>
<div class="hangyelb hangyelb3">
    <h3><img src="<%=basePath%>/images/WFJClient/Newjspim/x2.png"></h3>
    <div class="hylb_p" id="hylb_p3">
    </div>
</div>

<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>
</body>
<%--商品价格四舍五入计算--%>
<script type="text/javascript">
    var sumMoney = "${morre}";
    if(sumMoney == 0){
        $("#commit a").text("领取奖品");
    }
    var vipmoney = "${param.vipmoney}";
    if(vipmoney!=null&&vipmoney!=""){
        sumMoney = vipmoney;
    }

    var shuliang=$("#shuliag").val();
    var qian=Number(sumMoney)*Number(shuliang);
    qian = qian.toFixed(2);

    $("#morre").val(qian);
    $("#sapnqian").html("￥" + qian);
    $(".xzf").html(qian)



</script>
</html>
