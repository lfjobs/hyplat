$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.wspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : '未阅读',
				minheight : 349,
				buttons : [{
					name : '查看详情',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看附件',
					bclass : 'attach',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '标记已读',
					bclass : 'mark',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转发',
					bclass : 'transmit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '共享',
					bclass : 'share',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '发布到网站',
					bclass : 'publish',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '审批跟踪登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},
					
//						{
//					name : '转移位置',
//					bclass : 'transfer',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				}, 
					
						{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					       name : '导出',
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
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '查看详情' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				var load = $("#hidload", "tr#" + docId).val();
				var print = $("#hidprint", "tr#" + docId).val();

				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toReadnoView" + "&load=" + load
						+ "&print=" + print + "&date=" + new Date();

				break;
			case '标记已读' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				$("#readForm #docID").val(docId);
				markReaded();

				break;
			case '查看附件' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,"1","未阅读");
				break;
			case '转发' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				var trans = $("#hidtran", "tr#" + docId).val();

				if (trans != "on") {
					alert("对不起，您无权限转发！");
					return;
				}

				selectPeople(docId);
				break;
			case '共享' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				var shares = $("#hidshare", "tr#" + docId).val();
				if (shares != "on") {
					alert("对不起，您无权限共享！");
					return;
				}
				$("#jqModelShare").jqmShow();

				break;
			case '发布到网站' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				var pubs = $("#hidpub", "tr#" + docId).val();

				if (pubs != "on") {
					alert("对不起，您无权限！");
					return;
				}
				$("#PublishForm #pubdocid").val(docId);
				$("#jqModelPublish").jqmShow();
				break;
			case '转移位置' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docId+",");
				$("#positionForm #type1").val("read");
				$("#jqModelPosition").jqmShow();

				break;
			case '查询' :
				showQueryContent("read");
				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"/ea/documentflow/ea_getReadDocList.jspa?finishType=read&track=00";
				break;
			case '导出' :
				url = basePath + "/ea/documentflow/ea_showExcel.jspa?search="
						+ search+"&finishType=read&date="
						+ new Date();;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentflow/ea_getReadDocList.jspa?finishType=read&search="+search+"&date="
						+ new Date();
				numback(url);
				break;

		}
	}
    //分发里面的搜索

    $("#search").click(function () {

        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllPeople.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.drafterName":$("#searchc").val(),
                "document.companyID":""
            },
            success: function cbf(data) {

                var member = eval("(" + data + ")");
                var persons = member.plist;
                var str = "";
                for (var i = 0; i < persons.length; i++) {
                    var obj = persons[i];
                    str += "<option title='"+obj[7]+"-"+obj[4]+"'value='" + obj[1] + "-" + obj[5]
                        + "-" + obj[3] + "'>" + obj[0] + "("
                        + (obj[2]==''?obj[6]:obj[2]) + ")</option>";
                }
                $("#leftfields").append(str);
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });
    })

	$("#tosearch").click(function() {
		$("#searchForm #finishType").val("read");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentflow/ea_toSearchread.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
		token = 2;
	});
	$(".wspdoc tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".wspdoc tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看详情");
			});
	
	var url1 = basePath
	+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
+ new Date().toLocaleString();
$.ajax({
								url : url1,
		type : "get",
		dataType : "json",

		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			var oList = member.companylist;
			;
			var data = new Array();
			var result = "<ul id='browser' class='filetree'>";
			for ( var i = 0; i < oList.length; i++) {
				data[i] = {
					id : oList[i].companyID,
					text : oList[i].companyName
				};
				result += "<ul><li onclick='javascript:childMenu(\""
						+ data[i].id
						+ "\")' title='"
						+ data[i].text
						+ "' class='curor closed'><span class='folder'>"
						+ data[i].text
						+ "</span><ul id='"
						+ data[i].id + "'>";
				result += "</ul></ul>";

			}
			result += "</li></ul>";
			$(result).appendTo("#tree1");
			$("#browser").treeview();

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

$("#leftfields").dblclick(function() {
var left_vo, right_vo, vos, i;
vos = document.getElementsByName('leftfields');

if (vos == null)
	return false;
left_vo = vos[0];
vos = document.getElementsByName('rightfields');
if (vos == null)
	return false;
right_vo = vos[0];
for (i = 0; i < left_vo.options.length; i++) {
	if (left_vo.options[i].selected) {
		var no = new Option();
		no.value = left_vo.options[i].value;
		no.text = left_vo.options[i].text;
		right_vo.options[right_vo.options.length] = no;
	}
}

// 设为要可选状态

for (i = 0; i < right_vo.options.length; i++) {
	right_vo.options[i].selected = true;
}

return true;
});

$("#rightfields").dblclick(function() {
var vos, right_vo, i;
vos = document.getElementsByName('rightfields');
if (vos == null)
	return false;
right_vo = vos[0];
for (i = right_vo.options.length - 1; i >= 0; i--) {
	if (right_vo.options[i].selected) {
		// alert(i);
		right_vo.options.remove(i);
	}
}
// 设为要可选状态

for (i = 0; i < right_vo.options.length; i++) {
	right_vo.options[i].selected = true;
}

return true;
});

});

