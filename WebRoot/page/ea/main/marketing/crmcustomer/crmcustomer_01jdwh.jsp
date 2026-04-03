<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/"; %>
  <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>个人客户调查-进度维护</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/marketing/crmcustomer/crmcustomer_01jdwh.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  crmCustAttyKey = '';
         var  customerkey = '${crmCustomer.customerkey}';         
         var  token=0;
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">请选择</th>
                        <!-- <th width="40" align="center">序号</th> -->
                        <th width="80" align="center">活动日期</th>
                        <th width="80" align="center">主题</th>
                        <th width="100" align="center">客户阶段</th>
                        <th width="100" align="center">协同人</th>
                        <th width="200" align="center">携带产品资料</th>
                        <th width="100" align="center">跟踪方式</th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number" id="atty">
                        <tr id='<s:property value="#atty.activitykey"/>'>
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value='<s:property value="#atty.activitykey"/>'/>
                            </td>
                            <!-- 
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                             -->
                            <td>
                                <span id="activitydate"><s:property value="#atty.activitydate"/></span>
                            </td>
                            <td>
                               <span id="activitytitle"><s:property value="#atty.activitytitle"/></span>
                            </td>
                            <td>
                                <span id="status" style="display:none"><s:property value="#atty.status"/></span>
                                <span id="statusname"><s:if test="#atty.status==01">已成交</s:if><s:else>意向客户</s:else></span>
                            </td>
                            <td>
                               <span id=""></span>
                            </td>
                            <td>
                                <span id=""></span>
                            </td>
                            <td>
                                <span id=""></span>
                                <span id="crmCustAttyKey" style="display:none"><s:property value="#atty.activitykey"/></span>                            
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/marketingCrmCustomer/ea_getListJd.jspa?crmCustomer.customerkey=${crmCustomer.customerkey}&pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
 	<div class="contentbannb jqmWindow " style="top: 10%;width: 520px;right: 30%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <input type="text" name="crmCustomer.customerkey" id="customerkey"  value="${crmCustomer.customerkey}" style="display:none"/>
                <div class="drag">
                   	 详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="398" style="margin-left: 50px;" height="103"> 
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red">*</font>活动日期：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustAtty.activitydate" id="activitydate" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red">*</font>主题：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustAtty.activitytitle" id="activitytitle" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		客户阶段：
				    	</td>
				    	<td>
				    		<select name="dtCrmCustAtty.status">
                          		<option value="01">已成交</option>
                          		<option value="02">意向客户</option>
                        	</select>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		协同人：
				    	</td>
				    	<td>
				    		 <input name="xietong" id="xietong"  onclick=""/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		携带产品资料：
				    	</td>
				    	<td colspan="3">
				    		<label>
				    		<input type="radio" name="dtCrmCustAtty.ismanual" value="" />说明书
				    		<input type="radio" name="dtCrmCustAtty.isbrochure" value="" />宣传册
				    		<input type="radio" name="dtCrmCustAtty.isdemo" value="" />演示版本
				    		<input type="radio" name="dtCrmCustAtty.isother" value="" />其他
				    		<input name="dtCrmCustAtty.content1" id="content1" />
				    		</label>
				    		
			    	  </td>				    	
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		跟踪方式：
				    	</td>
				    	<td colspan="3">
				    		<label>
				    		<input type="radio" name="dtCrmCustAtty.issms" value="" />短信
				    		<input type="radio" name="dtCrmCustAtty.issms2" value="" />电话
				    		<input type="radio" name="dtCrmCustAtty.isweixin" value="" />微信互动
				    		<input type="radio" name="dtCrmCustAtty.isvisit" value="" />登门拜访
				    		<input type="radio" name="dtCrmCustAtty.ismeeting" value="" />会议
				    		<input type="radio" name="dtCrmCustAtty.isactivity" value="" />活动
				    		<input name="dtCrmCustAtty.content2" id="content2" />内容
				    		</label>
				    		
				    	</td>				    	
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		详细描述： 
				    	</td>
				    	<td colspan="3"><label>
				    	  <textarea name="textarea" cols="60" rows="3"></textarea>
				    	</label>				    	
				    	</td>				    	
				    </tr>
				    <tr>
				    	<td colspan="4" align="center" >				    		
				            <input type="hidden" name="dtCrmCustAtty.activitykey" id="activitykey" />				            
						    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
				    	</td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div> 
	
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 300px;right: 39%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                   	 查询条件
                    <div class="close">
                    </div>
            </div>
                <table width="260px" id="cataffSearchTable"> 
                    <tr>
                        <td align="right">
                            	主题:
						</td>
                        <td>
                        	 <input name="dtCrmCustAtty.activitytitle" id="activitytitle" />
                        </td>
                    </tr>
                </table>
            <div align="center"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>