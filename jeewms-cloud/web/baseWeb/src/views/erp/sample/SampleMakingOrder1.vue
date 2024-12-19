<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="主PO号">
              <a-input placeholder="请输入主PO号" v-model="queryParam.query13"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="采购人">
              <j-search-select-tag
                type="list"
                v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'"
                :placeholder="$t('请选择')"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户">
              <j-popup
                v-model="queryParam.query24"
                field="query08"
                org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08"
                code="md_cus"
                :multi="false"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商编码">
              <a-input placeholder="请输入供应商编码" v-model="queryParam.query08"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商名称">
              <a-input placeholder="请输入供应商名称" v-model="queryParam.query09"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="打样单号">
              <a-input placeholder="请输入打样单号" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="销售单号">
              <a-input placeholder="请输入销售单号" v-model="queryParam.link03"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="内部发票号">
              <a-input placeholder="请输入内部发票号" v-model="queryParam.query17"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商">
              <j-popup
                v-model="queryParam.query08"
                field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch"
                dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup"
                :multi="false"
              />
            </a-form-item>
          </a-col> -->
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item label="建单日期">
              <j-date :placeholder="$t('请选择')" v-model="queryParam.query03" />
            </a-form-model-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder1:search'"
                >查询</a-button
              >
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
                v-has="'SampleMakingOrder1:reset'"
                >重置</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'SampleMakingOrder1:add'">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('打样合同')" v-has="'SampleMakingOrder1:export'"
        >导出</a-button
      >
      <a-button type="primary" icon="download" @click="handleExportDetailXls('导出详情')" v-has="'BusiPoList:export'">导出详情</a-button>
      <a-button
        type="primary"
        icon="download"
        @click="handleExportXls2('打样合同模板')"
        v-has="'SampleMakingOrder1:export'"
        >下载导入模板</a-button
      >
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import" v-has="'SampleMakingOrder1:import'">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'SampleMakingOrder1:deleteBatch'"
            ><a-icon type="delete" />删除</a-menu-item
          >
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{ x: true }"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            下载
          </a-button>
        </template>
        <span slot="money" slot-scope="text, record">
          {{text ? text : 0}}
        </span>
        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'SampleMakingOrder1:edit'">编辑</a>

          <a-divider type="vertical" v-has="'SampleMakingOrder1:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">导出合同<a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-show="record.query25 == '是'">
                <a @click="purchaseContract3(record, 'YPHS')">样品（含税）</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '是'">
                <a @click="PoContractPDF(record, 'YPHS')">样品（含税PDF）</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '否'">
                <a @click="purchaseContract4(record, 'YPBHS')">样品（不含税）</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '否'">
                <a @click="PoContractPDF(record, 'YPBHS')">样品（不含税PDF）</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="salesContract(record, 'FKD')">付款单</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="SalesContractPdf(record, 'FKD')">付款单PDF</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">生成单据<a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="createPurchaseOrder(record)">创建采购单</a>
              </a-menu-item>
<!--        <a-menu-item>
                <a @click="paymentSchedule(record)" v-has="'SampleMakingOrder1:paymentSchedule'">创建付款计划</a>
              </a-menu-item>
               <a-menu-item>
                  <a @click="collectionSchedule(record)">创建收款计划</a>
              </a-menu-item>  -->
            </a-menu>
          </a-dropdown>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)" v-has="'SampleMakingOrder1:detail'">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a v-has="'SampleMakingOrder1:delete'">删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <SalesContractPdf ref="SalesContractPdf" />
    <SampleMakingOrderModal ref="modalForm" @ok="modalFormOk" />
    <PoContract ref="PoContract" @ok="modalFormOk" />
    <busi-po-modal ref="modalForm1" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import SampleMakingOrderModal from './modules/SampleMakingOrderModal'
