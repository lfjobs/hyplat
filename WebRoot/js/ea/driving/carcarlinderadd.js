$(document)
		.ready(
				function() {

					if ($("#carlinderID").val() != "") {
						$(".textType").hide();
						$("select").hide();
						$(".tj").attr("display", "none");

						$("#chooseconcart").hide();
					} else {
						$("#deleteline").show();
						$("#chooseconcart").show();
						$(".tj").attr("display", "block");
					}
					$(".jqmWindow").jqm({
						modal : true,// 限制输入（鼠标点击，按键）的对话
						overlay : 20
					// 遮罩程度%
					}).jqmAddClose('.close');// 添加触发关闭的selector
					// 添加行
					$("#addline")
							.click(
									function() {

										var addline = "<tr><td align='center'><img src='"
												+ basePath
												+ "images/gtk-del.png' style='cursor: pointer;' class='removeline' onclick='removeline(this)'/></td>"
												+ "<td><input type='text' class='textType' name = 'carcymap["
												+ select
												+ "].cylinderNum'/></td><td>"
												+ "<select name='carcymap["
												+ select
												+ "].cylinderType' class='selecttype'><option value='--请选择--'>--请选择--</option><option value='C:缠绕瓶'>C:缠绕瓶</option><option value='G:钢瓶'>G:钢瓶</option></select></td>"
												+ "<td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].designThickness'/></td><td>"
												+ "<select name='carcymap["
												+ select
												+ "].cylinderModel' class='carlindermodelclone'><option>Φ279</option><option>Φ229</option><option>NGVH-325</option><option>NGVH-356</option><option>NGVH-465</option></select></td>"
												+ "<td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].manufactureCompany' /></td><td><input type='text' class='textType'  onfocus='date(this)'name='carcymap["
												+ select
												+ "].leavefactorydate'/></td>"
												+ "<td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].volume'/></td><td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].weight'/></td>"
												+ "<td><input type='text' class='textType' value='20' name='carcymap["
												+ select
												+ "].nominalworkpressure'/></td><td><select name='carcymap["
												+ select
												+ "].hydraulicTestPressure'style='width:100%'><option>30</option><option>33.4</option></select></td><td><input type='text' class='textType' radyonly='radyonly' name='carcymap["
												+ select
												+ "].texture' value='30CrMo'/></td>"
												+ "<td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].fiber'/></td><td><input type='text' class='textType' name='carcymap["
												+ select
												+ "].resin'/></td><td><input type='text' value='中国' class='textType' name='carcymap["
												+ select
												+ "].manufacturingcountry'/></td>"
												+ "<td  class='bor_r'><input type='text' class='textType' name='carcymap["
												+ select
												+ "].madecode'/></td><td><input type='text' style='display:none' name='carcymap["
												+ select
												+ "].status'/></td></tr>"
												
												
												
										
										$("#informationline").after(
												$(addline).clone(true));
										select++;
									})

				});
function removeline(obj) {
	$(obj).parent().parent().remove();

}

