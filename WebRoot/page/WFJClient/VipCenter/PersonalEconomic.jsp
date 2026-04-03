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
<title>建设我的平台经济</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/setHtmlFont.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/swiper-3.3.1.min.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
</head>

<body>
    <!-- header start  -->
    <header class="mem_header">
    	<c:choose>
			<c:when test="${flag != null && flag != '' }">
				<a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa" target="_self" class="back"></a>							
			</c:when>
			<c:otherwise>
			 	<a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa" target="_self" class="back"></a>				
			</c:otherwise>			
		</c:choose>	
    
        <h1>建设我的平台经济</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page build_plam_wrap">
    	<div class="pm_head clearfix">
            <img class="pm_headimg" src="<%=basePath%><c:if test='${cuscom.state=="1"}'><s:if test="staff.headimage!=null">${staff.headimage}</s:if><s:else>images/WFJClient/VipCenter/headimage.png</s:else></c:if><c:if test='${cuscom.state=="2"}'><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/WFJClient/VipCenter/headimage.png</s:else></c:if>" >
            <div class="pm_text">
                <div class="pm_text_top">
                	<c:if test='${cuscom.state=="1"}'>${staff.staffName } </c:if>
                	<c:if test='${cuscom.state=="2"}'>${contactCompany.cresponsible }</c:if>
                </div>
                <div class="pm_text_bottom">${custypename}</div>
                <a href="<%=basePath%>ea/vipcenter/ea_Platform.jspa?staffid=${cuscom.staffid}&sccid=${cuscom.sccId}&flag=${flag }&type=1" class="change_btn">切换平台<i></i></a>
            </div>
        </div>
        <input type="hidden" id="account" value="${cuscom.account}"/>
    	<input type="hidden" id="type" value="${cuscom.cusType}"/>
        <div class="build_plam_con flex">
            <dl class="build_conL">
            </dl>
            <div class="build_conR">
            	<div class="build_conR_content">
            	</div>
            </div>
        </div>
    </div>
    
    <script>
   	var basePath="<%=basePath%>";
   	var type=$("#type").attr("value");//当前用户会员级别
   	var account=$("#account").attr("value");
   	var sccid = "${sccid}";
   	var pagenumber = 0;
	var height = 0;
	var t;
	var pagecount;
		
	$(document).ready(function(){
   		vipnum();//初始化左边会员导航
   		$(".build_conL").find("dd").eq(0).addClass("build_cur");
   		var custype = 0;
   		type = Number(type);
   		if(type==0){
   			custype = 2;
   		}else if(type==6||type==7){
   			custype=8;
   		}else{
   			custype=type+1;
   		}
   		loaded(custype);//初始化左边第一种会员级别的右边详细列表
		
		//点击左边不同会员类别，加载右边详细会员列表
		$(document).on("click",".build_conL dd",function (){
			$(this).addClass("build_cur").siblings().removeClass();
			$(".build_conR_content").empty();
			pagenumber=0;
			var custype=$(this).attr("id");
			loaded(custype);
	    });
	});
	
	
	
	//加载左边导航会员以及数量
	function vipnum(){
		var url = basePath+"ea/vipcenter/sajax_ea_findVipNum.jspa";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
				cusType:type,
				sccid:sccid
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var listt = member.listt;
				if(listt==null){
					return;
				}else{
					var htl = new Array();
					for(var i = 0;i<listt.length;i++){
						list = listt[i];
						htl.push("<dd id='"+list[2]+"' class='check'><span>"+list[0]+"</span><span>"+list[1]+"人</span></dd>");
					}
					$(".build_conL").append(htl.join(""));
				}
			},
			error:function(data){
				alert("获取失败");
			}
		});
	}
	
   	//加载每一种会员级别的详细列表
    function loaded(custype){
    	clearTimeout(t);
		pagenumber += 1;
		var url = basePath+"ea/vipcenter/sajax_ea_findVipAccout.jspa";
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{						
				 "pageForm.pageNumber":pagenumber,
					cusType:custype,
					sccid:sccid
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var pageForm = member.pageForm;
				if(pageForm ==null){
					return;
				}else{
					var htl =new Array();
					var list = pageForm.list;
					pagenumber = pageForm.pageNumber;
					pagecount = pageForm.pageCount;
					for(var i = 0;i<list.length;i++){
						var li = list[i];
						var type=li[7];
						var invite="";
						if(type==""||type==null){
							type="尚未注册";
							invite = "<a class='sendmsg' id='"+li[0]+"' href='javascript:;' style='float: right;padding-right: 0.5rem;cursor: pointer;'>短信邀请 </a>";
						}
                 		if(li[3]==""||li[3]==null){
                 			li[3]="images/WFJClient/VipCenter/headimage.png";
                 		}
						htl.push("<div class='build_wrap' data-index='0'><div class='build_box flex flex_align_center'>");
						htl.push("<div class='build_boximg'><img src='"+basePath+li[3]+"' alt=''></div><div class='build_boxtxt flex_1'>");
						htl.push("<span>"+li[2]+"</span><span>"+type+invite+"</span></div></div></div>");
					}
					$(".build_conR_content").append(htl.join(""));
					getHeight(".build_conR_content",".build_conR","loaded("+custype+")");
				}
			},
			error:function(data){
				alert("获取失败");
			}
		});
	}
	
	$(document).on("click",".sendmsg",function (){
		var num= $(this).attr("id");
		var u = navigator.userAgent; 	
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
       	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
       	if(isAndroid==true){
       		if(confirm("确定发送短信邀请?")){
       			Android.callmsg(num);
       		}
       	}else if(isiOS==true){
       		if(confirm("确定发送短信邀请?")){
       		 	var url= "func=" + 'message';
                params={'name':num};
                for(var i in params){
                	url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
       	}
	});
	
    </script>
    
    
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientHeight = document.documentElement.clientHeight;
            var plam_H = clientHeight-$('.build_plam_con').offset().top;
            document.getElementsByClassName('build_plam_con')[0].style.height=plam_H+'px';
            
        }
    </script>
</body>
</html>