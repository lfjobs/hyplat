
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
			<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 单位服务 -->
<title>单位服务</title>
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
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/contactcompany/danweiservice.js"></script>
<script type="text/javascript">
		  var ccompanyID = '';
		 var companyName = '';	 
		 var basePath='<%=basePath%>';
		 var pNumber ='${pageNumber}';
		 var search='${search}';
		 var token = 0 ;
		 var personurl='';
		 var opertionID = "";
		 var select=1;
		 var opaNum = 0; //客户类别传值number
		 
$(document).ready(function() {    
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	
	$("#DaoRuFanqd").click(function(){// 选择确定
		//var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		//childopertionID选中公司Id；
		var companyname = window.frames["daoRu"].$('tr#'+childopertionID).find("span#companyName").text();//公司名称
		var companyaddress = window.frames["daoRu"].$('tr#'+childopertionID).find("span#companyAddr").text();
		
		var companyTel = window.frames["daoRu"].$('tr#'+childopertionID).find("span#companyTel").text();//公司名称
		var cresponsible = window.frames["daoRu"].$('tr#'+childopertionID).find("span#cresponsible").text();
		var responsibleTel = window.frames["daoRu"].$('tr#'+childopertionID).find("span#responsibleTel").text();//公司名称
		var industryType = window.frames["daoRu"].$('tr#'+childopertionID).find("span#industryType").text();
						if(childopertionID == ""){
							alert("请选择");
							return;
						}
					var url = basePath+"/ea/companytrack/sajax_ea_getCompanybyID.jspa?contactCompany.ccompanyID="+childopertionID;
					$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
								document.location.href = basePath
										+ "page/ea/not_login.jsp";
							}
						var co=member.co;
						if(co==1){
							alert("不能重复添加");
							return;
						}else{
							$("#companyName").attr("value",companyname);
							$("#companyAddr").attr("value",companyaddress);
							$("#companyTel").attr("value",companyTel);
							$("#cresponsible").attr("value",cresponsible);
							$("#responsibleTel").attr("value",responsibleTel);
							$("#industryType").attr("value",industryType);
        					$("#bankJqm").jqmHide();
						}
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");

					}
				});
		
				});
		
   });
</script>
</head>

