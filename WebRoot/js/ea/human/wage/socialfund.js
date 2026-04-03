$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
		}).jqmAddClose('.close');
	$('#item').flexigrid({
		title:"社保公积金管理",
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '社保公积金规则',
			bclass : 'add',
			onpress : action
			}, {
			separator : true
		}, {
			name : '调整基数',
			bclass : 'add',
			onpress : action
			}, {
			separator : true
		}, {
			name : '查询',
			bclass : 'mysearch',
			onpress : action
			}, {
			separator : true
		}]
	});
function action(com, grid) {
switch (com) {
	case '社保公积金规则' :
		$("#jqmadd").jqmShow();
		break;
	case '调整基数' :
		if (cid == '') {
			Showbo.Msg.alert("请选择人员！");
			return;
		}
		$("#jqmbase").find("input#showauto").attr("disabled",true);
		var s1=$("tr#tr"+cid).find("span#sb1").text();
		var s2=$("tr#tr"+cid).find("span#sb2").text();
		if(s1!=""){
			$("tr#s0","#jqmbase").find("input#showauto").attr("disabled",false).val(s1);
			$("tr#s0","#jqmbase").find("input#r1").attr("checked","checked");
		}
		if(s2!=""){
			$("tr#s1","#jqmbase").find("input#showauto").attr("disabled",false).val(s2);
			$("tr#s1","#jqmbase").find("input#r1").attr("checked","checked");
		}
		$("#jqmbase").find("span#showstaffname").text(staffname);
		$("form#baseform").find("input#editId").val(cid);
		$("#jqmbase").jqmShow();
		
		
		
		break;
	case '查询' :
		$("#jqmsearch").jqmShow();
		break;
}
}

$("#item tr").click(function() {
	cid=$(this).find("input#cid").val();
	staffname=$(this).find("span#staffname").text();
	$(this).find("input#cid").attr("checked",true);
});

});

function saveM(){
	$("#addform .notnull").trigger("blur");
	if ($("#addform .error").length) {
		return;
	}
	$("#addform").attr("action",basePath+"ea/socialfund/ea_saveItem.jspa");
	$("#addform").submit();
}
function closeM(){
	document.forms["addform"].reset();
	document.forms["searchform"].reset();
	document.forms["baseform"].reset();
	$("#jqmadd").jqmHide();
	$("#jqmsearch").jqmHide();
	$("#jqmbase").jqmHide();
}
function searchM(){
	$("#searchform").attr("action",basePath+"ea/socialfund/ea_findItem.jspa");
	$("#searchform").submit();
	
}
function changeBase(ope){
	if(ope.value==2){
		$(ope).parent().parent().find("input#showauto").attr("disabled",false).val('');
	}else{
		$(ope).parent().parent().find("input#showauto").attr("disabled",true).val('').removeAttr("style");
	}
}
function saveBase(){
	
	var s1 =$("tr#s0","#jqmbase").find("input#showauto").attr("disabled");
	var s2 =$("tr#s1","#jqmbase").find("input#showauto").attr("disabled");
	if(s1&&s2){
		closeM();
		return;
	}
	if(!s1){
		$("tr#s0","#jqmbase").find("input#showauto").trigger("blur");
	}
	if(!s2){
		$("tr#s1","#jqmbase").find("input#showauto").trigger("blur");
	}
	if ($("#baseform .error").length) {
		return;
	}
	$("#baseform").attr("action",basePath+"ea/socialfund/ea_saveFundBase.jspa");
	$("#baseform").submit();
	
}