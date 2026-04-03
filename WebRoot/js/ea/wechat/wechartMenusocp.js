
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
function querendaili()
{
	if(yanz()){
		ifxh();		
	  	$("#chenpID").val(cpid);	
		if (confirm("确定代理？")) {
			$('#cpform')
			.attr("target", "hidden").attr("action",basePath + "/ea/wfjcustomer/ea_sevedailicp.jspa");
		  	token=2;
			$('#cpform').submit();
		}
		$("#chenpID").val("");
		cpid="";
	}		
	 
	
}
function dui(dd){
	if($(dd).find("div").find("#images").attr("src")==(basePath+"/images/WFJClient/choice_blank.png"))
 {
		$(dd).find("div").find("#images").attr("src",basePath+"/images/WFJClient/choice_choice.png");
		$(dd).find("div").find("div").find("#cpid").attr("checked","checked");
 }
 else
 {
	 $(dd).find("div").find("#images").attr("src",basePath+"/images/WFJClient/choice_blank.png");
	 $(dd).find("div").find("div").find("#cpid").attr("checked","");
 }
}
//AJAX刷新
function jiazai()
{
	yeshu++;
	$.ajax({   
        type: "get",   
        dataType: "json",          
         
        url:encodeURI( basePath+"ajax/wfjcustomer/sajax_ea_getDLgetlist.jspa?pagenull="+yeshu),  
    
        success: function (data) { 
        
        	var t = eval("("+data+")"); //强制转换一下json字符串，生成json对象   
            var list=t.pageForm;        	 
            for ( var i = 0;i<list.length; i++) {
       
            	var tabletr=" <div class='agent_block' id='"+list[i][0]+"'>";
            	 tabletr+="<div class='all_width' >";
            	 tabletr+="<div class='agent_title'>";
            	 tabletr+=" <ul><li>所属公司：</li><li style='padding-left:10px;'>"+list[i][2]+"</li> </ul>";
            	 tabletr+="</div>";
            	 tabletr+=" <div class='agent_pro'>";
            	 tabletr+="<ul>";
            	 tabletr+="<li><input type='checkbox' /></li>";
            	 tabletr+="<li class='agent_img'><img src="+list[i][6]+" /></li>";
            	 tabletr+="<li style='width:40%;'>";
            	 tabletr+=" <div>";
            	 tabletr+="<ul>";
            	 tabletr+="<li>"+list[i][1]+"</li>";
            	 if(list[i][3]!=null) {  
            	 tabletr+="<li class='colorf00'>￥:"+list[i][3]+"</li>";
            	 }
            	 else{
            		 
            		 tabletr+="<li class='colorf00'>￥:0</li>";
                 	
            	 }
            	 if(list[i][4]!=null) {   
            	 tabletr+="<li>总数量："+list[i][4]+"</li>";
            	 }
            	 else{
            		 tabletr+="<li>总数量：0</li>";
            	}
            	 tabletr+="</ul>";
            	 tabletr+="</div>";
            	 tabletr+="</li>";
            	 if(list[i][5]!=null) {   
            	 tabletr+="<li class='agent_yong colorf00'>佣金:"+list[i][5]+"</li>";
            }
            	 else{
            	 tabletr+="<li class='agent_yong colorf00'>佣金:0</li>";
            	 }
             	
            	 tabletr+="</ul>";
            	 tabletr+="</div>";
            	 tabletr+="</div>";
            	 tabletr+="</div>";
            	$("#app").append(tabletr);
			}			
           /* $.each(t, function (i, n) {           	
                var row = $("#template").clone(); //克隆模板，创建一个新数据行   
                for (attribute in n) {   
                    row.find("#" + attribute).html(n[attribute]); //循环json对象的属性，并赋值到数据行中对应的列，此处列的id就是相应的属性名称   
                }   
                row.appendTo($("#testTable"));   
            });  */ 
        }   
    });  
	
}