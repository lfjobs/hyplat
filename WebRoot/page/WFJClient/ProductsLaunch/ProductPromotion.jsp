<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html style="font-size: 62.5%;background: #fff;">
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>添加促销赠送产品</title>
    
    <link rel="stylesheet" href="<%=basePath %>css/ea/production/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/ea/production/reset.css">
    <link rel="stylesheet" href="<%=basePath %>css/ea/production/shopping.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/ea/production/my_css.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/ea/production/add-grift.css"/>
    <script src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath %>js/bootstrap.js"></script>
    <script src="<%=basePath %>js/ea/marketing/wfjeshop/productPromotion.js"></script>
</head>
<body>
<div class="top-sp">
    <ul>
        <li class="arrar">
            <div>
                <img src="<%=basePath %>images/ea/bids/jiantou-hei_03.png" alt="">
            </div>
        </li>
        <li><h5 class="shangpin">设置促销产品</h5></li>
    </ul>
</div>
<div class="main_auto">
    <div class="tou_head tou_head2">
        <img width="100%" src="<%=basePath %>images/ea/production/grft-banner.jpg">
    </div>
    <div class="biao_subproject_lis biao_subproject_lis2" >
        <div class="subproject_lis_left pull-left pull-left2">
            <img class="pullImg" src="<%=basePath %>${map.product[1]}" alt=""/>
        </div>
			<div class="subproject_lis_right subproject_lis_right2 pull-right">
				<p class="xqing xqing2">${map.product[2] }</p>
				<div class="bjia bjia2">
					<span class="pull-left">&yen;<b class="price">${map.product[3] }</b></span>
				</div>
				<div class="buy buy2">
					<div>
						<input type="hidden" id="ppId" class="ppId"
							name="productPackaging.ppID" value="${map.product[0]}" /> <input
							type="hidden" id="companyId" class="CompanyId"
							value="${map.product[5]}" /> <input type="hidden" id="ccompanyId"
							class="CCompanyId" value="${map.product[6]}" /> <input
							type="hidden" id="goodsid" class="goodsid"
							value="${map.product[7]}" /> <span class="companyName">${map.product[4] }</span>
						<span class="glyphicon glyphicon-play"> </span>
					</div>
				</div>
			</div>
		</div>
    <div class="add_subproject add_kuang2">
        <div class="add_subproject_main add_subproject_main2" onclick="giftProducts()">
            <div class="main_left pull-left text-center">
                <img src="<%=basePath %>images/ea/bids/A30E.tmp.jpg"/>
            </div>
            <div class="main_right main_right2 pull-left">
                <h4>添加促销赠送产品</h4>
            </div>
        </div>
    </div>
<div class=""> <!-- hidden -->   
    <div class="class_hidden">
	 	<input id="user" type="hidden" value="${user}"/> 
    </div>
    <div class="clearfix"></div>
	    <c:forEach var="m" items="${map.list }" >
			    	<div class="biao_subproject_lis biao_subproject_lis2 zengjia" onclick="Description(this);" >
				        <div class="subproject_lis_left pull-left pull-left2">
				            <img class="pullImg" src="<%=basePath %>${m[1]}" alt=""/>
				        </div>
			        	<div class="subproject_lis_right subproject_lis_right2 pull-right">
			            	<p class="xqing xqing2">${m[2] }</p>
			            	<img src="<%=basePath %>images/ea/production/iconfont-zengjia(1).png" class="del" onclick="del(this)" alt="" style="float: right;" />
				            <div class="bjia bjia2">
				                <span class="pull-left">&yen;<b class="price">${m[3] }</b></span>
				            </div>
				            <div class="buy buy2" ">
				                <div>
				                	<input type="hidden" class="PtppId" value="${m[0]}"/>
						       		<input type="hidden" class="PCompanyId"  value="${m[5]}"/>
						       		<input type="hidden" class="CCompanyId"  value="${m[6]}"/>
						       		<input type="hidden" class="goodsid"  value="${m[7]}"/>	
						       		<input type="hidden" class="comboGenre"  value="${m[8]}"/>
				                    <span class="companyName">${m[4] }</span>
				                    <span class="glyphicon glyphicon-play">
				                    </span>
				                </div>
				            </div>
			        	</div>
	    			</div>
	  	</c:forEach>
	  	<hr style="border-top: 10px solid #ddd; margin-bottom: 0px"/>
	  	<c:forEach items="${list}" var="l">
	  		<div class="biao_subproject_lis biao_subproject_lis2 zengjia" onclick="Description(this);" >
				        <div class="subproject_lis_left pull-left pull-left2">
				            <img class="pullImg" src="<%=basePath %>${l[1]}" alt=""/>
				        </div>
			        	<div class="subproject_lis_right subproject_lis_right2 pull-right">
			            	<p class="xqing xqing2">${l[2] }</p>
			            	<img src="<%=basePath %>images/ea/production/iconfont-zengjia(1).png" class="del" onclick="del(this)" alt="" style="float: right;" />
			            	<div class="clearfix"></div>
				            <div class="bjia bjia2">
				                <span class="pull-left">&yen;<b class="price">${l[3] }</b></span>
				            </div>
				            <div class="buy buy2" ">
				                <div>
				                	<input type="hidden" class="PtppId"  value="${l[0]}"/>
				                	<input type="hidden" class="PCompanyId"  value="${l[5]}"/>
						       		<input type="hidden" class="CCompanyId"  value="${l[6]}"/>
						       		<input type="hidden" class="goodsid"  value="${l[7]}"/>	
				                    <span class="companyName">${l[4] }</span>
				                    <span class="glyphicon glyphicon-play">
				                    </span>
				                </div>
				            </div>
			        	</div>
	    			</div>		
	  	</c:forEach>
</div>
 <div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
<div class="main_auto">
    <div class="add_kuang3" align="center">
        <h3>添加促销产品使用说明</h3>
        <p>您可将地球数字商城的产品（本公司和</p>
        <p>其他公司的均可）添加至此，将作为促销</p>
        <p>赠送产品，客户购买后，您需要支付产品费用。</p>
    </div>
    <div class="but_tijiao but_tijiao2 text-center" id="submit">提交保存</div>
</div>
<script>
var basePath = "<%=basePath %>";
var list;
var user;//用户
var ppid;//主产品id
var companyId;//主产品公司id
var salesPromotionId = "";//促销品id
var salesPromotionCompanyId = "";//促销品公司id
var companyname;//公司名称
var companyid;//产品公司id
var ccompanyId;//往来公司id
var goodsid;//物品id
var comboGenre=1;//套餐类型
    $(function(){
        $(".main_auto").css("height",$(window).height()-$(".top-sp").outerHeight()+"px");
        $(".subproject_lis_right").css("height",$(window).height()*0.17+"px");
        $(".pull-left2").css("height",$(window).height()*0.17+"px");
        $(".add_kuang").css("height",$(".biao_subproject_lis").height()+"px");
//        跳转页面
        $(".add_subproject").click(function(){
            window.open("#","_self");
        });
        $(".arrar").click(function(){
        	user = $("#user").val();
        	ppid=$("#ppId").val();
			goodsid= $("#goodsid").val();
			companyId=$("#companyId").val();
			var params=new Array();
			params.push("user="+user);
			params.push("&companyId="+companyId);
			params.push("&ppId="+ppid);
			params.push("&goodsId="+goodsid);
		    window.location.href=basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?"+params.join("");
        });
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
</script>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</body>
</html>