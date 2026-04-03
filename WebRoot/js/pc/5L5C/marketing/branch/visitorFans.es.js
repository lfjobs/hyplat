import {createApp,nextTick} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {sendAxiosPostFast, setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'
import {getCookie,createElement,arrayAppend} from "/js/pc/5L5C/marketing/stdlib.js";

setHeaderTitle("粉丝列表");
const elMain = document.getElementById('main');
const elLoadWaitContainer = elMain.querySelector(".load-wait-container");
const elTemplate = document.getElementById("template");
const tableRequest = {
    ready: true,
    innerHTML:false
}

let mainApp = {
    tableFields: ['姓名', '身份证', '电话', '地址', '驾照'],
    tableRows: [],
    tableIndex:0,
    addTableRow(dataList){
        dataList.forEach(item => {
            this.tableRows.push(item);
        });
    },
    onMounted:function (){
        mainApp = this;
    }
};

/**
 * 分页获取 - 标记对象
 */
const pageMark = {
    importerId: getCookie("key_customer"),
    sizePage: 40,
    currentPage: 1,
    totalPages: -1,
    totalRecords: -1
}

/**
 * 获取页面数据
 */
function getPageData() {
    if (!tableRequest.ready) {
        // 上次数据请求还未结束，直接拒绝这次数据请求。
        return;
    }
    else if (pageMark.totalPages > 0 && pageMark.currentPage > pageMark.totalPages) {
        // 数据已经全部加载完毕
        tableRequest.ready = false;
        elLoadWaitContainer.style.visibility = 'hidden';
        elMain.insertBefore(createElement("p", "数据已经全部加载完毕...."), elLoadWaitContainer);
        return;
    }

    tableRequest.ready = false;
    sendAxiosPostFast("/crm/controller/customer/find/page", {
        prototype:'standard',
        importerId: getCookie("key_customer"),
        index: pageMark.currentPage,
        size: pageMark.sizePage
    }).then((pageData) => {
        // 需要判断 HTML 是否已经加载
        displayTable(pageData);
    }).catch((error) => {
        // 请求或响应错误处理
        elMain.appendChild(createElement("p", "请求数据失败,请刷新页面后重新尝试！"))
        elMain.appendChild(createElement("p", "详细错误信息可以通过 F12 打开控制台查看。"))
        console.warn(error)
    }).finally(() => {
        tableRequest.ready = true;
    })
}

/**
 * 显示 tableBox
 * @param {{data:[]}} pageData
 */
function displayTable(pageData){
    if(!tableRequest.innerHTML){
        elMain.insertBefore(elTemplate.content.children[0], elLoadWaitContainer);
        createApp(mainApp).mount('#tableBox');
        tableRequest.innerHTML = true;
    }

    pageMark.currentPage = pageData['currentPage'];
    pageMark.totalPages = pageData['totalPages'];
    pageMark.totalRecords = pageData['totalRecords'];
    pageMark.sizePage = pageData['sizePage'];

    // 数据请求成功后，改变到下一页
    pageMark.currentPage++

    nextTick(()=>{
        mainApp.tableRows = mainApp.tableRows.concat(pageData['data']);
    })
}

// 初始化一个实例
const observer = new IntersectionObserver(entries => {
    entries.forEach(entry => {
        if (entry.target === elLoadWaitContainer) {
            // isIntersecting = true; 目标元素进入容器视口
            if (entry.isIntersecting) {
                getPageData();
            }
        }
    });
}, {
    root: null,
    threshold: 1
});

// 对元素 target 添加监听，当 target元素变化时，就会触发上述的回调
observer.observe(elLoadWaitContainer);

// displayTable({
//     'data':[],
//     'currentPage':1,
//     'totalPages':40,
//     'totalRecords':4000,
//     'sizePage':40,
// })
