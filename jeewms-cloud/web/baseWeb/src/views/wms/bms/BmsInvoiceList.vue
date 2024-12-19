<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('开票编号')">
              <a-input :placeholder="$t('请输入开票编号')" v-model="queryParam.invoiceNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象编号')">
              <a-input :placeholder="$t('请输入计费对象编号')" v-model="queryParam.costObjNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item :label="$t('计费对象名称')">
              <a-input :placeholder="$t('请输入计费对象名称')" v-model="queryParam.costObjName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">{{$t('查询')}}</a-button>
              <a-button
                type="primary"
                @click="searchReset"
                icon="reload"
                style="margin-left: 8px"
              >{{$t('重置')}}</a-button>
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? $t('收起') : $t('展开') }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>-->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">{{$t('新增')}}</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('开票记录')">{{$t('导出')}}</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">{{$t('导入')}}</a-button>
      </a-upload>-->
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>{{$t('删除')}}</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>-->
      <a-button type="primary" icon="mail" @click="collection">{{$t('收款')}}</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> {{$t('已选择')}}
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>{{$t('项')}}
        <a style="margin-left: 24px" @click="onClearSelected">{{$t('清空')}}</a>
      </div>

      <a-tabs @change="tabChange" defaultActiveKey="未付款">
        <a-tab-pane :tab="$t('未付款')+'('+total1+')'" key="未付款" >
        <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="columns"
        :scroll="{x:true}"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
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
          <a v-else  @click="downloadFile(text)" >{{$t('下载')}}</a>
        </template>

        <span slot="action" slot-scope="text, record">
          <!-- <a @click="handleEdit(record)">{{$t('编辑')}}</a>

          <a-divider type="vertical" />-->
          <!-- <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">{{$t('详情')}}</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
                  <a>{{$t('删除')}}</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>-->
          <a @click="handleDetail(record)">{{$t('详情')}}</a>
          <!-- <a-divider type="vertical" />
            <a-popconfirm :title="$t('确定删除吗?')" @confirm="() => handleDelete(record.id)">
             <a>{{$t('删除')}}</a>
          </a-popconfirm>-->
        </span>
      </a-table>
    </a-tab-pane>
   
   <a-tab-pane :tab="$t('已付款')+'('+total2+')'" key="已付款" >
        <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        class="j-table-force-nowrap"
        :loading="loading"
        :columns="columns"
        :scroll="{x:true}"
        :dataSource="dataSource"
        :pagination="ipagination"
        :expandedRowKeys="expandedRowKeys"
        :rowSelection="{selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无图片')}}</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">{{$t('无文件')}}</span>
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
          <a @click="handleDetail(record)">{{$t('详情')}}</a>
        </span>
      </a-table>
    </a-tab-pane>
     </a-tabs>
  </div>

    <bms-invoice-modal ref="modalForm" @ok="modalFormOk"></bms-invoice-modal>
    <bms-invoice-modal2 ref="modalForm2" @ok="modalFormOk"></bms-invoice-modal2>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { getAction } from '@/api/manage'
