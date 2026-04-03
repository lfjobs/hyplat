<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applydp.css" />
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <!--下拉框插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/swiper.min.css"/>
    <script src="<%=basePath%>js/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/swiper/js/dySelect.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_accountinfo.js"></script>
    <!--地址联动插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/layout/css/layout.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/layout/css/scs.min.css"/>
    <script src="<%=basePath%>js/layout/js/jquery.scs.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/layout/js/CNAddrArr.min.js" type="text/javascript" charset="utf-8"></script>
    <title>公司认证</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var  staffID = "${staffID}";
        var organization_type = "${applyParam.organization_type}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a href="<%=basePath%>ea/merch/ea_getApplyCard.jspa?applyParam.out_request_no=${applyParam.out_request_no}&companyID=${companyID}&staffID=${staffID}">

            <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            银行卡管理
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav">
        <ul class="clearfix" style="display: flex !important;overflow-x: scroll">
            <li >
                商家认领
            </li>
            <c:if test="${applyParam.organization_type ne '2401'}">
                <li>
                    证件认证
                </li>
            </c:if>
            <li>
                法人认证
            </li>
            <li class="active">
                银行卡管理
            </li>
            <li>
                元素代码
            </li>
            <li>
                集团管理
            </li>
        </ul>
    </section>
    <form name="form1" id="form1" method="post" enctype="multipart/form-data">
    <section class="sec-con">
        <c:if test="${applyParam.organization_type eq '2401'}">
            <p class="p-01">
                <label for="">账户类型</label>
                <input type="text" id="" value="对私账户" readonly />
                <input type="hidden" name="account_info.bank_account_type"  value="75" />
            </p>

            <section class="sec-03 sec-show clearfix">
                <p class="left">
                    银行卡照片
                </p>
                <div class="right">
                    <input type="file" name="accountcopy" id="sdfFile1" value="" onchange="f_change1(this);" >
                    <img alt="图片"  src="<%=basePath%>${account_info.account_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_015.png'"id="imgSdf1">
                </div>
            </section>

        </c:if>

        <c:if test="${applyParam.organization_type eq '2'||applyParam.organization_type eq '3'||applyParam.organization_type eq '1708'}">
            <p class="p-01">
                <label for="">账户类型</label>
                <input type="text" id="" value="对公账户" readonly />
                <input type="hidden" name="account_info.bank_account_type"  value="74" />
            </p>

        </c:if>
        <c:if test="${applyParam.organization_type eq '4'}">

            <div class="div-01 clearfix">
                <p class="p-lx">账户类型</p>
                <div class="box">
                    <input type="hidden" name="account_info.bank_account_type"  value="75"  class="bank_account_type"/>
                    <div class="divxl"><p><img src="<%=basePath%>images/ea/office/pbapply/img_019.png" alt="" /></p><span class="btn5">对私账户</span></div>
                    <div class="select_box select_box5"></div>
                </div>
            </div>
            <section class="sec-03 sec-show clearfix">
                <p class="left">
                    银行卡照片
                </p>
                <div class="right">
                    <input type="file" name="accountcopy" id="sdfFile1" value="" onchange="f_change1(this);" >
                    <img alt="图片"  src="<%=basePath%>${account_info.account_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_015.png'"id="imgSdf1">
                </div>
            </section>


        </c:if>




        </p>
        <div class="div-01 clearfix">
            <p class="p-lx">开户银行</p>
            <div class="box">
                <input type="hidden" name="account_info.account_bank"   class="notnull" data="开户银行" value="${account_info.account_bank}" id="account_bank" />
                <div class="divxl"><p><img src="<%=basePath%>images/ea/office/pbapply/img_019.png" alt="" /></p><span class="btn3">${account_info.account_bank eq null||account_info.account_bank eq ''?'请选择':account_info.account_bank}</span></div>
                <div class="select_box select_box3"></div>
            </div>
        </div>

        <p class="p-01">
            <label for="">开户名称</label>
            <input type="text" name="account_info.account_name"   class="notnull" data="开户名称" value="${account_info.account_name}" />
        </p>

        <div class="liandong">
            <!--地址联动开始-->
            <!--联动本体-->
            <input type="text" readonly placeholder="Click me to pick an address" id="myAddrs" name="addr" data-key="1-36-37" value="北京 北京市 东城区" />
            <!--联动显示-->
            <ul class="ul-dizhi">
                <li>
                    <p>
                        开户银行省市
                    </p>
                    <p>
                        <input type="hidden" name="account_info.bank_address"  id="bank_address" class="notnull" data="开户银行省市"  value="${account_info.bank_address}" />
                        <span class="sp-01">${(account_info.bank_address eq null||account_info.bank_address eq "")?"请选择":account_info.bank_address}</span>
                        <img src="<%=basePath%>images/ea/office/pbapply/img_019.png"/>
                    </p>
                </li>
                <%--<li>--%>
                    <%--<p>--%>
                        <%--城市--%>
                    <%--</p>--%>
                    <%--<p>--%>
                        <%--<span class="sp-02">北京</span>--%>
                        <%--<img src="<%=basePath%>images/ea/office/pbapply/img_019.png"/>--%>
                    <%--</p>--%>
                <%--</li>--%>
                <%--<li>--%>
                    <%--<p>--%>
                        <%--区县--%>
                    <%--</p>--%>
                    <%--<p>--%>
                        <%--<span class="sp-03">东城区</span>--%>
                        <%--<img src="<%=basePath%>images/ea/office/pbapply/img_019.png"/>--%>
                    <%--</p>--%>
                <%--</li>--%>

            </ul>
            <!--联动结束-->
        </div>
        <%--<p class="p-01">--%>
            <%--<label for="">开户银行省市编码</label>--%>
           <input type="hidden" name="account_info.bank_address_code"  value="${account_info.bank_address_code}" />
        <%--</p>--%>
        <p class="p-01 bank_name" style="display: none;">
            <label for="">开户银行全称 （含支行）</label>
            <input type="text" name="account_info.bank_name"  value="${account_info.bank_name}"  class="notnull" data="开户银行全称" />
        </p>

        <p class="p-01">
            <label for="">银行帐号</label>
            <input type="text" name="account_info.account_number" value="${account_info.account_number}"  class="notnull" data="银行帐号" id="account_number"/>
        </p>

        <div class="div-bottom">

        </div>
    </section>
        <div class="div-p-02" style="display: none;">
            <p>错误提示</p>
        </div>
    <section class="sec-footer clearfix">
        <%--<p class="p-s">
            <a href="<%=basePath%>ea/merch/ea_getApplyCard.jspa?applyParam.out_request_no=${applyParam.out_request_no}&companyID=${companyID}&staffID=${staffID}"  style="color:#fff;">上一步</a>

        </p>
         <p id="next1" class="p-x">
            下一步
         </p>--%>
        <p id="next1">
            确认
        </p>
        <input type="submit" name="submit" style="display:none;"/>

        <input type="hidden" name="companyID"   value="${companyID}"/>
        <input type="hidden" name="staffID"   value="${staffID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
        <input type="hidden" name="mode"   value="account_info"/>
        <input type="hidden" name="account_info.acId"  value="${account_info.acId}" />
        <input type="hidden" name="account_info.acKey"  value="${account_info.acKey}" />
        <input type="hidden" name="account_info.account_copy"  value="${account_info.account_copy}" />

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </section>
    </form>
