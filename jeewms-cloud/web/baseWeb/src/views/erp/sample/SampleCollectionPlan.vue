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
            <a-form-item label="客户">
               <j-popup
                  v-model="queryParam.query08"
                  field="query08"
                  org-fields="ke_hu_jian_cheng,ke_hu_bian_ma"
                  dest-fields="query09,query08"
                  code="md_cus"
                  :multi="false"
                />
            </a-form-item>
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="计划收款单号">
              <a-input placeholder="请输入单号" v-model="queryParam.query04"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="采购单号">
              <a-input placeholder="请输入采购单号" v-model="queryParam.link02"></a-input>
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
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="状态">
              <a-input placeholder="请输入状态" v-model="queryParam.query02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="PAYMENT LIST">
              <a-input placeholder="请输入PAYMENT LIST" v-model="queryParam.query19"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="币种">
              <j-dict-select-tag  v-model="queryParam.query22" dictCode="currency"/>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="金额">
              <a-input placeholder="请输入金额" v-model="queryParam.query09"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search" >查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" >重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus" v-has="'BusiOmPlan:add'" >新增</a-button> -->
      <a-button @click="handleAdd2" type="primary" icon="plus" v-has="'BusiOmPlan:add'" >创建收款计划</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('收款计划')" v-has="'BusiOmPlan:export'" >导出</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiOmPlan:import'" >导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel" v-has="'BusiOmPlan:batchDelete'" ><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)" v-has="'BusiOmPlan:edit'" >编辑</a>

          <a-divider type="vertical" v-has="'BusiOmPlan:edit'" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiOmPlan:detail'" >
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item v-has="'BusiOmPlan:delete'" >
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <!-- <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">导出单据 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="proformaInvoice(record,'XSFP')">形式发票</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="proformaInvoiceTotal(record,'XSFPHZ')">形式发票汇总</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
          <a-divider type="vertical" />
           <a-dropdown>
            <a class="ant-dropdown-link">导出PDF <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="ProformaInvoicePdf(record,'XSFP')">形式发票</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="ProformaInvoiceTotalPdf(record,'XSFPHZ')">形式发票（汇总）</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
        </span>

      </a-table>
    </div>
    <!-- <busi-po-plan-modal ref="modalForms" @ok="modalFormOk" /> -->
    <busi-om-plan-modal ref="modalForm" @ok="modalFormOk"/>
    <ProformaInvoice ref="ProformaInvoice" @ok="modalFormOk"/>
    <ProformaInvoiceTotal ref="ProformaInvoiceTotal" @ok="modalFormOk" />
    <addSampleCollectionPlan ref="addSampleCollectionPlan" @ok="modalFormOk" />
  </a-card>
</template>

<script>
  import { getAction } from '@/api/manage'
  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BusiOmPlanModal from './modules/BusiOmPlanModal'
