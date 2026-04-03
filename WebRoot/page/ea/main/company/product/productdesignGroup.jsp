<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
        <title>产品设计--集团汇总</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript"  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath %>js/ea/product/productdesignGroup.js"></script>   
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
            <script>
   		   var  basePath='<%=basePath%>';           
		   var  pNumber ='${pageNumber}';  
		   var  search='${search}';
		   var sdate="${sdate}";
		   var edate="${edate}";
		   var token= '';
		   var cc ="${cc}";
		</script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                   <tr class="tablewith">
                         <th width="30" align="center">
							序号
						</th>
						<th width="200" align="center">
							公司名称
						</th>
						<th width="100" align="center">
							部门
						</th>
						<th width="70" align="center">
							责任人
						</th>
						<th width="70" align="center">
							包装日期
						</th>
						<th width="70" align="center">
							产品编号
						</th>
						<th width="70" align="center">
							产品名称
						</th>
						<th width="90" align="center">
							产品单位
						</th>
						<th width="90" align="center">
							产品规格
						</th>
						<th width="50" align="center">
							产品数量
						</th>
						<th width="50" align="center">
							产品重量
						</th>
						<th width="50" align="center">
							产品单价
						</th>
						<th width="50" align="center">
							产品金额
						</th>
						<th width="50" align="center">
							说明书
						</th>
						<th width="50" align="center">
							产品宣传
						</th>
						<th width="50" align="center">
							公司文件
						</th>
					</tr>
                </thead>
                <tbody>
                   <% int number = 1; %>
					<c:forEach var='arr' items="${pageForm.list}">
						<tr id="${arr[0] }">
							<td>
								<span id="number"><%=number%></span>
							</td>
							<td>
								<span id="companyname">${arr[1]}</span>
							</td>
							<td>
								<span id="journalNum">${arr[2]}</span>
							</td>
							<td>
								<span id="staffID">${arr[3]}</span>
							</td>
							<td>
								<span id="PackagingDate">${fn:substring(arr[4], 0, 10)}</span>
							</td>
							<td>
								<span id="goodsCoding">${arr[5]}</span>
							</td>
							<td>
								<span id="goodsName">${arr[6]}</span>
							</td>
							<td>
								<span id="variableID">${arr[7]}</span>
							</td>
							<td>
								<span id="acquiesceStandard">${arr[8]}</span>
							</td>
							<td>
								<span id="quantity">${arr[9]}</span>
							</td>
							<td>
								<span id="weight">${arr[10]}</span>
							</td>
							<td>
								<span id="price">${arr[11]}</span>
							</td>
							<td>
								<span id="money">${arr[12]}</span>
							</td>
							<td>
								<c:if test="${arr[13]!=null}">
									<a href="#" class="accessoriesUrl1">查看附件</a>
									<input type="hidden" id="manualUrl" value="${arr[13]}" />
									<span id="manualUrl" style="display: none">${arr[13]}</span>
								</c:if>
								<c:if test="${arr[13]==null}">无</c:if>
							</td>
							<td>
								<c:if test="${arr[14]!=null}">
									<a href="#" class="accessoriesUrl2">查看附件</a>
									<input type="hidden" id="propagandaUrl" value="${arr[14]}" />
									<span id="propagandaUrl" style="display: none">${arr[14]}</span>
								</c:if>
								<c:if test="${arr[14]==null}">无</c:if>
							</td>
							<td>
								<c:if test="${arr[15]!=null}">
									<a href="#" class="accessoriesUrl3">查看附件</a>
									<input type="hidden" id="fileUrl" value="${arr[15]}" />
									<span id="fileUrl" style="display: none">${arr[15]}</span>
								</c:if>
								<c:if test="${arr[15]==null}">无</c:if>
							</td>
						</tr>
						<% number++; %>
					</c:forEach>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/productdesigngroup/ea_getListProductdesign.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&cc=jt"></c:param>
			</c:import>
        </div>
     
        <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post"  name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                  <tr>
                       <td align="right">
							公司名称：
						</td>
						<td>
							<select id="deptID" name="productDesign.companyID">
								<option value="" selected="selected">
									所有公司
								</option>
							</select>
						</td>
                    </tr>
                      <tr>
                       <td align="right">
							部门名称：
						</td>
						<td>
							<select id="orgID" name="productDesign.organizationID">
								<option value="">
									请选择公司
								</option>
							</select>
						</td>
                    </tr>
                  <tr>
                        <td>
                          物品名称：
                        </td>
                        <td>         
                           <input name="productDesign.goodsName" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                        包装日期：
                        </td>
                        <td>
                           <input id="sdate" name="sdate" onfocus="date(this);"
								style="width: 85px" />
							至
							<input id="edate" name="edate" onfocus="date(this)"
								style="width: 85px" />

                        </td>
                    </tr>
                </table>
                <div style="text-align:center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                    <input type="submit" name="submit" style="display:none"/>
                </div>
            </form>
        </div>
           <s:token></s:token>
    </body>
</html>