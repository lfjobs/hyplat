<!doctype html>
<%@page import="org.springframework.context.annotation.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.Date"%> 
<%@ page import="java.text.SimpleDateFormat" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Date currentTime = new Date(); 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String dateString = formatter.format(currentTime);
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加公司活动</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<body class="bgcolorFFF">
	<form id="activitiesForm" method="post" enctype="multipart/form-data" >
	<input id="submit" type="submit" style="display:none;"/>
	<input id="ccompanyId" value="${ccompanyId }" type="hidden"/>
	<input type="hidden" value="${activities.activitiesID }" name="activitiesId"/>
	<input name="activities.activitiesKey" type="hidden" value="${activities.activitiesKey }"/>
	<div class="wfj01_004">
    	<div class="wfj01_004_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_getCompanyActivitiesList.jspa?ccompanyId=${ccompanyId}&user=${user}" target="_self">
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>公司活动</li>
            	<li><a href="javascript:;"></a></li>
            </ul>
        </div>
        <div id="prompt" style="width: 100%;display: none;">
        			<center>
        				<div>
        					<span style="position: relative;top: 19.8%;"></span>
        				</div>
        			</center>
        </div> 
        <div class="wfj01_004_content">
        	<div class="wfj01_004_hidden">
            	<div class="wfj01_004_con">
                	<div class="wfj01_004_list">
                    	<div class="wfj01_004_width">
							<ul>
								<c:choose>
									<c:when test="${activities.releaseTime eq null||activities.releaseTime eq '' }"><li class="wfj01_004_date"><%=dateString %></li></c:when>
									<c:otherwise><li  id="date" class="wfj01_004_date"><fmt:formatDate value="${activities.releaseTime }"/></li></c:otherwise>
								</c:choose>
                                <li class="wfj01_004_title"><input  name="activities.title" type="text" value="${activities.title }" onfocus="if(this.value=='活动名称'){this.value='';}"  onblur="if(this.value==''){this.value='活动名称';}" /></li>                         
                          		 <li class="wfj01_004_title">作者</li>
                          		<c:choose>
                                	<c:when test="${activities.picture eq null ||activities.picture eq '' }">
                                	<li class="wfj01_004_addimg"><img src="<%=basePath %>images/WFJClient/PersonalJoining/wfj_addimg_01.png" /></li>
                                	</c:when>
                                	<c:otherwise><li class="wfj01_004_addimg"><img src="<%=basePath %>${activities.picture}" /></li></c:otherwise>
                                </c:choose>
                                <li class="wfj01_004_cons"><textarea name="activities.describe" id="textarea"  value="${activities.describe }" onfocus="if(this.value=='活动描述'){this.value='';}" onblur="if(this.value==''){this.value='活动描述';}" >${activities.describe }</textarea></li>
                                <li class="wfj01_004_save"><div>保存信息</div></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>       
         <div class="more">
        	<ul>
            	<li><a class="verification" href="javascript:;"><input id="pic1" type="file" name="activities.pic" onchange="show(this.id)" accept="image/*" capture="camera"/>拍照</a></li>
            	<li><a class="verification" href="javascript:;"><input onchange="show(this.id)" id="pic" type="file" name="activities.pic"/>从本地上传</a></li>
            	<li><a href="javascript:;">取消</a></li>
            </ul>
        </div>
        
    </div></form>
    
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    
    <script type="text/javascript">
		var wi=$("#textarea").css("width");
		var he=0;
		var basePath='<%=basePath%>';
		var ccompanyId=$("#ccompanyId").val();
    	$(document).ready(function(e) {
    		//时间显示
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$(".wfj01_004_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		 
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;font-size:"+$(window).height()*0.02+"px;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
		
			$(".wfj01_004_date").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_004_title").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
			$(".wfj01_004_title").find("input").attr("style","height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;");
			$(".wfj01_004_cons").find("textarea").attr("style","font-size:"+$(window).height()*0.02+"px;overflow:hidden;");
			$(".wfj01_004_save").find("div").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.025+"px;margin:"+$(window).height()*0.02+"px auto");
			he=$("#textarea").height();
		    $(".more").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom: 1px solid #F1F1F1;");
			$(".more").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			
			$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
			$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
			
			if($(".wfj01_004_title").find("input").val()==''){
				$(".wfj01_004_title").find("input").val("活动名称");
			}
			if($(".wfj01_004_cons").find("textarea").val()==''){
				$(".wfj01_004_cons").find("textarea").val("活动描述");
			}
			//if($(".wfj01_004_addimg").find("img").attr("src") == basePah+"images/WFJClient/PersonalJoining/wfj_addimg_01.png"){
				$(".wfj01_004_addimg").find("img").click(function(){
					
					$("#occlusion2").css("z-index",$(".wfj01_004").css("z-index")+1);
					$("#occlusion2").jqmShow();
					$(".more").css("z-index",$("#occlusion2").css("z-index")+1);
					$(".more").fadeIn(1000);
				});
				$(".jqmOverlay").live("click",function(){
					$(".more").fadeOut();
					$("#occlusion2").jqmHide();
				});
				
				//弹出层初始化
				$(".jqmWindow").jqm({
					modal : true, 
					overlay : 20
				}).jqmAddClose('.close');

			//}
			
				//加载公司风格
				var url=basePath+"ea/industry/sajax_ea_CompanyStyle.jspa?ccompanyId=${ccompanyId}";
				$.ajax({
					url : url,
					type : "get",
					async : false,
					success : function cbf(data){
						var member=eval("("+data+")");
						var activities=member.activities;
						if(activities!=null){
							$(".wfj01_004_top").css("background",activities.describe);
							$(".wfj01_004_save").find("div").css("background",activities.describe);
						}
					},
					error : function cbf(data){
						alert("公司风格加载失败！");
					}
				});
			
			
			
			if($(window).width()>$(window).height()){
				$(".wfj01_004").attr("style","width:70%;height:"+$(window).height()+"px;");
				$(".more").attr("style","width:70%;display:none;");
				$(".wfj01_004_content").attr("style","width:"+$(".wfj01_004").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_004_top").height())+"px;overflow:hidden;");
				$(".wfj01_004_hidden").attr("style","width:"+parseInt($(".wfj01_004_content").width()+17)+"px;height:"+$(".wfj01_004_content").height()+"px;overflow:auto;");
				
				
			}else{
				$(".wfj01_004").attr("style","width:100%;height:"+$(window).height()+"px;");
				$(".more").attr("style","width:100%;display:none;");
				$(".wfj01_004_content").attr("style","width:"+$(".wfj01_004").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_004_top").height())+"px;overflow:hidden;");
				if($(".wfj01_004_list").height()>$(".wfj01_004_content").height()){
					$(".wfj01_004_hidden").attr("style","width:"+parseInt($(".wfj01_004_content").width()+17)+"px;height:"+$(".wfj01_004_content").height()+"px;overflow:auto;");
				}else{
					$(".wfj01_004_hidden").attr("style","width:"+parseInt($(".wfj01_004_content").width())+"px;height:"+$(".wfj01_004_content").height()+"px;overflow:auto;");
				}
				
			}	
			
			$("#textarea").live("input propertychange",function(){
				if($(this).css("width")!=wi){
					$(".wfj01_004_hidden").attr("style","width:"+parseInt($(".wfj01_004_content").width()+17)+"px;height:"+$(".wfj01_004_content").height()+"px;overflow:auto;");
				}
			});
			
		
			
		});
		//保存	
    	$(".wfj01_004_save").click(function(){
    		var a=$(".wfj01_004_width").find("input").val();
    		var b=$(".wfj01_004_width").find("textarea").val();
    		
    		if(a=='')
    			prompt("活动名称不能为空");
    		else if(a=='活动名称')
    			prompt("活动名称不能为默认值");
    		else if(b=='')
    			prompt("活动描述不能为空");
    		else if(b=='活动描述')
    			prompt("活动描述不能为默认值");
    		else{
    		var formData=new FormData($("#activitiesForm")[0]);
    		var url=basePath+"ea/industry/sajax_ea_toSaveActivities.jspa?ccompanyId="+ccompanyId+"&user=${user}";
    		$.ajax({
    			url : encodeURI(url),
    			type : "post",
    			data : formData,
    			async : false,
    			cache: false,  
    		    contentType: false,  
    		    processData: false,  
    			success : function cbf(data){
    				var member=eval("("+data+")");
    				var flag=member.flag;
    				if(flag=="1"){
    					window.location.href=basePath+"ea/industry/ea_getCompanyActivitiesList.jspa?ccompanyId="+ccompanyId+"&user=${user}";
    				}
    			},
    			error : function cbf(data){
    				alert("保存失败！");
    				}
    		});
    		}
    	});
    	function getImgUrl($t){
    		var img=new Image();
    		var dw=$(".wfj01_004_addimg").width(),dh=$(".wfj01_004_addimg").height();
    		var f=$t.prop("files")[0];
    		if(f.type.match('image.*')){
    			var r = new FileReader();
    			r.onload = function(e){
    				img.setAttribute('src',e.target.result);
    		    };
    			r.readAsDataURL(f);
    		}
    		img.onload=function(){
    			var cv = document.createElement('div');
    			cv.innerHTML="<canvas></canvas>";
    			var rc = cv.children[0];
    			var ct = rc.getContext('2d');
    			rc.width=dw;
    			rc.height=dh;
    			ct.drawImage(img,0,0,dw,dh);
    			var da=rc.toDataURL();
    			$(".wfj01_004_addimg").find("img").attr("src",da);
    		};
    	}

		//上传图片预览
		function show(id){
			var picPath=$("#"+id);
			getImgUrl(picPath);
			$(".more").fadeOut();
			$("#occlusion2").jqmHide();
		}
		$(".more").find("li").eq(0).click(function(){
			$(this).find("input").click();
		});
	 	$(".more").find("li").eq(1).click(function(){							
			$(this).find("input").click();
		});
		//阻止冒泡
		$("input[type='file']").click(function(event){
			event.stopPropagation();
		}); 
		
		//取消
		$(".more").find("li").eq(2).click(function(){
			$(".more").fadeOut();
			$("#occlusion2").jqmHide();
		});

		function prompt(obj){
			if($("#prompt").css("display")!="none")
				return;
			$("#prompt").find("span").text(obj);
			$("#prompt").fadeIn(500);
			setTimeout(function(){
				$("#prompt").fadeOut(500);
				$("#prompt").find("span").text("");
			}, 2000);
		}
		var text = document.getElementById("textarea");
		autoTextarea(text);// 调用
		
		function  autoTextarea (elem, extra, maxHeight) {
			extra = extra || 0;
			var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window,
			isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'),
					addEvent = function (type, callback) {
							elem.addEventListener ?
									elem.addEventListener(type, callback, false) :
									elem.attachEvent('on' + type, callback);
			},
			getStyle = elem.currentStyle ? function (name) {
				var val = elem.currentStyle[name];
				if (name === 'height' && val.search(/px/i) !== 1) {
					var rect = elem.getBoundingClientRect();
					return rect.bottom - rect.top -
							parseFloat(getStyle('paddingTop')) -
							parseFloat(getStyle('paddingBottom')) + 'px';        
				};
	 
				return val;
			} : function (name) {
				return getComputedStyle(elem, null)[name];
			},
			minHeight = parseFloat(getStyle('height'));
	
			elem.style.resize = 'none';
	 
			var change = function () {
				var scrollTop, height,
						padding = 0,
						style = elem.style;
	
				if (elem._length === elem.value.length) return;
				elem._length = elem.value.length;
	
				if (!isFirefox && !isOpera) {
					padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
				};
				scrollTop = document.body.scrollTop || document.documentElement.scrollTop;
				if (elem.scrollHeight > minHeight) {
					elem.style.height=he+'px';
					if (maxHeight && elem.scrollHeight > maxHeight) {
						height = maxHeight - padding;
						style.overflowY = 'auto';
					} else {
						height = elem.scrollHeight - padding;
						style.overflowY = 'hidden';
					};
					style.height = height + extra + 'px';
					scrollTop += parseInt(style.height) - elem.currHeight;
					document.body.scrollTop = scrollTop;
										
					document.documentElement.scrollTop = scrollTop;
					elem.currHeight = parseInt(style.height);
						
				};
			};
	 
			addEvent('propertychange', change);
			addEvent('input', change);
			addEvent('focus', change);
			change();
		};
	</script>
</body>
</html>