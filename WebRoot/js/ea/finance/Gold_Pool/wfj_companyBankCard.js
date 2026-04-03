$(function(){
	
	object.push("khd=",khd);
	object.push("&flag=",flag);		
	object.push("&identifying=",identifying);
	object.push("&mark=",mark);
	
					
	//删除银行卡
	$(".relieve_btn").click(function(){
		
		var bankAccountID = $(this).find("#bankId").val();    			
		if(confirm("你确定解除绑定该银行卡吗？")){
			var url = basePath + "ea/perinfor/sajax_ea_deleteBankCard.jspa?bankAccountID="+bankAccountID;    			
			$.ajax({       				
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				success : function (data) {
					var member = eval("(" + data + ")");
					var flag = member.flag;
					if(flag == 'ok'){       						
						window.location.href = basePath+ "ea/industry/ea_getBankCardsList.jspa?"+object.join("")+"&staffId="+staffid+"&sccid="+sccid+"&bankId="+bankId+"&ccompanyId="+ccompanyId+"&user="+user;
						
					}else{
						alert("银行卡未解除绑定！");
					}
				}         			       			
			});
		}  			   			
	});
	
	if(mark == '00'){
		$(".bank_box").click(function(){
			var bankAccountID = $(this).parent().attr("id"); 
			window.location.href = basePath + "/ea/jinbi/ea_getwfjtixian.jspa?"+object.join("")+"&user="+user+"&sccid="+sccid +"&staffid="+staffid+"&bankId="+bankAccountID+"&ccompanyId="+ccompanyId;   				   				       
		});   			
	} 
});






