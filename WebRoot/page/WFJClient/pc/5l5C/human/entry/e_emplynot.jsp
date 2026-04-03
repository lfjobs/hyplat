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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_emplynot.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_emplynot.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

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
            入职通知
        </li>
    </ul>
</header>

<form  id="form" name="form" method="post">
    <input type="submit" style="display: none" name="submit">
<div class="content" >
    <div class="div-name">
        <label for="">姓名</label>
        <input type="text"  value="${audition.staffName}" readonly/>
    </div>

    <div class="div-name">
        <label for="">身份证号</label>
        <input type="text"  value="${audition.staffIdentityCard}" readonly/>
    </div>
    <div class="div-name">
        <label for="">手机号</label>
        <input type="text"  value="${audition.staffIdentityCard}" readonly/>
    </div>
    <div class="div-name">
        <label for="">应聘岗位</label>
        <input type="text"  value="${audition.auditionPost}" readonly/>
    </div>
    <div class="div-name">
        <label for="">应聘者简历</label>
        <input type="text"  value="查看" readonly  onclick="viewJl()"/>

    </div>
    <div class="div-name">
        <label for="">面试考官</label>
        <input type="text"  name="audition.examiner"  value="${audition.examiner}" readonly/>

    </div>


    <input type="hidden"  name="audition.auditionID" value="${audition.auditionID}"/>
    <input type="hidden"  name="vm" value="${param.vm}"/>
        <div class="div-name">
            <label for="">笔试得分：</label>
            <input type="text"  value="${auditionbc.auditionPointb}" readonly  name="audition.auditionPointb"/>

        </div>
        <div class="div-name">
            <label for="">笔试评语：</label>
            <input type="text"  value="${auditionbc.commentionb}"  readonly name="audition.commentionb"/>

        </div>
        <div class="div-name">
            <label for="">口试评分：</label>
            <input type="text"  value="${auditionbc.auditionPointk}"  readonly name="audition.auditionPointk"/>

        </div>
        <div class="div-name">
            <label for="">口试评语：</label>
            <input type="text"  value="${auditionbc.commentionk}" readonly  name="audition.commentionk"/>

        </div>

        <div class="div-name">
            <label for="">综合得分：</label>
            <input type="text"  value="${auditionbc.auditionPoint}"  readonly name="audition.auditionPoint"/>

        </div>
        <div class="div-name">
            <label for="">综合评语：</label>
            <input type="text"  value="${auditionbc.commention}"  readonly name="audition.commention"/>

        </div>

       <div class="div-name">
        <label for="">报到时间：</label>
        <input type="text"  value="${auditionbc.registerDate}"  class="bj"  name="audition.registerDate" onfocus="daytime(this);"/>

       </div>
    <div class="div-name">
        <label for="">报到地点：</label>
        <input type="text"  value="${auditionbc.auditionPlace}"  class="bj"  name="audition.auditionPlace"/>

    </div>
    <div class="div-name">
        <label for="">报到联系人：</label>
        <input type="text"  value="${auditionbc.contactor}"  class="bj"  name="audition.bdcontactor"/>

    </div>
    <div class="div-name">
        <label for="">报到电话：</label>
        <input type="text"  value="${auditionbc.contactTel}"  class="bj"  name="audition.bdcontactortel"/>

    </div>
</div>
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</form>


<div class="div-bottom">
    <p onclick="bdnotice()">
        确认入职通知
    </p>


</div>

<!--表单提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep">操作成功</p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>

</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";


    function viewJl(){
        var tpId = "${talentPool.tpId}";
        var resID = "${talentPool.resID}";
        var resumeID ="${talentPool.resumeID}";
        var sccId = "${sccId}";
        var type =  "${talentPool.type}";
        var r = "";
        if(type=="00"){
            r =resID;
        }else{
            r =resumeID;
        }
        window.location.href = basePath+"ea/bidrecruit/ea_resumedetail.jspa?sccId="+sccId+"&resumeID="+r+"&tpId="+tpId+"&view=v";

    }


</script>
</html>
