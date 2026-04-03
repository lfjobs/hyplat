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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网络加密管理公司汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/company/office_ea/netWorkCompany.js"></script>
<script  type="text/javascript">
   var basePath = '<%=basePath%>';           
   var bpageNumber = '${pageNumber}';
   var search = '${search}';  
</script>
</head>
<body>
<div id="main_main" class="main_main">
  <table   class="address">
  <thead> 
	 	    <tr>
            <th width="40" align="center" >序号</th>
            <th width="93" align="center" >网络地址</th>
            <th width="100" align="center">网络名称</th>
            <th width="100" align="center">网络编号</th>
            <th width="100" align="center">网络密码</th>
            <th width="100" align="center">修改密码</th>
            <th width="100" align="center">承办人</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list" status="number">
          <tr class="td_bg01 saveAjax" id="${netWorkID}">
            <td class="td_bg01">
                <s:property value="%{#number.index+1}"/>
			</td>
            <td class="td_bg01">
               <span id="netWorkAddress" class="netWorkAddress">${netWorkAddress}</span>
				</td>
			 <td class="td_bg01">
             <span id="netWorkName" class="netWorkName">${netWorkName} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="netWorkCode" class="netWorkCode">${netWorkCode}</span>
            </td>
             <td class="td_bg01">
             <span id="netWorkPassword" class="netWorkPassword">${netWorkPassword}</span>
            </td>
             <td class="td_bg01">
             <span id="amendPassword" class="amendPassword">${amendPassword}</span>
            </td>
             <td class="td_bg01">
             <span id="admin" class="admin">${admin}</span>
             <span id="netWorkID" style="display:none">${netWorkID}</span>
		     <span id="netWorkKey" style="display:none">${netWorkKey}</span>
            </td>
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/networkcompany/ea_getNetWorkCompany.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:10%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
                <div class="drag">
                <input type="submit" name="submit" style="display:none"/>
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           网络地址：
                        </td>
                        <td>         
                           <input name="network.netWorkAddress" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                           网络名称：
                        </td>
                        <td>         
                           <input name="network.netWorkName" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                           网络编号：
                        </td>
                        <td>         
                           <input name="network.netWorkCode" />
                        </td>
                    </tr>
                </table>
                <div style="text-align: center;">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
</body>
</html>