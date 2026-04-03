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
        <title>网络宣传管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
                <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/office_ea/information/Propagate.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  propagateID = '';
         var  token=0;
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
                            编号		
                        </th>
                         <th width="100" align="center">
                            起始日期
                        </th>
                         <th width="100" align="center">
                            终止日期
                        </th>
                         <th width="150" align="center">
                           网络名称
                        </th>
                         <th width="200" align="center">
                           宣传主题
                        </th>
                         <th width="200" align="center">
                           宣传内容
                        </th>
                         <th width="100" align="center">
                           预算费用
                        </th>
                         <th width="100" align="center">
                           宣传人员
                        </th>
                         <th width="100" align="center">
                           批准人
                        </th>
                         <th width="100" align="center">
                           承办人
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${propagateID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${propagateID}"/>
                            </td>
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                            <td>
                                <span id="serialNumber">${serialNumber}</span>
                            </td>
                            <td>
                               <span id="startDate">${fn:substring(startDate,0,10)}</span>
                            </td>
                            <td>
                                <span id="endDate">${fn:substring(endDate,0,10)}</span>
                            </td>
                            <td>
                                <span id="netName">${netName}</span>
                            </td>
                            <td>
                                <span id="subject">${subject}</span>
                            </td>
                            <td>
                                <span id="content">${content}</span>
                            </td>
                            <td>
                                <span id="cost">${cost}</span>
                            </td>
                            <td>
                                <span id="propagatePerson">${propagatePerson}</span>
                            </td>
                            <td>
                                <span id="approvePerson">${approvePerson}</span>
                            </td>
                            <td>
                                <span id="executePerson">${executePerson}</span>
                                <span id="propagateKey" style="display:none">${propagateKey}</span>
                                <span id="propagateID" style="display:none">${propagateID}</span>
                                <span id="companyID" style="display:none">${companyID}</span>
                                <span id="organizationID" style="display:none">${organizationID}</span> 
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/propagate/ea_getPropagateList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        <div class="jqmWindow jqmWindowcss" style="top: 10%;width: 450px;" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag"> 详细信息
				    	<div class="close"></div>
				   </div>
				   <input type="submit" name="submit" style="display:none"/>
			       <div class="content">
			       <div class="contentbannb">
			       </div>
  				<table cellpadding="5px" cellspacing="10px" name="stafftable" id="stafftable"> 
				    <tr>
				    	<td align="right">
				    		编号：
				    	</td>
				    	<td>
				    		<input name="propagate.serialNumber" id="serialNumber" />
				    	</td>
				    	<td align="right">
				    		开始日期：
				    	</td>
				    	<td> 
				    		<input name="propagate.startDate" id="startDate" onfocus="date(this)"/>
				    	</td>
				    </tr>
				     <tr>
				    	<td align="right">
				    		结束日期：
				    	</td>
				    	<td>
				    		<input name="propagate.endDate" id="endDate" onfocus="date(this)"/>
				    	</td>
				    	<td align="right">
				    		网络名称：
				    	</td>
				    	<td> 
				    		<input name="propagate.netName" id="netName" />
				    	</td>
				    </tr>
				    <tr>
				    	<td align="right">
				    		宣传主题：
				    	</td>
				    	<td>
				    		<input name="propagate.subject" id="subject" />
				    	</td>
				    	<td align="right">
				    		宣传内容：
				    	</td>
				    	<td> 
				    		<input name="propagate.content" id="content" />
				    	</td>
				    </tr>
				     <tr>
				    	<td align="right">
				    		预算费用：
				    	</td>
				    	<td>
				    		<input name="propagate.cost" id="cost" />
				    	</td>
				    	<td align="right">
				    		宣传人：
				    	</td>
				    	<td> 
				    		<input name="propagate.propagatePerson" id="propagatePerson" />
				    	</td>
				    </tr>
				     <tr>
				    	<td align="right">
				    		批准人：
				    	</td>
				    	<td>
				    		<input name="propagate.approvePerson" id="approvePerson" />
				    	</td>
				    	<td align="right">
				    		承办人：
				    	</td>
				    	<td> 
				    		<input name="propagate.executePerson" id="executePerson" />
				    	</td>
				    </tr>
				    <tr>
				    	<td colspan="4" align="right">
				    		 <input type="hidden" name="propagate.propagateID" id="propagateID" />
				            <input type="hidden" name="propagate.propagateKey" id="propagateKey" />
				            <input type="hidden" name="propagate.companyID" id="companyID" />
				            <input type="hidden" name="propagate.organizationID" id="organizationID" />
						    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
				    	</td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div> 
	
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width:500px;right: 25%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="460px" id="cataffSearchTable"> 
                    <tr>
                        <td align="right">
                            编号：
						</td>
                        <td>
                        	 <input name="propagate.serialNumber" id="serialNumber" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right"> 
                            起始日期： 
						</td>
                        <td>
                        	<input name="propagate.startDate" id="startDate" onfocus="date(this)"/>到<input name="propagate.endDate" id="endDate" onfocus="date(this)"/>
                        </td>
                    </tr>
                </table>
            <div align="center"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>