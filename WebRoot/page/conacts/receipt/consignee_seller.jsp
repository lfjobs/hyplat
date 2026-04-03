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
<title>卖家收货单</title>
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
<body >
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
            	<li>待评价</li>
            	<li>交易成功</li>
            </ul>
        </div>
    	<!--选择状态 end-->
        <div class="wfj12_010_content">
        	<div class="wfj12_010_hidden">
        <div id="wrapper">
	    <div id="scroller">
		<div id="scroller-pullDown">
		</div>
        	     <input type="hidden" id="pageNumber" value="${pageForm.pageNumber}"/>
                 <input type="hidden" id="pageCount" value="${pageForm.pageCount}"/>
            	<div class="wfj12_010_con che01">
                	<c:forEach items="${pageForm.list}" var="order">
	                	<div class="wfj12_010_title">
	                    	<div class="wfj12_010_width" id="dd">
	                        	<ul>
                    	
                        	        <li class="left"><font>单据编号：${order[3]}</font></li>
                        	        <li class="right">&nbsp;<img src="<%=basePath%>images/contacts/management/wfj_return_03.png" /></li>
                                 </ul>
	                        </div>
	                    </div>
                    <div class="wfj12_010_product">
                    	<div class="wfj12_010_width cpp" id="cp">
                        	<table>
                    	 <c:forEach   var="good" items="${map1[order[0]]}">
                        	<tr>
                            	<td width="10%"><img src="<%=basePath%>images/contacts/management/product_list_01.png" /></td>
                            	<td width="65%">${good[1]}</td>
                            	<td width="25%">${good[3]}箱</td>
                            </tr>    	
                            </c:forEach>
                        </table>
                        </div>
                    </div>
                	</c:forEach>
                </div>
                
        <div id="scroller-pullUp">
		
	</div>
	</div>
	</div>
            </div>
        </div>
    </div>
    
	
	<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
	<div id="occlusion3" class="jqmWindow jqmWindowcss1"></div>
    <script type="text/javascript">
    var sta = "${sta}";
		 var number=2;
		var myScroll,
			//upIcon = $("#up-icon"),
			downIcon = $("#down-icon");
			//document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
		setTimeout(function(){
			$("#wrapper").css("top","0.5rem");
			 myScroll = new IScroll("#wrapper", {  
            mouseWheel: true,
            probeType: 3,
            scrollbars: true,
            disableMouse: true,
            disablePointer: true,
            click:true});
	
			
			
			myScroll.on("scroll",function(){
			var nowPos = $('#scroller')[0].offsetHeight - $('#scroller').parent()[0].offsetHeight - parseInt(this.y)*(-2);
				if(nowPos <=0){
			        var pn=parseInt($("#pageNumber").val())+1;
					if(pn<=parseInt($("#pageCount").val())){
						var url=basePath+"ea/consignee/sajax_ea_receipt.jspa?stype=seller&tupn=confirm&stype3=pp&sta="+sta+"&user="+user+"&pageNumber="+number;
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
									goodstr+="<div class='wfj12_010_title'><div class='wfj12_010_width'><ul><li>"+cash[3]+"&nbsp;";
									
									goodstr+="<img src='"+basePath+"images/contacts/management/wfj_return_03.png' /></li></ul></div></div>";
									goodstr+="<div class='wfj12_010_product'><div class='wfj12_010_width'><div class='xq' id=''>";
									var goodsList = mapp;
							             for ( var j = 0; j < mapp[cash[0]].length; j++) {
										var goodbill=mapp[cash[0]][j];
										goodstr+="<table width='94%'><tr>";
										goodstr+="<td width='10%'><img src='"+basePath+"images/contacts/management/product_list_01.png' /></td>";
										goodstr+="<td width='65%'>"+goodbill[1]+"</td>"
										goodstr+="<td width='25%'>"+goodbill[3]+"箱</td>";
										goodstr+="</tr></table>";
							              }
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
		

      
       //点击评论按钮，跳转的界面
      function check(sta){
			document.location.href=basePath+"ea/consignee/ea_receipt.jspa?stype=seller&stype3=&tupn=confirm&sta="+sta+"&user="+user;
		
			}
	
		
    	$(document).ready(function(e) {
    	
    	//提交表单
			
			
			//中联园区头部
           $(".wfj_top").attr("style","height:"+$(window).height()*0.090+"px;line-height:"+$(window).height()*0.08+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
           
           
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
			 */
			/* 
		    if($(".wfj12_010_content").css("height").split("px")[0]<$(window).height()){
				$(".wfj12_010_content").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.89+"px;");
			} */ 

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