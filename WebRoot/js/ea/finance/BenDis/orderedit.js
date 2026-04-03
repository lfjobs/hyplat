$(function(){
	
	$(".connectName").live("click",function(){
		if($(this).text()!=""){
			var connectID = $(this).parent().find("#connectID").val();
			var url = basePath+"ea/costsheet/sajax_ea_getStaffIdByConenetID.jspa?date="
			+ new Date().toLocaleString();
			
			$
			.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data:{
				  "staff.staffID":connectID
				 
				},
				success : function (data) {
					var member = eval("(" + data + ")");
					getStaffInfo(member.staffID);
				},
				error:function(data){
					alert("获取数据失败");
				}
			});
			
		}
		
	});
	
	if(billsType=="项目收入预算单"){
		$("#titlestyle").find("span").text("订单");
	}else if(billsType=="积分入库单"){
		$("#titlestyle").find("span").text("收款单");
	}else{
        $("#titlestyle").find("span").text(billsType);
	}
   
 //打印
	 $(".print").click(function(){
	
		 window.open(basePath
					+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID="
					+ cashierBillsID+"&vuvtype=printcsb");
		
	 });
     //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
		 window.close();
		 }
		 
	 });
		//审核
	 $(".audit").click(function(){
			if (confirm("确定继续？")) {

				var url = basePath
						+ "ea/costsheet/sajax_ea_confCostSheet.jspa?date="
						+ new Date().toLocaleString();

				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data : {
						"cashierBills.cashierBillsID" : cashierBillsID
					},
					success : function cbf(data) {
						alert("操作成功！");
						 $(".audit").attr("disabled",true).addClass("grey");

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
			}
	 });
	 
		//删除
	$(".deletesheet").click(function(){
	
	if (confirm("确定删除？")) {
				
				$("#examinecsbID").val(cashierBillsID);
				$("#sendForm").attr("action",basePath
						+ "ea/costsheet/ea_deteleCostSheet.jspa");
				document.sendForm.submit.click();
				
				alert("删除成功");
				
			}
		 
		
	 });
	 
		//新增
	 $(".newsheet").click(function(){
		document.location.href=basePath
					+ "/ea/costsheet/ea_toSaveCostSheet.jspa?jumptype="+jumptype+"&billsType="+billsType+"&zctype="+zctype;
		 
		 
	 });
	 
		//修改
	 $(".updatesheet").click(function(){
		document.location.href=basePath
		+ "/ea/costsheet/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + cashierBillsID+"&vuvtype=edit_e&jumptype="+jumptype;
	 });

   
	
	//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	       }).jqmAddClose('.close');//
	       
	//返回按钮
	$(".JQueryClose").click(function(){
		if(toSee=="history"){
			history.back();
		}else{
        re_load();
		}
    });
    
	$(".accessoriesUrl").click(function(){
	    var accessoriesUrl=$.trim($("#accessoriesUrl").attr("value"));   
	    OpenWord(accessoriesUrl,3);
	});
	
	if(status=="00"){
		$(".JQuerySubmitbh").show();
		$(".JQuerySubmit").show();
	}
	//驳回作废
	$(".JQuerySubmitbh").click(function(){
		 if(status=="billStatus"){
              alert("历史数据不可更改");
              return ;
         }
		 $form =$("#costSheetForm"); 
		 if (confirm("确定驳回作废？")){
			$form.attr("target","hidden").attr("action", basePath+"/ea/costsheetapprovedby/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
		 	$form.find("input#billStatus").val("10");
			 document.costSheetForm.submit.click();
			 csbID = "";
			 token = 2;
		 }
	});
	
	//审核通过
	$("input.JQuerySubmit").click(function(){
		 if(status=="billStatus"){
              alert("历史数据不可更改");
              return ;
         }
		 $form =$("#costSheetForm"); 
		 if (confirm("确定审核通过？")){
			 $form.attr("target","hidden").attr("action", basePath+"/ea/costsheetapprovedby/ea_updateResponsible.jspa?search="+search+"&pageNumber="+pNumber);
			 $form.find("input#billStatus").val("01");
			 document.costSheetForm.submit.click();
			 csbID = "";
			 token = 2;
	     }
	     return;
	});
	//判断显示
	var fxStatus=$("#fkStatus").val();
	if(fxStatus!='已付款'&& fxStatus!='已出库正在物流'){
		$("#apply").attr("style","color:gray;");
	}
	if(fxStatus!='同意退货'){
		$("#add").attr("style","color:gray;");
	}
	// 打印预览
	$("input.JQueryprint").click(function() {
			var csbID = $("input#csbID",$("#costSheetForm")).val();
			window.open(basePath + "/ea/costsheetapprovedby/ea_toprintcsb.jspa?costSheetBill.csbID="+csbID+"&toSee="+toSee);
	});
	$("#apply").click(function(){
		var fxStatus=$("#fkStatus").val();
		var clientPositioningID=$("#clientPositioningID").val();
		//if(fxStatus=='已付款未发货'||fxStatus=='已出库正在物流'){
		window.open(basePath+"ea/bdbill/ea_getRefund.jspa?id="+clientPositioningID+"&ppid="+$("#ppid").val()+"&fxStatus="+fxStatus);
		/*}else{
			alert("不能执行此操作");
			return false;
		}*/
		});
	$("#add").click(function(){
		
		var clientPositioningID=$("#clientPositioningID").val();
		//if(fxStatus=='同意退货'){
		window.open(basePath+"ea/bdbill/ea_getRefundSheetById.jspa?id="+clientPositioningID);
		/*}else{
			alert("不能执行此操作");
			return false;
		}*/
		});
	
});
function re_load(){
	if(status!=""){
		document.location.href = basePath+"/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type="+type+"&search="+search+"&pageNumber="+pNumber+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
	}else{
		document.location.href = basePath+"/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="+pNumber+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
	}
	
}
//查看详情
function viewDetail(ppID){
	if(ppID==null||ppID==""){
		alert("没有产品信息");
	}else{
		window.open(basePath+"/ea/prodesign/ea_getEditOrPrevPage.jspa?productPackaging.ppID="+ppID);
	}
}
/** ---------------------------------------------------------选择多个人员---------------------------------------------* */

/**
 * 
 * 提交审核
 */
function submitExamine() {

	if ($("#receiverID").val() == "") {
		alert("请选择审核人");
		return;
	}

	$("#examineComName").val(
			$("#receiverCompanyID").find("option:selected").text());
	$("#examineorgName").val(
			$("#receiverDeptID").find("option:selected").text());

	$("#examineName").val($("#receiverID").find("option:selected").text());
	$("#examinecsbID").val(cashierBillsID);
	$("#sendForm").attr("action",
			basePath + "ea/costsheet/ea_zbqSubmitExamine.jspa");
	document.sendForm.submit.click();
	alert("操作成功");
	
}


//查看人员详细信息
function getStaffInfo(personvalue){
	var url = basePath
	+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
	+ personvalue;
window.open(url);
	
}
