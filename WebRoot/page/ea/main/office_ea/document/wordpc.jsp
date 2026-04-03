<%@ page language="java" 
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
    String fileType=request.getParameter("fileType");
    String docPath = request.getParameter("docPath");
   // String id=request.getParameter("id");
    String isRead = request.getParameter("isRead");
    String stage = request.getParameter("stage");

   
   
    
    //***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	
	poCtrl1.setCustomToolbar(false);
	poCtrl1.setCaption(stage);
	poCtrl1.setServerPage(basePath +"poserver.zz"); //此行必须
	poCtrl1.setSaveFilePage(basePath
			+ "ea/zoffice/ea_saveOffice.jspa?docPath=" +docPath);
   
	poCtrl1.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
	
	poCtrl1.getRibbonBar().setSharedVisible("FileSave", false);
	
	//poCtrl1.setCustomMenuCaption("自定义菜单");
    //poCtrl1.addCustomMenuItem("显示痕迹", "ShowRevisions", true);
    //poCtrl1.addCustomMenuItem("隐藏痕迹", "HiddenRevisions", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
    // poCtrl1.addCustomMenuItem("显示标题", "ShowTitle", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
   // poCtrl1.addCustomMenuItem("领导签批", "InsertHandSign", true);
   // poCtrl1.addCustomMenuItem("插入印章", "InsertSeal", true);
   // poCtrl1.addCustomMenuItem("接受所有修订", "AcceptAllRevisions", true);
    //poCtrl1.addCustomMenuItem("-", "", false);
    //poCtrl1.addCustomMenuItem("分层显示手写批注", "ShowHandDrawDispBar", true);
    
    poCtrl1.setCustomToolbar(true);
    if(isRead.equals("1")){
    
        poCtrl1.setMenubar(false);//隐藏自定义菜单栏
    	poCtrl1.setOfficeToolbars(false);//隐藏Office工具栏 xlsReadOnly,docReadOnly
    if(!stage.equals("查看")){
    	//poCtrl1.addCustomToolButton("打印设置","setPrint()",6);
    	poCtrl1.addCustomToolButton("打印预览","setPrintPreview()",6);
    	poCtrl1.addCustomToolButton("打印","directPrint()",6);
    	
    }
    }else{
     
       poCtrl1.addCustomToolButton("保存", "Save", 1);
     if(stage.equals("未审批")){
    	 poCtrl1.addCustomToolButton("领导签批", "InsertHandSign", 3);
    	 poCtrl1.addCustomToolButton("领导圈阅", "StartHandDraw", 3);
    	 poCtrl1.addCustomToolButton("插入键盘批注", "StartRemark", 3);
    	 poCtrl1.addCustomToolButton("显示/隐藏痕迹", "Show_HidRevisions", 5);
    	 poCtrl1.addCustomToolButton("接受所有修订", "AcceptAllRevisions", 5);
         poCtrl1.addCustomToolButton("分层显示手写批注", "ShowHandDrawDispBar", 7);
      }
     if(stage.equals("未盖章")){
    	 poCtrl1.setZoomSealServer("http://123.57.28.135:8816/ZoomSeal/poserver.zz");
    	 poCtrl1.addCustomToolButton("插入印章/签名", "InsertSeal", 2);
    	 poCtrl1.addCustomToolButton("插入键盘批注", "StartRemark", 3);
    	 poCtrl1.addCustomToolButton("显示/隐藏痕迹", "Show_HidRevisions", 5);
    	 poCtrl1.addCustomToolButton("接受所有修订", "AcceptAllRevisions", 5);
         poCtrl1.addCustomToolButton("分层显示手写批注", "ShowHandDrawDispBar", 7);
        
      }
       //poCtrl1.addCustomToolButton("另存为Html", "SaveAsHtml", 0);
     
        
      }
      //poCtrl1.getRibbonBar().setSharedVisible("FileSave", false);

	if(stage.equals("拟稿")||stage.equals("收件箱")||stage.equals("设置模板")){
		poCtrl1.setAllowCopy(true);
	}else{
		poCtrl1.setAllowCopy(false);
	}
      poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
      poCtrl1.setJsFunction_BeforeDocumentSaved("BeforeDocumentSaved()");
	   if (fileType == "W" || fileType.equals("W")) {
		if(isRead.equals("1")){
			poCtrl1.webOpen(basePath+docPath,OpenModeType.docReadOnly, "张三");
		}else{
		    poCtrl1.webOpen(basePath+docPath,OpenModeType.docNormalEdit, "张三");
		}
	   } else {
		if(isRead.equals("1")){
			poCtrl1.webOpen(basePath+docPath,OpenModeType.xlsReadOnly, "张三");
		}else{
		    poCtrl1.webOpen(basePath+docPath,OpenModeType.xlsNormalEdit, "张三");
		}
		

	}
	
	
	poCtrl1.setTagId("PageOfficeCtrl1"); //此行必须				  							  
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title>3333</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />	
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript"  src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script charset="utf-8" type="text/javascript" src="<%=basePath%>js/ea/office_ea/document/controlnewdoc.js"></script>


     
    <script type="text/javascript">
   var basePath="<%=basePath%>";
   var stage = "<%=stage%>";


    </script>
    <!-- *************************PageOffice组件的使用************************************ -->
    <script type="text/javascript">

    
    
    
  //保存前
    function BeforeDocumentSaved() {


        return true;
    }
    
    
    function Save() {

    	
        document.getElementById("PageOfficeCtrl1").WebSave();


    }
    //打印设置
    function setPrint() {
        document.getElementById("PageOfficeCtrl1").ShowDialog(5);
    }
    //打印预览
    function setPrintPreview(){
    	document.getElementById("PageOfficeCtrl1").PrintPreview(); 

    }
    function directPrint(){
       // document.getElementById("PageOfficeCtrl1").PrintOut();
        document.getElementById("PageOfficeCtrl1").ShowDialog(4);
    }

    function ShowRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = true;
    }
    function HiddenRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = false;
    }
    function Show_HidRevisions() {
        document.getElementById("PageOfficeCtrl1").ShowRevisions = !document.getElementById("PageOfficeCtrl1").ShowRevisions;
    }

    //领导圈阅签字
    function StartHandDraw() {
        document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
        document.getElementById("PageOfficeCtrl1").HandDraw.Start();
    }

    //接受所有修订
    function AcceptAllRevisions() {
        document.getElementById("PageOfficeCtrl1").AcceptAllRevisions();
    }

    //分层显示手写批注
    function ShowHandDrawDispBar() {
        document.getElementById("PageOfficeCtrl1").HandDraw.ShowLayerBar();
    }

    //全屏/还原
    function IsFullScreen() {
        document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
    }

    //显示菜单
    function ShowTitle() {
        alert("该菜单的标题是：" + document.getElementById("PageOfficeCtrl1").caption);
    }

    //插入电子印章
    function InsertSeal() {
        //alert("请使用此用户的印章测试\r\n用户名：李志 \r\n初始密码：111111");

        var zoomseal = document.getElementById("PageOfficeCtrl1").ZoomSeal;
        if (zoomseal != null)
            zoomseal.AddSeal();


    }
    // 签批
    function InsertHandSign() {
        //alert("请使用此用户测试\r\n用户名：李志 \r\n初始密码：111111");

        var zoomseal = document.getElementById("PageOfficeCtrl1").ZoomSeal;
        if (zoomseal != null)
            zoomseal.AddHandSign();
    }

    //文档另存为Html，并发布到web服务器
    function SaveAsHtml() {
        document.getElementById("PageOfficeCtrl1").WebSaveAsMHT();
        window.open("htmldoc.jsp?type=word&id=");
    }
    
    //领导圈阅签字
    function StartHandDraw() {
        document.getElementById("PageOfficeCtrl1").HandDraw.SetPenWidth(5);
        document.getElementById("PageOfficeCtrl1").HandDraw.Start();
    }
	// 插入键盘批注
    function StartRemark() {
        var appObj = document.getElementById("PageOfficeCtrl1").WordInsertComment();

    }	


    </script>
    <!-- *************************PageOffice组件的使用************************************ -->
