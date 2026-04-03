$(document).ready(function(e) {
     $("#returnClick").click(function() {history.go(-1);});
     var typedata = $("#typedatas").val();
     if(typedata=="修改"){
     	$("#beginTime").val($("#admissionTime").val());//入学时间
		$("#endTime").val($("#graduationTime").val());//毕业时间
		$("#professionalName").text($("#").val());//专业名成
		$("#education").text($("#educations").val());//学历学位
		$("#scname").val($("#name").val());//学校名称
		$("#professionalName").text($("#professionalNames").val());
		$("#educationIDa").val(eduid);
		$("#resumeIDa").val(resuid);
		$("#keya").val(keyap);
		$("#staffid").val(staff);
     }
     
     
   
     	//专业
		$("#JobSearch").click(function() {
			var admissionTime = $("#beginTime").val();//入学时间
			var graduationTime = $("#endTime").val();//毕业时间
			var name = $("#scname").val();//学校名称
			var staffid = $("#staffid").val();//人员id
			var staffids = $("#staffids").val();//人员id
			var education = $("#education").text();//学历学位
			
			
			var educationID = $("#educationIDs").val();
			var resumeID = $("#resumeIDs").val();
			var key =$("#keys").val();
			var educationIDa = $("#educationIDa").val();
			var resumeIDa = $("#resumeIDa").val();
			var keya =$("#keya").val();
			var resumeID =$("#resumeID").val();
			
			var url =null;
			if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
				if(staffids==""||staffids==null){
					staffids=staffid;
				} 
				url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=修改教育背景&staffids="+staffids
				    +"&admissionTime="+admissionTime
				    +"&graduationTime="+graduationTime
				    +"&name="+name
				    +"&education="+education+"&typedata=修改"
				    +"&key="+key
				    +"&resumeID="+resumeID
				    +"&educationID="+educationID+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
			}else{
				if(staffid==""||staffid==null){
					staffid=staffids;
				}
				  url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=修改教育背景&staffid="+staffid
				    +"&admissionTime="+admissionTime
				    +"&graduationTime="+graduationTime
				    +"&name="+name
				    +"&education="+education+"&typedata=修改"
				    +"&keya="+keya
				    +"&resumeIDa="+resumeIDa
				    +"&educationIDa="+educationIDa+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
			}
			
		  
			document.location.href = url;

		});
		//学历学位
		$("#degree").click(function() {
			var admissionTime = $("#beginTime").val();//入学时间
			var graduationTime = $("#endTime").val();//毕业时间
			var name = $("#scname").val();//学校名称
			var education = $("#education").text();//学历学位
			var staffid = $("#staffid").val();//人员id
			var staffids = $("#staffids").val();//人员id
			
			var educationIDs = $("#educationIDs").val();
			var resumeIDs = $("#resumeIDs").val();
			var keys =$("#keys").val();
			var educationIDa = $("#educationIDa").val();
			var resumeIDa = $("#resumeIDa").val();
			var keya =$("#keya").val();
			var resumeID = $("#resumeID").val();//人员id
			var professionalName=$("#professionalName").text().replace(/\|/g,'%7C');
			var url=null;
			if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
				if(staffid==""||staffid==null||staffid==undefined){
					staffid=staffids;
				}
				url = basePath + "/page/ea/main/production/resume/degree.jsp?admissionTime="
					+admissionTime+"&graduationTime="
					+graduationTime+"&name="
					+name+"&staffid="
					+staffid+"&typedata=修改"
					+"&keys="+keys+"&educationIDs="
					+educationIDs+"&resumeIDs="+resumeIDs
					+"&professionalName="+professionalName+"&type=修改教育背景&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
			}else{
				if(staffids==""||staffids==null||staffids==undefined){
					staffids=staffid;
				}
				url = basePath + "/page/ea/main/production/resume/degree.jsp?admissionTime="
					+admissionTime+"&graduationTime="
					+graduationTime+"&name="
					+name+"&staffids="
					+staffids+"&typedata=修改"
					+"&keya="+keya+"&educationIDa="
					+educationIDa+"&resumeIDa="+resumeIDa
					+"&professionalName="+professionalName+"&type=修改教育背景&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
			}
			document.location.href = url;

		});
		 
		//确定的点击
		 $("#quedingp").click(function(){
       	 	$(".alert_div2").css("display","none");
       	 update();
    	});
     });
        $(".alert_div2").css("height",$(window).height()*1+45+'px');
        function doFn(){
            var flag = true;//是否全部输入  默认true
            $(".ipt").each(function (){
                if($(this).val()==""){//有空输入，将flag置为false
                    flag = false;//
                }
            });
            if(flag){
                $(".alert_div2").css("display","block");
            }else{
                $(".alert_div").css("display","block");
                setTimeout(function(){
                $("#alert_d").hide();}
                ,2000);//2秒后执行该方法
            }
        }
        function update(){
        	
        	
        	var admissionTime = $("#beginTime").val();//入学时间
			var graduationTime = $("#endTime").val();//毕业时间
			var professionalName = $("#professionalName").text().replace(/\|/g,'%7C');//专业名称
			var education = $("#education").text();//学历学位
			var name = $("#scname").val();//学校名称
			var staffids = $("#staffids").val();//人员id
			var staffid = $("#staffid").val();//人员id
			
			var educationIDs = $("#educationIDs").val();
			var resumeIDs = $("#resumeIDs").val();
			var keys =$("#keys").val();
			
			var educationIDa = $("#educationIDa").val();
			var resumeIDa = $("#resumeIDa").val();
			var keya =$("#keya").val();
			var resumeID = $("#resumeID").val();//人员id
			var url =null;
			
			if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
				if(staffid==""||staffid==null||staffid==undefined){
					staffid=staffids;
				}
				url = basePath+ "ea/resumes/ea_addEducational.jspa?educational.admissionTime="
					+ admissionTime + "&educational.graduationTime="
					+ graduationTime + "&educational.professionalName="
					+ professionalName + "&educational.education=" 
					+ education+ "&educational.name="
					+ name + "&staffid="
					+staffid+"&educational.educationKey="+keys
					+"&educational.educationID="+educationIDs
					+"&educational.staffID="+staffid
					+"&educational.resumeID="+resumeIDs
					+"&educational.degree=1"
					+"&type=update&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back
				
			}else{
				if(staffids==""||staffids==null||staffids==undefined){
					staffids=staffid;
				}
				url = basePath+ "ea/resumes/ea_addEducational.jspa?educational.admissionTime="
					+ admissionTime + "&educational.graduationTime="
					+ graduationTime + "&educational.professionalName="
					+ professionalName + "&educational.education=" 
					+ education+ "&educational.name="
					+ name + "&staffid="
					+staffids+"&educational.educationKey="+keya
					+"&educational.educationID="+educationIDa
					+"&educational.staffID="+staffids
					+"&educational.resumeID="+resumeIDa
					+"&educational.degree=1"
					+"&type=update&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
			}
				
			$("#form").attr("target", "hidden").attr("action",url);
			document.form.submit.click();
			token = 2;
        }
		function re_load(){
			var keya =$("#key").val();
			var educationIDa = $("#educationID").val();
			var resumeIDa = $("#resumeID").val();
			var staffids = $("#staffids").val();//人员id
			var staffid = $("#staffid").val();//人员id
			var resumeID = $("#resumeID").val();//人员id
			
			var url=null;
			if(staffids==""||staffids==null){
				staffids=staffid;
			}
			if((keya==""||keya==undefined)&&(resumeIDa==""||resumeIDa==undefined)&&(educationIDa==""||educationIDa==undefined)){
				
				url = basePath+"ea/resumes/ea_queryEdu.jspa?staffid="+staffids+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			
			}else{
				
				url = basePath+"ea/resumes/ea_queryEdu.jspa?staffid="+staffids+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			}
			document.location.href= url;
		} 
		
		