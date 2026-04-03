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
<title>提醒消息</title>
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
<script src="<%=basePath%>js/ea/finance/remind.js"></script>
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
   var 	remindID = '';
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
            <th width="100" align="center" >访问链接类型</th>   
            <th width="100" align="center" >访问连接</th>
            <th width="100" align="center">接收人</th>
            <th width="150" align="center" >接收时间</th>
            <th width="100" align="center" >提醒方式</th>
            <th width="100" align="center" >提醒状态</th>
      </tr>
    </thead>
		<tbody>
		<%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${remindID}">
            <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${remindID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
			</td>
            <td class="td_bg01">
               <span id="circularType" ><c:if test="${circularType eq 01}">待阅</c:if><c:if test="${circularType eq 02}">待办</c:if></span>
			</td>
			<td class="td_bg01">
              <span id="circularTitle">${circularTitle}</span>
           	</td>
            <td class="td_bg01">
             <span id="circularText">${circularText}</span>
            </td>
            <td class="td_bg01">
             <span id="urlType">${urlType}</span>
            </td>
            <td class="td_bg01">
             <span id="detailedurl">${detailedurl}</span>
            </td>
            <td class="td_bg01">
             <span id="partnerName">${staffName}</span>
             <span id="partnerID" style="display:none">${staffID}</span>
            </td>
            <td class="td_bg01">     
             <span id="receiveDate">${fn:substring(receiveDate,0,19)}</span>
            </td>
            <td class="td_bg01">
             <span id="remindType"><c:if test="${remindType eq 01}">页面弹框</c:if><c:if test="${remindType eq 02}">电脑客户端</c:if><c:if test="${remindType eq 03}">手机客户端提醒</c:if></span>
            </td>
           <td class="td_bg01">
             <span id="remindStatus"><c:if test="${remindStatus eq 01}">未发送</c:if><c:if test="${remindStatus eq 02}">已发送</c:if><c:if test="${remindStatus eq 03}">发送失败</c:if></span>
             <span id="remindID" style="display:none">${remindID}</span>
             <span id="remindKey" style="display:none">${remindKey}</span>
            </td>    
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/remind/ea_getListRemind.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
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
                          <input   name="remind.circularTitle" />      
                        </td>
                    </tr>
					<tr>
                        <td>
                           接收人：
                        </td>
                        <td>
                           <input  name="remind.staffName" />
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
	        <div class="drag">消息提醒
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
			        <input type="hidden" name="circularType"  id="circularType"/>
			        <select name="remind.circularType"  id="circularType">
					<option value="01">待阅</option>
					<option value="02">待办</option>
					</select>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37"  align="right">标题：</td>
			        <td width="590" height="37" colspan="3">
			         <input name="remind.circularTitle" class="put3 ckTextLength" maxlength="80" type="text" id="circularTitle" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">内容：</td>
			        <td width="590" height="37" colspan="3">
			        <textarea name="remind.circularText" id="circularText" class="put3 ckTextLength" maxlength="800" style="width:500px; height:50px; border:1px solid #C5D9F1;resize: none;"></textarea>
                    </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">访问链接类型：</td>
			        <td width="250" height="37">
			        <select name="remind.urlType" id="urlType">
						<option value="列表">列表</option>
						<option value="主管审核">主管审核</option>
						<option value="出纳">出纳</option>
						<option value="财务">财务</option>
					</select>
			        </td>
			        <td width="100" height="37" align="right">访问连接：</td>
			        <td width="240" height="37">
			         <input name="remind.detailedurl" class="ckTextLength" maxlength="400" type="text" id="detailedurl" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">接收人：</td>
			        <td width="250" height="37">
			        <input type="hidden" id="partnerID" name="partnerID" readonly="readonly"/>
					<input type="text" id="partnerName" name="partnerName" class="put3" readonly="readonly" size="15" />
					<a href="#" class="see"
								onclick="importGY('stafftable','partnerID','partnerName','childPartnerName','ea/remind/ea_getStaffForCashier.jspa')">选择</a>
			        </td>
			        <td width="100" height="37" align="right">接收时间：</td>
			        <td width="240" height="37">
			        <input name="receiveDate" id="receiveDate" size="20" class="put3" onfocus="WdatePicker({minDate:'%y-%M-%d %H:#{%m+1}:%s',dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="110" height="37" align="right">提醒方式：</td>
			        <td width="250" height="37">
			        <input type="hidden" name="remindType"  id="remindType"/>
			        <select name="remind.remindType" id="remindType">
					<option value="01">页面弹框</option>
					<option value="02">电脑客户端</option>
					<option value="03">手机客户端提醒</option>
					</select>
                    </td>
                    <td width="100" height="37" align="right">&nbsp;</td>
			        <td width="240" height="37">
				    <input type="hidden" name="remindStatus"  id="remindStatus"/>
					<input type="hidden" name="remind.remindStatus" value="01"/>
					
					<input type="hidden" name="remind.remindID" id="remindID" />
					<input type="hidden" name="remind.remindKey" id="remindKey" />
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
	<%--选择责任人--%>
	<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
				<div class="bankJqmclose"></div>
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
	</div>
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
