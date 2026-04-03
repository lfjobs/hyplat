<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>IP控制</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/demo.css">
    <script type="text/javascript" src="<%=basePath%>js/common/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/easyui/jquery.easyui.min.js"></script>
    <script src="<%=basePath%>js/nwa/ipControlConf.js" type="text/javascript"></script>


<script type="text/javascript">

 var basePath = "<%=basePath%>";
 var enameSx = $("#bank").find("option:selected").val();
 $("table#dg").attr("url",basePath+"");
</script>
</head>
<body>
    <h2>IP控制</h2>
    <table id="dg" title="IP控制" class="easyui-datagrid" style="width:700px;height:250px"
            url="<%=basePath%>nwa/bank/datagrid_nwa_getIpConfByEnterID.jspa"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="ipAddr" width="80">IP地址</th>
   
                
                
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="align:center;width:380px;height:250px;padding:10px 20px "
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">Ip信息</div>
        <form id="fm"  method="post"   novalidate >
            <div class="fitem" >
                <label>IP地址：</label>
                <input name="ipConf.ipAddr" id="IpAddr"  class="easyui-validatebox" required="true">
                <input name="ipConf.enterpriseID" type="hidden" />
                <input name="ipConf.ipcID" type="hidden" />
                <input name="ipConf.key" type="hidden" />
            </div>
            
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
    </div>

    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:50px;
        }
    </style>
</body>
</html>