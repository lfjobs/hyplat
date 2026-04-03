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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/archiveBoxView.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript" charset="utf-8"></script>



    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
           档案盒详情
        </li>
    </ul>
</header>


    <div class="content" >
        <div class="div-name">
            <label for="">档号</label>
            <input type="text"  value="${archiveDocType.arnum}" readonly/>
        </div>

        <div class="div-name">
            <label for="">案卷题名</label>
            <input type="text"  value="${archiveDocType.typeName}" readonly id="title"/>
        </div>
        <div class="div-name">
            <label for="">编制单位</label>
            <input type="text"  value="${archiveDocType.companyName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">编制人</label>
            <input type="text"  value="${archiveDocType.createrName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">编制日期</label>
            <input type="text"  value="${fn:substring(archiveDocType.time,0,10)}" readonly/>
        </div>

        <div class="div-name">
            <label for="">档案盒条码</label>
            <p class="barcode">
            <%
                hy.ea.bo.office.ArchiveDocType data = (hy.ea.bo.office.ArchiveDocType) request
                        .getAttribute("archiveDocType");
                if (data != null) {
                    StringBuffer barCode = new StringBuffer();
                    barCode.append("<img src='");
                    barCode.append(request.getContextPath());
                    barCode.append("/CreateBarCode?data=");
                    barCode.append(data.getBarCode());
                    barCode.append("&barType=TF25&height=43&headless=true&drawText=true&width=1' width='160'>");
                    out.println(barCode.toString());
                } else {
                    out.println("no data");
                }
            %>
               </br> ${archiveDocType.barCode}
            </p>

        </div>



    </div>
<div class="div-bottom">
    <p onclick="print()">
        打印
    </p>

</div>
<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep">请到电脑端连接打印机打印</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<style media="print">
    @page{

        size:auto;
        margin:0mm;
    }

</style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";

    var barcode = "${param.barcode}";
    var adtId = "${archiveDocType.adtId}";
    if(barcode!=""&&barcode!=null){
           $(".div-bottom p").text("查看包含文件列表");
    }else{
        $(".div-bottom p").text("打印");
    }
    function print(){
        if(barcode!=""&&barcode!=null){
          document.location.href = basePath+"ea/androiddoc/ea_getArchiveTree.jspa?adtId="+adtId;

            return false;
        }


        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端





        if(isAndroid||isiOS) {
            $(".div-tingyong").show();
            return false;
        }
        $(".content").print({

            globalStyles:true,//是否包含父文档的样式，默认为true
            mediaPrint:false,//是否包含media=print的链接标签，会被globalStyles选项覆盖，默认为false
            stylesheet:null,//外部样式表的url地址，默认为null
            noPrintSelector:".no-print",//不想打印的元素的Jquery选择器，默认为".no-print"
            iframe:true,//是否使用一个Iframe来替代打一遍表单的弹出窗口，true未在本页面进行打印，false新开一个页面打印，默认为ture
            append:null,//将内容添加到打印机内容的后面
            prepend:null,//将内容添加到打印机内容的前面，可以用来作为要打印内容
            deferred:$.Deferred()//会掉函数

        });
    }
    $(".close-tingyong").click(function(){
        $(this).parents(".div-tingyong").hide();
    })
</script>
</html>
