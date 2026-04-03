<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>学员管理</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/studentManager_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var staffID = '';
		var basePath = '<%=basePath%>';
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType ='${showType}'; 
		var roleID = '${account.roleID}'; 
		var select = 1;
		var photosizes = 0;
	
    var str="";
    var temp = "";
  	 
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
    
    //点击读取芯片号
    function chipRead(){
	   $("#box1Form #loadcab").attr(
			"src",
			basePath + "page/ea/common/loadActiveX.html?code="
					+ Math.random());
       
    
    
    }
    //正在读取芯片号
    function readChiping(values){  

     $("input#chipid").val(values);
    }
</script>
</head>

<body>
	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;学员管理--编辑 </div>
			<table class="JQueryflexme" border="0">
			<tr>
			<td>
			</td>
			</tr>
			</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">人员基本信息</td>
				<td align="right"><a href="#" onclick="changemenu('box1',1,'edit')" id="mord1"
					class="mord" style="color:#0066FF;">修改</a><a href="#" onclick="changemenu('box1',1,'close')" id="mord1_close"
					class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
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
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data">
				<s:token/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td width="12%" align="right">员工编号：</td>
						<td width="23%"><input name="cstaff.staffCode" value="${cstaff.staffCode }"
							class="input isHide" id="staffCode" readonly="readonly" size="10"  style="width:100%;height:100%;border: 0;"/>
							<span class="isShow">${cstaff.staffCode }</span>
						</td>
						<td width="12%" align="right">档案编号：</td>
						<td width="23%" done0="9" done1="9"><input
							name="cstaff.recordCode" class="input isHide" id="recordCode" value="${cstaff.recordCode }"
							size="10" readonly="rephoto_preview_fakeadonly" style="width:100%;height:100%;border: 0;"/>
							<span class="isShow">${cstaff.recordCode }</span></td>
						<td width="30%" rowspan="5" align="center" id="phototd">
							<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"  
								codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0"
								width="250" height="180" id="singleShuter" align="middle">
								<param name="allowScriptAccess" value="sameDomain" />
								<param name="allowFullScreen" value="false" />
								<param name="FlashVars"
									value="servicesUrl=<%=basePath%>js/photo/save2.jsp" />
								<param name="movie"
									value="<%=basePath%>js/photo/singleShuter.swf" />
								<param name="quality" value="high" />
								<param name="bgcolor" value="#ffffff" />
								<param name="wmode" value="transparent" /> <!--是否透明--> 
								<embed src="<%=basePath%>js/photo/singleShuter.swf"
									FlashVars="servicesUrl=<%=basePath%>js/photo/save2.jsp"
									quality="high" bgcolor="#ffffff" width="250" height="180"
									name="singleShuter" align="middle" 
									allowScriptAccess="sameDomain" allowFullScreen="false"
									type="application/x-shockwave-flash" wmode="transparent" 
									pluginspage="http://www.macromedia.com/go/getflashplayer"/>
							</object>
							<img name="photos"  width="102" height="126" id="photo" style="display: none;"   src="xxx" 
								onload="setImg(this,   102,   126)" />
							<img id="idImg" style="display: none;" src="xxx" width="102" height="126"/>
							<br />
							图片大小：102 x 126
							<a id="PhotoName"></a>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right"><font color="red">*</font>姓名：</td>
						<td done0="10" done1="10"><input name="cstaff.staffName" style="width:100%;height:100%;border: 0;"
							class="put4 isHide" id="staffName" size="10" value="${cstaff.staffName }" />
							<span class="isShow">${cstaff.staffName }</span>
							<span id="staffID" style="display:none">${cstaff.staffID }</span>
							</td>
						<td align="right">曾用名：</td>
						<td done0="11" done1="11"><input name="cstaff.usedNmae" style="width:100%;height:100%;border: 0;"
							class="input isHide" id="usedNmae" size="10" value="${cstaff.usedNmae }" />
							<span class="isShow">${cstaff.usedNmae }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">性别：</td>
						<td done0="12" done1="12"><input name="cstaff.sex" style="width:100%;height:100%;border: 0;"
							title='根据身份证自动生成' class="input isHide" id="sex" size="10" value="${cstaff.sex }"
							readonly="readonly" /><span class="isShow">${cstaff.sex }</span></td>
						<td align="right">出生日期：</td>
						<td done0="13" done1="13"><input name="cstaff.birthday" style="width:100%;height:100%;border: 0;"
							title='根据身份证自动生成' class="input isHide" id="birthday" size="10" value="${cstaff.birthday }"
							readonly="readonly" /><span class="isShow">${cstaff.birthday }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">民族：</td>
						<td><s:select list="nations" listKey="codeValue" id="nation"
								listValue="codeValue" name="cstaff.nation" headerKey="汉族"
								headerValue="汉族" theme="simple"></s:select>
								<span class="isShow">${cstaff.nation }</span>
								</td>
						<td align="right">籍贯：</td>
						<td><s:select list="nativeplaces" listKey="codeValue"
								id="nativePlace" listValue="codeValue" headerKey="北京"
								headerValue="北京" name="cstaff.nativePlace" theme="simple">
							</s:select>
							<span class="isShow">${cstaff.nativePlace }</span>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right">最常用联系方式：</td>
						<td colspan="3">
						<input name="cstaff.reference" class="input isHide" style="width:100%;height:100%;border: 0;"
							id="reference" size="14"  value="${cstaff.reference}"/><span class="isShow">${cstaff.reference }</span>
								</td>
					</tr>
					<tr>

						<td style="height:30" align="right">信息类别：</td>
						<td><select id="staus" class="tm isHide">
								<option value="00" selected="selected">确定人员信息</option>
								<option value="01" >非确定人员信息</option>
						</select><span class="statusinfo"><c:if test="${cstaff.staffIdentityCard ne '' }">确定人员信息</c:if> <c:if
								test="${cstaff.staffIdentityCard eq '' }">非确定人员信息</c:if></span></td>
						<td align="right">护照号：</td>
						<td><input name="cstaff.passportNum" class="input isHide" style="width:100%;height:100%;border: 0;"
							id="passportNum" size="14"  value="${cstaff.passportNum}"/><span class="isShow">${cstaff.passportNum }</span>
							</td>
						<td style="height:30;border-bottom:none;" colspan="2">
						 &nbsp;
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right"><font color="red">*</font>身份证号码：</td>
						<td><span class="isShow staffIdentityCard">${cstaff.staffIdentityCard }</span><input name="cstaff.staffIdentityCard"
							class="input isHide IdentityCard card put4" id="staffIdentityCard" style="width:100%;height:100%;border: 0;"
							size="18" value="${cstaff.staffIdentityCard }"/>
						</td>
						<td style="height:30" colspan="2">
						<input type="button" name="ReadCardBtn" id="ReadCardBtn" class="input-button" style="cursor: pointer;" value=" 身份证读取 " onclick="autoRead();" />
						</td>
						<td style="display:block;border-top:none;border-bottom:none;">
							<input id="singleShuterphoto" type="button" style="width: 50px;" class="isHide input-button" 
								value="摄像头" /><input name="photo" id="staffphoto" class="input01 isHide"  type="file"
								style="width: 150px;" />
							<input name="cstaff.photo" type="hidden" id="photo" value="${cstaff.photo}"/>
							<input name="cstaff.staffID" id="staffID" type="hidden" value="${cstaff.staffID}"/>
							<input name="cstaff.staffKey" id="staffKey" type="hidden" value="${cstaff.staffKey}"/>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right"><font color="red">*</font>芯片号：</td>
						<td><input name="cstaff.chipid"
							class="input isHide chip" id="chipid" style="width:100%;height:100%;border: 0;"
							size="18" value="${cstaff.chipid}"/>
							<input type="hidden"  id="oldchipid" value="${cstaff.chipid}"/>
							
							<span class="isShow chipid">${cstaff.chipid }</span>
						</td>
						<td style="height:30" colspan="2">
						<input type="button" class="input-button isHide" style="cursor: pointer;" value=" 芯片号读取 " onclick="chipRead();" />
						</td>
						<td style="border-top:none;" colspan="2">
						  <iframe width="0" height="0" name="loadcab" id="loadcab"></iframe>
						</td>
						
					</tr>
				<tr id="tools" style="display:table-row;border:0;">
					<td style="height:30" align="right" colspan="5">
						<input type="button" onclick="toSave('box1Form','/ea/studentManager/ea_saveStudentList.jspa')" class="input-button JQuerySubmit isHide" style="cursor: pointer; width: 80px;" value="提交" />
					</td>
				</tr>
				</table>
			</form>
		</div>
		
		<div class="menu01" >
			<form name="humanForm" id="humanForm" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
					<input name="studentmanager.studentmanagerkey" value="${studentmanager.studentmanagerkey}" style="display: none"/>
					<input name="studentmanager.studentmanagerid" value="${studentmanager.studentmanagerid}" style="display: none"/>
					<input name="studentmanager.companyid" value="${studentmanager.companyid}" style="display: none"/>
					<ul class="menu00" style="z-index:100;">
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						<li>
							<input type="checkbox" name="studentmanager.periodmanager" id="periodmanager" value="1" />学时管理
						</li>
						<li>
							<input type="checkbox" name="studentmanager.changemanager" id="changemanager" value="1" />变动管理
						</li>
						<li>
							<input type="checkbox" name="studentmanager.missedlesson" id="missedlesson" value="1" />补课管理
						</li>
						<li>
							<input type="checkbox" name="studentmanager.classmanager" id="classmanager" value="1" />班级管理
						</li>
						<li>
							<input type="checkbox" name="studentmanager.attendancemanager" id="attendancemanager" value="1" />考勤管理
						</li>
						<li>
							<input type="checkbox" name="studentmanager.awardmanager" id="awardmanager" value="1" />奖励情况
						</li>
						<li>
							<input type="checkbox" name="studentmanager.produtmanagerr" id="produtmanagerr" value="1" />产品管理
						</li>
						<li>
							<input type="button" class="input-button JQuerySubmits" style="cursor: pointer; margin-top:0px; width: 60px;" value="保存" />
						</li>
					</ul>
				</li>
			</ul>
			</form>
		</div>
		
		<div style="overflow-y:scroll;" class="gdkd">
		
		<div name="periodmanager" id="${studentmanager.periodmanager}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">学时管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box2',2,'edit')" id="mord2" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box2',2,'close')" id="mord2_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box2" style="display:none;">
          <form name="box2Form" id="box2Form" method="post">
           
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
              <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/csaddress/ea_getListAddress.jspa?address.staffID=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe2" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="changemanager" id="${studentmanager.changemanager}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
	      <tr>
	        <td height="27" class="txt03">变动管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box3',3,'edit')" id="mord3" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box3',3,'close')" id="mord3_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box3" style="display:none;">
          <form name="box3Form" id="box2Form" method="post">
           
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
              <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/csaddress/ea_getListAddress.jspa?address.staffID=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe3" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
        
        
        <div name="missedlesson" id="${studentmanager.missedlesson}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">补课管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box4',4,'edit')" id="mord4" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box4',4,'close')" id="mord4_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box4" style="display:none;">
          <form name="box4Form" id="box2Form" method="post">
           
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
              <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/csaddress/ea_getListAddress.jspa?address.staffID=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe4" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
        <div name="classmanager" dir="ltr" id="${studentmanager.classmanager}" class="showorhide" style="display: none;">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">班级管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box5',5,'edit')" id="mord5" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box5',5,'close')" id="mord5_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box5" style="display:none;">
          <form name="box3Form" id="box3Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/contact/ea_getListContact.jspa?contact.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe5" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
         <div name="attendancemanager" id="${studentmanager.attendancemanager}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">考勤管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box6',6,'edit')" id="mord6" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box6',6,'close')" id="mord6_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box6" style="display:none;">
          <form name="box4Form" id="box4Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/education/ea_getListEducation.jspa?education.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe6" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
        <div name="awardmanager" id="${studentmanager.awardmanager}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
	      <tr>
	        <td height="27" class="txt03">奖励情况</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box7',7,'edit')" id="mord7" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box7',7,'close')" id="mord7_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box7" style="display:none;">
          <form name="box4Form" id="box4Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/education/ea_getListEducation.jspa?education.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe7" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staffresume" id="${ studentmanager.produtmanagerr}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
	      <tr>
	        <td height="27" class="txt03">产品管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box8',8,'edit')" id="mord8" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box8',8,'close')" id="mord8_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box8" style="display:none;">
          <form name="box8Form" id="box5Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/precord/ea_getListPRecord.jspa?record.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe8" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
		
      </div>
    </div>
		
