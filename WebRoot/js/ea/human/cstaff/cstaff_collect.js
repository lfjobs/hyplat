$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '个人客户基本信息',
				minheight : 80,
				buttons : [{
					name : '添加',
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
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				}/*, {
					name : '咨询跟踪',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				}*/,{
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
				}/*, {
					name : '咨询跟踪统计报表',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				}
				, {
					name : '电话呼入呼出记录',
					bclass : 'mysearch',
					onpress : action2
						// 当点击调用方法
					}, {
					separator : true
				}*/]
			});
	function action2(com, grid) {
		switch (com) {
			case '添加' :
			
				var url = basePath
						+ "ea/stafftrack/ea_toSaveJsp.jspa?showType=add";
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			  	 break;
			case '查看' :
				if (personvalue == "") {
					alert('请选择人员');
					return;
				}
				var url = basePath
						+ "ea/stafftrack/ea_toSaveJsp.jspa?showType=edit&cstaff.staffID="
						+ personvalue;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查询' :
				LiuZhongYaoDeShaGuaDiZhisearch("");
				$("#jqModelSearch").jqmShow();
				break;
			case '咨询跟踪':
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				$("#mainframe").css({"height":"auto"}).attr("src",basePath
										+ "ea/track/ea_getTrackById.jspa?foreignKeyID="
										+ personvalue + "&pageNumber=4&status=0");
				$(window).resize();
				break;
			case '添加客户类别':
				if (personvalue == ""){
					alert('请选择人员');
					return
				}
				$td = $("td.code");
				$td.children('select').empty();
				$td.children('select:gt(0)').hide();
				
				var url = pbasePath
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
				var url = pbasePath
						+ "/ea/stafftrack/ea_getStaffList.jspa?search="+search+"&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			case '咨询跟踪统计报表':
				document.location.href=basePath+"/ea/collectpersonal/ea_getTracklist.jspa";
				break;
			case '电话呼入呼出记录' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				var ii = $("tr#"+personvalue).find("span#reference").html();
				$("#mainframe").css({width:"100%",height:"auto"}).attr("src",basePath
										+ "ea/tel/tel_telInOutList.jspa?foreignKeyID="
										+ personvalue +"&reference="+ii+ "&pageNumber=5&status=0&inOutType=1");
				$(window).resize();
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
		$("#cstaffSearchForm").find("input#staffAddress").val(addr);
		$("#cstaffSearchForm").attr(
				"action",
				pbasePath + "/ea/stafftrack/ea_toSearchCStaff.jspa?pageNumber="
						+ ppageNumber + "&contactConnectionsVal=" + conVal);
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find(":input[name]").val("");
	});
	
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
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
		var wlgrurl = pbasePath
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
					document.location.href=basePath+"/ea/stafftrack/ea_getStaffList.jspa?search="+search+"&sdate=" 
							+ sdate + "&edate=" + edate+"&pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
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
		//var codeValue = $td.children('select:eq(' + num + ')')
		//		.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			codePID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			codeID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			codeValue = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
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
			codeValue = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
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
				codeValue = code.codeValue;
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
			
	$("#DaoRuFanqd").click(function(){// 选择确定
		window.frames["daoRu"].$("input#selectPerson").click();
		//var checkform =$("#checkform",$("#bankJqm")).attr("value");
		//var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionIDs = window.frames["daoRu"].opertionID;
		var childopertionID=childopertionIDs.substring(0,childopertionIDs.length-1);
		if (childopertionID == "" || childopertionID.length == 0) {
					alert('请选择');
					return;
				}
		//var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		//var address=window.frames["daoRu"].$('tr#'+childopertionID).find("span#staffAddress").text();
		var url = basePath+ "/ea/stafftrack/sajax_ea_getCompanybyID.jspa?childopertionID="+childopertionID;
		$.ajax({
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
						var co=member.co;
						if(co==1){
							alert("不能重复添加");
							return;
						}else{
							window.location.href=basePath+ "/ea/stafftrack/ea_saveStaffTrack.jspa?childopertionID="+childopertionID;
							$("#daoRu").attr("src","");
        					$("#bankJqm").jqmHide();
						}
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");

					}
				});
		
   });
   
	// //////////////////////////////地址查询!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	//var searchPID;// 当点新曾时,上一级被选中项的id
	var searchrovince;// 被改变的那个的id
	//var searchdistrictPID;
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
			var url = pbasePath
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
		var urldistrict = pbasePath
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
		var url = pbasePath
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
	
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		staffName = $(this).find("span#staffName").text();
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	 $(".JQueryflexme tr[id]").dblclick(function(){
	 	$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        personvalue =this.id;
        staffName = $(this).find("span#staffName").text();
        action2("查看");
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
               // var selid;
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
//子窗口调用
function refresh(){
    window.location.reload();
} 

function re_load(){
	if(token){
		 document.location.href=basePath+"/ea/stafftrack/ea_getStaffList.jspa?search="+search+"&sdate=" + sdate + "&edate=" + edate+"&pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
	}
};