<body>

	<div class="content" style="width:850px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;单位客户咨询 管理</div>
			<table class="JQueryflexme" border="0">
			<tr>
			<td>
			<a href="#" id="aaa">编辑菜单</a>
			</td>
			</tr>
			</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">单位基本信息</td>
				<td align="right"><input type="button" value="往来单位列表" id = "danweichoice" style="height: 25px; background-image: url('<%=basePath%>images/buttonzz.png');border:0;"/>
				</td>
			</tr>
		</table>
		
		<div id="box1">
			<form name="box1Form" id="box1Form" method="post"
				enctype="multipart/form-data" >
				<input type="submit" name="submit" style="display:none"/>
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table">
					<tr class="trheight">
						<td width="12%" align="right">单位名称：</td>
						<td width="23%"><input name="contactCompany.companyName" value=""
							 id="companyName" readonly="readonly" size="10"  style="width:100%;height:100%;border: 0;"/>
							<span class="isShow"></span>						</td>
						<td width="12%" align="right">单位地址：</td>
						<td width="23%" done0="9" done1="9"><input
							name="contactCompany.companyAddr"  id="companyAddr" value=""
							size="10" readonly="readonly" style="width:100%;height:100%;border: 0;"/>
							<span class="isShow"></span></td>
					</tr>
					<tr>
						<td style="height:30" align="right">单位电话：</td>
						<td><input name="contactCompany.companyTel" readonly="readonly" style="width:100%;height:100%;border: 0;"
							id="companyTel" size="10" value="" /></td>
						<td align="right">单位负责人：</td>
						<td><input name="contactCompany.cresponsible" style="width:100%;height:100%;border: 0;"
							 id="cresponsible" size="10" readonly="readonly" value=""/></td>
					</tr>
					<tr>
						<td style="height:30" align="right">单位负责人电话：</td>
						<td><input style="width:100%;height:100%;border: 0;"
							 id="responsibleTel" size="10" value="" name="contactCompany.responsibleTel"
							readonly="readonly" /></td>
						<td align="right">行业类别：</td>
						<td ><input name="contactCompany.industryType" style="width:100%;height:100%;border: 0;"
							id="industryType" size="10" value=""
							readonly="readonly" />
						</td>
					</tr>
				<tr id="tools">
					<td align="right" colspan="4">
						<input type="button" id = "tosave" class="input-button JQuerySubmit" style="cursor: pointer; width: 80px;" value="提交" />	
					</td>
				</tr>
				</table>
			</form>
		</div>

			<div id="bankJqm" class="jqmWindow"
			style="width: 60%; height: 400px; display:none; absolute; left: 20%; top: 20%; background: #eff; overflow-x: hidden; overflow-y: auto;">
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
		
		<div class="menu01" >
			<form name="humanForm" id="humanForm" method="post">
			<ul>
				<li>
					<input type="submit" name="submit" style="display: none" />
					<input name="contactCompany.ccompanyKey" value="${contactCompany.ccompanyKey}" style="display: none"/>
					<input name="contactCompany.ccompanyID" value="${contactCompany.ccompanyID}" style="display: none"/>
					<ul class="menu00" style="z-index:20; position: absolute;">
						<li>
							<input type="checkbox" class="oroupboxAll"/>全选
						</li>
						<li>
							<input type="checkbox"  name="contactCompany" id="staffaddress" value="1" />证件管理 
						</li>
						<li>
							<input type="checkbox"  name="" id="staffcontact" value="1" />联系方式
						</li>
						<li>
							<input type="checkbox"  name="" id="staffeducation" value="1" />银行账号 
						</li>
						<li>
							<input type="checkbox"  name="" id="staffresume" value="1" />个人列表 
						</li>
						<li>
							<input type="checkbox"  name="" id="stafffamilymember" value="1" />经营范围 
						</li>
						<li>
							<input type="button" class="input-button JQuerySubmits" style="cursor: pointer; margin-top:0px; width: 60px;" value="保存" />
						</li>
					</ul>
				</li>
			</ul>
			</form>
		</div>
		
		<div  style="overflow-y:scroll;" class="gdkd">
		
		<div  class="showorhide" id="showzhengjian" name="certificateManager" >
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
					name="main" height="80px" width="100%" marginwidth="0" 
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
      
      <div name="staffcontact" dir="ltr" id="${humanresource.staffcontact}" class="showorhide"  >
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
        
        <div name="staffcontact" dir="ltr" id="${humanresource.staffcontact}" class="showorhide" id="showzhanghao">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
	      <tr>
	        <td height="27" class="txt03">银行帐号<br /></td>
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
				    id="mainframe3" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
				</iframe>
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
        
        <div name="staffcontact" dir="ltr" id="${humanresource.staffcontact}" class="showorhide" id="showliebiao">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
	      <tr>
	        <td height="27" class="txt03">个人列表<br /></td>
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
				<iframe url="ea/cs/ea_getPerson.jspa?staff.ccompanyID="
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
        
        <div name="staffcontact" dir="ltr" id="${humanresource.staffcontact}" class="showorhide" id="showjingying">
		<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
	      <tr>
	        <td height="27" class="txt03">经营范围<br /></td>
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
				<iframe url="" 
					src="" name="main" width="100%" marginwidth="0" 
				    height="80px" marginheight="0" scrolling="no" frameborder="0" 
				    id="mainframe3" border="0" framespacing="0" noresize="noResize" 
				    vspale="0">
		<span style="color:red;font-size:12px;">该功能尚未启用，敬请期待！</span>				    
				</iframe>
				
			 </div>
			 </td></tr>
            </table>
          </form>
        </div>
        </div>
    </div>
		
		
	</div>	

</body>
</html>