<script type="text/javascript">
var ip = new ImagePreview( $$("staffphoto"), $$("idImg"), {
	maxWidth: 200, maxHeight: 200, action: ""
});
ip.img.src = ImagePreview.TRANSPARENT;
ip.file.onchange = function(){ ip.preview(); 
window.setTimeout('setwh(document.getElementById("idImg"))',200);
};

function setwh(img){
	if(img.width != 102 || img.height !=126||img.fileSize > 100*1024){
	   alert("上传图片规格必须为102X126！ 大小必须小于100k");
	    photosizes = 1;
	    $("table#stafftable").find('img#idImg').attr("src", "xxx");
	    $("table#stafftable").find('img#idImg').css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='')");
	}else{
	   photosizes = 0;
	}
}

function setImageUrl(str){
	$("#hideImagePath").value=str;
}

function gotoLogin(){
	document.location= basePath + "page/ea/not_login.jsp";
}

function   setImg(img,   width,   height){ 
    var   s1   =   width/height; 
    var   s2   =   img.offsetWidth/img.offsetHeight; 
    if(s1> s2)   img.height   =   img.offsetHeight> height   ?   height   :   img.offsetHeight; 
    else     img.width   =   img.offsetWidth> width   ?   width   :   img.offsetWidth; 
}

$(function(){   
	setTimeout(function(){ 
        $("div.gdkd").css({"height":GetPageSize()[3]-350+"px"});
 	},100);
	$(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-350+"px"});
		 },100);
	}); 	
});	
		
