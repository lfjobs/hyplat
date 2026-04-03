$(function(){
	$(".dps").each(function(index){
		if($(this).attr("id")=="0"){
			if(!$(this).find("#increase").text()){
				$(this).find("#balances").text(parseFloat($("#balanceNum").text())-
						parseFloat($(this).find("#reduce").text()));
			}else{
				$(this).find("#balances").text(parseFloat($("#balanceNum").text())+
						parseFloat($(this).find("#increase").text()));
			}			
		}else{
			var ids=parseInt($(this).attr("id"))-1;
			if(!$(this).find("#increase").text()){
				$(this).find("#balances").text(parseFloat($("#"+ids).find("#balances").text())-
						parseFloat($(this).find("#reduce").text()));
			}else{
				$(this).find("#balances").text(parseFloat($("#"+ids).find("#balances").text())+
						parseFloat($(this).find("#increase").text()));
			}
		}
	});

	/*
	 * 导出EXCEL表格
	 */
	$("#but").click(function(){
		var str="";
		$(".dataHead").find("th").each(function(index){
			if(index!=0&&$(this).attr("class")!="operation"){
				if(index==1)
					str+=$(this).text();
				else
					str+=","+$(this).text();
			}
		});
		var obj=new Array();
		$(".dps").each(function(i){
			obj[i]=new Array();
			for(var r=0;r<6;r++){
				obj[i][r]=$(this).find("td").eq(r+1).text();
			}
		});
		var json=arrayToJson(obj);
		window.location.href=basePath+"ea/goldwater/ea_exportExcelTable.jspa?title=金币流水报表&" +
		"str="+str+"&json="+json;
	});
	/*
	 * 查看详情
	 */
	$(".operation").click(function(){
		$.ajax({
			url:basePath+"ea/goldwater/sajax_ea_ajaxGetBillID.jspa?jifenId="+$(this).find("a").attr("id"),
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var wfj=member.wfj;
				$("#cashi").find("option").remove();
				var option="";
				if(wfj.goodId!=null){
					option+="<option value="+wfj.goodId+" id=goodsBill>查看订单物品信息</option>";
				}
				if(wfj.cashiId!=null){
					option+="<option value="+wfj.cashiId+" id=cashiBill>查看金币出入库信息</option>";
				}
				$("#cashi").append(option);
			},
			error:function(data){
				alert("数据获取失败!");
			}
		});
		$("#subjectr").jqmShow();
	});
	
	$(".butt").click(function(){
		if($(this).val()=="查看"){
			if($("#cashi option:selected").attr("id")=="goodsBill")
				open(basePath+"ea/goldwater/ea_getBillDInformation.jspa?goodsId=" + $("#cashi option:selected").val()+"&type=01");
			if($("#cashi option:selected").attr("id")=="cashiBill")
				open(basePath+"ea/goldStock/ea_getGoldPreviewPage.jspa?utboundOrderVo.cashierbillsid="+$("#cashi option:selected").val());
		}else{
			$("#subjectr").jqmHide();
			$("#cashi").find("option").remove();
		}
	});
	/*
	 * 根据条件查询
	 */
	$("#tosearch").click(function(){
		var sdate=$("#sdate").val();
		var edate=$("#edate").val();
		var category=$("#category").val();
		window.location.href=basePath+"ea/goldwater/ea_getHomePageInformation.jspa?type=like&" +
				"sdate="+sdate+"&edate="+edate+"&category="+category+"&userId="+$("#userId").val();
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20// 
	}).jqmAddClose('.close');
});

/*
 * 二维数组转JSon
 */
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