<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <title>
        ${object1[0] }
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>

    <script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/base.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css?version=20250317>">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/qr_share.css?version=20250317">
    <script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/platform/platformNew.js"></script>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>
    <script  type="text/javascript" src="<%=basePath%>js/jquery.qrcode.js"></script>
    <script  type="text/javascript" src="<%=basePath%>js/utf.js"></script>
    <script src="<%=path%>/js/qrcode2.js"></script>

    <style type="text/css">
        #prompt div {
            width: 70%;
            background: rgba(0, 0, 0, 1);
        }

    </style>

</head>

<body class="platbody">
<div class="mengban"></div>
<header>
    <ul class="pub_top1">
        <li style="width: 10%;">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
        </li>
        <li style="width: 80%;">
            <c:choose>
                <c:when test="${miniSystemJudge eq '00'}">
                    公司简介
                </c:when>
                <c:when test="${miniSystemJudge eq '01'}">
                    公司文化
                </c:when>
                <c:when test="${miniSystemJudge eq '02'}">
                    新闻资讯
                </c:when>
                <c:when test="${miniSystemJudge eq '03'}">
                    <c:if test="${object1[11] ne '慈善捐赠'&&object1[11] ne '关于数字地球'&&object1[11] ne '使用帮助'}">
                        资讯分享
                    </c:if>
                    <c:if test="${object1[11] eq '慈善捐赠'||object1[11] eq '关于数字地球'||object1[11] eq '使用帮助'}">
                        ${object1[11]}
                    </c:if>
                </c:when>
                <c:when test="${miniSystemJudge eq '04'}">
                    公司论坛
                </c:when>
            </c:choose>
        </li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content div-hyfx">
    <div class="news-det_tit">
        <h1>${object1[0]}</h1>
        <h5 style="position: relative;">${fn:substring(object1[1], 0, 10)}<span>${object1[8]}</span>&nbsp;|&nbsp;<span>${object1[11]}</span><i class="music_play paused"></i></h5>
        <input class="sccid" type="hidden" value="${object1[6]}"/>
        <p>阅读量：${readcount}</p>
    </div>
    <hr style="border-top: 1px solid #ddd;margin: 0;">
    <div class="article_con">
        <s:iterator value="maplist1" var="ml">
            ${ml.value[0]}
            <br/>
        </s:iterator>
        <c:if test="${param.m ne 'v'}">

            <div class="article_QR">
               <%-- <img src="<%=basePath%>${object1[5]}" alt=""><br>--%>
            <div class="qr_img"  id="qrcode" style=" margin-bottom: 0.5rem;">

            </div>
                <span>【扫二维码,加入数字地球】</span>
            </div>
            <p class="article_QR">
                <img src="<%=basePath%>${concom.pmCodePath}" alt="" class="wxh-img">
                <span>【扫二维码,关注数字地球公众号】</span>
            </p>
        </c:if>
    </div>
    <form id="form1" name="form1">
        <div class="zx">
            <h2>
                <img src="<%=basePath%>images/BuildPlatform/zx.png"/>
                我要咨询
            </h2>
            <p>
                我要咨询此项目（请留下联系方式，商家会主动联系您）
            </p>
            <section>
                <p>
                    <label for=""><span>*</span>姓名：</label>
                    <input type="text"  placeholder="请输入姓名" id="name"  name="consult.consultantName" />
                    <input type="text"  style="display:none;"  name="consult.ppid" value="${ppId}"/>
                    <input type="text"  style="display:none;"  name="consult.staffId" value="${object1[10]}"/>
                    <input type="text"  style="display:none;"  name="consult.ccompanyId" value="${ccompanyId}"/>
                    <input type="text"  style="display:none;"  name="consult.goodsname" value="${object1[0]}"/>

                    <input type="text"  style="display:none;"  name="consult.consultantContent" id="tcontent"/>


                </p>
                <p>
                    <label for=""><span>*</span>手机：</label>
                    <input type="number" maxlength="11" placeholder="请输入手机号" id="tel" name="consult.consultantPhone" />
                </p>
                <p>
                    <label for=""><span>*</span>验证码：</label>
                    <input type="number" name="" placeholder="请输入验证码" id="valnum" />
                    <input type="button" name=""  id="ver_btn" value="获取验证码"  onclick="sendCode(this);return false;" />
                </p>

                <c:if test="${object1[11] eq '慈善捐赠'}">
                    <input type="radio" name="tc" value="慈善" id="cs" checked style="display:none;"/>

                </c:if>
                <c:if test="${object1[11] ne '慈善捐赠'}">
                    <div class="consultContent">
                        <label  for=""><span>*</span>可发布咨询项目：</label>
                    <ul>

                    <li>
                        <input type="radio" name="tc" value="报名" id="bm"/>&nbsp;<label for="bm">报名</label>
                       </li>
                    <li>
                        <input type="radio" name="tc" value="生日" id="sr"/>&nbsp;<label for="sr">生日</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" value="婚宴" id="hy"/>&nbsp;<label for="hy">婚宴</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" value="广告" id="gg"/>&nbsp;<label for="gg">广告</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" value="培训" id="px"/>&nbsp;<label for="px">培训</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" value="活动" id="hd"/>&nbsp;<label for="px">活动</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" value="文章" id="wz"/>&nbsp;<label for="wz">文章</label>
                    </li>
                    <li>
                        <input type="radio" name="tc" id="other" value="其他"/>&nbsp;<label for="other">其他</label>
                    </li>
                </ul>
                    </div>
                </c:if>
            </section>
            <section class="tab-submit">

                <input type="button" name="" id="submit_btn" value="提交" />
                <div class="ybm" style="display:none;">
                    <h1 class="ybm">已咨询报名</h1>
                    <div class="tab-div">
                    </div>
                    等<span class="zxcount">${zxcount}</span>人
                </div>
                <p class="wbm">
                    已咨询报名<span class="zxcount">${zxcount}</span>人
                </p>
            </section>
        </div>
    </form>
    <!--打赏-->
    <div class="ipt-ds">
        <a href="<%=basePath%>ea/industry/ea_jumpReward.jspa?sccid=${object1[6]}&ppid=${ppId}&rnum=${object1[9]}&sname=${object1[8]}&cate=${object1[11]}" class="ipt-ds"><input type="button" value="${object1[11] eq '慈善捐赠'?'捐赠':'打赏'}" id="ipt-ds"></a>
        <c:if test="${object1[9]>0}">
            <div class="ds-div"></div>
            <span class="zxcount">等${object1[9]}人进行了${object1[11] eq '慈善捐赠'?'捐赠':'打赏'}</span>
        </c:if>
    </div>
    <div class="ipt-tip">
        点击注册并登陆数字地球网址<a href="<%=basePath%>page/WFJClient/pc/pc_login.jsp">http://www.impf2010.com/page/WFJClient/pc/pc_login.jsp</a>→我的→+资讯→编辑自己的内容
    </div>

    <!--打赏 end-->
    <ul class="related-rec">
        <h5>相关推荐</h5>
        <c:forEach items="${list1 }" var="li" varStatus="l">
            <c:if test="${l.index < 4}">
                <c:choose>
                    <c:when test="${type=='web'}">
                        <li><a href="ea/industry/ea_informationDetails.jspa?ppId=${li[2] }&ccompanyId=${ccompanyId}&type=${type}&miniSystemJudge=${miniSystemJudge}"><span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/dian.png" alt=""></span>${li[0]}</a></li>
                    </c:when>
                    <c:when test="${type=='time'}">
                        <li><a href="ea/industry/ea_informationDetails.jspa?ppId=${li[2] }&ccompanyId=${ccompanyId}&type=${type}&miniSystemJudge=${miniSystemJudge}"><span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/dian.png" alt=""></span>${li[0]}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="ea_newdetail.jspa?goodsid=${li[1]}&ppid=${ppid }"><span><img src="<%=basePath%>/images/WFJClient/PersonalJoining/dian.png" alt=""></span>${li[0]}</a></li>
                    </c:otherwise>
                </c:choose>
                <hr style="width: 94%;margin: 10px auto 15px auto;border-top: 1px solid #ddd;">
            </c:if>
        </c:forEach>
    </ul>
