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
        <script src="<%=basePath%>js/ea/marketing/crmcustomer/crmcustomer_03bjjl.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  crmCustOfferKey = '';
         var  customerkey = '${crmCustomer.customerkey}';         
         var  token=0;
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="20" align="center">请选择</th>
                        <!-- <th width="40" align="center">序号</th> -->
                        <th width="40" align="center">产品名称</th>
                        <th width="40" align="center">产品编号</th>
                        <th width="40" align="center">产品版本</th>
                        <th width="40" align="center">产品价格</th>
                        <th width="40" align="center">报价次数</th>
                        <th width="40" align="center">报价金额</th>
                        <th width="40" align="center">折扣比率</th>
                        <th width=40"" align="center">差价</th>
                        <th width="60" align="center">报价人</th>
                        <th width="80" align="center">报价日期</th>
                        <th width="60" align="center">报价客户</th>
                        <th width="60" align="center">客户职务</th>
                        <th width="60" align="center">承诺服务</th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number" id="offer">
                        <tr id='<s:property value="#offer.offerkey"/>'>
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value='<s:property value="#offer.offerkey"/>'/>
                            </td>
                            <!-- 
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                             -->
                            <td>
                                <span id="pname"><s:property value="#offer.pname"/></span>
                            </td>
                            <td>
                                <span id="goodscoding"><s:property value="#offer.goodscoding"/></span>
                            </td>
                            <td>
                                <span id="goodsversion"><s:property value="#offer.goodsversion"/></span>
                            </td>
                            <td>
                                <span id="goodsprice"><s:property value="#offer.goodsprice"/></span>
                            </td>
                            <td>
                                <span id="pname"><s:property value="#offer.pname"/></span>
                            </td>
                            <td>
                                <span id="offerprice"><s:property value="#offer.offerprice"/></span>
                            </td>
                            <td>
                                <span id="discount"><s:property value="#offer.discount"/></span>
                            </td>
                            <td>
                                <span id="gapprice"><s:property value="#offer.gapprice"/></span>
                            </td>
                            <td>
                                <span id="staffname"><s:property value="#offer.staffname"/></span>
                            </td>
                            <td>
                                <span id="offerdate"><s:property value="#offer.offerdate"/></span>
                            </td>
                            <td>
                                <span id="offercustomerkey"><s:property value="#offer.offercustomerkey"/></span>
                            </td>
                            <td>
                                <span id="offercustomertitle"><s:property value="#offer.offercustomertitle"/></span>
                            </td>
                            <td>
                                <span id="pname"><s:property value="#offer.pname"/></span>
                                <span id="crmCustOfferKey" style="display:none"><s:property value="#offer.offerkey"/></span>
                            </td>  
                            
                            
                             
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/marketingCrmCustomer/ea_getListBjjl.jspa?crmCustomer.customerkey=${crmCustomer.customerkey}&pageNumber=${pageNumber}&search=${search}">
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
				    		<font color="red"></font>产品名称：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustOffer.pname" id="pname" class="put3"/><input name="dtCrmCustOffer.ppkey" id="ppkey" type="hidden"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>产品编号：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustOffer.goodscoding" id="goodscoding" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>产品版本：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustOffer.goodsversion" id="goodsversion" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>产品价格：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustOffer.goodsprice" id="goodsprice" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>报价金额：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustOffer.offerprice" id="offerprice" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>折扣比率：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustOffer.discount" id="discount" class="put3"/>
				    	</td>
				    </tr>				    
				    <tr>
				    	<td width="130" height="37" align="right">
				    		详细描述： 
				    	</td>
				    	<td colspan="3"><label>
				    	  <textarea name="textarea" cols="60" rows="5"></textarea>
				    	</label>				    	
				    	</td>				    	
				    </tr>
				    <tr>
				    	<td colspan="4" align="center" >				    		
				            <input type="hidden" name="dtCrmCustOffer.offerkey" id="offerkey" />				            
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
                            	产品名称:
						</td>
                        <td>
                        	 <input name="dtCrmCustOffer.pname" id="pname" />
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