$(function(){
	if(depotName!="销售库"){
		$(".table").find("th").attr("style","width:12.1%");
	}
	//判断调入仓库
	if($("#choosedepotName").val()=="销售库"){
			$("#sale").attr("style","");         	status=1;
			$("#tr").find("td").eq(7).find("input").addClass("profit").attr("name","profitAmount");
			$("#tr").find("td").eq(8).find("input").addClass("sellingPrice").attr("name","pretium");
			$("#tr").find("td").eq(9).find("input").addClass("remarks").attr("readonly","").attr("placeholder","填写备注").attr("name","remark");
	}
	//选择物品
	$(".goodsName").click(function(){
		$(".goodsInformation").remove();
		$(this).parent().parent().addClass("goods");
		$.ajax({
			url:basePath+"ea/finished/sajax_ea_ajaxGetGoodsInformation.jspa",
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var tr=$("#sampleTr").clone(true).attr("id",list[i].inventoryID).attr("style","").addClass("goodsInformation");
					tr.find("td").eq(0).html("<input type='radio' name='goodsRadio' class='radio' id='"+list[i].goodsID+"'>" +
							"<input type='hidden' class='ppid' value="+list[i].productId+">");
					tr.find("td").eq(1).html("<span>"+list[i].goodsCoding+"</span>");
					tr.find("td").eq(2).html("<span>"+list[i].goodsName+"</span>");
					tr.find("td").eq(3).html("<span>"+list[i].goodsType+"</span>");
					tr.find("td").eq(4).html("<span>"+list[i].standard+"</span>");
					tr.find("td").eq(5).html("<span>"+list[i].unitPrice+"</span>");
					tr.find("td").eq(6).html("<span>"+list[i].invenQuantity+"</span>");
					tr.find("td").eq(7).html("<span>"+list[i].sumPrice+"</span>");
					tr.find("td").eq(8).html("<span>"+list[i].warehouseName+"</span>");
					$("#tbodya").append(tr);
				}
				$("#goodsjqModel").jqmShow();
			},
			error:function(data){
				alert("数据获取失败！");
			}
		});
	});
	//按钮操作
	$(".butt").click(function(){
		if($(this).val()=="保存"){
			var ir=0;
			$(".error").each(function(){
				$(this).css("background-color","red");
				ir++;
			});
			if($(".cashiDate").val()==""){
				alert("请填写出库日期！");
				return;
			}
			if(select==0){
				alert("请添加物品");
				return;
			}
			var t=true;
			$(".cloningContent").each(function(){
				if($(this).find(".goodsName").val()=="")
					t=false;
				else{
					if($(this).find(".profit").val()==""){
						$(this).find(".profit").addClass("error").css("background-color","red");
						ir++;
					}
				}
			});
			if(!t){
				alert("有未选择物品的空行");
				return;
			}
			if(ir>0)
				return;
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/finished/ea_editOutboundOrder.jspa");
			document.form.submit.click();
			token = 2;
		}else if($(this).val()=="增加一行"){ 
			select++;
			var tr=$("#tr").clone(true).attr("style","").attr("id","cloningContent"+select).addClass("cloningContent");
			tr.find("td").eq(0).text(select);
			tr.find("td").eq(10).find("a").attr("id","del"+select).attr("name",select);
			tr.find("input").each(function(){
				$(this).attr("name","goodsList["+(select-1)+"]."+$(this).attr("name"));
			});
			$("#tbody").append(tr);
			$("#delgoodtr").removeClass("grey").attr("disabled","");
		}else if($(this).val()=="删除新增行"){
			$("#cloningContent"+select).remove();
			select--;
			if(select==0)
				$("#delgoodtr").addClass("grey").attr("disabled","disabled");
		}else if($(this).val()=="打印预览"){
		}else{
			window.close();
		}
	});
	//删除物品行
	$(".dele").click(function(){
		var number=parseInt($(this).attr("name"));
		if($("#cloningContent"+number).find(".goodsBillsid").val()){
			if(confirm("该信息是单据原有物品，是否要继续删除！")){
				if($("#delGoodsBills").val()=="")
					$("#delGoodsBills").val($("#delGoodsBills").val()+$("#cloningContent"+number).find(".goodsBillsid").val());
				else
					$("#delGoodsBills").val($("#delGoodsBills").val()+","+$("#cloningContent"+number).find(".goodsBillsid").val());
			}else{
				return;
			}
		}
		$("#cloningContent"+number).remove();
		$(".cloningContent").each(function(){
			if($(this).attr("id")==("cloningContent"+(number+1))){
				$(this).attr("id","cloningContent"+number);
				$(this).find("a").attr("id","del"+number).attr("name",number);
				$(this).find("td").eq(0).text(number);
				$(this).find("input").each(function(){
					var str=$(this).attr("name").split(".");
					$(this).attr("name","goodsList["+(number-1)+"]."+str[1]);
				});
				number++;
			}
		});
		select--;
		if(select==0){
			$("#delgoodtr").attr("disabled","disabled").addClass("grey");
		}
		return false;
	});
	//添加选中的物品
	$(".goodsButton").click(function(){
		if($(this).val()=="查询"){
			
		}else if($(this).val()=="确定"){
			if(goodsId==""){
				alert("请选择！");
				return false;
			}
			var boo=true;
			$(".inventoryId").each(function(){
				if($(this).val()==goodsId)
					boo=false;
			});
			if(!boo){
				alert("请不要选择重复物品！");
				return false;
			}
			if(status==1){
				$.ajax({
					url:basePath+"ea/finished/sajax_ea_ajaxObtainGoodsHaveBeenSetUpToProfit.jspa?utboundOrderVo.ppId="+$("#"+goodsId).find(".ppid").val(),
					type:"post",
					async : false,
					success:function(data){
						var member = eval("(" + data + ")");
						var goods=member.goods;
						if(goods.profitAmount!=null){
							$(".goods").find(".profit").val(goods.profitAmount).attr("readonly","readonly");
							$(".goods").find(".sellingPrice").val(goods.pretium);
						}
					},
					error:function(){
						alert("数据获取失败！");
					}
				});
			}
			$(".goods").find(".goodsNum").val($("#"+goodsId).find("td").eq(1).text());
			$(".goods").find(".inventoryId").val(goodsId);
			$(".goods").find(".goodsName").val($("#"+goodsId).find("td").eq(2).text());
			$(".goods").find(".goodsId").val($("#"+goodsId).find(".radio").attr("id"));
			$(".goods").find(".productId").val($("#"+goodsId).find(".ppid").val());
			$(".goods").find(".goodsType").val($("#"+goodsId).find("td").eq(3).text());
			$(".goods").find(".specifications").val($("#"+goodsId).find("td").eq(4).text());
			$(".goods").find(".quantity").val($("#"+goodsId).find("td").eq(6).text());
			$(".goods").find(".price").val($("#"+goodsId).find("td").eq(5).text());
			$("#goodsjqModel").jqmHide();
			$(".goods").removeClass("goods");
		}else{
			$("#goodsjqModel").jqmHide();
			$(".goods").removeClass("goods");
		}
	});
	//添加选中的往来个人
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			
		}else if($(this).val()=="确定"){
			$(".staffName").val($("#"+staffId).find("#staffname").text());
			$(".staffId").val(staffId);
			$(".staffName").css("background-color","#FFFFFF").removeClass("error");
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	//选择往来个人
	$("#satffName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	// 添加选中仓库到
	$("#ckok").click(function() {
		//判断选择物品
		$i=$("#mainframe");
	    if ($i.contents().find("[name='a']").is(':checked')) {
    	var id=$i.contents().find("input[type='radio']:checked").val();//库房id
    	$r=$i.contents().find("#" +id);
    	var depotName=$r.find("#depotName").text();//库房name
    	var useState=$r.find("#useState").text();//启用/未启用
    	if(useState=="未启用"){
		     alert("未启用，请选择已启用仓库!");
		     return;
		}
		//赋值
		$("input#choosedepotID").val(id);	
		$("input#choosedepotName").val(depotName);
		$("input#choosedepotName").blur();
		$(".jqmWindow", $("#ckForms")).jqmHide();
		$(".hidetr").attr("style","display: none;");
		if(depotName=="销售库"){
			$("#sale").attr("style","");         	status=1;
			$("#tr").find("td").eq(7).find("input").addClass("profit").attr("name","profitAmount").attr("readonly","").attr("placeholder","填写利润金额");
			$("#tr").find("td").eq(8).find("input").addClass("sellingPrice").attr("name","pretium");
			$("#tr").find("td").eq(9).find("input").addClass("remarks").attr("readonly","").attr("placeholder","填写备注").attr("name","remark");
		}
		$("#addgoodtr").removeClass("grey").attr("disabled","");
	    }else {
			alert("请选择仓库！");
		}
	});
	//根据利润比例改变金额
	$(".profit").live("input propertychange",function(){
		var zz2=/^[0-9]+(.[0-9]{1,2})?$/;
		if(!zz2.test($(this).val()))
			$(this).attr("style","width:80%;background-color: red;").addClass("error");
		else{
			$(this).attr("style","width:80%;border: 0px;").removeClass("error");
			if($(this).parents("tr").find(".price").val()!=""){
				var profit=parseFloat($(this).parents("tr").find(".price").val())+parseFloat($(this).val());
				$(this).parents("tr").find(".sellingPrice").val(profit.toFixed(2));
			}
		}
	});
	
	//选择仓库
	$("input#choosedepotName").click(function() {
		$("#ckaadTree").attr("style","");
		$("#ckaadTree2").attr("style","display: none;");
		$(".xzck").attr("style","");
		$("#ckbody").html("");
		$("input#parmss").val("dc");
		$("#mainframe").attr("src","");
		$("#ckjqModel", $("#ckForms")).jqmShow();
	});
	//选择仓库关闭
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
	//物流方式添加取消
	$("input.JQueryreturn1").click(function() {// 取消
		var formID = $($("#formID").attr("value"));
		$("#newccode").jqmHide();
		$(".jqmWindow", formID).jqmShow();
	});
	//验证电话
	$(".telephone").live("input propertychange",function(){
		var zz1=/^1[3|4|5|8][0-9]\d{8}$/;
		var zz2=/^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		if(zz1.test($(this).val())||zz2.test($(this).val())||$(this).val()==""){
			$(this).css("background-color","#FFFFFF").removeClass("error");
		}else{
			$(this).css("background-color","red").addClass("error");
		}
	});
	//修改日期显示方式
	$(".date").each(function(){
		$(this).val($(this).val().split(" ")[0]);
	});
	//行点击事件 
	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("class")=="staff")
			staffId=$(this).attr("id");
		if($(this).attr("class")=="goodsInformation")
			goodsId=$(this).attr("id");
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});

