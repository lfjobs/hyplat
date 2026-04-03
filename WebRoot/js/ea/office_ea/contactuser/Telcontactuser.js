$(document).ready(function() {
	$('.JQueryflexme').flexigrid({
		height: 345,
		width: 'auto',
		minwidth: 30,
		title: '个人往来关系',
		minheight: 80
	});
	//复选框选中物品
	$(".chx").live("click", function(event){
		var b= $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	$(".JQueryflexme tr[id]").click(function(){
		var d=$("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked",!d);
	});
	$("#tosearch").click(function(){  //查询
		parent.$("#searchtype").val("person");
		$("#SearchForm").attr("action", basePath+"ea/contactuser/ea_toSearch.jspa?pageNumber=10&type=message&title=往来个人&typemes=waicha");
		document.SearchForm.submit.click();
	});    
});
