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
    <title>充值</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/supermarket/style-cs.css">
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/marketing/supermarket/integralTopup.js" type="text/javascript"></script>
</head>
<script>
    var basePath="<%=basePath%>";
    var posNum="${param.posNum}";
    var ccompanyID = "${ccompanyID}";
    var comID = "${comID}";
    var companyName = "${companyName}";
    var fhform = "${param.fhform}";
</script>
<body>

<section class="code-pay cz">
    <!--充值内容-->
    <article>
        <!--头部-->
        <header>
            <img src="<%=basePath%>images/supermarket/cz.png" alt="" style="width: 150px">
            <p>以下是您的个人信息<br/>
                <span>在线充值更方便</span></p>
            <a href="javascript: window.history.go(-1);"><input type="button" value="返回"></a>
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form  id  = "topUpForm" name = "topUpForm" class="form1" method="post"  target="hidden" action="<%=basePath%>ea/sm/ea_payErCodeByIgl.jspa?posNum=${param.posNum}&ccompanyID=${ccompanyID}">
            <ul class="cz-text">
                <li>姓名<input type="text" id="name" name="staffName" value="${param.staffName eq null?staff.staffName:param.staffName}" readonly="readonly"></li>
                <li>手机号<input type="text" id ="phone" name="phone" value="${staff.reference}" readonly="readonly"></li>
                <li>身份证<input type="text" id = "IdCard" name="IdCard" value="${staff.staffIdentityCard}" readonly="readonly">
                    <input id="cardNum" type="hidden" name="cardNum" value="${cardNum}" /></li>
            </ul>
            <div class="cz-je">
                <p>充值金额：<input type="text" onkeyup="checkBlus()" name="totalMoney" id="txtNum">元</p>
                <ul>
                    <li><span>100</span></li>
                    <li><span>200</span></li>
                    <li><span>300</span></li>
                    <li><span>500</span></li>
                </ul>
                <input type="submit" name="submit" style="display:none;"/>
                <input type="button" value="确认充值" id="cz">
            </div>
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
            <iframe name="hidden" width="0" height="0" />
        </figure>
        <!--内容 end-->
    </article>
    <!--充值 end-->
</section>
<div class="mm-alert">
    <div>
        <h1>请重新输入</h1>
        <h5>你输入的实付金额少于应收金额</h5>
        <input type="button" value="确定">
    </div>
</div>
</body>
</html>