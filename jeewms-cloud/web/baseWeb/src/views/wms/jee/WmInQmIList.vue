<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('入库单号')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.imNoticeId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('是否上架')">
              <a-select v-model="queryParam.binSta">
                <a-select-option value="N">{{$t('未上架')}}</a-select-option>
                <a-select-option value="Y">{{$t('已上架')}}</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('商品编码')">
              <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsId"></a-input>
            </a-form-item>
          </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('商品名称')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('客户')">
                <j-popup
                  v-model="queryParam.cusCode"
                  code="cus_jiancheng"
                  org-fields="zhong_wen_qch,ke_hu_bian_ma"
                  dest-fields="cusName,cusCode"
                  field="cusCode"
                />
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('托盘')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.tinId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="4" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('库位')">
                <a-input :placeholder="$t('请输入')" v-model="queryParam.binId"></a-input>
              </a-form-item>
            </a-col>
          <a-col :xl="4" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button @click="searchReset" icon="reload" style="margin-left: 8px">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button
        @click="printBqCode"
        type="primary"
        icon="plus"
        v-has="'WmImAcceptIList:printLabel'"
      >{{$t('样品标签打印')}}</a-button> -->
    
      <!--      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'inQm:add'">{{$t('添加')}}</a-button>-->
      <a-button
        icon="download"
        @click="handleExportXls($t('收货登记'))"
        v-has="'inQm:export'"
      >{{$t('导出')}}</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" v-has="'inQm:import'" :headers="tokenHeader" :action="importExcelUrl"-->
      <!--                @change="handleImportExcel">-->
      <!--        <a-button icon="import">{{$t('导入')}}</a-button>-->
      <!--      </a-upload>-->
      <a-button @click="batchDel" v-has="'inQm:batchDelete'">
        <a-icon type="delete" />
        {{$t('批量删除')}}
      </a-button>
      <a-button @click="handleRise(selectedRowKeys,'batch')" v-has="'inQm:batchRise'" :loading="isLoading">
        <a-icon type="rise" />
        {{$t('批量上架')}}
      </a-button>
      <a-button @click="openModal('modal')" v-has="'inQm:chooseStorage'">
        <a-icon type="edit" />
        {{$t('选择库位')}}
      </a-button>
      <a-button @click="openModal('modal1')" v-has="'inQm:batchUpdateDate'">
        <a-icon type="edit" />
        {{$t('批量修改生产日期')}}
      </a-button>
      <a-button @click="handlePrint('rk')" v-has="'inQm:printLabel'">{{$t('批量打印标签')}}</a-button>
      <a-button @click="handlePrint('xiangmai')" v-has="'inQm:printBox'">{{$t('批量打印箱唛')}}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i>
        {{$t('已选择')}}
        <a style="font-weight: 600">
          {{
          selectedRowKeys.length }}
        </a>
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

        <span slot="action" slot-scope="text, record">
          <a @click="handleCheck(record)" v-has="'inQm:check'">{{$t('查看')}}</a>
          <a-divider type="vertical" v-has="'inQm:check'" />
          <a @click="handleEdit(record)" v-has="'inQm:update'">{{$t('编辑')}}</a>
          <!-- <a-divider type="vertical" v-has="'inQm:check'"/>
          <a @click="handlePrint(record,2)" v-has="'inQm:update'">{{$t('打印标签')}}</a>
          <a-divider type="vertical" v-has="'inQm:check'"/>
          <a @click="handlePrint2(record,2)" v-has="'inQm:update'">{{$t('打印箱唛')}}</a>-->
          <a-divider type="vertical" v-has="'inQm:update'" />
          <a-popconfirm
            :title="$t('你确定吗?')"
            @confirm="() => handleDelete(record.id)"
            v-has="'inQm:delete'"
          >
            <a>{{$t('删除')}}</a>
          </a-popconfirm>

          <a-divider type="vertical" v-if="record.binSta=='N'" v-has="'inQm:delete'" />
          <a
            v-if="record.binSta=='N'"
            @click="handleRise(record.id,'alone')"
            v-has="'inQm:rise'"
          >{{$t('上架')}}</a>
        </span>
      </a-table>
    </div>
    <j-modal
      :title="$t('批量修改库位')"
      width="800px"
      class="ownModal"
      :visible="showModal"
      :confirmLoading="confirmLoading"
      switchFullscreen
      @ok="handleEditStorage"
      @cancel="handleCancelEditStorage"
    >
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">
          <a-form-item
            style="width: 100% !important;"
            :label="$t('库位')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <!--            <j-search-select-tag type="list" v-decorator="['storage', validatorRules.storage]" :trigger-change="true" dict="md_bin,ku_wei_ming_cheng,ku_wei_bian_ma" placeholder="请选择库位编码"/>-->
            <!--            <a-input placeholder="请输入库位" v-decorator="['storage',validatorRules.storage]"/>-->
            <!-- <a-select v-decorator="['storage', validatorRules.storage]" :placeholder="$t('请选择')">
              <a-select-option
                v-for="(item,key) in kwList"
                :value="item.kwCode"
                :key="key"
              >{{item.kwValue}}</a-select-option>
            </a-select> -->
            <j-popup
              v-decorator="['storage', validatorRules.storage]"
              :trigger-change="true"
              code="ku_wei_biao_ma"
              org-fields="ku_wei_bian_ma"  
              dest-fields="storage"
              @callback="popupCallback"
              />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>
    <j-modal
      :title="$t('批量修改生产日期')"
      width="800px"
      class="ownModal"
      :visible="showModal1"
      :confirmLoading="confirmLoading1"
      switchFullscreen
      @ok="handleEditData"
      @cancel="handleCancelEditData"
    >
      <a-spin :spinning="confirmLoading1">
        <a-form :form="form1">
          <a-form-item
            style="width: 100% !important;"
            :label="$t('生产日期')"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
          >
            <a-date-picker
              style="width: 100%"
              :placeholder="$t('请选择')"
              v-decorator="['produceData',validatorRules.produceData]"
            />
          </a-form-item>
        </a-form>
      </a-spin>
    </j-modal>

     <j-modal :visible="visibledy1" :title="$t('打印标签')" :width="700" @cancel="visibledy1=false">
      <!-- 自定义弹窗确定按钮，实现打印功能 -->
      <template slot="footer">
        <a-button key="back" @click="visibledy1=false">{{$t('取消')}}</a-button>
        <a-button key="submit" type="primary" :loading="loading" v-print="'#printbq'">{{$t('打印')}}</a-button>
      </template>

      <!-- 打印模板 -->
      <div style="margin-bottom: 20px">
        <div class="printTitle">
          <p>样品类型：</p>
          <j-search-select-tag v-model="bqText" dict="sample_type" :placeholder="$t('请选择')" />
        </div>
        <!-- <div class="printTitle" style="margin-top: 10px">
          <p>份额：</p>
          <a-input v-model="bqNum" />
        </div>-->
        <!-- <a-button  v-print="'#printbq'" style="margin-bottom: 10px">{{$t('打印')}}</a-button> -->
        <div class="printBady" id="printbq" ref="print">
          <div
            class="printItem"
            v-for="(item,index) in selectionRows"
            :key="index"
            style="page-break-after:always"
          >
            <div class="printItem3">
              <div class="printItem_1">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
              <div class="printItem_2">
                <p class="printItem_title">{{bqText}}</p>
                <p>- {{item.cusCode_dictText}}</p>
                <p>- {{item.goodsName}}</p>
                <p>Date：</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </j-modal>

    <wmInQmI-modal ref="modalForm" @ok="modalFormOk" :goodsTypeId="goodsTypeId"></wmInQmI-modal>
    <export-spin v-if="exportOk"></export-spin>

    <ShippingMark  ref="ShippingMark" />
    <!-- <AutoPrint  ref="AutoPrint" /> -->
    
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import WmInQmIModal from './modules/WmInQmIModal'
import { postAction, getAction } from '@/api/manage'
import { commonTableRowClass } from '@/mixins/commonTableRowClass'
import { listCheckMixin } from '@/mixins/listCheckMixin'
import JSearchSelectTag from '@/components/dict/JSearchSelectTag'
import ExportSpin from '@/components/ExportSpin/ExportSpin'
import ShippingMark from '@/views/wms/jee/pltn/ShippingMark'
// import AutoPrint from '@/components/AutoPrint/AutoPrint.vue'


