$(function() {

    
	
	//提交表单
	$("#submitPay").click(function(){
		 var bool = true;
		$("#main").find("input[name]").each(function(){
			if($(this).val()==""&&$(this).attr("name")!="ddid"){
				alert("请输入"+$(this).parent().find("p").text());
				bool = false;
				return false;
			}
			
		});
		
	   if(bool){
	     $("#moneyBox").attr("action",basePath+"ea/wfjshop/ea_confirmMoneyBox.jspa");
	     document.moneyBox.submit();
	   }
		
	});
	
	$("#back").click(function(){
		
		history.go(-1);
	});
	
	
});


