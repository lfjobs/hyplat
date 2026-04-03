<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style01.css"/>
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_bank_list.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var editType="${editType}";
	var backurl="<%=backurl%>";
	var flag="${flag}";
</script>
<title>银行卡信息</title>
</head>

<body class="bgcolorFFF">
<form method="post" id="form" name="form">
      	<input type="submit" id="submit" name="submit" style="display: none;">
        	<iframe name="hidden"  style="display: none;"></iframe>
	<div class="wfj01_007">
    	<div class="wfj01_007_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="#" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>${staff.staffName} - 银行卡信息<input type="hidden" id="cardType" value="${cardType}"></li>
            	<%-- <li  class="wfj01_007_edit display"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li> --%>
            </ul>
        </div>
        <input type="hidden" id="staffId" value="${staffId}" name="staffId">
        <div class="wfj01_007_changeinfo">
<!--             <div class="wfj01_007_ca bgcolorDDEEEE">
                <div class="wfj01_007_width">
                    <ul id="title">
                        <li class="left">银行卡详情</li>
                        <li class="right">+</li>
                    </ul>
                </div>
            </div> -->
        	<div class="wfj01_007_card">
            	<div class="wfj01_007_hid">
                
                	<div class="wfj01_007_cards">
                        <div class="wfj01_007_width">
                        <c:forEach items="${list}" var="l">
	                        <div class="wfj01_007_addcard">
                                <div class="wfj01_007_width">
                                	<input type="hidden" id="bankId" value="${l.bankAccountID}">
                                    <ul>
                                        <li class="left">${l.bankName}</li>
                                        <li class="left">
                                        	<font <c:if  test="${l.accountNature=='信用卡'}">style="color:#00cc00;"</c:if>>${l.accountNature}</font>
                                        </li>
                                        <c:if test="${l.isdefault=='1'}">
                                        	<li class="right">默认</li>
                                        </c:if>
                                    </ul>
                                    <ul>
                                    	<li class="left cardType">${l.cardType=='00'?'对私':'对公'}</li>
                                        <li class="right"><input class="txts" readonly="readonly" type="text" value="${l.bankAccount}" /></li>
                                    </ul>
                                </div>
                            </div>
                        </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
		<div class="wfj01_007_bottom" id="addBank">
        	<div>添加更多银行卡</div>
        </div>
    </div>
   <!--  <div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" class="wfj01_001_popup">
    	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 84%;text-align: center;" class="wfj01_001_cardType_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;">切换显示类型</div>
    	</div>
    	<hr  style="position: absolute;top: 92%;color:#000000;width: 100%;">
    	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 92%;text-align: center;" class="wfj01_001_popup_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;" class="positionSelection" name="job">选择默认银行卡</div>
    	</div>
    </div> -->
    </form>
</body>
</html>