//新增物品、物品管理切换
$(document).on("click",".gl_tab li",function(){
    $(this).parent().children("li").removeClass("active");
    $(this).addClass("active");
    $("#tab_div>div").hide();
    $("#tab_div>div").eq($(this).index()).show();
})
// 商品点击选中
$(document).on("click",".ul_con aside",function(){
    if($(this).is(".aside_yes")){
        $(this).removeClass().addClass("aside_no");
    }else{
        $(this).removeClass().addClass("aside_yes");
    }
})

//往来公司
$(document).on("click","#div_wlgs_table tr",function(){
    if($(this).find(".img_no").is(":hidden")){
        $(this).find(".img_no").show();
        $(this).find(".img_yes").hide();
        $(this).removeClass("active");
    }else{
        $("#div_wlgs_table aside .img_yes").hide();
        $("#div_wlgs_table aside .img_no").show();
        $("#div_wlgs_table tr").removeClass("active");
        $(this).find(".img_no").hide();
        $(this).find(".img_yes").show();
        $(this).addClass("active");
    }
})

//往来个人
$(document).on("click","#div_wlgr_table tr",function(){
    if($(this).find(".img_no").is(":hidden")){
        $(this).find(".img_no").show();
        $(this).find(".img_yes").hide();
        $(this).removeClass("active");
    }else{
        $("#div_wlgr_table aside .img_yes").hide();
        $("#div_wlgr_table aside .img_no").show();
        $("#div_wlgr_table tr").removeClass("active");
        $(this).find(".img_no").hide();
        $(this).find(".img_yes").show();
        $(this).addClass("active");
    }
})

//点击往来公司触发弹框
$(document).on("click","#ttsw_wlgs_show",function(){
    initWlGsInfo();
})

//点击往来个人触发弹框
$(document).on("click","#ttsw_wlgr_show",function(){
    initWlGrInfo();
})

/**
 * 初始化往来公司信息
 * @param num
 */
function initWlGsInfo(){
    pagenumber = 1;
    var search = $('#ttsw_wlgs_search_id').val();//取录入的模糊查询条件
    var url = basePath+"ea/scBudget/sajax_ea_ajaxWlGsList.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data : {
            "search":search,
        },
        dataType: "json",
        success: function cbf(data){
            var member = (new Function("return " + data))();//格式化返回参数
            var pageForm = member.pageForm;
            //拼接html页面
            var innerHtml = '<tr>'
                +'<th colspan="2"></th>'
                +'<th>往来单位名称</th>'
                +'<th>往来关系</th>'
                +'</tr>';
            if(pageForm != null){
                pagecount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var pageNum = pageForm.list.length-1;
                getHeight(0);//启动定时器刷新下拉距离刷新用
                innerHtml += wlGsHtml(pageForm,pageNum);
            }else {
                innerHtml += nullHtml();
            }
            $('#ttsw_wlgs_tk_id').html(innerHtml);
            //初始化显示页面
            $(".div_wlgs_name").show();
            $("body").addClass("body_yc");
        },error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}

/**
 * 初始化往来个人信息
 * @param num
 */
