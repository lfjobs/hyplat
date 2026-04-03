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
<html class="plhtml">
<head>
<title>评论详情</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<link type="text/css" rel="stylesheet" href="<%=basePath%>/css/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/style_platform.css">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
	<script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/toucher.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
</head>

<body>
<div class="mengban"></div>
<header>
    <ul class="pub_top1">
        <li style="width: 10%;">
            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
        </li>
        <li style="width: 80%;">评论</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content">
    <div class="comt_text">
        <div class="comt_head">
            <div>
            	<c:if test="${object1[0] == null }">
            		<img src="<%=basePath%>images/WFJClient/PersonalJoining/headimage.png" alt="">
            	</c:if>
            	<c:if test="${object1[0] != null }">
            		<img src="<%=basePath%>${object1[0] }" alt="">
            	</c:if>
            </div>
        </div>
        <div class="comt_txt">
            <h4>${object1[1] }</h4>
            <h5>${fn:substring(object1[2], 0, 10)}</h5>
            <p>${object1[3] }</p>
            <div class="comt_article">
                <img src="<%=basePath%>${object2[1] }">
                <div limit="50">
                    ${object2[3] }
                </div>
                <div class="clearfix"></div>
            </div>
            <div class="comt_zan">
                <div>
                	<c:if test="${object2[2]=='1' }">
                		<img class="guanzhu" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-zan.png" style="display:none" alt="">
                    	<img class="guanzhu2" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-zan2.png" style="display:block" alt="">
                	</c:if>
                	<c:if test="${object2[2]!='1' }">
                		<img class="guanzhu" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-zan.png" alt="">
                    	<img class="guanzhu2" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-zan2.png" alt="">
                	</c:if>
                    <span id="zan">${object1[4] }</span>
                    <div class="clearfix"></div>
                </div>
                <div class="comment_content">
                    <img class="" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico-pinglun.png" alt="">
                    <span>评论</span>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
            </div>
            <hr style="margin: 0 auto;border-top: 1px solid #ddd;width: 100%;">
            <h4 style="width: 100%;font-size: 0.7rem;color: #666;padding: 0.3rem 0 0.5rem 0;">全部评论</h4>
            <ul class="comt_com2">
            </ul>
        </div>
        <div class="clearfix"></div>
    </div>

</div>
<div class="input-box">
	<header>
	    <ul class="">
	        <li style="width: 10%;display: none;" >
	            <img src="<%=basePath%>/images/WFJClient/PersonalJoining/left_jt.png">
	        </li>
	        <li style="width: 100%;">评论详情</li>
	        <li style="width: 10%;display: none;"></li>
	        <div class="clearfix"></div>
	    </ul>
	</header>
	<div style="position:relative;">
		<button onclick="myFunction2()" style="left: 1rem;color: #fff;background: #FF6507;">取消</button><button onclick="myFunction()">发表</button>
	</div>
    <textarea placeholder="优质评论将会被优先显示" name="content"></textarea>
</div>
<footer>
    <div class="news-det_ipy comment_ipt">
        <input type="text" placeholder="写评论">
    </div>
    <div class="clearfix"></div>
</footer>
<script>
	var basePath="<%=basePath%>";
	var ppid = "${ppid }";
	var goodsid = "${goodsid }";
	var pcid = "${pcid }";
	var pagenumber = 0;
	var height = 0;
	var t;
	var pagecount;
	var type='${type}';
	var ccompanyId='${ccompanyId}';
	
	var miniSystemJudge = '${miniSystemJudge}';
	
	
	$(".pub_top1").find("li").eq(0).click(function(){
		if(type=='web' || type=='time'){
			document.location.href=basePath+"ea/industry/ea_informationDetails.jspa?ppId="+ppid+"&type="+type+"&ccompanyId="+ccompanyId+"&miniSystemJudge="+miniSystemJudge;
		}else{
			document.location.href = basePath +"ea/wfjplatform/ea_newdetail.jspa?ppid="+ppid+"&goodsid="+goodsid;
		}
	});
	//type为1 页面首次进入调用的ajax  type为2评论之后重新调用的ajax
	function loaded(type){
		clearTimeout(t);
		if(type==2){
			pagenumber=1;
		}else{
			pagenumber += 1;
		}
		//评论显示
		var url = basePath+"ea/wfjplatform/sajax_ea_AjaxComment.jspa?typeNews=1&pcid="+pcid+"&ppid="+ppid;
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{						
				"pageForm.pageNumber":pagenumber,
				"pageForm.pageSize":15
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var list = member.list;
				pagecount=member.pagecount;	
				pagenumber=member.pagenumber;	
				if (list == null) {
					return;
				} else {
					var htl = new Array();
					for ( var int = 0; int < list.length; int++) {
						var comment = list[int];
						if(comment[0]==""){
							comment[0] = "images/WFJClient/PersonalJoining/headimage.png";
						}
						htl.push("<li><div class='comt_com_head'><div><img src='"+basePath+comment[0]+"'></div></div><div class='comt_com_txt'><div class='praise' id='"+comment[6]+"'>");
	                    if(comment[8]=="1"){
	                    	htl.push("<img class='zan' style='display:none' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan.png'><img class='zan2' id='ispraise' style='display:block' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan2.png'>");
	                    }else{
	                    	htl.push("<img class='zan' style='display:block' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan.png'><img class='zan2' style='display:none' src='"+basePath+"/images/WFJClient/PersonalJoining/ico-zan2.png'>");
	                    }
	                    htl.push("<p>"+comment[4]+"</p></div><span>"+comment[1]+"</span><span>回复:</span><span></span><p style='font-size: 100%;'>"+comment[7]+"</p><h5>"+comment[2].substring(0,19)+"</h5><p>"+comment[3]+"</p></div>");
	                    htl.push("<div class='clearfix'></div></li><hr style='margin: 15px auto;border-top: 1px solid #ddd;width: 100%;'>");
					}
					if(pagenumber == pagecount){
						htl.push("<div style='text-align:center;font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.04+"px;color:#373737;'>已显示全部</div>");
					}
					$(".comt_com2").append(htl.join(""));
					getHeight(".comt_com2",".content","loaded(1)");
				}
			},
			error:function(data){
				alert("获取评论失败");
			}
		});
	}
	
