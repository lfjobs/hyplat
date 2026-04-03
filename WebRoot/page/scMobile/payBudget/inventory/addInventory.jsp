<!DOCTYPE html>
<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");

%>
<html>
<head>
    <title>盘库商品</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/addInventory.css">
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/iScroll.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/Mdate.js" type="text/javascript" charset="utf-8"></script>--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/inventory/Mdate/needcss/Mdate.css">
    <style>
        .content {
            background: url(<%=basePath %>images/scMobile/payBudget/inventory/bg_01.png) no-repeat left top;
            background-size: 100%
        }
    </style>
    <script>
        var basePath = "<%=basePath %>";
        var pagecount;//总页面数（选择往来页分也用）
        var count;//总条数（选择往来页分也用）
        var pageSize;//每页多少条（选择往来页分也用）
        var pagenumber;//第几页（选择往来页分也用）
        var timer;//接收定时器用
    </script>
</head>
<body class="hy">
<%--往来公司（商家）--%>
<div class="div-name div_wlgs_name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_wlgs_search_id" class="" placeholder="请录入往来公司信息" name="" value="" />
            <input type="button" value="查询" onclick="initWlGsInfo();"/>
        </section>
        <div class="div_table" id="div_wlgs_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_wlgs_tk_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_wlgs_add">
                发布
            </p>
        </section>
    </div>
</div>
<%--往来个人--%>
<div class="div-name div_wlgr_name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_wlgr_search_id" class="" placeholder="请录入往来个人信息" name="" value="" />
            <input type="button" value="查询" onclick="initWlGrInfo();"/>
        </section>
        <div class="div_table" id="div_wlgr_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_wlgr_tk_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_wlgr_add">
                发布
            </p>
        </section>
    </div>
</div>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/inventory/register_return.png"/>
        </li>
        <li>
            盘库商品
        </li>
        <li>
            打印
        </li>
    </ul>
