<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>产品</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript"></script>
    <link href="<%=basePath%>/css/WFJClient/wfjhtmlStyle.css" rel="stylesheet" />
    <script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
</head>
<body>
    <div class="wfjall">
        <div class="wfjtop">
            <ul>
                <li>产品</li>
            </ul>
        </div>
        <div class="wfj_class">
            <ul>
                <li><div onclick="change_agencys(this)" name="agencysinfo">代理</div></li>
                <li class="change_bg"><div onclick="change_agencys(this)" name="agencysinfo" style="color: #2E63FB">未代理</div></li>
            </ul>
        </div>
        <!--未代理-->
        <div class="unagency" id="unagencys">
            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfjlogo">
            			<img src="<%=basePath%>/images/WFJClient/Distribution/banner.png" />
        			</div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul class="wfj_m">
                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
                            <li>现价：￥70</li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjdetails">查看详情</div>
                            </li>
                            <li>
                                <div class="wfjagency">我来代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul class="wfj_m">
                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
                            <li>现价：￥70</li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjdetails">查看详情</div>
                            </li>
                            <li>
                                <div class="wfjagency">我来代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul class="wfj_m">
                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
                            <li>现价：￥70</li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjdetails">查看详情</div>
                            </li>
                            <li>
                                <div class="wfjagency">我来代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul class="wfj_m">
                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
                            <li>现价：￥70</li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjdetails">查看详情</div>
                            </li>
                            <li>
                                <div class="wfjagency">我来代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul class="wfj_m">
                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
                            <li>现价：￥70</li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjdetails">查看详情</div>
                            </li>
                            <li>
                                <div class="wfjagency">我来代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>


        <!--代理-->
        <div class="unagency" id="agency" style="display:none;">

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥70</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare">分享</div>
                            </li>
                            <li>
                                <div class="wfjcancel">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥70</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare">分享</div>
                            </li>
                            <li>
                                <div class="wfjcancel">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥70</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare">分享</div>
                            </li>
                            <li>
                                <div class="wfjcancel">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥70</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare">分享</div>
                            </li>
                            <li>
                                <div class="wfjcancel">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                        <img src="<%=basePath%>/images/WFJClient/Distribution/products.png" />
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">满9.9包邮拉线眉笔方便 满9.9包邮拉线眉笔方便</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥70</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare">分享</div>
                            </li>
                            <li>
                                <div class="wfjcancel">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>


        </div>
    </div>
</body>
</html>