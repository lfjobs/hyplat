if(!dpFlag){
	var codeAll = "";
    // var gsHtml = '批发商城'
    //     +'<img src="'+basePath+'images/scMobile/wholesaleMall/qiehuan.png" />'
    //     +'<span onclick="toBussList();">'+companyName+'</span>'
    // $("#ttsw_dp_gsmc").html(gsHtml);
    posNum = isExistPosNum(posNum);
    console.log("小屏");
    $(document).ready(function() {
        //获取一级商品分类
        oneGoodsClassifyList();
        //保存当前页面链接
        FastClick.attach(document.body)
        backUrl();
    });

    /**
     * 获取一级商品分类
     */
    function oneGoodsClassifyList(){
        //获取超市一级菜单商品分类
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxGetOneGoodsClassify.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "codePID": codePID,//物类id[该超市所有商品分类的父id]
                "ccompanyID": ccompanyID,
                "ccomIDPlatform":ccomIDPlatform
            },
            dataType: "json",
            success: function (data) {
                $("#ttsw_ts_hide").hide();//隐藏提示
                var member = (new Function("return " + data))();//格式化返回参数
                $("#ttsw_one_goods_Classify").empty();
                $("#ttsw_one_goods_Classify_All").empty();
                var gClassList = member.goodsClassifyList;
                if (gClassList != null && gClassList.length > 0) {
                    var innerHtml = getOneGoodsHtml(gClassList);
                    $("#ttsw_one_goods_Classify").append(innerHtml);
                    var innerAllHtml = getOneGoodsAllHtml(gClassList);
                    $("#ttsw_one_goods_Classify_All").append(innerAllHtml);
                } else {
                    alert("全球批发城配送信息交易平台正在拔地升启！");
                    var qbInnerHtml = '<menu class="clearfix tab_level_father" >';
                    qbInnerHtml +=  '<li class="ttsw_one_li_active" onclick="toGoodsSearch(\'all\',this);"><a>全部商品</a></li>';//全部商品——》就是跳转查询页面
                    qbInnerHtml +='</menu>';
                    $("#ttsw_one_goods_Classify").append(qbInnerHtml);
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }

    /**
     * 循环拼接一级菜单li数据
     * @param gClassList 商品分类集合
     * @returns {*}
     */
    function getOneGoodsHtml(gClassList) {
        var liHtml = '';
        var innerHtml = '<menu class="clearfix tab_level_father" >';
        innerHtml +=  '<li class="active ttsw_one_li_active" onclick="twoGoodsClassifyList(\'all\',this);"><a>全部商品</a></li>';//全部商品——》就是跳转查询页面
        for (var i = 0, len = gClassList.length; i < len; i++) {
            if(i==0){
                liHtml = '<li class="ttsw_one_li_active" onclick="twoGoodsClassifyList(\''+gClassList[i][0]+'\',this);">';
                twoGoodsClassifyList('all',"");
            }else{
                liHtml = '<li class="ttsw_one_li_active" onclick="twoGoodsClassifyList(\''+gClassList[i][0]+'\',this);">';
            }
            innerHtml += liHtml
                            +'<a>'+ gClassList[i][1]+'</a>'
                            +'<input type="hidden" name="" id="" value="' + gClassList[i][0] + '" style="display: none;" />'
                        +'</li>';
        }
        innerHtml +='</menu>';
        return innerHtml;
    }

    /**
     * 循环拼接所有一级菜单li数据
     * @param gClassList 商品分类集合
     * @returns {*}
     */
    function getOneGoodsAllHtml(gClassList) {
        var innerHtml = '';
        for (var i = 0, len = gClassList.length; i < len; i++) {
            innerHtml += '<li onclick="twoGoodsClassifyList(\''+gClassList[i][0]+'\',this);">'
                            +'<img src="' + basePath + gClassList[i][2] + '"/>'
                            +'<p>'+gClassList[i][1]+'</p>'
                            +'<input type="hidden" name="" id="" value="' + gClassList[i][0] + '" style="display: none;" />'
                        +'</li>';
        }
        return innerHtml;
    }

    /**
     * 获取二级商品分类
     * @param codePid 商品id
     * @param obj this
     */
    function twoGoodsClassifyList(codePid,obj){
    	  codeAll = codePid;
        //初始化选中状态
        $(".ttsw_one_li_active").removeClass("active");
        $(obj).addClass("active");
        //遮罩层隐藏
        $(".all_classification").animate({
            height:0
        },"slow");
        $("body").removeClass("body_yc");

        //获取超市二级菜单商品分类
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxGetTwoGoodsClassify.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "codePID": codePid,//物类id[该超市所有商品分类的父id]
                "ccompanyID": ccompanyID,
                "ccomIDPlatform":ccomIDPlatform
            },
            dataType: "json",
            success: function (data) {
                var member = (new Function("return " + data))();//格式化返回参数
                $("#ttsw_two_goods_Classify").empty();
                var gClassList = member.goodsClassifyList;
                if (gClassList != null && gClassList.length > 0) {
                    var innerHtml = getTwoGoodsHtml(gClassList);
                    $("#ttsw_two_goods_Classify").append(innerHtml);
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }


    /**
     * 循环拼接二级菜单li数据
     * @param gClassList 商品分类集合
     * @returns {*}
     */
    function getTwoGoodsHtml(gClassList) {
        var liHtml ='';
        var innerHtml = '<menu class="element">';
        for (var i = 0, len = gClassList.length; i < len; i++) {
            if(i==0){
            	if(codeAll=="all"){
                    liHtml = '<li class="ttsw_two_li_active"  onclick="threeGoodsClassifyList(\''+gClassList[i][0]+'\',this);">';
            	}else{
            		
                    liHtml = '<li class="active ttsw_two_li_active"  onclick="threeGoodsClassifyList(\''+gClassList[i][0]+'\',this);">';

            	}
                threeGoodsClassifyList(gClassList[i][0],"");
            }else{
                liHtml = '<li class="ttsw_two_li_active"  onclick="threeGoodsClassifyList(\''+gClassList[i][0]+'\',this);">';
            }
            innerHtml += liHtml
                            +'<a>'+ gClassList[i][1].substring(0,4)+'</a>'
                            +'<input type="hidden" name="" id="" value="' + gClassList[i][0] + '" style="display: none;" />'
                        +'</li>';
        }
        innerHtml +='</menu>';
        return innerHtml;
    }

    /**
     * 获取商品信息
     * @param codeId 商品分类id
     * @param obj this
     */
    function threeGoodsClassifyList(codeId,obj) {
    	
    	if($(".ttsw_two_li_active").length==0){
    		if(codeAll=="all"){
        		codeId = codeAll;
        	}
    	}
    	
        if(obj != null){
            $(".ttsw_two_li_active").removeClass("active");//二级选中
            $(obj).addClass("active");
        }
        //异步获取数据
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxGetSupermarketGoodsList.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                //"pageForm.pageNumber": pagenumber,
                "codePID": codeId,
                "ccompanyID": ccompanyID,
                //"goodsName": goodsName,
                //"industryType": industryType
                "ccomIDPlatform":ccomIDPlatform
            },
            dataType: "json",
            success: function (data) {
                var member = (new Function("return " + data))();//格式化返回参数
                var mallBeanList = member.mallBeanList;
                var shoppingCartList = member.shoppingCartList;
                var companyId = member.companyId;
                $("#companyId").val(companyId);
                $("#ttsw_three_goods_Classify").empty();
                if (mallBeanList != null && mallBeanList.length > 0) {
                    var innerHtml = getThreeGoodsHtml(mallBeanList,shoppingCartList,codeId);
                    $("#ttsw_three_goods_Classify").append(innerHtml);
                }
                //添加购物车数量及金额
                addShopNumAndPrice(member.shopNum,member.shopPrice,member.shopTypeNum);
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }

    /**
     * 循环拼接三级货物页面
     * @param gGoodslist 货物集合
     * @param shoppingCartList 购物车集合
     * @param codeId 二级商品分类id
     * @returns {string}
     */
    function getThreeGoodsHtml(gGoodslist,shoppingCartList,codeId){
        var innerHtml = '';
        var goods = null;
        var shopCart = null;
        var scCodeIds = null;
        var scGoodsIds = null;
        var scNums=0;//加入购物车类型为tjFlag=1数用于显示
        var ggHtml = '';//购物车类型为tjFlag=1拼接页面
        //循环三级货物信息
        var scFlag = false;//是否有购物车数据false无true有
        var cfArr = new Array();//保存重复数据的数组
        for (var i = 0; i < gGoodslist.length; i++) {
            goods = gGoodslist[i];
            //判断数组中是否存在
            if(cfArr.indexOf(goods.goodsid) > -1){//则包含该元素
                continue;
            }
            cfArr[i] =  goods.goodsid;
            if(shoppingCartList.length > 0){
                scFlag = false;//无
                //循环购物车信息
                scCodeIds = new Array();
                scGoodsIds = new Array();
                scNums=0;//加入购物车类型为tjFlag=1数用于显示
                ggHtml = '';//购物车类型为tjFlag=1拼接页面
                for(var n=0,m=shoppingCartList.length;n<m;n++){
                    shopCart = shoppingCartList[n];
                    //if(shopCart.codeId == codeId && goods.goodsid == shopCart.goodsid){//购物车保存的二级商品分类=选择的二级商品分类
                    if(goods.goodsid == shopCart.goodsid){//购物车保存的二级商品分类=选择的二级商品分类
                        //判断是否是选择规格添加
                        scFlag = true;//有
                        if(shopCart.tjFlag == 1){//1：选中产品规格、颜色添加
                            //判断元素是否在数组中
                            scNums += shopCart.tjNum;
                            ggHtml = getShopCartThreeGoodsHtml(shopCart,codeId,scNums);
                        }else{
                            //拼接购物车中三级商品页面
                            innerHtml += getShopCartThreeGoodsHtml(shopCart,codeId,0);
                        }
                        scCodeIds[n] = shopCart.codeId;
                        scGoodsIds[n] = shopCart.goodsid;
                    }
                }
                innerHtml +=ggHtml;//初始页面+购物车页面
                if(scFlag){//如果有购物车数据，则货物清空，不做初始化
                    goods = null;
                }
                if(goods != null){//货物不为空，初始化html
                    //初始三级商品页面
                    innerHtml += getInitialThreeGoodsHtml(goods,codeId);
                }
            }else{
                //初始三级商品页面
                innerHtml += getInitialThreeGoodsHtml(goods,codeId);
            }
        }
        return innerHtml;
    }

    /**
     * 拼接初始三级商品页面
     * @param goods 货物
     * @param codeId 二级商品分类id
     * @returns {string}
     */
    function getInitialThreeGoodsHtml(goods,codeId){
        var innerHtml = '<li class="clearfix">'
                            +'<div>'
                                +'<a>'
                                    +'<img src="' + basePath +  goods.image + '" alt="" />'
                                +'</a>'
                            +'</div>'
                            +'<section>'
                                +'<h2>'
                                    +'<a class="txt">'
                                        +goods.goodsName
                                    +'</a>'
                                +'</h2>'
                                +'<p class="p_money">￥<span>' + goods.allPrice +'</span></p>'
                                +'<div class="clearfix">'
                                    +'<p>' + goods.standard +'</p>'
                                    +'<p>'
                                        +'<img class="reduce_number" onclick="reduceNumber(this,\''+codeId+'\',\''+goods.goodsid+'\');" src="'+basePath+'images/scMobile/wholesaleMall/img_4_10.png" style="display: none;"/>'
                                        +'<input id="ttsw_three_num_'+codeId+'_'+ goods.goodsid+'" readonly="readonly" type="number" value="0" class="number" style="display: none;"></input>'
                                        +'<img class="add_number" onclick="addNumber(this,\''+codeId+'\',\''+goods.goodsid+'\',\''+goods.image+'\',\''+ goods.allPrice+'\');" data-num="0" src="' + basePath + 'images/scMobile/wholesaleMall/img_4_12.png" style="display: block;"/>'
                                    +'</p>'
                                +'</div>'
                            +'</section>'
                        +'</li>'
                        +'<input type="hidden" id="ttsw_three_hide_ppid_'+codeId+'_'+ goods.goodsid+'" value="'+goods.ppid+'"/>'//产品id
                        +'<input type="hidden" id="ttsw_three_hide_wholesale_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.wholesale+'"/>'//批发价
                        +'<input type="hidden" id="ttsw_three_hide_wholesaleId_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.wholesaleId+'"/>'//批发价id
                        +'<input type="hidden" id="ttsw_three_hide_totalWhoPrice_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.totalWhoPrice+'"/>'//使用红包后增幅价格
                        +'<input type="hidden" id="ttsw_three_hide_allPrice_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.allPrice+'"/>'//总价=批发价+使用红包后增幅价格
                        +'<input type="hidden" id="ttsw_three_hide_goodsid_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.goodsid+'"/>'//货物id
                        +'<input type="hidden" id="ttsw_three_hide_goodsName_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.goodsName+'"/>'//货物名称
                        +'<input type="hidden" id="ttsw_three_hide_image_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.image+'"/>'//图片路径
                        +'<input type="hidden" id="ttsw_three_hide_companyid_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.companyid+'"/>'//公司id
                        +'<input type="hidden" id="ttsw_three_hide_standard_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.standard+'"/>'//规格
                        +'<input type="hidden" id="ttsw_three_hide_psc_'+codeId+'_'+ goods.goodsid+'" value=""/>'//购物车id
                        +'<input type="hidden" id="ttsw_three_hide_barCode_'+codeId+'_'+ goods.goodsid+'" value="'+ goods.barCode+'"/>';//条码
        return innerHtml;
    }

    /**
     * 拼接购物车中三级商品页面
     * @param shopCart 购物车中信息
     * @param codeId 二级商品分类id
     * @returns {string}
     */
    function getShopCartThreeGoodsHtml(shopCart,codeId,scNum){
        var jianImgHtml= '';
        if(scNum == 0){//购物车已有数据，则不可点击
            jianImgHtml = '<img class="reduce_number" onclick="reduceNumber(this,\''+codeId+'\',\''+shopCart.goodsid+'\');" src="'+basePath+'images/scMobile/wholesaleMall/img_4_10.png" style="display: block;"/>';
        }
        var innerHtml = '<li class="clearfix">'
                            +'<div>'
                                +'<a>'
                                    +'<img src="' + basePath +  shopCart.image + '" alt="" />'
                                +'</a>'
                            +'</div>'
                            +'<section>'
                                +'<h2>'
                                    +'<a class="txt">'
                                        +shopCart.goodsName
                                    +'</a>'
                                +'</h2>'
                                +'<p class="p_money">￥<span>' + shopCart.allPrice +'</span></p>'
                                +'<div class="clearfix">'
                                    +'<p>' + shopCart.standard +'</p>'
                                    +'<p>'
                                        + jianImgHtml
                                        +'<input id="ttsw_three_num_'+codeId+'_'+ shopCart.goodsid+'"  readonly="readonly" type="number" value="'+(scNum>0?scNum:shopCart.tjNum)+'" class="number" style="display: block;"></input>'
                                        +'<img class="add_number" onclick="addNumber(this,\''+codeId+'\',\''+shopCart.goodsid+'\',\''+shopCart.image+'\',\''+ shopCart.allPrice+'\');" data-num="0" src="' + basePath + 'images/scMobile/wholesaleMall/img_4_12.png" style="display: block;"/>'
                                    +'</p>'
                                +'</div>'
                            +'</section>'
                        +'</li>'
                        +'<input type="hidden" id="ttsw_three_hide_ppid_'+codeId+'_'+ shopCart.goodsid+'" value="'+shopCart.ppid+'"/>'//产品id
                        +'<input type="hidden" id="ttsw_three_hide_wholesale_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.wholesale+'"/>'//批发价
                        +'<input type="hidden" id="ttsw_three_hide_wholesaleId_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.wholesaleId+'"/>'//批发价id
                        +'<input type="hidden" id="ttsw_three_hide_totalWhoPrice_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.totalWhoPrice+'"/>'//使用红包后增幅价格
                        +'<input type="hidden" id="ttsw_three_hide_allPrice_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.allPrice+'"/>'//总价=批发价+使用红包后增幅价格
                        +'<input type="hidden" id="ttsw_three_hide_goodsid_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.goodsid+'"/>'//货物id
                        +'<input type="hidden" id="ttsw_three_hide_goodsName_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.goodsName+'"/>'//货物名称
                        +'<input type="hidden" id="ttsw_three_hide_image_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.image+'"/>'//图片路径
                        +'<input type="hidden" id="ttsw_three_hide_companyid_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.companyid+'"/>'//公司id
                        +'<input type="hidden" id="ttsw_three_hide_standard_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.standard+'"/>'//规格
                        +'<input type="hidden" id="ttsw_three_hide_psc_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.pscId+'"/>'//购物车id
                        +'<input type="hidden" id="ttsw_three_hide_barCode_'+codeId+'_'+ shopCart.goodsid+'" value="'+ shopCart.barCode+'"/>';//条码
        return innerHtml;
    }


    /**
     * 异步获取规格分类
     * @param goodsid
     */
    function ajaxGetGGFlList(obj,codeId,goodsId,goodsImg,goodsPrice){
        //异步获取数据
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxGetGGFlList.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "goodsId": goodsId,
            },
            dataType: "json",
            success: function (data) {
                var member = (new Function("return " + data))();//格式化返回参数
                var attriBeanList = member.attriBeanList;
                $("#ttsw_ttsw_three_cm_goods_Classify").empty();
                if (attriBeanList != null && attriBeanList.length > 0) {
                    $(obj).attr("data-num","1");
                    var innerHtml = getThree_cm_goods(attriBeanList,codeId,goodsId,goodsImg,goodsPrice);
                    $("#ttsw_ttsw_three_cm_goods_Classify").append(innerHtml);
                }else{
                    $(obj).attr("data-num","0");
                }
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }

    /**
     * 产品规格页面
     * @param goodsId
     */
    function getThree_cm_goods(attriBeanList,codeId,goodsId,goodsImg,goodsPrice) {
        var cmStr,ysStr,ftStr,spStr;
        cmStr=ysStr=ftStr=spStr = "";
        for(var i=0;i<attriBeanList.length;i++){
            if(attriBeanList[i].type == 0){//尺码
                if(cmStr == ""){
                    cmStr += '<p class="active" onclick="toCheck_p(this,\'cmStr\',\''+attriBeanList[i].apid+'\');">';
                    cmStr += '<input type="hidden" id="ttsw_three_check_hide_cmStr" value="'+attriBeanList[i].attrivalue+'"/>'//选中尺码
                    cmStr += '<input type="hidden" id="ttsw_three_check_hide_cmStr_apid" value="'+attriBeanList[i].apid+'"/>'//选中尺码apid
                }else{
                    cmStr += '<p onclick="toCheck_p(this,\'cmStr\',\''+attriBeanList[i].apid+'\');">';
                }
                cmStr += attriBeanList[i].attrivalue+'</p>';
            }else if(attriBeanList[i].type == 1){//颜色
                if(ysStr == ""){
                    ysStr += '<p class="active" onclick="toCheck_p(this,\'ysStr\',\''+attriBeanList[i].apid+'\');">';
                    ysStr += '<input type="hidden" id="ttsw_three_check_hide_ysStr" value="'+attriBeanList[i].attrivalue+'"/>'//选中颜色
                    ysStr += '<input type="hidden" id="ttsw_three_check_hide_ysStr_apid" value="'+attriBeanList[i].apid+'"/>'//选中颜色apid
                }else{
                    ysStr += '<p onclick="toCheck_p(this,\'ysStr\',\''+attriBeanList[i].apid+'\');">';
                }
                ysStr += attriBeanList[i].attrivalue+'</p>';
            }else if(attriBeanList[i].type == 2){//副图
                if(ftStr == ""){
                    ftStr += '<p class="active" onclick="toCheck_p(this,\'ftStr\',\''+attriBeanList[i].apid+'\');">';
                    ftStr += '<input type="hidden" id="ttsw_three_check_hide_ftStr" value="'+attriBeanList[i].attrivalue+'"/>'//选中副图
                    ftStr += '<input type="hidden" id="ttsw_three_check_hide_ftStr_apid" value="'+attriBeanList[i].apid+'"/>'//选中副图apid
                }else{
                    ftStr += '<p onclick="toCheck_p(this,\'ftStr\',\''+attriBeanList[i].apid+'\');">';
                }
                ftStr += attriBeanList[i].attrivalue+'</p>';
            }else if(attriBeanList[i].type == 3){//视频
                if(spStr == ""){
                    spStr += '<p class="active" onclick="toCheck_p(this,\'spStr\',\''+attriBeanList[i].apid+'\');">';
                    spStr += '<input type="hidden" id="ttsw_three_check_hide_spStr" value="'+attriBeanList[i].attrivalue+'"/>'//选中视频
                    spStr += '<input type="hidden" id="ttsw_three_check_hide_spStr_apid" value="'+attriBeanList[i].apid+'"/>'//选中视频apid
                }else{
                    spStr += '<p onclick="toCheck_p(this,\'spStr\',\''+attriBeanList[i].apid+'\');">';
                }
                spStr += attriBeanList[i].attrivalue+'</p>';
            }
        }
        var innerHtml = '';
        innerHtml += '<div class="clearfix">'
            innerHtml += '<img src="'+basePath + goodsImg+'"/>'
            innerHtml += '<p>￥<span id="span_money">'+goodsPrice+'</span></p>'
            innerHtml += '<div>'
                innerHtml += '<span>选择</span>  <span>尺码</span>  <span>颜色分类</span>'
            innerHtml += '</div>'
            innerHtml += '<img class="pecifications_del" onclick="delPecifications();" src="'+basePath +'images/scMobile/wholesaleMall/pecifications_del.png"/>'
        innerHtml += '</div>'
        if(cmStr != ""){
            innerHtml += '<section class="clearfix">'
            innerHtml += '<h4>尺码</h4>'
            innerHtml += '<div class="choice_cmStr">'
            innerHtml += cmStr
            innerHtml += '</div>'
            innerHtml += '</section>'
        }
        if(ysStr != ""){
            innerHtml += '<section class="clearfix">'
            innerHtml += '<h4>颜色分类</h4>'
            innerHtml += '<div class="choice_ysStr">'
            innerHtml += ysStr
            innerHtml += '</div>'
            innerHtml += '</section>'
        }
        innerHtml += '<section class="clearfix">'
            innerHtml += '<p>购买数量</p>'
            innerHtml += '<div>'
                innerHtml += '<img class="Specifications_reduce" onclick="removeSpecification(this);" src="'+basePath+'images/scMobile/wholesaleMall/Specifications_reduce.png"/>'
                innerHtml += '<input type="number" class="purchase_quantity" name="" id="input_money" disabled value="1"/>'
                innerHtml += '<img class="Specifications_add" onclick="addSpecification(this);" src="'+basePath+'images/scMobile/wholesaleMall/Specifications_add.png"/>'
            innerHtml += '</div>'
        innerHtml += '</section>'
        innerHtml += '<input type="button" onclick="surePecifications(this,\''+codeId+'\',\''+goodsId+'\');" name="" class="pecifications_del" id="sure" value="确定" />'
        $("#ttsw_ttsw_three_cm_goods_Classify").append(innerHtml);
    }

    //加号点击
    $(".reduce_number").hide();
    $(".number").hide();
    function addNumber(obj,codeId,goodsId,goodsImg,goodsPrice){
        //获取产品规格
        ajaxGetGGFlList(obj,codeId,goodsId,goodsImg,goodsPrice);
        //显示价格及数量
        if($(obj).data("num")==1){
            //页面显示加减数量
            $(obj).siblings(".number").show();//显示数量
            //显示确定按钮用
            $(".pecifications").animate({
                bottom:0
            },"normal");
        }else{
            var num=$(obj).siblings(".number").val();//添加个数
            if(num<99){
                num++;
            }else{
                num=99
            }
            $(obj).siblings(".number").val(num);//赋值
            if(num>=1){
                $(obj).siblings(".number").show();
                $(obj).siblings(".reduce_number").show();
                $(obj).parents("li.clearfix").addClass("number_num_z");
            }
            //添加数据
            ajaxAddShoppingCart(codeId,goodsId,0);
        }
    }

    //减号点击
    function reduceNumber(obj,codeId,goodsId){
        var num=$(obj).siblings(".number").val();
        if(num>0){
            num--;
        }else{
            num=0;
        }
        $(obj).siblings(".number").val(num);
        if(num==0){
            $(obj).siblings(".number").hide();
        }
        if(num<=0){
            $(obj).hide();
            $(obj).parents("li.clearfix").removeClass("number_num_z");
        }
        //异步删除数据
        ajaxRemoveShoppingCart(codeId,goodsId);
    }
    //弹框数量增加
    function addSpecification(obj){
        var num=$(obj).siblings(".purchase_quantity").val();
        if(num<99){
            num++;
        }else{
            num=99
        }
        $(obj).siblings(".purchase_quantity").val(num);
    }
    //弹框数量减少
    function removeSpecification(obj){
        var num=$(obj).siblings(".purchase_quantity").val();
        if(num>1){
            num--;
        }else{
            num=1
        }
        $(obj).siblings(".purchase_quantity").val(num);
    }
    //规格相关操作
    function toCheck_p(obj, str,apid) {
        $(".choice_"+str+" p").removeClass("active");
        $(obj).addClass("active");
        //向隐藏域赋值
        var checkStr = $(obj).text();//选中的值
        $('#ttsw_three_check_hide_'+str).val(checkStr);
        $('#ttsw_three_check_hide_'+str+'_apid').val(apid);
    }

    //有产品规格、颜色的方法
    //弹框关闭按钮
    var pecifications_hei=$(".pecifications").outerHeight(true);
    $(".pecifications").css("bottom",-pecifications_hei+"px");
    //点击删除
    function delPecifications() {
        $("body").removeClass("body_yc");
        $(".pecifications").empty();
    }
    //弹框确定关闭按钮
    function surePecifications(obj,codeId,goodsId){
        var num=parseFloat($(obj).prev().find(".purchase_quantity").val());
        if(num == 0){
            alert('商品数量不能为0');
            return false;
        }
        var oldNum = parseFloat($('#ttsw_three_num_'+codeId+'_'+goodsId).val());
        $('#ttsw_three_num_'+codeId+'_'+goodsId).val(oldNum+num);
        //添加数据
        ajaxAddShoppingCart(codeId,goodsId,1);
        //样式修改
        $(".box_right menu li.active").addClass("number_num_z");
        $("body").removeClass("body_yc");
        $(".pecifications").empty();
    }

    /**
     * 异步将商品添加到购物车
     * @param codeId 二级商品分类id
     * @param goodsId 三级货物id
     * @param tjFlag 添加标识0:直接添加1：选中产品规格、颜色添加
     */
    function ajaxAddShoppingCart(codeId,goodsId,tjFlag){
        //选中货物信息
        var  ppid = $("#ttsw_three_hide_ppid_"+codeId+"_"+ goodsId).val();//产品id
        var  wholesale = $("#ttsw_three_hide_wholesale_"+codeId+"_"+ goodsId).val();//批发价
        var  wholesaleId = $("#ttsw_three_hide_wholesaleId_"+codeId+"_"+ goodsId).val();//批发价id
        var  totalWhoPrice = $("#ttsw_three_hide_totalWhoPrice_"+codeId+"_"+ goodsId).val();//使用红包后增幅价格
        var  allPrice = $("#ttsw_three_hide_allPrice_"+codeId+"_"+ goodsId).val();//总价=批发价+使用红包后增幅价格
        var  goodsid = $("#ttsw_three_hide_goodsid_"+codeId+"_"+ goodsId).val();//货物id
        var  goodsName = $("#ttsw_three_hide_goodsName_"+codeId+"_"+ goodsId).val();//货物名称
        var  image = $("#ttsw_three_hide_image_"+codeId+"_"+ goodsId).val();//图片路径
        var  companyid = $("#ttsw_three_hide_companyid_"+codeId+"_"+ goodsId).val();//公司id
//        var  standard = $("#ttsw_three_hide_standard_"+codeId+"_"+ goodsId).val();//规格
        var  barCode = $("#ttsw_three_hide_barCode_"+codeId+"_"+ goodsId).val();//条码
        var tjNum = 1;//添加数量
        var cmStr,cmApid,ysStr,ysApid,ftStr,ftApid,spStr,spApid;
        cmStr=cmApid=ysStr=ysApid=ftStr=ftApid=spStr=spApid="";
        if(tjFlag == 1){//有产品规格及颜色
              cmStr = $('#ttsw_three_check_hide_cmStr').val();//选中尺码
              cmApid = $('#ttsw_three_check_hide_cmStr_apid').val();//选中尺码apid
              ysStr = $('#ttsw_three_check_hide_ysStr').val();//选中颜色
              ysApid = $('#ttsw_three_check_hide_ysStr_apid').val();//选中颜色apid
              ftStr = $('#ttsw_three_check_hide_ftStr').val();//选中副图
              ftApid = $('#ttsw_three_check_hide_ftStr_apid').val();//选中副图apid
              spStr = $('#ttsw_three_check_hide_spStr').val();//选中视频
              spApid = $('#ttsw_three_check_hide_spStr_apid').val();//选中视频apid
              tjNum = $("#input_money").val();//添加数量
        }
        var standard = "";
        if(cmStr!=""&&ysStr!=""){
        	standard = cmStr+" "+ysStr; 
        }else if(cmStr==""&&ysStr!=""){
        	standard = ysStr; 
        }else if(cmStr!=""&&ysStr==""){
        	standard = cmStr; 
        }else{
        	standard = "默认规格"
        }
        
        
        //异步获取数据
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxAddShoppingCart.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "pfscShoppingCart.codeId": codeId,//二级商品分类id
                "pfscShoppingCart.ppid": ppid,//产品id
                "pfscShoppingCart.wholesale": wholesale,//批发价
                "pfscShoppingCart.wholesaleId": wholesaleId,//批发价id
                "pfscShoppingCart.totalWhoPrice": totalWhoPrice,//使用红包后增幅价格
                "pfscShoppingCart.allPrice": allPrice,//总价=批发价+使用红包后增幅价格
                "pfscShoppingCart.goodsid": goodsid,//货物id
                "pfscShoppingCart.goodsName": goodsName,//货物名称
                "pfscShoppingCart.image": image,//图片路径
                "pfscShoppingCart.companyid": companyid,//公司id
                "pfscShoppingCart.standard": standard,//规格
                //有产品规格及颜色
                "pfscShoppingCart.cmStr": cmStr,//选中尺码
                "pfscShoppingCart.cmApid": cmApid,//选中尺码apid
                "pfscShoppingCart.ysStr": ysStr,//选中颜色
                "pfscShoppingCart.ysApid": ysApid,//选中颜色apid
                "pfscShoppingCart.ftStr": ftStr,//选中副图
                "pfscShoppingCart.ftApid": ftApid,//选中副图apid
                "pfscShoppingCart.spStr": spStr,//选中视频
                "pfscShoppingCart.spApid": spApid,//选中视频apid
                "pfscShoppingCart.tjNum": tjNum,//添加数量
                "pfscShoppingCart.tjFlag": tjFlag,//添加标识
                "pfscShoppingCart.barCode": barCode//条码
            },
            dataType: "json",
            success: function (data) {
                var member = (new Function("return " + data))();//格式化返回参数
                $('#ttsw_three_hide_psc_'+codeId+'_'+goodsId).val(member.pscId);//购物车id
                //添加购物车数量及金额
                addShopNumAndPrice(member.shopNum,member.shopPrice,member.shopTypeNum);
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }

    /**
     * 添加购物车数量及金额
     * @param shopNum 购物车总数
     * @param shopPrice 购物车总金额
     * @param shopTypeNum 购物车商品种类
     */
    function addShopNumAndPrice(shopNum,shopPrice,shopTypeNum){
        $('#num_shop').text(shopNum);//购物车总数
        $('#number_shop').text(shopPrice);//购物车总金额
        $('#number_num').text(shopTypeNum);//购物车商品种类
    }


    /**
     * 异步移除购物车商品
     * @param codeId 二级商品分类id
     * @param goodsId 三级货物id
     */
    function ajaxRemoveShoppingCart(codeId,goodsId){
        var pscId = $('#ttsw_three_hide_psc_'+codeId+'_'+goodsId).val();//购物车id
        //异步获取数据
        var url = basePath + "ea/wholesaleMall/sajax_ea_ajaxRemoveShoppingCart.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "POST",
            async: false,
            data: {
                "pfscShoppingCart.codeId": codeId,//二级商品分类id
                "pfscShoppingCart.goodsid": goodsId,//产品id
                "pfscShoppingCart.pscId": pscId,//购物车id
            },
            dataType: "json",
            success: function (data) {
                var member = (new Function("return " + data))();//格式化返回参数
                //添加购物车数量及金额
                addShopNumAndPrice(member.shopNum,member.shopPrice,member.shopTypeNum);
            },
            error: function (data) {
                alert("数据获取失败！");
            }
        });
    }

    //跳转购物车列表
    function toShopCartList(){
            //判断是否是大屏终端
            //var posNum = Android.forAndroidDeviceId();
            var url = "";
            if (posNum == null || posNum == "") {//跳转小屏
                url = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa?companyName=" + companyName + "&ccompanyID=" + ccompanyID;
            } else {//跳转大屏
                posNum = isExistPosNum(posNum);
                url = basePath + "ea/wholesaleMall/ea_shoppingCartForDpList.jspa?posNum=" + posNum + "&ccompanyID=" + ccompanyID;
            }
            window.location.href = url;
    }

    //跳转模糊查询
    function toGoodsSearch(){
        var search = $("#ttsw_search_id").val();
        window.location.href = basePath + "ea/wholesaleMall/ea_toGoodsSearch.jspa?search="+search+"&companyName="+companyName+"&ccompanyID="+ccompanyID+"&lxType=0&ccomIDPlatform="+ccomIDPlatform+"&phl=phl";
    }

    //跳转查询全部商家
    function toBussList(){
        window.location.href = basePath + "ea/wholesaleMall/ea_toBussList.jspa";
    }


    //判断是否是终端机器
    function isExistPosNum(posNum){
        var url = basePath + "ea/smg/sajax_sm_isExistPosNum.jspa";
        $.ajax({
            url : url,
            type : "get",
            dataType : "json",
            async:false,
            data : {
                posNum:posNum
            },
            success : function(data) {
                var m = eval("(" + data + ")");
                var result = m.result;
                if(result!="0"){
                    posNum = "";
                }
            },
            error : function(data) {
                // alert("验证失败");
            }
        });
        return posNum;
    }

    //记录要返回的页面
    function backUrl(){
        if(localStorage!=null) {
            localStorage.setItem("backUrl", window.location.href);
        }
    }

}

