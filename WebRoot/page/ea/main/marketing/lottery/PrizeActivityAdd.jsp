<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/cropper.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/act_manger.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/cropper.min.js"></script>
    <!--选择日期时间插件 开始-->
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_002.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_004.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_002.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>css/ea/lottery/mobiscroll.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_003.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll_005.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_003.css" rel="stylesheet" type="text/css">
    <!--选择日期时间插件 结束-->
    <!--选择插件 开始-->
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/mobiscroll-select.min.css">
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll-select.min.js"></script>
    <script src="<%=basePath%>js/ea/marketing/lottery/prizeActivityAdd.js"></script>
    <!--选择插件 结束-->
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_004.css" rel="stylesheet" type="text/css">
    <s:if test="flag eq 0">
        <title>抽奖活动添加</title>
    </s:if>
    <s:else>
        <title>签到活动添加</title>
    </s:else>

</head>
<body>
<header class="com_head">
    <a href="<%=basePath%>ea/lottery/ea_prizeActivityList.jspa?flag=${flag}&model.companyId=${model.companyId}" class="back"></a>
    <s:if test="flag eq 0">
        <h1>抽奖活动添加</h1>
    </s:if>
    <s:else>
        <h1>签到活动添加</h1>
    </s:else>
</header>
<form id="ActivityForm" method="post" enctype="multipart/form-data">
    <input type="hidden" id="activityKey" name="model.activityKey" value="${model.activityKey}"/>
    <input type="hidden" id="activityId" name="model.activityId" value="${model.activityId}"/>
    <input type="hidden" id="activityType" name="model.activityType" value="${flag}">
    <input type="hidden" id="activityRange" name="model.activityRange" value="${model.activityRange}">
    <input type="hidden" id="companyId" name="model.companyId" value="${model.companyId}">
    <div class="wrap_page">
        <div class="cj_wrap">
            <div class="cj_mane cj_box">
                <div class="cj_tit">
                    <i class="tit_ico green"></i><span>活动名称</span>
                </div>
                <div class="cj_inp_box">
                    <input type="text" class="cj_inp_both" placeholder="请输入活动名称" name="model.activityName" value="${model.activityName}">
                </div>
            </div>
            <div class="act_main_wrap cj_box clearfix">
                <div class="cj_boxL">
                    <i class="tit_ico red"></i>
                    <span>活动主图</span>
                </div>
                <div class="act_mainimg">
                    <img src="<c:if test="${sign =='edit'}"><%=basePath%></c:if>${model.activityImg!=''? model.activityImg :''}" alt="" class="act_src" id="act_img">
                    <input type="file" class="act_upinp" id="act_upinp" accept="image/*">
                    <s:if test="sign eq 'edit'"><input type="hidden" id="activityImg" value="${model.activityImg}"></s:if>
                </div>
            </div>
            <div class="cj_box cj_time">
                <div class="cj_tit">
                    <i class="tit_ico yellow"></i><span>活动时间</span>
                </div>
                <div class="select_time_wrap clearfix">
                    <div class="time_L">
                        开始时间：
                    </div>
                    <div class="time_inp_wrap">
                        <input type="text" class="cj_time_inp beginTime" id="prize_begin_time" name="model.strStartingTime"
                               value="<fmt:formatDate value='${model.startingTime}' pattern='yyyy-MM-dd HH:mm'/>"
                               readonly placeholder="请选择开始时间">
                    </div>

                </div>
                <div class="select_time_wrap clearfix">
                    <div class="time_L">
                        结束时间：
                    </div>
                    <div class="time_inp_wrap">
                        <input type="text" class="cj_time_inp endTime" id="prize_end_time" name="model.strEndTime"
                               value="<fmt:formatDate value='${model.endTime}' pattern='yyyy-MM-dd HH:mm'/>"
                               readonly placeholder="请选择结束时间">
                    </div>

                </div>
            </div>
            <div class="cj_box">
                <div class="cj_tit no_border set_limit">
                    <input type="hidden" id="limit" name="model.showStatus" value="${model.showStatus}"/>
                    <i class="tit_ico blue"></i><span>设置限制</span>
                    <a href="javascript:;" class="set_limit_btn nest_trigge_btn" data-name="limit_wrap">
                        <s:if test="model.showStatus==0">所有人都可参加</s:if>
                        <s:elseif test="model.showStatus==1">公司内部人参加</s:elseif>
                        <s:else>选择参选人</s:else>
                    </a>
                </div>
            </div>

            <!-- 签到活动 -->

            <s:if test="flag == 1">
                <div class="cj_box">
                    <div class="cj_tit no_border set_limit">
                        <i class="tit_ico hsla"></i><span>积分设置</span>
                        <input type="text" class="cj_time_inp" name="model.score" id="score" value="${model.score}"
                               placeholder="每人每天签到所得积分数量" style="position: absolute;width: 10rem;right: 0;top: 0.3rem;border: 0;outline: 0;">
                    </div>
                </div>
                <div class="cj_box">
                    <div class="cj_tit no_border set_limit">
                        <i class="tit_ico hsla"></i><span>次数设置</span>
                        <input type="text" class="cj_time_inp" name="model.count" id="count" value="${model.count}"
                               placeholder="每人每天签到次数" style="position: absolute;width: 10rem;right: 0;top: 0.3rem;border: 0;outline: 0;">
                    </div>
                </div>
            </s:if>
            <s:if test="flag == 0">
                <div class="cj_box">
                    <div class="cj_tit no_border add_awards">
                        <i class="tit_ico yellow"></i><span>奖品设置（<span class="gray_tit">最多设置六项</span>）</span>
                        <a href="javascript:;" class="add_awards_btn nest_trigge_btn" data-name="award_add_wrap"></a>
                    </div>
                </div>
                <div class="award_wrap">
                    <dl class="award_th">
                        <dd>选择奖品类型</dd>
                        <dd>奖品名称</dd>
                        <dd>奖品数量</dd>
                        <dd>奖品级别</dd>
                    </dl>
                    <s:if test="sign eq 'edit'">
                        <s:iterator value="model.prizePool" var="entity">
                            <dl class="award_tb" id="<s:property value="#entity.poolId"/>">
                                <s:if test="#entity.prizeType==0"><dd>会员产品</dd></s:if>
                                <s:if test="#entity.prizeType==1"><dd>商家优惠券</dd></s:if>
                                <s:if test="#entity.prizeType==2"><dd>实物产品</dd></s:if>
                                <s:if test="#entity.prizeType==3"><dd>会员积分</dd></s:if>
                                <dd><s:property value="#entity.ppName"/></dd>
                                <dd><s:property value="#entity.prizeNum"/></dd>
                                <dd><s:property value="#entity.prizeLvl"/></dd>
                            </dl>
                        </s:iterator>
                    </s:if>
                </div>
                <div class="cj_mane cj_box">
                    <div class="cj_tit">
                        <i class="tit_ico yellow"></i><span>抽奖所需积分数</span>
                    </div>
                    <div class="cj_inp_box">
                        <input type="text" class="bonusPoints" placeholder="请输入抽奖所需积分数" name="model.bonusPoints" value="${model.bonusPoints}">
                    </div>
                </div>
            </s:if>
            <div class="cj_box">
                <div class="cj_tit set_limit">
                    <i class="tit_ico yellow"></i>
                    <span>活动注意事项</span>
                </div>
                <div class="matters_wrap">
                    <textarea class="matters_inp" placeholder="请输入注意事项…" name="model.activityDesc">${model.activityDesc}</textarea>
                </div>
                <div class="up_img_wrap clearfix">
                    <s:if test="sign eq 'edit'">
                        <s:iterator value="model.prizeDesc" var="entity">
                            <a class="upimg_box img_wrap" href="javascript:;">
                                <i class="upimg_del" id="<s:property value='#entity.activityDescId'/>"></i>
                                <img src="<%=basePath%><s:property value='#entity.activityDescImage'/>" alt="">
                            </a>
                        </s:iterator>
                    </s:if>
                    <a class="upimg_box add_upimg_btn" href="javascript:;">
                        <i></i>
                        <span>添加图片</span>
                        <input type="file" class="upimg_inp" id="upimg_inp" accept="image/*">
                    </a>
                </div>
                <a href="javascript:;" class="release_btn" onclick="save()">
                    <s:if test="flag eq 0">发布抽奖活动</s:if>
                    <s:else>发布签到活动</s:else>
                </a>
            </div>
        </div>


    </div>
    <div class="overlay">
        <div class="crop_wrap">
            <img id="image" src="" alt="">
            <a href="javascript:;" id="confrim">确 定</a>
        </div>
        <div class="loading">
            <img src="<%=basePath%>images\ea\lottery\loading.gif" alt="">
            <span>正在发布，请稍候！</span>
        </div>
    </div>
    <!--选择参选人 开始-->
    <div class="limit_wrap nest_page">
        <div class="nest_hd">
            <a href="###" class="nest_back" id="back_limit"></a>
            <span>设置限制</span>
        </div>
        <div class="nest_bd">
            <div class="limit_list">
                <a href="javascript:;" class="limit_list_box limit_cur">所有人都可参加</a>
                <a href="javascript:;" class="limit_list_box">公司内部人参加</a>
            </div>
        </div>

    </div>
