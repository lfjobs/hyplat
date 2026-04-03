<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page import="hy.ea.bo.human.Staff"%>
<%@ page language="java" pageEncoding="UTF-8" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
	String filepath = request.getSession().getServletContext().getRealPath("/");
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>登记表-编辑页面</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/driving/training/editRegistrationOftraining.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/autoComplete/autoCompleteOfExtend.js"></script>
<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var staffID = "${cstaff.staffID}";
		var staffName="${cstaff.staffName }";
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType ='${showType}'; 
		var select = 1;
    	var str="";
    	var temp = "";
    	var notoken = 0;
    	var pNumber = ${pageNumber};
    	var peopleid="";
    	var relationID="${contactrelation.relationID}";
    	var  search='${search}';
    	var module_title='${param.module_title}';  
    	var educationalCategories='${param.educationalCategories}';
    	var docstatus="${param.docstatus}";
    	var ppID="${productDesign.ppID}";
	function autoRead(){
		var ie = !-[1,]; 
        if(ie == false){
		   alert("该只支持IE!请切换为IE");
		   return;
        }
		initCardRead();
	}
	/**
	初始化信息 步驟
	使用的时候要先设置端口 串口：1~16 USB：1001~1016
     然后初始化，在读取信息
	*/
	function initCardRead(){
		var i = 1000;
		while(str!="0"){ 
		    SynIDCard1.Port = i++;
			//初始化成功是返回0
			str = SynIDCard1.Init();
		}
		if(str=="-1"){
			alert("未找到读卡设备，请先连接读卡设备。");
			return;
		}
		ReadCard_onclick();
	}
    function ReadCard_onclick(){
        var str1=SynIDCard1.ReadCard();
        var $form = $("#box1Form");
        //根据获得值判断是否成功读取，成功设置字段
        if(str1.indexOf("读卡成功") > -1){
            //检查该人员数据是否已经存在
            var urlCard = basePath+"ea/cstaff/sajax_n_ea_IsLawfulIdentityCard.jspa?result="+SynIDCard1.CardNo+"&date="+new Date().toLocaleString();
	        $.ajax({url: encodeURI(urlCard),type: "get",async: true,dataType: "json",
			    success: function cbf(data){
				    var member = eval("(" + data + ")");
					var SynIDCarder = member.SynIDCarder;
					document.box1Form.reset();
					//数据库中不存在记录
					if(SynIDCarder=="0"){
					    $("#invite").hide();
						$("#staffName",$form).val(SynIDCard1.NameA);
						
	                    var birthday = SynIDCard1.Born;
						birthday = birthday.substring(0,4)+"-"+birthday.substring(4,6)+"-"+birthday.substring(6,8);
						$("#birthday",$form).val(birthday);
						$("#staffIdentityCard",$form).val(SynIDCard1.CardNo);
						$("input#sex").val(SynIDCard1.SexL + "性");
						$("img#photo",$form).attr("src","");
						
						$("#PhotoName").html(SynIDCard1.PhotoName);
						
						//民族
						$("#nation option").each(function(){
							if($(this).text().indexOf(SynIDCard1.NationL) > -1 ){
								$(this).attr("selected",true);
								return;
							}
						});
						address = SynIDCard1.Address;
						//籍贯
						$("#nativePlace option").each(function(){
							if($(this).text().indexOf(address.substring(0,2)) > -1 ){
								$(this).attr("selected",true);
								return;
							}
						});
						personvalue =SynIDCard1.CardNo;
					}else{
					// 数据库中存在记录(根据记录的状态做不同的处理)
					// ###此时添加改成编辑
						staffsize = 1;
						personIdentityCard = SynIDCard1.CardNo;
						$("#staffCode",$form).val(SynIDCarder.staffCode);
						$("#recordCode",$form).val(SynIDCarder.recordCode);
						$("#staffName",$form).val(SynIDCarder.staffName);
						$("#usedNmae",$form).val(SynIDCarder.usedNmae);
						$("#sex",$form).val(SynIDCarder.sex);
						$("#birthday",$form).val(SynIDCarder.birthday);
						$("#nation",$form).val(SynIDCarder.nation);
						$("#nativePlace",$form).val(SynIDCarder.nativePlace);
						$("#staffIdentityCard",$form).val(SynIDCarder.staffIdentityCard);
						$("#nationality",$form).val(SynIDCarder.nationality);
						$("#reference",$form).val(SynIDCarder.reference);
						$("#referenceCode",$form).val(SynIDCarder.referenceCode);
						$("#PhotoName").html(SynIDCard1.PhotoName);
						
						if(SynIDCarder.verifyTime){
						    var verifyTime = SynIDCarder.verifyTime.time;
							verifyTime = new Date(verifyTime);
							verifyTime = verifyTime.getFullYear() + "-" + (verifyTime.getMonth() + 1) + "-" + verifyTime.getDate();
							$("#verifyTime",$form).val(verifyTime);
						}
						
						$("#staffDesc",$form).val(SynIDCarder.staffDesc);
						$("img#photo",$form).val(SynIDCarder.photo);
						$("img#photo",$form).attr("src",basePath+SynIDCarder.photo);
						$("table#stafftable").find('img#photo').show();
					    $("table#stafftable").find('#singleShuter').hide();
						$("#schedulingID",$form).val(SynIDCarder.schedulingID);
						$("#staffID",$form).val(SynIDCarder.staffID);
						$("#staffKey",$form).val(SynIDCarder.staffKey);
						personvalue = SynIDCarder.staffID;
						
						if(SynIDCarder.address){
						var urldistrict = basePath+"ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="+SynIDCarder.address+"&date="+new Date().toLocaleString();
						$.ajax({url: encodeURI(urldistrict),type: "get",async: true,dataType: "json",
						    success: function cbf(data){
							    var member = eval("(" + data + ")");
								var distinctlistSaved = member.distinctlistSaved;
								var list = member.list;
								$select = "<option selected='selected'>--请选择--</option>";
								for (var i = 0; i <  distinctlistSaved.length ; i++) {
								    if(i == 9){
									    return;
									}
								    $td.children('select:eq(' + i + ')').empty();
									$td.children('select:eq(' + i + ')').append($select);
									for(var j = 0 ;j < list[i].length; j++){
									    $op = $("<option />");
										$op.attr("value", list[i][j].districtID).attr("id",list[i][j].districtID).text(list[i][j].districtName);
										$td.children('select:eq(' + i + ')').append($op);
									}
									
									$opp = $("<option  selected='selected'/>");
									$opp.attr("value", distinctlistSaved[i].districtID).attr("id", distinctlistSaved[i].districtID).text(distinctlistSaved[i].districtName);
									$td.children('select:eq(' + i + ')').append($opp);
									$add = "<option class='add'  value = '" + distinctlistSaved[i].districtPID + "' >--新增--</option>";
									$td.children('select:eq(' + i + ')').append($add);
								}
								$td.children('select:eq(' + distinctlistSaved.length + ')').append($select);
								
								for(var z = 0 ; z < list[distinctlistSaved.length].length ; z++){
								    $op = $("<option />");
									$op.attr("value",list[distinctlistSaved.length][z].districtID).attr("id",list[distinctlistSaved.length][z].districtID).text(list[distinctlistSaved.length][z].districtName);
									$td.children('select:eq(' + distinctlistSaved.length + ')').append($op);
								}
								$addd = "<option class='add'  value = '" + distinctlistSaved[distinctlistSaved.length-1].districtID + "' >--新增--</option>";
								$td.children('select:eq(' + distinctlistSaved.length + ')').append($addd);
						    },
							error: function cbf(data){
							   // alert("数据获取失败！")
							}
						});
						}
						$("#invite").show();
					}
				},
				error: function cbf(data){
				    alert("数据获取失败！");
				}
			}); 
        }else{
        	alert("身份证信息读取失败!请确认二代身份证已放到读卡器读卡区域,或与管理员联系。");
        }
    }
