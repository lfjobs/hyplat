$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	getSettingInfo();

	$("#totransfer").click(function() {
		
		var dealerID = 	$("#dealerID").val();
		
		var dealerName = $("#dealerName").val();
		var check;
		if($("#trasData").attr("checked")==true){
			check = "yes";
		}else{
			check = "no";
		}
		var url = basePath + "ea/extralflow/sajax_ea_transferPermission.jspa?date="
				+ new Date();
		$.ajax({
					url : url,
					type : "get",
					async : "false",
					dataType : "json",
					data:{
						dealerID:dealerID,
						dealerName:dealerName,
						check:check
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						if(result=="suc"){
							alert("操作成功!");
						}
					},
					error : function() {
						alert("读取数据失败");
					}

				});

	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/extralflow/ea_getfinishedList.jspa?type=examine&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function getSettingInfo() {
	var url = basePath + "ea/extralflow/sajax_ea_getSetInfo.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : "false",
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "cstaff20110712KAX2RHUQZI0000025385") {
						$("#dealerID").val(result);
						$("#dealerName").val("管理员");
					} else {
						$("#dealerID").val(result.dealerID);
						$("#dealerName").val(result.dealerName);
					}
				},
				error : function() {
					alert("读取数据失败");
				}

			});
}

// 选择人员
function importGY(url) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date());

	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择");
		return;
	}

	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	$("#dealerName").val(staffName);
	$("#dealerID").val(childopertionID);

	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}