<%--    <div class="news-det_comment" id="com">--%>
<%--        <h5>评论</h5>--%>
<%--        <hr style="width:2rem;border-top: 2px solid #F59393;margin: 10px;margin-bottom: -2px;z-index: 1;position: relative;">--%>
<%--        <hr style="border-top: 2px solid #ddd;margin: 0;">--%>
<%--        <ul class="comment_mil">--%>
<%--        </ul>--%>
<%--        <!-- <h4 align="center">以显示全部</h4> -->--%>
<%--    </div>--%>
</div>
<div class="input-box">
    <header>
        <ul class="">
            <li style="width: 10%;display: none;" >
                <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
            </li>
            <li style="width: 100%;">评论详情</li>
            <li style="width: 10%;display: none;"></li>
            <div class="clearfix"></div>
        </ul>
    </header>
    <div style="position:relative;">
        <button onclick="myFunction2()" style="left: 1rem;color: #fff;background: #FF6507;">取消</button><button onclick="myFunction()">发表</button>
    </div>
    <textarea rows="23" placeholder="优质评论将会被优先显示" name="content"></textarea>
</div>
<footer class="footer-hyfx">
    <div class="news-det_ipy">
        <input type="text" placeholder="写评论">
    </div>
    <div class="news-det_lat">
        <div class="comment_count"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-pinglun.png">
            <span></span>
        </div>
        <div>
            <c:if test="${flag=='0' }">
                <img class="shouc" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-shoucang.png">
                <img class="shouc2" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-shoucang2.png">
            </c:if>
            <c:if test="${flag=='1' }">
                <img class="shouc" style="display: none" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-shoucang.png">
                <img class="shouc2" style="display: block" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-shoucang2.png">
            </c:if>
        </div>
    </div>
