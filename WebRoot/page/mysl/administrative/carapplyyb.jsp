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
        <title>用车申请已办</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/administrative/CarApplyyb.js"></script>
        <script>
            var basePath = "<%=basePath%>";
            var carid = "";
            var search="${search}";
            var pageNumber = ${pageNumber};
            var token=0;
            var notoken= 0;
            var type="${param.type}";
</script>
	</head>
	<body>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="50" align="center">
                            序号
                        </th>
                        <th width="120" align="center">
                          车牌号
                        </th>
                        <th width="120" align="center">
                           项目名称
                        </th>
                        <th width="120" align="center">
                           目的地
                        </th>
                        <th width="120" align="center">
                           用车人
                        </th>
                         <th width="120" align="center">
                           用车人备注
                        </th>
                        <th width="120" align="center">
                           驾驶员
                        </th>
                        <th width="120" align="center">
                           起点里程数
                        </th>
                        <th width="120" align="center">
                           终点里程数
                        </th>
                        <th width="120" align="center">
                            本次出车里程数
                        </th>
                        <th width="120" align="center">
                           过路费
                        </th>
                        <th width="120" align="center">
                           停车费
                        </th>
                        <th width="120" align="center">
                           驾驶员备注
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator var="arr" value="pageForm.list">
                        <tr id="${arr[0]}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${arr[0]}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="carcode">${arr[10]}</span>
                            </td>
                            <td>
                                <span id="carusereason">${arr[13]}</span>
                            </td>
                            <td>
                                <span id="destination">${arr[14]}</span>
                            </td>
                            <td>
                                <span id="carusername">${arr[15]}</span>
                            </td>
                            <td>
                                <span id="remarks">${arr[23]}</span>
                            </td>
                            <td>
                                <span id="cardriver">${arr[17]}</span>
                                <span id="cardriverid" style="display:none">${arr[18]}</span>
                                <span id="cardriverorgid" style="display:none">${arr[19]}</span>
                                <span id="cardriverorgname" style="display:none">${arr[20]}</span>
                                <span id="cardrivercompanyid" style="display:none">${arr[21]}</span>
                                <span id="cardrivercompanyname" style="display:none">${arr[22]}</span>
                                <span id="aid" style="display:none">${arr[0]}</span>
                                <span id="companyname" style="display:none">${arr[1]}</span>
                                <span id="companyid" style="display:none">${arr[2]}</span>
                            </td>
                            <td>
                                <span id="starnums">${arr[26]}</span>
                            </td>
                            <td>
                                <span id="endnum">${arr[27]}</span>
                            </td>
                            <td>
                                <span id="countnum">${arr[28]}</span>
                            </td>
                            <td>
                                <span id="roadtoll">${arr[29]}</span>
                            </td>
                            <td>
                                <span id="parkingfees">${arr[30]}</span>
                            </td>
                            <td>
                                <span id="driverremarks">${arr[31]}</span>
                                <span id="key" style="display:none">${arr[24]}</span>
                                <span id="lid" style="display:none">${arr[25]}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../ea/page_navigator.jsp">
                <c:param name="actionPath" value="ea/carapply/ea_getDtMyusecarapplyListysh.jspa?type=${param.type}&pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
         <!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							车牌号：
						</td>
						<td width="261">
							<input name="usecarapply.carcode" class="ckTextLength" maxlength="40" id="carcode"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							用车人：
						</td>
						<td>
						    <input name="usecarapply.carusername" class="ckTextLength" maxlength="40" id="carusername"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<div class="jqmWindow jqmWindowcss" style="left: 35%;top:10%;width:900px;" id="jqModel">
			<form name="carapplyForm" id="carapplyForm" method="post" >
			<input type="submit" name="submit" style="display:none" />
			<div class="content">
				<div class="contentbannb">
					<div class="drag">
						行车记录
						<div class="close"></div>
					</div>
				</div>
				<table width="900" height="46" border="0" align="center"
					cellpadding="0" cellspacing="0" id="carapplytable"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td align="right" width="80">车牌号：</td>
						<td align="left" width="220"><span id="carcode"></span></td>
						<td align="right" width="80">项目名称：</td>
						<td align="left" width="220"><span id="carusereason"></span></td>
						<td align="right" width="80">用车人：</td>
						<td align="left" width="220"><span id="carusername"></span></td>	
					</tr>
					<tr>
						<td align="right" width="80">目的地：</td>
						<td align="left" width="220"><span id="destination"></span></td>
						<td align="right" width="80">用车人备注：</td>
						<td align="left" width="440" colspan="3"><span id="remarks"></span></td>
					</tr>
				</table>
				<table width="900" height="244" border="0" align="center"
					cellpadding="0" cellspacing="0" class="table"
					style="background:#FFFFFF;" id="carusetable">
					<tr>
					    <td align="right" width="80">驾驶员：</span></td>
					    <td align="left" colspan="5" width="660"><span id="cardriver"></span></td>
					</tr>
					<tr>
						<td align="right" width="80"><span style="color:red;">*</span>起点里程数：</td>
						<td align="left" width="220"><input name="usecarlog.starnum" id="starnum" size="10" readonly="readonly"/></td>
						<td align="right" width="80"><span style="color:red;">*</span>终点里程数：</td>
						<td align="left" width="220"><input name="usecarlog.endnum" id="endnum" onkeydown="Reduction()" onkeyup="Reduction()" size="10" class="put3 ckTextLength positiveNum" maxlength="40"/></td>
						<td align="right" width="80">出车公里数：</td>
						<td align="left" width="220"><input name="usecarlog.countnum" id="countnum" readonly="readonly"  size="10" /></td>
					</tr>
					<tr>
						<td align="right" width="80">过路费：</td>
						<td align="left" width="220"><input name="usecarlog.roadtoll" id="roadtoll"  size="10" class="ckTextLength isNaN" maxlength="6"/>元</td>
						<td align="right" width="80">停车费：</td>
						<td align="left" colspan="3" width="440"><input name="usecarlog.parkingfees" id="parkingfees"  size="10" class="ckTextLength isNaN" maxlength="6"/>元</td>
					</tr>
					<tr>
						<td align="right" width="80">驾驶员备注：</td>
						<td colspan="5" width="810">
						<textarea cols="96" rows="4" class="ckTextLength" maxlength="250" name="usecarlog.driverremarks" id="driverremarks" style="margin-left:2px;"></textarea>
						<input type="hidden" name="usecarlog.lid" id="lid" />
					    <input type="hidden" name="usecarlog.key" id="key" />
					    <input type="hidden" name="usecarlog.companyname" id="companyname" />
					    <input type="hidden" name="usecarlog.companyid" id="companyid" />
					    <input type="hidden" name="usecarlog.carapplyid" id="aid" />
					    <input type="hidden" name="usecarlog.userremarks" id="remarks" />
					    <input type="hidden" name="usecarlog.usercarname" id="carusername" />
					    <input type="hidden" name="usecarlog.carcode" id="carcode" />
					    <input type="hidden" name="usecarlog.carusereason" id="carusereason" />
					    <input type="hidden" name="usecarlog.destination" id="destination" />
					    <input type="hidden" name="usecarlog.cardriver" id="cardriver" />
					    <input type="hidden" name="usecarlog.cardriverid" id="cardriverid" />
					    <input type="hidden" name="usecarlog.cardriverorgid" id="cardriverorgid" />
					    <input type="hidden" name="usecarlog.cardriverorgname" id="cardriverorgname" />
					    <input type="hidden" name="usecarlog.cardrivercompanyid" id="cardrivercompanyid" />
					    <input type="hidden" name="usecarlog.cardrivercompanyname" id="cardrivercompanyname" />
						</td>
					</tr>
				</table>
			</div>
			<s:token></s:token>
			  <div align="center" id="button">
			        <table>
			        <tr>
			        <td><input type="button" class="input-button" style="cursor:pointer;width:80px;"  onclick="tosave();" value=" 保存" /></td>
			        <td><input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /></td>
			        </tr>
			        </table>	
               </div>
		</form>
	</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>