<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>胜威驾校微信菜单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/wechat/wechatMenuList.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
<script src="<%=basePath%>swfupload/swfupload.js"></script>
<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>
<script type="text/javascript">
	var basePath='<%=basePath%>';           
	var pNumber ='${pageNumber}';  
	var token=0;
	var notoken = 0;
	var frameid = '<%=request.getParameter("frameid")%>';
	var mainheight = 0; //框架高度
	var menuPid='${menuPid}';
	var menuId='';
</script>
</head>
<body>
	<form style="display: none;" name="addressForm" id="addressForm" method="post">
		<s:token></s:token>
		<input type="submit" name="submit" style="display:none"/>
	</form>
	<div id="main_main" class="main_main" >
		<table class="JQueryflexme">
			<thead>
				<tr>
					<th width="40" align="center">
						选择
					</th>
					<th width="40" align="center">
						序号
					</th>
					<th width="180" align="center">
						菜单名称
					</th>
					<th width="180" align="center">
						父菜单名称
					</th>
					<th width="150" align="center">
						创建时间
					</th>
					<th  width="200" align="center">
						公司
					</th>
					<th width="180" align="center">
						内容
					</th>
					
				</tr>			
				</thead>			
				<tbody>
					<% int number = 1; %>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr  id="${arr[0]}">
							<td>
								<input type="radio" name="chbox" class="JQuerypersonvalue" value="${arr[0]}" />
							</td>
							<td>
								<span id="number"><%=number%></span>
							</td>
							<td>
								<span id="menuname">${arr[1]}</span>
								<span id="menuId" style="display: none">${arr[0]}</span>
							</td>
							<td>
								<span id="menuPid">${arr[6] } </span>
							</td>
							<td>
								<span id="createdate"><fmt:formatDate value="${arr[2]}"  pattern="yyyy-MM-dd" /> </span>
							</td>
							<td>
								<span id="company">${arr[4]}</span> 
							</td>
							<td>
								<span id="content1">${arr[3]}</span> 
							</td>							
						</tr>
						<% number++; %>
					</c:forEach>
				</tbody>
		</table>		
	<!-- 添加  -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%;height: 500px; width: 600px;"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post">
			 <input type="submit" name="submit" id="submit" style="display: none" />
			       <div class="drag">添加菜单
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>			
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table  border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100"  align="center">菜单名称：</td>
			        <td width="200" colspan="1"> 
			        	<input name="wechatMenu.menuName" type="text" id="menuname" size="20"/>
						<input type="hidden" name="wechatMenu.menuId" id="menuId" />
					</td>				
					<td rowspan="3" >
						<div contenteditable="" id="post_article_content" style="border:1px solid #000; width: 100px;height: 100px;overflow: hidden;"></div>
						<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
							<tbody id="divFileProgressContainer">
							</tbody>
							<tfoot>
								<tr>
									<td colspan="4">
										<input type="hidden" id="hidIdList"/>
									</td>
								</tr>
							</tfoot>
						</table>
					<div  style="border:4px solid #000; width: 100px;height: 100px;overflow: hidden;" id="pimage">						
					</div>
					</td>
		          </tr>
		          <tr>
		          	<td align="center">所属菜单：</td>
					<td>
						<s:select 
					        headerKey="" headerValue="请选择"
 					        list="%{#request.listmenu}" listKey="menuId"
							listValue="menuName" name="wechatMenu.menuPid" id="roleName" theme="simple">
						</s:select>
					</td>
		          </tr>
		          <tr>
			          <td align="center" >顶部菜单：</td>
			          <td >
			          	<select name="wechatMenu.topmenu"  style="width: 60px;">
							<option value="01"/>是</option>
							<option value="02" selected="selected"/>否</option>
						</select>
			         </td>
		          </tr>
		          <tr>
			          <td align="center" >编辑内容：</td>
			           <td align="center" ></td>
			          <td  colspan="2"><span id="uploads"></span></td>
		          </tr>
		          <tr>
	       		   	  <td align="left" colspan="3">	
						<textarea cols="70" id="content" name="wechatMenu.content" rows="5">							
	              		</textarea>
						<script type="text/javascript">
                   			 var editor = CKEDITOR.replace( 'content',
                                  {
                                  skin : 'kama',
                                  language : 'zh-cn',
                                  height:130
                                  });
                       </script>
					</td>
		           </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			  <div align="center">
                    <input type="button" class="input-button" style="cursor: pointer; width: 80px;" id="tosave" value=" 保存 " />
					<input type="button" class="input-button JQueryreturn" style="cursor: pointer; width: 80px;" value="取消" />
                </div>
			</form>
	</div>	
		<c:import url="../../ea/page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/wechatmenu/getMenuList.jspa?pageNumber=${pageNumber}&menuPid=${menuPid}&search=${search}"></c:param>
		</c:import>
	</div>
	<iframe name="hidden" height="0" width="0"></iframe>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
   var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load(){
    String.prototype.trim = function(){
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
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>images/shengwei/menu_1.jpg",
            button_width:100,
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
        var hidIdList=$("#hidIdList").val();
    	var result=hidIdList.split(",");
    	var str="";
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='width: 100px;height:100px;'/></center>";
    	}
    	 $("#post_article_content").append(str);
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
    }
    </script>
	<script type="text/javascript">
	 $(function(){
	       setTimeout(function(){
			   var _height = $(window).height();	
			   if($("#mainframe").height() > 0){
			       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			   }else{
			   	   $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
			       $("#mainframe").css({"height": 0 + "px"});
			   }
			},100);
		    $(window).resize(function(){
				setTimeout(function(){
			    var _height = $(window).height();
			    if($("#mainframe").height() > 0){
			        $(".bDiv").css({"height":_height /2 - 30 - 26 - 50 + "px"});
				    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			    }else{
			   	    $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
					$("#mainframe").css({"height": 0 + "px"});
			    }
			},100);
		    }); 
	   });
	</script>
</body>
</html>