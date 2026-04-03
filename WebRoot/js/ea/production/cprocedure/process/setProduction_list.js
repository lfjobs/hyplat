$(function(){

	var title="<form name='form' id='form' method='post'>设置生产量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;生产批次号：" 
			+ "<input type='text' id='likeBatchNumber' style='width:100px;'>&nbsp;&nbsp;&nbsp;产品名称："
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
			name : '提交',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '删除',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
				name : '打印',
				bclass : 'see',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},{
				name : '预览',
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
	function action(com, grid) {
		switch (com) {
			case '添加' :
				open(basePath+"ea/setpro/ea_addOrModifyPage.jspa?type=add&category="+category+"&fiveClear="+fiveClear);
				break;
			case '修改' :
				if(productionAmountID==""){
					alert("请选择");
					break;
				}
				if($("#"+productionAmountID).find("td").eq(8).text()=="已提交"){
					alert("已提交，不可修改");
					break;
				}
				open(basePath+"ea/setpro/ea_addOrModifyPage.jspa?type=edit&productionAmount.productionAmountID="+productionAmountID+"&category="+category);
				break;
			case '提交' :
				if(productionAmountID==""){
					alert("请选择");
					break;
				}
				if($("#"+productionAmountID).find("td").eq(8).text()=="已提交"){
					alert("已提交");
					break;
				}
				$("#forms").attr("target", "hidden").attr("action",basePath+"ea/setpro/ea_submit.jspa?productionAmount.productionAmountID="+productionAmountID+"&category="+category+"&fiveClear="+fiveClear);
				document.forms.submits.click();
				token = 2;
				break;
				break;
			case '删除' :
				if(productionAmountID==""){
					alert("请选择");
					break;
				}
				if($("#"+productionAmountID).find("td").eq(8).text()=="已提交"){
					alert("已提交,不可删除");
					break;
				}
				$("#forms").attr("target", "hidden").attr("action",basePath+"ea/setpro/ea_del.jspa?productionAmount.productionAmountID="+productionAmountID+"&category="+category);
				document.forms.submits.click();
				token = 2;
				break;
			case '打印' :
				if(productionAmountID==""){
					alert("请选择");
					break;
				}
				open(basePath+"ea/setpro/ea_seeOrPrint.jspa?type=see&productionAmount.productionAmountID="+productionAmountID+"&category="+category);
				break;
			case '预览' :
				if(productionAmountID==""){
					alert("请选择");
					break;
				}
				open(basePath+"ea/setpro/ea_seeOrPrint.jspa?type=print&productionAmount.productionAmountID="+productionAmountID+"&category="+category);
				break;
			case '设置每页显示条数':
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/setpro/ea_listPage.jspa?pageNumber="+pr+"&"+condition+"&category="+category;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
		}
	}

	$("#tosearch").live("click",function(){
		var likeBatchNumber=$("#likeBatchNumber").val();
		var likeGoodsName=$("#likeGoodsName").val();
		var likeStaffName=$("#likeStaffName").val();
		var like="?productionAmount.batchNumber="+likeBatchNumber+"&productionAmount.goodsName="
						+likeGoodsName+"&productionAmount.staffName="+likeStaffName;
		window.location.href=basePath+"ea/setpro/ea_listPage.jspa"+like+"&category="+category;
	});
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		productionAmountID=this.id;
	});
});

function re_load(){
	window.location.href=window.location.href;
}