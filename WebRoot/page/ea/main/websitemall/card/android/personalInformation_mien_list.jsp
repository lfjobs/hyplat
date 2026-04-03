<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@ page language="java" pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style12.css"/>
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_mien_list.js" type="text/javascript"></script>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var editType="${editType}";
	var backurl="<%=backurl%>";
</script>
<title>兴趣风采信息</title>
</head>

<body class="bgcolorFFF">
<form method="post" id="form" name="form" >
 <input type="submit" id="submit" name="submit" style="display: none;">
     <iframe name="hidden"  style="display: none;"></iframe>
	<div class="wfj12_025">
        
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li class="wfj_return"><a href="#" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>${obj[1]} - 兴趣风采</li>
            	<li  class="edit display"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj12_025_title">
        	<div>
            	<table>
                	<tr>
                    	<td width="25%" align="center"><div id="photo"><img class="wfj12_025_hyimg" src="<%=basePath%>${obj[2]==null?'images/ea/websitemall/card/no_pictures2.png':obj[2]}" /></div></td>
                    	<td width="15%">${obj[1]}<input type="hidden" id="staffId" name="staffId" value="${obj[0]}"></td>
                    	<td width="20%"><img class="wfj12_025_ewm" src="<%=basePath%>images/ea/websitemall/card/wfj_ewm_02.png" /></td>
                    	<td width="40%" align="right"><div>${obj[3]=='3'?'合伙人商城业主会员':
                    					obj[3]=='4'?'微分金商城业主会员':
                    					obj[3]=='5'?'代理商商城业主会员':'客户'}</div></td>
                    </tr>
                </table>
            </div>
        </div>
        
        <div class="wfj12_025_content">
        	<div class="wfj12_025_hidden">
            	<div class="wfj12_025_con">
                	<div class="wfj12_025_width">
                    	<div class="wfj12_025_con_title">风采展示</div>
                        <c:forEach items="${list}" var="l" varStatus="a">
                         <div class="${a.index%2==0?'wfj12_025_con_left':'wfj12_025_con_right'} wfj12_025_con_data">
                        	<ul>
                            	<li class="wfj12_025_bigimg"><img src="<%=basePath%>${l[0]}" /></li>
                            	<li class="wfj12_025_deleteimg"><img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png" /></li>
                            	<input type="hidden" class="describe" value="${l[1]}">
                            	<input type="hidden" class="materialID" value="${l[2]}">
                            </ul>
                       	 </div>
                        </c:forEach> 
                    </div>
                </div>
                <div class="wfj12_025_bottom display">
                    <div class="wfj12_025_bottom_width" id="mien_add">
                        添加展示我的风采
                    </div>
                </div>
                        <input type="hidden" id="mien_remove" name="photoRemove">
                <div class="wfj12_025_popimg">
                    <div class="wfj12_025_pop_content">
                        <ul>
                            <li><img src="" /><p></p></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div> 
    </div>
    <div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="QRCode">
    	<div style="background-color: #FFFFFF;position: relative;top: 15%;left: 20%;" id="QRCodeDiv">
    		<img id="interceptImg" src="<%=basePath%>${obj[4]}" style="width: 100%;height: 100%;">
    	</div>
    </div> 
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   </form>
</body>
</html>