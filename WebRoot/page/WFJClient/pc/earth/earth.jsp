<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String phone = (String) request.getAttribute("phone"); // 从后端获取手机号
    String state = (String) request.getAttribute("state"); // 从后端获取手机号

%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/pc/earth/earth.css?version=20230709">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/WFJClient/pc/package/css/swiper.css"/>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet">

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

<%--        <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/package/js/swiper.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/earth/earth.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/jweixin-1.5.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>webView/uni.webview.1.5.2.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

    <script>
        initTagCache(); // 页面加载时自动开始监听和恢复标签（可选）
        var wxLogin = "${param.wxLogin}";
        var preUrl = "<%=(String) session.getAttribute("preUrl")%>";
        if (wxLogin == "wx") {

            if (preUrl != "" && preUrl != "null" && preUrl != null && preUrl.indexOf("pc_login") == -1 && preUrl.indexOf("exitLogin") == -1 && preUrl.indexOf("earthIndex") == -1) {
                document.location.href = preUrl;
            }
        }

    </script>

    <title>数字地球</title>

    <style>
        .menu-modal {
            display: none;
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: rgba(0,0,0,0.5); /* 遮罩 */
        }
        .menu-modal .content {
            background: #fff;
            width: 300px;
            margin: 100px auto;
            padding: 20px;
            border-radius: 6px;
        }
    </style>
</head>
<body>
<header>

    <ul class="clearfix">
        <li style="display: flex; align-items: center; padding-left: 12px; gap: 8px">
            <!-- <img src="<%=basePath%>images/WFJClient/pc/newimg/return.png" > -->
            <img src="<%=basePath%>images/WFJClient/pc/newimg/img_56.png" alt="" id="location" style="width:.75rem;">
            <a id="history" href="<%=basePath%>page/WFJClient/pc/earth/country.jsp" style="color: white; display: inline-block; white-space: nowrap;">中国</a>
        </li>
        <li>
            <a href="<%=basePath%>page/ea/main/marketing/supermarket/container/hgmain.jsp">
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_55.png" alt="">
            </a>
        </li>
        <li>
            <img src="<%=basePath%>images/WFJClient/pc/newimg/img_12.png" alt="">
        </li>
    </ul>
