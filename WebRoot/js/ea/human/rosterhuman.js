$(document).ready(function() {

	$(".jqmWindow").jqm({
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexmepost').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '花名册',
				minwidth : 20,
				minheight : 100,

				buttons : [{
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
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印',
					bclass : 'excel',
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
			document.postAddForm.reset();
			$("#addupdateform").empty().append("<span id='information'>添加信息</span>");
			$("#addroster").attr("value","添加");
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
			if(rosterid == ""){
			alert("请选择!");
			return;
			}
			document.postAddForm.reset();
				$t = $("table#rosterAddTable");
				$p = $("tr#" + rosterid);
				$p.find("span[id]").each(function() {
					$t.find("input#" + this.id).val($(this).text());
				});
				$("#addupdateform").empty().append("<span id='information'>修改信息</span>");
				
				$("#addroster").attr("value","修改");
				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if(rosterid == ""){
				alert("请选择!");
				return;
				}
				if (rosterid.substring(0, 2) == 'sa') {
					if (confirm("是否删除?")) {
						$("#" + rosterid).remove();
						rosterid = "";
					}
					return;
				}
				$f = $('#rosterForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					$f.attr("target", "hidden").attr(
							"action",
							pbasePath + "ea/roster/ea_del.jspa?pageNumber="
									+ pageNumber + "&roster.rosterid="
									+ rosterid);
					document.rosterForm.submit.click();
					$("tr#" + rosterid).remove();
					rosterid = '';
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = pbasePath + "ea/roster/ea_getRosterAll.jspa?search="+ search;
				numback(url);
				break;
			case '打印' :
				$("#jqModelprint").jqmShow();
				break;
		}
	}
	$(".JQueryflexmepost tr[id]").click(function() {
				rosterid = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			
	$(".JQueryflexmepost tr[id]").dblclick(function() {
				action('修改');
			});
			/**
 * 添加信息
 */
$("input.JQuerySubmit").click(function() {// 保存
				if ($("form .error").length) {
					return;
				}
				if($("#addroster").val()=="添加"){
				$('#postAddForm').attr("target", "hidden").attr("action",
			pbasePath + "ea/roster/ea_addroster.jspa?pageNumber="
						+ pNumber);
			document.postAddForm.submit.click();
			token = 2;
				}else{
					$('#postAddForm').attr("target", "hidden").attr("action",
			pbasePath + "ea/roster/ea_addroster.jspa?roster.rosterid="+rosterid+"&pageNumber="
						+ pNumber);
			document.postAddForm.submit.click();
			token = 2;
				}
				
			});
/**
 * 打印信息
 */
$("#toprint").click(function(){
	$("#jqModelprint").jqmHide();
	$k = $("table#rosterPrintTable");
	$k.find("[class='checkbox']").each(function() {
					if($(this).attr("checked")==false){
					//$(this).css("display","none");
					}
				});
//花名册信息
       	var printpage = pbasePath + "ea/roster/sajax_ea_getRosterAll.jspa?status=1";
        open(printpage);
	});
	
});
function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/roster/ea_getRosterAll.jspa?search="
						+ search+ "&pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
