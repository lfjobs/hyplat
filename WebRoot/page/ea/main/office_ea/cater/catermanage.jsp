<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Company c = (Company) session.getAttribute("currentcompany");
    String sccid = request.getParameter("sccid");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>餐桌管理</title>
    <!--tree-->
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />

    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />

    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>


    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/Cater/CaterList.js"  type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
    <link rel="stylesheet" href="<%=basePath%>/css/css.css" />

    <style type="text/css">

        .xx{
            color:#FF0000;
            margin-right:2px;}
        a:hover {
            font-weight:bold;
            font-size:12px;
        }

        .text_tree{
            width:148px;
            height:300px;
            border:solid 1px #a8c7ce;
            margin-left:2px;
            background-color: white;
        }
        #qrcode img{
            margin:50px auto;
        }
    </style>
    <script type="text/javascript">
        var treeID = '<%=session.getAttribute("organizationID")%>';
        var treePName='<%=session.getAttribute("organizationName")%>';
        var companyName='<%=c.getCompanyName()%>';
        var basePath='<%=basePath%>';
        var sccid = '<%=session.getAttribute("sccid")%>';
        var companyID='${account.companyID}';
        var pNumber =${pageNumber};
        var search='${search}';
        var ID = "";
        var notoken = 0;
        var tokens = 0;
        var token = 0;
        var personurl = "";

        $(function(){
            $(window).resize(function(){
                setTimeout(function () {
                    $("#aadTree1").height($(window).height()- 150);
                },100);
            });
            $("#aadTree1").height($(window).height()- 150);
        });

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
            <th width="20" align="center">
                序号
            </th>
            <th width="80" align="center">
                餐桌编号
            </th>
            <th width="100" align="center">
                品名
            </th>
            </th>
            <th width="100" align="center">
                公司名称
            </th>
            </th>
            <th width="100" align="center">
                部门
            </th>
            <th width="100" align="center">
                责任人
            </th>
            <th width="100" align="center">
                状态
            </th>
        </tr>
        </thead>
        <tbody>
        <%
            int number = 1;
        %>
        <s:iterator id="id" value="pageForm.list">
            <tr id="${ID}">
                <td>
                    <input type="radio" name="a" class="JQuerypersonvalue"
                           value="${ID}" />
                    <span id ="ppIDs" style="display: none">${ppID}</span>
                </td>
                <td>
                    <span><%=number%></span>
                </td>
                <td>
                    <span id="boardNo">${boardNo}</span>
                </td>
                <td>
                    <span>${name}</span>
                </td>
                <td>
                    <span>${companyName}</span>
                </td>
                <td>
                    <span>${deptName}</span>
                </td>
                <td>
                    <span id="personName">${personName}</span>
                    <span id="personID" style="display: none">${personID}</span>
                </td>
                <td>
                    <s:if test="%{#id.status==0}">
                        <span id="status">使用中</span>
                    </s:if>
                    <s:else>
                        <span id="status">未使用</span>
                    </s:else>
                </td>
            </tr>
            <%
                number++;
            %>
        </s:iterator>
        </tbody>
    </table>
    <c:import url="../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/cater/ea_getCaterList.jspa?pageNumber=${pageNumber}&search=${search}">
        </c:param>
    </c:import>
</div>


