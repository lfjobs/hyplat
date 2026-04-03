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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>联营商城业主会员中心</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/swiper-3.3.1.min.css">
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/swiper-3.3.1.jquery.min.js"></script>
<script type="text/javascript">
var basePath = "<%=basePath%>"; 
var ff = document.referrer;

var state = "${cuscom.state}";
var user = "${cuscom.account}";
var sccid = "${cuscom.sccId}";
</script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a onclick="javascript: window.history.go(-1);return false;"target="_self" class="back"></a>
        <h1>业务中心</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
       <!--    banner start    -->
        <%-- 暂时隐藏<section class="m_banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <div class="swiper-slide"><img src="<%=basePath%>images/WFJClient/VipCenter/mem_AD.jpg" alt="banner"></div>
                    <div class="swiper-slide"><img src="<%=basePath%>images/WFJClient/VipCenter/mem_AD.jpg" alt="banner"></div>
                </div>
            </div>

        </section> --%>
        <!--    banner end    -->
        <!--    头像信息 导航 start   -->
        <a href="###" class="mem_headimg_box flex flex_align_center">
            <div class="mem_head_img">
                <img src="<%=basePath%><c:if test='${cuscom.state=="1"}'><s:if test="staff.headimage!=null">${staff.headimage}</s:if><s:else>images/contacts/Network/defaultbig.png</s:else></c:if><c:if test='${cuscom.state=="2"}'><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/contacts/recepit/wfj_huiyuan_01.png</s:else></c:if>">
            </div>
            <div class="mem_head_txt clearfix flex_1">
                <span>
                	<c:if test='${cuscom.state=="1"}'>${staff.staffName } </c:if>
                	<c:if test='${cuscom.state=="2"}'>${contactCompany.companyName }</c:if>
                </span>
                <span>
                	${custypename }
                </span>
            </div>
            <!-- <div class="mem_QR_r m_navbtn" data-modal="modal01"></div> -->
        </a>
        <!--    头像信息 导航 end    -->
        <hr class="mem_hr">
        
        <!--    列表导航 start    -->
        <section>
            <ul class="m_list">
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_01"></div>
                    </div>
                    <div class="list_R"><span>我的名片</span>
	                    <c:if test='${cuscom.state=="1"}'>
                        	<a href="<%=basePath%>ea/perinfor/ea_getPageHomeData.jspa?sccid=${cuscom.sccId}&editType=00&backurl=ea/consignee/ea_toVipCenter.jspa" class="m_more"></a>
                    	</c:if>
                    	<c:if test='${cuscom.state=="2"}'>
                        	<a href="<%=basePath%>ea/industry/ea_getCompanyHome.jspa?ccompanyId=${contactCompany.ccompanyID}&editType=0&backurl=ea/consignee/ea_toVipCenter.jspa&flag=vip" class="m_more"></a>
                    	</c:if>
                    </div>
                </li>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_02"></div>
                    </div>
                    <div class="list_R"><span>我的团队</span>
                        <a href="<%=basePath%>ea/vipcenter/ea_findconWealth.jspa?sccid=${cuscom.sccId }" class="m_more"></a>
                    </div>
                </li>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_03"></div>
                    </div>
                    <div class="list_R"><span>升级管理</span>
                        <a href="<%=basePath%>/ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform" class="m_more"></a>
                    </div>
                </li>
                <%--<li class="flex flex_align_center">--%>
                    <%--<div class="list_L">--%>
                        <%--<div class="list_L_contian list_04"></div>--%>
                    <%--</div>--%>
                    <%--<div class="list_R"><span>公司联营商城会员</span>--%>
                        <%--<a href="<%=basePath%>ea/vipcenter/ea_findconWealth.jspa?sccid=${cuscom.sccId}" class="m_more"></a>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_05"></div>
                    </div>
                    <div class="list_R"><span>电子钱包</span>
                        <a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=${cuscom.account }&sccid=${cuscom.sccId }&khd=0&app=00" class="m_more"></a>
                    </div>
                </li>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_06"></div>
                    </div>
                    <div class="list_R"><span>订单收发</span>
                        <a href="<%=basePath%>ea/vipcenter/ea_orderManage.jspa?sccid=${cuscom.sccId }&ret=ret" class="m_more"></a>
                    </div>
                </li>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_07"></div>
                    </div>
                    <div class="list_R"><span>需求发布</span>
                        <a href="<%=basePath%>ea/vipcenter/ea_vipDemand.jspa?sccid=${cuscom.sccId }" class="m_more"></a>
                    </div>
                </li>
                <%-- <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_10"></div>
                    </div>
                    <div class="list_R"><span>粉丝好友名片商务信息</span>
                        <a href="###" class="m_more"></a>
                    </div>
                </li> --%>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_13"></div>
                    </div>
                    <div class="list_R"><span>二维码分享</span>
                        <a href="<%=basePath%>ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02" class="m_more"></a>
                    </div>
                </li>
                <%-- <li class="flex flex_align_center">
                
                    <div class="list_L">
                        <div class="list_L_contian list_14"></div>
                    </div>
                    <div class="list_R"><span>图片视频等素材库</span>
                        <a href="<%=basePath%>ea/scm/ea_findScmanageIndex.jspa" class="m_more"></a>
                    </div>
                </li> --%>
                <%-- 
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_15"></div>
                    </div>
                    <div class="list_R"><span>激活通讯录粉丝经济</span>
                        <a href="###" class="m_more"></a>
                    </div>
                </li> --%>
                <%--<li class="flex flex_align_center">--%>
                    <%--<div class="list_L">--%>
                        <%--<div class="list_L_contian list_16"></div>--%>
                    <%--</div>--%>
                    <%--<div class="list_R"><span>系统数据客户导入营销</span>--%>
                        <%--<a href="<%=basePath%>ea/vipcenter/ea_importData.jspa?sccid=${cuscom.sccId}" class="m_more"></a>--%>
                    <%--</div>--%>
                <%--</li>--%>
                <li class="flex flex_align_center">
                    <div class="list_L">
                        <div class="list_L_contian list_20"></div>
                    </div>
                    <div class="list_R"><span>共享办公</span>
                        <%--<a href="<%=basePath%>ea/contract/ea_getMyFileList.jspa?module=" class="m_more"></a>--%>

                        <a href="<%=basePath%>/ea/documentcommon/ea_showDocumentModule.jspa?bb=new&module=" class="m_more"></a>
                    </div>
                </li>
            </ul>
        </section>
        <!--    列表导航 end    -->
    </div>
    <!--  页面内容 end -->
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        $(function() {

            //点击会员中心列表导航，跳转到该项相应a(class为m_more)标签链接
            $(".m_list li").click(function(even) {
                even.stopPropagation();
                var _link = $(this).find("a").first().attr("href"); //获取li标签内a的href链接地址
                window.location.href = "" + _link + "";
            });


        });

    </script>
    <script>
        var mySwiper = new Swiper('.swiper-container', {
                    loop: true,
                    direction: 'horizontal',
                    slidesPerView: 1,
                    touchMoveStopPropagation: false,
                    autoplay:3000,
                })

    </script>
</body>
</html>