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
        <title>员工级别变更单公司汇总</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
           <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
       <script type="text/javascript" src="<%=basePath%>js/ea/human/office/company/staffRankCompany.js"></script>
       
       <script type="text/javascript">
	        var basePath = '<%=basePath%>';
	        var ppageNumber = '${pageNumber}';
	        var psearch = '${search}';
	        var comID = '${account.companyID}';
        </script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="170" align="center">
                            公司
                        </th>
                        <th width="80" align="center">
                            部门
                        </th>
                        <th width="150" align="center">
                            凭证号
                        </th>
                        <th width="110" align="center">
                             责任人
                        </th>
                        <th width="110" align="center">
                             单据类型
                        </th>
                        <th width="110" align="center">
                             申请日期   
                        </th>
                        <th width="110" align="center">
                             制单人
                        </th>
                        <th width="110" align="center">
                             人员编号
                        </th>
                        <th width="110" align="center">
                             升降级别
                        </th>
                        <th width="110" align="center">
                             生效日期
                        </th>
                        <th width="110" align="center">
                             原级别明细
                        </th>
                        <th width="110" align="center">
                             起时间
                        </th>
                        <th width="110" align="center">
                             止时间
                        </th>
                        <th width="110" align="center">
                             变动理由
                        </th>
                        <th width="110" align="center">
                             工作内容
                        </th>
                        <th width="110" align="center">
                             自我评定
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
                    int number = 1; %>
                    <c:forEach var='arr' items="${pageForm.list}">
                        <tr id="${arr[0]}"  >
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
                                <span id="applyDate" class="datas">${fn:substring(arr[6], 0, 10)}</span>
                            </td>
                            <td>
                                <span id="operator">${arr[7]}</span>
                            </td>
                            <td>
                                <span id="childPartnerName1">${arr[8]}</span>
                            </td>
                            <td>
                                <span id="rankShenzhigrade1">${arr[9]}</span>
                            </td>
                            <td>
                                <span id="rankEffectivedate1">${arr[10]}</span>
                            </td>
                            <td>
                                <span id="rankOldlevel">${arr[11]}</span>
                            </td>
                            <td>
                                <span id="rankStartdate1">${arr[12]}</span>
                            </td>
                            <td>
                                <span id="rankEnddate1">${arr[13]}</span>
                            </td>
                            <td>
                                <span id="rankChangeReason1">${arr[14]}</span>
                            </td>
                            <td>
                                <span id="rankContent1">${arr[15]}</span>
                            </td>
                            <td>
                                <span id="rankExamine1">${arr[16]}</span>
                            </td>
                            <td>
                                <span id="firstAuditor">${arr[17]}</span>
                            </td>
                            <td>
                                <span id="secondAuditor">${arr[18]}</span>
                            </td>
                            <td>
                                <span id="receiptsStatus">${arr[19]}</span>
                            </td>
                            <td >
                            	<span id="look1" style="display:none">${arr[20]}</span>
                            	<span id="wu" style="display:none">无</span>
                            	<span id="look" style="display:none" onclick="lookImage('${arr[20]}');"><a href="#">查看</a></span>
                             	<span id="load" style="display:none"><a href='<%=basePath%>ea/publicreceipts/ea_downFile.jspa?downLoadPath=${arr[20]}'>下载</a></span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </c:forEach>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/staffrankcompany/ea_getRankCompany.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>

       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
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
                            部门名称：                        
                        </td>
                        <td>
                        <select id="principalOrganizationID" name="publicreceipts.principalOrganizationID" >
                        <option value="">全部</option>
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
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
    </body>
</html>