<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>卖家-订单详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/makeApp/BookingDetails.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/makeApp/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/wfjeshop/bookingDetails.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-zf.css">



    <script type="text/javascript">
        var basePath="<%=basePath%>";

        var  ddid  = "${object[7]}";
        var  zffs = "1";
        var goodsname = "${object[2]}";
        var companyName = "${object[21]}";
        goodsname = goodsname+"("+companyName+")";
        var companyID = "${object[22]}";
        var jifen = "${jifen}";
        var jinbi = "${jinbi}";
        var user = "${user}";
        var staffID = "";
        var dp = "${param.dp}";
        var posNum = "";
        var paysuc = "${param.paysuc}";
        var state = "${object[11]}";
        var sm = "${param.sm}";
        var search="${param.search}";
        var  sccId = "${param.sccId}";//学员的
        var jgzsccid='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
         var dz = "${param.dz}";
    </script>


</head>
<body>
<%--提示框--%>
<div class="div-ts">
    支付中
</div>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>/images/WFJClient/Login/left.png"></a></li>
        <li style="width: 80%;text-align: center;">预约练车</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content rec_content">
        <%--<div class="company">--%>
            <%--<div class="left img">--%>
                <%--<img src="<%=basePath%>images/ea/finance/NewPhoneOrders/sellerOrder/ico-com.png" alt="">--%>
            <%--</div>--%>
            <%--<div class="txt">--%>
                <%--<span>订单详情</span><a><img src="<%=basePath%>images/ea/finance/NewPhoneOrders/right2.png" alt=""></a>--%>
            <%--</div>--%>
            <%--<p>预约完成</p>--%>
        <%--</div>--%>
        <div class="shop_mil sel_shop_mil">
            <div class="left">
                <img src="<%=basePath%>${object[1]}" alt="">
            </div>
            <div class="txt">
                <h3>${object[2]}</h3>
                <h4>场地：<span>${object[4]}</span></h4>
                <h4>主管：<span>${object[5]}</span></h4>
                <h4>教练：<span>${object[6]}</span></h4>
                <h4>学员：<span>${object[20]}</span></h4>
            </div>
            <div class="txt2">
                <h3>&yen;<span>${object[3]}</span></h3>
                <h4>x<span>1</span></h4>
            </div>
        </div>
        <div class="time_code">
            <h5>计时二维码<p><span>${object[7]}</span></p></h5>
            <img src="<%=basePath%>${object[8]}">
            <div class="time">
                <ul class="left_">
                    <li><p style="color: #57bd66;">起：</p><p>${object[9]}</p></li>
                    <li><p style="color: #ff6e31;">止：</p><p>${object[10]}</p></li>
                </ul>
                <p class="right_" >
                    <span id="state" style="display: none;" >${object[11]}</span>
                    <c:choose>
                        <c:when test="${object[11] eq '00' }">
                            未使用
                        </c:when>
                        <c:when test="${object[11] eq '01' || object[11] eq '03'}">
                            已签到
                        </c:when>
                        <c:when test="${object[11] eq '02'}">
                            已签退
                        </c:when>
                        <c:when test="${object[11] eq '04'}">
                            等待客户直接支付
                        </c:when>
                </c:choose>
                </p>
            </div>
        </div>
        <c:if test="${object[13] ne null&&object[13] ne ''}">
        <div class="mon sel_mon">
            <div class="txt">
                <h4>买家实付<p>合计:&yen;<span>${object[13]}</span></p><p class="all">共:${object[12]}</p></h4>
            </div>
        </div>
        </c:if>
        <div class="code">
            <div class="code_">
                <h4>
                    <span>订单编号：</span>
                    <!--<textarea cols="1" id="bianhao">2016080911390900011</textarea>-->
                    <input id="bianhao" value="${object[14]}" readonly="readonly" >
                </h4>
                <input type="button" value="复制" onclick="IOSCopy(this)">
            </div>
            <h4>下单时间：<span>${object[15]}</span></h4>
            <h4>付款时间：<span>${object[16]}</span></h4>
            <h4>备　　注：<span>无</span><input type="text" class="barcode" onfocus="onFocus()" onblur="onBlur()" style="opacity:0;"/>
            </h4>
            <div class="up_btn">
                <ul>
                    <li  id="up">
                        <img src="<%=basePath%>images/ea/finance/NewPhoneOrders/sellerOrder/up.png" alt=""><p>点击隐藏</p>
                    </li>
                    <li id="down">
                        <img src="<%=basePath%>images/ea/finance/NewPhoneOrders/sellerOrder/down.png" alt=""><p>点击显示<p>
                    </li>
                </ul>
            </div>

        </div>

    </div>
    <!--选择支付方式-->
    <div class="wfj11_015" style="display:none;">
        <div class="wfj11_015_buy_commit">
            <div class="wfj11_015_need">
                <div class="wfj11_015_width">
                    <ul>
                        <li class="left" id="htime"></li>
                        <li class="right"><span>需支付￥</span><span class="xzf">0</span></span>

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

                        <%--金币--%>
                        <tr class="wfj11_015_choice gold">
                            <td align="left"><img class="all_pay"
                                                  src="<%=basePath%>/images/WFJClient/Newjspim/gold.png"/>
                                <span style="font-size: 12px"></span>
                            </td>
                            <td class="second" align="right">金币数：<fmt:parseNumber integerOnly="true" value="${jinbi}" /><img
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
                            <td class="second" align="right">积分数：<fmt:parseNumber integerOnly="true" value="${jifen}" /> <img
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
    <div class="btn" style="display: none;">
        <button onclick="topay('${object[13]}')">立即支付${object[13]}</button>
    </div>
