$(document).ready(function() {
		$(".jqmWindow").jqm({
			modal : true,// 
			overlay : 20
			}).jqmAddClose('.close');
		$('#item').flexigrid({
			title:"工资核算",
			width : 'auto',
			height:'auto',
			minwidth : 30,
			minheight : 80,
			buttons : [{
				name : '核算工资',
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
			case '核算工资' :
				$("#jqmhesuan").jqmShow();
				break;
			case '查询' :
				$("#jqmsearch").jqmShow();
				break;
		}
	}
});
function findMX(resultId){
	$.ajax({
		url:basePath+"ea/payroll/sajax_ea_findMX.jspa",
		data:"resultId="+resultId,
		type:"get",
		success:function(data){
			var member = eval("("+data+")");
			var details = member.details;
			var htr = new Array();
			for ( var i = 0; i < details.length; i++) {
				htr.push("<tr><td>"+details[i].wageItemName+":</td><td>"+details[i].realMoney+"元</td></tr>");
			}
			$("div#contents").html("<table>"+htr.join("")+"</table>");
			$("#jqmdetail").jqmShow();
		}
	});
	
}
function hesuanM(){
	if($("#hesuanform").find("#pdate").val().trim()==""){
		Showbo.Msg.alert("核算日期不能为空.");
		return;
	}
	$("#hesuanform").find("#sdate").val($("#hesuanform").find("#pdate").val());
	$("#hesuanform").attr("action",basePath+"ea/payroll/ea_payRoll.jspa");
	$("#hesuanform").submit();
}
function searchM(){
	$("#searchform").attr("action",basePath+"ea/payroll/ea_findItem.jspa");
	$("#searchform").submit();
}
function closeM(){
	$("#jqmsearch").jqmHide();
	$("#jqmhesuan").jqmHide();
}
