$(function() {
	var gamJeomID = "";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
    $p = $("div#jqModelSearch");
	var title = "";
	if (type == "c") {
		title = "奖罚单公司汇总";
		
		flexh(title);
		$p.find("tr#cc").hide();
	} else if (type == "g") {
		title = "奖罚单集团汇总";
		flexh(title);
		$p.find("tr#cc").show();
	} else {
		flex();
		$p.find("tr#cc").hide();
	}
	function flex() {
		$('.JQueryflexme').flexigrid({
					height : 360,
					width : 'auto',
					minwidth : 30,
					title : '金币罚单',
					minheight : 80,
					buttons : [{
						name : '添加',
						bclass : 'add',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '修改',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},{
						name : '查看',
						bclass : 'edit',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '查询',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '导出',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '打印预览',
						bclass : 'mysearch',
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
	}
	
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.cstaffForm.reset();
				getID();
				date();
				$("#operator1", $("#cstaffForm")).val(staff);
				$("#jqModel").jqmShow();		
				break;
			case '修改' :	
				if (gamJeomID == "") {
					alert('请选择!');
					return;
				}					
				$p = $("tr#" + gamJeomID);
				var status = $.trim($p.find("span#receiptsStatus").text());
				if(status != '草稿'){
					alert("只能修改草稿!");
					return;
				}							
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				
				//遍历帐号级别
				var tp = $p.find("span#type1").text();	
				$("#type option").each(function(){
					if($(this).text() == tp ){
						 $(this).attr("selected",true);
						}								
					});
										
				$p.find("span[id]").each(function() {
							$t.find("#" + this.id).val($(this).text());
						});
				date();	
				$("#jqModel").jqmShow();				
				break;
			case '查看' :
				if (gamJeomID == "") {
					alert('请选择!');
					return;
				}
				acceName = $p.find("#look1").text();	
				if ($p.find("#look1").text() != '') {
					var onload = acceName.substring(acceName.lastIndexOf("."),
							acceName.length);
					if (onload.toLowerCase() != ".jpg"
							&& onload.toLowerCase() != ".gif"
							&& onload.toLowerCase() != ".png") {
						$("#isLoad")
								.find("a")
								.attr(
										"href",
										basePath
												+ "ea/publicreceipts/ea_downFile.jspa?downLoadPath="
												+ acceName);
						$("#isLoad").show(); // 附件下载显示
					} else {
						$("#isLook").show(); // 附件查看显示
					}
				} else {
					$("#isNull").show(); // 附件无显示
				}				
				var url = basePath+ "ea/goldticket/ea_getDetailsFine.jspa?prID="+ gamJeomID;
		        open(url);
				
				break;				
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath+ "ea/goldticket/ea_ShowExcel.jspa?pageNumber="+ ppageNumber+"&pri=pri";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/goldticket/ea_getListGoldTicket.jspa?";						
				numback(url);
				break;
			case '打印预览' :
				if (gamJeomID == "") {
					alert('请选择!');
					return;
				}
				url = basePath + "ea/goldticket/ea_getDetailsFine.jspa?prID=" + gamJeomID+"&print=print";
				open(url);
				break;
		}
	}
	$("input.JQueryreturn").click(function() {// 取消
		$("span.hideAll").hide();
		$("#jqModel").jqmHide();
		re_load();
		
	});	
	$(".JQueryflexme tr[id]").each(function() { // 页面遍历判断附件格式
		var load = $("tr#" + this.id).find("#look1").text();
		if (load != '') {
			var onload = load.substring(load.lastIndexOf("."),
					load.length);
			if (onload.toLowerCase() != ".jpg"
					&& onload.toLowerCase() != ".gif"
					&& onload.toLowerCase() != ".png") {
				$("tr#" + this.id).find("#load").show();
			} else {
				$("tr#" + this.id).find("#look").show();
			}
		} else {
			$("tr#" + this.id).find("#wu").show();
		}
	});
	

$("input.JQuerySubmit").click(function() {// 保存
	
	submit("1",gamJeomID);	
	});
	

$("input.JQueryDraft").click(function() {// 草稿
	
	submit("0",gamJeomID);					
});
	
//获取选项
$(".JQueryflexme tr[id]").click(function() {
	gamJeomID = this.id;
	$("input.JQuerypersonvalue", $(this))
			.attr("checked", "checked");
});	
	
// 查询
$("#tosearch").click(function() {
	$("#postSearchForm")
			.attr("action",basePath
							+ "ea/goldticket/ea_toSearch.jspa?pageNumber="
							+ ppageNumber);
	document.postSearchForm.submit.click();
});	
		
	$(function(){
		var url = basePath+"ea/goldticket/sajax_ea_getoList.jspa?date="+new Date().toLocaleString();
		$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationlist;
					var data = new Array();
					data[0] = {
						id : treeID,
						pid : '-1',
						text : treeName
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					$t = $("div#jqModel");
					$p = $("div#jqModelSearch");
					var ts = new TreeSelector(
							$t.find("select#deptID")[0], data, -1);
					ts.createTree();
					var t1 = new TreeSelector(
							$p.find("select#deptID")[0], data, -1);
					t1.createTree();
		
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
		});		
	});
	
	
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/goldticket/ea_getListGoldTicket.jspa?pageNumber="
				+ pageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function submit(satus,gamJeomID){
	$(".put3").trigger("blur");
	if ($("form .error").length) {
		notoken = 0;
		return;
	}else{
		url=basePath+"ea/goldticket/ea_saveFindGold.jspa?pageNumber="+ ppageNumber+"&satus="+ satus+"&PrID="+ gamJeomID;
		$("#cstaffForm").attr("action",url);
		
		if(sub == '0'){
			sub == '1';
			document.cstaffForm.submit.click();			
			document.cstaffForm.reset();	
		}				
	}
							
}






















