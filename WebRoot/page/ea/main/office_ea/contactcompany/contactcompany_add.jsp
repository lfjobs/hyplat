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
<title>社会往来单位-添加页面</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<%-- <script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script> --%>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/contactcompany/contactcompany_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var showType ='${showType}'; 
		var select = 1;
		var companyName = '${contactCompany.companyName }';
		var ccompanyID = '${contactCompany.ccompanyID }';
	    var str="";
	    var temp = "";
	    var flag='${flag}';
	    var contactresourcekey='${contactresource.contactresourcekey}';
	    var contactresourceid='${contactresource.contactresourceid}';
	    </script>
</head>

<body>
	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;社会往来单位管理 </div>
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
				<td height="27" class="txt03">单位基本信息</td>
				<td align="right"><a href="#" onclick="changemenu('box1',1,'edit')" id="mord1"
					class="mord" style="color:#0066FF;">修改</a><a href="#" onclick="changemenu('box1',1,'close')" id="mord1_close"
					class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data">
				<s:token/>
				<s:hidden name="contactCompany.jjPath"></s:hidden>
				<table id="contacttable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td width="12%" align="right"><font color="red">*</font>单位名称：</td>
						<td width="43%">
							<input type="text" id="companyName" class="input2 put3 isHide"
															name="contactCompany.companyName" value="${contactCompany.companyName }"/>
							<span class="isShow">${contactCompany.companyName }</span>
						</td>
						<td width="15%" align="right">单位电话：</td>
						<td width="40%">
							<input name="contactCompany.companyTel" id="companyTel" type="text" class="input phone isHide" size="20" value="${contactCompany.companyTel }"/>
							<span class="isShow">${contactCompany.companyTel }</span>
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">单位负责人：</td>
						<td>
							<input name="contactCompany.cresponsible" id="cresponsible" type="text" class="input isHide" size="20" value="${contactCompany.cresponsible }"/>
							<span class="isShow">${contactCompany.cresponsible }</span>	
						</td>
						<td width="12%" align="right">单位负责人电话：</td>
						<td>
							<input name="contactCompany.responsibleTel" id="responsibleTel" type="text" class="input cellphone isHide" size="20" value="${contactCompany.responsibleTel }"/>
							<span class="isShow">${contactCompany.responsibleTel }</span>		
						</td>
					</tr>
					<tr class="trheight" id="indus">
						<td width="12%" align="right">行业类别：</td>
						<td id="indus1">
							<s:select list="%{typelist}" id="industryType"
								listKey="codeID" listValue="codeValue"
								theme="simple"></s:select>
								<span class="isShow">${contactCompany.industryType }</span>		
