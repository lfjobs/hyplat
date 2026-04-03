$(function(){
	alert(showType);
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
			if (i == 13) { // 为全选状态
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
						var formData = $("#crmCustForm").serialize();
						formData = decodeURIComponent(formData, true);
						var url = basePath
								+ "ea/marketingCrmCustomer/sajax_ea_saveCrmCustomerMenu.jspa?";
						$.ajax({
									url : encodeURI(url + formData),
									type : "get",
									async : true,
									dataType : "json",
									success : function(data) {
										if (showType == 'add') {
											window.location.href = basePath
													+ "ea/humanResource/ea_getHumanResource.jspa?showType=add";
										} else {
											var customerid = $("#customerid").text();
											window.location.href = basePath
													+ "ea/humanResource/ea_getHumanResource.jspa?cstaff.staffID="
													+ customerid
													+ "&showType=edit";
										}
									},
									error : function(data) {
										alert('保存失败！');
									}
								});
					});

	if (showType == 'add') {
		//$("form#box1Form").find(".isHide").removeClass("isHide");
		//$("span.statusinfo").hide();
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
			$("#staffIdentityCard").addClass("put4");
		} else {// 不确定人员信息
			$("#staffIdentityCard").removeClass("put4");
			$("#staffIdentityCard").removeClass("newerror");
		}
	});

	$("td.txt03").click(function() {
		var did = $(this).next().find("a:eq(0)").attr("id");
		var mid = did.substring(4);
		changemenu(did, mid, 'edit');
	});
});

function toSave(formID, url) { // 保存
	if (retoken)
		return;
	if(staffID == ""){
		$(".put4", 'form#' + formID).trigger("blur");
		if ($(".newerror", 'form#' + formID).length) {
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
	retoken = 1;
	$("form#" + formID).attr("action", basePath + url);
	$("form#" + formID).submit();	
};


//第一个参数为表单id，第二个参数为 table表头id
function changemenu(divid, menuid, opetype) {
	if (opetype == 'edit') {
		$("div#" + divid).find("span.isShow").removeClass("isShow").addClass("isHide");
		$("div#" + divid).find("input.isHide").removeClass("isHide").addClass("isShow");
		$("table.box" + menuid).find("a#mord" + menuid + "_close").removeClass("isHide");
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
			$("input#staffAddress", $("from#box1Form")).show();
			$("select#status").show();
			break;
		default:
			$("div#box" + menuid).show();
			var personvalue = $("span#customerkey").text();
			//alert(personvalue);
			if (personvalue == '') {
				alert("请先填写人员基本信息！");
				return;
			}
			var personurl = basePath + $("#mainframe" + menuid).attr("url");
			$("#mainframe" + menuid).attr("src", personurl + personvalue);
			break;
		}
	}else if (opetype == 'close'){
		$("div#" + divid).find("span.isHide").removeClass("isHide").addClass("isShow");
		$("div#" + divid).find("input.isShow").removeClass("isShow").addClass("isHide");
		$("table.box" + menuid).find("a#mord" + menuid + "_close").addClass("isHide");
		$("table.box" + menuid).find("a#mord" + menuid).removeClass("isHide");
		$("tr#tools").hide();
		if (menuid == 1) {
			$("select#nation").hide();
			$("select#nativePlace").hide();
			$("select#staus").hide();
			$("span.statusinfo").show();
			$("select#relationship").hide();
			$("input#staffAddress", $("from#box1Form")).hide();
			$("select#status").hide();
			$("input#staffIdentityCard").hide();
			$("input#staffIdentityCard").attr("readonly","");
			$("input#ReadCardBtn").hide();
			$("span#staffIdentityCard").show();
		}else{
			$("div#box" + menuid).hide();
		}
		
	}
};