<%--    <div class="QR_float" style="display: none;">--%>
<%--        <div class="o_white"></div>--%>
<%--        <div class="float_con">--%>
<%--            <span>你也能发这样的图文、视频</span>--%>
<%--            <a href="<%=basePath%>/ea/wfjshop/ea_getjspzc.jspa?sccid=${object1[6] }">制作发布项目</a>--%>
<%--            <i></i>--%>
<%--        </div>--%>
<%--    </div>--%>
</footer>

<!--***************打赏、捐赠列表****************-->
<div class="addCategorie">
    <header>
        <ul>
            <li id="back" style="width: 10%;">
                <img src="http://www.impf2010.com:80/images/WFJClient/PersonalJoining/back_03.png">
            </li>
            <li style="width: 70%;text-indent: 10%;">${object1[11] eq '慈善捐赠'?'捐赠':'打赏'}列表</li>
        </ul>
    </header>
    <div class="content_hidden">
        <section class="sec-list" id="pc-sec">
            <ul class="product">
                <li>
                    <div class="txt">
                        <div class="staff-div" style="width: 25%">${object1[11] eq '慈善捐赠'?'捐赠':'打赏'}人员</div>
                        <div class="jkname" style="width: 25%">${object1[11] eq '慈善捐赠'?'捐赠':'打赏'}名称</div>
                        <div class="jznum" style="width: 15%">数量</div>
                        <div class="money-div" style="width: 35%;border: 0;">金额</div>
                    </div>
                </li>
            </ul>
        </section>
    </div>
