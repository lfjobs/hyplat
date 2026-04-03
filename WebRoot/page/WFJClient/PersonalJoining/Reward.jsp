<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
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
    TEshopCusCom te = (TEshopCusCom) session.getAttribute("key_shop_cus_com");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <title>&lrm;</title>
    <%--<script type="application/javascript" src="<%=basePath%>/js/jquery-2.1.1.min.js"></script>--%>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/font-size.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/NEWjsp/jqModal.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>\css\WFJClient\RewardStyle.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/jqModal_blue.css"></link>
    <script>
        var basePath = '<%=basePath%>';
        var sccid = '${sccid}';
        var ppid = '${ppid}';
        var sname = '${param.sname}';
        var staffID = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
        var zffs = "1";
        var t = "";
        var journalNum = "";
        var mval = "";
        var token = 0;
    </script>
</head>
<body>
<header>
    <ul>
        <li><a onclick="javascript: window.history.go(-1);return false;"  target="_self">
            <img src="<%=basePath%>\images\supermarket\left.png"
                                                                   alt=""></a></li>
        <li>${param.cate eq '慈善捐赠'?'爱心捐赠':'打赏'}</li>
        <li></li>
    </ul>
</header>
<section class="reward">
    <article>
        <div class="top">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/bg1.png">
            <div class="txt">
                <c:if test="${param.cate ne '慈善捐赠'}">
                    <p>随手赞赏作者</p>
                    <p>赞赏成功可获得相应的金额</p>
                </c:if>
                <c:if test="${param.cate eq '慈善捐赠'}">
                    <p>感谢您的捐赠！</p>
                    <p>我们承诺，您的每一笔捐赠都将公开透明地提供给受助人</p>
                </c:if>
            </div>
            <c:if test="${param.cate ne '慈善捐赠'}">

                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/zan.png" class="zan">
            </c:if>

            <c:if test="${param.cate eq '慈善捐赠'}">
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/love.png" class="zan">

            </c:if>
        </div>
        <h5>${param.cate eq '慈善捐赠'?'轻松公益，与爱相伴！':'作者也不容易 给TA点鼓励吧~'}</h5>
        <ul class="sj">
            <li value="1">1元</li>
            <li value="3">3元</li>
            <li value="5">5元</li>
            <li value="10">10元</li>
            <li value="20">20元</li>
            <li value="50">50元</li>
            <li value="100">100元</li>
            <input type="text" value="" placeholder="请填金额" onfocus="this.placeholder=''"
                   onblur="this.placeholder='请填金额'" id="mnvl">
        </ul>
        <c:if test="${rnum>0}">
            <div class="dsr">
                <p>${rnum}人${param.cate eq '慈善捐赠'?'捐赠':'答谢'}过</p>
            </div>
        </c:if>
        </div>
        <input type="button" value="${param.cate eq '慈善捐赠'?'立即捐赠':'立即打赏'}" id="ds">

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
                        <tr class="wfj11_015_choice wechat">
                            <td align="left"><img class="all_pay"
                                                  src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png"/>
                            </td>
                            <td class="second" align="right"><img
                                    src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                    width="24" height="24" name="3"/>
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
    </article>
</section>

<!--弹框-->
<div class="alert-ds_" style="display: none;">
    <div class="alert-ds">
        <h4>温馨提示</h4>
        <p id="alertText"></p>
    </div>
</div>

<script type="application/javascript" src="<%=basePath%>/js/WFJClient/rewardScript.js"></script>
<div id="occlusion2" class="jqmWindow"></div>
</body>
</html>
