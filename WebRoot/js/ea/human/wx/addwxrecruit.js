$(document).ready(function() {
	if($("#recNature").val() != ""){
		$("#btnProfession").css("border","1px solid red");
	}
	if($("#recNaturei").val() != ""){
		$("#btnPost").css("border","1px solid red");
	}
	if($("#recNatureii").val() != ""){
		$("#btnPractice").css("border","1px solid red");
	}
	
	if(cid == ""){
		addtab();
	}else{
		window.opener.re_load();
	}
});
function addtab(){
	$("#tbTitle").after($("#tbTitle").clone(true).attr("id","tbTitle" + select).css("display",""));
	$("#tbTitle" + select).find(".addt").each(function(){
		$(this).addClass("fmap");
	});
	$("#tbTitle" + select).find(".addput3").addClass("put3");
	select++;
}
function changePro(e,t){
	if(t == "btnProfession"){
		if($("#recNature").val() == ""){
			$("#recNature").attr("value",$(e).val());
			$(e).css("border","1px solid red");
		}else{
			$("#recNature").attr("value","");
			$(e).css("border","none");
		}
	}else if(t == "btnPost"){
		if($("#recNaturei").val() == ""){
			$("#recNaturei").attr("value",$(e).val());
			$(e).css("border","1px solid red");
		}else{
			$("#recNaturei").attr("value","");
			$(e).css("border","none");
		}
	}else if(t == "btnPractice"){
		if($("#recNatureii").val() == ""){
			$("#recNatureii").attr("value",$(e).val());
			$(e).css("border","1px solid red");
		}else{
			$("#recNatureii").attr("value","");
			$(e).css("border","none");
		}
	}
}

function closeM(){
	window.opener.re_load();
	window.opener=null;
	window.open('','_self');
	window.close();
	
}
function saveM(){
	$("#addrecruitForm .put3").trigger("blur");
	if ($("#addrecruitForm .error").length) {
		return;
	}
	var num = 0 ;
	var numl = 0;
	$("#addrecruitForm").find(".fmap").each(function(){
		
		$(this).attr("name","wxdmap[" + num + "]." + this.name);
		
		if(numl == 6){
			numl = 0;
			num++;
		}else{
			numl++;
		}
		
	});
	$("#addrecruitForm").attr("action",basePath+"ea/wxrecruit/ea_saveItem.jspa?");
	$("#addrecruitForm").submit();
}
document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : ""); //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        return true;
    }
};
