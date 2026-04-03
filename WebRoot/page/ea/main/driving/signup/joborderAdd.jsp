<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加派工单</title>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<style>
*{ margin:0; padding:0px;}
a, a:link { color: #222; text-decoration: none; }
a:focus { outline: none; }
table{ background:#3fb2ad; line-height:15px; margin-top:20px;width:1000px; height:270px;align:center}
table tr td{ background:#fff; text-align:center;}
table tr .bg_color{ background:#e3e3e3; }
.bigtxt{
width:100%;
height:100%;
}
</style>
<script type="text/javascript">
			var basePath = '<%=basePath%>';
			var token = 0;
			var select = 1;
			var notoken = 0;
			
</script>
</head>
<body>
<h4   align="center" style="margin-top: 10px">CNG气瓶检验派工单</h4>
<div align="center">
<form name="orgEntryForm" id="orgEntryForm" method="post">
<input type="submit" name="submit" style="display: none" /> 
<table width="100%" border="0" cellspacing="1" cellpadding="1">
  <tr>
    <td width="16%" class="bg_color">气瓶编号</td>
    <td width="17%"><input type="text"  id="fname" onkeyup="upperCase(this.id)" onkeydown="lowCase(this.id)" onfocus="upperCase(this.id)" style=" float:left;width:100%;height:100%;"/>
    <input type="text" style="display:none" name="dispitchjob.cylinderNum" id="carlindernum"/>
    <div align="left" style="border:1px solid #3fb2ad;background-color:#3fb2ad;border-bottom-color:#FFFFFF; height:auto;width:168px;display:none; position:absolute; margin-top:30px; z-index:10000" id="datediv" ></div></td>
    <td width="67%" class="bg_color" colspan="4" ><input type="button" value="产品登记" 
    onclick="window.location.href = '<%=basePath%>page/ea/main/driving/signup/carCarlinderInformation_add.jsp'"/>
    </td>
  </tr>
  <tr>
    <td width="16%" class="bg_color">类型</td>
    <td width="17%"><span id = "carlindertype"><span/></td>
    <td width="16%" class="bg_color">设计壁厚</td>
    <td width="17%"><span id = "designThickness"></td>
    <td width="16%" class="bg_color">型号</td>
    <td width="18%"><span id = "cylinderModel"></td>
  </tr>
  <tr>
    <td class="bg_color">制造单位</td>
    <td><span id = "manufactureCompany"></td>
    <td class="bg_color">出厂日期</td>
    <td><span id = "leavefactorydate"></td>
    <td width="13%" class="bg_color">重量</td>
    <td width="37%"><span id = "weight"></td>
  </tr>
  <tr>
     <td class="bg_color">容积</td>
    <td><span id = "volume"></td>
    <td class="bg_color">公称工作压力</td>
    <td><span id = "nominalworkpressure"></td>
    <td width="13%" class="bg_color">水压实验压力</td>
    <td width="37%"><span id = "hydraulicTestPressure"></td>
    </tr>
    <tr>
     <td class="bg_color">材质</td>
    <td><span id = "texture"></td>
    <td class="bg_color">纤维</td>
    <td><span id = "fiber"></td>
    <td width="13%" class="bg_color">树脂</td>
    <td width="37%"><span id = "resin"></td>
    </tr>
    <tr>
     <td class="bg_color">车牌号码</td>
    <td colspan="5"><span id = "licensenumber"></td>
    </tr>
    <tr>
     <td class="bg_color">检验编号</td>
    <td><input type="text" class="bigtxt" name="dispitchjob.checkoutNum"/></td>
    <td class="bg_color">室温℃</td>
    <td><input type="text" class="bigtxt" name="dispitchjob.roomTemperature"/></td>
    <td width="13%" class="bg_color">水温℃</td>
    <td width="37%"><input type="text" style="width:100%;height:100%" name="dispitchjob.waterTemperature"/></td>
    </tr>
    <tr>
     <td class="bg_color">检测日期</td>
    <td><input type="text" onfocus="date(this)" class="bigtxt" id="testdate" name="dispitchjob.checkDate"/></td>
    <td class="bg_color">下次检验日期</td>
    <td><input type="text" onfocus="date(this)"class="bigtxt" name="dispitchjob.nextcheckDate"/></td>
    <td width="13%" class="bg_color">检验人</td>
    <td width="37%"><input type="text"  style="width:80%;height:100%"  id="principal1" readonly="readonly" /><img src = "<%=basePath%>images/r_8_12.gif" onclick="searchCoachpeople()"/><input type="hidden" id="principalID1" name="dispitchjob.staffId" /></td>
    </tr>
    <tr>
     <td class="bg_color">经手人</td>
    <td><input type="text" id="principal" readonly="readonly" style="width:80%;height:100%"/><img src = "<%=basePath%>images/r_8_12.gif" onclick="searchCoach()" /><input type="hidden" id="principalID" name="dispitchjob.staffIds" />
    </td>
    <td colspan="4"></td>
    </tr>
</table>
</form>
</br></br>
<div align="center"><input type="button" class="input-button" value="保&nbsp;&nbsp;&nbsp;&nbsp;存" onclick="savedate()"/>  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<input type="button" value="打印预览" class="input-button JQueryPrint"/> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
 <input type="button" value="返&nbsp;&nbsp;&nbsp;&nbsp;回" class="input-button JQueryreturn" onclick="window.location.href='<%=basePath%>ea/dispitch/ea_getListorderwork.jspa?'"/> </div>
</div>

<!-- 从当前部门的员工中选择经手人 -->
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 380px; absolute; display: none; left: 1%; top: 5%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm" />
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="340px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		</div>
<!-- 从当前部门的员工中选择检测人 -->
		<div id="jqmWindow3" class="jqmWindow"
			style="width: 95%; height: 380px; absolute; display: none; left: 1%; top: 5%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform1" />
				<input style="display: none;" id="parm1" />
			</div>
			<iframe name="ifr1" id="ifr1" width="100%" height="340px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isSubmit1" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack1" value=" 关闭 "
					style="cursor: hand" />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<iframe name="main" frameborder="0" noresize="noresize" border="0" id="main"
		framespacing="0" height="0"></iframe>
<script type="text/javascript">
function upperCase(x)
{
 $("#datediv").show();
var y=document.getElementById(x).value
var trur = document.getElementById(x).value=y.toUpperCase()
var url = basePath +"ea/productregister/sajax_ea_swdad.jspa?edit="+ trur;
$.ajax({
            type: "GET",
            url: encodeURI(url),
            contentType: "application/json; charset=utf-8",
            //data:{edit:y},
            async:false,
            dataType: "json",
            success: function (data) {
               	var member = eval("(" + data + ")");
                var cylindernumS = member.returndatalist;
               for(var i = 0;i<cylindernumS.length;i++){
                $("#datediv").append("<span onclick = 'huoquval(this.id)' width= '165px' id = '"+cylindernumS[i][1]+"' title= '"+cylindernumS[i][1]+
                "' style='background-color:#FFFFFF;cursor:pointer;display:block;line-height:30px;' onMouseOver=style.backgroundColor='#3fb2ad';style.color='white' onMouseOut=style.backgroundColor='#FFFFFF';style.color='black'>"+ 
        	      cylindernumS[i][6]+"</span><hr style='border-top:1px solid #3fb2ad;' />"); 
        	      $("#"+ cylindernumS[i][1]).click(function(){
        	     	$("#fname").attr("value",$(this).text());
        	     	 $("#datediv").empty();
        	      })
                } 
               if($("#fname").val()==""){
			    $("#datediv").empty();
	}
            },
            error: function (msg) {
                alert(msg);
            }
        });

}
function lowCase(x)
{
 $("#datediv").empty();

}

//打印预览派工单
$(".JQueryPrint").click(function(){
var url = basePath + "ea/productregister/ea_detailinformation.jspa?edit=printsheetlist&carCylinderId="+ $("#carlindernum").val();
if($("#fname").val()==""){
alert("您未选中气瓶！不能打印预览派工单！")
return;
}
open(url);
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/dispitch/ea_getListorderwork.jspa?";
}


 //选择责任人       
function searchCoachpeople(){
	 var parm = "zm";
	 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
	 getValueForParm2('cstaffForm','partnerName',url);
}
function getValueForParm2(attachTable,parm,url){ //打开页面
	 $("#myform1",$("#jqmWindow3")).attr("value",attachTable);
	 $("#parm1",$("#jqmWindow3")).attr("value",parm);
  	 $("#ifr1").attr("src",basePath+url);
  	 $("#jqmWindow3").jqmShow();
}
 //选择责任人       
function searchCoach(){
	 var parm = "zm";
	 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
	 getValueForParm('cstaffForm','partnerName',url);
}
function getValueForParm(attachTable,parm,url){ //打开页面
	 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
	 $("#parm",$("#jqmWindow2")).attr("value",parm);
  	 $("#ifr").attr("src",basePath+url);
  	 $("#jqmWindow2").jqmShow();
}
//保存数据
function savedate(){
if($("#fname").val()==""){
alert("请选择一个气瓶进行派工！");
return;
	}
	$("#orgEntryForm").attr("target","main")
				.attr("action", basePath
								+ "ea/dispitch/ea_toSavedispitch.jspa?"
								);
		document.orgEntryForm.submit.click();
		token=2;
}
function huoquval(yy){
 $("#carlindernum").attr("value",yy); 
 $("#datediv").show();
var url = basePath +"ea/productregister/sajax_ea_swdad.jspa?carlinderID="+ yy+"&date="+new Date();
$.ajax({
            type: "GET",
            url: encodeURI(url),
            contentType: "application/json; charset=utf-8",
            //data:{edit:y},
            async:false,
            dataType: "json",
            success: function (data) {
             var member = eval("(" + data + ")");
            var cylinderinfo = member.datasizelist;
            var wd = cylinderinfo.leavefactorydate;
            var leavedate = wd.substring(0,10);
          $("#carlindertype").text(cylinderinfo.cylinderType); $("#designThickness").text(cylinderinfo.designThickness);
           $("#cylinderModel").text(cylinderinfo.cylinderModel); $("#manufactureCompany").text(cylinderinfo.manufactureCompany); 
            $("#weight").text(cylinderinfo.weight); $("#volume").text(cylinderinfo.volume); $("#nominalworkpressure").text(cylinderinfo.nominalworkpressure); 
            $("#hydraulicTestPressure").text(cylinderinfo.hydraulicTestPressure); $("#texture").text(cylinderinfo.texture); $("#fiber").text(cylinderinfo.fiber);
            $("#resin").text(cylinderinfo.resin);$("#licensenumber").text(cylinderinfo.licensenumber); 
          $("#leavefactorydate").text(leavedate);
         $("#datediv").hide();
            },
            error: function (msg) {
                alert(msg);
            }
        });
}
</script>
<script type="text/javascript">
		$(document).ready(function() {
			$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		});
		$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			    }); 
			   
	$("#isSubmit").click(function(){// 选择确定
		var parm = $("#parm",$("#jqmWindow2")).attr("value");
		var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
		var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
		if(parm != "")
		
		$("#principal",$("#orgEntryForm")).attr("value",value2).trigger("blur");
		$("#principalID",$("#orgEntryForm")).attr("value",value1).trigger("blur");
		$("#ifr").attr("src","");
	       $("#jqmWindow2").jqmHide();
	   });	
	   $("#isBack1").click(function(){// 返回
			       $("#jqmWindow3").jqmHide();
			    }); 
			   
	 $("#isSubmit1").click(function(){// 选择确定
		var parm = $("#parm1",$("#jqmWindow3")).attr("value");
		var value1 = window.frames["ifr1"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
		if(value1 == ""){
			alert("请选择");
			return;
		}
		var value2 = window.frames["ifr1"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
		if(parm != "")
		$("#principal1",$("#orgEntryForm")).attr("value",value2).trigger("blur");
		$("#principalID1",$("#orgEntryForm")).attr("value",value1).trigger("blur");
		$("#ifr1").attr("src","");
	       $("#jqmWindow3").jqmHide();
	      
	   });	  
</script>
<body>
</body>
</html>