</div>
<script>
    var basePath="<%=basePath%>";
    var ppid = "${ppid }";
    var goodsid = "${object1[7] }";
    var pagenumber = 0;
    var height = 0;
    var t;
    var pagecount;
    var ppId = "${ppId}";//公司下的新闻Id
    var ccompanyId='${ccompanyId}';//往来单位id
    var type = '${type}';
    var miniSystemJudge = '${miniSystemJudge}';
    var typeNews='${typeNews}';
    var register = '${register}';
    var back = '${back}';

    $(function(){
        var imgsrc = $(".article_img").eq(0).find("img").attr("src");

        var url = basePath
            + "ea/qrshare/sajax_ea_getJssdkConfig.jspa";

        //var retUrl = encodeURIComponent(location.href.split('#')[0]);
        var retUrl = location.href.split('#')[0];
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data:{
                retUrl:retUrl
            },
            success : function(data) {
                var m = eval("("+data+")");

                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: m.appId, // 必填，公众号的唯一标识
                    timestamp:m.timestamp , // 必填，生成签名的时间戳
                    nonceStr: m.nonceStr, // 必填，生成签名的随机串
                    signature: m.signature,// 必填，签名
                    jsApiList: ["updateAppMessageShareData","updateTimelineShareData"] // 必填，需要使用的JS接口列表
                });

                wx.error(function (res) {
                    console.log(res);
                });


                wx.ready(function () {   //需在用户可能点击分享按钮前就先调用
                    wx.updateAppMessageShareData({
                        title: '${object1[0]}', // 分享标题
                        desc: '', // 分享描述
                        link: retUrl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        imgUrl: imgsrc, // 分享图标
                        success: function (res) {

                        },
                        fail:function(res){


                        }
                    })

                    wx.updateTimelineShareData({
                        title: '${object1[0]}', // 分享标题
                        link: retUrl, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                        imgUrl: imgsrc, // 分享图标
                        success: function () {

                        }
                    })

                });
            }
        });
    });
    $(".pub_top1").find("li").eq(0).click(function(){
        /*if(type=='web'){
            if(miniSystemJudge=='04'){
                document.location.href=basePath+"ea/companyforum/ea_forumMessage.jspa?concom.ccompanyID="+ccompanyId;
            }else{
                document.location.href=basePath+"ea/wfjplatform/ea_platformNews.jspa?ccompanyId="+ccompanyId+"&type="+type+"&miniSystemJudge="+miniSystemJudge;
            }
        }else if(type=='time'){
            document.location.href=basePath+"ea/wfjshop/ea_getNewsList.jspa?typeNews="+typeNews;
        }else{
            document.location.href = basePath +"ea/wfjplatform/ea_platformNews.jspa?ppid="+ppid;
        }*/
        window.history.go(-1);
        return false;
    });


    //type为1 页面首次进入调用的ajax  type为2评论之后重新调用的ajax
    function loaded(types){
        clearTimeout(t);
        if(types==2){
            pagenumber=1;
        }else{
            pagenumber += 1;
        }
        //评论显示
        var url = basePath+"ea/wfjplatform/sajax_ea_AjaxComment.jspa?typeNews=0&goodsid="+goodsid+"&ppid="+((type=='web' || type=='time')?ppId:ppid);
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            data:{
                "pageForm.pageNumber":pagenumber,
                "pageForm.pageSize":15
            },
            success : function (data) {
                var member = eval("(" + data + ")");
                var list = member.list;
                pagecount=member.pagecount;
                pagenumber=member.pagenumber;
                count = member.count;
                if(count>99){
                    count="99+";
                }
                $(".comment_count").find("span").html(count);
                if (list == null) {

                } else {
                    var htl = [];
                    for ( var int = 0; int < list.length; int++) {
                        var comment = list[int];
                        if(comment[0]==""){
                            comment[0] = "images/WFJClient/PersonalJoining/headimage.png";
                        }
                        htl.push("<li><div class='com_head' style='border-bottom: none;position: static;width: 20%;'><div><img src='"+basePath+comment[0]+"'></div></div><div class='com_txt'><div class='com_com'><div class='parise' id='"+comment[6]+"'>");
                        if(comment[8]=="1"){
                            htl.push("<img class='zan' style='display:none' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan.png'><img class='zan2' id='ispraise' style='display:block' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan2.png'>");
                        }else{
                            htl.push("<img class='zan' style='display:block' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan.png'><img class='zan2' style='display:none' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan2.png'>");
                        }
                        htl.push("<p>"+comment[4]+"</p></div><div class='comment' id='"+comment[6]+"'><img src='"+basePath+"/images/WFJClient/PersonalJoining/ico-com.png'><p>"+comment[5]+"</p></div>");
                        htl.push("<div class='clearfix'></div></div><span>"+comment[1]+"</span><h5>"+comment[2].substring(0,19)+"</h5><h3>"+comment[3]+"</h3></div><div class='clearfix'></div></li><hr style='border-top: 2px solid #ddd;margin: 15px 0;'>");
                    }
                    if(pagenumber == pagecount){
                        htl.push("<div style='text-align:center;font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.04+"px;color:#373737;'>已显示全部</div>");
                    }
                    $(".comment_mil").append(htl.join(""));
                    getHeight(".comment_mil",".content","loaded(1)");
                }
            },
            error:function(data){
                alert("获取评论失败");
            }
        });

    }


