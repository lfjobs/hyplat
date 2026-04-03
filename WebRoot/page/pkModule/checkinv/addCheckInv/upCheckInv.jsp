<!DOCTYPE html>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <meta charset="utf-8"/>
    <title>盘库单修改</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/addCheckInv/addCheckInv.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/checkInvList/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/checkInvList/swiper/swiper.min.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/mdate/needcss/Mdate.css">
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/iScroll.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/Mdate.js" type="text/javascript" charset="utf-8"></script>

</head>
<script>
    var basePath = "<%=basePath%>";
    var treeid = "<%=paramMap.get("companyId")%>";//公司id
    var staffId = "<%=paramMap.get("staffId")%>";//登录人id
    var showFlag = ${showFlag};//false所有查询全部数据 true查询当前部门数据
    var departmentId = "${departmentID}";//部门id
    var depotName ="${depotName}";
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
    var staffName;



</script>
<body class="hy">

<header>
    <ul class="clearfix">
        <li onclick="toBack()">
            <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/register_return.png"/>
        </li>
        <li>
            盘库
        </li>
        <li class="li_dy" hidden>
            打印
        </li>
        <li class="li_img" onclick="toQuery()">
            <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/fdj.png"/>
        </li>
    </ul>
</header>
<div class="content">
    <s:form id="f2" action="ea_upCostSheet.jspa"  namespace="/ea/cashinv"  method="POST">
        <input type="hidden" name="invtFbCheck.invtFbCheckID" value="${invtFbCheckID}"/><%--已添加预算单id（修改用）--%>
        <input type="hidden" id="ttsw_hid_delGoods2" name="delGoodsBillsIds" value="${invtFbCheckID}"/><%--修改页面删除已保存的货物id数组--%>
        <section class="sec_con clearfix">
            <ul class="ul_con">
                <li class="clearfix">
                    <div class="div_ul_c">
                        <p>
                            单据编号：
                            <input type="text" name="invtFbCheck.fbillid" id="ttsw_billID" value="${invtFbCheck.fbillid}" placeholder="自动获取" required readonly/>
                        </p>
                        <ul>
                            <li class="txt">
                                公司名称：
                                <input type="text" id="ttsw_companyNmae" name="invtFbCheck.companyName"  class="gs_name" value="${invtFbCheck.companyName}" required readonly/>

                            </li>
                            <li>
                                单据类型：
                                <input type="text" name="invtFbCheck.warehousename" id="pankudan" value="盘库单" readonly/>
                            </li>
                        </ul>
                        <ul>
                            <li class="txt">

                                    <%--所选分部门则显示不可修改--%>
                                <c:if test="${showFlag eq true}">
                                    部门：
                                    <input type="text" name="invtFbCheck.orgName" id="ttsw_dep_y_name" value="${invtFbCheck.orgName}" placeholder="请选择部门" required readonly />
                                    <input type="text" name="invtFbCheck.organizationid" id="ttsw_dep_y_id" value="${invtFbCheck.organizationid}" readonly style="display: none;"/><%--所选部门id--%>

                                </c:if>
                                    <%--所选为总列表，则显示下来自己选择--%>
                                <c:if test="${showFlag eq false}">

                                    部门：
                                    <input type="text" class="csbm_xiala-1" id="ttsw_dep_n_name" name="invtFbCheck.orgName" value="${invtFbCheck.orgName}" placeholder="请选择部门" required readonly/>
                                    <input type="text" class="csbm_id_xiala-1" id="ttsw_dep_n_id" name="invtFbCheck.organizationid" value="${invtFbCheck.organizationid}" readonly style="display: none;"/><%--所选部门id--%>
                                    <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>

                                    <div class="select_box csbm_select_box1"></div>
                                </c:if>

                            </li>


                            <li>
                                单据状态：
                                <input type="text" name="invtFbCheck.billstatus" id=" "  value="${invtFbCheck.billstatus}" placeholder="自动获取" readonly/>
                            </li>
                        </ul>
                    </div>
                </li>
            </ul>
        </section>

        <section class="sec_con2">
            <ul class="ul_con2">
                <li class="clearfix">
                    <label for=""><span>*</span>盘库日期：</label>
                    <input type="text" name="invtFbCheck.billsdate" value="<fmt:formatDate value="${invtFbCheck.billsdate}" pattern="yyyy-MM-dd" />"
                           placeholder="" required readonly/>
                </li>
                <li class="clearfix" >
                    <label for="" name="cashierBills.depotName"><span>*</span>盘库仓库:
                        <input type="text" name="invtFbCheck.Warehousename" id="ttsw_depotName" value="${invtFbCheck.warehousename}" required readonly/>
                    </label>
                </li>
                <li class="clearfix">
                    <label for=""><span></span>盘库人：</label>
                    <input type="text"  name="invtFbCheck.staffname" class="xiala-1"  value="${invtFbCheck.staffname}" placeholder="" required readonly/>
                    <input type="text" name="invtFbCheck.staffid" class="ttsw_emp_id" value="${invtFbCheck.staffid}" style="display: none;"/>
                    <input type="text" name="invtFbCheck.staffname" class="ttsw_emp_name" value="${invtFbCheck.staffname}" style="display: none;"/>
                </li>
            </ul>
        </section>
    </s:form>
    <section class="sec_con3" id="goodsTable" >
        <%--<c:if test="${!empty scanningMap}">--%>
        <table   width="100%"  style="table-layout:fixed">
            <tr>
                <td type="text">
                    物品名称
                </td>
                <td>
                    单价
                </td>
                <c:if test="${showStyle==1 ||showStyle==2}">
                    <td>
                        库存数
                    </td>
                </c:if>

                <c:if test="${showStyle==1 ||showStyle==3}">
                    <td>
                        盘库数量
                    </td>
                </c:if>

                <c:if test="${showStyle==1 ||showStyle==4}">
                    <td>
                        误差
                    </td>
                </c:if>
                <td></td>
            </tr>
            <c:forEach items="${goodBeanslist}" var="entity" varStatus="v">
                <tr class="abccc">
                    <td type="text">${entity.goodsName}</td>
                    <td class="ttsw_jj">${entity.price}</td>
                    <c:if test="${showStyle==1 ||showStyle==2}">
                        <td class="dnum">${entity.invenQuantity}</td>
                    </c:if>
                    <c:if test="${showStyle==1 ||showStyle==3}">
                        <td class="rnum">
                            <input type="text"  value="${entity.realQuantity}"  id="ttsw_depotNum"   name="cashierBills.depotNum"  onkeyup="clearNoNum(this)"  maxlength="5" size="40"/><input hidden class="goodPrice"  value="0">
                        </td>
                    </c:if>
                    <c:if test="${showStyle==1 ||showStyle==4}">
                        <td class="wucha">${entity.error}</td>
                    </c:if>
                    <td onclick="shanchu('${entity.goodsID}')">
                       <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/shanchu.png"style="width: 32px;height: 27px"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <%--</c:if>--%>
        <div class="clearfix">
            <div class="left"  >
                <p>合计：¥<span id="ttsw_allPrice" >${countPrice}</span></p>
                <p>共<span id="ttsw_num_pro">${goodBeanslist.size()}</span>种商品</p>
            </div>
            <div class="right">
                <p onclick="toUpdate()">提交保存</p>
            </div>
        </div>
    </section>
