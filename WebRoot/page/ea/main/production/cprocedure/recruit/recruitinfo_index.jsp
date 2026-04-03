<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

    <title>微分金人才招聘网</title>


    <link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/ea/production/my_css.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/ea/production/zhaopin.css"/>
    <script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/bootstrap.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_index.js"></script>

    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
        var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
        var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
        var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
        var lei = "${param.lei}";
        var token = 0;
        var token1 = 0;
        var staffid = "${staffid}";
        var users = "";
        var sccId = "${sccId}";
        var current = "${param.current}";

        var state = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getState():"" %>';


    </script>


</head>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>招聘业务正在完善中，暂不支持。</p>
        <button id="confirmBtn">确认</button>
    </div>
</div>
<body>
<header class="fixed">
    <div class="header">
        <ul>
            <a href="javascript:history.back(-1)">
                <li class="arrar" style="width: 10%;">
                    <div>
                        <img src="<%=basePath%>images/ea/recruit/top_reture.png" alt=""/>
                    </div>
                </li>
            </a>
            <li class="header_c" style="width: 85%;">
                <span class="header_c_tet"><img src="<%=basePath%>images/ea/recruit/search999.png"/><span></span></span>
                <div class="search_k">
                    <input type="search" name="" id="search" placeholder="搜索职位/公司" value=""/>
                </div>
            </li>
        </ul>
    </div>

</header>
<div class="main">
    <nav class="zhao_nav">
        <ul class="center-block">
            <li class="nav_zhao pull-left col-xs-6 text-center current">
                <img src="<%=basePath%>images/resume/img_12.png" alt=""/>
                <p>找工作</p>
            </li>
            <li class="nav_zhao pull-left col-xs-6 text-center">
                <img src="<%=basePath%>images/resume/img_13.png" alt=""/>
                <p>找人才</p>
            </li>
        </ul>
    </nav>
    <div class="content">
        <div class="div-tab">
            <div class="div-01">
                <section class="sec-02">
                    <ul class="clearfix">
                        <li class="active">
                            招聘信息
                        </li>
                        <li class="">
                            简历管理
                        </li>
                    </ul>
                </section>
                <section class="sec-03">
                    <ul class="clearfix">
                        <li class="active">
                            公司抢人才
                        </li>
                        <li class="">
                            职位管理
                        </li>
                    </ul>
                </section>
            </div>

            <div class="div-num">
                <div class="div-02">
                    <section class="sec-07">
                        <ul>
                            <li class="clearfix" id="manger">
                                <div>
                                    <img class="img-29" src="<%=basePath%>images/resume/img_29.png">
                                </div>
                                <p>
                                    简历管理
                                </p>
                            </li>
                            <li class="clearfix" id="record">
                                <div>
                                    <img class="img-30" src="<%=basePath%>css/ea/production/zp_per02.png">
                                </div>
                                <p>
                                    我的投递记录
                                </p>
                            </li>
                            <li class="clearfix" id="Invitation">
                                <div>
                                    <img class="img-30" src="<%=basePath%>images/resume/img_30.png">
                                </div>
                                <p>
                                    我的职位邀请
                                </p>
                            </li>
                            <li class="clearfix" id="postion">
                                <div>
                                    <img class="img-31" src="<%=basePath%>images/resume/img_31.png">
                                </div>
                                <p>
                                    我的职位关注
                                </p>
                            </li>
                            <li class="clearfix" id="myexam">
                                <div>
                                    <img class="img-31" src="<%=basePath%>images/ea/office/contract/selectp/exam.png"
                                         style="width:1rem;">
                                </div>
                                <p>
                                    我的考试
                                </p>
                            </li>
                        </ul>
                    </section>
                </div>
                <div class="div-03">
                    <section class="sec-05">
                        <ul>
                            <li class="clearfix" id="publish">
                                <div>
                                    <img class="img-22" src="<%=basePath%>images/resume/img_22.png">
                                </div>
                                <p>
                                    职位发布
                                </p>
                            </li>
                            <li class="clearfix" id="positionMange">
                                <div>
                                    <img class="img-23" src="<%=basePath%>images/resume/img_23.png">
                                </div>
                                <p>
                                    职位管理
                                </p>
                            </li>
                            <li class="clearfix" id="resume">
                                <div>
                                    <img class="img-24" src="<%=basePath%>images/resume//img_24.png">
                                </div>
                                <p>
                                    简历管理
                                </p>
                            </li>
                            <li class="clearfix" id="collect">
                                <div>
                                    <img class="img-25" src="<%=basePath%>images/resume/img_25.png">
                                </div>
                                <p>
                                    简历收藏
                                </p>
                            </li>
                        </ul>
                    </section>
                </div>
            </div>


        </div>
    </div>


    <div class="zhao_main">
        <ul class="zhao_main_1 com">
            <c:if test='${param.lei=="gs"}'>
                <c:forEach items="${pageForm.list}" var="item" varStatus="status">
                    <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
                        <li class="last" id="${item[5]}">
                    </c:if>
                    <c:if test="${fn:length(pageForm.list)-1 ne status.index}">
                        <li id="${item[5]}">
                    </c:if>
                    <span style="display:none;" class="riId">${item[5]}</span>
                    <span style="display:none;" class="position">${item[1]}</span>
                    <div class="zhao_main_lis_left pull-left text-center img-responsive gs">
                        <div class="img_wai">
                            <c:if test='${item[0] ne null&&item[0] ne ""}'>
                                <img src="<%=basePath%>${item[0]}"/>
                            </c:if>
                            <c:if test='${item[0] eq null||item[0] eq ""}'>
                                <img src="<%=basePath%>images/ea/recruit/gongsi_10.png"/>
                            </c:if>

                        </div>
                    </div>
                    <div class="zhao_main_lis_center pull-left gs">
                        <h4>${item[1]}</h4>
                        <p class="comname">${item[2]}</p>

                        <div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt=""/>${item[3]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[4] }
					</span>
                        </div>
                    </div>
                    <div class="pull-left zhao_main_lis_right">
                        <div class="text_wai">
                            <div class="text_wai">
                                <c:if test='${item[6] eq "0"}'>
                                    <div class="tou">投</div>
                                </c:if>
                                <c:if test='${item[6] ne "0"}'>
                                    <div class="yitou">已投</div>
                                </c:if>

                            </div>

                        </div>

                    </div>
                    </li>
                </c:forEach>
            </c:if>


        </ul>
        <ul class="dN person">
            <c:if test='${param.lei=="gr"}'>
                <c:forEach items="${pageForm.list}" var="item" varStatus="status">
                    <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
                        <li class="last" id="${item[0]}">
                    </c:if>
                    <c:if test="${fn:length(pageForm.list)-1 ne status.index}">
                        <li id="${item[0]}">
                    </c:if>
                    <span style="display:none;" class="resumeID">${item[0]}</span>
                    <span style="display:none;" class="position">${item[1]}</span>
                    <div class="zhao_main_lis_left pull-left text-center img-responsive gr">
                        <div class="img_wai">
                            <c:if test='${item[5] eq null||item[5] eq "" }'>
                                <img src="<%=basePath%>images/ea/production/head2x.png"/>
                            </c:if>
                            <c:if test='${item[5] ne null&&item[5] ne "" }'>
                                <img src="<%=basePath%>${item[5]}"/>
                            </c:if>
                        </div>
                    </div>
                    <div class="zhao_main_lis_center pull-left gr">
                        <h4>${item[1]}</h4>
                        <p>${item[2]}</p>
                        <div>
					<span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_13.png" alt=""/>${item[3]}
					</span><span class="yaoqiu">
						<img class="little_img" src="<%=basePath%>images/ea/recruit/ico_15.png"/>${item[6]}
					</span>
                        </div>
                    </div>
                    <div class="pull-left zhao_main_lis_right">
                        <div class="text_wai">
                            <c:if test='${item[7] eq "0"}'>
                                <div class="qiang">抢</div>
                            </c:if>
                            <c:if test='${item[7] ne "0"}'>
                                <div class="yiqiang">已抢</div>
                            </c:if>

                        </div>

                    </div>

                    </li>
                </c:forEach>
            </c:if>

        </ul>
        <div class="tan_kuang tan1 dN">
            <div class="zhaopin_kuang">
                <img src="<%=basePath%>images/ea/recruit/zhao_03.png" alt=""/>
                <span>
				<span class="again">
					继续投递简历
				</span>
				
			</span>
            </div>
        </div>
        <div class="tan_kuang tan2 dN">
            <div class="zhaopin_kuang1">
                <img src="<%=basePath%>images/ea/recruit/zhao_04.png" alt=""/>
                <span>
				<span class="again">
					继续抢人才
				</span>
				
			</span>
            </div>
        </div>
    </div>
