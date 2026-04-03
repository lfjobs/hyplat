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
<title>IP地址集团汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/company/office_ea/addressIPGroup.js"></script>
<script  type="text/javascript">
   var basePath = '<%=basePath%>';           
   var pNumber = '${pageNumber}';  
   var search = '${search}';
</script>
</head>
<body>
<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
            <th width="40" align="center" >序号</th>
            <th width="93" align="center" >编号</th>
            <th width="100" align="center">姓名</th>
            <th width="100" align="center" >电脑型号</th>
            <th width="120" align="center" >IP地址</th>           
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list" status="number">
          <tr class="td_bg01 saveAjax" id="${ipID}">
            <td class="td_bg01">
                <s:property value="%{#number.index+1}"/>
			</td>
            <td class="td_bg01">
               <span id="ipCode" class="datas">${ipCode}</span>
			</td>
			<td class="td_bg01">
              <span id="userName">${userName}</span>
           	</td>
            <td class="td_bg01">
             <span id="computerModel">${computerModel}</span>
            </td>
            <td class="td_bg01">
               <span id="ipAddress" >${ipAddress}</span>
			</td>     
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/addressIpGroup/ea_getAddressIpGroupList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
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
                           IP地址：
                        </td>
                        <td>
                           <input name="addressIP.ipAddress" />
                        </td>
                    </tr>
                </table>
                <div style="text-align:center;">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
</body>
</html>