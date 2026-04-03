$(function(){
//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
	      
	$(".JQueryClose").click(function(){
		if(confirm("确定要关闭？")){
        window.close();
		}
			//re_load();
    });
	// 打印预览
	$("input.JQueryprint").click(function() {
		var cashierbillsID = $("input#cashierID",
				$("#purchaseForm")).val();
		window.open(basePath + "/ea/receive/ea_toWareManagement.jspa?cashierBills.cashierBillsID="+cashierbillsID+"&print=print");
	});
	//审核
	var staffname="";
	var id="";
	var cashierBillsID="";
	$(".btncon").click(function(){
		$("#jqModelSearch").jqmShow();
		cashierBillsID=$("#cashierID").val();
		staffname=$("#staffauditname").val();
		var staffid=$("#staffauditid").val();
		id=$(this).attr("id");
	});
	$("#tocheckok").click(function(){
		var remark=$("#remarks").val();
		var status=($(".status").eq(0).attr("checked")?$(".status").eq(0).val():$(".status").eq(1).val());
		var subRuleurl = basePath
				+ "/ea/receive/sajax_ea_saveBillCheck.jspa?cashierBills.cashierBillsID="+cashierBillsID
				+"&deptpost="+id+"&status="+status+"&remarks="+remark;
				+"&date="+ new Date().toLocaleString();
		 	$.ajax({
			url : encodeURI(subRuleurl),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				if(status=="02"){
					alert("审核成功");
					window.location.reload();
				}else{
					alert("驳回成功");
					window.opener.location.reload(); 
					window.close();
				}
				
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		   });
		    $("#jqModelSearch").jqmHide();
			$("input#remark").val(remark);
		    $("input."+id).val(staffname);
		    $("input#"+id).css("display","none");
	});
	$("#tocheckclose").click(function(){
	   $("#jqModelSearch").jqmHide();
	});
});

function re_load(){
	document.location.href=basePath+"/ea/allocation/ea_getWareManagementList.jspa?pageNumber="+pNumber
	+"&search="+search+"&sdate="+sdate
	+"&edate="+edate+"&billStatus="+billStatus;
}
