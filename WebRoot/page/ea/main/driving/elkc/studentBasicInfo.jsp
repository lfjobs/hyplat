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

    TbJpStudentInfo studentInfo = (TbJpStudentInfo) session.getAttribute("stu");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/base.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/calendar.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/new_style.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/ea/elkc/popup.css">
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/setHtmlFont.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/calendar.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/driving/elkc/studentManage.js"></script>
    <title>学员基本资料</title>
</head>
<body>
<header>
    <ul>
        <li style="width: 10%;">
            <%--<a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/ea/driving/elkc/left_jt.png"></a>--%>
        </li>
        <li style="width: 90%;">基本资料</li>
        <li id="saveStudent" style="width: 10%;">保存</li>
    </ul>
</header>

<div class="overlay">
    <div class="save_info">
        <img src="<%=basePath%>/images/ea/driving/elkc/popup_ico.png" class="save_top" alt="">
        <div class="save_text">您的信息已经保存成功！</div>
        <div class="save_btn">
            <a href="<%=basePath%>/driving/elkc/ea_studentBasicInfoPage.jspa?staffId=${param.staffId}" class="go_on">继续修改</a>
            <a href="javascript:history.go(-1)" class="back_up">返回上一级</a>
        </div>
    </div>
</div>

<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="xy_basics">
                <form id="studentForm" method="post"
                      enctype="multipart/form-data">
                <ul>
                    <li>
                        <p>头像</p>
                        <div class="img_box">
                            <img id="image_box" src="<%=basePath%>${tbJpStudentInfo.photo!= ''&& tbJpStudentInfo.photo!= null ?tbJpStudentInfo.photo:"/images/ea/driving/elkc/head.png"}" alt="会员头像">
                            <input type="file" accept="image/*" id="head_img">
                            <input type="hidden" name="tbJpStudentInfo.photo" value="${tbJpStudentInfo.photo}">
                        </div>
                    </li>
                    <li>
                        <p>姓名</p>
                        <!--<img src="<%=basePath%>/images/ea/driving/elkc/right.png" class="right">-->
                        <!--<h5>某某某</h5>-->
                        <input type="hidden" value="${tbJpStudentInfo.studentKey}" name="tbJpStudentInfo.studentKey">
                        <input type="hidden" value="${tbJpStudentInfo.studentId}" name="tbJpStudentInfo.studentId">
                        <input type="hidden" value="${tbJpStudentInfo.companyId}" class="ipt" name="tbJpStudentInfo.companyId">
                        <input type="hidden" value="${tbJpStudentInfo.staffId == null || tbJpStudentInfo.staffId == '' ? param.staffId:tbJpStudentInfo.staffId }" name="tbJpStudentInfo.staffId">
                        <input type="text" value="${tbJpStudentInfo.name}" class="ipt" name="tbJpStudentInfo.name">
                    </li>
                    <li>
                        <p>性别</p>
                        <div class="sex">
                            <h5><i class="active"></i><span>男</span></h5>
                            <h5><i></i><span>女</span></h5>
                            <input type="hidden" value="${tbJpStudentInfo.sex == null || tbJpStudentInfo.sex == '' ? "1": tbJpStudentInfo.sex}" name="tbJpStudentInfo.sex">
                        </div>
                    </li>
                    <li>
                        <p>出生日期</p>
                        <input type="text" value="${tbJpStudentInfo.brith}" name="tbJpStudentInfo.brith" class="ipt calendars">
                    </li>
                    <li>
                        <p>国籍</p>
                        <h5>中国</h5>
                        <input type="hidden"  value="${tbJpStudentInfo.nationality == null || tbJpStudentInfo.nationality ==""?"中国":tbJpStudentInfo.nationality}" name="tbJpStudentInfo.nationality">
                    </li>
                    <li>
                        <p>证件类型</p>
                        <div class="cardType">
                            <h5><i class="active"></i><span>身份证</span></h5>
                            <h5><i></i><span>护照</span></h5>
                            <h5><i></i><span>军官证</span></h5>
                            <h5><i></i><span>其他</span></h5>
                            <input type="hidden" value="${tbJpStudentInfo.cardType == null || tbJpStudentInfo.cardType == '' ? "1": tbJpStudentInfo.cardType}" name="tbJpStudentInfo.cardType">
                        </div>
                    </li>
                    <li>
                        <p>证件号码</p>
                        <input type="text" value="${tbJpStudentInfo.cardNum}" name="tbJpStudentInfo.cardNum" class="ipt">
                    </li>
                    <li>
                        <p>是否本地</p>
                        <div class="isLocal">
                            <h5><i class="active"></i><span>是</span></h5>
                            <h5><i></i><span>否</span></h5>
                            <input type="hidden" value="${tbJpStudentInfo.isLocal == null || tbJpStudentInfo.isLocal == '' ? "0": tbJpStudentInfo.isLocal}" name="tbJpStudentInfo.isLocal">
                        </div>
                    </li>
                    <li class="isHide">
                        <p>暂住证号</p>
                        <input type="text" value="${tbJpStudentInfo.tempCardNo}" name="tbJpStudentInfo.tempCardNo" class="ipt">
                    </li>
                    <li class="isHide">
                        <p>居住地址</p>
                        <input type="text" value="${tbJpStudentInfo.stayAddress}" name="tbJpStudentInfo.stayAddress" class="ipt">
                    </li>
                    <li>
                        <p>家庭地址</p>
                        <input type="text" value="${tbJpStudentInfo.address}" name="tbJpStudentInfo.address" class="ipt">
                    </li>
                    <li>
                        <p>手机号</p>
                        <input type="text" value="${tbJpStudentInfo.phone}" name = "tbJpStudentInfo.phone" class="ipt">
                    </li>
                </ul>
                </form>
            </div>

        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath %>";
    var staffId = "${param.staffId}"
</script>
<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");

        /*性别选择*/
        $(".sex h5 i").click(function(){
            $(this).addClass("active").parents(".sex h5").siblings().find("i").removeClass("active");
            if($(".sex h5 .active").next().text() == "男"){
                $("input[name='tbJpStudentInfo.sex']").val("1")
            }else {
                $("input[name='tbJpStudentInfo.sex']").val("2")
            }
        });

        $(".cardType h5 i").click(function(){
            $(this).addClass("active").parents(".cardType h5").siblings().find("i").removeClass("active");
            if($(".cardType h5 .active").next().text() == "身份证"){
                $("input[name='tbJpStudentInfo.cardType']").val("1")
            }else if($(".cardType h5 .active").next().text() == "护照"){
                $("input[name='tbJpStudentInfo.cardType']").val("2")
            }else if($(".cardType h5 .active").next().text() == "军官证"){
                $("input[name='tbJpStudentInfo.cardType']").val("3")
            }else if($(".cardType h5 .active").next().text() == "其他"){
                $("input[name='tbJpStudentInfo.cardType']").val("4")
            }
        });

        $(".isLocal h5 i").click(function(){
            $(this).addClass("active").parents(".isLocal h5").siblings().find("i").removeClass("active");
            if($(".isLocal h5 .active").next().text() == "是"){
                $("input[name='tbJpStudentInfo.tempCardNo']").val("");
                $("input[name='tbJpStudentInfo.stayAddress']").val("");
                $("input[name='tbJpStudentInfo.isLocal']").val("1");
                $(".isHide").hide()
            }else {
                $("input[name='tbJpStudentInfo.isLocal']").val("0")
                $(".isHide").show()
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
