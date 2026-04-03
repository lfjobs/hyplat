function SelCity(obj,e) {
    var ths = obj;
    var dal = '<div class="_citys"><span title="关闭" id="cColse" >×</span><ul id="_citysheng" class="_citys0"><li class="citySel">省份</li><li>城市</li><li>区县</li></ul><div id="_citys0" class="_citys1"></div><div style="display:none" id="_citys1" class="_citys1"></div><div style="display:none" id="_citys2" class="_citys1"></div></div>';
    Iput.show({ id: ths, event: e, content: dal,width:"470"});
    $("#cColse").click(function () {
        Iput.colse();
    });

    getProvice();//获取省份

    $("#_citys0 a").click(function () {
        var g = getCity($(this));
        $("#_citys1 a").remove();
        $("#_citys1").append(g);
        $("._citys1").hide();
        $("._citys1:eq(1)").show();
        $("#_citys0 a,#_citys1 a,#_citys2 a").removeClass("AreaS");
        $(this).addClass("AreaS");
        var lev = $(this).data("name");
        ths.value = $(this).data("name");
        if (document.getElementById("hcity") == null) {
            var hcitys = $('<input>', {
                type: 'hidden',
                name: "hcity",
                "data-id": $(this).data("id"),
                id: "hcity",
                val: lev
            });
            $(ths).after(hcitys);
        }
        else {
            $("#hcity").val(lev);
            $("#hcity").attr("data-id", $(this).data("id"));
        }
        $("#_citys1 a").click(function () {
            $("#_citys1 a,#_citys2 a").removeClass("AreaS");
            $(this).addClass("AreaS");
            var lev =  $(this).data("name");
            if (document.getElementById("hproper") == null) {
                var hcitys = $('<input>', {
                    type: 'hidden',
                    name: "hproper",
                    "data-id": $(this).data("id"),
                    id: "hproper",
                    val: lev
                });
                $(ths).after(hcitys);
            }
            else {
                $("#hproper").attr("data-id", $(this).data("id"));
                $("#hproper").val(lev);
            }
            var bc = $("#hcity").val();
            ths.value = bc+ "/" + $(this).data("name");

            var ar = getArea($(this));

            $("#_citys2 a").remove();
            $("#_citys2").append(ar);
            $("._citys1").hide();
            $("._citys1:eq(2)").show();

            $("#_citys2 a").click(function () {
                $("#_citys2 a").removeClass("AreaS");
                $(this).addClass("AreaS");
                var lev = $(this).data("name");
                if (document.getElementById("harea") == null) {
                    var hcitys = $('<input>', {
                        type: 'hidden',
                        name: "harea",
                        "data-id": $(this).data("id"),
                        id: "harea",
                        val: lev
                    });
                    $(ths).after(hcitys);
                }
                else {
                    $("#harea").val(lev);
                    $("#harea").attr("data-id", $(this).data("id"));
                }
                var bc = $("#hcity").val();
                var bp = $("#hproper").val();
                ths.value = bc + "/" + bp + "/" + $(this).data("name");
                Iput.colse();
            });

        });
    });
    $("#_citysheng li").click(function () {
        $("#_citysheng li").removeClass("citySel");
        $(this).addClass("citySel");
        var s = $("#_citysheng li").index(this);
        $("._citys1").hide();
        $("._citys1:eq(" + s + ")").show();
    });
}
//省
function getProvice(){
    var url = basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictCity.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        success:function(data){
            var member = eval("(" + data + ")");
            var cityMap=member.city;
            var array=[];
            $(cityMap.districtCity).each(function(){
                array.push("<a data-level='0' data-id='"+$(this)[0]+"' data-name='"+$(this)[1]+"'>"+$(this)[1]+"</a>");
            });
            $("#_citys0").append(array.join(""));
        },
        error:function(){
            console.log("地址获取失败！");

        }
    });

}
//市
function getCity(obj) {
     proviceId = obj.data('id');
    var g = '';
    $.ajax({
        url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
        type : "post",
        async : false,
        data: {
            "sdistrict.districtID":proviceId,
            "showParam":"true"
        },
        dataType : "json",
        success: function(data){
            var subMember = eval("(" + data + ")");
            var country=subMember.district.country;
            var array=[];
            if(country!=null&&country.length>0){
                $(country).each(function(){
                    g += '<a data-level="1" data-id="' + $(this)[0] + '" data-name="' + $(this)[1] + '" title="' + $(this)[1] + '">' + $(this)[1] + '</a>'
                });

            }

        },
        error:function(){
            console.log("地址获取失败！");
        }
    });
    $("#_citysheng li").removeClass("citySel");
    $("#_citysheng li:eq(1)").addClass("citySel");
    return g;
}
//区
function getArea(obj) {
    cityId = obj.data('id');
    var g = '';
    $.ajax({
        url : basePath + "ea/newpcend/sajax_ea_ajaxSelDistrictByID.jspa",
        type : "post",
        async : false,
        data: {
            "sdistrict.districtID":cityId,
            "showParam":"false"
        },
        dataType : "json",
        success: function(data){
            var subMember = eval("(" + data + ")");
            var country=subMember.district.country;
            var array=[];
            if(country!=null&&country.length>0){
                $(country).each(function(){
                    g += '<a data-level="1" data-id="' +$(this)[0] + '" data-name="' + $(this)[1] + '" title="' + $(this)[1] + '">' + $(this)[1] + '</a>'
                });
                array.push("<option id='00'><span>其他区</span></option>");
            }
        },
        error:function(){
            console.log("地址获取失败！");
        }
    });

    $("#_citysheng li").removeClass("citySel");
    $("#_citysheng li:eq(2)").addClass("citySel");
    return g;
}
