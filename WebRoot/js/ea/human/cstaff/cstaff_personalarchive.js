// ajax查询物品通过芯片
var chipids = new Array();
var i=0;
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				
				loadcabs.window.closePort();//关闭读数据端口
				// ajax查询物品通过芯片
                chipids = new Array();
                i=0;
				
			});
	$('.JQueryflexmepost').flexigrid({
		height : 'auto',
		width : 'auto',
		title : "在职员工资料库存",
		minwidth : 20,
		minheight : 100,
		buttons : [{
			name : '查看',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		},{
			name : '查询',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}, {
			name : '打印条码',
			bclass : 'checkout',
			onpress : action
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
	function action(com, grid) {
		switch (com) {
			case '查看' :
			if(staffID==""){
				alert("请选择");
				return;
			}
			var url = basePath
			+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
			+ staffID;
	        window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');
			break;
		
			case '查询' :
					$(".jqmWindow", $("#goodsForm")).jqmShow();
					$("#goodsForm #loadcabs")
						.attr(
								"src",
								basePath
										+ "page/ea/main/human/cstaff/loadActiveX.html?code="
										+ Math.random());
				$("#tbody").html("");
				$(".scan").hide();
				$(".manual").show();
				$("#searchGood").hide();						
				break;
			case '打印条码': 
			    if(staffID==""){
				alert("请选择");
				return;
			    }
				var code = $("#" + staffID).find("#recordCode").text(); 
				var staffName = $("#" + staffID).find("#staffName").text();
				window.open(basePath + "ea/goodsmanage/ea_barcode.jspa?parameter=" + encodeURI(code)+"&staffName="+encodeURI(staffName));
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/personalarchive/ea_getListPersonalInfo.jspa?type=archive&search="
						+ search;
				numback(url);
				break;
		}
	}

	// 条码查询时捕捉回车
	document.onkeydown = function(evt) {// 捕捉回车
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		var activeElementId = document.activeElement.id;// 当前处于焦点的元素的id
		if (key == 13 && activeElementId == "bc") {
			queryByBarCode();// 要触发的方法
		}

	};
			
	
	// 根据输入的条码查询
	$("input#searchGood").click(function() {
				var recordCode = $("#recordCode", $("table#searchgood")).val();
				if($("#tbody").html().indexOf($.trim(recordCode))>0){
	 	            return;
	 	       }
				$(":input#parms").val("parameter=" + recordCode);

				cx("parameter=" + recordCode);
			});
	//手动输入点击
	$(".manual").click(function(){
	 $(this).hide();
	 $(".scan").show();
	 $("#searchGood").show();
//	 $("#recordCode", $("table#searchgood")).removeAttr("readonly");
		
		
	});
	
	// 扫描输入点击
	$(".scan").click(function() {
				$(this).hide();
				$(".manual").show();
				$("#searchGood").hide();
//				$("#recordCode", $("table#searchgood")).attr({
//							readonly : 'true'
//						});
			});
			
			
		 setInterval(function() {
	 	
	 	var recordCode = $("#recordCode", $("table#searchgood")).val();
	    if ($("#goodsForm .scan").is(":hidden")) {
	    	if($("#tbody").html().indexOf($.trim(recordCode))>0){
	 	       $("#recordCode", $("table#searchgood")).val("");
	 	        return;
	 	       }
		if (recordCode != "") {
			$("input#parms").val("parameter=" + recordCode);
			cx("parameter=" + recordCode);
			$("#recordCode", $("table#searchgood")).val("");
			
		}

	}
	 },1000);

	// ajax查询物品列表
	function cx(typeCN) {
//		if (notoken) {
//			alert("正在获取数据！请稍等");
//			return;
//		}
		notoken = 1;
		var searchurl = basePath
				+ "ea/personalarchive/sajax_ea_getGoodsManage.jspa?type=archive&";
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
					notoken = 0;
					return;
				}
				var tabletr = "";
				for (var i = 0; i < pageForm.list.length; i++) {
					var staffIDs ='"'+ pageForm.list[i].staffID+'"'; 
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
							+ pageForm.list[i].staffID + " name='check'/></td>";
					tabletr += "<td id='staffCode' align='center'>"
							+ pageForm.list[i].staffCode + "</td>";
					tabletr += "<td id='recordCode'  align='center'>"
							+ pageForm.list[i].recordCode + "</td>";
					tabletr += "<td id='staffName'  align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='usedNmae' align='center'>"
							+ pageForm.list[i].usedNmae + "</td>";
					tabletr += "<td id='sex' align='center'>"
							+ pageForm.list[i].sex + "</td>";
					tabletr += "<td id='staffID' style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += "<td id='birthday'  align='center'>"
							+ pageForm.list[i].birthday + "</td>";
					tabletr += "<td id='nationality'  align='center'>"
							+ pageForm.list[i].nationality + "</td>";
					tabletr += "<td id='nativePlace'  align='center'>"
							+ pageForm.list[i].nativePlace + "</td>";
					tabletr += "<td id='nation'  align='center'>"
							+ pageForm.list[i].nation + "</td>";
					tabletr += "<td id='staffIdentityCard'  align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
							+ staffIDs + ")'>查看</a></td>";
					tabletr += " </tr>";
				}
				$("#tbody").append(tabletr);
				$("#body_02").show();
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("获取物品出错！");
			}
		});
	}
	
	
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
			
			
			
			
			
	$(".JQueryflexmepost tr").click(function() {
				staffID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryflexmepost tr").dblclick(function() {
				staffID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

});

