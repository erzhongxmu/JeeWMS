<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('出库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.orderId"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('目标托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binIdTo"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('源托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binIdFrom"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('子PO号')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsBatch"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('装箱单号')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.u8Djcode1"></a-input>
              </a-form-item>
            </a-col>
            <!--            <a-col :xl="6" :lg="7" :md="8" :sm="24">-->
            <!--              <a-form-item label="货主名称">-->
            <!--                <a-input placeholder="请输入货主名称" v-model="queryParam.cusName"></a-input>-->
            <!--              </a-form-item>-->
            <!--            </a-col>-->
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('商品名称')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="订单号" :label="$t('出库单号')" >
                <a-input placeholder="请输入订单号" v-model="queryParam.imCusCode"></a-input>
              </a-form-item>
            </a-col>-->
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
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
       <!-- <a-button icon="download" @click="handleExportXls('拣货单')" v-has="'orderPickGoods:export'">{{$t('导出')}}</a-button> -->
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">{{$t('添加')}}</a-button>
     <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
       <a-button icon="import">{{$t('导入')}}</a-button>
     </a-upload>
     <a-button key="1" @click="batchDel"><a-icon type="delete"/>{{$t('批量删除')}}</a-button>
     <a-button key="3" @click="batchEdit" v-has="'orderPickGoods:batchUpdate'"><a-icon type="edit"/>批量修改</a-button>
     <a-button key="4" @click="batchSave" v-has="'orderPickGoods:batchUpdate'"><a-icon type="save"/>批量保存</a-button>
      <a-button key="5" @click="batchCancel" v-has="'orderPickGoods:batchUpdate'"><a-icon type="stop"/>取消批量修改</a-button>-->
      <a-button
        @click="handlePrint('ck')"
        v-has="'WmOrderPickGoodsList:batchPrint'"
      >{{$t('批量打印标签')}}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>
        {{$t('项目')}}
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
          >下载</a-button>
        </template>
        <template v-for="col in columnsEdit" :slot="col" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-input
              :value="text"
              @blur="e => handleBatchChange(e.target.value, record.id, col,record)"
            />
          </div>
          <div v-else>{{text}}</div>
        </template>
        <template slot="goodsProData" slot-scope="text, record">
          <div v-if="selectedRowKeys.indexOf(record.id) != -1 && batchEditFlag">
            <a-date-picker @change="e => handleBatchChange(e, record.id, 'goodsProData')" />
          </div>
          <div v-else>{{text}}</div>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'WmOrderPickGoodsList:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'WmOrderPickGoodsList:update'" />
          <a @click="handleEdit(record)" v-has="'WmOrderPickGoodsList:update'">{{$t('编辑')}}</a>

          <!--          <a-divider type="vertical" />-->
          <!--          <a-popconfirm :title="$t('你确定吗?')" @confirm="() => handleDelete(record.id)">-->
          <!--                  <a>{{$t('删除')}}</a>-->
          <!--          </a-popconfirm>-->
        </span>
      </a-table>
    </div>
    <wmOrderPickGoods-modal ref="modalForm" @ok="modalFormOk"></wmOrderPickGoods-modal>
    <export-spin v-if="exportOk"></export-spin>

    <ShippingMark ref="ShippingMark" />
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmOrderPickGoodsModal from './modules/WmOrderPickGoodsModal'
import JDictSelectTag from '@/components/dict/JDictSelectTag.vue'
import { putAction, getAction } from '@/api/manage'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'

export default {
  name: 'WmToDownGoodsList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    JDictSelectTag,
    WmOrderPickGoodsModal,
    ExportSpin,
    ShippingMark
  },
  data() {
    return {
      description: '下架明细管理页面',
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      //可编辑行
      columnsEdit: ['goodsQua', 'goodsQuaok', 'baseUnit', 'binIdFrom', 'kuWeiBianMa'],
      // 表头
      columns: [
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
          title: this.$t('装箱单号'),
          align: 'left',
          dataIndex: 'u8Djcode1',
          sorter: true
        },
        {
          title: this.$t('子PO号'),
          align: 'left',
          dataIndex: 'goodsBatch',
          sorter: true
        },
        {
          title: this.$t('商品编码'),
          align: 'left',
          dataIndex: 'goodsId',
          sorter: true
        },
        {
          title: this.$t('商品名称'),
          align: 'left',
          dataIndex: 'goodsName',
          sorter: true
        },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusCode_dictText',
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          sorter: true
        },
        {
          title: this.$t('数量'),
          align: 'left',
          dataIndex: 'goodsQua',
          scopedSlots: { customRender: 'goodsQua' },
          sorter: true
        },
        // {
        //   title:this.$t('完成数量'),
        //   align: 'left',
        //   dataIndex: 'goodsQuaok',
        //   scopedSlots: { customRender: 'goodsQuaok' },
        // sorter: true
        // },
        {
          title: this.$t('出货数量'),
          align: 'left',
          dataIndex: 'baseGoodscount',
          sorter: true
        },

        {
          title: this.$t('库位编码'),
          align: 'left',
          dataIndex: 'kuWeiBianMa',
          scopedSlots: { customRender: 'kuWeiBianMa' },
          sorter: true
        },
        // {
        //   title:this.$t('源托盘'),
        //   align: 'left',
        //   dataIndex: 'binIdFrom',
        //   scopedSlots: { customRender: 'binIdFrom' },
        // sorter: true
        // },
        {
          title: this.$t('托盘'),
          align: 'left',
          dataIndex: 'binIdTo',
          sorter: true
        },

        // {
        //   title:this.$t('出库单号'),
        //   align: 'left',
        //   dataIndex: 'orderId',
        // sorter: true
        // },
        //  {
        //   title:this.$t('批次'),
        //   align: 'left',
        //   dataIndex: 'goodsBatch',
        // sorter: true
        // },

        // {
        //   title:this.$t('状态'),
        //   align: 'left',
        //   dataIndex: 'downSta',
        // sorter: true
        // },

        {
          title: this.$t('创建人'),
          align: 'left',
          dataIndex: 'createBy',
          sorter: true
        },
        {
          title: this.$t('创建日期'),
          align: 'left',
          dataIndex: 'createTime',
          customRender: function(text) {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },
        {
          title: this.$t('生产日期'),
          align: 'left',
          dataIndex: 'goodsProData',
          scopedSlots: { customRender: 'goodsProData' },
          sorter: true
        },
        {
          title: this.$t('单号'),
          align: 'left',
          dataIndex: 'orderId',
          sorter: true
        },
        {
          title: this.$t('箱子规格'),
          align: 'left',
          dataIndex: 'tinVolume',
          // scopedSlots: { customRender: 'tinVolume' },
          sorter: true
        },
        {
          title: this.$t('重量'),
          align: 'left',
          dataIndex: 'tinWeight',
          // scopedSlots: { customRender: 'tinWeight' },
          sorter: true
        },
        // {
        //   title:this.$t('基本单位'),
        //   align: 'left',
        //   dataIndex: 'baseUnit',
        //   scopedSlots: { customRender: 'baseUnit' },
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          // fixed:"right",
          width: 147,
          scopedSlots: { customRender: 'action' }
        }
        // {
        //   title:this.$t('货主单号'),
        //   align: 'left',
        //   dataIndex: 'imCusCode'
        // }
      ],
      url: {
        list: '/jeewms/wmToDownGoods/orderPickingList',
        delete: '/jeewms/wmToDownGoods/delete',
        deleteBatch: '/jeewms/wmToDownGoods/deleteBatch',
        exportXlsUrl: '/jeewms/wmToDownGoods/exportXls',
        importExcelUrl: 'jeewms/wmToDownGoods/importExcel',
        editBatch: '/jeewms/wmToDownGoods/editBatch'
      },
      dictOptions: {},
      //查看详细数据
      detailInfo: {},
      //批量修改标记
      batchEditFlag: false,
      //批量修改时新值数据
      newColData: [],
      //批量修改时数据修改行的id集合
      newColIds: []
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  methods: {
    // handlePrint(){
    //   if(this.selectedRowKeys.length < 1) {
    //     this.$message.warning(this.$t('请选择一条记录!'));
    //     return;
    //   }
    //   var ids = this.selectedRowKeys.join(',')
    //   getAction('/jeewms/wmOmQmI/labelPrinting2',{ids}).then(res=>{})
    // },
    handlePrint(e) {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let ids = this.selectedRowKeys.join(',')
      this.selectionRows.map((item, index) => {
        item.xianghao = item.tinId
        item.eachBox = item.baseGoodscount
        item.goodsCode = item.goodsId
      })
      let data = {
        list: this.selectionRows,
        param: {
          id: ids,
          type: e,
          pageType: 'XJTZ'
        }
      }
      this.$refs.ShippingMark.onShow(data)
    },
    handleCancel() {
      this.visible = false
      this.detailInfo = {}
    },
    batchEdit() {
      if (this.selectedRowKeys.length > 0) {
        this.batchEditFlag = true
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    //可编辑行改变时调用
    handleBatchChange(newValue, id, col) {
      var newSingleData = {}
      if (this.newColIds.indexOf(id) != -1) {
        this.newColData.forEach((item, index) => {
          if (item.id == id) {
            this.newColData[index][col] = newValue
          }
        })
      } else {
        this.newColIds.push(id)
        newSingleData[col] = newValue
        newSingleData.id = id
        this.newColData.push(newSingleData)
      }
      console.log(this.newColData, this.newColIds)
    },
    //批量保存
    batchSave() {
      var that = this
      if (this.batchEditFlag) {
        putAction(that.url.editBatch, that.newColData).then(res => {
          if (res.success) {
            that.$message.success('批量修改成功')
            that.newColData = []
            that.newColIds = []
            that.loadData()
            that.batchEditFlag = false
          } else {
            that.$message.warning(this.$t(res.message))
          }
        })
      } else {
        this.$message.warning('无可保存内容!')
      }
    },
    //取消批量修改
    batchCancel() {
      if (this.batchEditFlag) {
        this.newColData = []
        this.newColIds = []
        this.batchEditFlag = false
        this.selectedRowKeys = []
      } else {
        this.$message.warning('无修改内容可取消!')
      }
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>