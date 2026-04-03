$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
		var tit = ""; // flexigrid控件标题
		if(newState == "00"){
			tit = "会议准备阶段管理";
		}else if(newState == "01"){
			tit = "正式会议阶段管理";
		}else if(newState == "02"){
			tit = "会议闭幕阶段管理";
		}
		
	$('.JQueryflexme').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : tit,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
				},{
					name : '修改',
					bclass : 'edit',
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
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '排序会议流程',
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
					}, {
					separator : true
				}]
			});
		
			
	function action(com, grid) {
		switch (com) {
			case '添加':
				atnull();
				$("#dtconferenceForm").find("#conferenceid").attr("value","");
				document.dtconferenceForm.reset();
				$("#jqModelSearch").jqmShow();
				vv();
				break;
			case '修改':
				if(dtconferenceID == ""){
					alert("请选择！");
					return; 
				}
				atnull();
				document.dtconferenceForm.reset();
				$t = $("table#dtconferenceTable");
				$p = $("tr#" + dtconferenceID);
				$p.find("span[id]").each(function() {
					$t.find("input[name]#" + this.id).attr("value",this.outerText);
				});
				$t.find("#annexurl").val($p.find("#annexurl").val());
				vv();
				$("#jqModelSearch").jqmShow();
				break;
			case '删除':
				if(dtconferenceID == ""){
					alert("请选择！");
					return; 
				}
				var url = basePath + "ea/dtconference/ea_deleteDtconference.jspa?newState="+newState;
			
				$("#mainForms")
						.attr("target", "hidden")
						.attr(
								"action",url+"&dtconference.conferenceid=" + dtconferenceID);
								
				document.mainForms.submit.click();
				$("tr#" + dtconferenceID).remove();
				token = 11;
				dtconferenceID = "";
				break;
			case '查询':
				document.selectForm.reset();
				$("#findWindow").jqmShow();
				break;
			case '排序会议流程':
				document.location.href = basePath
				+ "ea/dtconference/ea_sortchild_Dtconference.jspa?newState="+newState;
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/dtconference/ea_getDtconferenceList.jspa?newState="+newState;
				numback(url);
				break;
			case '导出' :
				var url = basePath
						+ "ea/dtconference/ea_showDtconferenceExcel.jspa?newState="+newState +"&search=" + search;
				open(url);
				break;
		}
	}
	
	/** *************************word文档编辑********************************* */
	$(".accessoriesUrl1").click(function(){ //添加修改时
		var accessoriesUrl = $.trim($("#annexurl","#dtconferenceForm").val());
		var urlReturn = OpenWord(accessoriesUrl, 2);
		$("#annexurl","#dtconferenceForm").val(urlReturn);
	});
	
	$(".accessoriesUrl").click(function() { //查看
		$(this).parent().click();
		$p = $("tr#" + dtconferenceID);
		var accessoriesUrl = $.trim($p.find("#annexurl").attr("value"));
		var urlReturn = OpenWord(accessoriesUrl, 2);
		$p.find("#annexurl").attr("value", urlReturn);
		if(accessoriesUrl == ''){
			var upurl = basePath
					+ "ea/dtconference/sajax_ea_updateEdit.jspa?dtconference.conferenceid="
					+ dtconferenceID +"&dtconference.annexurl= " + urlReturn;
			$.ajax({
				url : encodeURI(upurl),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					re_load();
					token = 13;
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		}
	})	;
	/** ********************************************************** */
	
	//双击事件
	$(".JQueryflexme tr[id]").dblclick(function() {
				dtconferenceID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("修改");
			});
			
	//*单击事件
	$(".JQueryflexme tr[id]").click(function() {
				dtconferenceID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
	// onblur事件
	$("#selectForm").find("#startdate").blur(function(){
		if(this.value==""){
			atnull();
		}
	});	
	$("#selectForm").find("#enddate").blur(function(){
		if(this.value==""){
			atnull();
		}
	});			
	//*添加保存
	$("#addDtconference").click(function() {
				var tab = $("#dtconferenceTable"); 
				$(".put3 ").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if(tab.find("#startdate").val() > tab.find("#enddate").val()){
					alert("起时间必须小于至时间！ ");
					tab.find("#enddate").attr("value","");
					tab.find("#enddate").focus(); 
					return; 
				}
				if ($("#dtconferenceForm").find("#responsible").val() == '') {
					alert("责任人不能为空！");
					return;
				}
				//URL 地址
				var url = basePath + "ea/dtconference/ea_addDtconference.jspa?newState="+
						newState +"&pageNumber="+ pNumber + "&search=" + search;
				$("#dtconferenceForm")
						.attr("target", "hidden")
						.attr(
								"action",url);
				document.dtconferenceForm.submit.click();
				token = 2;

			});
				
	//*查询
	$("#selectDtconference").click(function() {
	
		
		if ($("form .error").length) {
				return;
			}
			
		if($("#selectForm").find("#startdate").attr("value") != "" && $("#selectForm").find("#condate").attr("value") == ""){
			alert("必须填写日期！");
			$("#selectForm").find("#condate").focus();
			return ;
		}
		if($("#selectForm").find("#enddate").attr("value") != "" && $("#selectForm").find("#condate").attr("value") == ""){
			alert("必须填写日期！");	
			$("#selectForm").find("#condate").focus();
			return ;
		}			
		$("#selectForm")
				.attr("action",
						basePath
								+ "ea/dtconference/ea_toSeach.jspa?newState="+newState+"&pageNumber="
								+ pNumber );
		document.selectForm.submit.click();
	
	});	
		
	
	
	//搜索窗口
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	
	  $("#isBack").click(function(){// 返回
		       $("#jqmWindow2").jqmHide();
		    }); 
		   
			$("#isSubmit").click(function(){// 选择确定
				var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
				var parm = $("#parm",$("#jqmWindow2")).attr("value");
				var parmNum = $("#parmNum",$("#jqmWindow2")).attr("value");
				var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
				if(value1 == ""){
					alert("请选择");
					return;
				}
				var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parmNum).text();
				
				if(parm != "")
					$("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
				if(parmNum != "")
					$("#"+parmNum,$("#"+myfrom)).attr("value",value3).trigger("blur").css("color","black");
					
				$("#dtconferenceForm").find("#responsible").attr("value",value2);	
				//$("#responsible",$("#"+myfrom)).attr("value",value1).trigger("blur");
				$("#ifr").attr("src","");
		        $("#jqmWindow2").jqmHide();
		        
		        /*********************自动获取岗位明细*********************/
		        var url = basePath+"ea/dtconference/sajax_ea_getPos.jspa?staffIDvalue="+value1 +"&staffNum="+value3;
                $.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) { 
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if(nologin){
							document.location.href ="<%=basePath%>page/ea/not_login.jsp";
						}
						var dept = member.dept;
						if(dept == null){
							$("#dtconferenceForm").find("#postname").attr("value","");
						}else{
							$("#dtconferenceForm").find("#postname").attr("value",dept.postName);	
						}
					},
					error : function cbf(data) {
						alert("获取数据失败!");
					}
				});
		    });  
       
});

