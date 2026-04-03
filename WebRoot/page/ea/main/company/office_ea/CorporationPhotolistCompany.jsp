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
<title>企业图片管理--公司汇总</title>
			<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/company/office_ea/corporationPhotolistCompany.js"></script>
		<script  type="text/javascript">
   		var  basePath='<%=basePath%>';           
   		var  pNumber =${pageNumber};
   		var  search='${search}';  
  	    var  token=0;
</script>
</head>
<body>
  <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
            <th width="20" align="center" >序号</th>
            <th width="93" align="center" >编码</th>
            <th width="100" align="center">名称</th>
            <th width="240" align="center" >题片主题描述</th>
            <th width="100" align="center" >摄制年度</th>
            <th width="100" align="center" >图片文件</th>
            <th width="240" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
		 <%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr>
            <td >
                <span><%=number%></span>
				</td>
            <td>
               <span id="corporationPhotoCode">${corporationPhotoCode}</span>
				</td>
			 <td class="td_bg01">
             <span id="corporationPhotoName">${corporationPhotoName} 
             </span>
           			</td>
            <td >
             <span id="corporationPhotoDepict">${corporationPhotoDepict}</span>
            </td>
             <td >
             <span id="shootingYear">${shootingYear}</span>
            </td>
            <td >
              <span id="PhotoFile" style="display:none">${PhotoFile}</span>
              <s:if test="PhotoFile==null||PhotoFile==''">无</s:if>
                             <s:else>
                                <span id="Photo"   onclick="lookImage('${PhotoFile}');"><a href="#">查看</a></span>
                            </s:else>
            </td>
             <td >
             <span id="remark">${remark}</span>
            </td>
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/corporationPhotoC/ea_getCorporationPhotoList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

    <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post" name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           照片名称：
                        </td>
                        <td>         
                           <input name="corporationPhoto.photoFileName" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                          摄制年度：
                        </td>
                        <td>
                           <input name="corporationPhoto.shootingYear" />
                        </td>
                    </tr>
                </table>
                <div style="text-align:center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                    <input type="submit" name="submit" style="display:none"/>
                </div>
            </form>
        </div>
<s:token></s:token>
</body>
</html>
