<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>



<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<title>加入平台</title>
<link href="<%=basePath%>/css/WFJClient/platform/style11.css"
	rel="stylesheet" type="text/css" />
<%--<link rel="stylesheet" type="text/css" href="<%=basePath%>css/contacts/investment/fadongji.css">--%>


<link href="<%=basePath%>css/contacts/Restaurant/jqModal_blue.css"
	rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/WFJClient/platform/bootstrap.css">
<link type="text/css" rel="stylesheet"
	href="<%=basePath%>/css/WFJClient/platform/style.css">
	<script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/platform/bootstrap.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/WFJClient/platform/toucher.js"></script>


<%-- <script type="text/javascript" src="<%=basePath%>js/fontscroll.js"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script> --%>
</head>
<style>
.wfj11_015_buy_commit1 {
    width: 100%;
    /* position: absolute; */
    bottom: 0;
    z-index: 10;
}

</style>
<script type="text/javascript">
$(function(){

    $(function(){
        var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (!isWeixin) {
		/*	if(ua.indexOf("browser")!=-1){
			 $(".wechat").hide();

			 }else{
			 $(".wechat").show();

			 }*/
        }


    });
	
});

</script>
<body>
<div class="loading" style="display:none;">
	<img src="<%=basePath%>images/WFJClient/Newjspim/loading.gif"/>
	<p><span>加载中...</span></p>
