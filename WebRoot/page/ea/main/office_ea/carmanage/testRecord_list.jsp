<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<title>考场练车记录</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/carmanage/testRecord_list.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/office_ea/carmanage/pop_up_box.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
var basePath="<%=basePath%>";
var ppageNumber = "${pageNumber}";
var account = '${tcc.account}';
var signInState = '${bfmation.signInState}';
var bifId = "";

</script>
</head>
<body>
    <div style="margin-top:10px;margin-left:10px;display:none;"
		class="query"><form id="SearchForm" name="SearchForm" method="post">
		<input type="submit" name="submit" style="display:none;"/>请输入用户微分金账号：<input type="text"
			style="width:90px;height:18px;" name="tcc.account" value="${tcc.account }" id="account"/>
        <select class="whether" style="width:150px;height:20px;margin-left:50px;">
            <option data-signInState="">---全部---</option>
            <option data-signInState="00">未签到</option>
            <option data-signInState="01">已签到</option>
            <option data-signInState="02">已签退</option>
            <option data-signInState="03">签退失败</option>
        </select>
        <input type="hidden" class="signInState" value="${bfmation.signInState}" name="bfmation.signInState">
        <input
			type="button" class="input-button" value="  查询   "  id="tosearch" style="margin:0px;margin-left:5px;" />
		</form>
		
	</div>
	<div style="margin-top:10px;margin-left:10px;display:none;" class="query">
	考场记录
		
	</div>
	<div class="main_main">
		<table class="JQueryflexme">
			<thead>
				<tr class="tablewith">
					<th width="40" align="center">选择</th>
					<th width="40" align="center">序号</th>
					<th width="150" align="center">编号</th>
					<th width="80" align="center">用户名称</th>
					<th width="100" align="center">微分金账号</th>
					<th width="150" align="center">签到时间</th>
					<th width="150" align="center">签退时间</th>
					<th width="100" align="center">时长(总时间)</th>
					<th width="50" align="center">金额(元)</th>
					<th width="80" align="center">考场编号</th>
					<th width="80" align="center">考场名称</th>
					<th width="80" align="center">主管名称</th>
					<th width="80" align="center">教练名称</th>
					<th width="120" align="center">监管人员微分金账号</th>
					<th width="80" align="center">监管人员名称</th>
					<th width="80" align="center">状态</th>
				</tr>
			</thead>
			<tbody>
				<%
					int number = 1;
				%>
				<s:iterator value="pageForm.list" var="c">
					<tr id="${c[0]}" class="">
						<td><input type="radio" name="a" class="JQuerypersonvalue" value="" /></td>
						<td><%=number%></td>
						<td><span id="journalnum">${c[1]}</span></td>
						<td><span id="fname">${c[2]}</span></td>
                        <td><span id="macct">${c[3]}</span></td>
                        <td>
							<span id="checkintime">
                        		<c:choose>
									<c:when test="${empty c[4]}">
										--------
									</c:when>
									<c:otherwise>
										${c[4]}
									</c:otherwise>
								</c:choose>
							</span>
						</td>
						<td>
							<span id="signbacktime">
								<c:choose>
                                    <c:when test="${empty c[5]}">
                                        --------
                                    </c:when>
                                    <c:otherwise>
                                        ${c[5]}
                                    </c:otherwise>
                                </c:choose>
							</span>
						</td>
                        <td>
							<span id="howMuchTime">
								<c:choose>
                                    <c:when test="${empty c[6]}">
                                        --------
                                    </c:when>
                                    <c:otherwise>
                                        ${c[6]}
                                    </c:otherwise>
                                </c:choose>
							</span>
						</td>
                        <td>
							<span id="money">
								<c:choose>
                                    <c:when test="${empty c[7]}">
                                        ------
                                    </c:when>
                                    <c:otherwise>
                                        ${c[7]}
                                    </c:otherwise>
                                </c:choose>
							</span>
						</td>
                        <td><span id="ernumber">${c[8]}</span></td>
                        <td><span id="ername">${c[9]}</span></td>
                        <td><span id="dnaem">${c[10]}</span></td>
                        <td><span id="sname">${c[11]}</span></td>
                        <td><span id="cacct">${c[12]}</span></td>
                        <td><span id="aname">${c[13]}</span></td>
						<td>
                            <span id="signInState">
                                <c:choose>
                                    <c:when test="${c[14] eq '00'}">
                                        未签到
                                    </c:when>
                                    <c:when test="${c[14] eq '01'}">
                                        已签到
                                    </c:when>
                                    <c:when test="${c[14] eq '02'}">
                                        已签退
                                    </c:when>
                                    <c:when test="${c[14] eq '03'}">
                                        签退失败
                                    </c:when>
                                </c:choose>
                            </span>
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
				value="ea/carmanage/ea_testRecordList.jspa?pageNumber=${pageNumber}&tcc.account=${tcc.account }&bfmation.signInState=${bfmation.signInState}">
			</c:param>
		</c:import>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>