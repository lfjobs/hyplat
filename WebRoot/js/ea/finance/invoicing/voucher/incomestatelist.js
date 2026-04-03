$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	var query = "<form name='searchForm' id='searchForm' method='post'>" +
			"<input type='submit' name='submit' style='display: none' />" +
					"<table border='0' id='searchtbl' ><tr><td><strong>资产负债表计算设定</strong></td><td>&nbsp;&nbsp;&nbsp;</td><td>报表代号：</td><td>" +
					"<input name='fiscalPeriod.year' style='height:18px;width:100px;'type='text' value=''/>" +
					"<input name='search' type='hidden' value='search' />" +
					"<input  type='button' value='  查询  ' id='tosearch' style='margin:2px;'class='input-button'/>" +
					"</td></tr></table></form>";

	$('.fexlist').flexigrid({
		height : 160,
		width : 'auto',
		minwidth : 30,
		title : '资产负债表计算设定',
		minheight : 80,
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
				}, {
					name : '保存',
					bclass : 'store',
					onpress : action
						// 当点击调用方法
					},{
						separator : true
					}, {
						name : '放弃',
						bclass : 'delete',
						onpress : action
							// 当点击调用方法
						},{
							separator : true
						}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	

	function action(com, grid) {
		switch (com) {

			case '添加' :
				var tabSymbol =  window.parent.document.getElementById("tabSymbol").value;
				if(tabSymbol.indexOf("sa")!=-1){
					alert("请先保存内容设定，再进行添加计算设定");
					return;
				}
				if(tabSymbol==""){
					alert("请选择资产负债内容设定");
					return;
				}
			
				$("#sa").before($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					if($(this).attr("name")=="tabSymbol"){
						$(this).val(tabSymbol);
					}
					$(this).attr("name",
							"assetsdebtpmap[" + select + "]." + this.name);
					
					if($(this).attr("name").indexOf("plaType")!=-1||$(this).attr("name").indexOf("plaMode")!=-1){
                         if($(this).val()=="A"){
                        	 $(this).attr("checked","checked");
                         }
						
				    }
					
				

				});


				$("#sa" + select).show();
				select++;
				ccpId = "";
			
				break;
			case '修改' :
				if(ccpId.indexOf("sa")!=-1){
					return;
				}
				if (ccpId == '') {
					alert("请选择！");
					return;
				}
			
				$p = $("tr#" + ccpId);
		
				
	
				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					if((this.name).indexOf("assetsdebtpmap")==-1){
						var name = "";
				    if($(this).attr("name")=="plaMode"||$(this).attr("name")=="plaType"){
				    	name = this.name.substring(0,this.name.length);
				    }else{
				    	name = this.name;
				    }
					
					$(this).attr("name",
							"assetsdebtpmap[" + select + "]." + name);
					}
				});

				select++;
				$p.find("span.hid")
						.addClass("model1");
				$p.find("input").removeClass("model1");


				
				break;
			case '保存' :
				if(select==1){
					return;
				}
				//说明可以	

                var u=0;
				$(".stCom").each(function(){
					var value=$(this).val();

					if($(this).parent().parent().parent().attr("id")!="sa"){
			           if($(this).val()==""){
			        	   alert("会员科目(报表代号不能为空)");
			        	   u=1;
			        	   return false;
			           }

	            
				if($(this).parent().parent().parent().find("#bbdh").attr("checked")){
					var url = basePath+"ea/debtasset/sajax_ea_isExistsTabSymbol.jspa?dates="+new Date();
					$.ajax({
						url:url,
						type:"get",
						dataType:"json",
						async:false,
						data:{
							"invCcpbsgl.tabSymbol":$(this).val()
						},
						success:function(data){
							var me = eval("("+data+")");
							var result = me.result;
							if(result!="success"){
								alert(value+"报表代号不存在");
								$(this).val("");
								u=1;
								return false;
								
							}
					       
							
						},
						error:function(data){
							alert("获取报表代码失败");
						}
						
					});
					
					
			     	}
				   if(u==1){
					   return false;
				   }
			      }
				});
				if(u==1){
					return;
				}
				$('#fispriodForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/debtasset/ea_saveIncomestate.jspa?pageNumber="
										+ pNumber);
				
				document.fispriodForm.submit.click();
				token = 2;
				break;
			case '放弃' :

				document.location.reload();
				break;


			case '删除' :
				if (ccpId == '') {
					alert("请选择！");
					return;
				}

	
				
				$f = $('#fispriodForm');
				if (confirm("是否删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/debtasset/ea_delIncomeState.jspa?pageNumber="
											+ pNumber + "&invCcpbsgl.ccpId="
											+ ccpId);
					document.fispriodForm.submit.click();
					$("tr#" + ccpId).remove();
					ccpId = "";
					token = 11;

				}
				
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/debtasset/ea_getIncomeList.jspa?search=" + search+"&invCcpbsgl.tabSymbol="+tab;
				numback(url);
				break;
			
		}
	}
	// 点击选中
	$(".fexlist tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				ccpId = this.id;
			});
	$(".fexlist tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				ccpId = this.id;
				action("修改");
			});
	// 根据条件查询
//	$("#tosearch").click(function() {
//		
//
//		$("#searchForm")
//				.attr(
//						"action",
//						basePath + "ea/fisperiod/ea_toSearch.jspa?pageNumber="
//								+ pNumber);
//		document.searchForm.submit.click();
//	});

});
function re_load() {
	if (token)
		document.location.reload();
}



