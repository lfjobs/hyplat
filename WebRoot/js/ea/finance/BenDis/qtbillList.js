$(document).ready(
	function() {
	 
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
			}else{
				name = '订单管理';
			}
			

			var titletext="<form method='post' name='SearchForm' id='SearchForm'>" +
					"&nbsp;&nbsp;订单编号查询:<input type='text' id='zhi' name='inforType'/>" +
					"&nbsp;&nbsp;单据生成时间:<input type='text' size='7' name='sdate' onfocus='date(this)'/>" +
					"&nbsp;-&nbsp;<input type='test' size='7' name='edate' onfocus='date(this)'/>" +
					"<input type='submit' style='display:none;' name='submit' />" +
					"<input type='hidden' name='search' value='search'/>" +
					"&nbsp;&nbsp;<input type='button' value='查询' id='tosearch'/>" +
					"</form>";
		
			if(pl!=null&&pl=="pl"){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '一键发货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
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
						           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
					});
				}
				
			}else if(pl=="sk"){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
				});
			}else if(hylb!=null&&hylb=="wd"){
				$('.address').flexigrid({
					height : 445,
					width : 'auto',
					minwidth : 30,
					title : name+titletext,
					minheight : 80,
					buttons : [{name : '查看',bclass : 'edit',onpress : action},{separator : true},
					           {name : '确认收货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '申请退货',bclass : 'edit',onpress : action},{separator : true},
					           {name : '添加运单信息',bclass : 'edit',onpress : action},{separator : true},
					           {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
				});
			}
			
			
			
			function action(com, grid) {
				switch (com) {
				case '查看':		
					if (clientPositioningID == "") {
						alert("请选择！");
						return;
					}
					var ulr="/ea/bdbill/ea_toEditCostSheet.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit";
					/*if(weixinCompanyId==5)
					{
						ulr="/ea/bdbill/ea_toeditzkd.jspa?cashierBills.cashierBillsID=" + clientPositioningID;
						
					}*/
						
					window.open(basePath+ulr);
					break;
				case '确认收货':		
					if(clientPositioningID==""){
						alert("请选择！");
						return;
					}
					if($("#ddstatus").val()!="02"){
						alert("只有正在物流才可以确认收货！");
						return;
					} 
					$("#addressForm").attr("target", "hidden").attr("action",basePath+"ea/bdbill/ea_userConfirmationReceipt.jspa?cashierBills.cashierBillsID="+clientPositioningID);
					document.addressForm.submit.click();
					token = 2;
					break;
				case '一键发货':
					$("#onkeyfhForm").attr("target", "hidden").attr("action",basePath+ "/ea/bdbill/ea_toShip.jspa?vuvtype=edit");
					document.onkeyfhForm.submit.click();
					token = 2;
					break;
				case '查看金币分配明细':
					window.open(basePath+ "/ea/bdbill/ea_toEditjinbimx.jspa?cashierBills.cashierBillsID=" + clientPositioningID+"&vuvtype=edit");
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
								$("#caid").val(clientPositioningID);
								$("#fenpeiForm").attr("target", "hidden").attr("action",basePath+ "/ea/bdbill/ea_toDistribution.jspa?");
								document.fenpeiForm.submit.click();
								token = 2;
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
					var fkStatus=$("tr#"+id).find("#fkStatus").text(); 
					if (fkStatus =="退货中") {
						alert('不可重复操作!');
						return;
					}
					$("#refund").jqmShow();
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
				case '设置每页显示条数':
					var url = basePath
							+ "/ea/bdbill/ea_getcomporder.jspa?weixinCompanyId="
							+ weixinCompanyId+"&inforType="+inforType+"&hylb=wd&pl="+pl;
					numback(url);
					break;
				}
			}
			$("#tosearch").click(function() {
				var s="";
				if(pl=='pl'){
					s = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl;
				}else if (pl=='fl'){
					s = basePath+"/ea/bdbill/ea_getjbfpmx.jspa?hylb="+hylb+"&pl="+pl;
				}else if (pl=='sk'){
					s = basePath+"ea/bdbill/ea_getskd.jspa?hylb="+hylb+"&pl="+pl+"&iisnull="+iisnull;
				}else{
					s = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb="+hylb+"&pl="+pl;
				}
				var a="";
				$("#SearchForm").attr("action",s +"&vuvtype=edit");
				document.SearchForm.submit.click();
			});
			
//获取id			
			$(".address tr[id]").click(
					function() {
						clientPositioningID = this.id; 
						$("input.JQuerypersonvalue", $(this)).attr("checked","checked");
					});
			
//退出
			$("input.submitClose").click(
					function(){
						if($(this).attr("id")=="nopass"){
							 $("#addApply").jqmHide();
							 
						}else if($(this).attr("id")=="pass"){
							$("#refund").jqmHide();
						}else{
							$("#confirm").jqmHide();
						}
						
			});
//提交申请
			$(".submitResult").click(function(){
					
					if(confirm("确认提交"+refund+"吗？")){
						if(refund="申请"){
							
							$("form#refundForm")
							.attr("target","hidden")
							.attr("action",basePath+ "/ea/bdbill/ea_refundApply.jspa?id="+id);
							
							document.refundForm.submit.click();
							document.refundForm.reset();
							token = 2;
							
						}
						
						$("form#addApplyForm")
						.attr("target","hidden")
						.attr("action",basePath+ "/ea/bdbill/ea_refundApply.jspa?id="+id);
						document.addApplyForm.submit.click();
						document.addApplyForm.reset();
						token = 2;
						
					}
				    
				
				
			});
//上传图片
			$("input.upload").each(function(){
			
				var id=$(this).attr("id");
				$("#"+id).uploadify({
					"swf"      : basePath+"js/uploadify/uploadify.swf",    //指定上传控件的主体文件
					"fileObjName"  : "file",        //文件对象
					"uploader" : basePath+"/ea/refund/sajax_ea_uploadFile.jspa?companyID="+companyID,   //指定服务器端上传处理文件
					"fileSizeLimit":3072,
					"queueSizeLimit":1,
					'buttonText' : '图片上传',
					'width': 72,
					'fileTypeDesc':'请选择图片格式',
					'fileTypeExts':'*.jpeg;*.jpg;*.gif;*.png;*.JPEG',
				   "onUploadSuccess" : function(file, data, response) {
				     
					var member = eval("(" + data + ")");
					var obj = jQuery.parseJSON(member);
					var filePath = obj.filePath;
					 var img = "<img width=\"200\" height=\"200\" src=\""+basePath+filePath+"\">";
		          
					   $("#"+id).parents("tr").find("div.uploadpic").html(img);
		               var voucherfile=$("#"+id).parent().find(".voucherfile").attr("id");
					$("#"+voucherfile).val(filePath);
				}
			//其他配置项
			});
			});
		});


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
	if(pl=='pl'){
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl="+pl;
	}else if (pl=='fl'){
		window.location.href = basePath+"/ea/bdbill/ea_getjbfpmx.jspa?hylb=hy&pl="+pl;
	}else{
		window.location.href = basePath+"/ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl="+pl;
	}
}
