<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/swiper-3.3.1.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/apprise/student.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/driving/addapprise.js"></script>
    <title>发表评价</title>
</head>
<body>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    var studentId="${studentId}" ; //学员id
    var teacherId="${teacherId}"; //教练id
    var xingt  ; //教学星星数
    var xingo  ;  //服务态度星星数
    var form_data = [];//存放上传图片的文件的数组，需要在提交的时候遍历
    var tubas=[];
    var etoId = "${param.etoId}";
</script>
<header class="com_head">
    <a onclick="javascript:window.history.go(-1);return false;" class="back"></a>
    <h1>发表评价</h1>
</header>
<div class="wrap_page">
    <form  id="formd" method="post" enctype="multipart/form-data" >
    <div class="evaluate_wrap">
        <div class="eval_T_wrap clearfix">
            <%--<img src="<%=basePath%>/images/WFJClient/student/img.png" class="eval_ico" alt="" >--%>
            <textarea class="eval_text" placeholder="亲！可以对教练教学过程进行评价哦~" name="tbly.evaluateContent"></textarea>

        </div>
        <div class="eval_img clearfix">
            <a href="javascript:;" class="eval_img_box add_btn">
                <span class="add_img_hint">添加图片</span>
                <input type="file" accept="image/*" class="up_btn" id="img_logo">
            </a>
        </div>
        <div class="eval_star_wrap">
            <div class="wval_tit">教练评分</div>
            <div class="eval_star_box clearfix" name="tbly.serviceScore">
                <div class="star_L">服务态度</div>
                <div class="star_R clearfix" id="xingone" >
                    <i></i>
                    <i></i>
                    <i></i>
                    <i></i>
                    <i></i>
                </div>
            </div>
            <div class="eval_star_box clearfix" name="tbly.teachleveScore">
                <div class="star_L">教学水平</div>
                <div class="star_R clearfix" id="xingtwo">
                    <i></i>
                    <i></i>
                    <i></i>
                    <i></i>
                    <i></i>
                </div>
            </div>
        </div>
        <div class="eval_type_wrap clearfix" name="tbly.evaluateType">
            <div class="eval_type eval_cur"><a href="javascript:;" class="good" >好评</a></div>
            <div class="eval_type "><a href="javascript:;" class="middle"  >中评</a></div>
            <div class="eval_type"><a href="javascript:;" class="negative">差评</a></div>
        </div>
    </div>
        <input type="button" onclick="addApprise()" class="eval_btn" value="发表评价" />
    </form>
</div>
<script>
    $(function() {
        //上传图片选择图片显示
        $(".up_btn").change(function() {
            if (this.files.length > 0) {
                var file = this.files[0];
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function() {
                    var url = reader.result;
                    var _html = '<div class="eval_img_box"><i class="dele_btn"></i><img src=' + url + ' alt=""></div>';
                    tubas.push(url);
                    if (form_data.length < 5) {
                        $(".add_btn").before(_html);
                        form_data.push(file);
                        $(".add_img_hint").text('' + form_data.length + '/5');
                        if (form_data.length == 5) {
                            $(".add_btn").hide();
                        }
                    } else {
                        console.log("超过5张")
                    }
                }
            }else{
                console.log("没有选图片")
            }

        })


        //上传图片删除按钮
        $(document).on("click", ".dele_btn", function() {
            var _index = $(".eval_img .eval_img_box").index($(this).parent());
            //console.log(_index);
            $(this).parent().detach();
            form_data.splice(_index,1);
            console.log(form_data);
            if(form_data.length>0){
                $(".add_img_hint").text('' + form_data.length + '/5');
            }else{
                $(".add_img_hint").text("添加图片");
            }

        })
        //教练评分星星
        $("#xingone i").click(function(){
            var _index = $(this).parent().find("i").index($(this));
             xingo=_index+1;
            $(this).parent().find("i").eq(_index).prevAll().addClass("star_light").end().nextAll("i").removeClass().end().addClass("star_light");
            $(this).parent().attr("data-valnum",_index+1)
        })
        //教练评分星星
        $("#xingtwo i").click(function(){
            var _index = $(this).parent().find("i").index($(this));
             xingt=_index+1;
            $(this).parent().find("i").eq(_index).prevAll().addClass("star_light").end().nextAll("i").removeClass().end().addClass("star_light");
            $(this).parent().attr("data-valnum",_index+1)
        })
        //教练评价
        $(".eval_type").click(function(){
            $(this).addClass("eval_cur").siblings().removeClass("eval_cur");
        })
    })

</script>
</body>

</body>
</html>
