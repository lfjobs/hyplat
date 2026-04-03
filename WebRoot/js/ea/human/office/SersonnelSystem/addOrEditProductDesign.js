$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//

	// 选择产品分类
	$("input.xzcpfl").click(function(){
			category("pro",paret);
	});
	//主项目目录关闭
	$("#closeml").click(function(){
		$("#jqModel").hide();
		
	});

	//菜单起效
     //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
		 window.close();
		 }
		 
	 });
	 //#saveDJInfo
	 //保存方法
	 $("#saveCPDJInfo").click(function(){
		 
		var  ppID =  $("#ppID").val();
		var  compId =  $("#compId").val();
		var  ppccj =  $("#ppccj").val();
		var  pplsjpplsj =  $("#pplsj").val();
		var  compyxyj =  $("#compyxyj").val();
		var  compgjhhryj =  $("#compgjhhryj").val();
		var  comphhryj =  $("#comphhryj").val();
		var  wfjdyj =  $("#wfjdyj").val();
		var  dlsVIPhyyj =  $("#dlsVIPhyyj").val();
		var  dlshyyj =  $("#dlshyyj").val();
		var  xfzjf =  $("#xfzjf").val();
		
		if(ppccj == ""||ppccj==null||
				pplsjpplsj == ""||pplsjpplsj==null||
				compyxyj == ""||compyxyj==null||
				compgjhhryj == ""||compgjhhryj==null||
				comphhryj == ""||comphhryj==null||
				wfjdyj == ""||wfjdyj==null||
				dlsVIPhyyj == ""||dlsVIPhyyj==null||
				dlshyyj == ""||dlshyyj==null||
				xfzjf == ""||xfzjf==null){
			
			alert("请填完所有必填项");
			return false;
		}
//		alert("trure"+
//		$("#id").val()+
//		$("#comid").val());
		$("#CostCPDJForm").attr("target","hidden")
		.attr(
				"action",
				basePath
						+ "/ea/productdesign/ea_getpackegejiage.jspa");
		document.CostCPDJForm.submit.click();
		 
	 });
	
	// 整个添加页面 获得焦点带边框，失去焦点不带边框
	 $("#CostSheetForm input.xiaoguo").blur(function(){
			 
	 if($(this).attr("class").indexOf("model1")==-1){
	 $(this).addClass("model1");
	 }
	 }).focus(function(){
	 if($(this).attr("class").indexOf("model1")!=-1){
	 $(this).removeClass("model1");
	 }
	 });

	//物品新增一行
	
	$(".addline").click(function(){
		var max = 1;
		$("#goodtable").find("tr").each(function(){
			var trid=$(this).attr("id").substring(6);
			if(trid!=""){
				if(trid>max){
					max=trid;
				}
			}
			
		});
		 $("#kelong").before($("#kelong").
	    		   clone(true).attr("id","kelong"+ (max+1)).addClass("checkgoods"));
		 parentId=$("input#ppID",$("table#table3")).attr("value");
		 $("#kelong"+(max+1)).find("input#sorting").attr("value",max+1);
		 $("#kelong"+(max+1)).show();
	});
	//记录产品配件选中的id
	$("#goodtable").find("tr").click(function(){
		$("#line").text($(this).attr("id"));
	});
	//记录产品选中的id
	$("input.productSelect").click(function(){
		$("#line").text("");
		placeholder="";
		$("#body_02").html("");
		$("#selectType").val("goods");
		$(".jqmWindow", $("#goodsForm")).jqmShow();
	});
	
	//物品新增一行
	
	$(".deleteline").click(function(){
		$("#"+$("#line").text()).remove();
	});
	
	
	// 物品表格单击样式问题
	$("#goodtable :input").click(function() {
		$("#goodtable td").find("div").each(function() {
			$(this).css("border", "none");
			$(this).find(".caz").hide();

		});
		$(this).parent("div").css("border", "1px solid #000000");
		$(this).parent("div").find(".fou").focus();
		$(this).parent("div").find(".caz").show();

	});
	$("#goodtable td").click(function() {
		$("#goodtable td").each(function() {
			$(this).find("div").css("border", "none");
			$(this).find(".caz").hide();

		});

		$(this).find("div").css("border", "1px solid #000000");
		$(this).find(".fou").focus();
		$(this).find(".caz").show();

	});

	// 责任人,项目名称，银行选中查询结果
	$("#goodsQuery tr[id]").live("click", function(event) {
		var types = $("#types").val();
		var $trs = $("#goodsQuery tr#" + this.id);
       
		if (types == "projectName"||types=="staffname"||types=="bankaccount") {
			$trs.find("td").each(function(){
				if(this.id!=""){
				 $("#table3").find("#"+this.id).val($(this).text());
				 if(this.id=="xmtype"){
					 $("#table3").find("#xmtype2").val($(this).text());
				 }
				}
			
				
			});


		}

		var kelongid = $("#line").text();
		var id = kelongid.substring(6);
		
		if (types == "goodsNum"||types=="goodsName"||types=="ccompanyName"||types=="connectName") {
			$trs.find("td").each(function(){
				 $("tr#"+kelongid).find("#"+this.id).val($(this).text());
				 
				 
		        	if (this.title == "ava"&& $(this).text().length != 0) {
						$op = $("<option />");
						$op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
						$("select#goodsVariableID",$("#"+kelongid)).append($op);
					}
				
			});
			if (types == "goodsNum"||types=="goodsName"){
//				 修改当前行的所有input的name
			$("#kelong"+ id).find(':input[name]').each(function() {
				
		           $(this).attr("name","logbookmap["+ id+ "]."+ this.name);

		     });
			  
			}
			}

		$("input.rauser", $(this)).attr("checked", "checked");
		$("#goodsQuery").hide();
	});
	
	// 键入时模糊查询项目分类
	$("#moc").click(function() {

		getCate($("#inputmoc").val());

	});

	// 各个弹出框的关闭功能
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	// 添加页面的返回功能；
	$(".JQueryClose").click(function() {
		notoken = 0;
		re_load();
	});

//	// 选择物品弹出带有物品分类的弹出框
//	$("#shuju").click(function() {
//	
//		$(".jqmWindow", $("#goodsForm")).jqmShow();
//	});


	// 迭代的商品删除
	$(".ajaxsc")
			.click(
					function() {
						var taxstatus = $("#cashiertaxstatus").val();
						if (taxstatus != "00") {
							alert("已归档不能删除该条数据！");
							return;
						}
						if (confirm("是否删除？")) {
							var goodsBillsID = $(this).parent().find(
									"input#goodsBillsID").val();
							var delurl = basePath
									+ "ea/cashierbills/sajax_ea_delGoodsBills.jspa?typeID="
									+ goodsBillsID + "&date="
									+ new Date().toLocaleString();
							$.ajax({
								url : encodeURI(delurl),
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
									var typeID = member.typeID;
									alert("删除成功！");
									$("tr#" + typeID).remove();
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
						}
					});

/*	// 克隆的商品删除
	$("img.delete").click(function() {
		$(this).parent().parent().remove();
		docNull -= 1;
	});*/

	// 保存预算
	$("input.JQuerySubmitgd").click(function() {
						if (notoken) {
							alert("正在提交数据！请稍等");
							return;
						}
						
						
						$("input.notnull", $("#CostSheetForm")).trigger("blur");
						
						if ($("form .error").length) {
							alert("请填完所有必填项");
							return;
						}
						$("tr.checkgoods",$("table#goodtable")).each(function(index){
							$(this).find("input#sorting").attr("value",index);
						})
						$("#CostSheetForm").attr("target","hidden")
								.attr(
										"action",
										basePath
												+ "/ea/productdesign/ea_addOrEditProductDesign.jspa");
						document.CostSheetForm.submit.click();
						token = 2;
			         //   $("#CostSheetForm")
					//	.attr(
					//			"action","");
						return false;
					});

			//产品发布
			$("input.JQuerySubmitfb").click(function() {
		
				if (notoken) {
					alert("正在提交数据！请稍等");
					return;
				}
				
				$("tr.checkgoods",$("table#goodtable")).each(function(index){
					$(this).find("input#sorting").attr("value",index);
				})
				$("#profitForm").attr("target","hidden")
						.attr("action",basePath+ "/ea/productdesign/ea_productPublish.jspa?pptype="+typestring);
				document.profitForm.submit.click();
				token = 2;
		        $("#profitForm").attr("action","");
				return false;
			});

	});
	




