<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/cropper.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/officemanage/manage_5L5C.css">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/artEditor.js"></script>
   	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/officemanage/cropper.min.js"></script>
   	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/officemanage/addNews.js"></script>
    <title>添加新闻</title>
</head>

<body>
    <!-- header start  -->
    <header class="com_head">
        <a href="javascript:history.back(-1)" class="back"></a>
        <h1>公司新闻</h1>
        <a href="javascript:void(0);" class="head_R" onclick="preview()">预览</a>
        <a href="javascript:void(0);" class="release" onclick="save()">发布</a>
    	<div id="prompt" style="width: 100%; display: none;z-index: 1001">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <form action="<%=basePath%>ea/companyMaintain/ea_saveOrUpdateNews.jspa" class="com_news" enctype="multipart/form-data" method="post">
            <div class="mainimg_wrap clearfix">
                		新闻主图
                <div class="main_img crop_box">
                	<c:if test="${empty list[0][2]}"><img src="" alt="" id="crop_img"></c:if>
                	<c:if test="${!empty list[0][2]}"><img src="<%=basePath%>${list[0][2]}" alt="" id="crop_img"></c:if>
                    <input type="hidden" value="${list[0][0] }" id="ppId" name="productPackaging.ppID">
                    <input type="hidden" value="${list[0][1] }" id="goodsId" name="productPackaging.goodsID">
                    <input type="hidden" value="${cuscom.companyId }" id="companyId" name="cuscom.companyId">
                    <input type="hidden" value="${cuscom.staffid }" id="staffid" name="cuscom.staffid">
                    <input type="hidden" value="${list[0][2]}" id="zhutu" name="cropInp">
                    <input type="file" id="crop_inp" accept="image/*">
                </div>
            </div>
            <div class="new_tit">
                <input type="text" placeholder="请输入标题" value="${list[0][3]  }" id="text" name="productPackaging.goodsName">
            </div>
            <div class="new_conwrap">
                <input type="hidden" id="target" name="content" value="">
                <div id="new_content" class="new_content">
                	<c:forEach items="${list }" var="s">
                		${s[4] }
                	</c:forEach>
                </div>
            </div>
            <div style="display:none"><input type="submit" value="登 录" name="dosubmit" id="dosubmit" /></div>
        </form>
        <div class="new_footer">
            <i class="add_img"></i>添加图片
            <input class="input-file" id="imageUpload" type="file" name="fileInput"  accept="image/*">
        </div>
        
    </div>
    <div class="overlay">
            <div class="crop_wrap">
                <img id="image" src="" alt="">
                <a href="javascript:void(0);" id="confrim" >确 定</a>
            </div>
        </div>
    <!--  页面内容 end -->
	<div style="display: none;" id="preview" >
	<header class="com_head">
        <a href="javascript:void(0);" class="back" onclick="back()"></a>
        <h1>新闻预览</h1>
        <a href="javascript:void(0);" class="head_R" onclick="save()">发布</a>
    </header>
	<article class="yangshi">
    	<h1 style="margin-left:1rem;" class="title"></h1>
        <h2 style="margin-left:1rem;">
        	<span class="time"></span>
        	<span>新闻</span>
        </h2>
        <div class="line"></div>
        <div class="content" >
          <table class="content_tab" cellpadding="0" cellspacing="0"  >
            <tr>
               <td>
               <div >
             	 <img src=""  class="picture"/>
               </div>
               </td>
            </tr>
        	<tr>
        		<td style="height: 150px;">
        	    	<span>
        	    		<div style="width:100%;" id="display">
        	    			
        	    		</div>
        	    	</span> 
        		</td>
        	</tr>
          </table>
        </div>
        <div class="wfj12_019_body">
			<!--公司名片二维码 -->
			<table align="center" id="table1" name="table1" style="width: 100%;">
				<tr>
					<td align="center">
						<img style="width:150px;height:150px;" src="<%=basePath%>${concom.pmCodePath }" width="90px"
							height="90px" /><br> ${concom.companyName }  <br>
					</td>
				</tr>
			</table>
		</div>
	</div>
    </article>
    </div>
    <script>
    	var basePath='<%=basePath%>';
    	var companyId = '${cuscom.companyId}';
    	var staffId = '${cuscom.staffid}';
    
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        
          //input file选择图片上传事件
                $(document).on("change","#crop_inp", function() {
                    var file = this.files[0];
                    var reader = new FileReader();
                    reader.onload = function() {
                        // 通过 reader.result 来访问生成的 DataURL
                        var url = reader.result;
                        $("#crop_img").attr("src", url);
                        cutImg();
                        $("#image").cropper("replace", url);

                    };
                    reader.readAsDataURL(file);
                    $("#confrim").one("click", function() {
                        confirm($("#crop_img"));
                    });
                    
                });

        
        //裁剪
        //弹出裁剪遮罩层
        function cutImg() {
            $(".overlay").addClass("active");
            var $image = $('#image');
            $image.cropper({
                checkImageOrigin: true,
                aspectRatio: 4 / 3,//比例
                autoCropArea: 0.8
            });
        }

        function confirm(img) {
            $(".overlay").removeClass("active");
            var $image = $('#image');
            var dataURL = $image.cropper("getCroppedCanvas");
            var imgurl = dataURL.toDataURL("image/png", 1.0); 
            img.attr("src", imgurl);
			$("#zhutu").attr("value",imgurl);
        }
        //文章编辑器
        $('#new_content').artEditor({
            imgTar: '#imageUpload',
            limitSize: 5, // 兆（限制上传图片大小）
            showServer: false,
            uploadUrl: '',
            data: {},
            uploadField: 'image',
            placeholader: '<p style="color:#9d9d9d">请输入正文</p>',
            validHtml: ["<br/>"],
            formInputId: 'target',            
        });
    </script>
</body>
</html>