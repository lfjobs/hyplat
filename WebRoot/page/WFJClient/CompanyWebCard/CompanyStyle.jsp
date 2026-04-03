<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司风格</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/farbtastic.css"/>
<script type="text/javascript" src="<%=basePath %>js/farbtastic.js"></script>
</head>

<body>
	<div class="wfj01_011">
    	<div class="wfj01_011_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}&user=${user}&editType=0" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>公司风格</li>
            	<li><a href="#">恢复默认</a></li>
            </ul>
        </div>
        <div class="wfj01_011_color">
        	<div class="wfj01_011_width">
        	<form id="activitiesForm" action="" width="400px;">
        		<input type="hidden" id="activitiesId" value="${activities.activitiesID }"/>
				<input type="submit" id="submit" style="display:none"/>
				<input type="hidden" id="ccompanyId" value="${ccompanyId }"/>
				<div class="form-item">
                	<label for="color">公司风格色调展示：</label>
                    <input type="text" id="color" value="#123456" />
                </div>
                <div id="picker" style="margin-top:30px;"></div>
                <div class="wfj01_011_bottom">
                	<div>保存风格色调</div>
                </div>
               </form> 
            </div>
        </div>
        
        
        
    </div>
  
  
    <script type="text/javascript">
		var basePath='<%=basePath%>';
		var activitiesId=$("#activitiesId").val();
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			
			//修改字体大小
			$(".wfj01_011_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		 
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;font-size:"+$(window).height()*0.02+"px;");
			
			
			$(".wfj01_011_color").attr("style"," margin:"+$(window).height()*0.025+"px auto;");
			$(".form-item").find("label").attr("style","height:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.025+"px; background-color:#EAEAEA;");
			$(".form-item").find("input").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.03+"px;");
			
			$(".wfj01_011_bottom").attr("style"," margin-top:"+$(window).height()*0.05+"px;");
			$(".wfj01_011_bottom").find("div").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			
			if($(window).width()>$(window).height()){
				$(".wfj01_011").attr("style","width:70%;height:"+$(window).height()+"px;");
				$(".farbtastic").css("width",$(window).width()*0.61+"px").css("height",$(window).width()*0.61+"px");
			}else{
				$(".wfj01_011").attr("style","width:100%;height:"+$(window).height()+"px;");
				$(".farbtastic").css("width",$(window).width()*0.61+"px").css("height",$(window).width()*0.61+"px").css("marginLeft",$(window).width()*0.15+"px");
			}	
			
			//加载公司风格
			var url=basePath+"ea/industry/sajax_ea_CompanyStyle.jspa?ccompanyId=${ccompanyId}";
			$.ajax({
				url : url,
				type : "get",
				async : false,
				success : function cbf(data){
					var member=eval("("+data+")");
					var activities=member.activities;
					if(activities!=null){
						$(".wfj01_011_top").css("background",activities.describe);
						$(".wfj01_011_bottom").find("div").css("background",activities.describe);
					}
				},
				error : function cbf(data){
					alert("公司风格加载失败！");
				}
			});
			
		});
    	$(".wfj01_011_bottom").find("div").click(function(){
    		var url=basePath+"ea/industry/sajax_ea_SaveCompanyStyle.jspa?";   		
    		var style=$("#color").val();
    		var ccompanyId=$("#ccompanyId").val();
    		$.ajax({
    			url: url,
    			type :"get",
    			async: false,
    			dataType:"json",
    			data:{
    				"activitiesId":activitiesId,
    				"style":style,
    				"ccompanyId":ccompanyId
    			},  			
    			success :function cbf(data){
    				var member =eval("("+data+")");
    				var flag=member.flag;
    				if(flag=='1'){
    					window.location.href=basePath+"ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}";
    				}
    			},
    			error: function cbf(data){
    				alert("保存失败！");
    			}
    		});
    	});
		$(document).ready(function() {
			$('#picker').farbtastic('#color');
		  });
		$("#tops").find("li").eq(2).click(function(){
			var url=basePath+"ea/industry/sajax_ea_delCompanyStyle.jspa?activitiesId="+activitiesId;
			$.ajax({
				url :url,
				type : "get",
				async : false,
				success : function cbf(data){
					var member=eval("("+data+")");
					var flag=member.flag;
					if(flag=='1'){
						window.location.href=basePath+"ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}";
					}
				},
				error : function cbf(data){
					alert("恢复失败");
				}
			});
		});
	</script>
</body>
</html>
















