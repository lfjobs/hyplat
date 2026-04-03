<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";


%>

<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>${ccom.companyName }</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/setHtmlFont.js"></script>
    <%--<link type="text/css" rel="stylesheet" href="<%=basePath %>css/ea/production/swiper-3.3.1.min.css">--%>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript"
    <%--src="<%=basePath %>js/ea/production/cprocedure/scmanage/swiper-3.3.1.min.js"></script>--%>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page1.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/qrcode2.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/utf.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/companyHome2.js"></script>

    <link rel="stylesheet" href="<%=basePath%>css/swiper.min.css">
    <link rel="stylesheet" href="<%=basePath %>css/BuildPlatform/AL.css">
    <script src="<%=basePath%>js/swiper.min.js"></script>
</head>
<body>

<header class="tou" id="header">
    <ul>
        <li style="width: 10%;">
            <a>
                <img src="<%=basePath %>images/BuildPlatform/left_jt.png" id="ret">
            </a>
        </li>
        <li style="width: 80%;white-space: nowrap;">${ccom.companyName }</li>
        <li style="width: 10%; display: none;">
            <img src="<%=basePath %>images/BuildPlatform/ico-search.png" alt="" id="search">
        </li>
        <div class="clearfix"></div>
    </ul>
</header>
<!--公司认证状态弹窗-->
<div class="popup_rz" style="display:none;">
    <div class="rz_state">
        <div class="rz_con">
            <a href="javascript:void(0)" class="rz_close"></a>
            <div class="bg_top"></div>
            <img src="<%=basePath %>images/BuildPlatform/rz_img.png" class="rz_img" alt="">
            <div class="rz_infotop"></div>
            <div class="rz_info">为了给您提供更好的服务请进入5L5CERP->人事办->组织系统->企业认证处认证</div>
            <%--<a href="javascript:void(0)" class="rz_btn">立即认证</a>--%>
        </div>
    </div>