</script>

<style type="text/css">
	table.table td{
		text-align: center;padding: 0px
	}
	tr.data input[id],tr.data select{
		display: none;
	}
	input,select{
		border-left:0px;border-top:0px;border-right:0px;border-bottom:0px; border-bottom-color:Black;width:100%;
	}
</style>
<style type="text/css">
.dropDiv {
    position: absolute;
    z-index: 10;
    display: none;
    cursor: hand;
    border:1px solid #7F9DB9;
    width: 200px;
    background-color: #a8c7ce;
}

.dropDiv .jhover {
    background-color: #D5E2FF;
}

</style>

<script type="text/javascript">
$(function(){
	$("input").live('click',function(){
		var subject=$(this).parents("tr").find("select#subject").attr("value");
		var program=$(this).parents("tr").find("select#program").attr("value");
		var array={"ddsrcontent":{"subject":subject,"program":program}};
	  $(this).autopoint({url:'<%=basePath%>/ea/trainingregistration/sajax_ea_getListOfDetailsContentOfByAjax.jspa?',submit:["action1", "action2"],tpl:'<div class="list"><div class="content">{content}</div></div>'});
	})
});
</script>
</head>

<body sroll="auto">
	<div class="content" style="height:750px;width:100%;margin-top: 10px;overflow: scroll;position: relative;" id="divOfDdsrtrainingrecord">
		<table width="99%" border="0" align="center" cellpadding="0"  style="margin-top: 5px;"
			cellspacing="0"  >
			<tr>
				<td height="25" align="center" width="90%"><span style="font-size: x-large;">培训记录登记表</span></td>
				<td height="25" align="right" width="10%"> 
					<c:if test="${param.print!='print' }">
						<input type="button" id="JQuerySubmitOfDdsrtrainingrecord" value="保存" style="cursor: pointer;"/> 
					</c:if>
				</td>
			</tr>
			<tr>
			<td  height="25" align="left" colspan="2"><%
								hy.ea.bo.human.Staff  data =(hy.ea.bo.human.Staff)request.getAttribute("cstaff");
											if (data != null) {
												StringBuffer barCode = new StringBuffer();
												barCode.append("<img src='");
												barCode.append(request.getContextPath());
												barCode.append("/CreateBarCode?data=");
												barCode.append(data.getStaffIdentityCard());
												barCode
														.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
												out.println(barCode.toString());
											} else {
												out.println("no data");
											}
									%><br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${cstaff.staffIdentityCard }
			</td>
			</tr>
		</table>
		<p style="display: none;">
			<object classid="clsid:E6E0A751-541A-4855-9A8D-35EB7122C950"
				id="SynIDCard1" name="SynIDCard1"
				codeBase="<%=basePath%>WEB-INF/plug-in/SynIDCard.Cab#version=1,0,0,1"
				width="0" height="0">
				<param name="_Version" value="65536" />
				<param name="_ExtentX" value="635" />
				<param name="_ExtentY" value="582" />
				<param name="_StockProps" value="0" />
			</object>
			<textarea rows="17" name="S1" cols="82"></textarea>
		</p>
		<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data"><input type="submit" name="submit" style="display:none" autocomplete="off"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table" >
					<tr class="trheight">
						<td width="12%" align="right">学员编号：</td>
						<td width="23%"><span id="staffCode">${cstaff.staffCode }</span></td>
						<td width="12%" align="right">档案编号：</td>
						<td width="23%" done0="9" done1="9"><span id="recordCode">${cstaff.recordCode }</span></td>
						<td width="30%" rowspan="6" align="center" id="phototd">
							<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
								codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0"
								width="250" height="100" id="singleShuter" align="middle">
								<param name="allowScriptAccess" value="sameDomain" />
								<param name="allowFullScreen" value="false" />
								<param name="FlashVars"
									value="servicesUrl=<%=basePath%>js/photo/save2.jsp" />
								<param name="movie"
									value="<%=basePath%>js/photo/singleShuter.swf" />
								<param name="quality" value="high" />
								<param name="bgcolor" value="#ffffff" />
								<embed src="<%=basePath%>js/photo/singleShuter.swf"
									FlashVars="servicesUrl=<%=basePath%>js/photo/save2.jsp"
									quality="high" bgcolor="#ffffff" width="250" height="100"
									name="singleShuter" align="middle"
									allowScriptAccess="sameDomain" allowFullScreen="false"
									type="application/x-shockwave-flash"
									pluginspage="http://www.macromedia.com/go/getflashplayer" />
							</object>
							<img name="photos" id="photo" style="display: none;" src="xxx"
								onload="setImg(this,   102,   126)" />
							<img id="idImg" style="display: none;" src="xxx" />
							<br />
							图片大小：102 x 126
							<a id="PhotoName"></a>
						</td>
					</tr>
					<tr>
						<td  align="right"><font color="red">*</font>姓名：</td>
						<td done0="10" done1="10"><span id="staffName">${cstaff.staffName }</span></td>
						<td align="right">曾用名：</td>
						<td done0="11" done1="11"><span id="usedNmae">${cstaff.usedNmae }</span></td>
					</tr>
					<tr>
						<td  align="right">性别：</td>
						<td done0="12" done1="12"><span id="sex">${cstaff.sex }</span></td>
						<td align="right">出生日期：</td>
						<td done0="13" done1="13"><span id="birthday">${cstaff.birthday }</span></td>
					</tr>
					<tr>
						<td  align="right">民族：</td>
						<td><span id="nation">${cstaff.nation }</span></td>
						<td align="right">籍贯：</td>
						<td><span id="nativePlace">${cstaff.nativePlace }</span></td>
					</tr>
					<tr>
						<td align="right">国籍：</td>
						<td><span id="nationality">${cstaff.nationality }</span></td>
						<td  align="right">身份证号码：</td>
						<td><span id="staffIdentityCard">${cstaff.staffIdentityCard }</span></td>
					</tr>
					<tr>
						<td  align="right">电话：</td>
						<td><span id="nation">${cstaff.reference }</span></td>
						<td align="right">E-MAIL：</td>
						<td><span id="nativePlace">${cstaff.referenceOrganization }</span></td>
					</tr>
				</table>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table" >
					<thead>
						 <tr>
						 	<td colspan="10">培训记录</td>
						 	<td align="center"> 
							 	<c:if test="${param.print!='print' }">	
							 		<img src="<%=basePath%>/images/r_8_06.gif"  class="add" style="cursor: pointer;"/> 
							 	</c:if>
						 	</td>
						 </tr>
					     <tr> 
					     	 <td width="40"> 序号</td>
					    	 <td width="80"> 科目</td>
						     <td width="110">教学项目</td>
						     <td width="200">教学内容</td>
						     <td width="160">开始时间</td>
						     <td width="160">结束时间</td>
						     <td width="60">学时/小时</td>
						     <td>学员学习意见</td>
						     <td>老师意见</td>
						     <td>完成/否</td>
						     <td width="80"><c:if test="${param.print!='print' }">	操作</c:if> </td>
					     </tr> 
					</thead>
					<tbody>
						 <tr id="clone" class="clone" style="display: none;">
						 		<td>&nbsp; </td>
						 		<td> 
						 			<select id="subject" disabled="disabled"  >
						 				<!-- <option value="">请选择</option>
						 				<option value="科目一">科目一</option>
						 				<option value="科目二">科目二</option>
						 				<option value="科目三">科目三</option>
						 				<option value="科目四">科目四</option> -->
						 			</select> 
						 		</td>
						 		<td>
						 			<select id="program" disabled="disabled">
						 				<!--<option value="">请选择</option>
						 				 <optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup> -->
						 			</select> 
						 		</td>
						 		<td>
						 		<input type="text" id="content" size="10" class="model1" disabled="disabled"   autocomplete="off" />
						 		</td>
						 		<td><input type="text"  id="startDate" size="10" class="model1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" disabled="disabled" autocomplete="off"/></td>
						 		<td><input type="text"  id="endDate" size="10" class="model1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" disabled="disabled" autocomplete="off"/></td>
						 		<td><input type="text"  id="time" size="10" class="model1"  disabled="disabled"/></td>
						 		<td><input type="text" id="opinionOfStudent" size="10" class="model1" disabled="disabled"/></td>
						 		<td><input type="text"  id="opinionOfCoach" size="10" class="model1" disabled="disabled"/></td>
						 		<td><input type="checkbox" autocomplete="off"/> 
						 		<input type="hidden" id="key" disabled="disabled"/>
						 		<input type="hidden" id="id"  disabled="disabled"/>
						 		<input type="hidden" id="type" value="<s:property value="dtDrivingPrincipal.registrationcarname" />" disabled="disabled"/>
						 		<input type="hidden"  id="complete" value="0" disabled="disabled"/>
						 		<input type="hidden"  id="ddsrstudentid" value="<s:property value="cstaff.staffID" />" disabled="disabled"/>
						 		</td>
						 		<td>
							 		<c:if test="${param.print!='print' }">
							 			<img src="<%=basePath%>/images/r_8_10.gif"  style="cursor: pointer;" class="edit"/>&nbsp;&nbsp; &nbsp;   <img src="<%=basePath%>/images/r_8_08.gif"  style="cursor: pointer;" class="del"/> 
							 		</c:if>
						 		</td>
						 	</tr>
						 <s:iterator value="beanList"  status="i">
						 	<tr id="<s:property value="id" />" class="data">
						 		<td>  <s:property value="#i.count"/> </td>
						 		<td> 
						 			<select id="subject"  disabled="disabled" >
						 				<!-- <option value="">请选择</option>
						 				<option value="科目一">科目一</option>
						 				<option value="科目二">科目二</option>
						 				<option value="科目三">科目三</option>
						 				<option value="科目四">科目四</option> -->
						 			</select>
						 			<span id="subject"> <s:property value="subject"/> </span> 
						 		</td>
						 		<td>
						 			<select id="program"  disabled="disabled">
						 				<!--<option value="">请选择</option>
						 				 <optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup> -->
						 			</select> 
						 			<span id="program"><s:property value="program"/> </span> 
						 		</td>
						 		<td><input type="text" id="content"   disabled="disabled"  autocomplete="off" value="<s:property value="content"/>"/>
						 			<span> <s:property value="content"/> </span>
						 		</td>
						 		<td><input type="text"  id="startDate" size="10" class="model1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  disabled="disabled" value="<s:property value="startDate"/>" autocomplete="off"/>
						 			<span> <s:property value="startDate"/> </span> 
						 		</td>
						 		<td><input type="text"  id="endDate" size="10" class="model1" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  disabled="disabled" value="<s:property value="endDate"/>" autocomplete="off"/>
						 			<span> <s:property value="endDate"/> </span>
						 		</td>
						 		<td><input type="text"  id="time" size="10" class="model1"   disabled="disabled" value="<s:property value="time"/>"/>
						 			<span> <s:property value="time"/> </span>
						 		</td>
						 		<td><input type="text" id="opinionOfStudent"   disabled="disabled" value="<s:property value="opinionOfStudent"/>"/>
						 			<span> <s:property value="opinionOfStudent"/> </span>
						 		</td>
						 		<td><input type="text"  id="opinionOfCoach"    disabled="disabled" value="<s:property value="opinionOfCoach"/>"/>
						 			<span> <s:property value="opinionOfCoach"/> </span>
						 		</td>
						 		<td><input type="checkbox"  ${complete=='1'?'checked':''} autocomplete="off"/> 
						 		<input type="hidden" id="key" value="<s:property value="key" />" disabled="disabled"/>
						 		<input type="hidden" id="id"   value="<s:property value="id" />" disabled="disabled"/>
						 		<input type="hidden" id="type" value="<s:property value="dtDrivingPrincipal.registrationcarname" />" disabled="disabled"/>
						 		<input type="hidden"  id="complete" value="<s:property value="complete" />" disabled="disabled"/>
						 		<input type="hidden"  id="ddsrstudentid" value="<s:property value="cstaff.staffID" />" disabled="disabled"/>
						 		</td>
						 		<td>
							 		<c:if test="${param.print!='print' }">
							 			<img src="<%=basePath%>/images/r_8_10.gif"  style="cursor: pointer;" class="edit"/>&nbsp;&nbsp; &nbsp;   <img src="<%=basePath%>/images/r_8_08.gif"  style="cursor: pointer;" class="del"/> 
							 		</c:if>
						 		</td>
						 	</tr>
						 </s:iterator>
						 
						 <tr>
							<td colspan="3">交费情况</td>
							<td>已收</td>
							<td></td>
							<td>欠款</td>
							<td colspan="2"></td>
							<td colspan="3">未清/已清</td>
						</tr>
						<tr>
							<td colspan="3" height="45">学员科二，科三学习意见</td>
							<td colspan="2"></td>
							<td colspan="3">学员签字</td>
							<td colspan="3"></td>
						</tr>
						<tr>
							<td colspan="3" height="45">学员科二，科三学习意见</td>
							<td colspan="2"></td>
							<td colspan="3">教练签字</td>
							<td colspan="3"></td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<td>说明</td>
							<td colspan="10" align="left">&nbsp;
							</td>
						</tr>
					</tfoot>	
				</table>
				
			</form>
    </div>
</body>
</html>