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
<link href="<%=basePath%>css/ea/production/stylezt.css" rel="stylesheet"
	type="text/css" />
<link href="<%=basePath%>css/ea/production/common2.css" rel="stylesheet"
	type="text/css" />
	<link href="<%=basePath%>css/ea/production/styleresume.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/date.js"
	type="text/javascript"></script>

<script src="<%=basePath%>js/ea/production/resume/iscroll.js"
	type="text/javascript"></script>

<title>基本资料</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	var   back = "${param.back}";
	$(function() {
		$('#beginTime').date();
		$('#endTime').date({
			theme : "datetime"
		});
	});
	
</script>
</head>
<body>
    <div class="res_top">
        <ul>
            <li><img src="<%=basePath%>images/resume/left_jt.png" alt="" id="returnClick"></li>
            <li>基本资料</li>
            <li><a id="commit">保存</a></li>
            <div class="clearfix"></div>
        </ul>
    </div>
    <div class="res_bot">
    <form action="" id="from1" method="post" enctype="multipart/form-data">
    <input type="submit" style="display:none;" value="" id="sub2" />
	<input type="hidden" id="staffKey" name="staff.staffKey" value="${staff.staffKey}">
	<input type="hidden" id="staffID" name="staff.staffID" value="${staff.staffID}">
	<input type="hidden" id="staffid"  value="${staff.staffID}">
	<input type="hidden" id="groupCompanySn" name="staff.groupCompanySn"  value="${staff.groupCompanySn}">
	<input type="hidden" id="staffStatus" name="staff.staffStatus"  value="${staff.staffStatus}">
	<input type="hidden" id="staffCode" name="staff.staffCode"  value="${staff.staffCode}">
	<input type="hidden" id="recordCode" name="staff.recordCode"  value="${staff.recordCode}">
	<input type="hidden" id="chipid" name="staff.chipid"  value="${staff.chipid}">
	<input type="hidden" id="usedNmae" name="staff.usedNmae"  value="${staff.usedNmae}">
	<input type="hidden" id="nation" name="staff.nation"  value="${staff.nation}">

	<input type="hidden" id="passportNum" name="staff.passportNum"  value="${staff.passportNum}">
	<input type="hidden" id="address" name="staff.address"  value="${staff.address}">
	<input type="hidden" id="referenceCode" name="staff.referenceCode"  value="${staff.referenceCode}">
	<input type="hidden" id="weixin" name="staff.weixin"  value="${staff.weixin}">
	<input type="hidden" id="verifyTime" name="staff.verifyTime"  value="${staff.verifyTime}">
	<input type="hidden" id="photo" name="staff.photo"  value="${staff.photo}">
	<input type="hidden" id="politicsStatus" name="staff.politicsStatus"  value="${staff.politicsStatus}">
	<input type="hidden" id="marriage" name="staff.marriage"  value="${staff.marriage}">
	<input type="hidden" id="health" name="staff.health"  value="${staff.health}">
	<input type="hidden" id="bankNum" name="staff.bankNum"  value="${staff.bankNum}">
	<input type="hidden" id="province" name="staff.province"  value="${staff.province}">
	<input type="hidden" id="city" name="staff.city"  value="${staff.city}">
	<input type="hidden" id="staus" name="staff.staus"  value="${staff.staus}">
	<input type="hidden" id="staffOrgName" name="staff.staffOrgName"  value="${staff.staffOrgName}">
	<input type="hidden" id="staffPost" name="staff.staffPost"  value="${staff.staffPost}">
	<input type="hidden" id="staffsm" name="staff.staffsm"  value="${staff.staffsm}">
	<input type="hidden" id="staffupf" name="staff.staffupf"  value="${staff.staffupf}">
	<input type="hidden" id="vipno" name="staff.vipno"  value="${staff.vipno}">
	<input type="hidden" id="password" name="staff.password"  value="${staff.password}">
	<input type="hidden" id="price" name="staff.price"  value="${staff.price}">
	<input type="hidden" id="status" name="staff.status"  value="${staff.status}">
	<input type="hidden" id="source" name="staff.source"  value="${staff.source}">
	<input type="hidden" id="activityId" name="staff.activityId"  value="${staff.activityId}">
	<input type="hidden" id="accountID" name="staff.accountID"  value="${staff.accountID}">
	<input type="hidden" id="accountName" name="staff.accountName"  value="${staff.accountName}">
	<input type="hidden" id="industryType" name="staff.industryType"  value="${staff.industryType}">
	<input type="hidden" id="localAreaValue" name="staff.localAreaValue"  value="${staff.localAreaValue}">
	<input type="hidden" id="educationValue" name="staff.educationValue"  value="${staff.educationValue}">
	<input type="hidden" id="whereCompany" name="staff.whereCompany"  value="${staff.whereCompany}">
	<input type="hidden" id="photoPositive" name="staff.photoPositive"  value="${staff.photoPositive}">
	<input type="hidden" id="photoBack" name="staff.photoBack"  value="${staff.photoBack}">
	<input type="hidden" id="provinceAddress" name="staff.provinceAddress"  value="${staff.provinceAddress}">
	<input type="hidden" id="qrCodePath" name="staff.qrCodePath"  value="${staff.qrCodePath}">
        <input type="hidden"  name="staff.headimage"  value="${staff.headimage}">


        <div class="basics">
            <div class="basics_mil flex-vc">
                <p style="width: 50%;">头像</p>
                <div class="bas_head">
                    <%-- <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right"> --%>
                    
                    <div><img src="<%=basePath%>${staff.headimage}" alt="" id="image_box"></div>
                     <input type="file" name="file" accept="image/jpg,image/png" id="head_img" style="width: 2rem;height: 2rem;position: absolute;right: 1.35rem;opacity: 0;">
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
                <div class="jiantou">
                    <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right">
                </div>
                
                
                
                
                
            </div>
            <div class="basics_mil basics_mil2" id="rxsj">
                <p>出生日期</p>
                <input class="ipt_1" id="endTime" type="text" name="staff.birthday" value="${staff.birthday}" >
                <div class="jiantou">
                    <%-- <img src="<%=basePath%>images/resume/right_jt.png" class="bas_right"> --%>
                </div>
            </div>
            <div class="basics_mil basics_mil2" >
                <p>学历</p>
                <input type="type"
                 name="staff.culturalDegree" value="${staff.culturalDegree}" id="xueli" onblur="this.value=this.value.replace(/[^a-zA-Z\u4e00-\u9fa5]/g,'')">
           
                
            </div>
            <div class="basics_mil basics_mil2">
                <p>籍贯</p>
                <input type="text" id="nativePlace" name="staff.nativePlace" value="${staff.nativePlace}" onblur="this.value=this.value.replace(/[^a-zA-Z\u4e00-\u9fa5]/g,'')">
                
            </div>
            <div class="basics_mil basics_mil2">
                <p>现居住城市</p>
                <input type="text" id="staffAddress" name="staff.staffAddress" value="${staff.staffAddress}" onblur="check3()">
            </div>
            <div class="basics_mil basics_mil2">
                <p>联系电话</p>
                <input type="text" id="reference" name="staff.reference" value="${staff.reference}" onblur="phone()">
            </div>
            <div class="basics_mil basics_mil2">
                <p>身份证号</p>
                <input type="text" id="staffIdentityCard" name="staff.staffIdentityCard" value="${staff.staffIdentityCard}">
            </div>
            <div class="basics_mil basics_mil2">
                <p>邮箱</p>
                <input type="text" id="referenceOrganization" name="staff.referenceOrganization"  value="${staff.referenceOrganization}"  onblur="check()">
            </div>
        </div>
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
                <p>男</p>
                <p>女</p>
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
<script type="text/javascript">
function check1(){
	
	var myReg = /^[\u4e00-\u9fa5]+$/;
    if ($("#xueli").val()==""){
    	alert("学历不能为空");
    	$("#xueli").val("");
    	return
    }else if (!myReg.test($("#xueli").val())) {
    	alert('只能输入中文'); 
    	$("#xueli").val("");
    	return 
    } 
}
function check2(){
	
	var myReg = /^[\u4e00-\u9fa5]+$/;
    if ($("#nativePlace").val()==""){
    	alert("籍贯不能为空");
    	$("#nativePlace").val("");
    	return
    }else if (!myReg.test($("#nativePlace").val())) {
    	alert('只能输入中文'); 
    	$("#nativePlace").val("");
    	return 
    } 
}
function check3(){
	
	var myReg = /^[\u4e00-\u9fa5]+$/;
    if ($("#staffAddress").val()==""){
    	alert("地址不能为空");
    	$("#staffAddress").val("");
    	return
    }else if (!myReg.test($("#staffAddress").val())) {
    	alert('只能输入中文'); 
    	$("#staffAddress").val("");
    	return 
    } 
}
function check(){

 //邮箱
       var EmailReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/; //邮件正则
       
       if($("#referenceOrganization").val() == ''){
    		alert('邮箱还没填呢...'); 
    		$("#referenceOrganization").val("");
    	}else if(!EmailReg.test($("#referenceOrganization").val())){
			alert('邮箱格式错咯...'); 
			$("#referenceOrganization").val("");
		}
        

}
function phone(){
var phone = $("#reference").val(); 

 var PhoneReg = /^0{0,1}(13[0-9]|15[0-9]|153|156|18[0-9]|19[0-9])[0-9]{8}$/ ; //手机正则
if(phone == ''){
	alert('手机还没填呢...'); 
	$("#reference").val(""); 
	return
}else if(!PhoneReg.test(phone)){
	alert('手机格式错咯...'); 
	$("#reference").val(""); 
	return
} 

}


       
       