<!--添加餐桌 -->
<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
    <form name="caterForm" id="caterForm" method="post" enctype="multipart/form-data">
        <input type="submit" name="submit" style="display:none"/>
        <div class="content">
            <div class="contentbannb">
                <div class="drag">餐桌管理
                    <div class="close"></div>
                </div>
            </div>
            <table width="99%" id="table3">
                <tr>
                    <td>
                        <table width="699" height="263" border="0" id="catertable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                            <tr>
                                <td  height="40" align="right"><span class="STYLE1">公司名称：</span></td>
                                <td ><span id="companyNames"></span>
                                    <input type="hidden" id="companyName" name="cater.companyName" readOnly="true">
                                    </td>
                                <td height="40" align="right">责 任 人：</td>
                                <td><input type="hidden" id="personID" name="cater.personID" readonly="readonly" value="${cater.personID}" />
                                    <input type="text" id="personName" name="cater.personName" class="put3" value="${cater.personName}" readonly="readonly"  size="15"/>&nbsp;<a href="#" onclick="importGY('table3','personID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a></td>
                            </tr>
                            <tr>
                                <td height="40" align="right">部门名称：</td>
                                <td >
                                    <span id="deptNames"></span>
                                    <input type="hidden" id="deptName" name="cater.deptName" readonly="true"></td>
                                <td height= "46" align="right">选择物品：</td>
                                <td><input type="button" id="newG" name="button7" value="选择物品" /></td>
                                </select></td>
                            </tr>
                            <tr>
                                <td height = "46" align="right">物品名称：</td>
                                <td><span id = "goodsNames"></span>
                                    <input id="goodsName" name="cater.name" type="hidden"/>
                                </td>
                                <input type="hidden" id="ppID" name="cater.ppID" readonly="readonly" value="${cater.ppID}" />
                                <td height = "46" align="right">使用状态：</td>
                                <td><select name="cater.status" id="status">
                                    <option value="1">未使用</option>
                                    <option value="0">使用中</option>
                                </select></td>
                            </tr>
                            <tr>
                                <td height="30" colspan="5" align="center"><input name="cater.ID" id="catID" type="hidden" class="input"  size="20"/>
                                    <input name="cater.ID" id="caterID" type="hidden" class="input"  size="20"/>
                                    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                                    <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                                    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
        <s:token></s:token>
    </form>
</div>



<form name="productForm" id="productForm" method="post"
      enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none" />
    <div class="jqmWindow jqmWindowcss1" style="top: 5%;"
         id="PjqModel">
        <div class="content1" style="width: 100%;height: 400px;">
            <div class="contentbannb">
                <div class="drag">
                    选择产品
                </div>
            </div>
            <table width="99%" height="33" id="searchproduct" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td  align="right">
                        物品名称：
                    </td>
                    <td >
                        <input id="productName" name = "productPack.goodsName" type="text"/>
                    </td>
                    <td height="33">
                        <input type="button" class="btn02" id="chaxun"
                               name="button7" value="查询" />
                        <input type="button" class="btn02" id="qdpro"
                               name="button5" value="确定" />
                        <input type="button" class="btn02" id="JQueryreturngoods" name="button4"
                               value="关闭" />
                        <input type="hidden" name="parms" id="parms" />
                    </td>
                    <td width="80">
                        <a id="wpsy" title="0">上一页</a>
                    </td>
                    <td width="80">
                        <a id="wpxy" title="0">下一页</a>
                    </td>
                    <td width="100">
                        <a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
                                                         id="wpzycount"></span>&nbsp;&nbsp;页</a>
                    </td>
                </tr>
            </table>
            <table width="99%" border="0" align="center" cellpadding="0"
                   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                <tr>
                    <td width="99%" valign="top" align="left">
                        <div id="body_02"
                             style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <s:token></s:token>
</form>

<!-- 修改 -->
<div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModels">
    <form name="caterForms" id="caterForms" method="post" enctype="multipart/form-data">
        <input type="submit" name="submit" style="display:none"/>
        <div class="drag">
            修改
        </div>
        <table width="99%" id="table4"  >
            <tr>
                <td>
                    <table  border="0" id="catertables" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td height="40" align="right">责任人：</td>
                            <td>
                                <input type="hidden" id="personID" name="cater.personID" readonly="readonly" value="${cater.personID}" />
                                <input type="text" id="personName" name="cater.personName" readonly="readonly" value="${cater.personName}" size="15" />
                                <a href="#" onclick="importGY('table4','personID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
                            </td>
                        </tr>
                        <tr>
                            <td height = "46" align="right">使用状态：</td>
                            <td><select name="cater.status" id="statuss">
                                <option value="1">未使用</option>
                                <option value="0">使用中</option>
                            </select></td>
                        </tr>
                        <tr>
                            <td height="30" colspan="5" align="center">
                                <input name="cater.ID" id="IDs" type="hidden" class="input" value="${cater.ID}"  size="20"/>
                                <input type="button"   class="input-button JQuerySubmits" style="cursor:pointer;width:80px;" value="提交" />
                                <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                                <input type="button"  class="input-button JQueryreturns" style="cursor:pointer;width:80px;"  value="取消" /></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <s:token></s:token>
    </form>
