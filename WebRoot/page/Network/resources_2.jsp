<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
	<link href="<%=basePath%>css/contacts/Network/style12.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>
<title>客户会员</title>
    <script type="text/javascript">
    var basePath='<%=basePath%>';
    var pagenumber = "${pageForm.pageNumber}";
    var select = 2;
    
    var chooseCusType  = "${chooseCusType}";	
    
    function switck(type,param){
           document.location.href=basePath+"ea/resourse/ea_show.jspa?chooseCusType="+chooseCusType+"&type="+type+"&parameter="+param;

    } 
    
  
    //查询
    function search(){
     var type =  $("#typers").text();
     var parameter = $(".wfj_width").find("input").attr("value");
     if(parameter=="搜索昵称、ID或手机号"){
			      parameter="";
	}
       switck(type,parameter);

    }
    
    //跳转到名片
    $(".card").live("click",function(){
     var account = $(this).find(".ac").text();
      var type =  $("#typers").text();
      
      if(type=="1"){
       
        document.location.href = basePath+"ea/perinfor/ea_getPageHomeData.jspa?user="+account;
      }else{
        var title = $("#wfj1").text();
        if(title=="普通客户"){
          document.location.href = basePath+"ea/perinfor/ea_getPageHomeData.jspa?user="+account;
        }else{
           document.location.href = basePath+"ea/industry/ea_CompanyCard.jspa?user="+account;
        }
      } 
    
    });
    
    	
    </script>


</head>

