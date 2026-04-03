<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>QQ管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
                <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/microletter/microletter.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  microlettermenuid = '';
         var  token=0;
         var  microlettermenupid='${dtMicroletterMenu.microlettermenupid}';
         var  treename='${param.treename}';
         
         function getValueForParm(id){ //打开页面
     	  	$("#ifr").attr("src",basePath+"ea/microlettercontent/ea_getDtMicroletterMenuContentList.jspa?dtMicroletterMenu.microlettermenukey="+id);
     	  	$("#jqmWindow2").jqmShow();
     	}
     	$(document).ready(function() {
     		$("#isBack").click(function(){// 返回
     	       $("#jqmWindow2").jqmHide();
     	    }); 
     	});
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="60" align="center">
                            序号
                        </th>
                         <th width="120" align="center">
                            菜单名称		
                        </th>
                        <th width="60" align="center">
                            菜单等级
                        </th>
                         <th width="160" align="center">
                            创建时间
                        </th>
                         <th width="160" align="center">
                            更新时间
                        </th>
                         
                       <th width="60" align="center">
                            菜单状态
                        </th>
                      	<th width="200" align="center">
                            菜单备注
                        </th>
                        <th width="60" align="center">
                            详细内容
                        </th> 
                         
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${microlettermenuid}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${microlettermenuid}"/>
                            </td>
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                            <td>
                                <span id="microlettermenuname">${microlettermenuname}</span>
                            </td>
                             <td>
                                <span id="microlettermenulevel">${microlettermenulevel=='10'?'一级':'二级'}</span>
                            </td>
                            <td>
                               <span id="microlettermenucdate">${fn:substring(microlettermenucdate,0,11)}</span>
                            </td>
                            <td>
                                <span id="microlettermenuudate">${fn:substring(microlettermenuudate,0,19)}</span>
                            </td>
                            <td>
                                <span id="microlettermenustatusValue">${microlettermenustatus=='10'?'正常':'停用'}</span>
                                <span id="microlettermenustatus" style="display: none;" >${microlettermenustatus}</span>
                            </td>
                            <td>
                                <span id="microlettermenutext">${microlettermenutext}</span>
                                 <span id="microlettermenukey" style="display:none">${microlettermenukey}</span>
                                <span id="microlettermenuid" style="display:none">${microlettermenuid}</span>
                                <span id="microlettermenupid" style="display:none">${dtMicroletterMenu.microlettermenupid}</span>
                                <span id="companyid" style="display:none">${companyid}</span>
                            </td>    
                           <td>
                           	<a href="#" onclick="getValueForParm('${microlettermenukey}')">编辑</a>
                           </td>
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/microletter/ea_getListDtMicroletterMenuAll.jspa?pageNumber=${pageNumber}&search=${search}&dtMicroletterMenu.microlettermenupid=${dtMicroletterMenu.microlettermenupid }&treename=${param.treename}">
                </c:param>
            </c:import>
        </div> 
 	<div class="contentbannb jqmWindow " style="top: 10%;width: 600px;right: 30%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="600"  height="103"> 
				    <tr>
				    	<td width="100" height="37" align="right">
				    		菜单名称：
				    	</td>
				    	<td width="250">
				    		<input name="dtMicroletterMenu.microlettermenuname" id="microlettermenuname" class="ckTextLength put3"
										maxlength="30" size="10"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		菜单等级：
				    	</td>
				    	<td width="150"> 
				    		 <input type="hidden" name="dtMicroletterMenu.microlettermenulevel" id="microlettermenulevel"  size="10"/>
				    		 <span id="microlettermenulevelValue" ></span>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="100" height="37" align="right">
				    		创建时间：
				    	</td>
				    	<td >
				    		<input name="dtMicroletterMenu.microlettermenucdate" value="自动" id="microlettermenucdate" size="10" readonly="readonly"/>
				    	</td>
				    	<td width="100" height="37" align="right">
				    		更新时间：
				    	</td>
				    	<td >
				    		 <input name="dtMicroletterMenu.microlettermenuudate" value="自动" id="microlettermenuudate"  size="10" readonly="readonly"/>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="100" height="37" align="right">
				    		菜单状态：
				    	</td>
				    	<td colspan="3" >
				    		<s:select list="#{'10':'正常','11':'停用'}" id="microlettermenustatus" listKey="key" listValue="value" name="dtMicroletterMenu.microlettermenustatus" theme="simple"></s:select>
				    	</td>
				    </tr>
				    <tr>
				    	<td width="100" height="37" align="right">
				    		菜单备注：
				    	</td>
				    	<td colspan="3" >
				    		 <textarea cols="60" rows="5" style="resize: none;overflow-y: scroll;" id="microlettermenutext" name="dtMicroletterMenu.microlettermenutext" class="ckTextLength"
										maxlength="800">
				    		 </textarea>
				    	</td>
				    </tr>
				    <tr>
				    	<td colspan="4" align="center" >
				    		<input type="text" name="dtMicroletterMenu.microlettermenuid" id="microlettermenuid" style="display:none"/>
				            <input type="text" name="dtMicroletterMenu.microlettermenukey" id="microlettermenukey" style="display:none"/>
				            <input type="hidden" name="dtMicroletterMenu.microlettermenupid" id="microlettermenupid" value="${dtMicroletterMenu.microlettermenupid}"/>
				            <input type="hidden" name="dtMicroletterMenu.companyid" id="companyid" />
						    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
				    	</td>
				    </tr>
				    		
				  </table>
				   <s:token></s:token> 
			</form>
	</div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
   
   <!-- 从当前部门的员工中选择责任人 -->
<div id="jqmWindow2" class="jqmWindow"  align="center"
	style="width: 900px; height: 450px; absolute; display: none; left: 5%; top: 10%; background: #eff">
		<iframe name="ifr" id="ifr" width="900px" height="400px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
 </div>
   
    </body>
</html>