</head>
<body>

    <div id="content">
        <div id="textcontent" style="width: 100%; height: 800px;">
         
            <!--**************   卓正 PageOffice组件 ************************-->
             <po:PageOfficeCtrl id="PageOfficeCtrl1" />
        </div>
		<!--   OBJECT标签，客户端控件引用    -->
		<OBJECT id="SOAOfficeCtrl"
				codeBase="<%=basePath%>js/cabs/ZSOffice.ocx#version=2,0,0,1"
				height="100%" width="100%"
				classid="clsid:AD06827C-D92F-4648-B880-138AF11E8A13" data=""
				VIEWASTEXT>
			<div align=center STYLE="color: red;">
				本机尚未安装公文OFFICE组件，请安装浏览器上方黄色提示条或弹出提示框中的公文OFFICE组件。
			</div>
		</OBJECT>
    </div>
    <div id="footer">
        <hr width="1000" />
        <div>
         <TABLE width="80%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD width="100%" align="center" style="font-size: 15px;">
						<strong>京ICP备10034132号-2 版权所有 北京天太世统科技有限公司</strong>
						<br />
						Copyright 2009-2023 www.impf2010.com Corporation, All Rights
						Reserved
						<br />
						公司地址：北京市东直门外大街宇飞大厦801室 服务热线：64164005
					</TD>
				</TR>
			</TABLE>
    </div>
    
    


		 <script type="text/javascript">
		 
        function AfterDocumentOpened() {
     
            document.getElementById("PageOfficeCtrl1").SetEnableFileCommand(4, false); // 禁止另存
        
          
        }
    </script>
</body>
</html>
