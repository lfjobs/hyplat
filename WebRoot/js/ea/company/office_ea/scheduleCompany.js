$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '公共日程安排公司汇总',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
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
				url = basePath
						+ "ea/schedulecompany/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
		}
	}
	
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath + "ea/schedulecompany/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});

function re_load() {
	document.location.href = basePath
			+ "ea/schedulecompany/ea_getScheduleCompanyList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

$(function() {
	var url = basePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+comID+"&date1="+new Date(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.organizationlist;
                var data2 = new Array();
		        data2[0] = {
	                id: comID,
	                pid: '-1',
	                text: '请选择部门'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].organizationID,
                        pid: oList[i].organizationPID,
                        text: oList[i].organizationName
                    };
                }
                ts = new TreeSelector($("#corganizationsID")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});