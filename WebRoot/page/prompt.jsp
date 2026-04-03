<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>弹窗提示</title>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}
</style>

</head>
<body>
       	<div id="prompt" style="width: 100%; display: none;">
			<center>
				<div>
					<span style="position: relative; top: 19.8%;"></span>
				</div>
			</center>
		</div>

		<script type="text/javascript">
			$(document).ready(function(){
		        $("#prompt").css("position","fixed").css("top",$(window).height()*0.09+"px").css("z-index",9999);
				$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
				$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");				
			});	
			
		 	function prompt(obj){
				if($("#prompt").css("display")!="none")
					return;
				$("#prompt").find("span").text(obj);
				$("#prompt").fadeIn(500);
				setTimeout(function(){
					$("#prompt").fadeOut(500);
					$("#prompt").find("span").text("");
				}, 2000);
			}
		</script>

</body>
</html>