/** **********************************选择往来个人**************************************** */
$(document)
		.ready(
				function() {
					var cuID = "";
					var userstaffID = "";
					$("table#gouserstable tr[id]").live(
							"click",
							function(event) {
								cuID = this.id;
								userstaffID = this.title;
								$("input.rauser", $(this)).attr("checked",
										"checked");
							});

					// 选择往来个人
					$("#xzwlgr")
							.click(
									function() {
										$("input#contactUserID",
												$("table#searchuser")).val("");
										$("select#relation",
												$("table#searchuser"))
												.val("全部");
										$(".jqmWindow", $("#selectuserForm"))
												.jqmShow();
										cxwlgr("contactUser.staffName=&contactUser.relation=");
									});

					// 新增往来个人
					$(".xzgr")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
									});
					// 关闭选择
					$(".JQueryreturns").click(function() {
						$(".jqmWindow", $("#selectuserForm")).jqmHide();
					});
				
					// 添加所选中的往来个人
					$("#qduser")
							.click(
									function() {
										if (cuID != "") {
											$("#userstaffID").val(userstaffID);
											var RegistrationuserURL = basePath
													+ "ea/contactuser/sajax_ea_getListRegistrationUser.jspa?contactUser.staffID="
													+ userstaffID
													+ "&date="
													+ new Date()
															.toLocaleString();
											$
													.ajax({
														url : encodeURI(RegistrationuserURL),
														type : "get",
														async : true,
														dataType : "json",
														success : function cbf(
																data) {
															var member = eval("("
																	+ data
																	+ ")");
															var nologin = member.nologin;
															if (nologin) {
																document.location.href = basePath
																		+ "page/ea/not_login.jsp";
															}
															var bankList = member.bankList;
															$se = $("select#userNum");
															$se.empty();
															$se
																	.append("<option selected='selected' value = ''>--请选择--</option>");
															for ( var i = 0; i < bankList.length; i++) {
																$op = $("<option />");
																$op
																		.attr(
																				"value",
																				bankList[i].bankAccount)
																		.text(
																				bankList[i].bankName
																						+ "---"
																						+ bankList[i].bankAccount);
																$se.append($op);
															}
															$(
																	"span#userAccountNum")
																	.remove();
															$se
																	.attr(
																			"name",
																			"financialBill.userAccountNum");
															$("#xzwlgr").after("<input type='button' onclick='fdxddfperson()' id='clearperson' value='清除所选往来个人'/>");
															$se.show();
														},
														error : function cbf(
																data) {
															notoken = 0;
															alert("数据获取失败！");
														}
													});
											$("tr #" + cuID)
													.children("td")
													.each(
															function() {
																if (this.id == "contactUserID") {
																	$(
																			"input#contactUserID",
																			$("table#table5"))
																			.val(
																					$(
																							this)
																							.text());

																} else if (this.id == "phone") {

																	$(
																			"select#phone option[value="
																					+ $(
																							this)
																							.text()
																					+ "]",
																			$("table#table5"))
																			.remove();
																	$(
																			"select#phone",
																			$("table#table5"))
																			.append(
																					"<option selected='selected' value = "
																							+ $(
																									this)
																									.text()
																							+ ">"
																							+ $(
																									this)
																									.text()
																							+ "</option>")
																			.show();
																	$(
																			"span#phone",
																			$("table#table5"))
																			.hide();
																} else {
																	$(
																			"span#"
																					+ this.id,
																			$("table#table5"))
																			.text(
																					$(
																							this)
																							.text());
																	$(
																			"input#"
																					+ this.id,
																			$("table#table5"))
																			.val(
																					$(
																							this)
																							.text());
																}
															});
											cuID = "";
											$(".jqmWindow",
													$("#selectuserForm"))
													.jqmHide();
										} else {
											alert("请选择往来个人！");
										}
									});
				
					// 根据输入的往来个人名称查询
					$("input#searchuu").click(
							function() {
								cuID = "";
								userstaffID = "";
								var typeName = $("input#contactUserID",
										$("table#searchuser")).val();
								var relation = $("select#relation",
										$("table#searchuser")).val();
								cxwlgr("contactUser.staffName=" + typeName
										+ "&contactUser.relation=" + relation);
							});

					// 上一页
					$("#grsy").click(
							function() {
								var sy = $("#grsy").attr("title");
								if (sy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $("select#relation",
											$("table#searchuser")).val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + sy;
									cxwlgr(typeCN);
								} else {
									alert("已是首页！");
								}
							});
					// 下一页
					$("#grxy").click(
							function() {
								var xy = $("#grxy").attr("title");
								if (xy != 0) {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $("select#relation",
											$("table#searchuser")).val();
									var typeCN = "contactUser.staffName="
											+ typeName
											+ "&contactUser.relation="
											+ relation
											+ "&pageForm.pageNumber=" + xy;
									cxwlgr(typeCN);
								} else {
									alert("已是尾页！");
								}
							});

					// ajax查询往来个人列表
					function cxwlgr(typeCN) {
						if (notoken) {
							alert("正在获取数据！请稍等");
							return;
						}
						notoken = 1;
						$("#grsy").attr("title", 0);
						$("#grxy").attr("title", 0);
						$("#grzy").attr("title", 0);
						var searchurl = basePath
								+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
						$
								.ajax({
									url : encodeURI(searchurl + typeCN
											+ "&date="
											+ new Date().toLocaleString()),
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
										var pageForm = member.pageForm;
										var codeRelationList = member.codeRelationList;
										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}
										var $se = $("select#relation",
												$("table#searchuser"));
										$se.empty();
										$select = "<option selected='selected' value = ''>--全部--</option>";
										$se.append($select);
										for ( var i = 0; i < codeRelationList.length; i++) {
											$op = $("<option />");
											$op
													.attr(
															"value",
															codeRelationList[i].codeValue)
													.text(
															codeRelationList[i].codeValue);
											$se.append($op);
										}
										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#grsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#grxy").attr("title", dqy + 1);
										}
										$("span#grzycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th><th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
										for ( var i = 0; i < pageForm.list.length; i++) {
											tabletr += "<tr style='cursor: hand;' id = "
													+ pageForm.list[i].relationID
													+ " title= "
													+ pageForm.list[i].staffID
													+ ">";
											tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
													+ pageForm.list[i].relationID
													+ " name='checkradio'/></td>";
											tabletr += "<td id='contactUserName' align='center'>"
													+ pageForm.list[i].staffName
													+ "</td>";
											tabletr += "<td id='phone' align='center'>"
													+ pageForm.list[i].relation
													+ "</td>";
											tabletr += "<td id='tel' align='center'>"
													+ pageForm.list[i].reference
													+ "</td>";
											tabletr += "<td id='staffIdentityCard' align='center'>"
													+ pageForm.list[i].staffIdentityCard
													+ "</td>";
											tabletr += "<td id='userQq'  align='center'>"
													+ pageForm.list[i].referenceCode
													+ "</td>";
											tabletr += "<td id='email'  align='center'>"
													+ pageForm.list[i].referenceOrganization
													+ "</td>";
											tabletr += "<td id='userAddr'  align='center'>"
													+ pageForm.list[i].staffAddress
													+ "</td>";
											tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
													+ pageForm.list[i].staffID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cu").html(tabletr);
										$("#body_02cu").show();
										alert("数据加载成功");
										notoken = 0;
										window.status = "数据加载成功";
									},
									error : function cbf(data) {
										notoken = 0;
										alert("数据获取失败！");
									}
								});
					}
				});