</script>
<script>
    jQuery.fn.limit=function(){
	    var self = $("[limit]");
	    self.each(function(){
		    var objString = $(this).text();
		    var objLength = $(this).text().length;
		    var num = $(this).attr("limit");
		    if(objLength > num){
		    $(this).attr("title",objString);
		    objString = $(this).text(objString.substring(0,num) + "...");
		    }
		    })
	    }
	    $(function(){
	    $("[limit]").limit();
    })
</script>
<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.84+"px;overflow: auto;");
        $("footer").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		$(".comt_article").find("div").find("img").css("display","none");
        $(".input-box").css("top",$(window).height()*0.08+"px");
		$(".input-box").css("height",$(window).height()*0.92+"px");
		$(".input-box textarea").css("height",$(window).height()*0.81+"px");
        loaded(1);
        //赞
        $(document).on("click",".praise",function (){
			if($(this).find(".zan2").attr("id")!="ispraise"){
				var pcid = $(this).attr("id");
		        if($(this).find(".zan").css("display")=="block") {
	                $(this).find(".zan").css("display","none");
				    $(this).find(".zan2").css("display","block");
				    var zan=$(this).find("p").text();
				    zan=eval(zan)+1;
				    $(this).find("p").text(zan);
				}
				else {
					return false;
				}
				//点赞
				var url = basePath+"ea/wfjplatform/sajax_ea_ajaxPrise.jspa?pcid="+pcid+"&ppid="+ppid;
				$.ajax({
					url : url,
					type : "get",
				});
			}
		}); 
        $(".guanzhu").click(function(){
            $(".guanzhu").css("display","none");
            $(".guanzhu2").css("display","block");
            var  guanz=$("#zan").text();
            guanz++;
            $("#zan").text(guanz);
            var url = basePath+"ea/wfjplatform/sajax_ea_ajaxPrise.jspa?pcid="+pcid+"&ppid="+ppid;
			$.ajax({
				url : url,
				type : "get",
			});
        });

        //评论弹框
        $(".comment_ipt input").click(function(){
            $("footer").css("display","none");
            $(".mengban").show();
            $(".input-box").show();
            $(".input-box textarea").focus();
        });
        $(".comment_content").click(function(){
        	$("footer").css("display","none");
            $(".mengban").show();
            $(".input-box").show();
            $(".input-box textarea").focus();
        });
        //关闭弹框
        $(".mengban").click(function(){
            $(".mengban").css("display","none");
            $(".input-box").css("display","none");
            $("footer").show();
        });
        $(".input-box textarea").keyup(function(){
            var t = $(".input-box textarea");
            if (t.val() == "") {
                $(".input-box button").eq(1).attr("style","color:#fff;background:#ddd;");
            }
            else{
                $(".input-box button").eq(1).attr("style","color:#fff;background:#FF6507;");
            }
        });
    });
    //点击发表
        function myFunction(){
            $(".mengban").css("display","none");
            $(".input-box").css("display","none");
            $("footer").show();
            
            var content = $(".input-box textarea").val();
            var url = basePath+"ea/wfjplatform/sajax_ea_newsComment.jspa?typeNews=1&goodsid="+goodsid+"&content="+content+"&pcid="+pcid+"&ppid="+ppid+"&type="+type;
			$.ajax({
				url : url,
				type : "get",
				async: false,
			});
			$(".input-box textarea").val("");
			$(".comt_com2").empty();
			loaded(2);
        };
		function myFunction2(){
	        $(".mengban").css("display","none");
	        $(".input-box").css("display","none");
	        $("footer").show();
        };
</script>

</body>
</html>