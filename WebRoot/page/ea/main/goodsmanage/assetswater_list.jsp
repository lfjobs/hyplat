<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>资产流水报表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/goodsmanage/assetswater_list.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
<script type="text/javascript">
	var treeID = '<%=session.getAttribute("organizationID")%>';
	var treeNames="<%=session.getAttribute("organizationName")%>";
	var token = 0 ;
	var basePath = "<%=basePath%>";
	var goodsID = '';
	var ppageNumber =${pageNumber};
	var  search='${search}';  
	var sdate='${sdate}';
	var edate='${edate}';
	
	document.onkeydown = function(evt){//捕捉回车 
		evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
		if (key == 13) { //判断是否是回车事件。
			if($("input#journalNum").val()==''){
				return false;
			}
   			if($("input#journalNum").val()!=''){
       			$("#SearchForm").attr("action", basePath+"ea/goodsmanage/ea_toSearchAssetsWater.jspa?pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value"));
      		 	document.SearchForm.submit.click();
      		 	$("#tosearch").attr("disabled","disabled");
			}
		}
	}
	
</script> 
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							序号
						</th>
						<th width="50" align="center">
							部门
						</th>
						<th width="50" align="center">
							责任人
						</th>
						<th width="60" align="center">
							款源时间
						</th>
						<th width="100" align="center">
							入账时间
						</th>
						<th width="70" align="center">
							费用类别
						</th>
						<th width="100" align="center">
							单据类别
						</th>
						<th width="150" align="center">
							黏贴单编号
						</th>
						<th width="80" align="center">
							物品编号
						</th>
						<th width="80" align="center">
							物品名称
						</th>
						<th width="30" align="center">
							单位
						</th>
						<th width="50" align="center">
							出库
						</th>
						<th width="50" align="center">
							入库
						</th>
						<th width="50" align="center">
							库存
						</th>
						<th width="50" align="center">
							单价
						</th>
						<th width="50" align="center">
							借方金额
						</th>
						<th width="50" align="center">
							贷方金额
						</th>
						<th width="50" align="center">
							库存余额
						</th>
						<th width="100" align="center">
							审核情况
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="objs" items="${pageForm.list}" varStatus="idx">
						<tr id="${objs[0]}">
							<td >
								<span>${idx.index+1}</span>
							</td>
							<c:forEach items="${objs}" var="obj" end="17" >
							<td >
								<span>${obj}</span>
							</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/goodsmanage/ea_getAssetsWaterList.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}">
				</c:param>
			</c:import>
		</div>
	
		<!--搜索窗口 -->
        <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top:10%;" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
	            <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	            </div>
                <table width="396" id="SearchTable">
					<tr>
                        <td width="100" align="right">单据类别：</td>
						<td >
						<s:select list="%{#request.billTypes}" style="width:200px" headerKey="" headerValue="全部" listKey="codeValue" listValue="codeValue"  name="goodsCashierBillsVO.billsType" theme="simple"></s:select>
						</td>
                   </tr>
                    <tr>
                        <td align="right">黏贴单编号：</td>
                        <td><input id="journalNum"  style="width:195px"  name="goodsCashierBillsVO.journalNum" /></td>
                    </tr>
                    <tr><td align="right">责任人：</td>
                        <td>
                         <s:select list="%{#request.stafflist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="staffID" name="goodsCashierBillsVO.staffID"  listValue="staffName"   theme="simple" >
                         </s:select>
                       </td>
                    </tr>
                    <tr><td align="right">费用类别：</td>
                        <td>
                         <s:select list="%{#request.costTypeList}" style="width:200px" headerKey="" headerValue="请选择" listKey="codeValue" listValue="codeValue" id="costType" name="goodsCashierBillsVO.costType" theme="simple"></s:select>
                       </td>
                    </tr>
                  <tr>
                   <td align="right">
                            部门名称：                        </td>
                      <td >
                        <select id="departmentID" name="goodsCashierBillsVO.departmentID" style="width:200px"><option value="">请选择</option></select>
                    </td>
                  </tr>
                   <tr>
                        <td align="right" >款源时间：</td>
                        <td style="width: 200px"><input id="sdate"   name="sdate" onfocus="date(this);" style="width: 85px"/>至<input id="edate"    name="edate" onfocus="date(this);" style="width: 85px"/></td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </div>
            </form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>