$(document).ready(function() {			

		//切换银行 给参数赋值
		$("#bank").change(function() {						            
          var bankName = $(this).find("option:selected").text();
					var enameSx = $(this).find("option:selected").val();
					
          getBankParam(enameSx);                     
          $("#bankName").val(bankName);
					$("#enameSx").val(enameSx);      					
		});				
					
		//保存参数设置					
		$("#save").click(function() {			
				$.ajax(
						{
							type : "POST",
							url : basePath + "nwa/bank/nwa_getBanksFromXml.jspa?innerAction=insertBankParam",
							dataType : "json",
							data : {
									"bankLinkParam.key":$("#key").val(),
									"bankLinkParam.bpID":$("#bpID").val(),
									"bankLinkParam.bankBusiname":$("#bankBusiname").val(),
							    "bankLinkParam.bankName":$("#bankName").val(),
							    "bankLinkParam.enameSx":$("#enameSx").val(),
									"bankLinkParam.isEnabled":$("#isEnabled").val(),
									"bankLinkParam.enterpriseID":$("#enterpriseID").val(),
							    "bankLinkParam.customerCode":$("#customerCode").val(),
							    "bankLinkParam.bankID":$("#bankID").val(),
									"bankLinkParam.useCode":$("#useCode").val(),
									"bankLinkParam.loginPassword":$("#loginPassword").val(),
							    "bankLinkParam.testAccount":$("#testAccount").val(),
							    "bankLinkParam.interfaceIp":$("#interfaceIp").val(),
									"bankLinkParam.port":$("#port").val(),
									"bankLinkParam.protocol":$("#protocol").val(),
							    "bankLinkParam.path":$("#path").val()
								},
							dataType:"json",
							success:function(data)
							{
								alert("操作成功");
							}
						}
				)			
	  });
});
		
function getBankParam(enameSx) {
	$.ajax(
			{				
				type : "POST",				
				url : basePath + "nwa/bank/nwa_getBanksFromXml.jspa",
				dataType : "json",
				data : {
					"bankLinkParam.enameSx" : enameSx,
					innerAction : "getBankParam"
				},
				success : function(data) {							
					var bankLinkParam = eval(data.bankLinkParam);
					if (bankLinkParam == null) {
						document.paramForm.reset();
						$("#enameSx").val(enameSx);
						$("#bank").val(enameSx);
						return;
					}					
					
					$("#key").val(bankLinkParam.key);
					$("#bpID").val(bankLinkParam.bpID);
					$("#enterpriseID").val(bankLinkParam.enterpriseID);
					
					$("#bankBusiname").val(bankLinkParam.bankBusiname);
					$("#bankName").val(bankLinkParam.bankName);
					$("#enameSx").val(bankLinkParam.enameSx);					
					$("#isEnabled").attr("value",bankLinkParam.isEnabled);
					$("#customerCode").val(bankLinkParam.customerCode);
					$("#bankID").val(bankLinkParam.bankID);
					$("#useCode").val(bankLinkParam.useCode);
					$("#loginPassword").val(bankLinkParam.loginPassword);
					$("#testAccount").val(bankLinkParam.testAccount);
					$("#interfaceIp").val(bankLinkParam.interfaceIp);
					$("#port").val(bankLinkParam.port);
					$("#protocol").val(bankLinkParam.protocol);
					$("#path").val(bankLinkParam.path);
				},
				error : function(data) {
					alert("获取银行参数信息失败");
				}
			});

}
