

$(document).ready(function() {
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	tree.insertNewChild("0", companyID, companyName, 0, 0, 0, 0);
	$.ajaxSetup({async:false});
	treeid = companyID;
	treeload();
	tree.setOnClickHandler(function() {
				treeid = tree.getSelectedItemId();
				treeload();
				//Submit_onclick();
	});
});
function treeload(){
	treename = tree.getItemText(treeid);
				parentid = tree.getParentId(treeid);
				parentname = tree.getItemText(parentid);
				tree.deleteChildItems(treeid);
				var url1 = basePath + "ea/corganization/sajax_ea_getOrganizationList.jspa?organizationID="+treeid+"&date="+new Date().toLocaleString();
					$.ajax({
						 url: encodeURI(url1),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
									var member = eval("(" + data + ")");
									var nologin = member.nologin;
								                  		if(nologin){
								                  		document.location.href =basePath+"page/ea/not_login.jsp";
								                     }
									var organizationList = member.organizationList;
									if (null == organizationList) {
										return;
									}
									for (var i = 0; i < organizationList.length; i++) {
										tree.insertNewChild(treeid,organizationList[i].organizationID,organizationList[i].organizationName,0, 0, 0, 0);
		
									}
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					});
				$("#mainframe11").attr(
						"src",basePath+
						"ea/departmentpost/ea_getOrgPostListByOrg.jspa?departmentPost.organizationID="
								+ treeid + "&treename=" + treename+"&star=00");// 点击刷新机构列表
			
}