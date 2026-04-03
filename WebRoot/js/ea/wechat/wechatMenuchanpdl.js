
function re_load(){
	if(token){
		document.location.href =basePath+"/ea/wfjcustomer/ea_getdllist.jspa";
	}
};


function yanz()
{
	 var yan=0;
	  	$(".agent_block").each(function()
	  			{ 
	  			if($(this).find(".chebox").attr("checked"))
				  {	  		   	 
	  				yan=1;
				  }
			});
	  	if(yan==0)
	  		{
	  		    alert("请选择!");
	  		  return  false ;
	  		
	  		}
	  	else{
	  		
	  		return true;
	  	}	  
}
function ifxh()
{
	$(".agent_block").each(function()
  			{ 
  			if($(this).find(".chebox").attr("checked"))
			  {	  		   	 
  				  cpid+=$(this).attr("id");	  					
  				  	cpid+=",";					  
			  }	  			
		});
}
function quxiao()
{
	
	if(yanz()){
		ifxh();		
	
	  	$("#chenpID").val(cpid);	
		if (confirm("取消代理？")) {
			$('#cpform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_delchenp.jspa?type=2");
		  	token=2;
			$('#cpform').submit();
		}
		$("#chenpID").val("");
		cpid="";
	}		
	 
	
	}

