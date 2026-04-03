$(function(){
	 $("input.disabled").attr("disabled",false).removeClass("grey");
//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
	if(type1=="qrsh"){
		$(".JQuerySubmitgd").show();
		$("input#reQuantitys").css("display","none");
		$("input#reQuantity").css({"display":"block","width":"50%","height":"100%","margin-left":"2px"});
	}
	if($("input#billStatus").val()=="01"){
		search="search";
	}  
	$(".JQueryClose").click(function(){
			if(confirm("确定要关闭窗口？")){
		    	window.close();
				//re_load();
		     }
	});
	// 打印预览
	$("input.JQueryprint").click(function() {
		var cashierBillsID = $("input#cashierBillsID",$("#purchaseForm")).val();
		window.open(basePath + "/ea/purchase1/ea_toPrintPurchase.jspa?cashierBills.cashierBillsID="+cashierBillsID);
	});
	
	$(".JQuerySubmitgd").click(function(){
		var staffid=$("#staffauditid").val();//登录人员的id
		$(".put3").trigger("blur");
    	if ($("form .error").length) {
    		alert("收货数量为空或数量格式错误！");
    		//$("input#reQuantity").focus();
        	return false;
    	}
    	if(loginstaffcheck==0){
    	    alert("登录人员审核后再确认收货！");
        	return false;
    	}
    	
    	$("table#tt").find("tr.xggoods").each(function() {
    		$(this).find(':input').each(function() {
    			$(this).attr("name","goodsmap[" + select + "]." + this.name);
    		});
    		select ++;
    	});
    	
    	$("form#purchaseForm").attr("target","hidden").attr("action", 
    	basePath+"/ea/purchase1/ea_getUpdate.jspa?cashierBills.cashierBillsID="+cashierBillsID);
    	document.purchaseForm.submit.click();			
    	token=2;
	});
	//审核
	var staffname="";
	var id="";
	var cashierBillsID="";
	$(".btncon").click(function(){
		$("#jqModelSearch").jqmShow();
		cashierBillsID=$("#cashierBillsID").val();
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
					+ "/ea/purchase1/sajax_ea_saveBillCheck.jspa?cashierBills.cashierBillsID="+cashierBillsID
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
			    
			    
		    if(type1=="qrsh")
		     { 
	          document.location.href = basePath
				+ "/ea/purchase1/ea_toPurchase.jspa?pageNumber="
				+ pNumber + "&cashierBills.cashierBillsID="
				+ cashierBillsID+"&search="+search+"&type="+type+"&type1=qrsh"+"&remark="+remark;
	         }
		 
		    
		}
	});
});
function re_load(){
	      var cashierBillsID = $("input#cashierBillsID",$("#purchaseForm")).val();
		  var url = basePath+ "/ea/purchase1/ea_toPurchase.jspa?pageNumber="
					+ pNumber + "&cashierBills.cashierBillsID="
				    + cashierBillsID;
		  document.location.href = url;//跳转到查看页面
}
