$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.logbook').flexigrid({
				height : 145,
				width : 'auto',
				minwidth : 30,
				title : '工作日志----当前人员' + staffName,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '全部保存',
					bclass : 'add',
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
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"logbookmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				select++;
				break;
			case '修改' :
				if (logBookID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + logBookID + " #status").attr("value") == '01') {
					alert("已加锁不可修改");
					break;
				}
				
				var todate = $("span#todaydate", $("tr#" + logBookID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = pbasePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+tomanths;
				$.ajax({ //判断个人日志是否被加锁
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的日志已被加锁,不可修改！");
							 	return;
							}
							$p = $("tr#" + logBookID);
							if ($p.hasClass("check")) {
								return;
							}
							$p.addClass("check");
							$p.find(':input:gt(0)').each(function() {
								$(this).attr("name",
										"logbookmap[" + select + "]." + this.name);
							});
							select++;
							$p.find("span")
									.addClass("model1");
							$p.find("input").removeClass("model1");
							$p.find("td:has(select)").children("div")
									.children("select").attr("disabled", false);
							$p.find("select").show();
							$(this).parent().children("span").show();
						}
				});
				break;
			case '删除' :
				if (logBookID == '') {
					alert("请选择！");
					return;
				}
				if (logBookID.substring(0, 2) == 'sa') {
					if (confirm("是否移除？")) {
						$("#" + logBookID).remove();
						logBookID = '';
					}
					return;
				}
				if ($("#" + logBookID + " #status").attr("value") == '01') {
					alert("已加锁不可删除");
					break;
				}
				
				var todate = $("span#todaydate", $("tr#" + logBookID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = pbasePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+tomanths+"&date="+new Date();
				$.ajax({ //判断个人日志是否被加锁
						url : encodeURI(url),
						type: "get",
						async: false,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的日志已被加锁,不可修改！");
							 	return;
							}
							$f = $('#logFrom');
							if (confirm("是否删除？")) {
								if (notoken)
									return;
								notoken = 1;
								$f
										.attr("target", "hidden")
										.attr(
												"action",
												pbasePath
														+ "ea/logbook/ea_delLogBook.jspa?pageNumber="
														+ ppageNumber + "&logbook.staffID="
														+ logbookstaffID + "&sdate="
														+ psdate + "&edate=" + pedate
														+ "&logbook.logBookID=" + logBookID
														+ "&status=" + status);
								document.logFrom.submit.click();
								$("tr#" + logBookID).remove();
								logBookID = '';
								token = 11;
							}
						}
				});
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/logbook/ea_getListLogBook.jspa?logbook.staffID="
						+ logbookstaffID + "&sdate=" + psdate + "&edate="
						+ pedate + "&scoreSort=" + scoreSort + "&status="
						+ status;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				 sdate = $("#sdate").attr("value");
				 edate = $("#edate").attr("value");
				var url = pbasePath
						+ "ea/logbook/ea_showExcel.jspa?logbook.staffID="
						+ logbookstaffID + "&sdate=" + psdate + "&edate="
						+ pedate + "&scoreSort=" + scoreSort + "&status="
						+ status;
				open(url);
				break;
			case '全部保存' :
				if (notoken)
					return;
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = '';
				var manths = new Array();
				$("input#todaydate", $(".check")).each(function(i, tmp) {
					var todate = this.value;
					var $s = $(this);
					if (todate == "") {
						$s.css("background-color", "red");
						re = 1;
					}else{
						var tomanths = todate.substring(0,todate.lastIndexOf("-"));
						manths[i] = tomanths;
					}
				});
				$("input.baseScore", $(".check")).each(function(i, tmp) {
					var scoreSort = this.value;
					if (isNaN(scoreSort) || scoreSort == '') {
						$(this).css("background-color", "red");
						re = 2;
					}
				});
				$("input.jobLocation", $(".check")).each(function(i, tmp) {
					var jobLocation = this.value;
					if (jobLocation.length>50) {
						$(this).css("background-color", "red");
						re = 3;
					}
				});		
				if (re){
					if (re == 1) {
						alert("日期不能为空!");
					}else if( re == 2){
						alert("得分不能为空且必须为数字！");
					}else{
						alert("工作地点不能超过50个字!");
					}
					notoken = 0;
					return;
				}else{
					var str =manths.join(','); //数组转成字符串
				    var arr = [];
				    str = str.replace(/([^,]*)/g, function($0, $1, i){ //去除重复数据
				        if(str.indexOf($1) == i) arr[arr.length] = $1;
				    });
				    var manth = arr.join(','); //数组转成字符串
					var url = pbasePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+manth;
					$.ajax({ //判断个人日志是否被加锁
							url : encodeURI(url),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
								var member = eval("(" + data + ")");
								var islock = member.islock;
								if(islock != ''){
								 	alert("此人员"+islock+"的日志已被加锁,不可再添加！");
								 	notoken = 0;
								 	return;
								}
								$('#logbookForm')
										.attr("target", "hidden")
										.attr(
												"action",
												pbasePath
														+ "ea/logbook/t_ea_saveLogBook.jspa?pageNumber="
														+ ppageNumber);
								document.logbookForm.submit.click();
								token = 2;
							}
					});
				}
				break;
		}
	}
	$(".logbook tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				logBookID = this.id;
			});
	$(".logbook tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				logBookID = this.id;
				action("修改");
			});
	$(".logbook").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
	$("#tosearch").click(function() {
		$f = $('#postSearchForm');
		$f.attr("action", pbasePath
						+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/logbook/ea_getListLogBook.jspa?logbook.staffID="
				+ logbookstaffID + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}