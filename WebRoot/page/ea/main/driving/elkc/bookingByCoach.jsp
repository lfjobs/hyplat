<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/4 0004
  Time: 10:07
  To change this template use File | Settings | File Templates.
--%>
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

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=path%>/js/ea/elkc/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/base.css">
    <link rel="stylesheet" href="<%=path%>/css/ea/elkc/order_man.css">
    <script src="<%=path%>/js/ea/elkc/jquery.min.js"></script>
    <title>预约管理-按教练</title>
</head>
    <script type="text/javascript">
        var basePath='<%=basePath%>';
        var staffId='${param.staffId}';
        $(document).on("click",".search_btn",function(){
            var TeacherName=$("#name").val();
            var companyId=$("#companyId").val();
            if(TeacherName==null||TeacherName==""){
                alert("请输入所要查询的教练姓名");
            }else {
            $.ajax({
                type:"POST",
                url:basePath +"ea/coachreserv/sajax_ea_chaTeacher.jspa?TeacherName="+TeacherName+"&companyId="+companyId+"&staffId="+staffId,
                datatype:  "json",
                success:function(data){
                    var member = eval("("+data+")");
                    var t=member.list;
                    var str = new Array();
                    $(".jl_list").html("");
                    $.each(t,function (i,teach) {
                        str.push(' <div class="jl_box clearfix">');
                        if(teach[0]!=null&&teach[0]!=""){
                            str.push(' <img src="'+basePath+teach[0]+'" class="jl_img" alt="">');
                        }else {
                            str.push(' <img src="'+basePath+"images/contacts/Network/defaultbig.png"+'" class="jl_img" alt="">');
                        }
                        str.push('<div class="jl_text">');
                        str.push('<input type="hidden" id="companyId" value="'+teach[3]+'" >');
                        str.push('<input type="hidden" id="teacherId" value="'+teach[4]+'"  >');
                        str.push('<div class="jl_name">教练:'+teach[1]+'</div>');
                        str.push('<div class="star_box" >');
                        for(var a=1;a<=teach[5];a++){
                            str.push('<i></i>');
                        }
                        str.push('</div>');
                        str.push('</div>');
                        str.push('<a href="###" class="jl_btn">预约</a>');
                        str.push('</div>');
                    });
                    $(".jl_list").append(str.join(""));
                }
            });
            }
        });
        $(document).on("click",".jl_box",function(){
                var teacherId=$(this).find(" #teacherId").val();
                var companyId=$(this).find(" #companyId").val();
                var flag="${param.flag}";
                var staffId="${param.staffId}"
                document.location.href=basePath+"ea/coachreserv/ea_coachingTime.jspa?teacherId="+teacherId+"&companyId="+companyId+"&staffId="+staffId+"&flag="+flag;
            });
    </script>
<body>
<header class="com_head">
    <%--<a onclick="javascript: window.history.go(-1);return false;" class="back"></a>--%>
    <h1>预约管理</h1>
</header>
<div class="wrap_page jl_wrap">
    <div class="fixed_wrap">
        <div class="tab_wrap clearfix">
            <%--<a href="<%=basePath%>ea/coachreserv/ea_CoacheRservation.jspa?companyId=${param.companyId}" class="tab_cur">按教练</a>
            <a href="<%=basePath%>ea/coachreserv/ea_allTime.jspa?companyId=${param.companyId}">按时间</a>--%>
            <c:if test="${param.flag==1}">
                <a href="<%=basePath%>ea/coachreserv/ea_registration.jspa?companyId=${param.companyId}&staffId=${param.staffId}&flag=1" class="tab_cur">按教练</a>
            </c:if>
            <c:if test="${param.flag==2}">
                <a href="<%=basePath%>ea/coachreserv/ea_CoacheRservation.jspa?companyId=${param.companyId}&flag=2" class="tab_cur">按教练</a>
            </c:if>
            <c:if test="${param.flag==1}">
                <a href="<%=basePath%>ea/coachreserv/ea_accordingTime.jspa?companyId=${param.companyId}&staffId=${param.staffId}&flag=1" >按时间</a>
            </c:if>
            <c:if test="${param.flag==2}">
                <a href="<%=basePath%>ea/coachreserv/ea_allTime.jspa?companyId=${param.companyId}&flag=2" >按时间</a>
            </c:if>

        </div>
        <div class="search_wrap clearfix">
            <input type="text" class="search_inp" name="name" id="name">
            <input type="hidden" class="companyId" value="${beans[3]}" >
            <a href="###" class="search_btn">查询</a>
        </div>
    </div>
    <div class="jl_list">
        <c:forEach items="${list}" var="beans">
        <div class="jl_box clearfix">
            <img src="<%=basePath%>${beans[0]==null||beans[0]==''?'images/contacts/Network/defaultbig.png':beans[0]}" class="jl_img" alt="">
            <div class="jl_text">
                <input type="hidden" id="companyId" value="${beans[3]}" >
                <input type="hidden" id="teacherId" value="${beans[4]}"  >
                <div class="jl_name">教练:${beans[1]}</div>
                <div class="star_box" >
                    <c:forEach begin="1" end="${beans[5]}" var="v" >
                    <i></i>
                    </c:forEach>
                </div>

      </div>
            <a href="###" class="jl_btn">预约</a>
</div>
</c:forEach>
</div>
</div>
</body>

</html>
