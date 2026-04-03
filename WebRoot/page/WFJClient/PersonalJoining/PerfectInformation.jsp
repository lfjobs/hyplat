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
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/rl.css">
    <title>完善信息</title>
</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';

</script>
<body>

<header class="com_head">
    <a href="javascript:history.go(-1);" class="back"></a>
    <h1>完善信息</h1>
    <a href="###" class="head_R kf_ico"></a>
</header>

<div class="wrap_page">
    <form class="comSub" action="">
    <div class="info_tit">公司信息</div>
    <div class="info_box clearfix">
        <div class="info_L" style="line-height: 2.6rem;">公司logo</div>
        <div class="info_R">
            <!--/images/BuildPlatform/add_ico.png
            <!--<input type="file" accept="image/*" id="head_img">-->
            <a class="file_up">
                <img id="image_box" src="<%=basePath%>${param.logopath!= ''&& param.logopath!= null ?param.logopath:"/images/BuildPlatform/add_ico.png"}" style="width: 2.6rem;height: 2.6rem;margin-right: 0.6rem" />
                <input type="hidden" id="logopath" name="contactCompany.logoPath" value="">
                <input type="file" accept="image/*" id="head_img">
            </a>
        </div>
    </div>
    <div class="info_box clearfix">
        <div class="info_L">公司名称</div>
        <div class="info_R">
            <input type="text" name="contactCompany.companyName" class="info_inp" value="${param.companyName}">
            <input type="hidden" name="contactCompany.ccompanyID" class="info_inp" value="${param.ccompanyID}">
        </div>
    </div>
    <div class="info_box clearfix">
        <div class="info_L">公司负责人</div>
        <div class="info_R">
            <input type="text" name="contactCompany.cresponsible" class="info_inp" value="" placeholder="请输入真实姓名">
        </div>
    </div>
    <div class="info_box clearfix">
        <div class="info_L">联系电话</div>
        <div class="info_R">
            <input type="text" name="contactCompany.companyTel" class="info_inp" value="">
        </div>
    </div>
    <div class="info_box clearfix">
        <div class="info_L">公司地址</div>
        <div class="info_R" id="info_addr">
            <input id="_site" type="text" name="contactCompany.companyAddr" class="info_inp info_site" value="${param.companyAttr}">
            <input type="hidden" name="contactCompany.accuracy">
            <input type="hidden" name="contactCompany.dimension">
        </div>
    </div>
    <div class="info_box clearfix">
        <div class="info_R" style="width: 100%">
            <input id="companyAddr" type="text"  class="info_inp" value="" placeholder="可输入详细的门牌编号">
        </div>
    </div>
    <a href="###" class="sub_btn">提交</a>
        </form>
    <script type="text/javascript">
        $(function () {
            var u = navigator.userAgent;
            var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
            var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
            $("#info_addr").click(function () {
                if(isAndroid){
                    Android.callgetRoundLocal();
                }else if(isiOS){
                    var url= "func=" + 'iosMapaddress';
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            })
            $(".sub_btn").click(function () {
            var logo = $("#image_box").attr("src")
            if (logo.indexOf("base64")!= -1) {
                $("input[name='contactCompany.logoPath']").val(logo);
            }
            $("input[name='contactCompany.companyAddr']").val($("input[name='contactCompany.companyAddr']").val()+$("#companyAddr").val())

                $.ajax({
                    url:basePath+"/ea/industry/sajax_ea_perfectInfoSubmit.jspa?",
                    type : "post",
                    processData:false,
                    data:$(".comSub").serialize(),
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var flag = member.flag;
                        if(flag=="1"){
                            window.location.href=basePath+"page/WFJClient/PersonalJoining/SubmitIsOk.jsp";
                        }else if(flag =="2"){
                            window.location.href=basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                        }
                    }
                })
        })

            //头像上传显示
            $('#head_img').change(function() {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function() {
                    // 通过 reader.result 来访问生成的 DataURL
                    var url = reader.result;
                    setImageURL(url);
                };
                reader.readAsDataURL(file);
            });

            var image = new Image();

            function setImageURL(url) {
                document.getElementById("image_box").src = url;
            }
        })

        /**************************定位获取地址开始************************/
        function ios_address(param){
            var p=param.substring(0,param.indexOf(">"));
            var address=p.substring(0,p.indexOf("<"));
            var jv=p.substring(p.indexOf("<")+1);
            var j=jv.substring(1,jv.indexOf(","));
            var v=jv.substring(jv.indexOf(",")+2);
            $("#_site").val(address);
            $("#accuracy").val(j);
            $("#dimension").val(v);
        }

        function a_address(param){
            var address=param.substring(0,param.indexOf(","));
            var coordinate=param.substring(param.indexOf(",")+1);
            $("#_site").val(address);
            $("#accuracy").val(coordinate);
            $("#dimension").val(coordinate);
        }

        /**************************定位获取地址结束************************/
    </script>
</div>
</body>
</html>
