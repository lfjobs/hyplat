<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script id="jsID" src="<%=basePath%>js/jquery-1.3.2.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript"src="<%=basePath%>js/accifr/wfj/wfjift.js"></script>
<script type="text/javascript">
	var basePath ='<%=basePath%>';
</script>

<title>发布公告</title>
  <style>
html{overflow-x:hidden;}
body{background-color:#eeeeee;font-family:Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;font-size:12px;line-height:20px;color:#000000;margin:15px 12px;min-width:296px;-webkit-text-size-adjust:none;}
img{vertical-align:top;border:0;}
div,h1{word-break:break-all;}
div,h1,p,ul,li,label,textarea,input,button,form{margin:0;padding:0;-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}
input,textarea{border:0;}
ul{list-style:none;}
.left{float:left;}.right{float:right;}
.clear{clear:both;height:0;font-size:0;line-height:0;overflow:hidden;}
body{
	background-color: #eeeeee;
	font-family: Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;
	font-size: 12px;
	line-height: 20px;
	color: #000000;
	margin: 15px 12px;
	min-width: 296px;
	-webkit-text-size-adjust: none;
}
#topbar{
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 46px;
	background-color: #2fb0c8;
	border-bottom: 2px solid #2da9bf;
	z-index: 500;
}
.topbar_title{
	font-size: 20px;
	color: #ffffff;
	line-height: 20px;
	text-align: center;
	text-overflow: ellipsis;
	-o-text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	margin: 0 auto;
	width: 200px;
	padding: 13px 0;
}
#topbar_back {
	display: none;
	position: absolute;
	left: 0;
	top: 0;
	width: 55px;
	height: 33px;
	overflow: hidden;
	padding-top: 13px;
	background-color: #3fbbd1;
	text-align: center;
}
#topbar_menu {
	position: absolute;
	right: 0;
	top: 0;
	width: 65px;
	height: 35px;
	overflow: hidden;
	padding-top: 11px;
	background-color: #2ea9be;
	text-align: center;
	font-size: 16px;
	color: #ffffff;
	line-height: 25px;
}
#topbar_back img {width: 14px;height: 21px;}
.form_input {
	border: 1px solid #cccccc;
	background-color: #ffffff;
	padding: 10px 3px;
	margin-bottom: 15px;
	height: 20px;
}
.form_input .input {
	font-size: 16px;
	color: #000000;
	line-height: 20px;
	padding: 10px 0;
	margin: -10px 0;
}
.form_input input, .form_textarea textarea, .form_button button {width: 100%;}
.form_textarea textarea, .form_button button {
	width: 100%;font-size: 16px;resize:none;
}
#dt_join_form .textarea {height: 50px;}
.post_note {
	font-size: 12px;
	color: #333;
	line-height: 20px;
	margin-bottom: 15px;
	padding: 8px;
	background-color: #C9EAF1;
	border: 1px solid #9DDDE8;
	margin-bottom: 15px;
}
.form_richtext {position: relative;background-color: white;margin-bottom: 15px;}
.richtext {
	margin-top: -28px;
	padding: 3px;
	height: auto;
	border: 1px solid #CCC;
	font-size: 16px;
	color: black;
	line-height: 28px;
	min-height: 300px;
}
.richtext_bar {
	display: none;
	padding: 10px;
	border: 1px solid #CCC;
	border-top: 0;
	background-color: #DDD;
	overflow: hidden;
}
.richtext_bar_emo_button {float: left;margin: 0 20px 0 10px;}
.richtext_bar_emo_button_icon, .richtext_bar_emo_button_icon_active {width: 30px;height: 30px;overflow: hidden;}
.richtext_bar img {width: 30px;height: 30px;}
.richtext_bar_pic_upfile {
	position: absolute;
	right: 0;
	bottom: 0;
	filter: alpha(opacity=0);
	-moz-opacity: 0;
	opacity: 0;
}
.placeholder {
	width: 288px;
	height: 20px;
	overflow: hidden;
	padding: 4px;
	font-size: 16px;
	line-height: 28px;
	color: #888;
}
.richtext_bar_emo {
/*display: none;*/
	padding: 8px;
	border: 1px solid #CCC;
	border-top: 0;
	background-color: #EEE;
	overflow: hidden;
}
.emo {float: left;padding: 12px;}
.emo img {width: 24px;height: 24px;}
.button_1 {
	font-size: 18px;
	color: white;
	line-height: 20px;
	text-align: center;
	border: 1px solid #666;
	background-color: #F60;
	padding: 10px 0;
}
.dt_emo {width: 24px;height: 24px;}
</style>
<style type="text/css">
 a{text-decoration:none;}
 span.sharespan{cursor:pointer;}
 .swfupload_pic_name{align:left;width:15%;white-space:nowrap;} 
 .swfupload_pic_state{align:left;width:45%;}
 .swfupload_pic_option{align:left;width:35%;}
 .swfupload_pic_percent{
 }
 #showPicture{height:30px;overflow:hidden;}
 .swfupload{margin:0px;float:left;}
 </style>