</header>
<div class="content">
    <div class="div-search">

        <div class="search-box clearfix">
            <div class="div-left">
                <p><img src="<%=basePath%>images/WFJClient/pc/newimg/img_13.png" alt=""></p>
            </div>
            <input type="text" placeholder="请输入搜索内容" id="searchinput">
            <div class="sousou"><a href="<%=basePath%>page/WFJClient/pc/earth/soso.jsp">都爱搜搜</a></div>
        </div>
    </div>
    <div class="div-zixun">
        <div class="div-top clearfix">
            <div class="clearfix">


                <p id="tuijian"> 推荐</p>

                <p><a href="<%=basePath%>ea/earth/ea_getBrowseCompanyList.jspa">关注</a></p>
                <p><a href="<%=basePath%>ea/5l5c/ea_selectCompany.jspa">抽奖</a></p>
                <p class="active" id="sy">资讯</p>
                <p><a href="<%=basePath%>ea/dsp/ea_fullScreen.jspa">小视频</a></p>
                <p><a href="<%=basePath%>ea/digitalmall/ea_DigitalMall.jspa?back=index">购物</a></p>
                <p><a href="<%=basePath%>ea/qyrz/ea_toPeriphery.jspa">周边</a></p>
                <p><a href="javascript:phl()">市场</a></p>
                <p><a href="<%=basePath%>ea/productAgent/ea_productAgentList.jspa">招商</a></p>

                <p><a href="<%=basePath%>ea/purchasebids/ea_findGoodbidList.jspa">招标</a></p>
                <p class="zp-table" id="zp-table"><a href="<%=basePath%>/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs">招聘</a>
                </p>
                <p><a href="<%=basePath%>page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp">超市</a></p>
                <p><a href="<%=basePath%>ea/industry/ea_getAllCompanyList.jspa?industryType=">商家</a></p>
            </div>
            <p class="p-img" id="p-img">
                <a href="javascript:showMenuManager()">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                </a>
            </p>
            <div id="cle2">
            </div>
        </div>

        <iframe id="iframe" name="iframe" src="" width="100%" frameborder="0" style="margin-top: -0.7rem;"></iframe>
        <%--	<div class="tj" style="display:none;">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_03.png" >
                    </div>
                    <div class="swiper-slide">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_03.png" >
                    </div>
                    <div class="swiper-slide">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_03.png" >
                    </div>
                    <div class="swiper-slide">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_03.png" >
                    </div>
                </div>
                <!-- 如果需要分页器 -->
                <div class="swiper-pagination"></div>
            </div>
            <ul class="ul-list clearfix">
                <li>
                    <a href="<%=basePath%>ea/productAgent/ea_productAgentList.jspa">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_10.png" alt="">
                    </div>
                    <p>
                        代理招商
                    </p>
                        </a>
                </li>
                <li>
                    <a href="<%=basePath%>ea/purchasebids/ea_findGoodbidList.jspa">

                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_07.png" alt="">
                    </div>
                    <p>
                        招标采购
                    </p>
                        </a>
                </li>
                <li>
                    <a href="<%=basePath%>/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs">

                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_13.png" alt="">
                    </div>
                    <p>
                        招聘人才
                    </p>
                    </a>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_15.png" alt="">
                    </div>
                    <p>
                        业务员
                    </p>
                </li>
                <li>
                    <a href="javascript:preCar();">


                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_26.png" alt="">
                    </div>
                    <p>
                        考场约车
                    </p>
                        </a>
                </li>
                <li>
                    <a href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_29.png" alt="">
                    </div>
                    <p>
                        e路快车
                    </p>
                    </a>
                </li>
                <li>
                    <a href="<%=basePath%>ea/consignee/ea_toVipCenter.jspa?backu=2">


                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_21.png" alt="">
                    </div>
                    <p>
                        会员
                    </p>
                    </a>
                </li>
                &lt;%&ndash;<li>&ndash;%&gt;
                    &lt;%&ndash;<div>&ndash;%&gt;
                        &lt;%&ndash;<img src="<%=basePath%>images/WFJClient/pc/newimg/index_23.png" alt="">&ndash;%&gt;
                    &lt;%&ndash;</div>&ndash;%&gt;
                    &lt;%&ndash;<p>&ndash;%&gt;
                        &lt;%&ndash;更多&ndash;%&gt;
                    &lt;%&ndash;</p>&ndash;%&gt;
                &lt;%&ndash;</li>&ndash;%&gt;
            </ul>

        <div class="div-xiaoshipin">
            <div class="div-top clearfix">
                <div>
                    <p class="active">小视频</p>
                    <p>推荐</p>
                    <p>关注</p>
                </div>
                <p class="p-img">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                </p>
            </div>
            <div class="div-video player">
                <video id="video" class="video" data-play="play" x5-playsinline webkit-playsinline="true" playsinline="true" src="https://www.runoob.com/try/demo_source/movie.mp4"></video>
                <div class="div-play">
                    <img class="" src="<%=basePath%>images/WFJClient/pc/newimg/index_144.png" alt="">
                </div>
            </div>
        </div>
        <div class="div-shangjia">
            <div class="div-top clearfix">
                <div class="clearfix">
                    <p class="active">商家</p>
                    <p>推荐</p>
                    <p>关注</p>
                </div>
                <p class="p-img">
                    <a href="<%=basePath%>ea/qyrz/ea_toPeriphery.jspa">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                        </a>
                </p>
            </div>
            <div class="div-kuaijie clearfix">
                <div class="clearfix">
                    <a href="<%=basePath%>/ea/digitalmall/ea_DigitalMall.jspa?back=index"><p>快捷商城</p></a>
                    <a href="<%=basePath%>page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp"><p>推荐超市</p></a>
                    <a href="javascript:phl()"><p>批发市场</p></a>
                </div>
                <p>
                    更多>>
                </p>
            </div>
            <div class="div-list">
                <ul class="clearfix">
                    <li>
                        <div>
                            <img class="img01" src="<%=basePath%>images/WFJClient/pc/newimg/index_51.png" alt="">
                        </div>
                        <p>周边商家</p>
                    </li>
                    <li>
                        <div>
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_39.png" alt="">
                        </div>
                        <p>餐饮</p>
                    </li>
                    <li>
                        <div>
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_42.png" alt="">
                        </div>
                        <p>购物</p>
                    </li>
                    <li>
                        <div>
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_44.png" alt="">
                        </div>
                        <p>生活</p>
                    </li>
                    <li>
                        <div>
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_46.png" alt="">
                        </div>
                        <p>体育休闲</p>
                    </li>
                    <li>
                        <a href="<%=basePath%>ea/qyrz/ea_toPeriphery.jspa">
                        <div>

                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_48.png" alt="">

                        </div>
                        <p>更多</p>
                        </a>
                    </li>
                </ul>
            </div>
            <div class="div-renling clearfix">
                <p><a href="<%=basePath%>ea/industry/ea_getAllCompanyList.jspa?industryType=">商家认领</a></p>
                <p>商家未领</p>
                <p>快捷商城</p>
            </div>
            <div class="div-shop">
                <ul>
                    <li class="clearfix">
                        <div class="div-img1">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_60.png" alt="">
                        </div>
                        <div class="div-text">
                            <p>张烧包烤肉店</p>
                            <p>锦江区龙王庙正街21号</p>
                        </div>
                        <div class="div-text3">
                            <p>请认领</p>
                            <p>290米</p>
                        </div>
                        <div class="div-text2">
                            <p>￥10.9</p>
                            <p>97万评论</p>
                        </div>

                        <div class="div-img2">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_63.png" alt="">
                        </div>

                    </li>
                    <li class="clearfix">
                        <div class="div-img1">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_60.png" alt="">
                        </div>
                        <div class="div-text">
                            <p>张烧包烤肉店</p>
                            <p>锦江区龙王庙正街21号</p>
                        </div>
                        <div class="div-text3">
                            <p>请认领</p>
                            <p>290米</p>
                        </div>

                        <div class="div-text2">
                            <p>￥10.9</p>
                            <p>97万评论</p>
                        </div>
                        <div class="div-img2">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_63.png" alt="">
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="div-img1">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_60.png" alt="">
                        </div>
                        <div class="div-text">
                            <p>张烧包烤肉店</p>
                            <p>锦江区龙王庙正街21号</p>
                        </div>
                        <div class="div-text3">
                            <p>请认领</p>
                            <p>290米</p>
                        </div>
                        <div class="div-text2">
                            <p>￥10.9</p>
                            <p>97万评论</p>
                        </div>

                        <div class="div-img2">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_63.png" alt="">
                        </div>

                    </li>
                </ul>
            </div>
        </div>
        <div class="div-shichang">
            <div class="div-top clearfix">
                <div class="clearfix">
                    <p class="active">共享市场</p>
                </div>
                <p class="p-img">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                </p>
            </div>
            <div class="div-shop">
                <div class="clearfix div-list">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_68.png" alt="">
                    </div>
                    <div class="div-text">
                        <div class="div-title">
                            <p>祺黎阁家具官方旗舰店</p>
                            <p>旗舰店</p>
                        </div>
                        <div class="div-xing">
                            <p class="p-img"><img src="<%=basePath%>images/WFJClient/pc/newimg/index_71.png" alt=""></p>
                            <p>已拼7558件</p>
                        </div>
                    </div>
                    <div class="div-right">
                        <p>
                            进店逛逛
                        </p>
                    </div>
                </div>
                <div class="clearfix div-list">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_68.png" alt="">
                    </div>
                    <div class="div-text">
                        <div class="div-title">
                            <p>祺黎阁家具官方旗舰店</p>
                            <p>旗舰店</p>
                        </div>
                        <div class="div-xing">
                            <p class="p-img"><img src="<%=basePath%>images/WFJClient/pc/newimg/index_71.png" alt=""></p>
                            <p>已拼7558件</p>
                        </div>
                    </div>
                    <div class="div-right">
                        <p>
                            进店逛逛
                        </p>
                    </div>
                </div>
                <div class="div-more">
                    <p>
                        更多
                    </p>
                </div>
            </div>
        </div>
        <div class="div-wuliu">
            <div class="div-top clearfix">
                <div class="clearfix">
                    <p class="active">物流商城</p>
                </div>
                <p class="p-img">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                </p>
            </div>
            <div class="div-sh">
                <div class="div-list clearfix">
                    <ul class="clearfix">
                        <li class="active">
                            全部商品
                        </li>
                        <li>
                            水果
                        </li>
                        <li>
                            粮油
                        </li>
                        <li>
                            肉产品
                        </li>
                        <li>
                            蔬菜
                        </li>
                    </ul>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_103.png" alt="">
                    </div>
                </div>
                <div class="div-con">
                    <div class="div-left">
                        <ul>
                            <li>
                                罐头
                            </li>
                            <li>
                                休闲食品
                            </li>
                            <li>
                                特殊品
                            </li>
                            <li>
                                薯芋类
                            </li>
                            <li>
                                腊肉
                            </li>
                        </ul>
                    </div>
                    <div class="div-right">
                        <ul class="ul-top">
                            <li class="clearfix">
                                <div class="div-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_145.png" alt="">
                                </div>
                                <div class="div-text">
                                    <p>测试产品勿拍</p>
                                    <p>￥1</p>
                                </div>
                                <div class="div-right-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="div-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_145.png" alt="">
                                </div>
                                <div class="div-text">
                                    <p>测试产品勿拍</p>
                                    <p>￥1</p>
                                </div>
                                <div class="div-right-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                                </div>
                            </li>

                            <li class="clearfix">
                                <div class="div-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_145.png" alt="">
                                </div>
                                <div class="div-text">
                                    <p>测试产品勿拍</p>
                                    <p>￥1</p>
                                </div>
                                <div class="div-right-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                                </div>
                            </li>
                            <li class="clearfix">
                                <div class="div-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_145.png" alt="">
                                </div>
                                <div class="div-text">
                                    <p>测试产品勿拍</p>
                                    <p>￥1</p>
                                </div>
                                <div class="div-right-img">
                                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                                </div>
                            </li>
                        </ul>
                        &lt;%&ndash;<ul class="ul-bottom clearfix">&ndash;%&gt;
                            &lt;%&ndash;<li class="active">&ndash;%&gt;
                                &lt;%&ndash;特价扫把&ndash;%&gt;
                            &lt;%&ndash;</li>&ndash;%&gt;
                            &lt;%&ndash;<li>&ndash;%&gt;
                                &lt;%&ndash;特价扫把&ndash;%&gt;
                            &lt;%&ndash;</li>&ndash;%&gt;
                        &lt;%&ndash;</ul>&ndash;%&gt;
                    </div>
                </div>
                <div class="div-shop-bottom clearfix">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_75.png" alt="">
                                <span>
                                    6
                                </span>
                    </div>
                    <div class="div-text">
                        <p>合计：￥22844.01</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="div-zhaoshang">
            <ul class="ul-top clearfix">
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_79.png" alt="">
                    </div>
                    <p>代理招商</p>
                </li>
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_82.png" alt="">
                    </div>
                    <p>招业务员</p>
                </li>
            </ul>
            <div class="div-cn">
                <ul class="ul-top clearfix">
                    <li class="active">
                        贴牌产品
                    </li>
                    <li>
                        设备安装产品
                    </li>
                    <li>
                        县级代理产品
                    </li>
                    <li>
                        省级代理产品
                    </li>
                    <li>
                        镇级代理产品
                    </li>
                </ul>
                <ul class="ul-bottom">
                    <li class="clearfix">
                        <div class="div-img">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_87.png" alt="">
                        </div>
                        <div class="div-text1">
                            大良缘
                        </div>
                        <div class="div-text2">
                            <p>代理招商</p>
                            <p>招业务员</p>
                        </div>
                        <div class="divRight">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_90.png" alt="">
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="div-img">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_87.png" alt="">
                        </div>
                        <div class="div-text1">
                            大良缘
                        </div>
                        <div class="div-text2">
                            <p>代理招商</p>
                            <p>招业务员</p>
                        </div>
                        <div class="divRight">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_90.png" alt="">
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="div-zhaobiao">
            <ul class="ul-top">
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_79.png" alt="">
                    </div>
                    <p>招标采购</p>
                </li>
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_82.png" alt="">
                    </div>
                    <p>发布采购</p>
                </li>
            </ul>
            <div class="div-cn">
                <div class="div-list">
                    <ul>
                        <li>
                            全部商品
                        </li>
                        <li>
                            水果
                        </li>
                        <li>
                            粮油
                        </li>
                        <li>
                            肉产品
                        </li>
                        <li>
                            蔬菜
                        </li>
                    </ul>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_103.png" alt="">
                    </div>
                </div>
                <ul class="ul-bottom">
                    <li class="clearfix">
                        <div class="div-img">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_107.png" alt="">
                        </div>
                        <div class="div-text1">
                            <p>勾缝剂</p>
                            <p>2022年8月23日</p>
                        </div>
                        <div class="div-text2">
                            <p>已截至采购</p>
                        </div>
                    </li>
                    <li class="clearfix">
                        <div class="div-img">
                            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_110.png" alt="">
                        </div>
                        <div class="div-text1">
                            <p>角钢干挂</p>
                            <p>2022年8月23日</p>
                        </div>
                        <div class="div-text3">
                            <p>正在招标采购</p>
                            <p>供应商请投标</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="div-zhaopin">
            <ul class="ul-top clearfix">
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_113.png" alt="">
                    </div>
                    <p>招聘人才</p>
                </li>
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_116.png" alt="">
                    </div>
                    <p>发布招聘</p>
                </li>
            </ul>
            <ul class="ul-bottom">
                <li class="clearfix">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_121.png" alt="">
                    </div>
                    <div class="div-text1">
                        <p>销售人才</p>
                        <p>胜威驾校</p>
                    </div>
                    <div class="div-text2">
                        <p>经验三年</p>
                        <p>2022年8月23日</p>
                    </div>
                    <div class="divRight">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_90.png" alt="">
                    </div>
                </li>
                <li class="clearfix">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_121.png" alt="">
                    </div>
                    <div class="div-text1">
                        <p>设计师</p>
                        <p>北京天太世统科技有限公司</p>
                    </div>
                    <div class="div-text2">
                        <p>经验三年</p>
                        <p>2022年8月23日</p>
                    </div>
                    <div class="divRight">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_90.png" alt="">
                    </div>
                </li>
            </ul>
            <div class="div-more">
                <p>
                    <a href="<%=basePath%>ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs">
                    更多
                        </a>
                </p>
            </div>
        </div>
        <div class="div-zixunx">
            <div class="div-top clearfix">
                <p>
                    接入三方平台
                </p>
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">
                </div>
            </div>
            <ul class="ul-top clearfix">
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_125.png" alt="">
                    </div>
                    <p>我要咨询</p>
                </li>
                <li>
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_128.png" alt="">
                    </div>
                    <p>通讯</p>
                </li>
            </ul>
            <div class="div-con">
                <div class="p-top">
                    立即预约免费咨询
                </div>
                <div class="div">
                    <p class="clearfix">
                        <label for="">
                            姓名<span>*</span>
                        </label>
                        <input type="text" value="刘**" >
                    </p>
                    <p class="clearfix">
                        <label for="">
                            电话<span>*</span>
                        </label>
                        <input type="text" value="158****9888" >
                    </p>
                    <p class="clearfix">
                        <label for="">
                            公司名称
                        </label>
                        <input type="text" placeholder="请输入公司名称" value="" >
                    </p>
                    <p class="clearfix">
                        <label for="">
                            职位
                        </label>
                        <input type="text" placeholder="请输入" value="" >
                    </p>
                </div>
                <div class="div-text clearfix">
                    <div class="div-img">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/index_133.png" alt="">
                    </div>
                    <p>
                        自动输入历史信息<span><个人信息授权与隐私政策></span>
                    </p>
                </div>
                <div class="div-but">
                    <p>
                        立即提交预约
                    </p>
                </div>
                <div class="div-er">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_137.png" alt="">
                    <p>【扫描二维码，交换名片】</p>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index_140.png" alt="">
                </div>
            </div>
        </div>
        <div class="div-jianjie">
            <p>简介</p>
            <img src="<%=basePath%>images/WFJClient/pc/newimg/index_142.png" alt="">
        </div>
        <div class="divBottom">
            <p>Copyright © 2010-2018北京天太世统科技有限公司</p>
            <a href="https://beian.miit.gov.cn/" target="_blank">京ICP备11016224号-2</a>
        </div>

        <div class="content-bottom"></div>
            </div>--%>

    <div class="footer div-bottom">
        <ul class="clearfix">
            <li>
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
                </div>
                <p>
                    消息
                </p>
            </li>
            <li>
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
                </div>
                <p>
                    通讯
                </p>
            </li>
            <li class="active">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/index.png" alt="">
                </div>
                <p>
                    数字
                </p>
            </li>
            <li>
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_10.jpg" alt="">
                </div>
                <p>
                    5L5C
                </p>
            </li>
            <li>
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                </div>
                <p>
                    我的
                </p>
            </li>
        </ul>
    </div>

        <!-- 弹窗 -->
        <div class="menu-modal" id="menu-modal">

            <div class="menu-content">
                <div>
