$(function () {
    $("#searchinput").click(function () {
        document.location.href = basePath + "page/ea/main/production/cprocedure/qrshare/news_search.jsp";

    });
    if ($(".div-top .active").text().indexOf("资讯") !== -1) {
        $("#iframe").attr("src", basePath + "ea/wfjshop/ea_getNewsList.jspa?typeNews=&main=1");
        $("#iframe").attr("height", $(window).height());

    } else if ($(".div-top .active").text().indexOf("推荐") != -1) {
    }

    $("#zx").click(function () {
        $(".div-top .active").removeClass("active");
        $(this).addClass("active");
        $("#iframe").attr("height", $(window).height());

        $("#iframe").show();
    });
    $("#tuijian").click(function () {
        document.location.href = basePath + "ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web&tj=tj";

    });


    //个人发布
    $(".grfb").click(function () {
        $(".zx-div").hide();
        document.location.href = basePath + "ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02";
    });
    //公司发布
    $(".gsfb").click(function () {
        $(".zx-div").hide();
        document.location.href = basePath + "page/WFJClient/pc/5l5C/selectCompany.jsp?sccId=" + sccid + "&bd=zx";

    });
    $(".close-zx").click(function () {
        $(".zx-div").hide();

    });


});

document.querySelectorAll(".div-top .clearfix p").forEach(p => {
    p.addEventListener("click", function () {
        let parent = this.parentNode;

        // 如果已经是第一个就不用动
        if (parent.firstElementChild === this) return;

        // 移到第一个
        parent.insertBefore(this, parent.firstElementChild);
    });
});

function
hidewin(it) {
    console.log("关闭执行")
    it.style.display = "none";
}

