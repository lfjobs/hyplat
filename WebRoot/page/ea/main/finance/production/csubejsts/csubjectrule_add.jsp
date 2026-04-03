<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache"/>
		<title></title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/finance/finance_add.css"/>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"> </script>
		<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script>
    $(function(){
    	var basePath="<%=basePath%>";
		usableLevel='${subRule.usableLevel}';
		var rulesArray = new Array();
		var i=1;
		var b=true;
		$(".JQueryrules").each(function(){
			rulesArray[i]=$(this).val();
			if(i>=usableLevel){
				return false;
			}
			i++;
		})
     	$("#tosave").click(function(){
     		if(b){
     		
				$("#currentcountform").attr("action", "<%=basePath%>ea/csubjectsrule/t_ea_saveCSubjectsRule.jspa?pageNumber=${pageNumber}");
	          //  document.currentcountform.submit.click();
				parent.location.reload();
			}else{
				alert("如果想修改编码位数，请清除与此级别相关的科目内容");
			}
		})
		//var usedLevel = '${subRule.usedLevel}' == ""?'2' :'${subRule.usedLevel}'
		//$(".JQueryrules").slice(0,usedLevel).attr("readonly","readonly");
		$(".JQueryrules").blur(function(){
			var num=$(":input").index($(this))-1;
			if(num<=usableLevel){
				if(parseInt($(this).val())!=parseInt(rulesArray[num])){
					var url=basePath+"ea/csubjectsrule/sajax_ea_ajaxCsubejstsList.jspa?currentLevel="+num;
					$.ajax({
                        url: encodeURI(url),
                        type: "get",
                        async: false,
                        dataType: "json",
                        success: function cbf(data){
			              var member = eval("(" + data + ")");
			              var nologin = member.nologin;
			              if(nologin){
			                  document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			              }
			              var as = member.as;
			              if(as==1){
			                   alert("如果想修改编码位数，请清除与此级别相关的科目内容");
			                   b=false;
			               }
               		   },
                       error: function cbf(data){
                           alert("数据获取失败！");
                       }
                    });
				}
			}
		})
    })
    
</script>
	</head>
	<body>
    <div  style="width:100%;top:20%;right:20%; border:#7F9DB9 solid 1px;">
<div class="drag">科目编码规则</div>
	     <div class="unitlib_list_right02" >
	      <form method="post" name="currentcountform" id="currentcountform">
		<table width="550" height="248" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-bottom::5px;">
     <tr>
        <td height="30" align="center">
            <input type="submit" name="submit" style="display:none"/>
            <input name="subRule.usedLevel" value="${subRule.usedLevel}"    id="usedLevel" size="20" type="hidden"/>
            第一级：<input name="subRule.rules" value="2"  class="JQueryrules" size="20" readonly="readonly"/> </td>
        </tr>
     <tr>
       <td height="30" align="center">第二级：<input name="subRule.rules" value="3"  class="JQueryrules" size="20" readonly="readonly"/></td>
       </tr>
     <tr>
       <td height="30" align="center">第三级：<input name="subRule.rules" value="${subRule.rulesArray[2]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第四级：<input name="subRule.rules" value="${subRule.rulesArray[3]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第五级：<input name="subRule.rules" value="${subRule.rulesArray[4]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第六级：<input name="subRule.rules" value="${subRule.rulesArray[5]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第七级：<input name="subRule.rules" value="${subRule.rulesArray[6]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第八级：<input name="subRule.rules" value="${subRule.rulesArray[7]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center">第九级：<input name="subRule.rules" value="${subRule.rulesArray[8]}"  class="JQueryrules" size="20" /></td>
       </tr>
     <tr>
       <td height="30" align="center"><input type="submit" class="btn01" name="button" id="tosave" value="保存" /></td>       
       </tr>
       <tr>
       <td height="30" align="center"><span style="color: red;font-size: 20px">文本框所填值为相对应级别编号的位数,请慎重填写！</span></td>       
       </tr>
</table>
<s:token></s:token></form>
</div>
	</div>
</body>
</html>