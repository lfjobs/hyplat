$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe14").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.documentation').flexigrid({
				height : 'auto',
				allDouble : true,
				width : 'auto',
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
							"documentationmap[" + select + "]." + this.name);
				});
				
				$("input#start").val('');   
                $("input#end").val('');
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe14").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe14").style.height = heis;
				select++;
				break;
			case '修改' :
				if (documentationID == '') {
					alert("请选择！");
					return;
				}

				if ($("#" + documentationID + " #status").attr("value") == '01') {
					alert("不可修改");
					break;
				}
				$p = $("tr#" + documentationID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"documentationmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
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
				$(".check").find('input.err').each(function(){
					if($(this).val()==''){
						$(this).css("background-color","red");
						err = 1;
					}
				});
				if(err){
					return;
				}
				notoken = 1;
				$f = $('#documentationForm');
				$('#documentationForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentation/ea_savedocumentation.jspa");
				document.documentationForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (documentationID == '') {
					alert("请选择！");
					return;
				}
				if (documentationID.substring(0, 2) == "sa") {
					$("#" + documentationID).remove();
					documentationID = "";
					var heis = parent.document.getElementById("mainframe14").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe14").style.height = heis;
					return;
				}
				$f = $('#documentationForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentation/ea_deldocumentation.jspa?documentation.staffID="
										+ staffID
										+ "&documentation.documentationID="
										+ documentationID);
				document.documentationForm.submit.click();
				$("tr#" + documentationID).remove();
				documentationID = '';
				token = 11;
				break;
		}
	}
	
	$("input#documentationManagerStart").blur(function(){
    	$("input#start").val($(this).val());
    });
    
    $("input#documentationManagerEnd").blur(function(){
    	$("input#end").val($(this).val());
    });

	$(".documentation tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		documentationID = this.id;
	});

	$(".documentation tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		documentationID = this.id;
		action("修改");
	});
	
	$(".documentation").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/documentation/ea_getListDocumentation.jspa?documentation.staffID="
				+ staffID;
}