/**
 * 选择科目
 */
/** **************************************科目管理******************************************* */
$(function(){
	$(".subrid").click(function(){
		if($(this).val()=="首页")
			subtrs("1",$(this).attr("id"));
		else if($(this).val()=="上一页")
			subtrs(parseInt(pagenum)-1,$(this).attr("id"));
		else if($(this).val()=="下一页")
			subtrs(parseInt(pagenum)+1,$(this).attr("id"));
		else if($(this).val()=="尾页")
			subtrs(Math.ceil(search2/14),$(this).attr("id"));
	});
	$(".subtr").live("click",function(){
		$(".subtr").each(function(){
			$(this).attr("style","background-color:#E0EEEE");
		});
		$(this).attr("style","background-color:#8DB6CD");
	});
	$("#determine").click(function(){
		$(".subtr").each(function(){
			if($(this).attr("style")=="background-color:#8DB6CD"){
				$("#subjectsid", $(".receivesubjects")).attr("value",
						$(this).find("td").find("input").val());
				$("#subjectscode", $(".receivesubjects")).attr("value",
						$(this).find("td").eq(0).text());
				$("#tosubjects", $(".receivesubjects")).attr("value",
						"("+$(this).find("td").eq(0).text()+")"+$(this).find("td").eq(1).text());
				$("#tosubjects",$(".receivesubjects")).css("background-color","#FFFFFF").removeClass("error");
				$(".receivesubjects").removeClass("receivesubjects");
			}
		});
		$("#subjectr").jqmHide();	
	});
	$("#cancel").click(function(){
		$("#subjectr").jqmHide();		
	});
	$("#tosubjects").click(function(){
		$(this).parent().parent().addClass("receivesubjects");
		if(!$(".subNumber").text()){
		var subjecturl = basePath
			+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjects = member.subjectsList;
				for(var i=0;i<subjects.length;i++){
					var tr;
					if(i==0){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line4.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else if(i==(subjects.length-1)){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line2.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else{
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line3.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}
					$("#kemuone").append(tr);
				}				
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		}
		$("#subjectr").jqmShow();	
	});
	$("#fudanshu").change(function(){
		if(/^[1-9]*[1-9][0-9]*$/.test($(this).val()))
			$(this).attr("style","border: 0px;width: 100%;height: 100%;").removeClass("err");
		else
			$(this).attr("style","border: 0px;width: 100%;height: 100%;background-color: red").addClass("err");
	});
	
	$.ajax({
		url : basePath+"ea/office/sajax_ea_sajaxGetThenFiveDepartmentsList.jspa",
		type : "post",
		async : false,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				var option="<option value='"+list[i].organizationID+"'>"+list[i].organizationName+"</option>";
				$("#orgValue").append(option);
				$(".orgVAL").append(option);
			}
		},
		error:function(){
			alert("数据获取失败");
		}
	});
	$(".clickOrg").live("change",function(){
		$(this).parent().find("input#organizationName").val($("option:selected",this).text());
	});
	$(".billData").live("dblclick",function(){
		window.open(basePath
				+ "/ea/splitbill/ea_toSaveCashierBills.jspa?cashierBills.cashierBillsID="+ $(this).find("td").eq(8).text()+"&status=view");
	});
});
function subtrs(pageNumber,obj){
	if(pageNumber>(Math.ceil(search2/14)))
		pageNumber=(Math.ceil(search2/14));
	if(pageNumber<1)
		pageNumber=1;
	$(".subrid").attr("id",obj);
	$("#kemutoo").find(".subtr").remove();
	pagenum=pageNumber;
	var subjecturl = basePath
		+ "ea/csbjects/sajax_ea_getAjaxListCsubejstsByPID.jspa?subjectsID=";
	$.ajax({
		url : encodeURI(subjecturl + obj+"&pageForm.pageNumber="+pageNumber+"&pageNumber=14&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var subjects = member.pageForm.list;
			search2=member.search;
			for(var i=0;i<subjects.length;i++){
				var lb="其他";
				if(subjects[i].subjectsCategory=="A")
					lb="银行";
				if(subjects[i].subjectsCategory=="B")
					lb="现金";
				if(subjects[i].subjectsCategory=="C")
					lb="固定资产";
				var tr="<tr align='left' class='subtr' style=\"background-color:#E0EEEE\">" +
					   "<td>"+subjects[i].subjectsNumbers+"</td>" +
					   "<td><span class='sprs' title="+subjects[i].subjectsName+">"+subjects[i].subjectsName+"</span></td>"+
					   "<td>"+lb+"<input type='hidden' value="+subjects[i].subjectsID+"></td>" +
					   "<td>"+(subjects[i].subjectsDirection=='D'?'借':'贷')+"</td>" +
					   "<td>"+(subjects[i].subjectsAccounts=='Y'?'主账户':'虚账户')+"</td></tr>"; 

				$("#kemutoo").append(tr);
			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});		
};
/**
 * 选择仓库
 */
function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	if(codeValue==""){
		alert("请输入！");
		return false;
	}
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var code = member.code;
					$("#newccode").jqmHide();
					$op = $("<option/>");
					$op.attr("value", code.codeValue).text(code.codeValue);
					$(selectID, formID).append($op);
					alert("操作成功！");
					$(".jqmWindow", formID).jqmShow();
					window.location.reload();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

function clWarehouse(){
	$(".cyc"+irr).each(function(){
		queryWarehouse("cl",$(this).attr("id"));
	});
	irr++;
	if($(".cyc"+(irr-1)).attr("class")){
		clWarehouse();irr=0;
	}
}

//选择仓库
$(function(){
	// 新增
	$(".xzck").click(function() {
		window.open(basePath
				+ "/ea/depotmanage/ea_getListDepotManage.jspa");
	});
	cycleWarehouse();
	$("#xmul").treeview({
		persist: "location",
	    collapsed: true,
	    unique: false
	});
	$(".Warehouse").click(function(){
		if("wu"==$(this).attr("name"))
			return;
	    var srcUrl=basePath +"ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=${pageNumber}&depotID="+ $(this).attr("id") + "&usetype=ck";	          					           
	    $("#mainframe").attr(
		"src",srcUrl);
		$(window).resize();
	});
});

function clickware(obj){
	if("wu"==$("#"+obj).attr("name"))
		return;
    var srcUrl=basePath +"ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=${pageNumber}&depotID="+ $("#"+obj).attr("id") + "&usetype=ck";	          					           
    $("#mainframe").attr(
	"src",srcUrl);
	$(window).resize();
}
//选择仓库
function cycleWarehouse(){
	$(".cycle"+r).each(function(){
		queryWarehouse("cy",$(this).attr("id"));
	});
	r++;
	if($(".cycle"+(r-1)).attr("class")){
		cycleWarehouse();r=0;
	}
}

function queryWarehouse(direction,obj){
    var	getListCCodeurl=basePath+"ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID="+obj+"&date="+new Date().toLocaleString();
    $.ajax({
        url: encodeURI(getListCCodeurl),
        type: "get",
        async: false,
        dataType: "json",
        success: function cbf(data){
           var member = eval("("+data+")");
            var nologin = member.nologin;
          if(nologin){
          document.location.href =basePath + "page/ea/not_login.jsp";
          }
           depotManagelist = member.depotManagelist;
           if(depotManagelist.length==0){
        	   $("."+direction+obj).removeClass("folder");
        	   $("."+direction+obj).addClass("file");
        	   $("."+direction+obj).attr("name","wu");
           } 				                
            for(var i=0;i<depotManagelist.length;i++)
		   {		             
            if(direction=="cy"){
            	var ul="<ul class='uls'><li><span class='Warehouse folder cycle"+(r+1)+" cy"+
         		depotManagelist[i].depotID+"' id='"+depotManagelist[i].depotID+"'>" +
         		depotManagelist[i].depotName+"</span></li></ul>";
            	$("."+direction+obj).after(ul);
            }else{
            	var ul="<ul class='uls2'><li><span class='Warehouse folder cyc"+(irr+1)+" cl"+
         		depotManagelist[i].depotID+"' id='"+depotManagelist[i].depotID+"' onclick=clickware('"+
         		depotManagelist[i].depotID+"')>" +
         		depotManagelist[i].depotName+"</span></li></ul>";
            	$("."+direction+obj).after(ul);
            }
             
           }
            token = 0;													 	
        },
        error: function cbf(data){
           alert("数据获取失败！");
        }
    });
    return;
}

/** **********************************往来个人**************************************** */
//选择往来个人
$(document)
		.ready(
				function() {
					treegr = new dhtmlXTreeObject("grTree", "100%", "100%", 0);
					treegr.enableDragAndDrop(false);
					treegr.enableHighlighting(1);
					treegr.enableCheckBoxes(0);
					treegr.enableThreeStateCheckboxes(false);
					treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
					treegr.setImagePath(basePath + "js/tree/codebase/imgs/");
					treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
					var getcodeurl = basePath
							+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
							+ new Date().toLocaleString();
					treegr.insertNewChild(0,
							"scode20110106hfjes5ucxp0000000017", "往来个人类别", 0,
							0, 0, 0);
					$.ajax({
						url : encodeURI(getcodeurl),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var codeList = member.codeList;
							if (null == codeList) {
								return;
							}
							for ( var i = 0; i < codeList.length; i++) {

								treegr.insertNewChild(
										"scode20110106hfjes5ucxp0000000017",
										codeList[i].codeID,
										codeList[i].codeValue, 0, 0, 0, 0);
								treegr.setUserData(codeList[i].codeID,
										"codeNumber", codeList[i].codeNumber);

							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					treegr.setOnClickHandler(function() {

						treeid = treegr.getSelectedItemId();
						treename = treegr.getItemText(treeid);

						var typeName = $("input#contactUserID",
								$("table#searchuser")).val();

						if (treeid == "scode20110106hfjes5ucxp0000000017") {
							treename = "";
						}
						$(":input#grparms").val(treename);
						
						var typeCN = "contactUser.staffName=" + typeName
						+ "&contactUser.relation=" + treename;
						
						if($("#xmtype").val()=="043111"){
							typeCN+="&dataIsComplete=00&identifier=baokaixue";
						}
					    
						cxwlgr(typeCN);
						return;

					});

					var cuID = "";
					var userstaffID = "";
					$("table#gouserstable tr[id]").live(
							"click",
							function(event) {
								cuID = this.id;
								userstaffID = this.title;
								$("input.rauser", $(this)).attr("checked",
										"checked");
							});

							// 选择往来个人
					$(".wlgr")
							.live("click",
									function() {
										
										$(".jqmWindow", $("#selectuserForm"))
												.jqmShow();
										cxwlgr("contactUser.staffName=&contactUser.relation=");
									});

					// 新增往来个人
					$(".xzgr")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
									});

					// 添加所选中的往来个人
					$("#qduser").click(
							function() {
								if (cuID != "") {

									var $tbl = $("#"+$("#linet").val());
									var clicktr = $("#line",$tbl).text();
									$("tr#" + clicktr,$tbl).find("#connectName")
											.val(
													$("tr#" + cuID).find(
															"#contactUserName")
															.text());
									$("tr#" + clicktr,$tbl).find("#connectID").val(
											cuID);
									$(".jqmWindow", $("#selectuserForm"))
											.jqmHide();
									$(".wlgr").parent().removeClass("rwl");
									return;
								} else {
									alert("请选择往来个人！");
								}
							});

					// 根据输入的往来个人名称查询
					$("input#searchuu").click(
							function() {
								cuID = "";
								userstaffID = "";
								var typeName = $("input#contactUserID",
										$("table#searchuser")).val();
								var relation = $(":input#grparms").val();
								cxwlgr("contactUser.staffName=" + typeName
										+ "&contactUser.relation=" + relation);
							});

					// 上一页
					$("#grsy").click(
							function() {
								var sy = $("#grsy").attr("title");
								if (sy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $(":input#grparms").val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + sy;
									cxwlgr(typeCN);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#grxy").click(
							function() {
								var xy = $("#grxy").attr("title");
								if (xy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $(":input#grparms").val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + xy;
									cxwlgr(typeCN);
								} else {
									alert("已是尾页！");
								}
							});

					// ajax查询往来个人列表
					function cxwlgr(typeCN) {
						if (notoken) {
							alert("正在获取数据！请稍等");
							return;
						}
						notoken = 1;
						$("#grsy").attr("title", 0);
						$("#grxy").attr("title", 0);
						$("#grzy").attr("title", 0);
						var searchurl = basePath
								+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
						
						$
								.ajax({
									url : encodeURI(searchurl + typeCN
											+ "&date="
											+ new Date().toLocaleString()),
									type : "get",
									async : true,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");
										var nologin = member.nologin;
										if (nologin) {
											document.location.href = basePath
													+ "page/ea/not_login.jsp";
										}

										var pageForm = member.pageForm;
										var codeRelationList = member.codeRelationList;
										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}

										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#grsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#grxy").attr("title", dqy + 1);
										}
										$("span#grzycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th></tr>";
										for ( var i = 0; i < pageForm.list.length; i++) {
											tabletr += "<tr style='cursor: hand;' id = "
													+ pageForm.list[i].relationID
													+ " title= "
													+ pageForm.list[i].staffID
													+ ">";
											tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
													+ pageForm.list[i].relationID
													+ " name='checkradio'/></td>";
											tabletr += "<td id='contactUserName' align='center'>"
													+ pageForm.list[i].staffName
													+ "</td>";
											tabletr += "<td id='phone' align='center'>"
													+ pageForm.list[i].relation
													+ "</td>";
											tabletr += "<td id='tel' align='center'>"
													+ pageForm.list[i].reference
													+ "</td>";
											tabletr += "<td id='staffIdentityCard' align='center'>"
													+ pageForm.list[i].staffIdentityCard
													+ "</td>";
											tabletr += "<td id='userQq'  align='center'>"
													+ pageForm.list[i].referenceCode
													+ "</td>";
											tabletr += "<td id='email'  align='center'>"
													+ pageForm.list[i].referenceOrganization
													+ "</td>";
											tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
													+ pageForm.list[i].staffID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cu").html(tabletr);
										$("#body_02cu").show();
										// alert("数据加载成功")
										notoken = 0;
										window.status = "数据加载成功";
									},
									error : function cbf(data) {
										notoken = 0;
										alert("数据获取失败！");
									}
								});
					}

				});

//获取员工
function getStaffMember(typeCN) {


	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	
	
	$
			.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";

					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}

					$("span#dpzycount").text(zys);

					for ( var i = 0; i < pageForm.list.length; i++) {

						
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
						
						
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";

					}
					
					
					$("#body_02dept").html(tabletr);


				},
				error : function cbf(data) {
					notoken = 0;

				}
			});
}
function re_load(){
	window.opener.location.href=window.opener.location.href;
	window.close();
}