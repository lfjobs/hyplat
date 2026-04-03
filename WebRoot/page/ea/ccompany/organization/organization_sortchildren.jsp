<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构排序</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/human/attendance.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<form name="childrenform" method="post" id="childrenform">
	<input type="submit" name="submit" style="display:none"/>
	<input value="${organizationID}" name="organizationID" type="hidden" >
	<input value="${organizationName}" name="organizationName" type="hidden">
	<input id="childrenID" name="childrenID" type="hidden">
	<s:token></s:token>
</form>
<div id="body_02">
          <table width="100%" height="26" align="center" cellspacing="0" cellpadding="1" style="font-size:12px;" class="bannb_01">
            <tr>
                <td height="24" align="left" valign="top" class="txt01" >下属机构排序</td>
            </tr>
          </table>
  			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;" class="clear">
              <tr>
                <td width="1%" height="26">&nbsp;</td>
                <td width="2%"><div class="list_add"></div></td>
                <td width="97%"><span class="link02"></span></td>
              </tr>
            </table>
            <table width="99%" height="37" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="22%" height="30">&nbsp;</td>
                  <td width="22%">当前机构排序：</td>
                  <td width="8%" align="center">&nbsp;</td>
                  <td width="48%">重新排序</td>
              </tr>
 		    </table>
            <table width="99%" height="204" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td width="46%" align="right">
                	<SELECT name="staff" id="select1" size="15" style="width: 120">
                		<s:iterator value="children">
		        			<option value="<s:property value='organizationID' />"><s:property value="organizationName" />
		        		</s:iterator>	
                	</SELECT>
				</td>
                <td width="4%" align="center">
                	<div class="right_dan" id="add"></div>
					<div class="left_dan" id="del"></div>
					<div class="right_suang"  id="add_all"></div>
					<div class="left_suang" id="del_all"></div>
				</td>
                <td width="50%">
                	<SELECT name="staff1" id="select2" size="15" style="width: 120">
                 		<%-- 将被重排的机构--%>
                	</SELECT>
				</td>
              </tr>
            </table>
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td height="30" align="center"><input type="button" id="save_all" class="btn02" name="button" value="保存" />
                  <input type="button" class="btn02" name="button" id="goback" value="返回" /></td>
                </tr>
 		    </table>
</div>
<script type="text/javascript">
var basePath = '<%=basePath%>';
$(function(){
	$('#add').click(function(){
		var $options = $('#select1 option:selected');
		$options.appendTo('#select2');
	});
	$('#add_all').click(function(){
		var $options = $('#select1 option');
		$options.appendTo('#select2');
	});
	
	$('#del').click(function(){
		var $options = $('#select2 option:selected');
		$options.appendTo('#select1');
	});
	$('#del_all').click(function(){
		var $options = $('#select2 option');
		$options.appendTo('#select1');
	});
	
	$('#select1').dblclick(function(){
		var $options = $('option:selected',this);
		$options.appendTo('#select2');
	});
	
	$('#select2').dblclick(function(){
		var $options = $('option:selected',this);
		$options.appendTo('#select1');
	});
	$('#save_all').click(function(){
		var $options = $('#select1 option');
		if($options.length > 0){
			alert('您必须对所有下属机构排序.');
			return;
		}
		var childrenID = "";
		var $options = $('#select2 option');
		$options.each(function(){
			window.parent.tree.moveItem(this.value,'item_child',parent.treeid);
			childrenID += this.value+'_';
		});
		if(childrenID == ""){
			return ;
		}
		$('#childrenID').val(childrenID);
		document.childrenform.action = basePath + "ea/corganization/t_ea_sortChildOrganization.jspa?pageNumber=${pageNumber}";
		document.childrenform.submit.click(); 
	});
	$('#goback').click(function(){
		document.childrenform.action = basePath + "ea/corganization/ea_getOrganizationListAll.jspa?pageNumber=${pageNumber}";
		document.childrenform.submit.click(); 
	}); 
});
</script>
</body>
</html>
