<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style01.css"/>
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_activity_add.js" type="text/javascript"></script>

<script src="<%=basePath%>js/ea/websitemall/card/android/jquery.Jcrop.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/jquery.Jcrop.css"/>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var type="${type}";
	var select=0;
	var $this;
	var boundx,boundy;
	var rate=0,fileID="";
	var editType="${editType}";
	var backurl="<%=backurl%>";
</script>
<style type="text/css">
</style>
<title>添加${type=='00'?'个人活动':type=='01'?'个人论坛':type=='02'?'个人新闻':'生活兴趣'}</title>
</head>
<body class="bgcolorFFF">
	<div class="wfj01_001">
        <form method="post" id="form" name="form"  enctype="multipart/form-data">
        	<input type="submit" id="submit" name="submit" style="display: none;">
        	<iframe name="hidden"  style="display: none;"></iframe>
        	<input type="hidden" name="staffId" value="${staffId}" id="staffId">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="#" target="_self" id="superior"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>添加${type=='00'?'个人活动':type=='01'?'个人论坛':type=='02'?'个人新闻':'生活兴趣'}</li>
            	<li class="edit display"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>

        <div class="wfj01_001_content">
        	<div class="wfj01_001_hidden">
            	<div class="wfj01_001_con">
                	<div class="wfj01_001_width">
                   		<div class="wfj01_004_width">
              				   <ul>
                            	<li class="wfj01_004_date">${date}</li>
                            </ul>
                        </div>
                        <c:forEach items="${list}" var="l">
                        	 <div class="wfj01_001_con_left widths">
	                        	<ul>
	                            	<li class="wfj01_001_bigimg"><img src="<%=basePath%>${l.filepath}" class="wfj01_001_con_img"/></li>
	                            	<li class="wfj01_001_deleteimg" id="${l.fileID}"><img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png" /></li>
	                            </ul>
	                        </div>
                        </c:forEach>
                        <div class="wfj01_001_con_right widths display" >
                        	<ul>
                            	<li><img src="<%=basePath%>images/ea/websitemall/card/wfj_addimg_02.png" /></li>
                            </ul>
                        </div>
                        
                        <div id="pictureDiv" style="display: none;">
                           <input type="file" accept="image/*" id="file"/>
                           <input type="hidden" id="wheelbase">
	                        <div class="wfj01_001_con_left widths"  id="picture">
	                        	<ul>
	                            	<li class="wfj01_001_bigimg"><img/></li>
	                            	<li class="wfj01_001_deleteimg"><img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png" /></li>
	                            </ul>
	                        </div>
                        </div>
                    </div>
                </div>
                <div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="intercept">
                	<div style="width: 80%;height: 40%;background-color: #FFFFFF;position: relative;top: 15%;left: 10%;" id="interceptDiv">
                		<div id="operation" style="position: relative;top: -14%;text-align: right;height:0px;">
                		<span id="determine" style="display:-moz-inline-box;display:inline-block;position:relative;right:10%">
                		    <img src="<%=basePath%>images/ea/websitemall/card/choice_01.png"> 
                		</span>
                		<span id="cancel">
                		    <img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png"> 
                		</span>
                		</div>
                		<img id="interceptImg" >
                	</div>
                </div> 
              	  <div class="wfj01_004" >
       	  			<ul class="wfj01_004_list">
                       <li class="wfj01_004_title"><input type="text"  placeholder="标题" id="title" name="title" value="${ac.title}"/></li>
                       <li class="wfj01_004_cons"><textarea id="textarea"  placeholder="描述" name="describe">${ac.describe}</textarea></li>
               			<li class="wfj01_004_shareLink wfj01_004_cons" style="display: none;">
               				<c:if test="${ac.shareLink!=null}">
               					<a href="javascript:;" id="jump"><font>${ac.shareLink}</font></a>
               					<textarea id="textarea2" placeholder="链接" name="shareLink">${ac.shareLink}</textarea>
               				</c:if>
               			 	<c:if test="${ac.shareLink==null}">
               			 		<textarea id="textarea2" placeholder="链接" name="shareLink"></textarea>
               			 	</c:if> 
                          	<input type="hidden" value="${ac.picture}" id="paths">
                          	<input type="hidden" value="${ac.activitiesID}" name="activitiesID">
                       	</li>
               		</ul>
	           		<div class="wfj01_001_bottom display">
	                    <div class="wfj01_001_bottom_width">
	                        保存信息
	                    </div>
	                </div>
                  </div>
            </div>
        </div>
        </form>
    </div>
</body>
</html>