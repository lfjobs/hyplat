<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@page import="hy.ea.bo.Company"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany"); %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>车辆信息集团汇总管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <style type="text/css"> 
        .wenben{
        	border-color: #FFFFFF;
        	border-width: 0px;
        	background: url('../../../images/ea/office/brue.jpg')
        }
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/company/office_ea/CarInforGroup.js"></script>
         <script type="text/javascript">
			 var basePath='<%=basePath%>';           
	         var pNumber =${pageNumber};  
	         var search='${search}';  
	         var carID = "";
			 var companyID='${account.companyID}';
			 var companyName='${account.companyName}';
   		</script>  
	</head>
	
	<body >
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
                           <th width="150" align="center">
                            	公司
                        </th>
                         <th width="100" align="center">
                            	部门
                        </th>
                        <th width="100" align="center">
                           		 责任人
                        </th>
                        <th width="150" align="center">
                            	车型
                        </th>
                        <th width="100" align="center">
                            	车牌号
                        </th>
                        <th width="100" align="center">
                           		 发动机号
                        </th>
                        <th width="100" align="center">
                           		车架号
                        </th>
                        <th width="100" align="center">
                           		注册登记日期
                        </th>
                        <th width="100" align="center">
                           		 当前状态
                        </th>
                        <th width="100" align="center">
                           		 加盟状态
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <c:forEach var="arr" items="${pageForm.list}">
                        <tr id="${arr[0]}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${arr[0]}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span >${arr[1]}</span>
                            </td>
                            <td>
                                <span >${arr[2]}</span>
                            </td>
                             <td>
                                <span id="staffName">${arr[3]}</span>
                            </td>
                            <td>
                                <span id="carType">${arr[4]}</span>
                            </td>
                             <td>
                                <span id="carNum">${arr[5]}</span>
                            </td>     
                            <td>
                                <span id="engineNum">${arr[6]}</span>
                            </td>
                            
                            <td>
                                <span id="carFrameNum">${arr[7]}</span>
                            </td>
                            <td>
                                <span id="registrationDate">${arr[8]}</span>
                            </td>
                            <td>
                            	<span id="state3">${arr[9]}</span>
                            </td>
                            <td>
                            	<span id="state1">${arr[10]}</span>
                            </td>
                        </tr>
                        <%number++;%>
                    </c:forEach>
                </tbody>
                </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/cargroup/ea_getCarInformationList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
     
        <!--搜索窗口 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width:400px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					<tr>
						<td height="40" width = "35%">
							查询条件 
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							车牌号：
						</td>
						<td>
							<input name="carInformation.carNum" />
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							车辆类型：
						</td>
						<td>
							<input name="carInformation.carType" />
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							公司名称：
						</td>
						<td align="left" id="dept">
							<select name="carInformation.companyID" id="companyID">
								<option value="">
									请选择
								</option>
								</select>
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							责任人：
						</td>
						<td >
							<input name="carInformation.staffName" />
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							当前状态：
						</td>
						<td>
							<select name="carInformation.state3" id="state3" style="width: 135px;">
							<option value="" selected="selected">
									全部
								</option>
								<option value="00">未使用</option>
					    			<option value="01">已使用</option>
					    			<option value="10">下线</option>
					   			</select>
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							加盟状态：
						</td>
						<td>
						<select name="carInformation.state1" id="state1" style="width: 135px;">
						<option value="" selected="selected">
									全部
								</option>
					     <option value="00">加盟车</option>
					    <option value="01">本校车</option>
					   </select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
    </body>
</html>