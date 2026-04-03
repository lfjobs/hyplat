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
<title>建档管理</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/cstaff_collect_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var staffID = '${cstaff.staffID }';
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType ='${showType}'; 
		var roleID = '${account.roleID}'; 
		var select = 1;
		var staffName = '${cstaff.staffName }';
		var baokaixuecheguan='${param.baokaixuecheguan}';
    var str="";
    var temp = "";
</script>
</head>

<body>
	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;建档管理</div>
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
				<%--<td align="right"><a href="#" onclick="changemenu('box1',1,'edit')" id="mord1"
					class="" style="color:#0066FF;">添加人员</a>
				</td>
			--%></tr>
		</table>
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data">
				<s:token/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td width="12%" align="right">员工编号：</td>
						<td width="23%">
							<span class="isShow" id="staffCode">${cstaff.staffCode }</span>
						</td>
						<td width="12%" align="right">档案编号：</td>
						<td width="23%" done0="9" done1="9">
							<span class="isShow" id="recordCode">${cstaff.recordCode }</span></td>
						<td width="30%" rowspan="5" align="center" id="phototd">
							<img name="photos" id="photo" style="display: none;" src="xxx" 
								onload="setImg(this,   102,   126)" />
							<img id="idImg" style="display: none;" src="xxx" />
							<br />
							图片大小：102 x 126
							<a id="PhotoName"></a>
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right">姓名：</td>
						<td done0="10" done1="10">
							<span class="isShow" id="staffName">${cstaff.staffName }</span>
							<span id="staffID" style="display:none">${cstaff.staffID }</span>
							</td>
						<td align="right">曾用名：</td>
						<td done0="11" done1="11">
							<span class="isShow" id="usedNmae">${cstaff.usedNmae }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">性别：</td>
						<td done0="12" done1="12"><span class="isShow" id="sex">${cstaff.sex }</span></td>
						<td align="right">出生日期：</td>
						<td done0="13" done1="13"><span class="isShow" id="birthday">${cstaff.birthday }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">民族：</td>
						<td>
							<span class="isShow" id="nation">${cstaff.nation }</span>
							</td> 
						<td align="right">籍贯：</td>
						<td>
							<span class="isShow" id="nativePlace">${cstaff.nativePlace }</span>
						</td>
					</tr>
					<%--<tr>
						<td style="height:30" align="right">最常用联系方式：</td>
						<td colspan="3">
						<span class="isShow" id="reference">${cstaff.reference }</span>
								</td>
					</tr>
					--%><tr>
						<td style="height:30" align="right">身份证号码：</td>
						<td><span class="isShow staffIdentityCard" id="staffIdentityCard">${cstaff.staffIdentityCard }</span>
						</td>
						<td align="right">护照号：</td>
						<td><span class="isShow" id="passportNum">${cstaff.passportNum }</span>
							</td>
						<td style="display:block;border:0;">
							<input name="cstaff.photo" type="hidden" id="photo" value="${cstaff.photo}"/>
							<input name="cstaff.staffID" id="staffID" type="hidden" value="${cstaff.staffID}"/>
							<input name="cstaff.staffKey" id="staffKey" type="hidden" value="${cstaff.staffKey}"/>
						</td>
					</tr>
					<tr id="tools" style="display:table-row;border:0;">
						<td style="height:30" align="right" colspan="5">
							<input type="button" onclick="toSave('box1Form','/ea/stafftrack/sajax_ea_saveStaffTrack.jspa')" class="input-button JQuerySubmit isHide" style="cursor: pointer; width: 80px;" value="提交" />
						</td>
					</tr>
				</table>
			</form>
		</div>
			<form name="cstaffcollectForm" id="cstaffcollectForm" method="post">
				<input type="submit" name="submit" style="display: none" />
			</form>
		<div class="menu01" >
			<form name="humanForm" id="humanForm" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
					<input name="humancollect.humancollectkey" value="${humancollect.humancollectkey}" style="display: none"/>
					<input name="humancollect.humancollectid" value="${humancollect.humancollectid}" style="display: none"/>
					<input name="humancollect.companyid" value="${humancollect.companyid}" style="display: none"/>
					<input name="humancollect.cname" value="${humancollect.cname}" style="display: none"/>
					<input name="humancollect.ctime" value="${humancollect.ctime}" style="display: none"/>
					<ul class="menu00" style="z-index:100;">
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						<li>
							<input type="checkbox" name="humancollect.staffaddress" id="staffaddress" value="1" />地址管理
						</li>
						<li>
							<input type="checkbox" name="humancollect.staffcontact" id="staffcontact" value="1" />联系方式
						</li>
						<li>
							<input type="checkbox" name="humancollect.staffpersonl" id="staffpersonl" value="1" />电话记录
						</li>
						<li>
							<input type="checkbox" name="humancollect.stafftrack" id="stafftrack" value="1" />咨询跟踪
						</li>
						<%--<li>
							<input type="checkbox" name="humancollect.stafftrackl" id="stafftrackl" value="1" />咨询报表
						</li>
						
						--%><li>
							<input type="checkbox" name="humancollect.classification" id="classification" value="1" />客户分类
						</li>
						<li>
							<input type="checkbox" name="humancollect.branchCompany" id="branchCompany" value="1" />报名单位
						</li>
						<li>
							<input type="checkbox" name="humancollect.subordinate" id="subordinate" value="1" />所属单位
						</li>
						<li>
							<input type="checkbox" name="humancollect.callCenter" id="callCenter" value="1" />呼叫中心
						</li>
						<li>
							<input type="checkbox" name="humancollect.documents" id="documents" value="1" />个人证件
						</li>
						<li>
							<input type="checkbox" name="humancollect.interested" id="interested" value="1" />客户兴趣
						</li>
						<li>
							<input type="checkbox" name="humancollect.potential" id="potential" value="1" />潜在客户
						</li>
						<li>
							<input type="checkbox" name="humancollect.source" id="source" value="1" />客户来源
						</li>
						<li>
							<input type="checkbox" name="humancollect.Individual" id="Individual" value="1" />客户档案
						</li>
						<li>
							<input type="button" class="input-button JQuerySubmits" style="cursor: pointer; margin-top:0px; width: 60px;" value="保存" />
						</li>
					</ul>
				</li>
			</ul>
			</form>
		</div>
		
		<div style="overflow-y:scroll;"  class="gdkd">
		
		<div name="staffaddress" id="${humancollect.staffaddress}" class="showorhide"  style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">地址管理</td>
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
        
        <div name="staffcontact" dir="ltr" id="${humancollect.staffcontact}" class="showorhide"  style="display: none" >
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
	      <tr>
	        <td height="27" class="txt03">联系方式</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box3',3,'edit')" id="mord3" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box3',3,'close')" id="mord3_close"
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
        </div>
        
        <div name="staffpersonl" id="${humancollect.staffpersonl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">电话记录</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box4',4,'edit')" id="mord4" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box4',4,'close')" id="mord4_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box4" style="display:none;">
          <form name="box4Form" id="box4Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/tel/tel_telInOutList.jspa?inOutType=1&foreignKeyID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe4" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="stafftrack" id="${humancollect.stafftrack}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box51">
	      <tr>
	        <td height="27" class="txt03">咨询跟踪</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box51',51,'edit')" id="mord51" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box51',51,'close')" id="mord51_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box51" style="display:none;">
          <form name="box51Form" id="box51Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/track/ea_getTrackById.jspa?status=0&trackrelationID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe51" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>        
        <%--
        <div name="staff6" id="${humancollect.stafftrackl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">咨询报表</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box6',6,'edit')" id="mord6" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box6',6,'close')" id="mord6_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box6" style="display:none;">
          <form name="box6Form" id="box6Form" method="post">
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/collectpersonal/ea_getTracklist.jspa?" 
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
        --%>
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">收费信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box5',5,'edit')" id="mord5" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box5',5,'close')" id="mord5_close"
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
        
        
        
        <div name="staff11" id="${humancollect.classification}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">客户分类管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box11',11,'edit')" id="mord11" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box11',11,'close')" id="mord11_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box11" style="display:none;">
          <form name="box11Form" id="box11Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/customermanage/ea_getCustomerList.jspa?frameid=mainframe11&status1=00&staffid=" 
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
        </div>
        
        <div name="staff53" id="${humancollect.branchCompany}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box53">
	      <tr>
	        <td height="27" class="txt03">报名单位信息</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box53',53,'edit')" id="mord53" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box53',53,'close')" id="mord53_close"
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
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe53"  border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff12" id="${humancollect.subordinate}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box12">
	      <tr>
	        <td height="27" class="txt03">所属单位管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box12',12,'edit')" id="mord12" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box12',12,'close')" id="mord12_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box12" style="display:none;">
          <form name="box12Form" id="box12Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/contactcompany/ea_getListContactCompany.jspa?flexbutton=flexbutton&staffID=" 
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
        </div>
        
        <div name="staff14" id="${humancollect.callCenter}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
	      <tr>
	        <td height="27" class="txt03">呼叫信息中心</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box14',14,'edit')" id="mord14" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box14',14,'close')" id="mord14_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box14" style="display:none;">
          <form name="box14Form" id="box14Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/tel/tel_infoDealCenter.jspa?type=customer&staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="100px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe14" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff13" id="${humancollect.documents}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
	      <tr>
	        <td height="27" class="txt03">个人证件管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box13',13,'edit')" id="mord13" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box13',13,'close')" id="mord13_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box13" style="display:none;">
          <form name="box13Form" id="box13Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/credentials/ea_getListCredentials.jspa?customer=customer&credentials.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe13" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff15" id="${humancollect.interested}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box15">
	      <tr>
	        <td height="27" class="txt03">客户产品兴趣</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box15',15,'edit')" id="mord15" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box15',15,'close')" id="mord15_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box15" style="display:none;">
          <form name="box15Form" id="box15Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/customermanage/ea_getCustomerList.jspa?frameid=mainframe15&status1=03&staffid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe15" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff16" id="${humancollect.potential}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box16">
	      <tr>
	        <td height="27" class="txt03">潜在客户需求</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box16',16,'edit')" id="mord16" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box16',16,'close')" id="mord16_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box16" style="display:none;">
          <form name="box16Form" id="box16Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/customermanage/ea_getCustomerList.jspa?frameid=mainframe16&status1=01&staffid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe16" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff17" id="${humancollect.source}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box17">
	      <tr>
	        <td height="27" class="txt03">客户来源管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box17',17,'edit')" id="mord17" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box17',17,'close')" id="mord17_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
		<div id="box17" style="display:none;">
          <form name="box17Form" id="box17Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/customermanage/ea_getCustomerList.jspa?frameid=mainframe17&status1=02&staffid=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe17" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff18" id="${humancollect.individual}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box18">
	      <tr>
	        <td height="27" class="txt03">个人客户档案</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box18',18,'edit')" id="mord18" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box18',18,'close')" id="mord18_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
        
        <!-- 以下子模块为报开学车管部分 -->
        <c:if test="${param.baokaixuecheguan=='baokaixuecheguan'}">
        <div name="staff7" id="${humancollect.stafftrackl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
	      <tr>
	        <td height="27" class="txt03">交车管费报表</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box7',7,'edit')" id="mord7" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box7',7,'close')" id="mord7_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
        
        <div name="staff8" id="${humancollect.stafftrackl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
	      <tr>
	        <td height="27" class="txt03">已报车管报表</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box8',8,'edit')" id="mord8" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box8',8,'close')" id="mord8_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
        
        <div name="staff9" id="${humancollect.stafftrackl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box9">
	      <tr>
	        <td height="27" class="txt03">未报车管报表</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box9',9,'edit')" id="mord9" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box9',9,'close')" id="mord9_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
        
        <div name="staff10" id="${humancollect.stafftrackl}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box10">
	      <tr>
	        <td height="27" class="txt03">档案管理</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box10',10,'edit')" id="mord10" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box10',10,'close')" id="mord10_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
        </c:if>
    	</div>
	</div>
	<%-----------------公司在职员工---------%>
		<form name="Staffform1" id="Staffform1" method="post">
		<div id="bankJqm1" class="jqmWindow1"
			style="width: 95%; height: 400px;position:absolute; display: none; left: 2.5%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<iframe name="daoRu1" id="daoRu1" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd1"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan1" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
		</form>	
	
	
	<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
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
<script type="text/javascript">

$(function(){  
	setTimeout(function(){ 
        $("div.gdkd").css({"height":GetPageSize()[3]-295+"px"});
 	},100);
	$(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-295+"px"});
		 },100);
	}); 	
});	
		
$(function() {
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