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
    <title>金币充值</title>  
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style>
	#head_img {
	    position: absolute;
	    top: 50%;
	    transform: translateY(-50%);
	    -webkit-transform: translateY(-50%);
	    -moz-transform: translateY(-50%);
	    -ms-transform: translateY(-50%);
	    -o-transform: translateY(-50%);
 	}
	#head_img2 {
	    position: absolute;
	    top: 50%;
	    transform: translateY(-50%);
	    -webkit-transform: translateY(-50%);
	    -moz-transform: translateY(-50%);
	    -ms-transform: translateY(-50%);
	    -o-transform: translateY(-50%);
	}
	#alert{
		width: 100%;
	    height: 100%;
	    background: rgba(0, 0, 0, 0.2);
	    top: 0;
	    position: absolute;
	    display: none;
	}
	.jqmOverlay{
	    height: 100%;
    width: 100%;
    position: fixed;
    left: 0px;
    top: 0px;
    z-index: 1 !important;
    opacity: 0.2;
    display: none;
	}
	</style>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link rel="stylesheet" type="text/css"
		href="<%=basePath %>css/contacts/style12.css" />
	<%-- <link rel="stylesheet" type="text/css"
		href="<%=basePath%>js/jqModal/css/jqModal_blue.css" /> --%>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var khd="${khd}";
    	var user="${user}";
    	var sccid="${sccid}";
    	var jum="";
		var morre="";
		var zffs="1";
		var money=0;
		var staffid="";
		var flag = "${flag}";
	</script>
	
<script type="text/javascript">
$(function(){
	var ua = navigator.userAgent.toLowerCase();
	var isWeixin = ua.indexOf('micromessenger') != -1;
	if (!isWeixin) {
	   if(ua.indexOf("browser")!=-1||ua.indexOf("qq")!=-1||ua.indexOf("safari")!=-1){
		$(".wechat").hide();
		
	    }else{
		   $(".wechat").show();
		
	   }
	}
	
	
});

</script>
</head>

