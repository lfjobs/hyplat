<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="hy.ea.bo.Company"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司及子公司账号管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
   <script type="text/javascript"> 
        var treeID = '<%=session.getAttribute("organizationID")%>';
        
       var  basePath='<%=basePath%>';           
       var  search='${search}';    
       var  companyID='${companyID}'; 
       var  pNumber =${pageNumber};	
       var  compid="${compid}";
     </script> 
	</head>
	<body>
		<form name="caccountList" id="caccountList" method="post"><input type="submit" name="submit" style="display:none"/>
			<div class="main_main">
				<table class="flexme11">
				<thead>
					<tr >
					<th width="30" align="center">
							序号
						</th>
						<th width="170" align="center">
							公司名称
						</th>
						<th width="80" align="center">
							帐号名称
						</th>
						<th width="80" align="center">
							登录帐号
						</th>
						<th width="220" align="center">
							所属角色
						</th>
						<th width="130" align="center">
							帐号状态
						</th>
						<th width="130" align="center">
							在线情况
						</th>
						<th width="120" align="center">
							操作
						</th>
						<th width="100"  align="center">
							授权
						</th>
					</tr>
					</thead>
					<%
						int number = 1;
					%>
					<tbody>
					<s:iterator value="pageForm.list">
						<tr align="center" ondblclick="edit('<s:property value="accountID"/>')">
							<td><%=number%></td>
							<td>
								<s:property value="companyName" />
							</td>
							<td>
								<s:property value="accountName" />
							</td>
							<td>
								<s:property value="accountEmail" />
							</td>
							<td>
				 				<s:select list="%{#request.roleList}"  listKey="roleID"  listValue="roleName"   name="roleID" theme="simple" disabled="true">
                                </s:select>
                                <input type="hidden" id="companyIDD" value="<s:property value='companyID'/>"/>
							</td>
							<td>
							<c:if test="${accountStatus == '00'}">正常</c:if>
							<c:if test="${accountStatus == '02'}">停用</c:if>
							</td>
							<td>
							<c:if test="${accountOnLine == '00'}">离线</c:if>
							<c:if test="${accountOnLine == '01'}">在线</c:if>
							</td>
							<td>
							  <a href="#" onclick="edit('<s:property value="accountID"/>')"><img src="<%=basePath%>images/ea/main/edit.gif" width="16" height="16"  title="修改" border="0"/></a>
	                          <a href="#" onclick="del('<s:property value="accountID"/>','<s:property value="accountEmail" />')"><img src="<%=basePath%>images/ea/main/gtk-del.png" width="16" height="16" title="删除" border="0"/></a>
							</td>
							 <td><a href="#" onclick="toAllot('<s:property value="accountID"/>','<s:property value="accountName" />')">机构授权</a></td>
							<%
								number++;
							%>
						</tr>
					</s:iterator>
					</tbody>
				</table>
				<c:import url="../../page_navigator.jsp">
					<c:param name="actionPath" value="ea/ccaccount/ea_getListCAccount.jspa?pageNumber=${pageNumber}&methodX=${methodX}&roleIDX=${roleIDX}&search=${search}&title=${title}&compid=${compid}"></c:param>
				</c:import>
				<s:token />
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
                <table width="421" height="153" id="accountSearchTable" align="center">
			<tr>
                        <td width="118" align="right">
                            帐号名称：                        </td>
                        <td width="314">
                          <input name="caccount.accountName"  style="width:100px;"/>
                        </td>
                  </tr>
                  <tr>
                        <td align="right">
                            公司名称：                        </td>
                      <td>
                        <select id="companyID" name="caccount.companyID"  ${title=='title'?'disabled':''}   >
							</select>
                    </td>
                  </tr>
                    <tr>
                        <td align="right">
                          所属角色：                        </td>
                  <td>
                   		<select id="roleIDD" name="caccount.roleID" ${title=='title'?'disabled':''} >
                           	<option value="">请选择公司</option>
                           </select>
                        </td>
                    </tr>
          			<tr>
                        <td align="right">
                           账号状态：                        </td>
                  <td>
                  		<select name="caccount.accountStatus"  style="width:100px;"> 
                  			<option value="">--全部--</option>
                  			<option value="00">正常</option>
                  			<option value="02">停用</option>
                  		</select>
                        </td>
                    </tr>
					<tr>
                        <td align="right">
                           在线情况：                        </td>
                  <td>
                  		<select name="caccount.accountOnLine"  style="width:100px;"> 
                  			<option value="">--全部--</option>
                  			<option value="00">离线</option>
                  			<option value="01">在线</option>
                  		</select>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
                  </div>
            </form>
		<script type="text/javascript">
		var methodX='${methodX}';
		var roleIDX='${roleIDX}';
 	$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
           // .jqDrag('.drag');// 添加拖拽的selector 
	$(function(){ 
    $('.flexme11').flexigrid({
		height: methodX=="Y"?100:300,
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
        }]
	});
    function action(com, grid){
        switch (com) {
            case '添加账号':
            	$('#caccountList').attr("action","<%=basePath%>ea/ccaccount/ea_editCAccount.jspa?pageNumber=${pageNumber}&methodX=${methodX}&roleIDX=${roleIDX}&search=${search}&title=${title}&compid=${compid}");
				document.caccountList.submit.click();
                break;
            case '查询':
            	$("#jqModelSearch").jqmShow();
             	break;
            case '设置每页显示条数':
				var url="<%=basePath%>ea/ccaccount/ea_getListCAccount.jspa?1=1&roleIDX="+roleIDX+"&methodX="+methodX+"&search=${search}&title=${title}&compid=${compid}";
				numback(url);
				break;
        }
        
    }
    $(".flexme11").find("select").each(function(){
                    $s = $(this).hide();
                    $o = $("<span/>").text($s.find("option:selected").text()).attr('id',this.id);
                    $o.insertAfter($s);
                });
     });
	function edit(accountID){
	        $('#caccountList').attr("action","<%=basePath%>ea/ccaccount/ea_editCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&compid="+$("#companyIDD").attr("value")+"&roleIDX="+roleIDX+"&methodX="+methodX+"&search=${search}&title=${title}");
			document.caccountList.submit.click();
	}
	function del(accountID){
	     if(confirm("确定要执行此操作吗?")){   
	        $('#caccountList').attr("action","<%=basePath%>ea/ccaccount/t_ea_delCAccount.jspa?pageNumber=${pageNumber}&caccount.accountID="+accountID+"&compid="+$("#companyIDD").attr("value")+"&roleIDX="+roleIDX+"&methodX="+methodX+"&search=${search}&title=${title}");
			document.caccountList.submit.click();
	        }  
	     return false; 
	}
	function toAllot(accountID,accountName){
	    var url = "<%=basePath%>page/ea/ccompany/caccount/caccount_post.jsp?pageNumber=${pageNumber}&accountName="+accountName+"&accountID="+accountID+"&roleIDX="+roleIDX+"&methodX="+methodX+"&search=${search}&compid="+$("#companyIDD").attr("value")+"&title=${title}";
	    url = encodeURI(url);
	    document.location.href = url;
	}
	$("#tosearch").click(function() {//查询
		$("#SearchForm").attr(
				"action",
				basePath + "ea/ccaccount/ea_toSearch.jspa?pageNumber="
						+ pNumber+"&title=${title}&compid=${compid}&roleIDX="+roleIDX+"&methodX="+methodX);
		document.SearchForm.submit.click();
		$("#carSearchTable").find(":input[name]").val("");
	});
$(function() {
			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?date01="
					+ new Date();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
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
						},

						error : function cbf(data) {
							alert("机构数据获取失败！");
						}
					});
				changeButton(companyID);
			$("#companyID").change(function(){
            	changeButton(this.value);
            });
		});		
		function changeButton(id){
			 var url = basePath+"ea/ccaccount/sajax_ea_toseachto.jspa?compid="+id+"&date="+new Date(); 
						$.ajax({
						    url:encodeURI(url),
						    type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
								var member = eval("(" + data + ")");
				                var oList = member.roleList;
				                
				                var option_ = "<option value=''>--全部--</option>";
				                
								 for (var i = 0; i < oList.length; i++) {        
								 
								 option_ += "<option value='"+oList[i].roleID+"''>"+oList[i].roleName+"</option>";
								}
								$("#roleIDD").html(option_);
							},
						error: function cbf(data){
						alert("数据获取失败！");
						}
					}); 
		};
</script>
	</body>
</html>