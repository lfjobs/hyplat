<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>重置密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
  <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/wfjzn.css" type="text/css"></link></head>
  <script type="text/javascript">
	var basePath='<%=basePath%>';
	var pahe="${cuscom.account}";
  </script>
<body>
	<div class="wfj11_024">
    	<div class="wfj11_024_top">
        	<ul id="tops">
            	<li></li>
            	<li><span style="font-size:18px;font-weight: bold;">微分金-重置密码</span></li>
            	<li></li>
            </ul>
        </div>
        <div class="wfj11_024_title">
        	
        </div>
        <div class="wfj11_024_content">
        	<div class="wfj11_024_width">
            	<div class="wfj11_024_verify">
            	<form  id="myform" name="myform" method="post">
            	
                    <ul>
                        <li style="width:100%;"><input  id="pw_txt1" name="goodname"   type="text" value="请输入只包含数字和字母并长度大于6位密码" onfocus="if(this.value=='请输入只包含数字和字母并长度大于6位密码'){this.value=''}"  /></li>
                    </ul>
                       <ul>
                        <li style="width:100%;"><input  id="pw_txt2" name="goodname"  type="text" value="请输入只包含数字和字母并长度大于6位密码" onfocus="if(this.value=='请输入只包含数字和字母并长度大于6位密码'){this.value=''}"  /></li>
                    </ul>
                    
            	</div>
                <div class="wfj11_024_commit">
                	<ul class="wfj11_024_register">
                    	<li><div  id="confirm">确定</div></li>
                    	
                    </ul>
                    </form>
                	
                </div>
             
            </div>
        </div>
    </div>
    <script type="text/javascript">
    
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto; padding-left:"+$(window).height()*0.02+"px;");
			$("#tops").find("li").eq(1).attr("style","width:100%; text-align:center; font-size:"+$(window).height()*0.02+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto;");
			$(".wfj11_024_top").css("height",$(window).height()*0.05+"px");
			$(".wfj11_024_top").css("lineHeight",$(window).height()*0.05+"px");
			

			$(".wfj11_024_title").attr("style","margin:"+$(window).height()*0.04+"px auto;");
			$(".wfj11_024_title").find("li").eq(0).attr("style","color:#000;font-size:"+$(window).height()*0.025+"px;line-height:"+$(window).height()*0.04+"px;");
			$(".wfj11_024_title").find("li").eq(1).attr("style","font-size:"+$(window).height()*0.025+"px;line-height:"+$(window).height()*0.04+"px;");
			
			$(".wfj11_024_verify").find("ul").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj11_024_verify").find("input[id]").attr("style","font-size:"+$(window).height()*0.01+"px;color:gray;height:"+$(window).height()*0.04+"px; border-radius:"+$(window).height()*0.008+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj11_024_verify").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.045+"px;line-height:"+$(window).height()*0.045+"px;margin-top:"+$(window).height()*0.005+"px; border-radius:"+$(window).height()*0.008+"px;");
			$(".wfj11_024_register").attr("style","margin-bottom:"+$(window).height()*0.05+"px;height:"+$(window).height()*0.01+"px;");
			$(".wfj11_024_register").find("li").attr("style","width:100%;");
			$(".wfj11_024_register").find("li").find("div").attr("style","width:100%;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.045+"px;line-height:"+$(window).height()*0.045+"px; border-radius:"+$(window).height()*0.008+"px;");
			
			//密码输入完转换成密码形式，若空或位默认则清空ljc
			
	
			
			
			$("#pw_txt1").blur(function(){
				if($(this).val()==''){
					$(this).val("请输入只包含数字和字母并长度大于6位密码");	
				}
				
			});
			$("#pw_txt2").blur(function(){
				if($(this).val()==''){
					$(this).val("请输入只包含数字和字母并长度大于6位密码");	
				}
				
			});
	
			
				

		$("#confirm").click(function(){
			
			var text=$("#pw_txt1").val();
			var text2=$("#pw_txt2").val();
			if(text=="请输入只包含数字和字母并长度大于6位密码"){
				alert("请输入密码");
			}
           if(text2=="请输入只包含数字和字母并长度大于6位密码"){
        	   alert("请输入密码");
			}
           if(text==text2){
              var password=$("#pw_txt1").val();
			window.location.href=basePath+"/ea/consignee/ea_editPassWord.jspa?cuscom.account="+pahe+"&password="+password;
           }
           });
        });
		
      	
 
  
      
    </script>
</body>
</html>
