<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>My JSP 'queryCustomerList.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>page/ea/main/telrec/js/flexigrid.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/jsp/script/customerManager.js"></script>
		<LINK href="<%=basePath%>/jsp/css/admin.css" type="text/css"
			rel="stylesheet" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript">
			var basePath="<%=basePath%>";
		</script>
	</head>
	<body>
		<table id="flex1" style="display: none">
		</table>
		<input id="hidden" type="hidden" name="hidden" value="null" />
		<script>   
       $("#flex1").flexigrid   
       (   
       {   
      url: '<%=basePath%>telrecjson/webQueryCustomerAction.do',   
     dataType: 'json',   
         colModel : [
        //	{ display : 'ID', name : 'id',width : 100,align : 'center'},
			{ display : '姓名', name : 'customer_name',width : 100,align : 'center'},
			{ display : '电话', name : 'customer_tel',width : 100,align : 'center'},
			{ display : '手机', name : 'customer_mobile',width : 100,align : 'center'},
			{ display : '类型', name : 'customer_type',width : 100,align : 'center'},
			{ display : '单位', name : 'customer_unit',width : 100,align : 'center'},
			{ display : '地址', name : 'customer_address',width : 100,align : 'center'},
			{ display : '传真', name : 'customer_fax',width : 100,align : 'center'},
			{ display : '电邮', name : 'customer_email',width : 100,align : 'center'},
			{ display : '宅电', name : 'customer_hometel',width : 100,align : 'center'},
			{ display : '生日', name : 'customer_birthday',width : 100,align : 'center'},
			{ display : '职称', name : 'customer_title',width : 100,align : 'center'},
			{ display : '邮编', name : 'customer_postcode',width : 100,align : 'center'},
			{ display : '备注', name : 'customer_memo',width : 100,align : 'center'}
//			{ display : '拼音', name : 'spell',width : 100,align : 'center'},
//			{ display : 'customer_companyid', name : 'customer_companyid',width : 100,align : 'center'},
//			{ display : 'rela_companyid', name : 'rela_companyid',width : 100,align : 'center'},
//			{ display : 'rela_companyid_name', name : 'rela_companyid_name',width : 100,align : 'center'},
//			{ display : 'user_id', name : 'user_id',width : 100,align : 'center'},
//			{ display : 'user_name', name : 'user_name',width : 100,align : 'center'}
         ],
        buttons : [   
         {name: '添加', bclass: 'add', onpress : button},
             /*  {name: '删除', bclass: 'delete', onpress : button},   */   
            {name: '修改', bclass: 'modify', onpress : button}, 
             {name: '通话记录', bclass: 'tellist', onpress : button},                  
            {separator: true}   
            ],   
       sortname : "id",
		        sortorder : "asc",
		        usepager : true,
		        title : '客户列表',
		        useRp : true,
		        checkbox : true,// 是否要多选框
		        rowId : 'id',// 多选框绑定行的id
		        rp : 10,
		        showTableToggleBtn : true,
		        width : 'auto',
		        height : 400
        }   
        );   
           
        function button(com,grid)   
        {              
            if (com=='添加')   
                {   
                    $("hidden").value="add";
                    window.location.href="<%=basePath%>page/ea/main/telrec/insertCustomerInfo.jsp";   
                }   
            else if (com=='修改')   
            {   
                $("hidden").value="modify";   
                if($(".trSelected").length==1){   
                    window.location.href="<%=basePath%>page/ea/main/telrec/insertCustomerInfo.jsp?customerid="+$('.trSelected',grid).find("input").eq(0).val();   
                }else if($(".trSelected").length>1){   
                    alert("请选择一个修改,不能同时修改多个");   
                }else if($(".trSelected").length==0){   
                    alert("请选择一个您要修改的客户信息"); 
                }   
            }else if(com='tellist'){
                if($(".trSelected").length==1){   
                    window.location.href="<%=basePath%>page/ea/main/telrec/telrecStep2.jsp?id="+$('.trSelected',grid).find("input").eq(0).val();
                }else if($(".trSelected").length>1){   
                    alert("请选择一个查询,不能同时查询多个");   
                }else if($(".trSelected").length==0){   
                    alert("请选择一个您要查询的客户");   
                }
            }   
        }    
        </script>
	</body>
</html>
