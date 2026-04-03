$(document).ready(function() {
	ajax();
	$('.search').bind('input propertychange', function() {
		$(".site_wrap").empty();
		pageNumber = 0;
		ajax();
	});

    //分页
    $(window).scroll(function(){
    if($(".last").length>0){
            if ($(".last").offset().top < $(window).height()) {
                if(pageNumber<pageCount){
                    ajax();
                }
            }
        }
    });
	
})

// function getHeight() {
// 	t = setTimeout("getHeight()", 2000);
// 	if($(".last").length>0){
// 		if ($(".last").offset().top < $(window).height()) {
// 			if(pageNumber<pageCount){
// 				ajax();
// 			}
// 		}
// 	}
// }

function ajax() {
	var search = $(".search").val();
	var status = $(".select_box").attr("date-status");
	var url = basePath + "ea/qrshare/sajax_ea_queryTheVehicle.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"pageForm.pageNumber" : pageNumber + 1,
			"pageForm.pageSize" : 50,
			"caccount.companyID" : companyId,
			"carManage.carNumber":search,
			"carManage.status":status,
	         posNum:posNum
		},
		success : function(data) {
			var place = eval("(" + data + ")");
			var pageForm = place.pageForm;
			$(".last").removeClass("last");
			var place = [];
			if (pageForm != null && pageForm.list != null
					&& pageForm.list.length > 0) {
				$(pageForm.list).each(function(i, dom) {
					if ($(pageForm.list).length - 1 == i) {
						place.push("<a href='javascript:void(0)' class='site_box last' onclick='particulars(this)'>");
					} else {
						place.push("<a href='javascript:void(0)' class='site_box' onclick='particulars(this)'>");
					}
					place.push("<input type='hidden' class='carmID' value='"+this[0]+"'/>");
					place.push("<input type='hidden' class='status' value='"+this[6]+"'/>");
                    place.push("<input type='hidden' class='carNumber' value='"+this[1]+"'/>");
                    place.push("<input type='hidden' class='auditStatus' value='"+this[7]+"'/>");
					place.push("<div class='site_tit clearfix'>");
					place.push("<div class='plate_num'><i></i>"+this[1]+"</div>");
					place.push("<div class='sch_name'>"+this[2]+"</div>");
					place.push("</div>");
					place.push("<div class='site_con clearfix'>");
					place.push("<div class='site_time'>");
					if(this[3]!=null){
						place.push("<div><span class='green'>起</span>："+this[3]+"</div>");
					}else{
						place.push("<div><span class='green'>起</span>：空</div>");
					}
					if(this[4]!=null){
						place.push("<div><span class='red'>止</span>："+this[4]+"</div>");
					}else{
						place.push("<div><span class='red'>止</span>：空</div>");
					}
					place.push("</div>");
					if(this[5]!=null){
						place.push("<div class='site_R'>共计："+this[5]+"</div>");
					}else{
						place.push("<div class='site_R'>共计：0分</div>");
					}
                   var r = "" ;
					if(this[7]=="01"){
                        place.push("<div class='site_T' style='color:#ffad10'>人工审核中|");
					}else if(this[7]=="02"){
                        place.push("<div class='site_T'>人工审核通过|");
					}else if(this[7]=="03"){
                        place.push("<div class='site_T' style='color:#ff1a1a'>人工审核驳回|");
                    }else if(this[7]=="00"){
                        place.push("<div class='site_T' style='color:#ff5415'>未提交审核|");

                    }else{
                        place.push("<div class='site_T' style='color:#0d9908'>自动审核通过|");
					}




					if(this[6]=="1"){
						place.push("<span class='jr'>进入</span>");
					}else if(this[6]=="0"){
						place.push("<span class='lk'>离开</span>");
					}else if(this[6]=="2"){
						place.push("<span class='yc'>异常</span>");
					}		
					place.push("</div>");
					place.push("</div>");
					place.push("</a>");
				})
			}
			$(".site_wrap").append(place.join(""));
			if (pageForm != null) {
				pageNumber = pageForm.pageNumber;
				pageCount = pageForm.pageCount;
				// if (pageNumber < pageCount) {
				// 	getHeight();
				// }else{
                 //    clearTimeout(t);
				// }
			}
		}
	})
}

function particulars(obj){
	var carmID = $(obj).find(".carmID").val();
	var status = $(obj).find(".status").val();
    var carNumber = $(obj).find(".carNumber").val();
    var  auditStatus = $(obj).find(".auditStatus").val();
	if (status=="0" || status=="1"){
		document.location.href = basePath+"ea/qrshare/ea_theVehicleDetails.jspa?carManage.carmID="+carmID+"&posNum="+posNum+"&carManage.carNumber="+carNumber+"&auditStatus="+auditStatus;
	}else if (status=="2"){
		document.location.href = basePath+"ea/qrshare/ea_abnormalJump.jspa?carManage.carmID="+carmID+"&posNum="+posNum;
	}
	
}
function feeAudit(){
    document.location.href = basePath+"ea/carmanage/ea_getFeeAuditList.jspa?posNum="+posNum;
}