function re_load() {
	if (token)
		document.location.href = pbasePath
				+ "/ea/personalarchive/ea_getListPersonalInfo.jspa?type=archive";
}

// 快速条码查询
function queryByBarCode() {
	$("#postSearchForm #barcodes").val($("#bc").val());
	$("#postSearchForm").attr("action",
			basePath + "ea/personalarchive/ea_toSearch.jspa?pageNumber=" + pNumber);
	document.postSearchForm.submit.click();
}

// 查看详情
function viewDetail(staffID) {
//	 alert(staffID);
//	 var url = basePath +
//	 "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
//	 + staffID;
//	 window.open(url);
	var url = basePath
			+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
			+ staffID;
	window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');

}


function QueryArchiveInfo(typeCN) {
	typeCN='"'+typeCN+'"';
	setTimeout("getArchive("+typeCN+")",100);
}

function getArchive(typeCN){
	for (var i = 0; i < chipids.length; i++) {
	if (chipids[i].indexOf(typeCN)> -1) {
         return false;
	  }
	}
    chipids[i]=typeCN;
	typeCN = "parameter=" + typeCN;
	notoken = 1;
	var searchurl = basePath
			+ "ea/personalarchive/sajax_ea_QueryArchiveInfo.jspa?type=out"
			+ "&";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var staffinfo = member.staffinfo;
			if(staffinfo==null){
				return;
			}
			var tabletr = "";
			var staffIDs = '"' + staffinfo.staffID + '"';
			tabletr += "<tr style='cursor: hand;' id = "
					+ staffinfo.staffID + ">";
			tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
					+ staffinfo.staffID + " name='check'/></td>";
			tabletr += "<td id='staffCode' align='center'>"
					+ staffinfo.staffCode + "</td>";
			tabletr += "<td id='recordCode'  align='center'>"
					+ staffinfo.recordCode + "</td>";
			tabletr += "<td id='staffName'  align='center'>"
					+ staffinfo.staffName + "</td>";
			tabletr += "<td id='usedNmae' align='center'>"
					+ staffinfo.usedNmae + "</td>";
			tabletr += "<td id='sex' align='center'>" + staffinfo.sex
					+ "</td>";
			tabletr += "<td id='staffID' style='display:none' align='center'>"
					+staffinfo.staffID + "</td>";
			tabletr += "<td id='birthday'  align='center'>"
					+ staffinfo.birthday + "</td>";
			tabletr += "<td id='nationality'  align='center'>"
					+ staffinfo.nationality + "</td>";
			tabletr += "<td id='nativePlace'  align='center'>"
					+ staffinfo.nativePlace + "</td>";
			tabletr += "<td id='nation'  align='center'>"
					+ staffinfo.nation + "</td>";
			tabletr += "<td id='staffIdentityCard'  align='center'>"
					+ staffinfo.staffIdentityCard + "</td>";
			tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
					+ staffIDs + ")'>查看</a></td>";
			tabletr += " </tr>";
			$("#tbody").append(tabletr);
			$("#body_02").show();
			notoken = 0;
		},
		error : function cbf(data) {
			notoken = 0;
			alert("获取物品出错！");
		}
	
	});
	i++;
}