/** **********************************项目分类和项目**************************************** */

$(document)
		.ready(
				function() {


                  

					// 项目分类
					$(".projectbtn")
							.click(
									function() {
										
										$(".jqmWindow", $("#selectxmForm"))
												.jqmShow();
										
										getProjectByxmtype("parameter=&xmtype=");//默认获取该部门下的所有项目
									});

					// 新增项目
					$(".xzxm")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/promanage/ea_getProjectList.jspa?type=xm");
									});
					
					
					var cID = "";
					
					$("table#xmtable tr[id]").live(
							"click",
							function(event) {
								cID = this.id;
								
								$("input.ra", $(this)).attr("checked",
										"checked");
							});

					// 添加所选中的项目
					$("#qdxm").click(
							function() {
								if ($("input#selectxm").attr("value") != "") {
									$("#xmtypename").val($("input#selectxms").attr("value"));
									$("#xmtype").val($("input#selectxm").attr("value"));
									/*if($("#xmtype").val()=="043111"){
										$(".baokx").show();
									}else{
										$(".baokx").hide();
									}

									$("#projectName").val(
											$("tr#" + cID).find("#projectName")
													.text());
									$("#proID").val(
													$("tr#" + cID).find(
															"#proID")
															.text());
									$("#projectCode").val(
											$("tr#" + cID).find(
											"#projectCode")
											.text());*/
									$(".jqmWindow", $("#selectxmForm"))
											.jqmHide();

									return;
								} else {
									alert("请选择项目分类！");
								}
							});

					// 根据输入的项目名称查询
					$("input#searchxmbtn").click(
							function() {
								cuID = "";
								
								var parameter = $("input#parameterxm",$("table#searchxm")).val();
								var xmtype = $("input#selectxm").val();
								
								
								getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype);
							});

					// 上一页
					$("#xmsy").click(
							function() {
								var sy = $("#xmsy").attr("title");
								if (sy != 0) {
								
									
									var parameter = $("input#parameterxm",$("table#searchxm")).val();
									var xmtype = $("input#selectxm").val();
									
								
									getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype+ "&pageForm.pageNumber=" + sy);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#xmxy").click(
							function() {
								var xy = $("#xmxy").attr("title");
								if (xy != 0) {
									cuID = "";
									var parameter = $("input#parameterxm",$("table#searchxm")).val();
									var xmtype = $("input#selectxm").val();
									getProjectByxmtype("parameter="+parameter+"&xmtype="+xmtype+ "&pageForm.pageNumber=" + xy);
									
								} else {
									alert("已是尾页！");
								}
							});


				});

/** **********************************项目分类和项目**************************************** */



/** **********************************部门树和员工 **************************************** */

