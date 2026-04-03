$(function() {
	//var len = $("#tbwid").find(".trclass").length;+ len * 27
	window.parent.document.getElementById("mainframe1").style.height = 265  + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '当前部门  --->  '+ogName,
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
					name : '全部保存',
					bclass : 'add',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find('input:gt(0)').each(function() {
					$(this).attr("name",
							"depPostMap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe1").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe1").style.height = heis;
				select++;
				break;
			case '修改' :
				if (depPostID == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + depPostID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find('input:gt(0)').each(function() {
					$(this).attr("name",
							"depPostMap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("td:has(select)").children("div")
						.children("select").attr("disabled", false);
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '删除' :
				if (depPostID == '') {
					alert("请选择！");
					return;
				}
				if (depPostID.substring(0, 2) == "sa") {
					$("#" + depPostID).remove();
					depPostID = "";
					var heis = parent.document.getElementById("mainframe1").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe1").style.height = heis;
					return;
				}
				$f = $('#orgPostForm');
				var i = $f.find("tr#"+depPostID).find("span#adminNum").html();
				var ii = $f.find("tr#"+depPostID).find("input#adminNum").val();
				if(i > 0 || ii > 0){
				alert("职务下已经有编员，不允许删除！");
				return
				}
				if (confirm("是否删除？")) {
					var postName = $("tr#"+depPostID).find("#postName").text();
					$("#orgPostForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/departmentpost/ea_deleteOrgPost.jspa?ogName="+ogName+"&orgName="
								+ogName+"&departmentPost.depPostID="+depPostID+"&departmentPost.postName="+postName);
					document.orgPostForm.submit.click();
					$("tr#" + depPostID).remove();
					depPostID = "";
					token = 11;
				}
				break;
			case '全部保存':
				if (notoken)
					return;
				if (select == 1) {
					return;
				}
				$(".isNaN").trigger("blur");
       			if($(".error",$("#orgPostForm")).length){ 
					alert("岗位人数、定员必须为数字！");
					return 
				}
				notoken = 1;
				$('#orgPostForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/departmentpost/ea_sevaeOrgPostMap.jspa?ogName="+ogName+"&orgName="+ogName+"&departmentPost.leveloneOrgID="+ogID);
				document.orgPostForm.submit.click();
				token = 2;
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/departmentpost/ea_getOrgPostListByOrg.jspa?ogName="+ogName+"&search="+ search+"&departmentPost.organizationID="+ogID;
				numback(url);
				break;
		}
	}
	
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
					.attr("checked", "checked");
		depPostID = this.id;
	});
	
	//当双击时出发 action方法.等价于先选中再点击修改按钮
	$(".JQueryflexme tr[id]").dblclick(function(){
        action('修改');
    });
    
    $("#tosearch").click(function() {
		$f = $('#orgPostSearchForm');
		$f.attr("action", basePath
						+ "ea/departmentpost/ea_getOrgPostListByOrg.jspa?ogName="+ogName+"&pageNumber="
						+ pNumber+"&departmentPost.organizationID="+ogID);
		document.orgPostSearchForm.submit.click();
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/departmentpost/ea_getOrgPostListByOrg.jspa?ogName="+ogName+"&pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&departmentPost.organizationID="+ogID;
}