//考场约车
function preCar() {

    if (sccid == null || sccid == "") {

        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else {
        document.location.href = basePath + "ea/mappointment/ea_theTestTime.jspa?sccId=" + sccid + "&sc=web";
    }

}

//拼货拉农超
function phl() {
    if (sccid == "") {
        document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

    } else if (companyID == "" || companyID == "null") {
        document.location.href = basePath + "ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany";

    } else {

        document.location.href = basePath + "ea/phl/ea_getPhlIndex.jspa";
    }


}

// 定义所有菜单（id 对应元素的唯一标识）
const allMenus = [
    {
        id: "tuijian",
        name: "推荐",
        url: "/ea/industry/ea_getCompanyHome.jspa?ccompanyId=contactCompany20170107NT6PP8C9X40000029147&industryType=&etype=&sc=web&tj=tj"
    },
    {id: "guanzhu", name: "关注", url: "/ea/earth/ea_getBrowseCompanyList.jspa"},
    {id: "choujiang", name: "抽奖", url: "/ea/5l5c/ea_selectCompany.jspa"},
    {id: "zixun", name: "资讯", url: "/ea/earth/ea_earthIndex.jspa"},
    {id: "shipin", name: "小视频", url: "/ea/dsp/ea_fullScreen.jspa"},
    // {id: "zhibo", name: "直播", url: ""},
    {id: "gouwu", name: "购物", url: "/ea/digitalmall/ea_DigitalMall.jspa?back=index"},
    {id: "zhoubian", name: "周边", url: "/ea/qyrz/ea_toPeriphery.jspa"},
    {id: "shichang", name: "市场", url: "javascript:phl()"},
    {id: "zhaoshang", name: "招商", url: "/ea/productAgent/ea_productAgentList.jspa"},
    {id: "zhaobiao", name: "招标", url: "/ea/purchasebids/ea_findGoodbidList.jspa"},
    {id: "zhaopin", name: "招聘", url: "/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs"},
    {id: "chaoshi", name: "超市", url: "/page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp"},
    {id: "shangcheng", name: "购物快车", url: "/page/ea/main/marketing/unmannedsupermarket/supermarket_home1.jsp"},

    {id: "shangjia", name: "商家", url: "/ea/industry/ea_getAllCompanyList.jspa?industryType="},

    {id: "ershouche", name: "二手车", url: ""},
    {id: "jiayingji", name: "家应急", url: ""},
    {id: "cisan", name: "慈善", url: ""},
    {id: "gjpt", name: "国际平台", url: ""},
    {id: "jzgc", name: "建筑工程", url: ""},
    {id: "zsgc", name: "装饰工程", url: ""},
    {id: "dqwx", name: "电器维修", url: ""},
];
// 固定菜单 id，不允许取关，始终显示在关注区
const fixedMenus = ["tuijian", "zixun","shangcheng" ,"shipin"];
// const fixedMenus = ["tuijian", "shangcheng"];

// 可关注菜单（除固定菜单外）
const followableMenus = allMenus.filter(m => !fixedMenus.includes(m.id));

// 获取用户关注菜单
function getFollowMenus() {
    return JSON.parse(localStorage.getItem("followMenus")) || ["tuijian"];
}

// 渲染顶部菜单
function renderMenuBar() {
    let follows = getFollowMenus();

    // 先去重（避免 localStorage 里存重复）
    follows = [...new Set(follows)];

    const menuContainer = document.querySelector(".div-top .clearfix");
    const cle2=document.getElementById("cle2");
    menuContainer.innerHTML = "";
    cle2.innerHTML="";
    // 固定菜单先显示
    fixedMenus.forEach(fid => {
        const menu = allMenus.find(m => m.id === fid);
        if (menu) {
            // 如果是 Android 客户端，过滤掉一些特定菜单
            if (typeof Android !== 'undefined'&&(menu.id==='shipin'||menu.id==='zixun')) {
                console.log("过滤："+menu.name)

                return;
            }
            // 否则正常显示
            menuContainer.innerHTML += `<p id="${menu.id}"><a href="${menu.url}">${menu.name}</a></p>`;
        }
    });
    const followRow = document.createElement("div");
    followRow.className = "menu-row follow-row";
    // 可关注菜单显示用户选择的
    // follows.forEach(id => {
    //     if (!fixedMenus.includes(id)) {
    //         const menu = allMenus.find(m => m.id === id);
    //         if (menu) followRow.innerHTML += `<p id="${menu.id}"><a href="${menu.url}">${menu.name}</a></p>`;
    //     }
    // });

    // 根据是否是 Android 客户端筛选菜单
    follows.forEach(id => {
        console.log("显示："+follows)
        if (!fixedMenus.includes(id)) {
            let menu = allMenus.find(m => m.id === id);
            if (menu === undefined)
            {
                arr=id.split("|")
                menu = {
                    id: arr[0],
                    name: arr[1],
                    url: "/page/ea/main/marketing/unmannedsupermarket/supermarket_home1.jsp?keyword="+arr[1]
                };
            }
            if (menu) {
                // 如果是 Android 客户端，过滤掉一些特定菜单
                if (typeof Android !== 'undefined' && (id === "ershouche"
                    ||id ==="choujiang"
                    ||id ==="guanzhu"
                    ||id ==="choujiang"
                    ||id ==="shipin"
                    ||id ==="zhaobiao"
                    ||id ==="zhaopin"
                    ||id ==="choujiang"
                    ||id ==="choujiang"
                    ||id ==="choujiang")) {
                    return;
                }


                // 否则正常显示
                followRow.innerHTML += `<p id="${menu.id}"><a href="${menu.url}">${menu.name}</a></p>`;
            }
        }
    });

    cle2.appendChild(followRow);
    // menuContainer.appendChild(followRow);


}
let searchTimer = null; // 防抖定时器
let originalListUl = null; // 保存第一次渲染的原始列表
// 显示弹窗
function showMenuManager() {
    const follows = getFollowMenus();
    const followList = document.getElementById("follow-list");
    unfollow = document.getElementById("unfollow-list");

    followList.innerHTML = "";
    unfollow.innerHTML = "";

    // 关注区：固定菜单 + 用户关注的可关注菜单
    fixedMenus.forEach(fid => {
        const menu = allMenus.find(m => m.id === fid);
        followList.innerHTML += `<p class="fixed">${menu.name}</p>`;
    });

    follows.forEach(id => {
        if (!fixedMenus.includes(id)) {

            let menu = allMenus.find(m => m.id === id);

            if (!menu) {
                console.warn("跳过不存在的 menu:", id);
                arr=id.split("|")
                console.warn(" menu:", arr);

                // ✅ 创建新对象
                menu = {
                    id: arr[0],
                    name: arr[1],
                    url: "/page/ea/main/marketing/unmannedsupermarket/supermarket_home1.jsp"
                };
                console.warn("分割后", menu.id+"   "+menu.name);
            }

            followList.innerHTML +=
                `<p onclick="toggleMenu('${menu.id}', false)">${menu.name}</p>`;
        }
    });

    // 未关注区：用户未关注的可关注菜单
    followableMenus.forEach(menu => {
        if (!follows.includes(menu.id)) {
            unfollow.innerHTML += `<p onclick="toggleMenu('${menu.id}', true)">${menu.name}</p>`;
        }
    });
    // 渲染搜索框（追加）
    unfollow.insertAdjacentHTML("beforeend", `
      <div class="div-search">
        <div class="search-box clearfix">
          <div class="div-left">
            <p><img src="/images/WFJClient/pc/newimg/img_13.png" alt=""></p>
          </div>
          <input type="text" placeholder="请输入搜索内容" id="type_search">
          <div class="sousou"><a href="/page/WFJClient/pc/earth/soso.jsp"></a></div>
        </div>
      </div>
    `);
    initRoot(unfollow);
    document.getElementById("menu-modal").style.display = "flex";
    const searchInput = document.getElementById("type_search");
    let isComposing = false;

// 监听拼音输入开始
    searchInput.addEventListener("compositionstart", () => {
        isComposing = true;
    });

// 拼音输入结束
    searchInput.addEventListener("compositionend", () => {
        isComposing = false;
    });


// 绑定搜索输入事件
    document.getElementById("type_search").addEventListener("input", function () {
        const keyword = this.value.trim();

        // 清除之前的定时器
        if (searchTimer) clearTimeout(searchTimer);

        // 延迟 500ms 后执行
        searchTimer = setTimeout(() => {
            if (!keyword) {
                console.warn("搜索词为空:"+keyword);
                // 搜索框为空，显示原始列表
                doSearch(keyword);
            } else {
                console.warn("开始执行搜索:"+keyword);
                console.warn("拼音加测状态:"+isComposing);

                if (!isComposing){
                    // 搜索框有值 → 发起搜索
                    doSearch(keyword);
                }

            }
        }, 500);
    });
}
// 调用接口
function initRoot(){


    const url = basePath+'/ea/scBudget/sajax_ea_getBusinessTypeRoot.jspa';
    $.ajax({
        url: url,
        type: "GET",   // 这里用 GET，因为是查询接口
        async: true,   // 默认就是 true，可以不写
        data: {

        },
        dataType: "json",
        success: function (data) {
            renderList(unfollow, data);

        },
        error: function (xhr, status, error) {

        }
    });
}

let rootUl = null;     // 保存初始布局
let searchUl = null;   // 保存搜索布局
let unfollow = null;   // 你已有全局的 unfollow 容器
let startcode='';
// 渲染列表（初始数据）
function renderList(container, data) {
    let obj = JSON.parse(data);
    const list = obj.rootList;

    // 创建子布局（ul）
    rootUl = document.createElement("ul");
    rootUl.style.listStyle = "none";
    rootUl.style.padding = "0";
    rootUl.style.margin = "0";
    list.forEach(item => {
        const id = item[0];
        const codeId = item[3];

        const name = item[5];
        const desc = item[8];

        const li = document.createElement("li");
        li.textContent = name;
        li.title = desc;
        li.style.cursor = "pointer";
        li.style.padding = "8px 12px";
        li.style.borderBottom = "1px solid #eee";
        li.style.display = "block";
        li.style.width = "100%";
        li.className = "industry-item";

        li.addEventListener("click", function () {
            console.log("点击的codeId"+codeId);
            doSearchById(codeId);
        });
        rootUl.appendChild(li);
    });

    container.appendChild(rootUl);
}
function doSearchById(id){
    console.log(id);
    if (id=== undefined){
        if (!id) {
            // alert("请选择！");
            return;
        }
    }
    const url= basePath + '/ea/scBudget/sajax_ea_getBusinessTypesById.jspa?id='+id;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (rootUl) rootUl.style.display = "none";  // 隐藏原始列表
            if (searchUl) searchUl.remove();
            renderSearchList(unfollow, data);
        },
        error: function (xhr, status, error) {
            console.error("搜索失败：", error);
        }
    });
}
function isChinese(str) {
    // ^ 和 $ 表示从头到尾匹配
    // + 表示一个或多个汉字
    return /^[\u4e00-\u9fa5]+$/.test(str);
}
// 搜索逻辑
function doSearch(keyword) {
    if (keyword === undefined) {
        keyword = document.getElementById("type_search").value.trim();
    }

    if (!keyword) {
        if (!isChinese(keyword)){
            return;
        }
        if (searchUl) {
            searchUl.remove();
            searchUl = null;
        }
        if (rootUl) rootUl.style.display = "block";
        return;
    }
    const url = basePath + '/ea/scBudget/sajax_ea_getBusinessTypeLikeName.jspa?keyword=' + keyword;
    $.ajax({
        url: url,
        type: "GET",
        data: { keyword: keyword },
        dataType: "json",
        success: function (data) {

            console.log("搜索结果返回data：", data);
            if (rootUl) rootUl.style.display = "none";  // 隐藏原始列表
            if (searchUl) searchUl.remove();
            renderSearchList(unfollow, data);
        },
        error: function (xhr, status, error) {
            console.error("搜索失败：", error);
        }
    });
}
function renderSearchList(container, data) {
    let obj;
    try {
        obj = typeof data === "string" ? JSON.parse(data) : data;
    } catch (e) {
        console.error("解析 JSON 失败", e, data);
        return;
    }

    const list = obj.codeList || [];

    if (!list || list.length === 0) {
        // container.innerHTML = "<p>没有找到匹配项</p>";
        return;
    }

    searchUl = document.createElement("ul");
    searchUl.style.listStyle = "none";
    searchUl.style.padding = "0";
    searchUl.style.margin = "0";

    list.forEach(item => {
        if (item.level>3){
            if (String(item.codeID).startsWith(String(startcode))){
                console.warn(item.codeId)

                const li = document.createElement("li");
                li.textContent = item.name;
                li.title = item.name;
                li.style.cursor = "pointer";
                li.style.padding = "8px 12px";
                li.style.borderBottom = "1px solid #eee";
                li.style.display = "block";
                li.style.width = "100%";
                li.className = "industry-item";

                li.addEventListener("click", function () {
                    console.log("点击搜索结果："+ item.toString());
                    if (item.level<3){
                        startcode=item.codeId
                        doSearchById(item.codeId);
                    }else {
                        console.warn("该列已经是子菜单，执行子菜单操作");
                        followableMenus
                        toggleMenu(item.codeId+"|"+item.name,true);

                    }

                });

                searchUl.appendChild(li);
            }

        }

    });

    container.appendChild(searchUl);
    startcode='';
}

