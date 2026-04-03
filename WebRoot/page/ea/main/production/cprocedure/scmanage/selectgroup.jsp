<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title>选择分组</title>

<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/base.css">
<link rel="stylesheet"
	href="<%=basePath%>css/ea/production/scmanage/sc_manger.css">
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>

</head>

<body>
        <!-- header start  -->
    <header class="com_head">
        <a href="javascript:history.go(-1);" class="back"></a>
        <h1>上传到</h1>
        <a href="<%=basePath%>ea/scm/ea_newOrEditorGroup.jspa?type=nopic" class="head_R">新建分组</a>
    </header>
    <div class="wrap_page">
        <div class="togroup_wrap">
            <c:forEach  items="${grouplist}" var="item">
            <div class="togroup_box clearfix">
                <div class="togroup_img" style="background-image:url(<%=basePath%>${item.groupcover})"></div>
                <div class="togroup_text">
                    <div class="togroup_name">${item.groupname}</div>
                    <div class="togroup_id" style="display:none;">${item.mgId}</div>
                    <div>${item.filenum}张</div>
                </div>
            </div>
            </c:forEach>
            
        </div>
    </div>
    <!--  header end  -->
    <script>
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    </script>
    
    <script type="text/javascript">

    var basePath="<%=basePath%>";
    var type="${type}";
    var mcId="${materialContent.mcId}";
    
      $(function(){
    	  $(document).on("click",".togroup_box",function(){
    		  var groupname = $(this).find(".togroup_name").text();
    		  var groupid = $(this).find(".togroup_id").text();
    		  var bc = "${bc}";
    		   
    		  if(bc=="select"){
    			  var url = basePath+"ea/scm/sajax_ea_moveGroup.jspa";
    		      $.ajax({
    					url : url,
    					type : "get",
    					dataType : "json",
    					async : true,
    					data:{
    						"materialContent.groupID":groupid,
    						"materialContent.mcId":mcId
    					},
    					success:function(data){
    						var fileType=type=="nopic"?"00":type=="novideo"?"01":"02";
    		    			document.location.href = basePath+"ea/scm/ea_findFilelist.jspa?materialContent.fileType="+fileType+"&materialGroup.groupname=${materialGroup.groupname}&materialGroup.mgId=${materialContent.groupID}";
    					},
    					error : function(data) {
    						alert("移动文件失败");

    					}

    				});
    			  
    			  
    			  
    		  }else{
    			  document.location.href = basePath+"ea/scm/ea_uploadFileIndex.jspa?materialGroup.mgId="+groupid+"&materialGroup.groupname="+groupname+"&type="+type
        		  
    		  }
    		 
    		  
    	  });
    	  
      });
    </script>
</body>
</html>