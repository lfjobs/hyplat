$(function(){
	if(type!="examine"){
		$("#examine").attr("style","position:relative;top: 75px; display: none;");
	}
	
	$(".verify").click(function(){
		str=$(this).attr("id");
		$("#single").jqmShow();
	});
	$(".but").click(function(){
		if($(this).val()=="提交"){
			if(!confirm("确定提交"))
				return;
		
			$.ajax({
				url:basePath+"ea/production/sajax_ea_StorageSingleAudit.jspa?deptpost="+str+"&utboundOrderVo.cashierbillsid="+$("#cashiId").val()+"&audit="+$("textarea").val()+"&status="+$('input[name="radio"]:checked').val(),
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					if($('input[name="radio"]:checked').val()=="yes"){
						 window.location.reload();
					}else{
						window.opener.location.reload();
						window.close();
					};
				},
				error:function(data){
					alert("数据获取失败");
				}
			});
		}else{
			$("textarea").val("");
			$("#single").jqmHide();
		}
	});
	var str=	$("#date").val().split(" ");
	$("#date").val(str[0]);
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20// 
	}).jqmAddClose('.close');
});