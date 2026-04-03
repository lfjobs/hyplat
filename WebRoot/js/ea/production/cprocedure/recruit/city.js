$(function() {
     // $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
     //     if (remote_ip_info.ret == '1') {
     //       var city = remote_ip_info.city;
     //        $("#citys").text(city);
     //     } else {
     //         alert('没有找到匹配的IP地址信息！');
     //     }
     // });
    showCityInfo();

	var Initials = $('.initials');
	var LetterBox = $('#letter');
	Initials
			.find('ul')
			.append(
					'<li><a href="#A">A</a></li><li><a href="#B">B</a></li><li><a href="#C">C</a></li><li><a href="#D">D</a></li><li><a href="#E">E</a></li><li><a href="#F">F</a></li><li><a href="#G">G</a></li><li><a href="#H">H</a></li><li><a href="#I">I</a></li><li><a href="#J">J</a></li><li><a href="#K">K</a></li><li><a href="#L">L</a></li><li><a href="#M">M</a></li><li><a href="#N">N</a></li><li><a href="#O">O</a></li><li><a href="#P">P</a></li><li><a href="#Q">Q</a></li><li><a href="#R">R</a></li><li><a href="#S">S</a></li><li><a href="#T">T</a></li><li><a href="#U">U</a></li><li><a href="#V">V</a></li><li><a href="#W">W</a></li><li><a href="#X">X</a></li><li><a href="#Y">Y</a></li><li><a href="#Z">Z</a></li><li>#</li>');

	$("li[href='#Z']").css("background", "transparent");
	
	

	$(".initials").find("li a").css("background");
	$(".initials ul li").click(function() {
		var _this = $(this);
		var LetterHtml = _this.html();
		LetterBox.html(LetterHtml).fadeIn();

		Initials.css('background', 'rgba(145,145,145,0.6)');

		setTimeout(function() {
			Initials.css('background', 'rgba(145,145,145,0)');
			LetterBox.fadeOut();
		}, 1000);

		var _index = _this.index();
		if (_index == 0) {
			$('.main').animate({
				scrollTop : '0px'
			}, 300);// 点击第一个滚到顶部
		} else if (_index == 27) {
			var DefaultTop = $('#default').position().top;
			$('.main').animate({
				scrollTop : DefaultTop + 'px'
			}, 300);// 点击最后一个滚到#号
		} else {
			var letter = _this.text();
			if ($('#' + letter).length > 0) {
				var LetterTop = $('#' + letter).position().top;
				$('html,body').animate({
					scrollTop : LetterTop - 45 + 'px'
				}, 300);
			}
		}
	});

	var windowHeight = $(window).height();
	var InitHeight = windowHeight - 45;
	Initials.height(InitHeight);
	var LiHeight = InitHeight / 28;
	Initials.find('li').height(LiHeight);
});

// 公众号排序
var SortList = $(".xuan_lis");
var SortBox = $(".sort_box");
SortList.sort(asc_sort).appendTo('.sort_box');// 按首字母排序
function asc_sort(a, b) {
	return makePy($(b).find('.num_name').text().charAt(0))[0].toUpperCase() < makePy($(
			a).find('.num_name').text().charAt(0))[0].toUpperCase() ? 1 : -1;
}

var initials = [];
var num = 0;
SortList.each(function(i) {
	var initial = makePy($(this).find('.num_name').text().charAt(0))[0]
			.toUpperCase();
	if (initial >= 'A' && initial <= 'Z') {
		if (initials.indexOf(initial) === -1)
			initials.push(initial);
	} else {
		num++;
	}

});

$.each(initials, function(index, value) {// 添加首字母标签
	SortBox.append('<div class="clear"></div><div class="sort_letter" id="' + value + '">' + value
			+ '</div>');
});

SortBox.append('<div class="clear"></div><div class="sort_letter" id="default">#</div>');

for ( var c = 0; c < SortList.length; c++) {// 插入到对应的首字母后面
	var letter = makePy(SortList.eq(c).find('.num_name').text().charAt(0))[0]
			.toUpperCase();
	switch (letter) {
	case "A":
		$('#A').after(SortList.eq(c));
		break;
	case "B":
		$('#B').after(SortList.eq(c));
		break;
	case "C":
		$('#C').after(SortList.eq(c));
		break;
	case "D":
		$('#D').after(SortList.eq(c));
		break;
	case "E":
		$('#E').after(SortList.eq(c));
		break;
	case "F":
		$('#F').after(SortList.eq(c));
		break;
	case "G":
		$('#G').after(SortList.eq(c));
		break;
	case "H":
		$('#H').after(SortList.eq(c));
		break;
	case "I":
		$('#I').after(SortList.eq(c));
		break;
	case "J":
		$('#J').after(SortList.eq(c));
		break;
	case "K":
		$('#K').after(SortList.eq(c));
		break;
	case "L":
		$('#L').after(SortList.eq(c));
		break;
	case "M":
		$('#M').after(SortList.eq(c));
		break;
	case "N":
		$('#N').after(SortList.eq(c));
		break;
	case "O":
		$('#O').after(SortList.eq(c));
		break;
	case "P":
		$('#P').after(SortList.eq(c));
		break;
	case "Q":
		$('#Q').after(SortList.eq(c));
		break;
	case "R":
		$('#R').after(SortList.eq(c));
		break;
	case "S":
		$('#S').after(SortList.eq(c));
		break;
	case "T":
		$('#T').after(SortList.eq(c));
		break;
	case "U":
		$('#U').after(SortList.eq(c));
		break;
	case "V":
		$('#V').after(SortList.eq(c));
		break;
	case "W":
		$('#W').after(SortList.eq(c));
		break;
	case "X":
		$('#X').after(SortList.eq(c));
		break;
	case "Y":
		$('#Y').after(SortList.eq(c));
		break;
	case "Z":
		$('#Z').after(SortList.eq(c));
		break;
	default:
		$('#default').after(SortList.eq(c));
		break;
	}
}

function showCityInfo() {

    // 加载城市查询插件

    AMap.service([ "AMap.CitySearch" ], function() {

        // 实例化城市查询类

        var citysearch = new AMap.CitySearch();

        // 自动获取用户IP，返回当前城市

        citysearch.getLocalCity(function(status, result) {

            if (status === 'complete' && result.info === 'OK') {

                if (result && result.city && result.bounds) {

                    var cityinfo = result.city;

                    $("#citys").text(cityinfo);

                }

            } else {

                console.log("您当前所在城市：" + result.info + "");

            }

        });

    });

}
