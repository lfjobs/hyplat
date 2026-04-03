<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>绿化管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/afforest.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script  type="text/javascript">
   var select = '01';
   var afforestID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="150" align="center" >绿化类别</th>
            <th width="150" align="center" >绿化面积</th>
            <th width="150" align="center" >绿化数量</th>
            <th width="150" align="center" >绿化地点</th>
            <th width="150" align="center" >绿化负责人</th>
			<th width="150" align="center" >绿化日期</th>
           
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${afforestID}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${afforestID}"/>
            </td>
            <td class="td_bg01">
                <span id="classes" class="datas">${classes}</span>
				</td>
            <td class="td_bg01">
               <span id="area" class="datas">${area}</span>
				</td>
			 <td class="td_bg01">
             <span id="amount">${amount}</span>
           			</td>
            <td class="td_bg01">
             <span id="place">${place}</span>
            </td>
            <td class="td_bg01">
               <span id="principal" class="datas">${principal}</span>
				</td>
            <td class="td_bg01">
             <span id="afforestDate" class="datas">${afforestDate}</span>
					        <span id="afforestKey" style="display:none">${afforestKey}</span>    <input type="hidden" name="afforest.afforestKey" value="${afforestKey}"/>
					        <span id="afforestID" style="display:none">${afforestID}</span>   <input type="hidden" name="afforest.afforestID" value="${afforestID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/afforest/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 400px;right: 25%;" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                           绿化类别：
                        </td>
                        <td>
                           <input   name="afforest.classes" />
                        </td>
                    </tr>
					<tr>
                        <td>
                           绿化负责人：
                        </td>
                        <td>
                           <input   name="afforest.principal" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag">绿化管理
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="550" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="550" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100" height="37"  align="right">绿化类别：</td>
			        <td width="148" ><input name="afforest.classes" type="text" id="classes" size="20"/></td>
			        <td width="90"  align="right">绿化面积：</td>
			        <td width="212" ><input name="afforest.area" type="text" id="area" size="20"/></td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">绿化数量：</td>
			        <td ><input  id="amount" type="text"  class="input"  name="afforest.amount" size="20"/></td>
			        <td align="right">绿化地点：</td>
			        <td ><input id="place"   type="text"  class="input" name="afforest.place" size="20"/></td>
			        </tr>
			      <tr>
			        <td align="right">绿化负责人：</td>
			        <td><input  id="principal" type="text" name="afforest.principal" class="input"  size="20"/></td>
			      
			        <td  align="right">绿化日期：</td>
			        <td colspan="3"><input  name="afforest.afforestDate" id="afforestDate" onfocus="date(this);" />
								<input type="hidden" name="afforest.afforestKey" id="afforestKey" />
	               <input type="hidden" name="afforest.afforestID" id="afforestID" />			       </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value=" 取消 " />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
