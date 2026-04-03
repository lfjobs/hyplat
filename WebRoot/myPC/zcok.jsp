<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>注册成功</title>

    <link href="<%=basePath %>myPC/css/style.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath %>myPC/js/jquery-1.9.1.min.js" type="text/javascript"></script>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="<%=basePath %>myPC/js/html5shiv.min.js"></script>
    <script src="<%=basePath %>myPC/js/respond.min.js"></script>
    <![endif]-->
</head>
<body style="background: #fff">
    <div id="header" class="login_header">
        <ul>
            <li class="logo">
                <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区"><img src="<%=basePath %>myPC/images/wfj.png" alt="" class="log"></a>
            </li>
            <li class="name login_name">
                <div>
                    <h3>恭喜您注册成功</h3>

                </div>
            </li>
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&search=中联园区">
            <li class="login login_login">
                <img src="<%=basePath %>myPC/images/return2.png" alt="">
                <div>
                    <h3>返回首页</h3>
                </div>
                
            </li>
            </a>
        </ul>
    </div>
    <div class="content login_con xiugai">
        <div>
           <p>修改密码成功，是否返回登录页面</p>
            <a href="<%=basePath%>myPC/login.jsp"><input type="button" value="返回登录页面"></a>
        </div>
    </div>
   <!-- <footer>
        <div class="text">
            <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p>
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室热线<span>电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </footer>-->
<script>

    function check(form) {
        //手机
        var x=document.getElementById("tel").value;
        var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-3]{1})|(18[5-9]{1}))+\d{8})$/;
        if(x == ""){
            alert("请输入电话号！");
            return false;
        }else if(!myreg.test(x)){
            alert("请输入正确格式电话号！");
            return false;
        }
        //密码
        var y=document.getElementById("password").value;
        if(y == ""){
            alert("请输入密码！");
            return false;
        }

    }
</script>
<script>
    $(document).ready(function(){

        $(".ind_con_head ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });


        //绑定滚动条事件
        $(window).bind("scroll", function () {
            var sTop = $(window).scrollTop();
            var sTop = parseInt(sTop);

            if (sTop >= 1100) {
                $(".return").slideDown();
                $(".return").show();
            }
            else {

                $(".return").hide();

            }
        });
    })
</script>
</body>
</html>