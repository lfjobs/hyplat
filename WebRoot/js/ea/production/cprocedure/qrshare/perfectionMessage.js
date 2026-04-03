$(document).ready(function() {
	if(auditSkip=="00"||auditSkip=="03"){
		$(".auditor").hide();
		$(".opinion_text").hide();
		$(".auditor_btn").hide();
	}else if((auditSkip=="01")){
		$(".next_btn").hide();
		$(".auth_btn").hide();
		if(state=='01'){
			$(".auditor_btn").show();
			$(".opinion_text").show();
		}
	}
})

function sumitImageFile(){
	
	var logo = $("#logo_img").attr("src");
	var lic = $("#lic_img").attr("src");
	
	if(logo==null || $.trim(logo)=="" || logo==basePath+"images/WFJClient/PersonalJoining/logo@2x.png"){
		return prompt("请上传公司logo");
	}
	if(lic==null || $.trim(lic)=="" || lic==basePath+"images/WFJClient/PersonalJoining/auditLogo.png"){
		return prompt("请上传企业资质");
	}
	
    var form=document.forms[0];  
      
    var formData = new FormData(form);   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数  
      
    
    formData.append("contactCompany.logoPath",logo);  
    formData.append("certificate.certificateLocation",lic); 
    formData.append("certificate.certificateType","营业执照"); 
    formData.append("certificate.certificateName","营业执照"); 
    formData.append("caccount.staffID",staffID);
    formData.append("caccount.companyID",companyID);
      
    //ajax 提交form  
    $.ajax({  
        url : form.action,  
        type : "POST",  
        data : formData,  
        dataType:"json",  
        async : false,
        processData : false,         // 告诉jQuery不要去处理发送的数据  
        contentType : false,        // 告诉jQuery不要去设置Content-Type请求头  
          
        success:function(data){
        	var judge = eval("(" + data + ")");
        	if(judge.boolean){
        		prompt("上传成功");
        		document.location.href = basePath+"ea/qrshare/ea_queryState.jspa?auditSkip="+auditSkip;
        	}else{
        		prompt("上传失败请修改重新上传");
        	}
        },  
    });  
}  



function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}


//失去焦点触发事件
function lose(obj){
	var p;
	var val = obj.val();
	var auth = obj.parent().prev(".auth_L").html();
	var regular;
	if(auth=="公司名称"){
		regular = /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]*$/;
			if(!regular.test(val)){
				prompt("公司名称格式不正确");
				return p = "01" ;
			}
	}else if(auth=="法人代表"){
		regular = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
			if(!regular.test(val)){
				prompt("法人代表格式不正确");
				return p = "01" ;
			}
	}else if(auth=="身份证号"){
		regular = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
			if(!regular.test(val)){
				prompt("身份证号格式不正确");
				return p = "01" ;
			}
	}else if(auth=="公司地址"){
		regular = /^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]*$/;
			if(!regular.test(val)){
				prompt("公司地址格式不正确");
				return p = "01" ;
			}
	}else if(auth=="联系人"){
		regular = /^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20})$/;
			if(!regular.test(val)){
				prompt("联系人格式不正确");
				return p = "01" ;
			}
	}else if(auth=="联系电话"){
		regular = /(^13\d{9}$)|(^14)[5,7]\d{8}$|(^15[0,1,2,3,5,6,7,8,9]\d{8}$)|(^17)[6,7,8]\d{8}$|(^18\d{9}$)/g;
			if(!regular.test(val)){
				prompt("联系电话格式不正确");
				return p = "01" ;
			}
	}
}

function submitAudit(t) {
	if(t=="04"){
		var comment = $(".opinion_textarea").val();
		document.location.href = basePath+"ea/workmessage/ea_findCompanyPeople.jspa?auid="+auid+"&comment="+comment;
		return;
	}
	
	
	var formData = new FormData();   //这里连带form里的其他参数也一起提交了,如果不需要提交其他参数可以直接FormData无参数的构造函数  
    formData.append("caccount.staffID",staffID);
    formData.append("caccount.companyID",companyID);
    formData.append("auditRecord.AuditComment",$(".opinion_textarea").val());
    formData.append("auditRecord.state",t);
    formData.append("auditRecord.auid",auid);
    formData.append("contactCompany.ccompanyID",ccompanyID);
	$.ajax({  
        url : basePath + "ea/qrshare/sajax_ea_submitAudit.jspa",  
        type : "POST",  
        data : formData,  
        dataType:"json",  
        async : false,
        processData : false,         // 告诉jQuery不要去处理发送的数据  
        contentType : false,        // 告诉jQuery不要去设置Content-Type请求头  
          
        success:function(data){
        	var judge = eval("(" + data + ")");
        	if(judge.boolean){
        		prompt("提交成功,请耐心等待审核成功");
        		document.location.href = basePath + "ea/workmessage/ea_taskMessage.jspa";
        	}else{
        		prompt("提交失败,请修改信息重新提交");
        	}
        },  
    }); 
}
