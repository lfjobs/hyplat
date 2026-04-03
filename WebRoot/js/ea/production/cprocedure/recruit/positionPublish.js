//地址选择
$(function() {

    initData();

    $(".sec-zwlb .p-2").click(function(){
        if( $(".jobTitle").val()!=""){
            setcookie("jobTitle", $(".jobTitle").val());
        }else{
            setcookie("jobTitle", "");
        }
        if( $(".btn4").text()!="请选择"){
            setcookie("workYears", $(".btn4").text());
        }else{
            setcookie("workYears", "");
        }

        if( $(".btn6").text()!="请选择"){
            setcookie("education", $(".btn6").text());
        }else{
            setcookie("education","");
        }
        if( $(".btn7").text()!="请选择"){
            setcookie("salary", $(".btn7").text());
        }else{
            setcookie("salary", "");
        }

        setcookie("partorfull", $(".btn2").text());

        if( $(".jobRequire").val()!=""){
            setcookie("jobRequire", $(".jobRequire").val());
        }else{
            setcookie("jobRequire", "");
        }

        if( $("#myAddrs").val()!=""){
            setcookie("workCity", $("#myAddrs").val());
        }else{
            setcookie("workCity", "");
        }
        if( $(".workPlace").val()!=""){
            setcookie("workPlace", $(".workPlace").val());
        }else{
            setcookie("workPlace","");
        }

        setcookie("personNumber", $(".span-num").text());
        window.open(basePath+"ea/bidrecruit/ea_getPositionIndex.jspa?type=发布职位&selectpos="+$(".p-2").text()+"&sccId="+sccId+"&recruitInfo.riId="+riId+"&back="+back,"_self");


    });

    //取消
    $(".div-del .p-c").click(function () {

        $(".div-del").hide();

    })

    //立即发布
    $(".pb").click(function(){

     if($(".p-2").text()==""){
         $(".div-del").show();
         $(".titlep").text("请选择职位类别");
         return false;
     }

        if($(".jobTitle").val()==""){
            $(".div-del").show();
            $(".titlep").text("请填写职位名称");
            return false;
        }


        if($(".btn4").text()=="请选择"){
            $(".div-del").show();
            $(".titlep").text("请选择经验要求");
            return false;
        }

        if($(".btn6").text()=="请选择"){
            $(".div-del").show();
            $(".titlep").text("请选择学历要求");
            return false;
        }

        if($(".btn7").text()=="请选择"){
            $(".div-del").show();
            $(".titlep").text("请选择职位月薪");
            return false;
        }

        if($(".jobRequire").val()==""){
            $(".div-del").show();
            $(".titlep").text("请填写职位要求");
            return false;
        }
        if($("#myAddrs").val()==""){
            $(".div-del").show();
            $(".titlep").text("请选择工作城市");
            return false;
        }

        if($(".workPlace").val()==""){
            $(".div-del").show();
            $(".titlep").text("请填写详细地址");
            return false;
        }



        $(".partorfull").val($(".btn2").text());
        $(".workYears").val($(".btn4").text());
        $(".education").val($(".btn6").text());
        $(".salary").val($(".btn7").text());
        $(".span-num").text($(".span-num").text());

        $("#workCity").val($("#myAddrs").val());


        $("#mainForm").attr("target", "hidden").attr("action",
            basePath + "ea/bidrecruit/ea_addPosition.jspa");

        document.mainForm.submit.click();
        token = 13;

    });


    //人数加减
    var spnum=1;
    // $(".span-num").text(spnum);
    $(".div-num .img-add").click(function(){
        spnum+=1;
        $(".span-num").text(spnum);
    })
    $(".div-num .img-reduce").click(function(){
        if(spnum>1){
            spnum-=1;
            $(".span-num").text(spnum);
        }
    })
    //关闭
    $(".img-close").click(function(){
        $(this).parents("#div-zwxz").hide()
    })


    /**
     * 通过数组id获取地址列表数组
     *
     * @param {Number} id
     * @return {Array}
     */
    function getAddrsArrayById(id) {
        var results = [];
        if (addr_arr[id] != undefined)
            addr_arr[id].forEach(function(subArr) {
                results.push({
                    key: subArr[0],
                    val: subArr[1]
                });
            });
        else {
            return;
        }
        return results;
    }
    /**
     * 通过开始的key获取开始时应该选中开始数组中哪个元素
     *
     * @param {Array} StartArr
     * @param {Number|String} key
     * @return {Number}
     */
    function getStartIndexByKeyFromStartArr(startArr, key) {
        var result = 0;
        if (startArr != undefined)
            startArr.forEach(function(obj, index) {
                if (obj.key == key) {
                    result = index;
                    return false;
                }
            });
        return result;
    }

    //bind the click event for 'input' element
    $("#myAddrs").click(function() {
        var PROVINCES = [],
            startCities = [],
            startDists = [];
        //Province data，shall never change.
        addr_arr[0].forEach(function(prov) {
            PROVINCES.push({
                key: prov[0],
                val: prov[1]
            });
        });
        //init other data.
        var $input = $(this),
            dataKey = $input.attr("data-key"),
            provKey = 1, //default province 北京
            cityKey = 36, //default city 北京
            distKey = 37, //default district 北京东城区
            distStartIndex = 0, //default 0
            cityStartIndex = 0, //default 0
            provStartIndex = 0; //default 0

        if (dataKey != "" && dataKey != undefined) {
            var sArr = dataKey.split("-");
            if (sArr.length == 3) {
                provKey = sArr[0];
                cityKey = sArr[1];
                distKey = sArr[2];

            } else if (sArr.length == 2) { //such as 台湾，香港 and the like.
                provKey = sArr[0];
                cityKey = sArr[1];
            }
            startCities = getAddrsArrayById(provKey);
            startDists = getAddrsArrayById(cityKey);
            provStartIndex = getStartIndexByKeyFromStartArr(PROVINCES, provKey);
            cityStartIndex = getStartIndexByKeyFromStartArr(startCities, cityKey);
            distStartIndex = getStartIndexByKeyFromStartArr(startDists, distKey);
        }
        var navArr = [{//3 scrollers, and the title and id will be as follows:
            title: "省",
            id: "scs_items_prov"
        }, {
            title: "市",
            id: "scs_items_city"
        }, {
            title: "区",
            id: "scs_items_dist"
        }];
        SCS.init({
            navArr: navArr,
            onOk: function(selectedKey, selectedValue) {
                $input.val(selectedValue).attr("data-key", selectedKey);
            }
        });
        var distScroller = new SCS.scrollCascadeSelect({
            el: "#" + navArr[2].id,
            dataArr: startDists,
            startIndex: distStartIndex
        });
        var cityScroller = new SCS.scrollCascadeSelect({
            el: "#" + navArr[1].id,
            dataArr: startCities,
            startIndex: cityStartIndex,
            onChange: function(selectedItem, selectedIndex) {
                distScroller.render(getAddrsArrayById(selectedItem.key), 0); //re-render distScroller when cityScroller change
            }
        });
        var provScroller = new SCS.scrollCascadeSelect({
            el: "#" + navArr[0].id,
            dataArr: PROVINCES,
            startIndex: provStartIndex,
            onChange: function(selectedItem, selectedIndex) { //re-render both cityScroller and distScroller when provScroller change
                cityScroller.render(getAddrsArrayById(selectedItem.key), 0);
                distScroller.render(getAddrsArrayById(cityScroller.getSelectedItem().key), 0);
            }
        });
    });
});

