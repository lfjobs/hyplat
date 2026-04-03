$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
    var query = "<form name='postSearchForm' id='postSearchForm' method='post'>"
			+ "库存管理&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:10px;\">物品类别：</span><input type='text' style=\"width: 100px\" name='inventoryParam.goodsType'/>&nbsp;&nbsp;"
			+ "物品名称：<input name=\"inventoryParam.goodsName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";
    $.ajax({
    	url:basePath+"ea/fininv/sajax_ea_getCompanyName.jspa",
    	type:"post",
    	async : false,
		success : function(data) {
			var member = eval("(" + data + ")");
    		var companyName=member.companyName;
    		if(companyName=="北京天太世统科技有限责任公司"){
    			query+="<a style='position: absolute;right: 10px;top:6px;text-decoration:none;' id='finance' href='javascript:;'>切换财务库存模块</a>";
    		}
    	},
    	error:function(data){
    		alert("数据获取失败");
    	}
    })
	$('.address').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [/*{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, */{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '库存盘点',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '申请报损',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置上下限值',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
					},{
					name : '添加至设备',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印条码',
					bclass : 'checkout',
					onpress : action
						//
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			//case '导出' :
				//var url = basePath
						//+ "ea/warehousing/ea_showExcel.jspa?pageNumber=" + pNumber
						//+ "&search=" + search;
				//open(url);
				//break;
			/*case '查询' :
				$("#jqModelSearch").jqmShow();
				break;*/
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/warehousing/ea_getInventoryManagementList.jspa?search="
						+ search;
				numback(url);
				break;
			case '申请报损':
				if (seedID == "" || (seedID.substring(0,4))=="good") {
					alert("请选择！");
					return;
					}
				var num =$("tr#" + seedID).find("span#goodstatus").text();
				nums=$("tr#" + seedID).find("span#bad").text();
				if(num == "正常" || num==""){
				$("#jqModel").jqmShow();
				}else{
					alert("当前物品已报损");
					return;
				}
				break;
			case '库存盘点':
				window.open(basePath+"ea/warehousing/ea_invprint.jspa?"); 
				break;
			case '设置上下限值':
				if (seedID == "" || (seedID.substring(0,4))=="good") {
					alert("请选择！");
					return;
					}
				var num =$("tr#" + seedID).find("span#suan").text();
				if(num == ""){
					$("#jqModel2").jqmShow();	
				}else{
					alert("预警值已设置");
				}
				break;
				case '添加至设备':
				
				if (seedID == "") {
					
						alert("请选择！");
						return;
					
				}else{
					if(seedID.indexOf("inventory")==-1){
						alert("请选择具体物品！");
						return;
					}
				}
				$("#jqModeldevice").jqmShow();
				break;
			case '打印条码':
				if (seedID == "" || (seedID.substring(0,4))=="good") {
					alert("请选择！");
					return;
					}
				$("input#inventoryid").val(seedID);
				var num =$("tr#" + seedID).find("span#goodstatus").text();
				nums=$("tr#" + seedID).find("span#bad").text();//库存数量
				var goodsname =$("tr#" + seedID).find("span#goodsname").text();
				//取inventoryID的值查询单个物品编码
				if(num == "正常" || num==""){
					var URL = basePath
							+ "ea/warehousing/sajax_ea_getListGoodsNumByID.jspa?seedID="
							+ seedID + "&date=" + new Date().toLocaleString();
					$.ajax({
						url : encodeURI(URL),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
						    var goodsnum = member.goodsNumList;
							if (goodsnum == null) {
								alert("没有数据");
								notoken = 0;
								return;
							}
							var tabletr="";
							for (var i = 0; i < goodsnum.length; i++) {
								tabletr+="<tr style='cursor: hand;' class='addgoodsnum' id ="+goodsnum[i][0]+" name="+seedID+">";
								tabletr += "<td align='center' width='40'><input type ='radio' class='ragood' value="+ seedID + " name='check'/></td>";
								tabletr += "<td align='center' width='130'><span id='xh'>"+ ((goodsnum[i][1])==null ?'': goodsnum[i][1])+"</span></td>";
								tabletr += "<td align='center' width='140'><span id='goodsname' >"+ goodsname +"</span></td>";
								tabletr += "<td align='center' width='140'><span id='pmbh'>"+ ((goodsnum[i][3])==null ?'': goodsnum[i][3]) +"</span></td></tr>";
							}
							$("div#goodsnum").find("tr#goodsnum").after(tabletr);
							window.status = "数据加载成功";
						},
						error : function cbf(data) {
							notoken = 0;
							alert("数据获取失败！");
						}
					});
					$("#jqModel3").jqmShow();
				}else{
					alert("当前物品已报损");
					return;
				}
				break;
		}
	}
	
	$("#finance").click(function(){
		if(confirm("是否切换至财务库存模块")){
			window.location.href=basePath+"ea/fininv/ea_getPageHomeData.jspa";
		}else
			return;
	});
	//单击事件
	$("tr[id]").live("click", function(event) {
				seedID = this.id;
				$("input.ragood", $(this)).attr("checked", "checked");
			});
	//打印条码
	$("#tosubmitgnum").click(function() {
		if (seedID == "" || (seedID.substring(0,8))!="goodsnum") {
			alert("请选择！");
			return;
		}
		var goodsname=$("div#goodsnum").find("tr#" + seedID).find("span#goodsname").text();
		var pmbh=$("div#goodsnum").find("tr#" + seedID).find("span#pmbh").text();
		var xh=$("div#goodsnum").find("tr#" + seedID).find("span#xh").text();
		var code=pmbh+xh;
		window.open(basePath + "ea/goodsmanage/ea_barcode.jspa?goodsName="+goodsname+"&staffName="+xh+"&parameter=" + encodeURI(code));
	});
	$("#goodnumclose").click(function() {
		$("#jqModel3").jqmHide();
		$("div#goodsnum").find("tr.addgoodsnum").remove();
		var invid=$("input#inventoryid").val();
		seedID=invid;
	});
	
	//申请报损
	$("#tosubmit").click(function() {
		var testbad=$("#badQuantity").val();
		var statstext=$("#goodstatus",$("#postForm")).val();
		if(statstext==""){
			alert("请选择物品状态！");
			return;
		}
		if(testbad=="" ){
		alert("报损数量不能为空！");
		return;
		}
		if(parseInt(testbad)>parseInt(nums)){
			alert("报损数量大于库存数量\n请输入有效数字！！！");
			return;
		}
		var reg=/^[1-9]\d*$/;
		if(!reg.test(testbad)){
			 alert("请填写正整数！")
			 return;
		}
		$("#postForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/warehousing/ea_breakdowngood.jspa?pageNumber="
								+ pNumber + "&search=" + search+"&inventoryParam.inventoryID="+seedID);
		document.postForm.submit.click();
		token = 13;
	});
	//设置上下限值
	$("#tosubmits").click(function(){
		var invenonline=$("#invenOnline").val();
		var invenunderline=$("#invenUnderline").val();
		$(".notnull").trigger("blur");
				if ($("form .error").length) {
					return;
				}
		var reg=/^[1-9]\d*$/;
		if(!reg.test(invenonline)||!reg.test(invenunderline)){
			 alert("请填写正整数！")
			 return;
		}	
		$("#suanForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/warehousing/ea_record.jspa?pageNumber="
								+ pNumber + "&search=" + search+"&inventoryParam.inventoryID="+seedID);
		document.suanForm.submit.click();
		token = 13;
	});
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						basePath
								+ "ea/warehousing/ea_toSearchInventoryManagement.jspa?pageNumber="
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
$(".address tr[id]").click(function() {
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
				tabletr += "<td class='td_bg01'><span id='goodsname' >"+ ((pageForm.list[i][1])==null ?'': pageForm.list[i][1])+"</span></td>";
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][2])==null ?'': pageForm.list[i][2])+" </td>";
				tabletr += "<td class='td_bg01'><span >"+((pageForm.list[i][3])==null ?'无': pageForm.list[i][3]) +"</span> </td>";
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
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][8])==null ?'':pageForm.list[i][8])+ "</td>";
				if(pageForm.list[i][10]=='红色'){
				tabletr += "<td class='td_bg01'><span id='suan' class='xx'><font size='' color='#FF0000'>"+ pageForm.list[i][9]+"</font></span> </td>";
				}else if(pageForm.list[i][10]=='蓝色'){
				tabletr += "<td class='td_bg01'><span id='suan' class='xx1'><font size='' color='#0033FF'>"+ pageForm.list[i][9]+"</font></span> </td>";
				}else if(pageForm.list[i][10]=='黑色'){
				tabletr += "<td class='td_bg01'><span id='suan' >"+ pageForm.list[i][9]+"</span> </td>";
				}else{
				tabletr += "<td class='td_bg01'><span id='suan' ></span> </td>";	
				}
				tabletr += "<td class='td_bg01'>"+ ((pageForm.list[i][12])==null ?'': pageForm.list[i][12])+" </td></tr>";
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
});

//添加至设备中的确定添加 @mz
$("#confirmAdd").click(function(){
	if(confirm("确定添加至设备")){

	var url = basePath+"ea/costsheet/sajax_ea_addDevice.jspa";
	$.ajax({
		url:url,
		type:"get",
		async:false,
		dataType:"json",
		data:{
			goodsID:$("#"+seedID).attr("name"),
			codeID:$("#codeID").val()
		},
		success:function(data){
			var mem = eval("("+data+")");
			var result = mem.result;
			if(result=="fail"){
				alert("添加失败，不能重复添加");
				
			}else{
				alert("操作成功");
				
			}
			token = 1;
			re_load();
		},error:function(data){
			alert("添加设备失败");
		}
		
	});
	
	}
    
});



});


function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/warehousing/ea_getInventoryManagementList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
