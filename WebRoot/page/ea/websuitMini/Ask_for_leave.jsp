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
<title>5L5C微办公请假单</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="Access-Control-Allow-Origin" content="*">
		<meta name="viewport"  content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
	<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/websuitMini/pub.css" />
    <script src="<%=basePath%>js/ea/websuitMini/datepicker/jquery-1.11.0.min.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/datepicker/jquery.mobile-1.4.2.min.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/datepicker/mobiscroll.core.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/websuitMini/datepicker/mobiscroll.datetime.js"></script>
    <script src="<%=basePath%>js/ea/websuitMini/datepicker/mobiscroll.scroller.js" type="text/javascript"></script>
   <script src="<%=basePath%>js/ea/websuitMini/datepicker/mobiscroll.i18n.zh.js" type="text/javascript"></script> 
    <script src="<%=basePath%>js/ea/websuitMini/mobiscroll.select.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/websuitMini/datepicker/mobiscroll.scroller.css" rel="stylesheet" type="text/css" />
		<style>
#topbar_back img{ width:14px;}
body{ background-color:#b2ebff;}
.work_con{ width:360px; margin:auto;}
.work_nav{ width:100%; height:40px; line-height:40px; margin-top:4px; -webkit-border-radius:5px; -moz-border-radius:5px; background-color:#f0f0f0; }
.work_l{ width:100px; height:40px; line-height:40px; text-align:right; font-size:14px;}
.work_r{ width:250px; height:40px; line-height:40px; font-size:14px; color:#4297b6; margin-left:10px;}
.work_r input{background-color:transparent; border:none;font-size: 14px;color: #4297b6;line-height: 20px; float:left;padding: 10px 0;
margin: -10px 0; margin-top:0px; width:220px; font-weight:bold;outline: none; float:left;}
.work_r img{ float:left; width:16px; height:10px; margin-top:15px;}
.sub_div{margin-top:10px;}
.sub{ width:100%; height:40px;}
.sub img{ width:100%; height:40px;}
#askleavetype {
position:absolute;width:340px;height:200px;left:50%;top:50%;margin-left:-173px;margin-top:-100px;border:solid 3px #ccc;z-index:99;background-color: white;
}
#ask_type{
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
  .demo {
            display: none;
        }
        .dw-i{
        font-size: 26px;
        color:#FFFFFF;
        }
</style>
<script type="text/javascript" >
 
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
				if ($("#user_reason").val() == '') {
					alert("请假原因不能为空！");
					return;
				}
				if ($("#start_time").val() == ''
						|| $("#start_time").val() == '') {
					alert("请假起始日期不能为空！");
					return;
				}
					if ($("#end_time").val() == ''
						|| $("#end_time").val() == '') {
					alert("请假结束日期不能为空！");
					return;
				}
				getID();
				$("#leaveForm").attr("target","hidden").attr( "action", basePath + "ea/publicreceipts/ea_saveLeaveBills.jspa");
				document.leaveForm.submit.click();
				document.leaveForm.reset();
				token=2;
			});
			
})
function getID(){
	    	var url= basePath + "ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
			$.ajax({
	                url: url,
	                type: "get",
	                async: false,
	                dataType: "json",
	                success: function cbf(data){
				    var member = eval("(" + data + ")");
				    var nologin = member.nologin;
					if(nologin){
						document.location.href = basePath + "page/ea/not_login.jsp";
					}
			        var vouch = member.BillID;
			        $("#voucherNum1").val(vouch);
		    },
	              error: function cbf(data){
					         alert("数据获取失败！")
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
   <div id="topbar_title" class="topbar_title">请假单</div>
   <div id="topbar_back" ontouchstart="" style="display: block;" onclick="history.back()"><img src="<%=basePath%>images/websuitMini/button_back.png"/></div>
   <div id="topbar_menu" class="topbar_menu" ontouchstart=""></div>
</div>
<form name="leaveForm" id="leaveForm" method="post" enctype="multipart/form-data">
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
      请假类别：
      </div>
      <a href="#"  data-reveal-id="myModal"><div class="work_r fl"  id="chooseleavetype"  data-reveal-id="myModal" data-animation="fade">
          <select id="ask_type" class="input" placeholder="请选择"  name="publicreceiptsChild.leaveType" maxlength="20" readonly="readonly">
<option onclick="gaveval(id)" id="1">事假</option>
  <option onclick="gaveval(id)" id="2">病假</option>
  <option onclick="gaveval(id)" id="3">婚假</option>
  <option onclick="gaveval(id)" id="4">产假</option>
  <option onclick="gaveval(id)" id="5">丧假</option>
  <option onclick="gaveval(id)" id="6">年休假</option>
  <option onclick="gaveval(id)" id="7">探亲假</option>
  <option onclick="gaveval(id)" id="8">其他</option>
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
     
          <input type="text" id="user_reason" class="input" name="publicreceiptsChild.leaveReason" placeholder="直接录入" maxlength="20">
      </div>
   </div>
   <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      开始时间：
      </div>
      <div class="work_r fl">
      	 <!--  <input name="test" id="test" class="demo-test-date demo-test-datetime demo-test-time demo-test-credit" /> -->
          <input type="text" id="start_time" class="demo-test-date demo-test-datetime demo-test-time demo-test-credit" name="publicreceiptsChild.leaveStartDate" readonly="readonly"  placeholder="2014-08-26  18:00"  maxlength="20">
          <img alt="" title="" src="<%=basePath%>images/websuitMini/jt.png" />
      </div>
   </div>
   <div class="clear"></div>
   <div class="work_nav fl">
      <div class="work_l fl">
      截止时间：
      </div>
      <div class="work_r fl">
          <input type="text" id="end_time" name="publicreceiptsChild.leaveEndDate" class="demo-test-date demo-test-datetime demo-test-time demo-test-credit" readonly="readonly" onfocus="daytime(this)" placeholder="2014-08-26  21:00" maxlength="20">
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
 <input name="company.companyName" class="input yincang" id="companyName" readonly="readonly" type="hidden" size="30"/>
<input readonly="readonly" name="publicreceipts.voucherNum" id="voucherNum1" type="hidden" class="yincang" size="25"/>
 <input id="applyDate" type="hidden" name="publicreceipts.applyDate" />
</form>
 <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
 <div data-role="page"> 
        <div data-role="content" style="display:none">
            <div class="settings">
                <div data-role="fieldcontain">
                    <select name="demo" id="demo">
                    <option value="datetime">Datetime</option>
                    <option value="date">Date</option>
                       <option value="time">Time</option>
                        <option value="credit">Credit card expiration</option>
                    </select>
                </div>
            </div>
           
           
         
        </div>
    </div>


 <script type="text/javascript">
        $(function () {
            var curr = new Date().getFullYear();
            var opt = {
                'date': {
                    preset: 'date',
                    invalid: { daysOfWeek: [0, 6], daysOfMonth: ['5/1', '12/24', '12/25'] }
                },
                'datetime': {
                    preset: 'datetime',
                    minDate: new Date(2012, 3, 10, 9, 22),
                    maxDate: new Date(2014, 7, 30, 15, 44),
                    stepMinute: 5
                },
                'time': {
                    preset: 'time'
                },
                'credit': {
                    preset: 'date',
                    dateOrder: 'mmyy',
                    dateFormat: 'mm/yy',
                    startYear: curr,
                    endYear: curr + 10,
                    width: 100
                },
                'select': {
                    preset: 'select'
                },
                'select-opt': {
                    preset: 'select',
                    group: true,
                    width: 50
                }
            }

            $('.settings select').bind('change', function() {
                var demo = $('#demo').val();

                if (!demo.match(/select/i)) {
                    $('.demo-test-' + demo).val('');
                }

                $('.demo-test-' + demo).scroller('destroy').scroller($.extend(opt[demo], {
                    theme: $('#theme').val(),
                    mode: $('#mode').val(),
                    lang: $('#language').val(),
                    display: $('#display').val(),
                    animate: $('#animation').val()
                }));
                $('.demo').hide();
                $('.demo-' + demo).show();
            });

            $('#demo').trigger('change');

        });
    </script>
</body>
</html>
