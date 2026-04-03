<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>金币折扣单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%@ page import="hy.ea.bo.human.publicreceipts.Publicreceipts" %>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

		
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

		<style>
			.table{
				border: none;
			}
			.table td {
    			border: 1px solid #000;		
    			color: #333;
	    		padding: 0 5px;
			}
		    .table2{
		    	background: #FFFFFF;
   				height: 30px;
   				text-align: right;
		    }
		     .table2 td{
		     	border: none;
		     }
		     .input{
		     	border: none;
		     }
	         .tr2 td{
	         	border-top: none;
	         }
	         
	         
	         /* 2016.12.28 */
	         
	         
	         .audit_x_{
	         	    width: 100%;
				    height: 100%;
				    position: fixed;
				    top: 0;
				    left: 0;
				    background: rgba(0, 0, 0, 0.2);
			    	display: none;
	         }
	         .audit_x{
       		        width: 300px;
				    height: 120px;
				    border: 1px solid #000;
				    padding: 20px;
				    background: #fff;
				    position: fixed;
				    top: 240px;
				    left: 50%;
				    margin: -60px 0 0 -150px;
	         }
	         .audit_x .con{
	         	overflow: hidden;
	         }
	         .audit_x .con p{
	         	font-size: 14px;
	         	float: left;
	         }
	         .audit_x .con textarea{
             	width: 200px;
			    height: 70px;
			    font-size: 12px;
			    padding: 2px;
			    float: left;
			    border: 1px solid #000;
			    margin-left: 20px;
			    resize: none;
			}
			.audit_x .btn{
				margin: 20px 0 0 0;
			}
			.audit_x .btn input{
				width: 130px;
				height: 23px;
			    line-height: 22px;
				font-size: 12px;
				border: 1px solid #000;
				border-radius: 3px;
		    	
			}
			.audit_x .btn #qx{
			 	margin-left: 10px;
			}
   
   			#last{
				text-align: left;
			    padding: 20px 0;
   			}
   			#last .txt .mil{
   				overflow: hidden;
   				padding-left: 40px;
   			}
   			#last .txt .mil li{
   				float: left;
   				list-style-type:none;
   				overflow: hidden;
   			}
   			#last .txt .mil li h5{
   				font-size: 12px;
   				font-weight: 500;
   				float: left;
   			}
   			#last .txt .mil li p{
   				float: left;
   			}

	         
		</style>
		<script>
			var basePath = '<%=basePath %>';
