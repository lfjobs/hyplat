$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '员工请假单公司汇总',
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
						+ "ea/leavecompany/ea_showLeaveCompanyExcel.jspa?search="
						+ psearch;
				open(url);
				break;
		}
	}

	$(".JQueryflexme tr[id]").each(function() { // 页面遍历判断附件格式
		var load = $("tr#" + this.id).find("#look1").text();// 取出附件在服务器上存放地址
		if (load != '') {
			var onload = load.substring(load.lastIndexOf("."),
					load.length);
			if (onload.toLowerCase() != ".jpg"
					&& onload.toLowerCase() != ".gif"
					&& onload.toLowerCase() != ".png") {
				$("tr#" + this.id).find("#load").show();
			} else {
				$("tr#" + this.id).find("#look").show();
			}
		} else {
			$("tr#" + this.id).find("#wu").show();
		}
	});

	$("#tosearch").click(function() {// 查询请求事件
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/leavecompany/ea_toLeaveCompanySearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});

function re_load() {
	document.location.href = basePath
			+ "ea/leavecompany/ea_getLeaveCompanyList.jspa?search="
			+ psearch + "&pageNumber=" + ppageNumber
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
                ts = new TreeSelector($("#principalOrganizationID")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});