// 从vue框架导入createApp函数功能
import { createApp } from 'vue'
// 导入css样式
// import './style.css'
// 从一个单文件中导入根组件
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import App from './App.vue'

import router from "./router/router.js";

import * as ElementPlusIconsVue from '@element-plus/icons-vue'

let app = createApp(App);
app.use(ElementPlus).use(router)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app');

const debounce = (fn, delay) => {
    let timer
    return (...args) => {
        if (timer) {
            clearTimeout(timer)
        }
        timer = setTimeout(() => {
            fn(...args)
        }, delay)
    }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver{
    constructor(callback) {
        callback = debounce(callback, 200);
        super(callback);
    }
}
