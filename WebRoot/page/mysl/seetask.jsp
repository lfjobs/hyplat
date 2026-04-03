<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>查看任务</title>
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
		<%@page import="com.opensymphony.xwork2.ognl.OgnlValueStack"%>  
		<%  
 		((OgnlValueStack) request.getAttribute("struts.valueStack")).set(  
  		 "editOrAdd", request.getParameter("editOrAdd"));  
		%>  
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/ea/validate.css" />
		<link href="<%=basePath%>css/ea/document/admin_main.css"
			rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>

		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_upload.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/mysl/seetask.js"></script>

		<script type="text/javascript">
		 var basePath='<%=basePath%>';    
     	var docId = ""; 
        var token = 2;

     	var pNumber = "";      
     	var taskid = "${mytask.taskid}";	
     	var proid = "";
     	var editOrAdd="${editOrAdd}";
    	var jieduan="${mytask.phasestatus}";
    	
    	var auditid="${auditid}";
    	var auditurl="${auditurl}";
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
		<div style="width:100%;display: none;" align="center" id="anniu">
		<input type="button" value="审核通过并继续审核" id="jx" class="input-button" style="cursor:pointer;width:120px;"/> &nbsp;&nbsp;&nbsp;
				<input type="button" value="审核通过并结束审核" id="js" class="input-button" style="cursor:pointer;width:120px;"/> &nbsp;&nbsp;&nbsp;
				<input type="button" value="驳回" id="bh" class="input-button" style="cursor:pointer;width:80px;"/>
		</div>
		<div id="audithistory"  style="width:100%;height:90px;overflow:auto;">
			<table width="100%" height="100%" border="0" align="center"
				cellpadding="0" cellspacing="2">
				<tr>
					<td width="87%" align="left" valign="top">
						<div class="body_02">
							<table width="100%" height="26" align="center" cellspacing="0"
								cellpadding="1" style="font-size: 12px;" class="bannb_01">
								<tr>
									<td height="24" align="left" valign="top" class="txt01">
										&nbsp;审核历史
									</td>
									<td height="24" align="right" valign="top" class="txt01">
										<input type="button" class="anniu02" onclick="circulated();" value="传阅历史" />
										 &nbsp; &nbsp;
									</td>
								</tr>
							</table>
							<form name="historyForm" method="post" action="">
								<input type="submit" name="submit" style="display: none" />
								<table width="100%" height="80%" id="spls" border="0"
									align="center" cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">
									<s:iterator value="auditlist">
										<tr>
											<td width="150px">
												${fn:substring(audittime,0,19)}
											</td>
											<td width="120px" align="center">
												${auditorname}
											</td>
											<td width="70px" align="center">
												<s:if test='auditorstatus=="01"'>未审核</s:if>
												<s:if test='auditorstatus=="02"'>同意</s:if>
												<s:if test='auditorstatus=="03"'>驳回 </s:if>
											</td>
											<td>
												${comments}
											</td>
										</tr>

									</s:iterator>
								</table>


							</form>
						</div>
					</td>
				</tr>
			</table>

		</div>
		
		<form name="searchForm" id="searchForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
				id="jqModel1">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							传阅记录
							<div class="close"></div>
						</div>
					</div>

						<table width="99%" id="attachoprtbl" border="0"
									align="center" cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">


									<s:iterator value="passrecordlist">
										<tr>
											<td>
												${fn:substring(passtime,0,19)}
											</td>
											<td>
												${receiverName}
											</td>
										</tr>

									</s:iterator>



								</table>
						
					
				</div>
			</div>
			<s:token></s:token>
		</form>


		<div id="attachhistory" style="width:100%;height:120px;overflow:auto;">
			<table width="100%"  border="0" align="center"
				cellpadding="0" cellspacing="2">
				<tr>
					<td width="87%" align="left" valign="top">
						<div class="body_02">
							<table width="90%" height="26" align="center" cellspacing="0"
								cellpadding="1" style="font-size: 12px;" class="bannb_01">
								<tr>
									<td height="24" align="left" valign="top" class="txt01">
										&nbsp;附件记录
									</td>
									<td height="24" align="right" valign="top" class="txt01">
										<s:if test='mytask.phasestatus=="01"&&editOrAdd=="editOrAdd"'>
											<input type="button" class="anniu02" id="uploadattach"
												value="上传成果" />
										</s:if>
										<input type="button" class="anniu02" onclick="attachRecord();" value="成果历史" />
									    &nbsp; &nbsp;
									</td>
								</tr>
							</table>
							<form name="attachForm" method="post" action="">
								<input type="submit" name="submit" style="display: none" />
								<table width="100%" height="80%" id="attachtbl" border="0"
									align="center" cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">


									<s:iterator value="attachlist">
										<tr>
											<td width="15%" align="center">
												${fn:substring(uploadtime,0,19)}
											</td>
											<td width="5%" align="center">
												${staffname}
											</td>
											<td>
												<a onclick="download('${filepath}');"><span
													class="filenames">${filename}</span>
												</a>

											</td>
											<td>
												<s:if test='mytask.phasestatus=="01"&&editOrAdd=="editOrAdd"'>
													<s:if test='status=="00"'>
														<a href="javascript:operateAttach('${attachid}')">作废</a>
													</s:if>
													<s:else>
														<a href="javascript:operateAttach('${attachid}')">恢复</a>
													</s:else>
												</s:if>
												<span style="display: none;" class="statuss">${status}</span>
											</td>

										</tr>

									</s:iterator>



								</table>


							</form>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		
		<form name="searchForm" id="searchForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							附件操作记录
							<div class="close"></div>
						</div>
					</div>

						<table width="99%" id="attachoprtbl" border="0"
									align="center" cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">


									<s:iterator value="attrecordlist">
										<tr>
											<td>
												${fn:substring(operatetime,0,19)}
											</td>
											<td>
												${staffname}
											</td>
											<td>
												<s:if test='status=="01"'>作废附件 </s:if>
												<s:if test='status=="00"'>恢复附件 </s:if>
												<s:if test='status=="03"'>上传附件 </s:if>
												
											</td>
											<td>
												<a onclick="download('${filepath}');"><span>${filename}</span>
												</a>

											</td>
											

										</tr>

									</s:iterator>



								</table>
						
					
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		
		<div id="main" style="height:100%;overflow:auto;">
			<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="2">
				<tr>
					<td width="87%" align="left" valign="top">
						<div class="body_02" style="height:100%;">
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
								<table width="98%" height="80%" border="0" align="center"
									cellpadding="0" cellspacing="0" class="table"
									style="background: #FFFFFF; margin: 5px">

									<tr>
										<td align="right" style="width: 14%;">
											<span>任务名称</span>：
										</td>
										<td colspan="3">
											<input name="mytask.taskname" type="text"
												class="input borderStyle2" id="taskname"
												value="${mytask.taskname}" size="70" />
											<input type="hidden" name="mytask.taskid"
												value="${mytask.taskid}" />
											<input type="hidden" name="mytask.taskkey"
												value="${mytask.taskkey}" />
											<input type="hidden" name="mytask.phasestatu"
												value="${mytask.phasestatus}" />
										</td>
									</tr>

									<tr>
										<td align="right">
											执&nbsp;行&nbsp;&nbsp;人：
										</td>
										<td align="left" colspan="3">
											<input type="text" name="mytask.staffname" value="${mytask.staffname}" readonly/>

											<input type="hidden" class="staffid"
												value="${mytask.staffid}" />

										</td>
									</tr>
									<tr>

										<td align="right">
											任务类型：
										</td>
										<td colspan="3">
											<select id="tasktype" name="mytask.tasktype">
												<option value="htzd">
													合同审核
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
									</tr>
									<tr>
										<td align="right">
											任务编号：
										</td>
										
										<td colspan="3" >

											<input type="text"  name="mytask.taskcode"  value="${mytask.taskcode}"  id="taskcode" readonly/>
											
										</td>
									</tr>
									<tr>

										<td align="right">
											任务缓急：
										</td>
										<td colspan="3">
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
										<td align="right">
											任务时间：
										</td>
										<td align="left" colspan="3">
											开始时间
											<input type="text" name="mytask.startdate" id="startDate"
												class="input" onfocus="date()" readonly
												value="${fn:substring(mytask.startdate, 0, 10)}" />

											&nbsp;计划完成时间
											<input type="text" name="mytask.planfinishdate" id="endDate"
												readonly class="input" onfocus="date()"
												value="${fn:substring(mytask.planfinishdate, 0, 10)}" />
											&nbsp;实际完成时间
											<input type="text" name="mytask.factfinishdate" id="factfinishdate"
												readonly class="input" onfocus="date()"
												value="${fn:substring(mytask.factfinishdate, 0, 10)}" />
										</td>
									</tr>

								<tr>
										<td align="right">
											正文：
										</td>

										<td align="left" colspan="2">
										 <textarea cols="100" rows="8" >${mytask.content}</textarea>
										</td>
									</tr>
                                    <tr id="fj">
										<td align="right" style="height:100px;">
											附件：
										</td>

									<td align="left" colspan="2">
										<div style="height:100px;overflow:auto;">
											<table id="attachs">
											<thead>
												<s:iterator value="attachlist2">
												<tr><td><a href="#" onclick="download('${filepath}')">${filename}</a></td></tr>
												</s:iterator>
												<thead>
												<tbody id="tbodyattach">
												</tbody>
											</table>
											      
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

		<!-- -----------------------------------审核并继续审核-------------------------------- -->
	<form name="SendForm" id="SendForm" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 290px; right: 20%; top: 10%;"
			id="jqModelSend">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核通过并继续
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="10"
				cellpadding="20">
				<tr class="shows">
					<td align="right" width="20%">审核人公司：</td>
					<td align="left"><select id="auditorcompanyid"
						name="myaudit.auditorcompanyid" onchange="changeCompany(this);"
						style="width:200px;">
							<option value="">请选择审核人公司</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人部门：</td>
					<td align="left"><select id="auditororgID"
						name="myaudit.auditororgID" onchange="changeDept(this);"
						style="width: 200px;">
							<option value="">请选择审核人部门</option>
					</select></td>
				</tr>
				<tr class="shows">
					<td align="right">审核人姓名：</td>
					<td align="left"><select name="myaudit.auditorid"
						id='auditorid' style="width: 200px;">
							<option value="">请选择审核人</option>
					</select></td>
				</tr>
				<tr>
					<td align="right">审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="myaudit.comments" id="comments" class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>

			<div align="center">
				<input type="button" class="input-button" id="submitResult" value=" 提交 " />
			</div>
			</center>
		</div>
	</form>
	
	<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm2" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend2">
			<input type="submit" name="submit2" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right" width='15%'>审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" name="myaudit.comments" id="comments" class="ckTextLength" maxlength="1000"></textarea>
					
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult2" value=" 提交 " />
					<input type="hidden" name="myaudit.auditorstatus" id="auditorstatu" value=""/>
			</div>
			</center>
		</div>
	</form>

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
							<td align="right">
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
													<input type="hidden" id="hidIdList"
														name="myattach.filepath" />
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
		
	<!-- <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe> -->
		
		
		
		<iframe name="hidden" width="0" height="0"></iframe>


		<script type="text/javascript">

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
    tosave();
    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
	</body>
</html>
