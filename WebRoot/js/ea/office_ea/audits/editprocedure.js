$(function() {
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	
	var url = basePath + "ea/procedure/sajax_n_ea_getAudtiorInfo.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	dataType:"json",
	data:{
		
		"procedure.proId":proId
	},
	success : function cbf(data) {
	    var member = eval("(" + data + ")");
		var phaselist = member.phaselist;
	    var auditlist = member.auditlist;
		var obja = "";
		var objp = "";
		
		
		for (var i = 0; i < phaselist.length; i++) {
			var title="<table>";
			objp = phaselist[i];
			
			for (var j = 0; j < auditlist.length; j++) {
				obja = auditlist[j];
				if(objp.phaseId==obja.phaseId){
					title+= "<tr><td>审核人</td><td>"+obja.auditorName+"</td></tr>";
				}
			}
			title+="</table>"
			$("td#"+objp.phaseId).find("img").attr("title",title);
		}
		
	},
	error:function(data){
		alert("获取人员信息失败");
	}
	
	});


	
	$(".close").click(function(){
	
		$("#jqModel").slideUp("slow");
		
	}); 
	
	$(".show").click(function(){
	
		$("#jqModel").slideDown("slow");
		
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
			
	//	$("#sortable" ).sortable();
     $("#sortable .gg").disableSelection();
			
		$("#sortable .gg").sortable({
				stop : function() {
				
					var url = basePath
							+ "ea/photoboxmanager/sajax_n_ea_dragSort.jspa";
					$.ajax({
								url : encodeURI(url),
								type : "post",
								dataType : "json",
								async : true,
								data : {
									"aaa" : $("#sortable .gg").sortable('serialize')
								},
								success : function(data) {
									alert("顺序保存成功！");
								},
								error : function(data) {
                                    alert("gggggd");
								}

							});

				}

			});

	

});

function getPhaseInfo(phaseId){
	var url = basePath + "ea/procedure/sajax_n_ea_getPhaseInfoById.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	dataType:"json",
	data:{
		
		"phase.phaseId":phaseId
	},
	success : function cbf(data) {
	    var member = eval("(" + data + ")");
	    var phase = member.phase;
	    var auditlist = member.auditlist;
	    $("#phaseName").val(phase.phaseName);
	    $("#auditType").val(phase.auditType);
	    $(".numaudit").text(phase.orderNum);
	    $("#auditType").val(phase.auditType);
	    var strid = "";
	    var showname = "";
	    var obj = "";
	 
	    for (var i = 0; i < auditlist.length; i++) {
	    	obj = auditlist[i];
	    	strid+=obj.auditorID+"-"+obj.companyID+"-"+obj.organizationID+"|"
	    	showname+=obj.auditorName+"<br/>"
	    }
	  $("#auditarea").html(showname);
	  $("#strid").val(strid);
		
		
	},
	error:function(data){
		alert("获取人员信息失败");
	}
	
	});

	
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