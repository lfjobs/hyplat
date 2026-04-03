 $(function() {
    $("select#trainType").hide();
    $("select#trainTypes").hide();
    $("select#dripermitted").hide();
    $("select#busiType").hide();;
    $("select#perdriType").hide();
    $("select#subject").hide();
    if(relation=="学员"){
		$(".addtr").show();
		$(".studentinfo").show();
        $("select#trainType").hide();
        $("select#dripermitted").hide();
	}else if(relation=="教练"){
        $(".coach").show();
	} else {
        $(".addtr").hide();
        $(".studentinfo").hide();
        $(".coach").hide();
	}
    $("#isLocal").change(
        function() {
        	if($("#isLocal").val() =="0"){
                $(".zadd").show();
        	}else if($("#isLocal").val() == "1"){
                $(".zadd").find("input").val("");
                $(".zadd").hide();
			}
		});;

    $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20// 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector

    $('.JQueryflexme').flexigrid({
		buttons : [ {
			name : '编辑菜单',
			bclass : 'menu1',
			onpress : action2
		} ]
	});
	function action2(com, grid) {
		switch (com) {
		case '编辑菜单':
			$(".menu00").toggle();
			break;
		}
	}
	var i = 0;
	$("div.showorhide").each(function() {
		if (this.id == '1') {
			i++;
			$(this).show();
			$("#" + $(this).attr("name")).attr("checked", true);
			if (i == 15) { // 为全选状态
				$(".oroupboxAll").attr("checked", true);
			}
		}
	});
	$(".oroupboxAll").attr("checked", true);
	$(".oroupboxAll").click(function() { // 全选
		if ($(this).attr("checked")) {
			$("input[type='checkbox']").each(function() {
				$(this).attr("checked", true);
			});
		} else {
			$("input[type='checkbox']").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
	$(".checks").change(function() {
		var c = 0;
		$(".checks").each(function() {
			var check = $(this).attr("checked");
			if (check == true) {
				c++;
			}

		});
		if (c == 4) {
			$(".oroupboxAll").attr("checked", true);
		} else {
			$(".oroupboxAll").attr("checked", false);
		}
	});
	$(".JQuerySubmits")
			.click(
					function() { // 编辑菜单保存
						var formData = $("#humanForm").serialize();
						formData = decodeURIComponent(formData, true);
						var url = basePath
								+ "ea/humanResource/sajax_ea_saveHumanResource.jspa?";
						$
								.ajax({
									url : encodeURI(url + formData),
									type : "get",
									async : true,
									dataType : "json",
									success : function(data) {
										if (showType == 'add') {
											window.location.href = basePath
													+ "ea/humanResource/ea_getHumanResource.jspa?showType=add";
										} else {
											var staffid = $("#staffID").text();
											window.location.href = basePath
													+ "ea/humanResource/ea_getHumanResource.jspa?cstaff.staffID="
													+ staffid
													+ "&showType=edit";
										}
									},
									error : function(data) {
										alert('保存失败！');
									}
								});
					});

	if (showType == 'add') {
		$("form#box1Form").find(".isHide").removeClass("isHide");
        $("form#box18Form").find(".isHide").removeClass("isHide");
		$("span.statusinfo").hide();
		$("a.mord").hide();

	} else {
		$("select#nation").hide();
		$("select#nativePlace").hide();
		$("select#staus").hide();
		$("input#staffIdentityCard").hide();
		$("span.staffIdentityCard").show();
		$("input#ReadCardBtn").hide();
		$("tr#tools").hide();

		$t = $("table#stafftable");
		var photoVal = $t.find("input#photo").val();
		if (photoVal != '') {
			$t.find('img#idImg').attr("src", basePath + photoVal);
			$("table#stafftable").find('#singleShuter').hide();
			$t.find('img#idImg').show();
			$t.find('img#photo').hide();
		}

		var val1 = $("select#status").val();
		var text1 = $("select#status option[value='" + val1 + "']").text();
		$("span#status").html(text1);
		$("select#status").hide();
	}
	$("select#staus").change(function() {// 信息类型转变
		if ($(this).val() == '00') {// 确定人员信息
			$("input#staffIdentityCard").addClass("put4");
			$("input#staffIdentityCard").removeAttr("readonly");
		} else {// 不确定人员信息
			$("input#staffIdentityCard").removeClass("put4");
			$("input#staffIdentityCard").removeClass("newerror");
			$("input#staffIdentityCard").attr("readonly","readonly").val("");
		}
	});

	$("td.txt03").click(function() {
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did, mid, 'edit');
	});

    $("#isSubmit").click(function(){// 选择确定
        var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
        if(value1 == ""){
            alert("请选择");;
            return;
        }
        var value2 = window.frames["ifr"].$('tr#'+value1).find("span#s_account").text();//弹出框的页面存在于span中才取得到
        $("#spanzh").text(value2);
        $("#inputsccid").val(value1);
        $("#ifr").attr("src","");
        $("#jqmWindow2").jqmHide();
    });

    $("#isBack").click(function(){// 返回
        $("#jqmWindow2").jqmHide();
    });

});

// 查询往来关系
$(document)
		.ready(
				function() {
					var $select = $("#relationship");
					var url = basePath
							+ "ea/cstaff/sajax_n_ea_getSelectLists.jspa?date="
							+ new Date().toLocaleString();
					$
							.ajax({
								url : url,
								type : "get",
								async : true,
								dataType : "json",
								success : function cbf(data) {
									var member = eval("(" + data + ")");
									var codeRelationList = member.codeRelationList;
									$op = "<option selected='selected' value = ''>--请选择--</option>";
									$select.append($op);
									for ( var i = 0; i < codeRelationList.length; i++) {
										$op = $("<option/>");
										$op
												.val(codeRelationList[i].codeID)
												.text(
														codeRelationList[i].codeValue)
												.attr(
														"id",
														codeRelationList[i].codeID);
										$select.append($op);
									}
									notoken = 0;
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
							});
					$("#relationship").change(
							function() {
								$("input#relationshipValue").attr(
										"value",
										$(this).children("option:selected")
												.text());
								if($("input#relationshipValue").val()=="学员"){
                                    $(".addtr").show();
                                    $(".studentinfo").show();
                                    $(".coach").hide();
                                    if(showType == "add"){
                                        $("#trainTypes").show();
                                        $("#busiType").show();
                                        $("select#perdriType").show();
									}
                                } else if($("input#relationshipValue").val()=="教练"){
                                    $(".coach").show();
                                    $(".addtr").hide();
                                    $(".studentinfo").hide();
                                    if(showType == "add"){
                                        $("#trainType").show();
                                        $("#dripermitted").show();
                                        $("#subject").show();
                                    }
								} else {
                                    $(".addtr").hide();
                                    $(".studentinfo").hide();
                                    $(".coach").hide();
								}
							});;
					if (showType == "add") {
						$("select#relationship").show();
						$("#company").show();
						$("#company").val(companyName);
						$("#companyID").val(companyID);
					} else {
						$("select#relationship").hide();
					}
				});;
function toSave(form1ID, url) { // 保存
	if (retoken)
		return;
	if(staffID == ""){
		$(".put4", 'form#' + form1ID).trigger("blur");
		if ($(".newerror", 'form#' + form1ID).length) {
			retoken = 0;
			alert('请完善所有信息');
			return;
		}
	}
	if (photosizes == 1) {
		retoken = 0;
		alert("上传图片规格必须为102X126！ 大小必须小于100k");
		return;
	}
	(relation ==null || relation =="")? relation = $("input#relationshipValue").val() : relation = relation;
    retoken = 1;

	 $("form#" + form1ID).attr("action", basePath + url+"relation="+relation);
	 $("form#" + form1ID).submit();
}

function checklength(obj) {
	if (obj.value.length > 10) {
		obj.value = "";
		alert("您输入的字数超出范围 ");
	}
	obj.blur();
}
function checkSubmitMobil() { 
	if ($("#reference").val() == "") { 
	alert("手机号码不能为空！"); 
	$("#reference").focus(); 
	return false; 
	} 

	if (!$("#reference").val().match(/^1[0-9]{10}$/)) {
	alert("手机号码格式不正确,请输入正确的手机号码格式");
	$("#reference").val("");
	$("#reference").focus(); 
	return false; 
	} 
	return true; 
	}

function getValueForParm(id,isval){ //打开页面
    ids = id;
    isvals = isval;
    $("#ifr").attr("src",basePath+"/ea/wfjzh/ea_getlist.jspa?isdx=0");
    //mainheught = parent.document.getElementById("mainframe3").offsetHeight;
    //parent.document.getElementById("mainframe3").style.height = 330 + 'px';
    $("#jqmWindow2").jqmShow();
}

// 第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid, menuid, opetype) {
	if (opetype == 'edit') {
		$("div#" + divid).find("span.isShow").removeClass("isShow").addClass(
				"isHide");
		$("div#" + divid).find("input.isHide").removeClass("isHide").addClass(
				"isShow");
        $("div#" + divid).find("a.isHide").removeClass("isHide").addClass(
            "isShow");
		$("table.box" + menuid).find("a#mord" + menuid + "_close").removeClass(
				"isHide");
		$("table.box" + menuid).find("a#mord" + menuid).addClass("isHide");

		switch (menuid) {
		case 1:
			$("select#nation").show();
			$("select#nativePlace").show();
			$("select#staus").show();
			$("span.statusinfo").hide();
			// $("input#staffIdentityCard").hide();
			// $("span.staffIdentityCard").show();
			$("input#ReadCardBtn").hide();
			$("tr#tools").show();
			$("input#staffIdentityCard").show();
			$("input#staffIdentityCard").attr("readonly","readonly");
			$("input#ReadCardBtn").show();
			$("span#staffIdentityCard").hide();
			$("select#relationship").show();
			$("select#isLocal").show();
			$("input#address").show();
			$("input#tempCardNo").show();
            $("input#stayAddress").show();
            $("select#nationality").show();
            $("input#applydate").show();
            $("select#trainType").show();
            $("select#trainTypes").show();
            $("select#dripermitted").show();
            $("select#subject").show();
            $("select#busiType").show();;
            $("select#perdriType").show();;
			$("input#staffAddress", $("from#box1Form")).show();
			$("select#status").show();
			break;
		default:
			$("div#box" + menuid).show();

			var personvalue = $("span#staffID").text();
			if (personvalue == '') {
				alert("请先填写人员基本信息！");
				return;
			}
			var personurl = basePath + $("#mainframe" + menuid).attr("url");
			$("#mainframe" + menuid).attr("src", personurl + personvalue);
			break;
		}
	} else if (opetype == 'close') {
		$("div#" + divid).find("span.isHide").removeClass("isHide").addClass(
				"isShow");
		$("div#" + divid).find("input.isShow").removeClass("isShow").addClass(
				"isHide");
        $("div#" + divid).find("a.isShow").removeClass("isShow").addClass(
            "isHide");
		$("table.box" + menuid).find("a#mord" + menuid + "_close").addClass(
				"isHide");
		$("table.box" + menuid).find("a#mord" + menuid).removeClass("isHide");
		$("tr#tools").hide();
		if (menuid == 1) {
			$("select#nation").hide();
			$("select#nativePlace").hide();
			$("select#staus").hide();
			$("select#isLocal").hide();;
            $("select#nationality").hide();;
			$("span.statusinfo").show();
			$("select#relationship").hide();
			$("input#staffAddress", $("from#box1Form")).hide();
			$("select#status").hide();
			$("input#staffIdentityCard").hide();
			$("input#staffIdentityCard").attr("readonly","");
			$("input#ReadCardBtn").hide();
			$("span#staffIdentityCard").show();

            $("input#applydate").hide();
            $("select#trainType").hide();
            $("select#trainTypes").hide();
            $("select#dripermitted").hide();
            $("select#subject").hide();
            $("select#busiType").hide();;
            $("select#perdriType").hide()
		} else {
			$("div#box" + menuid).hide();
		}
	}

}