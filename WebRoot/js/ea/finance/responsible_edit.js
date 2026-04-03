$(function(){
	
	if(xmtype=="043111"){
		$(".baokx").show();
	}else{
		$(".baokx").hide();
	}

	$(".checkgoods").live("click",function(){
		$(".checkgoods").removeClass("clickTrClass");
		if($(this).find("#goodsNum").attr("class")=="clickTdClass"){
			$(this).find("#goodsNum").removeClass("clickTdClass");
		}else{
			$(this).find("#goodsNum").addClass("clickTdClass");
		}
		$(this).addClass("clickTrClass");
	 });
	
   
	//菜单

	if(status=="00"||status=="11"){
		  $(".audit").attr("disabled",false).removeClass("grey");
		  $(".updatesheet").attr("disabled",false).removeClass("grey");
	}else{
		  $(".audit").attr("disabled",true).addClass("grey");
		  $(".updatesheet").attr("disabled",true).addClass("grey");
	}
   
   	if(billsType=="项目收入预算单"){
		$(".generate").attr("disabled",false).removeClass("grey");
	}
   	if(sta=="view"){
   		$(".menubtn").attr("disabled",true).addClass("grey");
   		$(".closewindow").attr("disabled",false).removeClass("grey");
   	}
   

   
     //打印
	 $(".print").click(function(){
		 window.open(basePath
					+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
					+ cashierBillsID);
		
	 });
	 
     //关闭
	 $(".closewindow").click(function(){
		 if(confirm("确定要关闭添加窗口？")){
			 self.opener.location.reload(); //刷新父窗口
			 window.close();
		 }
		 
	 });
	 
	 //生成
	 $(".generate").click(function(){
		 var str="";
		 var num=0;
		 var tsnum=0;
		 var targetSalerID="";
		 $(".clickTdClass").each(function(){
			 var fm=$(this).find("#futureMoney").val();
			 if(targetSalerID==""){
				 targetSalerID=$(this).find("#targetSalerID").val();
			 }else{
				 if(targetSalerID!=$(this).find("#targetSalerID").val()){
					 tsnum=1;
				 }
			 }
			 if(fm==0||fm.indexOf("-")!=-1){
				 num=1;
			 }
			 str=str+$(this).find("#goodsBillsID").val()+",";
		 });
		 if(tsnum){
			 alert("选择的物品必须是同一个目标责任人！");
			 return;
		 }
		 if(num){
			 alert("您选择的物品中有的超出预算！");
			 return;
		 }
		 if(str==""){
			 alert("请选择物品");
			 return;
		 }
		 $("#jqModelbill").jqmShow();
	 });
	 
	 $("#billname").click(function(){
		 var str="";
		 $(".clickTdClass").each(function(){
			 str=str+$(this).find("#goodsBillsID").val()+",";
		 });
		 if(str==""){
			 alert("请选择物品");
			 return;
		 }
		 var text="";  
        $("input[name=btnname]").each(function() {  
            if ($(this).attr("checked")) {  
                text += $(this).val()+",";  
            }  
        });
        $("#billtname").val(text);
        $("#CostSheetForm").attr("target","hidden")
			.attr("action",
					basePath+"/ea/splitbill/ea_generateNewBill.jspa?caid="+str);
		 document.CostSheetForm.submit.click();
		 token = 2;
	 });
	 
   
	
	//为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
	       modal: true,// 
	       overlay: 20 // 
	}).jqmAddClose('.close');//
	       
	//返回按钮
	$(".JQueryClose").click(function(){
		if(toSee=="history"){
			history.back();
		}else{
        re_load();
		}
    });
    
	$(".accessoriesUrl").click(function(){
	    var accessoriesUrl=$.trim($("#accessoriesUrl").attr("value"));   
	    OpenWord(accessoriesUrl,3);
	});


	getProjectByParent(proID,cashierBillsID);

	
	function getProjectByParent(ppID,cashierBillsID){
		$("#xmstbl").html("");
		var url = basePath+"/ea/splitbill/sajax_ea_getProductAndGoodsAjax.jspa?date="
					+ new Date().toLocaleString();
		$.ajax({
			url : url,
			type : "get",
			async : false,
			dataType : "json",
			data:{
			  "cuid":ppID,
			  "caid":cashierBillsID
			},
			success : function (data) {
				var member = eval("(" + data + ")");
				var oList = member.productList;
				var data1 = new Array();
				for (var i = 0; i < oList.length; i++) {
					data1[i] = {
							id : oList[i][0],
							pid : oList[i][1],
							text : oList[i][2]
					};
				}
				var ts = new TreeSelector($("ul#xmul"),data1, null);
				ts.createTree();
				$("#xmul").treeview({
					persist: "location",
		            collapsed: false,
		            unique: false
		        });
				var goodsList=member.goodsList;
				var goodsnum=0;
				for ( var i = 0; i < oList.length; i++) {
					var goodstr = "";
					for ( var j = 0; j < goodsList.length; j++) {
						if(oList[i][0]==goodsList[j].ppID){
							var goodsbill=goodsList[j];
							goodstr+="<tr id='kelong"+goodsnum+"' class='checkgoods'>"+
										"<!-- 产品编号 -->"+
										"<td align='center' id='goodsNum'>"+
										"<input type='hidden' value='"+goodsbill.goodsBillsID+"' name='goodsBillsVO.goodsBillsID' id='goodsBillsID'/>"+
										"<input type='hidden' value='"+goodsbill.alreadyMoney+"' id='alreadyMoney'/>"+
										"<input type='hidden' value='"+goodsbill.futureMoney+"' id='futureMoney'/>"+
										"<input type='hidden' value='"+goodsbill.targetSalerID+"' id='targetSalerID'/>"+
										"<span>"+goodsbill.goodsNum+"</span>"+
										"</td>"+
										"<!-- 产品名称 -->"+
										"<td align='center'>"+
										"<span>"+goodsbill.goodsName+"</span>"+
										"</td>"+
										"<!-- 目标起时间 -->"+
										"<td align='center'>"+
										"<span>"+goodsbill.targetStart+"</span>"+
										"</td>"+
										"<!-- 目标止时间 -->"+
										"<td height='30' align='center'>"+
										"<span>"+goodsbill.targetEnd+"</span>"+
										"</td>"+
										"<!-- 报开学号 -->"+
										"<td class='baokx' align='center'>"+
										 "<span>"+goodsbill.studentCode+"</span>"+
										"</td>"+
										"<!-- 学员期数 -->"+
										"<td class='baokx'  height='30' align='center'>"+
										"<span>"+goodsbill.studentPeriods+"</span>"+
										"</td>"+
										"<!-- 报开学时间 -->"+
										"<td class='baokx'  height='30' align='center'>"+
										"<span>"+goodsbill.schoolopendate+"</span>"+
										"</td>"+
										"<!-- 单位 -->"+
										"<td align='center'>"+
										"<span id='goodsVariableID'>"+goodsbill.goodsVariableID+"</span>"+
										"</td>"+
										"<!-- 数量 -->"+
										"<td align='left'>"+
										"<span>"+goodsbill.quantity+"</span>"+
										"</td>"+
										"<td align='center'>"+
										"<span>"+goodsbill.priceManage+"</span>"+
										"</td>"+
										"<!-- 单价 -->"+
										"<td align='center'>"+
										"<span>"+goodsbill.price+"</span>"+
										"</td>"+
										"<!-- 总金额 -->"+
										"<td align='center'>"+
										"<span>"+goodsbill.money+"</span>"+
										"</td>"+
										"<td align='left'>"+
										"<span>"+goodsbill.targetDeptName+"</span>"+
										"</td>"+
										"<td align='left'>"+
										"<span>"+goodsbill.targetSalerName+"</span>"+
										"</td>"+
										"<td align='left'>"+
										"<span>"+goodsbill.ccompanyName+"</span>"+
										"</td>"+
										"<td align='left'>"+
										"<span>"+goodsbill.connectName+"</span>"+
										"</td>"+
										"<td align='center'>无</td>"+
									"</tr>";
							goodsnum++;
						}
					}
					if(goodstr!=""){
						goodstr="<table id='"+oList[i][0]+"tbl' class='datatbl'><tr><td><table class='table bg auto_arrange .goodtable2' id='goodtable2'>" +
						"<tr>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px' >" +
						"<span class='resizeDivClass' onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='14' /> </span>品名编号" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='14' /> </span>品名名称" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img style='vertical-align:middle;'" +
						" src='<%=basePath%>/images/header_bg.gif' width='5'" +
						" height='14' /> </span>目标起时间" +
						"</th>" +
						" <th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='3'" +
						" height='14' /> </span> 目标止时间" +
						" </th>" +
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>"+
						"<span class='resizeDivClass'"+
						" onMouseDown='MouseDownToResize(this);'"+
						" onMouseMove='MouseMoveToResize(this);'"+
						" onMouseUp='MouseUpToResize(this);'><img"+
						"	src='<%=basePath%>+/images/header_bg.gif' width='1'"+
						"	height='14' /></span>报开学号"+
						"</th>"+
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='80px'>"+
						"<span class='resizeDivClass'"+
						" onMouseDown='MouseDownToResize(this);'"+
						" onMouseMove='MouseMoveToResize(this);'"+
						" onMouseUp='MouseUpToResize(this);'><img"+
						"	src='<%=basePath%>+/images/header_bg.gif' width='1'"+
						"	height='14' /></span>学员期数"+
						"</th>"+
						"<th class='baokx' align='center' bgcolor='#E4F1FA' width='90px'>"+
						"<span class='resizeDivClass'"+
						" onMouseDown='MouseDownToResize(this);'"+
						" onMouseMove='MouseMoveToResize(this);'"+
						" onMouseUp='MouseUpToResize(this);'><img"+
						"	src='<%=basePath%>+/images/header_bg.gif' width='1'"+
						"	height='14' /></span>报开学时间"+
						"</th>"+
						" <th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='3'" +
						" height='14' /> </span>单位" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='3'" +
						" height='14' /> </span> 数量" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='3'" +
						" height='14' /> </span>单价管理" +
						"</th>" +
						" <th align='center' bgcolor='#E4F1FA' width='90px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='3'" +
						" height='14' /> </span>单价" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='5'" +
						" height='14' /> </span>预算金额" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='14' /> </span>目标部门" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='80px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='1'" +
						" height='14' /> </span>目标业务员" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='180px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='5'" +
						" height='14' /> </span>往来公司" +
						"</th>" +
						"<th align='center' bgcolor='#E4F1FA' width='70px'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" src='<%=basePath%>/images/header_bg.gif' width='5'" +
						" height='14' /> </span>往来个人" +
						"</th>" +
						"<th align='center' width='50px' bgcolor='#E4F1FA'>" +
						"<span class='resizeDivClass'" +
						" onMouseDown='MouseDownToResize(this);'" +
						" onMouseMove='MouseMoveToResize(this);'" +
						" onMouseUp='MouseUpToResize(this);'><img" +
						" src='<%=basePath%>/images/header_bg.gif' width='5'" +
						" height='14' /> </span> 操作" +
						"</th></tr>"+goodstr;
						goodstr+="</table>";
						$("li#"+oList[i][0]).find("span").eq(0).after(goodstr);
					}
				}
			},error:function(data){
				alert("获取项目失败");
			}
			});
	}
});

function re_load(){
	if(token){
		self.opener.location.reload(); //刷新父窗口
		 window.close();
	}	
}