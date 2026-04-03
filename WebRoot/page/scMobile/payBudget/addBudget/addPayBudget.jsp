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
    <meta charset="utf-8" />
    <title>预算单添加</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/addBudget.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js" type="text/javascript" charset="utf-8"></script>
</head>
<style>
    .content {
        background: url(<%=basePath %>/images/scMobile/payBudget/addBudget/bg_01.png) no-repeat left top;
        background-size: 100%
    }
</style>
<script>
    //初始化数据
    var basePath="<%=basePath%>";
    var treeid = "<%=paramMap.get("companyId")%>";//公司id
    var showFlag = ${showFlag};//false所有查询全部数据 true查询当前部门数据
    var departmentId="${departmentID}";//创收部门id
    var depNameArray;//部门名称（下拉显示用）
    var depIdArray;//部门id（下拉显示用）
    var staffIdArray;//负责人id（下拉显示用）
    var staffNameArray;//负责人名称（下拉显示用）
    var staffCodeArray;//负责编码名称（下拉显示用）
    var pagecount;//总页面数（选择项目页分也用）
    var count;//总条数（选择项目页分也用）
    var pageSize;//每页多少条（选择项目页分也用）
    var pagenumber;//第几页（选择项目页分也用）
    var timer;//接收定时器用
</script>
<style>
    #ttsw_billID{
        width: 55%!important;
    }
</style>
<body class="hy">
<%--项目名称/分类显示--%>
<div class="div-name">
    <div>
        <section class="clearfix header">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_03.png"/>
            <input type="text" id="ttsw_item_search_id" class="" placeholder="搜索项目名称" name="" value="" />
            <input type="button" value="查询" onclick="initItemInfo();"/>
        </section>
        <div id="div_table">
            <table border="0" cellspacing="0" cellpadding="0" id="ttsw_item_id">
            </table>
        </div>
        <section class="button fixed_bottom">
            <p id="p_add">
                确定
            </p>
        </section>
    </div>
