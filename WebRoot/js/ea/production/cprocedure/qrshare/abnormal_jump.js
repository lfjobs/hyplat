$(document).ready(function() {
	//提交匹配
	$(".btn").click(function(){
		if($(".mate").find(".active").length>0){
			var carmID2 = $(".mate").find(".active").find(".carmID").val();
			var url = basePath + "ea/carmanage/sajax_ea_errorCorrection.jspa";
			$.ajax({
				url : url,
				type : "post",
				async : false,
				dataType : "json",
				data:{
					"cm.carmID":carmID,
					"carmID":carmID2
				},
				success : function(data) {
					var standard = eval("(" + data + ")");
					if(standard.boolean){
						document.location.href = basePath+"/ea/qrshare/ea_jumpManagement.jspa";
					}else{
						alert("修改失败");
					}
				},
				error : function(data) {
					alert("修改失败");
				}
			});
		}else{
			alert("请选择匹配车辆");
		}
	})
})


//修改车牌号
function ajax1(carmID,t,vid){
	var url = basePath + "ea/qrshare/sajax_ea_addOrUpdateVehicle.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data :{
			"carManage.carNumber":t,
			"carManage.carmID":carmID
		},
		success : function(data) {
			var vehicle = eval("(" + data + ")");
			var boolean = vehicle.boolean;
			if(boolean){
				ajax2(t,vid);
			}else{
				alert("修改车牌失败");
			}
		},
		error : function(data) {
			alert("修改车牌失败");
		}
	});
}
//匹配数据
function ajax2(t,vid){
	var url = basePath + "ea/qrshare/sajax_ea_errorLicensePlateDetails.jspa";
	$.ajax({
		url : url,
		type : "post",
		async : false,
		dataType : "json",
		data :{
			"carManage.carNumber":t,
			"vf.siteId":vid,
			posNum:posNum
		},
		success : function(data) {
			var vehicle = eval("(" + data + ")");
			var list = vehicle.list;
			$(".mate").find("ul").empty();
			if(list!=null){
				var a =[];
				$(list).each(function(i, dom) {
					a.push("<li date-panorama='"+this[11]+"' date-picture='"+this[12]+"'>");
					a.push("<input type='hidden' class='carmID' value='"+this[0]+"'>");
					a.push("<div class='img'>");
					a.push("<img src='"+basePath+this[11]+"'>");
					a.push("<p><span>1</span>/2</p>");
					a.push("<img src='"+basePath+"/images/ea/production/qrshare/car-l.png' class='l'>");
					a.push("</div>");
					a.push("<div class='text'>");
					a.push("<div class='ipt'>");
					a.push("<input type='text' readonly value='"+this[1]+"'>");
					a.push("</div>");
					a.push("<p class='time'>"+this[2]+"（入）</p>");
					a.push("<p class='site'>"+this[4]+"</p>");
					a.push("</div>");
					a.push("<div class='select'>");
					a.push("<img src='"+basePath+"/images/WFJClient/PersonalJoining/companyHomepage/gou.png'>");
					a.push("</div>");
					a.push("</li>");
					a.push("");
				})
				$(".mate").find("ul").append(a.join(""));
				$(".mate").find("ul").show();
				$(".mate").find(".not").hide();
			}else{
				$(".mate").find(".not").show();
				$(".mate").find("ul").hide();
			}
		},
		error : function(data) {
			alert("查询失败");
		}
	});
}
