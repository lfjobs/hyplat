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
<title>企业图片管理</title>
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
<script src="<%=basePath%>js/ea/office_ea/corporationcode/CorporationPhoto.js"></script>
<script  type="text/javascript">
   var  corporationPhotoID = '';
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
	 	    <th width="30" align="center">选择</th>
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
          <tr class="td_bg01 saveAjax" id="${corporationPhotoID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${corporationPhotoID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
				</td>
            <td class="td_bg01">
               <span id="corporationPhotoCode" class="corporationPhotoCode">${corporationPhotoCode}</span>
				</td>
			 <td class="td_bg01">
             <span id="corporationPhotoName">${corporationPhotoName} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="corporationPhotoDepict">${corporationPhotoDepict}</span>
            </td>
             <td class="td_bg01">
             <span id="shootingYear">${shootingYear}</span>
            </td>
            <td class="td_bg01">
              <span id="PhotoFile" style="display:none">${PhotoFile}</span>
              <s:if test="PhotoFile==null||PhotoFile==''">无</s:if>
                             <s:else>
                                <span id="Photo"   onclick="lookImage('${PhotoFile}');"><a href="#">查看</a></span>
                            </s:else>
            </td>
             <td class="td_bg01">
             <span id="remark">${remark}</span>
             <span id="corporationPhotoID" style="display:none">${corporationPhotoID}</span>
		     <span id="corporationPhotoKey" style="display:none">${corporationPhotoKey}</span>
            </td>
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/corporationPhoto/ea_getCorporationPhotoList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post" enctype="multipart/form-data">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr >
                        <td>
                           图片编码：
                        </td>
                        <td>         
                           <input   name="corporationPhoto.corporationPhotoCode" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		 <form name="cstaffForm" id="cstaffForm" method="post"  action=""  enctype="multipart/form-data">
		  <input type="submit" name="submit" style="display:none"/>
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			
			       <div class="drag">图片管理
				    	<div class="close"></div>
				   </div>
				  
			       <div class="content">
			       <div class="contentbannb">
			       </div>
			 <table width="600" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="570" height="200" border="0"  cellpadding="0" cellspacing="5" id="stafftable2" style="margin-top:5px;margin-bottom:5px;margin-left: 10px">
			      <tr>
			        <td  height="37"  align="right">编码：</td>
			        <td width="120" ><input name="corporationPhoto.corporationPhotoCode" type="text" id="corporationPhotoCode" size="20" /></td>
			        <td width="60" align="right">文件</td>
			        <td width="125" rowspan="3" align="left"><img id="pic" width="99" height="135"  /></td>
			      </tr>
			      <tr>  
			        <td  height="37"  align="right">名称：</td>
			        <td width="120" ><input name="corporationPhoto.corporationPhotoName" type="text" id="corporationPhotoName" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">题片主题描述位：</td>
			        <td ><input  id="corporationPhotoDepict" type="text"  class="input"  name="corporationPhoto.corporationPhotoDepict" size="20"/></td>
			        </tr>
			        <tr>
			        <td align="right" height="41">摄制年度：</td>
			        <td width="120"><input id="shootingYear"  type="text"  class="input" name="corporationPhoto.shootingYear" size="30"/>
			        </td>
			        <td>
			        上传文件：
			        </td>
			         	 <td width="110" align="right">
			              <input name="corporationPhoto.photo" type="file" class="input"  size="15"  contentEditable="false"/>
                          <input name="corporationPhoto.PhotoFile" type="hidden" class="fileNum" id="PhotoFile"/></td>
                          </tr>
			        <tr>
			        <td align="right" height="41">备注：</td>
			        <td  colspan="3"><input id="remark"   type="text"  class="input" name="corporationPhoto.remark" style="width: 412px"/>	
			        <input name="corporationPhoto.corporationPhotoID" type="hidden" id="corporationPhotoID" size="20"/>
			        <input type="hidden" name="corporationPhoto.corporationPhotoKey" id="corporationPhotoKey" /></td>
			        </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			   
			    <div align="center">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value="保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor: pointer; width: 80px;" value="取消" />
                </div>
	</div>
	 <s:token></s:token>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
