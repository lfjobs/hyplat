<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="hy.ea.util.DateUtil"%>
<%@ page import="hy.ea.util.RandomDatas"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>企业录像管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_upload.js"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script
			src="<%=basePath%>js/ea/office_ea/corporationcode/EnterpriseVideo.js"></script>
		<script type="text/javascript">
		var companyID = '<%=session.getAttribute("companyID")%>';
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  enterpriseVideoID = '';
         var  token=0; 
		</script>
 <style type="text/css">
 ul li{
 float:right;
 }
 .swfupload_pic_name{
   align:left;
   width:15%;
   white-space:nowrap;
 
 } 
 
 .swfupload_pic_state{
  align:left;
  width:45%;
 
 }
 
 .swfupload_pic_option{
  align:left;
  width:35%;
 
 }
 
 .swfupload_pic_percent{
 
 
 
 }
 
 #showPicture{
   border:1px solid #FFFFFF;
   height:30px;
   overflow:hidden;
 }
 
 .swfupload{
 
 margin:8px;
 }
 
 
 </style>

	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="20" align="center">
							选择
						</th>
						<th width="20" align="center">
							序号
						</th>
						<th width="120" align="center">
							名称
						</th>
						<th width="240" align="center">
							录像主题描述
						</th><!--
						<th width="70" align="center">
							摄制年度
						</th>
						--><th width="240" align="center">
							备注
						</th>
						<th width="100" align="center">
							扫描附件
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr id="${enterpriseVideoID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${enterpriseVideoID}" />
							</td>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="enName">${enName}</span>
							</td>
							<td>
								<span id="enSubject">${enSubject}</span>
							</td>
							<!--<td>
								<span id="enDate">${fn:substring(enDate,0,10)}</span>
							</td>
							--><td>
								<span id="mark">${mark}</span>
							</td>
							<td>
								<span style="display: none" id="videoPath">${videoPath}</span>
								<s:if test="videoPath==null||videoPath==''">无</s:if>
								<s:else>
									<a href="#" onclick="viewVedio('${videoPath}','${enName}');">查看</a>
								</s:else>

								<span id="enterpriseVideoKey" style="display: none">${enterpriseVideoKey}</span>
								<span id="enterpriseVideoID" style="display: none">${enterpriseVideoID}</span>
								<span id="companyID" style="display: none">${companyID}</span>
								<span id="organizationID" style="display: none">${organizationID}</span>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/enterprisevideo/ea_getEnterpriseVideoList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="top: 10%; text-align: center; width: 650px;" id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					详细信息
					<div class="close">
					</div>
				</div>
				<center>
				<table cellpadding="10" cellspacing="5" name="stafftable" border="0"
					id="stafftable"  width="100%">
					<tr>
						<td align="right" width="10%">
							选择视频：
						</td>
						<td align="left" width="60%">
					
							<div class="upload_Bottom" id="showPicture"
								style="overflow: hidden;">
								<table id="upload_content" class="swfupload_main"   style="display:show;" width="100%">

									<tbody id="divFileProgressContainer">
									</tbody>
									<tfoot>
										<tr>

											<td colspan="4">
												<input type="hidden" id="hidIdList" />
											</td>
										</tr>
									</tfoot>
								</table>
							</div>
						</td>
						<td align="left">
							<div class="upload_bg">
								<div class="upload_top">
									<ul>
										
						
										<li  style="float:left;">
											<span id="uploads"></span>
										</li>
										
										
									</ul>
								</div>
							</div>
						
						</td>
					</tr>


			        <tr>
						<td align="right" height="41">
							名称：
						</td>
						<td height="41" colspan="2" align="left"  >
							<input name="enterpriseVideo.enName" id="enName" size="35"/>
						</td>
						
						
					</tr>
					<tr>
					<td align="right" height="41">
							主题：
						</td>
						<td height="41" align="left"  colspan="2" >
							<input name="enterpriseVideo.enSubject" id="enSubject" size="35"/>
						</td>
					</tr>
					
					<tr>
						<td align="right" height="41">
							描述：
						</td>
						<td align="left" colspan="2" height="41">
							<input name="enterpriseVideo.mark" id="mark" style="width: 300px" />
						</td>
					</tr>
					<tr>
						<td colspan="3" align="center">
							<input type="hidden" name="enterpriseVideo.enterpriseVideoID"
								id="enterpriseVideoID" />
							<input type="hidden" name="enterpriseVideo.enterpriseVideoKey"
								id="enterpriseVideoKey" />
							<input type="hidden" name="enterpriseVideo.companyID"
								id="companyID" />
							<input type="hidden" name="enterpriseVideo.organizationID"
								id="organizationID" />
								
							<input type="hidden" name="enterpriseVideo.videoPath"
								id="videoPath1" />
							<input type="button" onclick="uploads();" id="BtnSave" class="input-button"
								style="cursor: pointer; width: 80px;" value="开始上传" />
							<input type="button" class="input-button JQueryreturn"
								style="cursor: pointer; width: 80px;" value="取消" />
						</td>
					</tr>
				</table>
				</center>
				<s:token></s:token>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 350px; right: 35%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="cataffSearchTable">
					<tr>
						<td align="right" height="41">
							名称：
						</td>
						<td height="41">
							<input name="enterpriseVideo.enName" id="enName" />
						</td>
					</tr>
					<tr>
						<td align="right" height="41">
							时间：
						</td>
						<td height="41">
							<input name="sDate" id="sDate" onfocus="date(this)" />
							到
							<input name="eDate" id="eDate" onfocus="date(this)" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>


		<script type="text/javascript"> 
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/"+companyID+"/office/enterprisevideo/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)%>",
		                    fn:"",
		                    small:"false",
		                    sw:"100",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "300 MB",
		    file_types: "*.asx;*.asf;*.mpg;*.wmv;*.3gp;*.mov;*.avi;*.mp4;*.WAV;*.flv",
		    file_types_description: "只能上传*.asx;*.asf;*.mpg;*.wmv;*.3gp;*.mov;*.avi;*.mp4;*.WAV;*.flv格式文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>css/ea/photomanage/adds.jpg",
            button_width:82,
            button_height:23,  
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
    uploadVedio();
    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>

	</body>
</html>