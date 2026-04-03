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
    <title>单位规格</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/specsConfig.css">
    <script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>

    <style>
        .bug-nav li {
            padding: 0 0.5rem;
        }

        .bug-nav2 {
            overflow: hidden;
            background-color: #f2f2f2;
            color: #222;
            font-size: 0.5rem;
            padding: 0 0;
        }

        .bug-nav2 li {
            width: 18%;
            line-height: 2rem;
            float: left;
            text-align: center;
        }
    </style>
</head>

<style>
    table {
        border-collapse: collapse;
        /*border: 2px solid rgb(140 140 140);*/
        font-family: sans-serif;
        font-size: 1rem;
        letter-spacing: 1px;
        width: 100%;
        text-align: center;
    }

    caption {
        caption-side: bottom;
        padding: 10px;
        font-weight: bold;
    }

    thead,
    tfoot {
        background-color: rgb(228 240 245);
    }

    th,
    td {
        /*border: 1px solid rgb(160 160 160);*/
        padding: 8px 10px;
        text-align: center;
        vertical-align: center;
    }

    td:last-of-type {
        text-align: center;
    }

    tbody > tr:nth-of-type(even) {
        background-color: rgb(237 238 242);
    }

    aside.aside_yes .img_no {
        display: none;
    }

    aside.aside_yes .img_yes {
        display: block;
    }

    aside.aside_no .img_yes {
        display: none;
    }

    aside.aside_no .img_no {
        display: block;
    }
    .div-tingyong {
        display: none;
        position: fixed;
        left: 0;
        top: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, .4)
    }
    .div-tingyong .box {
        width: 21rem;
        margin: 30vh auto 0 auto
    }

    .div-tingyong .box>p {
        border-top-left-radius: .3rem;
        border-top-right-radius: .3rem;
        background-color: #f74c32;
        height: 2.5rem;
        line-height: 2.5rem;
        font-size: 1rem;
        color: #fefefe;
        text-align: center;
        position: relative
    }

    .div-tingyong .box>p img {
        position: absolute;
        width: 1.5rem;
        top: -8%;
        right: -2%
    }

    .div-tingyong .box .div-box p {
        background-color: #fff;
        height: 5rem;
        line-height: 5rem;
        font-size: 1rem;
        color: #222;
        text-align: center;
        border-bottom: 0.025rem solid #eee;
    }

    .div-tingyong .box .div-box div p {
        background-color: #fff;
        width: 50%;
        height: 3rem;
        line-height: 3rem;
        font-size: 1rem;
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
    /*img {*/
    /*    width: 1rem;*/
    /*}*/
</style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
</script>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            规格配置
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container">

    <div class="butt">
        <ul class="actions">
            <li onclick="toAdd();">
                <p class="draft"><img src="<%=basePath%>js/jqModal/css/images_blue/add.png"/>添加</p>
            </li>
            <li onclick="delCostSheet();">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>
            <li onclick="toUpdate();">
                <p class="edit"><img src="<%=basePath%>images/ea/office/contract/img_06.png"/>修改</p>
            </li>
        </ul>
    </div>
    <div>
        <div class="table1">
            <table>
                <!-- 表头 -->
                <thead>
                <tr>
                    <th scope="col"></th>
                    <%--                <th scope="col">序号</th>--%>
                    <th scope="col">单位</th>
                    <th scope="col">规格</th>
                </tr>
                </thead>
                <!-- 主体 -->
                <tbody class="tbody1">
                <c:forEach items="${specsList}" var="entity" varStatus="v">
                    <tr>
                        <th>
                            <section id="sec-checked" class="sec-checked" style="user-select: none">
                                <aside class="aside_no" checkCasId="${entity.specs},${entity.specsCode}">
                                    <img class="img_no"
                                         src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_07.png"/>
                                    <img class="img_yes"
                                         src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_10.png"/>
                                        <%--                        <img class="img_no_sel"--%>
                                        <%--                             src="<%=basePath %>images/scMobile/payBudget/budgetList/wupinguanli_img_20.png"/>--%>
                                </aside>
                            </section>
                        </th>
                            <%--            <th scope="row">${v.index +1}</th>--%>
                        <td>${entity.specs}</td>
                        <td>${entity.specsType}</td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
        </div>
    </div>
</div>
<script>
    //配置的添加页面
    function toAdd() {
        var url = "ea/specs/ea_specsConfigAdd.jspa";
        window.location.href = basePath + url;
    }


    //点击选中
    $(document).on("click", "#sec-checked", function () {
        if ($(this).find("aside").is(".aside_yes")) {
            $(this).find("aside").removeClass().addClass("aside_no");
        } else {
            $(this).find("aside").removeClass().addClass("aside_yes");
        }
    })

    //删除预算单
    function delCostSheet() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });

        if (id != "" && id != null) {
            var r = confirm("确定删除该数据吗？");
            if (r == true) {
                var url = "ea/specs/ea_delSpecsConfig.jspa?delSpecsInfo=" + id;
                window.location.href = basePath + url;
            }
        } else {
            alert("请选择要删除的数据");
            return false;
        }
    }


    // 修改
    function toUpdate() {
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function () {
            id = $(this).attr("checkCasId");
        });

        if (id != "" && id != null) {
             // var r = confirm("确定修改该数据吗？");
             //  if (r == true) {
            var url = "ea/specs/ea_specsConfigUpdate.jspa?toUpdateSpecsInfo=" + id;
            window.location.href = basePath + url;
            // }
        } else {
            alert("请选择要修改的数据");
            return false;
        }
    }



</script>
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>
</body>

</html>
