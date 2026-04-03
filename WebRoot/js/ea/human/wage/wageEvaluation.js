$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#item').flexigrid({
		title:"考评项管理",
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '添加打分项',
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
			name : '排序',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}]
	});
function action(com, grid) {
switch (com) {
	case '添加打分项' :
		document.forms["addform"].reset();
		$("#addform").find("#opeType").val("add");
		$("#jqmadd").jqmShow();
		break;
	case '修改' :
		if (cid == '') {
			Showbo.Msg.alert("请选择！");
			return;
		}
		$("#addform").find("#opeType").val("edit");
		$.ajax({
			url:basePath+"ea/evaluation/sajax_ea_findEditItem.jspa",
			data:"cid="+cid,
			type:"get",
			success:function(data){
				var member = eval("("+data+")");
				var entity = member.eItem;
				$("#jqmadd").find("#wageEvalSn").val(entity.wageEvalSn);
				$("#jqmadd").find("#wageEvalName").val(entity.wageEvalName);
				$("#jqmadd").find("#wageEvalScore").val(entity.wageEvalScore);
				$("#jqmadd").find("#wageEvalRemark").val(entity.wageEvalRemark);
				$("#jqmadd").find("select#codeId option").each(function(){
					if($(this).val()==entity.codeId){
						$(this).attr("selected","selected");
					}
					
				});
				$("#addform").find("#editId").val(cid);
			}
		});
		$("#jqmadd").jqmShow();
		break;
	case '删除' :
		if (cid == '') {
			Showbo.Msg.alert("请选择！");
			return;
		}
		Showbo.Msg.confirm("确定删除吗？", function(item){
			if(item=="yes"){
				document.location.href=basePath+"ea/evaluation/ea_delItem.jspa?cid="+cid;
			}
		});
		break;
	case '排序':
		$("#addform").attr("action",basePath + "ea/evaluation/ea_toReorder.jspa?pageNumber=0");
		document.addform.submit();
		break;
}
}

$("#item tr").click(function() {
	cid=$(this).find("input#cid").val();
	$(this).find("input#cid").attr("checked",true);
});
$("#item tr").dblclick(function() {
	cid=$(this).find("input#cid").val();
	action('修改');
});
});

function saveM(){
	$("#addform .put3").trigger("blur");
	if ($("#addform .error").length) {
		return;
	}
	if($("#addform").find("select#codeId").val()=="-1"){
		Showbo.Msg.alert("请选择分类!");
		return;
	}
	$("#addform").attr("action",basePath+"ea/evaluation/ea_saveItem.jspa");
	$("#addform").submit();
}
function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}