</header>
<div class="content">
<s:form id="f2" action="ea_toAddPayBudget.jspa" namespace="/ea/scBudget" method="POST" >
    <section class="con_co">
        <c:if test="${empty goodsManage}">
            <ul>
                <li class="clearfix">
                    <p class="noData">
                        未查询到产品信息，请添加产品后在尝试
                    </p>
                </li>
            </ul>
        </c:if>
        <c:if test="${!empty goodsManage}">
            <%--隐藏域传值用--%>
            <input type="hidden" name="addBean.tzFlag" value="true"/><%--跳转标识--%>
            <input type="hidden" name="addBean.goodsId" value="${addBean.goodsId}"/>
            <input type="hidden" name="addBean.goodsPhotoPath" value="${addBean.goodsPhotoPath}"/>
            <%--后退传值用--%>
            <input type="hidden" name="delGoodsBillsIds" value="${delGoodsBillsIds}"/><%--修改页面删除已保存的货物id数组--%>
            <input type="hidden" name="showFlag" value="${addBean.showFlag}"/><%--是否是选择全部false全部true单一部门--%>
            <input type="hidden" name="departmentID" value="${addBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="departmentName" value="${addBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <input type="hidden" name="cashierBillsId" value="${addBean.cashierBillsId}"/><%--已添加预算单id（修改用）--%>
            <%--前一页面传过来的数据--%>
            <input type="hidden" name="addBean.companyId" value="${addBean.companyId}"/><%--公司id--%>
            <input type="hidden" name="addBean.departmentID" value="${addBean.departmentID}"/><%--所选部门id--%>
            <input type="hidden" name="addBean.departmentName" value="${addBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
            <input type="hidden" name="addBean.billId" value="${addBean.billId}"/><%--单据凭证号--%>
            <input type="hidden" name="addBean.companyName" value="${addBean.companyName}"/><%--公司名称--%>
            <input type="hidden" name="addBean.itemName" value="${addBean.itemName}"/><%--项目名称--%>
            <input type="hidden" name="addBean.itemType" value="${addBean.itemType}"/><%--项目分类--%>
            <input type="hidden" name="addBean.xmType" value="${addBean.xmType}"/><%--项目类型--%>
            <input type="hidden" name="addBean.itemCode" value="${addBean.itemCode}"/><%--项目编号--%>
            <input type="hidden" name="addBean.itemId" value="${addBean.itemId}"/><%--项目id--%>
            <input type="hidden" name="addBean.staffFzrId" value="${addBean.staffFzrId}"/><%--负责人id--%>
            <input type="hidden" name="addBean.staffName" value="${addBean.staffName}"/><%--负责人名称--%>
            <input type="hidden" name="addBean.staffCode" value="${addBean.staffCode}"/><%--负责人编号--%>
            <input type="hidden" name="addBean.singleName" value="${addBean.singleName}"/><%--制单人名称--%>
            <input type="hidden" name="addBean.barcode" value="${addBean.barcode}"/><%--条形码--%>
            <ul>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>类别</p>
                    <input type="text" id="ttsw_lb" name="addBean.goodsTypeId" placeholder="自动获取/选择确定"  value="${addBean.goodsTypeId}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>条形码</p>
                    <input type="text" id="ttsw_txm" name="addBean.goodsBarCode" placeholder="自动获取" value="${addBean.goodsBarCode}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>品名编号</p>
                    <input type="text" id="ttsw_pmbh" name="addBean.goodsGoodsCoding" placeholder="自动获取"  value="${addBean.goodsGoodsCoding}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>品名名称</p>
                    <input type="text"  id="ttsw_pmmc" name="addBean.goodsGoodsName" placeholder="自动获取"  value="${addBean.goodsGoodsName}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>规格</p>
                    <input type="text" id="ttsw_gg" name="addBean.goodsStandard" placeholder="自动获取"  value="${addBean.goodsStandard}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>单位</p>
                    <input type="text" id="ttsw_dw" name="addBean.goodsVariableId" placeholder="自动获取"  value="${addBean.goodsVariableId}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>库存数量</p>
                    <input type="text"  id="ttsw_kcsl" name="addBean.invInvenQuantity" placeholder="数据库有库存数自动获取" value="${addBean.invInvenQuantity}" readonly/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>预算数量</p>
                    <input type="text" id="ttsw_yssl" name="addBean.budgetNumber" placeholder="请输入" value="" />
                </li>
                <li class="clearfix li_right_l">
                    <p class="left_name"><span>*</span>预算单价</p>
                    <input type="text" id="ttsw_ysdj" name="addBean.budgetUnitPrice" placeholder="请输入" value="" />
                </li>
                <li class="clearfix li_right_l">
                    <p class="left_name"><span>*</span>预算金额</p>
                    <input type="text" id="ttsw_ysje" name="addBean.budgetMoney" placeholder="请输入" value="" />
                </li>
                <li class="clearfix li_left_l">
                    <p class="left_name"><span>*</span>往来公司（商家）</p>
                    <div id="ttsw_wlgs_show">
                        <p id="ttsw_wlgs_text" class="txt" ></p>
                        <img src="<%=basePath %>images/scMobile/payBudget/inventory/wupinguanli_img_13.png"/>
                    </div>
                    <input type="hidden" id="ttsw_wlsj_check_id" name="addBean.currentCompanyId" value=""/><%--选中往来公司id--%>
                    <input type="hidden" id="ttsw_wlsj_check_name" name="addBean.currentCompany" value=""/><%--选中往来公司名称--%>
                </li>
                <li class="clearfix li_left_l">
                    <p class="left_name"><span>*</span>往来个人（商家责任人）</p>
                    <div id="ttsw_wlgr_show">
                        <p id="ttsw_wlgr_text" class="txt"></p>
                        <img src="<%=basePath %>images/scMobile/payBudget/inventory/wupinguanli_img_13.png"/>
                    </div>
                    <input type="hidden" id="ttsw_wlgr_check_id" name="addBean.currentPersonId" value=""/><%--选中往来个人id--%>
                    <input type="hidden" id="ttsw_wlgr_check_name" name="addBean.currentPerson" value=""/><%--选中往来个人名称--%>
                </li>
            </ul>
            <section class="btn_footer">
                <p onclick="toSubmit();">
                    提交审核
                </p>
            </section>
        </c:if>
    </section>
</s:form>
</div>
<%--页面添加数据封装成bean提交跳转页面用--%>
<s:form id="f1" action="" namespace="/ea/scBudget" method="POST" >
    <%--后退传值用--%>
    <input type="hidden" name="showFlag" value="${addBean.showFlag}"/><%--是否是选择全部false全部true单一部门--%>
    <input type="hidden" name="departmentID" value="${addBean.departmentID}"/><%--所选部门id--%>
    <input type="hidden" name="departmentName" value="${addBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" name="cashierBillsId" value="${addBean.cashierBillsId}"/><%--已添加预算单id（修改用）--%>
    <%--前一页面传过来的数据--%>
    <input type="hidden" name="addBean.companyId" value="${addBean.companyId}"/><%--公司id--%>
    <input type="hidden" name="addBean.departmentID" value="${addBean.departmentID}"/><%--所选部门id--%>
    <input type="hidden" name="addBean.departmentName" value="${addBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" name="addBean.billId" value="${addBean.billId}"/><%--单据凭证号--%>
    <input type="hidden" name="addBean.companyName" value="${addBean.companyName}"/><%--公司名称--%>
    <input type="hidden" name="addBean.itemName" value="${addBean.itemName}"/><%--项目名称--%>
    <input type="hidden" name="addBean.itemType" value="${addBean.itemType}"/><%--项目分类--%>
    <input type="hidden" name="addBean.xmType" value="${addBean.xmType}"/><%--项目类型--%>
    <input type="hidden" name="addBean.itemCode" value="${addBean.itemCode}"/><%--项目编号--%>
    <input type="hidden" name="addBean.itemId" value="${addBean.itemId}"/><%--项目id--%>
    <input type="hidden" name="addBean.staffFzrId" value="${addBean.staffFzrId}"/><%--负责人id--%>
    <input type="hidden" name="addBean.staffName" value="${addBean.staffName}"/><%--负责人名称--%>
    <input type="hidden" name="addBean.staffCode" value="${addBean.staffCode}"/><%--负责人编号--%>
    <input type="hidden" name="addBean.singleName" value="${addBean.singleName}"/><%--制单人名称--%>
    <input type="hidden" name="addBean.barcode" value="${addBean.barcode}"/><%--条形码--%>
    <input type="hidden" name="delGoodsBillsIds" value="${delGoodsBillsIds}"/><%--修改页面删除已保存的货物id数组--%>
</s:form>
</body>
<%--下拉控制js文件--%>
<script type="text/javascript" src="<%=basePath%>js/scMobile/payBudget/inventory/addInventory.js"></script>
<script>
    //提交
    function toSubmit(){
        //类别
        var lb = $('#ttsw_lb').val();
        if(lb == null || lb == ""){
            alert("请退后重新扫码");
            return false;
        }
        //条形码
        var txm = $('#ttsw_txm').val();
        if(txm == null || txm == ""){
            alert("请退后重新扫码");
            return false;
        }
        //品名编号
        var pmbh = $('#ttsw_pmbh').val();
        if(pmbh == null || pmbh == ""){
            alert("请退后重新扫码");
            return false;
        }
        //品名名称
        var pmmc = $('#ttsw_pmmc').val();
        if(pmmc == null || pmmc == ""){
            alert("请退后重新扫码");
            return false;
        }
        //规格
        var gg = $('#ttsw_gg').val();
        if(gg == null || gg == ""){
            alert("请退后重新扫码");
            return false;
        }
        //单位
        var dw = $('#ttsw_dw').val();
        if(dw == null || dw == ""){
            alert("请退后重新扫码");
            return false;
        }
        //库存数量
        var kcsl = $('#ttsw_kcsl').val();
        if(kcsl == null || kcsl == ""){
            alert("请退后重新扫码");
            return false;
        }
        //预算数量
        var yssl = $('#ttsw_yssl').val();
        if(yssl == null || yssl == ""){
            alert("请输入预算数量");
            return false;
        }else{
            var regu = "^[0-9]*$";
            var re = new RegExp(regu);
            if(!re.test(yssl)){
                alert("请输入正确的预算数量");
                return false;
            }
        }
        //预算单价
        var ysdj = $('#ttsw_ysdj').val();
        if(ysdj == null || ysdj == ""){
            alert("请输入预算单价");
            return false;
        }else{
            var regu = "^([0-9])[0-9]*(\\.\\w*)?$";
            var re = new RegExp(regu);
            if(!re.test(ysdj)){
                alert("请输入正确的预算单价");
                return false;
            }
        }
        //预算金额
        var ysje = $('#ttsw_ysje').val();
        if(ysje == null || ysje == ""){
            alert("请输入预算金额");
            return false;
        }else{
            var regu = "^([0-9])[0-9]*(\\.\\w*)?$";
            var re = new RegExp(regu);
            if(!re.test(ysje)){
                alert("请输入正确的预算金额");
                return false;
            }
        }
        //往来商家
        var wlsj = $('#ttsw_wlsj_check_id').val();
        if(wlsj == null || wlsj == ""){
            alert("请选择往来商家");
            return false;
        }
        //往来个人
        var wlgr = $('#ttsw_wlgr_check_id').val();
        if(wlgr == null || wlgr == ""){
            alert("请选择往来个人");
            return false;
        }
        //跳转页面标识
        var ident='${addBean.identification}';
        var url = "";
        if(ident == "add"){
            url = "ea_toAddPayBudget.jspa";
        }else{
            url = "ea_toUpPayBudget.jspa";
        }
        $("#f2").attr('action',url);    //通过jquery为action属性赋值
        $("#f2").submit();    //提交ID为myform的表单
    }

    //后退
    function toBack() {
        //跳转页面标识
        var ident='${addBean.identification}';
        var url = "";
        if(ident == "add"){
            url = "ea_toAddPayBudget.jspa";
        }else{
            url = "ea_toUpPayBudget.jspa";
        }
        $("#f1").attr('action',url);    //通过jquery为action属性赋值
        $("#f1").submit();    //提交ID为myform的表单
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
</script>
</html>
