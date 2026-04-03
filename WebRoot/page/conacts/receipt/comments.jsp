<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />

<title>发表评价</title>
	<link href="<%=basePath%>css/contacts/comments/style12.css" rel="stylesheet"
		type="text/css" />
		
		<link href="<%=basePath%>css/contacts/comments/jqModal_blue.css" rel="stylesheet"
		type="text/css" />
		<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
</head>
	<script type="text/javascript">
	var basePath="<%=basePath%>";
	var select=0,$this,$this2;
	var user='${user}';
</script>
<body>
	<div class="wfj12_018">
    
    	<!--中联园区头部-->
    	<div class="wfj_top">
    	<div id="pp">
        	<ul>
            	<li><a  target="_self"><img src="<%=basePath%>images/contacts/comments/wfj_return_02.png" onclick="fanhui()"/></a></li>
            	<li>发表评价</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/comments/top_more_02.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	<form action="" id="from1" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="comments.cashierBillsID" value="${list[0][0] }">
    	<input type="hidden" name="comments.ppID" value="${list[0][5]}">
    	<input type="hidden" name="comments.csid" value="${list[0][1] }">
    	<input type="hidden" name="key" id="key" value="${list[0][3] }">
    	<input type="hidden" style="display:none;" value="" id="sub2" />
        <div class="wfj12_018_content">
        	<div class="wfj12_018_hidden">
            	<div class="wfj12_018_top_assessment">
                	<div class="wfj12_018_width">
                    	<table id="table">
                            <tr>
                            <input type="hidden"  id="image" >
                                <td width="30%"  id="td1"><img src="${list[0][6]}" /></td>
                                <td width="70%"><textarea placeholder="亲，喜欢宝贝吗？快来写下对宝贝的评价吧！" name="comments.comments"></textarea></td>
                            </tr>
                        </table>
							<div class="wfj12_018_addimg  wfj12_018_img" id="redit">
								<div class="wfj12_018_add_imgs">
									<img
										src="<%=basePath%>images/contacts/comments/wfj_addimg_03.png" />
								</div>
								
							</div>

							<div id="pictureDiv" style="display: none;">
								<input type="file" accept="image/*" id="file" name="pictureList"
									multiple="multiple" />
								<div class="wfj12_018_addimg wfj12_018_img" id="picture">
									<div class="wfj12_018_add_imgs">
										<img/>
									</div>	
									<img class="wfj12_018_add_imgs_dele" src="<%=basePath%>images/contacts/comments/img_delete_02.png" />
									
								</div>	
							</div>
						</div>
                </div>
                <div class="wfj12_018_assessimg">
                	<div class="wfj12_018_width" id="divs">
                        <table>
                            <tr id="tuu">
                            	
                                <td class="selected" ><input type="hidden" name="comments.evaluation" id="evaluation"><img src="<%=basePath%>images/contacts/comments/wfj_assessment_01_01.png" /><span id="id1">好评</span></td>
                                <td ><img src="<%=basePath%>images/contacts/comments/wfj_assessment_02.png" /><span id="id2">中评</span></td>
                                <td ><img src="<%=basePath%>images/contacts/comments/wfj_assessment_03.png" /><span id="id3">差评</span></td>
                            </tr>
                        </table>
                    </div>
                </div>
                
                <div class="wfj12_018_assess_star">
                	<div class="wfj12_018_width">
                        <table class="comtbl">
                            <tr>
                                <td colspan="2"><span>店铺评分</span></td>
                            </tr>
                            <tr>
                                <td width="40%">描述相符</td>
                                <td width="60%">
                                    <div class="wfj12_018_depict" id="score1"  >
                                        <ul >
                                        	<input type="hidden" name="comments.score1" class="score">
                                            <li ><img name='0' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='1' id="im1" src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='2' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='3' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='4' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>物流服务</td>
                                <td>
                                    <div class="wfj12_018_depict" id="score2" >
                                        <ul>
                                        	<input type="hidden" name="comments.score2" class="score">
                                            <li><img name='0' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='1' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='2' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='3' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='4' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>服务态度</td>
                                <td>
                                    <div class="wfj12_018_depict" id="score3">
                                        <ul>
                                        	<input type="hidden" name="comments.score3"  class="score">
                                        
                                            <li><img name='0' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='1' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='2' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='3' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                            <li><img name='4' src="<%=basePath%>images/contacts/comments/wfj_star_01.png" /></li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <input type="hidden" id="companyID" name="companyID" value="${param.companyID}">
            </div>
        </div>
        <div class="wfj12_018_bottom">
			<div class="wfj12_018_width">
                <table>
                    <tr>
                    	<input type="hidden" name="comments.anonymous" id="hide">
                        <td width="50%"><img src="<%=basePath%>images/contacts/comments/wfj_right_03.png" /><span id="sp">匿名评价</span></td>
                        <td align="right" width="50%"><div id="commit">发表评价</div></td>
                    </tr>
                </table>
            </div>
        </div>
        </form>
        
        <div class="wfj12_018_addph" style="display:none;">
        	<ul>
            	<li><a class="verification" href="javascript:;"><input id="pic1" type="file" name="activities.pic" onchange="show(this.id)" accept="image/*" capture="camera"/>拍照</a></li>
            	<li><a class="verification" href="javascript:;"><input onchange="show(this.id)" id="pic" type="file" name="activities.pic"/>从本地上传</a></li>
            	<li><a href="javascript:;">取消</a></li>
            </ul>
        </div>
        </div>
        
    </div>

   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
<script>var basePath="<%=basePath%>";</script>
    <script type="text/javascript" src="<%=basePath%>js/restaurant/comments.js">
    </script>
</body>
</html>
