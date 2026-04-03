$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#item').flexigrid({
		title:"月考评管理",
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '月综合考评',
			bclass : 'add',
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
		}]
	});
function action(com, grid) {
switch (com) {
	case '月综合考评' :
		if (cid == '') {
			Showbo.Msg.alert("请选择人员再添加考评！");
			return;
		}
		document.forms["addform"].reset();
		$("#addform").find("span#parmName").text($("tr#"+cid).find("#cname").text());
		$("#addform").find("input#pdate").val($("tr#"+cid).find("#date").text());
		$("#addform").find("span#showDate").text($("tr#"+cid).find("#date").text());
		$("#addform").find("input#searchdate").val($("tr#"+cid).find("#date").text());
		$("#addform").find("#editId").val(cid);
		$.ajax({
			url:basePath+"ea/score/sajax_ea_findEditItem.jspa",
			type:"get",
			success:function(data){
				var member = eval("("+data+")");
				var wageEvalList = member.wageEvalList;
				var codeList=member.codeList;
				var htr=new Array();
				var result=0;
				var idx=0;
				for(var i=0;i<codeList.length;i++){
					var code=codeList[i];
					var tempstr=new Array();
					var r1=0;
					tempstr.push("<div class=\"fl\">");
					for ( var j = 0; j < wageEvalList.length; j++) {
						var wageEval=wageEvalList[j];
						if(code.codeID==wageEval.codeId){
							tempstr.push("<div class=\"option fl\">");
							tempstr.push("<label>"+wageEval.wageEvalName+":</label>");
							tempstr.push("<div class=\"nav fl\"><input type='text' name='items["+idx+"].wageScore' onkeyup=\"jsmoney(this,'evalcontent')\" onchange=\"jsmoney(this,'evalcontent')\" maxlength='3' size='3' ");//实际得分
							tempstr.push(" class='jsm ckSize' min='0' max='"+wageEval.wageEvalScore+"'/>");//实际得分
							tempstr.push("总分("+wageEval.wageEvalScore+")");
							tempstr.push("<input type='hidden' name='items["+idx+"].totalScore' value='"+wageEval.wageEvalScore+"'/>");//满分
							tempstr.push("<input type='hidden' name='items["+idx+"].wageEvalId' value='"+wageEval.wageEvalId+"'/>");//考评项id
							tempstr.push("</div></div><div class='clear'></div>");
							r1+=parseFloat(wageEval.wageEvalScore);
							idx++;
						}
					}
					tempstr.push("</div>");
					htr.push("<div class=\"tab1 fl\">");
					htr.push("<div class=\"jiben fl\">"+code.codeValue+"<br/>总分("+r1+")</div>");
					htr.push(tempstr.join(""));
					htr.push("</div>");
					result+=r1;
				}
				htr.push("<br/>考评总分："+result+"&nbsp;&nbsp;&nbsp;&nbsp;实际得分:<span id='retScore'></span>&nbsp;&nbsp;&nbsp;&nbsp;考核人:"+staffname);
				$("div#evalcontent").html(htr.join(""));
			}
		});
		$("#jqmadd").jqmShow();
		break;
	case '查询' :
		$("#jqmsearch").jqmShow();
		break;
}
}

$("#item tr").click(function() {
	cid=$(this).find("input#cid").val();
	$(this).find("input#cid").attr("checked",true);
});
$("#item tr").dblclick(function() {
	cid=$(this).find("input#cid").val();
	action('月综合考评');
});
});

function saveM(){
	$("#addform .ckSize").trigger("blur");
	if ($("#addform .error").length) {
		return;
	}
	var lt =$("#evalcontent").find("input[type='text']").size();
	if(lt>0){
		$("#addform").attr("action",basePath+"ea/score/ea_saveItem.jspa");
		$("#addform").submit();
	}else{
		Showbo.Msg.alert("请先设置考评项.");
	}
}
function closeM(){
	document.forms["addform"].reset();
	document.forms["searchform"].reset();
	$("#jqmadd").jqmHide();
	$("#jqmsearch").jqmHide();
}
function jsmoney(ope,tableid){
	if (isNaN(ope.value)) {
		ope.value=ope.value.replace(/[^\-\d.]/g,'');
		return;
	} else {
		var tm =ope.value;
		if(ope.value==""){
			tm=0;
		}
		var score=0;
		$("#"+tableid).find(".jsm").each(function(){
			if($(this).val()!=""){
				score+=parseFloat($(this).val());
			}
		});
		$("#"+tableid).find("span#retScore").text(score);
	}
	
}
function searchM(){
	if ($("#jqmsearch").find("input[name='searchdate']").val().trim() == '') {
		Showbo.Msg.alert("请填写日期.");
		return;
	}
	$("#searchform").attr("action",basePath+"ea/score/ea_findItem.jspa");
	$("#searchform").submit();
	
}
function viewMonthTask(){
	
	var staffname=$("#addform").find("input#parmName").val();
	var now = new Date();
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
	var dt=year+"-"+month;
	if(searchdate!=''){
		dt=searchdate;
	}
	var url=basePath+"ea/jobtask/s_ea_getTaskTrack.jspa?monthK=monthK&jobTask.staffName="+staffname+"&start="+dt+"&end="+dt+"&summarytype=com&search=search";
	window.open(url,"about:blank");
}