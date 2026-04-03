<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

 <title>简历管理</title>

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
        <h1>简历管理</h1>
<!--         <a href="javascript:;" class="head_R">保存</a>
 -->    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
           <section>
           <c:forEach items="${listobj}" var="k">
               <div class="resume_m_box">
                    <a href="###" class="resume_m_bd">
                       <p>${k[2]}（${k[3]}）</p>
                       <p>求职：${k[4]}</p>
                    	<input type="hidden" id="staffid" value="${k[0]}">
                       <p><span class="resume_info">${k[5]} | ${k[6]}</span></p>
                    </a>
                    <div class="box_btn3_fd clearfix">
                            <a href="###"><i class="resume_edit"></i><span onclick="edit(this)">
                             <input type="hidden" id="resumeID" value="${k[1]}">
                             <input type="hidden" id="privacy" value="${k[10]}">
                            	编辑简历</span></a>
                            <a href="###" class="toggle_state"><i></i><span>发布中</span><span>未发布</span></a>
                            <c:if test="${k[8] !=null&&k[9]!=null&&k[1]!=null}">
                            <a href="javascript:;" class="act_dele"><i class="act_dele_btn"></i><span onclick="del(this)">
                            <input type="hidden" id="keys1" value="${k[8]}">
                             <input type="hidden" id="keys2" value="${k[9]}">
                             <input type="hidden" id="resumeIDs" value="${k[1]}">
                            			删除</span></a>
                             </c:if>
                    </div>
               </div>
               </c:forEach>
              
           </section>
    </div>
    <!--  页面内容 end -->
    <div class="act_fd">
        <a id="addedu"><i class="act_add_btn"></i><span >添加简历</span></a>
    </div>
    <script type="text/javascript">

        $(function () {
            $(".toggle_state").each(function () {
                var privacy = $(this).prev().find("#privacy").val();
                if(privacy=="00"){
                    $(this).addClass("resume_state");
                }else if (privacy=="01"){
                    $(this).removeClass("resume_state");
                }
            })
        })
    //编辑简历
    function edit(obj){
    var resumeID = $(obj).find("#resumeID").val();
    var url = basePath + "ea/resumes/ea_editManagement.jspa?resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	document.location.href = url;
	
    }
    //删除简历
    function del(obj){
   	var keys1 = $(obj).find("#keys1").val();
   	var keys2 = $(obj).find("#keys2").val();
   	var resumeID = $(obj).find("#resumeIDs").val();
    var url = basePath + "ea/resumes/ea_deletManagement.jspa?keys1="+keys1+"&keys2="+keys2+"&staffid="+staffid+"&resumeID="+resumeID+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
	document.location.href = url;
	      
    }
     $(document).ready(function(){
         var back = "${param.back}";
    	 //$("#returnClick").click(function() {history.go(-1)});
          $("#returnClick").click(function() {
              if(back=="1"){
                  document.location.href = basePath+"ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs&current=jl";
              }else {
                  document.location.href = basePath+"page/ea/main/production/resume/resumeManagement/candidates.jsp?staffid=" + staffid + "&sccId=" + sccId + "&jitype=" + jitype;
              }
         });
  	 	//添加简历
    	 $("#addedu").click(function() {
	        	var url = basePath + "ea/resumes/ea_savePersion.jspa?staffid="+staffid+"&type=save"+"&sccId="+sccId+"&jitype="+jitype+"&back="+back;
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
        $(function(){
            //点击发布中 未发布切换样式状态
            $(".toggle_state").click(function(){
                var $this = $(this);
                var resumeID =  $this.prev().find("#resumeID").val()
                var privacy = $this.prev().find("#privacy").val()
                if(privacy=="00"){
                    privacy="01";
                }else if (privacy=="01"){
                    privacy="00";
                }
                $.ajax({
                    url:basePath+"ea/resumes/sajax_ea_updateRelease.jspa?privacy="+privacy+"&resumeID="+resumeID,
                    type:"POST",
                    async:false,
                    dataType:"json",
                    success:function (data) {
                        $this.toggleClass("resume_state");
                        $this.prev().find("#privacy").val(privacy);
                    }
                })
            })
        	
        })

    </script>
</body>

</html>
