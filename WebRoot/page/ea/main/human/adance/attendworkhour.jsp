<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>考勤正常工作时间预设</title>
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
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>/js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript">
		        var basePath="<%=basePath%>";
		        var token = 0;
		</script>

	</head>

<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<form name="workhourform" method="post" id="workhourform">
	<input type="submit" name="submit" style="display:none"/>
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td align="left" valign="top">
      <div class="right">
    	<div class="qh_gg_nav">&nbsp;考勤正常工作时间预设</div> 
          <table width="100%" 　border="0" align="center" cellpadding="0"
						cellspacing="0">
                    <tr id="tr00">
                      <td class="td_bg" height="30" align="right"width="10%" >
                      	上午
                      	<input name="workhourmap[0].workName" type="hidden" value="01"/>
                      </td>
                      <td class="td_bg" width="60%" align="left" style="padding-left: 50px;">
                      	<span style="color:red;">*</span>
                      	<input name="workhourmap[0].begintime" id="begintime0" class="begintime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/> -- 
                      	<select style="width:50px" name="workhourmap[0].status" id="status0" class="status"><option value="00">当日</option><option value="01">次日</option></select>
                      	<span style="color:red;">*</span>
                      	<input name="workhourmap[0].endtime" id="endtime0" class="endtime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
                      	<input name="workhourmap[0].sumtime" id="sumtime0" size="5" readonly="readonly" style="text-align:right;"/>小时
                      </td>
                      <td class="td_bg">
                      	&nbsp;
                      </td>
                    </tr>
                     <tr id="tr01">
                      <td class="td_bg" height="30" align="right"width="10%" >
                      	休息
                      	<input name="workhourmap[1].workName" type="hidden" value="02"/>
                      </td>
                      <td class="td_bg" width="40%" align="left" style="padding-left: 50px;">
                      <span style="color:red;">*</span>
                      	<input name="workhourmap[1].begintime" id="begintime1" class="begintime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/> -- 
                      	<select style="width:50px" name="workhourmap[1].status" id="status1" class="status"><option value="00">当日</option><option value="01">次日</option></select>
                      	<span style="color:red;">*</span>
                      	<input name="workhourmap[1].endtime" id="endtime1" class="endtime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
                      	<input name="workhourmap[1].sumtime" id="sumtime1" size="5" readonly="readonly" style="text-align:right;"/>小时
                      </td>
                      <td class="td_bg">
                      	&nbsp;
                      </td>
                    </tr>
                     <tr id="tr02">
                      <td class="td_bg" height="30" align="right"width="10%" >
                      	下午
                      	<input name="workhourmap[2].workName" type="hidden" value="03"/>
                      </td>
                      <td class="td_bg" width="40%" align="left" style="padding-left: 50px;">
                      	<span style="color:red;">*</span>
                      	<input name="workhourmap[2].begintime" id="begintime2" class="begintime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/> -- 
                      	<select style="width:50px" name="workhourmap[2].status" id="status2" class="status"><option value="00">当日</option><option value="01">次日</option></select>
                      	<span style="color:red;">*</span>
                      	<input name="workhourmap[2].endtime" id="endtime2" class="endtime put3" size="8" onclick="WdatePicker({dateFmt:'HH:mm'});"/>
                      	<input name="workhourmap[2].sumtime" id="sumtime2" size="5" readonly="readonly" style="text-align:right;"/>小时
                      </td>
                      <td class="td_bg">
                      	&nbsp;
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
		var url = basePath + "ea/attendworkhour/sajax_ea_getAttendWorkHour.jspa?d="+new Date();
		$.ajax({
	        url : encodeURI(url),
	        type : "get",
	        async : false,
	        success : function cbf(data) { 
	            var member = eval("(" + data + ")");
	            var workhour01 = member.workhour01;
	            var workhour02 = member.workhour02;
	            var workhour03 = member.workhour03;
	            
	            $("#tr00").find("input#begintime0").attr("value",workhour01.begintime);
	            $("#tr01").find("input#begintime1").attr("value",workhour02.begintime);
	            $("#tr02").find("input#begintime2").attr("value",workhour03.begintime);
	            $("#tr00").find("input#endtime0").attr("value",workhour01.endtime);
	            $("#tr01").find("input#endtime1").attr("value",workhour02.endtime);
	            $("#tr02").find("input#endtime2").attr("value",workhour03.endtime);
	            $("#tr00").find("input#sumtime0").attr("value",workhour01.sumtime);
	            $("#tr01").find("input#sumtime1").attr("value",workhour02.sumtime);
	            $("#tr02").find("input#sumtime2").attr("value",workhour03.sumtime);
	            $("#tr00").find("select#status0").find("option[value='"+workhour01.status+"']").attr("selected",true);
	            $("#tr01").find("select#status1").find("option[value='"+workhour02.status+"']").attr("selected",true);
	            $("#tr02").find("select#status2").find("option[value='"+workhour03.status+"']").attr("selected",true);
	           
	            
	        },
	        error : function cbf(data) {
	            alert("获取数据失败!");
	        }
	    });
	    
	    
	    $(".begintime").focus(function(){
	    	var num = this.id.substring(9);
	    	$("#endtime"+num).attr("value","");	
	    	$("#sumtime"+num).attr("value","0");
	    });
	    
	     $(".endtime").blur(function() {
	     	var num = this.id.substring(7);
			var sel = $("#status"+num).val();
			var starto = $("#begintime"+num).val()
	     	var start = "";
	     	var endo =  $("#endtime"+num).val()
	     	var end = "";
	     	
	     	if (endo != "") {
	     		start = "2014-10-10 " + starto + ":00";
		     	if(sel != "00"){
		     		end =  "2014-10-11 " + endo + ":00";
		     	}else{
					end =  "2014-10-10 " + endo + ":00";
		     	}
	     		if(sel == "00"){
					if ((start.replace(/-/g, '') >= end.replace(/-/g, ''))){
						$("#endtime"+num).attr("value","");
						alert("时间填写不准确！");
						$("#sumtime"+num).attr("value","0");
						return;
					}
					var date1 = new Date(start.replace(/-/g, "/"));
					var date2 = new Date(end.replace(/-/g, "/"));
					var date3 = date2.getTime() - date1.getTime();
					var days = Math.floor(date3 / (1000 * 3600 * 24)); 
					var leave1=date3%(24*3600*1000);
					var hours=Math.floor(leave1/(3600*1000));
					var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
					var minutes=Math.floor(leave2/(60*1000))
					if(days > 0 ){
						hours = days *24 + hours;
					}
					if(minutes >= 30){
						hours = hours+".5";
					}
					$("#sumtime"+num).attr("value",hours);	
	     		}else{
					var date1 = new Date(start.replace(/-/g, "/"));
					var date2 = new Date(end.replace(/-/g, "/"));
					var date3 = date2.getTime() - date1.getTime();
					var days = Math.floor(date3 / (1000 * 3600 * 24)); 
					var leave1=date3%(24*3600*1000);
					var hours=Math.floor(leave1/(3600*1000));
					var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
					var minutes=Math.floor(leave2/(60*1000))
					if(days > 0 ){
						hours = days *24 + hours;
					}
					if(minutes >= 30){
						hours = hours+".5";
					}
					$("#sumtime"+num).attr("value",hours);	
	     		
	     		}
	     	}
	     }).focus(function(){
	     	var num = this.id.substring(7);
	     	if($("#begintime"+num).val()==""){
	     		alert("请填写起止时间！");
	     		$("#begintime"+num).focus();
	    	}
	     });
	     $(".status").change(function(){
	    	var num = this.id.substring(6);
	    	$("#endtime"+num).attr("value","");	
	    	$("#sumtime"+num).attr("value","0");	
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
				 $(".put3").trigger("blur");
		          if($("form .error").length)
		          { 
		            return;
		          }    
				$("#workhourform").attr("target","hidden").attr("action",basePath + "ea/attendworkhour/ea_save.jspa?");
				document.workhourform.submit.click();
				token = "2";
				$("#tosave").attr("disabled","disabled");
		});
	});
function re_load(){
		 document.location.href=basePath+"/page/ea/main/human/adance/attendworkhour.jsp?";
}
</script>
</body>
</html>
