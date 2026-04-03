<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>新建任务</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ page import="hy.ea.util.DateUtil"%>
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
			href="<%=basePath%>css/ea/validate.css" />
	    <link href="<%=basePath%>css/ea/document/admin_main.css"
			rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	    <script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>


		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		
		

        <link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_upload.js"></script>
		<script src="<%=basePath%>js/ea/mysl/addtask.js"></script>

		<script type="text/javascript">
		 var basePath='<%=basePath%>';    
     	var taskid = ""; 
     	var proid ="${myproject.proid}";
        var token = 2; 	
        var update = "${param.update}";
        var procode = "${myproject.procode}";
        var isSelected="htzd";
       

     	</script>

    <style type="text/css">
.swfupload_pic_name {
	align: left;
	width: 15%;
	white-space: nowrap;
}

.swfupload_pic_state {
	align: left;
	width: 45%;
}

.swfupload_pic_option {
	align: left;
	width: 35%;
}

.swfupload_pic_percent {
	
}

#showPicture {
	border: 1px solid #FFFFFF;
	height: 100px;
	overflow: hidden;
}

.swfupload {
	margin: 8px;
}
#attachs{
 margin-top:5px;
 border-collapse: collapse;


}
</style>
	</head>
	<body>
		<div id="main" style="height:100%;overflow:auto;">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="2">
				<tr>
					<td width="87%" style="height:600px;"align="left" valign="top">
						<div id="body_02" style="height:100%;">
							<table width="100%" height="26" align="center" cellspacing="0"
								cellpadding="1" style="font-size: 12px;" class="bannb_01">
								<tr>
									<td height="24" align="left" valign="top" class="txt01">
										&nbsp;新 建
									</td>
								</tr>
							</table>
							<form name="newForm" method="post" action="" id="newForm">
								<input type="submit" name="submit" style="display: none" />
								<table width="98%"  border="0" align="center"
									cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">
									<tr>
										<td height="35" colspan="4" align="left"
											style="padding-left: 10%;">

											<nobr>
												<input type="button" value="<<返回" class="anniu02"
													id="back"  />


												<input type="button" class="anniu02" id="xddoc"
													value="下达任务" onclick="saveTask();" />
													
												<input type="button" class="anniu02" id="storedoc"
													value="保存" onclick="saveTask();" />
													


											</nobr>
										</td>
									</tr>
									<tr>
										<td align="right" style="width: 14%;">
											<span>任务名称</span>：
										</td>
										<td colspan="2">
											<input name="mytask.taskname" type="text" maxlength="50" 
												class="put3 ckTextLength" style="height:20px;"id="taskname"
												value="${mytask.taskname}" size="70" />
											<input type="hidden" name="mytask.taskid"
												value="${mytask.taskid}" />
												<input type="hidden" name="mytask.taskkey"
												value="${mytask.taskkey}" />
											<input type="hidden" name="mytask.phasestatus"
												value="${mytask.phasestatus}" />
											<input type="hidden" name="mytask.proid"
												value="${mytask.proid}" id="proid" />
											<input type="hidden" name="mytask.proName"
												value="${myproject.proname}" id="proName" />
										</td>
									</tr>

									<tr>
										<td align="right">
											执&nbsp;行&nbsp;&nbsp;人：
										</td>
										<td align="left" colspan="2">
											<select id="staffid" name="mytask.staffid"></select>

											<input type="hidden" class="staffid"
												value="${mytask.staffid}" />
										
											

										</td>
									</tr>
									
									<tr>

										<td align="right">
											任务类型：
										</td>
										<td colspan="2"  class="tcode">
											<select id="tasktype" name="mytask.tasktype">
												<option value="htzd">
													合同制定
												</option>
												<option value="scsj">
													生产计划通知书
												</option>
												<option value="scrw">
													生产任务通知书
												</option>
												<option value="sjdg">
													设计大纲
												</option>
												<option value="htrw">
													绘图任务
												</option>
											</select>
											<input type="hidden" class="tasktype"
												value="${mytask.tasktype}" />
										</td>
									
									
								    <td colspan="2" class="ttaskcode">
								   <c:choose>
										<c:when test='${mytask.tasktype=="htzd"}'>合同制定</c:when>
										<c:when test='${mytask.tasktype=="scsj"}'>生产计划通知书</c:when>
										<c:when test='${mytask.tasktype=="scrw"}'>生产任务通知书</c:when>
										<c:when test='${mytask.tasktype=="sjdg"}'>设计大纲</c:when>
										<c:otherwise>绘图任务</c:otherwise>
									</c:choose></td>
								</tr>
									<tr>
										<td align="right">
											任务编号：
										</td>
										<td colspan="2" class="tcode">
								

											<input type="text"   class="put3" style="width:40px;" id="code1"  onfocus="dayhoury({skin:'whyGreen',dateFmt:'yyyy'},'htzd')"  /><span> —</span>
											<input type="text"   class="put3"  style="width:60px;" id="code2" onfocus="dayhoury({skin:'whyGreen',dateFmt:'yyyy'},'scsj')" /><span> —</span>
											<input type="text"    class="put3" style="width:40px;" id="code3" onfocus="dayhoury({skin:'whyGreen',dateFmt:'yyyy'},'scrw')" /><span class="code4"> —</span>
											<input type="text"    class="put3" style="width:40px;" id="code4"/>
											<%--<input type="hidden"  name="mytask.taskcode" id="taskcode"/>
											--%><input type="hidden"  name="mytask.seqnum" id="seqnum"/>
											
										</td>
										<td colspan="3" class="ttaskcode">

											<input type="text"  name="mytask.taskcode"  value="${mytask.taskcode}"  id="taskcode" readonly/>
											
										</td>
									</tr>
									<tr>

										<td align="right">
											任务缓急：
										</td>
										<td colspan="2">
											<select id="emergency" name="mytask.emergency">
												<option value="pt">
													普通
												</option>
												<option value="jj">
													紧急
												</option>
												<option value="tj">
													特急
												</option>
											</select>
											<input type="hidden" class="emergency"
												value="${mytask.emergency}" />
										</td>
									</tr>

									


									<tr>
										<td align="right" style="width:10%;">
											任务时间：
										</td>
										<td align="left" style="width:35%;">
											开始时间：
											<input type="text"  style="width: 150px;" name="mytask.startdate" id="startDate"
												class="input put3" onfocus="date()" readonly
												value="${fn:substring(mytask.startdate, 0, 10)}" />

											
										</td>
										<td align="left"  >
											

											&nbsp;计划完成时间：
											<input type="text" style="width: 150px;" name="mytask.planfinishdate" id="endDate"
												readonly class="input put3" onfocus="date()"
												value="${fn:substring(mytask.planfinishdate, 0, 10)}" />
										</td>
									</tr>

									<tr>
										<td align="right">
											正文：
										</td>

										<td align="left" colspan="2">
										 <textarea cols="100" rows="8" name="mytask.content" class="ckTextLength" maxlength="500" >${mytask.content}</textarea>
										</td>
									</tr>
                                    <tr>
										<td align="right" style="height:170px;">
											附件：
										</td>

									<td align="left" colspan="2"><input type="button"
										class="anniu02" id="uploadattach" value="上传附件" />
										<div>
											<table id="attachs">
											<thead>
												<s:iterator value="attachlist">
												<tr><td><a href="#" onclick="downAttach('${filepath}')">${filename}</a></td><td><input type="button" title="删除" onclick="deleteAttach('${attachid}',this)" class="deleteBtn"/></td></tr>
												</s:iterator>
												<thead>
												<tbody id="tbodyattach">
												</tbody>
											</table>
											        <input type="hidden" id="hidIdLists" name="myattach.filepath"/>
													<input type="hidden" id="filename" name="myattach.filename" />
													<input type="hidden" id="fileformat"
														name="myattach.fileformat" />
													<input type="hidden" name="myattach.taskid"
														value="${mytask.taskid}" />
													<input type="hidden" name="myattach.proid"
														value="${mytask.proid}" />
													<input type="hidden" name="myattach.staffid"
														value="${mytask.staffid}" />
													<input type="hidden" name="myattach.staffname"
														value="${mytask.staffname}" />
													<input type="hidden" name="myattach.attachid" id="attachid"
														value="" />	
										</div>
										</td>
								</tr>


								</table>


							</form>
						</div>
					</td>
				</tr>
			</table>

		</div>
		
		<!--上传附件 -->
		<form name="uploadForm" id="uploadForm" method="post" action=""
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss" style="top: 10%; width: 600px"
				id="jqModelUpload">

				<div class="drag">
					上传附件
					<div class="close"></div>
				</div>

				<div class="content">
					<div class="contentbannb">
					</div>

					<table width="100%" border="0" align="center" cellpadding="10"
													
                                            cellspacing="10" id="stafftable2"
						style="margin-top: 5px; margin-bottom: 5px;">



						<tr>
							<td align="right" style="width: 15%;">
								上传文件：
							</td>
							<td align="left" style="width: 60%;">

								<div class="upload_Bottom" id="showPicture"
									style="overflow: hidden;">
									<table id="upload_content" class="swfupload_main"
										style="display: show;" width="100%">

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


											<li>
												<span id="uploads"></span>
											</li>


										</ul>
									</div>
								</div>

							</td>
						</tr>
					</table>


					<div align="center">
						<input type="button" class="input-button"
							style="cursor: pointer; width: 80px;" onclick="uploads();"
							id="BtnSave" value=" 上传 " />

					</div>
				</div>
				<s:token></s:token>
		</form>
		

		<iframe name="hidden" width="0" height="0"></iframe>


		<script type="text/javascript">
     $(function(){
         
//$("#main").height($(window).height() - 5);

     });
</script>


	<script type="text/javascript"> 
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{ 
		                    path: "/upload_files/mysl/attach/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)%>",
		                    fn:"",
		                    small:"false",
		                    sw:"100",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "50 MB",
		    file_types: "*.*",
		    file_types_description: "所有文件",
		    file_upload_limit: 5,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>css/ea/photomanage/af.jpg",
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
    	
         $("#filename").val($("#filename").val()+file.name+",");
          $("#fileformat").val($("#fileformat").val()+file.type+",");
        uploadSuccess(file, serverData,this);
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
    
    function upload_completed(){
    
    	show();
    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
	</body>
</html>
