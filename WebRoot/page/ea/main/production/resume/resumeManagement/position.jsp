<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    
        <link rel="stylesheet" href="<%=basePath%>css/ea/production/resest.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/mem_center.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/production/resume/resumeManagement/zepto.min.js"></script>

 <title>我关注的职位-编辑</title>

<body>
    <!-- header start  -->
    <header class="mem_header">
        <a id="returnClick" class="back"></a>
        <h1>我关注的职位</h1>
    </header>
    <!--  header end  -->
    <!-- 页面内容 start  -->
    <div class="wrap_page">
        <section>
            <div class="wrap_contain">
                <div class="contain_hd">
                    <div class="contain_hd_L">
                        <i class="checkbox_ico"></i>
                        <span>全部职位（<span class="job_num">${list.size()}</span>）</span>
                    </div>
                    <a href="javascript:;" class="contain_hd_R">
                        <span class="edit_btn">编辑</span><span class="complete_btn">完成</span>
                    </a>
                </div>
                <div class="contain_bd">
                   
                 <c:forEach items="${list}" var="k">
                    <label class="checkbox">
                    
                        <a href="###"></a>
                        <i class="checkbox_ico"></i>
                        <input type="checkbox" id="cokey" value="${k[6]}">
                        <input type="hidden" id="crid" value="${k[7]}">
                        <div class="job_content">
                            <p class="job_name">
                                <span>${k[2]}</span>
                                <span class="job_time">${fn:substring(k[5],0,10)}</span>
                            </p>
                            <p class="job_com">
                                <span>${k[0]}</span>
                                <span class="job_wage">${k[4]}</span>
                            </p>
                            <p>
                                <i class="area_ico"></i><span>${k[1]}</span>
                                <i class="edu_ico"></i><span>${k[3]}</span>
                            </p>
                        </div>

                    </label>
                    </c:forEach>
                </div>
                <div class="contain_fd">
                    <a href="javascript:;" class="dele_btn" onclick="del()"><i></i>删除职位</a>
                   <!--  <a href="javascript:;" class="send_btn" onclick="postResume()">投递简历</a> -->
                </div>
            </div>
        </section>
    </div>
    <!--  页面内容 end -->

    <script>
      var basePath="<%=basePath%>";
  	var jitype="${param.jitype}";
	var sccId="${param.sccId}";
	var back = "${param.back}";
    function del(){
     $("input:checked").each(function(){
     	var _val = $(this).val();
	    var urL = basePath + "ea/resumes/sajax_ea_delPostion.jspa?cokey="+_val+"&sccId="+sccId+"&jitype="+jitype;
		$.ajax({
			url : encodeURI(urL),
			type : "get",
			async : false,
			dataType : "json",
			success : function(data) {
			var url = basePath+"ea/resumes/ea_getPostion.jspa?&sccId="+sccId+"&jitype="+jitype;
			document.location.href=url;
			},
			error : function(data) {
			}
		});
     	
     })
    
    
    }
    //ea/bidrecruit
    function postResume(){
    
    $("input:checked").each(function(){
   
	    var urL = basePath + "ea/resumes/sajax_ea_delPostion.jspa?cokey="+_val+"&sccId="+sccId+"&jitype="+jitype;
			$.ajax({
				url : encodeURI(urL),
				type : "get",
				async : false,
				dataType : "json",
				success : function(data) {
				var urL = basePath + "ea/resumes/sajax_ea_delPostion.jspa?cokey="+_val+"&sccId="+sccId+"&jitype="+jitype;
				document.location.href=url;
				},
				error : function(data) {
				}
			});
	    })
    
    }
        window.onload = window.onresize = function() {
            //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
            //获取窗口的尺寸
            var clientWidth = document.documentElement.clientWidth;
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
        $(function() {
        
       
      //后退
        $("#returnClick").click(function() {

            if(back=="1"){
                document.location.href = basePath+"ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs&current=jl";
            }else {
                history.go(-1)
            }
        });
         
            //初始化
            $("input[type='checkbox']").prop("disabled", true);
            //全选
            function Aselect() {
                $("label.checkbox input").each(function() {
                    this.checked = true;
                    $(this).prev("i").addClass("checkbox_select");
                    $(".contain_hd_L").find("i").addClass("checkbox_select");
                });
            }
            //全不选
            function All_noselect() {
                $("label.checkbox input").each(function() {
                    this.checked = false;
                    $(this).prev("i").removeClass("checkbox_select");
                    $(".contain_hd_L").find("i").removeClass("checkbox_select");
                });
            };

            //判断是否为编辑状态
            $(".contain_hd_R").click(function() {
                var _n = $(".edit").length;
                if (_n == 0) {
                    $(".wrap_contain").addClass("edit");
                    $("input[type='checkbox']").prop("disabled", false);
                    $(".checkbox a").addClass("a_hidden");
                } else if (_n > 0) {
                    $(".wrap_contain").removeClass("edit");
                    $("input[type='checkbox']").prop("disabled", true);
                    $(".checkbox a").removeClass("a_hidden");
                    All_noselect();
                }
            });
            //编辑状态下点击列表
            $("label.checkbox").tap(function() {
                    var _n = $(".edit").length;
                    if (_n > 0) {
                        $(this).find("i").first().toggleClass("checkbox_select");
                        $(".contain_hd_L").find("i").removeClass("checkbox_select");
                    }
                })
                //全选操作
            $(".contain_hd_L").tap(function() {
                var _n = $(".edit").length;
                if (_n > 0) {
                    Aselect();
                }

            });
        })
    </script>
</body>

</html>
