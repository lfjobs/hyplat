<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*,java.awt.*,java.util.*,java.net.*" errorPage="" %>
<%@ page import="com.zhuozhengsoft.ZSOfficeX.*, java.awt.*"%>
<jsp:useBean id="ZSCtrl" scope="page" class="com.zhuozhengsoft.ZSOfficeX.ZSPDFCtrl"></jsp:useBean>

<%request.setCharacterEncoding("UTF-8");%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@page pageEncoding="UTF-8"%>
<%



// 设置ZSOFFICE服务页面  因为basePath在这里获取有问题；所以采用了判断方式；
if(basePath.contains("impf")){
	
 ZSCtrl.ServerURL = "/zsserver.doo";
	
}else{
 ZSCtrl.ServerURL = "/hyplat/zsserver.doo";
}

ZSCtrl.Caption = "只读模式";
ZSCtrl.MainStyle = 2;
ZSCtrl.BorderStyle = 0;
String docpath =  request.getParameter("docpath");
// 打开文档

if(basePath.contains("impf")){
	
	ZSCtrl.webOpen("/"+docpath);
	
}else{
	ZSCtrl.webOpen("/hyplat/"+docpath);
}

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>北京天太胜威集团</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<div id="textcontent" style=" height:800px;"> 
    <script language="JavaScript" event="OnDocumentOpened(str, obj)" for="ZSPDFCtrl">
        // 控件打开文档后立即触发，添加自定义菜单，自定义工具栏，禁止打印，禁止另存，禁止保存等等
        ZSPDFCtrl.AppendToolButton(2, "显示/隐藏书签");
        ZSPDFCtrl.AppendToolButton(3, "-");
        ZSPDFCtrl.AppendToolButton(4, "适合页面", 3);
        ZSPDFCtrl.AppendToolButton(5, "实际大小", 3);
        ZSPDFCtrl.AppendToolButton(6, "适合宽度", 3);
        ZSPDFCtrl.AppendToolButton(7, "-");
        ZSPDFCtrl.AppendToolButton(8, "首页",0);
        ZSPDFCtrl.AppendToolButton(9, "上一页",0);
        ZSPDFCtrl.AppendToolButton(10, "下一页", 0);
        ZSPDFCtrl.AppendToolButton(11, "末页", 0);
        ZSPDFCtrl.AppendToolButton(12, "-");
        ZSPDFCtrl.AppendToolButton(13, "缩小", 0);
        ZSPDFCtrl.AppendToolButton(14, "放大", 0);
        //ZSPDFCtrl.AppendToolButton(15, "Language");
        ZSPDFCtrl.AppendToolButton(16, "全屏/还原", 4);

    </script>
    <script language="JavaScript" event="OnCustomToolBarClick(index, caption)" for="ZSPDFCtrl">
        // 添加您的自定义工具栏按钮事件响应
        if(index == 2) ZSPDFCtrl.BookmarksVisible = !ZSPDFCtrl.BookmarksVisible;
        if(index == 4) ZSPDFCtrl.SetPageFit(2);
        if(index == 5) ZSPDFCtrl.SetPageFit(1);
        if(index == 6) ZSPDFCtrl.SetPageFit(3);
        if(index == 8) ZSPDFCtrl.GoToFirstPage();
        if(index == 9) ZSPDFCtrl.GoToPreviousPage();
        if(index == 10) ZSPDFCtrl.GoToNextPage();
        if(index == 11) ZSPDFCtrl.GoToLastPage();
        if(index == 13) ZSPDFCtrl.ZoomOut();
        if(index == 14) ZSPDFCtrl.ZoomIn();
        if(index == 15) ZSPDFCtrl.ShowLanguageBox();
        if(index == 16) ZSPDFCtrl.FullScreen = !ZSPDFCtrl.FullScreen;
   </script>
   <%=ZSCtrl.getDocumentView("ZSPDFCtrl", request)%>
       
</div>
</body>
</html>
