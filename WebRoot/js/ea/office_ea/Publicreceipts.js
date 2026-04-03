$(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if (labelTag == '00') {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : '审核管理科',
					minheight : 80,
					buttons : [{
						name : '查看',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
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
					}, {
						name : '打印预览',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : labelTag == '00' ? '主管审核通过' : '人事审核通过',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : labelTag == '00' ? '主管驳回作废' : '人事驳回作废',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}]
				});
	} else {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : '审核管理科',
					minheight : 80,
					buttons : [{
						name : '查看',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
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
					}, {
						name : '打印预览',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : labelTag == '00' ? '主管审核通过' : '人事审核通过',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : labelTag == '00' ? '主管驳回作废' : '人事驳回作废',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '撤销',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}]
				});
	}

	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (prID == "") {
					alert('请选择!');
					return;
				}
				var type = $.trim($("tr#" + prID).find("span#type").text());
				switch (true) {
					case type == '奖励单' || type == '扣分单' :
						document.location.href = basePath
								+ "ea/publicreceipts/ea_editReward.jspa?pageNumber="
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag;
						break;
						
					case type == '金币折扣单' :
						document.location.href = basePath
							    + "ea/goldticket/ea_getDetailsFine.jspa?pageNumber="														
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag+"&flag=0";													
					break;	
						
						
						
					case type == '员工级别变更单' :
						document.location.href = basePath
								+ "ea/publicreceipts/ea_toRank.jspa?pageNumber="
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag;
						break;
					case type == '员工请假申请单' :
						document.location.href = basePath
								+ "ea/publicreceipts/ea_toLeave.jspa?pageNumber="
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag;
						break;
					case type == '员工加班申请单' :
						document.location.href = basePath
								+ "ea/publicreceipts/ea_toOver.jspa?pageNumber="
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag;
						break;
				}
				break;
			case labelTag == "00" ? '主管审核通过' : '人事审核通过' :
				if (prID == "") {
					alert('请选择!');
					return;
				}
				if (labelTag == "01") {
					action('查看');
					return;
				}
				if (confirm("确定通过？")){
					var type = $.trim($("tr#" + prID).find("span#type").text());				
					if(type == '金币折扣单'){
						FinePass(labelTag == "00" ? '主管审核通过' : '人事审核通过');
					}else{
						AjaxFunc(labelTag == "00" ? '主管审核通过' : '人事审核通过');
					}
				}											
				break;
			case labelTag == "00" ? '主管驳回作废' : '人事驳回作废' :
				if (prID == "") {
					alert('请选择!');
					return;
				}
				if (confirm("确定驳回？")){
					var type = $.trim($("tr#" + prID).find("span#type").text());				
					if(type == '金币折扣单'){
						FinePass(labelTag == "00" ? '主管驳回作废' : '人事驳回作废');
					}else{
						AjaxFunc(labelTag == "00" ? '主管驳回作废' : '人事驳回作废');
					}					
				}									
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/publicreceipts/ea_ShowExcelPublic.jspa?pageNumber="
						+ ppageNumber + "&search=" + search + "&labelTag="
						+ labelTag;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?search="
						+ search + "&labelTag=" + labelTag;
				numback(url);
				break;
			case '打印预览' :
				if (prID == "") {
					alert('请选择!');
					return;
				}
				var type = $.trim($("tr#" + prID).find("span#type").text());
				switch (true) {
					case type == '奖励单' || type == '扣分单' :
						url = basePath
								+ "ea/publicreceipts/ea_printReward.jspa?prID="
								+ prID;
						open(url);
						break;
						
					case type == '金币折扣单' :
						url = basePath
							    + "ea/goldticket/ea_getDetailsFine.jspa?pageNumber="														
								+ ppageNumber + "&pageForm.pageNumber=" + pnum
								+ "&search=" + search + "&prID=" + prID
								+ "&labelTag=" + labelTag+"&flag=0"+"&print=print";
					open(url);
					break;	
									
					case type == '员工级别变更单' :
						window.open(basePath
								+ "ea/publicreceipts/ea_toprintRank.jspa?prID="
								+ prID);
						break;
					case type == '员工请假申请单' :
						window
								.open(basePath
										+ "ea/publicreceipts/ea_printLeaveBill.jspa?prID="
										+ prID);
						break;
					case type == '员工加班申请单' :
						url = basePath
								+ "ea/publicreceipts/ea_toprintOver.jspa?prID="
								+ prID;
						open(url);
						break;
				}
				break;
			case '撤销' :
				if (labelTag == '01') {
					if (prID == "") {
						alert('请选择!');
						return;
					}					
					var type = $.trim($("tr#" + prID).find("span#type").text());
					if(type == '金币折扣单'){
						alert("金币折扣，暂不能撤销！");
						return;
					}		
					var backStatus = $.trim($("tr#" + prID)
							.find("span#receiptsStatus").text());
					var billstype = $.trim($("tr#" + prID).find("span#type")
							.text());
					if (backStatus == '人力资源部审核通过') {
						if (confirm("确定撤销？")) {
							document.location.href = basePath
									+ "ea/publicreceipts/ea_toBackBills.jspa?prID="
									+ prID + "&publicreceipts.type="
									+ billstype + "&labelTag=01";
						}
					} else {
						alert('数据未归档，不能撤销！');
					}
				}
				break;
		}
	}
	
	//金币折扣单
	function FinePass(type){
		var status;
		if(type == '主管审核通过'){
			status = '部门主管审核通过';
		}else if(type == '人事审核通过'){
			status = '人力资源部审核通过';
		}else if(type == '主管驳回作废'){
			status = '部门主管驳回作废';
		}else if(type == '人事驳回作废'){
			status = '人力资源部驳回作废';
		}
		var url = basePath+"ea/goldticket/sajax_ea_PublicreceiptsAudit.jspa?prID="+prID+"&types="+status;
		$.ajax({
		  	  url: encodeURI(url),
             type: "get",
             async: false,
             dataType: "json",
             success: function cbf(data){
             var member = eval("(" + data + ")");
             var str = member.str;	                       
	            alert(str);
	            document.location.href = basePath
				+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&labelTag="
				+ labelTag;
             },
             error: function cbf(data){
                 alert("数据获取失败！");
             }
		});
		
		
		
		
	}
	

	
	function AjaxFunc(types) {
		if (notoken) {
			alert("正在加载数据");
			return;
		}
		notoken = 1;
		var receiptsStatus = $.trim($("tr#" + prID).find("span#receiptsStatus")
				.text());
		if (receiptsStatus == (labelTag == '00' ? '待审' : '部门主管审核通过')) {
					
			var url = basePath
				+ "ea/publicreceipts/sajax_ea_AjaxPublicreceiptsAudit.jspa?prID="
				+ prID + "&date1=" + new Date() + "&types=" + types;
				
			$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var publicrece = member.publicrece;
					var typet = "";
					if (types == '主管审核通过') {
						typet = "部门主管审核通过";
						$("tr#" + prID).find("span#firstAuditor")
								.text(publicrece);
					} else if (types == '主管驳回作废') {
						typet = "驳回作废";
						$("tr#" + prID).find("span#firstAuditor")
								.text(publicrece);
					} else if (types == '人事审核通过') {
						typet = "人力资源部审核通过";
						$("tr#" + prID).find("span#secondAuditor")
								.text(publicrece);
					} else if (types == '人事驳回作废') {
						typet = "驳回作废";
						$("tr#" + prID).find("span#secondAuditor")
								.text(publicrece);
					}
					$("tr#" + prID).find("span#receiptsStatus").text(typet);
					alert("操作成功!");
					document.location.href = basePath
							+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="
							+ ppageNumber + "&pageForm.pageNumber="
							+ $("#pageNumber").attr("value") + "&labelTag="
							+ labelTag;
				},
				error : function cbf(data) {
					alert("数据获取失败！");
					notoken = 0;
				}
			});
		} else {
			alert("数据已归档");
			notoken = 0;
			return;
		}

	}
	$(".JQueryflexme tr[id]").each(function() { // 页面遍历判断附件格式
				var load = $("tr#" + this.id).find("#look1").text();
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
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				prID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/publicreceipts/ea_toSearchPublicreceipts.jspa?pageNumber="
								+ ppageNumber + "&labelTag=" + labelTag);
		document.postSearchForm.submit.click();
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&labelTag=" + labelTag;
}