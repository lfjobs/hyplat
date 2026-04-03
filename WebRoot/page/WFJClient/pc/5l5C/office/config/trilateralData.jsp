<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>三方</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <%--    <link href="<%=basePath %>css/WFJClient/pc/5l5C/office/style.css" rel="stylesheet">--%>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralData.css">
    <%--    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>--%>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>

</head>
<style>
    .bug-nav li {
        padding: 0 0.5rem;
    }

    .bug-nav2 li {
        width: 18%;
        line-height: 2rem;
        float: left;
        text-align: center;
    }
</style>
<body>
<header>
    <div class="div-ul1">
        <ul class="clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li>
                三方
            </li>
            <li>
            </li>
        </ul>
    </div>
    <div class="div-ul2">
        <ul class="but">
            <li onclick="toAdd()">
                <%--                <img class="tu" src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>--%>
                添加
            </li>
            <li onclick="toDel()">
                <%--                <img class="tu" src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>--%>
                删除
            </li>
            <li onclick="toUpdate()">
                <%--                <img class="tu" src="<%=basePath%>images/ea/office/contract/img_06.png"/>--%>
                修改
            </li>
            <%--            <li onclick="toQuery()">查询</li>--%>
            <li id="openModalBtn" onclick="toQuery1()">查询</li>
            <li onclick="assignment()">分配</li>
            <%--            <li onclick="toDownload()">打印</li>--%>
            <li onclick="window.print()">打印</li>
            <li onclick="importData()">导入</li>
        </ul>
        <ul class="but1">
            <li onclick="toQueryByUploadProject('微信')">微信</li>
            <li onclick="toQueryByUploadProject('抖音')">抖音</li>
            <li onclick="toQueryByUploadProject('快手')">快手</li>
            <li onclick="toQueryByUploadProject('驾校一点通')">驾校一点通</li>
            <li onclick="toQueryByUploadProject('头条')">头条</li>
            <li onclick="toQueryByUploadProject('58同城')">58同城</li>
            <li onclick="toQueryByUploadProject('招聘')">招聘</li>
            <li onclick="toQueryByUploadProject('百度')">百度</li>
        </ul>
    </div>
</header>
<div class="div-ul3">
    <div class="bug-con  main_hide">
        <ul class="tj_con">
            <c:forEach items="${trilateralDataList}" var="entity" varStatus="v">

                <li class="clearfix1<c:if test="${v.index+1 ne fn:length(trilateralDataList)}"> ttsw_last</c:if>">
                    <section id="sec-checked">
                        <aside class="aside_no" checkCasId="${entity.id}">
                            <img class="img_no"
                                 src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                            <img class="img_yes"
                                 src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                        </aside>
                        <h5>公司名称：${entity.companyName}<img
                                src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_img_13.png"/></h5>
                    </section>
                    <div onclick="toDetail('${entity.id}')">
                            <%--                    <p>人员编号：${entity.personnelID}</p>--%>
                        <p>姓名：${entity.name}</p>
                            <%--                    <p>性别：${entity.sex}</p>--%>
                            <%--                    <p>行业：${entity.sector}</p>--%>
                        <p>电话：${entity.phone}</p>
                            <%--                    <p>注册平台名称：${entity.registrationPlatformName}</p>--%>
                            <%--                    <p>注册号：${entity.registrationNo}</p>--%>
                            <%--                    <p>注册账号：${entity.registerAccount}</p>--%>
                            <%--                    <p>上传项目：${entity.uploadProject}</p>--%>
                            <%--                    <p>推广事由：${entity.promotionReasons}</p>--%>
                            <%--                    <p>分配服务：${entity.distributionService}</p>--%>
                            <%--                    <p>服务跟踪：${entity.serviceTracking}</p>--%>
                            <%--                    <p>业务人：${entity.businessPersonnel}</p>--%>
                        <p style="display: none">状态：${entity.state}</p>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>

</div>
<!-- 弹窗 -->
<div id="myModal" class="modal">
    <!-- 查询弹窗内容 -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>请输入查询的公司名称：</p>
        <input style="height: 25px;
    width: 70%;
    border-top: none;
    border-left: none;
    border-right: none;
    border-bottom: 2px solid #c7baba;
    outline: none;
    padding: 5px;" type="text" id="queryInput" placeholder="请输入查询的公司名称">
        <div class="but2">
            <%--            <div id="confirmBtn" onclick="toQuery2()" >查询</div>--%>
            <div id="confirmBtn">查询</div>
            <div id="cancelBtn">取消</div>
        </div>

    </div>
</div>

