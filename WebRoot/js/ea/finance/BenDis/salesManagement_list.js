$(function(){
	var title="";
	/*var title="帐号类型：<select id='likeType'>";
	var strs=str.split(",");
	for(var i=0;i<strs.length;i++){
		title+="<option value="+strs[i].split(" - ")[0]+">"+strs[i].split(" - ")[1]+"</option>";
	}*/
var query = "<form id='form' name='form' method='post'>微分金销售出库单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"入库编号：<input type='text' id='likeJournalNum' style='width:120px'>&nbsp;&nbsp;&nbsp;" +
			"订单编号：<input type='text' id='likeJNumOrder' style='width:120px'>&nbsp;&nbsp;&nbsp;" +
			"<input type='submit' id='submit' name='submit' style='display: none;'>" +
			"开始时间：<input type='text' id='likeSdate' style='width:120px' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\">&nbsp;&nbsp;&nbsp;" +
			"结束时间：<input type='text' id='likeEdate' style='width:120px' onfocus=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\">&nbsp;&nbsp;";
var tail="<input type='button' id='tosearch' class='input-button' value='查询' style='margin:0px;margin-left:5px;width:35px;'></select></form>";
	
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		title : query+title+tail,
		minwidth : 30,
		checkbox : true,
		minheight : 80,
		buttons : [{
			name : '导出',
			bclass : 'excel',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '查看预览',
			bclass : 'see',
			onpress : action
				// 当点击调用方法
			},{
				separator : true
			},{
			name : '打印',
			bclass : 'see',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '批量打印',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
});
function action(com, grid) {
	switch (com) {

		case '导出' :
			var str="";
			$("#titleTr").find("th").each(function(index){
				if(index!=0){
					if(index==1)
						str+=$(this).text();
					else
						str+=","+$(this).text();
				}
			});
			window.location.href=basePath+"/ea/salesman/ea_exportExcelTable.jspa?title=微分金销售出库单&str="+str;
			break;
		case '查看预览' :
			if(id==""){
				alert("请选择！");
				return;
			}
			open(basePath+"/ea/salesman/ea_getPreviewPageData.jspa?type=00&cashierBills.cashierBillsID="+id);
			break;
		case '设置每页显示条数':
			var pr=prompt("请输入0~20的正整数");
			if(pr>0&&pr<=20){
				window.location.href=basePath+"ea/salesman/ea_getHomePageInformationList.jspa?type=01&pageNumber="+pr;
			}else{
				alert("您输入的有误，请重新输入");
			}
			break;
		case '打印':
			if(id==""){
				alert("请选择！");
				return;
			}
			open(basePath+"/ea/salesman/ea_getPreviewPageData.jspa?type=01&cashierBills.cashierBillsID="+id);
			break;
		case '批量打印':
			open(basePath+"/ea/salesman/ea_piliangdayin.jspa");

			break;
	}
}
$(function(){
	
	$("#tosearch").click(function(){
		var journalNum=$("#likeJournalNum").val();
		var jNumOrder=$("#likeJNumOrder").val();
		var type=$("#likeType option:selected").val();
		var sdate=$("#likeSdate").val();
		var edate=$("#likeEdate").val();
		window.location.href=basePath+"/ea/salesman/ea_getHomePageInformationList.jspa?" +
				"cashierBills.journalNum="+journalNum+
				"&cashierBills.jNumOrder="+jNumOrder+
				"&type="+type+"&sdate="+sdate+"&edate="+edate;
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		id=$(this).attr("id");
	});
});

function re_load() { 
	}