$(function() {
	/***根据身份证获取  性别，生日 start***/
	if(showType=="add"){
		$("input#staffIdentityCard", "form#box1Form").trigger("blur");
	}
	$("input#staffIdentityCard", "form#box1Form").bind(
			"blur",
			function() {
				var cardID = $(this).attr("value");
				if (cardID.length == 18) {
					// $("select#sex","form#box1Form")[0].selectedIndex =
					// cardID.slice(14,17)%2;
					if (cardID.slice(14, 17) % 2) {// 偶数女 奇数男
						$("#sex", "form#box1Form").attr("value",
								"男");
					} else {
						$("#sex", "form#box1Form").attr("value",
								"女");
					}
					var birthday2 = cardID.slice(6, 10) + "-"
							+ cardID.slice(10, 12) + "-"
							+ cardID.slice(12, 14);
					$("#birthday", "form#box1Form").attr("value",
							birthday2);
				} else if (cardID.length == 15) {
					if (cardID.substr(14, 1) % 2) {
						$("#sex", "form#box1Form").attr("value",
								"男");
					} else {
						$("#sex", "form#box1Form").attr("value",
								"女");
					}
					// $("select#sex","form#box1Form")[0].selectedIndex =
					// cardID.substr(14,1)%2;
					var birthday2 = "19" + cardID.substr(6, 2)
							+ "-" + cardID.substr(8, 2) + "-"
							+ cardID.substr(10, 2);
					$("#birthday", "form#box1Form").attr("value",
							birthday2);

				}
			});
	/***根据身份证获取  性别，生日 end***/
				
				
	//图片预览
    $('#staffphoto').change(function(){
        $t = $("table#stafftable");
		$("table#stafftable").find('#singleShuter').hide();
                $t.find('img#idImg').show();
        $t.find('img#photo').hide();       
    });
    //图片预览END 
});
</script>
</body>
</html>