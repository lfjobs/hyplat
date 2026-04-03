<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmx" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>产品设计推广管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>						
		<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />		
	
		<script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/ueditor/ueditor.config.js"></script>
	    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/ueditor/ueditor.all.min.js"></script>
	    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	    <script type="text/javascript" charset="utf-8" src="<%=basePath%>/page/ueditor/lang/zh-cn/zh-cn.js"></script>
	    
	</head>
	<body>
		<div class="jqmWindow jqmWindow4UEditor" style="height: 700px;overflow: scroll;display: block;">
		    <h1 style=" font-size:12px;">请在此处编辑产品展示说明</h1>
		    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
		    <div id="btns">
			    <div>
			        <button onclick="getContent()">保存内容</button>
			    </div>
			</div>
		</div>
		<input type="hidden" name="productDesign.ppID" id="ppID" value="${productDesign.ppID }"/>
	</body>	
	<iframe src="" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no" frameBorder="0"></iframe>
<script type="text/javascript">
	 var basePath='<%=basePath%>'; 
	 var editType='<%=request.getParameter("editType")%>';
	 $(document).ready(function(){//ready()是在DOM解析完成   onload是在页面全部加载完成之后
	    	if (editType=="html"){
	    		var productDetail = '${productDesign.productDetail}';    		
	    		if (productDetail!=''){
	    			$.ajax(
				        {
				         	type: "POST",
				         	url: basePath + "ea/productdesign/ea_getProductdesignAddorEdit.jspa?",
				         	data: {			         			
				         			"productDesign.productDetail":productDetail,
				         			"actionName":"getContentFromFile"
				         		  }, 
				         	dataType:"text",
				         	success:function(data)
				         	{	         		
				         		setContent(data,false);
				         	},
				         	error:function(data)
				         	{
				         		alert("系统发生异常,请尝试刷新.");	         		
				         	}
				        }
			        );
	    		}
	    		
	    	}else
	    	{
	    	}    	
	    });  

    //保存内容
    function getContent() {
        var content = UE.getEditor('editor').getContent(); 
        var ppID = $("#ppID").val();        
        
        $.ajax(
	        {
	         	type: "POST",
	         	url: basePath + "ea/productdesign/ea_addProductdesign.jspa?",
	         	data: {
	         			"htmlContent":content,
	         			"productDesign.ppID":ppID,
	         			"actionName":"saveContentToFile"
	         		  }, 
	         	dataType:"text",
	         	success:function(data)
	         	{	         		
	         		alert("内容保存完毕.");
	         	},
	         	error:function(data)
	         	{
	         		alert("系统发生异常,请联系管理员.");	         		
	         	}
	        }
        );        
    }
    
    //将内容付给UE控件
    function setContent(content,isAppendTo) {        
        UE.getEditor('editor').setContent(content, isAppendTo);        
    }
    
    </script>
    
    <script type="text/javascript">
    	//实例化编辑器
    	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    	var ue = UE.getEditor('editor');
    </script>
    
	</body>
</html>
