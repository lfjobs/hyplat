<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/contacts/style12.css"/>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>
<title>订单管理-卖家</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
    var pl="${pl}";
    var staid="${staid}";
    var pagenumber="${pageForm.pageNumber}";
    var ljly="${ljly}";
</script>
</head>

<body onload="loaded()">
	<div class="wfj12_016">
		<s:if test="ljly==html">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:history.go(-1);" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" /></a></li>
            	<li>订单管理</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/ea/finance/BenDis/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	</s:if>
        <div class="wfj12_016_top_title">
        	<ul>
            	<li id="wfj_dd_">全部订单</li>
            	<li id="wfj_dd_01">未付款</li>
            	<li id="wfj_dd_00">待发货</li>
            	<li id="wfj_dd_02">待收货</li>
            </ul>
        </div>
        <div class="wfj12_016_content">
        	<div class="wfj12_016_hidden">
        	<div id="wrapper">
	<div id="scroller">
		<div id="scroller-pullDown"><img id="wfj_sx" src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
			<span id="down-icon" class="icon-double-angle-down pull-down-icon"></span>
			<span id="pullDown-msg" class="pull-down-msg sx">下拉刷新</span>
		</div>
        		<div id="wfj12_append">
            	<s:iterator value="pageForm.list" var="pf">
            	<div class="wfj_mx" id="${pf.cashierBillsID}">
            	<div class="wfj12_016_con">
                	<div class="wfj12_016_width">
                    	<ul>
                        	<li class="left"><font>单据编号：${pf.journalNum }</font></li>
                        	<li class="right">${fn:substring(pf.cashierdate, 0, 10)}&nbsp;<img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_03.png" /></li>
                        </ul>
                    </div>
                </div>
            	<div class="wfj12_016_product">
                	<div class="wfj12_016_width">
                    	<table>      	
                    		<s:iterator value="#pf.goodsList" var="gl" status="gln">
                        	<tr>
                            	<td width="10%"><img src="<%=basePath %>images/ea/finance/BenDis/product_list_01.png" /></td>
                            	<td width="45%">${gl[5]}</td>
                            	<td width="30%">${gl[10]}</td>
                            	<td width="10%">X${gl[2]}</td>
                            </tr>
                            </s:iterator>
                            
                            <s:iterator value="#pf.ptgoodsList" var="pt" status="gln">
                        	<tr>
                            	<td width="10%"><img src="<%=basePath %>images/ea/finance/BenDis/product_list_01.png" /></td>
                            	<td width="45%">${pt[5]}</td>
                            	<td width="30%">${pt[10]}</td>
                            	<!--<td width="10%">X${pt[2]}</td> -->
                            	<td width="5%"><img src="<%=basePath %>/images/WFJClient/Newjspim/cuxiao.png" style="width: 33px;"/></td>
                            </tr>
                            </s:iterator>
                        </table>
                    </div>
                </div>
                </div>
                </s:iterator>
            </div>
            <div id="scroller-pullUp"><img src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
		<span id="up-icon" class="icon-double-angle-up pull-up-icon"></span>
		<span id="pullUp-msg" class="pull-up-msg sx">上拉刷新</span>
	</div>
	</div> 
	</div>
        </div>
        </div>
    </div>
    <script type="text/javascript">
		var myScroll,
			upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		function loaded () {
			$("#wrapper").css("top",$(".wfj_top").height()+$(".wfj12_016_top_title").height()+$(window).height()*0.03+"px");
            //preventDefault为false这行就是解决onclick失效问题
            //为true就是阻止事件冒泡,所以onclick没用
			myScroll = new IScroll('#wrapper', { scrollX: false, scrollY: true, mouseWheel: true,probeType: 3,preventDefault:false});
	
			myScroll.on("scroll",function(){
				var y = this.y,	maxY = this.maxScrollY - y,downHasClass = downIcon.hasClass("reverse_icon"),
					upHasClass = upIcon.hasClass("reverse_icon");
				if(y >= 40){
					!downHasClass && downIcon.addClass("reverse_icon");
					return "";
				}else if(y < 40 && y > 0){
					
					downHasClass && downIcon.removeClass("reverse_icon");
					return "";
				}
				
				if(maxY >= 40){
					!upHasClass && upIcon.addClass("reverse_icon");
					return "";
				}else if(maxY < 40 && maxY >=0){
					upHasClass && upIcon.removeClass("reverse_icon");
					return "";
				}
			});
			
			myScroll.on("slideDown",function(){
				if(this.y > 40){
					console.log("down");
					document.location.href=basePath+"/ea/ghspb/ea_getcomporder.jspa?staid="+staid+"&pl="+pl+"&pageForm.pageNumber=1";
					myScroll.refresh();
					
				}
			});
			
			myScroll.on("slideUp",function(){
				if(this.maxScrollY - this.y > 40){
					console.log("up");
					var pn=parseInt(pagenumber)+1;
					 if(pn<=parseInt("${pageForm.pageCount}")){
						pn=(pn>parseInt("${pageForm.pageCount}")?parseInt("${pageForm.pageCount}"):pn);
						var url=basePath+"/ea/ghspb/sajax_getAjax.jspa?";
						$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							data:{
							  "staid":staid,
							  "pl":pl,
							  "pageForm.pageNumber":pn
							},
							success : function (data) {
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								var cashlist=pageForm.list;
								var goodstr="";
								for ( var i = 0; i < cashlist.length; i++) {
									var cash=cashlist[i];
									goodstr+="<div class='wfj_mx' id='"+cash.cashierBillsID+"'>";
					            	goodstr+="<div class='wfj12_016_con'>";
					                goodstr+="<div class='wfj12_016_width'>";
					                goodstr+="<ul>";
					                goodstr+="<li class='left'><font>单据编号："+cash.journalNum+"</font></li>";
					                goodstr+="<li class='right'>"+cash.cashierdate+"&nbsp;<img src='"+basePath+"images/ea/finance/BenDis/wfj_return_03.png' /></li>";
					                goodstr+="</ul>";
					                goodstr+="</div>";
					                goodstr+="</div>";
					            	goodstr+="<div class='wfj12_016_product'>";
					               	goodstr+="<div class='wfj12_016_width'>";
					                goodstr+="<table>";
		                    		for ( var j = 0; j < cash.goodsList.length; j++) {
		                    			var gl=cash.goodsList[j];
			                        	goodstr+="<tr>";
			                            goodstr+="<td width='10%'><img src='"+basePath+"images/ea/finance/BenDis/product_list_01.png' /></td>";
			                            goodstr+="<td width='65%'>"+gl[5]+"</td>";
			                            goodstr+="<td width='25%'>"+gl[2]+"</td>";
			                            goodstr+="</tr>";
		                            }
		                    		if(cash.ptgoodsList!=null&&cash.ptgoodsList.length>0){
		                    			for(var x=0;x<cash.ptgoodsList.length;x++){
		                    				var pt=cash.ptgoodsList[x];
		                    				goodstr+="<tr>";
		                                	goodstr+="<td width='10%'><img src='"+basePath+"images/ea/finance/BenDis/product_list_01.png' /></td>";
				                            goodstr+="<td width='45%'>"+pt[5]+"</td>";
				                            goodstr+="<td width='30%'>"+pt[10]+"</td>";
				                            goodstr+="<td width='5%'><img src='"+basePath+"/images/WFJClient/Newjspim/cuxiao.png' style='width: 33px;'/></td>";
				                            goodstr+="</tr>";
		                    			}
		                    		}
					                goodstr+="</table>";
					                goodstr+="</div>";
					                goodstr+="</div>";
					                goodstr+="</div>";
								}
								$("div#wfj12_append").append(goodstr);
								$(".wfj12_016_con").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;margin-bottom:"+$(window).height()*0.005+"px;");
								$(".wfj12_016_con").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
								$(".wfj12_016_con").find("li").find("img").attr("style","height:"+$(window).height()*0.02+"px;");
								
								$(".wfj12_016_product").attr("style","margin-bottom:"+$(window).height()*0.005+"px;");
								$(".wfj12_016_product").find("tr").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
								$(".wfj12_016_product").find("tr").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
								$(".wfj12_016_product").find("tr").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
								$(".wfj12_016_hidden").attr("style","overflow-y: auto;");
								$("#scroller").css("height",$(".wfj12_append").height()+"px");
							},
							error:function(data){
								alert("获取单据失败");
							}
						}); 
					}else{
						if(pn>0){
							alert("已无更多单据");
						}
					}
					pagenumber=pn;
				}
			});
		}
	</script>
    <script type="text/javascript">
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj12_016_top_title").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;margin-bottom:"+$(window).height()*0.01+"px;");
			$(".wfj12_016_top_title").find("li").attr("style","font-size:"+$(window).height()*0.022+"px;");
			$(".wfj12_016_top_title").find("#wfj_dd_"+pl).attr("style","font-size:"+$(window).height()*0.022+"px;color:#F74C31;");
			$("#wrapper").find(".sx").attr("style","font-size:"+$(window).height()*0.018+"px;");
			$(".wfj12_016_top_title").find("li").click(function(){
				var a=$(this).attr("id").substring(7);
				$(".wfj12_016_top_title").find("li").css("color","#666");
				$(this).css("color","#F74C31");
				document.location.href=basePath+"/ea/ghspb/ea_getcomporder.jspa?staid="+staid+"&pl="+a;
			});
			
			
			var len = $(".wfj12_016_top_title").find("li").length;
			if(len==1){
				$(".wfj12_016_top_title").find("li").css("width","100%");	
			}else if(len==2){
				$(".wfj12_016_top_title").find("li").css("width","50%");	
			}else if(len==3){
				$(".wfj12_016_top_title").find("li").css("width","33.3333%");	
			}else if(len==4){
				$(".wfj12_016_top_title").find("li").css("width","25%");	
			}else{
				alert("标题太多了。。。。。")
			}
			
			
			$(".wfj12_016_con").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;margin-bottom:"+$(window).height()*0.005+"px;");
			$(".wfj12_016_con").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_016_con").find("li").find("img").attr("style","height:"+$(window).height()*0.02+"px;");
			
			$(".wfj12_016_product").attr("style","margin-bottom:"+$(window).height()*0.005+"px;");
			$(".wfj12_016_product").find("tr").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj12_016_product").find("tr").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_016_product").find("tr").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
			
			$(".wfj12_016_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj12_016").height())+"px;");
			$(".wfj12_016_hidden").attr("style","height:"+parseInt($(".wfj12_010_content").height())+"px;");
			
			//$(".wfj12_016_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj12_016_top_title").height())+"px;");
			
			if($(".wfj12_016_content").css("height").split("px")[0]<$(window).height()){
				$(".wfj12_016_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.89+"px;");
			} 

			
			var h = $(".wfj12_016_con").height()*$(".wfj12_016_con").length+$(".wfj12_016_product").height()*$(".wfj12_016_product").length+$(window).height()*0.005*$(".wfj12_016_con").length+$(window).height()*0.005*$(".wfj12_016_product").length;
			if(h < $(".wfj12_016_content").height()){
				$(".wfj12_016_hidden").css("width",$(".wfj12_016_content").width()+"px");
			}else{
				$(".wfj12_016_hidden").css("width",parseInt($(".wfj12_016_content").width()+17)+"px");
			}
			
			$(".wfj_mx").click(function(){
				var id=$(this).attr("id");
				window.location.href=basePath+"/ea/ghspb/ea_getCashBill.jspa?staid="+id;
			});
        });
    </script>
</body>
</html>