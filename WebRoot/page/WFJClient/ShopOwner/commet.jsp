<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
%>

<!DOCTYPE html>
<html>
  <head lang="en">
    <base href="<%=basePath%>">
    <title>买家评论</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/goodsDetail.css" type="text/css"></link>
	<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/restaurant/layer/jquery.js?v=1.83.min"></script>
	<script src="<%=basePath%>js/restaurant/layer/layer.min.js"></script>
	<script src="<%=basePath%>js/restaurant/layer/extend/layer.ext.js"></script>
	
	

 </head>
<body>
<div class="comment">
	<div class="top" id="top">
        <ul>
           	<li><a href="javascript:history.back(1)"><img src="<%=basePath%>images/wfj_return_01.png" /></a></li>
           	<li class="top_text">评价（<span id="com_num">${qp}</span>）</li>
           	<li><a href="javascript:;"></a></li>
            <div class="clear"></div>
        </ul>
    </div>
    <article>
        <ul class="com_nav">
            <li class="xz"><h5>全部评论</h5><p>${qp}</p></li>
            <li><h5>好评</h5><p>${hp}</p></li>
            <li><h5>中评</h5><p>${zp}</p></li>
            <li><h5>差评</h5><p>${cp}</p></li>
            <li><h5>有图</h5><p>${tp}</p></li>
            <div class="clear"></div>
        </ul>
        <ul class="com_con" id="com_con">

        <c:forEach items="${pageForm.list}" var="list" varStatus="status">
          <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
              <li class="last">
            </c:if> 
             <c:if test="${fn:length(pageForm.list)-1 ne status.index}"> 
              <li>
             </c:if> 
            	<div class="con_title">

            	    <c:if test='${list[1] eq "null" ||list[1] eq ""||list[1] eq null}'>
                	<img class="title_icon" src="<%=basePath%>images/contacts/Network/defaultsmall.png" />
                	</c:if>
                	<c:if test='${list[1] ne "null" &&list[1] ne ""&&list[1] ne null}'>
                	<img class="title_icon" src="<%=basePath%>${list[1]}" />
                	</c:if> 
                    <p class="title_name"><c:if test='${list[3]=="匿名评价"}'>匿名</c:if><c:if test='${list[3]!="匿名评价"}'>${list[2]}</c:if></p>
                </div>
                <div class="con_con">
                	<p class="con_text">${list[5]}</p>
                    <ul class="con_img img${list[10]}">
                        <c:if test="${list[6]!=null}">
                    	<li><img src="<%=basePath%>${list[6]}"></li>
                        </c:if>
                        <c:if test="${list[7]!=null}">
                        <li><img src="<%=basePath%>${list[7]}"></li>
                        </c:if>
                         <c:if test="${list[8]!=null}">
                        <li><img src="<%=basePath%>${list[8]}"></li>
                        </c:if>
                       
                    </ul>
                </div>
                <div style="width:97%;text-align:right;">${fn:substring(list[9],0,19)}</div>
            </li>
            </c:forEach>
        </ul>
    </article>
</div>

<script type="text/javascript">
var basePath="<%=basePath%>";
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};

$(document).ready(function(e) {
    $(".top").css({"height":$(window).height()*0.08+"px","lineHeight":$(window).height()*0.08+"px"});
	$(".top").find("li").find("img").attr("style","height:"+$(window).height()*0.04+"px;");
	$(".top").find("li").eq(1).attr("style","  font-size:"+$(window).height()*0.03+"px;");
	$("article").css("height",$(window).height()*0.92+"px");
	
	getHeight();

	
	//点击每种评价类型
	$(".com_nav li").click(function() {
		$(".com_nav li").removeClass("xz");
		$(this).addClass("xz");
		pagenumber = 1;
		loaded("init");

	});
	
});
    
  function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").length>0){
		if($(".last").offset().top-$(".last").height()*2<=$(window).height()){
		if(pagenumber<pagecount){
			loaded("");
		}	
      }
	}
   }
    
	function loaded(type){
	if(type!="init"){
	   pagenumber += 1;
	}
	
	var evaluation = $(".xz").find("h5").text();
	var url = basePath + "ea/wfjshop/sajax_ea_ajaxfindComBytype.jspa";
		$.ajax({
			url : url,
			type : "get",
			asycn : false,
			dataType : "json",
			data : {
				evaluation : evaluation,
				ppid:"${ppid}",
				"pageForm.pageNumber":pagenumber,
			},
			success : function(data) {
                var member = eval("("+data+")");
                var pageForm = member.pageForm;
                var html = "";
                var obj;
                
                if(pagenumber==1){
                   $(".com_con").html("");
                }
                $(".last").removeClass("last");
              
	                     if (pageForm != null) {

							pagecount = pageForm.pageCount;
							count = pageForm.recordCount;
							pageSize = pageForm.pageSize;
							for ( var i = 0; i < pageForm.list.length; i++) {
								obj = pageForm.list[i];
								if (i == pageForm.list.length - 1) {
									html += "<li class='last'><div class=\"con_title\">";
								} else {
									html += "<li><div class=\"con_title\">";
								}

								if (obj[1] == null || obj[1] == "") {
									html += "<img class=\"title_icon\" src=\""+basePath+"images/contacts/Network/defaultsmall.png\" />";
								} else {
									html += "<img class=\"title_icon\" src=\""+basePath+obj[1]+"\"/>";
								}
								html += " <p class=\"title_name\">";
								if (obj[3] == "匿名评价") {
									html += "匿名";
								} else {
									html += obj[2];
								}

								html += "</p></div><div class=\"con_con\">";
								html += "<p class=\"con_text\">"
										+ obj[5]
										+ "</p><ul class=\"con_img img"+obj[10]+"\">";
								if (obj[6] != null && obj[6] != "") {
									html += "<li><img width=\"65px\" height=\"65px\" src='"+basePath+obj[6]+"'></li>";
								}
								if (obj[7] != null && obj[7] != "") {
									html += "<li><img width=\"65px\" height=\"65px\" src='"+basePath+obj[7]+"'></li>";
								}
								if (obj[8] != null && obj[8] != "") {
									html += "<li><img width=\"65px\" height=\"65px\" src='"+basePath+obj[8]+"'></li>";
								}
								html += "</ul></div><div style='width:97%;text-align:right;'>"
										+ obj[9] + "</div></li>";
							}
						}

						$(".com_con").append(html);
						$(".top").css({
							"height" : $(window).height() * 0.08 + "px",
							"lineHeight" : $(window).height() * 0.08 + "px"
						});
						$(".top").find("li").find("img").attr("style",
								"height:" + $(window).height() * 0.04 + "px;");
						$(".top").find("li").eq(1).attr(
								"style",
								"  font-size:" + $(window).height() * 0.03
										+ "px;");
						$("article").css("height",
								$(window).height() * 0.92 + "px");

					//初始化预览图片
					$(".con_img").each(function() {
						layer.photosPage({
							parent : '.'+$(this).attr("class").substring($(this).attr("class").lastIndexOf("img"))
						});
					});
						getHeight();

					},
					error : function(data) {
						alert("获取评价失败");
					}
				});
	}
</script>

<script>
		;!function () {
				//初始加载即调用，所以需放在ext回调里
				layer.ext = function() {
					$(".con_img").each(function() {
						layer.photosPage({
							parent : '.'+$(this).attr("class").substring($(this).attr("class").lastIndexOf("img"))
						});
					});

				};
		}();
	</script>
</body>
</html>