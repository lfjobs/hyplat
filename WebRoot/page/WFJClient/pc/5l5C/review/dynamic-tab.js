////////////////////////////////////////动态TAB//////////////////////////////////////////

function DynamicTab(target, options) {
    this.target = target;
    this.options = options || {};
    this.init()
}

DynamicTab.prototype.init = function () {
    this.container = document.getElementById(this.target);

    this.render()
}

DynamicTab.prototype.selectTab = function () {

}

DynamicTab.prototype.render = function () {
    let activeStyle = ""
    if (this.options.indicator) {
        activeStyle = "underline underline-offset-8 decoration-2 decoration-red-500"
    }

    const htmls = []

    let items = this.options.items

    this.container.innerHTML = "";

    for (let i = 0; i < items.length; i++) {
        const item = items[i]
        const tabItem = document.createElement("span");
        tabItem.innerText = item.label
        tabItem.className = `cursor-default text-nowrap ${item.selected ? activeStyle : ''} `;
        tabItem.setAttribute("id", item.id);
        tabItem.onclick = () => {
            this.options.items.map((tab, i) => {
                tab.selected = item.id == tab.id ? true : false;
            })
            this.onSelectChange(item, i);
            this.render()
        }
        this.container.appendChild(tabItem)
    }
}

DynamicTab.prototype.setSelectChangeListener = function (listener) {
    this.onSelectChange = listener;
}

///////////////////////////////////////////////生成动态TAB//////////////////////////////////////////////

function generateDateRangeTabs(defaultSelected = 0) {
    var configMap = [
        {id: 1, label: "今日", options: {duration: 0, unit: "day"}},
        {id: 2, label: "昨日", options: {duration: -1, unit: "day"}},
        {id: 3, label: "本周", options: {duration: 0, unit: "week"}},
        {id: 4, label: "上周", options: {duration: -1, unit: "week"}},
        {id: 5, label: "本月", options: {duration: 0, unit: "month"}},
        {id: 6, label: "上月", options: {duration: -1, unit: "month"}},
        {id: 7, label: "本季", options: {duration: 0, unit: "quarter"}},
        {id: 8, label: "上季", options: {duration: -1, unit: "quarter"}},
        {id: 9, label: "今年", options: {duration: 0, unit: "year"}},
        {id: 10, label: "去年", options: {duration: -1, unit: "year"}}
    ]

    function getItemData(duration = 0, unit = "day" | "month" | "quarter" | "year") {
        const current = dayjs().add(duration, unit)
        const start = current.startOf(unit).format("YYYY-MM-DD HH:mm:ss")
        const end = current.endOf(unit).format("YYYY-MM-DD HH:mm:ss")
        return `${start},${end}`;
    }

    const newTabs = []
    for (let i = 0; i < configMap.length; i++) {
        const item = configMap[i]
        const tab = {
            id: item.id,
            label: item.label,
            data: getItemData(item.options.duration, item.options.unit),
            selected: defaultSelected === i
        }
        newTabs.push(tab)
    }
    return newTabs
}


////////////////////////////////////////////////动态TOOLBAR/////////////////////////////////////////////
function Toolbar(target, options) {
    this.target = target
    this.options = options || {}
    this.init()
}

Toolbar.prototype.BACK = function () {
    return {id: "back", label: "返回"}
}

Toolbar.prototype.init = function () {
    // 初始化布局容器
    this.initContainer()
}

Toolbar.prototype.initContainer = function () {
    this.containerEl = document.getElementById(this.target)
    this.containerEl.className = "bg-red-600 text-white min-h-14"

    // 标题
    const titleEl = document.createElement("div");
    titleEl.className = "absolute h-14 flex w-full items-center justify-center text-xl"
    titleEl.append(this.createTitle())
    this.containerEl.appendChild(titleEl)

    // 返回
    const backEl = this.createBack(this.BACK())
    backEl.className = "absolute h-14 ml-2"
    backEl.innerHTML = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"32\" height=\"32\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\" class=\"feather feather-chevron-left\"><polyline points=\"15 18 9 12 15 6\"></polyline></svg>"
    backEl.style.zIndex = titleEl.style.zIndex + 1 ;
    this.containerEl.appendChild(backEl)

    // 更多操作
    const actionsContainerEl = document.createElement("div");
    actionsContainerEl.className = "absolute h-14 right-2 flex flex-row items-center justify-center"
    actionsContainerEl.style.zIndex = titleEl.style.zIndex + 1 ;

    // 创建操作活动按钮
    const actions = this.createActions();

    actionsContainerEl.append(...actions)

    this.containerEl.appendChild(actionsContainerEl)
}

Toolbar.prototype.createActions = function () {
    const actions = this.options.actions
    if (!actions) {
        return [];
    }

    const actionEls = []
    for (let i = 0; i < this.options.actions.length; i++) {
        actionEls.push(this.createAction(this.options.actions[i]))
    }
    return actionEls
}

Toolbar.prototype.createAction = function (action) {
    const actionEl = document.createElement("button");
    actionEl.innerText = action.label
    actionEl.setAttribute("id", action.id)
    actionEl.className = "w-8 h-8"
    actionEl.onclick = () => {
        this.options.onClick(action.id)
    }
    return actionEl
}

Toolbar.prototype.createBack = function (action) {
    const actionEl = document.createElement("button");
    actionEl.innerText = action.label
    actionEl.setAttribute("id", action.id)
    actionEl.onclick = () => {
        this.options.onClick(action.id)
    }
    return actionEl
}

Toolbar.prototype.createTitle = function () {
    const titleEl = document.createElement("div");
    titleEl.innerText = this.options.title
    return titleEl
}

Toolbar.prototype.setOptions = function (options) {
    this.options = options
}
