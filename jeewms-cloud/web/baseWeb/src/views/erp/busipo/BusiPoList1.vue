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
              <j-search-select-tag type="list" v-model="queryParam.query16"
                dict="sys_user,realname,realname,del_flag = '0'" :placeholder="$t('请选择')" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="客户">
              <j-popup v-model="queryParam.query24" field="query08" org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                dest-fields="query09,query08" code="md_cus" :multi="false" />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="供应商">
              <j-popup v-model="queryParam.query08" field="supplyCode"
                org-fields="gys_bian_ma,gys_jian_cheng,zhong_wen_qch" dest-fields="supplyCode,supplyName,supplyNick"
                code="md_sup" :multi="false" />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="采购单号">
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
          <!-- <a-col  :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-model-item label="建单日期" >
              <j-date :placeholder="$t('请选择')"  v-model="queryParam.query03" />
            </a-form-model-item>
          </a-col> -->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" v-has="'SampleMakingOrder:search'">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px"
                v-has="'SampleMakingOrder:reset'">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiPoList1:add'">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('采购订单')" v-has="'BusiPoList1:export'">导出</a-button>
      <a-button type="primary" icon="download" @click="handleExportDetailXls('导出详情')" v-has="'BusiPoList1:export'">导出详情</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls2('采购订单模板')"
        v-has="'SampleMakingOrder:export'">下载导入模板</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
        @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPoList1:import'">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiPoList1:batchDelete'"><a-icon type="delete" />删除</a-menu-item>
          <a-menu-item key="2" @click="handleCopy(selectedRowKeys)"><a-icon type="copy" />复制</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
      <a-button @click="handleCopy(selectedRowKeys)" type="primary" icon="copy"
        v-if="selectedRowKeys.length == 1">复制</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table ref="table" size="middle" bordered rowKey="id" class="j-table-force-nowrap" :scroll="{ x: true }"
        :columns="columns" :dataSource="dataSource" :pagination="ipagination" :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }" @change="handleTableChange">
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic" />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            下载
          </a-button>
        </template>
        <template slot="previewImg" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="eye" size="small" @click="previewImg(text)">
            预览
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <!-- 单据未完成显示 -->
          <span v-if="record.query02 != '已完成'">
            <a @click="PoFinish(record)">完成</a>
            <a-divider type="vertical" />

            <a @click="handleEdit(record)" v-has="'BusiPoList1:edit'">编辑</a>
            <a-divider type="vertical" v-has="'BusiPoList1:edit'" />
          </span>
          <a-dropdown>
            <a class="ant-dropdown-link">导出<a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-show="record.query25 == '是'">
                <a @click="purchaseContract1(record, 'CGHS')">大货（含税）</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '是'">
                <a @click="PoContractPDF(record, 'CGHS')">大货（含税）PDF</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '否'">
                <a @click="purchaseContract2(record, 'CGBHS')">大货（不含税）</a>
              </a-menu-item>
              <a-menu-item v-show="record.query25 == '否'">
                <a @click="PoContractPDF(record, 'CGBHS')">大货（不含税）PDF</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="salesContract(record, 'FKD')">付款单</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="SalesContractPdf(record, 'FKD')">付款单PDF</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <!-- <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">收付款 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="paymentSchedule(record)" v-has="'BusiPoList1:paymentSchedule'">付款计划</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="collectionSchedule(record)">收款计划</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="paymentSchedule2(record)">形式发票</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">出入库 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="pushWMS(record)" v-has="'BusiPoList1:pushWMS'">推送入库</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="pushWMSOm(record)" v-has="'BusiPoList1:pushWMS'">推送出库</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiPoList1:detail'">
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiPoList1:delete'">
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
              <a-menu-item v-show="record.query25=='是'">
                <a @click="handleUpload2(record)">上传发票</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="QualityInspectionSchedule(record)">排班</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="exportList(record)">导出检验单</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="handleUpload(record)">质检凭证</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>

    <busi-po-modal ref="modalForm" @ok="modalFormOk" />
    <busi-po-push-modal ref="modalForm2" @ok="modalFormOk" />
    <uploadFile ref="uploadFile" @ok="modalFormOk" />
    <uploadFile2 ref="uploadFile2" @ok="modalFormOk" />
    <previewFile ref="previewFile" />
    <PoFinish ref="PoFinish" @ok="modalFormOk" />
    <!-- <BusiPosheetModal ref="BusiPosheetModal" @ok="modalFormOk" /> -->
    <SalesContractPdf ref="SalesContractPdf" />
    <updateTimeLimit2 ref="updateTimeLimit2" @ok="modalFormOk" />
    <PoContract ref="PoContract" @ok="modalFormOk" />
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import BusiPoModal from './modules/BusiPoModal'
// import BusiPosheetModal from './modules/BusiPosheet'
import BusiPoPushModal from './modules/BusiPoPushModal'
import updateTimeLimit2 from './modules/updateTimeLimit2'
import PoFinish from './modules/PoFinish'
import '@/assets/less/TableExpand.less'
import { getAction, httpAction, postAction, downFile } from '@/api/manage'
import uploadFile from '@/components/uploadFile/uploadFile.vue'
import uploadFile2 from '@/components/uploadFile/uploadFile2.vue'
import previewFile from '@/components/previewFile/previewFile.vue'
import { exportFile } from '@/utils/PLTN_EXCEL_OMS/purchaseContract1.js'
import { exportFile2 } from '@/utils/PLTN_EXCEL_OMS/purchaseContract2.js'
import { exportSalesContract } from '@/utils/PLTN_EXCEL_OMS/salesContract.js'
import SalesContractPdf from '@/views/erp/modules/SalesContractPdf.vue'
import PoContract from '@/views/erp/modules/PoContract'
import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
import { BusiPoList,BusiPoList1,BusiPoDetailList } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
import { INSPECTION_SHEET } from '@/utils/PLTN_EXCEL/index' // WMS导出检验单，包装领料单
import store from '@/store/'
export default {
  name: 'BusiPoList',
  mixins: [JeecgListMixin],
  components: {
    // BusiPosheetModal,
    BusiPoModal,
    BusiPoPushModal,
    PoContract,
    uploadFile,
    uploadFile2,
    PoFinish,
    previewFile,
    updateTimeLimit2,
    SalesContractPdf,
  },
  data() {
    return {
      description: 'busi_po管理页面',
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
          title: '状态',
          align: 'center',
          dataIndex: 'query02',
        },
        // {
        //   title: '建单日期',
        //   align: 'center',
        //   dataIndex: 'query03',
        // },
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
        // {
        //   title: '采购单号',
        //   align: 'center',
        //   dataIndex: 'query04',
        // },
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
          title: '仓库',
          align: "center",
          dataIndex: 'query07'
        },
        {
          title: '单备注',
          align: 'center',
          dataIndex: 'text01',
        },
        {
          title: '质检凭证',
          align: 'center',
          dataIndex: 'attr2',
          scopedSlots: { customRender: 'previewImg' },
        },
        {
          title: '发票',
          align: 'center',
          dataIndex: 'attr3',
          scopedSlots: { customRender: 'fileSlot' },
        },
        {
          title: '质检备注',
          align: 'center',
          dataIndex: 'text03',
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
        importExcelUrl: 'jeeerp/Import/SampleMakingOrderImportExcel2',
      },
      dictOptions: {},
      superFieldList: [],
      queryParam: {
        query01: 'CGD',
        createBy: store.getters.userInfo.username
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
    exportList(e) {
      let params = { orderids: e.query04, type: 'CGZJ' }
      getAction('/jeeerp/busiPo/orderExcelOrPdf', params).then((res) => {
        INSPECTION_SHEET(res.result)
        console.log(res.result.records, '-=-=-=');
        // res.result.records
      })
    },
    QualityInspectionSchedule(e) {
      this.$refs.updateTimeLimit2.open(e)
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
        downFile("/jeeerp/Import/SampleMakingOrderExportXls")
          .then(data => {
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
        })
        ExportTemplate([], BusiPoList1, res.result, name)
      })
    },
    collectionSchedule(record) {
      // 收款计划
      this.$router.push({ path: '/erp/busiom/BusiOmPlan', query: { orderId: record.query04 } })
    },
    SalesContractPdf() {
      this.$refs.SalesContractPdf.open()
    },
    PoContractPDF(e, type) {
      this.$refs.PoContract.open(e, type)
    },
    SalesContractPdf(e, type) {
      this.$refs.SalesContractPdf.open(e, type)
    },
    PoFinish(e) {
      this.$refs.PoFinish.open(e)
    },
    purchaseContract1(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportFile([], [], res.result, 'WMS PO 大货含税')
        }
      })
    },
    purchaseContract2(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportFile2([], [], res.result, 'WMS PO 大货不含税')
        }
      })
    },
    salesContract(e, type) {
      getAction('/jeeerp/busiPo/orderExcelOrPdf', { orderids: e.query04, type: type }).then((res) => {
        if (res.success) {
          exportSalesContract([], [], res.result, 'WMS 付款单')
        }
      })
    },
    previewImg(text) {
      // props = 'https://t7.baidu.com/it/u=1819248061,230866778&fm=193&f=GIF,https://t7.baidu.com/it/u=1297102096,3476971300&fm=193&f=GIF'
      // this.$refs.previewFile.open(props)
      this.$refs.previewFile.open(text)
    },
    handleUpload(record) {
      this.$refs.uploadFile.open(record)
    },
    handleUpload2(record) {
      this.$refs.uploadFile2.open(record)
    },
    handleCopy(ids) {
      let orderId = ''
      this.dataSource.map((item, index) => {
        if (item.id == ids[0]) {
          orderId = item.query04
        }
      })
      this.$refs.modalForm.copyAdd(orderId)
    },
    searchReset() {
      // this.queryParam = { query01: 'CGD',createBy: this.store.getters.userInfo.username }
      this.queryParam = { query01: 'CGD'}
      this.loadData()
    },
    pushWMS(record) {
      let pushData = []
      let params = { query04: record.query04, pageNo: 1, pageSize: 1000, query01: 'CGD' }
      getAction('/jeeerp/busiPo/list', params).then((res) => {
        if (res.success) {
          pushData = res.result.records
          this.$refs.modalForm2.open(pushData)
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    paymentSchedule(record) {
      // 付款计划
      this.$router.push({ path: '/erp/busipo/BusiPoPlan', query: { orderId: record.query04 } })
    },
    paymentSchedule2(record) {
      // 形式发票
      this.$router.push({ path: '/erp/busiom/BusiOmProInvoice', query: { orderId: record.query04 } })
    },
    pushWMSOm(record) {
      // 推送WMS
      this.$router.push({ path: '/erp/busiom/BusiOmOrder', query: { orderId: record.query04 } })
    },
    popupCallback3(value, row) { },
    initDictConfig() { },
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