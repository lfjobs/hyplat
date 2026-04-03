<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/main.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/table.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
<title>收入完成率</title>
<script type="text/javascript"> 
</script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
var level="${level}";
var comp = "${comp}";
var sztype="${sztype}";

$(function(){
if(sztype==""){
   $("#cataffSearchTable tr.sz").show();
}else{
 $("#cataffSearchTable tr.sz").hide();

}
if(sztype=""){
  sztype="s";
}
$("#selectsz").change(function(){
if($(this).val()=="s"){
$("#szt").text("收入");
 sztype="s";

}else{
$("#szt").text("支出");
 sztype="z";

}


});

 $("#tosearch").click(function(){
                        $(".put3").trigger("blur");
                        if ($("#SearchForm .error").length) {
                            alert("请填完所有必填项");
                            return;
                        }
                        var year = $("#year").val();
                    window.open(basePath + "/ea/earnbudget/ea_searchCompleteRate.jspa?comp="+comp+"&earnBudgetBill.year="+year+"&sztype="+sztype);
                });
});
 
</script>
</head>
<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form  name="SearchForm" method="post" id="SearchForm">
<input type="submit" name="submit" style="display:none"/>
<div class="main" >
<table width="98%" border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="#D1DDE9" style="margin-top:8px" >
        <tr bgcolor="#E7E7E7" >
            <td height="24" width="20%" align="left" bgcolor="#d8e6f4">&nbsp;<span class="txt">查询</span>&nbsp;</td>
            <td width="60%" height="24" bgcolor="#d8e6f4">&nbsp;</td>
            <td width="20%"  height="24" align="right" bgcolor="#d8e6f4">
           </td>
        </tr>
</table>
         <table id="cataffSearchTable" width="98%" height="180" align="center" style="border:#D1DDE9 1px solid;">
                    <tr class="sz" style="display:none;">
                     <td width="35%"  align="right" class="txt02">
                       <s:if test="comp=='budget'">预算</s:if><s:else>调整</s:else>完成率：
                     </td>
                      <td width="40%">
                               <select style="width:130px;" id="selectsz">
                               <option value="s">收入<s:if test="comp=='budget'">预算</s:if><s:else>调整</s:else>完成率</option>
                               <option value="z">支出<s:if test="comp=='budget'">预算</s:if><s:else>调整</s:else>完成率</option>
                               </select>
                        </td>
                    </tr>
                    <tr height="40">
                      
                        <td width="35%"  align="right" class="txt02">
                                <s:if test='sztype=="s"'>收入</s:if><s:elseif test='sztype=="z"'>支出</s:elseif><s:else><span id="szt">收入</span></s:else><span>预算日期：</span>
                        </td>
                        <td width="40%">
                           <input type="text" name="earnBudgetBill.year"  id="year" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy'})" class="Wdate put3"/>
                        </td>
                    </tr>
                     <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
</body>
</html>