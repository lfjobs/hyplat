<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet/less" type="text/css" href="<%=basePath%>css/scMobile/qyrz/rzsjlb.less" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/rzsjlb.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>

    <title>类型选择</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";


    </script>
</head>
<body>
<%--<header>--%>
    <%--<ul  class="clearfix">--%>
        <%--<li onclick="toBack();">--%>
            <%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--企业入驻类型--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content">
    <ul>
        <c:forEach items="${productList}" var="beans" varStatus="v">
            <li class="clearfix dianji" onclick="toRz(this);">
                <img class="img-left" src="<%=basePath%>${beans[3]}" onerror="this.src='<%=basePath%>images/ea/production/forum/reportAnError.png'">
                <div id="">
                    <p>${beans[0]}</p>
                    <p><span>￥${beans[2]}</span>${beans[7]}</p>
                </div>
                <img class="img-right" src="<%=basePath%>images/scMobile/qyrz/img-list-02.png" >
                <input type="hidden" value="${beans[1]}"  name="ppid"     class="ppid"/>
                <input type="hidden" value="${beans[2]}"  name="money" class="money"/>
                <input type="hidden" value="${beans[0]}"  name="goodsname" class="goodsname"/>
                <input type="hidden" value="${beans[8]}"  name="companyname" class="companyname"/>
                <input type="hidden" value="${beans[9]}"  name="ccompanyid" class="ccompanyid"/>
                <input type="hidden" value="${beans[5]}"  name="goodsid" class="goodsid"/>
                <input type="hidden" value="${beans[3]}"  name="goodsimg" class="goodsimg"/>
            </li>



        </c:forEach>
    </ul>
</div>
</body>

<script type="text/javascript">
    //跳转认证详情页
    function toRz(obj){
        var ppid = $(obj).find(".ppid").val();
        var goodsid = $(obj).find(".goodsid").val();
        var money = $(obj).find(".money").val();
        var goodsname = $(obj).find(".goodsname").val();
        var goodsimg =  $(obj).find(".goodsimg").val();
        var companyname = $(obj).find(".companyname").val();
        var ccompanyid = $(obj).find(".ccompanyid").val();
        var id = "${param.id}";
        var name = "${param.name}";
        var photo = "${param.photo}";
        var tel = "${param.tel}";
        var pname = "${param.pname}";
        var cityname = "${param.cityname}";
        var adname = "${param.adname}";
        var address = "${param.address}";
        var x = "${param.x}";
        var y = "${param.y}";
        var head = "${param.head}";
        var busiManagerID = "${param.busiManagerID}";
        var type =  "${param.type}";
        var typecode = "${param.typecode}";
        var gdcate = "", gdcode="", gdcate2 = "",gdcode2;

        if(type!=null&&type!=""){
            var types = type.split(";");
            if(types.length==1) {
                gdcate = types[0];
                gdcode = typecode;
            }else if(types.length>=2){

                gdcate = types[0];
                gdcate2 = types[1];
                gdcode2 = typecode;
            }


        }
        document.location.href=basePath+"/ea/qyrz/ea_toRz.jspa?ppid="+ppid+"&money="+money+"&id="+id+"&name="+name+"&photo="+photo+"&tel="+tel+"&pname="+pname+"&cityname="+cityname+"&adname="+adname+"&address="+address+"&x="+x+"&y="+y+"&goodsname="+goodsname+"&companyname="+companyname+"&goodsid="+goodsid+"&ccompanyid="+ccompanyid+"&head="+head+"&busiManagerID="+busiManagerID+"&gdcate="+gdcate+"&gdcode="+gdcode+"&gdcate2="+gdcate2+"&gdcode2="+gdcode2+"&goodsimg="+goodsimg;

    }

    //后退
    function toBack() {
        window.location.href = basePath + "/ea/qyrz/ea_toPeriphery.jspa";
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });

</script>
</html>
