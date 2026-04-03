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
<title>银行卡信息</title>
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

<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<body class="bgcolorFFF">
	<div class="wfj01_008">
    	<div class="wfj01_008_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_getBankCardsList.jspa?ccompanyId=${ccompanyId}&user=${user}" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>银行卡信息</li>
            </ul>
        </div>
        <input id="ccompanyId" type="hidden" value="${ccompanyId }" />
        <input id="bankAccountId" type="hidden" value="${staffbank.bankAccountID }"/>
        <input type="hidden" value="${user }" id="user" />
        <div id="prompt" style="width: 100%;display: none;">
        	<center>
        		<div>
        			<span style="position: relative;top: 19.8%;"></span>
        		</div>
        	</center>
        </div>
        <div class="wfj01_008_content">
            <table>
            	<tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_01.png" /></td>
                	<td width="85%"><input class="verification" type="text" value="${staffbank.cardholder}" readonly="readonly"/></td>
                </tr>
                <tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_03.png" /></td>
                	<td width="85%"><input class="verification" type="text" value="${staffbank.province}" readonly="readonly"/></td>
                </tr>
                <tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_04.png" /></td>
                	<td width="85%"><input class="verification" type="text" value="${staffbank.bankAddress}" readonly="readonly"/></td>
                </tr>
            	<tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_02.png" /></td>
                	<td><input class="verification" type="text"  value="${staffbank.bankName}"  readonly="readonly"/></td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_03_03.png" /></td>
                	<td><input class="verification" type="text"  value="${staffbank.branchName}" readonly="readonly"/></td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_09.png"/></td>
                	<td><input class="verification" type="text"  value="${staffbank.cardType=='00'?'个人账户':'企业账户'}" readonly="readonly"/></td>
                </tr>
            	<tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_04.png" /></td>
                	<td><input class="verification" type="text"  value="${staffbank.bankAccount}" maxlength="16" readonly="readonly"/></td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/wfj09_25_03.png" /></td>
                	<td><input class="verification" type="text" value="${staffbank.accountNature }"  readonly="readonly"/></td>
                </tr>
            </table></form>
        </div>
        <div class="wfj01_008_bottom">
        	<div class="wfj01_008_save">操作</div>
    	</div>    
	</div>
	<div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" class="wfj01_001_popup">
    	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 84%;text-align: center;" class="wfj01_001_cardType_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;" id="resetdefault">删除该银行卡</div>
    	</div>
    	<hr  style="position: absolute;top: 92%;color:#000000;width: 100%;">
    	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 92%;text-align: center;" class="wfj01_001_popup_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;" class="positionSelection" name="job" id="setdefault">设置为默认银行卡</div>
    	</div>
    </div>

    <script type="text/javascript">
    var basePath='<%=basePath%>';
	var ccompanyId=$("#ccompanyId").val();
	var user=$("#user").val();
	var bankAccountId=$("#bankAccountId").val();
    	$(document).ready(function(e) {
    	
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;cursor:pointer;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj01_008_top").css("height",$(window).height()*0.08+"px");
			$(".wfj01_008_top").css("lineHeight",$(window).height()*0.08+"px");
			
			$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
			$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
		    $(".more").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom: 1px solid #F1F1F1;");
			$(".more").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			
			
			$(".wfj01_008_title").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px;");
			$(".wfj01_008_title").find("div").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_008_content").find("tr").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; border-bottom:"+$(window).height()*0.003+"px solid #FFF;");
			$(".wfj01_008_content").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto;");
			$(".wfj01_008_content").find("input").attr("style"," height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_008_bottom").attr("style","height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_008_save").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
			
		  
        });
    	
    	$(".wfj01_001_popup").click(function(){
	  		$(".wfj01_001_popup").hide();
		});
    	//点击更多
    	$(".wfj01_008_save").click(function(){
    		$(".wfj01_001_popup").show();	
    	});
    	//设置默认
    	$("#setdefault").live("click",function(){
    		var purl = basePath+"ea/industry/sajax_ea_setDefault.jspa?";
    		var parm=new Array();
    		parm.push("ccompanyId="+ccompanyId);
    		parm.push("&user="+user);
    		parm.push("&bankAccountId="+bankAccountId);
    		$.ajax({
    			url:purl,
    			type:"post",
    			data:parm.join(""),
    			success:function(data){
    				var member = eval("(" + data + ")");
					var flag=member.flag;
					if(flag=='1'){
						document.location.href=basePath+"ea/industry/ea_getBankCardsList.jspa?"+parm.join("");
					}
    			}
    			
    		});
    		
    	});
    	//解除绑定
    	$("#resetdefault").live("click",function(){
    		var purl = basePath+"ea/industry/sajax_ea_del.jspa?";
    		var parm=new Array();
    		parm.push("ccompanyId="+ccompanyId);
    		parm.push("&user="+user);
    		parm.push("&bankAccountId="+bankAccountId);
    		$.ajax({
    			url:purl,
    			type:"post",
    			data:parm.join(""),
    			success:function(data){
    				var member = eval("(" + data + ")");
					var flag=member.flag;
					if(flag=='1'){
						document.location.href=basePath+"ea/industry/ea_getBankCardsList.jspa?"+parm.join("");
					}
    			}
    			
    		});
    	});
    	//提示窗口
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
    	function re_load(){
    		document.location.href=basePath+"ea/industry/ea_getBankCardsList.jspa?ccompanyId="+ccompanyId+"&user="+user+"&bankAccountId="+bankAccountId;
    	}
    </script>
</body>
</html>