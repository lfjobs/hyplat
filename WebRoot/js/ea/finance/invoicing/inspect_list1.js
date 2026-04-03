$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
		var query = "<form name='postSearchForm2' id='postSearchForm2' method='post'>"
			+ "验货管理&nbsp;<input type='submit' style='display:none;' name='submit'/>" 
			+ "<input type='hidden' name='search' value='search'/>" 
			+ "<span style=\"margin-left:150px;\">单据编号：</span><input type='text' style=\"width: 100px\" name='cashierBills.journalNum'/>&nbsp;&nbsp;"
			+ "项目名称：<input name=\"cashierBills.projectName\" style=\"width: 90px\" />&nbsp;&nbsp;"
			+ "单据状态：<select id=\"taxstatus\" style=\"width:65px\"  name=\"cashierBills.status\"><option value=\"13\" selected=\"selected\" >已收货</option><option value=\"14\">已验货</option><option value=\"15\">已入库</option></select>"
			+ "<input class=\"input-button\" type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></form>";	
		
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '验货',
					bclass : 'edit',
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
				},/*{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/]
			});

	function action(com, grid) {
		switch (com) {
			case '验货' :
			    /*document.location.href =basePath
						+ "ea/purchase1/ea_getinspectList.jspa";*/
				//查询之后的判断
		        /*if(inputstatus=="14"){
		           alert("查询的是已验货数据不可操作！！");
				   return;
		        }
		        if(inputstatus=="15"){
		           alert("查询的是已入库数据不可操作！！");
				   return;
		        }
		        //根据tb 行判断是否有已收货物品
				var strs=0;		
				$("table.flexme11").find("tr.xggoods").each(function() {
					strs++;
				});
				if(strs==0){
					alert("没有要验货物品！");
					return;
				}
				//后台的判断，查询是否有  已收货的物品
			    var inspectsize=0;
				var URL = basePath
				+ "ea/purchase1/sajax_ea_getInspectListSize.jspa?date=" + new Date().toLocaleString();
				$.ajax({
					url : encodeURI(URL),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
					    inspectsize = member.inspectlistsize;
					    
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				if (inspectsize==0) {
					alert("已验货不可操作！");
					document.location.href =basePath
						+ "ea/purchase1/ea_getinspectList.jspa";
					return;
				    }
				var url =basePath
				+ "/ea/purchase1/ea_toInspectAddDan.jspa?print=danframe";
				window.open(url);*/

                if (financialbillID == "") {
                    alert("请选择！");
                    return;
                }
                var a=$("#"+financialbillID).find(".status").val();
                if(a=="13"){
                    var url =basePath
                        + "/ea/purchase1/ea_toInspectAddDan.jspa?print=danframe&financialbillID="+financialbillID;
                    window.open(url);
				}else{
                	alert("已验货");
				}

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/purchase1/ea_getinspectList.jspa?search="
						+ search+"&xmtype="+xmtype;
				numback(url);
				break;
			case '打印预览':
				var url =basePath
				+ "page/ea/main/finance/invoicing/inspect_frame.jsp?search="+search
				+ "&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one";
		         open(url);
				break;	
		}
	}
	
	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
				/*if (event.srcElement.value == undefined) {
					if ($("input.JQuerypersonvalue", $(this)).attr("checked")) {
						$("input.JQuerypersonvalue", $(this)).attr("checked",
								false);
					} else {
						financialbillID = this.id;
						$("input.JQuerypersonvalue", $(this)).attr("checked",
								true);
					}
				}*/

        financialbillID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
			});
	$("#tosearch").click(function() {// 查询
		        var taxstatus=$("select#taxstatus  option:selected").val();
				$("#postSearchForm2")
						.attr(
								"action",
								basePath
										+ "ea/purchase1/ea_toinspectSearch.jspa?pageNumber="
										+ pNumber+"&taxstatus="+taxstatus+"&xmtype="+xmtype);
				document.postSearchForm2.submit.click();
			});
});

function re_load() {
	document.location.href = basePath
			+ "/ea/purchase1/ea_getinspectList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search;
}
