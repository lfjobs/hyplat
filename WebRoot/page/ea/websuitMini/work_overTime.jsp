<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page  import="hy.ea.bo.CAccount" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
%>
<%
CAccount M = (CAccount)session.getAttribute("account"); //从session里把a拿出来，并赋值给M

%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>5L5C微办公加班单</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"  content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
		<link rel="stylesheet" href="<%=basePath%>css/reveal.css">
			<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>	
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/websuitMini/pub.css" />
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.reveal.js"></script>
		<style>
#topbar_back img{ width:14px;}
body{ background-color:#b2ebff;}
.work_con{ width:360px; margin:auto;}
.work_nav{ width:100%; height:40px; line-height:40px; margin-top:4px; -webkit-border-radius:5px; -moz-border-radius:5px; background-color:#f0f0f0; }
.work_textarea{ width:100%; height:60px; line-height:60px; margin-top:4px; -webkit-border-radius:5px; -moz-border-radius:5px; background-color:#f0f0f0; }
.work_l{ width:100px; height:40px; line-height:40px; text-align:right; font-size:14px;}
.work_r{ width:250px; height:40px; line-height:40px; font-size:14px; color:#4297b6; margin-left:10px;}
.work_r input{background-color:transparent; border:none;font-size: 14px;color: #4297b6;line-height: 20px; float:left;padding: 10px 0;
margin: -10px 0; margin-top:0px; width:220px; font-weight:bold;outline: none; float:left;}
.work_r img{ float:left; width:16px; height:10px; margin-top:15px;}
.sub_div{margin-top:10px;}
.sub{ width:100%; height:40px;}
.sub img{ width:100%; height:40px;}
.inputtextarea{
width:100%;
height:56px;
border: 0px white;
color:#4297b6;
background-color:#F0F0F0;
font-size:14px;
font-weight:bold;
}
#overTimeSorts{
height: 40px;
border-color: white;
width: 100%;
color:#4297b6;
font-size:14px;
font-weight:bold;
background-color: #F0F0F0;
}
#askleavetype span{
height:25px;
width:100%;
font-size:14px;
font-weight:bold;
line-height:25px;
color:#4297b6;
display:inline-block;
cursor: pointer;
}
::-webkit-input-placeholder { /* WebKit browsers */
    color:    #4297b6;
}
:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    color:    #4297b6;
}
::-moz-placeholder { /* Mozilla Firefox 19+ */
    color:    #4297b6;
}
:-ms-input-placeholder { /* Internet Explorer 10+ */
    color:    #4297b6;
}

</style>
 <script type="text/javascript">
  var basePath='<%=basePath%>';
 var token = 0;
$(document).ready(function(){

var name = '<%=M.getStaffName()%>';
var companyname = '<%=M.getCompanyName()%>';
$("#user_name").val(name);
$("#companyName").val(companyname);
var myDate = new Date();
var current =  myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-"+myDate.getDate();
$("#applyDate").val(current);
	$(".JQuerySubmit").click(function() { // 提交审核
				if ($("#overTimeSorts").val()=='请选择加班类别') {
					alert("请选择加班类别！");
					return;
				}
				
				if ($("#overTimeReason").val() == '') {
					alert("加班事由不能为空！");
					return;
				}
				if ($("#overTimeContent").val() == '') {
					alert("加班内容不能为空！");
					return;
				}
				if ($("#overTimeStartDate").val() == ''
						|| $("#overTimeEndDate").val() == '') {
					alert("加班时间不能为空！");
					return;
				}
				 getID();
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/publicreceipts/ea_saveOverPublicreceipts.jspa");
				document.cstaffForm.submit.click();
				<!-- document.cstaffForm.reset(); -->
				
				token = 1;
			});
})
 function getID(){
		    	var url="<%=basePath%>ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
				$.ajax({
		                url: url,
		                type: "get",
		                async: false,
		                dataType: "json",
		                success: function cbf(data){
					    var member = eval("(" + data + ")");
					    var nologin = member.nologin;
						if(nologin){
							document.location.href ="<%=basePath%>page/ea/not_login.jsp";
						}
				        vouch = member.BillID;
				        $("#voucherNum1").val(vouch);
			    },
		              error: function cbf(data){
						         alert("数据获取失败!")
						 }
				});
			}
			 function re_load() {
	    if(token)
		document.location.href = basePath
				+ "page/ea/websuitMini/index.jsp";
}
</script> 

</head>

<body style="margin-top: 63px;">

	<div id="topbar" style="display: block;">
   <div id="topbar_title" class="topbar_title">加班单</div>
   <div id="topbar_back" ontouchstart="" style="display: block;" onclick="history.back()"><img src="<%=basePath%>images/websuitMini/button_back.png"/></div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""></div>
</div>
<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
<input type="submit" name="submit" style="display: none">
<div class="work_con">
   <div class="work_nav fl">
      <div class="work_l fl">
      姓名：
      </div>
      <div class="work_r fl">
          <input type="text" id="user_name" class="input"  readonly="readonly" nsme="publicreceipts.principal" placeholder="默认为当前登录人" maxlength="20">
      </div>
   </div>
   <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      加班类别：
      </div>
      <a href="#"  data-reveal-id="myModal"><div class="work_r fl"  id="chooseleavetype"  data-reveal-id="myModal" data-animation="fade">
          <select id="overTimeSorts" class="input" placeholder="请选择"  name="publicreceiptsChild.overTimeSort" maxlength="20" readonly="readonly">
           <option id="1">请选择加班类别</option>
  <option id="2">工作日加班</option>
  <option id="3">休息日加班</option>
  <option id="4">法定假日加班</option>
</select>
          <img alt="" title="" src="<%=basePath%>images/websuitMini/jt.png" />
 
      </div></a>
	 
   </div>
 <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      事由：
      </div>
      <div class="work_r fl">
     
          <input type="text" id="overTimeReason" class="input" name="publicreceiptsChild.overTimeReason"  maxlength="20">
      </div>
   </div>
   <div class="clear"></div>
    <div class="work_textarea fl">
      <div class="work_l fl">
     加班内容：
      </div>
      <div class="work_r fl">
     
          <textarea id="overTimeContent" class="inputtextarea" name="publicreceiptsChild.overTimeContent"  ></textarea>
      </div>
   </div>
   <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      开始时间：
      </div>
      <div class="work_r fl">
          <input type="text" id="overTimeStartDate" class="input" name="publicreceiptsChild.overTimeStartDate" readonly="readonly"  onfocus="daytime(this)" maxlength="20">
          <img alt="" title="" src="<%=basePath%>images/websuitMini/jt.png" />
      </div>
   </div>
   <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      截止时间：
      </div>
      <div class="work_r fl">
          <input type="text" id="overTimeEndDate" name="publicreceiptsChild.overTimeEndDate" class="input" readonly="readonly" onfocus="daytime(this)"  maxlength="20">
          <img alt="" title="" src="<%=basePath%>images/websuitMini/jt.png" />
      </div> 
   </div>
   <div class="clear"></div>
   <div class="work_nav fl sub_div">
   <a class="sub" href="javascript:void(0);"><img alt="" class="JQuerySubmit" title="" src="<%=basePath%>images/websuitMini/tj_button.png" /></a>
   </div>
   <div class="clear"></div>
</div>
<input name="publicreceipts.principalID" id="principalID" type="hidden" />
 <input name="company.companyName" class="input yincang" id="companyName"  type="hidden" readonly="readonly"  size="30"/>
<input readonly="readonly" name="publicreceipts.voucherNum"  type="hidden" id="voucherNum1"  size="25"/>
 <input id="applyDate"  name="publicreceipts.applyDate"  type="hidden" />
</form>

 <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
