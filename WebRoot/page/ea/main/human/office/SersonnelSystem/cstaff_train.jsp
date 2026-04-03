<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>入职管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_train.js"></script>
        
        <script>
        	   var ppageNumber = ${pageNumber};
               var basePath = "<%=basePath%>";
			   var auditionID="";
			   var  staffIdentityCard="${audition.staffIdentityCard}";
           	   var  sName="${audition.staffName}";
           	   var  search="${search}";
           	   var session_val = '${session_value}';
           	   var companyName = '${companyName}';
        </script>
    </head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="80" align="center">
							人员姓名
						</th>
						<th width="150" align="center">
							身份证
						</th>
						<th width="60" align="center">
							状态
						</th>
						<th width="60" align="center">
							工种类别
						</th>
						<th width="100" align="center">
							应聘方向
						</th>
						<th width="100" align="center">
							应聘岗位
						</th>
						<th width="150" align="center">
							工作经验
						</th>
						<th width="100" align="center">
							面试地点
						</th>
						<th width="100" align="center">
							面试考官
						</th>
						<th width="80" align="center">
							面试时间
						</th>
						<th width="100" align="center">
							面试部门
						</th>
						<th width="100" align="center">
							评语
						</th>
						<th width="50" align="center">
							分数
						</th>
						<th width="80" align="center">
							入职时间
						</th>
						<th width="100" align="center">
							报到地点
						</th>
						<th width="80" align="center">
							转正时间
						</th>
						<th width="65" align="center">
							备注
						</th>
					</tr>
				</thead>
				<tbody>
					<%
                    int number = 1; %>
					<s:iterator value="pageForm.list">
						<tr id="${auditionID}">
							<td>
								<input type="radio" name="a" class="JQueryauditionID"
									value="${auditionID}" />
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
							</td>
							<td>
								<span id="status">${status=='21'?'未报到入职':'已入职'}</span>
							</td>
							<td>
								<span id="categoryName">${categoryName}</span>
							</td>
							<td>
								<span id="auditionDirection">${auditionDirection}</span>
							</td>
							<td>
								<span id="auditionPost">${auditionPost}</span>
							</td>
							<td>
								<span id="experience">${experience}</span>
							</td>

							<td>
								<span id="place">${place}</span>
							</td>
							<td>
								<span id="examiner">${examiner}</span>
							</td>
							<td>
								<span id="auditionDate">${fn:substring(auditionDate, 0,
									10)}</span>
							</td>
							<td>
								<span id="auditionDept">${auditionDept}</span>
							</td>

							<td>
								<span id="commention">${commention}</span>
							</td>

							<td>
								<span id="auditionPoint">${auditionPoint}</span>
							</td>
							<td>
								<span id="registerDate">${fn:substring(registerDate, 0,
									10)}</span>
							</td>
							<td>
								<span id="auditionPlace">${auditionPlace}</span>
							</td>
							<td>
								<span id="becomesDate">${fn:substring(becomesDate, 0,
									10)}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
								<span style="display: none" id="auditionID">${auditionID}</span>
								<span style="display: none" id="auditionKey">${auditionKey}</span>
								<span style="display: none" id="staffID">${staffID}</span>
								<span style="display: none" id="photo">${photo}</span>
							</td>
						</tr>
						<%
                        number++; %>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/saudition/ea_getauditionList.jspa?pageNumber=${pageNumber}&status=2&search=${search}&audition.staffName=${audition.staffName}&audition.staffIdentityCard=${audition.staffIdentityCard}">
				</c:param>
			</c:import>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 700px; top: 4%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post" action="">
				<div class="drag">
					入职管理
					<div class="close">
					</div>
				</div>
				<table width="642" border="0" id="stafftable" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 0px; margin-bottom: 0px;">
					<tr>
						<td>
							<table width="699" height="370" border="0" id="stafftable2"
								align="center" cellpadding="0" cellspacing="0"
								style="margin-top: 0px; margin-bottom: 0px;">
								<tr>
									<td width="90" height="20" align="right">
										员工姓名：
									</td>
									<td width="189">
										<input type="text" id="staffName" readonly="readonly" />
									</td>
									<td width="80" align="right">
										身份证号：
									</td>
									<td width="189">
										<input type="text" id="staffIdentityCard" readonly="readonly" />
									</td>
									<td width="151" height="20" rowspan="5" align="center">
										<img id="photo" width="99" height="135" />
									</td>
								</tr>
								<tr>
									<td height="20" align="right">
										应聘方向：
									</td>
									<td>
										<input id="auditionDirection"
											name="audition.auditionDirection" readonly="readonly"
											type="text" class="input" size="20" />
									</td>
									<td align="right">
										应聘岗位：
									</td>
									<td>
										<input id="auditionPost" type="text"
											name="audition.auditionPost" readonly="readonly"
											class="input" size="20" />
									</td>
								</tr>
								<tr>
									<td height="20" align="right">
										面试地点：
									</td>
									<td>
										<input id="place" type="text" class="input"
											name="audition.place" readonly="readonly" size="20" />
									</td>
									<td align="right">
										面试部门：
									</td>
									<td>
										<input id="auditionDept" type="text" class="input"
											readonly="readonly" name="audition.auditionDept" size="20" />
									</td>
								</tr>
								<tr>
									<td height="20" align="right">
										面试时间：
									</td>
									<td>
										<input id="auditionDate" type="text" readonly="readonly"
											class="input" name="audition.auditionDate" size="20" />
									</td>
									<td align="right">
										面试考官：
									</td>
									<td>
										<input id="examiner" type="text" class="input"
											readonly="readonly" name="audition.examiner" size="20" />
									</td>
								</tr>
								<tr>
									<td height="20" align="right">
										评语：
									</td>
									<td>
										<input id="commention" type="text" readonly="readonly"
											class="input" name="audition.commention" size="20" />
									</td>
									<td align="right">
										分数：
									</td>
									<td>
										<input id="auditionPoint" type="text" readonly="readonly"
											class="input" name="audition.auditionPoint" size="20" />
									</td>
								</tr>
								<tr>
									<td height="30" align="right">
										入职时间：
									</td>
									<td>
										<input id="registerDate" type="text" readonly="readonly"
											class="input" name="audition.registerDate" size="20" />
									</td>
									<td align="right">
										报到地点：
									</td>
									<td colspan="2">
										<input id="auditionPlace" readonly="readonly" type="text"
											class="input" name="audition.auditionPlace" size="30" />
									</td>
								</tr>
								<tr>
									<td height="30" align="right">
										报到状态：
									</td>
									<td>
										<input id=status type="text" readonly="readonly" class="input"
											name="audition.registerDate" size="20" />
									</td>
									<td align="right">
										转正时间:
									</td>
									<td colspan="2">
										<input id=becomesDate type="text" readonly="readonly"
											class="input" name="audition.becomesDate" size="20" />
									</td>
								</tr>
								<tr>
									<td align="right">
										工作经验：
									</td>
									<td colspan="4">
										<textarea style="width: 410px; height: 70px;"
											readonly="readonly" name="audition.experience"
											id="experience">
                            </textarea>
									</td>
								</tr>
								<tr>
									<td align="right">
										备注：
									</td>
									<td colspan="4">
										<textarea style="width: 410px; height: 70px;"
											readonly="readonly" name="audition.remark" id="remark">
                            </textarea>
									</td>
								</tr>
								<s:token></s:token>
							</table>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="saveCos"
						value=" 入职报到 " />
					<input type="button" class="input-button JQueryreturn" value="取消" />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<!--搜索窗口 -->
		<form name="SearchForm" id="SearchForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="audition.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证号码：
						</td>
						<td>
							<input name="audition.staffIdentityCard" />
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
		<!-- 修改窗口 -->
		<form name="UpdateForm" id="UpdateForm" method="post">
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelUpdate">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					修改员工信息
					<div class="close"></div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="audition.staffName" id="staffName" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							入职时间：
						</td>
						<td>
							<input name="audition.registerDate" size="20" class="input put3" type="text" id="registerDate" readonly="readonly" onfocus="date(this);"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							转正时间:
						</td>
						<td>
							<input type="text" class="input put3" name="audition.becomesDate" size="20" id="becomesDate" readonly="readonly" onfocus="date(this);"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toUpdate"
						value=" 修改 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<!--员工离职原因 -->
		<form name="cstaffDForm" id="cstaffDForm" method="post">
			<input type="submit" name="submit" id="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%;"
				id="jqModelDimission">

				<div class="drag">
					员工离职
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							离职时间：
						</td>
						<td>
							<input name="codimission.dimissionDate" onfocus="date(this);" class="goodsCoding put3"/>
						</td>
					</tr>
					<tr>
						<td>
							离职原因：
						</td>
						<td>
							<input name="codimission.dimissionCause" class="goodsCoding put3"/>
						</td>
					</tr>
					<tr>
						<td>
							经手人：
						</td>
						<td>
							<input name="codimission.issued" class="goodsCoding put3"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button JQ" id="searchCod"
						value="确定" />
					<input type="button" class="input-button JQueryreturn" value="取消" />
					<input name="dimission" type="hidden" value="dimission" />
				</div>
			</div>
			<s:token></s:token>
		</form>
	</body>
</html>