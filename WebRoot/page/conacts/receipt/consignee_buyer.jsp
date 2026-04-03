<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />

<title>买家收货单</title>
<link href="<%=basePath%>css/contacts/recepit/style12.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<script type="text/javascript"
	src="<%=basePath%>js/restaurant/iscroll-probe.js"></script>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var pagenumber="${pageForm.pageNumber}";
	var user="${user}";
</script>
</head>
<body>
	<div class="wfj12_010">
    	<!--中联园区头部-->
    	<div class="wfj_top">
			<ul>
				<li><a href="<%=basePath %>ea/vipcenter/ea_orderManage.jspa" target="_self">
					<img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" />
				</a>
				</li>
				<li>收货单</li>
				<li><a href="javascript:;"><img	src="<%=basePath %>images/ea/finance/BenDis/top_more.png" />
				</a>
				</li>
			</ul>
		</div>
    	<!--中联园区头部 end-->
    	<!--选择状态-->
        <div class="wfj_state">
        	<ul>
            	<li >待评价</li>
            	<li >交易成功</li>
            </ul>
        </div>
    	<!--选择状态 end-->
        <div class="wfj12_010_content">
        	<div class="wfj12_010_hidden">
        <div id="wrapper">
	    <div id="scroller">
        	     <input type="hidden" id="pageNumber" value="${pageForm.pageNumber}"/>
                 <input type="hidden" id="pageCount" value="${pageForm.pageCount}"/>
            	<div class="wfj12_010_con che01">
                	<c:forEach items="${pageForm.list}" var="order">
	                	<div class="wfj12_010_title">
	                    	<div class="wfj12_010_width" id="dd">
	                        	<ul>
	                        	<!-- 关联id -->
	                        	<input type="hidden" id="cashierBillsID" name="pingf" value="${order[0]}">
	                        	<!-- 关联id -->
	                        	<input type="hidden" id="csid" name="" value="${order[5]}">
	                            <!-- 下单时间 -->
	                            <input type="hidden" id="orderDate" name="orderDate" value="${order[6]}">
	                            <!-- 收货人名称 -->
	                            <input type="hidden" id="consigneeName" name="consigneeName" value="${order[7]}">
	                            <!-- 收货地址 -->
	                            <input type="hidden" id="consingneeAddress" name="consingneeAddress" value="${order[8]}">
	                            <!-- 收货时间 -->
	                            <input type="hidden" id="consignneDate" name="consignneDate" value="${order[9]}">
	                            <!-- 发货时间 -->
	                            <input type="hidden" id="senDate" name="senDate" value="${order[10]}">
	                            <!-- 价钱总和 -->
	                            <input type="hidden" id="priceSub" name="priceSub" value="${order[11]}">
	                            <!-- 运费 -->
	                            <input type="hidden" id="wlyf" name="wlyf" value="${order[14]}">
	                            <!-- 订单编号 -->
	                            <input type="hidden" id="number" name="number" value="${order[3]}">
	                             <!-- 主键-->
	                            <input type="hidden" id="cskey" name="cskey" value="${order[12]}">
	                            <!-- 主键-->
	                            <input type="hidden" id="companyID" name="companyID" value="${order[13]}">
	                           <li>${order[2]}&nbsp;<img src="<%=basePath%>images/contacts/recepit/wfj_return_03.png" /></li>
	                            	
	                            </ul>
	                        </div>
	                    </div>
                    <div class="wfj12_010_product">
                    	<div class="wfj12_010_width cpp" id="cp">
                    	<c:forEach   var="good" items="${map1[order[0]]}">
                        	<table width="94%">
                        	
                        	<input type="hidden" id="ppid" name="ppid" value="${good[7] }">
                            	<tr id="image">
                                	<td width="30%" rowspan="4"><img width="95%" src="<%=basePath%>${good[0] }" onclick="Click(this)"/></td>
                                	<td colspan="3"><span>产品编号：${good[5] }</span></td>
                                </tr>
                            	<tr id="idu">
                                	<td colspan="2" class="productInfo"><span id="spu">${good[1]}</span></span></td>
                                	<td class="pdright" align="right"><span id="money">${good[4]}</span></td>
                                </tr>
                            	<tr>
                                	<td width="25%">颜色：黑色</td>
                                	<td width="25%">尺码：42</td>
                                	<td class="pdright" width="20%" align="right">X<span id="price">${good[3]}</span></td>
                                </tr>
                            </table>
                        	 <table class="wfj12_010_height">
                            	<tr>
                                	<td width="30%"></td>
                                	<td>
                                    	<div class="wfj12_010_button">
                                        	<ul>
                                            	<li><a href="javascript:;">查看物流</a></li>
                                            	<c:if test='${sta=="00"}'>
                                            	<li class="her" id="pj" onclick="check1(this)"><a href="javascript:;">评价</a></li>
                                            	</c:if>
                                            	
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
                               
                            </table>
                               </c:forEach>
                        </div>
                    </div>
                	</c:forEach>
                </div>
	</div>
	</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    var sta = "${sta}";
    var number=2;
		var myScroll,
			//upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		setTimeout(function(){
			$("#wrapper").css("top","0.4rem");
            myScroll = new IScroll("#wrapper", {  
            mouseWheel: true,
            probeType: 3,
            scrollbars: true,
            disableMouse: true,
            disablePointer: true,
            click:true});
			//myScroll.refresh();
			myScroll.on("scroll",function(){
			var nowPos = $('#scroller')[0].offsetHeight - $('#scroller').parent()[0].offsetHeight - parseInt(this.y)*(-2);
				console.log(nowPos);		
				if(nowPos <=0){
			        var pn=parseInt($("#pageNumber").val())+1;
			        var pc=parseInt($("#pageCount").val());
					if(pn<=pc){
						var url=basePath+"ea/consignee/sajax_ea_receipt.jspa?&tupn=goods&stype3=ww&sta="+sta+"&user="+user+"&pageNumber="+number;
						$.ajax({
							    url:url,
								type:"get",
								async : false,
								dataType : "json",
								
								success:function(data){
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								var mapp = member.mapp;
								var chars = pageForm.list;
								if(chars==null||chars.length==0){
									number++;
								return;
								}
								var goodstr = "";
								for ( var i = 0; i < chars.length; i++) {
									var cash = chars[i];
									goodstr+="<div class='wfj12_010_title'><div class='wfj12_010_width'><ul><li>"+cash[2]+"&nbsp;";
									goodstr+="<input type='hidden' id='cashierBillsID' name='pingf' value='"+cash[0]+"'>";
									goodstr+="<input type='hidden' id='csid' name='' value='"+cash[5]+"'>";
									goodstr+="<input type='hidden' id='orderDate' name='orderDate' value='"+cash[6]+"'>";
									goodstr+="<input type='hidden' id='consigneeName' name='consigneeName' value='"+cash[7]+"'>";
									goodstr+="<input type='hidden' id='consingneeAddress' name='consingneeAddress' value='"+cash[8]+"'>";
									goodstr+="<input type='hidden' id='consignneDate' name='consignneDate' value='"+cash[9]+"'>";
									goodstr+="<input type='hidden' id='senDate' name='senDate' value='"+cash[10]+"'>";
									goodstr+="<input type='hidden' id='priceSub' name='priceSub' value='"+cash[11]+"'>";
									goodstr+="<input type='hidden' id='number' name='number' value='"+cash[3]+"'>";
									goodstr+="<input type='hidden' id='cskey' name='cskey' value='"+cash[12]+"'>";
									goodstr+="<input type='hidden' id='companyID' name='companyID' value='"+cash[13]+"'>";
									goodstr+="<input type='hidden' id='wlyf' name='wlyf' value='"+cash[14]+"'>";
									goodstr+="<img src='"+basePath+"images/contacts/recepit/wfj_return_03.png' /></li><input type='hidden' id='id' value='' /></ul></div></div>";
									goodstr+="<div class='wfj12_010_product'><div class='wfj12_010_width'><div class='xq' id=''>";
									var goodsList = mapp;
							             for ( var j = 0; j < mapp[cash[0]].length; j++) {
										var goodbill=mapp[cash[0]][j];
										goodstr+="<table width='94%' class='wfj_goods'><tr>";
										goodstr+="<td width='30%' rowspan='4'><img width='95%' src='"+basePath+goodbill[0]+"'onclick='Click(this)' /></td>";
										goodstr+="<input type='hidden' id='ppid' name='ppid' value='"+goodbill[7]+"'/>";
										goodstr+="<td colspan='3'><span>产品编号："+goodbill[5]+"</span></td>";
										goodstr+="</tr><tr id='idu'>";
										 goodstr+="<td colspan='2' class='productInfo'><span id='spu'>"+goodbill[1]+"</span></td>";
										goodstr+="<td class='pdright' align='right'><span>￥"+goodbill[4]+"</span></td>";
										goodstr+="</tr><tr>";
										goodstr+="<td width='25%'>颜色：黑色</td>";
										goodstr+="<td width='25%'>尺码：42</td>";
										goodstr+="<td class='pdright' width='20%' align='right'>X"+goodbill[3]+"</td>";
										goodstr+="</tr><tr>";
										goodstr+="</tr></table>";
							              }
									
									goodstr+="</div>";
									goodstr+="<table class='wfj12_010_height'><tr><td width='30%'></td><td><div class='wfj12_010_button'><ul>";
									
									goodstr+="<li><a href='javascript:;'>查看物流</a></li>";
									if(${sta=="00"}){
								        goodstr+="<li class='her' id='pj' onclick='check1(this)'><a href='javascript:;'>评价</a></li>";
									}
									goodstr+="</ul></div></td></tr>";
									goodstr+="<tr><td width='30%'></td><td><div class='wfj12_010_button'><ul>";
									goodstr+="</ul></div></td></tr></table>";
									goodstr+="</div></div>";
								}
			                    $("div.wfj12_010_con").append(goodstr);
								$(".wfj12_010_title").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
					            $(".wfj12_010_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
					            $(".wfj12_010_title").find("li").find("img").attr("style","height:"+$(window).height()*0.015+"px;");
								$(".wfj12_010_product").find("table").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #DEDEDE;padding-top:"+$(window).height()*0.01+"px;");
					            $(".wfj12_010_product").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
					            $(".wfj12_010_product").find("table").find("span").attr("style","font-size:"+$(window).height()*0.021+"px;");
					            $(".wfj12_010_product").find("table").find("span").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto; vertical-align:middle");
					            $(".wfj12_010_product").find("table").find("a").attr("style","font-size:"+$(window).height()*0.02+"px; padding:"+$(window).height()*0.002+"px "+$(window).height()*0.01+"px; border:"+$(window).height()*0.002+"px solid #666; border-radius:"+$(window).height()*0.005+"px;");
								//$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;");
								$(".wfj12_010_hidden").attr("style","overflow-y: auto;");
								//var h = $(".wfj12_010_title").height()*$(".wfj12_010_title").length+$(".wfj12_010_product").height()*$(".wfj12_010_product").length;
								var h = $(".wfj12_010_con").height();
								$("#pageNumber").val(number);
				                $("#scroller").css("height",h+"px");
								number++;
								myScroll.refresh();
							},error:function(data){
								alert("获取项目失败");
							}
						}); 
					}
				}
			});
		},0);
		

       //点击商品名称跳转的界面，传参
       function Click(obj){
            var cashierBillsID =$(obj).parents("div").find("#cashierBillsID").val();
			var csid= $(obj).parents("div").find("#csid").val();
			var ppid=$(obj).parents("div").find("#ppid").val();
			var price=$(obj).parents("div").find("#price").html();
			var money=$(obj).parents("div").find("#money").html();
			var key=$(obj).parents("div").find("#cskey").val();
			var image= $(obj).parents("div").find("#image").find("img").attr("src");
			var orderDate=$(obj).parents("div").find("#orderDate").val();
			var consigneeName=$(obj).parents("div").find("#consigneeName").val();
			var consingneeAddress=$(obj).parents("div").find("#consingneeAddress").val();
			var consignneDate=$(obj).parents("div").find("#consignneDate").val();
			var senDate=$(obj).parents("div").find("#senDate").val();
			var order=$(obj).parents("div").find("#order").text();
			var priceSub=$(obj).parents("div").find("#priceSub").val();
			var wlyf=$(obj).parents("div").find("#wlyf").text();
			var nuuk = $(obj).parents("table").find("#spu").text();
			var number=$(obj).parents("div").find("#dd").find("#number").val();
            document.location.href=basePath+"page/conacts/receipt/trading.jsp?orderDate="+orderDate+"&consingneeAddress="+consingneeAddress
            +"&consigneeName="+consigneeName+"&senDate="+senDate+"&consignneDate="+consignneDate+"&order="+order
            +"&image="+image+"&nuuk="+nuuk+"&price="+price+"&priceSub="+priceSub+"&wlyf="+wlyf+"&number="+number+"&cskey="+key+"&user="+user+"&money="+money; 
       
       }
       //点击评论按钮，跳转的界面
       function check(sta){

			document.location.href=basePath+"ea/consignee/ea_receipt.jspa?tupn=goods&sta="+sta+"&stype3=&user="+user;
			}
	   function check1(obj){
	          var companyID = $("#companyID").val();
	        var images= $(obj).parents("div").find("#image").find("img").attr("src");
			var cashierBillsID =$("#dd").find("#cashierBillsID").val();
			var csid=$(obj).parents("div").find("#dd").find("#csid").val();
			var cskey =$("#dd").find("#cskey").val();
			var ppid=$("#cp").find("#ppid").val();
			//var image=$("#cp").find("#image").find("img").attr("src");
			  document.location.href=basePath+"page/conacts/receipt/comments.jsp?cashierBillsID="
			+cashierBillsID+"&csid="+csid+"&ppid="+ppid+"&image="+images+"&cskey="+cskey+"&user="+user+"&companyID="+companyID;
			
	   }
		
    	$(document).ready(function(e) {
    	
    	//提交表单
			
			
			//中联园区头部
           $(".wfj_top").attr("style","height:"+$(window).height()*0.090+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
           //$('#wrapper').css('height', $(window).height()-266);
           
            $(".wfj_state").attr("style","height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_state").find("li").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #dedede; font-size:"+$(window).height()*0.025+"px;");
            //上拉刷新加载
			$("#wrapper").find(".sx").attr("style","font-size:"+$(window).height()*0.018+"px;");
			//后加的
			$(".wfj_top").find("ul").attr("style","margin-bottom:0px;");
			$(".wfj_state").find("ul").attr("style","margin-bottom:0px;");
            // $(".wfj_state2").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
           
            if(sta=="00"){
            $(".wfj_state").find("li").eq(0).attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
			}else{
			 $(".wfj_state").find("li").eq(1).attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
			}
			$(".wfj_state").find("li").click(function(){
				$(".wfj_state").find("li").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #dedede; font-size:"+$(window).height()*0.025+"px;");
				$(this).attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #F74C31; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
				if($(this).text()=="待评价"){
				check("00");
					$(".che01").css("display","block");
					$(".che02").css("display","none");
				}else if($(this).text()=="交易成功"){
				check("11");
					$(".che02").css("display","none");
					$(".che01").css("display","block");
				}
				
			});
			
			
            $(".wfj12_010_title").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px;");
            $(".wfj12_010_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_title").find("li").find("img").attr("style","height:"+$(window).height()*0.015+"px;");
            $(".wfj12_010_product").find("table").attr("style"," border-bottom:"+$(window).height()*0.003+"px solid #DEDEDE;padding-top:"+$(window).height()*0.01+"px;");
            $(".wfj12_010_product").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_product").find("table").find("span").attr("style","font-size:"+$(window).height()*0.021+"px;");
            $(".wfj12_010_product").find("table").find("span").find("img").attr("style","height:"+$(window).height()*0.03+"px;width:auto; vertical-align:middle");
            $(".wfj12_010_product").find("table").find("a").attr("style","font-size:"+$(window).height()*0.02+"px; padding:"+$(window).height()*0.002+"px "+$(window).height()*0.01+"px; border:"+$(window).height()*0.002+"px solid #666; border-radius:"+$(window).height()*0.005+"px;");
            $(".wfj12_010_height").find("tr").attr("style","height:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.02+"px;");
            $(".wfj12_010_height").find("tr").find("span").css("fontSize",$(window).height()*0.025+"px");
			$(".pdright").css("paddingRight",$(window).height()*0.01+"px");
			$(".wfj12_010 .wfj12_010_content").css({"position":"relative","height":$(window).height()-$(".wfj_top").outerHeight()-$(".wfj_state").outerHeight()+"px","width":"100%"})
								
			/* //隐藏滚动条
			$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj12_010").height())+"px;");
			$(".wfj12_010_hidden").attr("style","height:"+parseInt($(".wfj12_010_content").height())+"px;");
			
			
		    if($(".wfj12_010_content").css("height").split("px")[0]<$(window).height()){
				$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.89+"px;");
			} 
 */
			/* var h = $(".wfj12_010_title").height()*$(".wfj12_010_title").length+$(".wfj12_010_product").height()*$(".wfj12_010_product").length;
			if(h < $(".wfj12_010_content").height()){
				$(".wfj12_010_hidden").css("width",$(".wfj12_010_content").width()+"px");
				$("#scroller").css("height",$(".wfj12_010_hidden").height()+"px");
			}else{
				$(".wfj12_010_hidden").css("width",parseInt($(".wfj12_010_content").width()+17)+"px");
			} */
        });
    </script>
</body>
</html>