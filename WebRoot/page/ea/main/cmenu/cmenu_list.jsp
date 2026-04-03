<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>菜单列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<script type="text/javascript">
		$(function(){ 
    $('.flexme11').flexigrid({
		height: 300,
		width: 'auto',
		minwidth: 30,
		title: '菜单列表--查询',
		minheight: 80 , buttons: [ 
		 {
            name: '添加菜单',
            bclass: 'add',
			onpress : action //当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        },{
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
	});
    function action(com, grid){
        switch (com) {
            case '添加菜单':
            $('#cmenuList').attr("action","<%=basePath%>ea/cmenu/ea_editCMenu.jspa?pageNumber=${pageNumber}");
			document.cmenuList.submit.click();
                break;
            case '设置每页显示条数':
			var url="<%=basePath%>ea/cmenu/ea_getListCMenu.jspa?1=1";
				numback(url);
				break;
        }
    }
     $(".flexme11").find("select").each(function(){
                    $s = $(this).hide()
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s)
                })
 })
	function edit(menuID){
	            $('#cmenuList').attr("action","<%=basePath%>ea/cmenu/ea_editCMenu.jspa?pageNumber=${pageNumber}&cmenu.menuID="+menuID);
			document.cmenuList.submit.click();
		}
	function del(menuID){
	         if(confirm("确定要执行此操作吗?")){   
	          $('#cmenuList').attr("action","<%=basePath%>ea/cmenu/t_ea_delCMenu.jspa?pageNumber=${pageNumber}&cmenu.menuID="+menuID);
			document.cmenuList.submit.click();
		          }  
	             return false; 
		}
</script>
	</head>
	<body>
		<form name="cmenuList" id="cmenuList" method="post"><input type="submit" name="submit" style="display:none"/>
			<div class="main_main">
			<table class="flexme11">
				<thead>
					<tr >
					<th width="30" align="center">
							序号
						</th>
						<th width="80" align="center">
							菜单排序号
						</th>
						<th width="180" align="center">
							所属功能
						</th>
						<th width="130" align="center">
							菜单名称
						</th>
						<th width="220" align="center">
							菜单描述
						</th>
						<th width="120" align="center">
							操作
						</th>
					</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
					<s:iterator value="pageForm.list">
						<tr align="center" ondblclick="edit('<s:property value="menuID"/>')">
							<td><%=number%></td>
							<td>
								<s:property value="menuNumber" />
							</td>
							<td>
							    <s:select list="cealist" listKey="eaID" listValue="eaName" name="eaID" id="eaID" theme="simple" disabled="true"></s:select>
							</td>
							<td>
								<s:property value="menuName" />
							</td>
							<td>
								<s:property value="menuDesc" />
							</td>
							<td>
								<a href="#" onclick="edit('<s:property value="menuID"/>')"><img
										src="<%=basePath%>images/ea/main/edit.gif" width="16"
										height="16" title="修改" border="0" /></a>
								<a href="#" onclick="del ('<s:property value="menuID"/>')"><img
										src="<%=basePath%>images/ea/main/gtk-del.png" width="16"
										height="16" title="删除" border="0" /></a>
							</td>
							<%
								number++;
							%>
						</tr>
					</s:iterator>
					</tbody>
				</table>
				<s:token />
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath"
						value="ea/cmenu/ea_getListCMenu.jspa?pageNumber=${pageNumber}"></c:param>
				</c:import>
			</div>
		</form>
	</body>
</html>