/** **********************************往来单位**************************************** */
$(document)
		.ready(
				function() {
					var contactcID = "";// 已经添加到往来单位的ID
					var ccID = "";// ccompanyID
					$("table#gostable tr[id]").live("click", function(event) {
						contactcID = this.id;
						ccID = this.title;
						$("input.ra", $(this)).attr("checked", "checked");
					});

					// 选择往来单位
					$("#xzwlaw")
							.click(
									function() {
										$("input#ccompanyID",
												$("table#searchcompany")).val(
												"");
										$("select#contactConnections",
												$("table#searchcompany")).val(
												"全部");
										$(".jqmWindow", $("#selectcompanyForm"))
												.jqmShow();
										cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
									});

					// 新增往来单位
					$(".xzdw")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/contactcompany/ea_getListContactCompany.jspa?");
									});

					// 添加所选中的往来单位
					$("#qdcompany")
							.click(
									function() {
										if (contactcID != "") {
											$("#ccompanyidcarlinder").val(ccID);
											var RegistrationURL = basePath
													+ "ea/contactcompany/sajax_ea_getListRegistration.jspa?contactCompany.ccompanyID="
													+ ccID
													+ "&date="
													+ new Date()
															.toLocaleString();
											$
													.ajax({
														url : encodeURI(RegistrationURL),
														type : "get",
														async : true,
														dataType : "json",
														success : function cbf(
																data) {
															var member = eval("("
																	+ data
																	+ ")");
															var nologin = member.nologin;
															if (nologin) {
																document.location.href = basePath
																		+ "page/ea/not_login.jsp";
															}
															var bankList = member.bankList;
															$se = $("select#aNum",$("table#table4"));
															$se.empty();
															$se.append("<option selected='selected' value = ''>--请选择--</option>");
															for ( var i = 0; i < bankList.length; i++) {
																$op = $("<option />");
																$op.attr("value",bankList[i].bankAccount).text(
																bankList[i].bankName+ "---"+ bankList[i].bankAccount);
																$se.append($op);
															}
															$("span#accountNum",$("#table4")).remove();
															$se.attr("name","financialBill.accountNum");
															$("#xzwlaw").after("<input type='button' onclick='clearcompany()' id='clearcom' value='清除所选往来单位'/>");
															$se.show();
														},
														error : function cbf(
																data) {
															notoken = 0;
															alert("数据获取失败！");
														}
													});
											$("tr #" + contactcID)
													.children("td")
													.each(
															function() {
																if (this.id == "ccompanyID") {
																	$(
																			"input#ccompanyID",
																			$("table#table4"))
																			.val($(this).text());
																} else if (this.id == "contactConnections") {
																	$("select#contactConnections option[value="+ $(this).text()+ "]",
																		$("table#table4")).remove();
																	$("select#contactConnections",
																			$("table#table4")).append(
																					"<option selected='selected' value = "+ $(this).text()+ ">"+ $(this).text()+ "</option>")
																					.show();
																	$("span#contactConnections",$("table#table4")).hide();
																	
																} else {
																	$("span#"+ this.id,$("table#table4")).text($(this).text());
																	$("input#"+ this.id,$("table#table4")).val($(this).text());
																}
															});
											contactcID = "";
											$(".jqmWindow",$("#selectcompanyForm")).jqmHide();
										} else {
											alert("请选择往来单位！");
										}
									});
					// 关闭退出选择
					$(".JQueryreturns").click(function() {
						$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
					});
					// 根据输入的往来单位名称查询
					$("input#searchcc").click(
							function() {
								contactcID = "";
								var typeName = $("input#ccompanyID",
										$("table#searchcompany")).val();
								var contactConnections = $(
										"select#contactConnections",
										$("table#searchcompany")).val();
								cxwldw("contactCompany.companyName=" + typeName
										+ "&cconnection.contactConnections="
										+ contactConnections);
							});

					// 上一页
					$("#dwsy")
							.click(
									function() {
										var sy = $("#dwsy").attr("title");
										if (sy != 0) {
											contactcID = "";
											var typeName = $(
													"input#ccompanyID",
													$("table#searchcompany"))
													.val();
											var contactConnections = $(
													"select#contactConnections",
													$("table#searchcompany"))
													.val();
											var typeCN = "contactCompany.companyName="
													+ typeName
													+ "&cconnection.contactConnections="
													+ contactConnections
													+ "&pageForm.pageNumber="
													+ sy;
											cxwldw(typeCN);
										} else {
											alert("已是首页！");
										}
									});

					// 下一页
					$("#dwxy")
							.click(
									function() {
										var xy = $("#dwxy").attr("title");
										if (xy != 0) {
											contactcID = "";
											var typeName = $(
													"input#ccompanyID",
													$("table#searchcompany"))
													.val();
											var contactConnections = $(
													"select#contactConnections",
													$("table#searchcompany"))
													.val();
											var typeCN = "contactCompany.companyName="
													+ typeName
													+ "&cconnection.contactConnections="
													+ contactConnections
													+ "&pageForm.pageNumber="
													+ xy;
											cxwldw(typeCN);
										} else {
											alert("已是尾页！");
										}
									});
					
					
					
					// ajax查询往来单位列表
					function cxwldw(typeCN) {
						if (notoken) {
							alert("正在获取数据！请稍等");
							return;
						}
						notoken = 1;
						$("#dwsy").attr("title", 0);
						$("#dwxy").attr("title", 0);
						$("#dwzy").attr("title", 0);
						var searchurl = basePath
								+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
						$
								.ajax({
									url : encodeURI(searchurl + typeCN
											+ "&date="
											+ new Date().toLocaleString()),
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
										var pageForm = member.pageForm;
										var connectionlist = member.connectionlist;
										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}
										var $se = $(
												"select#contactConnections",
												$("table#searchcompany"));
										$se.empty();
										$select = "<option selected='selected' value = ''>--全部--</option>";
										$se.append($select);
										for ( var i = 0; i < connectionlist.length; i++) {
											$op = $("<option />");
											$op
													.attr(
															"value",
															connectionlist[i].codeValue)
													.text(
															connectionlist[i].codeValue);
											$se.append($op);
										}
										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#dwsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#dwxy").attr("title", dqy + 1);
										}
										$("span#zycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
										for ( var i = 0; i < pageForm.list.length; i++) {
											tabletr += "<tr style='cursor: hand;' title="
													+ pageForm.list[i].ccompanyID
													+ " id = "
													+ pageForm.list[i].contactConnectionID
													+ ">";
											tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
													+ pageForm.list[i].contactConnectionID
													+ " name='checkradio'/></td>";
											tabletr += "<td id='ccompanyname' align='center'>"
													+ pageForm.list[i].companyName
													+ "</td>";
											tabletr += "<td id='contactConnections' align='center'>"
													+ pageForm.list[i].contactConnections
													+ "</td>";
											tabletr += "<td id='industryType' align='center'>"
													+ pageForm.list[i].industryType
													+ "</td>";
											tabletr += "<td id='companyTel'  align='center'>"
													+ pageForm.list[i].companyTel
													+ "</td>";
											tabletr += "<td id='cresponsible' align='center'>"
													+ pageForm.list[i].cresponsible
													+ "</td>";
											tabletr += "<td id='responsibleTel' align='center'>"
													+ pageForm.list[i].responsibleTel
													+ "</td>";
											tabletr += "<td id='companyAddr'  align='center'>"
													+ pageForm.list[i].companyAddr
													+ "</td>";
											tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
													+ pageForm.list[i].ccompanyID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cc").html(tabletr);
										$("#body_02cc").show();
										alert("数据加载成功");
										notoken = 0;
										window.status = "数据加载成功";
									},
									error : function cbf(data) {
										notoken = 0;
										alert("数据获取失败！");
									}
								});

					}
					$(".jquemsubmit").click(function() {
										var licensenumber = $("#carnum").text();
										var ccompanyidcarlinder = $(
												"#ccompanyidcarlinder").val();
										var concertpeople = $("#userstaffID")
												.val();
										var flag = false;
										var concertCom = $("#concertcompany")
										.val();
										var carlinderID = $("#carlinderID")
												.text();
										
										$(".selecttype").each(function() {
											
										 if($(this).find("option:selected").text()=="--请选择--"){
											 alert("气瓶类型必须选！")
											 return;
										 }else{
											 
											 if (confirm("确定继续？")) {
													$("#carlinderForm")
															.attr("target", "main")
															.attr(
																	"action",
																	basePath
																			+ "ea/productregister/ea_saveCarlinderInformation.jspa?ccompanyIDlinder="
																			+ ccompanyidcarlinder
																			+ "&concertpeople="
																			+ concertpeople
																			+ "&carlinderID="
																			+ carlinderID
																			+ "&liscennum="
																			+ licensenumber);
													document.carlinderForm.submit
															.click();
													token = 2;
													return;
												} 
										 }
										
										});
										
											
									});
					$("#carlindertype")
							.change(
									function() {
										// 动态操作select 的option
										if ($('#carlindertype option:selected')
												.val() == "G:钢瓶") {
											$("#carlindermodel").empty();
											$("#carlindermodel")
													.append(
															"<option value='Φ279'>Φ279</option>");
											$("#carlindermodel")
													.append(
															"<option value='Φ229'>Φ229</option>");
											$("#hydraulicTestPressure")
													.remove();
											$("#chanraopingval").remove();
											$(".hydraulicTestPressure")
													.append(
															"<select id='cylinderTestPressure' name = 'carcymap[0].hydraulicTestPressure' style='width:100%'><option>30</option><option>33.4</option></select>");
										}
										if ($('#carlindertype option:selected')
												.val() == "C:缠绕瓶") {
											$("#carlindermodel").empty();
											$("#carlindermodel")
													.append(
															"<option value='NGVH-325'>NGVH-325</option>");
											$("#carlindermodel")
													.append(
															"<option value='NGVH-356'>NGVH-356</option>");
											$("#carlindermodel")
													.append(
															"<option value='NGVH-465'>NGVH-465</option>");
											$("#cylinderTestPressure").remove();
											$(".hydraulicTestPressure")
													.append(
															"<span id = 'chanraopingval' name='carcymap[0].hydraulicTestPressure'>30</span>");

										}
										if ($('#carlindertype option:selected')
												.val() == "--请选择--") {
											$("#carlindermodel").empty();
											$("#cylinderTestPressure").remove();
											$("#chanraopingval").remove();
										}
									});
				});


function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/productregister/ea_getListProductregister.jspa";
}

