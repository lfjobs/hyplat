import {reactive} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {getCookie, getDataType} from "./stdlib.js";

/**
 * 全局状态管理
 */
const store = reactive({
    header: {
        show:true,
        title: '标准模板'
    },
    footer:{
        show:true
    },
    alert:{
        type:'error',
        text:'hello'
    },
    alertIndex:0,
    alertList:[]
});

/**
 * 设置头部标题
 * @param title
 */
export function setHeaderTitle(title) {
    if (title !== null && title !== undefined) {
        store.header.title = title
    }
}

/**
 * 设置警示信息
 * @param {(error | success | warning | info)} type      提示条类型
 * @param {string} text      文本内容
 * @param {Int8Array} time      显示时间,单位为毫秒。 如果需要永久显示请将值设为 -1
 */
export function addAlert(type,text,time = 2000) {
    if (text !== null && text !== undefined) {

        let kid = 'A'+(store.alertIndex++);
        store.alertList.push({
            'kid':kid,
            'type':type,
            'text':text
        });

        if(time >= 0){
            let len = store.alertList.length;
            if(len > 1){
                time += (len-1) * 500;
            }
            setTimeout(()=>{
                cancelAlert(kid)
            }, time);
        }

    }
}

/**
 * 清除警示信息
 * @param {sting} kid       信息 id
 */
export function cancelAlert(kid){
    if (kid !== null && kid !== undefined) {
        let list = store.alertList;
        let ofIndex = -1;
        for (let i = 0; i < list.length; i++) {
            if(list[i].kid === kid){
                ofIndex = i;
                break;
            }
        }
        if(ofIndex === -1){
            return;
        }
        list.splice(ofIndex, 1)
    }
}

// ajax 请求地址前缀
const serviceURL = window.location.protocol + "//" + window.location.hostname + ':8080/';

/**
 * 发送 Ajax 请求
 * @param pathname          请求路径:即 http://{hostname}:{port}/{pathname}
 * @param requestData       发送请求的数据,默认采用 json 字符串的形式发送
 * @param conf              自定义的请求配置
 * @returns {Promise<String|{config:{},data:{data:({}|[]),message:string,success:boolean}|string,headers:{},request:{}}>}
 */
export function sendAxiosPost(pathname, requestData = {},conf = {}) {
    if (typeof axios !== 'undefined') {

        let deftConfig = {
            baseURL: serviceURL,
            timeout: -1,
            headers: {
                'Content-Type': 'application/json',
            }
        };

        Object.keys(conf).forEach((key)=>{
            let itemValue = conf[key];
            if(deftConfig.hasOwnProperty(key) && getDataType(itemValue) === "Object"){
                deftConfig[key] = Object.assign(deftConfig[key],itemValue);
            }else{
                deftConfig[key] = itemValue;
            }
        })

        // 创建 Axios 实例
        const instanceAxios = axios.create(deftConfig);

        return instanceAxios.post(pathname, requestData);
    } else {
        return new Promise((response,error)=>{
            error("Axios 未成功加载！");
        });
    }
}

/**
 * 发送 Ajax 请求
 * @param pathname          请求路径:即 http://{hostname}:{port}/{pathname}
 * @param requestData       发送请求的数据,采用 json 字符串的形式发送
 * @returns {Promise<{}|[{}]>}
 */
export function sendAxiosPostFast(pathname, requestData){
    return sendAxiosPost(pathname, requestData).then(response=>{
        let resData = response.data;

        if(Object.hasOwn(resData,"success") && Object.hasOwn(resData,"message") && Object.hasOwn(resData,"data")){
            if(resData.success === true){
                return resData.data;
            }
        }

        return resData;
    })
}


/**
 * 获取 ajax 请求的基本参数
 * @returns {{customerKey: (string|null)}}
 */
export function getAxiosBaseData(){
    return  {
        customerKey: getCookie("key_customer")
    }
}

/**
 * 事件注册中心
 */
class EventPubSubCentre{
    constructor() {
        this.events = {};
    }

    /**
     * 发布事件
     */
    publish(name,...args){
        if (this.events[name]) {
            this.events[name].forEach(item => item.listener(...args));
        }
    }

    /**
     * 订阅事件
     */
    subscribe(name,listener){
        if (!this.events.hasOwnProperty(name)) {
            this.events[name] = [];
        }

        // 使用唯一标识符
        const token = Symbol(name);
        this.events[name].push({
            token,
            listener
        });
    }

    getEventNames(){
        return Object.keys(this.events);
    }

    /**
     * 取消订阅
     * @param token
     */
    unsubscribe(token) {
        for (const name in this.events) {
            this.events[name] = this.events[name].filter(subscriber => subscriber.token !== token);
        }
    }
}



export const eventPubSub  = new EventPubSubCentre();
export default store;