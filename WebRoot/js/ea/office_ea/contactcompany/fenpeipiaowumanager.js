$(document).ready(function() {
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
				title : '登记票务',
				minheight : 80,
				buttons : [{
					name : '登记票务',
					bclass : 'add',
					onpress : action
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
			case '登记票务' :
				if(ticketbusinessid==""){
					alert("请选择一张票据！");
					return;
				}
				var staffnamestr = $("tr#" + ticketbusinessid).find("span#staffName").text();
				if(staffnamestr != ''){
				alert("已分配人员,不能重新分配！");
				return;
				}
				$("#piaowuInformation").empty();
				$t = $("table#hotelSearchTable");
				$p = $("tr#" + ticketbusinessid);
				$("#staffname").attr("value","");
				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).val($(this).text());
						});
				var piaojuhaoma = $p.find("span#billNumber").text();
				if( $p.find("span#ticketType").text()=='飞机'){
					$("#chooseairplan").attr("selected",true);
					$("#train").attr("disabled",true);
				}else{
					$("#choosetrain").attr("selected",true);
					$("#airplan").attr("disabled",true);
				}
				$("<div style='background-color: #D7D7D7;float: left;margin-left:5px;margin-top:5px;'><input type='radio' name='radiobutton' value="+piaojuhaoma +">"+piaojuhaoma+"</radio></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").appendTo($("#piaowuInformation"));
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
				if (ticketbusinessid == "") {
					alert('请选择!');
					return;
				}
				document.piaowuAddForm.reset();
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
				$("#jqModelAdd").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=aa&search="
						+ search;
				numback(url);
				break;
		}
	}
	/*//添加票据号
	$("#addpiaohao").click(function(){
		var shurupiaoju = $(".billNumber").val();
		 if(shurupiaoju== ''){
		 	alert("请输入票据号!")
		 	return;
		 }
		 $("<div style='background-color: #D7D7D7;float: left;margin-left:5px;margin-top:5px;'><input type='radio' name='radiobutton' value="+shurupiaoju +">"+shurupiaoju+"</radio></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").appendTo($("#piaowuInformation"));
		 });
	//删除票据号
	$("#shanchupiaohao").click(function(){
		var i,myObj;
		var myObj=$("input[name='radiobutton']");
		for(i=0;i<myObj.length;i++){
		if(myObj[i].checked)
		break;
		};
		if(i>=myObj.length){
		alert("请选择一个票据号!");
		} else {
			 $("#piaowuInformation").find("input[name=radiobutton]:checked").parent().remove();
		//alert("选择的是第："+(i+1)+"个");
}
		
	});*/
	//选择责任人       
$("#choosestaff").click(function(){
var url = "ea/contactuser/ea_getListContactUser.jspa";
	 getValueForParm('cstaffForm',url);
	
});
function getValueForParm(attachTable,url){ //打开页面
	 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
  	 $("#ifr").attr("src",basePath+url);
  	 $("#jqmWindow2").jqmShow();
}
	
//选择确定
$("#isSubmit").click(function(){
	var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
	if(value1 == ""){
		alert("请选择");
		return;
	}
	var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();
	var staffID = window.frames["ifr"].$('tr#'+value1).find("span#staffID").text();
	$("#jqmWindow2").jqmHide();
	$("#staffname").empty();
	$(".staffname").attr('value',value2);
});
$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
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
	$(".fenpeistaff").dblclick(function(){
				$t = $("table#hotelSearchTable");
				$p = $("tr#" + ticketbusinessid);
				$p.find("span[id]").each(function() {
							$t.find("input#" + this.id).attr("text",$(this).text());
						});
				if( $p.find("span#ticketType").text()=='飞机'){
					$("#chooseairplan").attr("selected",true);
					$("#train").attr("disabled",true);
				}else{
					$("#choosetrain").attr("selected",true);
					$("#airplan").attr("disabled",true);
				}
				$("#jqModelAdd").jqmShow();
		
	});
	//模糊查询
			$("#tosearch").click(function(){
				$("#postSearchForm").attr(
				"action",
				basePath + "ea/piaowuManager/ea_toSearch.jspa?aa=aa&pageNumber="
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
	 //添加分配票据信息
	 $("input.JQuerySubmit").click(function() {
	 		if (notoken){
					return;
				}
				notoken = 1;
				/**/
	 	var billnumberstr = $("tr#" + ticketbusinessid).find("span#billNumber").text();
	 	$("#billNumber2").attr("value",billnumberstr);
	 	var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择分配人员");
			return;
		}
		var staffname = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();
		var staffid = window.frames["ifr"].$('tr#'+value1).find("span#staffID").text();
				$(".put3").trigger("blur");
				if ($("form#piaowuAddForm .error").length) {
					return;
				}
				if($("#piaojutype").find("option:selected").text() == '--请选择--'){
					alert("请选择票据类型！");
					return;
				}
					$("#piaowuAddForm").attr("target","hidden").attr(
									"action",
									basePath
											+ "ea/piaowuManager/ea_savepiaowuInformation.jspa?pageNumber="
											+ pNumber + "&staffID=" + staffid + "&staffName=" + staffname);
					document.piaowuAddForm.submit();
					$("#jqModelAdd").jqmHide();
					token = 2;
			});
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/piaowuManager/ea_getListpiaowu.jspa?aa=aa&pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}