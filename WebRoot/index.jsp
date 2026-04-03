<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <title>My JSP 'index.jsp' starting page。</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" href="swfupload/css/StyleSheet.css" type="text/css"></link>
    <link rel="stylesheet" href="swfupload/swfupload.css" type="text/css"></link> 
    <script type="text/javascript" src="js/jquery.js" ></script>
  
<!--     <script type="text/javascript" src="swfupload/swfupload.js"></script>
    <script type="text/javascript" src="swfupload/single_fileUpload.js"></script>
    <script type="text/javascript" src="swfupload/files_upload.js"></script> -->
  </head>
  
  <body>  
  
  <form id="txtForm" method="post" enctype="multipart/form-data" action="<%=basePath%>/ea/restaurant/ea_addProduct.jspa"  >
  	<input type="file"  name="txtFile">
  	<input type="button" name="submit" id="txtSubmit" value="提交">
  	<input type="submit" id="submit" style="display: none;">
  </form>
  <script>
  	$("#txtSubmit").click(function(){
  		$("#txtForm").find("#submit").click();
  	})
  </script>
    <!-- this is my JSP page. <br> 
    <br/>
    <br/>
    <br/>
    <br/> 
    <img src="images/uploadPic.jpg" id="upok_img" alt=""/>
    <input type="button" onclick="clickUpload(this);" value="点击上传图片"/>
    
    <div class="box_bg" id="box_bg">
    	<span title="关闭" onclick="boxClose(this);" class="box_close">×</span> 
    	<table>
    		<tr>
    			<td>
    				<input type="text" id="file_name" readonly="readonly"/>
    				<input type="hidden" id="HfFileInfo" name="HfFileInfo" />
    			</td>
    			<td><span id="FilePlaceholder"></span></td>
    			<td><input onclick="realUpload();" class="realUpload" type="button" value="上传"/></td>
    		</tr> 
    	</table>
    </div>  -->
    
 <!--    <script type="text/javascript">
    String.prototype.trim = function() { return this.replace(/(^\s*)|(\s*$)/g, ""); }
    	var upload = null;
    	
    	single_addLoadEvent(function(){
    		var settings = {
    		post_params:{
	    		path: "/uploads",//文件保存路径
	            fn:"",    
	            width:"160",
	            height:"120",
	            small:"false",//小图
	            //sw:"100", //小图宽度
	            //sh:"100", //小图高度
	            wm:"False",   //
	            data:""
	        },
	        file_size_limit: "2 MB",
	        file_types: "*.jpg;*.gif;*.png;*.bmp",
	        file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
	        custom_settings:{
		        submitBtnId: "",
		        serverDataId:"HfFileInfo",
		        singleFileQueued:singleFileQueued,
		        singleUploadComplete:singleUploadComplete
		    }
    	};
    	upload = initFileUpload("FilePlaceholder",settings);
    	});
    	//FileQueued ,and the file name show in input:text
    	function singleFileQueued(file){
    		document.getElementById("file_name").value = file.name;
    	} 
    	//upload success event
    	function singleUploadComplete(datas){
    		var d = "/hyplat" + datas.split("|")[0].trim(); 
    		document.getElementById("upok_img").setAttribute("src",d);
    		document.getElementById("file_name").value = "";
    		document.getElementById("box_bg").style.display="none";
    	}
    	//start upload
    	function realUpload(file){
    		if(!document.getElementById("file_name").value){
    			alert("请选择上传文件");
    			return false;
    		}
    		if(upload && upload.getStats().files_queued > 0){
    			upload.startUpload();
    		}
    	}
    	//show upload box
    	function clickUpload(ele){    	
    		clearQueue(upload); 
    		var elements = document.getElementById("box_bg");
    		elements.style.top = getTop(ele) + "px";
    		elements.style.left = getLeft(ele) + "px"; 
    		elements.style.display = "block";
    	}
    	//close the upload box
    	function boxClose(e){
    		if(e){
    			e.parentNode.style.display = "none";
    			document.getElementById("file_name").value = "";
    		}
    	}
    	
    	function getTop(e){
			var offset=e.offsetTop;
			if(e.offsetParent!=null) offset+=getTop(e.offsetParent);
			return offset;
		}
		function getLeft(e){
			var offset=e.offsetLeft;
			if(e.offsetParent!=null) offset+=getLeft(e.offsetParent);
			return offset;
		}
    </script>
  
  
  
  
  
  
  
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  <br/>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
   	<span id="uploads"></span>
   	<table id="upload_content" class="swfupload_main">
   		<thead>
   			<tr>
   				<td>文件名称</td>
   				<td>上传状态</td>
   				<td>上传进度</td>
   				<td>文件操作</td>
   			</tr>
   		</thead>
   		<tbody id="divFileProgressContainer"> </tbody>
   		<tfoot>
   			<tr>
   				<td>
   					<input type="button" onclick="uploads()" id="BtnSave" value="save"/>
   				</td>
   				<td><input type="hidden" id="hidIdList" /></td>
   			</tr>
   		</tfoot>
   	</table>  
    <script type="text/javascript"> 
    	   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() { 
        var LoadSettings = {
            post_params:{
		                    path: "/uploads",
		                    fn:"",
		                    small:"true",
		                    sw:"100",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp格式文件",
		    file_upload_limit: 5,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,
		    button_disabled : false,
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"/hyplat/swfupload/swf_files_upload.gif",
            button_width:65,
            button_height:23,  
            button_placeholder_id:"uploads", 
            custom_settings: {
                upload_target: "divFileProgressContainer",
                submitBtnId: "BtnSave",
                serverDataId: "hidIdList",
                uploadMode: "LIST"
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
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
     -->
  </body>
</html>
