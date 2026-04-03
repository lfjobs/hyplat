<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">  
    <title>金币兑现</title> 
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <!-- 2016年7月28日 15:47:10 -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <!-- 2016年7月28日 15:47:22 --> 
	
	<link rel="stylesheet" type="text/css"
		href="<%=basePath %>css/contacts/style12.css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.11.3.js""></script>
	<script type="text/javascript">
		var khd="${khd}";
		var tocen=0;
		var flag = "${flag}";
	</script>
	
<style type="text/css">

.flip_d{
    width: 100%;
    height: 100%;
    position: absolute;
    background-color: rgba(102,102,102,0.3);
    top: 0;
}
.flip{
  width: 60%;
    background-color: #fff;
    text-align: center;
    position: fixed;
    top: 50%;
    left: 50%;
    margin-left: -30%;
    padding: 35px 0 0 0;
    border-radius: 10px;
    transform: translateY(-50%);
}
.flip p{
    font-size: 41px;
    color: #000;
    line-height: 78px;
}
.flip img{
       width: 25%;
    margin: 40px 0 70px 0;
}
.flip h3{
     font-size: 55px;
    color: #FF6507;
    border-top: 1px solid #ddd;
    width: 100%;
    padding: 50px 0;

}

/* 薛帅添加 */
@charset "utf-8";
/* CSS Document */



.flex {
    display: -webkit-box;
    /* OLD - iOS 6-, Safari 3.1-6 */
    display: -moz-box;
    /* OLD - Firefox 19- (buggy but mostly works) */
    display: -ms-flexbox;
    /* TWEENER - IE 10 */
    display: -webkit-flex;
    /* NEW - Chrome */
    display: flex;
    /* NEW, Spec - Opera 12.1, Firefox 20+ */
}

.flex_column {
    -webkit-box-orient: vertical;
    -moz-box-orient: vertical;
    -ms-box-orient: vertical;
    -webkit-flex-direction: column;
    flex-direction: column;
}

.flex_justify_center {
    -webkit-box-pack: center;
    -moz-box-pack: center;
    -ms-box-pack: center;
    -webkit-justify-content: center;
    justify-content: center;
}

.flex_align_center {
    -webkit-box-align: center;
    -moz-box-align: center;
    -ms-box-align: center;
    -webkit-align-items: center;
    align-items: center;
}

.flex_1 {
    -webkit-box-flex: 1;
    -moz-box-flex: 1;
    -ms-box-flex: 1;
    -webkit-flex: 1;
    flex: 1;
}

/*弹窗*/

.overlay {
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: -1;
    background-color: rgba(0, 0, 0, 0);
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    -webkit-justify-content: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
    display: -webkit-box;
    display: -webkit-flex;
    display: flex;
}

.active {
    z-index: 980;
}

.modal {
    width: 80%;
    margin: 0 auto;
    background: #fff;
    -webkit-transition: all 0.3s ease-in-out;
    transition: all 0.3s ease-in-out;
    opacity: 0;
    -webkit-transform: translate3d(0, 0, 0) scale(0.815);
    transform: translate3d(0, 0, 0) scale(0.815);
    -webkit-transition-property: -webkit-transform, opacity;
    transition-property: transform, opacity;
}

.modal.modal-in {
    opacity: 1;
    -webkit-transform: translate3d(0, 0, 0) scale(1);
    transform: translate3d(0, 0, 0) scale(1);
}

.flip1{
    width: 24rem;
    height: 22rem;
    background-color: #f1f1f1;
    text-align: center;
    border-radius: 5%;
    z-index: 99;
}
.flip1 .top{
    height: 5rem;
    border-bottom: 1px solid #ddd;
    padding: 0 5%;
}
.flip1 .top h3{
    font-size: 1.2rem;
    overflow:hidden;
}
.flip1 .top .head{
    width: 3.5rem;
    height: 3.5rem;
    border-radius: 50%;
    overflow: hidden;
    float: left;
    margin-right: 1.5rem;
}

