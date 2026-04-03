$(function(){
	if(verification=="验证码不正确"){
		alert(verification);
	}
	if(subjectId2!=""&&subjectId2!=null){
		$(".li2").attr("style","");
		bl=true;
		$("#srv").remove();
	}
})
    
function sr(){
		$(".li2").attr("style","");
		bl=true;
		$("#srv").remove();
}
function save(){
	 var z1=/[\u4e00-\u9fa5]/;
		 var z2=/^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
		 var z5=/^[0-9a-zA-Z]+(?:[\.\!\#\$\%\^\&\*\'\+\-\/\`\_\{\|\}\~]{0,1}[a-zA-Z0-9]+)*@[a-zA-Z0-9\-]+\.[0-9a-zA-Z\-]+$/;
		 var z6=/((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/;
		 var i=0;
			var bb=false;
	        $("form").find(":input").trigger("blur");
	        $(".validate").trigger("blur");
	        $(".error").each(function(){   	
	        	var va=$(this).val();
	        	if(i==1||i==4){
	        		if(!z1.test(va)){
	        			$(this).parent().find(".font").attr("color","red").text("X");
	        			bb=true;
	        			return false;
	        		}else{
	        			$(this).parent().find(".font").attr("color","green").text("√");
	        		}
	        	}else if(i==2){
	        		if(!z2.test(va)){
	        			$(this).parent().find(".font").attr("color","red").text("X");
	        			bb=true;
	        			return false;
	        		}else{
	        			$(this).parent().find(".font").attr("color","green").text("√");
	        		}
	        	}else if(i==5){
	        		if(!z5.test(va)){
	        			$(this).parent().find(".font").attr("color","red").text("X");
	        			bb=true;
	        			return false;
	        		}else{
	        			$(this).parent().find(".font").attr("color","green").text("√");
	        		}
	        	}else if(i==6){
	        		if(!z6.test(va)){
	        			$(this).parent().find(".font").attr("color","red").text("X");
	        			bb=true;
	        			return false;
	        		}else{
	        			$(this).parent().find(".font").attr("color","green").text("√");
	        		}
	        	}
	        	
	        	i++
	        	if(va==""||va==null){
	        		alert("带*的为必填项"); 
	        		bb=true;
	        		return false;
	        	}
	        });
	        if(bl){
	          	$(".crr").each(function(){
	        	var va=$(this).val();
	        	if(va==""||va==null){
	        		alert("带*的为必填项");  
	        		bb=true;     		
	        		return false;
	        	}
	       		});
	   	    	codeValue=$("#sel option:selected").text();
	        }
	         if(bb){
	         	return;
	         }   	
	    $("#DetailForm").attr("action","ea/ccompany/t_ea_saveCompanyDetail.jspa?codeValue="+codeValue);
	       document.DetailForm.submit.click();
	       alert("保存成功！");
}
function subtrs(pageNumber,obj){
	if(pageNumber>(Math.ceil(search2/14)))
		pageNumber=(Math.ceil(search2/14));
	if(pageNumber<1)
		pageNumber=1;
	$(".subrid").attr("id",obj);
	$("#kemutoo").find(".subtr").remove();
	pagenum=pageNumber;
	var subjecturl = basePath
		+ "ea/csbjects/sajax_ea_getAjaxListCsubejstsByPID.jspa?subjectsID=";
	$.ajax({
		url : encodeURI(subjecturl + obj+"&pageForm.pageNumber="+pageNumber+"&pageNumber=14&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var subjects = member.pageForm.list;
			search2=member.search;
			for(var i=0;i<subjects.length;i++){
				var lb="其他";
				if(subjects[i].subjectsCategory=="A")
					lb="银行";
				if(subjects[i].subjectsCategory=="B")
					lb="现金";
				if(subjects[i].subjectsCategory=="C")
					lb="固定资产";
				var tr="<tr align='left' class='subtr' style=\"background-color:#E0EEEE\">" +
				   "<td>"+subjects[i].subjectsNumbers+"</td>" +
				   "<td><span class='sprs' title="+subjects[i].subjectsName+">"+subjects[i].subjectsName+"</span></td>"+
				   "<td>"+lb+"<input type='hidden' value="+subjects[i].subjectsID+"></td>" +
				   "<td>"+(subjects[i].subjectsDirection=='D'?'借':'贷')+"</td>" +
				   "<td>"+(subjects[i].subjectsAccounts=='Y'?'主账户':'虚账户')+"</td></tr>"; 
				$("#kemutoo").append(tr);
			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});		
};