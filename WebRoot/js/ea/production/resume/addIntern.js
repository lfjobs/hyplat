var url=null; 
$(".alert_div2").css("height",$(window).height()*1+45+'px');
        function doFn(){
            var flag = true;//是否全部输入  默认true
            //跳转教育背景页面 
          
            $(".ipt").each(function (){
                if($(this).val()==""){//有空输入，将flag置为false
                    flag = false;//
                }
            });
            if(flag){
                $(".alert_div2").css("display","block");
              
            }else{
                $(".alert_div").css("display","block");
                setTimeout(function(){$("#alert_d").hide();},2000);//2秒后执行该方法
            }

        }
 
     $(document).ready(function(e) {
    	 $("#returnClick").click(function() {history.go(-1);});
     
	    	 //跳转工作描述页面
	    	 $("#jobDescription").click(function() {
				 Share("工作描述");
	         });
	      
	    	 	//跳转行业搜索页面
		       $("#industrySearch1").click(function() {
					 Share("行业搜索");
		      });
		      //跳转岗位搜索页面
		       $("#jobSearch").click(function() {
		    	    Share("岗位搜索");
		      });
		       //保存
		     $("#quedingp").click(function() {
		    	Share("保存");
		      });
        	
      });
     function Share(obj){
    	var name = $("#scname").val();//学校名称
     	var admissionTime = $("#beginTime").val();//开始时间
     	var graduationTime = $("#endTime").val();//结束时间
     	var position = $("#position").text();//所在行业
     	var postName = $("#postName").text().replace(/\|/g,'%7C');//岗位名称
     	var staffid = $("#staffid").val();
     	var duties = $("#duties").val();
     	var resumeID=$("#resumeID").val();
    	 if(obj=="保存"){
		    		 
		    url = basePath + "ea/resumes/ea_addResume.jspa?resume.staffID="+staffid+"&resume.companyName="+name+"&resume.startTime="+admissionTime+"&resume.endTime="+graduationTime+"&resume.position="+position+"&resume.postName="+postName+"&staffid="+staffid+"&resume.duties="+duties+"&type=save&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
		    console.log(url);
        	$("#form").attr("target", "hidden").attr("action",url);
        
			document.form.submit.click();
			token = 2;
    	 }else if(obj=="岗位搜索"){
    		 url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=工作经验&staffid="
	    	    +staffid+"&name="+name
		     	+"&admissionTime="+admissionTime+"&graduationTime="
		     	+graduationTime+"&position="+position+"&postName="
		     	+postName+"&type="+type+"&duties="+duties+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	    	    document.location.href = url;
    	 }else if(obj=="行业搜索"){
    		 url =basePath+"ea/bidrecruit/ea_getIndustryList.jspa?type=工作经验&staffid="
	    	    +staffid+"&name="+name
		     	+"&admissionTime="+admissionTime+"&graduationTime="
		     	+graduationTime+"&position="+position+"&postName="
		     	+postName+"&type="+type+"&duties="+duties+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
		       
				document.location.href = url;
    		 
    	 }else if(obj=="工作描述"){
    			var type=$("#type").val();
    			
    			url = basePath + "/page/ea/main/production/resume/jobDescription.jsp?staffid="
    	       	+staffid+"&name="+name
    	     	+"&admissionTime="+admissionTime+"&graduationTime="
    	     	+graduationTime+"&position="+position+"&postName="
    	     	+postName+"&type="+type+"&duties="+duties+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
    			document.location.href = url;
    	 }
    	 
    	 return url;
     }
      function re_load(){
    	  var staffid = $("#staffid").val();
    	  var resumeID=$("#resumeID").val();
    	  document.location.href=basePath+"ea/resumes/ea_queryResume.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
    	} 