</div>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="friend frd">
                <img src="<%=basePath %>${ccom.logoPath!=null?ccom.logoPath:"images/WFJClient/PersonalJoining/logo@2x.png" }" alt="" id="logo">
                <div class="txt">
                    <h4>${ccom.companyName }</h4>
                    <p>${ccom.industryType }</p>
                </div>
            </div>
            <div class="swiper-container" id="swiper-ord">
                <div class="swiper-wrapper">
                    <c:forEach items="${list}" var="li" varStatus="l">
                        <c:choose>
                            <c:when test="${li[6] == '会员分享' }">
                                <a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03"
                                   class="swiper-slide banner_img" style="background:url('<%=basePath%>${li[2]}');position: relative;height: 9rem;background-size: cover;display: block;background-position: center;">
                                    <span class="banner_tit">${li[0] }</span>
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=basePath%>ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=${li[8] }&search=${search }&goodsid=${li[3] }"
                                   class="swiper-slide banner_img" style="background:url('<%=basePath%>${li[2]}');position: relative;height: 9rem;background-size: cover;display: block;background-position: center;">
                                    <span class="banner_tit">${li[0] }</span>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
                <div class="swiper-pagination"></div>
            </div>
            <ul class="ico_grd">
                <li>
                    <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${ccompanyId}&type=web&miniSystemJudge=00&ppid=${ppid}">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav1.png" alt="">
                        <p>${ccom.industryType=='协会社团/社会团体'?'协会介绍':'公司简介'}</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:shopCart();">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav2.png" alt="">
                        <p>联营商城</p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath %>ea/consignee/ea_toVipCenter.jspa?backu=ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav3.png" alt="">
                        <p>会员中心</p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath %>ea/wfjshop/ea_getpk.jspa?ccompanyId=${ccompanyId}">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav4.png" alt="">
                        <p>联营招商</p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${ccompanyId}&type=web&miniSystemJudge=02">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav5.png" alt="">
                        <p>${ccom.industryType=='协会社团/社会团体'?'协会新闻':'公司新闻'}</p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${ccompanyId}&type=web&miniSystemJudge=01">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav6.png" alt="">
                        <p>${ccom.industryType=='协会社团/社会团体'?'协会文化':'公司文化'}</p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath %>ea/bidrecruit/ea_showCompanydetail.jspa?ccompanyID=${ccompanyId}&companyId=${companyId }&search=${search }&back=3">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-nav7.png" alt="">
                        <p>${ccom.industryType=="协会社团/社会团体"?"协会招聘":"公司招聘"}</p>
                    </a>
                </li>
                <li>
                    <a href="javascript:forum()">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-lt.png" alt="">
                        <p>${ccom.industryType=="协会社团/社会团体"?"协会论坛":"公司论坛"}</p>
                    </a>
                </li>
                <li id = "addRes">
                    <a href="javascript:resource()">
                        <img src="<%=basePath %>images/WFJClient/PersonalJoining/companyHomepage/ico-jia2.png" alt="">
                        <p>加资源</p>
                    </a>
                </li>
            </ul>

            <!-- js拼接 -->

            <div id="consultingRegistered" style="display:none;">
                <hr class="activity-hr">
                <!--我要咨询报名-->
                <div class="activity-mil activity-news activity-ambitus">
                    <div class="title">
                        <img src="<%=basePath %>/st/images/ico-ambitus.png">
                        <span>我要咨询报名</span>
                    </div>
                    <div class="startup">
                        <p>我要咨询此项目（请留下联系方式，商家会主动联系您）</p>
                        <form class="ipt_con">
                            <div class="ipt">
                                <h3><span>＊</span>姓名：</h3>
                                <input type="text" placeholder="" class="consultantName">
                            </div>
                            <div class="ipt">
                                <h3><span>＊</span>手机：</h3>
                                <input type="text" placeholder="" class="consultantPhone">
                            </div>
                            <input type="button" value="提交" class="submit" onclick="consultantSubmit(this)">
                        </form>
                    </div>
                </div>
                <hr class="activity-hr">
                <!--注册下载-->
                <div class="activity-mil activity-car">
                    <div class="title">
                        <img src="<%=basePath %>/st/images/ico-car10.png">
                        <span>注册下载</span>
                    </div>
                    <div class="startup download">
                        <form class="ipt_con" id="myform">
                            <div class="ipt">
                                <input type="text" placeholder="请填写姓名" id="name" name="staff.staffName">
                            </div>
                            <div class="ipt">
                                <input type="text" placeholder="请填写手机号" id="tel" name="phones">
                            </div>
                            <div class="verification">
                                <div class="ipt">
                                    <input type="text" placeholder="请输入验证码" id="valnum">
                                </div>
                                <div class="gain">
                                    <input type="button" value="获取验证码" onclick="sendCode(this);return false;"
                                           id="ver_btn">
                                </div>
                            </div>
                            <div class="ipt">
                                <input type="text" placeholder="请输入密码" id="password" name="intf">
                            </div>
                            <div class="ipt">
                                <input type="text" placeholder="请再次输入密码" id="confirmPassword">
                                <input type="hidden" value="${sccid}" name="sccid">
                            </div>
                            <input type="button" value="注册微分金" class="submit" id="submit_btn">
                        </form>
                    </div>
                </div>
            </div>


            <!--二维码-->
            <ul class="erweima" align="center">
                <li>
                    <img src="<%=basePath %>${ccom.pmCodePath}" alt="" onerror="conceal(this)">
                    <p>公司公众号</p>
                </li>
                <li>
                    <%--<img src="<%=basePath %>${ccom.qrCodePath}" alt="" onerror="conceal(this)">--%>
                    <div id="qrcodeCanvas"></div>
                    <p>公司二维码</p>
                </li>
            </ul>
            <div class="footer">
                <p>2013-2016 <a href="https://beian.miit.gov.cn/" target="_blank">京ICP备11016224号-2</a></p>
                <p>技术支持：企业官网</p>

            </div>
            <a href="" id="return"><img src="<%=basePath %>page/newMyapp/images/return.png" alt=""></a>
        </div>
    </div>
</div>
<!--<div class="alert"></div>-->
<div class="alert_search">
    <div class="top">
        <input type="search" name="" placeholder="搜索" onfocus="this.placeholder=''" onblur="this.placeholder='搜索'"
               value="" class="sousuo">
        <input type="submit" value="搜索" id="ss">
        <input type="submit" value="取消" id="qx">
    </div>
</div>
<!--签到成功弹框-->
<%--<div class="alert_succeed_"></div>
<div class="alert_succeed" id="ssss" >
    <img src="<%=basePath%>images/ea/finance/BonusPoints/x.png" alt="" class="x">
    <p>恭喜~签到成功</p>
    <h5 id="jifenNum">已赠送100积分</h5>
</div>--%>

