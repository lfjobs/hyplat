<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
       <%@page import="hy.ea.bo.Company"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");
        %>
        <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>集团--奖罚单公司汇总</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
               <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
       <script type="text/javascript" src="<%=basePath %>js/ea/company/human/gamjeom_company.js"></script>
       <script>
            var ppageNumber = '${pageNumber}';
            var basePath = '<%=basePath %>';
            var search = '${search}';
            var comID = '${account.companyID}';
            var gamJeomID = "";
             var comIDa = '<%=c.getCompanyID()%>';
		 	var comName = '<%=c.getCompanyName()%>';   
		</script>
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                    	<th width="40" align="center">
                            请选择
                        </th>
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
                             奖励分/扣分  
                        </th>
                        <th width="110" align="center">
                             奖励/扣分日期
                        </th>
                        <th width="110" align="center">
                            奖励/扣分金额
                        </th>
                        <th width="110" align="center">
                            奖励/扣分原因
                        </th>
                        <th width="110" align="center">
                             部门主管审核
                        </th>
                        <th width="110" align="center">
                             人力资源审核部
                        </th>
                        <th width="110" align="center">
                             总经理审核
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
                                <input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
                            </td>
                            <td>
                                <span id="companyName1">${arr[1]}</span>
                            </td>
                            <td>
                               <span id="organizationName">${arr[2]}</span>
                            </td>
                            <td>
                                <span id="voucherNum1">${arr[3]}</span>
                            </td>
                            <td>
                                <span id="partnerName1">${arr[4]}</span>
                            </td>
                            <td>
                                <span id="type1">${arr[6]}</span>
                            </td>
                            <td>
                                <span id="applyDate" class="datas">${fn:substring(arr[7], 0, 10)}</span>
                            </td>
                            <td>
                                <span id="operator1">${arr[8]}</span>
                            </td>
                            <td>
                                <span id="rorpDeductPoint1">${arr[9]}</span>
                            </td>
                            <td>
                                <span id="rorpDate1">${arr[10]}</span>
                            </td>
                            <td>
                                <span id="rorpMyriad1">${arr[11]}</span>
                            </td>
                            <td>
                                <span id="rorpReason1">${arr[12]}</span>
                            </td>
                            <td>
                                <span id="firstAuditor">${arr[13]}</span>
                            </td>
                            <td>
                                <span id="secondAuditor">${arr[14]}</span>
                            </td>
                            <td>
                                <span id="finalAuditor">${arr[15]}</span>
                            </td>
                            <td>
                                <span id="receiptsStatus">${arr[16]}</span>
                            </td>
                             <td >
                          		<span id="look1" style="display:none">${arr[17]}</span>
                            	<span id="wu" style="display:none">无</span>
                            	<span id="look" style="display:none" onclick="lookImage('${arr[17]}');"><a href="#">查看</a></span>
                             	<span id="load" style="display:none"><a href='<%=basePath%>ea/publicreceipts/ea_downFile.jspa?downLoadPath=${arr[17]}'>下载</a></span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </c:forEach>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/gamjeomscompany/ea_getGamJeomsList.jspa?pageNumber=${pageNumber}&search=${search}">
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
	                            单据类别：                      
	                  	</td>
	                  	<td>
	                  		<select name="publicreceipts.type" >
	                  			<option value="">全部</option>
	                  			<option value="奖励单">奖励单</option>
	                  			<option value="扣分单">扣分单</option>
	                  		</select>
	                  	</td>
	                </tr>
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
                           责任人：                      </td>
                  		<td>
                            <input name="publicreceipts.principal" />
                        </td>
                    </tr>
                    <tr>
						<td align="right">
							公司名称：
						</td>
						<td>
							<select id="companyID" name="publicreceipts.companyID">
								<option value="">
									请选择公司
								</option>
							</select>
							
						<br /></td>
					</tr>
					<tr>
						<td align="right">
							部门名称：
						</td>
						<td >
							<select id="orgID" name="publicreceipts.organizationID">
								<option value="">
									请选择公司
								</option>
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
        <div class="contentbannb jqmWindow windowJqm" style="top: 15%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
		  	<div class="contentbannb">
		  	<div class="drag">员工奖罚单
		    <div class="close" ></div>
		    </div>
	    </div>
  
	  <table width="850 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
	    <tr>
	      <td width="80" align="right">公司：</td>
	      <td width="200"><input name="company.companyName" class="input yincang" id="companyName" readonly="readonly" value="${company.companyName}" size="30"/>
	      				  <span class="xianshi" id="companyName1"></span>
	      </td>
	      <td width="110" align="right">部门：</td>
	      <td width="190"><select id="deptID" name="publicreceipts.principalOrganizationID" class="deptID yincang"></select>
	      	  <span class="xianshi" id="organizationName"></span>
	      </td>
	      <td width="70" align="right"><span class="xx">*</span>责任人：</td>
	      <td class="disName"><input name="publicreceipts.principal" class="input put3 yincang" id="partnerName" size="10" style="margin-left:2px;" readonly="readonly"/>
		      <input name="publicreceipts.principalID"  id="principalID" type="hidden"/>
		      <a href="#" class="yincang" onclick="searchCoach();">选择</a>
			  <span class="xianshi" id="partnerName1"></span>
		  </td>
	    </tr>
	    <tr>
	      <td align="right">凭证号：</td>
	      <td>
	      <input class="input yincang" name="publicreceipts.voucherNum" id="voucherNum" size="25" style="margin-left:2px;" readonly="readonly"/>
	      <span id="voucherNum1" class="xianshi"></span>
	      </td>
	      <td align="right"><span class="xx">*</span>单据类别：</td>
		  <td class="disName">
		  	  <select name="publicreceipts.type" id="type" class="put3 yincang">
		  		  <option value=""></option>
				  <option value="奖励单">奖励单</option>
				  <option value="扣分单">扣分单</option>
		  	  </select>
		  	  <span class="xianshi" id="type1"></span>
		  </td>
	      <td align="right">附件：</td>
	      <td><input name="publicreceipts.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
			  <input name="photo" type="file" class="input yincang" size="10" contentEditable="false" id="photo"/>
		      <span id="isNull" style="display:none" class="hideAll">无</span>
		      <span id="isLook" style="display:none" onclick="lookImage(acceName);" class="hideAll"><a href="#">查看</a></span>
		      <span id="isLoad" style="display:none" class="hideAll"><a href='#'>下载</a></span>
	      </td>
	    </tr>
	  </table>
  <table width="850" height="150"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <%--<td width="69" align="right">岗位：</td>
      <td width="80"><input  class="input" id="rorpPostName" size="10" style="margin-left:2px;" name="publicreceiptsChild.rorpPostName" value="自动获取" disabled="disabled"/></td>
      --%>
	  <td width="100" height="34" align="right"><span class="xx">*</span>奖罚分：</td>
      <td width="170" class="disName"><input name="publicreceiptsChild.rorpDeductPoint" class="input put3 Point yincang" id="rorpDeductPoint" size="10" />
      				  <span class="xianshi sty" id="rorpDeductPoint1"></span>
      </td>
      <td  width="100" align="right">制单人：</td>
      <td  width="170"><input class="input yincang" id="operator" size="10" name="publicreceipts.operator" readonly="readonly"/>
      	  <span class="xianshi sty" id="operator1"></span>
      </td>
      <td width="100" align="right"><span class="xx">*</span>奖罚日期：</td>
      <td class="disName"><input name="publicreceiptsChild.rorpDate"  class="input put3 yincang" id="rorpDate" onfocus="date(this);" size="15" />
          <span class="xianshi sty" id="rorpDate1"></span>
      </td>
    </tr>
    <tr>
      <td width="100" height="40" align="right">奖罚金额(大写)</td>
      <td colspan="5">　
      <input name="publicreceiptsChild.rorpMyriad" id="rorpMyriad" class="input yincang" size="60" readonly="readonly" style="border:none;"/>
      <input name="publicreceiptsChild.rorpMoney" id="rorpMoney" size="3" style="display:none;"/>
      <span class="xianshi" id="rorpMyriad1"></span>
      </td>
    </tr>
     <tr>
      <td width="100" height="76" align="right"><span class="xx">*</span>奖罚原因：</td>
      <td colspan="5">
      	<textarea name="publicreceiptsChild.rorpReason" cols="90" rows="4" class="input yincang ckTextLength" maxlength="250" id="rorpReason" style="margin-left:2px;"></textarea>
      	<textarea class="xianshi sty" id="rorpReason1" readonly="readonly" cols="102" rows="4" style="margin-left:2px;"></textarea>
      </td>
    </tr>
  </table>
  <table width="850" height="20" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
     <td align="center">
      <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="返回" />
      </td>
    </tr>
  </table>
</div>
    <s:token></s:token>
    </body>
</html>