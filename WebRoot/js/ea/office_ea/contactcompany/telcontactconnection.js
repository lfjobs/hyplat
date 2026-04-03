$(document).ready(function() {
	$('.JQueryflexme').flexigrid({
        height: 360,
        width: 'auto',
        minwidth: 30,
        title: '单位往来关系',
        minheight: 80
    });
	$(".chx").live("click", function(event){
		var b= $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	$(".JQueryflexme tr[id]").click(function(){
		var d=$("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked",!d);
	});
	$("#tosearch").click(function(){  //查询
		parent.$("#searchtype").val("company");
		$("#SearchForm").attr("action", basePath+"ea/contactConnection/ea_toSearch.jspa?pageNumber=10&type=message&title=往来公司&typemes=waicha");
		document.SearchForm.submit.click();
	});      
});