<body>
<div id="alert"></div>
	<div class="wfj01_016">
    	
    	<s:if test="khd==0">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li class="wfj_return"><a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid}&flag=${flag}&khd=0" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" id="head_img"/></a></li>
            	<li>金币充值</li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	</s:if>
        
        <div class="wfj01_016_content">
        	<div class="wfj01_016_hidden">
            	<div class="wfj01_016_topimg">
                    <ul>
						<li><div class="wfj01_016_topdiv"style="border-radius:50%;overflow:hidden;width:35%;margin:0 auto;">
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
                            <td><font><fmt:formatNumber value="${jifen.wfjJifenScore }" groupingUsed="true"/></font></td>
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
                        	<td width="30%">充值数量：</td>
                        	<td width="70%"><input type="text" id="money" value="输入金币数" onfocus="if(this.value=='输入金币数'){this.value='';}"  onblur="if(this.value==''){this.value='输入金币数';}"  /></td>
                        </tr>
                    	<tr>
                        	<td>应付金额：</td>
                        	<td><span id="jinbi"></span></td>
                        </tr>
                    </table>
                </div>
                <p class="font" style="line-height:40px;color:#F75130;"><img style="width:30px;vertical-align:middle;margin:0 4px;" src="<%=basePath %>images/ea/finance/BenDis/jinbi_03.png">100金币=1元</p>
                <div class="wfj01_016_commit">
               		<div id="dh">确认充值</div>
                </div>
            </div>
        </div>
        <div class="wfj12_014_hidden_buy" style="display:none;">
			<table id="pays" width="100%">
				<tr>
					<td width="50%" style="padding-left:4%;"><span>需支付：</span>
					</td>
					<td align="right" style="padding-right:4%;" width="50%"><span
						id="money2" style="color:#F74C31;"></span>
					</td>
				</tr>
			</table>
			<div class="wfj12_014_buy_width">
				<table>
					<tr>
						<td colspan="2">选择支付方式</td>
					</tr>
					<tr>
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_01.png" />
						</td>
						<td align="right" class="wfj12_014_choice"><img width="24" name="1"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_01.png" />
						</td>
					</tr>
					 <%--<tr>--%>
						<%--<td class="wfj12_014_pay"><img--%>
							<%--src="<%=basePath %>images/ea/finance/BenDis/all_pay_02.png" />--%>
						<%--</td>--%>
						<%--<td align="right" class="wfj12_014_choice"><img width="24" name="2"--%>
							<%--height="24"--%>
							<%--src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />--%>
						<%--</td>--%>
					<%--</tr>--%>
					
					 <tr>
						<td class="wfj12_014_pay"><img
							src="<%=basePath %>images/ea/finance/BenDis/all_pay_03.png" />
						</td>
						<td align="right" class="wfj12_014_choice"><img width="24" name="3"
							height="24"
							src="<%=basePath %>images/ea/finance/BenDis/choice_02.png" />
						</td>
					</tr>
					
					
				

					
					
				</table>
			</div>
			 
			<div class="wfj12_014_bottom_commit">
				<div id="paycommit" onclick="zf()">确认支付</div>
			</div>
		</div>
    </div>
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
	<form name="formsutm" id="formsutm" method="post">
		<s:hidden name="journalNum" id="journalNum"></s:hidden>
		<s:hidden name="baseUrl" id="baseUrl"></s:hidden>
		<s:hidden name="morre" id="morre"></s:hidden>
		<s:hidden name="staffid" id="staffid"></s:hidden>
		<input type="submit" style="display: none" name="submit" id="submit" />
	</form>
	<script type="text/javascript">
    	$(document).ready(function(e) {
			$("body").css("height",$(window).height()) ;
			$("body").css("width",$(window).width()) ;
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px");
            $(".wfj_top").find("li").find("img").each(function(){
            	var ihei=imgPosition($(".wfj_top").height(),$(this).height());
            	//$(this).attr("style",$(this).attr("style")+";margin-top:"+ihei+"px");
            	$(this).attr("style",$(this).attr("style")+";");
            });
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			$(".font").css({"font-size":$(window).height()*0.017+"px","padding-left":$(window).height()*0.01+"px"})
			$(".font img").css({"width":$(window).height()*0.017+"px"})
			
			if($(window).width()>$(window).height()){
				$(".wfj01_016").css("width","70%");
				$(".wfj01_016_content").attr("style","width:100%; height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:hidden;");
				$(".wfj01_016_hidden").attr("style","width:"+parseInt($(".wfj01_016_content").width()+17)+"px; height:"+parseInt($(window).height()-$(".wfj_top").height())+"px;overflow:auto;");
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
			
			$(".wfj12_014_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj12_014_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
            $("#money2").attr("style","color:#F74C31;");
			$("#money2").css("color","#F74C31");
			$(".wfj12_014_bottom_commit").attr("style","height:"+$(window).height()*0.05+"px; padding-top:"+$(window).height()*0.01+"px;");
			$(".wfj12_014_bottom_commit").find("div").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");
			$(".wfj12_014_hidden_buy").attr("style","width:"+$(window).width()+"px;position:absolute;display:none;");
			
			
	          $(".wfj01_016 .wfj_top ul").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
	            $(".wfj01_016 .wfj_top ul li").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position: relative;");
	            $(".wfj01_016 .wfj_top ul li:nth-child(1)").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;width:15%;");
	            $(".wfj01_016 .wfj_top ul li:nth-child(2)").attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
	            $(".wfj01_016 .wfj_top ul li:nth-child(3)").attr("style","width:15%;");
			
	            //$(".jqmWindowcss1").attr("style","width:"+$(window).width()+"px;margin-left:0;");
	            

	            
			$(".wfj_return").click(function(){
				open("wfj01_015.html","_self");
			});
			
			$("#money").keyup(function(){
				money=$("#money").val();
				money = money.toString().replace(/\$|\,/g,'');  
				var reg=/^[1-9]\d*$|^0$/;
				if(reg.test(money)==true){
					money=formatCurrency(money/100);
		    		$("#jinbi").text("￥"+money);
				}else{					
					alert("输入格式错误，请重新输入！");
					$("#money").val("");
				}										    	
			})
						
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			$("#dh").click(function(){
				$("#alert").show();
				//alert("研发中,敬请期待....");
				//return;
				if($("#jinbi").text()==null||$("#jinbi").text()==""||$("#jinbi").text()==0){
					alert("请先填写充值金币数量");
					return;
				}
				//生成订单号
				$.ajax({
					url:basePath+"/ea/jinbi/sajax_getJum.jspa?",
					type:"get",
					data:"user="+user+"&khd="+khd,
					success:function suc(data){
						var mb=eval("("+data+")");
						jum=mb.jum;
						staffid=mb.wfj_staffid;
					}
				});
				ljzf(formatCurrency(money));
			});
			
			$(".wfj12_014_choice").find("img").click(function(){
				$(".wfj12_014_choice").find("img").attr("src","<%=basePath %>images/ea/finance/BenDis/choice_02.png");
				$(this).attr("src","<%=basePath %>images/ea/finance/BenDis/choice_01.png");
				zffs=$(this).attr("name");
			});
			
			/* $(".jqmOverlay").live("click",function(){
				$(".wfj12_014_hidden_buy").fadeOut();
				$("#occlusion2").jqmHide();
				$("#alert").hide();
			}); */
			$("#alert").click(function(){
				$(".wfj12_014_hidden_buy").fadeOut();
				$("#occlusion2").jqmHide();
				$("#alert").hide();
			});
		});
		
    	
		function ljzf(el){
			$("#money2").text("￥"+el);
			$("#occlusion2").css("z-index",$(".wfj12_010").css("z-index")+1);
			$("#occlusion2").jqmShow();
			$(".wfj12_014_hidden_buy").css("z-index",$("#occlusion2").css("z-index")+1);
			$(".wfj12_014_hidden_buy").fadeIn(1000);
			//var $tb=$(el).parent().parent().parent().parent().parent().parent().parent();
			//jum=$tb.find(".xq").attr("id");
			morre=el;
		};
		
		function zf(){
	         if(zffs==null){
                alert("请选择支付方式");
                return false;
         	 }else{
	          	if(zffs=='1'){
	 				var par=new Array();
	 				par.push(basePath);
	 				par.push("page/ea/main/finance/BenDis/wfjAlipay.jsp?");
	 				par.push("WIDout_trade_no="+jum);
	 				par.push("&WIDtotal_fee="+morre);
	 				par.push("&WIDsubject=(北京天太世统科技有限公司)微分金金币");
					par.push("&WIDbody="+staffid+","+sccid);//订单描述
					par.push("&WIDit_b_pay=''");//超时时间
					par.push("&WIDextern_token=''");//钱包
					window.location.href = encodeURI(par.join(""));
    			}else if(zffs=='2'){
    				document.forms[0].reset();
    				$("#formsutm").find("#journalNum").val(jum);
    				$("#formsutm").find("#baseUrl").val(basePath);
    				$("#formsutm").find("#morre").val(morre);
    				$("#formsutm").find("#staffid").val(staffid);
    				$("#formsutm").find("#sccid").val(sccid);
        			$("#formsutm").attr("action",basePath + "/ea/jinbi/ea_zfgs.jspa");
					$("#submit").click();
     	 		}else if(zffs=='3'){
     	 			var goodsname = "(北京天太世统科技有限公司)微分金金币";
     	 			var ua = navigator.userAgent.toLowerCase();
     				var isWeixin = ua.indexOf('micromessenger') != -1;
     				if (!isWeixin) {
     				 //app微信支付
     					$.ajax({
     						url:basePath+"ea/wfjshop/sajax_ea_appWechatPay.jspa",
     						type:"get",
     						data:{
     							"payDto.orderId":jum,
     							"payDto.totalFee":morre,
     							"payDto.body":goodsname
     						},
     						success:function suc(data){
     							var mb=eval("("+data+")");
     							var appPackage=mb.appPackage;
     							//调用ios/android
     							var u = navigator.userAgent;
     				        	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
     				        	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
     				            var  appid = appPackage.appid
     				            var partnerid = appPackage.partnerid;
     				            var prepayid = appPackage.prepayid;
     				            var packages = appPackage.packages;
     				            var noncestr = appPackage.noncestr;
     				            var timestamp = appPackage.timestamp;
     				            var err_code = appPackage.err_code;
     				            var sign = appPackage.sign;
     		                   
     				         	if(isAndroid==true){		
     				         		Android.callAndroidappChat(appid,partnerid,prepayid,packages,noncestr,timestamp,sign,jum,err_code);	
     				        	}else if(isiOS==true){   		
     				        		 var url= "func=" + 'ioscallappChat';        
     				                  params={'appid':appid,'partnerid':partnerid,'prepayid':prepayid,'noncestr':noncestr,'timestamp':timestamp,'sign':sign,'journalNum':jum,'err_code':err_code}; 
     				                  for(var i in params){
     				                  url = url + "&" + i + "=" + params[i];
     				                  }   
     				                  window.webkit.messageHandlers.Native.postMessage(url);    
     				        	}
     						}
     						
     					});
     					
     				}else{
     					window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa1b3f84c027804c3&redirect_uri=http://www.impf2010.com/page/WFJClient/CustomerOrder/jsapi_demo.jsp?params="+jum+"-"+goodsname+"-"+morre+"-"+staffid+"&response_type=code&scope=snsapi_base&state=STATE&connect_redirect=1#wechat_redirect";	
     					
     				}	
	       	 	    return false;
	       	 	}else{
	       	 		if(token!=0){
				     	return;
			        }
			        token=1;
					$.ajax({
						url:basePath+"ea/wfjshop/sajax_ea_changeBillState.jspa",
						type:"get",
						data:"fenlei=03&ddid="+$("#"+jum).find("#jnum").val(),
						success:function suc(data){
							var mb=eval("("+data+")");
							var succ=mb.succ;
							var threeNo=mb.threeNo;
							if(succ=="success"){
								window.location.href=basePath+"page/WFJClient/suc/xxzf.jsp?threeNo="+threeNo;	
							};
						}
					});
	       	 	}
	       	 }
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
</body>
</html>
