<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">

    <link href="<%=basePath%>css/ea/production/common2.css" rel="stylesheet"
          type="text/css" />
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_addQuestions.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_addQuestions.js"
	type="text/javascript"></script>

    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
    var  qrID = "${questions.qrID}";
    var p = "${param.p}";
    var  seqlast = "${questionslast.seq}";
    var scorelast = "${questionslast.score}";
    var quesTypelast = "${questionslast.quesType}";
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
            <li>编辑题目</li>
            <li>

            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
    <form action="" id="from1" method="post" name="form" enctype="multipart/form-data">
    <input type="submit" style="display:none;" name="submit" value="" />
	<input type="hidden" id="qrID" name="questions.qrID" value="${questions.qrID}">
    <input type="hidden" name="questions.tqID" value="${questions.tqID}">


        <div class="basics">
            <div class="basics_mil basics_mil2">
                <p>序号</p>
                <input type="text" id="seq" name="questions.seq" value="${questions.seq}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>题型</p>
                <input type="text" id="est" style="display: none;" readonly value="${questions.quesType=='00'?'单选题':questions.quesType=='01'?'多选题':questions.quesType=='02'?'判断题':'简答题'}" >
                <input type="hidden" id="st"  value="${questions.quesType}" >
                <s:select  cssClass="st" list="#{'00':'单选题','01':'多选题','02':'判断题','03':'简答题'}"
                           listKey="key" id="quesType" listValue="value" name="questions.quesType" headerValue="00"
                           theme="simple"></s:select>
            </div>
            <div class="basics_mil basics_mil2">
                <p>题目</p>
                <input type="text" id="title" name="questions.title" value="${questions.title}" >
            </div>
            <div class="basics_mil flex-vc">
                <p style="width: 50%;">图片(选填)</p>
                <div class="bas_head clearfix">
                     <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                    <input type="hidden"  name="questions.picpath" value="${questions.picpath}" >
                    <div><img src="<%=basePath%>${questions.picpath}"  onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"' alt="" id="image_box"></div>
                     <input type="file" name="personImageInfo" accept="image/jpg,image/png" id="head_img" style="width: 6rem;height: 4rem;float:right;margin-right:-5rem;opacity: 0;">
                </div>
            </div>

            <div class="basics_mil basics_mil2">
                <p>分值</p>
                <input type="text" id="score" name="questions.score" value="${questions.score}" >
                <input type="hidden" name="yscore" value="${questions.score}" >
            </div>

            <div class="basics_mil basics_mil2" id="rxsj">
                <p><span>答案选项</span><img class="img001" src="<%=basePath%>images/WFJClient/pc/5l5c/plus.png"><span class="tip">单选题最多4个选项</span></p>
            </div>
            <div class="basics_mil basics_mil2 option-a" style="display: none;">
                <div><div class="letter">A</div></div>
                <input type="hidden" class="codeName"/>
                <input type="text"  class="input-bj codeValue"    placeholder="请填写选项内容"  />
                <img class="img002" src="<%=basePath%>images/ea/office/contract/selectp/jy.png">

            </div>
            <c:if test="${not empty valueList}">
            <c:if test="${valueList.size() > 0}">

            <c:forEach  items="${valueList}" var="item" varStatus="v">
                <div class="basics_mil basics_mil2 news">
                    <div><div class="letter">${item.codeName}</div></div>
                    <input type="hidden" name="valueMap[${v.index+1}].qrvID" value="${item.qrvID}"/>
                    <input type="hidden" class="codeName"  name="valueMap[${v.index+1}].codeName" value="${item.codeName}"/>
                    <input type="text"  class="input-bj codeValue"   name="valueMap[${v.index+1}].codeValue"   placeholder="请填写选项内容"  value="${item.codeValue}"/>
                    <img class="img002" src="<%=basePath%>images/ea/office/contract/selectp/jy.png">

                </div>

            </c:forEach>

            </c:if>
            </c:if>
            <div class="basics_mil basics_mil2">
                <p>正确答案</p>
                <p id="sex">${questions.correctAnswer}</p>
                <input type="hidden" id="sexs" name="questions.correctAnswer" value="${questions.correctAnswer}">
                <div class="jiantou" id="sexjt">
                    <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                </div>

            </div>
            <div class="div-bottom">
                <p class="saveDraft">
                    保存
                </p>

            </div>
        </div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
                framespacing="0" height="0"></iframe>
  </form>
    </div>

    <div class="job-int_gzxz">
        <div class="job-int_d3"></div>
        <div class="job-int_d1">
            <div class="job-int_d">
                <button id="btn_qx">取消</button>
                <button id="btn_wc">完成</button>
                <div class="clearfix"></div>
            </div>
            <div class="job-int_d2">
                <%--<p>A</p>--%>
                <%--<p>B</p>--%>
                <%--<p>C</p>--%>
                <%--<p>D</p>--%>
                <%--<p>E</p>--%>
                <%--<p>F</p>--%>
                <%--<p>G</p>--%>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>

    <script type="text/javascript">


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
      
    </script>


    <!--表单提示-->
    <div class="div-tingyong div-dqd">
        <div class="box">
            <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
            <div class="div-box">
                <p class="titlep"></p>
                <div class="clearfix">
                    <p class="left close-tingyong">取消</p>
                    <p class="right close-confirm">确定</p>
                </div>
            </div>
        </div>
    </div>

</body>
</html>