$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var query = "<form name='SearchForm' id='SearchForm' method='post'>" +
	"<input type='submit' name='submit' style='display: none'/>" +
	"<span style=\"margin-left:10px;\">单位名称：</span>" +
	"<input type='text' style=\"width: 80px\" id='companyName' name='contactCompany.companyName'/>" +
	"<span style=\"margin-left:10px;\">单位地址：</span>" +
	"<input type='text' style=\"width: 80px\" id='companyAddr' name='contactCompany.companyAddr' onfocus='blue()'/>" +
	"<span style=\"margin-left:10px;\">行业类别：</span>" +
	"<input type='text' style=\"width: 50px\" id='type' name='contactCompany.industryType' />" +
	"<span style=\"margin-left:10px;\">单位责任人：</span>" +
	"<input type='text' style=\"width: 50px\" id='cresponsible' name='contactCompany.cresponsible'/>" +
	"<span style=\"margin-left:10px;\">录入人员：</span>" +
	"<input type='text' style=\"width: 50px\" id='entryPersonnel' name='contactCompany.entryPersonnel'/>" +
	"<span style=\"margin-left:10px;\">部门：</span>" +
	"<input type='text' style=\"width: 80px\" id='entryoName' name='contactCompany.entryoName'/>" +
	"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='tosearch'/>" +
	"<input name='search' type='hidden' value='search' />" +
	"</form>";

	$('.JQueryflexme').flexigrid({
				height :'100',
				width : 'auto',
				minwidth : 30,
				title : '单位客户咨询管理'+query,
				minheight : 80,
				buttons : [{
					name : '选择添加',
					bclass : 'add',
					onpress : action2
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '添加客户类别',
					bclass : 'excel',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action2(com, grid) {
		switch (com) {
			case '选择添加':
				accift('01',paret);
				break;
			case '查看' :
				if (personvalue == "") {
					alert('请选择往来单位');
					return;
				}
				var url = basePath
						+ "ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="
						+ personvalue;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查询' :
				LiuZhongYaoDeShaGuaDiZhisearch("");
				$("#jqModelSearch").jqmShow();
				break;
			case '添加客户类别':
				if (personvalue == ""){
					alert('请选择单位');
					return;
				}
				$td = $("td.code");
				$td.children('select').empty();
				$td.children('select:gt(0)').hide();
				
				var url = basePath
					+ "ea/stafftrack/sajax_ea_getSelectLists.jspa?date="
					+ new Date().toLocaleString();
				$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var codeRelationList = member.codeRelationList;
						    $select = "<option selected='selected' value = ''>--请选择--</option>";
							$td.children('select:eq(0)').append($select);
							for (var i = 0; i < codeRelationList.length; i++) {
								$op = $("<option/>");
								$op.val(codeRelationList[i].codeID)
										.text(codeRelationList[i].codeValue)
										.attr("id",codeRelationList[i].codeID);
								$td.children('select:eq(0)').append($op);
							}
							notoken = 0;
							$add = "<option class='add'  value ='001' >--新增--</option>";
							$td.children('select:eq(0)').append($add);
							$td.children('select:eq(0)').show();
							$("div#selectcode").jqmShow();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
				});
				break;
			
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/companytrack/ea_getCompanyList.jspa?search="+search+"&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			
			case '单位菜单' :
				$(".menu00").toggle();
				break;

			
		}
	}
	$("#searchStaff").click(function() {
		var conValue = $("#contactConnections").children('option:selected').text();
		var conVal = '';
		if(conValue != '请选择'){
			conVal = conValue.substring(conValue.indexOf('├')+1); //客户类别名称
		}
		var addr = "";
		$(".JQueryaddresssearch").find("select").find("option[value]:selected")
				.each(function() {
					if ($(this).text() != '--新增--'
							&& $(this).text() != '--请选择--')
						addr = addr + $(this).text();
		});
		$("#cstaffSearchForm").find("input#companyAddr").val(addr);
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "/ea/companytrack/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&contactConnectionsVal=" + conVal);
		document.getElementById("cstaffSearchForm").submit();
	});

	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/companytrack/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		companyName = $(this).find("span#companyName").text();
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	
	$("input.JQueryreturns").click(function() {// 添加客户类别取消
		retoken = 0;
		$("div#selectcode").jqmHide();
	});
	
	$("input#savecode").click(function() {// 客户类别保存
		if (retoken)
			return;
		retoken = 1;
		var relats = '';
		$("td.code").children('select:lt(' + opaNum + ')').each(function(){
			var relat = $(this).children("option:selected").text();
			relats = relats + relat + "-";
		});
		relatAll = relats.substring(0,relats.lastIndexOf("-"));
		var wlgrurl = basePath
				+ "ea/stafftrack/sajax_ea_isContactUser.jspa?trackRelation.foreignKeyID="
				+ personvalue + "&trackRelation.contactConnections=" + relatAll
				+ "&date=" + new Date().toLocaleString();
		$.ajax({
				url : encodeURI(wlgrurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var c = member.c;
					retoken = 0;
					if (c == '1') {
						alert("添加客户类别成功！");
						$("div#selectcode").jqmHide();
					} else if(c == '2'){
						alert("客户类别已修改！");
						$("div#selectcode").jqmHide();
					}
					token = 2;
					re_load();
				},
				error : function cbf(data) {
					retoken = 0;
					alert("数据获取失败！");
				}
		});
	});
	
	/** ************************************客户类别********************************** */
	$('td.code select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.code");
		$td.children('select:gt(' + num + ')').empty();
		var codePID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		var codeID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		//var codeValue = $td.children('select:eq(' + num + ')').children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			codePID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			codeID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			//codeValue = $td.children('select:eq(' + numbers + ')').children("option:selected").text();
			$("#selectcode").jqmHide();
			$("input#codePID", $("#clientCodeForm")).val(codePID);
			$("input#treenum", $("#clientCodeForm")).val(num);
			$("input#codePID", $("#clientCodeForm")).attr("title", "selectcode");
			$("input#codeNumber", $("#clientCodeForm")).val(maxNum);
			$("#jqModelkf").jqmShow();
			$("input#codeValue", $("#clientCodeForm")).focus();
			return;
		}
		if (codeID == "") {
			var numbers = parseInt(num) - 1;
			codeID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			//codeValue = $td.children('select:eq(' + numbers + ')').children("option:selected").text();
			$td.children('select:gt(' + num + ')').hide();
			notoken = 0;
			return;
		}
		var codeurl = basePath
				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=";
		$.ajax({
			url : encodeURI(codeurl + codeID + "&date="
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
				var codeList = member.codeList;
				$td = $("td.code");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < codeList.length; i++) {
					$op = $("<option />");
					$op.attr("value", codeList[i].codeID).attr("id",
							codeList[i].codeID)
							.text(codeList[i].codeValue);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + codeID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				$td.children('select:gt(' + number + ')').hide();
				opaNum = number;
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	
	$(".JQuerySubmitkf").click(function(){ //保存客户类别
	 	$(".put3").trigger("blur");
        if($(".error",$("#clientCodeForm")).length){ 
            return;
        }  
        parmiter = $("#parmiter").val();
        var url = basePath + "ea/ccode/sajax_ea_ajaxSavaCCode.jspa?pageNumber=${pageNumber}&parmiter="
        		+ parmiter + "&code.codeNumber=" + $("#codeNumber").val() + "&code.codeValue="
        		+ $("#codeValue").val() + "&code.codeDesc=" + $("#desc").val() + "&code.codePID="
        		+ $("input#codePID", $("#clientCodeForm")).val();
        $.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var code = member.code;
				var divid = $("input#codePID", $("#clientCodeForm")).attr("title");
				$op1 = $("<option selected='selected'/>").attr("value",
						code.codeID).attr("id", code.codeID)
						.text(code.codeValue);
				var treenum = $("input#treenum", $("#clientCodeForm")).val();
				var num = parseInt(treenum);
				$("select:eq(" + num + ")", $("#" + divid)).append($op1);
				$select = "<option selected='selected'>--请选择--</option>";
				var number = num + 1;
				$("select:eq(" + number + ")", $("#" + divid)).append($select);
				$add = "<option class='add'  value = '" + code.codeID
						+ "' >--新增--</option>";
				$("select:eq(" + number + ")", $("#" + divid)).append($add);
				$("select:eq(" + number + ")", $("#" + divid)).show();
				codeID = code.codeID;
				//codeValue = code.codeValue;
				notoken = 0;
				alert("添加成功！");
				$(".error",$("#clientCodeForm")).remove();
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	
	$(".JQueryreturnkf").click(function() { //取消添加客户类别
		notoken = 0;
		document.clientCodeForm.reset();
		$(".error",$("#clientCodeForm")).remove();
		var divid = $("input#codePID", $("#clientCodeForm")).attr("title");
		$("#jqModelkf").jqmHide();
		$("#" + divid).jqmShow();
	});
	/** ************************************************************** */
				
	/*	//企业 证件管理
	$("#dm1").click(function() {
			if (personvalue == "") {
				alert("请选择具体单位！");
				return;
			}
			$("#mainframe").css({width:"100%",height:"auto"})
					.attr(
							"src",
							basePath
									+ "ea/certificate/ea_getaCertificateList.jspa?certificate.ccompanyID="
									+ personvalue + "&pageNumber=4");
			$(window).resize();
	});			
	//企业 联系方式
	$("#dm2").click(function() {
		if (personvalue == "") {
					alert("请选择具体单位！");
					return;
				}
				$("#mainframe").css({width:"100%",height:"auto"})
						.attr(
								"src",
								basePath
										+ "ea/contacttype/ea_getContactTypeList.jspa?pageNumber=4&contactType.ccompanyID="
										+ personvalue);
				$(window).resize();
	});
	//企业 银行帐号
	$("#dm3").click(function() {
		if (personvalue == "") {
					alert("请选择具体单位！");
					return;
				}
				$("#mainframe").css({width:"100%",height:"auto"})
						.attr(
								"src",
								basePath
										+ "ea/registration/ea_getListRegistration.jspa?registration.ccompanyID="
										+ personvalue + "&pageNumber=4");
				$(window).resize();
	});
	
	//企业 个人列表
	$("#dm4").click(function() {
		if (personvalue == "") {
					alert("请选择具体单位！");
					return;
				}
				personurl = basePath + "ea/cs/ea_getPerson.jspa?companyName="+companyName+"&companyID=";
				$("#mainframe").css({width:"100%",height:"auto"}).attr("src", personurl+personvalue);
				$(window).resize();
	});
	*/
	//双击查看修改
	$(".JQueryflexme tr[id]").dblclick(function() {
				var url = basePath  
						+ "ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="
						+ personvalue;
				window.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				
			});
	// //////////////////////////////地址查询!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	//var searchPID;// 当点新曾时,上一级被选中项的id
	var searchrovince;// 被改变的那个的id
	function LiuZhongYaoDeShaGuaDiZhisearch(address) {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");

		// 非空验证End
		$td = $("td.JQueryaddresssearch");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddresssearch");
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0&date="
					+ new Date().toLocaleString();
			$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date=" + new Date().toLocaleString();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddresssearch select[number]').change(function() {
		if (retoken)
			return;
		retoken = 1;

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddresssearch");
		searchrovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		//var D = $(searchrovince, $td).children("option:selected").attr("class");
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var searchdistrictPID = $(searchrovince, $td)
				.children("option:selected").val();
		if (searchdistrictPID == '--请选择--') {
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ searchdistrictPID + "&date=" + new Date().toLocaleString();
		$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var distinctlist = member.distinctlist;
					$select = "<option selected='selected'>--请选择--</option>";
					$(searchrovince, $td).next().append($select);
					if (distinctlist.length) {
						for (var i = 0; i < distinctlist.length; i++) {
							$op = $("<option/>");
							$op.attr("value", distinctlist[i].districtID)
									.attr("id", distinctlist[i].districtID)
									.text(distinctlist[i].districtName);
							$(searchrovince, $td).next().append($op);
						}
					}
					$td.find('input#address').val(searchdistrictPID);
					retoken = 0;
				},
				error : function cbf(data) {
					retoken = 0;
					alert("数据获取失败！");

				}
		});
	});
});

$(document).ready(function(){
	//序号自动生成
    var numurl = basePath + "ea/ccode/sajax_ea_getCodeNum.jspa";
    $.ajax({
            url: encodeURI(numurl),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
                if(nologin){
                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
                }
                maxNum = member.maxNum;
			},
			error: function cbf(data){
                alert("数据获取失败！");
            }
    });
});

$(function() {
   	var treeid = 'scode20121015uqn3qtck280000000003'; //客户类别
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };									
                }
                ts = new TreeSelector($("#contactConnections","#jqModelSearch")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
});
function paret(e){
	var arr = e.split(",");
	var wlgrurl = basePath
			+"/ea/companytrack/sajax_ea_saveCompanyTrack.jspa?ccompanyID="+arr[0]+"&date=" + new Date();
	$.ajax({
				url : encodeURI(wlgrurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {alert(data)
					var member = eval("(" + data + ")");
					var c = member.c;
					retoken = 0;
					if (c) {
						alert("已经添加了咨询跟踪！");
					} else {
						alert("添加咨询跟踪成功！");
						document.location.href=basePath+"/ea/companytrack/ea_getCompanyList.jspa?";
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	
}
function re_load(){
	if(token){
		 document.location.href=basePath+"/ea/companytrack/ea_getCompanyList.jspa?search="+search+"&sdate=" + sdate + "&edate=" + edate+"&pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
	}
};