
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector

		var refund="";
			$(".jqmWindow").jqm({
				modal : true,// 限制输入（鼠标点击，按键）的对话
				overlay : 20
			// 遮罩程度%
			}).jqmAddClose('.close');// 添加触发关闭的selector
			// .jqDrag('.drag');// 添加拖拽的selector
			var name;
			if(pl=='pl'){
				name = '批量发货管理';
			}else if (pl=='fl'){
				name = '金币分配明细管理';
			}else if (pl=='sk'){
				name = '收款单管理';
			}else if (pl=='zk'){
				name = '支款单管理';
			}else{
				name = '订单管理';
			}
			
			var titletext="<form method='post' name='SearchForm' id='SearchForm'>" +
						"&nbsp;&nbsp;用户名查询:<input type='text' id='zhi' size='10' name='nameSearch'/>" +
						"&nbsp;&nbsp;订单编号查询:<input type='text' id='zhi' name='inforType'/>" +
						"&nbsp;&nbsp;单据生成时间:<input type='text' size='10' name='sdate' onfocus='date(this)' id='sdates'/>" +
						"&nbsp;-&nbsp;<input type='test' size='10' name='edate' onfocus='date(this)' id='edates'/>" +
						"<input type='submit' style='display:none;' name='submit' />" +
						"<input type='hidden' name='search' value='search'/>" +
						"&nbsp;&nbsp;<input type='button' value='查询' id='tosearch'/>" +
						"<input type='hidden' name='zzorder' value='"+zzorder+"'/>" +
						"</form>";
		
			var s="";
			if(pl=='pl'){
				s = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl;
			}else if (pl=='fl'){
				s = basePath+"/ea/bdbill/ea_getjbfpmx.jspa?hylb="+hylb+"&pl="+pl;
			}else if (pl=='sk'){
				s = basePath+"ea/bdbill/ea_skd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
			}else if(pl=='zk'){
                s = basePath+"ea/bdbill/ea_getskd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
            }else{
				s = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl+"&zzorder="+zzorder;
				
				titletext="<form method='post' name='SearchForm' id='SearchForm'>" +
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单编号查询:<input type='text' id='zhi' name='inforType'/>" +
						"&nbsp;&nbsp;单据生成时间:<input type='text' size='10' name='sdate' onfocus='date(this)' id='sdates'/>" +
						"&nbsp;-&nbsp;<input type='test' size='10' name='edate' onfocus='date(this)' id='edates'/>" +
						"<br /><div id='div' style='height:3px'></div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户名查询:&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' name='nameSearch' />" +						
						"&nbsp;&nbsp;订单状态:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name='fk' style='width:195px'><option value=''>请选择 </option> <option value='00'>已付款未发货 </option><option value='01'>未付款</option>"+
						"<option value='02'>已出库正在物流</option><option value='03'>用户已收货</option><option value='04'>已分配金币</option>"+
						"<option value='05'>申请退货</option>	<option value='06'>同意退货</option>"+
						"<option value='07'>退货中 </option><option value='08'>买家退货，卖家确认收货</option>"+
						"<option value='09'>转账确认 </option><option value='10'>申请售后 </option>"+
						"<option value='11'>同意售后 </option><option value='12'>售后中 </option>"+
						"<option value='13'>售后成功</option></select>"+
						"<input type='submit' style='display:none;' name='submit' />" +
						"<input type='hidden' name='search' value='search'/>" +
						"&nbsp;&nbsp;<input type='button' value='查询' id='tosearch'/>" +
						"<input type='hidden' name='zzorder' value='"+zzorder+"'/>" +
						"</form>";
			}

			if(pl!=null&&pl=="pl"){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           //{name : '一键发货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '发货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true},
                        {name : '打印发货单预览',bclass : 'printer',onpress : action},{separator : true}]
				});
			}else if(pl!=null&&pl=="fl"){
				if(companyID=="company201009046vxdyzy4wg0000000025"){
					$('.address').flexigrid({
						height : 445,
						width : 'auto',
						minwidth : 30,
						title : name+titletext,
						minheight : 80,
						buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
						           {name : '查看金币分配明细',bclass : 'edit',onpress : action},{separator : true},
						           {name : '确认分配',	bclass : 'mark',onpress : action},{separator : true},
						           {name : '打印预览',bclass : 'printer',onpress : action},{separator : true},
						           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
					});
				}else{
					$('.address').flexigrid({
						height : 445,
						width : 'auto',
						minwidth : 30,
						title : name+titletext,
						minheight : 80,
						buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
						           {name : '查看金币分配明细',bclass : 'edit',onpress : action},{separator : true},
						           {name : '打印预览',bclass : 'printer',onpress : action},{separator : true},
						           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
					});
				}

			}else if(pl!=null&&(pl=="zk"||pl=="sk")){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '打印',bclass : 'printer',onpress : action},{separator : true},
					           {name : '补充学员信息',bclass : 'add',onpress : action},{separator : true},
					           {name : 'EXCEL导出',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
				});
			}else if(hylb!=null&&hylb=="wd"&&pl==""&&zzorder!="zz"){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '查看金币分配明细',bclass : 'edit',onpress : action},{separator : true},
					           {name : '打印',bclass : 'printer',onpress : action},{separator : true},
					           {name : '确认收货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '申请退货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '添加运单信息',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
				});
			}else if(zzorder=="zz"){
				if(companyID=="company201009046vxdyzy4wg0000000025"){
					$('.address').flexigrid({
						height : 445,
						width : 'auto',
						minwidth : 30,
						title : name+titletext,
						minheight : 80,
						buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
						           {name : '审核通过并生成收款单',bclass : 'edit',onpress : action},{separator : true},
						           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
					});
				}else{
					$('.address').flexigrid({
						height : 445,
						width : 'auto',
						minwidth : 30,
						title : name+titletext,
						minheight : 80,
						buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
						           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
					});
				}

			}else{
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '查看金币分配明细',bclass : 'edit',onpress : action},{separator : true},
					           {name : '打印预览',bclass : 'printer',onpress : action},{separator : true},
                        		{name : '导出',bclass : 'excel',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
				});
			}
			
			function action(com, grid) {
				switch (com) {
				case '补充学员信息':
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}

					var jNumOrder=$("#"+clientPositioningID).find("span#jNumOrder").text();
					var journalNum=$("#"+clientPositioningID).find("span#journalNum").text();
					$("#joinput").val(jNumOrder);
					$("#jospan").text(jNumOrder);
					$("#jN").text(journalNum);
					var url=basePath+"/ea/bdbill/sajax_getNovice.jspa?";
					$.ajax({
				         url:url,
				         type:"get",
				         async:false,
				         dataType:"json",
				         data: {jo:jNumOrder},
				         success:function(data){
				         	var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
							var UserName = member.UserName;
							var Reference = member.Reference;
							var ReferenceCode = member.ReferenceCode;
							var ReferrerAddress = member.ReferrerAddress;
							$("#noviceName").val(UserName);
							$("#novicePhone").val(Reference);
							$("#noviceCode").val(ReferenceCode);
							$("#noviceAddress").val(ReferrerAddress);
				         },error:function(data){
				          	alert("操作失败！");
				         }
			         });
					$("#jqModeladd").jqmShow();
					break;
				case '查看':		
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}
					var ulr="/ea/bdbill/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit&type=pc";
					//var ulr="ea/phonebill/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001";
					window.open(basePath+ulr);
					break;
                    case '打印发货单预览':
                        if (clientPositioningID == "") {
                            alert("请选择！");
                            return;
                        }
                        var ulr="/ea/bdbill/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit&type=invoice";
                        //var ulr="ea/phonebill/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001";
                        window.open(basePath+ulr);
                        break;
				case '确认收货':
					if(clientPositioningID==""){
						alert("请选择！");
						return;
					}
					if($("#"+clientPositioningID).find("#ddstatus").val()!="02"){
						alert("只有正在物流才可以确认收货！");
						return;
					}
					$("#addressForm").attr("target", "hidden").attr("action",basePath+"ea/bdbill/ea_userConfirmationReceipt.jspa?cashierBills.cashierBillsID="+clientPositioningID);
					document.addressForm.submit.click();
					token = 2;
					break;
				/*case '一键发货':
					$("#onkeyfhForm").attr("target", "hidden").attr("action",basePath+ "/ea/bdbill/ea_toShip.jspa?vuvtype=edit");
					document.onkeyfhForm.submit.click();
					token = 2;
					break;*/
				case '发货':
					if(clientPositioningID==""||clientPositioningID==null){
						alert("请选择");
						break;
					}
                    var url = basePath+"/ea/bdbill/sajax_toShipOne.jspa?vuvtype=edit&cashiId="+clientPositioningID;
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
                            var state = member.state;
                            if(state[0]=="01"){
                                alert("订单号为："+state[1]+"的物品库存不足");
                            }else{
                                alert("操作成功");
                                $("#"+clientPositioningID).remove();
							}
                        },error:function(data){
                            alert("操作失败！");
                        }
                    });
					break;
				case '查看金币分配明细':
					if(clientPositioningID==""){
						alert("请选择！");
						return;
					}
					var status=$("#"+clientPositioningID).find("#ddstatus").val();
					if(status!="01"){
						window.open(basePath+ "/ea/bdbill/ea_toEditjinbimx.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit");
					}else{
						alert("用户没有付款,没办法预估佣金分配数据！");
					}
					break;

				case '审核通过并生成收款单':		
					if(clientPositioningID==""){
						alert("请选择！");
						return;
					}
					
					$("#addressForm").attr("target", "hidden").attr("action",basePath+"ea/wfjshop/ea_passAuditGenReSheet.jspa?cashierBillsID="+clientPositioningID);
					document.addressForm.submit.click();
					token = 2;
					break;
				case '确认分配':
					if(clientPositioningID==""){
						alert("请选择！");
						return;
					}
					var url = basePath+"/ea/bdbill/sajax_getjbfp.jspa?cashierBills.cashierBillsID="+clientPositioningID;
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
							var b = member.b;
							if(b){
								ajaxtoyjfp(clientPositioningID);
							}else{
								alert("该订单产品没有分配佣金比例！不能分配金币");
							}
				         },error:function(data){
				          	alert("操作失败！");
				         }
			         });
					break;
				case '申请退货':
                    if (clientPositioningID == "") {
						alert('请选择!');
						return;
					}
					var fkStatus=$("tr#"+clientPositioningID).find("#fkStatus").text(); 
					if (fkStatus !="已付款") {
						alert('不可重复操作!');
						return;
					}
					if(type=="mobile"){
						window.open(basePath+"/ea/bdbill/ea_applyMobile.jspa?id="+clientPositioningID );
					}
					if(type!="mobile"){
					$("#refund").jqmShow();
					}
				    refund="申请";
					break;
				case '添加运单信息':
					var fkStatus=$("tr#"+clientPositioningID).find("#fkStatus").text();
					if (fkStatus !="同意退货") {
						alert('退货失败，请重新申请!');
						return;
					}
					$.ajax({
						url:basePath+"/ea/bdbill/sajax_getRefundSheetById.jspa?id="+clientPositioningID,
					    type:"get",
					    success:function(data){
					    	var member = eval("(" + data + ")");
							var rs = member.rs;
							$("input#refundAddress").val(rs.refundAddress);
							$("input#postcode").val(rs.postcode);
							$("input#receiverName").val(rs.receiverName);
							$("input#receiverTel").val(rs.receiverTel);
					    },
					});
					
					$("#addApply").jqmShow();
					refund="运单";
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
                    }else if(billtype=="金币入库单"){
						window.open(basePath
							+ "ea/bdbill/ea_toprintCashier.jspa?cassid="+ clientPositioningID);
                	}else {
                        window.open(basePath
                            + "/ea/printcashierbills/ea_toprintCashier.jspa?cashierBillsVO.cashierBillsID="
                            + clientPositioningID);
                    }
					break;
				case '导出' :
                        url = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl+"&zzorder="+zzorder+"&reportType=oldOrder";
                        open(url);
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
                var sdate = $("#sdates").val();
                var edate = $("#edates").val();
				if(sdate!=""&&edate==""){
                    alert("请选择截止日期");
                    return false;
				}
                if(sdate==""&&edate!=""){
                    alert("请选择开始日期");
                    return false;
                }
                if(sdate!=""&&edate!="") {

                    var year1 = sdate.substring(0, 4);
                    var year2 = edate.substring(0, 4);
                    if (year1 != year2) {
                        alert("不能跨年份查询");
                        return false;
                    }
                }
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
	/*$("#caid").val(clientPositioningID);
	$("#fenpeiForm").attr("target", "hidden").attr("action",basePath+ "/ea/bdbill/ea_toDistribution.jspa?");
	document.fenpeiForm.submit.click();
	token = 2;*/
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
	/*if(pl=='pl'){
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl="+pl;
	}else if (pl=='fl'){
		window.location.href = basePath+"/ea/bdbill/ea_getjbfpmx.jspa?hylb=hy&pl="+pl;
	}else if(zzorder=="zz"){
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?zzorder="+zzorder;
	}else{
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb=wd";
	}*/
	if(pl=='pl'){
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl;
	}else if (pl=='fl'){
		window.location.href =  basePath+"/ea/bdbill/ea_getjbfpmx.jspa?hylb="+hylb+"&pl="+pl;
	}else if (pl=='sk'){
		window.location.href =  basePath+"ea/bdbill/ea_skd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
	}else if(pl=='zk'){
        window.location.href =  basePath+"ea/bdbill/ea_getskd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
	}else{
		window.location.href =  basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl+"&zzorder="+zzorder;
	}
}
