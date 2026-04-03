<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>岗位薪水调查</title>
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
		<script type="text/javascript">
        	var payResearchID="";
            var basePath = "<%=basePath%>";
            var ppageNumber=${pageNumber};
            var search='${search}'; 
            var token=0;       
        </script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/payresearch_list.js"></script>
		
	</head>
	<body>
		<table class="flexme11">
			<thead>
				<tr>
					<th width="40" align="center">
						选择
					</th>
					<th width="150" align="center">
						行业类别
					</th>
					<th width="150" align="center">
						岗位
					</th>
					<th width="200" align="center">
						工薪范围
					</th>
					<th width="100" align="center">
						工作年限
					</th>
					<th width="240" align="center">
						工作要求
					</th>
					<th width="220" align="center">
						备注
					</th>

				</tr>
			</thead>
			<%
				int number = 1;
			%>
			<tbody>
				<s:iterator value="pageForm.list">
					<tr id="${payResearchID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue" />
						</td>
						<td>
							<span id="industryCategory"><s:property
									value="industryCategory" />
							</span>
						</td>
						<td>
							<span id="post"><s:property value="post" />
							</span>
						</td>
						<td>
							<span id="salaryRange"><s:property value="salaryRange" />
							</span>
						</td>
						<td>
							<span id="workingTenure"><s:property
									value="workingTenure" />
							</span>
						</td>
						<td>
							<span id="jobRequirement"><s:property
									value="jobRequirement" />
							</span>
						</td>
						<td>
							<span id="remarks"><s:property value="remarks" />
							</span>
							<span id="payResearchKey" style="display: none">${payResearchKey}</span>
							<span id="payResearchID" style="display: none">${payResearchID}</span>
						</td>
						<%
							number++;
						%>
					</tr>
				</s:iterator>
			</tbody>
		</table>
		<c:import url="../../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="ea/payresearch/ea_getCOPayResearchList.jspa?pageNumber=${pageNumber}">
			</c:param>
		</c:import>

		<!-- ADD-->
		<div class="jqmWindow jqmWindowcss3" style="width: 500px; top: 10%"
			id="jqModelAdd">
			<form name="addForm" id="addForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					岗位薪水调查
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td align="right" width="100px">
							行业类别：
						</td>
						<td>
							<input name="payResearch.industryCategory" id="industryCategory" />
						</td>
					</tr>
					<tr>
						<td align="right">
							岗位：
						</td>
						<td>
							<input name="payResearch.post" id="post" />
						</td>
					</tr>
					<tr>
						<td align="right">
							工薪范围：
						</td>
						<td>
							<input name="payResearch.salaryRange" id="salaryRange" />
						</td>
					</tr>
					<tr>
						<td align="right">
							工作年限：
						</td>
						<td>
							<input name="payResearch.workingTenure" id="workingTenure" />
						</td>
					</tr>
					<tr>
						<td align="right">
							工作要求：
						</td>
						<td>
							<input name="payResearch.jobRequirement" id="jobRequirement"
								size="37" />
						</td>
					</tr>
					<tr>
						<td align="right">
							备注：
						</td>
						<td>
							<textarea name="payResearch.remarks" cols="30" rows="5"
								id="remarks">
                            </textarea>
						</td>
					</tr>
				</table>
				<div align="center">
				    <input name="payResearch.payResearchKey" type="hidden"
						id="payResearchKey" />
					<input name="payResearch.payResearchID" type="hidden"
						id="payResearchID" />
					<input type="button" class="input-button JQuerySubmit"
						style="cursor: pointer; width: 80px;" value="提交" />
					<input type="button" class="input-button JQueryreturn"
						style="cursor: pointer; width: 80px;" value="取消" />
				</div>
			</form>
		</div>
		
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
			id="jqModelSerch">
			<form name="jqModelSerchForm" id="jqModelSerchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="260px" id="paySearchTable">
					<tr>
						<td align="right" width="100px">
							行业类别：
						</td>
						<td>
							<input name="payResearch.industryCategory" id="industryCategory" />
						</td>
					</tr>
					<tr>
						<td align="right">
							岗位：
						</td>
						<td>
							<input name="payResearch.post" id="post" />
						</td>
					</tr>
					<tr>
						<td align="right">
							工作年限：
						</td>
						<td>
							<input name="payResearch.workingTenure" id="workingTenure" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchPay"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		
		
		<!--导入-->
		<div class="jqmWindow jqmWindowcss4" style="width: 500px; top: 10%"
			id="jqModelDaoRu">
			<form name="daoRuForm" id="daoRuForm" method="post"
				enctype="multipart/form-data">
				<div class="drag">
					岗位薪水调查导入
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTableDaoRu">
					<tr>
						<td width="100px">
							导入文件：
						</td>
						<td>
							<input type="file" name="excelImport.excelFile" id="DaoRu" />
						</td>
						<td>
							<input type="button" class="input-button JQueryDaoRu"
								style="cursor: pointer; width: 80px;" value="预览" />
						</td>
					</tr>
				</table>
				<input type="submit" name="submit" style="display: none" />
			</form>
		</div>
		<!-- ENDADD-->
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 500px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<a id="DaoRuFan" href="#" style="height: 23px; width: 60px">返回</a>
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="500px"
				frameborder="0"></iframe>
		</div>
	</body>
</html>
