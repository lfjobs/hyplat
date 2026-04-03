<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
    <script src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js" type="text/javascript"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/cropper.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/manage_5L5C.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/edmandServe/demand.css">
        <script type="text/javascript" src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
       	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/officemanage/cropper.min.js"></script>
       	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/qrshare/perfectionMessage.js"></script>
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
    <script src="<%=basePath%>js/ea/marketing/lottery/prizeActivity.js"></script>
    <!--选择插件 结束-->
    <link href="<%=basePath%>css/ea/lottery/mobiscroll_004.css" rel="stylesheet" type="text/css">
    <script src="<%=basePath%>js/ea/marketing/edmandServe/jiaxiaorelease.js" type="text/javascript"></script>
    <title>基本资料认证</title>
</head>

<script>
	var basePath = '<%=basePath%>';
	var staffID = '${caccount.staffID}';
	var companyID = '${caccount.companyID}';
	var auditSkip = '${auditSkip}';
	var auid = '${auditRecord.auid}';
	var ccompanyID='${contactCompany.ccompanyID}'
	var state = '${auditRecord.state}';
</script>

<tbody>
    <!-- header 开始  -->
    <header class="com_head">
        <a href="javascript:getBack();" class="back"></a>
        <h1>公司认证</h1>
    </header>
    <!--  header 结束  -->
    <!-- 页面内容 开始  -->
    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
    <div class="wrap_page">
        <div class="auth_tab_wrap clearfix">
            <a href="javascript:;" class="auth_tab auth_tab_cur">
                <span>1</span> 完善企业信息
            </a>
            <a href="javascript:;" class="auth_tab">
                <span>2</span> 上传企业资质
            </a>
        </div>
        <div class="tab_box">
        <div class="auth_list_wrap">
        	<form action="<%=basePath%>ea/qrshare/sajax_ea_addMessage.jspa">
            <div class="auth_list">
                <div class="auth_L">公司logo</div>
                <div class="auth_R">
                    <i class="auth_arrR"></i>
                    <div class="logo_wrap">
                        <img src="<%=basePath%>${contactCompany.logoPath!=null?contactCompany.logoPath:'images/WFJClient/PersonalJoining/logo@2x.png' }" alt="" id="logo_img" id="tcompany.coachnumber">
                        <input type="file" accept="image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp" id="logo_inp">
                    </div>
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">公司名称</div>
                <div class="auth_R">
                    <input type="text" name="contactCompany.companyName" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.companyName }" id="contactCompany.companyName">
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">法人代表</div>
                <div class="auth_R">
                    <input type="text" name="contactCompany.representative" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.representative }" id="contactCompany.representative">
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">身份证号</div>
                <div class="auth_R">
                    <input type="text" name="contactCompany.idCard" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.idCard }" id="contactCompany.idCard">
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">公司地址</div>
                <div class="auth_R">
                    <input type="text" name="contactCompany.companyAddr" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.companyAddr }" id="contactCompany.companyAddr">
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">联系人</div>
                <div class="auth_R">
                    <input type="text" name="contactCompany.cresponsible" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.cresponsible }" id="contactCompany.cresponsible">
                </div>
            </div>
            <div class="auth_list">
                <div class="auth_L">联系电话</div>
                <div class="auth_R">
                    <input type="tel" name="contactCompany.responsibleTel" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${contactCompany.responsibleTel }" id="contactCompany.responsibleTel">
                </div>
            </div>
                <div class="auth_list ">
                    <div class="auth_L">行业类别</div>
                    <div class="auth_R">
                   <input type="text" class="auth_inp" placeholder="点击选择行业类别" readonly id="selsct_classify" name="contactCompany.industryType" readonly/>
                   <input type="hidden" name="demandDetail.ddscodeid" id="ddscodeid"/>
                    </div>
                </div>

                <div id="j_body">

                <div class="auth_list">
                <div class="auth_L">教练员总数</div>
                <div class="auth_R">
                    <input type="tel" name="tcompany.coachnumber" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.coachnumber }">
                </div>
            </div>
                <div class="auth_list">
                    <div class="auth_L">教练场总面积</div>
                    <div class="auth_R">
                        <input type="tel" name="tcompany.coachnumber" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.coachnumber }">
                    </div>
                </div>
                <div class="auth_list">
                    <div class="auth_L">初次发证日期</div>
                    <div class="auth_R" id="dianji">
<%--
                        <input type="text" name="tcompany.firstIssueDate" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.firstIssueDate }" onfocus="daytime(this);">
