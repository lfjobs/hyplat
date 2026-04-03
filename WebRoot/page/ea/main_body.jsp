<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<link href="<%=basePath%>/css/ea/admin_index.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/main_body.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />	
			
			 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/main_body.css" />		      	
		<script type="text/javascript">
		var basePath="<%=basePath%>";
		var pNumber=${pageNumber};
		var pNumbernews=${pageNumbernews};
		var token=0;
		var notoken = 0;
		var search="${search}";
		var sdate="${sdate}";  
        var edate="${edate}";
	</script>
					<style type = "text/css">   
		    html{-webkit-user-select:none;}html, body, div, p, ul, li, dl, dt, dd, h1, h2, h3, h4, h5, h6, form, input, select, button, textarea, iframe, table, th, td{margin:0;padding:0}img{border:0 none;vertical-align:top;cursor:pointer;}ul, li{list-style-type:none}h1, h2, h3, h4, h5, h6{font-size:14px}body, input, select, button, textarea{font-size:12px;font-family:Tahoma, Geneva, sans-serif}button{cursor:pointer}i, em, cite{font-style:normal}body{background:#fff;color:#363636;line-height:1.2}a, a:link{color:#222;text-decoration:none}a:visited{}a:active, a:hover{text-decoration:underline}a:focus{outline:none}.fixed:after{content:".";display:block;clear:both;height:0;visibility:hidden}.fixed{display:block;min-height:1%}*html .fixed{height:1%}.clear{diplay:block!important;float:none!important;clear:both;overflow:hidden;width:auto!important;height:0!important;margin:0 auto!important;padding:0!important;font-size:0;line-height:0}.more{float:right}.more a{font-weight:normal;font-size:12px}.fl, .fr{float:left}.fr{float:right}
		 .con_head{ width:100%; height:50px;/* background-image:url(<%=basePath%>images/head_bg.png);*/}
		 .con_head span{ display:block; font-size:14px; font-weight:bold; color:#3d5a78; line-height:40px;}
		 .con_head .con_head_span{margin-left:20px;}
		 .con_head_img{float: right; text-align:right; padding-right:100px; margin-left:960px; position: relative;}
		 .con_head_img *{ display:block; float:left; line-height:50px; color:#3d5a78; font-size:20px; font-weight:bold;}
		 .con_head_img label{ line-height:40px; margin-left:10px; margin-right:10px;}		 
		 .wrap{ width:100%; margin-top:40px; height:500px;}
		 .wrap_5_img{ width:100%; height:50px;}
		 .wrap_5_img img{ float:left; width:50px; height:50px; margin-left:85px; margin-right:85px;}
		 .wrap_5_span{ width:100%; height:30px; line-height:30px; margin-top:0px;}
		 .wrap_5_span span{ float:left; width:220px; height:30px; line-height:30px; font-size:20px; font-weight:bold; color:#57aad6; text-align:center;} 
		 .con_r .cursorPoint img{ filter:alpha(opacity=0);-moz-opacity:0;opacity:0; float:left; width:29px;; height:25px; margin-left:100px; margin-right:90px;}
		 .con_r .cursorPoint img.dis{ filter:alpha(opacity=100);-moz-opacity:1;opacity:1; }
		 .table_div{ display:none; width:1106px; height:336px; margin-left:50px; margin-top:-2px; border:1px solid #57aad6;}
		 .table_div.dis{ display:block;}
		 .table{ margin:auto; margin-top:5px; width:1095px; height:265px; border:1px solid #57aad6; background-color:#eee;}
		 .tr_head{ margin:auto; width:1084px; height:35px; line-height:35px;}
		 .tr_head *{ font-weight:bold; color:#57aad6;}
		 .tr{ margin:auto; margin-bottom:2px; width:1086px; height:33px; line-height:35px; border:1px solid #57aad6;}
		 .tr.trs{ background-color:#fff;}
		 .tr_head .td1,.tr .td1{ width:195px; height:35px; text-align:center;  border-right:1px solid #57aad6;}
		 .tr_head .td2,.tr .td2{ width:492px; height:35px; text-align:center;  border-right:1px solid #57aad6;}
		 .tr_head .td3,.tr .td3{ width:200px; height:35px; text-align:center;  border-right:1px solid #57aad6;}
		 .tr_head .td4,.tr .td4{ width:194px; height:35px; text-align:center;}
		 .tr .td2 { text-align:left;}
		 .tr .td2 span{ padding-left:20px; color:#57aad6;}
		 .tableFloor{ margin:auto; margin-top:10px; width:1095px; height:40px;}
		 .tableFloor *{ float:left; display:block;}
		 .tableFloor span{ font-size:14px; font-weight:bold; color:#88d606; text-align:right; padding-right:6px; line-height:40px;}
		 .tableFloor input[type=text]{width: 100px;height: 20px;padding: 5px 4px;font: 16px arial; margin-top:4px;border: 1px solid #d8d8d8;border-bottom: 1px solid #ccc;vertical-align: top;outline: none;}
		 .sou{ margin-left:20px; margin-top:2px;}
		 .page{ width:320px; margin-left:20px;}
		 .page *{  margin-left:4px; margin-top:2px;}
		 .text_jump{ width:60px; height:35px; background-image:url(<%=basePath%>images/text.png);}
		 .text_jump input[type=text]{ width:40px; padding:4px 4px; border:none;vertical-align: top;outline: none;}
		 .download{ display:none; position: absolute; top:30px; left:0px; font-size:12px;width:150px; height:100px;} 
		 .download p{ height:25px;list-style:disc; }  
		 .download a{ font-size:12px; color:#57aad6;}  
   		 </style>
	</head>
	<body style="width:99%;overflow-x:auto;">
		   <div class="con_r fl" style="width:1185px;">
       <div class="con_head fl">
           <div class="con_head_img">
               <!-- <img alt="代办事项" title="代办事项" src="<%=basePath%>images/time.png"/><label></label>&nbsp;&nbsp;&nbsp;&nbsp; -->
               <img id="downPlug-in" alt="插件下载" title="下载插件" src="<%=basePath%>images/down.png" style="width:20px; height:20px;"/>
               <div class="download">
               	<p><a target="_blank" href="<%=basePath%>page/ea/main/telrec/activeSetup/telRecActiveX.rar">电话系统插件</a></p>
                <p><a target="_blank" href="<%=basePath%>page/ea/main/telrec/activeSetup/idCardRead.zip">身份证读卡插件</a></p>
               </div>
               
           </div>
       </div>
       <div class="clear"></div>
       <div class="wrap" id="">
           <div class="wrap_5_img fl">
                <img alt="" title="" src="<%=basePath%>images/indexIco1.png"/>
                <img alt="" title="" src="<%=basePath%>images/indexIco2.png"/>
                <img alt="" title="" src="<%=basePath%>images/indexIco3.png"/>
                <img alt="" title="" src="<%=basePath%>images/indexIco4.png"/>
                <img alt="" title="" src="<%=basePath%>images/indexIco5.png"/>
            </div>
            <div class="clear"></div>
           <div class="wrap_5_span fl">
                <span>人事公共信息</span>
                <span>办公室公共信息</span>
                <span>财务公共信息</span>
                <span>生产公共信息</span>
                <span>营销公共信息</span>
            </div>
            <div class="clear"></div>
           <div class="cursorPoint fl">
                <img alt="" title="" src="<%=basePath%>images/point.png"/>
                <img alt="" title="" src="<%=basePath%>images/point.png"/>
                <img alt="" title="" src="<%=basePath%>images/point.png"/>
                <img alt="" title="" src="<%=basePath%>images/point.png"/>
                <img alt="" title="" src="<%=basePath%>images/point.png"/>
           </div>
           <div class="clear"></div>
           <div class="table_div fl">
               <div class="table">
                   <div class="tr_head">
                       <div class="td1 fl">类型</div>
                       <div class="td2 fl">标题</div>
                       <div class="td3 fl">发布人</div>
                       <div class="td4 fl">发布时间</div>
                   </div>
                   <s:iterator value="pageFormnews.list">
                   <div class="tr trs">
                       <div class="td1 fl"><c:choose>
								<c:when test='${module=="news"}'>新闻</c:when>
								<c:when test='${module=="AnnNoti"}'>公告通知</c:when>
								<c:otherwise>${module}</c:otherwise>
							</c:choose>
						</div>
                       <div class="td2 fl"><span>
                       		<a href="<%=basePath%>ea/documentinfo/ea_getViewDocument.jspa?docId=${docid}&type=toFinishedView&mainbodytype=m"><u>${title}</u> </a>
                       </span></div>
                       <div class="td3 fl">${draftername}</div>
                       <div class="td4 fl">${fn:substring(examinetime,0,19)}</div>
                   </div>
                   </s:iterator>                               
               </div>
               
               <div class="tableFloor">
                   <span style="width:60px;">标题</span>
                   <input name="" type="text">
                   <span style="width:66px;">发布人</span>
                   <input name="" type="text">
                   <span style="width:50px;">时间</span>
                   <input name="" type="text">
                   <span style="width:12px;">-</span>
                   <input name="" type="text">
                   <img class="sou" alt="" title="" src="<%=basePath%>images/sousuo.png"/>
                   <div class="page">
                       <img alt="" title="" src="<%=basePath%>images/zuo.png"/>
                       <img alt="" title="" src="<%=basePath%>images/left.png"/>
                       <div class="text_jump"><input name="" type="text"></div>
                       <img alt="" title="" src="<%=basePath%>images/go.png" />
                       <img alt="" title="" src="<%=basePath%>images/right.png"/>
                       <img alt="" title="" src="<%=basePath%>images/you.png"/>
                   </div>
               </div>
           </div>
           <div class="table_div fl">
           </div>
           <div class="table_div fl">
           </div>
           <div class="table_div fl">
           </div>
           <div class="table_div fl">
           </div>
           	
       </div>
       <div class="clear"></div>
	</body>
</html>
<script type="text/javascript">
	$(document).ready(function(){ 
		$(".wrap_5_img img").each(function(index, element) {
            $(this).attr("index",index);
			$(this).click(function(){
				var num=$(this).attr("index");
				$(".cursorPoint img").each(function(index, element) {
                    if(index==num){
						$(this).attr("class","dis");
					}
					else{
						$(this).attr("class","");
					}
                });
				$(".table_div").each(function(index, element) {
                    if(index==num){
						$(this).attr("class","table_div dis fl");
					}else{
						$(this).attr("class","table_div fl");
					}
                });
			})
		});
		$("#downPlug-in").hover(function(){$(".download").attr("style","display:block;");});	
		$(".download").hover(function(){},function(){$(".download").attr("style","display:none;");});
		
	});
</script>