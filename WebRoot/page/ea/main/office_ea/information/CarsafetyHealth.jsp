<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page import="java.util.ArrayList" %>
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
		<meta http-equiv="Expires" content="0"/>
        <title>车辆安全卫生检查表</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};
         var  search='${search}';
         var  safetyid = '';
         var  token=0;
		</script>     
		<script  src="<%=basePath%>js/ea/office_ea/information/safetyhealth.js"></script>
	</head>
	
	<body>
		<form name="mainForms" id="mainForms" method="post">
		<input type="submit" name="submit" style="display: none" />	
        <div class="main_main" id="main_main">
            <table class="JQueryflexme" >
                 <thead>
                     <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="150" align="center">
                            时间 
                        </th>
                         <th width="150" align="center">
                            部门		
                        </th>
                         <th width="100" align="center">
                            责任人
                        </th>
                         <th width="100" align="center">
                            总计奖励分
                        </th>
                         <th width="100" align="center">
                           总计扣分
                        </th> 
                        <th width="50" align="center">
                           总得分
                        </th>
                        <th width="150" align="center">
                            附件
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                     <c:forEach var='safety' items="${pageForm.list}">
						<tr id="${safety[0] }" >
							<td class="td_bg01">
								<input type="radio"  name="a" class="JQuerypersonvalue" value="${safety[0] }"/>
							</td>
							<td class="td_bg01">
								<span id="adddate">${safety[1]}</span>
							</td>
							<td class="td_bg01">
								<span id="organizationname">${safety[2]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffname">${accountname}</span>
							</td>
							<td class="td_bg01">
								<span id="countrewards">${safety[4]}</span>
							</td>
							<td class="td_bg01">
								<span id="countpenalty">${safety[5]}</span>
							</td>
							<td class="td_bg01">
								<span id="totlescore">${safety[6]}</span>
								<span id="safetykey" style="display:none">${safety[7]}</span>
                                <span id="safetyid" style="display:none">${safety[0]}</span>
                                <span id="companyID" style="display:none">${safety[8]}</span>
                                <span id="organizationID" style="display:none">${safety[9]}</span> 
                                <span id="fujian" style="display:none"></span> 
							</td>
							<td class="td_bg01"> 
                           		<s:if test="netDiskPath==null||netDiskPath==''">无</s:if>
                            	<s:else> <span id="photo"  onclick="lookImage('${netDiskPath}');"><a href="#">查看</a></span></s:else>
                            </td> 
						</tr>
					</c:forEach>
                    </tbody>	
                </table>
          		<c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/safetyHealth/ea_getSafetyHealthList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            	</c:import>
        </div>
       </form>
         
        <!-- 详细信息查看 -->
	 <div class="jqmWindow" style="width: 1200px;left:0%;right: 20%;;top: 0%" id="jqModel">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<table width="99%" height="33" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" style="font-size:14px; font-weight:bold; margin-top: 5px;">公司 资阳胜威 部门 教务处车辆管理组 汽车安全卫生检查表</td>
  </tr>
</table>
<table width="99%" align="center" cellpadding="1" cellspacing="1" class="table" style="margin-bottom:5px;">
  <tr>
    <td width="2%" height="21" rowspan="3" align="center" bgcolor="#E4F1FA">序号</td>
    <td width="5%" rowspan="3" align="center" bgcolor="#E4F1FA">时间</td>
    <td width="4%" rowspan="3" align="center" bgcolor="#E4F1FA">职务</td>
    <td width="4%" rowspan="3" align="center" bgcolor="#E4F1FA">责任人</td>
    <td width="4%" rowspan="3" align="center" bgcolor="#E4F1FA">车牌号</td>
    <td width="4%" colspan="8" align="center" bgcolor="#E4F1FA">卫生/扣分数（分）</td>
    <td width="5%" colspan="6" align="center" bgcolor="#E4F1FA">安全/扣分数（分）</td>
    <td width="4%" rowspan="3" align="center" bgcolor="#E4F1FA">奖励分</td>
    <td width="3%" rowspan="3" align="center" bgcolor="#E4F1FA">扣分</td>
    <td width="3%" rowspan="3" align="center" bgcolor="#E4F1FA">总计得分</td>
    <td width="3%" rowspan="3" align="center" bgcolor="#E4F1FA">备注</td>
  </tr>
  <tr>
    <td colspan="4" align="center" bgcolor="#E4F1FA">车内</td>
    <td colspan="4" align="center" bgcolor="#E4F1FA">车外</td>
    <td width="2%" rowspan="2" align="center" bgcolor="#E4F1FA">发动机</td>
    <td width="3%" rowspan="2" align="center" bgcolor="#E4F1FA">刹车</td>
    <td width="5%" rowspan="2" align="center" bgcolor="#E4F1FA">大灯</td>
    <td width="2%" rowspan="2" align="center" bgcolor="#E4F1FA">应急灯</td>
    <td width="3%" rowspan="2" align="center" bgcolor="#E4F1FA">防雾灯</td>
    <td width="5%" rowspan="2" align="center" bgcolor="#E4F1FA">转弯灯</td>
  </tr>
  <tr>
    <td align="center" bgcolor="#E4F1FA">车箱</td>
    <td align="center" bgcolor="#E4F1FA">后备箱</td>
    <td align="center" bgcolor="#E4F1FA">脚垫</td>
    <td align="center" bgcolor="#E4F1FA">坐垫</td>
    <td align="center" bgcolor="#E4F1FA">车身</td>
    <td align="center" bgcolor="#E4F1FA">玻璃</td>
    <td align="center" bgcolor="#E4F1FA">门窗</td>
    <td align="center" bgcolor="#E4F1FA">发动机</td>
  </tr>
 <%int number = 1; %>
  <tr>
    <td align="center"><%=number%></td>
    <td align="center">${attr[0]}</td>
    <td align="center">&nbsp;</td>
    <td align="center">${attr[1]}</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
    <td align="center">&nbsp;</td>
  </tr>
   <% number++; %>
</table>
<table width="99%" height="33" border="0"  style="text-align:center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" style="font-size:14px; color:#000000; font-weight:bold">注：扣分细则</td>
  </tr>
</table>
<table width="99%" height="33" border="0"  style="text-align:center" cellpadding="0" cellspacing="0">
  <tr>
    <td align="left" style="font-size:12px; line-height:22px ">
    （一）卫生：1、车箱干净无杂乱；2、后备箱完好清洁；3、脚垫无灰尘；4、车身无泥土；5、玻璃窗门无灰；6、门窗干净无损；7、发动机无杂音。评优扣分方法（按月计算）：评优打勾 10个勾奖 0.5分 评劣打半勾 评10个半勾扣0.5分 评差打× ；每个差扣0.5分 部门按工位累加</br>
    （二）安全：1、发动机正常；2、刹车灵活；3、大灯正常；4、应急灯正常；5、防雾灯正常；6、转弯灯正常。评优扣分方法（按月计算）：评优打勾 10个勾奖 0.5分 评劣打半勾 评10个半勾扣0.5分 评差打× ；每个差扣0.5分 部门按工位累加 
    </td>
  </tr>
  <tr>
  	<td align="center"><input type="button" value="返回" class="input-button JQueryreturn" style="cursor:pointer;width:80px;"/></td>
  </tr>
</table>
</form>
</div>
	 <!-- 查询信息 -->
			<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					<tr>
						<td height="40">
							车牌号：
						</td>
						<td>
							<input name="motorcar.carNum" />
						</td>
					</tr>
					<tr>
						<td height="40">
							责任人：
						</td>
						<td >
							<input name="motorcar.carpeople" />
						</td>
					</tr>
				</table>
				<div style="text-align:center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search"/>
				</div>
			</div>
		</form>
</body> 
</html>