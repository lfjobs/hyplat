<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色列表</title>
<!-- 后台管理 角色管理  -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var token = 0;
var companyID='${company.companyID}'
var companyName='${company.companyName}';
var roleID="";
var ziUrl;
var methodX="N";
$(function(){ 
    $('.flexme11').flexigrid({
        allDouble:true,
		height: 150,
		width: 'auto',
		minwidth: 30,
		title: '角色列表',
		minheight: 80 , buttons: [ 
		 {
            name: '添加角色',
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
        },{
            name: '权限帐号',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
            separator: true
        }]
});
function action(com, grid){
        switch (com) {
            case '添加角色':
             $("#croleList").attr("action","<%=basePath%>page/ea/main/crole/crole_edit.jsp?pageNumber=${pageNumber}&companyID=${company.companyID}&companyName=${company.companyName}");
            document.croleList.submit.click();
                break;
            case '设置每页显示条数':
			var url="<%=basePath%>ea/crole/ea_getListCRole.jspa?1=1";
				numback(url);
				break;
			case '权限帐号':
				if(roleID==""){
					alert("请选择");
					return;	
				}
				methodX="Y";
				ziUrl=basePath
						   + "ea/caccount/ea_getListCAccount.jspa?methodX="+methodX+"&roleIDX=";
				$("#mainframe").css({"height" :"auto"})
						.attr(
								"src",ziUrl+ roleID);		
				$(window).resize();
				break;  
        }
    }
    $("tr[id]").click(function(){
    	roleID = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		if(ziUrl){
			$("#mainframe").attr("src",ziUrl+ roleID);		
		}
    });
    
 });
 	//编辑
	function edit(roleID){
	        $('#croleList').attr("action","<%=basePath%>ea/crole/ea_editCRole.jspa?pageNumber=${pageNumber}&crole.roleID="+roleID+"&companyID=${company.companyID}&companyName=${company.companyName}");
			document.croleList.submit.click();
	}
	//删除
	function del(roleID){
	          if(confirm("确定要执行此操作吗?")){   
	            $('#croleList').attr("target","hidden").attr("action","<%=basePath%>ea/crole/t_ea_delCRole.jspa?pageNumber=${pageNumber}&crole.roleID="+roleID+"&date="+new Date());
		    	document.croleList.submit.click();
		    	$("tr#"+roleID).remove();
		    	token = 2;
		      }  
	}
	//分配权限
   function toAllot(roleID){
       $('#croleList').attr("action","<%=basePath%>ea/croleallot/ea_getListSEAForAllot.jspa?pageNumber=${pageNumber}&roleID="+roleID);
		document.croleList.submit.click();
   }
   //重新载入
	function re_load(){
	 	if(token){
			document.location.href="<%=basePath%>ea/crole/ea_getListCRole.jspa?pageNumber=${pageNumber}";
		}
	}
</script>
</head>
<body>
<form name="croleList" id="croleList" method="post"><input type="submit" name="submit" style="display:none"/>
<div class="main_main">
		<table class="flexme11">
								<thead>
							    	   	<tr>
							             	<th width="23" align="center">
												序号
											</th>
											<th width="40" align="center">
												选择
											</th>
											<th width="180" align="center">
												公司名称
											</th>
											<th width="100" align="center">
												部门名称
											</th>
											<th width="100" align="center">
												岗位名称
											</th>
											<th width="100" align="center">
												职务名称
											</th>
											<th width="300" align="center">
												角色描述
											</th>
											<th width="100"  align="center">
												操作
											</th>
											<th width="100"  align="center">
												授权
											</th>
							            </tr>
							    </thead>
          <%int number =1;  %>
          <tbody>
           <s:iterator value="pageForm.list">
            <tr id="<s:property value="roleID"/>" align="center" height="22" class="JQueryflexme" ondblclick="edit('<s:property value="roleID"/>')">
	            <td><%=number%></td>
	            <td> <input  type="radio" name="a" class="JQuerypersonvalue"/>  </td>
	            <td> <s:property value="companyName"/></td>
	            <td> <s:property value="organizationNameDesc"/></td>
	            <td> <s:property value="opostName"/></td>
	            <td> <s:property value="roleName"/></td>
	            <td> <s:property value="roleDesc"/></td>
	            <td>
	             <a href="#" onclick="edit('<s:property value="roleID"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
	             <a href="#" onclick="del('<s:property value="roleID"/>')"><img src="<%=basePath%>images/ea/main/gtk-del.png" width="16" height="16" title="删除" border="0"/></a>
	            </td>
	            <td> <a href="#" onclick="toAllot('<s:property value="roleID"/>')">设置权限</a></td>
            <%number++; %>
          </tr>
          </s:iterator>
          </tbody>
        </table>
        <c:import url="../../page_navigator.jsp">
			<c:param name="actionPath" value="ea/crole/ea_getListCRole.jspa?pageNumber=${pageNumber}"></c:param>
		</c:import>
		<div>
				<iframe src="" name="main" style="width:100%;height:0;"
					marginwidth="0"  marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0"  
					vspale="0"> </iframe>
			</div>
        <s:token/>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height / 2 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 25 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 35 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
			setTimeout(function(){ 
			   var _height = $(window).height();		
			   if($("#mainframe").height() > 0){
			       $(".bDiv").css({"height": _height /2 - 30 - 26 - 40 + "px"});
			       $("#mainframe").css({"height": _height / 2 - 25 + "px"});
			   }else{		    
			       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 35 + "px"});
			       $("#mainframe").css({"height": 0 + "px"});
			   }
			},100);
	    }); 
   });
</script>  
</body>
</html>