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
<title>网站商城名片</title>
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
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/companyCard.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}

</style>
</head>
<script type="text/javascript">
var user='${user}';
</script>
<body>
	<div class="wfj12_008">
    	<div class="wfj12_008_top">
        	<ul id="tops">
            	<li><a href="<%=basePath %>ea/industry/ea_CompanyCard.jspa?ccompanyId=${ccompanyId}&user=${user}&editType=0" target="_self"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a></li>
            	<li>完善公司名片</li>
            	<li><a href="javascript:;"></a></li>
            </ul>
        </div>
        
        <div class="wfj12_008_changeinfo">
        	<div class="wfj12_008_shop_content">
            	<div class="wfj12_008_shop_hidden">
                	<form method="post" id="companyInfo" enctype="multipart/form-data">
                	<input id="submit" type="submit" style="display:none;"/>
                	<input type="hidden" value="${contactCompany.ccompanyKey }" name="contactCompany.ccompanyKey"/>
                    <input type="hidden" value="${contactCompany.ccompanyID }" name="ccompanyId"/>
                    <input type="hidden" value="${ccomconf.ccomConfKey }" name="ccomconf.ccomConfKey"/>
                    <input type="hidden" value="${contactCompany.ccompanyID }" name="contactCompany.ccompanyID"/>
                    <input type="hidden" value="${ccomconf.ccomConfId }" name="ccomConfId"/>
                    <div class="wfj12_008_shop">
                        <div class="wfj12_008_position">
                            <ul>
                                <li>
                                <c:choose>
                                	<c:when test="${contactCompany.logoPath==null||contactCompany.logoPath eq ''}">
                                		<img id="logo_add" src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_addimg_01.png"/>
                                	</c:when>
                                	<c:otherwise>
                                		<img id="logo" src="<%=basePath%>${contactCompany.logoPath}" />
                                	</c:otherwise>
                                </c:choose>
                                </li>
                                <li><a class="verification" href="javascript:;"><input id="pic" type="file" name="contactCompany.lg"/>上传公司logo</a></li>
                            </ul>
                        </div>
                    </div>
                 <div id="prompt" style="width: 100%;display: none;">
        			<center>
        				<div>
        					<span style="position: relative;top: 19.8%;"></span>
        				</div>
        			</center>
        		 </div>          
                    <div class="wfj12_008_info">
                        <div class="wfj12_008_width">
                            <table>
                                <tr>
                                    <td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_02.png" /></td>
                                    <td colspan="2" width="55%">
                                    <c:choose>
                                    <c:when test="${contactCompany.companyName eq null ||contactCompany.companyName eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入公司名称"  name="contactCompany.companyName"/></c:when>
                                    <c:otherwise>
                                    <input class="verification" type="text" name="contactCompany.companyName" value="${contactCompany.companyName }" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司名称';}"/>
                                    </c:otherwise>
                                    </c:choose></td>
                                </tr>
                                <tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_03.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${contactCompany.industryType eq null ||contactCompany.industryType eq ''}" >
                                    <input class="verification" id="indus" type="text" placeholder="请选择行业"  name="contactCompany.industryType"/></c:when>
                                    <c:otherwise>
									<input class="verification" id="indus" type="text" name="contactCompany.industryType" value="${contactCompany.industryType }" />
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_04.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${contactCompany.companyAddr eq null ||contactCompany.companyAddr eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入公司地址"  name="contactCompany.companyAddr"/></c:when>
                                    <c:otherwise>
                                    <input class="verification" type="text" name="contactCompany.companyAddr" value="${contactCompany.companyAddr }" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司地址';}"/>
                                    </c:otherwise>
                                    </c:choose></td>
                                </tr>
                                <tr>
                                    <td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_01.png" /></td>
                                    <td width="55%">
                                    <c:choose>
                                    <c:when test="${contactCompany.cresponsible eq null ||contactCompany.cresponsible eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入公司负责人"  name="contactCompany.cresponsible"/></c:when>
                                    <c:otherwise>
                                    <input class="verification" type="text"  name="contactCompany.cresponsible" value="${contactCompany.cresponsible }" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司负责人';}"/>
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td id="ch_sex" width="30%" align="right">
                                    	<div>
                                        	<ul>
                                            	<li class="left">男</li>
                                            	<li class="right">女</li>
                                            </ul>
                                        </div>
                                    </td>
                                </tr>
								<tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_infophone_02.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${contactCompany.responsibleTel eq null ||contactCompany.responsibleTel eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入手机号码"  name="contactCompany.responsibleTel"/></c:when>
                                    <c:otherwise>
									<input class="verification" type="text" name="contactCompany.responsibleTel" value="${contactCompany.responsibleTel }"onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入手机号码';}"/>                                   
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_06.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${contactCompany.companyWeb eq null ||contactCompany.companyWeb eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入公司网址例如:http://www.impf2010.com"  name="contactCompany.companyWeb"/></c:when>
                                    <c:otherwise>
									<input class="verification" type="text" name="contactCompany.companyWeb" value="${contactCompany.companyWeb }" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司网址例如:http://www.impf2010.com';}"/>
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_tel.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${contactCompany.companyTel eq null ||contactCompany.companyTel eq ''}" >
                                    <input class="verification" type="text" placeholder="请输入公司电话"  name="contactCompany.companyTel"/></c:when>
                                    <c:otherwise>
									<input class="verification" type="text" name="contactCompany.companyTel" value="${contactCompany.companyTel }" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司电话';}"/>
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_07.png" /></td>
                                    <td colspan="2">
                                    <c:choose>
                                    <c:when test="${ccomconf.modalRemark eq null ||ccomconf.modalRemark eq ''}" >
                                    <textarea class="verification" name="ccomconf.modalRemark">请输入公司简介</textarea></c:when>
                                    <c:otherwise>
									<textarea class="verification" name="ccomconf.modalRemark" onfocus="if(this.value!=''){this.value='';}"  onblur="if(this.value==''){this.value='请输入公司简介';}">${ccomconf.modalRemark }</textarea>
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div></form>
                </div>
            </div>

        </div>
		<div class="wfj12_008_bottom">
        	<div id="save">保存信息</div>
        </div>
        
        <div class="tanchu">
        	<div class="industry1">
        		<ul id="industry1">
        		</ul>
        	</div>
        	<div class="industry2" style="display:none;background:#FFF;">
        		<ul id="industry2">
        		</ul>
        	</div>
        </div>
    </div>
    <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
    <script type="text/javascript">
    var basePath='<%=basePath%>';
    var sex='';
    var sex1='${contactCompany.sex}';
    var companyName='${contactCompany.companyName}'
    
    $(document).ready(function(e) {
    	
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
    				$(".wfj12_008_top").css("background-color",activities.describe);
    				$("#save").css("background-color",activities.describe);
    			}
    		},
    		error : function cbf(data){
    			alert("公司风格加载失败！");
    		}
    	});
    	
    	$(".tanchu").attr("style","width:100%;height:"+$(window).height()*0.9+"px;position: fixed;top:7%;display:none;background:#FFF;");
		
    	$(".jqmOverlay").live("click",function(){
			$(".tanchu").fadeOut();
			$("#occlusion2").jqmHide();
		});
    	//弹出层初始化
		$(".jqmWindow").jqm({
			modal : true, 
			overlay : 20
		}).jqmAddClose('.close');
		//显示性别
		if(sex1=='男'){
			$("#ch_sex").find("div").find("li.left").attr("style","background-color:#1F7EC8;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
			sex='男';
		}else{
			$("#ch_sex").find("div").find("li.right").find(".right").attr("style","background-color:#F74C31;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
			sex='女';
		}
		$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");	   
	
		
		$("#logo_add").click(function(){
			$("#pic").click();
		});
		
		$("input[type='file']").live("change",function(){
    	var picPath=$(this);
    	getImgUrl(picPath);
		});

		
	    function getImgUrl($t){	 
			var img=new Image();
			var dw=$("#logo_add").width(),dh=$("#logo_add").height();	  
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
				$("#logo_add").attr("src",da);
			};		
		}	
    });//加载完毕
    


    </script>
</body>
</html>