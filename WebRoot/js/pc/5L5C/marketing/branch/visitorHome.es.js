import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'

// 设置页面标题
setHeaderTitle('未注册粉丝')

createApp({
    page: {
        singleRowNavs: [
            {
                text: "个人粉丝",
                to: "#"
            },
            {
                text: "移动粉丝",
                to: "#"
            },
            {
                // text: "导入粉丝",
                text: "导入数据",
                to: "#"
            },
            {
                text: "办理购物卡",
                to: "#"
            },
            {
                text: "新增个人",
                to: "#"
            },
            {
                text: "visitorFansList 粉丝列表",
                to: "/page/pc/5L5C/marketing/visitorFansList.jsp"
            },
            // {
            //     text: "visitorImport 导入粉丝",
            //     to: "/page/pc/5L5C/marketing/visitorImport.jsp"
            // },
            {
                text: "visitorImport2 导入粉丝",
                to: "/page/pc/5L5C/marketing/visitorImport2.jsp"
            },
            {
                // text: "visitorImport 导入粉丝",
                text: "visitorImport1 导入数据",
                to: "/page/pc/5L5C/marketing/visitorImport1.jsp"
            },
            {
                text: "visitorMessagesCreate 创建模板",
                to: "/page/pc/5L5C/marketing/visitorMessagesCreate.jsp"
            },
            {
                text: "visitorMessagesHome 模板列表",
                to: "/page/pc/5L5C/marketing/visitorMessagesHome.jsp"
            },
            {
                text: "visitorMessagesSend 群发短信",
                to: "/page/pc/5L5C/marketing/visitorMessagesSend.jsp"
            }
        ],
    },
    event: {
        singleRowNavAction(ev, to) {
            document.location.assign(to)
        }
    }
}).mount('#main')