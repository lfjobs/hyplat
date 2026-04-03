$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='threepaySearchForm' id='threepaySearchForm' method='post'>" +
		"<span style=\"margin-left:20px;\">开始时间：</span>" +
		"<input type='text' style=\"width: 100px\" id='startdate' name='startdate' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"/>" +
		"<span style=\"margin-left:10px;\">结束时间：</span>" +
		"<input type='text' style=\"width: 100px\" id='enddate' name='enddate' size=\"10\" onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchthree'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	var tit = "";
	$('#item').flexigrid({
		title: "工资管理 ("+startdate+"---"+enddate+")"+query,
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [
//		           ({
//			name : '生成工资',
//			bclass : 'edit',
//			onpress : action
//		// 当点击调用方法
//		}), {
//			separator : true
//		},
		{
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
		case '生成工资' :
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/payroll/ea_getThreePay.jspa?1=1";
			numback(url);
			break;
	}
}

	$("#item tr").click(function() {
		cid=$(this).find("input.cid").val();
		$(this).find("input.cid").attr("checked",true);
	});
	$("#item tr").dblclick(function() {
		cid=$(this).find("input.cid").val();
//		action('修改');
	});
	
	$("#searchthree").click(function(){
		$('#threepaySearchForm').attr("action",basePath + "ea/payroll/ea_getThreePay.jspa?");
		document.threepaySearchForm.submit.click();
	});
	
});

function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "cofi/ea_findItem.jspa?wstate=" + wstate;
}