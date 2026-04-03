<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会计科目管理--期初余额管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}
-->
</style>

<script type="text/javascript">
   var basePath="<%=basePath%>";
   var treeID = "${csbjects.subjectsID}";
   var treePID = "${csbjects.subjectsPID}";
   var subjectsValue = "${csbjects.subjectsName}";
   var subjectsNumbers="${csbjects.subjectsNumbers}";
	if(treeID != ""){
	  if(window.parent.tree.getItemText(treeID) != '0'){
		   window.parent.tree.setItemText(treeID,subjectsValue);
		  }
	  else if(treeID ){
			window.parent.tree.insertNewChild(treePID,treeID,subjectsValue,0,0,0,0);
			window.parent.tree.setUserData(treeID,"subjectsNumbers",subjectsNumbers);
		}
    }
$(function(){ 
   var buttons = [{
       name: '添加期初余额',
       bclass: 'add',
       onpress: action //当点击调用方法
   }, {
       // 设置分割线  
       separator: true
   }, {
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
   }, {
            separator: true
    }];
   $('.flexme11').flexigrid({
       height: 300,
       width: 'auto',
       minwidth: 30,
       title: '当前科目--' + parent.tree.getSelectedItemText(),
       minheight: 80,
       buttons: buttons
   });
   
    function action(com, grid){
    
        switch (com) {
            case '添加期初余额':
            if ("" == parent.treeid) {
                    alert("请选择科目");
                    return;
                }
                //获取当前节点下子节点数
                var count = window.parent.tree.hasChildren(parent.treeid);
                if(count>0){
                	alert("当前科目下有子科目，不能添加期初余额！");
                	return;
                }
                var b=true;
                var url1=basePath+"/ea/csbjects/sajax_ea_ajaxEndAccount.jspa?date="+new Date().toLocaleString();
	               $.ajax({
	                        url: encodeURI(url1),
	                        type: "get",
	                        async: false,
	                        dataType: "json",
	                        success: function cbf(data){
				              var member = eval("(" + data + ")");
				              var nologin = member.nologin;
				              if(nologin){
				                  document.location.href =basePath+"page/ea/not_login.jsp";
									}
									var c = member.count;
									if (c != 0) {
										alert("已结账不能设置期初余额！");
										b = false;
									}
								},
								error : function cbf(data) {
									alert("数据获取失败！");
								}
					});
				if(b){
				document.location.href="<%=basePath%>/page/ea/main/finance/invoicing/voucher/csubejst_add.jsp?oper=save";
				}
                break;
		    case '设置每页显示条数':
			var url="<%=basePath%>ea/csbjects/ea_getCsubejstsListAll.jspa?subjectsID=${subjectsID}";
				numback(url);
				break;
			}
		}
	});
</script>
</head>
<body >    
<form method="post" name="sortchildren"><%-- 不告诉你--%>
<input id="oID" name="subjectsID" type="hidden"/>
</form>
<form name="subjectsForm" id="subjectsForm" method="post">
<input type="submit" style="display:none" name="submit"/>
        <input name="sub" value="${session_value}" type="hidden"/><!-- 代替token-->
<div>
  <table class="flexme11" width="600">
        				<thead>
							    	   	<tr>
							             	<th width="150" align="center">
												科目序号
											</th>
											<th width="200" align="center">
												科目名称
											</th>
											<th width="150" align="center">
												设置时间
											</th>
											<th width="50" align="center">
												期初方向
											</th>
											<th width="150" align="center">
												期初余额
											</th>
											<th width="50" align="center">
												期末方向
											</th>
											<th width="150" align="center">
												期末余额
											</th>
							            </tr>
						</thead>
								 <tbody>
							         	<s:iterator value="pageForm.list">
							    		<tr ondblclick="alee('<s:property value="subjectsID"/>')" >
							            	<td><s:property value="subjectsNumbers" /></td>
										<td>
											<s:property value="subjectsName" />
										</td>
										<td>
										    <s:property value="datess" />
										</td>
										<td>
										    <s:property value="sdirection" />
										</td>
										<td>
										    <s:property value="startCash" />
										</td>
										<td>
										    <s:property value="edirection" />
										</td>
										<td>
										    <s:property value="endCash" />
										</td>
							            </tr>
							            </s:iterator>
							            </tbody>
							</table>
								<c:import url="../../../../page_navigator.jsp">
							<c:param name="actionPath"
								value="ea/csbjects/ea_getCsubejstsListAll.jspa?pageNumber=${pageNumber}&subjectsID=${subjectsID}"></c:param>
						</c:import>
						<s:token />
			</div>
		</form>
		<!--搜索窗口 -->
		<!-- <form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="SearchTable">
					<tr>
						<td align="right">
							日期：
						</td>
						<td style="width: 300px">
							<input id="sdate" name="sdate" onfocus="daymonth(this);"
								style="width: 85px" />至<input id="edate" name="edate" onfocus="daymonth(this);"
								style="width: 85px" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
				</div>
			</div>
		</form> -->
	</body>
</html>	    