<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_activity_list.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var type="${type}";
	var pageCount="${pageForm.pageCount}",pageNumber="${pageForm.pageNumber}";
	var editType="${editType}";
	var backurl="<%=backurl%>";
	$(function(){
		$(".path").each(function(){
			$(this).attr("src",$(this).attr("src").split("$$$$")[0]);
		})
	});
</script>
<title>${type=='00'?'个人活动':type=='01'?'个人论坛':type=='02'?'个人新闻':'生活兴趣'}</title>
</head>

<body>
<form method="post" id="form" name="form">
      	<input type="submit" id="submit" name="submit" style="display: none;">
        	<iframe name="hidden"  style="display: none;"></iframe>
	<div class="wfj01_003">
    	<div class="wfj01_003_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="#" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>${type=='00'?'个人活动':type=='01'?'个人论坛':type=='02'?'个人新闻':'生活兴趣'}<input type="hidden" name="staffId" value="${staffId}" id="staffId"></li>
            	<li class="wfj01_003_edit display"><a href="javascript:;">编辑</a></li>
            </ul>
        </div>
        
        <div class="wfj01_003_content">
        	<div class="wfj01_003_hidden">
            	<div class="wfj01_003_con">
                	<div class="wfj01_003_list">
                		<c:forEach items="${pageForm.list}"  var="l">
	                		<table class="wfj01_003_click01">
	                        	<tr>
	                            	<td rowspan="2" class="wfj01_003_uploadimg"><img src="<%=basePath%>${l.picture}"  class="path"/></td>
	                            	<td><span>${l.title}</span><input type="hidden" class="activitiesID" value="${l.activitiesID}"></td>
	                            	<td rowspan="2" class="wfj01_003_delete"><img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_04.png" /></td>
	                        	</tr>
	                        	<tr>
	                            	<td>作者：${l.author}</td>
	                            </tr>
	                      	</table>
                		</c:forEach>
                    	<input type="hidden"  id="deleteActivities">
                    </div>
                    <div class="wfj01_001">
			         	<div class="wfj01_001_bottom display">
		                    <div class="wfj01_001_bottom_width">
		                        添加<p></p>
		                    </div>
		                </div>
	                </div>
                </div>
            </div>
        </div>
    </div>
    </form>
</body>
</html>