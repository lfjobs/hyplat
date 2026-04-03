<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/23 0023
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/customerCashingAudit.css">
</head>
<body>
<div>
    <h2>
        客户兑现审核
    </h2>
    <section>
        <menu>
            <input type="hidden"  value="${wday.wdaID}" id="wdaID">
            <li>
                币种：<span>${wday.currency}</span>
            </li>
            <li>
                日期：<span><fmt:formatDate value="${wday.applyDate}"></fmt:formatDate></span>
            </li>
            <li>
                明细标志：<span>${wday.detailmark}</span>
            </li>
            <li>
                顺序号：<span>${wday.orderNum}</span>
            </li>
        </menu>
    </section>
    <div class="xian"></div>
    <section>
        <menu>
            <li>
                付款账号开户行：
                <span>${wday.payOpenAccountBank}</span>
            </li>
            <li>
                付款账号/卡号：
                <span>${wday.payCardAccount}</span>
            </li>
            <li>
                付款账号名称/卡名字：
                <span>${wday.payCardName}</span>
            </li>
            <li>
                收款账号开户行：
                <span>${wday.recevOpenAccountBank}</span>
            </li>
            <li>
                收款账号省份：
                <span>${wday.recevCardProvince}</span>
            </li>
            <li>
                收款账号地市：
                <span>${wday.recevCardCity}</span>
            </li>
            <li>
                收款账号地区码：
                <span>${wday.receCardDCode}</span>
            </li>
            <li>
                收款账号：
                <span>${wday.recevCardAccount}</span>
            </li>
            <li>
                收款账号名称：
                <span>${wday.recevCardName}</span>
            </li>
            <li>
                金额：
                <span class="color_red">${wday.money}元</span>
            </li>
            <li>
                汇款用途：
                <span>${wday.payurpose}</span>
            </li>
            <li>
                备注信息：
                <span>${wday.remark}</span>
            </li>
            <li>
                汇款方式：
                <span>${wday.payMode}</span>
            </li>
            <li>
                收款账户短信通知手机号码：
                <span>${wday.recevTel}</span>
            </li>
            <li>
                自定义序号：
                <span>${wday.userDefined}</span>
            </li>
            <li>
                审核意见：<span>${wday.auditOpinion}</span>
            </li>
            <li class="quer">
                审核状态：
                <span>${wday.payState=='01'?"通过":"未通过"}</span>
            </li>
            <li>
                审核人：<span>${account.accountEmail}</span>
            </li>
        </menu>
    </section>
    <div class="xian"></div>
    <form action="" method="post" id="formid">
        <section class="shenhe">
            <menu>
                    <li>
                        回执单号：
                        <span><input type="text" name="tradeCode" value="${wday.tradeCode}" id="tradeCode"></span>
                    </li>
                    <li>
                        操作人：
                        <span id="receiptOprName">${account.accountEmail}</span>
                        <input type="hidden" name="wda.receiptOprID" value="${account.staffID}" id="receiptOprID"/>
                    </li>
            </menu>
        </section>
        <p style="text-align: center;">
            <input type="button" id="shenhe" value="回执确认"></input>
        </p>
    </form>
</div>
</body>
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var basePath="<%=basePath%>";
    $("#shenhe").click(function(){
        if($("#shenhe").val()=="提交保存"){
            //此处写跳转的
                var tradeCode=$("#tradeCode").val();
                var receiptOprName=$("#receiptOprName").text();
                var receiptOprID=$("#receiptOprID").val();
                if(tradeCode==""){
                    alert("回执单号不能为空");
                    return
                }
                var wdaID=$("#wdaID").val();
                $.ajax({
                    url:basePath+"/ea/jinbi/sajax_addAudit.jspa?tradeCode="+tradeCode+"&receiptOprName=" +receiptOprName+"&wdaID="+wdaID+"&receiptOprID="+receiptOprID+"&flag=01",
                    type:"post",
                    asycn : false,
                    dataType:"json",
                    success: function(data) {
                        var wait=eval("("+data+")");
                        var fa=wait.bl;
                        if(fa==true){
                            window.location.href =basePath+"/ea/jinbi/ea_getWdsList.jspa";
                        }

                    }
                });
        }
        $(".shenhe").slideDown();
        $(this).val("提交保存");
    })
</script>
</html>

