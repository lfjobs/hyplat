$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if(ott == "00"){
		$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '奖励管理',
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
					name : '送审',
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
					name : '奖励表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '汇总表',
					bclass : 'excel',
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
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	}else if(ott == "01"){
		$('.JQueryflexme').flexigrid({
			height : 'auto',
			width : 'auto',
			minwidth : 30,
			title : '督察审核奖励管理',
			minheight : 80,
			buttons : [{
				name : '督察通过',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '督察退回',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},	{
				name : '查询',
				bclass : 'mysearch',
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
	}else if(ott == "02"){
		$('.JQueryflexme').flexigrid({
			height : 'auto',
			width : 'auto',
			minwidth : 30,
			title : '董事长审核奖励管理',
			minheight : 80,
			buttons : [{
				name : '董事长通过',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '董事长退回',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},	{
				name : '查询',
				bclass : 'mysearch',
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
	}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.AddForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (rewardid == "") {
					alert('请选择项目');
					return
				}
				var  r = $("tr#"+rewardid).find("span#status").text()
				if(r == "10" || r == "21" || r == "31"){
					$("tr#"+rewardid).find("span[id]").each(function(){
						$at = $("#AddForm");
						$at.find("input#"+this.id).attr("value",$(this).text());
						
						if(this.id == "rewstatus"){
							$at.find(".jllx[value='"+$(this).text()+"']").attr("checked","checked");
						}
						if(this.id == "oneormore"){
							$at.find(".jl[value='"+$(this).text()+"']").attr("checked","checked");
						}
						if(this.id == "companyid"){
							$at.find("select#companyid").find("option[value='"+$(this).text()+"']").attr("selected","selected");
						}
						if(this.id == "codeid"){
							$at.find("select#codeid").find("option[value='"+$(this).text()+"']").attr("selected","selected");
						}
					});
				$("#jqModel").jqmShow();
				}else{
					alert("已送审项目不可以修改!");
					return 
				}
				break;
			case '删除' :
				if (rewardid == "") {
					alert('请选择项目');
					return
				}
				var  r = $("tr#"+rewardid).find("span#status").text()
				if(r == "10" || r == "21" || r == "31"){
					$f = $('#rewardForm');
					if (confirm("是否删除？")) {
						$f.attr("target", "hidden")
								.attr("action",
										basePath
												+ "ea/reward/ea_delReward.jspa?reward.rewardid="
												+ rewardid );
						document.rewardForm.submit.click();
						$("tr#" + rewardid).remove();
						rewardid = "";
						token = 11;
					}
				}else{
					alert("已送审项目不可以删除!");
					return 
				}
				break;
			case '送审' :
				getchk();
				if (rewardids == "") {
					alert('请选择项目');
					return
				}
				var m = 0;
				var str=rewardids.split(',');
				for(var i=0;i<str.length;i++){
					if(str[i] != ""){
						var  r = $("tr#"+str[i]).find("span#status").text()
						if(r == "20" || r == "30" || r == "99"){
							 $("input[value='"+str[i]+"']").attr("checked","");
							m++;	
						}
					}
				}
				if(m>0){
					alert("已送审项目不能再次送审!");
					return
				}
				var url = basePath + "ea/reward/sajax_ea_upSTATUS.jspa?rewardids=" +
					rewardids + "&aud=20&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						alert("操作成功！");
						document.location.href = basePath
							+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
							+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
			case '督察通过' :
				getchk();
				if (rewardids == "") {
					alert('请奖励项目');
					return
				}
				var url = basePath + "ea/reward/sajax_ea_upSTATUS.jspa?rewardids=" +
					rewardids + "&aud=30&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						alert("操作成功！");
						document.location.href = basePath
							+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
							+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
			case '督察退回' :
				getchk();
				if (rewardids == "") {
					alert('请奖励项目');
					return
				}
				var url = basePath + "ea/reward/sajax_ea_upSTATUS.jspa?rewardids=" +
					rewardids + "&aud=21&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						alert("操作成功！");
						document.location.href = basePath
							+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
							+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
				getchk();
			case '董事长通过' :
				if (rewardids == "") {
					alert('请奖励项目');
					return
				}
				var url = basePath + "ea/reward/sajax_ea_upSTATUS.jspa?rewardids=" +
					rewardids + "&aud=99&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						alert("操作成功！");
						document.location.href = basePath
							+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
							+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
			case '董事长退回' :
				getchk();
				if (rewardids == "") {
					alert('请奖励项目');
					return
				}
				var url = basePath + "ea/reward/sajax_ea_upSTATUS.jspa?rewardids=" +
					rewardids + "&aud=31&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						alert("操作成功！");
						document.location.href = basePath
							+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
							+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
			case '奖励表' :
				document.SearchOneForm.reset();
				$("#jqModelOneSearch").jqmShow();
				break;
			case '汇总表' :
				document.SearchMoreForm.reset();
				$("#jqModelMoreSearch").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url  = basePath
						+ "ea/reward/ea_getStaffReward.jspa?search="
						+ search +"&ott=" + ott ;
				numback(url);
				break;
		}
	}
//	$(".JQueryflexme tr[id]").dblclick(function() {
//		rewardid = this.id;
////		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
//			});
//	$(".JQueryflexme tr[id]").click(function() {
//		rewardid = this.id;
//		$("input.JQuerypersonvalue", $(this))
//				.attr("checked", "checked");
//			});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/reward/ea_toSearch.jspa?pageNumber="
						+ ppageNumber + "&ott=" +ott);
		document.SearchForm.submit.click();
	});
	// 打印One
	$("#searchOne").click(function() {
		var x = $("#SearchOneForm").find("select#status").val();
		var y = $("#SearchOneForm").find("select#rewstatus").val();
		var z = $("#SearchOneForm").find("select#oneormore").val();
		var o = $("#SearchOneForm").find("input#rewtimes").val();
		var url = basePath + "ea/reward/ea_printONE.jspa?reward.status="+x
			+ "&reward.rewstatus="+y + "&reward.oneormore="+z + "&reward.rewtimes="+o ;
		open(url);

		$("#jqModelOneSearch").jqmHide();
	});
	// 打印More
	$("#searchMore").click(function() {
		var x = $("#SearchMoreForm").find("input#rewtimes").val();
		var url = basePath + "ea/reward/ea_printMORE.jspa?reward.rewtimes="+x ;
		open(url);

		$("#jqModelOneSearch").jqmHide();
	});
	//草稿保存
	$("#savedraft").click(function() {
		$("form :input").trigger("blur");
		 if ($("form .error").length) {
            return;
        }
		var s = $("#AddForm").find("select#companyid").val();
		if( s=="" ){
			alert("请选择公司!");
			return;
		}
		if($("#gr").attr("checked") == true && $("#addtable").find("input#staffid").val() == ""){
			alert("请选择人员!");
			return;
		}
		var y = $("#AddForm").find("select#codeid").val();
		if( y=="" ){
			alert("请选择荣誉!");
			return;
		}
		
		$('#AddForm')
				.attr("target", "hidden")
				.attr("action",
						basePath
								+ "ea/reward/ea_save.jspa?pageNumber="
								+ ppageNumber + "&aud=10");
		document.AddForm.submit.click();
		token = 2;
		$("#jqModel").jqmHide();

	});
	//送审
	$("#submitaudit").click(function() {
		$("form :input").trigger("blur");
		 if ($("form .error").length) {
           return;
     	}
		var s = $("#AddForm").find("select#companyid").val();
		if( s=="" ){
			alert("请选择公司!");
			return;
		}
		if($("#gr").attr("checked") == true && $("#addtable").find("input#staffid").val() == ""){
			alert("请选择人员!");
			return;
		}
		var y = $("#AddForm").find("select#codeid").val();
		if( y=="" ){
			alert("请选择荣誉!");
			return;
		}
		$('#AddForm')
				.attr("target", "hidden")
				.attr("action",
						basePath
								+ "ea/reward/ea_save.jspa?pageNumber="
								+ ppageNumber + "&aud=20");
		document.AddForm.submit.click();
		token = 2;
		$("#jqModel").jqmHide();

	});
	// 重置
	$("input#res").click(function() {
		document.AddForm.reset();
		});
	$("select#companyid").change(function(){
		comID = $(this).val();
		$("#companyname",$("#AddForm")).attr("value", $(this).find("option:selected").text());
	});
	$("select#codeid").change(function(){
		$("#codevalue",$("#AddForm")).attr("value", $(this).find("option:selected").text());
		$("#money",$("#AddForm")).attr("value", $(this).find("option:selected").attr("title"));
	});
	$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	}); 
	$("#isSubmit").click(function(){// 选择确定
		var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
//		var parm = $("#parm",$("#jqmWindow2")).attr("value");
		
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffid").text();//弹出框的页面存在于span中才取得到
		var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffname").text();
		var value4 = window.frames["ifr"].$('tr#'+value1).find("span#organizationid").text();
		var value5 = window.frames["ifr"].$('tr#'+value1).find("span#organizationname").text();
		var value6 = window.frames["ifr"].$('tr#'+value1).find("span#deppostid").text();
		var value7 = window.frames["ifr"].$('tr#'+value1).find("span#postname").text();

		$("#staffid",$("#AddForm")).attr("value",value2);
		$("#staffname",$("#AddForm")).attr("value",value3);
		$("#organizationid",$("#AddForm")).attr("value",value4);
		$("#orgname",$("#AddForm")).attr("value",value5);
		$("#deptid",$("#AddForm")).attr("value",value6);
		$("#deptname",$("#AddForm")).attr("value",value7);
		
		$("#ifr").attr("src","");
	    $("#jqmWindow2").jqmHide();
	});
	// radio按钮事件;
	$(".jl").click(function(){
		if(this.id =="tt"){
			$("a",$("#AddForm")).hide();
			$("#staffid",$("#AddForm")).attr("value","");
			$("#staffname",$("#AddForm")).attr("value","");
			$("#organizationid",$("#AddForm")).attr("value","");
			$("#orgname",$("#AddForm")).attr("value","");
			$("#deptid",$("#AddForm")).attr("value","");
			$("#deptname",$("#AddForm")).attr("value","");
		}else if(this.id =="gr"){
			$("a",$("#AddForm")).show();
		}
	});
	
	$(".jllx").click(function(){
//		alert($("input.jllx").val());
		getSel($(this).val());
	});
	
	
	//加载初始化
	getSel("00");
	
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/reward/ea_getStaffReward.jspa?pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value") + "&ott=" + ott;
}
//选择责任人       
function searchCoach(){
	if(comID == ""){
		alert("请选择单位！");
		return;
	}
	 var url = "ea/reward/ea_getSCO.jspa?comID="+ comID;
	 getValueForParm('AddForm','staffid',url);
}
//打开页面
function getValueForParm(attachTable,parm,url){
	 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
	 $("#parm",$("#jqmWindow2")).attr("value",parm);
  	 $("#ifr").attr("src",basePath+url);
  	 $("#jqmWindow2").jqmShow();
}
/**
* 操作全选复选框事件
**/
function doCheck(obj)
{
	var str="";
	var inputs=document.getElementsByTagName("input");
	for(var i=0;i<inputs.length;i++)
	{
		if(inputs[i].type=="checkbox" && inputs[i].id=="chkMsgId23") //刷选出所有复选框
		{
			inputs[i].checked=obj.checked; 
			if(inputs[i].checked==true){
				 str=str+inputs[i].value+",";
			}
		}	
	}
	rewardids=str;
}