--%>
                    <input type="tel" class="cj_time_inp endTime" id="prize_end_time" name="tcompany.firstIssueDate"
                           value="<fmt:formatDate value='${tcompany.firstIssueDate}' pattern='yyyy-MM-dd hh:mm'/>" placeholder="请输入">
                    </div>
                </div>
                <div class="auth_list">
                    <div class="auth_L">培训机构简称</div>
                    <div class="auth_R">
                        <input type="tel" name="tcompany.shortname" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.shortname }">
                    </div>
                </div>
                <div class="auth_list">
                    <div class="auth_L">统一信用代码</div>
                    <div class="auth_R">
                        <input type="tel" name="tcompany.creditcode" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.creditcode }" >
                    </div>
                </div>
                <div class="auth_list">
                    <div class="auth_L">邮政编码</div>
                    <div class="auth_R">
                        <input type="tel" name="tcompany.postcode" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.postcode }" >
                    </div>
                </div>
                <div class="auth_list">
                    <div class="auth_L">经营状态</div>
                    <div class="auth_R">
                        <input type="tel" name="tcompany.busistatus" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.busistatus }" >
                    </div>
                </div>
                <div class="auth_list">
                <div class="auth_L">经营许可日期</div>
                <div class="auth_R">
                    <input type="tel" class="cj_time_inp endTime"  name="tcompany.licetime" id="prize_begin_time"
                           value="<fmt:formatDate value='${tcompany.licetime}' pattern='yyyy-MM-dd hh:mm'/>" placeholder="请输入">
                </div>
                </div>

                    <div class="auth_list">
                        <div class="auth_L">经营范围</div>
                        <div class="auth_R">
                            <input type="tel" name="tcompany.busiscope" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.busiscope}" >

                        </div>
                    </div>
                    <div class="auth_list">
                        <div class="auth_L">教练车总数</div>
                        <div class="auth_R">
                            <input type="tel" name="tcompany.tracarnum" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.tracarnum}" >

                        </div>
                    </div>
                    <div class="auth_list">
                        <div class="auth_L">全国统一编码</div>
                        <div class="auth_R">
                            <input type="tel" name="tcompany.inscode" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.inscode}" >

                        </div>
                    </div>
                    <div class="auth_list">
                        <div class="auth_L">所属行政区域</div>
                        <div class="auth_R">
                            <input type="tel" name="tcompany.district" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.district}" >

                        </div>
                    </div>
                    <div class="auth_list">
                        <div class="auth_L">税务登记证编号</div>
                        <div class="auth_R">
                            <input type="tel" name="tcompany.taxregcer" onBlur="lose($(this))" placeholder="请输入" class="auth_inp" value="${tcompany.taxregcer}" >

                        </div>
                    </div>

                    <div class="auth_list d_r_site">
                        <div class="auth_L">获取经纬度</div>
                        <div class="auth_R " >
                            <input type="tel" name="tcompany.delFlag" onBlur="lose($(this))" placeholder="请点击获取" class="auth_inp" value="${contactCompany.jingdu}">

                        </div>
                    </div>
                </div>
           </form>
        </div>

        <a href="javascript:;" class="auth_btn next_btn">下一步</a>
        </div>
        <!--正在加载/正在发布 遮罩层 结束-->
        <div class="nest_page" style="background: #f3f3f3;">
            <div class="nest_hd">
                <a href="###" class="nest_back"></a>
                <span>选择行业类别</span>
            </div>
            <div class="nest_bd"></div>
        </div>
        <!-- 遮罩层 -->
        <div class="tab_box" id="bbb">
        	<div class="license_img">
                <img src="<%=basePath%>${certificate.certificateLocation!=null?certificate.certificateLocation:'images/WFJClient/PersonalJoining/auditLogo.png'}" alt="" id="lic_img">
                <input type="file" id="lic_inp" accept="image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp">
            </div>
            <a href="javascript:sumitImageFile();" class="auth_btn">提交完成</a>
        </div>

        <!-- 审批人意见 -->
        <div class="auditor"><div class="auditor">
            <div class="auditor_tit">审批人</div>
            <div class="auditor_wrap">
            <c:forEach items="${list}" var="l">
                <div class="auditor_box">
                    <img src="<%=basePath%>images/WFJClient/Mimage/head_img.png" class="auditor_img" alt="">
                    <div class="auditor_con">
                        <div class="auditor_type"><span>岗位：${l[3]}</span><span>部门：${l[2]}</span></div>
                        <div class="auditor_com">公司：${l[1]}</div>
                        <div class="auditor_details">
                            <div>
                                <span class="auditor_name"><i class="pass"></i>审核人:    ${l[0]}</span>
                                <span class="auditor_time">${ l[4]}</span>
                            </div>
                            <div class="auditor_opinion"><c:if test="${l[5] == 2}">通过</c:if><c:if test="${l[5] == 3}">驳回</c:if>意见：${l[6]}</div>
                        </div>
                    </div>
                </div>
                </c:forEach>
        
            </div>
        </div>
        <div class="opinion_text" id="opinion_text" style="display: none;">
            <textarea class="opinion_textarea" placeholder="请输入审核意见" name="AuditComment"></textarea>
        </div>
		<div class="auditor_btn clearfix" style="display: none;">
            <a href="javascript:submitAudit('02');" class="reject_btn">同意</a>
            <a href="javascript:submitAudit('03');" class="pass_btn">驳回</a>
            <a href="javascript:submitAudit('04');"class="pass_btn">转交</a>
        </div>	
        <!-- 审批人意见结束 -->
        
        
        <div class="overlay">
            <div class="crop_wrap">
                <img id="image" src="" alt="">
                <a href="javascript:;" id="confrim">确 定</a>
            </div>
        </div>
    </div>
    <!--  页面内容 结束 -->
    <script>
		//Input file选择图片上传事件
        $(document).on("change", "#logo_inp", function() {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onload = function() {
                // 通过 reader.result 来访问生成的 DataURL
                var url = reader.result;
                $("#logo_img").attr("src", url);
                cutImg(1/1);
                $("#image").cropper("replace", url);

            };
            reader.readAsDataURL(file);
            $("#confrim").one("click", function() {
                confirm($("#logo_img"));
            });

        });
        $(document).on("change", "#lic_inp", function() {
            var file = this.files[0];
            var reader = new FileReader();
            reader.onload = function() {
                // 通过 reader.result 来访问生成的 DataURL
                var url = reader.result;
                $("#lic_img").attr("src", url);
                cutImg(3/4);
                $("#image").cropper("replace", url);

            };
            reader.readAsDataURL(file);
            $("#confrim").one("click", function() {
                confirm($("#lic_img"));
            });

        });
        //初始化显示第一个
        $(".tab_box").eq(0).show();
        //切换页面点击
        $(".auth_tab").click(function(){
        	var auth = $(".auth_inp");
			for ( var i = 0; i < auth.length; i++) {
				if(lose($(auth[i]))=='01'){
					return $(auth[i]).focus();
				}
			}
            $(this).addClass("auth_tab_cur").siblings("a").removeClass("auth_tab_cur");
            var index=$(".auth_tab_wrap .auth_tab").index(this);
            $(".tab_box").eq(index).show().siblings(".tab_box").hide();
        })
        //下一步 点击
        $(".next_btn").click(function(){
        	var auth = $(".auth_inp");
			for ( var i = 0; i < auth.length; i++) {
				if(lose($(auth[i]))=='01'){
					return $(auth[i]).focus();
				}
			}
            $(".tab_box").eq(0).hide();
            $(".tab_box").eq(1).show();
            $(".auth_tab").eq(0).removeClass("auth_tab_cur");
            $(".auth_tab").eq(1).addClass("auth_tab_cur");
        })
        //裁剪
        //弹出裁剪遮罩层
        function cutImg(num) {
            $(".overlay").addClass("active");
            var $image = $('#image');
            $image.cropper("setAspectRatio",num);
            $image.cropper({
                checkImageOrigin: true,
                //aspectRatio:num, //比例
                autoCropArea: 0.8
            });
           
        }

        function confirm(img) {
            $(".overlay").removeClass("active");
            var $image = $('#image');
            var dataURL = $image.cropper("getCroppedCanvas");
            
            var imgurl = dataURL.toDataURL("image/png", 1.0);
            img.attr("src", imgurl);
            $image.cropper("reset");

        }
        var screenH=window.innerHeight;
		
		//审核意见输入法弹出
        window.onresize = function () {
            var t=window.innerHeight;       
            var inp=document.getElementById("opinion_text");
            if(t<screenH){
                $(".opinion_text").css({
                    position:'fixed',
                    top:'2.16rem',
                    left:0,
                    'z-index':999
                })
            }else{
                $(".opinion_text").css({
                    position:'static'
                })
            }
        }
    //提示框    
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
	$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
    
  /*   
    //2017.1.24 手机端输入法遮盖输入框
        $(".auth_inp").focus(function(){
            $(".wrap_page").attr("style","height:"+$(window).height()*1.3+"px");
        });
        $(".auth_inp").blur(function(){
            $(".wrap_page").attr("style","height:"+$(window).height()*0.92+"px");
        }); */
    
    //处理浏览器输入法遮挡
        var screenH=window.innerHeight;
         window.onresize = function () {
            var t=window.innerHeight;    
             console.log(t);
             console.log(screenH);
            var inp=$("input:focus")[0];
            if(t<screenH){
               inp.scrollIntoView(false);
            }
         }
    
    
    
    function getBack(){
    	if(auditSkip=='00'){
    		document.location.href = basePath + "/mobile/office/mobileoffice_fastApplication.jspa?";
    	}else if(auditSkip=='01'){
    		document.location.href = basePath + "ea/workmessage/ea_taskMessage.jspa?";
    	}
    }
    
    if(auditSkip=='01'){
    	document.getElementById("logo_inp").readOnly=true;
    	document.getElementById("contactCompany.companyName").readOnly=true;
    	document.getElementById("contactCompany.representative").readOnly=true;
    	document.getElementById("contactCompany.idCard").readOnly=true;
    	document.getElementById("contactCompany.companyAddr").readOnly=true;
    	document.getElementById("contactCompany.cresponsible").readOnly=true;
    	document.getElementById("contactCompany.responsibleTel").readOnly=true;
    }
        var xingye='${contactCompany.industryType}';
        if(xingye.indexOf("驾校")>=0) {
            $("#j_body").show();
            $("#mainframe").attr("height", 632 + "px");

        } else {
            $("#j_body").hide();
        }
    </script>
</body>

</html>