</div>
<%--整体页面--%>
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            预算单添加
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="f2" action="ea_saveCostSheet.jspa" namespace="/ea/scBudget" method="POST">
    <section class="sec_con1">
        <p>
            <label for=""><span></span>单据凭证号：</label>
            <input type="text"  name="cashierBills.journalNum" id="ttsw_billID" value="${billID}" placeholder="自动获取" required readonly/>
        </p>
        <div class="clearfix">
            <p>
                <label for=""><span></span>公司：</label>
                <input type="text" id="ttsw_companyNmae" name="cashierBills.companyName"  class="gs_name" value="${companyName}" placeholder="" required readonly/>
            </p>
            <%--所选分部门则显示不可修改--%>
            <c:if test="${showFlag eq true}">
                <p>
                    <label for=""><span></span>创收部门：</label>
                    <input type="text" name="cashierBills.departmentName" id="ttsw_dep_y_name" value="${departmentName}" placeholder="请选择部门" required readonly />
                    <input type="text" name="cashierBills.departmentID" id="ttsw_dep_y_id" value="${departmentID}" readonly style="display: none;"/><%--所选部门id--%>
                </p>
            </c:if>
            <%--所选为总列表，则显示下来自己选择--%>
            <c:if test="${showFlag eq false}">
                <p class="clearfix">
                    <label for=""><span>*</span>创收部门： </label>
                    <input type="text" class="csbm_xiala-1" id="ttsw_dep_n_name" name="cashierBills.departmentName" value="${addBean.departmentName}" placeholder="请选择部门" required readonly/>
                    <input type="text" class="csbm_id_xiala-1" id="ttsw_dep_n_id" name="cashierBills.departmentID" value="${addBean.departmentID}" readonly style="display: none;"/><%--所选部门id--%>
                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                </p>
                <div class="select_box csbm_select_box1"></div>
            </c:if>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>项目名称：</label>
                <input type="text" name="cashierBills.projectName" id="project-name" value="${addBean.itemName}" placeholder="请选择项目名称"required readonly/>
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
            </p>
            <p class="clearfix">
                <label for=""><span>*</span>项目分类： </label>
                <input type="text" name="cashierBills.xmtypename" id="project-fl" value="${addBean.itemType}" placeholder="请选择项目分类" required readonly/>
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
            </p>
            <input type="text" id="ttsw_item_check_id" name="cashierBills.proID" value="${addBean.itemId}" style="display: none;"/><%--所选项目ppid--%>
            <input type="text" id="ttsw_item_check_type" name="cashierBills.xmtype" value="${addBean.xmType}" style="display: none;"/><%--所选项目类型--%>
            <input type="text" id="ttsw_item_check_code" name="cashierBills.projectCode" value="${addBean.itemCode}" style="display: none;"/><%--所选项目编号--%>
        </div>
        <div class="clearfix">
            <p class="clearfix">
                <label for=""><span>*</span>责任人： </label>
                <input type="text" class="xiala-1"  value="${empty addBean.staffName ? staff.staffName:addBean.staffName}(${empty addBean.staffCode ? staff.staffCode:addBean.staffCode})" placeholder="" required readonly/>
                <input type="text" name="cashierBills.staffID" class="ttsw_emp_id" value="${empty addBean.staffFzrId ? staff.staffID:addBean.staffFzrId}" style="display: none;"/>
                <input type="text" name="cashierBills.staffName" class="ttsw_emp_name" value="${empty addBean.staffName ? staff.staffName:addBean.staffName}" style="display: none;"/>
                <input type="text" name="cashierBills.staffCode" class="ttsw_emp_code" value="${empty addBean.staffCode ? staff.staffCode:addBean.staffCode}" style="display: none;"/>
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
            </p>
            <div class="select_box select_box1"></div>
        </div>
        <div class="clearfix">
            <p>
                <label for=""><span></span>制单人：</label>
                <input type="text" name="" id="ttsw_singleName" value="${empty addBean.singleName ? staff.staffName:addBean.singleName}" placeholder="自动获取" required  readonly/>
            </p>
            <p>
                <label for=""><span>*</span>制单日期： </label>
                <input type="text" name="" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" placeholder="" required readonly/>
            </p>
        </div>
        <p>
            <label for=""><span></span>单据状态： </label>
            <input type="text" name="" id="" value="草稿" placeholder="自动获取" readonly/>
        </p>
    </section>
    </s:form>
    <section class="sec_con2">
        <%--选择具体项目后显示项目详细信息--%>
        <c:if test="${!empty scanningMap}">
            <ul class="ul_con">
                <c:forEach items="${scanningMap}" var="entity" varStatus="v">
                    <li class="clearfix" id="${entity.key}">
                        <aside class="aside_no">
                            <img class="img_no" src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_07.png"/>
                            <img class="img_yes" src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_10.png"/>
                        </aside>
                        <section class="clearfix">
                            <div>
                                <img src="<%=basePath %>${entity.value.goodsPhotoPath}"/>
                            </div>
                            <ul>
                                <li class="img_no1 txt">${entity.value.goodsGoodsName}</li>
                                <li class="img_no2 txt">条码：${entity.value.goodsBarCode}</li>
                                <li class="img_no3 txt">
                                    <span>规格：${entity.value.goodsStandard}</span>
                                    单位：${entity.value.goodsVariableId}
                                </li>
                                <li class="img_no4 txt">
                                    <span>进价：¥${entity.value.budgetUnitPrice}</span>
                                    状态：<span>未审</span>
                                </li>
                                <img onclick="toRemove(${entity.key});" class="img_del" src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_del.png"/>
                            </ul>
                        </section>
                        <input type="hidden" class="ttsw_jj" value="${entity.value.budgetUnitPrice}"/><%--价格--%>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <div class="clearfix">
            <div class="left">
                <p>合计：¥<span id="ttsw_allPrice">0</span></p>
                <p>共<span id="ttsw_num_pro">0</span>种商品</p>
            </div>
            <div class="right">
                <p onclick="toAdd();">审核</p>
                <p>添加</p>
            </div>
        </div>
    </section>
    <ul class="ul_con3 clearfix">
        <%--  <li id="ttsw_smq_id" onclick="Android.callcamera();"/> --%>
        <li id="ttsw_smq_id" onclick="toAndroidCallcamera();"/>
            <p class="p_img">
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_07.png"/>
            </p>
            <p class="p_p">
                扫码枪
            </p>
        </li>
        <li>
            <p class="p_img">
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_09.png"/>
            </p>
            <p class="p_p">
                扫码
            </p>
        </li>
        <li>
            <p class="p_img">
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_11.png"/>
            </p>
            <p class="p_p">
                物品管理
            </p>
        </li>
        <li>
            <p class="p_img">
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_13.png"/>
            </p>
            <p class="p_p">
                商城
            </p>
        </li>
        <li>
            <p class="p_img">
                <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_15.png"/>
            </p>
            <p class="p_p">
                常用采购单
            </p>
        </li>
    </ul>
