<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <title>修改活动</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/zane-calendar.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script type="application/javascript"
            src="<%=basePath%>js/ea/finance/brokerage/activity/zane-calendar.min.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        /*var pageNumber = 0;*/
    </script>
</head>
<body>

<section class="commission add-act">
    <!--修改活动-->
    <article>
        <!--头部-->
        <header>
            <p id="head">修改${activityType=="00" ?"普通":"特价"}活动</p>
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <div class="head">
                <input type="button" value="关闭" id="close">
                <input type="button" value="保存" id="sure">
            </div>
            <div class="grid-1">
                <form action="" enctype="multipart/form-data" method="POST" id="updateActivity">
                    <input type="hidden" value="${activity[0]}" name="activity.activityId"><%--活动id--%>
                    <ul class="left">
                        <li>
                            <p>活动名称</p>
                            <h5>
                                <input type="text" value="${activity[1]}" name="activity.activityName"
                                       id="activityName">
                            </h5>
                        </li>
                        <li>
                            <p>起时间</p>
                            <h5>
                                <input type="text" name="startTime" id="begin" value="${activity[4]}">
                            </h5>
                        </li>
                        <li>
                            <p>止时间</p>
                            <h5>
                                <input type="text" name="endTime" id="end" value="${activity[5]}">
                            </h5>
                        </li>
                        <li>
                            <p>活动图片</p>
                            <!--添加图片-->
                            <div class="up_pic eva">
                                <div class="picture">
                                    <img src="<%=basePath%>${activity[3]}">
                                </div>
                                <div class="btn_ btn_3">
                                    <img src="<%=basePath%>images/ea/finance/brokerage/images/ico-tj.png" alt=""
                                         id="image_box">
                                </div>
                            </div>
                        </li>
                        <li>
                            <p>活动详情</p>
                            <textarea id="xq" name="activity.activityDescribe">${activity[2]}</textarea>
                        </li>
                    </ul>
                </form>
            </div>

            <ul class="footer">
                <li>
                    修改活动责任人<input type="text" readonly="readonly" value="${cac.staffName}">
                </li>
                <li>
                    修改活动日期<input type="text" readonly="readonly" value="${myDate}">
                </li>
            </ul>
        </figure>
        <!--内容 end-->
    </article>
    <input type="hidden" value="${flag}" id="flag">
    <!--修改活动 end-->
</section>
<script>
    //上传照片(提交服务器时需判断input=file 是否有空值，遍历删除；)
    var click_times = 0;
    $(".up_pic .btn_3").click(function () {
        if ($(".h_img").length == 1) {
            alert("您最多只能上传1张图片")
        } else {
            click_times++;
            var _id = "click_" + click_times;
            var t = '<div class="upload_img" id=' + _id + '><div class="img_box"><img src="" alt=""></div><input type="file" id="picture" accept="image/*" name ="file" style="opacity: 0;width: 3rem;margin-top: 1.5rem;"><i class="del_upimg"></i></div>'
            $(".up_pic .btn_3").before(t);
            var $id = $('#' + _id);
            $id.hide();
            var $id_inp = $('#' + _id + ' ' + 'input');
            $id_inp.click();
            //Input file选择图片上传事件
            $id_inp.one("change", function () {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function () {
                    // 通过 reader.result 来访问生成的 DataURL
                    var url = reader.result;
                    $id.find("img").attr("src", url)
                };
                reader.readAsDataURL(file);
                $id.show();
                $id.addClass("h_img");
                $(".picture").attr("style","display:none;");//隐藏div
            });
        }

    });
    //上传图片删除功能
    $(".up_pic").on("click", ".del_upimg", function (e) {
        e.stopPropagation();
        $(this).parent().detach();
        $(".picture").attr("style","display:block;");//显示div
    });

</script>
<script type="application/javascript">
    $(function () {
        //标识判断
        var flag = $("#flag").val()
        if (flag == '01') {//设置数据为只读
            $("form input").each(function () {
                $(this).attr('readonly', 'readonly')
            });
            $("#sure").attr("disabled", "disabled");
            $("#head").html("查看活动");
        }

        var start = '${activity[4]}';
        var end = '${activity[5]}';
        /*时间选择*/
        function getNow(s) {
            return s < 10 ? '0' + s : s;
        }

        var myDate = new Date();
        //获取当前年
        var year = myDate.getFullYear();
        //获取当前月
        var month = myDate.getMonth() + 1;
        //获取当前日
        var date = myDate.getDate();
        var h = myDate.getHours();       //获取当前小时数(0-23)
        var m = myDate.getMinutes();     //获取当前分钟数(0-59)
        var s = myDate.getSeconds();
        var now = year + '-' + getNow(month) + "-" + getNow(date) + " " + "0" + ':' + "0" + ":" + "0";
        var min = year - 5 + '-' + getNow(month) + "-" + getNow(date) + " " + "0" + ':' + "0" + ":" + "0";
        var max = year + 5 + '-' + getNow(month) + "-" + getNow(date) + " " + "0" + ':' + "0" + ":" + "0";
        zaneDate({
            elem: '#begin',
            format: 'yyyy-MM-dd HH:mm:ss',
            showtime: true,
            begintime: start,
            min: now,
            max: max
        });
        zaneDate({
            elem: '#end',
            format: 'yyyy-MM-dd HH:mm:ss',
            showtime: true,
            begintime: end,
            min: now,
            max: max
        })
    })
    //关闭修改窗口
    $("#close").click(function () {
        if (confirm("您确定要关闭本页吗？")) {
            window.opener = null;
            window.open('', '_self');
            window.close();
        } else {
        }
    })
    /*ajax异步请求防止重复提交*/
    //设置一个对象来控制是否进入AJAX过程
    var post_flag = false;
    //保存数据
    $("#sure").click(function () {
        //参数校验
        if ($("#activityName").val() == null || $("#activityName").val() == "") {
            alert("请输入活动名称!")
            return "";
        }
        var begin = $("#begin").val()
        var end = $("#end").val()
        var oDate1 = new Date(begin);
        var oDate2 = new Date(end);
        if (oDate1.getTime() > oDate2.getTime()) {
            alert("活动起始日期不能比结束日期晚!")
            return "";
        } else if (oDate1.getTime() == oDate2.getTime()) {
            alert("活动起始日期不能等于结束日期!")
            return "";
        } else {

        }
        if ($("#xq").val() == null || $("#xq").val() == "") {
            alert("请输入活动详情!")
            return "";
        }
        if ($("#xq").val().length > 100) {
            alert("活动描述字数范围在100以内!");
            return "";
        }
      /*  if ($("#picture").val() == null || $("#picture").val() == "") {
            alert("请添加图片!")
            return "";
        }*/

        //如果正在提交则直接返回，停止执行
        if (post_flag) {
            alert("正在提交请稍后...")
            return;
        }
        //标记当前状态为正在提交状态
        post_flag = true;
        $.ajax({
            type: "POST",
            url: basePath + "ea/activityPrice/sajax_ea_updateActivity.jspa",
            cache: false,
            data: new FormData($('#updateActivity')[0]),
            processData: false,
            contentType: false,
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                post_flag = false; //在提交成功之后将标志标记为可提交状态
                if (code == "200") {
                    alert("修改成功！");
                    window.opener.location.reload();
                    window.close();
                    location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa";
                } else {
                    //未登录
                    location.href = basePath + "page/ea/not_login.jsp";
                }
            },
            error: function (data) {
                alert("修改异常！");
            },
            dateType: "json"
        });

    })
</script>
</body>
</html>