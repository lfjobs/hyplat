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
        <title>个人客户调查-竟品资料</title>
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
        <script src="<%=basePath%>js/ea/marketing/crmcustomer/crmcustomer_04jpzl.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  crmCustCompetKey = '';
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
                        <th width="80" align="center">产品名称</th>
                        <th width="80" align="center">产品编号</th>
                        <th width="100" align="center">公司</th>
                        <th width="100" align="center">产地</th>
                        <th width="100" align="center">主销地区</th>
                        <th width="80" align="center">规格</th>
                        <th width="80" align="center">型号</th>
                        <th width="40" align="center">单价</th>
                        <th width="40" align="center">会员价</th>
                        <th width="40" align="center">VIP价</th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number" id="compet">
                        <tr id='<s:property value="#compet.competitivekey"/>'>
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value='<s:property value="#compet.competitivekey"/>'/>
                            </td>
                            <!-- 
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                             -->
                            <td>
                                <span id="pname"><s:property value="#compet.pname"/></span>
                            </td>
                            <td>
                               <span id="goodscoding"><s:property value="#compet.goodscoding"/></span>
                            </td>                            
                            <td>
                               <span id="companyname"><s:property value="#compet.companyname"/></span>
                            </td>
                            <td>
                                <span id="address"><s:property value="#compet.address"/></span>
                            </td>
                            <td>
                               <span id="saletoarea"><s:property value="#compet.saletoarea"/></span>
                            </td>                            
                            <td>
                               <span id="specification"><s:property value="#compet.specification"/></span>
                            </td>
                            <td>
                                <span id="model"><s:property value="#compet.model"/></span>
                            </td>
                            <td>
                               <span id="price"><s:property value="#compet.price"/></span>
                            </td>
                            <td>
                                <span id="cprice"><s:property value="#compet.cprice"/></span>
                            </td>
                            <td>
                                <span id="vprice"><s:property value="#compet.vprice"/></span>
                                <span id="crmCustCompetKey" style="display:none"><s:property value="#compet.competitivekey"/></span>                            
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/marketingCrmCustomer/ea_getListJpzl.jspa?crmCustomer.customerkey=${crmCustomer.customerkey}&pageNumber=${pageNumber}&search=${search}">
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
				    		<input name="dtCrmCustCompet.pname" id="" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>产品编号：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustCompet.goodscoding" id="" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>公司：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustCompet.companyname" id="" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>产地：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustCompet.address" id="" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>主销地区：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustCompet.saletoarea" id="" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>规格：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustCompet.specification" id="" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>型号：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustCompet.model" id="" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>单价：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustCompet.price" id="" class="put3"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="130" height="37" align="right">
				    		<font color="red"></font>会员价：
				    	</td>
				    	<td>
				    		<input name="dtCrmCustCompet.cprice" id="" class="put3"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		<font color="red"></font>VIP价：
				    	</td>
				    	<td> 
				    		 <input name="dtCrmCustCompet.vprice" id="" class="put3"/>
				    	</td>
				    </tr>
				    
				    <tr>
				    	<td colspan="4" align="center" >				    		
				            <input type="hidden" name="dtCrmCustCompet.competitivekey" id="competitivekey" />				            
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
                        	 <input name="dtCrmCustCompet.pname" id="pname" />
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