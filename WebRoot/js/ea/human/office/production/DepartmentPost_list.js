$(function() {
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
				title : '岗位',
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
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
				document.location.href = pbasePath
								+ "/ea/departmentpost/ea_toSaveDepartmentPost.jspa?pageNumber="
								+ ppageNumber;
				break;
			case '删除' :
				if (depPostID == "") {
					alert('请选择!');
					return;
				}
				var url = pbasePath +"ea/departmentpost/sajax_ea_isUseOfPost.jspa?departmentPost.depPostID="+depPostID;
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var count = member.count;
						if(count == 0){
							if (confirm("确定删除？")) {
								var orgname = $("tr#"+depPostID).find("#organizationName").text();
								var postname = $("tr#"+depPostID).find("#postName").text();
								document.location.href = pbasePath
												+ "/ea/departmentpost/ea_deleteDepatPost.jspa?pageNumber="
												+ ppageNumber+"&departmentPost.depPostID="+depPostID+"&search="
												+ psearch+"&orgName="+orgname+"&departmentPost.postName="+postname;
							}
						}else{
							alert("此岗位正在被使用，不能删除！");
						}
					}
				});
				break;
			case '修改' :
				if(depPostID == ''){
					alert("请选择！");
					return;
				}
				document.location.href = pbasePath
								+ "/ea/departmentpost/ea_toEditDepartmentPost.jspa?pageNumber="
								+ ppageNumber+"&departmentPost.depPostID="+depPostID;
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				if (depPostID == "") {
					alert('请选择!');
					return;
				}
				var organizationName = $("tr#"+depPostID).find("#organizationName").text();
				document.location.href = pbasePath
								+ "/ea/departmentpost/ea_toSeeDepartmentPost.jspa?pageNumber="
								+ ppageNumber+"&departmentPost.depPostID="+depPostID+"&orgName="
								+ organizationName+"&pageForm.pageNumber="+pnum;
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/departmentpost/ea_getDepartmentPostList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		depPostID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	$("#tosearch").click(function() { //查询
		$("#postSearchForm")
				.attr(
						"action",
						pbasePath
								+ "ea/departmentpost/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	
	$(function() { //职位所属部门
        //职位所属部门
		var url = pbasePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+comID+"&date1="+new Date(); 
		$.ajax({
			    url:encodeURI(url),
			    type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
					var member = eval("(" + data + ")");
	                var oList = member.organizationlist;
	                var data2 = new Array();
	               // var selid;
			        data2[0] = {
		                id: comID,
		                pid: '-1',
		                text: '选择部门'
		            };
	                for (var i = 0; i < oList.length; i++) {
	                    data2[i + 1] = {
	                        id: oList[i].organizationID,
	                        pid: oList[i].organizationPID,
	                        text: oList[i].organizationName
	                    };
	                }
	                ts = new TreeSelector($("#organizationID")[0], data2, -1);
	        		ts.createTree();
				},
				error: function cbf(data){
					alert("数据获取失败！");
				}
		});
    });
});

function re_load() {
	if (token)
		document.location.href=pbasePath
    				+"ea/departmentpost/ea_getDepartmentPostList.jspa?pageNumber="+ppageNumber+"&search="+psearch;
}