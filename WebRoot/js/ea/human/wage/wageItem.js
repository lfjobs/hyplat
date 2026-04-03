$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	$('#wageItem').flexigrid({
				title:"工资构成管理",
				width : 'auto',
				height:'auto',
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '添加工资构成',
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
			case '添加工资构成' :
				document.forms["addform"].reset();
				defaultset();
				$("#addform").find("#opeType").val("add");
				$("#jqmadd").jqmShow();
				break;
			case '修改' :
				if (cid == '') {
					Showbo.Msg.alert("请选择！");
					return;
				}
				defaultset();
				document.forms["addform"].reset();
				$("#addform").find("#opeType").val("edit");
				$.ajax({
					url:basePath+"ea/witem/sajax_ea_findEditItem.jspa",
					data:"cid="+cid,
					type:"get",
					success:function(data){
						var member = eval("("+data+")");
						var entity = member.eItem;
						$("#jqmadd").find("#wageItemSn").val(entity.wageItemSn);
						$("#jqmadd").find("#wageItemName").val(entity.wageItemName);
						$("#jqmadd").find("#wiRemark").val(entity.wiRemark);
						if(entity.wageEvalState.trim()=="00"){
							$("#jqmadd").find("select#wageEvalState option").each(function(){
								if("00"==$(this).val()){
									$(this).attr("selected","selected");
									return;
								}
								
							});
							$("#jqmadd").find("select#evalItem option").each(function(){
								if(entity.wageFkEval==$(this).val()){
									$(this).attr("selected","selected");
									return;
								}
								
							});
							$("#jqmadd").find("select#evalItem").show();
							$("#jqmadd").find("select#evalItem").removeAttr("disabled");
						}else if(entity.wageEvalState.trim()=="01"){
							$("#jqmadd").find("select#wageEvalState option").each(function(){
								if("01"==$(this).val()){
									$(this).attr("selected","selected");
									return;
								}
								
							});
							$("#jqmadd").find("select#evalType option").each(function(){
								if(entity.wageFkEval==$(this).val()){
									$(this).attr("selected","selected");
									return;
								}
								
							});
							$("#jqmadd").find("select#evalType").show();
							$("#jqmadd").find("select#evalType").removeAttr("disabled");
						}
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
						document.location.href=basePath+"ea/witem/ea_delItem.jspa?cid="+cid;
					}
				});
				break;
			case '排序':
				$("#addform").attr("action",basePath + "ea/witem/ea_toReorder.jspa?pageNumber=0");
				document.addform.submit();
				break;
		}
	}
	$("#wageItem tr").click(function() {
		cid=$(this).find("input#cid").val();
		$(this).find("input#cid").attr("checked",true);
	});
	$("#wageItem tr").dblclick(function() {
		cid=$(this).find("input#cid").val();
		action('修改');
	});
});
function saveM(){
	$("#addform .put3").trigger("blur");
	if ($("#addform .error").length) {
		return;
	}
	if($("#addform").find("select#wageEvalState").val()=="00"){
		var item=$("#addform").find("select#evalItem").val();
		if(item==null||item==""){
			Showbo.Msg.alert("请先完善基础数据维护中的考评项！");
			return;
		}
	}else if($("#addform").find("select#wageEvalState").val()=="01"){
		var item=$("#addform").find("select#evalType").val();
		if(item==null||item==""){
			Showbo.Msg.alert("请先完善基础数据维护中的考评分类！");
			return;
		}
	}
	$("#addform").attr("action",basePath+"ea/witem/ea_saveItem.jspa");
	$("#addform").submit();
}
function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	defaultset();
}
function defaultset(){
	$("#jqmadd").find("select#evalItem").hide();
	$("#jqmadd").find("select#evalItem").attr("disabled","disabled");
	$("#jqmadd").find("select#evalItem option").removeAttr("selected");
	$("#jqmadd").find("select#evalType").hide();
	$("#jqmadd").find("select#evalType").attr("disabled","disabled");
	$("#jqmadd").find("select#evalType option").removeAttr("selected");
}
function changeEval(val){
	if(val=="00"){
		$("#jqmadd").find("select#evalItem").removeAttr("disabled");
		$("#jqmadd").find("select#evalItem").show();
		$("#jqmadd").find("select#evalType").attr("disabled","disabled");
		$("#jqmadd").find("select#evalType").hide();
	}else if(val=="01"){
		$("#jqmadd").find("select#evalType").removeAttr("disabled");
		$("#jqmadd").find("select#evalType").show();
		$("#jqmadd").find("select#evalItem").attr("disabled","disabled");
		$("#jqmadd").find("select#evalItem").hide();
	}else{
		$("#jqmadd").find("select#evalType").attr("disabled","disabled");
		$("#jqmadd").find("select#evalType").hide();
		$("#jqmadd").find("select#evalItem").attr("disabled","disabled");
		$("#jqmadd").find("select#evalItem").hide();
	}
}