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
        </menu>
    </section>
    <div class="xian"></div>
    <form action="" method="post" id="formid">
        <section class="shenhe">
            <menu>
               <li>
                    审核意见：<input type="text" name="auditOpinion" value="${wday.auditOpinion}" id="auditOpinion" />
                </li>
                <li class="state radio">
                    <p>审核状态：</p>
                    <p id="adopt"><input type="radio" name="payState" id="yes" value="02" checked/><label for="yes"></label>未通过</p>
                    <p id="notThrough"><input type="radio" name="payState" id="no" value="01" /><label for="no"></label>通过</p>
                </li>
                <div id="tongg">
                    <li> 审核状态：<span >${wday.payState=='01'?"通过":"未通过"}</span></li>
                </div>
                <li>
                    审核人：<span id="payOperatorName">${account.accountEmail}</span>
                    <input type="hidden" name="wda.payOperatorID" value="${account.staffID}" id="payOperatorID"/>
                </li>
            </menu>
        </section>
        <p style="text-align: center;">
            <input type="button" id="shenhe" value="审核"></input>
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
            var payOperatorName=$("#payOperatorName").text();
            var item=$('input:radio[name="payState"]:checked').val();
            var wdaID=$("#wdaID").val();
            var auditOpinion=$("#auditOpinion").val();
            var payOperatorID=$("#payOperatorID").val();
                if(auditOpinion==""){
                    alert("审核意见不能为空");
                    return
                }
            $.ajax({
                url:basePath+"/ea/jinbi/sajax_addAudit.jspa?payOperatorName="+payOperatorName+"&wdaID="+wdaID+"&payState="+item+"&auditOpinion="+auditOpinion+"&payOperatorID"+payOperatorID+"&flag=02",
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
        var state=$("#tongg span").text();
        if(state=="通过"){
            $(".state").hide();
            $("#tongg").show();
        }else {
            $("#tongg").hide();
            $(".state").show();
        }
    })
</script>
</html>

