<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>注册企业查询管理列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=basePath%>css/ea/register.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
			var search="${search}";
			var basePath="<%=basePath%>";
			var pNumber=${pageNumber};
			var token = 0;
			var opertionID = "";
		
			$(function() {
				$(".jqmWindow").jqm({
					modal : true,// 限制输入（鼠标点击，按键）的对话
					overlay : 20
						// 遮罩程度%
					}).jqmAddClose('.close')// 添加触发关闭的selector
				$('.JQueryflexme').flexigrid({
							height : 260,
							width : 'auto',
							minwidth : 30,
							title : "注册企业列表",
							minheight : 80,
							buttons : [{
								name : '查询企业',
								bclass : 'mysearch',
								onpress : action
									// 当点击调用方法
								}, {
								separator : true
							}, {
								name : '设置每页显示条数',
								bclass : 'mysearch',
								onpress : action
									// 当点击调用方法
								}, {
								separator : true
							}]
						});
				function action(com, grid) {
					switch (com) {
						case '查询企业' :
							$("#jqModelSearch1").jqmShow();
							break;
						case '设置每页显示条数' :
							var url = basePath + "ea/organization/ea_getRegistAll.jspa?search="+search;
							numback(url);
							break;
					}
				}
			
				$("#tosearch").click(function() { // 查询
					$("#SearchForm").attr(
							"action",
							basePath + "ea/organization/ea_toRegistSearch.jspa?pageNumber="
									+ pNumber);
					document.SearchForm.submit.click();
					$("#jqModelSearch1").jqmHide();
				});
			
				$("#closes").click(function() {
					window.location.reload();
					$("#jqModelSearch").jqmHide();
				});
				
				$(".JQueryflexme tr[id]").click(function(){
					opertionID=this.id;
					$("input.JQuerypersonvalue",$(this)).attr("checked","checked");
				});
			})
			
			function re_load() {
				window.location.reload();
			}
		</script>
		<style type="text/css">
		.yname {
		height: 14px;
		}
		</style>
	</head>
	<body>
		<div style="z-index: 90">
			<table class="JQueryflexme">
				<thead>
					<tr>
						<th align="center" width="30">
							选择
						</th>
						<th align="center" width="200">
							公司名称
						</th>
						<th align="center" width="200">
							组织机构名
						</th>
						<th align="center" width="250">
							公司地址
						</th>
						<th align="center" width="80">
							负责人
						</th>
						<th align="center" width="100">
							公司电话
						</th>
						<th align="center" width="180">
							邮箱
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}" varStatus="index">
						<tr id="${index.index+1}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${index.index+1}" />
							</td>
							<td>
								<span id="companyname">${arr[0]}</span>
								<span id="organizationName" style="display:none">${arr[0]}</span>
							</td>
							<td>
								<span id="companyidentifier">${arr[1]}</span>
							</td>
							<td>
								<span id="companyaddress">${arr[2]}</span>
							</td>
							<td>
								<span id="companyphone">${arr[3]}</span>
							</td>
							<td>
								<span id="companymanager">${arr[4]}</span>
							</td>
							<td>
								<span id="companyemail">${arr[5]}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/organization/ea_getRegistAll.jspa?search=${search}&pageNumber=${pageNumber}">
			</c:param>
		</c:import>
		
		<div class="jqmWindow" style="width: 400px;right: 50%;;top: 20%" id="jqModelSearch1">
          <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询企业资料
                    <div class="close">
                    </div>
            </div>
             <table width="396" id="cataffSearchTable">
              <tr>
                        <td width="123" align="right">
                          组织机构名：       </td>
						<td width="261">
							<input name="company.companyIdentifier" />
                        </td>
              </tr>
              <tr>
                        <td width="123" align="right">
                           公司名称：        </td>
						<td width="261">
							<input name="company.companyName" />
                        </td>
              </tr>
             </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
	</body>
</html>