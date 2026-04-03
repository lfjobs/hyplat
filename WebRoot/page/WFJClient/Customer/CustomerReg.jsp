<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />   
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>注册</title>
<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>/js/WFJClient/regValidate.js"  type="text/javascript"></script>
<style type="text/css">
	.tex{color: red;font-size: 12px;}
</style>
</head>
<body>
<iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
<div class="con">
    <div class="customerInfo" id="customerInfo">
    <form name="Loginform" id="submit" action="<%=basePath%>ea/wfj/ea_register.jspa" method="post" >
         <div class="clear"></div>
        <div class="info fl">
           <input id="topdl" name="topdlaa" style="display: none;"/>
                    <div class="optionsNav fl">账号</div>
            <div class="optionsCon fl"><input type="text" name="customer.account" class="yname account"/></div>
            <div class="optionsOper fl"><d class="dis">*</d></div>
        </div>
         <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">密码</div>
            <div class="optionsCon fl"><input type="password" name="customer.password" class="yname  password" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/></div>
            <div class="optionsOper fl"><d  class="dis">*</d></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">确认密码</div>
            <div class="optionsCon fl"><input type="password" value="" name="pass" class="yname password1" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/></div>
        	<div class="optionsOper fl"><d class="dis">*</d></div>
        </div>
            <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">选择上级代理</div>
          
         <div class="optionsCon fl"><select id="dl" name="activity"  class="yname " style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;">
							<option  value="0">选择上级代理</option>
						</select></div>
       
        	
        	<div class="optionsOper fl">*</div>
        </div>
        <div class="info fl">
            <div class="optionsNav fl">姓名</div>
            <div class="optionsCon fl"><input type="text" name="staffInf.staffName" class="yname staffName"/></div>
        	<div class="optionsOper fl"><d class="dis">*</d></div>
        </div>
       <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">身份证</div>
            <div class="optionsCon fl"><input type="text" name="staffInf.staffIdentityCard" class="yname staffIdentityCard"/></div>
        	<div class="optionsOper fl"><d class="dis">*</d></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">手机</div>
            <div class="optionsCon fl"><input type="text" name="staffInf.reference" class="yname cellphone"/></div>
        	<div class="optionsOper fl"><d class="dis">*</d></div>
        </div>
        <div class="clear"></div>
        <div class="info fl" style="height: 70px;margin-top: 0px;">
            <div class="optionsNav fl">
           		    地址
            </div>
            <div class="optionsCon fl" style="height: 70px;">
            	<table id="cataffSearchTable" > 
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
					<input name="staffAddress.addressDetailed" type="hidden" id="address" class="yname address"/>
				</table>	
            </div>
            <div class="optionsOper fl"><d class="dis">*</d></div>
      </div>
      <div class="clear"></div>
      <div class="info fl">
            <div class="optionsNav fl">验证码</div>
            <div class="optionsCon verification fl">
                <input type="text"  class="yname validate" />
                <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>          
            </div>
        	<div class="optionsOper fl"><d class="dis">*</d></div>
      </div>
      <div class="clear"></div>
    </form>
    <div class="customerButton" id="reg">确定</div>
    <span class="error"><a class="tex1" id="aa1"></a></span></br>
    <span class="error" style="color:red;"><a class="tex" id="aa"></a></span>
  </div>
  <div class="jqmWindow" style="top: 20%; width: 90%" id="newdistrict">			
		<table style="margin-top: 5px;">
			<tr align="center">
				<td align="right" style="font-size: 12px;"> 
				地域名字：
				</td>
				<td>
					<input id="districtNames" style="width: 50%;height: 20px; font-size: 12px;"/>
					&nbsp;&nbsp;
					<span style="color: red;font-size: 12px;">*按地域区分组</span>
				</td>
			</tr>
		</table>
		<div align="center" style="margin-top: 2px;">
			<input type="button" class="input-button" id="savedistrict" style="width: 30%;" value="确定" />
			<input type="button" class="input-button JQueryreturn2" style="width: 30%;" value="取消" />
		</div>
	</div>
</body>
<script type="text/javascript">
  var basePath="<%=basePath%>";
  $('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML="注册";
		doc.getElementById("return").onclick=function(){
			window.history.go(-1);
		}
	});
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

<script>
   var where='03';
	$(function()
	{
		  var   pahe=basePath+"/ea/wfjshop/sajax_ea_getgrlist.jspa?sort="+where;
		         $.ajax({
	             type: "GET",
	             url: pahe,
	             dataType: "json",
	             success: function(data){  
	                     		var member = eval("(" + data + ")");
	                     		
	                        for(var i=0;i<=member.list.length;i++)
	                       {
	                      	 $("#dl").append("<option value='"+member.list[i].ID+"'>"+member.list[i].name+"</option>");
	                       }
	                      }
	         });
	
	})
</script>

</html>