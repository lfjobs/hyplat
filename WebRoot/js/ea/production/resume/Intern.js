$(document).ready(function(){
		      	//返回queryPersion
		        $("#returnClick").click(function() {
		        	var staffid = $("#staffid").val();
		        	var resumeID=$("#resumeID").val();
		    		document.location.href=basePath+"ea/resumes/ea_queryPersion.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
		        
		        });
      			//跳转添加实习经验页面
		        $("#addInternshipExperience").click(function() {
			       var staffid = $("#staffid").val();
			      
			       var url = basePath + "/page/ea/main/production/resume/addInternshipExperience.jsp?staffid="+staffid+"&type=&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
				   document.location.href = url;
		        });
		        
});

		//修改的方法
		function xiugai(obj){
		  	var key = $(obj).find("#key").val();
		  	var staffid = $("#staffid").val();
		  	var type = $(obj).find("#types").val();
		  	var resumeID=$("#resumeID").val();
			var url = basePath + "ea/resumes/ea_updateResume.jspa?key="+key+"&staffid="+staffid+"&type="+type+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
			document.location.href = url; 
		}
		//删除的方法
		function dianji(obj){
		    var key = $(obj).parents(".edu-bg_mil").find("#key").val();
		    $(".edu-bg_mil").removeAttr("onclick");
		    var flag = true;//是否全部输入  默认true
		    if(flag){
		        $(".alert_div2").css("display","block");
		    }else{
		        $(".alert_div").css("display","block");
		        setTimeout(function(){
		        $("#alert_d").hide();}
		        ,2000);//2秒后执行该方法
		    }
		    $("#qued").click(function() {
		    	var resumeID=$("#resumeID").val();
			var url = basePath+"ea/resumes/sajax_ea_addResume.jspa?key="+key+"&staffid="+staffid+"&type=ajax"+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;   		
		        $.ajax({
				url : encodeURI(url),
				type : "get",
				success: function(){
					var url = basePath+"ea/resumes/ea_queryResume.jspa?resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					document.location.href = url; 	
				}	
				});
			$(".alert_div2").css("display","none");
			
		});
		}