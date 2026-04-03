<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
        <title>电子称预置键</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/presetlist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
        <script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
        <script language="javascript" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>

        <script type="text/javascript">
            var scpId = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var notoken = 0;
            var number = 0;


        </script>
        <style type="text/css">
            input.input-button {
                margin: 4px;
            }
        </style>



    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="scale.scaleNo" id="caleNo" style="display:none"/>
			<input  name="scale.address" id="address" style="display:none"/>
            <input type="hidden" name="search" value="search" />

			<s:token/>
        </form>
        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                      <th width="80" align="center">
                      编号
                    </th>
                   <th width="80" align="center">
                       键数
                    </th>

                     <th width="90" align="center">
                       责任人
                    </th>
                    <th width="140" align="center">
                        日期
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${scpId}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${scpId}" />
                            <span id="scpKey" style="display:none;">${scpKey}</span>
                            <span id="scpId" style="display:none;">${scpId}</span>
						</td>
						<td>
							<span id="preCode">${preCode}</span>
						</td>
						<td>
							<span id="keyCount">${keyCount}</span>
						</td>
                        <td>
                            <span id="staffname">${staffname}</span>
                        </td>
                        <td>
                            <span id="cdate">${fn:substring(cdate,0,19)}</span>
                        </td>

					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/scale/ea_getPreKeyList.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
            <div class="jqmWindow" style="width:220px;right: 30%;top:5%" id="jqModeladd">
                <div class="drag">
                     电子秤预置键数量
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">


                    <tr><td align="right">预置键数量：</td>
                        <td>
                            <select class="keyCount"  name="presetPage.keyCount">
                                <option value="28" selected>28键横4X纵7</option>
                                <option value="77">77键横11X纵7</option>
                                <option value="40">40键横10X纵4</option>
                                <option value="40">105键横15X纵7</option>
                            </select>
                        </td>

                    </tr>


                </table>
                <div align="center">
                    <input type="button" class="input-button" id="setup" value=" 设置 "  style="margin: 10px;" />
                </div>
            </div>


        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:280px;height:660px;right: 30%;top:10%" id="jqModelset">
                <input type="submit" name="submit" style="display:none"/>
                <input type="hidden" name="presetPage.keyCount" id="kec" value=""/>
                <input type="hidden" name="presetPage.scpId" id="scpIds" value=""/>
                <input type="hidden" name="presetPage.scpKey" id="scpKeys" value=""/>
                <div class="drag">
                    预置键设置
                    <div class="close">
                    </div>
                </div>
                <div id="outline">
                <div id="content" style="border: 1px solid #00a0e9;background: #ffffff;width: 6.95cm;height: 12.5cm;padding: 0.2cm 0.2cm 0 0.2cm;">
                <table  id="setTable" style="width: 6.95cm;height: 12cm;background:#ffffff;" cellspacing="0" cellpadding="0">

                </table>
                </div>
                </div>
                <div align="center">
                    <%--<input type="button" class="input-button" id="yijian" value="  一键分配  "  style="margin: 10px;" />--%>
                    <input type="button" class="input-button" id="save" value=" 保存 "  style="margin: 10px;" />
                    <input type="button" class="input-button" id="print" value=" 打印 "  onclick="prn1_preview()"style="margin: 10px;" />
                </div>
            </div>
        </form>


        <div id="products" class="popMain">
            <div class="choose-box">
                <div class="choosetitle">
                    <span>选择商品</span>
                </div>
                <div class="chooseborder">
                    <table width="99%" height="33" id="searchpro"     border="0"
                           align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; background: #FFFFFF;">
                        <tr>
                            <td width="100" align="right">
                                产品PLU或名称：
                            </td>
                            <td width="110">
                                <input name="parameter" class="input" id="parameter" size="10"
                                       style="margin-left: 2px;" />
                            </td>
                            <td height="33">
                                <input type="button" class="btn01" id="searchProduct"
                                       name="button7" value=" 查询 "/>
                                <input type="button" class="btn01" id="selectProduct"
                                       name="button5" value=" 确定 " />



                            </td>
                            <td width="80">
                                <a id="wpsyp" title="0" style="cursor:pointer;">上一页</a>
                            </td>
                            <td width="80">
                                <a id="wpxyp" title="0" style="cursor:pointer;">下一页</a>
                            </td>
                            <td width="100">
                                <a id="wpzyp">共&nbsp;&nbsp; <span style="color: red"
                                                                  id="wpzycountp"></span>&nbsp;&nbsp;页 </a>
                            </td>
                        </tr>
                    </table>
                    <table width="99%" border="0" align="center" cellpadding="0"
                           cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td width="83%" valign="top" align="left">
                                <div id="body_03"
                                     style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
                                </div>
                            </td>
                        </tr>
                    </table>

                </div>
                <div class="choose-box-bottom">
                    <input type="botton" onclick="hide('products')" value="关闭" id="gb"/>
                </div>
            </div>
        </div>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
        <object  id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0>
            <embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0></embed>
        </object>

        <form id="form1"><table><tr><td>您好</td><td>您好</tr><tr><td>您好</td><td>您好</tr><tr><td>您好</td><td>您好</tr></table></form>
    </body>

</html>