</div>
<%--页面添加数据封装成bean提交跳转页面用--%>
<s:form id="f1" action="" namespace="/ea/scBudget" method="POST">
    <input type="hidden" name="addBean.showFlag" value="${showFlag}"/><%--是否是选择全部false全部true单一部门--%>
    <input type="hidden" id="ttsw_hid_companyId" name="addBean.companyId" value=""/><%--公司id--%>
    <input type="hidden" id="ttsw_hid_departmentID" name="addBean.departmentID" value=""/><%--所选部门id--%>
    <input type="hidden" id="ttsw_hid_departmentName" name="addBean.departmentName" value=""/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" id="ttsw_hid_billId" name="addBean.billId" value=""/><%--单据凭证号--%>
    <input type="hidden" id="ttsw_hid_companyName" name="addBean.companyName" value=""/><%--公司名称--%>
    <input type="hidden" id="ttsw_hid_itemName" name="addBean.itemName" value=""/><%--项目名称--%>
    <input type="hidden" id="ttsw_hid_itemType" name="addBean.itemType" value=""/><%--项目分类--%>
    <input type="hidden" id="ttsw_hid_xmType" name="addBean.xmType" value=""/><%--项目类型--%>
    <input type="hidden" id="ttsw_hid_itemCode" name="addBean.itemCode" value=""/><%--项目编号--%>
    <input type="hidden" id="ttsw_hid_itemId" name="addBean.itemId" value=""/><%--项目id--%>
    <input type="hidden" id="ttsw_hid_staffFzrId" name="addBean.staffFzrId" value=""/><%--负责人id--%>
    <input type="hidden" id="ttsw_hid_staffName" name="addBean.staffName" value=""/><%--负责人名称--%>
    <input type="hidden" id="ttsw_hid_staffCode" name="addBean.staffCode" value=""/><%--负责人编号--%>
    <input type="hidden" id="ttsw_hid_singleName" name="addBean.singleName" value=""/><%--制单人名称--%>
    <input type="hidden" id="ttsw_hid_barcode" name="addBean.barcode" value=""/><%--条形码--%>
    <input type="hidden" name="addBean.identification" value="add"/><%--跳转页面标识--%>
</s:form>
</body>
<%--下拉控制js文件--%>
<script type="text/javascript" src="<%=basePath%>js/scMobile/payBudget/addBudget/addPayBudget.js"></script>
<%--调用安卓js文件--%>
<script type="text/javascript" src="<%=basePath %>js/scMobile/payBudget/addBudget/productslaunch.js"></script>
<script>

    //点击审核提交
    function toAdd(){
        var result = toCheckNull(0);
        if(result == "true"){//验证通过
            $("#f2").submit();
        }else{
            alert(result);
            return false;
        }

    }

    //调用扫码枪获取货品信息
    function toAndroidCallcamera(){
        var result = toCheckNull(1);
        if(result == "true"){//验证通过
            Android.callcamera();
            //calltiaoma('111');
        }else{
            alert(result);
            return false;
        }
    }

    //校验是否为空
    function toCheckNull(num){
        //所选部门id名称
        var message = "true";
        var hid_departmentID,hid_departmentName;
        if(showFlag == true){
            hid_departmentID = $("#ttsw_dep_y_id").val();
            hid_departmentName = $("#ttsw_dep_y_name").val();
        }else{
            hid_departmentID = $("#ttsw_dep_n_id").val();
            hid_departmentName = $("#ttsw_dep_n_name").val();
        }
        if(hid_departmentID == null || hid_departmentID == "" || hid_departmentName == null || hid_departmentName == ""  ){
            message = "请选择创收部门";
        }
        //单据凭证号
        var hid_billId = $("#ttsw_billID").val();
        if(hid_billId == null || hid_billId == "" ){
            message = "请录入单据凭证号";
        }
        //公司名称
        var hid_companyName = $("#ttsw_companyNmae").val();
        if(hid_companyName == null || hid_companyName == "" ){
            message = "请录入公司名称";
        }
        //项目名称
        var hid_itemName = $("#project-name").val();
        if(hid_itemName == null || hid_itemName == "" ){
            message =  "请选择项目名称";
        }
        //项目分类
        var hid_itemType = $("#project-fl").val();
        if(hid_itemType == null || hid_itemType == "" ){
            message =  "请选择项目分类";
        }
        //负责人id
        var hid_staffFzrId = $(".ttsw_emp_id").val();
        if(hid_staffFzrId == null || hid_staffFzrId == "" ){
            message =  "请选择负责人";
        }
        //制单人名称
        var hid_singleName = $("#ttsw_singleName").val();
        if(hid_singleName == null || hid_singleName == "" ){
            message =  "请填写制单人";
        }
        //判断金额是否为0
        if(num == 0){
            var priceAll = $("#ttsw_allPrice").text();
            if(priceAll == "" || priceAll == null || priceAll == "0" ){
                message = "请扫描商品或商品金额不能为0";
            }
        }
        return message;
    }

    //后退
    function toBack() {
        window.location.href = basePath + "ea/scBudget/ea_toPayBudgetList.jspa";
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
