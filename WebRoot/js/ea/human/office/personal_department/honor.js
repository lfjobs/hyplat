$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
		$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '荣誉名称管理',
				minheight : 80,
				buttons : [{
					name : '添加',
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
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.AddForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (honorID == "") {
					alert('请选择！');
					return
				}
				$("tr#"+honorID).find("span[id]").each(function(){
					$at = $("#AddForm");
					$at.find("input#"+this.id).attr("value",$(this).text());
					if(this.id == "honorType"){
						$at.find("select#"+this.id).find("option[value='"+$(this).text()+"']").attr("selected","selected");
					}
				});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '删除' :
				if (honorID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#honorForm');
				if (confirm("是否删除？")) {
					$f.attr("target", "hidden")
							.attr("action",
									basePath
											+ "ea/honor/ea_delHonor.jspa?honor.honorID="
											+ honorID );
					document.honorForm.submit.click();
					$("tr#" + honorID).remove();
					honorID = "";
					token = 2;
				}
				break;
			case '设置每页显示条数' :
				var url  = basePath
						+ "ea/honor/ea_getHonorList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
		honorID = this.id;
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
		honorID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
			});
	// 查询相关操作
	$("#search").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/honor/ea_toSearch.jspa?pageNumber="
						+ ppageNumber );
		document.SearchForm.submit.click();
	});
	$("#save").click(function() {
		$("form :input").trigger("blur");
		 if ($("form .error").length) {
             return;
         }
		 
		$('#AddForm').attr("target", "hidden")
				.attr("action",
						basePath
								+ "ea/honor/ea_save.jspa?pageNumber="
								+ ppageNumber　);
		document.AddForm.submit.click();
		if($('#AddForm').find("input#honorID").val == ""){
			token = 1;
		}else{
			token = 2
		}
		document.AddForm.reset();
	});
	$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	}); 
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/honor/ea_getHonorList.jspa?pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