</form>
<!--选择参选人 结束-->
<!--添加奖品 开始-->
<form id="prizePoolForm" method="post" action="">
    <input type="hidden" id="productId" name="poolBean.productId" value="">
    <input type="hidden" name="model.companyId" value="${model.companyId}">
    <div class="award_add_wrap nest_page">
        <div class="nest_hd">
            <a href="javascript:;" class="nest_back" id="back_prize"></a>
            <span>奖项设置</span>
        </div>
        <div class="nest_bd award_list">
            <div class="award_box clearfix">
                <div class="a_L">奖品类型</div>
                <input type="hidden" id="prizeType" name="poolBean.prizeType" value="">
                <input href="javascript:;" class="a_R radio_text" id="type" placeholder="选择奖品类型" readonly>
            </div>
            <div class="award_box clearfix">
                <div class="a_L">奖品名称</div>
                <input href="javascript:;" class="a_R" id="pro" name="poolBean.ppName" placeholder="选择奖品" readonly>
            </div>
            <div class="award_box clearfix">
                <div class="a_L">奖品价值</div>
                <input href="javascript:;" class="a_R" id="price" name="" placeholder="输入奖品价值" readonly>
            </div>
            <div class="award_box clearfix">
                <div class="a_L">奖品数量</div>
                <input href="javascript:;" class="a_R" name="poolBean.prizeNum" placeholder="输入奖品数量">
            </div>
            <div class="award_box clearfix">
                <div class="a_L">奖品级别</div>
                <input href="javascript:;" class="a_R radio_text" name="poolBean.prizeLvl" id="rank" placeholder="选择几等奖" readonly>
            </div>
        </div>
        <a href="javascript:;" class="save_btn" onclick="savePrizePool()">保存信息</a>
        <ul id="type_list" style="display: none">
            <%--<li>会员产品</li>--%>
            <%--<li>商家优惠券</li>--%>
            <li>实物产品</li>
            <%--<li>会员积分</li>--%>
        </ul>
        <ul id="rank_list" style="display: none">
            <s:iterator value="#request.poolList" var="entity">
                <li id="<s:property value="#entity.probaId"/>"><s:property value="#entity.prizeLevel"/></li>
            </s:iterator>
        </ul>
    </div>
