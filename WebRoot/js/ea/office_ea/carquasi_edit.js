$(document).ready(function() {
	$('.carquasi').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '车辆准载座位管理----当前车牌号：' + carInformation,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '全部保存',
					bclass : 'add',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"carquasimap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;

			case '修改' :
				if (quasiID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + quasiID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"carquasimap[" + select + "]." + this.name);
				});

				select++;
				$p.children("td:has(:input)").children("div").find("span")
						.addClass("model1");
				$p.children("td:has(:input)").children("div")
						.find("input").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				var returnStr = "";
				var kg =0;
				var fk = 0;
				$(".val3", $(".check")).each(function(i, tmp) {
					
							if (this.value == "") {
								$(this).css("background-color", "red");
								fk++;
							}else if ($.trim(this.value) == "") {
								$(this).css("background-color", "red");
								kg++;
							}else {
								$(this).css("background-color", "white");
							}
						});
					
				if(kg >0){
					returnStr+= " 不可以为空格";
					re=1;
				} 
				if(fk>0){
					returnStr+= " 不可以为空";
					re=1;
				}
				if(returnStr != ""){
					alert(returnStr);
				}
				if (re) {
					notoken = 0;
					return;
				}
				var carhao=$("#carNum").html();
				var carlei=$("#carType").html();
				var carjia=$("#carFrameNum").html();
				$('#carquasiForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/carquasi/ea_saveCarquasiList.jspa?carquasi.carID="
										+ carID +"&carquasi.carNum=" +carhao+"&carquasi.carType="+carlei+"&carquasi.carFrameNum="+carjia+"&pageNumber=" + pNumber);
				document.carquasiForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (quasiID == '') {
					alert("请选择！");
					return;
				}
				if (quasiID.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + quasiID).remove();
						quasiID = "";
					}

					return;
				}
				$f = $('#carquasiForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/carquasi/ea_deleteCarquasi.jspa?pageNumber="
											+ pNumber + "&carquasi.carID="
											+ carID + "&carquasi.quasiID="
											+ quasiID);
					document.carquasiForm.submit.click();
					$("tr#" + quasiID).remove();
					quasiID = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/carquasi/ea_getCarQuasiList.jspa?carquasi.carID="
						+ carID;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/carquasi/ea_showCarquasiExcel.jspa?carquasi.carID="
						+ carID;
				open(url);
				break;

		}
	}

	$(".carquasi tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				quasiID = this.id;
			});
	$(".carquasi tr[id]").dblclick(function() {

				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				quasiID = this.id;
				action("修改");
			});

		// $(".address").find("select[id!=xxx]").each(function() {
		// $s = $(this).hide()
		// })
	$(".carquasi").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carquasi/ea_getCarQuasiList.jspa?carquasi.carID="
				+ carID + "&pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}