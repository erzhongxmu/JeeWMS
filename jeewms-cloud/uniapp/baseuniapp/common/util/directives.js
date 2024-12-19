import clickoutside from './clickoutside'
// 自定义指令
const directives = {
  clickoutside
}

export default {
  install(Vue) {
    Object.keys(directives).forEach((key) => {
      Vue.directive(key, directives[key])
    })
  },
}
