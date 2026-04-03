<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>批发商城</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/wholesaleMall/wholesaleMall.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/wholesaleMall/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/wholesaleMall/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/wholesaleMall/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/jquery.fly.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath %>js/scMobile/wholesaleMall/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>--%>
    <%--<script src="<%=basePath %>js/scMobile/wholesaleMall/swiper/dySelect.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath %>js/scMobile/wholesaleMall/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/unmannedsupermarket/fastclick.js" type="application/javascript" charset="utf-8"></script>
</head>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    //var ccompanyID = '<%=paramMap != null? paramMap.get("companyId"):""%>';//公司id
    var pfscReturnFlag = '<%= paramMap != null
					? paramMap.get("pfscReturnFlag"):""%>';//返回标识

    var ccompanyID = "${ccompanyID}";//公司id
    var companyName = "${companyName}";//公司名称
    var codePID = "${codePID}";//物类id[该超市所有商品分类的父id]
    var companyId = "${companyId}";
    var ccomIDPlatform = "${param.ccomIDPlatform}";
    console.log(ccompanyID+"-----"+companyId+"---"+companyName);
    //大屏用
    var posNum = "";//大屏id
    var pagenumber = 0;
    var pagecount = 0;
    var industryType = '${industryType}';
    var dpFlag = false;//大屏标识
    var relateID = "";
    var t;
    var tt = 0;
    try {
        //判断是否是大屏终端
        posNum = Android.forAndroidDeviceId();
        var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async:false,
            data : {
                posNum:posNum
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                if(result!="0"){
                    posNum = "";
                }
            },
            error : function(data) {
                // alert("验证失败");
                posNum = "";
            }
        });
        console.log('---'+posNum);
        if (posNum == null || posNum == "") {//跳转小屏
            dpFlag = false;
        } else {//跳转大屏
            dpFlag = true;
        }
    } catch (e) {
        //posNum="777";
        dpFlag = false;
//        if(($(window).width()>=1080&&$(window).height()>=1546)||($(window).width()==534&&$(window).height()==636)||($(window).width()==534&&$(window).height()==782)) {//为大屏
//            //posNum = '520db076df6202c7';//TODO 测试用
//            dpFlag = true;
//        }else{//小屏
//            dpFlag = false;
//        }
    }
    
    var scriptNode = document.createElement("script");

        scriptNode.setAttribute("type", "text/javascript");

         if(dpFlag==false){
            scriptNode.setAttribute("src", basePath+"js/scMobile/wholesaleMall/wholesaleMall.js");//小屏幕
        }else{
            scriptNode.setAttribute("src", basePath+"js/scMobile/wholesaleMall/dpWholesaleMall.js");//大屏幕
        }
      document.head.appendChild(scriptNode); 

</script>
<body class="hy">
<header>
    <ul class="clearfix">
        <%-- <li onclick="javascript:window.history.back();return false;">--%>
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/wholesaleMall/register_return.png" />
        </li>
			<c:if test='${param.phl ne "phl" }'>
				<li id="ttsw_dp_gsmc">批发商城 <img
					src="<%=basePath%>images/scMobile/wholesaleMall/qiehuan.png" /> <span
					onclick="toBussList();">${companyName}</span></li>
			</c:if>
			<c:if test='${param.phl eq "phl"}'>
				<li id="ttsw_dp_gsmc"><span>${companyName}</span></li>
			</c:if>
			<li>
           <%--  <img src="<%=basePath%>images/scMobile/wholesaleMall/yusuan_img_02.png" /> --%>
        </li>
    </ul>