<%--                    <img src="<%=basePath%>images/WFJClient/PersonalJoining/left_jt.png" onclick="saveMenus()" style="width: 10px; height: 10px; vertical-align: middle;">--%>
                    <span onclick="saveMenus()">返回</span>
                </div>
                <h6>默认项目</h6>
                <div id="follow-list" class="menu-section"></div>

                <h6>管理项目</h6>
                <div id="unfollow-list" class="menu-section"></div>
            </div>
        </div>

    <%--		<!-- 触发按钮 -->--%>
    <%--		<div class="container center-align" style="margin-top: 100px;">--%>
    <%--			<a class="waves-effect waves-light btn modal-trigger" href="#bindPhoneModal">绑定手机号</a>--%>
    <%--		</div>--%>

    <!-- 弹窗 -->
    <!-- 绑定手机号弹窗 -->
    <div id="bindPhoneModal" class="modal" data-dismissible="false" style="border-radius: 12px;">
        <div class="modal-content">
            <h5 class="blue-text text-darken-2 center-align">📱 绑定手机号</h5>

            <!-- 手机号输入框 -->
            <div class="input-field">
                <input id="phone" type="tel" class="validate" maxlength="11">
                <label for="phone">请输入手机号</label>
            </div>
            <div class="input-field">
                <input id="code" type="text" class="validate" maxlength="6">
                <label for="code">验证码</label>
            </div>
            <a class="waves-effect waves-light btn-small blue" onclick="sendCode()">发送验证码</a>

        </div>

        <!-- 弹窗底部操作按钮 -->
        <div class="modal-footer" style="display: flex; justify-content: space-between; padding: 10px 20px;">
            <a href="#!" class="waves-effect waves-light btn blue darken-2"
               style="border-radius: 20px;"
               onclick="bindPhone()">
                <i class="material-icons left">确定绑定</i>
            </a>
        </div>
    </div>