/**
* 获取所有复选框
**/
function getCheckBox()
{
	var inputs=document.getElementsByTagName("input");
	var chkInputs=new Array();
	var j=0;
	for(var i=0;i<inputs.length;i++)
	{
		if(inputs[i].type=="checkbox" && inputs[i].id=="chkMsgId23") //刷选出所有复选框
		{
			chkInputs[j]=inputs[i].checked;					
			j=j+1;
		}
	}
	return chkInputs;
}	
/**
*	单选复选框选中事件
**/
function toChkSon(){
	var chkInputs=document.getElementsByTagName("input");
	
	for(var i=0;i<chkInputs.length;i++)
	{
		if(chkInputs[i].type=="checkbox" && chkInputs[i].id=="chkMsgId23" && chkInputs[i].checked==true){
			 rewardid = chkInputs[i].value;
		}else{
			if(chkInputs[i].value == rewardid)
				rewardid = '';
		}
	}				
	
}
/**
 *	获取所有选中 
 **/
function getchk(){
	var str="";
	var chkInputs=document.getElementsByName("chkMsgId23");
	for(var i=0;i<chkInputs.length;i++)
	{
		if(chkInputs[i].type=="checkbox" && chkInputs[i].id=="chkMsgId23" 
			&& chkInputs[i].checked==true){
			 str=str+chkInputs[i].value+",";
		}
	}	
	rewardids=str;	
}
function getSel(e){
		
	var url = basePath + "ea/reward/sajax_ea_getSel.jspa?" +
			"reward.rewstatus="+e+"&date="+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var honorList = member.honorList;
			
			 if (null != honorList) {
                 var htmlStr="<option value=''>请选择</option>";
                 for (var i = 0; i < honorList.length; i++) {
                     htmlStr+="<option title='"+honorList[i].honorMoney+
                     "' value='"+honorList[i].honorID+"'>"+honorList[i].honorName+"</option>";
                 	}
               $("select#codeid").html(htmlStr);
			 }
			 $("#money",$("#AddForm")).attr("value","");
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}