export default {
  name: 'WmInQmIList',
  mixins: [JeecgListMixin, mixinDevice, commonTableRowClass, listCheckMixin],
  components: {
    WmInQmIModal,
    JSearchSelectTag,
    ExportSpin,
    ShippingMark,
    // AutoPrint
  },
  data() {
    return {
      isLoading:false,
      disableMixinCreated: true,
      description: 'wm_in_qm_i管理页面',
      bqText: 'GROLET LONDON',
      showModal: false,
      showModal1: false,
      confirmLoading: false,
      confirmLoading1: false,
      visibledy1: false,
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
        // {
        //   title: '#',
        //   dataIndex: '',
        //   key: 'rowIndex',
        //   width: 60,
        //   align: 'left',
        //   customRender: function(t, r, index) {
        //     return parseInt(index) + 1
        //   }
        // },
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
          title: this.$t('商品中文名称'),
          align: 'left',
          dataIndex: 'ywMingCheng',
          sorter: true
        },
        // {
        //   title: '到货通知行项目',
        //   align: 'left',
        //   dataIndex: 'imNoticeItem'
        // },
        // {
        //   title: '商品编码',
        //   align: 'left',
        //   dataIndex: 'goodsId'
        // },
        // {
        //   title: '单托数量',
        //   align: 'left',
        //   dataIndex: 'imQuat'
        // },
        {
          title: this.$t('上架数量'),
          align: 'left',
          dataIndex: 'qmOkQuat',
          sorter: true
        },
        {
          title: this.$t('单位'),
          align: 'left',
          dataIndex: 'goodsUnit',
          sorter: true
        },
        // {
        //   title: this.$t('托盘'),
        //   align: 'left',
        //   dataIndex: 'tinId'
        // },
        // {
        //   title: '商品分类',
        //   align: 'left',
        //   dataIndex: 'goodsTypeName'
        // },
        {
          title: this.$t('生产日期'),
          align: 'left',
          dataIndex: 'proData',
          customRender: text => {
            return !text ? '' : text.length > 10 ? text.substr(0, 10) : text
          },
          sorter: true
        },
        {
          title: this.$t('批次'),
          align: 'left',
          dataIndex: 'goodsBatch',
          sorter: true
        },
        {
          title: this.$t('库位'),
          align: 'left',
          dataIndex: 'binId',
          sorter: true
        },
        // {
        //   title: '体积',
        //   align: 'left',
        //   dataIndex: 'tinTj'
        // },
        // {
        //   title: '重量',
        //   align: 'left',
        //   dataIndex: 'tinZhl'
        // },
        {
          title: this.$t('是否上架'),
          align: 'left',
          dataIndex: 'binSta',
          customRender: (t, r, index) => {
            return t == 'N' ? this.$t('未上架') : this.$t('已上架')
          }
        },
        {
          title: this.$t('货主名称'),
          align: 'left',
          dataIndex: 'cusName',
          sorter: true
        },
        // {
        //   title: '温度',
        //   align: 'left',
        //   dataIndex: 'recDeg'
        // },
        // {
        //   title: '基本单位',
        //   align: 'left',
        //   dataIndex: 'baseUnit'
        // },
        // {
        //   title: '基本单位数量',
        //   align: 'left',
        //   dataIndex: 'baseGoodscount'
        // },
        // {
        //   title: '基本单位收货数量',
        //   align: 'left',
        //   dataIndex: 'baseQmcount'
        // },
        // {
        //   title: '货主名称',
        //   align: 'left',
        //   dataIndex: 'cusName'
        // },
        //
        // {
        //   title: '入库单号',
        //   align: 'left',
        //   dataIndex: 'imCusCode'
        // },
        // {
        //   title: this.$t('品质'),
        //   align: 'left',
        //   dataIndex: 'itemText'
        // },
        {
          title: this.$t('入库单号'),
          align: 'left',
          dataIndex: 'imNoticeId',
          sorter: true
        },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'left',
          width: 200,
          // fixed:"right",
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/jeewms/wmInQmI/list',
        delete: '/jeewms/wmInQmI/delete',
        deleteBatch: '/jeewms/wmInQmI/deleteBatch',
        exportXlsUrl: '/jeewms/wmInQmI/exportXls',
        importExcelUrl: 'jeewms/wmInQmI/importExcel',
        batchUpdateBin: '/jeewms/wmInQmI/batchUpdateBin',
        batchUpdateProduceDate: '/jeewms/wmInQmI/batchUpdateProduceDate',
        upToShelf: '/jeewms/wmInQmI/upToShelf1',
        getKwListByGoodsType: '/jeewms/baKw/getKwListByGoodsType'
      },
      dictOptions: {},
      isSameGoodType: true,
      kwList: [],
      goodsTypeId: '',
      printb:[], // 打印的内容
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  mounted() {
    this.queryParam.binSta = 'N'
    this.loadData()
  },
  methods: {
//     handlePrint(e) {
//       if(this.selectedRowKeys.length < 1) {
//         this.$message.warning(this.$t('请选择一条记录!'))
//         return
//       }
//       let ids = this.selectedRowKeys.join(',')
//       let url = '/jeewms/wmImNoticeH/labelPrints' // 默认打印箱唛
//       if (e == 1) {
//         url = '/jeewms/wmImNoticeH/labelPrinting' // 标签
//       }
//       getAction(url, { ids }).then(res => {})
//     },
    printBqCode() { // 样品标签打印
      if (this.selectedRowKeys.length > 0) {
        this.visibledy1 = true
        // this.countArr(this.bqNum)
      } else {
        this.$message.warning(this.$t('请选择一条记录!'))
      }
    },
    popupCallback(row){
      this.form.setFieldsValue(row)
    },
    handlePrint(e) {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      let ids = this.selectedRowKeys.join(',')
      this.selectionRows.map((item,index)=>{
          item.xianghao = item.tinId
          item.eachBox = item.baseGoodscount
          item.goodsCode = item.goodsId
      })
      let data = {
        list: this.selectionRows,
        param:{
          id:ids,
          type:e,
          pageType:'SJ'
        }
      }
      this.$refs.ShippingMark.onShow(data)
    },
    handleEdit: function(record) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.title = '编辑'
      this.$refs.modalForm.disableSubmit = false
      this.goodsTypeId = record.goodsTypeId
    },
    openModal(type) {
      if (this.selectedRowKeys.length < 1) {
        this.$message.warning(this.$t('请选择一条记录!'))
        return
      }
      this.isSameGoodType = true
      var baseType = this.selectionRows[0].goodsTypeId
      // for (let i = 0; i < this.selectionRows.length; i++) {
      //   if (this.selectionRows[i].goodsTypeId != baseType) {
      //     this.isSameGoodType = false
      //     break
      //   }
      // }
      if (type == 'modal') {
        if (!this.isSameGoodType) {
          this.$message.warning('请选择商品分类一致的数据!')
        } else {
          this.kwList = []
          postAction(this.url.getKwListByGoodsType, [baseType]).then(res => {
            if (res.success) {
              for (let key in res.result) {
                this.kwList.push({ kwCode: key, kwValue: res.result[key] })
              }
              console.log(this.kwList)
              this.showModal = true
            } else {
              this.$message.warning(this.$t('操作失败'))
            }
          })
        }
      } else {
        this.showModal1 = true
      }
    },
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
      this.isLoading = true
      postAction(this.url.upToShelf, param).then(res => {
        this.isLoading = false
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
      var param = {}
      if (type == 'alone') {
        param.idList = ids
        that.rise(param)
      } else {
        if (this.selectedRowKeys.length < 1) {
          this.$message.warning(this.$t('请选择一条记录!'))
          return
        }
        param.idList = ids.join(',')
        that.rise(param)
      }
    }
  }
}
</script>
<style scoped lang="less">
@import '~@assets/less/common.less';

/deep/ .ant-table-row-cell-break-word {
  text-align: left !important;
}
/deep/ .ant-table-row-cell-break-word span {
  padding-left: 15px !important;
}
/deep/ .ant-table-thead .ant-table-row-cell-break-word {
  text-align: center !important;
}
/deep/ .ant-table-thead .ant-table-row-cell-break-word span {
  padding-left: 0 !important;
}
.printTitle {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  p {
    width: 30%;
    margin: 0;
  }
}
.printBady {
  background: #eee;
  width: 330px;
  .printItem {
    font-size: 10px;
    width: 330px;
    .printItem3{
      width: 330px;
      height: 106px;
      display: flex;
    }
    .printItem_2 {
      width: 50%;
      height: 100%;
      margin-left: 20px;
      display: inline-block;
    }
    .printItem_1 {
      width: 50%;
      height: 100%;
      display: inline-block;
    }
    .printItem_title {
      font-size: 17px;
    }
    p {
      font-weight: 600;
      color: #000;
      margin: 0;
      line-height: 16px;
    }
  }
}
</style>