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
   <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    
        <link rel="stylesheet" href="<%=basePath%>css/ea/production/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/zepto.min.js"></script>
<link href="<%=basePath%>css/ea/production/stylezt.css"
	rel="stylesheet" type="text/css" />

<%--<title>个人应聘</title>--%>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var staffid = "${param.staffid}";
	if(staffid==""){
        staffid = "${staffid}";
    }
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
    if(sccId==""){
        sccId = "${sccId}";
    }
</script>
</head>
<body>
<%--<div id="myModal" class="modal">--%>
    <%--<div class="modal-content">--%>
        <%--<span class="close">&times;</span>--%>
        <%--<p>招聘业务正在完善中，暂不支持。</p>--%>
        <%--<button id="confirmBtn">确认</button>--%>
    <%--</div>--%>
<%--</div>--%>
    <!-- header start  -->
    <header class="mem_header">
       <a id="returnClick" class="back"></a>
        <h1>个人应聘</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="pzp_top">
            <a id="manger" class="pzp_manger_box">
                <span class="pzp_L pzp_list01"></span>
                <span class="pzp_R">简历管理</span>
            </a>
        </section>
        <section class="pzp_bottom">
          <a id="card"  class="pzp_manger_box">
                <span class="pzp_L pzp_list02"></span>
                <span class="pzp_R">我的投递记录</span>
            </a>
            <a id="Invitation"  class="pzp_manger_box">
                <span class="pzp_L pzp_list03"></span>
                <span class="pzp_R">我的职位邀请</span>
            </a>
            <a id="postion"  class="pzp_manger_box">
                <span class="pzp_L pzp_list04"></span>
                <span class="pzp_R">我的职位关注</span>
            </a>
            <a id="myexam"  class="pzp_manger_box">
                <span class="pzp_L pzp_list05"></span>
                <span class="pzp_R">我的考试</span>
            </a>
        </section>
    </div>
    <!--  页面内容 end -->
    <script>
    
   /*  function callAndroidjianli(){
    	
    	
    	
    		 if(jitype=="demand"){
    			
    			 document.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?jitype=demand"
    					
    		 }else {
    			 
    			 document.location.href = basePath+"/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
    		 }
    	} */ 
    $(document).ready(function(){
     $("#returnClick").click(function() {


         window.history.back();
         return false;

     });
      var u = navigator.userAgent;
	var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

//	if(isAndroid==true){
//
//		var obj = document.getElementById("returnClick");
//		obj.setAttribute("onclick", "javascript:Android.callAndroidjianli()");
//
//	}else if(isiOS==true){
//
//		var obj = document.getElementById("returnClick");
//		obj.setAttribute("onclick", "cal()");
//	}
	
    //简历管理
    	 	$("#manger").click(function() {
	        	var url = basePath + "ea/resumes/ea_resumeManagement.jspa?staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
				document.location.href = url;
	      
	        });
	         //投地记录
	         $("#card").click(function() {
	        	var url = basePath + "ea/resumes/ea_getRecord.jspa?staffid="+staffid+"&type=00";
				document.location.href = url;
	      
	        });
	          //邀请的
	         $("#Invitation").click(function() {
	        	//var url = basePath + "page/ea/main/production/resume/resumeManagement/Invitation.jsp?staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
                 var url = basePath + "ea/resumes/ea_getRecord.jspa?staffid="+staffid+"&type=01";
				document.location.href = url;
	        });
	        $("#manger").click(function() {
	        	var url = basePath + "ea/resumes/ea_resumeManagement.jspa?staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
				document.location.href = url;

	        });
	        $("#postion").click(function() {
	        	var url = basePath + "ea/resumes/ea_getPostion.jspa?staffid="+staffid+"&sccId="+sccId+"&jitype="+jitype;
				document.location.href = url;
	      
	        });
        $("#myexam").click(function() {
            var url = basePath + "ea/quest/ea_myExam.jspa";
            document.location.href = url;

        });


    });
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        //安卓的调用
function callAndroidjianli(){
	
	
	try {
		
		 Android.callAndroidjianli();
		}catch(err) {
			 if(jitype=="demand"){
    			 
    			 document.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?jitype=demand"
    					
    		 }else {
    			
    			 document.location.href = basePath+"/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
    		 }
		}
	
		
	
	
	}

//苹果的调用‘’
function cal(){
	try {
		calliosjianli("");
		}catch(err) {
			 if(jitype=="demand"){
    			 
    			 document.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?jitype=demand"
    					
    		 }else {
    			
    			 document.location.href = basePath+"/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs";
    		 }
		}
	}
 
    </script>
</body>
<script>
    // 获取弹框元素
    var modal = document.getElementById('myModal');
    // 获取关闭按钮元素
    var closeBtn = document.getElementsByClassName('close')[0];
    // 获取确认按钮元素
    var confirmBtn = document.getElementById('confirmBtn');

    // 显示弹框
    function showModal() {
        if (typeof Android !=='undefined'){
            modal.style.display = "block";
        }else {
            modal.style.display ="none"
        }
    }

    // 点击关闭按钮时关闭弹框
    closeBtn.onclick = function () {
        window.history.back();
    }

    // 点击确认按钮时关闭弹框
    confirmBtn.onclick = function () {
        window.history.back();
    }

    // 点击弹框外部区域时关闭弹框
    window.onclick = function (event) {
        if (event.target === modal) {
            window.history.back();
        }
    }

    // 示例：页面加载完成后显示弹框
    window.onload = function () {
        showModal();
    }
</script>
<!-- ... existing code ... -->
<!-- ... existing code ... -->
<style>
    /* 弹框背景 */
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgba(0, 0, 0, 0.4);
    }

    /* 弹框内容 */
    .modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 30%;
        text-align: center;
    }

    /* 关闭按钮 */
    .close {
        color: #aaa;
        float: right;
        font-size: 28px;
        font-weight: bold;
    }

    .close:hover,
    .close:focus {
        color: black;
        text-decoration: none;
        cursor: pointer;
    }

    /* 确认按钮 */
    #confirmBtn {
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border: none;
        cursor: pointer;
    }

    #confirmBtn:hover {
        background-color: #45a049;
    }
</style>
</html>
