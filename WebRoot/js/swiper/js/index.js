$(function() {
	var hgS1 = new selectSwiper({
		el: '.select_box1',
        mustSelect: true,
		data: ['旅游住宿', '商业服务', '航空票务', '工业产品', '维修服务', '办公用品', '政府服务', '医疗服务'],
		init: function(index) {
			if(index !== -1) {
				$('.btn1').html(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			console.log(index);
			$('.btn1').html(this.data[index]);
			$("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
			$("body").removeClass("body_yc");
		},
	});
	$('.btn1').on('click', function() {
		hgS1.openSelectSwiper();
		$("body").addClass("body_yc");
	});
	var hgS2 = new selectSwiper({
		el: '.select_box2',
        mustSelect: true,
		data: ['全职', '兼职', '实习'],
		init: function(index) {
			if(index !== -1) {
				$('.btn2').html(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			console.log(index);
			$('.btn2').html(this.data[index]);
			$("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
			$("body").removeClass("body_yc");
		},
	});
	$('.btn2').on('click', function() {
		hgS2.openSelectSwiper();
		$("body").addClass("body_yc");
	});
	var hgS3 = new selectSwiper({
		el: '.select_box3',
        mustSelect: true,
		data: ['工商银行', '交通银行', '招商银行', '民生银行', '中信银行','浦发银行', '兴业银行', '光大银行', '广发银行', '平安银行','北京银行', '华夏银行', '农业银行', '建设银行', '邮政储蓄银行','中国银行', '宁波银行', '其他银行'],
		init: function(index) {
			if(index !== -1) {
				$('.btn3').html(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			console.log(index);
			$('.btn3').html(this.data[index]);
			$("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
			$("body").removeClass("body_yc");
		},
	});
	$('.btn3').on('click', function() {
		hgS3.openSelectSwiper();
		$("body").addClass("body_yc");
	});
	var hgS4 = new selectSwiper({
		el: '.select_box4',
        mustSelect: true,
		data: ['无经验', '1年以下', '1-3年', '3-5年', '5年以上'],
		init: function(index) {
			if(index !== -1) {
				$('.btn4').html(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			console.log(index);
			$('.btn4').html(this.data[index]);
			$("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
			$("body").removeClass("body_yc");
		},
	});
	$('.btn4').on('click', function() {
		hgS4.openSelectSwiper();
		$("body").addClass("body_yc");
	});

	var hgS5 = new selectSwiper({
		el: '.select_box5',
        mustSelect: true,
		data: ['对私账户', '对公账户'],
		init: function(index) {
			if(index !== -1) {
				$('.btn5').html(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			console.log(index);
			$('.btn5').html(this.data[index]);
			$("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
			$("body").removeClass("body_yc");
		},
	});
	$('.btn5').on('click', function() {
		hgS5.openSelectSwiper();
		$("body").addClass("body_yc");
	});


    var hgS6 = new selectSwiper({
        el: '.select_box6',
        mustSelect: true,
        data: ['大专', '硕士', 'EMBA', 'MBA', '本科', '博士', '不限', '其他'],
        init: function(index) {
            if(index !== -1) {
                $('.btn6').html(this.data[index]);
            }
        },
        okFunUndefind: function() {
            alert('必须选择一项');
            return false;
        },
        okFun: function(index) {
            console.log(index);
            $('.btn6').html(this.data[index]);
            $("body").removeClass("body_yc");
        },
        closeFun: function() {
            console.log('取消');
            $("body").removeClass("body_yc");
        },
    });
    $('.btn6').on('click', function() {
        hgS6.openSelectSwiper();
        $("body").addClass("body_yc");
    });

	var hgS7 = new selectSwiper({
		el: '.select_box7',
		mustSelect: true,
		// activeIndex: 0,
		data: ['1千以下', '1千-2千', '2千-4千', '4千-6千', '6千-8千', '8千-1万', '1万-1.5万', '1.5万-2万', '2万-3万', '3万-5万', '5万以上'],
		init: function(index) {
			if(index !== -1) {
				$('.btn7').val(this.data[index]);
			}
		},
		okFunUndefind: function() {
			alert('必须选择一项');
			return false;
		},
		okFun: function(index) {
			$('.btn7').html(this.data[index]);
            $("body").removeClass("body_yc");
		},
		closeFun: function() {
			console.log('取消');
            $("body").removeClass("body_yc");
		},
	});
	$('.btn7').on('click', function() {
		hgS7.openSelectSwiper();
        $("body").addClass("body_yc");
	});
});