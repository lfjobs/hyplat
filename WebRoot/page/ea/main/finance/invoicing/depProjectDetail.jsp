<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
<title>部门项目预算管理汇总详情查看</title>
 <link rel="stylesheet" href="<%=basePath%>css/websuitMini/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<!-- <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css"> -->
 <link rel="stylesheet" href="<%=basePath%>css/websuitMini/font-awesome.min.css">


<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
<script src="<%=basePath%>/js/ea/websuitMini/hammer.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=basePath%>/js/ea/websuitMini/bootstrap.min.js"></script>
<style>
#back-to-top{

margin-top: 300px; 
}
</style>
	</head>
	<body>

		<div class="container" id="container">
		
		<div class="row">
  <div class="col-xs-12 col-sm-6 col-md-12">
	<div class="table-responsive">
    <table class="table table-striped  table-bordered  table-hover">  
      <h4  class="text-center">项目管理详情查看<small>projectDetail</small></h4>
      <thead>  
        <tr>  
        <th>项目编号</th>
          <th>项目名称</th>
          <th>公司名称</th>  
          <th>部门名称</th>
          <th>负责人</th>  
        </tr> 
         
      </thead>  
        <s:iterator var="arr" value="billlist">
      <tbody>  
        <tr class="success">  
      <td>${arr[14] }</td>
        	<td>${arr[3] }</td>
        	<td>${arr[0] }</td>
        	<td>${arr[1] }</td>
        	<td>${arr[2] }</td>
    
        </tr>
      </tbody> 
        </s:iterator> 
      <thead>  
        <tr>  
          <th>项目内容</th>
          <th>项目开始时间</th>  
          <th>项目结束时间</th>
          <th>责任人</th>  
          <th>更新日期</th> 
        </tr> 
      </thead> 
      
       <s:iterator var="arr" value="billlist">
      <tbody>  
        <tr class="danger">  
      
        	<td>${arr[4] }</td>
        	<td>${arr[5] }</td>
        	<td>${arr[6] }</td>
        	<td>${arr[10] }</td>
    <td>${arr[15] }</td>
        </tr>
      </tbody> 
        </s:iterator> 
        
        
    </table>
    </div>
  </div>
 
</div>
<div class="row text-center">
  <div class="col-xs-6 col-sm-12"><div>
<button type="button" class="btn btn-primary" id="return" onclick="javascript:window.location.href='<%=basePath%>ea/budget/ea_getprojectPlanbudget.jspa?'">返回</button>
</div></div>
</div>
		<!--  </br></br>
		<div id="back-to-top"><span class="icon-circle-arrow-up 5px pull-right"></span></div>
	 
	<div class="row text-center">
  <div class="col-xs-6 col-sm-12">
   <div class="col-xs-6 col-md-12">
    <abbr title="HyperText Markup Language" class="initialism">HTML</abbr>
    To switch directories, type <kbd>cd</kbd> followed by the name of the directory.<br>
To edit settings, press <kbd>ctrl+，</kbd>
</div>	
  </div>
</div>
	 -->



<%--  <div class="row text-center">
  <div class="col-xs-6 col-sm-12">
   <div class="progress progress-striped active">
  <div class="progress-bar" role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
    <span class="sr-only">45% Complete</span>
  </div>
</div>	
  </div>
</div> --%>
</div>
<script>

 
// 然后加入相应的回调函数即可

 $(document).ready(function(){
  /*这是方法一  jquery*/
 $("#container")
   .hammer({
        // 对DOM进行一些初始化，这里可以加入一些参数
   })
   .bind("swipeleft", function(ev) {
     /*   var message = "x坐标：" + ev.gesture.touches[0].pageX + "<br>"
                                      + "Y坐标：" + ev.gesture.touches[0].pageY + "<br>"
                        $("#show").html(message); */
                       window.location.href='"<%=basePath%>"ea/budget/ea_getprojectPlanbudget.jspa?';
   });
   
   
   
   
    $("#container")
   .hammer({
        // 对DOM进行一些初始化，这里可以加入一些参数
       
        touch: true
        
   })
   .bind("swiperight", function(ev) {
     /*   var message = "x坐标：" + ev.gesture.touches[0].pageX + "<br>"
                                      + "Y坐标：" + ev.gesture.touches[0].pageY + "<br>"
                        $("#show").html(message); */
                        alert("您向右滑动了我")
   });
   
});

 $(window).scroll(function(){
	                if ($(window).scrollTop()>200){
	                    $("#back-to-top").fadeIn(500);
	                }
	                else
	                {
	                    $("#back-to-top").fadeOut(500);
	                }
	            });
   //当点击跳转链接后，回到页面顶部位置

	            $("#back-to-top").click(function(){
	                $('body,html').animate({scrollTop:0},500);
	                return false;
	            });
</script>
 </body>
</html>