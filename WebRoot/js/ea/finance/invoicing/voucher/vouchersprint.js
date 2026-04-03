$(function(){
	var voucherID=$(".cherid").val();
	$input=$(".cherid");
	$.ajax({
		url:basePath + "/ea/voucher/sajax_ea_toPrint.jspa",
		type:"post",
		data:{"voucherID":voucherID},
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			var debit=0; 
			var credit=0; 
			var ir=0;
			for(var i=0;i<list.length;i++){
				if(i>4){
					if(i%5==0){
						var div=$input.parents("div").clone(true);
						div.find("table").find("tr.trs").remove();
						var ul=div.find("table").find("tr").find("td.u1").text();
						div.find("table").find("tr").find("td.u1").text(ul[1]+"/"+(ir+2));
						if(ir!=0){
							$input.parents("div").nextAll(".div"+(ir-1)).after(div.addClass("div"+ir));
						}else{
							$input.parents("div").after(div.addClass("div"+ir));
						}
						ir++;
					}
					var tr="<tr class='trs' height='65'><td style=\"font-size: 20px;\">"+list[i].subjectsname+"</td>" +
					   "<td class='td_money1' align='right' style=\"font-size: 20px;\">"+(list[i].direction=='D'?$.fAmount(list[i].standardmoney,2):'')+"</td>" +
					   "<td class='td_money2' align='right' style=\"font-size: 20px;\">"+(list[i].direction=='C'?$.fAmount(list[i].standardmoney,2):'')+"</td>" +
					   "<td style=\"font-size: 20px;\">"+list[i].reasonthing+"</td></tr>";
					$input.parents("div").nextAll(".div"+(ir-1)).find("table.tab").append(tr);
				}else{
					var tr="<tr class='trs' height='65'><td style=\"font-size: 20px;\">"+list[i].subjectsname+"</td>" +
					   "<td class='td_money1' align='right' style=\"font-size: 20px;\">"+(list[i].direction=='D'?$.fAmount(list[i].standardmoney,2):'')+"</td>" +
					   "<td class='td_money2' align='right' style=\"font-size: 20px;\">"+(list[i].direction=='C'?$.fAmount(list[i].standardmoney,2):'')+"</td>" +
					   "<td style=\"font-size: 20px;\">"+list[i].reasonthing+"</td></tr>";
					$input.parents("tr").after(tr);
					list[i].direction=='D'?debit+=list[i].standardmoney:credit+=list[i].standardmoney;
				}	
			};
			if(list.length%5!=0){
				var vr=5-(list.length%5);
				for(var i=0;i<vr;i++){
					if(ir==0){
						var tr="<tr height='65'><td></td><td></td><td></td><td></td></tr>";
						$input.parents("table").append(tr);
					}else{
						var tr="<tr height='65'><td></td><td></td><td></td><td></td></tr>";
						$input.parents("div").nextAll(".div"+(ir-1)).find("table.tab").append(tr);
					}					
				}			
			}
			$(".money1").each(function(){
				var debit=0.0;
				var credit=0;
				$(this).parents("div").find("tr.trs","table.tab").find(".td_money1").each(function(){
					if($(this).text())
					debit+=parseFloat(commafyback($(this).text()));			
				});
				$(this).parents("div").find("tr.trs","table.tab").find(".td_money2").each(function(){
					if($(this).text())
					credit+=parseFloat(commafyback($(this).text()));
				});
				$(this).text($.fAmount(debit,2));
				$(this).parents("tr").find("td.money2").text($.fAmount(credit,2));
			});	
		},
		error: function(data){
			alert(">>>");
		}
	});
});




function commafyback(num){
	var s=num.split(",");
	var vr="";
	for(var i=0;i<s.length;i++){
		vr+=s[i];
	}
	return vr;
}