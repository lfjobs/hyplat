var url = null;		
//工作性质
		function WorkingProperty(obj){
		var vaul = $(".confirmationBox").text();
		$("#vaue").text(vaul);
		
		}
		//求职状态
		function Jobstatus(obj){
		var vauls = $(".confirmationBox2").text();
		$("#vaues").text(vauls);
		
		
		}
        $(document).ready(function(){
        	if($("#typep").val()!='求职意向'){
        		if($("#staffIDs").val()!=null&&$("#staffIDs").val()!=undefined&&$("#staffIDs").val()!=""){
                	$("#vaue").text($("#works").val());//工作性质
        			$("#reg").text($("#region").val());//工作地区
        			$("#pos").text($("#positions").val());//职位类别
        			$("#industry").text($("#industrya").val());//行业类别
        			$("#qbox").text($("#salarys").val());//期望薪资
        			$("#vaues").text($("#statuss").val());//求职状态
        			$("#resumeIDs").val($("#resumeIDs").val());
        			
        			//编辑的点击
                	}	
        	}else{
        		
        	}
        	
        	
        	if($("#staffIDs").val()==null||$("#staffIDs").val()==undefined||$("#staffIDs").val()==""){
				$("#staffIDs").val(staff);
			}
        	 //返回
            $("#returnClick").click(function() {
            	var staffid = $("#staffid").val();
	        	var resumeID=$("#resumeID").val();
	    		document.location.href=basePath+"ea/resumes/ea_queryPersion.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	        
            
            });
            
        	//保存的确定
    		$("#saves").click(function() {
    			Share("保存");
    		});
        	$("#bianji").click(function() {
        		Share("编辑");
			});
        	//地区
	        $("#diqu").click(function() {
	        	Share("地区");
	        });
	        
	        //跳转期望薪资
			$("#salaryExpectation").click(function() {
				
				Share("期望薪资");
			});
	       	//跳转行业搜索2 
			$("#industrySearch2").click(function() {
				Share("行业搜索");
			});
			//跳转职位搜索
			$("#JobSearch").click(function() {
				Share("职位搜索");
			});
			//跳转个人简历
			$("#resume").click(function() {
				url = basePath + "/page/ea/main/production/resume/resume.jsp?sccId="+sccId+"&jitype="+jitype;;
				document.location.href = url;
	
			});
});
        
        function Share(obj){
        	var work = $("#vaue").text();//工作性质
			var region = $("#reg").text();//工作地区
			var position = $("#position").text().replace(/\|/g,'%7C');//职位类别
			var industry =$("#industry").text();//行业类别
			var status = $("#vaues").text();//求职状态
			var staffIDs = $("#staffIDs").val();
			var staffid = $("#staffid").val();
			var salary=$("#qbox").text();
			
			var jobWantedKeys =$("#jobWantedKeys").val();
			var resumeIDs =$("#resumeIDs").val();
			var jobWantedIds=$("#jobWantedIds").val();
			
			if(obj=="职位搜索"){
				url =basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?work="+work
				+"&region="+region+"&position="
				+position+"&industry="+industry+"&staus="
				+status+"&staffid="+staffid+"&type=求职意向"+"&salary="+salary+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
				
			}else if(obj=="行业搜索"){
				var position = $("#pos").text().replace(/\|/g,'%7C');//职位类别
				url = basePath+"ea/bidrecruit/ea_getIndustryList.jspa?work="+work
				+"&region="+region+"&position="+position
				+"&industry="+industry+"&staus="+status
				+"&staffid="+staffid+"&type=求职意向"+"&salary="+salary+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
			}else if(obj=="期望薪资"){
				var position = $("#pos").text().replace(/\|/g,'%7C');//职位类别
				
				url = basePath + "/page/ea/main/production/resume/salaryExpectation.jsp?work="+work
				+"&region="+region+"&position="+position+"&industry="+industry+"&status="
				+status+"&staffid="+staffid+"&staffIDs="+staffIDs+"&resumeID="+resumeID+"&type=求职意向"+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
			
				document.location.href = url;
			}else if(obj=="地区"){
				var position = $("#pos").text().replace(/\|/g,'%7C');//职位类别
				url=basePath +"/page/ea/main/production/cprocedure/recruit/recruitCity.jsp?type=求职意向&work="
		        	+work+"&position="+position+"&industry="
		        	+industry+"&status="+status
		        	+"&staffid="+staffid        
		        	+"&salary="+salary+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
				document.location.href = url;
				
			}else if(obj=="编辑"){
				var position = $("#pos").text().replace(/\|/g,'%7C');//职位类别
				url = basePath+"ea/resumes/ea_searchIntention.jspa?wanted.work="+work+"&wanted.region="
				+region+"&wanted.position="+position+"&wanted.industry="
				+industry+"&wanted.salary="+salary+"&wanted.status="
				+status+"&staffid="+staffIDs+"&type=update"+"&wanted.staffID="+staffIDs
				+"&wanted.jobWantedKey="+jobWantedKeys+"&wanted.resumeID="+resumeIDs+"&wanted.jobWantedId="
				+jobWantedIds+"&wanted.degree=1&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
				$("#form").attr("target", "hidden").attr("action",url);
				document.form.submit.click();
				token = 2;
			}else if(obj=="保存"){
				var position = $("#pos").text().replace(/\|/g,'%7C');//职位类别
				
				 url = basePath+"ea/resumes/ea_searchIntention.jspa?wanted.work="+work+"&wanted.region="
					+region+"&wanted.position="+position+"&wanted.industry="
					+industry+"&wanted.salary="+salary+"&wanted.status="
					+status+"&staffid="+staffid+"&type=save&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype;
	    			
	    			$("#form").attr("target", "hidden").attr("action",url);
	    			document.form.submit.click();
	    			token = 2;
			}
			
			
			return url;
        }
     
        function re_load(){
      	  var staffid = $("#staffid").val();
      	var resumeID=$("#resumeID").val();
		document.location.href=basePath+"ea/resumes/ea_queryPersion.jspa?staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
    } 