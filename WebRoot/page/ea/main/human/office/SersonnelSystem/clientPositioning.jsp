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
<title>产品定位展示</title>
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
<script src="<%=basePath%>js/ea/human/office/SersonnelSystem/clientPositioning.js"></script>
<script  type="text/javascript">
   var  clientPositioningID = '';
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
	 	    <th width="40" align="center">选择</th>
            <th width="40" align="center" >序号</th>
            <th width="150" align="center">客户编号</th>
            <th width="150" align="center">客户名称</th>
            <th width="150" align="center" >品牌</th>
            <th width="150" align="center" >等级</th>
            <th width="150" align="center" >型号</th>
            <th width="150" align="center" >品牌规格</th>
            <th width="150" align="center" >单位</th>
            <th width="150" align="center" >重量</th>
            <th width="150" align="center" >箱规格</th>
            <th width="150" align="center" >附件</th>
            
      </tr>
    </thead>
		<tbody>
		 <%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${clientPositioningID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${clientPositioningID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
				</td>
            <td class="td_bg01">
               <span id="clientCode" class="clientCode">${clientCode}</span>
				</td>
			 <td class="td_bg01">
             <span id="clientName">${clientName} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="brand">${brand}</span>
            </td>
             <td class="td_bg01">
             <span id="grade">${grade}</span>
            </td>
            
            <td class="td_bg01">
             <span id="model">${model}</span>
            </td>
            <td class="td_bg01">
             <span id="brandSpecification">${brandSpecification}</span>
            </td>
            <td class="td_bg01">
             <span id="unit">${unit}</span>
            </td>
            <td class="td_bg01">
             <span id="weight">${weight}</span>
            </td>
            <td class="td_bg01">
             <span id="caseSpecification">${caseSpecification}</span>
            </td>
            <td class="td_bg01">
              <span id="PhotoFile" style="display:none">${PhotoFile}</span>
              <s:if test="PhotoFile==null||PhotoFile==''">无</s:if>
                             <s:else>
                                <span id="Photo"   onclick="lookImage('${PhotoFile}');"><a href="#">查看</a></span>
                            </s:else>
             <span id="clientPositioningID" style="display:none">${clientPositioningID}</span>
		     <span id="clientPositioningKey" style="display:none">${clientPositioningKey}</span>
                            
            </td>
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea_getClientPositioningList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
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
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                           客户编码：
                        </td>
                        <td>         
                           <input   name="clientPositioning.clientCode" />
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
		<div class="jqmWindow jqmWindowcss" style="top: 10%;width: 660px"  id="jqModel">
			       <div class="drag">产品定位展示
				    	<div class="close"></div>
				   </div>
			       <div class="content">
			       <div class="contentbannb">
			       </div>
			 <table width="650" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			        <td  align="right">客户编号：</td>
			        <td  ><input name="clientPositioning.clientCode" type="text" id="clientCode" size="20" /></td>
			        <td  align="right">客户名称：</td>
			        <td  ><input name="clientPositioning.clientName" type="text" id="clientName" size="20"/>
			        </td>
			        <td  rowspan="3" align="center"><img id="pic" width="99" height="135"  /></td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">品牌：</td>
			        <td ><input  id="brand" type="text"  class="input"  name="clientPositioning.brand" size="20"/></td>
			        <td align="right" height="41">等级：</td>
			        <td ><input id="grade"   type="text"  class="input" name="clientPositioning.grade" size="30"/>
			      </tr>
			     <tr>
			        <td height="41"  align="right">型号：</td>
			        <td ><input  id="model" type="text"  class="input"  name="clientPositioning.model" size="20"/></td>
			         <td height="41"  align="right">品牌规格：</td>
			        <td ><input  id="brandSpecification" type="text"  class="input"  name="clientPositioning.brandSpecification" size="20"/></td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">单位：</td>
			        <td ><input  id="unit" type="text"  class="input"  name="clientPositioning.unit" size="20"/></td>
			        <td height="41"  align="right">重量：</td>
			        <td ><input  id="weight" type="text"  class="input"  name="clientPositioning.weight" size="20"/></td>
			        <td  align="center">
			              <input name="clientPositioning.photo" type="file" class="input"  size="10"  contentEditable="false"/>
                          <input name="clientPositioning.PhotoFile" type="hidden" class="fileNum" id="PhotoFile"/></td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">箱规格：</td>
			        <td >
			        <input  id="caseSpecification" type="text"  class="input"  name="clientPositioning.caseSpecification" size="20"/>
			        <input type="hidden" name="clientPositioning.clientPositioningID"  id="clientPositioningID" size="20"/>
			        <input type="hidden" name="clientPositioning.clientPositioningKey" id="clientPositioningKey" />
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
