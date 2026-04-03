<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="java.util.Date"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人产品</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"

	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/shengwei/product.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<script type="text/javascript"
	src="<%=basePath%>js/ckeditor/ckeditor.js"></script>
<script src="<%=basePath%>swfupload/swfupload.js"></script>
<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>
<script src="<%=basePath%>js/ea/wechat/wechatMenuchanpinadd.js"></script>

<script type="text/javascript">
	var basePath='<%=basePath%>';
	var token = 0;
	var menuId = '';      
	var menuPid = '${menuPid}';
	var pNumber = '${pageNumber}';
	<%
	
	   String ref=request.getHeader("REFERER");
	%>

</script>
<script type="text/javascript" src="../../../js/WFJClient/topMore.js"></script>
</head>

<body>
    <div class="product_manage">
	<s:token></s:token>
	<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
        <div class="product_tit">
        <div style="display: none;">
				<input type="submit" />
					<input id="tuplogo" type="file" accept="image/gif, image/jpeg,image/BMP,image/png" name="goodsManage.fileLogo"/>
					<input id="tupphoto" type="file" accept="image/gif, image/jpeg,image/BMP,image/png" name="goodsManage.filePhoto"/>
			</div>      
            <ul>
                <li class="product_sym"><a id="return" href="javascript:void(0);" onclick="fanhui()">《</a></li>
                <li class="product_name">产品修改管理</li>
            </ul>
        </div>
        <div class="pro_name">
            <ul>
                <li>产品名称：<input type="text" name="goodsManage.goodsName" id="goodsname" value=${goodsManage.goodsName }/></li>
            </ul>
        </div>
        <div class="pro_upload">
            <div class="pro_logo">logo：</div>
            <div>
                <ul>
                    <li id="uploads2">
                        <div>浏览</div>
                    </li>
                    <li onclick="chuan('logo')">
                        <div>上传</div>
                    </li>
                </ul>
            </div>
                <%-- <img src="" onclick="shangchuan('logo')" /> --%>
			<div contenteditable="" id="post_article_content2" style="border:0px solid #000; width: 45px;height: 35px;overflow: hidden;">
	 		<img type="image" id="log2" src="<%=basePath%>images/pro_pic.png" style="width: 45px;height: 35px;"/>
				
				<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
					<tbody id="divFileProgressContainer2">
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<input type="hidden" id="hidIdList2"/>
							</td>
						</tr>
					</tfoot><br/>
				</table>
			</div>
            <div class="pro_logo pro_zhu">主题图：</div>
            <div>
                <ul>
                	<li id="uploads3">
                        <div>浏览</div>
                    </li>
                    <li onclick="chuan('zhuti')">
                        <div>上传</div>
                    </li>
                </ul>
            </div>
            <div contenteditable="" id="post_article_content3" style="border:0px solid #000; width: 45px;height: 35px;overflow: hidden;">
		 		<img type="image" id="log3" src="<%=basePath%>images/pro_pic.png" style="width: 45px;height: 35px;"/>
						
				<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
					<tbody id="divFileProgressContainer3">
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<input type="hidden" id="hidIdList3"/>
							</td>
						</tr>
					</tfoot><br/>
				</table>
			</div>
        </div>
        <div class="pro_text">
            <div>
                <ul>
                    <li>产品类型：</li>
                    <li>        
                        <s:select list="codeList" class="yz" listKey="codeID" listValue="codeValue" name="productPackaging.weiDianType" id="unit" ></s:select>
                        </li>
                </ul>
                <ul>
                    <li>是否可被代理：</li>
                    <li><div>
                        <input class="wei_left" id="Radio1" type="radio" name="productPackaging.ppstatus" value="0" checked="checked"/><span>是</span>
                        <input class="wei_right" id="Radio2" type="radio" name="productPackaging.ppstatus" value="1" /><span>否</span>
                        </div>
                    </li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>规格：</li>
                    <li><input type="text" name="goodsManage.standard" value="${goodsManage.standard }"/></li>
                </ul>
                <ul>
                    <li>类型：</li>
                    <li>
                        <input type="text"  name="goodsManage.typeID" value="${goodsManage.typeID }"/></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>单价：</li>
                    <li><input type="text" name="productPackaging.price" value="${productPackaging.price }"/></li>
                </ul>
                <ul>
                    <li>金额：</li>
                    <li>
                        <input type="text" name="productPackaging.money" value="${productPackaging.money }"/></li>
                </ul>
            </div>
         
            
            <div>
                <ul>
                    <li>代理商：</li>
                    <li><input type="text" name="profitshare.agent" value="${profitshare.agent }"/></li>
                </ul>
                <ul>
                    <li>微分金店：</li>
                    <li>
                        <input type="text" name="profitshare.shop" value="${profitshare.shop }"/></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>公司：</li>
                    <li><input type="text" name="profitshare.company" vlaue="${profitshare.company }"/></li>
                </ul>
                <ul>
                    <li>合伙人：</li>
                    <li>
                        <input type="text" name="profitshare.partner" value="${profitshare.partner }"/></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>营销员：</li>
                    <li><input type="text" name="profitshare.salesman" value="${profitshare.salesman }"/></li>
                </ul>
                <ul>
                    <li>消费者积分：</li>
                    <li>
                        <input type="text" name="profitshare.integral" value="${profitshare.integral }"/></li>
                </ul>
            </div>
            <div>
                <ul>
                    <li>责任人：</li>
                    <li class="pro_txt"><input type="text" readonly="readonly"/></li>
                </ul>
                <ul>
                    <li>包装日期：</li>
                    <li class="pro_txt">
                        <input type="text"  value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd"/>" readonly="readonly"/></li>
                </ul>
            </div>
        </div>
        <div class="pro_bottom">
            <div style="margin-left:90px; " onclick="tijiao()">发布产品</div>
            <div>返回</div>
            	<iframe name="hidden" height="0" width="0"></iframe>
        </div>
        <div style="display: none;">
        
          <iframe name="hidden" height="0" width="0"></iframe>
       		<s:token></s:token></div>
        </form>
    </div>
    
    <script type="text/javascript">
   var SWFUpload2_id={SWFObj:new Object()}
    function SWFUpload2_load(){
    String.prototype.trim = function(){
             return this.replace(/(^\s*)|(\s*$)/g, "");
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:""
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload2_uploadSuccess,
            button_image:"<%=basePath%>/images/ty-pro.png",
            //button_text: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;浏览',
            button_width:66,
            button_height:16,
            button_placeholder_id:"uploads2", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer2",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList2",//隐藏域
                uploadMode: "LIST"//?
            },
           
        }
        SWFLoad(SWFUpload2_id,LoadSettings);
    }
    addLoadEvent(SWFUpload2_load);
    function SWFUpload2_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        var hidIdList=$("#hidIdList2").val();
    	var result=hidIdList.split(",");
    	var str="";
    	log2.parentNode.removeChild(log2); 
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='margin-top:-50px;width: 45px;height:35px;'/></center>";
    	}
    	 $("#post_article_content2").append(str);
    	 
    }
    function uploads2(){
    	var swf = SWFUpload1_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
    }
    </script>
    
    <script type="text/javascript">
   var SWFUpload3_id={SWFObj:new Object()}
    function SWFUpload3_load(){
    String.prototype.trim = function(){
             return this.replace(/(^\s*)|(\s*$)/g, "");
             }
        var LoadSettings = {
            post_params:{
		                    path: "/upload_files/office/filemanage/",
		                    fn:"",
		                    small:"false",
		                    sw:"30",
		                    sh:"80",
		                    wm:"True",
		                    data:""
                        },
            file_size_limit: "2 MB",
		    file_types: "*.jpg;*.gif;*.png;*.bmp",
		    file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
		    file_upload_limit: 1,
		    button_action:SWFUpload.BUTTON_ACTION.SELECT_FILES,//点击按钮将会打开多文件上传的对话框
		    button_window_mode : SWFUpload.WINDOW_MODE.OPAQUE,//在页面上显示swf不透明的
		    button_disabled : false,//是否禁用按钮
		    upload_success_handler:SWFUpload3_uploadSuccess,
            <%-- button_image:"<%=basePath%>/images/wup_02.png", --%>
            button_image:"<%=basePath%>/images/ty-pro.png",
            //button_text: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;浏览',
            button_width:66,
            button_height:16,
            button_placeholder_id:"uploads3", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer3",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList3",//隐藏域
                uploadMode: "LIST"//?
            },
           
        }
        SWFLoad(SWFUpload3_id,LoadSettings);
    }
    addLoadEvent(SWFUpload3_load);
    function SWFUpload3_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        var hidIdList=$("#hidIdList3").val();
    	var result=hidIdList.split(",");
    	var str="";
    	log3.parentNode.removeChild(log3); 
    	for(var i=0;i<result.length-1;i++){
    		str+="<center><image src='<%=basePath%>"+result[i]+"' style='margin-top:-50px;width: 45px;height:35px;'/></center>";
    	}
    	 $("#post_article_content3").append(str);
    	 
    }
    function uploads3(){
    	var swf = SWFUpload3_id.SWFObj;
		if (swf != null && swf.getStats().files_queued > 0) {
			swf.startUpload();
		}else{
		 alert("请添加文件");
		 return;
		}
    }
    function dialogOpen() {
    }
    </script>
    <script>
    $().ready(function(){
    	$("select[name='productPackaging.weiDianType'] option[value='${productPackaging.weiDianType}']").attr('selected','selected');
    	$("input[name='productPackaging.ppstatus'][value='${productPackaging.ppstatus}'][type='radio']").attr("checked","checked");
    });
    </script>
</body>
