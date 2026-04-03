<%@page import="hy.ea.bo.Company"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Company company=(Company)session.getAttribute("company");
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8" />
<base href="<%=basePath%>">
<meta name="author" content="www.frebsite.nl" />
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no" />
<title>预约教练</title>

<link type="text/css" rel="stylesheet" href="<%=basePath%>/js/ea/ddsr/jQuery.mmenu-master/demo/css/demo.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath%>/js/ea/ddsr/jQuery.mmenu-master/src/css/jquery.mmenu.all.css" />

<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/ea/ddsr/jQuery.mmenu-master/src/js/jquery.mmenu.min.all.js"></script>
<script src="<%=basePath%>/js/ea/ddsr/dropDownRefresh/js/ai.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/ddsr/dropDownRefresh/js/slip-min.js" type="text/javascript"></script>
<style type="text/css">
/* The drop-down refresh hints */
*{
	padding:0px;
	margin:0px;
}
ul,li{
	list-style-type: none;			
}
html{
	-webkit-text-size-adjust:100%;
	-ms-text-size-adjust:100%;
	-webkit-user-select: none;
}
header{
	width: 100%;
	height: 40px;
	background: -webkit-gradient(linear,left top,left bottom,from(#6f85a2),to(#6f85a2));
	text-shadow: 1px 1px rgba(0, 0, 0, .2);
	line-height: 40px;
	text-align: center;
	color: #F3F3F3;
	font-size: 19px;
	font-weight: bold;
	font-family: helvetica;
	position:absolute;
}
#bar_list_div{
	background-color:#FFFFFF;
}
#bar_list_div li{
	display:block;
	/* height:39px;
	line-height:39px; */
	padding:0px 12px 0px 12px;
}
#bar_list{
	position: absolute;
	top:40px;
	left:0px;
	width:100%;
	overflow:hidden;
	background-color:#FFFFFF;
}
.up_down{
	width:100%;
	height:60px;
	/*background-color:#f8f8f8;	*/
	position:relative;
}
.up_down_arrow{
	width:40px;
	height:40px;
	position:absolute;
	top:9px;
	left:70px;
	-webkit-transform-style: preserve-3d;
	-webkit-transition: 300ms;
	background-image:url(img/up_arrow.png);
	background-size: 40px 80px;
	background-repeat: no-repeat;
	
}
.loading{
	background-position: center -40px;	
	-webkit-transform:rotate(0deg) translateZ(0);
	-webkit-animation-name:loading;
	-webkit-animation-duration:2s;
	-webkit-animation-iteration-count:infinite;
	-webkit-animation-timing-function:linear;
}
@-webkit-keyframes loading {
	from { -webkit-transform:rotate(0deg) translateZ(0); }
	to { -webkit-transform:rotate(360deg) translateZ(0); }
}
.up_down_text{
	width:150px;
	/*background-color:#0099FF;*/
	position:absolute;
	height:60px;
	line-height:60px;
	text-align:center;
	top:50%;
	left:50%;
	margin-top:-30px;
	margin-left:-75px;
	color:#878787;
}
#container{
	border-top: 1px solid #ededed;
}
/* container */
#container{width:315px;margin:16px auto;overflow: auto;text-align: center;margin-top: 5px;}
#container ul{width:100px;list-style:none;float:left;margin-right:3px;margin-left: 3px;}
#container ul li{margin-bottom:0px;padding: 0px;}
#container ul li img{width:100px;height: 150px;}
.instructions {text-align: center;margin-top: 0px;}
</style>

</head>
<body>
		<div id="page">
			<div class="header">
				<a href="#menu"></a>
			</div>
			<div class="content" style="padding: 0px;" >
					<header id="fullscreen-button">下拉刷新</header>
					<div id="bar_list">
						<div id="bar_list_div">
					    	<div class="up_down"><div id="up_arrow" class="up_down_arrow"></div><div id="up_text" class="up_down_text">下拉刷新</div></div>
					        <div id="container" >
										<ul class="col"></ul>
										<ul class="col"></ul>
										<ul class="col" style="margin-right:0;"></ul>
							</div>
					      </div>
					</div>
			</div>
			<nav id="menu">
				
			</nav>
		</div>
