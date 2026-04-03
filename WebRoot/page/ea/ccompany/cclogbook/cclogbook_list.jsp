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
		<title>操作日志——总公司、子公司</title>        
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css" />		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
		<form name="caccountList" method="post">
			<div class="main_main">
				<table class="flexme11">
					<thead>
						<tr>
							<th width="30" align="center">
								序号
							</th>
							<th width="80" align="center">
								操作帐号
							</th>
							<th width="140" align="center">
								操作日期
							</th>
							<th width="400" align="center">
								操作内容
							</th>
							<th width="220" align="center">
								操作URL
							</th>
							<th width="130" align="center">
								IP地址来源
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
									<s:property value="accountEmail" />
								</td>
								<td>
									<s:property value='clogbookTime' />
								</td>
								<td>
									<s:property value="clogbookCounect" />
								</td>
								<td>
									<s:property value='clogbookUrl' />
								</td>
								<td>
									<s:property value='clogbookIP' />
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
						value="ea/cclogbook/ea_getListCLogBook.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}"></c:param>
				</c:import>
				<s:token />
			</div>
		</form>

		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 500px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460" id="cataffSearchTable">
					<tr>
						<td align="right">
							操作帐号：
						</td>
						<td>
							<input name="clogbook.accountEmail" />
						</td>
					</tr>
					<tr>
						<td align="right">
							操作内容：
						</td>
						<td>
							<input name="clogbook.clogbookCounect" />
						</td>
					</tr>
					<tr>
						<td align="right">
							操作URL：
						</td>
						<td>
							<input name="clogbook.clogbookUrl" />
						</td>
					</tr>
					<tr>
						<td align="right">
							操作日期：
						</td>
						<td>
							<input name="sdate" id="sdate" onfocus="date(this);" />
							至
							<input name="edate" id="edate" onfocus="date(this);" />
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
        var  sdate= "${sdate}";
        var  edate= "${edate}";
$(function(){ 
    $('.flexme11').flexigrid({
		height: 355,
		width: 'auto',
		minwidth: 30,
		title: '操作日志',
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
			var url="<%=basePath%>ea/cclogbook/ea_getListCLogBook.jspa?search="+search+"&sdate="+sdate+"&edate="+edate;
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
         	$("#SearchForm").attr("action",  "<%=basePath%>ea/cclogbook/ea_toSearch.jspa?pageNumber=${pageNumber}");
            document.SearchForm.submit.click();
         });
	});
</script>
	</body>
</html>