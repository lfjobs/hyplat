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
<title>网络加密管理</title>
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
<script src="<%=basePath%>js/ea/office_ea/information/NetWork.js"></script>
<script  type="text/javascript">
   var  netWorkID = '';
   var  basePath='<%=basePath%>';           
   var  bpageNumber =${pageNumber};
   var  search='${search}';  
   var  token=0;
</script>
</head>
<body>
<div id="main_main" class="main_main">
  <table   class="address">
  <thead> 
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
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
		 <%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${netWorkID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${netWorkID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
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
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/network/ea_getNetWork.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:10%" id="jqModelSearch">
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
                           网络地址：
                        </td>
                        <td>         
                           <input   name="network.netWorkAddress" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                           网络名称：
                        </td>
                        <td>         
                           <input   name="network.netWorkName" />
                        </td>
                    </tr>
                     <tr>
                        <td>
                           网络编号：
                        </td>
                        <td>         
                           <input   name="network.netWorkCode" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag">网络加密管理
				    	<div class="close"></div>
				   </div>
				   <input type="submit" name="submit" style="display:none"/>
			       <div class="content">
			       <div class="contentbannb">
			       </div>
			 <table width="550" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="550" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100" height="37"  align="right">网络地址：</td>
			        <td width="148" ><input name="network.netWorkAddress" type="text" id="netWorkAddress" size="20"/></td>
			        <td width="90"  align="right">网络名称：</td>
			        <td width="212" ><input name="network.netWorkName" type="text" id="netWorkName" size="20"/></td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">网络编号：</td>
			        <td ><input  id="netWorkCode" type="text"  class="input"  name="network.netWorkCode" size="20" /></td>
			        <td align="right" height="41">网络密码：</td>
			        <td width="212"><input id="netWorkPassword"   type="text"  class="input" name="network.netWorkPassword" size="30"/></td>
			       </tr>
			       <tr>
			        <td align="right" height="41">修改密码：</td>
			        <td width="212"><input id="amendPassword"   type="text"  class="input" name="network.amendPassword" size="30"/></td>	
			        <td align="right" height="41">操作人：</td>
			        <td width="212"><input id="admin"   type="text"  class="input" name="network.admin" size="30"/>	
			        <input name="network.netWorkID" type="hidden" id="netWorkID" size="20"/>
			        <input type="hidden" name="network.netWorkKey" id="netWorkKey" /></td>
			        </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                     <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value="保存 " />
              		 <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
