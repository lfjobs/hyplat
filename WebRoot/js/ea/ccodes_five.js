$(document).ready(function() {
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	tree.insertNewChild("0", treeid, treename, 0, 0, 0, 0);
	// if (basic == 'scode20141029rnzhjs7ap60000000004') {
	// // tree.insertNewChild("0","001","物品管理",0,0,0,0);
	// tree.insertNewChild("0", "002", "库存编码树", 0, 0, 0, 0);
	// }
	tree.setOnClickHandler(function() {
		$("#mainframe").contents().empty();
		treeid = tree.getSelectedItemId();
		var url = "";
		if(treeid == 'scode20150105fbdz4z3zer0000000004'){//基本工资设定
			url = basePath+"ea/cofi/ea_findItem.jspa?codeID="+treeid+"&wstate=0&xmtype=013411";
		}else if(treeid == 'scode20150105fbdz4z3zer0000000005'){//职务工资设定
			url = basePath+"ea/cofi/ea_findItem.jspa?codeID="+treeid+"&wstate=1&xmtype=013412";
		}else if(treeid == 'scode20150105fbdz4z3zer0000000007'){//考勤工资设定
			url = basePath+"ea/cofi/ea_findItem.jspa?codeID="+treeid+"&wstate=2&xmtype=013414";
		}else if(treeid == 'scode20150105fbdz4z3zer0000000006'){//考评工资设定
			url = basePath+"ea/cofi/ea_findItem.jspa?codeID="+treeid+"&wstate=3&xmtype=013413";
		}else if(treeid == 'scode20150128g2jivghrq80000000002'){//奖惩工资设定
			url = basePath+"ea/cofi/ea_findItem.jspa?codeID="+treeid+"&wstate=4&xmtype=013417";
		}else if(treeid == 'scode20150105fbdz4z3zer0000000008'){//计件工资设定
			url = basePath+"ea/cofjj/ea_findItem.jspa?xmtype=013415";
		}else if(treeid == 'scode20150105fbdz4z3zer0000000009'){//级差工资设定
			url = basePath+"ea/cofra/ea_findItem.jspa?&xmtype=013416";
		}
		if(url != ""){
			$("#mainframe").css({"height":"auto"}).attr("src",url);
			$(window).resize();
		}else{
			$("#mainframe").contents().empty();
		}
		treename = tree.getItemText(treeid);
		// Submit_onclick();//让左侧导航栏隐藏
		$("#codeName").attr("value", treename);
		tree.deleteChildItems(treeid);
		load();
	});
	load();
});
function load() {
	var getListCCodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid
			+ "&date=" + new Date().toLocaleString();
$.ajax({
	url : encodeURI(getListCCodeurl),
	type : "get",
	async : true,
	dataType : "json",
	success : function cbf(data) {
		var member = eval("(" + data + ")");
		var codeList = member.codeList;
		if (null == codeList) {
			return;
		}
		for ( var i = 0; i < codeList.length; i++) {
			tree.insertNewChild(treeid, codeList[i].codeID,
					codeList[i].codeSn + codeList[i].codeValue, 0,
					0, 0, 0);
			tree.setUserData(codeList[i].codeID, "groupSn",
					codeList[i].groupSn);
		}
	},
	error : function cbf(data) {
		alert("数据获取失败！");
	}
});

}