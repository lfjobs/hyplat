<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>安全检查任务</title>
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
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/safe/OASafeInspectInfo_edit.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css"/>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript">
		var  treeID ="<%=session.getAttribute("organizationID")%>";
			var id='${entityVO.inspectID}';
			var  token = 0;
			var inspectID='';
			var typeName='${entityVO.typeName}';
			var basePath='<%=basePath%>';
			var	tsiDeptID ='${entityVO.tsiDeptID}';
			var search="${search}";
			var pNumber='${pageNumber}';
			var notoken=0;
			var select=1;
			function getValueForParm(attachTable,parm1,parm2,parm3,url){ //打开页面
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm1",$("#jqmWindow2")).attr("value",parm1);
			 $("#parm2",$("#jqmWindow2")).attr("value",parm2);
			 $("#parm3",$("#jqmWindow2")).attr("value",parm3);
		  	  $("#ifr").attr("src",basePath+url);
		  	   $("#jqmWindow2").jqmShow();
			}
	
	
		
			$(document).ready(function() {
				$("select#inspectTypeID").find("option").each(function(){
						if($(this).text()==typeName){
						$(this).attr("selected","selected");
							return ;
						}
				
				} );
				
			
				$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			   }); 
				$("#isSubmit").click(function(){// 选择确定
					var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
					var parm1 =$("#parm1",$("#jqmWindow2")).attr("value");
					var parm2 = $("#parm2",$("#jqmWindow2")).attr("value");
					var parm3 = $("#parm3",$("#jqmWindow2")).attr("value");
					var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
					if(value1 == ""){
						alert("请选择")
						return;
					}
					var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm2).text();//弹出框的页面存在于span中才取得到
					var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm3).text();//弹出框的页面存在于span中才取得到
					if(parm1 != "")
						$("#"+parm1,$("#"+myfrom)).attr("value",value1).trigger("blur");
					if(parm2 != "")
						$("#"+parm2,$("#"+myfrom)).attr("value",value2).trigger("blur");
					if(parm3 != "")
						$("#"+parm3,$("#"+myfrom)).attr("value",value3).trigger("blur");
					 $("#ifr").attr("src","");
			       $("#jqmWindow2").jqmHide();
			   });
			   
			   //更改部门事件 清空责任人
   $("select#tsiDept","table#tabletop").change(function(){
            $("input#partnerID","table#tabletop").attr("value","");
            $("input#partnerName","table#tabletop").attr("value","");
            $("input#childPartnerName","table#tabletop").attr("value","");
    });
    // 重置往来单位
    $("input.resetcompany").click(function(){
                  $t=$("table#table4");
                  $t.find(":span .qk").each(function(){
                   $(this).text("");
                  });
                  $t.find("select").each(function(){
                   $(this).empty();
                   $(this).attr("style","display:none");
                  });
                  $t.find(":input").each(function(){
                   $(this).attr("value","");
                  });
    });
    // 重置往来个人
    $("input.resetperson").click(function(){
                    $t=$("table#table5");
                  $t.find(":span .qk").each(function(){
                   $(this).text("");
                  });
                  $t.find("select").each(function(){
                   $(this).empty();
                   $(this).attr("style","display:none");
                  });
                  $t.find(":input").each(function(){
                   $(this).attr("value","");
                  });
    });
    $("input#partnerID",$("table#tabletop")).bind('propertychange',function(){
    		var partnerID=$("input#partnerID").attr("value");
    		var departmentID=$("select#tsiDept option:selected").attr("value");
    		var companyID=$("input#companyID",$("table#tabletop")).attr("value");
    	 /*********************************取得岗位职责下拉*************************************/
	   var RegistrationURL=basePath+"ea/safeinspect/sajax_ea_ajaxResponsibilitiesList.jspa?partnerID="+partnerID+"&departmentID="+departmentID+"&companyID="+companyID+"&staffPost=专岗&date="+new Date().toLocaleString();
				  $.ajax({
			                        url: encodeURI(RegistrationURL),
			                        type: "get",
			                        async: true,
			                        dataType: "json",
			                        success: function cbf(data){
					                  var member = eval("(" + data + ")");
					                  var nologin = member.nologin;
					                  if(nologin){
					                  document.location.href =basePath+"page/ea/not_login.jsp";
					                  }
					                
					                  var responsibilitiesList = member.responsibilitiesList;
					                  $se=$("select#position",$("table#tabletop"));
					                  $se.empty();
					                  $se.append("<option selected='selected' value = ''>--请选择--</option>");
					                   for(var i = 0 ;i<responsibilitiesList.length;i++){
					                     $op = $("<option />");
						                 $op.attr("value", responsibilitiesList[i].postName).text(responsibilitiesList[i].postName);
						                 $se.append($op);
					                  }
					                   $se.attr("name","entity.position")
					                   $se.show();
					                   $("span#gwxgyc").hide();
					                   $("input#gwxgyctext").remove();
					                   $("select#position").removeAttr("disabled");
				    },
			          error: function cbf(data){
                              notoken = 0;
			                 alert("数据获取失败！")
			          }
	            });
	////////////////////////////////////////////////////
    })
});
		
		</script>

	</head>
	<body>
		<form name="cashierTallyForm" id="cashierTallyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="billDate" value="${entityVO.makeDate }" />
			<div class="content" style="width: 100%; background-color: #E4F1FA">
				<div class="contentbannb">
					<div class="divtx" >
						安全检查任务
					</div>
				</div>
				<table width="99%" height="30" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;" class="table">
					<tr>
						<td align="right" width="120px">
							<span class="STYLE1">凭证号</span>：
						</td>
						<td style="padding-left: 2px;" width="120px">
		<input type="hidden"  name="entity.id" id="id" value="${entityVO.inspectID}"/>
       <input type="hidden" name="entity.key" id="key" value="${entityVO.inspectKey}"/>
							<input name="entity.inspectNO" class="input " id="username16"
								style="margin-left: 2px;" size="30"
								value="${entityVO.inspectNO }" />
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单据类别</span>：
						</td>
						<td width="80px">
						<s:if test="entityVO.inspectID==null">
						<s:select list="oasafeKindList" listKey="id" listValue="name"  name="entity.safeTypeID" theme="simple" value="" id="inspectTypeID"></s:select></s:if>
						<s:else>
							<span>${entityVO.typeName}</span>
							<input name="entity.safeTypeID" type="hidden" value="${entityVO.inspectTypeID}" id="inspectTypeID"/>
						</s:else>
						</td>
						<%--<td align="right">
							<span class="STYLE1">制单日期：</span>
						</td>
						<td width="0">
							<input name="entity.makeDate" class="input" id="username15"
								value="${entityVO.makeDate}" size="12" />
						</td>
						--%>
						<td align="right">
							<span class="STYLE1">附件浏览：</span>
							
						</td>
						<td>
						<s:if test="entityVO.attachment==null||entityVO.attachment==''">
							<input type="file" name="photo" value="上传" wigth="200" /></s:if>
						  <s:else>
                                <span id="filephoto"  onclick="lookImage('${entityVO.attachment}');"><a href="#">查看附件</a></span>
                                <input type="file" name="photo" value="上传" style="display: none" id="cxscfjyck"/><span><a href="#" id="cxscfj" ><font color="RED">重新上传附件</font></a>
                                <a href="#" id="qxscfj" style="display: none;"><font color="RED">取消上传</font></a></span>
                         </s:else>
                         <input name="entity.attachment" type="hidden" value="${entityVO.attachment}" />
						</td>
					</tr>
				</table>
				<table id="tabletop" width="100%" border="0" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">公司：</span>
						</td>
						<td colspan="3">
							<span class="STYLE1" id="companyNames">${entityVO.tsiCompany}</span>
							<input name="entity.companyID" type="hidden" id="companyID"
								value="${entityVO.tsicompanyID}" />
						</td>
						<td align="right">
							<span class="STYLE1">部门：</span>
						</td>
						<td width="0">
						<s:if test="entityVO.tsiDept==null">
							<span class="STYLE1"><select name="entity.personDepartmentID" id="tsiDept">
								</select></span></s:if>
						<s:else>
						<span class="STYLE1" id="spanTsiDept">${entityVO.tsiDept}</span>&nbsp;<a href="#" id="xgbm">修改</a>
						<span class="STYLE1" style="display:none" id="xgxsbm"><select name="entity.personDepartmentID" id="tsiDept">
								</select></span>
						</s:else>
						</td>
						<td align="right">
							<span class="STYLE1">责任人：</span>
						</td>
						<td width="0">
					
							<input type="hidden" id="partnerID" readonly="readonly"  name="entity.staffID"  value="${entityVO.tsiPersonID}" />
							<input type="text" id="partnerName" name="partnerName"
								readonly="readonly" value="${entityVO.tsiPerson}" size="15" />
								
							<a href="#"
								onclick="getValueForParm('tabletop','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
						</td>
						<td align="right">
							<span class="STYLE1">人员编号：</span>
						</td>
						<td width="0">
							<input type="text" style="border: 0" id="childPartnerName"
								name="entity.staffNumber" readonly="readonly"
								value="${entityVO.tsiPersonCode}" size="15" />
						</td>
						<td align="right">
							<span class="STYLE1">岗位职务：</span>
						</td>
						<td width="120px">
							<s:if test="entityVO.inspectID==null">
							<span style="color: #FF0000"><select name="entity.position" id="position">
									<option value="">
										选择内容
									</option>
								</select></span></s:if>
								<s:else><span id="gwxgyc" >${entityVO.tsiPersonPost}</span>
								<input name="entity.position" type="hidden" id="gwxgyctext" value="${entityVO.tsiPersonPost}"/>
								<select name="entity.position" id="position" style="display:none" disabled="disabled">
									<option value="">
										选择内容
									</option>
								</select>
								</s:else>
								
						</td>
					</tr>
				</table>

				<table width="99%" height="140px" border="0" align="center"
					cellpadding="0" cellspacing="0" style="margin-top: 5px">
					<tr>
						<td valign="top">
							<div id="Layer1"
								style="position: absolute; width: 100%; height: 139px; overflow: scroll;">
								<table width="3000" align="center" cellpadding="0"
									cellspacing="0" class="table" id="goodtable">
									<tr>
										<th height="10" align="center" bgcolor="#E4F1FA">
											日期
										</th>
										<th align="center" align="center" bgcolor="#E4F1FA">
											检查起时间
										</th>
										<th align="center" align="center" bgcolor="#E4F1FA">
											检查止时间
										</th>
										<th align="center" align="center" bgcolor="#E4F1FA">
											条码号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											检查项编号
										</th>
										<th width="150" bgcolor="#E4F1FA">
											工作检查地点
										</th>
										<th width="120" bgcolor="#E4F1FA">
											工作检查对象
										</th>
										<th width="120" bgcolor="#E4F1FA">
											工作检查名称
										</th>
										<th width="120" bgcolor="#E4F1FA">
											检查项目
										</th>
										<th width="150" bgcolor="#E4F1FA">
											检查结果
										</th>
										<th width="100" bgcolor="#E4F1FA">
											检查结果附件
										</th>
										<th align="center" bgcolor="#E4F1FA">
											数据提醒
										</th>
										<th align="center" bgcolor="#E4F1FA">
											手机号
										</th>
										<th align="center" bgcolor="#E4F1FA">
											发送短信
										</th>
										<th align="center" bgcolor="#E4F1FA">
											检查人意见
										</th>
										<th align="center" bgcolor="#E4F1FA">
											主管检查意见
										</th>
										<th align="center" bgcolor="#E4F1FA">
											备注
										</th>
										<th align="center" bgcolor="#E4F1FA">
											操作
										</th>
									</tr>
									<s:iterator value="pageForm.list">
										<tr class="xggoods" id="${id}">
											<td height="20" align="center" bgcolor="#FFFFFF">
												<span id="operationDate">${fn:substring(operationDate,
													0, 10)}</span>
												<input name="operationDate" class="put3 input model1" 
													onfocus="date(this);"
													value="${fn:substring(operationDate, 0, 10)}"
													style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="startDate">${fn:substring(startDate, 0,
													19)}</span>
												<input name="startDate" class="model1 input put3" onfocus="date(this);"
													 value="${fn:substring(startDate, 0, 10)}"
													size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="endStart">${fn:substring(endStart, 0, 19)}</span>
												<input name="endStart" class="model1 input put3" id="endStart"
													onfocus="date(this);"
													value="${fn:substring(endStart, 0, 10)}"
													style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="barCode">${barCode}</span>
												<input name="barCode" class="model1 input" id="barCode"
													value="${barCode}" style="margin-left: 2px;" size="10" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input  name="inspectItemno" class="model1 input"
													value="${inspectItemno}" id="inspectItemno" />
												<span  id="inspectItemno">${inspectItemno}</span>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectAddress">${inspectAddress}</span>
												<input name="inspectAddress" value="${inspectAddress}"
													class="model1 input" id="inspectAddress"
													style="margin-left: 2px;"  />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectTarget" >${inspectTarget}</span>
												<input name="inspectTarget" value="${inspectTarget}"
													class="model1 input" id="inspectTarget" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectName" >${inspectName}</span>
												<input name="inspectName" value="${inspectName}"
													class="model1 input" id="inspectName" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectType">${inspectType}</span>
												<input name="inspectType" value="${inspectType}"
													class="model1 input" id="inspectType" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectResult" >${inspectResult}</span>
												<input name="inspectResult" value="${inspectResult}"
													class="model1 input" id="inspectResult" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
										<span><s:if test="attachMent==null||attachMent==''">无</s:if></span>
                           				  <s:else><span id="attachMent"  onclick="lookImage('${attachMent}');"><a href="#">查看</a></span></s:else>
						    			<input name="photo"   type="file" class="model1 input" size="10" contentEditable="false" />
						    				<input name="attachMent" value="${attachMent}"
													 id="inspectResult" style="margin-left: 2px;" type="hidden"/>
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="isRemind" >
												<s:if test="isRemind==true"><span>是</span></s:if>
												<s:else><span>否</span></s:else></span>
												<select name="isRemind"  class="model1 input" id="isRemind" style="margin-left: 2px;">
												<option value="true">是</option>
												<option value="false">否</option>
												</select>
											
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="moblle">${moblle}</span>
												<input name="moblle" class="model1 input" value="${moblle}"
													id="moblle" size="3" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="message">${message}</span>
												<input name="message" class="model1 input" value="${message}"
													id="message" size="3" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="inspectOrremark">${inspectOrremark}</span>
												<input class="model1 input" name="inspectOrremark"
													value="${inspectOrremark}" id="inspectOrremark" size="23"
													style="margin-left: 2px;" />
											</td>

											<td align="center" bgcolor="#FFFFFF">
												<span id="ManagerRemark">${ManagerRemark}</span>
												<input name="ManagerRemark" 
													value="${ManagerRemark}" class="input model1" id="ManagerRemark"
													size="23" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<span id="comments">${comments}</span>
												<input name="comments" value="${comments}" class="model1 input"
													id="comments" size="23" style="margin-left: 2px;" />
											</td>
											<td align="center" bgcolor="#FFFFFF">
												<input type="hidden" name="id" value="${id}" id="id" />
												<input type="hidden" name="key" value="${key}" id="key" />
												<a href="#" class="ajaxxg"><img
														src="<%=basePath%>images/admin_images/edit.gif" width="16"
														height="16" title="修҉改҉" border="0" /> </a>
												<a href="#" class="ajaxsc"><img
														src="<%=basePath%>images/admin_images/gtk-del.png"
														width="16" height="16" title="删除" border="0" /> </a>
											</td>
										</tr>
									</s:iterator>
									<tr id="kelong" style="display: none">
										<td height="20" align="center" bgcolor="#FFFFFF">
											<input name="operationDate" onfocus="date(this);"
												class="input put3" id="username" style="margin-left: 2px;"
												size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="startDate" class="input " id="startDate"
												onfocus="date(this);" size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="endStart" class="input " id="endStart"
												onfocus="date(this);" style="margin-left: 2px;" size="10" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="barCode" class="input " id="barCode"
												style="margin-left: 2px;" size="15" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectItemno" id="inspectItemno" size="15"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectAddress" id="inspectAddress" size="15"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectTarget" id="inspectTarget" size="15"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectName" id="inspectName" size="15"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectType" id="inspectType" size="6"
												value="" readonly="readonly"
												class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectResult" class="input" id="inspectResult"
												size="21" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="photo" id="attachMent" size="10" type="file"
												style="margin-left: 2px;" class="input " value="上传"/>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<select name="isRemind"  class="input" id="isRemind" style="margin-left: 2px;">
												<option value="true">是</option>
												<option value="false">否</option>
												</select>
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="moblle" id="moblle" size="5"
												style="margin-left: 2px;" class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="message" id="message" size="5"
												style="margin-left: 2px;" class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="inspectOrremark" id="inspectOrremark" size="23"
												style="margin-left: 2px;" class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="ManagerRemark" id="ManagerRemark" size="23"
												style="margin-left: 2px;" class="input " />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<input name="comments" id="comments" size="23"
												style="margin-left: 2px;" class="input " />
											<input name="safeTypeID" type="hidden" id="safeTypeID"
												size="15" style="margin-left: 2px;" />
										</td>
										<td align="center" bgcolor="#FFFFFF">
											<a href="#" class="klsc"><img
													src="<%=basePath%>images/admin_images/gtk-del.png"
													width="16" height="16" title="删除" border="0" /> </a>
										</td>
									</tr>
								</table>
							</div>
						</td>
					</tr>
				</table>
				<table width="105%" height="30" border="0" align="center"
					cellpadding="0" cellspacing="0" bgcolor="#FFFFFF"
					style="margin-bottom: 5px;">
					<tr>
						<td bgcolor="#FFFFFF">
							<input type="button" class="ACT_btn" name="button4" value="导入数据"
								disabled="disabled" />
							<input type="button" class="ACT_btn" name="button4"
								value="选择安全类别" id="shuju" />
							<input type="button" class="ACT_btn" name="button5"
								value="选择往来单位" id="xzwlaw" />
							<input type="button" class="ACT_btn" name="button4"
								value="选择往来个人" id="xzwlgr" />
							<input type="button" class="ACT_btn" id="selectLeibie" disabled="disabled"
								name="button5" value="新增检查项" />
						</td>
					</tr>
				</table>

				<table width="99%" border="0" id="table4" align="center"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来单位：</span>
						</td>
						<td width="15%">
							<span id="ccompanyname" class="qk">${entityVO.exUnitName}</span>
							<input type="hidden" id="ccompanyID"
								name="entity.contactcompanyID" value="${entityVO.exunitID}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位电话：</span>
						</td>
						<td width="15%">
							<span id="companyTel" class="qk">${entityVO.exUnitTel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">单位负责人：</span>
						</td>
						<td width="15%">
							<span id="cresponsible" class="qk">${entityVO.exUnitPerson}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账号：</span>
						</td>
						<td width="15%">
							<span id="accountNum" class="qk">${entityVO.exUnitBanknum}</span>
							<input id="accountNum" type="hidden"
								name="entity.contactcompanyBankNum"
								value="${entityVO.exUnitBanknum}" />
							<select style="display: none" id="aNum"
								name="contactcompanyBankNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">单位负责人电话：</span>
						</td>
						<td>
							<span id="responsibleTel" class="qk">${entityVO.exUnitPersonTel}</span>
						</td>
						<td align="right">
							<span class="STYLE1">公司地址：</span>
						</td>
						<td>
							<span id="companyAddr" class="qk">${entityVO.exUnitAddr}</span>
						</td>
						<td align="right">
							<span class="STYLE1">行业类别：</span>
						</td>
						<td>
							<span id="industryType" class="qk">${entityVO.exUnitIndustry}</span>
						</td>
						<td height="30" align="right">
							<span class="STYLE1">单位往来关系：</span>
						</td>
						<td>
							<div align="left">
								<span id="contactConnections" class="qk">${entityVO.exUnitRelation}</span>
							</div>
							<s:select list="connectionlist" listKey="codeValue"
								style="display:none" id="contactConnections"
								listValue="codeValue" headerKey="" headerValue="请选择"
								name="entityVO.exUnitRelation" theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" id="table5"
					cellpadding="0" cellspacing="0" style="background: #FFFFFF;"
					class="table">
					<tr>
						<td width="10%" height="30" align="right">
							<span class="STYLE1">往来个人：</span>
						</td>
						<td width="15%">
							<span id="contactUserName" class="qk">${entityVO.exPerson}</span>
							<input type="hidden" id="contactUserID"
								name="entity.contactUserID" value="${entityVO.expersonID}" />
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">电话：</span>
						</td>
						<td width="15%">
							<span id="tel" class="qk">${entityVO.exPersonTel}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人身份证号：</span>
						</td>
						<td width="15%">
							<span id="staffIdentityCard" class="qk">${entityVO.exPersonIdcard}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">银行账户：</span>
						</td>
						<td width="15%">
							<span id="userAccountNum" class="qk">${entityVO.exPersonBanknum}</span>
							<input id="userAccountNum" type="hidden"
								name="entity.contactUserBankNum"
								value="${entityVO.exPersonBanknum}" />
							<select style="display: none" id="userNum"
								name="contactUserBankNum">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td height="30" align="right">
							<span class="STYLE1">QQ：</span>
						</td>
						<td>
							<span id="userQq" class="qk">${entityVO.exPersonQQ}</span>
						</td>
						<td align="right">
							<span class="STYLE1">地址：</span>
						</td>
						<td>
							<span id="userAddr" class="qk">${entityVO.exPersonaddr}</span>
						</td>
						<td width="10%" align="right">
							<span class="STYLE1">个人往来关系：</span>
						</td>
						<td width="15%">
							<span id="phone" class="qk">${entityVO.exPersonRelation}</span>
							<s:select list="codeRelationList" listKey="codeValue"
								style="display:none" listValue="codeValue" id="phone"
								headerKey="" headerValue="请选择" name="entityVO.exPersonRelation"
								theme="simple"></s:select>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td height="30" align="center">
				<input type="button" class="ACT_btn resetcompany" name="button" value="重置往来单位" />
    		  <input type="button" class="ACT_btn resetperson" name="button" value="重置往来个人" />
							<input type="button" class="ACT_btn JQuerySubmitgd"
								name="button3" value="保 存" />
							<input type="button" class="ACT_btn JQueryClose" name="button2"
								value="返回" />
						</td>
					</tr>
				</table>
				<s:token></s:token>
		</form>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							选择安全检查
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								安全检查描述：
							</td>
							<td width="142">
								<input name="typeID" class="input" id="typeID" size="20"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn02" ID="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 xzwp" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0" href="#">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0" href="#">下一页</a>
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
									style="margin-top: 2px; height: 450px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
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
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
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
								<a id="dwsy" title="0" href="#">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0" href="#">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; height: 450px; width: 100%; overflow: scroll; height: 450px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>

		<form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="userjqModel">
				<div class="content1" style="width: 100%">
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
								<s:select list="codeRelationList" listKey="codeValue"
									listValue="codeValue" headerKey="" headerValue="--全部--"
									id="relation" name="relation" theme="simple"></s:select>
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
								<a id="grsy" title="0" href="#">上一页</a>
							</td>
							<td width="50">
								<a id="grxy" title="0" href="#">下一页</a>
							</td>
							<td width="70">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; display: none; height: 450px; width: 100%; overflow: scroll; height: 450px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		<div id="jqmWindow2" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="myform" />
				<input style="display: none;" id="parm1" />
				<input style="display: none;" id="parm2" />
				<input style="display: none;" id="parm3" />
			</div>
			<iframe name="ifr" id="ifr" width="100%" height="380px"
				frameborder="0"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
			</div>
		</div>
		<form name="TypeForm" id="TypeForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 10%"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							安全检查类别
							<div class="close"></div>
						</div>
					</div>
					<table width="885"  border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 50px;">
						<tr>
							<td>
								<table width="885"  border="0" id="stafftable2" align="center"
									cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 50px;">
									<tr>
										<td align="center">
											安全检查类别名称：
										</td>
										<td>
											<input name="oasafeKind.name" type="text" maxlength="25"
												id="name" class="input name put3 chinese" size="30" />
										</td>
										<td height="35" align="center">
											描述：
										</td>
										<td>
											<input name="oasafeKind.descRiption" id="descRiption" maxlength="50"
												type="text" class="input ckTextLength" size="30" />
										</td>
			        					<td rowspan="3" align="left"><img id="pic" width="99" height="135"  /></td>
										
									</tr>
									<tr>
										
										<td align="center">
											检查指标：
										</td>
										<td>
											<input name="oasafeKind.guideline" id="guideline" maxlength="50"
												type="text" class="input ckTextLength" size="30" />
										</td>
										<td align="center">
											上传检查指标附件：
										</td>
										
			         	 <td width="110" align="center">
			              <input name="photo" type="file" class="input"  size="15"  contentEditable="false"/></td>
									</tr>
									<tr>
										<td height="35" colspan="5" align="center">
											<input type="button" class="input-button saveType"
												style="cursor: pointer; width: 80px;" value="保存" />
											<input type="button" class="input-button unSave"
												style="cursor: pointer; width: 80px;" value="取消" />
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
	</body>
</html>
