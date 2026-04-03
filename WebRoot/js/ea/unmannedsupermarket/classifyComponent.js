let unfollow = null;   // 你已有全局的 unfollow 容器
let rootUl = null;     // 保存初始布局
let startcode = '';
let clickSet = new Set();
let callImpl=null;
let searchTimer = null; // 防抖定时器
let body=null;
(function (global) {


    /**
     * 初始化（由外部注入）
     */
    function init(options = {}) {
        console.log("✅ ClassifyComponent.init 执行", options);
        callImpl = options.callImpl;
    }


    // 只暴露一个全局对象
    global.ClassifyComponent = {
        init
    };

})(window);

//更多
// ===== 显示弹窗 =====
function showMore() {
    const modal = document.getElementById("menu-modal");
    body = document.getElementById("menu-body");
    // 清空旧内容（非常重要）
    body.innerHTML = "";

    // // 插入搜索框
    // body.insertAdjacentHTML("beforeend", `
    //     <div class="div-search">
    //         <input type="text" id="type_search" placeholder="请输入搜索内容">
    //     </div>
    // `);

    initRoot(body);

    modal.style.display = "flex";

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


// ===== 隐藏弹窗 =====
function hidewin() {
    document.getElementById("menu-modal").style.display = "none";
}


// ===== 加载列表数据 =====
function initRoot(container) {
    const url = basePath + '/ea/scBudget/sajax_ea_getBusinessTypeRoot.jspa';

    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            renderList(container, data);
        },
        error: function (xhr, status, error) {
            console.log("加载失败:", error);
        }
    });
}

// ===== 渲染列表 =====
function renderList(container, data) {


    // ⭐ 关键：兼容 string / object
    if (typeof data === "string") {
        try {
            data = JSON.parse(data);
        } catch (e) {
            console.error("JSON 解析失败", data);
            return;
        }
    }

    if (!data.rootList || !Array.isArray(data.rootList)) {
        console.error("数据格式异常", data);
        return;
    }

    const list = data.rootList;

    const ul = document.createElement("ul");
    list.forEach(item => {
        const codeId = item[3];   // A / B / C ...
        const name = item[5];   // 行业名称
        const desc = item[8];   // 描述

        const li = document.createElement("li");
        li.className = "industry-catalog";

        li.textContent = name;
        li.title = desc;

        li.onclick = function () {
            if (clickSet.has(codeId)) {
                [...li.children].forEach(child => child.remove());
                clickSet.delete(codeId);
                return;
            }
            clickSet.add(codeId)
            doSearchById(codeId, li);
            // hidewin();
        };

        ul.appendChild(li);
    });

    container.appendChild(ul);
    // bindSearch(ul);
}

function doSearch(keyword) {
    if (keyword === undefined) {
        keyword = document.getElementById("type_search").value.trim();
    }

    if (!keyword) {
        if (!isChinese(keyword)){
            if (keyword===null||keyword==="")
            {
                console.log("supermarketSearch");
                body.innerHTML=""
                initRoot(body)
            }
            return;
        }
        if (searchUl) {
            console.log("searchUl.remove()");
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
            if (body)  body.innerHTML = "";
            renderSearchList(unfollow, data,body);
        },
        error: function (xhr, status, error) {
            console.error("搜索失败：", error);
        }
    });
}
// ===== 搜索过滤 =====
function bindSearch(ul) {
    const input = document.getElementById("type_search");
    if (!input) return;

    input.addEventListener("input", function () {
        const keyword = this.value.trim().toLowerCase();
        const items = ul.querySelectorAll("li");

        items.forEach(li => {
            li.style.display = li.textContent.toLowerCase().includes(keyword)
                ? "block"
                : "none";
        });
    });
}


function doSearchById(id, li) {

    if (id === undefined) {
        if (!id) {
            // alert("请选择！");
            return;
        }
    }
    const url = basePath + '/ea/scBudget/sajax_ea_getBusinessTypesById.jspa?id=' + id;
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            // if (rootUl) rootUl.style.display = "none";  // 隐藏原始列表
            // if (searchUl) searchUl.remove();
            renderSearchList(unfollow, data, li);
        },
        error: function (xhr, status, error) {
            console.error("搜索失败：", error);
        }
    });
}

function renderSearchList(container, data, root) {
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
        if (item.level > 2) {
            if (String(item.codeID).startsWith(String(startcode))) {
                const li = document.createElement("li");
                li.textContent = item.name;
                li.title = item.name;
                li.className = `industry-item level-${item.level}`;
                li.addEventListener("click", (event) => {
                    event.stopPropagation();


                    if (typeof callImpl === "function") {
                        callImpl(item);
                    } else {
                        console.warn("❌ callImpl 不是函数", callImpl);
                    }

                    // 子菜单逻辑
                    // doSubMenuAction(item);
                });
                searchUl.appendChild(li);
                console.log(root)
                root.appendChild(searchUl)
            }

        }

    });

    // container.appendChild(searchUl);
    startcode = '';
}

function isChinese(str) {
    // ^ 和 $ 表示从头到尾匹配
    // + 表示一个或多个汉字
    return /^[\u4e00-\u9fa5]+$/.test(str);
}