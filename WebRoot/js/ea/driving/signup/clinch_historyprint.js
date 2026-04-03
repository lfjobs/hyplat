// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;

$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : "学员证历史打印",
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '返回':
				document.location.href=basePath+"/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&companyGroupLogo="+companyGroupLogo;
				break;	
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/clinch/ea_getListContactUser.jspa?search="
						+ search+"&module_Identifier="+module_Identifier+"&view_Identifier="+view_Identifier+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule+"&typeprint=history&companyGroupLogo="+companyGroupLogo;
				numback(url);
				break;
			

		}
	}
	$("tr[id]").click(function(){
		relationID = $(this).attr("id");
		staffID=$.trim($(this).find("span#staffID").text());
		$(this).find("input#relationID").attr("checked","checked");
	})
	$(".JQueryflexme tr[id]").dblclick(function(){
		relationID = $(this).attr("id");
		var staffID=$.trim($(this).find("span#staffID").text());
		var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule;
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	})
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",basePath+ "ea/clinch/ea_toSearch.jspa?pageNumber="+pNumber+"&typeprint=history");
		document.getElementById("SearchForm").submit.click();
	})
	
	
	
		

	$("tr[id]").click(function() {
				relationID = $(this).attr("id");
				staffID = $.trim($(this).find("span#staffID").text());
				$(this).find("input#relationID").attr("checked", "checked");
			});
	$("tr[id]").dblclick(function() {
				$(this).trigger("click");
				action("查看");
			});


	// 手动输入点击
	$(".manual").click(function() {
		$(this).hide();
		$(".scan").show();
		$("#searchGood").show();
			// $("#recordCode", $("table#searchgood")).removeAttr("readonly");

		});



	
	
	
	
	
});


//打印学员证正面
function printCard(relationID,staffID,companyID){
	
	window.open(basePath
						+ "ea/studentManager/card.jspa?staffID="
						+ encodeURI(staffID)+"&parameter="+encodeURI(companyID));
						

						
	var count = $("tr#"+relationID).find("span#printCount").text();
	
	$("tr#"+relationID).find("span#printCount").text(Number(count)+1);				
						
	
	
	
}















/*
    
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactuser/ea_getListContactUser.jspa?pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}*/