</script>

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.84+"px;overflow: auto;");
        $("footer").css("marginTop",$(window).height()-$("header").height()+"px");
        $("footer").css("height",$(window).height()*0.08+"px");
        $("footer").css("line-height",$(window).height()*0.08+"px");
        $("#com").css("padding-top",$(window).height()*0.08+"px");
        $(".news-det_lat div").css("height",$(window).height()*0.08+"px");
        $(".news-det_lat div").css("line-height",$(window).height()*0.08+"px");
        $(".input-box").css("top",$(window).height()*0.08+"px");
        $(".input-box").css("height",$(window).height()*0.92+"px");
        $(".input-box textarea").css("height",$(window).height()*0.81+"px");
        if(back=='1'){
            $(".pub_top1").find("li").eq(0).hide();
            $(".pub_top1").find("li").eq(1).css("margin-left","10%");
        }
        loaded(1);
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        //判断是否显示马上制作
        if(register=='0'){

            $(".QR_float").show();//未登陆
            if (isWeixin) {
                $(".float_con a").attr("href",basePath+"ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02&tjsccid=${object1[6]}");
            }
        }else{

            if (!isWeixin) {
                $(".QR_float").hide();
            }else{
                $(".QR_float").show();
                $(".float_con a").attr("href",basePath+"ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02");
            }
        }

        $(".news-det_tit").find("span").click(function(){
            var sccid = $(this).parents(".news-det_tit").find(".sccid").val();
            window.open("ea/companyforum/ea_myMessage.jspa?community=01&ccom.sccId="+sccid);
        })


        //赞
        $(document).on("click",".parise",function (){
            if($(this).find(".zan2").attr("id")!="ispraise"){
                var pcid = $(this).attr("id");
                if($(this).find(".zan").css("display")=="block") {
                    $(this).find(".zan").css("display","none");
                    $(this).find(".zan2").css("display","block");
                    var zan=$(this).find("p").text();
                    zan=eval(zan)+1;
                    $(this).find("p").text(zan);
                }
                else {
                    return false;
                }
                //点赞
                var url = basePath+"ea/wfjplatform/sajax_ea_ajaxPrise.jspa?pcid="+pcid;
                $.ajax({
                    url : encodeURI(url),
                    type : "get",
                    data : {
                        ppid :((type=='web' || type=='time')?ppId:ppid),
                        goodsid:goodsid,
                    },
                    success : function(data) {
                        var m = eval("(" + data + ")");
                        var login;
                        if(m!=null){
                            login = m.login;
                        }
                        if (login == "login") {
                            document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

                        }
                    },
                    error : function(data) {
                        alert("失败");
                    }
                });
            }
        });
        $(document).on("click",".comment",function (){
            var pcid = $(this).attr("id");
            var url = basePath+"ea/wfjplatform/sajax_ea_platformNewComment.jspa";
            $.ajax({
                url : encodeURI(url),
                type : "get",
                data : {
                    ppid : ((type=='web' || type=='time')?ppId:ppid),
                    goodsid:goodsid,
                    ccompanyId:ccompanyId,
                    type:type,
                    miniSystemJudge:miniSystemJudge,
                },
                success : function(data) {
                    var m = eval("(" + data + ")");
                    var login;
                    if(m!=null){
                        login = m.login;
                    }
                    if (login == "login") {
                        document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

                    }
                }
            });
            var url = basePath+"ea/wfjplatform/ea_platformNewComment.jspa?ppid="+((type=='web' || type=='time')?ppId:ppid)+"&goodsid="+goodsid+"&pcid="+pcid;
            if(type=='web'){
                url+="&type="+type+"&ccompanyId="+ccompanyId+"&miniSystemJudge="+miniSystemJudge;
            }else if(type=='time'){
                url+="&type="+type+"&miniSystemJudge="+miniSystemJudge;
            }
            document.location.href = url;
        });

        //收藏
        $(document).on("click",".shouc",function (){
            var url = basePath+"ea/wfjplatform/sajax_ea_ajaxCollect.jspa?ppid="+((type=='web' || type=='time')?ppId:ppid)+"&goodsid="+goodsid;
            $.ajax({
                url : encodeURI(url),
                type : "get",
                success : function(data) {
                    var m = eval("(" + data + ")");
                    var login;
                    if(m!=null){
                        login = m.login;
                    }
                    if (login == "login") {
                        document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

                    }
                },
                error : function(data) {
                    alert("失败");
                }
            });
            $(".shouc").css("display","none");
            $(".shouc2").show();
        });
        $(".shouc2").click(function(){
            var url = basePath+"ea/wfjplatform/sajax_ea_ajaxCollect.jspa";
            $.ajax({
                url : encodeURI(url),
                type : "get",
                data : {
                    ppid : ((type=='web' || type=='time')?ppId:ppid),
                    goodsid:goodsid,
                },
                success : function(data) {
                    var m = eval("(" + data + ")");
                    var login;
                    if(m!=null){
                        login = m.login;
                    }
                    if (login == "login") {
                        document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

                    }

                },
                error : function(data) {
                    alert("失败");
                }
            });
            $(".shouc2").css("display","none");
            $(".shouc").show();
        });

        //评论弹框
        $(".news-det_ipy input").click(function(){
            var url = basePath+"ea/wfjplatform/sajax_ea_ajaxWhetherTheLanding.jspa";
            $.ajax({
                url : encodeURI(url),
                type : "post",
                async : true,
                dataType : "json",
                success : function(data) {
                    var m = eval("(" + data + ")");
                    if(m.boolean){
                        $("footer").css("display","none");
                        $(".mengban").show();
                        $(".input-box").show();
                        $(".input-box textarea").focus();
                    }else{
                        document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
                    }
                },
                error : function(data) {
                    alert("失败");
                }
            });

        });
        $(".mengban").click(function(){
            $(".mengban").css("display","none");
            $(".input-box").css("display","none");
            $("footer").show();
        });

        $(".input-box textarea").keyup(function(){
            var t = $(".input-box textarea");
            if (t.val() == "") {
                $(".input-box button").eq(1).attr("style","color:#fff;background:#ddd;");
            }
            else{
                $(".input-box button").eq(1).attr("style","color:#fff;background:#FF6507;");
            }
        });


    });
    //点击发表
    function myFunction(){
        $(".mengban").css("display","none");
        $(".input-box").css("display","none");
        $("footer").show();
        var content = $(".input-box textarea").val();
        var url = basePath+"ea/wfjplatform/sajax_ea_newsComment.jspa?typeNews=0&content="+content;
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async: false,
            data : {
                ppid:(type=='web' || type=='time')?ppId:ppid,
                goodsid:goodsid,
                ccompanyId:ccompanyId,
                type:type,
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var login;
                if(m!=null){
                    login = m.login;
                }
                if (login == "login") {
                    document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";

                }
            },
            error : function(data) {
                alert("失败");
            }
        });
        $(".input-box textarea").val("");
        $(".comment_mil").empty();
        loaded(2);

    }
    function myFunction2(){
        $(".mengban").css("display","none");
        $(".input-box").css("display","none");
        $("footer").show();
    }
