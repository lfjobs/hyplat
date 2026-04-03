<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/zy_sys.css">
    <script type="text/javascript"  src="<%=basePath%>js/ea/finance/jquery.min.js"></script>
    
    <title>任务消息</title>

</head>
<body>
<header class="com_head">
        <a href="<%=basePath%>mobile/office/mobileoffice_resourceSystem.jspa?" class="back"></a>
        <h1>任务消息</h1>
    </header>
    <div class="wrap_page chat_page">
        <div class="chat_wrap">
            <div class="chat_box clearfix">
               <%--  <img class="chat_headimg" src="<%=basePath%>images/WFJClient/Mimage/head_img.png" alt="">
                <div class="chat_R">
                   <div class="chat_name">孙海舰</div>
                    <a href="###" class="chat_check no_check">
                        <div class="check_type">审批</div>
                        <div class="check_con">张小四的请假</div>
                        <div class="check_list"><span>请假类型：</span><span>病假</span></div>
                        <div class="check_list"><span>开始时间：</span><span>2016-01-06 15:18</span></div>
                        <div class="check_list"><span>结束时间：</span><span>2016-01-06 15:18</span></div>
                        <div class="check_state"></div>
                    </a>
                    <div class="chat_time">12月05日</div>
                </div>  --%>
            </div>

        </div>
    </div>
    <script>
    var basePath = "<%=basePath%>";
    var t;
    var logintype="${param.logintype}";
	var pagenumber = 0;
	var pagecount = 0;
	 $(document).ready(function(){
	        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");
			loaded();
         if(logintype==1){
             $(".com_head").hide();
         }else{
             $(".back").show();
         }
	    });
    
	 function getHeight1(){
		 var height = parseInt(Math.abs($(".clearfix").height()-($(window).height()-$(".clearfix").offset().top)));
		 t=setTimeout("getHeight1()", 200);
		 var height1 =  $(".wrap_page").height()
		// console.log(height+"=="+height1);
	    	if(height<height1){
	    		if(pagenumber<pagecount){
	    			loaded();
	    		}	
	    	}
	    }
	 
    </script>
	<script>
	    function loaded(){
	    	
	    	clearTimeout(t);
    		pagenumber += 1;
    		
			var url = basePath+"/ea/workmessage/sajax_ea_findMessage.jspa";
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
    			pagecount=pageForm.pageCount;	
				pagenumber=pageForm.pageNumber;
    			if(pageForm =="1"){
    				 
    			}else{
    				var list = pageForm.list;
    				var htl = new Array();
    				for ( var int = 0; int < list.length; int++) {
    					var message = list[int];
    					if(message[4]==02){ //00；尚未收到，01：审核中；02：审核通过，03：已驳回，04：已转交，05：发起人已撤销
    						message[4]="check_state check_agree";
    					}else if(message[4]==03){
    						message[4]="check_state check_disagree";
    					}else if(message[4]==04){
    						message[4]="check_state check_transfer";
    					}
    					else{
    						message[4]="check_state";
    					}
    					if(message[3]==null){
    						message[3]="待审核";
    					}
    					if(message[6]==null){
    						message[6]="";
    					}
    					htl.push("<img class='chat_headimg' src='"+basePath+"images/WFJClient/Mimage/head_img.png"+"'>");
    					htl.push("<div class='chat_R'><div class='chat_name'>"+message[0]+"</div>");
    					htl.push("<a href='"+basePath+message[5]+"' class='chat_check'><div class='check_type'>审批</div>");
    					htl.push("<div class='check_con'>"+message[6]+"</div>");
    					htl.push("<div class='check_list'><span>申请类型:</span><span>"+message[1]+"</span></div>");
    					htl.push("<div class='check_list'><span>申请时间：</span><span>"+message[2]+"</span></div>");
    					htl.push("<div class='check_list'><span>处理时间：</span><span>"+message[3]+"</span></div>");   
    					htl.push("<div class='"+message[4]+"'></div></a></div>");
    				}		
    				$(".clearfix").append(htl.join(""));
    				getHeight1();
    				}
	    		},
	    		error:function(data){
	    			alert("获取项目失败");
	    		}
	    	});
		}  

    </script>
</body>

</html>