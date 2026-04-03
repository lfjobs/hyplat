<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>教练评价</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath %>";

    </script>

</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;">我的教练</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">


            <ul class="coach_list2 team2" id="coach_list2">
                <c:forEach items="${tealist}" var="item">
              <li id="${item[0]}">
                  <a href="<%=basePath%>/ea/student/ea_getStudentAppraise.jspa?teacherId=${item[0]}">
                  <img src="<%=basePath%>${item[3]==null?"images/ea/driving/elkc/head.png":item[3]}" class="left">
                  <div class="text">
                      <h4>${item[1]}</h4>
                     <p></p>
                     <p class="tel">${item[2]}</p>
                   </div>
                   <img class="right" src="<%=basePath%>st/images/ico-right2.png">
              </a>
              </li>
                </c:forEach>

            </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath='<%=basePath%>';
    var companyID='${companyID}';
</script>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");


        /*搜索*/
        $(".search_frd input").focus(function(){
            $(".search_frd .search_").hide();
        });
        $(".search_frd input").blur(function(){
            if( $(".search_frd input").val()==""){
                $(".search_frd .search_").show();
            }else{
                $(".search_frd .search_").hide();
            }
        });
        $(".search_frd .search_").click(function(){
            $(".search_frd .search_").hide();
            $(".search_frd input").focus();
        });
        $(".search").on("input",function(){
            $("#coach_list2").empty();
            var staffName = $(this).val();
            teamList(staffName);
            staffName="";
        });
    });
</script>

</body>
</html>