<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var companyId = '${companyId}';
    var ccompanyId = '${ccompanyId}';
    var companyname = '${search}';
    var pageNumber = '${pageNumber}';
    var pageSize = '${pageSize}';
    var itye = '${contactCompany.industryType}';
    var authState = '${ccom.authState}';
    var rikey = '${recruitInfo.rikey}';
    var only = 0;
    var surl1;
    var search = "";
    var flag = '${flag}';
    var isflag = '${param.isflag}'; //判断是否是从共建平台跳转过来的,值为gjpt
    var industryType = "${param.industryType}";
    var etype = "${param.etype}";
    var pos = "${param.pos}";

    var tcc = ${tcc};//用于判断公司是否关联个人账号
    $(document).ready(function () {
        $(".carousel-inner").find("div").eq(0).addClass("active");
        $("header").attr("style", "height:" + $(window).height() * 0.08 + "px;line-height:" + $(window).height() * 0.08 + "px;position:fixed;");
        $(".content_hidden").attr("style", ";overflow: hidden;position: relative;" + "top:" + $(window).height() * 0.08 + "px");
        $(".content").attr("style", ";overflow: auto;height:" + $(window).height() * 0.92 + "px;");
        if (flag == 'vip') {
            $("#ret").attr("href", basePath + "ea/consignee/ea_toVipCenter.jspa");
        }
        if (authState != '02') {//未认证的商家，隐藏“+”按钮，并提示认证（00:未认证,01:认证审核中,02:已认证,03:认证失败）
            $("#addRes").hide();
            $(".popup_rz").show();
        }
        //查看该公司是否有活动
        getPrizeActivity();

        //查看该公司是否有抽奖
        $.ajax({
            url: basePath + "ea/lottery/sajax_ea_ajaxIsDraw.jspa?model.companyId=" + companyId,
            type: "get",
            async: false,
            success: function (data) {
                var str = new Array();
                var member = eval("(" + data + ")");
                if (member.model != null) {
                    str.push('<li>');
                    str.push('<a href="' + basePath + 'ea/lottery/ea_goLottery.jspa?model.companyId='
                        + companyId + '&model.activityId=' + member.model.activityId + '&ccompanyId=' + ccompanyId + '">');
                    str.push('<img src="' + basePath + 'images/WFJClient/PersonalJoining/companyHomepage/lottery.png" alt="">');
                    str.push('<p>抽奖</p>');
                    str.push('</a></li>');
                    $(".ico_grd li:last").before(str.join(""));
                }
            }
        });
        $.ajax({
            url: basePath + "/ea/android/sajax_ea_addPersonKuaiJie.jspa?companyid=" + companyId,
            type: "post",
            dataType: "json",
            success: function (data) {
                console.log(data);
            }
        });
    });

    function getPrizeActivity() {
        var url = basePath + "/ea/bonuspoints/sajax_getPrizeActivity.jspa?ccompanyId=" + ccompanyId;
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (data) {
                var str = new Array();
                //标识（判断是否有签到活动）
                var mark = data.flag;
                if (mark == "sign") {
                    str.push('<li>');
                    str.push('<a href="' + basePath + 'ea/bonuspoints/ea_getSign.jspa?ccompanyId=' + ccompanyId + '">');
                    str.push('<img src="' + basePath + 'images/WFJClient/PersonalJoining/companyHomepage/bp.png" alt="">');
                    str.push('<p>签到</p>');
                    str.push('</a></li>');
                    $(".ico_grd li:last").before(str.join(""));
                }

            }
        });
    }
    //关闭认证
    $(".rz_close").click(function(){
        $(".popup_rz").hide();
    })
</script>


<script>
    var typelei = "${sessionScope.playto}";
    var sccid = "${sessionScope.sccid}";
    var typeback = "${sessionScope.typeback}"
    $(document).ready(function () {
        $("header").css("height", $(window).height() * 0.08 - 1 + "px");
        $("header").css("line-height", $(window).height() * 0.08 - 1 + "px");
        $(".content_hidden").attr("style", ";overflow: hidden;" + "height:" + $(window).height() * 0.92 + "px");
        $(".content").attr("style", "overflow: hidden;" + "height:" + $(window).height() * 0.92 + "px");
        $(".head_top").css("height", $(window).height() * 0.08 - 1 + "px");
        $(".head_top ul li").css("line-height", $(window).height() * 0.05 + "px");
        $(".head_top ul li:nth-child(1) dl").css("margin", $(window).height() * 0.015 + "px");
        $(".head_top ul li:nth-child(2) input").attr("style", "margin:" + $(window).height() * 0.015 + "px;margin-left:0;line-height:" + $(window).height() * 0.05 + "px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height", $(window).height() * 0.92 + "px");
        $(".ind-grd li").attr("style", "height:" + $(".ind-grd li").width() + "px");
        $(".so_shop ul li img").css("height", $(".so_shop ul li img").width() + "px");

        $("body").css("margin-top", $(window).height() * 0.08 + "px");


        /*搜索*/
        $("header ul li:nth-child(3) img").click(function () {
            $(".alert").show();
            $(".alert_search").show();
        });
    });
</script>
<script>
    jQuery.fn.limit = function () {
        var self = $("[limit]");
        self.each(function () {
            var objString = $(this).text();
            var objLength = $(this).text().length;
            var num = $(this).attr("limit");
            if (objLength > num) {
                $(this).attr("title", objString);
                objString = $(this).text(objString.substring(0, num) + "...");
            }
        })
    }
    $(function () {
        $("[limit]").limit();
        if (companyId == null) {
            $(".col-xs-4").find("a").removeAttr("href");
        }
    })
    $("#return").click(function () {
        $(".content").scrollTop(0)
    })

    function conceal(obj) {
        $(obj).parent().remove();
    }


