<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>凭证管理树</title>
        <%@ page language="java" pageEncoding="UTF-8"%>
        <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
        %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
            type="text/css" />
        <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet"
            href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
        <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
        <script src="<%=basePath%>/js/ea/finance/Navigation/finace_na.js"></script>

        <script type="text/javascript">
   
             var basePath='<%=basePath%>';  
        </script>
        <style type="text/css">
#doctree {
    position: absoute;
    padding-top: 0cm;
    margin-top: 0px;
    width: 100%;
    background-color: #FFFFFF;
    float: left;
}

#qh_sw {
    width: 15%;
    border: 1px solid #DAE7F6;
}

.treeview li {
    margin: 0;
    padding: 1px 0 1px 16px;
}

.numcss {
    color: red;
}
.ssb{
color: #C2C2C2;}
</style>
    </head>
    <body>
        <div class="main_main">
            <table width="100%" cellspacing="0" cellpadding="0" "border="2">
                <tr>
                    <td id="qh_sw" style="width: 15%" valign="top">
                        <div class="qh_gg_nav">
                            &nbsp;
                            <span id="frametitle">凭证管理</span>

                        </div>
                        <!--左边的树 -->
                        <ul id="navigation" style="width: 200px;"
                            class="filetree">
                        <li>
                           <span class="folder">凭证管理</span>
                           <ul>
                            <li>
                                <span class="folder" id="pzgl">凭证管理</span>
                                <ul>
                                	<li>
                                        <a href="#" onclick="tonclick('pzlb')">
                                        <span class="file" id="pzlb">凭证类别</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('pzlr')">
                                        <span class="file" id="pzlr">凭证录入</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('pzsh')">
                                        <span class="file" id="pzsh">凭证审核</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('pzjz')">
                                        <span class="file" id="pzjz">凭证记账</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('pzyj')">
                                        <span class="file" id="pzyj">凭证月结</span></a>
                                    </li>
                                     <li>
                                        <a href="#" onclick="tonclick('kjnd')">
                                        <span class="file" id="pzyj">会计年度设定</span></a>
                                    </li>
                                     <li>
                                        <a href="#" onclick="tonclick('ndjz')">
                                        <span class="file" id="ndjz">年度结转</span></a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="folder" id="xgbb">相关报表</span>
                                <ul>
                                    <li>
                                        <a href="#" onclick="tonclick('pzly')">
                                        <span class="file" id="pzly">凭证列印</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('ssb')">
                                        <span class="file" id="ssb">试算表</span></a>
                                    </li>
                                    <li>
                                        <span class="file ssb" id="pzlsz">凭证流水账</span>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('xjlsz')">
                                        <span class="file" id="xjlsz">现金流水账</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('yhlsz')">
                                        <span class="file" id="yhlsz">银行流水账</span></a>
                                    </li>
                                    <li>
                                        <span class="file ssb" id="zzgl">总账管理</span>
                                    </li>
                                    <li>
                                        <span class="file ssb" id="mxzgl">明细账管理</span>
                                    </li>
                                    <li>
                                        <span class="file ssb" id="yemx">余额明细</span>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="folder" id="xgbb">管理分析</span>
                                <ul>
                                    <li>
                                        <a href="#" onclick="tonclick('zcfzbnrsd')">
                                        <span class="file" id="zcfzbnrsd">资产负债表内容设定</span></a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('zcfzb')">
                                        <span class="file" id="zcfzb">资产负债表</span></a>
                                    </li>
                                </ul>
                            </li>
                          </ul>
                    </li>
                    </ul>
                    </td>
                    <td style="width: 85%;" valign="top">
                        <iframe id="mainframe"
                            frameborder="0" style="width: 100%;"></iframe>
                    </td>
                </tr>
            </table>
        </div>
        <script type="text/javascript">
     $(function(){
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#navigation").height($(window).height()- 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#navigation").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height()+ "px"}); 
     });
</script>
    </body>


</html>
