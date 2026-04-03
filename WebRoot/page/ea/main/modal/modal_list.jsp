<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>自定义导航树</title>
		<link href="<%=basePath%>css/ea/human/staff_post.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
	<script type="text/javascript">
		var treeName=parent.tree.getSelectedItemText();
		var treeId=parent.tree.getSelectedItemId();
	</script>
	</head>
	<body>
		<div id="body_02">
			<table width="100%" height="26" align="center" cellspacing="0"
				cellpadding="1" style="font-size: 12px;" class="bannb_01">
				<tr>
					<td height="24" align="left" valign="top" class="txt01">
						&nbsp;分配机构
					</td>
				</tr>
			</table>
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;"
				class="clear">
				<tr>
					<td width="1%" height="26">&nbsp;
						
					</td>
					<td width="2%">
						<div class="list_add01"></div>
					</td>
					<td width="97%">
						<span class="link02" id="staffname">分配机构-<script>document.write(treeName)</script></span>
					</td>
				</tr>
			</table>
			<table height="100" border="0" align="center"
				cellpadding="0" cellspacing="0">
				<tr>
					<td width="45%" align="left" valign="top">
						<div id="aadTree" class="text_tree"
							style="overflow: auto; z-index: 99; width: 300px"></div>
					</td>
				</tr>
			</table>
			<table width="99%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="35" align="center">
						<input type="button" class="btn02 jquerysub" name="button"
							value="确定" />
						<input type="button" class="btn02" id="cosed" name="button"
							value="关闭" />
					</td>
				</tr>
			</table>
		</div>
	</body>
	<script>
    	var basePath = "<%=basePath%>";
        tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
        tree.enableDragAndDrop(false);
        tree.enableHighlighting(1);
        tree.enableCheckBoxes(0);
        tree.enableThreeStateCheckboxes(false);
        tree.setSkin('<%=basePath%>js/tree/dhx_skyblue');
        tree.setImagePath("<%=basePath%>js/tree/codebase/imgs/");
        tree.loadXML("<%=basePath%>js/tree/common/tree_b.xml");

        tree.enableCheckBoxes(1);//设置复选框
    	tree.enableThreeStateCheckboxes(true);//允许半选状态
        //tree1.enableCheckBoxes(0);
        tree.setOnClickHandler(function(){
        	treeid = tree.getSelectedItemId();
        	if(tree.isItemChecked(treeid) == "0"){
        		tree.setCheck(treeid,1);
        	}else{
        		tree.setCheck(treeid,0);
        	}
        		
        });
             //单位下所有的部门列表
           var url = basePath +"ea/Cmodal/sajax_ea_getListCmodal.jspa";
           $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        data:"organizationID="+treeId,
                        dataType: "json",
                        success: function cbf(data){
		                var member = eval("(" + data + ")");
		                var nologin = member.nologin;
					                  if(nologin){
					                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
					                  }
					                  
		                var modalList = member.modalList;//0 id ,1 pid ,2 name
		                var checkList = member.checkList;//拼接好的字符串
		                
		                tree.insertNewChild('0', 'root', '全选', 0, 0, 0, 0);
		                if (null != modalList) {
		                      for (var i = 0; i < modalList.length; i++) {
		                      	if(modalList[i].pmodalid=="root"){
		                      		tree.insertNewChild('root', modalList[i].modalid, modalList[i].modalname, 0, 0, 0, 0);
		                      		if(checkList.indexOf(modalList[i].modalid)>-1){
		                      			tree.setCheck(modalList[i].modalid,1);
		                      		}
		                      		findChild(modalList[i].modalid,modalList,checkList);
		                      	}
		                      }
		                }
		            }, 
		             error: function cbf(data)
		             {
		                           alert("数据获取失败！")
		             }
		});
		
		function findChild(pare,vals,checkArea){
				for (var i = 0; i < vals.length; i++) {
                	if(vals[i].pmodalid==pare){
                		tree.insertNewChild(pare, vals[i].modalid, vals[i].modalname, 0, 0, 0, 0);
                		if(checkArea.indexOf(vals[i].modalid)>-1){
                  			tree.setCheck(vals[i].modalid,1);
                  		}
                	findChild(vals[i].modalid,vals,checkArea);
                	}
                }
		}
        $(".jquerysub").click(function(){
        	var menuids = tree.getAllCheckedBranches();
        	var orgId=treeId;
        	var parm=new Array();
        	parm.push("menuids="+menuids);
        	parm.push("organizationID="+orgId);
        	$.ajax({
        		 url: basePath+"ea/Cmodal/sajax_ea_saveMenu.jspa",
                 type: "post",
                 data:parm.join("&"),
                 dataType: "json",
                 success: function cbf(data){
                	 alert("操作成功");
                 }
        	});
        })
    </script>
</html>
