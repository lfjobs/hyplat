<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
    <title><s:iterator value="pageForm.list">${theme}</s:iterator></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<style>
html{overflow-x:hidden;}
body{background-color:#eeeeee;font-family:Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;font-size:12px;color:#000000;margin:15px 12px;min-width:296px;-webkit-text-size-adjust:none;}
img{vertical-align:top;border:0;}
div,h1{word-break:break-all;}
div,h1,p,ul,li,label,textarea,input,button,form{margin:0;padding:0;-webkit-tap-highlight-color:rgba(0, 0, 0, 0);}
input,textarea{border:0;}
input{width:100%;height:30px;}
ul{list-style:none;}
.left{float:left;}.right{float:right;}
.clear{clear:both;height:0;font-size:0;line-height:0;overflow:hidden;}
body{background-color: #eeeeee;font-family: Helvetica, STHeiti STXihei, Microsoft JhengHei, Microsoft YaHei, Tohoma, Arial;
	font-size: 12px;color: #000000;margin: 15px 12px;min-width: 296px;-webkit-text-size-adjust: none;}
#topbar{display: none;position: fixed;left: 0;top: 0;width: 100%;height: 46px;background-color: #2fb0c8;border-bottom: 2px solid #2da9bf;z-index: 500;}
.topbar_title{font-size: 20px;color: #ffffff;line-height: 20px;text-align: center;text-overflow: ellipsis;
	-o-text-overflow: ellipsis;white-space: nowrap;overflow: hidden;margin: 0 auto;width: 200px;padding: 13px 0;	}
#topbar_back {display: none;position: absolute;left: 0;top: 0;width: 55px;height: 33px;overflow: hidden;
	padding-top: 13px;background-color: #3fbbd1;text-align: center;}
#topbar_menu {position: absolute;right: 0;top: 0;width: 65px;height: 35px;overflow: hidden;padding-top: 11px;background-color: #2ea9be;
	text-align: center;font-size: 16px;color: #ffffff;line-height: 25px;}
#topbar_back img {width: 14px;height: 21px;}
.form_input .input {
	font-size: 16px;color: #000000;line-height: 20px;padding: 10px 0;margin: -10px 0;}
.dt_title {font-weight: normal;font-size: 20px;line-height: 40px;height:40px;color: #000000;overflow: hidden;}
.bm_tx_kuang{width:90%; border:1px solid #CCC; margin:auto; background:#FFF; padding-top:20px; padding-bottom:20px; border-radius:4px;}
.bm_tx_zd{width:20%; float:left; text-align:right; font-size:14px; color:#666; height:30px; padding-top:5px;}
.bm_tx_bd{width:75%; float:left; text-align:left; height:37px;}
.dt_tip {font-size: 14px;color: #666666;margin-top:10px;}
#dt_content {margin-top: 15px;overflow: hidden;border: 1px solid #cccccc;background-color: #ffffff;padding: 10px;font-size: 16px;line-height: 28px;color: #000000;
}
#dtjoinbar {position: fixed;left: 0;bottom: 0;margin-bottom: -100px;width: 100%;overflow: hidden;background-color: #ffffff;border-top: 1px solid #cccccc;
	transition: margin-bottom 0.3s linear 0s;-webkit-transition: margin-bottom 0.3s linear 0s;-moz-transition: margin-bottom 0.3s linear 0s;-o-transition: margin-bottom 0.3s linear 0s;z-index: 900;}
#dtjoinbar div {margin: 15px;}
.button_1 {font-size: 18px;color: #ffffff;line-height: 20px;text-align: center;border: 1px solid #666666;background-color: #ff6600;padding: 10px 0;width: 100%;}
.dt_join_tip {font-size: 14px;color: #999999;text-align: right;margin-top:20px;}
#dt_join_form {display: none;margin: 35px -6px 0 -6px;overflow: hidden;padding: 8px 6px;border: 1px solid #cccccc;background-color: #ffffcc;
	transition: height 0.3s linear 0s;-webkit-transition: height 0.3s linear 0s;-moz-transition: height 0.3s linear 0s;-o-transition: height 0.3s linear 0s;}
.form_input {border: 1px solid #cccccc;background-color: #ffffff;padding: 10px 3px;margin-bottom: 8px;}
.form_input input, .form_textarea textarea {width: 100%;}
.form_textarea textarea{width: 100%;font-size: 16px;resize:none;}
#dt_join_form .textarea {height: 50px;}
.button_1 {font-size: 18px;color: #ffffff;line-height: 20px;text-align: center;border: 1px solid #666666;background-color: #ff6600;padding: 10px 0;width: 100%;}
.button_2 {font-size: 18px;color: #ffffff;line-height: 20px;text-align: center;border: 1px solid #666666;background-color: #ff6600;padding: 10px 0;width: 50%;}
#dt_button_join {margin-top: 15px;}
.jqmWindow {
    display: none;
    font-size: 12px;
    position: absolute;
    background:#DAE7F6 repeat top;
    color: #333;
    border: 1px solid #99bbe8;
}
.input-button
{
	line-height:18px;
	height:21px;
	margin:8px;
	border:1px solid #336699;
	background : url(images/headbg.gif) repeat-x 0px -2px;
}
</style>
</head>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" >
var winHeight;
var winWidth;                      
if (window.innerWidth)
    winWidth = window.innerWidth;
else if ((document.body) && (document.body.clientWidth))
    winWidth = document.body.clientWidth;
// 获取窗口高度
if (window.innerHeight)
    winHeight = window.innerHeight;
else if ((document.body) && (document.body.clientHeight))
    winHeight = document.body.clientHeight;
    // 通过深入 Document 内部对 body 进行检测，获取窗口大小
if (document.documentElement && document.documentElement.clientHeight  && document.documentElement.clientWidth){
    winHeight = document.documentElement.clientHeight;
    winWidth = document.documentElement.clientWidth;
}                   
	function init(){
    // 先获取想要改变的所有图片对象（集合）
    var obj=document.getElementsByTagName("img");
    for(var i=0;i<obj.length;i++){
        var width = obj[i].width;
        // 判断图片宽度是否大于屏幕宽度
       if(width > winWidth){
           obj[i].width = winWidth-38;         
       }
    }
   // var content=document.getElementById("dt_content").innerHTML;
	//var content2=content.replace(/..\/..\/..\//g,"<%=basePath%>");
	//document.getElementById("dt_content").innerHTML="";
	//document.getElementById("dt_content").innerHTML=content2;	
}
</script>

	<body style="margin-top: 63px;"  onload="init();" >
	
			<div id="topbar" style="display: block;">
				<div id="topbar_title" class="topbar_title">
					<s:if test="dtActivity.inforType=='00'">活动详情</s:if>
					<s:if test="dtActivity.inforType=='01'">信息详情</s:if>
				</div>
			</div>	
	
			<h1 id="dt_title" class="dt_title">
				${dtActivity.theme }
			</h1>
			<div id="dt_subinfo" class="dt_tip">
				发布日期：<fmt:formatDate value="${dtActivity.publishTime}"  pattern="yyyy-MM-dd HH:mm" /> 				
			</div>
			
			<jsp:include page="../../../upload_files/activity/${dtActivity.content}"  flush="true" />
			
			<!-- 
			<div id="dt_join_tip" class="dt_join_tip">
				<s:if test="dtActivity.inforType=='00'">
			    	<div style="float: left;">报名费：${beans[0].price}元&nbsp;&nbsp;&nbsp;&nbsp;</div> 
				</s:if>
				截止日期:&nbsp;<fmt:formatDate value="${dtActivity.endtime}"  pattern="yyyy-MM-dd HH:mm" /> &nbsp;
			</div> -->
			<s:if test="dtActivity.inforType=='00'">
			<div id="dt_join_tip1" class="dt_join_tip" style="float: left;  color: -webkit-text;font-size: 16px;">
				已报名
			</div>
			</s:if>
			<div id="dt_join_tipen" class="dt_join_tip" style="display: none;">
				<table style="width: 100%;">
				<thead>
					<tr>
						<th style="width: 48%;text-align: center;">姓        名</th>
						<th style="width: 48%;text-align: center;">电       话</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${bean}">
						<tr id="${arr[0] }">
							<td style="width: 48%;text-align: center;font-size: 14px;">
								<span>${arr[0]}</span>
							</td>
							<td style="width: 48%;text-align: center;font-size: 14px;">
								<span name="phone">${arr[1]}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</div>
		
		<!-- 报名 -->		
		<div id="dt_join_form">
			<form action="ea/activity/ea_activityEnroll.jspa?weixinCompanyId=${weixinCompanyId}&inforType=00" method="post" id="submit">
					<input type="hidden" name="price" value="${beans[0].price}"/>
					<div class="bm_tx_zd">
						活动时间：
					</div>
					<div class="bm_tx_bd">
					<s:select list="%{#request.addressBean}" name="acAddress.actDate" headerKey="--请选择--" headerValue="--请选择--" cssStyle="width:100%;" onchange="getTime(this)" id="acttate"></s:select>
					</div>
					<div class="bm_tx_zd">
						报名时间：
					</div>
					<div class="bm_tx_bd">
						<input name="" id="ensDate" type="text" class="bm_tx_bd_bd" readonly="readonly" style="width: 48%"/>至
						<input name="" id="eneDate" type="text" class="bm_tx_bd_bd" readonly="readonly" style="width: 48%"/>
					</div>
					
					<div class="bm_tx_zd">
						姓&nbsp;&nbsp;&nbsp;&nbsp;名：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.staffName" id="name" type="text" class="bm_tx_bd_bd"/>
					</div>
					<div class="bm_tx_zd">
						电&nbsp;&nbsp;&nbsp;&nbsp;话：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.reference" id="reference" type="text" class="bm_tx_bd_bd" maxlength="11" class="input"/>
					</div>
					<%--<div class="bm_tx_zd">
						身&nbsp;份&nbsp;证：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.staffIdentityCard" id="IdentityCard" type="text" class="bm_tx_bd_bd"/>
					</div>
					<div class="bm_tx_zd">
						地&nbsp;&nbsp;&nbsp;&nbsp;址：
					</div>
					<div class="bm_tx_bd" style="height:53px;">
					<table id="cataffSearchTable" width="100%;"> 
						<tr> 
							<td class="JQueryaddress" >
								<select name="addressProvince" id="province" number='0'
									style="width: 30%;">
								</select>
								<select name="addressCity" id="city" number='1'
									style="width: 30%;">
								</select>
								<select name="addressCounty" id="county" number='2'
									style="width: 30%;">
								</select>
								<select name="addressTown" id="addressTown" number='3'
									style="width: 30%;">
								</select>
								<select name="addressVillage" id="addressVillage"
									number='4' style="width: 30%;">
								</select>
								<select name="addressCommunity" id="addressCommunity"
									number='5' style="width: 30%;">
								</select>
							</td>
						</tr>
						<input name="address.addressDetailed" type="hidden" id="address"/>
					</table>				
					</div>
					--%>
					<div class="bm_tx_zd">
						服务店&nbsp;：
					</div>
					<div class="bm_tx_bd">
				           	<input id="orgname" name="org.organizationName" type="text" class="bm_tx_bd_bd" style="width: 80%;float: left" placeholder="请输入微分金编号或名称"/>
				           	<input id="orgnameId" name="org.organizationID"  type="hidden" />
				           	<input type="hidden" id="companyId" value="${dtActivity.weixinCompanyId}"/>
						<a href="javascript:;" id="shopSubmit" onclick="getValueForParm()">
                    		<img id="search" src="<%=basePath %>/images/WFJClient/ss.png" style="width:25px;height:30px;float:left;"/>
                		</a>
					</div>
					<div class="bm_tx_zd">
						活动地址：
					</div>
					<div class="bm_tx_bd" >
						<select id="actAdr" name="acAddress.addressId" style="width:100%">
						</select>
					</div>
					<input name="dtActivity.id" type="hidden" id="activityId" value="${dtActivity.id}">
					<input name="dtActivity.activityType" type="hidden" id="activityType" value="${dtActivity.activityType}">
					<input name="dtActivity.ppid" type="hidden"  value="${dtActivity.ppid}">
					<div style="clear: both;"></div>
					<div class="form_button" style="height: 40px;">
						<input type="button" class="button_1" id="but" onClick="enroll()" value="报名" style="height:40px;" />
					</div>
			</form>
		</div>
		<div id="dtjoinbar" style="display: block; margin-bottom: 0px;">
			<div>
				<button class="button_2" ontouchstart="" onClick="show()" style="float: left;" id="baoming">
					我要报名
				</button>
				<%--<button class="button_2" ontouchstart="" onClick="window.location.href='<%=basePath>page/mobile/activity/activity_publish.jsp?weixinCompanyId=${weixinCompanyId}'">
					我要发布活动
				</button> --%>
				<button class="button_2" ontouchstart="" id="fabu" onClick="window.location.href='<%=basePath%>ea/activity/ea_publishLogin.jspa?weixinCompanyId=${weixinCompanyId}&inforType=${dtActivity.inforType}'">
					<s:if test="dtActivity.inforType=='00'">我要发布活动</s:if>
					<s:if test="dtActivity.inforType=='02'">我要发布活动</s:if>
					<s:if test="dtActivity.inforType=='01'">发布公共信息</s:if>
				</button>
			</div>
		</div>
		<div class="jqmWindow" style="width: 96%; right: 2%;"
			id="newdistrict">			
			<table style="margin-top: 5px;">
				<tr align="center">
					<td align="right" style="font-size: 12px;"> 
						&nbsp;&nbsp;地域名字：
					</td>
					<td>
						<input id="districtNames" style="width: 50%;height: 25px; font-size: 12px;"/>
						&nbsp;
						<span style="color: red;font-size: 12px;">*按地域区分组</span>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedistrict" style="width: 30%;" value="确定" />
				<input type="button" class="input-button JQueryreturn2" style="width: 30%;" value="取消" />
			</div>
		</div>	
		<%-- 选择微分金店 --%>
		<div id="jqmWindow2" class="jqmWindow"position:relative; bottom:800px; right:2px;
			style=" display: none; width: 98%; left: 1%;background: #eff;position:relative; bottom:450px; right:2px;">
			<div align="center">
				<iframe name="ifr" id="ifr" width="100%" height="50%" frameborder="0"></iframe>
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="width: 20%;" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="width: 20%" />
			</div>
		 </div>
	</body>
	<script type="text/javascript">
	//根据选的时间获取报名开始截止时间和活动地址
	function getTime(e){
		var actDate=e.value;
		var activityId=$("#activityId").val();
		var url = basePath
		+ "/ea/activity/sajax_getAddressByTime.jspa?acAddress.actDate="+actDate+"&acAddress.activityId="+activityId+"&date1="+ new Date();
		$("#actAdr").text("");
		$("#ensDate").val("");
		$("#eneDate").val("");
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var addresss=member.adr;
				$("#ensDate").val(addresss[0].ensDate);
				$("#eneDate").val(addresss[0].eneDate);
				for(var i=0;i<addresss.length;i++){
					$("#actAdr").append("<option value="+addresss[i].addressId+">"+addresss[i].companyAddr+"</option>")
				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		return;
		}
	$(document).ready(function() {
		
		var phones=document.getElementsByName("phone");
		for(var i=0;i<phones.length;i++){
			var phone=phones[i].innerHTML;
			phones[i].innerHTML=phone.substring(0, 3) + "*****" + phone.substring(8, 11);
			//alert(a);
		}
		
		var inforType=${dtActivity.inforType};
		if(inforType=="01"){
			document.getElementById("baoming").style.display="none";
			document.getElementById("fabu").style.width="100%";
		}

	});
	  //控制报名列表的显示隐藏
	  document.getElementById("dt_join_tip1").onclick=function(){
	  	 	if(!this.isok){
	  	 		this.isok=true;
	 	    	document.getElementById("dt_join_tipen").style.display="block";
	  	 	}else{
	  	 		this.isok=false;
	  	 		document.getElementById("dt_join_tipen").style.display="none";
	  	 	}
	 	}
		//---
		 function show(){
		    $("#dtjoinbar").remove();
			document.getElementById("dt_join_form").style.display="block";
			location.hash="dt_join_form";
		 }
		 function enroll(){
			 var name= $("#name").val().trim();
		     var reference=$("#reference").val().trim();
		     //var IdentityCard=$("#IdentityCard").val();
		     var orgnameId=$("#orgnameId").val();
		     var activityType=$("#activityType").val();
			 var acttate=$("#acttate").val();
			 if(acttate=="--请选择--"){
				 alert("请选择活动时间！");
				 return;
				 }
			 if(name=="" || reference=="" || reference.length !=11 ){
					alert("信息有误或不完整！");
					return;
		           }
	        if(orgnameId==""){
					alert("请选择服务店！");
					return;
		        }
			
			$("#submit").submit();
			$("#but").attr("disabled", true);
	    }
		   //--------------获取微分金店--------------
			 var ids = ''; //存放行ID
			 var comid=$("#companyId").val();
			 var orgname=$("#orgname").val();
			 function getValueForParm(){ //打开页面
			 	//ids = id;
			   	$("#ifr").attr("src",basePath+"/ea/wfjshop/ea_getWFJshops.jspa?search=searchShops&companyId="+comid+"&corganization.organizationName="+orgname+"&activity=activity");
			   	$("#jqmWindow2").show();
			 }

			$("#isBack").click(function(){// 返回
		       $("#jqmWindow2").hide();
		    }); 
		   
			$("#isSubmit").click(function(){// 选择确定
				var value1 = window.frames["ifr"].personvalue;
				var value2 = window.frames["ifr"].$('tr#'+value1).find("li#name").text();
				$("#orgname").val(value2);
				$("#orgnameId").val(value1);
				if(value1 == ""){
					alert("请选择一个店铺！")
					return;
				}
				$("#ifr").attr("src","");
		        $("#jqmWindow2").hide();
		    });
			 //--------------获取微分金店--------------	
	</script>
	<script type="text/javascript">
	var basePath='<%=basePath%>'; 	
	$(document).ready(function() {
		
		
		$("input.JQueryreturn").click(function() {// 取消
			$(".jqmWindow").hide();
		});
	$(".close").click(function() {// 取消
				$(".jqmWindow").hide();
			});
	$("input.JQueryreturn2").click(function() {// 城市添加取消
		$(".jqmWindow").hide();
	});
	
	$(".jqmWindow").hide();
			
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
function LiuZhongYaoDeShaGuaDiZhi(address) {
	// 非空验证End
	$td = $("td.JQueryaddress");
	$td.children('select').empty();
	$select = "<option selected='selected'>--请选择--</option>";
	$("#province", $td).append($select);
	$td = $("td.JQueryaddress");
	$('td.JQueryaddress input[name=changes]').show();
	var DistrictID = address;
	if (DistrictID == "") {
		var url = basePath
				+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&showType=weifenjin&districtPID="
				+ "&date1=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				
				var distinctlist = member.distinctlist;
				for (var i = 0; i < distinctlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", distinctlist[i].districtID)
							.attr("id", distinctlist[i].districtID)
							.text(distinctlist[i].districtName);
					$("#province", $td).append($op);
				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		return;
	}	
}

$('td.JQueryaddress select[number]').change(function() {

	var province = this.id;
	var number = $(this).attr("number");
	$td = $("td.JQueryaddress");
	rovince = "#" + province;
	$('#newdistrict', $td).hide();
	$td.children('select:gt(' + number + ')').empty();
	$td.children('select:gt(' + number + ')').show();
	var D = $(rovince, $td).children("option:selected").attr("class");
	if (D == 'add') {
		PID = $(rovince, $td).children("option:selected").val();
		$('#districtNames').attr("title", number).attr("value", "");
		$("#newdistrict").show();
		return;
	}
	$($td).children('select:gt(' + number + ')').attr("disabled", false);
	districtPID = $(rovince, $td).children("option:selected").val();
	if (districtPID == '--请选择--') {
		if (number != "0") {
			var nu = parseInt(number) - 1;
			districtPID = $("select[number=" + nu + "]", $td).val();
		} else {
			districtPID = "";
		}
		$td.find('input#address').val(districtPID);
		return;
	}
	var url = basePath
			+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID="
			+ encodeURI(districtPID) + "&date3=" + new Date();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var distinctlist = member.distinctlist;
			$select = "<option selected='selected'>--请选择--</option>";
			$(rovince, $td).next().append($select);
			if (distinctlist.length) {
				for (var i = 0; i < distinctlist.length; i++) {
					$op = $("<option/>");
					$op.attr("value", distinctlist[i].districtID).attr(
							"id", distinctlist[i].districtID)
							.text(distinctlist[i].districtName);
					$(rovince, $td).next().append($op);
				}
			}
			$add = "<option class='add'  value = '" + districtPID
					+ "' >--新增--</option>";
			$(rovince, $td).next().append($add);
			$td.find('input#address').val(districtPID);
		},
		error : function cbf(data) {
			alert("数据获取失败！");

		}
	});
});
$('input#savedistrict').click(function() {

	$td = $("td.JQueryaddress");
	number = $('input#districtNames').attr('title');
	districtName = $.trim($('input#districtNames').val());
	$td.children('select:gt(' + number + ')').empty();
	if ('' == districtName) {
		alert("请填写地域名称");
		return;
	}
	var lastname = districtName.substring(districtName.length - 1,
			districtName.length);
	if (number == 1) {
		var tfname = true;
		var arrname = ["市", "县", "区", "盟"];
		$.each(arrname, function(key, value) {
					if (lastname == value) {
						tfname = false;
					}
				});
		if (tfname) {
			alert("输入错误!请填写以市、县、区、盟为结尾的地址名称");
			$('input#districtNames').val("");
			return;
		}
	}
	if (number == 2) {
		var tfname = true;
		var arrname = ["区", "县", "乡", "镇", "港", "巷", "旗", "路", "环", "街",
				"湾", "号"];
		$.each(arrname, function(key, value) {
					if (lastname == value) {
						tfname = false;
					}
				});
		if (tfname) {
			alert("输入错误!请填写以区, 县,乡,镇,港,巷,旗,路,环,街为结尾的地址名称");
			$('input#districtNames').val("");
			return;
		}

	}
	$("#newdistrict").hide();
	var urldistrict = basePath
			+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?type=wechat&district.districtPID="
			+ encodeURI(PID) + "&district.districtName="
			+ encodeURI(districtName) + "&date4=" + new Date();
	$.ajax({
		url : urldistrict,
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");		
			var sdistrict = member.sdistrict;
			$op1 = $("<option selected='selected'/>").attr("value",
					sdistrict.districtID).attr("id", sdistrict.districtID)
					.text(sdistrict.districtName);
			$("#" + sdistrict.districtID, $td).remove();
			$(rovince, $td).append($op1);
			districtPID = sdistrict.districtID;
	
			$select = "<option selected='selected'>--请选择--</option>";
			$(rovince, $td).next().append($select);
			$add = "<option class='add'  value = '" + districtPID
					+ "' >--新增--</option>";
			$(rovince, $td).next().append($add);
			$td.find('input#address').val(districtPID);
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
 });
	LiuZhongYaoDeShaGuaDiZhi("");
});
	</script>
</html>
