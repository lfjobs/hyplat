// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;

$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	var searchFormHtml=$("div#jqModelSearch").html();	
	$("div#jqModelSearch").remove();
 $('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 395,
				width : 'auto',
				minwidth : 30,
				title : searchFormHtml,
				minheight : 80,
				buttons : [{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '打印培训记录登记表',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/trainingregistration/ea_getListOfViewTrainingRegistration.jspa?search="
						+ search;
				numback(url);
				break;
			case '打印培训记录登记表' :
				if (studentid == "") {
					alert("请选择");
					return;
				}
				window.open(basePath
						+ "/ea/trainingregistration/ea_toRegistrationOftraining.jspa?cstaff.staffID="
						+ encodeURI(studentid)+"&print=print",'','fullscreen=1,toolbar=0,directories=0,status=0,menubar=0');
				break;		
		}
	}
	$("tr[id]").click(function(){
		studentid = $(this).attr("id");
		$(this).find("input#studentid").attr("checked","checked");
	});
	$(".JQueryflexme tr[id]").dblclick(function(){
		studentid = $(this).attr("id");
		var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+studentid;
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	});
	$("#tosearch").click(function(){
		$(".ckTextLength", $("table#cataffSearchTable")).trigger("blur");
		if ($("#cataffSearchTable .error").length) {
			alert("请按提示填写查询条件");
			return;
		}
		$("#SearchForm").attr("action",basePath+ "/ea/trainingregistration/ea_toSearchOfViewTrainingRegistration.jspa?pageNumber="+pNumber+"&companyGroupLogo="+companyGroupLogo);
		document.getElementById("SearchForm").submit.click();
	});
	
});


