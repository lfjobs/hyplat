<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考勤核算周期预设</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"type="text/css"/>
<script type="text/javascript">
        var basePath="<%=basePath%>";
        var token = "0";
</script>
<script src="<%=basePath%>/js/ea/validate.js"  type="text/javascript"></script>
	</head>

<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form name="cycleform" method="post" id="cycleform">
	<input type="submit" name="submit" style="display:none"/>
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td align="left" valign="top">
      <div class="right">
    	<div class="qh_gg_nav">&nbsp;考勤核算周期预设</div> 
          <table width="100%" 　border="0" align="center" cellpadding="0"
						cellspacing="0">
                    <tr>
                      <td class="td_bg" height="30" align="right"width="20%" ><input name="status" type="radio" class="rad" checked="checked" value="00"/></td>
                      <td class="td_bg" width="12%" align="center" >
                      	按自然月核算
                      </td>
                      <td class="td_bg">
                      	每月第一天到最后一天
                      </td>
                    </tr>
                    <tr>
                      <td  height="30" align="right" class="td_bg"><input name="status" class="rad" type="radio" value="01"/></td>
                      <td class="td_bg" align="center" >
                      	个性设置
                      </td>
                      <td class="td_bg">
                      	本月<select name="begintime" id="begintime" disabled="disabled">
                      			<option value="" ></option>
                      			<option value="2">2</option>
                      			<option value="3">3</option>
                      			<option value="4">4</option>
                      			<option value="5">5</option>
                      			<option value="6">6</option>
                      			<option value="7">7</option>
                      			<option value="8">8</option>
                      			<option value="9">9</option>
                      			<option value="10">10</option>
                      			<option value="11">11</option>
                      			<option value="12">12</option>
                      			<option value="13">13</option>
                      			<option value="14">14</option>
                      			<option value="15">15</option>
                      			<option value="16">16</option>
                      			<option value="17">17</option>
                      			<option value="18">18</option>
                      			<option value="19">19</option>
                      			<option value="20">20</option>
                      			<option value="21">21</option>
                      			<option value="22">22</option>
                      			<option value="23">23</option>
                      			<option value="24">24</option>
                      			<option value="25">25</option>
                      			<option value="26">26</option>
                      			<option value="27">27</option>
                      			<option value="28">28</option>
                      			<option value="29">29</option>
                      			<option value="30">30</option>
                      			<option value="31">31</option>
                      		</select>
                      	日----	
                      	下月 <input name="endtime" id="endtime" size="4" value="" readonly="readonly"/> 日	
                      </td>
                    </tr>
                    <tr>
                      <td height="39" align="center" class="td_bg">&nbsp;</td>
                      <td height="39" colspan="2" align="left" class="td_bg">
                      <input type="button" class="ACT_btn" id="tosave" value=" 保存 " />
                      <td width="16%" height="39" align="left" class="td_bg">&nbsp;</td>
                    </tr>
                    <tr>
                      <td height="18" colspan="5" align="center" class="td_bg">${result}</td>
                    </tr>
                  </table>
				  <s:token></s:token>
        </div>
      </td>
  </tr>
</table>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
	$(function(){
		var url = basePath + "ea/attendcycle/sajax_ea_getAttendCycle.jspa?d=" + new Date();
		$.ajax({
	        url : encodeURI(url),
	        type : "get",
	        async : false,
	        success : function cbf(data) { 
	            var member = eval("(" + data + ")");
	            var cycle = member.cycle;
	            if(cycle.status == "01"){
	            	$(".rad").each(function(){
	            		if($(this).val() == 01){
	            			$(this).attr("checked","checked");	
	            		}else{
	            			$(this).attr("checked","");	
	            		}
	            	});
	      			$("select#begintime").attr("disabled","")	
	      			$("select#begintime").find("option[value='"+cycle.begintime+"']").attr("selected",true);
	      			$("#endtime").attr("value",cycle.endtime);
	            }
	        },
	        error : function cbf(data) {
	            alert("获取数据失败!");
	        }
	    });
	     $("#begintime").change(function(){
		      	if($(this).val() == ""){
		      		$("#endtime").attr("value","");
		      	}else{
		      		$("#endtime").attr("value",$(this).val()-1);
		      	}
		      });
		 $(".rad").click(function(){
	      		if($(this).val() == "00"){
	      			$("select#begintime").attr("disabled","disabled")	
	      			$("select#begintime").find("option[value='']").attr("selected",true);
	      			$("#endtime").attr("value","");
	      		}else{
	      			$("select#begintime").attr("disabled","")	
	      		}
	      });
		$("#tosave").click(function(){
				if($("input[type='radio']:checked").val() == "01" && $("select#begintime").val() == ""){
					alert("请选择日期!");
					return;
				}
				$("#cycleform").attr("target","hidden").attr("action",basePath + "ea/attendcycle/ea_save.jspa?");
				document.cycleform.submit.click();
				token = "2";
				$("#tosave").attr("disabled","disabled");
		});
	});
function re_load(){
		 document.location.href=basePath+"/page/ea/main/human/adance/attendcycle.jsp?";
}
</script>
</body>
</html>
