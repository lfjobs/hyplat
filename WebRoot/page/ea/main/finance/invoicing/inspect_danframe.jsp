<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CAccount ca = (CAccount) session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base href="<%=basePath%>"/>
    <title>验货单添加</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript">
    </script>
    <link href="<%=basePath%>css/ea/main.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/admin_main111.css"/>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/finance/invoicing/inspect_danframe.js"></script>
    <style type="text/css" media="Print">
        @media Print {
            .noPrint {
                DISPLAY: none;
            }

            @media Screen {
                .noDisaplay {
                    DISPLAY: none;
                }
            }
        }
    </style>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pageNumber = '${pageForm.pageNumber}';
        var staffid = "<%=ca.getStaffID()%>";
        var pageNum = 0;
        var pageCount = '${pageForm.pageCount}';
        //var str = '${str}';//选中验货物品业务id
        var notoken = 0;
        var token = 0;
        var financialbillID = '${financialbillID}';
        // onload="document.getElementById('xsxyClone').click()"
    </script>
</head>
<body style="background:#ffffff;border-top:5px solid #c5d9f1;" height="550px">
<div style="width: 100%;height:0px;border-top:0px;" id="titleClone">
    <div id="buttonClone" style="text-align: left;width: 100%;">
        <table id="titleClone" width="100%" border="0" align="center" cellpadding="0"
               cellspacing="0" style="background:#dbe5f1;margin-top:1px;
					margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
            <tr>
                <td align="left" style="height:24px;">
                    <nobr>
                        <span id="JQuerySubmitgd">
                        <input type="button" id="JQuerySubmitgd" class="menubtn"
                               value="提交审核" onclick="save()"/>
                        </span>
                        <%--<span id="okrk">
                            <input type="button" id="okrk" class="menubtn" value="合格入库" onclick="saverk()"/>
                        </span>--%>
                        <input type="button" id="noth" class="menubtn grey disabled" value="退货" disabled="disabled"/>
                        <input id="dayinClone" type="button" class="dayinCloneClass menubtn" value="打印预览"/>
                        <input type="button" id="JQueryClose" class="menubtn" value="关闭"/>
                        <input type="button" id="help" class="menubtn grey disabled" value="帮助" disabled="disabled"/>
                        <span id="pageCode" style="font-weight: bold; position: absolute; margin-left: 5px" class="noPrint"></span>
                    </nobr>
                </td>
            </tr>
        </table>
    </div>
    <div id="contextClone" style="height:700px;">
        <iframe name="main" width="100%" height="90%" scrolling="no" frameborder="0" src="about:blank"
                noresize="noResize" vspale="0" id="frameClone"
                class="frameCloneClass" border="0">
        </iframe>
    </div>
</div>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
    framespacing="0" height="0"></iframe>
</body>
</html>