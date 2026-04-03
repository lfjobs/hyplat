$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	var query = "<form name='searchForm' id='searchForm' method='post'>" +
			"<input type='submit' name='submit' style='display: none' />" +
					"<table border='0' id='searchtbl' ><tr><td><strong>会计期间</strong></td><td>&nbsp;&nbsp;&nbsp;</td><td>会计年度：</td><td>" +
					"<input name='fiscalPeriod.year' style='height:18px;width:100px;'type='text' value=''/>" +
					"<input name='search' type='hidden' value='search' />" +
					"<input  type='button' value='  查询  ' id='tosearch' style='margin:2px;'class='input-button'/>" +
					"</td></tr></table></form>";

	$('.fexlist').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
						separator : true
					},{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '保存',
					bclass : 'store',
					onpress : action
						// 当点击调用方法
					},{
						separator : true
					}, {
						name : '放弃',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						},{
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

			case '添加' :

				if(select>1){
					alert("请先保存");
					return;
				}
				if($(".saveAjax").length>1){
					alert("无法进行此操作");
					break;
				}
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"fiscalPeriodmap[" + select + "]." + this.name);

				});


				$("#sa" + select).show();
				select++;
				fpID = "";
			
				break;
			case '修改' :
				if(select>1){
					alert("请先保存");
					return;
				}
				if (fpID == '') {
					alert("请选择！");
					return;
				}
				if(iscanUpdateOrDelete()){
				$p = $("tr#" + fpID);
		
				
	
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"fiscalPeriodmap[" + select + "]." + this.name);
				});

				select++;
				$p.find("span.hid")
						.addClass("model1");
				$p.find("input#endDate").removeClass("model1");


				}else{
					alert("只能修改最大会计年限");
				}
				break;
			case '保存' :
				if(select==1){
					return;
				}
				var re = 0;
				var re1 = 0;
				$("#startDate", $("tr.check")).each(function() {
							if (this.value == "") {
								alert("请输入起始月份");
								$(this).css("background-color", "red");
								re = 1;
							}

						});
				$("#endDate", $("tr.check")).each(function() {
					if (this.value == "") {
						alert("请输入终止月份");
						$(this).css("background-color", "red");
						re1 = 1;
					}

				});
				
				if (re||re1) {
					notoken = 0;
					return;
				}
                var ff= 0;
				$("#year", $("tr.check")).each(function() {
					var year = $(this).text();
					if(year.indexOf("-")!=-1){
						if(year.length>6){
							alert("同一年最多设定10个,您已经超出范围");
							ff=1;
							
						}
					}

				});
				if(ff==1){
					return;
				}
				$('#fispriodForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/fisperiod/ea_saveFiscalPeriod.jspa?pageNumber="
										+ pNumber);
				
				document.fispriodForm.submit.click();
				token = 2;
				break;
			case '放弃' :

				document.location.reload();
				break;


			case '删除' :
				if (fpID == '') {
					alert("请选择！");
					return;
				}

				if(iscanUpdateOrDelete()){
				
				$f = $('#fispriodForm');
				if (confirm("是否删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/fisperiod/ea_delFiscalPeriod.jspa?pageNumber="
											+ pNumber + "&fiscalPeriod.fpID="
											+ fpID);
					document.fispriodForm.submit.click();
					$("tr#" + fpID).remove();
					fpID = "";
					token = 11;

				}
				}else{
					alert("只能删除最大会计年度");
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/fisperiod/ea_getFiscalPeriodList.jspa?search=" + search;
				numback(url);
				break;
			
		}
	}
	// 点击选中
	$(".fexlist tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				fpID = this.id;
			});
	$(".fexlist tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				fpID = this.id;
				action("修改");
			});
	// 根据条件查询
	$("#tosearch").click(function() {
		

		$("#searchForm")
				.attr(
						"action",
						basePath + "ea/fisperiod/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.searchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.reload();
}

//选择开始时间之后调用的
function getend(datestr,obj){
	var $tr = $(obj).parent().parent().parent();
	var endDate = $tr.find("#endDate").val();
	if(endDate!=""){
	if(endDate<datestr){
		$(obj).css("background-color", "red");
		alert("起始时间必须小于终止时间");
		$(obj).val("");
		return;
		
	   }
    }
}
//选择终止时间调用的
function getstat(datestr,obj){
	var $tr = $(obj).parent().parent().parent();
	var startDate = $tr.find("#startDate").val();

	
	if(startDate!=""){
	if(startDate>datestr){
		$(obj).css("background-color", "red");
		alert("终止月份必须大于起始月份");
		$(obj).val("");
		return;
		
	}
	}
	var year = datestr.substring(0,datestr.length-2);
	var newyear = "";
	var oriyear = $tr.find("#oriyear").text();
	
	var url = basePath+"ea/fisperiod/sajax_ea_getMaxYearByYear.jspa?dates="+new Date();
	
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		async:false,
		data:{
			"fiscalPeriod.year":year
		},
		success:function(data){
			var me = eval("("+data+")");
			var result = me.result;
			if(result!=""){
				var index = result.indexOf("-");
				if(index==-1){
					if(oriyear==""){
						newyear = year+"-"+1;
					}else{
						newyear = year;
					}
					
				}else{
					if(oriyear==""){
						//添加状态
						result = result.substring(index+1);
						newyear = year+"-"+(Number(result)+1);
					}else{
						//修改状态
						if(result==oriyear){
							alert(3);
						   newyear = result;
						}else{
							result = result.substring(index+1);
							newyear = year+"-"+(Number(result)+1);
						}
					}
					
				}
				
			}else{
				newyear = year;
			}
			
		},
		error:function(data){
			alert("验证失败");
		}
		
	});
	
	$tr.find("#year").text(newyear);
	$tr.find("#year").next().val(newyear);
	
}
//验证是否可以删除或者修改
function iscanUpdateOrDelete(){
	var bool = true;
	var url = basePath+"ea/fisperiod/sajax_ea_isCanUpdateOrDelete.jspa?dates="+new Date();
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		async:false,
		data:{
			"fiscalPeriod.fpID":fpID
		},
		success:function(data){
			var me = eval("("+data+")");
			var result = me.result;
			if(result=="fail"){
				bool = false;
				
			}
			
		},
		error:function(data){
			alert("验证失败");
		}
		
	});
	
	return bool;
	
}