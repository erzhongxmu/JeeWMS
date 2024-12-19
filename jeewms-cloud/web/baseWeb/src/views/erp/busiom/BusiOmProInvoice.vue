<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('PAYMENT LIST') ">
              <a-input :placeholder="$t('请输入PAYMENT LIST') " v-model="queryParam.query19"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('主PO号')">
              <a-input :placeholder="$t('请输入主PO号')" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购人')">
              <a-input placeholder="请输入" v-model="queryParam.query16"></a-input>
              <!-- <j-search-select-tag type="list" v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'" :placeholder="$t('请选择')" /> -->
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('客户')">
              <j-popup v-model="queryParam.query24" field="query08" org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08" code="md_cus" :multi="false" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('供应商')">
              <j-popup v-model="queryParam.query08" field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch" dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup" :multi="false" />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('发票单号')">
              <a-input :placeholder="$t('请输入发票单号')" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col> -->
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('采购单号')">
              <a-input :placeholder="$t('请输入采购单号')" v-model="queryParam.link02"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('销售单号')">
              <a-input :placeholder="$t('请输入销售单号')" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('内部发票号')">
              <a-input :placeholder="$t('请输入内部发票号')" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('备注')">
              <a-input :placeholder="$t('请输入备注')" v-model="queryParam.text01"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col  :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item :label="$t('建单日期')" >
              <j-date :placeholder="$t('请选择')"  v-model="queryParam.query03" />
            </a-form-model-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'">{{$t('查询')}}</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px"
                v-has="'SampleMakingOrder:reset'">{{$t('重置')}}</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd2" type="primary" icon="plus" v-has="'BusiOmPlan:add'" >{{$t('创建发票')}}</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('形式发票')" v-has="'BusiOmPlan:export'">{{$t('导出')}}</a-button>
      <a-button type="primary" icon="download" @click="proformaInvoiceTotal('XSFPHZ')"
        v-has="'BusiOmPlan:export'">{{$t('形式发票（汇总）')}}</a-button>
      <a-button type="primary" icon="download" @click="ProformaInvoiceTotalPdf('XSFPHZ')"
        v-has="'BusiOmPlan:export'">{{$t('形式发票（汇总）PDF')}}</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiOmPlan:import'" >导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiOmPlan:batchDelete'"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}} <a style="font-weight: 600">{{
          selectedRowKeys.length }}</a>{{$t('项目')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-table ref="table" size="middle" :scroll="{ x: true }" bordered rowKey="id" :columns="columns"
        :dataSource="dataSource" :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
            style="max-width:80px;font-size: 12px;font-style: italic;" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            {{$t('下载')}}
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit2(record)" v-has="'BusiOmPlan:edit'">{{$t('修改状态')}}</a>
          <a-divider type="vertical" v-has="'BusiOmPlan:edit'" />

          <a @click="handleEdit(record)" v-has="'BusiOmPlan:edit'">{{$t('编辑')}}</a>

          <a-divider type="vertical" v-has="'BusiOmPlan:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('更多')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiOmPlan:detail'">
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiOmPlan:delete'">
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">{{$t('导出单据')}} <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="proformaInvoice(record, 'XSFP')">{{$t('形式发票')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="ProformaInvoicePdf(record, 'XSFP')">{{$t('形式发票PDF')}}</a>
              </a-menu-item>
              <!-- <a-menu-item>
                <a @click="proformaInvoiceTotal(record,'XSFPHZ')">形式发票汇总</a>
              </a-menu-item> -->
            </a-menu>
          </a-dropdown>
          <!-- <a-divider type="vertical" />
           <a-dropdown>
            <a class="ant-dropdown-link">导出PDF <a-icon type="down" /></a>
            <a-menu slot="overlay">
              
              <a-menu-item>
                <a @click="ProformaInvoiceTotalPdf(record,'XSFPHZ')">形式发票（汇总）</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
        </span>

      </a-table>
    </div>

    <BusiOmInvoiceModal ref="modalForm" @ok="modalFormOk" />
    <ProformaInvoice ref="ProformaInvoice" @ok="modalFormOk" />
    <ProformaInvoiceTotal ref="ProformaInvoiceTotal" @ok="modalFormOk" />
    <addProformaInvoice ref="addProformaInvoice" @ok="modalFormOk" />
    <j-modal :title="$t('状态修改')" :width="300" :visible="visible" switchFullscreen @ok="handleOk" @cancel="handleCancel">
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="24" :lg="7" :md="8" :sm="24">
              <a-form-item :label="$t('状态')">
                <j-dict-select-tag v-model="EditQuery02.query02" dictCode="order_state" />
              </a-form-item>
            </a-col>
          </a-row>
        </a-form>
      </div>
    </j-modal>
  </a-card>
</template>

<script>
import { getAction, putAction } from '@/api/manage'
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiOmInvoiceModal from './modules/BusiOmInvoiceModal'
import addProformaInvoice from './modules/addProformaInvoice'
import ProformaInvoice from '@/views/erp/modules/ProformaInvoice.vue'
import ProformaInvoiceTotal from '@/views/erp/modules/ProformaInvoiceTotal.vue'
import { exportProformaInvoice } from '@/utils/PLTN_EXCEL_OMS/proformaInvoice.js'
import { exportProformaInvoiceTotal } from '@/utils/PLTN_EXCEL_OMS/proformaInvoiceTotal.js'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { BusiOmProInvoice } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import { OUT_PUT} from '@/utils/util'

export default {
  name: 'BusiOmPlan',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    BusiOmInvoiceModal,
    ProformaInvoice,
    ProformaInvoiceTotal,
    addProformaInvoice
  },
  data() {
    return {
      EditQuery02: '',
      visible: false,
      description: '收款计划管理页面',
      // 表头
      columns: [
        {
          title: this.$t('PAYMENT LIST'),
          align: 'center',
          dataIndex: 'query19',
        },
        {
          title: this.$t('主PO'),
          align: 'center',
          dataIndex: 'query13',
        },
        {
          title: this.$t('子PO'),
          align: "center",
          dataIndex: 'query14'
          , sorter: true,
        },
        {
          title: this.$t('客户'),
          align: 'center',
          dataIndex: 'query24_dictText',
        },
        {
          title: this.$t('公司属性'),
          align: 'center',
            sorter: true,
          dataIndex: 'query30',
        },
        {
          title: this.$t('状态'),
          align: 'center',
          dataIndex: 'query02',
          customRender: (text) => {
            return text?this.$t(text):text
          }
        },
        {
          title: this.$t('销售单号'),
          align: 'center',
          dataIndex: 'link03',
        },
        {
          title: this.$t('内部发票号'),
          align: 'center',
          dataIndex: 'query17',
        },
        {
          title: this.$t('采购人'),
          align: 'center',
          dataIndex: 'query16',
        },
        {
          title: this.$t('创建日期'),
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: this.$t('税点'),
          align: 'center',
            sorter: true,
          dataIndex: 'num10',
          customRender:  (text)=> {
              if (text) {
                return text + '%'
              } else {
                return 0
              }
            }
        },
        {
          title: this.$t('金额'),
          align: 'center',
          dataIndex: 'num09',
          customRender: (text) => {
            return text?OUT_PUT(text):text
          }
        },
        {
          title: this.$t('币种'),
          align: "center",
          dataIndex: 'query22',
        },
        {
          title: this.$t('创建人'),
          align: "center",
          dataIndex: 'createName',
        },
        {
          title: this.$t('发票单号'),
          align: 'center',
          dataIndex: 'query04',
        },
        // {
        //   title: this.$t('采购单号'),
        //   align: 'center',
        //   dataIndex: 'link02',
        // },
        {
          title: this.$t('备注'),
          align: "center",
          dataIndex: 'text01'
        },
        // {
        //   title: this.$t('供应商编码'),
        //   align: 'center',
        //   dataIndex: 'query08',
        // },
        // {
        //   title: this.$t('供应商名称'),
        //   align: 'center',
        //   dataIndex: 'query09',
        // },
        // {
        //   title: this.$t('单备注'),
        //   align: 'center',
        //   dataIndex: 'text01',
        // },
        {
          title: this.$t('操作'),
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: "/jeeerp/busiPaymentReceived/list2",
        delete: "/jeeerp/busiPaymentReceived/delete",
        deleteBatch: "/jeeerp/busiPaymentReceived/deleteBatch",
        exportXlsUrl: "/jeeerp/busiPaymentReceived/exportXls",
        importExcelUrl: "jeeerp/busiPaymentReceived/importExcel",

      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'XSFP'
      }
    }
  },
  created() {
    this.getSuperFieldList();
  },
  mounted() {
    if (this.$route.query.orderId) {
      this.$refs.modalForm.add2(this.$route.query.orderId)
    }
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    handleAdd2(){
      this.$refs.addProformaInvoice.add()
    },
    handleEdit2(e) {
      this.EditQuery02 = e
      this.visible = true
    },
    handleOk() {
      if(!this.EditQuery02.query02) return this.$message.warning(this.$t('请选择状态'))
      putAction('/jeeerp/busiPaymentReceived/EditQuery02', this.EditQuery02).then((res) => {
        if (res.success) {
          this.loadData()
          this.handleCancel()
          this.$message.success(this.$t(res.message))
        } else {
          this.$message.warning(this.$t(res.message))
        }
      })
    },
    handleCancel() {
      this.EditQuery02 = {}
      this.visible = false
    },
    handleExportXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '*' + myparam[mkey] + '*'
        }
      }
      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['selections'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/ExportTableData/ExportBusiPaymentReceived', param).then((res) => {
        ExportTemplate([], BusiOmProInvoice, res.result, name)
      })
    },
    searchReset() {
      this.queryParam = { query01: "XSFP" }
      this.loadData()
    },
    proformaInvoice(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then(res => {
        let data = res.result
        data.map((item, index) => {
          exportProformaInvoice([], [], item, 'WMS 形式发票')
        })
      })
    },
    proformaInvoiceTotal(type) {
      if (this.selectionRows.length == 0) return this.$message.warning(this.$t('请选择发票'))
      let arr = []
      this.selectionRows.map(item => {
        arr.push(item.query04)
      })
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: arr.join(','), type: type }).then(res => {
        exportProformaInvoiceTotal([], [], res.result, 'WMS 形式发票汇总')
      })
    },
    ProformaInvoiceTotalPdf(type) {
      if (this.selectionRows.length == 0) return this.$message.warning(this.$t('请选择发票'))
      let arr = []
      this.selectionRows.map(item => {
        arr.push(item.query04)
      })
      this.$refs.ProformaInvoiceTotal.open(arr.join(','), type)
    },
    ProformaInvoicePdf(e, type) {
      this.$refs.ProformaInvoice.open(e, type)
    },
    initDictConfig() {
    },
    getSuperFieldList() {
      let fieldList = [];
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态' })
      fieldList.push({ type: 'date', value: 'query03', text: '建单日期' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点' })
      fieldList.push({ type: 'string', value: 'query08', text: '对象编码' })
      fieldList.push({ type: 'string', value: 'query09', text: '对象名称' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO' })
      fieldList.push({ type: 'string', value: 'query15', text: 'query15' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20' })
      fieldList.push({ type: 'string', value: 'query21', text: 'query21' })
      fieldList.push({ type: 'string', value: 'query22', text: 'query22' })
      fieldList.push({ type: 'string', value: 'query23', text: 'query23' })
      fieldList.push({ type: 'string', value: 'query24', text: 'query24' })
      fieldList.push({ type: 'string', value: 'query25', text: 'query25' })
      fieldList.push({ type: 'string', value: 'query26', text: 'query26' })
      fieldList.push({ type: 'string', value: 'query27', text: 'query27' })
      fieldList.push({ type: 'string', value: 'query28', text: 'query28' })
      fieldList.push({ type: 'string', value: 'query29', text: 'query29' })
      fieldList.push({ type: 'string', value: 'query30', text: 'query30' })
      fieldList.push({ type: 'number', value: 'num01', text: '数量' })
      fieldList.push({ type: 'number', value: 'num02', text: '未完成数量' })
      fieldList.push({ type: 'number', value: 'num03', text: '已完成数量' })
      fieldList.push({ type: 'number', value: 'num04', text: 'num04' })
      fieldList.push({ type: 'number', value: 'num05', text: 'num05' })
      fieldList.push({ type: 'string', value: 'link01', text: '关联单据类型' })
      fieldList.push({ type: 'string', value: 'link02', text: '关联单号' })
      fieldList.push({ type: 'string', value: 'link03', text: 'link03' })
      fieldList.push({ type: 'string', value: 'link04', text: 'link04' })
      fieldList.push({ type: 'string', value: 'link05', text: 'link05' })
      fieldList.push({ type: 'string', value: 'text01', text: '备注' })
      fieldList.push({ type: 'string', value: 'text02', text: '备注' })
      fieldList.push({ type: 'string', value: 'text03', text: 'text03' })
      fieldList.push({ type: 'string', value: 'text04', text: 'text04' })
      fieldList.push({ type: 'string', value: 'text05', text: 'text05' })
      fieldList.push({ type: 'string', value: 'attr1', text: '单据附件' })
      fieldList.push({ type: 'string', value: 'attr2', text: 'attr2' })
      fieldList.push({ type: 'string', value: 'attr3', text: 'attr3' })
      fieldList.push({ type: 'string', value: 'attr4', text: 'attr4' })
      fieldList.push({ type: 'string', value: 'attr5', text: 'attr5' })
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>