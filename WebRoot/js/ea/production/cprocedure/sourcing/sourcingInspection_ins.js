window.onload=function(){
	$(".tableOuter").css("width",$(".tableSample").width()+"px");
	$(".tableInner").css("width",($(".tableSample").width()+17)+"px");
	$(".tableOuter").find("table").css("width",$(".tableSample").width()+"px");
};
$(function(){
	$(".operation").click(function(){
		if($(this).val()=="提交"){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/sourcingsto/ea_addInspection.jspa?fiveClear="+fiveClear);
			document.form.submit.click();
			token = 2;
		}else{
			window.close();
		}
	});
	//选择往来个人
	$("#staffsName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	//添加选中的往来个人
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			getStaffMember("selectDept="+orgId+"&parameter="+$("#searchdept").find("#parameterrm").val());
		}else if($(this).val()=="确定"){
			$("#staffsName").val($("#"+staffId).find("#staffname").text());
			$("#staffsID").val(staffId);
		//	$(".staffName").css("background-color","#FFFFFF").removeClass("error");
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	$("tr[id]").live("click",function(){
		if($(this).attr("class")=="staff"){
			staffId=this.id;
			$(this).find(".radio").attr("checked","checked");
		}
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});

//获取员工
function getStaffMember(typeCN) {
	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	$.ajax({
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
	window.opener.location.href=window.opener.location.href;
	window.close();
}