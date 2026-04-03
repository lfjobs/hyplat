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
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 社会人力页面报名调整方案31-->   
<title>人员报名列表</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_enroll.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

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
</head>

<body style="overflow: hidden;">
	<div class="content" style="width:950px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;学员管理-${param.module_title}</div>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">人员基本信息</td>
				<td align="right"><input type="button" id="newG" name="button7"value="选择人员" /></td>
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
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data"><input type="submit" name="submit" style="display:none"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table" >
					<tr class="trheight">
						<td width="12%" align="right">员工编号：</td>
						<td width="23%"><span id="staffCode">${cstaff.staffCode }</span></td>
						<td width="12%" align="right">档案编号：</td>
						<td width="23%" done0="9" done1="9"><span id="recordCode">${cstaff.recordCode }</span></td>
						<td width="30%" rowspan="4" align="center" id="phototd">
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
						<td style="display:block;border:0;">
							<input id="singleShuterphoto" type="button" style="width: 50px;" class="isHide input-button" 
								value="摄像头" /><input name="photo" id="staffphoto" class="input01 isHide"  type="file"
								style="width: 150px;" />
							<input name="cstaff.photo" type="hidden" id="photo"/>
							<input name="cstaff.staffKey" id="staffKey" type="hidden" />
						</td>
					</tr>
				<tr id="tools" style="display:table-row;border:0;height: 20px;">
					<td  align="right" colspan="5">
						<input name="cstaff.staffID" id="contactUserID" type="hidden" class="input"  size="20"/>
						<input type="button"  style="cursor: pointer; width: 80px;margin: 0px;border: 0px" class="input-button JQuerySubmit"  value="提交" />
						<input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
					</td>
				</tr>
				<c:if test="${param.docstatus=='01'&&param.studentstatus=='02'}">
					<tr>
							<td align="right">是否合格：</td>
							<td align="left" >
							<span>${param.istrues=='合格'?'合格':param.istrues=='不合格'?'不合格':'未处理'}</span>
							</td>
							<td align="right"  >
							<span>${param.istrues=='不合格'?'不合格原因':''}</span>
							</td>
							<td align="left"  >
								<span>${param.istrues=='不合格'?param.reason:''}</span>	
							</td>
							<td align="right"  >
							</td>
							<td align="right"  >
							</td>
					</tr>
				</c:if>	
				<tr>
						<td align="right" rowspan="2">导航菜单：</td>
						<td align="left" colspan="4" ><div  class="navMenu"><input type="button" value="全部" class="all"/></div></td>
				</tr>
				<tr>
					<td align="left" colspan="4" ><div  class="navMenu">
						<input type="button" value="学员联系方式" class="box3"/>
						<input type="button" value="学员地址信息" class="box2"/>
						<input type="button" value="客户学员分类" class="box11"/>
						<input type="button" value="客户学员跟踪" class="box51"/>
						<input type="button" value="呼叫信息中心" class="box14"/>
						
						
						<input type="button" value="学员办证资料" class="box13"/>
						<input type="button" value="报名单位信息" class="box53"/>
						<input type="button" value="客户学员收费" class="box5"/>
						
						
						
						<%--<input type="button" value="客户学员证件" class="box6"/>--%>
						<%--<input type="button" value="学员分班管理" class="box28"/>
						--%>
						<%--<input type="button" value="学员教室分配" class="box29"/>
						--%>
						<%--<input type="button" value="学员培训管理" class="box30"/>
						--%>
						
						<%--<input type="button" value="学员教练分车" class="box48"/>
						--%>
  <%--
  param:  theModule  describe：区别模块   parameters：培训计时 00,分车管理 01,预约培训管理02,培训管理03,接送管理 04,约考管理05,考试成绩管理06,合格管理07
  --%>					
  						<%-- marketingArchives参数是为了区分  是否  为  营销处 学员档案  跳转来源 --%>		
						<c:if test="${!fn:startsWith(param.marketingArchives, 'marketingArchives')}">
						
						<input type="button" value="学员教练信息" class="box8"/>
						
						<input type="button" value="学员培训信息" class="box9"/>
						<input type="button" value="学员考试信息" class="box10"/>
						
						<c:if test="${param.theModule=='04' }">
						<input type="button" value="客户学员接送" class="box7"/>
						</c:if>
						<c:if test="${param.theModule=='01' }">
						<input type="button" value="学员分车管理" class="box55"/>
						</c:if>
						<input type="button" value="学员车管管理" class="box56"/>
						<%--<input type="button" value="学员归档信息" class="box11"/>
						--%>
						<%--<input type="button" value="学员收集信息" class="box12"/>--%>
						<c:if test="${fn:startsWith(param.module_title, '科一')||param.module_title==null||param.educationalCategories=='01'}">
							<input type="button" value="学员报开学车" class="box47"/>
							<c:choose>
								<c:when test="${param.theModule=='00' }"><input type="button" value="科一培训计时" class="box32"/></c:when>
								<c:when test="${param.theModule=='02' }"><input type="button" value="科一预约培训" class="box31"/></c:when>
								<c:when test="${param.theModule=='03' }"><input type="button" value="学员科一培训" class="box33"/> </c:when>
								<c:when test="${param.theModule=='05' }"><input type="button" value="科一约考信息" class="box52"/></c:when>
								<c:when test="${param.theModule=='06' }"><input type="button" value="科一考试信息" class="box15"/></c:when>
								<c:when test="${param.theModule=='07' }"><input type="button" value="科一考试归档" class="box34"/></c:when>
								<c:otherwise>
								<input type="button" value="客户学员接送" class="box7"/>
								<input type="button" value="学员分车管理" class="box55"/><br/>
								<input type="button" value="科一培训计时" class="box32"/>
								<input type="button" value="科一预约培训" class="box31"/>
								<input type="button" value="学员科一培训" class="box33"/>
								<input type="button" value="科一约考信息" class="box52"/>
								<input type="button" value="科一考试信息" class="box15"/>
								<input type="button" value="科一考试归档" class="box34"/><br/>
								</c:otherwise>
							</c:choose>
							
						</c:if>
						
						<c:if test="${fn:startsWith(param.module_title, '科二')||param.module_title==null||param.educationalCategories=='02'}">
						<c:choose>
							<c:when test="${param.theModule=='00' }"><input type="button" value="科二培训计时" class="box36"/></c:when>
							<c:when test="${param.theModule=='02' }"><input type="button" value="科二预约培训" class="box35"/></c:when>
							<c:when test="${param.theModule=='03' }"><input type="button" value="学员科二培训" class="box37"/></c:when>
							<c:when test="${param.theModule=='05' }"><input type="button" value="学员科二约考" class="box16"/></c:when>
							<c:when test="${param.theModule=='06' }"><input type="button" value="学员科二考试" class="box17"/></c:when>
							<c:when test="${param.theModule=='07' }"><input type="button" value="科二考试归档" class="box38"/></c:when>
							<c:otherwise>
							<input type="button" value="科二培训计时" class="box36"/>
							<input type="button" value="科二预约培训" class="box35"/>
							<input type="button" value="学员科二培训" class="box37"/>
							<input type="button" value="学员科二约考" class="box16"/>
							<input type="button" value="学员科二考试" class="box17"/>
							<input type="button" value="科二考试归档" class="box38"/><br/>
							</c:otherwise>
						</c:choose>
						</c:if>
						
						<c:if test="${fn:startsWith(param.module_title, '科三')||param.module_title==null||param.educationalCategories=='03'}">
						<c:choose>
							<c:when test="${param.theModule=='00' }"><input type="button" value="科三培训计时" class="box40"/></c:when>
							<c:when test="${param.theModule=='02' }"><input type="button" value="科三预约培训" class="box39"/></c:when>
							<c:when test="${param.theModule=='03' }"><input type="button" value="学员科三培训" class="box41"/></c:when>
							<c:when test="${param.theModule=='05' }"><input type="button" value="学员科三约考" class="box18"/></c:when>
							<c:when test="${param.theModule=='06' }"><input type="button" value="学员科三考试" class="box19"/></c:when>
							<c:when test="${param.theModule=='07' }"><input type="button" value="科三考试归档" class="box42"/></c:when>
							<c:otherwise>
							<input type="button" value="科三培训计时" class="box40"/>
							<input type="button" value="科三预约培训" class="box39"/>
							<input type="button" value="学员科三培训" class="box41"/>
							<input type="button" value="学员科三约考" class="box18"/>
							<input type="button" value="学员科三考试" class="box19"/>
							<input type="button" value="科三考试归档" class="box42"/><br/>
							</c:otherwise>
						</c:choose>
						</c:if>
						
						<c:if test="${fn:startsWith(param.module_title, '科四')||param.module_title==null||param.educationalCategories=='04'}">
						<c:choose>
							<c:when test="${param.theModule=='00' }"><input type="button" value="科四培训计时" class="box44"/></c:when>
							<c:when test="${param.theModule=='02' }"><input type="button" value="科四预约培训" class="box43"/></c:when>
							<c:when test="${param.theModule=='03' }"><input type="button" value="学员科四培训" class="box45"/></c:when>
							<c:when test="${param.theModule=='05' }"><input type="button" value="学员科四约考" class="box20"/></c:when>
							<c:when test="${param.theModule=='06' }"><input type="button" value="学员科四考试" class="box21"/></c:when>
							<c:when test="${param.theModule=='07' }"><input type="button" value="科四考试归档" class="box46"/></c:when>
							<c:otherwise>
							<input type="button" value="科四培训计时" class="box44"/>
							<input type="button" value="科四预约培训" class="box43"/>
							<input type="button" value="学员科四培训" class="box45"/>
							<input type="button" value="学员科四约考" class="box20"/>
							<input type="button" value="学员科四考试" class="box21"/>
							<input type="button" value="科四考试归档" class="box46"/>
							</c:otherwise>
						</c:choose>
						</c:if>
						</c:if>
						</div>
						</td>
				</tr>
				</table>
			</form>
		</div>
		<div style="overflow-y: scroll;height: 300px" class="gdkd">
			<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
	      <tr>
	        <td height="27" class="txt03">学员联系方式</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box3',3,'edit')" id="mord3" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box3',3,'close')" id="mord3_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box3" style="display:none;">
          <form name="box3Form" id="box3Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/contact/ea_getListContact.jspa?contact.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe3" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">学员地址信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box2',2,'edit')" id="mord2" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box2',2,'close')" id="mord2_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box2" style="display:none;">
          <form name="box2Form" id="box2Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/csaddress/ea_getListAddress.jspa?address.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe2" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">客户学员分类</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box11',11,'edit')" id="mord11" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box11',11,'close')" id="mord11_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box11" style="display:none;">
          <form name="box11Form" id="box11Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/customermanage/ea_getCustomerList.jspa?frameid=mainframe11&status1=00&staffid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe11" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box51">
	      <tr>
	        <td height="27" class="txt03">客户学员跟踪</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box51',51,'edit')" id="mord51" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box51',51,'close')" id="mord51_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box51" style="display:none;">
          <form name="box51Form" id="box51Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/track/ea_getTrackById.jspa?status=0&trackrelationID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe51" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
	      <tr>
	        <td height="27" class="txt03">呼叫信息中心</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box14',14,'edit')" id="mord14" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box14',14,'close')" id="mord14_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box14" style="display:none;">
          <form name="box14Form" id="box14Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/tel/tel_infoDealCenter.jspa?staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe14" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
	      <tr>
	        <td height="27" class="txt03">学员办证资料</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box13',13,'edit')" id="mord13" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box13',13,'close')" id="mord13_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box13" style="display:none;">
          <form name="box13Form" id="box13Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/credentials/ea_getListCredentials.jspa?credentials.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe13" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box53">
	      <tr>
	        <td height="27" class="txt03">报名单位信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box53',53,'edit')" id="mord53" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box53',53,'close')" id="mord53_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box53" style="display:none;">
          <form name="box53Form" id="box53Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentCompany">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentCompanyList.jspa?dtDrivingAllInformation.dataTitle=04&dtDrivingAllInformation.staffID=" 
					src="" name="mainframe53" width="100%" marginwidth="0" 
				    height="180px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe53"  border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">客户学员收费</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box5',5,'edit')" id="mord5" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box5',5,'close')" id="mord5_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box5" style="display:none;">
          <form name="box5Form" id="box5Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentShargeList.jspa?dtDrivingAllInformation.dataTitle=05&dtDrivingAllInformation.staffID=" 
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
        
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">客户学员证件</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box6',6,'edit')" id="mord6" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box6',6,'close')" id="mord6_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box6" style="display:none;">
          <form name="box6Form" id="box6Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentPapersList.jspa?dtDrivingAllInformation.dataTitle=06&dtDrivingAllInformation.staffID=" 
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
        --%>
        <%-- marketingArchives参数是为了区分  是否  为  营销处 学员档案  跳转来源 --%>		
		<c:if test="${!fn:startsWith(param.marketingArchives, 'marketingArchives')}">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
	      <tr>
	        <td height="27" class="txt03">客户学员接送</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box7',7,'edit')" id="mord7" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box7',7,'close')" id="mord7_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box7" style="display:none;">
          <form name="box7Form" id="box7Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 830px;overflow: scroll;">
				<iframe url="ea/enroll/ea_getStudentShuttleList.jspa?dtDrivingAllInformation.dataTitle=07&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="1100px" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe7" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box55">
	      <tr>
	        <td height="27" class="txt03">学员分车管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box55',55,'edit')" id="mord55" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box55',55,'close')" id="mord55_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box55" style="display:none;">
          <form name="box55Form" id="box55Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getFenCheShuttleList.jspa?dtDrivingAllInformation.dataTitle=09&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe55" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box56">
	      <tr>
	        <td height="27" class="txt03">学员车管管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box56',56,'edit')" id="mord56" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box56',56,'close')" id="mord56_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box56" style="display:none;">
          <form name="box56Form" id="box56Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="/ea/cheguan/ea_getListDrivingCheGuan.jspa?drivingDealCheGuan.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe56" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
	      <tr>
	        <td height="27" class="txt03">学员教练信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box8',8,'edit')" id="mord8" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box8',8,'close')" id="mord8_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box8" style="display:none;">
          <form name="box8Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<%--<iframe url="ea/enroll/ea_getStudentCoachList.jspa?dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe8" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
				--%><iframe url="ea/driving/ea_getDrivingList.jspa?extensionStaffCoach=extensionStaffCoach&dtDrivingPrincipal.studentid=" 
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
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box9">
	      <tr>
	        <td height="27" class="txt03">学员培训信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box9',9,'edit')" id="mord9" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box9',9,'close')" id="mord9_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box9" style="display:none;">
          <form name="box9Form" id="box9Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentTrainList.jspa?dtDrivingAllInformation.dataTitle=09&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe9" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box10">
	      <tr>
	        <td height="27" class="txt03">学员考试信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box10',10,'edit')" id="mord10" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box10',10,'close')" id="mord10_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box10" style="display:none;">
          <form name="box10Form" id="box10Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentExamList.jspa?dtDrivingAllInformation.dataTitle=10&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe10" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">学员归档信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box11',11,'edit')" id="mord11" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box11',11,'close')" id="mord11_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box11" style="display:none;">
          <form name="box11Form" id="box11Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/archive/ea_getArchiveList.jspa?extensionStaffCoach=extensionStaffCoach&catemodule=global&cstaff.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe11" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        --%>
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box12">
	      <tr>
	        <td height="27" class="txt03">学员收集信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box12',12,'edit')" id="mord12" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box12',12,'close')" id="mord12_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box12" style="display:none;">
          <form name="box12Form" id="box12Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentCollectList.jspa?dtDrivingAllInformation.dataTitle=10&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe12" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        --%>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box47">
	      <tr>
	        <td height="27" class="txt03">学员报开学车</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box47',47,'edit')" id="mord47" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box47',47,'close')" id="mord47_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box47" style="display:none;">
          <form name="box47Form" id="box47Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=04&title=01&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" style="overflow: scroll;"
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe47" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box48">
	      <tr>
	        <td height="27" class="txt03">学员教练分车</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box48',48,'edit')" id="mord48" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box48',48,'close')" id="mord48_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box48" style="display:none;">
          <form name="box48Form" id="box48Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?extensionStaffCoach=extensionStaffStudent&docstatus=01&studentstatus=05&title=02&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe48"  framespacing="0"  noresize="noResize"  
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        --%>
        
        
        
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box28">
	      <tr>
	        <td height="28" class="txt03">学员分班管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box28',28,'edit')" id="mord28" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box28',28,'close')" id="mord28_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box28" style="display:none;">
          <form name="box28Form" id="box28Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe28" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
         --%>
         
         <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box29">
	      <tr>
	        <td height="29" class="txt03">学员教师分配</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box29',29,'edit')" id="mord29" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box29',29,'close')" id="mord29_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box29" style="display:none;">
          <form name="box29Form" id="box29Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe29" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        --%>
        
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box30">
	      <tr>
	        <td height="30" class="txt03">学员培训管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box30',30,'edit')" id="mord30" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box30',30,'close')" id="mord30_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box30" style="display:none;">
          <form name="box30Form" id="box30Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe30" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        --%>
        
        <!-- 科一 -->
        <c:if test="${fn:startsWith(param.module_title, '科一')||param.module_title==null}">
        	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box31">
	      <tr>
	        <td height="27" class="txt03">科一预约培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box31',31,'edit')" id="mord31" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box31',31,'close')" id="mord31_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box31" style="display:none;" >
          <form name="box31Form" id="box31Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=05&title=01&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" style="overflow: scroll;"
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe31" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box32">
	      <tr>
	        <td height="27" class="txt03">科一培训计时</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box32',32,'edit')" id="mord32" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box32',32,'close')" id="mord32_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box32" style="display:none;">
          <form name="box32Form" id="box32Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentTimingList.jspa?dtDrivingAllInformation.subjectStatus=01&dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe32" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box33">
	      <tr>
	        <td height="27" class="txt03">学员科一培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box33',33,'edit')" id="mord33" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box33',33,'close')" id="mord33_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box33" style="display:none;">
          <form name="box33Form" id="box33Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=03&title=02&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe33" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box52">
	      <tr>
	        <td height="27" class="txt03">科一约考信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box52',52,'edit')" id="mord52" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box52',52,'close')" id="mord52_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box52" style="display:none;">
          <form name="box52Form" id="box52Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=02&title=05&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe52" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box15">
	      <tr>
	        <td height="27" class="txt03">科一考试信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box15',15,'edit')" id="mord15" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box15',15,'close')" id="mord15_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box15" style="display:none;">
          <form name="box15Form" id="box15Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getStatisticsList.jspa?docstatus=01&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe15" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box34">
	      <tr>
	        <td height="27" class="txt03">科一考试归档</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box34',34,'edit')" id="mord34" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box34',34,'close')" id="mord34_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box34" style="display:none;">
          <form name="box34Form" id="box34Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=01&studentstatus=07&title=05&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe34" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        </c:if>
        <!-- 科二模块 -->
        <c:if test="${fn:startsWith(param.module_title, '科二')||param.module_title==null}">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box35">
	      <tr>
	        <td height="27" class="txt03">科二预约培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box35',35,'edit')" id="mord35" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box35',35,'close')" id="mord35_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box35" style="display:none;">
          <form name="box35Form" id="box35Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=02&title=06&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe35" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box36">
	      <tr>
	        <td height="27" class="txt03">科二培训计时</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box36',36,'edit')" id="mord36" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box36',36,'close')" id="mord36_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box36" style="display:none;">
          <form name="box36Form" id="box36Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentTimingList.jspa?dtDrivingAllInformation.subjectStatus=02&dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe36" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box37">
	      <tr>
	        <td height="27" class="txt03">学员科二培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box37',37,'edit')" id="mord37" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box37',37,'close')" id="mord37_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box37" style="display:none;">
          <form name="box37Form" id="box37Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=03&title=02&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe37" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box16">
	      <tr>
	        <td height="27" class="txt03">学员科二约考</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box16',16,'edit')" id="mord16" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box16',16,'close')" id="mord16_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box16" style="display:none;">
          <form name="box16Form" id="box16Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=04&title=08&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe16" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box17">
	      <tr>
	        <td height="27" class="txt03">学员科二考试</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box17',17,'edit')" id="mord17" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box17',17,'close')" id="mord17_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box17" style="display:none;">
          <form name="box17Form" id="box17Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getStatisticsList.jspa?docstatus=02&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe17" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box38">
	      <tr>
	        <td height="27" class="txt03">科二考试归档</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box38',38,'edit')" id="mord38" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box38',38,'close')" id="mord38_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box38" style="display:none;">
          <form name="box38Form" id="box38Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=02&studentstatus=07&title=08&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe38" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </c:if>
        <!-- 科三模块 -->
        <c:if test="${fn:startsWith(param.module_title, '科三')||param.module_title==null}">
         <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box39">
	      <tr>
	        <td height="27" class="txt03">科三预约培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box39',39,'edit')" id="mord39" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box39',39,'close')" id="mord39_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box39" style="display:none;">
          <form name="box39Form" id="box39Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=02&title=09&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0" frameborder="0" 
				    id="mainframe39" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
         <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box40">
	      <tr>
	        <td height="27" class="txt03">科三培训计时</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box40',40,'edit')" id="mord40" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box40',40,'close')" id="mord40_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box40" style="display:none;">
          <form name="box40Form" id="box40Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentTimingList.jspa?dtDrivingAllInformation.subjectStatus=03&dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0" frameborder="0" 
				    id="mainframe40" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
         <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box41">
	      <tr>
	        <td height="27" class="txt03">学员科三培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box41',41,'edit')" id="mord41" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box41',41,'close')" id="mord41_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box41" style="display:none;">
          <form name="box41Form" id="box41Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=03&title=02&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0" frameborder="0" 
				    id="mainframe41" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box18">
	      <tr>
	        <td height="27" class="txt03">学员科三约考</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box18',18,'edit')" id="mord18" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box18',18,'close')" id="mord18_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box18" style="display:none;">
          <form name="box18Form" id="box18Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=04&title=11&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0" frameborder="0" 
				    id="mainframe18" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box19">
	      <tr>
	        <td height="27" class="txt03">学员科三考试</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box19',19,'edit')" id="mord19" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box19',19,'close')" id="mord19_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box19" style="display:none;">
          <form name="box19Form" id="box19Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getStatisticsList.jspa?docstatus=03&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe19" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
         <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box42">
	      <tr>
	        <td height="27" class="txt03">科三考试归档</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box42',42,'edit')" id="mord42" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box42',42,'close')" id="mord42_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box42" style="display:none;">
          <form name="box42Form" id="box42Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=03&studentstatus=07&title=11&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0" frameborder="0" 
				    id="mainframe42" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </c:if>
        <!-- 科四模块 -->
        <c:if test="${fn:startsWith(param.module_title, '科四')||param.module_title==null}">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box43">
	      <tr>
	        <td height="27" class="txt03">科四预约培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box43',43,'edit')" id="mord43" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box43',43,'close')" id="mord43_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box43" style="display:none;">
          <form name="box43Form" id="box43Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=02&title=12&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe43" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box44">
	      <tr>
	        <td height="27" class="txt03">科四培训计时</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box44',44,'edit')" id="mord44" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box44',44,'close')" id="mord44_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box44" style="display:none;">
          <form name="box44Form" id="box44Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/enroll/ea_getStudentTimingList.jspa?dtDrivingAllInformation.subjectStatus=04&dtDrivingAllInformation.dataTitle=08&dtDrivingAllInformation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe44" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box45">
	      <tr>
	        <td height="27" class="txt03">学员科四培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box45',45,'edit')" id="mord45" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box45',45,'close')" id="mord45_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box45" style="display:none;">
          <form name="box45Form" id="box45Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=03&title=02&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe45" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box20">
	      <tr>
	        <td height="27" class="txt03">学员科四约考</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box20',20,'edit')" id="mord20" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box20',20,'close')" id="mord20_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box20" style="display:none;">
          <form name="box20Form" id="box20Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=04&title=14&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe20" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box21">
	      <tr>
	        <td height="27" class="txt03">学员科四考试</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box21',21,'edit')" id="mord21" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box21',21,'close')" id="mord21_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box21" style="display:none;">
          <form name="box21Form" id="box21Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getStatisticsList.jspa?docstatus=04&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe21" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box46">
	      <tr>
	        <td height="27" class="txt03">科四考试归档</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box46',46,'edit')" id="mord46" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box46',46,'close')" id="mord46_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box46" style="display:none;">
          <form name="box46Form" id="box46Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/driving/ea_getDrivingList.jspa?docstatus=04&studentstatus=07&title=14&extensionStaffCoach=extensionStaffStudent&dtDrivingPrincipal.studentid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe46" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </c:if>
        </c:if>
        <%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box22">
	      <tr>
	        <td height="27" class="txt03">科三路考归档</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box22',22,'edit')" id="mord22" 
	        	class="mord" style="color:#0066FF">修改</a>&nbsp;&nbsp;&nbsp;<a href="#" onclick="changemenu('box22',22,'close')" id="mord22_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box22" style="display:none;">
          <form name="box22Form" id="box22Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table studentSharge">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/archive/ea_getArchiveList.jspa?extensionStaffCoach=extensionStaffStudent&catemodule=global&cstaff.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="500px" marginheight="0"  frameborder="0" 
				    id="mainframe22" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>--%>
        
        
        
        </div>
    </div>
	<%------------------------------------社会人力选择------------------------------------%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择人员
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td  align="right">
								人员姓名：
							</td>
							<td >
								<input name="staffName" class="input" id="staffName" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td align="right">
								身份证：
							</td>
							<td >
								<input name="staffIdentityCard" class="input" id="staffIdentityCard" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdpeople"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		<s:token></s:token>
		</form>
		 
		<%--<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 2.5%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
		</form>
		--%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
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
        $("div.gdkd").css({"height":GetPageSize()[3]-400+"px"});
 	},100);
	 $(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-400+"px"});
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
	
