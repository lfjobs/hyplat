<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-07-27
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/localforage.min.js"></script>
    <title>Loading</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var sn = "${param.sn}";//序列号
        var model = "${param.model}";//芯片型号
    </script>
    <STYLE TYPE="text/css">
        header {
            background-color: #f74c32
        }

        .loading {
            /*display: none;*/
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, .4);
        }

        .loading .div-box {
            border-radius: 0.3rem;
            border: 0.025rem solid #f4f4f4;
            width: 8rem;
            background-color: #fff;
            margin: 15rem auto 0 auto;
            text-align: center;
            height: 6rem;
            line-height: 2.5rem;
            padding-top: 1rem;

        }
    </STYLE>
</head>
<body>
<%--http://localhost:8080/page/WFJClient/secure/nfc/LoadingPage.jsp?sn=36:04:7E:0A:11:A2:04:E0&model=NXP%20Semiconductors--%>
<form id="subForm" name="subForm" METHOD="post">
    <input type="hidden" name="sn" value="${param.sn}" id="sn"/>
    <input type="hidden" name="model" value="${param.model}" id="model"/>
    <input type="submit" id="submit" name="submit" style="display: none;"/>
</form>
<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt=""/>
        <p>跳转中...</p>
    </div>
</div>
<script type="text/javascript">
    //扩展jquery的格式化方法
    $.fn.parseForm = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    $(function () {
        localforage.removeItem("dataKey");
        console.log($("#sn").val());
        if($("#sn").val()!=null&&$("#sn").val().length>0){
            var subForm = $("#subForm").parseForm();
            var expires = new Date().getTime() + 3600 * 1000; // 当前时间戳 + 1小时的毫秒数
            var data = {
                formVal: subForm,
                expires: expires
            };
            console.log(data);
            // 将数据存储到 localForage 中
            localforage.setItem('dataKey', data).then(function () {
                console.log('Data stored');
                $("#subForm").attr("action",basePath+"/ea/bindnfc/ea_readNfc.jspa");
                $("#submit").click();
            }).catch(function (err) {
                console.log('Error: ' + err);
            });
        }else {
            $("#subForm").attr("action",basePath+"/page/WFJClient/pc/pc_login.jsp");
            $("#submit").click();
        }


       /* var subForm = $("#subForm").parseForm();
        localforage.setItem('subForm', subForm).then(function (value) {
            $("#submit").click();
        }).catch(function (err) {    // 当出错时，此处代码运行
            console.log(err);
        });*/
    });

</script>
</body>
</html>
