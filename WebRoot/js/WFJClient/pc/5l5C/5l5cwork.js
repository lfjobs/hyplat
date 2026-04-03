$(function(){
	
	   //判断是否认证
	   if(authState=='00'){
		   $(".popup_rz").show();
		   $(".rz_infotop").html("您的公司信息尚未认证");
	   }else if(authState=='03'){
		   $(".popup_rz").show();
		   $(".rz_infotop").html("认证失败,请重新认证");
	   }
	   //关闭认证
	   $(".rz_close").click(function(){
		   $(".popup_rz").hide();
	   })
	   //跳转认证
	   $(".rz_btn").click(function(){
		   document.location.href = basePath + "/ea/qrshare/ea_queryState.jspa?auditSkip=01&companyID="+companyID+"&staffID="+staffID;
	   })
	
	
});