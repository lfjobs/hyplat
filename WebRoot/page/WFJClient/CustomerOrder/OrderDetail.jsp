<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>产品订单详情</title>
     <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
  <body>
   <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
<div class="con">
    <div class="shippingAddress" id="infor">
    	<form action="<%=basePath %>ea/buyproducts/ea_unregistered.jspa" id="submit" method="post">
    		<ul>
    			<li>收货人&nbsp;：</li>
    			<li><input type="text" id="name" name="staffInf.staffName"/></li>
    		</ul>
    		<ul class="ss">
    			<li>身份证&nbsp;：</li>
    			<li><input type="text" id="identitycard" name="staffInf.staffIdentityCard"/></li>
    		</ul>
        	<ul>
    			<li>联系电话：</li>
    			<li><input type="text" id="reference" name="staffInf.reference"/></li>
    		</ul>
    		<ul class="ss">
    			<li style="width: 20%;">收货地址：</li>
    			<li style="width: 80%;">
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
					</table>	
    			</li>
    		</ul>
    		<ul>
    			<li></li>
    			<li></li>
    		</ul>
    		<ul class="ss">
    			<li>支付方式：</li>
    			<li>
    				<select id="sfirst" name="cashierBills.wfStatus1" onchange="changeway(1)"><option value="00">在线支付</option><option value="01">订单购买</option></select>
					<select id="ssecond" name="cashierBills.wfStatus2" onchange="changeway(2)" style="display:none" disabled="disabled" ><option value="00">在线支付</option><option value="01">现金支付</option></select>
					<select id="sthird" name="cashierBills.wfStatus3" onchange="changeway(3)" style="display:none" disabled="disabled"><option value="00">立即支付</option><option value="01">货到付款</option></select>
    			</li>
    		</ul>
			<input name="staffAddress.addressDetailed" type="hidden" id="address"/>
    		<input type="hidden" name="orgnizationId" value="${orgnizationId }"/>
    		<input type="hidden" name="orgnizationName" value="${orgnizationName }"/>
    		<input type="hidden" name="pid" value="${productList[0][0]}"/>
    		<input type="hidden" name="productPackaging.quantity" id="quantity"/>
    		<input type="hidden" name="productPackaging.money" id="money1"/>
    		<input type="hidden" name="productPackaging.price"  value="${productList[0][6] }"/>
    		<input type="hidden" name="cashierBills.wfStatus1" value="00" id="buyCash"/>
    		<input type="hidden" name="buy" value="${buy }" />
    	</form>
    </div>
    <input type="hidden" name="itemnum" id="itemnum" value="${itemnum }"/>
    <div class="orderDetail fl">
        <div class="orderDetailNav orderDetailCon">
            <div class="orderDetailCon">
                <img alt="" src="<%=basePath %>${productList[0][2] }"/>
                <div class="orderHeader fl">
                    <p class="orderTitle fl">${productList[0][1] }</p>
                    <p class="orderTitle fl" id="pirce">￥${productList[0][6] }</p>
                </div>
            </div>
        </div>
        <div class="orderDetailNav">
          		  购买数量
            <div class="buyCount fr">
                <span id="jian">-</span>
                <d id="num">1</d>
                <span id="jia">+</span>
            </div>
        </div>
        <div class="orderFloor">
            <div class="orderConfirm">
                <div class="confirm fr" id="jiesuan">结算</div>
                <d class="fr" id="money"></d>
                <span class="fr">合计：</span>
            </div>
        </div>
    </div>
    <div class="jqmWindow" style="top: 20%; width: 25%" id="newdistrict">			
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
</div>
</body>
<script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
<script type="text/javascript">
	var buy="<%=request.getAttribute("buy")%>";
	var p=$("#pirce").text();
	var price=p.substring(1,p.length);
	$(document).ready(function() {
		var num=$("#num").text();
		$("#money").text("￥"+price*num);
		$("#money1").val(price*num);
		$("#quantity").val(num);
	});
	var num= parseInt($("#num").text());
	$("#jia").click (function(){
		num=num+1;
		var itemnum=$("#itemnum").val();//库存
		if(num>itemnum){
			alert("库存不足！");
			return;
		}
		$("#num").text(num)
		$("#money").text("￥"+price*num);
		$("#money1").val(price*num);
		$("#quantity").val(num);
	});
	$("#jian").click (function(){
		if(num>1){
			num=num-1;
			$("#num").text(num);
			$("#money").text("￥"+price*num);
			$("#money1").val(price*num);
			$("#quantity").val(num);
		}
	});

	$("#jiesuan").click(function(){
		 var name= $("#name").val();
	     var reference=$("#reference").val();
	     var idc=$("#identitycard").val();
		 var addr = "";
		  $(".JQueryaddress").find("select").find("option[value]:selected").each(function() {
			if ($(this).text() != '--请选择--' && $(this).text() != '--新增--')
				addr = addr + $(this).text();
		  });
	     $("#address").val(addr);
	     var reg = /^1\d{10}$/;
		 if(buy=="toFriend"){
			 if(name=="" || reference=="" || !reg.test(reference)){
					alert("信息有误或不完整！111");
					return;
			    }
		}else{
			 if(name=="" || reference=="" || addr=="" || !reg.test(reference)||idc.length==0){
					alert("信息有误或不完整！");
					return;
			 }
		}
		$("#submit").submit();
	});
	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "确认订单";
	});	
</script>
<script type="text/javascript">
var basePath='<%=basePath%>'; 	
	$(document).ready(function() {
		//判断是立即购买还是送朋友
		if(buy=="toFriend"){
			$(".ss").remove();
			document.getElementById("infor").style.height="80px";
		}
		if(buy==null || buy!="toFriend"){
			$("#buyCash").remove();
		}
		$("input.JQueryreturn").click(function() {// 取消
			$(".jqmWindow").hide();
		});
		//-------
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
	function changeway(ope){
         	switch (ope) {
			case 1://第一层
				if($("#sfirst").val()=="01"){//订单购买
					$("#ssecond").show().removeAttr("disabled");
				}else{//在线支付
					$("#ssecond").attr("disabled","disabled").hide();
					$("#sthird").attr("disabled","disabled").hide();
				}
				break;
			case 2://第二层
				if($("#ssecond").val()=="01"){//现金支付
					$("#sthird").show().removeAttr("disabled");
				}else{//在线支付
					$("#sthird").attr("disabled","disabled").hide();
				}
				break;
			case 3://第三层
				//alert($("#sthird").val());
				break;
			default:
				break;
			}
         }
</script>
</html>