<!-- 							<a href="#" class="mord isHide" id="mord2" -->
<!-- 								onclick="toCCode('scode20110106hfjes5ucxp0000000003','#industryType','#box1Form')">新添</a> -->
							<select id="industryType1" style="display:none;"></select>
						</td>
						<td width="12%" align="right">备注：</td>
						<td>
							<input name="contactCompany.remark" id="remark"
								type="text" class="input isHide"  maxlength="256"   value="${contactCompany.remark }"/>
							<span class="isShow">${contactCompany.remark }</span>		
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">公司宗旨：</td>
						<td>
							<input name="contactCompany.comPurpose"  id="compurpose" type="text" class="input isHide" size="50" value="${contactCompany.comPurpose }"/>
							<span class="isShow"><s:if test="contactCompany.comPurpose==null||contactCompany.comPurpose==''">无</s:if><s:else>${contactCompany.comPurpose }</s:else></span>
						</td>
						<td width="12%" align="right">公司LOGO：</td>
						<td><input id="123" name="photo" size="5" type="file" class="input isHide" onchange="check()"/>
							<span class="isShow"><s:if test="contactCompany.logoPath==null||contactCompany.logoPath==''">无</s:if></span>
                             <s:else>
                               <span id="logoPath" onclick="lookImage('${contactCompany.logoPath}')"><a href="javascript:void(0)">查看</a></span>
                            </s:else></span>
                            <input type="hidden" name="contactCompany.logoPath" value="${contactCompany.logoPath }"/>
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">品牌信息：</td>
						<td colspan="3">
							<textarea style="width:470px;height:50px" id="tta1" name="contactCompany.brandInfo">${contactCompany.brandInfo }</textarea>
							<span class="isShow"><s:if test="contactCompany.brandInfo==null||contactCompany.brandInfo==''">暂无</s:if><s:else>${contactCompany.brandInfo }</s:else></span>
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">经营范围：</td>
						<td colspan="3">	
							<textarea style="width: 470px; height: 50px" id="tta"
									name="contactCompany.dealIn" id="dealIn" >${contactCompany.dealIn }</textarea>
							<span class="isShow"><s:if test="contactCompany.dealIn==null||contactCompany.dealIn==''">暂无</s:if><s:else>${contactCompany.dealIn }</s:else></span>	
						</td>
					</tr>
					<tr class="trheight">
						<td width="12%" align="right">公司地址：</td>
						<td colspan="3" class="JQueryaddress">
							<input id="companyAddr" type="hidden" class="input"
								name="contactCompany.companyAddr" value="${contactCompany.companyAddr }"/>
							<input name="contactCompany.address" id="address"
								type="hidden" value="${contactCompany.address }"/>
							<span style="display: none;" id="address">${contactCompany.address }</span>		
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;">
							</select>
							<!-- <option>选择省</option>-->
							<select name="addressCity" id="city" number='1'
								style="width: 110px;">
							</select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px;">
							</select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px;">
							</select>
							<select name="addressVillage" id="addressVillage"
								number='4' style="width: 110px;">
							</select>
							<select name="addressCommunity" id="addressCommunity"
								number='5' style="width: 110px;">
							</select>
							<%--
							<select name="addressFloor" id="addressFloor" number='6'
								style="width: 110px;">
							</select>
							<select name="addressLayer" id="addressLayer" number='7'
								style="width: 110px;">
							</select>
							<select name="addressSize" id="addressSize" number='8'
								style="width: 110px;">
							</select>--%>
							<span class="isShow">${contactCompany.companyAddr }</span>	
						</td>
					</tr>
				<tr class="trheight">
					<td width="12%" align="right">公司网址：</td>
						<td colspan="3">
							<input name="contactCompany.companyWeb"  id="companyWeb" type="text" class="input isHide" size="50" value="${contactCompany.companyWeb }"/>
							
							<span class="isShow"><s:if test="contactCompany.companyWeb==null||contactCompany.companyWeb==''">暂无</s:if><s:else>${contactCompany.companyWeb }</s:else></span>
					</td>
				</tr>	
				<tr class="trheight">
					<td width="12%" align="right">公司性质：</td>
					<td>
					<s:select list="%{#request.typelist}" id="comPro" name="contactCompany.comPro"  
								listKey="goodsName" listValue="goodsName"
								theme="simple"></s:select>
                      <span class="isShow">${contactCompany.comPro}</span>		
							
					</td>
					<td width="12%" align="right">公司规模：</td>
					<td>
				    <s:select list="%{#request.scalelist}" id="comScale" name="contactCompany.comScale" 
								listKey="goodsName" listValue="goodsName"
								theme="simple"></s:select>
						  <span class="isShow">${contactCompany.comScale}</span>			
					</td>

                </tr>

                    <tbody id="j_body" style="display: none;">
                    <tr class="trheight">
                        <td width="12%" align="right"></font>经营范围：</td>
                        <td width="43%">
                            <%--<input type="text"  class="input2  isHide"
                                   name="tcompany.busiscope" />--%>
                                <select  name="tcompany.busiscope" style="width:140px;">
                                    <option value="A1">A1</option>
                                    <option value="A2">A2</option>
                                    <option value="A3">A3</option>
                                    <option value="B1">B1</option>
                                    <option value="B2">B2</option>
                                    <option value="C1">C1</option>
                                    <option value="C2">C2</option>
                                    <option value="C3">C3</option>
                                    <option value="C4">C4</option>
                                    <option value="C5">C5</option>
                                    <option value="D">D</option>
                                    <option value="E">E</option>
                                    <option value="F">F</option>
                                    <option value="M">M</option>
                                    <option value="N">N</option>
                                    <option value="P">P</option>
                                </select>

                           <%-- <span class="isShow"> ${tcompany.busiscope}</span>--%>
                        </td>
                        <td width="15%" align="right">经营状态：</td>
                        <td width="40%">
                            <%--<input name="tcompany.busistatus"  type="text" class="input  isHide" size="20"/>--%>
                                <select  name="tcompany.busistatus" style="width:140px;">
                                    <option value="1">营业</option>
                                    <option value="2">停业</option>
                                    <option value="3">整改</option>
                                    <option value="4">停业整顿</option>
                                    <option value="5">歇业</option>
                                    <option value="6">注销</option>
                                    <option value="9">其他</option>
                                </select>

                           <%-- <span class="isShow">${tcompany.busistatus} </span>--%>
                        </td>
                    </tr>
					<tr class="trheight">
						<td width="12%" align="right"></font>教练员总数：</td>
						<td width="43%">
							<input type="text"  class="input2  isHide"
								   name="tcompany.coachnumber" />
							<span class="isShow"> ${tcompany.coachnumber}</span>
						</td>
						<td width="15%" align="right">教练场总面积：</td>
						<td width="40%">
							<input name="tcompany.praticefield"  type="text" class="input  isHide" size="20"/>
							<span class="isShow">${tcompany.praticefield} </span>
						</td>
					</tr>
                    <tr class="trheight">
                        <td width="12%" align="right"></font>初次发证日期：</td>
                        <td width="43%">
                            <input type="text"  class="input2  isHide"
                                   name="tcompany.firstIssueDate" onfocus="daytime(this);" />
                            <span class="isShow">${tcompany.firstIssueDate} </span>
                        </td>
                        <td width="15%" align="right">培训机构简称：</td>
                        <td width="40%">
                            <input name="tcompany.shortname"  type="text" class="input  isHide" size="20"/>
                            <span class="isShow"> ${tcompany.shortname}</span>
                        </td>
                    </tr>

                    <tr class="trheight">
                        <td width="12%" align="right"></font>统一社会信用代码：</td>
                        <td width="43%">
                            <input type="text"  class="input2  isHide"
                                   name="tcompany.creditcode"  />
                            <span class="isShow">${tcompany.creditcode} </span>
                        </td>
                        <td width="15%" align="right">邮政编码：</td>
                        <td width="40%">
                            <input name="tcompany.postcode"  type="text" class="input  isHide" size="20"/>
                            <span class="isShow">${tcompany.postcode} </span>
                        </td>
                    </tr>
                    <tr class="trheight">
                        <td width="12%" align="right"></font>教练车总数：</td>
                        <td width="43%">
                            <input type="text"  class="input2  isHide"
                                   name="tcompany.tracarnum"  />
                            <span class="isShow">${tcompany.tracarnum} </span>
                        </td>
                        <td width="15%" align="right">经营许可日期：</td>
                        <td width="40%">
                            <input name="tcompany.licetime"  type="text" class="input  isHide" size="20" onfocus="daytime(this);"/>
                            <span class="isShow"> ${tcompany.licetime}</span>
                        </td>
                    </tr>

                    <tr class="trheight">
                        <td width="15%" align="right">经营截止日期：</td>
                        <td width="40%">
                            <input name="tcompany.busisEndDate"  type="text" class="input  isHide" size="20" onfocus="daytime(this);"/>
                            <span class="isShow">${tcompany.busisEndDate} </span>
                        </td>
                        <td width="15%" align="right">全国统一编码：</td>
                        <td width="40%">
                            <input name="tcompany.inscode"  type="text" class="input  isHide" size="20" />
                            <span class="isShow">${tcompany.inscode} </span>
                        </td>
                    </tr>
                    <tr class="trheight">
                       <%-- <td width="15%" align="right">所属行政区域：</td>
                        <td width="40%">
                            <input name="tcompany.district"  type="text" class="input  isHide" size="20" id="quyu" />
                            <span class="isShow"> </span>
                        </td>--%>

                        <td width="12%" align="right">所属行政区域：</td>
                        <td id="t_xingzheng" name="tcompany.district">
                            <s:select list="%{tbSysGeography}" id="xingzheng"
                                      listKey="geKey" listValue="geoName"
                                      theme="simple"></s:select>
                            <span class="isShowXZ"></span>
                            <!-- 							<a href="#" class="mord isHide" id="mord2" -->
                            <!-- 								onclick="toCCode('scode20110106hfjes5ucxp0000000003','#industryType','#box1Form')">新添</a> -->
                            <select   id="xingzheng1" style="display:none;"></select>
                            <select   name="tcompany.district" id="xingzheng2" style="display:none;"></select>
                        </td>
                    </tr>
					<tr class="trheight">
						<td width="12%" align="right"></font>税务登记证编号：</td>
						<td width="43%">
							<input type="text"  class="input2  isHide"
								   name="tcompany.taxregcer"  />
							<span class="isShow">${tcompany.taxregcer} </span>
						</td>
						<td width="15%" align="right">联系电话：</td>
						<td width="40%">
							<input name="tcompany.phone"  type="text" class="input  isHide" size="20"/>
							<span class="isShow"> ${tcompany.phone}</span>
						</td>
					</tr>

                    </tbody>
				<tr id="tools" style="display:table-row;border:0;">
					<input name="contactCompany.ccompanyKey" id="ccompanyKey"
						type="hidden" class="input" size="20" value="${contactCompany.ccompanyKey }"/>
					<input name="contactCompany.ccompanyID" id="ccompanyID"
						type="hidden" class="input" size="20" value="${contactCompany.ccompanyID }"/>
					<span style="display: none;" id="ccompanyID">${contactCompany.ccompanyID }</span>		
					<td style="height:30" align="right" colspan="5">
						<input type="hidden" value="${flag }" name="flag"/>
					   <input type="hidden" value="${contactCompany.webstatus}" name="contactCompany.webstatus"/>
						<input type="button" onclick="toSave('box1Form','/ea/contactcompany/sajax_ea_saveContactCompany.jspa')" class="input-button JQuerySubmit isHide" style="cursor: pointer; width: 80px;" value="提交" />
					</td>
				</tr>
				
				</table>
			</form>
		</div>
		
		<div class="menu01" >
			<form name="contactcomForm" id="contactcomForm" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
 					<input name="contactresource.contactresourcekey" value="${contactresource.contactresourcekey}" style="display: none"/>  
 					<input name="contactresource.contactresourceid" value="${contactresource.contactresourceid}" style="display: none"/> 
					<input name="contactresource.companyid" value="${contactresource.companyid}" style="display: none"/>
					<input name="contactresource.cname" value="${contactresource.cname}" style="display: none"/>
					<input name="contactresource.ctime" value="${contactresource.ctime}" style="display: none"/>
					<ul class="menu00" style="z-index:100; text-align: left;" >
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						
						<li class="conf">
							<input type="checkbox" name="contactresource.ccomconf" id="ccomconf" value="1" />公司首页
						</li>
							
						<li class="other">
							<input type="checkbox" name="contactresource.certificate" id="certificate" value="1" />证件管理
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contacttype" id="contacttype" value="1" />联系方式
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.registration" id="registration" value="1" />银行帐号
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactcom" id="contactcom" value="1" />个人列表
						</li>
						<!-- <li>
							<input type="checkbox" name="contactresource.certificate" id="certificate" value="1" />单位地址管理
						</li>
						<li>
							<input type="checkbox" name="contactresource.contacttype" id="contacttype" value="1" />单位联系方式管理
						</li>
						<li>
							<input type="checkbox" name="contactresource.registration" id="registration" value="1" />客户单位人员管理
						</li> 
						<li>
							<input type="checkbox" name="contactresource.contactcom" id="contactcom" value="1" />单位证件管理
						</li>-->						
						<li class="other">
							<input type="checkbox" name="contactresource.contactinout" id="contactinout" value="1" />呼入呼出
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactzxgztj" id="contactzxgztj" value="1" />咨询跟踪统计
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactzxgz" id="contactzxgz" value="1" />咨询跟踪
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactscope" id="contactscope" value="1" />经营范围
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactannual" id="contactannual" value="1" />年产值
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactoperation" id="contactoperation" value="1" />经营状况调研
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactglzah" id="contactglzah" value="1" />管理着爱好调研
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactqysshy" id="contactqysshy" value="1" />企业所属行业
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactkhcpzy" id="contactkhcpzy" value="1" />客户产品资源
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactkhjbhy" id="contactkhjbhy" value="1" />客户级别会员
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactkhgzfw" id="contactkhgzfw" value="1" />客户跟踪服务
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactkhxqdy" id="contactkhxqdy" value="1" />客户需求调研
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactphone" id="contactphone" value="1" />电话记录
						</li>
						<li class="other">
							<input type="checkbox" name="contactresource.contactimg" id="contactimg" value="1" />图片管理
						</li>
					
						<li>
							<input type="button" class="input-button JQuerySubmits" style="cursor: pointer; margin-top:0px; width: 60px;" value="保存" />
						</li>
					
					</ul>
				</li>
			</ul>
			</form>
		</div>		
		<div style="overflow-y:scroll;" class="gdkd" >
		 <div name="ccomconf" id="${contactresource.ccomconf}" class="showorhide conf1" style="display: none;">
        	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box23">
	      		<tr>
	        		<td height="27" class="txt03">公司首页</td>
	        		<td align="right"><a href="javascript:" onclick="changemenu('box23',23,'edit')" id="mord23" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box23',23,'close')" id="mord23_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        	</td>
	     	 </tr>
		</table>
        <div id="box23" style="display:none;">
          <form name="box23Form" id="box23Form" method="post">
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/ccomconf/ea_getCconConfList.jspa?ccomConf.ccompanyId=" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe23" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>       
        </div>

		<!--flag为web只显示公司首页配置  -->

		<div name="certificate" id="${contactresource.certificate}" class="showorhide other1" style="display: none;">

        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">证件管理</td>
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
				<iframe url="ea/certificate/ea_getaCertificateList.jspa?certificate.ccompanyID=" 
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
        

        
        <div name="contacttype" dir="ltr" id="${contactresource.contacttype}" class="showorhide other1" style="display: none;">
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
				<iframe url="ea/contacttype/ea_getContactTypeList.jspa?contactType.ccompanyID=" 
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
        
        <div name="registration" id="${contactresource.registration}" class="showorhide other1" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">银行帐号</td>
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
				<iframe url="ea/registration/ea_getListRegistration.jspa?registration.ccompanyID=" 
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
        
        <div name="contactcom" id="${contactresource.contactcom}" class="showorhide other1" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">个人列表</td>
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
			 <div style="width: 819px;height:218px;overflow:scroll;">
				<iframe url="ea/cs/ea_getPerson.jspa?companyID=" 
					src="" name="main" width="980px" marginwidth="0" 
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
        

         <%-- <div name="staffresume" id="${contactresource.contactcom}" class="showorhide other1" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">单位地址管理</td>
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
				<iframe url="ea/cs/ea_getPerson.jspa?companyID=" 
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
        </div> --%>
 
         <%-- <div name="staffresume" id="${contactresource.contactcom}" class="showorhide other1" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
	      <tr>
	        <td height="27" class="txt03">单位联系方式管理</td>
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
				<iframe url="ea/cs/ea_getPerson.jspa?companyID=" 
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
        </div> --%>


         <%-- <div name="staffresume" id="${contactresource.contactcom}" class="showorhide other1" style="display: none;">
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
	      <tr>
	        <td height="27" class="txt03">客户单位人员管理</td>
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
				<iframe url="ea/cs/ea_getPerson.jspa?companyID=" 
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
        </div> --%>

       

         
         <div name="contactinout" dir="ltr"  class="showorhide other1" id="${contactresource.contactinout}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box10">
	      <tr>
	        <td height="27" class="txt03">电话呼入呼出<br /></td>
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
				<iframe url="ea/tel/tel_telInOutCompanyList.jspa?foreignKeyID="+childopertionID
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
        
        <div name="contactzxgztj" dir="ltr"  class="showorhide other1" id="${contactresource.contactzxgztj}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">咨询跟踪统计<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box11',11,'edit')" id="mord11" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box11',11,'close')" id="mord11_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box11" style="display:none;">
          <form name="box11Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/collectunit/ea_getTracklist.jspa?"
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
        
        <div name="contactzxgz" dir="ltr"  class="showorhide other1" id="${contactresource.contactzxgz}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box51">
	      <tr>
	        <td height="27" class="txt03">咨询跟踪<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box51',51,'edit')" id="mord51" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box51',51,'close')" id="mord51_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box51" style="display:none;">
          <form name="box51Form" id="box9Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/track/ea_getTrackById.jspa?trackrelationID="
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
		 <div name="contactscope" dir="ltr" id="${contactresource.contactscope}" class="showorhide other1" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box9">
	      <tr>
	        <td height="27" class="txt03">经营范围管理<br /></td>
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
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>

        <div name="contactannual" dir="ltr"  class="showorhide other1" id="${contactresource.contactannual}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
	      <tr>
	        <td height="27" class="txt03">年产值管理<br /></td>
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
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactoperation" dir="ltr"  class="showorhide other1" id="${contactresource.contactoperation}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
	      <tr>
	        <td height="27" class="txt03">经营状况调研管理<br /></td>
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
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactglzah" dir="ltr"  class="showorhide other1" id="${contactresource.contactglzah}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box15">
	      <tr>
	        <td height="27" class="txt03">管理者爱好调研管理<br /></td>
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
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactqysshy" dir="ltr"  class="showorhide other1" id="${contactresource.contactqysshy}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box16">
	      <tr>
	        <td height="27" class="txt03">企业所属行业管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box16',16,'edit')" id="mord16" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box16',16,'close')" id="mord16_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box16" style="display:none;">
          <form name="box8Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactkhcpzy" dir="ltr"  class="showorhide other1" id="${contactresource.contactkhcpzy}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box17">
	      <tr>
	        <td height="27" class="txt03">客户产品资源分享<br /></td>
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
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactkhjbhy" dir="ltr"  class="showorhide other1" id="${contactresource.contactkhjbhy}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box18">
	      <tr>
	        <td height="27" class="txt03">客户级别会员管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box18',18,'edit')" id="mord18" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box18',18,'close')" id="mord18_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box18" style="display:none;">
          <form name="box18Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactkhgzfw" dir="ltr"  class="showorhide other1" id="${contactresource.contactkhgzfw}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box19">
	      <tr>
	        <td height="27" class="txt03">客户跟踪服务管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box19',19,'edit')" id="mord19" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box19',19,'close')" id="mord19_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box19" style="display:none;">
          <form name="box19Form" id="box8Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			<div style="width: 100%;">
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="contactkhxqdy" dir="ltr"  class="showorhide other1" id="${contactresource.contactkhxqdy}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box20">
	      <tr>
	        <td height="27" class="txt03">客户需求调研管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box20',20,'edit')" id="mord20" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box20',20,'close')" id="mord20_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box20" style="display:none;">
          <form name="box20Form" id="box20Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
        <div name="contactphone" dir="ltr"  class="showorhide other1" id="${contactresource.contactphone}" style="display: none">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box21">
	      <tr>
	        <td height="27" class="txt03">客户电话记录管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box21',21,'edit')" id="mord21" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box21',21,'close')" id="mord21_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box21" style="display:none;">
          <form name="box21Form" id="box21Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>	
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>



		<div name="contactimg" dir="ltr"  class="showorhide other1" id="${contactresource.contactimg}" style="display: none;" >
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box22">
	      <tr>
	        <td height="27" class="txt03">单位图片管理<br /></td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box22',22,'edit')" id="mord22" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box22',22,'close')" id="mord22_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        <div id="box22" style="display:none;">
          <form name="box22Form" id="box22Form" method="post">
            
             <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
             <tr><td>
             <input type="submit" name="submit" style="display: none" />
			 <div style="width: 100%;">
				<iframe url="ea/contactimg/ea_findItem.jspa?cimg.ccompanyID="
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe22" border="0" framespacing="0" noresize="noResize" 
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
    <%--行业类别--%>
	<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input class="Max" id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>	
	<!-- 地址添加 -->
	<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%;"
			id="newdistrict">
			<div class="drag">
				添加地域
			</div>
			<table>
				<tr align="center">
					<td align="right">
						&nbsp;&nbsp;&nbsp;地域名字：
					</td>
					<td>
						<input id="districtNames" />
						&nbsp;&nbsp;
						<span style="color: red">*按地域区分组</span>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedistrict"
					value="确定" />
				<input type="button" class="input-button JQueryreturn2" value="取消" />
			</div>
		</div>	
