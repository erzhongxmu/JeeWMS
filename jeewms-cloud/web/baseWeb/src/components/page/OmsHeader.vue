<template>
  <!-- , width: fixedHeader ? `calc(100% - ${sidebarOpened ? 256 : 80}px)` : '100%'  -->
  <div class="header">
    <div class="header_left">
      <a-select
        class="search-input"
        showSearch
        :showArrow="false"
        :placeholder="$t('搜索菜单')"
        optionFilterProp="children"
        :filterOption="filterOption"
        :getPopupContainer="(node) => node.parentNode"
        @change="searchMethods"
      >
        <a-select-option
          v-for="(site,index) in searchMenuOptions"
          :key="index"
          :value="site.id"
        >{{site.meta.title}}</a-select-option>
      </a-select>
    </div>
    <div class="header_right">
      <header-notice class="action" />
      <a-dropdown>
        <span class="action action-full ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" size="small" :src="getAvatar()" />
          <span >{{$t('welcome')}}，{{ nickname() }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <!-- <a-menu-item key="0">
            <router-link :to="{ name: 'account-center' }">
              <a-icon type="user" />
              <span>个人中心</span>
            </router-link>
          </a-menu-item>
          <a-menu-item key="1">
            <router-link :to="{ name: 'account-settings-base' }">
              <a-icon type="setting" />
              <span>账户设置</span>
            </router-link>
          </a-menu-item> -->
          <a-menu-item key="3" @click="systemSetting">
            <a-icon type="tool" />
            <!-- <span>系统设置</span> -->
            <span>{{$t('setting')}}</span>
          </a-menu-item>
          <a-menu-item key="4" @click="updatePassword">
            <a-icon type="setting" />
            <!-- <span>密码修改</span> -->
            <span>{{$t('changePassword')}}</span>
          </a-menu-item>
          <a-menu-item key="5" @click="updateCurrentDepart">
            <a-icon type="cluster" />
            <!-- <span>切换部门</span> -->
            <span>{{$t('switchDepart')}}</span>
          </a-menu-item>
          <a-menu-item key="6" @click="clearCache">
            <a-icon type="sync" />
            <!-- <span>清理缓存</span> -->
            <span>{{$t('clean')}}</span>
          </a-menu-item>
          <a-menu-item key="7">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout" />
              <!-- <span>退出登录</span> -->
              <span>{{$t('login-out')}}</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>
    <user-password ref="userPassword"></user-password>
    <depart-select ref="departSelect" :closable="true" :title="$t('switchDepart')"></depart-select> 
    <setting-drawer ref="settingDrawer" :closable="true" :title="$t('setting')"></setting-drawer>
  </div>
</template>

<script>
import UserMenu from '../tools/UserMenu'
import HeaderNotice from '../tools/HeaderNotice'
import UserPassword from '../tools/UserPassword'
  import SettingDrawer from "@/components/setting/SettingDrawer";
import SMenu from '../menu/'
import Logo from '../tools/Logo'
import { mapActions, mapGetters, mapState } from 'vuex'
import DepartSelect from '../tools/DepartSelect'
import { getFileAccessHttpUrl, getAction } from '@/api/manage'
import { mixin } from '@/utils/mixin.js'

export default {
  name: 'GlobalHeader',
  components: {
    DepartSelect,
    HeaderNotice,
    UserPassword,
    SettingDrawer,
    UserMenu,
    SMenu,
    Logo
  },
  mixins: [mixin],
  props: {
    menus: {
      type: Array,
      required: true
    },
    theme: {
      type: String,
      required: false,
      default: 'dark'
    },
    collapsed: {
      type: Boolean,
      required: false,
      default: false
    },
    device: {
      type: String,
      required: false,
      default: 'desktop'
    }
  },
  data() {
    return {
      searchMenuOptions: [],
      searchMenuComp: 'span',
      searchMenuVisible: false
    }
  },
  watch: {},
  computed: {
    ...mapState({
      // 后台菜单
      permissionMenuList: state => state.user.permissionList
    })
  },
  created() {
    let lists = []
    this.searchMenus(lists, this.permissionMenuList)
    this.searchMenuOptions = [...lists]
  },
  mounted() {
    console.log(1232222222222222222222222222222)
    if (process.env.VUE_APP_SSO == 'true') {
      let depart = this.userInfo().orgCode
      if (!depart) {
        this.updateCurrentDepart()
      }
    }
  },
  methods: {
    ...mapActions(['Logout']),
    ...mapGetters(['nickname', 'avatar', 'userInfo']),
    getAvatar() {
      return getFileAccessHttpUrl(this.avatar())
    },
    filterOption(input, option) {
      return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
    },
    searchMethods(value) {
      let route = this.searchMenuOptions.filter(item => item.id === value)[0]
      if (route.meta.internalOrExternal === true || route.component.includes('layouts/IframePageView')) {
        window.open(route.meta.url, '_blank')
      } else {
        this.$router.push({ path: route.path })
      }
    },
    searchMenus(arr, menus) {
      for (let i of menus) {
        if (!i.hidden && 'layouts/RouteView' !== i.component) {
          arr.push(i)
        }
        if (i.children && i.children.length > 0) {
          this.searchMenus(arr, i.children)
        }
      }
    },
    handleLogout() {
      const that = this
      this.$confirm({
        // title: '提示',
        title: this.$t('login-out-title'),
        // content: '真的要注销登录吗 ?',
        content: this.$t('login-out-message') + '?',
        onOk() {
          return that
            .Logout({})
            .then(() => {
              // update-begin author:wangshuai date:20200601 for: 退出登录跳转登录页面
              that.$router.push({ path: '/user/login' })
              // update-end author:wangshuai date:20200601 for: 退出登录跳转登录页面
              //window.location.reload()
            })
            .catch(err => {
              that.$message.error({
                title: '错误',
                description: err.message
              })
            })
        },
        onCancel() {}
      })
    },
    clearCache() {
      getAction('sys/dict/refleshCache')
        .then(res => {
          if (res.success) {
            //重新加载缓存
            getAction('sys/dict/queryAllDictItems').then(res => {
              if (res.success) {
                Vue.ls.remove(UI_CACHE_DB_DICT_DATA)
                Vue.ls.set(UI_CACHE_DB_DICT_DATA, res.result, 7 * 24 * 60 * 60 * 1000)
              }
            })
            // this.$message.success("刷新缓存完成！");
            this.$message.success(this.$t('cache-success') + '!')
          }
        })
        .catch(e => {
          // this.$message.warn("刷新缓存失败！");
          this.$message.warn(this.$t('cache-fail') + '!')
          console.log('刷新失败', e)
        })
    },
    updatePassword(){
        let username = this.userInfo().username
        this.$refs.userPassword.show(username)
    },
    updateCurrentDepart() {
      this.$refs.departSelect.show()
    },
    systemSetting(){
      this.$refs.settingDrawer.showDrawer()
    },
  }
}
</script>

<style lang="less" scoped>
.header {
  width: 100%;
  height: 100%;
  padding-left: 10px;
  .search-input {
    width: 200px;
    /deep/ .ant-select-selection--single{
      border-radius: 20px;
    }
  }
  .header_left {
    display: inline;
  }
  .header_right {
    float: right;
    margin-right: 20px;
    .action{
      width: 35px;
      height: 35px;
      cursor: pointer;
    }
    .avatar{
      margin: 0px 10px;
    }
  }
}
</style>