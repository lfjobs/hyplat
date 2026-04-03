$(function(){
	ajaxGetProductMix("utboundOrderVo.goodsbillsid="+goodsBillsID);
	$(".disImg").live("click",function(){
		cls="disImg";
		if($(this).parent().parent().attr("name")=="block"){
			$(this).parent().parent().attr("name","none");
			none($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/add.png");
		}else{
			$(this).parent().parent().attr("name","block");
			block($(this).parent().parent().attr("id"));
			$(this).attr("src",basePath+"images/mobile/close.png");
		}
	});
	$("#selButton").click(function(){
			ajaxGetProductMix("utboundOrderVo.goodsbillsid="+goodsBillsID+"&utboundOrderVo.ppId="+$("#sel option:selected").val()+"&utboundOrderVo.goodsname="+$("#sel option:selected").text());
	});
	$("#tableR").find("tr").find("a").live("click",function(){
		open(basePath+"ea/assembly/ea_getProductPrint.jspa?utboundOrderVo.goodsbillsid="+goodsBillsID+"&utboundOrderVo.ppId="+$(this).parents("tr").attr("id")+"&utboundOrderVo.goodsname="+$(this).attr("name"))+"&fiveClear="+fiveClear;
	});
});

function ajaxGetProductMix(str){
	$("#tableR").find("tbody").html("");
	$.ajax({
		url:basePath+"ea/assembly/sajax_ea_ajaxGetProductMix.jspa?"+str,
		type : "post",
		async : false,
		dataType : "json",
		success : function (data) {
			var member = eval("(" + data + ")");
			var list=member.list;
			var goods=member.goods;
			for(var i=0;i<list.length;i++){
				var ss=(i==0?"style='border-top:0px;'":"");	
				var tr="<tr id="+list[i][0]+" class="+list[i][1]+" name='block'><td width='300px;' "+ss+">";
				for(var r=0;r<list[i][2];r++){
					tr+="&nbsp;&nbsp;";
				}
				if(i!=list.length-1&&list[i+1][2]>list[i][2]){
					tr+="<img src='"+basePath+"images/mobile/close.png' style='width:10px;height:10px;' class='disImg'>";
				}else{
					tr+="&nbsp;&nbsp;&nbsp;";
				}
				var operation="";
				if(i!=0||list.length<2){
					operation="<a href='javascript:;' name="+list[i][3]+">查看</a>";
				}
				tr+=list[i][3]+"</td><td width='111px;' "+ss+">"+(list[i][4]==null?"":list[i][4])+"</td><td width='111px;' "
				+ss+">"+(list[i][5]==null?"0":list[i][5])+"</td><td width='111px;' "+ss+">"
				+(list[i][4]==null?"0":list[i][4]*quantity)+"</td><td width='111px;' "+ss+">"+(list[i][6]==null?"":list[i][6])
				+"</td><td width='179px;' "+ss+">"
				+staffName+"</td><td "+ss+" width='70px;' align='center'>"+operation+"</td></tr>";
				$("#tableR").find("tbody").append(tr);
				$("#sel").append("<option value="+list[i][0]+">"+list[i][3]+"</option>");
		}
		},
		error:function(data){
			alert("数据获取失败");
		}
	});
	
}
function none(str){
	var strs=str.split(",");
	if(strs==""){
		return;
	}
	var s="";
	for(var i=0;i<strs.length;i++){
		$("."+strs[i]).each(function(){
			if(s=="")
				s+=$(this).attr("id");
			else
				s+=","+$(this).attr("id");
			$(this).css("display","none");
		});
	}
	none(s);
}
function block(str){
	var strs=str.split(",");
	if(strs==""){
		return;
	}
	var s="";
	for(var i=0;i<strs.length;i++){
		$("."+strs[i]).each(function(){
			if(s=="")
				s+=$(this).attr("id");
			else
				s+=","+$(this).attr("id");
			$(this).attr("name","block").css("display","");
			$(this).find("img").attr("src",basePath+"images/mobile/close.png");
		});
	}
	block(s);
}
function re_load(){
	window.location.href=window.location.href;
}