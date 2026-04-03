<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="hy.ea.bo.Company"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Company c = (Company)session.getAttribute("currentcompany"); 
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
</head>
<body>
<form name="croleList" id="croleList" method="post"><input type="submit" name="submit" style="display:none"/>
<div class="main_main">
		<table class="flexme11">
								<thead>
							    	   	<tr>
							             	<th width="30" align="center">
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
	            <td> <s:property value="companyName"/>
	            	<input type="hidden" id="companyIDD" value="<s:property value='companyID'/>"/>
	            </td>
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
			<c:param name="actionPath" value="ea/ccrole/ea_getListCRole.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
		</c:import>
		<div>
				<iframe src="" name="main" style="width:100%;height:0;"
					marginwidth="0" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0"  
					vspale="0"> </iframe>
			</div>
        <s:token/>
</div>
</form>
 <!--搜索窗口 -->
         <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width:500px;top: 10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table width="421" height="153" id="SearchTable" align="center">
                <tr>
                        <td align="right">
                            公司名称：                        </td>
                      <td>
                        <select id="companyID" name="crole.companyID">
							</select>
                    </td>
                  </tr>
			<tr>
                        <td width="118" align="right">
                            部门名称：                        </td>
                        <td width="314">
                           <select id="orgID" name="crole.organizationName" >
                           	<option value="">请选择公司</option>
                           </select>
                        </td>
                  </tr>
					<tr>
                        <td align="right">
                           职务名称：                        </td>
                  <td>
                  		<input name="crole.roleName"  style="width:100px;"/>
                        </td>
                    </tr>
                    
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
                  </div>
            </form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var token = 0;
var companyID='${company.companyID}';
var companyName='${company.companyName}';
var roleID="";
var ziUrl;
var methodX="N";
var  pNumber =${pageNumber};	
$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
           // .jqDrag('.drag');// 添加拖拽的selector 
$(function(){ 
    $('.flexme11').flexigrid({
        allDouble:true,
		height: 130,
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
            name: '查询',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        },{
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
             $("#croleList").attr("action","<%=basePath%>page/ea/ccompany/crole/crole_edit.jsp?pageNumber=${pageNumber}&companyID=${company.companyID}&companyName=${company.companyName}&search=${search}");
            document.croleList.submit.click();
                break;
             case '查询':
            	$("#jqModelSearch").jqmShow();
             	break;
            case '设置每页显示条数':
			var url="<%=basePath%>ea/ccrole/ea_getListCRole.jspa?search=${search}";
				numback(url);
				break; 
				case '权限帐号':
				if(roleID==""){
					alert("请选择");
					return;	
				}
				methodX="Y";
				ziUrl=basePath
						   + "ea/ccaccount/ea_getListCAccount.jspa?methodX="+methodX+"&compid="+$("#companyIDD").attr("value")+"&title=title&roleIDX=";
				$("#mainframe").css({"height":"auto"}).attr("src",ziUrl+roleID);		
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
	function edit(roleID){
	             $('#croleList').attr("action",basePath + "ea/ccrole/ea_editCRole.jspa?pageNumber=${pageNumber}&crole.roleID="+roleID+"&compID="+$("#companyIDD").attr("value")+"&companyName=${company.companyName}&search=${search}");
			document.croleList.submit.click();
		}
	function del(roleID){
	          if(confirm("确定要执行此操作吗?")){   
	            $('#croleList').attr("action",basePath + "ea/ccrole/t_ea_delCRole.jspa?pageNumber=${pageNumber}&crole.roleID="+roleID+"&compID="+$("#companyIDD").attr("value")+"&search=${search}");
		    	document.croleList.submit.click();
		          }  
	             return false; 
		}
   function toAllot(roleID){
       $('#croleList').attr("action",basePath + "ea/ccroleallot/ea_getListSEAForAllot.jspa?pageNumber=${pageNumber}&roleID="+roleID+"&search=${search}&compID="+$("#companyIDD").attr("value"));
		document.croleList.submit.click();
   }
   $("#tosearch").click(function() {//查询
		$("#SearchForm").attr(
				"action",
				basePath + "ea/ccrole/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
		$("#SearchTable").find(":input[name]").val("");
	});
   $(function() {
			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date01="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var data1 = new Array();
							data1[0] = {
									id : "<%=c.getCompanyID()%>",
									pid : '-1',
									text : "<%=c.getCompanyName()%>"
								};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : companylist[i].companyPID,
									text : companylist[i].companyName
								};
							}
							var ts3 = new TreeSelector($("#companyID")[0],
									data1, -1);
							ts3.createTree();
							changeValue();
						},
						error : function cbf(data) {
							alert("机构数据获取失败！");
						}
					});
			$("#companyID").change(function(){
				changeValue();
			});		
		});	
   function changeValue(){
		//-------------------
		var companyID=$("#companyID").find("option:selected").attr("value");
		$("#orgID").html("");
		var url =basePath+"ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="+companyID+"&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				/** **添加部门列表** */
				var member = eval("(" + data + ")");
				var oList = member.organizationlist;
				var data2 = new Array();
				data2[0] = {
					id : companyID,
					pid : '-1',
					text : ""
				};
				for (var i = 0; i < oList.length; i++) {
					data2[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				ts = new TreeSelector($("#orgID")[0], data2, -1);
				ts.createTree();
			},
			error : function cbf(data) {	
				alert("数据获取失败！");
			}
		});
		//----------------------
   }
</script>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			$("#mainframe").css({"height": _height / 2 - 10 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>  
</body>
</html>