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
    <base href="<%=basePath%>">

    <title>购物车确认订单</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/NEWjsp/jqModal.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/style11.css" type="text/css"></link>
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/jqModal_blue.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/wfjeshop/OrderDetails.js"
            type="text/css"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
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
    var adrss = '${staffaddress.addressDetailed}';
    var basePath = '<%=basePath%>';
    var buyIsOkPage = "${buyIsOkPage}";
    var user1 = "${user1}";
    var ddid;
    var zffs = 1;//选择的支付方式   默认为支付宝
    var producttype = "${productDesign.type}";
    var model = "${productDesign.model}";
    var token = 0;
    var staffID = "${staffID}";
    var panduan = "00";
    var journalNum = "${param.journalNum}";
    var user = user1;
</script>
<body>
<div class="loading" style="display:none;">
    <img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
    <p><span>加载中...</span></p>
</div>
<div class="wfj11_015">
    <form id="formsutm" method="post">
        <div class="wfj11_015_top">

            <input type="submit" id="submit" style="display: none;"/>
            <input type="hidden" id="companyIds" name="companyId"/>
            <input type="hidden" id="pids" name="pid"/>
            <input type="hidden" id="leavemessages" name="leavemessage"/>
            <input type="hidden" name="pricetype" value="${param.pricetype}"/>


            <ul id="tops">
                <li><a onclick="javascript: window.history.go(-1);return false;"
                       target="_self"><img
                        src="<%=basePath%>/images/WFJClient/Newjspim/wfj_return_02.png"/>
                </a>
                </li>
                <li>确认订单</li>
                <li>
                    </a>
                </li>
            </ul>
        </div>
        <div class="wfj11_015_body">
            <div class="wfj11_015_hidden">
                <a href="<%=basePath %>ea/wfjshop/ea_getAddressList.jspa?ppid=${ppid}&intf=1&pricetype=${param.pricetype}"
                   target="_self">
                    <div class="wfj11_015_consignee">
                        <div class="wfj11_015_width">
                            <table>
                                <tr>
                                    <td width="10%"><input type="hidden"
                                                           value="${staffaddress.addressID }"
                                                           name="staffAddress.addressID"
                                                           id="addressDetailed"/></td>
                                    <td width="40%">收货人：<span>${staffaddress.consignee}</span>
                                    </td>
                                    <td width="40%">${staffaddress.phone}</td>
                                    <td width="10%"></td>
                                </tr>
                                <tr>
                                    <td><img style="width:60%;position: absolute;font-size: 13.34px;top: 37%;left: 13.34px;"
                                             src="<%=basePath%>/images/WFJClient/Newjspim/wfj11_address_01.png"/>
                                    </td>
                                    <td colspan="2">收货地址：${staffaddress.area}${staffaddress.addressDetailed }</td>
                                    <td align="right"><img style="width:40%;position: absolute;font-size: 13.34px;top: 37%;right: 10px;"
                                                           src="<%=basePath%>/images/WFJClient/Newjspim/wfj_return_03.png"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </a>
                <c:set value="0" var="c"></c:set>
                <!-- 产品 -->
                <c:forEach items="${complist}" var="status">
                    <!-- 公司 -->
                    <div class="wfj11_015_com">
                        <div class="wfj11_015_width div-mc-img">
                        	<img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">
                            <a href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${status[2]}"> ${status[1]}</a>
                            <input type="hidden" class="companyId" value="${status[0]}"/>
                        </div>
                    </div>
                    <!-- 公司结束 -->
                    <!--产品列表 -->
                    <c:forEach items="${pplist}" var="pklist">
                        <c:if test="${pklist[5] eq status[0]}">

                            <div class="wfj11_015_proinfo">
                                <div class="wfj11_015_width">
                                    <table width="100%" class="ptbl">
                                        <tr>
                                            <td width="30%" rowspan="3">

                                                <c:choose>
                                                    <c:when test="${pklist[11]!=null&& pklist[9]!=null}">
                                                        <%--活动价--%>
                                                        <c:if test="${pklist[11]=='00'}"><%--促销活动--%>
                                                            <span class="sp cx"><i></i></span>
                                                        </c:if>
                                                        <c:if test="${pklist[11]=='01'}"><%--特价活动--%>
                                                            <span class="sp tj"><i></i></span>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${pklist[10]!=null && cusType=='vip'}">
                                                                <%--VIP价--%>
                                                                <span class="sp vip"><i></i></span>
                                                            </c:when>
                                                            <%-- <c:otherwise>
                                                                &lt;%&ndash;零售价&ndash;%&gt;
                                                             </c:otherwise>--%>
                                                        </c:choose>

                                                    </c:otherwise>
                                                </c:choose>

                                                <input type="hidden" class="ppid" name="ppid" value="${pklist[0]}"/>
                                                <c:choose><c:when test="${pklist[2]&&pklist[2]==''}"><img  class="img-gs"
                                                        src="<%=basePath %>/images/WFJClient/zwtp160.png"/></c:when>
                                                    <c:otherwise><img class="img-gs" src="<%=basePath %>${pklist[2]}" width="100"
                                                                      height="100"/></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td width="62%" colspan="2">${pklist[1]}</td>
                                            <input type="hidden" value="${pklist[1]}" class="goodsName" name="sort"
                                                   id="goodsName"/>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="stardard">${pklist[7]}</td>
                                        </tr>
                                        <tr>
                                                <%--<c:set var="c" value="${c+pklist[14]}"></c:set>
                                                    //价格类型 0或者null零售订单  1：批发价格 2：VIP  3. 普通活动 4.特价活动--%>
                                            <td>￥
                                                <c:choose>
                                                    <c:when test="${pklist[11]!=null&& pklist[9]!=null}">
                                                        <fmt:formatNumber pattern="#,##0.00"
                                                                          value="${pklist[9]*status[4]+0.0001}"/><%--活动价--%>
                                                        <input type="hidden" name="priceid" class="priceid"
                                                               value="${pklist[14]}"/>
