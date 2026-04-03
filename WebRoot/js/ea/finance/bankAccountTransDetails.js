$(document).ready(function(){	
	$("#tosearch").click(function(){					
				var _account = $("#accountlist").find("option:selected").text().split("|");		 			
	 			$.ajax(
					{type:"POST",
					url:basePath + "ea/bankaccountTransDetails/ea_getAccountTransDetails.jspa?innerAction=accountTransDetailsQuery",
					data:{
						"bankAccount.account":_account[0],
						"bankAccount.banksx":_account[1],
						"bankAccount.provcode":_account[2],
						"bankAccount.currency":_account[3],
						"requestBean.startDate":$("#startDate").val().replace("-","").replace("-",""),
						"requestBean.endDate":$("#endDate").val().replace("-","").replace("-",""),
						innerTransCode:$("#innerTransCode").val()
					  },
					dataType:"json",
					success:function(data)
					{						
						var _totalHtml = "";
						for (var i=0;i<data.length;i++)
						{							
							var _html = "<tr>";
							_html = _html + "<td><span>" + (i+1) + "</span></td>";
							_html = _html + "<td><span>" + data[i].prov + "</span></td>";
							_html = _html + "<td><span>" + data[i].accno + "</span></td>";
							_html = _html + "<td><span>" + data[i].cur + "</span></td>";
							_html = _html + "<td><span>" + data[i].trdate + "</span></td>";
							_html = _html + "<td><span>" + data[i].timestab + "</span></td>";
							_html = _html + "<td><span>" + data[i].trjrn + "</span></td>";
							_html = _html + "<td><span>" + data[i].trtype + "</span></td>";
							_html = _html + "<td><span>" + data[i].trbankno + "</span></td>";
              _html = _html + "<td><span>" + data[i].accname + "</span></td>";
							_html = _html + "<td><span>" + data[i].amtindex + "</span></td>";
							_html = _html + "<td><span>" + data[i].oppprov + "</span></td>";
							_html = _html + "<td><span>" + data[i].oppaccno + "</span></td>";
							_html = _html + "<td><span>" + data[i].oppcur + "</span></td>";
							_html = _html + "<td><span>" + data[i].oppname + "</span></td>";
							_html = _html + "<td><span>" + data[i].oppbkname + "</span></td>";
							_html = _html + "<td><span>" + data[i].cshindex + "</span></td>";
							_html = _html + "<td><span>" + data[i].errdate + "</span></td>";
							_html = _html + "<td><span>" + data[i].errvchno + "</span></td>";
							_html = _html + "<td><span>" + data[i].amt + "</span></td>";
							_html = _html + "<td><span>" + data[i].bal + "</span></td>";
							_html = _html + "<td><span>" + data[i].preamt + "</span></td>";
							_html = _html + "<td><span>" + data[i].totchg + "</span></td>";
							_html = _html + "<td><span>" + data[i].vouchertype + "</span></td>";
							_html = _html + "<td><span>" + data[i].voucherprov + "</span></td>";
							_html = _html + "<td><span>" + data[i].voucherbat + "</span></td>";
							_html = _html + "<td><span>" + data[i].voucherno + "</span></td>";
							_html = _html + "<td><span>" + data[i].custref + "</span></td>";
							_html = _html + "<td><span>" + data[i].transcode + "</span></td>";
							_html = _html + "<td><span>" + data[i].teller + "</span></td>";
							_html = _html + "<td><span>" + data[i].vchno + "</span></td>";
							_html = _html + "<td><span>" + data[i].abs + "</span></td>";
							_html = _html + "<td><span>" + data[i].postscript + "</span></td>";
							_html = _html + "<td><span>" + data[i].trfrom + "</span></td>";
							_html = _html + "</tr>";
							_totalHtml = _totalHtml + _html;
						}
						$("table#detailTable").find("tbody").html(_totalHtml);
					},
					error : function(data) {												
						alert("系统发生异常,请联系管理员.");
					}
				}
			)	 			
	});
	
});