<link rel="stylesheet" href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css" charset="utf-8" />
<script src="<%=basePath%>swfupload/swfupload.js"></script>
<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>
<script>

function onBlur(){
	if(document.getElementById('post_article_content').innerHTML==""){
		document.getElementById('post_article_content_placeholder').innerHTML="公共信息内容";
	}
}
function onFocus(){
	if(document.getElementById('post_article_content_placeholder').innerHTML=="公共信息内容")
		document.getElementById("post_article_content_placeholder").innerHTML="";
}
function showup(){
	$("#post_article_content_emo").toggle();
}
function _emoadd(index){
	onFocus();
	$("#post_article_content").append("<img class=\"dt_emo\" src=\"<%=basePath%>images/activity/"+(index+1)+".png\">");
}
function show_alt(){
	$("#activityImage").click();
}
function publish(){
	$("#content").val($("#post_article_content").html());
	var theme=$("#post_article_title").val();
	$("#content").val($("#post_article_content").html());
	var content=$("#content").val();
	var endtimes=$("#endtimes").val();
	var a=document.getElementsByName("acAddress.companyAddr");
	
	if(theme==""){
		alert("公共信息主题不能为空");
		return false;
		}
	if(content==""){
		alert("公共信息不能为空");
		return false;
		}
	if(endtimes==""){
		alert("结束时间不能为空");
		return false;
		}
	var j=0;
	  var inforType="${inforType}"; 
	for(var i=0;i<a.length-1;i++){
		if(a[i].value != ""){
			j++;
			}
		}
	
	document.getElementById("submit").submit();
}
</script>
</head>
<body style="margin-top: 63px;overflow: auto">
<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">公共信息${inforType }</div>
</div>
<div id="post_form">
<form  action="<%=basePath %>ea/activity/ea_activityPublish.jspa?weixinCompanyId=${weixinCompanyId}" method="post" id="submit" enctype= "multipart/form-data">
   <div id="post_note" class="post_note">发布违法、反动互动信息或者冒用他人、组织名义发起互动，将依据记录提交公安机关处理</div>
   <div class="form_input">
   		<input type="text" name="dtActivity.theme" id="post_article_title" class="input" placeholder="公共信息主题" maxlength="70">
   		<input type="hidden" name="dtActivity.ppid" id="ppid">
   		<input type="hidden" name="dtActivity.inforType" value="${inforType }"/>
   </div>
   
   <div id="post_article_content_richtext" class="form_richtext">
      <div id="post_article_content_placeholder" class="placeholder" onclick="">公共信息内容</div>
      <div contenteditable="true" id="post_article_content" class="richtext" onpaste="" onfocus="onFocus()" onblur="onBlur()" placeholder="公共信息主题" style="overflow: scroll;"></div>
	  
	  <div id="post_article_content_richtext_bar" class="richtext_bar" style="display: block; " >
	     <div class="richtext_bar_emo_button">
		    <div class="richtext_bar_emo_button_icon_active" style="display:block;"><img onClick="showup()" src="<%=basePath%>images/activity/button_emo_active.png"></div>
			<div class="clear"></div>
		 </div>
		 
		 <span id="uploads"></span><div style="float:left; width:20px ">&nbsp;</div>
		 <span id="uploads2"></span>
		 	<div class="upload_Bottom" id="showPicture" style="overflow: hidden; float:left; width:50% ">
				<table id="upload_content" class="swfupload_main"   style="display:show;" width="100%">
					<tbody id="divFileProgressContainer">
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<input type="hidden" id="hidIdList" name="fileManage.fileAccessories"/>
							</td>
						</tr>
					</tfoot>
				</table>
		   </div>
		   
		 <div class="clear"></div>
	  </div>
	  <input type="hidden" value="${inforType }"/>
	  <div class="clear"></div>
	  <div id="post_article_content_emo" class="richtext_bar_emo" style="display: none; ">
		  <ul><li class="emo" ontouchstart="" onclick="_emoadd(0)"><img src="<%=basePath%>images/activity/1.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(1)"><img src="<%=basePath%>images/activity/2.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(2)"><img src="<%=basePath%>images/activity/3.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(3)"><img src="<%=basePath%>images/activity/4.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(4)"><img src="<%=basePath%>images/activity/5.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(5)"><img src="<%=basePath%>images/activity/6.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(6)"><img src="<%=basePath%>images/activity/7.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(7)"><img src="<%=basePath%>images/activity/8.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(8)"><img src="<%=basePath%>images/activity/9.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(9)"><img src="<%=basePath%>images/activity/10.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(10)"><img src="<%=basePath%>images/activity/11.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(11)"><img src="<%=basePath%>images/activity/12.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(12)"><img src="<%=basePath%>images/activity/13.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(13)"><img src="<%=basePath%>images/activity/14.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(14)"><img src="<%=basePath%>images/activity/15.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(15)"><img src="<%=basePath%>images/activity/16.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(16)"><img src="<%=basePath%>images/activity/17.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(17)"><img src="<%=basePath%>images/activity/18.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(18)"><img src="<%=basePath%>images/activity/19.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(19)"><img src="<%=basePath%>images/activity/20.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(20)"><img src="<%=basePath%>images/activity/21.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(21)"><img src="<%=basePath%>images/activity/22.png"></li>
		  <li class="emo" ontouchstart="" onclick="_emoadd(22)"><img src="<%=basePath%>images/activity/23.png"></li></ul>
	  </div>
	  <input type="hidden" name="dtActivity.content" id="content"/>	  
	  <div class="clear"></div>
    </div>
     <div class="form_input">
		 <input type="text"  id="starttime" name="dtActivity.starttime" class="input" onfocus="WdatePicker()" placeholder="开始时间" style="width: 49%;float: left;"/>
		 <input type="text"  id="endtimes" name="dtActivity.endtime" class="input" onfocus="WdatePicker()" placeholder="截止时间" style="width: 49%;float: right;"/>
	</div>

