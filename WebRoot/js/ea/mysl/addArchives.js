// 新建任务页面的js

$(document).ready(function() {
			year = procode.substring(0, procode.indexOf("-"));
			
			getPerson(proid);

			if ($(".filetype").val() != "") {
				getTemplate($(".filetype").val());
			} else {
				getTemplate("W");
			}
			
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
					// 遮罩程度%
				}).jqmAddClose('.close');// 添加触发关闭的selector


			// 文件类型
			$("#newForm #filetype").change(function() {
						if ($(this).val() != '') {
							getTemplate(this.value);
						}
					});
					
		   //返回
					
		$("#back").click(function(){
			re_load();
		});
		//上传附件按钮
		$("#uploadattach").click(function() {

					$("#jqModelUpload").jqmShow();

		});
		
		if(!$("#attachtbl").has('tr').length)	
		{
			//$("#attachhistory").hide();
		}else{
			$("#attachhistory").show();
			
		}
		
		// 任务类型change
		$("#tasktype").change(function() {
			var tasktype = $(this).val();
			change(tasktype);
		});
		
		
		//执行人change
		$("#staffid").change(function(){
			var tasktype = $("#tasktype").val();
			change(tasktype);
			
		});
		
		$("#code4").hide();
		$(".code4").hide();
		$("#code1").val(year);
		$("#xddoc").show();
		$(".tcode").show();
		$(".ttaskcode").hide();

		});

// 保存更新task
function saveTask() {
	$(".put3").trigger("blur");
	$(".isNaN").trigger("blur");
	if($(".error").length!=0){
		return;
	}
	$("#proid").val(proid);
	$("#newForm").attr("target", "hidden").attr("action",
			basePath + "ea/taskmanage/ea_saveArchives.jspa?date=" + new Date());

	document.newForm.submit.click();
	token = 2;
}

function re_load() {
	if (token) {
		document.location.href = basePath
		+ "ea/taskmanage/ea_getListByTaskManageFile.jspa?myproject.proid="+proid;
	}
}

// 获取执行人
function getPerson(proid) {
	var url = basePath + "ea/taskmanage/sajax_ea_getExpeopleByproID.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"myproject.proid" : proid

				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var mystafflist = member.result;
					var str = "";
					
					var currentstaffid = $(".staffid").val();
					var value = "";
					
					for (var i = 0; i < mystafflist.length; i++) {
						var obj = mystafflist[i];

						str += "<option value='" + obj.staffid + "-"
								+ obj.organizationID + "-" + obj.companyID
								+ "-" + obj.staffname + "-"
								+ obj.organizationName + "-" + obj.companyName
								+ "'>" + obj.staffname + "</option>";
						
						if(obj.staffid==currentstaffid){
							value=$.trim(obj.staffid + "-"+obj.organizationID + "-" + obj.companyID
									+ "-" + obj.staffname + "-"
									+ obj.organizationName + "-" + obj.companyName);
									
							
						}
					
					}
					$("#staffid").html(str);
					$("#staffid").val(value);
					change("htzd");

				}
			});
}

// 获取具体模板
function getTemplate(filetype) {
	var url = basePath + "ea/taskmanage/sajax_ea_getOfficeTemplate.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"mytask.filetype" : filetype

				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var templist = member.templist;
					var docPath = member.docPath;
					var filetype = member.filetype;
					var str = "";
					for (var i = 0; i < templist.length; i++) {
						var obj = templist[i];

						str += "<option  title ='" + obj.templateId
								+ "' value='" + obj.templateId + "'>"
								+ obj.fileShowName + "</option>";
					}
					$("#templateid").html(str);
					if ($(".attachpath").val() != ""
							&& filetype == $(".filetype").val()) {
						docPath = $(".attachpath").val();

						$("#templateid").val($(".templateid").val());

					}
					$("#attachpath").val(docPath);

					// 赋初始页面路径
					$("#office").attr(
							"src",
							basePath + "page/mysl/office.jsp?fileType="
									+ filetype + "&docPath=" + docPath);

				}
			});
}

