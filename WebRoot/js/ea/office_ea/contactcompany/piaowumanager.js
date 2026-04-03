$(document).ready(function() {
	var checkText1=$("#piaojutype").find("option:selected").text()
	if(checkText1 == '--请选择--'){
			$("#airplan").attr("disabled",true);
	 		$("#train").attr("disabled",true);
	}
	
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '票务管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				var str = 0;
				$(".tishiinformation").empty();
				$("#addpiaohao").text("添加");
				$("#shanchupiaohao").css("display","block");
				$("#jqModelAdd").jqmShow();
				document.piaowuAddForm.reset();
				break;
			case '修改' :
				if (ticketbusinessid == "") {
					alert('请选择!');
					return;
				}
				document.piaowuAddForm.reset();
				$(".tishiinformation").empty();
				$("#addtitle").append("<lable class='tishiinformation' style='color:red;'>*票据号只能填写一个</lable>");
				$t = $("table#hotelSearchTable");
				$p = $("tr#" + ticketbusinessid);
				if( $p.find("span#ticketType").text()=='飞机'){
					$("#chooseairplan").attr("selected",true);
					$("#train").attr("disabled",true);
				}else{
					$("#choosetrain").attr("selected",true);
					$("#airplan").attr("disabled",true);
				}
				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).attr("value",$(this).text());
						});
				$("#addpiaohao").text("修改");
				$("#shanchupiaohao").css("display","none");
				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if (ticketbusinessid == '') {
					alert('请选择');
					return;
				}
				if (ticketbusinessid.substring(0, 2) == "sa") {
					if (confirm("是否移除？")) {
						$("#" + ticketbusinessid).remove();
						ticketbusinessid = "";
					}
					return;
				}
				$f = $('#piaojuInformationForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/piaowuManager/ea_deletepiaojuInformation.jspa?pageNumber="
											+ pNumber + "&piaowumanager.ticketbusinessid="
											+ ticketbusinessid);
					document.piaojuInformationForm.submit.click();
					token = 2;
					ticketbusinessid = "";
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/contactConnection/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz&search="
						+ search;
				numback(url);
				break;
		}
	}
	//添加票据号
	$("#addpiaohao").click(function(){
		var shurupiaoju = $.trim($(".billNumber").val());
		 if(shurupiaoju== ''){
		 	alert("请输入票据号!")
		 	return;
		 }if($("#addpiaohao").text()=="修改"){
		 $("#piaowuInformation").empty();
		 $("<div style='background-color: #D7D7D7;float: left;margin-left:5px;margin-top:5px;'><input type='radio' name='radiobutton' value="+shurupiaoju +">"+shurupiaoju+"</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").appendTo($("#piaowuInformation"));
		 }else{
		 	var b=false;
		 		$("#piaowuInformation").find("input").each(function(){
			 		if($(this).val()==shurupiaoju){
			 			b=true;
			 		};
		 		})
		 		if(b){
		 			 alert("票据号不可重复");
		 		}else{
		 			$("<div style='background-color: #D7D7D7;float: left;margin-left:5px;margin-top:5px;'><input type='radio' name='radiobutton' value="+shurupiaoju+">"+shurupiaoju+"</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").appendTo($("#piaowuInformation"));
		 		}
		 }
		 });
	//删除票据号
	$("#shanchupiaohao").click(function(){
		var i,myObj;
		var myObj=$("input[name='radiobutton']");
		for(i=0;i<myObj.length;i++){
		if(myObj[i].checked)break;
		};
		if(i>=myObj.length){
		alert("请选择一个票据号!");
		} else {
			 $("#piaowuInformation").find("input[name=radiobutton]:checked").parent().remove();
		//alert("选择的是第："+(i+1)+"个");
}
		
	});
	$(".JQueryflexme tr[id]").click(function() {
				ticketbusinessid = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + ticketbusinessid);
				}
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	//模糊查询
			$("#tosearch").click(function(){
				$("#postSearchForm").attr(
				"action",
				basePath + "ea/piaowuManager/ea_toSearch.jspa?aa=zz&pageNumber="
			+ pNumber);
		document.postSearchForm.submit.click();
			});
	//$("#piaojutype")
	 $("#piaojutype").change(function(){
	 	var checkText=$("#piaojutype").find("option:selected").text()
	 	if(checkText == '火车'){
	 		$("#airplan").attr("disabled",true);
	 		$("#train").attr("disabled",false);
	 	}if(checkText == '飞机'){
	 		$("#airplan").attr("disabled",false);
	 		$("#train").attr("disabled",true);
	 	}if(checkText == '--请选择--'){
	 		$("#airplan").attr("disabled",true);
	 		$("#train").attr("disabled",true);
	 	}
	 });
	 //添加保存信息
	 $("input.JQuerySubmit").click(function() {
	 	var str = "";
	 	$("div#piaowuInformation").find("input[type=radio]").each(function(){
		 	if(str == ""){
		 		str += $(this).attr("value");
		 	}else{
		 		str += "-" + $(this).attr("value");
		 	}	
	 	});
	 	$("#billNumber2").attr("value",str);
				$(".put3").trigger("blur");
				if ($("form#piaowuAddForm .error").length) {
					return;
				}
				if($("#piaojutype").find("option:selected").text() == '--请选择--'){
					alert("请选择票据类型！");
					return;
				}
				if (ticketbusinessid == "") {
				var piaojuhaoString =  $("#piaowuInformation").find("div[type=radio]").val();
					$("#piaowuAddForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/piaowuManager/ea_savepiaowuInformation.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.piaowuAddForm.submit.click();
					document.piaowuAddForm.reset();
					token = 1;
					return;
				}
				$("#piaowuAddForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/piaowuManager/ea_savepiaowuInformation.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.piaowuAddForm.submit.click();
					document.piaowuAddForm.reset();
					token = 2;
					return;
			});
});


function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz&pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}