function re_load() {

   document.location.href = basePath+"ea/bidrecruit/ea_findPositionList.jspa?sccId="+sccId+"&back="+back;

}

function initData(){
    if(pos!=null&&pos!=""){
        $(".p-2").text(pos);
        $("#positionName").val(pos);
        $("#positionCode").val(codeid);
        if(getcookie("jobTitle")!=""){
            $(".jobTitle").val(decodeURIComponent(getcookie("jobTitle")));
        }
        if(getcookie("workYears")!=""){
            $(".btn4").text(decodeURIComponent(getcookie("workYears")));
        }
        if(getcookie("education")!=""){
            $(".btn6").text(decodeURIComponent(getcookie("education")));
        }
        if(getcookie("salary")!=""){
            $(".btn7").text(decodeURIComponent(getcookie("salary")));
        }



        $(".btn2").text(decodeURIComponent(getcookie("partorfull")));

        if(getcookie("jobRequire")!=""){
            $(".jobRequire").val(decodeURIComponent(getcookie("jobRequire")));
        }
        if(getcookie("workCity")!=""){
            $("#myAddrs").val(decodeURIComponent(getcookie("workCity")));
        }
        if(getcookie("workPlace")!=""){
            $(".workPlace").val(decodeURIComponent(getcookie("workPlace")));
        }


        $(".span-num").text(decodeURIComponent(getcookie("personNumber")));
        $(".personNumber").val(decodeURIComponent(getcookie("personNumber")));


    }

}
function backs(){
    // if(back=="1"&&pst!=1){
    //     document.location.href = basePath+"ea/bidrecruit/ea_getResumeIndex.jspa?lei=gr&current=zw";
    // }else{
    //     re_load();
    // }

    window.history.go(-1);
    return false;

}

