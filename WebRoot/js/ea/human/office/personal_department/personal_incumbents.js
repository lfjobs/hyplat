$(document).ready(function() {
	//var len = $("#trwid").find(".trclass").length;137 + len * 27
	window.parent.document.getElementById("mainframe2").style.height = 256 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20 // 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 'auto',
				width : 'auto',
				minwidth : 30,
				title : '正式员工管理 --> ' + ogName,
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'mysearch',
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
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '人员分配',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				search = true;
				break;
			case '导出' :
				url = basePath + "ea/cosincumbent/ea_showExcel.jspa?ogID="
						+ ogID+"&search="+ psearch + "&searchValue=searchValue";
				open(url);
				break;
			case '人员分配' :
				$("#jqModelEntry").jqmShow();
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/cosincumbent/ea_toSearchCStaff.jspa?ogID="
						+ ogID+"&ogName="+ogName+"&pageNumber="
						+ ppageNumber + "&searchValue=searchValue");
		document.cstaffSearchForm.submit.click();
	});
	
	$(".close").click(function(){ //关闭
		re_load();
	});

	
	// radio按钮事件;
	$(".radio").click(function(){
		if(this.id == "radio1"){
			parent.document.getElementById("mainframe2").style.height = 350 + "px";
			$("#jqModelEntry").jqmHide();
			$("#jqModelEntry").jqmShow();
			$("tr.div1").show();
		}else{
			parent.document.getElementById("mainframe2").style.height = 137 + len * 27 + "px";
			$("#jqModelEntry").jqmHide();
			$("#jqModelEntry").jqmShow();
			$("tr.div1").hide();
		}
	});
	
	 //选择责任人       
	$("#searchCoach").click(function(){
		heigh = parent.document.getElementById("mainframe2").offsetHeight;
		parent.document.getElementById("mainframe2").style.height = 300 + "px";
		$("#jqModelEntry").jqmHide();
		$("#jqModelEntry").jqmShow();
		
		var parm = "zm" + $(".radio:checked").val();
		var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
		getValueForParm('cstaffForm','partnerName',url);
	});
	
	function getValueForParm(attachTable,parm,url){ //打开页面
		$("#myform",$("#jqmWindow2")).attr("value",attachTable);
		$("#parm",$("#jqmWindow2")).attr("value",parm);
	  	$("#ifr").attr("src",basePath+url);
	  	$("#jqmWindow2").jqmShow();
	}
	
	$("#isSubmit").click(function(){// 选择确定
		var parm = $("#parm",$("#jqmWindow2")).attr("value");
		var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
		if(parm != "")
		$("#principal",$("#orgEntryForm")).attr("value",value2).trigger("blur");
		$("#principalID",$("#orgEntryForm")).attr("value",value1).trigger("blur");
		$("#ifr").attr("src","");
		
		parent.document.getElementById("mainframe2").style.height = heigh + "px";
	    $(".jqmWindow").jqmHide();
	    $("#jqModelEntry").jqmShow();
	});
	
	$("#isBack").click(function(){// 返回
	   parent.document.getElementById("mainframe2").style.height = heigh + "px";
       $(".jqmWindow").jqmHide();
	   $("#jqModelEntry").jqmShow();
    });
    
    $("#toCommit").click(function() { //保存
    	if($("#postID").val() == ""){
    		alert("请选择岗位！");
			return 
    	}
		if($("#orgEntryForm").find("#principal").val() == ""){
			alert("人员不能为空！");
			return 
		}
		if($("#orgEntryForm").find("#radio1").attr("checked") == true){
			if($("#orgEntryForm").find("#staffType").val() == ""){
				alert("员工类别不能为空！");
				return
			}
			if($("#orgEntryForm").find("#payScaleID").val() == ""){
				alert("职务级别不能为空！");
				return
			}
			if($("#startDate").val()==""){
				alert("合同起日期不能为空！");
				return;
			}
			if($("#ednDate").val()==""){
				alert("合同止日期不能为空！");
				return;
			}
		}
		var url = basePath +"ea/departmentpost/sajax_ea_orgPostEntry.jspa?date="+new Date()+"&departmentPost.organizationID="+ogID;
        var formData = $("#orgEntryForm").serialize();
		formData = decodeURIComponent(formData,true);
		$.ajax({
			  url : encodeURI(url + "&" + formData),
              type: "get",
              async: false,
              dataType: "json",
              success: function cbf(data){
       		var member = eval("(" + data + ")");
       		var vals = member.vals;
       			alert(vals);
     		},error: function cbf(data){
                alert("数据获取失败！");
            }
        });			
		$("#jqModelEntry").jqmHide();
		document.orgEntryForm.reset();
		re_load();
	});  
	
	$("#toReset").click(function() { //重置
		document.orgEntryForm.reset();
		$("tr.div1").hide();
	}); 
});

$(function(){
	var url = basePath +"ea/departmentpost/sajax_ea_getOrganizationPost.jspa?departmentPost.organizationID="+ogID;
	$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var persons = member.departmentPostlist;
				var str = "";
				if(persons.length == 0){
					str = "<option value=''>此部门无岗位  </option>";
				}else{
					str = "<option value=''>请选择岗位</option>";
					for(var i=0;i<persons.length;i++){
						var obj = persons[i].postName;
						var objID = persons[i].depPostID;
						str += "<option value='"+objID+"'>"+obj+"</option>";
					}
				}
				$("#postID").html(str);
			}
	});
	
	 //员工类别
   	var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date();
    $.ajax({
                url: url,
                type: "get",
                async: false,
                dataType: "json",
                success: function cbf(data){
	           		var member = eval("(" + data + ")");
	           		var nologin = member.nologin;
					if(nologin){
	                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
	                }
	           		var staffTypeList = member.staffTypeList;
	           		if (null != staffTypeList) {
	           			var htmlStr="<option value=''>请选择</option>";
		               	for (var i = 0; i < staffTypeList.length; i++) {
		               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>";
		             	}
		             	$("#staffType").html(htmlStr);
      				}
       			},error: function cbf(data){
                      		alert("数据获取失败！");
                }
    });
	
	//获取工资级别
   	var url1 = basePath +"ea/soincumbent/sajax_n_ea_getStaffListForPost.jspa?date="+new Date();
    $.ajax({
            url: url1,
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data){
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if(nologin){
				document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			}
       		var paylist = member.paylist;
			if (null != paylist) {
				for (var j = 0; j < paylist.length; j++) {
					$op = $("<option/>");
					$op.val(paylist[j].payScaleID).text(paylist[j].position+"/"+paylist[j].scale);
					$(".PayScale").append($op);
	     		}
			}
   			},error: function cbf(data){
                  alert("数据获取失败！");
            }
    });
    
    //读取芯片号
	$("#readchipid").click(function() {
		$("#orgEntryForm #loadcab")
				.attr(
						"src",
						basePath
								+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
								+ Math.random());
	});  
});

function re_load() {
	document.location.href = basePath
			+ "ea/cosincumbent/ea_getStaffList.jspa?ogID="+ogID+"&ogName="
			+ ogName +"&pageNumber=" + ppageNumber + "&search=" 
			+ psearch + "&searchValue=searchValue";
}