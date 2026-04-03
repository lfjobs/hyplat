$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '预算收入金额',
				minheight : 80,
				buttons : [{
					name : '添加',
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
					name : '删除',
					bclass : 'delete',
					onpress : action
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.addForms.reset();
				$("#jqModel").jqmShow();	
				break;
			case '修改' :
				if(cashID == ""){
					alert('请选择！');
					return
				}
				document.upForms.reset();
				$t = $("table#uptables");
				$p = $("tr#" + cashID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				$("#jqModels").jqmShow();
				break;
			case '删除' :
				if(cashID == ""){
					alert('请选择！');
					return
				}
				$f = $('#cashtimeList');
				$f.find(':input#cashID').val(cashID);
				if (confirm("是否删除？")) {
					$("#cashtimeList").attr("target", "hidden").attr(
							"action",basePath + "ea/cashtime/ea_deletlist.jspa?cashid="+cashID);
					document.cashtimeList.submit.click();
					$("tr#" + cashID).remove();
					cashID = "";
					token = 11;
				}
				break;
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/cashtime/ea_getcashList.jspa?search="+ search;
				numback(url);
				break;
		
		}
	}
	//查询
	$("#search").click(function() {
		$("#SearchForm").attr("action",basePath + "/ea/cashtime/ea_toSearch.jspa?pageNumber="+ pNumber+"&stime="+$("#sdate").val());
		document.getElementById("SearchForm").submit();
		$("#SearchTable").find(":input[name]").val("");
	});	
	 //添加提交
	$("input.addSubmits").click(function() {
		if($("#num", $("#addForms")).val()==""){
			alert("请填写金额！");
			return;
		}
		if($("#stime", $("#addForms")).val()==""){
			alert("请填写时间！");
			return;
		}
		var url1="<%=basePath%>/ea/cashtime/sajax_ea_ajaxFunction.jspa?date="+new Date().toLocaleString()+"&stime="+$("input#stime",$("#addForms")).val();
	               $.ajax({
	                        url: encodeURI(url1),
	                        type: "get",
	                        async: false,
	                        dataType: "json",
	                        success: function cbf(data){
				              var member = eval("(" + data + ")");
							  var c = member.count;
							  if (c != 0) {
									alert("该月份已添加");
									return;
							  }else{
								  	$("#addForms").attr("target", "hidden").attr(
									"action",basePath + "/ea/cashtime/ea_getFunction.jspa?num="+$("input#num",$("#addForms")).val()+"&stime="+$("input#stime",$("#addForms")).val());
									document.addForms.submit.click();
									token = 2;
							  }
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
					});
		
	});
	//添加的取消
	$("input.addreturns").click(function(){
		$("#jqModel").jqmHide();
		re_load();
	});
	 //修改提交
	$("input.upSubmits").click(function() {
		if($("#num", $("#upForms")).val()==""){
			alert("请填写金额！");
			return;
		}
		if ($("form .error").length) {
			return;
		}
		$("#upForms").attr("target", "hidden").attr("action",basePath+ "ea/cashtime/ea_updatelist.jspa?cashid="+cashID+"&pageNumber="+ pNumber + "&search=" + search);
		document.upForms.submit.click();
		token = 2;
	});
	//修改的取消
	$("input.upreturns").click(function(){
		$("#jqModels").jqmHide();
		re_load();
	});
	//单击事件
	$(".flexme11 tr[id]").click(function() {
				cashID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + cashID);
				}
				$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
			});
});

function re_load() {
	if (token)
		document.location.href = basePath+ "ea/cashtime/ea_getcashList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value");
}