</header>
<div class="content">
    <nav class="clearfix">
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/wholesaleMall/wupinguanli_03.png" onclick="toGoodsSearch();"/>
            <input type="text" class="" placeholder="搜索商品" name="search" id="ttsw_search_id" value="" />
        </section>
        <div class="ttsw_searchCss"  onclick="toGoodsSearch();">
            <span>搜索</span>
        </div>
    </nav>
    <div class="select_box select_box1"></div>
    <section class="sec_bg">
    <c:if test="${ppid ne null&&ppid ne ''}">
        <a href="<%=basePath%>/ea/industry/ea_informationDetails.jspa?ppId=${ppid}&ccompanyId=&type=time&miniSystemJudge=03&pricetype=1">
        <img src="<%=basePath%>${image}"  onerror="this.src='<%=basePath%>images/scMobile/wholesaleMall/1_03.png'" />
       </a>
     </c:if> 
     <c:if test="${ppid eq null||ppid==''}">
        <img src="<%=basePath%>${image}"  onerror="this.src='<%=basePath%>images/scMobile/wholesaleMall/1_03.png'" />

     </c:if>  
        
    </section>
    <!--数据加载出来前提示-->
    <span id="ttsw_ts_hide"></span>
    <%--<div id="ttsw_ts_hide" style="font-size:0.8rem;text-align:center;display: block;">数据获取中</div>--%>
    <div class="content_search clearfix">
        <section class="clearfix">
					<span class="li_last">
					<img src="<%=basePath %>images/scMobile/wholesaleMall/img_2_05.png"/>
				</span>
            <div class="all_classification">
                <h2>
                    全部分类
                    <img src="<%=basePath %>images/scMobile/wholesaleMall/img_3_11.png" class="close_all_classification" />
                </h2>
                <div>
                    <menu class="clearfix" id="ttsw_one_goods_Classify_All">
                    </menu>
                </div>
            </div>
        </section>
        <div id="ttsw_one_goods_Classify">
        </div>
        <div class="tab_level_son clearfix ttsw_wfl">
            <div class="tab_two_level_son" id="ttsw_two_goods_Classify"></div>
            <section class="box_right">
                <menu id="ttsw_three_goods_Classify">
                </menu>
            </section>
        </div>
    </div>
    <section class="footer fixed_bottom clearfix ">
        <div class="left clearfix">
            <div id="end" onclick="toShopCartList();">
                <img src="<%=basePath %>images/scMobile/wholesaleMall/bijiacaigou_15.png" alt="" />
                <span id="num_shop">0</span>
            </div>
            <div class="ttsw_dpzj_hide">
                <p>
                    合计：¥<span class="txt" id="number_shop">0</span>
                </p>
                <p>
                    共<span id="number_num">0</span>种商品
                </p>
            </div>
        </div>
        <div class="right">
    <!--         <input type="button" name="" id="" value="采购单预算" />
            <input type="button" name="" id="" value="加入预算单" /> -->
        </div>
    </section>
</div>
<div class="pecifications" id="ttsw_ttsw_three_cm_goods_Classify">
</div>
<%--添加用input隐藏域--%>
<input type="hidden" id="ttsw_all_num" value=""/><%--购物车总数--%>
<input type="hidden" id="ttsw_all_price" value=""/><%--购物车总金额--%>
<%--大屏用--%>
<input type="hidden" id="hiddenNumber"/>
<input type="hidden" id="companyId"/>
<input type="hidden" class="skuId"/>
<ul id="specifications"></ul>
<%--异步获取所有信息--%>
<script src="<%=basePath %>js/scMobile/util/FourItemsOperation.js" type="text/javascript" charset="utf-8"></script>

</body>
<script>
    //tab标签 水平总导航
    $(".tab_level_father li").not(".li_last").click(function(){
        $(".tab_level_father li").removeClass("active");
        $(this).addClass("active");
    })
    //tab标签 左侧导航
    $(".tab_level_son li").click(function(){
        $(".tab_level_son li").removeClass("active");
        $(this).addClass("active");
    })
    //计算总列表宽度
    var listWidth_1=$(".tab_level_father li").length;
    var listWidth=0;
    for(var i=0;i<listWidth_1;i++){
        listWidth+=$(".tab_level_father").children("li").eq(i).outerWidth(true);
    }
    $(".tab_level_father").width(listWidth+10);
    //高度计算
    var delet_height=$(".tab_level_father").outerHeight(true)+$(".fixed_bottom").outerHeight(true);
    $(".element").css("height",$(window).height()-delet_height+"px");
    $(".box_right").css("height",$(window).height()-delet_height+"px");
    //详细列表
    $(".li_last").click(function(){
        //alert($(".pecifications").css("bottom"))
        $("body").addClass("body_yc");
        if($(".pecifications").css("bottom")!="1px"){
            var all_classificationHeight=$(window).height();
            $(".all_classification").animate({
                height:all_classificationHeight
            },"slow")
        }
    })
    $(".close_all_classification").click(function(){
        $(".all_classification").animate({
            height:0
        },"slow")
        $("body").removeClass("body_yc");
    })
    $(".all_classification menu li").click(function(){
        $("body").removeClass("body_yc");
        $(".all_classification").animate({
            height:0
        },"slow")
        var scrleft1=$(".tab_level_father li:first-of-type").outerWidth(true);
        var scrleft2=$(".tab_level_father li").eq(2).outerWidth(true);
        scrleft=scrleft1+(scrleft2*($(this).index()-1))-15;
        $(".tab_level_father").parent().scrollLeft(scrleft);
        $(".tab_level_father li").eq($(this).index()).trigger('click');
    })
</script>
<script>
    //后退
    function toBack() {
       var phl = "${param.phl}";
       if(phl=="phl"){
            window.history.back();
            return false;
     
        }
        var url = "";
        if(pfscReturnFlag == "" || pfscReturnFlag == "null" || pfscReturnFlag == "0"){//后退到预算单页面
            window.location.href = basePath + "ea/scBudget/ea_toPayBudgetList.jspa";
        }else{//后退到跳转链接页面
            toReturnForUrl();
        }
    }

    //后退至跳转链接页
    function toReturnForUrl() {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            Android.callAndroidjianli();//调用安卓接口
        } else if (isiOS == true) {
            console.log("IOS");
          // window.history.back();
        }
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