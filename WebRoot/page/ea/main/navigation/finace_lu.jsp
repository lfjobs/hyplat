<%@ page language="java" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>集团财务现金使用管理</title>
<!-- <link href="<%=basePath%>css/navegate.css" rel="stylesheet" type="text/css" /> -->
<link href="<%=basePath%>css/navigation_a.css" rel="stylesheet"
    type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
         <script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
    <br />
    <table style="width:100%;border:0; padding: 0;margin:0">
        <tr>
            <td>
                <!--项目预算招标管理-->
                <table>
                    <tr>
                        <td  rowspan="2">
                            <div class="na_back_img_ks"></div>
                            <div class="center_a">
                                <strong>资金使用管理</strong>
                            </div>
                        </td>
                        <td ><div class="na_back_img_jt_hx"></div></td>     
                            <td><table class="d">
                                    <tr>
                                        <td >
                                        <div class="na_back_img"></div>
                                        <div class="center_a" >收入管理</div></td>
                                        <td >
                                        <div class="na_back_img" ></div>
                                        <div class="center_a">支出管理</div></td>    
                                        <td >
                                        <div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/splitbill/ea_toPage2.jspa?level=allCompany'"></div>
                                        <div class="center_a">收入支出余额管理</div>
                                        </td>
                                    </tr>
                                </table></td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</body>
</html>
