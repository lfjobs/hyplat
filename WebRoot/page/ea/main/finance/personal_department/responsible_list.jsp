<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
         <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>主管审核管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/responsible_list.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var treeNames = "<%=session.getAttribute("organizationName")%>";
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var host="${host}";
        	var hostStatus="${cashierBillsVO.status}";
        	var sdate="${sdate}";
        	var edate="${edate}";
			
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"ea/responsible/ea_toSearch.jspa?pageNumber="+pNumber);
                    	document.SearchForm.submit.click();
					}
    			}
			} 
			
			function fj(cID) {
				var statusfj = $("tr#" + cID).find("span#status").text();
				if (statusfj != '05') {
					alert("已经归档不能修改附件");
					return;
				}
				window.open(basePath
						+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
			}
			
		/**
		* 操作全选复选框事件
		**/
		function doCheck(obj)
		{
			var str="";
			var inputs=document.getElementsByTagName("input");
			for(var i=0;i<inputs.length;i++)
			{
				if(inputs[i].type=="checkbox" && inputs[i].id=="chkMsgId23") //刷选出所有复选框
				{
					inputs[i].checked=obj.checked; 
					if(inputs[i].checked==true){
						 str=str+inputs[i].value+",";
					}
				}	
			}
			cashierBillsID=str;
		}
		
		/**
		* 复选框变化  全选按钮变化
		**/
		function toChkSon(obj)
		{
			if(obj.checked==false) //当此复选框未选中 全选为未选
			{
				document.getElementById("chkMsgId").checked=obj.checked;
				return ;
			}
		
		   
			var chkInputs=getCheckBox(); //获取所有复选框
			var j=0;
			var str="";
			for(var i=0;i<chkInputs.length;i++)
			{
				if(chkInputs[i].checked==obj.checked){
					if(chkInputs[i].checked==true){
						 str=str+chkInputs[i].value+",";
					}
					j++;
				}else
					break;
			}	
			
			cashierBillsID=str;
			if(j==chkInputs.length) //当所有复选框为同一状态时 赋值全选同一状态
				document.getElementById("chkMsgId").checked=obj.checked;
			
		}
		
		/**
		* 获取所有复选框
		**/
		function getCheckBox()
		{
			var inputs=document.getElementsByTagName("input");
			var chkInputs=new Array();
			var j=0;
			for(var i=0;i<inputs.length;i++)
			{
				if(inputs[i].type=="checkbox" && inputs[i].id=="chkMsgId23") //刷选出所有复选框
				{
					chkInputs[j]=inputs[i].checked;					
					j=j+1;
				}
			}
			return chkInputs;
		}	

		function toChkSon(){	
			var chkInputs=document.getElementsByTagName("input");
			var str="";
			for(var i=0;i<chkInputs.length;i++){
				if(chkInputs[i].type=="checkbox" && chkInputs[i].id=="chkMsgId23" &&　chkInputs[i].checked==true){
					str=str+chkInputs[i].value+",";
				};
			}				
			cashierBillsID=str;
		}

        </script>
    </head>
<body>
	<form name="CashierBillsform" id="CashierBillsform">
		<input type="submit" name="submit" style="display:none" />
		<input name="comments" id="comments2" style="display:none" />
		<input name="cashierBills.cashierBillsID" id="cashierBillsID"
			style="display:none" />
		<input name="cashierBills.status" id="status" style="display:none" />
		<s:token />
	</form>
	<table class="flexme11">
		<thead>
			<tr>
				<th width="30" align="center"><input type="checkbox"
					name="chkMsgId" id="chkMsgId" onclick="doCheck(this)" /> <br /></th>
				<th width="200" align="center">
						公司名称
					</th>
				<th width="200" align="center">
						项目名称
					</th>
					<th width="150" align="center">
						黏贴单编号
					</th>
					<th width="70" align="center">
						单据类别
					</th>
					<th width="50" align="center">
						部门
					</th>
					<th width="60" align="center">
						责任人
					</th>
					<th width="60" align="center">
						制单人
					</th>
					<th width="80" align="center">
						制单日期
					</th>
					<th width="80" align="center">
						单据状态
					</th>
					<th width="45" align="center">
						附件管理
					</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="pageForm.list">
				<tr id="${cashierBillsID}">
					<td><input type="checkbox" onclick="toChkSon(this); " class="chx"
						id="chkMsgId23" name="chkMsgId23" value="${cashierBillsID}" /></td>
					<td><span id="companyname">${companyname}</span></td>
					<td><span id="projectName">${projectName}</span></td>
					<td><span id="journalNum">${journalNum}</span></td>
					<td><span id="billsType">${billsType}</span></td>
					<td><span id="departmentname">${departmentname}</span></td>
					<td><span id="staffname">${staffname}</span></td>
					<td><span id="staffname">${staffname}</span></td>
					<td><span id="cashierDate">${fn:substring(cashierDate,0, 10)}</span></td>
					<td><span id="statuss">${status=='04'?"待主管审核":
                                status=='05'?"待会计审核":status=='06'?"待出纳审核":status=='07'?"已审核":
                                status=='20'?"税务单据":status=='30'?"未审核作废":status=='11'?"驳回待修改":status=='09'?"待确认收款":status=='40'?"预算收款单":status==null?"":"驳回作废"}</span>
					</td>
					<td><a href="#" onclick="fj('${cashierBillsID}')" class="fj">附件</a>
					<input type="hidden" value="${statusbill}" id="statusbill"/>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
			value="ea/responsible/ea_getResponsibleList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate} ">
		</c:param>
	</c:import>
	<!-- -----------------------------------审核-------------------------------- -->
	<form name="SendForm2" id="SendForm2" method="post">
		<div class="jqmWindow"
			style="display: none; width: 500px; height: 230px; right: 20%; top: 10%;"
			id="jqModelSend2">
			<input type="submit" name="submit2" style="display: none" />
			<div class="contentbannb">
				<div class="drag">
					审核
					<div class="close"></div>
				</div>
			</div>
			<center>
			<table width="100%" id="SearchTable2" cellspacing="20"
				cellpadding="20">
				<tr>
					<td align="right" width='15%'>审核意见：</td>
					<td align="left"><textarea rows="5" cols="40" style="max-width: 270px;max-height: 100px;" 
							name="comments" id="comments" class="ckTextLength"
							maxlength="1000"></textarea></td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="submitResult2"
					value=" 提交 " /> <input type="hidden" name="myaudit.auditorstatus"
					id="auditorstatu" value="" />
			</div>
			</center>
		</div>
	</form>
	<!-- 审核记录 -->
	<form method="post">
		<div class="jqmWindow" style="width: 600px;right: 25%;top:15%"
			id="jqModelCheck">
			<div class="drag">
				审核记录
				<div class="close" id="cloremove"></div>
			</div>
			<table width="596" id="CheckTable"  cellspacing='5px' cellpadding='5px'>
			</table>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
