<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8" %>
		<%@ page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>       
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
	        String path = request.getContextPath();
	        String basePath = request.getScheme() + "://"
	        + request.getServerName() + ":" + request.getServerPort()
	        + path + "/"; 
	        Company c = (Company)session.getAttribute("currentcompany");
        %>
        <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>金币折扣单</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
         <style type="text/css"> 
         .windowJqm{
		    left:55%;
		    width:850px;
		    margin-left:-450px;;	
		}
		.sty{
			padding-left:5px;
		}
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath %>js/ea/office_ea/goldticket.js"></script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
               <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script>
            var ppageNumber = ${pageNumber};
            var basePath = '<%=basePath %>';
            var search ='${search}';
            var token=0;
            var acceName = '';  //附件查看赋值
            var notoken =0;
            var vouch = '';  //凭证号传值
            var staff = '';  //制单人传值
            var type= '${type}';
            var treeID = "<%=session.getAttribute("organizationID")%>";
	        var treeName = "<%=session.getAttribute("organizationName")%>";
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
	        var parm;
	        var sub = "0";
	       
</script>

<script type="text/javascript">
	
	//获取凭证号
	function getID(){    
	   	var url="<%=basePath%>ea/cashierbills/sajax_ea_getBillID.jspa?date="+new Date().toLocaleString();
		$.ajax({
	               url: url,
	               type: "get",
	               async: true,
	               dataType: "json",
	               success: function cbf(data){
			    var member = eval("(" + data + ")");
			    var nologin = member.nologin;
				if(nologin){
					document.location.href ="<%=basePath%>page/ea/not_login.jsp";
				}
		        vouch = member.BillID;
		        $("#voucherNum1", $("#cstaffForm")).val(vouch);
	    },
	             error: function cbf(data){
				         alert("数据获取失败！")
				 }
		});
	}	
	//获取当前时间
	function date(){
		 var date = new Date();
		    var seperator1 = "-";
		    var seperator2 = ":";
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
		            + " " + date.getHours() + seperator2 + date.getMinutes()
		            + seperator2 + date.getSeconds();
			$("#applyDate",$("#cstaffForm")).attr("value",currentdate);
	}
	
	 //选择责任人       
	 function searchCoach(){
			 var parm =$.trim($("select.deptID").attr("value"))
			 var url = "ea/cosincumbent/ea_getStaffForCashier.jspa?checkOrgID="+ parm + "&title1=title1";
			 getValueForParm('cstaffForm','partnerName','childPartnerName',url);
			 
		}
	 //打开页面（被选择人的列表）
	 function getValueForParm(attachTable,parm,parmNum,url){ //打开页面
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm",$("#jqmWindow2")).attr("value",parm);
			 $("#parmNum",$("#jqmWindow2")).attr("value",parmNum);
		  	 $("#ifr").attr("src",basePath+url);
		  	 $("#jqmWindow2").jqmShow();
		}
	
	 function getValueAccount(attachTable,parm,parmNum,url){ //打开页面
			 $("#myform",$("#jqmWindow2")).attr("value",attachTable);
			 $("#parm",$("#jqmWindow2")).attr("value",parm);
			 $("#parmNum",$("#jqmWindow2")).attr("value",parmNum);
		  	 $("#ifr").attr("src",basePath+url);
		  	 $("#jqmWindow2").jqmShow();
		}
	 
	 //选择账户级别
	 function searchAccount(){
		 parm= $("#type", $("#cstaffForm")).val();
		 var url = "ea/goldticket/ea_getAccounts.jspa?custype="+parm;		 	 
		 getValueAccount('cstaffForm','partnerName','account',url);
	 }
	
	 $(function(){

			$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			    }); 
			   
				$("#isSubmit").click(function(){// 选择确定
					var myfrom =$("#myform",$("#jqmWindow2")).attr("value");
					var parm = $("#parm",$("#jqmWindow2")).attr("value");
					var parmNum = $("#parmNum",$("#jqmWindow2")).attr("value");
				 	
					var value1 = window.frames["ifr"].opertionID;//弹出框的页面必须声明opertionID这个参数接收id
					if(value1 == ""){
						alert("请选择")
						return;
					}
					
					var value2 = window.frames["ifr"].$('tr#'+value1).find("span#"+parm).text();//弹出框的页面存在于span中才取得到
					var value3 = window.frames["ifr"].$('tr#'+value1).find("span#"+parmNum).text();
					
					if(parmNum == "account"){//帐号信息
						var url = basePath + "ea/goldticket/sajax_ea_cusType.jspa?sccid="+value1;					
						$.ajax({
							url : url,
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var custype = member.custype;
								var staffid = member.staffid;
								var sccid = member.sccid;
								$("#typeID", $("#jqModel")).val(staffid);
								$("#sccId", $("#jqModel")).val(sccid);
								$("#custypett", $("#jqModel")).val(custype);								
								$("#type option").each(function(){
									if($(this).val()==custype){
										$(this).attr("selected",true);
										$(this).parent().attr("disabled","disabled");										
									}	
									
								});									
							},
							 error: function cbf(data){
						         alert("数据获取失败！")
						 	}						
						});							
						if(parmNum != ""){	
							$("#accountinform", $("#jqModel")).val(value3);
						}
					}else{						
						if(parm != ""){
							$("#"+parm,$("#"+myfrom)).attr("value",value2).trigger("blur");
						}
						if(parmNum != ""){
							$("#"+parmNum,$("#"+myfrom)).attr("value",value3).trigger("blur");
						}
						$("#principalID",$("#"+myfrom)).attr("value",value1).trigger("blur");//责任人
						
					}																		
					$("#ifr").attr("src","");
			        $("#jqmWindow2").jqmHide();
			    });
	})

	
	$(document).ready(function() {    //获取制单人
	   	var url="<%=basePath%>ea/goldticket/sajax_ea_getOperator.jspa?date="+new Date().toLocaleString();
		$.ajax({
	            url: url,
	            type: "get",
	            async: true,
	            dataType: "json",
	            success: function cbf(data){
			    var member = eval("(" + data + ")");
			    staff = member.staff;
			    		
	    },
	             error: function cbf(data){
				         alert("数据获取失败！")
				 }
		});	
		 //判断输入的金币
		$("#goldnum", $("#jqModel")).live('input',function(){			
			var num = $("#goldnum", $("#jqModel")).val();						
			var reg=/^[1-9]\d*$|^0$/;  
			if(reg.test(num)==true){
				if(num == 0){
					alert("惩罚金币不能为0！");
					$("#goldnum").val("");
				}else{
					$("#goldnum").val(num);
				}				
			}else{
				alert("输入格式错误，请重新输入！");
				$("#goldnum", $("#jqModel")).val("");
			}		
		});
		
		$("#goldnum", $("#jqModel")).click(function(){
			$("#goldnum", $("#jqModel")).val("");		
		});
		
		if(sub == '1'){
			$(".JQuerySubmit").unbind("click")//去除点击事件
			$(".JQuerySubmit").css({"background": "#cdcdcd"});//改变按钮的颜色
		}
		
		
		
	});
				
