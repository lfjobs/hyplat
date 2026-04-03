<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
		<title>往来单位管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<!--  办公室 营销 售前 收集单位办 or 财务记帐单 新增往来关系  -->
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		
		<script type="text/javascript">
		var  ccompanyID = '';
		var  companyName = '';
		var  basePath='<%=basePath%>';           
		var  pNumber =${pageNumber};  
		var search='${search}';
		var  token = 0 ;  
		var  personurl='';
		var notoken = 0;
		var retoken=0;
		var  select=1;
		var opertionID = "";
		var personurl = "";
		
		$(function(){
			$(".jqmWindow").jqm({
		        modal: true,// 限制输入（鼠标点击，按键）的对话  
		        overlay: 20 // 遮罩程度%  
		    }).jqmAddClose('.close')// 添加触发关闭的selector  
		    $('.JQueryflexme').flexigrid({
		        height: 220,
		        width: 'auto',
		        minwidth: 30,
		        title: '公司列表',
		        minheight: 80,
		        buttons: [{
		                  name: '查询',
		                  bclass: 'mysearch',
		                  onpress: action//当点击调用方法
		              }, {
		                  separator: true
		              }, {
						  name: '设置每页显示条数',
						  bclass: 'mysearch',
						  onpress : action//当点击调用方法
					  },{
						  separator: true
				}]
		    });
		    
			function action(com, grid){
				switch (com) {
					case '查询' :
						$("#jqModelSearch").jqmShow();
						break;
					case '设置每页显示条数':
			var url=basePath
								+ "ea/custdata/ea_getListContactCompanyJQM.jspa?search="
								+ search;
				numback(url);
						break;
				}
			}
		   
			$(".JQueryflexme tr[id]").click(function(){
				opertionID=this.id;
				 if(personurl){
		               $("#mainframe").attr("src",personurl + opertionID);
		           }
				$("input.JQuerypersonvalue",$(this)).attr("checked","checked");
			});
			 //查询相关操作
		     $("#tosearch").click(function() {
				$("#SearchForm").attr(
						"action",
						basePath + "ea/custdata/ea_toSearchJQM.jspa?pageNumber="
								+ pNumber);
				document.SearchForm.submit.click();
			});
		});
		</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="250" align="center">
							单位名称
						</th>
						<th width="250" align="center">
							单位地址
						</th>
						<th width="100" align="center">
							单位电话
						</th>
						<th width="100" align="center">
							单位负责人
						</th>
						<th width="100" align="center">
							单位负责人电话
						</th>
						<th width="100" align="center">
							行业类别
						</th>
						<th width="100" align="center">
							单位备注
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${ccompanyID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ccompanyID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="companyName">${companyName}</span>
								
								<span id="name" style="display:none">${companyName}</span>
								<span id="addr" style="display:none">${companyAddr}</span>
								<span id="tel" style="display:none">${companyTel}</span>
								<span id="ble" style="display:none">${cresponsible}</span>
							</td>

							<td>
								<span id="companyAddr">${companyAddr}</span>
							</td>
							<td>
								<span id="companyTel">${companyTel}</span>
							</td>
							<td>
								<span id="cresponsible">${cresponsible}</span>
							</td>
							<td>
								<span id="responsibleTel">${responsibleTel}</span>
							</td>
							<td>
								<span id="industryType">${industryType}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
								<span id="address" style="display: none">${address}</span>
								<span id="ccompanyID" style="display: none">${ccompanyID}</span>
								<span id="ccompanyKey" style="display: none">${ccompanyKey}</span>
								<span id="dealIn" style="display: none">${dealIn}</span>
							</td>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/custdata/ea_getListContactCompanyJQM.jspa?search=${search}&pageNumber=${pageNumber}">
				</c:param>
			</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<div style="overflow: ">
				<iframe src="" name="main" width="100%" marginwidth="0"
					scrolling="no" height="258px" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
			</div>
		</div>
		
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询往来单位
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							单位名称：
						</td>
						<td width="261">
							<input name="contactCompany.companyName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位责任人：
						</td>
						<td>
							<input name="contactCompany.cresponsible" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							单位地址：
						</td>
						<td width="261">
							<input name="contactCompany.companyAddr" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							行业类别：
						</td>
						<td width="261">

							<s:select list="%{#request.typelist}" id="industryType"
								listKey="codeValue" listValue="codeValue"
								name="contactCompany.industryType" theme="simple" headerKey=""
								headerValue="全部"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		
	</body>
</html>