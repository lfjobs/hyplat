<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>人员信息</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>
    <script src="<%=basePath%>/js/qrcode.js"></script>
    <script type="text/javascript">
        $(function () {
                // 二维码对象
                var qrcode;
                // 默认设置
                var content;
                var size;
                // 设置点击事件
                /* document.getElementById("send").onclick =function(){*/
                // 获取内容
                content = document.getElementById("content").value;
                //alert(content);
                content = content.replace(/(^\s*)|(\s*$)/g, "");
                // 获取尺寸
                size = document.getElementById("size").value;
                // 检查内容
                if(content==''){
                    //alert('请输入内容！');
                    dizhi();
                    //return false;
                }
                // 检查尺寸

                // 清除上一次的二维码
                if(qrcode){
                    qrcode.clear();
                }
                // 创建二维码
                qrcode = new QRCode(document.getElementById("qrcode"), {
                    width : size,//设置宽高
                       height : size
                });
                qrcode.makeCode(document.getElementById("content").value);
        })
        function dizhi() {
            var sccIDs = $("#sccID").val();
            $("#content").val(basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccIDs);

        }
    </script>
</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;">人员信息</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="facility_details">
                <div class="top">
                    <img src="<%=basePath%>${teacher[2] == null?"images/ea/driving/elkc/head.png":teacher[2]}" class="left2">
                    <div class="right right2">
                        <ul>
                            <li>
                                <h5>姓名：</h5><p>${teacher[0]==null?"":teacher[0]}</p>
                            </li>
                            <li>
                                <h5>工号：</h5><p>${teacher[1]==null?"":teacher[1]}</p>
                            </li>
                            <li>
                                <h5>职位：</h5><p>教练员</p>
                            </li>
                        </ul>
                    </div>
                </div>
                <ul class="txt">
                    <li>联系方式：<span>${teacher[3]==null?"":teacher[3]}</span></li>
                    <li>联系地址：<span>${teacher[4]==null?"":teacher[4]}</span></li>
                </ul>
                <input type="hidden" id="content" value="" />
                <input type="hidden" id="sccID" value="${teacher[5]}"/>
                <input type="hidden" id="size" value="150"></p>
                <div id="qrcode" style="width: 6rem;margin: 2rem auto;display: block;"></div>
                <div class="bottom">
                    <a onclick="getPhone(${teacher[3]})"><img src="<%=basePath %>st/images/ico-tel.png" class="left"></a>
                    <a onclick="getmsg(${teacher[3]})"><img src="<%=basePath %>st/images/ico-sms.png" class="right"></a>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath='<%=basePath%>';
    var staffId = "${staffId}"
</script>
<script>
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
    function getmsg(num) {
        if (num != null && num != ""){
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            if(isAndroid==true){
                if(confirm("确定发送短信邀请?")){
                    Android.callmsg(num+"");
                }
            }else if(isiOS==true){
                if(confirm("确定发送短信邀请?")){
                    var url= "func=" + 'message';
                    params={'name':num};
                    for(var i in params){
                        url = url + "&" + i + "=" + params[i];
                    }
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            }
        }else {
            alert("无电话号码")
        }
    }

    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");

    });
</script>

</body>
</html>