</div>


<!----------------------------------------查询---------------------------------------- -->

<form name="caterSearchForm" id="caterSearchForm" method="post">
    <input type="submit" id="submit"  />
    <div class="jqmWindow" style="width: 300px; right: 35%; top: 10%"
         id="jqModelcarSearch">
        <div class="drag">
            查询信息
            <div class="close">
            </div>
        </div>
        <table id="carterSearchTable" cellpadding="5" cellspacing="5">
            <tr>
                <td>
                    查询条件
                </td>
            </tr>
            <tr>
                <td align="right">
                    餐桌编号：
                </td>
                <td>
                    <input name="caterSearch.boardNo" />
                </td>
            </tr>
            <tr>
                <td height="40" align="right">责任人：</td>
                <td>
                    <input type="hidden" id="personID" name="caterSearch.personID" readonly="readonly"  />
                    <input type="text" id="personName" name="caterSearch.personName" readonly="readonly"  size="15" />
                    <a href="#" onclick="importGY('carterSearchTable','personID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
                </td>
            </tr>
            <tr>
                <td align="right">
                    状态：
                </td>
                <td>
                    <select name="caterSearch.status">
                        <option value="1">未使用</option>
                        <option value="0">使用中</option>
                    </select>
                </td>
            </tr>

        </table>
        <div align="center">
            <input type="button" class="input-button" id="searchCater"
                   value=" 查询 " />
            <input name="search" type="hidden" value="search" />
        </div>
    </div>
</form>

<!----------------------------------------选择责任人---------------------------------------- -->
<div id="bankJqm" class="jqmWindow"
     style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
    <div style="background: #efg; margin-right: 500px;">
        <input style="display: none;" id="checkopertionID" />
        <input style="display: none;" id="checkopertionName" />
        <input style="display: none;" id="childopertionName" />
        <input style="display: none;" id="checkform" />
    </div>
    <iframe name="daoRu" id="daoRu" width="100%" height="360px"
            frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
    <div>
        <input type="button" class="input-button" id="DaoRuFanqd"
               value=" 确定 "
               style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
        <input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
               style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px"  />
    </div>
</div>
<!--JS遮罩层-->
<div id="fullbg"></div>

</body>
<input type="text" style="display: none;" id="treeid" />
<input type="text" style="display: none;" id="parentid" />
<input type="text" style="display: none;" id="treename" />
<input type="text" style="display: none;" id="parentname" />
<input type="text" style="display: none;" id="unitsID" />

<!-- 行业类别  -->
<%--<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"  id="JQueryaddress">--%>
    <%--<form name="SearchForm1" id="SearchForm1" method="post">--%>
        <%--<input type="submit" name="submit" style="display: none" />--%>
        <%--<div class="drag">--%>
            <%--请选择行业类别--%>
        <%--</div>--%>
        <%--<table width="400" id="cataffSearchTable">--%>
            <%--<tr class="trheight">--%>
                <%--<td colspan="2" class="JQueryaddress">--%>
                    <%--<select name="addressProvince" id="province" number='0'--%>
                            <%--style="width: 150px;">--%>
                    <%--</select>--%>
                    <%--<select name="addressCity" id="city" number='1'--%>
                            <%--style="width: 150px;">--%>
                    <%--</select>--%>

                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
        <%--<div align="center">--%>
            <%--<input type="button" class="input-button" id="tosearch1" onclick="getAddress()"--%>
                   <%--value="确定" />--%>
            <%--<input type="button" class="input-button JQueryreturn2" value="取消" />--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>