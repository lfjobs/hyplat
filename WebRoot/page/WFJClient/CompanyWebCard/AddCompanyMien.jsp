<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司风采</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<body class="bgcolorFFF">
	<div class="wfj01_001">
        
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath %>ea/industry/ea_getCompanyMien.jspa?ccompanyId=${ccompanyId}" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>${contactCompany.companyName }</li>
            	<li><a href="javascript:;"></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
<%--         <div class="wfj01_001_title">
        	<div>
            	<table>
                	<tr>
                    	<td width="25%" align="center"><img class="wfj01_001_hyimg" src="<%=basePath%>${contactCompany.logoPath}" /></td>
                    	<td width="15%">${contactCompany.cresponsible }</td>
                    	<td width="20%"><img class="wfj01_001_ewm" src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_ewm_02.png" /></td>
                    	<td width="40%" align="right"><div><c:if test="${company.ccomtype eq 0 }">大型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 1 }">中型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 2 }">小型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 3 }">微型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 4 }">供应商企业平台管理商城</c:if></div></td>
                    </tr>
                </table>
            </div>
        </div> --%>
        
        <div id="prompt" style="width: 100%;display: none;">
        			<center>
        				<div>
        					<span style="position: relative;top: 19.8%;"></span>
        				</div>
        			</center>
        </div>   
        <div class="wfj01_001_content">
        	<div class="wfj01_001_hidden">
        	<div class="wfj01_001_con_left widths" id="sa">
                        	<ul>
                        		<li class="wfj01_001_bigimg">
                        		<input class="file" type="file" name="pics" capture="camera" style="display:none;"/>
                        		<img src=""/></li>
                        	</ul>
            </div>
           
        		<form id="MienForm" method="post" enctype="multipart/form-data">
        		<input type="hidden" value="${ccompanyId }" id="ccompanyId"/>
        		<input type="hidden" name="ms.mienStyleID" value="${mienstyleId }"/>
            	<div class="wfj01_001_con">
                	<div class="wfj01_001_width">
                    	<div class="wfj01_001_depict">
                        	<textarea name="material.elaborate" onfocus="if(this.value=='风采描述'){this.value='';}"  onblur="if(this.value==''){this.value='风采描述';}" >风采描述</textarea>
                        </div>
                    	<div class="wfj01_001_con_title">
                        	<div class="wfj01_001_width">
                            	<ul>
                                	<li class="left"><span>上传到</span></li>
                                	<li class="right">我的风采库</li>
                                </ul>
                            </div>
                        </div>
                        
                        <div class="wfj01_001_con_right widths">
                        	<ul>
                            	<li class="addimg"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_addimg_02.png" /></li>
                            </ul>
                        </div>   
                    </div>
                </div>
                <div class="wfj01_001_bottom">
                    <div class="wfj01_001_bottom_width">保存信息</div>
                </div>    
            </div>
        </div>
        
        
        <div class="more">
        	<ul>
            	<li><a href="javascript:;">拍照</a></li>
            	<li><a href="javascript:;">从本地上传</a></li>
            	<li><a href="javascript:;">取消</a></li>
            </ul>
        </div></form>
        
        
    </div>
    
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    <script type="text/javascript">
    var select=0;
	var ccompanyId=$("#ccompanyId").val();
	var basePath='<%=basePath%>'
		$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			
			if($(window).width()>$(window).height()){
				$(".wfj01_001").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
				$(".more").attr("style","width:"+$(window).width()*0.7+"px;display:none;");
				$(".wfj01_001_popimg").attr("style","width:"+$(window).width()*0.7+"px;");
				$(".wfj01_001_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.03+"px;");
			}else{
				$(".wfj01_001").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
				$(".more").attr("style","width:"+$(window).width()+"px;display:none;");
				$(".wfj01_001_popimg").attr("style","width:"+$(window).width()+"px;");
				$(".wfj01_001_deleteimg").find("img").attr("style"," top:-"+$(window).height()*0.01+"px; right:"+$(window).height()*0.01+"px;");
			}
			
			$(".wfj01_001_title").attr("style","height:"+$(window).height()*0.1+"px;line-height:"+$(window).height()*0.1+"px; margin-top:"+$(window).height()*0.0015+"px;");
			$(".wfj01_001_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_title").find("div").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.04+"px; line-height:"+$(window).height()*0.04+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_hyimg").attr("style","height:"+$(window).height()*0.08+"px; width:auto;margin:"+$(window).height()*0.01+"px auto;");
			$(".wfj01_001_ewm").attr("style","height:"+$(window).height()*0.02+"px; width:auto;");
			$(".wfj01_001_con_title").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
			$(".wfj01_001_con_title").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_001_con_title").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			$(".wfj01_001_con_left").attr("style","display:none; margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj01_001_con_right").attr("style"," margin-bottom:"+$(window).height()*0.015+"px;");
			$(".wfj01_001_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_001_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");

			$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
			$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
			

			$(".wfj01_001_popimg").find("li").find("p").attr("style","font-size:"+$(window).height()*0.02+"px; height:"+$(window).height()*0.075+"px; line-height:"+$(window).height()*0.025+"px; bottom:"+$(window).height()*0.075+"px;");
			
			
			
			
		    $(".more").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom:"+$(window).height()*0.005+"px solid #F0F0F0;");
			$(".more").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");
				
			
			$(".wfj01_001_depict").find("textarea").attr("style"," height:"+$(window).height()*0.09+"px; line-height:"+$(window).height()*0.03+"px;font-size:"+$(window).height()*0.02+"px;");
			
			
			$(".wfj01_001_con_right").click(function(){
				
				$("#occlusion2").css("z-index",$(".wfj01_001").css("z-index")+1);
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
						$(".wfj_top").css("background",activities.describe);
						$(".wfj01_001_title").css("background",activities.describe);
						$(".wfj01_001_bottom_width").css("background",activities.describe);
					}
				},
				error : function cbf(data){
					alert("公司风格加载失败！");
				}
			});
			
			
			 var flag = false;
			 $(".wfj01_001_bigimg").mousedown(function() {
				var $this=$(this);
				var stop = setTimeout(function() {//down 1s，才运行。
					flag = true;
					$this.parent().find(".wfj01_001_deleteimg").find("img").css("display","block");
				},1500);
				 $(".wfj01_001_bigimg").mouseup(function() {//鼠标up时，判断down了多久，不足一秒，不执行down的代码。
					if (flag) {
						$(this).parent().find(".wfj01_001_deleteimg").find("img").css("display","block");
					}
					window.clearTimeout(stop);
				});
			 });
			
			 
			
			
			$(".wfj01_001_content").attr("style"," width:"+$(".wfj01_001").width()+"px;height:"+parseInt($(window).height()-$(".wfj_top").height()-$(".wfj01_001_title").height()-$(window).height()*0.0015)+"px;overflow:hidden;");
			
			var h = $(".wfj01_001_con").height()+$(".wfj01_001_bottom").height()+$(window).height()*0.03;
			
			if(h>$(".wfj01_001_content").height()){
				$(".wfj01_001_hidden").attr("style"," width:"+parseInt($(".wfj01_001_content").width()+17)+"px;height:"+$(".wfj01_001_content").height()+"px;overflow:auto;");
			}else{
				$(".wfj01_001_hidden").attr("style"," width:"+$(".wfj01_001_content").width()+"px;height:"+$(".wfj01_001_content").height()+"px;overflow:auto;");
			}
			
		
			//保存	
	    	$(".wfj01_001_bottom").click(function(){	    		
	    		var b=$(".wfj01_001_depict").find("textarea").val();
	    		if(b=='')
	    			prompt("风采描述不能为空");
	    		else if(b=='风采描述')
	    			prompt("风采描述不能为默认值");
	    		else{
	    		var formData=new FormData($("#MienForm")[0]);
	    		var url=basePath+"ea/industry/sajax_ea_saveCompanyMien.jspa?ccompanyId="+ccompanyId;
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
	    					window.location.href=basePath+"ea/industry/ea_getCompanyMien.jspa?ccompanyId="+ccompanyId;
	    				}
	    			},
	    			error : function cbf(data){
	    				alert("保存失败！");
	    				}
	    		});
	    		}
	    	});

	    	//取消
			$(".more").find("li").eq(2).click(function(){
				$(".more").fadeOut();
				$("#occlusion2").jqmHide();
			});
	    	//上传图片
	    	$(".more").find("li").eq(1).click(function(){	    		
	    		$(".wfj01_001_con_right").before($("#sa").clone(true).prop("id","pic"+select));
	    		$("#pic"+select).find("input").prop("id","file"+select);	    		    		
	    		$("#file"+select).click();
	
	    		$(".file").live("change",function(){
	    			var picPath=$(this);
	    		 	getImgUrl(picPath);
	    		});
	    		
	    		$(".more").fadeOut();
				$("#occlusion2").jqmHide();
				select++;
	    	});
	    	//拍照 
	    	$(".more").find("li").eq(0).click(function(){
	    		$(".wfj01_001_con_right").before($("#sa").clone(true).prop("id","pic"+select));
	    		$("#pic"+select).find("input").prop("id","file"+select);	    		    		
	    		$("#file"+select).click();
	
	    		$(".file").live("change",function(){
	    			var picPath=$(this);
	    		 	getImgUrl(picPath);
	    		});
	    		
	    		$(".more").fadeOut();
				$("#occlusion2").jqmHide();
				select++;
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
        });
    	
/*     	//上传图片预览
		function show(i){
    		var picPath=$("#"+i);
			getImgUrl(picPath,i);
			$(".more").fadeOut();
			$("#occlusion2").jqmHide();
		} */
    				
	    	function getImgUrl($t){	    		
	    		var img=new Image();
	    		var dw=$(".wfj01_001_con_right").find("img").width(),dh=$(".wfj01_001_con_right").find("img").height();	  
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
	    	
	    			$("#pic"+(select-1)).find("img").attr("src",da);
    				$("#pic"+(select-1)).show();
	    		};
	    		
	    	}
    </script>
</body>
</html>