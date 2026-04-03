<%@ page import="hy.ea.bo.DrivingSchool.TbJpStudentInfo" %>
<%@ page import="com.opensymphony.xwork2.ActionContext" %>
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
        <title>教练基本资料</title>
        <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
        <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/popup.css">
        <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/calendar.min.css">
        <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/new_style.css">
        <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/calendar.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/studentManage.js"></script>
        <script type="text/javascript">
            var type = "${param.type}";
            $(function () {
                if(type == "el"){
                    var obj = document.getElementById("ret");
                    obj.setAttribute("href", "#");
                    obj.setAttribute("onclick", "retAndroid()");
                }
            })
            //安卓，苹果返回
            function retAndroid(){
                try{
                    Android.finishWeb();
                }catch(err){
                    $("#ret").removeAttr("onclick");
                    $("#ret").attr("onclick","javascript: window.history.go(-1);return false;");
                    $("#ret").trigger('click');
                }
            }
        </script>
</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <%--<a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/driving/elkc/left_jt.png"></a>--%>
        </li>
        <li style="width: 90%;">基本资料</li>
        <li id="saveCoach" style="width: 10%;">保存</li>
    </ul>
</header>

<div class="overlay">
    <div class="save_info">
        <img src="<%=basePath%>/images/ea/driving/elkc/popup_ico.png" class="save_top" alt="">
        <div class="save_text">您的信息已经保存成功！</div>
        <div class="save_btn">
            <a href="<%=basePath %>/driving/elkc/ea_coachInfo.jspa?staffId=${param.staffId}" class="go_on">继续修改</a>
            <a href="javascript:history.go(-1)" class="back_up" id="ret">返回上一级</a>
        </div>
    </div>
</div>

<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="xy_basics">
                <form id="coachForm" method="post"
                      enctype="multipart/form-data">
                <ul>
                    <li>
                        <p>头像</p>
                        <div class="img_box">
                            <img id="image_box" src="<%=basePath%>${tbJpTeacher.photo!= ''&& tbJpTeacher.photo!= null ?tbJpTeacher.photo:"/images/ea/driving/elkc/head.png"}" alt="会员头像">
                            <input type="file" accept="image/*" id="head_img">
                            <input type="hidden" name="tbJpTeacher.photo" value="${tbJpTeacher.photo}">
                        </div>
                    </li>
                    <li>
                        <p>姓名</p>
                        <!--<img src="images/right.png" class="right">-->
                        <!--<h5>某某某</h5>-->
                        <input type="hidden" name="tbJpTeacher.teacherKey" value="${tbJpTeacher.teacherKey}">
                        <input type="hidden" name="tbJpTeacher.teacherId" value="${tbJpTeacher.teacherId}">
                        <input type="text" value="${tbJpTeacher.name}" name="tbJpTeacher.name" class="ipt">
                    </li>
                    <li>
                        <p>性别</p>
                        <div id="sex" class="sex">
                            <h5><i class="active"></i><span>男</span></h5>
                            <h5><i></i><span>女</span></h5>
                            <input type="hidden" value="${tbJpTeacher.sex == null || tbJpTeacher.sex == '' ? "1": tbJpTeacher.sex}" name="tbJpTeacher.sex">
                        </div>
                    </li>
                    <li>
                        <p>身份证号</p>
                        <input type="text" value="${tbJpTeacher.idcard}" name="tbJpTeacher.idcard" class="ipt">
                    </li>
                    <li>
                        <p>驾驶证号</p>
                        <input type="text" value="${tbJpTeacher.drilicence}" name="tbJpTeacher.drilicence" class="ipt">
                    </li>
                    <li>
                        <p>手机号</p>
                        <input type="text" value="${tbJpTeacher.mobile}" name="tbJpTeacher.mobile" class="ipt">
                    </li>
                    <li>
                        <p>职业资格号</p>
                        <input type="text" value="${tbJpTeacher.occupationno}" name="tbJpTeacher.occupationno" class="ipt">
                    </li>
                    <li>
                        <p>驾驶证初领日期</p>
                        <input type="text" value="${tbJpTeacher.fstdrilicdate}" name="tbJpTeacher.fstdrilicdate" class="ipt calendars">
                    </li>
                    <li>
                        <p>职业资格等级</p>
                        <div id="occupationlevel" class="sex">
                            <h5><i class="active"></i><span>一级</span></h5>
                            <h5><i></i><span>二级</span></h5>
                            <h5><i></i><span>三级</span></h5>
                            <h5><i></i><span>四级</span></h5>
                            <input type="hidden" value="${tbJpTeacher.occupationlevel}" name="tbJpTeacher.occupationlevel">
                        </div>
                    </li>
                    <li>
                        <p>准驾科目</p>
                        <s:select list="#{'1':'科目一','2':'科目二','3':'科目三','4':'科目四'}"
                                  class="isHide" listKey="key" id="subject" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.subject"
                                  theme="simple"></s:select>
                    </li>
                    <li>
                        <p>准驾车型</p>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                   listKey="key" id="trainType" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.dripermitted"
                                  theme="simple"></s:select>
                    </li>
                    <li>
                        <p>准教车型</p>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  listKey="key" id="trainType" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.teachpermitted"
                                  theme="simple"></s:select>
                    </li>
                    <li style="display: none">
                        <p>入职时间</p>
                        <input type="text" value="${tbJpTeacher.hiredate}" name="tbJpTeacher.hiredate" class="ipt">
                    </li>
                    <li style="display: none">
                        <p>离职时间</p>
                        <input type="text" value="${tbJpTeacher.leavedate}" name="tbJpTeacher.leavedate" class="ipt">
                    </li>
                    <li>
                        <p>备注</p>
                        <input type="text" name="tbJpTeacher.remark" value="${tbJpTeacher.remark}" class="ipt">
                    </li>
                    <li>
                        <p>地址</p>
                        <input type="text" name="tbJpTeacher.address" value="${tbJpTeacher.address}" class="ipt">
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
        $("#sex h5 i").click(function(){
            $(this).addClass("active").parents("#sex h5").siblings().find("i").removeClass("active");
            if($("#sex h5 .active").next().text() == "男"){
                $("input[name='tbJpTeacher.sex']").val("1")
            }else {
                $("input[name='tbJpTeacher.sex']").val("2")
            }
        });

        $("#occupationlevel h5 i").click(function(){
            $(this).addClass("active").parents("#occupationlevel h5").siblings().find("i").removeClass("active");
            if($("#occupationlevel h5 .active").next().text() == "一级"){
                $("input[name='tbJpTeacher.occupationlevel']").val("1")
            }else if($("#occupationlevel h5 .active").next().text() == "二级"){
                $("input[name='tbJpTeacher.occupationlevel']").val("2")
            }else if($("#occupationlevel h5 .active").next().text() == "三级"){
                $("input[name='tbJpTeacher.occupationlevel']").val("3")
            }else if($("#occupationlevel h5 .active").next().text() == "四级"){
                $("input[name='tbJpTeacher.occupationlevel']").val("4")
            }
        });

        //头像上传显示
        $('#head_img').change(function() {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onload = function() {
                // 通过 reader.result 来访问生成的 DataURL
                var url = reader.result;
                setImageURL(url);
            };
            reader.readAsDataURL(file);
        });

        var image = new Image();

        function setImageURL(url) {
            document.getElementById("image_box").src = url;
        }


    });
</script>

</body>
</html>