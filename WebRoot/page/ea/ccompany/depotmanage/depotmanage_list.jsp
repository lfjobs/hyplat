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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>库房信息</title>
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

<script type="text/javascript" src="<%=basePath%>js/ea/ccompany/depotmanage/depotmanage_list.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script type="text/javascript">
   var depotID = '';
   var basePath = '<%=basePath%>';
   var personurl='';
   var  depotName='';
	var pNumber=${pageNumber};
	var token=0;
	var depotPID = '${depotID}';
	var usetype='${param.usetype}';
	$(function(){
		$(".trPage").remove();
	})
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
                        <tr id="${depotID}"  class="trPage" >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${depotID}"/>
                            </td>
                            
                             <td>
                                <span id="depotCoding" style="as">${depotCoding}</span>
                            </td>
                               
                            <td>
                                <span id="depotName">${depotName}</span>
                            </td>
                             <td>
                                <span></span><s:select list="typelist" listKey="codeID"   listValue="codeValue" name="itemID"   theme="simple"></s:select> 
                            </td>
                            <td>
                                <span id="useState">${useState=='00'?'未启用':'已启用'}</span>
                            </td>
                            <td>
                                <span id="remark">${remark}</span>
                                 <span id="itemID" style="display:none">${itemID}</span>
                                  <span id="depotID" style="display:none">${depotID}</span>
                                  <span id="depotKey" style="display:none">${depotKey}</span>
                                   <span id="useState" style="display:none">${useState}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/cdepotmanage/ea_getListDepotManageTree.jspa?pageNumber=${pageNumber}&depotID=${depotID}&usetype=ck">
                </c:param>
            </c:import>
        </div>
        <iframe src="" name="main" marginwidth="0" scrolling="no" style="height:0;width:100%;" marginheight="0"  frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
         <form name="cstaffForm" id="cstaffForm" method="post"> 
         <input type="submit" name="submit" style="display:none"/>
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">     
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">库房信息
    <div class="close"></div>
    </div>
  </div>
   <table width="685" height="340" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;"><tr><td><table width="692" height="361" border="0" id="stafftable2" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
  <tr>
   
    <td align="right">仓库编码：</td>
     <td><input type="text" id="depotCoding" class="put3" name="depotManage.depotCoding"/></td>
     <td width="135" align="right">仓库名称：</td>
    <td width="227"><input name="depotManage.depotName" id="depotName" type="text" class="put3"  size="20"/></td>
  </tr>
  <tr>
    
     <td align="right">仓库类别：</td>
    <td><s:select list="typelist" listKey="codeID" id="itemID"  listValue="codeValue" name="depotManage.itemID"   theme="simple"></s:select> 
        <a href="#" onclick="toCCode('scode20101014v5zed7cukk0000000004','#itemID','#cstaffForm')">新添</a>
    </td>
    <td width="152" align="right">使用状态：</td>
   <td width="227"><select name="depotManage.useState" id="useState">
     <option value="00">未启用</option>
    <option value="01">已启用</option>
   </select>
   </td>
    </tr>
  <tr>
    <td align="right"> 备注：</td>
   <td width="227"><input name="depotManage.remark" id="remark" type="text" class="input"  size="20"/></td> 
    </tr>
  <tr>
    <td colspan="5" align="center"><input name="depotManage.depotKey" id="depotKey" type="hidden" class="input"  size="20"/>
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
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
        
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 30 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			$("#mainframe").css({"height": _height / 2 - 30 + "px"});
		    }else{		    
			$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>  
    </body>
</html>