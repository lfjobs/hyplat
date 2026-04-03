// ajax查询物品通过芯片
    var orgId="";
    var orgName="";
	var chipids = new Array();
	var i = 0;
	var kelongtr="";
	var choosepiaoju="no";
	var subtype="";//判断是  新增票据还是修改票据
	var chooseedit=false;
	var search="";
	$(function() {
		$('.flexme11').flexigrid({
			width : "100%",
			minwidth : 30,
			minheight : 50,
	        colModel : [{  
	                display : '选择',  
	                name : 'id',  
	                width : 50,// 得加上 要不IE报错  
	                sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '<span style="color:red;display:inline;">*</span>凭证日期',  
	                name : '凭证日期',  
	                width : 100,  
	                sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '凭证号码',  
	                name : '凭证号码',  
	                width : 80,  
	                sortable : true,
	                align : 'center'  
	            }, {  
	                display : '凭证类别',  
	                name : '凭证类别',  
	                width : "115",  
	                //sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '凭证来源',  
	                name : '凭证来源',  
	                width : 100,  
	                //sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '制单人',  
	                name : '制单人',  
	                width : 110,  
	                //sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '审核注记',  
	                name : '审核注记',  
	                width : 100,  
	                //sortable : true,  
	                align : 'center'  
	            }, {  
	                display : '<span style="color:red;display:inline;">*</span>附单数',  
	                name : '附单数',  
	                width : 52,  
	                //sortable : true,  
	                align : 'center'  
	            }],
	            resizable: false,

	    });
		$("div.bDiv",$("div#flex1")).css("height","90px");
		$('.flexme12').flexigrid({
			width : 'auto',
			minwidth : 30,
			minheight : 50,
			resizable: true,
	    });
		$("div.bDiv",$("div#flex2")).css("height","350px");
		   //当前时间--------------
				var d = new Date(); 
				var year=d.getFullYear(); 
				var month = d.getMonth()+1; 
				var date = d.getDate(); 
				var day = d.getDay(); 
				var hours = d.getHours(); 
				var minutes = d.getMinutes(); 
				var seconds = d.getSeconds(); 
				var ms = d.getMilliseconds();   
				var curDateTime= year;
				if(month>9)
				 curDateTime = curDateTime +month;
				else
				 curDateTime = curDateTime +"0"+month;
				if(date>9)
				 curDateTime = curDateTime +date;
				else
				 curDateTime = curDateTime +"0"+date;
		
			   //查询
				$(".search").click(function() {
					if(($(".date").eq(0).val()==""||$(".date").eq(0).val()==null)
							||($(".date").eq(1).val()==""||$(".date").eq(1).val()==null)){
						alert("凭证日期必须输入");
						return false;
					}

					if($(".date").eq(1).val()<$(".date").eq(0).val()){
						alert("结束日期小于开始日期");
						return false;
					}		
				   $("#searchForms").attr(
						"action",
						basePath + "ea/voucher/ea_toSearch.jspa?otype=pzlr&date=" + new Date());
				    document.searchForms.submit.click();
			    });
				// 保存归档
				$("#saveinvvoucher").click(function() {
					var ibr=true,tbr=true;	
					var iz=/^\d{1,}$|^\d{1,}\.{1}(\d{1,})$/;
					var tz=/[`~!@#\$%\^\&\*\(\)_\+<>\?:"\{\},\.\\\/;'\[\]]/;
					//判断为汇率是否合格
					$(".huilv").each(function(){
						if(!iz.test($(this).val())){
							ibr=false; return;
						}
					});		
					if(!ibr){
						alert("汇率填写错误");
						return;
					}		
					
					//判断附单数是否填写正确
					if($("#fudanshu").val()==""||$(".err").attr("class")){
						$("#fudanshu").attr("style","border: 0px;width: 100%;height: 100%;background-color: red");
						return;
					}
					
					//判断部门选择
					var bprs=true;
					$(".clickOrg").each(function(){
						if($("option:selected",this).val()=="")
							bprs=false;
					});
					if(!bprs){
						alert("请选择部门");
						return;
					}
					//判断摘要是否填写正确
					$(".zhaiyao").each(function(){
						if(tz.test($(this).val())||$(this).val().length==0){
							tbr=false; //return;
						}
					});
					if(!tbr){
						alert("摘要里不可含有特殊字符,且不能为空");
						return;
					}
					//判断是否选择票据
					/*if(choosepiaoju=="no"){
						alert("请先选择票据");
						return;
					}*/
					var trlength1=$("table#dtinvvoucher").find("tr.checkbill").length;
					var trlength2=$("table#dtinvvoucher").find("tr.searchcheckbill").length;
					if(trlength1==0&&trlength2==0){
						alert("没有可以保存的数据！可以通过查询凭证或者选择票据来进行操作！");
						return;
					}
					//查询凭证 修改保存
					if(search!=""){
						var trlength3=$("table#dtinvvouchers").find("tr.checkbillgoodsold").length;
						if (trlength3 == 0) {
							alert("选择主凭证获取明细进行修改保存！");
							notoken = 0;
							return;
						}
						if(!chooseedit){
							alert("无任何操作，不能修改！");
							return;
						}
						subtype="edit";
					}
					
					//选择票据保存
					if(subtype=="add"&&choosepiaoju=="yes"){
						if(!$("#direction2").attr("id")){
							alert("无数据！");
							return;
						}
						var ymoney=$("table#jdcount").find("input#ymoney").val();
						if(ymoney!=0){
							alert("凭证借贷余额为零才可以存档！");
							return;
						}
						//凭证月结和会计期间限制
						var pzdate=$("table#dtinvvoucher").find("tr.checkbill").find("input#voucherdate").val();
						var ym1;
						var ym2;
						var URL = basePath
						+ "ea/mco/sajax_ea_getmy.jspa?mco_ym="
						+ pzdate.substring(0,6) + "&date=" + new Date().toLocaleString();
						$.ajax({
							url : encodeURI(URL),
							type : "get",
							async : false,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var nologin = member.nologin;
								if (nologin) {
									document.location.href = basePath
											+ "page/ea/not_login.jsp";
								}
							    ym1 = member.c;
							    ym2 = member.b;
							},
							error : function cbf(data) {
								notoken = 0;
								alert("数据获取失败！");
							}
						});
						  if (ym1>0||ym2==false) {
							alert("凭证日期不是有效日期！");
							return;
						  }
					}
					$(".notnull").trigger("blur");
					if (notoken) {
						alert("正在提交数据！请稍等");
						return;
					}
					var re=0;
					$(".panduan",$(".checkbillgoods")).each(function(){
						if (this.value==""){
							$(this).css("background-color","red");
							$(this).attr("class","notnull");
							re=1;
						}
					});
					
					$(".put3", $("tr.checkbillgoods")).trigger("blur");
					if ($("form .error").length){
			         	re=1;
			        }
			        if(re){
						alert("请填完所有必填项");
						return;
					}
					if ($("form#InvVoucherForm .error").length) {
						alert("请填完所有必填项");
						notoken = 0;
						return;
					}
					$("#InvVoucherForm").attr("target", "hidden").attr(
							"action",
							basePath + "/ea/voucher/ea_saveVoucher.jspa?otype=pzlr&subtype="+subtype+"&fudanshu="+$("#fudanshu").val());
					document.InvVoucherForm.submit.click();
					document.InvVoucherForm.reset();
					if(subtype=="add"&&choosepiaoju=="yes"){
						$("tr.checkbill").remove();
						$("tr.checkbillgoods").remove();
					}else{
						$("tr.searchcheckbill").remove();
						$("tr.checkbillgoodsold").remove();
					}
					token = 2;
					return;
					
				});
				// 行的单击事件
				var billstatus;
				$(".flexme11 tr").click(function() {
					$("input.JQuerypersonvalue1", $(this)).attr("checked", "checked");
					//显示票据明细
					var vid=$("input.JQuerypersonvalue1", $(this)).val();
					billstatus=$("span#vouchercategory", $(this)).text();
					if(vid!=""&&vid!=null){
						billlist(vid);
						$("table#dtinvvouchers").find("tr.checkbillgoodsold").remove();
					}
				});
				// 行的单击事件
				$(".flexme12 tr").click(function() {
					$("input.JQuerypersonvalue2", $(this)).attr("checked", "checked");
				});
				//显示票据明细
				function billlist(voucherid){
					var gurl = basePath
					+ "ea/voucher/sajax_ea_getInvVouchersById.jspa?voucher.voucherid="+voucherid;
					$.ajax({
						url : encodeURI(gurl + "&date="
								+ new Date().toLocaleString()),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath + "page/ea/not_login.jsp";
							}
							var billgoodlist = member.billgoodlist;
							if (billgoodlist.length == 0) {
								alert("没有数据");
								notoken = 0;
								return;
							}
							var countjie=0;
							var countdai=0;
							for (var i = 0; i < billgoodlist.length; i++) {
								var x=parseFloat(billgoodlist[i].accountingmoney);
								if(!isNaN(x)){
									if(billgoodlist[i].direction=="D"){
											countjie+=x;
									}else if(billgoodlist[i].direction=="C"){
											countdai+=x;
									}
								}
							    // 选中一行克隆一行
								goodselect++;
								// 克隆一行并添加行class
								$("#kelongoldgood").before(
										$("#kelongoldgood").clone(true).attr("id",
												"kelongoldgood" + goodselect).addClass("checkbillgoodsold"));
								// 修改当前行的所有input的name
								$("#kelongoldgood" + goodselect).find(':input[name!=b]').each(function() {
									$(this).attr("name",
											"goodsmap[" + goodselect + "]." + this.name);
								});
								//key
								$("input#voucherskey", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].voucherskey);
								$("input#radio", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].vouchersid);
								//vouchersid
								$("input#vouchersid", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].vouchersid);
								//id
								$("input#voucherid", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].voucherid);
								//序号
								$("input#vouchersnum", $("#kelongoldgood" + goodselect)).attr("value",
										billgoodlist[i].vouchersnum);
								//科目
								$("input#subjectsid", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].subjectsid);
								$("input#tosubjects", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].subjectsname);
								//单据id
								$("input#casId", $("#kelongoldgood" + goodselect))
										.attr("value", billgoodlist[i].casId);
								//物品id
								$("input#goodsid", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].goodsid);
								//物品单据id
								$("input#goodsbillsid", $("#kelongoldgood" + goodselect))
										.attr("value", billgoodlist[i].goodsbillsid);
								//摘要
								$("input#reasonthing", $("#kelongoldgood" + goodselect))
										.attr("value", billgoodlist[i].reasonthing);
								//币别
								$("select#logistics", $("#kelongoldgood" + goodselect))
								.attr("value", billgoodlist[i].currencyid);
							   //借贷方向
								$("input#direction", $("#kelongoldgood" + goodselect))
								.attr("value", billgoodlist[i].direction);
								if(billgoodlist[i].direction=="D")
									{
									$("span#direction", $("#kelongoldgood" + goodselect))
									.text("借");
									}
								else if(billgoodlist[i].direction=="C"){
									$("span#direction", $("#kelongoldgood" + goodselect))
									.text("贷");
								}
								var money1=$.fAmount(billgoodlist[i].standardmoney, 2);
								var money2=$.fAmount(billgoodlist[i].accountingmoney, 2);
								//本位币金额
								$("input#standardmoney", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].standardmoney);
								$("span#standardmoney", $("#kelongoldgood" + goodselect)).text(money1);
								//记账金额
								$("input#accountingmoney", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].accountingmoney);
								$("span#accountingmoney", $("#kelongoldgood" + goodselect)).text(money2);
								//汇率
								$("input#exchangerate", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].exchangerate);
								//乘除标记
								$("select#mdannotation", $("#kelongoldgood" + goodselect))
								.attr("value", "D");
								//数量
								$("input#quantity", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].quantity);
								$("span#quantity", $("#kelongoldgood" + goodselect)).text(billgoodlist[i].quantity);
								//供货商name
								$("input#clientid", $("#kelongoldgood" + goodselect)).attr(
										"value", billgoodlist[i].clientid);	
								$("input#ccompanyname", $("#kelongoldgood" + goodselect)).attr(
									"value", billgoodlist[i].ccompanyName);	
								
								$("tr#kelongoldgood" + goodselect).show();
							}
							//借贷方金额统计
							var countyu=countjie - countdai;
							//借方金额
							$("table#jdcount").find("span#jfmoney").text($.fAmount(countjie, 2));
							$("table#jdcount").find("input#jfmoney").val(countjie);
							//贷方金额
							$("table#jdcount").find("span#dfmoney").text($.fAmount(countdai, 2));
							$("table#jdcount").find("input#dfmoney").val(countdai);
							//余额
							$("table#jdcount").find("span#ymoney").text($.fAmount(countyu, 2));
							$("table#jdcount").find("input#ymoney").val(countyu);
						
							notoken = 0;
							window.status = "数据加载成功";
						},
						error : function cbf(data) {
							notoken = 0;
							alert("数据获取失败！");
						}
					});
				}
				//删除
				$("#deletetr").click(function() {
					var trlength1=$("table#dtinvvoucher").find("tr.checkbill").length;
					var trlength2=$("table#dtinvvoucher").find("tr.searchcheckbill").length;
					if(trlength1==0&&trlength2==0){
						alert("没有可以删除的数据！可以通过查询凭证或者选择票据来进行操作！");
						return;
					}
					//查询凭证 删除
					if(search!=""){
						var radiochecked;//是否选中参数
						var voucherid;//凭证主键
						$("input#radio","form#InvVoucherForm").each(function(){
							if ($(this).is(':checked')) {
								voucherid=$(this).val();
								radiochecked=1;
							}
						});
						if (radiochecked!=1) {
							alert("请选择");
							return;
						}
						if(billstatus!="未审核"){
							alert("只有未审核状态才能删除！");
							return;
						}
						if(confirm("是否确定删除？")){
							$("#searchForms").attr("target", "hidden").attr(
									"action",
									basePath + "/ea/voucher/ea_deleteVoucher.jspa?otype=pzlr&voucherID="+voucherid);
							document.searchForms.submit.click();
							document.searchForms.reset();
							token = 2;
							return;
						}
					}
					//选择票据删除
					if(subtype=="add"&&choosepiaoju=="yes"){
						var strtts=0;
						var radiochecked;
						var qufen="";
						$("input#radio","form#InvVoucherForm").each(function(){
							if ($(this).is(':checked')) {
								radiochecked=1;
								qufen=$(this).attr("class");
							}
						});
						if (radiochecked!=1) {
							alert("请选择");
							return;
						}
						if(qufen=="JQuerypersonvalue1"){
							$("tr.checkbill").remove();
							$("tr.checkbillgoods").remove();
							alert("删除成功！");
							window.location.reload();//刷新当前页面
							return;
						}
						if(qufen=="JQuerypersonvalue2"){
							var trid;
							$("input.JQuerypersonvalue2","form#InvVoucherForm").each(function(){
								if ($(this).is(':checked')) {
									trid=$(this).parent().parent().parent().attr("id");
									strtts=parseInt(trid.substr(6));
									$("tr#"+trid).remove();
								}
							});
//							var goodsbillsid=$("tr#"+trid).find("input#goodsbillsid").val();
//							$("input#goodsbillsid","form#InvVoucherForm").each(function(){
//								var a=$(this).val();
//								if (a==goodsbillsid) {
//									var trid=$(this).parent().parent().parent().attr("id");
//									$("tr#"+trid).remove();
//								}
//							});
							var trlength=$("table#dtinvvouchers").find("tr.checkbillgoods").length;
							if(trlength>0){
								$("tr.checkbillgoods","form#InvVoucherForm").each(function(item,idx){
									$(this).find("input#vouchersnum").val(item+1);
								});
							}
							//借贷方金额统计
//							var countjie=0;
//							var countdai=0;
//							$("table .checkbillgoods").each(function(){
//								var smoney=$(this).find("input#standardmoney").val();
//								var direction=$(this).find("input#direction").val();
//								var x=parseFloat(smoney);
//								if(!isNaN(x)){
//									if(direction=="D"){
//											countjie+=x;
//									}else if(direction=="C"){
//											countdai+=x;
//									}
//								}
//							});
//							var countyu=countjie - countdai;
//							//借方金额
//							$("table#jdcount").find("span#jfmoney").text($.fAmount(countjie, 2));
//							//贷方金额
//							$("table#jdcount").find("span#dfmoney").text($.fAmount(countdai, 2));
//							//余额
//							$("table#jdcount").find("span#ymoney").text($.fAmount(countyu, 2));
//							$("table#jdcount").find("input#ymoney").val(countyu);	

							while(true){
								if($("#kelong"+(strtts+1)).attr("id"))
									$("#kelong"+(strtts+1)).attr("id","kelong"+strtts);
								else
									break;
								strtts++;
							}
							select=select-1;
							rate();
						}
					}
					
				});
				//打印预览
				$("#dyyl").click(function() {
					var trlength=$("table#dtinvvoucher").find("tr.searchcheckbill").length;
					if(trlength==0){
						alert("请查询凭证打印！");
						return;
					}
					//查询凭证 删除
						var radiochecked;//是否选中参数
						var voucherid;//凭证主键
						$("input#radio","form#InvVoucherForm").each(function(){
							if ($(this).is(':checked')) {
								voucherid=$(this).val();
								radiochecked=1;
							}
						});
						if (radiochecked!=1) {
							alert("请选择");
							return;
						}
						var vid=voucherid.substring(0, 12);
						if(vid=="VoucherBills"){
							alert("请选择主单据打印！");
							return;
						}
						window.open(basePath + "/ea/voucher/ea_toPrint.jspa?sta=voucher&" +
								"voucherID="+voucherid);
					
				});
				
				// 修改行数据
				$(".imgedit").click(function() {
					//$("#imgbc",$(this).parent().parent()).attr("style", "display:block");
					//$("#imgedit",$(this).parent().parent()).attr("style", "display:none");
					var trid=$(this).parent().parent().parent().parent().attr("id");
					//科目
					$("tr#"+trid).find("input#tosubjects").removeAttr("disabled");
					//币别
					$("tr#"+trid).find("select#logistics").removeAttr("disabled");
				    //汇率
					$("tr#"+trid).find("input#exchangerate").removeAttr("disabled");
					//乘除注记
					$("tr#"+trid).find("select#mdannotation").removeAttr("disabled");
					//摘要
					$("tr#"+trid).find("input#reasonthing").removeAttr("disabled");
					//供货商
					$("tr#"+trid).find("input#ccompanyname").removeAttr("disabled");
					chooseedit=true;
					search="endit";
				});
				/*$(".imgbc").click(function() {
					var trid=$(this).parent().parent().parent().parent().attr("id");
					//---------------------验证----------------------------
					$(".notnull").trigger("blur");
					if (notoken) {
						alert("正在提交数据！请稍等");
						return;
					}
					var re=0;
					$(".panduan",$("tr#"+trid)).each(function(){
						if (this.value==""){
							$(this).css("background-color","red");
							$(this).attr("class","notnull");
							re=1;
						}
					});
					
					$(".put3", $("tr#"+trid)).trigger("blur");
					if ($("tr#"+trid+".error").length){
			         	re=1;
			        }
			        if(re){
						alert("请填完必填项");
						return;
					}
					if ($("tr#"+trid+".error").length) {
						alert("请填完必填项");
						notoken = 0;
						return;
					}
					//科目
					$("tr#"+trid).find("input#tosubjects").attr("disabled","true");
					//币别
					$("tr#"+trid).find("select#logistics").attr("disabled","true");
				    //汇率
					$("tr#"+trid).find("input#exchangerate").attr("disabled","true");
					//乘除注记
					$("tr#"+trid).find("select#mdannotation").attr("disabled","true");
					//摘要
					$("tr#"+trid).find("input#reasonthing").attr("disabled","true");
					//供货商
					$("tr#"+trid).find("input#ccompanyname").attr("disabled","true");			
					$("#imgbc",$(this).parent().parent()).attr("style", "display:none");
					$("#imgedit",$(this).parent().parent()).attr("style", "display:block");
				});*/
				// 改变汇率值 计算记账金额
				$(".jisuan").live("keyup", function(event) {
						var huilv=this.value;
						if (isNaN(this.value)) {
							return;
						} else {
							//本位币金额
							var bwmoney = $(this).parent().parent().parent().find("input#standardmoney")
									.val();
							//乘除标记
							var mdannotation = $(this).parent().parent().parent().find("select#mdannotation")
									.children("option:selected").val();
							if (!isNaN(bwmoney)) {
								var jine;
								if(mdannotation=="M"){
									jine = bwmoney * huilv;
								}else if(mdannotation=="D"){
									jine = bwmoney / huilv;
								}
								if(isNaN(jine))
									jine=0;
								jine = Math.round(jine * 100) / 100;
								$(this).parent().parent().parent().find("input#accountingmoney").attr(
										"value", jine);
								var moneygs=$.fAmount(jine, 2);
								$(this).parent().parent().parent().find("span#accountingmoney").text(moneygs);
								
								rate();
							}
						}
				});
				$(".jisuan").live("keydown", function(event) {
						var huilv=this.value;
						if (isNaN(this.value)) {
							return;
						} else {
							original=$(this).parent().parent().parent().find("input#accountingmoney").val();
							//本位币金额
							var bwmoney = $(this).parent().parent().parent().find("input#standardmoney")
									.val();
							//乘除标记
							var mdannotation = $(this).parent().parent().parent().find("select#mdannotation")
									.children("option:selected").val();
							if (!isNaN(bwmoney)) {
								var jine;
								if(mdannotation=="M"){
									jine = bwmoney * huilv;
								}else if(mdannotation=="D"){
									jine = bwmoney / huilv;
								}
								jine = Math.round(jine * 100) / 100;
								$(this).parent().parent().parent().find("input#accountingmoney").attr(
										"value", jine);
								var moneygs=$.fAmount(jine, 2);
								$(this).parent().parent().parent().find("span#accountingmoney").text(moneygs);						
							}
						}
				});
				//乘除标记改变  计算记账金额
				$("select#mdannotation").live("change", function(event) {
					var bj=this.value;
					//本位币金额
					var bwmoney = $(this).parent().parent().parent().find("input#standardmoney")
							.val();
					//汇率
					var huilv= $(this).parent().parent().parent().find("input#exchangerate")
							.val();
					if (isNaN(huilv)) {
						return;
					} else {
						if (!isNaN(bwmoney)) {
							var jine;
							if(bj=="M"){
								jine = bwmoney * huilv;
							}else if(bj=="D"){
								jine = bwmoney / huilv;
							}
							if(isNaN(jine))
								jine=0;
							jine = Math.round(jine * 100) / 100;
							$(this).parent().parent().parent().find("input#accountingmoney").attr(
									"value", jine);
							var moneygs=$.fAmount(jine, 2);
							$(this).parent().parent().parent().find("span#accountingmoney").text(moneygs);
							
						}
					}
			    });
		/** ********************************选择票据**************************************** */
		//复选框事件处理
		$("table#gostablebill tr[id]").live("click", function(event) {
			        var b = $("input.ra", $(this)).attr("checked");
					$("input.ra", $(this)).attr("checked", !b);
				});
		$("input.ra").live("click", function(event) {
			        var b = $(this).attr("checked");
					$(this).attr("checked", !b);
				});
		// 选择票据
		var goodsNum="";
		$("#xzpj").click(function() {
			$("tr.checkbillgoods").each(function(){
				var direction=$(this).find("input#direction").val();
				if(direction=="D"){
					var s=$(this).find("input#goodsbillsid").val();
					if(s!=""){
					  goodsNum=goodsNum+s+",";
					}
				}
			});
			$("input#projectName", $("table#searchbill")).val("");
			$("input#goodsName", $("table#searchbill")).val("");
			$("input#journalNum", $("table#searchbill")).val("");
			$(".jqmWindow", $("#billForm")).jqmShow();
			cxpiaoju("cashierBills.projectName=&cashierBills.goodsName=&cashierBills.journalNum=&cashierBills.departmentID=");
	    });
	    // 根据项目名称，物品名称，凭证号查询
		$("input#searchccbill").click(function() {
			var projectName = $("input#projectName", $("table#searchbill")).val();
			var goodsName = $("input#goodsName", $("table#searchbill")).val();
			var journalNum = $("input#journalNum", $("table#searchbill")).val();
			var departmentID=$("#orgValue  option:selected").val();
			cxpiaoju("cashierBills.projectName="+projectName+"&cashierBills.goodsName="+goodsName+"&cashierBills.journalNum="+journalNum+"&cashierBills.departmentID="+departmentID);
		});
		// 添加所选中的票据到（凭证明细子列）
		$("#qdchoose").click(function() {
			var br=true;
			//判断新添加的数据是否有重复
			$("input[name='checkbillradio']").each(function() {	
				if ($(this).is(':checked')) {
				var srid="";
				$("tr #" + $(this).val()).children("td").each(function() {
					if(this.id == "goodsBillsID")
						srid=$(this).text();
				});
				$("input#goodsbillsid").each(function(){
					if($(this).val()==srid){
						br=false;
						return;
					}
						
				});
				}
			});
			if(!br){
				alert("无法添加重复的数据");return;
			}	
			var trlength=$("table#dtinvvouchers").find("tr.checkbillgoods").length;
			if(trlength==0&&choosepiaoju=="yes"){
				select="";
				docNull;
			}
			search="";
			$("tr.searchcheckbill").remove();
			$("tr.checkbillgoodsold").remove();
			//----------------克隆物品行--------------
			if ($("[name='checkbillradio']").is(':checked')) {
				if(choosepiaoju=="no"){
					//----------------克隆凭证主表行--------------
					// 选中一行克隆一行
					billselect++;
					billdocNull += 1;//克隆行序号
					// 克隆一行并修改文本框的name
					$("#kelongbill").before(
							$("#kelongbill").clone(true).attr("id",
									"kelongbill" + billselect).addClass("checkbill"));
					kelongtr="kelongbill" + billselect;
					// 修改当前行的所有input的name
					$("#kelongbill" + billselect).find(':input[name!=b]').each(function() {
						$(this).attr("name",
								"voucher." + this.name);
					});
					//凭证日期
					$("input#voucherdate", $("#kelongbill" + billselect)).attr(
								"value", curDateTime);
					$("tr#kelongbill" + billselect).show();
				}
				choosepiaoju="yes";
				subtype="add";
				var chooserepeat="";
				var moneys=0;
				var number=0;
				$("input[name='checkbillradio']").each(function() {
					if ($(this).is(':checked')) {
						var goodbillid=$(this).val();
					    var result=goodsNum.split(",");
					    if(result!=""){
					    	for(var i=0;i<result.length;i++){
								 var a=result[i];
								 if(goodbillid==a){
									 chooserepeat="1";
									 goodbillid="1";
								 }
							}
					    }
						if(goodbillid=="1"){
						  $(this).attr("checked", false);
						  return;
						}
						
							// 选中一行克隆一行
							select++;
							docNull += 1;
							// 克隆一行并添加行class
							$("#kelong").before(
									$("#kelong").clone(true).attr("id",
											"kelong" + select).addClass("checkbillgoods"));
							// 修改input标签Id为vouchersnum的值
							$("input#vouchersnum", $("#kelong" + select)).attr("value",
									select);
							// 修改当前行的所有input的name
							$("#kelong" + select).find(':input[name!=b]').each(function() {
								$(this).attr("name",
										"goodsmap[" + select + "]." + this.name);
							});
							//汇率
							$("input#exchangerate", $("#kelong" + select)).attr(
									"value", "1").addClass("huilv");
							$("input#organizationId",$("#kelong"+select)).val(orgId);
							$("input#organizationName",$("#kelong"+select)).val(orgName);
							//借贷方向
							var name=$("input#direction", $("#kelong" + select)).attr("name");
							var sel="<select name='"+name+"' style=\"align:center;width:100%;border:0;\" id='direction2' onchange=\"sele()\"><option value='D'>借</option><option value='C'>贷</option></select>";
							$("input#direction", $("#kelong" + select)).parents("td").attr("style","text-align:center;").html(sel);
								
							var money="";
							var realmoney="";
							// 遍历Id为$(this).val()的tr里说以的td
							$("tr #" + $(this).val()).children("td").each(function() {
								if (this.id == "goodsID") {
									//物品id
									$("input#goodsid", $("#kelong" + select)).attr(
											"value", $(this).text());
								}else if (this.id == "cashierBillsID") {
									//单据id
									$("input#casId", $("#kelong" + select))
											.attr("value", $(this).text());
								}else if (this.id == "goodsBillsID") {
									//物品单据id
									$("input#goodsbillsid", $("#kelong" + select))
											.attr("value", $(this).text());
								}else if (this.id == "goodsName") {
									//摘要
									$("input#reasonthing", $("#kelong" + select))
											.attr("value", $(this).text()).addClass("zhaiyao");
								} else if (this.id == "money") {
									money=$(this).text();
									moneys+=parseFloat(money);
								}else if (this.id == "realmoney") {
									realmoney=$(this).text();
								} else if (this.id == "quantity") {
									//数量
									$("input#quantity", $("#kelong" + select)).attr(
											"value", $(this).text());
									$("span#quantity", $("#kelong" + select)).text($(this).text());
									number+=parseInt($(this).text());
								}else if (this.id == "ccompanyID") {
									//供货商id
									$("input#ccompanyID", $("#kelong" + select)).attr(
											"value", $(this).text());
								}else if (this.id == "ccompanyname") {
									//供货商name
									$("input#ccompanyname", $("#kelong" + select)).attr(
											"value", $(this).text());
								}else {
									$("span#" + this.id, $("#kelong" + select))
											.text($(this).text());
								}
							});
							//判断申请金额和实际金额
							if(realmoney==""&&money!=""){
								var money1=$.fAmount(money, 2);
							//本位币金额
							$("input#standardmoney", $("#kelong" + select)).attr(
									"value", money);
							$("span#standardmoney", $("#kelong" + select)).text(money1);
							
							//记账金额
							$("input#accountingmoney", $("#kelong" + select)).attr(
									"value", money);
							$("span#accountingmoney", $("#kelong" + select)).text(money1);
						    }else{
						    	var money2=$.fAmount(realmoney, 2);
						    	//本位币金额
								$("input#standardmoney", $("#kelong" + select)).attr(
										"value", realmoney);
								$("span#standardmoney", $("#kelong" + select)).text(money2);
								//记账金额
								$("input#accountingmoney", $("#kelong" + select)).attr(
										"value", realmoney);
								$("span#accountingmoney", $("#kelong" + select)).text(money2);
						    }
							$(this).attr("checked", false);
							$("tr#kelong" + select).show();
						
					}

				});
				// 选中一行克隆一行
				select++;
				docNull += 1;
				// 克隆一行并添加行class
				$("#kelong").before(
						$("#kelong").clone(true).attr("id",
								"kelong" + select).addClass("checkbillgoods").attr("style",""));
				// 修改input标签Id为vouchersnum的值
				$("input#vouchersnum", $("#kelong" + select)).attr("value",
						select);
				// 修改当前行的所有input的name
				$("#kelong" + select).find(':input[name!=b]').each(function() {
					$(this).attr("name",
							"goodsmap[" + select + "]." + this.name);
				});
				
				//借贷方
				var name=$("input#direction", $("#kelong" + select)).attr("name");
				var sel="<select name='"+name+"' style=\"align:center;width:100%;border:0;\" id='direction2' onchange=\"sele()\"><option value='C'>贷</option><option value='D'>借</option></select>";
				$("input#direction", $("#kelong" + select)).parents("td").attr("style","text-align:center;").html(sel);
				//汇率
				$("input#exchangerate", $("#kelong" + select)).attr(
						"value", "1").addClass("huilv");
				
				$("input#organizationId",$("#kelong"+select)).remove();
				var orgValue=$("#orgValue").clone(true).attr("name","goodsmap[" + select + "].organizationId").attr("id","organizationId")
								.attr("style","width:100%;height:100%;border:0px;").addClass("clickOrg");
				orgValue.find("option").eq(0).text("选择部门");
				$("input#organizationName",$("#kelong"+select)).attr("style","display: none;").parent().append(orgValue);
	
				
				//本位币金额
				$("input#standardmoney", $("#kelong" + select)).attr(
						"value", moneys);
				var put="<input type='text' value='"+$.fAmount(moneys)+"' class='inputbottom charge charge"+select+"' size='4' style=\"height:100%;border:0;\" onclick=\"charge('charge"+select+"')\">";
				$("span#standardmoney", $("#kelong" + select)).append(put);
				//记账金额
				$("input#accountingmoney", $("#kelong" + select)).attr(
						"value", moneys);
				$("span#accountingmoney", $("#kelong" + select)).text($.fAmount(moneys));
				//数量
				$("input#quantity", $("#kelong" + select)).attr(
						"value", number);
				$("span#quantity", $("#kelong" + select)).text(number);
				$("input#reasonthing", $("#kelong" + select)).addClass("zhaiyao");
				
				if(chooserepeat=="1"){
					  alert("不能选择重复单据！");
				}
				$(".jqmWindow", $("#billForm")).jqmHide();
				goodsNum="";
				chooserepeat="";
			
				rate();
				
			} else {
				alert("请选择票据！");
			}
			loadcab.window.closePort();// 关闭读数据端口
			chipids = new Array();
	        i = 0;
		});
		// 上一页
		$("#dwsybill").click(function() {
			var sy = $("#dwsybill").attr("title");
			if (sy != 0) {
				var projectName = $("input#projectName", $("table#searchbill")).val();
			    var goodsName = $("input#goodsName", $("table#searchbill")).val();
			    var journalNum = $("input#journalNum", $("table#searchbill")).val();
				var typeCN = "cashierBills.projectName=" + projectName
						+"&cashierBills.goodsName="+goodsName
						+"&cashierBills.journalNum="+journalNum
						+"&pageForm.pageNumber=" + sy
						+"&cashierBills.departmentID="+orgId;
				cxpiaoju(typeCN);
			} else {
				alert("已是首页！");
			}
		});

		// 下一页
		$("#dwxybill").click(function() {
			var xy = $("#dwxybill").attr("title");
			if (xy != 0) {
				var projectName = $("input#projectName", $("table#searchbill")).val();
			    var goodsName = $("input#goodsName", $("table#searchbill")).val();
			    var journalNum = $("input#journalNum", $("table#searchbill")).val();
				var typeCN = "cashierBills.projectName=" + projectName
						+"&cashierBills.goodsName="+goodsName
						+"&cashierBills.journalNum="+journalNum
						+"&pageForm.pageNumber=" + xy
						+"&cashierBills.departmentID="+orgId;
				cxpiaoju(typeCN);
			} else {
				alert("已是尾页！");
			}
		});
		// ajax查询往来单位列表
		function cxpiaoju(typeCN) {
			if (notoken) {
				alert("正在获取数据！请稍等");
				return;
			}
			notoken = 1;
			$("#dwsybill").attr("title", 0);
			$("#dwxybill").attr("title", 0);
			$("#dwzybill").attr("title", 0);
			$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:"100%"}).appendTo("#tableOne");     
		    $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载。。。").appendTo("#tableOne").css({position: "absolute",left:"400px",top:"200px"});
			var searchurl = basePath
					+ "ea/voucher/sajax_ea_getListBillByName.jspa?";
			$.ajax({
				url : encodeURI(searchurl + typeCN + "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : true,
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var org=member.org;
					orgId=org.organizationID;
					orgName=org.organizationName;
					if (pageForm == null) {
						alert("没有数据");
						notoken = 0;
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dwsybill").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dwxybill").attr("title", dqy + 1);
					}
					$("b#zycountbill").text(zys);
					var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择票据</td></tr></table>";
					tabletr += "<table width='99%' align='center' id='gostablebill' cellpadding='0'  cellspacing='0' class='table'>";
					tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>凭证号</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>项目名称</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>物品名称</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>单价</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>数量</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>申请金额</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>实际金额</th></tr>";
					for (var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' class='billData' title="
								+ pageForm.list[i].goodsBillsID + " id = "
								+ pageForm.list[i].goodsBillsID + ">";
						tabletr += "<td id='checkbillcc' align='center'><input type ='checkbox'  class='ra' value="
								+ pageForm.list[i].goodsBillsID
								+ " name='checkbillradio'/></td>";
						tabletr += "<td id='journalNum' align='center'>"
								+ pageForm.list[i].journalNum + "</td>";
						tabletr += "<td id='projectName' align='center'>"
								+ pageForm.list[i].projectName + "</td>";
						tabletr += "<td id='goodsName' align='center'>"
								+ pageForm.list[i].goodsName + "</td>";
						tabletr += "<td id='price'  align='center'>"
								+ pageForm.list[i].price + "</td>";
						tabletr += "<td id='quantity' align='center'>"
								+ pageForm.list[i].quantity + "</td>";
						tabletr += "<td id='money' align='center'>"
								+ pageForm.list[i].money + "</td>";
						tabletr += "<td id='realmoney' align='center'>"
								+ pageForm.list[i].realmoney + "</td>";
						tabletr += "<td id='cashierBillsID' style='display:none' align='center'>"
							+ pageForm.list[i].cashierBillsID + "</td>";
						tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";
						tabletr += "<td id='goodsBillsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsBillsID + "</td>";
						tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
						tabletr += "<td id='ccompanyname' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyname + "</td>";
						tabletr += "<td id='goodsStatus' style='display:none' align='center'>"
							+ pageForm.list[i].goodsStatus + "</td>";
						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_02ccbill").html(tabletr);
					$("#body_02ccbill").show();
					notoken = 0;
					window.status = "数据加载成功";
					$("#tableOne").find("div.datagrid-mask-msg").remove();
		            $("#tableOne").find("div.datagrid-mask").remove();
		            $("#tableOne").attr("id","");
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
		}
	    // 为弹出框准备下拉表内容
		$(".jqmWindow").jqm({
			modal : true,// 
			overlay : 20
				// 
			}).jqmAddClose('.close');//
		$(".JQueryreturns").click(function() {
			        goodsNum="";
					notoken = 0;
					$(".jqmWindow").jqmHide();
					//loadcab.window.closePort();// 关闭读数据端口
					chipids = new Array();
	                i = 0;
				});


	//新增
	$("#inserttr").click(function(){
		if(select==0){
			alert("请先选择票据");
		}else{
			// 选中一行克隆一行
			select++;
			// 克隆一行并添加行class
			$("#kelong").before(
					$("#kelong").clone(true).attr("id",
							"kelong" + select).addClass("checkbillgoods").attr("style",""));
			// 修改input标签Id为vouchersnum的值
			$("input#vouchersnum", $("#kelong" + select)).attr("value",
					select);
			// 修改当前行的所有input的name
			$("#kelong" + select).find(':input[name!=b]').each(function() {
				$(this).attr("name",
						"goodsmap[" + select + "]." + this.name);
			});
			//借贷方
			var name=$("input#direction", $("#kelong" + select)).attr("name");
			var sel="<select name='"+name+"' style=\"align:center;width:100%;border:0;\" id='direction2' onchange=\"sele()\"><option value='D'>借</option><option value='C'>贷</option></select>";
			$("input#direction", $("#kelong" + select)).parents("td").attr("style","text-align:center;").html(sel);
			
			$("input#organizationId",$("#kelong"+select)).remove();
			var orgValue=$("#orgValue").clone(true).attr("name","goodsmap[" + select + "].organizationId").attr("id","organizationId")
							.attr("style","width:100%;height:100%;border:0px;").addClass("clickOrg");
			orgValue.find("option").eq(0).text("选择部门");
			$("input#organizationName",$("#kelong"+select)).attr("style","display: none;").parent().append(orgValue);
			
			//本位币金额
			$("input#standardmoney", $("#kelong" + select)).attr(
					"value", "");
			var put="<input type='text' value='' class='inputbottom charge charge"+select+"' size='4' style=\"height:100%;border:0;\">";
			$("span#standardmoney", $("#kelong" + select)).append(put);
			//记账金额
			$("input#accountingmoney", $("#kelong" + select)).attr(
					"value", "");
			$("span#accountingmoney", $("#kelong" + select)).text();
			//数量
			$("input#quantity", $("#kelong" + select)).attr(
					"value", "");
			var num="<input type='text' class='inputbottom' size='4' style=\"height:100%;border:0;\">";
			$("span#quantity", $("#kelong" + select)).html(num);
		}
		
	});
		
	});	
	function re_load() {
		window.location.reload();//刷新当前页面
		/*if(subtype=="save"){
		   window.location.reload();//刷新当前页面
		}
		if(subtype=="savecheck"){
		   var url = basePath+ "/ea/purchase1/ea_toPurchase.jspa?pageNumber="
					+ pNumber + "&cashierBills.cashierBillsID="
				    + cashierBillsID;
		   document.location.href = url;//跳转到查看页面
		}*/
	}
	/** **********************************往来单位(供货商)**************************************** */
	$(document).ready(function() {
		var contactcID = "";// 已经添加到往来单位的ID
		var ccID = "";// ccompanyID
		$("table#gostable tr[id]").live("click", function(event) {
					contactcID = this.id;
					ccID = this.title;
					$("input.ra", $(this)).attr("checked", "checked");
				});

		// 选择往来单位
		$("input#ccompanyname").click(function() {
			        $("input#inputname", $("table#searchcompany")).val($(this).attr("name"));
					$("input#ccompanyID", $("table#searchcompany")).val("");
					$("select#contactConnections", $("table#searchcompany"))
							.val("全部");
					$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
					cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
				});

		// 新增往来单位
		$(".xzdw").click(function() {
			window.open(basePath
					+ "ea/contactcompany/ea_getListContactCompany.jspa?");
		});
		
		// 添加所选中的往来单位
		$("#qdcompany").click(function() {
			var inputname=$("input#inputname", $("table#searchcompany")).val();
			if (contactcID != "") {
				var RegistrationURL = basePath
						+ "ea/contactcompany/sajax_ea_getListRegistration.jspa?contactCompany.ccompanyID="
						+ ccID + "&date=" + new Date().toLocaleString();
				$.ajax({
					url : encodeURI(RegistrationURL),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath
									+ "page/ea/not_login.jsp";
						}
						var bankList = member.bankList;
						$se = $("select#aNum", $("table#table4"));
						$se.empty();
						$se.append("<option selected='selected' value = ''>--请选择--</option>");
						for (var i = 0; i < bankList.length; i++) {
							$op = $("<option />");
							$op.attr("value", bankList[i].bankAccount)
									.text(bankList[i].bankName + "---"
											+ bankList[i].bankAccount);
							$se.append($op);
						}
						$("span#accountNum", $("#table4")).remove();
						$se.attr("name", "financialBill.accountNum");
						$se.show();
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				$("tr #" + contactcID).children("td").each(function() {
					if (this.id == "ccompanyID") {
						$("input[name='"+inputname.split(".")[0]+".clientid']")
								.val($(this).text());
					} else if (this.id == "ccompanyname") {
						$("input[name='"+inputname+"']")
								.val($(this).text());
					}
				});
				contactcID = "";
				$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
			} else {
				alert("请选择往来单位！");
			}
		});

		// 根据输入的往来单位名称查询
		$("input#searchcc").click(function() {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
			var contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
			cxwldw("contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections);
		});

		// 上一页
		$("#dwsy").click(function() {
			var sy = $("#dwsy").attr("title");
			if (sy != 0) {
				contactcID = "";
				var typeName = $("input#ccompanyID", $("table#searchcompany"))
						.val();
				var contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
				var typeCN = "contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections=" + contactConnections
						+ "&pageForm.pageNumber=" + sy;
				cxwldw(typeCN);
			} else {
				alert("已是首页！");
			}
		});

		// 下一页
		$("#dwxy").click(function() {
			var xy = $("#dwxy").attr("title");
			if (xy != 0) {
				contactcID = "";
				var typeName = $("input#ccompanyID", $("table#searchcompany"))
						.val();
				var contactConnections = $("select#contactConnections",
						$("table#searchcompany")).val();
				var typeCN = "contactCompany.companyName=" + typeName
						+ "&cconnection.contactConnections=" + contactConnections
						+ "&pageForm.pageNumber=" + xy;
				cxwldw(typeCN);
			} else {
				alert("已是尾页！");
			}
		});

		// ajax查询往来单位列表
		function cxwldw(typeCN) {
			if (notoken) {
				alert("正在获取数据！请稍等");
				return;
			}
			notoken = 1;
			$("#dwsy").attr("title", 0);
			$("#dwxy").attr("title", 0);
			$("#dwzy").attr("title", 0);
			var searchurl = basePath
					+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
			$.ajax({
				url : encodeURI(searchurl + typeCN + "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath + "page/ea/not_login.jsp";
					}
					var pageForm = member.pageForm;
					var connectionlist = member.connectionlist;
					if (pageForm == null) {
						alert("没有数据");
						notoken = 0;
						return;
					}
					var $se = $("select#contactConnections",
							$("table#searchcompany"));
					$se.empty();
					$select = "<option selected='selected' value = ''>--全部--</option>";
					$se.append($select);
					for (var i = 0; i < connectionlist.length; i++) {
						$op = $("<option />");
						$op.attr("value", connectionlist[i].codeValue)
								.text(connectionlist[i].codeValue);
						$se.append($op);
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dwsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dwxy").attr("title", dqy + 1);
					}
					$("span#zycount").text(zys);
					var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择单位</td></tr></table>";
					tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
					tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>单位名称</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>业务关系</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
					tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
					for (var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' title="
								+ pageForm.list[i].ccompanyID + " id = "
								+ pageForm.list[i].contactConnectionID + ">";
						tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
								+ pageForm.list[i].contactConnectionID
								+ " name='checkradio'/></td>";
						tabletr += "<td id='ccompanyname' align='center'>"
								+ pageForm.list[i].companyName + "</td>";
						tabletr += "<td id='contactConnections' align='center'>"
								+ pageForm.list[i].contactConnections + "</td>";
						tabletr += "<td id='industryType' align='center'>"
								+ pageForm.list[i].industryType + "</td>";
						tabletr += "<td id='companyTel'  align='center'>"
								+ pageForm.list[i].companyTel + "</td>";
						tabletr += "<td id='cresponsible' align='center'>"
								+ pageForm.list[i].cresponsible + "</td>";
						tabletr += "<td id='responsibleTel' align='center'>"
								+ pageForm.list[i].responsibleTel + "</td>";
						tabletr += "<td id='companyAddr'  align='center'>"
								+ pageForm.list[i].companyAddr + "</td>";
						tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
								+ pageForm.list[i].ccompanyID + "</td>";
						tabletr += " </tr>";
					}
					tabletr += " </table>";
					$("#body_02cc").html(tabletr);
					$("#body_02cc").show();
					// alert("数据加载成功");
					notoken = 0;
					window.status = "数据加载成功";
				},
				error : function cbf(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
		}
	}); 
	
	//修改汇率时   修改借方金额和贷方金额汇总
	function rate(){
		var moneyj=0,moneyd=0;								
		$("input#accountingmoney").each(function(){
			if($(this).val()!=""){
				var text=$(this).parents("tr").find("#direction").text();
				if(text=="")	
					text=$(this).parents("tr").find("#direction2").find("option:selected").text();
				if(text=="借")
					moneyj+=parseFloat($(this).val());
				if(text=="贷")
					moneyd+=parseFloat($(this).val());
			}
		});
		$("#jdcount").find("span#jfmoney").text($.fAmount(moneyj,2));
		$("#jdcount").find("span#dfmoney").text($.fAmount(moneyd,2));
		$("#jdcount").find("input#ymoney").val(moneyj-moneyd);
		$("#jdcount").find("span#ymoney").text($.fAmount((moneyj-moneyd),2));
	}
	
	//
	function charge(str){
		$("."+str).val(convert($("."+str).val()));
	}
	
	
	//将千分位的字符串转换为float
	function convert(s){
		var str=s.split(",");
		var st="";
		for(var i=0;i<str.length;i++){
			st+=str[i];
		}
		return parseFloat(st);
	}
	function sele(){
		rate();
	}
	
	$(function(){
		$(".charge").live("keyup", function(event){
			var key_code = event.keyCode;
			if(key_code!=110){
				$(this).parents("td").find("input#standardmoney").val($(this).val());
				var currency=$(this).parents("tr").find("td").find("input#exchangerate").val();
				$(this).parents("tr").find("td").find("span#accountingmoney").text($.fAmount(currency*$(this).val()));
				$(this).parents("tr").find("td").find("input#accountingmoney").val(currency*$(this).val());
				rate();
			}
		});
		$(".charge").live("keydown", function(event){
			var key_code = event.keyCode;
			if(key_code==8||key_code==46||(key_code>47&&key_code<58)||
					(key_code>95&&key_code<106)||key_code==110||(key_code>36&&key_code<41)){
				if(key_code==110){
					var val=$(this).val();
					for(var r=0;r<val.length;r++){
						if(val[r]=="."){
							return false;
						}
					}
				}
			}else{
				return false;	
			}
		});
		
	});
	/** **************************************科目管理******************************************* */
$(function(){
	$(".subrid").click(function(){
		if($(this).val()=="首页")
			subtrs("1",$(this).attr("id"));
		else if($(this).val()=="上一页")
			subtrs(parseInt(pagenum)-1,$(this).attr("id"));
		else if($(this).val()=="下一页")
			subtrs(parseInt(pagenum)+1,$(this).attr("id"));
		else if($(this).val()=="尾页")
			subtrs(Math.ceil(search2/14),$(this).attr("id"));
	});
	$(".subtr").live("click",function(){
		$(".subtr").each(function(){
			$(this).attr("style","background-color:#E0EEEE");
		});
		$(this).attr("style","background-color:#8DB6CD");
	});
	$("#determine").click(function(){
		$(".subtr").each(function(){
			if($(this).attr("style")=="background-color:#8DB6CD"){
				$("#subjectsid", $(".receivesubjects")).attr("value",
						$(this).find("td").find("input").val());
				$("#subjectscode", $(".receivesubjects")).attr("value",
						$(this).find("td").eq(0).text());
				$("#tosubjects", $(".receivesubjects")).attr("value",
						"("+$(this).find("td").eq(0).text()+")"+$(this).find("td").eq(1).text());
				$(".receivesubjects").removeClass("receivesubjects");
			}
		});
		$("#subjectr").jqmHide();	
	});
	$("#cancel").click(function(){
		$("#subjectr").jqmHide();		
	});
	$("#tosubjects").click(function(){
		$(this).parent().parent().addClass("receivesubjects");
		if(!$(".subNumber").text()){
		var subjecturl = basePath
			+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjects = member.subjectsList;
				for(var i=0;i<subjects.length;i++){
					var tr;
					if(i==0){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line4.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else if(i==(subjects.length-1)){
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line2.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}else{
						tr="<tr onclick=subtrs('1','"+subjects[i].subjectsID+"')><td><img src='"+basePath+"js/tree/codebase/imgs/line3.gif'></td>" +
							"<td id='"+subjects[i].subjectsID+"'" +
							"class='"+subjects[i].subjectsNumbers+" subNumber'><a>" +
							subjects[i].subjectsName+"</a></td></tr>";
					}
					$("#kemuone").append(tr);
				}				
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		}
		$("#subjectr").jqmShow();	
	});
	$("#fudanshu").change(function(){
		if(/^[1-9]*[1-9][0-9]*$/.test($(this).val()))
			$(this).attr("style","border: 0px;width: 100%;height: 100%;").removeClass("err");
		else
			$(this).attr("style","border: 0px;width: 100%;height: 100%;background-color: red").addClass("err");
	});
	
	$.ajax({
		url : basePath+"ea/office/sajax_ea_sajaxGetThenFiveDepartmentsList.jspa",
		type : "post",
		async : false,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				var option="<option value='"+list[i].organizationID+"'>"+list[i].organizationName+"</option>";
				$("#orgValue").append(option);
				$(".orgVAL").append(option);
			}
		},
		error:function(){
			alert("数据获取失败");
		}
	});
	$(".clickOrg").live("change",function(){
		$(this).parent().find("input#organizationName").val($("option:selected",this).text());
	});
	$(".billData").live("dblclick",function(){
		window.open(basePath
				+ "/ea/splitbill/ea_toSaveCashierBills.jspa?cashierBills.cashierBillsID="+ $(this).find("td").eq(8).text()+"&status=view");
	});
});
function subtrs(pageNumber,obj){
	if(pageNumber>(Math.ceil(search2/14)))
		pageNumber=(Math.ceil(search2/14));
	if(pageNumber<1)
		pageNumber=1;
	$(".subrid").attr("id",obj);
	$("#kemutoo").find(".subtr").remove();
	pagenum=pageNumber;
	var subjecturl = basePath
		+ "ea/csbjects/sajax_ea_getAjaxListCsubejstsByPID.jspa?subjectsID=";
	$.ajax({
		url : encodeURI(subjecturl + obj+"&pageForm.pageNumber="+pageNumber+"&pageNumber=14&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var subjects = member.pageForm.list;
			search2=member.search;
			for(var i=0;i<subjects.length;i++){
				var lb="其他";
				if(subjects[i].subjectsCategory=="A")
					lb="银行";
				if(subjects[i].subjectsCategory=="B")
					lb="现金";
				if(subjects[i].subjectsCategory=="C")
					lb="固定资产";
				var tr="<tr align='left' class='subtr' style=\"background-color:#E0EEEE\">" +
					   "<td>"+subjects[i].subjectsNumbers+"</td>" +
					   "<td><span class='sprs' title="+subjects[i].subjectsName+">"+subjects[i].subjectsName+"</span></td>"+
					   "<td>"+lb+"<input type='hidden' value="+subjects[i].subjectsID+"></td>" +
					   "<td>"+(subjects[i].subjectsDirection=='D'?'借':'贷')+"</td>" +
					   "<td>"+(subjects[i].subjectsAccounts=='Y'?'主账户':'虚账户')+"</td></tr>"; 

				$("#kemutoo").append(tr);
			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});		
};
