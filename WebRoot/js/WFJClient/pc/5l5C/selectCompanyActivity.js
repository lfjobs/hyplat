$(function(){
    //点击公司列表
	$(document).on("click",".div-con li",function(){
		var companyID = $(this).attr("id");
		var staffID = $(this).attr("data-staff");
		var ccomid =  $(this).attr("ccomid");
		//查看该公司是否有抽奖
		$.ajax({
			url: basePath + "ea/lottery/sajax_ea_ajaxIsDraw.jspa?model.companyId=" + companyID,
			type: "get",
			async: false,
			success: function (data) {
				var member = eval("(" + data + ")");
				if (member.model != null) {
					document.location.href = basePath + "/ea/lottery/ea_goLottery.jspa?model.companyId=" + companyID
						+ "&model.activityId=" + member.model.activityId + "&ccompanyId=" + ccomid + "&selectType=" + bd;
				}
				else {
					alert("本公司没有抽奖");
				}
			}
		});
	});
	load();
});

function load(){
	$(".selectCompany").hide();
	var url = basePath+"ea/android/sajax_ea_findCompanyActivity.jspa";
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
		   if (comlist == null||comlist.length==0) {
			   $(".selectCompany").show();
		   }else {
			   $(".selectCompany").hide();
			   for(var i = 0;i<comlist.length;i++){
				   var obj = comlist[i];

				   html.push("<li ccomid='"+obj.ccompanyID+"' id='"+obj.companyid+"'  data-staff='"+obj.staffID+"'>");
				   html.push("<img style='margin-top: 20px;' width = '55rem' height='55rem' src='"+basePath+obj.logopath+"'  onerror='this.src=\""+ basePath +"/images/ea/lottery/act_nav01.png\"'>");
				   html.push("<div class='div-right' style='padding-left: 60px;'>");
				   html.push("<p class='p-top'>");
				   html.push(obj.activityName);
				   html.push("</p>");
				   html.push("<p class='p-bottom'>");
				   html.push(obj.companyname);
				   html.push("</p>");
				   html.push("<p class='p-bottom'>");
				   html.push(obj.companyAddr);
				   html.push("</p>");
				   html.push("</div>");
				   html.push("</li>");
			   }
			   $(".xz").append(html.join(""));
		   }
	   },
	   error:function(data){
	   }
	});
}