</form>
<!--添加奖品 结束-->

<!--实物产品-->
<div class="award_add_wrap_pro nest_page">
    <div class="nest_hd">
        <a href="javascript:;" class="nest_back" id="back_pro"></a>
        <span>选择招商产品发布</span>
        <a href="javascript:;" class="head_R pro_add">新增产品</a>
    </div>

    <div class="nest_bd ">
        <div class="pro_search_wrap">
            <input type="text" class="pro_search" placeholder="搜索产品">
        </div>
        <div class="pro_wrap clearfix">
        </div>
    </div>
    <a href="javascript:;" class="fix_btn save_btn" onclick="chance()">确认选择</a>
</div>

<!--优惠券-->
<div class="award_add_wrap_coupons nest_page">
    <div class="nest_hd">
        <a href="javascript:;" class="nest_back" id="back_coupons"></a>
        <span>优惠券选择</span>
        <a href="javascript:;" class="head_R add_btn">新增</a>
    </div>

    <div class="nest_bd ">
        <div class="coupon_list">
            <div class="coupon_box clearfix">
                <input type="radio" name="coupon"  class="coupon_inp">
                <i class="select_state"></i>
                <div class="coupon_text">
                    <span>100元优惠券</span>
                    <span>满100.01元使用</span>
                </div>
            </div>
            <div class="coupon_box clearfix">
                <input type="radio" name="coupon"  class="coupon_inp">
                <i class="select_state"></i>
                <div class="coupon_text">
                    <span>100元优惠券</span>
                    <span>满100.01元使用</span>
                </div>
            </div>
            <div class="coupon_box clearfix">
                <input type="radio" name="coupon"  class="coupon_inp">
                <i class="select_state"></i>
                <div class="coupon_text">
                    <span>100元优惠券</span>
                    <span>满100.01元使用</span>
                </div>
            </div>
        </div>
    </div>
    <div class="wrap_fixed clearfix">
        <a href="javascript:;" class="dele_btn"><i></i>删除优惠券</a>
        <a href="javascript:;" class="affirm_btn">确认选择</a>
    </div>
