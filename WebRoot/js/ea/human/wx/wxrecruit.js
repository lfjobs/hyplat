$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='wxSearchForm' id='wxSearchForm' method='post'>" +
		"<span style=\"margin-left:20px;\">职位名称：</span>" +
		"<input type='text' style=\"width: 90px\" id='recName' name='wxRecruit.recName'\"/>" +
		"<span style=\"margin-left:10px;\">工作地点：</span>" +
		"<input type='text' style=\"width: 130px\" id='codeValue' name='wxRecruit.recAdd' size=\"10\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchwx'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	$('#item').flexigrid({
		title: "微信招聘管理"+query,
		width : 'auto',
		height:'auto',
		minwidth : 30,
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
			name : '停用',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
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
		case '添加' :
			var url = basePath+ "ea/wxrecruit/ea_toAdd.jspa?wxRecruit.wxRecID=";
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			break;
		case '修改' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			} 
			if($("#"+cid).find("input#recState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			var url = basePath+ "ea/wxrecruit/ea_toAdd.jspa?wxRecruit.wxRecID=" + cid;
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			break;
		case '删除' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#recState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定删除吗？", function(item){
				if(item=="yes"){
					$('#recruitForm').attr("action",basePath + "ea/wxrecruit/ea_delItem.jspa?wxRecruit.wxRecID="+ cid);
					$('#recruitForm').submit();
					$("tr#" + cid).remove();
					cid = "";
				}
			});
			break;
		case '停用' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#recState").val() == "1"){
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定停用吗？", function(item){
				if(item=="yes"){
					$('#recruitForm').attr("action",basePath + "ea/wxrecruit/ea_upItem.jspa?wxRecruit.wxRecID="+ cid);
					$('#recruitForm').submit();
				}
			});
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/wxrecruit/ea_findItem.jspa?1=1";
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
	$("#searchwx").click(function(){
		$('#wxSearchForm').attr("action",basePath + "ea/wxrecruit/ea_toSearch.jspa?");
		document.wxSearchForm.submit.click();
	});
});
function re_load() {
	document.location.href = basePath + "ea/wxrecruit/ea_findItem.jspa?";
}
