	$(document).ready(function() {
				//返回
				$("#returnClick").click(function() {
						history.go(-1)
				});
				//跳转添加实习经验页面
				$("#addInternshipExperience").click(function() {
				var staffid = $("#staffid").val();
				var name = $("#name").val();
				var admissionTime = $("#admissionTime").val();
				var graduationTime = $("#graduationTime").val();
				var position = $("#position").val();
				var postName = $("#postName").val().replace(/\|/g,'%7C');
				var duties = $("#duties").val();
				var typedata = $("#typedatas").val();
				
				
				
				var recordKeys = $("#recordKeys").val();
				var recordIDs = $("#recordIDs").val();
				var resumeIDs = $("#resumeIDs").val();
				
				var recordKeya = $("#recordKeya").val();
				var recordIDa = $("#recordIDa").val();
				var resumeIDa = $("#resumeIDa").val();
				
				var resumeID=$("#resumeID").val();
			
				var url =null;
				if(typedata=="修改"){
					if(recordKeya==""&&recordIDa==""&&resumeIDa==""){
						
						url = basePath + "/page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid+"&name="+name
	     				+"&admissionTime="+admissionTime+"&graduationTime="+graduationTime+"&position="+position+"&postName="
	     				+postName.replace(/\|/g,'%7C')+"&duties="+duties
	     				+"&ip="+"&typedata="+typedata
	     				+"&recordKeys="+recordKeys
	     				+"&recordIDs="+recordIDs
	     				+"&resumeIDs="+resumeIDs
	     				+"&duties="+duties
	     				+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					}else{
						url = basePath + "/page/ea/main/production/resume/updateInternshipExperience.jsp?staffid="+staffid+"&name="+name
	     				+"&admissionTime="+admissionTime+"&graduationTime="+graduationTime+"&position="+position+"&postName="
	     				+postName.replace(/\|/g,'%7C')+"&duties="+duties
	     				+"&ip="+"&typedata="+typedata
	     				+"&recordKeya="+recordKeya
	     				+"&recordIDa="+recordIDa
	     				+"&resumeIDa="+resumeIDa
	     				+"&duties="+duties
	     				+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
						
					}
					
				}else{
					url = basePath + "/page/ea/main/production/resume/addInternshipExperience.jsp?staffid="+staffid+"&name="+name
     				+"&admissionTime="+admissionTime+"&graduationTime="+graduationTime+"&position="
     				+position+"&postName="+postName.replace(/\|/g,'%7C')+"&duties="+duties+"&ip=&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
					
				}
				
				document.location.href = url;
				
				});
				

		});