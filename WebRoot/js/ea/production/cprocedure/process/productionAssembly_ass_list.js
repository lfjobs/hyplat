$(function(){

	var title="<form name='form' id='form' method='post'>生产组装管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;批次号：" 
			+ "<input type='text' id='likeJournalNum' style='width:100px;'>&nbsp;&nbsp;&nbsp;产品名称："
			+ "<input type='text' id='likeGoodsName' style='width:100px;'>&nbsp;&nbsp;&nbsp;责任人："
			+ "<input type='text' id='likeStaffName' style='width:100px;'>&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit'  name='submit' id='submit' style=\"display: none;\">&nbsp;&nbsp;&nbsp;<input type='button' id='tosearch' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title :title,
		minheight : 80,
		buttons : [{
			name : '开始组装',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}/*, {
			separator : true
			},{
			name : '打印',
			bclass : 'see',
			onpress : action
				// 当点击调用方法
			}*/, {
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
			case '开始组装' :
				if(goodsBillsID==""){
					alert("请选择!");
					break;
				}
				open(basePath+"ea/assembly/ea_getProductData.jspa?utboundOrderVo.goodsbillsid="+goodsBillsID+"&fiveClear="+fiveClear);
				break;
			case '设置每页显示条数':
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/assembly/ea_getProductAssemblyList.jspa?pageNumber="+pr+"&"+condition+"&fiveClear="+fiveClear;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
		}
	}

	$("#tosearch").live("click",function(){
		var likeJournalNum=$("#likeJournalNum").val();
		var likeGoodsName=$("#likeGoodsName").val();
		var likeStaffName=$("#likeStaffName").val();
		var like="?utboundOrderVo.journalnum="+likeJournalNum+"&utboundOrderVo.goodsname="
						+likeGoodsName+"&utboundOrderVo.staffName="+likeStaffName;
		window.location.href=basePath+"ea/assembly/ea_getProductAssemblyList.jspa"+like+"&fiveClear="+fiveClear;
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		goodsBillsID=this.id;
	});
});

function re_load(){
	window.location.href=window.location.href;
}