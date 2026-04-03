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
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/font_load.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/platform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/platform/kehu.css">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    <title>服务客户</title>
</head>

<body>
   <header class="com_head">
        <a href="<%=basePath%>ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid=${sccid}" target="_self" class="back" id="ret"></a>
        <h1>抢客户</h1>
    </header>
    <div class="wrap_page">
        <div class="tab_menu clearfix">
            <a href="javascript:;" class="menu_cur"><i class="menu_no_ico"></i>待抢列表</a>
            <a href="javascript:;" ><i class="menu_yes_ico"></i>已抢列表</a>
        </div>
        <div class="content content2">
	        <div class="menu_con menu_no_list">
	        </div>
        
	        <div class="menu_con menu_yes_list">
	        </div>
		</div>
    </div>
    <div class="overlay">
       <!--抢人成功 弹窗 开始-->
        <div class="popup success">
            <div class="popup_top">
                <img src="<%=basePath%>images/WFJClient/VipCenter/robuser//popup_yes.png" alt="">
                <span>恭喜你抢人成功！</span>
            </div>
            <a href="javascript:;" class="popup_btn">确 定</a>
        </div>
        <!--抢人成功 弹窗 结束-->
        <!--抢人失败 弹窗 开始-->
        <div class="popup failed">
            <div class="popup_top">
                <img src="<%=basePath%>images/WFJClient/VipCenter/robuser//popup_no.png" alt="">
                <span>抱歉没有抢到！</span>
            </div>
            <a href="javascript:;" class="popup_btn">再 接 再 厉</a>
        </div>
        <!--抢人失败 弹窗 结束-->
        <!--抢单-级别不够 开始-->
         <div class="popup no_level">
            <div class="popup_top">
                <img src="<%=basePath%>images/WFJClient/VipCenter/robuser/no_level.png" alt="">
                <span>您的会员等级不够！</span>
            </div>
            <div class="clearfix">
                <a href="javascript:;" class="no_level_btn popup_btn later_btn">稍后再说</a>
                <a href="javascript:;" class="no_level_btn upgrade_btn">立即升级</a>
            </div>
        </div>
        <!--抢单-级别不够 结束-->
    </div>
    <script>
    	var sccid="${sccid}";
	    var basePath = "<%=basePath%>";
		var t;
		var pagenumber = 0;
		var pagecount;
		var t1;
		var pagenumber1 = 0;
		var pagecount1;
	    $(document).ready(function(){
	        $(".content").attr("style","margin-top:2.2rem;height:"+$(window).height()*0.92+"px;overflow: auto;");
			loaded();
			loaded1();
	    });
	</script>
    <script>
    function getHeight1(){
    	height = parseInt(Math.abs($(".menu_yes_list").height()-($(window).height()-$(".menu_yes_list").offset().top)));
    	t=setTimeout("getHeight1()", 5000);
    	if(height<$(".content").height()){
    		if(pagenumber1<pagecount1){
    			loaded1();
    		}	
    	}
    }
    //没有上级的用户
    	function loaded(){
    		clearTimeout(t);
    		pagenumber += 1;
    		
    		var url = basePath + "ea/vipcenter/sajax_ea_ajaxrobWfjUser.jspa";
        	$.ajax({
    		url : url,
    		type : "get",
    		async : false,
    		dataType : "json",
    		data:{						
    			  "pageForm.pageNumber":pagenumber,
    		},
    		success : function (data) {
    			var member = eval("(" + data + ")");
    			var pageForm = member.pageForm;
    			var pagm = member.pagm;
    			var htl = new Array();
    			
    			if(pagm =="1"){//会员等级不够
    				$(".overlay").addClass("active");
    				$(".no_level").css("display","block");
    			}
    			if (pageForm == null ) {
    				htl.push("<div class='without'>");
		            htl.push("<img src='"+basePath+"images/WFJClient/VipCenter/robuser/no_img.png' alt=''>");
		            htl.push("<div class='without_text'>需要服务的客户已抢完<br>请时时关注消息<br>抢服务客户等于收财源!</div></div>");
    				$(".menu_no_list").append(htl.join(""));
    				return;
    			} else {
    				pagecount=pageForm.pageCount;	
    				pagenumber=pageForm.pageNumber;
    				var list = pageForm.list;
    				if(list==null){
        				return;
        			}
    				
    				for ( var int = 0; int < list.length; int++) {
    					var user = list[int];
    					if(user[2]==null||user[2]==""){
    						user[2]="images/WFJClient/VipCenter/robuser/default-img.png";
    					}
    					 if(user[3]==null ||user[3]==""){
    						user[3]=user[1];
    					} 
    					var ss = user[1].substring(3,8);
    					user[1] = user[1].replace(ss,"*****");
    					htl.push("<div class='kh_box clearfix'><div class='kh_himg img_wrap'>");
    					htl.push("<img src='"+basePath+user[2]+"' alt=''></div>");
    					htl.push("<div class='kh_text'><div class='kh_name'>"+user[3]+"</div>");
    					htl.push("<div class='kh_tell'>"+user[1]+"</div></div>");
    					htl.push("<a href='javascript:;' id='"+user[0]+"' class='kh_btn'></a></div>");
    				}
    				$(".menu_no_list").append(htl.join(""));
    				getHeight(".menu_no_list",".content","loaded()");
    				}		 
	    		},
	    		error:function(data){
	    			alert("获取项目失败");
	    		}
	    	});
    	}
    	
    	//已抢到的客户列表
    	function loaded1(){
    		clearTimeout(t1);
    		pagenumber1 += 1;
    		
    		var url = basePath + "ea/vipcenter/sajax_ea_WfjUser.jspa";
        	$.ajax({
    		url : url,
    		type : "get",
    		async : false,
    		dataType : "json",
    		data:{						
  			  "pageForm.pageNumber":pagenumber1,
  			},
    		success : function (data) {
    			var member = eval("(" + data + ")");
    			var pageForm = member.pageForm;
    			var htl = new Array();
    			
    			if (pageForm == null ) {
    				htl.push("<div class='without'>");
		            htl.push("<img src='"+basePath+"images/WFJClient/VipCenter/robuser/yes_img.png' alt=''>");
		            htl.push("<div class='without_text'>您还没有抢到的客户<br>请时时关注消息<br>抢服务客户等于收财源!</div></div>");
    				$(".menu_yes_list").append(htl.join(""));
    				return;
    			} else {
    				pagecount1=pageForm.pageCount;	
    				pagenumber1=pageForm.pageNumber;
    				var list = pageForm.list;
    				if(list==null){
    				return;
    			}
    				for ( var int = 0; int < list.length; int++) {
    					var user = list[int];
    					if(user[2]==null||user[2]==""){
    						user[2]="images/WFJClient/VipCenter/robuser/default-img.png";
    					}
    					 if(user[3]==null ||user[3]==""){
    						user[3]=user[1];
    					} 
    					var ss = user[1].substring(3,8);
    					user[1] = user[1].replace(ss,"*****");
    					
    					htl.push("<div class='kh_box clearfix'><div class='kh_himg img_wrap'>");
    					htl.push("<img src='"+basePath+user[2]+"' alt=''></div>");
    					htl.push("<div class='kh_text'><div class='kh_name'>"+user[3]+"</div>");
    					htl.push("<div class='kh_tell'>"+user[1]+"</div></div>");
    					htl.push("<a class='kh_arrR'></a></div>");
    				}
    				$(".menu_yes_list").append(htl.join(""));
    				getHeight1();
    				}		 
	    		},
	    		error:function(data){
	    			alert("获取项目失败");
	    		}
	    	});
    	}
    </script>
    <script >
     var basePath = "<%=basePath%>";
    $(document).on('click','.kh_btn',function(){
    	 var sccid = $(this).attr("id");
		 $.ajax({
	        	url:basePath + "ea/vipcenter/sajax_ea_getWfjUser.jspa?sccid="+sccid,
	        	type:"post",
	        	async : false,
	        	dataType : "json",
	        	success:function(data){	
	        	var da=	eval("(" + data + ")")
	        	  var number=da.pageForm;
	        		if(number =="0"){
	        		$(".popup").hide();
	                $(".success").show();
	                $(".overlay").addClass("active");
	                $(".menu_yes_list").empty();
	                pagenumber1=0;
	                loaded1();
	        		}else{
	        			$(".popup").hide();
		                $(".failed").show();
		                $(".overlay").addClass("active");
	        		}
					 
	        	},error:function(data){
	        		alert("抢人失败！");
	        	}
        });
		$(this).parent().css("display","none");
    });
    </script> 
    	
    <script>
        $(".kh_box:odd").css("background","#f7f7f7");//间隔背景色

        $(".tab_menu a").click(function(){
            $(this).addClass("menu_cur").siblings().removeClass();
            var n=$(".tab_menu a").index($(this));
            if(n==0){
                $(".menu_no_list").show();
                $(".menu_yes_list").hide();
            }else{
                $(".menu_no_list").hide();
                $(".menu_yes_list").show();
            }
        })
        //点击抢按钮
       /*  $(".kh_btn").click(function(){
            $(".popup").hide();
            $(".success").show();
            $(".overlay").addClass("active");
        }); */
        $(".popup").click(function(e){e.stopPropagation();});
        //点击弹窗遮罩层关闭弹窗
        $(".overlay").click(function(){
                $(this).removeClass("active").find(".popup").hide();
        })
        //点击弹窗确定和再接再厉
        $(".popup_btn").click(function(){
            $(".overlay").removeClass("active").find(".popup").hide();
        })
        //点击已抢列表跳转页面
        $(".menu_yes_list .kh_box").click(function(){
            var href=$(this).find(".kh_arrR").attr("href");
            window.location.href=href;
        })
        //点击会员升级
        $(".upgrade_btn").click(function(){
        	var url = basePath + "ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform";
        	document.location.href=url;
        })
    </script>
    
    <script>	
	$(document).ready(function(){
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
		if(isAndroid==true){		
        	var obj = document.getElementById("ret");
       		obj.setAttribute("href","#");
       		obj.setAttribute("onclick", "retAndroid()");    
       	}
    });
    
      	//安卓返回
	function retAndroid(){
		try{       		
			Android.callAndroidjianli();
		}catch(err){
			$(".back").removeAttr("onclick");
			$(".back").attr("href",basePath+"ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid="+sccid);
		}
	}
	</script>
</body>
</html>