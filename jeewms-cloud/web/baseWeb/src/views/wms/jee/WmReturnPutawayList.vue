<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="到货通知单">
              <a-input placeholder="请输入到货通知单" v-model="queryParam.imNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="商品编码">
              <a-input placeholder="请输入商品编码" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="托盘">
                <a-input placeholder="请输入托盘" v-model="queryParam.tinId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="仓位">
                <a-input placeholder="请输入仓位" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开')  }}
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
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'returnPutaway:add'">{{$t('添加')}}</a-button>
      <a-button icon="download" @click="handleExportXls('退货上架单')" v-has="'returnPutaway:export'">{{$t('导出')}}</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button icon="import" v-has="'returnPutaway:import'">{{$t('导入')}}</a-button>
      </a-upload>
      <a-button key="1" @click="batchDel" v-has="'returnPutaway:batchDelete'">
        <a-icon type="delete" />批量删除
      </a-button>
      <a-button
        @click="handleRise(selectedRowKeys,'batch')"
        v-has="'returnPutaway:batchPutaway'"
      >批量上架</a-button>
      <a-button @click="showModal = true" v-has="'returnPutaway:batchUpdateKw'">批量更改库位</a-button>
      <a-button @click="showModal1 = true" v-has="'returnPutaway:batchUpdateDate'">批量更改生产日期</a-button>
      <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
      <!--        <a-menu slot="overlay">-->
      <!--          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>-->
      <!--          <a-menu-item key="2" @click="handleRise(selectedRowKeys,'batch')"><a-icon type="rise"/>上架</a-menu-item>-->
      <!--          <a-menu-item key="3" @click="showModal = true"><a-icon type="edit"/>更改库位</a-menu-item>-->
      <!--          <a-menu-item key="4" @click="showModal1 = true"><a-icon type="edit"/>更改生产日期</a-menu-item>-->
      <!--        </a-menu>-->
      <!--        <a-button style="margin-left: 8px"> {{$t('批量操作')}} <a-icon type="down" /></a-button>-->
      <!--      </a-dropdown>-->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange"
        :rowClassName="setRowClsaa"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt
            style="max-width:80px;font-size: 12px;font-style: italic;"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)"
          >{{$t('下载')}}</a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'returnPutaway:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'returnPutaway:check'" />
          <a @click="handleEdit(record)" v-has="'returnPutaway:update'">{{$t('编辑')}}</a>
          <a-divider type="vertical" v-has="'returnPutaway:update'" />
          <a @click="handleRise(record.id,'alone')" v-has="'returnPutaway:putaway'">上架</a>
          <a-divider type="vertical" v-has="'returnPutaway:putaway'" />
          <a-popconfirm
            :title="$t('你确定吗?')"
            @confirm="() => handleDelete(record.id)"
            v-has="'returnPutaway:delete'"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>
        </span>
      </a-table>
    </div>
    <j-modal
      title="批量修改库位"
      width="800px"
      :visible="showModal"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="handleEditStorage"
      @cancel="handleCancelEditStorage"
      
    >
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item label="库位" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input placeholder="请输入库位" v-decorator="['storage',validatorRules.storage]" />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>
    <j-modal
      title="批量修改生产日期"
      width="800px"
      :visible="showModal1"
      :confirmLoading="confirmLoading1"
      switchFullscreen
      @ok="handleEditData"
      @cancel="handleCancelEditData"
      
    >
      <a-spin :spinning="confirmLoading1">
        <a-form :form="form1">
          <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-date-picker
              style="width: 100%"
              placeholder="请选择生产日期"
              v-decorator="['produceData',validatorRules.produceData]"
            />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>

    <wmReturnPutaway-modal ref="modalForm" @ok="modalFormOk"></wmReturnPutaway-modal>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmReturnPutawayModal from './modules/WmReturnPutawayModal'
import { putAction } from '@/api/manage'
import { httpAction } from '@/api/manage'
import { getAction } from '@/api/manage'
import { postAction } from '@/api/manage'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'