</div>
</body>
<script src="<%=basePath%>js/swiper/js/index.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $(function() {
        /**
         * 通过数组id获取地址列表数组
         *
         * @param {Number} id
         * @return {Array}
         */
        function getAddrsArrayById(id) {
            var results = [];
            if (addr_arr[id] != undefined)
                addr_arr[id].forEach(function(subArr) {
                    results.push({
                        key: subArr[0],
                        val: subArr[1]
                    });
                });
            else {
                return;
            }
            return results;
        }
        /**
         * 通过开始的key获取开始时应该选中开始数组中哪个元素
         *
         * @param {Array} StartArr
         * @param {Number|String} key
         * @return {Number}
         */
        function getStartIndexByKeyFromStartArr(startArr, key) {
            var result = 0;
            if (startArr != undefined)
                startArr.forEach(function(obj, index) {
                    if (obj.key == key) {
                        result = index;
                        return false;
                    }
                });
            return result;
        }

        //bind the click event for 'input' element
        $("#myAddrs").click(function() {
            var PROVINCES = [],
                startCities = [],
                startDists = [];
            //Province data，shall never change.
            addr_arr[0].forEach(function(prov) {
                PROVINCES.push({
                    key: prov[0],
                    val: prov[1]
                });
            });
            //init other data.
            var $input = $(this),
                dataKey = $input.attr("data-key"),
                provKey = 1, //default province 北京
                cityKey = 36, //default city 北京
                distKey = 37, //default district 北京东城区
                distStartIndex = 0, //default 0
                cityStartIndex = 0, //default 0
                provStartIndex = 0; //default 0

            if (dataKey != "" && dataKey != undefined) {
                var sArr = dataKey.split("-");
                if (sArr.length == 3) {
                    provKey = sArr[0];
                    cityKey = sArr[1];
                    distKey = sArr[2];

                } else if (sArr.length == 2) { //such as 台湾，香港 and the like.
                    provKey = sArr[0];
                    cityKey = sArr[1];
                }
                startCities = getAddrsArrayById(provKey);
                startDists = getAddrsArrayById(cityKey);
                provStartIndex = getStartIndexByKeyFromStartArr(PROVINCES, provKey);
                cityStartIndex = getStartIndexByKeyFromStartArr(startCities, cityKey);
                distStartIndex = getStartIndexByKeyFromStartArr(startDists, distKey);
            }
            var navArr = [{//3 scrollers, and the title and id will be as follows:
                title: "省",
                id: "scs_items_prov"
            }, {
                title: "市",
                id: "scs_items_city"
            }, {
                title: "区",
                id: "scs_items_dist"
            }];
            SCS.init({
                navArr: navArr,
                onOk: function(selectedKey, selectedValue) {
                    $input.val(selectedValue).attr("data-key", selectedKey);
                }
            });
            var distScroller = new SCS.scrollCascadeSelect({
                el: "#" + navArr[2].id,
                dataArr: startDists,
                startIndex: distStartIndex
            });
            var cityScroller = new SCS.scrollCascadeSelect({
                el: "#" + navArr[1].id,
                dataArr: startCities,
                startIndex: cityStartIndex,
                onChange: function(selectedItem, selectedIndex) {
                    distScroller.render(getAddrsArrayById(selectedItem.key), 0); //re-render distScroller when cityScroller change
                }
            });
            var provScroller = new SCS.scrollCascadeSelect({
                el: "#" + navArr[0].id,
                dataArr: PROVINCES,
                startIndex: provStartIndex,
                onChange: function(selectedItem, selectedIndex) { //re-render both cityScroller and distScroller when provScroller change
                    cityScroller.render(getAddrsArrayById(selectedItem.key), 0);
                    distScroller.render(getAddrsArrayById(cityScroller.getSelectedItem().key), 0);
                }
            });
        });
    });
    //地址联动输入
    $(document).on("click",".scs_btn_ok",function(){
        $(".sp-01").text($("#scs_items_prov .scs_selected").text()+","+$("#scs_items_city .scs_selected").text()+","+$("#scs_items_dist .scs_selected").text());
        $(".sp-02").text($("#scs_items_city .scs_selected").text())
        $(".sp-03").text($("#scs_items_dist .scs_selected").text())
        $("#bank_address").val($(".sp-01").text());
    })
</script>
</html>