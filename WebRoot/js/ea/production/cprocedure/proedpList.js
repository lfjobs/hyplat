$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
	var queryArr=new Array();
	
	queryArr.push("<form method='post' name='queryform' id='queryform' >");
	queryArr.push("<input type='submit' style='display:none;' name='submit' />");
	queryArr.push("<input type='hidden' name='search' value='search'/>");
	queryArr.push("设备分配管理&nbsp;&nbsp;&nbsp;");
	queryArr.push("产品编号:<input name='prodctSn' style='width:90px;height:18px;'  value='"+prodctSn+"'/>&nbsp;");
	queryArr.push("产品名称:<input name='goodsName' style='width:90px;height:18px;' value='"+goodsName+"'/>");
	queryArr.push("<input type='submit' value='  查询  ' id='querybtn' style=\"margin:0px;margin-left:5px;\" class='input-button'/>");
	queryArr.push("<input type='hidden' id='pidf' name='pedId'/>");
	queryArr.push("</form>");
	//$("form#queryform").find("input#querybtn").click();
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title :queryArr.join(""),
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
				 {
					name : '打印',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
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
				window.open(basePath+"ea/proedpdist/ea_toPage.jspa?fiveClear="+fiveClear);
				break;
			case '修改' :
			  if (pedId == "") {
					alert('请选择!');
					return;
				}
			 
			   window.open(basePath+"ea/proedpdist/ea_toPage.jspa?pedId="+pedId+"&fiveClear="+fiveClear);
				break;
			case '删除' :
				if (pedId == "") {
					alert('请选择！');
					return
				}
				
				$("#pidf").val(pedId);
				
				if (confirm("确定删除？")) {
					$("#queryform")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/proedpdist/ea_deleteProEdp.jspa?pageNumber="
											+ ppageNumber);
					document.queryform.submit.click();
					$("tr#" + pedId).remove();
					pedId = "";
					token = 11;
				}
				break;
			case '导出' :
				url = basePath + "ea/proedpdist/ea_showExcel.jspa?search=" + search+"&fiveClear="+fiveClear;
				open(url);
				break;
				
		   case '打印' :
				url = basePath + "ea/proedpdist/ea_toPrintDevices.jspa?search=" + search+"&fiveClear="+fiveClear;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/proedpdist/ea_findList.jspa?search="
						+ search+"&fiveClear="+fiveClear;
				numback(url);
				break;
		}
	}


	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				pedId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});

	$("#querybtn").click(function() {
		$("#queryform")
				.attr(
						"action",
						basePath + "ea/proedpdist/ea_toSearch.jspa?pageNumber="
								+ ppageNumber+"&fiveClear="+fiveClear);
		document.queryform.submit.click();
	});
	
	/*
	 * zj
	 */
	var pedId="";
	$(".fieClass").each(function(){
		if(pedId=="")
			pedId+=$(this).attr("id");
		else
			pedId+=","+$(this).attr("id");
	});
	$.ajax({
		url:basePath+"ea/proedpdist/sajax_ea_ajaxGetFieData.jspa?pedId="+pedId+"&fiveClear="+fiveClear,
		type:"post",
		async : true,
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				$("#"+list[i][0]).find("span").eq(8).text(list[i][1]);
			}
		},
		error:function(data){
			alert("数据获取失败");
		}
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/proedpdist/ea_findList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&fiveClear="+fiveClear;
}