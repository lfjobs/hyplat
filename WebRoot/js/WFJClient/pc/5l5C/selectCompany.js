$(function(){
    //点击公司列表
	$(document).on("click",".div-con li",function(){
		var companyID = $(this).attr("id");
		var staffID = $(this).attr("data-staff");
		var ccomid =  $(this).attr("ccomid");
		if(bd=="xcx") {
             //小程序
			document.location.href = basePath + "/ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccomid+"&industryType=&etype=&sc=web";
		}else if(bd=="zx") {
            //资讯
			document.location.href = basePath+"/ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02&companyID="+companyID;
		} else{
			//选择公司进入工作
			document.location.href = basePath + "ea/5l5c/ea_work5L5C.jspa?companyID=" + companyID + "&staffID=" + staffID;
		}
	});
	load();
	
});

function load(){
	var url = basePath+"ea/android/sajax_ea_findConpany.jspa";
	$.ajax({
	   url:url,
	   type:"POST",
	   dataType:"json",
	   aysnc:false,
	   data:{
		 sccId:sccId
	   },
	   success:function(data){
		   var comlist = data.company;
		   var html = [];
		   //如果没有公司就跳到认领页面
		   if (comlist == null||comlist.length==0) {
			   window.location.href = basePath + "/ea/qyrz/ea_toPeriphery.jspa";
		   }
		   for(var i = 0;i<comlist.length;i++){
			   var obj = comlist[i];
			   
			   html.push("<li ccomid='"+obj.ccompanyID+"' id='"+obj.companyid+"'  data-staff='"+obj.staffID+"'>");
			   html.push("<img width = '55rem' height='55rem' src='"+basePath+obj.logopath+"'  onerror='this.src=\""+ basePath +"/images/WFJClient/pc/5l5c/img_08.png\"'>");
			   html.push("<div class='div-right'>");
			   html.push("<p class='p-top'>");
			   html.push(obj.companyname);
			   html.push("</p>");
			   html.push("<p class='p-bottom'>");
			   html.push(obj.companyAddr);
			   html.push("</p>");
			   html.push("</div>");
			   html.push("</li>");
		   }
		   $(".xz").append(html.join(""));
	   },
	   error:function(data){
	   }
	});
	
	
}