import '@/assets/less/TableExpand.less'
import { exportFile3 } from '@/utils/PLTN_EXCEL_OMS/purchaseContract3.js'
import { exportFile4 } from '@/utils/PLTN_EXCEL_OMS/purchaseContract4.js'
import PoContract from '@/views/erp/modules/PoContract'
import BusiPoModal from '../busipo/modules/BusiPoModal'
import { getAction, postFormAction, postAction, downFile } from '@/api/manage'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { SampleMakingOrder,SampleMakingOrder1,BusiPoDetailList } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import { exportSalesContract } from '@/utils/PLTN_EXCEL_OMS/salesContract.js'
import SalesContractPdf from '@/views/erp/modules/SalesContractPdf.vue'


export default {
  name: 'SampleMakingOrder',
  mixins: [JeecgListMixin],
  components: {
    SampleMakingOrderModal,
    PoContract,
    BusiPoModal,
    SalesContractPdf,
  },
  data() {
    return {
      description: '打样通知单页面',
      // 表头
      columns: [
        {
          title: '主PO',
          align: 'center',
          dataIndex: 'query13',
        },
        {
          title: '客户',
          align: 'center',
          dataIndex: 'query24_dictText',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'query02',
        },
        {
          title: '预计到货时间',
          align: 'center',
          dataIndex: 'query21',
        },
        {
          title: '业务类型',
          align: 'center',
          dataIndex: 'query31',
        },

        {
          title: '销售单号',
          align: 'center',
          dataIndex: 'link03',
        },
        {
          title: '内部发票号',
          align: 'center',
          dataIndex: 'query17',
        },

        // {
        //   title: '打样单号',
        //   align: 'center',
        //   dataIndex: 'query04',
        // },

        // {
        //   title: '供应商编码',
        //   align: 'center',
        //   dataIndex: 'query08',
        // },
        {
          title: '供应商名称',
          align: 'center',
          dataIndex: 'query09',
        },

        {
          title: '采购人',
          align: 'center',
          dataIndex: 'query16',
        },
        // {
        //   title: '含税总金额',
        //   align: 'center',
        //   dataIndex: 'num09',
        // },
        {
          title: '币种',
          align: 'center',
          dataIndex: 'query22',
        },
        {
          title: '税点',
          align: 'center',
          dataIndex: 'num10',
          customRender:  (text)=> {
              if (text) {
                return text+'%'
              } else {
                return 0
              }
            }
        },
        {
          title: '业务员',
          align: 'center',
          dataIndex: 'query30',
        },
        {
          title: '创建日期',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '可退样品费金额',
          align: 'center',
          dataIndex: 'num16',
          scopedSlots: { customRender: 'money' },
        },
        {
          title: '备注',
          align: 'center',
          dataIndex: 'text01',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/jeeerp/busiPo/list2',
        delete: '/jeeerp/busiPo/delete',
        deleteBatch: '/jeeerp/busiPo/deleteBatch',
        exportXlsUrl: '/jeeerp/busiPo/exportXls',
        importExcelUrl: 'jeeerp/Import/SampleMakingOrderImportExcel',
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'YP',
      },
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
      handleExportDetailXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '*' + myparam[mkey] + '*'
        }
      }
      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        var dat = []
        this.selectionRows.forEach(v=>{
          dat.push(v.query04)
        })
        param['query04'] = dat.join(',')
      }
      getAction('/jeeerp/busiPo/list', param).then((res) => {
        res.result.records.map(v=>{
          if (!v.num10) { v.num10 = 0}
        })
        ExportTemplate([], BusiPoDetailList, res.result.records, name)
      })
    },
    salesContract(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportSalesContract([], [], res.result, 'WMS 付款单')
        }
      })
    },
    SalesContractPdf() {
      this.$refs.SalesContractPdf.open()
    },
        SalesContractPdf(e, type) {
      this.$refs.SalesContractPdf.open(e, type)
    },
    collectionSchedule(record) {
      // 收款计划
      this.$router.push({ path: '/erp/sample/SampleCollectionPlan', query: { orderId: record.query04 } })
    },
    handleExportXls(name) {
      let myparam = {}
      Object.assign(myparam, this.queryParam)
      for (var mkey in myparam) {
        if (myparam[mkey] != null && myparam[mkey] != '' && myparam[mkey].indexOf('*') == -1) {
          myparam[mkey] = '' + myparam[mkey] + ''
        }
      }
      let param = { ...myparam }
      if (this.selectedRowKeys && this.selectedRowKeys.length > 0) {
        param['selections'] = this.selectedRowKeys.join(',')
      }
      getAction('/jeeerp/ExportTableData/ExportBusiPo', param).then((res) => {
        res.result.map(v=>{
          if (!v.num10) { v.num10 = 0}
          if (!v.num16) { v.num16 = 0}
        })
        ExportTemplate([], SampleMakingOrder1, res.result, name)
      })
    },
    handleExportXls2(fileName) {
      this.exportOk = true
      if (!fileName || typeof fileName != 'string') {
        fileName = '导出文件'
      }
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
      console.log('导出参数', param)
      try {
        downFile('/jeeerp/Import/SampleMakingOrderExportXls')
          .then((data) => {
            if (!data) {
              this.$message.warning('文件下载失败')
              this.exportOk = false
              return
            }
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
              window.navigator.msSaveBlob(new Blob([data], { type: 'application/vnd.ms-excel' }), fileName + '.xls')
            } else {
              let url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
              let link = document.createElement('a')
              link.style.display = 'none'
              link.href = url
              link.setAttribute('download', fileName + '.xls')
              document.body.appendChild(link)
              link.click()
              document.body.removeChild(link) //下载完成移除元素
              window.URL.revokeObjectURL(url) //释放掉blob对象
            }
            this.exportOk = false
            console.log(this.exportOk)
          })
          .finally(() => {
            this.exportOk = false
          })
      } catch (e) {
        this.exportOk = false
      }
    },
    createPurchaseOrder(e) {
      this.$refs.modalForm1.copyAdd(e.query04)
    },
    PoContractPDF(e, type) {
      this.$refs.PoContract.open(e, type)
    },
    purchaseContract3(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportFile3([], [], res.result, 'WMS PO 样品含税')
        }
      })
    },
    purchaseContract4(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportFile4([], [], res.result, 'WMS PO 样品不含税')
        }
      })
    },
    paymentSchedule(record) {
      this.$router.push({ path: '/erp/sample/SamplePlanList', query: { orderId: record.query04 } })
    },
    searchReset() {
      this.queryParam = { query01: 'YP' }
      this.loadData()
    },
    initDictConfig() {},
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createName', text: '创建人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'updateName', text: '更新人名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query01', text: '单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query02', text: '单据状态', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query03', text: '建单日期', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query04', text: '单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query05', text: '公司', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query06', text: '工厂', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query07', text: '库存地点', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query08', text: '供应商编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query09', text: '供应商名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query10', text: '商品编码', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query11', text: '商品名称', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query12', text: '单位', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query13', text: '主PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query14', text: '子PO', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query15', text: '检验类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query16', text: 'query16', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query17', text: 'query17', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query18', text: 'query18', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query19', text: 'query19', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query20', text: 'query20', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query21', text: '预计到货时间', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query22', text: 'query22', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query23', text: '单号-行项目号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query24', text: 'query24', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query25', text: 'query25', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query26', text: 'query26', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query27', text: 'query27', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query28', text: 'query28', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query29', text: 'query29', dictCode: '' })
      fieldList.push({ type: 'string', value: 'query30', text: 'query30', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num01', text: '数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num02', text: '未完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num03', text: '已完成数量', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num04', text: 'num04', dictCode: '' })
      fieldList.push({ type: 'double', value: 'num05', text: 'num05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link01', text: '关联单据类型', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link02', text: '关联单号', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link03', text: 'link03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link04', text: 'link04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'link05', text: 'link05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text01', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text02', text: '备注', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text03', text: 'text03', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text04', text: 'text04', dictCode: '' })
      fieldList.push({ type: 'string', value: 'text05', text: 'text05', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr1', text: '单据附件', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr2', text: 'attr2', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr3', text: 'attr3', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr4', text: 'attr4', dictCode: '' })
      fieldList.push({ type: 'string', value: 'attr5', text: 'attr5', dictCode: '' })
      this.superFieldList = fieldList
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>