.flip1 .top .head img{
    width: 100%;
    height: 100%;
}
.close1{
    width: 3rem;
    height: 3rem;
    position: absolute;
    right: 0;
    top: .5rem;
    border-left: 1px solid #ddd;
}
.close1 img{
    width: 2rem;
    height: 2rem;
    margin-top: .5rem;
}
.flip1 .con{
    height: 8rem;
    border-bottom: 1px solid #ddd;
    -webkit-justify-content: center;
    -moz-justify-content: center;
    -o-justify-content: center;
    justify-content: center;
    margin-top:2rem;
}
.flip1 .con .text h5{
    font-size: 1.8rem;
    color: #ff6600;
}
.flip1 .con .text h5 span{
    font-size: 2.8rem;
    font-weight: 500;
}
.flip1 .con .text p{
    font-size: 1.2rem;
    color: #666;
    line-height: 2;
    margin-top:1rem;
}

#ipt {
    width: 0;
    opacity: 0;
    position: absolute;
    bottom: 0;
    left: 0;
    border:0;
}
/*input {
    margin: 0;
    padding: 0;
    width: 1px;
    opacity: 0;
    height: 1px;
    border: none;
}*/
label{
    display: block;
    margin: 5% auto;
}
.ul{
    border: 1px solid #c8c8c8;
    font-size: 0;
    display: inline-block;
    position: relative;
    left: 0;
    top: 0;
}
.ul li{
    display: inline-block;
    width: 3rem;
    height: 3rem;
    font-size: 1.6rem;
    font-weight: 700;
    text-align: center;
    line-height: 3rem;
    border-left: 1px solid #e6e6e6;
    vertical-align: middle;
    overflow: hidden;
}
.ul #ipt{position:absolute;top:0;bottom:0;left:0;right:0;z-index:999;width:100%;}
.ul li:first-child {
    border-left: none;
}
/* 2016年8月2日 15:27:57  薛帅*/
.modal_TS p{font-size:2rem;line-height:1.5;}
.modal_TS img{margin: 1rem 0 1.5rem 0;}
.modal_TS h3{font-size:2rem;padding: 1.8rem 0;}
/* 2016年8月23日14:58:41  薛帅*/
.x_head_text{
	height:3.5rem;
	line-height:3.5rem;
	font-size: 1.2rem;
    text-align: left;
    float: left;
    margin-left: 1rem;
}
	</style>	
</head>

