$(document).ready(function() {
	if(titleJudge!=null){
		if(titleJudge=='01'){
	    	$(".ind_con_head").find("ul").find(".information").find("li").addClass("active");
	    }else if(titleJudge=='02'){
	    	$(".ind_con_head").find("ul").find(".work").find("li").addClass("active");
	    }else if(titleJudge=='03'){
	    	$(".ind_con_head").find("ul").find(".investment").find("li").addClass("active");
	    }else if(titleJudge=='04'){
	    	$(".ind_con_head").find("ul").find(".member").find("li").addClass("active");
	    }else if(titleJudge=='05'){
	    	$(".ind_con_head").find("ul").find(".mall").find("li").addClass("active");
	    }else if(titleJudge=='06'){
	    	$(".ind_con_head").find("ul").find(".readExtensively").find("li").addClass("active");
	    }else if(titleJudge=='07'){
	    	$(".ind_con_head").find("ul").find(".recruitment").find("li").addClass("active");
	    }else if(titleJudge=='08'){
	    	$(".ind_con_head").find("ul").find(".platform").find("li").addClass("active");
	    }else if(titleJudge=='09'){
	    	$(".ind_con_head").find("ul").find(".aboutUs").find("li").addClass("active");
	    }else{
	    	$(".ind_con_head").find("ul").find(".homePage").find("li").addClass("active");
	    }
	}
   	var t = [];
	if(a==""){
		t.push("<li class='login'>");
		t.push("<a href='"+basePath+"/page/newMyapp/login.jsp' style='margin-right: 10px;'><input type='button' value='登录'></a>");
		t.push("<a href='"+basePath+"/page/newMyapp/register.jsp'><input type='button' value='注册' id='zc'></a>");
		t.push("</li>");
	}else{
		t.push("<li class='login login_in'>");
		t.push("<a href='javascript:member();' id='name'>"+a+"</a><span style='font-size: 18px;padding: 0 0 0 5px;'>|</span>");
		t.push("<a href='javascript:quit();' class='quit'>退出</a>");
		t.push("</li>");
	} 
	$("#header").find("ul").append(t.join(""));
});

//退出登录
function quit() {
	if (confirm("是否退出登录???")) {
		var url = basePath + "/ea/newpcend/sajax_ea_quitSession.jspa?";
		$.ajax({
			url : url,
			type : "post",
			async : true,
			dataType : "json",
			success : function(data) {
				document.location.href = basePath
						+ "ea/wfjshop/ea_getWFJshops.jspa";
			},
			error : function(data) {
				alert("退出失败");
			}
		});
	}
}