//			var labelTag=='00';			
			var ppageNumber = ${pageNumber};
			var pnums = '${pageForm.pageNumber}';
			var labelTag="${labelTag}";
			var prID = '${publicreceipts.prID}';
			var search ='${search}';
			var types;
			var flag = "${flag}";
			var receiptsStatus='';
			var sumbit = "0";
		</script>
		
	</head>
	<body>
		<div id="tableprint" align="center">
			<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;padding-bottom:20px;">			 		 
				<tr>
					<td height="25" align="center" style="font-weight: bold;font-size:20px">
						&nbsp;金币折扣单
					</td>
				</tr>
			</table>
			
			<table width="850" height="150"  border="0" align="center" cellpadding="0" cellspacing="0" class="table table2" style="background:#FFFFFF;height: 30px;text-align: right;" >	      
				  <tr>			   
				    <td width="11%" align="right" height="10"></td>
				    <td width="10%" height="10">凭证号：<% Publicreceipts data =(Publicreceipts)request.getAttribute("publicreceipts");
															if (data != null) {
																StringBuffer barCode = new StringBuffer();
																barCode.append("<img src='");
																barCode.append(request.getContextPath());
																barCode.append("/CreateBarCode?data=");
																barCode.append(data.getVoucherNum());
																barCode
																		.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' wdith='200'>");
																out.println(barCode.toString());
															} else {
																out.println("no data");
															}
													%>
													
					   </td>								
				  </tr>
				  <tr>			   
				    <td width="11%" align="right" height="10"></td>
				    <td width="10%" height="10">${publicreceipts.voucherNum}</td>
				  </tr>
			</table>
					
			<table width="850" height="150"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">	      
					    <tr>
					      <td width="80" align="right">公司：</td>
					      <td width="200">${company.companyName}</td>
					      <td width="110" align="right">部门：</td>
					      <td width="190">${organizationName}</td>
					      <td width="70" align="right">责任人：</td>
					      <td width="190">${publicreceipts.principal}</td>
					   </tr>
					   <tr>					      			     
					      <td width="110" align="right">帐号级别：</td>
						  <td width="190">
						  		<c:if test="${publicreceipts.custype=='0'}">平台</c:if>
	                           	<c:if test="${publicreceipts.custype=='1'}">税务</c:if>
	                           	<c:if test="${publicreceipts.custype=='2'}">公司</c:if>
	                           	<c:if test="${publicreceipts.custype=='3'}">合伙创业</c:if>
	                           	<c:if test="${publicreceipts.custype=='4'}">微分金</c:if>
	                           	<c:if test="${publicreceipts.custype=='5'}">代理商</c:if>
	                           	<c:if test="${publicreceipts.custype=='6'}">vip客户</c:if>
	                            <c:if test="${publicreceipts.custype=='7'}">普通客户</c:if> 						  						  						  						 					  						  
						  </td>	
						  <td  width="100" align="right">制单日期：</td>
			      		  <td  width="100">${publicreceipts.applyDate}</td>					  						  					   
					      <td width="70" align="right">帐号信息：</td>						  
					      <td width="190">${publicreceipts.accountinform}</td>
					  </tr>	  
					  <tr>						  
						  <td  width="100" align="right">制单人：</td>
					      <td  width="170">${publicreceipts.operator}</td>
					      <td  width="100" align="right">单据状态：</td>
			      		  <td  width="100"><span id="receiptsStatus">
		      		  		 <c:if test="${publicreceipts.receiptsStatus =='P'}"><font style="margin-left:2px;">待审</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='F'}"><font style="margin-left:2px;">部门主管审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='S'}"><font style="margin-left:2px;">人力资源部审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='A'}"><font style="margin-left:2px;">总经理审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='R'}"><font style="margin-left:2px;">驳回作废</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='B'}"><font style="margin-left:2px;">撤销</font></c:if></span>
						  	 <c:if test="${publicreceipts.receiptsStatus =='D'}"><font style="margin-left:2px;">草稿</font></c:if></span>						  	
						  </td>
					      
					      <td align="right">附件：</td>
					      <td>
					         <c:choose>
					         	<c:when test="${publicreceipts.accessory == null}">
					         		<span >无</span>				      					         	
					         	</c:when>
					         	<c:otherwise>
					         		<span >有</span>						         						         	
					         	</c:otherwise>				        
					         </c:choose>				      	
					      </td>					      					  						     		  						  				      
					  </tr>
				  </table>
				  
				  <table width="850" height="150"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">	      
				      <tr class="tr2">
				      	  <td width="100" height="34" align="right">折扣金币数：</td>
					      <td width="400">${publicreceiptsChild.fineCount}</td>				      					     
				     </tr>
				     <tr>
					      <td width="100" height="76" align="right">折扣原因：</td>
					      <td >${publicreceiptsChild.fineReason}</td>
				     </tr>	
				      <tr id="auditing"> 
					      <td width="100" height="76" align="right" colspan="2" id="last">
					      		<div class="txt">
					      			<ul class="mil" >
					      				<li style="width:300px;">
						      				<h5>审核意见：</h5>
						      				<p style="width:230px;">${publicreceipts.firstAuditComments}</p>
					      				</li>
					      				<li style="width:180px;">
						      				<h5>主管部分审核：</h5>	
						      				<p style="width:80px;">${publicreceipts.firstAuditor}</p>					      									      				
					      				</li>					      									      									      									   
					      			</ul>
					      			<br />
					      			<ul class="mil" id="mil">
					      				<li style="width:300px;">
						      				<h5>审核意见：</h5>
						      				<p style="width:230px;">${publicreceipts.secondAuditComments}</p>
					      				</li>
					      				<li style="width:180px;">
						      				<h5>人力资源部审核：</h5>
						      				<p style="width:80px;">${publicreceipts.secondAuditor}</p>						      											      							      									      				
					      				</li>					      								   
					      			</ul>
					      		</div>	
					      	
					      </td>					      
				     </tr>
				     <tr class="tr2">
				      	  <td width="100" height="34" align="right">审核状态：</td>
					      <td width="400" id="p">
					      	 <c:if test="${publicreceipts.receiptsStatus =='P'}"><font style="margin-left:2px;">待审</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='F'}"><font style="margin-left:2px;">部门主管审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='S'}"><font style="margin-left:2px;">人力资源部审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='A'}"><font style="margin-left:2px;">总经理审核通过</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='R'}"><font style="margin-left:2px;">驳回作废</font></c:if>
						     <c:if test="${publicreceipts.receiptsStatus =='B'}"><font style="margin-left:2px;">撤销</font></c:if></span>
						  	 <c:if test="${publicreceipts.receiptsStatus =='D'}"><font style="margin-left:2px;">草稿</font></c:if></span>	
					      </td>				      					     
				     </tr>				    					    
				  </table>
				  
				  				  				  			 				  
			<br /><br />					
																			
			<div id="look">
				<table width="840" height="60" border="0" align="center"cellpadding="0" cellspacing="0"style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td col	span="10" align="center">
						  <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" onclick="AjaxFunc(this.value);" value="审核通过" />
					      <input type="button" class="input-button JQueryNoSubmit" style="cursor:pointer;width:80px;" onclick="AjaxFunc(this.value)" value="驳回作废" />
					      <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="返回" />
						</td>
					</tr>
					 <input id="prID" type="hidden" value="${publicreceipts.prID}"/>	
				</table>			
			</div>	
			
		<!--审核意见窗口 -->				
		<div class="auditComments" style="width: 400px; right: 25%;; top: 10%" id="auditComments">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />				
				<div class="audit_x_">
					<div class="audit_x">
					 	<div class="con">
					 		<p>审核意见:</p>
					 		<textarea rows="4" placeholder="审核意见" id="comments"></textarea>
						</div>
						<div class="btn">
							<input type="button" id="pass"  value="确认审核（不）通过">
							<input type="button" value="取消" id="qx">
						</div>
					</div>	
				</div>											
			</form>
		</div>
										
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
	<script type="text/javascript">
	
	 function AjaxFunc(type){
		 
		 var receiptsStatus = $.trim($("span#receiptsStatus").text()); 
		 if(type == '审核通过' ){
			 $("#pass").val("审核通过");
		 }else if(type == '驳回作废'){
			 $("#pass").val("驳回作废");
		 }
		$(".audit_x_").show();			
		
		var prID=$.trim($("input#prID").attr("value"));
		if(receiptsStatus == '待审' && type == '审核通过' ){			
			types = '部门主管审核通过';
		}else if(receiptsStatus == '待审' && type == '驳回作废'){			
			types = '部门主管驳回作废';
		}else if(receiptsStatus == '部门主管审核通过' && type == '审核通过'){			
			types = '人力资源部审核通过';
		}else if(receiptsStatus == '部门主管审核通过' && type == '驳回作废'){			
			types = '人力资源部驳回作废';
		}					 			 
	 }	
	 
		$(document).ready(function(){
			var status="${publicreceipts.receiptsStatus }";		
			if(flag  == ""){
				$("#look").hide();
			}
			
			if(status =='P' || status == 'D'){
				$("#auditing").hide();				
			}else if(status =='F'){
				$("#mil").hide();
			}
							
		$("input.JQueryreturn").click(function(){   //返回
			document.location.href = basePath
			+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="
			+ ppageNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&labelTag="
			+ labelTag;       	
		});	
		
		$("#qx").click(function(){
			document.postSearchForm.reset();
			$(".audit_x_").hide();			
		});
		
		
		if(sumbit == '0'){
			sumbit = '1';
			$("#pass").click(function(){		
				$(".audit_x_").hide();	
				var view = $("#comments").val();		
				var url = basePath+"ea/goldticket/sajax_ea_PublicreceiptsAudit.jspa?prID="+prID+"&types="+types+"&view="+view;
				$.ajax({
				  	  url: encodeURI(url),
	                 type: "get",
	                 async: false,
	                 dataType: "json",
	                 success: function cbf(data){
	                 var member = eval("(" + data + ")");
		              var str = member.str;	 
		              if(str == '该用户没有金币，系统驳回！'){
		            	  $("#p").text(str);	            	  
		              }
			            alert(str);
			            document.location.href = basePath
						+ "ea/publicreceipts/ea_getListPublicreceipts.jspa?pageNumber="
						+ ppageNumber + "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value") + "&labelTag="
						+ labelTag;
	                 },
	                 error: function cbf(data){
	                     alert("数据获取失败！");
	                 }
				});	
	    									
			});
		}else{
			$("#pass").unbind("click")//去除点击事件
			$("#pass").css({"background": "#cdcdcd"});//改变按钮的颜色
		}
		
		});	
	
		
	
	</script>
</html>
