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

<title>公司招聘</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var staffid = "${param.staffid}";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	var back = "${param.back}";
</script>
</head>
<body>
    <!-- header start  -->
    <header class="mem_header">
       <a id="returnClick" class="back"></a>
        <h1>公司招聘</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section class="pzp_bottom">
            <a id="card"  class="pzp_manger_box">
                <span class="pzp_L pzp_list02"></span>
                <span class="pzp_R">职位发布</span>
            </a>
            <a id="Invitation"  class="pzp_manger_box">
                <span class="pzp_L pzp_list01"></span>
                <span class="pzp_R">职位管理</span>
            </a>
            <a id="resume"  class="pzp_manger_box">
                <span class="pzp_L pzp_list03"></span>
                <span class="pzp_R">简历管理</span>
            </a>
                <a id="collect"  class="pzp_manger_box">
                    <span class="pzp_L pzp_list04"></span>
                    <span class="pzp_R">简历收藏</span>
                </a>
        </section>
    </div>
    <!--  页面内容 end -->
    <script>

    $(document).ready(function(){
     $("#returnClick").click(function() {
        if(back=="2"){
        document.location.href = basePath+"mobile/office/mobileoffice_fastApplication.jspa";
        }else if(back=="3"){
            window.history.back();
            return false;
            // document.location.href = basePath+"ea/vipcenter/ea_vipDemand.jspa?sccid="+sccId;
         }else{
            history.back();
            return false;
        }




     });


	         //发布招聘
	         $("#card").click(function() {
                 document.location.href = basePath + "ea/bidrecruit/ea_getPositionPub.jspa?sccId="+sccId+"&back="+back;
	      
	        });
	          //职位管理
	         $("#Invitation").click(function() {
                 document.location.href = basePath + "ea/bidrecruit/ea_findPositionList.jspa?sccId="+sccId+"&back="+back;
	        });
	        $("#resume").click(function() {
				document.location.href = basePath +  "ea/bidrecruit/ea_getTalentResumeList.jspa?sccId="+sccId+"&back="+back;
	      
	        });
	        $("#collect").click(function() {
                document.location.href = basePath +  "ea/bidrecruit/ea_getCollectResume.jspa?sccId="+sccId+"&back="+back;
	      
	        });
	        
	       
    });
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }



    </script>
</body>

</html>
