$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '项目管理',
				minheight : 80,
				buttons : [{
					name : '新建',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}/*, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '项目完成',
					bclass : 'examine mark',
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
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
				, {
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
			case '新建' :
			    var url = basePath
						+ "ea/projectmanager/ea_toAddProject.jspa?type=add&pageNumber="
						+ ppageNumber;
				document.location.href = encodeURI(url);
				proid = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				break;
			case '修改' :
				if (proid == "") {
					alert('请选择!');
					return;
				}
				var finishdate = $("tr#"+proid).find("span#factfinishdate").text();
				if(finishdate!=""){
				    alert('项目已经完成,不能修改!');
				    return;
				}
				 var id = $("tr#"+proid).find("span#proid").text();
				 var url = basePath
						+ "ea/projectmanager/ea_toAddProject.jspa?type=edit&project.proid="+id+"&pageNumber="
						+ ppageNumber;
				 document.location.href = encodeURI(url);
				break;
			case '查看' :
			    if (proid == "") {
					alert('请选择!');
					return;
				}
				var proname = $("tr#"+proid).find("span#proname").text();
			     document.location.href=basePath+ "page/mysl/ProjectTree.jsp?proid="+proid+"&proname="+proname;
			    /*window.parent.document.getElementById('rightFrame').location= basePath
						+ "page/mysl/ProjectTree.jsp";*/
				break;
			case '项目完成' :
			    if (proid == "") {
					alert('请选择!');
					return;
				}
				var finishdate = $("tr#"+proid).find("span#factfinishdate").text();
				if(finishdate!=""){
				    alert('项目已经完成!');
				    return;
				}
				var url = basePath
						+ "ea/projectmanager/ea_finishedDtMyproject.jspa?project.proid="+proid+"&pageNumber="
						+ ppageNumber;
				$("#projectForm").attr("target","hidden").attr(
						"action",url);
			    if(confirm("是否确定完成？"))
			    {
			     document.projectForm.submit.click();
		         token = 2;
			    }
				$("input.JQuerypersonvalue").attr("checked", false);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#postSearchForm").find("input#procode").focus();
				break;
			case '导出' :
			    var url = basePath+ "ea/projectmanager/ea_showExcel.jspa?search="+search;
			    open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/projectmanager/ea_getDtMyprojectList.jspa?1=1";
				numback(url);
				break;
		}
	}
	$(".close").click(function() {// 取消
				$("#jqModelSearch").jqmHide();
			});
	//条件查询确定
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/projectmanager/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				proid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/projectmanager/ea_getDtMyprojectList.jspa?pageNumber="
				+ ppageNumber;
}