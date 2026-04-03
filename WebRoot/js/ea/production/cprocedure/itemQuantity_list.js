$(function(){
	var title=" <form name='likeForm' id='likeForm'>项目产品批量生产出库 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 单据编号：" +
			"<input type='text' id='likeJournalnum' style='width:100px;'>&nbsp;&nbsp;&nbsp;物品名称：" +
			"<input type='text' id='likeGoodsname' style='width:100px;'>&nbsp;&nbsp;&nbsp;" +
			"<input type='button' id='like' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
				name : '导出',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
				name : '打印',
				bclass : 'printer',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
	function action(com, grid) {
		switch (com) {

			case '添加' :
					$.ajax({
						url:basePath+"ea/quantity/sajax_ea_ajaxAddASinglePageData.jspa",
						type:"post",
						async : false,
						success:function(data){
							var member = eval("(" + data + ")");
							var list=member.list;
							$(".goodsOutOfTheLibrary").find("#journalNum").val(member.journalNum);
							$(".goodsOutOfTheLibrary").find("#warehouseName").val(list[0].depotName);
							$(".goodsOutOfTheLibrary").find("#warehouseId").val(list[0].depotID);
							$(".goodsOutOfTheLibrary").find("#staffID").val(member.staffId);
							$(".goodsOutOfTheLibrary").find("#staffName").val(member.staffName+" - "+member.staff.staffCode);
							$(".goodsOutOfTheLibrary").find("#currentTime").val(member.currentTime);
							$("#single").jqmShow();
						},
						error:function(data){
							alert("数据获取失败！");
						}
					});
				break;
			case '导出' :
				
				var obj=new Array();
				$(".dps").each(function(i,element){				
					obj[i]=new Array();
					for(var r=0;r<11;r++){
						obj[i][r]=$(this).find("span").eq(r).text();
					}
				}); 
				var json=arrayToJson(obj);
				window.location.href=basePath+"ea/quantity/ea_exportExcelTable.jspa?json="+json;			
				
				break;
			case '打印' :
				if(id==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/quantity/ea_toPrint.jspa?utboundOrderVo.cashierbillsid="+id+"&type=action");
				break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?pageNumber="+pr+"&statusbill="+$("#statusbill").val();
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
			
		}
	}
	
	
	//物品提交
	$(".goodsNumBut").click(function(){
		if($(this).val()=="提交"){
			if(parseInt($("#SNum").text())<1||$("#SNum").text()==""){
				alert("请填写有效的序号区间");
				return;
			}
			if(parseInt($("#SNum").text())>parseInt($("#number").val())){
				alert("所选序号大于出库数量");
				return;
			}
			if(!confirm("确定提交"))
				return;
			var url=basePath+"ea/quantity/ea_AddProductionOutOfADocument.jspa?start="+$("#start").val()+"&end="+$("#end").val();
			$("#form").attr("target", "hidden").attr("action",url);
			document.form.submit.click();
			token = 2;
			
		}else{
			$("#availableNumber").text("");
			$("#SNum").text("");
			$(".itemNumber").val("");
			$("#goodsNum").jqmHide();
			$("#occlusion2").jqmHide();
		}
	});

	//选择物品
	$("#goodsName").click(function(){
		
		$.ajax({
			url:basePath+"ea/quantity/sajax_ea_ajaxGetItemInformationGoodsInformation.jspa?depotid="+$("#warehouseId").val(),
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.goodlist;
				$(".goods").remove();
				for(var i=0;i<list.length;i++){
					var tr=$("#sampleTr").clone(true).attr("style","").attr("id","goods"+(i+1)).addClass("goods");
					tr.find("td").eq(0).html("<input type='radio' name='goodsRa' class='radio' id='"+list[i].inventoryID+"'/>");
					tr.find("td").eq(1).html("<span>"+list[i].goodsName+"</span>");
					tr.find("td").eq(2).html("<span>"+list[i].weizhi+"</span>");
					tr.find("td").eq(3).html("<span>"+list[i].barcode+"</span>");
					tr.find("td").eq(4).html("<span>"+list[i].goodsCoding+"</span>");
					tr.find("td").eq(5).html("<span>"+list[i].goodsType+"</span>");
					tr.find("td").eq(6).html("<span>"+list[i].invenQuantity+"</span>");
					tr.find("td").eq(7).html("<span>"+list[i].unitPrice+"</span>");
					tr.find("td").eq(8).html("<span>"+list[i].standard+"</span>");
					$("#tbodya").append(tr);
					}
				$("#single").jqmShow();
			},
			error:function(data){
				alert("数据获取失败！");
			}
		});
		
		$("#occlusion2").css("z-index",$("#single").css("z-index")+1);
		$("#goodsjqModel").css("z-index",$("#single").css("z-index")+2);
		$("#occlusion2").jqmShow();
		$("#goodsjqModel").jqmShow();
	});
	
	$(".btn02").click(function(){
		if($(this).val()=="查询"){
			$.ajax({
				url:basePath+"ea/quantity/sajax_ea_ajaxGetItemInformationGoodsInformation.jspa?depotid="+$("#warehouseId").val()+"&parameter="+$("#parameter").val(),
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.goodlist;
					$(".goods").remove();
					for(var i=0;i<list.length;i++){
						var tr=$("#sampleTr").clone(true).attr("style","").attr("id","goods"+(i+1)).addClass("goods");
						tr.find("td").eq(0).html("<input type='radio' name='goodsRa' class='radio' id='"+list[i].inventoryID+"'/>");
						tr.find("td").eq(1).html("<span>"+list[i].goodsName+"</span>");
						tr.find("td").eq(2).html("<span>"+list[i].weizhi+"</span>");
						tr.find("td").eq(3).html("<span>"+list[i].barcode+"</span>");
						tr.find("td").eq(4).html("<span>"+list[i].goodsCoding+"</span>");
						tr.find("td").eq(5).html("<span>"+list[i].goodsType+"</span>");
						tr.find("td").eq(6).html("<span>"+list[i].invenQuantity+"</span>");
						tr.find("td").eq(7).html("<span>"+list[i].unitPrice+"</span>");
						tr.find("td").eq(8).html("<span>"+list[i].standard+"</span>");
						$("#tbodya").append(tr);
						}
					$("#single").jqmShow();
				},
				error:function(data){
					alert("数据获取失败！");
				}
			});
		}else if($(this).val()=="确定"){
			var good=false;
			$(".goods").find(".radio").each(function(){
				if($(this).attr("checked")){
					$("#goodsId").val($(this).attr("id"));
					$("#goodsName").val($(this).parents("tr").find("span").eq(0).text());
					$("#goodsHidden").val($(this).parents("tr").find("span").eq(0).text());
					$("#productNumber").val($(this).parents("tr").find("span").eq(3).text());
					$(".standard").val($(this).parents("tr").find("span").eq(7).text());
					$(".price").val($(this).parents("tr").find("span").eq(6).text());
					$("#number").val("1"); dtnumber=parseInt($(this).parents("tr").find("span").eq(5).text());
					$(".money").val($(this).parents("tr").find("span").eq(6).text());
					good=true;
					$("#number").attr("disabled","");
				}
			});
			if(good){
				$("#goodsjqModel").jqmHide();
				$("#occlusion2").jqmHide();
			}else
				alert("请选择 ！");
			
			
		}else{
			$("#goodsjqModel").jqmHide();
			$("#occlusion2").jqmHide();
		}
		
	});
	
	//获取序号区间中的可用数量
	$(".itemNumber").live("input propertychange",function(){
		var zz=/^\d*$/;
		if(!zz.test($(this).val()))
			$(this).attr("style","width: 100px;height: 15px;background-color: red;").addClass("err");
		else
			$(this).attr("style","width: 100px;height: 15px;").removeClass("err");
		var boobr=true;
		var ss=$("#availableNumber").text().split(" - ");
		if($("#start").val()!=""&&parseInt($("#start").val())<parseInt(ss[0]))
			boobr=false;
		if(($("#end").val()!=""&&parseInt($("#end").val())>parseInt(ss[1]))||parseInt($("#end").val())<parseInt(ss[0]))
			boobr=false;
		if(!$(".err").attr("id")&&boobr){
			$.ajax({
				url:basePath+"ea/quantity/sajax_ea_ajaxAccessNumberInTheNumberOfAvailable.jspa?inventoryID="+$("#goodsId").val()+"&start="+$("#start").val()+"&end="+$("#end").val(),
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					$("#SNum").text(member.number);
				},
				error:function(data){
					alert("获取数据失败！");
				}
			});
		}else
			$("#SNum").text("0");
	});
	
	//出库单保存
	$(".theLibrary").click(function(){
		if($(this).val()=="确定"){
			if($(".error").attr("id")||!$("#goodsHidden").val())
				return;
			
			$.ajax({
				url:basePath+"ea/quantity/sajax_ea_ajaxToObtainTheCurrentItemAvailableSerialNumber.jspa?inventoryID="+$("#goodsId").val()+"&utboundOrderVo.journalnum=123456",
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.list;
					var str=list[0].goodsnumber+" - "+list[list.length-1].goodsnumber;
					$("#availableNumber").text(str);
				},
				error:function(data){
					alert("获取数据失败！");
				}
			});
			$("#occlusion2").css("z-index",$("#single").css("z-index")+1);
			$("#goodsNum").css("z-index",$("#single").css("z-index")+2);
			$("#occlusion2").jqmShow();
			$("#goodsNum").jqmShow();
		}else{
			$(".goodsOutOfTheLibrary").find("input").each(function(){
				$(this).val("");
			});
			$("#single").jqmHide();
		}
	});
	
	
	//根据数量改变总价
	$("#number").live("input propertychange",function(){
		var zz=/^\d*$/;
		if(zz.test($(this).val())&&$(this).val()!=""){
			if($(this).val()<=dtnumber){
				$("#number").attr("style","width:130px;").removeClass("error");
				$("#money").val(parseFloat($("#number").val())*parseFloat($("#price").val()));
			}else{
				alert("所填数量超过物品原有数量！");
				$("#number").attr("style","width:130px;background-color: red;").addClass("error");
			}
		}else{
			$("#number").attr("style","width:130px;background-color: red;").addClass("error");
		}
	});
	
	$("#like").live("click",function(){
		window.location.href=basePath+"ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?statusbill="+$("#statusbill").val()+"&utboundOrderVo.journalnum="+$("#likeJournalnum").val()+"&utboundOrderVo.goodsname="+$("#likeGoodsname").val();
	});
	$(".date").each(function(){
		var str=$(this).text().split(" ");
		$(this).text(str[0]);
	});
	
	//每行的点击事件
	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("name")=="dps")
			id=$(this).find(".radio").attr("id");
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});		

//将多维数组转换成JSon
function arrayToJson(o) {     
    var r = [];     
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";     
    if (typeof o == "object") {     
    if (!o.sort) {     
    for (var i in o)     
    r.push(i + ":" + arrayToJson(o[i]));     
    if (!!document.all && !/^\n?function\s*toString\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {     
    r.push("toString:" + o.toString.toString());     
    }     
    r = "{" + r.join() + "}";     
    } else {     
    for (var i = 0; i < o.length; i++) {     
    r.push(arrayToJson(o[i]));     
    }     
    r = "[" + r.join() + "]";     
    }     
    return r;     
    }     
    return o.toString();     
}   
function re_load(){
	document.location.href = basePath+ "ea/quantity/ea_getTheProductionOfTheHomePageInformation.jspa?statusbill="+$("#statusbill").val();
}