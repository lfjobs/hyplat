$(document).ready(function() {
	if(flexbutton == 'flexbutton'){
		var len = $("tr.trclass").length;
		parent.document.getElementById("mainframe12").style.height = 100 + len * 27 + "px";
	}
	
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	
	var query = "<form name='SearchForm' id='SearchForm' method='post'>" +
			"<input type='submit' name='submit' style='display: none'/>" +
			"<span style=\"margin-left:20px;\">单位名称：</span>" +
			"<input type='text' style=\"width: 90px\" id='companyName' name='contactCompany.companyName'/>" +
			"<span style=\"margin-left:10px;\">单位地址：</span>" +
			"<input type='text' style=\"width: 90px\" id='companyAddr' name='contactCompany.companyAddr' onfocus='blue()'/>" +
			"<span style=\"margin-left:10px;\">行业类别：</span>" +
			"<input type='text' style=\"width: 90px\" id='type' name='contactCompany.industryType' onfocus='leibie()'/>" +
			"<span style=\"margin-left:10px;\">单位责任人：</span>" +
			"<input type='text' style=\"width: 90px\" id='cresponsible' name='contactCompany.cresponsible'/>" +
	/*		"<span style=\"margin-left:10px;\">录入人员：</span>" +
			"<input type='text' style=\"width: 90px\" id='entryPersonnel' name='contactCompany.entryPersonnel'/>" +*/
			"<span style=\"margin-left:10px;\">邀请人账号：</span>" +
			"<input type='text' style=\"width: 90px\" id='accountID' name='contactCompany.accountID'/>" +
			"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='tosearch'/>" +
			"<input name='search' type='hidden' value='search' />" +
			"<input name='showType' type='hidden' value='"+showType+"' />" +
			"<input name='parameter' type='hidden' value='"+parameter+"' />" +
			"<input name='flag' type='hidden' value='"+flag+"' />" +
			"</form>";
	
	if(flexbutton == 'flexbutton'){
		$('.JQueryflexme').flexigrid({
	        allDouble:true,
			height : 'auto',
			width : 'auto',
			minwidth : 30,
			minheight : 80,
			buttons : [{
				name : '添加',
				bclass : 'add',
				onpress : action
				}, {
				separator : true
			}, {
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			}]
		});
	}
	if(flag=='web'&&gtype==''){
		$('.JQueryflexme').flexigrid({
	        allDouble:true,
			height : 'auto',
			width : 'auto',
			minwidth : 30,
			title : '社会往来单位'+query,
			minheight : 80,
			buttons : [{
				name : '添加',
				bclass : 'add',
				onpress : action
				}, {
				separator : true
			},{
				name : '修改',
				bclass : 'edit',
				onpress : action
				}, {
				separator : true
			}, {
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			}
			,
			{
				name : '取消网站',
				bclass : 'edit',
				onpress : action
			},{
				separator : true
			}
			]
		});
		
	}if(gtype=='office'){
		$('.JQueryflexme').flexigrid({
	        allDouble:true,
			height : 'auto',
			width : 'auto',
			minwidth : 30,
			minheight : 80,
			title : '单位网站',
			buttons : [{
				name : '修改',
				bclass : 'edit',
				onpress : action
				}, {
				separator : true
			}, {
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			}]
		});
		
	}else{
		$('.JQueryflexme').flexigrid({
	        allDouble:true,
			height : 180,
			width : 'auto',
			minwidth : 30,
			title : '社会往来单位'+query,
			minheight : 80,
			buttons : [{
				name : '添加',
				bclass : 'add',
				onpress : action
				}, {
				separator : true
			}, {
				name : '修改',
				bclass : 'edit',
				onpress : action
				}, {
				separator : true
			}, {
				name : '导出',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '导入',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},
			{
				name : '设置网站',
				bclass : 'edit',
				onpress : action
					//设置网站
				},{
				separator : true
			}, 
			{
				name : '客户登记表',
				bclass : 'excel',
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
			},  {
				name : '添加到往来关系',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			},{
				name : '添加咨询跟踪',
				bclass : 'mysearch',
				onpress : action
				}, {
				separator : true
			}]
		});
	}
		
	function action(com, grid) {
		switch (com) {
			case '添加' :	
			if(flexbutton == 'flexbutton'){
					hei = parent.document.getElementById("mainframe12").offsetHeight;
					parent.document.getElementById("mainframe12").style.height = 335 + "px";
					$("input#ccompanyID", $("table#searchcompany")).val("");
					$(".jqmWindowcss1").show();
					cxwldw("contactCompany.companyName=");
				}else{
					if(flag=='web'){
						if(quanxian!='company201009046vxdyzy4wg0000000025'){
							alert("权限级别不够！");
							return;
						}
					}
					var url = basePath
							+ "ea/contactcompany/ea_toSaveJsp.jspa?showType=add&date="+new Date()+"&flag="+flag;
					window.open(url);
				}
				break;
			case '修改' :
				if (ccompanyID == "") {
					alert('请选择!');
					return;
				}
			
				var url = basePath
						+ "ea/contactcompany/ea_toSaveJsp.jspa?showType=edit&ccompanyID="+ccompanyID+"&date="+new Date()+"&flag="+flag;
				window.open(url);
				break;
			case '添加到往来关系' :
				staffsize = 1;
				if (ccompanyID == "") {
					alert('请选择公司！');
					return
				}
				$td = $("td.code");
				$td.children('select').empty();
				$td.children('select:gt(0)').hide();
				
				var url = basePath
					+ "ea/contactcompany/sajax_ea_getConnectionList.jspa?date="
					+ new Date().toLocaleString();
				$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var connectionList = member.connectionList;
						    $select = "<option selected='selected' value = ''>--请选择--</option>";
							$td.children('select:eq(0)').append($select);
							for (var i = 0; i < connectionList.length; i++) {
								$op = $("<option/>");
								$op.val(connectionList[i].codeID)
										.text(connectionList[i].codeValue)
										.attr("id",connectionList[i].codeID);
								$td.children('select:eq(0)').append($op);
							}
							notoken = 0;
							$add = "<option class='add'  value ="+connectionList[0].codePID+" >--新增--</option>";
							$td.children('select:eq(0)').append($add);
							$td.children('select:eq(0)').show();
							$("div#selectcode").jqmShow();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
				});
				break;
			case '导出' :
				url = basePath + "ea/contactcompany/ea_showExcel.jspa?search="+search+"&showType="+showType+"&parameter="+parameter;
				open(url);
				break;
			case '导入' :
				url = basePath + "ea/importdata/ea_showImportDataPage.jspa?fileType=SHDWGL(YX)";				
				window.open(url,"importDataPage","height=400,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
				break;
			case '客户登记表':
				staffsize = 1;
				if (ccompanyID == "") {
					alert('请选择公司');
					return
				}
				var url = basePath
				+ "ea/cstaff/ea_getUtilInformation.jspa?contactCompany.ccompanyID="+ ccompanyID;
				window.open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/contactcompany/ea_getListContactCompany.jspa?search="
						+ search + "&flexbutton="+flexbutton+"&staffID=" + staffID+"&showType="+showType+"&parameter="+parameter+"&flag="+flag+"&gtype="+gtype;
				numback(url);
				break;
			case '添加咨询跟踪':
				if (ccompanyID == "") {
					alert("请选择具体人员！");
					return;
				}
				var relat = $("select#contactConnections").attr("value");
				var  wlgrurl = basePath
						+"/ea/companytrack/sajax_ea_saveCompanyTrack.jspa?ccompanyID="+ccompanyID+"&date=" + new Date();
				$.ajax({
							url : encodeURI(wlgrurl),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var c = member.c;
								retoken = 0;
								if (c) {
									alert("已经添加了咨询跟踪！");
									$("div#selectcode").jqmHide();
								} else {
									alert("添加咨询跟踪成功！");
									$("div#selectcode").jqmHide();
								}
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
						});
				break;
			case '设置网站' :
				if (ccompanyID == "") {
					alert('请选择公司');
					return
				}
			if(webstatus=='01'){
				alert("该公司已经拥有网站功能，请选择其他公司！");
				return;
			}
			document.location.href=basePath+"ea/contactcompany/ea_setWeb.jspa?ccompanyID="+ccompanyID+"&date=" + new Date()+"&flag=web";
				break;
			case '取消网站' :
				if (ccompanyID == "") {
					alert('请选择公司');
					return
				}
				document.location.href=basePath+"ea/contactcompany/ea_cancelWeb.jspa?ccompanyID="+ccompanyID+"&date=" + new Date()+"&flag=web";
				break;
		}
	}
	
	//子窗口调用
	function refresh(){
	    window.location.reload();
	} 

	$("input.JQueryreturn4").click(function() {// 取消
		window.close();
	});
	$("input.JQueryreturn5").click(function() {// 打印预览
		var tel=$("input#tel").val();
		var qq=$("input#qq").val();
		var emal=$("input#emal").val();
		var wang=$("input#wang").val();
		var ying=$("input#ying").val();
		var li=$("input#li").val();
		var mys=$("input#mys").val();
		var tress=$("input#tress").val();
		var idea=$("input#idea").val();
		var shao=$("#shao").val();
		var share=$("#share").val();
		var wen1=$("#wen1").val();
		var wen2=$("#wen2").val();
		var wen3=$("#wen3").val();
		var wen4=$("#wen4").val();
		var wen5=$("#wen5").val();
		var wen6=$("#wen6").val();
		var wen7=$("#wen7").val();
		var wen8=$("#wen8").val();
		var wen9=$("#wen9").val();
		var wen0=$("#wen0").val();
		var str ="";
				$("[name='cbox']").each(function() {
							if ($(this).is(':checked')) {
								str+=$(this).val()+"，"	;															
							}
						});
		str=str.substring(0,str.length-1) ;
		window.open( encodeURI(basePath+ "/ea/cstaff/ea_toprintutil.jspa?contactCompany.ccompanyID="
		+ ccompanyID +"&ying=" +ying+ "&li=" +li+ "&mys=" +mys+ "&tress=" 
		+tress+ "&idea=" +idea+ "&shao=" +shao+ "&share=" 
		+share+ "&tel=" +tel+ "&qq=" +qq+ "&emal=" +emal+ "&wang=" +wang+ "&wen1="
		 +wen1+ "&wen2=" +wen2+ "&wen3=" +wen3+ "&wen4=" +wen4+ "&wen5="
		  +wen5+ "&wen6=" +wen6+ "&wen7=" +wen7+ "&wen8=" +wen8+ "&wen9="
		   +wen9+ "&wen0=" +wen0+ "&str=" +str));	
	});
	
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	// 新加内容结束
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		ccompanyID = this.id;
		webstatus=$(this).find("span#webstatus").text();
		if (personurl) {
				$("#mainframe").attr("src", personurl + ccompanyID);
		}
		companyName = $(this).find("span#companyName").text();
	});
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		if(flexbutton != 'flexbutton')
			action('修改');
	});
	
	// /////////////////////////////添加到往来单位
	$("input#savecode").click(function() {// 保存
		var relats = '';
		$("td.code").children('select:lt(' + opaNum + ')').each(function(){
			var relat = $(this).children("option:selected").text();
			relats = relats + relat + "-";
		});
		relatAll = relats.substring(0,relats.lastIndexOf("-"));
		var wlgrurl = basePath
				+ "ea/contactcompany/sajax_ea_isContactConnection.jspa?cconnection.ccompanyID="
				+ ccompanyID + "&cconnection.contactConnections="
				+ relatAll +"&onlyCompany=&date=" + new Date();
		$.ajax({
				url : encodeURI(wlgrurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var c = member.c;
					retoken = 0;
					if (c) {
						alert("已经添加到往来关系！");
						$("div#selectcode").jqmHide();
					} else {
						alert("添加往来单位成功！");
						$("div#selectcode").jqmHide();
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
		});
	});
	
	/** ************************************往来关系********************************** */
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
			$("input#codePID", $("#companyCodeForm")).val(codePID);
			$("input#treenum", $("#companyCodeForm")).val(num);
			$("input#codePID", $("#companyCodeForm")).attr("title", "selectcode");
			$("input#codeNumber", $("#companyCodeForm")).val(maxNum);
			$("#jqModelkf").jqmShow();
			$("input#codeValue", $("#companyCodeForm")).focus();
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
	$(".JQuerySubmitkf").click(function(){ //保存单位往来关系
	 	$(".put3").trigger("blur");
        if($(".error",$("#companyCodeForm")).length){ 
            return;
        }  
        parmiter = $("#parmiter").val();
        var url = basePath + "ea/ccode/sajax_ea_ajaxSavaCCode.jspa?pageNumber=${pageNumber}&parmiter="
        		+ parmiter + "&code.codeNumber=" + $("#codeNumber").val() + "&code.codeValue="
        		+ $("#codeValue").val() + "&code.codeDesc=" + $("#desc").val() + "&code.codePID="
        		+ $("input#codePID", $("#companyCodeForm")).val();
        $.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var code = member.code;
				var divid = $("input#codePID", $("#companyCodeForm")).attr("title");
				$op1 = $("<option selected='selected'/>").attr("value",
						code.codeID).attr("id", code.codeID)
						.text(code.codeValue);
				var treenum = $("input#treenum", $("#companyCodeForm")).val();
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
				$(".error",$("#companyCodeForm")).remove();
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	
	$(".JQueryreturnkf").click(function() { //取消添加单位往来关系
		notoken = 0;
		document.companyCodeForm.reset();
		$(".error",$("#companyCodeForm")).remove();
		var divid = $("input#codePID", $("#companyCodeForm")).attr("title");
		$("#jqModelkf").jqmHide();
		$("#" + divid).jqmShow();
	});
	/** ************************************************************** */

	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/contactcompany/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});
	
	$(".close").click(function() {// 取消
		$(".jqmWindow").jqmHide();
		re_load();
	});
	
	/** **********************************社会往来单位**************************************** */	
	$("table#gostable tr[id]").live("click", function(event) {
		contactcID = this.id;
		$("input.ra", $(this)).attr("checked", "checked");
	});
	
	//查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		cxwldw("contactCompany.companyName=" + typeName);
	});
	
	//确定
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			var url = basePath + "ea/contactcompany/sajax_ea_saveCS.jspa?contactcID=" 
					+ contactcID + "&staffID=" + parent.staffID + "&date=" 
					+ new Date().toLocaleString();
			$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						$(".jqmWindow", $("#selectcompanyForm")).hide();
						parent.document.getElementById("mainframe12").style.height = hei + "px";
						token = 2;
						re_load();
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据添加失败！");
					}
			});
		} else {
			alert("请选择往来单位！");
		}
	});
	
	// 新增
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	
	//关闭
	$(".JQueryreturns1").click(function(){
		parent.document.getElementById("mainframe12").style.height = hei + "px";
		$(".jqmWindow", $("#selectcompanyForm")).hide();
	});
	
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
			var typeCN = "contactCompany.companyName=" + typeName
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
			var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&pageForm.pageNumber=" + xy;
			cxwldw(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	
	// ajax查询社会往来单位列表
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
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?searchCC=searchCC&";
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
				$("span#zycount").text(zys);
				var tabletr = "<table width='100%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='100%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th width='4%' height='30' align='center' bgcolor='#E4F1FA'>选择</th>";
			    tabletr += " <th width='20%' align='center' bgcolor='#E4F1FA'>单位名称</th>";
			    tabletr += " <th width='20%' align='center' bgcolor='#E4F1FA'>单位地址</th>";
				tabletr += " <th width='12%' align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th width='8%' align='center' bgcolor='#E4F1FA'>单位负责人</th>";
				tabletr += " <th width='12%' align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th width='8%' align='center' bgcolor='#E4F1FA'>行业类别</th>";
				tabletr += " <th width='8%' align='center' bgcolor='#E4F1FA'>单位备注</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位状态</th></tr>";
				
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr height='25' style='cursor: hand;' id = "
							+ pageForm.list[i].ccompanyID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].ccompanyID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='remark' align='center'>"
							+ pageForm.list[i].remark + "</td>";
					tabletr += "<td id='custStatus' align='center'>"
							+ (pageForm.list[i].custStatus == '01' ? '未注册单位' : '已注册单位')  + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
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

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?pageNumber="
				+ pNumber + "&flexbutton="+flexbutton+"&staffID="
				+ staffID+"&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

function blue(){
	$("#JQueryaddress").jqmShow();
}

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

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?pageNumber="
				+ pNumber + "&flexbutton="+flexbutton+"&staffID="
				+ staffID+"&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&showType="+showType+"&parameter="+parameter;
}

function blue(){
	$("#JQueryaddress").jqmShow();
}
function leibie(){
	$("#jqModelSearch").jqmShow();
	
}
//地域调查 
$(document).ready(function() {
	
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
	function LiuZhongYaoDeShaGuaDiZhi() {
		
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var url = basePath
				+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
				+ "&date1=" + new Date();
		$.ajax({
					url : url,
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
		
	$('td.JQueryaddress select[number]').change(function() {

		if (retoken)
			return;
		retoken = 1;
		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date3=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].districtID).attr(
								"id", distinctlist[i].districtID)
								.text(distinctlist[i].districtName);
						$(rovince, $td).next().append($op);
					}
				}
				
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");

			}
		});
	});	
	LiuZhongYaoDeShaGuaDiZhi();
});