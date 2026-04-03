$(function(){
	var leng=$(".dd").find("p").length;
	for(var i=leng;i>0;i--){
		if(i!=1)
			$(".dd").parent().find("font").append($(".dd").find("p").eq(i-1).text()+"&nbsp;>>>&nbsp;");
		else
			$(".dd").parent().find("font").append($(".dd").find("p").eq(i-1).text());
	}
	$(".number").keydown(function(e){
		if(!(e.keyCode>=48&&e.keyCode<=57)&&!(e.keyCode>=96&&e.keyCode<=105)&&e.keyCode!=8){
			return false;
		}
	});
	
	var cuID;
	$(".turns").click(function(){
		if($(this).val()=="查询"){
			cuID = "";
			var parameter = $("input#parameterrm",
					$("table#searchdept")).val();
			var selectDept = $(":input#selectdept").val();
			getStaffMember("parameter="+parameter+"&selectDept="+orgId);
		}else if($(this).val()=="确定"){
			$(".staffName").val($("#"+staffId).find("#staffname").text());
			$(".staffID").val(staffId);
			$(".staffName").css("background-color","#FFFFFF").removeClass("error");
			$("#deptjqModel").hide();
			 $("input#parameterrm",$("table#searchdept")).val("");
		}else{
			$("#deptjqModel").hide();
			 $("input#parameterrm",$("table#searchdept")).val("");
		}
	});
	


	// 下一页
	$("#dpxy").click(
			function() {
				var xy = $("#dpxy").attr("title");
				if (xy != 0) {
					cuID = "";
					
					var parameter = $("input#parameterrm",
							$("table#searchdept")).val();
					var selectDept = $(":input#selectdept").val();
					getStaffMember("parameter="+parameter+"&selectDept="+orgId+ "&pageForm.pageNumber=" + xy);
				} else {
					alert("已是尾页！");
				}
			});
	
	// 上一页
	$("#dpsy").click(
			function() {
				var sy = $("#dpsy").attr("title");
				if (sy != 0) {
				
					
					var parameter = $("input#parameterrm",
							$("table#searchdept")).val();
					var selectDept = $(":input#selectdept").val();
					getStaffMember("parameter="+parameter+"&selectDept="+orgId+"&pageForm.pageNumber=" + sy);
				} else {
					alert("已是首页！");
				}
			});

	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("class")=="staff")
			staffId=$(this).attr("id");
	});

	//选择往来个人
	$(".staffName").click(function(){
		$("#deptjqModel").show();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	
	$("#save").click(function(){
		var outQuantity=$(".outQuantity").val();
		var oQuantity=$(".oQuantity").val();
		if(typeof(oQuantity)=="undefined")
			oQuantity="0";
		var sQuantity=$(".sQuantity").val();
		var iQuantity=$(".iQuantity").val();
		var startTime=$(".startTime").val();
		var endTime=$(".endTime").val();
		var teachingSupervisor=$(".teachingSupervisor").val();
		//if(outQuantity==""||parseInt(outQuantity)>parseInt(iQuantity)){
		//	alert("当前出库量不可超过库存量，总出库数量不可超过生产数量,也不可为空");
		//	return;
		//}
		//if(startTime==""||endTime==""||startTime>endTime){
		//	alert("起止时间错误");
		//	return;
		//}
		//if(teachingSupervisor==""){
		//	alert("请输入教学主管");
		//	return;
		//}
		$("#assForm").attr("target", "hidden").attr("action",basePath+"ea/assembly/ea_getProductAdd.jspa");
		document.assForm.assSubmit.click();
		token = 2;
	});
	$(".teaching").click(function(){
		$(this).attr("id","coach");
		$(".journal_title").text($(this).parent().parent().find("td").eq(0).text().split("：")[0]);
		var strs=$(this).parent().find("input").attr("name").split(" | ");
		for(var i=0;i<strs.length;i++){
			$(".journal_content_inner").append("<div>"+strs[i]+"</div>");
		}
		$(".coachPopup").parent().show();
		$(".journal_content_inner").css("width",(parseInt($(".journal_content_outer").width())+18)+"px");
	});
	$(".journal_Determine").click(function(){
		$("#coach").val($(".journal_content").val());
		$("#coach").attr("id","");
		$(".journal_title").text("");
		$(".journal_content_inner").html("");
		$(".coachPopup").parent().hide();
		$(".journal_content").val("");
	});
	$(".journal_Close").click(function(){
		$("#coach").attr("id","");
		$(".journal_title").text("");
		$(".journal_content_inner").html("");
		$(".coachPopup").parent().hide();
		$(".journal_content").val("");
	});
});

//获取员工
function getStaffMember(typeCN) {


	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	
	
	$
			.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";

					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}

					$("span#dpzycount").text(zys);

					for ( var i = 0; i < pageForm.list.length; i++) {

						
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
						
						
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";

					}
					
					
					$("#body_02dept").html(tabletr);


				},
				error : function cbf(data) {
					notoken = 0;

				}
			});
}
function re_load(){
	 window.opener.location.href = window.opener.location.href; 
	window.close();
}