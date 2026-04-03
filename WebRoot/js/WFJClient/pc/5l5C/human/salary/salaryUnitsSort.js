$(document).ready(function() {

	getUnitsFirst();



	
});

function initsUnits() {

	var unitslist=new Array();
	$.ajax({
		type: "post",
		url: basePath + "ea/salarylevel/sajax_ea_initPreSalary.jspa",
		async:false,
		dataType: "json",
		success: function (data) {
			var member = eval("("+data+")");
			var result = member.result;
			if(result=="repeat"){

				//系统生成只能生成一次
			}

               alert("系统生成完毕");
		},
		error: function (data) {

		}


	});
	return  unitslist;

}

function getUnitsFirst(){

	var unitslist1 = getUnits("");
	var html = new Array();
	for(var u=0;u<unitslist1.length;u++){
		var obj = unitslist1[u];
		var suID = obj[0];
		var unitName = obj[1];
		if(u==0){
			html.push("<li id='" + suID + "' class='first' onclick=\"getUnitsSecond('" + suID + "')\"><div class='tb-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\" style='visibility: hidden;'>上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

		}else if(u==unitslist1.length-1){
			html.push("<li id='" + suID + "' class='first' onclick=\"getUnitsSecond('" + suID + "')\"><div class='tb-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\" style='visibility: hidden;'>下移</span></div></li>");

		}else {
			html.push("<li id='" + suID + "' class='first' onclick=\"getUnitsSecond('" + suID + "')\"><div class='tb-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");
		}
	}

	$(".sec-list ul").html(html.join(""));
}



function getUnitsSecond(parentsID) {

	if ($("#" + parentsID).find(".img-0").is(':hidden')) {//说明是展开的
		//需要关闭
		$("." + parentsID).hide();
		$("#" + parentsID).find(".img-1").hide();
		$("#" + parentsID).find(".img-0").show();


		$("." + parentsID).each(function () {

			$("." + $(this).attr("id")).hide();


		});
		$("." + parentsID).find(".img-1").hide();
		$("." + parentsID).find(".img-0").show();
		return false;
	} else {
		$("#" + parentsID).find(".img-0").hide();
		$("#" + parentsID).find(".img-1").show();
		if ($("." + parentsID).length > 0) {
			$("." + parentsID).show();
			return false;
		}

	}
	var unitslist2 = getUnits(parentsID);

	if (unitslist2.length == 0) {

		//如果没有二级单元，直接查询项目点
		var itemslist = getItems(parentsID);
		var html = new Array();
		for(var u=0;u<itemslist.length;u++){
			var obj=itemslist[u];
			var stID=obj[0];
			var itemName=obj[1];
			if(itemslist.length==1){
				html.push("<li id='"+stID+"' class='"+parentsID+"'><div class='cc-div three-div'><span>"+itemName+"</span></div><div class='opr' style='visibility: hidden;'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

				break;
			}

			if(u==0){
				html.push("<li id='" + stID + "' class='" + parentsID + "'><div class='cc-div three-div'><span>" + itemName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\" style='visibility: hidden;'>上移</span>&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

			}else if(u==itemslist.length-1){
				html.push("<li id='" + stID + "' class='" + parentsID + "'><div class='cc-div three-div'><span>" + itemName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\" style='visibility: hidden;'>下移</span></div></li>");

			}else {
				html.push("<li id='" + stID + "' class='" + parentsID + "'><div class='cc-div three-div'><span>" + itemName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");
			}
		}

		$("#"+parentsID).after(html.join(""));
	} else {

	var html = new Array();
	for (var u = 0; u < unitslist2.length; u++) {
		var obj = unitslist2[u];
		var suID = obj[0];
		var unitName = obj[1];
		if(unitslist2.length==1){
			html.push("<li id='" + suID + "' class='" + parentsID + "' onclick=\"getItemList('" + suID + "')\"><div class='tb-div second-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div></li>");

			break;
		}

		if(u==0){
			html.push("<li id='" + suID + "' class='" + parentsID + "' onclick=\"getItemList('" + suID + "')\"><div class='tb-div second-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\" style='visibility: hidden;'>上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

		}else if(u==unitslist2.length-1){
			html.push("<li id='" + suID + "' class='" + parentsID + "' onclick=\"getItemList('" + suID + "')\"><div class='tb-div second-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\" style='visibility: hidden;'>下移</span></div></li>");

		}else{
			html.push("<li id='" + suID + "' class='" + parentsID + "' onclick=\"getItemList('" + suID + "')\"><div class='tb-div second-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div><div class='opr'><span class='sy' onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

		}

	}

	$("#" + parentsID).after(html.join(""));
  }
}




function getItemList(suID){
	if($("#"+suID).find(".img-0").is(':hidden')){//说明是展开的
		//需要关闭
		$("."+suID).hide();
		$("#"+suID).find(".img-1").hide();
		$("#"+suID).find(".img-0").show();
		return false;
	}else{
		$("#"+suID).find(".img-0").hide();
		$("#"+suID).find(".img-1").show();
		if($("."+suID).length>0){
			$("."+suID).show();
			return false;
		}

	}

	var itemslist = getItems(suID);
	var html = new Array();
	for(var u=0;u<itemslist.length;u++){
		var obj=itemslist[u];
		var stID=obj[0];
		var itemName=obj[1];
		if(u==0){
			html.push("<li id='"+stID+"' class='"+suID+"'><div class='cc-div three-div'><span>"+itemName+"</span></div><div class='opr'><span class='sy'  onclick=\"sy(this,event)\" style='visibility: hidden;'>上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

		}else if(u==itemslist.length-1){
			html.push("<li id='"+stID+"' class='"+suID+"'><div class='cc-div three-div'><span>"+itemName+"</span></div><div class='opr'><span class='sy'  onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\" style='visibility: hidden;'>下移</span></div></li>");

		}else{
			html.push("<li id='"+stID+"' class='"+suID+"'><div class='cc-div three-div'><span>"+itemName+"</span></div><div class='opr'><span class='sy'  onclick=\"sy(this,event)\">上移</span>&nbsp;&nbsp;&nbsp;<span class='xy' onclick=\"xy(this,event)\">下移</span></div></li>");

		}

	}

	$("#"+suID).after(html.join(""));
}

//获取单元
function getUnits(parentID) {

	var unitslist=new Array();
	$.ajax({
		type: "post",
		url: basePath + "ea/salarylevel/sajax_ea_getSalaryUnitsList.jspa",
		data: {

			suID:parentID
		},
		async:false,
		dataType: "json",
		success: function (data) {
			var member = eval("("+data+")");
			unitslist = member.unitslist;


		},
		error: function (data) {

		}


	});
	return  unitslist;

}

//获取项目
function getItems(suID) {
	var itemlist = new Array();

	$.ajax({
		type: "post",
		url: basePath + "ea/salarylevel/sajax_ea_getSalaryItemList.jspa",
		data: {

			suID:suID
		},
		dataType: "json",
		async:false,
		success: function (data) {
			var member = eval("("+data+")");
			itemlist = member.itemlist;
		},
		error: function (data) {

		}


	});
	return itemlist;
}

//上移
function sy(obj,event){

	event.stopPropagation();
	event.preventDefault();
	var parent = $(obj).parents("li");

	 parent.find(".img-0").show();
	parent.find(".img-1").hide();
	var id = parent.attr("id");
	$("."+id).each(function(){

         $("."+$(this).attr("id")).remove();

	});
	$("."+id).remove();



	var class1 = $(parent).attr("class");
	var prev = parent.prevAll("."+class1).first();


	parent.insertBefore(prev);

	$(parent).find(".xy").css("visibility","visible");

	var pprev =$(parent).prevAll("."+class1);
	if(pprev.length>0){
		$(parent).find(".sy").css("visibility","visible");

	}else{
		$(parent).find(".sy").css("visibility","hidden");

	}
	$(prev).find(".sy").css("visibility","visible");

	var nnext =$(prev).nextAll("."+class1);
	if(nnext.length>0){
		$(prev).find(".xy").css("visibility","visible");

	}else{
		$(prev).find(".xy").css("visibility","hidden");

	}


	changeSort(id,$(prev).attr("id"));
}
//下移
function xy(obj,event){

	event.stopPropagation();
	event.preventDefault();
	var parent = $(obj).parents("li");

	parent.find(".img-0").show();
	parent.find(".img-1").hide();
	var id = parent.attr("id");
	$("."+id).each(function(){

		$("."+$(this).attr("id")).remove();

	});
	$("."+id).remove();



	var class1 = $(parent).attr("class");
	var next = parent.nextAll("."+class1).first();
	var id1 = next.attr("id");
	$("."+id1).each(function(){

		$("."+$(this).attr("id")).remove();

	});

	$("."+id1).remove();


	parent.insertAfter(next);


	$(next).find(".xy").css("visibility","visible");
	var pprev =$(next).prevAll("."+class1);
	if(pprev.length>0){
		$(next).find(".sy").css("visibility","visible");

	}else{
		$(next).find(".sy").css("visibility","hidden");

	}

	$(parent).find(".sy").css("visibility","visible");
	var nnext =$(parent).nextAll("."+class1);
	if(nnext.length>0){
		$(parent).find(".xy").css("visibility","visible");

	}else{
		$(parent).find(".xy").css("visibility","hidden");

	}

	changeSort(id,id1);
}
//交换位置
function changeSort(id1,id2){

	$.ajax({
		type: "post",
		url: basePath + "ea/salarylevel/sajax_ea_changeSort.jspa",
		data: {

			id1:id1,
			id2:id2
		},
		dataType: "json",
		async:false,
		success: function (data) {


		},
		error: function (data) {

		}



	})

}