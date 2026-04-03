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
<title>公司首页配置</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/ea/office_ea/ccomconf.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/common/common.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var ccompanyID= '${ccomConf.ccompanyId}';
   var certificateID = '';
   var basePath = '<%=basePath%>';
   var ppageNumber = ${pageNumber};
   
   var pserch ='${search}';
   var notoken=0;
   var times=0;
   
 </script>
</head>
<body>
<form  name="ccomconfForm" id="ccomconfForm" method="post"  enctype="multipart/form-data">
<input type="hidden" name="ccomConf.ccompanyId" value="${ccomConf.ccompanyId}"></input>
<input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table class="address">
  <thead>
	 	    <tr>
	 	    <th width="30" align="center">选择</th>
            <th width="100" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>模块名称</th>
            <th width="100" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>模块类型</th>
            <th width="150" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>详细说明</th>
            <th width="150" align="center" ><span style="color:#FF0000;margin-right:2px;">*(1-9之间)</span>序号</th>
            <th width="150" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>图片地址</th>   
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2 trclass" >
            <td>
             <input type="radio" name="a" class="JQuerypersonvalue" value="${ccomConfId}"/>
             </td>
            <td class="td_bg01"><input  name="modalName" id="modalName" class="aaaa"   size="10" /> </td>
            <td class="td_bg01"><select name="modalType" id="mT">
            					<option value="1">关于公司</option>
            					<c:if test="${account.company.companyIdentifier eq 'fljybj' }">
            					<option value="2">中联园区头部</option>
            					<option value="3">中联园区中部</option>
            					<option value="4">中联园区底部</option>
            					</c:if>
            					
            </select></td>
            <td class="td_bg01"><input  name="modalRemark" id="modalRemark" class="aaaa" size="250"/></td> 
            <td class="td_bg01"><input  name="sn" id="sn"  class="aaaa"size="1"/></td> 
            <td class="td_bg01"><input  name="photo" id="photo"  class="aaaa" size="5" type="file" contentEditable="false" size="10" />
            <input  name="certificateID" id="certificateID"/>
            </td> 
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${ccomConfId}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${ccomConfId}"/>
          </td>
          <td class="td_bg01">
                 <span id="modalName" class="modalName">${modalName}</span>
				 <input class="model1" value="${modalName}" name="modalName"  /></td>
          <td class="td_bg01">
          		
                <c:choose>
                <c:when test="${account.company.companyIdentifier eq 'fljybj' }">
                <c:if test="${modalType==0 }"><span class="modalType">公司简介</span></c:if>
                <c:if test="${modalType==1 }"><span class="modalType">关于公司</span></c:if>
                <c:if test="${modalType==2 }"><span class="modalType">中联园区头部</span></c:if>
                <c:if test="${modalType==3 }"><span class="modalType">中联园区中部</span></c:if>
                <c:if test="${modalType==4 }"><span class="modalType">中联园区底部</span></c:if>
	               <select id="modalType" name="modalType" class="model1">
	                	<option value="0" <c:if test="${modalType==0 }">selected="selected"</c:if>>公司简介</option>
	                	<option value="1" <c:if test="${modalType==1 }">selected="selected"</c:if>>关于公司</option>
	                	<option value="2" <c:if test="${modalType==2 }">selected="selected"</c:if>>中联园区头部</option>
	                	<option value="3" <c:if test="${modalType==3 }">selected="selected"</c:if>>中联园区中部</option>
	                	<option value="4" <c:if test="${modalType==4 }">selected="selected"</c:if>>中联园区底部</option>
	               	</select>
	                
                </c:when>
                <c:otherwise>
                	<c:if test="${modalType==0 }">公司简介</c:if>
                	<c:if test="${modalType==1 }">关于公司</c:if>
                </c:otherwise>
                </c:choose>
          <td class="td_bg01">
                <span id="modalRemark" class="modalRemark">${modalRemark}</span>
				<input class="model1" value="${modalRemark}" name="modalRemark" size="250"/>
				<input  type="hidden"  name="ccompanyID" id="ccompanyID" value="${ccompanyID}"/></td>
		  <td class="td_bg01">
                 <span id="sn" class="sn">${sn}</span>
				 <input class="model1" value="${sn}" name="sn"  /></td>
		  <td class="td_bg01">
				<span><s:if test="picPath==null||picPath==''">无</s:if></span>
                             <s:else>
                               <span id="picPath" onclick="lookImage('${picPath}')"><a href="javascript:void(0)">查看</a></span>
                            </s:else>
						    <input name="photo" type="file" class="model1" size="5" contentEditable="false"/>
						    <input name="picPath" type="hidden" value="${picPath}" class="model1"/>	
						    <input type="hidden" name="ccomConfId" value="${ccomConfId}"/>
						    <input type="hidden" name="ccomConfKey" value="${ccomConfKey}"/>
						    </td>	
						    
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/ccomconf/ea_getCconConfList.jspa?pageNumber=${pageNumber}&ccomConf.ccompanyId=${ccomConf.ccompanyId}"></c:param>
</c:import>
</div>
</form>
<!--搜索窗口 -->
        <div class="jqmWindow " style="width: 400px;right: 25%;top:10%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <div align="center">
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           模块名称
                        </td>
                        <td>
                           <input   name="ccomConf.modalName" />
                        </td>
                    </tr>
                </table>
                </div>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
      <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
      <script type="text/javascript">
    $(function(){   
    	setTimeout(function(){ 			
    	  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe2").offsetHeight-80+"px"});
    	    },100);
    	$(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe2").offsetHeight-80+"px"});
		      },100);
	 }); 	
});
</script> 
</body>
</html>