<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <link rel="stylesheet" href="<%=basePath%>css/ea/websitemall/card/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/websitemall/card/mem_center.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/websitemall/card/swiper-3.3.1.min.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/swiper-3.3.1.jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_list.js" type="text/javascript"></script>

    <title>商城业主会员名片</title>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a href="javascript:;" class="back wfj_return display"></a>
        <h1>商城业主会员名片</h1>
        <a href="javascript:;" class="head_R display">展示设置</a>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
    	<input type="hidden" id="staffId" value="${obj[0]}">
        <section class="mp_info">
            <div class="mp_headimg">
                <a href="javascript:;"><img src="<%=basePath%>${obj[2]}" alt=""></a>
            </div>
            <span>${obj[1]}</span>
            <span>${obj[3]=='3'?'合伙人商城业主会员':
                    					obj[3]=='4'?'微分金商城业主会员':
                    					obj[3]=='5'?'代理商商城业主会员':'客户'}</span>
        </section>
        <section class="mp_btnwrap">
            <a href="javascript:;" class="mp_edit mp_btn display" id="more"><i></i><span>编辑基本资料</span></a>
            <a href="javascript:;" class="mp_send mp_btn  wfj01_006_ewm" id="exchange"><i></i><span>发名片</span></a>
        </section>
        <section class="mp_contact">
            <span>联系电话：<small>${obj[4]}</small></span>
            <span>行业分类：<small>${obj[10]}</small></span>
            <span>公司名称：<small>${obj[6]}</small></span>
            <span>职位工种：<small>${obj[11]}</small></span>
        </section>
        <hr class="mp_hr">
        <section class="mp_box section certificates display" >
            <div class="mp_box_hd wfj01_006_click01">
                <div class="mp_navico mp_nav01"></div>
                <div class="mp_nav_tit"><span>证件信息</span><i></i></div>
                <a href="javascript:;" class="m_more"></a>
            </div>
            <div class="mp_box_bd">
                <div class="idcard">
                    <a href="javascript:;"><img src="<%=basePath%>${paper.positive}" alt=""></a>
                </div>
                <div class="idcard_txt">身份证正面照</div>
            </div>
        </section>
        <hr class="mp_hr section_hr display">
        <section class="mp_box section bankCard display">
            <div class="mp_box_hd" id="bank">
                <div class="mp_navico mp_nav02"></div>
                <div class="mp_nav_tit"><span>银行卡</span><i></i></div>
                <a href="javascript:;" class="m_more"></a>
            </div>
            <div class="mp_box_bd">
                <div class="band_card">
                    <div class="band_name"><i></i><span>${bank.bankName}</span></div>
                    <div class="band_num">${bank.bankAccount}</div>
                    <div class="band_txt">
                        VALID
                        <br>THRU
                    </div>
                    <div class="band_sign">
                        <img src="<%=basePath%>images/ea/websitemall/card/yinlianixo.png" alt="">
                    </div>
                </div>
            </div>
        </section>
        <hr class="mp_hr section_hr display">
        <section class="mp_box section goodsReceipt display">
            <div class="mp_box_hd wfj01_006_click09">
                <div class="mp_navico mp_nav03"></div>
                <div class="mp_nav_tit"><span>收货地址</span><i></i></div>
                <a href="javascript:;" class="m_more"></a>
            </div>
            <div class="mp_box_bd">
                <div class="mp_arr_top">
                    <span>收货人：${addres.consignee}</span>
                    <span>${addres.phoneNumber}</span>
                </div>
                <div>
                    收货地址：${addres.province}${addres.city}${addres.area}${addres.address}
                </div>
            </div>
        </section>
        <hr class="mp_hr section_hr display">
        <section class="mp_box section rewardStyle">
            <div class="mp_box_hd wfj01_006_click02">
                <div class="mp_navico mp_nav04"></div>
                <div class="mp_nav_tit"><span>奖励风采</span><i></i></div>
                <a href="javascript:;" class="m_more display"></a>
            </div>
            <div class="mp_box_bd">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                    	<c:if test="${fn:length(styleList)==0}">
		            		无数据
		            	</c:if>
                    	<c:forEach items="${styleList}" var="sl">
                        	<div class="swiper-slide"><img src="<%=basePath%>${sl[0]}" alt=""></div>
                    	</c:forEach>
                    </div>
                </div>
                <div class="overlay" id="overlay">
                    <section class="modal award_img"></section>
                </div>
            </div>
        </section>
        <hr class="mp_hr section_hr">
        <section class="mp_box section resume display ">
            <div class="mp_box_hd">
                <div class="mp_navico mp_nav05"></div>
                <div class="mp_nav_tit"><span>求职简历</span><i></i></div>
                <a href="javascript:;" class="m_more"></a>
            </div>
            <div class="mp_contact mp_zp">
                <span>期望职位：<small>${job.position}</small></span>
                <span>期望薪资：<small>${job.salary}元/月</small></span>
                <span>期望地区：<small>${job.region}</small></span>
            </div>
        </section>
        <hr class="mp_hr section_hr display">
        <section class="mp_box section journalism">
            <div class="mp_box_hd wfj01_006_click07">
                <div class="mp_navico mp_nav06"></div>
                <div class="mp_nav_tit"><span>个人新闻</span><i></i></div>
                <a href="javascript:;" class="m_more display"></a>
            </div>
            <div class="mp_box_bd">
            	<c:if test="${fn:length(articleList)==0}">
            		无数据
            	</c:if>
            	<c:forEach items="${articleList}" var="al" varStatus="a">
            		<c:if test="${a.index<3}">
            			<a href="javascript:;" class="article_box" id="${al[0]}">
		                    <div class="article_L">
		                        <div class="article_img">
		                            <img src="<%=basePath%>${al[3]}" alt="">
		                        </div>
		                    </div>
		                    <div class="article_R">
		                        <h3>${al[1]}</h3>
		                        <p>${al[2]}</p>
		                    </div>
	              	  	</a>
            		</c:if>
            		<c:if test="${a.index>2}">
            			<a href="javascript:;" class="article_box" id="${al[0]}" style="display: none;">
		                    <div class="article_L">
		                        <div class="article_img">
		                            <img src="<%=basePath%>${al[3]}" alt="">
		                        </div>
		                    </div>
		                    <div class="article_R">
		                        <h3>${al[1]}</h3>
		                        <p>${al[2]}</p>
		                    </div>
	              	  	</a>
            		</c:if>
            	</c:forEach>             
                <a href="javascript:;" class="article_more wfj01_006_click07">
                    点击查看更多
                </a>
            </div>
        </section>
        <hr class="mp_hr section_hr">
        <section class="mp_box section forum">
            <div class="mp_box_hd wfj01_006_click06">
                <div class="mp_navico mp_nav07"></div>
                <div class="mp_nav_tit"><span>个人论坛</span><i></i></div>
                <a href="javascript:;" class="m_more display"></a>
            </div>
            <div class="mp_box_bd">
            	<c:if test="${fn:length(forumList)==0}">
            		无数据
            	</c:if>
            	<c:forEach items="${forumList}" var="fl" varStatus="a">
            		<c:if test="${a.index<3}">
            			<a href="javascript:;" class="article_box" id="${fl[0]}">
		                    <div class="article_L">
		                        <div class="article_img">
		                            <img src="<%=basePath%>${fl[3]}" alt="">
		                        </div>
		                    </div>
		                    <div class="article_R">
		                        <h3>${fl[1]}</h3>
		                        <p>${fl[2]}</p>
		                    </div>
               			</a>
            		</c:if>
            		<c:if test="${a.index>2}">
            			<a href="javascript:;" class="article_box" id="${fl[0]}">
		                    <div class="article_L">
		                        <div class="article_img">
		                            <img src="<%=basePath%>${fl[3]}" alt="">
		                        </div>
		                    </div>
		                    <div class="article_R">
		                        <h3>${fl[1]}</h3>
		                        <p>${fl[2]}</p>
		                    </div>
               			</a>
            		</c:if>
            		
            	</c:forEach>
                <a href="javascript:;" class="article_more wfj01_006_click06">
                    点击查看更多
                </a>
            </div>
        </section>
        <hr class="mp_hr section_hr">
    </div>
    <!--  页面内容 end -->


	<div class="choice" style="position: absolute;top: 0px;left: 0px;background-color: #fff;width: 320px;height:568px;display: none">
		<!-- header start  -->
	    <header class="mem_header">
	        <a href="javascript:;" class="back run"></a>
	        <h1>名片展示设置</h1>
	    </header>
	    <!--  header end  -->
	    <!-- 页面内容 start  -->
	    <div class="wrap_page">
	        <section>
	            <ul class="set_mp">
	                <li>
	                    <span>证件信息</span>
	                    <i class="set_on" name="certificates"></i>
	                </li>
	                <li>
	                    <span>银行卡</span>
	                    <i class="set_on" name="bankCard"></i>
	                </li>
	                <li>
	                    <span>收货地址</span>
	                    <i class="set_on" name="goodsReceipt"></i>
	                </li>
	                <li>
	                    <span>奖励风采</span>
	                    <i class="set_on" name="rewardStyle"></i>
	                </li>
	                <li>
	                    <span>求职简历</span>
	                    <i class="set_on" name="resume"></i>
	                </li>
	                <li>
	                    <span>个人新闻</span>
	                    <i class="set_on" name="journalism"></i>
	                </li>
	                <!-- <li>
	                    <span>个人活动</span>
                    <i class="set_on"></i>
                </li> -->
                <li>
                    <span>个人论坛</span>
                    <i class="set_on" name="forum"></i>
                </li>
            </ul>
        </section>
	    </div>
	</div>
	<div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="QRCode">
    	<div style="background-color: #FFFFFF;position: relative;top: 15%;left: 20%;" id="QRCodeDiv">
    		<img id="interceptImg" src="<%=basePath%>${obj[9]}" style="width: 100%;height: 100%;">
    	</div>
   	</div>
    <script>
	    var basePath="<%=basePath%>";
		var editType="${editType}";
		var sccid="${sccid}";
		var user="${user}";
		var activity="${key_customer.account}";
		var backurl="<%=backurl%>";
		var upgrade = "00";//银行升级，关闭入口
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
            //  初始化奖励风采 图片滚动
            var mySwiper = new Swiper('.swiper-container', {
                    loop: true,
                    direction: 'horizontal',
                    slidesPerView: 4,
                    spaceBetween: 6,
                    touchMoveStopPropagation: false,
                })
                //设置点击图片全屏放大
            $(function() {
                function modalHidden($ele) {
                    $ele.removeClass('modal-in');
                    $ele.one('transitionend', function() {
                        $ele.css({
                            "display": "none"
                        });
                        $("#overlay").removeClass('active');
                    });
                }
                $(".swiper-slide").live("tap",function(e) {
                        var _index = $(".swiper-slide").index(this);
                        $(".swiper-container").clone(false).prependTo("#overlay .modal");
                        $("#overlay").addClass('active');
                        $("#overlay .modal").animate({
                            "display": "block"
                        }, 100, function() {
                            $(this).addClass('modal-in');
                        });
                        var newSwiper = new Swiper('#overlay .swiper-container', {
                            direction: 'horizontal',
                            slidesPerView: 1,
                            initialSlide: _index,
                        });

                        $("#overlay").live("tap",function(e) {
                            if (e.target.classList.contains('overlay')) {
                                $(this).unbind("tap");
                                modalHidden($(".modal"));
                                $("#overlay .modal").empty();
                            }
                        });
                    })
                    //点击跳转到该项相应a(class为m_more)标签链接
                $(".mp_box_hd").live("tap",function(even) {
                    even.stopPropagation();
                    var _link = $(this).find("a").first().attr("href"); //获取.mp_box_hd标签内a的href链接地址
                    window.location.href = "" + _link + "";
                });
             	 //设置名片 class 点击切换
	            $(".set_mp i").live("tap",function() {
	                $(this).toggleClass("set_on")
	            })
            })
        }
    </script>
</body>

</html>
