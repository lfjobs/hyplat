$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//



	
	$(".connectName").live("click",function(){
		if($(this).val()!=""){
			var connectID = $(this).parent().find("#connectID").val();
			var url = basePath+"ea/costsheet/sajax_ea_getStaffIdByConenetID.jspa?date="
			+ new Date().toLocaleString();
			
			$
			.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data:{
				  "staff.staffID":connectID
				 
				},
				success : function (data) {
					var member = eval("(" + data + ")");
					getStaffInfo(member.staffID);
				},
				error:function(data){
					alert("获取数据失败");
				}
			});
			
		}
		
	});

	if(billsType=="项目收入预算单"){
	   $("#titlestyle").find("span").text("项目收入预算单");
	   $(".audit").val("确定预算");
	}else{
		 $("#titlestyle").find("span").text("项目支出预算单");
		 $(".audit").val("比价审核");
	}
//	$("#titlestyle").find("input").val(billsType);
//	$("#billstypes").val(billsType);
	
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
		
		select++;
		if($("#linet").val()!=""){
		var $tbl = $("#"+$("#linet").val());
		 $("#kelong",$tbl).before($("#kelong",$tbl).
	    		   clone(true).attr("id","kelong"+ select).addClass("checkgoods"));
		 $("#kelong"+select,$tbl).show();
		}
	});
	
	$(".datatbl").find("tr").live("click",function(){
		$("#linet").val($(this).parent().parent().attr("id"));

		$(this).parent().find("#line").text($(this).attr("id"));

		
		
	});
	
	//物品删除一行
	
	$(".deleteline").click(function(){
		if($("#linet").val()!=""){
		var $tbl = $("#"+$("#linet").val());
		if($("#line",$tbl).text()!=""){
		$("#"+$("#line",$tbl).text()).remove();
		}
		}
	});
	
	
	// 物品表格单击样式问题
	$(".datatbl :input").live("click",function() {
		$(".datatbl td").find("div").each(function() {
			$(this).css("border", "none");
			$(this).find(".caz").hide();

		});
		$(this).parent("div").css("border", "1px solid #000000");
		$(this).parent("div").find(".fou").focus();
		$(this).parent("div").find(".caz").show();

	});
	$(".datatbl td").live("click",function() {
		$(".datatbl td").each(function() {
			$(this).find("div").css("border", "none");
			$(this).find(".caz").hide();

		});

		$(this).find("div").css("border", "1px solid #000000");
		$(this).find(".fou").focus();
		$(this).find(".caz").show();

	});

	// 责任人,项目名称，银行选中查询结果
	$("#goodsQuery tr[id]").live("click", function(event) {
		alert(333);
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

	// 计算金额
	$(".jisuan").live(
			"keyup",
			function(event) {
				if (this.value != "") {
					if (isNaN(this.value)) {
						return;
					} else {
						var dj = $(this).parent().parent().parent().find(
								":input#quantity").val();
						var price = $(this).parent().parent().parent().find(
								":input#price").val();
						if (!isNaN(dj) && !isNaN(price)) {
							var jine = dj * price;
							jine = Math.round(jine * 100) / 100;
							$(this).parent().parent().parent().find(
									":input#money").attr("value", jine);
						}
					}

				}
			});
	
	
	// 计算金额
	$(".jisuan").live(
			"blur",
			function(event) {
				if (this.value != "") {
					if (isNaN(this.value)) {
						return;
					} else {
						var dj = $(this).parent().parent().parent().find(
								":input#quantity").val();
						var price = $(this).parent().parent().parent().find(
								":input#price").val();
						if (!isNaN(dj) && !isNaN(price)) {
							var jine = dj * price;
							jine = Math.round(jine * 100) / 100;
							$(this).parent().parent().parent().find(
									":input#money").attr("value", jine);
						}
					}

				}
			});





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

	// 克隆的商品删除
	$(".klsc").live("click",function() {
		$(this).parent().parent().remove();
		
	});

	// 保存预算
	$("input.JQuerySubmitgd")
			.click(
					function() {
						if (notoken) {
							alert("正在提交数据！请稍等");
							return;
						}
						
						
						$(".notnull", $("#CostSheetForm")).trigger("blur");
						//$(".posnumred", $("#CostSheetForm")).trigger("blur");
                        if ($(".linetable .error").length) {
                            alert("请填完所有必填项");
                            return;
                        }
						if($(".cs").length<1){
                            alert("请添加物品清单");
                            return;
						}

                        if($(".cs .error").length){
                            alert("请填完所有必填项");
                            return;
                        }

						$("#CostSheetForm")
								.attr(
										"action",
										basePath
												+ "/ea/costsheet/ea_saveCostSheet.jspa");
						document.CostSheetForm.submit.click();
						token = 0;
			            alert("操作成功");
			          window.opener.refress();
			            $("#CostSheetForm")
						.attr(
								"action","");
			            
			            
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
														+ "ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua&fiveClear=4");
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
								if (cID != "") {

									$("#xmtypename").val($("tr#" + cID).find("#xmtypename")
											.text());
									$("#xmtype").val($("tr#" + cID).find("#xmtype")
											.text());
									if($("#xmtype").val()=="043111"){
										$(".baokx").show();
									}else{
										$(".baokx").hide();
									}

									$("#projectName").val(
											$("tr#" + cID).find("#projectName")
													.text());
									$("#proID").val(cID);
									$("#projectCode").val(
											$("tr#" + cID).find(
											"#projectCode")
											.text());
									$(".jqmWindow", $("#selectxmForm"))
											.jqmHide();
									
									
									//准备项目产品树
									getProjectByParent(cID,$("tr#" + cID).find("#projectName")
											.text());
									
									

									return;
								} else {
									alert("请选择项目！");
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
					.live("click",
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
									var $tbl = $("#"+$("#linet").val());
									var clicktr = $("#line",$tbl).text();
									$("#"+clicktr,$tbl).find("#targetDeptName").val(selectdeptname);
									$("#"+clicktr,$tbl).find("#targetDeptID").val(selectdept);
									
									$("#"+clicktr,$tbl).find("#targetSalerName").val($("tr#" + cID).find(
											"#staffname").text());
									$("#"+clicktr,$tbl).find("#targetSalerID").val($("tr#" + cID).find(
											"#staffid").text());

								}
									$(".jqmWindow", $("#selectdeptForm"))
											.jqmHide();
								/****************计件工资*****************/
//									if("013415" == $("#xmtype").val()){
//										getCofjjByI();
//									}
								/****************end*****************/
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
					$(".shuju").live("click",
							function() {
                               if($("#xmtypename").val()==""){
                            	   alert("请选选择项目");
                            	   return;
                               }
								$("#body_02").html("");
								$("#selectType").val("goods");
								$(".jqmWindow", $("#goodsForm")).jqmShow();
                                notoken = 0;
                                cx("parameter=");
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
												tabletr += "<td id='goodsID' style='display:none' align='center'>"
													+ pageForm.list[i].goodsID
													+ "</td>";
						
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
														+ "page/ea/main/navigation/product_procedure.jsp?fiveClear=4");
									});

// 添加所选中的物品到物品单
$("#selectGood").click(function() {
	var ppID = $("#linet").val().substring(0,$("#linet").val().length-3);
	
	var $tbl = $("#"+$("#linet").val());
	var clicktr = $("#line",$tbl).text();
	if ($("[name='check']").is(':checked')) {								
	$("input[name='check']").each(function() {
		if ($(this).is(':checked')) {
			// 选中一行克隆一行
			select++;

		// 克隆一行并修改文本框的name

		if ($("tr#"+ clicktr, $tbl).find("#goodsNum").val() == "") {

			$("#"+ clicktr,$tbl).before($("#kelong").
					clone(true).attr("id","kelong"+ select).addClass("checkgoods"));

       } else {
       $("#"+ clicktr,$tbl).after($("#kelong").
    		   clone(true).attr("id","kelong"+ select).addClass("checkgoods"));

		}
		$("#kelong"+ select,$tbl).find("#ppID").val(ppID);
		// 修改当前行的所有input的name
		$("#kelong"+ select,$tbl).addClass("cs")
		$("#kelong"+ select,$tbl).find(':input').each(function() {

			 if(this.name=="quantity"||this.name=="price"||this.name=="targetEnd"||this.name=="targetStart"){
                 $(this).addClass("notnull");
			 }

           $(this).attr("name","logbookmap["+ select+ "]."+ this.name);

          });
			
			
          $("tr#"+ $(this).val()).children("td").each(function() {
        	$("input#"+ this.id,$("#kelong"+ select,$tbl)).val($(this).text());
        	if (this.title == "ava"&& $(this).text().length != 0) {
				$op = $("<option />");
				$op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
				$("select#goodsVariableID",$("#kelong"+ select,$tbl)).append($op);
			}

		});

		$("tr#kelong"+ select,$tbl).show();

		$(this).attr("checked",false);
			}
		});
	
		$(".jqmWindow", $("#goodsForm")).jqmHide();
		//如果规则多条无法处理，是否单独选择（规则连带查询）
		/*** 基本、职务、考评、考勤、奖惩提成提成*****/
//		if($("#xmtype").val().indexOf("01")==0){
//			getCofiPunish("kelong"+select);
//		}
		/****************end*****************/
	} else {
		alert("请选择物品！");
		}
		if ($("tr#" + clicktr).find("#goodsNum").val() != "") {
					$("#" + clicktr).remove();
		}
	/****************计件工资*****************/
//	if("013415" == $("#xmtype").val()){
//		getCofjjByI();
//	}
	
	/****************end*****************/


     $("#typeID",
        $("table#searchgood")).val("");
		
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
												+ "<th align='center' bgcolor='#E4F1FA'>条码号</th>";
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
                                            tabletr += "<td id='barCode'  align='center'>"
                                                + pageForm.list[i].barCode
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
											tabletr += "<td id='acquiesceStandard' align='center'>"
													+ pageForm.list[i].acquiesceStandard
													+ "</td>";
											tabletr += "<td id='model' align='center'>"
													+ pageForm.list[i].model
													+ "</td>";
											tabletr += "<td id='goodsID' style='display:none' align='center'>"
													+ pageForm.list[i].goodsID
													+ "</td>";
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
							.live("click",
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
							.live("click",
									function() {
										window
												.open(basePath
														+ "ea/contactcompany/ea_getListContactCompany.jspa?");
									});

					// 添加所选中的往来单位
					$("#qdcompany").click(
							function() {

								if (contactcID != "") {
									var $tbl = $("#"+$("#linet").val());
									var clicktr = $("#line",$tbl).text();

									$("tr#" + clicktr,$tbl).find("#ccompanyName")
											.val(
													$("tr#" + contactcID).find(
															"#ccompanyname")
															.text());

									$("tr#" + clicktr,$tbl).find("#ccompanyID").val(
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
													+ pageForm.list[i].ccompanyID
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
							.live("click",
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

									var $tbl = $("#"+$("#linet").val());
									var clicktr = $("#line",$tbl).text();
									$("tr#" + clicktr,$tbl).find("#connectName")
											.val(
													$("tr#" + cuID).find(
															"#contactUserName")
															.text());
									$("tr#" + clicktr,$tbl).find("#connectID").val(
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
	window.close();
	// var url = basePath +
	// "/page/ea/main/finance/invoicing/left_frame.jsp?jumptype=00&type=00";
	// parent.document.location.href = url;
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






/** ***********************项目分类模糊查询**************************** */
//键入时查询项目分类
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



//根据项目分类获取项目
function getProjectByxmtype(prams) {
	$("#xmsy").attr("title", 0);
	$("#xmxy").attr("title", 0);
	$("#xmzy").attr("title", 0);
	var url = basePath
			+ "ea/costsheet/sajax_ea_getProjectList.jspa?"+prams+"&dat="+Math.random();

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
								+ pageForm.list[i].ppID + " id = "
								+ pageForm.list[i].ppID + ">";
						tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
								+ pageForm.list[i].ppID
								+ " name='checkradio'/></td>";
						tabletr += "<td align='center'>" + (i + 1) + "</td>";
						tabletr += "<td id='projectCode' align='center'>"
								+ pageForm.list[i].goodsNum + "</td>";
						tabletr += "<td id='projectName' align='center'>"
								+ pageForm.list[i].goodsName + "</td>";
						tabletr += "<td id='xmtypename' align='center'>"
								+ pageForm.list[i].xmtypename + "</td>";
						tabletr += "<td id='xmtype' style='display:none;'>"
								+ pageForm.list[i].xmtype + "</td>";

						tabletr += " </tr>";
					}
	
					
					$("#body_02xm").html(tabletr);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}
/** ***********************项目分类结束**************************** */


// 获取员工
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


function getProjectByParent(ppID,parentName){
	$("#xmul").html("");
	var url = basePath+"ea/costsheet/sajax_ea_getProjectByParent.jspa?date="
				+ new Date().toLocaleString();
	$
	.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{
		  "productPack.ppID":ppID
		},
		success : function (data) {
			var member = eval("(" + data + ")");
			var oList = member.productlist;
		
	
			var data1 = new Array();

			for (var i = 0; i < oList.length; i++) {
				data1[i] = {
						id : oList[i][0],
						pid : oList[i][1],
						text : oList[i][2]
				};
			}

			var ts = new TreeSelector($("ul#xmul"),
					data1, null);
			ts.createTree();
			
			$("#xmul").treeview({
	            collapsed: false,
	            unique: false
	        });
			
			$("#xmul").find("span").each(function(){


			var $newtable = $("#Layer1").clone();
			$newtable.find("table").attr("id",$(this).parent().attr("id")+"tbl").addClass("datatbl");
			$(this).after($newtable.html());
           });

            $("#kelong1").find("#barCode").click().addClass("active");
		},error:function(data){
			alert("获取项目失败");
		}
		});

}
/**
 * 计件提成
 */
function getCofjjByI(){
	var $tbl = $("#"+$("#linet").val());
	var clicktr = $("#line",$tbl).text(); //选中行id
	var tblid = $tbl.attr("id");		//tableidkelong1
	

	var vali = $("#" + clicktr).find("input#goodsID").val() ;//品名名称
	var valii = $("#" + clicktr).find("input#targetDeptID").val() ;//部门ID
	var valiii = $("#" + clicktr).find("input#targetSalerID").val() ;//目标业务员ID
	
	if(vali != "" && valii != "" && valiii != "" ){
		//计件Action WageCofJJAction
		var url = basePath + "ea/cofjj/sajax_ea_getCofjjByI.jspa?date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				"wcofjj.goodsName" : vali,//品名名称
				"wcofjj.deppostID" : valii,//部门ID
				"wcofjj.companyID" : valiii,//目标业务员ID
				"wcofjj.cofJjID" : tblid,  //阶段id
				"wcofjj.cjTcxsT": $("input#projectName").val() //项目名称
			},
			success : function cbf(data) { 
				var member = eval("(" + data + ")");
				var jj = member.jj;
				if(jj.cofJjKey != ""){
					if(jj.cjState == "0"){
						$("#" + clicktr).find("input#price").attr("value",jj.cjTcxs);
					}else{
						var pri = jj.cjProPrice * jj.cjTcxs /100;
						$("#" + clicktr).find("input#price").attr("value",pri);
					}
					var dj = $("#" + clicktr).find("input#quantity").val();
					var price = $("#" + clicktr).find("input#price").val();
					if (!isNaN(dj) && !isNaN(price)) {
						var jine = dj * price;
						jine = Math.round(jine * 100) / 100;
						$("#" + clicktr).find("input#money").attr("value", jine);
					}
				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}
}
/**
 *  基本、职务、考评、考勤、奖惩提成
 */
function getCofiPunish(e){
	
	var clicktr = e; //选中行id
	var contype = $("#xmtype").val();
	var goodsName = $("#" + clicktr).find("input#goodsName").val() ;//品名名称
	
	if(goodsName != ""){
		//Action WageCofiPunish
		var url = basePath + "ea/cofipunish/sajax_ea_getCofiPunish.jspa?contype=" + contype
			+ "&gbs.goodsName=" +goodsName+ "&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			success : function cbf(data) { 
				var member = eval("(" + data + ")");
				var cofi = member.cofi;
				$("#" + clicktr).find("input#price").attr("value", cofi.price);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}
}

//查看人员详细信息
function getStaffInfo(personvalue){
	var url = basePath
	+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
	+ personvalue;
window.open(url);
	
}


//查看部门详情 parentid父部门，treeid当前部门ID
function getDeptInfo(){
	var treeid=$("#departmentID").val();
	var parentid =  "";
	var parentname = "";
	var url = basePath+"ea/costsheet/sajax_ea_getParentOrg.jspa?date="
	+ new Date().toLocaleString();
	$
	.ajax({
		url : url,
		type : "get",
		async : false,
		dataType : "json",
		data:{
		  "cashierBills.departmentID":treeid
		 
		},
		success : function (data) {
			var member = eval("(" + data + ")");
		     parentid = member.parentid;
		     parentname=member.parentname;
		},
		error:function(data){
			alert("获取数据失败");
		}
	});
	
	
window.open(basePath + "ea/corganization/ea_toAdd.jspa?pageNumber=0&porganization.organizationID="
	 + parentid + "&porganization.organizationName=" + parentname + "&organization.organizationID="
	 + treeid);


}

$(function(){
	$(".digital").live("keydown",function(e){
		if(!((e.keyCode>47&&e.keyCode<58)||e.keyCode==8
				||e.keyCode==110||(e.keyCode>95&&e.keyCode<106))){
				return false;
			}
		if(e.keyCode==110){
			var str=$(this).val();
			var zz=/^[\w]*[\.]/;
			if(zz.test(str)||str=="")
				return false;
		}
	});
});



//暂时没用
function showDataByBarCode (){

    var ppID = $("#linet").val().substring(0,$("#linet").val().length-3);

    var $tbl = $("#"+$("#linet").val());
    var clicktr = $("#line",$tbl).text();
        $(".ragood").each(function() {
                // 选中一行克隆一行
                select++;

                // 克隆一行并修改文本框的name

            $("#"+ clicktr,$tbl).before($("#kelong").
            clone(true).attr("id","kelong"+ select).addClass("checkgoods"));

            $("#kelong"+ select,$tbl).find("#ppID").val(ppID);
                // 修改当前行的所有input的name
                $("#kelong"+ select,$tbl).addClass("cs")
                $("#kelong"+ select,$tbl).find(':input').each(function() {

                    if(this.name=="quantity"||this.name=="price"||this.name=="targetEnd"||this.name=="targetStart"){
                        $(this).addClass("notnull");
                    }

                    $(this).attr("name","logbookmap["+ select+ "]."+ this.name);

                });


                $("tr#"+ $(this).val()).children("td").each(function() {
                    $("input#"+ this.id,$("#kelong"+ select,$tbl)).val($(this).text());
                    if (this.title == "ava"&& $(this).text().length != 0) {
                        $op = $("<option />");
                        $op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
                        $("select#goodsVariableID",$("#kelong"+ select,$tbl)).append($op);
                    }

                });

                $("tr#kelong"+ select,$tbl).show();

                $(this).attr("checked",false);

        });



    if ($("tr#" + clicktr).find("#goodsNum").val() != "") {
        $("#" + clicktr).remove();
    }
}