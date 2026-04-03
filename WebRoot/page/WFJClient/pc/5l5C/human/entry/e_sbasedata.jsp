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
    <link href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_sbaseinfo.css" rel="stylesheet"
          type="text/css" />

    <script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_sbaseinfo.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/date.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/iscroll.js"
	type="text/javascript"></script>

    <script src="<%=basePath%>js/chosen_v1.8.7/chosen.jquery.js"
            type="text/javascript"></script>
    <link href="<%=basePath%>js/chosen_v1.8.7/chosen.min.css" rel="stylesheet"
          type="text/css" />
    <title>&lrm;</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";

	var sccId="${param.sccId}";
	var staffID = "${param.staffID}";

	$(function() {

		$('#endTime').date({
			theme : "datetime"
		});

        $("#endTimejt").click(function(){
            $('#endTime').trigger("click");

        });
	});
	
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><a onclick="javascript: window.history.go(-1);return false;" target="_self" ><img src="<%=basePath%>images/ea/office/contract/selectp/return.png"></a></li>
            <li>基本资料</li>
            <li>
                <c:if test="${param.view ne 'view'}">
                <span id="commit">保存</span>
                </c:if>
            </li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
    <form action="" id="from1" method="post" name="form" enctype="multipart/form-data">
    <input type="submit" style="display:none;" name="submit" value="" />
	<input type="hidden" id="staffID" name="staff.staffID" value="${staff.staffID}">

        <div class="basics">
            <div class="basics_mil flex-vc">
                <p style="width: 50%;">照片</p>
                <div class="bas_head clearfix">
                     <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                    
                    <div><img src="<%=basePath%>${staff.photo}"  onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"' alt="" id="image_box"></div>
                     <input type="file" name="personImageInfo" accept="image/jpg,image/png" id="head_img" style="width: 6rem;height: 4rem;float:right;margin-right:-5rem;opacity: 0;">
                </div>
            </div>
            <div class="basics_mil basics_mil2">
                <p>姓名</p>
                <input type="text" id="staffName" name="staff.staffName" value="${staff.staffName}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>性别</p>
                <p id="sex">
                ${staff.sex}</p>
                <input type="hidden" id="sexs" name="staff.sex" value="${staff.sex}">
                <div class="jiantou" id="sexjt">
                    <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                </div>
                
            </div>
            <div class="basics_mil basics_mil2" id="rxsj">
                <p>出生日期</p>
                <input class="ipt_1" id="endTime" type="text" name="staff.birthday" value="${staff.birthday}" >
                <div class="jiantou" id="endTimejt">
                   <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                </div>
            </div>
            <div class="basics_mil basics_mil2" >
                <p>身份证号</p>
                <input type="type"
                       name="staff.staffIdentityCard" value="${staff.staffIdentityCard}" id="staffIdentityCard" >

            </div>
            <%--<div >--%>
                <%--<p>人员往来关系</p>--%>
                <%--<select multiple="true" class="chosen-select form-control" id="teachpermitted" datatype="*" data-val="C1" name="teachpermitted" data-placeholder="请选择" required="" style="display: none;"><option value="A1">A1</option><option value="A2">A2</option><option value="A3">A3</option><option value="B1">B1</option><option value="B2">B2</option><option value="C1">C1</option><option value="C2">C2</option><option value="C3">C3</option><option value="C4">C4</option><option value="C5">C5</option><option value="C6">C6</option><option value="D">D</option><option value="E">E</option><option value="F">F</option><option value="M">M</option><option value="N">N</option><option value="P">P</option></select>--%>

            <%--</div>--%>
            <div class="basics_mil basics_mil2" >
                <p>学历</p>
                <input type="type"
                 name="staff.culturalDegree" value="${staff.culturalDegree}"  >
                
            </div>
            <div class="basics_mil basics_mil2">
                <p>籍贯</p>
                <input type="text" id="nativePlace" name="staff.nativePlace" value="${staff.nativePlace}" >
                
            </div>
            <div class="basics_mil basics_mil2">
                <p>民族</p>
                <input type="text" id="nation" name="staff.nation" value="${staff.nation}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>现居住城市</p>
                <input type="text" id="staffAddress" name="staff.staffAddress" value="${staff.staffAddress}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>联系电话</p>
                <input type="text" id="reference" name="staff.reference" value="${staff.reference}" >
            </div>
            <div class="basics_mil basics_mil2">
                <p>人员多级分类</p>
                <input type="hidden" id="st"  value="${staff.status}" >
                <s:select  cssClass="st" list="#{'00':'一般人物','01':'政界人物','02':'商界人物','03':'学术界人物','04':'艺术界人物','05':'科学界人物'}"
                          listKey="key" id="status" listValue="value" name="staff.status"
                          theme="simple"></s:select>
            </div>

            <div class="basics_mil basics_mil2">
                <p>数字地球账号</p>
                <input type="hidden" id="sccId" name="staff.sccId" />
                <input type="text" id="acc">
            </div>
        </div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
                framespacing="0" height="0"></iframe>
  </form>
    </div>
    <div id="datePlugin"></div>
    <div class="job-int_gzxz">
        <div class="job-int_d3"></div>
        <div class="job-int_d1">
            <div class="job-int_d">
                <button id="btn_qx">取消</button>
                <button id="btn_wc">完成</button>
                <div class="clearfix"></div>
            </div>
            <div class="job-int_d2">
                <p class="confirmationBox">男</p>
                <p>女</p>
            </div>
        </div>
        <div class="clearfix"></div>
    </div>

    <script type="text/javascript">


        $("#teachpermitted").chosen({width: "100%",search_contains: true,no_results_text: "未找到"});
        var teachpermittedArry = new Array();
        var teachpermittedStr = $("#teachpermitted").attr("data-val");
        var selectedOption2 = teachpermittedStr.split(",");
        teachpermittedArry.push('<option value="A1">A1</option>');
        teachpermittedArry.push('<option value="A2">A2</option>');
        teachpermittedArry.push('<option value="A3">A3</option>');
        teachpermittedArry.push('<option value="B1">B1</option>');

        $('#teachpermitted').html(teachpermittedArry.join(''));
        // 选中的值依然被选中
        $('#teachpermitted').val(selectedOption2);
        $('#teachpermitted').trigger('chosen:updated');

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