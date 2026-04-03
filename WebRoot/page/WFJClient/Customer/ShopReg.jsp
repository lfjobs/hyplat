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
<script src="<%=basePath%>/js/WFJClient/shopRegValidate.js"  type="text/javascript"></script>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var chejiahao;
	var goodsID;
	var token = 13;
	$(document).ready(function(){
	var rs=$("#zhuan").val();
		if(rs=="1"){
			alert("请登录");
			document.location.href = basePath + "/page/WFJClient/Login.jsp?loginPage=login";
		}else if(rs=="2"){
			alert("注册成功");
			document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
		}
	});
</script>
<style type="text/css">
	.tex{color: red;font-size: 12px;}
</style>
</head>
<body>
<iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/cusLabel.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
<div class="con">
    <div class="customerInfo" id="customerInfo">
    <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"></div>
            <div class="optionsCon fl"><span style="font-size:18px;">注册店铺信息</span></div>
        </div>
    <form name="dianpu" id="dianpu"  method="post" enctype="multipart/form-data">
        <input type="hidden" id="zhuan" value="${zhuan }"/>
        <s:token></s:token>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>用户名：</div>
            <div class="optionsCon fl"><input type="text" name="customer.account" class="yname account" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>密码：</div>
            <div class="optionsCon fl"><input type="password" name="customer.password" class="yname  password" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>确认密码：</div>
            <div class="optionsCon fl"><input type="password" value="" name="pass" class="yname password1" style=" border: 1px solid #ccc;height: 24px; padding: 2px;width: 90%;"/></div>
        </div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>店铺名称：</div>
            <div class="optionsCon fl"><input type="text" name="organization.organizationName" class="yname organizationName"/></div>
        </div>
       <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">行业类别：</div>
            <div class="optionsCon fl">
            	<select name="shopinfo.tradeid" id="tradeid" onchange="con()" style="height:30px;">
            		<option value="wu" >请选择 </option>
            		<s:iterator value="typeList">
						<option value="<s:property value="codeID"/>" >
							<s:property value="codeValue"/>
						</option>
					</s:iterator>
            	</select>
        	</div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"></div>
            <div class="optionsCon fl">
				<select name="organization.companyID" id="companyID" style="height:30px;">
            		<option value="wu" >
						请选择
					</option>
            	</select>
			</div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl">支付账号：</div>
            <div class="optionsCon fl"><input type="text" name="shopinfo.payaccount" class="yname payaccount"/></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>负责人：</div>
            <div class="optionsCon fl"><input type="text" name="shopinfo.owner" class="yname owner"/></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>电话：</div>
            <div class="optionsCon fl"><input type="text" name="shopinfo.telephone" class="yname telephone"/></div>
        </div>
        <div class="clear"></div>
        <div class="info fl">
            <div class="optionsNav fl"><div class="optionsOper fl"><d class="dis">*</d></div>QQ：</div>
            <div class="optionsCon fl"><input type="text" name="shopinfo.qq" class="yname qq"/></div>
        </div>
      <div class="clear"></div>
      <div class="info fl">
            <div class="optionsNav fl">验证码</div>
            <div class="optionsCon verification fl">
                <input type="text"  class="yname validate" />
                <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>          
            </div>
        	
      </div>
      <div class="clear"></div>
    </form>
    <div class="customerButton" id="reg">提交</div>
    <span class="error"><a class="tex1" id="aa1"></a></span></br>
    <span class="error" style="color:red;"><a class="tex" id="aa"></a></span>
    
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
function con(){
var tradeId = $("#tradeid").val();
var tradeName = $("#tradeid").find("option:selected").text()
var url = basePath + "/ea/wfj/sajax_ea_companyList.jspa?tradename="+tradeName+"&tradeId="+tradeId;
$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var companyList = member.companyList;
				var start = "<option ";
				var fu = ">";
				var end = "</option >";
				
				$("#companyID").find("option").remove();
				if(companyList.length==0){
					start+='value="wu" ';
					$("#companyID").append(start+fu+"暂无此类公司"+end);
				}else{
					for (var i = 0; i < companyList.length; i++) {
						$("#companyID").append(start+'value="'+companyList[i].companyID+'"'+fu+ companyList[i].companyName +end);
					}
				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
}
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