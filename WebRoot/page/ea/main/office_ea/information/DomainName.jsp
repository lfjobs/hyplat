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
<title>域名管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/information/DomainName.js"></script>
<script  type="text/javascript">
   var domainID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber = '${pageNumber}';  
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
            <th width="93" align="center" >编号</th>
             <th width="100" align="center">单位名称</th>
            <th width="100" align="center" >单位网址</th>
            <th width="100" align="center" >域名</th>           
      </tr>
    </thead>
		<tbody>
		 <%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${domainID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${domainID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
				</td>
            <td class="td_bg01">
               <span id="domainCode">${domainCode}</span>
				</td>
			 <td class="td_bg01">
             <span id="companyName">${companyName} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="companyUrl">${companyUrl}</span>
            </td>
            <td class="td_bg01">
               <span id="domain" >${domain}</span>
                <span id="domainKey" style="display:none">${domainKey}</span>  
			   <span id="domainID" style="display:none">${domainID}</span>
			</td>     
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/domainname/ea_getListDomainname.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:15%" id="jqModelSearch">
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
                          单位名称：
                        </td>
                        <td>         
                           <input   name="domainName.companyName" />
                        </td>
                    </tr>
					<tr>
                        <td align="right">
                           域名：
                        </td>
                        <td>
                           <input   name="domainName.domain" />
                        </td>
                    </tr>
                </table>
                <div style="text-align: center;">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
				
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag">域名管理
				    <div class="close"></div>
				    </div>
			 <div class="content">
			 <input type="submit" name="submit" style="display:none"/>
			  <div class="contentbannb">
			  </div>
			 <table width="550" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="550" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100" height="37"  align="right">编号：</td>
			        <td width="148" ><input name="domainName.domainCode" type="text" id="domainCode" size="20"/></td>
			        <td width="90"  align="right">单位名称：</td>
			        <td width="212" >
			        <input name="domainName.companyName" type="text" id="companyName" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">单位网址：</td>
			        <td ><input  id="companyUrl" type="text"  class="input"  name="domainName.companyUrl" size="20"/></td>
			        <td align="right" height="41">域名：</td>
			        <td width="212"><input id="domain"   type="text"  class="input" name="domainName.domain" size="30"/>
			        <input name="domainName.domainID" type="hidden" id="domainID" size="20"/>
			        <input type="hidden" name="domainName.domainKey" id="domainKey" /></td>
			        </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div style="text-align: center;">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value=" 保存 " />
                     <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
