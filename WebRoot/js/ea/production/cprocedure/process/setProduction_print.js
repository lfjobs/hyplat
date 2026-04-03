window.onload=function(){
	$(".tableOuter").css("width",$(".tableOuter").width()+"px");
	$(".tableInner").css("width",($(".tableOuter").width()+17)+"px");
	$(".tableOuter").find("table").css("width",$(".tableOuter").width()+"px");
};
$(function(){
	$("input").attr("readonly","readonly");
	if($("#productID").val()!=""){
		$.ajax({
			url:basePath+"ea/setpro/sajax_ea_productMix.jspa?productionAmount.productID="+$("#productID").val(),
			type : "post",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var tr="<tr class='packaging'><td width='320px'>";
					 console.log(list[i][5]);
					for(r=0;r<parseInt(list[i][5]==null?'0':list[i][5])+1;r++){
						tr+="&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					tr+="--"+list[i][0]+"</td><td width='200px'>"+list[i][4]
					+"</td><td width='170px'>"+list[i][3]+"</td><td width='169px'>"+(parseInt(list[i][3])*amount)+"</td></tr>";
					$("#tableBody").find("tbody").append(tr);
				}
			},
			errot:function(data){
				alert("数据获取失败");
			}
		});
	}
	
});