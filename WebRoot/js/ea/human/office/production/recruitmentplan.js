$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		height : 320,
		width : 'auto',
		minwidth : 5,
		title : '招聘规划',
		minheight : 40,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '修改',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '删除',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		},{
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
		},{
			name : '打印预览',
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
			}]
		});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.cstaffForm.reset();
				recruitruleid = "";
				var enName = $("#enName",$("#cstaffForm")).val('01');
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				enName++;
				break;
			case '修改' :
				if (recruitruleid == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + recruitruleid);
				$p.find("span[id]").each(function() {
					$t.find("[name]#" + this.id).val($(this).text());
				});
				postname = $p.find("#organizationPost").text();
				$("#organizationid","#cstaffForm").change();
				$("#jqModel").jqmShow();
				break;
			case '查询' :	
					$("#jqModelSearch").jqmShow();
				break;
			case '删除' :
				if (recruitruleid == "") {
					alert('请选择！');
					return
				}
				if (confirm("确定删除？")) {
					var orgName = $("tr#" + recruitruleid).find("#organizationName").text();
					var postName = $("tr#" + recruitruleid).find("#postname").text();
					var url = basePath + "ea/recruitment/ea_delRecruitment.jspa?pageNumber="
									+ pNumber + "&dtrecruitrule.recruitruleid=" 
									+ recruitruleid + "&orgName=" + orgName + "&postName="
									+ postName;
					document.location.href = encodeURI(url);
				}
				break;
			case '打印预览':
				var url = basePath+"ea/recruitment/ea_printaccess.jspa?search="+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/recruitment/ea_getRecruitmentList.jspa?search="
						+ search;
				numback(url);
				break;
			case '导出' :
				var url = basePath + "ea/recruitment/ea_showExcel.jspa";
				open(url);
				break;
		}
	}
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		recruitruleid = this.id;
		$("input.JQuerypersonvalue", $(this))
					.attr("checked", "checked");
	});
	
	$("#searchStaff").click(function() { //查询
		if($("#organizationid","#postSearchForm").val() == comID){
			$("#organizationid","#postSearchForm").attr("value","");
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/recruitment/ea_toSearch.jspa?pageNumber="
			+ pNumber);
		document.postSearchForm.submit.click();
	});
			
	$("input.JQuerySubmit").click(function() {// 保存
		if ($("form .error").length) {
			return;
		}
		var com = $("#organizationid","#cstaffForm").val();
		if(com == '' || com == comID){
			alert('请选择部门！');
			return;
		}
		var orgName = $("#organizationid :selected","#cstaffForm").text();
		orgName = orgName.substring(orgName.indexOf("├")+1);
		var postName = $("#organizationPost :selected","#cstaffForm").text();
		if (recruitruleid == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/recruitment/ea_saveRecruitment.jspa?pageNumber="
									+ pNumber + "&orgName=" + orgName + "&postName="
									+ postName);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			$("#cstaffForm").find("#recruitruleid").trigger("change");
			token = 1;
			return;
		}
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/recruitment/ea_saveRecruitment.jspa?pageNumber="
								+ pNumber + "&orgName=" + orgName + "&postName="
								+ postName);
		document.cstaffForm.submit.click();
		token = 2;
	});
			
	$("input.JQueryreturn").click(function() {// 取消按钮
		$("#jqModel").jqmHide();
		postName = '';
	});
	
	$(".close").click(function() {// 关闭按钮
		$("#jqModel").jqmHide();
		postName = '';
	});
});

$(function() { //职位所属部门
	var url = basePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+comID+"&date1="+new Date(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.organizationlist;
                var data2 = new Array();
		        data2[0] = {
	                id: comID,
	                pid: '-1',
	                text: '请选择部门'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].organizationID,
                        pid: oList[i].organizationPID,
                        text: oList[i].organizationName
                    };
                }
                ts = new TreeSelector($("#organizationid","#cstaffForm")[0], data2, -1);
                ts1 = new TreeSelector($("#organizationid","#postSearchForm")[0], data2, -1);
        		ts.createTree();
        		ts1.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
  		//根据部门获取岗位
	$("#organizationid","#cstaffForm").change(function() { // 添加时获取选中部门
		publics("#cstaffForm",$(this).val());
	});
	
	$("#organizationid","#postSearchForm").change(function() { // 查询时获取选中部门
		publics("#postSearchForm",$(this).val());
	});
	
	function publics(formID,vals){
		var url = basePath +"ea/departmentpost/sajax_ea_getOrganizationPost.jspa?departmentPost.organizationID="+vals;
		$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.departmentPostlist;
					var str = "";
					if(persons.length == 0){
						str = "<option value=''>此部门无岗位</option>";
					}else{
						str = "<option value='' id='firstpost'>请选择岗位</option>";
						for(var i=0;i<persons.length;i++){
							var obj = persons[i].postName;
							var objID = persons[i].depPostID;
							str += "<option value='"+objID+"'>"+obj+"</option>";
						}
					}
					$("#organizationPost",formID).html(str);
					if(postname != ''){
						$("#organizationPost",formID).val(postname);
					}
				}
		});
	}
});

function re_load() {
	if (token)
		document.location.href = basePath
		+"ea/recruitment/ea_getRecruitmentList.jspa?pageNumber="+pNumber+"&search="+search;
}