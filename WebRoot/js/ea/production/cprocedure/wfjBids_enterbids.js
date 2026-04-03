$(document).ready(function() {

//  添加竞标产品
	$(".add_subproject")
			.click(
					function() {
						
						
						 var url = basePath+"ea/purchasebids/sajax_ea_ajaxValidate.jspa";
					        $.ajax({
					        	url:url,
					        	type:"get",
					        	async:false,
					        	dataType:"json",
					        	data:{
					        		valtype:"selb"
					        		
					        	},
					        	success:function(data){
					        		var mem = eval("("+data+")");
					        		var login = mem.login;
					        		user = mem.user;
									if (login == "login") {
										document.location.href = basePath
												+ "page/WFJClient/NewLogin.jsp?loginPage=login";
										return false;
									}
					        		var result = mem.result;
					        		
					        		if(result=="0"){
					        		
					        			$(".tanshow").show();
					        			$("#bg").show();
					        			return;
					        		}
					        		
									window
											.open(
													basePath
															+ "ea/purchasebids/ea_selectMybidGoods.jspa?inviteBids.cashierBillsID="
															+ cashierBillsID
															+ "&bidsinfo.goodsID="
															+ goodsID+"&mainpid="+mainpid+"&ppid="+ppids, "_self");
								
									},error:function(data){
					        		   alert("验证失败");
					        	    }
					        	
					        });

						
						
						
					
					});
	
	//关闭提示框
	$("#bg").click(function(){
		
		$(".tanshow").hide();
		$("#bg").hide();
	});

	//返回
	$(".arrar").click(function() {
	document.location.href = basePath
				+ "ea/purchasebids/ea_viewSubProduct.jspa?ppid="
				+ mainpid + "&bidsinfo.goodsID=" + goodsID
				+ "&inviteBids.cashierBillsID=" + cashierBillsID;
		
	});
	
	//马上发布产品
	$(".tan_kuang_text_bot p").click(function(){

		open(basePath + "ea/productslaunch/ea_productsManage.jspa?user="+user,
				"_self");
	});
	


	
	//提交表单
	$(".but_tijiao")
			.click(
					function() {
					/*	if ($("#pp").val() == "") {
							alert("请添加竞标产品");
							return;
						}*/
						$("#form")
								.attr("target","hidden").attr(
										"action",
										basePath
												+ "ea/purchasebids/ea_submitEnterBids.jspa");
						document.form.submit.click();
						token = 2;
		});
	
});

function re_load() {
	document.location.href = basePath
			+ "ea/purchasebids/ea_viewSubProduct.jspa?ppid="
			+ mainpid + "&bidsinfo.goodsID=" + goodsID
			+ "&inviteBids.cashierBillsID=" + cashierBillsID;
}