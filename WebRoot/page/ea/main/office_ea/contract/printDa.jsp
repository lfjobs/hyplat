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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/printDa.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/ea/office_ea/contract/jQuery.print.min.js" type="text/javascript" charset="utf-8"></script>



    <title>&lrm;</title>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >

                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" >
            </a>
        </li>
        <li>
           打印档案
        </li>
    </ul>
</header>


    <div class="content" >
        <div class="div-name">
            <label for="">文件编号</label>
            <input type="text"  value="${document.docNum}" readonly/>
        </div>

        <div class="div-name">
            <label for="">文件标题</label>
            <input type="text"  value="${document.title}" readonly/>
        </div>
        <div class="div-name">
            <label for="">主题词</label>
            <input type="text"  value="${document.theme}" readonly/>
        </div>
        <div class="div-name">
            <label for="">文件缓急</label>
            <c:choose>
            <c:when test="${document.emergencyType eq 'p'}">
                <input class="emergencyType" type="text"  value="普通" readonly/>
            </c:when>
            <c:when test="${document.emergencyType eq 'j'}">

                <input class="emergencyType" type="text"  value="急件" readonly/>

            </c:when>
            <c:otherwise>
                <input class="emergencyType" type="text"  value="特级" readonly/>

            </c:otherwise>
            </c:choose>
        </div>
        <div class="div-name">
            <label for="">公文类型</label>
            <c:choose>
                <c:when test='${docType=="aa"}'> <input type="text"  class="docType" value="董事会会议决定文件" readonly/></c:when>
                <c:when test='${docType=="bb"}'> <input type="text"  class="docType" value="董事长办公室文件" readonly/></c:when>
                <c:when test='${docType=="cc"}'> <input type="text"  class="docType" value="总裁办公室文件" readonly/></c:when>
                <c:when test='${docType=="dd"}'> <input type="text"  class="docType" value="总部人事处文件" readonly/></c:when>
                <c:when test='${docType=="ee"}'> <input type="text"  class="docType" value="总部办公室文件" readonly/></c:when>
                <c:when test='${docType=="ff"}'> <input type="text"  class="docType" value="总部财务处文件" readonly/></c:when>
                <c:when test='${docType=="gg"}'> <input type="text"  class="docType" value="总部教务(生产)处文件" readonly/></c:when>
                <c:when test='${docType=="hh"}'> <input type="text"  class="docType" value="总部营销处文件" readonly/></c:when>
                <c:when test='${docType=="jj"}'> <input type="text"  class="docType" value="总部教务部文件" readonly/></c:when>
                <c:otherwise><input type="text" class="docType" value="总部服务(创收)平台" readonly/></c:otherwise>
            </c:choose>

        </div>

        <div class="div-name docNum">
            <label for="">正式编号</label>
            <p class="formalNum">

                <input type="text"  value="${document.numWord}"  readonly id="numWord"/>字【<span class="year">${document.numTime}</span>】第<input type="text" readonly  id="numCode" style="width:1rem;" value="${document.numCode}" /> 号

            </p>
        </div>
        <%  String module = (String)session.getAttribute("module");
            if("contract".equals(module)){

        %>
        <div class="div-name">
            <label for="">甲方公司</label>
            <input type="text" value="${document.partyAName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">甲方责任人</label>
            <input type="text" value="${document.partyAstaffnames}" readonly/>
        </div>
        <div class="div-name">
            <label for="">甲方身份证</label>
            <input type="text" value="${document.staffIdentityCardA}" readonly/>
        </div>
        <div class="div-name">
            <label for="">乙方公司</label>
            <input type="text" value="${document.partyBName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">乙方责任人</label>
            <input type="text" value="${document.partyBstaffnames}" readonly/>
        </div>
        <div class="div-name">
            <label for="">乙方身份证</label>
            <input type="text" value="${document.staffIdentityCardB}" readonly/>
        </div>


        <div class="div-name time-Div">
            <label for="">有效期</label>
            <p class="validateTime">

                从<input type="text" value="${fn:substring(document.startValidity,0,10)}" id="startDate" readonly/>至<input type="text"   id="endDate"  readonly name="document.endValidity"  value="${fn:substring(document.endValidity,0,10)}" />

            </p>
        </div>
        <%
            }
        %>
        <div class="div-name">
            <label for="">文件模板</label>
            <input type="text"   value="${documentTemplate.fileShowName}" readonly  id="specificTemplate" />
        </div>
        <div class="div-name">
            <label for="">拟稿公司</label>
            <input type="text"   value="${document.companyName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">拟稿部门</label>
            <input type="text"   value="${document.deptNameOfDraft}" readonly/>
        </div>
        <div class="div-name">
            <label for="">拟稿人</label>
            <input type="text"  value="${document.drafterName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">拟稿人电话</label>
            <input type="text" value="${document.telphone}" readonly/>
        </div>
        <div class="div-name">
            <label for="">拟稿时间</label>
            <input type="text" value="${fn:substring(document.draftTime,0,19)}" readonly/>
        </div>
        <div class="div-name">
            <label for="">存放档案地</label>
            <input type="text"  value="${document.archiveSite}" readonly/>
        </div>
        <div class="div-name">
            <label for="">档案存管人</label>
            <input type="text"  value="${document.archiveCustodian}" readonly/>
        </div>
        <div class="div-name">
            <label for="">文件条码</label>
            <p class="barcode">
            <%
                hy.ea.bo.office.Document data = (hy.ea.bo.office.Document) request
                        .getAttribute("document");
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
               </br> ${document.barCode}
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
    var module = "<%=session.getAttribute("module")%>";
    function print(){

        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端





        if(isAndroid) {

           try {
                var posNum = Android.forAndroidDeviceId();

                Android.printDoc("${document.docNum}","${document.title}","${document.theme}",$(".emergencyType").val(),$(".docType").val(),$("#numWord").val()+"字【"+$(".year").text()+"】第"+($("#numCode").val()==""?"  ":$("#numCode").val())+"号","${document.partyAName}","${document.partyAstaffnames}","${document.staffIdentityCardA}","${document.partyBName}","${document.partyBstaffnames}","从"+$("#startDate").val()+"至"+$("#endDate").val(),"${document.staffIdentityCardB}","${documentTemplate.fileShowName}","${document.companyName}","${document.deptNameOfDraft}","${document.drafterName}","${fn:substring(document.draftTime,0,19)}","${document.archiveSite}","${document.archiveCustodian}","${document.barCode}");

           }catch(error) {
               //出错说明不在APP内

           }
        }else if(isiOS){
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
