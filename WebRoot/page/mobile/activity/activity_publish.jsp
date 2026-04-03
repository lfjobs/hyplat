<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script id="jsID" src="<%=basePath%>js/jquery-1.3.2.js"
	type="text/javascript"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModl_blue.css" />
<script type="text/javascript"src="<%=basePath%>js/accifr/wfj/wfjift.js"></script>

<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>/page/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>/page/ueditor/ueditor.all.min.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8"
	src="<%=basePath%>/page/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">

	var basePath ='<%=basePath%>';
	 var UEDITOR_HOME_URL =basePath+"/ueditora/";　　//从项目的根目录开始
</script>

<title>我要发布活动</title>
  <style>
html{overflow-x:hidden;}
body{background-color:#eeeeee;font-family:Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;font-size:12px;color:#000000;margin:15px 12px;min-width:296px;-webkit-text-size-adjust:none;}
img{vertical-align:top;border:0;}
div,h1{word-break:break-all;}
div,h1,p,ul,li,label,textarea,input,button,form{margin:0;padding:0;-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}

ul{list-style:none;}
.left{float:left;}.right{float:right;}
.clear{clear:both;height:0;font-size:0;line-height:0;overflow:hidden;}
body{
	background-color: #eeeeee;
	font-family: Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;
	font-size: 12px;
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
	margin-bottom: 8px;
	height: 20px;
}
.form_inputta {
	border: 1px solid #cccccc;
	background-color: #ffffff;
	padding: 10px 3px;
	margin-bottom: 8px;
	
	height:200px;	
}
.form_inputta .input {
	font-size: 16px;
	color: #000000;
	line-height: 20px;
	padding: 5px 0;
	margin: -10px 0;
}
.form_input .input {
	font-size: 16px;
	color: #000000;
	line-height: 20px;
	padding: 8px 0;
	margin: -10px 0;
	border:0px;
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
	min-height: 200px;
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
		document.getElementById('post_article_content_placeholder').innerHTML="产品及活动详情、时间、地点、事件";
	}
}
function onFocus(){
	if(document.getElementById('post_article_content_placeholder').innerHTML=="产品及活动详情、时间、地点、事件")
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
	var theme=$("#post_article_title").val();
	var content=UE.getEditor('editor').getContent();
	$("#content").val(content);
	var actDates=document.getElementsByName("acAddress.actDate");
	var ensDates=document.getElementsByName("acAddress.ensDate");
	var eneDates=document.getElementsByName("acAddress.eneDate");
	var a=document.getElementsByName("acAddress.companyAddr");

	if(theme==""){
		alert("产品主题不能为空");
		return false;
	}
	if(content==""){
		alert("产品内容不能为空");
		return false;
	}
	
	for(var j=0;j<actDates.length-1;j++){
		if(actDates[j].value=="" || ensDates[j].value=="" || eneDates[j].value==""){
			alert("时间不能为空！");
			return;
		}
	}
	if(a.length==0||a.length<actDates.length-1){
		alert("请添加活动地址！");
		return false;
	}	
	for(var i=0;i<a.length;i++){
		if(a[i].value == ""){
			alert("请选择并完善活动地址！");
			return false;
			}
		}

    $("#ensDate1").val($("#ensDate").val());
    $("#eneDate1").val($("#eneDate").val());
	document.getElementById("submit").submit();
}
function aa(e){
	var a=$("#"+e.id).parent().parent().remove();
}

