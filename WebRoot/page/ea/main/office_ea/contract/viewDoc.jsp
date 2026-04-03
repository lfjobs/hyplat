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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/viewDoc.css"/>
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
           查看详情
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
            <input type="text"  value="${document.title}" readonly id="title"/>
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
            <input type="text"   value="${document.companyName}" readonly id="companyName"/>
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
<c:if test="${document.subscriberName ne null&&document.subscriberName ne ''}">
        <div class="div-name">
            <label for="">审批公司</label>
            <input type="text"   value="${document.comNameofSub}" readonly/>
        </div>
        <div class="div-name">
            <label for="">审批部门</label>
            <input type="text"   value="${document.deptNameofSub}" readonly/>
        </div>
        <div class="div-name">
            <label for="">审批人</label>
            <input type="text"  value="${document.subscriberName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">审批时间</label>
            <input type="text" value="${fn:substring(document.subscribeTime,0,19)}" readonly/>

        </div>
 </c:if>
<c:if test="${document.sealerName ne null&&document.sealerName ne ''}">
        <div class="div-name">
            <label for="">盖章公司</label>
            <input type="text"   value="${document.comNameofSealer}" readonly/>
        </div>
        <div class="div-name">
            <label for="">盖章部门</label>
            <input type="text"   value="${document.deptNameofSealer}" readonly/>
        </div>
        <div class="div-name">
            <label for="">盖章签约人</label>
            <input type="text"  value="${document.sealerName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">盖章时间</label>
            <input type="text" value="${fn:substring(document.sealTime,0,19)}" readonly/>
        </div>
</c:if>
<c:if test="${document.publisherName ne null&&document.publisherName ne ''}">
        <div class="div-name">
            <label for="">分发公司</label>
            <input type="text"   value="${document.comNameofPublisher}" readonly/>
        </div>
        <div class="div-name">
            <label for="">分发部门</label>
            <input type="text"   value="${document.deptNameofPublisher}" readonly/>
        </div>
        <div class="div-name">
            <label for="">分发人</label>
            <input type="text"  value="${document.publisherName}" readonly/>
        </div>
        <div class="div-name">
            <label for="">分发时间</label>
            <input type="text" value="${fn:substring(document.publishTime,0,19)}" readonly/>

        </div>
</c:if>
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
    <p onclick="viewContent()">
        查看正文
    </p>

</div>
<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
        <p>正在准备文档...</p>
    </div>
</div>

</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var module = "<%=session.getAttribute("module")%>";
    var isRead = "${param.isRead}";
   function viewContent(){
    var status = "${document.status}";
     var scene = "${document.scene}";
    var docId = "${document.docId}";

    var title = $("#title").val();

       if (scene == "00") {

           var companyID = "${document.companyID}";
           var companyName =$("#comapnyName").val();
           if (status == "K") {

               document.location.href = basePath + "ea/contract/ea_getFilePre.jspa?doc.scene=" + scene + "&doc.companyID=" + companyID + "&doc.docId=" + docId;
           } else if (status == "F") {
               document.location.href = basePath+"ea/contract/ea_viewUrl.jspa?doc.docId="+docId;


//               var ulp = basePath
//                       + "ea/contract/sajax_ea_viewUrl.jspa";
//               $.ajax({
//                   type: "GET",
//                   url: ulp,
//                   async: false,
//                   dataType: "json",
//                   data: {
//                       "doc.docId": docId
//                   },
//                   success: function (data) {
//                       var member = eval('(' + data + ')');
//                       var viewUrl = member.viewUrl;
//                     //  document.location.href = viewUrl;
//                       document.location.href =  basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(viewUrl);
//
//
//                   },
//                   error: function (data) {
//
//                       console.log("获取链接失败");
//                   }
//
//
//               });
           }else if (status == "A") {
               //发起签约
               document.location.href = basePath+"ea/contract/ea_getSignUrl.jspa?doc.scene="+scene+"&doc.companyID="+companyID+"&doc.docId="+docId;

//               var ulp = basePath
//                       + "ea/contract/sajax_ea_getSignUrl.jspa";
//               $.ajax({
//                   type: "GET",
//                   url: ulp,
//                   async: false,
//                   dataType: "json",
//                   data: {
//                       "doc.scene": scene,
//                       "doc.companyID": companyID,
//                       "doc.docId": docId
//                   },
//                   success: function (data) {
//                       var member = eval('(' + data + ')');
//                       var signUrl = member.signUrl;
//                       var docId = member.docId;
//                       if(signUrl!="") {
//                           //document.location.href = signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId);
//
//                           var url =  signUrl + "&backUrl=" + encodeURI(basePath + "ea/contract/ea_updateState.jspa?doc.docId=" + docId);
//                           document.location.href =  basePath+"ea/androiddoc/ea_jumpJzq.jspa?url="+encodeURIComponent(url);
//                       }
//
//
//                   },
//                   error: function (data) {
//
//                       console.log("获取链接失败");
//                   }
//
//
//               });
           }
       }else {
           var ulp = basePath
                   + "ea/contract/sajax_ea_getPdfView.jspa";
           $(".loading").show();
           $.ajax({
               type: "GET",
               url: ulp,
               async: false,
               dataType: "json",
               data: {
                   "doc.docId": docId
               },
               success: function (data) {
                   var member = eval('(' + data + ')');
                   var pdfpath = member.pdfpath;


                   $(".loading").hide();
                   window.location.href = basePath + "page/ea/main/office_ea/document/pdfView.jsp?pdfpath=" + pdfpath + "&docId=" + docId + "&status=" + status + "&filetype=2&title=" + title + "&isRead=" + isRead;


               },
               error: function (data) {

                   console.log("获取链接失败");
               }


           });
       }
}

</script>
</html>
