<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
	<script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
	<script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/login/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/news_list.css">

    <script type="text/javascript" src="<%=basePath%>js/WFJClient/swiper-3.3.1.min.js"></script>
    <title>${goodname }</title>
</head>
<body>
<c:if test="${param.main ne '1'}">
	<header class="com_head">
        <a onclick="javascript: window.history.go(-1);return false;" class="back"></a>
        <h1>${param.cate eq '慈善捐赠'||param.cate eq '使用帮助'||param.cate eq '关于数字地球'?param.cate:'实时新闻'}</h1>
    </header>
</c:if>
    <div class="wrap_page" id="listnews">
		<c:if test="${param.query ne 'query'}">
        <div class="banner_wrap swiper-container ">
            <div class="swiper-wrapper">
            	<c:forEach items="${list }" var="li" varStatus="l">
            		<c:choose>
						<c:when test="${li[6] == '会员分享' }">
							<c:if test="${param.main ne '1'}">
							<a href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
								<span class="banner_tit">${li[0] }</span>
							</a>
							</c:if>
							<c:if test="${param.main eq '1'}">
								<a target="_parent" href="<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${li[5] }&ccompanyId=${li[8] }&type=time&miniSystemJudge=03" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
									<span class="banner_tit">${li[0] }</span>
								</a>
							</c:if>
						</c:when>
						<c:otherwise>
							<c:if test="${param.main ne '1'}">
						 	<a href="<%=basePath%>ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=${li[8] }&search=${search }&goodsid=${li[3] }" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
						 		<span class="banner_tit">${li[0] }</span>
						 	</a>
							</c:if>
							<c:if test="${param.main eq '1'}">
							<a target="_parent" href="<%=basePath%>ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=${li[8] }&search=${search }&goodsid=${li[3] }" class="swiper-slide banner_img" style="background-image:url(<%=basePath%>${li[2]})">
								<span class="banner_tit">${li[0] }</span>
							</a>
							</c:if>
						</c:otherwise>
					</c:choose>
            	</c:forEach>
            </div>
            <div class="swiper-pagination"></div>
        </div>
		</c:if>
        <div class="news_list">
        </div>
    </div>
		
	<script type="text/javascript">
	
	var ccompanyId = "${ccompanyId }";
	var search = "${search}";
	var height = 0;
	var t;
	var pagenumber = 0;//第几页
	var pagecount;
	var basePath = "<%=basePath%>";
	var query = "${param.query}";
	function getHeight(){
		height = parseInt(Math.abs($(".news_list").height()-($(window).height()-$(".news_list").offset().top)));
		t=setTimeout("getHeight()", 200);
		if(height<$(window).height()){
			if(pagenumber<pagecount){
				loaded();
			}	
		}
	}
	function loaded () {
		clearTimeout(t);
		pagenumber += 1;
		var url=basePath+"ea/wfjshop/sajax_ea_AjaxNewsList.jspa?ccompanyId="+ccompanyId;
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{						
			  "pageForm.pageNumber":pagenumber,
				searchc:"${param.searchc}",
				fbr:"${param.fbr}",
				start:"${param.start}",
				end:"${param.end}",
				cate:"${param.cate}",
				query:"${param.query}"
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var pageForm = member.pageForm;

				if (pageForm == null) {
					return;
				} else {
					pagecount=pageForm.pageCount;	
					pagenumber=pageForm.pageNumber;
					var newslist=pageForm.list;
					var newstr="";
					var num = 1;
					if(query=="query"){
						num = 0;
					}
					for(var i = 0; i < newslist.length; i++){
						var news=newslist[i];
						if(news[1].hours<10){
							news[1].hours = "0"+news[1].hours;
						}
						if(news[1].minutes<10){
							news[1].minutes = "0"+news[1].minutes;
						}
						var time = news[1].hours+":"+news[1].minutes;
						if(news[1].month < 9){
							if(news[1].date<10){
								var date=(news[1].year+1900)+"-0"+(news[1].month+1)+"-0"+news[1].date;
							}else{
								var date=(news[1].year+1900)+"-0"+(news[1].month+1)+"-"+news[1].date;
							}
						}else{
							var date=(news[1].year+1900)+"-"+(news[1].month+1)+"-"+news[1].date;
						}
						var name = news[9]==null ? "":news[9];
						if(news[2]!=null&&pagenumber > num) {

							var t = news[2].split(".")[0] + "small." + news[2].split(".")[1];
							newstr += "<a href='###' class='new_box clearfix' id='" + news[3] + "'>";
							newstr += "<input type='hidden' id='ppid' value='" + news[5] + "' />";
							newstr += "<input type='hidden' id='types' value='" + news[6] + "' />";
							newstr += "<input type='hidden' id='companyId' value='" + news[7] + "' />";
							newstr += "<input type='hidden' id='ccompanyId' value='" + news[8] + "' />";
							newstr += "<img src='" + basePath + t + "' class='new_img' alt='' onerror='IsExist(this)' data-url='" + news[2] + "'>";
							newstr += "<div class='new_text'>";
							newstr += "<div class='new_tit'>" + news[0] + "</div>";
							newstr += "<div class='new_state'><span class='new_name'>" + name + "</span>";
							newstr += "<span class='new_time' data-date='" + date + "' data-time='" + time + "'></span>";
							newstr += "</div></div></a>";

						}
					}
					if(pagenumber == pagecount){
						newstr+="<div class='all_load'>已展示全部</div>";
					}
					$(".news_list").append(newstr);
					
					 //当前时间
		            var curDate=getNowFormatDate();
		            $(".new_time").each(function(){
		                var d=$(this).attr("data-date");
		                var t=$(this).attr("data-time");
		                if(curDate==d){
		                    $(this).text(t);
		                }else{
		                    $(this).text(d);
		                }
		            });
				         //获取当前时间（格式：2017-01-23）
		            function getNowFormatDate() {
		                var day = new Date();
		                var Year = 0;
		                var Month = 0;
		                var Day = 0;
		                var CurrentDate = "";
		                //初始化时间
		                Year = day.getFullYear(); 
		                Month = day.getMonth() + 1;
		                Day = day.getDate();
		                //Hour = day.getHours();
		                // Minute = day.getMinutes();
		                // Second = day.getSeconds();
		                CurrentDate += Year + "-";
		                if (Month >= 10) {
		                    CurrentDate += Month + "-";
		                } else {
		                    CurrentDate += "0" + Month + "-";
		                }
		                if (Day >= 10) {
		                    CurrentDate += Day;
		                } else {
		                    CurrentDate += "0" + Day;
		                }
		                return CurrentDate;
		            }
					
					getHeight();
				}
			},
			error:function(data){
				console.log("获取项目失败");
			}
		});	
	}
    function IsExist(obj) {
        var imgurl = basePath + $(obj).attr("data-url");
        var ImgObj = new Image(); //判断图片是否存在
        ImgObj.onload=function(){
            return $(obj).attr("src",imgurl);
        }
        ImgObj.onerror=function () {
            return $(obj).attr("src",basePath + "/images/ea/production/forum/reportAnError.png");
        }
        ImgObj.src = imgurl;

    }
	</script>
	
	
	
	<script type="text/javascript">
		var main = "${param.main}";
	$(document).ready(function(e) {
		if(main=="1") {
			$("body").css("margin-top", "-3rem");
		}

		loaded();
		// 新闻详情
		$(document).on("click",".new_box",function() {
		//$(".new_box").click(function() {
			var types=$(this).find("#types").val();
			var goodsid = $(this).attr("id");
			var ppid = $(this).find("#ppid").val();
			var companyId = $(this).find("#companyId").val();
			var ccompanyId = $(this).find("#ccompanyId").val();
			if(types=="会员分享"){
              if(main!=""&&main!=null){


				window.open(basePath + "/ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03","_parent");
			  }else{

				  document.location.href = basePath + "/ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03";

			  }
			}else if(types=="新闻"){

				var share = $(this).find(".share").text();
				if(main!=""&&main!=null) {
					window.open(basePath
							+ "ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=" + ccompanyId + "&search=" + search + "&goodsid=" + goodsid, "_parent");

				}else{
					document.location.href = basePath
							+ "ea/wfjshop/ea_getWFJnews.jspa?ccompanyId=" + ccompanyId + "&search=" + search + "&goodsid=" + goodsid;


				}


				}
			
		});
		var mySwiper = new Swiper('.swiper-container', {
            direction: 'horizontal',
            autoplay: 3000,
            loop: true,
            pagination: '.swiper-pagination', // 分页器
        });
   	});
    </script>
    
   
		
	
</body>
</html>