<body class="bgcolorFFF" onload="loaded()">
	<div class="wfj12_022">
    <input type="hidden" id="user" value="${param.user}"/>
     <input type="hidden" id="type" value="${param.type}"/>
    	<!--中联园区头部-->
   
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:open('<%=basePath%>/ea/resourse/ea_findconWealth.jspa','_self');" target="_self"><img src="<%=basePath%>images/contacts/Network/wfj_return_01.png" /></a></li>
            	<li>客户</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/Network/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        
        <!--中联园区搜索 end-->
        <div class="wfj_search">
        	<div class="wfj_width">
            	<ul>
                	<li><input type="text" id="inp" value="搜索昵称、ID或手机号"  onfocus="if(this.value=='搜索昵称、ID或手机号'){this.value='';}"  onblur="if(this.value==''){this.value='搜索昵称、ID或手机号';}" /></li>
                    <li><img src="<%=basePath%>images/contacts/Network/search999.png"  onclick="search()"/></li>
                </ul>
            </div>
        </div>
        <!--中联园区搜索 end-->
          <span style="display:none;" id="typers">${type}</span>
        <div class="wfj12_022_top_title">
           
        	<ul>
        	<li id="wfj2" value="1"><s:if test='chooseCusType=="6"'>普通客户</s:if><s:else>个人<s:if test='chooseCusType=="8"'>粉丝</s:if><s:else>会员</s:else></s:else></li>
            	<li id="wfj1" value="2"><s:if test='chooseCusType=="6"'>VIP客户</s:if><s:else>公司<s:if test='chooseCusType=="8"'>粉丝</s:if><s:else>会员</s:else></s:else></li>
            	
            </ul>
        </div>    
         <input type="hidden" id="pageNumber" value="${pageForm.pageNumber}"/>
                 <input type="hidden" id="pageCount" value="${pageForm.pageCount}"/>
         <table id="table1" style="display:none;">
                   
                			<tr class="card">
                            	<td width="18%" class="wfj12_022_leftimg" ><img src="<%=basePath%>images/contacts/Network/defaultsmall.png" style="border-radius:150px;"/></td>
                            	<td class="wfj12_022_name"></td>
                            	<td class="wfj12_022_post"></td>
                            	<td width="20%" align="center" class="wfj12_022_rightimg"><img src="<%=basePath%>images/contacts/Network/wfj_infophone_01.png"/>
                            	<span class="ac" style="display:none;"></span>
                            	</td>
                            </tr>
                          
          </table>	
      <div class="wfj12_022_content">
        	<div class="wfj12_022_hidden">
        	
       <div id="wrapper">
	<div id="scroller">
		<div id="scroller-pullDown"><img id="wfj_sx" src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
			<span id="down-icon" class="icon-double-angle-down pull-down-icon"></span>
			<span id="pullDown-msg" class="pull-down-msg sx">下拉刷新</span>
		</div>
        	         
                  <div class="wfj12_022_person che03">
             
                 
                  <c:forEach items="${pageForm.list}" var="order">
                    <table>
                           
                			<tr class="card">
                            	<td width="18%" class="wfj12_022_leftimg" >
                            	<c:if test='${order[2]!=null&&order[2]!="(null)"}'>
                            	<img src="<%=basePath%>${order[2]}" style="border-radius:150px;"/>
                            	</c:if>
                            	<c:if test='${order[2]==null||order[2]=="(null)"}'>
                            	<img src="<%=basePath%>images/contacts/Network/defaultsmall.png" style="border-radius:150px;"/>
                            	</c:if>
                            	</td>
                            	<c:if test='${order[0]!=null}'>
                            	<td class="wfj12_022_name" align="center">${order[0] }</td> 
                            	</c:if>
                            	<c:if test='${order[0]==null}'>
                            	<td class="wfj12_022_name" align="center">${order[3] }</td> 
                            	</c:if>
                            	
                            	<td class="wfj12_022_post">${order[4] }</td>
                            	<td width="18%" align="center" class="wfj12_022_rightimg"><img src="<%=basePath%>images/contacts/Network/wfj_infophone_01.png"/>
                            	<span class="ac" style="display:none;">${order[3]}</span>
                            	</td>
                            </tr>
                	</table>
                </c:forEach>
                </div>
                
           <div id="scroller-pullUp"><img src="<%=basePath %>images/ea/finance/BenDis/fej_jiazai.gif" width="20px" height="20px">
		<span id="up-icon" class="icon-double-angle-up pull-up-icon"></span>
		<span id="pullUp-msg" class="pull-up-msg sx">加载更多</span>
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
			$("#wrapper").css("top",$(".wfj_top").height()+$(".wfj_search").height()+$(".wfj12_022_top_title").height()+($(window).height()*0.013)+"px");
            
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
					search();
					myScroll.refresh();
				}
			});
			
			myScroll.on("slideUp",function(){
				console.log("maxScrollY:"+this.maxScrollY);
				console.log("y:"+this.y);
				 var type =  $("#typers").text();
                 var parameter = $(".wfj_width").find("input").attr("value");
                  if(parameter=="搜索昵称、ID或手机号"){
			          parameter="";
                 	}
				if(this.maxScrollY - this.y > 40){
					console.log("up");
					var pn=parseInt($("#pageNumber").val())+1;
					if(pn<=parseInt($("#pageCount").val())){
 
						
						var url=basePath+"/ea/resourse/sajax_ea_show.jspa";
						$.ajax({
							url : url,
							type : "get",
							async : false,
							dataType : "json",
							data:{
							   type:type,
							   parameter:parameter,
							   "pageForm.pageNumber":pn,
							   scroll:"scroll",
							   chooseCusType:chooseCusType
							},
							success : function (data) {
								var member = eval("(" + data + ")");
								var pageForm = member.pageForm;
								var list=pageForm.list;
								$("#pageNumber").val(pageForm.pageNumber);
								for ( var i = 0; i < list.length; i++) {
								 $(".che03").append($("#table1").clone(true).attr("id","table"+select));
								 $("#table"+select).show();
                            	var img = list[i][2];
                            	if(img==null||img=="null"||img==""||img=="(null)"){
                            	   img="images/contacts/Network/defaultsmall.png";
                            	}
								 
							     $("#table"+select).find("tr").eq(0).find("td").eq(0).find("img").attr("src",basePath+img);
							     $("#table"+select).find("tr").eq(0).find("td").eq(1).text(list[i][0]);
							     $("#table"+select).find("tr").eq(0).find("td").eq(2).text(list[i][4]);
							     $("#table"+select).find("tr").eq(0).find("td").find("span").text(list[i][3]);
							    
									select++;
			$(".wfj12_022_content").find("table").attr("style","border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
			$(".wfj12_022_content").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_022_content").find("table").find(".wfj12_022_leftimg").attr("style","padding:"+$(window).height()*0.01+"px 0;position:relative;left:2%;");
			$(".wfj12_022_content").attr("style","width:"+$(".wfj12_022").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(".wfj12_022_top_title").height()-$(window).height()*0.02)+"px;");
			$(".wfj12_022_content").attr("style","width:"+$(".wfj12_022").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(".wfj12_022_top_title").height()-$(window).height()*0.02)+"px;");
		    $(".wfj12_022_name").attr("style","font-weight:bold;font-size:"+$(window).height()*0.022+"px;height:auto;vertical-align:middle;text-align:left;");
		    $(".wfj12_022_post").attr("style","font-size:"+$(window).height()*0.025+"px;height:auto;vertical-align:middle;text-align:center;");
			
	                       
								}
					myScroll.refresh();				
							},error:function(data){
								alert("获取会员信息失败");
							}
						}); 
					}
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
			
			if($(window).width()>$(window).height()){
				$(".wfj12_022").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
			}else{
				$(".wfj12_022").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			}
			
			//中联园区搜索
			$(".wfj_search").find("div").attr("style","margin:"+$(window).height()*0.012+"px auto;height:"+$(window).height()*0.05+"px; border-radius:"+$(window).height()*0.01+"px;");
			$(".wfj_search").find("li").eq(0).attr("style","width:88%;margin-left:2%;");
			$(".wfj_search").find("input").attr("style","height:"+$(window).height()*0.038+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj_search").find("li").eq(1).attr("style","width:8%;margin-right:2%;");
			$(".wfj_search").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
			
			
			$(".wfj12_022_top_title").attr("style","height:"+$(window).height()*0.07+"px;line-height:"+$(window).height()*0.07+"px;");
			$(".wfj12_022_top_title").find("li").attr("style","font-size:"+$(window).height()*0.025+"px;");
			var type = "${type}";
			var index = 0;
			if(type=="2"){
			   index = 1;
			}
			$(".wfj12_022_top_title").find("li").eq(index).attr("style","font-size:"+$(window).height()*0.025+"px;color:#F74C31;");
			$(".wfj12_022_content").find("table").attr("style","border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
			$(".wfj12_022_content").find("table").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_022_content").find("table").find(".wfj12_022_leftimg").attr("style","padding:"+$(window).height()*0.01+"px 0;position:relative;left:2%;");
			$("#table1").attr("style","display:none;");
			$(".wfj12_022_content").attr("style","width:"+$(".wfj12_022").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(".wfj12_022_top_title").height()-$(window).height()*0.02)+"px;");
			$(".wfj12_022_content").attr("style","width:"+$(".wfj12_022").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj_search").height()-$(".wfj12_022_top_title").height()-$(window).height()*0.02)+"px;");
		    $(".wfj12_022_name").attr("style","font-weight:bold;font-size:"+$(window).height()*0.022+"px;height:auto;vertical-align:middle;text-align:left;");
		    $(".wfj12_022_post").attr("style","font-size:"+$(window).height()*0.025+"px;height:auto;vertical-align:middle;text-align:center;");
			
			
			
		    var h = $(".wfj12_022_person").find("table").height()*$(".wfj12_022_person").find("table").length;
			$("#scroller").css("width",$(window).width()+"px");
			if(h < $(".wfj12_022_content").height()){
				$("#scroller").css("height",$(".wfj12_022_content").height()+"px");
			}else{

 				$(".wfj12_022_hidden").css("width",parseInt($(".wfj12_022_content").width()+17)+"px");
			} 
			
			
			
			
			
			$(".wfj12_022_top_title").find("li").click(function(){
			 var parameter = $(".wfj_width").find("input").attr("value");
			 if(parameter=="搜索昵称、ID或手机号"){
			      parameter="";
			 }
				if($(this).attr("value")=="2"){
				
					$(".wfj12_022_top_title").find("li").css("color","#666");
					$(this).css("color","#F74C31");
                    $("#typers").text(2);
					
					if($(".wfj12_022_company").height()>$(".wfj12_022_content").height()){
						$(".wfj12_022_hidden").attr("style","width:"+parseInt($(".wfj12_022_content").width()+17)+"px;height:"+$(".wfj12_022_content").height()+"px;");
					}else{
						$(".wfj12_022_hidden").attr("style","width:"+parseInt($(".wfj12_022_content").width())+"px;height:"+$(".wfj12_022_content").height()+"px;");
					}
					switck("2",parameter)
				}else if($(this).attr("value")=="1"){
					$(".wfj12_022_top_title").find("li").css("color","#666");
					$(this).css("color","#F74C31");

					if($(".wfj12_022_person").height()>$(".wfj12_022_content").height()){
						$(".wfj12_022_hidden").attr("style","width:"+parseInt($(".wfj12_022_content").width()+17)+"px;height:"+$(".wfj12_022_content").height()+"px;");
					}else{
						$(".wfj12_022_hidden").attr("style","width:"+parseInt($(".wfj12_022_content").width())+"px;height:"+$(".wfj12_022_content").height()+"px;");
					}
					$("#typers").text(1);
					
					switck("1",parameter)
				}
				
			
			});
				
        });
	</script>
</body>
</html>
