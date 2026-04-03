$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#item').flexigrid({
		title:"考评分类设置",
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '添加分类',
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
		case '添加分类' :
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
				url:basePath+"ea/assort/sajax_ea_findEditItem.jspa",
				data:"cid="+cid,
				type:"get",
				success:function(data){
					var member = eval("("+data+")");
					var entity = member.eItem;
					$("#jqmadd").find("#cname").val(entity.codeValue);
					$("#jqmadd").find("#cdesc").val(entity.codeDesc);
					$("#jqmadd").find("#cnumber").val(entity.codeNumber);
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
			Showbo.Msg.confirm("删除考评分类，连同考评项会一同删除，确认删除吗？", function(item){
				if(item=="yes"){
					document.location.href=basePath+"ea/assort/ea_delItem.jspa?cid="+cid;
				}
			});
			break;
		case '排序':
			$("#addform").attr("action",basePath + "ea/assort/ea_toReorder.jspa?pageNumber=0");
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
	$("#addform").attr("action",basePath+"ea/assort/ea_saveItem.jspa");
	$("#addform").submit();
}
function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}