<body>
	<div class="wfj01_016">
    	
    	<s:if test="khd==0">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li class="wfj_return"><a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid}&flag=${flag}&khd=0" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" /></a></li>
            	<li>金币兑现</li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	</s:if>
        
        <div class="wfj01_016_content">
        	<div class="wfj01_016_hidden">
            	<div class="wfj01_016_topimg">
                    <ul>
						<li>
							<div class="wfj01_016_topdiv" style="border-radius:50%;overflow:hidden;width:29%;margin:0 auto;">
								<c:choose>
									<c:when test="${staff.headimage == null || staff.headimage == ''}">
										<img style="display:block;width:100%;height:100%;" src="<%=basePath %>images/WFJClient/VipCenter/headimage.png" />										
									</c:when>
									<c:otherwise>
										<img style="display:block;width:100%;height:100%;" src="<%=basePath %>${staff.headimage }" />									
									</c:otherwise>								
								</c:choose>								
							</div>
						</li>
						<li><span>${staff.staffName}</span></li>
                        <%-- <li>${pp.goodsName }</li> --%>
                    </ul>
                </div>
                
                <div class="wfj01_016_congold">
                    <table>
                        <tr>
                            <td rowspan="3" width="35%" align="center"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_01.png" /></td>
                            <td><font id="wfjScore"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }" groupingUsed="true"/></font></td>
                        </tr>
                        <tr>
                            <td>金币余额</td>
                        </tr>
                        <tr>
                            <td>可兑换<fmt:formatNumber value="${jifen.wfjJifenScore/100}" type="currency" groupingUsed="true"/></td>
                        </tr>
                    </table>
                </div>
                
               <div class="wfj01_016_input">
                	<table>
                    	<tr>
                        	<td width="30%">兑换金额：</td>
                        	<td width="70%">
                        	<input type="text" maxlength="10" id="money" value="输入兑换现金数" onfocus="if(this.value=='输入兑换现金数'){this.value='';}" onblur="if(this.value==''){this.value='输入兑换现金数';}"/></td>
                        </tr>
                    	<tr>
                        	<td>可兑换现金：</td>
                        	<td><span id="jinbi"></span></td>
                        </tr>
                    </table>
                </div>
                <p class="font" style="line-height:40px;color:#F75130;">
                <img style="width:30px;vertical-align:middle;margin:0 4px;" src="<%=basePath %>images/ea/finance/BenDis/jinbi_03.png">满10000金币方可提现，每笔银联收取2元手续费 (1元=100金币)</p>
                
                <div class="wfj01_016_commit">
               	<div id="dh" data-modal="pw">确认兑换</div>     		         	
                </div>
            </div>
        </div>
       <!--  图片 bounced_gold1.png -->
        <div style="padding-top: 45px; display: none;" id="picture1">
        <div class="flip_d">
            <div class="flip modal_TS">
                <p>您金币不够10000个</p>
                <p>请尽快集齐金币再来兑换吧</p>
                <img src="<%=basePath %>images/ea/finance/BenDis/bounced_gold1.png">
                <h3 id="out1">我知道了</h3>
            </div>
        </div>
    </div>
    </div>
    
    <!--  图片 bounced_gold2.png --> 
    <div style="display: none; " id="picture2">
        <div class="flip_d">
            <div class="flip modal_TS">
                <p>您所持有的金币</p>
                <p>不足以兑换<span id="xjs"></span>元现金</p>
                <img src="<%=basePath %>images/ea/finance/BenDis/bounced_gold2.png">
                <h3 id="out2">我知道了</h3>
            </div>
        </div>
    </div>
    
    
    <!--   金币弹框 -->
   <div class="overlay" id="overlay">
        <div class="flip1 modal pw" id="flip1">
            <div class="top">
                <h3>
                    <div class="head"> <img id="pic" src="<%=basePath %>images/ea/finance/BenDis/wfj_gold.png"></div>
                    	<div class="x_head_text">请输入支付密码</div>
                </h3>
                <div class="close1 modal_btn"><img  src="<%=basePath %>images/ea/finance/BenDis/x.png"></div>
            </div>
            <div class="con">
                <div class="text">
                    <h5><span id="gold"></span></h5>
                    <p>可兑换现金</p>
                </div>
            </div>
            <div class="pwd-box">
                <label for="ipt" id="pwd-box">
                    <ul class="ul">
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
                        <li></li>
						<input type="tel" id="ipt" maxlength="6">
                    </ul>
			
                </label>

                
            </div>
        </div>
    </div>
    
    
    
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>

   
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var user="${user}";
    	var sccid="${sccid}";
    	$(document).ready(function(e) {
			$("body").css("height",$(window).height()) ;
         	
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px");
            $(".wfj_top").find("li").find("img").each(function(){
            	var ihei=imgPosition($(".wfj_top").height(),$(this).height());
            	/* $(this).attr("style",$(this).attr("style")+";margin-top:"+ihei+"px"); */
            	$(this).attr("style",$(this).attr("style")+";");
            });
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			if($(window).width()>$(window).height()){
				$(".wfj01_016").css("width","70%");
				$(".wfj01_016_content").attr("style","width:100%; height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:hidden;");
				$(".wfj01_016_hidden").attr("style","width:"+parseInt($(".wfj01_016_content").width()+17)+"px; height:"+parseInt($(window).height()-

				$(".wfj_top").height())+"px;overflow:auto;");
			}else{
				$(".wfj01_016").css("width","100%");
			}
			
            $(".wfj01_016_topimg").attr("style","padding:"+$(window).height()*0.035+"px 0;");
            $(".wfj01_016_topimg").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;line-height:"+$(window).height()*0.035+"px;");
            $(".wfj01_016_topimg").find("li").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
            $(".wfj01_016_congold").attr("style"," margin:"+$(window).height()*0.015+"px auto; padding:"+$(window).height()*0.02+"px 0;");
            $(".wfj01_016_congold").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj01_016_input").find("td").attr("style","font-size:"+$(window).height()*0.02+"px; padding-left:"+$(window).height()*0.01+"px; height:"+$(window).height()*0.05+"px;  line-height:"+$(window).height()*0.05+"px; ");
            $(".wfj01_016_input").find("td").find("input").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.05+"px;padding-left:"+$(window).height()*0.01+"px;");
			
            $(".wfj01_016_commit").attr("style"," margin:"+$(window).height()*0.025+"px auto;");
            $(".wfj01_016_commit").find("div").attr("style"," height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; border-radius:"+$(window).height()*0.005+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj01_016_topdiv").css({"height":$(".wfj01_016_topdiv").width()+"px","overflow":"hidden"})		
			$(".font").css({"font-size":$(window).height()*0.017+"px","padding-left":$(window).height()*0.01+"px"})
			$(".font img").css({"width":$(window).height()*0.017+"px"})
			
			$(".flip p").css("font-size",+$(window).height()*0.03+"px");
			$(".modal_TS h3").css("font-size",+$(window).height()*0.04+"px");
			
			/* $(".wfj_return").click(function(){
				open("wfj01_015.html","_self");
			}); */
			
			$("#money").keyup(function(){
				var money=$("#money").val()-2;
				$("#jinbi").text("￥"+formatCurrency(money));
			})
			
			//点击，消失
			$("#out1").click(function(){				
				$("#picture1").css("display","none");
			});
			
			//点击，消失
			$("#out2").click(function(){				
				$("#picture2").css("display","none");
			});
			
			
			
			$("#dh").click(function(){
				var monboo=true;
				if($("#money").val()==null){
					return;
				}
				var url=basePath+"/ea/jinbi/sajax_getUserPurview.jspa?user="+user;
				$.ajax({
					url : url,
					type : "get",
					async : false,
					dataType : "json",
					success : function (data) {
						var member = eval("(" + data + ")");
						var purview=member.purview;
						 if(purview<=0){
							alert("金币账户冻结，不能体现！！！");
							monboo=false;
						}
				     },
				});
				var jinbinum= parseFloat("${jifen.wfjJifenScore }")/100;
				if(monboo&&jinbinum<100){
				 	 // alert("您金币不够10000个，请尽快集齐金币再来兑现吧(*^__^*)")		 
			 	 	 $("#picture1").css("display","block");
			 	 	 monboo=false;
				}
				if(monboo&&isNaN($("#money").val())){
					alert("请输入有效数字");
					monboo=false;
				}
				var money=parseFloat($("#money").val())-2;
				if(monboo&&parseFloat($("#money").val())<100){
				 	alert("您的提现金额不能少于100元");
				 	monboo=false;
				}
				if(monboo&&${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore}<(money==null?0:money*100)){
					$("#xjs").text(money);
					$("#picture2").css("display","block"); 
				 	$("#dh").show();
				 	monboo=false;
				}
				if(monboo){
					$("#dh").hide();
				 	var url=basePath+"/ea/jinbi/sajax_getUserBank.jspa?user="+user;
					$.ajax({
						url : url,
						type : "get",
						async : false,
						dataType : "json",
						success : function (data) {
							var member = eval("(" + data + ")");
							var count=member.count;
							var wfj_staffid=member.wfj_staffid;
							if(count<=0){
								alert("请去名片完善银行卡信息！");
							}else{
								//获取兑换金币数
								var gold = $("#jinbi").text();
								$("#gold").text(gold);
								//显示弹框
								var $that = $('#dh');
								$('#overlay').addClass('active');
					            var $whichModal = $('.' +  $('#dh').data('modal'));
					            $whichModal.css({"display":"block"});
					            $whichModal.animate({"display": "block"}, 100, function() {$(".modal").addClass('modal-in');});
								$("#ipt").focus();
							}
					     }
					});
				}
			});
			
			//弹框密码判断
			$("#ipt").focus(function(){$(this).css("left","-9999px");})
			$("#ipt").blur(function(){
				$(this).css("left",0);
			})
			$("#ipt").on("input", function(e) {
                   var numLen = 6;
                   var pw = $('#ipt').val();//获取全部值
                   var list = $('.ul li');//填充标签
                   for (var i = 0; i < numLen; i++) {
                       if (pw[i]) {
                           $(list[i]).text('·');
                       } else {
                           $(list[i]).text('');
                       }
                   }
				   if (pw.length == 6) {
				   	if(tocen==1){
				   		return;
				   	}
				   	if(tocen==1){
				   		return false;
				   	}
				   	tocen=1;
                    $("#ipt").blur();
                	var code=$("#ipt").val();
                    var uut = basePath + "/ea/jinbi/sajax_toSearchCode.jspa?password="+code+"&user="+user;
                    $.ajax({
						url : encodeURI(uut),
						type : "get",
						async : false,
						dataType : "json",
						success : function (data) {
							var member = eval("(" + data + ")");
							var str=member.search.type;
							//获取用户头像   var gold = $("#jinbi").text();$("#gold").text(gold);
							var pic=member.search.headImage;
							if( pic == null){
								var path=basePath+"images/ea/finance/BenDis/头像@2x.png";
							}else{
								var path=basePath+pic;
							}							
							$("#pic").attr('src',path);
							if(str==1){
								var pw = $('#ipt').val();
			                    var list = $('.ul li');
			                    for (var i = 0; i < 6; i++) {
			                        if (pw[i]) {
			                            $(list[i]).text('');
			                        }
			                    }
			                    $("#ipt").val('');
								alert("没有设置支付密码！");
								hideModal();//隐藏弹框
								$("#dh").show();
							}else if(str==2){
								var pw = $('#ipt').val();
			                    var list = $('.ul li');
			                    for (var i = 0; i < 6; i++) {
			                        if (pw[i]) {
			                            $(list[i]).text('');
			                        } 
			                    }
			                    $('#ipt').val('');
								alert("支付密码输入错误！");
								hideModal();//隐藏弹框
								$("#dh").show();
							}else{
								$("#dh").hide();
								if(basePath=="http://test.impf2010.com"){
								   alert("请到微分金里进行兑现！");	
								}else{                         
									var url=basePath+"/ea/jinbi/sajax_tixian.jspa?user="+user+"&sccid="+sccid+"&money="+$("#money").val();
									$.ajax({
										url : url,
										type : "get",
										async : false,
										dataType : "json",
										success : function (data) {
											var member = eval("(" + data + ")");
											var str=member.str;						
											if(str=="提交成功"){
												alert("如果兑现成功,金额将在五个工作日内打到您的账户,请耐心等待");
												open(basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+user+"&sccid="+sccid,"_self");
											}
										},
										error:function(data){
											alert("获取数据失败");
										}
									});
								}												
							}
						}
					}); 
                    var pw = $('#ipt').val();
                    var list = $('.ul li');
                    for (var i = 0; i < 6; i++) {
                        if (pw[i]) {
                            $(list[i]).text('');
                        } 
                    }
                    $('#ipt').val('');
                    hideModal();//隐藏弹框                  
                }
			});
    	});
		  
 
 //隐藏弹框
 function hideModal(){
	 $("#overlay").removeClass("active");
     $(".modal").removeClass("modal-in");
}

