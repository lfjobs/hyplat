<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户成交服务</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/human/office/SersonnelSystem/MarketSurvey.js"></script>

<script  type="text/javascript">
   var 	marketSurveyID  = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
</script>
</head>
<body>
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
<input type="" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="40" align="center" >序号</th>
            <th width="100" align="center" >调查名称</th>
            <th width="100" align="center">调查形式 </th>
            <th width="100" align="center" >调查内容</th>
            <th width="100" align="center" >调查目标</th>   
            <th width="100" align="center" >调查区域</th>
            <th width="100" align="center">调查结果</th>
            <th width="100" align="center">调查评价</th>
            <th width="100" align="center" >调查预算</th>
            <th width="200" align="center" >备注</th>  
      </tr>
    </thead>
		<tbody>
		<%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${marketSurveyID}">
            <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${marketSurveyID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
			</td>
            <td class="td_bg01">
               <span id="surveyName" >${surveyName}</span>
			</td>
			<td class="td_bg01">
              <span id="surveyForms">${surveyForms}</span>
           	</td>
            <td class="td_bg01">
             <span id="surveyContent">${surveyContent}</span>
            </td>
            <td class="td_bg01">
             <span id="surveyTarget">${surveyTarget}</span>
            </td>
            <td class="td_bg01">
             <span id="surveyRegional">${surveyRegional}</span>
            </td>
            <td class="td_bg01">
             <span id="surveyResult">${surveyResult}</span>
            </td>
            <td class="td_bg01">
             <span id="surveyEvaluation">${surveyEvaluation}</span>
            </td>
             <td class="td_bg01">
             <span id="surveyBudget">${surveyBudget}</span>
            </td>
            <td class="td_bg01">
               <span id="note" >${note}</span>
               <span id="marketSurveyKey" style="display:none">${marketSurveyKey}</span>
			   <span id="marketSurveyID" style="display:none">${marketSurveyID}</span>
			</td>     
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/marketsurvey/ea_getListMarketSurvey.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
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
                           调查名称：
                        </td>
                        <td>   
                          <input   name="marketSurvey.surveyName" />      
                        </td>
                    </tr>
					<tr>
                        <td>
                           调查区域：
                        </td>
                        <td>
                           <input  name="marketSurvey.surveyRegional" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag">市场调查办
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="550" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="600" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100" height="37"  align="right">调查名称：</td>
			        <td width="148" ><input name="marketSurvey.surveyName" type="text" id="surveyName" size="20"/></td>
			        <td width="90"  align="right">调查形式：</td>
			        <td width="212" >
			         <input name="marketSurvey.surveyForms" type="text" id="surveyForms" size="20"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="100" height="37"  align="right">调查内容：</td>
			        <td width="148" ><input name="marketSurvey.surveyContent" type="text" id="surveyContent" size="20"/></td>
			        <td width="90"  align="right">调查目标：</td>
			        <td width="212" >
			         <input name="marketSurvey.surveyTarget" type="text" id="surveyTarget" size="20"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="100" height="37"  align="right">调查区域：</td>
			        <td width="148" ><input name="marketSurvey.surveyRegional" type="text" id="surveyRegional" size="20"/></td>
			        <td width="90"  align="right">调查结果：</td>
			        <td width="212" >
			         <input name="marketSurvey.surveyResult" type="text" id="surveyResult" size="20"/>
			        </td>
			      </tr>
			        <tr>
			        <td width="100" height="37"  align="right">调查评价：</td>
			        <td width="148" ><input name="marketSurvey.surveyEvaluation" type="text" id="surveyEvaluation" size="20"/></td>
			        <td width="90"  align="right">调查预算：</td>
			        <td width="212" >
			         <input name="marketSurvey.surveyBudget" type="text" id="surveyBudget" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td width="100" height="41"  align="right">备注：</td>
			        <td colspan="4">
			         <textarea rows="3" cols="48" id="note" class="input" name="marketSurvey.note"></textarea>
			        <input  type="hidden" name="marketSurvey.marketSurveyID"  id="marketSurveyID" size="20"/>
			        <input type="hidden" name="marketSurvey.marketSurveyKey" id="marketSurveyKey" /></td>
			        </tr>
			    </table>
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
