$(function(){

	var title="<form name='form' id='form' method='post'>采购收货单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单据编号：" 
			+ "<input type='text' id='likeJournalnum' style='width:100px;'>&nbsp;&nbsp;&nbsp;<input type='submit' " 
			+ " name='submit' id='submit' style=\"display: none;\">&nbsp;&nbsp;&nbsp;<input type='button' id='tosearch' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title :title,
		minheight : 80,
		buttons : [{
			name : '收货',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
				name : '打印',
				bclass : 'printer',
				onpress : action
					// 当点击调用方法
			}, {
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
			case '收货' :
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				if($("#"+cashierBillsID).find("td").eq(8).text()!="已验货"){
					alert("只能选择未收货的单据！");
					break;
				}
				open(basePath+"ea/sourcingsto/ea_goodsReceiptData.jspa?cashierBillsID="+cashierBillsID+"&fiveClear="+fiveClear);
				break;
			case '打印':
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/sourcingsto/ea_goodsReceiptData.jspa?cashierBillsID="+cashierBillsID+"&type=see");
				break;
			case '设置每页显示条数':
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/sourcingsto/ea_goodsReceiptList.jspa?pageNumber="+pr+"&fiveClear="+fiveClear;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
		}
	}

	$("#tosearch").click(function(){
		var likeJournalnum=$("#likeJournalnum").val();
		window.location.href=basePath+"ea/sourcingsto/ea_goodsReceiptList.jspa?cashierbills.journalNum="+likeJournalnum+"&fiveClear="+fiveClear;
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		cashierBillsID=this.id;
	});
});