<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资料文档管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link rel="stylesheet"
			href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css"
			charset="utf-8" />
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/files_upload.js"></script>
<script src="<%=basePath%>js/ea/office_ea/corporationcode/FileManage.js"></script>

<script  type="text/javascript">
var companyID = '<%=session.getAttribute("companyID")%>';
   var  fileManageID = '';
   var  basePath='<%=basePath%>';           
   var  bpageNumber =${pageNumber};
   var  search='${search}';  
   var  token=0;
   var type = "${type}";
</script>
<style type="text/css">
 a{
 text-decoration:none;
 
 }
 span.sharespan{
  cursor:pointer;
 
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
<div id="main_main" class="main_main">

  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="30" align="center">选择</th>
            <th width="20" align="center" >序号</th>
            <th width="150" align="center">文件标题</th>
            <th width="170" align="center" ><s:if test='type=="1"'>上传单位</s:if><s:else>共享单位</s:else></th>
            <th width="100" align="center" ><s:if test='type=="1"'>上传人</s:if><s:else>共享人</s:else></th>
            <th width="134" align="center" ><s:if test='type=="1"'>上传时间</s:if><s:else>共享时间</s:else></th>
            <th width="100" align="center" >文件类型</th>
            <th width="100" align="center" >文件附件</th>
            <th width="130" align="center" >描述</th>
            <th width="130" align="center" >共享范围</th>
      </tr>
    </thead>
		<tbody>
		 <%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${fileManageID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${fileManageID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
				</td>
           
			 <td class="td_bg01">
             <span id="documentTitle">${documentTitle} 
             </span>
           			</td>
            <td class="td_bg01">
             <span id="planUnit">${planUnit}</span>
            </td>
            
     
            
           
            <td class="td_bg01">
             <span id="fileArincipal">${fileArincipal}</span>
            </td>
            
             <td class="td_bg01">
               <s:if test='type=="1"'> <span id="fileManageDate" class="fileManageDate">${fn:substring(fileManageDate,0,19)}</span></s:if>
               <s:else><span id="shareDate">${fn:substring(shareDate,0,19)}</span></s:else>
              
				</td>
            <td class="td_bg01">
             <span id="fileType">${fileType}</span>
            </td>
             <td class="td_bg01">
              <span id="fileAccessories" style="display:none">${fileAccessories}</span>
							
								
              <s:if test="fileAccessories==null||fileAccessories==''">无</s:if>
                             <s:else>
                                <span id="file"><s:if test='type=="1"'><a href="javascript:edit('${fileAccessories}')">编辑</a>&nbsp;|&nbsp;</s:if><a href="javascript:lookImage('${fileAccessories}')">查看</a>&nbsp;|&nbsp;<a href="javascript:loadFile('${fileAccessories}')">下载</a></span>
                            </s:else>
            </td>
               <td class="td_bg01">
             <span id="remark">${remark}</span>
             <span id="fileManageID" style="display:none">${fileManageID}</span>
		     <span id="fileManageKey" style="display:none">${fileManageKey}</span>
		       
            </td>
           
           <td class="td_bg01">
             <span id="shares" style="display:none;" >${shares}</span>
             <s:if test="shares==null||shares==''">仅自己可见</s:if><s:elseif test='shares=="1"'><img src="<%=basePath%>js/jqModal/css/images_blue/share16.png"/>部门共享</s:elseif><s:elseif test='shares=="2"'><img src="<%=basePath%>js/jqModal/css/images_blue/share16.png"/>公司共享</s:elseif><s:else><img src="<%=basePath%>js/jqModal/css/images_blue/share16.png"/>集团共享</s:else>
            </td>
          
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/fileManage/ea_getaFileManageList.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}"></c:param>
</c:import>
</div>
 <!--分享窗口 -->
     
            <form name="shareForm" id="shareForm" method="post" enctype="multipart/form-data">
            	<input type="submit" name="submit" style="display:none"/>
               <div class="jqmWindow " style="width: 300px;right: 35%; top:15%" id="jqModelShare">
                <div class="drag">
                    共享范围
                    <div class="close">
                    </div>
                </div>
               <center>
                <table id="shareTable" width="100%" cellspacing="10" cellpadding="10" >
                    <tr>
                        <td align="center" >
                           <input type="radio" value="1" name="fileManage.shares" checked="checked" id="b1"/><span class="sharespan" id="b">共享到当前部门</span>
                        </td>
                       
                    </tr>
                      <tr>
                        <td align="center">
                           <input type="radio" value="2" name="fileManage.shares" id="c1"/><span class="sharespan" id="c">共享到当前公司</span>
                        </td>
                       
                    </tr>
                      <tr>
                        <td align="center">
                           <input type="radio" value="3" name="fileManage.shares" id="g1"/><span class="sharespan" id="g">共享到当前集团</span>
                        </td>
                       
                    </tr>
                     <tr>
                        <td align="center">
                           <input type="radio" value="" name="fileManage.shares" id="z1"/><span class="sharespan" id="z">仅自己可见&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </td>
                       
                    </tr>
                </table>
                </center>
                <div align="center">
                    <input type="button" class="input-button" id="toshare" value=" 共享 " />
                     <input type="hidden"  id="fileManageIDs" value="" name="fileManage.fileManageID"/>
                     <input type="hidden"   value="${type}" name="type"/>
                </div>
               </div>
            </form>
		
 <!--搜索窗口 -->
     
            <form name="postSearchForm" id="postSearchForm" method="post" enctype="multipart/form-data">
            	<input type="submit" name="submit" style="display:none"/>
               <div class="jqmWindow " style="width: 300px;right: 35%; top:15%" id="jqModelSearch">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
               
                <table id="cataffSearchTable" width="100%">
                    <tr>
                        <td align="right">
                           文件标题：
                        </td>
                        <td>         
                           <input   name="fileManage.documentTitle" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                    <input name="type" type="hidden" value="${type}" />
                </div>
               </div>
            </form>
       
		
		
		<!--添加 -->
		<form name="cstaffForm" id="cstaffForm" method="post" action=""
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss" style="top: 10%; width: 600px"
				id="jqModel">

				<div class="drag">
					添加
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
								文件标题：
							</td>
							<td colspan="2">
								<input name="fileManage.documentTitle" type="text"
									id="documentTitle" size="20" />


								<input name="fileManage.fileManageID" type="hidden"
									id="fileManageID" />
								<input name="fileManage.fileManageKey" type="hidden"
									id="fileManageKey" />

							</td>
						</tr>
						<tr>
							<td align="right" >
								文件类型：
							</td>
							<td colspan="2">
								<input name="fileManage.fileType" type="text" size="20"
									id="fileType" />
							</td>
						</tr>
					
						<tr>
						<td align="right" >
							上传文件：
						</td>
						<td align="left" style="width:60%;">
					
							<div class="upload_Bottom" id="showPicture"
								style="overflow: hidden;">
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

						
						<tr>
							<td align="right">
								描述：
							</td>
							<td colspan="3">
								<textarea name="fileManage.remark" id="remark" cols="56"
									rows="5"></textarea>
							</td>

						</tr>
					</table>


					<div align="center">
						<input type="button" class="input-button"
							style="cursor: pointer; width: 80px;" onclick="uploads();" id="BtnSave" value=" 保存 " />
						<input type="button" class="input-button JQueryreturn"
							style="cursor: pointer; width: 80px;" value="取消" />
					</div>
				</div>
				<s:token></s:token>
		</form>
		
		
		
		
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<script type="text/javascript"> 
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{ 
		                    path: "/upload_files/"+companyID+"/office/filemanage/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT)%>",
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
		    file_upload_limit: 1,
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