$(document)
		.ready(
				function() {
                    var cID="";
					$("table#dptable tr[id]").live(
							"click",
							function(event) {
								cID = this.id;
								
								$("input.rauser", $(this)).attr("checked",
										"checked");
							});


					// 选择部门
					$(".deptbtn")
							.click(
									function() {
										$(".jqmWindow", $("#selectdeptForm"))
												.jqmShow();
										$("#deptpos").val("wm");
										
										$("#selectdeptname").val(treeNames);
										$("#selectdept").val(treeID);
									getStaffMember("parameter=&selectDept="+treeID);
									});
					
					//物品选择部门 
					$(".target")
					.click(
							function() {
								$(".jqmWindow", $("#selectdeptForm"))
										.jqmShow();
								$("#deptpos").val("lm");
								$("#selectdeptname").val(treeNames);
								$("#selectdept").val(treeID);
								getStaffMember("parameter=&selectDept="+treeID);
							});

		

					// 添加所选中的人员
					$("#qddept").click(
							function() {
								if (cID != "") {
									var deptpos  = $("#deptpos").val();
									var selectdeptname = $("#selectdeptname").val();
									var selectdept = $("#selectdept").val();
									if(deptpos=="wm"){
										$("#departmentName").val(selectdeptname);
										$("#departmentID").val(selectdept);
										$("#staffid").val($("tr#" + cID).find(
												"#staffid").text());
										$("#staffname").val($("tr#" + cID).find(
										"#staffname").text());
										$("#staffcode").val($("tr#" + cID).find(
										"#staffcode").text());
									}
								if(deptpos=="lm"){
									var clicktr = $("#line").text();
									$("#"+clicktr).find("#targetDeptName").val(selectdeptname);
									$("#"+clicktr).find("#targetDeptID").val(selectdept);
									
									$("#"+clicktr).find("#targetSalerName").val($("tr#" + cID).find(
											"#staffname").text());
									$("#"+clicktr).find("#targetSalerID").val($("tr#" + cID).find(
											"#staffid").text());

								}
									$(".jqmWindow", $("#selectdeptForm"))
											.jqmHide();
								
									return;
								} else {
									alert("请选择员工！");
								}
							});

					// 根据输入的往来个人名称查询
					$("input#searchdeptbtn").click(
							function() {
								cuID = "";
								
								var parameter = $("input#parameterrm",
										$("table#searchdept")).val();
								var selectDept = $(":input#selectdept").val();
								getStaffMember("parameter="+parameter+"&selectDept="+selectDept);
							});

					// 上一页
					$("#dpsy").click(
							function() {
								var sy = $("#dpsy").attr("title");
								if (sy != 0) {
								
									
									var parameter = $("input#parameterrm",
											$("table#searchdept")).val();
									var selectDept = $(":input#selectdept").val();
									getStaffMember("parameter="+parameter+"&selectDept="+selectDept+"&pageForm.pageNumber=" + sy);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#dpxy").click(
							function() {
								var xy = $("#dpxy").attr("title");
								if (xy != 0) {
									cuID = "";
									
									var parameter = $("input#parameterrm",
											$("table#searchdept")).val();
									var selectDept = $(":input#selectdept").val();
									getStaffMember("parameter="+parameter+"&selectDept="+selectDept+ "&pageForm.pageNumber=" + xy);
								} else {
									alert("已是尾页！");
								}
							});
					
					
				


				});

/** **********************************部门树和员工**************************************** */




/** **********************************选择物品**************************************** */
$(document)
		.ready(
				function() {
					tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
					tree.enableDragAndDrop(false);
					tree.enableHighlighting(1);
					tree.enableCheckBoxes(0);
					tree.enableThreeStateCheckboxes(false);
					tree.setSkin(basePath + 'js/tree/dhx_skyblue');
					tree.setImagePath(basePath + "js/tree/codebase/imgs/");
					tree.loadXML(basePath + "js/tree/common/tree_b.xml");
					var getcodeurl = basePath
							+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
							+ new Date().toLocaleString();
					tree.insertNewChild(0, "scode20101014v5zed7cukk0000000002",
							"物品树", 0, 0, 0, 0);
					$.ajax({
						url : encodeURI(getcodeurl),
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
							var codeList = member.codeList;
							if (null == codeList) {
								return;
							}
							for ( var i = 0; i < codeList.length; i++) {

								tree.insertNewChild(
										"scode20101014v5zed7cukk0000000002",
										codeList[i].codeID,
										codeList[i].codeValue, 0, 0, 0, 0);
								tree.setUserData(codeList[i].codeID,
										"codeNumber", codeList[i].codeNumber);

							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					tree.setOnClickHandler(function() {

						treeid = tree.getSelectedItemId();
						treename = tree.getItemText(treeid);

						if (treeid != "scode20101014v5zed7cukk0000000002") {
							$(":input#parms").val("typeID=" + treename);
							cx("typeID=" + treename);
							return;
						}

					});

					// 双击选中物品
					$("table#gotable tr[id]").live("click", function(event) {
						var b = $("input.ragood", $(this)).attr("checked");
						$("input.ragood", $(this)).attr("checked", !b);
					});

					// 复选框选中物品
					$(".ragood").live("click", function(event) {
						var b = $(this).attr("checked");
						$(this).attr("checked", !b);
					});

					// 导入数据（选择物品）
					$(".shuju").click(
							function() {
                               /*if($("#xmtypename").val()==""){
                            	   alert("请选选择项目");
                            	   return;
                               }*/
								if(parentId==""){
									alert("请选择超级产品");
	                            	   return;
								}
								placeholder="";
								$("#body_02").html("");
								$("#selectType").val("goods");
								$(".jqmWindow", $("#goodsForm")).jqmShow();

							});

					// 上一页
					$("#wpsy").click(
							function() {
								var sy = $("#wpsy").attr("title");
								if (sy != 0) {
									var typeName = $(":input#parms").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + sy;
									cx(typeCN);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#wpxy").click(
							function() {
								var xy = $("#wpxy").attr("title");
								if (xy != 0) {
									var typeName = $(":input#parms").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + xy;
									cx(typeCN);
								} else {
									alert("已是尾页！");
								}
							});

					// ////////////////////////键入查询物品////////////////////////////////////////////////////
					$(".closes").click(function() {

						$("#goodsQuery").hide();

					});

					// 键入查询

					$("#table3").find(".querys").focus(function() {
						showtime($(this));

					});

					$(".querys").keyup(function() {

						showtime($(this));

					});

					function showtime(obj) {
						var query = $.trim($(obj).val());
						var type = $(obj).attr("id");

						var top = $(obj).position().top;
						var topadd=33;
						if ($(obj).parent().parent().parent().attr("id")
								.indexOf("kelong") != -1) {
							topadd = 215;
						}

						$("#goodsQuery").css({
							position : "absolute",
							left : 230,
							top : top + topadd
						}).show();

						querysometing("parameter=" + query, type);
						$("#querys").val("parameter=" + query);
						$("#types").val(type);
					}

					// 物品键入实时查询
					function querysometing(typeCN, type) {

						$("#wpsyq").attr("title", 0);
						$("#wpxyq").attr("title", 0);
						$("#wpzyq").attr("title", 0);
						var searchurl = "";
						
						if (type == "goodsNum" || type == "goodsName") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getGoodsByQuery.jspa?";
						}
						if (type == "ccompanyName") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getListContactCompany.jspa?";
						}
						if (type == "connectName") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getListContactUser.jspa?";
						}
						if (type == "referrerName") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getListContactUser.jspa?";
						}
					
						$
								.ajax({
									url : encodeURI(searchurl + typeCN
											+ "&date="
											+ new Date().toLocaleString()),
									type : "get",
									async : false,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");
										var pageForm = member.pageForm;
										var tabletr = "";
										var thead = "";
								

								

									

										if (type == "goodsNum"
												|| type == "goodsName") {
											thead = "<tr><th align=\"center\" bgcolor=\"#e4f1fa\">选择</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">物品编码</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">物品名称</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">统一分类条码</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">类型</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">品牌</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">库存量</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">单位</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">默认规格</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">型号</th>"
													+ "<th align=\"center\" bgcolor=\"#e4f1fa\">品牌规格</th></tr>";
										}

										if (type == "ccompanyName") {
											thead = "<tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>往来单位名称</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>行业类别</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>单位电话</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>单位负责人</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>负责人电话</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
										}

										if (type == "connectName"
												|| type == "referrerName") {
											thead = "<tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>个人名称</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>电话</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>身份证</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>qq</th>"
													+ "<th align='center' bgcolor='#E4F1FA'>邮箱 </th>"
													+ "<th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
										}
										$("#goodthead").html(thead);
										if (pageForm == null) {
											$("#goodboy").html("");
											$("span#wpzycountq").text(0);
											return;
										}
										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#wpsyq").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#wpxyq").attr("title", dqy + 1);
										}

										$("span#wpzycountq").text(zys);

										for ( var i = 0; i < pageForm.list.length; i++) {

											
											if (type == "goodsNum"
													|| type == "goodsName") {
												tabletr += "<tr style='cursor: hand;' id = "
														+ pageForm.list[i].goodsID
														+ ">";
												tabletr += "<td id='check' align='center'><input type='radio' class='ragood' value="
														+ pageForm.list[i].goodsID
														+ " name='check'/></td>";
												tabletr += "<td id='goodsNum' align='center'>"
														+ pageForm.list[i].goodsCoding
														+ "</td>";
												tabletr += "<td id='goodsName'  align='center'>"
														+ pageForm.list[i].goodsName
														+ "</td>";
												tabletr += "<td id='defaultStorage'  align='center'>"
														+ pageForm.list[i].defaultStorage
														+ "</td>";
												tabletr += "<td id='typeID' align='center'>"
														+ pageForm.list[i].typeID
														+ "</td>";
												tabletr += "<td id='mnemonicCode' align='center'>"
														+ pageForm.list[i].mnemonicCode
														+ "</td>";
												tabletr += "<td id='num'  align='center'>"
													    +pageForm.list[i].num
														+ "</td>";
												tabletr += "<td id='variableID' title='ava' align='center'>"
														+ pageForm.list[i].variableID
														+ "</td>";
												tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
														+ pageForm.list[i].variable1ID
														+ "</td>";
												tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
														+ pageForm.list[i].variable2ID
														+ "</td>";
												tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
														+ pageForm.list[i].variable3ID
														+ "</td>";
												tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
														+ pageForm.list[i].variable4ID
														+ "</td>";
												tabletr += "<td id='acquiesceStandard' align='center'>"
														+ pageForm.list[i].acquiesceStandard
														+ "</td>";
												tabletr += "<td id='model' align='center'>"
														+ pageForm.list[i].model
														+ "</td>";
												
												tabletr += "<td id='standard' align='center'>"
														+ pageForm.list[i].standard
														+ "</td>";
												/*tabletr += "<td id='goodsID' style='display:none' align='center'>"
													+ pageForm.list[i].goodsID
													+ "</td>";*/
						
												tabletr += " </tr>";

											}

											if (type == "ccompanyName") {
												tabletr += "<tr style='cursor: hand;' title="
														+ pageForm.list[i].ccompanyID
														+ " id = "
														+ pageForm.list[i].contactConnectionID
														+ ">";
												tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
														+ pageForm.list[i].contactConnectionID
														+ " name='checkradio'/></td>";
												tabletr += "<td id='ccompanyName' align='center'>"
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

											if (type == "connectName"
													|| type == "referrerName") {

												tabletr += "<tr style='cursor: hand;' id = "
														+ pageForm.list[i].relationID
														+ " title= "
														+ pageForm.list[i].staffID
														+ ">";
												tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
														+ pageForm.list[i].relationID
														+ " name='checkradio'/></td>";
												tabletr += "<td id='connectName' align='center'>"
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
												tabletr += "<td id='connectID'  style='display:none' align='center'>"
														+ pageForm.list[i].staffID
														+ "</td>";
												tabletr += " </tr>";
											}



										}

										$("#goodboy").html(tabletr);


									},
									error : function cbf(data) {
										notoken = 0;

									}
								});
					}

					// 上一页
					$("#wpsyq").click(
							function() {
								var sy = $("#wpsyq").attr("title");
								if (sy != 0) {
									var typeName = $("input#querys").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + sy;
									querysometing(typeCN, type);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#wpxyq").click(
							function() {
								var xy = $("#wpxyq").attr("title");
								if (xy != 0) {
									var typeName = $("input#querys").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + xy;
									querysometing(typeCN, type);
								} else {
									alert("已是尾页！");
								}
							});

					// /////////////////键入查询物品结束/////////////////////

					// 新增物品
					$(".xzwp")
							.click(
									function() {
										window
												.open(basePath
														+ "ea/gooddesign/ea_getGoodDesignList.jspa?fiveClear=5");
									});
					

/*******************************************物品以及子物品列表*****************************************************/				
// 添加所选中的物品到物品列表
$("#selectGood").click(function() {
	var clicktr = $("#line").text();
	if ($("[name='check']").is(':checked')) {	
		if(clicktr==""){//选择产品
			if($("input:checked").size()==1){
				var $checkedTr=$("input:checked").parents("tr");
				
				var trFirst=$("tr.checkgoods",$("table#goodtable")).first();//选中第一行
				
				if ($("input#ppID",$("table#table3")).attr("value") == "") {
					$("input#ppID",$("table#table3")).attr("value",getServerID());
					parentId=$("input#ppID",$("table#table3")).attr("value");
					
					$("input#ppID",trFirst).attr("value",getServerID());
					$("input#parentId",trFirst).attr("value",parentId);
					select++;
					trFirst.find(':input').each(function() {
						$(this).attr("name","productPackagingMap["+ select+ "]."+ this.name);
			        });
				}
				$("input#goodsName",$("table#table3")).attr("value",$checkedTr.children("td#goodsName").text());
				$("input#goodsID",$("table#table3")).attr("value",$.trim($checkedTr.children("td#goodsID").text()));
				$("input#goodsNum",$("table#table3")).attr("value",$checkedTr.children("td#goodsNum").text());
				$("input#type",$("table#goodtable1")).attr("value",$checkedTr.children("td#typeID").text());
				$("input#standard",$("table#goodtable1")).attr("value",$checkedTr.children("td#standard").text());
				$("input#logo",$("table#goodtable1")).attr("value",$checkedTr.children("td#logo").text());
				$("input#image",$("table#goodtable1")).attr("value",$checkedTr.children("td#image").text());
				$("img#logo",$("table#goodtable1")).attr("src",basePath+$checkedTr.children("td#logo").text());
				$("img#image",$("table#goodtable1")).attr("src",basePath+$checkedTr.children("td#image").text());
				
				
				
				$("input#goodsName",trFirst).attr("value",$checkedTr.children("td#goodsName").text());
				$("input#goodsID",trFirst).attr("value",$.trim($checkedTr.children("td#goodsID").text()));
				$("input#goodsNum",trFirst).attr("value",$checkedTr.children("td#goodsNum").text());
				$("input#type",trFirst).attr("value",$checkedTr.children("td#typeID").text());
				$("input#standard",trFirst).attr("value",$checkedTr.children("td#standard").text());
				$("input#logo",trFirst).attr("value",$checkedTr.children("td#logo").text());
				$("input#image",trFirst).attr("value",$checkedTr.children("td#image").text());
				$("img#logo",trFirst).attr("src",basePath+$checkedTr.children("td#logo").text());
				$("img#image",trFirst).attr("src",basePath+$checkedTr.children("td#image").text());
				
				$("span#nodeName",trFirst).text($checkedTr.children("td#goodsName").text());
			}else{
				alert("请选中单个物品!!");
			}
		}else if(addOrEdit=="edit"){
			if($("input:checked").size()==1){
				if ($("tr#"+ clicktr).find("#ppID").val() == "") {
					$("tr#"+ clicktr).find(':input').each(function() {
						$(this).attr("name","productPackagingMap["+ clicktr.substring(6)+ "]."+ this.name);
			        });
					$("input#ppID",$("tr#"+ clicktr)).attr("value",getServerID());
					$("input#parentId",$("tr#"+ clicktr)).attr("value",$("input#ppID",$("table#table3")).attr("value"));
					$("tr#"+ clicktr).find("td#hierarchy").html($("tr#kelong").find("td#hierarchy").children().clone(true));
				}
				var $checkedTr=$("input:checked").parents("tr");
				$("input#goodsName",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#goodsName").text());
				$("input#goodsID",$("tr#"+ clicktr)).attr("value",$.trim($checkedTr.children("td#goodsID").text()));
				$("input#goodsNum",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#goodsNum").text());
				$("input#typeID",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#typeID").text());
				$("input#standard",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#standard").text());
				$("input#logo",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#logo").text());
				$("input#image",$("tr#"+ clicktr)).attr("value",$checkedTr.children("td#image").text());
				$("img#logo",$("tr#"+ clicktr)).attr("src",basePath+$checkedTr.children("td#logo").text());
				$("img#image",$("tr#"+ clicktr)).attr("src",basePath+$checkedTr.children("td#image").text());
				
				$("span#nodeName",$("tr#"+ clicktr)).text($checkedTr.children("td#goodsName").text());
			}else{
				alert("请选中单个物品!!");
			}
			addOrEdit="add";
		}else{//选择产品配件以及子配件
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					select++;
					// 克隆一行并修改文本框的name
					if ($("tr#"+ clicktr).find("#ppID").val() == "") {
						$("tr#"+ clicktr).before($("tr#kelong").clone(true).attr("id","kelong"+ select).addClass("checkgoods"))	;		
					}else{
						$("tr#"+ clicktr).after($("tr#kelong").clone(true).attr("id","kelong"+ select).addClass("checkgoods"));		
					}
					//添加占位符placeholder  达到层级效果 
		        	$("tr#kelong"+ select).find("td#hierarchy").html($("tr#"+ clicktr).find("td#hierarchy").html()).prepend($("<img>").attr("src",basePath +"/js/tree/codebase/imgs/blank.gif").attr("class","treeTable"));
		        	
					$("tr#kelong"+ select).find(':input').each(function() {
						$(this).attr("name","productPackagingMap["+ select+ "]."+ this.name);
			        });
					//添加ppID
					$("input#ppID",$("tr#kelong"+ select)).attr("value",getServerID());
					//添加parentId
					$("input#parentId",$("tr#kelong"+ select)).attr("value",parentId);
					//添加sorting
					$("input#sorting",$("tr#kelong"+ select)).attr("value",select);
					// 修改当前行的所有input的name getServerID()
					$("tr#"+ $(this).val()).children("td").each(function() {
			        	$("input#"+ this.id,$("tr#kelong"+ select)).val($(this).text());
			        	$("img#"+ this.id,$("tr#kelong"+ select)).attr("src",basePath+$(this).text());
			        	if (this.title == "ava"&& $(this).text().length != 0) {
							$op = $("<option />");
							$op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
							$("select#goodsVariableID",$("tr#kelong"+ select)).append($op);
						}
					});
					$("tr#kelong"+ select).find("td#hierarchy").find("span#nodeName").text($("tr#kelong"+ select).find("input#goodsName").attr("value"));
					$("tr#kelong"+ select).show();
					$(this).attr("checked",false);
				}
			});
		}
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	} else {
			alert("请选择物品！");
	}
});
//选中物品新增下级
$("img.addChild").click(function() {
		var clicktr=$(this).parent().parent().attr("id");
		if ($("tr#"+ clicktr).find("#ppID").val() == "") {
			alert("请先增加父级物品后增加下级!");
        } else {
        	placeholder=$("<img>").attr("src",basePath +"/js/tree/codebase/imgs/blank.gif").attr("class","treeTable");
        	parentId=$.trim($("tr#"+ clicktr).find("input#ppID").val());
      
        	$("#line").text($(this).parent().parent().attr("id"));
        	$("#body_02").html("");
			$("#selectType").val("goods");
			$(".jqmWindow", $("#goodsForm")).jqmShow();
		}
}); 
//编辑当前物品
$("img.edit").click(function(){
		if(parentId==""){
			alert("请选择父级物品!");
			   return;
		}
		var clicktr=$(this).parent().parent().attr("id");
		addOrEdit="edit";
		$("#line").text($(this).parent().parent().attr("id"));
    	$("#body_02").html("");
		$("#selectType").val("goods");
		$(".jqmWindow", $("#goodsForm")).jqmShow();
});
//切换当前物品下的子物品的可见状态
$("img.showOrHideChildren,span#nodeName").live("click",function(){
	   	
	   var clicktr=$(this).parent().parent().attr("id");
	   if ($("tr#"+ clicktr).find("#ppID").val() == "") {
			alert("父级物品为空!");
       } else {
    	   if($(this).hasClass("show")){
    		   //隐藏
    		   $(this).removeClass("show");
    		   showOrHideChildrenHide($(this).parent().parent().find("input#ppID").attr("value"));
    	   }else{
    		   //显示
    		   $(this).addClass("show");
    		   getProductPackagingListByPID($(this).parent().parent().find("input#ppID").attr("value"));
        	   showOrHideChildrenShow($(this).parent().parent().find("input#ppID").attr("value"));
    	   }
       }
});
//隐藏物品以及所有子物品
function showOrHideChildrenHide(id){
	var $child=$("input#parentId[value="+id+"]").parent().parent();
	for ( var int = 0; int < $child.size(); int++) {
		$child.eq(int).hide();
		showOrHideChildrenHide($child.eq(int).find("input#ppID").attr("value"));
	}
}
//显示物品以及所有子物品
function showOrHideChildrenShow(id){
	var $child=$("input#parentId[value="+id+"]").parent().parent();
	for ( var int = 0; int < $child.size(); int++) {
		$child.eq(int).show();
	}
}
//ajax查询子物品并展示
function getProductPackagingListByPID(id){
	var $Select=$("input#ppID[value="+id+"]").parent().parent();
	var url = basePath + "ea/productdesign/sajax_n_ea_getProductPackagingListByPID.jspa?";
	$.ajax({
		url:url,
		type:"get",
		async : false,
		data : {
			"productDesign.ppID" : id,
			"date":new Date().toLocaleString()
		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
			var productPackagingList = member.productPackagingList;
			if(productPackagingList!=null||productPackagingList.length!=0){
				for ( var int = 0; int < productPackagingList.length; int++) {
					if($("input#ppID[value="+productPackagingList[int].ppID+"]").size()==0){
						select++;
						$Select.after($("tr#kelong").clone(true).attr("id","kelong"+ select).addClass("checkgoods"));		
						//添加占位符placeholder  达到层级效果 
			        	$("tr#kelong"+ select).find("td#hierarchy").html($Select.find("td#hierarchy").html()).prepend($("<img>").attr("src",basePath +"/js/tree/codebase/imgs/blank.gif").attr("class","treeTable"));
			        	$("tr#kelong"+ select).find(':input').each(function() {
							$(this).attr("name","productPackagingMap["+ select+ "]."+ this.name);
				        });
			        	
				        $("input#goodsNum",$("tr#kelong"+ select)).attr("value",productPackagingList[int].goodsNum);
			        	$("input#goodsName",$("tr#kelong"+ select)).attr("value",productPackagingList[int].goodsName);
						$("input#goodsID",$("tr#kelong"+ select)).attr("value",productPackagingList[int].goodsID);
						
						$("input#standard",$("tr#kelong"+ select)).attr("value",productPackagingList[int].standard);
						$("input#typeID",$("tr#kelong"+ select)).attr("value",productPackagingList[int].type);
						
						$("input#quantity",$("tr#kelong"+ select)).attr("value",productPackagingList[int].quantity);
						$("input#money",$("tr#kelong"+ select)).attr("value",productPackagingList[int].money);
						$("input#weight",$("tr#kelong"+ select)).attr("value",productPackagingList[int].weight);
						$("input#price",$("tr#kelong"+ select)).attr("value",productPackagingList[int].price);
						
						$("input#ppKey",$("tr#kelong"+ select)).attr("value",productPackagingList[int].ppKey);
						$("input#ppID",$("tr#kelong"+ select)).attr("value",productPackagingList[int].ppID);
						$("input#parentId",$("tr#kelong"+ select)).attr("value",productPackagingList[int].parentId);
						$("input#sorting",$("tr#kelong"+ select)).attr("value",productPackagingList[int].sorting);
						
						$("input#logo",$("tr#kelong"+ select)).attr("value",productPackagingList[int].logo);
						$("input#image",$("tr#kelong"+ select)).attr("value",productPackagingList[int].sorting);
						$("img#logo",$("tr#kelong"+ select)).attr("src",basePath + productPackagingList[int].logo);
						$("img#image",$("tr#kelong"+ select)).attr("src",basePath + productPackagingList[int].image);
						
						$("span#nodeName",$("tr#kelong"+ select)).text(productPackagingList[int].goodsName);
						
			        	$("tr#kelong"+ select).show();
					}
				}
			}
		},
		error : function(data) {
			alert("获取数据失败");
		}
		
	});
}
//删除物品以及所有子物品
$("img.delete").click(function(){
	   var clicktr=$(this).parent().parent().attr("id");
	   if ($("tr#"+ clicktr).find("#ppID").val() == "") {
		   $("tr#"+ clicktr).remove();
	   } else {
		   if(confirm("确定删除本级以及所有子集!")){
			   deleteChildren($(this).parent().parent().find("input#ppID").attr("value"));
			   deleteProductPackagingByPID($(this).parent().parent().find("input#ppID").attr("value"));
		   }
	   }
});
//向上移动位置
$("img.up").click(function(){
	var thisTR=$(this).parents("tr");
	var thisID=$(this).parents("tr").find("input#ppID").attr("value");
	var prevTR = thisTR.prevAll().has("input#parentId[value='"+thisTR.find("input#parentId").attr("value")+"']").first();
	if(prevTR.size()>0){
		thisTR.insertBefore(prevTR);
		function findChildrenNode(thisID,thisTR){
			var $child=$("input#parentId[value="+thisID+"]").parent().parent();
			for ( var int = 0; int < $child.size(); int++) {
				thisTR.after($child.eq(int));
				findChildrenNode($child.eq(int).find("input#ppID").attr("value"),thisTR);
			}
		}	
		findChildrenNode(thisID,thisTR);
	}
})

//向下移动位置
$("img.down").click(function(){
	var thisTR=$(this).parents("tr");
	var nextTR = thisTR.nextAll().has("input#parentId[value='"+thisTR.find("input#parentId").attr("value")+"']").first();
	var nextID=nextTR.find("input#ppID").attr("value");
	if(nextTR.size()>0){
		nextTR.insertBefore(thisTR);
		function findChildrenNode(nextID,nextTR){
			var $child=$("input#parentId[value="+nextID+"]").parent().parent();
			for ( var int = 0; int < $child.size(); int++) {
				nextTR.after($child.eq(int));
				findChildrenNode($child.eq(int).find("input#ppID").attr("value"),nextTR);
			}
		}
		findChildrenNode(nextID,nextTR);
	}
})
//删除物品以及所有子物品以及后代物品
function deleteChildren(id){
	var $child=$("input#parentId[value="+id+"]").parent().parent();
	for ( var int = 0; int < $child.size(); int++) {
		$child.eq(int).remove();
		deleteChildren($child.eq(int).find("input#ppID").attr("value"));
	}
	$("input#ppID[value="+id+"]").parent().parent().remove();
}

//ajax删除物品以及所有子物品以及后代物品
function deleteProductPackagingByPID(id) {
	
	var url = basePath + "ea/productdesign/sajax_n_ea_deleteProductPackagingByPID.jspa?";
	$.ajax({
		url : url,
		type : "get",
		async : true,
		data : {
			"productDesign.ppID" : id,
			"date":new Date().toLocaleString()
		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
			var success = member.success;
			if(success=="success"){
				alert("删除成功!");
			}
		},
		error : function(data) {
			alert("删除失败");
		}
	});
}
//ajax动态从后台获得serverID
function getServerID() {
	var url = basePath + "ea/productdesign/sajax_n_ea_getServerID.jspa?date="
			+ Math.random();
	var serverID="";
	$.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
			serverID = member.serverID;
		},
		error : function(data) {
			alert("获取ServerID失败");
		}
	});

	return serverID;
}

// 计算物品金额
$(".jisuan").live("keyup keydown focus blur",function(event) {
	if (this.value != "") {
		if (isNaN(this.value)) {
			$(this).attr("value",""); 
		} else {
				var $trThis=$(this).parent().parent().parent();
				var quantity=parseInt($.trim($trThis.find("input#quantity").val())==""?"0":$.trim($trThis.find("input#quantity").val()));
				var price=parseFloat($.trim($trThis.find("input#price").val())==""?"0.00":$.trim($trThis.find("input#price").val())).toFixed(2);
				var money=quantity*price;
				$(this).parent().parent().parent().find("input#quantity").val(quantity);
				/*$(this).parent().parent().parent().find("input#price").val(price);*/
				$(this).parent().parent().parent().find("input#money").val(money.toFixed(2));
				
		}
	}
});
//汇总物品金额
$(".jisuan",$("table#goodtable1")).live("keyup keydown focus blur",function(event) {
	/*var ppID=$("table#table3").find("input#ppID").attr("value") ;
	//var parentId=$("input[value='"+ppID+"']").parents("tr").find("input#parentId").attr("value");
	var totalAmount=0;
	$("input[value='"+ppID+"']").parents("tr").each(function(){
		if(!isNaN($.trim($(this).find("input#money").attr("value")))&&$.trim($(this).find("input#money").attr("value"))!=""){
			totalAmount+=parseInt($.trim($(this).find("input#money").attr("value")));
		}
	})
	$("input#quantity",$("table#goodtable1")).attr("value",1);
	$("input#price",$("table#goodtable1")).attr("value",totalAmount);
	$("input#money",$("table#goodtable1")).attr("value",totalAmount.toFixed(2));
	*/
	$("input#price.history",$("table#productPriceCategory")).attr("value",$("input#price",$("table#goodtable1")).attr("value"));
	$("input#money.history",$("table#productPriceCategory")).attr("value",$("input#money",$("table#goodtable1")).attr("value"));
})
//弹出DIV显示预览图片
$("img.preview").live("mouseover",function(event){  
	
 	var $div = $("#div_allFun");  
     $div.show();  
     $div.css("left",document.body.scrollLeft+event.clientX+1);  
     $div.css("top",document.body.scrollLeft+event.clientY+1);
     $div.css("width",225);
     $div.css("height",225);
     $div.html($("<img>").attr("src",$(this).attr("src")).css("width",225).css("height",225));
     $div.css("background-color","#EFF7FE");     
});  
$("img.preview").live("mouseout",function(event){  
 var $div = $("#div_allFun");   
    $div.hide();   
}); 
$("tr.checkgoods").live("click",function(){
	$(this).find("td,input").css("background-color", "#E4F1FA"); 	
	$(this).siblings().find("td").css({"border-color":"#a8c7ce","background-color":"#FFFFFF"});
	$(this).siblings().find("input").css({"background-color":"#FFFFFF"});
})

/********************************************************在线编辑文档*************************************/
//附产品说明书
$(".accessoriesUrl1").click(function() {
	var accessoriesUrl = $.trim($("#manualUrl").attr("value"));
	var urlReturn = OpenWord(accessoriesUrl, 2);
	$("#manualUrl").attr("value", urlReturn);
});
//附产品宣传
$(".accessoriesUrl2").click(function() {
	var accessoriesUrl = $.trim($("#propagandaUrl").attr("value"));
	var urlReturn = OpenWord(accessoriesUrl, 2);
	$("#propagandaUrl").attr("value", urlReturn);
});
//附公司文件
$(".accessoriesUrl3").click(function() {
	var accessoriesUrl = $.trim($("#fileUrl").attr("value"));
	var urlReturn = OpenWord(accessoriesUrl, 2);
	$("#fileUrl").attr("value", urlReturn);
});

// 根据输入的物品编号或物品名称查询
$("input#searchGood").click(
							function() {
								var typeName = $("#typeID",
										$("table#searchgood")).val();
								$(":input#parms").val("parameter=" + typeName);

								cx("parameter=" + typeName);
							});

					// ajax查询物品列表
					function cx(typeCN) {
						if (notoken) {
							alert("正在获取数据！请稍等");
							return;
						}
						notoken = 1;
						$("#wpsy").attr("title", 0);
						$("#wpxy").attr("title", 0);
						$("#wpzy").attr("title", 0);
						var searchurl = basePath
								+ "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
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
										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}
										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#wpsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#wpxy").attr("title", dqy + 1);
										}
										//
										$("span#wpzycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
										tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
										tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
										tabletr += "<th align='center' bgcolor='#E4F1FA'>类型</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>品牌</th>";
										tabletr += "<th align='center' bgcolor='#E4F1FA'>库存量</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>单位</th>";
										tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th>"
												+ "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
										if ($("#selectType").val() == "projects") {

											types = "radio";
										}
										for ( var i = 0; i < pageForm.list.length; i++) {
											tabletr += "<tr style='cursor: hand;' id = "
													+ pageForm.list[i].goodsID
													+ ">";
											tabletr += "<td id='check' align='center'>"
													+ "<input type ='checkbox' class='ragood' value="
													+ pageForm.list[i].goodsID
													+ " name='check'/></td>";
											tabletr += "<td id='goodsNum' align='center'>"
													+ pageForm.list[i].goodsCoding
													+ "</td>";
											tabletr += "<td id='goodsName'  align='center'>"
													+ pageForm.list[i].goodsName
													+ "</td>";
											tabletr += "<td id='sortCode'  align='center'>"
													+ pageForm.list[i].defaultStorage
													+ "</td>";
											tabletr += "<td id='typeID' align='center'>"
													+ pageForm.list[i].typeID
													+ "</td>";
											tabletr += "<td id='mnemonicCode' align='center'>"
													+ pageForm.list[i].mnemonicCode
													+ "</td>";
											tabletr += "<td id='variableID'  align='center'>"
													+ "</td>";
											tabletr += "<td id='goodsID' style='display:none' align='center'>"
													+ pageForm.list[i].goodsID
													+ "</td>";
											tabletr += "<td id='unit' title='ava' align='center'>"
													+ pageForm.list[i].variableID
													+ "</td>";
											tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
													+ pageForm.list[i].variable1ID
													+ "</td>";
											tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
													+ pageForm.list[i].variable2ID
													+ "</td>";
											tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
													+ pageForm.list[i].variable3ID
													+ "</td>";
											tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
													+ pageForm.list[i].variable4ID
													+ "</td>";
											tabletr += "<td id='image'  style='display:none' align='center'>"
													+ pageForm.list[i].photoPath
													+ "</td>";
											tabletr += "<td id='logo'   style='display:none' align='center'>"
													+ pageForm.list[i].logoPath
													+ "</td>";
											tabletr += "<td id='acquiesceStandard' align='center'>"
													+ pageForm.list[i].acquiesceStandard
													+ "</td>";
											tabletr += "<td id='model' align='center'>"
													+ pageForm.list[i].model
													+ "</td>";
											/*tabletr += "<td id='goodsID' style='display:none' align='center'>"
													+ pageForm.list[i].goodsID
													+ "</td>";*/
											tabletr += "<td id='standard' align='center'>"
													+ pageForm.list[i].standard
													+ "</td>";

											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02").html(tabletr);
										$("#body_02").show();
										// alert("数据加载成功");
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

					treedw = new dhtmlXTreeObject("dwTree", "100%", "100%", 0);
					treedw.enableDragAndDrop(false);
					treedw.enableHighlighting(1);
					treedw.enableCheckBoxes(0);
					treedw.enableThreeStateCheckboxes(false);
					treedw.setSkin(basePath + 'js/tree/dhx_skyblue');
					treedw.setImagePath(basePath + "js/tree/codebase/imgs/");
					treedw.loadXML(basePath + "js/tree/common/tree_b.xml");
					var getcodeurl = basePath
							+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110224xpd2t2jvda0000000002&date="
							+ new Date().toLocaleString();
					treedw.insertNewChild(0,
							"scode20110224xpd2t2jvda0000000002", "单位类别", 0, 0,
							0, 0);
					$.ajax({
						url : encodeURI(getcodeurl),
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
							var codeList = member.codeList;
							if (null == codeList) {
								return;
							}
							for ( var i = 0; i < codeList.length; i++) {

								treedw.insertNewChild(
										"scode20110224xpd2t2jvda0000000002",
										codeList[i].codeID,
										codeList[i].codeValue, 0, 0, 0, 0);
								treedw.setUserData(codeList[i].codeID,
										"codeNumber", codeList[i].codeNumber);

							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					treedw
							.setOnClickHandler(function() {

								treeid = treedw.getSelectedItemId();
								treename = treedw.getItemText(treeid);

								var typeName = $("input#ccompanyIDs",
										$("table#searchcompany")).val();

								if (treeid == "scode20110224xpd2t2jvda0000000002") {
									treename = "";
								}
								$(":input#dwparms").val(treename);
								cxwldw("contactCompany.companyName=" + typeName
										+ "&cconnection.contactConnections="
										+ treename);
								return;

							});

					var contactcID = "";// 已经添加到往来单位的ID
					var ccID = "";// ccompanyID
					$("table#gostable tr[id]").live("click", function(event) {
						contactcID = this.id;
						ccID = this.title;
						$("input.ra", $(this)).attr("checked", "checked");
					});

					// 选择往来单位
					$(".wanladw")
							.click(
									function() {
										$("#clicktr").val(
												$(this).parent().parent()
														.parent().attr("id"));
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
					$("#qdcompany").click(
							function() {

								if (contactcID != "") {
									var clicktr = $("#line").text();

									$("tr#" + clicktr).find("#ccompanyName")
											.val(
													$("tr#" + contactcID).find(
															"#ccompanyname")
															.text());

									$("tr#" + clicktr).find("#ccompanyID").val(
											contactcID);
									$(".jqmWindow", $("#selectcompanyForm"))
											.jqmHide();

								} else {
									alert("请选择往来单位！");
								}
							});

					// 根据输入的往来单位名称查询
					$("input#searchcc").click(
							function() {
								contactcID = "";
								var typeName = $("input#ccompanyIDs",
										$("table#searchcompany")).val();
								var contactConnections = $(":input#dwparms")
										.val();

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
													"input#ccompanyIDs",
													$("table#searchcompany"))
													.val();
											var contactConnections = $(
													":input#dwparms").val();
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
													"input#ccompanyIDs",
													$("table#searchcompany"))
													.val();
											var contactConnections = $(
													":input#dwparms").val();
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

										if (pageForm == null) {
											alert("没有数据");
											notoken = 0;
											return;
										}

										var dqy = pageForm.pageNumber;// 当前页
										var zys = pageForm.pageCount;// 总页数
										if (dqy > 1) {
											$("#dwsy").attr("title", dqy - 1);
										}
										if (dqy < zys) {
											$("#dwxy").attr("title", dqy + 1);
										}
										$("span#dwzycount").text(zys);
										var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
										tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
										tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
										tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
										
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
											
											tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
													+ pageForm.list[i].ccompanyID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cc").html(tabletr);
										$("#body_02cc").show();
										// alert("数据加载成功");
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
/** **********************************往来个人**************************************** */
// 选择往来个人
$(document)
		.ready(
				function() {
					treegr = new dhtmlXTreeObject("grTree", "100%", "100%", 0);
					treegr.enableDragAndDrop(false);
					treegr.enableHighlighting(1);
					treegr.enableCheckBoxes(0);
					treegr.enableThreeStateCheckboxes(false);
					treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
					treegr.setImagePath(basePath + "js/tree/codebase/imgs/");
					treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
					var getcodeurl = basePath
							+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
							+ new Date().toLocaleString();
					treegr.insertNewChild(0,
							"scode20110106hfjes5ucxp0000000017", "往来个人类别", 0,
							0, 0, 0);
					$.ajax({
						url : encodeURI(getcodeurl),
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
							var codeList = member.codeList;
							if (null == codeList) {
								return;
							}
							for ( var i = 0; i < codeList.length; i++) {

								treegr.insertNewChild(
										"scode20110106hfjes5ucxp0000000017",
										codeList[i].codeID,
										codeList[i].codeValue, 0, 0, 0, 0);
								treegr.setUserData(codeList[i].codeID,
										"codeNumber", codeList[i].codeNumber);

							}
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
					treegr.setOnClickHandler(function() {

						treeid = treegr.getSelectedItemId();
						treename = treegr.getItemText(treeid);

						var typeName = $("input#contactUserID",
								$("table#searchuser")).val();

						if (treeid == "scode20110106hfjes5ucxp0000000017") {
							treename = "";
						}
						$(":input#grparms").val(treename);
						
						var typeCN = "contactUser.staffName=" + typeName
						+ "&contactUser.relation=" + treename;
						
						if($("#xmtype").val()=="043111"){
							typeCN+="&dataIsComplete=00&identifier=baokaixue";
						}
					    
						cxwlgr(typeCN);
						return;

					});

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
					$(".wlgr")
							.click(
									function() {
										
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

					// 添加所选中的往来个人
					$("#qduser").click(
							function() {
								if (cuID != "") {

									var clicktr = $("#line").text();
									$("tr#" + clicktr).find("#connectName")
											.val(
													$("tr#" + cuID).find(
															"#contactUserName")
															.text());
									$("tr#" + clicktr).find("#connectID").val(
											cuID);
									$(".jqmWindow", $("#selectuserForm"))
											.jqmHide();
									$(".wlgr").parent().removeClass("rwl");
									return;
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
								var relation = $(":input#grparms").val();
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
									var relation = $(":input#grparms").val();
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
									var relation = $(":input#grparms").val();
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
												+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th></tr>";
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
											tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
													+ pageForm.list[i].staffID
													+ "</td>";
											tabletr += " </tr>";
										}
										tabletr += " </table>";
										$("#body_02cu").html(tabletr);
										$("#body_02cu").show();
										// alert("数据加载成功")
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

function re_load() {
	if (token){
		document.location.href=basePath+ "/ea/productdesign/ea_toAddOrEditProductDesign.jspa?showType=edit&flexbutton="+flexbutton+"&productDesign.ppID="+ $("input#ppID",$("table#table3")).attr("value");
	//	window.opener.location.href=window.opener.location.href;
	}
}
function showGood() {
	$("#body_02").html("");
	$("#selectType").val("projects");
	$(".jqmWindow", $("#goodsForm")).jqmShow();
}

function close3() {
	$("#jqModel3").hide();
	$("#query").html("");
}

// 生成凭证号
function getBillID() {
	var url = basePath + "ea/costsheet/sajax_ea_getBillIDs.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var billID = member.billID;
			$("#pbillID").val(billID);
			$("#journalNum").val(billID);
		},
		error : function(data) {
			alert("获取超级父项目凭证号失败");
		}
	});

}
/** **********************************价格类别列表操作**************************************** */
//ajax删除产品价格类别列表
function deleteProductPriceCategoryByID(id) {
	
	var url = basePath + "ea/productdesign/sajax_n_ea_deleteProductPriceCategoryByID.jspa?";
	$.ajax({
		url : url,
		type : "get",
		async : true,
		data : {
			"productPriceCategory.pcID" : id,
			"date":new Date().toLocaleString()
		},
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath
                    + "page/ea/not_login.jsp";
            }
			var success = member.success;
			if(success=="success"){
				alert("删除成功!");
			}
		},
		error : function(data) {
			alert("删除失败");
		}
	});
}

$(document).ready(function(){
	//增加
	$("img.addPrice").click(function(){
		selects++;
		$("#kelongOfPrice").before($("#kelongOfPrice").clone(true).show().attr("class","checkgoods").attr("id","kelongOfPrice" + selects));
		$("#kelongOfPrice" + selects).find(':input').each(function() {
			$(this).attr("name","productPriceCategoryMap["+selects+"]." + this.name);
		});
	});
	if(selects<=1){$("img.addPrice").trigger("click");}
	//删除
	$("img.deletePrice").click(function(){
		$(this).parent().parent().remove();	
		var id=$(this).parent().parent().find("input#pcID").attr("value");
		if(id!=""){
			deleteProductPriceCategoryByID(id);
		}
	});
	//编辑
	$("img.editPrice").click(function(){
		$(this).parent().parent().find("input#categoryOther").hide();
		var i=$(this).parent().parent().attr("id").substring(13);
		$(this).parent().parent().find("input#categoryOther").after($("tr#kelongOfPrice").find("select#categoryOther").clone(true).show().attr("name","productPriceCategoryMap["+i+"].category" ));
		$(this).parent().parent().find("select#categoryOther").children("option[value='"+$(this).parent().parent().find("input#categoryOther").attr("value")+"']").attr("selected","selected");
		$(this).parent().parent().find("input#categoryOther").remove();
	});
	/* 计算设计金额
	$(".jisuan").keyup( function() {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var p1=this.id;
				var s=p1.substring(5,p1.length);
				var p = $("input#"+p1,$("div#priceList")).val();
				var dj = $("input#quantity",$("div#priceList")).val();
				if (!isNaN(dj) && !isNaN(p)) {
					var jine = dj * p;
					jine = Math.round(jine * 100) / 100;
					var moneyid="money"+s;
					$("input#"+moneyid,$("div#priceList")).attr("value",jine);
				}
			}
		}
	});*/
	//计算金额 
	$(".jisuan",$("table#productPriceCategory")).live("keyup keydown focus blur",function(event) {
				if (this.value != "") {
					if (isNaN(this.value)) {
						$(this).attr("value","");
					} else {
							var $trThis=$(this).parent().parent();
							var price=parseFloat($.trim($trThis.find("input#price").val())==""?"0.00":$.trim($trThis.find("input#price").val()));
							$(this).parent().parent().find("input#money").attr("value", price.toFixed(2));
					}
				}else{
					$(this).parent().parent().find("input#money,input#price").attr("value", "");
				}
	});
});
/** ***********************键入时查询项目分类**************************** */
function getCate(value) {


	var url = basePath + "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
			+ new Date().toLocaleString();

	$
			.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					parameter : $.trim(value)
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var xmList = member.xmlist;
					var parameter = $("input#parameterxm",$("table#searchxm")).val();
					var params = "parameter="+parameter+"&xmtype=";

					var str = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\");'>所有项目分类</a></span><br/>";

					for ( var i = 0; i < xmList.length; i++) {
						params = "parameter="+parameter+"&xmtype="+xmList[i].codeSn;

							str += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\");'>("
									+ xmList[i].codeSn
									+ ")"
									+ xmList[i].codeValue + "</a></span><br/>";
						
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}
					$(".mohuc").html("");
					$(".mohuc").html(str).show();
					$("#treeg").hide();

				
					

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}



/** ***********************根据项目分类获取项目**************************** */
function getProjectByxmtype(prams) {
	$("#xmsy").attr("title", 0);
	$("#xmxy").attr("title", 0);
	$("#xmzy").attr("title", 0);
	var url = basePath
			+ "ea/costsheet/sajax_ea_getProjectList.jspa?"+prams;

	$
			.ajax({
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
					var pageForm = member.pageForm;
					if (pageForm == null) {
						$("#body_02xm").html("");
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#cdpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#xmxy").attr("title", dqy + 1);
					}
					//
					$("span#xmzycount").text(zys);
					var tabletr="";
        
					for ( var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' title="
								+ pageForm.list[i].proID + " id = "
								+ pageForm.list[i].proID + ">";
						tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
								+ pageForm.list[i].proID
								+ " name='checkradio'/></td>";
						tabletr += "<td align='center'>" + (i + 1) + "</td>";
						tabletr += "<td id='projectCode' align='center'>"
								+ pageForm.list[i].projectCode + "</td>";
						tabletr += "<td id='projectName' align='center'>"
								+ pageForm.list[i].projectName + "</td>";
						tabletr += "<td id='xmtypename' align='center'>"
								+ pageForm.list[i].xmtypename + "</td>";
						tabletr += "<td id='xmtype' style='display:none;'>"
								+ pageForm.list[i].xmtype + "</td>";

						tabletr += "<td  align='center'>"
								+ pageForm.list[i].staffName + "</td>";
						tabletr += "<td  align='center'>"
								+ pageForm.list[i].createName + "</td>";
						tabletr += "<td id='content'  align='center' style='display:none;'>"
								+ pageForm.list[i].content + "</td>";
						tabletr += "<td id='proID'  align='center' style='display:none;'>"
								+ pageForm.list[i].proID + "</td>";
						tabletr += " </tr>";
					}
	
					
					$("#body_02xm").html(tabletr);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}



/** ***********************获取员工**************************** */

function getStaffMember(typeCN) {


	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	
	
	$
			.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";

					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}

					$("span#dpzycount").text(zys);

					for ( var i = 0; i < pageForm.list.length; i++) {

						
							tabletr += "<tr style='cursor: hand;' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
						
						
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";

					}
					
					
					$("#body_02dept").html(tabletr);


				},
				error : function cbf(data) {
					notoken = 0;

				}
			});
}
