$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : 'PK金汇总管理',
				minheight : 80,
				buttons : [{
					name : 'PK金支出',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : 'PK金详情',
					bclass : 'mysearch',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case 'PK金支出':
				var url = basePath +"ea/pkgoldpool/sajax_ea_toGoldpaypool.jspa?t=" + new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var pool = member.pool;
						
						if(pool == "err"){
							alert("pk金没有可余额");
						}else{
							$("#jqModelSave").jqmShow();
							$t = $("table#pkSaveTable");
							$t.find("input#companyName").val(pool.companyName);
							$t.find("#companyID").val(pool.companyID);
							$t.find("#goldbalpool").val(pool.goldbalpool);
						
						}
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});
				break;
			case 'PK金详情'	:
				if( pkgoldpoolID == ""){
					alert('请选择具体月份！');
					return
				}
				var pkd = $("tr#"+pkgoldpoolID).find("span#pkDate").html();
				document.location.href = basePath + "ea/pkgold/ea_toSearch.jspa?pkgold.pkDate="+pkd
					+"&search=search&t="+ new Date();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/pkgoldpool/ea_getList.jspa?search="
						+ search;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/pkgoldpool/ea_showExcel.jspa?startdate="
						+ pstartdate + "&enddate=" + penddate + "&search="
						+ search;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
		}
	}
	// 点击选中

	$(".JQueryflexme tr[id]").click(function() {
			$("input.JQuerypersonvalue", $(this))
					.attr("checked", "checked");
			pkgoldpoolID = this.id;
		});
	$(".JQueryflexme tr[id]").dblclick(function() {
			$("input.JQuerypersonvalue", $(this))
					.attr("checked", "checked");
			pkgoldpoolID = this.id;
			action('PK金详情');
		});
	// 根据条件查询
	$("#tosearch").click(function() {
		$("#searchForm")
				.attr(
						"action",
						basePath
								+ "ea/pkgoldpool/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.searchForm.submit.click();
	});
	// 根据条件查询
	$("#tosave").click(function() {
		if($("table#pkSaveTable").find("input#pkDate").val() == ""
			|| $("table#pkSaveTable").find("input#gold").val() == ""){
			alert("请填写完整信息！");
			return
		}
		$("#pkSaveForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/pkgoldpool/ea_savePKGold.jspa?pageNumber="
								+ ppageNumber);
		document.pkSaveForm.submit.click();
		token = 2;
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/pkgoldpool/ea_getList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}