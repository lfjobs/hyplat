$(function() {
			var logBookID = "";

			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			$('.JQueryflexme').flexigrid({
						height : 350,
						width : 'auto',
						minwidth : 30,
						title : '工作日志汇总',
						minheight : 80,
						buttons : [{
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
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}, {
							name : '打印',
							bclass : 'mysearch',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						}]
					});
			function action(com, grid) {
				switch (com) {
					case '查看' :
						if (logBookID == "") {
							alert('请选择!');
							return;
						}
						document.cstaffForm.reset();
						$t = $("table#stafftable");
						$p = $("tr#" + logBookID);
						var photo = $p.find("span#attachment").text();
						if (photo.length != 0) {
							$t.find('img#attachment').attr("src",
									pbasePath + photo);
						}
						$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
						$t.find(":input[name]#scoreSort").val($p
								.find("select option:selected").text());
						$("#jqModel").jqmShow();
						break;
					case '查询' :
						$("#jqModelSearch").jqmShow();
						break;
					case '打印' :
						document.postDaYinForm.reset();
						$("#DaYin").jqmShow();
						break;
					// case '归档加锁':
					// $("#jqModelLock").jqmShow();
					// break;
					case '导出' :
						url = pbasePath
								+ "ea/logbooksummary/ea_showExcel.jspa?search="
								+ psearch + "&sdate=" + psdate + "&edate="
								+ pedate+ "&aa=zz";
						open(url);
						break;
					case '设置每页显示条数' :
						var url = pbasePath
								+ "ea/logbooksummary/ea_getListLogBook.jspa?search="
								+ psearch + "&sdate=" + psdate + "&edate="
								+ pedate+ "&aa=zz";
						numback(url);
						break;
				}
			}
			$(".menu00").click(function() {
						$(this).hide();
					});
			$(".JQueryflexme tr[id]").dblclick(function() {
						action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
					});
			$(".JQueryflexme tr[id]").click(function() {
				logBookID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			$("input.JQueryreturn").click(function() {// 取消
						$("#jqModel").jqmHide();

					});
			$("#toDaYin").click(function() {
				var date = $("#todaydate", $("#cataffPrintTable")).val();
				if (date == "") {
					alert("日期不为空");
					$(".todaydate").css("background-color", "red");
					return;
				}

				var staffName = $("table#cataffPrintTable").find("#staffName")
						.val();
				var todaydate = $("table#cataffPrintTable").find("#todaydate")
						.val();
				window
						.open(pbasePath
								+ "ea/logbooksummary/ea_toDaYin.jspa?logbooksummary.todaydate="
								+ todaydate + "&logbooksummary.staffName="
								+ encodeURI(staffName));
				$("#DaYin").jqmHide();
			});

			$("#tosearch").click(function() {	
					$("#postSearchForm")
						.attr(
								"action",
								pbasePath
										+ "ea/logbooksummary/ea_toSearch.jspa?pageNumber="
										+ ppageNumber + "&aa=zz");
				document.postSearchForm.submit.click();
			});
			// $("#toLock").click(function(){
			// if($("#jqModelLock #sdate").val()==""){alert('请输入开始时间 ');return;}
			// if($("#jqModelLock #edate").val()==""){alert('请输入结束时间');return;}
			//                	
			// $("#postLockForm").attr("action",
			// pbasePath+"ea/logbooksummary/ea_toLock.jspa?pageNumber="+ppageNumber+"&lock=on");
			// document.postLockForm.submit.click();
			// });
			// $("#unLock").click(function(){
			// if($("#jqModelLock #sdate").val()==""){alert('请输入开始时间');return;}
			// if($("#jqModelLock #edate").val()==""){alert('请输入结束时间');return;}
			// $("#postLockForm").attr("action",
			// pbasePath+"ea/logbooksummary/ea_toLock.jspa?pageNumber="+ppageNumber+"&lock=off");
			// document.postLockForm.submit.click();
			// });
			$(".JQueryflexme").find("select").each(function() {
						$s = $(this).hide();
						$o = $("<span/>").text($s.find("option:selected")
								.text());
						$o.insertAfter($s);
					});
		});