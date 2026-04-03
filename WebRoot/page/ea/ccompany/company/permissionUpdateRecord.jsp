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
        <title>权限修改记录</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
    </head>
  <body>
    <div class="main_main">
			<table class="JQueryflexmepost">
				<thead>
					<tr>
					<th width="100" align="center">
							序号
						</th>
						<th width="300" align="center">
							权限路径
						</th>
						<th width="270" align="center">
							权限描述
						</th>
						<th width="150" align="center">
							修改时间
						</th>
					</tr>
				</thead>
					<%
						int number = 1;
					%>
					<tbody>
						<s:iterator value="pageForm.list">
							<tr align="center">
								<td><%=number%></td>
								<td>
									<s:property value='croledetails' />
								</td>
								<td>
									<s:property value="croledescribe" />
								</td>
								<td>
									<s:property value='crolechangelogodate' />
								</td>
								<%
									number++;
								%>
							</tr>
						</s:iterator>
					</tbody>
			</table>
			<c:import url="../../page_navigator.jsp" >
					<c:param name="actionPath"
						value="ea/permissionupdaterecord/ea_getListPermissionupdateRecord.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
				</c:import>
				<s:token />
		</div>
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 300px; right: 45%; top: 25%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="300" id="cataffSearchTable">
					<tr>
						<td align="right">
							创建时间：
						</td>
						<td>
							<input name="permissionRecord.crolechangelogodate" onfocus="date(this);"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							权限路径：
						</td>
						<td>
							<input name="permissionRecord.croledetails" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<script type="text/javascript">
        var  search="${search}";
$(function(){ 
    $('.JQueryflexmepost').flexigrid({
		height: 355,
		width: 'auto',
		minwidth: 30,
		title: '权限修改记录',
		minheight: 80 ,
	  buttons: [ 
		 {
            name: '设置每页显示条数',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            // 设置分割线  
            separator: true
	    }, {
            name: '查询',
            bclass: 'mysearch',
			onpress : action//当点击调用方法
        }, {
            // 设置分割线  
            separator: true
	    }
	    ]});
	 function action(com, grid){
        switch (com) {
         case '设置每页显示条数':
			var url="<%=basePath%>ea/permissionupdaterecord/ea_getListPermissionupdateRecord.jspa?search="+search+"";
				numback(url);
				break;
		 case '查询':
              $("#jqModelSearch").show();
               break;		
				}
        }
        $(".close").click(function(){
        	 $("#jqModelSearch").hide();
        });
         $("#tosearch").click(function(){
         	$("#SearchForm").attr("action",  "<%=basePath%>ea/permissionupdaterecord/ea_toSearch.jspa?pageNumber=${pageNumber}");
            document.SearchForm.submit.click();
         });
	});
</script>
  </body>
</html>
