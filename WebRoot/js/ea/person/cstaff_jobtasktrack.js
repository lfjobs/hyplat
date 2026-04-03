$(document).ready(function() {
	
	//隐藏显示
	
	if(summarytype=="com"){
		$("td.com").hide();
		$("td.org").show();
	}else if(summarytype=="group"){
		$("td.com").show();
		$("td.org").show();
	}else{
		$("td.com").hide();
		$("td.org").hide();
	}
	
	
	
	$("tr.datagrid-row").each(function(){
		var time = $(this).find("span#time").text();
		var facttime = $(this).find("span#facttime").text();
		var factrateday = $(this).find("span#factrateday").text();
		if($(this).find("span#time").text()!=""){
			var array1 = time.split(",");
			var array2 = facttime.split(",");
		//	var array3 = factrateday.split(",");
			for ( var i = 0; i < array1.length; i++) {
				$(this).find("div#time"+$.trim(array1[i])).css("background","green");
				//$(this).find("div#time"+$.trim(array1[i])).text($.trim(array1[i]));
			}
			for ( var i = 0; i < array2.length; i++) {
				if((","+time+",").indexOf(","+array2[i]+",")!=-1){
					$(this).find("#time"+$.trim(array2[i])).css("background","orange");
				}else{
				  $(this).find("#time"+$.trim(array2[i])).css("background","red");
				}
				//$(this).find("#time"+$.trim(array2[i])).text($.trim(array2[i]));
			}

		}
		
		
		
	});
	
	
	
	
	//查询
	$("#search").click(function(){
		$("#searchForm").attr("action",pbasePath+"ea/jobtask/ea_getTaskTrack.jspa");

		document.searchForm.submit.click();
		
	});

	//
	
	$("#companyid").change(function(){
		var url=pbasePath+"/ea/documentcommon/sajax_ea_getAllOrganizations.jspa";
		$.ajax({
			 url:url,
			 type:"get",
			 async:false,
			 dataType:"json",
			 data:{
				 companyID:$(this).val()
			 },
			 success:function(data){
				var member = eval("("+data+")");
				var orgaizationlist  = member.orgaizationlist;
				optionstr="<option value=''>全部</option>";
				for ( var j = 0; j < orgaizationlist.length; j++) {
					optionstr+="<option value='"+orgaizationlist[j].organizationID+"'>"+orgaizationlist[j].organizationName+"</option>";
				}
				
			    $("#orgid").html(optionstr);
				
				
				
			 },
			 error:function(data){
				 
			 }
			 
		 });
		
		
	});
	
	
	$("#orgid").change(function(){
		
		var url=pbasePath+"/ea/jobtask/sajax_ea_getProjectByOrg.jspa";
		$.ajax({
			 url:url,
			 type:"get",
			 async:false,
			 dataType:"json",
			 data:{
				 "jobTask.organizationID":$(this).val()
			 },
			 success:function(data){
				var member = eval("("+data+")");
				var projectlist  = member.projectlist;
				optionstr="<option value=''>全部</option>";
				for ( var j = 0; j < projectlist.length; j++) {
					optionstr+="<option value='"+projectlist[j].csbid+"'>"+projectlist[j].projectname+"</option>";
				}
				
			    $("#projectID").html(optionstr);
				
				
				
			 },
			 error:function(data){
				 
			 }
			 
		 });
		
		
	});
	
	

	
	
	
	//保存
	$("#fullsave").click(function() {
		$("#kelong1").find(':input').each(function() {
			$(this).attr("name","jobTaskmap[1]." + this.name);
		});
	 $("#projectName").val($("#projectID option:selected").text());
		

	$("#taskTargetForm").attr(
			"action",
			pbasePath + "ea/jobtask/ea_saveTaskTarget.jspa?pageNumber="
					+ ppageNumber);
	document.taskTargetForm.submit.click();
	token = 2;
	
	
		
	});
	

});
function re_load() {
	document.location.href = pbasePath
			+ "ea/jobtask/ea_getJobTaskList_a.jspa?staffID=" + pstaffID
			+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value");
}