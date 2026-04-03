$(document)
		.ready(
				function() {
                    $(".jqmWindow").jqm({
						modal : true,// 限制输入（鼠标点击，按键）的对话
						overlay : 20
							// 遮罩程度%
						}).jqmAddClose('.close');// 添加触发关闭的selector

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
					
					
					var strid = "";
					var strname = "";
					var url1 = basePath
							+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
							+ new Date().toLocaleString();
					$
							.ajax({
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

// 提交分发
function Publish() {
	if ($("#choosePss").val() == "") {
		alert("请选择接收人员");
		return;
	}
	// if (confirm("确定分发？")) {
	var publishComment = $("#publishComment").val();
	if ((publishComment.length) * 2 >= 200) {

		alert("意见长度不能大于200");
		return;
	}

	$("#wviewForm").attr("target", "hidden").attr(
			"action",
			basePath + "ea/documentflow/ea_publishDocument.jspa?date="
					+ new Date());
	document.wviewForm.submit.click();
	token = 2;
	// }

}

// 点击分发，选择人员
function selectPeople() {


	$("#zj").jqmShow();

}

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
/**
 * 
 * 清除
 */
function clearPeople() {
	$("#choosePss").val("");
}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getPublishDocList.jspa?finishType=publish";

	}
}

// 点击确定按钮
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
	$("#choosePss").val(strname);
	$("#readers").val(strid);
	$("#zj").jqmHide();
}

function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}
