<%@ page language="java" pageEncoding="UTF-8"%>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
    <title>开通购物卡</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/keyboard/vk_loader.js?vk_layout=CN%20Chinese%20Simpl.%20Pinyin&vk_skin=flat_gray" ></script>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script>
        var basePath = "<%=basePath%>";

    </script>
</head>
<body>
<section class="code-pay zc">
    <!--充值内容-->
    <article>
        <!--头部-->
        <header>
            <img src="<%=basePath%>images/supermarket/zc.png" alt="">
            <p>请输入您的个人信息<br/>
                <span>注册后更方便您的操作喔</span></p>
            <a href="javascript:history.go(-1)"><input  type="button" value="返回"></a>
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form id="openCard" method="post">
                <ul class="zc-text">
                    <li><span>*</span>姓名<input type="text" value="" placeholder="请输入姓名" id="name" name="staff.staffName" onclick="test(this);"></li>
                    <li><span>*</span>手机号<input type="text" value="" placeholder="请输入手机号" id="mobile" name="staff.reference" onclick="test(this);"></li>
                    <li><span>*</span>确认<input type="text" value="" placeholder="请确认输入手机号" id="mobile2" onclick="test(this);"></li>
                    <li><span>*</span>登陆密码<input type="password" value="" placeholder="请输入密码" id="password" name="password" onclick="test(this);"></li>
                    <li><span>*</span>支付密码<input type="password" value="" placeholder="请输入密码" id="pay_password" name="paymentCode" onclick="test(this);"></li>
                    <li><span></span>身份证<input type="text" value="" placeholder="请输入身份证号(选填)" id="sfz" name="staff.staffIdentityCard" onclick="test(this);"></li>
                    <li><span>*</span>购物卡号<input type="text" value="" placeholder="请输入购物卡号" id="gift_card" name="giftCards.cardNumber" onclick="test(this);"></li>
                    <li><span>*</span>推荐人<input type="text" value="" placeholder="请输入推荐人手机号" id="ren" name="iphone" onclick="test(this);"></li>
                </ul>
                <input type="button" value="确认" id="zc-btn">
            </form>
            <div class="cz-jp333">
                <table class="jp">
                    <tr>
                        <td><input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)"/></td>
                        <td><input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"/></td>
                        <td><input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"/></td>
                    </tr>
                    <tr>
                        <td><input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"/></td>
                        <td><input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"/></td>
                        <td><input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"/></td>
                    </tr>
                    <tr>
                        <td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"/></td>
                        <td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"/></td>
                        <td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"/></td>
                    </tr>
                    <tr>
                        <td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"/></td>
                        <td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"/></td>
                        <td><input id="Buttond" type="button" value="." onclick="return dian()"/></td>
                    </tr>
                    <tr>
                        <td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"/></td>
                        <td colspan="2"><input id="btnEnter" type="button" value="确定" /></td>
                    </tr>
                </table>
            </div>
        </figure>
        <!--内容 end-->
    </article>
    <div id="softkey"></div>
    <!--充值 end-->
</section>

<script type="text/javascript">

    function test(obj){
        var left = 325;


        VirtualKeyboard.toggle($(obj).attr("id"), 'softkey');
        $("#kb_langselector,#kb_mappingselector,#copyrights").css("display", "none");
        $("#softkey").attr("style","margin-left:-"+left+"px;");

    }
</script>

<script language="javascript" type="text/javascript">
    /*表单验证*/

    /*姓名不能为空*/
    $("#zc-btn").click(function () {
            //姓名
            var name=document.getElementById("name").value;
            if(name == ""){
                alert("姓名不能为空！");
                return false;
            }
            //手机号
            var mobile = document.getElementById("mobile").value;
            var mobile2 = document.getElementById("mobile2").value;
            var myreg = /^1(3|4|5|6|7|8|9)\d{9}$/;
            var isIDCardReg=/(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
            if(mobile == ''){
                alert("请输入手机号！");
                return false;
            }else if(!myreg.test(mobile)){
                alert("请输入正确格式电话号！");
                return false;
            }else if(mobile2 != mobile){
                alert("二次手机号输入不一致，请重新输入！");
                return false;
            }
            //密码
            var password=document.getElementById("password").value;
            if(password == ""){
                alert("登陆密码不能为空！");
                return false;
            }else if(password.length<6){
                alert("登陆密码长度不安全！")
                return false;
            }
            var pay_password = document.getElementById("pay_password").value;
            if(pay_password == ""){
                alert("支付密码不能为空！");
                return false;
            }else if(!pay_password.match(/^\d{6}$/)){
                alert("支付密码必须为6位数字！");
                return false;
            }

            var idCard = document.getElementById("sfz").value;
        if(idCard == ''){
//            alert("请输入身份证号！");
//            return false;
        }else
            if(!isIDCardReg.test(idCard)){
            alert("请输入正确格式身份证号！");
            return false;
        }
            var giftCard = document.getElementById("gift_card").value;
            if(giftCard == ''){
                alert("请输入购物卡号！");
                return false;
            }else if(giftCard.length!=20){
                alert("请输入正确格式购物卡号！");
                return false;
            }
            //推荐人
            var ren=document.getElementById("ren").value;
            if(ren == ''){
                alert("请输入推荐人手机号！");
                return false;
            }else if(!myreg.test(ren)){
                alert("请输入正确格式推荐人手机号！");
                return false;
            }

            var dataFrom = $("#openCard").serialize();
            var cardNum =  $("#gift_card").val();
            var mobile= $("#mobile").val();
            var url =basePath+"ea/giftcard/sajax_ea_registered.jspa";
            $.ajax({
                url:encodeURI(url),
                type:"post",
                data:dataFrom,
                async:false,
                dataType:"json",
                success:function (data) {
                    var member = eval("("+data+")");
                    var isRegister = member.isRegister;
                    var mes = member.message;
                    if(isRegister==false){
                        alert(mes);
                    }else if(isRegister==true){
                        alert(mes);
                        window.location.href=basePath+"/ea/sm/ea_joinVip.jspa?cardNum="+cardNum+"&account="+mobile;
                    }
                }
            });
    })

</script>
</body>
</html>
