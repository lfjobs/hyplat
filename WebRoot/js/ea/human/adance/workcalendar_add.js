$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("div.dbcil").dblclick(function(){
		if($(this).find("input.week").val() == ""){
			return
		} 
		$("#jqModelDeit").jqmShow();
		$d = $("#DeitTable");
		$d.find("input#days").attr("value",$(this).find("input.days").val());
		$d.find("input#week").attr("value",$(this).find("input.week").val());
		var s = $(this).find("input.status").val()
		$d.find("select#status").find("option[value="+s+"]").attr("selected","selected");
	});
	//编辑
	$("input.edit").click(function(){
		var days = $("#DeitTable").find("input#days").val(); //2014-08-26
		$v = $(".workcalendar").find("input[value='"+days+"']").parent().parent();
		$v.find("input.status").attr("value",$("#DeitTable").find("select#status").val());
		var op = $("#DeitTable").find("select#status").find("option:selected").text();
		$v.find("span.status").text( op);
		$("#jqModelDeit").jqmHide();
	});
	//保存
	$("input.save").click(function(){
		if(stus == "00"){
			return
		}
		$('#workcalendarForm')
			.attr("target", "hidden")
			.attr(
				"action",
				basePath
						+ "ea/workcalendar/ea_save.jspa?days="+days);
		document.workcalendarForm.submit.click();
		token = 2;
	});
	$("input.toNewDB").click(function(){
		var url = basePath+"ea/workcalendar/sajax_ea_toNewDB.jspa?d="+Math.ceil(Math.random()*1000);
        $.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            success : function cbf(data) { 
                var member = eval("(" + data + ")");
                 if(member.suc == "suc"){
                	 alert("操作成功！");
                	 document.location.href = basePath
     					+ "/ea/workcalendar/ea_toAdd.jspa?seaDate="+member.seaDate;
                 }else{
                	 alert("初始化数据已有！");
                 }
                 
               },
            error : function cbf(data) {
                alert("获取数据失败!");
            }
        });
	});
});
function foc(e){
	$(".save").attr('disabled',"true");
	document.location.href = basePath
		+ "/ea/workcalendar/ea_toAdd.jspa?seaDate="+$(e).val();
}
function mouo(e){
	$(e).css("border-color","#eead00");
}
function moum(e){
	$(e).css("border-color","#e6e7e8");
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/workcalendar/ea_toAdd.jspa?seaDate="+seaDate;
}