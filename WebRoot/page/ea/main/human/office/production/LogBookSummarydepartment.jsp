<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>部门员工工作日志汇总</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/production/LogBookSummary.js">
		</script>

		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script>
           var pbasePath = '<%=basePath%>';
           var psearch = '${search}';
           var ppageNumber = '${pageNumber}';
           var psdate = '${sdate}';
           var pedate = '${edate}';
        </script>
		<script type="text/javascript">
$(function(){
	    var comID = '<%=c.getCompanyID()%>';
        var url = "<%=basePath%>ea/responsibilities/sajax_n_ea_getoList.jspa?date="+new Date().toLocaleString();
         $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(cc){
						                    var member = eval("(" + cc + ")");
						                     var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                  }
								            var oList = member.organizationlist;
								            var data1 = new Array();
											        data1[0] = {
											            id: comID,
											            pid: '-1',
											            text: "全部"
											        }; 
								            for (var i = 0; i < oList.length; i++) {
								                data1[i + 1] = {
								                    id: oList[i].organizationID,
								                    pid: oList[i].organizationPID,
								                    text: oList[i].organizationName
								                };
								            }
								            ts3 = new TreeSelector($("#organizationID")[0], data1, -1);
        									ts3.createTree();      
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
       
})
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
						<th width="200" align="center">
							公司名称
						</th>
						<th width="100" align="center">
							员工编号
						</th>
						<th width="100" align="center">
							员工姓名
						</th>
						<th width="100" align="center">
							当天日期
						</th>
						<th width="100" align="center">
							起时间
						</th>
						<th width="100" align="center">
							止时间
						</th>
						<th width="200" align="center">
							工作地点
						</th>
						<th width="200" align="center">
							完成工作内容
						</th>
						<th width="100" align="center">
							得分类别
						</th>
						<th width="100" align="center">
							得分
						</th>
						<th width="100" align="center">
							附件类别及名称
						</th>
						<th width="100" align="center">
							主管签字
						</th>
						<th width="100" align="center">
							人事主管管理
						</th>
						<th width="100" align="center">
							公司经理
						</th>
						<th width="100" align="center">
							状态
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${logBookID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${logBookID}" />
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="todaydate">${fn:substring(todaydate, 0, 10)}</span>
							</td>
							<td>
								<span id="startdate">${startdate}</span>
							</td>
							<td>
								<span id="enddate">${enddate}</span>
							</td>
							<td>
								<span id="jobLocation">${jobLocation}</span>
							</td>
							<td>
								<span id="jobContent">${jobContent}</span>
							</td>
							<td>
								<span id="scoreSort"></span>
								<s:select list="%{#request.scoreSortlist}" listKey="codeID"
									listValue="codeValue" name="scoreSort" disabled="true"
									theme="simple"></s:select>
							</td>
							<td>
								<span id="bisect">${bisect}</span>
							</td>
							<td>
								<s:if test="attachment==null||attachment==''">无</s:if>
								<s:else>
									<span id="look" onclick="lookImage('${attachment}');"><a
										href="#">查看</a>
									</span>
								</s:else>
								<span id="attachment" style="display: none">${attachment}</span>
							</td>
							<td>
								<span id="supervisor">${supervisor}</span>
							</td>
							<td>
								<span id="staffingManage">${staffingManage}</span>
							</td>
							<td>
								<span id="manager">${manager}</span>
							</td>
							<td>
								<span id="status"> <s:if test="status == 00">未加锁</s:if> <s:if
										test="status == 01">已加锁</s:if> </span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/logbooksummary/ea_getListLogBook.jspa?&aa=aa&pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}">
				</c:param>
			</c:import>
		</div>
		<div class="jqmWindow jqmWindowcss2" style="width: 760px; top: 10%"
			id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<div class="drag">
					工作日志汇总
					<div class="close"></div>
				</div>
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
					</div>
					<table width="750" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="750" height="250" border="0" align="center"
									cellpadding="0" cellspacing="0" id="stafftable2"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td width="125" align="right">
											员工编号：
										</td>
										<td width="190">
											<input name="staffCode" type="text" id="staffCode" size="20" />
										</td>
										<td width="125" align="right">
											员工姓名：
										</td>
										<td width="190">
											<input name="staffName" type="text" id="staffName" size="20" />
										</td>
										<td width="144" rowspan="4" align="center">
											<img width="99" height="135" id="attachment" />
										</td>
									</tr>
									<tr>
										<td align="right">
											起时间：
										</td>
										<td>
											<input id="startdate" type="text" name="startdate"
												class="input" size="20" />
										</td>
										<td align="right">
											止时间：
										</td>
										<td>
											<input id="enddate" type="text" name="enddate" class="input"
												size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											工作地点：
										</td>
										<td>
											<input id="jobLocation" type="text" name="jobLocation"
												class="input" size="20" />
										</td>
										<td align="right">
											得分类别：
										</td>
										<td>
											<input name="scoreSort" type="text" class="input"
												id="scoreSort" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											得分：
										</td>
										<td>
											<input id="bisect" type="text" name="bisect" class="input"
												size="20" />
										</td>
										<td align="right">
											当天日期：
										</td>
										<td>
											<input name="todaydate" type="text" class="input"
												id="todaydate" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											主管签字：
										</td>
										<td>
											<input id="supervisor" type="text" name="supervisor"
												class="input" size="20" />
										</td>
										<td align="right">
											人事主管管理：
										</td>
										<td>
											<input id="staffingManage" type="text" name="staffingManage"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right" rowspan="2">
											完成工作内容：
										</td>
										<td>
											<textarea rows="2" cols="25" id="jobContent"
												name="jobContent" class="input"></textarea>
										</td>
										<td align="right">
											公司经理：
										</td>
										<td>
											<input id="manager" type="text" name="manager" class="input"
												size="20" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 500px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="493" id="cataffSearchTable">
					<tr>
						<td width="97" align="right">
							员工编号：
						</td>
						<td width="384">
							<input name="logbooksummary.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							员工姓名：
						</td>
						<td>
							<input name="logbooksummary.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="organizationID" name="logbooksummary.organizationID"></select>
						</td>
					</tr>
					<tr>
						<td align="right">
							得分类别：
						</td>
						<td>
							<s:select list="%{#request.scoreSortlist}" listKey="codeID"
								listValue="codeValue" headerKey="" headerValue="全部"
								name="logbooksummary.scoreSort" theme="simple"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							时间：
						</td>
						<td>
							<input name="sdate" onfocus="date(this);" />
							至
							<input name="edate" onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="right">
							状态：
						</td>
						<td>
							<select name="logbooksummary.status">
								<option value="" selected="selected">
									全部
								</option>
								<option value="01">
									已加锁
								</option>
								<option value="00">
									未加锁
								</option>
							</select>
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


		<div class="jqmWindow" style="width: 500px; right: 25%; top: 10%"
			id="DaYin">
			<form name="postDaYinForm" id="postDaYinForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					打印信息
					<div class="close">
					</div>
				</div>
				<table width="493" id="cataffPrintTable">
					<tr>
						<td align="right">
							员工姓名：
						</td>
						<td>
							<input name="logbooksummary.staffName" id="staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							时间：
						</td>
						<td>
							<input name="logbooksummary.todaydate" class="todaydate"
								id="todaydate" onfocus="date(this)" />
						</td>
					</tr>

				</table>
				<div align="center">
					<input type="button" class="input-button" id="toDaYin" value=" 打印 " />
					<input name="search" type="hidden" value="search" />
					<input id="pagevalue" type="hidden" value="aa" />
				</div>
			</form>
		</div>
	</body>
</html>