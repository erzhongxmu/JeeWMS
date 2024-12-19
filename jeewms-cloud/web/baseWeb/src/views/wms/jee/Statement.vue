<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24" v-for="(item, index) in searchList" :key="index">
            <a-form-item :label="$t(item.label)">
              <a-input :placeholder="$t('请输入')" v-model="queryParam[item.field]" v-if="item.mode == 'single'" />
              <div v-else>
                <a-range-picker
                  v-model="queryParam[item.field]"
                  format="YYYY-MM-DD"
                  @change="
                    (e, mode) => {
                      pickerChange(e, mode, item.field)
                    }
                  "
                />
              </div>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24" v-if="searchList.length != 0">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{ $t('查询') }}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <a-button type="primary" style="margin: 10px;" @click="ExportXls(title)">{{ $t('导出') }}</a-button>
    <a-table
      ref="table"
      size="middle"
      bordered
      rowKey="id"
      class="j-table-force-nowrap"
      :loading="loading"
      :columns="columns"
      :dataSource="dataSource"
      :pagination="ipagination"
      @change="TableChange"
    ></a-table>
  </a-card>
</template>

<script>
import { getAction, downFile } from '@/api/manage'
import { filterDictText } from '@/components/dict/JDictSelectUtil'
export default {
  name: 'cgreport',
  components: {},
  data() {
    return {
      title: '',
      id: '',
      searchList: [],
      url: {
        search: '/online/cgreport/api/getQueryInfo/',
        // list:'/sys/reportApi/getlist',
        list: '/online/cgreport/api/getColumnsAndData/',
        exportXlsUrl: '/online/cgreport/api/exportXls/'
      },
      columns: [],
      dataSource: [],
      dictOptions: {},
      order: '',
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['5', '10', '20', '50', '100'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      queryParam: {
        pageNo: 1,
        pageSize: 10
      },
      loading: false
    }
  },
  created() {
    this.id = this.getRequest()
    this.getSearch()
    this.getList()
  },
  watch: {
    $route: function(newRoute) {
      this.id = this.getRequest()
      this.getSearch()
      this.getList()
    }
  },
  methods: {
    pickerChange(e, mode, key) {
      this.queryParam[key+'_begin'] = mode[0]?mode[0]:null
      this.queryParam[key+'_end'] = mode[1]?mode[1]:null
    },
    ExportXls(title) {
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      for (let key in obj) {
        if(key.indexOf('_begin') != -1 || key.indexOf('_end')  != -1  ){
          key = key.replace('_begin','')
          key = key.replace('_end','')
          obj[key] = null
        }else{
          if (!obj[key]) {
            Reflect.deleteProperty(obj, key)
          } else {
            obj[key] = '*' + obj[key] + '*'
          }
        }
      }
      let queryParam = {
        ...obj,
        ...this.order
      }
      downFile(this.url.exportXlsUrl + this.id, queryParam).then(data => {
        if (!data) {
          this.$message.warning('文件下载失败')
          return
        }
        if (typeof window.navigator.msSaveBlob !== 'undefined') {
          window.navigator.msSaveBlob(new Blob([data], { type: 'application/vnd.ms-excel' }), title + '.xls')
        } else {
          let url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
          let link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', title + '.xls')
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link) //下载完成移除元素
          window.URL.revokeObjectURL(url) //释放掉blob对象
        }
      })
    },
    searchQuery() {
      this.getList()
    },
    searchReset() {
      this.queryParam = {}
      this.getList()
    },
    getSearch() {
      getAction(this.url.search + this.id).then(res => {
        this.searchList = res.result
      })
    },
    getList() {
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      for (let key in obj) {
        console.log(key.indexOf('_begin'));
        if(key.indexOf('_begin') != -1 || key.indexOf('_end')  != -1  ){
          key = key.replace('_begin','')
          key = key.replace('_end','')
          obj[key] = null
        }else{
          if (!obj[key]) {
            Reflect.deleteProperty(obj, key)
          } else {
            obj[key] = '*' + obj[key] + '*'
          }
        }
      }
      let queryParam = {
        // self_tenant_id:1,
        id: this.id,
        ...obj,
        pageNo: this.ipagination.current,
        pageSize: this.ipagination.pageSize,
        ...this.order
      }
      this.loading = true
      getAction(this.url.list + this.id, queryParam).then(res => {
        this.loading = false
        this.title = res.result.cgreportHeadName
        res.result.columns.map((item, index) => {
          item.title = this.$t(item.title)
          item.align = 'left'
          if (item.customRender) {
            let str = item.customRender
            item.customRender = (text, record, index) => {
              return filterDictText(res.result.dictOptions[str], text)
            }
          }
          if (item.dataIndex == 'odertype') {
            item.customRender = (text, record, index) => {
              return text == '入库' ? this.$t('入库') : text == '出库' ? this.$t('入库') : text
            }
          }
        })
        this.columns = res.result.columns
        res.result.data.records.map((item, index) => {
          item.id = index
        })
        this.dataSource = res.result.data.records
        this.dictOptions = res.result.dictOptions
        this.ipagination.total = res.result.data.total
      })
    },
    TableChange(e, l, v) {
      this.ipagination = e
      if (v.order) {
        let str = v.order
        str = str.substring(0, str.lastIndexOf('end'))
        this.order = { order: str, column: v.columnKey }
      }
      this.getList()
    },
    getRequest() {
      let url = this.$route.path //获取url中"?"符后的字串
      let arr = url.split('/')
      let id = arr[arr.length - 1]
      return id
    }
  }
}
</script>

<style scoped></style>
