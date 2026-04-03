<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>


<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head lang="en">
    <base href="<%=basePath%>">
    <title>购物车详细页面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/NEWjspcss/city.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var pahe = "<%=basePath%>";
        var num = "${num}";
        var scandc = "${scandc}";
        var companyId = "";
        var ccompanyId = "";
        var pos = "${pos}";
        var coID = '<%=session.getAttribute("coID")%>';
        var pricetype = "${param.pricetype}";
        var posNum = "${param.posNum}";

        if ("${comps}" != null) {
            companyId = "${comps[0][0]}";
            ccompanyId = "${comps[0][2]}";

        }
        $(function () {
            if (scandc != "scandc") {
               /*  if (window.name != "bencalie") {
                   location.reload();
                    window.name = "bencalie";
                }
                else {
                   window.name = "";
                } */
            } else {
                $(".bk").html("&nbsp;");

            }
            if (pos == "1") {
                $(".bk").html("&nbsp;");

            }


        })


    </script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/NEWjsp/ap.js"></script>
</head>
<body>
<div class="wfj12_004">
    <!--中联园区头部-->

    <div class="wfj_top">
        <ul>
            <li class="bk">
                <a onclick="javascript: window.history.go(-1);return false;" target="_self" class="bhj"><img
                        src="<%=basePath%>images/WFJClient/DigitalMall/left_jt.png"/>
                </a>
            </li>
            <li>购物车</li>
            <li>
                <img width="100" height="80" src="<%=basePath%>/images/WFJClient/Newjspim/shopcar.png"/>
            </li>
        </ul>
    </div>

    <div class="wfj12_004_content">
        <div class="wfj12_004_hidden" id="allprod">

            <div class="kgwc" style="display:none;"><img src="<%=basePath%>images/WFJClient/Newjspim/emptycar.png">
            </div>

            <c:forEach items="${comps}" var="com">

                <div class="changes bj01">
                    <div class="wfj12_004_con">
                        <!--一个公司-->
                        <div class="wfj12_004_title">
                            <div class="wfj12_004_height">
                                <div class="wfj12_004_width">
                                    <ul>
                                        <li id="choice"><a href="javascript:;"><img
                                                class="change_imgs" src="<%=basePath%>/images/choice_02.png"/>
                                        </a>
                                        </li>
                                        <li class="title"><a
                                                href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${com[2]}"><img
                                                class="logopic"
                                                src="<%=basePath%>${com[3]!=null?com[3]:'images/WFJClient/PersonalJoining/logo@2x.png'}">&nbsp;${com[1]}
                                        </a>
                                            <input type="hidden" value="${com[0]}" class="fcomid"/>
                                        </li>
                                        <li class="changefont change"><a href="javascript:;">编辑</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--一个公司 end-->
                        <c:forEach items="${beanList}" var="entity">

                            <c:if test="${com[0] eq entity[5]}">
                                <!--产品-->
                                <div class="wfj12_004_product zcp ${entity[0]}zcp">
                                    <div class="wfj12_004_width">
                                        <div class="wfj12_004_choice">
                                            <ul>
                                                <li><img class="change_img ${entity[5]}"
                                                         src="<%=basePath%>/images/choice_02.png"/>
                                                </li>
                                            </ul>
                                            <input type="hidden" class="dx" name="xuanz" value="2"/>
                                            <input type="hidden" class="ppid" name="ppid" value="${entity[0]}"/>
                                            <input type="hidden" value="${entity[5]}" class="companyId ${entity[5]}"/>
                                            <input type="hidden" class="cartid" value="${entity[9]}"/>
                                        </div>
                                        <div class="wfj12_004_proimg">
                                            <img src="<%=basePath%>${entity[2]}"/>
                                            <c:choose>
                                                <c:when test="${entity[12]!=null&& entity[10]!=null}">
                                                    <c:if test="${entity[12]=='00'}"><%--促销活动--%>
                                                        <span class="cx"><i></i></span>
                                                    </c:if>
                                                    <c:if test="${entity[12]=='01'}"><%--特价活动--%>
                                                        <span class="tj"><i></i></span>
                                                    </c:if>
                                                    <%--<span class="qian"
                                                          id="price">${entity[10]}</span>&lt;%&ndash;活动价&ndash;%&gt;--%>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${entity[11]!=null&&param.posNum eq null&& cusType=='vip'}">
                                                            <span class="vip"><i></i></span><%--VIP价--%>
                                                        </c:when>
                                                        <%-- <c:otherwise>
                                                                     <span class="qian"
                                                                           id="price">${entity[4]}</span>&lt;%&ndash;零售价&ndash;%&gt;
                                                         </c:otherwise>--%>
                                                    </c:choose>

                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="wfj12_004_proInfo proinfo02 oneProduct">
                                            <ul>
                                                <li>${entity[1]}</li>
                                            </ul>
                                            <ul>
                                                <li class="stard">${entity[8]}</li>
                                            </ul>
                                            <ul>
                                                <li class="left">￥
                                                    <c:choose>
                                                        <c:when test="${entity[12]!=null&& entity[10]!=null}">
                                                            <c:if test="${entity[12]=='00'}"><%--促销活动--%>
                                                            </c:if>
                                                            <c:if test="${entity[12]=='01'}"><%--特价活动--%>
                                                            </c:if>
                                                            <span class="qian"
                                                                  id="price">${entity[10]}</span><%--活动价--%>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${entity[11]!=null && cusType=='vip'&&param.posNum eq null}">
                                                                    <span class="qian"
                                                                          id="price">${entity[11]}</span><%--VIP价--%>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span class="qian"
                                                                          id="price">${entity[4]}</span><%--零售价--%>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:otherwise>
                                                    </c:choose>
                                                </li>
                                                <li class="right">x<span id="num">${entity[7]}</span>
                                                </li>
                                            </ul>
                                        </div>
                                        <div class="wfj12_004_changedelete deletes01"
                                             style="display:none;">
                                            <div class="wfj12_004_nums">
                                                <ul class="borders">
                                                    <li class="li1">-</li>
                                                    <li id="nums"><input type="text" readonly="readonly"
                                                                         value="${entity[7]}" class="ls"/>
                                                    </li>
                                                    <li class="li2">+</li>
                                                </ul>

                                                <ul>
                                                    <c:choose>
                                                        <c:when test="${entity[12]!=null&& entity[10]!=null}">
                                                            <li style="font-size: 15px;">单价:￥
                                                                <span id="oprPrice">${entity[10]}</span><%--活动价--%>
                                                            </li>
                                                            <li style="font-size: 15px;">小计:￥<span
                                                                    id="oprSum"><fmt:formatNumber
                                                                    value="${entity[10]*entity[7]}"
                                                                    type="NUMBER"
                                                                    maxFractionDigits="2"/></span>
                                                            </li>
                                                            <input type="hidden" value="${entity[10]}" id="moue"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${entity[11]!=null}">
                                                                    <li style="font-size: 15px;">单价:￥
                                                                        <span id="oprPrice">${entity[11]}</span><%--VIP价--%>
                                                                    </li>
                                                                    <li style="font-size: 15px;">小计:￥<span
                                                                            id="oprSum"><fmt:formatNumber
                                                                            value="${entity[11]*entity[7]}"
                                                                            type="NUMBER"
                                                                            maxFractionDigits="2"/></span>
                                                                    </li>
                                                                    <input type="hidden" value="${entity[11]}"
                                                                           id="moue"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <li style="font-size: 15px;">单价:￥
                                                                        <span id="oprPrice">${entity[4]}</span><%--零售价--%>
                                                                    </li>
                                                                    <li style="font-size: 15px;">小计:￥<span
                                                                            id="oprSum"><fmt:formatNumber
                                                                            value="${entity[4]*entity[7]}"
                                                                            type="NUMBER"
                                                                            maxFractionDigits="2"/></span>
                                                                    </li>
                                                                    <input type="hidden" value="${entity[4]}"
                                                                           id="moue"/>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </c:otherwise>
                                                    </c:choose>
                                                    <input type="hidden" id="editppid" value="${entity[0]}"/>
                                                </ul>
                                            </div>
                                            <div class="wfj12_004_delete">
                                                <ul>
                                                    <li class="dele">删除</li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <%--促销品--%>
                                <c:forEach items="${ccmap}" var="item">
                                    <c:if test="${item.key==entity[0]}">
                                        <c:forEach items="${item.value}" var="subitem">
                                            <div class="wfj12_004_product ${entity[0]} ${entity[9]}">
                                                <div class="wfj12_004_width">
                                                    <div class="wfj12_004_choice">
                                                        <ul>
                                                            <li>
                                                                &nbsp;
                                                            </li>
                                                        </ul>
                                                    </div>
                                                    <div class="wfj12_004_proimg">
                                                        <img src="<%=basePath%>${subitem[6]}"/>
                                                    </div>
                                                    <div class="wfj12_004_proInfo  oneProduct">
                                                        <ul>
                                                            <li>${subitem[4]}</li>
                                                        </ul>
                                                        <ul>
                                                            <li class="stard">${subitem[2]}</li>
                                                        </ul>
                                                        <ul>
                                                            <li class="left">
                                                                <img src="<%=basePath%>images/ea/finance/NewPhoneOrders/ico-cu.png"
                                                                     class="cu">
                                                            </li>
                                                            <li class="right">x<span id="num">1</span>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>

                                            </div>
                                        </c:forEach>

                                    </c:if>

                                </c:forEach>
                                <!-- 产品结束 -->
                            </c:if>
                        </c:forEach>

                    </div>
                    <!--一个公司 end-->
                </div>

            </c:forEach>

        </div>

    </div>
    <!--所有订单 end-->
    <span id="shuliang" style="display:none;">0</span>
    <div class="wfj12_004_bottom">
        <div class="wfj12_004_width">
            <form method="post" id="form">
                <input type="hidden" name="ppid" id="ppic"/>
                <input type="hidden" name="companyId" id="companyIds"/>
                <input type="submit" style="display: none;" id="submit"/>
            </form>
            <div class="wfj12_004_allchecked">
                <ul>
                    <li><img class="allchangeimg" style="text-align: center;position:relative;left: 0px;"
                             src="<%=basePath%>/images/choice_02.png"/>
                    </li>
                    <li class="qx">全选</li>
                </ul>
            </div>
            <div class="wfj12_004_allmoney">
                <ul>
                    <li>合计：</li>
                    <li>￥<span id="zh">0</span>
                    </li>
                </ul>
            </div>
            <div class="ordercommit" style="display:none;">生成订单</div>
            <div class="wfj12_004_commit">结算</div>

        </div>
    </div>

</div>


</div>

<script>
    $(".wfj12_004 .wfj12_004_con .wfj12_004_product div .wfj12_004_proimg").css("height", $(".wfj12_004 .wfj12_004_con .wfj12_004_product div .wfj12_004_proimg").width() + "px");
</script>

</body>
</html>
