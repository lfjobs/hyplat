$(function(){
	
	//获取项目物品树
	getProjectByParent(proID,projectName);
	
	
	
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
	if(zctype=="t"){
		$(".tz").show();
		$(".newsheet").attr("disabled",true).addClass("grey");
	}else{
		$(".tz").hide();
	}
	if(xmtype=="043111"){
		$(".baokx").show();
	}else{
		$(".baokx").hide();
	}
	/*---------------              显示审核公章               ---------------*/
	var tax = $("#status").val();
	
   if (tax == "02") {
		var str = "<img src='" + basePath
				+ "images/ea/finance/yishen.png'/>";
		$("#apDiv1").html(str);
	} else {
		var str = "<img src='" + basePath
				+ "images/ea/finance/daishen.png'/>";
		$("#apDiv1").html(str);
	}
    
	if(billsType=="项目收入预算单"){
		   $("#titlestyle").find("span").text("项目收入预算单");
		   $(".audit").val("确定预算");
		}else{
			 $("#titlestyle").find("span").text("项目支出预算单");
			 $(".audit").val("比价审核");
		}
   
   
	if(jumptype!="fxlb"){
		 $(".newsheet").attr("disabled",true).addClass("grey");
		

	 }
     
     if(jumptype!=""){
   //菜单
   if(status=="00"){
	  $(".deletesheet").attr("disabled",false).removeClass("grey");
   }else{
	   $(".deletesheet").attr("disabled",true).addClass("grey"); 
   }
   
   if(status=="00"||status=="11"){
	   $(".audit").attr("disabled",false).removeClass("grey");
	 
		  $(".updatesheet").attr("disabled",false).removeClass("grey");
	   }else{
		   $(".audit").attr("disabled",true).addClass("grey");
		
		   $(".updatesheet").attr("disabled",true).addClass("grey");
	   }
   
     }else{
    	
    	  
		   $(".updatesheet").attr("disabled",true).addClass("grey");
		   $(".deletesheet").attr("disabled",true).addClass("grey"); 
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
	
	// 打印预览
	$("input.JQueryprint").click(function() {
			var csbID = $("input#csbID",$("#costSheetForm")).val();
			window.open(basePath + "/ea/costsheetapprovedby/ea_toprintcsb.jspa?costSheetBill.csbID="+csbID+"&toSee="+toSee);
	});
	
});
function re_load(){
	if(status!=""){
		document.location.href = basePath+"/ea/costsheetapprovedby/ea_getApprovedByList.jspa?type="+type+"&search="+search+"&pageNumber="+pNumber+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
	}else{
		document.location.href = basePath+"/ea/costsheet/ea_getCostSheetList.jspa?pageNumber="+pNumber+"&type="+type+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&treeType="+treeType+"&jumptype="+jumptype;
	}
	
}

/** ---------------------------------------------------------选择多个人员---------------------------------------------* */
/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;

			var str = "<option value=''>请选择公司</option>";
			for ( var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#jqModelSend select#receiverCompanyID").html(str);
			$("#jqModelSend #receiverDeptID").html(
					"<option value=''>请选择部门</option>");
			$("#jqModelSend #receiverID").html(
					"<option value=''>请选择人员</option>");
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdept(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"companyID" : val
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var orgaizationlist = member.orgaizationlist;
			var str = "<option value=''>请选择部门</option>";
			for ( var i = 0; i < orgaizationlist.length; i++) {
				var obj = orgaizationlist[i];
				str += "<option value='" + obj.organizationID + "'>"
						+ obj.organizationName + "</option>";
			}
			$("#jqModelSend #receiverDeptID").html(str);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPerson(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"currentCompanyID" : company,
			"checkOrgID" : org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>请选择人员</option>";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj.staffID + "'>" + obj.staffName
						+ "(" + obj.staffCode + ")</option>";
			}
			$("#jqModelSend #receiverID").html(str);
		}
	});
}
/**
 * 
 * 当公司改变时，获取部门
 * 
 * @param {}
 *            obj
 */
function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#jqModelSend #receiverDeptID").html(
				"<option value=''>请选择部门</option>");
	}
}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	if ($(obj).val() != '') {
		getPerson($("#jqModelSend #receiverCompanyID").val(), $(obj).val());
	} else {
		$("#jqModelSend #receiverID").html("<option value=''>请选择人员</option>");
	}
}

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

function getProjectByParent(ppID,parentName){
	$("#xmul").html("");
	var url = basePath+"ea/costsheet/sajax_ea_getProjectByParent.jspa?date="
				+ new Date().toLocaleString();
	$
	.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{
		  "productPack.ppID":ppID,
		  "cashierBills.cashierBillsID":cashierBillsID
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var oList = member.productlist;
		
		
	
			var data1 = new Array();

			for (var i = 0; i < oList.length; i++) {
				data1[i] = {
						id : oList[i][0],
						pid : oList[i][1],
						text : oList[i][2]
				};
			}

			var ts = new TreeSelector($("ul#xmul"),
					data1, null);
			ts.createTree();
			
			$("#xmul").treeview({
	            collapsed: false,
	            unique: false
	        });
		
			
			
			$("#xmul").find("span").each(function(){
						var tbody = "";
						var proid = $(this).parent().attr("id");
                       
						$("#goodtable").find("tr").each(function() {
						select++;
							if ($(this).find("#ppID").val() == proid) {
					
			
								tbody += "<tr id='kelong"+select+"' class='checkgoods'>" + $(this).html() + "</tr>";
								$(this).remove();
								
								
							}
						

						});
						
						select++;
						
						
		    	
		    	
				var $newtable = $("#Layer1").clone();
				$newtable.find("table").attr("id",$(this).parent().attr("id")+"tbl")
				.addClass("datatbl").html("<tr>"+$("#goodtable").find("tr:eq(0)").html()+"</tr><tr>"+tbody+"</tr>");

				$(this).after($newtable.html());
				
			

				
           });
			

			
			
			
		},error:function(data){
			alert("获取项目失败");
		}
		});

}

//查看人员详细信息
function getStaffInfo(personvalue){
	var url = basePath
	+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
	+ personvalue;
window.open(url);
	
}


//查看部门详情 parentid父部门，treeid当前部门ID
function getDeptInfo(){
	var treeid=$("#departmentID").val();
	var parentid =  "";
	var parentname = "";
	var url = basePath+"ea/costsheet/sajax_ea_getParentOrg.jspa?date="
	+ new Date().toLocaleString();
	$
	.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{
		  "cashierBills.departmentID":treeid
		 
		},
		success : function (data) {
			var member = eval("(" + data + ")");
		     parentid = member.parentid;
		     parentname=member.parentname;
		},
		error:function(data){
			alert("获取数据失败");
		}
	});
	
	
window.open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=0&porganization.organizationID="
	 + parentid + "&porganization.organizationName=" + parentname + "&organization.organizationID="
	 + treeid);


}