<script>
    //点击选中
    $(document).on("click", "#sec-checked", function () {
        if ($(this).find("aside").is(".aside_yes")) {
            $(this).find("aside").removeClass().addClass("aside_no");
        } else {
            $(this).find("aside").removeClass().addClass("aside_yes");
        }
    })

    /**
     * 添加数据
     */
    function toAdd() {
        var url = "ea/trilateral/ea_trilateralDataAdd.jspa";
        window.location.href = basePath + url;
    }

    /**
     * 删除数据
     */
    function toDel() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });

        if (id != "" && id != null) {
            var r = confirm("确定删除该数据吗？");
            if (r == true) {
                var url = "ea/trilateral/ea_delTrilateralData.jspa?id=" + id;
                window.location.href = basePath + url;
            }
        } else {
            alert("请选择要删除的数据");
            return false;
        }
    }

    /**
     * 查询
     */
    function toQuery() {
        var url = "ea/trilateral/ea_trilateralDataList.jspa";
        window.location.href = basePath + url;
    }

    /**
     * 根据项目查询
     */
    function toQueryByUploadProject(uploadProject) {
        var url = "ea/trilateral/ea_trilateralDataList.jspa?uploadProject=" + uploadProject;
        window.location.href = basePath + url;
    }

    /**
     * 修改
     */
    function toUpdate() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });

        if (id != "" && id != null) {
            // var r = confirm("确定修改该数据吗？");
            //  if (r == true) {
            // var url = "ea/specs/ea_specsConfigUpdate.jspa?toUpdateSpecsInfo=" + id;
            // window.location.href = basePath + url;
            var url = "ea/trilateral/ea_trilateralDataUpdate.jspa?id=" + id;
            window.location.href = basePath + url;
            // }
        } else {
            alert("请选择要修改的数据");
            return false;
        }

    }

    /**
     *打印
     */
    function toDownload() {

    }

    /**
     * 分配
     */
    function assignment1() {
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });
        if (id != "" && id != null) {
            var url = "ea/trilateral/ea_assignment2.jspa?id=" + id;
            window.location.href = basePath + url;
        } else {
            alert("请选择要分配的数据");
            return false;
        }
    }

    /**
     * 分配
     */
    function assignment() {
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });
        if (id != "" && id != null) {
            var url = "ea/trilateral/ea_assignment3.jspa?id=" + id;
            window.location.href = basePath + url;
        } else {
            alert("请选择要分配的数据");
            return false;
        }
    }

    /**
     * 数据导入
     */
    function importData() {
        var url = "ea/trilateral/ea_importData.jspa";
        window.location.href = basePath + url;
    }

    // /**
    //  * 数据导入
    //  */
    // function importData(){
    //     var url = "ea/trilateral/ea_importData.jspa";
    //     window.location.href = basePath + url;
    // }
    function toDetail(id) {
        var url = "ea/trilateral/ea_toDetailTrilateralData.jspa?id=" + id;
        window.location.href = basePath + url;
    }

    /**
     * 查询
     */
    function toQuery1() {
        // 获取模态窗口
        var modal = document.getElementById("myModal");

// 获取打开模态窗口的按钮，并为按钮添加点击事件监听器
        var btn = document.getElementById("openModalBtn");
        btn.onclick = function () {
            modal.style.display = "block";
        }

// 获取关闭按钮，并为关闭按钮添加点击事件监听器
        var span = document.getElementsByClassName("close")[0];
        span.onclick = function () {
            modal.style.display = "none";
        }

// 获取确认按钮并添加点击事件监听器
        var confirmBtn = document.getElementById("confirmBtn");
        confirmBtn.onclick = function () {
            var queryInput = document.getElementById("queryInput").value;
            if (queryInput) {
                // 使用 fetch API 发送 POST 请求
                var url = "ea/trilateral/ea_toQuery1.jspa?companyName=" + queryInput;
                window.location.href = basePath + url;
                // $.ajax({
                //     url: basePath + 'ea/trilateral/ea_toQuery1.jspa?companyName=' + queryInput,  // 替换为你的后端API地址
                //     type: 'POST',
                //     data: {
                //         "data": queryInput,
                //     },
                //     success: function (data) {
                //         var url = "ea/trilateral/ea_trilateralDataList.jspa";
                //         window.location.href = basePath + url;
                //     },
                //     error: function (xhr, status, error) {
                //         // 错误处理
                //         // console.error("分配失败");
                //         alert("查询失败,请重新查询");
                //     }
                // })
            } else {
                var url = "ea/trilateral/ea_trilateralDataList.jspa";
                window.location.href = basePath + url;
                // alert("请输入查询条件！");
            }
        }

// 获取取消按钮并添加点击事件监听器
        var cancelBtn = document.getElementById("cancelBtn");
        cancelBtn.onclick = function () {
            modal.style.display = "none";
        }

// 点击模态背景时关闭模态
        window.onclick = function (event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    }
</script>

</body>
</html>
