$(function(){
    //为弹出框准备下拉表内容
    $(".jqmWindow").jqm({
        modal: true,// 
        overlay: 20 // 
    }).jqmAddClose('.close');//
    $(".JQueryreturns").click(function(){
        notoken = 0;
        $(".jqmWindow").jqmHide();
    });
    $(".JQueryClose").click(function(){
        notoken = 0;
        re_load();
    });
    $("#staffID").hide();
    $("#departmentID").hide();
    if (cashierBillsID == "") {
    	$("input.JQueryprint").hide();    //添加页面打印预览隐藏
    	$("input.sub_bill").show();       //添加页面草稿保存显示
		$("input.JQuerySubmitgd").show(); //添加页面提交审核显示
        $("#cashierstatusService").val("11");  //设置单据为售前客户服务单据
        var staff = $("select#staffID").attr("name", "cashierBills.staffID").show();
        var dept = $("select#departmentID").attr("name", "cashierBills.departmentID").show();
        $("td#dept").html(dept);
        $("td#staff").html(staff);
    }
    else {
        $("select[id]", $("tr.xggoods")).each(function(){
            $(this).hide();
        });
        $(".classhide").show();
        $("input.JQueryunitret").hide();    //查看页面重置往来单位隐藏
		$("input.JQuerypersonret").hide();  //查看页面重置往来个人隐藏
		$("input.JQuerySubmitgd").show();   //查看页面提交审核显示
		$("input.JQueryprint").show();      //查看页面打印预览显示
		
		if($("#consultStatus").val() != '' && $("#consultStatus").val() == '00'){ //当单据状态不为空或是草稿状态
			$("input.sub_bill").show();     //添加页面草稿保存显示
		}
        
        /*****  数据归档编辑页面显示  ****/
        if($("#consultStatus").val() != '' && $("#consultStatus").val() != '00'){ //当单据状态不为空或不是草稿状态
        	$("#journalNum").css("border","none");  //粘贴单编号边框隐藏
        	$("#partnerName").css("border","none"); //责任人边框隐藏
        	$('#nameChoose').hide();  //责任人选择隐藏
        	$("#depText").css("border","none"); //部门边框隐藏
        	$('.update').hide();      //部门修改隐藏
        	$("#bankNum").css("border","none"); //银行账号边框隐藏
        	$('#bankChoose').hide();  //银行账号选择隐藏
        	$('#otherChoose').hide(); //选择物品、单位、个人隐藏
        	$('.JQuerySubmitgd').hide();    //提交审核按钮隐藏
        }
        
        /*---------------              显示审核公章               ---------------*/
        var st = $("#consultStatus").val();
		if (st != "00" && st != ''){
			if (st == "10") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/zuofei.png'/>";
				$("#apDiv1").html(str);
			} else if (st == "03") {
				var str = "<img src='" + basePath
						+ "images/ea/finance/yishen.png'/>";
				$("#apDiv1").html(str);
			} else {
				var str = "<img src='" + basePath
						+ "images/ea/finance/daishen.png'/>";
				$("#apDiv1").html(str);
			}
		}else {
			$("input.JQueryprint").hide();
		}
    }
    
    /** *************************添加时word文档编辑********************************* */
		$(".accessoriesUrl").click(function() {
			var accessoriesUrl = $.trim($(this).next().next().val());
			var urlReturn = OpenWord(accessoriesUrl, 2);
			$(this).next().next().attr('value',urlReturn);
		})	;
		
	/** *************************查看时word文档编辑********************************* */
		$(".accessoriesUrl1").click(function() {
			var accessoriesUrl = $.trim($(this).next().next().val());
			OpenWord(accessoriesUrl, 2);
		})	;
	/** ********************************************************** */
    
    $(".update").click(function(){
        var selectID = $(this).attr("title");
        var departmentIDselect = $(this).next("select").attr("name", "cashierBills." + selectID);
        $(this).parent().html(departmentIDselect);
        $("#" + selectID).show();
    });
    
    $("input.JQueryprint").click(function() {// 打印预览
		var cashierBillsID = $("input#cashierID",$("#cashierTallyForm")).val();
		window.open(basePath
						+ "ea/custService/ea_toprintCashierService.jspa?cashierBillsVO.cashierBillsID="
						+ cashierBillsID);
	});
    
    $("input.JQueryunitret").click(function(){// 重置往来单位
        $t = $("table#table4");
        $t.find(":span .qk").each(function(){
            $(this).text("");
        });
        $t.find("select").each(function(){
            $(this).empty();
            $(this).attr("style", "display:none");
        });
        $t.find(":input").each(function(){
            $(this).attr("value", "");
        });
    });
    $("input.JQuerypersonret").click(function(){// 重置往来个人
        $t = $("table#table5");
        $t.find(":span .qk").each(function(){
            $(this).text("");
        });
        $t.find("select").each(function(){
            $(this).empty();
            $(this).attr("style", "display:none");
        });
        $t.find(":input").each(function(){
            $(this).attr("value", "");
        });
    });
    
    //双҉击҉事҉件҉修҉改
    $("tr.xggoods").dblclick(function(){
    	var consultStatus = $("#consultStatus").val();
		if (consultStatus!='' && consultStatus != "00") {
			alert("已归档不能修改该条数据！");
			return;
		}
        goodsBillsID = this.id;
        $p = $("tr#" + goodsBillsID);
        if (!$p.hasClass("checkgoods")) {
            $p.addClass("checkgoods");
            $p.find(':input').each(function(){
                $(this).attr("name", "goodsmap[" + select + "]." + this.name);
            });
            select++;
            $p.find("td").children("span[class!=bhide]").addClass("model1");
            $p.find("td").children("input").removeClass("model1");
            $p.find("select").show();
            $p.find("a").show();
        }
    });
    //更改部门事件 清空银行帐号
    $("select#departmentID", "table#table3").change(function(){
        $("input#bankNum", "table#table3").attr("value", "");
    });
    //计算金额
    $(".jisuan").live("keyup", function(event){
        if (this.value != "") {
            if (isNaN(this.value)) {
                return;
            }
            else {
                var dj = $(this).parent().parent().find(":input#quantity").val();
                var price = $(this).parent().parent().find(":input#price").val();
                if (!isNaN(dj) && !isNaN(price)) {
                    var jine = dj * price;
                    jine = Math.round(jine * 100) / 100;
                    $(this).parent().parent().find(":input#money").attr("value", jine);
                }
            }
        }
    });
    $(".jisuan").live("keydown", function(event){
        if (this.value != "") {
            if (isNaN(this.value)) {
                return;
            }
            else {
                var dj = $(this).parent().parent().find(":input#quantity").val();
                var price = $(this).parent().parent().find(":input#price").val();
                if (!isNaN(dj) && !isNaN(price)) {
                    var jine = dj * price;
                    jine = Math.round(jine * 100) / 100;
                    $(this).parent().parent().find(":input#money").attr("value", jine);
                }
            }
        }
    });
    //修҉改҉
    $(".ajaxxg").click(function(){
    	var consultStatus = $("#consultStatus").val();
		if (consultStatus!='' && consultStatus != "00") {
			alert("已归档不能修改该条数据！");
			return;
		}
        goodsBillsID = $(this).parent().find("input#goodsBillsID").val();
        $p = $("tr#" + goodsBillsID);
        $p = $("tr#" + goodsBillsID);
        if (!$p.hasClass("checkgoods")) {
            $p.addClass("checkgoods");
            $p.find(':input').each(function(){
                $(this).attr("name", "goodsmap[" + select + "]." + this.name);
            });
            select++;
            $p.find("td").children("span[class!=bhide]").addClass("model1");
            $p.find("td").children("input").removeClass("model1");
            $p.find("select").show();
            $p.find("a").show();
        }
    });
    //迭代的商品删除
    $(".ajaxsc").click(function(){
    	var consultStatus = $("#consultStatus").val();
		if (consultStatus!='' && consultStatus != "00") {
			alert("已归档不能删除该条数据！");
			return;
		}
        if (confirm("是否删除？")) {
            var goodsBillsID = $(this).parent().find("input#goodsBillsID").val();
            var delurl = basePath + "ea/cashierbills/sajax_ea_delGoodsBills.jspa?typeID=" + goodsBillsID + "&date=" + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(delurl),
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath + "page/ea/not_login.jsp";
                    }
                    var typeID = member.typeID;
                    alert("删除成功！");
                    $("tr#" + typeID).remove();
                },
                error: function cbf(data){
                    alert("数据获取失败！");
                }
            });
        }
    });
    //克隆的商品删除
    $(".klsc").click(function(){
        $(this).parent().parent().remove();
    });
    //提交保存
    $("input.sub_bill").click(function(){
        if (notoken) {
            alert("正在提交数据！请稍等");
            return;
        }
        
        notoken = 1;
        $(".put3", $("tr.checkgoods")).trigger("blur");
        $(".isNaN").trigger("blur");
        $(".jisuan").trigger("blur");
        if ($("#cashierTallyForm .error").length) {
            alert("请正确填完所有必填项");
            notoken = 0;
            return;
        }
        $("#cashierstatusService").attr("value", "11");  //单据状态为客户咨询单
        $("#consultStatus").attr("value", "00");         //客户咨询单为草稿状态
        if (cashierBillsID == "") {
            $("#cashierTallyForm").attr("target", "hidden").attr("action", basePath + "ea/custService/ea_saveCustCashierBills.jspa?pageNumber=" + pNumber);
            document.cashierTallyForm.submit.click();
            document.cashierTallyForm.reset();
            $(".qk").text("");
            
            $("select#contactConnections", $("table#table4")).hide();
            $("select#aNum", $("table#table4")).hide();
            $("select#phone", $("table#table5")).hide();
            $("select#userNum", $("table#table5")).hide();
            $("#consultStatus").attr("value", "00");
            $("tr.checkgoods").remove();
            token = 1;
            return;
        }
        
        $("#cashierTallyForm").attr("target", "hidden").attr("action", basePath + "ea/custService/ea_saveCustCashierBills.jspa?pageNumber=" + pNumber);
        document.cashierTallyForm.submit.click();
        token = 2;
    });
    
    // 归档
	$("input.JQuerySubmitgd").click(function() {
		var consultStatus = $("#consultStatus").val();
		if (consultStatus != "00" && consultStatus !='') {
			alert("已归档不能再次提交！");
			return;
		}
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		notoken = 1;
		$(".put3", $("tr.checkgoods")).trigger("blur");
		$(".isNaN").trigger("blur");
		$(".jisuan").trigger("blur");
		if ($("#cashierTallyForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cashierstatusService").attr("value", "11");  //单据状态为客户咨询单
		$("#consultStatus").attr("value", "01");         //设置单据状态为待营销审核
		if (cashierBillsID == "") {
			$("#cashierTallyForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/custService/ea_saveCustCashierBills.jspa?pageNumber="
									+ pNumber);
			document.cashierTallyForm.submit.click();
			document.cashierTallyForm.reset();
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();  
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("#consultStatus").attr("value", "00");
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
		$("#cashierTallyForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/custService/ea_saveCustCashierBills.jspa?pageNumber="
								+ pNumber);
		document.cashierTallyForm.submit.click();
		token = 2;
	});
    
    /*********************************取得部门下拉*************************************/
    var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
    var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
    var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
    $("span#companyNames").text(treePName);
    var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date=" + new Date().toLocaleString();
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath + "page/ea/not_login.jsp";
            }
            var oList = member.organizationlist;
            var data = new Array();
            data[0] = {
                id: treeID,
                pid: '-1',
                text: treeName
            };
            for (var i = 0; i < oList.length; i++) {
                data[i + 1] = {
                    id: oList[i].organizationID,
                    pid: oList[i].organizationPID,
                    text: oList[i].organizationName
                };
            }
            var ts = new TreeSelector($("select#departmentID")[0], data, -1);
            ts.createTree();
            if (deptID != "") {
                $("table#table3").find("select#departmentID").attr("value", deptID);
            }
        },
        error: function cbf(data){
            alert("数据获取失败！");
        }
    });
});