</div>
<%--页面添加数据封装成bean提交跳转页面用--%>
<s:form id="f1" action="" namespace="/ea/cashinv" method="POST">
    <input type="hidden" name="addBean.invtFbCheckID" value="${invtFbCheckID}"/><%--已添加预算单id（修改用）--%>
    <input type="hidden" name="addBean.showFlag" value="${showFlag}"/><%--是否是选择全部false全部true单一部门--%>
    <input type="hidden" id="ttsw_hid_billId" name="addBean.billId" value="${addBean.billId}"/><%--单据凭证号--%>
    <input type="hidden" id="ttsw_hid_departmentID" name="addBean.departmentID" value="${addBean.departmentID}"/><%--所选部门id--%>
    <input type="hidden" id="ttsw_hid_departmentName" name="addBean.departmentName" value="${addBean.departmentName}"/><%--所选部门名称（-1为所有部门）--%>
    <input type="hidden" id="ttsw_hid_companyName" name="addBean.companyName" value="${addBean.companyName}"/><%--公司名称--%>
    <input type="hidden" id="ttsw_hid_companyId" name="addBean.companyId" value="${addBean.companyId}"/><%--公司id--%>
    <input type="hidden" id="ttsw_hid_staffFzrId" name="addBean.staffFzrId" value="${addBean.staffFzrId}"/><%--负责人id--%>
    <input type="hidden" id="ttsw_hid_staffName" name="addBean.staffName" value="${addBean.staffName}"/><%--负责人名称--%>
    <input type="hidden" id="ttsw_hid_depotName" name="addBean.Warehousename" value="${depotName}"/><%--盘库仓库--%>
    <input type="hidden" id="ttsw_hid_barcode" name="addBean.barcode" value=""/><%--条形码--%>
    <input type="hidden" name="addBean.identification" value="add"/><%--跳转页面标识--%>
    <input type="hidden" id="ttsw_hid_depotNum" name="addBean.depotNum" value="" >  <%--盘库数量--%>
    <input type="hidden" name="addBean.identification" value="update"/><%--跳转页面标识--%>
    <input type="hidden" id="ttsw_hid_delGoods" name="delGoodsBillsIds" value="${delGoodsBillsIds}"/><%--修改页面删除已保存的货物id数组--%>
