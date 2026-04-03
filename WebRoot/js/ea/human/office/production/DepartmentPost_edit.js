$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
         
    $("input.JQueryNoSubmit").click(function(){  //返回
    	document.location.href=pbasePath
    				+"ea/departmentpost/ea_getDepartmentPostList.jspa?pageNumber="
    				+ppageNumber+"&search="+psearch+"&pageForm.pageNumber="+pnums;
    });
});