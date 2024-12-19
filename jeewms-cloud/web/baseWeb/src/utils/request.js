import Vue from 'vue'
import axios from 'axios'
import store from '@/store'
import { VueAxios } from './axios'
import {Modal, notification} from 'ant-design-vue'
import { ACCESS_TOKEN, TENANT_ID } from "@/store/mutation-types"
import VueI18n from 'vue-i18n'
const locales = { // 引入zh.json以及en.json
  zh: require('./../assets/languages/zh.json'),
  en: require('./../assets/languages/en.json')
}
// 针对js文件访问不到VueI18n做出修改
// 在该js文件中，单独注册一个i18n实例并引入语言文件

Vue.use(VueI18n) // 这里一行是必须加的。
const i18n = new VueI18n({
  locale: localStorage.Language || 'zh',
  messages: locales
})


/**
 * 【指定 axios的 baseURL】
 * 如果手工指定 baseURL: '/base-boot'
 * 则映射后端域名，通过 vue.config.js
 * @type {*|string}
 */
let apiBaseUrl = window._CONFIG['domianURL'] || "/base-boot";
// 创建 axios 实例
const service = axios.create({
  baseURL: apiBaseUrl, // api base_url
  timeout: 1200000 // 请求超时时间
})

const err = (error) => {
  if (error.response) {
    let that=this;
    let data = error.response.data
    const token = Vue.ls.get(ACCESS_TOKEN)
    switch (error.response.status) {
      case 403:
        notification.error({ message: i18n.t('系统提示'), description: i18n.t('拒绝访问'),duration: 4})
        break
      case 500:
        // update-begin- --- author:liusq ------ date:20200910 ---- for:处理Blob情况----
        let type=error.response.request.responseType;
        if(type === 'blob'){
          blobToJson(data);
          break;
        }
        // update-end- --- author:liusq ------ date:20200910 ---- for:处理Blob情况----
        //notification.error({ message: '系统提示', description:'Token失效，请重新登录!',duration: 4})
        if(token && data.message.includes("Token失效")){
          // update-begin- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
          Modal.error({
            title: i18n.t('登录已过期'),
            content: i18n.t('很抱歉，登录已过期，请重新登录'),
            okText: i18n.t('重新登录'),
            mask: false,
            onOk: () => {
              store.dispatch('Logout').then(() => {
                Vue.ls.remove(ACCESS_TOKEN)
                try {
                  let path = window.document.location.pathname
                  if(path!="/" && path.indexOf('/user/login')==-1){
                    window.location.reload()
                  }
                }catch (e) {
                  window.location.reload()
                }
              })
            }
          })
          // update-end- --- author:scott ------ date:20190225 ---- for:Token失效采用弹框模式，不直接跳转----
        }
        break
      case 404:
          notification.error({ message: i18n.t('系统提示'), description:i18n.t('很抱歉，资源未找到!'),duration: 4})
        break
      case 504:
        notification.error({ message: i18n.t('系统提示'), description: i18n.t('网络超时')})
        break
      case 401:
        notification.error({ message: i18n.t('系统提示'), description:i18n.t('未授权，请重新登录'),duration: 4})
        if (token) {
          store.dispatch('Logout').then(() => {
            setTimeout(() => {
              window.location.reload()
            }, 1500)
          })
        }
        break
      default:
        notification.error({
          message: i18n.t('系统提示'),
          description: data.message,
          duration: 4
        })
        break
    }
  }
  return Promise.reject(error)
};

// request interceptor
service.interceptors.request.use(config => {
  const token = Vue.ls.get(ACCESS_TOKEN)
  if (token) {
    config.headers[ 'X-Access-Token' ] = token // 让每个请求携带自定义 token 请根据实际情况自行修改
  }
  //update-begin-author:taoyan date:2020707 for:多租户
  let tenantid = Vue.ls.get(TENANT_ID)
  if (!tenantid) {
    tenantid = 0;
  }
  //  switch (config.method) {
  //   case 'get':
  //     config.params = { ...config.params, tenantId : tenantid }
  //     break;
  //   case 'post':
  //     if(!config.data[0]){
  //       config.data = { ...config.data, tenantId : tenantid }
  //     }
  //     break;
  //   default:
  //     config.headers[ 'tenant-id' ] = tenantid
  //     break;
  // }
  // config.headers[ 'tenant-id' ] = tenantid
  //update-end-author:taoyan date:2020707 for:多租户
  if(config.method=='get'){
    if(config.url.indexOf("sys/dict/getDictItems")<0){
      config.params = {
        _t: Date.parse(new Date())/1000,
        ...config.params
      }
    }
  }
  return config
},(error) => {
  return Promise.reject(error)
})

// response interceptor
service.interceptors.response.use((response) => {
    return response.data
  }, err)

const installer = {
  vm: {},
  install (Vue, router = {}) {
    Vue.use(VueAxios, router, service)
  }
}
/**
 * Blob解析
 * @param data
 */
function blobToJson(data) {
  let fileReader = new FileReader();
  let token = Vue.ls.get(ACCESS_TOKEN);
  fileReader.onload = function() {
    try {
      let jsonData = JSON.parse(this.result);  // 说明是普通对象数据，后台转换失败
      if (jsonData.status === 500) {
        if(token && jsonData.message.includes("Token失效")){
          Modal.error({
            title: '登录已过期',
            content: '很抱歉，登录已过期，请重新登录',
            okText: '重新登录',
            mask: false,
            onOk: () => {
              store.dispatch('Logout').then(() => {
                Vue.ls.remove(ACCESS_TOKEN)
                window.location.reload()
              })
            }
          })
        }
      }
    } catch (err) {
      // 解析成对象失败，说明是正常的文件流
    }
  };
  fileReader.readAsText(data)
}

export {
  installer as VueAxios,
  service as axios
}