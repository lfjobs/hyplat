$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '审核流程',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看/修改流程',
					bclass : 'see',
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
			case '添加' :
				$("#jqModel").jqmShow();
				break;
				
		    case '查看/修改流程' :
		    		if (proId == "") {
					alert('请选择！');
					return
				}
             document.location.href=basePath+"ea/procedure/ea_editProcedure.jspa?procedure.proId="+proId;

			  
				break;
			
			case '删除' :
				if (proId == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#informID').val(informID);
				if (confirm("是否删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/informbills/ea_delInformBills.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.cstaffForm.submit.click();
					$("tr#" + informID).remove();
					informID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/smeeting/ea_getPartiStaffList.jspa?search="
						+ psearch+"&meetingID="+meetingID;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				proId= this.id;
				action('参会情况录入');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				proId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");

				if ($("form .error").length) {
					return;
				}

				$("#cstaffForm").attr("target", "hidden").attr(
						"action",
						basePath
								+ "ea/smeeting/ea_saveSMParti.jspa?pageNumber="
								+ ppageNumber + "&search=" + psearch);
				document.cstaffForm.submit.click();
				token = 2;
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/smeeting/ea_toSearchParti.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
			
    //继续添加审核阶段
       $(".NextAudit").click(function(){
       	$(".PreAudit").show();
		var numaudit = Number($("#numauditid").text());
		
		
		 //真正保存数据的时候
		 
		 $("#cstaffForm").attr("target","hidden").attr("action",basePath+"ea/procedure/ea_saveProcedure.jspa");
		  document.cstaffForm.submit.click();
		
		if(numaudit==10){
			alert("最多10个审核阶段");
			return;
		}
       	 $(".numaudit").text(numaudit+1);
		 $("#orderNum").val(numaudit+1);
		 
		 
		
		});
		
		
		$(".JQuerySubmit").click(function(){
			var numaudit = Number($("#numauditid").text());
			//真正保存数据的时候
		 
		 $("#cstaffForm").attr("target","hidden").attr("action",basePath+"ea/procedure/ea_saveProcedure.jspa");
		  document.cstaffForm.submit.click();
		  re_load();
			
		});
		
		
      //前一个审核阶段
       $(".PreAudit").click(function(){
       	
       	
		var numaudit = Number($("#numauditid").text());
		
		if(numaudit==2){
			$(".PreAudit").hide();
			
		}
		
		
       	 $(".numaudit").text(numaudit-1);
       	 $("#orderNum").val(numaudit-1);
		
       	 var url = basePath+"ea/procedure/ea_getPhaseInfo.jspa";
       	 $.ajax({
       	 url:url,
       	 type:"get",
       	 async:false,
       	 dataType:"json",
       	 data:{
       	 	
       	 	"procedure.proId":$("#proIds").val(),
       	 	"phase.orderNum":$("#orderNum").val()
       	 	
       	 },
       	 success:function(data){
       	 	 var mem = eval("("+data+")");
       	 	 var phase = mem.result;
       	 	 
       	 	 $("#phaseName").val(phase.phaseName);
       	 	 $("#auditType").val(phase.auditType);
       	 },
       	 error:function(data){
       	 	alert("获取审核信息失败");
       	 }
       	 
       	 });
       	 
			
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
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var oList = member.companylist;;
			var data = new Array();
			var result = "<ul id='browser' class='filetree'><li><span class='folder'>集团机构树</span>";
			for (var i = 0; i < oList.length; i++) {
				data[i] = {
					id : oList[i].companyID,
					text : oList[i].companyName
				};
				result += "<ul><li onclick='javascript:childMenu(\""
						+ data[i].id + "\",\""
						+ data[i].text + "\")' title='"
						+ data[i].text 
						+ "' class='curor expandable closed'><span class='folder'>" + data[i].text
						+ "</span><ul id='"+data[i].id+"'>";
				result += "</ul></li></ul>";
				

			}
			result+="</li></ul>";
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
						no.title = left_vo.options[i].title;
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

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/procedure/ea_getProcedureList.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
function selectAuditor(){

	
	
	$("#zj").jqmShow();
}

	function childMenu(companyID,companyName) {// 2级
		if($("ul#"+companyID+">li").length>0){
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
				var result="";
				for (var i = 0; i < orglist.length; i++) {
					data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				    };
					result += "<li onclick='javascript:getPerson(\""
								+ companyID + "\",\"" + data[i].id + "\",\"" + data[i].text + "\",\"" + companyName + "\")' title='"
								+ data[i].text
								+ "'><a ><span id='"
								+ data[i].id
								+ "' class='folder curor'>"
								+ data[i].text + "</span></a></li>";
				}
				
				
				
				$(result).appendTo("#"+companyID);

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

	}

function getPerson(company, org,orgName,companyName) {
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
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];

						str += "<option title='" + obj.staffName + "("+obj.staffCode+")[" +orgName
								+ "-" + companyName + "]' value='" + obj.staffID + "-" + company
								+ "-" + org + "'>" + obj.staffName + "("
								+ obj.staffCode + ")</option>";
					}
					$("#leftfields").html(str);
				}
			});
}
function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}

// 点击选人确定按钮
function submit() {
		var auditType = $("#auditType").val();
	
	
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
    if(lengths>1){
	if(auditType=="01"){
		
		alert("您选择的是单一审核人类型，只能选择一个审核人");
		return;
	 }
    }
	var strid = "";
	var strname = "";
	var flag = true;
	var showname = "";
	for (var i = 0; i < lengths; i++) {
		flag = true;
		for (var j = i + 1; j < lengths; j++) {
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
			showname+= document.getElementById("rightfields").options[i].title;
			showname+="<br/>";

		}
	}
	
	$("#auditarea").html(showname);
	$("#strid").val(strid);
	$("#auditorName").val(showname);
	$("#zj").jqmHide();
}
