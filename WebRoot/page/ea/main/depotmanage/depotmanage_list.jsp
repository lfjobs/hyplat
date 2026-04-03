<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cs" uri="http://java.sun.com/jstl/core_rt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库房信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>

<script type="text/javascript" src="<%=basePath%>js/ea/depotmanage/depotmanage_list.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript">
   var depotID = '';
   var basePath = '<%=basePath%>';
   var personurl='';
   var  depotName='';
	var pNumber=${pageNumber};
	var token=0;
	var depotPID = '${depotID}';
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
</head>
<body>
<form method="post" name="sortchildren" id="sortchildren">
<input type="submit" name="submit" style="display: none" />
		<input id="oID" name="depotID" type="hidden"/>
</form>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                           请选择
                        </th>
                        <th width="200" align="center">
                          仓库编码
                        </th>
                        <th width="200" align="center">
                          仓库名称
                        </th>
                         <th width="200" align="center">
                          仓库类别
                        </th>
                        <th width="200" align="center">
                            类型
                        </th>
                        <th width="200" align="center">
                          使用状态
                        </th>
                        <th width="200" align="center">
                           备注
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${depotID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${depotID}"/>
                            </td>
                            
                             <td>
                                <span id="depotCoding" style="as">${depotCoding}</span>
                            </td>
                               
                            <td>
                                <span id="depotName" style="">${depotName}</span>
                            </td>
                             <td>
                                <span></span><s:select list="typelist" listKey="codeID"   listValue="codeValue" name="itemID"   theme="simple"></s:select> 
                            </td>
                            <td>
                                <span>${depotType=='1'?'库房':depotType=='2'?'区域':depotType=='3'?'货架':'展位'}</span>
                            </td>
                            <td>
                                <span>${useState=='00'?'未启用':'已启用'}</span>
                            </td>
                            <td>
                                <span id="remark">${remark}</span>
                                 <span id="itemID" style="display:none">${itemID}</span>
                                  <span id="depotID" style="display:none">${depotID}</span>
                                  <span id="depotKey" style="display:none">${depotKey}</span>
                                   <span id="useState" style="display:none">${useState}</span>
                                    <span id="depotType" style="display:none">${depotType}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/depotmanage/ea_getListDepotManageTree.jspa?pageNumber=${pageNumber}&depotID=${depotID}">
                </c:param>
            </c:import>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
         <div style="300px;overflow: ">
                <iframe src="" name="main" style="height:0;whdth:100%;" marginwidth="0" scrolling="no" marginheight="0"  frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
         <form name="cstaffForm" id="cstaffForm" method="post"> 
         <input type="submit" name="submit" style="display:none"/>
        <div class="contentbannb jqmWindow jqmWindowcss3" style="top: 10%" id="jqModel">     
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">库房信息
    <div class="close"></div>
    </div>
  </div>
   <table width="485"  border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;"><tr><td><table width="492" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
  <tr>
   
    <td width="120" height="35" align="right">仓库编码：</td>
     <td><input type="text" id="depotCoding" class="put3" name="depotManage.depotCoding"/></td>
    
  </tr>
  <tr>
   <td height="35" align="right">仓库名称：</td>
    <td><input name="depotManage.depotName" id="depotName" type="text" class="put3"  size="20"/></td>
  </tr>
  <tr>
    
     <td height="35" align="right">仓库类别：</td>
    <td><s:select list="typelist" listKey="codeID" id="itemID"  listValue="codeValue" name="depotManage.itemID"   theme="simple"></s:select> 
        <a href="#" onclick="toCCode('scode20101014v5zed7cukk0000000004','#itemID','#cstaffForm')">新添</a>
    </td>
  
    </tr>
       <tr>
           <td height="35" align="right">类型：</td>
           <td><select name="depotManage.depotType" id="depotType">
               <option value="1" selected>库房</option>
               <option value="2">区域</option>
               <option value="3">货架</option>
               <option value="4">展位</option>
           </select>
           </td>
       </tr>
    <tr>
      <td height="35" align="right">使用状态：</td>
   <td><select name="depotManage.useState" id="useState">
     <option value="00">未启用</option>
    <option value="01">已启用</option>
   </select>
   </td>
    </tr>
  <tr>
    <td height="35" align="right"> 备注：</td>
   <td><input name="depotManage.remark" id="remark" type="text" maxlength="255" class="input ckTextLength"  size="20"/></td> 
    </tr>
  <tr>
    <td height="40" colspan="2" align="center"><input name="depotManage.depotKey" id="depotKey" type="hidden" class="input"  size="20"/>
        <input name="depotManage.depotID" id="depotID" type="hidden" class="input"  size="20"/>
        <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交"/>
        <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /></td>
  </tr>
</table></td>
        </tr>
</table>
</div>
        </div><s:token></s:token>
            </form>
                               <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%" id="newccode">
                                            <div class="drag">
                                                添加
                                            </div>
                                            <table>
                                                <tr>
                                                    <td >
                                                         代码名字：
                                                    </td>
                                                    <td >
                                                        <input id="ccodevalue" />
                                                        <input id="codePID" type="hidden"/>
                                                        <input id="selectID" type="hidden"/>
                                                        <input id="formID" type="hidden"/>
                                                    </td>
                                                </tr>
                                            </table>
                                            <div align="center">
                                               <input type="button" class="input-button" onclick="saveCCode()" value="确定"/><input type="button" class="input-button JQueryreturn1" value="取消" />
                                            </div>
                                    </div>
        <script type="text/javascript">
		    setTimeout(function(){   
			    $(".bDiv").css({"height":( $(window).height() - 31 - 30 - 26 - 55 ) + "px"});
		    },100);
		    
		    $(window).resize(function(){ 
				setTimeout(function(){ 					    
				    $(".bDiv").css({"height":( $(window).height() - 31 - 30 - 26 - 55 ) + "px"});
				},100);
	        }); 
        </script>        
    </body>
</html>