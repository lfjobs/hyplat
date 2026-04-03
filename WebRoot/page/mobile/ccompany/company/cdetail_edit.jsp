<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司基本信息管理</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>css/ea/register.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
</head>
<script type="text/javascript">
var basePath="<%=basePath%>";
 function save(){
        $(":form").find(":input").trigger("blur");
        $(".validate").trigger("blur");
          if($("form .error").length)
          { 
            return;
          } 
          $("#DetailForm").attr("action","<%=basePath%>ea/ccompany/t_ea_saveCompanyDetail.jspa");
             document.DetailForm.submit.click();
             alert("保存成功！");
    }
    
 

</script>
<body>
<div>
<div class="unitlib_list_right03">
<form name="DetailForm" method="post" id="DetailForm">
<input type="submit" name="submit" style="display:none">
    <div>
         <div class="titlenav1"><span class="td21">基本资料注册</span><span class="td22">(以下各项为必填项)</span></div>
        <div class="conten">
          <ul>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>组织机构：</span>
                <input type="text" name="companyIdentifier" maxlength="50"  class="yname put1" style="color: red;" value="${companyIdentifier}" disabled="disabled"/>
              </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>单位名称：</span>
                <input type="text" name="companyName" id="units.companyName" maxlength="48" class="yname put1" value="${companyName}"/>
              </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>单位地址：</span>
                <input type="text" name="detail.companyAddress" id="detail.companyAddress" maxlength="50" class="yname put2" value="${detail.companyAddress}"/>
              </li>

                <li>
                	<span class="contenspan"><span class="txt05">* </span>负责人：</span>

                  	<input type="text" name="detail.companyManager" id="unitsDetail.companyManager" maxlength="50" class="yname put3" value="${detail.companyManager }"/>
                </li>
                <li>
                	<span class="contenspan">电子邮箱：</span>
                    <input type="text" name="detail.companyEmail" id="unitsDetail.companyEmail" maxlength="50" class="yname email" value="${detail.companyEmail }"/>
                </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>电话:</span>
                    <input type="text" name="detail.companyPhone"  id="unitsDetail.companyPhone" maxlength="50" class="yname phone" value="${detail.companyPhone }" />
                    
                </li>
                 <li>
               	  <span class="contenspan">图片：</span>
                   <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>          
                </li>
                <li>
               	  <span class="contenspan">输入验证码：</span>
                  <input type="text"  class="yname validate" />
                </li>
                      <s:token/>
              </ul>
             </div>
		    
	</div>
	<input  name="detail.detailKey"  type="hidden" class="input01" id="detailKey" size="14" readonly="readonly" value="${detail.detailKey}"/>
	<input  name="detail.detailID"  type="hidden" class="input01" id="regionID" size="14" readonly="readonly" value="${detail.detailID}"/>
</form>

	<div class="mainxuxian"></div>
    	   <div class="conten1">
        	  <ul>
            	<li>
              	<input  type="button" onclick="save()" class="button" value="提 交" />
               </li>
          </ul>
   </div>

</div>
</div>


 
</body>
</html>

