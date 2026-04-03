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
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>车辆信息管理</title>
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
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
        <script src="<%=basePath%>js/ea/driving/driving_CarInformation.js"></script>
        <script src="<%=basePath%>js/ea/office_ea/carMenu.js"></script>
         <script type="text/javascript">
         var treeID = '<%=session.getAttribute("organizationID")%>';
		 var  basePath='<%=basePath%>';           
         var pNumber =${pageNumber};  
         var  search='${search}';  
         var carNums="";
         var personurl = "";
         var carID = "";
         var token=0;
         var treeid;
  		 var treename;
		 var goodsBillsID="";
		 var notoken = 0;
		 var tokens = 0;
		 var token = 0;
		 var companyID='${account.companyID}';
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
                        <th width="100" align="center">
                            	车牌号
                        </th>
                        <th width="100" align="center">
                           		 发动机号
                        </th>
                        <th width="150" align="center">
                            	车辆类型
                        </th>
                        <th width="100" align="center">
                           		购买价格
                        </th>
                        <th width="100" align="center">
                            当前状态
                        </th>
                        <th width="100" align="center">
                            加盟状态
                        </th>
         	            <th width="100" align="center">
                          		购买单位
                        </th>
                        <th width="100" align="center">
                            责任人
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${carID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${carID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span id="carNum">${carNum}</span>
                            </td>     
                            <td>
                                <span id="engineNum">${engineNum}</span>
                            </td>
                            <td>
                                <span id="carType">${carType}</span>
                            </td>
                            <td>
                                <span id="carPrice">${carPrice}</span>
                            </td>
                            <td>
                            	<span id="state3">${state3=='00'?'未使用':state3=='01'?'已使用':'下线'}</span>
                            </td>
                            <td>
                            	<span id="state1">${state1=='00'?'加盟车':'本校车'}</span>
                            </td>
                             <td>
                                <span id="companyName">${companyName}</span>
                                <span id="carID" style="display:none">${carID}</span>
                                 <span id="carKey"  style="display:none">${carKey}</span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            
                        </tr>
                        <%number++;%>
                    </s:iterator>
                </tbody>
                </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/driving/ea_getCarInformationList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        <!--搜索窗口 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
		<input type="submit" name="submit" style="display: none;"/>
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					<tr>
						<td height="20" >
							查询条件 
						</td>
					</tr>
					<tr>
						<td height="20">
							车牌号：
						</td>
						<td>
							<input name="carInformation.carNum" />
						</td>
					</tr>
					<tr>
						<td height="20">
							车辆类型：
						</td>
						<td>
							<input name="carInformation.carType" />
						</td>
					</tr>
					<tr>
						<td height="20">
							责任人：
						</td>
						<td >
							<input name="carInformation.staffName" />
						</td>
					</tr>
					<tr>
						<td height="20">
							加盟状态：
						</td>
						<td>
						<select name="carInformation.state1" id="state1">
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