</div>

<!--增加优惠券-->
<div class="award_add_wrap_couponAdd nest_page">
    <div class="nest_hd">
        <a href="javascript:;" class="nest_back" id="back_couponAdd"></a>
        <span>优惠券选择</span>
    </div>
    <div class="nest_bd">
        <div class="award_box clearfix">
            <div class="a_L">优惠券金额</div>
            <input href="javascript:;" class="a_R" placeholder="输入优惠券金额">
        </div>
        <div class="award_box clearfix">
            <div class="a_L">优惠券数量</div>
            <input href="javascript:;" class="a_R" placeholder="输入优惠券数量">
        </div>
        <div class="award_box clearfix">
            <div class="a_L">最低金额</div>
            <input href="javascript:;" class="a_R" placeholder="输入使用单笔最低金额">
        </div>
        <div class="award_box clearfix">
            <div class="a_L">限领数量</div>
            <input href="javascript:;" class="a_R" placeholder="输入限领数量">
        </div>
        <div class="award_box clearfix">
            <div class="a_L">有效时间-起</div>
            <input href="javascript:;" class="a_R beginTime" placeholder="选择有效时间开始时间" readonly id="coupon_begin_time">
        </div>
        <div class="award_box clearfix">
            <div class="a_L">有效时间-止</div>
            <input href="javascript:;" class="a_R endTime" placeholder="选择有效时间结束时间" readonly id="coupon_end_time">
        </div>
        <div class="award_box clearfix">
            <div class="coupon_rule_tit">优惠券使用规则</div>
            <textarea class="coupon_rule_inp" placeholder="如：单笔满200元可使用每人限领5张"></textarea>
        </div>
        <a href="javascript:;" class="coupon_save_btn">保 存</a>
    </div>
</div>
<!--弹窗提示-->
<jsp:include page="/page/prompt.jsp"/>

<script>
    var basePath = '<%=basePath%>';
    var companyId = '${model.companyId}';
    var pagenumber = 0;
    var pagecount = 0;
    var t ;
    var search = "";
</script>
</body>

</html>
