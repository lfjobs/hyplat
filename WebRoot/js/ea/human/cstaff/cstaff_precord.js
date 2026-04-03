$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe5").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	
	$('.precord').flexigrid({
				height : 'auto',
				allDouble : true,
				width : 'auto',
				minwidth : 20,
				minheight : 100,
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find('input:gt(0)').each(function() {
					$(this).attr("name",
							"recordmap[" + select + "]." + this.name);
				});
				$("#sa" + select).find('select').each(function() {
					$(this).attr("name",
							"recordmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe5").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe5").style.height = heis;
				select++;
				break;
			case '修改' :
				if (recordID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + recordID);
				if ($p.hasClass("check")) {
					return;
				}
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"recordmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("a").removeClass("model1");
				$p.find("select").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#startTime", $(".check")).each(function(i, tmp) {
							if (this.value == "") {
								alert("请输入日期");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					notoken = 0;
					return;
				}
				$f = $('#recordForm');
				$('#recordForm')
						.attr("target", "hidden")
						.attr(
								"action",
								pbasePath
										+ "ea/precord/ea_savePRecord.jspa");
				document.recordForm.submit.click();
				token = 2;
				break;
			case '删除' :
				if (recordID == '') {
					alert("请选择！");
					return;
				}
				if (recordID.substring(0, 2) == "sa") {
					$("#" + recordID).remove();
					recordID = "";
					var heis = parent.document.getElementById("mainframe5").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe5").style.height = heis;
					return;
				}
				$f = $('#recordForm');
					$f.attr("target", "hidden")
							.attr(
									"action",
									pbasePath
											+ "ea/precord/ea_delPRecord.jspa?record.staffID="
											+ recordstaffID
											+ "&record.recordID=" + recordID);
					document.recordForm.submit.click();
					$("tr#" + recordID).remove();
					recordID = "";
					token = 11;
				break;
		}
	}

	$(".precord tr[id]").click(function() {
		recordID = $(this).attr('id');
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	$(".precord tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		recordID = this.id;
		action("修改");
	});
	
});

/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	//var ccID = "";// ccompanyID
	var dd="";
	var hei = 0;
	$(".JQueryreturns").click(function(){
		parent.document.getElementById("mainframe5").style.height = hei + "px";
		$(".jqmWindow", $("#selectcompanyForm")).hide();
	});
	$("table#gostable tr[id]").live("click", function(event) {
		contactcID = this.id;
		ccID = this.title;
		$("input.ra", $(this)).attr("checked", "checked");
	});
	// 选择往来单位
	$("#xzwlaw").click(function() {
		hei = parent.document.getElementById("mainframe5").offsetHeight;
		parent.document.getElementById("mainframe5").style.height = 300 + "px";
		
		dd=$(this).parent().parent().parent();
		$("input#ccompanyID", $("table#searchcompany")).val("");
		$("select#contactConnections", $("table#searchcompany"))
				.val("全部");
		$(".jqmWindowcss1").show();
		cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
	});
	// 选择往来单位
	$("#xzwla").live("click",function() {
		hei = parent.document.getElementById("mainframe5").offsetHeight;
		parent.document.getElementById("mainframe5").style.height = 300 + "px";
		
		dd=$(this).parent().parent().parent();
		$("input#ccompanyID", $("table#searchcompany")).val("");
		$("select#contactConnections", $("table#searchcompany"))
				.val("全部");
		$(".jqmWindowcss1").show();
		cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
	});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			$("input#companyName", dd).val($("#ccompanyname",$("tr #" + contactcID)).text());
			$("input#ccompanyID", dd).val($("#ccompanyID",$("tr #" + contactcID)).text());
			$(".jqmWindow", $("#selectcompanyForm")).hide();
			
			parent.document.getElementById("mainframe5").style.height = hei + "px";
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
		quzhi=contactConnections;
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
			if(quzhi != ""){
				 contactConnections=quzhi;
			}else{
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
			if(quzhi != ""){
				 contactConnections=quzhi;
			}else{
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
				//alert("数据加载成功")
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
	if (token)
		document.location.href = pbasePath
				+ "ea/precord/ea_getListPRecord.jspa?record.staffID="
				+ recordstaffID;
}