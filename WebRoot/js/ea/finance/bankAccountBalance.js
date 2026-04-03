$(document).ready(function(){
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20 // 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	//关闭
	$(".JQueryreturn").click(function() {
		$("#jqModel").jqmHide();		
	});
	//打印
	$(".JQueryPrinter").click(function() {
		$("#jqModel").jqmHide();
		var dt = new Date();
		window.open(basePath + "page/ea/main/finance/dockingbank/printBankAccountBalance.html?datetime=" + dt.getTime() );		
	});
		
	
	$("#tosearch").click(function(){	
				
				var _account = $("#accountlist").find("option:selected").text().split("|");				
				$("#account").val(_account[0]);
				$("#banksx").val(_account[1]);
				$("#provcode").val(_account[2]);
		 		$("#currency").val(_account[3]);	
		 			
	 			$.ajax(
					{type:"POST",
					url:basePath + "ea/bankaccountBalance/ea_getAccountBalance.jspa?innerAction=accountBalanceQuery",
					data:{  "bankAccount.account":$("#account").val(),
							"bankAccount.banksx":$("#banksx").val(),
							"bankAccount.provcode":$("#provcode").val(),
							"bankAccount.currency":$("#currency").val(),
						    innerTransCode:$("#innerTransCode").val()
					      },
					dataType:"json",
					success:function(data)
					{						
					   try{
						var accountBalanceBean = eval(data.accountBalanceBean);
						$("#jqModel").jqmShow();									
						$("#accountBalanceBean-accType").html(accountBalanceBean.accType);
						$("#accountBalanceBean-accSts").html(accountBalanceBean.accSts);
						$("#accountBalanceBean-frzAmt").html(accountBalanceBean.frzAmt);
						$("#accountBalanceBean-frzBal").html(accountBalanceBean.frzBal);
						$("#accountBalanceBean-valUDLmt").html(accountBalanceBean.valUDLmt);
						$("#accountBalanceBean-valMonthLmt").html(accountBalanceBean.valMonthLmt);
						$("#accountBalanceBean-valDayLmt").html(accountBalanceBean.valDayLmt);
						$("#accountBalanceBean-lastAvailBal").html(accountBalanceBean.lastAvailBal);
						$("#accountBalanceBean-lastBal").html(accountBalanceBean.lastBal);
						$("#accountBalanceBean-availBal").html(accountBalanceBean.availBal);
						$("#accountBalanceBean-bal").html(accountBalanceBean.bal);
					   }catch(err)
					   {
					   	alert("系统发生异常,请联系管理员.");
					   }	
					},
					error : function(data) {						
						alert("系统发生异常,请联系管理员.");
					}
				}
			)	 			
	});
	
});

