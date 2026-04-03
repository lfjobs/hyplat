<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache" /> 
<meta http-equiv="Cache-Control" content="no-cache" /> 
<title>注册</title>
<link href="<%=basePath%>css/ea/register.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
 

<style type="text/css">
<!--
.roundedCorners{
width:935px;
height:540px;
padding: 10px;
background-color: #FFF;
border:1px solid #83A2C6;
margin:0px auto;
margin-top:5px;
 
/* Do rounding (native in Safari, Firefox and Chrome) */
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
 
}
</style>


<script type="text/javascript">
  var basePath="<%=basePath%>";
  var pass;
  document.onkeydown = function(evt){//捕捉回车   
    evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    if (key == 13) { //判断是否是回车事件。
       save();
    }
}
  function save()
  {
      pass = '1';
      $("form :input").trigger("blur");
      $(".validate").trigger("blur");
          if($("form .error").length)
          { 
            return;
          }     
     $('#Loginform').attr('action', "<%=basePath%>register1.jspa");
    document.Loginform.submit.click();
     alert("注册成功");
  }
  
  function login(){
		  window.location.href="<%=basePath%>page/ea/login1.jsp";
  }
</script>
</head>
<body>
<div class="main">
	<div class="conter">
        <div class="logo_zc"></div>
        <div class="logo_right"></div>
        <div class="logo_rightwz">注册</div>
    </div>
    <div class="roundedCorners">
    	<div class="titlenav1"><span class="td21">账号注册</span>
        <span class="td22">(以下<span class="txt05"> * </span>为必填项)</span></div>
        	 <form name="Loginform" id="Loginform"  method="post" >
        <div class="conten"><input type="submit" name="submit" style="display:none"/>
          <ul>
              <li>
              	<span class="contenspan"><span class="txt05"> * </span>组织机构名：</span>
                <input type="text" name="company.companyIdentifier"  class="yname company" />            
              </li>
              <li>
              	<span class="contenspan"><span class="txt05"> * </span>用户名：</span>sa
              </li>
               <li>
              	<span class="contenspan"><span class="txt05"> * </span>密码：</span>
                <input type="password" name="account.accountPassword"  class="yname  password" />            
              </li>
               <li>
              	<span class="contenspan"><span class="txt05"> * </span>确认密码：</span>
                <input type="password" name="ddd"  class="yname password1" />            
              </li>
           </ul>
           </div>
        <div class="titlenav1"><span class="td21">基本资料注册</span><span class="td22">(以下各项为必填项)</span></div>
        <div class="conten">
          <ul>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>政府名称：</span>
                <input type="text" name="company.companyName"  class="yname put1" />
              </li>
               <li>
              	<span class="contenspan"> <span class="txt05">* </span>政府类型：</span>
              	  <s:select list="#{'10':'政府'}"  name="company.companyType" theme="simple" >
                  </s:select>
              </li>
              <li>
              	<span class="contenspan"> <span class="txt05">* </span>政府地址：</span>
                <input type="text" name="companyDetail.companyAddress"  class="yname put2" />
              </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>负责人：</span>
                  	<input type="text" name="companyDetail.companyManager" class="yname put3" />
                </li>
                <li>
                	<span class="contenspan">电子邮箱：</span>
                    <input type="text" name="companyDetail.companyEmail"  class="yname email" />
                </li>
                <li>
                	<span class="contenspan"><span class="txt05">* </span>电话:</span>
                    <input type="text" name="companyDetail.companyPhone"  class="yname phone" />
                    
                </li>
                <li>
               	  <span class="contenspan">图片：</span>
                   <img border="0" name="validateImage" onclick="this.src='<%=basePath%>page/ea/security_code.jsp?abc='+Math.random()" src="<%=basePath%>page/ea/security_code.jsp"/>          
                </li>
                <li>
               	  <span class="contenspan">输入验证码：</span>
                  <input type="text"  class="yname validate" />
                  <s:token/>
                </li>
              </ul>
             </div>
		    </form>
		        <div class="conten1">
		          <ul>
		            <li>
		              <input  type="button" onclick="save()" class="button" value="提 交" />
		              <input  type="button"  onclick="login()" class="button01" value="返 回" />
		            </li>
		          </ul>
		   </div>
  </div>
    </div>
     <div class="footer">北京天太世统科技有限公司 版权所有   服务电话：010-64167113</div>
    	
</body>
</html>



