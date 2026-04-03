$(document)
		.ready(
				function() {
					var len = $("#tbwid").find(".trclass").length;
					window.parent.document.getElementById("mainframe22").style.height = 180
							+ len * 27 + "px";
					$(".jqmWindow").jqm({
						modal : true,// 
						overlay : 20
					// 
					}).jqmAddClose('.close');//
					// .jqDrag('.drag');//
					$(".contactimg").flexigrid({
						height : 'auto',
						allDouble : true,
						width : 'auto',
						minwidth : 30,
						minheight : 80,
						buttons : [ {
							name : '添加',
							bclass : 'add',
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
						} ]
					});
					function action(com, grid) {
						switch (com) {
						case '添加':
							$("#jqmadd").jqmShow();
							break;
						case '删除':
							if (contactimgID == '') {
								alert("请选择！");
								return;
							}
							
							$f = $('#cImgForm');
							if (notoken)
								return;
							notoken = 1;
							$f.attr("target", "hidden")
									.attr(
											"action",
											basePath
													+ "ea/contactimg/ea_delImg.jspa?cimg.contactimgID="
													+ contactimgID
													+ "&cimg.imgFile="
													+ $("tr#"+contactimgID).find("span#imgFile").text());
							document.cImgForm.submit.click();
							$("tr#" + contactimgID).remove();
							contactimgID = "";
							token = 11;
							break;
						}
					}

					$(".contactimg tr[id]").click(
							function() {
								$("input.JQuerypersonvalue", $(this)).attr(
										"checked", "checked");
								contactimgID = this.id;
							});

				});
function ck(photoVal){
	$t = $('#showTab');
	if (photoVal != "") {
		$t.find("img#idImg").attr("src", basePath + photoVal);
		$("#jqmshow").jqmShow();
	}
}
function saveM(){
	$("#saveform").attr("action",basePath+"ea/contactimg/ea_saveImg.jspa?cimg.ccompanyID=" + ccompanyID);
	$("#saveform").submit();
}
