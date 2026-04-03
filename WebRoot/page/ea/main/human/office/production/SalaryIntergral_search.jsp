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
<title>工资管理</title>
<script type="text/javascript"> 
</script>

<script type="text/javascript">
var basePath = "<%=basePath%>";
$(function(){
    $("#tosearch").click(function(){
    	if($(".rad:checked").val() == undefined){
    		alert('请选择查询方式！');
    		return;
    	};
        if($(".rad:checked").val() == '1'){
        	$(".put3").trigger("blur");
	        if ($("#SearchForm .error").length){
	            alert("请填完所有必填项");
	            return;
	        }
        	$("#SearchForm").attr("action", basePath+"ea/logbooksummary/ea_getListLogBookIntegral.jspa?");
        }else{
        	var cname = '';
        	if($("#staffType").val() != ''){
        		cname = $("#sty").find("option:selected").text();
        	}
        	$("#SearchForm").attr("action", basePath+"ea/monthSalary/ea_toSearch.jspa?monthSalary.categoryname="+cname);
        }
        $(".rad:checked").removeAttr("checked"); //去除选中事件
        document.SearchForm.submit.click();
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
                   <tr>
                    <td width="35%" align="right" class="txt02">
                      <input type="radio" name="ra" class="rad" value="1"/>按日查询
                      
                    </td>
                    <td width="40%" style="font-size: 12px;color: #000000;">
                      <input type="radio" name="ra" class="rad" value="2"/>按月查询
                    </td>
                    </tr> 
                    <tr>
                        <td align="right" class="txt02">
                            员工姓名：
                        </td>
                        <td width="40%">
                            <input id="sname"/>
                        </td>
                    </tr>
                    <tr class="rad1" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                            起始时间：
                        </td>
                        <td width="40%">
                            <input name="sdate" class="put3" onfocus="date(this);"/>
                        </td>
                    </tr>
                    <tr class="rad1" style="display: none;">
                        <td width="35%"  align="right" class="txt02">
                            结束时间：
                        </td>
                        <td width="40%">
                            <input name="edate" class="put3" onfocus="date(this);"/>
                        </td>
                    </tr>
                     <tr class="rad1" style="display: none;">
                        <td width="40%"  align="right">
                               查询方式：
                        </td>
                        <td width="40%">
                           <select name="arg" class="put3" >
                           	<option value="0">日期未分组</option>
                           	<option value="1">日期分组</option>
                           </select>
                        </td>
                    </tr>
                    <tr width="40%" class="rad2" style="display: none;">
                        <td width="40%" class="txt02" align="right" >
                            月份：
                        </td>
                        <td width="40%">
                            <input name="monthSalary.months" onfocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
                        </td>
                    </tr>
                     <tr id="sty">
                        <td width="35%"  align="right" class="txt02">
                            员工种类：
                        </td>
                        <td width="40%"><select id="staffType" name="staffcategoryid"></select></td>
                        
                    </tr>
                    <tr height="40">
                        <td  colspan="1"  align="center" class="txt02">
                            <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
</div>
</form>
<script type="text/javascript">
		$(document).ready(function(){
			//员工类别
	       	var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date().toLocaleString();
	        $.ajax({
	                    url: url,
	                    type: "get",
	                    async: true,
	                    dataType: "json",
	                    success: function cbf(data){
			           		var member = eval("(" + data + ")");
			           		var nologin = member.nologin;
							if(nologin){
			                	document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			                }
			           		var staffTypeList = member.staffTypeList;
			           		var htmlStr="<option value=''>请选择</option>";
			           		if (null != staffTypeList) {
				               	for (var i = 0; i < staffTypeList.length; i++) {
				               		htmlStr+="<option value='"+staffTypeList[i].codeID+"'>"+staffTypeList[i].codeValue+"</option>";
				             	}
				             	}
				             $("#staffType").html(htmlStr);
	           			},error: function cbf(data){
	                          		alert("数据获取失败！");
	                    }
	        });	
		});
		$(".rad").change(function() { 
			if(this.value=="1"){
				$(".rad1").show();
				$(".rad2").hide();
				$("#sname").attr("name","staffName");
			}else{
				$(".rad1").hide();
				$(".rad2").show();
				$("#sname").attr("name","monthSalary.staffname");
			}
		}); 
</script>
</body>
</html>