$(document).ready(function() {
	$("input[type='button']").css({
				color : "#000",
				background : "#ccc"
			});
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
 var title="短消息列表";
 var nic ="";
 if(orgDetail=="office"){
 	nic = "办公室";
 	
 }
  if(orgDetail=="market"){
 	nic = "营销";
 	
 }
 if(type=="current"){
 	title="短消息列表公司"+nic+"汇总";''
 	flexh(title);
 }else if(type=="group"){
 	title="短消息列表集团"+nic+"汇总";
 	flexh(title);
 }else{
 	flex(title);
 }
 function flex(title){
 		$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '清空',
					bclass : 'empty',
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
					name : '查看',
					bclass : 'edit',
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
					name : '发短信',
					bclass : 'sms',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转发',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '失败批量重发',
					bclass : 'sms',
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
 }

 
 function flexh(title){
 		$('.JQueryflexme').flexigrid({
				height : 240,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
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
			case '清空' :
				if (confirm("是否清空？")) {
				$f = $('#cstaffForm');
//					$f.find('#id').val(telMessageID);
//				   alert(telMessageID);
//					return;
					var url = basePath
							+ "ea/telmessage/ea_delTelMessageAll.jspa";
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			case '删除' :
				if (telMessageID == "") {
					alert('请选择短消息');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#id').val(telMessageID);
					var url = basePath
							+ "ea/telmessage/ea_delTelMessage.jspa?telMessage.telMessageID="
							+ telMessageID + "&pageNumber=" + pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + telMessageID).remove();
					telMessageID = "";
					token = 11;
				}
				break;
			case '查看' :
				if (telMessageID == "") {
					alert('请选择短消息');
					return
				}
				document.cstaffForm.reset();
				lookMSG(telMessageID);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				getCmember();
				getCCompany();
				break;
			case '发短信' :
				parent.document.mForm.reset();
				parent.$('#jqModelSend').jqmShow();
    			parent.getQms();
    			parent.$(".SEND").addClass("disable").attr({disabled: true}); 
				
				break;
			case '转发' :
				if (telMessageID == "") {
					alert('请选择要转发的短消息');
					return;
				}
//				var stel = $("tr#" + telMessageID).find("#telNum").text();
//				if (stel == "") {
//					alert("请选择有手机号码的记录");
//					return;
//				}
//				parent.$('#cData input').remove();
//				parent.$('#cumID').val("");
                  
//				parent.$("#cumID").val(parent.$("#cumID").val() + stel + ";");
                
                parent.document.mForm.reset();
                parent.$("#content").val($("tr#" + telMessageID).find("#content").text());
				//parent.$(".SEND").removeClass("disable").removeAttr('disabled');
                parent.$(".SEND").addClass("disable").attr({disabled: true}); 
				parent.$('#jqModelSend').jqmShow();
				parent.getQms();
				break;
				
		   	case '失败批量重发' :
		   	
				parent.document.remForm.reset();
				parent.$("#errorcause").text("");
				parent.$('#jqModelReSends').jqmShow();
				parent.getFailLog();
				break;
			case '导出' :
				url = basePath
						+ "ea/telmessage/ea_showTelMessageExcel.jspa?search="
						+ search+"&type="+type+"&orgDetail="+orgDetail;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/telmessage/ea_getTelMessageList.jspa?search="
						+ search+"&type="+type+"&orgDetail="+orgDetail;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				telMessageID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/telmessage/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
	
	//
	

});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/telmessage/ea_getTelMessageList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&type="+type+"&orgDetail="+orgDetail;
	}
}
function lookMSG(msgID) {
	document.cstaffForm.reset();
	$temp = $("tr#" + msgID);
	$temp = $("tr#" + msgID);
	var msgTime = $temp.find("span#sendDate").text();
	var content = $temp.find("span#content").text();
	$("input#sendDate", "form#cstaffForm").attr("value", msgTime);
	$("textarea#content", "form#cstaffForm").text(content);
	$("#jqModel").jqmShow();
}
//没用
function repeadSend(telMessageID) {
$("#postRepeatForm #telMessageIDs").val(telMessageID);
//alert($("#postRepeatForm #telMessageIDs").val());
$("#postRepeatForm").attr(
				"action",
				basePath + "ea/telmessage/t_ea_repeatSend.jspa?pageNumber="
						+ pNumber);
		document.postRepeatForm.submit.click();
//	document.location.href = basePath
//			+ "ea/telmessage/t_ea_repeatSend.jspa?telMessageID=" + telMessageID+"&type=repeat";

}


//获取往来个人关系

function getCmember(){

	var url = basePath+"ea/telmessage/sajax_ea_getCodeCmRalation.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	success:function(data){
	   var member = eval("("+data+")");
	   var relationlist = member.relationlist;
       var result="<option value=\"\">全部</option><option value=\"在职员工\">在职员工</option>";
	   for (var i = 0; i < relationlist.length; i++) {
	   	var codeValue = relationlist[i];
	    result+="<option value=\""+codeValue+"\">"+codeValue+"</option>";		
	
	  }
	  $("#relations").html(result);
					
	},
	error:function(data){
		
	}
	
	});
	
}


function getCCompany(){

	var url = basePath+"ea/telmessage/sajax_ea_getCCompanyRalation.jspa";
	$.ajax({
	url:url,
	type:"get",
	async:false,
	success:function(data){
	   var member = eval("("+data+")");
	   var relationlist = member.relationlist;
       var result="<option value=\"\">全部</option>";
	   for (var i = 0; i < relationlist.length; i++) {
	   	var codeValue = relationlist[i];
	    result+="<option value=\""+codeValue+"\">"+codeValue+"</option>";		
	
	  }
	  $("#connection").html(result);
					
	},
	error:function(data){
		
	}
	
	});
	
}