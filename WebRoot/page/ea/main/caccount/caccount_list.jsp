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
<title>z</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript">
var methodX='${methodX}';
var roleIDX='${roleIDX}';
var  search='${search}';
$(function(){ 
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20 
		}).jqmAddClose('.close');  
    $('.flexme11').flexigrid({
        allDouble:true,
		height: methodX=="Y"?142:300,
		width: 'auto',
		minwidth: 30,
		title: '账号列表',
		minheight: 80 ,  buttons: [ 
		 {
            name: '添加账号',
            bclass: 'add',
			onpress : action //当点击调用方法
        }, {
            // 设置分割线  
            separator: true
        },{
            name:'查询',
            bclass:'mysearch',
            onpress : action //当点击调用方法
        },{
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
            case '添加账号':
             $('#caccountList').attr("action","<%=basePath%>ea/caccount/ea_editCAccount.jspa?pageNumber=${pageNumber}&roleIDX="+roleIDX+"&methodX="+methodX);
			document.caccountList.submit.click();
                break;
            case '查询':
            $("#jqModelSearch").jqmShow();
            $("#roleName").val("请选择");
            break;
            
            case '设置每页显示条数':
			var url="<%=basePath%>ea/caccount/ea_getListCAccount.jspa?1=1&roleIDX="+roleIDX+"&methodX="+methodX;
				numback(url);
				break;
        }
    }
     $("#tosearch").click(function() {
		$("#SearchForm").attr("action","<%=basePath%>/ea/caccount/ea_toSearch.jspa?pageNumber=${pageNumber}");
		document.SearchForm.submit.click();
	});
    
    $(".flexme11").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s);
                });
    $(".Max").blur(function(){
		if($(this).val().length>50)
		{
			alert("请输入小于50的字符数");
			$(this).val("");
			}
	});
    
     })
	function edit(accountID){
	        $('#caccountList').attr("action","<%=basePath%>ea/caccount/ea_editCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX);
			document.caccountList.submit.click();
	}
	function del(accountID){
	     if(confirm("确定要执行此操作吗?")){   
	        $('#caccountList').attr("action","<%=basePath%>ea/caccount/t_ea_delCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX);
			document.caccountList.submit.click();
	        }  
	     return false; 
	}
	function toAllot(accountID,accountName){
	    var url = "<%=basePath%>page/ea/main/caccount/caccount_post.jsp?pageNumber=${pageNumber}&accountName="+accountName+"&accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX;
	    url = encodeURI(url);
	    document.location.href = url;
	}     		
</script>
</head>
<body>
	<form name="caccountList" id="caccountList" method="post">
		<input type="submit" name="submit" style="display:none" />
		<div class="main_main">
			<table class="flexme11">
				<thead>
					<tr>
						<th width="30" align="center">序号</th>
						<th width="80" align="center">账号责任人名称</th>
						<th width="80" align="center">登录帐号</th>
						<th width="220" align="center">职务名称</th>
						<th width="130" align="center">帐号状态</th>
						<th width="130" align="center">在线情况</th>
						<th width="120" align="center">操作</th>
						<th width="100" align="center">授权</th>
					</tr>
				</thead>
				<%
					int number = 1;
				%>
				<tbody>
					<s:iterator value="pageForm.list">
						<tr align="center"
							ondblclick="edit('<s:property value="accountID"/>')">
							<td><%=number%></td>
							<td><s:property value="accountName" /></td>
							<td><s:property value="accountEmail" /></td>
							<td><s:select list="%{#request.roleList}" listKey="roleID"
									listValue="roleName" name="roleID" theme="simple"
									disabled="true">
								</s:select></td>
							<td><c:if test="${accountStatus == '00'}">正常</c:if> <c:if
									test="${accountStatus == '02'}">停用</c:if></td>
							<td><c:if test="${accountOnLine == '00'}">离线</c:if> <c:if
									test="${accountOnLine == '01'}">在线</c:if></td>
							<td><a href="#"
								onclick="edit('<s:property value="accountID"/>')"><img
									src="<%=basePath%>images/ea/main/edit.gif" width="16"
									height="16" title="修改" border="0" /> </a> <a href="#"
								onclick="del('<s:property value="accountID"/>','<s:property value="accountEmail" />')"><img
									src="<%=basePath%>images/ea/main/gtk-del.png" width="16"
									height="16" title="删除" border="0" /> </a></td>
							<td><a href="#"
								onclick="toAllot('<s:property value="accountID"/>','<s:property value="accountName" />')">机构授权</a>
							</td>
							<%
								number++;
							%>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/caccount/ea_getListCAccount.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
			<s:token />
		</div>
	</form>
	<!--搜索窗口 -->
	<form name="SearchForm" id="SearchForm" method="post">
		<div class="jqmWindow" style="width: 400px;right: 25%;top:10%;"
			id="jqModelSearch">
			<input type="submit" name="submit" style="display:none" />
			<div class="drag">
				查询信息
				<div class="close"></div>
			</div>
			<table width="396" id="SearchTable">
				<tr>
					<td width="123" align="right">职务名称：</td>
					<td>
						<s:select 
					        headerKey="" headerValue="请选择"
 					        list="%{#request.roleList}" listKey="roleID"
							listValue="roleName" name="caccount.roleID" id="roleName" theme="simple">
						</s:select>
					</td>
				</tr>
				<tr>
					<td width="123" align="right">账号状态：</td>
					<td><select name="caccount.accountStatus">
							<option value="">请选择</option>
							<option value="00">正常</option>
							<option value="02">停用</option>
					</select>
					</td>
				</tr>
				<tr>
					<td width="123" align="right">在线情况：</td>
					<td><select name="caccount.accountOnLine">
							<option value="">请选择</option>
							<option value="00">离线</option>
							<option value="01">在线</option>
					</select>
					</td>
				</tr>
				<tr>
					<td width="123" align="right">账号责任人名称：</td>

					<td><input id="accountName" style="width:195px"
						name="caccount.accountName" class="Max" /></td>
				</tr>
				<tr>
					<td width="123" align="right">登陆账号：</td>

					<td><input id="accountEmail" style="width:195px"
						name="caccount.accountEmail"  class="Max" /></td>
				</tr>		
			</table>
			<div align="center">
				<input type="button" class="input-button" id="tosearch" value=" 查询 " /><input
					name="search" type="hidden" value="search" />
			</div>
		</div>
	</form>
	<script type="text/javascript">
    setTimeout(function(){ 
	    $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    },100);
    
    $(window).resize(function(){ 
		setTimeout(function(){ 					    
		   $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
		},100);
    }); 
</script>
</body>
</html>