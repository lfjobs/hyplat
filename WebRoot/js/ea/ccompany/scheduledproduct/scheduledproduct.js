

$(document).ready(function () {
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
	//设置是否允许拖拽
	tree.enableDragAndDrop(false);
	//设置是否允许选中节点名称高亮显示
	tree.enableHighlighting(1);
	//设置是否允许显示复选框
	tree.enableCheckBoxes(0);
	//允许三种状态的复选框（全选、不选、部分选中）
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + "js/tree/dhx_skyblue");
	
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	//添加子节点
	tree.insertNewChild("0", "0001", "\u6240\u6709\u5355\u636e", 0, 0, 0, 0);
	$.ajaxSetup({async:false});
	var url1 = basePath + "/ea/sp/sajax_ea_getBillTypes.jspa";
	$.ajax({
		//设置发送请求地址
		url:encodeURI(url1), 
		//设置请求方式
		type:"get", 
		//设置是否是同步请求
		async:true, 
		//设置预期服务器返回的数据类型
		dataType:"json", 
		//请求成功后的回调函数
		success:function cbf(data) {
		var member = eval("(" + data + ")");
		var nologin = member.nologin;
		if (nologin) {
			document.location.href = basePath + "page/ea/not_login.jsp";
		}
		var BillTypes = member.BillTypes;
		if (null == BillTypes) {
			return;
		}
		for (var i = 0; i < BillTypes.length; i++) {
			tree.insertNewChild("0001", BillTypes[i].codePID, BillTypes[i].codeValue, 0, 0, 0, 0);
		}
	}, error:function cbf(data) {
		alert("\u6570\u636e\u83b7\u53d6\u5931\u8d25\uff01");
	}});
	//各节点单击事件
	tree.setOnClickHandler(function () {
		//获取当前节点id
		treeid = tree.getSelectedItemId();
		//获取当前节点名称
		treename = tree.getItemText(treeid);
		if(treename=="所有单据"){
			 parent.document.getElementById("mainFrame").src=basePath+"/ea/cashierbills/ea_getCashierBillsList.jspa";
		}else{
			var parms = basePath + "/ea/cashierbills/ea_toSearch.jspa?BType="+treename+"&search=search";
			parent.document.getElementById("mainFrame").src=encodeURI(parms);
		}
	});
});