${pklist[11]}
                                                        <c:if test="${pklist[11]=='00'}"><%--促销活动--%>
                                                            <input type="hidden" name="prt" class="prt" value="3"/>
                                                        </c:if>
                                                        <c:if test="${pklist[11]=='01'}"><%--特价活动--%>
                                                            <input type="hidden" name="prt" class="prt" value="4"/>
                                                        </c:if>

                                                        <input type="hidden" name="ccj" class="ccj"
                                                               value="${pklist[15]}"/>
                                                        <input type="hidden" name="price" class="price"
                                                               value="<fmt:formatNumber pattern="0.00" value="${pklist[9]*status[4]}"/>"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${pklist[10]!=null && cusType=='vip'}">
                                                                <fmt:formatNumber pattern="#,##0.00"
                                                                                  value="${pklist[10]*status[4]+0.0001}"/><%--VIP价--%>
                                                                <input type="hidden" name="priceid" class="priceid"
                                                                       value="${pklist[16]}"/>
                                                                <input type="hidden" name="prt" class="prt" value="2"/>
                                                                <input type="hidden" name="ccj" class="ccj"
                                                                       value="${pklist[17]}"/>
                                                                <input type="hidden" name="price" class="price"
                                                                       value="<fmt:formatNumber pattern="0.00" value="${pklist[10]*status[4]+0.0001}"/>"/>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <fmt:formatNumber pattern="#,##0.00"
                                                                                  value="${pklist[4]*status[4]+0.0001}"/><%--零售价--%>
                                                                <input type="hidden" name="priceid" class="priceid"
                                                                       value="${pklist[12]}"/>
                                                                <input type="hidden" name="prt" class="prt" value="0"/>
                                                                <input type="hidden" name="ccj" class="ccj"
                                                                       value="${pklist[13]}"/>
                                                                <input type="hidden" name="price" class="price"
                                                                       value="<fmt:formatNumber pattern="0.00" value="${pklist[4]*status[4]}"/>"/>
                                                            </c:otherwise>
                                                        </c:choose>

                                                    </c:otherwise>
                                                </c:choose>

                                            </td>
                                                <%--<input type="hidden" value="${inventory.unitPrice}" name="indus" id="indus"/>
                                                <input type="hidden" value="${inventory.warehouse}" name="weiid" id="weiid"/>--%>
                                            <input type="hidden" value="${pklist[6]}" class="num"/>
                                            <td width="8%" align="center">×${pklist[6]}</td>
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

                        <li id="commitmulti" class="commit"><a href="javascript:;">确认订单</a></li>
                        <li id="footing">合计：<span id="sapnqian">￥${total}</span></li>
                        <input type="hidden" value="${total}" id="morre" name="morre"/>
                    </ul>
                </div>

                <div class="wfj11_015_buy_commit" style="display:none;">
                    <div class="wfj11_015_need">
                        <div class="wfj11_015_width">
                            <ul>
                                <li class="left">需支付：</li>
                                <li class="right"><span>￥${total}</span></li>


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
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png" width="24"
                                            height="24" name="1"/></td>
                                </tr>
                                <%--<tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay" src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_02.png"/></td>
                                    <td class="second" align="right"><img src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png" width="24" height="24"  name="2"/></td>
                                </tr>--%>
                               <tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png" width="24"
                                            height="24" name="3"/></td>
                                </tr>
                                <tr class="wfj11_015_choice">
                                    <td align="left"><img class="all_pay"
                                                          src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png"/>
                                    </td>
                                    <td class="second" align="right"><img
                                            src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                            width="24" height="24" name="4"/>
                                    </td>
                                </tr>                                <%--金币--%>
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
                                        <div id="paycommit" onclick="zf()">确认支付</div>
                                    </td>
                                </tr>


                            </table>
                        </div>
                    </div>
                </div>


            </div>

            <div class="tanchu">
                <div class="oneindustry">
                    <ul id="oneindustry"></ul>
                </div>

                <div class="twoindustry" style="display:none;background:#FFF;">
                    <ul id="twoindustry"></ul>
                </div>
            </div>


        </div>
    </form>
</div>
<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>

</body>

</html>