</body>

<!-- 初始化脚本 -->
<script>

    document.addEventListener('DOMContentLoaded', function () {

        var elems = document.querySelectorAll('.modal');
        M.Modal.init(elems, {
            dismissible: false  // 禁止点击阴影关闭
        });
        var phone1 = "<%= phone == null ? "" : phone %>";
        var state1 = "<%= state == null ? "" : state %>";
        if (!phone1 && state1) {
            var modal = document.getElementById('bindPhoneModal');
            var instance = M.Modal.getInstance(modal);
            instance.open();

        }
    });

    function sendCode() {
        const phone = document.getElementById("phone").value;
        if (phone.length === 11) {
            M.toast({html: '验证码已发送到 ' + phone});
        } else {
            M.toast({html: '请输入有效的手机号'});
        }
        fetch(basePath + "ea/earth/ea_sendMessage.jspa?phone=" + encodeURIComponent(phone), {
            method: "GET", // 或 "POST"，看后端接口要求
            headers: {
                "Accept": "application/json"
            }
        }).then(response => response.json())
            .then(data => {
                if (data.success) {
                    M.toast({html: '✅ 发送成功：' + phone});

                } else {
                    M.toast({html: '❌ 发送失败：'});
                }
            })
    }

    function bindPhone() {
        const phone = document.getElementById("phone").value;
        const code = document.getElementById("code").value;

        // 通过 fetch 发送异步请求，不跳转页面
        fetch(basePath + "ea/earth/ea_bindPhone.jspa?phone=" + encodeURIComponent(phone) + "&code=" + code, {
            method: "GET", // 或 "POST"，看后端接口要求
            headers: {
                "Accept": "application/json"
            }


        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    M.toast({html: '✅ 绑定成功：' + phone});

                    // 关闭弹窗
                    const modal = M.Modal.getInstance(document.getElementById('bindPhoneModal'));
                    modal.close();

                    // 你也可以根据返回值跳转页面
                    // window.location.href = basePath + "ea/earth/success.jsp";

                } else {
                    M.toast({html: '❌ 绑定失败：' + data.message});
                }
            })
            .catch(error => {
                console.error("请求出错", error);
                M.toast({error});
            });
    }
