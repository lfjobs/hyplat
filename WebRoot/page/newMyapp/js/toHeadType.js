$(document).ready(function() {
	if(titleJudge!=null){
        if(titleJudge=='00'){
            $("#header ul li.nav").find(".homePage").addClass("active");
        }else if(titleJudge=='01'){
            $("#header ul li.nav").find(".companyShoppingMall").addClass("active");
	    }else if(titleJudge=='02'){
            $("#header ul li.nav").find(".companyMarketing").addClass("active");
	    }else if(titleJudge=='03'){
            $("#header ul li.nav").find(".companyChinaMerchants").addClass("active");
	    }else if(titleJudge=='04'){
            $("#header ul li.nav").find(".companyNews").addClass("active");
	    }else if(titleJudge=='05'){
            $("#header ul li.nav").find(".companyRecruit").addClass("active");
	    }else if(titleJudge=='06'){
            $("#header ul li.nav").find(".companyAboutUs").addClass("active");
        }else{
            $("#header ul li.nav").find(".homePage").addClass("active");
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
						+ "/ea/newpcend/ea_companyWebsite.jspa?titleJudge="+titleJudge+"&ccompanyId="+ccompanyId;
			},
			error : function(data) {
				alert("退出失败");
			}
		});
	}
}
