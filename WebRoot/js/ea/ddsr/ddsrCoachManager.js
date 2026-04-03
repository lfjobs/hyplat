$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '教练信息管理',
				minheight : 80,
				buttons : [{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置工作时段',
					bclass : 'edit',
					onpress : action						
					}, {
					separator : true
				},/* {
					name : '导出',
					bclass : 'excel',
					onpress : action						
					},*/{
					name : '修改详细信息',
					bclass : 'edit',
					onpress : action						
					}, {
					separator : true
				},{
					name : '是否推荐',
					bclass : 'examine',
					onpress : action						
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
			case '添加' :
			  	 $("#daoRu").attr("src",basePath+'/ea/academicadmin/ea_getCompanyListEmployeeReferral.jspa');
			  	 $("#bankJqm").jqmShow();
			  	 break;
			case '删除' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";								
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择教练');
					return
				}				
				if (confirm("是否删除？")) {					
					$.ajax(
							{type:"POST",
							url:basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=delDdsrCoach",
							data:{  
								    delCoach:str
							      },
							dataType:"json",
							success:function(data)
							{						
							   try{	
								   $("[name='chbox']").each(function() {
										if ($(this).is(':checked')) {
											 $("tr#" + $(this).val()).remove();																		
										}
									});								  								   
								   token = 11;								   
							   }catch(err)
							   {
							   	alert("系统发生异常,请联系管理员.");
							   }	
							},
							error : function(data) {						
								alert("系统发生异常,请联系管理员.");
							}
						}
					);					
				}
				break;
			case '修改' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								coacKey = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择教练');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个教练');
					return
				}				
				
				if (coacKey == "") {
					alert('请选择教练');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + coacKey);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});				
				
				$("#coacTeachtypeSelect").val($p.find("span#coacTeachtype").html());
				$("#coacStatusSelect").val($p.find("span#coacStatus").html());				
				$("#coacStarSelect").val($p.find("span#coacStar").html());				
				$("#jqModel").jqmShow();
				break;	
			case '查看' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								coacKey = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择教练');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个教练');
					return
				}
				if (coacKey == "") {
					alert('请选择要查看的教练');
					return
				}
				
				$t = $("table#showstafftable");
				$p = $("tr#" + coacKey);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#show_" + this.id).val($(this).text());							
						});
				$("#show_coacTeachtypeSelect").val($p.find("span#coacTeachtype").html());
				$("#show_coacStatusSelect").val($p.find("span#coacStatus").html());				
				$("#show_coacStarSelect").val($p.find("span#coacStar").html());
				$("#jqModelShow").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '修改详细信息' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";	
								coacKey = str;
							}
						});
				if (str == "" || str.length == 0) {					
					alert('请选择教练');
					return
				}else if (str.split(",").length>2){
					alert('只能选择一个教练');
					return
				}
				if (coacKey == "") {
					alert('请选择要修改的教练');
					return
				}
				$p = $("tr#" + coacKey);
				status = $p.find("span#status").text();
				staffID = $p.find("span#staffID").text();
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ staffID+"&cstaff.status="+status;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');				
				break;
			case '是否推荐' :
				var str = "";
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + ",";								
							}
						});
				if (str == "" || str.length == 0) {
					alert('请选择教练');
					return
				}
				var b;
				if (confirm("推荐？“确定”或 “取消”")) {					
						b=1;				
				}else{
						b=0;
				}
				$.ajax(
						{type:"get",
						url:basePath + "ea/ddsrcoachManager/sajax_ea_setRecommended.jspa?",
						data:{  
							IsrecommendStr:str,
							isrecommendTrue:b
						      },
						dataType:"json",
						success:function(data)
						{						
							var result = eval('(' + data + ')'); 
							if(result.message){
								alert("推荐成功!");
							}else{
								alert("取消成功!");
							}
							document.location.reload();
						},
						error : function(data) {						
							alert("系统发生异常,请联系管理员.");
						}
					}
				);
				break;	
			case '设置工作时段':	
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";	
						coacKey = str;
					}
				});
				if (coacKey != ""){//取教练的
					if (str.split(",").length==2){//一个教练的						
						$.ajax(
								{type:"POST",
								url:basePath + "ea/ddsrcoachManager/ea_getWorkTimeByCoachs.jspa?",
								data:{  
									    selCoach:str
								      },
								dataType:"json",
								success:function(data)
								{								   
									document.workTimeForm.reset();
									$(".JQuerySetWorkTimeSubmit2").attr("disabled",true);
									if (data!="" && data.length==4){//表明选择了一个设置过的教练								   		
								   		$("#wotiStrdaydate").val(data[0][3].toString());
								   		$("#wotiEnddaydate").val(data[0][4].toString());
								   		
								   		$("#zjWotiStrdateHour").val(data[0][1].split(":")[0]);
								   		$("#zjWotiStrdateTime").val(data[0][1].split(":")[1]);
								   		$("#zjWotiEnddateHour").val(data[0][2].split(":")[0]);
								   		$("#zjWotiEnddateTime").val(data[0][2].split(":")[1]);
								   		$("#swWotiStrdateHour").val(data[1][1].split(":")[0]);
								   		$("#swWotiStrdateTime").val(data[1][1].split(":")[1]);
								   		$("#swWotiEnddateHour").val(data[1][2].split(":")[0]);
								   		$("#swWotiEnddateTime").val(data[1][2].split(":")[1]);
								   		$("#xwWotiStrdateHour").val(data[2][1].split(":")[0]);
								   		$("#xwWotiStrdateTime").val(data[2][1].split(":")[1]);
								   		$("#xwWotiEnddateHour").val(data[2][2].split(":")[0]);
								   		$("#xwWotiEnddateTime").val(data[2][2].split(":")[1]);
								   		$("#wjWotiStrdateHour").val(data[3][1].split(":")[0]);
								   		$("#wjWotiStrdateTime").val(data[3][1].split(":")[1]);
								   		$("#wjWotiEnddateHour").val(data[3][2].split(":")[0]);
								   		$("#wjWotiEnddateTime").val(data[3][2].split(":")[1]);								   		
								   	}else{//没设置过的
								   		
								   	};							   	
								},
								error : function(data) {						
									alert("系统发生异常,请联系管理员.");
								}
							}
						);
					}else{//多个教练的						
						$.ajax(
								{type:"POST",
								url:basePath + "ea/ddsrcoachManager/ea_getWorkTimeByCoachs.jspa?",
								data:{  
									    selCoach:str
								      },
								dataType:"json",
								success:function(data)
								{								   
									document.workTimeForm.reset();
									$(".JQuerySetWorkTimeSubmit2").attr("disabled",true);
									if (data!="" && data.length==4){//表明多个教练设置了同样的工作时段
								   		$("#wotiStrdaydate").val(data[0][3].toString());
								   		$("#wotiEnddaydate").val(data[0][4].toString());
								   		
								   		$("#zjWotiStrdateHour").val(data[0][1].split(":")[0]);
								   		$("#zjWotiStrdateTime").val(data[0][1].split(":")[1]);
								   		$("#zjWotiEnddateHour").val(data[0][2].split(":")[0]);
								   		$("#zjWotiEnddateTime").val(data[0][2].split(":")[1]);
								   		$("#swWotiStrdateHour").val(data[1][1].split(":")[0]);
								   		$("#swWotiStrdateTime").val(data[1][1].split(":")[1]);
								   		$("#swWotiEnddateHour").val(data[1][2].split(":")[0]);
								   		$("#swWotiEnddateTime").val(data[1][2].split(":")[1]);
								   		$("#xwWotiStrdateHour").val(data[2][1].split(":")[0]);
								   		$("#xwWotiStrdateTime").val(data[2][1].split(":")[1]);
								   		$("#xwWotiEnddateHour").val(data[2][2].split(":")[0]);
								   		$("#xwWotiEnddateTime").val(data[2][2].split(":")[1]);
								   		$("#wjWotiStrdateHour").val(data[3][1].split(":")[0]);
								   		$("#wjWotiStrdateTime").val(data[3][1].split(":")[1]);
								   		$("#wjWotiEnddateHour").val(data[3][2].split(":")[0]);
								   		$("#wjWotiEnddateTime").val(data[3][2].split(":")[1]);										   		
								   	};							   	
								},
								error : function(data) {						
									alert("系统发生异常,请联系管理员.");
								}
							}
						);
					}
					
				}else{//取公司的
					$.ajax(
							{type:"POST",
							url:basePath + "ea/ddsrcoachManager/ea_getWorkTimeByCompany.jspa?",
							data:{},
							dataType:"json",
							success:function(data)
							{								   
								$("input[name=ddsrWorkTime.wotiType][value='10']").attr("checked",true);
						   		$(".JQuerySetWorkTimeSubmit2").attr("disabled",false);
								if (data!="" && data.length==4){//设置过公司
							   		$("#wotiStrdaydate").val(data[0][3].toString());
							   		$("#wotiEnddaydate").val(data[0][4].toString());
							   		
							   		$("#zjWotiStrdateHour").val(data[0][1].split(":")[0]);
							   		$("#zjWotiStrdateTime").val(data[0][1].split(":")[1]);
							   		$("#zjWotiEnddateHour").val(data[0][2].split(":")[0]);
							   		$("#zjWotiEnddateTime").val(data[0][2].split(":")[1]);
							   		$("#swWotiStrdateHour").val(data[1][1].split(":")[0]);
							   		$("#swWotiStrdateTime").val(data[1][1].split(":")[1]);
							   		$("#swWotiEnddateHour").val(data[1][2].split(":")[0]);
							   		$("#swWotiEnddateTime").val(data[1][2].split(":")[1]);
							   		$("#xwWotiStrdateHour").val(data[2][1].split(":")[0]);
							   		$("#xwWotiStrdateTime").val(data[2][1].split(":")[1]);
							   		$("#xwWotiEnddateHour").val(data[2][2].split(":")[0]);
							   		$("#xwWotiEnddateTime").val(data[2][2].split(":")[1]);
							   		$("#wjWotiStrdateHour").val(data[3][1].split(":")[0]);
							   		$("#wjWotiStrdateTime").val(data[3][1].split(":")[1]);
							   		$("#wjWotiEnddateHour").val(data[3][2].split(":")[0]);
							   		$("#wjWotiEnddateTime").val(data[3][2].split(":")[1]);							   		
							   	}else{//没有设置过
							   		
							   	};							   	
							},
							error : function(data) {						
								alert("系统发生异常,请联系管理员.");
							}
						}
					);
				};					
				$("#jqModelSetWorkTime").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList";
				numback(url);
				break;			
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	// 复选框选中
	$(".JQuerypersonvalue").bind("click", function(event) {				
				var b = $(this).attr("checked");				
				$(this).attr("checked", !b);
				coacKey = d ? "" : this.value;				
	});
	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.JQuerypersonvalue", $(this)).attr("checked");
		$("input.JQuerypersonvalue", $(this)).attr("checked", !d);
		coacKey = d ? "" : this.id;							
	});
	$(".JQuerySubmit").click(function() {		
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=updateDdsrCoach&pageNumber=" + pNumber);		
		if (coacKey == "") {			
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}		
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQuerySearchSubmit").click(function(){
		$("#jqModelSearch").jqmHide();
		var dt = new Date();
		$("#searchForm").attr("action",
				basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&datetime=" + dt.getTime());
		document.searchForm.submit.click();		
	});
	$(".JQuerySearchReturn").click(function(){
		$("#jqModelSearch").jqmHide();
	});
	$("#hrefSelect").click(function(){
		$("#jqModelSelect").jqmShow();		
	});
	$(".JQuerySelectSubmit").click(function(){
		var str1 = "";
		$("[name='chboxselect']").each(function() {
					if ($(this).is(':checked')) {
						str1 += $(this).val() + ",";								
					}
				});		
		if (str1 == "" || str1.length == 0) {
			alert('请选择科目');
			return
		}
		$t = $("table#no_JQueryflexme");
		var str2 = "";
		var array = str1.substring(0, str1.length-1).split(",");
		for ( var int = 0; int < array.length; int++) {
			var array_element = array[int];
			$p = $("tr#" + array_element);
			$p.find("span[id]").each(function(){
				if (this.id=='subjType2')
					{
						str2 += $.trim($(this).html()) + ",";
					}
			});
		}
		$("[name='subject.subjKey']").val(str1.substring(0, str1.length-1));
		$("[name='subject.subjType']").val(str2.substring(0, str2.length-1));		
		$("#jqModelSelect").jqmHide();
	});	
	$(".JQuerySelectReturn").click(function(){
		$("#jqModelSelect").jqmHide();
	});
	
	//-- 设置工作时段 --//
	$(".JQuerySetWorkTimeSubmit").click(function(){
		
		var str = "";
		if ($('input:radio[name="ddsrWorkTime.wotiType"]:checked').val()=='20'){
			$("[name='chbox']").each(function() {
						if ($(this).is(':checked')) {
							str += $(this).val() + ",";								
						}
					});
			if (str == "" || str.length == 0) {
				alert('请选择教练');
				return
			}
		}
		
		$(".put3", $("table#setWorkTimeTable")).trigger("blur");
		if ($("#workTimeForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}		
		$("#workTimeForm").attr("target", "hidden").attr("action",
				basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=setWorkTime&buttonFlag=confirm&selCoach=" + str + "&pageNumber=" + pNumber);
		document.workTimeForm.submit.click();
		token = 2;
	});	
	
	
	$(".JQuerySetWorkTimeSubmit2").click(function(){
		$(".put3", $("table#setWorkTimeTable")).trigger("blur");
		if ($("#workTimeForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}		
		$.ajax({
			type:"POST",
			url:basePath + "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?innerAction=setWorkTime&buttonFlag=ajax",
			data:{
				"ddsrWorkTime.wotiType":$('input:radio[name="ddsrWorkTime.wotiType"]:checked').val(),
				"ddsrWorkTime.wotiStrdaydate":$("#wotiStrdaydate").val(),
				"ddsrWorkTime.wotiEnddaydate":$("#wotiEnddaydate").val(),
				zjWotiStrdateHour:$("#zjWotiStrdateHour").val(),
				zjWotiStrdateTime:$("#zjWotiStrdateTime").val(),
				zjWotiEnddateHour:$("#zjWotiEnddateHour").val(),
				zjWotiEnddateTime:$("#zjWotiEnddateTime").val(),
				swWotiStrdateHour:$("#swWotiStrdateHour").val(),
				swWotiStrdateTime:$("#swWotiStrdateTime").val(),
				swWotiEnddateHour:$("#swWotiEnddateHour").val(),
				swWotiEnddateTime:$("#swWotiEnddateTime").val(),
				xwWotiStrdateHour:$("#xwWotiStrdateHour").val(),
				xwWotiStrdateTime:$("#xwWotiStrdateTime").val(),
				xwWotiEnddateHour:$("#xwWotiEnddateHour").val(),
				xwWotiEnddateTime:$("#xwWotiEnddateTime").val(),
				wjWotiStrdateHour:$("#wjWotiStrdateHour").val(),
				wjWotiStrdateTime:$("#wjWotiStrdateTime").val(),
				wjWotiEnddateHour:$("#wjWotiEnddateHour").val(),
				wjWotiEnddateTime:$("#wjWotiEnddateTime").val()
			},
			dataType:"json",
			success:function(data)
			{
				alert("操作成功");
			},
			error:function(data) {						
				alert("系统发生异常,请联系管理员.");
			}
		});
	});	
	//-- 设置工作时段完毕 --//
	//-- 控制“设为本校常规上班时间”按钮 --//
	$(".JQuerySetWorkTimeSubmit2").attr("disabled",true);
	$('input:radio[name="ddsrWorkTime.wotiType"]').click(function(){
		if ($('input:radio[name="ddsrWorkTime.wotiType"]:checked').val()=='10') 
			{
				$(".JQuerySetWorkTimeSubmit2").attr("disabled",false);
			}else
				{
				    $(".JQuerySetWorkTimeSubmit2").attr("disabled",true);
				}
	});
	//-- 控制“设为本校常规上班时间”按钮 完毕 --//
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/ddsrcoachManager/ea_doDdsrCoachManagerAction.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");		
	}
}