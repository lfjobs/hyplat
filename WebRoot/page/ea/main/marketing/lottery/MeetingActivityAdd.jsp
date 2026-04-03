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
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/hot_activity.css">
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
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/qr_share.css">
    <!--选择日期时间插件 结束-->
    <!--选择插件 开始-->
    <link rel="stylesheet" href="<%=basePath%>css/ea/lottery/mobiscroll-select.min.css">
    <script src="<%=basePath%>js/ea/marketing/lottery/mobiscroll-select.min.js"></script>
    <!--选择插件 结束-->
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_004.css" rel="stylesheet" type="text/css">
    <title>活动添加</title>
</head>

<body style="background: #f7f7f7;">
<header class="com_head">
    <a href="<%=basePath%>ea/lottery/ea_meetingAddReturn.jspa?flag=${flag}&model.companyId=${model.companyId}" class="back"></a>
    <h1>活动添加</h1>
</header>
    <div class="wrap_page act_wrap">
        <div class="step_wrap clearfix">
            <a href="###" class="step_box step_act">活动基本信息</a>
            <a href="###" class="step_box">票券信息</a>
            <a href="###" class="step_box">活动详情</a>
        </div>
        <form id="ActivityForm" method="post" enctype="multipart/form-data">
            <!-- 活动详情信息 -->
            <div class="submit_html"></div>
            <input type="hidden" id="submit_val" name="content" value=""/>
            <input type="hidden" id="submit_Img" name="model.activityImg" value=""/>
            <input type="hidden" id="activityKey" name="model.activityKey" value="${model.activityKey}"/>
            <input type="hidden" id="activityId" name="model.activityId" value="${model.activityId}"/>
            <input type="hidden" id="activityType" name="model.activityType" value="${flag}">
            <input type="hidden" id="companyId" name="model.companyId" value="${model.companyId}">
            <div class="act_info_wrap hotact_con">
                <div class="act_info_box clearfix">
                    <div class="act_infoL act_imgL">
                        活动主图
                    </div>
                    <div href="javascript:;" class="act_infoR act_imgR clearfix">
                        <div class="act_mainimg">
                            <img src="<c:if test="${sign =='edit'}"><%=basePath%></c:if>${model.activityImg!=''? model.activityImg :''}" alt="" class="act_src" id="act_img">
                            <input type="file" class="act_upinp" id="act_upinp" accept="image/*">
                            <s:if test="sign eq 'edit'"><input type="hidden" id="activityImg" value="${model.activityImg}"></s:if>
                        </div>
                    </div>
                </div>
                <div class="act_info_box clearfix">
                    <div class="act_infoL">
                        活动名称
                    </div>
                    <div class="act_infoR">
                        <input type="text" class="act_info_inp act_name" placeholder="请输入活动名称" name="model.activityName" value="${model.activityName}">
                    </div>
                </div>
                <div class="act_info_box clearfix">
                    <div class="act_infoL">
                        活动开始时间
                    </div>
                    <div class="act_infoR">
                        <input type="text" class="act_info_inp act_begin" id="begin_time" name="model.strStartingTime"
                               value="<fmt:formatDate value='${model.startingTime}' pattern='yyyy-MM-dd hh:mm'/>"
                               readonly placeholder="请选择开始时间">
                    </div>
                </div>
                <div class="act_info_box clearfix">
                    <div class="act_infoL">
                        活动结束时间
                    </div>
                    <div class="act_infoR">
                        <input type="text" class="act_info_inp act_end" id="end_time" name="model.strEndTime"
                               value="<fmt:formatDate value='${model.endTime}' pattern='yyyy-MM-dd hh:mm'/>"
                               readonly placeholder="请选择结束时间">
                    </div>
                </div>
                <div class="act_info_box clearfix">
                    <div class="act_infoL">
                        活动地点
                    </div>
                    <div class="act_infoR">
                        <input type="text" class="act_info_inp" id="address" value="${model.address}" placeholder="请输入活动地点" name="model.address">
                    </div>
                </div>
            </div>
            <div class="ticket_wrap hotact_con">
                <div class="ticket_box ticket_box_info">
                    <a href="javascript:;" class="minus_btn"></a>
                    <s:if test="sign eq 'edit'">
                        <s:iterator value="model.ticket" var="t">
                            <div class="ticket_info clearfix">
                                <input type="hidden" name="ticket.tid" value="<s:property value="#t.tid"/>" class="ticket_inp ticketId"/>
                                <input type="hidden" name="ticket.tkey" value="<s:property value="#t.tkey"/>" class="ticket_inp ticketKey"/>
                                <div class="ticket_info_tr">
                                    <span>票券名称</span>
                                    <span><input type="text" class="ticket_inp ticketName" value="<s:property value="#t.tname"/>" placeholder="请输入"></span>
                                </div>
                                <div class="ticket_info_tr">
                                    <span>票券价格</span>
                                    <span><input type="text" class="ticket_inp ticketPrice" value="<s:property value="#t.tprice"/>" placeholder="0.00元" readonly></span>
                                </div>
                                <div class="ticket_info_tr">
                                    <span>票券数量</span>
                                    <span><input type="text" class="ticket_inp ticketCount" value="<s:property value="#t.tcount"/>" placeholder="0.00元"></span>
                                </div>
                            </div>
                        </s:iterator>
                    </s:if>
                    <s:else>
                        <div class="ticket_info clearfix">
                            <div class="ticket_info_tr">
                                <span>票券名称</span>
                                <span><input type="text" class="ticket_inp ticketName" placeholder="请输入"></span>
                            </div>
                            <div class="ticket_info_tr">
                                <span>票券价格</span>
                                <span><input type="text" class="ticket_inp ticketPrice" value="0" readonly></span>
                            </div>
                            <div class="ticket_info_tr">
                                <span>票券数量</span>
                                <span><input type="text" class="ticket_inp ticketCount" placeholder="0张"></span>
                            </div>
                        </div>
                    </s:else>
                </div>
                <!-- 票券信息 -->
                <input type="hidden" id="tickets" name="tickets" value=""/>
                <a href="javascript:;" class="ticket_add">添加票券类型</a>
            </div>
        </form>
        <div class="detail_con hotact_con">
            <div class="qrw_tit" style="display:none;">
                <input type="text"
                       name="productPackaging.goodsName" class="goodsName"
                       value="" onkeydown="if(event.keyCode==13){event.keyCode=0;return false;}">
            </div>
            <div class="art_con">
                <iframe id="idss" border="0"  width="100%" name="iframe_con" style="border:0;overflow:auto" outline="0" src="<%=basePath%>page/ea/main/production/cprocedure/qrshare/qrshare_nest.jsp"></iframe>
            </div>
            <div class="qrw_add" style="bottom: 3.2rem;">
                <div class="art_add_menu">
                    <a href="javascript:;" class="add_music"> <span>选择音乐</span> </a>
                    <a href="javascript:;" class="add_img"> <input type="file"
                                                                    accept="image/*" multiple="" id="up_img"> <span>添加图片</span></a>
                    <a href="javascript:;" class="add_vedio"> <input type="file"
                                                                      accept="video/*" id="up_video"> <span>添加视频</span> </a>
                    <a href="javascript:;" class="add_pro"> <span>添加产品</span> </a>
                </div>
            </div>
        </div>
        <a href="javascript:;" class="save_next">保存进行下一步</a>
    </div>
    <div class="overlay">
        <div class="crop_wrap">
            <img id="image" src="" alt="">
            <a href="javascript:;" id="confrim">确 定</a>
        </div>
        <div class="loading">
            <img src="<%=basePath%>images/ea/production/qrshare/loading.gif" alt="">
            <span>正在发布活动</span>
        </div>
    </div>
    <div id="loading">
        <img src="<%=basePath%>images/ea/production/qrshare/loading.gif"
             alt="">
        <p class="load"></p>
    </div>
    <div id="delImgVideo" style="display: none;" data-url=""></div>
    <div id="comparison" style="display: none;" data-url=""></div>
    <audio src="" id="edit_audio"></audio>
    <!--遮罩层-内嵌页面 开始-->
    <div class="overlay" style="display:block;" id="overlay">
    <!--在线视频选择 结束-->
    <!--文字输入全屏页面 开始-->
    <div class="textarea_inp">
        <header class="com_head t_head">
            <a href="javascript:;" class="back"></a>
            <h1>编辑文字</h1>
            <a href="javascript:;" class="head_R">保存</a>
        </header>
        <textarea id="text_val" placeholder="请输入文章详情"></textarea>
    </div>
    <!--文字输入全屏页面 结束-->
    <!--选择背景音乐 开始-->
    <div class="music_wrap">
        <header class="com_head">
            <a href="javascript:;" class="back"></a>
            <h1>选择背景音乐</h1>
            <a href="javascript:;" class="head_R">完成</a>
        </header>
        <div class="wrap_page">
            <div class="no_music">无背景音乐</div>
            <div class="online_music">
                <!-- ajax拼接 -->
            </div>
            <div class="built_music">
                <!-- ajax拼接 -->
            </div>
        </div>
        <!--选择在线音乐 开始-->
        <div class="onlie_search_wrap">
            <div class="m_s_wrap clearfix">
                <a href="javascript:;" class="m_sback"></a>
                <div class="m_sbox">
                    <input type="text" placeholder="搜索歌曲名" class="m_ssearch">
                </div>
                <a href="javascript:searchMusic();" class="m_s_headR">搜索</a>
            </div>
            <div class="m_slist">
                <!-- ajax拼接 -->
            </div>
        </div>
        <!--选择在线音乐 结束-->
        <a href="javascript:;" class="search_btn" style="display: none;"><i></i>搜索在线音乐</a>
    </div>
        <!--选择产品页面 开始-->
        <div class="pro_wrap">
            <header class="com_head">
                <a href="javascript:;" class="back"></a>
                <h1>
                    <span>选择客户当前需求产品</span>
                </h1>
                <a href="javascript:;" class="head_R">完成</a>
            </header>
            <div class="wrap_page">
                <div class="pro_search_box">
                    <input type="text" placeholder="搜索产品" class="pro_search">
                </div>
                <div class="pro_con clearfix product">
                    <!-- ajax拼接 -->
                </div>
            </div>
        </div>
        <!--选择产品页面 结束-->
    </div>
<!--弹窗提示-->
<jsp:include page="/page/prompt.jsp"/>

    <script>
        var basePath = '<%=basePath%>';
        var companyId = '${model.companyId}';
        var tickets = "";
        var staffid = '${caccount.staffID}';
        var goodsName = "";
        var flag = 2;
    </script>
    <script src="<%=basePath%>js/ea/marketing/lottery/MeetingActivityAdd.js"></script>
    <script src="<%=basePath%>js/ea/production/cprocedure/qrshare/QR_art.js"></script>
</body>

</html>