function initWlGrInfo(){
    pagenumber = 1;
    var search = $('#ttsw_wlgr_search_id').val();//取录入的模糊查询条件
    var url = basePath+"ea/scBudget/sajax_ea_ajaxWlGrList.jspa";
    $.ajax({
        url: url,
        type: "POST",
        data : {
            "search":search,
        },
        dataType: "json",
        success: function cbf(data){
            var member = (new Function("return " + data))();//格式化返回参数
            var pageForm = member.pageForm;
            //拼接html页面
            var innerHtml = '<tr>'
                +'<th colspan="2"></th>'
                +'<th>往来名称</th>'
                +'<th>往来关系</th>'
                +'</tr>';
            if(pageForm != null){
                pagecount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var pageNum = pageForm.list.length-1;
                getHeight(1);//启动定时器刷新下拉距离刷新用
                innerHtml += wlGrHtml(pageForm,pageNum);
            }else{
                innerHtml += nullHtml();
            }
            $('#ttsw_wlgr_tk_id').html(innerHtml);
            //初始化显示页面
            $(".div_wlgr_name").show();
            $("body").addClass("body_yc");
        },error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}

/**
 * 根据数据拼接往来公司
 * @param oList 异步获取信息
 * @param pageNum 总数据数-1
 * @returns {string} 返回拼接好的td 页面
 */
function wlGsHtml(oList,pageNum){
    var innerHtml = '';
    for (var i = 0; i < oList.list.length; i++) {
        obj = oList.list[i];
        innerHtml += (i+1) !=  pageNum ? '<tr class="ttsw_last">':'<tr>';
        innerHtml += '<td colspan="2" id="'+obj.contactConnectionID+'">'
                            +'<aside class="aside_no">'
                                +'<img class="img_no" src="'+basePath+'images/scMobile/payBudget/addBudget/wupinguanli_07.png"/>'
                                +'<img class="img_yes" src="'+basePath+'images/scMobile/payBudget/addBudget/wupinguanli_10.png"/>'
                            +'</aside>'
                        +'</td>'
                        +'<td>'+ obj.companyName +'</td>'
                        +'<td>'+ obj.contactConnections +'</td>'
                      +'</tr>';
    }
    return innerHtml;
}

/**
 * 无数据拼接页面
 */
function nullHtml(){
    var innerHtml = '<tr><td colspan="4">未查询到数据</td></tr>';
    return innerHtml;
}

/**
 * 根据数据拼接往来个人
 * @param oList 异步获取信息
 * @param pageNum 总数据数-1
 * @returns {string} 返回拼接好的td 页面
 */
function wlGrHtml(oList,pageNum){
    var innerHtml = '';
    for (var i = 0; i < oList.list.length; i++) {
        obj = oList.list[i];
        innerHtml += (i+1) !=  pageNum ? '<tr class="ttsw_last">':'<tr>';
        innerHtml += '<td colspan="2" id="'+obj.staffID+'">'
                            +'<aside class="aside_no">'
                                +'<img class="img_no" src="'+basePath+'images/scMobile/payBudget/addBudget/wupinguanli_07.png"/>'
                                +'<img class="img_yes" src="'+basePath+'images/scMobile/payBudget/addBudget/wupinguanli_10.png"/>'
                            +'</aside>'
                        +'</td>'
                        +'<td>'+ obj.staffName +'</td>'
                        +'<td>'+ obj.relation +'</td>'
                      +'</tr>';
    }
    return innerHtml;
}

/**
 * 定时刷新判断页面距底高度
 */
function getHeight(flag){
    clearTimeout(timer);//清空定时器
    timer =setTimeout("getHeight("+flag+")", 1000);
    if($(".ttsw_last").length>0){
        //li偏移量-li的3倍高度<=页面高度时
        if($(".ttsw_last").offset().top-$(".ttsw_last").height()*3<=$(window).height()){
            if(pagenumber<pagecount){
                if(flag == 0){//往来公司
                    ajaxWlGsInfo();
                }else{//往来个人
                    ajaxWlGrInfo();
                }
            }
        }
    }
}

/**
 * 异步获取往来公司加载数据
 */
function ajaxWlGsInfo(){
    pagenumber += 1;
    var search = $('#ttsw_wlgs_search_id').val();//取录入的模糊查询条件
    var url = basePath+"ea/scBudget/sajax_ea_ajaxWlGsList.jspa";
    $.ajax({
        url : url,
        type : "POST",
        async : true,//默认设置为true，所有请求均为异步请求
        data : {
            "pageForm.pageNumber":pagenumber,
            "search":search,
        },
        dataType : "json",
        success : function(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var pageForm = member.pageForm;
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            var innerHtml = '';
            if(pageForm!=null) {
                pagecount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var pageNum = pageForm.list.length-1;
                $(".ttsw_last").removeClass("ttsw_last");
                innerHtml += wlGsHtml(pageForm,pageNum);
            }else {
                innerHtml += nullHtml();
            }
            $('#ttsw_wlgs_tk_id').append(innerHtml);
        },
        error:function(data){
            alert("加载下一页失败");
        }
    });
}

/**
 * 异步获取往来个人加载数据
 */
function ajaxWlGrInfo(){
    pagenumber += 1;
    var search = $('#ttsw_wlgr_search_id').val();//取录入的模糊查询条件
    var url = basePath+"ea/scBudget/sajax_ea_ajaxWlGrList.jspa";
    $.ajax({
        url : url,
        type : "POST",
        async : true,//默认设置为true，所有请求均为异步请求
        data : {
            "pageForm.pageNumber":pagenumber,
            "search":search,
        },
        dataType : "json",
        success : function(data) {
            var member = (new Function("return " + data))();//格式化返回参数
            var pageForm = member.pageForm;
            $(".ttsw_last").removeClass("ttsw_last");//清空样式
            var innerHtml = '';
            if(pageForm!=null) {
                pagecount = pageForm.pageCount;
                count = pageForm.recordCount;
                pageSize = pageForm.pageSize;
                var pageNum = pageForm.list.length-1;
                $(".ttsw_last").removeClass("ttsw_last");
                innerHtml += wlGrHtml(pageForm,pageNum);
            }else{
                innerHtml += nullHtml();
            }
            $('#ttsw_wlgr_tk_id').append(innerHtml);
        },
        error:function(data){
            alert("加载下一页失败");
        }
    });
}

//将选中的往来公司在父级页面呈现
$("#div_wlgs_table").height(($(".div_wlgs_name").height()-$(".header").outerHeight(true)-$(".button").outerHeight(true))*0.835+"px");
$("#p_wlgs_add").click(function(){
    clearTimeout(timer);//暂时定时器
    timer = null;
    var ppid = $("#div_wlgs_table").find(".active").children("td:nth-of-type(1)").attr("id");
    $("#ttsw_wlsj_check_id").val(ppid);
    var wlgsName = $("#div_wlgs_table").find(".active").children("td:nth-of-type(2)").text();
    $("#ttsw_wlgs_text").text(wlgsName);
    $("#ttsw_wlsj_check_name").val(wlgsName);
    $(this).parents(".div_wlgs_name").hide();
    $("body").removeClass("body_yc");
});

//将选中的往来个人在父级页面呈现
$("#div_wlgr_table").height(($(".div_wlgr_name").height()-$(".header").outerHeight(true)-$(".button").outerHeight(true))*0.835+"px");
$("#p_wlgr_add").click(function(){
    clearTimeout(timer);//暂时定时器
    timer = null;
    var ppid = $("#div_wlgr_table").find(".active").children("td:nth-of-type(1)").attr("id");
    $("#ttsw_wlgr_check_id").val(ppid);
    var wlgrName = $("#div_wlgr_table").find(".active").children("td:nth-of-type(2)").text();
    $("#ttsw_wlgr_text").text(wlgrName);
    $("#ttsw_wlgr_check_name").val(wlgrName);
    $(this).parents(".div_wlgr_name").hide();
    $("body").removeClass("body_yc");
});

