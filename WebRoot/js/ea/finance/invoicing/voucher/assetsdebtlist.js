$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector



	
	var query = "<form name='searchForm' id='searchForm' method='post'>" +
			"<input type='submit' name='submit' style='display: none' />" +
					"<table border='0' id='searchtbl' ><tr><td><strong>资产负债表内容设定</strong></td><td>&nbsp;&nbsp;&nbsp;</td><td>报表代号：</td><td>" +
					"<input name='invCCbsgl.tabSymbol' style='height:18px;width:100px;'type='text' value=''/>" +
					"<input name='tabPSymbol' type='hidden' value='"+tabPSymbol+"'/>"+
					"<input name='search' type='hidden' value='search' />" +
					"<input  type='button' value='  查询  ' id='tosearch' style='margin:2px;'class='input-button'/>" +
					"</td></tr></table></form>";

	$('.fexlist').flexigrid({
		height :150,
		width : 'auto',
		minwidth : 30,
		title : query,
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
	
	$("div.bDiv",$("div.flexigrid")).css("height","260px");
	function action(com, grid) {
		switch (com) {

			case '添加' :
                 
				
				$("#sa").before($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					
					if($(this).attr("name")=="tabType"){
						$(this).val(tabType);
					}
					
					if($(this).attr("name")=="tabSymbol"){
						$(this).parent().find(".tabs").text(tabType);
					}
					$(this).attr("name",
							"assetsdebtmap[" + select + "]." + this.name);

					if($(this).attr("name").indexOf("bsAtion")!=-1){
						if(tabType=="A"){
							
                         if($(this).val()=="A"){
                        	 $(this).attr("checked","checked");
                         }
						}else{
							if($(this).val()=="E"){

	                        	 $(this).attr("checked","checked");
	                         }
						}
						
						
				    }

				});


				$("#sa" + select).show();
				select++;
				tabSymbol = "";
			
				break;
			case '修改' :
				if(tabSymbol.indexOf("sa")!=-1){
					return;
				}
				if (tabSymbol == '') {
					alert("请选择！");
					return;
				}
				
				$p = $("tr#" + tabSymbol);
		
				
	
				$p.addClass("check");

				$p.find(':input:gt(0)').each(function() {
					
					if($(this).attr("name")=="tabSymbol"){
						$(this).parent().find(".tabs").text(tabType);
					}
					var yname = this.name;
					if((this.name).indexOf("assetsdebtmap")==-1){
						var name="";
				if($(this).attr("name").indexOf("bsAtion")!=-1){

						name = "bsAtion";
						
				}else{

					    name = yname;
				}
					$(this).attr("name",
							"assetsdebtmap[" + select + "]." +  name);
					}else{
						return;
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

				if(tabType=="B"){
                  var result = isRight();
					
					var arry = result.split(",");
					var c = arry[0];
					var d = arry[1];
	
					
					var cc = 0;
					var ccid= 0;
					$(".cc").each(function(){
						if($(this).attr("name").indexOf("assetsdebtmap")!=-1){
						if($(this).attr("checked")==true){
							cc++;
//							if($(this).parent().parent().parent().parent().attr("id").indexOf("sa")==-1){
//								ccid++;
//							}
						}
						}
						
					});
					var dd = 0;
					var ddid = 0;
                    $(".dd").each(function(){

                    	if($(this).attr("name").indexOf("assetsdebtmap")!=-1){
                    	if($(this).attr("checked")==true){
							dd++;
//							if($(this).parent().parent().parent().parent().attr("id").indexOf("sa")==-1){
//								ddid++;
//							}
						  }
                    	}
					});
                    
                    if(cc>1){
                    	alert("营业收入净值只能有一笔");
                    	return;
                    }
                    
                    if(dd>1){
                    	alert("本期存益只能有一笔");
                    	return;
                    }
                    
                    
                    

                    
                    if(c==1){
                    	if(cc==1){
                    		  alert("营业收入净值只能有一笔");
                              return;
                    	}
                    
                    }
                     if(d==1){
                    	 if(dd==1){
                   		    alert("本期存益只能有一笔");
                             return;
                     	}
                    }
                    
                    
                    
					
				}
				var re = isReaptBySeq();
				if(re=="blanktp"){
					alert("报表代号不能为空");
					return;
				}
				if(re=="failtp"){
					alert("报表代号不能重复");
					return;
				}
				if(re=="blank"){
					alert("序号不能为空");
					return;
				}
				
				
				
				if(re=="cl"){
					alert("是否本期损益只能有一个为'是'");
					return;
				}

				$(".posnumred").trigger("blur");


				if($("#fispriodForm .error").length>1){
					return;
				}
				
				$('#fispriodForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "/ea/debtasset/ea_saveDebtAssets.jspa?pageNumber="
										+ pNumber);
				
				document.fispriodForm.submit.click();
				token = 2;
				
				
				
				break;
			case '放弃' :

				document.location.reload();
				break;


			case '删除' :
				if (tabSymbol == '') {
					alert("请选择！");
					return;
				}

	
				
				$f = $('#fispriodForm');
				if (confirm("其子内容设定以及相关计算设定也会随之删除，确定继续删除？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/debtasset/ea_delAssetsDebt.jspa?pageNumber="
											+ pNumber + "&invCCbsgl.tabSymbol="
											+ tabSymbol);
					document.fispriodForm.submit.click();
					$("tr#" + tabSymbol).remove();
					var myiframe = window.parent.document.getElementById("main2");
					myiframe.src = myiframe.src;
					tabSymbol = "";
					token = 2;
					
					

				}
				
				break;
			case '设置每页显示条数' :

				var url = basePath
						+ "ea/debtasset/ea_getDebtAssetsList.jspa?search=" + search+"&tabPSymbol="+tabPSymbol;
				numback(url);
				break;
			
		}
	}
	// 点击选中
	$(".fexlist tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				 tabSymbol = this.id;

				 
				 window.parent.document.getElementById("tabSymbol").value=tabSymbol;
				 parent.getIncomeList();
			});
	$(".fexlist tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				tabSymbol = this.id;
				if(tabSymbol.indexOf("sa")!=-1){
					return;
				}
				 window.parent.document.getElementById("tabSymbol").value=tabSymbol;
				action("修改");
			});
	// 根据条件查询
	$("#tosearch").click(function() {
		

		$("#searchForm")
				.attr(
						"action",
						basePath + "ea/debtasset/ea_toSearch.jspa?pageNumber="
								+ pNumber);
		document.searchForm.submit.click();
	});

});
function re_load() {
	if (token)
		document.location.reload();
	
      window.parent.parent.updateTree(tabPSymbol);
}
//判断添加是否合理 损益类只能存在一笔C和一笔D其他均为E
function isRight(){
	var result="";
	var url = basePath+"ea/debtasset/sajax_ea_isRight.jspa?dates="+new Date();
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		async:false,
		success:function(data){
			var me = eval("("+data+")");
			var listC = me.listC;
			var listD = me.listD;
			result=listC.length+","+listD.length;
			
	         
			
		},
		error:function(data){
			alert("获取失败");
		}
		
	});
	return result;
}

//判断序号是否重复
function isReaptBySeq(){

	var re="";
	
	 var str_data=$("#fispriodForm input").map(function(){
		 var value=$(this).val();
		 if($(this).attr("name").indexOf("cglAtion")!=-1){
			 if($(this).attr("checked")==true){
				 value="Y";
			 }else{
				 value="N";
			 }
		 }
		  return ($(this).attr("name")+'='+value);
		}).get().join("&") ;


	var url = basePath+"ea/debtasset/sajax_ea_isRepeatBySeq.jspa?dates="+new Date();
	$.ajax({
		url:url,
		type:"get",
		dataType:"json",
		async:false,
		data: str_data,
		success:function(data){
			var me = eval("("+data+")");
			 var result = me.result;
			 re = result;

		
			
		},
		error:function(data){
			alert("获取失败");
		}
		
	});
	
	return re;
}



