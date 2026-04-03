$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.fund').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '产品清单',
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
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询':
				$("#jqModelSearch").jqmShow();
                $("input#journalNum").focus();
				break;
			case '导出':
				var url = basePath+ "ea/product/ea_showallExcel.jspa?sdate=" + sdate + "&edate=" + edate
						+"&treeid="+treenums+"&costSheetBill.journalNum="+xiang+"&costSheetBill.staffID="+people
						+"&type="+type;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/product/ea_getAllList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate+"&treeid="+treenums;
				numback(url);
				break;
		
		}
	}
	//单击选中
	$(".fund tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		csdID = this.id;
	});
	 //查询按钮单击事件
     $("#tosearch").click(function(){
        $("#SearchForm").attr("action", basePath+"/ea/product/ea_toSearchall.jspa?pageNumber="+pNumber);
        document.SearchForm.submit.click();
    });
});

function re_load() {
	if (token)
		document.location.href = basePath+ "ea/product/ea_getAllList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeid="+treenums;
}