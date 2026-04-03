<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>预约记录汇总</title>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script  type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/ddsr/coachreservationrecordsummary.js"></script>
        <style type="text/css">
        	.JQueryflexme .record{
        		cursor: pointer;
        	}
        </style>
        <script type="text/javascript">
			 var  basePath='<%=basePath%>';           
	        var  pNumber =${pageNumber};  
	         var  search='${search}';
	         var  coacKey = '';
	         var  token=0;
	         var notoken=0;
	         
	         var subjType='${dssrsubject.subjType}';//科目导航页面传值 区分科目类别
	         
	         var coacKey="";//选中教练ID
			 var rereAppdate="";//选中预约日期
		</script>    
	</head>
	<body>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr>
                        <th width="40" align="center">
                     	   请选择
                        </th>
                        <th width="40" align="center">
                     	   序号
                        </th>
                        <th width="80" align="center">
                     	   预约时间
                        </th>
                        <th width="80" align="center">
                     	   预约班次
                        </th>
                        <th width="80" align="center">
                     	   预约起时间
                        </th>
                        <th width="80" align="center">
                     	   预约止时间
                        </th>
                        <th width="80" align="center">
                     	   预约人数
                        </th>
                        <th width="80" align="center">
                     	   预约科目
                        </th>
                        <th width="80" align="center">
                     	   预约教练
                        </th>
                        <th width="110" align="center">
                     	   教练电话
                        </th>
                        <th width="80" align="center">
                     	   预约学员
                        </th>
                        <th width="110" align="center">
                     	   学员电话
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" var="list" status="number">
                    	<tr  id="<s:property value="#list[0]"/>">
                    		<td  align="center">
	                    		 <span><input type="radio" name="chek" class="JQuerypersonvalue" value="<s:property value="#list[0]"/>"/></span>
	                         </td>
	                    	 <td  align="center">
	                    		 <span><s:property value="#number.index+1"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[2]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span>
	                    		 	<c:choose>
										<c:when test="${list[3]==10}">早间</c:when>
										<c:when test="${list[3]==20}">上午</c:when>
										<c:when test="${list[3]==30}">下午</c:when>
										<c:when test="${list[3]==40}">晚间</c:when>
									</c:choose>
								</span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[4]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[5]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[6]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span>
	                    		 	<c:choose>
										<c:when test="${list[7]==10}">科一</c:when>
										<c:when test="${list[7]==20}">科二</c:when>
										<c:when test="${list[7]==30}">科三</c:when>
										<c:when test="${list[7]==40}">科四</c:when>
										<c:when test="${list[7]==90}">暂无</c:when>
									</c:choose>
	                    		 </span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[8]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[9]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[10]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[11]"/></span>
	                         </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?pageNumber=${pageNumber}&search=${search}&dssrsubject.subjType=${dssrsubject.subjType}">
                </c:param>
            </c:import>
        </div> 
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
   
   <!-- 根据教练，日期生成 预约详细记录 列表 -->
<div id="jqmWindow2" class="jqmWindow"  align="center"
	style="width: 1000px; height: 450px; absolute; display: none; left: 10%; top: 10%; background: #eff">
		<iframe name="ifr2" id="ifr2" width="1000px" height="400px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit2" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack2" value=" 关闭 "
			style="cursor: hand" />
 </div>
 
    <!-- 选择学员 -->
<div id="jqmWindow3" class="jqmWindow"  align="center"
	style="width: 1000px; height: 450px; absolute; display: none; left: 10%; top: 10%; background: #eff">
		<iframe name="ifr3" id="ifr3" width="1000px" height="400px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit3" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack3" value=" 关闭 "
			style="cursor: hand" />
 </div>
 
 <!-- 查询功能 -->
 <div class="jqmWindow" style="width: 420px; right: 45%;; top: 10%" 
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				<div class="drag">
					预约查询
					<div class="close">
					</div>
				</div>
				<table width="420" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							预约时间：
						</td>
						<td width="261">
							<input type="text" style="width: 80px" name="ddsrreservationrecord.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>&nbsp;
							至&nbsp;<input type="text" style="width: 80px" name="ddsrreservationrecord.searchEndDate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"/>
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							教练姓名：
						</td>
						<td width="261">
							<input name="ddsrcoach.dtHrStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							学员姓名：
						</td>
						<td width="261">
							<input name="dssrstudent.dtHrStaff.staffName" />
						</td>
					</tr>
                </table>
            <div align="center">
            <input type="submit" name="submit" style="display: none" />
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
              <input name="dssrsubject.subjType" type="hidden" value="${dssrsubject.subjType}"/>
            </div>
            </form>
        </div>
 
<!-- 隐藏from  学员选择多个预约时间段 -->   
<form name="ReservationRecoardForm" id="ReservationRecoardForm" method="post" action="">
   			<input type="submit" name="submit" style="display: none" />
   			<input name="rereKeyString" type="hidden" id="rereKeyString"/>
   			<input name="dssrstudent.studKey" type="hidden" id="studKey"/>
</form>
    </body>
</html>