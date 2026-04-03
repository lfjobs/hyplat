$(function(){
	var clicker;
$(".t-keybord").mousedown(function(event) {
clicker = $(this);
var values = clicker.val();
var wh = $(window).height();
var ww = $(window).width();
var kh = $("#t-keybord").height();
var kw = $("#t-keybord").width();
var t = clicker.offset().top + clicker.outerHeight() + 5;
var l = clicker.offset().left;
if ((t + kh) > wh) {
	t = clicker.offset().top - kh - 5;
}
if (l + kw > ww) {
	l = clicker.offset().left - kw + clicker.width();
}
//$("#t-keybord").css({ top: t, left: l });
$("#t-value").val(values);
$("#t-keybord").fadeIn('fast');
});
//取消
$("#t-qx").click(function(){
	$(".cz").fadeOut('fast');
    $("#t-value").val("");
})

//确认
$("#t-submit").click(function(event) {

$(".cz").fadeOut('fast');
var tvalue = $("#t-value").val();
	if(tvalue.length>=0&&tvalue.length<=5) {
        searchPlu($("#t-value").val());
    }else{
        $(".t-keybord").val($("#t-value").val());
        searchGoods($("#t-value").val());
        $(".t-keybord").val("");
	}
    $("#t-value").val("");
});

//关闭
$("#t-close").click(function(event) {
$("#t-value").val('');
});

//当点击数字键时
$("#t-keyvalue li").bind({
mousedown: function() {
	var val = $("#t-value").val();
	val += $(this).html();
	$("#t-value").val(val);
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseup: function() {
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseover: function() {
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseout: function(){
	$(this).css({
		"background-color": '#ffffff'
	});
}
});

//点击del
$("#t-del").click(function() {
var val = $("#t-value").val();
if (val.length > 0) {
	val = val.substring(0, val.length - 1);
}
$("#t-value").val(val);
});
//$(function(){
//$("#t-keybord").draggable({ handle: "#t-value" });
//})
})