</div>
<div class="alert_dh">
    <div class="zfz">

        <img src="<%=basePath%>images/supermarket/zfz.png" alt="">
        <p>正在支付中...</p>
    </div>
</div>
<%@ include file="/page/WFJClient/ShopOwner/payCodeCommon.jsp"%>
<script>
    $(document).ready(function(){
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".header ul li").css("height",$(window).height()*0.08+"px");
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92-40+"px");
        $(".so_shop ul li img").css("height",$(".so_shop ul li img").width()+"px");


        $(".up_btn #up").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #down").click(function(){
            $(this).hide().siblings().show();
            $(".mon .txt h5").show();
            $(".code h4").show();
            $(".code .code_").show();
        });
        //切换支付方式
        $(".wfj11_015_choice").click(function(){
            if($(this).find("span").text()!="(您金币为0,无法选择)"&& $(this).find("span").text()!="(您的积分不足，无法选择)"&& $(this).find("span").text()!="(您的金币不足，无法选择)"&& $(this).find("span").text()!="(您积分为0,无法选择)"&& $(this).find("span").text()!="(您的金币已冻结，无法选择)") {
                $(".wfj11_015_choice").find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_02.png");
                $(this).find(".second").find("img").attr("src", basePath + "/images/WFJClient/Newjspim/choice_01.png");
                zffs = $(this).find(".second").find("img").attr("name");
            }
        });
        /*复制订单号*/
        /*$(".code_ input").click(function(){
         var cp_txt=$(".code_ h4 span").text();
         alert(cp_txt);
         });*/
    })
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }


    //复制
    function IOSCopy(obj){
        var content=$(obj).prev('h4').find('#bianhao').val();
        var url= "func=" + 'iosstick';
        params={'content':content};
        for(var i in params){
            url = url + "&" + i + "=" + params[i];
        }
        alert("复制成功");
        window.webkit.messageHandlers.Native.postMessage(url);
    }

    //联系商家
    function ioschat(obj){
        var url= "func=" + 'ioschat';
        params={'sccid':$(obj).attr("data-sccid"),
            'account':$(obj).attr("data-account"),
            'username':$(obj).attr("data-staffname")};
        for(var i in params){
            url = url + "&" + i + "=" + params[i];
        }
        window.webkit.messageHandlers.Native.postMessage(url);

    }
</script>
</body>
</html>