function childMenu(companyID) {// 2级
	if ($("ul#" + companyID + ">li").length > 0) {
		return;
	}
	var url2 = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url2),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			companyID : companyID
		},
		success : function cbf(data) {

			/** **添加部门列表** */

			var member = eval("(" + data + ")");
			var orglist = member.orgaizationlist;
			var data = new Array();
			var result = "";
			for ( var i = 0; i < orglist.length; i++) {
				data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				};
				result += "<li onclick='javascript:getPerson(\"" + companyID
						+ "\",\"" + data[i].id + "\")' title='" + data[i].text
						+ "'><a href='#'><span id='" + data[i].id
						+ "' class='folder curor'>" + data[i].text
						+ "</span></a></li>";
			}

			$(result).appendTo("#" + companyID);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}
function getPerson(company, org) {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"currentCompanyID" : company,
			"checkOrgID" : org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];
                str += "<option value='" + obj.staffID + "-" + company
                    + "-" + org + "'>" + obj.staffName + "("
                    + (obj.reference==''?obj.staffCode:obj.reference) + ")</option>";
			}
			$("#leftfields").html(str);
		}
	});
}
//转发选择人
function selectPeople(docId) {

	$("#zj").jqmShow();

}



// 分享
function shareDocument() {

	var shareType = $("input[name=docShare.shareType]:radio:checked").val();
	if (shareType == undefined) {
		alert("请选择共享范围");
		return;
	}
	var url = basePath + "ea/documentinfo/sajax_ea_checkShare.jspa?date="
			+ new Date();

	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				data : {
					docId : docId,
					shareType : shareType
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var results = member.result;
					if (results == "yes") {
						$("#ShareForm #docIdss").val(docId);
						$("#ShareForm").attr("target", "hidden").attr("action",
								basePath + "ea/documentinfo/ea_setShare.jspa");
						document.ShareForm.submit.click();
						token = 2;
					} else {
						alert("该文件在当前范围已被分享");
						return false;
					}

				}
			});

}

// 发布新闻到信息平台
function pubDocument() {
	var docId = $("#PublishForm #pubdocid").val();
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			length += 1;
			values += checkinput[i].value + ",";
		}
	}
	if (length == 0) {
		alert("请选择网站");
		return;
	}
	var url = basePath + "ea/documentinfo/sajax_ea_publishToWeb.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					docId : docId,
					webs : values
				},
				success : function cbf(data) {
					$("#jqModelPublish").jqmHide();
					alert("发布成功！");

				},
				error : function cbf(data) {
					alert("发布到网站失败");
				}
			});
}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getReadDocList.jspa?finishType=read";

	}
}

// 展现word文件
function ViewOffice(docPath, fileType,fileShowNameField){
	window.open(
					basePath
							+ "page/ea/main/office_ea/document/wordonlyreadprint.jsp?docPath="
							+ docPath + "&fileType=" + fileType
							+ "&fileShowNameField="+fileShowNameField);
	countHits(docId);
}

// 标记为已读
function markReaded() {
	//if (confirm("确定标记为已读？")) {

		$("#readForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/documentflow/ea_completeRead.jspa?date="
						+ new Date());
		document.readForm.submit.click();
		token = 2;
	//}
}

// 点击word或者扫描附件时设置属性文件的值
function countHits(docId) {

	var url = basePath + "ea/documentflow/sajax_n_ea_completeRead.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					docId : docId
				},
				success : function cbf(data) {

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}


//点击确定按钮
function submit() {
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
	var strid = "";
	var strname = "";
	var flag = true;
	for ( var i = 0; i < lengths; i++) {
		flag = true;
		for ( var j = i + 1; j < lengths; j++) {
			var stri = document.getElementById("rightfields").options[i].value;
			var strj = document.getElementById("rightfields").options[j].value;
			if (stri == strj) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			strid += document.getElementById("rightfields").options[i].value;
			strid += ",";
			strname += document.getElementById("rightfields").options[i].text;
			strname += "|";

		}
	}

	var zfStatus = "on";
	var loadStatus = "on";
	var printStatus = "on";
	var shareStatus = "on";
	var pubStatus = "on";

	strid = strid.substring(0, strid.length - 1);

	if (strid == "") {
		alert("请选择人员");
		return;
	}
	var url2 = basePath + "ea/documentflow/sajax_n_ea_rePublishDocument.jspa";
	$.ajax({
		url : encodeURI(url2),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			readers : strid,
			docId : docId,
			zfStatus : zfStatus,
			type : "zhuanfa",
			loadStatus : loadStatus,
			printStatus : printStatus,
			shareStatus : shareStatus,
			pubStatus : pubStatus
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var re = member.result;
			if (re == "")
				alert("发送成功！");
			else if (re == "fail") {
				alert("文档已结束任务，无法再发送！");
			} else {
				alert(re + "已存在该文档，无需再分发，其余已发送成功！");
			}
			
			$("#zj").jqmHide();
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}

function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}