function re_load(){
    document.location.href = basePath + "ea/custService/ea_getCashierToPage.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber=" + pageNumber + "&search=" + search;
}

function toCCode(codePID, selectID, formID){
    $(".jqmWindow").jqmHide();
    $("#codePID").attr("value", codePID);
    $("#selectID").attr("value", selectID);
    $("#formID").attr("value", formID);
    $("#ccodevalue").attr("value", "");
    $("#newccode").jqmShow();;
}

function saveCCode(){
    var codePID = $("#codePID").attr("value");
    var codeValue = $("#ccodevalue").attr("value");
    var selectID = $("#selectID").attr("value");
    var formID = $($("#formID").attr("value"));
    var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID=" + codePID + "&code.codeValue=" + codeValue + "&date=" + new Date().toLocaleString();
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
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
            $(".jqmWindow", "#formID").jqmShow();
        },
        error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}

$(document).ready(function(){
    $("input.JQueryreturn1").click(function(){// 取消
        //var formID = $($("#formID").attr("value"));
        $("#newccode").jqmHide();
        $(".jqmWindow", "#formID").jqmShow();
    });
});

/************************************选择物品*****************************************/
$(document).ready(function(){
    tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
    tree.enableThreeStateCheckboxes(false);
    tree.setSkin(basePath + 'js/tree/dhx_skyblue');
    tree.setImagePath(basePath + "js/tree/codebase/imgs/");
    tree.loadXML(basePath + "js/tree/common/tree_b.xml");
    var getcodeurl = basePath + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date=" + new Date().toLocaleString();
    tree.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0, 0);
    $.ajax({
        url: encodeURI(getcodeurl),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if (nologin) {
                document.location.href = basePath + "page/ea/not_login.jsp";
            }
            var codeList = member.codeList;
            if (null == codeList) {
                return;
            }
            for (var i = 0; i < codeList.length; i++) {
            
                tree.insertNewChild("scode20101014v5zed7cukk0000000002", codeList[i].codeID, codeList[i].codeValue, 0, 0, 0, 0);
                tree.setUserData(codeList[i].codeID, "codeNumber", codeList[i].codeNumber);
                
            }
        },
        error: function cbf(data){
            alert("数据获取失败！");
        }
    });
    tree.setOnClickHandler(function(){
        var oldtreeid = treeid;
        treeid = tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        if (oldtreeid != treeid) {
            if (treeid != "scode20101014v5zed7cukk0000000002") {
                $(":input#parms").val("typeID=" + treename);
                cx("typeID=" + treename);
                return;
            }
        }
    });
    //双击选中物品
    $("table#gotable tr[id]").live("click", function(event){
        var b = $("input.ragood", $(this)).attr("checked");
        $("input.ragood", $(this)).attr("checked", !b);
    });
    //复选框选中物品
    $(".ragood").live("click", function(event){
        var b = $(this).attr("checked");
        $(this).attr("checked", !b);
    });
    //导入数据（选择物品）
    $("#shuju").click(function(){
        $(".jqmWindow", $("#goodsForm")).jqmShow();
    });
    //上一页
    $("#wpsy").click(function(){
        var sy = $("#wpsy").attr("title");
        if (sy != 0) {
            var typeName = $(":input#parms").val();
            var typeCN = typeName + "&pageForm.pageNumber=" + sy;
            cx(typeCN);
        }
        else {
            alert("已是首页！");
        }
    });
    //下一页
    $("#wpxy").click(function(){
        var xy = $("#wpxy").attr("title");
        if (xy != 0) {
            var typeName = $(":input#parms").val();
            var typeCN = typeName + "&pageForm.pageNumber=" + xy;
            cx(typeCN);
        }
        else {
            alert("已是尾页！");
        }
    });
    //新增物品
    $(".xzwp").click(function(){
        window.open(basePath + "ea/goodsmanage/ea_getListGoodsManage.jspa?");
    });
    //添加所选中的物品到物品单
    $("#selectGood").click(function(){
        if ($("[name='check']").is(':checked')) {
            $("input[name='check']").each(function(){
                if ($(this).is(':checked')) {
                    //选中一行克隆一行
                    select++;
                    //克隆一行并修改文本框的name
                    $("#kelong").after($("#kelong").clone(true).attr("id", "kelong" + select).addClass("checkgoods"));
                    $("#kelong" + select).find(':input').each(function(){
                        $(this).attr("name", "goodsmap[" + select + "]." + this.name);
                    });
                    $op = $("<option value='' selected>请选择</option>");
                    $("select#goodsVariableID", $("#kelong" + select)).append($op);
                    $("tr #" + $(this).val()).children("td").each(function(){
                        if (this.id == "goodsID") {
                            $("input#goodsID", $("#kelong" + select)).attr("value", $(this).text());
                        }
                        else {
                            $("span#" + this.id, $("#kelong" + select)).text($(this).text());
                        }
                        if (this.title == "ava" && $(this).text().length != 0) {
                            $op = $("<option />");
                            $op.attr("value", $(this).text()).text($(this).text());
                            $("select#goodsVariableID", $("#kelong" + select)).append($op);
                        }
                    });
                    $("tr#kelong" + select).show();
                    $(this).attr("checked", false);
                }
            });
            $(".jqmWindow", $("#goodsForm")).jqmHide();
        }
        else {
            alert("请选择物品！");
        }
    });
    //根据输入的物品编号或物品名称查询
    $("input#searchGood").click(function(){
        var typeName = $("#typeID", $("table#searchgood")).val();
        $(":input#parms").val("parameter=" + typeName);
        
        cx("parameter=" + typeName);
    });
    //ajax查询物品列表
    function cx(typeCN){
        if (notoken) {
            alert("正在获取数据！请稍等");
            return;
        }
        notoken = 1;
        $("#wpsy").attr("title", 0);
        $("#wpxy").attr("title", 0);
        $("#wpzy").attr("title", 0);
        var searchurl = basePath + "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
        $.ajax({
            url: encodeURI(searchurl + typeCN + "&date=" + new Date().toLocaleString()),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    alert("没有数据");
                    notoken = 0;
                    return;
                }
                var dqy = pageForm.pageNumber;//当前页
                var zys = pageForm.pageCount;//总页数
                if (dqy > 1) {
                    $("#wpsy").attr("title", dqy - 1);
                }
                if (dqy < zys) {
                    $("#wpxy").attr("title", dqy + 1);
                }
                $("span#wpzycount").text(zys);
                var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
                tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th><th align='center' bgcolor='#E4F1FA'>类型</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th><th align='center' bgcolor='#E4F1FA'>换算单位</th>";
                tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th><th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
                for (var i = 0; i < pageForm.list.length; i++) {
                    tabletr += "<tr style='cursor: hand;' id = " + pageForm.list[i].goodsID + ">";
                    tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value=" + pageForm.list[i].goodsID + " name='check'/></td>";
                    tabletr += "<td id='goodsCoding' align='center'>" + pageForm.list[i].goodsCoding + "</td>";
                    tabletr += "<td id='goodsName'  align='center'>" + pageForm.list[i].goodsName + "</td>";
                    tabletr += "<td id='defaultStorage'  align='center'>" + pageForm.list[i].defaultStorage + "</td>";
                    tabletr += "<td id='mnemonicCode' align='center'>" + pageForm.list[i].mnemonicCode + "</td>";
                    tabletr += "<td id='typeID' align='center'>" + pageForm.list[i].typeID + "</td>";
                    tabletr += "<td id='model' align='center'>" + pageForm.list[i].model + "</td>";
                    tabletr += "<td id='variableID'  align='center'>" + pageForm.list[i].goodsvariable + "</td>";
                    tabletr += "<td id='acquiesceStandard' align='center'>" + pageForm.list[i].acquiesceStandard + "</td>";
                    tabletr += "<td id='goodsID' style='display:none' align='center'>" + pageForm.list[i].goodsID + "</td>";
                    tabletr += "<td id='standard' align='center'>" + pageForm.list[i].standard + "</td>";
                    tabletr += "<td id='variableID' title='ava' style='display:none' align='center'>" + pageForm.list[i].variableID + "</td>";
                    tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>" + pageForm.list[i].variable1ID + "</td>";
                    tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>" + pageForm.list[i].variable2ID + "</td>";
                    tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>" + pageForm.list[i].variable3ID + "</td>";
                    tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>" + pageForm.list[i].variable4ID + "</td>";
                    tabletr += " </tr>";
                }
                tabletr += " </table>";
                $("#body_02").html(tabletr);
                $("#body_02").show();
                //alert("数据加载成功")
                notoken = 0;
                window.status = "数据加载成功";
            },
            error: function cbf(data){
                notoken = 0;
                alert("数据获取失败！");
            }
        });
    }
});

