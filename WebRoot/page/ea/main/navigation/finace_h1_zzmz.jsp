<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>公司总账明细树</title>
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
        <link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
        <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
        <script src="<%=basePath%>/js/ea/finance/Navigation/finace_na.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
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
</style>
    </head>
    <body>
        <div class="main_main">
            <table width="100%" cellspacing="0" cellpadding="0" "border="2">
                <tr>
                    <td id="qh_sw" style="width: 15%" valign="top">
                        <div class="qh_gg_nav">
                            &nbsp;
                            <span id="frametitle">总账明细</span>

                        </div>
                        <!--左边的树 -->

                        <ul id="navigation" style="width: 200px;"
                            class="filetree">
                            <li>
                                <span class="folder" id="zzmx">总账明细</span>
                                <ul id="zzgldd">
                                    <li>
                                        <span class="folder" id="zz">总账管理</span>
                                        <ul id="zzul">
                                            <li>
                                                <span class="folder" id="jcxxwh">基础信息维护</span>
                                                <ul>
                                                    <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="kmqcgl">科目期初管理</span></a>
                                                    </li>
                                                     <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="wpgl">物品管理</span></a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <span class="folder" id="pzgl">凭证管理</span>
                                                <ul>
                                                    <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="pzlr">凭证录入</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="pzsh">凭证审核</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="pzjz">凭证记账</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="javascript:void(0);" target="mainframe">
                                                        <span class="file" id="qmyjz">期末月结账</span></a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <span class="folder" id="zzgl">总账管理</span>
                                                <ul>
                                                    <li>
                                                        <a href="<%=basePath%>/ea/vaccount/ea_getVaccountList.jspa?zz=00" target="mainframe">
                                                        <span class="file" id="zzgl">总账管理</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="<%=basePath%>/ea/vaccount/ea_getVaccountList.jspa?zz=01" target="mainframe">
                                                        <span class="file" id="mxzgl">明细账管理</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="<%=basePath%>/ea/vsequence/ea_getVsequenceList.jspa" target="mainframe">
                                                        <span class="file" id="xsz">序时账</span></a>
                                                    </li>
                                                    <li>
                                                        <a href="<%=basePath%>/ea/csbjects/ea_toPage.jspa" target="mainframe">
                                                        <span class="file" id="kmyeb">科目余额表</span></a>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="kczgl">库存账管理</span>
                                         <ul>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="yhgl">验货管理</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="rkgl">入库管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="ckgl">出库管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="kcgl">库存管理</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="bsgl">报损管理</span></a>
	                                    </li>
                                                 <li>
                                                    <a href="javascript:void(0);" target="mainframe">
                                                    <span class="file" id="chhs">存货核算</span></a>
                                                 </li>
                                	  </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="gdzc">固定资产</span>
                                         <ul>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="gdzc">固定资产</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="zcbsgl">资产报损管理</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="zczj">资产增加</span></a>
	                                    </li>
	                                     <li>
	                                        <a href="javascript:void(0);" target="mainframe">
                                       	 <span class="file" id="acjs">资产减少</span></a>
	                                    </li>
	                                    <li>
	                                        <a href="<%=basePath%>/ea/splitbill/ea_getBillsDetailList.jspa?dtype=asset&level=company" target="mainframe">
                                       	 <span class="file" id="zcbb">资产报表</span></a>
	                                    </li>
                                	  </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="ysyfgl">应付应收管理</span>
                                         <ul>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="ysgl">应收管理</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="yfgl">应付管理</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="ysmx">应收明细</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="yfmx">应付明细</span></a>
                                        </li>
                                      </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="gzgl">工资管理</span>
                                         <ul>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="yfgz">应付工资</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="yfgz">已付工资</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="gzbb">工资报表</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="gzft">工资分摊</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="jjgz">计件工资</span></a>
                                        </li>
                                      </ul>
                                    </li>
                                    <li>
                                        <span class="folder" id="xsgl">销售管理</span>
                                         <ul>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="khgl">客户管理</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="xsdh">销售订货</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="xsfh">销售发货</span></a>
                                        </li>
                                         <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="xsth">销售退货</span></a>
                                        </li>
                                        <li>
                                            <a href="javascript:void(0);" target="mainframe">
                                         <span class="file" id="xsdb">销售调拨</span></a>
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
