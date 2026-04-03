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
<!DOCTYPE html>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}
</style>			
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>移动办公</title>

    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/gjpt_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>

</head>
<body>
<script>
var basePath='<%=basePath%>';
var sys='${sys}';
var companyId='${companyId}';
var ccompanyId='${ccompanyId}';
var companyName='${companyName}';
</script>
<header>
   <ul>
       <li style="width: 10%;">
          <a id="ret" href="javascript:;"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
           <%-- <a onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>--%>
       </li>
       <li style="width: 80%;"></li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con login_con">
            <div style="overflow: hidden;"><img src="<%=basePath %>images/BuildPlatform/companylogo.png" alt="" class="login_logo" style="float: left;padding: 0.5rem;"></div>
            <s:if test="#request.logoPath==null||request.logoPath==''">
            <div style="text-align: center;"> <img src="<%=basePath %>images/BuildPlatform/default.png" alt=""></div>
            </s:if>
            <s:else>
               <%-- <img src="<%=basePath %>${personalInfo[0][0]}" alt="">--%>
                <div style="text-align: center;"><img src="<%=basePath%>${logoPath}" id="logo"  style="width: 5rem;"></div>
            </s:else>
            <%--<div style="text-align: center;"><img src="<%=path%>${logoPath}" id="logo"  style="width: 5rem;"></div>--%>
            <h3>${companyName }</h3>
            <form name="loginForm" id="loginForm" method="post">
               <%--<div class="ipt company">--%>
                    <input type="hidden" placeholder="组织机构" id="institutional" name="companyIdentifier" value="${companyIdentifier }">
                   <%--</div>--%>
                <div class="ipt personnel">
                    <input type="text" placeholder="用户名" id="name" name="caccount.accountEmail" value="${caccount.accountEmail }">
                </div>
                <div class="ipt password">
                    <i class="eye"></i>
                    <input type="password" placeholder="密码" id="password" name="caccount.accountPassword" value="${caccount.accountPassword }">
                </div>
                <div class="login_dl">
                    <input type="button" value="登录" id="login_dl" onclick="login()">
                    <%--<a>忘记密码?</a>--%>
                </div>
            </form>
        </div>
       	<div id="prompt" style="width: 100%; display: none;">
			<center>
				<div>
					<span style="position: relative; top: 19.8%;"></span>
				</div>
			</center>
		</div>
    </div>
</div>


<script>

    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.92+"px");

        $("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

	    $(".login_con form .ipt input").focus(function(){
	            $(".content_hidden").attr("style","height:"+$(window).height()*1.3+"px");
	        });
	        $(".login_con form .ipt input").blur(function(){
	            $(".content_hidden").attr("style","height:"+$(window).height()*0.92+"px");
	    });
		
        /*登录密码可见不可见*/
        $(".login_con form .password .eye").click(function(){
            if($(".login_con form .password i").hasClass("eye_")){
                $(".login_con form .password input").attr('type','password');
                $(".login_con form .password i").removeClass("eye_")
            }else{
                $(".login_con form .password input").attr('type','text');
                $(".login_con form .password i").addClass("eye_")
            }

        });

      	var u = navigator.userAgent;
    	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

     	if(isAndroid==true){		
    		var obj = document.getElementById("ret");
    		obj.setAttribute("href","javascript:;");
    		obj.setAttribute("onclick", "retAndroid()");    		
    	}else if(isiOS==true){   		
    		var obj = document.getElementById("ret");
    		obj.setAttribute("href","javascript:;");
        	obj.setAttribute("onclick", "retIOS()");
    	}	
    });
    function check(form) {
    	var flag=true;
        //组织机构
        var name=document.getElementById("institutional").value;
       /* if(name == ""){
        	prompt("组织机构不能为空！");
            flag= false;
        }*/
        //用户名称
        var name2=document.getElementById("name").value;
        if(name2 == ""){
        	prompt("用户名称不能为空！");
            flag= false;
        }
        //密码
        var name3=document.getElementById("password").value;
        if(name3 == ""){
        	prompt("密码不能为空！");
            flag= false;
        }
		return flag;
    }
    function login(){
    	if(check()){
    		$.ajax({
    			url: basePath+"mobile/office/sajax_ea_mobileLogin.jspa?",   					
    			type: "post",
    			data:$("#loginForm").serialize(),
    			async: false,
    			success: function cbf(data){
    				var member=eval("("+data+")");
    				var ii=member.i;
    				if(ii==0){
    					prompt("登录成功！");
    					window.location.href=basePath+"/mobile/office/mobileoffice_resourceSystem.jspa?";
    				}else if(ii==1){
    					prompt("公司不存在！");
    				}else if(ii==2){
    					prompt("公司状态不正常！");
    				}else if(ii==3){
    					prompt("账户不存在！");
    				}else if(ii==4){
    					prompt("账号状态不正常！");    					
    				}else if(ii==5){
    					prompt("密码不正确！");
    				}else if(ii==6){
    					prompt("微分金账号未登陆！");
    				}else if(ii==7){
    					prompt("只允许登陆自己公司的移动办公！");
    				}else if(ii==8){
                        prompt("登录成功！");
                        window.location.href=basePath+"/mobile/office/mobileoffice_StudentManagement.jspa?flag=1&companyId="+companyId;
                    }else if(ii==9){
                        prompt("登录成功！");
                        window.location.href=basePath+"/mobile/office/mobileoffice_CoachingManagement.jspa?";
                    }
    			}, 
    		});
    	}
    }
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
	//安卓，苹果返回
	function retAndroid(){
		try{
			Android.callAndroidjianli();
		}catch(err){
				$("#ret").removeAttr("onclick");
				//if(sys=='sys'){
					$("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId);
				//}else{
				//}		
		}
	}	
	function retIOS(){
		try{
			var url= "func=" + "calliosOffice";                
	        window.webkit.messageHandlers.Native.postMessage(url);
		}catch(err){
			$("#ret").removeAttr("onclick");
			//if(sys=='sys'){
				$("#ret").attr("href",basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyId);
			//}
		}		
	}    		
	
</script>
</body>
</html>