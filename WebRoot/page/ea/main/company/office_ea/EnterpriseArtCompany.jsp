<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
		<title>企业文化艺术作品管理--公司汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"> </script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
		<script type="text/javascript" src="<%=basePath%>js/ea/company/office_ea/EnterpriseArtCompany.js"> </script>
		<script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
		</script>


	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="20" align="center">
							序号
						</th>
						<th width="100" align="center">
							作者
						</th>
						<th width="100" align="center">
							作品名称
						</th>
						<th width="200" align="center">
							作品描述
						</th>
						<th width="100" align="center">
							作品类别
						</th>
						<th width="200" align="center">
							鉴赏评论
						</th>
						<th width="100" align="center">
							备注
						</th>
						<th width="100" align="center">
							作品文件
						</th>
					</tr>
				</thead>
				<tbody>
					<s:iterator value="pageForm.list" status="number">
						<tr>
							<td>
								<s:property value="%{#number.index+1}" />
							</td>
							<td>
								<span id="enPerson">${enPerson}</span>
							</td>
							<td>
								<span id="enName">${enName}</span>
							</td>
							<td>
								<span id="enSubject">${enSubject}</span>
							</td> 
							<td>
								<span style="display:none" id="enType">${enType}</span><s:if test="enType=='00'">国内</s:if><s:if test="enType=='01'">国际</s:if>
							</td>
							<td>
								<span id="enDiscuss">${enDiscuss}</span>
							</td>
							<td>
								<span id="mark">${mark}</span>
							</td> 
							<td> 
								<span style="display:none" id="artFilePath">${artFilePath}</span>
								<s:if test="artFilePath==null||artFilePath==''">无</s:if>
                            	<s:else>
                            		<a href="#" onclick="lookImage('${artFilePath}')">查看</a>
                            	</s:else>
							</td>
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/enterpriseartgroup/ea_getEnterpriseArtList.jspa?pageNumber=${pageNumber}&search=${search}">
				</c:param>
			</c:import>
		</div>
		 <!--搜索窗口 -->
        <div class="jqmWindow " style="width:300px;right: 45%; top:10%" id="jqModelSearch">
            <form class="postSearchForm"  id="postSearchForm"  method="post" name="postSearchForm">
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           作品名称：
                        </td>
                        <td>         
                           <input name="enterpriseArt.enName" />  
                        </td>
                    </tr>
					<tr>
                        <td>
                          作品描述：
                        </td>
                        <td>
                           <input name="enterpriseArt.enSubject" />
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