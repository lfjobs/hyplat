$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='cofiPunishForm' id='cofiPunishForm' method='post'>" +
		"<span style=\"margin-left:20px;\">提成人：</span>" +
		"<input type='text' style=\"width: 90px\" id='staffName' name='gbs.targetSalerName'/>" +
		"<span style=\"margin-left:10px;\">奖惩名称：</span>" +
		"<input type='text' style=\"width: 130px\" id='goodsName' name='gbs.goodsName' size=\"10\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchCofiPunish'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	var flexname = "";

		flexname = "考评工资管理";


	$('#item').flexigrid({
		title: flexname+query,
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [
//		, {
//			name : '导出',
//			bclass : 'mysearch',
//			onpress : action
//				// 当点击调用方法
//			}, {
//			separator : true
//		}
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
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/cofipunish/ea_findItem.jspa?contype="+contype;
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
		action('修改');
	});
	
	$("#searchCofiPunish").click(function(){
		$('#cofiPunishForm').attr("action",basePath + "ea/cofipunish/ea_toSearch.jspa?contype="+contype);
		document.cofiPunishForm.submit.click();
	});
});

function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}

