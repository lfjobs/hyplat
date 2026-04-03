<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<title>公司银行卡管理</title>
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



</head>
<body class="bgcolorFFF">
	<div class="wfj01_007">
        <div class="wfj01_007_changeinfo">
           <div class="wfj01_007_ca bgcolorDDEEEE">
                <div class="wfj01_007_width">
                    <ul id="title">
                    	<li class="wfj_return"><a href="<%=basePath %>/mobile/office/mobileoffice_fastApplication.jspa" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png"/></a></li>
                        <li class="left">银行卡</li>
                    </ul>
                </div>
            </div> 
        	<div class="wfj01_007_card">
            	<div class="wfj01_007_hid">
            	<input id="ccompanyId" type="hidden" value="${ccompanyId }"/>
            	<input id="sccId" type="hidden" value="${sccId }"/>
                <c:forEach items="${BankCardList }" var="entity">
                	<div class="wfj01_007_cards">
                        <div class="wfj01_007_width">                        
                            <div class="wfj01_007_addcard" onclick="toEdit('${entity.bankAccountID }')">
                                <div class="wfj01_007_width">
                                    <ul>
                                        <li class="left">${entity.bankName }</li>
                                        <li class="left">${entity.accountNature }</li>                                                                     	
                                     	<li class="left">${entity.isdefault eq '1'? '默认':'' }</li> 
                                    </ul>
                                    <ul>  
                                    	<li class="left">${entity.cardType eq '01'? '企业账户':'个人账户'}</li>                     				
                                   		<li class="right"><input class="txts" type="text" readonly="readonly" value="${entity.bankAccount }" /></li>
                                    </ul>
                                </div>
                            </div>   
                        </div>
                    </div></c:forEach>
                </div>
            </div>          
        </div>
        
        
		<div class="wfj01_007_bottom">
        	<div id="add">添加更多银行卡</div>
        </div>
    </div>
    <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    <script type="text/javascript">
    var basePath='<%=basePath%>';
	var ccompanyId=$("#ccompanyId").val();
	var sccId=$("#sccId").val();
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
         	$("body").css("width",$(window).width()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#FFF;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;cursor:pointer;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$(".wfj01_007_top").css("height",$(window).height()*0.08+"px");
			$(".wfj01_007_top").css("lineHeight",$(window).height()*0.08+"px");
			
			
			$(".wfj01_007_bottom").find("div").attr("style","font-size:"+$(window).height()*0.03+"px; border-radius:"+$(window).height()*0.01+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			
			
			
			//银行信息
//			$(".wfj01_007_ca").click(function(){
//				var ccompanyId=$("#ccompanyId").val();
//				var user=$("#user").val();
//				
//				open(basePath+"ea/industry/ea_getaddBankCardInformation.jspa?ccompanyId="+ccompanyId+"&user="+user,"_self")
//			});

			$(".wfj01_007_ca").attr("style"," height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px;");
			$("#title").find("li").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$("#title").find("li").eq(0).attr("style","padding-left:"+$(window).width()*0.03+"px;");
			$("#title").find("li").eq(1).attr("style","padding-right:"+$(window).width()*0.03+"px;");
			$(".wfj01_007_addcard").each(function(index, element) {
				$(this).attr("style","height:"+$(window).height()*0.12+"px;line-height:"+$(window).height()*0.03+"px;margin-top:"+$(window).height()*0.01+"px;");
				$(this).find("ul").eq(0).attr("style","height:"+$(window).height()*0.03+"px;line-height:"+$(window).height()*0.03+"px;padding-top:"+$(window).height()*0.02+"px;");
				$(this).find("li").eq(0).attr("style","border-bottom:1px solid #F74C31;font-size:"+$(window).height()*0.025+"px;margin-left:"+$(window).width()*0.03+"px;");
				$(this).find("li").eq(1).attr("style","font-size:"+$(window).height()*0.02+"px;padding-top:"+$(window).height()*0.008+"px;padding-left:"+$(window).height()*0.01+"px;");
				$(this).find("li").eq(2).attr("style","font-size:"+$(window).height()*0.02+"px;padding-right:"+$(window).height()*0.01+"px;float:right;");
				$(this).find("li").eq(3).attr("style","padding-top:"+$(window).height()*0.01+"px;margin-left:"+$(window).width()*0.03+"px;font-size:"+$(window).height()*0.025+"px;");
				$(this).find("li").eq(3).find("input").attr("style","font-size:"+$(window).height()*0.02+"px;width:"+$(window).width()*0.4+"px;");
            });
			
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
						$("#add").css("background",activities.describe);
					
					}
				},
				error : function cbf(data){
					alert("公司风格加载失败！");
				}
			});
			
			//隐藏滚动条
			$(".wfj01_007_content").attr("style"," width:"+$(window).width()+"px;height:"+$(window).height()*0.82+"px;");
			$(".wfj01_007_hidden").attr("style","width:"+parseInt($(".wfj01_007_content").width()+17)+"px;height:"+parseInt($(".wfj01_007_content").height())+"px;");
			$(".wfj01_007_profile").attr("style","width:"+$(window).width()+"px;");
			
			//银行信息--隐藏滚动条
			$(".wfj01_007_card").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj01_007_top").height()-$(".wfj01_007_ca").height()-$(".wfj01_007_bottom").height()-$(window).height()*0.02)+"px; overflow:hidden;");
			$(".wfj01_007_hid").attr("style","height:"+$(".wfj01_007_card").height()+"px;overflow:auto;");
			
			var h=$(".wfj01_007_addcard").height()*$(".wfj01_007_addcard").length+$(window).height()*0.01*$(".wfj01_007_addcard").length;
			if(h < $(".wfj01_007_card").height()){
				$(".wfj01_007_hid").css("width",$(".wfj01_007_card").width()+"px");
			}else{
				$(".wfj01_007_hid").css("width",parseInt($(".wfj01_007_card").width()+17)+"px");
			}
			
			if($(window).width()>$(window).height()){
				$(".wfj01_007").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".wfj01_007_bottom").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			}else{
				$(".wfj01_007").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".wfj01_007_bottom").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			}
			$("#add").click(function(){
				open(basePath+"ea/industry/ea_getaddBankCardInformation.jspa?ccompanyId="+ccompanyId+"&sccId="+sccId,"_self");
			});	
			//隐藏卡号
			var t = /^\d{12}(\d{4})$/;
			var t1=/^\d{15}(\d{4})$/;
			$(".txts").each(function(){
				if($(this).val().length==16)
					$(this).val($(this).val().replace(t,"**** **** **** $1"));
				else
					$(this).val($(this).val().replace(t1,"**** **** **** $1"));
			});
        });
function toEdit(entityId){
	window.open(basePath+"ea/industry/ea_BankCardInfo.jspa?ccompanyId="+ccompanyId+"&sccId="+sccId+"&bankAccountId="+entityId,"_self");
}

    </script>
</body>
</html>