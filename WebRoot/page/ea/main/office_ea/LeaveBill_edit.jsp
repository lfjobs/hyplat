<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>员工请假申请单</title>
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
		<style type="text/css"> 
		.input-buttons {
			line-height: 18px;
			height: 21px;
			margin: 8px;
			border: 1px solid #336699;
			background: url(../../js/jqModal/css/images/headbg.gif) repeat-x 0px
				-2px;
		}
		
		#table input {
			width: 180px;
		}
		
		#apDiv1 {
			position: absolute;
			left: 750px;
			top: 387px;
			width: 63px;
			height: 32px;
			z-index: 1;
		}
		.beizhutable {
			border-collapse:collapse;
			border-left:1px solid #a8c7ce;
			border-right:1px solid #a8c7ce;
			font-size:12px;
			table-layout:fixed
			
		}
		</style>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>

		<script type="text/javascript">
    		var acceName = '${publicreceipts.accessory }';  //附件查看赋值
            var ppageNumber = ${pageNumber};
            var basePath = '<%=basePath%>';
            var search ='${search}';
            var labelTag="${labelTag}";
            var token=0;
            var notoken=0;
            var prID = '${publicreceipts.prID}';
            var pnums = '${pageForm.pageNumber}';
            
            $(document).ready(function(){
            	if(labelTag=='00'){
            	$("#star").hide();
            	$("#checkdiscount").attr("readonly","readonly");
            	
            	$("input.JQuerySubmit").attr("value","主管审核通过")
            	$("input.JQueryNoSubmit").attr("value","主管驳回作废")
            	}else{
            	$("input.JQuerySubmit").attr("value","人事审核通过")
            	$("input.JQueryNoSubmit").attr("value","人事驳回作废")
            	
            	if($.trim($("span#receiptsStatus").text()) == '人力资源部审核通过'){  //撤销显示
            		$("input.JQuerySubmit").hide();
            		$("input.JQueryNoSubmit").hide();
            		$("input.JQueryBackSubmit").show();
            	}
            	}
            	$("input.JQueryBackSubmit").click(function(){   //撤销
            		if(confirm("确定撤销？")){
		               document.location.href=basePath+"ea/publicreceipts/ea_toBackBills.jspa?prID="+prID+"&publicreceipts.type=员工请假申请单&labelTag=01";
	                }           	
            	})
            	$("input.JQueryreturn").click(function(){  //返回
            		document.location.href=basePath+"ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="+ppageNumber+"&pageForm.pageNumber="+pnums+"&search="+search+"&labelTag="+labelTag;           	
            	})
            	$("input.JQuerySubmitPrint").click(function(){  //打印预览
            		window.open(basePath+"ea/publicreceipts/ea_printLeaveBill.jspa?prID="+prID);           	
            	})
            })
            
              function AjaxFunc(types){
             		var checkdiscount=$.trim($("input#checkdiscount").attr("value"));
              		if(types=="人事审核通过"&&(checkdiscount<=0 ||isNaN(checkdiscount))){
              			alert("考勤折扣分数不能为空,且必须为正数！");
              			notoken=0;
              			return;
              		}
              		if(notoken){
              		alert("正在加载数据")
              		return;
              		}
              		
              		notoken=1;
                	var receiptsStatus=$.trim($("span#receiptsStatus").text()); 
                	var prID=$.trim($("input#prID").attr("value"));
                          if(receiptsStatus==(labelTag=='00'?'待审':'部门主管审核通过')){
                          	 var url = basePath+"ea/publicreceipts/sajax_ea_AjaxPublicreceiptsAudit.jspa?prID="+prID+"&date1="+new Date()+"&types="+types+"&checkdiscount="+checkdiscount;
          						   $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
						                        var member = eval("(" + data + ")");
									            var publicrece = member.publicrece;
									            var typet;
									            if(types=='主管审核通过'){
									           		 typet="部门主管审核通过";
									           		 $("span#firstAuditor").text(publicrece)
									            }else if(types=='主管驳回作废'){
									            	 typet="驳回作废";
									            	 $("span#firstAuditor").text(publicrece)
									            }else if(types=='人事审核通过'){
									            	 typet="人力资源部审核通过";
									            	 $("span#secondAuditor").text(publicrece)
									            }else if(types=='人事驳回作废'){
									            	 typet="驳回作废";
									            	 $("span#secondAuditor").text(publicrece)
									            }
									            $("span#receiptsStatus").text(typet);
									               alert("操作成功!");
									               document.location.href=basePath+"ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="+ppageNumber+"&search="+search+"&labelTag="+labelTag;
						                        },
						                        error: function cbf(data){
						                            alert("数据获取失败！")
						                           notoken=0;
						                        }
						                    }); 
                          }else{
                          	alert("数据已归档");
                          	notoken=0;
                          	return;
                          }
                
                }	
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/OverTime_edit.js"></script>
	</head>

	<body>
		<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 5%position: absolute;margin-left: 15%;"
			id="jqModel">
			<form name="leaveForm" id="leaveForm" method="post"
				enctype="multipart/form-data">
				<div class="content">
					<table width="850 " height="46" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td height="30" colspan="6" align="center">
								<font style="font-weight:bold;font-size:15px">员工请假申请单</font>
							</td>
						</tr>
						<tr>
							<td width="80" align="right">
								公司：
							</td>
							<td width="240">
								${companyname}
							</td>
							<td width="180" align="right">
								部门：
							</td>
							<td width="200">
								${organizationname }
							</td>
							<td width="150" align="right">
								责任人：
							</td>
							<td width="100" class="td_bg disName">
								${publicreceipts.principal }</td>
						</tr>
						<tr>
							<td align="right">
								凭证号：
							</td>
							<td width="90">
								${publicreceipts.voucherNum }
							</td>
							<td align="right">
								申请日期：
							</td>
							<td align="left">
								${fn:substring(publicreceipts.applyDate,0,10)}
							</td>
							<td width="150" align="right">
								附件：
							</td>
							<td width="163">
								<s:if test="publicreceipts.accessory == null">
									<span id="isNull">无</span>
								</s:if>
								<s:else>
									<span id="look" onclick="lookImage(acceName);"><a
										id="isLook" href="#">查看</a>
									</span>
									<span id="load" style="display: none"><a href="#">下载</a>
									</span>
								</s:else>
							</td>
						</tr>
					</table>
					<table width="847" height="140" border="0" align="center" cellpadding="0"
						cellspacing="0" class="table" style="background: #FFFFFF;">

						<tr>
							<td width="140" height="30" align="right">
								请假类别：
							</td>
							<td width="283" colspan="1" align="left">
								<font style="margin-left:2px;">${publicreceiptsChild.leaveType}</font>
							</td>
							<td width="140" height="30" align="right">
								工作接管人：
							</td>
							<td width="" colspan="1" align="left">
								<font style="margin-left:2px;">${publicreceiptsChild.leaveReceiver}</font>
								
							</td>
						</tr>
						<tr>
							<td width="140" height="30" align="right">
								请假时间：
							</td>
							<td colspan="3" align="left">
								<span style="padding-left:5px">自</span>
								<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.leaveStartDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>至
								<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.leaveEndDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
								共计
								<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.leaveDays}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>天
								<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${publicreceiptsChild.leaveHour}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>小时
							</td>
						</tr>
						<tr>
							<td width="140" height="30" align="right">
								考勤折扣：
							</td>
							<td colspan="3" align="left">
								<span style="padding-left:5px">本月事假累计</span>
								<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<c:if test="${publicreceiptsChild.leavecasual != null}">${publicreceiptsChild.leavecasual}</c:if>
								<c:if test="${publicreceiptsChild.leavecasual == null}">0</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>天,
								本月病假累计<span style="text-decoration:underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<c:if test="${publicreceiptsChild.leavesick != null}">${publicreceiptsChild.leavesick}</c:if>
								<c:if test="${publicreceiptsChild.leavesick == null}">0</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>天
								&nbsp;&nbsp;&nbsp;&nbsp;<span id="star" style="color:red">*</span>本次考勤折扣：
								<input name="publicreceiptsChild.checkdiscount" class="input" id="checkdiscount" size="5" value="${publicreceiptsChild.checkdiscount}" />
								分
							</td> 
						</tr>
						</table>
						<table width="847" height="60" border="0" align="center"
							cellpadding="0" cellspacing="0" class="beizhutable"
							style="background: #FFFFFF;">
						<tr>
							<td width="140" height="60" align="right" style="border-right:1px solid #a8c7ce;">
								请假原因：
							</td>
							<td> 
								${publicreceiptsChild.leaveReason } 
							</td>
						</tr>
					</table>
					<table width="838" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td width="140" align="right">
								部门负责人审核：
							</td>
							<td width="80" align="center"><span id="firstAuditor">
								<font style="margin-left:2px;">${publicreceipts.firstAuditor }</font></span>
							</td>
							<td width="105" align="right">
								人力资源部审核：
							</td>
							<td width="80" align="center"><span id="secondAuditor">
								<font style="margin-left:2px;">${publicreceipts.secondAuditor}</font></span>
							</td>
							<td width="98" align="right">
								总经理审核：
							</td>
							<td width="80">
							</td>
							<td width="80" align="right">
								单据状态：
							</td>
							<td width="175" align="center"><span id="receiptsStatus">
								<c:if test="${publicreceipts.receiptsStatus =='P'}"><font style="margin-left:2px;">待审</font></c:if>
							    <c:if test="${publicreceipts.receiptsStatus =='F'}"><font style="margin-left:2px;">部门主管审核通过</font></c:if>
							    <c:if test="${publicreceipts.receiptsStatus =='S'}"><font style="margin-left:2px;">人力资源部审核通过</font></c:if>
							    <c:if test="${publicreceipts.receiptsStatus =='A'}"><font style="margin-left:2px;">总经理审核通过</font></c:if>
							    <c:if test="${publicreceipts.receiptsStatus =='R'}"><font style="margin-left:2px;">驳回作废</font></c:if>
							    <c:if test="${publicreceipts.receiptsStatus =='B'}"><font style="margin-left:2px;">撤销</font></c:if></span>
							</td>
						</tr>
	    				<input id="prID" type="hidden" value="${publicreceipts.prID}"/>		
					</table>
					<table width="850" height="40" border="0" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td colspan="10" align="center">
								<input type="button" class="input-buttons JQuerySubmitPrint" 
									style="cursor:pointer;width:80px;" value="打印预览" />
								<input type="button" class="input-buttons JQuerySubmit"
									style="cursor: pointer; width: 80px;"
									onclick="AjaxFunc(this.value);" value="审核通过" />
								<input type="button" class="input-buttons JQueryNoSubmit"
									style="cursor: pointer; width: 80px;"
									onclick="AjaxFunc(this.value)" value="驳回作废" />
								<input type="button" class="input-buttons JQueryBackSubmit" 
									style="cursor:pointer;width:80px;display:none;" value="撤销" />
								<input type="button" class="input-buttons JQueryreturn"
									style="cursor: pointer; width: 80px;" value="返回" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
	</body>
</html>
