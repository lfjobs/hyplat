var deatch2="";
$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20 // 
	}).jqmAddClose('.close');
	
	   var mydate = new Date();
	   var str = "" + mydate.getFullYear() + "年";
	   str += (mydate.getMonth()+1) + "月";
	   str += mydate.getDate() + "日";
	   $("#jy").text(str+"结余："+jy);
	
	//拨款
	$("#pass").click(function(){
		notoken = 1;
		if ($("#CashApplyBillsform .error").length) {
			$("input.posnum").each(function(){
				if($.trim($(this).val())==''){
					$(this).css("background-color","red");
				}
			});
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		var bj=0;
		$("tr[id]",$("#CashApplyBillsform")).each(function(){
			var sqje=parseFloat($.trim($(this).find("#money").text()));
			var pzje=parseFloat($.trim($(this).find("#appropriationMoney").attr("value")));
			if(pzje>sqje){
				$(this).find("#appropriationMoney").css("color","red");
				$(this).find("#appropriationMoney").css("background-color","blue");
				bj++;
			}
		});
		if(bj){
			alert("批准金额不得大于申请金额！！");
			notoken = 0;
			return;
		}
		title="";
		$("#CashApplyBillsform").attr("target","hidden")
			.attr(
					"action",
					basePath
							+ "ea/cashapplybills/ea_SaveCashApplyBills.jspa?");
		document.CashApplyBillsform.submit.click();
	    bokuan="";
		token = 2;
	});
	
	//暂不拨款
	$("#nopass").click(function(){
		$("#CashApplyBillsform").attr("target","hidden")
			.attr("action",basePath
							+ "ea/cashapplybills/ea_SaveCashApplyNoBills.jspa");
		document.CashApplyBillsform.submit.click();
	    bokuan="";
		token = 2;
	});
    
	$("#DaoRuFan2").click(function(){// 返回
	    $("#yhbankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd2").click(function(){// 选择确定
		var childopertionID = window.frames["yhdaoRu"].opertionID;
		var childopertionName =window.frames["yhdaoRu"].$('tr#'+childopertionID).find("span#childbankName").text();
		$("tr#"+goodsBillsID).find("#appropriationNum").attr("value",childopertionName);
		$("#yhdaoRu").attr("src","");
		$("#yhbankJqm").jqmHide();
	});
	
	$("#moc").live("click",function(){
		var type=$(this).attr("title");
		if(type=="xmtype"){
			getCate($("#inputmoc").val(),$(this).attr("name"));
		}else if(type=="xm"){
			getCate2($("#inputmoc").val(),$(this).attr("name"));
			
		}else if(type=="wp"){
			getCate3($("#inputmoc").val(),$(this).attr("name"));
			
		}
	});
	
	$("#mocc").live("click",function(){
		var type=$(this).attr("title");
		document.location.href =basePath+"/ea/splitbill/ea_reportform.jspa?level=organization&sztype="+type+"&searchValue="+$("#searchValue").val();
	});
	
});
	
function yhzh(ids){
	goodsBillsID=ids;
	$("#yhdaoRu").attr("src",basePath+"ea/institutionsregistration/ea_getListInstitutionsRegistrationCopy.jspa");
	$("#yhbankJqm").jqmShow();
}
	
	
function getPID(type,el){
	var divstr="<div style='margin-top:5px;float:left;width:100%'>模糊查询<input type='text' size='10' id='inputmoc' /><input type='hidden' name='searchValue' id='searchValue' />" +
			"<input type='button' class='btncon querybtn' id='moc' title='"+type+"'/>&nbsp;&nbsp;&nbsp;&nbsp;<input type='button' id='mocc' value='确定' title='"+type+"'/></div>" +
			"<div style='overflow:auto;z-index: 4 ;filter : Alpha(opacity=100);width: 210px; height: 300px;'>";
	$("#jqModel").empty();
	if(type=="xmtype"){
		divstr+="<div><input type='hidden' id='selectxm' /> <input type='hidden' id='selectxms' /></div>" +
		"<div class='mohuc' style='display:none;'></div><div id='treeg'><iframe src='"
		+ basePath
		+ "page/ea/main/finance/invoicing/oprojecttree.jsp?codeID=scode201410284shpd9x4fa0000000005&title=项目分类'" +
				" width='600' height='300'></iframe></div>";
	}else if(type=="xm"){
		divstr+="<div class='mohuc'></div>";
		
	}else if(type=="wp"){
		divstr+="<div class='mohuc'></div>";
		
	}
	divstr+="</div>";
	$("#jqModel").append(divstr);
	if(type=="xm"){
		getCate2();
	}else if(type=="wp"){
		getCate3();
	}
	
	var pel=$(el).parent();
	var offset=$(pel).offset();
	var left=offset.left;
	var top=offset.top;
	$("#jqModel").css("left",left);
	$("#jqModel").css("top",top+20);
	$("#jqModel").show();

}

/** ***********************项目分类模糊查询**************************** */
//键入时查询项目分类
function getCate(value,type) {
	var url = basePath + "/ea/splitbill/sajax_ea_getProjectList.jspa?date="
	+ new Date().toLocaleString();
	$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				parameter : $.trim(value),
				parameterTyle:type
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var xmList = member.xmlist;
				var params = "c.xmtype=";

				var str = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\");'>所有项目分类</a></span><br/>";

				for ( var i = 0; i < xmList.length; i++) {
					params = "c.xmtype='"+xmList[i].codeSn+"'";
					str += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\",this);'>("
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

//键入时查询项目名称
function getCate2(value,type){
	var url = basePath + "/ea/splitbill/sajax_ea_getProjectList.jspa?date="
		+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			parameter : $.trim(value),
			parameterTyle:type
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var gngid = member.gngid;
			var params = "c.projectName=";
			
			var divstr = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\");'>所有项目</a></span><br/>";
		
			for ( var i = 0; i < gngid.length; i++) {
				params = "c.projectName='"+gngid[i]+"'";
				if(gngid[i]==null||gngid[i]==""){
					continue;
				}
				divstr += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\",this);'>"
						+ gngid[i] + "</a></span><br/>";
			}
			if (gngid.length <=0) {
				divstr += "&nbsp;无搜索结果";
			}
			$(".mohuc").html("");
			$(".mohuc").html(divstr);
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

//键入时查询物品
function getCate3(value,type){
	var url = basePath + "/ea/splitbill/sajax_ea_getgoods.jspa?date="
		+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			parameter : $.trim(value),
			parameterTyle:type
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var gngid = member.gngid;
			var params = "g.goodsName=";
			
			var divstr = "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\");'>物品</a></span><br/>";
		
			for ( var i = 0; i < gngid.length; i++) {
				params = "g.goodsName='"+gngid[i][0]+"'";
				if(gngid[i]==null||gngid[i]==""){
					continue;
				}
				divstr += "&nbsp;&nbsp;<span><a href='#' style='color:#333;text-decoration:none;' onclick='getProjectByxmtype(\""+params+"\",$(this));'>("
						+ gngid[i][0] +")"+gngid[i][1]+ "</a></span><br/>";
			}
			if (gngid.length <=0) {
				divstr += "&nbsp;无搜索结果";
			}
			$(".mohuc").html("");
			$(".mohuc").html(divstr);
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}

//根据项目分类获取项目
function getProjectByxmtype(prams,el) {
	alert(prams)
	var pel=$(el).parent().parent();
	$(pel).find("span").each(function(){
		$(this).removeClass("c");
	});
	$(el).parent().addClass("c");
	$("#searchValue").val(prams);
}
/** ***********************项目分类结束**************************** */
	

//确定
function submit(ulid,positionid){
	//获取选中的值
	var values = "";
	 
	if(ulid=="tree"){
		
		 values = $("#xmtypeddd").val();
	
	 }else if (ulid=="treeDate"){
		 values = $("#ddate").val();
			
	 }else{

	$("#"+ulid+" input:checked").each(function(){
		values+=$(this).val()+",";
		
	});
	  values = values.substring(0,values.length-1);
    }
	$("#"+positionid).val(values);
	$("#searchForm").attr("action",basePath+"ea/product/ea_getBudgetGoodList.jspa");
	document.searchForm.submit.click();

	
}

function re_load() {
	document.location.href =basePath+"ea/cashapplybills/ea_toCash.jspa?&cother=cother&weibokuan="+weibokuan+"&level="+level+"&str="+str;
}	

/************************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$(".xzwlaw").click(function() {
		goodsBillsID=$(this).parent().parent().attr("id");
		$("input#ccompanyID", $("table#searchcompany")).val("");
		$("select#contactConnections", $("table#searchcompany")).val("全部");
		$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
		cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			$("tr #" + contactcID).children("td").each(function() {
				if (this.id == "ccompanyID") {
					$("input#appropriationcompanyID", $("tr#"+goodsBillsID)).val($(this).text());
				}else if (this.id == "ccompanyname") {
					$("input#appropriationcompanyName", $("tr#"+goodsBillsID)).val($(this).text());
					$("span#appropriationcompanyName", $("tr#"+goodsBillsID)).text($(this).text());
				}
			});
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
		} else {
			alert("请选择往来单位！");
		}
	});
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		quzhi = contactConnections;
		cxwldw("contactCompany.companyName=" + typeName
				+ "&cconnection.contactConnections=" + contactConnections);
	});
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + sy;
			cxwldw(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#dwxy").click(function() {
		var xy = $("#dwxy").attr("title");
		if (xy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = "";
			if (quzhi != "") {
				contactConnections = quzhi;
			} else {
				contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
			}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + xy;
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
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
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
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				//alert("数据加载成功");
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

