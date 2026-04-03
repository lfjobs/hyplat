$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 140,
				width : 'auto',
				minwidth : 30,
				title : '教务 > '+(docstatus=='01'?'科一':docstatus=='02'?'科二':docstatus=='03'?'科三':docstatus=='04'?'科四':'')+' > 培训',
				minheight : 80,
				buttons : [{
					name : '培训通过',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},/* {
					name : '查看',
					bclass : 'edit',
					onpress : action
						// �
					}, {
					separator : true
				},*/  {
		            name: '培训管理',
		            bclass: 'edit',
		            onpress: action
		            // 当点击调用方法
		        }, {
		            separator: true
		        }, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// �
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
			case '返回上级':
				/*if(docstatus=='01'){
					document.location.href=basePath+"page/ea/main/navigation/cultivateManager.jsp";
				}
				if(docstatus=='02'){
					document.location.href=basePath+"page/ea/main/navigation/cultivateManager_zhuangkao.jsp";
				}
				if(docstatus=='03'){
					document.location.href=basePath+"page/ea/main/navigation/cultivateManager_changdi.jsp";
				}
				if(docstatus=='04'){
					document.location.href=basePath+"page/ea/main/navigation/cultivateManager_lukao.jsp";
				}*/
				document.location.href=basePath+"page/ea/main/navigation/driving_management.jsp";
				break; 
			case '培训通过' :
				var str = "";
				var ytg="";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";
								drivingprincipalid = $(this).val();
								var studentstatus1=$("tr#" + drivingprincipalid).find("span#studentstatus").text();
								if($.trim(studentstatus1)=="已培训"){
									ytg = "ytg";
								}
							}
				});
				if(ytg=="ytg"){
					alert("以选中人员中包含已培训人员!请重新选择");
					return;
				}
				if (str == "" || str.length == 0) {
					alert('请选择');
					return;
				}
				if (confirm("确定继续？")) {
					$f = $('#cstaffForm');
					$f.find('input#strs').val(str);
					$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_saveDrivingPeixun.jspa?pageNumber="
												+ pNumber );
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			case '查看' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								drivingprincipalid = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + drivingprincipalid);
				$p.find("span[id]").each(function() {
							  $t.find("input#" + this.id+","+"select#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;	
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;		
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDrivingList.jspa?docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions
						+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
				numback(url);
				break;
			case '培训管理':
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								drivingprincipalid = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				 $("#daoRu").attr("src",basePath+'/ea/training/ea_getListDtDrivingTrainingInfor.jspa?dtDrivingAppointmentRecord.drivingprincipalid='+drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="+docstatus);
			 	 $("div#bankJqm").show();
            	break;	
		}
	};
	// 复选框选中
	$(".chx").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});
	$(".JQueryflexme tr[id]").click(function() {
				var d = $("input.chx", $(this)).attr("checked");
				$("input.chx", $(this)).attr("checked", !d);
			})	;
	/*$(".JQueryflexme tr[id]").dblclick(function(){
    	staffID=$(this).find("span#studentid").text();
        var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&istrues="+istrues+"&title="+title;
		window.open(encodeURI(url) , '','scrollbars=yes,resizable=yes,channelmode');
    });*/		
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});	
	$("#DaoRuFan").click(function(){// 返回
	       $("#bankJqm").hide();
		}); 
	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath
								+ "ea/driving/ea_toSearch.jspa?pageNumber="
								+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
				document.SearchForm.submit.click();
			});				
});
function re_load() {
	if (token)
		document.location.href= basePath
				+ "ea/driving/ea_getDrivingList.jspa?pageNumber="
				+ pNumber+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&title="+title+"&search="+search+"&conditions="+conditions +"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID ;
}