<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>购物卡</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/supermarket/giftCard.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/supermarket/gift.css" rel="stylesheet" type="text/css" />

    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var  pageNumber = "${pageNumber}";
        var search='${search}';
        var status = '${cm.status}';
        var carmID = "";
        var model = "";
        var methodPayment="";
        var staffid= "";
        var jum ="";
        var pageNumByBill=1;
    </script>
</head>
<body>
<div style="margin-top:10px;margin-left:10px;display:none;"
     class="query"><form id="SearchForm" name="SearchForm" method="post">
    姓名查询：<input type="text" style="width:90px;height:18px;" name="staff.staffName"  id="name"/>
    手机号查询：<input type="text" style="width:90px;height:18px;" name="staff.reference"  id="phoneNo"/>
    身份证号查询：<input type="text" style="width:90px;height:18px;" name="staff.staffIdentityCard"  id="idCard"/>
    购物卡号查询：<input type="text" style="width:90px;height:18px;" name="giftCards.cardNumber"  id="cardNumber"/>
    <input id="toSearch" type="button" class="input-button" value="  查询   "   style="margin:0px;margin-left:5px;" />
</form>

</div>
<div class="main_main">
    <table class="JQueryflexme">
        <thead>
        <tr class="tablewith">
            <th width="40" align="center">选择</th>
            <th width="40" align="center">序号</th>
            <th width="150" align="center">姓名</th>
            <th width="80" align="center">账号</th>
            <th width="200" align="center">身份证</th>
            <th width="100" align="center">购物卡号</th>
            <th width="150" align="center">余额</th>
            <th width="100" align="center">推荐人</th>
            <th width="40" align="center">操作人</th>
            <th width="40" align="center">状态</th>
        </tr>
        </thead>
        <tbody>
        <%
            int number = 1;
        %>
        <s:iterator value="pageForm.list" var="list">
            <tr id="${list[10]}" class="">
                <td><input type="radio" name="a"  class="JQuerypersonvalue" value="${list[4]}" /></td>

                <td><%=number%></td>
                <td><span class="mkname">${list[0]}</span></td>
                <td><span class="reference">${list[1]}</span></td>
                <td><span class="staffidentitycard">${list[2]}</span></td>
                <td><span class="cardShow">${list[6]==null || list[6]=="" ? 0:list[6]}</span>
                    <span class="cardnumber" style="display: none">${list[6]}</span></td>
                <td><span class="bonuspointscore">${list[3]}</span></td>
                <td><span class="name">${list[5]}</span></td>
                <td><span class="operator">${list[7]}</span></td>
                <td><span class="state">${list[8]=="1"?"正常":"挂失"}</span></td>
            </tr>
            <%
                number++;
            %>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/giftcard/ea_getSearchListByLimit.jspa?pageNumber=${pageNumber}">
        </c:param>
    </c:import>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
        framespacing="0" height="0"></iframe>
<div class="zhuce" id="registered">
    <div class="box">
        <h2>
            开通购物卡
            <img class="close" style="width: 30px;height: 30px;position: absolute;right: 4px;top: 5px;" src="<%=basePath%>/images/ea/main/ico-delete.png" alt="">
        </h2>
        <section>
            <form id="registerForm" name="registerForm" method="post">
            <p>
                <label for="registerName">姓名</label>
                <input type="text" class="register" name="staff.staffName" id="registerName" value="" placeholder="请输入姓名" />
            </p>
            <p>
                <label for="call">手机号</label>
                <input type="text" class="register" name="staff.reference" id="call" value="" placeholder="请输入手机号" />
            </p>
            <p>
                <label for="confirm_call">确认</label>
                <input type="text" class="register" name="" id="confirm_call" value="" placeholder="请确认手机号" />
            </p>
            <p>
                <label for="registerPassWord">密码</label>
                <input type="password" class="register" name="password" id="registerPassWord" autocomplete="off" value="" placeholder="请输入密码" />
            </p>
            <p>
                <label for="id_card">身份证</label>
                <input type="text" class="register" name="staff.staffIdentityCard" id="id_card" value="" placeholder="请输入身份证号" />
            </p>
            <p>
                <label for="shopping_card">购物卡号</label>
                <input type="text" class="register" name="giftCards.cardNumber" id="shopping_card" value="" placeholder="请输入购物卡号" />
            </p>
            <p>
                <label for="referee">推荐人</label>
                <input type="text" class="register" name="iphone" id="referee" value="" placeholder="请输入推荐人手机号" />
            </p>
            </form>
        </section>
        <input type="button" name="" id="registeredSubmit" value="确定" />
    </div>
</div>

<div class="buka" id="reissueCard">
    <div class="box">
        <h2>
            <span></span>
            <img class="close"  style="width: 30px;height: 30px;position: absolute;right: 4px;top: 5px;" src="<%=basePath%>images/ea/main/ico-delete.png" alt="">
        </h2>
        <section>
            <form id="openForm" name="openForm" method="post">
            <p>
                <span>姓名</span>：
                <input type="text" class="open input_" id="openName" name="staff.staffName"  value="" placeholder="请输入购物卡号" />
            </p>
            <p>
                <span>手机号</span>：
                <input type="text" class="open input_" id="openCall" name="staff.reference"  value="" placeholder="请输入购物卡号" />
            </p>
            <p>
                <span>身份证号</span>：
                <input type="text" class="open input_" id="openIdCard" name="staff.staffIdentityCard"  value="" placeholder="请输入购物卡号" />
            </p>
            <p>
                <span>新的购物卡号</span>：
                <input type="text" class="input_" name="giftCards.cardNumber" id="openCard"  placeholder="请输入购物卡号" />
            </p>
            </form>
        </section>
        <input type="button" name="" id="reissueSubmit" value="确定" />
    </div>
</form>
</div>

<div class="zhangdan" id="bill">
    <div class="box">
        <h2>
            购物账单
            <img class="close"  style="width: 30px;height: 30px;position: absolute;right: 4px;top: 5px;" src="<%=basePath%>images/ea/main/ico-delete.png" alt="">
        </h2>
        <section>
            <h3 class="clearfix">
                <p>
                    总积分数：<span class="integralNumber"></span>
                </p>
                <p>
                    金额：<span class="money"></span>
                </p>
            </h3>
            <table border="0" cellspacing="0" cellpadding="0" class="billdal">
                <tr>
                    <td>积分获赠</td>
                    <td>积分数量</td>
                    <td>明细时间</td>
                </tr>
            </table>
            <p>
                <a class="up">上一页</a>
                <span>共<i class="pageSum"></i>页</span>
                <span>当前第<i class="pageNum"></i>页</span>
                <a class="next">下一页</a>
            </p>
        </section>
    </div>
</div>
</body>
<script type="text/javascript">
    $(function () {
        $(".cardShow").each(function () {
            $(this).text($(this).text().replace(/\d{5}(\d{10})\d{5}/, "*****$1*****"));
        })
    })
</script>
</html>