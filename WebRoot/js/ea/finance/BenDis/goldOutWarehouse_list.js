$(function(){

var query = "<form id='form' name='form' method='post'>金币出库单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
			"单据编号：<input type='text' id='likeJournalNum' style='width:120px'>&nbsp;&nbsp;&nbsp;" +
			"责任人：<input type='text' id='likeStaffName' style='width:120px'>&nbsp;&nbsp;&nbsp;" +
			"单据时间：<input type='text' id='likeSdate' style='width:100px'" +
			"  onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\">&nbsp;&nbsp;&nbsp;" +
			"-&nbsp;&nbsp;&nbsp;<input type='text' id='likeEdate' style='width:100px'" +
			"  onfocus=\"WdatePicker({dateFmt:'yyyyMMdd'})\">&nbsp;&nbsp;" +
			"&nbsp;&nbsp;<input type='submit' id='submit' name='submit' style='display: none;'><input " +
			" type='button' id='tosearch' class='input-button' value='查询' style='margin:0px;margin-left:5px;width:35px;'></form>";
	
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		title : query,
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
			}, {
				separator : true
			},{
			name : '设置每页显示条数',
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
				if(index!=0&&index!=1){
					if(index==2)
						str+=$(this).text();
					else
						str+=","+$(this).text();
				}
			});
			window.location.href=basePath+"ea/goldStock/ea_GoldExportExcelTable.jspa?type=01&title=金币出库单&str="+str;
			break;
		case '查看预览' :
			if(id==""){
				alert("请选择！");
				return;
			}
			open(basePath+"ea/goldStock/ea_getGoldPreviewPage.jspa?type=00&utboundOrderVo.cashierbillsid="+id);
			break;
		case '设置每页显示条数':
			var pr=prompt("请输入0~20的正整数");
			if(pr>0&&pr<=20){
				window.location.href=basePath+"ea/goldStock/ea_getGoldOfTheHomePage.jspa?type=01&pageNumber="+pr;
			}else{
				alert("您输入的有误，请重新输入");
			}
			break;
	}
}
$(function(){
	$("#tosearch").click(function(){
		var journalNum=$("#likeJournalNum").val();
		var staffName=$("#likeStaffName").val();
		var sdate=$("#likeSdate").val();
		var edate=$("#likeEdate").val();
		if($("#likeEdate").val()<$("#likeSdate").val()&&$("#likeEdate").val()){
			alert("结束日期不可小于开始日期！");
			return;
		}
		window.location.href=basePath+"ea/goldStock/ea_getGoldOfTheHomePage.jspa?type=01" +
				"&utboundOrderVo.journalnum="+journalNum+
				"&utboundOrderVo.staffName="+staffName+
				"&sdate="+sdate+"&edate="+edate;
	});
	
	$(".bodyTr").each(function(){
		var goodsId=$(this).attr("id");
		var $t=$(this);
		$.ajax({
			url:basePath+"ea/goldStock/sajax_ea_ajaxGetUserOwnedCompany.jspa?utboundOrderVo.cashierbillsid="+goodsId,
		    type:"get",
		    success:function(data){
		    	var member = eval("(" + data + ")");
				var com = member.com;
				$t.find("td").eq(1).text(com.companyName);
		    },
		});
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		id=$(this).attr("id");
	});
});