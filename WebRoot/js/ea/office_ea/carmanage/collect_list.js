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
				buttons : [{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印',
					bclass : 'printer',
					onpress : action

				},  {
					separator : true
				},{
                    name : 'EXCEL导出',
                    bclass : 'excel',
                    onpress : action

                },  {
                    separator : true
                },{
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
			case '删除' :
				if (carmID == "") {
					alert('请选择');
					return
				}
				if(model=="1"){
					if (confirm("确定删除？")) {
						delVehicleInformation(carmID);
					}
				}else if(model=="0"){
					alert("该数据无法删除");
				}
				break;
			case '打印' :
				if (carmID == "") {
					alert('请选择');
					return
				}
				window.open(basePath+ "ea/carmanage/ea_queryMessage.jspa?cm.carmID="+carmID);
				break;
			case 'EXCEL导出':
                window.open(basePath+"ea/carmanage/ea_showExcel.jspa?");
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carmanage/ea_collectList.jspa?cm.carNumber="+$("#carNumber").val();
				numback(url);
				break;
		}
	}
	//like查询车牌号
	$(".input-button").click(function(){
		ppageNumber = 0;
        $("#SearchForm").attr("action",basePath+ "ea/carmanage/ea_collectList.jspa?");
        document.SearchForm.submit.click();
	})
	
	

	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				carmID = this.id;
				model = $(this).find(".model").val();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	//删除车辆停车信息
	function delVehicleInformation(obj){
		var url = basePath + "ea/carmanage/sajax_ea_delVehicleInformation.jspa";
		$.ajax({
			url : url,
			type : "post",
			async : false,
			dataType : "json",
			data :{
				"cm.carmID":obj
			},
			success : function(data) {
				var standard = eval("(" + data + ")");
				if(standard.boolean){
					window.location.reload();
				}else{
					alert("删除失败");
				}
			},
			error : function(data) {
				alert("删除失败");
			}
		});
	}
	
	function upload(obj){
		$(".carNumber").val($(obj).text());
	}
});
