window.onload=function(){
	$(".tableOuter").css("width",$(".tableOuter").width()+"px");
	$(".tableInner").css("width",($(".tableOuter").width()+17)+"px");
	$(".tableOuter").find("table").css("width",$(".tableOuter").width()+"px");
};
$(function(){
	if($("#productID").val()!=""){
		$.ajax({
			url:basePath+"ea/setpro/sajax_ea_productMix.jspa?productionAmount.productID="+$("#productID").val(),
			type : "post",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var input="";
					if(i==0)
						input="<input type='text' style='border:0px;width:100%;height:100%;' class='quantity' name='productionAmount.amount' value='"+amount+"'>";
					else
						input=parseInt(list[i][3])*parseInt(amount);
					console.log(list[i][3]+" - - "+amount);
					var tr="<tr class='packaging'><td width='320px'>";
					 console.log(list[i][5]);
					for(r=0;r<parseInt(list[i][5]==null?'0':list[i][5])+1;r++){
						tr+="&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					tr+="--"+list[i][0]+"</td><td width='200px'>"+list[i][4]
					+"</td><td width='170px'>"+list[i][3]+"</td><td width='169px'>"+input+"</td></tr>";
					$("#tableBody").find("tbody").append(tr);
				}
			},
			errot:function(data){
				alert("数据获取失败");
			}
		});
	}
	
	$(".quantity").live("input propertychange",function(){
		var zz=/^[0-9]*$/;
		if(zz.test($(this).val())&&$(this).val()!=""){
			$(this).removeClass("error").css("backgroundColor","#ffffff");
			var quantity=$(this).val();
			 $("#tableBody").find("tr").each(function(index){
				 if(index!=0)
				$(this).find("td").eq(3).text(quantity*$(this).find("td").eq(2).text());
			 });
		}else{
			$(this).addClass("error").css("backgroundColor","red");
		}
	});
	
	
	//选择往来个人
	$("#staffsName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	//添加选中的往来个人
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			getStaffMember("selectDept="+orgId+"&parameter="+$("#searchdept").find("#parameterrm").val());
		}else if($(this).val()=="确定"){
			$("#staffsName").val($("#"+staffId).find("#staffname").text());
			$("#staffsID").val(staffId);
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	$("tr[id]").live("click",function(){
		if($(this).attr("class")=="staff"){
			staffId=this.id;
			$(this).find(".radio").attr("checked","checked");
		}
	});
	
	$("#goodsName").click(function(){
		$.ajax({
			url:basePath+"ea/setpro/sajax_ea_productList.jspa?category="+category+"&fiveClear="+fiveClear,
			type : "post",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var tr="<tr id='"+list[i].ppID+"' name='product'  style='height:20px;' class='productTr'>";
					tr+="<td width='"+$("#ttr").find("th").eq(0).css("width")+"px'><input type='radio' class='radio'  name='productRadio' id="+list[i].ppID+"></td>";
					tr+="<td width='"+$("#ttr").find("th").eq(1).css("width")+"px'>"+list[i].tradeCode+"</td>";
					tr+="<td width='"+$("#ttr").find("th").eq(2).css("width")+"px'>"+list[i].goodsName+"</td>";
					tr+="<td width='"+$("#ttr").find("th").eq(3).css("width")+"px'>"+list[i].productCode+"</td>";
					tr+="<td width='"+$("#ttr").find("th").eq(4).css("width")+"px'>"+list[i].subjectName+"</td>";
					tr+="<td width='"+$("#ttr").find("th").eq(5).css("width")+"px'>"+list[i].price+"</td>";
					tr+="<td width='"+$("#ttr").find("th").eq(6).css("width")+"px'>"+list[i].remark+"</td>";
					tr+="</tr>";
					$("#productTable").find("tbody").append(tr);
				}
			},
			error:function(data){
				alert("获取数据失败");
			}
		});
		$("#productCoatings").show();
	});
	$("#close").click(function(){
		$(".productTr").remove();
		$("#productCoatings").hide();
	});
	$("#determine").click(function(){
		if(productID==""){
			alert("请选择");
			return;
		}
		$("#goodsName").val($("#"+productID).find("td").eq(2).text());
		$("#productID").val(productID);
		$(".packaging").remove();
		$.ajax({
			url:basePath+"ea/setpro/sajax_ea_productMix.jspa?productionAmount.productID="+productID,
			type : "post",
			async : false,
			dataType : "json",
			success : function (data) {
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var input="";
					if(i==0)
						input="<input type='text' style='border:0px;width:100%;height:100%;' class='quantity' name='productionAmount.amount'>";
					var tr="<tr class='packaging'><td width='320px'>";
					 console.log(list[i][5]);
					for(r=0;r<parseInt(list[i][5]==null?'0':list[i][5])+1;r++){
						tr+="&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					tr+="--"+list[i][0]+"</td><td width='200px'>"+list[i][4]
					+"</td><td width='170px'>"+list[i][3]+"</td><td width='169px'>"+input+"</td></tr>";
					$("#tableBody").find("tbody").append(tr);
				}
			},
			errot:function(data){
				alert("数据获取失败");
			}
		});
		$(".productTr").remove();
		$("#productCoatings").hide();
	});
	$("tr[id]").live("click",function(){
		if($(this).attr("name")=="product"){
			productID=this.id;
			$(this).find(".radio").attr("checked","checked");
		}
	});
	$(".operation").click(function(){
		if($(this).val()=="提交"){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/setpro/ea_addOrModify.jspa");
			document.form.submit.click();
			token = 2;
		}else{
			window.close();
		}
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
});


/*-------------------------------------------------------------------------人员选择-----------------------------------------------------------------------------*/
function getStaffMember(typeCN) {
	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	$.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";
					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}
					$("span#dpzycount").text(zys);
					for ( var i = 0; i < pageForm.list.length; i++) {
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";
					}
					$("#body_02dept").html(tabletr);
				},
				error : function cbf(data) {
					notoken = 0;
				}
			});
}
function re_load(){
	window.opener.location.href=window.opener.location.href;
	window.close();
}