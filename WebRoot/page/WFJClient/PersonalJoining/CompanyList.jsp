<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>${p=param.etype=="elu"?"驾校联盟":((param.flag=="activity")?"选择活动公司":((param.flag=="el")?"选择驾校":(param.industryType=="汽车驾校")?"e路快车":"中联园博览"))}</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="<%=basePath%>js/WFJClient/setHtmlFont.js" type="text/javascript"></script>
<script src="<%=basePath%>js/WFJClient/jquery.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/district.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/base.css"/>
<script type="text/javascript" src="//api.map.baidu.com/api?v=1.0&type=webgl&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
<style>

</style>
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
var h=0;
var h1=0;
var industryType = "${param.industryType}";
var etype = "${param.etype}";
var enroll = "${param.enroll}"
var ccompanyId="${param.ccompanyId}"
var flag = "${param.flag}"
var activityType = "${param.activityType}"
var isMobile = "${param.isMobile}";
$(function(){
   if(etype=="elu"){

       $(".search_wrap").hide();
       $(".list_wrap").css("padding-top","0rem");


   }
   if(isMobile=="true"){
       $("header .back").hide();
       $(".filtrate_wrap").hide();
       $(".list_wrap").css("padding-top","2.2rem")
//       $(".com_head").hide();
//       $(".search_wrap").css("top","0");
//       $(".wrap_page").css("margin-top","0");
   }

});
</script>
<body>
    <header class="com_head">
        <a onclick="javascript: window.history.go(-1);return false;" class="back"></a>
        <h1>${param.etype=="elu"?"驾校联盟":((param.flag=="activity")?"选择活动公司":((param.flag=="el")?"选择驾校":(param.industryType=="汽车驾校")?"e路快车":"中联园博览"))}</h1>
        <a href="javascript:void(0)" class="head_R"></a>

    </header>
    <div id="allmap"></div>
    <%--<div id="container" style="padding: 2.5rem 0.2rem; width: 100%;">
        <iframe src="<%=basePath%>page/WFJClient/PersonalJoining/baiduMap.jsp" style="width: 100%;"></iframe>
    </div>--%>
    <div class="wrap_page list_wrap">
       <div class="search_wrap">
           <div class="search_box">
                <input type="text" class="search" >
                <span class="search_overly"><i class="com_search"></i>搜索</span>
            </div>
           <%--<a class="claim" href="<%=basePath%>/ea/industry/ea_claimCompanyList.jspa">认领公司</a>--%>
            <div class="filtrate_wrap clearfix">
                <a href="###" class="filtrate_nav General">筛选行业</a>
                <a href="###" class="filtrate_nav Regional">区域选择</a>
                <a href="###" class="filtrate_nav">销量优先</a>
            </div>
       </div>
       <div class="grade-eject">
                    <ul class="grade-w" id="gradew"></ul>
                    <ul class="grade-t" id="gradet"></ul>
                    <ul class="grade-s" id="grades"></ul>
        </div>
        <div class="content">
	        <div class="c_list_wrap">
	        </div>
	    </div>
    </div>
    
    
<script>
var height = 0;
var t;
var pagenumber = 0;//第几页
var pagecount;
var acquiesceLoGo = "images/WFJClient/PersonalJoining/logo@2x.png";
var acquiesceIntro = "images/WFJClient/PersonalJoining/picture@2x.png";

var accuracy = "";//东经
var dimension = "";//北纬
var city = "";//所在城市

if(industryType=="汽车驾校"){
    var map = new BMapGL.Map("allmap");
    var point = new BMapGL.Point(116.331398,39.897445);
    $(".content").attr("style", "padding-top:4.2rem;");
    $(".wrap_page").attr("style", "margin: 0;padding: 0;");
    $("#allmap").css("width", "96%").css("height", "25%").css("margin", "2.5rem 0.3rem 0 0.3rem").css("font-family", "微软雅黑");
    obtain();
}else {
    $(".search_wrap").css("top","2.16rem");
}

