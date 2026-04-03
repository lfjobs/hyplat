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
        <title>待会计审核管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/accountant_list.js"></script>
        <script type="text/javascript">
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var status="";
        	var hs = "${cashierBillsVO.status}";
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var treenames = '<%=session.getAttribute("organizationName")%>';
			
		document.onkeydown = function(evt){//捕捉回车 
			evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
			if (key == 13) { //判断是否是回车事件。
				if($("input#journalNum").val()==''){
					return false;
				}
                			if($("input#journalNum").val()!=''){
            		        	             $("#SearchForm").attr("action",basePath + "ea/accountant/ea_toSearch.jspa?pageNumber="+ pNumber);
            					document.SearchForm.submit.click();
            			             }
			}
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
			for(var i=0;i<chkInputs.length;i++)
			{				
					if(chkInputs[i].type=="checkbox" && chkInputs[i].id=="chkMsgId23" &&　chkInputs[i].checked==true){
						 str=str+chkInputs[i].value+",";
					}
			}				
			cashierBillsID=str;		
		}
			
        </script>
        <style rel="stylesheet" type="text/css">
        	.table {
    border-collapse: collapse;
    border: 1px solid #000000;
    font-size: 9px;
}

.table th {
    border: 1px solid #000000;
    color: #000000;
}

.table td {
    border: 1px solid #000000;
    color: #000000;
}
        </style>
    </head>
    <body>
        <form  name="CashierBillsform" id="CashierBillsform">
            <input type="submit" name="submit" style="display:none"/>
            <input name="comments" id="comments2" style="display:none" />
			<input  name="cashierBills.cashierBillsID" id="cashierBillsID" style="display:none"/>
			<input  name="cashierBills.status" id="status" style="display:none"/>
			<s:token/>
        </form>
        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        <input type="checkbox" name="chkMsgId" id="chkMsgId" onclick="doCheck(this)" /> <br/>
                    </th>
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
                            <td>
                             <input type="checkbox" onclick="toChkSon(this); " id="chkMsgId23" name="chkMsgId23"  class="chx" value="${cashierBillsID}"/> 
                              <!--   <input type="radio" name="a"  class="JQuerypersonvalue" value="${cashierBillsID}"/> -->
                            </td>
                             <td>
                                <span id="companyname">${companyname}</span>
                            </td>
                            <td><span id="projectName">${projectName}</span></td>
                            <td>
                                <span id="journalNum">${journalNum}</span>
                            </td>
                            <td>
                                <span id="billsType">${billsType}</span>
                            </td>
                            <td>
                                <span id="departmentname">${departmentname}</span>
                            </td>
                            <td>
                                <span id="staffname">${staffname}</span>
                            </td>
                            <td><span id="staffname">${staffname}</span></td>
                              <td>
                                <span id="cashierDate">${fn:substring(cashierDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="status">
	                                <s:if test="status == '00'">草稿</s:if>
	                                <s:elseif test="status == '04'">待主管审核</s:elseif>
	                                <s:elseif test="status == '05'">待会计审核 </s:elseif>
	                                <s:elseif test="status == '06'">待出纳审核</s:elseif>
	                                <s:elseif test="status == '07'">已审核</s:elseif>
	                                <s:elseif test="status == '20'">转税务单据</s:elseif>
	                                <s:elseif test="status == '30'">未审核作废</s:elseif>
	                                <s:else>驳回作废</s:else>
                                </span>
                            </td>
                            <td>
                               <a href="#" onclick="fj('${cashierBillsID}')" class="fj">附件</a>
                               <input type="hidden" value="${statusbill}" id="statusbill"/>
                            </td>
                        </tr>
                  </s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/accountant/ea_getAccountantList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
            </c:param>
        </c:import>
          <!--搜索窗口 -->
          <!-- <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top:15%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
            	<input name="search" type="hidden" value="search" />
	            <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	            </div>
				<table width="396" id="SearchTable">
					<tr>
						<td width="123" align="right">
							单据类别：
						</td>
						<td><select name="cashierBillsVO.billsType" id="btype"></select>
						</td>
					</tr>
					<tr>
						<td align="right">
							黏贴单编号：
						</td>
						<td>
							<input id="journalNum" style="width: 195px"
								name="cashierBillsVO.journalNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							部门：
						</td>
						<td>
							<select name="cashierBillsVO.departmentID" style="width: 200px"
								id="departmentID">
								<option value="">
									请选择
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							责任人：
						</td>
						<td>
							<s:select list="%{#request.stafflist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="cashierBillsVO.staffID" listValue="staffName"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							制单日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="edate" name="edate" onfocus="date(this);"
								style="width: 87px" />
						</td>
					</tr>
					<tr>
						<td align="right">
							往来单位：
						</td>
						<td>
							<input id="ccID" style="width: 195px"
								name="cashierBillsVO.ccompanyname" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位往来关系：
						</td>
						<td>
							<s:select list="%{#request.connectionlist}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.contactConnections" listValue="codeValue"
								theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							往来个人：
						</td>
						<td>
							<input id="cuID" style="width: 195px"
								name="cashierBillsVO.contactUserName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							个人往来关系：
						</td>
						<td>
							<s:select list="%{#request.codeRelationList}" style="width:200px"
								headerKey="" headerValue="请选择" listKey="codeValue"
								name="cashierBillsVO.phone" listValue="codeValue" theme="simple">
							</s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							数据类别：
						</td>
						<td>
							<select style="width: 200px" name="cashierBillsVO.status"
								theme="simple">
								<option value="wait" selected="selected">
									查询待审核数据
								</option>
								<option value="history">
									查询历史数据
								</option>
							</select>
						</td>
					</tr>
					</table>
				<div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
            </div>
            </div>
            </form> -->
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
                    <td align="left"><textarea rows="5" cols="40"   style="max-width: 270px;max-height: 100px;" 
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
				<div class="close"></div>
			</div>
			<table width="596" id="CheckTable"  cellspacing='5px' cellpadding='5px'>
			</table>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
