$(document).ready(function() {
		$(".jqmWindow").jqm({
			modal : true,// 
			overlay : 20
			}).jqmAddClose('.close');
		$('#item').flexigrid({
			title:"个税管理",
			width : 'auto',
			height:'auto',
			minwidth : 30,
			minheight : 80,
			buttons : [{
				name : '设置缴税规则',
				bclass : 'edit',
				onpress : action
				}, {
				separator : true
			},{
				name : '查询',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			}]
		});
	function action(com, grid) {
		switch (com) {
			case '设置缴税规则' :
				$("#addform").find("tr.trev").remove();
				$("#jqmadd").jqmShow();
				break;
			case '查询' :
				document.searchform.reset();
				$("#jqmsearch").jqmShow();
				break;
		}
	}
});

function saveM(){
	$("#addform").attr("action",basePath+"ea/tax/ea_saveItem.jspa?search=" + search);
	$("#addform").submit();
}
function searchM(){
	$("#searchform").attr("action",basePath+"ea/tax/ea_toSearch.jspa?");
	$("#searchform").submit();
}
function closeM(){
	window.location.href=basePath+"ea/tax/ea_findItem.jspa?search=" + search;
}
function addItem(tableid){
	var htr=new Array();
	htr.push("<tr>");
	htr.push("<td><span id=\"sn\">递增</span></td>");
	htr.push("<td colspan=\"2\">&gt;<input type=\"text\" size=\"8\" maxlength='8' name=\"titem["+itemSn+"].wageTaxLow\"");
	htr.push(" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onblur=\"this.value=this.value.replace(/[^\\d]/g,'')\" />");
	htr.push("&lt;=<input type=\"text\" size=\"8\" maxlength='8' name=\"titem["+itemSn+"].wageTaxHigh\"");
	htr.push(" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onblur=\"this.value=this.value.replace(/[^\\d]/g,'')\" /></td>");
	htr.push("<td><input type=\"text\" size=\"5\" maxlength=\"5\" name=\"titem["+itemSn+"].wageTaxRate\"");
	htr.push(" onkeyup=\"this.value=this.value.replace(/[^\\d]/g,'')\" onblur=\"this.value=this.value.replace(/[^\\d]/g,'')\" />");
	htr.push("</td><td style=\"text-align: right\"><input type=\"button\" value=\"-\" onclick='javascript:delItem(this)'/></td>");
	htr.push("</tr>");
	$("table#"+tableid).append(htr.join(""));
	itemSn++;
}
function delItem(val){
	$(val).parent().parent().remove();
}