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
<title>测试成绩分析</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery-1.3.1.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>

<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;
   var drivingprincipalid='${dtDrivingPrincipal.drivingprincipalid}'
   var docstatus='${docstatus}';//单据状态  		
   var sdate='${sdate}';
   var edate='${edate}';
   var search='${search}';
   var other='${other}';//区别导航页面
   var coachid="";
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/driving/driving_list_fenxi_test.js"></script></head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
			 	    <th width="30" align="center"  >序号</th>
			 	    <th width="60" align="center"  >科目类型</th>
			 	    <th width="120" align="center"  >教练员</th>
		            <th width="80" align="center" >起时间</th>
		            <th width="80" align="center" >止时间</th>
		            <th width="60" align="center" >参考人数</th>
		            <th width="60" align="center" >合格人数</th>
		            <th width="120" align="center" >不合格人数</th>
		            <th width="80" align="center" >缺考人数</th>
		            <th width="80" align="center" >误报人数</th>
		            <th width="80" align="center" >合格率</th>
	      		</tr>
	    </thead>
		<tbody>
			<% int i=1; %>
          <s:iterator value="pageForm.list" var="ls">
	          <tr class="td_bg01 saveAjax" class="trclass">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" />
	            	</td>
	            	<td class="td_bg01">
		                <span> <%=i%></span></td>
		             <td class="td_bg01">
		                <span  >${ls[10]}</span>
					</td>
		            <td class="td_bg01">
		                <span  >${ls[1]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[2]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[3]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[4]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[5]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[6]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[7]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[8]}</span>
					</td>
					<td class="td_bg01">
		                <span  >${ls[9]}</span>
		                <span  id="coachid" style="display:none">${ls[0]}</span>
					</td>
	          </tr>
	          <%  i++; %>
          </s:iterator>
    	</tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/driving/ea_getAnalysisListOfTest.jspa?pageNumber=${pageNumber}&docstatus=${docstatus}&sdate=${sdate}&edate=${edate }&search=${search}&other=${other}"></c:param>
</c:import>
</div>
</form>
<div id="jqModelSearchss">
				<form name="SearchForm" id="SearchForm" method="post">
					<input type="submit" name="submit" style="display: none" />
					
                            教练名称：
                            <input name="dtDrivingPrincipalType.coachname" size="10"/>
                       
                            起始时间：
                       
                            <input name="sdate"  class="put3"  onfocus="date(this);" size="10"/>
                      
                            结束时间：
                       
                            <input name="edate"  class="put3" onfocus="date(this);" size="10"/>
                      
                         <input name="docstatus"  type="hidden" value="${docstatus}"/>
                            <input type="button" class="input" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                       
				</form>
			</div>
<div class="jqmWindow" style="width: 450px; right: 40%; top: 15%;"
				id="jqModelPrint">
				<form name="PrintForm" id="PrintForm" method="post">
					<input type="submit" name="submit" style="display: none" />
					<div class="drag">
						(个人)教练业绩明细表打印
						<div class="close">
						</div>
					</div>
					<table id="cataffSearchTableOfPrint" width="450"  >
                    <tr >
                        <td  align="right">
                            教练名称：
                        </td>
                        <td>
                           <input name="dtDrivingPrincipalType.coachname" id="coachname" size="15" readonly="readonly" class="put3"/> <span id="xzjl" style="cursor: pointer;color: blue;">选择</span>
                           <input type="hidden" name="dtDrivingPrincipalType.coachid" id="coachid" size="10" />
                        </td>
                    </tr>
                    <tr >
                        <td  align="right" >
                            日期时间：
                        </td>
                          <td >
                            <input name="sdate"  class="put3"  onfocus="date(this);" size="10"/>&nbsp;至&nbsp;<input name="edate"  class="put3" onfocus="date(this);" size="10"/>
                        </td>
                    </tr>
                    <tr  align="center">
                        <td  colspan="2"  align="center" >
                         <input name="docstatus"  type="hidden" value="${docstatus}"/>
                            <input type="button" class="input" id="tosearchOfPrint" value=" 打印 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
				</form>
</div>
<div class="jqmWindow" style="width: 450px; right: 40%; top: 15%;"
				id="jqModelPrintThree">
				<form name="PrintFormThree" id="PrintFormThree" method="post">
					<input type="submit" name="submit" style="display: none" />
					<div class="drag">
						(月度)教练培训质量排行榜打印
						<div class="close">
						</div>
					</div>
					<table id="cataffSearchTableOfPrint" width="450"  >
                    <tr >
                        <td  align="right" >
                            日期时间：
                        </td>
                        <td>
                            <input name="sdate"  class="put3"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'});" size="10"/>&nbsp;至&nbsp;<input name="edate"  class="put3" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" size="10"/>
                        </td>
                    </tr>
                    <tr  align="center">
                        <td  colspan="2"  align="center" >
                        <input name="docstatus"  type="hidden" value="${docstatus}"/>
                            <input type="button" class="input" id="tosearchOfPrintThree" value=" 打印 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
				</form>
</div>
<div class="jqmWindow" style="width: 450px; right: 40%; top: 15%;"
				id="jqModelPrintFour">
				<form name="PrintFormFour" id="PrintFormFour" method="post">
					<input type="submit" name="submit" style="display: none" />
					<div class="drag">
						(年度)教练培训质量排行榜打印
						<div class="close">
						</div>
					</div>
					<table id="cataffSearchTableOfPrint" width="450"  >
                    <tr >
                        <td  align="right" >
                            日期时间：
                        </td>
                        <td>
                            <input name="sdate"  class="put3"  onfocus="WdatePicker({dateFmt:'yyyy-01-01'});" size="10"/>&nbsp;至&nbsp;<input name="edate"  class="put3" onfocus="WdatePicker({dateFmt:'yyyy-12-31'})" size="10"/>
                        </td>
                    </tr>
                    <tr  align="center">
                        <td  colspan="2"  align="center" >
                        <input name="docstatus"  type="hidden" value="${docstatus}"/>
                            <input type="button" class="input" id="tosearchOfPrintFour" value=" 打印 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
				</form>
</div>
<div class="jqmWindow" style="width: 450px; right: 40%; top: 15%;"
				id="jqModelPrintTwo">
				<form name="PrintFormTwo" id="PrintFormTwo" method="post">
					<input type="submit" name="submit" style="display: none" />
					<div class="drag">
						(年度)各科目合格率统计打印
						<div class="close">
						</div>
					</div>
					<table id="cataffSearchTableOfPrint" width="450"  >
                    <tr >
                        <td  align="right" >
                            日期时间：
                        </td>
                        <td>
                            <input name="sdate"  class="put3"  onfocus="WdatePicker({dateFmt:'yyyy-01-01'});" size="10"/>&nbsp;至&nbsp;<input name="edate"  class="put3" onfocus="WdatePicker({dateFmt:'yyyy-12-31'})" size="10"/>
                        </td>
                    </tr>
                    <tr  align="center">
                        <td  colspan="2"  align="center" >
                            <input type="button" class="input" id="tosearchOfPrintTwo" value=" 打印 " /><input name="search" type="hidden" value="search" />
                        </td>
                    </tr>
                </table>
				</form>
</div>
<%-- 选择车辆信息  --%>
<div id="bankJqm" class="jqmWindow"
			style="width: 90%; height: 300px; absolute; display: none; left: 5%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="260px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
</div>			
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