</script>
<script type="text/javascript">

    document.addEventListener("DOMContentLoaded", function () {
        // 取本地缓存的国家名
        const savedCountry = localStorage.getItem("国家");

        // 如果有缓存，替换 <a> 的文本
        if (savedCountry) {
            const historyLink = document.getElementById("history");
            if (historyLink) {
                historyLink.innerText = savedCountry;
            }
        }
        restoreFunctionTags();
        if (typeof Android !== 'undefined') {
            setShowFromSystem("p-img");
            setShowFromSystem("location");
            setShowFromSystem("zp-table");

            // const el = document.getElementById('sy');
            // el.remove();
            // const tj = document.getElementById('tuijian');
            // tj.setAttribute('class', "active");
            // $("#iframe").attr("src", basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web&tj=tj");
            // $('#iframe').contents().find("header").remove();


        }

    });
</script>
<!-- <script src="js/less.js" type="text/javascript" charset="utf-8"></script> -->
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sccid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var companyID = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';
    // 小程序-跳转uniapp（api）
    document.addEventListener('UniAppJSBridgeReady', function () {
        let currentEnvironment = "";
        uni.getEnv(function (res) {
            console.log(JSON.stringify(res));
            currentEnvironment = Object.keys(res)[0]
        })
        if (currentEnvironment == 'miniprogram') {
            uni.switchTab({
                url: '/pages/index/index?webview=webviewIndex'
            })
            return;
        }
    });
    let mySwiper = new Swiper('.swiper-container', {
        effect: 'fade',          //滑动效果
        direction: 'horizontal',
        loop: true,
        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination',
        },
        autoplay: {
            stopOnLastSlide: true,
        },
    })
    //点击播放暂停
    $(".div-play").click(function () {
        if ($("#video").data("play") == "play") {
            $(".player").find('video')[0].play();
            $("#video").data("play", "pause");
            $(".div-play img").hide();
        } else {
            $(".player").find('video')[0].pause();
            $("#video").data("play", "play");
            $(".div-play img").show();
        }
    })

    window.onload = function () {
        clearAllCookie();
        $('#iframe').contents().find(".co" +
            "m_head").remove();
        $('#iframe').contents().find("body").css("margin-top", "-3rem");
    }
</script>
</html>
