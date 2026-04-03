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
<title>文件管理公司汇总</title>
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
<script src="<%=basePath%>js/ea/company/office_ea/fileManageCompany.js"></script>

<script  type="text/javascript">
   var  basePath = '<%=basePath%>';           
   var  bpageNumber = '${pageNumber}';
   var  search='${search}';  
</script>

</head>
<body>
<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	  <tr>
        <th width="20" align="center" >序号</th>
        <th width="70" align="center" >日期</th>
        <th width="150" align="center">公文标题</th>
        <th width="100" align="center" >拟搞单位</th>
        <th width="100" align="center" >主办单位</th>
        <th width="100" align="center" >主送单位</th>
        <th width="100" align="center" >抄送单位</th>
        <th width="100" align="center" >公文批复意见</th>
        <th width="100" align="center" >公文文件类型</th>
        <th width="100" align="center" >公文附件文件</th>
        <th width="100" align="center" >公文负责人</th>
        <th width="100" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list" status="number">
          <tr class="td_bg01 saveAjax" id="${fileManageID}">
            <td class="td_bg01">
                <s:property value="%{#number.index+1}"/>
			</td>
            <td class="td_bg01">
               <span id="fileManageDate" class="fileManageDate">${fn:substring(fileManageDate,0,10)}</span>
				</td>
			 <td class="td_bg01">
             <span id="documentTitle">${documentTitle} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="planUnit">${planUnit}</span>
            </td>
             <td class="td_bg01">
             <span id="frontUnit">${frontUnit}</span>
            </td>
             <td class="td_bg01">
             <span id="blastMainUnit">${blastMainUnit}</span>
            </td>
            
            <td class="td_bg01">
             <span id="copyUnit">${copyUnit}</span>
            </td>
            <td class="td_bg01">
             <span id="fileRescriptumAttitude">${fileRescriptumAttitude}</span>
            </td>
            <td class="td_bg01">
             <span id="fileType">${fileType}</span>
            </td>
            <td class="td_bg01">
              <span id="fileAccessories" style="display:none">${fileAccessories}</span>
              <s:if test="fileAccessories==null||fileAccessories==''">无</s:if>
                             <s:else>
                                <span id="file"   onclick="lookImage('${fileAccessories}');"><a href="#">查看</a></span>
                            </s:else>
            </td>
            <td class="td_bg01">
             <span id="fileArincipal">${fileArincipal}</span>
            </td>
             <td class="td_bg01">
             <span id="remark">${remark}</span>
             <span id="fileManageID" style="display:none">${fileManageID}</span>
		     <span id="fileManageKey" style="display:none">${fileManageKey}</span>
            </td>
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/fileManageCompany/ea_getaFileManageCompanyList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:15%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post" enctype="multipart/form-data">
                <div class="drag">
                <input type="submit" name="submit" style="display:none"/>
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           公文标题：
                        </td>
                        <td>         
                           <input   name="fileManage.documentTitle" />
                        </td>
                    </tr>
                </table>
                <div style="text-align:center" >
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
</body>
</html>