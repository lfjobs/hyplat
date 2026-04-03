$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : "序时账报表",
				minheight : 80,
				buttons : [ {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
					}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'mysearch',
					onpress : action
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '打印预览':
				window.open(basePath+"/ea/vsequence/ea_toPrint.jspa?pageNumber="+pNumber+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&kemu="+kemu+"&keid="+keid+"&vnum="+vnum);
				break;
			case '导出':
				var url = basePath+ "ea/vsequence/ea_showExcel.jspa?&sdate="+sdate+"&edate="+edate+"&kemu="+kemu+"&keid="+keid+"&vnum="+vnum;
				open(url);
				break;
			case '设置每页显示条数':
				var url = basePath+ "ea/vsequence/ea_getVsequenceList.jspa?search="+ search+"&sdate="+sdate+"&edate="+edate+"&kemu="+kemu+"&keid="+keid+"&vnum="+vnum;
				numback(url);
				break;
		}
	}
	//查询按钮
	$("#search").click(function(){
		if($("#ming option:selected").text()=="请选择"){
		$("#ming option:selected").attr("text","");
		}
		$("#SearchForm").attr("action",basePath + "/ea/vsequence/ea_toSearch.jspa?pageNumber="+ pNumber+"&sdate="+$("#sdate").val()+"&edate="+$("#edate").val()+"&vouchers.subjectsName="+$("#ming option:selected").text()+"&vnum="+$("#nums").val());
		document.getElementById("SearchForm").submit();
		$("#SearchTable").find(":input[name]").val("");
	});
});
function re_load() {
	if (token)
		document.location.href = basePath+ "ea/vsequence/ea_getVsequenceList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value")+"&sdate="+sdate+"&edate="+edate+"&kemu="+kemu+"&keid="+keid+"&vnum="+$("#nums").val();
}