// 切换菜单
function toggleMenu(id, follow) {
    let follows = getFollowMenus();
    console.log(follows)
    if (follow) {
        if (!follows.includes(id)) follows.push(id);
    } else {
        follows = follows.filter(mid => mid !== id);
    }
    // 存之前去重
    follows = [...new Set(follows)];
    localStorage.setItem("followMenus", JSON.stringify(follows));
    showMenuManager();
}

// 保存关闭弹窗
function saveMenus() {
    renderMenuBar();
    hidewin();
}

// 关闭弹窗
function hidewin() {
    document.getElementById("menu-modal").style.display = "none";
}


// 初始化
document.addEventListener("DOMContentLoaded", renderMenuBar);


//咨询发布选择个人或者公司发不
function zxselect() {

    if (sccid == "") {
        document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
        return false;

    }

    var url = basePath + "ea/android/sajax_ea_findConpany.jspa";
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        aysnc: false,
        data: {
            sccId: sccid
        },
        success: function (data) {
            var comlist = data.company;
            if (comlist.length > 0) {
                $(".zx-div").show();
                restoreFunctionTags();
            } else {
                document.location.href = basePath + "ea/qrshare/ea_qrshareList.jspa?miniSystemJudge=03&androidJudge=02";
            }


        },
        error: function (data) {

        }
    })

}

