$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//
	 if(status=="40"&&billstype=="项目收入预算单"){
		 $(".audit").attr("disabled",true).addClass("grey");
		 $(".JQuerySubmitgd").attr("disabled",true).addClass("grey");
		 $(".addline").attr("disabled",true).addClass("grey");
		 $(".deleteline").attr("disabled",true).addClass("grey");
		 $(".shengcheng").attr("disabled",false).removeClass("grey");
	 }else if(status=="40"&&billstype=="收款单"){
		 $(".audit").attr("disabled",false).removeClass("grey");
		 $(".JQuerySubmitgd").attr("disabled",false).removeClass("grey");
		 $(".addline").attr("disabled",false).removeClass("grey");
		 $(".deleteline").attr("disabled",false).removeClass("grey");
		 $(".shengcheng").attr("disabled",true).addClass("grey");
	 }
	
	 //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
		 window.close();
		 }
		 
	 });
	//打印
	 $(".print").click(function(){
		 window.open(basePath
					+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
					+ cashierBillsID);
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
	
	//处理单价管理
	$(".dis").each(function(){
		var kelongid = $(this).parent().parent().parent().attr("id");
		var id = "";
		if(kelongid!=""&&kelongid.length>6){
			id=kelongid.substring(6);
			$(this).attr("name","logbookmap["+id+"].priceManage");
			$(this).val($(this).parent().find("span").text());
		}
	});

	//物品新增一行
	$(".addline").click(function(){
		var max = 1;
		$("#goodtable").find("tr").each(function(){
			var trid=$(this).attr("id").substring(6);
			if(trid!=""){
				if(trid>max){
					max=Number(trid);
				}
			}
		});
		 $("#kelong").before($("#kelong").
	    		   clone(true).attr("id","kelong"+ (max+1)).addClass("checkgoods"));
		 $("#kelong"+(max+1)).show();
	});
	
	$("#goodtable").find("tr").click(function(){
		$("#line").text($(this).attr("id"));
	});
	
	//物品删除一行
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
		if (types == "goodsNum"||types=="goodsName"||types=="ccompanyName"||types=="connectName") {
			$trs.find("td").each(function(){
				 $("tr#"+kelongid).find("#"+this.id).val($(this).text());
			});
		}
		$("input.rauser", $(this)).attr("checked", "checked");
		$("#goodsQuery").hide();
	});

	// 键入时查询项目分类
	$("#xmtypename").focus(function() {
		getCate($(this));
	}).keyup(function() {
		getCate($(this));
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

	// 选择物品弹出带有物品分类的弹出框
	$("#shuju").click(function() {
		$(".jqmWindow", $("#goodsForm")).jqmShow();
	});

	// 计算金额
	$(".jisuan").live(
			"keyup",
			function(event) {
				if (this.value != "") {
					var dj=0;
					var price=0;
					var jine=0;
					if (isNaN(this.value)) {
						return;
					} else {
						dj = $(this).parent().parent().find(
								":input#quantity").val();
						price = $(this).parent().parent().find(
								":input#price").val();
						if (!isNaN(dj) && !isNaN(price)) {
							jine = dj * price;
							jine = Math.round(jine * 100) / 100;
							$(this).parent().parent().find(
									":input#money").attr("value", jine);
						}
					}

				}
			});

	$(".jisuan").blur(function() {
		var title = "";
		if ($(this).attr("id") == "quantity") {
			title = "数量";
		}
		if ($(this).attr("id") == "price") {
			title = "单价";
		}

		if ($(this).val() == "") {
			alert(title + "不能为空");
			return;
		}

		if (isNaN($(this).val())) {
			alert(title + "必须为数字");
			$(this).attr("value", "");
			return;
		}
		var jj = parseFloat($(this).val());
		if (jj < 0) {
			alert(title + "不能为负数");
			$(this).attr("value", "");
			return;
		}

	});

	$("select#depotType").change(function() {
		var kufang = $(this).val();
		var jie = $(this).parent().parent().find(":input#forLoan");
		var dai = $(this).parent().parent().find(":input#loan");
		var qita = $(this).parent().parent().find(":input#username5");
		var fangxiang = $(this).parent().parent().find("input#direction");
		var qitajine = $(this).parent().parent().find(":input#otherAmount");
		if ($(jie).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(jie).val(), qitajine);
		} else if ($(dai).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(dai).val(), qitajine);
		} else if ($(qita).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(qita).val(), qitajine);
		} else if ($(qitajine).val() != '') {
			jj(kufang, fangxiang, dai, jie, qita, $(qitajine).val(), qitajine);
		} else {
			jj(kufang, fangxiang, "", "", "", "", "");
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
	$(".klsc").click(function() {
		$(this).parent().parent().remove();
		docNull -= 1;
	});

	// 保存预算
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		$(".put3", $("#CostSheetForm")).trigger("blur");
		if ($("form .error").length) {
			alert("请填完所有必填项");
			return;
		}
		$("#CostSheetForm").attr("action",basePath+ "/ea/splitbill/ea_saveCashierBills.jspa?");
		document.CostSheetForm.submit.click();
		alert("保存成功");
		return;
	});
	
	// 生成收款单
	$("input.shengcheng").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		document.location.href=basePath+ "ea/splitbill/ea_generateNewBill.jspa?cashierBills.cashierBillsID="+ cashierBillsID;
		return;
	});

	// 取得部门下拉
	getDeptList();

	// 代码树取消，根据不能的代码树，目前只有一个
	$("input.JQueryreturn1").click(function() {// 取消
		var formID = $($("#formID").attr("value"));
		$("#newccode").jqmHide();
		$(".jqmWindow", formID).jqmShow();
	});

});

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
									if(typeName.indexOf("&pageForm.pageNumber=")>-1){
										var num1=typeName.indexOf("&pageForm.pageNumber=");
										//var num2=typeName.indexOf("&",num1+1);
										typeName=typeName.substring(0,num1);
									}
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
									if(typeName.indexOf("&pageForm.pageNumber=")>-1){
										var num1=typeName.indexOf("&pageForm.pageNumber=");
										//var num2=typeName.indexOf("&",num1+1);
										typeName=typeName.substring(0,num1);
									}
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
						if (type == "staffname") {
							searchurl = basePath
									+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
						}
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
						if (type == "projectName") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getProjectList.jspa?xmtypename="
									+ $("#xmtypename").val() + "&";
						}
						if (type == "bankaccount") {
							searchurl = basePath
									+ "ea/costsheet/sajax_ea_getBankAccountList.jspa?";
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
										if (type == "staffname") {
											thead = "<tr><th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='30' >序号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >人员编号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >人员姓名</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='30' >性别</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='100'>出生日期</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='30' >籍贯</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='30' >民族</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >邮箱</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >手机号 </th>"
													+ "<th align='center' bgcolor='#E4F1FA'  >身份证</th>"
													+ "</tr>";
										}

										if (type == "bankaccount") {
											thead = "<tr><th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='30' >序号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >账号编码</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='120' >银行名称</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='70' >开户账号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='100' >开户时间</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='90' >责任人</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='120' >开户登记证明号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='100' >发证日期</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='100' >账户性质</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='100' >注销时间</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='120' >开户银行地址</th>"
													+ "</tr>";
										}

										if (type == "projectName") {
											thead = "<tr><th  align='center' bgcolor='#E4F1FA' width='3%' >选择</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='3%' >序号</th>"
													+ "<th  align='center' bgcolor='#E4F1FA' width='15%' >项目编号</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='20%' >项目名称</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='8%' >项目分类</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='10%' >项目开始时间</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='10%' >项目结束时间</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='6%' >负责人</th>"
													+ "<th align='center' bgcolor='#E4F1FA' width='6%' >创建人</th>"
													+ "<th align='center' bgcolor='#E4F1FA'  >更新时间 </th>"
													+ "</tr>";
										}

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

											if (type == "staffname") {
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
												tabletr += "<td id='nation'  align='center'>"
														+ pageForm.list[i].nation
														+ "</td>";
												tabletr += "<td id='referenceOrganization'  align='center'>"
														+ pageForm.list[i].referenceOrganization
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
//											
											if (type == "projectName") {
												tabletr += "<tr style='cursor: hand;' title="
														+ pageForm.list[i].proID
														+ " id = "
														+ pageForm.list[i].proID
														+ ">";
												tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
														+ pageForm.list[i].proID
														+ " name='checkradio'/></td>";
												tabletr += "<td align='center'>"
														+ (i + 1) + "</td>";
												tabletr += "<td id='projectCode' align='center'>"
														+ pageForm.list[i].projectCode
														+ "</td>";
												tabletr += "<td id='projectName' align='center'>"
														+ pageForm.list[i].projectName
														+ "</td>";
												tabletr += "<td id='xmtypename' align='center'>"
														+ pageForm.list[i].xmtypename
														+ "</td>";
												tabletr += "<td id='xmtype' style='display:none;'>"
														+ pageForm.list[i].xmtype
														+ "</td>";

												tabletr += "<td align='center'>"
														+ (pageForm.list[i].startDate)
																.substring(0,
																		10)
														+ "</td>";
												tabletr += "<td  align='center'>"
														+ (pageForm.list[i].endDate)
																.substring(0,
																		10)
														+ "</td>";
												tabletr += "<td  align='center'>"
														+ pageForm.list[i].staffName
														+ "</td>";
												tabletr += "<td  align='center'>"
														+ pageForm.list[i].createName
														+ "</td>";
												tabletr += "<td  align='center'>"
														+ pageForm.list[i].updateDate
														+ "</td>";
												tabletr += "<td id='content'  align='center' style='display:none;'>"
														+ pageForm.list[i].content
														+ "</td>";
												tabletr += "<td id='proID'  align='center' style='display:none;'>"
													+ pageForm.list[i].proID
													+ "</td>";
												tabletr += " </tr>";

											}

											if (type == "bankaccount") {
												tabletr += "<tr style='cursor: hand;' title="
														+ pageForm.list[i].bankAccount
														+ " id = "
														+ pageForm.list[i].registrationID
														+ ">";
												tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
														+ pageForm.list[i].registrationID
														+ " name='checkradio'/></td>";
												tabletr += "<td align='center'>"
														+ (i + 1) + "</td>";
												tabletr += "<td id='accountNum' align='center'>"
														+ pageForm.list[i].accountNum
														+ "</td>";
												tabletr += "<td id='bankName' align='center'>"
														+ pageForm.list[i].bankName
														+ "</td>";
												tabletr += "<td id='bankaccount' align='center'>"
														+ pageForm.list[i].bankAccount
														+ "</td>";
												tabletr += "<td id='openAccountDate'  align='center'>"
														+ pageForm.list[i].openAccountDate
														+ "</td>";
												tabletr += "<td id='conPerson' align='center'>"
														+ pageForm.list[i].conPerson
														+ "</td>";
												tabletr += "<td id='bankRegistrationID' align='center'>"
														+ pageForm.list[i].bankRegistrationID
														+ "</td>";
												tabletr += "<td id='registrationDate'  align='center'>"
														+ pageForm.list[i].registrationDate
														+ "</td>";
												tabletr += "<td id='accountNature'  align='center'>"
														+ pageForm.list[i].accountNature
														+ "</td>";
												tabletr += "<td id='cancellationDate'  align='center'>"
														+ pageForm.list[i].cancellationDate
														+ "</td>";
												tabletr += "<td id='bankAddr'  align='center'>"
														+ pageForm.list[i].bankAddr
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
														+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
									});

// 添加所选中的物品到物品单
$("#selectGood").click(function() {
	var clicktr = $("#line").text();
	if ($("[name='check']").is(':checked')) {								
	$("input[name='check']").each(function() {
		if ($(this).is(':checked')) {
			// 选中一行克隆一行
			
			var max = 1;
			$("#goodtable").find("tr").each(function(){
				var trid=$(this).attr("id").substring(6);
				if(trid!=""){
					if(trid>max){
						max=trid;
					}
				}
			});
			select=Number(max)+1;
			alert(select)

			
		// 克隆一行并修改文本框的name

		if ($("tr#"+ clicktr).find("#goodsNum").val() == "") {

			$("#"+ clicktr).before($("#kelong").
					clone(true).attr("id","kelong"+ select).addClass("checkgoods"));

       } else {
           $("#"+ clicktr).after($("#kelong").
    		   clone(true).attr("id","kelong"+ select).addClass("checkgoods"));

		}

		// 修改当前行的所有input的name
		$("#kelong"+ select).find(':input').each(function() {
           $(this).attr("name","goodsmap["+ select+ "]."+ this.name);
          });
			
			
          $("tr#"+ $(this).val()).children("td").each(function() {
        	$("input#"+ this.id,$("#kelong"+ select)).val($(this).text());
        	if (this.title == "ava"&& $(this).text().length != 0) {
				$op = $("<option />");
				$op.attr("value",$(this).text()).text(($(this).text() == "null" || $(this).text() == null) ? null: $(this).text());
				$("select#goodsVariableID",$("#kelong"+ select)).append($op);
			}

		});

		$("tr#kelong"+ select).show();

		$(this).attr("checked",false);
			}
		});
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	} else {
		alert("请选择物品！");
		}
		if ($("tr#" + clicktr).find("#goodsNum").val() != "") {
					$("#" + clicktr).remove();
		}
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
							.click(
									function() {
										
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
						cxwlgr("contactUser.staffName=" + typeName
								+ "&contactUser.relation=" + treename);
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
	//window.close();
	var url = basePath +
	"/ea/splitbill/ea_toSaveCashierBills.jspa?cashierBills.cashierBillsID="+ cashierBillsID;
	parent.document.location.href = url;
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

function jj(kufang, fangxiang, dai, jie, qita, jine, qitajine) {
	if (kufang == '出库') {
		$(jie).attr("value", jine);
		$(qita).attr("value", jine);
		$(fangxiang).attr("value", "贷");
		$(dai).val("");
		$(qitajine).val("");
	} else if (kufang == '入库') {
		$(dai).attr("value", jine);
		$(qita).attr("value", jine);
		$(fangxiang).attr("value", "借");
		$(jie).val("");
		$(qitajine).val("");
	} else if (kufang == '库存') {
		$(fangxiang).attr("value", "库存");
		$(qita).attr("value", jine);
		$(jie).val("");
		$(dai).val("");
		$(qitajine).val("");
	} else if (kufang == '其它') {
		$(qitajine).attr("value", jine);
		$(fangxiang).attr("value", "其它");
		$(qita).val("");
		$(jie).val("");
		$(dai).val("");
	}
}

/** ***********************项目分类**************************** */
// 键入时查询项目分类
function getCate(obj) {

	var left = $(obj).position().left;
	var top = $(obj).position().top;
	var url = basePath + "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
			+ new Date().toLocaleString();

	$
			.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					parameter : $.trim($(obj).val())
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var xmList = member.xmlist;

					var str = "";

					for ( var i = 0; i < xmList.length; i++) {
						if (xmList[i].isLeaf == "00") {
							str += "&nbsp;&nbsp;<span><a href='#' style='color:royalblue;' onclick='selectz(this);'>("
									+ xmList[i].codeSn
									+ ")"
									+ xmList[i].codeValue + "</a></span><br/>";
						} else {
							str += "&nbsp;&nbsp;<span style='color:#000000;'>("
									+ xmList[i].codeSn + ")"
									+ xmList[i].codeValue + "</span><br/>";
						}
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}

					$("#treeBoxs").html(str);

					$("#jqModel").css({
						"width" : "300px",
						"height" : "350px",
						"overflow" : "auto",
						"background-color" : "#e1ecfc",
						"border" : "1px solid #a8c7ce",
						"position" : "absolute",
						"left" : left,
						"top" : top + 25
					}).show();
					$("#goodsQuery").hide();

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}
// 选中一个项目分类
function selectz(obj) {
	$("#jqModel").hide();
	var codeValues = $(obj).text();
	var xmtypename = codeValues.substring(codeValues.indexOf(")") + 1);
	var xmtype = codeValues.substring(codeValues.indexOf("(") + 1, codeValues
			.indexOf(")"));
	if (xmtype != $("#xmtype2").val()) {
		$("#proID").val("");
		$("#projectName").val("");
		$("#projectCode").val("");
		$("#content").val("");
	}
	$("#xmtypename").val(xmtypename);
	$("#xmtype").val(xmtype);

}
/** ***********************项目分类结束**************************** */

// 取得部门下拉列表（在前面加载页面时调用了）
function getDeptList() {
	$("span#companyNames").text(gongsiname);
	var treeName = treeNames;
	var treePID = treeID;
	var treePName = treeNames;
	if (zorg != "") {
		treeID = zorg;
		treeName = zorgname;
	}

	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(cc) {
			var member = eval("(" + cc + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var oList = member.organizationlist;
			var data = new Array();
			data[0] = {
				id : treeID,
				pid : '-1',
				text : treeName
			};
			var data1 = new Array();
			data1[0] = {
				id : treePID,
				pid : '-1',
				text : treePName
			};
			for ( var i = 0; i < oList.length; i++) {
				data[i + 1] = {
					id : oList[i].organizationID,
					pid : oList[i].organizationPID,
					text : oList[i].organizationName
				};
				data1[i + 1] = {
					id : oList[i].organizationID,
					pid : oList[i].organizationPID,
					text : oList[i].organizationName
				};
			}
			$t = $("div#content1");
			var ts = new TreeSelector($t.find("select#departmentID")[0], data,
					-1);
			ts.createTree();
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}

// //////////////////////////代码管理开始///////////////////////////////////////////////////////////////////////////
function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var code = member.code;
			$("#newccode").jqmHide();
			$op = $("<option/>");
			$op.attr("value", code.codeValue).text(code.codeValue);
			$(selectID, formID).append($op);
			alert("操作成功！");
			$(".jqmWindow", formID).jqmShow();
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

// //////////////////////////代码管理结束///////////////////////////////////////////////////////////////////////////
/** ---------------------------------------------------------选择多个人员---------------------------------------------* */
/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;

			var str = "<option value=''>请选择公司</option>";
			for ( var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#jqModelSend select#receiverCompanyID").html(str);
			$("#jqModelSend #receiverDeptID").html(
					"<option value=''>请选择部门</option>");
			$("#jqModelSend #receiverID").html(
					"<option value=''>请选择人员</option>");
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdept(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"companyID" : val
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var orgaizationlist = member.orgaizationlist;
			var str = "<option value=''>请选择部门</option>";
			for ( var i = 0; i < orgaizationlist.length; i++) {
				var obj = orgaizationlist[i];
				str += "<option value='" + obj.organizationID + "'>"
						+ obj.organizationName + "</option>";
			}
			$("#jqModelSend #receiverDeptID").html(str);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPerson(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"currentCompanyID" : company,
			"checkOrgID" : org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>请选择人员</option>";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj.staffID + "'>" + obj.staffName
						+ "(" + obj.staffCode + ")</option>";
			}
			$("#jqModelSend #receiverID").html(str);
		}
	});
}
/**
 * 
 * 当公司改变时，获取部门
 * 
 * @param {}
 *            obj
 */
function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#jqModelSend #receiverDeptID").html(
				"<option value=''>请选择部门</option>");
	}
}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	if ($(obj).val() != '') {
		getPerson($("#jqModelSend #receiverCompanyID").val(), $(obj).val());
	} else {
		$("#jqModelSend #receiverID").html("<option value=''>请选择人员</option>");
	}
}

/**
 * 
 * 提交审核
 */
function submitExamine() {

	if ($("#receiverID").val() == "") {
		alert("请选择审核人");
		return;
	}

	$("#examineComName").val(
			$("#receiverCompanyID").find("option:selected").text());
	$("#examineorgName").val(
			$("#receiverDeptID").find("option:selected").text());

	$("#examineName").val($("#receiverID").find("option:selected").text());
	$("#examinecsbID").val(cashierBillsID);
	$("#sendForm").attr("action",
			basePath + "ea/costsheet/ea_zbqSubmitExamine.jspa");
	document.sendForm.submit.click();
	alert("操作成功");
	
}