</script>
</head>
<body style="margin-top: 63px;overflow: auto">
<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">发布活动</div>
</div>
<div id="post_form">
<form  action="<%=basePath %>ea/activity/ea_activityPublish.jspa?weixinCompanyId=${weixinCompanyId}" method="post" id="submit" enctype= "multipart/form-data">
   <div id="post_note" class="post_note">发布违法、反动互动信息或者冒用他人、组织名义发起互动，将依据记录提交公安机关处理</div>
   <div class="form_input">
   		<input type="text" name="dtActivity.theme" id="post_article_title" class="input" placeholder="输入产品或活动主题" maxlength="70" onfocus="butSea()" readonly="readonly">
   		
		<input type="hidden" name="dtActivity.starttime" id="ensDate1">
		<input type="hidden" name="dtActivity.endtime" id="eneDate1">
		<input type="hidden" name="dtActivity.ppid" id="ppid">
		<input type="hidden" name="dtActivity.content" id="content">
		
   		<input type="hidden" name="dtActivity.inforType" value="00"/>
   		
   </div>
   		<div class="jqmWindow jqmWindow4UEditor" style="height:610px; width:100%;verflow: scroll">
				<script id="editor" type="text/plain" name='ceshi'
					style="width:100%px;height:500px"></script>
			</div>
   
  
    <div class="form_input">
	 	<div style="width: 30%;height: 25px;float:left; font-size: 16px;">活动时间地点：</div>
	 	<div style="width: 10%;height: 25px;float:left; font-size: 16px;"><img alt="" id="timeJia" class="jia" src="<%=basePath %>/images/WFJClient/jia.png" style="width: 25px;height: 25px; float: left;margin-right: 2px;"></div>		 	
    </div>
   <div class="form_inputta" id="kelongOfTime" style="display:none;">
	 	<div style="width: 100%;height: 40px; float: left;">
   			<div style="width: 80%;height: 30px; float: left;margin-top: 5px;" id="oDiv">
   				<input type="text"  id="starttime" name="acAddress.actDate" class="input" onfocus="WdatePicker()" placeholder="活动时间" style="width: 100%;float: left;"/>
   			</div>
   			<img alt="" class="jian" src="<%=basePath %>/images/WFJClient/shan.png" style="width: 25px;height: 25px; float: left;margin-left: 2px;">
	 	</div>
	 	<div style="width: 90%;height: 40px; float: left;margin-top: 5px;">
	 		<input type="text"  id="ensDate" name="acAddress.ensDate" class="input" onfocus="WdatePicker()" placeholder="报名开始时间" style="width: 49%;float: left;"/>
		 	<input type="text"  id="eneDate" name="acAddress.eneDate" class="input" onfocus="WdatePicker()" placeholder="报名截止时间" style="width: 49%;float: right;"/>
	 	</div>
	 	<div style="width: 100%;height: 35px; float: left;">
	 	<div style="width: 30%;height: 25px;float:left; font-size: 16px;">活动类别：</div>
	 	<div style="width: 70%;height: 25px; float: left;">
	   		<select name="acAddress.activityType" style="font-size: 16px;width: 50%;float: right;">
   				<option value="活动" selected="selected">活动</option>
   				<option value="会议">会议</option>
   				<option value="其他">其他</option>
	   		</select>
	 	</div>
    	</div>
		<div style="width: 30%;height: 25px;float:left; font-size: 16px;">活动地点：</div>
	 	<div style="width: 10%;height: 25px;float:left; font-size: 16px;"><img alt="" id="jia" class="jia" src="<%=basePath %>/images/WFJClient/jia.png" style="width: 25px;height: 25px; float: left;margin-right: 2px;"></div>	
	 	<div style="width: 60%;height: 30px; float: left;">
   			<%--<div style="width: 70%;height: 25px; float: left;"><input type="text" id="address" onclick="javascript:accift('01',paret,'00')" style="width: 100%;height: 25px;" name="dtActivity.activityAddress"/></div>
   			<img alt=""  src="<%=basePath %>/images/WFJClient/shan.png" style="width: 25px;height: 25px; float: left;margin-left: 2px;"> --%>
	 	</div>
	 	<div id="address11">
	 	</div>
    </div>
</form>
   <div class="form_button">
	 	<button id="post_article_button" class="button_1" onclick="publish()">发布</button>
   </div>
</div>
<form name="seaform" id="seaform" method="post">
	<div style="width: 245px; top: 25%;left: 15%; position:absolute; display: none; background-color:#c9ebf1; text-align: center;" id="jqmsea">
		<div id="" style="width: 100%; margin-top:5px; margin-bottom:5px; font-size: 20px; text-align:center; ">
			产品/主题
		</div>
		<table id="seaTab">
			<tr>
				<td align="center">
					<input name="seaname" id="seaname" style="height: 25px;"/>
					<a href='javascript:void(0)' onclick="butSea()"><img src="<%=basePath %>/images/wx_7_09.png"/></a>
				</td>
			</tr>
			<tr>
				<td style="width: 240px;height:305px;" align="center">
					<div id="intext" style="width: 200px;height:290px;text-align: left;"></div>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="butM()" value="&nbsp;确定 &nbsp;"/>&nbsp;
			<input type="button" class="input-button" onclick="colM()" value="&nbsp;取消 &nbsp;"/>
		</div>
	</div>
</form>
</body>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script>
	var aid = "";
   var oDivId="";
  
   $('input[name="acAddress.companyName"]').click(function(){
	  showwfjift('01');
	  oDivId=$(this).parent().parent().parent().attr("id");
   });
   function selectAddr(e){
		 var p=$("#"+e.id).parent().parent().parent().parent().parent().attr("id");
		 var pDatev=document.getElementById(p).firstElementChild.firstElementChild.firstElementChild.value;
		 var q=$("#"+e.id).parent().find("input.pDate").val(pDatev);
		
		 showwfjift('01');
		 oDivId=$("#"+e.id).parent().parent().parent().attr("id");
		
	}
