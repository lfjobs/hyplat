<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<html lang="en">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style01.css"/>
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_mien_add.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/jquery.Jcrop.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/jquery.Jcrop.css"/>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var select=0;
	var $this;
	var editType="${editType}";
	var backurl="<%=backurl%>";
</script>
<title>添加兴趣风采</title>
</head>
<body class="bgcolorFFF">
	<div class="wfj01_001">
        <form method="post" id="form" name="form"  enctype="multipart/form-data">
        	<input type="submit" id="submit" name="submit" style="display: none;">
        	<iframe name="hidden"  style="display: none;"></iframe>
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="#" target="_self" id="superior"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>添加兴趣风采</li>
            	<li class="edit"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
        <div class="wfj01_001_title">
        	<div>
            	<table>
                	<tr>
                    	<td width="25%" align="center"><img class="wfj01_001_hyimg" src="<%=basePath%>${obj[2]==null?'images/ea/websitemall/card/no_pictures2.png':obj[2]}" /></td>
                    	<td width="15%">${obj[1]}<input type="hidden" id="staffId" value="${obj[0]}" name="staffId"></td>
                    	<td width="20%"><img class="wfj01_001_ewm" src="<%=basePath%>images/ea/websitemall/card/wfj_ewm_02.png" /></td>
                    	<td width="40%" align="right"><div>${obj[3]=='3'?'合伙人商城业主会员':
                    					obj[3]=='4'?'微分金商城业主会员':
                    					obj[3]=='5'?'代理商商城业主会员':'客户'}</div></td>
                    </tr>
                </table>
            </div>
        </div>
        
        
        <div class="wfj01_001_content">
        	<div class="wfj01_001_hidden">
            	<div class="wfj01_001_con">
                	<div class="wfj01_001_width">
                    	<div class="wfj01_001_depict">
                        	<textarea placeholder="风采描述......" name="elaborate"></textarea>
                        </div>
                    	<div class="wfj01_001_con_title">
                        	<div class="wfj01_001_width">
                            	<ul>
                                	<li class="left"><span>上传到</span></li>
                                	<li class="right">我的风采库</li>
                                </ul>
                            </div>
                        </div>
						
                        <div class="wfj01_001_con_right widths">
                        	<ul>
                            	<li><img src="<%=basePath%>images/ea/websitemall/card/wfj_addimg_02.png" /></li>
                            </ul>
                        </div>
                         <div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="intercept">
		              	<div style="width: 80%;height: 40%;background-color: #FFFFFF;position: relative;top: 15%;left: 10%;" id="interceptDiv">
		              		<div id="operation" style="position: relative;top: -14%;text-align: right;height:0px;">
		              		<span id="determine" style="display:-moz-inline-box;display:inline-block;position:relative;right:10%">
		              		    <img src="<%=basePath%>images/ea/websitemall/card/choice_01.png"> 
		              		</span>
		              		<span id="cancel">
		              		    <img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png"> 
		              		</span>
		              		</div>
		              		<img id="interceptImg" >
		              	</div>
		              </div> 
                        
                        <div id="pictureDiv" style="display: none;">
                           <input type="file" accept="image/*" id="file"/>
                           <input type="hidden" id="wheelbase">
	                        <div class="wfj01_001_con_left widths"  id="picture">
	                        	<ul>
	                            	<li class="wfj01_001_bigimg"><img/></li>
	                            	<li class="wfj01_001_deleteimg"><img src="<%=basePath%>images/ea/websitemall/card/wfj_delete_02.png" /></li>
	                            </ul>
	                        </div>
                        </div>
                    </div>
                </div>
                <div class="wfj01_001_bottom">
                    <div class="wfj01_001_bottom_width">
                        保存信息
                    </div>
                </div>
            </div>
        </div>
        </form>
        <div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="QRCode">
    	<div style="background-color: #FFFFFF;position: relative;top: 15%;left: 20%;" id="QRCodeDiv">
    		<img id="interceptImg" src="<%=basePath%>${obj[4]}" style="width: 100%;height: 100%;">
    	</div>
    </div> 
    </div>
</body>
</html>