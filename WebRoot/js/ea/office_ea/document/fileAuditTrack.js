$(document)
		.ready(
				function() {

					$(".jqmWindow").jqm({
						modal : true,// 限制输入（鼠标点击，按键）的对话
						overlay : 20
					// 遮罩程度%
					}).jqmAddClose('.close');// 添加触发关闭的selector

					$('.wspdoc').flexigrid({
						height : 349,
						width : 'auto',
						minwidth : 30,
						title : '文件审批跟踪登记表',
						minheight : 349,
						buttons : [ {
							separator : true
						}, {
							name : '查看详情',
							bclass : 'see',
							onpress : action
						// 当点击调用方法
						},{
							separator : true
						}, {
							name : '查看附件',
							bclass : 'attach',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						}, {
							name : '打印文件状态跟踪报表',
							bclass : 'printer',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						}, {
							name : '打印文件责任人跟踪表',
							bclass : 'printer',
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
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
						// 当点击调用方法
						}, {
							separator : true
						} ]
					});

					function action(com, grid) {
						switch (com) {
						case '查看详情' :
							if (docId == "") {
								alert('请选择!');
								return;
							}
							window.location.href = basePath
									+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
									+ docId + "&type=toFinishedView&date=" + new Date();

							break;
						case '查看附件':
							if (docId == "") {
								alert('请选择!');
								return;
							}
							var url = basePath
									+ "ea/documentinfo/sajax_ea_viewAttach.jspa";
							$
									.ajax({
										url : url,
										type : "get",
										async : false,
										dataType : "json",
										data : {
											docId : docId
										},
										success : function(data) {
											var member = eval("(" + data + ")");
											var attach = member.attach;
											ViewOffice(attach.filePath,
													attach.fileType,
													attach.fileShowName,
													attach.fileId);

										},
										error : function(data) {
											alert("获取附件失败");
										}

									});
							break;
						case '打印文件责任人跟踪表':
							if (docId == "") {
								alert("请选择");
								return;
							}
							window
									.open(basePath
											+ "ea/documentcommon/ea_getfilePersonPrint.jspa?docId="
											+ docId);

							break;

						case '打印文件状态跟踪报表':
							$("#titles").text("查询打印");
							$("#submitresult").val("打印预览");
							$("#jqModelSearch").jqmShow();
							break;
						case '查询':
							$("#titles").text("查询");
							$("#submitresult").val("查询");
							$("#track").val("00");
							$("#jqModelSearch").jqmShow();
							break;

						case '设置每页显示条数':
							var url = basePath
									+ visiturl+"?track=00&searchType="+searchType+"&search="
									+ search +"&finishType="+finishType+"&date=" + new Date();
					
							numback(url);
							break;

						}
					}

					$(".wspdoc tr").click(
							function() {
								docId = this.id;

								$("input.JQuerypersonvalue", $(this)).attr(
										"checked", "checked");
							});
					$(".wspdoc tr").dblclick(
							function() {
								docId = this.id;
								$("input.JQuerypersonvalue", $(this)).attr(
										"checked", "checked");
								action("查看详情");
							});

					// 打印查询预览
					$("#submitresult")
							.click(
									function() {
										var url = basePath
										+ "ea/documentinfo/ea_toSearch.jspa";
										if(finishType!=""){
											if(finishType.indexOf("examine")!=-1)
											url = basePath
											+ "ea/documentflow/ea_toSearchexamine.jspa";
											
											if(finishType.indexOf("seal")!=-1)
												url = basePath
												+ "ea/documentflow/ea_toSearchseal.jspa";
											
											if(finishType.indexOf("publish")!=-1)
												url = basePath
												+ "ea/documentflow/ea_toSearchpublish.jspa";
											
											if(finishType.indexOf("read")!=-1)
												url = basePath
												+ "ea/documentflow/ea_toSearchread.jspa";
										}
										if ($("#track").val() == "00") {

											$("#searchForm").attr("target","_self")
													.attr(
															"action",url
															);
											
											
											
										} else {
											$("#searchForm")
													.attr("target", "_blank")
													.attr(
															"action",
															url);
										

										}
										document.searchForm.submit.click();
										$("#jqModelSearch").jqmHide();
										
										
									});

				});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal";

	}
}

// 点击查看office
function ViewOffice(docPath, fileType,fileShowNameField) {
	window.open(
					basePath
							+ "page/ea/main/office_ea/document/wordonlyreadprint.jsp?docPath="
							+ docPath + "&fileType=" + fileType
							+ "&fileShowNameField="+fileShowNameField);
}