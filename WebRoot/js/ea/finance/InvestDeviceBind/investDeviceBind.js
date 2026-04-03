$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [
					{name : '添加(获取数据)',bclass : 'add',onpress : action}, {separator : true},
					{name : '添加业务员',bclass : 'add',onpress : action}, {separator : true}, 
					{name : '删除',bclass : 'delete',onpress : action}, {separator : true},
					{name : 'EXCEL导出',bclass : 'edit',onpress : action},{separator : true}]
			});
	
	//获取deviceBind表的id
	var dbId = "";
	$("table#deviceBind tr[id]").live("click", function(event) {
		dbId = this.id;
	});
	
	function action(com, grid) {
		switch (com) {
			case '添加(获取数据)' :
				window.open(basePath
						+ "/page/ea/main/finance/BenDis/InvestDeviceBind/device.jsp");
						
				break;
			case '添加业务员' :
				if(dbId == ""){
					alert('请选择!');
					return;
				}else{
					window.open(basePath
							+ "ea/devicebind/ea_selGlStaff.jspa?dbId="+dbId);
				}
				break;
				/*case '查询' :
				
				window.open(basePath
						+ "ea/bidrecruit/ea_getAddPage.jspa");
						
				break;
				/*case '修改' :
				if (riId == "") {
					alert('请选择!');
					return;
				}
			  window.open(basePath
						+ "ea/bidrecruit/ea_getAddPage.jspa?recruitInfo.riId="+riId);
				break;*/
			case '删除' :
				if (dbId == "") {
					alert('请选择');
					return
				}
				//$("#riId").val(riId);
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/devicebind/ea_delDeviceBind.jspa?dbId="+dbId);
					document.SearchForm.submit.click();
					$("tr#" + dbId).remove();
					dbId = "";
					token = 11;
				}
				break;
			case 'EXCEL导出':
				window.location.href=basePath+"ea/devicebind/ea_exportExcelDevice.jspa?carNum="+carNum+
				"&deviceStatu="+deviceStatu+"&tzName="+tzName+"&tzAccount="+tzAccount;
				break;
		}
	}
	
	//发布\取消发布
	$(".confirm").click(function(){
      $("#SearchForm")
		 .attr("target", "hidden")
		 .attr(
				"action",
				basePath
						+ "ea/bidrecruit/ea_publishRecruitInfo.jspa");
          document.SearchForm.submit.click();
          token = 2;
		
	});

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				riId = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/devicebind/ea_selDeviceBind.jspa?&pageForm.pageNumber="+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
	});

});

function re_load() {

if (token)
	document.location.href = basePath
	+ "ea/devicebind/ea_selDeviceBind.jspa?&pageForm.pageNumber="+ ppageNumber;
}
