"use strict";

/**
 * 复制对象的属性，包含不可遍历的属性
 * @param from      数据源
 * @param to        目标
 * @param cover     目标键已存在时，是否覆盖数据，默认为 true
 */
export function copyOwnProperty(from, to, cover = true) {
    let toKeys = Object.getOwnPropertyNames(to);
    let fromKeys = Object.getOwnPropertyNames(from);
    one:for (let key of fromKeys) {

        let value = from[key];

        // 如果值为 null 或 undefined，将其设置为空字符串
        if (value === null || value === undefined) {
            value = '';
        }

        if (!cover) {
            for (let tpmKye of toKeys) {
                if (key === tpmKye) {
                    continue one;
                }
            }
        }

        to[key] = value;
    }
    return to;
}

/**
 * 获取变量的数据类型
 */
export function getDataType(value) {
    return Object.prototype.toString.call(value).slice(8, -1);
}

/**
 * 移除数组中的指定元素
 * @param items     任意数组对象
 * @param item      数组对象内的成员
 */
export function removeArrayItem(items, item) {
    const i = items.indexOf(item);
    if (i > -1) {
        items.splice(i, 1)
    }
}

/**
 * 计算字符串长度，中文字符算作2个单位
 * @param str       字符串
 */
export function calculateCharLength(str) {
    if (!str) return 0;
    return Array.from(str).reduce((length, char) => {
        return length + (char.match(/[^\x00-\xff]/) ? 2 : 1);
    }, 0);
}

/**
 * 按照字符的实际长度截取字符串。（其中中文字符算作 2 个单位，英文字符算作 1 个单位）
 * @param str           字符串
 * @param maxLength     最大长度
 */
export function truncateText(str, maxLength) {
    // 当前累计长度
    let length = 0;

    return Array.from(str).filter(char => {
        // 判断当前字符长度
        const charLength = char.match(/[^\x00-\xff]/) ? 2 : 1;

        // 如果超出最大长度，则停止添加
        if (length + charLength > maxLength) return false;

        // 累计当前字符长度
        length += charLength;

        return true;
    }).join('');
}

/**
 * 数组元素追加
 * @param {[]} target        希望追加的数组
 * @param {[]} source        提供数据的数组
 */
export function arrayAppend(target, source) {
    for (let i = 0; i < source.length; i++) {
        target.push(source[i]);
    }
}

/**
 * 创建 HTML 元素对象
 * @param {string} name          元素名称
 * @param {string} textContent   文本内容
 */
export function createElement(name, textContent = '') {
    const el = document.createElement(name);
    el.textContent = textContent;
    return el;
}


/**
 * 读取文件内容
 * @param file
 * @returns {Promise<[number]>}
 */
export function readerFile(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = () => {
            if (reader.result === null) {
                resolve('');
            } else {
                resolve(reader.result);
            }
        }
        reader.onerror = () => {
            reject(file);
        }
        reader.readAsArrayBuffer(file);
    });
}

/**
 * 获取浏览器中的 Cookie 数据
 * @param key
 * @returns {null|string}
 */
export function getCookie(key){
    let cookies = document.cookie.split(';');
    for (let i = 0; i < cookies.length; i++) {
        let row = cookies[i];
        let index = row.indexOf("=");
        let k = row.slice(0,index);
        let v = row.slice(index+1);
        if(k === key){
            return v;
        }
    }
    return null;
}

/**
 * 保存 sessionStorage 数据,用于浏览器的一次会话（session）,存储空间的共享范围为一级域名
 * @param {string} key
 * @param {string} value
 */
export function setSessionStorage(key,value){
    window.sessionStorage.setItem(key,value)
}

/**
 * 读取 sessionStorage 数据，用于浏览器的一次会话（session）,存储空间的共享范围为一级域名
 * @param {string} key
 * @returns {null|string}
 */
export function getSessionStorage(key){
    return window.sessionStorage.getItem(key)
}

/**
 * 读取 sessionStorage 所有键名
 * @returns {string[]}
 */
export function getSessionStorageKeys(){
    return Object.keys(window.sessionStorage)
}

/**
 * getUrlParam 方法中需要保留的缓存
 * @type {null}
 */
let urlParams = null;

/**
 * 获取 URL 链接中的 GET 参数
 * @param {string} key
 * @returns {string}
 */
export function getUrlParam(key){
    if(urlParams === null){
        // 获取当前 URL 中的查询字符串部分
        const queryString = window.location.search;

        // 将查询字符串解析成 URLSearchParams 对象
        urlParams = new URLSearchParams(queryString);
    }
    return urlParams.get(key);
}

export function encodeURIComponentSVG(svg){
    let compressedSVG = '';

    // 替换掉 SVG 图片中的无用信息
    compressedSVG = svg.replace(/([\w-]+="[\w\\.]+")/g,'');

    // 去除首尾空白
    compressedSVG = compressedSVG.trim();

    // 移除多余的空白
    compressedSVG = compressedSVG.replace(/\s+>/g,'>');
    compressedSVG = compressedSVG.replace(/\s+</g,'<');
    compressedSVG = compressedSVG.replace(/\s+/g,' ');

    // 将 SVG 转换为 Data URI
    return  'data:image/svg+xml;charset=utf-8,' + encodeURIComponent(compressedSVG);

}