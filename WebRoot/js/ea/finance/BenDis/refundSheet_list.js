$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	var html = $(".query").html();
	$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '同意退款并退货',
					bclass : 'examine',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '拒绝',
					bclass : 'noapprove',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '确认收货',
					bclass : 'excel',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '退款给买家',
					bclass : 'delete',
					onpress : action
						//
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
					name : '打印',
					bclass : 'printer',
					onpress : action

				}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {

			case '查看' :
				if (rsid == "") {
					alert('请选择!');
					return;
				}

				window
						.open(basePath
								+ "ea/refund/ea_getEditOrPrintPage.jspa?refundSheet.rsid="
								+ rsid + "&voptype=e&type=pc");
				break;

			case '同意退款并退货' :
				if (rsid == "") {
					alert('请选择!');
					return;
				}

				var refundType = $("tr#" + rsid).find("#refundType").text();
				if (refundType == "00") {
					alert("仅退款,请直接退款转给买家");
					return;
				}

				var refundstate = $("tr#" + rsid).find("#refundstate").text();
				if (refundstate == "01") {
					alert("不可重复操作");
					return;
				}

				window.open(basePath
						+ "ea/refund/ea_approveOrejectPage.jspa?refundSheet.rsid="
						+ rsid+"&state=01&type=pc");

				break;

			case '拒绝' :
				if (rsid == "") {
					alert('请选择!');
					return;
				}

				

				var refundstate = $("tr#" + rsid).find("#refundstate").text();
				if (refundstate == "02") {
					alert("不可重复操作");
					return;
				}
				if (refundstate == "00") {
				     window.open(basePath
						+ "ea/refund/ea_approveOrejectPage.jspa?refundSheet.rsid="
						+ rsid+"&state=02&type=pc");
				}else{
					alert("无法操作");
					return;
				}

			

				break;
			case '确认收货' :

				if (rsid == "") {
					alert('请选择!');
					return;
				}

				/*var refundType = $("tr#" + rsid).find("#refundType").text();
				if (refundType == "00") {
					alert("仅退款,请直接退款转给买家");
					return;
				}

				var refundstate = $("tr#" + rsid).find("#refundstate").text();
				if (refundstate== "03") {
					alert("不可重复确认收货");
					return;
				}
				if (refundstate == "00"||refundstate=="02") {
					alert("同意退货退款,才能确认收货");
					return;
				}*/
			

				if (confirm("确定要确认收货？")) {
					$("#rsid").val(rsid);

					$("#SearchForm").attr("target", "hidden").attr("action",
							basePath + "ea/refund/ea_confirmRefund.jspa");
					document.SearchForm.submit.click();
					token = 2;

				}
				break;
			case '退款给买家' :
				if (rsid == "") {
					alert('请选择!');
					return;
				}
				var url = basePath
								+ "ea/refund/ea_backMoneytoBuyer.jspa";
				window.open(url);
//				$.ajax({
//					url:url,
//					type:"get",
//					dataType:"json",
//					async:false,
//					data:{
//						"refundSheet.rsid":rsid
//					},
//					success:function(data){
//						
//						var me = eval("("+data+")")
//						var result = me.result;
////						if(result=="success"){
////							alert("退款成功，预计1-3个工作日到账！");
////						}else{
////							alert("退款失败，请联系系统管理员");
////						}
//					},error:function(data){
//						alert("退款失败！请联系系统管理员");
//					}
				
				//});

				
				break;

			case '导出' :
				var url = basePath + "ea/refund/ea_showExcel.jspa?search="
						+ search+"&stype="+stype;
				window.open(url);
				break;
			case '打印' :
				if (rsid == "") {
					alert('请选择!');
					return;
				}

				window
						.open(basePath
								+ "ea/refund/ea_getEditOrPrintPage.jspa?refundSheet.rsid="
								+ rsid + "&voptype=p");

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/refund/ea_getRefundSheetList.jspa?search="
						+ search+"&stype="+stype;
				numback(url);
				break;

		}
	}
	$("div.bDiv", $("div.flexigrid")).css("height", "400px");
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				rsid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});

	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/refund/ea_toSearch.jspa?pageNumber="
						+ ppageNumber + "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();

	});

});

function re_load() {

		document.location.href = basePath
				+ "ea/refund/ea_getRefundSheetList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&stype="+stype+"&type=pc";

}
