<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
		
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>小票打印</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
    <link href="<%=basePath%>css/ea/finance/StyleSheet.css" rel="stylesheet" />
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
   <script type="text/javascript">
   var monay=0;
   </script>
  </head>
  
  <body>
    <div>
        <div class="div_tit">
            <ul>
                <li style=" font-weight:50px">${cashierBillsVO.companyname}</li>
            </ul>
        </div>
        <div class="div_top">
            <ul>
                <li>制单人：</li>
                <li>${cashierBillsVO.inputName}</li>
            </ul>
            <ul>
                <li>时间：</li>
                <li>${fn:substring(cashierBillsVO.cashierDate,0, 10)}</li>
            </ul>
            <ul>
                <li>凭证号：</li>
                <li>${cashierBillsVO.journalNum}</li>
            </ul>
        </div>
        <div class="content">
            <table>
                <tr>
                    <td>品名</td>
                    <td>单价</td>
                    <td>数量</td>
                    <td>折扣</td>
                    <td>金额</td>
                </tr>
                <s:iterator value="goodList" var="num">
                <tr>
                    <td colspan="5" class="con">${goodsCoding} ${goodsName}</td>
                </tr>
                <tr>
                    <td></td>
                    <td>${price}</td>
                    <td>${quantity}</td>
                    <td>${rebate==null||rebate==""?10:rebate}折</td>
                    <td><span class="realMoney">${realMoney}</span></td>
                </tr>
                </s:iterator>
            </table>
        </div>
        <div class="settlement">
            <ul>
                <li class="sett">结算方式</li>
                <li class="appstyle">${appstyle==01?银行转帐:appstyle==02?银行支票转账:appstyle==03?支付宝转帐:appstyle==04?App转账:appstyle==04?POS机转账:现金转账 }</li>
            </ul>
            <ul>
                <li class="sett">现金</li>
                <li class="money"></li>
            </ul>
        </div>
        <div class="total settlement">
            <!-- <table>
                <tr>
                    <td class="txt">合计：</td>
                    <li class="money"></li>
                </tr>
                <tr>
                    <td class="txt">应付：</td>
                    <li class="money"></li>
                </tr>
                <tr>
                    <td class="txt">实收：</td>
                    <li class="money"></li>
                </tr>
                <tr>
                    <td class="txt">找零：</td>
                    <li class="money"></li>
                </tr>
            </table> -->
            <ul >
                <li class="sett">合计：</li>
                <li class="money">${b}</li>
            </ul>
            <ul>
                <li class="sett">应付：</li>
                <li class="money">${b}</li>
            </ul>
            <ul>
                <li class="sett">实收：</li>
                <li >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="ss" size="1px" style="border: 0px"/></li>
            </ul>
            <ul>
                <li class="sett">找零：</li>
                <li class="money"></li> 
            </ul>
        </div>
        <div class="bott">
            <table>
                <tr>
                    <td>服务电话：010-64167113</td>
                </tr>
                <tr>
                    <td>谢谢惠顾</td>
                </tr>
                <tr>
                    <td class="logo">
                        <img src="<%=basePath%>images/logo11.png" />5L5C</td>
                </tr>
            </table>
        </div>
    </div>
     <script type="text/javascript">
    	$(".realMoney").each(function(){
    		monay=monay+parseInt($(this).text());
    	});
    	$(".money").html(monay);
    </script>
  </body>
</html>
