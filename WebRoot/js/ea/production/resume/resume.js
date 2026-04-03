$(document).ready(function(){
	if(resumeID==null||resumeID==""){
		resumeID=resumeIDp;
	}
	/* $("#returnClick").click(function() {history.go(-1)});*/
			 //返回
			 $("#returnClick").click(function() { 
			   	var staffid = $("#staffid").val();
			   	var url = basePath + "ea/resumes/ea_resumeManagement.jspa?staffid="+staffid+"&type=query&resumeID="
			   	+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				
			   	document.location.href = url;
		
			 });
	
			 //跳转编辑简历
			 $("#min").click(function() { 
			   	var staffid = $("#staffid").val();
			   	var url = basePath + "ea/resumes/ea_savePersion.jspa?staffid="+staffid+"&type=query&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					document.location.href = url;
		 
			 });
			 //跳转编辑简历
			 $("#edit").click(function() {
		    	var staffid = $("#staffid").val();
		    	var url = basePath + "ea/resumes/ea_getEditResume.jspa?staffid="+staffid+"&type=&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
		  
		    });
	        //跳转隐私设置
	        $("#stealthSetting").click(function() {
	        	var staffid = $("#staffid").val();
	        	var url = basePath + "ea/resumes/ea_getPrivacys.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
				document.location.href = url;
	      
	        });
	       //跳转简历详情
	        $("#resumeDetails").click(function() {
	          	var url = basePath + "ea/bidrecruit/ea_showResumedetail.jspa?resumeID="+resumeID+"&type="+"&sccId="+sccId+"&jitype="+jitype;
			  	document.location.href = url;
	      
	      	});
	            
	            //跳转求职意向
	       	$("#JobSearchIntention").click(function() {
	       	var staffid = $("#staffid").val();
	       	
		       	var url = basePath + "/ea/resumes/ea_querySearch.jspa?resumeID="+resumeID+"&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
	      
	      	});
	              //跳转教育背景
	       	$("#educationalBackg").click(function() {
		       var staffid = $("#staffid").val();
		       
		       var url = basePath+ "ea/resumes/ea_queryEdu.jspa?resumeID="+resumeID+"&type=&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
	      
	      	});
	      		//实习经验
		       $("#internshipExperience").click(function() {
			       var staffid = $("#staffid").val();
			    
			        var url = basePath+ "ea/resumes/ea_queryResume.jspa?resumeID="+resumeID+"&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					document.location.href = url;
		      
		      });
		      //自我评价
		      $("#selfEvaluation").click(function() {
		       var staffid = $("#staffid").val();
		       var url = basePath + "ea/resumes/ea_savePersion.jspa?resumeID="+resumeID+"&type=queryOne&staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			   document.location.href = url;
		      
		      });
});
