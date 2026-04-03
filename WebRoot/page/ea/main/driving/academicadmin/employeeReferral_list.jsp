<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>公司正式员工汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"/>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/driving/academicadmin/employeeReferral_list.js"></script>
        
        <script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
            var basePath = "<%=basePath%>";
            var opertionID = "";
            var staffName="";
            var search='${search}';
            var  pNumber =${pageNumber};
            
        </script>  
    </head>
    <body >
        <div class="main_main">
            <table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							员工工种
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							部门名称
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="80" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="80" align="center">
							国籍
						</th>
						<th width="80" align="center">
							籍贯
						</th>
						<th width="80" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="200" align="center">
							地址
						</th>
						<th width="100" align="center">
							qq
						</th>
						<th width="100" align="center">
							邮箱
						</th>
						<!-- 
						<th width="100" align="center">
							录入时间
						</th>
						<th width="100" align="center">
							政治面貌
						</th>
						<th width="100" align="center">
							文化程度
						</th>
						<th width="100" align="center">
							婚姻状况
						</th>
						<th width="100" align="center">
							健康状况
						</th>
						<th width="100" align="center">
							银行帐号
						</th>
						<th width="100" align="center">
							备注
						</th>
						-->
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="lists">
						<tr id="${lists[0]}" class="${lists[4]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${lists[0]}" />
							</td>
							<td>
								<span id="staffCode">${lists[1]}</span>
							</td>
							<td>
								<span id="recordCode">${lists[2]}</span>
							</td>
							<td>
								<span id="categoryname">${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td>
								<span id="staffName">${lists[4]}</span>
							</td>
							<td>
								<span id="organizationName">${lists[5]}</span>
							</td>
							<td>
								<span id="postName">${lists[6]}</span>
							</td>
							<td>
								<span id="usedNmae">${lists[7]}</span>
							</td>
							<td>
								<span id="sex">${lists[8]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${lists[9]}</span>
							</td>
							<td>
								<span id="nationality">${lists[10]}</span>
							</td>
							<td>
								<span id="nativePlace">${lists[11]}</span>
							</td>
							<td>
								<span id="nation">${lists[12]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${lists[13]}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${lists[25]}</span>
								<span style="display: none" id="staffID">${lists[0]}</span>
								<span style="display: none" id="photo">${lists[27]}</span>
							</td>
							<td>
								<span id="reference">${lists[15]}</span>
							</td>
							 
							<td>
								<span id="staffAddress">${lists[14]}</span>
							</td>
							<td>
								<span id="referenceCode">${lists[16]}</span>
							</td>
							<td>
								<span id="referenceOrganization">${lists[17]}</span>
							</td>
							<!-- 
							<td>
								<span id="verifyTime" class="datas">${lists[18]}</span>
							</td>
							<td>
								<span id="politicsStatus">${lists[19]}</span>
							</td>
							<td>
								<span id="culturalDegree">${lists[20]}</span>
							</td>
							<td>
								<span id="marriage">${lists[21]}</span>
							</td>
							<td>
								<span id="health">${lists[22]}</span>
							</td>
							<td>
								<span id="bankNum">${lists[23]}</span>
							</td>
							<td>
								<span id="staffDesc">${lists[24]}</span>
								<span style="display: none" id="address">${lists[26]}</span>
							</td>
							-->
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/academicadmin/ea_getCompanyListEmployeeReferral.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        <!--搜索窗口 -->
         <!--搜索窗口 -->
         <form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%;" id="jqModelSearch">
           
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" width="400">
                    <tr>
                        <td align="right">
                            人员编号：
                        </td>
                        <td>
                            <input name="cos.staffCode" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            人员姓名：
                        </td>
                        <td>
                            <input name="cos.staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            身份证：
                        </td>
                        <td>
                            <input name="cos.staffIdentityCard" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchStaff" value="查询" /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" />
                </div>
        </div> 
        </form>
    </body>
</html>