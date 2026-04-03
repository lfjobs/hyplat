$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		height : 165,
		width : 'auto',
		minwidth : 30,
		title : '车辆信息公司汇总管理',
		minheight : 80,
		buttons : [{
			name : '查询',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		},{
			name : '导出',
			bclass : 'excel',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}]
	});
			
	function action(com, grid) {
		switch (com) {
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/carcompany/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
		}
	}	
	
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		carID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	//车辆搜索
	$("#searchCar").click(function() {
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/carcompany/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});	
});

//查询部门事件
$(function(){
	var url = basePath
			+ "ea/corganization/sajax_ea_getOrganizationLists.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"organizationID" : companyID
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var organizationList = member.organizationList;
					var str = "<option value=''>请选择</option>";
					for (var i = 0; i < organizationList.length; i++) {
						var obj = organizationList[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#organizationID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}		
	});	
});