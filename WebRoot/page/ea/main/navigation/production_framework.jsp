<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
    <title>生产框架</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/production/bootstrap.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/production/style.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/javascript.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/bootstrap.js"></script>
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var goodsName="物品设计";
    	var fiveClear="";
    </script>
    <style type="text/css">
    	.tab_top .div1{cursor:pointer;}
    </style>
    <!-- 引入下面两个库让 IE8 支持 HTML5 元素 -->
    <!-- 警告: Respond.js 通过 file:// 浏览的时候不能正常工作！-->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="header">
    <div><h5>5L5C系统管理平台</h5></div>
    <div>
        <input type="text" placeholder="搜索人事、工作、附件">
        <img src="<%=basePath%>images/ea/production/yun.png">
    </div>
    <div class="clearfix"></div>
</div>
<div class="content">
    <div class="con-left">
        <div class="icon">
            <div class="con-left_div1">
                <p>周刚</p>
            </div>
            <h5>周刚</h5>
        </div>
        <div class="application">
            <!--<h4><img src="<%=basePath%>images/ea/production/app1.png" alt="">应用代码管理</h4>-->
            <ul>
                <li class="active"><img id="ret" src="<%=basePath%>images/ea/production/return.png" alt=""><img src="<%=basePath%>images/ea/production/app1.png" alt=""><span>物品设计 - 组织机构<span></li>
                <li id="organizational"><img src="<%=basePath%>images/ea/production/app1.png" alt=""><span></span></li>
            </ul>
        </div>
        <ul class="con-left_txt">
            <li><a href="#;"> <img src="<%=basePath%>images/ea/production/upgrade.png" alt=""><p>升级说明</p></a></li>
            <li><a href="#;"> <img src="<%=basePath%>images/ea/production/Setting.png" alt=""><p>个人设置</p></a></li>
            <li><a href="#;"> <img src="<%=basePath%>images/ea/production/quit.png" alt=""><p>退出登录</p></a></li>
        </ul>
    </div>
    <div class="con-right">
        <div class="tab_top" style="overflow: hidden;margin-left: 20px;">
            <div class="div1">
                <p class="p1"></p>
                <span class="sp1">组织结构</span>
                <p class="p2"></p>
            </div>
            <div class="div1 div2" id="road" style="display: none;">
                <p class="p1"></p>
                <span class="sp1"></span>
                <p class="p2"></p>
            </div>
        </div>

        <div class="tab">
            <!--项目设计-->
            <div class="tabHead layer2 sheji">
                <ul class="sheji_">
                	<li   class="top-level distance active" tagging="物品设计"  name="sing-y" record="goods">
                        <img src="<%=basePath%>images/ea/production/laery2_1.png">
                        <p>物品设计</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/设计@2x.png">
                        <p>单项目产品设计</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/laery2_2.png">
                        <p>组装项目产品设计</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="layer2_">
                    <div class="tabItem ">
                   		<ul id="myTab" class="nav nav-tabs">
                             <li class="left-click distance active selected" id="wupinsheji" tagging="物品设计" src="ea/gooddesign/ea_getGoodDesignList.jspa?1=1" >
                             <a  data-toggle="tab">
                             	<img src="<%=basePath%>images/ea/production/物品设计@2x.png" alt=""  style="display: inline;">物品设计
                             </a>
                        </li>
                        </ul>
                        <ul id="myTab1" class="nav nav-tabs">
                            <li class="danxiangmu-1 active" id="danxiangmu-1" tagging="单产品设计" src="ea/prodesign/ea_getProductDesignList.jspa?type=01&category=00">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangmu1.png" alt="" class="danxiangmu1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/danxiangmu1_.png" alt="" class="danxiangmu1_ danxiangm_" style="display: inline;">
                                    单项目产品设计
                                </a>
                            </li>
                            <li class="danxiangmu-2" id="danxiangmu-2" tagging="单产品预算" src="ea/budgetplan/ea_getBudgetPlanList.jspa?type=01&category=00">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangmu2.png" alt="" class="danxiangmu2">
                                    <img src="<%=basePath%>images/ea/production/danxiangmu2_.png" alt="" class="danxiangmu2_ danxiangm_">
                                    单项目产品预算
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab2" class="nav nav-tabs" >
                            <li class="danxiangmu-1 active" id="danxiangmu-5"  tagging="组装产品设计" src="ea/prodesign/ea_getProductDesignList.jspa?type=01&category=01">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/产品设计@2x.png" alt="" class="danxiangmu5 danxiangmu" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/产品设计2@2x.png" alt="" class="danxiangmu5_ danxiangm_ danxiangmu_" style="display: inline;">
                                    组装项目产品设计
                                </a>
                            </li>
                            <li class="danxiangmu-1" id="danxiangmu-3" tagging="组装产品流程" src="ea/prodesign/ea_getProductPage.jspa?category=01">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangm3_.png" alt="" class="danxiangmu3 danxiangmu_" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/danxiangm3.png" alt="" class="danxiangmu3_ danxiangm_ danxiangmu" style="display: inline;">
                                    组装项目产品流程设计
                                </a>
                            </li>
                            <li class="danxiangmu-2" id="danxiangmu-4" tagging="项目预算"  src="ea/budgetplan/ea_getBudgetPlanList.jspa?type=01&category=01">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangm4.png" alt="" class="danxiangmu4 danxiangmu">
                                    <img src="<%=basePath%>images/ea/production/danxiangm4_.png" alt="" class="danxiangmu4_ danxiangm_ danxiangmu_">
                                    项目产品预算
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!--项目立项-->
            <div class="tabHead layer2 lixiang" style="display: none;">
                <ul class="lixiang_">
                    <li class="active">
                        <img src="<%=basePath%>images/ea/production/project-img1.png">
                        <p>单项目产品立项审批</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/project-img2.png">
                        <p>组装产品立项审批</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="layer2_">
                    <div class="tabItem ">
                        <ul id="myTab" class="nav nav-tabs">
                            <li id="design1" class="active" tagging="单产品审批（未审批）" src="ea/bsimtest/ea_getBsimtestList.jspa?type=00&status=00&category=00">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design1.png" alt="" class="design1" style="display: none">
                                    <img src="<%=basePath%>images/ea/production/design1_.png" alt="" class="design1_ danxiangm_" style="display: inline;">
                                    单项目产品立项审批（未审批）
                                </a>
                            </li>
                            <li id="design2" tagging="单产品审批（通过）" src="ea/bsimtest/ea_getBsimtestList.jspa?status=02&category=00" >
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design2.png" alt="" class="design2">
                                    <img src="<%=basePath%>images/ea/production/design2_.png" alt="" class="design2_ danxiangm_">
                                    单项目产品立项审批（通过）
                                </a>
                            </li>
                            <li id="design3" tagging="单产品审批（未通过）" src="ea/bsimtest/ea_getBsimtestList.jspa?status=03&category=00">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design3.png" alt="" class="design3">
                                    <img src="<%=basePath%>images/ea/production/design3_.png" alt="" class="design3_ danxiangm_">
                                    单项目产品立项审批（未通过）
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab2" class="nav nav-tabs" >
                            <li id="design1_" class="active" tagging="组装产品审批（未审批）" src="ea/bsimtest/ea_getBsimtestList.jspa?type=00&status=00&category=01">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design1.png" alt="" class="design1-2" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/design1_.png" alt="" class="design1-2_ danxiangm_"  style="display:inline;">
                                  组装产品立项审批（未审批）
                                </a>
                            </li>
                            <li id="design2_" tagging="组装产品审批(通过)" src="ea/bsimtest/ea_getBsimtestList.jspa?status=02&category=01" >
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design2.png" alt="" class="design2-2">
                                    <img src="<%=basePath%>images/ea/production/design2_.png" alt="" class="design2-2_ danxiangm_">
                                   组装产品立项审批（未通过）
                                </a>
                            </li>
                            <li id="design3_" tagging="组装产品审批(未通过)" src="ea/bsimtest/ea_getBsimtestList.jspa?status=03&category=01">
                                <a href="#;" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design3.png" alt="" class="design3-2">
                                    <img src="<%=basePath%>images/ea/production/design3_.png" alt="" class="design3-2_ danxiangm_">
                                    组装产品立项审批（未合格）
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!--项目生产-->
            <div class="tabHead layer2 shengchan" style="display: none;">
                <ul class="shengchan_">
                    <li class="active">
                        <img src="<%=basePath%>images/ea/production/production-img1.png">
                        <p>单项目产品生产</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/production-img2.png">
                        <p>组装项目产品生产</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/production-img3.png">
                        <p>项目生产跟踪</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="layer2_ layer2_1">
                    <div class="tabItem ">
                        <ul id="myTab" class="nav nav-tabs">
                            <li id="ded1" class="active">
                                <a href="#design3-8" data-toggle="tab" id="only_design">
                                    <img src="<%=basePath%>images/ea/production/danxiangm1.png" alt="" class="ded1 danxiangm" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/danxiangm1_.png" alt="" class="ded1_ danxiangm_" style="display: inline">
                                    单项目产品订单设计
                                </a>
                            </li>
                            <li id="ded2">
                                <a href="#design3-9" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangm2.png" alt="" class="ded2">
                                    <img src="<%=basePath%>images/ea/production/danxiangm2_.png" alt="" class="ded2_ danxiangm_">
                                    单项目产品计划生产
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab2" class="nav nav-tabs">
                            <li id="ded2-1" class="active">
                                <a href="#design3-8_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangm1.png" alt="" class="ded2-1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/danxiangm1_.png" alt="" class="ded2-1_ danxiangm_" style="display: inline">
                                    组装项目产品订单生产
                                </a>
                            </li>
                            <li id="ded2-2">
                                <a href="#design3-9_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/danxiangm2.png" alt="" class="ded2-2">
                                    <img src="<%=basePath%>images/ea/production/danxiangm2_.png" alt="" class="ded2-2_ danxiangm_">
                                    组装项目产品计划生产
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab3" class="nav nav-tabs">
                            <li id="ded3-1" class="active" src="ea/ptrack/ea_getPtrackList.jspa?type=00">
                                <a href="#design3-8_3" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/prod1.png" alt="" class="ded3-1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/prod1_.png" alt="" class="ded3-1_ danxiangm_" style="display: inline">
                                    日志系统提示
                                </a>
                            </li>
                            <li id="ded3-2">
                                <a href="#design3-9_3" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/prod2.png" alt="" class="ded3-2">
                                    <img src="<%=basePath%>images/ea/production/prod2_.png" alt="" class="ded3-2_ danxiangm_">
                                    项目列表跟踪
                                </a>
                            </li>
                            <li id="ded3-3">
                                <a href="#design3-10_3" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/prod3.png" alt="" class="ded3-3">
                                    <img src="<%=basePath%>images/ea/production/prod3_.png" alt="" class="ded3-3_ danxiangm_">
                                    项目图标提示
                                </a>
                            </li>
                            <li  id="ded3-4">
                                <a href="#design3-11_3" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/prod4.png" alt="" class="ded3-4">
                                    <img src="<%=basePath%>images/ea/production/prod4_.png" alt="" class="ded3-4_ danxiangm_">
                                    项目数据评估
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!--项目成果-->
            <div class="tabHead layer2 chengguo" style="display: none;">
                <ul class="chengguo_">
                    <li class="active">
                        <img src="<%=basePath%>images/ea/production/success-img1.png">
                        <p>单项目产品成果</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/success-img2.png">
                        <p>组装项目产品成果</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="layer2_">
                    <div class="tabItem ">
                        <ul id="myTab" class="nav nav-tabs">
                            <li id="cguo1" class="active" tagging="单产品入库" src="ea/production/ea_getAccessToProductInformation.jspa?category=00">
                                <a href="#design5-8" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo1.png" alt="" class="cguo1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/cguo1_.png" alt="" class="cguo1_ danxiangm_" style="display: inline;">
                                    单产品产品入库
                                </a>
                            </li>
                            <li id="cguo2" tagging="单产品出库" src="ea/finished/ea_getHomePageData.jspa?category=00">
                                <a href="#design5-9" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo2.png" alt="" class="cguo2">
                                    <img src="<%=basePath%>images/ea/production/cguo2_.png" alt="" class="cguo2_ danxiangm_">
                                    单产品成品出库
                                </a>
                            </li>
                            <li id="cguo3" tagging="单产品库存"  src="ea/resultInv/ea_getResultInvList.jspa?category=00">
                                <a href="#design5-10" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo3.png" alt="" class="cguo3">
                                    <img src="<%=basePath%>images/ea/production/cguo3_.png" alt="" class="cguo3_ danxiangm_">
                                    单产品库存管理
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab2" class="nav nav-tabs" >
                            <li id="cguo1-1" class="active" tagging="组装产品入库" src="ea/production/ea_getAccessToProductInformation.jspa?category=01">
                                <a href="#design5-8_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo1.png" alt="" class="cguo1-1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/cguo1_.png" alt="" class="cguo1-1_ danxiangm_" style="display: inline;">
                                    组装产品产品入库
                                </a>
                            </li>
                            <li id="cguo1-2" tagging="组装产品出库" src="ea/finished/ea_getHomePageData.jspa?category=01">
                                <a href="#design5-9_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo2.png" alt="" class="cguo1-2">
                                    <img src="<%=basePath%>images/ea/production/cguo2_.png" alt="" class="cguo1-2_ danxiangm_">
                                    组装产品成品出库
                                </a>
                            </li>
                            <li id="cguo1-3" tagging="组装产品库存" src="ea/resultInv/ea_getResultInvList.jspa?category=01">
                                <a href="#design5-10_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/cguo3.png" alt="" class="cguo1-3">
                                    <img src="<%=basePath%>images/ea/production/cguo3_.png" alt="" class="cguo1-3_ danxiangm_">
                                    组装产品库存管理
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!--项目验收-->
            <div class="tabHead layer2 yanshou" style="display: none;">
                <ul class="yanshou_">
                    <li class="active">
                        <img src="<%=basePath%>images/ea/production/check-img1.png">
                        <p>单项目产品验收</p>
                    </li>
                    <li>
                        <img src="<%=basePath%>images/ea/production/check-img2.png">
                        <p>组装项目产品验收</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <div class="layer2_">
                    <div class="tabItem ">
                        <ul id="myTab" class="nav nav-tabs">
                            <li id="dyans1" class="active" tagging="单产品验收（未验收）" src="ea/inspection/ea_inspectionList.jspa?type=01&category=00" > 
                                <a href="#design4-8" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/yanshou1.png" alt="" class="dyans1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/yanshou1_.png" alt="" class="dyans1_ danxiangm_" style="display: inline;">
                                    单项目验收
                                </a>
                            </li>
                            <li id="dyans2" tagging="单产品验收(合格)" src="ea/inspection/ea_inspectionList.jspa?type=02&category=00">
                                <a href="#design4-9" data-toggle="tab" >
                                    <img src="<%=basePath%>images/ea/production/design2.png" alt="" class="dyans2">
                                    <img src="<%=basePath%>images/ea/production/design2_.png" alt="" class="dyans2_ danxiangm_">
                                    单项目验收（合格）
                                </a>
                            </li>
                            <li id="dyans3" tagging="单产品验收(不合格)" src="ea/inspection/ea_inspectionList.jspa?type=03&category=00">
                                <a href="#design4-10" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design3.png" alt="" class="dyans3">
                                    <img src="<%=basePath%>images/ea/production/design3_.png" alt="" class="dyans3_ danxiangm_">
                                    单项目验收（不合格）
                                </a>
                            </li>
                            <li id="dyans4" tagging="单产品合格率" src="ea/dcheck/ea_getDCheckyieldList.jspa?type=00&show=00&category=00">
                                <a href="#design4-11" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/dyans4.png" alt="" class="dyans4">
                                    <img src="<%=basePath%>images/ea/production/dyans4_.png" alt="" class="dyans4_ danxiangm_">
                                    单项目合格率
                                </a>
                            </li>
                            <li id="dyans5">
                                <a href="#design4-12" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/dyans5.png" alt="" class="dyans5">
                                    <img src="<%=basePath%>images/ea/production/dyans5_.png" alt="" class="dyans5_ danxiangm_">
                                    单项目排行榜
                                </a>
                            </li>
                        </ul>
                        <ul id="myTab2" class="nav nav-tabs">
                            <li id="zyans1" class="active" tagging="组装产品验收（未验收）" src="ea/inspection/ea_inspectionList.jspa?type=01&category=01" >
                                <a href="#design4-8_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/yanshou1.png" alt="" class="zyans1" style="display: none;">
                                    <img src="<%=basePath%>images/ea/production/yanshou1_.png" alt="" class="zyans1_ danxiangm_" style="display: inline">
                                    组装项目验收
                                </a>
                            </li>
                            <li id="zyans2" tagging="组装产品验收(合格)" src="ea/inspection/ea_inspectionList.jspa?type=02&category=01">
                                <a href="#design4-9_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design2.png" alt="" class="zyans2">
                                    <img src="<%=basePath%>images/ea/production/design2_.png" alt="" class="zyans2_ danxiangm_">
                                    组装项目验收（合格）
                                </a>
                            </li>
                            <li id="zyans3" tagging="组装产品验收(不合格)" src="ea/inspection/ea_inspectionList.jspa?type=03&category=01">
                                <a href="#design4-10_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/design3.png" alt="" class="zyans3">
                                    <img src="<%=basePath%>images/ea/production/design3_.png" alt="" class="zyans3_ danxiangm_">
                                    组装项目验收（不合格）
                                </a>
                            </li>
                            <li id="zyans4" tagging="组装产品合格率" src="ea/dcheck/ea_getDCheckyieldList.jspa?type=01&show=00&category=01">
                                <a href="#design4-11_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/dyans4.png" alt="" class="zyans4">
                                    <img src="<%=basePath%>images/ea/production/dyans4_.png" alt="" class="zyans4_ danxiangm_">
                                    组装项目合格率
                                </a>
                            </li>
                            <li id="zyans5">
                                <a href="#design4-12_" data-toggle="tab">
                                    <img src="<%=basePath%>images/ea/production/dyans5.png" alt="" class="zyans5">
                                    <img src="<%=basePath%>images/ea/production/dyans5_.png" alt="" class="zyans5_ danxiangm_">
                                    组装项目排行榜
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="layer2_ tab_div">
            <div class="tabHead layer4 xiangmupinggu2 shengchan_div">
                <ul class="img-1 dede1" name="eject_1">
                    <li  tagging="单产品订单管理" src="ea/assembly/ea_getProductOrderList.jspa?category=00">
                    	<img src="<%=basePath%>images/ea/production/layer4_1.png" alt="">单项目产品订单管理
                    </li>
                    <li name="cgfb" class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_2.png" alt="">单项目产品采购预算发布</li>
                    <li name="qrcg" class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_3.png" alt="">单项目产品确认采购（招标竞标）</li>
                    <li name="zbjb" class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_4.png" alt="">单项目产品生产分配（总分配汇总）</li>
                    <li tagging="单产品生产组装" src="ea/assembly/ea_getProductAssemblyList.jspa?type=00&category=00">
                    	<img src="<%=basePath%>images/ea/production/layer4_5.png" alt="">单项目产品生产组装
                    </li>
                    <div class="clearfix"></div>
                </ul>
                <ul class="img-1 dede2" name="eject_2">
                    <li class="active" tagging="单产品设置生产量" src="ea/setpro/ea_listPage.jspa?type=01&category=00">
                    	<img src="<%=basePath%>images/ea/production/layer4_1.png" alt="">单项目产品设置生产量
                    </li>
                    <li name="cgfb"  class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_2.png" alt="">单项目产品采购预算发布</li>
                    <li name="qrcg"  class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_3.png" alt="">单项目产品确认采购（招标竞标）</li>
                    <li name="zbjb"  class="lf_click" ><img src="<%=basePath%>images/ea/production/layer4_4.png" alt="">单项目产品生产分配（总分配汇总）</li>
                    <li  tagging="单产品生产组装" src="ea/assembly/ea_getProductAssemblyList.jspa?type=01&category=00">
                    	<img src="<%=basePath%>images/ea/production/layer4_5.png" alt="">单项目产品生产组装
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
            <div class="tabHead layer4 xiangmupinggu2 shengchan_div1">
                <ul class="img-1" name="eject_3">
                    <li class="active zzcpgl" tagging="组装产品订单管理"  src="ea/assembly/ea_getProductOrderList.jspa?type=00&category=01">
                    	<img src="<%=basePath%>images/ea/production/layer4_1.png"> 组装项目产品订单管理
                    </li>
                    <li name="zbjb"  class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_4.png">组装项目产品生产分配</li>
                    <li  class="zzcpgl"  tagging="组装产品生产组装" src="ea/assembly/ea_getProductAssemblyList.jspa?type=00&category=01">
                    	<img src="<%=basePath%>images/ea/production/layer4_5.png">组装项目产品生产组装
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
            <div class="tabHead layer4 xiangmupinggu2 shengchan_div2">
                <ul class="img-1" name="eject_4">
                    <li class="active zzcpgl" tagging="组装产品设置生产量" src="ea/setpro/ea_listPage.jspa?type=01&category=01">
                    	<img src="<%=basePath%>images/ea/production/layer4_1.png"> 组装项目产品设置生产量
                    </li>
                    <li name="zbjb"  class="lf_click"><img src="<%=basePath%>images/ea/production/layer4_4.png">组装项目产品生产分配</li>
                    <li class="zzcpgl" tagging="组装产品生产组装" src="ea/assembly/ea_getProductAssemblyList.jspa?type=01&category=01">
                    	<img src="<%=basePath%>images/ea/production/layer4_5.png">组装项目产品生产组装
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
            <div class="tabHead layer4 xiangmupinggu2 shengchan_div3">
                <ul class="img-1">
                    <li class="active"><img src="<%=basePath%>images/ea/production/ico-design1.png" alt="">项目数据风险指南</li>
                    <li><img src="<%=basePath%>images/ea/production/ico-design2.png" alt="">人事数据平衡指南</li>
                    <li><img src="<%=basePath%>images/ea/production/ico-design3.png" alt="">办公室数据平衡指南</li>
                    <li><img src="<%=basePath%>images/ea/production/ico-design4.png" alt="">财务数据平衡指南</li>
                    <li><img src="<%=basePath%>images/ea/production/ico-design5.png" alt="">生产数据平衡指南</li>
                    <li><img src="<%=basePath%>images/ea/production/ico-design6.png" alt="">营销数据平衡指南</li>
                    <div class="clearfix"></div>
                </ul>
            </div>
         	<div class="eject_1 eject">
				<div class="tabTitle layer5 tab_d zbjb">
					<ul class="zuzhuang">
						<li class="left-click  distance" tagging="单产品人员分配" src="ea/member/ea_getMemberList.jspa?type=00&category=00"
								name="sing-l">单项目产品人员分配</li>
						<li class="left-click  distance" tagging="单产品场地分配" src="ea/fielddistr/ea_getHomePage.jspa?type=00&category=00"
								name="sing-l">单项目产品场地分配</li>
						<li class="left-click  distance" tagging="单产品设备分配" src="ea/proedpdist/ea_findList.jspa?type=00&category=00"
								name="sing-l">单项目产品设备分配</li>
						<li class="left-click  distance" tagging="单产品班值分配" src="ea/duty/ea_getPutyList.jspa?type=00&category=00"
								name="sing-l">单项目产品班值分配</li>
						<div class="clearfix"></div>
					</ul>
				</div>
					<div class="tabTitle layer5 tab_d qrcg">
						<ul class="zuzhuang">
							<li class="left-click  distance" tagging="单产品比价选标" src="ea/purchasebids/ea_findSelectBidsList.jspa?type=00&category=00"
								name="sing-l">单项目产品比价选标</li>
							<li class="left-click  distance" tagging="单产品验货管理" src="ea/sourcingsto/ea_inspectionList.jspa?type=00&category=00"
								name="sing-l'">单项目产品验货管理</li>
							<li class="left-click  distance" tagging="单产品收货管理" src="ea/sourcingsto/ea_goodsReceiptList.jspa?type=00&category=00"
								name="sing-l">单项目产品收货管理</li>
							<li class="left-click  distance" tagging="单产品入库管理" src="ea/sourcingsto/ea_storageList.jspa?type=00&category=00"
								name="sing-l">单项目产品采购入库管理</li>
							<div class="clearfix"></div>
						</ul>
					</div>
					<div class="tabTitle layer5 tab_d cgfb">
						<ul class="zuzhuang">
							<li class="left-click  distance" tagging="单产品采购申请" src="ea/purchasebids/ea_getPurcBugetSheetList.jspa?type=00&category=00"
								name="sing-l">单项目产品采购预算申请</li>
							<li class="left-click  distance" tagging="单产品采购审批" src="ea/purchasebids/ea_findAuditPurSheetList.jspa?type=00&category=00"
								name="sing-l'">单项目产品采购审批</li>
							<li class="left-click  distance" tagging="单产品采购发布" src="ea/purchasebids/ea_findInviteBidsList.jspa?type=00&category=00"
								name="sing-l">单项目产品采购发布</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</div>
				<div class="eject_2 eject">
				<div class="tabTitle layer5 tab_d zbjb">
					<ul class="zuzhuang">
						<li class="left-click  distance"tagging="单产品人员分配" src="ea/member/ea_getMemberList.jspa?type=01&category=00"
								name="sing-l">单项目产品人员分配</li>
						<li class="left-click  distance" tagging="单产品场地分配" src="ea/fielddistr/ea_getHomePage.jspa?type=01&category=00"
								name="sing-l">单项目产品场地分配</li>
						<li class="left-click  distance" tagging="单产品设备分配" src="ea/proedpdist/ea_findList.jspa?type=01&category=00"
								name="sing-l">单项目产品设备分配</li>
						<li class="left-click  distance" tagging="单产品班值分配" src="ea/duty/ea_getPutyList.jspa?type=01&category=00"
								name="sing-l">单项目产品班值分配</li>
						<div class="clearfix"></div>
					</ul>
				</div>
					<div class="tabTitle layer5 tab_d qrcg">
						<ul class="zuzhuang">
							<li class="left-click  distance" tagging="单产品比价选标" src="ea/purchasebids/ea_findSelectBidsList.jspa?type=01&category=00"
								name="sing-l">单项目产品比价选标</li>
							<li class="left-click  distance" tagging="单产品验货管理" src="ea/sourcingsto/ea_inspectionList.jspa?type=01&category=00"
								name="sing-l'">单项目产品验货管理</li>
							<li class="left-click  distance" tagging="单产品收货管理" src="ea/sourcingsto/ea_goodsReceiptList.jspa?type=01&category=00"
								name="sing-l">单项目产品收货管理</li>
							<li class="left-click  distance" tagging="单产品入库管理" src="ea/sourcingsto/ea_storageList.jspa?type=01&category=00"
								name="sing-l">单项目产品采购入库管理</li>
							<div class="clearfix"></div>
						</ul>
					</div>
					<div class="tabTitle layer5 tab_d cgfb">
						<ul class="zuzhuang">
							<li class="left-click  distance" tagging="单产品采购申请" src="ea/purchasebids/ea_getPurcBugetSheetList.jspa?type=01&category=00"
								name="sing-l">单项目产品采购预算申请</li>
							<li class="left-click  distance" tagging="单产品采购审批" src="ea/purchasebids/ea_findAuditPurSheetList.jspa?type=01&category=00"
								name="sing-l'">单项目产品采购审批</li>
							<li class="left-click  distance" tagging="单产品采购发布" src="ea/purchasebids/ea_findInviteBidsList.jspa?type=01&category=00"
								name="sing-l">单项目产品采购发布</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</div>
				<div class="eject_3 eject">
					<div class="tabTitle layer5 tab_d zbjb">
						<ul class="zuzhuang">
							<li class="left-click  distance" tagging="组装产品人员分配" src="ea/member/ea_getMemberList.jspa?type=00&category=01"
									name="sing-l">组装项目产品人员分配</li>
							<li class="left-click  distance" tagging="组装产品场地分配" src="ea/fielddistr/ea_getHomePage.jspa?type=00&category=01"
									name="sing-l">组装项目产品场地分配</li>
							<li class="left-click  distance" tagging="组装产品设备分配" src="ea/proedpdist/ea_findList.jspa?type=00&category=01"
									name="sing-l">组装项目产品设备分配</li>
							<li class="left-click  distance" tagging="组装产品班值分配" src="ea/duty/ea_getPutyList.jspa?type=00&category=01"
									name="sing-l">组装项目产品班值分配</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</div>
				<div class="eject_4 eject">
					<div class="tabTitle layer5 tab_d zbjb">
						<ul class="zuzhuang">
							<li class="left-click  distance"tagging="组装产品人员分配" src="ea/member/ea_getMemberList.jspa?type=01&category=01"
									name="sing-l">组装项目产品人员分配</li>
							<li class="left-click  distance" tagging="组装产品场地分配" src="ea/fielddistr/ea_getHomePage.jspa?type=01&category=01"
									name="sing-l">组装项目产品场地分配</li>
							<li class="left-click  distance" tagging="组装产品设备分配" src="ea/proedpdist/ea_findList.jspa?type=01&category=01"	
									name="sing-l">组装项目产品设备分配</li>
							<li class="left-click  distance" tagging="组装产品班值分配" src="ea/duty/ea_getPutyList.jspa?type=01&category=01"
									name="sing-l">组装项目产品班值分配</li>
							<div class="clearfix"></div>
						</ul>
					</div>
				</div>
        </div>
        <div class="ifrom1">
            <iframe style="width: 100%;height: 100%;"></iframe>
        </div>
        <div class="tab_foot">
            <div class="tabHead layer1">
                <ul>
                    <li style="color: #FF974B;" id="sheji">
                        <img class="layer1-img1" src="<%=basePath%>images/ea/production/item-design.png">
                        <img class="layer1-img1_" src="<%=basePath%>images/ea/production/item-design_.png">
                        <p>项目设计</p>
                    </li>
                    <li id="lixiang">
                        <img class="layer1-img2" src="<%=basePath%>images/ea/production/project.png">
                        <img class="layer1-img2_" src="<%=basePath%>images/ea/production/project_.png">
                        <p>项目立项</p>
                    </li>
                    <li id="shengchan">
                        <img class="layer1-img3" src="<%=basePath%>images/ea/production/production.png">
                        <img class="layer1-img3_" src="<%=basePath%>images/ea/production/production_.png">
                        <p>项目生产</p>
                    </li>
                    <li id="yanshou">
                        <img class="layer1-img4" src="<%=basePath%>images/ea/production/check.png">
                        <img class="layer1-img4_" src="<%=basePath%>images/ea/production/check_.png">
                        <p>项目验收</p>
                    </li>
                    <li id="chengguo">
                        <img class="layer1-img5" src="<%=basePath%>images/ea/production/success.png">
                        <img class="layer1-img5_" src="<%=basePath%>images/ea/production/success_.png">
                        <p>项目成果</p>
                    </li>
                    <div class="clearfix"></div>
                </ul>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
</div>
<script type="text/javascript">
    $(".avb li").click(function(){
        var asd=$(this).text();
        $("#myTabDrop1").empty().text();
        $("#myTabDrop1").append(asd);
        $("#myTabDrop1_").empty().text();
        $("#myTabDrop1_").append(asd);
        $("#myTabDrop2").empty().text();
        $("#myTabDrop2").append(asd);
        $("#myTabDrop2_").empty().text();
        $("#myTabDrop2_").append(asd);
        $("#myTabDrop3").empty().text();
        $("#myTabDrop3").append(asd);
        $("#myTabDrop3_").empty().text();
        $("#myTabDrop3_").append(asd);
        $("#myTabDrop3_3").empty().text();
        $("#myTabDrop3_3").append(asd);
        $("#myTabDrop4").empty().text();
        $("#myTabDrop4").append(asd);
        $("#myTabDrop4_").empty().text();
        $("#myTabDrop4_").append(asd);
        $("#myTabDrop5").empty().text();
        $("#myTabDrop5").append(asd);
        $("#myTabDrop5_").empty().text();
        $("#myTabDrop5_").append(asd);
    });
</script>
<script>
    $(document).ready(function(){

    })
</script>
</body>
</html>