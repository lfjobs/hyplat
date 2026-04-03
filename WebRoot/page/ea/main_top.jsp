<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<title>天太世统ERP后台管理</title>
		<style>
		.fl{float:left;}
		.left{height:45px; width:319px; background-repeat: no-repeat;}
		.left img{ margin:10px; float:left;}
		.left span{ color:#fff; display:block; float:left; font-size:18px; font-weight: bold; line-height:40px;}
		.right{ width:100%; height:35px; background: url("<%=basePath%>images/top_bg02.png") repeat-x;}
		.daohang ul li a { font-size:16px; color:#fff; font-weight: bold;}
		.daohang ul li a:hover { font-size:16px; color:#fff; font-weight: bold;}
		.daohang ul { bottom: 0px;left: 350px;}
		.daohang ul li { margin-left:10px;}
		.li_zj {background-image:none;font-size:14px; color:#fff;}
		 .top_right{ height:40px; color:#fff;margin-left:400px; margin-top:56px; }
 .top_right span{ display:block; margin-left:12px; height:40px; line-height:40px; font-size:16px; font-weight:bold; float:left; cursor:pointer;}
		
		</style>
	</head>
	<body>
		<div id="left" class="left"><img style="width:32px;" alt="" title="" src="<%=basePath%>images/logo.png" /><span>5L5C管理系统</span></div>
		<div class="right">
			<div class="daohang">
				<ul>
					<s:iterator value="seaList">
						<li>
							<a href="javascript:;"
								onclick="list('<s:property value="eaID"/>','<s:property value="eaName"/>')"><s:property
									value="eaName" /> </a>
						</li>
						<li class="li_zj"></li>
					</s:iterator>
					<li>
						<a id="Online"></a>
					</li>
					<li class="li_zj"></li>
					<li>
						<a href="javascript:;" onclick="logOut()" style="color: #FFFFFF;">返回登录</a>
					</li>
					<li class="li_zj"></li>
					<li>
						<a style="color: #fffebb; font-weight: bold;"> 欢迎您
							${sessionScope.account.accountEmail}</a>
					</li>
				</ul>
			</div>
		</div>
<script src="<%=basePath%>js/comm.js" type="text/javascript"></script>
<script>
    var basePath = '<%=basePath %>';
	$(document).ready(function() {
		var imgLog = '<%=request.getParameter("imgLog")%>';
		if(imgLog == '1'){
		  //$("#left").css("background","url("+ basePath +"images/5L5C_logo02.jpg)"); 
		}else{
		  //$("#left").css("background","url("+ basePath +"images/5L5C_logo02.jpg)"); 
		}
	});
	
    function logOut(){
	    parent.location.href=basePath + "ea/login/ea_logOut.jspa";	
    }
     
	function list(eaID,eaName){ 
		if(window.parent.document.getElementById("leftFrame").style.display == "none") { 
		    window.parent.document.getElementById("bgMainContent").cols="194,5,*";
		    window.parent.document.getElementById("leftFrame").style.display = "block";
		} 
	   window.parent.document.getElementById("rightFrame").src=basePath + "ea/hdocument/ea_getHDocumentList.jspa?";	
	   window.parent.document.getElementById("mainContent").rows="0,*";
	   var leftUrl = window.parent.document.getElementById("leftFrame").src;
	   if(leftUrl.indexOf("ea_getCompanyMessage") > -1){
	       window.parent.document.getElementById("leftFrame").src = basePath + "ea/login_main_generateleft.jspa?eaID=" + eaID + "&eaName=" + eaName;
	   }else{
	       window.parent.frames["leftFrame"].lists(eaID);
	   } 
	}
	var mainContent = window.parent.document.getElementById("mainContent");	
	
   if(window.parent.document.getElementById("daohang").style.display == "none"){
       mainContent.rows = "0,*";
   }else{
       mainContent.rows = "60,*";
   }
	
    function ObjectA(text){   
	    this.text = (text!=null) ? text : "not set the text.";
	    this.viewText = function() {
	       var url1 = basePath + "ea/caccount/sajax_ea_getCaccoutOnLineNumber.jspa?date="+new Date().toLocaleString(); 
	         $.ajax({
	             url: url1,
	             type: "get", 
	             dataType: "json",
	             success: function cbf(data){
	                var member = eval("(" + data + ")");
	                var nologin = member.nologin;
					if(nologin){
						document.src.href =basePath + "page/ea/not_login.jsp";
					}
	                var OnLineNumber = member.OnLineNumber;
	                $("#Online").html( " 在线人数：  " + OnLineNumber);
	              }//,
	              //error: function cbf(data){}
	        });
	    };
	    this.showText = function() {
	        var me = this;
	        setInterval(function(){me.viewText();}, 5000*60);
	    };
    }
	var e = new ObjectA();
	e.viewText();
	e.showText();
</script>
	</body>
</html>

