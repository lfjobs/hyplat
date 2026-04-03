$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var query="<form method='post' name='searchf' id='searchf'><input type='hidden' name='type' value='"+type+"'/><input type='submit' style='display:none;' name='submit' />" +
	"<table  border='0' cellspacing='0' cellpadding='0'>" +

	"<td><input type='hidden' name='search' value='search'/>" +
	"<input type='hidden' name='csbSearch.zprojectname' class='xmtype'/></td>" +
	"<td align='right'>项目名称：</td>" +
	"<td><input type='text' name='csbSearch.sprojectname' size='6'/></td>" +
	"<td align='right'>物品编号：</td>" +
	"<td><input type='text' name='csbSearch.goodsnum' size='6'/></td>" +
	"<td align='right'>责任人：</td>" +
	"<td><input type='text' name='csbSearch.staffname' size='6'/></td>" +
	"<td align='right'>制单日期：</td>" +
	"<td colspan='3'><input type='text' name='csbSearch.start' onfocus='date(this)' size='6'/>-<input  type='text' name='csbSearch.end' onfocus='date(this)' size='6'/></td>" +
	"<td colspan='4' align='left'><input type='button' class='input-button' value=' 查询 ' id='tosearch'/></td></tr>" +
	"</table>";
	$('.fund').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				//title : (type=="01"?"项目物品费用未审批":"项目物品费用已审批")+"列表",
				title : "已招标物品列表"+query,
				minheight : 80,
				buttons : [{
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
			
			case '导出':
				var url = basePath+ "ea/product/ea_showproductExcel.jspa?sdate=" + sdate + "&edate=" + edate
						+ "&type=" + type+"&treeid="+treenums+"&costSheetBill.journalNum="+xiang+"&costSheetBill.staffID="+people;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/product/ea_getZbPriceedList.jspa?search="
						+ search;
				numback(url);
				break;
		
		}
	}
	 //查询按钮单击事件
     $("#tosearch").click(function(){
        $("#searchf").attr("action", basePath+"/ea/product/ea_toSearch.jspa?pageNumber="+pNumber);
        document.searchf.submit.click();
    });
});
function re_load() {
	if (token)
		document.location.href = basePath+ "ea/product/ea_getBudgetList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value")+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeid="+treenums;
}