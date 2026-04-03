<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base>
    <title>购物车确认订单</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, count-scalable=yes"/>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/scMobile/wholesaleMall/shoppingCart/jqModal.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/style11.css" type="text/css"></link>    <link rel="stylesheet" href="<%=basePath%>/css/scMobile/wholesaleMall/shoppingCart/jqModal_blue.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/scMobile/wholesaleMall/settlement/orderDetails.js" type="text/css"></script>
    <script type="text/javascript" src="<%=basePath %>/js/scMobile/wholesaleMall/settlement/ap.js"></script>
    <style type="text/css">
        .placeholder {
            color: #C9C9C9;
            font-size: 14px;
            position: absolute;
            left: 3px;
            top: 6px;
            line-height: 14px;
            visibility: hidden;
            background: none;
        }
        .showPlaceholder .placeholder {
            visibility: visible;
            cursor: text;
        }
        .loginFormIpt {
            position: relative;
            width: 100%;
            height: 25px;
            line-height: 25px;
            clear: both;
            background-position: 0 -352px;
            z-index: 2;
        }
    </style>
</head>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var adrss = '${mapObj.staffaddress.addressDetailed}';
    var user1 = "${mapObj.user1}";//当前登录人账号对象
    var staffID = "${mapObj.staffID}";
    //不知道干嘛的参数
    var ddid;
    var zffs = 1;//选择的支付方式   默认为支付宝
    var buyIsOkPage = "${buyIsOkPage}";
    var producttype = "${productDesign.type}";
    var model = "${productDesign.model}";
    var token = 0;
    var panduan = "00";
    var journalNum = "${param.journalNum}";
    var user = user1;

</script>
<body>
<div class="loading" style="display:none;">
    <img src="<%=basePath%>images/scMobile/wholesaleMall/shoppingCart/loading.gif"/>
    <p><span>加载中...</span></p>
