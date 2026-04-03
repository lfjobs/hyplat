<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + path + "/"; 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>数据导入页面</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>

		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/importdata/importdata.js"></script>
		<script src="<%=basePath%>swfupload/swfupload.js"></script>
		<script src="<%=basePath%>swfupload/handlers.js"></script>
		<script type="text/javascript">
			var basePath='<%=basePath%>';
			var swfu;			
		</script>
		<style type="text/css">
		<!--
			.btn3_mouseout {
				BORDER-RIGHT: #99CCCC 1px solid;
				PADDING-RIGHT: 2px;
				BORDER-TOP: #99CCCC 1px solid;
				PADDING-LEFT: 2px;
				FONT-SIZE: 12px;
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5);
				BORDER-LEFT: #99CCCC 1px solid;
				CURSOR: hand;
				COLOR: black;
				PADDING-TOP: 1px;
				BORDER-BOTTOM: #99CCCC 1px solid;
				HEIGHT: 20px
			}
			
			.btn3_mouseover {
				BORDER-RIGHT: #99CCCC 1px solid;
				PADDING-RIGHT: 2px;
				BORDER-TOP: #99CCCC 1px solid;
				PADDING-LEFT: 2px;
				FONT-SIZE: 12px;
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#0066CC);
				BORDER-LEFT: #99CCCC 1px solid;
				CURSOR: hand;
				COLOR: black;
				PADDING-TOP: 1px;
				BORDER-BOTTOM: #99CCCC 1px solid;
				HEIGHT: 20px
			}
			
			.btn3_mousedown {
				BORDER-RIGHT: #FFE400 1px solid;
				PADDING-RIGHT: 2px;
				BORDER-TOP: #FFE400 1px solid;
				PADDING-LEFT: 2px;
				FONT-SIZE: 12px;
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#0066CC);
				BORDER-LEFT: #FFE400 1px solid;
				CURSOR: hand;
				COLOR: black;
				PADDING-TOP: 1px;
				BORDER-BOTTOM: #FFE400 1px solid;
				HEIGHT: 20px
			}
			
			.btn3_mouseup {
				BORDER-RIGHT: #99CCCC 1px solid;
				PADDING-RIGHT: 2px;
				BORDER-TOP: #99CCCC 1px solid;
				PADDING-LEFT: 2px;
				FONT-SIZE: 12px;
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#C3DAF5);
				BORDER-LEFT: #99CCCC 1px solid;
				CURSOR: hand;
				COLOR: black;
				PADDING-TOP: 1px;
				BORDER-BOTTOM: #99CCCC 1px solid;
				HEIGHT: 20px
			}
		-->
		</style>
	</head>

    <body>
	<div class="contentbannb jqmWindow" style="top: 10%;width: 720px;right: 30%" id="jqModelShow">          
              <div class="drag">数据导入
                (目前仅支持xls格式)
                <div class="cc">
                </div>
              </div>  				
  				<table cellpadding="0px" cellspacing="0px" name="showstafftable" id="showstafftable" width="710" style="margin-left: 5px;" height="402"> 			    
				     <tr height="5px">
				       <td align="left" valign="top">&nbsp;</td>
				       <td align="left" valign="top">&nbsp;</td>
		          </tr>
				     <tr>
				       <td align="left" valign="top">文件类型：<select name="fileType" id="fileType">                          		
								<option value="0">请选择</option>
								<c:if test="${!empty fileMap}">
									<c:forEach items="${fileMap}" var="obj">
										<option value="${obj.key}">${obj.value.name}</option>
									</c:forEach>
								</c:if>
						  </select></td>
				       <td align="left" valign="top">
					   <div id="content">
							<form>
								<div
									style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;">
									<span id="spanButtonPlaceholder"></span>
									<input id="btnUpload" type="button" value="上  传"
										onclick="startUploadFile();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
										onmousedown="this.className='btn3_mousedown'"
										onMouseOver="this.className='btn3_mouseover'"
										onmouseout="this.className='btn3_mouseout'"/>
									<input id="btnCancel" type="button" value="取消所有上传"
										onclick="cancelUpload();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
										onmousedown="this.className='btn3_mousedown'"
										onMouseOver="this.className='btn3_mouseover'"
										onmouseout="this.className='btn3_mouseout'"/>
								</div>
							</form>
							<div id="divFileProgressContainer"></div>
							<div id="thumbnails">
								<table id="infoTable" border="0" width="550" style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
								</table>
							</div>
						</div>					
						</td>
		            </tr>			     
				     <tr>
					   <td height="25" colspan="2" align="center" valign="middle">
					   	<span id="spanProgressBar" style="display:none; vertical-align:middle"><img src="<%=basePath%>/images/progressbar.gif" width="24" height="24"/>&nbsp;&nbsp;正在努力工作中...</span>
						</td>
				    </tr>
					 <tr>
					   <td height="25" colspan="2" align="center">
					   		<input type="button"  class="input-button JQueryValidate" style="cursor:pointer;width:80px;" value="检查数据" />
							<input type="button"  class="input-button JQueryImport" style="cursor:pointer;width:80px;" value="导入" />
						    <input type="button"  class="input-button JQueryRollback" style="cursor:pointer;width:80px; display:none" disabled="disabled" value="回滚" />						
						</td>
				    </tr>
	  </table>			   
	</div>
	<form action="" name="" method="post">
		<input type="hidden" name="fileType1" id="fileType1"/><input type="hidden" name="fileName1" id="fileName1"/>
		<input type="hidden" name="fileType2" id="fileType2"/><input type="hidden" name="fileName2" id="fileName2"/>
		<input type="hidden" name="fileType3" id="fileType3"/><input type="hidden" name="fileName3" id="fileName3"/>
		<input type="hidden" name="fileType4" id="fileType4"/><input type="hidden" name="fileName4" id="fileName4"/>
		<input type="hidden" name="fileType5" id="fileType5"/><input type="hidden" name="fileName5" id="fileName5"/>
	</form>	
    </body>	
</html>