</body>
	

<script type="text/javascript">
	var pageNumber=1;
	var notoken=0;
	var pageCount=0;
	var organizationID="";

// 你可能会看到ai.js这个javascript文件，这个文件和slip.js无任何依赖关系，ai.js只是一些常用的函数如：ai.i，这些函数也有非常详细的注释。
window.addEventListener('load', function(){
	ai.hideUrl();
	
	var bar_list_div = ai.i("bar_list_div"),
		bar_list     = ai.i("bar_list"),
		minus        = ai.ovb.ios() && ai.ovb.safari() && !ai.ovb.ipad() ? -20 : 40,
		up_arrow	 = ai.i("up_arrow"),
		up_text      = ai.i("up_text"),
		bar_list_ul  = ai.i("container");
		up_ing       = false,
	  down_ing       = false;
	bar_list.style.height =  ai.wh() - minus +"px";
	
	function update() {
		if(this.xy > 10 && up_ing == false){
			up_ing = true;
			down_ing = false;
			up_arrow.style['webkitTransitionDuration'] = '300ms';	
			up_arrow.style['webkitTransform'] = 'rotate(-180deg)';
			up_text.innerHTML = "松开即可刷新";
			this.up_range = 0;
		}else if(this.xy < 10 && down_ing == false){
			down_ing = true;
			up_ing = false;
			up_arrow.style['webkitTransitionDuration'] = '300ms';	
			up_arrow.style['webkitTransform'] = 'rotate(0deg)';
			up_text.innerHTML = "下拉刷新";
			this.up_range = 60;
		}
	}
	function loading() {
		if(this.up_range == 0){
			var that = this;
			up_text.innerHTML= 'Loading...';
			up_arrow.style['webkitTransitionDuration'] = '0ms';	
			up_arrow.className += ' loading';
			setTimeout(function () {
				/* now = new Date(); 
				var newli = '<li>这是 '+now.getHours()+' : '+now.getMinutes()+' : '+now.getSeconds()+'&nbsp;&nbsp;加载的新内容</li>';
				 var bar_list_ul_html = bar_list_ul.innerHTML; 
				bar_list_ul.innerHTML = newli + bar_list_ul_html; */
				if(pageCount!=0&&pageNumber>pageCount){
					alert("已到尾页!");
					up_arrow.style['webkitTransform'] = 'rotate(0deg)';
					up_arrow.className = 'up_down_arrow';
					that.up_range = 60;
					that.refresh();
					return false;
				}
				if(notoken){
					up_arrow.style['webkitTransform'] = 'rotate(0deg)';
					up_arrow.className = 'up_down_arrow';
					that.up_range = 60;
					that.refresh();
					return false;
				}
				loadMeinv()//** 查询教练信息 */
				up_arrow.style['webkitTransform'] = 'rotate(0deg)';
				up_arrow.className = 'up_down_arrow';
				that.up_range = 60;
				that.refresh();
			}, 1000);
		}
	}
	var slipjs_yuxiang = slip('px', bar_list_div, {
			up_range: 60,
			 moveFun: update,
			  endFun: loading
	});	
	
	function loadMeinv(){
		if(pageCount!=0&&pageNumber>pageCount){
			return false;
		}
		if(notoken){
			return false;
		}
		notoken=1;
		var url="<%=basePath%>/ea/appointmentbymicroletter/sajax_ea_getListOfCoachByAjax.jspa?"
		$.ajax( {
                    type : 'POST',
                    url : url,
                     data: {"pageForm.pageNumber":pageNumber,"rand":Math.random(),"departmentPost.organizationID":organizationID,"search":"search"},
                    dataType : 'json',
                    async: true,
                    success : function(json){
                    	var json = eval("(" + json + ")"); 
                    	var pageForm=json.pageForm;
                    	if(pageForm!=null){
                    		var list=pageForm.list;
                    		pageCount=pageForm.pageCount;
                    		for ( var i = 0; i < list.length; i++) {
								var html = "";
								html = "<li><img   src = '<%=basePath%>/"+list[i][9]+"' onerror=this.src='<%=basePath%>/images/ea/ddsr/moren.jpg' onclick=javascript:window.location.href='<%=basePath%>/ea/appointmentbymicroletter/ea_getListCoachReservationRecord.jspa?ddsrcoach.coacKey="+list[i][2]+"' ><div class='instructions'><span>"+list[i][3]+"</span><div> </li>";
								$minUl = getMinUl();
								$minUl.append(html);
							}
                    	}
						pageNumber++;
						notoken=0;
						up_arrow.style['webkitTransform'] = 'rotate(0deg)';
						up_arrow.className = 'up_down_arrow';
						that.up_range = 60;
						that.refresh();
                    },
                    error : function(json){
                    	notoken=0;
                    	up_arrow.style['webkitTransform'] = 'rotate(0deg)';
						up_arrow.className = 'up_down_arrow';
						that.up_range = 60;
						that.refresh();
                    }
         });
	}
	loadMeinv();
	/* $(window).on("scroll",function(){
		$minUl = getMinUl();
		if($minUl.height() <= $(window).scrollTop()+$(window).height()){
		//当最短的ul的高度比窗口滚出去的高度+浏览器高度大时加载新图片
			loadMeinv();
		}
	}) */
	function getMinUl(){//每次获取最短的ul,将图片放到其后
		var $arrUl = $("#container .col");
		var $minUl =$arrUl.eq(0);
		$arrUl.each(function(index,elem){
			if($(elem).height()<$minUl.height()){
				$minUl = $(elem);
			}
		});
		return $minUl;
	}
	
	// 菜单
  //menu_list为json数据
        //parent为要组合成html的容器
 function showall(menu_list, parent) {
      for (var menu in menu_list) {
          //如果有子节点，则遍历该子节点
          if (menu_list[menu].children.length > 0) {
             //创建一个子节点li
              var li = $("<li></li>");
              //拼接字符：<a href='http://xfy142305.iteye.com/blog/1902502#' target='_blank' rel='nofollow'>             
              // var link = (menu_list[menu].Url=="#")?$("<a href="http://xfy142305.iteye.com/blog/" target="_blank" rel="nofollow">                    
            var link = $("<a href='#' title='"+menu_list[menu].organizationID+"'  rel='nofollow'>"+menu_list[menu].organizationName+"</a>") ;                    
             //将a附加到li里，并马上添加一个空白的ul子节点，并且将这个li添加到父亲节点中
              $(li).append(link).append("<ul></ul>").appendTo(parent);
              //将空白的ul作为下一个递归遍历的父亲节点传入
              showall(menu_list[menu].children, $(li).children().eq(1));
          }else {
          	//如果该节点没有子节点，则直接将该节点li以及文本创建好直接添加到父亲节点中
             //拼接字符：<a href="http://xfy142305.iteye.com/blog/1902502#" target="_blank" rel="nofollow">                    
             var link = $("<a href='#' title='"+menu_list[menu].organizationID+"' rel='nofollow'>"+menu_list[menu].organizationName+"</a>") ;                  
              $("<li></li>").append(link).appendTo(parent);
          }
      }
  }
	var url="<%=basePath%>/ea/appointmentbymicroletter/sajax_ea_getAllCOrganizationListByPID.jspa?"
		$.ajax( {
                    type : 'get',
                    url : url,
                    dataType : 'json',
                    async: false,
                    success : function(json){
                    	var json = eval("(" + json + ")"); 
                    	var showlist = $("<ul></ul>");
                    	var link = $("<a href='#'  rel='nofollow'><%=company.getCompanyName()%></a>") ;                  
              			$("<li></li>").append(link).appendTo(showlist);
		                showall(json, showlist);
		                console.log(showlist)
		                $("nav#menu").append(showlist);
                    },
                    error : function(json){
                    }
         }); 
	$("nav#menu").mmenu();
	
	$("a[title]",$("nav#menu")).click(function(){
		organizationID=$(this).attr("title");
		pageNumber=1;
		pageCount=0;
		var $arrUl = $("#container .col");
		$arrUl.each(function(index,elem){
			$(elem).html("")
		});
		loadMeinv();
	})	
});
</script>	
</html>