</script>

	</head>	
	<body >
        <div class="main_main" >
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                       <th width="40" align="center">请选择</th>
                       <th width="170" align="center">凭证号</th>
                       <th width="170" align="center">公司</th>
                       <th width="80" align="center">责任人部门</th>
                       <th width="150" align="center">责任人</th>
                       <th width="110" align="center">帐号信息</th>
                       <th width="110" align="center">帐号级别</th>
                       <th width="110" align="center">折扣金币数量</th>
                       <th width="110" align="center">折扣原因</th>
                       <th width="110" align="center">制单人</th>
                       <th width="110" align="center">制单日期</th>
                       <th width="110" align="center">部门主管审核</th>
                       <th width="110" align="center">人力资源审核部</th>
                       <th width="110" align="center">单据状态</th>
                       <th width="110" align="center">日期</th>
                       <th width="110" align="center">附件</th></tr>
                </thead>
                
               <tbody>
                    
                    <c:forEach var='arr' items="${pageForm.list}" varStatus="num">                    
                        <tr id="${fn:trim(arr[0])}"  >
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue" value="${arr[0]}" />
                           		<span id="prKey" style="display: none">${arr[17]}</span>
                           		<span id="orKey" style="display: none">${arr[18]}</span>                         		
                            	<span id="orID" style="display: none">${arr[19]}</span>
                            	<span id="prID" style="display: none">${arr[0]}</span>
                            </td>
                            <td>
                                <span id="voucherNum1">${arr[1]}</span>
                            </td>
                            <td>
                                <span id="companyName1">${arr[2]}</span>
                            </td>
                            <td>
                               <span id="opostname1">${arr[3]}</span>
                            </td>
                            <td>
                                <span id="partnerName">${arr[4]}</span>
                            </td>
                            <td>
                               <span id="accountinform">${arr[5]}</span>
                            </td>
                            <td>
                            
                            	<c:if test="${arr[6]=='0'}">
                            		  <span id="type1">平台</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='1'}">
                            		 <span id="type1">税务</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='2'}">
                            		 <span id="type1">公司</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='3'}">
                            		 <span id="type1">合伙创业</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='4'}">
                            		 <span id="type1">微分金</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='5'}">
                            		 <span id="type1">代理商</span>
                            	</c:if>
                            	<c:if test="${arr[6]=='6'}">
                            		 <span id="type1">vip客户</span>
                            	</c:if>
	                            <c:if test="${arr[6]=='7'}">
	                            	<span id="type1">普通客户</span>
	                            </c:if>          
                            </td>
                            <td>
                               <span id="goldnum">${arr[7]}</span>
                            </td>
                            <td>
                               <span id="finereason">${arr[8]}</span>
                            </td>
                            <td>
                                <span id="operator1">${arr[9]}</span>
                            </td>
                            <td>
                                <span id="applyDate" class="datas">${fn:substring(arr[10], 0, 10)}</span>
                            </td>
                            <td>
                                <span id="firstAuditor">${arr[11]}</span>
                            </td>
                            <td>
                                <span id="secondAuditor">${arr[12]}</span>
                            </td>                                                                            
                            <td>
                            	<c:if test="${arr[14]=='P'}">
                            		 <span id="receiptsStatus">待审</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='F'}">
                            		 <span id="receiptsStatus">部门主管审核通过</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='S'}">
                            		 <span id="receiptsStatus">人力资源部审核通过</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='A'}">
                            		 <span id="receiptsStatus">总经理审核通过</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='R'}">
                            		 <span id="receiptsStatus">驳回作废</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='D'}">
                            		 <span id="receiptsStatus">草稿</span>
                            	</c:if>
                            	<c:if test="${arr[14]=='B'}">
                            		 <span id="receiptsStatus">撤销</span>
                            	</c:if>                          
                            </td>
                            <td>
                            	<c:choose>
                            		<c:when test="${arr[14]=='R'}">
                            		    <span id="refundDate" class="datas">${fn:substring(arr[20], 0, 10)}</span>                        		
                            		</c:when>
                            		<c:otherwise>
                            			<span id="finedate" class="datas">${fn:substring(arr[15], 0, 10)}</span>                          			
                            		</c:otherwise>                       	
                            	</c:choose>                            	
                            </td>
                            <td >
                          		<span id="look1" style="display:none">${arr[16]}</span>
                            	<span id="wu" style="display:none">无</span>
                            	<span id="look" style="display:none" onclick="lookImage('${arr[16]}');"><a href="#">查看</a></span>
                             	<span id="load" style="display:none"><a href='<%=basePath%>ea/goldticket/ea_downFile.jspa?downLoadPath=${arr[16]}'>下载</a></span>
                            </td>
                        </tr> 
                    </c:forEach>
                </tbody>            
            </table>
            
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" 
                	value="ea/goldticket/ea_getListGoldTicket.jspa?&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
        
        
        
        <!--提交审核页面  -->
 		<div class="contentbannb jqmWindow windowJqm" style="top: 15%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
                <div class="contentbannb">
                	<div class="drag">金币折扣<div class="close" ></div></div>
                </div>  
				  	<table width="850 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
					    <tr>
					      <td width="80" align="right">公司：</td>
					      <td width="200"><input name="company.companyName" class="input yincang" id="companyName" readonly="readonly" value="${company.companyName}" size="30"/></td>
					      <td width="110" align="right">部门：</td>
					      <td width="190"><select id="deptID" name="publicreceipts.principalOrganizationID" class="deptID yincang"></select>
					      	  <span class="xianshi" id="organizationName"></span>
					      </td>
					      <td width="70" align="right"><span class="xx">*</span>责任人：</td>
					      <td class="disName"><input name="publicreceipts.principal" class="input put3 " id="partnerName" size="10" style="margin-left:2px;" />
						      <input name="publicreceipts.principalID"  id="principalID" type="hidden"/>
						      <input name="publicreceipts.prKey"  id="prKey" type="hidden" />
						      <input name="publicreceiptsChild.orKey"  id="orKey" type="hidden"/>						     
						      <input name="publicreceiptsChild.orID"  id="orID" type="hidden"/>	
						      <input name="publicreceipts.prID"  id="prID" type="hidden"/>	
						      					     						      
						      <a href="#" class="yincang" onclick="searchCoach();">选择</a>
							  <span class="xianshi" id="partnerName1"></span>
						  </td>
					   </tr>
					   <tr>
					      <td align="right">凭证号：</td>
					      <td>
						      <input class="input yincang" name="publicreceipts.voucherNum" id="voucherNum1" size="25" style="margin-left:2px;" readonly="readonly"/>
					      </td>
					      <td align="right"><span class="xx">*</span>帐号级别：</td>
						  <td class="disName" >
						  	  <input name="publicreceipts.custype" value="${publicreceipts.custype }" id="custypett" type="hidden"/>							  	  
						  	  <select id="type" class="put3 yincang" ">
						  		  <option value=""></option>
								  <option value="0">平台</option>
								  <option value="1">税务</option>
								  <option value="2">公司</option>
								  <option value="3">合伙创业</option>
								  <option value="4">微分金</option>
								  <option value="5">代理商</option>
								  <option value="6">vip客户</option>
								  <option value="7">普通客户</option>								  
						  	  </select>				  	 
						  </td>
						  
						  <td width="70" align="right"><span class="xx">*</span>帐号信息：</td>
					      <td class="disName"><input name="publicreceipts.accountinform"  class="input put3" id="accountinform" size="10" style="margin-left:2px;" readonly="readonly"/>				     
						      <input name="publicreceipts.accountinformID"  id="typeID" type="hidden"/>
						      <input name="publicreceipts.refundSccid"  id="sccId" type="hidden"/>
						      <a href="#" class="yincang" onclick="searchAccount()">选择</a>
							  <span class="xianshi" id="acccount1"></span>
						  </td>
					  </tr>	  
					  <tr>						  
						  <td  width="100" align="right">制单人：</td>
					      <td  width="170"><input class="input yincang" id="operator1" size="10" name="publicreceipts.operator" readonly="readonly"/>
					      	  <span class="xianshi sty" id="operator"></span>
					      </td>
					      <td width="100" align="right">制单日期：</td>
			      			<td >
			      				<input name="publicreceipts.applyDate" class="input yincang" id="applyDate" size="20" readonly="readonly" />
						     	 <span class="xianshi sty" id="rorpDate1"></span>
						     </td>					  
						     		  						  
					      <td align="right">附件：</td>
					      <td><input name="publicreceipts.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
							  <input name="photo" type="file" class="input yincang" size="10" contentEditable="false" id="photo"/>
						      <span id="isNull" style="display:none" class="hideAll">无</span>
						      <span id="isLook" style="display:none" onclick="lookImage(acceName);" class="hideAll"><a href="#">查看</a></span>
						      <span id="isLoad" style="display:none" class="hideAll"><a href='#'>下载</a></span>
					      </td>
					  </tr>
				  </table>
				  
				  <table width="850" height="150"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">	      
				      <tr>
				      	  <td width="100" height="34" align="right"><span class="xx">*</span>折扣金币数：</td>
					      <td width="400"><input name="publicreceiptsChild.fineCount" value="${publicreceiptsChild.fineCount }" class="input put3 Point yincang" id="goldnum" size="20" /></td>				      					     
				     </tr>
				     <tr>
					      <td width="100" height="76" align="right"><span class="xx">*</span>折扣原因：</td>
					      <td colspan="5">
					      	<textarea name="publicreceiptsChild.fineReason" cols="90" rows="4" class="input yincang ckTextLength" maxlength="250" id="finereason" style="margin-left:2px;"></textarea>
					      </td>
				     </tr>    
				  </table>
  
  
				  <table width="850" height="20" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
				    <tr>
				      <td align="center">
					      <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交审核" />
					      <input type="button" class="input-button JQueryDraft" style="cursor:pointer;width:80px;" value="保存草稿" />					      
					      <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="返回" />
				       </td>
				    </tr>
				  </table>
			  </div>   
           </form>
       </div>
     
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
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
							责任人：
						</td>
						<td>
							<input name="publicreceipts.principal" />
						</td>
					</tr>
					<tr>
						<td align="right">
							帐号信息：
						</td>
						<td>
							<input name="publicreceipts.accountinform" />
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
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
           <div id="jqmWindow2" class="jqmWindow" style="width: 95%; height: 420px; absolute; display: none; left: 1%; top: 1%; background: #eff">
				<div style="background: #efg; margin-right: 500px;">
					<input style="display: none;" id="myform" />
					<input style="display: none;" id="parm" />
					<input style="display: none;" id="parmNum"/>
				</div>
				<iframe name="ifr" id="ifr" width="100%" height="380px" frameborder="0"></iframe>
				<div align="center"> 
					<input type="button" class="input-button" id="isSubmit"value=" 确定 " style="cursor: hand" />
					<input type="button" class="input-button" id="isBack" value=" 关闭 " style="cursor: hand" />
				</div>
		 </div>
    </body>
</html>