</script>
<script>
    if($("audio").length==0){
        $(".paused").hide();
    }
    //新增2016年11月15日 15:16:31
    //点击背景音乐播放图标暂停
    var audio = $(".article_audio")[0];
    if ($(".article_audio").length > 0) {
        $(".music_play").click(function() {
            if (!audio.paused) {
                audio.pause();
                $(this).addClass("paused");
            } else {
                audio.play();
                $(this).removeClass("paused");
            }
        })
        $('html').one('touchstart', function() {
            audio.play();
            $(".music_play").removeClass("paused");
        });

    }
    var v = document.getElementsByTagName("video");
    for (i = 0; i < v.length; i++) {
        v[i].addEventListener("play", function() {
            if ($(".article_audio").length > 0) {
                audio.pause();
                $(".music_play").addClass("paused");
            }
            if($("video").length>1){
                var t=$(this).parent().siblings(".article_p").find("video")[0];
                if(!t.paused){
                    t.load();
                }
            }

        })
    }
</script>
<script>
    //因音乐接口失效,故暂时使用该方法
    if($(".article_audio").length>0){
        $(".article_audio").attr("src",$(".article_audio").attr("data-hash"));
    }
    //因音乐接口失效,故暂时注销该方法
    /* if($(".article_audio").length>0){
            var url=musicPath($(".article_audio").attr("data-hash"));
            while(url==null){
               url=musicPath($(".article_audio").attr("data-hash"));
            }
            $(".article_audio").attr("src",url);
        }
    function musicPath(musichash){
        var judge;
        var url= basePath+"ea/qrshare/sajax_ea_musicPath.jspa?";
         $.ajax({
                url : url,
                type : "post",
                async : false,
                dataType : "json",
                data:{"musicName":encodeURIComponent(musichash)},
                success : function(data) {
                    if(data.indexOf("<title>页面不存在_百度搜索</title>") == -1){
                        var music = eval("(" + data + ")");
                        judge = music.data.url;
                    }
                },error:function(data){

                 alert("查询失败");

                }
         });
         return judge;
    } */

    $(".QR_float").find("i").click(function(){
        $(this).parents(".QR_float").hide();
    })


    //拼货拉赋值
    var pricetype ="${param.pricetype}";
    if(pricetype==1){
        var href = $(".article_pro .pro").attr("href")+"&pricetype=1";
        $(".article_pro .pro").attr("href",href);

        $(".more_pro").attr("href",basePath+"page/ea/collage/phl/phl_product.jsp?ccompanyID="+ccompanyId);

    }
