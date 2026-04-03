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
        <title>网络硬盘</title>
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
          <style type="text/css">
          	.nob{
          		border:0px;
          	}
          	.zidong{
          	font-size: 10px;
          	color: gray;
          	}
          </style>
        <script src="<%=basePath%>js/ea/office_ea/information/NetDisk.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  netDiskID = '';
         var  token=0;
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
                        <th width="40" align="center">
                            序号
                        </th>
                         <th width="150" align="center">
                            编号		
                        </th>
                         <th width="150" align="center">
                            文件名
                        </th>
                         <th width="150" align="center">
                            文件类型
                        </th>
                         <th width="100" align="center">
                            大小
                        </th> 
                        <th width="150" align="center">
                            修改时间
                        </th>
                        <th width="100" align="center">
                            查看
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${netDiskID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${netDiskID}"/>
                            </td>
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
                            <td>
                                <span id="netDiskNum">${netDiskNum}</span>
                            </td>
                            <td>
                               <span id="netDiskName">${netDiskName}</span>
                            </td>
                            <td>
                                <span id="netDiskType">${netDiskType}</span>
                            </td>
                             <td>
                             	 <span id="netDiskSize">${netDiskSize}</span>
                            </td>
                            <td> 
                                <span id="netDiskDate"><s:date name="netDiskDate" format="yyyy年MM月dd日HH时mm分"/></span>
                                <span id="netDiskKey" style="display:none">${netDiskKey}</span>
                                <span id="netDiskID" style="display:none">${netDiskID}</span>
                                <span id="companyID" style="display:none">${companyID}</span>
                                <span id="organizationID" style="display:none">${organizationID}</span> 
                                <span id="netDiskPath" style="display:none">${netDiskPath}</span> 
                            </td> 
                            <td>
                            	<s:if test="netDiskPath==null||netDiskPath==''">无</s:if>
                            	<s:else> <span id="photo"  onclick="lookImage('${netDiskPath}');"><a href="#">查看</a></span></s:else>
                            </td> 
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/netdisk/ea_getNetDiskList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
 	<div class="contentbannb jqmWindow jqmWindowcss" style="top: 10%;width: 490px;height: 260" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">  
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="5px" cellspacing="10px" name="stafftable" id="stafftable" > 
				    <tr>
				    	<td width="124" height="37" align="right">
				    		编号：
				    	</td>
				    	<td>
				    		<input name="netDisk.netDiskNum" id="netDiskNum" />
				    	</td>
				    	<td width="124" align="right">
				    		文件名：
				    	</td>
				    	<td> 
				    		<input name="netDisk.netDiskName"  id="netDiskName" />
				    	</td>
				    </tr>
				    <tr>
				    	<td width="124"  align="right">
				    		文件类型：
				    	</td>
				    	<td>
				    		<input name="netDisk.netDiskType"  class="zidong" id="netDiskType" contentEditable="false" value="＊根据上传文件获取"/>
				    	</td>
				    	<td width="124"  align="right">
				    		文件大小：
				    	</td>
				    	<td>
				    		 <input name="netDisk.netDiskSize"  class="zidong" id="netDiskSize" contentEditable="false" value="＊根据上传文件获取"/>
				    	</td>
				    </tr>
				     <tr>
				    	<td width="124"  align="right">
				    		修改时间：
				    	</td>
				    	<td>
				    		<input name="netDisk.netDiskDate"  class="zidong" id="netDiskDate" contentEditable="false" value="＊根据上传文件获取"/>
				    	</td>
				    	<td colspan="2" align="right">
				    		 <input type="hidden" name="netDisk.netDiskPath" id="netDiskPath" contentEditable="false"/>
				    		 <input type="file" name="netDisk.netDiskFile"  contentEditable="false"/>
				    	</td> 
				    </tr>
				    <tr>
				    	<td colspan="4" align="center">
				    		 <input type="hidden" name="netDisk.netDiskID" id="netDiskID" />
				            <input type="hidden" name="netDisk.netDiskKey" id="netDiskKey" />
				            <input type="hidden" name="netDisk.companyID" id="companyID" />
				            <input type="hidden" name="netDisk.organizationID" id="organizationID" />
						    <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
				    	</td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div> 
	
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 300px;right: 40%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="260px" id="cataffSearchTable"> 
                	 <tr>
                        <td align="right">
                            编号：
						</td>
                        <td>
                        	 <input name="netDisk.netDiskNum" id="netDiskNum" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            文件名：
						</td>
                        <td>
                        	 <input name="netDisk.netDiskName" id="netDiskName" />
                        </td>
                    </tr> 
                </table>
            <div align="center"> 
              <input type="button" class="input-button" id="searchStaff" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>