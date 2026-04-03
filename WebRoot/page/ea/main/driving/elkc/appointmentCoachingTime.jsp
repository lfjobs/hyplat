<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/4 0004
  Time: 10:14
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
    <title>预约管理-按教练</title>
</head>
<script type="text/javascript">

</script>
<body>
<header class="com_head">
    <a onclick="javascript: window.history.go(-1);return false;"
       class="back"></a>
    <h1>预约管理</h1>
</header>
<div class="wrap_page">
    <div class="fixed_wrap">
        <div class="tab_wrap clearfix">
           <%-- <a href="<%=basePath%>ea/coachreserv/ea_coachingTime.jspa?teacherId=${param.teacherId}&companyId=${param.companyId}&staffId=${param.staffId}"  class="tab_cur">按教练</a>
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
    </div>
    <form id="appointment" action="" method="post">
        <input type="hidden" id="leaveTime" value="${list[0][2]}"/>
        <input type="hidden" id="dayTime" value="${list[0][3]}"/>
        <div class="jl_info_wrap">
        <div class="jl_info clearfix">
            <img src="<%=basePath%>${coaching[0][6]==null||coaching[0][6]==''?'images/contacts/Network/defaultbig.png':coaching[0][6]}" class="jl_info_L" alt="">
            <ul class="jl_info_R">
                <li>教练:${coaching[0][0]}</li>
                <li>性别：${coaching[0][1]==1?'男':'女'}</li>
                <li>准教车型：${coaching[0][2]}</li>
                <li>所属驾校：${coaching[0][4]}</li>
                <li>联系电话：${coaching[0][3]}</li>
                <a href="<%=basePath%>/ea/student/ea_getStudentAppraise.jspa?teacherId=${param.teacherId}"><li>好评度：${coaching[0][3]}</li></a>
            </ul>
        </div>

                <div class="jl_time_wrap">
                <div class="jl_time_box">
                <c:forEach items="${listTime}" var="m">
                    <c:forEach items="${map}" var="beans" varStatus="var">
                        <c:if test="${beans.key eq m}">
                <div class="jl_time_date">
                    <fmt:formatDate value="${beans.key}" dateStyle="full" />
                </div>
                           <c:if test="${empty beans.value}">
                    <div class="time_select clearfix">

                        <p class style="line-height: 2rem;text-align: center;">教练休班或驾校放假</p>

                    </div>
                            </c:if>

                <div class="time_select clearfix">
                    <c:forEach items="${beans.value}" var="str" varStatus="var">
                        <c:if test="${str[0]=='3'}">
                            <label class="time_box">
                                <span> 教练休假</span>
                            </label>
                        </c:if>
                        <c:if test="${str[0]=='1'}">
                            <label class="time_box yuyue">
                                <input type="hidden" id="startTime" name="lessionStartTime" value="${str[1]}">
                                <input type="hidden" id="endTime" name="lessionEndTime" value="${str[2]} ">
                                <span class="pred"> 已预约</span>
                            </label>
                        </c:if>
                        <c:if test="${str[0]=='2'}">
                    <label class="time_box">
                      <input type="hidden" id="startTime" name="lessionStartTime" value="${str[1]}">
                        <input type="hidden" id="Time"  value="<fmt:formatDate value="${str[1]}" pattern="yyyy-MM-dd" />">
                        <input type="hidden" id="endTime" name="lessionEndTime" value="${str[2]} ">

                        <%
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) +2);
                            Date date=calendar.getTime();


                        %>
                        <c:set var="nowDate" value="<%=date %>"></c:set>
                        <c:if test="${str[1] gt nowDate }">
                            <input type="checkbox" class="time_checkbox" name="time_select" value="${str[4]}">
                            <span> <fmt:formatDate value="${str[1]}" pattern="HH:mm " /> -<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        </c:if>

                        <c:if test="${nowDate gt  str[1]}">
                            <span class="outime"> <fmt:formatDate value="${str[1]}" pattern="HH:mm " /> -<fmt:formatDate value="${str[2]}" pattern="HH:mm" /></span>
                        </c:if>

                    </label>
                        </c:if>
                    </c:forEach>
                </div>
                        </c:if>
                    </c:forEach>
                </c:forEach>
            </div>

                    <a href="javascript:;" class="order_btn">预约</a>
        </div>

    </div>
    <div class="overlay">
        <div class="su_popup">
            <span>恭喜您预约成功</span>
            <a href="javascript:location.reload();" class="popop_close"></a>
        </div>
    </div>
    </form>
</div>
<script>
    var teacherId="${param.teacherId}";
    var companyId="${param.companyId}";
    var staffId="${param.staffId}";
    var basePath="<%=basePath%>";
    var flag="${param.flag}";
    $(function(){
        var leaveTime=$("#leaveTime").val();
        var yuyue=$(".yuyue").length;
        //弹框 关闭
        $(".popop_close").click(function(){
            $(".overlay").removeClass("active");
        })
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
                    var conflict = muber.conflict;
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
                    }else if (conflict=="1"){
                        alert("时间冲突")
                        $cc.removeAttr("checked");
                    }

                }
            });
        });

        })
        //点击预约
        $(".order_btn").click(function(){
            var str=document.getElementsByName("time_select");
            var objarray=str.length;
            var chestr="";
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
                    Time+= $(str[i]).siblings("#Time").val()+",";
                }
            }
            var newArr = Time.split(",");
            var count = 0;
            for(var j= 0; j< newArr.length-1; j++){
                /*if(newArr[j] ==newArr[j+1] ) {*/
                    count++;
              /*  }*/
            }if(count>leaveTime) {
                alert("每天只能约"+leaveTime+"小时");
                return false;
             }
            if(chestr == "")
            {
                alert("请您选择预约时间段！");
            }
            else
            {

                $.ajax({
                type:"POST",
                url:basePath +"ea/coachreserv/sajax_ea_saveTime.jspa?",
                data:{"teacherId":teacherId,"companyId":companyId,"chestr":chestr,"StartTime":startTime,"EndTime":endTime,"flag":flag,"staffId":staffId},
                datatype:  "json",
                success:function(data){
                    if(data!=null){
                    $(".overlay").addClass("active");//提示预约成功
                    }
                }

            });

            }
        })

    function jisuanHour(start,end){
        var date = end.getTime() - start.getTime();   //时间差的毫秒数

        //计算出小时数

        var leave1=date%(24*3600*1000)    //计算天数后剩余的毫秒数
        var hours=Math.floor(leave1/(3600*1000))
        return hours;
    }
</script>
</body>

</html>