//   import BusiPoPlanModal from './modules/BusiPoPlanModal'
  import ProformaInvoice from '@/views/erp/modules/ProformaInvoice.vue'
  import ProformaInvoiceTotal from '@/views/erp/modules/ProformaInvoiceTotal.vue'
  import { exportProformaInvoice } from '@/utils/PLTN_EXCEL_OMS/proformaInvoice.js'
  import { exportProformaInvoiceTotal } from '@/utils/PLTN_EXCEL_OMS/proformaInvoiceTotal.js'
  import addSampleCollectionPlan from './modules/addSampleCollectionPlan'

  import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
  import { BusiOmPlan } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
  export default {
    name: 'BusiOmPlan',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
    //   BusiPoPlanModal,
      BusiOmPlanModal,
      ProformaInvoice,
      ProformaInvoiceTotal,
      addSampleCollectionPlan
    },
    data () {
      return {
        description: '收款计划管理页面',
        // 表头
        columns: [
          {
            title:'PAYMENT LIST',
            align:"center",
            dataIndex: 'query19'
          },
          {
            title:'主PO',
            align:"center",
            dataIndex: 'query13'
          },
          {
            title:'客户',
            align:"center",
            dataIndex: 'query24_dictText'
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'query02'
          },
          {
            title:'销售单号',
            align:"center",
            dataIndex: 'link03'
          },
          {
            title:'内部发票号',
            align:"center",
            dataIndex: 'query17'
          },
          {
            title:'采购人',
            align:"center",
            dataIndex: 'query16',
          },
          {
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime',
          },
          {
            title:'金额',
            align:"center",
            dataIndex: 'num09',
          },
          {
            title:'币种',
            align:"center",
            dataIndex: 'query22',
          },
          {
            title:'创建人',
            align:"center",
            dataIndex: 'createName',
          },
          // {
          //   title:'单号',
          //   align:"center",
          //   dataIndex: 'query04'
          // },
          // {
          //   title:'供应商编码',
          //   align:"center",
          //   dataIndex: 'query08'
          // },
          // {
          //   title:'供应商名称',
          //   align:"center",
          //   dataIndex: 'query09'
          // },
          // {
          //   title:'采购单号',
          //   align:"center",
          //   dataIndex: 'link02'
          // },
          {
            title:'备注',
            align:"center",
            dataIndex: 'text01'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/jeeerp/busiPaymentReceived/list2",
          delete: "/jeeerp/busiPaymentReceived/delete",
          deleteBatch: "/jeeerp/busiPaymentReceived/deleteBatch",
          exportXlsUrl: "/jeeerp/busiPaymentReceived/exportXls",
          importExcelUrl: "jeeerp/busiPaymentReceived/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'YPSKJH'
        }
      }
    },
    created() {
    this.getSuperFieldList();
    },
    mounted() {
      if(this.$route.query.orderId) {
        this.$refs.modalForm.add2(this.$route.query.orderId)
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      handleAdd2(){
        this.$refs.addSampleCollectionPlan.add()
      },
      searchReset() {
        this.queryParam = {query01: "YPSKJH"}
        this.loadData()
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
          ExportTemplate([],BusiOmPlan,res.result,name)
        })
      },
      proformaInvoice() {
        exportProformaInvoice([], [], [], 'WMS 形式发票')
      },
      proformaInvoiceTotal(e,type) {
        getAction('/jeeerp/busiPo/orderExcelOrPdf',{orderids:e.query04,type:type}).then(res => {
          exportProformaInvoiceTotal([],[],res.result,'WMS 形式发票汇总')
        })
      },
      ProformaInvoiceTotalPdf(e,type) {
        this.$refs.ProformaInvoiceTotal.open(e,type)
      },
      ProformaInvoicePdf(e) {
         this.$refs.ProformaInvoice.open(e)
      },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'createName',text:'创建人名称'})
        fieldList.push({type:'string',value:'updateName',text:'更新人名称'})
        fieldList.push({type:'string',value:'query01',text:'单据类型'})
        fieldList.push({type:'string',value:'query02',text:'单据状态'})
        fieldList.push({type:'date',value:'query03',text:'建单日期'})
        fieldList.push({type:'string',value:'query04',text:'单号'})
        fieldList.push({type:'string',value:'query05',text:'公司'})
        fieldList.push({type:'string',value:'query06',text:'工厂'})
        fieldList.push({type:'string',value:'query07',text:'库存地点'})
        fieldList.push({type:'string',value:'query08',text:'对象编码'})
        fieldList.push({type:'string',value:'query09',text:'对象名称'})
        fieldList.push({type:'string',value:'query10',text:'商品编码'})
        fieldList.push({type:'string',value:'query11',text:'商品名称'})
        fieldList.push({type:'string',value:'query12',text:'单位'})
        fieldList.push({type:'string',value:'query13',text:'主PO'})
        fieldList.push({type:'string',value:'query14',text:'子PO'})
        fieldList.push({type:'string',value:'query15',text:'query15'})
        fieldList.push({type:'string',value:'query16',text:'query16'})
        fieldList.push({type:'string',value:'query17',text:'query17'})
        fieldList.push({type:'string',value:'query18',text:'query18'})
        fieldList.push({type:'string',value:'query19',text:'query19'})
        fieldList.push({type:'string',value:'query20',text:'query20'})
        fieldList.push({type:'string',value:'query21',text:'query21'})
        fieldList.push({type:'string',value:'query22',text:'query22'})
        fieldList.push({type:'string',value:'query23',text:'query23'})
        fieldList.push({type:'string',value:'query24',text:'query24'})
        fieldList.push({type:'string',value:'query25',text:'query25'})
        fieldList.push({type:'string',value:'query26',text:'query26'})
        fieldList.push({type:'string',value:'query27',text:'query27'})
        fieldList.push({type:'string',value:'query28',text:'query28'})
        fieldList.push({type:'string',value:'query29',text:'query29'})
        fieldList.push({type:'string',value:'query30',text:'query30'})
        fieldList.push({type:'number',value:'num01',text:'数量'})
        fieldList.push({type:'number',value:'num02',text:'未完成数量'})
        fieldList.push({type:'number',value:'num03',text:'已完成数量'})
        fieldList.push({type:'number',value:'num04',text:'num04'})
        fieldList.push({type:'number',value:'num05',text:'num05'})
        fieldList.push({type:'string',value:'link01',text:'关联单据类型'})
        fieldList.push({type:'string',value:'link02',text:'关联单号'})
        fieldList.push({type:'string',value:'link03',text:'link03'})
        fieldList.push({type:'string',value:'link04',text:'link04'})
        fieldList.push({type:'string',value:'link05',text:'link05'})
        fieldList.push({type:'string',value:'text01',text:'备注'})
        fieldList.push({type:'string',value:'text02',text:'备注'})
        fieldList.push({type:'string',value:'text03',text:'text03'})
        fieldList.push({type:'string',value:'text04',text:'text04'})
        fieldList.push({type:'string',value:'text05',text:'text05'})
        fieldList.push({type:'string',value:'attr1',text:'单据附件'})
        fieldList.push({type:'string',value:'attr2',text:'attr2'})
        fieldList.push({type:'string',value:'attr3',text:'attr3'})
        fieldList.push({type:'string',value:'attr4',text:'attr4'})
        fieldList.push({type:'string',value:'attr5',text:'attr5'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>