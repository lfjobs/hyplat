$(document).ready(function() {
	/*var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe13").style.height = 250 + len * 27 + "px";*/
	parent.document.getElementById("mainframe13").style.height = 225 + "px";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.credentials').flexigrid({
				height : 200,
				width : 'auto',
				title : '证件管理',
				minwidth : 30,
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"credentialsmap[" + select + "]." + this.name);
				});
				
				$("input#start").val('');   
                $("input#end").val('');
				$("#sa" + select).show();
				/*var heis = parent.document.getElementById("mainframe13").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe13").style.height = heis;*/
				select++;
				break;
			case '修改' :
				if (credentialsID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + credentialsID + " #status").attr("value") == '01') {
					alert("不可修改");
					break;
				}
				$p = $("tr#" + credentialsID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"credentialsmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("select").attr("disabled", false);
				$p.find("select").show();
				
				$("input#start").val('');   
                $("input#end").val('');
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken)
					return;
				if (select == 1) {
					return;
				}
				
				var err = 0;
				$("input[id][name][id!='staffID']", $(".check")).each(function(i, tmp) {
					if (this.value!=null&&this.value.length>30) {
						alert("最多可输入30个字");
						$(this).css("background-color", "red");
						err = 1;
					}
				});
				if(err){
					return;
				}
				notoken = 1;
				
				$f = $('#credentialsForm');
				$('#credentialsForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/credentials/ea_saveCredentials.jspa");
				document.credentialsForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (credentialsID == '') {
					alert("请选择！");
					return;
				}
				if (credentialsID.substring(0, 2) == "sa") {
					$("#" + credentialsID).remove();
					credentialsID = "";
					/*var heis = parent.document.getElementById("mainframe13").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe13").style.height = heis;*/
					return;
				}
				$f = $('#credentialsForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/credentials/ea_delCredentials.jspa?credentials.staffID="
										+ staffID
										+ "&credentials.credentialsID="
										+ credentialsID);
				document.credentialsForm.submit.click();
				$("tr#" + credentialsID).remove();
				credentialsID = '';
				token = 11;
				break;
		}
	}
	
	$("input#invalidateStart").blur(function(){
    	$("input#start").val($(this).val());
    });
    
    $("input#invalidateEnd").blur(function(){
    	$("input#end").val($(this).val());
    });

	$(".credentials tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		credentialsID = this.id;
	});

	$(".credentials tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		credentialsID = this.id;
		action("修改");
	});
	
	$(".credentials").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/credentials/ea_getListCredentials.jspa?credentials.staffID="
				+ staffID+"&customer="+customer;
}