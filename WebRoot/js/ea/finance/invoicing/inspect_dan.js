// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;

$(function() {
	$f = $("form#billGoodsForm");
	$t=$f.find("table#goodtable");
	$t1=$f.find("table#table2");
	//收货金额合计
	var scountmoney =0;
	$t.find("span#money").each(function() {
		    var a=parseFloat($(this).text());
			scountmoney+=a;
	});
    $t1.find("span#scountmoney").text(scountmoney);   
    //-------------------------------------- 合格数量计算金额
	$t.find(".jisuan").live("keyup", function(event) {
		if (this.value != "") {
			//判断
			var reQuantity = $(this).parent().parent().find("span#reQuantity").text();
			if(parseInt(this.value)>parseInt(reQuantity)){
			   alert("合格数量大于收货数量！");
			   $(this).val("");
			   return;
			}
			//计算不合格数量和合格金额以及不合格金额
			if (isNaN(this.value)) {
				return;
			} else {
				var hgnum = $(this).val();
				var shnum = $(this).parent().parent().find("span#reQuantity").text();
				$(this).parent().parent().find("input#noisQualify").attr(
							"value",Number(shnum)-Number (hgnum));
				var price = $(this).parent().parent().find("span#price").text();
				if (!isNaN(hgnum) && !isNaN(price)) {
					var jine = hgnum * price;
					jine = Math.round(jine * 100) / 100;
					$(this).parent().parent().find("span#hgamount").text(jine);
				}
			    var scm=$(this).parent().parent().find("span#money").text();//收货金额
	            var hecm=$(this).parent().parent().find("span#hgamount").text();//不合格金额
			    $(this).parent().parent().find("input#bhgamount").val(parseFloat(scm)-parseFloat(hecm));
			}
		}
		//合格金额合计
		var hgcountmoney =0;
		$t.find("span#hgamount").each(function() {  
			 var a=parseFloat($(this).text());
			 if (!isNaN(a)) {
			 hgcountmoney+=a;
			 }
		});
	    $t1.find("span#hgcountmoney").text(hgcountmoney);
	    //不合格金额合计
	    var bhgcountmoney =0;
		$t.find("input#bhgamount").each(function() {  
			 var a=parseFloat($(this).val());
			 if (!isNaN(a)) {
			 bhgcountmoney+=a;
			 }
		});
	    $t1.find("span#bhgcountmoney").text(bhgcountmoney);
	    
	});
	$(".jisuan").live("keydown", function(event) {
			if (this.value != "") {
			//判断
			var reQuantity = $(this).parent().parent().find("span#reQuantity").text();
			if(parseInt(this.value)>parseInt(reQuantity)){
			   alert("合格数量大于收货数量！");
			   $(this).val("");
			   return;
			}
			//计算不合格数量和合格金额以及不合格金额
			if (isNaN(this.value)) {
				return;
			} else {
				var hgnum = $(this).val();
				var shnum = $(this).parent().parent().find("span#reQuantity").text();
				$(this).parent().parent().find("input#noisQualify").attr(
							"value", parseInt(shnum)-parseInt(hgnum));
				var price = $(this).parent().parent().find("span#price").text();
				if (!isNaN(hgnum) && !isNaN(price)) {
					var jine = hgnum * price;
					jine = Math.round(jine * 100) / 100;
					$(this).parent().parent().find("span#hgamount").text(jine);
				}
			    var scm=$(this).parent().parent().find("span#money").text();//收货金额
	            var hecm=$(this).parent().parent().find("span#hgamount").text();//不合格金额
			    $(this).parent().parent().find("input#bhgamount").val(parseFloat(scm)-parseFloat(hecm));
			}
		}
		//合格金额合计
		var hgcountmoney =0;
		$t.find("span#hgamount").each(function() {  
			 var a=parseFloat($(this).text());
			 if (!isNaN(a)) {
			 hgcountmoney+=a;
			 }
		});
	    $t1.find("span#hgcountmoney").text(hgcountmoney);
	    //不合格金额合计
	    var bhgcountmoney =0;
		$t.find("input#bhgamount").each(function() {  
			 var a=parseFloat($(this).val());
			 if (!isNaN(a)) {
			 bhgcountmoney+=a;
			 }
		});
	    $t1.find("span#bhgcountmoney").text(bhgcountmoney);
	    
	});
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
		}).jqmAddClose('.close');
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
});

/** **************************************仓库管理开始******************************************* */
$(document).ready(function() {
	// 新增
	$(".xzck").click(function() {
		window.open(basePath
				+ "/ea/depotmanage/ea_getListDepotManage.jspa");
	});
	//选择仓库
	$("input#depotName").click(function() {
		$("#ckbody").html("");
		$(".jqmWindow", $("#ckForms")).jqmShow();
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
		$("input#depotID").val(id);	
		$("input#depotName").val(depotName);
		$("input#depotName").blur();
		$i.contents().find("input[type='radio']:checked").attr("checked", false);
		$(".jqmWindow", $("#ckForms")).jqmHide();
	    }else {
			alert("请选择仓库！");
		}
	});
	
});	
/** **********************************仓库管理结束**************************** */
