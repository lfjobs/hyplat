

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
	tree.insertNewChild("0", "0001", "审核管理", 0, 0, 0, 0);
	tree.insertNewChild("0001", "00011", "未审核", 0, 0, 0, 0);
	tree.insertNewChild("0001", "00012", "已审核", 0, 0, 0, 0);
	//各节点单击事件
	tree.setOnClickHandler(function () {
		parent.document.getElementById("mainFrame").src=basePath+"page/mysl/CheckManage-right.jsp";
		//获取当前节点id
		treeid = tree.getSelectedItemId();
		//获取当前节点名称
		treename = tree.getItemText(treeid);
		var parms="";
		if(treename=="审核管理"){
			parms=basePath+"/ea/receiptcheck/ea_getDtMycheckList.jspa";
		}else if(treename=="未审核"){
			parms = basePath + "/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=01";
		}else {
			parms = basePath + "/ea/receiptcheck/ea_getDtMycheckList.jspa?auditorstatus=02";
		}
		parent.document.getElementById("mainFrame").src=encodeURI(parms);
	});
});
