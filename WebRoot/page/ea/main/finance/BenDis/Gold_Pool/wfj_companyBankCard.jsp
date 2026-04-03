<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/gold_pool.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>	
    <script src="<%=basePath%>/js/ea/finance/Gold_Pool/zepto.min.js"></script>    
    <script src="<%=basePath%>/js/ea/finance/Gold_Pool/event.min.js"></script>    
    <script src="<%=basePath%>/js/ea/finance/Gold_Pool/touch.min.js"></script>   
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_companyBankCard.js"></script>	       
    <title>公司银行卡管理</title>
</head>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var ccompanyId="${ccompanyId}";		
		var sccid="${sccid}";			
		var khd="${khd}";//去除表头
    	var user="${user}";
    	var staffid="${staffid}";
    	var flag = "${flag}"; //判断   移动办公    个人中心进入
    	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）       	
    	var staffName = "${staff.staffName}";
    	var mark = "${mark}";
    	var object = new Array();				
	</script>

<body>  

   	<s:if test="khd==0">	     			
	    <header class="com_head">   
	    	<s:if test="mark=='00'">
	    	     <a href="<%=basePath%>/ea/jinbi/ea_getwfjtixian.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffId}&mark=${mark}&bankId=${bankId}&identifying=${identifying}&ccompanyId=${ccompanyId}" class="back"></a> 	
	    	</s:if>
	    	<s:elseif test="mark=='01'">
	    	    <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}&identifying=${identifying}&flag=${flag}&mark=${mark}&ccompanyId=${ccompanyId}" class="back"></a>    	   	   	
	    	</s:elseif>    	
	    	<s:else>
	    	   <a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa" class="back"></a>      				       			       				
	    	</s:else>             
	       <h1>公司银行卡</h1>
	    </header>
    </s:if>
    <s:else>
    	<style type='text/css'>
			.wrap_page{
				margin-top:0;
				padding-top:0;
			}
		</style>  
    </s:else>
    
    <div class="wrap_page">
        <div class="bank_wrap">
        	<c:forEach items="${BankCardList }" var="a">
	            <div class="bank_btn_wrap" id="${a.bankAccountID}">        
	                <div class="bank_box clearfix">	                                    
	                    <!-- 显示对应银行的log -->
	                    <c:if test="${a.bankName == '中国工商银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/gs.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国建设银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/js.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zg.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国农业银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/ny.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国交通银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/jt.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '招商银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zs.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '浦发银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/pf.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中信银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zx.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国光大银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/gd.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '华夏银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/hx.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国民生银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/ms.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '兴业银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/xy.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '平安银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/pa.png" class="bank_ico" alt="">
	                    </c:if>	                                   	                    
	                    <div class="bank_text">
	                        <div class="bank_name">${a.bankName}</div>
	                        <div class="bank_n">${a.bankAccount}</div>
	                    </div>
	                </div>          
	                <a href="###" class="relieve_btn"><input type="hidden" id="bankId" value="${a.bankAccountID}">解除绑定</a>
	            </div>
            </c:forEach>                    
        </div> 
        
        <a href="<%=basePath %>ea/industry/ea_getaddBankCardInformation.jspa?ccompanyId=${ccompanyId}&user=${user}&sccid=${sccid}&staffid=${staffid}&mark=${mark}&identifying=${identifying}&flag=${flag}&khd=${khd}&bankId=${bankId}" class="add_card">
            <i class="addcard_ico"></i>
            <span>添加新的银行卡</span>
        </a>
    </div>
</body>  
    <script>
        /*左划事件  */
        Zepto(".bank_box").swipeLeft(function(){
            $(this).addClass("bank_box_L").parent().siblings().find(".bank_box").removeClass("bank_box_L")
        });
        Zepto(".bank_box").swipeRight(function(){
            $(this).removeClass("bank_box_L")
        })

    </script>  
</html>
