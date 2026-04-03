<%@ page import="hy.ea.bo.CAccount" %>
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
    CAccount cAccount = (CAccount) session.getAttribute("key_caccount");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/popup.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/calendar.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/new_style.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/studentManage.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/calendar.min.js"></script>
    <title>学员报名信息</title>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/driving/elkc/left_jt.png"></a>
        </li>
        <li style="width: 80%;">学员报名资料</li>
        <li id="svaeApply" style="width: 10%;">保存</li>
    </ul>
</header>

<div class="overlay">
    <div class="save_info">
        <img src="<%=basePath%>/images/ea/driving/elkc/popup_ico.png" class="save_top" alt="">
        <div class="save_text">您的信息已经保存成功！</div>
        <div class="save_btn">
            <a href="<%=basePath%>/driving/elkc/ea_studentApplyInfoPage.jspa?staffId=${param.staffId}" class="go_on">继续修改</a>
            <a href="javascript:history.go(-1)" class="back_up">返回上一级</a>
        </div>
    </div>
</div>

<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="xy_basics">
                <form id="applyForm" method="post"
                      enctype="multipart/form-data">
                <ul>
                    <li>
                        <p>报名驾校</p>
                        <input type="hidden" value="${tbJpStudentInfo.studentKey}" name="tbJpStudentInfo.studentKey">
                        <input type="hidden" value="${tbJpStudentInfo.studentId}" name="tbJpStudentInfo.studentId">
                        <input type="hidden" value="${tbJpStudentInfo.companyId}" class="ipt" name="tbJpStudentInfo.companyId">
                        <input type="text" value="${companyName}" readonly="readonly" class="ipt">
                    </li>
                    <li>
                        <p>报名日期</p>
                        <input type="text" value="${tbJpStudentInfo.applyDate}" class="ipt calendars" name="tbJpStudentInfo.applyDate">
                    </li>
                    <li>
                        <p>培训车型</p>
                        <%--<s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"--%>
                                   <%--listKey="key" id="trainType" listValue="value" headerKey="" headerValue="请选择" name="tbJpStudentInfo.trainType"--%>
                                  <%--theme="simple"></s:select>--%>
                        <input type="text" value="${tbJpStudentInfo.trainType}" class="ipt" name="tbJpStudentInfo.trainType" readonly="true">
                    </li>
                    <li>
                        <p>付费模式</p>
                        <input type="text" name="tbJpStudentInfo.charId" value="${tbJpStudentInfo.charId}" class="ipt">
                    </li>
                    <li>
                        <p>申请类型</p>
                        <s:select list="#{'0':'初领','1':'增领'}"
                                  listKey="key" id="trainType" listValue="value" name="tbJpStudentInfo.busiType"
                                  theme="simple"></s:select>
                    </li>
                    <li id="perdriType">
                        <p>原准驾车型</p>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  listKey="key" id="trainType" listValue="value" headerKey="" headerValue="请选择" name="tbJpStudentInfo.perdriType"
                                  theme="simple"></s:select>
                    </li>
                </ul>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath %>";
    var staffId = "${param.staffId}";

</script>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");

        /*性别选择*/
        $(".sex h5 i").click(function(){
            $(this).addClass("active").parents(".sex h5").siblings().find("i").removeClass("active");

        });

        if($("select[name='tbJpStudentInfo.busiType']  option:selected").text() == "初领"){
            $("#perdriType").hide();
        }

        $("select[name='tbJpStudentInfo.busiType']").change(function () {
            if($("select[name='tbJpStudentInfo.busiType']  option:selected").text() == "初领"){
                $("select[name='tbJpStudentInfo.perdriType'] option[value='']").attr("selected",true)
                $("#perdriType").hide();
            }else if($("select[name='tbJpStudentInfo.busiType']  option:selected").text() == "增领"){
                $("#perdriType").show();
            }
        })

    });
</script>
</body>
</html>
