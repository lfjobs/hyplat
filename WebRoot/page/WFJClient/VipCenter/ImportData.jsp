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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>系统数据客户导入</title>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa" target="_self" class="back" id="ret"></a>
        <h1>系统数据客户导入</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="content">
	    <div class="wrap_page">
	        <section class="att_search_wrap">
	            <div class="att_search_box">
	                <i style="background: url(../../images/WFJClient/VipCenter/att_search.png) no-repeat center;background-size: contain;"></i>
	                <input type="text" class="att_search" placeholder="搜索账号/手机号">
	                <i class="empty_val"></i>
	            </div>
	            <a href="javascript:;" class="client_cancel" style="display:none">取消</a>
	        </section>
	        <section class="att_list">
	        </section>
	        <div class="att_message">
	        	<c:forEach items="${list1 }" var="list" varStatus="st">
	        		<div class="att_del_box">
	        			<div class="att_listbox">
	        				<div class="att_list_img">
	        					<c:if test="${list[3]=='' }"><img src="<%=basePath%>${list[3] }"></c:if>
	        					<c:if test="${list[3]!='' }"><img src="<%=basePath%>images/WFJClient/PersonalJoining/headimage.png"></c:if>
	        				</div>
	        				<div class="att_listtxt">
		        				<span class="att_personname">${list[2] }(<span class="person_num1">${list[0] }</span>)</span>
		        				<span class="att_identity">
									${list[1]}
								</span>
		        			</div>
	        				<div class="client_btn client_accpet" id="${list[4] }">接受</div>
	        			</div>
	        			<div class="att_delbtn" id="${list[4] }">删除</div>
	        		</div>
	        	</c:forEach>
	        </div>
	    </div>
    </div>
    <!--  页面内容 end -->

	<script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
    
	<script>
	var basePath='<%=basePath%>';
	var account="${cuscom.account}";
	var pagenumber = 0;
	var height = 0;
	var t;
	var pagecount;
	var sccid="${sccid}";
	//获得焦点 出现取消按钮
	$(".att_search").focus(function(){
		$(".att_search_wrap").addClass("client_search");
		$(".client_cancel").attr("id","client_cancel");
		$(".att_message").css("display","none");
		$(".client_cancel").css("display","block");
	});
	//取消按钮
	$(document).on("click","#client_cancel",function (){
		$(".att_search_wrap").removeClass("client_search");
		$(".client_cancel").css("display","none");
		$(".att_message").css("display","block");
		$(".att_search").val("");
		$(".att_list").empty();
	});
	//搜索框的×按钮
	$(document).on("click",".empty_val",function (){
		$(".att_search").val("");
		$(".empty_val").hide();
		$(".client_cancel").text("取消").attr("id","client_cancel");
	});
	//输入框值为空 为取消按钮 不为空则为搜索按钮
	$(".att_search").on("input",function(){
        if($(this).val()!=''){
            $(".client_cancel").text("搜索").attr("id","client_search");
            $(".empty_val").show();
        }else{
            $(".client_cancel").text("取消").attr("id","client_cancel");
            $(".empty_val").hide();
        }
    });
	//搜索
	$(document).on("click","#client_search",function (){
		$(".att_message").css("display","none");
		$(".att_list").empty();
		loaded();
	});
	function loaded(){
		var content = $(".att_search").val();
		var url = basePath+"ea/vipcenter/sajax_ea_ajaxData.jspa?user="+content+"&sccid="+sccid;
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list = member.list;
				if (list == null) {
					return;
				} else {
					var htl =new Array();
					for(var i = 0;i<list.length;i++){
						var search = list[i];
						var type="";
						if(search[3]==null){
							search[3]="images/WFJClient/PersonalJoining/headimage.png";
						}
						if(search[4]=="01"){
							search[4]="等待验证";
						}else if(search[4]=="00"){
							search[4]="已添加";
						}else{
							search[4]="添加";
						}
						htl.push("<div class='att_listbox'><div class='att_list_img'><img src='"+basePath+search[3]+"'></div>");
						htl.push("<div class='att_listtxt'><span class='att_personname'>"+search[2]+"(<span class='person_num'>"+search[0]+"</span>)</span>");
						htl.push("<span class='att_identity'>"+search[1]+"</span></div><div class='client_btn client_add' id='"+search[5]+"'>"+search[4]+"</div></div>");
					}
					$(".att_list").append(htl.join(""));
					
					//输入内容实时高亮筛选内容
	                var val = $(".att_search").val();
	                var wrapT = $('.person_num:contains("' + val + '")');
	                if (val != '') {
	                    wrapT.each(function() {
	                        var t = $(this).text();
	                        re = new RegExp(val, "i");
	                        var newstart = t.replace(re, '<span style="color:red">' + val + '</span>');
	                        $(this).html(newstart);
	                    });
	                } else if (val == '') {
	                    wrapT.each(function() {
	                        $(this).find("span").css("color", "#232323")
	                    });
	                }
				}
			}
		});
	}
	//发送粉丝请求
	$(document).on("touchend",".client_add",function (){
		var content = $(".att_search").val();
		var text = $(this).text();
		if(content==account){
			alert("您不可添加自己为粉丝好友!");
		}else{
			if(text=="添加"){
				if(confirm("是否发送该粉丝好友请求")){
					var sccid = $(this).attr("id");
					$(this).html("等待验证");
					var url = basePath+"ea/vipcenter/sajax_ea_ajaxAddFriend.jspa?sccid="+sccid;
					$.ajax({
						url : encodeURI(url),
						type : "get",
						success : function(data) {
							
						},
						error : function(data) {
							alert("失败");
						}
					});
				}
			}
		}
	});
	//接受粉丝请求
	$(document).on("touchend",".client_accpet",function (){
		if(confirm("是否接受该粉丝好友请求?")){
			var sccid = $(this).attr("id");
			$(this).html("已接受");
			$(this).parents(".att_del_box").attr("class","att_del_already");
			$(this).parent().next(".att_delbtn").css("z-index","-1")
			var url = basePath+"ea/vipcenter/sajax_ea_ajaxReceiveFriend.jspa?sccid="+sccid;
			$.ajax({
				url : encodeURI(url),
				type : "get",
				success : function(data) {
					
				},
				error : function(data) {
					alert("失败");
				}
			});
		}
	});
	//拒绝粉丝请求
	$(document).on("touchend",".att_delbtn",function (){
		if(confirm("是否拒绝该粉丝好友请求?")){
			var sccid = $(this).attr("id");
			$(this).parent().css("display","none");
			var url = basePath+"ea/vipcenter/sajax_ea_deleteFriend.jspa?sccid="+sccid;
			$.ajax({
				url : encodeURI(url),
				type : "get",
				success : function(data) {
					
				},
				error : function(data) {
					alert("失败");
				}
			});
		}
	});
	</script>
	<script>
	$(document).ready(function(){
		$(".content").attr("style","overflow:auto;height:"+$(window).height()*0.92+"px;margin-top: 2.16rem;");
		
		Zepto(".att_message").on("swipeLeft",".att_del_box",function(){
            $(this).children(".att_listbox").animate({left:"-2rem"},200,function(){
                $(this).next(".att_delbtn").css("z-index",9)
            });
            
        })
        Zepto(".att_message").on("swipeRight",".att_del_box",function(){
            $(this).children(".att_listbox").css("left",0);
            $(this).find(".att_delbtn").css("z-index","-1")
        })
	});
	</script>
    <script>	
	$(document).ready(function(){
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
       	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
		if(isAndroid==true){		
        	var obj = document.getElementById("ret");
       		obj.setAttribute("href","#");
       		obj.setAttribute("onclick", "retAndroid()");    
       	}else if(isiOS==true){   	
       		var obj = document.getElementById("ret");
       		obj.setAttribute("href","#");
       		obj.setAttribute("onclick", "retIOS()");
       	}
       });
    
       	//安卓，苹果返回
		function retAndroid(){
			try{       		
				Android.callAndroidjianli();
			}catch(err){
				$(".back").removeAttr("onclick");
				$(".back").attr("href",basePath+"ea/consignee/ea_toVipCenter.jspa");
			}
		}
		function retIOS(){
			try{
				calImport('');
			}
			catch(err){    		
				$(".back").removeAttr("onclick");
				$(".back").attr("href",basePath+"ea/consignee/ea_toVipCenter.jspa");
			}    		
		}
	</script>
</body>
</html>