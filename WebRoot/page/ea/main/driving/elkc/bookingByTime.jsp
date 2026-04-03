<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/4 0004
  Time: 10:09
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
    <title>预约管理-按时间</title>
</head>

<body>
<header class="com_head">
    <a  onclick="javascript: window.history.go(-1);return false;" class="back"></a>
    <h1>预约管理</h1>
</header>
<div class="wrap_page">
    <div class="fixed_wrap">
        <div class="tab_wrap clearfix">
            <%--<a href="<%=basePath%>ea/coachreserv/ea_CoacheRservation.jspa?companyId=${param.companyId}">按教练</a>
            <a href="<%=basePath%>ea/coachreserv/ea_allTime.jspa?companyId=${param.companyId}" class="tab_cur">按时间</a>--%>
            <c:if test="${param.flag==1}">
                <a href="<%=basePath%>ea/coachreserv/ea_registration.jspa?companyId=${param.companyId}&staffId=${param.staffId}&flag=1" >按教练</a>
            </c:if>
            <c:if test="${param.flag==2}">
                <a href="<%=basePath%>ea/coachreserv/ea_CoacheRservation.jspa?companyId=${param.companyId}&flag=2">按教练</a>
            </c:if>
            <c:if test="${param.flag==1}">
                <a href="<%=basePath%>ea/coachreserv/ea_accordingTime.jspa?companyId=${param.companyId}&staffId=${param.staffId}&flag=1" class="tab_cur">按时间</a>
            </c:if>
            <c:if test="${param.flag==2}">
                <a href="<%=basePath%>ea/coachreserv/ea_allTime.jspa?companyId=${param.companyId}&flag=2"  class="tab_cur" >按时间</a>
            </c:if>

        </div>

    </div>
    <div class="order_time_wrap">
        <input type="hidden" id="leaveTime" value="${list[0][2]}"/>
        <div class="time_day clearfix">
           <c:forEach items="${listTime}" var="str" varStatus="var">
            <label class="day_box">
                <c:if test="${var.index eq 0}">
                     <input type="radio" checked class="day_checkbox" name="order_days" value="<fmt:formatDate value="${str}" />">
                </c:if>
                <c:if test="${var.index ne 0}">
                    <input type="radio"  class="day_checkbox" name="order_days" value="<fmt:formatDate value="${str}" />">
                </c:if>
                <div class="day_style">
                    <span><fmt:formatDate value="${str}" dateStyle="full"/></span>
                </div>
            </label>
            </c:forEach>
        </div>
        <div class="days_list">
        <c:forEach items="${teachers}" var="teachers" varStatus="var">
            <div class="days_jl_box">
                <div class="days_jl_info clearfix">
                        <img src="<%=basePath%>${teachers[7]==null || teachers[7]==''?"images/contacts/Network/defaultbig.png" :teachers[7]  }" class="jl_img" alt="">
                    <div class="jl_text">
                        <div class="jl_name">${teachers[0]}</div>
                        <c:if test="${teachers[5]=='1'}"><div class="jl_type">科目一</div></c:if>
                        <c:if test="${teachers[5]=='2'}"><div class="jl_type">科目二</div></c:if>
                        <c:if test="${teachers[5]=='3'}"><div class="jl_type">科目三</div></c:if>
                        <c:if test="${teachers[5]=='4'}"><div class="jl_type">科目四</div></c:if>
                    </div>
                </div>

                <div class="time_select clearfix">
                    <c:forEach items="${map}" var="beans" varStatus="var">
                        <c:if test="${beans.key eq teachers[6]}">
                            <c:if test="${empty beans.value}">
                                    <p class style="line-height: 2rem;text-align: center;">教练休班或驾校放假</p>
                            </c:if>
                        <c:forEach items="${beans.value}" var="str" varStatus="var">
                            <c:if test="${str[0]=='3'}">
                                <label class="time_box">
                                    <span> 教练休假</span>
                                </label>
                            </c:if>
                            <c:if test="${str[0]=='1'}">
                                <label class="time_box ">
                                    <input type="hidden" id="startTime" name="lessionStartTime" value="${str[1]}">
                                    <input type="hidden" id="endTime" name="lessionEndTime" value="${str[2]} ">
                                    <span class="pred"> 已预约</span>
                                </label>
                            </c:if>
                            <c:if test="${str[0]=='2'}">
                    <label class="time_box">
                        <input type="hidden" value="${str[3]}" id="teachers">
                        <input type="hidden" id="startTime" name="lessionStartTime" value="${str[1]}">
                        <input type="hidden" id="Time" name="lessionStartTime" value="<fmt:formatDate value="${str[1]}" pattern="yyyy-MM-dd" />">
                        <input type="hidden" id="endTime" name="lessionEndTime" value="${str[2]} ">

                       <%
                           Calendar calendar = Calendar.getInstance();
                           calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) +2);
                           Date date=calendar.getTime();


                       %>
                        <c:set var="nowDate" value="<%=date %>"></c:set>
                        <c:if test="${str[1] gt nowDate }">
                            <input type="checkbox" class="time_checkbox" name="time_select" value="${str[4]}">
                          <span><fmt:formatDate value="${str[1]}" pattern="HH:mm "/>-<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        </c:if>
                        <c:if test="${nowDate gt str[1]}">

                            <span class="outime"><fmt:formatDate value="${str[1]}" pattern="HH:mm "/>-<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        </c:if>
                    </label>
                            </c:if>
                    </c:forEach>
                        </c:if>
                    </c:forEach>
                </div>

            </div>

        </c:forEach>
    </div>
    <a href="javascript:;" class="order_btn">预约</a>
    <div class="overlay">
        <div class="su_popup">
            <span>恭喜您预约成功</span>
            <a href="javascript:location.reload();" class="popop_close"></a>
        </div>
    </div>

