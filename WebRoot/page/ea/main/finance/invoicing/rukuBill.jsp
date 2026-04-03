<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");
%>
<html lang="en">
<head>
    <meta charset='utf-8'>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <meta name="x5-orientation" content="portrait">
    <title>入库单</title>
    <link rel="stylesheet" href="<%=basePath %>css/scMobile/payBudget/budgetList/style.css">
    <style>
        .bug-con ul li p {
            float: right;
        }

        footer ul {
            width: 40%;
            margin: 0 auto;
        }

        footer ul li {
            width: 50% !important;
        }

        .div-tingyong {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, .4);
            z-index: 999;

        }

        .div-tingyong .box {
            width: 80%;
            margin: 30vh auto 0 auto
        }

        .div-tingyong .box > p {
            border-top-left-radius: .3rem;
            border-top-right-radius: .3rem;
            background-color: #f74c32;
            height: 2.5rem;
            line-height: 2.5rem;
            font-size: .65rem;
            color: #fefefe;
            text-align: center;
            position: relative
        }

        .div-tingyong .box > p img {
            position: absolute;
            width: .95rem;
            top: -8%;
            right: -2%
        }

        .div-tingyong .box .div-box p {
            background-color: #fff;
            height: 3.6rem;
            line-height: 3.6rem;
            font-size: .65rem;
            color: #222;
            text-align: center;
            border-bottom: .025rem solid #eee
        }

        .div-tingyong .left {
            float: left;
        }

        .div-tingyong .right {
            float: right;
        }

        .div-tingyong .box .div-box div p {
            background-color: #fff;
            width: 50%;
            height: 2.3rem;
            line-height: 2.3rem;
            font-size: .65rem;
            color: #222;
            text-align: center
        }

        .div-tingyong .box .div-box div p:nth-of-type(1) {
            border-bottom-left-radius: .3rem;
            color: #666;
            position: relative
        }

        .div-tingyong .box .div-box div p:nth-of-type(1):before {
            content: "";
            position: absolute;
            height: 100%;
            width: .025rem;
            right: .025rem;
            background-color: #eee
        }

        .div-tingyong .box .div-box div p:nth-of-type(2) {
            border-bottom-right-radius: .3rem
        }

        .bug-nav2 {
            padding: 0 !important;
        }

        .bug-nav2 li {
            padding: 0 0.5rem !important;
            width: auto !important;
        }

        .footer-one ul {
            width: 20% !important;
        }

        .footer-one ul li {
            width: 100% !important;
        }

        .box-nav {
            overflow-x: scroll;
            margin-bottom: -0.5rem;
        }

        .active {
            color: #1daf48;
        }
    </style>
</head>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var comid = "${param.compayid}";//公司id
    var staffid = "${param.staffid}";//登录人id
    var sccid = "${param.sccId}";//登录人id
</script>
<script type="text/javascript">
    function onFocus() {
        var target = event.target
        setTimeout(function () {
            target.readOnly = false
        }, 0)
    }
    function onBlur() {
        event.target.readOnly = true
    }
    var staffid = "${param.staffid}";
    var sort = "${param.sort}";
    //大屏用
    var posNum = "";//大屏id
    var dpFlag = false;//大屏标识
    try {
        //判断是否是大屏终端
        posNum = Android.forAndroidDeviceId();
        var url = "ea/smg/sajax_sm_isExistPosNum.jspa";
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            async: true,
            data: {
                posNum: posNum
            },
            success: function (data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                if (result != "0") {
                    posNum = "";
                }
            },
            error: function (data) {
                // alert("验证失败");
                posNum = "";
            }
        });
        console.log('---' + posNum);
        if (posNum == null || posNum == "") {//跳转小屏
            dpFlag = false;
        } else {//跳转大屏
            dpFlag = true;
        }
    } catch (e) {
        dpFlag = false;
    }
</script>
<body>
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img id="close-tingyong" src="img/img_031.png" alt=""/></p>
        <div class="div-box">
            <p id="tk">您确定要停用吗？</p>
            <div class="clearfix">
                <p class="left">取消</p>
                <p class="right">确定</p>
            </div>
        </div>
    </div>
</div>
<ul class="header">
    <li onclick="toBack();"><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png" alt=""></li>
    <li>收货验货入库</li>
    <%--<li><img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-more.png" alt=""></li>--%>
</ul>
<!--一级菜单-->
<div class="bug-nav-box" id="ttsw_one_menu_id"></div>
<div class="box-nav">
    <ul class="bug-nav2">
        <li id="rk">入库单</li>
        <li id="xx">线下采购入库</li>
        <li id="sc">生产产品入库</li>
        <li id="yd">异动产品入库</li>
        <li id="xs">线上采购入库</li>
        <%--<li>打印</li>--%>
    </ul>
