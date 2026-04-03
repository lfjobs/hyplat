$(document).ready(function() {
	//页面加载时运行以下方法
	ajax(); 
});
function getHeight(){
	t=setTimeout("getHeight()", 200);
	if($(".last").offset().top<$(window).height()){	
		if(pageNumber<pageCount){
			ajax();
		}
	}
}
function ajax() {
	surl1=basePath+"ea/carSchool/sajax_ea_recommendFriend.jspa";
	if(pageNumber==null){
		pageNumber = 1;
	}else{
		pageNumber = parseInt(pageNumber)+1;
	}
	$.ajax({
		url:encodeURI(surl1),
	 	type:"post",
	 	data:{"pageForm.pageNumber":pageNumber,"pageForm.pageSize":7},
	  	dataType:"json",
	   	async:false, 
	   	success:function (data) {
			var result = eval("(" + data + ")");
			$(".last").removeClass("last");
			var friend="";
			if(result.pageForm==null){
				friend+="<section class='share_wrap'>";
				friend+="<div class='share_img outer_img'>";
				friend+="<img src='"+basePath+"images/ea/production/drive/share_img.png'>";
				friend+="</div>";
				friend+="<div class='share_text'>你现在还没有推荐好友赚钱~<br>赶快推荐分享吧！</div>";
				friend+="<a href='javascript:;' class='share_btn share_mbtn' data-modal='share_modal'>推荐分享</a>";
				friend+="</section>";
				friend+="<!--弹窗 开始-->";
				friend+="<div class='overlay' id='overlay'>";
				friend+="<section class='modal share_modal'>";
				friend+="<div class='share_bd'>";
				friend+="<i class='share_close modal_btn'></i>";
				friend+="<i class='share_ico'></i>";
				friend+="<div class='share_ttop'>级别不够，分享无法赚钱</div>";
				friend+="<div class='share_tb'>赶快选择升级会员，方可<br>分享赚钱</div>";
				friend+="</div><div class='share_fd clearfix'>";
				friend+="<a href='javascript:void(0);' class='modal_btn' onclick='share()'>强行推荐</a>";
				friend+="<a href='javascript:void(0);' class='modal_btn' onclick='upgrade()'>马上升级会员</a>";
				friend+="</div></section></div>";
				friend+="<!--弹窗 结束-->";
			}else{
				friend+="<section class='s_listwrap'>";
				friend+="<div class='s_list_top'>已成功注册购买的好友！</div>";
				friend+="</section>";
				$(result.pageForm.list).each(function(i,dom){
					if($(result.pageForm.list).length-1==i){
						friend+="<a href='javascript:void(0);' class='att_listbox last' onclick='business_card(this)'>";
					}else{
						friend+="<a href='javascript:void(0);' class='att_listbox' onclick='business_card(this)'>";
					}
					friend+="<div class='att_list_img'>";
					friend+="<img src='"+basePath+this[0]+"'>";
					friend+="</div>";
					friend+="<div class='att_listtxt'>";
					friend+="<span class='att_personname'>"+this[1]+"</span>";
					var type;
					switch(this[2]){
					case "0":
						type = "平台";
						break; 
					case "1":
						type = "税务 ";
						break;
					case "2":
						type = "公司";
						break;
					case "3":
						type = "合伙人商城业主会员";
						break;
					case "4":
						type = "微分金商城业主会员";
						break;
					case "5":
						type = "代理商商城业主会员";
						break;
					case "6":
						type = "vip客户";
						break;
					case "7":
						type = "普通客户";
						break;
					default :
						type = "未知";
						break;  
					}
					friend+="<span class='att_identity'>"+type+"</span>";
					friend+="<input class='staffId' type='hidden' value='"+this[3]+"'/>";
					friend+="<input class='account' type='hidden' value='"+this[4]+"'/>";
					friend+="</div></a>";
				})
			}
			
			$(".wrap_page").append(friend);
			$overlay = $('#overlay');
			if(result.pageForm!=null){
				pageNumber = result.pageForm.pageNumber;
				pageCount = result.pageForm.pageCount;
				if(pageNumber<pageCount){
					getHeight();
				}else{
					alert("已加载所有")
				}
			}
	   	}
	});
}
//查看个人名片
function business_card(obj) {
	 var backurl = "ea/consignee/ea_toVipCenter.jspa";
	 var staffId = $(obj).find(".staffId").val();
	 var user = $(obj).find(".account").val();
	 open(basePath
				+"ea/perinfor/ea_getPageHomeData.jspa?user="+user+"&editType=01&backurl="+backurl,
		"_self");
}
//升级会员
function upgrade() {
	var ccompanyId = 'contactCompany20101230UB4U5884S30000000176';
	document.location.href = basePath+"ea/wfjshop/ea_getpk.jspa?ccompanyId="+ccompanyId;
}
//分享二维码
function share(){
	document.location.href = basePath+"ea/vipcenter/ea_QRcode.jspa?staffid="+staffId;
}
//弹窗 strat
function modalHidden($ele) {
    $ele.removeClass('modal-in');
    $ele.one('transitionend', function() {
        $ele.css({
            "display": "none"
        });
        $overlay.removeClass('active');
    });
}
//点击弹窗内按钮、链接关闭弹窗
$(document).on("click",".modal_btn",function(e){
    e.stopPropagation();
    $(this).parents("section").removeClass('modal-in').one('transitionend', function() {
        $(this).css({
            display: "none"
        });
        $overlay.removeClass('active');
    });
});
//点击相应按钮 弹出弹窗
$(document).on("click",".share_mbtn",function(){
    var $that = $(this);
    $overlay.addClass('active');
    var $whichModal = $('.' + $(this).data('modal'));
    $whichModal.animate({
        "display": "block"
    }, 100, function() {
        $(this).addClass('modal-in');
    })
    $whichModal.css("display","block")
});
//点击遮罩层，关闭弹窗
$(document).on("click","#overlay",function(e){
        if (e.target.classList.contains('overlay')) {
            $(this).find(".modal-in").removeClass("modal-in").one('transitionend', function() {
                $(this).css({
                    display: "none"
                });
                $overlay.removeClass('active');
            })
        }
    })
//弹窗 end