function obtain(){

    try{
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            console.log("安卓");
            var collection = Android.callgetLocal();//调用安卓接口
            if(collection!="-1"){
                var a = collection.split(",");
                city = a[0];//所在城市
                accuracy = a[1];//东经
                dimension = a[2];//北纬
                $(".head_R").text(city);
            }else{
                $(".head_R").text("未知");
            }
            loaded();
        } else if (isiOS == true) {
            console.log("IOS");
            var url= "func=" + 'calliosMapInfo';
            window.webkit.messageHandlers.Native.postMessage(url);

        }

    }catch(error){

        getCurrentPosition();
    }

}
function getCurrentPosition() {//调用浏览器定位服务
        // 百度地图API功能
        /*var map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398,39.897445);
        map.centerAndZoom(point,12);

        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                var mk = new BMap.Marker(r.point);
                map.addOverlay(mk);
                map.panTo(r.point);

                accuracy = r.point.lng;
                dimension = r.point.lat;

                loaded();

            } else {
                alert('failed'+this.getStatus());
            }
        },{enableHighAccuracy: true})*/


    map.centerAndZoom(point,12);
    map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
    var geolocation = new BMapGL.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMapGL.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            accuracy = r.point.lng;
            dimension = r.point.lat;

            loaded();
        }
        else {
            alert('failed' + this.getStatus());
        }
    });
}
function calliosMapInfo(name){
    if(name!="-1"){
        var a = name.split(",");
        city = a[0];//所在城市
        accuracy = a[1];//东经
        dimension = a[2];//北纬
        $(".head_R").text(city);
    }else{
        $(".head_R").text("未知");
    }
    loaded();
}

