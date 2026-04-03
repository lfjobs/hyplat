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
     
    	 
    	 var typetata = $("#typedatas").val();
    	 
    	 if(typetata=="修改"){
    		 $("#scname").val($("#name").val());//学校名称
 	    	 $("#beginTime").val($("#admissionTime").val());//开始时间
 	    	 $("#endTime").val($("#graduationTime").val());//结束时间
 	    	 $("#position").text($("#positions").val());//所在行业
 	    	 $("#postName").text($("#postNames").val());//岗位名称
 	    	 $("#recordKeya").val(reckey);
 	    	$("#recordIDa").val(recod);
 	    	$("#resumeIDa").val(resuid);
 	    	
    	 }
    	 //跳转工作描述页面
    	 $("#jobDescription").click(function() {
	       //$("#staffid").val($("#staff").val());
	       	var name = $("#scname").val();//学校名称
	    	var admissionTime = $("#beginTime").val();//开始时间
	    	var graduationTime = $("#endTime").val();//结束时间
	    	var position = $("#position").text();//所在行业
	    	var postName = $("#postName").text().replace(/\|/g,'%7C');//岗位名称
	    	var staffid = $("#staffid").val();
	    	var typedata = $("#typedata").val();
	    	var duties = $("#duties").val();
	    	var dutiess = $("#dutiess").val();
	    	
	    	var recordKeys=$("#recordKeys").val();
	    	var recordIDs =$("#recordIDs").val();
	    	var resumeIDs = $("#resumeIDs").val();
	    	var recordKeya=$("#recordKeya").val();
	    	var recordIDa =$("#recordIDa").val();
	    	var resumeIDa = $("#resumeIDa").val();
	    	//var resumeID = $("#resumeID").val();
	    	
	    	var url=null;
	    	if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
	    		if(duties==""||duties==null||duties==undefined){
	    			duties=dutiess;
				}
	    		
	    		url = basePath + "/page/ea/main/production/resume/jobDescription.jsp?staffid="+staffid+"&name="+name
		    		+"&admissionTime="+admissionTime+"&graduationTime="
		    		+graduationTime+"&position="+position
		    		+"&postName="+postName+"&typedata=修改"
		    		+"&duties="+duties
		    		+"&recordKeys="+recordKeys
		    		+"&recordIDs="+recordIDs
			     	+"&resumeIDs="+resumeIDs
			     	+"&resumeID="+resumeID
			     	+"&sccId="+sccId
			     	+"&jitype="+jitype;

	    	}else{
	    		if(duties==""||duties==null||duties==undefined){
	    			duties=dutiess;
				}
	    		
	    		 url = basePath + "/page/ea/main/production/resume/jobDescription.jsp?staffid="+staffid+"&name="+name
		    		+"&admissionTime="+admissionTime+"&graduationTime="
		    		+graduationTime+"&position="+position
		    		+"&postName="+postName+"&typedata=修改"
		    		+"&duties="+duties
		    		+"&recordKeya="+recordKeya
		    		+"&recordIDa="+recordIDa
			     	+"&resumeIDa="+resumeIDa
			     	+"&resumeID="+resumeID
			     	+"&sccId="+sccId+"&jitype="+jitype;
	    	}
	    	
	       	
			document.location.href = url;
      
         });
      
      //跳转行业搜索页面
	       $("#industrySearch1").click(function() {
	    	   var name = $("#scname").val();//学校名称
		    	var admissionTime = $("#beginTime").val();//开始时间
		    	var graduationTime = $("#endTime").val();//结束时间
		    	var position = $("#position").text();//所在行业
		    	var postName = $("#postName").text().replace(/\|/g,'%7C');//岗位名称
		    	var staffid = $("#staffid").val();
		    	var duties = $("#duties").val();
		    	var dutiess = $("#dutiess").val();
		    	var recordKeys=$("#recordKeys").val();
		    	var recordIDs =$("#recordIDs").val();
		    	var resumeIDs = $("#resumeIDs").val();
		    	var recordKeya=$("#recordKeya").val();
		    	var recordIDa =$("#recordIDa").val();
		    	var resumeIDa = $("#resumeIDa").val();
		    	var resumeID = $("#resumeID").val();
		    	var url=null;
		    	if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
		    		if(duties==""||duties==null||duties==undefined){
		    			duties=dutiess;
					}
		    		 url =basePath+"ea/bidrecruit/ea_getIndustryList.jspa?type=修改工作经验&staffid="
			    	    +staffid+"&name="+name
				     	+"&admissionTime="+admissionTime+"&graduationTime="
				     	+graduationTime+"&position="+position+"&postName="
				     	+postName+"&duties="+duties+"&typedata="+typedata
				     	+"&recordKeys="+recordKeys+"&recordIDs="+recordIDs
				     	+"&resumeIDs="+resumeIDs
				     	+"&resumeID="+resumeID
				     	+"&sccId="+sccId
				     	+"&jitype="+jitype;
		    	}else{
		    		if(duties==""||duties==null||duties==undefined){
		    			duties=dutiess;
					}
		    		 url =basePath+"ea/bidrecruit/ea_getIndustryList.jspa?type=修改工作经验&staffid="
			    	    +staffid+"&name="+name
				     	+"&admissionTime="+admissionTime+"&graduationTime="
				     	+graduationTime+"&position="
				     	+position+"&postName="
				     	+postName+"&duties="+duties
				     	+"&typedata="+typedata
				     	+"&recordKeya="+recordKeya
				     	+"&recordIDa="+recordIDa
				     	+"&resumeIDa="+resumeIDa
				     	+"&resumeID="
				     	+resumeID+"&sccId="
				     	+sccId+"&jitype="+jitype;
		    	}
	    	   
		       
				document.location.href = url;
		      
	      });
	      //跳转岗位搜索页面
	       $("#jobSearch").click(function() {
	    	   var name = $("#scname").val();//学校名称
		    	var admissionTime = $("#beginTime").val();//开始时间
		    	var graduationTime = $("#endTime").val();//结束时间
		    	var position = $("#position").text();//所在行业
		    	var postName = $("#postName").text().replace(/\|/g,'%7C');//岗位名称
		    	var staffid = $("#staffid").val();
		    	var duties = $("#duties").val();
		    	var dutiess = $("#dutiess").val();
		    	var recordKeys=$("#recordKeys").val();
		    	var recordIDs =$("#recordIDs").val();
		    	var resumeIDs = $("#resumeIDs").val();
		    	var recordKeya=$("#recordKeya").val();
		    	var recordIDa =$("#recordIDa").val();
		    	var resumeIDa = $("#resumeIDa").val();
		    	//var resumeID = $("#resumeID").val();
		    	var url=null;
		    	if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
		    		if(duties==""||duties==null||duties==undefined){
		    			duties=dutiess;
					}
		    		url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=修改工作经验&staffid="
			    	    +staffid+"&name="+name
				     	+"&admissionTime="+admissionTime+"&graduationTime="
				     	+graduationTime+"&position="
				     	+position+"&postName="
				     	+postName+"&duties="+duties
				     	+"&typedata="+typedata
				     	+"&recordKeys="+recordKeys
				     	+"&recordIDs="+recordIDs
				     	+"&resumeIDs="+resumeIDs
				     	+"&resumeID="+resumeID
				     	+"&sccId="+sccId
				     	+"&jitype="+jitype;
		    	}else{
		    		if(duties==""||duties==null||duties==undefined){
		    			duties=dutiess;
					}
		    		url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=修改工作经验&staffid="
		    	    +staffid+"&name="+name
			     	+"&admissionTime="
			     	+admissionTime+"&graduationTime="
			     	+graduationTime+"&position="
			     	+position+"&postName="
			     	+postName+"&duties="+duties
			     	+"&typedata="+typedata
			     	+"&recordKeya="+recordKeya
			     	+"&recordIDa="+recordIDa
			     	+"&resumeIDa="+resumeIDa
			     	+"&resumeID="+resumeID
			     	+"&sccId="+sccId+"&jitype="+jitype;
		    		
		    		
		    	}
		    	
	    	   
	    	    document.location.href = url;
	      
	      });
	    //点击确定按钮
	     $("#quedingp").click(function() {
	    	save();
	       
	      });
 });
      function save(){
      	var name = $("#scname").val();//学校名称
    	var admissionTime = $("#beginTime").val();//开始时间
    	var graduationTime = $("#endTime").val();//结束时间
    	var position = $("#position").text();//所在行业
    	var postName = $("#postName").text().replace(/\|/g,'%7C');//岗位名称
    	var staffid = $("#staffid").val();
    	var staffids = $("#staffids").val();
    	var dutiess = $("#dutiess").val();
    	var recordKeys=$("#recordKeys").val();
    	var recordIDs =$("#recordIDs").val();
    	var resumeIDs = $("#resumeIDs").val();
    	var recordKeya=$("#recordKeya").val();
    	var recordIDa =$("#recordIDa").val();
    	var resumeIDa = $("#resumeIDa").val();
    	//var resumeID = $("#resumeID").val();
    	var duties = $("#duties").val();
    	
    	var url =null;
    	if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
    		
    		
    		
    		url = basePath + "ea/resumes/ea_addResume.jspa?resume.staffID="+staffid+"&resume.companyName="+name
    		+"&resume.startTime="+admissionTime+"&resume.endTime="
    		+graduationTime+"&resume.position="+position+"&resume.postName="
    		+postName+"&staffid="+staffids
    		+"&resume.duties="+dutiess
    		+"&resume.recordKey="+recordKeys
    		+"&resume.recordIDs="+recordIDs
    		+"&resume.resumeID="+resumeIDs+
    		"&type=update&resumeID="+resumeID
    		+"&sccId="+sccId+"&jitype="
    		+jitype+"resume.duties="+duties;
    	}else{
    		
    		
    		
    		url = basePath + "ea/resumes/ea_addResume.jspa?resume.staffID="+staffid+"&resume.companyName="+name
    		+"&resume.startTime="+admissionTime+"&resume.endTime="
    		+graduationTime+"&resume.position="
    		+position+"&resume.postName="
    		+postName+"&staffid="+staffid
    		+"&resume.duties="+dutiess
    		+"&resume.recordKey="+recordKeya
    		+"&resume.recordID="+recordIDa
    		+"&resume.resumeID="+resumeIDa+
    		"&type=update&resumeID="
    		+resumeID+"&sccId="+sccId
    		+"&jitype="+jitype
    		+"resume.duties="+duties;
    	}
    	
    	$("#form").attr("target", "hidden").attr("action",url);
		document.form.submit.click();
		token = 2;
     
      }
      function re_load(){
	    	var recordKeya=$("#recordKey").val();
	      	var recordIDa =$("#recordID").val();
	      	var resumeIDa = $("#resumeIDa").val();
	      	var staffid = $("#staffid").val();
	      	var staffids = $("#staffids").val();
	      	//var resumeID = $("#resumeID").val();
	    	var url=null;
	    	
    		if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
    			
    			url = basePath+"ea/resumes/ea_queryResume.jspa?staffid="+staffids+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
    		
    		}else{
    			
    			url = basePath+"ea/resumes/ea_queryResume.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
    		}
    		document.location.href= url;
    	} 