$(function() {  
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 328,
				width : 'auto',
				minwidth : 30,
				title : '会务组织机构人员配备',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
					name : '修改',
					bclass : 'edit',
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
				document.dtconferenceorgForm.reset();
				$("#jqModelSearch").jqmShow();
				break;
			case '修改':
				if(conferenceorgid == ""){
					alert("请选择！");
					return; 
				}
				$t = $("table#dtconOrgTable");
				$p = $("tr#" + conferenceorgid);
				document.dtconferenceorgForm.reset();
				$("#jqModelSearch").jqmShow();
				$p.find("span[id]").each(function() {
					$t.find("input#" + this.id).attr("value",($(this).text()));
					if(this.id == "remarks"){
						$t.find("#" + this.id).attr("value",($(this).text()));
					}
				});
				
			
				break;
			case '删除':
				if(conferenceorgid == '') {
					alert("请选择！");
					return;
				}
				$f = $('#mainForms');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					$f.attr("target", "hidden").attr("action",
									basePath
											+ "ea/dtconferenceorg/ea_delDtconferenceOrg.jspa?pageNumber="
											+ pNumber + "&dtconferenceorg.conferenceorgid="
											+ conferenceorgid );
					document.mainForms.submit.click();
					$("tr#" + conferenceorgid).remove();
					conferenceorgid = "";
					token = 11;
				}
				break;
			case '导出':
				var url = basePath
						+ "ea/dtconferenceorg/ea_showDtconOrgExcel.jspa?search=" + search;
				open(url);
				break;
			case '设置每页显示条数' :
			var url = basePath
						+ "ea/dtconferenceorg/ea_getAllDtconOrg.jspa?";
				numback(url);
				break;
		}
	}
	
	//控件单击事件
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				conferenceorgid = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				conferenceorgid = this.id;
				action("修改");
			});
	//弹出框保存事件		
	$("#addDtconorg").click(function() {
		$(".put3").trigger("blur");
		$(".timeformat").trigger("blur");
			if ($("form .error").length) {
			return;
		}
		
		var url = basePath + "ea/dtconferenceorg/ea_addDtconferenceOrg.jspa?pageNumber="+ pNumber+ "&search=" + search;
		$("#dtconferenceorgForm").attr("target", "hidden").attr("action",url);
		document.dtconferenceorgForm.submit.click();
		token = 2;
			
	});
	
	//搜索窗口
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	
	$("#isBack").click(function(){// 返回
		       $("#jqmWindow2").jqmHide();
		    }); 
	//选择员工信息	   
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
		//alert(value1 +"--"+value2+"--"+value3)
		if(parm != "")
			$("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
		if(parmNum != "")
			$("#"+parmNum,$("#"+myfrom)).attr("value",value3).trigger("blur").css("color","black");
			
		$("#dtconOrgTable").find("#staffName").attr("value",value2);
		$("#dtconOrgTable").find("#responsible").attr("value",value1);
		$("#ifr").attr("src","");
        $("#jqmWindow2").jqmHide();
       });
      
});
	//选择责任人       
function searchCoach(v,w){
	repid = v;
	cstffidd = w;
	 var url = "ea/dtconferenceorg/ea_getAllStaff.jspa?";
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
//刷新事件
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/dtconferenceorg/ea_getAllDtconOrg.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}