$(document).ready(function(){
	/*
	 *  放入个人草稿箱
	 */
	$("#toDrafts").click(function(){
				if($(".service_content").find("input[type='checkbox']:checked").length>0){
					 if(confirm("确定要将选中公文转至个人收件箱吗？"))
					   {
					   alert("转入成功");
					   $(".service_content").find("input[type='checkbox']").attr("checked",false);
					   }
				}else{
					
					alert("请选择具体公文！");	
				}
		});
	/*
	 *  传阅公文
	 */
	$("#circularize").click(function(){
		//$(".swiper-slide").html("some text");
				if($(".service_content").find("input[type='checkbox']:checked").length>0){
					/* if(confirm("确定要将选中公文转至个人收件箱吗？"))
					   {
					   alert("转入成功");
					   $(".service_content").find("input[type='checkbox']").attr("checked",false);
					   }*/
					var url = basePath
					+ "ea/costsheet/sajax_ea_seachgroupsync.jspa?";
					$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var oList = member.company;
							;
							var data = new Array();
							var options = "";
							for ( var i = 0; i < oList.length; i++) {
								options += "<select><option onclick='javascript:test1(\""
										+  oList[i].companyID
										+ "\")' title='"
										+  oList[i].companyName
										+ "'>"+oList[i].companyName+"";
								options += "</option></select>";

							}
							$('#selectrecivecom').html(options);
							//$(options).appendTo("#selectrecivepeople");
					
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}

					});
					
				
					$("#circularizedialog").dialog({
					    modal:true 
					    
					});
					$("#circularizedialog").html($("#chuanyhue").html())
					$(".service_content").find("input[type='checkbox']").attr("checked",false);					 
				}else{
					
					alert("请选择具体公文！");	
				}
		});
	
	
})

function seedoc(){
$("#documentpreview").dialog({
    modal:true 
    
});
	$("#documentpreview").empty();
	$("#documentpreview").html("<img src= '"+basePath+"images/websuitMini/loading.gif' style='margin-top: 20%;margin-left: 50%'/></br>");
var url = basePath + "ea/company/sajax_n_ea_getCompanyList.jspa?date1="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
				$("#documentpreview img").hide();
					$("#documentpreview").html($("#message").html());
					
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}


function test1(val) {
	// $("#companyID").val(val);
	var url = basePath + "ea/costsheet/sajax_ea_seachgroupsync.jspa?date="
			+ new Date().toLocaleString() + "&companyID=" + val;
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var departmentList = member.departmentList;
			var options = "";
			for ( var i = 0; i < departmentList.length; i++) {
				options += "<select><option onclick='javascript:searchRenyuan(\""
										+  val
										+ "\",\""
										+  departmentList[i].organizationID
										+ "\")' title='"
					+ departmentList[i].organizationName
					+ "'>"+ departmentList[i].organizationName+"";
			options += "</option></select>";

		}
		$("select#selectrecivedep").html(options);
		//$(options).appendTo("#selectrecivepeople");

	},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
//根据公司部门查询当前所选部门的员工

function searchRenyuan(companyid, org) {
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffListfordep.jspa?";
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		data:{
			currentCompanyID:companyid,
			currentOrgnizationID:org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var depstaff = member.list;
			var options = "";
			for ( var i = 0; i < depstaff.length; i++) {
				options += "<select><option   id='"
+ depstaff[i].staffID 
+ "'>"+ depstaff[i].staffName+"";
options += "</option></select>";

}
$("select#selectrecivepeople").html(options);
//$(options).appendTo("#selectrecivepeople");

},
	});
}