</script>
<script type="text/javascript">
    var  headimg = "${object1[12]}";
    var basePath="<%=basePath%>";
    var  sccid =  "${object1[6]}";
    $(function(){

        jQuery("#qrcode").qrcode({
            render : "canvas",    //设置渲染方式，有table和canvas，使用canvas方式渲染性能相对来说比较好
            text : basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid,
            width : "90",               //二维码的宽度
            height : "90",              //二维码的高度
            background : "#ffffff",       //二维码的后景色
            foreground : "#000000",        //二维码的前景色
            src:basePath+headimg    //二维码中间的图片
        })
        setTimeout(function(){
            var canvas = $('#qrcode canvas')[0]; // 获取 canvas 元素
            var imgSrc = canvas.toDataURL("image/png"); // 将 canvas 转换为 data URL
            $('#qrcode').empty(); // 清空原有的 canvas 元素
            $('<img>').attr('src', imgSrc).appendTo('#qrcode');
        },1000);


            $(document).on("click","#qrcode img",function(){
            if($(this).parent().attr("class").indexOf("check")!=-1){
                $(this).css("width","4rem");
                $(this).parent().removeClass("check");

            }else{
                $(this).css("width","8.8rem");
                $(this).parent().addClass("check");

            }

        });

        $(".wxh-img").click(function(){
            if($(this).attr("class").indexOf("check")!=-1){
                $(this).css("width","4.5rem");
                $(this).removeClass("check");
            }else{
                $(this).css("width","10rem");
                $(this).addClass("check");
            }

        });
    })

</script>
<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none">
    <center>
        <div style="width: 70%; background: rgba(0,0,0, 0.5);">
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>
</html>