$(function(){
	var title="<form name='form' id='form' method='post'>成品出库单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单据编号：" 
			+ "<input type='text' id='likeJournalnum' style='width:100px;'>&nbsp;&nbsp;&nbsp;出库人：" 
			+ "<input type='text' id='likeGoodsname' style='width:100px;'>&nbsp;&nbsp;&nbsp;类型："
			+ "<select style=\"width:70px;\" id='select'><option value='22'>未出库</option><option value='16'>已出库</option></select><input type='submit' " 
			+ " name='submit' id='submit' style=\"display: none;\">&nbsp;&nbsp;&nbsp;<input type='button' id='tosearch' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title :title,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '修改',
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
				name : '查看审核',
				bclass : 'see',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
				name : '确认出库',
				bclass : 'bc',
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
			case '添加' :
					open(basePath+"ea/finished/ea_getAddPageData.jspa?type=add"+"&category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName);
				break;
			case '修改' :
				if(id==""){
					alert("请选择！");
					break;
				}
				var br=true;
				$.ajax({
					url:basePath+"ea/finished/sajax_ea_ajaxCheckWhetherAudit.jspa?utboundOrderVo.cashierbillsid="+id,
					type:"post",
					async : false,
					success:function(data){
						var member = eval("(" + data + ")");
						var sta=member.sta;
						if(sta=="1"){
							br=false;
						}
					},
					error:function(){
						alert("数据获取失败");
					}
				});
				if(br){
					open(basePath+"ea/finished/ea_getEditPageData.jspa?utboundOrderVo.cashierbillsid="+id);
				}else{
					alert("该单据已审核，无法修改");
					break;
				}
				break;
			case '打印' :
				if(id==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/finished/ea_getToPrintPageData.jspa?utboundOrderVo.cashierbillsid="+id);

				break;
			case '查看审核' :
				if(id==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/finished/ea_getReviewAuditPageData.jspa?utboundOrderVo.cashierbillsid="+id);
				break;
			case '确认出库' :
				if(id==""){
					alert("请选择！");
					break;
				}
				if($("#"+id).parents("tr").find("span").eq(4).text()=="已出库"){
					alert("该单据已出库"); break;
				}
				var br=true;
				$.ajax({
					url:basePath+"ea/finished/sajax_ea_ajaxCheckWhetherAudit.jspa?utboundOrderVo.cashierbillsid="+id,
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
				if(!br){
					alert("该单据还未审核！");
					break;
				}else{
					if(confirm("确定出库")){
						var url=basePath+"ea/finished/ea_ConfirmationOfLibrary.jspa?utboundOrderVo.cashierbillsid="+id;
						$("#forms").attr("target", "hidden").attr("action",url);
						document.forms.submits.click();
						token = 2; 
					}
				}
				break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/finished/ea_getHomePageData.jspa?pageNumber="+pr+"&category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName;
				}else if(pr==null){
					break;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
		}
	}
	
	$("#tosearch").click(function(){
		url=basePath+"ea/finished/ea_getHomePageData.jspa?utboundOrderVo.journalnum="+$("#likeJournalnum").val()+"&utboundOrderVo.ctUserName="+$("#likeGoodsname").val()+"&type="+$("#select option:selected").val()+"&category="+category+"&fiveClear="+fiveClear+"&fiveClearName="+fiveClearName;
		window.location.href=url;		
	});
	
	$(".dps").each(function(){
		var str=$(this).find("span").eq(11).text().split(" ");
		$(this).find("span").eq(11).text(str[0]);
	});
	
	//每行的点击事件
	$("tr[id]").live("click",function(){
		$(this).find(".radio").attr("checked","checked");
		if($(this).attr("name")=="goods")
			goodsId=$(this).find(".radio").attr("id");
		if($(this).attr("name")=="dps")
			id=$(this).find(".radio").attr("id");
	});
});

function re_load(){
	document.location.href = document.location.href;
}