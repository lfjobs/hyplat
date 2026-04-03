<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/"; 
	String filepath = request.getSession().getServletContext().getRealPath("/");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${showType}个人客户调查</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/crmcustomer/crmcustomer_info.js"></script>

<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var customerid = '${crmCustomer.customerid}';		
		var personIdentityCard;			
		var select = 1;
		var photosizes = 0;
		var status = '${crmCustomer.status}';   
		var showType = '${showType}'; 
</script>
</head>

<body style="overflow: auto;">
	<div class="content" style="width:850px;height: auto;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;${oper}个人客户调查 </div>
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
				<td height="27" class="txt03">基本信息</td>
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
			<form name="box1Form" id="box1Form" method="post" enctype="multipart/form-data">
				<s:token/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td style="height:30" align="right">员工编号：</td>
					  <td done0="10" done1="10"><input name="crmCustomer.customercode" value="${crmCustomer.customercode}"
						class="input" id="customercode" readonly="readonly" size="10"  style="width:100%;height:100%;border:0;"/>
				      <span class="isHide">${crmCustomer.customercode }</span>
					  <span id="customerid" name="" style="display:none">${crmCustomer.customerid}</span></td>
					  <span id="customerkey" name="" style="display:none">${crmCustomer.customerkey}</span></td>
						<td width="12%" align="right"><font color="red">*</font>姓名：</td>
					  <td width="23%"><input name="crmCustomer.customername" style="width:100%;height:100%;border: 0;"
							class="put4" id="customername" size="10" value="${crmCustomer.customername }" />
				      <span class="isHide">${crmCustomer.customername }</span></td>
						<td width="30%" rowspan="8" align="center" id="phototd">
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
							<img name="photos" id="photo" style="display: none;" src="xxx" 
								onload="setImg(this,   102,   126)" />
							<img id="idImg" style="display: none; width: 126px; height: 102px; " src="xxx" />
							<br />
							图片大小：102 x 126
							<a id="PhotoName"></a>
							<input id="singleShuterphoto" type="button" style="width: 50px;" class="isHide input-button" 
								value="摄像头" /><input name="photo" id="staffphoto" class="input01 isHide"  type="file"
								style="width: 150px;" />
							<input name="crmCustomer.photo" type="hidden" id="photo" value="${crmCustomer.photo}"/>
							<input name="crmCustomer.staffid" id="staffid" type="hidden" value="${crmCustomer.staffid}"/>
							<input name="crmCustomer.customerkey" id="customerkey" type="hidden" value="${crmCustomer.customerkey}"/>						</td>
					</tr>
					<tr>
					  <td style="height:30" align="right">性别：</td>
					  <td><input name="crmCustomer.sex" type="radio" value="男" />男
                          <input name="crmCustomer.sex" type="radio" value="女" />女
						  <span class="isShow">${crmCustomer.sex }</span></td>
					  <td style="height:30" align="right">证件类型：</td>
					  <td done0="12" done1="12"><span class="isShow" id="idtype" >${crmCustomer.idtype }</span>
					    <select name="crmCustomer.idtype">
                          <option value="01">身份证</option>
                          <option value="02">军官证</option>
                          <option value="03">护照</option>
                        </select></td>
				  </tr>
					<tr>
						<td style="height:30" align="right">证件号码：</td>
						<td><input name="crmCustomer.identitycard" value="${crmCustomer.identitycard}"
							class="input" id="crmCustomer.identitycard" size="10"  style="width:100%;height:100%;border: 0;"/></td>
						<td style="height:30" align="right"><font color="red">*</font>意向产品：</td>
						<td done0="12" done1="12"><input name="crmCustomer.productid" value="${crmCustomer.productid}"
							class="isHide" id="crmCustomer.productid" size="10"  style="width:100%;height:100%;border: 0;"/>
							<input name="productname" value="${productname}"
							class="put4" id="productname" size="10"  style="width:100%;height:100%;border: 0;"/>							</td>
					</tr>
					<tr>
					  <td align="right"><span style="height:30">联系电话：</span></td>
					  <td done0="13" done1="13"><input name="crmCustomer.reference" class="input isHide" style="width:100%;height:100%;border: 0;"
							id="reference" size="14"  value="${crmCustomer.reference}"/>
				      <span class="isShow">${crmCustomer.reference }</span></td>
					  <td style="height:30" align="right">曾用名：</td>
					  <td><input name="crmCustomer.usednmae" style="width:100%;height:100%;border: 0;"
							class="input" id="crmCustomer.usednmae" size="10" value="${crmCustomer.usednmae }" />					    
                        <span class="isShow">${crmCustomer.usednmae }</span></td>
				  </tr>
					<tr>
						<td align="right">推荐人：</td>
						<td done0="13" done1="13"><input name="crmCustomer.staffid" value="${crmCustomer.staffid}"
							class="isHide" id="crmCustomer.staffid" size="10"  style="width:100%;height:100%;border: 0;"/>
							<input name="staffname" value="${staffname}"
							class="input" id="staffname" size="10"  style="width:100%;height:100%;border: 0;"/></td>
						<td style="height:30" align="right">出生日期：</td>
						<td><input name="crmCustomer.birthday" value="${crmCustomer.birthday }"
							class="input isHide" id="birthday" readonly="readonly" size="10"  style="width:100%;height:100%;border: 0;"/>
					    <span class="isShow">${crmCustomer.birthday }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">所属地区：</td>
						<td><input name="crmCustomer.area" class="input" style="width:100%;height:100%;border: 0;"
							id="crmCustomer.area" size="14"  value="${crmCustomer.area}"/>
                          <span class="isShow">${crmCustomer.area}</span></td>
						<td style="height:30" align="right">客户阶段：</td>
						<td><select name="crmCustomer.status">
                          <option value="01">已成交</option>
                          <option value="02">意向客户</option>
                        </select></td>
					</tr>
					<tr>
						<td align="right">地址：</td>
						<td colspan="3" >	 
					        	<input name="crmCustomer.address" class="input" style="width:100%;height:100%;border: 0;"
							id="crmCustomer.address" size="14"  value="${crmCustomer.address}"/>
					        	<span class="isShow">${crmCustomer.address}</span></td>
					</tr>
					<tr>
						
						<td style="height:30" align="right">备注：</td>
						<td colspan="3">
						  <input name="crmCustomer.ddesc" class="input" style="width:100%;height:100%;border: 0;"
							id="crmCustomer.ddesc" size="14"  value="${crmCustomer.ddesc}"/>
							<span class="isShow">${crmCustomer.ddesc}</span></td>
					</tr>
					<tr id="tools" style="display:table-row;border:0;">
					<td style="height:30" align="right" colspan="5">
						<input type="button" onclick="toSave('box1Form','/ea/marketingCrmCustomer/ea_saveOrUpdateCustomer.jspa')" class="input-button JQuerySubmit" style="cursor: pointer; width: 80px;" value="提交" />					</td>
				</tr>
				</table>
			</form>
		</div>
		
		<div class="menu01" >
			<form name="crmCustForm" id="crmCustForm" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
					<input name="crmCustMenu.customermenukey" value="${crmCustMenu.customermenukey}" style="display: none"/>
					<input name="crmCustMenu.customermenuid" value="${crmCustMenu.customermenuid}" style="display: none"/>
					<input name="crmCustMenu.companyid" value="${crmCustMenu.companyid}" style="display: none"/>
					<ul class="menu00" style="z-index:100;">
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.jd" id="jd" value="1" />进度维护
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.yxcp" id="yxcp" value="1" />意向产品
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.bjjl" id="bjjl" value="1" />报价记录
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.jpzl" id="jpzl" value="1" />竞品资料
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.shgl" id="shgl" value="1" />社会关联
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.yjsz" id="yjsz" value="1" />预警设置
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.lxfs" id="lxfs" value="1" />联系方式
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.hyzy" id="hyzy" value="1" />行业职业
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.jypx" id="jypx" value="1" />教育培训
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.dzhz" id="dzhz" value="1" />地址汇总
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.xqah" id="xqah" value="1" />兴趣爱好
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.zjzs" id="zjzs" value="1" />证件证书
						</li>
						<li>
							<input type="checkbox" name="crmCustMenu.jntc" id="jntc" value="1" />技能特长
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
		
		<div name="jd" id="${crmCustMenu.jd}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">进度维护</td>
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
				<iframe url="ea/marketingCrmCustomer/ea_getListJd.jspa?crmCustomer.customerkey=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="yxcp" dir="ltr" id="${crmCustMenu.yxcp}" class="showorhide" style="display: none;">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
	      <tr>
	        <td height="27" class="txt03">意向产品</td>
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
				<iframe url="ea/marketingCrmCustomer/ea_getListYxcp.jspa?crmCustomer.customerkey=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="bjjl" id="${crmCustMenu.bjjl}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">报价记录</td>
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
				<iframe url="ea/marketingCrmCustomer/ea_getListBjjl.jspa?crmCustomer.customerkey=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="jpzl" id="${crmCustMenu.jpzl}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">竞品资料</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box5',5,'edit')" id="mord5" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box5',5,'close')" id="mord5_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box5" style="display:none;">
          <form name="box5Form" id="box5Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/marketingCrmCustomer/ea_getListJpzl.jspa?crmCustomer.customerkey=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="shgl" id="${crmCustMenu.shgl}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">社会关联</td>
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
				<iframe url="ea/fmember/ea_getListFMember.jspa?member.staffID=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="yjsz" id="${crmCustMenu.yjsz}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
	      <tr>
	        <td height="27" class="txt03">预警设置</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box7',7,'edit')" id="mord7" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box7',7,'close')" id="mord7_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box7" style="display:none;">
          <form name="box7Form" id="box7Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/pcondition/ea_getListPCondition.jspa?condition.staffID=" 
					src="" name="main" height="390px" width="100%" marginwidth="0" 
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
        
        <div name="lxfs" id="${crmCustMenu.lxfs}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
	      <tr>
	        <td height="27" class="txt03">联系方式</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box8',8,'edit')" id="mord8" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box8',8,'close')" id="mord8_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box8" style="display:none;">
          <form name="box8Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/political/ea_getListPolitical.jspa?political.staffID=" 
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
        
        <div name="hyzy" id="${crmCustMenu.hyzy}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box9">
	      <tr>
	        <td height="27" class="txt03">行业职业</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box9',9,'edit')" id="mord9" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box9',9,'close')" id="mord9_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box9" style="display:none;">
          <form name="box9Form" id="box9Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/encourage/ea_getListEncourage.jspa?encourage.staffID=" 
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
        </div>
        
        <div name="jypx" id="${crmCustMenu.jypx}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box10">
	      <tr>
	        <td height="27" class="txt03">教育培训</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box10',10,'edit')" id="mord10" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box10',10,'close')" id="mord10_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box10" style="display:none;">
          <form name="box10Form" id="box10Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/punishment/ea_getListPunishment.jspa?punishment.staffID=" 
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
        </div>
        
        <div name="dzhz" id="${crmCustMenu.dzhz}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">地址汇总</td>
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
				<iframe url="ea/insurance/ea_getListInsurance.jspa?insurance.staffID=" 
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
        
        <div name="xqah" id="${crmCustMenu.xqah}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box12">
	      <tr>
	        <td height="27" class="txt03">兴趣爱好</td>
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
				<iframe url="ea/investigation/ea_getListInvestigation.jspa?investigation.staffID=" 
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
        
        <div name="zjzs" id="${crmCustMenu.zjzs}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
	      <tr>
	        <td height="27" class="txt03">证件证书</td>
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
				<iframe url="ea/credentials/ea_getListCredentials.jspa?credentials.staffID=" 
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
        
        <div name="jntc" id="${crmCustMenu.jntc}" class="showorhide" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
	      <tr>
	        <td height="27" class="txt03">技能特长</td>
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
				<iframe url="ea/documentation/ea_getListDocumentation.jspa?documentation.staffID=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe14" border="0" framespacing="0" noresize="noResize" 
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
	//if(img.width != 102 || img.height !=126||img.fileSize > 100*1024){
	//   alert("上传图片规格必须为102X126！ 大小必须小于100k");
	//    photosizes = 1;
	//    $("table#stafftable").find('img#idImg').attr("src", "xxx");
	//    $("table#stafftable").find('img#idImg').css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='')");
	//}else{
	//   photosizes = 0;
	//}
	 photosizes = 0;
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
