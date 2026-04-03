<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>兑换账单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">	
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/Chart.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/slideUpDownRefresh_files/iscroll.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/bootstrap-theme.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/slideUpDownRefresh_files/iscroll.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/contacts/style12.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />	
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/Gold_Pool/wfj_jinbiBill.css" />
	<script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_jinbiBill.js"></script>
		
	<script type="text/javascript">
		var khd="${khd}";
		var year="${year}";
		var month="${month}";
		var basePath="<%=basePath%>";
    	var user="${user}";
    	var sccid="${sccid}";
    	var pagenumber="0";
    	var pagecount="0";
    	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
    	var t;
		var pagenumber2="0";
		var pagecount2="0";
		var tt;
		var dc="${dc}";
    	var flag = "${flag}";
    	var ccompanyId="${ccompanyId}";
    	var object = new Array();    	   	
    	var mths = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"];  
		var WEEKs =["周日", "周一", "周二", "周三", "周四", "周五","周六"];  
		var WEKs = [ "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" ];
		var  xjtx = "${xjtx}";
		var  jdetail= "${jdetail}";
		var  xjtx2 = "";
		var  jdetail2 = "";
	</script>
</head>

<body>
    
	<div class="wfj01_019">
    	
    	<s:if test="khd==0">
	    	<!--中联园区头部-->
	    	<div class="wfj_top">
	        	<ul>
	            	<li><a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid}&flag=${flag}&khd=0&identifying=${identifying}&ccompanyId=${ccompanyId}" target="_self"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_return_01.png" id="head_img"/></a></li>
	            	<li>兑换账单</li>
	            	<li><a href="javascript:;"><img src="<%=basePath %>images/ea/finance/BenDis/top_more.png" id="head_img2"/></a></li>
	            </ul>
	        </div>
    	<!--中联园区头部 end-->
    	</s:if>
        <input type="text" id = "d2"  style="display:none;"/>       
                
        <div class="wfj01_019_top">
        	<div class="wfj01_019_top_date" onclick="WdatePicker({el:'d2',skin:'default',dateFmt:'yyyy-MM',onpicked:pickedFunc})">
        	   
            	<ul>
                	<li ><span id="year">${year }</span>年</li>
                	<li id="dateimg"><font><span id="month">${month }</span></font>月<img src="<%=basePath %>images/ea/finance/BenDis/wfj_more_date_01.png"/></li>
                </ul>
            </div>
            <div class="wfj01_019_top_expend">
            	<ul>
                	<li>兑换</li>
                	<li class="dh"><fmt:formatNumber value="${xjtx==null?0:xjtx}" groupingUsed="true"/></li>
                </ul>
            </div>
            <div class="wfj01_019_top_income">
            	<ul>
                	<li>收益汇总</li>
                	<li class="sy"><fmt:formatNumber value="${jdetail==null?0:jdetail}" groupingUsed="true"/></li>
                </ul>
            </div>
        </div>
        
        
        
        
        <div class="wfj01_019_top_title">
            <ul class="wfj01_019_top_link left">
                <li id="title1"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_bill_01.png" /><c:if test="${dc eq '1'||dc eq null}">账单明细</c:if><c:if test="${dc eq '2'}">基本账户明细</c:if></li>
            </ul>
            <ul class="wfj01_019_top_link right">
                <li  id="title2"><img src="<%=basePath %>images/ea/finance/BenDis/wfj_gold_bill_02.png" /><c:if test="${dc eq '1'||dc eq null}">类别报表</c:if><c:if test="${dc eq '2'}">微信商户明细</c:if></li>
            </ul>
        </div>
     
        <div class="wfj01_019_content">
        
        
        
        	<div class="wfj01_019_hidden">
                <div class="wfj01_019_con">
               	
                    <div class="wfj01_019_con_bill">                           					
                        <div class="content" id="jbtab"></div>
                    </div>
					<c:if test="${dc eq '2'}">
					<div class="wfj01_019_con_type">
						<div class="content" id="jbtab2"></div>
					</div>
				   </c:if>

					<c:if test="${dc eq '1'}">
                    <div class="wfj01_019_con_type">
                        <table>
                            <tr>
                                <td>库存金币</td><%-- ${jifen.wfjJifenScore } --%>
                                <td><input class="allgold" type="text" disabled="disabled"/></td>
                            </tr>
							<tr>
                                <td>已兑换金币</td>
                                <td><input class="othergold" type="text" disabled="disabled"/></td>
                            </tr>
                        </table>
                    	<div class="wfj01_019_con_type_width">
                        	<canvas id="myChart" width="100%" height="100%"></canvas>
                        	<div class="wfj01_019_con_type_text">
								<p>库存金币数</p>
								<p><span class="span1">${jifen.wfjJifenScore }</span></p>
								<p>已兑换金币数</p>
								<p><span class="span2">${xjtx}</span></p>
							</div>
                        </div>
                    </div>
					</c:if>
                </div>
            </div>
          
        </div>
        </div>             
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
</body>
</html>
