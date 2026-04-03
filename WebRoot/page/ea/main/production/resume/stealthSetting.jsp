<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<link href="<%=basePath%>css/ea/production/stylezt.css"
	rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/ea/production/resume/jquery-2.1.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/production/resume/javascript.js" type="text/javascript"></script>

<title>默认设置</title>

<script type="text/javascript">
	var basePath="<%=basePath%>";
	var privacy = "${persion.privacy}";
	var resumeID = "${param.resumeID}";
	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
</script>
</head>
<body>
    <div class="res_top">
    <input type="hidden" id="staffid" value="${param.staffid }">
        <ul>
            <li><a><img src="<%=basePath%>images/resume/left_jt.png" id="returnClick"></a></li>
            <li>默认设置</li>
            <li></li>
        </ul>
    </div>
    <div class="res_bot">
        <%--<div class="res_bot_grd pri-set_grd">--%>
            <%--<p>发布求职信息</p>--%>
            <%--<div class="pri-set_div">--%>
                <%--<div id="each">--%>
                    <%--<p class="pri-set_p" id="ok">是</p>--%>
                    <%--<p class="pri-set_p" id="no">否</p>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="clearfix"></div>--%>
        <%--</div>--%>
        
        
        <div class="res_bot_grd pri-set_grd">
            <p style="padding: 0.6rem 0;width:100%;">设置默认简历<span style="font-size:0.55rem;">（投递时使用的简历，只可设置一份）</span></p>
            <div class="clearfix"></div>
        </div>
        <div class="res_bot_grd pri-set_grd pri-set_grd2" style="padding:0;" id="div1">
           <c:forEach items="${list }" var="app">
            <a onclick="choose(this)">
                <input type="hidden" class="isDefault" value="${app.isDefault }">
                <input type="hidden"  id="resumeName" value="01">
                <input type="hidden"  id="resumeID" value="${app.resumeID }">
                <p id="pp">${app.resumeName!=null&&app.resumeName!=""?app.resumeName:"个人简历" }</p>
            </a>
            </c:forEach>
            <div class="clearfix"></div>
        </div>
       
    </div>
<script type="text/javascript">
        $(document).ready(function(){

            $(".isDefault").each(function () {
                if($(this).val()=="01"){
                    $(this).parent().find("#pp").attr("style","background: url('"+basePath+"images/resume/gou.png') 14rem 0.8rem no-repeat;background-size: 0.9rem;").siblings().attr("style","background:#fff;");
                    return;
                }
            })
            if(privacy=="00"){
                $("#ok").attr("style","background:#9ECCE2;color:#fff;");
            }else{
            $("#no").attr("style","background:#9ECCE2;color:#fff;");
            }
          //返回
            $("#returnClick").click(function() {
                /* var staffid= $("#staffid").val();

                document.location.href = basePath+"ea/resumes/ea_savePersion.jspa?staffid="+staffid+"&type=&resumeID="+resumeID; */
                history.go(-1);
                }

            )
               $("#each p").click(function(){
                            var  privacy=$(this).text();
                            var p = "";
                            if(privacy=="是"){
                                  p="00"
                                  ch(p);
                            }else{

                                  p="01"
                                yinsi(p);
                            }

             });
            });
            function yinsi(p){
             var staffid= $("#staffid").val();
             var url = basePath+"ea/resumes/sajax_ea_savePersion.jspa?privacy="+p+"&staffid="+staffid+"&type=ajax&resumeID="+resumeID;
                $.ajax({
                url : encodeURI(url),
                type : "get"
                });
                }
            function ch(p){
            var staffid= $("#staffid").val();
            var url = basePath+"ea/resumes/sajax_ea_queryPersion.jspa?staffid="+staffid+"&type=fina";

             $.ajax({
                url : encodeURI(url),
                type : "get",
                async : false,
                dataType : "json",
                success : function(data) {
                    var member = eval("(" + data + ")");
                    var obj1=member.obj1;
                    var obj2=member.obj2;
                    var obj3=member.obj3;
                    var obj4=member.obj4;
                    if(obj1!=null&&obj2!=null&&obj3!=null&&obj4!=null){

                    yinsi(p);
                    }else{
                        alert("请您完善简历信息");
                    }

            },
            error : function(data) {

            }
        });
        
        }


        function choose(obj){
            var resumeID = $(obj).find("#resumeID").val();
            var staffid= $("#staffid").val();
            var url = basePath+"ea/resumes/sajax_ea_updateIsDefault.jspa?resumeID="+resumeID+"&staffid="+staffid;
            $.ajax({
                url : encodeURI(url),
                type : "POST",
                async:false,
                dataType:"json",
                success:function (data) {
                    $(".pri-set_grd2").find("p").attr("style","background:#fff;")
                    $(obj).find("p").attr("style","background: url('"+basePath+"images/resume/gou.png') 14rem 0.8rem no-repeat;background-size: 0.9rem;")
                }
            });
        }
    </script>

</body>
</html>