<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">   
    <title>个人信息	维护</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<style type="text/css">
		.tex{color: red;font-size: 12px;}
	</style>
  </head> 
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	<div class="con">
      <div class="customerInfo">
      <form action="<%=basePath %>/ea/wfj/ea_getPassword.jspa?steep=zhuce" method="post" id="submit">
        
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">账号</div>
            <div class="optionsCon fl"><input type="text" name="customer.account" class="yname account"/></div>
        </div>
        <div class="clear"></div>       
        <div class="info fl">
            <div class="optionsNav fl">新密码</div>
            <div class="optionsCon fl">
            	<input type="password" id="P2" name="customer.password" value="${customer.password}" class="yname  password" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/>
            </div>
        </div>
        <div class="clear"></div>       
        <div class="info fl">
            <div class="optionsNav fl">确认密码</div>
            <div class="optionsCon fl">
            	<input type="password" id="P2"  class="yname  password1" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/>
            </div>
        </div>
        
        </form>
        <div class="clear"></div>
        <div class="customerButton"><a href="javascript:;" id="reg"> 确定</a></div>
        <div class="clear"></div>
     </div>
	</div>
  </body>
  <script type="text/javascript">
  $('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML="找回密码";
		doc.getElementById("return").onclick=function(){
			window.history.go(-1);
		}
	});
  $("#reg").click(function () {
	  
	  pass = '1';
      if($("form .error").length)
      { 
          return;
      } 
      $("#submit").submit();
	});
  $(document).ready(function() {
	  var basePath="<%=basePath%>";
		$("form :input").live("blur", function() {
			$input = $(this);
			$parent = $input.parent().parent().parent();
			var inputValue = $input.attr("value");
			$parent.find(".error").remove();
			$parent.find(".tex").remove();
			if ($input.is(".password")) {
				
				if (inputValue.indexOf(' ') != -1) {
					$parent.append("<span class=\"error\"><a class=\"tex\">密码不能有空格</a></span>");
					return;
				}
				if (inputValue.length < 6 || inputValue.indexOf(' ') != -1) {
					$parent.append("<span class=\"error\" style=\"color:red;\"><a class=\"tex\">\u8bf7\u8f93\u51656\u4f4d\u4ee5\u4e0a\u7684\u5bc6\u7801</a></span>");
					return;
				}	
			}
			// 判断密码
			if ($input.is(".password1")) {
				b = $(".password").val();

				if (inputValue == "" || !(inputValue == b)) {
					$parent
							.append("<span class=\"error\"><a class=\"tex\">2\u6b21\u8f93\u5165\u7684\u5bc6\u7801\u4e0d\u4e00\u6837</a></span>");
					return;
				}
			}				
			if ($input.is(".account")) {
				if (inputValue == "") {
					$parent
							.append("<span class=\"error\"><a class=\"tex\">帐号\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
					return;
				}			
				if (inputValue != "" && inputValue.length > 5) {
					var url = basePath + "ea/wfj/sajax_ea_isRegister.jspa?parameter="
							+ inputValue + "&date=" + new Date();
					$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							if (member.isR==false) {
								$parent
										.append("<span class=\"error\"><a class=\"tex\">帐号不存在</a></span>");
								return;
							}
							if(member.recoveryquestion!=null){
								$parent.find("#tishi").html(member.recoveryquestion);
							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					return;
				}
				$parent.append("<span class=\"error\"><a class=\"tex\">帐号长度必须大于6位</a></span>");
			}
		});
	});
  
  </script>
</html>
