import {sendAxiosPostFast, setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'
import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {calculateCharLength, getCookie} from "../stdlib.js";

setHeaderTitle("群发短信");


const pageStore = {
    smsList: [],
    smsOptionIndex: -1,
}
const customerPageMark = {
    importerId: getCookie("key_customer"),
    sizePage: 80,
    currentPage: 1,
    totalPages: 999,
    totalRecords: -1
}

let smsApp = {
    selectSwitch: false,
    optionIndex: -1,
    optionValue: "",
    options: [],
    templateText: "",
    templateUpdatedAt: "",
    onMounted: function (el) {
        smsApp = this;

        /**
         * 获取已经过审的短信模板
         */
        sendAxiosPostFast("/crm/controller/sms/template/query", {
            customerKey: getCookie("key_customer"),
            'auditStatus': 4,
            'freezeStatus': 0
        })
            .then((dataList) => {
                if (Array.isArray(dataList)) {
                    pageStore.smsList = dataList;

                    dataList.forEach((item) => {
                        smsApp.options.push({
                            name: item['templateHeadline'],
                            value: item['id']
                        })
                    });
                }
            })
    },
    get templateCharLength() {
        if (this.templateText.length > 0) {
            return calculateCharLength(this.templateText) + "/200";
        } else {
            return "";
        }
    },
    eventAnimationSwitch: function () {

        if (this.options.length === 0) {
            return;
        }

        // 状态取反
        this.selectSwitch = !this.selectSwitch;

        const elSelect = this["$refs"]['elSelect'];
        const elSelectIcon = this["$refs"]['elSelectIcon'];

        if (this.selectSwitch) {
            elSelectIcon.style.transform = "rotate(270deg)";
            elSelect.style.transform = "scale(1, 1)";
        } else {
            elSelectIcon.style.transform = "";
            elSelect.style.transform = "scale(1, 0)";
        }
    },
    eventSelectOption: function (index) {
        const option = pageStore.smsList[index];
        this.optionIndex = index;
        this.optionValue = this.options[index].name;
        this.templateText = option['templateText'];
        this.templateUpdatedAt = option['updatedAt'];

        this.eventAnimationSwitch();

        pageStore.smsOptionIndex = index;
    }
}
let customerApp = {
    menuSwitch: false,
    menuSelect: [
        {
            name: "客户身份",
            key: "social",
            datList: [
                {
                    key: "social1",
                    name: "不限"
                },
                {
                    key: "social2",
                    name: "学员"
                },
                {
                    key: "social3",
                    name: "生产商"
                },
                {
                    key: "social4",
                    name: "优质客户"
                },
                {
                    key: "social5",
                    name: "微分金商城业主会员"
                },
                {
                    key: "social6",
                    name: "其他"
                }
            ],
            dataIndex: 0
        },
        {
            name: "客户来源",
            key: "source",
            datList: [
                {
                    key: "source1",
                    name: "不限"
                },
                {
                    key: "source2",
                    name: "自行导入"
                },
                {
                    key: "source3",
                    name: "系统分配"
                }
            ],
            dataIndex: 0
        },
        {
            name: "营销进度",
            key: "progress",
            datList: [
                {
                    key: "progress1",
                    name: "不限"
                },
                {
                    key: "progress2",
                    name: "未营销"
                },
                {
                    key: "progress3",
                    name: "已营销"
                },
                {
                    key: "progress4",
                    name: "转换成功"
                }
            ],
            dataIndex: 0
        }
    ],
    menuSelectIndex: 0,
    customerList: [],
    onMounted: function (el) {
        customerApp = this;

        // 初始化一个实例
        const observer = new IntersectionObserver(entries => {
            entries.forEach(entry => {
                // isIntersecting = true; 目标元素进入容器视口
                if (entry.isIntersecting) {
                    // 加载数据
                    getCustomerPageData();
                }
            });
        }, {
            root: document.getElementById("customerSelect"),
            threshold: 1
        });
        observer.observe(this.$refs['loadWait']);
    },
    get menuSelectOptions() {
        return this.menuSelect[this.menuSelectIndex].datList;
    },
    getMenuSelectOptionClass(item, index) {
        if (this.menuSelect[this.menuSelectIndex].dataIndex === index) {
            return 'active';
        } else {
            return '';
        }
    },
    eventMenuSelectSwitch: function (el, menuItem, index) {
        if (this.menuSwitch && this.menuSelectIndex !== index) {
            return;
        } else {
            this.menuSelectIndex = index;
        }

        const elMenuList = this["$refs"]['menuList'];
        const elSVG = el.querySelector(".icon-svg > svg");

        if (this.menuSwitch) {
            elMenuList.style.transform = "scale(1, 0)";
            elSVG.style.transform = "";
        } else {
            elMenuList.style.transform = "scale(1, 1)";
            elSVG.style.transform = "rotate(180deg)";
        }

        // 状态取反
        this.menuSwitch = !this.menuSwitch;
        console.log("end", this.menuSwitch);
    },
    eventMenuSelectOption: function (el, index) {
        let menuSelectItem = this.menuSelect[this.menuSelectIndex];
        menuSelectItem.dataIndex = index;

        const elMenuList = this["$refs"]['menuList'];
        const elMenuButton = this["$refs"]['menuButton'];
        const elMenu = elMenuButton.children[this.menuSelectIndex];
        const elMenuSVG = elMenu.querySelector(".icon-svg > svg");
        const elMenuName = elMenu.querySelector(".name");

        if (index === 0) {
            elMenuName.innerText = menuSelectItem.name;
        } else {
            elMenuName.innerText = menuSelectItem.datList[index].name;
        }

        elMenuList.style.transform = "scale(1, 0)";
        elMenuSVG.style.transform = "";
        this.menuSwitch = false;
    }
}
let dialogApp = {
    customerCount: 0,
    headline: "",
    templateCharLength: 0,
    calcIntegral: 0,
    calcMoney: 0,
    onMounted:function (el){
        // 获取其他组件中的数据
        dialogApp = this;
    },
    eventSubmit: function (value) {
        if (value === 'cancel') {

        } else if (value === 'confirm') {
            // 提交事件
        }
        elDialog.close();
    }
}

createApp(smsApp).mount('#shortMessage');
createApp(customerApp).mount('#customerSelect')
createApp(dialogApp).mount('#dialog')

function getCustomerPageData() {
    if (customerPageMark.totalPages > 0 && customerPageMark.currentPage > customerPageMark.totalPages) {
        // 数据已经全部加载完毕
        customerApp.$refs['loadWait'].style.display = 'none';
        return;
    }

    sendAxiosPostFast("/crm/controller/customer/find/page", {
        importerId: getCookie("key_customer"),
        index: customerPageMark.currentPage,
        size: customerPageMark.sizePage
    }).then((pageData) => {
        if (Array.isArray(pageData.data)) {
            customerApp.customerList = customerApp.customerList.concat(pageData.data);

            customerPageMark.currentPage = pageData['currentPage'];
            customerPageMark.totalPages = pageData['totalPages'];
            customerPageMark.totalRecords = pageData['totalRecords'];
            customerPageMark.sizePage = pageData['sizePage'];

            // 数据请求成功后，改变到下一页
            customerPageMark.currentPage++
        }
    })
}

const elDialog = document.getElementById("dialog");
const elButtonConfirm = document.getElementById("buttonConfirm");

elButtonConfirm.addEventListener("click", function (ev) {
    dialogApp.customerCount = customerPageMark.totalRecords;
    dialogApp.headline = smsApp.optionValue;
    dialogApp.templateCharLength = calculateCharLength(smsApp.templateText);
    dialogApp.calcIntegral = customerPageMark.totalRecords * 20;
    dialogApp.calcMoney = customerPageMark.totalRecords * 0.5 / 10;

    elDialog.showModal();
})
