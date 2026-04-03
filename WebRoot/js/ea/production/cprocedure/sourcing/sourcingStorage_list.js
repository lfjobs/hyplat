$(function(){

	var title="<form name='form' id='form' method='post'>采购入库单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单据编号：" 
			+ "<input type='text' id='likeJournalnum' style='width:100px;'>&nbsp;&nbsp;&nbsp;制单人："
			+ "<input type='text' id='likeStaffName' style='width:100px;'>&nbsp;&nbsp;&nbsp;制单日期："
			+ "<input type='text' id='likeSDate' style='width:100px;' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\">&nbsp;-&nbsp;"
			+ "<input type='text' id='likeEDate' style='width:100px;' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\">&nbsp;&nbsp;&nbsp;单据类型：" 
			+ "<select id='likeStatus'><option value='13'>物品收货单</option><option value='15'>采购入库单</option></select>&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit'  name='submit' id='submit' style=\"display: none;\">&nbsp;&nbsp;&nbsp;" 
			+ "<input type='button' id='tosearch' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title :title,
		minheight : 80,
		buttons : [{
			name : '填写入库单',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
				name : '查看审核',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '打印预览',
				bclass : 'printer',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
				name : '确认入库',
				bclass : 'edit',
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
			case '填写入库单' :
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				/*if($("#"+cashierBillsID).find("td").eq(9).text()!="已收货"){
					alert("只能选择已收货的单据！");
					break;
				}*/
				open(basePath+"ea/sourcingsto/ea_storageAddPage.jspa?cashierBillsID="+cashierBillsID+"&fiveClear="+fiveClear);
				break;
			case '查看审核':
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				if($("#"+cashierBillsID).find("td").eq(9).text()!="草稿"&&$("#"+cashierBillsID).find("td").eq(9).text()!="审核中"){
					alert("只能选择草稿或审核中的单据！");
					break;
				}
				open(basePath+"ea/sourcingsto/ea_storageData.jspa?cashierBillsID="+cashierBillsID);
				break;
			case '确认入库':
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				var br=true;
				$.ajax({
					url:basePath+"ea/sourcingsto/sajax_ea_warehousingQueryAudit.jspa?cashierBillsID="+cashierBillsID,
					type:"post",
					async : false,
					success:function(data){
						var member = eval("(" + data + ")");
						var sta=member.sta;
						if(sta=="0"){
							br=false;
						}
					},
					error:function(){
						alert("数据获取失败");
					}
				});
				if($("#"+cashierBillsID).find("td").eq(9).text()!="审核中"||!br){
					alert("只能选择审核中已审核过的单据！");
					break;
				}
				if(confirm("确定出库")){
					var url=basePath+"ea/sourcingsto/ea_confirmStorage.jspa?cashierBillsID="+cashierBillsID;
					$("#forms").attr("target", "hidden").attr("action",url);
					document.forms.submits.click();
					token = 2; 
				}
				break;
			case '打印预览':
				if(cashierBillsID==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/sourcingsto/ea_storageData.jspa?cashierBillsID="+cashierBillsID+"&type=see");
				break;
			case '设置每页显示条数':
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					var str="&cashierbills.journalNum="+temporaryJournalNum+"&cashierbills.inputName="+temporaryStaffName
					+"&sDate="+temporarySDate+"&eDate="+temporaryEDate+"&cashierbills.status="+temporaryStatus;
					window.location.href=basePath+"ea/sourcingsto/ea_storageList.jspa?pageNumber="+pr+str+"&fiveClear="+fiveClear;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
		}
	}

	$("#tosearch").click(function(){
		var likeJournalnum=$("#likeJournalnum").val();
		var likeStaffName=$("#likeStaffName").val();
		var likeSDate=$("#likeSDate").val();
		var likeEDate=$("#likeEDate").val();
		var likeStatus=$("#likeStatus option:selected").val();
		var url="?cashierbills.journalNum="+likeJournalnum+"&cashierbills.inputName="+likeStaffName
			+"&sDate="+likeSDate+"&eDate="+likeEDate+"&cashierbills.status="+likeStatus;
		window.location.href=basePath+"ea/sourcingsto/ea_storageList.jspa"+url+"&fiveClear="+fiveClear+"&type="+type;
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		cashierBillsID=this.id;
	});
});

function re_load(){
	window.location.href=window.location.href;
}