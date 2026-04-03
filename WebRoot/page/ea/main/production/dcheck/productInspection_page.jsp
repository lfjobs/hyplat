<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>生产检验</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	
	
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="${utboundOrderVo.goodsbillsid}";
		var ppId="${utboundOrderVo.ppId}";
		var orgId="${orgId}";
		var st="${st}";
		
		
$(function(){
		//选择往来个人
	$("#staffsName").click(function(){
		$("#deptjqModel").jqmShow();
		getStaffMember("parameter=&selectDept="+orgId);
	});
	//添加选中的往来个人
	$(".JQueryreturns").click(function(){
		if($(this).val()=="查询"){
			getStaffMember("selectDept="+orgId+"&parameter="+$("#searchdept").find("#parameterrm").val());
		}else if($(this).val()=="确定"){
			$("#staffsName").val($("#"+staffId).find("#staffname").text());
			$("#staffsID").val(staffId);
		//	$(".staffName").css("background-color","#FFFFFF").removeClass("error");
			$("#deptjqModel").jqmHide();
		}else{
			$("#deptjqModel").jqmHide();
		}
	});
	$("tr[id]").live("click",function(){
		if($(this).attr("class")=="staff"){
			staffId=this.id;
			$(this).find(".radio").attr("checked","checked");
		}
	});
	//弹出层初始化
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
	}).jqmAddClose('.close');
	$(".number").keydown(function(e){
		if(!(e.keyCode>=48&&e.keyCode<=57)&&!(e.keyCode>=96&&e.keyCode<=105)&&e.keyCode!=8){
			return false;
		}
	});
	$(".qualifiedQuantity").live("propertychange input",function(){
		
		if(parseInt($(this).val())>parseInt($(".iQuantity").val()))
			$(this).val($(".iQuantity").val());
		if($(this).val()=="")
			$(this).val(0);
		else
			$(this).val(parseInt($(this).val()));
		$(".unqualifiedQuantity").val(parseInt($(".iQuantity").val())-parseInt($(this).val() ));
	});
	$("#save").click(function(){
		if(st=="00")
			$("#assForm").attr("target", "hidden").attr("action",basePath+"ea/inspection/ea_singleInspection.jspa");
		else
			$("#assForm").attr("target", "hidden").attr("action",basePath+"ea/inspection/ea_inspection.jspa");
		document.assForm.assSubmit.click();
		token = 2;
	});
});
		
