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
<title>单位证书管理</title>
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
<script type="text/javascript" src="<%=basePath %>/js/ea/office_ea/certificate.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var ccompanyID= '${certificate.ccompanyID}';
   var certificateID = '';
   var basePath = '<%=basePath%>';
   var ppageNumber = ${pageNumber};
   var pserch ='${search}';
   var notoken=0;
   var times=0;
   
 </script>

</head>
<body>
<form  name="certificateForm" id="certificateForm" method="post"  enctype="multipart/form-data">
<input type="hidden" name="certificate.ccompanyID" value="${certificate.ccompanyID}"></input>
<s:token></s:token><input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table class="address">
  <thead>
	 	    <tr>
	 	    <th width="30" align="center">选择</th>
            <th width="100" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>证书编号</th>
            <th width="100" align="center" >证书类型</th>
            <th width="100" align="center" ><span style="color:#FF0000;margin-right:2px;">*</span>证书名称</th>
            <th width="70" align="center" >发证时间</th>
            <th width="70" align="center" >有效期</th>
			<th width="100" align="center" >发证部门</th>
			<th width="150" align="center" >证书附件</th>
			<th width="100" align="center" >存放地点</th>
			<th width="60" align="center" >责任人</th>
			<th width="70" align="center" >证书副本数</th>
			<th width="300" align="center" >备注</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2 trclass" >
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" value="${certificateID}"/>
             </td>
            <td class="td_bg01"><input   name=certificateCode id="certificateCode" class="aaaa" size="10"/></td>
            <td class="td_bg01"> <s:select list="cccList" listKey="codeValue" listValue="codeValue" 
			name="certificateType" id="xxx" theme="simple"></s:select></td>
            <td class="td_bg01"><input  name="certificateName" id="certificateName" class="aaaa"   size="10" /> </td>
            <td class="td_bg01"><input  name="certificateTiime" id="certificateTiime"  onfocus="date(this);" size="10" class="ss"></td>
			<td class="td_bg01"><input  name="availabilityDate" id="availabilityDate" onfocus="date(this);" size="10" class="ss"></td>
            <td class="td_bg01"><input  name="certificateDepth" id="certificateDepth" size="10"/></td> 
            <td class="td_bg01"><input  name="photo" id="photo" size="5" type="file" contentEditable="false" size="10" /></td> 
            <td class="td_bg01"><input  name="certificateLocation" id="certificateLocation" size="10"/></td> 
            <td class="td_bg01"><input  name="responsible" id="responsible" size="10"/></td> 
            <td class="td_bg01"><input  name="certificateCopyNumber" id="certificateCopyNumber" size="10"/></td> 
            <td class="td_bg01"><input  name="remark" id="remark"/><input  name="ccompanyID" id="ccompanyID" value="${certificate.ccompanyID}"/>
            	<input  name="certificateID" id="certificateID"/>
            </td> 
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${certificateID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${certificateID}"/>
            </td>
            <td class="td_bg01">
                <span id="certificateCode" class="certificateCode">${certificateCode}</span>
				<input class="model1" value="${certificateCode}" name="certificateCode" size="10"/></td>
            <td class="td_bg01">
            	 <span  id="certificateType" class="certificateType">${certificateType}</span>
				 <s:select list="cccList" listKey="codeValue" listValue="codeValue" disabled="true"
				 name="certificateType" id="certificateType" theme="simple" style="display:none"></s:select>
				</td>
            <td class="td_bg01">
                 <span id="certificateName" class="certificateName">${certificateName}</span>
				 <input class="model1" value="${certificateName}" name="certificateName"  size="10"/></td>
            <td class="td_bg01">
             <span id="certificateTiime">${fn:substring(certificateTiime, 0, 10)}</span>
            	<input class="model1" name="certificateTiime" value="${certificateTiime}" onfocus="date(this);" size="10"></td>
			 <td class="td_bg01">
             <span id="availabilityDate">${fn:substring(availabilityDate, 0, 10)}</span>
            	<input class="model1"  name="availabilityDate" value="${availabilityDate}" onfocus="date(this);" size="10"></td>
            <td class="td_bg01">
                <span id="certificateDepth" class="certificateDepth">${certificateDepth}</span>
				<input class="model1" value="${certificateDepth}" name="certificateDepth" size="10"/></td>
            <td class="td_bg01">
				<span><s:if test="certificateAccessory==null||certificateAccessory==''">无</s:if></span>
                             <s:else>
                               <span id="certificateAccessory" onclick="lookImage('${certificateAccessory}')"><a href="javascript:void(0)">查看</a></span>
                            </s:else>
						    <input name="photo" type="file" class="model1" size="5" contentEditable="false"/>
						    <input name="certificateAccessory" type="hidden" value="${certificateAccessory}" class="model1"/>
            <td class="td_bg01">
                <span id="certificateDepth" class="certificateLocation">${certificateLocation}</span>
				<input class="model1" value="${certificateLocation}" name="certificateLocation" size="10"/></td>
            <td class="td_bg01">
                <span id="responsible" class="responsible">${responsible}</span>
				<input class="model1" value="${responsible}" name="responsible" size="10"/></td>
            <td class="td_bg01">
                <span id="certificateCopyNumber" class="certificateCopyNumber">${certificateCopyNumber}</span>
				<input class="model1" value="${certificateCopyNumber}" name="certificateCopyNumber" size="10"/></td>
            <td class="td_bg01">
                <span id="remark" class="remark">${remark}</span>
				<input class="model1" value="${remark}" name="remark" />
				<input  type="hidden"  name="ccompanyID" id="ccompanyID" value="${ccompanyID}"/>
				<input type="hidden" name="certificatkey" value="${certificatkey}"/>
				<input type="hidden" name="certificateID" value="${certificateID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/certificate/ea_getaCertificateList.jspa?pageNumber=${pageNumber}&certificate.ccompanyID=${certificate.ccompanyID}&search=${search}"></c:param>
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
                           证件编码
                        </td>
                        <td>
                           <input   name="certificate.certificateCode" />
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
