<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车辆气瓶详细信息查看</title>
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

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/driving/carcarlinderadd.js"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<link href="<%=basePath%>js/jqModal/css/jqModal_blue.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<style>
body{ margin:0; padding:0px;}
a, a:link { color: #222; text-decoration: none; }
a:visited {  }
a:active, a:hover {}
a:focus { outline: none; }
.clear { diplay: block!important; float: none!important; clear: both; overflow: hidden; width: auto!important; height: 0!important; margin: 0 auto!important; padding: 0!important; font-size: 0; line-height: 0; }
.table_con{font-family: '宋体 Regular','宋体'; font-size:12px; float:left; width:1150px; margin:0; padding:0px; color:#000; border:1px solid #3fb2ad;margin-left: 5%}
._con{ width:1130px;  float:left; margin-left:10px; margin-top:8px;  }
table{ float:left;}
.t_f{ font-size:13px; font-weight:bold; color:#333;}
table tr td{ border:1px solid #3fb2ad;border:1px solid #3fb2ad; height:35px; line-height:35px; text-align:center;}
table tr td p{ line-height:16px; height:15px; margin:0px; padding:0px;}
table tr td dd{ font-size:14px; display:initial;}
.table tr td{ border:1px solid #3fb2ad; border-right:none; border-bottom:none;}
table tr .bor_n{ border:none;}
table tr .bor_r{ border-right:1px solid #3fb2ad;}
#addline{cursor: pointer;}
.textType {
	width: 100%;
	height: 100%;
	border: 0px;
	background-color: transparent;
}
</style>
<script type= "text/javascript">
	$(document).ready(function(){
	$(".baofei").click(function(){
	if(confirm("确定继续?")){
	alert("此功能还未开通！");
	}
	})
	})
</script>
<script type="text/javascript">
	var basePath = "<%=basePath%>";
	var token = 0;
	var pNumber="${pageNumber}";
	var pageNumber=<%=request.getParameter("pagepageNumber")%>;
	var notoken = 0;
	var select = 1;
	
	$(document).ready(function() {//销售单FORM
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
     	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
		$("#PArchiveForm #managerName").val(staffName);
	    $("#PArchiveForm #managerID").val(childopertionID);	
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
		}
		if(checkopertionName =="partnerName"){
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
});  
	
</script>

</head>
<body style="overflow-Y:scroll">
<div class="table_con">
    <div class="_con">
    <form  name="carlinderForm" id="carlinderForm"  method="post"
			enctype="multipart/form-data">
    <input name="submit" type="submit" value="提交" style="display: none;"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="t_f">1、气瓶基本信息</td>
      </tr>
      <tr>
        <td class="bor_n">
        <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<input type="text" STYLE="display:none" id="ccompanyidcarlinder"/>
          	<input type="text" STYLE="display:none" id="userstaffID"/>
            <td width="50">操作</td>
            <td width="65">气瓶编号</td>
            <td width="50">类型</td>
            <td width="80"><p>设计壁厚(mm)</p>
             </td>
            <td width="50">型号</td>
            <td width="60">制造单位</td>
            <td width="60">出厂日期</td>
            <td width="50"><p>重量(kg)</p>
              </td>
            <td width="50"><p>容积(L)</p>
              </td>
            <td width="120"><p>公称工作压力(Mpa)</p>
              </td>
            <td width="120"><p>水压实验压力(Mpa)</p>
              </td>
            <td width="50">材质</td>
            <td width="50">纤维</td>
            <td width="50">树脂</td>
            <td width="65">制造国别</td>
            <td width="65">制造代号</td>
            <td class="bor_r">状态</td>
          </tr>
     	 <c:forEach items='${sessionScope.carlinderlist}' var="obj" varStatus="i">
			<tr id="${obj.carCylinderId}">
					<td><c:if test='${obj.status=="01"}'><input type = "button" value="报废" class="baofei"/></c:if>
					<c:if test='${obj.status=="00"}'>已报废</c:if></td>
					<td><span>${obj.cylinderNum}</span></td>
            		<td><span> ${obj.cylinderType}</span></td>
            		<td><span>${obj.designThickness}</span></td>
		            <td><span>${obj.cylinderModel}</span></td>
		            <td><span>${obj.manufactureCompany}</span></td>
		            <td><span>  ${fn:substring(obj.leavefactorydate, 0, 10)}</span></td>
		            <td><span>${obj.weight}</span></td>
		            <td><span>${obj.volume}</span></td>
		            <td><span>${obj.nominalworkpressure}</span></td>
		            <td><span>${obj.hydraulicTestPressure}</span></td>
		            <td><span>${obj.texture}</span></td>
		            <td><span>${obj.fiber}</span></td>
		            <td><span>${obj.resin}</span></td>
		            <td><span>${obj.manufacturingcountry}</span></td>
		            <td><span>${obj.madecode}</span></td>
		            <td class="bor_r"><span id="status"><c:if test='${obj.status=="01"}'>正常</c:if>
		             <c:if test='${obj.status=="00"}'>已报废</c:if></span></td>
			</tr>
		 </c:forEach>
        </table>

        </td>
      </tr>
      <tr>
         <td class="t_f">2、客户基本信息</td>
      </tr>
      <tr>
         <td class="bor_n">
        <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0" id="table4">
          <tr>
            <td class="bor_r" colspan="6" id="chooseconcart">
              <input type="button" class="ACT_btn" name="button5" value="选择往来单位" id="xzwlaw" style=" width:100px; float:left; margin-left:10px;"/>
              <input type="button" class="ACT_btn" name="button4" value="选择往来个人" id="xzwlgr" style=" width:100px; float:left; margin-left:10px;">
            </td>
          </tr>
          <tr>
            <td width="157">往来单位</td>
            <td width="157"><span id="ccompanyname" class="qk">${cashierBillsVO.ccompanyname}</span><span> ${obj[27] }</span></td>
            <td width="157">单位电话</td>
            <td width="157"><span id="companyTel" class="qk">${cashierBillsVO.companyTel}</span><span> ${obj[28] }</span></td>
            <td width="157">单位负责人</td>
            <td class="bor_r"><span id="cresponsible" class="qk">${cashierBillsVO.cresponsible}</span><span> ${obj[29] }</span></td>
          </tr>
          <tr>
            <td>单位负责人电话</td>
            <td><span id="responsibleTel" class="qk">${cashierBillsVO.responsibleTel}</span><span> ${obj[30] }</span></td>
            <td>行业类别</td>
            <td><span id="industryType" class="qk">${cashierBillsVO.industryType}</span><span> ${obj[31] }</span></td>
            <td>单位往来关系</td>
            <td class="bor_r"><span id="contactConnections" class="qk"></span>
            <select name="paBill.ccompanyRelationship" id="contactConnections" style="display:none"><option value="">请选择</option></select>
            <span> ${obj[32] }</span>
</td>
          </tr>
          <tr>
            <td>公司地址</td>
            <td class="bor_r" colspan="6"><span id="companyAddr" class="qk">${cashierBillsVO.companyAddr}</span><span> ${obj[33] }</span></td>
          </tr>
        </table>
        <div class="clear"></div>
        <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0" id="table5">
          <tr>
            <td width="157">往来个人</td>
            <td width="157"><span id="contactUserName" class="qk">${cashierBillsVO.contactUserName}</span><span> ${obj[34] }</span></td>
            <td width="157">电话</td>
            <td width="157"><span id="tel" class="qk">${cashierBillsVO.tel}</span><span> ${obj[35] }</span></td>
            <td width="157">个人身份证</td>
            <td class="bor_r"><span id="staffIdentityCard" class="qk">${cashierBillsVO.staffIdentityCard}</span><span> ${obj[36] }</span></td>
          </tr>
          <tr>
            <td>QQ</td>
            <td><span id="userQq" class="qk">${cashierBillsVO.userQq}</span><span> ${obj[37] }</span></td>
            <td>邮箱</td>
            <td><span id="email" class="qk">${cashierBillsVO.email}</span><span> ${obj[38] }</span></td>
            <td>个人往来关系</td>
            <td class="bor_r"><span id="phone" class="qk"></span><select name="paBill.cstaffRelationship" id="phone" style="display:none">
    		<option value="" >请选择</option> </select><span> ${obj[39] }</span>
    		</td>
          </tr>
          <tr>
            <td>地址</td>
            <td class="bor_r" colspan="6"><span id="userAddr" class="qk">${cashierBillsVO.userAddr}</span><span> ${obj[40] }</span></td>
          </tr>
        </table>

        </td>
      </tr>
      <tr>
         <td class="t_f">3、气瓶使用登记证信息</td>
      </tr>
      <tr>
        <td class="bor_n">
        <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="76">车牌号码</td>
            <td width="76">登记代码</td>
            <td width="76">登记证号</td>
            <td width="76">区域</td>
            <td width="76">厂牌型号</td>
            <td width="76">车架号</td>
            <td width="76">发动机号</td>
            <td width="76">安装单位</td>
            <td width="76">安装数量(只)</td>
            <td width="76">发证机构</td>
            <td width="76">安装日期</td>
            <td  class="bor_r">发证日期</td>
          </tr>
          <tr>
            <td><input type="text" class="textType" name="carCylinderInformation.licensenumber"/><span> ${obj[15] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.registrationcode"/><span> ${obj[16] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.certificateNumber"/><span> ${obj[17] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.area"/><span> ${obj[18] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.licenseplatetype"/><span> ${obj[19] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.chassisnumber"/><span> ${obj[20] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.enginenumber"/><span> ${obj[21] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.installationunit"/><span> ${obj[22] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.installationnumber"/><span> ${obj[23] }</span></td>
            <td><input type="text" class="textType" name="carCylinderInformation.certifyingauthority"/><span> ${obj[24] }</span></td>
            <td><input type="text" class="textType" onfocus="date(this)" name="carCylinderInformation.installationdate"/><span> ${fn:substring(obj[25], 0, 10)}</span></td>
            <td class="bor_r"><input type="text" class="textType" onfocus="date(this)" name="carCylinderInformation.issuedate"/><span>${fn:substring(obj[26], 0, 10)}</span></td>
          </tr>
        </table>
        </td>
      </tr>
      <tr>
      <tr>
        <td>
          <div class="tj" style="width:100%; float:left; height:80px;">
              <input name="" type="button" value="返回" style="margin-left:20px; margin-top:30px;" onclick="window.location.href='<%=basePath%>ea/productregister/ea_getlistregistrationlist.jspa?showtype=registration'">
          </div>
        </td>
      </tr>
     </table>
     </form>
     <table class="table" width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px; margin-bottom:10px; border-bottom:1px solid #3fb2ad;">
      <tr>
        <td class="bor_r t_f" colspan="8" style="border-bottom:2px solid #3fb2ad;">气瓶检测记录</td>
      </tr>
      <tr>
        <td width="100">序号</td>
        <td width="120">检验编号</td>
        <td width="120">气瓶编号</td>
        <td width="100">类型</td>
        <td width="100">型号</td>
        <td width="120">检验结果</td>
        <td width="120">检验日期</td>
        <td class="bor_r">下次检验日期</td>
      </tr>
    </table>

    </div>
</div>	
	<iframe src="" name="main"  id="mainframe"  frameborder="0" border="0"  framespacing="0" style="height:0;"></iframe>
	<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #black;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<select id="contactConnections" 
									headerValue="--全部--" name="contactConnections" theme="simple"></select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 330px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		
		<%------------------------------------选择往来个人------------------------------------%>
		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="userjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">

							选择往来个人
						</div>
					</div>
					<table width="99%" height="33" id="searchuser" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="40" align="right">
								姓名：
							</td>
							<td width="50">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="100">
								<select headerValue="--全部--"
									id="relation" name="relation" theme="simple"></select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchuu" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qduser" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzgr" name="button5"
									value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="grsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="grxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 330px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height:330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		</body>
</html>
