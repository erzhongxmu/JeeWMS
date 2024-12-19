<template>
  <a-card class="j-inner-table-wrapper" :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="24" v-if="id == 'canPinGenJinTongJi'">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购名')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('业务类型')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02" />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购名')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('日期')">
              <a-range-picker format="YYYY-MM-DD" @change="pickerChange" v-model="vau" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{ $t('查询') }}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>

        <a-row :gutter="24" v-if="id == 'chaXunMeiTianDingDan'">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购名')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('业务类型')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02" />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购名')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('日期')">
              <a-range-picker format="YYYY-MM-DD" @change="pickerChange" v-model="vau" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{ $t('查询') }}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>

        <a-row :gutter="24" v-if="id == 'XiangChuKuXiangQing'">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('装箱单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query03" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query04" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库通知单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query05" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query06" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('日期')">
              <a-range-picker format="YYYY-MM-DD" @change="pickerChange" v-model="vau" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{ $t('查询') }}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="id == 'huokuanzhichu'">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('主PO')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('公司属性')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query03" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query04" />
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('日期')">
              <a-range-picker format="YYYY-MM-DD" @change="pickerChange" v-model="vau" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span class="table-page-search-submitButtons table-operator">
              <a-button type="primary" icon="search" @click="searchQuery">{{ $t('查询') }}</a-button>
              <a-button type="primary" icon="reload" @click="searchReset">{{ $t('重置') }}</a-button>
            </span>
          </a-col>
        </a-row>
        <a-row :gutter="24" v-if="id == 'rukumingxi'">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('子PO号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query01" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query02" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('通知单状态')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query03" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query04" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query05" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('通知单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.query06" />
            </a-form-item>
          </a-col>
          
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('日期')">
              <a-range-picker format="YYYY-MM-DD" @change="pickerChange" v-model="vau" />
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
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
      vau: '',
      title: '',
      id: '',
      searchList: [],
      url: {
        list: '/jeeerp/busiPo/getQuery',
        exportXlsUrl: '/jeeerp/busiPo/getQueryExportXls'
      },
      columns: [],
      dataSource: [],
      dictOptions: {},
      order: "",
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
    if (this.id == 'canPinGenJinTongJi') {
      this.columns = [
        {
          title: this.$t('采购名'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01'
        },
        {
          title: this.$t('业务类型'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02'
        },
        {
          title: this.$t('样品商品'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03'
        },
        {
          title: this.$t('大货商品'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04'
        },
        {
          title: this.$t('统计日期'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05'
        }
      ]
    } else if (this.id == 'chaXunMeiTianDingDan') {
      this.columns = [
        {
          title: this.$t('采购名'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01'
        },
        {
          title: this.$t('业务类型'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02'
        },
        {
          title: this.$t('样品订单'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03'
        },
        {
          title: this.$t('大货订单'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04'
        },
        {
          title: this.$t('统计日期'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05'
        }
      ]
    } else if (this.id == 'XiangChuKuXiangQing') {
      this.columns = [
        {
          title: this.$t('装箱单号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01'
        },
        {
          title: this.$t('子PO号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02'
        },
        {
          title: this.$t('客户名'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03'
        },
        {
          title: this.$t('商品编码'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04'
        },
        {
          title: this.$t('商品名称'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05'
        },
        {
          title: this.$t('托盘'),
          sorter: true,
          align: 'center',
          dataIndex: 'query06'
        },
        {
          title: this.$t('数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query07'
        },
        {
          title: this.$t('单位'),
          sorter: true,
          align: 'center',
          dataIndex: 'query08'
        },
        {
          title: this.$t('箱子规格'),
          sorter: true,
          align: 'center',
          dataIndex: 'query09'
        },
        {
          title: this.$t('重量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query10'
        },
        {
          title: this.$t('出库时间'),
          sorter: true,
          align: 'center',
          dataIndex: 'query11'
        },
        {
          title: this.$t('出库通知单号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query12'
        }
      ]
    } else if (this.id == 'huokuanzhichu') {
      this.columns = [
        {
          title: this.$t('主PO'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01'
        },
        {
          title: this.$t('客户'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02'
        },
        {
          title: this.$t('公司属性'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03'
        },
        {
          title: this.$t('供应商'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04'
        },
        {
          title: this.$t('税点'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05'
        },
        {
          title: this.$t('含税总金额'),
          sorter: true,
          align: 'center',
          dataIndex: 'query06'
        },
        {
          title: this.$t('已付金额'),
          sorter: true,
          align: 'center',
          dataIndex: 'query07'
        },
        {
          title: this.$t('余额'),
          sorter: true,
          align: 'center',
          dataIndex: 'query08'
        },
        {
          title: this.$t('未完成数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query10'
        },
        {
          title: this.$t('合同建单日期'),
          sorter: true,
          align: 'center',
          dataIndex: 'query09'
        },
      ]
      
    } else if (this.id == 'rukumingxi') {
      this.columns = [
        {
          title: this.$t('子PO号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query01'
        },
        {
          title: this.$t('客户名称'),
          sorter: true,
          align: 'center',
          dataIndex: 'query02'
        },
        {
          title: this.$t('通知单状态'),
          sorter: true,
          align: 'center',
          dataIndex: 'query03'
        },
        {
          title: this.$t('商品编码'),
          sorter: true,
          align: 'center',
          dataIndex: 'query04'
        },
        {
          title: this.$t('商品名称'),
          sorter: true,
          align: 'center',
          dataIndex: 'query05'
        },
        {
          title: this.$t('通知单数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query06'
        },
        {
          title: this.$t('验收数量'),
          sorter: true,
          align: 'center',
          dataIndex: 'query07'
        },
        {
          title: this.$t('单位'),
          sorter: true,
          align: 'center',
          dataIndex: 'query08'
        },
        {
          title: this.$t('通知单号'),
          sorter: true,
          align: 'center',
          dataIndex: 'query09'
        },
        {
          title: this.$t('入库时间'),
          sorter: true,
          align: 'center',
          dataIndex: 'query10'
        },
      ]
    }
    this.queryParam = {
      pageNo: 1,
      pageSize: 10
    }
    this.getSearch()
    this.getList()
  },
  watch: {
    $route: function(newRoute) {
      this.id = this.getRequest()
      this.getList()
    }
  },
  methods: {
    pickerChange(e, a) {
      if (this.id == 'canPinGenJinTongJi') {
        this.queryParam.query03 = a[0]
        this.queryParam.query04 = a[1]
      } else if (this.id == 'chaXunMeiTianDingDan') {
        this.queryParam.query03 = a[0]
        this.queryParam.query04 = a[1]
      } else if (this.id == 'XiangChuKuXiangQing') {
        this.queryParam.query07 = a[0]
        this.queryParam.query08 = a[1]
      } else if (this.id == 'huokuanzhichu') {
        this.queryParam.query05 = a[0]
        this.queryParam.query06 = a[1]
      } else if (this.id == 'rukumingxi') {
        this.queryParam.query07 = a[0]
        this.queryParam.query08 = a[1]
      }
    },
    ExportXls() {
      let title = ""
      if (this.id == 'canPinGenJinTongJi') {
        title = "每日产品跟进统计"
      } else if (this.id == 'chaXunMeiTianDingDan') {        
        title = "每天订单量"
      } else if (this.id == 'XiangChuKuXiangQing') {
        title = "箱出库详情"
      } else if (this.id == 'huokuanzhichu') {
        title = "供应商货款支出"
      } else if (this.id == 'rukumingxi') {
        title = "入库明细"
      }
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      if(this.id == 'XiangChuKuXiangQing' && !this.order.column){
        this.order = {
          order: 'asc',
          column:'query02'
        }
      }
      let queryParam = {
        type: this.id,
        ...obj,
        ...this.order
      }
      queryParam.query18 = this.order.column
      queryParam.query19 = this.order.order
      downFile(this.url.exportXlsUrl , queryParam).then(data => {
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
      this.vau = ''
      this.getList()
    },
    getSearch() {},
    getList() {
      let obj = JSON.parse(JSON.stringify(this.queryParam))
      if(this.id == 'XiangChuKuXiangQing' && !this.order.column){
        this.order = {
          order: 'asc',
          column:'query02'
        }
      }
      let queryParam = {
        type: this.id,
        ...obj,
        pageNo: this.ipagination.current,
        pageSize: this.ipagination.pageSize,
        ...this.order
      }
      queryParam.query18 = this.order.column
      queryParam.query19 = this.order.order
      this.loading = true
      getAction(this.url.list, queryParam).then(res => {
        this.loading = false
        this.dataSource = res.result.records
        this.ipagination.total = res.result.total
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
