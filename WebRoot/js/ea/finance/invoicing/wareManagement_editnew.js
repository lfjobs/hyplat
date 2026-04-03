$(function(){
//判断此单据是否为驳回状态
	if(status=="10"){
		   $(".verify").hide();  
	   }
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
		window.open(basePath + "/ea/newsales/ea_toWareManagement.jspa?cashierBills.cashierBillsID="+cashierbillsID+"&print=print");
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
		if(!($(".xuanxiang").eq(0).attr("checked")||$(".xuanxiang").eq(1).attr("checked"))){
			alert("请选择一个选项");
			return;
		}
		if ($.trim($("#comments").val()) == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		}
		var edate=$(".xuanxiang").eq(0).attr("checked")==true?$(".xuanxiang").eq(0).val():$(".xuanxiang").eq(1).val();
		if(confirm("你确定要"+(edate=="02"?"通过":"驳回")+"此单据吗？")){
			var subRuleurl = basePath
					+ "/ea/newsales/sajax_ea_saveBillCheck.jspa?cashierBills.cashierBillsID="+cashierBillsID
					+"&deptpost="+id+"&remarks="+remark;
					+"&date="+ new Date().toLocaleString();
			 	$.ajax({
				url : encodeURI(subRuleurl),
				type : "get",
				async : false,
				data: {"type": edate},
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath + "page/ea/not_login.jsp";
					}
					if(edate=="03"){
						self.opener.location.reload(); //刷新父窗口
						 window.close();	
				    }else{
				    	window.location.reload();
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
			    
			    

		}
	});
	$("#tocheckclose").click(function(){
	   $("#jqModelSearch").jqmHide();
	});
});

function re_load(){
	document.location.href=basePath+"/ea/newsales/ea_getWareManagementList.jspa?pageNumber="+pNumber+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&billStatus="+billStatus;
}
