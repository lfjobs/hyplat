<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
String path=request.getContextPath();
String basePath=request.getScheme()+"://"
                           +request.getServerName()+":"
		                   +request.getServerPort()+path+"/";
String staid=request.getParameter("staid");
String count=request.getParameter("count");
String price=request.getParameter("price");

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/style12(5).css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/mobile/jqModal_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
	
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/mobile/mobile_apply.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<title>申请退款</title>
<script type="text/javaScript">
   var basePath="<%=basePath%>";
   var type="${type}";
   var id="${id}";
   var staid="<%=staid%>";
   var count="<%=count%>";
   var priceSub="${cashierBills.priceSub}";
   var price="<%=price%>"
   var ppid="${ppid}";
</script>
</head>

<body>
	<div class="wfj12_006">
    	<!--中联园区头部-->
    	
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath%>ea/hypb/ea_getCashBill.jspa?staid=${id}&id=${id}" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png" /></a></li>
            	<li>申请退款</li>
            	<li><a href="javascript:;" id="apply"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <form name="applyRefundForm" id="applyRefundForm" method="post" enctype="multipart/form-data" > 	
        	<input type="submit" name="submit" style="display: none;"/>
        	<div class="wfj12_006_width">
            	<div class="wfj12_006_con">
                	<ul>
                    	<li class="wfj12_006_paytype">退款类型<font>*</font></li>
                    	<li class="wfj12_006_choice" >
                        	<select id="ss" name="refundSheet.refundType">
                        	  <c:if test="${type=='00'}">
                            	<option value="00">
                                	<div>
                                		<ul>
                                        	<li>我要退款<span>（无需退货）</span></li>
                                            <li></li>
                                        </ul>		
                                	</div>
                                </option>
                            	
                                </c:if>
                        	
                            	<option value="00">
                                	<div>
                                		<ul>
                                        	<li>我要退款<span>（无需退货）</span></li>
                                            <li></li>
                                        </ul>		
                                	</div>
                                </option>
                            	<option value="01">
                                	<div>
                                		<ul>
                                        	<li>我要退货</li>
                                            <li></li>
                                        </ul>		
                                	</div>
                                </option>
                                
                            </select>
                        </li>
                    </ul>
                	<ul>
                    	<li class="wfj12_006_paytype">退款原因<font>*</font></li>
                    	<li class="wfj12_006_choice"><input class="paytype" name="refundSheet.refundCause" value="请先选择退款类型"/></li>
                    </ul>
                    <ul>
                    	<li class="wfj12_006_paytype">退款数量<font>*</font><span>最多<%=count %>个</span></li>
                    	<li class="wfj12_006_choice"><input type="text" name="refundSheet.refundNum" class="put3" id="countSub" value="请输入退款数量" onfocus="if(this.value=='请输入退款数量'){this.value='';}"  onblur="if(this.value==''){this.value='请输入退款金额';}" /></li>
                    </ul>
                	<ul>
                    	<li class="wfj12_006_paytype">退款金额<font>*</font><span>最多${cashierBills.priceSub==null?'0':'cashierBills.priceSub'}元</span></li>
                    	<li class="wfj12_006_choice"><input type="text" name="refundSheet.refundMoney" class="put3" id="priceSub" value="请输入退款金额" onfocus="if(this.value=='请输入退款金额'){this.value='';}"  onblur="if(this.value==''){this.value='请输入退款金额';}" /></li>
                    </ul>
                	<ul>
                    	<li class="wfj12_006_paytype">退款说明<span>（可不填）</span></li>
                    	<li class="wfj12_006_choice"><input type="text" name="refundSheet.refundRemark"  value="请输入退款说明" onfocus="if(this.value=='请输入退款说明'){this.value='';}"  onblur="if(this.value==''){this.value='请输入退款说明';}"/></li>
                    </ul>
                </div>
                    <div class="wfj12_006_addp">
                        <ul style="width:55%; float:left;">
                            <li class="left"><img style="padding-left:5px;" src="<%=basePath%>js/jqModal/css/images_blue/wfj_addimg_03.png" /></li>
                        </ul>
                        <ul style="float:right; width:45%;">
                        	
                           <!--   <li class="wfj12_006_hiddenimg"><img src="../Images/wfj11_010_04.png" />
						   <img class="img_delete" src="../Images/img_delete.png" /></li>
                            <li class="wfj12_006_hiddenimg"><img src="../Images/wfj11_010_04.png" />
							<img class="img_delete" src="../Images/img_delete.png" /></li>
                            
                            -->
                            <li class="right hiddenfont">上传凭证 最多三张</li>
                        </ul>
                    </div>
                    <div class="change_width"></div>
                <div class="wfj12_006_bottom">提交申请</div>
            </div>
        </form>
        <div class="wfj12_006_float">
            <div class="wfj12_006_content" style="background-color: red;margin-top:10px;">
                <div style="width:100%;height:400px; overflow-y:auto;">
                    <div class="wfj12_006_width">
                       <c:if test="${type=='00'}">
                            	<ul class="changeimg">
                            <li class="left">退款原因</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_01.png" /></li>
                        </ul>
                       
                        <ul class="changeimg">
                            <li class="left">未按约定时间发货</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">假冒品牌</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>

                                </c:if>
                      
                
                        <ul class="changeimg">
                            <li class="left">7天无理由退换货</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">退运费</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">收到商品破损</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">商品错发/漏发</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">商品需要维修</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">发票问题</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">收到商品不符</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">商品质量问题</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                       
                           
                       
                    </div>
                </div>
        	</div>
        </div>
          <div class="wfj12_006_addimg" style="display:none;">
        	<ul>
            	<li><a href="javascript:;">拍照</a></li>
            	<li><a href="javascript:;">本地上传</a></li>
            	<li><a href="javascript:;">取消</a></li>
            </ul>
        </div>
	</div>
        
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   <div id="occlusion3" class="jqmWindow jqmWindowcss1"></div>
  <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
