/**  
 * 将数值四舍五入(保留2位小数)后格式化成金额形式  
 *  
 * @param num 数值(Number或者String)  
 * @return 金额格式的字符串,如'1,234,567.45'  
 * @type String  
 */
function formatCurrency(num) {    
    num = num.toString().replace(/\$|\,/g,'');    
    if(isNaN(num))    
    num = "0";    
    sign = (num == (num = Math.abs(num)));    
    num = Math.floor(num*100+0.50000000001);    
    cents = num%100;    
    num = Math.floor(num/100).toString();    
    if(cents<10)    
    cents = "0" + cents;    
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)    
    num = num.substring(0,num.length-(4*i+3))+','+    
    num.substring(num.length-(4*i+3));    
    return (((sign)?'':'-') + num + '.' + cents);    
}    
     
function imgPosition(phi,thi){
	return (phi-thi)/2;
}	

	</script>
	
	<script>
        
        $(function() {
            //弹窗 strat
            var $overlay = $('#overlay');
            var basePath="<%=basePath%>";
            function modalHidden($ele) {
                $ele.removeClass('modal-in');
                $ele.one('transitionend', function() {
                    $ele.css({
                        "display": "none"
                    });
                    $overlay.removeClass('active');
                });
            }
            //点击弹窗内按钮、链接关闭弹窗
            $('.modal_btn').click(function(e) {
                e.stopPropagation();
                $(this).parents(".modal").removeClass('modal-in').one('transitionend', function() {
                    $(this).css({
                        display: "none"
                    });
                    $overlay.removeClass('active');
                    var pw = $('#ipt').val();
                    var list = $('.ul li');
                    for (var i = 0; i < 6; i++) {
                        if (pw[i]) {
                            $(list[i]).text('');
                        }
                    }
                    $('#ipt').val('');
                });
                $("#dh").show();
            });
            
            //点击遮罩层，关闭弹窗
            $overlay.click(function(e) {
                    if (e.target.classList.contains('overlay')) {
                        $(this).find(".modal-in").removeClass("modal-in").one('transitionend', function() {
                            $(this).css({
                                display: "none"
                            });
                            $overlay.removeClass('active');
                            
                            var pw = $('#ipt').val();
                            var list = $('.ul li');
                            for (var i = 0; i < 6; i++) {
                                if (pw[i]) {
                                    $(list[i]).text('');
                                } 
                            }
                            $('#ipt').val('');
                        })
                    }
                })
                //弹窗 end
              
        })
			
    </script>	
</body>
</html>
