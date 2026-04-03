var url=null;
$(document).ready(function(e) {
     $("#returnClick").click(function() {history.go(-1)});
     	//专业
		$("#JobSearch").click(function() {
			Share("专业");

		});
		//学历学位
		$("#degree").click(function() {
			
			Share("学历学位");
		});
		
		//确定的点击
		 $("#quedingp").click(function(){
       	 	$(".alert_div2").css("display","none");
			Share("确定");
    	});
     });
        $(".alert_div2").css("height",$(window).height()*1+45+'px')
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
        function Share(obj){
        	var admissionTime = $("#beginTime").val();//入学时间
			var graduationTime = $("#endTime").val();//毕业时间
			var name = $("#scname").val();//学校名称
			var staffid = $("#staffid").val();//人员id
			var education = $("#education").text();//学历学位
			var professionalName=$("#professionalName").text().replace(/\|/g,'%7C');
			var resumeID=$("#resumeID").val();
		
        	if(obj=="专业"){
	        	url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=教育背景&staffid="+staffid
	     		    +"&admissionTime="+admissionTime+"&graduationTime="+graduationTime+"&name="+name
	     		    +"&education="+education+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	     			document.location.href = url;
        		
        	}else if(obj=="学历学位"){
	        	url = basePath + "/page/ea/main/production/resume/degree.jsp?admissionTime="+admissionTime+
	    			"&graduationTime="+graduationTime+"&name="+name+"&staffid="+staffid+"&type="
	    			+"&professionalName="+professionalName+"&education="+education+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	    			document.location.href = url;
        	}else if(obj=="确定"){
        		url = basePath+ "ea/resumes/ea_addEducational.jspa?educational.admissionTime="
				+ admissionTime + "&educational.graduationTime="
				+ graduationTime + "&educational.professionalName="
				+ professionalName + "&educational.education=" + education
				+ "&educational.name=" + name + "&educational.staffID="+staffid+"&resumeID="+resumeID+"&type=save"+"&sccId="+sccId+"&jitype="+jitype;
				$("#form").attr("target", "hidden").attr("action",url);
				document.form.submit.click();
				token = 2;
        	}
        		
        }
function re_load(){
	var staffid = $("#staffid").val();//人员id
	var resumeID=$("#resumeID").val();
	document.location.href=basePath+"ea/resumes/ea_queryEdu.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
}