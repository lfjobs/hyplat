<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>标题</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
 
 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main2.css"/>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
 <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

$(function(){
var fileID="";
$(".JQuerySubmit").click(function(){
 $(".put3").trigger("blur");
  if ($("#uploadForm .error").length) {
	                        alert("请填完所有必填项")
	                        return;
	                    }
	$("#uploadForm").attr("action","<%=basePath%>ea/uploadfile/ea_saveImage.jspa?");
	document.uploadForm.submit.click();
})

 $(".table tr[id]").click(function(){
                    fileID = this.id;
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    $("tr#"+fileID).find("span[id]").each(function(){
                          $("table#sc").find(":input[name]#" + this.id).val($(this).text());
                      });
                    $("#imageField").attr("src","<%=basePath%>"+$("tr#"+fileID).find("#filepath").text())
  })
  $("#photofile").change(function(){
	   $("#imageField").attr("src",$(this).attr("value"))
  })
	
})
function edits(fID){
 $("tr#"+fID).find("span[id]").each(function(){
       $("table#sc").find(":input[name]#" + this.id).val($(this).text());
   });
    $("#imageField").attr("src","<%=basePath%>"+$("tr#"+fID).find("#filepath").text())
}
 function sc(fID){
		if (confirm("确定删除？")){
		    $("#uploadForm").find("input#fileID").attr("value",fID);
		    $("#uploadForm").attr("action","<%=basePath%>ea/uploadfile/ea_delImage.jspa?");
			document.uploadForm.submit.click();
		}
   }
</script>
    </head>
    
<body>
<form name="uploadForm" id="uploadForm" action="a" method="post" enctype="multipart/form-data"> 
         <input type="submit" name="submit" style="display:none"/><s:token></s:token>
<div class="content" style="width:800px; background:#FFFFFF;">
  <div class="contentbannb">
  	<div class="divtx">附件管理</div>
    <div class="close"></div>
  </div>
    
     <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="sc">
                <tr>
                  <td width="127" height="35" align="right">附件名称：</td>
                  <td width="357">
                    <input name="loadFile.filename" type="text" class="input01 put3" id="filename"size="30"/></td>
                  <td width="316" rowspan="3" align="center" valign="middle"><label>
                  <img id="imageField" width="200" height="150"  name="imageField" src="" align="middle"/>
                  </label></td>
                </tr>
                <tr>
                  <td height="35" width="127" align="right">附件书名：</td>
                  <td><textarea name="loadFile.filedesc" id="filedesc" cols="30" rows="4" class="input01"></textarea></td>
                </tr>
                <tr>
                  <td width="127" height="35" align="right">附件：</td>
                  <td width="357">
                    <input type="file" name="photo" class="put3" id="photofile"/>
                     <input type="hidden" id="filepath" name="loadFile.filepath"/>
                    <input type="button" class="code_ann JQuerySubmit" name="button7" value="上传"/>
                      <input name="loadFile.fileID"  type="hidden" class="input01" id="fileID"size="30"/>
                       <input name="loadFile.parmeterID" value="${loadFile.parmeterID}" type="hidden" class="input01" id="parmeterID"size="30"/>
                      <input name="loadFile.fileKey"  type="hidden" class="input01" id="fileKey"size="30"/></td>
                </tr>
                <tr>
                  <td height="90" colspan="3" align="center">
                  <table width="99%" align="center" cellspacing="1" cellpadding="1" style="margin-top:5px;" class="table">
                    <tr>
                      <th width="35" height="25" align="center">选择</th>
                      <th align="center">附件名称</th>
                      <th align="center">附件说明</th>
                      <th width="50" align="center">操作</th>
                    </tr>
                      <s:iterator value="pageForm.list">
                    <tr id="${fileID}">
                     <td><input type="radio" name="a"  class="JQuerypersonvalue" value="${fileID}"/></td>
                      <td align="center"><span id="filename">${filename}</span></td>
                      <td align="center"><span id="filedesc">${filedesc}</span>
                      <span id="filepath" style="display:none">${filepath}</span>
                      <span id="fileID" style="display:none">${fileID}</span>
                      <span id="fileKey" style="display:none">${fileKey}</span>
                      <span id="parmeterID" style="display:none">${parmeterID}</span></td>
                      <td align="center"><a href="#" onclick="edits('${fileID}')"><img src="<%=basePath%>/images/admin_images/edit.gif" width="16" height="16" title="编辑" border="0"/></a>&nbsp;&nbsp; <a href="#" onclick="sc('${fileID}')"><img src="<%=basePath%>/images/admin_images/gtk-del.png" width="16" height="16" title="删除" border="0"/></a></td>
                    </tr>
                    </s:iterator>
                  </table></td>
              </tr>
  </table>
    <c:import url="../page_navigator.jsp">
            <c:param name="actionPath" value="ea_getListImage.jspa?loadFile.parmeterID=${loadFile.parmeterID}">
            </c:param>
    </c:import>
</div>
</form>
</body>
</html>
