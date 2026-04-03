$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.resume').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '网站人才' ,
				minwidth : 30,
				minheight : 80,
				buttons : [ {
					name : '查看',
					bclass : 'mysearch',
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
					name : '转社会人力',
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
			case '查询' :
				$("#jqModelSerch").jqmShow();
				break;
			case '查看' :
				if(resumekey == ""){
					alert("请选择人员");
					return;
				}

				$t = $("table#resTable");
				$("tr#"+resumekey).find("span[id]").each(function() {
					$t.find("input#" + this.id).val($(this).text().trim()).attr("readonly", "readonly");
				});
				$("#jqModelSee").jqmShow();
				break;
			case '转社会人力' :
				if(resumekey == ""){
					alert("请选择人员");
					return;
				}
				var url = basePath+"ea/resume/sajax_ea_toResume.jspa?resume.resumekey="+resumekey+"&d="+Math.ceil(Math.random()*1000);
                $.ajax({
                    url : encodeURI(url),
                    type : "get",
                    async : false,
                    dataType : "json",
                    success : function cbf(data) { 
                        var member = eval("(" + data + ")");
                         if(member.suc == "suc"){
                        	 alert("操作成功！");
                        	 token = 0;
                        	 re_load();
                         }else{
                        	 alert("本人已转移到社会人力！");
                         }
                         
                       },
                    error : function cbf(data) {
                        alert("获取数据失败!");
                    }
                });
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/resume/ea_getList.jspa?search=search";
				numback(url);
				break;
		}
	}

	$(".resume tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		resumekey = this.id;
	});
	// 查询相关操作
	$("#searchAda").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/resume/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
		$("#jqModelSerchForm").find("input[name]").val("");
	});
	// 取消
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	// 预览返回
	$("a#DaoRuFan").click(function() {
				$("#jqmWindow2").jqmHide();
				re_load();
			});	
	
});
function re_load() {
		document.location.href = basePath
				+ "ea/resume/ea_getList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
