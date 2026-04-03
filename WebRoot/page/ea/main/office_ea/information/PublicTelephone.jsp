<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>公共电话薄管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/information/PublicTelephone.js"></script>
		<script type="text/javascript">
   var  telephoneID = '';
   var  basePath='<%=basePath%>';           
   var  bpageNumber =${pageNumber};
   var  search='${search}';  
   var  token=0;
</script>
	</head>
	<body>
		<div id="main_main" class="main_main">
			<table class="address">
				<thead>
					<tr>
						<th width="40" align="center">
							请选择
						</th>
						<th width="40" align="center">
							序号
						</th>
						<th width="150" align="center">
							公司名称
						</th>
						<th width="150" align="center">
							往来关系
						</th>
						<th width="180" align="center">
							联系人姓名
						</th>
						<th width="180" align="center">
							联系人电话
						</th>
						<th width="180" align="center">
							电子邮箱
						</th>
						<th width="180" align="center">
							地址
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="objs" status="i">
						<tr>
							<td>
								<input type="radio" name="a" value="#i.index" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<s:iterator value="#objs" var="obj" status="st">
								<s:if test="#st.index !=6 && #st.index !=7 ">
									<td>
									<span><s:property value="#obj"/> </span>
									</td>
  						  		</s:if>		
							</s:iterator>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/telephone/ea_getaTelephoneList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow " style="width: 270px; right: 40%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							联系人姓名：
						</td>
						<td>
							<input name="telephone.linkmanName" />
						</td>
					</tr>
					<tr>
						<td>
							往来关系：
						</td>
						<td>
							<input name="telephone.company" />
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


		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post">
				<div class="drag">
					公共电话薄管理
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
					</div>
					<table width="550" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="550" height="117" border="0" align="center"
									cellpadding="0" cellspacing="0" id="stafftable2"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="100" height="37" align="right">
											联系人姓名：
										</td>
										<td width="148">
											<input name="telephone.linkmanName" type="text"
												id="linkmanName" size="20" />
										</td>
										<td width="90" align="right">
											职位：
										</td>
										<td width="212">
											<input name="telephone.job" type="text" id="job" size="20" />
										</td>
									</tr>
									<tr>
										<td height="41" align="right">
											手机：
										</td>
										<td>
											<input id="handset" type="text" class="input"
												name="telephone.handset" size="20" />
										</td>
										<td align="right" height="41">
											电子邮箱：
										</td>
										<td width="212">
											<input id="email" type="text" class="input"
												name="telephone.email" size="30" />
										</td>
									</tr>
									<tr>
										<td align="right" height="41">
											所在公司：
										</td>
										<td width="212">
											<input id="company" type="text" class="input"
												name="telephone.company" size="30" />
										</td>
										<td align="right" height="41">
											详细信息：
										</td>
										<td>
											<input id="moreInformation" type="text"
												name="telephone.moreInformation" size="30" />
											<input name="telephone.telephoneID" type="hidden"
												id="telephoneID" size="20" />
											<input type="hidden" name="telephone.telephoneKey"
												id="telephoneKey" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
				<div align="center">
					<input type="button" class="input-button"
						style="cursor: pointer; width: 80px;" id="tosave" value="保存 " />
					<input type="button" class="input-button JQueryreturn"
						style="cursor: pointer; width: 80px;" value="取消" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
