<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>
<header>
   <ul>
       <li style="width: 10%;">
           <%-- <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/BuildPlatform/left_jt.png"></a> --%>
           <a href="<%=basePath%>ea/WfjIndustryPlatform/ea_getIndustryList.jspa?sccid=${sessionScope.sccid}"><img src="<%=basePath%>/images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">找资源</li>
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
            <div class="gjpt_zzy">
                <ul id="nihao">
                 

                </ul>
            </div>
        </div>
        <div class="alert"></div>
        
        <div class="alert_zzy_"></div>
        <div class="alert_zzy">
            
            <div class="btm">
                <input type="button" value="取消" class="left qx">
                <input type="button" value="确定" class="left qd">
            </div>
        </div>
        <div class="alert_2">
            <div class="alert1">
                <p>您已请求</p>
            </div>
            <div class="alert2">
                <p>您已添加</p>
            </div>
        </div>
    </div>
</div>
<div class="alert_pay_">
    <div class="alert_pay">
        <img src="<%=basePath%>/images/BuildPlatform/bang.png" alt="" class="bang">
        <h4>恭喜你关注成功！</h4>
        <p>确定</p>
    </div>
</div>

<script>

var pagenumber = 0;
var height = 0;
var t;
var pagecount = 0;
var sccid = "${sessionScope.sccid}";
    $(document).ready(function(){
    loaded();
    
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.828+"px");

        $(document).on("click",".gjpt_zzy ul li .right .djgz",function(){
            $(".alert_pay_").show();
            $(this).removeClass("djgz");
            $(this).addClass("qxgz");
            $(this).val("取消关注")
        });
        $(document).on("click",".gjpt_zzy ul li .right .qxgz",function(){
            $(this).removeClass("qxgz");
            $(this).addClass("djgz");
            $(this).val("点击关注")
        });
        $(".alert_pay_").click(function(){
            $(".alert_pay_").hide();
        });
        
        /*2017.4.20点击公司名字跳公司官网页面*/
        $(document).on("click",".c_box",function(){
			var ccompanyid=$(this).parent().siblings().val();
			open(basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyid+"&isflag=gjpt","_self");
		});
        
        /* 2017.2.9 */
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
        $("#content").bind("propertychange input", function() {
		$("#nihao").empty();
		
		loaded();
	})

    });
    
  

    function getHeight() {
	t = setTimeout("getHeight()", 200);
	var height = $(".gjpt_zzy").height() - $(".gjpt_zzy").offset().top;
	var height1 = $(".content").height();
	if (height < height1) {
		if (pagenumber < pagecount) {
			loaded();
		}
	}
}

function Share(){
	clearTimeout(t);
	pagenumber += 1;
}
function loaded() {
	var content = $("#content").val();
	Share();
	if(content!=null){
	url = basePath + "ea/WfjIndustryPlatform/sajax_ea_getQueryCompany.jspa?name="+content;
		
	}else{
	url = basePath + "ea/WfjIndustryPlatform/sajax_ea_getQueryCompany.jspa?";
	
	}
		
	
	$.ajax({
		url: url,
		type: "post",
		async: false,
		dateType: "json",
		data: {
			"pageNumber": pagenumber,
		},
		success: function(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageform;
			$(".last").removeClass("last");
			var htl = new Array();
			if (pageForm != null) {
				var list = pageForm.list;
				pagenumber = pageForm.pageNumber;
				pagecount = pageForm.pageCount
			}
			if (pageForm != null && pageForm.recordCount > 0) {
				for (var i = 0; i < list.length; i++) {
					var pp = list[i];
					   htl.push('<li>');
					   if(pp[0]==null||pp[0]==""){
					   htl.push('<div class="left"><img src="<%=basePath%>/images/BuildPlatform/ico-zy1.png" alt=""></div>');
					   }else{
					   htl.push('<div class="left"><img src="<%=basePath%>'+pp[1]+'" alt=""></div>');
					   }
                       htl.push('<div class="right">');
                       //htl.push(' <p>' + pp[0] + '</p>');
                       htl.push(" <p><a href='###' class='c_box clearfix'>" + pp[0] + "</a></p>");
                       htl.push("<input type='hidden' id='ccompanyId'  value='"+pp[4]+"'/>");
                       console.log(pp[3])
                       if(pp[3]=="00")
                        htl.push('<input type="button" value="取消关注" class="qxgz" onclick="shift(\''+pp[2]+'\',\''+pp[0]+'\')">');
                       
                       else if  (pp[3]=="01"||pp[3]==null)
                        htl.push(' <input type="button" value="点击关注" class="djgz" onclick="shift(\''+pp[2]+'\',\''+pp[0]+'\')">');
                       

                       htl.push(' </div>');
                       htl.push('</li>');
				}
			}
			$("#nihao").append(htl.join(""));
			getHeight();
		},
		error: function(data) {
			alert("获取项目失败")
		}
	})
}

//添加/取消关注
function shift(sccid,company){

	var url = basePath + "ea/companyforum/sajax_ea_shift.jspa?ccom.sccId="+sccid+"&companyName="+company;
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		success : function(data) {
			var shift = eval("(" + data + ")");
			if(!shift.userExist){
				document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
			}
		}
	});
}


</script>

</body>
</html>