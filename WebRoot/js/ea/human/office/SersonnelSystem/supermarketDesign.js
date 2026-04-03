
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if(flexbutton == 'flexbutton'){
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : '产品设计推广管理',
			minheight : 80,
			buttons : [{
				name : '查询',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});
	}else if(flexbutton == 'yongjin' ){
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			checkbox : true,// 是否要多选框  
			title : "超市产品佣金设计<form name='yjForm' id='yjForm' method='post'>" +
			"<input type='submit' name='submit' style='display: none' />" +
					"商品名称：" +
					"<input name='productDesign.goodsName' />" +
					"<input type='button' id='tosearch2'	value=' 查询 ' />" +
					"<input name='search' type='hidden' value='search' />" +
					"<input name='fiveClear' type='hidden' id='fiveClear' value='"+fiveClear+"' /></form>",
			minheight : 80,
			buttons : [{
				name : '佣金设计添加',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, /*{
				separator : true
			},{
				name : '佣金比例设计',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
                name : '二维码收款',
                bclass : 'add',
                onpress : action
                // 当点击调用方法
            }, */{
                separator : true
            }, {
				name : '修改佣金',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '查看',
				bclass : 'see',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
            }]
	});
	}else if(flexbutton == 'jiage' ){
		var searchFormHtml=$("div#jqModelSearch").html();
		$("div#jqModelSearch").remove();
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : '产品价格管理'+searchFormHtml,
			minheight : 80,
			buttons : [{
				name : '设置产品价格',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});
		}
	
	else if(flexbutton == 'publish'){
		var searchFormHtml=$("div#jqModelSearch").html();	
		$("div#jqModelSearch").remove();
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : '产品发布管理'+searchFormHtml,
			minheight : 80,
			buttons : [{
				name : '产品发布',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});
	}else if(flexbutton == 'chanphuizong'){	
		var searchFormHtml=$("div#jqModelSearch").html();
		$("div#jqModelSearch").remove();
		
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : '产品发布完成汇总',
			minheight : 80,
			buttons : [{
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '产品下架',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});
		
	}
	else if(flexbutton == 'type')
	{		
		var searchFormHtml=$("div#jqModelSearch").html();	
		$("div#jqModelSearch").remove();
		$('.address').flexigrid({
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : '产品发布',
			minheight : 80,
			buttons : [
			           {
				name : '产品发布',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}
			
			]
		});
		
	}	
	else{
		var searchFormHtml=$("div#jqModelSearch").html();
		$("div#jqModelSearch").remove();
		$('.address').flexigrid({
			height : 120,
			width : 'auto',
			minwidth : 30,
			title : identifier=='identifier'?'产品销售报表':'产品设计推广管理'+searchFormHtml,
			minheight : 80,
			buttons : ghua=="ghua"?([{
				name : '添加',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},  {
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
				name : '在线编辑',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '签订合同',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '招标打印',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				},{
					separator : true
				},{
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
			},
			{
					name : '转移',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
					]):([{
				name : '添加',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},  {
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
				name : '在线编辑',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
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
			},{
					name : '转移',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}])
		});
	}
	
	$("div.bDiv",$("div.flexigrid")).css("height","410px");	
	
	function action(com, grid) {
		var fiveClear = $("#fiveClear").val();
		switch (com) {
			case '添加' :
				if(identifier=="identifier"){//区分页面跳转 客户分类报表
					var url = basePath
						+ "ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType=add";
					window.open(url, '','scrollbars=yes,resizable=yes,channelmode'); 
					return
				}
			
				window.open(basePath
						+ "/ea/productdesign/ea_toAddOrEditPercentageDesign.jspa?fiveClear="+fiveClear);
				break;
			case '修改' :
				if (productdesignID == "") {
					alert('请选择!');
					return;
				}
				window.open(basePath
						+ "/ea/productdesign/ea_toAddOrEditProductDesign.jspa?showType=edit&productDesign.ppID="+ productdesignID+"&fiveClear="+fiveClear);
				break;	
			case '设置产品价格':
				if (productdesignID == "") {
					alert('请选择!一条产品在设置价格');
					
					return;
				}
				var url=basePath+"/ea/productdesign/ea_getProductdesignByParam.jspa?ppID="+ productdesignID;
				window.open(url);
				
				break;
				
			case '在线编辑' :
				if (productdesignID == "") {
					alert('请选择!');
					return;
				}
				if(identifier=="identifier"){//区分页面跳转 客户分类报表
					var url = basePath
						+ "ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType=edit&productDesign.ppID="
						+ productdesignID + "&editType=html";					
					window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
					return
				}else{
					var url = basePath
					+ "ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType=edit&priceType=type&productDesign.ppID="
					+ productdesignID + "&editType=html";					
					window.open(url, '','scrollbars=yes,resizable=yes,channelmode,width=910,left=250,top=100');
					return
				}
				break;
			case '删除' :
				if (productdesignID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#ppID').val(productdesignID);
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/productdesign/ea_delProductdesign.jspa?pageNumber="
											+ pNumber + "&search=" + search+"&sdate="+sdate+"&edate="+edate);
					document.cstaffForm.submit.click();
					$("tr#" + productdesignID).remove();
					productdesignID = "";
					token = 11;
				}
				break;
				
			case '招标打印':
				if (productdesignID== "") {
					alert("请选择！");
					return;
				}
				
				open(basePath
						+ "/ea/costsheet/ea_projectPrint.jspa?productPack.ppID="
						+ productdesignID);

				break;
				
			case '产品下架':
				if (productdesignID== "") {
					alert("请选择！");
					return;
				}
				if(confirm("确定将产品下架？")){
					$("#addressForm").attr("target","hidden").attr("action",basePath+"ea/productdesign/ea_productOffLine.jspa?productDesign.ppID="
						+ productdesignID);
					document.addressForm.submit.click();
					token = 2;
				}
				
				break;
			case '签订合同':
				if (productdesignID == "") {
					alert("请选择！");
					return;
				}
				
				var goodsName = $("span#goodsName", $("tr#" + productdesignID)).text();
				document.location.href = basePath
						+ "/ea/promanage/ea_getContractByProject.jspa?productPack.ppID="
						+ productdesignID + "&projectName=" + goodsName+"&ghua="+ghua;
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '转移' :
				if (productdesignID == "") {
					alert('请选择!');
					return;
				}
				$("#jqModelTransfer").jqmShow();
				

				break;
		
			case '设置每页显示条数' :

				var url = basePath
						+ "ea/productdesign/ea_getListProductdesign.jspa?search="+search+"&sdate="+sdate+"&edate="+edate+"&identifier="+identifier+"&ghua="+ghua+"&flexbutton="+flexbutton+"&fiveClear="+fiveClear+"&devide="+devide+"&no="+no+"&typeString="+typeString;
				numback(url);
				break;
			case '打印预览' :
				url = basePath+ "ea/productdesign/ea_toProPrint.jspa";
				open(url);
				break;	
			
			case '产品发布' :
				if (productdesignID== "") {
					alert("请选择！");
					return;	
				}
				var parm=new Array();
				parm.push("companyId="+companyId);
				$.ajax({
					url:basePath+"ea/refund/sajax_ea_isAllow.jspa",
					type:"get",
					data:parm.join(""),
					success:function(data){
						if(data!=null&&data.length>0){
							alert(data);
						}else{		
							if(confirm("确定将产品发布？")){
								$("#addressForm").attr("target","hidden").attr("action",basePath+"ea/productdesign/ea_productOffLine.jspa?productDesign.ppID="
									+ productdesignID+"&productDesign.showweixin=01");
								document.addressForm.submit.click();
								token = 2;
							}

						}
						
					}
					
				});
				
				

				break;

			case '佣金设计添加' :
				window.open(basePath
						+ "/ea/productdesign/ea_toAddOrEditPercentageDesign.jspa?fiveClear="+fiveClear+"&flexbutton="+flexbutton+"&addOrEdit=add");
				break;
			case '佣金比例设计' :
				window.open(basePath+"ea/design/ea_selCommissionList.jspa");
				break;
			case '修改佣金':
				if (opertionID== "") {
					alert("请选择！");
					return;	
				}
				window.open(basePath
						+ "/ea/productdesign/ea_toAddOrEditPercentageDesign.jspa?fiveClear="+fiveClear+"&flexbutton="+flexbutton+"&productDesign.ppID="+opertionID+"&addOrEdit=edit&showType=editYj");
				break;
			case '查看':
				if (opertionID== "") {
					alert("请选择！");
					return;	
				}
				see();
				break;
            case '二维码收款' :
                window.open(basePath
                    + "/ea/productslaunch/ea_getErWeiMa.jspa?companyId="+companyId+"&addOrEdit=add");
                break;
		};
	}
	$("table tr[id]").click(function() {
		        opertionID = this.id;
				productdesignID = this.id ;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/productdesign/ea_toSearch.jspa?pageNumber="
						+ pNumber+"&identifier="+identifier+"&flexbutton="+flexbutton+"&ghua="+ghua);
		document.postSearchForm.submit.click();
	});
	
	$("#tosearch2").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#yjForm").attr(
				"action",
				basePath + "ea/productdesign/ea_toSearch.jspa?pageNumber="
						+ pNumber+"&identifier="+identifier+"&flexbutton="+flexbutton+"&ghua="+ghua);
		document.yjForm.submit.click();
	});
	
	
	$(".address tr[id]").dblclick(function() {
		see();
	});

	
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		var hidIdList=$("#hidIdList").val();
		$t = $("table#stafftable");
		if ($("input#goodsID", $t).attr("value") == "") {
			alert("请选择物品！");
			return;
		}
		$(".put3", $("#jqModel")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请正确操作");
			notoken = 0;
			return;
		}
		$(".isNaN").trigger("blur")	;
		if (productdesignID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/productdesign/ea_addProductdesign.jspa?pageNumber="+ pNumber+"&hidIdList="+hidIdList);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			window.close();
			token = 1;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/productdesign/ea_addProductdesign.jspa?pageNumber="
								+ pNumber+"&hidIdList="+hidIdList);
		document.cstaffForm.submit.click();
		window.close();
		token = 2;
	});
	$(".accessoriesUrl1").click(function() {
		var accessoriesUrl = $.trim($("#manualUrl").attr("value"));
		var urlReturn = OpenWord(accessoriesUrl, 2);
		$("#manualUrl").attr("value", urlReturn);
	});
	$(".accessoriesUrl2").click(function() {
		var accessoriesUrl = $.trim($("#propagandaUrl").attr("value"));
		var urlReturn = OpenWord(accessoriesUrl, 2);
		$("#propagandaUrl").attr("value", urlReturn);
	});
	$(".accessoriesUrl3").click(function() {
		var accessoriesUrl = $.trim($("#fileUrl").attr("value"));
		var urlReturn = OpenWord(accessoriesUrl, 2);
		$("#fileUrl").attr("value", urlReturn);
	});
	//计算配件金额
	$(".jisuanpj").keyup( function() {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var p1=this.id;
				var s=p1.substring(7,p1.length);
				var quant=$("input#quantpj"+s,$("div#jqModel")).val();
				var price=$("input#pricepj"+s,$("div#jqModel")).val();
				var money=Math.round(quant * price * 100) / 100;
				$("input#moneypj"+s,$("div#jqModel")).attr("value",money);
				var moneyj=0;
				//计算成本价
				for (var i = 0; i <= s; i++) {
					var quanti=$("input#quantpj"+i,$("div#jqModel")).val();
					var pricei=$("input#pricepj"+i,$("div#jqModel")).val();
					if(!isNaN(quanti) && !isNaN(pricei)){
						var moneyi=Math.round(quanti * pricei * 100) / 100;
						moneyj+=moneyi;
						$("input#moneyi",$("div#jqModel")).attr("value",moneyj);
					}
				}
			}
		}
	});

	// 计算设计金额
	$(".jisuan").keyup( function() {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var p1=this.id;
				var s=p1.substring(5,p1.length);
				var p = $("input#"+p1,$("div#jqModel")).val();
				var dj = $("input#quantity",$("div#jqModel")).val();
				if (!isNaN(dj) && !isNaN(p)) {
					var jine = dj * p;
					jine = Math.round(jine * 100) / 100;
					var moneyid="money"+s;
					$("input#"+moneyid,$("div#jqModel")).attr("value",jine);
				}
			}
		}
	});

	
	$("#newline").click(function(){
		selects++;
		$("#kelong").before(
		
		$("#kelong").clone(true).show().attr("class","checkgoods")
		.attr("id",
				"kelong" + selects)
		);
		$("#kelong" + selects).find(':input').each(function() {
			$(this).attr("name","productPriceCategory." + this.name);
			$(this).attr("id",this.id+selects);
		});

	});
	
	//删除
	
	$(".removeline").click(function(){
		$(this).parent().parent().remove();	
	});
	
	//转移
 $("#trans").click(function(){
	 if(confirm("确定转移？")){

			
			var url = basePath+"ea/productdesign/sajax_ea_updateCate.jspa?d="+new Date();
			$.ajax({
				url:url,
				type:"get",
				async:false,
				dataType:"json",
				data:{
					"productDesign.ppID":productdesignID,
					 fiveClear:$("input:radio[name=fiveClear]:checked").val()
				},
				success:function(data){
					alert("转移成功");
					window.location.reload();
					
					
				},
				error:function(data){
					alert("转移失败");
				}
				
				
			});
		 
	 }
 });
	
});

