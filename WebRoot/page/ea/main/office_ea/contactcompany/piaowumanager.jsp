<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>票务管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
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
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/contactcompany/piaowumanager.js"></script>
		<script type="text/javascript">
		 var ccompanyID = '';
		 var basePath='<%=basePath%>'; 
		 var ticketbusinessid = "";
		 var pNumber =${pageNumber};  
		 var search='${search}';
		 var token = 0 ;  
		 var personurl='';
		 var notoken = 0;
		 var retoken=0;
		 var select=1;
</script>
<style type="text/css">
	.tishi{
	color: red;
	}
	a{
	color:green;
	text-decoration: none;
	}
	a:HOVER {
	color:red;
	text-decoration: underline;
	}
</style>
	</head>
	<body>
	<form name="piaojuInformationForm" id="piaojuInformationForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="main_main">
			<table class="JQueryflexme" id="">
				<thead>
					<tr class="tablewith">
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="100" align="center">
							票据类型
						</th>
						<th width="100" align="center">
							班次/车次
						</th>
						<th width="100" align="center">
							出发地点
						</th>
						<th width="100" align="center">
							到达地点
						</th>
						<th width="100" align="center">
							出发日期
						</th>
						<th width="100" align="center">
							到达日期
						</th>
						<th width="100" align="center">
							出发时间
						</th>
						<th width="100" align="center">
							到达时间
						</th>
						<th width="100" align="center">
							标准价
						</th>
						<th width="100" align="center">
							折扣价
						</th>
						<th width="100" align="center">
							航舱等级
						</th>
						<th width="100" align="center">
							席别
						</th>
						<th width="100" align="center">
							票据号
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${ticketbusinessid}" class="trclass">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ticketbusinessid}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="ticketType">${ticketType}</span>
							</td>

							<td>
								<span id="classOrtrains">${classOrtrains}</span>
							</td>
							<td>
								<span id="startplace">${startplace}</span>
							</td>
							<td>
								<span id="endplace">${endplace}</span>
							</td>
							<td>
									<span id="departure">${fn:substring(departure,0,10)}</span>
							</td>
							<td>
								<span id="arrivalDate">${fn:substring(arrivalDate,0,10)}</span>
							</td>
							<td>
								<span id="departureTime">${departureTime}</span>
							</td>
							<td>
								<span id="arrivalTime">${arrivalTime}</span>
							</td>
							<td>
								<span id="normalPrice">${normalPrice}</span>
							</td>
							<td>
								<span id="discount">${discount}</span>
							</td>
							<td>
								<span id="airTankLevel"><c:if test="${airTankLevel ne '' }">${airTankLevel}</c:if></span> 
								<span><c:if test="${empty airTankLevel}">无</c:if></span>
							</td>
							<td>
								<span id="positionLamp"><c:if test="${positionLamp ne '' }">${positionLamp}</c:if></span> 
								<span><c:if test="${empty positionLamp}">无</c:if></span>
							</td>
							<td>
								<span id="billNumber">${billNumber}</span>
								<span id="companyID" style="display: none">${companyID}</span>
								<span id="ticketbusinessid" style="display:none">${ticketbusinessid}</span>
								<span id="ticketbusinesskey" style="display:none">${ticketbusinesskey}</span>
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
					value="ea/piaowuManager/ea_getListpiaowu.jspa?search=${search}&pageNumber=${pageNumber}">
				</c:param>
			</c:import>
			</div>
            </form>			
		<!--         添加修改票务信息         -->
		<div class="jqmWindow" style="width: 700px; right: 33%; top: 15%;"
			id="jqModelAdd">
			<form name="piaowuAddForm" id="piaowuAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag"  id="addtitle">
					票务信息 
					<div class="close">
					
					</div>
				</div>
				<table id="hotelSearchTable" align="center" border="0px">
					<tr align="center">
						<td align="left">
							<lable class="tishi">*</lable>票&nbsp;&nbsp;据&nbsp;&nbsp;类&nbsp;&nbsp;型：
						</td>
						<td align="left">
							<select id="piaojutype" style="width: 100px" name="piaowumanager.ticketType" >
							<option id="choose" selected="selected">--请选择--</option>
							<option id="choosetrain">火车</option>
							<option id="chooseairplan">飞机</option>
							</select>
						</td>
						<td align="left">
							<lable class="tishi">*</lable>班次/车次：
						</td>
						<td align="left">
							<input type="text" id="classOrtrains" name="piaowumanager.classOrtrains" size="20" class="put3" />
							
						</td>
					</tr>
					<tr align="center">
						<td align="left">
							出发机场/车站：
						</td>
						<td align="left">
							<input type="text" id="startplace" name="piaowumanager.startplace" size="20" />
						</td>
						 <td align="left">
							<lable class="tishi">*</lable>到达机场/车站：
						</td>
						<td align="left">
							<input type="text"  id="endplace" name="piaowumanager.endplace" size="20" class="put3" />
						</td>
					</tr>
					<tr align="center"> 
						<td align="left">
							出发日期：
						</td>
						<td align="left">
						<input type="text" id="departure"
						onfocus="date(this);" name="piaowumanager.departure" size="20"/>
						</td>
						<td align="left">
							<lable class="tishi">*</lable>到达日期：
						</td>
						<td align="left">
							<input type="text" id="arrivalDate"
						onfocus="date(this);" name="piaowumanager.arrivalDate" size="20"
						class="put3" />
						</td>
					</tr>
					<tr align="center">
						<td align="left">
							出发时间：
						</td>
						<td align="left">
							<input type="text" id="departureTime"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'});" name="piaowumanager.departureTime" size="20"/>
						</td>
						<td align="left">
							<lable class="tishi">*</lable>到达时间：
						</td>
						<td align="left">
							<input type="text" id="arrivalTime" class="put3"
						onfocus="WdatePicker({dateFmt:'HH:mm:ss'});" name="piaowumanager.arrivalTime" size="20" />
						</td>
					</tr>
					<tr align="center">
						<td align="left">
							<lable class="tishi">*</lable>标准价格(RMB)：
						</td>
						<td align="left">
							<input type="text"  id="normalPrice" name="piaowumanager.normalPrice" size="20" class="put3" />
						</td> 
						<td align="left">
							航舱等级：
						</td>
						<td align="left">
							<select id="airplan" name="piaowumanager.airTankLevel">
								<option>经济舱</option>
								<option>头等舱</option>
								<option>公务舱</option>
							</select>
						</td> 
					</tr>
					<tr align="center">
						<td align="left">
							折扣价格(RMB)：
						</td>
						<td align="left">
							<input type="text" id="discount" name="piaowumanager.discount" size="20"/>
						</td>
						<td align="left">
							席&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：
						</td>
						<td align="left">
							<select id="train" name="piaowumanager.positionLamp">
								<option>硬座</option>
								<option>硬卧</option>
								<option>软座</option>
								<option>一等软座</option>
								<option>二等软座</option>
								<option>软卧</option>
								<option>无座</option>
							</select>
						</td>
					</tr>
					<tr align="center">
						<td align="left">
							票&nbsp;据&nbsp;号：
						</td>
						<td align="left" colspan="3">
						<input id ="billNumber" class="billNumber"
								style="width: 80px; text-align: center;"/>&nbsp;&nbsp;<a id="addpiaohao"  href='#'>增加</a>
						</td>
					</tr>
					<tr align="center">
						<td colspan="4">
						<input name="piaowumanager.ticketbusinessid" id="ticketbusinessid" type="hidden" class="input"  size="20"/>
					<input name="piaowumanager.ticketbusinesskey" id="ticketbusinesskey" type="hidden" class="input"  size="20"/>
						<input name="piaowumanager.billNumber" id = "billNumber2" value="" style="display: none"/>
						<div style="float: left;"><a id="shanchupiaohao" href='#' >删除</a></div>
						<div style="width:500px;height:80px;background-color: white ;float: left;text-align: left"  id="piaowuInformation"></div>
						</td>
					</tr>
					
				</table>
				<div align="center">
					<input type="button" class="input-button JQuerySubmit" value=" 保存 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		
		 <!--搜索窗口 -->
        <div class="jqmWindow jqmWindowcss" style="width: 450px;top:10%" id="jqModelSearch">
			 <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                         票据类型：
                        </td>
                        <td>
                           <select  style="width: 100px" name="piaowumanager.ticketType" >
							<option id="choosesearch" selected="selected">--请选择--</option>
							<option id="searchtrain">火车</option>
							<option id="searchairplan">飞机</option>
							</select>
                        </td>
                    </tr>
					<tr>
                        <td>
                          班次/车次：
                        </td>
                        <td>
                           <input name="piaowumanager.classOrtrains" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                         出发日期：
                        </td>
                        <td>
                           <input name="piaowumanager.startplace" onfocus="date(this);"/> 
                        </td>
                         <td>
                        到达日期：
                        </td>
                        <td>
                           <input name="piaowumanager.endplace" onfocus="date(this);"/> 
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