</div>
<div class="bug-con  main_hide">
    <ul id="tj_con">
        <%--<c:forEach items="${pageForm.list}" var = "entity" varStatus="v">
            <li class="clearfix<c:if test="${v.index+1 ne fn:length(pageForm.list)}"> ttsw_last</c:if>">
                <section id="sec-checked">
                    <aside class="aside_no" checkCasId="${entity.cashierBillsID}">
                        <img class="img_no" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                        <img class="img_yes" src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                    </aside>
                    <h5>单据凭证号：${entity.journalNum}<img src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>
                </section>
                <div onclick="toDetail('${entity.cashierBillsID}');" >
                    <p>公司：${entity.companyName}</p>
                    <p>项目编号：${entity.projectCode}</p>
                    <p>项目分类：${entity.xmtypename}</p>
                    <p>项目名称：${entity.projectName}</p>
                    <p>单据类别：${entity.billsType}</p>
                    <p>责任人部门：${entity.departmentName}</p>
                    <p>责任人：${entity.staffName}(${entity.staffCode})</p>
                    <p>制单人：${entity.inputName}</p>
                    <p>制单日期：${fn:substring(entity.cashierDate,0,19)}</p>
                    <p class="un">单据状态：
                        <span>
                                <c:if test="${!empty entity.paystatus }">
                                    <c:if test="${entity.paystatus eq '00' }">项目未分配</c:if>
                                    <c:if test="${entity.paystatus eq '01'}">项目已分配未跟踪</c:if>
                                    <c:if test="${entity.paystatus eq '02'}">项目已跟踪未考评</c:if>
                                    <c:if test="${entity.paystatus eq '03'}">项目已考评</c:if>
                                </c:if>
                                <c:if test="${empty entity.paystatus}">
                                    <c:if test="${entity.status eq '00'}">草稿</c:if>
                                    <c:if test="${entity.status eq '01'}">审核中-招标前</c:if>
                                    <c:if test="${entity.status eq '02'}">已通过-招标前</c:if>
                                    <c:if test="${entity.status eq '03'}">比价审核中</c:if>
                                    <c:if test="${entity.status eq '04'}">已提交资金申请</c:if>
                                    <c:if test="${entity.status eq '05'}">待会计审核</c:if>
                                    <c:if test="${entity.status eq '06'}">待出纳审核</c:if>
                                    <c:if test="${entity.status eq '07'}">已审核</c:if>
                                    <c:if test="${entity.status eq '20'}">税务单据</c:if>
                                    <c:if test="${entity.status eq '08'}">三审已归档</c:if>
                                    <c:if test="${entity.status eq '09'}">待确认收款</c:if>
                                    <c:if test="${entity.status eq '40'}">待确定预算收入单</c:if>
                                    <c:if test="${entity.status eq '45'}">已收款</c:if>
                                    <c:if test="${entity.status eq '46'}">系统生成</c:if>
                                    <c:if test="${entity.status eq '11'}">驳回待修改</c:if>
                                </c:if>
                            </span>
                    </p>
                </div>
            </li>
        </c:forEach>
        <c:if test="${empty pageForm}">
            <li style="text-align:center;">暂无数据</li>
        </c:if>--%>
    </ul>
</div>
<footer>
    <ul>
        <li id="smli">
            <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-5.png">
            <h5>扫码</h5>
        </li>
        <li id="wei">
            <img src="<%=basePath %>images/scMobile/payBudget/budgetList/foot-1.png">
            <h5>无码称重</h5>
        </li>
    </ul>
</footer>
<input type="text" id="displayIndex" style="opacity: 0;" onfocus="onFocus()" onblur="onBlur()" autofocus/>
</body>
<script src="<%=basePath %>js/scMobile/payBudget/budgetList/jquery-2.1.1.min.js"></script>
<script type="application/javascript" src="<%=basePath %>js/scMobile/payBudget/budgetList/font-size.js"></script>
<script type="application/javascript" src="<%=basePath %>js/scMobile/payBudget/budgetList/javascript.js"></script>
<script type="application/javascript" src="<%=basePath %>js/ea/finance/invoicing/rukubill.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/localforage.min.js"></script>
<script>

    //添加方法
    function toAdd() {
        var url = "ea/scBudget/ea_toAddPayBudget.jspa";
        var parameter = "?showFlag=" + showFlag + "&departmentID=" + departmentId;
        if (showFlag) {//单独部门传部门名称过去
            parameter += "&departmentName=" + departmentName;
        }
        window.location.href = basePath + url + parameter;
    }
    //计算总列表宽度
    var listWidth_1 = $(".bug-nav2 li").length;
    //    alert(listWidth_1);
    var listWidth = 0;
    for (var i = 0; i < listWidth_1; i++) {
        listWidth += $(".bug-nav2").children("li").eq(i).outerWidth(true);
//        alert($(".bug-nav2").children("li").eq(i).outerWidth(true))
    }
    $(".bug-nav2").width(listWidth + 15);
    //点了变色
    $(".bug-nav2 li").click(function () {
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
    })
    //跳转详情
    function toDetail(id) {
        var url = "ea/ruku/ea_getrukuBill.jspa";
        var parameter = "?rkid=" + id;
        /*if (showFlag) {//单独部门传部门名称过去
            parameter += "&departmentName=" + departmentName;
        }*/
        window.location.href = basePath + url + parameter;
    }

    //修改预算单
    /*function toUpdate(){
     //循环获取选中的值
     var id = "";
     $(".aside_yes").each(function (){
     id=$(this).attr("checkCasId");
     });
     if(id != "" && id != null){
     var url = "ea/scBudget/ea_toUpPayBudget.jspa";
     var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&cashierBillsId="+id;
     if(showFlag){//单独部门传部门名称过去
     parameter += "&departmentName="+departmentName;
     }
     window.location.href = basePath + url + parameter;
     }else{
     alert("请选择要修改的数据");
     return false;
     }
     }*/

    //删除预算单
    /*function delCostSheet(){
     //循环获取选中的值
     var id = "";
     $(".aside_yes").each(function (){
     id=$(this).attr("checkCasId");
     });
     if(id != "" && id != null){
     var r = confirm("确定删除该数据吗？");
     if (r == true) {
     var url = "ea/scBudget/ea_delCostSheet.jspa?cashierBillsId="+id;
     window.location.href = basePath + url;
     }
     }else{
     alert("请选择要删除的数据");
     return false;
     }
     }*/

    //跳转查询页面
    function toQuery() {
        var url = "ea/queryUtil/ea_toQuery.jspa?jumpType=YSD_LB";
        window.location.href = basePath + url;
    }
</script>
</html>