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
<html>
<base href="<%=basePath%>">

<title>&lrm;</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/templateManage.css">
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var defaults = "${param.defaults}";
</script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            文书模板
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li>
                    <p id="ul-tab1" class="active">公司模板</p>
                </li>
                <li>
                    <p id="ul-tab2">共享模板 </p>
                </li>
                <li>
                    <p id="ul-tab3">模版分类</p>
                </li>
            </ul>
            <ul class="ul-tab tabs ul-tab1 clearfix">
                <li class="gwmb">
                    <p class="active"><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>公司公文模版</p>
                </li>
                <li class="htmb">
                    <p><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>公司合同模板</p>
                </li>
            </ul>

            <ul class="ul-tab tabs ul-tab2 clearfix" style="display:none;">
                <li class="sgwmb">
                    <p  class="active"><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>共享公文模版</p>
                </li>
                <li class="shtmb">
                    <p><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>共享合同模板</p>
                </li>
            </ul>

            <ul class="ul-tab tabs ul-tab3 clearfix" style="display:none;">
                <li class="gwfl">
                    <p  class="active"><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>公文模版分类</p>
                </li>
                <li class="htfl">
                    <p><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>合同模板分类</p>
                </li>
            </ul>

        </div>


    </div>
</div>

<div class="iframecom" >
    <iframe id="iframe"  name="iframe" src="<%=basePath%>/ea/androiddoc/ea_getDocTempTree.jspa?module=doc" width="100%" height="100%" frameborder="0"></iframe>
</div>


<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="tittle-p"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        if(defaults!="") {
        var  li = $("."+defaults).parent().attr("class");

            $(".tabs .active").removeClass("active");
            $(".ul-tab-fh .active").removeClass("active");
            if (li.indexOf("ul-tab1") != -1) {
                $("#ul-tab1").addClass("active");

            } else if (li.indexOf("ul-tab2") != -1) {
                $("#ul-tab2").addClass("active");
            } else if (li.indexOf("ul-tab3") != -1) {
                $("#ul-tab3").addClass("active");
            }
            $("."+defaults).find("p").addClass("active");
            $(".tabs").hide();
            $("."+defaults).parents("ul").show();
            jumptab(defaults);
        }
        $(".ul-tab-fh li p").click(function () {
            $(".ul-tab-fh .active").removeClass("active");
            $(this).addClass("active");

            var id = $(this).attr("id");
            $(".tabs").hide();
            $("."+id).show();
            $(".tabs .active").removeClass("active");
            $("."+id).find("li").find("p").eq(0).addClass("active");
            $("."+id).find("li").find("p").eq(0).trigger("click");
        });
        $(".tabs li p").click(function () {
            $(".tabs .active").removeClass("active");
            $(this).addClass("active");


        });

        //公文模板分类

        $(".tabs li").click(function(){

            jumptab($(this).attr("class"));
        })

    });


    function jumptab(defaults){
        if(defaults=="gwfl"){
             url="ea/androiddoc/ea_getTempTypeTree.jspa?module=doc";

        }else if(defaults=="htfl"){
            url="ea/androiddoc/ea_getTempTypeTree.jspa?module=contract";


        }else if(defaults=="gwmb"){

            url = "ea/androiddoc/ea_getDocTempTree.jspa?module=doc";

        }else if(defaults=="htmb"){
         url = "ea/androiddoc/ea_getDocTempTree.jspa?module=contract";

        }else if(defaults=="sgwmb"){
            var url = "page/ea/main/office_ea/contract/shareTempTree.jsp?module=doc";

        }else if(defaults=="shtmb"){
            var url = "page/ea/main/office_ea/contract/shareTempTree.jsp?module=contract";


        }
        var original = document.getElementsByTagName("iframe")[0];
        var newFrame = document.createElement("iframe");

        newFrame.setAttribute('frameborder', '0', 0);
        newFrame.src = basePath+url;
        var parent = original.parentNode;
        parent.replaceChild(newFrame,original);
    }
</script>
</html>

