<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
    <meta name="format-detection" content="telephone=yes"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/detail.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>

    <title>${param.name}</title>
    <style>
        html,
        body,
        #container {
            width: 100%;
            height: 100%;
        }
    </style>


    <script>
        var  basePath = "<%=basePath%>";
        var tel = "${param.tel}";
        var name = "${param.name}";
        var tels = tel.split(";");
        var retel = "";
        $(function(){

            if(tels.length>0){

                for(var i = 0;i<tels.length;i++){
                    if(tels[i].length==11){
                        retel = tels[i];
                        $(".fxx").show();
                        break;
                    }
                }
            }
            var u = navigator.userAgent;
            isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端



        });


        function sendMes(){

            if(isAndroid==true){

                Android.toChat(retel,"",name);//手机号
            }else if(isiOS==true){
                var url= "func=" + 'ioschat';
                params={'sccid':"",
                    'account' :retel,
                    'username' :name};
                for(var i in params){
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }


        }


        //打电话

        function getPhone(num) {
            if (num != null && num != ""){
                var u = navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                if (isAndroid == true) {
                    if (confirm("确定呼叫?")) {
                        Android.callPhone(num+"");
                    }
                } else if (isiOS == true) {
                    if (confirm("确定呼叫?")) {
                        var url = "func=" + 'iosCallphone';
                        params = {'phoneNum': num};
                        for (var i in params) {
                            url = url + "&" + i + "=" + params[i];
                        }
                        window.webkit.messageHandlers.Native.postMessage(url);
                    }
                }
            }else {
                alert("无电话号码");
            }
        }




        //点击认领跳转会员
        function claim(){
            var id = "${param.gdID}";
            var photo = "${param.photoys}";
            var name = "${param.name}";
            var tel = "${param.tel}";
            var pname = "${param.pname}";
            var cityname = "${param.cityname}";
            var adname = "${param.adname}";
            var address = "${param.address}";
            var location = "${param.location}";

            var head = "${param.head}";
            var busiManagerID = "${param.busiManagerID}";
            var loc = location.split(",");
            var type =  "${param.type}";
            var typecode = "${param.typecode}";

            var x = loc[0];
            var y = loc[1];
            tel =  tel.split(";")[0];

//            if(busiManagerID!=null&&busiManagerID!=""){
//                document.location.href = basePath+"ea/qyrz/ea_getpk.jspa?id="+id+"&name="+name+"&photo="+photo+"&tel="+tel+"&pname="+pname+"&cityname="+cityname+"&adname="+adname+"&address="+address+"&x="+x+"&y="+y+"&head="+head+"&busiManagerID="+busiManagerID+"&type="+type+"&typecode="+typecode;
//                return false;
//            }


            var url = basePath+"/ea/qyrz/sajax_ea_validateCom.jspa";
            $.ajax({
                url: encodeURI(url),
                type: "GET",
                async: false,
                dataType: "json",
                data:{
                    gdID:id,
                        busiManagerID:busiManagerID
                },
                success: function (data) {
                    var m = eval("("+data+")");
                    var result = m.result;
                    var login = m.login;
                    if(login=="login"){
                        document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                        return false;
                    }


                    if(result!="0") {
                        if (result == "1") {
                            //已被其他人认领

                            $("#btn_gwc").find("p").text("已被其他人认领");
                        } else if (result == "2") {
                            //已经入驻过公司无法重复入驻

                            $("#btn_gwc").find("p").text("已经认领过公司无法重复");
                        }

                        if(!($("#btn_gwc").is(":animated"))){
                            $("#btn_gwc").show();
                            setTimeout(function () {
                                $("#btn_gwc").animate({
                                    opacity: "0",
                                },1000,function () {
                                    $("#btn_gwc").css("opacity","1");
                                    $("#btn_gwc").hide();
                                })
                            }, 1000);
                        }
                    }else{


                        document.location.href = basePath+"ea/qyrz/ea_getpk.jspa?id="+id+"&name="+name+"&photo="+photo+"&tel="+tel+"&pname="+pname+"&cityname="+cityname+"&adname="+adname+"&address="+address+"&x="+x+"&y="+y+"&head="+head+"&busiManagerID="+busiManagerID+"&type="+type+"&typecode="+typecode;


                    }



                },
                error:function(data){

                    console.log("验证失败");
                }
            } )




        }

    </script>
</head>
<body>
<div id="btn_gwc" style=" opacity: 1;display:none;">
    <p></p>
</div>
<%--<header>--%>
<%--<ul class="clearfix">--%>

<%--<li>--%>
<%--<c:if test="${param.head eq 'show'}">--%>
<%--<a href="<%=basePath%>/ea/qyrz/ea_toPeriphery.jspa?dwLnglatX=${param.dwLnglatX}&dwLnglatY=${param.dwLnglatY}">--%>
<%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" >--%>
<%--</a>--%>
<%--</c:if>--%>
<%--</li>--%>

<%--<li>--%>
<%--${param.name}--%>
<%--</li>--%>
<%--</ul>--%>
<%--</header>--%>
<div class="content">
    <ul>
        <li class="clearfix">
            <div>
                <img class="img-2" src="<%=basePath%>images/scMobile/qyrz/shop.png"/>
            </div>
            <p>
                ${param.name}
            </p>
            <p class="p-rl" onclick="claim()">
                认领引流
            </p>
        </li>
        <li class="clearfix">
            <div>
                <img src="<%=basePath%>images/scMobile/qyrz/img-dz-01.png"/>
            </div>
            <p>
                地址：${param.site}
            </p>
            <span>
                ${param.distance}
            </span>
        </li>
        <li class="clearfix">
            <div>
                <img class="img-2" src="<%=basePath%>images/scMobile/qyrz/img-dz-02.png"/>
            </div>
            <p>
                电话：
                <c:if test = "${param.tel eq ''}">
                    暂无
                </c:if>
                <c:if test = "${param.tel ne ''}">
                    <c:set var="tels" value="${fn:split(param.tel, ';')}"  />
                    <c:forEach items="${tels}" var="tel">
                        <c:if test="${param.head eq 'show'}">
                            <a href="tel:${tel}">${tel}</a>
                        </c:if>
                        <c:if test="${param.head ne 'show'}">
                            <a onclick="getPhone('${tel}')">${tel}</a>
                        </c:if>

                    </c:forEach>
                </c:if>
            </p>
        </li>
        <li class="clearfix fxx" style="display: none;">
            <div>
                <img src="<%=basePath%>images/scMobile/qyrz/mes.png"/>
            </div>
            <p onclick="sendMes()">
                发消息
            </p>
        </li>
    </ul>
    <%--<jsp:include page="/page/mobile/gaoDePosition.jsp"/>--%>
    <div class="div-img">
        <c:set var="photos" value="${fn:split(param.allphoto, ',')}"  />
        <c:forEach items="${photos}" var="photo">

            <img src="${photo}"/>

        </c:forEach>
    </div>
</div>
</div>
</body>
</html>