//获取员工
function getStaffMember(typeCN) {
	$("#dpsy").attr("title", 0);
	$("#dpxy").attr("title", 0);
	$("#dpzy").attr("title", 0);
	var searchurl = basePath
				+ "ea/promanage/sajax_ea_getStaffForOrg.jspa?";
	$.ajax({
				url : encodeURI(searchurl + typeCN
						+ "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : false,
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var pageForm = member.pageForm;
					var tabletr = "";
					if (pageForm == null) {
						$("#body_02dept").html("");
						$("span#dpzycount").text(0);
						return;
					}
					var dqy = pageForm.pageNumber;// 当前页
					var zys = pageForm.pageCount;// 总页数
					if (dqy > 1) {
						$("#dpsy").attr("title", dqy - 1);
					}
					if (dqy < zys) {
						$("#dpxy").attr("title", dqy + 1);
					}
					$("span#dpzycount").text(zys);
					for ( var i = 0; i < pageForm.list.length; i++) {
							tabletr += "<tr style='cursor: hand;' class='staff' id = "
									+ pageForm.list[i].staffID
									+ " title= "
									+ pageForm.list[i].staffID
									+ ">";
							tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='radio' value="
									+ pageForm.list[i].staffID
									+ " name='checkradio'/></td>";
							tabletr += "<td id='mum' align='center'>"
									+ (i + 1) + "</td>";
							tabletr += "<td id='staffcode' align='center'>"
									+ pageForm.list[i].staffCode
									+ "</td>";
							tabletr += "<td id='staffname' align='center'>"
									+ pageForm.list[i].staffName
									+ "</td>";
							tabletr += "<td id='sex' align='center'>"
									+ pageForm.list[i].sex
									+ "</td>";
							tabletr += "<td id='birthday' align='center'>"
									+ pageForm.list[i].birthday
									+ "</td>";
							tabletr += "<td id='nativePlace'  align='center'>"
									+ pageForm.list[i].nativePlace
									+ "</td>";
							tabletr += "<td id='reference'  align='center'>"
									+ pageForm.list[i].reference
									+ "</td>";
							tabletr += "<td id='staffIdentityCard' align='center'>"
									+ pageForm.list[i].staffIdentityCard
									+ "</td>";
							tabletr += "<td id='staffid' align='center' style='display:none;'>" 
								+ pageForm.list[i].staffID
								+ "</td>";
							tabletr += " </tr>";
					}
					$("#body_02dept").html(tabletr);
				},
				error : function cbf(data) {
					notoken = 0;
				}
			});
}
function re_load(){
	 window.opener.location.href = window.opener.location.href; 
	window.close();
}
	</script>
	<style type="text/css">
		#tableR{
			border-right:1px solid #a8c7ce;border-bottom:1px solid #a8c7ce;
		}
		#tableR td{
			border-left:1px solid #a8c7ce;border-top:1px solid #a8c7ce;
		}
	</style>
  </head>
  <body>
		  		<form method="post" id="assForm" name="assForm">
		  		<input type="submit" id="assSubmit" name="assSubmit" style="display: none;">
		  		<input type="hidden" class="goodsbillsid" name="utboundOrderVo.goodsbillsid"  value="${utboundOrderVo.goodsbillsid}">
		  		<input type="hidden" class="proAssemblyID" name="inspection.proAssemblyID"  value="${ass.proAssemblyID}">
		  		<input type="hidden" class="proInspectionID" name="inspection.proInspectionID"  value="${pi.proInspectionID}">
 				<iframe name="hidden"  style="display: none;"></iframe>
		  		<div style="width: 35%;height:90%;background-color: #fff;position: absolute;left: 32.5%;border: 1px solid #000;">
		  			<div style="width: 100%;height: 6%;background-color: #009DD9;color: #fff;text-align: center;padding-top: 4%;font-size: 24px;"> 
		  				考核检验
		  			</div>
		  			<div style="height: 8%;width: 100%;;">
		  				<font style="font-weight:bold;position: relative;top: 20px;left: 20px;font-size: 20px;" class="productName">
		  				${goods.goodsName}
		  				</font>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%">
		  					<tr>
		  						<td style="text-align: right;" width="30%">产品名称：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;" class="goodsName"
		  								 readonly="readonly" name="productionAssembly.goodsName" value="${pp.goodsName}">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">预算数量：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;" class="sQuantity"
		  								 readonly="readonly" name="productionAssembly.structureQuantity" value="${pp.quantity*goods.quantity}">
		  						</td>
		  					</tr>
		  				</table>	
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">生产数量：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;" class="iQuantity"
		  								 readonly="readonly" value="${ass.outgoingQuantity}">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">合格数量：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;"
		  								class="qualifiedQuantity number"  name ="inspection.qualifiedQuantity" value="${pi.qualifiedQuantity}">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">不合格数量：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;" class="unqualifiedQuantity"
		  							 name ="inspection.unqualifiedQuantity"  readonly="readonly" value="${pi.unqualifiedQuantity}">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">检验人：</td>
		  						<td style="text-align: left;">
		  							<input type="hidden" id="staffsID" name ="inspection.inspectionManID" >
		  							<input type="text" style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;" id="staffsName"
		  							name ="inspection.inspectionManName" 	 readonly="readonly" value="${pi.inspectionManName}">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 7%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">检验时间：</td>
		  						<td style="text-align: left;">
		  							<input type="text" style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;" class="iQuantity"
		  							name ="inspection.inspectionTime" 	 readonly="readonly" value="${pi.inspectionTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 9%;width: 100%;;">
		  				<table style="width: 100%;height:100%"">
		  					<tr>
		  						<td style="text-align: right;" width="30%">备注：</td>
		  						<td style="text-align: left;">
		  							<textarea style="width: 100%;height: 100%;border: 0px;border-bottom: 1px solid #AEADA7;"
		  									 class="remarks" name ="inspection.remarks"  value="${pi.remarks}"></textarea>
		  						</td>
		  					</tr>
		  				</table>
		  			</div>
		  			<hr width="100%" style="height:1px;border:none;border-top:1px dotted  #000;"/>
		  			<div style="height: 5%;background-color: #AEADA7;text-align: center;padding-top: 3%;font-size: 24px;color: #fff;cursor:pointer;" id="save"> 
		  					保存当前信息
		  			</div>
		  		</div>
		  		</form>
		  		
		  		
		  		  <%------------------------------------部门树和人 ------------------------------------%>
		<form name="selectdeptForm" id="selectdeptForm" method="post"
			enctype="multipart/form-data">
			
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="deptjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							组织机构
						</div>
					</div>
					<table width="99%" height="33" id="searchdept"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								员工姓名：
							</td>
							<td width="142">
								<input class="input" id="parameterrm"
									size="10" style="margin-left: 2px;" />
								<input type="hidden" id="selectdept"
									/>
								<input type="hidden" id="selectdeptname" />
								<input type="hidden" id="deptpos" />
							</td>
							<td height="33">
								<input type="button" class="btn02 JQueryreturns" id="searchdeptbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02 JQueryreturns" id="qddept" name="button5"
									value="确定" />
								
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									
			
							</td>
							<td width="80">
								<a id="dpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dpzy">共&nbsp;&nbsp; <span style="color: red"
									id="dpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree"
												style="overflow: auto; z-index: 99;width:170px; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/organizationtree.jsp?yanzheng=${zhuangtai}" width="250" height="270"></iframe></div>
											
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
								<div 
									style="margin-top: 2px; height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择员工</td>
									</tr>
								</table>
								<table width='99%' align='center' id='dptable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<tr>
											<th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>序号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员编号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员姓名</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>性别</th>
											<th align='center' bgcolor='#E4F1FA' width='100'>出生日期</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>籍贯</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>手机号</th>
											<th align='center' bgcolor='#E4F1FA'>身份证</th>
										</tr>
									</thead>
									<tbody id="body_02dept"></tbody>
								</table>
							</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
  </body>
</html>
