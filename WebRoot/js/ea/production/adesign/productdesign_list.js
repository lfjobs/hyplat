$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
		var html =  $(".query").html();
		$(".query").remove();
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : html,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印',
					bclass : 'printer',
					onpress : action

				},  {
					separator : true
				},{
					name : '预览',
					bclass : 'mysearch',
					onpress : action

				}, {
					separator : true
				},{
					name : '提交至模拟测试',
					bclass : 'examine',
					onpress : action

				}, {
					separator : true
				}, {
                    name : '生成二维码',
                    bclass : 'erwei',
                    onpress : action

                }, {
                    separator : true
                },  {
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
				

				window.open(basePath
						+ "ea/prodesign/ea_getAddPage.jspa?fiveClear="+fiveClear+"&category="+category);
				break;
			case '修改' :
				if (ppID == "") {
					alert('请选择!');
					return;
				}
			  window.open(basePath
						+ "ea/prodesign/ea_getAddPage.jspa?productPackaging.ppID="+ppID+"&fiveClear="+fiveClear+"&category="+category);
				break;
			case '删除' :
				if (ppID == "") {
					alert('请选择');
					return
				}
				var productstate = $("tr#"+ppID).find("#productstate").text();
				if(productstate!="00"){
					alert("已提交模拟测试无法删除");
					return;
				}
				
				$("#ppID").val(ppID);
				
				if (confirm("确定删除？")) {
					$("#SearchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/prodesign/ea_deleteProduct.jspa?pageNumber="
											+ ppageNumber+"&category="+category);
					document.SearchForm.submit.click();
					$("tr#" + ppID).remove();
					ppID = "";
					token = 11;
				}
				break;
			case '预览' :
				if (ppID == "") {
					alert('请选择!');
					return;
				}
			    window.open(basePath
						+ "ea/prodesign/ea_getEditOrPrevPage.jspa?productPackaging.ppID="+ppID+"&category="+category);
				break;
			case '提交至模拟测试' :
				if (ppID == "") {
					alert('请选择!');
					return;
				}
				var ir=0;
				if(category=="01"){
					$.ajax({
						url:basePath+"ea/prodesign/sajax_ea_getLower.jspa?ppID="+ppID,
						type:"post",
						async : false,
						success:function(data){
							var member = eval("(" + data + ")");
							ir=member.i;
							
						},error:function(data){
							alert("数据获取失败");
						}
					});
					if(ir==0){
						alert("该产品无下级产品，请添加");
						return ;
					}
				}
				var productstate = $("tr#"+ppID).find("#productstate").text();
				if(productstate=="00"){
				
				if(confirm("确定提交至模拟测试?")){
				$("#SearchForm #ppID").val(ppID);
			    
				$("#SearchForm").attr("target","hidden").attr("action",basePath+"ea/prodesign/ea_transferToSim.jspa?category="+category);
				document.SearchForm.submit.click();
				token = 2;
				}
				}else{
					alert("不能重复提交至模拟审核");
					return;
				}
				
				
				break;
			case '导出' :
				var url = basePath + "ea/prodesign/ea_showExcel.jspa?search="
						+ search+"&type=01&fiveClear="+fiveClear+"&category="+category;
				window.open(url);
				break;
		    case '打印' :
		   
				var url = basePath + "ea/prodesign/ea_printPrev.jspa?search="
						+ search+"&type=01&fiveClear="+fiveClear+"&category="+category;
				window.open(url);
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/prodesign/ea_getProductDesignList.jspa?search="
						+ search+"&fiveClear="+fiveClear+"&type=01&category="+category;
				numback(url);
				break;

			case '生成二维码':
                if (ppID == "") {
                    alert('请选择!');
                    return;
                }else {
                	$.ajax({
                    url:basePath+"ea/productslaunch/sajax_ea_getById.jspa?ppID="+ppID,
                    type:"post",
                    async : false,
                    success:function(data){
                        var member = eval("(" + data + ")");
                        ir=member.i;
                        //alert(ir);
                        if (ir != "01") {
                            alert('产品未发布!不能生成二维码');
                        }else {
                            var url=basePath +"ea/productslaunch/ea_getEWeiMa.jspa?ppID="+ppID;
                            window.open(url);
						}

                    }
                });
                }


                break;

			
			
		}
	}


	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				ppID = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});


	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/prodesign/ea_toSearch.jspa?pageNumber=" + ppageNumber
						+ "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value")+"&fiveClear="+fiveClear+"&type=01&category="+category);
		document.SearchForm.submit.click();
		
	});

});

function re_load() {
if (token)
		document.location.href = basePath
				+ "ea/prodesign/ea_getProductDesignList.jspa?pageNumber="
			+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search+"&fiveClear="+fiveClear+"&category="+category+"&type=01";
}
