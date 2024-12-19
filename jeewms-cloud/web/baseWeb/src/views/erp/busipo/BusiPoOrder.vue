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
          </a-col>
          <!-- <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="采购单号">
              <a-input placeholder="请输入打样单号" v-model="queryParam.link02"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="入库单号">
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
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px" v-has="'SampleMakingOrder:reset'">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('入库预约')" v-has="'BusiPoOrder:export'">导出</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import" v-has="'BusiPoOrder:import'">导入</a-button>
      </a-upload> -->
      <!-- 高级查询区域 -->
      <!-- <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query> -->
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete" v-has="'BusiPoOrder:batchDelete'"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
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
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :scroll="{x:true}"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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
          <a @click="handleEdit(record)" v-has="'BusiPoOrder:edit'">编辑</a>

          <a-divider type="vertical" v-has="'BusiPoOrder:edit'" />

          <a-popconfirm title="确定推送吗?" @confirm="() => pushWMS(record)">
            <a v-if="record.query02 == '未推送'">推送WMS</a>
          </a-popconfirm>
          <a-divider type="vertical" v-if="record.query02 == '未推送'" />

          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item v-has="'BusiPoOrder:detail'">
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <busi-po-order-modal ref="modalForm" @ok="modalFormOk"/>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import BusiPoOrderModal from './modules/BusiPoOrderModal'
  import '@/assets/less/TableExpand.less'
  import { getAction, httpAction, postAction, downFile } from '@/api/manage'
  import { ExportTemplate } from '@/utils/PLTN_EXCEL_OMS/ExportTemplate.js'
  import { BusiPoOrder } from '@/utils/PLTN_EXCEL_OMS/ExportHeader.js'
  import store from '@/store/'
  export default {
    name: "BusiPoOrder",
    mixins:[JeecgListMixin],
    components: {
      BusiPoOrderModal
    },
    data () {
      return {
        description: '入库预约页面',
        // 表头
        columns: [
         {
          title: '主PO',
          align: 'center',
          sorter: true,
          dataIndex: 'query13',
        },
        {
          title: '客户',
          align: 'center',
          sorter: true,
          dataIndex: 'query24_dictText',
        },
        {
          title: '状态',
          align: 'center',
          sorter: true,
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
          sorter: true,
          dataIndex: 'query21',
        },
        // {
        //   title: '入库单号',
        //   align: 'center',
        //   dataIndex: 'query04',
        // },
        // {
        //   title: '采购单号',
        //   align: 'center',
        //   dataIndex: 'link02',
        // },
        {
          title: '销售单号',
          align: 'center',
          sorter: true,
          dataIndex: 'link03',
        },
        {
          title: '内部发票号',
          align: 'center',
          sorter: true,
          dataIndex: 'query17',
        },
        {
          title: '采购人',
          align: 'center',
          sorter: true,
          dataIndex: 'query16',
        },
        {
          title: '创建日期',
          align: 'center',
          sorter: true,
          dataIndex: 'createTime',
        },
        {
          title:'仓库',
          align:"center",
          dataIndex: 'query07'
        },
        {
          title: '单备注',
          align: 'center',
          sorter: true,
          dataIndex: 'text01',
        },
        // {
        //   title: '质检凭证',
        //   align: 'center',
        //   dataIndex: 'attr2',
        //   scopedSlots: { customRender: 'previewImg' },
        // },
        // {
        //   title: '质检备注',
        //   align: 'center',
        //   dataIndex: 'text03',
        // },
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
          list: "/jeeerp/busiPo/BusiPoItemList",
          delete: "/jeeerp/busiPo/delete",
          deleteBatch: "/jeeerp/busiPo/deleteBatchItem",
          exportXlsUrl: "/jeeerp/busiPo/exportXls",
          importExcelUrl: "jeeerp/busiPo/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
        queryParam:{
          query01:'RKYY',
          createBy: store.getters.userInfo.username
        }
      }
    },
    created() {
      this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      pushWMS(e) {
        getAction('/jeeerp/busiPo/samplePoPushWms', { ids: e.id }).then(res => {
          if (res.success) {
            this.$message.success(res.message)
            this.loadData()
          } else {
            this.$message.warning(res.message)
          }
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
        getAction('/jeeerp/ExportTableData/ExportBusiPoItem', param).then((res) => {
          ExportTemplate([],BusiPoOrder,res.result,name)
        })
      },
      searchReset() {
        this.queryParam = {query01: "RKYY",createBy: this.store.getters.userInfo.username }
        this.loadData()
      },
      popupCallback1() {},
      popupCallback2() {},
      popupCallback3() {},
      popupCallback4() {},
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
         fieldList.push({type:'string',value:'createName',text:'创建人名称',dictCode:''})
         fieldList.push({type:'string',value:'updateName',text:'更新人名称',dictCode:''})
         fieldList.push({type:'string',value:'query01',text:'单据类型',dictCode:''})
         fieldList.push({type:'string',value:'query02',text:'单据状态',dictCode:''})
         fieldList.push({type:'string',value:'query03',text:'建单日期',dictCode:''})
         fieldList.push({type:'string',value:'query04',text:'单号',dictCode:''})
         fieldList.push({type:'string',value:'query05',text:'公司',dictCode:''})
         fieldList.push({type:'string',value:'query06',text:'工厂',dictCode:''})
         fieldList.push({type:'string',value:'query07',text:'库存地点',dictCode:''})
         fieldList.push({type:'string',value:'query08',text:'供应商编码',dictCode:''})
         fieldList.push({type:'string',value:'query09',text:'供应商名称',dictCode:''})
         fieldList.push({type:'string',value:'query10',text:'商品编码',dictCode:''})
         fieldList.push({type:'string',value:'query11',text:'商品名称',dictCode:''})
         fieldList.push({type:'string',value:'query12',text:'单位',dictCode:''})
         fieldList.push({type:'string',value:'query13',text:'主PO',dictCode:''})
         fieldList.push({type:'string',value:'query14',text:'子PO',dictCode:''})
         fieldList.push({type:'string',value:'query15',text:'检验类型',dictCode:''})
         fieldList.push({type:'string',value:'query16',text:'query16',dictCode:''})
         fieldList.push({type:'string',value:'query17',text:'query17',dictCode:''})
         fieldList.push({type:'string',value:'query18',text:'query18',dictCode:''})
         fieldList.push({type:'string',value:'query19',text:'query19',dictCode:''})
         fieldList.push({type:'string',value:'query20',text:'query20',dictCode:''})
         fieldList.push({type:'string',value:'query21',text:'预计到货时间',dictCode:''})
         fieldList.push({type:'string',value:'query22',text:'query22',dictCode:''})
         fieldList.push({type:'string',value:'query23',text:'单号-行项目号',dictCode:''})
         fieldList.push({type:'string',value:'query24',text:'query24',dictCode:''})
         fieldList.push({type:'string',value:'query25',text:'query25',dictCode:''})
         fieldList.push({type:'string',value:'query26',text:'query26',dictCode:''})
         fieldList.push({type:'string',value:'query27',text:'query27',dictCode:''})
         fieldList.push({type:'string',value:'query28',text:'query28',dictCode:''})
         fieldList.push({type:'string',value:'query29',text:'query29',dictCode:''})
         fieldList.push({type:'string',value:'query30',text:'query30',dictCode:''})
         fieldList.push({type:'double',value:'num01',text:'数量',dictCode:''})
         fieldList.push({type:'double',value:'num02',text:'未完成数量',dictCode:''})
         fieldList.push({type:'double',value:'num03',text:'已完成数量',dictCode:''})
         fieldList.push({type:'double',value:'num04',text:'num04',dictCode:''})
         fieldList.push({type:'double',value:'num05',text:'num05',dictCode:''})
         fieldList.push({type:'string',value:'link01',text:'关联单据类型',dictCode:''})
         fieldList.push({type:'string',value:'link02',text:'关联单号',dictCode:''})
         fieldList.push({type:'string',value:'link03',text:'link03',dictCode:''})
         fieldList.push({type:'string',value:'link04',text:'link04',dictCode:''})
         fieldList.push({type:'string',value:'link05',text:'link05',dictCode:''})
         fieldList.push({type:'string',value:'text01',text:'备注',dictCode:''})
         fieldList.push({type:'string',value:'text02',text:'备注',dictCode:''})
         fieldList.push({type:'string',value:'text03',text:'text03',dictCode:''})
         fieldList.push({type:'string',value:'text04',text:'text04',dictCode:''})
         fieldList.push({type:'string',value:'text05',text:'text05',dictCode:''})
         fieldList.push({type:'string',value:'attr1',text:'单据附件',dictCode:''})
         fieldList.push({type:'string',value:'attr2',text:'attr2',dictCode:''})
         fieldList.push({type:'string',value:'attr3',text:'attr3',dictCode:''})
         fieldList.push({type:'string',value:'attr4',text:'attr4',dictCode:''})
         fieldList.push({type:'string',value:'attr5',text:'attr5',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>