//下载

function download(docPath) {
	window.open(basePath + "/servlets/render?filename=" + encodeURI(docPath));
}
//操作附件，作废恢复
function operateAttach(attachid) {
	$("#attachid").val(attachid);
	$("#uploadForm").attr("target", "hidden").attr("action",
			basePath + "ea/taskmanage/ea_operateAttach.jspa");
	document.uploadForm.submit.click();
	token = 2;
}

function attachRecord(){
	$("#jqModel").jqmShow();
}

//获取序号
function getNum(tasktype,companyid,orgid,proid) {
	var url = basePath + "ea/taskmanage/sajax_ea_ajaxGetNum.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"mytask.tasktype" : tasktype,
			"mytask.companyid":companyid,
			"mytask.orgid":orgid,
			"mytask.proid":proid

		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var num = member.result;
			if(tasktype=="scrw"){
				$("#code4").val(num);
			}else if(tasktype=="htzd"){
				$("#code2").val(num);
			}else{
				$("#code3").val(num);
			}
			
		}
	});
}


//change时间时生成编码组合
function change(tasktype){
	
	isSelected=tasktype;
	
	if (tasktype == "scrw") {
		$("#code4").show();
		$(".code4").show();
	} else {
		$("#code4").hide();
		$(".code4").hide();
	}
	
	
	if(tasktype!="htzd"){
		$("#code1").attr("readonly","readonly");
	}else{
		$("#code1").attr("readonly",false);
	}
	
	if(tasktype=="scrw"||tasktype=="sjdg"||tasktype=="htrw"){
		$("#code2").attr("readonly","readonly");
	}else{
		$("#code2").attr("readonly",false);
	}
	
	if(tasktype=="scsj"||tasktype=="sjdg"||tasktype=="htrw"){
		$("#code3").addClass("isNaN");
	}else{
		$("#code3").removeClass("isNaN");
	}
	
	if(tasktype=="htzd"){
		$("#code2").addClass("isNaN");
	}else{	
	$("#code2").removeClass("isNaN");
	}
	
	if(tasktype=="scrw"){
		$("#code4").addClass("isNaN");
	}else{	
	  $("#code4").removeClass("isNaN");
	}
	
	
	
	
	// 生成编码
	
    var staffinfo = $("#staffid").val();
    var array = staffinfo.split("-");   
    var orgid = array[1];
    var companyid = array[2];
    var orgname = array[4];
	if (tasktype == "htzd") {
		
		$("#code1").val(year);
		$("#code3").val("");
		$("#code4").removeClass("put3");

		

	}
	if (tasktype == "scsj") {
		
		$("#code1").val("生计通");
		$("#code2").val(year);
		$("#code4").removeClass("put3");
	

	}
	if (tasktype == "scrw") {
		$("#code1").val("生通");
		$("#code2").val(orgname);
		$("#code3").val(year);
		$("#code4").addClass("put3");


	}
	if (tasktype == "sjdg") {
		$("#code1").val("设计大纲");
		$("#code2").val(procode);
		$("#code4").removeClass("put3");
	}
	if (tasktype == "htrw") {
		$("#code1").val("绘图");
		$("#code2").val(procode);
		$("#code4").removeClass("put3");

	}
	getNum(tasktype,companyid,orgid,proid);

	
}



//判断编号重复性
function checkCodeRepeat(tasktype,companyid,orgid,proid,taskcode) {
	var url = basePath + "ea/taskmanage/sajax_ea_checkCodeRepeat.jspa?date="
			+ new Date();
	var result2="";
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		data : {
			"mytask.tasktype" : tasktype,
			"mytask.companyid":companyid,
			"mytask.orgid":orgid,
			"mytask.proid":proid,
			"mytask.taskcode":taskcode

		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var result = member.result;
			
		
			result2=result;
			
		},
		error:function(data){
			alert("编号验证失败");
		}
	});
	
	return result2;
}