</div>
<script>
    var basePath="<%=basePath%>";
    var leaveTime=$("#leaveTime").val();
    var companyId="${param.companyId}";
    var staffId="${param.staffId}";



    function jisuanHour(start,end){
        var date = end.getTime() - start.getTime();   //时间差的毫秒数

        //计算出小时数

        var leave1=date%(24*3600*1000)    //计算天数后剩余的毫秒数
        var hours=Math.floor(leave1/(3600*1000));
        return hours;
    }


    $(function() {
        //弹框 关闭
        $(".popop_close").click(function() {
            $(".overlay").removeClass("active");
        })

        //点击预约
        $(".order_btn").click(function() {

            var leaveTime=$("#leaveTime").val();
            var str=document.getElementsByName("time_select");
            var objarray=str.length;
            var chestr="";
            var teacherId="";
            var startTime="";
            var endTime="";
            var Time="";
            for (i=0;i<objarray;i++)
            {
                if(str[i].checked == true)
                {
                    chestr+=str[i].value+",";
                    console.info(chestr);
                    startTime+= $(str[i]).siblings("#startTime").val()+",";
                    endTime+= $(str[i]).siblings("#endTime").val()+",";
                    teacherId+=$(str[i]).siblings("#teachers").val()+",";
                    Time+= $(str[i]).siblings("#Time").val()+",";
                }
            }
            var newArr = Time.split(",");
            var count = 0;
            for(var j= 0; j< newArr.length-1; j++){
                if(newArr[j] ==newArr[j+1] ) {
                    count++;
                }
            }if(count>=leaveTime) {
                alert("每天只能约"+leaveTime+"小时");
                return false;
            }
            if(chestr == "")
            {
                alert("请先选择预约时间段");
            }
            else {
                $(".order_btn").hide();
                $.ajax({
                    type: "POST",
                    url: basePath + "ea/coachreserv/sajax_ea_saveTime.jspa?",
                    data: {
                        "teacherId": teacherId,
                        "companyId": companyId,
                        "chestr": chestr,
                        "StartTime": startTime,
                        "EndTime": endTime,
                        "staffId":staffId
                    },
                    datatype: "json",
                    success: function (data) {
                        if (data != null) {
                            $(".overlay").addClass("active");//提示预约成功
                        }
                    }
                })
            }

    });
    //选择日期
    $(".day_style").click(function(){
        var _index=$(".time_day .day_box").index($(this).parent());
        var time=$(this).parent().find(".day_checkbox").val();
        console.log("获取当日数据");
        console.log(_index);
        console.log(time);
        $.ajax({
            type: "POST",
            url: basePath +"ea/coachreserv/sajax_ea_coachByTime.jspa?",
            data: { "companyId": companyId, "lessionStartTime": time,"staffId":staffId},
            datatype: "json",
            success: function (data) {
                var member = eval("("+data+")");
                var teach=member.teachers;
                var time=member.map;
                var str = new Array();
                var str1 = new Array();
                $(".days_list").html("");
              $.each(teach,function (i,teacher) {
                    str.push(' <div class="days_jl_box">');
                    str.push('<div class="days_jl_info clearfix">');
                    if(teacher[7]!=null&&teacher[7]!=""){
                    str.push('<img src="' + basePath + teacher[7] + '" class="jl_img" alt="">');
                    }else {str.push('<img src="' + basePath + "images/contacts/Network/defaultbig.png" + '" class="jl_img" alt="">');
                    }
                    str.push('<div class="jl_text">');
                    str.push('<div class="jl_name">' + teacher[0] + '</div>');
                    if(teacher[5]=='1'){
                    str.push('<div class="jl_type">' + "科目一" + '</div>');
                    }else if(teacher[5]=='2'){
                    str.push('<div class="jl_type">' + "科目二" + '</div>');
                    }else if(teacher[5]=='3') {
                        str.push('<div class="jl_type">' + "科目三"+ '</div>');
                    }else if(teacher[5]=='4'){
                    str.push('<div class="jl_type">' + "科目四" + '</div>');
                    }
                    str.push(' </div>');
                    str.push('</div>');
                    $.each(time,function(key,values){
                        if(teacher[6]==key) {
                            console.log(key);
                            str.push('<div class="time_select clearfix">');
                            if(values.length==0 ){
                                str.push('<p class style="line-height: 2rem;text-align: center;">教练休班或驾校放假</p>');
                            }else {
                                $.each(values, function (j, itme) {
                                    var date = itme[5];
                                    var j=date.split(" ");
                                    if (itme[0] == '3') {
                                        str.push('<label class="time_box">');
                                        str.push('<span> 教练休假</span>');
                                        str.push('</label>');
                                    } else if (itme[0] == '2') {
                                        str.push('<label class="time_box">');
                                        str.push('<input type="hidden" value="' + itme[3] + '" id="teachers">');
                                        str.push('<input type="hidden" id="Time" name="lessionStartTime" value="'+j[0]+'">');
                                        str.push(' <input type="hidden" id="startTime" name="lessionStartTime" value="' + itme[5] + '">');
                                        str.push('<input type="hidden" id="endTime" name="lessionEndTime" value="' + itme[6] + '">');
                                        str.push('<input type="checkbox" class="time_checkbox"  name="time_select" value="' + itme[4] + '">');
                                        str.push(' <span>' + itme[1] + ":00" + '-' + itme[2] + ":00" + '</span>');
                                        str.push(' </label>');
                                    } else if (itme[0] == '1') {
                                        str.push('<label class="time_box">');
                                        str.push('<span> 已预约</span>');
                                        str.push('</label>');
                                    }
                                });
                            }
                            str.push('</div>');
                        }
                    });
                    str.push('</div>');
             });
                $(".days_list").append(str.join(""));

            }
           })
      });

        $(document).on("click",".time_checkbox",function(e) {
            var  $cc = $(this);
            if(!$(this).is(':checked')){
                 return;
            }
            var start = new Date($(this).parent().find("#startTime").val());
            var end = new Date($(this).parent().find("#endTime").val());
            var odtId = $(this).val();
            var teacherId= $(this).siblings("#teachers").val();

            //同一天，不同的教练同一时间段不能选

            var curid="";
            var bool = true;
            var selhours = 0;
            $('input:checkbox[name=time_select]:checked,.pred').each(function(k){
                curid=$(this).val();
                var curstart =  new Date($(this).parent().find("#startTime").val());
                var currend = new Date($(this).parent().find("#endTime").val());
                if(curid!="") {
                    selhours += jisuanHour(curstart, currend);
                }
                if(odtId!=curid){

                    if((curstart<start&&currend<end)||(curstart>start&&currend>end)){

                        //不冲突
                    }else{
                        alert("时间冲突");
                        bool = false;
                        return false;
                    }
                }
            })
            if(!bool){
                   return false;
            }

            console.info(odtId);
            $.ajax({
                type:"POST",
                url:basePath +"ea/coachreserv/sajax_ea_Reservation.jspa?",
                data:{"teacherId":teacherId,"companyId":companyId,"odtId":odtId,"staffId":staffId},
                datatype:  "json",
                success:function(data){
                    var str = new Array();
                    var strj = new Array();
                    var muber=eval("("+data+")");
                    var hours=muber.hours;
                    var status=muber.status;
                    if(status=='3'){
                        str.push('<label class="time_box yuyue">');
                        str.push('<span> 已预约</span>') ;
                        str.push('</label>');
                    }
                    $(".clearfix").append(str.join(""));
                    if (hours==leaveTime){
                        alert("您今天已经预约了"+leaveTime+"小时，明天再来预约")
                        $(".order_btn").hide();
                    }else if (hours+selhours>leaveTime){
                        alert("今天预约超出"+leaveTime+"小时！")
                        $cc.removeAttr("checked");
                    }

                }
            });
        });

    });

</script>
</body>

</html>
