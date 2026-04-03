<%@ page language="java" 
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,java.sql.*,java.io.*,javax.servlet.*,javax.servlet.http.*;" 
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

    String WorkMode = request.getParameter("WorkMode");

   
   
    
    //***************************卓正PageOffice组件的使用********************************
	PageOfficeCtrl poCtrl1 = new PageOfficeCtrl(request);
	
	poCtrl1.setCustomToolbar(false);
	poCtrl1.setCaption("5L5C");
	poCtrl1.setServerPage(basePath +"poserver.zz"); //此行必须
	poCtrl1.setSaveFilePage(basePath
			+ "ea/uploadfile/ea_saveWord.jspa?docPath=" +docPath);
	
	
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
    if(WorkMode.equals("1")){
    
        poCtrl1.setMenubar(false);//隐藏自定义菜单栏
    	poCtrl1.setOfficeToolbars(false);//隐藏Office工具栏 xlsReadOnly,docReadOnly
    
    	poCtrl1.addCustomToolButton("打印设置","setPrint()",6);
    	poCtrl1.addCustomToolButton("打印预览","setPrintPreview()",6);
    }else{
     
    	 poCtrl1.addCustomToolButton("保存", "Save", 1);
    
    	
      }
    
      poCtrl1.addCustomToolButton("全屏/还原", "IsFullScreen", 4);
	   if (fileType == "W" || fileType.equals("W")) {
		if(WorkMode.equals("1")){
			poCtrl1.webOpen(basePath+docPath,OpenModeType.docReadOnly, "张三");
		}else{
		    poCtrl1.webOpen(basePath+docPath,OpenModeType.docNormalEdit, "张三");
		}
	   } else {
		if(WorkMode.equals("1")){
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
    <title></title>
    <link href="images/csstg.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />	
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript"  src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script charset="utf-8" type="text/javascript" src="<%=basePath%>js/ea/office_ea/document/controlnewdoc.js"></script>
   
			
     
    <script type="text/javascript">
   var basePath="<%=basePath%>";
 
  
    </script>
    <!-- *************************PageOffice组件的使用************************************ -->
    <script type="text/javascript">
    

    
  
    
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
    <form id="form2">

    <div id="content">
        <div id="textcontent" style="width: 100%; height: 800px;">
         
            <!--**************   卓正 PageOffice组件 ************************-->
             <po:PageOfficeCtrl id="PageOfficeCtrl1" />
        </div>
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
						Copyright 2009-2010 www.ttst2010.cn Corporation, All Rights
						Reserved
						<br />
						公司地址：北京市东直门外大街宇飞大厦801室 服务热线：64164005
					</TD>
				</TR>
			</TABLE>
    </div>
    
    
    </form>
    
    
    
</body>
</html>