// 显示
   function showwfjift(t) {
	alert(t);
   	$("#wfjift").css("margin-top", "140px");
   	//$("#wfjift").css("height", $(window).height()); // 高度
   	// $(window).width();
   	$("#wfjift").show();
   	ts = t;
   	selectv("");
   }
	function a(e){
		
		}
	function paret(e){
		var ds = e.split(",");
		var companyId=ds[0];
		var companyName=ds[1];
		var address=ds[2];
		$("#"+oDivId).find("input.companyName").val(companyName);
		$("#"+oDivId).find("input.companyAddr").val(address);
		$("#"+oDivId).find("input.companyId").val(companyId);
	}	
	
	
</script>
<script type="text/javascript"> 
	$(document).ready(function() {
		//活动地址克隆
		var selects=0;
		var weixin='<%=request.getParameter("weixinCompanyId")%>';
		$("#jia").click(function(){
			selects+=1;
			var html="<div class='form_input1' id='kelongOfAddress"+selects+"' >";
				html+="<div style='width: 10%;height: 25px;float:left; font-size: 16px;'></div>";
				html+="<div style='width: 90%;height: 30px; float: left;'>";
				html+="<div style='width: 80%;height: 40px; float: left;' id='oDiv'>";
				html+="<input type='text' onclick='javascript:selectAddr(this)' class='companyName' style='width:48%;height: 25px;border:solid 1px #cccccc;' name='acAddress.companyName' id='companyName"+selects+"'/>";
				html+="<input type='text' class='companyAddr' style='width: 48%;height: 25px;margin-left: 2px;border: solid 1px #cccccc;' name='acAddress.companyAddr' id='address"+selects+"'/>";
				html+="<input type='hidden' class='companyId' name='acAddress.companyId'  id='companyId"+selects+"'/>";
				html+="<input type='hidden' class='pDate' name='acAddress.paDate' id='pDate"+selects+"'/>";
				html+="</div>";
				html+="<img alt='' class='ajian' onclick='javascript:aa(this)' src='<%=basePath %>/images/WFJClient/shan.png' style='width: 25px;height: 25px; float: left;margin-left: 2px;' id='image"+selects+"'>";
				html+="</div>";
				html+="</div>";
			<%--selects++;
			$("#kelongOfAddress").before($("#kelongOfAddress").clone(true).show().attr("id","kelongOfAddress" + selects));
			$("#kelongOfAddress" + selects).find(':input').each(function() {
			    $(this).parent().find("input.companyName").attr("id","companyName"+selects);;
			    $(this).parent().find("input.companyAddr").attr("id","address"+selects);;
			    $(this).parent().find("input.companyId").attr("id","companyId"+selects);;
				
			});--%>

			var addr=$(this).parent().parent().find("#address11");
			addr.append(html);
			
			
		});
		
		$("img.jian").click(function(){
			$(this).parent().parent().remove();	
		});
		
		//活动时间克隆
		var timeSe=0;
		$("#timeJia").click(function(){
			timeSe++;
			$("#kelongOfTime").before($("#kelongOfTime").clone(true).show().attr("id","kelongOfTime" + timeSe));
			$("#kelongOfTime" + timeSe).find(':input').each(function() {
			    $(this).parent().find("input.companyName").attr("id","companyName"+timeSe);;
			    $(this).parent().find("input.companyAddr").attr("id","address"+timeSe);;
			    $(this).parent().find("input.companyId").attr("id","companyId"+timeSe);;
				
			});
		});
	});
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
	function butSea(){
		var url = basePath + "ea/activity/sajax_getAjaxThemes.jspa?date="
			+ new Date().toLocaleString()+"&weixinCompanyId=${weixinCompanyId}";
		$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					result : $.trim($("#seaname").val())
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var bl = member.bl;
					var str = "";
					for ( var i = 0; i < bl.length; i++) {
						str += "<input type='radio' name='rad' id='" + bl[i][1] +"'/> "
							+ "<a href='javascript:void(0)' id='" + bl[i][1] +"' onclick='selectz(this);'>"
							+ bl[i][0] + "</a><br/>";
					}
					if (str == "") {
						str = "&nbsp;无搜索结果";
					}
					$("#intext").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		$("#jqmsea").show();
	}
	function selectz(e){
		aid = $(e).attr("id");
		$("input#" + aid).attr("checked",'checked');
	}
	function butM(){
		if(aid == ""){
			$("input#post_article_title").attr("value","");
		}else{
			$("input#post_article_title").attr("value",$("a#" + aid).text());
			$("#ppid").val(aid);
		}
		$("#jqmsea").hide();
		$("#seaname").attr("value","");
		aid = "";
	}
	function colM(){
		$("#jqmsea").hide();
		$("#seaname").attr("value","");
		aid = "";
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
  //将内容付给UE控件
 
  var ue = UE.getEditor('editor');
  $(document).ready(function() {
		var ts = "";//状态
		var es = "";//页数
		load();
		// $(".jqmWindow").jqm({
		// modal : true,// 限制输入（鼠标点击，按键）的对话
		// overlay : 20// 遮罩程度%
		// }).jqmAddClose('.close');// 添加触发关闭的selector
		//	
		// 下一页
		$('#grxy').click(function(){
			var xy = $(this).attr("title");
			if (xy != 0) {
				es = "&pageForm.pageNumber=" + xy ;
				selectv(es);
			} else {
				alert("已是尾页！");
			}
		});
		// 上一页
		$("#grsy").click(function() {
			var xy = $(this).attr("title");
			if (xy != 0) {
				es = "&pageForm.pageNumber=" + xy ;
				selectv(es);
			} else {
				alert("已是首页！");
			}
		});
		//查询
		$("#sel").click(function(){
			selectv("");
		});
		//关闭
		$("#clo").click(function(){
			$("#wfjift").hide();
			$("#selectv").attr("value","");
		});
		
	});
	function load() {
		var html = "<div id='wfjift' class='jqmWindow'"
				+ "style='width:95%; position:relative; display: none; bottom:800px; left:15px; background: #eff; '>";
		html += "<table style='width:100%; height: 100%;' border='1'>"
				+ "<tr><td colspan='2' style='height:50px;' align='center'>"
				+ "<input name='selectv' id='selectv' style='border: 1px solid #CCC;'/>&nbsp;<a href='javascript:void(0);' id='sel'>查询</a>&nbsp;"
				+ "<a href='javascript:void(0);' id='clo'>关闭</a></td></tr>"
				+ "<tr><td colspan='2' id='newtable'></td></tr>"
				+ "<tr><td style='height:30px;' align='center'>" +
						"<a href='javascript:void(0);' style='width:100%;height:100%;display:block;vertical-align:middle;padding-top:19px;' id=\"grsy\" title=\"0\">上一页</a></td>" +
						"<td align='center'><a href='javascript:void(0);' style='width:100%;height:100%;display:block;vertical-align:middle;padding-top:19px;' id=\"grxy\" title=\"0\">下一页</a></td></tr></table>";
		html += "</div>";
		$("body").append(html);
	}
	//行事件
	function trclick(e){
		var trval = $("#"+e.id).find("#all").text();
		$("#wfjift").hide();
		$("#selectv").attr("value","");
		paret(trval);
	}
	// 显示
	function showwfjift(t) {
		$("#wfjift").css("margin-top", "140px");
		//$("#wfjift").css("height", $(window).height()); // 高度
		// $(window).width();
		$("#wfjift").show();
		ts = t;
		selectv("");
	}
	// 查询
	function selectv(es) {
		var selecv = $("#selectv").val();
		var url = basePath
				+ "ea/accessresource/sajax_ea_getWfjift.jspa?searchvalue=" + selecv
				+ "&stuts=" + ts + es;
		$.ajax({
			url : encodeURI(url + "&date=" + new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var pageForm = member.pageForm;
				if (pageForm == null) {
					var tabletr = "没有查询到数据！";
					$("#body_02cu").html(tabletr);
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				var tabletr = "<table width='100%' height='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				if(ts == "01"){ //单位
					tabletr += " <tr>"
						+ "<th align='center' bgcolor='#E4F1FA' height='40px'>往来单位名称</th>"
						+ "<th align='center' bgcolor='#E4F1FA' width='35%' >单位电话</th>"
						+ "</tr>";
					for ( var i = 0; i < pageForm.list.length; i++) {
						tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + " onclick='javascript:trclick(this)'>";
						tabletr += "<td id='companyName' align='center'>"
								+ pageForm.list[i][1] + "</td>";
						tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i][3] + "</td>";
						tabletr += "<td id='all' style='display:none' align='center'>"
							+ pageForm.list[i] + "</td>";
						tabletr += " </tr>";
					}
				}
				tabletr += "</table>";
				$("#newtable").html(tabletr);
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
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