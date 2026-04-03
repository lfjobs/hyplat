
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
		<title>分配机构</title>
		<!-- 后台管理中的分配机构 -->
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
	</head>
	<script type="text/javascript">
		var methodX='<%=request.getParameter("methodX")%>';
		var roleIDX='<%=request.getParameter("roleIDX")%>'; 
	</script>
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
						<span class="link02" id="staffname">分配机构-<%=request.getParameter("accountName")%></span>
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
					<td width="11%" align="center">
						<table>
						<tr><td><div class="right_dan" id="rightdan"></div></td></tr>
						<tr><td><div class="left_dan" id="leftdan"></div></td></tr>
						<tr><td><div class="left_suang" id="leftsuang"></div></td></tr>
						</table>
					</td>
					<td width="44%" align="left" valign="top">
						<div id="text_tree" class="text_tree"
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

        tree1 = new dhtmlXTreeObject("text_tree", "100%", "100%", 3);
        tree1.enableDragAndDrop(false);
        tree1.enableHighlighting(1);
        tree.enableCheckBoxes(1);//设置复选框
    	tree.enableThreeStateCheckboxes(true);//允许半选状态
        //tree1.enableCheckBoxes(0);
        tree1.enableThreeStateCheckboxes(false);
        tree1.setSkin('<%=basePath%>js/tree/dhx_skyblue');
        tree1.setImagePath("<%=basePath%>js/tree/codebase/imgs/");
        tree1.loadXML("<%=basePath%>js/tree/common/tree_a.xml");
          var accountID='<%=request.getParameter("accountID")%>';
           var accountName='<%=request.getParameter("accountName")%>';
        //tree.setOnDblClickHandler(function(){
        //    treeid = tree.getSelectedItemId();
        //    treename = tree.getItemText(treeid);
        //   if(tree1.getItemText(treeid)){return;}
        //     tree1.insertNewChild("3",treeid,treename,0,0,0,0);return;
        //  
        //})
        tree.setOnClickHandler(function(){
        	treeid = tree.getSelectedItemId();
        	if(tree.isItemChecked(treeid) == "0"){
        		tree.setCheck(treeid,1);
        	}else{
        		tree.setCheck(treeid,0);
        	}
        		
        });
        tree1.setOnDblClickHandler(function(){
           treeid1 = tree1.getSelectedItemId();
           tree1.deleteItem(treeid1);
           })
           //账号所属的部门列表
           var searchurl=  basePath +"ea/caccount/sajax_ea_getOrganizationForAccount.jspa?accountID="+encodeURI(accountID)+"&date="+new Date().toLocaleString();
            $.ajax({
                        url: searchurl,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
                var member = eval("(" + data + ")");
                 var nologin = member.nologin;
                 if(nologin){
                 document.location.href ="<%=basePath%>page/ea/not_login.jsp";
                 }
                var organizationList = member.organizationList;
                var companylist = member.companylist;
                
                
                if (null != organizationList) {
                      for (var i = 0; i < organizationList.length; i++) {
                        tree1.insertNewChild("3", organizationList[i].organizationID, organizationList[i].organizationName, 0, 0, 0, 0);
                      }
                }
                if (null != companylist) {
                    for (var i = 0; i < companylist.length; i++) {
                      tree1.insertNewChild('3', companylist[i].companyID, companylist[i].companyName, 0, 0, 0, 0);
                    }
                }
           },
           error: function cbf(data){
                           alert("数据获取失败！")
                        }
                    });
             //单位下所有的部门列表
           var url = basePath +"ea/office/sajax_ea_getOrganizationList.jspa?organizationID=0&date="+new Date().toLocaleString();
           $.ajax({
                        url: url,
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
		                var member = eval("(" + data + ")");
		                var nologin = member.nologin;
					                  if(nologin){
					                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
					                  }
					                  
		                var organizationList = member.organizationList;
		                var companylist = member.companylist;
		                tree.insertNewChild('0', '00a', '全选', 0, 0, 0, 0);
		                if (null != organizationList) {
		                      for (var i = 0; i < organizationList.length; i++) {
		                      	if(organizationList[i].organizationPID.indexOf("company")>-1){
		                      		tree.insertNewChild('00a', organizationList[i].organizationID, organizationList[i].organizationName, 0, 0, 0, 0);
		                      		findChild(organizationList[i].organizationID,organizationList);
		                      	}
		                      }
		                }
		                 if (null != companylist) {
		                    for (var i = 0; i < companylist.length; i++) {
		                      tree.insertNewChild('00a', companylist[i].companyID, companylist[i].companyName, 0, 0, 0, 0);
		                    }
		                }
		            }, 
		             error: function cbf(data)
		             {
		                           alert("数据获取失败！")
		             }
		});
		
		function findChild(pare,vals){
				for (var i = 0; i < vals.length; i++) {
                	if(vals[i].organizationPID==pare){
                		tree.insertNewChild(pare, vals[i].organizationID, vals[i].organizationName, 0, 0, 0, 0);
                	}
                }
		}
        $(".jquerysub").click(function(){
          var organizations=tree1.getAllSubItems("3");
          document.location.href= basePath+"ea/caccount/ea_accountRedeployOrganization.jspa?pageNumber=<%=request.getParameter("pageNumber")%>&organizations="+organizations+"&accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX;
        })
        $("#cosed").click(function(){
          document.location.href= basePath+"/ea/caccount/ea_getListCAccount.jspa?pageNumber=<%=request.getParameter("pageNumber")%>&roleIDX="+roleIDX+"&methodX="+methodX;
        })
        
       
        $("#rightdan").click(function(){
        	tree1.deleteChildItems("3");
        
            var treeids = tree.getAllCheckedBranches();
            var tids = treeids.split(",");
            
            for(var i = 0 ; i < tids.length ; i++){
           		treeid = tids[i];
            	if(treeid == "00a" || treeid == 0){continue;}
            	treename = tree.getItemText(treeid);
            	if(tree1.getItemText(treeid)){continue;}
             	tree1.insertNewChild("3",treeid,treename,0,0,0,0);
            }
            return;
        })
         $("#leftdan").click(function(){
           treeid1 = tree1.getSelectedItemId();
           tree1.deleteItem(treeid1);
        })
         $("#leftsuang").click(function(){
           tree1.deleteChildItems(3);
        })
    </script>
</html>