//清空状态
	function atnull(){
		$("#dtconferenceForm").find(".error").remove();
		$("#dtconferenceForm").find(".corect").remove();
		$("#selectForm").find(".error").remove();
	}


 //选择责任人       
		function searchCoach(){
			 if(parm == ''){
			 	parm = treeID;
			 }
			 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
			 getValueForParm('cstaffForm','partnerName','childPartnerName',url);
		}
		//打开页面
		function getValueForParm(attachTable,parm,parmNum,url){ 
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm",$("#jqmWindow2")).attr("value",parm);
			 $("#parmNum",$("#jqmWindow2")).attr("value",parmNum);
		  	 $("#ifr").attr("src",basePath+url);
		  	 $("#jqmWindow2").jqmShow();
		}
	


//添加准备信息
function vv() {
				var sel = document.forms["dtconferenceForm"].elements["orgname"];
				sel.length = 0; //为0表全清空，为1表留下一项……
			
			var url = basePath
					+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(cc) {
							var member = eval("(" + cc + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var oList = member.organizationlist;
							var data = new Array();
							data[0] = {
								id : treeID,
								pid : '-1',
								text : treeNames
							};
							for (var i = 0; i < oList.length; i++) {
								data[i + 1] = {
									id : oList[i].organizationID,
									pid : oList[i].organizationPID,
									text : oList[i].organizationName
								};
							}
							$p = $("div#jqModelSearch");
							var t1 = new TreeSelector(
									$p.find("select#orgname")[0],
									data, -1);
							t1.createTree();
							var deptval = $("#"+dtconferenceID,$("#mainForms")).find("#orgname").text();
							$p.find("select#orgname").val(deptval);
							
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});
		}

//刷新事件
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/dtconference/ea_getDtconferenceList.jspa?newState="+newState+"&pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