/************************************往来单位*****************************************/
$(document).ready(function(){
    var contactcID = "";//已经添加到往来单位的ID
    var ccID = "";//ccompanyID
    $("table#gostable tr[id]").live("click", function(event){
        contactcID = this.id;
        ccID = this.title;
        $("input.ra", $(this)).attr("checked", "checked");
    });
    //选择往来单位
    $("#xzwlaw").click(function(){
        $("input#ccompanyID", $("table#searchcompany")).val("");
        $("select#contactConnections", $("table#searchcompany")).val("全部");
        $(".jqmWindow", $("#selectcompanyForm")).jqmShow();
        cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
    });
    //新增往来单位
    $(".xzdw").click(function(){
        window.open(basePath + "ea/contactcompany/ea_getListContactCompany.jspa?");
    });
    //添加所选中的往来单位
    $("#qdcompany").click(function(){
        if (contactcID != "") {
            var RegistrationURL = basePath + "ea/contactcompany/sajax_ea_getListRegistration.jspa?contactCompany.ccompanyID=" + ccID + "&date=" + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(RegistrationURL),
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath + "page/ea/not_login.jsp";
                    }
                    var bankList = member.bankList;
                    $se = $("select#aNum", $("table#table4"));
                    $se.empty();
                    $se.append("<option selected='selected' value = ''>--请选择--</option>");
                    for (var i = 0; i < bankList.length; i++) {
                        $op = $("<option />");
                        $op.attr("value", bankList[i].bankAccount).text(bankList[i].bankName + "---" + bankList[i].bankAccount);
                        $se.append($op);
                    }
                    $("span#accountNum", $("#table4")).remove();
                    $("input#accountNum", $("#table4")).remove();
                    $se.attr("name", "cashierBills.accountNum");
                    $se.show();
                },
                error: function cbf(data){
                    notoken = 0;
                    alert("数据获取失败！");
                }
            });
            $("tr #" + contactcID).children("td").each(function(){
                if (this.id == "ccompanyID") {
                    $("input#ccompanyID", $("table#table4")).val($(this).text());
                    
                }
                else 
                    if (this.id == "contactConnections") {
                        $("select#contactConnections option[value=" + $(this).text() + "]", $("table#table4")).remove();
                        $("select#contactConnections", $("table#table4")).append("<option selected='selected' value = " + $(this).text() + ">" + $(this).text() + "</option>").show();
                        $("span#contactConnections", $("table#table4")).hide();
                    }
                    else {
                        $("span#" + this.id, $("table#table4")).text($(this).text());
                    }
            });
            $(".jqmWindow", $("#selectcompanyForm")).jqmHide();
        }
        else {
            alert("请选择往来单位！");
        }
    });
    //根据输入的往来单位名称查询
    $("input#searchcc").click(function(){
        contactcID = "";
        var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
        var contactConnections = $("select#contactConnections", $("table#searchcompany")).val();
        cxwldw("contactCompany.companyName=" + typeName + "&cconnection.contactConnections=" + contactConnections);
    });
    //上一页
    $("#dwsy").click(function(){
        var sy = $("#dwsy").attr("title");
        if (sy != 0) {
            contactcID = "";
            var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
            var contactConnections = $("select#contactConnections", $("table#searchcompany")).val();
            var typeCN = "contactCompany.companyName=" + typeName + "&cconnection.contactConnections=" + contactConnections + "&pageForm.pageNumber=" + sy;
            cxwldw(typeCN);
        }
        else {
            alert("已是首页！");
        }
    });
    //下一页
    $("#dwxy").click(function(){
        var xy = $("#dwxy").attr("title");
        if (xy != 0) {
            contactcID = "";
            var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
            var contactConnections = $("select#contactConnections", $("table#searchcompany")).val();
            var typeCN = "contactCompany.companyName=" + typeName + "&cconnection.contactConnections=" + contactConnections + "&pageForm.pageNumber=" + xy;
            cxwldw(typeCN);
        }
        else {
            alert("已是尾页！");
        }
    });
    //ajax查询往来单位列表
    function cxwldw(typeCN){
        if (notoken) {
            alert("正在获取数据！请稍等");
            return;
        }
        notoken = 1;
        $("#dwsy").attr("title", 0);
        $("#dwxy").attr("title", 0);
        $("#dwzy").attr("title", 0);
        var searchurl = basePath + "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
        $.ajax({
            url: encodeURI(searchurl + typeCN + "&date=" + new Date().toLocaleString()),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                var pageForm = member.pageForm;
                var connectionlist = member.connectionlist;
                if (pageForm == null) {
                    alert("没有数据");
                    notoken = 0;
                    return;
                }
                var $se = $("select#contactConnections", $("table#searchcompany"));
                $se.empty();
                $select = "<option selected='selected' value = ''>--全部--</option>";
                $se.append($select);
                for (var i = 0; i < connectionlist.length; i++) {
                    $op = $("<option />");
                    $op.attr("value", connectionlist[i].codeValue).text(connectionlist[i].codeValue);
                    $se.append($op);
                }
                var dqy = pageForm.pageNumber;//当前页
                var zys = pageForm.pageCount;//总页数
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
                for (var i = 0; i < pageForm.list.length; i++) {
                    tabletr += "<tr style='cursor: hand;' title=" + pageForm.list[i].ccompanyID + " id = " + pageForm.list[i].contactConnectionID + ">";
                    tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value=" + pageForm.list[i].contactConnectionID + " name='checkradio'/></td>";
                    tabletr += "<td id='ccompanyname' align='center'>" + pageForm.list[i].companyName + "</td>";
                    tabletr += "<td id='contactConnections' align='center'>" + pageForm.list[i].contactConnections + "</td>";
                    tabletr += "<td id='industryType' align='center'>" + pageForm.list[i].industryType + "</td>";
                    tabletr += "<td id='companyTel'  align='center'>" + pageForm.list[i].companyTel + "</td>";
                    tabletr += "<td id='cresponsible' align='center'>" + pageForm.list[i].cresponsible + "</td>";
                    tabletr += "<td id='responsibleTel' align='center'>" + pageForm.list[i].responsibleTel + "</td>";
                    tabletr += "<td id='companyAddr'  align='center'>" + pageForm.list[i].companyAddr + "</td>";
                    tabletr += "<td id='ccompanyID' style='display:none' align='center'>" + pageForm.list[i].ccompanyID + "</td>";
                    tabletr += " </tr>";
                }
                tabletr += " </table>";
                $("#body_02cc").html(tabletr);
                $("#body_02cc").show();
                alert("数据加载成功");
                notoken = 0;
                window.status = "数据加载成功";
            },
            error: function cbf(data){
                notoken = 0;
                alert("数据获取失败！");
            }
        });
    }
});

