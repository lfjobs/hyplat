<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>档案管理</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@page import="hy.ea.util.DateUtil"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />

		<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>js/common/organizationTree.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/archives/ArchivesManage.js"></script>
		<script src="<%=basePath%>swfupload/files_upload.js"></script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  historyID ="";
         var  token=0;
         var  catemodule= '<%=session.getAttribute("module")%>';
         var type = '${type}';
         
         var extensionStaffCoach='${extensionStaffCoach}';//新版学员报名标识符
         var studentID='${cstaff.staffID}';//新版报名学员ID
      	
      	$(function(){
      		if($.browser.version=="6.0"){
      			$("html")[0].scrollHeight;
      			$("html").height();
      		}
      		$("html").css("overflow-x",extensionStaffCoach=='extensionStaffStudent'?"scroll":"");
      		$("html").css("overflow-y","hidden");
      	});
		</script>

		<style type="text/css">
a {
	text-decoration: none;
}

.table {
	border-collapse: collapse;
	border: 1px solid #a8c7ce;
	font-size: 12px;
	margin-top: 5px;
	margin-bottom: 5px;
}
</style>
		<script type="text/javascript"> 
     var SWFUpload1_id={SWFObj:new Object()};
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/archive/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)%>",
		                    fn:"",
		                    small:"false",
		                    sw:"100",
		                    sh:"80",
		                    wm:"True",
		                    data:"" 
                        },
            file_size_limit: "1 MB",
		    file_types: "*.*",
		    file_types_description: "所有格式",
		    file_upload_limit: 5,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>images/attach.jpg",
            button_width:85,
            button_height:27,  
            button_placeholder_id:"uploads", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
                submitBtnId: "toEnter",//save按钮
                serverDataId: "hidIdList",//隐藏域
                uploadMode: "LIST"//?
            }
        };
        SWFLoad(SWFUpload1_id,LoadSettings);
    }
    addLoadEvent(SWFUpload1_load);
    var sizes = "";
    function SWFUpload1_uploadSuccess(file, serverData) {
       
        sizes +=file.size+","; 
        uploadSuccess(file, serverData,this);
    }
   
    function uploads(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}
		
		
    }
     function upload_completed(){
			        document.getElementById("sizeattach").value=sizes;
					$("#postEnterForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/archive/ea_addArchives.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.postEnterForm.submit.click();
					document.postEnterForm.reset();
					$("#divFileProgressContainer").empty();
				    token = 1;
				    if($("div#title").text()=="修改"){
                      token = 2;
                    }
			        return;

    }
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>
						<th width="60" align="center">
							档案编号
						</th>
						<th width="200" align="center">
							档案名称
						</th>
						<th width="80" align="center">
							档案类别
						</th>
						<th width="120" align="center">
							条码
						</th>
						<th width="175" align="center">
							芯片号
						</th>

						<th width="80" align="center">
							保密等级
						</th>
						<th width="150" align="center">
							入库时间
						</th>
						<th width="80" align="center">
							出库接收人
						</th>
						<th width="150" align="center">
							出库时间
						</th>
						<th width="80" align="center">
							状态
						</th>
						<th width="80" align="center">
							存放位置
						</th>
						<th width="100" align="center">
							生效日期
						</th>
						<th width="100" align="center">
							失效日期
						</th>
						<th width="100" align="center">
							续签日期
						</th>
						<th width="80" align="center">
							附件
						</th>
						<th width="80" align="center">
							追踪
						</th>
						<th width="80" align="center">
							是否作废
						</th>
						<th width="150" align="center">
							备注
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${historyid}">
							<td>
								<input type="checkbox" name="checkinput"
									class="JQuerypersonvalue" value="${historyid}" />

							</td>
							<td>
								<span id="num"><%=number%></span>
							</td>
							<td>
								<span id="archiveCode">${archivecode}</span>
							</td>
							<td>
								<span id="name">${name}</span>
								<span id="archivesid" style="display: none">${archivesid}</span>
								<span id="staffid" style="display: none">${staffid}</span>
								<span id="staffname" style="display: none">${member}</span>
							</td>
							<td>
								<span id="catalogue">${categroyname}</span>
								<span id="catalogueid" style="display: none">${categroyid}</span>
							</td>
							<td>
								<span id="barcode">${barcode}</span>
							</td>
							<td>
								<span id="chipids">${chipid}</span>
							</td>
							<td>
								<s:if test="securitylevel=='01'">
									<span id="">一级</span>
								</s:if>
								<s:elseif test="securitylevel=='02'">
									<span>二级</span>
								</s:elseif>
								<s:else>
									<span>三级</span>

								</s:else>
								
	                           <span id="securitylevel" style="display: none">${securitylevel}</span>
							</td>
							<td>
								<span id="intime">${fn:substring(intime,0,19)}</span>
							</td>
							<td>
								<span id="outusername">${outusername}</span>
							</td>
							<td>
								<span id="outtime">${fn:substring(outtime,0,19)}</span>
							</td>
							<td>
								<s:if test="state==1">
									<span id="state">在库</span>
								</s:if>
								<s:else>
									<span id="state">出库</span>
								</s:else>

							</td>
							<td>
								<s:if test="locationid==null||locationid==''">暂无指定</s:if>
								<s:else>
									<span>${locationname}</span>
								</s:else>
								<span id="locationid" style="display:none;">${locationid}</span>

							</td>
							<td>
								<span id="startDate">${fn:substring(startvalidity,0,10)}</span>
							</td>
							<td>
								<span id="endDate">${fn:substring(endvalidity,0,10)}</span>
							</td>
							<td>
								<span id="renewalDate">${fn:substring(renewaldate,0,10)}</span>
							</td>
							<td>
								<s:if test="attach==null||attach==''">无</s:if>
								<s:else>
									<a href="#" onclick="showAttach('${archivesid}');">查看</a>
								</s:else>

							</td>
							<td>
								<span id=""><a href="#"
									onclick="trackArchive('${historyid}');">追踪</a> </span>
							</td>
							<td>
								<s:if test="obsoletestatus=='00'">
									<span>否</span>
								</s:if>
								<s:else>
									<span>是</span>
								</s:else>
							</td>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/archive/ea_getArchiveList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}&extensionStaffCoach=${extensionStaffCoach}&cstaff.staffID=${cstaff.staffID}">
				</c:param>
			</c:import>
		</div>
		<!--入库窗口 -->
		<div class="jqmWindow" style="width: 460px; right: 30%; top: 2%"
			id="jqModelEnter">
			<form name="postEnterForm" id="postEnterForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div id="title">
						入库
					</div>
					<div class="close">
					</div>
				</div>
				<center>
				<table width="100%" cellpadding="0" cellspacing="5" id="entertable">
					<tr>
						<td align="right" style="width: 30%;">
							档案编号：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.archiveCode"
								style="width: 150px;" id="archiveCode" 
								class="disable" readonly />
							<input type="hidden" name="archiveTemp.historyID"
								value="${historyID}" id="historyIDss" />
						</td>
					</tr>
					<tr>
						<td align="right">
							档案名称：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.name" id="name"
								style="width: 150px;" class="put3 disable" />

						</td>
					</tr>
					<tr class="member">
						<td align="right">
							所属员工：
						</td>
						<td align="left">
							<input type="text" name="staffname" id="staffname2"
								style="width: 150px;" readonly class="put3" />
							<input name="archiveTemp.staffID" id="staffID" type="hidden" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/documentcommon/ea_getStaffformalList.jspa')" />

						</td>
					</tr>
				  	<tr class="student">
						<td align="right">
							所属学员：
						</td>
						<td align="left">
							<input type="text" name="stuname" id="stuname"
								style="width: 150px;" readonly class="put3" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/archive/ea_getStaffList.jspa')" />

						</td>
					</tr>

					<tr>
						<td align="right">
							条码：
						</td>
						<td align="left">
							<input type="text" id="barcode" name="archiveTemp.barcode"
								style="width: 150px;" value="" class="disable" />
						</td>
					</tr>
					<tr>
						<td align="right">
							芯片号：
						</td>
						<td align="left">
							<input type="text" id="chipids" name="archiveTemp.chipid"
								value="" class="put3 disable chip" style="width: 150px;" />
							<input type="hidden" id="oldchipids" value="" />
							<input type="button" class="input-button readchipid disable"
								value=" 读取 " />
						</td>
					</tr>
					<tr>
						<td align="right">
							保密等级：
						</td>
						<td align="left">
							<select name="archiveTemp.securitylevel" style="width: 155px;"
								class="put3 disable" id="security">
								<option>
									选择保密等级
								</option>
								<option value="01">
									一级
								</option>
								<option value="02">
									二级
								</option>
								<option value="03">
									三级
								</option>
							</select>
						</td>
					</tr>
					<tr class="hidtr">
						<td align="right">
							档案类别：
						</td>
						<td align="left">

							<input type="text" value="" readonly id="catalogue"
								style="width: 150px;" class="put3 disable" />
							<input type="hidden" value="" id="catalogueid"
								name="archiveTemp.catalogue" />

							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/catalogue/ea_getCatalogueList.jspa')"
								class="disable" />

						</td>

					</tr>
					<tr>
						<td align="right">
							存储位置：
						</td>
						<td align="left">
							<select name="archiveTemp.location" style="width: 155px;"
								id="location" class="put3">
								<option value="">
									请选择位置
								</option>
							</select>
							<input type="button" class="input-button"
								onclick="addLocation();" value=" 快速添加 " />
						</td>
					</tr>
					<tr class="member">
						<td align="right">
							合同签订日期：
						</td>
						<td align="left">
							<input type="text" name="archiveTemp.contractSignDate"
								id="startDate" onfocus="date(this)" style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<td align="right">
							生效日期：
						</td>
						<td align="left">

							<input type="text" name="archiveTemp.startDate" id="startDate"
								class="put3" style="width: 150px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly value="${fn:substring(archiveTemp.startDate, 0, 10)}" />
						</td>
					</tr>
					<tr>
						<td align="right">
							失效日期：
						</td>
						<td align="left">
						
							<input type="text" name="archiveTemp.endDate" id="endDate"
								class="put3" style="width: 150px;"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly value="${fn:substring(archiveTemp.endDate, 0, 10)}" />

						</td>
					</tr>
					<tr class="exist">
						<td align="right">
							已上传文件：
						</td>
						<td align="left">
							<table class="table"
								style="white-space: nowrap; border-top: none; border-right: none; margin-top: 0px;">
								<thead>
									<tr>
										<td align="center">
											文件名称
										</td>
										<td align="center">
											上传状态
										</td>
										<td align="center">
											上传进度
										</td>
										<td align="center">
											文件操作
										</td>
									</tr>
								</thead>
								<tbody id="exitfile">
								</tbody>
							</table>

						</td>
					</tr>
					<tr class="uploadfile">
						<td align="right">
							<span id="uploads"></span>
						</td>
						<td align="left">
							<input type="hidden" id="sizeattach" name="archiveTemp.filesize" />
							<div
								style="width: 250px; height: 98px; overflow: auto; border: 1px solid #A4D3EE;">

								<table class="table" id="upload_content" class="swfupload_main"
									style="white-space: nowrap; border-top: none; border-right: none; margin-top: 0px;">
									<thead>
										<tr>
											<td align="center">
												文件名称
											</td>
											<td align="center">
												上传状态
											</td>
											<td align="center">
												上传进度
											</td>
											<td align="center">
												文件操作
												<input type="hidden" id="hidIdList"
													name="archiveTemp.attach" value="" />
											</td>
										</tr>
									</thead>
									<tbody id="divFileProgressContainer">
									</tbody>
								</table>

							</div>


						</td>
					</tr>
				</table>
				</center>
				<div align="center" style="margin-bottom: 5px;">
					<input name="type" type="hidden" value="${type}" />
					<input name="catemodule" type="hidden" value="${catemodule}" />
					<input type="button" class="input-button" id="toCancel"
						value=" 关闭 " />
					<input type="button" class="input-button JQuerySubmit" id="toEnter"
						value=" 提交 " />
				</div>
			</form>

			<iframe width="1" height="1" name="loadcab" id="loadcab"></iframe>
		</div>




		<!--下载附件 -->
		<div class="jqmWindow"
			style="width: 400px; height: 240px; right: 25%; top: 10%"
			id="jqModelAttach">
			<form name="postAttachForm" id="postAttachForm" method="post">
				<div class="drag">
					下载附件
					<div class="close">
					</div>
				</div>
				<center>
				<div style="width: 400px; height: 200px; overflow: auto;">
					<table cellpadding="0" cellspacing="5" id="showattach" width="300">


					</table>
				</div>
				</center>

			</form>
		</div>



		<!--追踪窗口 -->
		<div class="jqmWindow"
			style="width: 450px; height: 250px; right: 25%; top: 10%"
			id="jqModelTrack">
			<form name="postTrackForm" id="postTrackForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					追踪
					<div class="close">
					</div>
				</div>
				<div style="height: 240px; overflow: auto;">
					<table width="396" cellpadding="0" cellspacing="5" id="tracktable">


					</table>
				</div>
			</form>
		</div>

		<!--添加窗口 -->
		<div class="jqmWindow" style="width: 300px; right: 33%; top: 30%"
			id="jqModelAdd">
			<form name="postAddForm" id="postAddForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					添加
					<div class="onlyclose">
					</div>
				</div>
				<table width="396" cellpadding="0" cellspacing="5" id="addTable">
					<tr>
						<td width="123" align="right">
							位置名称：
						</td>
						<td width="261">
							<input type="text" name="archivelocation.locationname"
								id="locationname" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="toSubmit"
						value=" 确定 " />
					<input type="button" class="input-button" id="toCancell"
						value=" 关闭 " />
				</div>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<center>
				<table width="396" cellpadding="0" cellspacing="5"
					id="cataffSearchTable">
					<tr>
						<td align="right" style="width: 35%;">
							档案编号：
						</td>
						<td align="left">
							<input name="viewArchive.archivecode" style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<td align="right">
							档案名称：
						</td>
						<td align="left">
							<input name="viewArchive.name" style="width: 150px;" />

						</td>
					</tr>
					<tr class="member">
						<td align="right">
							所属员工：
						</td>
						<td align="left">
							<input type="text" name="staffname" id="staffname"
								style="width: 150px;" readonly />
							<input name="viewArchive.staffid" id="staffID" type="hidden" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/stamplog/ea_getStaffformalList.jspa')" />

						</td>
					</tr>
					<tr class="hidtr">
						<td align="right">
							档案类别：
						</td>
						<td align="left">
							<select name="viewArchive.categroyid" id="catalogue"
								style="width: 155px;">

							</select>

						</td>
					</tr>
					<tr>
						<td align="right">
							条码：
						</td>
						<td align="left">
							<input type="text" name="viewArchive.barcode" id="barcodes"
								style="width: 150px;" />
						</td>
					</tr>
					<tr>
						<td align="right">
							芯片号：
						</td>
						<td align="left">
							<input type="text" name="viewArchive.chipid" id="chipidss"
								style="width: 150px;"  />
							<input type="button" class="input-button readchipid" value=" 读取 " />
							
						</td>
					</tr>
					<tr>
						<td align="right">
							保密等级：
						</td>
						<td align="left">
							<select name="viewArchive.securitylevel" style="width: 155px;"
								id="security">
								<option value="">
									选择保密等级
								</option>
								<option value="01">
									一级
								</option>
								<option value="02">
									二级
								</option>
								<option value="03">
									三级
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							状态：
						</td>
						<td align="left">
							<select name="viewArchive.state" style="width: 155px;" id="state">
								<option value="">
									选择档案状态
								</option>
								<option value="1">
									在库
								</option>
								<option value="0">
									出库
								</option>
							</select>
						</td>
					</tr>
					<tr class="hidtr">
						<td align="right">
							是否作废：
						</td>
						<td align="left">
							<select name="viewArchive.obsoletestatus" style="width: 155px;"
								id="obsoletestatus">
								<option value="">
									选择是否作废
								</option>
								<option value="01">
									是
								</option>
								<option value="00">
									否
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							存放位置：
						</td>
						<td align="left">
							<select name="viewArchive.locationid" style="width: 155px;"
								id="location">
								<option value="">
									请选择位置
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="tosearch"
								value=" 查询 " />
							<input name="search" type="hidden" value="search" />
							<input name="type" type="hidden" value="${type}" />
						</td>

					</tr>
				</table>
				</center>
			</form>
		</div>




		
		<!--续签 -->
		<div class="jqmWindow" style="width: 370px; right: 25%; top: 10%"
			id="jqModelSign">
			<form name="postSignForm" id="postSignForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					续签
					<div class="close">
					</div>
				</div>
				<center>
				<table width="100%" cellpadding="0" cellspacing="5">
					<tr>
						<td align="right" style="width: 35%;">
							合同续签、终止日期：
						</td>
						<td align="left">
							<input name="archiveTemp.renewalDate" onfocus="date(this)"
								style="width: 150px;" />
							<input type="hidden" name="archiveTemp.historyID" id="historyids" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="submit" value="   提交  " id="contractSubmit"
								class="input-button" />
						</td>
					</tr>

				</table>
				</center>
			</form>
		</div>




		<div id="socialJqm" class="jqmWindow"
			style="width: 60%; height: 250px; absolute; display: none; left: 20%; top: 8%; z-index: 9999; background: #DAE7F6; overflow-y: hidden;">
			<div style="background: #DAE7F6; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="210px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;">
			</iframe>
			<div style="height: 28px; border: 1;">
				<input type="hidden" value="" id="markid" />
				<input type="button" class="input-button" id="DaoRuFanqd"
					onclick="DaoruConfirm();" value=" 确定 "
					style="cursor: hand; border: 1; margin-left: 300px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan"
					onclick="cancelJqm();" value=" 关闭 "
					style="cursor: hand; border: 1; margin-left: 40px; height: 25px; width: 60px" />
			</div>

		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>

		<!--出库单-->
		<div class="jqmWindow"
			style="background: #FFFFFF repeat top; width: 900px; right: 13%; top: 2%;"
			id="jqModelOutDetail">
			<form name="postOutDetailForm" id="postOutDetailForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div class="divtx">
						出库单
					</div>
					<div class="close"></div>
				</div>

				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr>
						<td height="30" colspan="3" align="center"
							style="font-size: 14px; font-weight: bold; color: #3366CC">
							出库单
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" class="table">

					<tr id="baseInfo">
						<td width="13%" height="25" align="right">
							公司：
						</td>
						<td align="center" width="25%">
							<span id="companyName"> </span>
						</td>
						<td align="right">
							部门：
						</td>
						<td align="center" width="15%">
							<span id="organization"></span>
						</td>
						<td align="right">
							责任人：
						</td>
						<td align="center">
							<span id="inuser"> </span>
						</td>
						<td align="right" width="10%">
							出库时间：
						</td>
						<td align="center" colspan="3">
							
							<span id="outtimestr"></span>
						</td>
					</tr>
					<tr>
						<td height="25" align="right">
							出库接收人：
						</td>
						<td>
							<input name="outusername" id="outusername2" type="text"
								class="input" size="18" readonly />
							<input name="archiveTemp.outuser" id="outuserid" type="hidden" />
							<input name="archiveTemp.historyID" id="historyIDs" type="hidden" />
							<input name="archiveTemp.outtimestr" id="outtimestr2"
								type="hidden" />
							<input name="printtype" id="printtype" type="hidden" />
							<img src="<%=basePath%>images/r_8_12.gif" style="cursor: hand;"
								onclick="importGY('ea/stamplog/ea_getStaffformalList.jspa')" />
						</td>
						<td class="hidtd" height="25" align="right">
							出库至：
						</td>
						<td class="hidtd" height="25" align="center">
							<select name="archiveTemp.catemodule" id="outstatus">
								<option value="theory" selected>
									理论档案管理
								</option>
								<option value="piletest">
									桩考档案管理
								</option>
								<option value="yard">
									场地档案管理
								</option>
								<option value="roadtest">
									路考档案管理
								</option>
							</select>
						</td>

					</tr>

				</table>
				<div style="width: 99%; height: 100px; overflow: auto; margin: auto">
					<table
						style="align: center :                   align-text :                   center;"
						width="100%" cellpadding="0" cellspacing="0" class="table">
						<thead>
							<tr>
								<th height="24" align="center" bgcolor="#E4F1FA">
									档案编号
								</th>
								<th align="center" bgcolor="#E4F1FA">
									档案名称
								</th>
								<th align="center">
									档案类别
								</th>
								<th align="center">
									条码
								</th>
								<th align="center">
									芯片号
								</th>
								<th align="center">
									保密等级
								</th>

								<th align="center">
									入库人
								</th>
								<th align="center">
									入库时间
								</th>
								<th align="center">
									存放位置
								</th>
								<th align="center">
									操作
								</th>
							</tr>
						</thead>
						<tbody id="tbody">

						</tbody>
					</table>
				</div>
				<div style="margin-left: 10px;">
					<font color="red">*只显示在库且未作废档案信息</font>
				</div>

				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-bottom: 5px;">
					<tr>
						<td height="30" colspan="3" align="center">
							<span style="overflow: hidden; text-overflow: ellipsis;">
								<input type="button" class="btn02 JQueryPrint" name="button3"
									value="打印预览" /> <input type="button" class="btn02"
									name="button3" value="提交" id="toOutDetail" /> </span><span
								style="overflow: hidden; text-overflow: ellipsis;"> <input
									type="button" class="btn02 close" name="button" value="取消" />
							</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<iframe name="hidden" width="0" height="0"></iframe>


	</body>
</html>
