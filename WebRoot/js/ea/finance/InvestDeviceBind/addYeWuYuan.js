$(function() {
	  $("#browser").treeview();  

	$(".jqmWindow").jqm({
		modal : false,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$("#newG").click(function() {
		cx();
	});
	
	$("#yys").click(function(){
		$("#searchiFrame").attr("src",basePath+"/ea/wfjzh/ea_getlist.jspa?isdx=1");
	});
	
	$("#zzyg").click(function(){
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
				for (var i = 0; i < orglist.length; i++) {
					data[i] = {
						id : orglist[i].organizationID,
						text : orglist[i].organizationName
					};
					result += "<li ><a href='#'><span id='"
							+ data[i].id
							+ "' class='folder curor' onclick='javascript:getPerson(\""
							+ companyID + "\",\"" + data[i].id + "\",\""
							+ data[i].text + "\",\"" + companyName
							+ "\")' title='" + data[i].text + "'>"
							+ data[i].text + "</span></a></li>";
				}
				$("#orgid").html(result);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	});
});


// 关联业务员
/*$(function() {
	$("#glyw").click(function() {
		$("#jqmWindow2").jqmShow();
	});
});*/

// 获取部门
/*function childInner() {

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

					*//** **添加部门列表** *//*

					var member = eval("(" + data + ")");
					var orglist = member.orgaizationlist;
					var data = new Array();
					var result = "";
					for (var i = 0; i < orglist.length; i++) {
						data[i] = {
							id : orglist[i].organizationID,
							text : orglist[i].organizationName
						};
						result += "<li ><a href='#'><span id='"
								+ data[i].id
								+ "' class='folder curor' onclick='javascript:getPerson(\""
								+ companyID + "\",\"" + data[i].id + "\",\""
								+ data[i].text + "\",\"" + companyName
								+ "\")' title='" + data[i].text + "'>"
								+ data[i].text + "</span></a></li>";
					}
					$("#orgid").html(result)
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

}*/

// 根据部门和公司回获取在职员工
function getPerson(companyID, orgID, orgname, companyName) {
	$("#searchtype").val("person");

	var title = companyName + "——在职员工——" + orgname;
	$("#searchiFrame").attr(
			"src",
			basePath + "/ea/wfjzh/ea_getPersonByDept.jspa?companyID="
					+ companyID + "&orgID=" + orgID + "&title="
					+ encodeURI(title) + "&companyName="
					+ encodeURI(companyName));

}


/*function getValueForParm(){ //打开页面
    $("#searchiFrame").attr("src",basePath+"/ea/wfjzh/ea_getlist.jspa?isdx=1");
}*/

$(function(){
	 //添加查询出来的 号码
	  $("#searchAdd").click(function(){
	  		var t = $('#searchiFrame').contents().find('tr[id]'); 
	  		console.log(t);
	  		var x=$(t).find('input:checked');
	  		if(x.length<1){
	  			alert("请选择");
	  			return;
	  		}
			var count = 0;
			var b = "";
	  		$(t).each(function(){
	  			var chk = $(this).find('.JQuerypersonvalue').attr('checked');
					if(chk){ 
						var a = "";
						var sccid =$(this).find('.JQuerypersonvalue').val();
				        var staffid = $(this).find("input#staffid").val();
				        if(sccid == "" || staffid == ""){
				        	alert("请先去在职员工页面完善不完整的微分金账号");
				        	return;
				        }
				        a = staffid+","+sccid;
				        b = b+a+"@";
					}
	  		});
	  		if(b != ""){
	  			var flag = true;
				var searchurl = basePath + "/ea/devicebind/sajax_isStaffInDbs.jspa?";
				$.ajax({
					url : searchurl,
					type : "post",
					async : false,
					dataType : "json",
					data : {
						"dbId":dbId,
						"sccidStaffids":b
					},
					success : function(data) {
						var count = eval("(" + data + ")");
						var nologin = count.nologin;
						if (nologin) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
						var num = parseInt(count);
						if(num > 0){
							alert("选中人员有人已被关联，请重新选择");
							flag = false;
							return;
						}
					},
					error : function(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				if(flag){
					window.open(basePath
							+ "ea/devicebind/ea_addGlStaff.jspa?dbId="+dbId+"&sccidStaffids="+b);
				}
	  		}
	  		}); 
})
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
		}).jqmAddClose('.close');
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '添加业务员',
					bclass : 'add',
					onpress : action
					}, {
					separator : true
					}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
					}, {
					separator : true
				}]
			});
	
	function action(com, grid) {
		switch (com) {
			case '添加业务员' :
				$("#jqmWindow2").jqmShow();
				break;
			case '删除' :
				if (dbsid == "") {
					alert('请选择');
					return
				}
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/devicebind/ea_delDeviceBindStaff.jspa?dbsid="+dbsid);
					document.SearchForm.submit.click();
					$("tr#" + dbsid).remove();
					dbsid = "";
					token = 11;
				}
				break;
		}
	}
	
	//获取deviceBindStaff表的id
	var dbsid = "";
	$("table#deviceBind tr[id]").live("click", function(event) {
		dbsid = this.id;
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		riId = this.id;
	});
});

function re_load() {
	if (token)
		document.location.href=basePath
		+ "ea/devicebind/ea_selGlStaff.jspa?dbId="+dbId;
}

