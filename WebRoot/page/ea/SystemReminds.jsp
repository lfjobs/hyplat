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
<title>系统提醒消息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/systemremind.js"></script>
<style>
div.bankJqmclose{
  position: absolute;
  right: 7px;
  top: 6px;
  padding: 0 0 0 13px;
  height: 19px;
  width: 0px;
  background: url(<%=basePath%>js/jqModal/css/images/close_icon.png) no-repeat top left;
  cursor: hand;
}
</style>
<script  type="text/javascript">
   var 	sremindID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
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
            <th width="100" align="center" >类型</th>
            <th width="100" align="center">标题</th>
            <th width="100" align="center" >内容</th>
            <th width="100" align="center">接收人分类</th>
            <th width="150" align="center" >接收时间</th>
            <th width="100" align="center" >提醒方式</th>
      </tr>
    </thead>
		<tbody>
		<%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${sremindID}">
            <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${sremindID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
			</td>
            <td class="td_bg01">
               <span id="scircularType" ><c:if test="${scircularType eq 01}">待阅</c:if><c:if test="${scircularType eq 02}">待办</c:if></span>
			</td>
			<td class="td_bg01">
              <span id="scircularTitle">${scircularTitle}</span>
           	</td>
            <td class="td_bg01">
             <span id="scircularText">${scircularText}</span>
            </td>
            <td class="td_bg01">
             <span id="sreceiveType"><c:if test="${sreceiveType eq 01}">在线</c:if><c:if test="${sreceiveType eq 02}">全部</c:if></span>
            </td>
            <td class="td_bg01">     
             <span id="sreceiveDate">${fn:substring(sreceiveDate,0,19)}</span>
            </td>
            <td class="td_bg01">
             <span id="sremindType"><c:if test="${sremindType eq 01}">页面弹框</c:if><c:if test="${sremindType eq 02}">电脑客户端</c:if><c:if test="${sremindType eq 03}">手机客户端提醒</c:if></span>
             <span id="sremindID" style="display:none">${sremindID}</span>
             <span id="sremindKey" style="display:none">${sremindKey}</span>
            </td>  
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
<c:import url="page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/systemremind/ea_getListRemind.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">条件查询
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           标题：
                        </td>
                        <td>   
                          <input   name="sremind.scircularTitle" />      
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " />
                    <input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--添加 修改 查看-->
		<div class="jqmWindow jqmWindowcss" style="top:10%;width:700px;" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
	        <div class="drag">系统消息提醒
		    <div class="close"></div>
		    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			     <table width="700" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="110" height="37" align="right">类型：</td>
			        <td width="590" height="37" colspan="3">
			        <input type="hidden" name="scircularType"  id="scircularType"/>
			        <select name="sremind.scircularType"  id="scircularType">
					<option value="01">待阅</option>
					<option value="02">待办</option>
					</select>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37"  align="right">标题：</td>
			        <td width="590" height="37" colspan="3">
			         <input name="sremind.scircularTitle" class="put3 ckTextLength" maxlength="80" type="text" id="scircularTitle" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">内容：</td>
			        <td width="590" height="37" colspan="3">
			        <textarea name="sremind.scircularText" id="scircularText" class="put3 ckTextLength" maxlength="800" style="width:400px; height:50px; border:1px solid #C5D9F1;resize: none;"></textarea>
                    </td>
			      </tr>
			      <tr>
			        <td width="150" height="37" align="right">接收人分类：</td>
			        <td width="150" height="37">
				        <table border="0" width="100px">
				        <tr>
				        <td width="10px"><input name="linetype" style="width:auto; margin-top:10px;" type="radio" checked="checked" value="zx"/></td>
				        <td width="30px"><span style="width:30px; line-height:50px; display:block;">在线</span></td>
				        <td width="20px"><input name="linetype" style="width:auto; margin-top:10px;" type="radio" value="qb"/></td>
				        <td width="40px"><span style="width:40px; line-height:50px; display:block;">全部</span></td>
				        </tr>
			            </table>
			        </td>
			        <td width="100" height="37" align="right">接收时间：</td>
			        <td width="300" height="37">
			        <input name="sreceiveDate" id="sreceiveDate" size="20" class="put3" onfocus="WdatePicker({minDate:'%y-%M-%d %H:#{%m+1}:%s',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">提醒方式：</td>
			        <td width="250" height="37">
			        <input type="hidden" name="sremindType"  id="sremindType"/>
			        <select name="sremind.sremindType" id="sremindType">
					<option value="01">页面弹框</option>
					<option value="02">电脑客户端</option>
					<option value="03">手机客户端提醒</option>
					</select>
                    </td>
                    <td width="100" height="37" align="right">&nbsp;</td>
			        <td width="240" height="37">
				    <input type="hidden" name="sremindStatus"  id="sremindStatus"/>
					<input type="hidden" name="sremind.sremindStatus" value="01"/>
					
					<input type="hidden" name="sremind.sremindID" id="sremindID" />
					<input type="hidden" name="sremind.sremindKey" id="sremindKey" />
			        </td>
			      </tr>
			    </table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value=" 保存 " />
                	<input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
