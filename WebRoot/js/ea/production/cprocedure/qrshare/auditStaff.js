$(function(){
	$(document).on('click','.staffdiv',function(){

		var staffID =  $(this).find(".staffID").val();
		var staffName = $(this).find(".staffname").val();
		var position  = $(this).find(".position").val();
		var companyName= $("#companyName").val();
		var orgName =  $("#orgName").val();
		var companyID =  $("#comID").val();
		var orgID =  $("#orgID").val();
		var auid = $("#auid").val();
		 $.ajax({
		     	url:basePath + "ea/workmessage/sajax_ea_transferMessage.jspa",
		     	type:"post",
		     	async : false,
		     	dataType : "json",
		     	data:{
		     		"auditRecord.auditID":staffID,
		     		"auditRecord.auditName":staffName,
		     		"auditRecord.auditOrgID":orgID,
		     		"auditRecord.auditOrgName":orgName,
		     		"auditRecord.auditComID":companyID,
		     		"auditRecord.auditComName":companyName,
		     		"auditRecord.position":position,
		     		"auditRecord.auid":auid,
		     		"auditRecord.auditComment":comment
		     		
		     	},
		     	success:function(data){	
		     	  var da  = eval("(" + data + ")")
		     	  var result=da.result;
		     	  if(result=="success"){
		     		  
                   document.location.href =  basePath + "ea/workmessage/ea_taskMessage.jspa";
		     	  }
		     	
		     		
		     	},error:function(data){
		     		alert("查询失败！");
		     	}
		    });
	});
	
	
	
	  //搜索框点击
    $(".tinfo_over").click(function() {
        $(this).hide();
        $(".taskinfo_search").focus();
    });
    //搜索，失去焦点事件
    $(".taskinfo_search").blur(function() {
            if ($(this).val() == '') {
                $(".tinfo_over").show();
                $(".person_list").hide();
                $(".department").show();
                $(".head_R").hide().removeClass("add_btn");
            }
        })
        //搜索获取焦点事件
    $(".taskinfo_search").focus(function() {
            $(".person_list").show();
            $(".department").hide();
            $(".head_R").show();
        })
        //点击公司展开-折叠部门
    $(document).on("click", ".c_nav", function() {
            $(this).next().slideToggle(200)
                .end().find("i").toggleClass("c_arr_b")
                .parents(".c_box").siblings(".c_box").find("i").removeClass("c_arr_b")
                .end().find(".c_section_wrap").slideUp(200);
        })
        //点击搜索出列表
    $(document).on('click', '.p_search', function() {
            $(this).find("i").addClass("select_ico").end().siblings(".p_search").find("i").removeClass();
            $(".head_R").show().addClass("add_btn");
        })
        //点击部门内人员
    $(document).on('click', '.dep_box', function() {
            $(this).find("i").addClass("select_ico").end().siblings(".dep_box").find("i").removeClass();
            $(".head_R").show().addClass("add_btn");
        })
        //搜索
    $(".taskinfo_search").on("input", function() {
        if ($(this).val() == '') {
            $(".person_list").hide();
            $(".department").show();
            $(".head_R").hide().removeClass("add_btn");
        }
    })

});