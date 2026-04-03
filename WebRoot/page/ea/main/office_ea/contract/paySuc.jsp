<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/paySuc.css"/>

    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <title>支付成功</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>"
        var  ddid = "${ddid}";
        var basePath="<%=basePath%>";

        $(function(){
            if(ddid==""||ddid==null){
                $(".p-01").text("签约成功");
                $(".p-02").text("恭喜您已签约");
                $(".p-03").text("完成");
            }

        });


        function signUrl(){
           var tx =  $(".p-03").text();
           if(tx=="完成"){
               document.location.replace(basePath+"ea/contract/ea_getDealFileByState.jspa?state=seallist");
               return false;
           }
            document.location.href = basePath+"ea/contract/ea_getSignUrl.jspa?ddid="+ddid;
            //发起签约
//
//            var ulp = basePath
//                + "ea/contract/sajax_ea_getSignUrl.jspa";
//            $.ajax({
//                type: "GET",
//                url: ulp,
//                async: false,
//                data:{
//                  ddid:ddid
//                },
//                dataType: "json",
//                success: function (data) {
//                    var member = eval('(' + data + ')');
//                    var signUrl = member.signUrl;
//                    var docId = member.docId;
//                    if(signUrl!="") {
//                        document.location.replace(signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId));
//                    }
//
//
//                },
//                error: function (data) {
//
//                    console.log("获取链接失败");
//                }
//
//
//            });


        }

    </script>
</head>
<body>
<%--<header>--%>
    <%--<ul class="clearfix">--%>
        <%--<li>--%>
            <%--<img src="<%=basePath%>images/ea/office/contract/return.png" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--签约文件--%>
        <%--</li>--%>
        <%--<li>--%>

        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content">
    <section>
        <div class="div-top">
            <img src="<%=basePath%>images/ea/office/contract/img_13.png"/>
        </div>
        <p class="p-01">
            支付成功
        </p>
        <p class="p-02">
            恭喜您已完成支付
        </p>
        <%--<p class="p-02">--%>

        <%--</p>--%>
        <p class="p-03" onclick="signUrl()">
            立即签约
        </p>
    </section>
</div>
</body>

</html>
