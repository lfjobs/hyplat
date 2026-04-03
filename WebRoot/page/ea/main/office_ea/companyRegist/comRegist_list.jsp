<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>企业管理列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/companyRegist/comRegist_list.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link href="<%=basePath%>css/ea/register.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
			var cashierBillsID="";
			var search="${search}";
			var basePath="<%=basePath%>";
			var pNumber=${pageNumber};
			var token = 0;
			var sdate="${sdate}";
			var edate="${edate}";
		</script>
		
		<script type="text/javascript">
			var pass;
			document.onkeydown = function(evt){//捕捉回车   
				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
				var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
				if (key == 13) { //判断是否是回车事件。
					save();
					$("#jqModelSearch").jqmHide();
				}
			}
			function save()
			{
				pass = '1';
				$("form :input").trigger("blur");
				$(".validate").trigger("blur");
				if($("form .error").length)
				{ 
					return;
				}     
				$('#Loginform').attr("target","hidden").attr('action', basePath+"custregister.jspa");
				document.Loginform.submit.click();
				$("#jqModelSearch").jqmHide();
				token=2;
			}
			function close()
			{
				$("#jqModelSearch").jqmHide();
				window.location.reload();
			}
			setTimeout(close,300000);
		</script>
		<style type="text/css">
		.yname {
		height: 14px;
		}
		</style>
	</head>
	<body>
		<form name="CashierBillsform" id="CashierBillsform">
			<input type="submit" name="submit" style="display: none" />
			<input name="cashierBills.cashierBillsID" id="cashierBillsID"
				style="display: none" />
			<s:token />
		</form>
		<div style="z-index: 90">
			<table class="flexme11">
				<thead>
					<tr>
						<th align="center" width="30">
							序号
						</th>
						<th align="center" width="200">
							父公司名称
						</th>
						<th align="center" width="200">
							组织机构名
						</th>
						<th align="center" width="200">
							公司名称
						</th>
						<th align="center" width="250">
							公司地址
						</th>
						<th align="center" width="80">
							负责人
						</th>
						<th align="center" width="100">
							公司电话
						</th>
						<th align="center" width="180">
							邮箱
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var='arr' items="${pageForm.list}" varStatus="index">
						<tr>
							<td>
								<span>${index.index+1}</span>
							</td>
							<td>
								<span id="pcompanyname">${arr[0]}</span>
							</td>
							<td>
								<span id="companyidentifier">${arr[1]}</span>
							</td>
							<td>
								<span id="companyname">${arr[2]}</span>
							</td>
							<td>
								<span id="companyaddress">${arr[3]}</span>
							</td>
							<td>
								<span id="companyphone">${arr[4]}</span>
							</td>
							<td>
								<span id="companymanager">${arr[5]}</span>
							</td>
							<td>
								<span id="companyemail">${arr[6]}</span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/comregist/ea_getCompanyRegistList.jspa?pageNumber=${pageNumber}">
			</c:param>
		</c:import>
		
		<div class="jqmWindow" style="width: 400px;right: 50%;;top: 20%" id="jqModelSearch1">
          <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询企业资料
                    <div class="close">
                    </div>
            </div>
             <table width="396" id="cataffSearchTable">
              <tr>
                        <td width="123" align="right">
                          组织机构名：       </td>
						<td width="261">
							<input name="company.companyIdentifier" />
                        </td>
              </tr>
              <tr>
                        <td width="123" align="right">
                           公司名称：        </td>
						<td width="261">
							<input name="company.companyName" />
                        </td>
              </tr>
             </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
