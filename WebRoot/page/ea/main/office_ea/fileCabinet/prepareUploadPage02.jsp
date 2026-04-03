
<%@page import="hy.ea.util.DateUtil"%>
<%@page import="hy.ea.util.RandomDatas"%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="x-ua-compatible" content="ie=7" />
		<title>图片库</title>
		<%@ page language="java" pageEncoding="UTF-8" import="java.io.*"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/style.css" type="text/css"
			charset="utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script
			src="<%=basePath%>js/ea/office_ea/fileCabinet/prepareUploadPage2.js"></script>
		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<script
			src="<%=basePath%>swfupload/swfupload.js"></script>
		<script
			src="<%=basePath%>swfupload/files_upload.js"></script>
		<script type="text/javascript">
         var  basePath='<%=basePath%>';
 </script>
		<style type="text/css">
		a {
	text-decoration: none;
	color: #946652;
}
.sep {
	background: url(<%=basePath%>images/admin_images/act_btn00.gif)
		no-repeat;
	height: 24px;
	width: 70px;
	cursor: pointer;
	border: solid 0px #111;
	font-size: 10px;
}
</style>
		<style type="text/css">
body {
	font-family: Arial;
}

* {
	margin: 0px;
	padding: 0px;
}

div,ul,li,dl,dd,dt {
	list-style: none;
	margin: 0px auto;
}

.upload_bg {
	width: 680px;
	float: left;
	height: 390px;
	border: #a2c1e0 solid 1px;
	background: url(bg.jpg) repeat-x;
	margin-top: 10px;
}

.upload_top {
	width: 98%;
	height: 40px;
}

.upload_Center {
	width: 98%;
	height: 28px;
	line-height: 28px;
}

.upload_Bottom {
	width: 98%;
	height: 300px;
	background-color: #FFF;
	border: #a2c1e0 solid 1px;
}

.upload_top ul li {
	float: left;
	width: 90px;
	padding-top: 10px;
}

.upload_top ul .fr {
	float: right;
}


.fr {
	float: right;
}

.ml10 {
	margin-left: 10px;
}

</style>
	</head>
	<body style="margin: 0px; padding: 0px;">
		<div style="margin-left: 10px; margin-top: 5px;">
			<font size="+1"color="#946652">上传图片</font>
			
			
		</div>
		<div
			style="width: 800px; margin-top: 10px; margin-left: 30px; align: center;">
			选择图片库：
			<select id="selectbox" size="1" style="width:150px;">
			</select>&nbsp;&nbsp;<a href="javascript:history.back();">返回</a>
		</div>
		<div class="upload_bg">
			<div class="upload_top">
				<ul>
					<li>
					<span id="uploads"></span>
					</li>
					<li class="ml10">
						<img src="<%=basePath%>images/ea/office/fileCabinet/Up.jpg" style="cursor:pointer;"onclick="uploads();" id="BtnSave"/>
					</li>
				<!--  <li class="fr">
						<img src="<%=basePath%>images/ea/office/fileCabinet/del.jpg" style="cursor:pointer;"onclick="delLocalPic();"/>
					</li>-->	
				</ul>
			</div>
			<div class="upload_Center" id="label">
				请先添加要上传的图片，然后点击开始上传。
			</div>
			<div class="upload_Bottom" id="showPicture" style="overflow:auto;">
			  <table id="upload_content" class="swfupload_main">
			<thead>
				<tr>
					<td width="100px;">
						文件名称
					</td>
					<td width="100px;">
						上传状态
					</td>
                    <td width="100px;">
						上传进度
					</td>
					<td width="100px;"> 
						文件操作
					</td>
				</tr>
			</thead>
			<tbody id="divFileProgressContainer">
			</tbody>
			<tfoot>
				<tr>
					
					<td>
						<input type="hidden" id="hidIdList" />
					</td>
				</tr>
			</tfoot>
		</table>
			</div>
		</div>

   <script type="text/javascript"> 
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/photomanage/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT) %>",
		                    fn:"",
		                    small:"false",
		                    sw:"100",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "1 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp格式文件",
		    file_upload_limit: 5,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>css/ea/photomanage/Add.jpg",
            button_width:90,
            button_height:25,  
            button_placeholder_id:"uploads", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList",//隐藏域
                uploadMode: "LIST"//?
            }
        }
        SWFLoad(SWFUpload1_id,LoadSettings);
    }
    addLoadEvent(SWFUpload1_load);
    function SWFUpload1_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
    }
    function uploads(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		} 
		
    }
    
    function upload_completed(){
       var selectbox = document.getElementById("selectbox").value;
	   var hidIdList = document.getElementById("hidIdList").value;
	           window.location.href = basePath
			+ "ea/photomanager/ea_startUpload2.jspa?picPath=" + hidIdList
			+ "&selectbox=" + selectbox+"&mark="+<%=RandomDatas.getRandomNumber(7)%>;
    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
		
	</body>

</html>
