<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>
<header>
   <ul>
       <li style="width: 10%;">
       </li>
       <li style="width: 100%;">共建平台</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
   <div class="content">
       <div class="gjpt_search" style="height: 50.16px;">
            <input type="search" id="content">
            <div class="gjpt_search_">
                <img src="<%=basePath%>/images/BuildPlatform/ico-search.png" alt="">
                <p>搜索</p>
            </div>
        </div>


        <div class="con">
        
            <div class="gjpt">
                <ul class="top">
                    <li>
                        <a href="<%=basePath%>/page/ea/BuildPlatform/wfjIndustryPlatfrom/resources.jsp?"><img src="<%=basePath%>images/BuildPlatform/ico-zzy.png" alt=""></a>
                    </li>
                    <li>
                    
                        <a id="add"><img src="<%=basePath%>images/BuildPlatform/ico-jia.png" alt=""></a>
                    </li>
                    <li>
                        <a id="tuig"><img src="<%=basePath%>images/BuildPlatform/ico-wytg.png" alt=""></a>
                    </li>
                    <li>
                        <a id="zhaok"><img src="<%=basePath%>images/BuildPlatform/q2.png" alt=""></a>
                    </li>
                </ul>
              
                <div class="txt"id="text2">
                </div>
              
            </div>
            
             <div class="gjpt_zzy" >
	             <div class="mil mil_1">
	                        <h5>我的平台</h5>
	                        <ul id="id5">
	                        </ul>
	              </div>
	               <div class="mil mil_3">
                        <h5>我关注的公司</h5>
                        <ul id="id6">
                           
                        </ul>
                    </div>
            </div>
           
        </div>
        <div class="alert"></div>
      
    </div>
</div>


<script>
var sccid = "${sessionScope.sccid}";
var param;
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;width: 95%;margin-left: 2.5%;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.828+"px");
        
        $(".gjpt_search_").click(function(){
        	$(".gjpt_search_").hide();
        	$(".gjpt_search input").focus()
        });
        $(".gjpt_search input").focus(function(){
        	$(".gjpt_search_").hide();
        });
         $(".gjpt_search input").blur(function(){
        	if($(".gjpt_search input").val()==""){
        		$(".gjpt_search_").show();
        	}else{
        		$(".gjpt_search_").hide();
        	}
        });
          
        
		$("#add").click(function(){
			url = basePath+"ea/WfjIndustryPlatform/ea_getQuery.jspa?type=01&ppid=p201612089W7PQNDBEM0000000013";
			document.location.href = url;
		})
		$("#tuig").click(function(){
			url = basePath+"ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=01&zzl=zzl";
			document.location.href = url;
		})
		$("#zhaok").click(function(){
			url = basePath+"ea/vipcenter/ea_robWfjUser.jspa?sccid="+sccid;
			document.location.href = url;
		})
		$(document).on("click",".mil li",function(){
			var  ccompanyId = $(this).next().val();
			url = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId+"&typelei=ZHANGZHAOLIANG";
			document.location.href = url;
		});
		
		
			loaded();
		$("#content").bind("propertychange input", function() {
		$(".gjpt_zzy").empty();
		
		loaded();
	})

    });
    
    function loaded(){
    var url;
    	var sou = $("#content").val();
    	if(sou!=null&sou!=""&&sou!=undefined){
    	
    	url = basePath+"/ea/WfjIndustryPlatform/sajax_ea_getIndustryList.jspa?ccmomtype=ajax&sccid="+sccid+"&cumname="+sou;
		
    	}else{
    	url = basePath+"/ea/WfjIndustryPlatform/sajax_ea_getIndustryList.jspa?ccmomtype=ajax&sccid="+sccid;
		
    	}
		

			$.ajax({
				url : url,
				type : "post",
				async : false,
				dateType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var list = member.list;
					var lists = member.lists;
					var htl = new Array();
					var htl2 = new Array();
					if(lists!=null){
						for (var i = 0; i < lists.length; i++) {
							var pp = lists[i];
							if(pp[0]!=null&&pp[0]!="")
								htl.push('<li><div class="left"><img src="'+basePath+pp[0]+'"></div>');
							else
								htl.push('<li><div class="left"><img src="'+basePath+'images/BuildPlatform/logo.png"></div>');
							htl.push('<div class="right"><p>'+pp[1]+'<span style="font-size: 15px;">('+pp[4]+'经济平台)</span></p> </div></li>');
						}
						htl.push('<input type="hidden" id="ccompanyId" value="'+pp[2]+'">');
						$("#id5").append(htl.join(""));
					}
					if(list!=null){
              			for (var j = 0; j < list.length; j++) {
							var pps = list	[j];
							if(pps[3]!=null){
								htl2.push('<li><div class="left"><img src="'+basePath+pps[3]+'" alt=""></div>');
							}else{
								htl2.push('<li><div class="left"><img src="'+basePath+'images/BuildPlatform/logo.png" alt=""></div>');
							}
							if(pps[0]!=null){
								console.log(pps[0]);
                       			htl2.push('<div class="right"><p>'+pps[0]+'</p></div>');
                       		}
							htl2.push('<div class="right"><p>'+pps[4]+'</p> </div></li>');
							htl2.push('<input type="hidden" id="ccompanyId" value="'+pps[5]+'">');
						}
                        $("#id6").append(htl2.join(""));         
                     }
					if(list == null && lists == null){
						htl.push(' <img src="'+basePath+'images/BuildPlatform/bg.png" class="bg">');
						htl.push(' <p>共建平台，共享资源，共享金</p>');
						htl.push(' <p class="cg">成功从这里开始</p>');
						htl.push(' <p>欢迎免费建设经济平台~</p>');
						$("#text2").append(htl2.join(""));
					}
				}
			});
    };
</script>

</body>
</html>