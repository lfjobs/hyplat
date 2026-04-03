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
<script src="<%=basePath%>js/ea/finance/mobile/mobile_after.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<title>申请售后</title>
<script type="text/javaScript">
   var basePath="<%=basePath%>";
   var type="${type}";
   var id="${id}";
   var staid="${staid}";
   var ppid="${ppid}";
</script>
</head>

<body>
	<div class="wfj12_006">
    	<!--中联园区头部-->
    	
    	<div class="wfj_top">
        	<ul>
            	<li><a href="javascript:;" target="_self"><img src="<%=basePath%>js/jqModal/css/images_blue/wfj_return_01.png" /></a></li>
            	<li>申请售后</li>
            	<li><a href="javascript:;" id="apply"><img src="<%=basePath%>js/jqModal/css/images_blue/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <form name="applyRefundForm" id="applyRefundForm" method="post" enctype="multipart/form-data" > 	
        	<input type="submit" name="submit" style="display: none;"/>
        	<div class="wfj12_006_width">
            	<div class="wfj12_006_con">
            	<input name="refundSheet.refundMoney" value="${rs.refundMoney}" type="hidden"/>
                	<ul>
                    	<li class="wfj12_006_paytype"><font>申请服务*</font></li>
                    	<li class="wfj12_006_choice" >
                        	<select id="ss" name="refundSheet.refundType">
                            	<option value="02">
                                	<div>
                                		<ul>
                                        	<li>换货</li>
                                            <li></li>
                                        </ul>		
                                	</div>
                                </option>
                            	<option value="03">
                                	<div>
                                		<ul>
                                        	<li>维修</li>
                                            <li></li>
                                        </ul>		
                                	</div>
                                </option>
                            </select>
                        </li>
                    </ul>
                	<ul>
                    	<li class="wfj12_006_paytype">换货原因<font>*</font></li>
                    	<li class="wfj12_006_choice"><input class="paytype" name="refundSheet.refundCause" value="请先选择换货原因"/></li>
                    </ul>
                	<ul>
                    	<li class="wfj12_006_paytype">换货说明<span>（可不填）</span></li>
                    	<li class="wfj12_006_choice"><input type="text" name="refundSheet.refundRemark" value="请输入换货说明" onfocus="if(this.value=='请输入换货说明'){this.value='';}"  onblur="if(this.value==''){this.value='请输入换货说明';}"/></li>
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
                        <ul class="changeimg">
                            <li class="left">请选择换货原因</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_01.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">大小尺寸与商品描述不符</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">材质,面料与商品描述不符</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">质量问题</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue/choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">颜色,吊牌，款式等与商品不符</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">卖家发错货</li>
                            <li class="right txtright"><img class="change_img" src="<%=basePath%>js/jqModal/css/images_blue//choice_02.png" /></li>
                        </ul>
                        <ul class="changeimg">
                            <li class="left">其他</li>
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
















