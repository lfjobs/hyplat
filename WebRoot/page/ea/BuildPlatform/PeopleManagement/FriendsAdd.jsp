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
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>北京天太世统科技有限公司</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>

</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var pagenumber=0;
var pagecount=0;
var t;
var search;
</script>
<body>
<header>
   <ul>
       <li style="width: 10%;">
           <a href="<%=basePath%>/people/manage/ea_teamAdd.jspa?"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">好友添加</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="search_frd_">
            <div class="search_frd">
                <input type="search">
                <div class="search_">
                    <img src="<%=basePath %>images/BuildPlatform/ico-search.png" alt="">
                    <p>搜索 </p>
                </div>
            </div>
        </div>
        <div class="con">
            <ul class="mil_frd">
            </ul>
        </div>
    </div>
</div>

<script>
    $(document).ready(function(){
    	load();
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");    
        $(".con").css("height",$(window).height()*0.92-$(".search_frd_").height()-5+"px");


        /*搜索*/
        $(".search_frd input").focus(function(){
            $(".search_frd .search_").hide();
        });
        $(".search_frd input").blur(function(){
            if( $(".search_frd input").val()==""){
                $(".search_frd .search_").show();
            }else{
                $(".search_frd .search_").hide();
            }
        });
        $(".search_frd .search_").click(function(){
            $(".search_frd .search_").hide();
            $(".search_frd input").focus();
        });
        
        $(".search_frd input").bind("propertychange input", function(){
        	$(".mil_frd").empty();
        	search=$(this).val();
        	pagenumber=0;
    		load();
        });
    	$(document).on("click",".mil_frd li",function(){
    		window.location.href=basePath+"people/manage/ea_manualFriendsAdd.jspa?staff.staffID="+$(this).find("input").val();
    	});
        
    });//加载结束
    function getHeight(){
   	 t=setTimeout("getHeight()",200);
   	 if($(".last").length>0){
   		 if($(".last").offset().top+$(".last").height()-$("header").height()*4<$(window).height()){
   			 if(pagenumber<pagecount){
   				 load();
   			 }
   		}		 
   	 }
   }

   function load(){
   	 clearTimeout(t);
   	 pagenumber++;
   	 var url=basePath+"people/manage/sajax_ajaxFansFriends.jspa?";
   	 $.ajax({
   		url:url,
   		type:"get",
   		data:{"pageForm.pageNumber":pagenumber,"search":search},
   		async:false,
   		success:function (data){
   			var member=eval("("+data+")");
   			var pageForm=member.pageForm;
   			var str=new Array();
   			if(pageForm!=null&&pageForm.recordCount>0){
   				pagenumber=pageForm.pageNumber;
   				pagecount=pageForm.pageCount;
   				var list=pageForm.list;
   				$(".mil_frd li").removeClass("last");
   				for(var i=0;i<list.length;i++){
   					var s=list[i];
   					if(i==list.length-1){
   						str.push('<li class="last">');
   					}else{
   						str.push('<li>');
   					}
   					str.push('<input type="hidden" value="'+s[1]+'"/>');
   					if(s[2]==null||s[2]==''){
   						str.push('<img src="'+basePath+'images/BuildPlatform/xiaotu.png" alt="">')
   					}else{
   						str.push('<img src="'+basePath+s[2]+'" alt="">')
   					}
   				 	
               		str.push('<h4>'+s[0]+'</h4></li>')
   				}				
   			}
   			$(".mil_frd").append(str.join(""));
   			getHeight();
   		}
   	 });
   }
</script>
</body>
</html>