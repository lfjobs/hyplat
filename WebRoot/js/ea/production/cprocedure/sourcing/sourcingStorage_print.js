$(function(){
	if(status=="26"){
		$(".display").css("display","none");
	}else{
		$(".examine").css("display","none");
	}
	$(".operation").click(function(){
		if($(this).val()=="提交审核"){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/sourcingsto/ea_submitAudit.jspa");
			document.form.submit.click();
			token = 2;
		}else{
			window.close();
		}
	});
	//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
});
$(function(){
	/**----------------------------------------------------------------审核开始---------------------------------------------------------------*/
	var cashierBillsID="",id="";
	$(".btncon").click(function(){
		$("#single").jqmShow();
		cashierBillsID=$("#cashierBillsID").val();
		id=$(this).attr("id");
	});
	$(".but").click(function(){
		if($(this).val()=="提交"){
			if ($("#single").find("textarea").val() == "") {
				alert("请填写审核意见");
				return;
			}
			var edate=$("#single").find(".radio").eq(0).attr("checked")==true?$("#single").find(".radio").eq(0).val():$("#single").find(".radio").eq(1).val();
			if(confirm("你确定要"+(edate=="yes"?"通过":"驳回")+"此单据吗？")){
				var subRuleurl = basePath
						+ "/ea/newsales/sajax_ea_saveBillCheck.jspa?cashierBills.cashierBillsID="+cashierBillsID
						+"&deptpost="+id+"&remarks="+$("#single").find("textarea").val();
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
			}
		}else{
			 $("#single").jqmHide();
		}
	});
	if(status=="10"){
		   $(".verify").hide();  
	   }
	/**----------------------------------------------------------------审核开始---------------------------------------------------------------*/
})
function re_load(){
	window.location.href=window.location.href;
}