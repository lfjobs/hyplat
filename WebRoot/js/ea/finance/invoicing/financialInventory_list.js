$(document).ready(function() {
    var query = "财务库存管理";
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '库存盘点',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
				name : '设置上下限值',
				bclass : 'edit',
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
			case '库存盘点' :
				$("#subjectr").jqmShow();
				break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/fininv/ea_getPageHomeData.jspa?pageNumber="+pr;
				}else if(pr==null){
					break;
				}else{
					alert("您输入有误，请重新输入");
				}
				break;
			 case '设置上下限值':
				 if(invId==""){
					 alert("请选择!");
					 return;
				 }
				 $("#limiting").jqmShow();
				 break;
		}
	}
	$(".colse").click(function(){
		$(this).parents(".jqmWindow").jqmHide();
	});
	$("#tijiao").click(function(){
		if($(".error").eq(0).val()||$(".error").eq(0).val()==""){
			$(".error").each(function(){
				$(this).css("background-color","red");
			});
			return;
		}
		$("#form").attr("target", "hidden").attr("action",basePath+"ea/fininv/ea_SetUpperAndLowerLimits.jspa?inventoryID="+invId);
		document.form.submit.click();
		token = 2;
	});
	$(".saveAjax").click(function(){
		var $this=$(this);
		$(".inv").remove();
		if($(this).attr("id")=="dps"){
			$(this).attr("id","");
		}else{
			var url=basePath+"ea/fininv/sajax_ea_getInventoryItemInformation.jspa?goods="+
					$(this).find("td").eq(1).text()+"&warehouse="+$(this).find("td").eq(10).attr("id");
			$.ajax({
				url:url,
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.list;
					if(list.length==0){
						alert("该物品无详细数据！");return;
					}else{
						for(var i=0;i<list.length;i++){
							var tr="<tr id="+list[i].inventoryID+" class='inv'>" +
									"<td><input type='radio' class='rad' name='rad' id='"+list[i].inventoryID+"'></td>" +
									"<td>"+list[i].goodsName+"</td>" +
									"<td>"+list[i].goodsType+"</td>" +
									"<td>"+list[i].invenQuantity+"</td>" +
									"<td>"+list[i].source+"</td>" +
									"<td>"+list[i].goodsCoding+"</td>" +
									"<td>"+list[i].unit+"</td>" +
									"<td>"+list[i].invenOnline+"</td>" +
									"<td>"+list[i].invenUnderline+"</td>" +
									"<td></td>" +
									"<td>"+list[i].warehouseName+"</td>" +
									"</tr>";
							$this.after(tr);
							$this.attr("id","dps");
						}
					}
				},error:function(data){
					alert("数据获取失败！");
				}
			});
		}
	});
	
	$(".dds").each(function(){
		var n=parseInt($(this).find("td").eq(3).attr("name"));
		var s=parseInt($(this).find("td").eq(3).val());
		var str="";
		if(n<=10000){
			if(s<5000)
				$(this).find("font").attr("color","red").text("5000");
			else if(s<8000)
				$(this).find("font").attr("color","yellow").text("5000");
			else
				$(this).find("font").attr("color","green").text("5000");
		}else if(n>10000&&n<=1000000){
			if(s<n*0.3)
				$(this).find("font").attr("color","red").text(parseInt(n*0.3));
			else if(s<n*0.4)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.3));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.3));
		}else if(n>1000000&&n<10000000){
			if(s<n*0.2)
				$(this).find("font").attr("color","red").text(parseInt(n*0.2));
			else if(s<n*0.3)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.2));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.2));
		}else{
			if(s<n*0.15)
				$(this).find("font").attr("color","red").text(parseInt(n*0.15));
			else if(s<n*0.25)
				$(this).find("font").attr("color","yellow").text(parseInt(n*0.15));
			else
				$(this).find("font").attr("color","green").text(parseInt(n*0.15));
		}
	});
	$(".inven").live("input propertychange",function(){
		var zz=/^[0-9]+(.[0-9]{1,2})?$/;
		if(!zz.test($(this).val())){
			 $(this).css("background-color","red").addClass("error");
		}else{
			 $(this).css("background-color","#FFFFFF").removeClass("error");
		}
	});
	
	/*行点击事件
	 */
	$("tr[id]").live("click",function(){
		$(this).find(".rad").attr("checked","checked");
		if($(this).attr("class")=="inv"){
			invId=this.id;
		}
	});
	$("#pandian").click(function(){
		if(!$("#sdate").val()){
			alert("请选择日期");return;
		}
		open(basePath+"ea/fininv/ea_getStockingData.jspa?datte="+$("#sdate").val());
	});                                                                                                                                     
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20// 
	}).jqmAddClose('.close');
});


function re_load() {
	window.location.href=basePath+"ea/fininv/ea_getPageHomeData.jspa";
}