</div>
	<div class="content-b1">
	<input type="hidden" id="moneyp" value="${param.money}">
		<header>
			<ul>
				<li style="width: 10%;"><a id="returnClick"><img
						src="<%=basePath%>/images/WFJClient/Platform/left_jt.png">
				</a></li>
				<li style="width: 80%;">加入平台</li>
				<li style="width: 10%;"></li>
				<div class="clearfix"></div>
			</ul>
		</header>
		<div class="content">
			<div class="pub_banner">
				<img width="100%"
					src="<%=basePath%>/images/WFJClient/Platform/pla-banner.png">
			</div>
			<ul class="pla_con">
				<img src="<%=basePath%>/images/WFJClient/Platform/pingtaiyoushi.png"
					width="100%">
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/1.png">
					</div>
					<div>
						<h2>低成本</h2>
						<p>免费加入会员，100元即可成为代理商</p>
					</div>
					<div class="clearfix"></div></li>
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/2.png">
					</div>
					<div>
						<h2>会员种类多</h2>
						<p>平台拥有多种会员类型，面向公司、个人不同的需求</p>
					</div>
					<div class="clearfix"></div></li>
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/3.png">
					</div>
					<div>
						<h2>管理模块化</h2>
						<p>较其它管理系统，我们将企业管理分为人事、财务、办公室、生产、营销模块</p>
					</div>
					<div class="clearfix"></div></li>
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/4.png">
					</div>
					<div>
						<h2>智能吸粉系统</h2>
						<p>各种方式推广会员名片，增加会员粉丝获取“金粉丝”经济</p>
					</div>
					<div class="clearfix"></div></li>
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/5.png">
					</div>
					<div>
						<h2>轻松做老板</h2>
						<p>底下会员购买产品产生交易，获取丰厚佣金，在家轻松做老板</p>
					</div>
					<div class="clearfix"></div></li>
				<li>
					<div>
						<img src="<%=basePath%>/images/WFJClient/Platform/6.png">
					</div>
					<div>
						<h2>扩大公司收益</h2>
						<p>公司底下加入更多会员，会员相互宣传，购买企业产品，产生更多利益</p>
					</div>
					<div class="clearfix"></div></li>
			</ul>
			
						<input type="hidden" name="ppid" value="${ppid}">
						
			<div class="pub_banner">
				<img width="100%"
					src="<%=basePath%>/images/WFJClient/Platform/pla-banner2.png">
			</div>
			<div class="pla_mil" align="center">
				<img src="<%=basePath%>/images/WFJClient/Platform/pla1.png"
					width="60%">
			<form>
                <div><span>电话</span><input type="text" name="cdl.companyPhone" id="companyPhone" onblur="phone()" style="outline: none;" ></div>
                <hr style="border-top:1px solid #ddd;width: 90%;margin: 0.5rem 0;">
                <div><span>公司名称</span><input type="text" name="company.companyName" id="companyName" style="outline: none;"></div>
                <hr style="border-top:1px solid #ddd;width: 90%;margin: 0.5rem 0;">
            </form>
			
			<ul class="way">
				<li class="active  none" id="quane" onclick="quan()">全额付款
					<div></div>
				</li>
				<li id="fenqi" class="none">分期付款
					<div></div>
				</li>
			</ul>
			<ul class="aging aging2">
				<!--加aging2是四个的,去除是五个-->
				<%
					int number = 1;
				%>
				<c:forEach items="${list}" var="a">
				<li class="march" onclick="dianji(this)">
				<input type="hidden" id="money" value="${a[2]}">
					<!--march是已经读到的状态--> 
					<img src="<%=basePath%>/images/WFJClient/Platform/aging<%=number%>_.png"
					alt="">
					<div class="txt">
						<h5>${a[0]}</h5>
						<p>
							&yen;<span>${a[2]}元</span>
						</p>
					</div></li>
				<span class="n 
				n<%=number%>"><img
					src="<%=basePath%>/images/WFJClient/Platform/n.png" alt="">
				</span>
				<%
						number++;
					%>
				</c:forEach>
			</ul>
			<div class="pla_pay">
				<div class="none">
					<img src="<%=basePath%>/images/WFJClient/Platform/zhifubao.png"><span>支付方式</span>
				</div>
		<div class="wfj11_015_buy_commit" style="display:block;padding: 0;">
        	<div class="wfj11_015_need" style="padding:0 15px;">
            	<div class="wfj11_015_width" style="padding: 0;overflow:hidden;">
                	<ul>
                    	<li class="left">需支付：</li>
                    	<li class="right"><span >￥<span id="moneys">${param.money}</span>元</span><input type="hidden" id="morre" name="morre" value="${param.money}"/></li>
                    </ul>
                </div>
            </div>
           <div class="wfj11_015_allbay">
				<div class="wfj11_015_width" style="padding: 0;">
					<table>
						
						<tr class="wfj11_015_choice none">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png" />
							</td>
							<td  class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
								width="24" height="24" name="1" />
							</td>
						</tr>
						<tr class="wfj11_015_choice none" id="weixin" class="wechat">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="3" />
							</td>
						</tr>
						<tr class="wfj11_015_choice none">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="4" />
							</td>
						</tr>
						<tr class="wfj11_015_choice none">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/moneybox.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="5" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><div id="paycommit"
									onclick="submint()">确认支付</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			</div>
			<!-- </form> -->
		</div>
		<input type="hidden" id="addre" value="${param.addre}"> 
	</div>



	<script>
	var basePath = "<%=basePath%>";
	var zffs = 1;//选择的支付方式   默认为支付宝
	var token=0;
	var ddid;
	var ppid = "${ppid}";
	var goodsname="${param.goodsname}";
	var model = "${param.model}"
	var content="${content}";
    var staffID="${staffid}";
    var platfromid = '${platfromid}';

		function phone(){
		var phone = $("#companyPhone").val(); 

		 var PhoneReg = /^([0\+]\d{1,4}-)?(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/; ; //手机正则
		if(phone == ''){
			alert('手机还没填呢...'); 
			$("#companyPhone").val("");
			return
		}else if(!PhoneReg.test(phone)){
			alert('手机格式错咯...'); 
			$("#companyPhone").val("");
			return
		} 

		}
    $(document).ready(function(){
    	 $("#returnClick").click(function() {history.go(-1)});
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
        $(".content2").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");

			$(".wfj11_015_need").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+"1;");
			$(".wfj11_015_need").find("li").attr("style","font-size:"+$(window).height()*0.03+"px;color:#000;");
			$(".wfj11_015_need").find("li").find("span").attr("style","font-size:"+$(window).height()*0.03+"px;color:#F74C31;");
			$(".wfj11_015_allbay").find("td").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.09+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_015_allbay").find("td").eq(0).css("height",$(window).height()*0.05+"px")
			$(".wfj11_015_allbay").find("tr").eq(5).css("height",$(window).height()*0.1+"px")
			$(".wfj11_015_allbay").find("td").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto");
			$(".wfj11_015_allbay").find("td").find(".all_pay").attr("style","height:"+$(window).height()*0.04+"px;");
			$(".wfj11_015_allbay").find("td").find("#paycommit").attr("style"," width:50%; background-color:#F74C31; color:#FFF; cursor:pointer; border-radius:"+$(window).height()*0.006+"px; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;padding:0;");
			$(".wfj11_015_consignee1").css({"width":$(window).width()*0.07+"px","left":$(window).height()*0.02+"px"})
		

        $(".pla_buy").click(function(){
            $(".content-b1").css("display","none");
            $(".content-b2").show();
        });
        $(".content-b2 header ul li:nth-child(1) img").click(function(){
            $(".content-b2").css("display","none");
            $(".content-b1").show();
        });
		//切换支付方式
		$(".wfj11_015_choice").click(function(){
				$(".wfj11_015_choice").find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_02.png");
				$(this).find(".second").find("img").attr("src",basePath+"/images/WFJClient/Newjspim/choice_01.png");
				zffs=$(this).find(".second").find("img").attr("name");
		});
		
		

        $(".cho_mil li").click(function(){
            var cho=$(this).find("h3").text();
            $(".pla_buy p").text(cho);
            $(".content-b2").css("display","none");
            $(".content-b1").show();
            $(this).attr("style","background: url("+basePath+"/images/WFJClient/Platform/gou.png') 14rem 18px no-repeat;background-size: 1rem;")
														.siblings().attr(
																"style", "");
											});
							/*2016.9.18*/
							$(".way li").click(
									function() {
										$(this).addClass("active").siblings()
												.removeClass("active");
									});
							$("#quane").click(function() {
								$(".aging").hide();
							});
							$("#fenqi").click(function() {
								$(".aging").show();
							});
						});
					
					

	</script>
	<script type="text/javascript" src="<%=basePath%>js/WFJClient/platform/platformD.js"></script>
	<script>
		// var num1=num2=num3=0
		window.onload = window.onresize = function() {
			//含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
			//获取窗口的尺寸
			var clientWidth = document.documentElement.clientWidth;
			//console.log(clientWidth);
			//通过屏幕宽度去设置不同的后台根字体的大小
			//document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
			document.getElementsByTagName('html')[0].style.fontSize = clientWidth
					/ 640 * 40 + 'px'
		}
	</script>
</body>
</html>