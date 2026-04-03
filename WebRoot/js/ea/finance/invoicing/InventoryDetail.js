$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
    var query = "<form name='postSearchForm' id='postSearchForm' method='post'>"
			+ "进销存汇总&nbsp;（"+title+"）<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">物品类别：</span><input type='text' style=\"width: 100px\" name='stockinvParam.goodsType'/>&nbsp;&nbsp;"
			+ "物品名称：<input name=\"stockinvParam.goodsName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/warehousing/ea_getInventoryDetailList.jspa?search="
						+ search;
				numback(url);
				break;
			case '打印预览':
				var url =basePath
				+ "page/ea/main/finance/invoicing/Inventory_frame.jsp?search="+search
				+ "&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one0";
		         open(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/warehousing/ea_detailShowExcel.jspa?pageNumber=" + pNumber
						+ "&search=" + search+"&state=1";
				open(url);
				break;
		}
	}	
	
	// 这一行的单击事件
	/*$(".address tr[id]").click(function() {
		financialbillID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});*/
	
	//单击事件
	/*$("tr[id]").live("click", function(event) {
				seedID = this.id;
				$("input.ragood", $(this)).attr("checked", "checked");
			});*/
	//查询
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/warehousing/ea_toSearchInventoryDetail.jspa?pageNumber="
								+ pNumber);
		document.postSearchForm.submit.click();
	});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	//用于图片
/*$(".address tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		inventoryID = this.id;
		if($(this).attr("name")=="add"){//ln 判断name值，如果是add，执行生成行操作
		$(this).attr("name","remove");//更改name值
		var urlpath= basePath+ "ea/warehousing/sajax_ea_getspan.jspa?&date="+new Date()+"&zongID="+inventoryID;
		$.ajax({
		url : encodeURI(urlpath),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var pageForm = member.pageForm;
			if (pageForm == null) {
				alert("没有数据");
				notoken = 0;
				return;
			}
			var tabletr="";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr+="<tr class='td_bg01 saveAjax' id ="+pageForm.list[i][0]+" name="+inventoryID+">";
				tabletr += "<td class='td_bg01'><input type ='radio' class='ragood' value="+ pageForm.list[i][0] + " name='check'/></td>";
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][1])==null ?'': pageForm.list[i][1])+" </td>";
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][2])==null ?'': pageForm.list[i][2])+" </td>";
				tabletr += "<td class='td_bg01'><span >"+((pageForm.list[i][3])==null ?'': pageForm.list[i][3]) +"</span> </td>";
				tabletr += "<td class='td_bg01'><span id='bad' >"+ ((pageForm.list[i][4])==null ?'': pageForm.list[i][4]) +"</span> </td>";
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][5])==null ?'': pageForm.list[i][5])+" </td>";
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][6])==null ?'': pageForm.list[i][6])+" </td>";
				if(pageForm.list[i][7]=='00'){
				tabletr += "<td class='td_bg01'><span id='goodstatus' >"+ "正常"+"</span></td>";
				}else if(pageForm.list[i][7]=='01'){
				tabletr += "<td class='td_bg01'><span id='goodstatus' >"+ "维修"+"</span> </td>";
				}else{
				tabletr += "<td class='td_bg01'><span id='goodstatus' >"+ "损坏"+"</span> </td>";
				}
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][8])==null ?'':'pageForm.list[i][8]')+ "</td>";
				if(pageForm.list[i][10]=='红色'){
				tabletr += "<td class='td_bg01'><span id='suan' class='xx'>"+ pageForm.list[i][9]+"</span> </td>";
				}else if(pageForm.list[i][10]=='蓝色'){
				tabletr += "<td class='td_bg01'><span id='suan' class='xx1'>"+ pageForm.list[i][9]+"</span> </td>";
				}else if(pageForm.list[i][10]=='黑色'){
				tabletr += "<td class='td_bg01'><span id='suan' >"+ pageForm.list[i][9]+"</span> </td>";
				}else{
				tabletr += "<td class='td_bg01'><span id='suan' ></span> </td></tr>";	
				}
			}
			$("tr#"+inventoryID).after(tabletr);
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});

}else if($(this).attr("name")=="remove"){//ln 判断name值，如果是remove，执行删除行操作
	$(this).attr("name","add");//更改name值
	$("tr[class='td_bg01 saveAjax'][name="+inventoryID+"]").remove();
}
});*/

});


function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/warehousing/ea_getInventoryDetailList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
