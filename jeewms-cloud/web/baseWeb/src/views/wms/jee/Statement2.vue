<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24" v-for="(item, index) in searchList" :key="index">
            <a-form-item :label="$t(item.label)">
              <a-input :placeholder="$t('请输入')" v-model="queryParam[item.field]" v-if="item.mode == 'single'" />
              <div v-else>
                <a-input style="width:40%" v-model="queryParam[item.field]" />
                ——
                <a-input style="width:40%" v-model="queryParam[item.field]" />
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
    <!-- <a-button type="primary" style="margin: 10px;"  @click="ExportXls(title)" >{{$t('导出')}}</a-button> -->
    <a-table ref="table" size="middle" bordered rowKey="id" class="j-table-force-nowrap" :loading="loading"
      :columns="columns" :dataSource="dataSource" :pagination="ipagination" @change="TableChange"></a-table>
  </a-card>
</template>

<script>
import { getAction, downFile } from '@/api/manage'
import { filterDictText } from '@/components/dict/JDictSelectUtil'
import moment from 'moment'
export default {
  name: 'cgreport',
  components: {},
  data() {
    return {
      title: '',
      id: '',
      searchList: [
        {
          label: '供应商',
          mode: 'single',
          field: 'query01',
        },
        {
          label: '币种',
          mode: 'single',
          field: 'query02',
        },
        {
          label: '订单属性',
          mode: 'single',
          field: 'query03',
        },
      ],
      url: {
        search: '/online/cgreport/api/getQueryInfo/',
        // list:'/sys/reportApi/getlist', 
        list: '/online/cgreport/api/getColumnsAndData/',
        exportXlsUrl: "/online/cgreport/api/exportXls/",
      },
      columns: [
        {
          title: '供应商',
          align: 'left',
          dataIndex: 'query01',
        },
        {
          title: '币种',
          align: 'left',
          dataIndex: 'query02',
        },
        {
          title: '订单属性',
          align: 'left',
          dataIndex: 'query03',
          customRender: (text) => {
            if (text == "YP") {
              return "样品"
            } else {
              return "大货"
            }
          }
        },
      ],
      dataSource: [],
      dictOptions: {},
      order: '',
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['5', '10', '20', '50', '100'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + '共' + ' ' + total + ' ' + '项目'
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0
      },
      queryParam: {
        pageNo: 1,
        pageSize: 10
      },
      loading: false,
      type: 'GYS'
    }
  },
  created() {
    this.id = this.getRequest()
    // this.getSearch()

    this.getList()
  },
  watch: {
    $route: function (newRoute) {
      this.id = this.getRequest()
      this.getSearch()
      this.getList()
    }
  },
  methods: {
    ExportXls(title) {
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      for (let key in obj) {
        if (!obj[key]) {
          Reflect.deleteProperty(obj, key)
        } else {
          obj[key] = '*' + obj[key] + '*'
        }
      }
      let queryParam = {
        ...obj,
        ...this.order
      }
      downFile(this.url.exportXlsUrl + this.id, queryParam).then((data) => {
        if (!data) {
          this.$message.warning("文件下载失败")
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
          document.body.removeChild(link); //下载完成移除元素
          window.URL.revokeObjectURL(url); //释放掉blob对象obj
        }
      })
    },
    searchQuery() {
      this.getList(1)
    },
    searchReset() {
      this.queryParam = {}
      this.getList(1)
    },
    getSearch() {
      // getAction(this.url.search+this.id).then(res=>{
      //   this.searchList = res.result
      // })
    },
    getList(e) {
      if (this.$route.fullPath != '/wms/jee/Statement/getTotalMoney') {
        this.type = 'KH'
        this.columns = [
          {
            title: '客户',
            align: 'left',
            dataIndex: 'query01',
          },
          {
            title: '币种',
            align: 'left',
            dataIndex: 'query02',
          },
          {
            title: '订单属性',
            align: 'left',
            dataIndex: 'query03',
            customRender: (text) => {
              if (text == "YP") {
                return "样品"
              } else {
                return "大货"
              }
            }
          },
        ]
      } else {
        this.type = 'GYS'
        this.columns = [
          {
            title: '供应商',
            align: 'left',
            dataIndex: 'query01',
          },
          {
            title: '币种',
            align: 'left',
            dataIndex: 'query02',
          },
          {
            title: '订单属性',
            align: 'left',
            dataIndex: 'query03',
            customRender: (text) => {
              if (text == "YP") {
                return "样品"
              } else {
                return "大货"
              }
            }
          },
        ]
      }
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      let queryParam = {
        id: this.id,
        ...obj,
        type: this.type,
        pageNo: this.ipagination.current,
        pageSize: this.ipagination.pageSize,
      }
      getAction('/jeeerp/busiPo/getTotalMoney', queryParam).then(res => {
        if (res.result.records[0]) {
          let i = res.result.records[0].attr1 // 当前页最早的年份 21
          let i1 = moment().format('YY') // 当前年份 23
          let i2 = i1 - i + 1 // 当前年份 - 当前页最早的年份 = 需要添加多少个表头列 
          for (let index = 0; index < i2; index++) {
            let j = 4 + index
            if (j < 10) {
              j = "0" + j
            }
            this.columns.push({
              title: '20' + (Number(i) + Number(index)),
              align: 'left',
              dataIndex: 'query' + j,
            })
          }
          res.result.records.map((item, index) => {
            item.id = index
          })
        }
        this.dataSource = res.result.records
      })
    },
    TableChange(e, l, v) {
      this.ipagination = e
      if (v.order) {
        let str = v.order
        str = str.substring(0, str.lastIndexOf("end"))
        this.order = { order: str, column: v.columnKey }
      }
      this.getList()
    },
    getRequest() {
      let url = this.$route.path //获取url中"?"符后的字串
      let arr = url.split('/')
      let id = arr[arr.length - 1]
      return id
    },
  }
}
</script>

<style scoped></style>