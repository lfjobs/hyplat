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
    
    <title>课程报名</title>
     <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/bm.css">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/css.css">

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
     <style type="text/css">
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
    <script type="text/javascript">
    
        $(function () {
            
            imgAuto();//初始化图片大小
			$(".bm_tx_fx").click(function(){Fscreen();});
			$(".bm_tx_pyq").click(function(){Fscreen();});
			$(".bm_2_an").click(function(){$(".bm_2").attr("class","new");});

            window.onresize = function () {
                imgAuto();//屏幕缩放时图片的大小
            }
			
        });
		function Fscreen(){
			$(".new").attr("class","bm_2");
		}


        function imgAuto() {
            var wid = document.documentElement.clientWidth * .8;
            $(".imgAuto").each(function () {
                $(this).css("width", wid);
            });
        }//遍历循环所有class="imgAuto"的标签并改变其大小
		
		function imgAuto2() {
            var wid = document.documentElement.clientWidth * .10;
            $(".imgAuto2").each(function () {
                $(this).css("width", wid);
            });
        }
		
       function enroll(){
          var name= $("#name").val();
          var reference=$("#reference").val();
          var IdentityCard=$("#IdentityCard").val();
       	 /* var address="";
          var province=$("#s_province").children('option:selected').val();
          var city=$("#s_city").children('option:selected').val();
          var county=$("#s_county").children('option:selected').val();
          var staffAddress=$("#staffAddress").val();
          if(county!="市、县级市"){
				address=province+city+county+staffAddress;
               }else if(county=="市、县级市" && city!="地级市"){
            	   address=province+city+staffAddress;
                   }else if(city=="地级市" && province!="省份"){
                	   address=province+staffAddress;
                       }else{
                    	   address=staffAddress;
                           }
		*/
          var addr = "";
     	  $(".JQueryaddress").find("select").find("option[value]:selected").each(function() {
     		if ($(this).text() != '--请选择--' && $(this).text() != '--新增--')
     			addr = addr + $(this).text();
     	  });
          $("#address").val(addr);
          if(name=="" || reference=="" || IdentityCard=="" || addr==""){
			alert("请完善信息");
			return;
              }
          if(confirm("确定报名?"))
          {
        	  $("#submit").submit();
          }
           }
     
    </script>
	</head>
	<body bgcolor="#E7E7E7">
		<div class="new">
			<div class="bm_2_con">
				<div class="bm_2_div">
					<img  class="imgAuto2" src="<%=basePath %>images/shengwei/yd.png" />
				</div>
				<div class="bm_2_div">
					<input name="" type="button" class="bm_2_an" value="关闭提示 " />
				</div>
			</div>
		</div>
		<s:iterator value="courseList" id="list">
			<div class="bm_tx_bt">
				${list[1]}
			</div>
			<div class="geduan"></div>
			<form action="<%=basePath%>/ea/shengwei/courseEnroll.jspa?weidianType=${weidianType}&enroll=${enroll}" method="post" id="submit">
				<div class="bm_tx_kuang">
					<div class="bm_tx_zd">
						学员姓名：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.staffName" id="name" type="text" class="bm_tx_bd_bd"/>
					</div>
					<div class="bm_tx_zd">
						学员电话：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.reference" id="reference" type="text" class="bm_tx_bd_bd" maxlength="11"/>
					</div>
					<div class="bm_tx_zd">
						身&nbsp;份&nbsp;证：
					</div>
					<div class="bm_tx_bd">
						<input name="staff.staffIdentityCard" id="IdentityCard" type="text" class="bm_tx_bd_bd" onblur="aa();"/>
					</div>
					
					<div class="bm_tx_zd">
						学员地址：
					</div>
					<div class="bm_tx_bd" style="height:53px;">
						<table id="cataffSearchTable" width="100%;"> 
							<tr> 
								<td class="JQueryaddress" >
									<select name="addressProvince" id="province" number='0'
										style="width: 25%;">
									</select>
									<!-- <option>选择省</option>-->
									<select name="addressCity" id="city" number='1'
										style="width: 25%;">
									</select>
									<select name="addressCounty" id="county" number='2'
										style="width: 25%;">
									</select>
									<select name="addressTown" id="addressTown" number='3'
										style="width: 25%;">
									</select>
									<select name="addressVillage" id="addressVillage"
										number='4' style="width: 25%;">
									</select>
									<select name="addressCommunity" id="addressCommunity"
										number='5' style="width: 25%;">
									</select>
								</td>
							</tr>
							<input name="address.addressDetailed" type="hidden" id="address"/>
						</table>
					<%-- <select id="s_province" name="s_province" style="width:25%;"></select>
						<select id="s_city" name="s_city" style="width:25%;"></select>
						<select id="s_county" name="s_county" style="width:30%;"></select>
						<script src="<%=basePath%>js/ea/wechat/address.js"></script>
						<script type="text/javascript">_init_area();</script> 
						<input id="staffAddress" type="text" class="bm_tx_bd_bd" placeholder="请输入详细地址" maxlength="25"/>
						--%>
					</div>
					<%--<div class="bm_tx_zd">
						微分金店长：
					</div>
					<div class="bm_tx_bd">
						<input name="" id="" type="text" class="bm_tx_bd_bd" value="${list[6] }" readonly="readonly"/>
					</div>
						--%><input name="staff.activityId" type="hidden" value="${list[0]}">
						<input name="companyid" type="hidden" value="${companyid}">
					<div style="clear: both;"></div>
				</div>
			</form>
			<div class="geduan"></div>
			<div class="bm_tx_kuang">
				<div class="bm_pic">
					<img id="image1" class="imgAuto" src="<%=basePath %>${list[2]}" />
				</div>
				<div class="bm_font">
					<div id="name" class="bm_font_xx">
						${list[1] }
					</div>
					<div class="bm_font_jg">
						￥${list[3] }
					</div>
					<div class="bm_font_bz">考核办证费：￥${list[4] }</div>
				</div>
						
			<div class="bm_tx_anbg"><input name="" type="button" class="bm_tx_an" onClick="enroll()" value="我要报名" /></div>
			<div class="geduan"></div>
			<div class="bm_tx_anbg">
				<div class="bm_tx_fx"><input name="" type="button" class="bm_tx_fxan" value="分享到群/朋友" /></div>
				<div class="bm_tx_pyq"><input name="" type="button" class="bm_tx_fxan" value="分享到朋友圈" /></div>
			</div>
		</div>
		<div class="geduan"></div>
	
	</s:iterator>	
	<div class="jqmWindow" style="width: 90%; right: 5%; top: 50%;"
			id="newdistrict">			
			<table style="margin-top: 5px;">
				<tr align="center">
					<td align="right" style="font-size: 12px;"> 
						&nbsp;&nbsp;&nbsp;地域名字：
					</td>
					<td>
						<input id="districtNames" style="width: 50%;height: 25px; font-size: 12px;"/>
						&nbsp;&nbsp;
						<span style="color: red;font-size: 12px;">*按地域区分组</span>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedistrict" style="width: 30%;" value="确定" />
				<input type="button" class="input-button JQueryreturn2" style="width: 30%;" value="取消" />
			</div>
		</div>	
		

	 <script>
        var imgUrl =document.getElementById("image1").src;
		var lineLink = document.location.href;
		var descContent = document.getElementById("name").innerText;
		var shareTitle = document.title;
		var appid = 'wxc9937e3a66af6dc8';
		function shareFriend() {
		    WeixinJSBridge.invoke('sendAppMessage',{
		                            "appid": appid,
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": '点击报名...',
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('send_msg', res.err_msg);
		                            })
		}
		function shareTimeline() {
		    WeixinJSBridge.invoke('shareTimeline',{
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": descContent,
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('timeline', res.err_msg);
		                            });
		}
		function shareWeibo() {
		    WeixinJSBridge.invoke('shareWeibo',{
		                            "content": 'descContent',
		                            "url": lineLink,
		                            }, function(res) {
		                            _report('weibo', res.err_msg);
		                            });
		}
		// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {

	        // 发送给好友
	        WeixinJSBridge.on('menu:share:appmessage', function(argv){
	            shareFriend();
	            });
	
	        // 分享到朋友圈
	        WeixinJSBridge.on('menu:share:timeline', function(argv){
	            shareTimeline();
	            });
	
	        // 分享到微博
	        WeixinJSBridge.on('menu:share:weibo', function(argv){
	            shareWeibo();
	            });
        }, false);
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
				+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?type=wechat&districtPID=0"
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
	var urldistrict = basePath
			+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?type=wechat&districtPID="
			+ encodeURI(DistrictID) + "&date2=" + new Date();
	$.ajax({
		url : urldistrict,
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var distinctlistSaved = member.distinctlistSaved;
			var list = member.list;
			$select = "<option selected='selected'>--请选择--</option>";
			retoken = 0;
			for (var i = 0; i < distinctlistSaved.length; i++) {
				if (i == 9) {
					return;
				}
				$td.children('select:eq(' + i + ')').empty();
				$td.children('select:eq(' + i + ')').append($select);
				for (var j = 0; j < list[i].length; j++) {
					$op = $("<option />");
					$op.attr("value", list[i][j].districtID).attr("id",
							list[i][j].districtID)
							.text(list[i][j].districtName);
					$td.children('select:eq(' + i + ')').append($op);
				}
				$opp = $("<option  selected='selected'/>");
				$opp.attr("value", distinctlistSaved[i].districtID).attr(
						"id", distinctlistSaved[i].districtID)
						.text(distinctlistSaved[i].districtName);
				$td.children('select:eq(' + i + ')').append($opp);
				$add = "<option class='add'  value = '"
						+ distinctlistSaved[i].districtPID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + i + ')').append($add);
			}
			$td.children('select:eq(' + distinctlistSaved.length + ')')
					.append($select);
			for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
				$op = $("<option />");
				$op
						.attr(
								"value",
								list[distinctlistSaved.length][z].districtID)
						.attr(
								"id",
								list[distinctlistSaved.length][z].districtID)
						.text(list[distinctlistSaved.length][z].districtName);
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($op);
			}
			$addd = "<option class='add'  value = '"
					+ distinctlistSaved[distinctlistSaved.length - 1].districtID
					+ "' >--新增--</option>";
			$td.children('select:eq(' + distinctlistSaved.length + ')')
					.append($addd);
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
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
	</body>
</html>
