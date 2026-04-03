<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ page import="hy.ea.bo.Company"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>请假申请单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
		<style type="text/css"> 
		.windowJqm{
		    left:55%;
		    width:850px;
		    margin-left:-450px;;	
		}
		.underline {
			text-decoration: underline;
		}
		.sty{
			padding-left:5px;
		}
		</style>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
       		var treeID = "<%=session.getAttribute("organizationID")%>";
	        var treeName = "<%=session.getAttribute("organizationName")%>";
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
       		var token = 0;
            var prID = ""; 
            var b=true;
            var basePath='<%=basePath%>';
            var ppageNumber='${pageNumber}';
            var psearch='${search}';
            var acceName = '';  //附件查看赋值
            var times = '0';
            var parm = '';
            var vouch = '';  //凭证号传值
	        
	    function getID(){
	    	var url= basePath + "ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
			$.ajax({
	                url: url,
	                type: "get",
	                async: true,
	                dataType: "json",
	                success: function cbf(data){
				    var member = eval("(" + data + ")");
				    var nologin = member.nologin;
					if(nologin){
						document.location.href = basePath + "page/ea/not_login.jsp";
					}
			        vouch = member.BillID;
			        $("#voucherNum1").val(vouch);
		    },
	              error: function cbf(data){
					         alert("数据获取失败！")
					 }
			});
	    }
        //选择责任人       
		function searchCoach(idNum){
			 if(parm == ''){
			 	parm = treeID;
			 }
			 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm;
			 var idN=idNum;
			 getValueForParm('leaveForm','partnerName','childPartnerName',url,idN);
		}
		function getValueForParm(attachTable,parm,parmNum,url,idNum){ //打开页面
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm",$("#jqmWindow2")).attr("value",parm);
			 $("#parmNum",$("#jqmWindow2")).attr("value",parmNum);
			  $("#idNum",$("#jqmWindow2")).attr("value",idNum);
		  	 $("#ifr").attr("src",basePath+url);
		  	 $("#jqmWindow2").jqmShow();
		}
		
		$(document).ready(function() {
			$("#isBack").click(function(){// 返回
		       $("#jqmWindow2").jqmHide();
		    }); 
		   
			$("#isSubmit").click(function(){// 选择确定
				var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
				var parm = $("#parm",$("#jqmWindow2")).attr("value");
				var parmNum = $("#parmNum",$("#jqmWindow2")).attr("value");
				var idNum = $("#idNum",$("#jqmWindow2")).attr("value");
				var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
				if(value1 == ""){
					alert("请选择");
					return;
				}
				
				var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
				var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parmNum).text();
				if(parm != "")
				$("#"+idNum,$("#"+myfrom)).attr("value",value2).trigger("blur");
				if(parmNum != "")
				$("#"+parmNum,$("#"+myfrom)).attr("value",value3).trigger("blur");
				if(idNum=="partnerName"){
				$("#principalID",$("#"+myfrom)).attr("value",value1).trigger("blur");
				}
				$("#ifr").attr("src","");
		        $("#jqmWindow2").jqmHide(); 
		        
		        if(idNum == 'partnerName'){
			        var url=basePath+"ea/publicreceipts/sajax_ea_getLeaveDays.jspa?staffIDvalue="+value1+"&date="+new Date().toLocaleString();
					$.ajax({
				            url: url,
				            type: "get",
				            async: true,
				            dataType: "json",
				            success: function cbf(data){
						    var member = eval("(" + data + ")");
						    var leaveDaysList = member.leaveDaysList;
						    if(leaveDaysList != ''){
								for(var i=0;i<leaveDaysList.length;i++){ 
									var obj =leaveDaysList[i];
									if(obj[0] == '事假' && obj[1] != null){
										$("#leavecasual",$("#leaveForm")).val(obj[1]);
									}else{
										$("#leavecasual",$("#leaveForm")).val("0");
									}
									if(obj[0] == '病假' && obj[1] != null){
										$("#leavesick",$("#leaveForm")).val(obj[1]);
									}else{
										$("#leavesick",$("#leaveForm")).val("0");
									}
								}
							}else{
								$("#leavecasual",$("#leaveForm")).val("0");
								$("#leavesick",$("#leaveForm")).val("0");
							}
				    },
				             error: function cbf(data){
							         alert("数据获取失败！");
							 }
					});
				}
            });
        });
            
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/LeaveBills.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="170" align="center">
							公司
						</th>
						<th width="80" align="center">
							部门
						</th>
						<th width="160" align="center">
							凭证号
						</th>
						<th width="80" align="center">
							责任人
						</th>
						<th width="110" align="center">
							单据类型
						</th>
						<th width="110" align="center">
							请假类别
						</th>
						<th width="80" align="center">
							申请日期
						</th>
						<th width="120" align="center">
							起日期
						</th>
						<th width="120" align="center">
							止日期
						</th>
						<th width="80" align="center">
							请假天数
						</th>
						<th width="80" align="center">
							请假小时
						</th>
						<th width="80" align="center">
							请假事由
						</th>
						<th width="80" align="center">
							工作接管人
						</th>
						<th width="80" align="center">
							职位
						</th>
						<th width="110" align="center">
							操作人(制单人)
						</th>
						<th width="110" align="center">
							报到日期
						</th>
						<th width="110" align="center">
							销假日期
						</th>
						<th width="110" align="center">
							部门主管审核人
						</th>
						<th width="110" align="center">
							人力资源部审核人
						</th>
						<th width="110" align="center">
							单据状态
						</th>
						<th width="110" align="center">
							附件
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[0]}" />
							</td>
							<td>
								<span id="companyName1">${arr[1]}</span>
							</td>
							<td>
								<span id="organizationName">${arr[2]}</span>
							</td>
							<td>
								<span id="voucherNum">${arr[3]}</span>
							</td>
							<td>
								<span id="principal">${arr[4]}</span>
							</td>

							<td>
								<span id="type">${arr[5]}</span>
							</td>
							<td>
								<span id="leaveType">${arr[19]}</span>
							</td>
							<td>
								<span id="applyDate" class="datas">${fn:substring(arr[6],0,10)}</span>
							</td>
							<td>
								<span id="leaveStartDate1">${arr[14]}</span>
							</td>
							<td>
								<span id="leaveEndDate1">${arr[17]}</span>
							</td>
							<td>
								<span id="leaveDays1">${arr[9]}</span>
							</td>
							<td>
								<span id="leaveHour1">${arr[10]}</span>
								<span style="display: none">
								<span id="leavecasual1">${arr[11]}</span>
								<span id="leavesick1">${arr[12]}</span>
								<span id="checkdiscount1">${arr[13]}</span>
								</span>
							</td>
							<td>
								<span id="leaveReason1">${arr[20]}</span>
							</td>
							<td>
								<span id="leaveReceiver">${arr[18]}</span>
							</td>
							<td>
								<span id="leavePostName1">${arr[8]}</span>
							</td>
							<td>
								<span id="operator">${arr[7]}</span>
							</td>
							<td>
								<span id="Signdate">${arr[15]}</span>
							</td>
							<td>
								<span id="Terminatedate">${arr[16]}</span>
							</td>
							<td>
								<span id="firstAuditor">${arr[21]}</span>
							</td>
							<td>
								<span id="secondAuditor">${arr[22]}</span>
							</td>
							<td>
								<span id="receiptsStatus">${arr[23]}</span>
							</td>
							<td>
								<span id="look1" style="display: none">${arr[24]}</span>
								<span id="wu" style="display: none">无</span>
								<span id="look" style="display: none"
									onclick="lookImage('${arr[24]}');"><a href="#">查看</a> </span>
								<span id="load" style="display: none"><a
									href='<%=basePath%>ea/publicreceipts/ea_downFile.jspa?downLoadPath=${arr[24]}'>下载</a>
								</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/publicreceipts/ea_getLeaveBillsList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow  jqmWindow1"
			style="width: 430px; right: 25%;; top: 10%; z-index: 10000"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
	                  	<td align="right">
	                           凭证号：                      
	                  	</td>
	                  	<td>
	                  		<input name="publicreceipts.voucherNum" />
	                  	</td>
	                </tr>
					<tr>
						<td align="right">
							请假责任人：
						</td>
						<td>
							<input name="publicreceipts.principal" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td>
							<select id="principalOrganizationID"
								name="publicreceipts.principalOrganizationID">
								<option value="">
									全部
								</option>
							</select>
						</td>
					</tr>
					<tr>
                    	<td align="right">
	                          单据状态：                      
	                    </td>
	                  	<td>
	                  	<select name="publicreceipts.receiptsStatus">
	                  		<option value="">全部</option>
	                  		<option value="P">待审</option>
	                  		<option value="F">部门主管审核通过</option>
	                  		<option value="S">人力资源部审核通过</option>
	                  		<option value="A">总经理审核通过</option>
	                  		<option value="R">驳回作废</option>
	                  		<option value="B">撤销</option>
	                  	</select>
	                  	</td>
                    </tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>

		<!-- ADD -->
		<div class="contentbannb jqmWindow windowJqm" style="top: 5%"
			id="jqModel">
			<form name="leaveForm" id="leaveForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							员工请假单
							<div class="close"></div>
						</div>
					</div>
					
				  <table width="850 " height="50" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
				    <tr>
				      <td width="80" align="right">公司：</td>
				      <td width="180"><input name="company.companyName" class="input yincang" id="companyName" readonly="readonly" value="${company.companyName}" size="30"/>
				      				  <span class="xianshi" id="companyName1"></span>
				      </td>
				      <td width="140" align="right">部门：</td>
				      <td width="200"><select id="principalOrganizationID" class="yincang" name="publicreceipts.principalOrganizationID"></select>
				      <span id="organizationName" class="xianshi"></span>
				      </td>
				      <td align="left">附件：
					      <input name="photoFileName" class="fileNum" type="hidden"
									id="accessory" size="15" />
								<input name="photo" type="file" id="accessoryName" class="input yincang"
									size="15" contentEditable="false" />
								<span id="isNull" style="display: none" class="hideAll">无</span>
								<span id="isLook" style="display: none" class="hideAll"
									onclick="lookImage(acceName);"><a href="#">查看</a> </span>
								<span id="isLoad" style="display: none" class="hideAll"><a href='#'>下载</a>
								</span>
				      </td>
				    </tr>
				    <tr>
				      <td align="right">凭证号：</td>
				      <td>
				      <input readonly="readonly" name="publicreceipts.voucherNum" id="voucherNum1" class="yincang" size="25"/>
				      <span id="voucherNum" class="xianshi"></span>
				      </td>
				      <td align="right"><span class="xx">*</span>责任人：</td>
				      	<td class="disName"><input name="publicreceipts.principalID" id="principalID" type="hidden" />
						<input id="partnerName" name="publicreceipts.principal" class="put3 yincang" value="${principal}" size="10" readonly="readonly" /> 
						<a href="#" class="yincang" onclick="searchCoach('partnerName');"> 选择</a>
						<span id="principal" class="xianshi"></span>
					  </td>
					  <td align="left">职位：
					  <input name="publicreceiptsChild.leavePostName" class="yincang" id="leavePostName" readonly="readonly" size="6" />
					  <span name="leavePostName1" class="xianshi" id="leavePostName1"></span>
					  </td>
				    </tr>
				   </table>
				   <table width="850" height="210" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td width="120" height="34" align="right"><span class="xx">*</span>申请日期：</td>
							<td width="250">
								<input name="publicreceipts.applyDate" onfocus="date(this);" class="input put3 yincang" id="applyDate1" size="20" style="margin-left: 2px;" readonly="readonly" />
								<span id="applyDate" class="xianshi sty"></span>
							</td>
							<td width="100" align="right">
								接管人：
							</td>
							<td width="375">
								<input name="publicreceiptsChild.leaveReceiver" class="input yincang" id="partnerNa" size="15" style="margin-left: 2px;" readonly="readonly" />
								<a href="#" class="yincang" onclick="searchCoach('partnerNa');">选择</a>
								<span id="leaveReceiver" class="xianshi sty"></span>
							</td>
						<tr>
							<td width="120" height="34" align="right">
								<span class="xx">*</span>请假日期：
							</td>
							<td colspan="3" class="errortime">
								<input name="publicreceiptsChild.leaveStartDate"
									onfocus="daytime(this);" class="input yincang" id="leaveStartDate"
									size="20" style="margin-left: 2px;" readonly="readonly" />
								<span class="underline xianshi sty">&nbsp;&nbsp;&nbsp;</span><span id="leaveStartDate1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span>
								至
								<input name="publicreceiptsChild.leaveEndDate"
									onfocus="daytime(this);" class="input yincang" id="leaveEndDate"
									size="20" style="margin-left: 2px;" readonly="readonly" />
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span><span id="leaveEndDate1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;</span>
								计
								<input name="publicreceiptsChild.leaveDays" class="input yincang"
									id="leaveDays" size="5" style="margin-left: 2px;" disabled="disabled"/>
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="leaveDays1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>
								天
								<input name="publicreceiptsChild.leaveHour" class="input yincang" 
									id="leaveHour" size="5" style="margin-left: 2px;" disabled="disabled"/>
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="leaveHour1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>
								小时
							</td>


						</tr>
						<tr>
							<td width="120" height="34" align="right">
								<span class="xx">*</span>请假类别：
							</td>
							<td colspan="3" id="leaveTypes">
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="事假"/>
								<font class="yincang">事假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="病假"/>
								<font class="yincang">病假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="婚假"/>
								<font class="yincang">婚假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="产假"/>
								<font class="yincang">产假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="丧假"/>
								<font class="yincang">丧假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="年休假"/>
								<font class="yincang">年休假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="探亲假"/>
								<font class="yincang">探亲假</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="出差"/>
								<font class="yincang">出差</font>
								<input type="radio" name="publicreceiptsChild.leaveType" class="yincang" value="其他"/>
								<font class="yincang">其他</font>
								<span id="leaveType" class="xianshi sty"></span>
							</td>
						</tr>
						<tr>
							<td width="120" height="34" align="right">
								考勤折扣：
							</td>
							<td colspan="3">
								<font class="sty">本月事假累计</font>
								<input name="publicreceiptsChild.leavecasual" class="input yincang"
									id="leavecasual" size="5" readonly="readonly" value="${publicreceiptsChild.leavecasual}" />
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="leavecasual1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>天,本月病假累计
								<input name="publicreceiptsChild.leavesick" class="input yincang"
									id="leavesick" size="5" readonly="readonly" value="${publicreceiptsChild.leavesick}" />
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="leavesick1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>天,本次考勤折扣 
								<input name="publicreceiptsChild.checkdiscount" class="input yincang"
									id="checkdiscount" size="5" readonly="readonly" value="${publicreceiptsChild.checkdiscount}" />
								<span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span><span id="checkdiscount1" class="underline xianshi"></span><span class="underline xianshi">&nbsp;&nbsp;&nbsp;&nbsp;</span>分。
							</td>
						</tr>
						<tr>
							<td width="120" align="right">
								<span class="xx">*</span>请假原因：
							</td>
							<td colspan="3">
								<textarea name="publicreceiptsChild.leaveReason" cols="88" 
									rows="4" class="input yincang ckTextLength" maxlength="250" 
									id="leaveReason" style="margin-left: 2px;"></textarea>
								<textarea id="leaveReason1" class="xianshi sty" readonly="readonly" 
									cols="99" rows="4" style="margin-left: 2px;"></textarea>
							</td>
						</tr>
						<!-- 
						<tr id="Tleave">
							<td align="right">
								<span class="xx">*</span>销假手续：
							</td>
							<td colspan="8">
								我于
								<input name="publicreceiptsChild.Signdate"
									onfocus="daytime(this);" class="input" id="Signdate" size="20"
									style="margin-left: 2px;" readonly="readonly" />
								到岗上班，特此销假。
								<span class="xx">*</span>销假人：
								<span id="nameShow"> <input type="text" id="partnerName"
										name="DRD" class="leaveR1" readonly="readonly"
										value="${publicreceipts.principal}" size="10"
										readonly="readonly" /> </span>
								<span class="xx">*</span>销假日期：
								<input name="publicreceiptsChild.Terminatedate"
									onfocus="daytime(this);" class="input" id="Terminatedate"
									size="20" style="margin-left: 2px;" readonly="readonly" />
							</td>
						</tr>
						-->
					</table>
					<table width="850" height="10" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
				    <tr>
				      <td align="center">
				        <input type="hidden" name="publicreceiptsChild.prID" id="prID" />
								<input type="hidden" name="publicreceiptsChild.orKey" id="orKey" />
								<input type="button" class="input-button JQueryprint xianshi"
									style="cursor: pointer; width: 80px;" value="打印预览" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交审核" />
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="返回" />
				    </tr>
				  </table>
				</div>
				<s:token></s:token>
			</form>
			<!-- 从当前部门的员工中选择责任人 -->
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<div id="jqmWindow2" class="jqmWindow"
				style="width: 95%; height: 380px; absolute; display: none; left: 1%; top: 1%; background: #eff">
				<div style="background: #efg; margin-right: 500px;">
					<input style="display: none;" id="myform" />
					<input style="display: none;" id="parm" />
					<input style="display: none;" id="parmNum" />
					<input style="display: none;" id="idNum" />
				</div>
				<iframe name="ifr" id="ifr" width="100%" height="335px"
					frameborder="0"></iframe>
				<div align="center"> 
						<input type="button" class="input-button" id="isSubmit"
						value=" 确定 " style="cursor: hand" />
						<input type="button" class="input-button" id="isBack" value=" 关闭 "
						style="cursor: hand" />
				</div>
			</div>
	</body>
</html>