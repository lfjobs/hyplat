$(document).ready(function() {
	 $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
	$('.JQueryflexme').flexigrid({
				height : 335,
				width : 'auto',
				minwidth : 30,
				title : '社会单位人员信息--'+basicInfo,
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'prev',
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
					name : '导出',
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
			case '导出' :
				var headerLists=new Array();
					$("#headerList").children().each(function(i){
						headerLists[i]=$(this).val();
					});
				var headerListsize=0;
				if (headerLists.length%3==0) headerListsize=headerLists.length/3;
				else headerListsize=Math.floor(headerLists.length/3)+1;
				
				var htmlShow="<table cellpadding='0' cellspacing='0' id='showTable' width='500px'>";	
					htmlShow+="<tr><td ><input type='checkbox' id='qx' /></td><td >全选:</td></tr>";	
				for (var y = 0; y <= headerListsize; y++) {// 循环 3 列每一行    
					htmlShow+="<tr>";
						for (var i = y*3; i < y*3+3&&i<headerLists.length; i++) {// 循环 依次循环显示  3  列
						htmlShow+="<td><input type='checkbox' name='feildName' id='feild' value="+"'"+i+"'"+"/></td><td width='120px' align='left'>"+headerLists[i]+";</td>"	;
						}
					htmlShow+="</tr>";
				}
					htmlShow+="</table>";
			   $("div#showTablefaild").html(htmlShow);
			   $("#jqModelExcel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/suminfo/ea_"+actionName+".jspa?search=" + search +"&basicInfo="+basicInfo+"&conditions="+conditions;
				numback(url);
				
				break;
			case '返回' :
				document.location.href=basePath+"page/ea/main/navigation/pdc_sum_statements.jsp";
				break;
			case '查询':
				$("#jqModelSearch").jqmShow();
			    break;
		}

	}
	$("#searchStaff").click(function(){
			$f = $('#postSearchForm');
			var url=basePath+ "ea/suminfo/ea_"+actionName+".jspa?basicInfo="+basicInfo;
			$f.attr("action",url);
			document.postSearchForm.submit.click();
	});
	$("input#qx").live('click',function(){
	 		$("#showTable").find("input#feild").each(function(){
	 			 $(this).attr("checked",true);
	 		});
	});
	$("a.aUrl").click(function() {
		var uid = $(this).attr("id");
		var urlReturn = OpenWord(uid, 2);
	});
	
	$("#showReturn").click(function(){
		$("#jqModelExcel").jqmHide();
	});
	$("#showSure").click(function(){
		var xz=false;
			$("#showTable").find("input#feild").each(function(){
	 			if ($(this).attr("checked")) {
	 			 xz=true;
	 			 return;
	 			}
	 		});
		if (!xz)
		              {
		               alert('请至少选择一个要导出的字段信息!!');
		                     return
		               }
		 if(confirm("是否导出？")){
		              	  $f = $('#showExcelForm');
							var url= basePath + "ea/suminfo/ea_"+actionNameExcel+".jspa?basicInfo="+basicInfo;
							$f.attr("target","_blank").attr("action",url);
							document.showExcelForm.submit.click();
					  }
	});
	});


	