var scrollPos="";
window.onscroll=function(){
	 
	if (typeof window.pageYOffset != 'undefined')    //针对Netscape 浏览器
    { 
         scrollPos = window.pageYOffset; 
     } 
     else if (typeof document.compatMode != 'undefined' &&   document.compatMode != 'BackCompat')
     { 
         scrollPos = document.documentElement.scrollTop; 
     } 
   else if (typeof document.body != 'undefined') 
    { 
        scrollPos = document.body.scrollTop; 
	} 
};

function GetPageSize(){
    var xScroll, yScroll;
    if (window.innerHeight  &&  window.scrollMaxY) { 
        xScroll = document.body.scrollWidth;
        yScroll = window.innerHeight + window.scrollMaxY;
    } else if (document.body.scrollHeight > document.body.offsetHeight){
        xScroll = document.body.scrollWidth;
        yScroll = document.body.scrollHeight;
    } else {
        xScroll = document.body.offsetWidth;
        yScroll = document.body.offsetHeight;
    }
    var windowWidth=0, windowHeight=0;
    if (self.innerHeight) {
        windowWidth = self.innerWidth;
        windowHeight = self.innerHeight;
    } else if (document.documentElement  &&  document.documentElement.clientHeight) {
        windowWidth = document.documentElement.clientWidth;
        windowHeight = document.documentElement.clientHeight;
    } else if (document.body) {
        windowWidth = document.body.clientWidth;
        windowHeight = document.body.clientHeight;
    } 
    if(yScroll < windowHeight){
        pageHeight = windowHeight;
    } else { 
        pageHeight = yScroll;
    }
    if(xScroll < windowWidth){ 
        pageWidth = windowWidth;
    } else {
        pageWidth = xScroll;
    }
    arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight); 
    return arrayPageSize;
}
</script>
</body>
</html>