</s:form>
</body>
<script type="text/javascript" src="<%=basePath%>js/pkModule/checkinv/checkInvList/addCheckInv.js"></script>
<%--调用安卓js文件--%>
<script type="text/javascript" src="<%=basePath %>js/pkModule/checkinv/addinventory/productslaunch.js"></script>
<script type="text/javascript">
    //计算总列表宽度
    var listWidth_1 = $(".ul_nav li").length;
    var listWidth = 0;
    for (var i = 0; i < listWidth_1; i++) {
        listWidth += $(".ul_nav").children("li").eq(i).outerWidth(true);
    }
    $(".ul_nav").width(listWidth + 25);
    //商品点击选中
    $(document).on("click", "aside", function () {
        if ($(this).is(".aside_yes")) {
            $(this).removeClass().addClass("aside_no");
        } else {
            $(this).removeClass().addClass("aside_yes");
        }
    });
    //nav点击选中
    $(document).on("click", ".ul_nav li", function () {
        $(".ul_nav li").removeClass("active");
        $(this).addClass("active");
    });


    //输入的盘库数量不能为负数
    function clearNoNum(obj){
        obj.value = obj.value.replace(/[^\d.]/g,"");  //清除“数字”和“.”以外的字符
        obj.value = obj.value.replace(/^\./g,"");  //验证第一个字符是数字而不是.
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的.
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
//    alert(obj.value);//输入的盘库数量
        var  depotNum   = obj.value;
//    alert(depotNum);
        $("#ttsw_hid_depotNum").val(depotNum);
        var tds = $(obj).parent().prevAll();
        var price = 0;
        for(i = 0;i<tds.length;i++){
            if(tds[i].className == "ttsw_jj"){
                price = $(tds[i]).text()
            }
        }
        var count =(obj.value)*price;
        $($(obj).next()).val(count);
//        alert("单个商品总价"+ $($(obj).next()).val())
        var prices = $(".goodPrice");
        var goodsPrices = 0;
        for(i=0;i<prices.length;i++){
            if(prices[i].className == "goodPrice"){
                goodsPrices += parseInt($(prices[i]).val());
            }
        }
        var numn=0;
        //库存数
        for(i = 0;i<tds.length;i++){
            if(tds[i].className == "dnum"){
                numn = $(tds[i]).text();
            }
        }
        /* numn 库存数
         obj.value  盘库数量
         nums  每个商品的误差=库存数-盘库数量
         */
        var nums=numn-(obj.value);
        if(nums<0){
            alert("误差不能为负数")
        }
        $("#ttsw_allPrice").text(goodsPrices);
        $(obj).parent().siblings(".wucha").text(nums);
    }
    //点击审核提交
    function toUpdate(){
        $("#f2").submit();
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
//负责人id
        var hid_staffFzrId = $(".ttsw_emp_id").val();
        if(hid_staffFzrId == null || hid_staffFzrId == "" ){
            message =  "请选择盘库号";
        }
//制单人名称
        var hid_staffName = $(".ttsw_emp_name").val();
        if(hid_staffName == null || hid_staffName == "" ){
            message =  "请填写盘库人";
        }
//仓库
        var hid_depotName = $("#ttsw_depotName").val();
        if(hid_depotName == null || hid_depotName == "" ){
            message =  "请填写盘库仓库";
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

    //根据id删除以保存数据
    function toRemoveGoodsBills(inventoryId){
        var delGoodsBillsIds = $("#ttsw_hid_delGoods").val();
        if(delGoodsBillsIds == null || delGoodsBillsIds == ""){
            delGoodsBillsIds = "-1";
        }
        delGoodsBillsIds += "," +inventoryId;
        $("#ttsw_hid_delGoods").val(delGoodsBillsIds);
        $("#ttsw_hid_delGoods2").val(delGoodsBillsIds);//修改提交用
        $("#"+inventoryId).remove();
        countPrice();//计算商品价格
    }
    //后退
    function toBack() {
        window.location.href = basePath + "ea/cashinv/ea_getCheckInvList.jspa?companyid="+treeid+"&&staffId="+staffId+"";
    }
    //跳转查询页面
    function toQuery(){
        var url = "ea/queryUtil/ea_toQuery.jspa?jumpType=YSD_LB";
        window.location.href = basePath + url;
    }
    //下拉部门js
    //点击创收部门，异步获取部门信息，初始化下拉列表
    $('.csbm_xiala-1').on('click', function() {
        var url = basePath+"ea/menuUtil/sajax_ea_findOrgByAcc.jspa";
        $.ajax({
            url: url,
            type: "POST",
            data : {
                "organizationID":encodeURI(treeid),
                "datesete":new Date(),
            },
            dataType: "json",
            success: function cbf(data){
                var member = (new Function("return " + data))();//格式化返回参数
                var oList = member.organizationList;
                depIdArray=new Array(oList.length);
                depNameArray=new Array(oList.length);
                for (var i = 0; i < oList.length; i++) {
                    depIdArray[i] = oList[i].organizationID;//部门id
                    depNameArray[i] = oList[i].organizationName;//部门名称
                }
                initSelectSwiper(0);
            },error: function cbf(data){
                alert("数据获取失败！");
            }
        });
    });
    /**
     *初始化下拉信息
     */
    function initSelectSwiper(num){
        if(num==0){//部门下拉初始化
            depSelectSwiper();
        }
    }

    /**
     * 部门下拉初始化数据
     */
    function depSelectSwiper(){
        if(showFlag == false) {//所有部门.
            //创收部门模拟下拉框
            var csS1 = new selectSwiper({
                el: '.csbm_select_box1',
                data: depNameArray,
                init: function(index) {
                    if(index !== -1) {
                        $('.btn1').html(this.data[index]);
                    }
                },
                okFunUndefind: function() {
                    alert('必须选择一项');
                    return false;
                },
                okFun: function(index) {
                    $('.csbm_xiala-1').val(this.data[index]);
                    $('.csbm_id_xiala-1').val(depIdArray[index]);
                    $("body").removeClass("body_yc");
                },
                closeFun: function() {
                    console.log('取消');
                    $("body").removeClass("body_yc");
                },
            });
            csS1.openSelectSwiper();
            $("body").addClass("body_yc");
        }
    }

</script>
<script>
    /**
     *跳转到库房列表页面方法
     */
    function panku() {
//判断盘库仓库是否为空，不为空的情况下输入
//给隐藏域中的对象赋值
//是否是选择全部false全部true单一部门
        $("#ttsw_hid_showFlag").val(showFlag);
//所选部门id名称
        var hid_departmentID,hid_departmentName;
        if(showFlag == true){
            hid_departmentID = $("#ttsw_dep_y_id").val();
            hid_departmentName = $("#ttsw_dep_y_name").val();
        }else{
            hid_departmentID = $("#ttsw_dep_n_id").val();
            hid_departmentName = $("#ttsw_dep_n_name").val();
        }
        if(hid_departmentID == null || hid_departmentID == "" || hid_departmentName == null || hid_departmentName == ""  ){
            alert("请选择创收部门");
            return false;
        }
        $("#ttsw_hid_departmentID").val(hid_departmentID);
        $("#ttsw_hid_departmentName").val(hid_departmentName);
//单据凭证号
        var hid_billId = $("#ttsw_billID").val();
        if(hid_billId == null || hid_billId == "" ){
            alert("请录入单据凭证号");
            return false;
        }
        $("#ttsw_hid_billId").val(hid_billId);
//公司名称
        var hid_companyName = $("#ttsw_companyNmae").val();
        if(hid_companyName == null || hid_companyName == "" ){
            alert("请录入公司名称");
            return false;
        }
        $("#ttsw_hid_companyName").val(hid_companyName);

//负责人id
        var hid_staffFzrId = $(".ttsw_emp_id").val();
        if(hid_staffFzrId == null || hid_staffFzrId == "" ){
            alert("请选择负责人");
            return false;
        }
        $("#ttsw_hid_staffFzrId").val(hid_staffFzrId);
//负责人名称
        var hid_staffName = $(".ttsw_emp_name").val();
        $("#ttsw_hid_staffName").val(hid_staffName);
//公司id
        var treeid = "company201009046vxdyzy4wg0000000025";//测试用
        $("#ttsw_hid_companyId").val(treeid);

//点击盘库仓库的时候获取到部门信息
        var url = "ea/cashinv/ea_depot.jspa";
        $("#f1").attr('action',url);    //通过jquery为action属性赋值
        $("#f1").submit();    //提交ID为myform的表单
    }
    function  weighing() {
        //判断仓库是否为空
        var hid_depotName = $("#ttsw_depotName").val();
        if (hid_depotName == null || hid_depotName == "") {
            alert("请填写盘库仓库");
            return false;
        }
        window.location.href = basePath + "/page/WFJClient/ProductsLaunch/ProductsWeighing.jsp?companyID=" + treeid+"&showButton='true'" ;
    }
    function getUrlParam(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r!=null) return unescape(r[2]);
        return null;
    }
    function  shanchu(goodsID) {
        var id = getUrlParam('fbillid');

        window.location.href = basePath+"ea/cashinv/ea_delUpdateGoods.jspa?goodsID="+goodsID+"&fbillid="+id;
    }
    function loadTable(data) {
        console.log(data)
        //把section中原有的数据获取出来（审核，添加。。。）
        var table ;
        if (rowNum == 0){
            table = '<tr>'+
                '<td type="text">'+
                '物品名称'+
                '</td>'+
                '<td>'+
                '单价'+
                '</td>'+
                '<td>'+
                '库存数'+
                '</td>'+
                '<td>'+
                '盘库数量'+
                '</td>'+
                '<td>'+
                '误差'+
                '</td>'+
                '</tr>'
        }
        table += '<tr>\n' +
            '<td  type="text">\n' +
            data.goodsName +
            '</td>\n' +
            '<td class="ttsw_jj" >\n' +
            data.unitPrice +
            '</td>\n' +
            '<td  class="dnum">\n' +
            data.invenQuantity +
            '</td>\n' +
            '<td>\n' +
            '<input type="text"  value="0"  id="ttsw_depotNum"   name="cashierBills.depotNum"  onkeyup="clearNoNum(this)"  maxlength="5" size="40"/><input hidden class="goodPrice"  value="0">\n' +
            '</td>\n' +
            '<td class="wucha"></td>\n' +
            '</tr>'
        $("#goodsL").append(table);
    }

</script>

</html>