$(document).ready(function(){
 $("#returnClick").click(function() {history.go(-1);});
	
    $("#sex").click(function(){
       $(".job-int_gzxz").show();
    });
    $("#commit").click(function() {
    	var phone = $("#reference").val(); 
    	if(phone == ''){
    		alert('手机还没填呢...'); phone.val("");
    		return
    	}else if($("#referenceOrganization").val() == ''){
    		alert('邮箱还没填呢...'); phone.focus();
    		return
    	}else if($("#xueli").val()==""){
    		alert('学历还没填呢...'); 
    		$("#xueli").val("");
    		return
    	}else if($("#nativePlace").val()==""){
    		alert('籍贯还没填呢...'); 
    		$("#staffAddress").val("");
    		return
    	}else if($("#staffAddress").val()==""){
    		alert('地址还没填呢...'); 
    		$("#staffAddress").val("");
    		return
    	}
    var resumeID = "${resumeID}";
    var staffid=$("#staffid").val();
     $("#from1").attr("action",basePath+ "ea/resumes/ea_saveEdit.jspa?type=saveUpdate&staffid="+staffid+"&resumeID="+resumeID+"&jitype="+jitype+"&back="+back);
				$("#sub2").click();
				});
    });
	$(".job-int_d2 p").click(function(){
		var job=$(this).text();
		$("#sex").text(job);
		
		$("#sexs").val(job);
	})
</script>
<script type="text/javascript">

function edus(){
	var staffKey = $("#staffKey").val();
	var staffID = $("#staffID").val();
	var staffName = $("#staffName").val();
	var endTime = $("#endTime").val();
	var culturalDegree = $("#culturalDegree").text();
	var nativePlace = $("#nativePlace").val();
	var staffAddress = $("#staffAddress").val();
	var reference = $("#reference").val();
	var sex = $("#sex").text();


var referenceOrganization = $("#referenceOrganization").val();
var url = basePath + "page/ea/main/production/resume/degreeBasicdata.jsp?staffKey="+staffKey
					+"&staffID="+staffID
					+"&staffName="+staffName
					+"&endTime="+endTime
					+"&culturalDegree="+culturalDegree
					+"&nativePlace="+nativePlace
					+"&staffAddress="+staffAddress
					+"&reference="+reference
					+"&referenceOrganization="+referenceOrganization;
					
document.location.href = url;
	}
    function ifImgNotExist(){
    var img=event.srcElement;
    img.src="imges/head.png";
    img.onerror=null; //取消它的onerror事件
}
</script>
</body>
</html>