<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>${param.content}代理平台</title>
    
	 <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style2016.9.18.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>

  </head>
  <body >
<header>
    <ul>
        <li style="width: 10%;">
            <a id="returnClick"><img src="<%=basePath%>/images/WFJClient/Platform/left_jt.png"></a>
        </li>
        <li style="width: 80%;">${param.content}代理平台</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
    <input type="hidden" id="addre" value="${param.addre}">
</header>
<div class="content content2">
    <div class="search_pt">
        <input type="text" id="content" >
        <div class="sear">搜 索</div>
    </div>
    <ul class="lypingtai">
   
    </ul>
</div>

<script>
var basePath="<%=basePath%>";
var content = '${content}';
	var standard ;
	var money  ;
	var ppid   ;
	var addre = $("#addre").val();
	var goodsName = "${productDesign.goodsName }";
	var pagenumber = 0;
	var height = 0;
	var t;   
	var pagecount='';
	var p;
	p=content.substring(0,2);
	if(p=="省级"){
		standard="a";
		ppid='p20170220ZVZR76B88M0000000018';
	}else if(p=="县级"){
		standard="b";
		ppid='p20170220ZVZR76B88M0000000019';
	}else if(p=="镇级"){
		standard="c";
		ppid='p20170220ZVZR76B88M0000000020';
	}
	console.log(standard);
	function onj(obj){
	 var goodsname=$(obj).find("#goodsname").val();
	 var ppids=$(obj).find("#ppid").val();
	console.log(standard);
	document.location.href = basePath+"ea/wfjshop/ea_doodsDetail.jspa?goodsname="+goodsname+"&ppid="+ppid+"&standard="+standard+"&money="+money+"&addre="+addre+"&companyId=company201009046vxdyzy4wg0000000025&ppids="+ppids;
	}
        function getHeight(){
            height = parseInt(Math.abs($(".lypingtai").height()-($(window).height()-$(".lypingtai").offset().top)));
            t=setTimeout("getHeight()", 200);
            if(height<$(window).height()){
                if(pagenumber<pagecount){
                    ajax();
                }
            }
	 }
function ajax(){


		clearTimeout(t);
		pagenumber += 1;
		
		var typeId="${typeId}";
        var sousuo = $("#content").val();
		var url = basePath+"ea/wfjplatform/sajax_ea_getPlatform.jspa?goodsid=2&type=qureyy&content="+content+"&standard="+standard+"&sousuo="+sousuo;
		
		console.log(url)
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dateType : "json",
			data:{						
				"pageForm.pageNumber":pagenumber,
			},
			success : function (data) {
				var member = eval("("+data+")");
				var pageForm = member.pageForm;
				var p;
				if (pageForm == null) {
					return;
				} else {
					var htl =new Array();
					var list = pageForm.list;
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					for(var i = 0;i<list.length;i++){
						var search = list[i];
						p=search[0].substring(0,2);
						htl.push(" <li onclick='onj(this)''> <input type='hidden' id='goodsname' value="+search[0]+"> <input type='hidden' id='ppid' value="+search[1]+"><div class='left'>"+p+"</div><div class='txt'>"+search[0]+"代理资格  </div> </li>");
					}
					$(".lypingtai").append(htl.join(""));
					getHeight();
				}
				
			},
			error:function(data){
				alert("获取项目失败");
			}
		});


}
/*function loaded(){
	var content = $("#content").val();
	var types="${types}";
		if(content ==null || content==""){
			ajax();
		}
			clearTimeout(t);
			pagenumber += 1;
			var url = basePath+"ea/wfjplatform/sajax_ea_getPlatformB.jspa?content="+content+"&addre="+addre+"&standard="+standard;
			console.log(url)
			$.ajax({
				url : url,
				type : "post",
				async : false,
				dateType : "json",
				data:{						
					"pageForm.pageNumber":pagenumber,
				},
				success : function (data) {
					var member = eval("("+data+")");
					var pageForm = member.pageForm;
					var p;
					if (pageForm == null) {
						return;
					} else {
						var htl =new Array();
						var list = pageForm.list;
						pagenumber = pageForm.pageNumber;
						pagecount = pageForm.pageCount;
						for(var i = 0;i<list.length;i++){
							var search = list[i];
							if(search[2]==null){
								search[2]="";
							}else{
								search[2]="-"+search[2];
							}
							p=search[0].substring(0,2);
							
	                    htl.push(" <li onclick='onj(this)''> <input type='hidden' id='goodsname' value="+search[0]+"> <input type='hidden' id='ppid' value="+search[1]+"><div class='left'>"+p+"</div><div class='txt'>"+search[0]+"代理平台  </div> </li>");					}
						$(".lypingtai").append(htl.join(""));
						getHeight()
					}
				},
				error:function(data){
					alert("获取项目失败");
				}
			});
			
		}*/
    $(document).ready(function(){
    	 $("#returnClick").click(function() {history.go(-1)});
    ajax();
    $("#content").bind("propertychange input",function(){
		
		$(".lypingtai").empty();
        pagenumber=0;
        ajax();
	});
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
        $(".lypingtai").css("margin-top",$(".search_pt").height()+15+"px");

        $(".search_pt .sear").click(function(){
            $(this).hide();
            $(".search_pt input").focus();
        });
        $(".search_pt input").blur(function(){
            if($(".search_pt input").val()==""){
                $(".search_pt .sear").show();
            }
        });

       
        $(".lypingtai li").each(function(){
            //依次选取颜色
            var string = "#eda473,#8bdfd2,#8992c8,#89a13a,#2dab4b,#eb4f38,#826e19,#4d4abf ,#FF501A,#68B83A,#78CF7F,#53BBCB,#ED837B,#C5E6F9,#1F75BB,#B41717";   //原始数据
            var array = string.split(",");
            var string1 = "#fff4ec,#f0fffd,#e9ecff,#f5ffd6,#e 2ffe9,#ffeae7,#fffcf0,#e9e8ff ,#F7C2B1,#B2E893,#C3EEC7,#C4EBF1,#F5C1BD,#EEF5F9,#AAD0F1,#E4A0A0";   //原始数据
            var array1 = string1.split(",");        //转化为数组
            var iii=Math.round(Math.random()*(array.length-1));
            var col = array[iii];  //随机抽取一个值
            var col1 = array1[iii];
            $(this).find(".left").attr("style","border:2px solid "+col+";color:"+col+";background:"+col1+";");
        });



    });
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>