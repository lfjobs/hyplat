$(function(){
	var height = $("#tableprint").height();
	var str = '';
    if(st!="00" && st!=''){
    	alert(st);
        if(st=="10"){
            str ="<img src='"+basePath+"images/ea/finance/zuofei.png'/>";
        }
        
    	else if(st=="07"||st=="43"||st=="44"||st=="45"||st=="46"||st=="08"){
        	str ="<img src='"+basePath+"images/ea/finance/yishen.png'/>";
        }
        else{
            str ="<img src='"+basePath+"images/ea/finance/daishen.png'/>";
       }
     }
     $("#apDiv1").html(str);
     $("#apDiv1").css({'top':height-70}); 
});
