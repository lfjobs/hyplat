$(function(){
    initload();
    //搜索框
    $("#search").bind('input propertychange', function() {
        var search =$("#search").val();
        if(search==""){
            $(".content").show();
            $(".initials_list").show();
            $(".main1").html("");
            return;
        }
        var result = "";
        $(".sort_section .clearfix .num_name").each(function(){
            if($(this).text().indexOf(search)!=-1){
                result+="<div class='sort_list'><div class='num_name'>"+$(this).text()+"</div></div>";
            }

        });
        if(result==""){
            result="<div style='margin-top:50%;text-align:center;'>'"+search+"'相关城市还没有开通，请切换其他城市试试</div>";
        }
        $(".content").hide();
        $(".initials_list").hide();
        $(".main1").html(result);

    });


    //点击

    $(document).on("click",".num_name,.click_li",function(){
        var city= $(this).text();
        document.location.href = basePath+"/ea/industry/ea_claimCompanyList.jspa?city="+city;

    });
    var Initials=$('.section_initials .clearfix');
    //var LetterBox=$('.section_initials .clearfix');
    Initials.find('ul').append('<li><a href="#A">A</a></li><li><a href="#B">B</a></li><li><a href="#C">C</a></li><li><a href="#D">D</a></li><li><a href="#E">E</a></li><li><a href="#F">F</a></li><li><a href="#G">G</a></li><li><a href="#H">H</a></li><li><a href="#I">I</a></li><li><a href="#J">J</a></li><li><a href="#K">K</a></li><li><a href="#L">L</a></li><li><a href="#M">M</a></li><li><a href="#N">N</a></li><li><a href="#O">O</a></li><li><a href="#P">P</a></li><li><a href="#Q">Q</a></li><li><a href="#R">R</a></li><li><a href="#S">S</a></li><li><a href="#T">T</a></li><li><a href="#U">U</a></li><li><a href="#V">V</a></li><li><a href="#W">W</a></li><li><a href="#X">X</a></li><li><a href="#Y">Y</a></li><li><a href="#Z">Z</a></li>');


    $("li[href='#Z']").css("background","transparent");
    $(".main").css({"position":"relative","height":$(window).height()-$(".fixed").outerHeight()+"px","overflow":"auto"});

    $(".section_initials .clearfix").find("li a").css("background");
    $(".section_initials .clearfix ul li").click(function(){
        var _this=$(this);
        var LetterHtml=_this.html();
        //LetterBox.html(LetterHtml).fadeIn();

        Initials.css('background','rgba(145,145,145,0.6)');

        setTimeout(function(){
            Initials.css('background','rgba(145,145,145,0)');
            //LetterBox.fadeOut();
        });

        var _index = _this.index();
        if(_index==0){
            $('.main').animate({scrollTop: '0px'}, 300);//点击第一个滚到顶部
        }else if(_index==27){
            var DefaultTop=$('#default').position().top;
            $('.main').animate({scrollTop: DefaultTop+'px'}, 300);//点击最后一个滚到#号
        }else{
            var letter = _this.text();
            if($('#'+letter).length>0){
                var LetterTop = $('#'+letter).position().top;
                $('html,body').animate({scrollTop: LetterTop-45+'px'}, 300);
            }
        }
    });

    var windowHeight=$(window).height();
    var InitHeight=windowHeight-100;
    Initials.height(InitHeight);
    var LiHeight=InitHeight/30;
    Initials.find('li').height(LiHeight);
});
    function initload(){
        $.getScript('http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js', function(_result) {
            if (remote_ip_info.ret == '1') {
                var city = remote_ip_info.city;
                $("#city").text(city);
            } else {
                alert('没有找到匹配的IP地址信息！');
            }
        });

    }
    //公众号排序
    var SortList=$(".sort_list");
    var SortBox=$(".sort_section .clearfix");
    SortList.sort(asc_sort).appendTo('.sort_section .clearfix');//按首字母排序
    function asc_sort(a, b) {
        return makePy($(b).find('.num_name').text().charAt(0))[0].toUpperCase() < makePy($(a).find('.num_name').text().charAt(0))[0].toUpperCase() ? 1 : -1;
    }

    var initials = [];
    var num=0;
    SortList.each(function(i) {
        var initial = makePy($(this).find('.num_name').text().charAt(0))[0].toUpperCase();
        if(initial>='A'&&initial<='Z'){
            if (initials.indexOf(initial) === -1)
                initials.push(initial);
        }else{
            num++;
        }

    });


    $.each(initials, function(index, value) {//添加首字母标签
        SortBox.append('<div style="margin-left: -20px" class="sort_letter" id="'+ value +'"><p><a name="'+value+'">'+value+'</a>');
    });
    //if(num!=0){

    //SortBox.append('<div class="sort_letter" id="default">#</div>');

    //}
    for (var c =0;c<SortList.length;c++) {//插入到对应的首字母后面
        var letter=makePy(SortList.eq(c).find('.num_name').text().charAt(0))[0].toUpperCase();
        switch(letter){
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
    //}



