

$(function(){
	//归档
	$(".theLibrary").click(function(){
		if(status=="add"){
			if($(this).val()=="确定"){
				if($(".error").attr("id")||!$("#goodsName").val()){
					alert("产品有错误信息");
					return;
				}
				if(!confirm("确定提交"))
					return;
				var url=basePath+"ea/production/ea_addProductStorageList.jspa";
				$("#goodsform").attr("target", "hidden").attr("action",url);
				document.goodsform.goodssubmit.click();
				token = 2; 
			}else{
				window.close();
			}
		}else if(status=="edit"){
			if($(this).val()=="确定"){
				if($(".error").attr("id"))
					return;
				if(!confirm("确定修改"))
					return;
				var url=basePath+"ea/production/ea_editProductStorageList.jspa?utboundOrderVo.cashierbillsid="+id+"&number="+dtnumber;
				$("#goodsform").attr("target", "hidden").attr("action",url);
				document.goodsform.goodssubmit.click();
				token = 2;
			}else{
				$(".fieldContent").find("#goodsName").attr("disabled","");
				$(".fieldContent").find("#number").attr("disabled","disabled");
				$(".fieldContent").find("input").val("");
				$("#single").jqmHide();
			}
		}
	});
	
	//选择物品
	$("#goodsName").click(function(){
		$(".goods").remove();
		$.ajax({
			url:basePath+"ea/production/sajax_ea_ajaxGetDocumentsForInspection.jspa?category="+category+"&fiveClear="+fiveClear,
			type:"post",
			async : false,
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var tr=$("#sampleTr").clone(true).attr("style","").attr("id",list[i][0]+"list").addClass("goods");
					var td="<input type='radio' class='radio' name='goodsRadio' id='"+list[i][0]+"'><input type='hidden' class='gId' value='"+list[i][1]+"'>" +
					"<input type='hidden' class='dcheckid' value='"+list[i][13]+"'>";
					tr.find("td").eq(0).html(td);
					tr.find("td").eq(1).html("<span>"+(list[i][2]==null?"":list[i][2])+"</span>");
					tr.find("td").eq(2).html("<span>"+(list[i][3]==null?"":list[i][3])+"</span>");
					tr.find("td").eq(3).html("<span>"+(list[i][4]==null?"":list[i][4])+"</span>");
					tr.find("td").eq(4).html("<span>"+(list[i][5]==null?"":list[i][5])+"</span>");
					tr.find("td").eq(5).html("<span>"+(list[i][6]==null?"":list[i][6])+"</span>");
					tr.find("td").eq(6).html("<span>"+(list[i][7]==null?"":list[i][7])+"</span>");
					tr.find("td").eq(7).html("<span>"+(list[i][8]==null?"":list[i][8])+"</span>");
					tr.find("td").eq(8).html("<span>"+(list[i][9]==null?"":list[i][9])+"</span>");
					tr.find("td").eq(9).html("<span>"+(list[i][10]==null?"":list[i][10])+"</span><input type='hidden' class='yId' value='"+list[i][11]+"'>");
					tr.find("td").eq(10).html("<span>"+(list[i][12]==null?"":list[i][12])
					+"</span><input type='hidden' class='goodsType' value="+list[i][14]+">" 
					+"<input type='hidden' class='cashierBillsID' value="+list[i][15]+">");
					$("#tbodya").append(tr);
				}
			},error:function(data){
				alert("获取数据失败");
			}
		});
		
		$("#occlusion2").css("z-index",$("#single").css("z-index")+1);
		$("#goodsjqModel").css("z-index",$("#single").css("z-index")+2);
		$("#occlusion2").jqmShow();
		$("#goodsjqModel").jqmShow();
	});
	$(".goodsButton").click(function(){
		if($(this).val()=="查询"){
			$(".goods").remove();
			$.ajax({
				url:basePath+"ea/production/sajax_ea_ajaxGetDocumentsForInspection.jspa?parameter="+$("#parameter").val()
				+"&industry="+$("#industry  option:selected").val()+"&product="+$("#product   option:selected").val()
				+"&category="+category+"&grType="+grType+"&fiveClear="+fiveClear,
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.list;
					for(var i=0;i<list.length;i++){
						var tr=$("#sampleTr").clone(true).attr("style","").attr("id",list[i][0]+"list").addClass("goods");
						var td="<input type='radio' class='radio' name='goodsRadio' id='"+list[i][0]+"'><input type='hidden' class='gId' value='"+list[i][1]+"'>" +
						"<input type='hidden' class='dcheckid' value='"+list[i][13]+"'>";
						tr.find("td").eq(0).html(td);
						tr.find("td").eq(1).html("<span>"+(list[i][2]==null?"":list[i][2])+"</span>");
						tr.find("td").eq(2).html("<span>"+(list[i][3]==null?"":list[i][3])+"</span>");
						tr.find("td").eq(3).html("<span>"+(list[i][4]==null?"":list[i][4])+"</span>");
						tr.find("td").eq(4).html("<span>"+(list[i][5]==null?"":list[i][5])+"</span>");
						tr.find("td").eq(5).html("<span>"+(list[i][6]==null?"":list[i][6])+"</span>");
						tr.find("td").eq(6).html("<span>"+(list[i][7]==null?"":list[i][7])+"</span>");
						tr.find("td").eq(7).html("<span>"+(list[i][8]==null?"":list[i][8])+"</span>");
						tr.find("td").eq(8).html("<span>"+(list[i][9]==null?"":list[i][9])+"</span>");
						tr.find("td").eq(9).html("<span>"+(list[i][10]==null?"":list[i][10])+"</span><input type='hidden' class='yId' value='"+list[i][11]+"'>");
						tr.find("td").eq(10).html("<span>"+(list[i][12]==null?"":list[i][12])+"</span>" 
						+"<input type='hidden' class='goodsType' value="+list[i][14]+">" 
						+"<input type='hidden' class='cashierBillsID' value="+list[i][15]+">");
						$("#tbodya").append(tr);
					}
				},error:function(data){
					alert("获取数据失败");	
				}
			});
		}else if($(this).val()=="确定"){
			if(goodsId==""){
				alert("请选择！");
				return;
			}
			$(".productNumber").val($("#"+goodsId).parents("tr").find("span").eq(2).text());
			$(".goodsName").val($("#"+goodsId).parents("tr").find("span").eq(1).text());
			$(".goodsHidden").val($("#"+goodsId).parents("tr").find("span").eq(1).text());
			$(".goodsId").val($("#"+goodsId).parents("tr").find(".gId").val());
			$(".goodsDcheckid").val($("#"+goodsId).parents("tr").find(".dcheckid").val());
			$(".number").val($("#"+goodsId).parents("tr").find("span").eq(6).text());
			$(".typeName").val($("#"+goodsId).parents("tr").find("span").eq(3).text());
			$(".ppId").val(goodsId);
			$(".price").val($("#"+goodsId).parents("tr").find("span").eq(5).text());
			$(".specifications").val($("#"+goodsId).parents("tr").find("span").eq(4).text());
			$(".acceptanceName").val($("#"+goodsId).parents("tr").find("span").eq(8).text());
			$(".examineDate").val($("#"+goodsId).parents("tr").find("span").eq(9).text());
			$(".acceptanceId").val($("#"+goodsId).parents("tr").find(".yId").val());
			$(".utType").val($("#"+goodsId).parents("tr").find(".goodsType").val());
			$(".utCashi").val($("#"+goodsId).parents("tr").find(".cashierBillsID").val());
			dtnumber=parseInt($("#"+goodsId).parents("tr").find("span").eq(6).text());
			
			$("#number").attr("readonly","").css("borderBottom","1px solid #AEADA7");
			$("#occlusion2").jqmHide();
			$("#goodsjqModel").jqmHide();
		}else{
			$("#occlusion2").jqmHide();
			$("#goodsjqModel").jqmHide();
		}
	});
	
	//根据数量改变总价
	$("#number").live("input propertychange",function(){
		var zz=/^\d*$/;
		if(zz.test($(this).val())&&$(this).val()!=""){
			if($(this).val()<=dtnumber){
				$("#number").attr("style","width:130px;").removeClass("error");
			}else{
				alert("所填数量超过物品原有数量！");
				$("#number").attr("style","width:100%;background-color: red;").addClass("error");
			}
		}else{
			$("#number").attr("style","width:100%;background-color: red;").addClass("error");
		}
	});
	
	$(".dps").each(function(){
		var str=$(this).find("span").eq(11).text().split(" ");
		$(this).find("span").eq(11).text(str[0]);
	});
	
	//每行的点击事件
	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("name")=="goods")
			goodsId=$(this).find(".radio").attr("id");
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20// 
	}).jqmAddClose('.close');
});

function re_load(){
	window.opener.location.href = window.opener.location.href; 
	window.close();}