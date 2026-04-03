<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <title>银行交易配置</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/common/easyui/demo.css">
    <script type="text/javascript" src="<%=basePath%>js/common/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/common/easyui/jquery.easyui.min.js"></script>
    <script src="<%=basePath%>js/nwa/bankTransConf.js" type="text/javascript"></script>


<script type="text/javascript">

 var basePath = "<%=basePath%>";
 var enameSx = $("#bank").find("option:selected").val();
 $("table#dg").attr("url",basePath+"");
</script>
</head>
<body>
    <h2>银行交易配置</h2>
    <div class="demo-info" style="margin-bottom:10px">
        <div class="demo-tip icon-tip">&nbsp;</div>
        <div>选择银行<select id="bank"></select></div>
    </div>
    
    <table id="dg" title="交易配置" class="easyui-datagrid" style="width:700px;height:250px"
            url="<%=basePath%>nwa/bank/datagrid_nwa_getTransConfByBank.jspa?transConf.enameSx=ABC"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th field="enameSx" width="80">银行英文缩写</th>
                <th field="transName" width="50">交易名称</th>
                <th field="wbatransCode" width="100">交易代码(网银适配器)</th>
                <th field="btransCode" width="100">交易代码(银行)</th>
                <th field="ver" width="70">版本号</th>
                <th field="verDesc" width="70">版本说明</th>
                <th field="isUsed" width="70">是否使用</th>
                <th field="key" hidden=true></th>
                
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="align:center;width:450px;height:350px;padding:10px 20px "
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">配置信息</div>
        <form id="fm"  method="post"   novalidate >
            <div class="fitem">
                <label>银行英文缩写：</label>
                <input name="transConf.enameSx" id="enameSxs" readonly class="easyui-validatebox" required="true">
                <input name="transConf.enterpriseID" type="hidden" />
                <input name="transConf.confID" type="hidden" />
                <input name="transConf.key" type="hidden" />
            </div>
            <div class="fitem">
                <label>交易名称：</label>
                <input name="transConf.transName" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>交易代码(网银适配器)：</label>
                <input name="transConf.wbatransCode" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>交易代码(银行)：</label>
                <input name="transConf.btransCode" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>版本号：</label>
                <input name="transConf.ver" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>版本说明：</label>
                <input name="transConf.verDesc" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>是否使用：</label>
                <select name="transConf.isUsed"  class="easyui-validatebox" required="true">
                <option value="0" selected>未使用</option><option value="1">已使用</option></select>
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
            width:130px;
        }
    </style>
</body>
</html>