export default {
  name: 'WmInQmIList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    WmReturnPutawayModal
  },
  data() {
    return {
      description: 'wm_in_qm_i管理页面',
      showModal: false,
      showModal1: false,
      confirmLoading: false,
      confirmLoading1: false,
      form: this.$form.createForm(this),
      form1: this.$form.createForm(this),
      validatorRules: {
        storage: {
          rules: [{ required: true, message: '请输入库位!' }]
        },
        produceData: {
          rules: [{ required: true, message: '请选择生产日期!' }]
        }
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      // 表头
      columns: [
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 200,
          scopedSlots: { customRender: 'action' }
        },
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key:'rowIndex',
        //   width:60,
        //   align: 'left',
        //   customRender:function (t,r,index) {
        //     return parseInt(index)+1;
        //   }
        // },
        {
          title: '到货通知单',
          align: 'left',
          dataIndex: 'imNoticeId'
        },
        {
          title: '到货通知行项目',
          align: 'left',
          dataIndex: 'imNoticeItem'
        },
        {
          title: '商品编码',
          align: 'left',
          dataIndex: 'goodsId'
        },
        {
          title: '到货数量',
          align: 'left',
          dataIndex: 'imQuat'
        },
        {
          title: '收货数量',
          align: 'left',
          dataIndex: 'qmOkQuat'
        },
        {
          title: '生产日期',
          align: 'left',
          dataIndex: 'proData',
          customRender: (text) => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          }
        },
        {
          title: '备注',
          align: 'left',
          dataIndex: 'itemText'
        }
        // {
        //   title:'托盘',
        //   align: 'left',
        //   dataIndex: 'tinId'
        // },
        // {
        //   title:'单位',
        //   align: 'left',
        //   dataIndex: 'goodsUnit'
        // },
        // {
        //   title:'批次',
        //   align: 'left',
        //   dataIndex: 'goodsBatch'
        // },
        // {
        //   title:'仓位',
        //   align: 'left',
        //   dataIndex: 'binId'
        // },
        // {
        //   title:'体积',
        //   align: 'left',
        //   dataIndex: 'tinTj'
        // },
        // {
        //   title:'重量',
        //   align: 'left',
        //   dataIndex: 'tinZhl'
        // },
        // {
        //   title:'是否已上架',
        //   align: 'left',
        //   dataIndex: 'binSta'
        // },
        // {
        //   title:'货主',
        //   align: 'left',
        //   dataIndex: 'cusCode'
        // },
        // {
        //   title:'温度',
        //   align: 'left',
        //   dataIndex: 'recDeg'
        // },
        // {
        //   title:'基本单位',
        //   align: 'left',
        //   dataIndex: 'baseUnit'
        // },
        // {
        //   title:'基本单位数量',
        //   align: 'left',
        //   dataIndex: 'baseGoodscount'
        // },
        // {
        //   title:'基本单位收货数量',
        //   align: 'left',
        //   dataIndex: 'baseQmcount'
        // },
        // {
        //   title:'货主名称',
        //   align: 'left',
        //   dataIndex: 'cusName'
        // },
        // {
        //   title:'商品名称',
        //   align: 'left',
        //   dataIndex: 'goodsName'
        // },
        // {
        //   title:'入库单号',
        //   align: 'left',
        //   dataIndex: 'imCusCode'
        // }
      ],
      url: {
        list: '/jeewms/wmInQmI/sjList',
        delete: '/jeewms/wmInQmI/delete',
        deleteBatch: '/jeewms/wmInQmI/deleteBatch',
        exportXlsUrl: '/jeewms/wmInQmI/exportXls',
        importExcelUrl: 'jeewms/wmInQmI/importExcel',
        batchUpdateBin: '/jeewms/wmInQmI/batchUpdateBin',
        batchUpdateProduceDate: '/jeewms/wmInQmI/batchUpdateProduceDate',
        upToShelf: '/jeewms/wmInQmI/upToShelf1'
      },
      dictOptions: {}
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    initDictConfig() {},
    handleEditStorage() {
      this.form.validateFields((err, value) => {
        if (!err) {
          var storage = this.form.getFieldValue('storage')
          var param = {
            kwCode: storage,
            id: this.selectedRowKeys
          }
          postAction(this.url.batchUpdateBin, param).then(res => {
            if (res.success) {
              this.showModal = false
              this.selectedRowKeys = []
              this.loadData()
              this.$message.success('设置成功')
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      })
    },
    handleCancelEditStorage() {
      this.showModal = false
      this.form.setFieldsValue({
        //《=关键 清空tag选项
        storage: ''
      })
    },
    handleEditData() {
      this.form1.validateFields((err, value) => {
        if (!err) {
          var produceData = this.form1.getFieldValue('produceData')
          var param = {
            produceDate: produceData,
            id: this.selectedRowKeys
          }
          postAction(this.url.batchUpdateProduceDate, param).then(res => {
            if (res.success) {
              this.showModal1 = false
              this.selectedRowKeys = []
              this.loadData()
              this.$message.success('设置成功')
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      })
    },
    handleCancelEditData() {
      this.showModal1 = false
      this.$nextTick(() => {
        this.form1.setFieldsValue({
          //《=关键 清空tag选项
          produceData: ''
        })
      })
    },

    //上架
    rise(param) {
      postAction(this.url.upToShelf, {idList:param}).then(res => {
        if (res.success) {
          this.$message.success('上架成功')
          this.selectedRowKeys = []
          this.loadData()
        } else {
          this.$message.warning(this.$t('操作失败'))
        }
      })
    },
    handleRise(ids, type) {
      var that = this
      var param
      if (type == 'alone') {
        param = ids
        that.rise(param)
      } else {
        param = ids
        that.$confirm({
          title: '确定上架',
          content: '是否确认上架选定数据',
          onOk() {
            that.rise(param)
          },
          onCancel() {}
        })
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>