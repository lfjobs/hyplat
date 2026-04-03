<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>菜单添加/修改</title>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script type="text/javascript">

function edit(menuKey,menuID){
        $(".put3").trigger("blur");
          if($("form .error").length)
          { 
            return;
          } 
        $('#smenuEdit').attr("action","<%=basePath%>ea/cmenu/t_ea_saveCMenu.jspa?pageNumber=${pageNumber}&cmenu.menuKey=" + menuKey + "&cmenu.menuID="+menuID);
		document.smenuEdit.submit.click();  
		alert("保存成功！");  
}
</script>
</head>
<body>
<form  name="smenuEdit" method="post" id="smenuEdit">
<input type="submit" name="submit" style="display:none"/>
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#d8e6f4" style="margin-top:8px" >
		<tr bgcolor="#E7E7E7">
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">菜单添加/修改</span>&nbsp;</td>
  		    <td width="51%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
			<td width="20%" height="24" align="right" bgcolor="#d8e6f4">
            <img src="<%=basePath%>images/ea/main/list_add.gif" width="8" height="8" /> <a href="<%=basePath%>ea/cmenu/ea_getListCMenu.jspa" class="link02">返回菜单列表</a></td>
  		</tr>
</table>
		<table width="98%" height="166" align="center" style="border:#d8e6f4 1px solid;">
			<tr height="22">
              <td width="35%" height="33" align="right" class="txt02">菜单名称：</td>
    		  <td><input name="cmenu.menuName" type="text" class="kuang put3 ckTextLength" maxlength="50" size="30" value="${cmenu.menuName}"/><span style="color:#FF0000;">*</span></td>
            </tr>
		    <tr height="22">
              <td width="35%" height="33" align="right" class="txt02">菜单排序号：</td>
    		  <td><input name="cmenu.menuNumber" type="text" maxlength="2" class="kuang put3"  size="30" value="${cmenu.menuNumber}"/><span style="color:#FF0000;">*</span></td>
            </tr>
		     <tr height="22" >
              <td height="33" align="right"><span class="txt02">所属EA：</span></td>
              <td>
	                <s:select list="%{#request.cealist}"  listKey="eaID"  listValue="eaName"  name="cmenu.eaID" theme="simple" >                   </s:select>              </td>
           </tr>
           
            <tr height="22" >
              <td height="90" align="right"><span class="txt02">菜单描述：</span></td>
              <td><textarea name="cmenu.menuDesc" maxlength="250" class="ckTextLength" cols="40" rows="5">${cmenu.menuDesc}</textarea></td>
             </tr>            
           <tr align="center" bgcolor="#FFFFFF" height="22" >
             <td height="33" align="right">&nbsp;</td>
             <td height="33" align="center">
           <div class="submit" onclick="edit('${cmenu.menuKey}','${cmenu.menuID}')">保存</div>             </tr> 
		</table>
  <s:token/>
</form>
</body>
</html>