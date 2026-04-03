$(function(){
	var query = "<form method='post' action='ea/voucher/ea_getVoucherCondition.jspa?sNumber=1&eNumber=3&otype=pzjz' id='Form' name='Form'>凭证记账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;凭证日期：<input type='text' class='date' name='sdate' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\">" +
				"&nbsp;&nbsp;~&nbsp;&nbsp;<input type='text' class='date' name='edate' onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\">&nbsp;&nbsp;" +
				"&nbsp;&nbsp;凭证号码：<input type='text' class='number' name='snumber' >&nbsp;&nbsp;~&nbsp;&nbsp;" +
				"<input type='text' class='number' name='enumber' >&nbsp;&nbsp;<input type='radio' name='status'" +
				" value='5' checked>已记账&nbsp;&nbsp;<input type='radio' name='status' value='3'>未记账&nbsp;" +
				"&nbsp;&nbsp;<input type='submit' value='查询' id='submit' class='button' name='submit'></form>";
	
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,

		title : query,
		minheight : 80,
		buttons : [{
			name : '记账',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '退记账',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '打印预览',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			},{
			separator : true
			},{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
	function action(com, grid) {
		switch (com) {

			case '记账' :
				if(Id==""){
					alert("请先选择");
					break;
				}
				var i=0;
				var bl=true;
				$(".check").each(function(){
					if($(this).attr("checked")){
						var sta=$(this).parents("td").find(".hid").val();
						if(sta!="3"){
							alert("进行记账时，必须选择未记账的数据");
							bl=false;
							return false;
						}
						i++;
					}
				});
				if(bl&&i>0){
					if(confirm("是否确定记账")){
						$("#form2").attr("target", "hidden").attr("action",basePath+"ea/voucher/ea_examineVoucher.jspa?status=5");
						document.form2.submit2.click();
						token = 2;
					}
				}
				
				break;
			case '退记账' :
				if(Id==""){
					alert("请先选择");
					break;
				}
				var i=0;
				var bl=true;
				$(".check").each(function(){
					var date=$(this).parents("td").parents("tr").find("td").eq(1).text().substr(0,6);
					$.ajax({
						url:"ea/voucher/sajax_ea_getMonthly.jspa?mco_ym="+date,
						async : false,
						type:"post",
						success:function(data){
							var member = eval("("+data+")");
							var number=member.number;
							if(number>0){
								alert(date+"月份的账单已月结，不能退记账");
								bl=false;
								return false;
							}
						},
						error:function(data){}
					});
					if($(this).attr("checked")){
						var sta=$(this).parents("td").find(".hid").val();
						if(sta!="5"){
							alert("进行退记账时，必须选择已记账的数据");
							bl=false;
							return false;
						}
						i++;
					}
				});
				if(bl&&i>0){
					if(confirm("是否确定退记账")){
						$("#form2").attr("target", "hidden").attr("action",basePath+"ea/voucher/ea_examineVoucher.jspa?status=4");
						document.form2.submit2.click();
						token = 2;
					}
				}
				break;
				
			case '打印预览' :
				var voucherid=new Array();
				var irs=0;
				$(".check").each(function(){
					if($(this).attr("checked")){
						voucherid[irs]=$(this).val();
						irs++;
					}
				});
				if(voucherid!=""&&voucherid!=null&&voucherid.length>0)
					window.open(basePath + "/ea/voucher/ea_examineVoucherToPrint.jspa?sta=lord&voucherId="+voucherid);
				else
					alert("请选择一条数据");
				
				break;
			case '设置每页显示条数' :
				var page=prompt("请输入小于10的整数");
				if(page>0&&page<10){
					var url=basePath+"ea/voucher/ea_getVoucherExamineList.jspa?otype=pzjz&pageNumber="+page;
					window.location.href=url;
				}else{
					alert("输入有误");
					break;
				}
				break;
			
		}
	}
	$('.fexlist2').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		minheight : 80});
	$("div.bDiv",$("div#fex1")).css("height","75px");
	$("div.bDiv",$("div#fex2")).css("height","350px");
	$(".check").click(function(){
		$(this).attr("checked",!$(this).attr("checked"));
	});
	$("tr.main").click(function(){
		$("#tb2").find("tr.ttr").remove();
		Id=this.id;
		$(this).find("input").attr("checked",!$(this).find("input").attr("checked"));
		$.ajax({
			url:basePath+"ea/voucher/sajax_ea_getVouchersExamineList.jspa",
			data:{"voucherID":this.id},
			type:"post",
			async: false,
			success:function(data){
				var member = eval("("+data+")");
				var list=member.list;
				for(var i=0;i<list.length;i++){
					var tr=$(".trs").clone(true).removeClass().addClass("ttr");
					tr.find("span").eq(0).text((i+1));
					tr.find("span").eq(1).text(list[i].subjectsname);
					tr.find("span").eq(2).text((list[i].direction=='D'?'借':'贷'));
					tr.find("span").eq(3).text(list[i].organizationName);
					tr.find("span").eq(4).text($.fAmount(list[i].standardmoney,2));
					tr.find("span").eq(5).text($.fAmount(list[i].accountingmoney,2));
					tr.find("span").eq(6).text(list[i].currencyname);
					tr.find("span").eq(7).text(list[i].exchangerate);
					tr.find("span").eq(8).text(list[i].quantity);
					tr.find("span").eq(9).text(list[i].reasonthing);
					tr.find("span").eq(10).text(list[i].ccompanyName);
					$(".trs").before(tr);
				}
			},
			error:function(){
				alert("????");
			}
		});
	});
	$("#submit").click(function(){
		if(($(".date").eq(0).val()==""||$(".date").eq(0).val()==null)
				||($(".date").eq(1).val()==""||$(".date").eq(1).val()==null)){
			alert("凭证日期必须输入");
			return false;
		}

		if($(".date").eq(1).val()<$(".date").eq(0).val()){
			alert("结束日期小于开始日期");
			return false;
		}		
	});
});
function re_load() {
	window.location.reload();//刷新当前页面
}
$(function(){
	$(".check").each(function(){
		var borrow=0;
		var loan=0;
		$.ajax({
			url:basePath+"ea/voucher/sajax_ea_getVouchersExamineList.jspa",
			data:{"voucherID":$(this).val()},
			type:"post",
			async: false,
			success:function(data){
				var member = eval("("+data+")");
				var list=member.list;	
				for(var r=0;r<list.length;r++){
					if(list[r].direction=="D"){
						borrow+=list[r].standardmoney;
					}else{
						loan+=list[r].standardmoney;
					}
				}
			},
			error:function(){
				alert("????");
			}
		});
		$(this).parents("td").parents("tr").find("span").eq(4).text($.fAmount(borrow,2));
		$(this).parents("td").parents("tr").find("span").eq(5).text($.fAmount(loan,2));
	});
});