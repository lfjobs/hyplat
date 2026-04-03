 $(document).ready(function(){
      	//返回
        $("#returnClick").click(function() {history.go(-1)})
            $(".degree_txt p").click(function(){
				var admissionTime = $("#admissionTime").val();
				var graduationTime = $("#graduationTime").val();
				var name = $("#name").val();
				var staffid = $("#staffid").val();
				var staffids = $("#staffids").val();
                var education=$(this).text();
                
                var educationID = $("#educationIDs").val();
    			var resumeID = $("#resumeIDs").val();
    			var key =$("#keys").val();
    			var educationIDa = $("#educationIDa").val();
    			var resumeIDa = $("#resumeIDa").val();
    			var keya =$("#keya").val();
                
                var typedata = $("#typedata").val();
                var type=$("#type").val();
                var professionalName=$("#professionalName").val().replace(/\|/g,'%7C');
                
                var resumeID=$("#resumeID").val();
                
                var url =null;
                 if(typedata=="修改"){
                	 if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
                		 if(staffid==""||staffid==null||staffid==undefined){
         					staffid=staffids;
         				}
                		 url = basePath + "/page/ea/main/production/resume/updateEducationalBackg.jsp?admissionTime="+admissionTime+
		     					"&graduationTime="+graduationTime+"&name="
		     					+name+"&staffid="+staffid+"&education="+education+"&keys="
		     					+keys+"&typedata="+typedata+"&educationIDs="
		     					+educationIDs+"&resumeIDs="+resumeIDs
		     					+"&professionalName="+professionalName+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
                	 }else{
                		 if(staffids==""||staffids==null||staffids==undefined){
         					staffids=staffid;
         				}
                		 url = basePath + "/page/ea/main/production/resume/updateEducationalBackg.jsp?admissionTime="+admissionTime+
		     					"&graduationTime="+graduationTime+"&name="
		     					+name+"&staffids="+staffids+"&education="+education+"&keya="
		     					+keya+"&typedata="+typedata+"&educationIDa="
		     					+educationIDa+"&resumeIDa="+resumeIDa
		     					+"&professionalName="+professionalName+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
                	 }
	                
                }else{ 
	                url = basePath + "/page/ea/main/production/resume/addEducationalBackg.jsp?admissionTime="+admissionTime+
					"&graduationTime="+graduationTime+"&name="
					+name+"&staffid="+staffid+"&education="+education+"&key="+key
					+"&professionalName="+professionalName+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
               	} 
              		
				document.location.href = url;

            });
        });