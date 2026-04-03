$(document).ready(function() {
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
				title : '科目信息管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				}, {
					name : '设置教学项目学时',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置教学项目内容',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				subjKey = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (subjKey == "") {
					alert('请选择科目');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#subjKey').val(subjKey);
					var url = basePath + "ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=delSubject&pageNumber=" 
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();					
					$("tr#" + subjKey).remove();
					subjKey = "";
					token = 11;
				}
				break;
			case '修改' :
				if (subjKey == "") {
					alert('请选择科目');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + subjKey);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});						
				$("#subjTypeSelect").val($p.find("span#subjKeyVal").html());								
				$("#jqModel").jqmShow();
				break;			
			case '设置每页显示条数' :
				var url = basePath + "ea/subjectManager/ea_doSubjectManagerAction.jspa?search=" + search;
				numback(url);
				break;	
			case '设置教学项目学时' :
				$("#jqModelOfSyllabus").jqmShow();
				$("select#type",$("form#syllabusForm")).trigger("change");
				break;
			case '设置教学项目内容' :
				$.getListOfTeachingContentByAjax("","","","");
				$("#jqModelOfContent").jqmShow();
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				subjKey = this.id;				
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {		
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=updateSubject&pageNumber=" + pNumber);		
		if (subjKey == "") {			
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}		
		document.cstaffForm.submit.click();
		token = 2;		
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
	});	
	
	/*******教学内容设置与教学时间设置****/
	$(":checkbox").click(function(){
		if($(this).attr("checked")){
			$(this).parents("tr").find("input[type!='checkbox']").attr("disabled",false);
		}else{
			$(this).parents("tr").find("input[type!='checkbox']").attr("disabled",true);
		}
	});
	$(".JQuerySubmitOfSyllabus").click(function() {		
		/*$(".posnum", $("table#tableOfSyllabus")).trigger("blur");
		if ($("#syllabusForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}*/
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"430px"}).appendTo("#jqModelOfSyllabus");     
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理。。。").appendTo("#jqModelOfSyllabus").css({display:"block",left:($("#jqModelOfSyllabus").outerWidth(true)-100) / 2,top:($("#jqModelOfSyllabus").outerHeight(true) - 45) / 2});  
		var urlString=basePath+"ea/subjectManager/sajax_ea_toSaveTeachingContentByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: $("#syllabusForm").serialize(),
	             dataType: "json",
	             async: false,
	             success: function(data){
		             var data = eval("(" + data + ")"); 
		             $("select#type",$("form#syllabusForm")).trigger("change");
	             },
	             error: function(data) {
	                    alert("数据异常");
	             }
	         });
	});
	$("select#type",$("form#syllabusForm")).bind("change",function(){
		$("input[id='id'],input[id='key'],input[id='time']",$("form#syllabusForm")).val("");
		$("input[id]",$("form#syllabusForm")).attr("disabled",true);
		$(":checkbox",$("form#syllabusForm")).attr("checked",false);
		var typeValue=$(this).children("option:selected").val();
		var urlString=basePath+"ea/subjectManager/sajax_ea_getListOfTeachingContentByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: {"ddsrsyllabus.type":typeValue},
	             dataType: "json",
	             async: false,
	             success: function(data){
	             var data = eval("(" + data + ")"); 
	             var beanList=data.beanList;
		         	for ( var i = 0; i < beanList.length; i++) {
						var ddsrsyllabus=beanList[i];
						var $c=$("input[value="+ddsrsyllabus.program+"]").parents("tr");
						$c.find("input[id='key']").val(ddsrsyllabus.key);
						$c.find("input[id='id']").val(ddsrsyllabus.id);
						$c.find("input[id='subject']").val(ddsrsyllabus.subject);
						$c.find("input[id='type']").val(ddsrsyllabus.type);
						$c.find("input[id='program']").val(ddsrsyllabus.program);
						$c.find("input[id='programType']").val(ddsrsyllabus.programType);
						$c.find("input[id='time']").val(ddsrsyllabus.time);
						$c.find("input[id='serial']").val(ddsrsyllabus.serial);
						$c.find("input[id]").attr("disabled",false);
						$c.find(":checkbox").attr("checked",true);
					 }
		         	 $("#jqModelOfSyllabus").find("div.datagrid-mask-msg").remove();
	                 $("#jqModelOfSyllabus").find("div.datagrid-mask").remove();
	             },
	             error: function(data) {
	                    alert("数据异常");
	                 $("#jqModelOfSyllabus").find("div.datagrid-mask-msg").remove();
	                 $("#jqModelOfSyllabus").find("div.datagrid-mask").remove();
	             }

	         });
		
	});
	var index=1;
	$("img.add").click(function(){
		$("tr#clone").after(
					$("tr#clone").clone(true).attr("id","clone"+index).css("display","")
		);
		$("tr#clone" + index).find(':input').each(function() {
					$(this).attr("name","ddsrcontentMap[" + index + "]."+$(this).attr("name"));
		});
		index++;
	});
	$("img.edit",$("tr[class!='clone']")).click(function(){
		$(this).parents("tr").find(":input").each(function(){
			$(this).attr("name","ddsrcontentMap[" + index + "]."+$(this).attr("name")).show();
		});
		$(this).parents("tr").find("span").each(function(){
			$(this).css("display","none");
		});
		$(this).unbind("click");
		$(this).parents("tr").unbind("dblclick");
		index++;
	});
	$("tr[class!='clone']").dblclick(function(){
		$(this).find(":input").each(function(){
			$(this).attr("name","ddsrcontentMap[" + index + "]."+$(this).attr("name")).show();
		});
		$(this).find("span").each(function(){
			$(this).css("display","none");
		});
		$(this).unbind("dblclick");
		$(this).find("img.edit").unbind("click");
		index++;
	});
	$("img.del").click(function(){
		$(this).parents("tr").remove();
		var key=$(this).parents("tr").find("input#key").attr("value");
		if(key==""){
			index--;
			return false;
		}
        var urlString=basePath+"/ea/subjectManager/sajax_ea_deleteOfTeachingContentOfDetailsByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: {"ddsrcontent.key":key},
	             dataType: "json",
	             async: false,
	             success: function(data){
		             var data = eval("(" + data + ")");
		             $.getListOfTeachingContentByAjax("","","","");
	             },
	             error: function(data) {
	                    alert("数据异常");
	             }
	         });
	});
	$("img.save").click(function() {
		if(index==1){
			return false;
		}
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"430px"}).appendTo("#jqModelOfContent");     
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理。。。").appendTo("#jqModelOfContent").css({display:"block",left:($("#jqModelOfContent").outerWidth(true)-100) / 2,top:($("#jqModelOfContent").outerHeight(true) - 45) / 2});
        var urlString=basePath+"/ea/subjectManager/sajax_ea_toSaveTeachingContentOfDetailsByAjax.jspa?date="+new Date();
		$.ajax({
	             type: "post",
	             url: urlString,
	             data: $("#contentForm").serialize(),
	             dataType: "json",
	             async: false,
	             success: function(data){
		             var data = eval("(" + data + ")"); 
		             $.getListOfTeachingContentByAjax("","","","");
		             $("#jqModelOfContent").find("div.datagrid-mask-msg").remove();
		             $("#jqModelOfContent").find("div.datagrid-mask").remove();
	             },
	             error: function(data) {
	                    alert("数据异常");
	                 $("#jqModelOfContent").find("div.datagrid-mask-msg").remove();
			         $("#jqModelOfContent").find("div.datagrid-mask").remove();
	             }
	         });
	});
	$("img.search").click(function(){
		var type=$("tr#search",$("table#tableOfContent")).find("select#type").val();
		var subject=$("tr#search",$("table#tableOfContent")).find("select#subject").val();
		var program=$("tr#search",$("table#tableOfContent")).find("select#program").val();
		var content=$("tr#search",$("table#tableOfContent")).find("input#content").val();
		$.getListOfTeachingContentByAjax(type,subject,program,content);
	});
	jQuery.extend({
		getListOfTeachingContentByAjax:function (type,subject,program,content) {
			index=1;
			var urlString=basePath+"ea/subjectManager/sajax_ea_getListOfTeachingContentOfDetailsByAjax.jspa?date="+new Date();
			$.ajax({
		             type: "post",
		             url: urlString,
		             data: {"ddsrcontent.type":type,"ddsrcontent.subject":subject,"ddsrcontent.program":program,"ddsrcontent.content":content},
		             dataType: "json",
		             async: false,
		             success: function(data){
		             var data = eval("(" + data + ")"); 
		             var beanList=data.beanList;
		             $("tfoot",$("table#tableOfContent")).children("tr[id!='data']").remove();
		             $("tbody",$("table#tableOfContent")).children("tr[id!='clone']").remove();
			         for ( var i = 0; i < beanList.length; i++) {
							var ddsrcontentBeans=beanList[i];
							$("tr#data").before(
									$("tr#data").clone(true).attr("id","data"+i).css("display","")
							);
							$("tr#data" + i).find("span#number").text(i);
							$("tr#data" + i).find("span#type").text(ddsrcontentBeans.type);
							$("tr#data" + i).find("span#subject").text(ddsrcontentBeans.subject);
							$("tr#data" + i).find("span#program").text(ddsrcontentBeans.program);
							$("tr#data" + i).find("span#content").text(ddsrcontentBeans.content);
							$("tr#data" + i).find("select#type").val(ddsrcontentBeans.type).css("display","none");
							$("tr#data" + i).find("select#subject").val(ddsrcontentBeans.subject).css("display","none");
							$("tr#data" + i).find("select#program").val(ddsrcontentBeans.program).css("display","none");
							$("tr#data" + i).find("input#content").val(ddsrcontentBeans.content).css("display","none");
							$("tr#data" + i).find("input#id").val(ddsrcontentBeans.id);
							$("tr#data" + i).find("input#key").val(ddsrcontentBeans.key);
						}
		             },
		             error: function(data) {
		                    alert("数据异常");
		             }

		         });
		}
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=showSubjectList&pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");		
	}
}