$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.attendconf').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '考勤预设项目' ,
				minwidth : 30,
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
				document.jqModelAddForm.reset();
				$("div#happents").hide();
				$("input#playtime").attr("value","");
				$("input#stoptime").attr("value","");
				$("input#maxnum").css("display","none").attr("value","");
				$("span#maxnum").text("");
				$("input#minnum").css("display","none").attr("value","");
				$("span#minnum").text("");
				$("input#stusnum").css("display","none").attr("value","");
				$("span#stusnum").text("");
				$("span#stusnum0").text("");
				$("input#confname").attr("readonly","");
				$("#jqModelAdd").jqmShow();
				break;
			case '修改' :
				document.jqModelAddForm.reset();
				$("input#confname").attr("readonly","");
				if(confID == ""){
					alert("请选择!");
					return;
				}
				$at = $("#jqModelAddForm");
				$("tr#"+confID).find("span[id]").each(function(){
					var v = $(this).text();
					$at.find("input#"+this.id).attr("value",v);
					
					if(this.id == "confstus"){
						if(v == "00"){
							$at.find("input#confname").attr("readonly","readonly");
						}
					}else if(this.id == "happents"){
						$at.find("select#happents").find("option[value='"+v+"']").attr("selected","selected");
						if(v == "00"){
							$("div#happents").hide();
						}else{
							$("div#happents").show();
						}
					}else if(this.id == "stopstus"){
						$at.find("select#stopstus").find("option[value='"+v+"']").attr("selected","selected");
					}else if(this.id == "minstus"){
						$at.find("select#minstus").find("option[value='"+v+"']").attr("selected","selected");
						if(v == "00"){
							$at.find("input#minnum").css("display","none");
							$at.find("span#minnum").text("");
						}else{
							$at.find("input#minnum").css("display","");
							if(v == "01"){
								$at.find("span#minnum").text(" 天");
							}else if(v == "02"){
								$at.find("span#minnum").text(" 小时");
							}else if(v == "03"){
								$at.find("span#minnum").text(" 分钟");
							}
						}
					}else if(this.id == "maxstus"){
						$at.find("select#maxstus").find("option[value='"+v+"']").attr("selected","selected");
						if(v == "00"){
							$at.find("input#maxnum").css("display","none");
							$at.find("span#maxnum").text("");
						}else{
							$at.find("input#maxnum").css("display","");
							if(v == "01"){
								$at.find("span#maxnum").text(" 天");
							}else if(v == "02"){
								$at.find("span#maxnum").text(" 小时");
							}else if(v == "03"){
								$at.find("span#maxnum").text(" 分钟");
							}
						}
					}else if(this.id == "stus"){
						$at.find("select#stus").find("option[value='"+v+"']").attr("selected","selected");
						if(v == "00"){
							$at.find("input#stusnum").css("display","none");
							$at.find("span#stusnum").text("");
							$at.find("span#stusnum0").text("");
						}else{
							$at.find("input#stusnum").css("display","");
							if(v == "01" || v == "02"){
								$at.find("span#stusnum").text("");
								$at.find("span#stusnum0").text("");
							}else if(v == "03" || v == "04"){
								$at.find("span#stusnum0").text(" X");
								$at.find("span#stusnum").text(" %");
							}
						}
					}
				});

				$("#jqModelAdd").jqmShow();
				break;
			case '删除' :
				if(confID == ""){
					alert("请选择!");
					return;
				}
				var cs = $("tr#"+confID).find("span#confstus").text();
				if(cs == "00"){
					alert("预设信息不可删除!");
					return ;
				}
				$f = $('#attendconfForm');
				if (confirm("是否删除？")) {
					$f.attr("target", "hidden")
							.attr("action",
									basePath
											+ "ea/attendconf/ea_del.jspa?attendConfId=" + confID );
					document.attendconfForm.submit.click();
					$("tr#" + confID).remove();
					confID = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/attendconf/ea_getAttendConf.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".attendconf tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		confID = this.id;
	});
	$(".attendconf tr[id]").dblclick(function() {
		confID = this.id;
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	$("input#confname").change(function(){
		var formd = $("#jqModelAddForm").serialize();
		var url = basePath+"ea/attendconf/sajax_ea_valConfName.jspa?d="+Math.ceil(Math.random()*1000);
	    $.ajax({
	        url : encodeURI(url),
	        type : "get",
	        async : false,
	        dataType : "application/json",
	        data:formd,
	        success : function cbf(data) { 
	            var member = eval("(" + data + ")");
	            	if(member.suc > 0){
	            		$("input#confname").attr("value","");
	            		alert("考勤项目名称重复!");
	            	}
	           },
	        error : function cbf(data) {
	            alert("获取数据失败!");
	        }
	    });
	});
	//select 发生时间内容事件
	$("select#happents").change(function(){
		var v = $(this).val();
		if(v == "00"){
			$("div#happents").hide();
			$("input#playtime").attr("value","");
			$("input#stoptime").attr("value","");
		}else{
			$("div#happents").show();
		}
	});
	
	
	//select 最大事件
	$("select#maxstus").change(function(){
		var v = $(this).val();
		if(v == "00"){
			$("input#maxnum").css("display","none").attr("value","");
			$("span#maxnum").text("");
		}else{
			$("input#maxnum").css("display","");
			if(v == "01"){
				$("span#maxnum").text(" 天");
			}else if(v == "02"){
				$("span#maxnum").text(" 小时");
			}else if(v == "03"){
				$("span#maxnum").text(" 分钟");
			}
		}
	});
	//select 最小事件
	$("select#minstus").change(function(){
		var v = $(this).val();
		if(v == "00"){
			$("input#minnum").css("display","none").attr("value","");
			$("span#minnum").text("");
		}else{
			$("input#minnum").css("display","");
			if(v == "01"){
				$("span#minnum").text(" 天");
			}else if(v == "02"){
				$("span#minnum").text(" 小时");
			}else if(v == "03"){
				$("span#minnum").text(" 分钟");
			}
		}
	});
	//select 奖惩
	$("select#stus").change(function(){
		var v = $(this).val();
		if(v == "00"){
			$("input#stusnum").css("display","none").attr("value","");
			$("span#stusnum").text("");
			$("span#stusnum0").text("");
		}else{
			$("input#stusnum").css("display","");
			if(v == "01" || v == "02"){
				$("span#stusnum").text("元");
				$("span#stusnum0").text("");
			}else if(v == "03" || v == "04"){
				$("span#stusnum0").text(" X");
				$("span#stusnum").text(" %");
			}
		}
	});
	
	$("#addbut").click(function(){
		$("form :input").trigger("blur");
		if ($("form .error").length) {
           return;
		}
		$('#jqModelAddForm')
		.attr("target", "hidden")
		.attr("action",
				basePath
						+ "ea/attendconf/ea_save.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelAddForm.submit.click();
		token = 2;
	});
	// 取消
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
	
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/attendconf/ea_getAttendConf.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
