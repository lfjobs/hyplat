<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>公司凭证管理树</title>
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
             function baobiao(i){
          		var urlaa='';
          		if(i=='1'){
          			urlaa='<%=basePath%>/ea/splitbill/ea_toSprins.jspa?level=company&zz=06';
          		}else if(i=='2'){
          			urlaa='<%=basePath%>/ea/splitbill/ea_toSprins.jspa?level=company&zz=07';
          		}else if(i=='3'){
          			urlaa='<%=basePath%>
          	/ea/splitbill/ea_toSprins.jspa?level=company&zz=07';
          		}
          		window.open(urlaa);

          	}
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
                                <span class="folder" id="pzgl">凭证管理</span>
                                <ul>
                                    <li>
                                        <span class="folder" id="szpz">收支凭证</span>
                                         <ul>
	                                    <li>
	                                        <a href="<%=basePath%>/ea/splitbillcompany/ea_getCashierBillsList.jspa?zz=21&sztype=s'" target="mainframe">
                                       	 <span class="file" id="szpzgl">收支凭证管理</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="<%=basePath%>/ea/splitgoodsbill/ea_CsearchGood.jspa?zz=09&level=company&sztype=s" target="mainframe">
                                       	 <span class="file" id="szmxgl">收支明细管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" onclick="baobiao('1')">
                                       	 <span class="file" id="szyegl">收支余额管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="szys">收支预算完成率</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="sztz">收支调整完成率</span></a>
	                                    </li>
                                	  </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="srpz">收入凭证</span>
                                         <ul>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="srpzgl">收入凭证管理</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="<%=basePath%>/ea/splitgoodsbill/ea_CsearchGood.jspa?zz=07&level=company&sztype=s" target="mainframe">
                                       	 <span class="file" id="srmxgl">收入明细管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" onclick="baobiao('2')">
                                       	 <span class="file" id="sryegl">收入余额管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="srys">收入预算完成率</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="srtz">收入调整完成率</span></a>
	                                    </li>
                                	  </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="zcpz">支出凭证</span>
                                         <ul>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="szpzgl">支出凭证管理</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="<%=basePath%>/ea/splitgoodsbill/ea_CsearchGood.jspa?zz=08&level=company&sztype=s" target="mainframe">
                                       	 <span class="file" id="szmxgl">支出明细管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" onclick="baobiao('3')">
                                       	 <span class="file" id="szyegl">支出余额管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="szys">支出预算完成率</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="sztz">支出调整完成率</span></a>
	                                    </li>
                                	  </ul>
                                    </li>
                        </ul>
                    </td>
                    <td style="width: 85%;" valign="top">
                        <iframe id="mainframe" name="mainframe"
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