<script type="text/javascript">

$(function(){   
	setTimeout(function(){ 
        $("div.gdkd").css({"height":GetPageSize()[3]-180+"px"});
 	},100);
	$(window).resize(function(){ 		
		 setTimeout(function(){ 
		        $("div.gdkd").css({"height":GetPageSize()[3]-180+"px"});
		 },100);
	}); 	
});	
		
</script>

<script type="text/javascript">
        window.check=function(){
            var input = document.getElementById("123");
            if(input.files){
                //读取图片数据
                var f = input.files[0];
                var reader = new FileReader();
                reader.onload = function (e) {
                    var data = e.target.result;
                    //加载图片获取图片真实宽度和高度
                    var image = new Image();
                    image.onload=function(){
                        var width = image.width;
                        var height = image.height;
                        //alert(width+'======'+height+"====="+f.size);
                        if(f.size>1024*1024){
                        	alert("公司logo的大小不能大于1024KB!请调整图片大小后上传!");
                        	$("#123").val("");
                        }else if(image.width!="45"||image.height!="45"){
							alert("公司logo的尺寸应为45*45!请调整图片大小后上传!");
							image.src="";
							$("#123").val("");
                        }
                    };
                    image.src= data;
                };
                reader.readAsDataURL(f);
            }else{
                var image = new Image();
                image.onload =function(){
                    var width = image.width;
                    var height = image.height;
                    var fileSize = image.fileSize;
                };

                image.src = input.value;

            }

        }

        var xingye='${contactCompany.industryType}';
        if(xingye.indexOf("驾校")>=0) {
                $("#j_body").show();
                $("#mainframe").attr("height", 632 + "px");

         } else {
            $("#j_body").hide();
        }
      $('#industryType1').change(function () {
        var t = $("#industryType1 option:selected").text();
          if(t.indexOf("驾校")>=0) {
              $("#j_body").show();
              $("#mainframe").attr("height", 632 + "px");

          } else {
              $("#j_body").hide();
          }
      })


        // 二级行业
        $("#xingzheng").change(function(){
            var xingzhengs= $("#xingzheng").find("option:selected").text();
            $("#xingzheng1").show();
            var urld= basePath + "/ea/contactcompany/sajax_ea_getChengshi.jspa" ;
            $.ajax({
                url : encodeURI(urld),
                type : "post",
                async : true,
                dataType : "json",
                data : {"xingzhengs":xingzhengs},
                success : function cbf(data){
                    var member=eval("("+data+")");
                    var list=member.industryList;
                    if(list==null){
                        return;
                    }else{
                        $("#xingzheng1").empty();
                        $td=$("#xingzheng1");
                        for(var i=0;i<list.length;i++){
                            $td.append("<option value='"+list[i].geoBh+"'>"+list[i].geoName+"</option>");
                        }


                    }
                },
                error : function cbf(data){
                    alert("数据加载失败！");
                }
            });
        });


        // 三级行业
        $("#xingzheng1").change(function(){
            var xingzhengs= $("#xingzheng1").find("option:selected").text();
            $("#xingzheng2").show();
            var urld= basePath + "/ea/contactcompany/sajax_ea_getChengshi.jspa" ;
            $.ajax({
                url : encodeURI(urld),
                type : "post",
                async : true,
                dataType : "json",
                data : {"xingzhengs":xingzhengs},
                success : function cbf(data){
                    var member=eval("("+data+")");
                    var list=member.industryList;
                    if(list==null){
                        return;
                    }else{
                        $("#xingzheng2").empty();
                        $td=$("#xingzheng2");
                        for(var i=0;i<list.length;i++){
                            $td.append("<option value='"+list[i].geoBh+"'>"+list[i].geoName+"</option>");
                        }


                    }
                },
                error : function cbf(data){
                    alert("数据加载失败！");
                }
            });
        });
    </script>

</body>
</html>