function getHeight(){
	height = parseInt(Math.abs($(".c_list_wrap").height()-($(window).height()-$(".c_list_wrap").offset().top)));
	t=setTimeout("getHeight()", 200);
	if(height<$(window).height()){
		if(pagenumber<pagecount){
			loaded();
		}	
	}
}
var ssss= new XMLHttpRequest();
function loaded () {
	clearTimeout(t);
	ssss.abort();
	pagenumber += 1;
		var industryType="${industryType }";
		var companyname=$(".search").val();
	 	var companyAddr="${companyAddr }";
		var url=basePath+"/ea/industry/sajax_ea_getAjax.jspa?industryType="+industryType+"&companyAddr="+companyAddr+"&search="+companyname+"&ccompanyId="+ccompanyId+"&flag="+flag+"&activityType="+activityType;
		ssss = $.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			data:{						
              "pageForm.pageNumber":pagenumber,
              "accuracy":accuracy,
              "dimension":dimension
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var pageForm = member.pageForm;		
				var companystr = new Array();	
				if(pageForm!=null&&pageForm.recordCount>0){
					var companylist=pageForm.list;
					pagecount=pageForm.pageCount;	
					pagenumber=pageForm.pageNumber;					
					for(var i = 0; i < companylist.length; i++){
						var company=companylist[i];	
						var indus;
						if(company[7]==null||company[7]=="/"){
							company[7]="&nbsp;";
							indus = " style='background:none';";
						}else {
							indus = "";
							if(company[7].substr(company[7].length-1,1)=="/" && company[7].length>1){
								company[7]=company[7].substr(0,company[7].length-1);
							}
						}
						if(company[8]==null){
							company[8]="&nbsp;";
						}
						
						companystr.push("<a href='###' class='c_box clearfix'>");
						companystr.push("<input type='hidden' id='companyname' value='"+company[6]+"'/>");
						companystr.push("<input type='hidden' id='ccompanyId'  value='"+company[5]+"'/>");
						companystr.push("<input type='hidden' id='companyId'   value='"+company[0]+"'/>");
			            companystr.push("<img src='"+basePath+(company[4]!=null?company[4]:acquiesceLoGo)+"' alt='' class='c_img'>");
			            companystr.push("<div class='c_text' style='position: relative;'><div class='c_type'><span"+indus+">"+company[7]+"</span></div>");
			            companystr.push("<div class='c_name'>"+company[6]+"</div>");
                        companystr.push("<div style='position: absolute;bottom: 0;right: -2.6rem;width: 4rem;font-size: .5rem;line-height: 1rem;color: #a4a4a4;white-space: nowrap;text-align: right;overflow: hidden;text-overflow: ellipsis;'>");
                        var num = company[9];
                        if(industryType=="汽车驾校") {
                            var a = company[10];
                            var b = company[11];
                            if (a != null && b != null && a != "" && b != "") {
                                var marker = new BMapGL.Marker(new BMapGL.Point(a, b));        // 创建标注
                                map.addOverlay(marker);                     // 将标注添加到地图中
                            }
                        }
                        if(num!=null){
//                            if(num>1000){
//                                num = num/1000;
//                                num = parseFloat(num.toFixed(2));
//                                companystr.push(""+num+"千米");
//                            }else{
//                                companystr.push(""+num+"米");
//                            }

                            if(num<1){
                                num = num*1000;
                                num = parseFloat(num.toFixed(2));
                                companystr.push(""+num+"米");
                            }else{
                                num = parseFloat(num.toFixed(2));
                                companystr.push(""+num+"千米");
                            }
                        }
                        companystr.push("</div></div></a>");
					}
				}
				$(".c_list_wrap").append(companystr.join(""));
				getHeight();
			},
			error:function(data){
				//alert("获取项目失败");
			}
		});
} 
	
	</script>
	
	<script type="text/javascript">
    	$(document).ready(function(e) {
            if(industryType!="汽车驾校"){
                loaded();
            }
			
			//搜索
	        $(".search").focus(function(){
	            $(".search_overly").hide();
	        });
	        $(".search").blur(function(){
	            var val=$(this).val();
	            if(val==''){
	                $(".search_overly").show();
	            }
	        });
	        
	        $(".search").on("input",function(){
	        	$(".c_list_wrap").empty();
	        	pagenumber = 0;
	        	pagecount = 0;
	        	loaded();
	        });
		    $(document).on("click",".General",function(){
		    	//document.location.href=basePath+"page/WFJClient/PersonalJoining/IndustryList.jsp"ea/purchasebids/ea_findbidIndexList.jspa
                document.location.href=basePath+"ea/industry/ea_getAllIndustryList.jspa?industryType="
		    });
			 
			$(document).on("click",".c_box",function(){
				var companyname=$(this).find("#companyname").val();
				var companyid=$(this).find("#companyId").val();
				var ccompanyid=$(this).find("#ccompanyId").val();
                $.ajax({
                    url : basePath + "/ea/android/sajax_ea_addPersonKuaiJie.jspa?companyid="+companyid,
                    type : "post",
                    dataType : "json",
                    success : function (data) {
                        var member = eval("(" + data + ")");
                        console.log(member);
                    }
                });
                if(enroll=="enroll"){
                    document.location.href =  basePath+"st/enroll/ea_getAssociatedMall.jspa?companyID="+companyid;
                }else if(flag=="activity"){
                    if(activityType==0){
                        $.ajax({
                            url: basePath + "ea/lottery/sajax_ea_ajaxIsDraw.jspa?model.companyId=" + companyid,
                            type: "get",
                            async: false,
                            success: function (data) {
                                var str = new Array();
                                var member = eval("(" + data + ")");
                                if (member.model != null) {
                                    open(basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyid + "&industryType=" + industryType + "&etype=" + etype, "_self");
                                }else {
                                    alert("该公司活动结束！");
                                }
                            }
                        });
                    }else if(activityType ==1){
                        var url = basePath + "/ea/bonuspoints/sajax_getPrizeActivity.jspa?ccompanyId=" + ccompanyid;
                        $.ajax({
                            url: url,
                            type: "get",
                            dataType: "json",
                            success: function (data) {
                                var str = new Array();
                                //标识（判断是否有签到活动）
                                var mark = data.flag;
                                if (mark == "sign") {
                                    open(basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyid + "&industryType=" + industryType + "&etype=" + etype, "_self");
                                }else if(mark == 'without'){
                                    alert('该公司没有积分！');
                                }else if(mark == 'notSet'){
                                    alert('该公司没有设置积分数！');
                                }else if(mark == 'notEnough'){
                                    alert('积分不足！');
                                }
                            }
                        });
                    }else {
                        open(basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyid + "&industryType=" + industryType + "&etype=" + etype, "_self");
                    }
                }else if(flag=="el"){
                    open(basePath + "mobile/office/mobileoffice_learningProcess.jspa?flag=el&companyId=" + companyid , "_self");
                }else {
                    open(basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=" + ccompanyid + "&industryType=" + industryType + "&etype=" + etype+"&sc=web", "_self");
                }
            });
			$(".c_box").find("a").click(function(){
				$(".filtrate_wrap").find("a").css("color","#000");
				$(this).css("color","#F00");
			});
        });
      
		//省
		$(".Regional").click(function(){
			$(".grade-eject").toggle();
			$("#gradew").empty();
			$op1 = "<li onclick=\"getCity('全部')\" id='全部' >全部</li>";
			$("#gradew").append($op1);
			var url = basePath
				+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&showType=weifenjin&districtPID="
				+ "&date1=" + new Date();
			$.ajax({
				url : url,
				type : "get",
				async : true,
				dataType : "json",
				success : function (data) {
					var member = eval("(" + data + ")");						
					var distinctlist = member.distinctlist;
					for (var i = 0; i < distinctlist.length; i++) {
						$op = "<li onclick=\"getCity('"+distinctlist[i].districtID+"')\" id='"+distinctlist[i].districtID+"'>"+distinctlist[i].districtName+"</li>";
						$("#gradew").append($op);
					}
				},
				error : function (data) {
					alert("数据获取失败！");
				}
			});
		});
		//市
		function getCity(districtID){
			$("#gradew").find("li").css("color","#000");
			$("#"+districtID).css("color","#f00");
			if(districtID == "全部"){
				var industryType="${industryType }";
		    	var companyname=$(".wfj12_001_top_search").find("input").val();
		    	var url = basePath+"ea/industry/ea_SelCompanyList.jspa?industryType="+industryType+"&companyAddr="+"&search="+companyname;
				document.location.href=url;
			}else{
				$(".grade-t").css("left","33.48%");
			    var companyAddr = $("#"+districtID).text();
			    $("#gradet").empty();
			    $("#grades").empty();
		        var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID="
				+ encodeURI(districtID) + "&date3=" + new Date();
				$.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					success : function (data) {
						var member = eval("(" + data + ")");						
						var distinctlist = member.distinctlist;
						for (var i = 0; i < distinctlist.length; i++) {
							$op = "<li onclick=\"getDistinct('"+distinctlist[i].districtID+"','"+companyAddr+"')\" id='"+distinctlist[i].districtID+"'>"+distinctlist[i].districtName+"</li>";
							$("#gradet").append($op);
						}
					},
					error : function (data) {
						alert("数据获取失败！");
					}
				});
			}
		}
		//区/县
		function getDistinct(districtID,companyAddr){
			$("#gradet").find("li").css("color","#000");
			$("#"+districtID).css("color","#f00");
			companyAddr = companyAddr + $("#"+districtID).text();
		    $(".grade-s").css("left","66.96%");
		    $("#grades").empty();
	        var url = basePath
			+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID="
			+ encodeURI(districtID) + "&date3=" + new Date();
			$.ajax({
				url : url,
				type : "get",
				async : true,
				dataType : "json",
				success : function (data) {
					var member = eval("(" + data + ")");						
					var distinctlist = member.distinctlist;
					for (var i = 0; i < distinctlist.length; i++) {
						$op = "<li onclick=\"getList('"+distinctlist[i].districtID+"','"+companyAddr+"')\" id='"+distinctlist[i].districtID+"'>"+distinctlist[i].districtName+"</li>";
						$("#grades").append($op);
					}
				},
				error : function (data) {
					alert("数据获取失败！");
				}
			});
		}
		//获得区域搜索公司列表
		function getList(districtID,companyAddr){
			$("#grades").find("li").css("color","#000");
			$("#"+districtID).css("color","#f00");
			companyAddr = companyAddr + $("#"+districtID).text();
			var industryType="${industryType }";
			var search="${search }";
		    var url = basePath+"ea/industry/ea_SelCompanyList.jspa?industryType="+industryType+"&companyAddr="+companyAddr+"&search="+search;
			document.location.href=url;
		}

        function back()
        {
            if(etype=="elu"){
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                if(isAndroid==true){
                    Android.finishWeb();
                }else if(isiOS==true){
                    var url= "func=" + 'ioscallback';
                    window.webkit.messageHandlers.Native.postMessage(url);

                }

            }else {
                document.location.href = basePath+"ea/wfjshop/ea_getWFJshops.jspa";
            }


        }




    </script>
</body>
</html>