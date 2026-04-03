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
<title>客户分类报表--添加修改页面</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/productDesign_add.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
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
		var notoken = 0;
	    var str="";
	    var temp = "";
	    var treeid;
	    var treename;
	    var tree;
	    var showType='${showType}';
	    var ppID='${productDesign.ppID}';
	    var goodsID='';	    
</script>
</head>

<body>
	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;客户分类报表</div>
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
				<td height="27" class="txt03">客户产品信息</td>
				<td height="20" align="right" colspan="4" >
				<c:if test="${showType=='add'}">
				<input type="button" id="shuju" value="产品选择" />
				</c:if>
				</td>
			</tr>
		</table>
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<s:token/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr >
						<td width="12%" align="right">产品编号：</td>
						<td width="23%">
							<span class="isShow" id="goodsCoding">${goodsManage.goodsCoding }</span>
						</td>
						<td width="12%" align="right">产品名称：</td>
						<td width="23%" done0="9" done1="9">
							<span class="isShow" id="goodsName">${goodsManage.goodsName }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">单位：</td>
						<td done0="10" done1="10">
							<span class="isShow" id="variableID">${goodsManage.goodsvariable }</span>
							</td>
						<td align="right">数量：</td>
						<td done0="11" done1="11">
						<c:if test="${showType=='edit'}">
									<span class="isShow" id="quantity">${productDesign.quantity }</span>
						</c:if>
						<c:if test="${showType=='add'}">
							<input name="productDesign.quantity" class="jisuan put3 isNaN" 
							id="quantity"  style="margin-left: 2px;width: 50px;" />
						</c:if>	
						</td>
					</tr>
					<tr>
						<td style="height:30" align="right">重量：</td>
						<td done0="12" done1="12">
						<c:if test="${showType=='edit'}">
									<span class="isShow" id="weight">${productDesign.weight }</span>
						</c:if>
						<c:if test="${showType=='add'}">
							<input name="productDesign.weight"  id="weight" style="margin-left: 2px;width: 50px" class="isNaN"/>
						</c:if>	
						</td>
						<td align="right">规格：</td>
						<td done0="13" done1="13"><span id="acquiesceStandard">${goodsManage.acquiesceStandard }</span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">单价：</td>
						<td>
							<c:if test="${showType=='edit'}">
									<span class="isShow" id="price">${productDesign.price }</span>
						</c:if>
						<c:if test="${showType=='add'}">
						<input name="productDesign.price" class="jisuan put3 isNaN" id="price" 
									style="margin-left: 2px;width: 50px" />
						</c:if>			
							</td> 
						<td align="right">金额：</td>
						<td>
							<c:if test="${showType=='edit'}">
									<span class="isShow" id="money">${productDesign.money } </span>
						</c:if>
						<c:if test="${showType=='add'}">
						<input name="productDesign.money"  id="money" style="margin-left: 2px;width: 50px"   />
						</c:if>		
						</td>
					</tr>
					<tr>
							<td  align="right">
								附产品说明书：
							</td>
							<td>
								<c:if test="${showType=='edit'}">
									<a href="#" class="accessoriesUrl1">查看附件</a>
									<input name="productDesign.manualUrl" id="manualUrl" type="hidden" value="${productDesign.manualUrl}"/>
								</c:if>
								<c:if test="${showType=='add'}">
									<input class=" accessoriesUrl1" type="button" value="文档编辑" />
									<input name="productDesign.manualUrl" id="manualUrl" type="hidden" />
								</c:if>	
							</td>
							<td align="right">
								附产品宣传：
							</td>
							<td>
								<c:if test="${showType=='edit'}">
									<a href="#" class="accessoriesUrl2">查看附件</a>
									<input name="productDesign.propagandaUrl" id="propagandaUrl" type="hidden" value="${productDesign.propagandaUrl}"/>
								</c:if>
								<c:if test="${showType=='add'}">
									<input class="accessoriesUrl2" type="button" value="文档编辑" />
									<input name="productDesign.propagandaUrl" id="propagandaUrl" type="hidden" />
								</c:if>		
							</td>
					</tr>
					<tr>
							<td  align="right">
								附公司文件：
							</td>
							<td>
								<c:if test="${showType=='edit'}">
									<a href="#" class="accessoriesUrl3">查看附件</a>
									<input name="productDesign.fileUrl" id="fileUrl" type="hidden" value="${productDesign.fileUrl}"/>
								</c:if>
								<c:if test="${showType=='add'}">
									<input class="accessoriesUrl3" type="button" value="文档编辑" />
									<input name="productDesign.fileUrl" id="fileUrl" type="hidden" />
								</c:if>	
								<input type="hidden" name="productDesign.ppID" id="ppID" />
								<input name="productDesign.goodsID" type="hidden" id="goodsID" value="${productDesign.goodsID}"/>
								<input name="productDesign.goodsName" type="hidden" id="goodsName" />
							</td>
						</tr>
					<c:if test="${showType=='add'}">
					<tr >
						<td style="height:30" align="right" colspan="5">
							<input type="button" id="tosave" style="cursor: pointer; width: 80px;" value="提交" />
							<input type="hidden" name="title" value="title" />
						</td>
					</tr>
					</c:if>	
				</table>
			</form>
		</div>
			<form name="cstaffcollectForm" id="cstaffcollectForm" method="post">
				<input type="submit" name="submit" style="display: none" />
			</form>
		<div class="menu01" >
			<form name="customersForms" id="customersForms" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
					<input name="customersForms.customersFormskey" value="${customersForms.customersFormskey}" style="display: none"/>
					<input name="customersForms.customersFormsid" value="${customersForms.customersFormsid}" style="display: none"/>
					<ul class="menu00" style="z-index:100;">
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						<li>
							<input type="checkbox" name="customersForms.latentCustomers" id="latentCustomers" value="1" />潜在客户报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.latentHighCustomers" id="latentHighCustomers" value="1" />潜在优质客户
						</li>
						<li>
							<input type="checkbox" name="customersForms.customersContact" id="customersContact" value="1" />联系方式报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.customersort" id="customersort" value="1" />客户分类报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.belongUnit" id="belongUnit" value="1" />所属单位报表
						</li>
						
						<li>
							<input type="checkbox" name="customersForms.customersFollow" id="customersFollow" value="1" />客户跟踪报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.callingMessage" id="callingMessage" value="1" />呼叫信息报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.personalPapers" id="personalPapers" value="1" />个人证件报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.productInterest" id="productInterest" value="1" />产品兴趣报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.customersNeed" id="customersNeed" value="1" />客户需求报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.customerSource" id="customerSource" value="1" />客户来源报表
						</li>
						<li>
							<input type="checkbox" name="customersForms.customersRecord" id="customersRecord" value="1" />客户档案报表
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
		
		<div name="staffaddress" id="${customersForms.latentCustomers}" class="showorhide"  style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
	      <tr>
	        <td height="27" class="txt03">潜在客户报表</td>
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
				<iframe url="ea/customermanage/ea_getPotentialList.jspa?frameid=mainframe2&status1=scode201306069vd8t6qypi0000000030&goodsid=" 
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
        
        <div name="staffcontact" dir="ltr" id="${customersForms.latentHighCustomers}" class="showorhide"  style="display: none" >
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
	      <tr>
	        <td height="27" class="txt03">潜在优质客户</td>
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
				<iframe url="ea/customermanage/ea_getPotentialList.jspa?frameid=mainframe3&status1=scode201306069vd8t6qypi0000000031&goodsid=" 
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
        
        <div name="staffpersonl" id="${customersForms.customersContact}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">联系方式报表</td>
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
				<iframe url="ea/customermanage/ea_getMessageList.jspa?frameid=mainframe4&status1=con&goodsid=" 
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
        
        <div name="stafftrack" id="${customersForms.customersort}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">客户分类报表</td>
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
				<iframe url="ea/customermanage/ea_getSortList.jspa?frameid=mainframe5&status1=00&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe5" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff6" id="${customersForms.belongUnit}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">所属单位报表</td>
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
				<iframe url="ea/customermanage/ea_getMessageList.jspa?frameid=mainframe6&status1=com&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe6" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        
        <div name="staff11" id="${customersForms.customersFollow}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
	      <tr>
	        <td height="27" class="txt03">客户跟踪报表</td>
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
				<iframe url="ea/customermanage/ea_getMessageList.jspa?frameid=mainframe11&status1=tra&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe11" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff12" id="${customersForms.callingMessage}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box12">
	      <tr>
	        <td height="27" class="txt03">呼叫信息报表</td>
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
				<iframe url="ea/customermanage/ea_getMessageList.jspa?frameid=mainframe12&status1=tel&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe12" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff13" id="${customersForms.personalPapers}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
	      <tr>
	        <td height="27" class="txt03">个人证件报表</td>
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
				<iframe url="ea/customermanage/ea_getMessageList.jspa?frameid=mainframe13&status1=cer&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe13" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff14" id="${customersForms.productInterest}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
	      <tr>
	        <td height="27" class="txt03">产品兴趣报表</td>
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
				<iframe url="ea/customermanage/ea_getSortList.jspa?frameid=mainframe14&status1=03&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe14" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff15" id="${customersForms.customersNeed}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box15">
	      <tr>
	        <td height="27" class="txt03">客户需求报表</td>
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
				<iframe url="ea/customermanage/ea_getSortList.jspa?frameid=mainframe15&status1=01&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe15" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff16" id="${customersForms.customerSource}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box16">
	      <tr>
	        <td height="27" class="txt03">客户来源报表</td>
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
				<iframe url="ea/customermanage/ea_getSortList.jspa?frameid=mainframe16&status1=02&goodsid=" 
					src="" name="main" height="80px" width="100%" marginwidth="0" 
				     marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe16" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staff17" id="${customersForms.customersRecord}" class="showorhide" style="display: none" >
        <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box17">
	      <tr>
	        <td height="27" class="txt03">客户档案报表</td>
	        <td align="right"><a href="javascript:" onclick="changemenu('box17',17,'edit')" id="mord17" 
	        	class="mord" style="color:#0066FF">编辑</a><a href="#" onclick="changemenu('box17',17,'close')" id="mord17_close"
				class="mord isHide" style="color:#0066FF;">取消</a>&nbsp;&nbsp;
	        </td>
	      </tr>
		</table>
        </div>
          
    </div>
	</div>

<form name="SubjectsForm" id="SubjectsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择产品
							<div class="close">
							</div>
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								物品编码或名称：
							</td>
							<td width="70">
								<input name="parameter" class="input" id="parameter" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								物品类型：
							</td>
							<td width="100">
								<s:select list="codeList" listKey="codeValue" id="typeID"
									listValue="codeValue" headerKey="" headerValue="请选择"
									name="typeID" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<!-- <input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" /> -->
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="40">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="40">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="80">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="SubjectsAadTree" class="text_tree"
												style="overflow: scroll; z-index: 99; height: 320px;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 320px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
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


</script>
</body>
</html>