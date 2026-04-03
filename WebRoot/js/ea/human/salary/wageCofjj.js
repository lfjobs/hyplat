$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	var query = "<form name='cofjjSearchForm' id='cofjjSearchForm' method='post'>" +
		"<span style=\"margin-left:20px;\">设定时间：</span>" +
		"<input type='text' style=\"width: 90px\" id='yyyyCofDate' name='wcofjj.yyyyCofDate'  onfocus=\"WdatePicker({dateFmt:'yyyy'})\"/>" +
		"<span style=\"margin-left:10px;\">产品名称：</span>" +
		"<input type='text' style=\"width: 130px\" id='codeValue' name='wcofjj.goodsName' size=\"10\"/>" +
		"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchCofjj'/>" +
		"<input name='search' type='hidden' value='search' /><input type=\"submit\" name=\"submit\" style=\"display:none\"/>" +
		"</form>";
	$('#item').flexigrid({
		title: "计件工资设定管理"+query,
		width : 'auto',
		height:'auto',
		minwidth : 30,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
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
			name : '删除',
			bclass : 'delete',
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
			name : '停用',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}
//		, {
//			name : '导出',
//			bclass : 'mysearch',
//			onpress : action
//				// 当点击调用方法
//			}, {
//			separator : true
//		}
		, {
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
			$("#sa").after($("#sa").clone(true).attr("id","sa" + select).addClass("check"));
			$("#sa" + select).find(".cid").attr("value","sa" + select);
			$("#sa" + select).find('input:gt(1)').each(
					function() {
						$(this).attr(
								"name",
								"wcofjjmap[" + select + "]."
										+ this.name).addClass("put3");
					});
			$("#sa" + select).find('select').each(
					function() {
						$(this).attr(
								"name",
								"wcofjjmap[" + select + "]."
										+ this.name);
					});
			$("#sa" + select).show();
			$("#sa" + select).find("select").show();
			select++;
			break;
		case '修改' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#confJjState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			$p = $("tr#" + cid);
			if ($p.hasClass("check")) {
				return;
			}
			
			$p.addClass("check");
			$p.find('input:gt(1)').each(
					function() {
						$(this).attr(
								"name",
								"wcofjjmap[" + select + "]."
										+ this.name);
					});
			$p.find('select').each(
					function() {
						$(this).attr(
								"name",
								"wcofjjmap[" + select + "]."
										+ this.name);
						if($(this).attr("id") == "cjState"){
							var tt = $(this).parent().find("span").text().trim();
							$(this).find("option[text='"+tt+"']").attr("selected","selected");
						}else if($(this).attr("id") == "deppostID"){
							var tt = $(this).parent().find("span").text().trim();
							$(this).find("option[text='"+tt+"']").attr("selected","selected");
						}
						
					});
			select++;
			$p.find("span").addClass("model1");
			$p.find("input").removeClass("model1");
//			$p.find("s:select").attr("disabled", false);
			$p.find("select").show();
//			$(this).parent().children("span").show();
			break;
		case '删除' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#confJjState").val() == "1"){
				alert("已停用不可修改！");
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定删除吗？", function(item){
				if(item=="yes"){
					$('#cofjjForm').attr("action",basePath + "ea/cofjj/ea_delItem.jspa?wcofjj.cofJjID="+ cid+ "&xmtype="+xmtype);
					$('#cofjjForm').submit();
					$("tr#" + cid).remove();
					cid = "";
				}else{
					notoken = 0;
				}
			});
			break;
		case '全部保存':
			$("#cofjjForm .put3").trigger("blur");
			$("#cofjjForm .put3").each(function(){
				$(this).parent().find(".corect").remove();
				$(this).parent().find(".tooltip").remove();
			});
			if ($("#cofjjForm .error").length) {
				alert("请填写完整信息！");
				return;
			}
			if (notoken) {
				return;
			}
			notoken = 1;
			$('#cofjjForm').attr("action",basePath + "ea/cofjj/ea_saveItem.jspa?xmtype="+xmtype);
			$('#cofjjForm').submit();
			break;
		case '停用' :
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			if($("#"+cid).find("input#confJjState").val() != "0"){
				alert("不可停用！");
				return;
			}
			if (notoken)
				return;
			notoken = 1;
			Showbo.Msg.confirm("确定停用吗？", function(item){
				if(item=="yes"){
					$('#cofjjForm').attr("action",basePath + "ea/cofjj/ea_upItem.jspa?wcofjj.cofJjID="+ cid+ "&xmtype="+xmtype);
					$('#cofjjForm').submit();
				}else{
					notoken = 0;
				}
			});
			break;
		case '设置每页显示条数' :
			var url = basePath
					+ "ea/cofjj/ea_findItem.jspa?1=1";
			numback(url);
			break;
	}
}
	$("select").each(function(){
		$(this).attr("style", "display:none");
	});
	$("#item tr").click(function() {
		cid=$(this).find("input.cid").val();
		$(this).find("input.cid").attr("checked",true);
	});
	$("#item tr").dblclick(function() {
		cid=$(this).find("input.cid").val();
		action('修改');
	});
	
	//主项目目录关闭
	$("#closeml").click(function(){
		$("#jqModel").hide();
		
	});
	$("#searchCofjj").click(function(){
		$('#cofjjSearchForm').attr("action",basePath + "ea/cofjj/ea_toSearch.jspa?xmtype="+xmtype);
		document.cofjjSearchForm.submit.click();
	});
	// 导入数据（选择物品）
	$(".shuju").live("click",
		function() {
			if (cid == '') {
				Showbo.Msg.alert("请选择！");
				return;
			}
			$("#body_02").html("");
			$("#selectType").val("goods");
			$(".jqmWindow", $("#goodsForm")).jqmShow();

	});
	// 各个弹出框的关闭功能
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	/***************选择物品**********************/
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
	// 选中物品
	$("table#gotable tr[id]").live("click", function(event) {
		wpid = this.id;
		$(".ragood").each(function(){
			if($(this).val() == wpid){
				$(this).attr("checked","checked");
			}else{
				$(this).attr("checked","");
			}
		});
	});

	// 导入数据（选择物品）
	$(".shuju").live("click", function() {
		if($("#xmtypename").val()==""){
			alert("请选选择项目");
			return;
		}
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
	// 上一页
	$("#wpsyq").click(function() {
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
	$("#wpxyq").click(function() {
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
	$(".xzwp").click(function() {window.open(basePath+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");});
	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
//		var $tbl = $("#"+$("#linet").val());
//		var clicktr = $("#line",$tbl).text();
		
		if (wpid == "") {
			alert("请选择物品！");
			return;
		} else {
			$("tr#"+cid).find("input#jjCodeName").attr("value",$("tr#"+wpid, $("#goodsForm")).find("td#goodsName").text());
			$("tr#"+cid).find("input#jjCodeID").attr("value",wpid);
			
			$(".jqmWindow", $("#goodsForm")).jqmHide();
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
	
	
	
});

function closeM(){
	document.forms["addform"].reset();
	$("#jqmadd").jqmHide();
	
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "cofi/ea_findItem.jspa?wstate=" + wstate;
}
/** ***********************项目分类**************************** */
// 键入时查询项目分类
function getCate(obj) {
	var pY = pageY(obj);
	if(pY > GetPageSize()[1]/2){
		pY = pageY(obj) - 350 ;
	}else{
		pY = pageY(obj) + 30;
	}
	var url = basePath + "ea/cofi/sajax_ea_getXmtypeByCode.jspa?xmtype="+xmtype+"&date="
			+ new Date().toLocaleString();
	$.ajax({
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
						str += "&nbsp;&nbsp;<span><a href='#' style='color:royalblue;'"
							+ "title='" + xmList[i][2] +"' onclick='selectz(this);'>"
							+ xmList[i][0]
							+ "/"
							+ xmList[i][1] + "</a></span><br/>";
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}

					$("#treeBoxs").html(str);

					$("#jqModel").css({
						"width" : "230px",
						"height" : "350px",
						"overflow" : "auto",
						"background-color" : "#e1ecfc",
						"border" : "1px solid #a8c7ce",
						"position" : "absolute",
						"left" : pageX(obj),
						"top" : pY
					}).show();

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
	var xmtypename = codeValues.substring(0,codeValues.indexOf("/"));
	var xmtype = codeValues.substring(codeValues.indexOf("/") + 1);
	$("tr#"+cid).find("#goodsName").attr("value",xmtypename);
	$("tr#"+cid).find("#cjProPriceT").attr("value",xmtype);

}
//获取当前的x坐标值

function pageX(elem){
      return elem.offsetParent?(elem.offsetLeft+pageX(elem.offsetParent)):elem.offsetLeft;
      }
//获取当前的Y坐标值
function pageY(elem){
      return elem.offsetParent?(elem.offsetTop+pageY(elem.offsetParent)):elem.offsetTop;
      }
/*************************项目分类结束**************************** */
/**阶段select选择**/
function jjCodeonch(e){
	$("#" + cid).find("input#jjCodeName").attr("value",$(e).find("option:selected").text());
}
/**部门select选择**/
function postonch(e){
	$("#" + cid).find("input#postName").attr("value",$(e).find("option:selected").text());
}


/** **********************************选择物品**************************************** */
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
//物品键入实时查询
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
	$.ajax({
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
//ajax查询物品列表
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