//选择往来个人
$(document).ready(function(){
    var cuID = "";
    var userstaffID = "";
    $("table#gouserstable tr[id]").live("click", function(event){
        cuID = this.id;
        userstaffID = this.title;
        $("input.rauser", $(this)).attr("checked", "checked");
    });
    //选择往来个人
    $("#xzwlgr").click(function(){
        $("input#contactUserID", $("table#searchuser")).val("");
        $("select#relation", $("table#searchuser")).val("全部");
        $(".jqmWindow", $("#selectuserForm")).jqmShow();
        cxwlgr("contactUser.staffName=&contactUser.relation=");
    });
    //新增往来个人
    $(".xzgr").click(function(){
        window.open(basePath + "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
    });
    
    //添加所选中的往来个人
    $("#qduser").click(function(){
        if (cuID != "") {
            var RegistrationuserURL = basePath + "ea/contactuser/sajax_ea_getListRegistrationUser.jspa?contactUser.staffID=" + userstaffID + "&date=" + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(RegistrationuserURL),
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data){
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath + "page/ea/not_login.jsp";
                    }
                    var bankList = member.bankList;
                    $se = $("select#userNum");
                    $se.empty();
                    $se.append("<option selected='selected' value = ''>--请选择--</option>");
                    for (var i = 0; i < bankList.length; i++) {
                        $op = $("<option />");
                        $op.attr("value", bankList[i].bankAccount).text(bankList[i].bankName + "---" + bankList[i].bankAccount);
                        $se.append($op);
                    }
                    $("span#userAccountNum").remove();
                    $("input#userAccountNum").remove();
                    $se.attr("name", "cashierBills.userAccountNum");
                    $se.show();
                },
                error: function cbf(data){
                    notoken = 0;
                    alert("数据获取失败！");
                }
            });
            $("tr #" + cuID).children("td").each(function(){
                if (this.id == "contactUserID") {
                    $("input#contactUserID", $("table#table5")).val($(this).text());
                    
                }
                else 
                    if (this.id == "phone") {
                    
                        $("select#phone option[value=" + $(this).text() + "]", $("table#table5")).remove();
                        $("select#phone", $("table#table5")).append("<option selected='selected' value = " + $(this).text() + ">" + $(this).text() + "</option>").show();
                        $("span#phone", $("table#table5")).hide();
                    }
                    else {
                        $("span#" + this.id, $("table#table5")).text($(this).text());
                    }
            });
            $(".jqmWindow", $("#selectuserForm")).jqmHide();
        }
        else {
            alert("请选择往来个人！");
        }
    });
    //根据输入的往来个人名称查询
    $("input#searchuu").click(function(){
        cuID = "";
        userstaffID = "";
        var typeName = $("input#contactUserID", $("table#searchuser")).val();
        var relation = $("select#relation", $("table#searchuser")).val();
        cxwlgr("contactUser.staffName=" + typeName + "&contactUser.relation=" + relation);
    });
    //上一页
    $("#grsy").click(function(){
        var sy = $("#grsy").attr("title");
        if (sy != 0) {
            cuID = "";
            userstaffID = "";
            var typeName = $("input#contactUserID", $("table#searchuser")).val();
            var relation = $("select#relation", $("table#searchuser")).val();
            var typeCN = "contactUser.staffName=" + typeName + "&contactUser.relation=" + relation + "&pageForm.pageNumber=" + sy;
            cxwlgr(typeCN);
        }
        else {
            alert("已是首页！");
        }
    });
    //下一页
    $("#grxy").click(function(){
        var xy = $("#grxy").attr("title");
        if (xy != 0) {
            cuID = "";
            userstaffID = "";
            var typeName = $("input#contactUserID", $("table#searchuser")).val();
            var relation = $("select#relation", $("table#searchuser")).val();
            var typeCN = "contactUser.staffName=" + typeName + "&contactUser.relation=" + relation + "&pageForm.pageNumber=" + xy;
            cxwlgr(typeCN);
        }
        else {
            alert("已是尾页！");
        }
    });
    //ajax查询往来个人列表
    function cxwlgr(typeCN){
        if (notoken) {
            alert("正在获取数据！请稍等");
            return;
        }
        notoken = 1;
        $("#grsy").attr("title", 0);
        $("#grxy").attr("title", 0);
        $("#grzy").attr("title", 0);
        var searchurl = basePath + "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
        $.ajax({
            url: encodeURI(searchurl + typeCN + "&date=" + new Date().toLocaleString()),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                
                var pageForm = member.pageForm;
                var codeRelationList = member.codeRelationList;
                if (pageForm == null) {
                    alert("没有数据");
                    notoken = 0;
                    return;
                }
                var $se = $("select#relation", $("table#searchuser"));
                $se.empty();
                $select = "<option selected='selected' value = ''>--全部--</option>";
                $se.append($select);
                for (var i = 0; i < codeRelationList.length; i++) {
                    $op = $("<option />");
                    $op.attr("value", codeRelationList[i].codeValue).text(codeRelationList[i].codeValue);
                    $se.append($op);
                }
                var dqy = pageForm.pageNumber;//当前页
                var zys = pageForm.pageCount;//总页数
                if (dqy > 1) {
                    $("#grsy").attr("title", dqy - 1);
                }
                if (dqy < zys) {
                    $("#grxy").attr("title", dqy + 1);
                }
                $("span#grzycount").text(zys);
                var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
                tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>" +
                "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th><th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
                for (var i = 0; i < pageForm.list.length; i++) {
                    tabletr += "<tr style='cursor: hand;' id = " + pageForm.list[i].relationID + " title= " + pageForm.list[i].staffID + ">";
                    tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value=" + pageForm.list[i].relationID + " name='checkradio'/></td>";
                    tabletr += "<td id='contactUserName' align='center'>" + pageForm.list[i].staffName + "</td>";
                    tabletr += "<td id='phone' align='center'>" + pageForm.list[i].relation + "</td>";
                    tabletr += "<td id='tel' align='center'>" + pageForm.list[i].reference + "</td>";
                    tabletr += "<td id='staffIdentityCard' align='center'>" + pageForm.list[i].staffIdentityCard + "</td>";
                    tabletr += "<td id='userQq'  align='center'>" + pageForm.list[i].referenceCode + "</td>";
                    tabletr += "<td id='email'  align='center'>" + pageForm.list[i].referenceOrganization + "</td>";
                    tabletr += "<td id='userAddr'  align='center'>" + pageForm.list[i].staffAddress + "</td>";
                    tabletr += "<td id='contactUserID'  style='display:none' align='center'>" + pageForm.list[i].staffID + "</td>";
                    tabletr += " </tr>";
                }
                tabletr += " </table>";
                $("#body_02cu").html(tabletr);
                $("#body_02cu").show();
                alert("数据加载成功");
                notoken = 0;
                window.status = "数据加载成功";
            },
            error: function cbf(data){
                notoken = 0;
                alert("数据获取失败！");
            }
        });
    }
});
