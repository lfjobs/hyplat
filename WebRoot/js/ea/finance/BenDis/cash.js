
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
		if(str=="01"){
			alert("订单号为："+str2+"的物品库存不足");
		}
		var refund="";
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
			// 遮罩程度%
			}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			var name;
		if (pl=='zk'){
				name = '支款单管理';
			}else{
				name = '订单管理';
			}
			
			var titletext="<form method='post' name='SearchForm' id='SearchForm'>" +
						"&nbsp;&nbsp;用户名查询:<input type='text' id='zhi' size='10' name='nameSearch'/>" +
						"&nbsp;&nbsp;订单编号查询:<input type='text' id='zhi' name='inforType'/>" +
						"&nbsp;&nbsp;单据生成时间:<input type='text' size='10' name='sdate' onfocus='date(this)'/>" +
						"&nbsp;-&nbsp;<input type='test' size='10' name='edate' onfocus='date(this)'/>" +
						"<input type='submit' style='display:none;' name='submit' />" +
						"<input type='hidden' name='search' value='search'/>" +
						"&nbsp;&nbsp;<input type='button' value='查询' id='tosearch'/>" +
						"<input type='hidden' name='zzorder' value='"+zzorder+"'/>" +
						"</form>";

					var s="";
					if (pl=='sk'){
						s = basePath+"ea/bdbill/ea_skd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
					}else if(pl=='zk'){
						s = basePath+"ea/bdbill/ea_getskd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
					}
				if(pl!=null&&(pl=="zk"||pl=="sk")) {
					$('.address').flexigrid({
						height: 445,
						width: 'auto',
						minwidth: 30,
						title: name + titletext,
						minheight: 80,
						buttons: [{name: '查看', bclass: 'edit', onpress: action}, {separator: true},
							{name: '打印', bclass: 'printer', onpress: action}, {separator: true},
							{name: 'EXCEL导出', bclass: 'edit', onpress: action}, {separator: true},
							{name: '设置每页显示条数', bclass: 'mysearch', onpress: action}, {separator: true}]
					});
				}
			function action(com, grid) {
				switch (com) {
				case '查看':		
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}
					var ulr="/ea/bdbill/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit&type=pc";
					//var ulr="ea/phonebill/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001";
					window.open(basePath+ulr);
					break;
				case '打印预览':
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}
					 $("#jqModelpj").jqmShow();

					break;
				case '打印':
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}
                    var billtype=$("#"+clientPositioningID).find(".billtype").val();
					if(billtype=="积分入库单"){
                        window.open(basePath
                            + "ea/bdbill/ea_toprintCashier.jspa?cassid="+ clientPositioningID);
                    }else {
                        window.open(basePath
                            + "/ea/printcashierbills/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
                            + clientPositioningID);
                    }
					break;
				case '设置每页显示条数':					
					numback(s);
					break;
				case 'EXCEL导出':
					window.location.href=basePath+"ea/bdbill/ea_exportExcelTable.jspa?pl="+pl+"&hylb="+hylb+"&pl="+pl+"&sdate="+sdate+"&edate="+edate+"&iisnull="+iisnull+"&nameSearch="+nameSearch+"&inforType="+inforType;
				}
			}
			$("div.bDiv",$("div.flexigrid")).css("height","350px");
			$("#tosave").click(function(){
				var a=true;
				$(".novice").each(function(){
					if($(this).val().trim()==null||$(this).val().trim()==""){
						a=false;
					}
				});
				if(a){
					$("#addForm").attr("target", "hidden").attr("action",basePath +"ea/bdbill/ea_addNovice.jspa?hylb="+hylb+"&pl="+pl);
					token=2;
					document.addForm.submit.click();
				}else{
					alert("学员信息均需填写！");
				}
			});
			
			$("#tosearch").click(function() {
				var a="";
				$("#SearchForm").attr("action",s +"&vuvtype=edit");
				document.SearchForm.submit.click();
			});
			
			$("#printvsk").click(function(){
				var radioType=$("input[name='pj']:checked").val();
					window.open(basePath
							+ "/ea/cashiersummary/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
							+ clientPositioningID+"&radioType="+radioType);
			});
			//获取id			
			$(".address tr[id]").click(
					function() {
						clientPositioningID = this.id; 
						$("input.JQuerypersonvalue", $(this)).attr("checked","checked");
					});
			


		});

function ajaxtoyjfp(caid){
	var url = basePath+"/ea/bdbill/sajax_toDistribution.jspa?cashierBills.cashierBillsID="+caid;
    $.ajax({
         url:url,
         type:"get",
         async:false,
         dataType:"json",
         success:function(data){
         	var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			var str = member.str;
			if(str=="提交成功"){
				$("tr#"+caid).remove();
			}
			alert(str);
         },error:function(data){
          	alert("操作失败！");
         }
     });

}

//获取单据真正状态status
function getCurrentStatus(){
	var url = basePath
	+ "/ea/bdbill/sajax_ea_getCurrentStatus.jspa?date="
	+ new Date().toLocaleString();
   $.ajax({
    url : encodeURI(url),
    type : "post",
    async : false,
     dataType : "json",
     data : {
	  "cashierBills.cashierBillsID" : clientPositioningID
    },
    success : function cbf(data) {
		var member = eval("(" + data + ")");
		var status = member.result;
		statuscurr = status;
	
	    },error : function cbf(data) {
		      alert("获取状态失败！");
	    }
   });
}

function re_load() {
 if (pl=='sk'){
		window.location.href =  basePath+"ea/bdbill/ea_skd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
	}else if(pl=='zk'){
        window.location.href =  basePath+"ea/bdbill/ea_getskd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
	}
}
