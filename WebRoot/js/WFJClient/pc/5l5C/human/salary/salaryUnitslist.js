$(document).ready(function() {

	getUnitsFirst();



//自动生成
	$(".zadd").click(function () {
		initsUnits()
	})
    //手动添加1
	$(".saddy").click(function () {
		$(".ul .active").removeClass("active");
		document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryUnitsAdd.jsp";
	})
	//手动添加2
	$(".sadde").click(function () {
		var li = $(".ul li.active").length;
		var class1 = $(".ul li.active").attr("class");
		if(li<1||class1.indexOf("first")==-1){

				$(".div-tingyong").show();
				$(".titlep").text("请先选择一级薪资单元");
				return false;

		}

		var id = $(".ul li.active").attr("id");
		var name = $(".ul li.active .cc-div").find("span").text()
		document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryUnitsAdd.jsp?id="+id+"&name="+name;
	})

	//项目添加
	$(".itemadd").click(function () {
		var li = $(".ul li.active").length;
		var id = $(".ul li.active").attr("id");
		if(li<1||id.indexOf("suid")==-1){

			$(".div-tingyong").show();
			$(".titlep").text("请先选择薪资单元");
			return false;

		}

		var id = $(".ul li.active").attr("id");
		var name = $(".ul li.active .cc-div").find("span").text();
		document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryItemAdd.jsp?id="+id+"&name="+name;
	})

	//修改
	$(".edit").click(function () {
		var li = $(".ul li.active").length;
		if(li<1){
			return false;
		}
		var id2 = $(".ul li.active").attr("id");
		var name2 = $(".ul li.active .cc-div").find("span").text();
		var jbie = $(".ul li.active").attr("class");
		var jj = "";
		if(jbie.indexOf("first")!=-1){
			jj  = 1;
		}else{
			jj = 2;
		}
		if(id2.indexOf("suid")!=-1){
			document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/salaryUnitsAdd.jsp?id2="+id2+"&name2="+name2+"&jj="+jj;

		}else {
			document.location.href = basePath + "page/WFJClient/pc/5l5C/human/salary/salaryItemAdd.jsp?id2="+id2+"&name2="+name2;
		}
	})
	//排序
	$(".sort").click(function () {
		document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryUnitsSort.jsp";
	})

	//删除
	$(".del").click(function () {
		var li = $(".ul li.active").length;
		if(li<1){
			return false;
		}

		$(".div-tingyong").show();
		$(".titlep").text("确认删除该结构及其子结构？");
		return false;

	})


	$(".close-tingyong,.close-confirm").click(function(){
		if($(".titlep").text()=="操作成功"){
			document.location.href = basePath+"page/WFJClient/pc/5l5C/human/salary/salaryUnitslist.jsp";

		}else {
			$(".div-tingyong").hide();
			if($(".titlep").text().indexOf("删除")!=-1){
				var id = $(".ul li.active").attr("id");
				$.ajax({
					type: "post",
					url: basePath + "ea/salarylevel/sajax_ea_deleteSalaryStruts.jspa",
					async:false,
					dataType: "json",
					data:{
						id:id
					},
					success: function (data) {
						$(".div-tingyong").show();
						$(".titlep").text("操作成功");

					},
					error: function (data) {

					}


				});
			}

		}
	})


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
		html.push("<li id='"+suID+"' class='first' onclick=\"getUnitsSecond('"+suID+"')\"><div onclick=\"selectedli(this,event)\" class='select-div'><img class='img-011' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'><img class='img-022' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'></div><div class='tb-div'><img class='img-0' src='"+basePath+"images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='"+basePath+"images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>"+unitName+"</span></div></li>");

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

			html.push("<li id='"+stID+"' class='"+parentsID+"'><div onclick=\"selectedli(this,event)\" class='select-div select3'><img class='img-011' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'><img class='img-022' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'></div><div class='cc-div three-div'><span>"+itemName+"</span></div></li>");

		}

		$("#"+parentsID).after(html.join(""));
	} else {

	var html = new Array();
	for (var u = 0; u < unitslist2.length; u++) {
		var obj = unitslist2[u];
		var suID = obj[0];
		var unitName = obj[1];
		html.push("<li id='" + suID + "' class='" + parentsID + "' onclick=\"getItemList('" + suID + "')\"><div onclick=\"selectedli(this,event)\"  class='select-div select2'><img class='img-011' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'><img class='img-022' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'></div><div class='tb-div second-div'><img class='img-0' src='" + basePath + "images/ea/office/contract/selectp/img_08.png'><img class='img-1' src='" + basePath + "images/ea/office/contract/selectp/img_09.png'></div><div class='cc-div'><span>" + unitName + "</span></div></li>");

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
		html.push("<li id='"+stID+"' class='"+suID+"'><div onclick=\"selectedli(this,event)\"  class='select-div select3'><img class='img-011' src='" + basePath + "/images/ea/office/contract/selectp/img_02.png'><img class='img-022' src='" + basePath + "/images/ea/office/contract/selectp/img_03.png'></div><div class='cc-div three-div'><span>"+itemName+"</span></div></li>");

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

function selectedli(obj,event){


		event.stopPropagation();
		event.preventDefault();

		if($(obj).parents("li").is(".active")){
			$(obj).parents("li").removeClass("active");
		}else{

			$(".ul .active").removeClass("active");

			$(obj).parents("li").addClass("active");
		}
		return false;

}