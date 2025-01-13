<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('仓库')">
              <a-select v-model="storeCode" :placeholder="$t('请选择')" @change="getKuqu">
                <a-select-option v-for="(item, index) in storeList" :value="item.storeCode" :key="index">{{
                  item.storeName
                }}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品名称')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.shpMingCheng"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('库位')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.kuWeiBianMa" />
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('库存类型')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.kuctype"></a-input>
              </a-form-item>
            </a-col>
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item  :label="$t('移动日期')">
                <a-range-picker @change="onChangeDate" />
              </a-form-item>
            </a-col> -->
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="货主">-->
            <!--                <a-input placeholder="请输入货主" v-model="queryParam.cusCode"></a-input>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="货主名称">-->
            <!--                <a-input placeholder="请输入货主名称" v-model="queryParam.zhongWenQch"></a-input>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="生产日期">-->
            <!--                <a-range-picker @change="onChangeDate1" />-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{ $t('查询') }}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{ $t('重置') }}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>-->
      <a-button icon="download" @click="handleExportXls($t('生成库位转移'))" v-has="'WmToMoveStorageList:export'">{{
        $t('导出')
      }}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">-->
      <!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <a-button @click="batchTask" v-has="'WmToMoveStorageList:batchMove'" :loading="confirmLoading">{{
        $t('批量转移')
      }}</a-button>
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="6" @click="batchTask">批量生产库存转移</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{ $t('已选择') }}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >{{ $t('项目') }}
        <a style="margin-left: 24px" @click="onClearSelected">{{ $t('清空') }}</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无图片') }}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">{{ $t('无文件') }}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <!--          <a @click="handleEdit(record)">{{$t('编辑')}}</a>-->

          <!--          <a-divider type="vertical" />-->
          <!--          <a>生产托盘转移</a>-->
        </span>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { postAction } from '@/api/manage'
import { getAction } from '@/api/manage'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import moment from 'moment'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'

export default {
  name: 'WmToMoveTrayList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass],
  components: {
    JDictSelectTag,
  },
  data() {
    return {
      description: '托盘转移管理页面',
      confirmLoading: false,
      storeCode:'',
      // 表头
      columns: [
        // {
        //   title: this.$t('操作'),
        //   dataIndex: 'action',
        //   align: 'left',
        //   // fixed:"right",
        //   width:147,
        //   scopedSlots: { customRender: 'action' }
        // },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'shpMingCheng',
          sorter: true,
        },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          sorter: true,
        },
        {
          title: this.$t('货主编码'),
          align: 'left',
          dataIndex: 'cusCode',
          sorter: true,
        },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'zhongWenQch',
          sorter: true,
        },
        {
          title: this.$t('库位'),
          align: 'left',
          dataIndex: 'kuWeiBianMa',
          sorter: true,
        },
        {
          title: this.$t('托盘'),
          align: 'left',
          dataIndex: 'binId',
          sorter: true,
        },
        {
          title: this.$t('库存类型'),
          align: 'left',
          dataIndex: 'kuctype',
          sorter: true,
        },
        {
          title: this.$t('移动日期'),
          align: 'left',
          dataIndex: 'lastMove',
          sorter: true,
        },

        {
          title: this.$t('数量'),
          align: 'left',
          dataIndex: 'goodsQua',
          sorter: true,
        },
        {
          title: this.$t('生产日期'),
          align: 'left',
          dataIndex: 'goodsProData',
          sorter: true,
        },
        // {
        //   title:this.$t('批次'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch'
        // },

        // {
        //   title:this.$t('保质期'),
        //   align: 'left',
        //   dataIndex: 'bzhiQi'
        // },
        // {
        //   title:this.$t('单位'),
        //   align: 'left',
        //   dataIndex: 'goodsUnit'
        // },
        {
          title: this.$t('状态'),
          align: 'left',
          dataIndex: 'moveSta',
          sorter: true,
        },
      ],
      disableMixinCreated: true,
      url: {
        list: '/jeewms/wmToMoveGoods/findPageList',
        editBatch: '/jeewms/wmToMoveGoods/addWmToMoveGoods',
        addWmToMoveGoods: '/jeewms/wmToMoveGoods/addWmToMoveGoods',
        exportXlsUrl: '/jeewms/wmToMoveGoods/exportTpXls',
      },
      storeList: [],
      /* 分页参数 */
      ipagination: {
        current: 1,
        pageSize: 20,
        pageSizeOptions: ['5', '10', '20', '50'],
        showTotal: (total, range) => {
          return range[0] + '-' + range[1] + ' ' + this.$t('共') + ' ' + total + ' ' + this.$t('项目')
        },
        showQuickJumper: true,
        showSizeChanger: true,
        total: 0,
      },
    }
  },
  mounted() {
    this.$http.get('jeewms/baStore/selectList').then((res) => {
      if (res.success) {
        this.storeList = res.result
        this.storeList.map((item, index) => {
          if (item.storeCode === 'Pinghu') {
            this.storeCode = item.storeCode
          }
        })
        this.queryParam.storeCode = this.storeCode
        this.getKuqu()
        this.loadData()
      } else {
        this.$message.error(this.$t('操作失败'))
      }
    })
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
      getKuqu(){
        this.areaCode = ''
        this.queryParam.areaCode = ''
        this.queryParam.storeCode = this.storeCode
        this.kuquList = []
        this.$http.get('jeewms/baStoreArea/list',{
          params:{
            storeCode:this.queryParam.storeCode
          }
        }).then(res=>{
          if (res.success){
            this.kuquList = res.result.records
          }else{
            this.$message.error(this.$t('操作失败'))
          }
        })
      },
    initDictConfig() {},
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then(res => {
        if (res.success) {
          this.dataSource = res.result.records
          this.ipagination.total = res.result.total
        }
        if (res.code === 510) {
          this.$message.warning(this.$t('操作失败'))
        }
        this.loading = false
      })
    },
    onChangeDate(val) {
      this.queryParam.lastMove_begin1 = moment(val[0]).format('YYYY-MM-DD')
      this.queryParam.lastMove_end2 = moment(val[1]).format('YYYY-MM-DD')
    },
    onChangeDate1(val) {
      this.queryParam.goodsProData_begin = moment(val[0]).format('YYYY-MM-DD')
      this.queryParam.goodsProData_end = moment(val[1]).format('YYYY-MM-DD')
    },
    batchTask() {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      var ids = this.selectedRowKeys
      this.confirmLoading = true
      postAction(this.url.addWmToMoveGoods, ids).then((res) => {
        if (res.success) {
          this.$message.success(res.message)
          this.loadData()
          this.confirmLoading = false
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
          this.confirmLoading = false
        }
      })
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>