</div>
<div class="wfj11_015">
    <form id="formsutm" method="post">
        <div class="wfj11_015_top">
            <input type="submit" id="submit" style="display: none;"/>
            <input type="hidden" id="companyIds" name="companyId"/><%--公司拼接id--%>
            <input type="hidden" id="pids" name="pid"/><%--购物车拼接信息--%>
            <input type="hidden" id="leavemessages" name="leavemessage"/><%--备注信息--%>
            <input type="hidden" name="pricetype" value="${param.pricetype}"/>
            <ul id="tops">
                <li>
                    <a onclick="toBack();" target="_self">
                        <img src="<%=basePath%>images/WFJClient/DigitalMall/left_jt.png"/>
                    </a>
                </li>
                <li>确认订单</li>
                <li>
                </li>
            </ul>
        </div>
        <div class="wfj11_015_body">
            <div class="wfj11_015_hidden">
                <%--跳转地址页面--%>
                <a href="<%=basePath %>ea/wfjshop/ea_getAddressList.jspa?ppid=${shoppingCartParmStr}&intf=40" target="_self">
                    <div class="wfj11_015_consignee">
                        <div class="wfj11_015_width">
                            <table>
                                <tr>
                                    <td width="10%">
                                        <input type="hidden" value="${mapObj.staffAddress.addressID }" name="staffAddress.addressID" id="addressDetailed"/>
                                    </td>
                                    <td width="40%">
                                        收货人：<span>${mapObj.staffAddress.consignee}</span>
                                    </td>
                                    <td width="40%">${mapObj.staffAddress.phone}</td>
                                    <td width="10%"></td>
                                </tr>
                                <tr>
                                    <td>
                                        <img style="width:60%;position: absolute!important; top: 35%!important;left:13.34px;" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/wfj11_address_01.png"/>
                                    </td>
                                    <td colspan="2">
                                        收货地址：${mapObj.staffAddress.area}${mapObj.staffAddress.addressDetailed }
                                    </td>
                                    <td align="right">
                                        <img style="width:40%;position: absolute!important; top: 33%!important;right:10px" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/wfj_return_03.png"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </a>
                <c:set value="0" var="c"></c:set>
                <!-- 产品 -->
                <c:forEach items="${mapObj.compList}" var="comEntity">
                    <!-- 公司 -->
                    <div class="wfj11_015_com">
                        <div class="wfj11_015_width div-mc-img">
                        	<img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">
                            <a href="javcript:"> ${comEntity[1]}</a>
                            <input type="hidden" class="companyId" value="${comEntity[0]}"/>
                        </div>
                    </div>
                    <!-- 公司结束 -->
                    <!--产品列表 -->
                    <c:forEach items="${mapObj.shoppingCartList}" var="cartEntity">
                        <c:if test="${cartEntity.companyid eq comEntity[0]}">
                            <div class="wfj11_015_proinfo">
                                <div class="wfj11_015_width">
                                    <table width="100%" class="ptbl">
                                        <tr>
                                            <td width="30%" rowspan="3">
                                                <input type="hidden" class="ppid" name="ppid" value="${cartEntity.ppid}"/>
                                                <input type="hidden" class="pscId" name="pscId" value="${cartEntity.pscId}"/>
                                                <c:choose>
                                                    <c:when test="${empty cartEntity.image }">
                                                        <img src="<%=basePath %>/images/WFJClient/zwtp160.png" class="img-gs" width="110" height="110"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<%=basePath %>${cartEntity.image}" class="img-gs" width="100" height="100"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td width="62%" colspan="2">${cartEntity.goodsName}</td>
                                            <input type="hidden" value="${cartEntity.goodsName}" class="goodsName" name="sort" id="goodsName"/>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="stardard">
                                                <c:if test="${!empty cartEntity.cmStr}">尺码：${cartEntity.cmStr}</c:if>
                                                <c:if test="${!empty cartEntity.ysStr}">颜色：${cartEntity.ysStr}</c:if>
                                   <%--              <c:if test="${!empty cartEntity.ftStr}">副图：${cartEntity.ftStr}</c:if>
                                                <c:if test="${!empty cartEntity.spStr}">视频：${cartEntity.spStr}</c:if> --%>
                                                <c:if test="${empty cartEntity.cmStr && empty cartEntity.ysStr}">${cartEntity.standard}</c:if>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                ￥${cartEntity.allPrice}
                                                <input type="hidden" name="price" class="price" value="${cartEntity.allPrice}"/>
                                                <input type="hidden" name="priceid" class="priceid" value="${cartEntity.wholesaleId}"/><%--批发价ID--%>
                                                <input type="hidden" name="prt" class="prt" value="1"/><%--价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动--%>
                                                <input type="hidden" name="ccj" class="ccj" value="${cartEntity.wholesale}"/> <%--批发价--%>
                                            </td>
                                            <input type="hidden" value="${cartEntity.tjNum}" class="num"/>
                                            <td width="8%" align="center">×${cartEntity.tjNum}</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                    <div class="wfj11_015_delivery">
                        <div class="wfj11_015_width">
                            <table>
                                <tr>
                                    <td>配送方式</td>
                                    <td align="right">快递&nbsp;免邮<span></span></td>
                                </tr>
                                <tr>
                                    <td>买家留言</td>
                                    <td><input type="text" placeholder="选填" class="leavemessage"/></td>
                                </tr>

                            </table>
                        </div>
                    </div>
                    <!-- 地址结束 -->
                </c:forEach>
                <div class="wfj11_015_bottom">
                    <ul>
                        <li id="commitmulti" class="commit">
                            <a href="javascript:;">确认订单</a>
                        </li>
                        <li id="footing">合计：<span id="sapnqian">￥${mapObj.total}</span></li>
                        <input type="hidden" value="${mapObj.total}" id="morre" name="morre"/>
                    </ul>
                </div>
                <div class="wfj11_015_buy_commit" style="display:none;">
                    <div class="wfj11_015_need">
                        <div class="wfj11_015_width">
                            <ul>
                                <li class="left">需支付：</li>
                                <li class="right"><span>￥${mapObj.total}</span></li>
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
                                    <td align="left">
                                        <img class="all_pay" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/all_pay_01.png"/>
                                    </td>
                                    <td class="second" align="right">
                                        <img src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/choice_01.png" width="24" height="24" name="1"/>
                                    </td>
                                </tr>
                                <tr class="wfj11_015_choice">
                                    <td align="left">
                                        <img class="all_pay" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/all_pay_03.png"/>
                                    </td>
                                    <td class="second" align="right">
                                        <img src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/choice_02.png" width="24" height="24" name="3"/>
                                    </td>
                                </tr>
                                <tr class="wfj11_015_choice">
                                    <td align="left">
                                        <img class="all_pay" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/xxzz.png"/>
                                    </td>
                                    <td class="second" align="right">
                                        <img src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/choice_02.png" width="24" height="24" name="4"/>
                                    </td>
                                </tr>
                                <tr class="wfj11_015_choice gold">
                                    <td align="left">
                                        <img class="all_pay" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/gold.png"/>
                                        <span style="font-size: 12px"></span>
                                    </td>
                                    <td class="second" align="right">
                                        <img src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/choice_02.png" width="24" height="24" name="6"/>
                                    </td>
                                </tr>
                                <%--积分--%>
                                <tr class="wfj11_015_choice integral_">
                                    <td align="left">
                                        <img class="all_pay" src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/jifen.png"/>
                                        <span style="font-size: 12px"></span>
                                    </td>
                                    <td class="second" align="right">
                                        <img src="<%=basePath%>/images/scMobile/wholesaleMall/shoppingCart/choice_02.png" width="24" height="24" name="7"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center">
                                        <div id="paycommit" onclick="zf()">确认支付</div>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <%--<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>--%>
            <div class="tanchu">
                <div class="oneindustry">
                    <ul id="oneindustry"></ul>
                </div>
                <div class="twoindustry" style="display:none;background:#FFF;">
                    <ul id="twoindustry"></ul>
                </div>
            </div>
            <%--<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>--%>
        </div>
    </form>
</div>
<div id="occlusion2" class="jqmWindow jqmWindowcss1" style="position:fixed;"></div>
<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>
</body>
<script>
    //后退
    function toBack() {
    window.history.go(-1);return false;
       // window.location.href = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa";
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
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
</script>
</html>
