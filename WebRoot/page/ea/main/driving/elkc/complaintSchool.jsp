<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/1 0001
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <title>投诉详情</title>
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/elkc/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/elkc/complaint_style.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/elkc/left_jt.png"></a>
        </li>
        <li style="width: 80%;">投诉详情</li>
        <li style="width: 10%;"></li>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <p class="tit">我的投诉</p>
           <%-- <from id="from"  onsubmit="return tovoid()" method="post">--%>
            <ul class="complaint">
                <li class="sel-all">
                    <input type="hidden" name="teacherId" id="tid" value="${teacherId}">
                    <input type="hidden" name="studentId" id="sid" value="${studentId}">
                    <input type="hidden" name="staffid" id="fid" value="${staffid}">
                    <input type="hidden" name="companyid" id="cid" value="${companyid}">
                    <input type="hidden" name="studentname" id="sname" value="${studentname}">
                    <img src="<%=basePath%>/images/ea/elkc/ico-header_.png" class="img">
                    <div class="text">
                        <p class="select">请选择投诉教练</p>
                    </div>
                    <a ><img src="<%=basePath%>/images/ea/elkc/ico-right.png" class="right sel-r"></a>
                </li>
            </ul>
            <div class="comp_txt">
                <div class="driving_">
                    <textarea class="driving_2" name="content" placeholder="请输入您的投诉意见" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的投诉意见'"></textarea>
                </div>

                <input type="button" value="提交" class="huifu">
            </div>
           <%-- </from>--%>
        </div>
    </div>
</div>
<div class="sel-alert">
    <header>
        <ul>
            <li style="width: 10%;">
                <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/elkc/left_jt.png"></a>
            </li>
            <li style="width: 80%;">选择教练</li>
            <li style="width: 10%;"></li>
        </ul>
    </header>
    <%--<ul class="sel_con">--%>
        <%--<c:forEach items="${list}" var="ter">--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="photo" value="${ter[0]}" id="photo"></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="teacherId" value="${ter[2]}" id="teacherId"></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="studentId" value="${ter[4]}" id="studentId"></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="staffid" value="${ter[3]}" id="staffid"></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="companyid" value="${ter[5]}" id="companyid"></li>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<li><input type="hidden" name="studentname" value="${ter[6]}" id="studentname"></li>&ndash;%&gt;--%>
        <%--<li id="name">${ter[1]}</li>--%>
        <%--</c:forEach>--%>
    <%--</ul>--%>

    <ul class="coach_list2 team2 sel_con">
        <c:forEach items="${list}" var="item">
            <li id="${item[2]}">
                   <input type="hidden" name="photo" value="${item[0]}" id="photo">
                   <input type="hidden" name="teacherId" value="${item[2]}" id="teacherId">
                   <input type="hidden" name="studentId" value="${item[4]}" id="studentId">
                   <input type="hidden" name="staffid" value="${item[3]}" id="staffid">
                   <input type="hidden" name="companyid" value="${item[5]}" id="companyid">
                   <input type="hidden" name="studentname" value="${item[1]}" id="studentname">

                    <img src="<%=basePath%>${item[0]==null?"images/ea/driving/elkc/head.png":item[0]}" class="left">
                    <div class="text">
                        <h4>${item[1]}</h4>
                        <p></p>
                        <p class="tel">${item[7]}</p>
                    </div>
                    <img class="right" src="<%=basePath%>st/images/ico-right2.png">

            </li>
        </c:forEach>

    </ul>
</div>
<script>
    $(document).ready(function(){

        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".content .con").css("height",$(window).height()*0.92+"px");

        /*选择教练*/
        $(".sel-all").click(function(){
            $(".sel-alert").show();
        });

        var basePath = "<%=basePath%>";
        $(document).on("click",".sel-alert .sel_con li",function(){
            var photo=$(this).find("#photo").val();
            var teacherId=$(this).find("#teacherId").val();
            var studentId=$(this).find("#studentId").val();
            var staffid=$(this).find("#staffid").val();
            var companyid=$(this).find("#companyid").val();
            var studentname=$(this).find("#studentname").val();


            if(photo==null||photo==""){
               photo = "images/ea/driving/elkc/head.png";
            }
            $(".img").attr("src",basePath+photo);
            $("#tid").val(teacherId);
            $("#sid").val(studentId);
            $("#fid").val(staffid);
            $("#cid").val(companyid);
            $("#sname").val(studentname);
            $(".select").text(studentname);
            $(".sel-alert").hide();
        });
        $(document).on("click",".huifu",function () {
            var content=$(".driving_2").val();
            if(content==""){
                alert("请输入您的投诉意见");
                return
            }
            var name=$(".select").text();
            if(name=="请选择投诉教练"){
                alert("请选择投诉教练");
                return
            }
            var tid=$("#tid").val();
            var sid=$("#sid").val();
            var fid=$("#fid").val();
            var cid=$("#cid").val();
            var sname=$("#sname").val();
            $.ajax({
                url:"<%=basePath%>ea/complaint/sajax_ea_addComplaint.jspa?companyId="+cid+"&teacherId="+tid+"&studentId="+sid+ "&staffid="+fid+ "&content="+content+"&studentName="+sname,
                type:"post",
                async:false,
                dateType:"json",
                success : function (date) {
                    var medate=eval("("+date+")");
                    var fan=medate.fanhui;
                    if(fan=="ok"){
                        window.location.href="<%=basePath%>page/ea/main/driving/elkc/complaintSuccess.jsp?staffid="+fid;
                    }
                }
            });

        })


    });

</script>
</body>
</html>