import BmsInvoiceModal from './modules/BmsInvoiceModal'
import BmsInvoiceModal2 from './modules/BmsInvoiceModal2'
  export default {
    name: 'BmsInvoiceList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      BmsInvoiceModal,
      BmsInvoiceModal2
    },
    data () {
      return {
        description: '开票记录管理页面',
        total1:'0',
        total2:'0',
        disableMixinCreated:true,
        imStatus:'未付款',
        expandedRowKeys: [],
        // 表头
        columns: [
          // {
          //   title: '#',
          //   dataIndex: '',
          //   key:'rowIndex',
          //   width:60,
          //   align:"center",
          //   customRender:function (t,r,index) {
          //     return parseInt(index)+1;
          //   }
          // },
          {
            title: this.$t('操作'),
            dataIndex: 'action',
            align:"center",
            fixed:"left",
            width:147,
            scopedSlots: { customRender: 'action' }
          },
          {
            title: this.$t('计费对象类型'),
            align:"center",
            dataIndex: 'costObjType'
          },
          {
            title: this.$t('计费对象编号'),
            align:"center",
            dataIndex: 'costObjNo'
          },
          {
            title: this.$t('计费对象名称'),
            align:"center",
            dataIndex: 'costObjName'
          },
          {
            title: this.$t('开票编号'),
            align:"center",
            dataIndex: 'invoiceNo'
          },
          {
            title: this.$t('开票类型'),
            align:"center",
            dataIndex: 'invoiceType'
          },
          {
            title: this.$t('发票号'),
            align:"center",
            dataIndex: 'invoiceDocNo'
          },
          {
            title: this.$t('开票金额'),
            align:"center",
            dataIndex: 'invoiceSum'
          },
          {
            title: this.$t('税额'),
            align:"center",
            dataIndex: 'invoiceSe'
          },
          {
            title: this.$t('税率'),
            align:"center",
            dataIndex: 'costSl'
          },
          {
            title: this.$t('账单编号'),
            align:"center",
            dataIndex: 'billNo'
          },
          {
            title: this.$t('状态'),
            align:"center",
            dataIndex: 'status'
          },
          {
            title: this.$t('备注'),
            align:"center",
            dataIndex: 'remark'
          },
        ],
        url: {
          list: "/bms/bmsInvoice/list",
          delete: "/bms/bmsInvoice/delete",
          deleteBatch: "/bms/bmsInvoice/deleteBatch",
          exportXlsUrl: "/bms/bmsInvoice/exportXls",
          importExcelUrl: "bms/bmsInvoice/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    this.loadData()
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      tabChange(active){
        this.imStatus = active
        this.loadData()
      },
      loadData(arg) {
      if(!this.url.list){
        this.$message.error("请设置url.list属性!")
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1;
      }
      var params = this.getQueryParams();//查询条件
      params.status = this.imStatus
      this.loading = true;
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          //update-begin---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
          this.dataSource = res.result.records||res.result;
          if(this.imStatus == '未付款'){
            this.total1 = res.result.total;
          }else if(this.imStatus == '已付款'){
            this.total2 = res.result.total;
          }
          if(res.result.total)
          {
            this.ipagination.total = res.result.total;
          }
          //update-end---author:zhangyafei    Date:20201118  for：适配不分页的数据列表------------
        }
        if(res.code===510){
          this.$message.warning(res.message)
        }
        this.loading = false;
      })
    },
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'costObjType',text:'计费对象类型',dictCode:''})
        fieldList.push({type:'string',value:'costObjNo',text:'计费对象编号',dictCode:''})
        fieldList.push({type:'string',value:'costObjName',text:'计费对象名称',dictCode:''})
        fieldList.push({type:'string',value:'invoiceNo',text:'开票编号',dictCode:''})
        fieldList.push({type:'string',value:'invoiceType',text:'开票类型',dictCode:''})
        fieldList.push({type:'string',value:'invoiceDocNo',text:'发票号',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'invoiceSum',text:'开票金额',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'invoiceSe',text:'税额',dictCode:''})
        fieldList.push({type:'BigDecimal',value:'costSl',text:'税率',dictCode:''})
        fieldList.push({type:'popup',value:'billNo',text:'账单编号', popup:{code:'bms_bill_h',field:'bill_no',orgFields:'bill_no',destFields:'billNo'}})
        fieldList.push({type:'string',value:'status',text:'状态',dictCode:''})
        fieldList.push({type:'Text',value:'remark',text:'备注',dictCode:''})
        this.superFieldList = fieldList
      },
      collection(){
      if (!this.selectionRows || this.selectionRows.length == 0) {
        this.$message.warn('请至少选择一条数据')
        return
      }
      this.$refs.modalForm2.list(this.selectedRowKeys)
      this.$refs.modalForm2.title = '收款'
      this.$refs.modalForm2.disableSubmit = false
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>