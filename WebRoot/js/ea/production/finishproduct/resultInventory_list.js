$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
    var query = "<form name='postSearchForm' id='postSearchForm' method='post'>"
			+ "库存管理&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">物品类别：</span><input type='text' style=\"width: 100px\" name='inventoryParam.goodsType'/>&nbsp;&nbsp;"
			+ "物品名称：<input name=\"inventoryParam.goodsName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";

    		query+="<a style='position: absolute;right: 10px;top:6px;text-decoration:none;' id='"+(type=="01"?"notProduct":"finance")+"' href='javascript:;'>"+(type=="01"?"查看未出库产品":"查看已出库产品")+"</a>";
    
    if(fiveClearName=="加班"&&fiveClear=="ProductPackaging201608123Q39CC2A6U0000003047"){
    		if(type=="01"){
    			$('.address').flexigrid({
    				height : 350,
    				width : 'auto',
    				minwidth : 30,
    				title : query,
    				minheight : 80,
    				buttons : [
    				           {name : '修改',bclass : 'mysearch',onpress : action}, {separator : true},
    				           /*{name : '删除',bclass : 'mysearch',onpress : action}, {separator : true},*/
    				           {name : '查看审核',bclass : 'mysearch',onpress : action}, {separator : true},
    				           {name : '确认入库',bclass : 'mysearch',onpress : action}, {separator : true}
    				           /*{name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}*/]
        	  });
    		}else{
    			
    			$('.address').flexigrid({
    				height : 350,
    				width : 'auto',
    				minwidth : 30,
    				title : query,
    				minheight : 80,
    				buttons : [{name : '添加',bclass : 'mysearch',onpress : action}, {separator : true}
    				          /* {name : '查看',bclass : 'mysearch',onpress : action}, {separator : true},*/
    				       /*    {name : '打印预览',bclass : 'mysearch',onpress : action}, {separator : true},*/
    				           /*{name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}*/]
    			});
    		}
    }else if(fiveClearName=="处罚"&&fiveClear=="ProductPackaging201608123Q39CC2A6U0000003059"){
    	if(type=="01"){
			$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [
				           {name : '修改',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '删除',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '查看审核',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}]
			});
		}else{
			$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{name : '添加',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '查看',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '打印预览',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}]
			});
		}
    }else if(fiveClearName=="未拨现金申请"&&fiveClear=="ProductPackaging20160817DZZKKJD29J0000005828"){
    	if(type=="01"){
			$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [
				           {name : '修改',bclass : 'edit',onpress : action}, {separator : true},
				           /*{name : '删除',bclass : 'mysearch',onpress : action}, {separator : true},*/
				           {name : '查看审核',bclass : 'mysearch',onpress : action}, {separator : true},
				           {name : '确认入库',bclass : 'mysearch',onpress : action}, {separator : true}
				           /*{name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}*/]
    	  });
		}else{
			
			$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{name : '填写',bclass : 'add',onpress : action}, {separator : true}
				          /* {name : '查看',bclass : 'mysearch',onpress : action}, {separator : true},*/
				       /*    {name : '打印预览',bclass : 'mysearch',onpress : action}, {separator : true},*/
				           /*{name : '设置每页显示条数',bclass : 'mysearch',onpress : action}, {separator : true}*/]
			});
		}
    }else{
    	$('.address').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : "该功能模块尚未完成",
			minheight : 80,
			buttons : [{name : '无',bclass : 'mysearch',onpress : action}, {separator : true}]
	  });
    }
	function action(com, grid) {
	    if(fiveClearName=="加班"&&fiveClear=="ProductPackaging201608123Q39CC2A6U0000003047"){
	    	switch (com) {
			case '添加':
				open(basePath+"ea/resultInv/ea_getResultsAddPages.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&invID="+invID);
				break;
			case '修改':
				if(invID==""){
					alert("请选择");
					return;
				}
				open(basePath+"ea/resultInv/ea_seeInformation.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&invID="+invID);
				break;
			case '删除':
				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status = $.trim($p.find("span#status").text());
				if (status != "00") {
					alert("只能删除草稿状态!");
					return;
				}
				if (confirm("确认要删除吗")) {
					var value = $.trim($p.find("span#key").text());
					$("<input>", {
						type : "hidden",
						value : value,
						name : "dtMyovertime.key"
					}).appendTo($("form#postSearchForm"));
					$("#postSearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/overtime/ea_deleteByOvertime.jspa?");
					document.postSearchForm.submit.click();
					token = 2;
				}
				break;
			case '查看':
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath
						+ "ea/overtime/ea_getDetailsByOvertime.jspa?dtMyovertime.id="
						+ id;
				open(url);
				break;
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '导出':
				url = basePath
						+ "ea/overtime/ea_exportByOvertime.jspa?search="
						+ search+"&type="+type;
				open(url);
				break;
			case '打印预览':
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath
						+ "ea/overtime/ea_toPrintPreviewByOvertime.jspa?dtMyovertime.id="
						+ id;
				open(url);
				break;
			case '查看审核':
				open(basePath+"ea/resultInv/ea_getResultInvPageData.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&cashierBillsID="+invID);
				break;
			case '确认入库':
				if(invID==""){
					alert("请选择");
					return;
				}
				if($("#"+invID).find("td").eq(11).find("span").text()!="审核中"){
					alert("该单据已入库");
					return;
				}
				$("#addressForm").attr("target", "hidden").attr("action",basePath
								+"ea/resultInv/ea_itemConfirmation.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&cashierBillsID="+invID);
				document.addressForm.submit.click();
				token = 2;
				break;
			case '设置每页显示条数':
				var url = basePath
						+ "ea/overtime/ea_getListByOvertime.jspa?search="
						+ search+"&type="+type;
				numback(url);
				break;
			}
	    }
	    
	    
	    if(fiveClearName=="处罚"&&fiveClear=="ProductPackaging201608123Q39CC2A6U0000003059"){
	    	switch (com) {
			case '添加':
				open(basePath+"ea/resultInv/ea_getResultsAddPages.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&invID="+invID);
				break;
			case '修改':
				open(basePath+"ea/resultInv/ea_seeInformation.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&invID="+invID);
				break;
			case '删除':
				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status = $.trim($p.find("span#status").text());
				if (status != "00") {
					alert("只能删除草稿状态!");
					return;
				}
				if (confirm("确认要删除吗")) {
					var value = $.trim($p.find("span#key").text());
					$("<input>", {
						type : "hidden",
						value : value,
						name : "dtMyovertime.key"
					}).appendTo($("form#postSearchForm"));
					$("#postSearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/overtime/ea_deleteByOvertime.jspa?");
					document.postSearchForm.submit.click();
					token = 2;
				}
				break;
			case '查看':
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath
						+ "ea/overtime/ea_getDetailsByOvertime.jspa?dtMyovertime.id="
						+ id;
				open(url);
				break;
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '导出':
				url = basePath
						+ "ea/overtime/ea_exportByOvertime.jspa?search="
						+ search+"&type="+type;
				open(url);
				break;
			case '打印预览':
				if (id == "") {
					alert("请选择");
					return;
				}
				url = basePath
						+ "ea/overtime/ea_toPrintPreviewByOvertime.jspa?dtMyovertime.id="
						+ id;
				open(url);
				break;
			case '设置每页显示条数':
				var url = basePath
						+ "ea/overtime/ea_getListByOvertime.jspa?search="
						+ search+"&type="+type;
				numback(url);
				break;
			}
	    }
	}

});

$("#finance").live("click",function(){
	window.location.href=basePath+"ea/resultInv/ea_outgoingItemList.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName+"&type=01";
});
$("#notProduct").live("click",function(){
	window.location.href=basePath+"ea/resultInv/ea_getResultInvList.jspa?category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName;
})

$("tr[id]").live("click",function(){
	invID=this.id;
	$(this).find(".invRadio").attr("checked",true);
});
function getSerialNumber() {
	var url = basePath + "ea/overtime/sajax_ea_getSerialNumber.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		data : {
			"serialnumber" : "009"
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			vouch = member.BillID;
			$("input#serialnumber", $("form#overtimeForm")).val(vouch);
		},
		error : function cbf(data) {
			alert("数据获取失败!");
		}
	});
}

function re_load() {
		document.location.href = document.location.href;
}
