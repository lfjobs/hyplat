$(document).ready(function(){
	if(status=="seedetail"){
		$("#windingTestForm").find("input").each(function(){
				    			$(this).hide();
				    		});
				    		$("#windingTestForm").find("select").each(function(){
				    			$(this).hide();
				    		});

		}
	
	$("#returnmain").click(function(){
		window.location.href = basePath + "ea/productregister/ea_getAllListProductregister.jspa";
		
	});
	 //打印单据
	  $("#printpage").click(function(){
		    var carCylinderId = $("#carCylinderId").text();
	    	if(confirm("确定继续？")){
	    		var url = basePath + "ea/dispitch/ea_detectionInformation.jspa?carCylinderId="+ carCylinderId+"&bottletype=Winding"
	    		open(url);
	    	}
			});
	//保存缠绕瓶 记录表信息
		$("#savewinding").click(function(){
			var flag = 1;
				$("#windingTestForm").find(".littleClass").each(function(){
					if($(this).val()==""){
						$(this).css("border","1px solid red");
						$(this).css("width","96%");
						flag = 2;
						
					}else{
						$(this).css("border","0");
						$(this).css("width","100%");
					}
				});
				if(flag==2){
					
					$("#tishiInformation").fadeIn(2000); 
					return;
					
				}else{
					$("#tishiInformation").fadeOut(2000); 
						
				}
				
				
			//	$("#tishiInformation").fadeOut(2000); 
				
				if(confirm("确定继续？")){
					$("#tishiInformation").fadeOut(2000); 
		    		var carCylinderId = $("#carCylinderId").text();
		    		$("#windingTestForm") .attr("action", basePath + "ea/gascylindertest/ea_saveWindingtest.jspa?carCylinderId="+ carCylinderId);
		    		document.windingTestForm.submit.click();
		    		alert("操作成功！");
		    	}
		});
});





