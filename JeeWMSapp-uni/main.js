import Vue from 'vue'
import App from './App'
import store from './store'
import MinCache from'./common/util/MinCache.js'
import tip from'./common/util/tip.js'
import configService from'./common/service/config.service.js'
import modal from './common/js_sdk/plus-modal/plus-modal.js';
import router from './common/router'
import {RouterMount} from './plugin/uni-simple-router/index.js'
import {dateFormat,debounce,throttle} from './common/util/tools.js'
import Directives from './common/util/directives'
import VueI18n from "vue-i18n";
Vue.use(VueI18n);





let lang = uni.getStorageSync('lang')
if(!lang){
	uni.setStorageSync('lang', 'zh');
	lang = 'zh'
}


const i18n = new VueI18n({
    locale:lang,
    messages: {
        zh: require("@/static/lang/zh.json"),
        en: require("@/static/lang/en.json")
    }
});

Vue.prototype._i18n = i18n

Vue.use(Directives)
// 注册缓存器
Vue.use(MinCache,{timeout: 6})

// 公共方法工具
Vue.prototype.$dateFormat=dateFormat;
Vue.prototype.$debounce=debounce;
Vue.prototype.$throttle=throttle;
// store
Vue.prototype.$store=store;
// tip
Vue.prototype.$tip=tip;
// config
Vue.prototype.$config=configService;
// toast
Vue.prototype.modal = modal;

/* 
* 传给后端判断是什么项目
* SW: 广西双维，GS：国声声学，PLTN：普拉提诺
*/

Vue.prototype.PROJECT = 'PLTN'
Vue.prototype.$systemType = 2; // 1-旧版  2-增鑫








// request请求
import { http } from '@/common/service/service.js' 
Vue.prototype.$http = http

import home from './pages/home/home.vue'
Vue.component('home',home)

import people from './pages/user/people.vue'
Vue.component('people',people)

import message from './pages/msg/message.vue'
Vue.component('message',message)

// 自定义组件
import mySelect from './components/my-componets/my-select.vue'
Vue.component('mySelect',mySelect)

import myImageUpload from './components/my-componets/my-image-upload.vue'
Vue.component('myImageUpload',myImageUpload)


import myPage from './components/my-componets/my-page.vue'
Vue.component('myPage',myPage)

import cuCustom from './plugin/colorui/components/cu-custom.vue'
Vue.component('cu-custom',cuCustom)

// import VConsole from './js_sdk/vconsole.min'

//   var vConsole = new VConsole();

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
	i18n,
	store,
	MinCache,
    ...App
})
//v1.3.5起 H5端 你应该去除原有的app.$mount();使用路由自带的渲染方式
// #ifdef H5
	RouterMount(app,'#app');
// #endif

// #ifndef H5
	app.$mount(); //为了兼容小程序及app端必须这样写才有效果
// #endif



 