</script>

<script type="text/javascript">
    var clock = '';
    var nums = 60;
    var btn = document.getElementById("ver_btn");
    var tel = document.getElementById("tel");
    var _name = document.getElementById("name");
    var valnum = document.getElementById("valnum");
    var submit_btn = document.getElementById("submit_btn");
    var confirmPassword = document.getElementById("confirmPassword");
    var password = document.getElementById("password");

    function sendCode(thisBtn) {
        if (_name.value == "") {
            alert("姓名不能为空");
            return false;
        }
        if (tel.value == "") {
            alert("手机号不能为空");
            return false;
        }
        if (!ver_phone()) {
            return false;
        }
        //发送短信
        $.ajax({
            cache: true,
            type: "POST",
            url: basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + tel.value,
            async: false,
            dataType: "json",
            success: function (data) {
                var member = data;
                i = member.returna;
            }
        });
        btn = thisBtn;
        btn.disabled = true; //将按钮置为不可点击
        //btn.innerHTML = nums + '秒重新获取';
        btn.value = nums + '秒重新获取';
        clock = setInterval(doLoop, 1000); //一秒执行一次
        btn.className = "send_btn disable_btn";

    }

    //手机验证格式
    tel.addEventListener("input", function () {
        var Sreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if (!Sreg.test(tel.value)) {
            //console.log("格式不正确，不提交");
            if (tel.value.length == 11) {
                alert("请输入正确格式");
                tel.value = "";
                tel.focus();
            }
        } else {
            isRegister();
        }
    });

    //验证码失去焦点验证
    valnum.addEventListener("blur", verCode);

    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function () {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
        }
    };
    //点击注册
    submit_btn.addEventListener("click", function () {
        if (_name.value == "") {
            alert("姓名不能为空");
            return false;
            name.focus();
        }
        if (tel.value == "") {
            alert("手机号不能为空");
            return false;
            tel.focus();
        }
        if (!ver_phone()) {
            return false;
            tel.focus();
        }
        if (!verCode()) {
            return false;
            valnum.focus();
        }
        if (!ver_password()) {
            return false;

        }
        $("#myform").attr("action", basePath + "/ea/wfjlogin/ea_seves.jspa");
        $("#myform").submit();
    })

    //手机号验证
    function ver_phone() {
        var Sreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if (tel.value == "") {
            alert("手机号不能为空");
            return false;
        } else if (!Sreg.test(tel.value)) {
            alert("请输入正确格式电话号！");
            return false;
        } else {
            return true;
        }
    }
    ;

    // 判断手机号是否注册
    function isRegister() {
        if (tel.value != '' && ver_phone()) {
            $.ajax({
                cache: true,
                type: "POST",
                url: basePath + "/ea/android/sajax_ea_isacounnt.jspa?pahe=" + tel.value,
                async: false,
                dataType: "json",
                success: function (data) {
                    var member = eval("(" + data + ")");
                    if (member.result == 0) {
                        d = 1;
                        console.log("未注册，可以使用");
                    } else {
                        alert("已被注册,请更换手机号码！");
                        tel.value = "";
                        tel.focus();
                        d = 2;
                        return;
                    }
                }
            });
        } else {
            tel.value = "";
        }
    }

    function doLoop() {
        nums--;
        if (nums > 0) {
            btn.value = nums + '秒重新获取';
        } else {
            clearInterval(clock); //清除js定时器
            btn.disabled = false;
            btn.value = '获取验证码';
            nums = 60; //重置时间
            btn.className = "send_btn";
        }
    }

    //验证码验证
    function verCode() {
        if (valnum.value == "") {
            alert("请填写验证码");
            return false;
        } else if (valnum.value != i) {
            alert("验证码不正确");
            return false;
        } else {
            return true;
        }
    }

    //密码
    function ver_password() {

        var flag = false;
        var message = "";

        if (password.value == '') {
            alert("请输入密码！");
            return false;
        } else if (password.value.length < 6) {
            alert("密码长度不安全");
            return false;
        } else if (confirmPassword.value == '') {
            alert("请输入确认密码");
        } else if (confirmPassword.value != password.value) {
            alert("二次密码输入不一致，请重新输入！");
            confirmPassword.value = "";
            confirmPassword.focus();
            return false;
        } else {
            return true;
        }
    }


</script>
<script>
    /*banner轮播*/
    var mySwiper1 = new Swiper('#swiper-ord', {
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: false,
        },
        loop: true,
        pagination: {
            el: '#swiper-ord .swiper-pagination',
            type: 'bullets',
            bulletElement: 'li',
            clickable: false,

        },


    });
</script>

</body>
</html>