</div>

<div class="jianli_tan_mo dN tan_mo">
    <div class="jianli_tan">
        <p>确认是否邀请</p>
        <p style="font-weight:bold;">邀请一位人才会消耗</br>
            100微分金金币(1元)</p>
        <div>
            <div class="no">取消</div>
            <div class="yue">确认</div>
        </div>
    </div>
</div>


<div class="jianli_tan_mo dN tiptan">
    <div class="jianli_tan">
        <p class="tiptitle">温馨提示</p>
        <p class="tipcontent" style="font-weight:bold;"></p>
        <div>
            <div class="tipcan">取消</div>
            <div class="tipconfirm" style="color:#FF6600;">确认</div>
        </div>
    </div>
</div>


</body>
<!-- ... existing code ... -->
<script>
	// 获取弹框元素
	var modal = document.getElementById('myModal');
	// 获取关闭按钮元素
	var closeBtn = document.getElementsByClassName('close')[0];
	// 获取确认按钮元素
	var confirmBtn = document.getElementById('confirmBtn');

	// 显示弹框
	function showModal() {
        if (typeof Android !=='undefined'){
            modal.style.display = "block";
        }else {
            modal.style.display ="none"
        }
	}

	// 点击关闭按钮时关闭弹框
	closeBtn.onclick = function () {
		window.history.back();
	}

	// 点击确认按钮时关闭弹框
	confirmBtn.onclick = function () {
		window.history.back();
	}

	// 点击弹框外部区域时关闭弹框
	window.onclick = function (event) {
		if (event.target === modal) {
			window.history.back();
		}
	}

	// 示例：页面加载完成后显示弹框
	window.onload = function () {
		showModal();
	}
</script>
<!-- ... existing code ... -->
<!-- ... existing code ... -->
<style>
	/* 弹框背景 */
	.modal {
		display: none;
		position: fixed;
		z-index: 1;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: auto;
		background-color: rgba(0, 0, 0, 0.4);
	}

	/* 弹框内容 */
	.modal-content {
		background-color: #fefefe;
		margin: 15% auto;
		padding: 20px;
		border: 1px solid #888;
		width: 30%;
		text-align: center;
	}

	/* 关闭按钮 */
	.close {
		color: #aaa;
		float: right;
		font-size: 28px;
		font-weight: bold;
	}

	.close:hover,
	.close:focus {
		color: black;
		text-decoration: none;
		cursor: pointer;
	}

	/* 确认按钮 */
	#confirmBtn {
		background-color: #4CAF50;
		color: white;
		padding: 10px 20px;
		border: none;
		cursor: pointer;
	}

	#confirmBtn:hover {
		background-color: #45a049;
	}
</style>
</html>