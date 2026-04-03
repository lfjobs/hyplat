	//甲方乙方处理
	var contype = "";
	var contype2= "";
$(function(){
	
		$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

});




// 获得人力资源或者在职员工等的弹出框的操作
function importGY2(url,type) { // 打开页面
	$("#daoRu").attr("src", basePath + url);
	$("#socialJqm2").jqmShow();
	$("#drafttbl").css("margin-bottom","150px");
	$("#socialJqm2 #type2").val(type);
}

function DaoruConfirm2() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择");
		return;
	}
	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	var type = $("#socialJqm2 #type2").val();
	var companyName = "";	
	var staffIdentityCard;
	
	var projectName;
	
	if(type=="partyAD"||type=="partyBD"){ 
        companyName= window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#companyName").text();
			
      }
     
    if(type=="partyA"||type=="partyB"){ 
        staffIdentityCard= window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffIdentityCard").text();
			
      }
      
      if(type=="project"){ 
        projectName= window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#goodsName").text();
		
			
      } 
      
	
	if(type=="partyA"){
		$("#drafttbl #partyAstaffnames").val(staffName);
		$("#drafttbl #partyAstaff").val(childopertionID);
		$("#drafttbl #staffIdentityCardA").val(staffIdentityCard);
		
	}else if(type=="partyB"){
		$("#drafttbl #partyBstaffnames").val(staffName);
		$("#drafttbl #partyBstaff").val(childopertionID);
		$("#drafttbl #staffIdentityCardB").val(staffIdentityCard);
		
	}else if(type=="partyAD"){
		$("#drafttbl #partyAName").val(companyName);
		$("#drafttbln #partyA").val(childopertionID);	
	    
	}else  if(type=="partyBD"){
		$("#drafttbl #partyBName").val(companyName);
		$("#drafttbl #partyB").val(childopertionID);
		
	}else  if(type=="project"){
		$("#drafttbl #projectName").val(projectName);
		$("#drafttbl #journalNum").val(childopertionID);
		
	}
	$("#daoRu").attr("src", "");
	$("#drafttbl").css("margin-bottom","10px");
	$("#socialJqm2").jqmHide();
}
function cancelJqm2() {
	$("#socialJqm2").jqmHide();
	$("#drafttbl").css("margin-bottom","10px");
}



function clears(f){
	
	if(f=="partyAD"){
		$("#drafttbl #partyAName").val("");
		$("#drafttbl #partyA").val("");	
	}
	if(f=="partyA"){
		$("#drafttbl #partyAstaffnames").val("");
		$("#drafttbl #partyAstaff").val("");
		$("#drafttbl #staffIdentityCardA").val("");
	}
	if(f=="partyBD"){
		$("#drafttbl #partyBName").val("");
		$("#drafttbl #partyB").val("");
	}
	if(f=="partyB"){
		$("#drafttbl #partyBstaffnames").val("");
		$("#drafttbl #partyBstaff").val("");
		$("#drafttbl #staffIdentityCardB").val("");
	}
	
	if(f=="project"){
		$("#drafttbl #projectName").val("");
		$("#drafttbl #journalNum").val("");
	}
	
	
}