</form>
   <div class="form_button">
	 	<button id="post_article_button" class="button_1" onclick="publish()">发布</button>
   </div>
</div>

</body>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>


<script type="text/javascript"> 
	
	var basePath = "<%=basePath%>";
	   var SWFUpload1_id={SWFObj:new Object()}
	    function SWFUpload1_load() {
	    String.prototype.trim = function() { 
	             return this.replace(/(^\s*)|(\s*$)/g, ""); 
	             }
	        var LoadSettings = {
	            post_params:{ 
			                    path: "/upload_files/office/filemanage/",
			                    fn:"",
			                    small:"false",
			                    sw:"30",
			                    sh:"80",
			                    wm:"True",
			                    data:"" 
	                        },
	            file_size_limit: "2 MB",
			    file_types: "*.jpg;*.gif;*.png;*.bmp",
			    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
			    file_upload_limit: 20,
			    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
			    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
			    button_disabled : false,//是否禁用按钮
			    upload_success_handler:SWFUpload1_uploadSuccess,
	            button_image:"<%=basePath%>images/activity/button_pic.jpg",
	            button_width:30,
	            button_height:30,  
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
        $("#upload_content").hide();
        
        var hidIdList=$("#hidIdList").val();
        
    	var result=hidIdList.split(",");
    	var result=hidIdList.split(",");
    	var str="";
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"'/></center>";
    	}
    	 $("#post_article_content").append(str);
    	 $("#hidIdList").val("");
    }
    function uploads(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
	
	
	

 </script>

<script type="text/javascript"> 

	var basePath = "<%=basePath%>";
	   var SWFUpload2_id={SWFObj:new Object()}
	    function SWFUpload2_load() {
	    String.prototype.trim = function() { 
	             return this.replace(/(^\s*)|(\s*$)/g, ""); 
	             }
	        var LoadSettings = {
	            post_params:{ 
			                    path: "/upload_files/office/filemanage/",
			                    fn:"",
			                    small:"false",
			                    sw:"30",
			                    sh:"80",
			                    wm:"True",
			                    data:"" 
	                        },
	            file_size_limit: "30 MB",
	            file_types: "*.mp4;*.flv;*.mp3",
			    file_types_description: "只能上传*.mp4;*.flv;*.mp3格式文件",
			    file_upload_limit: 3,
			    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
			    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
			    button_disabled : false,//是否禁用按钮
			    upload_success_handler:SWFUpload2_uploadSuccess,
	            button_image:"<%=basePath%>images/activity/video.png",
	            button_width:30,
	            button_height:30,  
	            button_placeholder_id:"uploads2", //swf替换页面中的位置
	            custom_settings: {
	                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
	                submitBtnId: "BtnSave",//save按钮
	                serverDataId: "hidIdList",//隐藏域
	                uploadMode: "LIST"//?
	            }
	        }
	        SWFLoad(SWFUpload2_id,LoadSettings);
    }
    addLoadEvent(SWFUpload2_load);
    function SWFUpload2_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        $("#upload_content").hide();
        
         var hidIdList=$("#hidIdList").val();
        
    	var result=hidIdList.split(",");
    	
    	var str="";
    	for(var i=0;i<result.length-1;i++){
    		
        var path = basePath+"/page/ea/main/office_ea/corporationcode/jwplayer/player.jsp?enName=微分金&path="+result[i]+"&imagepath=";
        	        
        	    str="<iframe width='100%' style='border:0px;' height='300' src='"+path+"'></iframe></center></br></br>";
        	    		 $("#post_article_content").append(str);
      	
    	}   	
    	 $("#hidIdList").val("");	 
    	  $("#divFileProgressContainer").html("");
    }
 </script>
<script type="text/javascript">

//输入回车键监听               
document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
        return true;
    }
}
</script>
</html>