
<%@page import="hy.ea.util.RandomDatas"%>
<%@page import="hy.ea.util.DateUtil"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();	
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String emailID = request.getParameter("emailID");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>写信</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/human/staff_post.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/plat/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
			 <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<script  src="<%=basePath%>js/ea/office_ea/email/email_add.js"></script>
<link rel="stylesheet" href="<%=basePath%>css/ea/photomanage/swfupload.css" type="text/css" charset="utf-8" />
<script src="<%=basePath%>swfupload/swfupload.js"></script>
<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
<script type="text/javascript"
			src="<%=basePath%>js/common/Concurrent.Thread.js"></script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
var emailID = "<%=emailID%>";
var zhType = "${zhType}";
var title = "${email.title}";
var tree1id;
var tree1name;
$(function(){
                $(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close')// 添加触发关闭的selector  
                 $("#jqModel").show();
      /*  tree1 = new dhtmlXTreeObject("staffTree", "100%", "100%", 0);
        tree1.enableDragAndDrop(false);
        tree1.enableHighlighting(1);
        tree1.enableCheckBoxes(0);
        tree1.enableThreeStateCheckboxes(false);
        tree1.setSkin('<%=basePath%>js/tree/dhx_skyblue');
        tree1.setImagePath("<%=basePath%>js/tree/codebase/imgs/");
        tree1.loadXML("<%=basePath%>js/tree/common/tree_b.xml");
        tree1.setOnDblClickHandler(function(){
            tree1id = tree1.getSelectedItemId();
            tree1name = tree1.getItemText(tree1id);
            var use = tree1.getUserData(tree1id,"status");
            var reciveID=$("#reciveID").val();
	        var reciveName=$("#reciveName").val();
            if(use!="used")
            {
             	tree1.setUserData(tree1id,"status","used");
	            $("#reciveID").attr("value",reciveID+tree1id+";");
	            $("#reciveName").attr("value",reciveName+tree1name+";");
            }
           else
           {
                tree1.setUserData(tree1id,"status","");
	            reciveID=reciveID.replace(tree1id+";","");
	            reciveName=reciveName.replace(tree1name+";","");
	            $("#reciveID").attr("value",reciveID);
	            $("#reciveName").attr("value",reciveName);
           }
        })    
             //公司下所有的账户
        var url = basePath +"ea/email/sajax_n_ea_getAccountList.jspa";
        var params = {};
            $.post(url, params, function cbf(data){
                var member = eval("(" + data + ")");
                var accountList = member.accountList;
                if (null != accountList) {
                    for (var i = 0; i < accountList.length; i++) {
                    tree1.insertNewChild("0", accountList[i].accountID, accountList[i].accountName, 0, 0, 0, 0); 
                   }
                }
            }, 'json'); */
            $(".JQuerySubmit").click(function(){
               $(".put3").trigger("blur");
               var status=$(this).attr("title");
                var hidIdList = document.getElementById("hidIdList").value;
                $("#cstaffForm").attr("action", basePath+"ea/email/ea_addEmail.jspa?status="+status+"&hidIdList="+hidIdList);
	            document.cstaffForm.submit.click();
	            document.cstaffForm.reset();
            })
                });
               
</script>


</head>
<body>    
<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
			<div style=" overflow:hidden; position:static;"
				class="jqmWindow" id="jqModel">
				<div>
					<table border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0" >
						<tr>
							<td >
								<table border="0" id="stafftable2"
									 cellpadding="0" cellspacing="0" >
									<tr>	
										<td>
										收件人：
										</td>
										<td>
											<input type="text" name="reciveName" 
												id="reciveName" size="40" value="${account.accountName}(${account.accountEmail});"/>
											<input type="hidden" name="reciveID"  id="reciveID" value="${account.accountID}"/>
										</td>
									</tr>
									<tr>
										<td height="40">
											主题：
										</td>
										<td>
											<input type="text"  id="title" 
												name="email.title" size="40" value="${email.title}"/>
										</td>
									</tr>
									<tr>
										<td colspan="2" height="30" align="left">
										
										    <span id="uploads"></span>
										   
										    
											<div  style="overflow:auto;">
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
										</td>

									</tr>
									<tr>
										<td height="200" align="left">
											正文：
										</td>
										<td align="left" colspan="5">
											<textarea name="email.content" cols="35" rows="12"
												id="content">${email.content}</textarea>
										</td>
									</tr>
									<tr>
										<td height="60" colspan="5" align="center">
											<input name="email.emailID" id="emailID" type="hidden"
												class="input" size="20" />
											<input name="email.emailKey" id="emailKey" type="hidden"
												class="input" size="20" />
											<input type="button" class="input-button JQuerySubmit"
												title="01" style="cursor: pointer; width: 80px;" value="发送" />
											<input type="button" class="input-button JQuerySubmit"
												title="02" style="cursor: pointer; width: 80px;"
												value="至草稿箱" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="取消" onclick="cancelButton();"/>
										</td>
									</tr>
								</table>
							</td>
							<td width="80"></td>
							<td width="150" align="left" valign="top">
							    
								<div id="staffTree" class="text_tree"
									style="width: 300px; overflow: auto; z-index: 99;"></div>
							</td>
						</tr>
					</table>
				</div>


			</div>
			<s:token></s:token>
            </form>
   
   
     <script type="text/javascript"> 
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
    String.prototype.trim = function() { 
             return this.replace(/(^\s*)|(\s*$)/g, ""); 
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/email/"+ "<%=DateUtil.getCurrentDate(DateUtil.C_DATE_PATTON_DEFAULT) %>",
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

    
    function dialogOpen() {
		$("#upload_content").show();
    } 
    </script>
	</body>
</html>	    