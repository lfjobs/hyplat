$(document).ready(function() {
		$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '产品设计-集团汇总',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/productdesigngroup/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
		}
	}
	//模糊查询
	
	$("#tosearch").click(function() {
	$("#postSearchForm").attr(
			"action",
			basePath + "/ea/productdesigngroup/ea_toSearch.jspa?pageNumber="
					+ pNumber);
	document.postSearchForm.submit.click();
	
});

// 查询总公司下的所有子公司
	$(function() {
		var pid="";
		var pname="";
		if(cc == "jt"){
			pid=parent.frames["leftFrame"].tree.getParentId(parent.frames["leftFrame"].tree.getSelectedItemId());
			pname=parent.parent.frames["leftFrame"].tree.getItemText(pid);
		}else{
			pid=parent.frames["leftFrame"].tree.getSelectedItemId();
			pname=parent.parent.frames["leftFrame"].tree.getItemText(pid);
		}
		
				var url = basePath
						+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var companylist = member.companylist;
								var data1 = new Array();
								data1[0] = {
									id : pid,
									pid : '-1',
									text :pname
								};
								for (var i = 0; i < companylist.length; i++) {
									data1[i + 1] = {
										id : companylist[i].companyID,
										pid : companylist[i].companyPID,
										text : companylist[i].companyName
									};
								}
								var ts3 = new TreeSelector($("#deptID")[0],
										data1, -1);
								ts3.createTree();
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
				// 公司名称change事件
				$("#deptID").change(function() {
							if ($(this).val() != '') {
								bmDept(this.value);
							} else {
								$("option", $("#orgID")).remove();
								$("#orgID")
										.html("<option value=''>请选择公司</option>");
							}
						});
				// 部门名称change事件
				$("#orgID").change(function() {
							var temp = $("#orgID").val();
							if (temp.substring(0, 7) != 'company') {
								getPerson($("#deptID").val(), this.value);
							} else {
								$("option", $("select#person")).remove();
								$("#person")
										.html("<option value=''>请选择部门</option>");
							}
						});

			});

			
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();

			});
});
// 根据公司ID和部门ID查询责任人
function getPerson(company, org) {
	$("option", $("select#person")).remove();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffList.jspa?currentCompanyID="
			+ company + "&currentOrgnizationID=" + org + "&date111="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>全部</option>";
			for (var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj[0] + "'>" + obj[1] + "</option>";
			}
			$("#person").html(str);
		}
	});
}

// 根据公司ID查询对应的部门列表
function bmDept(val) {

	$("option", $("#orgID")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					/** **添加部门列表** */
					var member = eval("(" + data + ")");
					var oList = member.organizationlist;
					var data2 = new Array();
					data2[0] = {
						id : $("#deptID").attr("value"),
						pid : '-1',
						text : '全部'
					};
					for (var i = 0; i < oList.length; i++) {
						data2[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					ts = new TreeSelector($("#orgID")[0], data2, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#orgID")).val("");
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/productdesigngroup/ea_getListProductdesign.jspa?cc=jt&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}