/** **********************************选择物品**************************************** */
$(document).ready(function() {
	 tree1 = new dhtmlXTreeObject("SubjectsAadTree", "100%", "100%", 0);
		tree1.enableDragAndDrop(false);
		tree1.enableHighlighting(1);
		tree1.enableCheckBoxes(0);
		tree1.enableThreeStateCheckboxes(false);
		tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
		tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
		tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
		var getcodeurl = basePath
				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
				+ new Date().toLocaleString();
		tree1.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
				0);
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
						for (var i = 0; i < codeList.length; i++) {
							tree1.insertNewChild(
									"scode20101014v5zed7cukk0000000002",
									codeList[i].codeID, codeList[i].codeValue, 0,
									0, 0, 0);
							tree1.setUserData(codeList[i].codeID, "codeNumber",
									codeList[i].codeNumber);
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
		});
		tree1.setOnClickHandler(function() {
			var oldtreeid = treeid;
			treeid = tree1.getSelectedItemId();
			treename = tree1.getItemText(treeid);
			if (oldtreeid != treeid) {
				if (treeid != "scode20101014v5zed7cukk0000000002") {
					$("input#parms").val("typeID=" + treename);
					cx("typeID=" + treename);
					return;
				}
			}
				});
	
	// 双击选中物品
	$("table#gotable tr[id]").live("click", function(event) {
				goodsID = this.id;				
			});
	// 选择产品分类
	$("#xzcpfl").click(function(){
			category("pro",paret);
	});
	// 导入数据（选择物品）
	$("#shuju").click(function() {
				newType = '';
				$(".jqmWindow", $("#SubjectsForm")).jqmShow();
				$("#selectGood2").hide();
				$("#selectGood").show();
			});	
	$("#shuju2").click(function() {
		newType = '';
		$(".jqmWindow", $("#SubjectsForm")).jqmShow();
		$("#selectGood").hide();
		$("#selectGood2").show();
	});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $("input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $("input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		var a=$("[name='check']");
		var count=0;
		for(var i = 0; i < a.length; i++){
			if(a[i].checked == true){
				count++;
			}
		}		
		if(count==1) {
			$("input#goodsID",$("div#jqModel")).attr("value",goodsID);
			$("tr #" + goodsID).children("td").each(function() {
				if(this.id=="goodsName"){
					$("input#goodsName",$("div#jqModel")).attr("value",$(this).text());
				}
				$("span#" + this.id,$("#jqModel")).text($(this).text());
				$("#jqModel").jqmShow();
			});
			$(".jqmWindow", $("#SubjectsForm")).jqmHide();
		} else {
			alert("请选择一件物品！");
		}
	});
	
//ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo	
	// 添加所选中的物品到物品单
	$("#selectGood2").click(function() {
		
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					// 选中一行克隆一行
					selectpeijian++;
					// 克隆一行并修改文本框的name
					$("#kelongpeijian").before(
					$("#kelongpeijian").clone(true).attr("id",
							"kelongpeijian" + selectpeijian).addClass("checkpeijian")
							
					);
					//修改input标签Id为goodsNomber的值
					$("input#numbers",$("#kelongpeijian" + selectpeijian)).attr("value",selectpeijian - 1);
					//修改当前行的所有input的name
					$("#kelongpeijian" + selectpeijian).find(':input').each(function() {
						$(this).attr("name","productpeijian." + this.name);
					
						if(this.id=="goodsName"){
							$(this).attr("id",this.id);
						}else if(this.id=="goodsID"){
							$(this).attr("id",this.id);
						}else{
							$(this).attr("id",this.id+selectpeijian);
						}
					});
					
					//遍历Id为$(this).val()的tr里说以的td
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "goodsID") {
							$("input#goodsID", $("#kelongpeijian" + selectpeijian)).attr(
									"value", $(this).text());
						} else {
							$("span#" + this.id, $("#kelongpeijian" + selectpeijian))
									.text($(this).text());
						}
						if(this.id=="goodsName"){
							$("input#goodsName", $("#kelongpeijian" + selectpeijian)).attr(
									"value", $(this).text());
						}
						
					});
					$("tr#kelongpeijian" + selectpeijian).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#SubjectsForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
	});
//oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
	
	// 根据输入的物品编号或物品名称查询
	$("input#searchGood").click(function() {
				var typeName = $("#parameter", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName);
				cx("parameter=" + typeName);
			});

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
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
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
				alert("没有您要查的数据");
				$("table").remove("#gotable");
				$("table").remove("#dixzwp");
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
			$("span#wpzycount").text(zys);
			var tabletr = "<table width='98%' height='26' align='center' id='dixzwp' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
			tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>芯片号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].goodsID + ">";
				tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
						+ pageForm.list[i].goodsID + " name='check'/></td>";
				tabletr += "<td id='goodsCoding' align='center'>"
						+ pageForm.list[i].goodsCoding + "</td>";
				tabletr += "<td id='goodsName'  align='center'>"
						+ pageForm.list[i].goodsName + "</td>";
				tabletr += "<td id='defaultStorage'  align='center'>";
						+ pageForm.list[i].defaultStorage + "</td>";
				tabletr += "<td id='mnemonicCode' align='center'>"
						+ pageForm.list[i].mnemonicCode + "</td>";
				tabletr += "<td id='typeID' align='center'>"
						+ pageForm.list[i].typeID + "</td>";
				tabletr += "<td id='model' align='center'>"
						+ pageForm.list[i].model + "</td>";
				tabletr += "<td id='variableID'  align='center'>"
						+ pageForm.list[i].goodsvariable + "</td>";
				tabletr += "<td id='acquiesceStandard' align='center'>"
						+ pageForm.list[i].acquiesceStandard + "</td>";
				tabletr += "<td id='goodsID' style='display:none' align='center'>"
						+ pageForm.list[i].goodsID + "</td>";
				tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
				tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variableID + "</td>";
				tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable1ID + "</td>";
				tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable2ID + "</td>";
				tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable3ID + "</td>";
				tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].variable4ID + "</td>";
				tabletr += "<td id='subjectsName' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsName + "</td>";
				tabletr += "<td id='subjectsID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			$("#body_02").show();
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

function see(){
	if(flexbutton == 'flexbutton'){
		parent.document.getElementById("isSubmit").click();
	}if(flexbutton == 'yongjin'){
		window.open(basePath
				+ "/ea/productdesign/ea_toAddOrEditProductDesign.jspa?fiveClear="+fiveClear+"&flexbutton="+flexbutton+"&productDesign.ppID="+opertionID+"&addOrEdit=edit&showType=edit");
	}else if(flexbutton == 'publish'){
		action('产品发布');
	}else{
		action('修改');
	}
}

function re_load() {
	
	if (token)
		document.location.href = basePath
				+ "ea/productdesign/ea_getListProductdesign.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&identifier="+identifier+"&flexbutton="+flexbutton+"&ghua="+ghua+"&fiveClear="+fiveClear+"&devide="+devide+"&no="+no+"&typeString="+typeString;
}
