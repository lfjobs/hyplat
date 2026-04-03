$(function() {
		$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		// .jqDrag('.drag');// 添加拖拽的selector

		$("#DaoRuFanqd").click(function(){// 选择确定
		window.frames["daoRu"].$("input#selectPerson").click();
		var childopertionID = window.frames["daoRu"].opertionID;
		if (childopertionID == "" || childopertionID.length == 0) {
					alert('请选择');
					return;
				}
		var url = basePath+ "/ea/stafftrack/sajax_ea_getCompanybyID.jspa?childopertionID="+childopertionID+"&date="+new Date();
		
		
   });
		
		$('.JQueryflexme').flexigrid({
			buttons : [{
				name : '编辑菜单',
				bclass : 'menu1',
				onpress : action2
				}]
		});
		function action2(com, grid) {
			switch (com) {
				case '编辑菜单' :
					$(".menu00").toggle();
					break;
			}
		}
	var i = 0;
	$("div.showorhide").each(function(){
		if(this.id == '1'){
			i++;
			$(this).show();
			$("#"+this.name).attr("checked",true);
			if(i == 15){ //为全选状态
				$(".oroupboxAll").attr("checked",true);
			}
		}
	});
	
	$(".oroupboxAll").click(function(){ //全选
        if($(this).attr("checked")){
            $("input[type='checkbox']").each(function(){
          		$(this).attr("checked",true);
            });
        }else{
            $("input[type='checkbox']").each(function(){
            	$(this).attr("checked",false);
            });
        }
    });
	$(".JQuerySubmits").click(function(){ //编辑菜单保存
		var formData = $("#customersForms").serialize();
		formData = decodeURIComponent(formData,true);
		var url = basePath + "ea/productdesign/sajax_ea_saveHumanCollect.jspa?date="+new Date()+"&showType="+showType+"&";
		$.ajax({
				url: encodeURI(url + formData),
				type: "get",
				async: false,
				dataType: "json",
				success: function(data){
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath
                            + "page/ea/not_login.jsp";
                    }
					if(showType == 'add'){
						window.location.href = basePath+"ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType=add";//baokaixuecheguan参数  为了区分是否为学员报名模块   特显示另外几个模块
					}else{
						var staffid = $("#staffID").text();
						window.location.href = basePath+"ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType=edit&productDesign.ppID="+ppID;
					}
				},
				error:function(data){
					alert('保存失败！');
				}
		});
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
	
	$("input#tosave").click(function() {
		$t = $("table#stafftable");
		if ($("input#goodsID", $t).attr("value") == "") {
			alert("请选择物品！");
			return;
		}
		
		$(".put3", $t).trigger("blur");
		if ($("#box1Form .error").length) {
			alert("请正确操作");
			notoken = 0;
			return;
		}
		$(".isNaN").trigger("blur");
		$("#box1Form").attr(
						"action",
						basePath
								+ "ea/productdesign/ea_addProductdesign.jspa?");
		document.box1Form.submit.click();
	});
	
	// 计算金额
	$(".jisuan").keyup( function() {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $("input#quantity",$("div#jqModel")).val();
				var price = $("input#price",$("div#jqModel")).val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					$("input#money",$("div#jqModel")).attr("value",jine);
				}
			}
		}
	});
	$(".jisuan").keydown( function() {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(":input#quantity").val();
				var price = $(":input#price").val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					$(":input#money").attr("value",jine);
				}
			}
		}
	});
	
	$("td.txt03").click(function(){
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did,mid,'edit');
	});
});

//第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid,menuid,opetype){
	if(opetype=='edit'){
		$("div#"+divid).find("span.isShow").removeClass("isShow").addClass("isHide");
		$("div#"+divid).find("input.isHide").removeClass("isHide").addClass("isShow");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").removeClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).addClass("isHide");
		$("div#box"+menuid).show();
		
		var personurl = basePath + $("#mainframe"+menuid).attr("url") + $("#goodsID").val();
		$("#mainframe"+menuid).attr("src", personurl);
		
	}else if(opetype=='close'){
		$("div#"+divid).find("span.isHide").removeClass("isHide").addClass("isShow");
		$("div#"+divid).find("input.isShow").removeClass("isShow").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid+"_close").addClass("isHide");
		$("table.box"+menuid).find("a#mord"+menuid).removeClass("isHide");
		$("tr#tools").hide();
		$("div#box"+menuid).hide();
	}
}

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
					$(":input#parms2").val("typeID=" + treename);
					cx("typeID=" + treename);
					return;
				}
			}
				});
	
	// 双击选中物品
	$("table#gotable tr[id]").live("click", function(event) {
				goodsID = this.id;
				$("input.ragood", $(this)).attr("checked", "checked");
			});
	// 复选框选中物品
	$(".ragood").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	// 导入数据（选择物品）
	$("#shuju").click(function() {
				newType = '';
				$(".jqmWindow", $("#SubjectsForm")).jqmShow();
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
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
					var typeName = $(":input#parms").val();
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
		if(goodsID!='') {
			$("input#goodsID",$("table#stafftable")).attr("value",goodsID);
			$("tr #" + goodsID).children("td").each(function() {
				if(this.id=="goodsName"){
					$("input#goodsName",$("table#stafftable")).attr("value",$(this).text());
				}
				$("span#" + this.id,$("#stafftable")).text($(this).text());
				$("#jqModel").jqmShow();
			});
			$(".jqmWindow", $("#SubjectsForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
	});
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
				tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
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

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/productdesign/ea_getProductdesignAddorEdit.jspa?showType="+showType+"&productDesign.ppID="+ppID;
}