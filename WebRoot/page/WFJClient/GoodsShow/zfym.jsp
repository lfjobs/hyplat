<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微分金产品支付</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
 <link href="<%=basePath%>css/WFJClient/style11.css" rel="stylesheet" type="text/css"/>
 <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
<body class="bgcolorFFF">
	<div class="wfj11_002">
	   <form  method="post" id="formsutm">
	   <input id="submit"  type="submit" style="display: none;"> 
    	<div class="wfj11_002_top">
            <ul>
            	<li style="width:15%;"><img src="<%=basePath%>/images/WFJClient/fh.png"></img></li>
            	<li style="width:70%;">${goodname}（在线支付）</li>
            </ul>
        </div>
        <div class="wfj11_002_product">
        	<div class="wfj_phwidth">
                <table>
                	<tr>
                    	<td width="45%" rowspan="2"><img src="${page}" /></td>
                    	<td width="55%"><a href="javascript:;">${goodname}</a></td>
                    </tr>
                	<tr>
                    	<td><a href="javascript:;">￥${morre}</a></td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="wfj11_002_pay">
        	<table>
            	<tr>
                	<td width="40%" align="center" style="color:#000; font-size:14px; font-weight:bold;">支付方式</td>
                	<td width="60%"></td>
                </tr>
            	<tr>
                	<td></td>
                	<td><input  id="aaa1" type="radio"  name="code"  value="1"   checked="checked"/> <img src="<%=basePath%>/images/WFJClient/zfby.png"></img></td>
                </tr>
            	<%-- <tr>
                	<td></td>
                	<td>  <input id="aaa2" type="radio" name="code"  value="2" /> <img src="<%=basePath%>/images/WFJClient/yl.png"></img></td>
                </tr> --%>
            	<tr>
                	<td></td>
                	<td> <input id="aaa3" type="radio" name="code"  value="3" /> <img src="<%=basePath%>/images/WFJClient/wx.png"></img></td>
                </tr>
                  </form>
            	<tr class="wfj11_002_paybutton">
                	<td colspan="2">
                    	<div   onclick="zfb()">支付</div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

 
        <script type="text/javascript">
      	var basePath="<%=basePath%>";
        	
          var orid="${ddid}";
          var mort="${morre}";
          var orname="${goodname}";
        $(function()
        {   
             
        })
        //支付宝支付单独跳转页面
	        function zfb()
	        {
	           
		         var val=$('input:radio[name="code"]:checked').val();
	            if(val==null){
	                alert("请选择支付方式");
	                return false;
	            }
	            else{
	              
	              
	          	 if(val=='1')
	          	 {
	    			// window.location.href=  encodeURI(basePath+"page/WFJClient/GoodsShow/cg.jsp");
	       		 
	      			window.location.href=  encodeURI(basePath+"page/WFJClient/GoodsShow/wfjAlipay.jsp?WIDout_trade_no="+orid+"&WIDtotal_fee="+mort+"&WIDsubject="+orname);
	       		  }
	       		  else if(val=='2'){
	       	  	
	          		/* $("#formsutm").attr("action",basePath + "/ea/wfjshop/ea_zfgs.jspa?ddid="+orid+"&baseUrl="+basePath+"&morre="+mort);
					$("#submit").click(); */
					alert("微信支付暂无法使用");
	       	 	         return false;
	       	 	 }
	       	 	 else if(val=='3')
	       	 	 {
	       	 	     alert("微信支付暂无法使用");
	       	 	         return false;
	       	 	 }
	            }
	       		 
	        } 
        
        
        </script>
  </body>
</html>
